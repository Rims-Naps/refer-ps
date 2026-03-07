package com.ruse.world.content.groupslayer.input;

import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class JoinGroupSlayerPartyInputHandler extends Input {

    public void handleSyntax(Player player, String playerName) {
        if(player.getGroupSlayerParty() != null) {
            player.sendMessage("You are already in a group slayer party");
            return;
        }
        Player target = World.getPlayerByName(playerName);
        if (target == null) {
            player.sendMessage(playerName + " is offline");
            return;
        }

        if (target.getGroupSlayerParty() == null) {
            player.sendMessage(playerName + " does not have a group slayer party setup");
            return;
        }

        target.getGroupSlayerParty().join(player);
    }
}
