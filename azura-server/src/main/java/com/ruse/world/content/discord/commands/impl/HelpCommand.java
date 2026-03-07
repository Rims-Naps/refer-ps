package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.content.discord.handler.DiscordCommandManager;

import java.util.Map;

/**
 * Command to display help information via WebSocket
 */
public class HelpCommand extends BaseCommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display help information for commands";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get channel ID for response
            String channelId = getOptString(data, "channel");
            
            // Get options
            JSONObject options = getOptObject(data, "options");
            String commandName = options != null ? options.optString("command", null) : null;
            
            // Get all commands
            Map<String, BaseCommand> commands = DiscordCommandManager.getInstance().getCommands();
            
            if (isNullOrEmpty(commandName)) {
                // Show all commands
                StringBuilder response = new StringBuilder("**Available Commands**\n");
                response.append("Use `/help <command>` for more information about a specific command.\n\n");
                
                for (BaseCommand command : commands.values()) {
                    response.append("• **").append(command.getName()).append("** - ");
                    response.append(command.getDescription()).append("\n");
                }
                
                // Send response
                sendMessage(channelId, response.toString());
            } else {
                // Show specific command help
                commandName = commandName.toLowerCase();
                BaseCommand command = commands.get(commandName);
                
                if (command != null) {
                    StringBuilder response = new StringBuilder();
                    response.append("**Command: ").append(command.getName()).append("**\n\n");
                    response.append("**Description:** ").append(command.getDescription()).append("\n");
                    
                    // Add examples if we have any
                    if (commandName.equals("kill")) {
                        response.append("\n**Example:** `/kill username`");
                    } else if (commandName.equals("announce")) {
                        response.append("\n**Example:** `/announce Server will restart in 5 minutes!`");
                    } else if (commandName.equals("backup")) {
                        response.append("\n**Example:** `/backup`");
                    } else if (commandName.equals("charfile")) {
                        response.append("\n**Example:** `/charfile username`");
                    } else if (commandName.equals("senditem")) {
                        response.append("\n**Example:** `/senditem username 4151 1` (gives an Abyssal whip)");
                    }
                    
                    // Send response
                    sendMessage(channelId, response.toString());
                } else {
                    sendError(channelId, "Command not found: " + commandName);
                }
            }
        } catch (Exception e) {
            System.out.println("[Discord] Error in help command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the help command");
            }
        }
    }
} 