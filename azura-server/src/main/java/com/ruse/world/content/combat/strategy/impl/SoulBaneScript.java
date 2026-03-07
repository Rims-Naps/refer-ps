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
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class SoulBaneScript implements CombatStrategy {
    public static boolean TILE_ATTACK_RUNNING = false;
    public static boolean GROUND_ATTACK_RUNNING = false;
    public static boolean REGULAR_ATTACK_RUNNING = false;
    public static int SAFE_SPOT = 0;

    public int npcId() {
        return 316;
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
        int random = Misc.random(0, 4);

            if (healthPercentage <= 95) {
                if (random == 0) {
                    TileAttack(player, boss);
                } else if (random == 1 || random == 2 || random == 3) {
                    regularattack(player, boss);
                } else if (random == 4) {
                    RandomGroundAttack(player, boss);
                }
            }

            if (healthPercentage <= 65) {
                if (random == 0) {
                    RandomGroundAttack(player, boss);
                } else if (random == 1 || random == 2 || random == 3) {
                    regularattack(player, boss);
                } else if (random == 4) {
                    TileAttack(player, boss);
                }
            }

            if (healthPercentage <= 35) {
                if (random == 0) {
                    RandomGroundAttack(player, boss);
                } else if (random == 1 || random == 2 || random == 3) {
                    regularattack(player, boss);
                } else if (random == 4) {
                    TileAttack(player, boss);
                }
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
                new Position(2849, 3995, player.getPosition().getZ()),
                new Position(2849, 3978, player.getPosition().getZ()),
                new Position(2868, 3978, player.getPosition().getZ()),
                new Position(2868, 3995, player.getPosition().getZ())
        };


        TILE_ATTACK_RUNNING = true;
        boss.forceChat("YOU WILL FAIL!!");
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 25)) {
            new Projectile(boss, p, 2101, 50, 5, 65, 35, 0).sendProjectile();
            p.setBurnDamage(5);
            boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
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
                        TaskManager.submit(new Task(ticksPerTile * tileNumber / 2, false) {
                            public void execute() {
                                p.getPA().sendGraphic(new Graphic(5, 0, GraphicHeight.LOW), new Position(pos));
                                if (pos.equals(playerPosition)) {
                                    if (p.getPosition().equals(playerPosition)) {
                                        int modifier = 1;
                                        if (boss.isRegistered())
                                            p.dealDamage(new Hit(Misc.random(5 * modifier, 12 * modifier), Hitmask.FIRE, CombatIcon.MAGIC));
                                        p.setBurnDamage(BurnEffect.BurnType.BRIMSTONE_ATTACK.getDamage());
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

        for (Player p : World.getNearbyPlayers(boss.getPosition(), 25)) {
            new Projectile(boss, p, 2188, 75, 10, 110, 0, 0).sendProjectile();
            boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
            REGULAR_ATTACK_RUNNING = true;

            TaskManager.submit(new Task(3, player, false) {
                @Override
                public void execute() {
                    if (!p.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                        p.dealDamage(new Hit(Misc.random(4, 10), Hitmask.WATER, CombatIcon.MAGIC));
                    }
                    if (p.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                        p.dealDamage(new Hit(Misc.random(0,3), Hitmask.WATER, CombatIcon.BLOCK));
                    }
                    this.stop();
                    REGULAR_ATTACK_RUNNING = false;
                }
            });
        }
    }
    ArrayList<Position> pos = new ArrayList<>();
    private int delay = 0;
    private void RandomGroundAttack(Player player, NPC boss) {
        if (REGULAR_ATTACK_RUNNING)
            return;
        if (GROUND_ATTACK_RUNNING)
            return;
        if (TILE_ATTACK_RUNNING)
            return;

        GROUND_ATTACK_RUNNING = true;

        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            int xOffset = random.nextInt(6) - 3;
            int yOffset = random.nextInt(6) - 3;
            pos.add(new Position(2859 + xOffset + i, 3985 + yOffset, 4));
            pos.add(new Position(2859 - xOffset - i, 3985 + yOffset, 4));
            pos.add(new Position(2859 + xOffset, 3985 + yOffset + i, 4));
            pos.add(new Position(2859 + xOffset, 3985 - yOffset - i, 4));
        }
        pos.forEach(position -> player.getPacketSender().sendGraphic(new Graphic(281), position));
        new Projectile(boss, player, 453 , 50, 3, 75, 2, 0).sendProjectile();
        boss.performAnimation(new Animation(5545));
        player.dealDamage(new Hit(Misc.random(0, 15), Hitmask.EARTH, CombatIcon.NONE));
        TaskManager.submit(new Task(1) {
            int ticks = 0;

            @Override
            protected void execute() {
                if (ticks == 5) {
                    if (player != null) {
                        pos.forEach(position -> player.getPacketSender().sendGraphic(new Graphic(975), position));
                        if (pos.contains(player.getPosition())) {
                            player.dealDamage(new Hit(Misc.random(0,120), Hitmask.CRITICAL, CombatIcon.NONE));
                        }
                    }
                    stop();
                }
                ticks++;
            }
            @Override
            public void stop() {
                ticks = 0;
                GROUND_ATTACK_RUNNING = false;
                delay = 0;
                pos.clear();
                setEventRunning(false);
            }
        });
    }

    @Override
    public int attackDelay(Character entity) {
        return 8; // SET BOSS ATTACK SPEED
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
