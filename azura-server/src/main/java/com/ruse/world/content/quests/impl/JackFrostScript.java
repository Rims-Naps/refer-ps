package com.ruse.world.content.quests.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class JackFrostScript implements CombatStrategy {

    public static final Position SAFE_1 = new Position(2516,4654);
    public static final Position SAFE_2 = new Position(2515,4654);
    public int npcId() {
        return 8517;
    }

    boolean trackAttackRunning = false;

    boolean safeSpotAttackRunning = false;


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

        NPC boss = (NPC) entity;

        Player player = (Player) victim;
        if (player.getBossFight() == null || !(player.getBossFight() instanceof JackFrostFight))
            return false;

        int currentStage = player.getJackFrostStage();
        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
        if (healthPercentage <= 50 && currentStage == 1) {
            player.getBossFight().setCurrentStage(2);
            boss.setTransformationId(8518);
            boss.getUpdateFlag().flag(Flag.TRANSFORM);
        }
        int random = Misc.random(1, 10);
        switch (currentStage) {
            case 1:
                if (random == 5) {
                    groundTrackingAttack(player, boss);
                }
                regularAttack(player, boss);
                break;
            case 2:
                if (random == 5) {
                    groundTrackingAttack(player, boss);
                }
                if (random == 6) {
                    oneHitKO(player, boss);
                }
                regularAttack(player, boss);
            break;
        }
        return true;
    }

    private void regularAttack(Player player, NPC boss) {
        if (trackAttackRunning)
            return;
        if (safeSpotAttackRunning)
            return;
        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        new Projectile(boss, player, 2119 , 75, 10, 110, 0, 0).sendProjectile();
        TaskManager.submit(new Task(3, player, false) {
            @Override
            public void execute() {
                player.dealDamage(new Hit(Misc.random(1, (player.getBossFight().getCurrentStage() == 2 ? 35 : 15)), Hitmask.WATER, CombatIcon.MAGIC));
                this.stop();
            }
        });
    }

    private void groundTrackingAttack(Player player, NPC boss) {

        if (trackAttackRunning)
            return;
        if (safeSpotAttackRunning)
            return;


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

        trackAttackRunning = true;
        for (int currentTileNumber = 0; n > 0; --n, ++currentTileNumber) {
            final int tileNumber = currentTileNumber;

            Position pos = new Position(x, y, player.getPosition().getZ());
            if (tileNumber <= maxTicks) {
                TaskManager.submit(new Task(ticksPerTile * tileNumber/ 2, false) {
                    public void execute() {
                        player.getPA().sendGraphic(new Graphic(570, 0, GraphicHeight.LOW), new Position(pos));
                        if (pos.equals(playerPosition)) {
                            if (player.getPosition().equals(playerPosition)) {
                                player.dealDamage(new Hit(Misc.random(75, 75), Hitmask.WATER, CombatIcon.MAGIC));
                            }
                        }
                        stop();
                        trackAttackRunning = false;

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

    private void oneHitKO(Player player, NPC boss) {
        if (trackAttackRunning)
            return;
        if (safeSpotAttackRunning)
            return;

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        new Projectile(boss, player, 2188, 75, 10, 110, 0, 0).sendProjectile();

        player.dealDamage(new Hit(Misc.random(1, 12), Hitmask.VOID, CombatIcon.MAGIC));
        safeSpotAttackRunning = true;

        player.getPA().sendGraphic(new Graphic(2128, 0, GraphicHeight.LOW),SAFE_1);
        player.getPA().sendGraphic(new Graphic(2128, 0, GraphicHeight.LOW),SAFE_2);

        TaskManager.submit(new Task(8, false) {
            @Override
            public void execute() {
                if (player.getPosition() != SAFE_1 || player.getPosition() != SAFE_2){
                    player.getPA().sendGraphic(new Graphic(68, 0, GraphicHeight.LOW),SAFE_1);
                    player.dealDamage(new Hit(Misc.random(120, 120), Hitmask.WATER, CombatIcon.MAGIC));
                }
                safeSpotAttackRunning = false;
                this.stop();
            }
        });
    }


    @Override
    public int attackDelay(Character entity) {
        return 4;
    }

    @Override
    public int attackDistance(Character entity) {
        return 15;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MAGIC;
    }
}
