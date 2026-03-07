package com.ruse.world.content.holidays.boss;

import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import lombok.Getter;

@Getter
public abstract class HolidayBoss {

    private NPC bossNpc;

    public void spawn() {
        bossNpc = new NPC(npcId(), position());
        bossNpc.setDefaultConstitution(health());
        bossNpc.setConstitution(health());
        World.register(bossNpc);
    }

    public abstract int health();

    public abstract int npcId();

    public abstract Position position();

    public abstract void handleDeath();

}
