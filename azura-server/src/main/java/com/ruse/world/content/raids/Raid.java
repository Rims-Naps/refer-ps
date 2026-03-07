package com.ruse.world.content.raids;

import com.ruse.model.Position;
import com.ruse.util.Stopwatch;
import com.ruse.world.content.parties.Party;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

public abstract class Raid {

    public RaidType type;

    public Raid(RaidType type) {
        this.type = type;
    }

    public Stopwatch timer = new Stopwatch();

    public Stopwatch wave_time = new Stopwatch();

    @Setter@Getter
    public Party party;

    @Getter@Setter
    public boolean in_party;

    @Getter@Setter
    Reward reward;

    public abstract void start(Player player);

    public abstract void proceed();

    public abstract void handleNextWave();

    public abstract void endRaid();

    public abstract void spawnNPCs();

    public abstract double getHitpointMulti();

    public abstract int getPartiesAverageDamage();

    public abstract void handleDeath(Player player);
    public abstract void openChest(Player player, Position position, NPC npc);

    @Getter@Setter
    public int killCounter;
}

