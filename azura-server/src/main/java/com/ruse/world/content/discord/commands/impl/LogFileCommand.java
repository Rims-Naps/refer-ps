package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Command to retrieve log files via WebSocket
 */
public class LogFileCommand extends BaseCommand {

    private static final List<String> VALID_DIRECTORIES = Arrays.asList(
        "chat", "commands", "logins", "loginsWIP", "npcdrops", 
        "other", "pickups", "pms", "trades"
    );

    @Override
    public String getName() {
        return "logfile";
    }

    @Override
    public String getDescription() {
        return "Retrieve a specific log file";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get command options
            JSONObject options = getOptObject(data, "options");
            String channelId = getOptString(data, "channel");
            String author = getOptString(data, "author");
            if (author == null) author = "Administrator";
            
            if (options == null) {
                sendError(channelId, "Invalid command format");
                return;
            }
            
            // Get parameters
            String directory = options.optString("directory", "").toLowerCase();
            String playerName = options.optString("player", "");
            
            if (isNullOrEmpty(directory) || isNullOrEmpty(playerName)) {
                sendError(channelId, "Missing directory or player name");
                return;
            }
            
            // Validate directory
            if (!VALID_DIRECTORIES.contains(directory)) {
                sendError(channelId, "Invalid directory. Available directories: " + String.join(", ", VALID_DIRECTORIES));
                return;
            }
            
            // Construct the log file path
            String logPath = "data/logs/" + directory + "/" + playerName + ".txt";
            File logFile = new File(logPath);
            
            if (!logFile.exists()) {
                sendError(channelId, "Log file not found for player: " + playerName);
                return;
            }
            
            // Log the action
            PlayerLogs.log(author, author + " requested log file for " + playerName + " from " + directory + " directory");
            
            // Log to Discord
            DiscordManager.sendMessage("[LOGFILE] " + author + " requested log file for " + playerName + " from " + directory, Channels.ADMIN_COMMANDS);
            
            // Send the log file
            sendFile(channelId, logFile, playerName + "_" + directory + ".txt");
            
            // Send success message
            sendSuccess(channelId, "Log file retrieved for player: " + playerName + " from " + directory);
            
            System.out.println("[Discord] Log file for " + playerName + " from " + directory + " sent to " + author);
        } catch (Exception e) {
            System.out.println("[Discord] Error in logfile command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }
} 