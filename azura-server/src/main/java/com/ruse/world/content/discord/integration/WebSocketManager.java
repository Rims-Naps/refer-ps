package com.ruse.world.content.discord.integration;

import com.ruse.world.content.discord.constants.Guilds;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import com.ruse.world.content.discord.handler.DiscordCommandManager;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.model.definitions.ItemDefinition;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.awt.Color;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.EnumSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Collection;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

/**
 * Manages WebSocket connection to the Discord bot.
 * Handles message queuing, rate limiting, and reconnection.
 * Supports both text commands and file transfers.
 */
public class WebSocketManager {
    private static WebSocketClient discordClient;
    private static final String WS_URL = "ws://localhost:8080";
    private static final String SECRET_KEY = "wBPSxMd5QWESIfHftxLRLwWJBtd0X9Vk";
    private static JDA jda;
    private static final String BOT_TOKEN = "your_token_here";

    // Rate limiting settings
    private static final int MESSAGES_PER_SECOND = 5;
    private static final long RATE_LIMIT_INTERVAL = 1000;

    // Message queue and executor
    private static final Queue<JSONObject> messageQueue = new LinkedList<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final BlockingQueue<Boolean> rateLimitSemaphore = new ArrayBlockingQueue<>(MESSAGES_PER_SECOND);

    // Reconnection settings
    private static final int MAX_RECONNECT_ATTEMPTS = 5;
    private static final long RECONNECT_DELAY = 5000;
    private static int reconnectAttempts = 0;
    private static final ExecutorService reconnectExecutor = Executors.newSingleThreadExecutor();

