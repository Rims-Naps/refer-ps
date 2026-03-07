package com.ruse.world.content.combat.strategy.impl;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.effect.BurnEffect;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class HellfireScript implements CombatStrategy {
    public static boolean TILE_ATTACK_RUNNING = false;
    public static boolean GROUND_ATTACK_RUNNING = false;
    public static boolean REGULAR_ATTACK_RUNNING = false;
    public static int SAFE_SPOT = 0;

    public int npcId() {
        return 2111;
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
    public boolean customContainerAttack(Character entity, Character victim) {

        NPC boss = (NPC) entity;
        Player player = (Player) victim;
        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
        int random = Misc.random(0, 30);

            if (random == 0) {
                TileAttack(player, boss);
            } else {
                regularattack(player, boss);
        }
        return true;
    }


    private void TileAttack(Player player, NPC boss) {
        if (REGULAR_ATTACK_RUNNING)
            return;
        if (GROUND_ATTACK_RUNNING)
            return;
        if (TILE_ATTACK_RUNNING)
            return;

        Position[] cornerPositions = {
                //SET CORNERS OF TOWER ARENA HERE
                new Position(2005, 5545, player.getPosition().getZ()),
                new Position(2005, 5527, player.getPosition().getZ()),
                new Position(2023, 5545, player.getPosition().getZ()),
                new Position(2023, 5527, player.getPosition().getZ())
        };


        TILE_ATTACK_RUNNING = true;
        boss.forceChat("TURNING UP THE HEAT!");
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 25)) {
            new Projectile(boss, p, 2101, 50, 5, 65, 35, 0).sendProjectile();
            p.setBurnDamage(5);
            boss.performAnimation(new Animation(381));
            TaskManager.submit(new BurnEffect(p));
            final int[] tickCounter = {0};

            for (final Position cornerPosition : cornerPositions) {

                final int ticksPerTile = 1;
                final int maxTicks = 20;
                final Position playerPosition = new Position(p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
                final int startX = cornerPosition.getX();
                final int startY = cornerPosition.getY();
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

                for (int currentTileNumber = 0; n > 0; --n, ++currentTileNumber) {
                    final int tileNumber = currentTileNumber;
                    Position pos = new Position(x, y, p.getPosition().getZ());

                    if (tileNumber <= maxTicks) {
                        TaskManager.submit(new Task(ticksPerTile * tileNumber / 2 , false) {
                            public void execute() {
                                p.getPA().sendGraphic(new Graphic(1154, 0, GraphicHeight.LOW), new Position(pos));
                                if (pos.equals(playerPosition)) {
                                    if (p.getPosition().equals(playerPosition)) {
                                        int modifier = 1;
                                        if (boss.isRegistered())
                                            p.dealDamage(new Hit(Misc.random(50 * modifier, 100 * modifier), Hitmask.FIRE, CombatIcon.MAGIC));
                                        p.setBurnDamage(BurnEffect.BurnType.BOOSTED_BURN.getDamage());
                                        TaskManager.submit(new BurnEffect(p));

                                    }
                                }

                                tickCounter[0]++;
                                if (tickCounter[0] >= 20) {
                                    TILE_ATTACK_RUNNING = false;
                                    stop();
                                }
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
        }
    }


    private void regularattack(Player player, NPC boss) {
        if (REGULAR_ATTACK_RUNNING)
            return;
        if (GROUND_ATTACK_RUNNING)
            return;
        if (TILE_ATTACK_RUNNING)
            return;

        REGULAR_ATTACK_RUNNING = true;
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 25)) {

            new Projectile(boss, p, 1617, 150, 10, 110, 0, 0).sendProjectile();
            int prayeroff = Misc.random(1, 20);
            if (prayeroff == 1) {
                p.dealDamage(new Hit(Misc.random(20, 30), Hitmask.FIRE, CombatIcon.MELEE));
                PrayerHandler.deactivateAll(p);
                CurseHandler.deactivateAll(p);
                p.msgRed("Hellfire disabled your prayers!");
            }

            p.dealDamage(new Hit(Misc.random(10, 20), Hitmask.FIRE, CombatIcon.MELEE));

            boss.performAnimation(new Animation(10961));
            TaskManager.submit(new Task(5, p, false) {
                @Override
                public void execute() {
                    if (p.getCurseActive()[CurseHandler.BLOCK_MELEE]) {
                        p.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                    } else {
                        p.dealDamage(new Hit(Misc.random(55, 120), Hitmask.FIRE, CombatIcon.MELEE));
                    }
                    REGULAR_ATTACK_RUNNING = false;
                    this.stop();

                }
            });
        }
    }

    ArrayList<Position> pos = new ArrayList<>();
    private int delay = 0;


    @Override
    public int attackDelay(Character entity) {
        return 7; // SET BOSS ATTACK SPEED
    }

    @Override
    public int attackDistance(Character entity) {
        return 14; // SET BOSS ATTACK DISTANCE
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.RANGED;
    }
}
