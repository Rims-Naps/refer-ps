package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.model.CombatIcon;
import com.ruse.model.Hit;
import com.ruse.model.Hitmask;

/**
 * Command to kill a player via WebSocket
 */
public class KillCommand extends BaseCommand {

    @Override
    public String getName() {
        return "kill";
    }

    @Override
    public String getDescription() {
        return "Kill a player";
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
            Player target = World.getPlayerByName(playerName);
            if (target == null) {
                sendError(channelId, "Player not found: " + playerName);
                return;
            }
            
            // Log the action
            PlayerLogs.log(author, author + " killed player: " + playerName);
            
            // Kill the player
            target.getCombatBuilder().reset(true);
            target.dealDamage(new Hit(9999, Hitmask.NEUTRAL, CombatIcon.MELEE));
            
            // Log to Discord
            DiscordManager.sendMessage("[KILL] " + author + " killed player: " + playerName, Channels.ADMIN_COMMANDS);
            
            // Send success message
            sendSuccess(channelId, "Player " + playerName + " has been killed");
            
            System.out.println("[Discord] Player " + playerName + " killed by " + author);
        } catch (Exception e) {
            System.out.println("[Discord] Error in kill command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }
}