package com.ruse.world.content.discord.commands.impl;

import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import org.json.JSONObject;

/**
 * Command to display player statistics via WebSocket
 */
public class StatsCommand extends BaseCommand {

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Display server and player statistics";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get channel ID for response
            String channelId = getOptString(data, "channel");
            
            // Get online players count
            int onlinePlayers = World.getPlayers().size();
            
            // Get total registered players (example implementation)
            int totalPlayers = getTotalRegisteredPlayers();
            
            // Get uptime
            long uptime = System.currentTimeMillis() - World.getStartTime();
            long uptimeHours = uptime / (1000 * 60 * 60);
            long uptimeMinutes = (uptime / (1000 * 60)) % 60;
            
            // Format uptime
            String uptimeFormatted = String.format("%d hours, %d minutes", uptimeHours, uptimeMinutes);
            
            // Create response message
            StringBuilder response = new StringBuilder("**Server Statistics**\n");
            response.append("• Online Players: ").append(onlinePlayers).append("\n");
            response.append("• Total Registered Players: ").append(totalPlayers).append("\n");
            response.append("• Server Uptime: ").append(uptimeFormatted).append("\n");
            
            // Add any additional statistics
            response.append("• CPU Usage: ").append(getCpuUsage()).append("%\n");
            response.append("• Memory Usage: ").append(getMemoryUsage()).append("MB\n");
            
            // Send response
            sendMessage(channelId, response.toString());
            
        } catch (Exception e) {
            System.out.println("[Discord] Error in stats command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the stats command");
            }
        }
    }
    
    private int getTotalRegisteredPlayers() {
        // Implementation to get total registered players
        // This is just an example - replace with actual implementation
        return 5000;
    }
    
    private double getCpuUsage() {
        // Implementation to get CPU usage
        // This is just an example - replace with actual implementation
        return 25.5;
    }
    
    private int getMemoryUsage() {
        // Implementation to get memory usage
        // This is just an example - replace with actual implementation
        return (int)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
    }
} 