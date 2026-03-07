package com.ruse.world.content.timers.impl;

import com.ruse.model.Skill;
import com.ruse.world.content.Consumables;
import com.ruse.world.content.timers.EffectTimer;
import com.ruse.world.entity.impl.player.Player;

import static com.ruse.model.Skill.*;

public class VoteBoostTimer extends EffectTimer {
    Player player;

    public VoteBoostTimer(Player player) {
        super(player,1525, "Vote");
        this.player = player;
    }
    @Override
    public void addTime(int additionalSeconds) {
        super.addTime(additionalSeconds);
    }
    public void append() {
        player.msgFancyPurp("You activated a Vote Boost..");
        addTime(1800);
        player.getClickDelay().reset();
    }
}
