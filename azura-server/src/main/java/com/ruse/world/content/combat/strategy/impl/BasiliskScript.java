package com.ruse.world.content.combat.strategy.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.content.bossfights.impl.BasiliskFight;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class BasiliskScript implements CombatStrategy {
    public int npcId() {
        return 2018;
    }
    boolean trackAttackRunning = false;
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

       // System.out.println("Attempting Attack");

        NPC boss = (NPC) entity;
        Player player = (Player) victim;
        if (player.getBossFight() == null || !(player.getBossFight() instanceof BasiliskFight)) {
           // System.out.println("Boss Fight was null for some reason so we stopped");
            return false;
        }

        int currentStage = player.getBasiliskStage();
        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
        if (healthPercentage <= 75 && currentStage == 1) {
            player.getBossFight().setCurrentStage(2);
            currentStage = 2;
        } else if (healthPercentage <= 50 && currentStage == 2) {
            player.getBossFight().setCurrentStage(3);
            currentStage = 3;
        }

        int random = Misc.random(0, 3);
       // System.out.println("Reached Stage Check");
        switch (currentStage) {

            case 1:
                if (random == 0) {
                    groundTrackingAttack(player, boss);
                } else if (random == 1 || random == 2 || random == 3) {
                    regularAttack(player, boss);
                }
                break;
            case 2:
                if (random == 0) {
                    groundTrackingAttack(player, boss);
                } else if (random == 1 || random == 2 || random == 3) {
                    regularAttack(player, boss);
                }
                break;
            case 3:
                if (random == 0|| random == 1) {
                    groundTrackingAttack(player, boss);
                } else if (random == 2 || random == 3) {
                    regularAttack(player, boss);
                }
                break;
        }
        return true;
    }


    private void groundTrackingAttack(Player player, NPC boss) {

        if (trackAttackRunning) {
            trackAttackRunning = false;
        }

        final int ticksPerTile = 1;
        final int maxTicks = 20;
        final Position playerPosition = new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        int startX = boss.getPosition().getX();
        int startY = boss.getPosition().getY();
        int endX = playerPosition.getX();
        int endY = playerPosition.getY();
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        final int finalX = startX;
        final int finalY = startY;
        int x = finalX;
        int y = finalY;
        int n = 1 + dx + dy;
        int x_inc = (endX > startX) ? 1 : -1;
        int y_inc = (endY > startY) ? 1 : -1;
        int error = dx - dy;
        dx *= 2;
        dy *= 2;

      //  System.out.println("Sending Track Attack");

        boss.forceChat("SPLISH SPLASH!");
        player.msgRed("SPLISH SPLASH!");


        trackAttackRunning = true;
        for (int currentTileNumber = 0; n > 0; --n, ++currentTileNumber) {
            final int tileNumber = currentTileNumber;

            Position pos = new Position(x, y, player.getPosition().getZ());
            if (tileNumber <= maxTicks) {
                TaskManager.submit(new Task(ticksPerTile * tileNumber/ 2, false) {
                    public void execute() {
                        player.getPA().sendGraphic(new Graphic(1026, 0, GraphicHeight.LOW), new Position(pos));
                        if (pos.equals(playerPosition)) {
                            if (player.getPosition().equals(playerPosition)) {
                                player.dealDamage(new Hit(Misc.random(1200, 1200), Hitmask.WATER, CombatIcon.MAGIC));
                            }
                        }
                        stop();
                        trackAttackRunning = false;
                     //   System.out.println("Ending Track Attack");

                    }
                });
            }
            if (error > 0) {
                x += x_inc;
                error -= dy;
            } else {
                y += y_inc;
                error += dx;
            }
        }
    }


    private void regularAttack(Player player, NPC boss) {


      //  System.out.println("Sending Prayer Flick Task");
        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        new Projectile(boss, player, 500 , 75, 10, 110, 0, 0).sendProjectile();
        TaskManager.submit(new Task(3, player, false) {
            @Override
            public void execute() {
                if (!player.getCurseActive()[CurseHandler.EBBFLOW] && !player.getCurseActive()[CurseHandler.SWIFTTIDE]) {
                    player.dealDamage(new Hit(Misc.random(100, 120), Hitmask.WATER, CombatIcon.MAGIC));
                    player.sendMessage("<shad=0>@red@You failed to block Basilisk's Water attack");
                    player.msgRed("Use Ebb & Flow or Swifttide to avoid massive damage!");
                }
                if (player.getCurseActive()[CurseHandler.EBBFLOW] || player.getCurseActive()[CurseHandler.SWIFTTIDE]) {
                    player.dealDamage(new Hit(Misc.random(0), Hitmask.WATER, CombatIcon.BLOCK));
                    player.sendMessage("<shad=0>@gre@You successfully blocked Basilisk's Water attack");
                }
                this.stop();
            }
        });
    }

    @Override
    public int attackDelay(Character entity) {
        return 6;
    }

    @Override
    public int attackDistance(Character entity) {
        return 30;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MAGIC;
    }
}
