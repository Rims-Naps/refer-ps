package com.ruse.world.content.AresTrials.impl;

import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.NPCRespawnTask;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.content.AresTrials.Boss;
import com.ruse.world.content.PrayerMinigame.PrayerMinigame;
import com.ruse.world.content.bossfights.BossFight;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class FirstBoss extends Boss {

    private static final long HEALTH = 7500;
    private int ENTRY_FEE = 100000;
    private int ENTRY_CURRENCY = 995;

    public FirstBoss(Player player) {
        super(player);
    }

    @Override
    public int npcId() {
        return 3170;
    }

    @Override
    public void begin() {
        if (!getPlayer().getInventory().contains(ENTRY_CURRENCY, ENTRY_FEE)) {
            endFight();
            return;
        }
        getPlayer().setAresTrialsInstanceActive(true);
        setGlobalHeight(getGlobalHeight() + 4);
        setMyHeight(getGlobalHeight());
        setCurrentNpc(new NPC(npcId(), new Position(1953, 5152, getMyHeight())));
        getCurrentNpc().setConstitution((int) HEALTH);
        getCurrentNpc().setDefaultConstitution((int) HEALTH);
        World.register(getCurrentNpc());
        getNpcList().add(getCurrentNpc());
        //Set Player Teleport Location
        TeleportHandler.teleportPlayer(getPlayer(), new Position(1955, 5150, getMyHeight()), TeleportType.NORMAL);
    }

    @Override
    public void onDeath(NPC npc) {
            endFight();
            getPlayer().setAresTrialsInstanceActive(false);
            getPlayer().moveTo(2651, 3990, 0);
            getPlayer().msgRed("You finished your trial.");
            getPlayer().setTrials2Unlocked(true);
            return;

    }


    @Override
    public void onPlayerDeath() {
        endFight();
    }

    @Override
    public void endFight() {
        for (NPC n : getNpcList()) {
            if (n == null)
                continue;
            if (n.isRegistered())
                World.deregister(n);
        }
        getNpcList().clear();
        setCurrentNpc(null);
        getPlayer().setBossFight(null);
    }

    @Override
    public CombatStrategy bossStrategy() {
        return new CombatStrategy() {
            @Override
            public boolean canAttack(com.ruse.world.entity.impl.Character entity, com.ruse.world.entity.impl.Character victim) {
                return true;
            }

            @Override
            public CombatContainer attack(com.ruse.world.entity.impl.Character entity, com.ruse.world.entity.impl.Character victim) {
                return null;
            }

            @Override
            public boolean customContainerAttack(com.ruse.world.entity.impl.Character entity, com.ruse.world.entity.impl.Character victim) {
                return true;
            }
            @Override
            public int attackDelay(com.ruse.world.entity.impl.Character entity) {
                return 8;//SET BOSS ATTACK SPEED
            }

            @Override
            public int attackDistance(Character entity) {
                return 20;//SET BOSS ATTACK DISTANCE
            }

            @Override
            public CombatType getCombatType() {
                return CombatType.MAGIC;
            }
        };
    }
}

