package com.ruse.model.input.impl.Moderation;

import com.ruse.model.input.Input;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.entity.impl.player.PlayerSaving;

public class UnbanPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playerToUnban) {
        if (!PlayerSaving.playerExists(playerToUnban)) {
            player.getPacketSender().sendMessage("Player " + playerToUnban + " does not exist.");
            return;
        } else {
            if (!PlayerPunishment.banned(playerToUnban)) {
                player.getPacketSender().sendMessage("Player " + playerToUnban + " is not banned!");
                return;
            }
            PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " just unbanned " + playerToUnban + "!");
            DiscordManager.sendMessage("[UNBAN] " + player.getUsername() + " has just unbanned " + playerToUnban + ".", Channels.PUNISHMENTS);

            PlayerPunishment.unban(playerToUnban);
            player.getPacketSender().sendMessage("Player " + playerToUnban + " was successfully unbanned. Command logs written.");
        }
    }
}

