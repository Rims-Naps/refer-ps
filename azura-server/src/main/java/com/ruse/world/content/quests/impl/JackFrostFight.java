package com.ruse.world.content.quests.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.NPCRespawnTask;
import com.ruse.model.GameObject;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.content.CustomObjects;
import com.ruse.world.content.bossfights.BossFight;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class JackFrostFight extends BossFight {
    private static final long HEALTH = 50000;

    public JackFrostFight(Player player) {
        super(player);
    }



    @Override
    public int npcId() {
        return 8517;//SET NPC ID
    }

    @Override
    public void begin() {
        setGlobalHeight(getGlobalHeight() + 4);
        setMyHeight(getGlobalHeight());
        getPlayer().setJackFrostStage(1);
        //Set Location for Boss Npc
        setCurrentNpc(new NPC(npcId(), new Position(2517, 4642, getMyHeight())));
        getCurrentNpc().setConstitution((int) HEALTH);
        getCurrentNpc().setDefaultConstitution((int) HEALTH);
        World.register(getCurrentNpc());
        getNpcList().add(getCurrentNpc());
        //Set Player Teleport Location
        TeleportHandler.teleportPlayer(getPlayer(), new Position(2518, 4659, getMyHeight()), TeleportType.NORMAL);
    }



    @Override
    public void onDeath(NPC npc) {
        getPlayer().getInventory().add(1468, 1);
        getPlayer().sendMessage("You rip the cold dead heart of Jack Frost, out of his cold chest!");
        getPlayer().setChristmasQuest2023Stage(5);
        endFight();
    }


    @Override
    public void onPlayerDeath() {
        endFight();
    }

    @Override
    public void endFight() {
        getPlayer().setJackFrostStage(0);
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
