package com.ruse.world.content.discord.handler;

import com.ruse.world.content.discord.integration.WebSocketManager;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 * Manager for Discord functionality.
 * Handles WebSocket communication with the JavaScript Discord bot.
 */
public class DiscordManager {
    private static final Logger logger = Logger.getLogger(DiscordManager.class.getName());

    /**
     * Initialize the Discord manager.
     * Sets up WebSocket connection to the JavaScript bot.
     */
    public static void initialize() {
        try {
            WebSocketManager.initialize();
            logger.info("Discord manager initialized successfully");
        } catch (Exception e) {
            logger.severe("Failed to initialize Discord manager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Send a message to a Discord channel via WebSocket.
     * @param message The message to send
     * @param channelId The channel ID
     */
    public static void sendMessage(String message, long channelId) {
        try {
            // Send directly to WebSocketManager without additional JSON encoding
            logger.info("Sending message to Discord: " + message);
            WebSocketManager.sendToDiscord(String.valueOf(channelId), message);
        } catch (Exception e) {
            logger.severe("Failed to send message to Discord: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Shutdown the Discord manager.
     * Closes the WebSocket connection.
     */
    public static void shutdown() {
        try {
            WebSocketManager.shutdown();
            logger.info("Discord manager shutdown successfully");
        } catch (Exception e) {
            logger.severe("Error during Discord manager shutdown: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 