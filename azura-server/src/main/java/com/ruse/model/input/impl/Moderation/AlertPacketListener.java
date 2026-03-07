package com.ruse.model.input.impl.Moderation;

import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

public class AlertPacketListener extends Input {
    @Override
    public void handleSyntax(Player player, String alertMsg) {
        // Send the alert message to the Discord channel and the game world
        DiscordManager.sendMessage("[ALERT] " + player.getUsername() + " has just sent an alert: " + alertMsg + ".", Channels.ADMIN_COMMANDS);
        World.sendMessage("NewAlert##ALERT##" + alertMsg + "##By: " + player.getUsername());
    }
}
