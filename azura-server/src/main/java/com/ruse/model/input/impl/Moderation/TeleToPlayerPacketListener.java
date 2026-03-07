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

public class TeleToPlayerPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoMove) {

        String playerToTele = playertoMove;
        Player player2 = World.getPlayerByName(playerToTele);
        if (player2 == null) {
            player.getPacketSender().sendMessage("Cannot find that player online..");
            return;
        } else {
            boolean canTele = TeleportHandler.checkReqs(player, player2.getPosition().copy());
            if (canTele) {
                if (player.isVisible())
                    player2.performGraphic(new Graphic(730));

                TeleportHandler.teleportPlayer(player, player2.getPosition().copy(), TeleportType.NORMAL);
                player.getPacketSender().sendMessage("Teleporting to player: " + player2.getUsername() + "");
            } else
                player.getPacketSender()
                        .sendMessage("You can not teleport to this player at the moment. Minigame maybe?");
        }
    }
}
