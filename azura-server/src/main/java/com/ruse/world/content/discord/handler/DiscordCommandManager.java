package com.ruse.world.content.discord.handler;

import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.content.discord.commands.impl.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager for Discord commands.
 */
public class DiscordCommandManager {

    private static DiscordCommandManager instance;
    private final Map<String, BaseCommand> commands;

    private DiscordCommandManager() {
        commands = new HashMap<>();
        registerCommands();
    }

    /**
     * Get the singleton instance of the command manager.
     * @return The command manager instance
     */
    public static DiscordCommandManager getInstance() {
        if (instance == null) {
            instance = new DiscordCommandManager();
        }
        return instance;
    }

    private void registerCommands() {
        // Register all commands
        registerCommand(new PingCommand());
        registerCommand(new PlayersCommand());
        registerCommand(new SendItemCommand());
        registerCommand(new MoveHomeCommand());
        registerCommand(new KillCommand());
        registerCommand(new BackupCommand());
        registerCommand(new CharFileCommand());
        registerCommand(new LogFileCommand());
        registerCommand(new ListenCommand());
        registerCommand(new AnnouncementCommand());
        registerCommand(new HelpCommand());
        registerCommand(new StatsCommand());
    }

    /**
     * Register a command.
     * @param command The command to register
     */
    private void registerCommand(BaseCommand command) {
        commands.put(command.getName().toLowerCase(), command);
    }

    /**
     * Get all registered commands.
     * @return Map of command names to command instances
     */
    public Map<String, BaseCommand> getCommands() {
        return commands;
    }

    /**
     * Execute a command.
     * @param command The command name
     * @param event The message event
     * @param args The command arguments
     */
    public void executeCommand(String commandName, MessageReceivedEvent event, String[] args) {
        BaseCommand command = commands.get(commandName.toLowerCase());
        if (command != null) {
            command.execute(event, args);
        }
    }
}