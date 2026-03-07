package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;

/**
 * Command to toggle listening to in-game clan chat via WebSocket
 */
public class ListenCommand extends BaseCommand {

    private static boolean isListening = true;

    @Override
    public String getName() {
        return "listen";
    }

    @Override
    public String getDescription() {
        return "Toggle listening to in-game clan chat";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get channel ID for response
            String channelId = getOptString(data, "channel");
            String author = getOptString(data, "author");
            if (author == null) author = "Administrator";
            
            // Toggle the listening state
            isListening = !isListening;
            
            // Update the clan chat manager's listening state
            ClanChatManager.setDiscordListening(isListening);
            
            // Log the action
            PlayerLogs.log(author, author + " " + (isListening ? "enabled" : "disabled") + " Discord clan chat listening");
            
            // Log to Discord
            DiscordManager.sendMessage("[LISTEN] " + author + " " + (isListening ? "enabled" : "disabled") + " Discord clan chat listening", Channels.ADMIN_COMMANDS);
            
            // Send response
            sendSuccess(channelId, "Listening to in-game clan chat is now " + (isListening ? "enabled" : "disabled"));
            
            System.out.println("[Discord] Clan chat listening " + (isListening ? "enabled" : "disabled") + " by " + author);
        } catch (Exception e) {
            System.out.println("[Discord] Error in listen command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }

    /**
     * Get the current listening state
     * @return true if listening is enabled, false otherwise
     */
    public static boolean isListening() {
        return isListening;
    }
} 