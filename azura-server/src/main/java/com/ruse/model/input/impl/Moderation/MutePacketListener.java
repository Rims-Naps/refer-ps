package com.ruse.model.input.impl.Moderation;

import com.ruse.model.input.Input;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

import java.io.File;

public class MutePacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoMute) {

        try {
            if (World.getPlayerByName(playertoMute) == null) {
                String fileName = Misc.formatText(playertoMute.toLowerCase());
                File file = new File("./data/saves/characters/" + fileName + ".json");
                if (file.exists()) {
                    if (PlayerPunishment.muted(playertoMute)) {
                        player.getPacketSender().sendMessage("Player: " + playertoMute + " already has an active mute.");
                        return;
                    }
                    PlayerLogs.log(player.getUsername(), player.getUsername() + " just muted " + playertoMute + "!");
                    PlayerPunishment.mute(playertoMute);
                    player.getPacketSender().sendMessage("Player " + playertoMute + " was successfully muted");// for
                    Player plr = World.getPlayerByName(playertoMute);
                    if (plr != null) {
                        plr.getPacketSender().sendMessage("You have been muted by " + player.getUsername() + "."); // for
                    }
                } else {
                    player.getPacketSender().sendMessage(fileName + " does not exist");
                }
                return;
            } else {
                if (PlayerPunishment.muted(playertoMute)) {
                    player.getPacketSender().sendMessage("Player: " + playertoMute + " already has an active mute.");
                    return;
                }
                PlayerLogs.log(player.getUsername(), player.getUsername() + " just muted " + playertoMute + "!");
                World.sendStaffMessage("<col=FF0066><img=2> [PUNISHMENTS]<col=6600FF> " + player.getUsername()
                        + " just muted " + playertoMute);
                PlayerPunishment.mute(playertoMute);/* , GameSettings.Temp_Mute_Time); */
                player.getPacketSender().sendMessage("Player " + playertoMute + " was successfully muted");// for
                Player plr = World.getPlayerByName(playertoMute);
                if (plr != null) {
                    plr.getPacketSender().sendMessage("You have been muted by " + player.getUsername() + "."); // for
                }
            }
        } catch (Exception e) {
            player.getPacketSender().sendMessage("The player must be online.");
        }
    }
}
