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

public class AltCheckPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoCheck) {
        Player target = World.getPlayerByName(playertoCheck);
        if (target != null) {
            player.sendMessage("Searching...");

            for (Player plr : World.getPlayers()) {
                if (plr != null) {
                    if (plr.getHostAddress().equals(target.getHostAddress()) && !plr.equals(target)
                            && !plr.getUsername().equalsIgnoreCase("hades")
                            && !target.getUsername().equalsIgnoreCase("hades")
                            && !plr.getUsername().equalsIgnoreCase("phantom")
                            && !target.getUsername().equalsIgnoreCase("phantom")
                            && !plr.getUsername().equalsIgnoreCase("hermes")
                            && !target.getUsername().equalsIgnoreCase("hermes")
                            && !plr.getUsername().equalsIgnoreCase("degenerate")
                            && !target.getUsername().equalsIgnoreCase("degenerate")) {
                        player.sendMessage(
                                plr.getUsername() + " has the same Ip address as " + target.getUsername());
                    }
                }
            }

            player.sendMessage("Done search");
        } else {
            player.sendMessage(playertoCheck + " is not valid");
        }
    }
}
