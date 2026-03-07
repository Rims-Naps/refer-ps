package com.ruse.world.content.timers.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.world.content.timers.EffectTimer;
import com.ruse.world.entity.impl.player.Player;


public class NauticTimer extends EffectTimer {

    Player player;
    public NauticTimer(Player player) {
        super(player,12424, "Nautic");
        this.player = player;
    }

    @Override
    public void addTime(int additionalSeconds) {
        super.addTime(additionalSeconds);
    }

    public void append() {
        player.getInventory().delete(12424,1);
        if (isActive()){
            player.sendMessage("Added 6 Hours of " + getName() + " effect!");
        } else {
            player.sendMessage("You've started 6 Hours of " + getName() + " effect!");
        }
        addTime(21600);
        player.getClickDelay().reset();
    }
}
