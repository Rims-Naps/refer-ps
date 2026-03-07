package com.ruse.world.content.raids.invocation.impl;

import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.entity.impl.player.Player;

public class TowerInvocations {
    public static void openInterface(Player player) {
        int counter = 88005;
        for (Invocations.TowerInvocations tower : Invocations.TowerInvocations.values()) {
            if (player.getInvocations().is_invocation_unlocked(tower)) {
                player.getPacketSender().sendString(counter, "false");
            } else {
                player.getPacketSender().sendString(counter, "true");
            }
            counter++;
        }
        player.getInvocations().sendNumbers();
        player.getPacketSender().sendInterface(88000);
    }
}
