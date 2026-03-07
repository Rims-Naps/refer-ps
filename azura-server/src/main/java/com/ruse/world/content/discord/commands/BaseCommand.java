package com.ruse.world.content.discord.commands;

import org.json.JSONObject;
import com.ruse.world.content.discord.integration.WebSocketManager;
import java.util.logging.Logger;

/**
 * Base class for all Discord commands.
 * Provides common functionality for WebSocket-based commands.
 */
public abstract class BaseCommand {

    protected static final Logger logger = Logger.getLogger(BaseCommand.class.getName());
    
    /**
     * Get the name of this command
     * @return The command name
     */
    public abstract String getName();
    
    /**
     * Get the description of this command
     * @return The command description
     */
    public abstract String getDescription();
    
    /**
     * Execute this command with the provided data
     * @param data The JSON data from Discord
     */
    public abstract void execute(JSONObject data);
    
    /**
     * Send a response message to Discord
     * @param channelId The channel ID to send to
     * @param message The message to send
     */
    protected void sendMessage(String channelId, String message) {
        WebSocketManager.sendToDiscord(channelId, message);
    }
    
    /**
     * Send an error message to Discord
     * @param channelId The channel ID to send to
     * @param message The error message
     */
    protected void sendError(String channelId, String message) {
        WebSocketManager.sendToDiscord(channelId, "⚠️ **Error**: " + message);
    }
    
    /**
     * Send a success message to Discord
     * @param channelId The channel ID to send to
     * @param message The success message
     */
    protected void sendSuccess(String channelId, String message) {
        WebSocketManager.sendToDiscord(channelId, "✅ **Success**: " + message);
    }
    
    /**
     * Send a file to Discord
     * @param channelId The channel ID to send to
     * @param file The file to send
     * @param fileName The name to give the file
     */
    protected void sendFile(String channelId, java.io.File file, String fileName) {
        WebSocketManager.sendFileToDiscord(channelId, file, fileName);
    }
    
    /**
     * Check if a string is null or empty
     * @param str The string to check
     * @return True if the string is null or empty
     */
    protected boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
    /**
     * Get an optional string from a JSON object
     * @param data The JSON object
     * @param key The key to get
     * @return The string value or null if not found
     */
    protected String getOptString(JSONObject data, String key) {
        return data.optString(key, null);
    }
    
    /**
     * Get an optional JSON object from a JSON object
     * @param data The JSON object
     * @param key The key to get
     * @return The JSON object or null if not found
     */
    protected JSONObject getOptObject(JSONObject data, String key) {
        return data.optJSONObject(key);
    }
    
    /**
     * Get an optional integer from a JSON object
     * @param data The JSON object
     * @param key The key to get
     * @param defaultValue The default value if not found
     * @return The integer value or defaultValue if not found
     */
    protected int getOptInt(JSONObject data, String key, int defaultValue) {
        return data.optInt(key, defaultValue);
    }
} 