package com.ruse.model.input.impl.Moderation;

import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.entity.impl.player.Player;

public class UnmutePacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playerToUnmute) {

        if (playerToUnmute == null) {
            player.getPacketSender().sendMessage("Player " + playerToUnmute + " does not exist.");
            return;
        } else {
            if (!PlayerPunishment.muted(playerToUnmute)) {
                player.getPacketSender().sendMessage("Player " + playerToUnmute + " is not muted!");
                return;
            }
            PlayerLogs.log(player.getUsername(), player.getUsername() + " just unmuted " + playerToUnmute + "!");
            World.sendStaffMessage("<col=FF0066><img=2> [PUNISHMENTS]<col=6600FF> " + player.getUsername()
                    + " just unmuted " + playerToUnmute);
            PlayerPunishment.unmute(playerToUnmute);
            player.getPacketSender()
                    .sendMessage("Player " + playerToUnmute + " was successfully unmuted. Command logs written.");
            Player plr = World.getPlayerByName(playerToUnmute);
            if (plr != null) {
                plr.getPacketSender().sendMessage("You have been unmuted by " + player.getUsername() + ".");
            }
        }
    }
}
