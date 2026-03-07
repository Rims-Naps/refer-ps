package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;

/**
 * Command to make server announcements via WebSocket
 */
public class AnnouncementCommand extends BaseCommand {

    @Override
    public String getName() {
        return "announce";
    }

    @Override
    public String getDescription() {
        return "Make a server announcement";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get command options
            JSONObject options = getOptObject(data, "options");
            String channelId = getOptString(data, "channel");
            
            if (options == null) {
                sendError(channelId, "Invalid command format");
                return;
            }
            
            // Get message from options
            String message = options.optString("message", "");
            String author = options.optString("author", "Administrator");
            
            if (isNullOrEmpty(message)) {
                sendError(channelId, "No announcement message provided");
                return;
            }
            
            // Format the announcement with proper colors and images
            String formattedMessage = "<img=10>@blu@<shad=0>[ANNOUNCEMENT] @red@<shad=0>" + message;
            
            // Log the action
            PlayerLogs.log(author, author + " made an announcement: " + message);
            
            // Send announcement to all players using game's broadcast method
            World.sendBroadcastMessage(formattedMessage);
            
            // Log to Discord
            DiscordManager.sendMessage("[ANNOUNCEMENT] " + author + " announced: " + message, Channels.ANNOUNCEMENTS);
            
            // Send success response
            sendSuccess(channelId, "Announcement sent to all players: " + message);
            
            System.out.println("[Discord] Announcement sent by " + author + ": " + message);
        } catch (Exception e) {
            System.out.println("[Discord] Error in announcement command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }
} 