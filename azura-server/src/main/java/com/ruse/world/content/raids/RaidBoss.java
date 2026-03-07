package com.ruse.world.content.raids;

import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.forgottenRaids.data.RaidFightData;
import com.ruse.world.entity.impl.npc.NPC;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class RaidBoss {
    private final RaidFightData fightData = new RaidFightData(bossName());

    @Setter @Getter
    private NPC bossNpc;

    @Getter
    private final List<NPC> extras = new ArrayList<>();

    @Getter
    private final Raid connectedRaid;

    protected RaidBoss(Raid connectedRaid) {
        this.connectedRaid = connectedRaid;
    }

    public abstract int npcId();

    public abstract int health();

    public abstract Position position();

    public abstract Position afterDeathPosition();

    public abstract String bossName();
    public void spawn() {
        fightData.setStartTime(System.currentTimeMillis());
        bossNpc = new NPC(npcId(), position());
        int health = (int) ((double) health());
        bossNpc.setDefaultConstitution(health);
        bossNpc.setConstitution(health);
        World.register(bossNpc);
        bossNpc.setStrategy(combatStrategy());
    }

    public abstract CombatStrategy combatStrategy();

    public abstract void death(int npcId);

    public void dispose() {
        if (getBossNpc() != null && getBossNpc().isRegistered()) {
            World.deregister(getBossNpc());
            setBossNpc(null);
        }
        if (getExtras().size() > 0) {
            for (NPC npc : getExtras()) {
                if (npc != null && npc.isRegistered()) {
                    World.deregister(npc);
                }
            }
            getExtras().clear();
        }
    }

    public void submitDeath(String player) {
        if (!getConnectedRaid().getParty().getPlayers().contains(player))
            return;
        if (fightData.getDeaths().containsKey(player))
            fightData.getDeaths().put(player, fightData.getDeaths().get(player)+1);
        else fightData.getDeaths().put(player, 1);
    }
}
