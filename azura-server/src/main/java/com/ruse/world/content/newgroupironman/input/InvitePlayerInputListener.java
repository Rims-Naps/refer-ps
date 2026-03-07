package com.ruse.world.content.newgroupironman.input;

import com.ruse.model.input.Input;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class InvitePlayerInputListener extends Input {

    @Override
    public void handleSyntax(Player player, String username) {
        Player target = World.getPlayerByName(username);
        if (target == null) {
            player.sendMessage(username + " is offline");
            return;
        }

        player.getGroupIronmanGroup().invite(target);

    }
}
