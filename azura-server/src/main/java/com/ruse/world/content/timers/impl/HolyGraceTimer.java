package com.ruse.world.content.timers.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.world.content.timers.EffectTimer;
import com.ruse.world.entity.impl.player.Player;


public class HolyGraceTimer extends EffectTimer {

    Player player;
    public HolyGraceTimer(Player player) {
        super(player,15901, "Holy Grace");
        this.player = player;
    }

    @Override
    public void addTime(int additionalSeconds) {
        super.addTime(additionalSeconds);
    }

    public void append() {
        player.getInventory().delete(getItemId(),1);
        if (isActive()){
            player.sendMessage("Added 1 Minute of " + getName() + " effect!");
        } else {
            player.sendMessage("You've started 1 Minute of " + getName() + " effect!");
        }
        addTime(60);
        player.getClickDelay().reset();
    }
}
