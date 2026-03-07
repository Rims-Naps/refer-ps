package com.ruse.world.content.combat.strategy.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.content.bossfights.impl.BrimstoneFight;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.effect.BurnEffect;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Brimstone boss combat script, with firewall (specialattack1) nerfed via cooldown.
 */
public class BrimstoneScript implements CombatStrategy {

    public int npcId() {
        return 2017;
    }

    // ─── FIREWALL COOLDOWN ────────────────────────────────────────────────────────
    /** 10 seconds between firewall specials against the same player */
    private static final long FIREWALL_COOLDOWN_MS = 10_000L;
    /** last time we used specialattack1 against each player */
    private static final ConcurrentMap<Long, Long> lastFirewallTime = new ConcurrentHashMap<>();

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

        if (player.getBossFight() == null || !(player.getBossFight() instanceof BrimstoneFight))
            return false;

        if (player.brimnstoneattackrunnning) {
            return false;
        }

        int currentStage = player.getBrimstoneStage();
        int hpPercent = (int) (boss.getConstitution() * 100D / boss.getDefaultConstitution());

        if (hpPercent <= 75 && currentStage == 1) {
            player.getBossFight().setCurrentStage(2);
            currentStage = 2;
        } else if (hpPercent <= 50 && currentStage == 2) {
            player.getBossFight().setCurrentStage(3);
            currentStage = 3;
        }

        int random = Misc.random(0, 3);
        switch (currentStage) {
            case 1:
                if (random == 0) {
                    specialattack1(player, boss);
                } else if (random <= 2) {
                    specialattack2(player, boss);
                } else {
                    specialattack3(player, boss);
                }
                break;
            case 2:
                if (random == 0) {
                    specialattack1(player, boss);
                } else if (random <= 2) {
                    specialattack3(player, boss);
                } else {
                    specialattack2(player, boss);
                }
                break;
            case 3:
                if (random == 0 || random == 3) {
                    specialattack3(player, boss);
                } else {
                    specialattack1(player, boss);
                }
                break;
        }
        return true;
    }

    private void specialattack1(Player player, NPC boss) {
        // ─── enforce firewall cooldown ───────────────────────────────────────────
        long now = System.currentTimeMillis();
        long key = player.getLongUsername();
        Long last = lastFirewallTime.get(key);
        if (last != null && (now - last) < FIREWALL_COOLDOWN_MS) {
            // still on cooldown, skip this special
            return;
        }
        lastFirewallTime.put(key, now);

        // ─── existing firewall attack logic ──────────────────────────────────────
        Position[] cornerPositions = {
                new Position(3866, 2775, player.getPosition().getZ()),
                new Position(3881, 2775, player.getPosition().getZ()),
                new Position(3866, 2791, player.getPosition().getZ()),
                new Position(3881, 2791, player.getPosition().getZ())
        };

        player.setBrimnstoneattackrunnning(true);
        boss.forceChat("YOU WILL BURN!!");
        new Projectile(boss, player, 1155, 50, 5, 65, 35, 0).sendProjectile();
        player.setBurnDamage(BurnEffect.BurnType.BRIMSTONE_ATTACK.getDamage());
        boss.performAnimation(new Animation(7834));
        TaskManager.submit(new BurnEffect(player));
        final int[] tickCounter = {0};

        for (final Position cornerPosition : cornerPositions) {
            // border graphics
            for (int x = 3866; x < 3881; x++) {
                player.getPA().sendGraphic(new Graphic(1154), new Position(x, 2791, player.getPosition().getZ()));
                player.getPA().sendGraphic(new Graphic(1154), new Position(x, 2775, player.getPosition().getZ()));
            }
            for (int y = 2775; y < 2791; y++) {
                player.getPA().sendGraphic(new Graphic(1154), new Position(3866, y, player.getPosition().getZ()));
                player.getPA().sendGraphic(new Graphic(1154), new Position(3881, y, player.getPosition().getZ()));
            }

            // Bresenham line from corner to player
            final int ticksPerTile = 1, maxTicks = 20;
            final Position playerPos = player.getPosition().copy();
            int startX = cornerPosition.getX(), startY = cornerPosition.getY();
            int endX = playerPos.getX(), endY = playerPos.getY();
            int dx = Math.abs(endX - startX), dy = Math.abs(endY - startY);
            int x = startX, y = startY;
            int n = 1 + dx + dy, x_inc = (endX > startX ? 1 : -1), y_inc = (endY > startY ? 1 : -1);
            int error = dx - dy; dx *= 2; dy *= 2;

            for (int tileNum = 0; n > 0; --n, ++tileNum) {
                final Position pos = new Position(x, y, playerPos.getZ());
                if (tileNum <= maxTicks) {
                    TaskManager.submit(new Task(ticksPerTile * tileNum / 2, false) {
                        @Override
                        public void execute() {
                            player.getPA().sendGraphic(new Graphic(1154, 0, GraphicHeight.LOW), pos);
                            if (pos.equals(playerPos) && player.getPosition().equals(playerPos)) {
                                player.dealDamage(new Hit(Misc.random(50, 50), Hitmask.FIRE, CombatIcon.MAGIC));
                                player.setBurnDamage(BurnEffect.BurnType.BRIMSTONE_ATTACK.getDamage());
                                TaskManager.submit(new BurnEffect(player));
                            }
                            tickCounter[0]++;
                            if (tickCounter[0] >= 20) {
                                player.setBrimnstoneattackrunnning(false);
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
        if (player.brimnstoneattackrunnning) {
            return;
        }
        new Projectile(boss, player, 1000, 150, 5, 65, 35, 0).sendProjectile();
        int prayeroff = Misc.random(1,4);
        if (prayeroff == 1) {
            player.sendMessage("<shad=0>@red@Brimstone disabled your prayers..");
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
        }
        boss.performAnimation(new Animation(7834));

        TaskManager.submit(new Task(5, player, false) {
            @Override
            public void execute() {
                if (player.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                    player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                    player.sendMessage("<shad=0>@gre@You successfully blocked Brimstone's ranged attack");
                } else {
                    player.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.RANGED));
                    player.sendMessage("<shad=0>@red@You failed to block Brimstone's ranged attack");
                }
                stop();
            }
        });
    }

    private void specialattack3(Player player, NPC boss) {
        new Projectile(boss, player, 1010, 150, 5, 65, 35, 0).sendProjectile();
        int prayeroff = Misc.random(1,4);
        if (prayeroff == 1) {
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
            player.sendMessage("<shad=0>@red@Brimstone disabled your prayers..");
        }
        boss.performAnimation(new Animation(7834));

        TaskManager.submit(new Task(5, player, false) {
            @Override
            public void execute() {
                if (player.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                    player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                    player.sendMessage("<shad=0>@gre@You successfully blocked Brimstone's magic attack");
                } else {
                    player.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.MAGIC));
                    player.sendMessage("<shad=0>@red@You failed to block Brimstone's magic attack");
                }
                stop();
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
