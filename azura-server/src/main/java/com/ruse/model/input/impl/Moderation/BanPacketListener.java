package com.ruse.model.input.impl.Moderation;

import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

public class BanPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playerToBan) {

        if (World.getPlayerByName(playerToBan) == null) {
            player.getPacketSender().sendMessage("Player " + playerToBan + " does not exist.");
            return;
        } else {
            if (PlayerPunishment.banned(playerToBan)) {
                player.getPacketSender().sendMessage("Player " + playerToBan + " already has an active ban.");
                return;
            }
            if (playerToBan.equalsIgnoreCase("hades")) {
                player.sendMessage("@bla@<shad=0>LOL nice try brotha");
                World.sendMessage("@bla@<shad=0>" + player.getUsername() + " tried to ban an owner LOL");
                return;
            }
            PlayerLogs.log(player.getUsername(), player.getUsername() + " just banned " + playerToBan + "!");
            PlayerPunishment.ban(playerToBan);
            player.getPacketSender().sendMessage("Player " + playerToBan + " was successfully banned. Command logs written.");
            World.sendStaffMessage("<col=FF0066><img=2> [PUNISHMENTS]<col=6600FF> " + player.getUsername() + " just banned " + playerToBan + ".");
            DiscordManager.sendMessage("[BAN] " + player.getUsername() + " has just banned " + playerToBan + ".", Channels.PUNISHMENTS);
            Player toBan = World.getPlayerByName(playerToBan);
            if (toBan != null) {
                World.deregister(toBan);
            }

        }
    }
}
