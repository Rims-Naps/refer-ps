package com.ruse.world.content.timers.impl;

import com.ruse.world.content.timers.EffectTimer;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;


public class RaidBoostTimer extends EffectTimer {
    Player player;
    public RaidBoostTimer(Player player) {
        super(player,2092, "Raid");
        this.player = player;
    }

    @Override
    public void addTime(int additionalSeconds) {
        super.addTime(additionalSeconds);
    }
    private static int minutes = 60;

    public void append() {
        int timeToAdd = 60 * minutes;

        if (isActive() && getTimeLeft() + timeToAdd > 3600) {
            player.getPacketSender().sendMessage("You can only have up to 1 hour of " + getName() + " active");
            return;
        }
        player.getInventory().delete(getItemId(),1);
        if (isActive()){
            player.msgFancyPurp("Added " + (timeToAdd) + " Minutes of " + getName() + " effect!");
        } else {
            player.msgFancyPurp("You've started " + (timeToAdd) + " Minutes of " + getName() + " effect!");
        }
        addTime(timeToAdd);
        player.getClickDelay().reset();
    }
}
