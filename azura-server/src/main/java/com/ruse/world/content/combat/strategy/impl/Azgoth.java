package com.ruse.world.content.combat.strategy.impl;
import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class Azgoth implements CombatStrategy {

    public int npcId() {
        return 8000;
    }

    public void regularAttack(Player player, NPC megaman) {
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC boss = (NPC) entity;
        Player p = (Player) victim;

        boss.performAnimation(new Animation(7769));

        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);

        for(Player otherVictims : World.getNearbyPlayers(boss.getPosition(), 25)) {
            new Projectile(boss, otherVictims, 335, 75, 10, 110, 0, 0).sendProjectile();
            boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));

            TaskManager.submit(new Task(3, otherVictims, false) {
                @Override
                public void execute() {
                    if (!otherVictims.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                        otherVictims.dealDamage(new Hit(Misc.random(5, 15), Hitmask.EARTH, CombatIcon.RANGED));
                    }
                    if (otherVictims.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                        otherVictims.dealDamage(new Hit(Misc.random(0), Hitmask.EARTH, CombatIcon.BLOCK));
                    }
                    handlestages(boss, otherVictims, healthPercentage);
                    this.stop();
                }
            });
        }

        return true;
    }

    private void sendStage(NPC boss, int execStage) {

        switch (execStage) {
            case 0:
                GameSettings.azgothstage = 3;

                for (Player victims : World.getNearbyPlayers(boss.getPosition(), 20)) {

                    boss.performGraphic(new Graphic(6));
                    victims.performGraphic(new Graphic(6));
                    // boss.forceChat("STAGE 0");
                    TaskManager.submit(new Task(2, boss, false) {
                        @Override
                        protected void execute() {
                            boss.setTransformationId(8002);
                            boss.getUpdateFlag().flag(Flag.TRANSFORM);
                            boss.setForceAggressive(true);
                            stop();
                        }

                    });
                }
                break;
            case 1:
                GameSettings.azgothstage = 1;
                for (Player victims : World.getNearbyPlayers(boss.getPosition(), 20)) {

                    boss.performGraphic(new Graphic(606));
                    victims.performGraphic(new Graphic(606));
                    //  boss.forceChat("STAGE 1");

                    TaskManager.submit(new Task(2, boss, false) {
                        @Override
                        protected void execute() {
                            boss.setTransformationId(8004);
                            boss.getUpdateFlag().flag(Flag.TRANSFORM);
                            boss.setForceAggressive(true);
                            stop();
                        }
                    });
                }
                break;
            case 2:
                GameSettings.azgothstage = 2;

                for (Player victims : World.getNearbyPlayers(boss.getPosition(), 20)) {

                    boss.performGraphic(new Graphic(482));
                    victims.performGraphic(new Graphic(482));
                    //   boss.forceChat("STAGE 2");

                    TaskManager.submit(new Task(2, boss, false) {
                        @Override
                        protected void execute() {
                            boss.setTransformationId(8000);
                            boss.getUpdateFlag().flag(Flag.TRANSFORM);
                            boss.setForceAggressive(true);
                            stop();
                        }
                    });
                }
                break;
        }
    }

    private void handlestages(NPC boss, Player p, int healthPercentage) {
        if (boss.getConstitution() >= 80 && GameSettings.azgothstage == 0) {
            sendStage(boss,1);
        } else if (healthPercentage <= 50 && GameSettings.azgothstage == 1) {
            sendStage(boss, 2);
        } else if (healthPercentage <= 25 && GameSettings.azgothstage == 2) {
            sendStage(boss, 0);
        }
    }

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return true;
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override
    public int attackDelay(Character entity) {
        return 5;
    }

    @Override
    public int attackDistance(Character entity) {
        return 50;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}