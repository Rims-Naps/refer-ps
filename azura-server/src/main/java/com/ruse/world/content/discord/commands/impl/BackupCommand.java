package com.ruse.world.content.discord.commands.impl;

import org.json.JSONObject;
import com.ruse.world.content.discord.commands.BaseCommand;
import com.ruse.util.BackupUtil;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.grandexchange.GrandExchangeOffers;
import com.ruse.world.content.pos.PlayerOwnedShopManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Command to backup player data via WebSocket
 */
public class BackupCommand extends BaseCommand {

    @Override
    public String getName() {
        return "backup";
    }

    @Override
    public String getDescription() {
        return "Backup player data";
    }

    @Override
    public void execute(JSONObject data) {
        try {
            // Get channel ID for response
            String channelId = getOptString(data, "channel");
            String author = getOptString(data, "author");
            if (author == null) author = "Administrator";
            
            // Send initial response
            sendMessage(channelId, "⏳ Backup started... This may take a moment.");
            
            // Log the action
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            PlayerLogs.log(author, author + " initiated a server backup at " + timestamp);
            
            // Announce to players
            World.sendMessage("<col=FF0066><img=10> [SYSTEM]<col=6600FF> Server backup has been initiated by " + author + ".");
            
            // Perform all backups
            GrandExchangeOffers.save();
            ClanChatManager.save();
            PlayerOwnedShopManager.saveShops();
            World.savePlayers();
            BackupUtil.backup();
            
            // Log to Discord
            DiscordManager.sendMessage("[BACKUP] " + author + " has initiated a server backup at " + timestamp, Channels.ADMIN_COMMANDS);
            
            // Send success message
            sendSuccess(channelId, "Backup completed successfully. The following data was backed up:\n• Players\n• Grand Exchange\n• Clan Chats\n• Player Shops");
            
            System.out.println("[Discord] Backup initiated by " + author + " at " + timestamp);
        } catch (Exception e) {
            System.out.println("[Discord] Error in backup command: " + e.getMessage());
            e.printStackTrace();
            if (data != null) {
                sendError(getOptString(data, "channel"), "An error occurred while executing the backup command");
            }
        }
    }
} 