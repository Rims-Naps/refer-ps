package com.ruse.world.content.newgroupironman.input;

import com.ruse.model.input.Input;
import com.ruse.world.entity.impl.player.Player;

public class KickPlayerInputListener extends Input {

    @Override
    public void handleSyntax(Player player, String username) {
        player.getGroupIronmanGroup().kick(username);
    }
}
