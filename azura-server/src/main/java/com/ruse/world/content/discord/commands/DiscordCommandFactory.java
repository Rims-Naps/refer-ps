package com.ruse.world.content.discord.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating and managing Discord commands.
 * Maintains a registry of all available commands.
 */
public class DiscordCommandFactory {
    
    private final Map<String, DiscordCommand> commands;
    
    public DiscordCommandFactory() {
        this.commands = new HashMap<>();
        registerCommands();
    }
    
    /**
     * Register all available commands
     */
    private void registerCommands() {
        // Add commands here as they are created
        // Example: registerCommand(new PlayersCommand());
        // Example: registerCommand(new StatsCommand());
    }
    
    /**
     * Register a new command
     * @param command The command to register
     */
    public void registerCommand(DiscordCommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }
    
    /**
     * Get a command by name
     * @param name The name of the command
     * @return The command instance, or null if not found
     */
    public DiscordCommand getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
    
    /**
     * Get all registered commands
     * @return Map of command names to command instances
     */
    public Map<String, DiscordCommand> getAllCommands() {
        return new HashMap<>(commands);
    }
    
    /**
     * Get all commands that don't require admin permissions
     * @return Map of command names to command instances
     */
    public Map<String, DiscordCommand> getPublicCommands() {
        Map<String, DiscordCommand> publicCommands = new HashMap<>();
        commands.forEach((name, command) -> {
            if (!command.isAdminOnly()) {
                publicCommands.put(name, command);
            }
        });
        return publicCommands;
    }
} 