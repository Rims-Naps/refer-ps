package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;

import java.io.File;

/**
 * Command to get a player's character file via WebSocket
 */
public class CharFileCommand extends BaseCommand {

    @Override
    public String getName() {
        return "charfile";
    }

    @Override
    public String getDescription() {
        return "Get a player's character file";
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
            
            // Get player name from options
            String playerName = options.optString("player", "");
            
            if (isNullOrEmpty(playerName)) {
                sendError(channelId, "No player name provided");
                return;
            }
            
            // Check if player is online
            Player target = World.getPlayerByName(playerName);
            if (target != null) {
                sendError(channelId, "Player is currently online. Please wait until they log out.");
                return;
            }
            
            // Log the action
            PlayerLogs.log(author, author + " requested character file for: " + playerName);
            
            // Get character file
            File charFile = new File("./data/saves/characters/" + playerName + ".json");
            if (!charFile.exists()) {
                sendError(channelId, "Character file not found for: " + playerName);
                return;
            }
            
            // Log to Discord
            DiscordManager.sendMessage("[CHARFILE] " + author + " requested character file for: " + playerName, Channels.ADMIN_COMMANDS);
            
            // Send file
            sendFile(channelId, charFile, playerName + ".json");
            
            System.out.println("[Discord] Character file for " + playerName + " sent to " + author);
        } catch (Exception e) {
            System.out.println("[Discord] Error in charfile command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }
} 