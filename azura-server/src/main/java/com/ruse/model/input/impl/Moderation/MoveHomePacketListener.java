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
import com.ruse.world.entity.impl.player.Player;

public class MoveHomePacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoMove) {

        String player2 = playertoMove;
        Player playerToMove = World.getPlayerByName(player2);
        if (playerToMove != null && playerToMove.getDueling().duelingStatus < 5) {
            if (playerToMove.getLocation() == Locations.Location.WILDERNESS) {
                PlayerLogs.log(player.getUsername(), "Just moved " + playerToMove.getUsername()
                        + " to home from level " + playerToMove.getWildernessLevel() + " wild.");
            } else {
                PlayerLogs.log(player.getUsername(), "Just moved " + playerToMove.getUsername() + " to home.");
            }
            playerToMove.moveTo(3167 + -Misc.random(0, 3), 3544 + -Misc.random(0, 3), 0);
            player.getPacketSender().sendMessage("Sucessfully moved " + playerToMove.getUsername() + " to home.");
            player.performGraphic(new Graphic(730));
            playerToMove.performGraphic(new Graphic(730));
        } else {
            player.getPacketSender().sendMessage("Could not send \"" + player2 + "\" home. Try kicking first?")
                    .sendMessage("Will also fail if they're in duel/wild.");
        }
    }
}
