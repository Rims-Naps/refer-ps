package com.ruse.world.content.timers.impl;

import com.ruse.world.content.timers.EffectTimer;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;


public class NoobTimer extends EffectTimer {
    Player player;
    public NoobTimer(Player player) {
        super(player,6805, "Noob");
        this.player = player;
    }

    @Override
    public void addTime(int additionalSeconds) {
        super.addTime(additionalSeconds);
    }

    public void append() {
        if (isActive()) {
            return;
        }

        player.msgFancyPurp("You have been granted with 10 hours of Apprentice Boost.");
        player.msgFancyPurp("50% Maxhit, 5% Droprate, 5% Crit");

        addTime(36000);
        player.getClickDelay().reset();
    }
}
