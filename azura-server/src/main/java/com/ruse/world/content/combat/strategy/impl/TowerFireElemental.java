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
import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class TowerFireElemental implements CombatStrategy {
    private boolean attack1Running;
    public int npcId() {
        return 2021;
    }
    @Override public boolean canAttack(Character entity, Character victim) {
        return true;
    }
    @Override public CombatContainer attack(Character entity, Character victim) {
        return null;
    }

    @Override public boolean customContainerAttack(Character entity, Character victim) {

        NPC boss = (NPC) entity;
        Player player = (Player) victim;

        if (attack1Running) {
            return false;
        }

        int random = Misc.random(0, 6);
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
            if (random == 0) {
                specialattack1(player, boss);
            } else if (random == 1 || random == 2 || random == 3) {
                specialattack2(player, boss);
            } else if (random == 4 || random == 5 || random == 6) {
                specialattack3(player, boss);
            }
        }
        return true;
    }

    private void specialattack1(Player player, NPC boss) {
        if (attack1Running) {
            return;
        }

        Position[] cornerPositions = {
                //SET CORNERS OF TOWER ARENA HERE
                new Position(3483, 3428, player.getPosition().getZ()),
                new Position(3493, 3428, player.getPosition().getZ()),
                new Position(3483, 3416, player.getPosition().getZ()),
                new Position(3493, 3416, player.getPosition().getZ())
        };


            attack1Running = true;
            boss.forceChat("MUAHAHA BURN FOOLS!!");
            new Projectile(boss, player, 1155, 50, 5, 65, 35, 0).sendProjectile();
        if (!player.is_dead_in_tower()) {
                player.setBurnDamage(BurnEffect.BurnType.BRIMSTONE_ATTACK.getDamage());
            }
            boss.performAnimation(new Animation(7834));
            TaskManager.submit(new BurnEffect(player));
            final int[] tickCounter = {0};

            for (final Position cornerPosition : cornerPositions) {

                final int ticksPerTile = 1;
                final int maxTicks = 20;
                final Position playerPosition = new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
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
                    Position pos = new Position(x, y, player.getPosition().getZ());

                    if (tileNumber <= maxTicks) {
                        TaskManager.submit(new Task(ticksPerTile * tileNumber / 2, false) {
                            public void execute() {
                                if (!player.is_dead_in_tower()) {
                                    player.getPA().sendGraphic(new Graphic(1154, 0, GraphicHeight.LOW), new Position(pos));
                                    if (pos.equals(playerPosition)) {
                                        if (player.getPosition().equals(playerPosition)) {
                                            boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
                                            int modifier = 1;
                                            if (doubleDamage)
                                                modifier = 2;
                                            if (boss.isRegistered())
                                                player.dealDamage(new Hit(Misc.random(10 * modifier, 15 * modifier), Hitmask.FIRE, CombatIcon.MAGIC));
                                            player.setBurnDamage(BurnEffect.BurnType.BRIMSTONE_ATTACK.getDamage());
                                            TaskManager.submit(new BurnEffect(player));
                                        }
                                    }
                                }

                                tickCounter[0]++;
                                if (tickCounter[0] >= 20) {
                                    attack1Running = false;
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




    private void specialattack2(Player player, NPC boss) {

        if (attack1Running) {
            return;
        }

            new Projectile(boss, player, 1166, 150, 5, 65, 35, 0).sendProjectile();
            int prayeroff = Misc.random(1, 4);
            if (prayeroff == 1) {
                player.sendMessage("<shad=0>@red@Fire Elemental disabled your prayers..");
                PrayerHandler.deactivateAll(player);
                CurseHandler.deactivateAll(player);
            }
            boss.performAnimation(new Animation(7834));
            TaskManager.submit(new Task(5, player, false) {
                @Override
                public void execute() {
                    if (!player.is_dead_in_tower()) {
                        if (player.getCurseActive()[CurseHandler.RIPTIDE]) {
                            player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                            player.sendMessage("<shad=0>@gre@You successfully blocked Fire Elemental's Flame attack");
                        } else {
                            boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
                            int modifier = 1;
                            if (doubleDamage)
                                modifier = 2;
                            if (boss.isRegistered())
                                player.dealDamage(new Hit(50 * modifier, Hitmask.CRITICAL, CombatIcon.MAGIC));
                            player.sendMessage("<shad=0>@red@You failed to block Fire Elemental's Flame attack");
                            player.msgRed("Use Riptide to avoid massive damage!");
                        }
                    }
                    this.stop();
                }
            });
        }

    private void specialattack3(Player player, NPC boss) {

            new Projectile(boss, player, 1010, 150, 5, 65, 35, 0).sendProjectile();
            int prayeroff = Misc.random(1, 4);
            if (prayeroff == 1) {
                PrayerHandler.deactivateAll(player);
                CurseHandler.deactivateAll(player);
                player.sendMessage("<shad=0>@red@Fire Elemental disabled your prayers..");
            }
            boss.performAnimation(new Animation(7834));

            TaskManager.submit(new Task(5, player, false) {
                @Override
                public void execute() {
                    if (player.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                        if (!player.is_dead_in_tower()) {
                            player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                            player.sendMessage("<shad=0>@gre@You successfully blocked Fire Elemental's Magic attack");
                        }
                    } else {
                        if (!player.is_dead_in_tower()) {
                            boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
                            int modifier = 1;
                            if (doubleDamage)
                                modifier = 2;
                            if (boss.isRegistered())
                                player.dealDamage(new Hit(35 * modifier, Hitmask.CRITICAL, CombatIcon.RANGED));
                            player.sendMessage("<shad=0>@red@You failed to block Fire Elemental's Magic attack");
                        }
                    }
                    this.stop();
                }
            });
        }


    @Override
    public int attackDelay(Character entity) {
        return 8;
    }

    @Override
    public int attackDistance(Character entity) {
        return 20;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MAGIC;
    }
}
