package com.ruse.world.content.combat.strategy.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class RockmawScript implements CombatStrategy {
    private boolean regularRunning;
    private boolean rockFallRunning;
    private boolean groundRunning;

    public int npcId() {
        return 2466;
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

        int random = Misc.random(0, 4);
            if (random == 0 || random == 1 || random == 2) {
                regularAttack(player, boss);
            } else if (random == 3) {
                rockFallAttack(player, boss);
            } else if (random == 4) {
                groundAttack(player, boss);
            }
        return true;
    }

    private void regularAttack(Player player, NPC boss) {
        if (regularRunning || rockFallRunning || groundRunning)
            return;


        regularRunning = true;
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {

            int prayeroff = Misc.random(1, 35);
            if (prayeroff == 1) {
                PrayerHandler.deactivateAll(player);
                CurseHandler.deactivateAll(player);
                player.msgRed("Rockmaw disabled your prayers!");
            }

            boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
            new Projectile(boss, p, 1000, 150, 5, 65, 35, 0).sendProjectile();
            TaskManager.submit(new Task(3, p, false) {
                @Override
                public void execute() {
                    if (!p.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                        p.dealDamage(new Hit(Misc.random(100, 120), Hitmask.WATER, CombatIcon.RANGED));
                        p.sendMessage("<shad=0>@red@You failed to block Rockmaw's Range attack");
                    }
                    if (p.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                        p.dealDamage(new Hit(Misc.random(0), Hitmask.WATER, CombatIcon.BLOCK));
                        p.sendMessage("<shad=0>@gre@You successfully blocked Rockmaw's Range attack");
                    }
                    this.stop();
                    regularRunning = false;

                }
            });
        }
    }

    ArrayList<Position> pos = new ArrayList<>();
    private int delay = 0;

    private void rockFallAttack(Player player, NPC boss) {
        if (regularRunning || rockFallRunning || groundRunning)
            return;
        boss.forceChat("YOU SHOULD PROBABLY MOVE!!!");
        player.msgRed("YOU SHOULD PROBABLY MOVE!!!");
        rockFallRunning = true;
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 25)) {

            Position initialPosition = p.getPosition().copy();
            TaskManager.submit(new Task(1, true) {
                int tick = 0;

                @Override
                protected void execute() {
                   // boss.forceChat("YOU SHOULD PROBABLY MOVE!!!");
                   // p.msgRed("YOU SHOULD PROBABLY MOVE!!!");

                    if (tick == 2) {
                        p.getPA().sendGraphic(new Graphic(406), initialPosition);
                    }

                    if (tick == 4) {
                        if (p.getPosition().equals(initialPosition)) {
                            p.dealDamage(new Hit(Misc.random(120, 120), Hitmask.DARK_CRIT, CombatIcon.NONE));
                            p.performGraphic(new Graphic(1028));
                        }
                    }
                    if (tick == 6) {
                        stop();
                        rockFallRunning = false;
                    } else {
                        tick++;
                    }
                }
            }.bind(p));
        }
    }

    private void groundAttack(Player player, NPC boss) {
        if (regularRunning || rockFallRunning || groundRunning)
            return;

        groundRunning = true;

        for (Player p : World.getNearbyPlayers(boss.getPosition(), 25)) {
            boss.forceChat("THE EARTH CRUMBLES BENEATH YOU!");
            Random random = new Random();
            for (int i = 0; i < 12; i++) {
                int xOffset = random.nextInt(6) - 3;
                int yOffset = random.nextInt(6) - 3;
                pos.add(new Position(3552 + xOffset + i, 5405 + yOffset, player.getPosition().getZ()));
                pos.add(new Position(3552 - xOffset - i, 5405 + yOffset, player.getPosition().getZ()));
                pos.add(new Position(3552 + xOffset, 5405 + yOffset + i, player.getPosition().getZ()));
                pos.add(new Position(3552 + xOffset, 5405 - yOffset - i, player.getPosition().getZ()));
            }
            pos.forEach(position -> p.getPacketSender().sendGraphic(new Graphic(60), position));
            new Projectile(boss, p, 550, 50, 3, 75, 2, 0).sendProjectile();
            boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
            p.dealDamage(new Hit(Misc.random(0, 20), Hitmask.EARTH, CombatIcon.NONE));
            TaskManager.submit(new Task(1) {
                int ticks = 0;

                @Override
                protected void execute() {
                    if (ticks == 5) {
                        if (p != null) {
                            pos.forEach(position -> p.getPacketSender().sendGraphic(new Graphic(659), position));
                            if (pos.contains(p.getPosition())) {
                                p.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.NONE));
                            }
                        }
                        stop();
                        groundRunning = false;
                    }
                    ticks++;
                }

                @Override
                public void stop() {
                    ticks = 0;
                    delay = 0;
                    pos.clear();
                    setEventRunning(false);
                    groundRunning = false;

                }
            });
        }
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
