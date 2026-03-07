package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.model.Position;
import com.ruse.world.content.discord.constants.Channels;

/**
 * Command to move a player to their home location via WebSocket
 */
public class MoveHomeCommand extends BaseCommand {

    @Override
    public String getName() {
        return "movehome";
    }

    @Override
    public String getDescription() {
        return "Move a player to their home location";
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
            
            // Find the player
            Player player = World.getPlayerByName(playerName);
            if (player == null) {
                sendError(channelId, "Player not found: " + playerName);
                return;
            }
            
            // Log the action
            PlayerLogs.log(author, author + " moved " + playerName + " to home");
            
            // Move the player to their home location
            player.moveTo(new Position(3088, 3493, 0));
            
            // Notify the player
            player.sendMessage("You have been teleported to your home location by an administrator.");
            
            // Log to Discord
            DiscordManager.sendMessage("[MOVEHOME] " + author + " moved " + playerName + " to home", Channels.ADMIN_COMMANDS);
            
            // Send success response
            sendSuccess(channelId, "Player " + playerName + " has been moved to their home location");
            
            System.out.println("[Discord] " + playerName + " moved to home by " + author);
        } catch (Exception e) {
            System.out.println("[Discord] Error in movehome command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }
} 