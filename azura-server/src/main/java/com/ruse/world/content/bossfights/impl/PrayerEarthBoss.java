package com.ruse.world.content.bossfights.impl;

import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.NPCRespawnTask;
import com.ruse.model.*;
import com.ruse.world.World;
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

public class PrayerEarthBoss extends BossFight {

    private static final long HEALTH = 7500;

    public PrayerEarthBoss(Player player) {
        super(player);
    }

    @Override
    public int npcId() {
        return 3169;//SET NPC ID
    }

    @Override
    public void begin() {
        if (getPlayer().getPrayerMinigameBossKillsLeft() <= 0) {
            endFight();
            return;
        }
        setGlobalHeight(getGlobalHeight() + 4);
        setMyHeight(getGlobalHeight());
        //Set Location for Boss Npc
        setCurrentNpc(new NPC(npcId(), new Position(1952, 5024, getMyHeight())));
        getCurrentNpc().setConstitution((int) HEALTH);
        getCurrentNpc().setDefaultConstitution((int) HEALTH);
        World.register(getCurrentNpc());
        getNpcList().add(getCurrentNpc());
        //Set Player Teleport Location
        TeleportHandler.teleportPlayer(getPlayer(), new Position(1959, 5025, getMyHeight()), TeleportType.NORMAL);
    }

    @Override
    public void onDeath(NPC npc) {

        if (getPlayer().getPrayerMinigameBossKillsLeft() <= 1) {
            endFight();
            getPlayer().setPrayerMinigameBossInstanceActive(false);
            PrayerMinigame.handleBossKill(getPlayer());
            PrayerMinigame.resetRequiredMinionKills(getPlayer());
            getPlayer().moveTo(2651, 3990, 0);
            getPlayer().msgRed("You finished your remaining Boss kills.");
            return;
        }
        getPlayer().setPrayerMinigameBossKillsLeft(getPlayer().getPrayerMinigameBossKillsLeft() - 1);
        TaskManager.submit(new NPCRespawnTask(npc, 8, getPlayer()));
        //getPlayer().sendMessage("Respawning: " + getPlayer().getPrayerMinigameBossKillsLeft());
        PrayerMinigame.handleBossKill(getPlayer());
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
            public boolean canAttack(Character entity, Character victim) {
                return true;
            }

            @Override
            public CombatContainer attack(Character entity, Character victim) {
                return null;
            }

            @Override
            public boolean customContainerAttack(Character entity, Character victim) {
                return true;
            }
            @Override
            public int attackDelay(Character entity) {
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
