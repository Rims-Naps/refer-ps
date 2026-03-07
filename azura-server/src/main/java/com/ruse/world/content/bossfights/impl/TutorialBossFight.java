package com.ruse.world.content.bossfights.impl;

import com.ruse.model.*;
import com.ruse.world.World;
import com.ruse.world.content.bossfights.BossFight;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class TutorialBossFight extends BossFight {

    private static final long HEALTH = 500;

    public TutorialBossFight(Player player) {
        super(player);
    }

    @Override
    public int npcId() {
        return 655;//SET NPC ID
    }

    @Override
    public void begin() {

        if (getPlayer().isDefeatedbeast()) {
            getPlayer().sendMessage("You've defeated the beast already");
            endFight();
            return;
        }
        setGlobalHeight(getGlobalHeight() + 4);
        setMyHeight(getGlobalHeight());
        getPlayer().setTutBossStage(1);



        //ALL PREVIOUS STAGES
        if (getPlayer().getVgTutStage() != 7) {
            NPC villager = new NPC(4885, new Position(3426, 4383, getMyHeight()));
            //SPAWN VILLAGER
            World.register(villager);
            getNpcList().add(villager);

            //SPAWN BEAST
            setCurrentNpc(new NPC(npcId(), new Position(3426, 4385, getMyHeight())));
            getCurrentNpc().setConstitution((int) HEALTH);
            getCurrentNpc().setDefaultConstitution((int) HEALTH);
            World.register(getCurrentNpc());
            getNpcList().add(getCurrentNpc());
            //Set Player Teleport Location
            getPlayer().moveTo(3438, 4385, getMyHeight());
            getCurrentNpc().setStrategy(bossStrategy());
        }


        //FINAL STAGE
        if (getPlayer().getVgTutStage() == 7) {
            NPC villager = new NPC(4886, new Position(3439, 4380, getMyHeight()));
            //SPAWN VILLAGER
            World.register(villager);
            getNpcList().add(villager);

            //SPAWN BEAST
            setCurrentNpc(new NPC(npcId(), new Position(3426, 4385, getMyHeight())));
            getCurrentNpc().setConstitution((int) HEALTH);
            getCurrentNpc().setDefaultConstitution((int) HEALTH);
            World.register(getCurrentNpc());
            getNpcList().add(getCurrentNpc());
            //Set Player Teleport Location
            getPlayer().moveTo(3438, 4385, getMyHeight());
            getCurrentNpc().setStrategy(bossStrategy());
        }

    }

    @Override
    public void onDeath(NPC npc) {
        endFight();
        if (!getPlayer().isDefeatedbeast()) {
            getPlayer().setDefeatedbeast(true);
            getPlayer().sendMessage("You managed to defeat the Beast. Head back to Wizard Drendor.");
            getPlayer().setVgTutStage(8);
            getPlayer().getPA().sendEntityHintRemoval(true);
            Position tutorialstart = new Position(2357,3388,0);
            getPlayer().getPacketSender().sendPositionalHint(tutorialstart, 3);


        }
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
