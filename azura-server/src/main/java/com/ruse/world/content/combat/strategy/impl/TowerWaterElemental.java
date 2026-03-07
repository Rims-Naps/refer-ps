package com.ruse.world.content.combat.strategy.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TowerWaterElemental implements CombatStrategy {

    public int npcId() {
        return 2022;
    }

    private static final Animation animRain = new Animation(7834);
    private static final Graphic gfxSafe = new Graphic(2128, 7, GraphicHeight.LOW);
    private static final Graphic gfxDanger = new Graphic(1194, 10, GraphicHeight.LOW);

    private boolean customAttackInProgress = false;

    private int safeQuadrant;

    @Override
    public boolean canAttack(Character entity, Character victim) {
        return true;
    }

    @Override
    public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    public void normalAttack(Player player, NPC galactus) {
        new Projectile(galactus, player, 1243, 250, 5, 75, 10, 0).sendProjectile();
        galactus.performAnimation(new Animation(7834));
        boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
        int modifier = 1;
        if (doubleDamage)
            modifier = 2;
        if (galactus.isRegistered())
            player.dealDamage(new Hit(Misc.random(1 * modifier, 15 * modifier), Hitmask.WATER, CombatIcon.MAGIC));
    }


    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC waterEle = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (waterEle.isChargingAttack()) {
            return true;
        }

        double healthPercentage = (double) waterEle.getConstitution() / waterEle.getDefaultConstitution();

        boolean customAttack = (healthPercentage <= 0.70 && healthPercentage >= 0.50 && !customAttackInProgress);

        if (customAttack) {
            customAttackInProgress = true;
            safeQuadrant = (int) (Math.random() * 4);
            markSafeAre(waterEle, safeQuadrant);

            TaskManager.submit(new Task(6, waterEle, false) {
                @Override
                protected void execute() {
                    damageTiles(waterEle);
                    stop();
                }
            });
        } else {
            if (victim.isPlayer()) {
                normalAttack((Player) victim, waterEle);
            }
        }

        return true;
    }

    private void markSafeAre(NPC waterEle, int quadrant) {

        List<Position> safeTiles = getTilesInQuadrant(quadrant);
        for (Position tile : safeTiles) {
            for (Player otherVictims : World.getNearbyPlayers(waterEle.getPosition(), 25)) {
                if (otherVictims != null) {
                    otherVictims.getPA().sendGraphic(gfxSafe, tile);
                    waterEle.performAnimation(animRain);

                }
            }
        }
    }

    private void damageTiles(NPC waterEle) {
        for (int i = 0; i < 4; i++) {
            if (i != safeQuadrant) {

                List<Position> dangerTiles = getTilesInQuadrant(i);
                for (Position tile : dangerTiles) {
                    for (Player otherVictims : World.getNearbyPlayers(waterEle.getPosition(), 25)) {
                        if (otherVictims != null) {
                            waterEle.forceChat("YOU WILL DROWN!");
                            otherVictims.getPA().sendGraphic(gfxDanger, tile);
                            if (otherVictims.getPosition().equals(tile)) {
                                if (!otherVictims.is_dead_in_tower()) {
                                    if (waterEle.isRegistered())
                                        otherVictims.dealDamage(new Hit(55, Hitmask.CRITICAL, CombatIcon.MAGIC));
                                }
                                customAttackInProgress = false;

                            }
                        }
                    }
                }
            }
        }
    }

    private List<Position> getTilesInQuadrant(int quadrant) {
        List<Position> tiles = new ArrayList<>();
        int startX, startY, endX, endY;

        switch (quadrant) {
            case 0:
                startX = 3480;
                startY = 3414;
                break;
            case 1:
                startX = 3488;
                startY = 3414;
                break;
            case 2:
                startX = 3480;
                startY = 3422;
                break;
            default:
                startX = 3488;
                startY = 3422;
                break;
        }

        endX = startX + 8;
        endY = startY + 8;

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                tiles.add(new Position(x, y));
            }
        }

        return tiles;
    }

    @Override
    public int attackDelay(Character entity) {
        return 7;
    }

    @Override
    public int attackDistance(Character entity) {
        return 25;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MAGIC;
    }
}
