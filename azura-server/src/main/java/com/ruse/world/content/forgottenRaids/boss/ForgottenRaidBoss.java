package com.ruse.world.content.forgottenRaids.boss;

import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatBuilder;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.data.RaidFightData;
import com.ruse.world.entity.impl.npc.NPC;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public abstract class ForgottenRaidBoss {

    private final RaidFightData fightData = new RaidFightData(bossName());

    @Setter
    private NPC bossNpc;

    private final List<NPC> extras = new ArrayList<>();

    private final ForgottenRaid connectedRaid;

    public abstract int npcId();

    public abstract int health();

    public abstract Position position();

    public abstract Position afterDeathPosition();

    public abstract String bossName();
    public void spawn() {
        fightData.setStartTime(System.currentTimeMillis());
        bossNpc = new NPC(npcId(), position());
        double multiplier = 1 + (getConnectedRaid().getParty().getPlayers().size()*0.25);
        multiplier *= connectedRaid.getDifficulty().getHealthMultiplier();
        int health = (int) ((double) health() * multiplier);
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
