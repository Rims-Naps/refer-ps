package com.ruse.world.content.timers.impl;

import com.ruse.world.content.timers.EffectTimer;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;


public class DivineTimer extends EffectTimer {
    Player player;
    public DivineTimer(Player player) {
        super(player,358, "Divine");
        this.player = player;
    }

    @Override
    public void addTime(int additionalSeconds) {
        super.addTime(additionalSeconds);
    }

    public void append() {
        int timeToAdd = 300;
        if (player.getNodesUnlocked() != null) {
            if (player.getSkillTree().isNodeUnlocked(Node.HERBALIST)) {
                timeToAdd = 360;
            }
        }
        if (isActive() && getTimeLeft() + timeToAdd > 1500) {
            player.getPacketSender().sendMessage("You can only have up to 25 minutes of " + getName() + " active");
            return;
        }
        player.getInventory().delete(getItemId(),1);
        if (isActive()){
            player.msgFancyPurp("Added " + (timeToAdd / 60) + " Minutes of " + getName() + " effect!");
        } else {
            player.msgFancyPurp("You've started " + (timeToAdd / 60) + " Minutes of " + getName() + " effect!");
        }
        addTime(timeToAdd);
        player.getClickDelay().reset();
    }
}
