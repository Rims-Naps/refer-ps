package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.Color;

/**
 * Command to send an item to a player via WebSocket
 */
public class SendItemCommand extends BaseCommand {

    @Override
    public String getName() {
        return "senditem";
    }

    @Override
    public String getDescription() {
        return "Send an item to a player";
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
            
            // Get parameters
            String playerName = options.optString("player", "");
            int itemId = options.optInt("itemId", -1);
            int amount = options.optInt("amount", 1);
            
            if (isNullOrEmpty(playerName)) {
                sendError(channelId, "No player name provided");
                return;
            }
            
            if (itemId <= 0) {
                sendError(channelId, "Invalid item ID: " + itemId);
                return;
            }
            
            // Find the player
            Player target = World.getPlayerByName(playerName);
            if (target == null) {
                sendError(channelId, "Player not found: " + playerName);
                return;
            }
            
            // Check item definition
            ItemDefinition itemDef = ItemDefinition.forId(itemId);
            if (itemDef == null || itemDef.getName().equalsIgnoreCase("null")) {
                sendError(channelId, "Invalid item ID: " + itemId);
                return;
            }
            
            // Log the action
            PlayerLogs.log(author, author + " gave " + amount + "x " + itemDef.getName() + " to " + playerName);
            
            // Add the item to the player's bank
            target.getBank(0).add(itemId, amount);
            
            // Notify the target player
            target.sendMessage("<shad=0>@red@Check your Bank!" + " <shad=0>@red@You received a(n) " + itemDef.getName() + " <shad=0>@red@from Discord");
            
            // Log to Discord
            DiscordManager.sendMessage("[ITEM] " + author + " gave " + playerName + " " + amount + "x " + itemDef.getName() + " (" + itemId + ")", Channels.ADMIN_COMMANDS);
            
            // Send success message
            sendSuccess(channelId, "Successfully sent " + amount + "x " + itemDef.getName() + " to " + playerName + "'s bank");
            
            System.out.println("[Discord] Successfully sent " + amount + "x " + itemDef.getName() + " to " + playerName + "'s bank");
        } catch (Exception e) {
            System.out.println("[Discord] Error in senditem command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the command");
            }
        }
    }
} 