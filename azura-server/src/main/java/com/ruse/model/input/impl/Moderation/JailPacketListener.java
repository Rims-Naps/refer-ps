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

public class JailPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoJail) {
        Player player2 = World.getPlayerByName(playertoJail);
        if (player2 != null) {
            if (PlayerPunishment.Jail.isJailed(player2.getUsername())) {
                player.getPacketSender().sendMessage("That player is already jailed!");
                return;
            }
            if (PlayerPunishment.Jail.jailPlayer(player2.getUsername()) && player2.getDueling().duelingStatus == 0) {
                player2.getSkillManager().stopSkilling();
                PlayerLogs.log(player.getUsername(), player.getUsername() + " just jailed " + player2.getUsername() + "!");
                player.getPacketSender().sendMessage("Jailed player: " + player2.getUsername());
                player2.getPacketSender().sendMessage("You have been jailed by " + player.getUsername() + ".");
                player2.performAnimation(new Animation(1994));
                player.performGraphic(new Graphic(730));
                player2.moveTo(new Position(2507, 9324));
                player2.setLocation(Locations.Location.JAIL);
            } else {
                if (player2.getDueling().duelingStatus > 0) {
                    player.getPacketSender().sendMessage("That player is currently in a stake. Please wait before jailing them, or kick then jail.");
                    return;
                } else {
                    player.getPacketSender().sendMessage("Error: JAIL45");
                }
            }
        } else {
            String target = playertoJail;
            String fileName = Misc.formatText(target.toLowerCase());
            File file = new File("./data/saves/characters/" + fileName + ".json");
            if (file.exists()) {
                if (PlayerPunishment.Jail.isJailed(target)) {
                    player.getPacketSender().sendMessage("Player: " + target + " is already jailed.");
                    return;
                }
                if (PlayerPunishment.Jail.jailPlayer(target)) {
                    PlayerLogs.log(player.getUsername(), player.getUsername() + " just jailed " + target + " (offline)!");
                    World.sendStaffMessage("<col=FF0066><img=2> [PUNISHMENTS]<col=6600FF> " + player.getUsername() + " just jailed " + target + " (offline).");
                    player.getPacketSender().sendMessage("Player " + target + " was successfully jailed (offline).");
                } else {
                    player.getPacketSender().sendMessage("Failed to jail offline player: " + target);
                }
            } else {
                player.getPacketSender().sendMessage(fileName + " does not exist in my files, maybe you typed it wrong!");
            }
        }
    }
}