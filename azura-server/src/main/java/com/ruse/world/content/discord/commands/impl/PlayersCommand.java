package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Command to display online players via WebSocket
 */
public class PlayersCommand extends BaseCommand {

    @Override
    public String getName() {
        return "players";
    }

    @Override
    public String getDescription() {
        return "Display online players";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get channel ID for response
            String channelId = getOptString(data, "channel");
            
            // Get online players
            int playerCount = World.getPlayers().size();
            List<String> playerNames = new ArrayList<>();
            
            // Get player names (up to 30 for display)
            for (Player player : World.getPlayers()) {
                if (player != null && playerNames.size() < 30) {
                    playerNames.add(player.getUsername());
                }
            }
            
            // Create response message
            StringBuilder response = new StringBuilder();
            response.append("**Online Players: ").append(playerCount).append("**\n\n");
            
            if (playerCount > 0) {
                response.append("```");
                for (int i = 0; i < playerNames.size(); i++) {
                    response.append(playerNames.get(i));
                    
                    // Add comma separator if not the last player
                    if (i < playerNames.size() - 1) {
                        response.append(", ");
                    }
                    
                    // Add newline every 3 players for better formatting
                    if ((i + 1) % 3 == 0) {
                        response.append("\n");
                    }
                }
                response.append("```");
                
                // If not all players are shown, add a note
                if (playerCount > playerNames.size()) {
                    response.append("\n*...and ").append(playerCount - playerNames.size()).append(" more players*");
                }
            } else {
                response.append("*No players online at the moment*");
            }
            
            // Send response
            sendMessage(channelId, response.toString());
            
        } catch (Exception e) {
            System.out.println("[Discord] Error in players command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }
} 