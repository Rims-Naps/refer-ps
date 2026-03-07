package com.ruse.model.input.impl.Moderation;

import com.ruse.GameSettings;
import com.ruse.model.Graphic;
import com.ruse.model.Locations;
import com.ruse.model.input.Input;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;

public class TelePlayerToMePacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoMove) {

        String playerToTele = playertoMove;
        Player player2 = World.getPlayerByName(playerToTele);
        if (player2 == null) {
            player.getPacketSender().sendMessage("Cannot find that player..");
            return;
        } else {
            boolean canTele = TeleportHandler.checkReqs(player, player2.getPosition().copy());
            if (canTele && player2.getDueling().duelingStatus < 5) {
                player.getPacketSender().sendMessage("Moving player: " + player2.getUsername() + "");
                player2.moveTo(player.getPosition().copy());
                player2.performGraphic(new Graphic(342));
            } else
                player.getPacketSender()
                        .sendMessage("Failed to move player to your coords. Are you or them in a minigame?")
                        .sendMessage("Also will fail if they're in duel/wild.");
        }
    }
}
