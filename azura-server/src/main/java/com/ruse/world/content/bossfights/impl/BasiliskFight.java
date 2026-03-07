package com.ruse.world.content.bossfights.impl;

import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.NPCRespawnTask;
import com.ruse.model.*;
import com.ruse.world.World;
import com.ruse.world.content.bossfights.BossFight;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class BasiliskFight extends BossFight {

    private static final long HEALTH = 75000;

    public BasiliskFight(Player player) {
        super(player);
    }



    @Override
    public int npcId() {
        return 2018;//SET NPC ID
    }

    @Override
    public void begin() {
        if (getPlayer().getSlayer().getSlayerTask().getNpcId() != npcId()) {
            endFight();
            return;
        }
        setGlobalHeight(getGlobalHeight() + 4);
        setMyHeight(getGlobalHeight());
        getPlayer().setBasiliskStage(1);
        //Set Location for Boss Npc
        setCurrentNpc(new NPC(npcId(), new Position(2656, 2981, getMyHeight())));
        getCurrentNpc().getMovementQueue().setLockMovement(true);
        getCurrentNpc().setConstitution((int) HEALTH);
        getCurrentNpc().setDefaultConstitution((int) HEALTH);
        World.register(getCurrentNpc());
        getNpcList().add(getCurrentNpc());
        //Set Player Teleport Location
        TeleportHandler.teleportPlayer(getPlayer(), new Position(2656, 2974, getMyHeight()), TeleportType.NORMAL);
    }



    @Override
    public void onDeath(NPC npc) {
        getPlayer().setBasiliskStage(1);
        if (getPlayer().getSlayer().getSlayerTask().getNpcId() != npc.getId()) {
            endFight();
            return;
        }

        if (getPlayer().getSlayer().getAmountToSlay() <= 0) {
            endFight();
            getPlayer().sendMessage("ENDING DUE TO TASK COMPLETION");
            TeleportHandler.teleportPlayer(getPlayer(), new Position(3167, 3530,0), TeleportType.ANCIENT);
            return;
        }
        TaskManager.submit(new NPCRespawnTask(npc, 10, getPlayer()));
        getPlayer().sendMessage("Respawning: " + getPlayer().getSlayer().getAmountToSlay());
    }


    @Override
    public void onPlayerDeath() {
        endFight();
    }

    @Override
    public void endFight() {
        getPlayer().setBasiliskStage(1);
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

            private void specialattack1(Player player, NPC boss) {
            }



            private void specialattack2(Player player, NPC boss) {

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
