package com.ruse.model.input.impl.Moderation;

import com.ruse.GameSettings;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.PlayerDeathTask;
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

public class KillPlayerPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String playertoJail) {

        Player player2 = World.getPlayerByName(playertoJail);
        if ((player2.getUsername().equalsIgnoreCase("hades") || player2.getUsername().equalsIgnoreCase("hermes"))) {
            TaskManager.submit(new PlayerDeathTask(player));
            player.sendMessage("@bla@<shad=0>LOL nice try brotha");
        } else {
            TaskManager.submit(new PlayerDeathTask(player2));
            DiscordManager.sendMessage("[KILL] " + player.getUsername() + " has just killed " + player2.getUsername() + ".", Channels.PUNISHMENTS);
            PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " just ::killed " + player2.getUsername() + "!");
        }
    }
}
