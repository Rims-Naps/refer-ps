package com.ruse.model;

import com.ruse.world.entity.impl.player.Player;

public enum XpMode {

    EASY, MEDIUM, ELITE, MASTER;


    public static void set(Player player, XpMode newxpmode, boolean death) {
        if (!death && !player.getClickDelay().elapsed(1000))
            return;
        player.getClickDelay().reset();
        player.getPacketSender().sendInterfaceRemoval();
        player.setXpMode(newxpmode);
    }
}
