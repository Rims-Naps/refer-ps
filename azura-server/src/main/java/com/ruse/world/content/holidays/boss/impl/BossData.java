package com.ruse.world.content.holidays.boss.impl;

import com.ruse.model.Position;
import com.ruse.world.content.holidays.boss.HolidayBoss;

public class BossData extends HolidayBoss {
    @Override
    public int health() {
        return 2_000_000;
    }

    @Override
    public int npcId() {
        return 1789;
    }

    @Override
    public Position position() {
        return new Position(2528, 3807, 4);
    }

    @Override
    public void handleDeath() {

    }
}
