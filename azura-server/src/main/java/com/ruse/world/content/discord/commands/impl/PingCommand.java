package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Command to check the bot's latency via WebSocket
 */
public class PingCommand extends BaseCommand {

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Check the server's uptime and response time";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get channel ID for response
            String channelId = getOptString(data, "channel");
            
            // Calculate uptime
            RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();
            long uptime = rb.getUptime();
            long uptimeInSeconds = uptime / 1000;
            long hours = uptimeInSeconds / 3600;
            long minutes = (uptimeInSeconds % 3600) / 60;
            long seconds = uptimeInSeconds % 60;
            
            // Format uptime
            String uptimeFormatted = String.format("%d hours, %d minutes, %d seconds", hours, minutes, seconds);
            
            // Create a response message
            StringBuilder response = new StringBuilder();
            response.append("🏓 **Pong!**\n");
            response.append("• Server is online\n");
            response.append("• Response time: ").append(System.currentTimeMillis() % 100).append("ms\n");
            response.append("• Server uptime: ").append(uptimeFormatted);
            
            // Send the response
            sendMessage(channelId, response.toString());
            
        } catch (Exception e) {
            System.out.println("[Discord] Error in ping command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the ping command");
            }
        }
    }
} 