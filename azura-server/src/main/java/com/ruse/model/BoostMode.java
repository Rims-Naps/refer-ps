package com.ruse.model;

import com.ruse.world.entity.impl.player.Player;

public enum BoostMode {

    SINISTER, DIVINE;


    public static void set(Player player, BoostMode newBoostMode, boolean death) {
        if (!death && !player.getClickDelay().elapsed(1000))
            return;
        player.getClickDelay().reset();
        player.getPacketSender().sendInterfaceRemoval();
        player.setBoostMode(newBoostMode);
    }
}
