package com.ruse.model.input.impl.Moderation;

import com.ruse.GameSettings;
import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.model.input.Input;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

import java.io.File;

public class UnJailPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoJail) {

        Player player2 = World.getPlayerByName(playertoJail);
        if (player2 != null) {
            if (PlayerPunishment.Jail.unJail(player2.getUsername())) {
                PlayerLogs.log(player.getUsername(),
                        "" + player.getUsername() + " just unjailed " + player2.getUsername() + "!");
                player.getPacketSender().sendMessage("Unjailed player: " + player2.getUsername() + "");
                player2.getPacketSender().sendMessage("You have been unjailed by " + player.getUsername() + ".");
                World.sendStaffMessage("<col=FF0066><img=2> [PUNISHMENTS]<col=6600FF> " + player.getUsername()
                        + " just unjailed " + player2.getUsername());
                player2.performAnimation(new Animation(1993));
                player.performGraphic(new Graphic(730));
            }
        } else {
            player.getPacketSender().sendMessage("Could not find \"" + playertoJail + " online.");
        }
    }
}
