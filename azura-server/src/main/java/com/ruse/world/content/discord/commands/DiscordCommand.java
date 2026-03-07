package com.ruse.world.content.discord.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * Interface for all Discord commands.
 * Defines the contract that all commands must implement.
 */
public interface DiscordCommand {
    
    /**
     * Get the name of the command
     * @return The command name
     */
    String getName();
    
    /**
     * Get the description of the command
     * @return The command description
     */
    String getDescription();
    
    /**
     * Get the usage of the command
     * @return The command usage
     */
    String getUsage();
    
    /**
     * Check if the command is enabled
     * @return true if the command is enabled
     */
    boolean isEnabled();
    
    /**
     * Check if the command is admin only
     * @return true if the command is admin only
     */
    boolean isAdminOnly();
    
    /**
     * Get the minimum number of arguments required
     * @return The minimum number of arguments
     */
    int getMinArgs();
    
    /**
     * Get the maximum number of arguments allowed
     * @return The maximum number of arguments, or -1 for unlimited
     */
    int getMaxArgs();
    
    /**
     * Execute the command
     * @param event The message event
     * @param args The command arguments
     */
    void execute(MessageReceivedEvent event, String[] args);
} 