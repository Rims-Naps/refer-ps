package com.ruse.world.content.discord.handler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Handler for Discord commands.
 */
public class DiscordCommandHandler extends ListenerAdapter {
    
    private final DiscordCommandManager commandManager;
    
    /**
     * Constructor for the command handler.
     */
    public DiscordCommandHandler() {
        this.commandManager = DiscordCommandManager.getInstance();
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        // Ignore messages from bots
        if (event.getAuthor().isBot()) {
            return;
        }
        
        String content = event.getMessage().getContentRaw();
        
        // Check if the message starts with a slash (slash command)
        if (content.startsWith("/")) {
            // Split the command and arguments
            String[] parts = content.substring(1).split("\\s+");
            String command = parts[0].toLowerCase();
            String[] args = new String[parts.length - 1];
            System.arraycopy(parts, 1, args, 0, args.length);
            
            // Execute the command
            commandManager.executeCommand(command, event, args);
        }
    }
} 