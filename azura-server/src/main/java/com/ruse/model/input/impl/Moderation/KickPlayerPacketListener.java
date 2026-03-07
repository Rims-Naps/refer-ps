package com.ruse.model.input.impl.Moderation;

import com.ruse.GameSettings;
import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

public class KickPlayerPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoKick) {

        String player2 = playertoKick;
        Player playerToKick = World.getPlayerByName(player2);
        if (playerToKick == null) {
            player.getPacketSender().sendMessage("Player " + player2 + " couldn't be found on " + GameSettings.RSPS_NAME + ".");
            return;
        } else if (playerToKick.getDueling().duelingStatus < 5) {
            World.getPlayers().remove(playerToKick);
            player.getPacketSender().sendMessage("Kicked " + playerToKick.getUsername() + ".");
            PlayerLogs.log(player.getUsername(),
                    player.getUsername() + " just kicked " + playerToKick.getUsername() + "!");
        } else {
            PlayerLogs.log(player.getUsername(), player.getUsername() + " just tried to kick "
                    + playerToKick.getUsername() + " in an active duel.");
            player.getPacketSender().sendMessage("You have tried to kick someone in duel arena/wild. Logs written.");
        }
    }
}