    static {
        // Initialize rate limit semaphore
        for (int i = 0; i < MESSAGES_PER_SECOND; i++) {
            rateLimitSemaphore.offer(true);
        }

        // Start message processor
        scheduler.scheduleAtFixedRate(WebSocketManager::processMessageQueue, 0, 100, TimeUnit.MILLISECONDS);

        // Initialize JDA
        try {
            jda = JDABuilder.createDefault(BOT_TOKEN)
                    .enableIntents(EnumSet.allOf(GatewayIntent.class))
                    .enableCache(EnumSet.allOf(CacheFlag.class))
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .build();

            // Wait for JDA to be ready
            jda.awaitReady();
            System.out.println("[Discord] JDA initialized successfully");

            // Check for main guild
            Guild mainGuild = jda.getGuildById(Guilds.MAIN_GUILD);
            if (mainGuild == null) {
                System.out.println("[Discord] WARNING: Bot is not in the main guild! (" + Guilds.MAIN_GUILD + ")");
                System.out.println("[Discord] Please invite the bot using this link:");
                System.out.println("[Discord] https://discord.com/api/oauth2/authorize?client_id=" + jda.getSelfUser().getId() + "&permissions=8&scope=bot%20applications.commands");
            } else {
                System.out.println("[Discord] Connected to main guild: " + mainGuild.getName());
                System.out.println("[Discord] Available channels in main guild:");
                for (TextChannel channel : mainGuild.getTextChannels()) {
                    System.out.println("[Discord] Channel: " + channel.getName() + " (" + channel.getId() + ")");
                }
            }
        } catch (Exception e) {
            System.out.println("[Discord] Error initializing JDA: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Initialize the WebSocket connection to the Discord bot.
     */
    public static void initialize() {
        try {
            System.out.println("[Discord] Initializing WebSocket connection to " + WS_URL);
            discordClient = new WebSocketClient(new URI(WS_URL + "?key=" + SECRET_KEY)) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("[Discord] WebSocket connected successfully");
                    reconnectAttempts = 0;
                }

                @Override
                public void onMessage(String message) {
                    try {
                        System.out.println("[Discord] Received raw message: " + message);
                        JSONObject data = new JSONObject(message);
                        String type = data.getString("type");
                        System.out.println("[Discord] Received message type: " + type);

                        // Handle different types of messages
                        switch (type) {
                            case "message":
                            case "global_message":
                            case "channel_message":
                                // Handle regular messages
                                handleMessage(data);
                                break;
                            case "command":
                                // Handle commands
                                handleCommand(data);
                                break;
                            case "kill":
                                // Handle kill command with player name
                                System.out.println(data);
                                String killPlayerName = data.optString("playername", "");
                                if (killPlayerName.isEmpty()) {
                                    System.out.println("[Discord] Error: No player name provided for kill command");
                                    return;
                                }

                                JSONObject killCommandData = new JSONObject();
                                killCommandData.put("type", "command");
                                killCommandData.put("command", "kill");

                                JSONObject killOptions = new JSONObject();
                                killOptions.put("player", killPlayerName);
                                killCommandData.put("options", killOptions);

                                handleCommand(killCommandData);
                                break;
                            case "backup":
                                // Handle backup command
                                JSONObject backupCommandData = new JSONObject();
                                backupCommandData.put("type", "command");
                                backupCommandData.put("command", "backup");
                                handleCommand(backupCommandData);
                                break;
                            case "charfile":
                                // Handle charfile command
                                String charPlayerName = data.optString("username", "");
                                if (charPlayerName.isEmpty()) {
                                    System.out.println("[Discord] Error: No player name provided for charfile command");
                                    return;
                                }

                                JSONObject charfileCommandData = new JSONObject();
                                charfileCommandData.put("type", "command");
                                charfileCommandData.put("command", "charfile");
                                charfileCommandData.put("channel", data.optString("channel", ""));

                                JSONObject charfileOptions = new JSONObject();
                                charfileOptions.put("player", charPlayerName);
                                charfileCommandData.put("options", charfileOptions);
                                handleCommand(charfileCommandData);
                                break;
                            case "logfile":
                                // Handle logfile command
                                String directory = data.optString("directory", "");
                                String playerName = data.optString("playername", "");
                                if (directory.isEmpty() || playerName.isEmpty()) {
                                    System.out.println("[Discord] Error: Missing parameters for logfile command");
                                    return;
                                }

                                JSONObject logfileCommandData = new JSONObject();
                                logfileCommandData.put("type", "command");
                                logfileCommandData.put("command", "logfile");

                                JSONObject logfileOptions = new JSONObject();
                                logfileOptions.put("directory", directory);
                                logfileOptions.put("player", playerName);
                                logfileCommandData.put("options", logfileOptions);

                                handleCommand(logfileCommandData);
                                break;
                            case "movehome":
                                // Handle movehome command
                                String movePlayerName = data.optString("username", "");
                                if (movePlayerName.isEmpty()) {
                                    System.out.println("[Discord] Error: No player name provided for movehome command");
                                    return;
                                }

                                JSONObject movehomeCommandData = new JSONObject();
                                movehomeCommandData.put("type", "command");
                                movehomeCommandData.put("command", "movehome");

                                JSONObject movehomeOptions = new JSONObject();
                                movehomeOptions.put("player", movePlayerName);
                                movehomeCommandData.put("options", movehomeOptions);

                                handleCommand(movehomeCommandData);
                                break;
                            case "senditem":
                                // Handle senditem command
                                System.out.println(data);
                                int itemId = data.optInt("itemid", 0);
                                int amount = data.optInt("amount", 1);
                                String itemPlayerName = data.optString("username", "");
                                String authorName = data.optString("author", "Discord Bot");

                                if (itemId <= 0 || itemPlayerName.isEmpty()) {
                                    System.out.println("[Discord] Error: Invalid parameters for senditem command");
                                    return;
                                }

                                JSONObject senditemCommandData = new JSONObject();
                                senditemCommandData.put("type", "command");
                                senditemCommandData.put("command", "senditem");

                                JSONObject senditemOptions = new JSONObject();
                                senditemOptions.put("itemId", itemId);
                                senditemOptions.put("amount", amount);
                                senditemOptions.put("player", itemPlayerName);
                                senditemCommandData.put("options", senditemOptions);

                                handleCommand(senditemCommandData);

                                // Log to admin commands channel
                                String logMessage = String.format("```[GIVEITEM] %s gave %s x %d %s (%d)```",
                                        authorName,
                                        itemPlayerName,
                                        amount,
                                        ItemDefinition.forId(itemId).getName(),
                                        itemId);
                                sendToDiscord("admin_commands", logMessage);
                                break;
                            case "listen":
                                // Handle listen command
                                JSONObject listenCommandData = new JSONObject();
                                listenCommandData.put("type", "command");
                                listenCommandData.put("command", "listen");
                                handleCommand(listenCommandData);
                                break;
                            case "stats_request":
                                // Handle stats request
                                JSONObject statsCommandData = new JSONObject();
                                statsCommandData.put("type", "command");
                                statsCommandData.put("command", "stats");
                                statsCommandData.put("channel", data.optString("channel", ""));
                                handleCommand(statsCommandData);
                                break;
                            default:
                                // Handle other message types
                                handleMessage(data);
                        }
                    } catch (Exception e) {
                        System.out.println("[Discord] Error processing message: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("[Discord] WebSocket disconnected: " + reason + " (code: " + code + ", remote: " + remote + ")");
                    // Schedule reconnect in a separate thread
                    reconnectExecutor.submit(() -> {
                        try {
                            Thread.sleep(RECONNECT_DELAY);
                            reconnect();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
                }

                @Override
                public void onError(Exception ex) {
                    System.out.println("[Discord] WebSocket error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            };

            System.out.println("[Discord] Attempting to connect to WebSocket server...");
            discordClient.connect();
        } catch (Exception e) {
            System.out.println("[Discord] Error initializing WebSocket: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle commands
     * @param commandData The command data
     */
    private static void handleCommand(JSONObject commandData) {
        try {
            System.out.println("COMMAND DATA = " + commandData);
            String commandName = commandData.getString("command");
            System.out.println("[Discord] Processing command: " + commandName);

            // Get the command from the manager
            BaseCommand command = DiscordCommandManager.getInstance().getCommands().get(commandName.toLowerCase());
            System.out.println("[Discord] Found command: " + (command != null ? command.getClass().getSimpleName() : "null"));

            if (command != null) {
                System.out.println("INSIDE IF, RAW DATA - " + commandData);
                // Get the channel ID from the command data
                String channelId = commandData.optString("channel", "");
                if (!channelId.isEmpty()) {
                    // Execute the command
                    command.execute(commandData);
                } else {
                    System.out.println("[Discord] No channel ID provided in command data");
                }
            } else {
                System.out.println("[Discord] Command not found: " + commandName);
            }
        } catch (Exception e) {
            System.out.println("[Discord] Error handling command: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle regular messages
     * @param data The message data
     */
    private static void handleMessage(JSONObject data) {
        try {
            String channelId = data.optString("channel", "");
            if (!channelId.isEmpty()) {
                String message = data.optString("message", "");
                if (!message.isEmpty()) {
                    sendToDiscord(channelId, message);
                }
            }
        } catch (Exception e) {
            System.out.println("[Discord] Error handling message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Send a message to Discord
     * @param channel The channel ID
     * @param content The message content
     */
    public static void sendToDiscord(String channel, String content) {
        try {
            JSONObject message = new JSONObject();
            message.put("type", "message");
            message.put("channel", channel);
            message.put("message", content);
            message.put("username", "Server");

            messageQueue.offer(message);
            processMessageQueue();
        } catch (Exception e) {
            System.out.println("[Discord] Error queueing message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Send a file to Discord
     * @param channel The channel ID
     * @param file The file to send
     * @param fileName The name to give the file
     */
    public static void sendFileToDiscord(String channel, File file, String fileName) {
        try {
            // Read file content
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String base64Content = Base64.getEncoder().encodeToString(fileContent);

            // Create message
            JSONObject message = new JSONObject();
            message.put("type", "file");
            message.put("channel", channel);
            message.put("filename", fileName);
            message.put("content", base64Content);

            messageQueue.offer(message);
            processMessageQueue();
        } catch (Exception e) {
            System.out.println("[Discord] Error sending file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Process the message queue with rate limiting
     */
    private static void processMessageQueue() {
        try {
            if (messageQueue.isEmpty()) {
                return;
            }

            Boolean token = rateLimitSemaphore.poll(100, TimeUnit.MILLISECONDS);
            if (token != null) {
                JSONObject message = messageQueue.poll();
                if (message != null && discordClient != null && discordClient.isOpen()) {
                    discordClient.send(message.toString());
                }
                rateLimitSemaphore.offer(true);
            }
        } catch (Exception e) {
            System.out.println("[Discord] Error processing message queue: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Attempt to reconnect to the WebSocket server.
     */
    private static void reconnect() {
        if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
            reconnectAttempts++;
            System.out.println("[Discord] Attempting to reconnect (attempt " + reconnectAttempts + " of " + MAX_RECONNECT_ATTEMPTS + ")");

            try {
                if (discordClient != null) {
                    discordClient.close();
                }
                initialize();
            } catch (Exception e) {
                System.out.println("[Discord] Error during reconnect: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("[Discord] Max reconnection attempts reached, giving up");
        }
    }

    /**
     * Shutdown the WebSocket connection and cleanup resources.
     */
    public static void shutdown() {
        try {
            if (discordClient != null) {
                discordClient.close();
            }
            if (jda != null) {
                jda.shutdown();
            }
            scheduler.shutdown();
            reconnectExecutor.shutdown();
            if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduler.shutdownNow();
            }
            if (!reconnectExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                reconnectExecutor.shutdownNow();
            }
        } catch (Exception e) {
            System.out.println("[Discord] Error during shutdown: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Send a command to Discord
     * @param commandData The command data
     */
    public static void sendCommandToDiscord(JSONObject commandData) {
        try {
            messageQueue.offer(commandData);
            processMessageQueue();
        } catch (Exception e) {
            System.out.println("[Discord] Error sending command to Discord: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get the WebSocket secret key
     * @return The secret key
     */
    public static String getSecretKey() {
        return SECRET_KEY;
    }
} 