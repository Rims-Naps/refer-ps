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
import java.util.List;
import java.util.stream.Collectors;

public class GrimScript implements CombatStrategy {
    private boolean meleeRunning;
    private boolean mageRunning;
    private boolean rangeRunning;
    private boolean specialRunning;

    public int npcId() {
        return 2467;
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

        int random = Misc.random(0, 3);
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
            if (random == 0) {
                iceWaveAttack(p, boss);
            } else {
                regularAttack(p, boss);
            }
        }
        return true;
    }

    private void regularAttack(Player player, NPC boss) {
        int prayeroff = Misc.random(1, 40);
        if (prayeroff == 1) {
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
            player.msgRed("Grimlash disabled your prayers!");
        }

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        new Projectile(boss, player, 500 , 75, 10, 110, 0, 0).sendProjectile();
        TaskManager.submit(new Task(3, player, false) {
            @Override
            public void execute() {
                if (!player.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                    player.dealDamage(new Hit(Misc.random(95, 120), Hitmask.WATER, CombatIcon.MAGIC));
                    player.sendMessage("<shad=0>@red@You failed to block Grimlash's Ice attack");
                }
                if (player.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                    player.dealDamage(new Hit(Misc.random(Misc.random(1,8)), Hitmask.WATER, CombatIcon.BLOCK));
                    player.sendMessage("<shad=0>@gre@You successfully blocked Grimlash's Ice attack");
                }
                this.stop();
            }
        });
    }

    static List<Position> tiles = new ArrayList<>();

    private void iceWaveAttack(Player player, NPC boss) {

        if (boss.getConstitution() <= 0) {
            return;
        }

        if (boss.isChargingAttack()) {
            return;
        }

        Animation anim = new Animation(boss.getDefinition().getAttackAnimation());
        Graphic graphics = new Graphic(363);

        if (!boss.isChargingAttack()) {
            boss.performAnimation(anim);
            boss.setChargingAttack(false);
            boss.forceChat("YOU WILL FREEZE");

            TaskManager.submit(new Task(2, boss, false) {

                @Override
                public void execute() {
                    getWavesNorthSouth(boss, player);
                }

                public void getWavesNorthSouth(NPC npc, Player player) {
                    if (tiles.size() <= 0) {
                        boss.setChargingAttack(false);
                        this.stop();
                        return;
                    }
                    TaskManager.submit(new Task(3 / 2, boss, false) {
                        final int[] y = {5421};
                        final int hole1 = Misc.exclusiveRandom(1, 4);
                        final int hole2 = Misc.exclusiveRandom(1, 3);
                        final int hole3 = Misc.exclusiveRandom(1, 4);
                        final int hole4 = Misc.exclusiveRandom(1, 3);

                        public void execute() {

                            final List<Position> currentYTiles = tiles.stream().filter(position -> position.getY() == y[0]).collect(Collectors.toList());
                            removeExitPoints(currentYTiles, hole1, hole2, hole3, hole4);

                            for (Position p : currentYTiles) {
                                player.getPacketSender().sendGraphic(new Graphic(500), new Position(p.getX(), p.getY(), p.getZ()));
                                if (player.getPosition().getX() == p.getX() &&
                                    player.getPosition().getY() == p.getY() &&
                                    player.getPosition().getZ() == boss.getPosition().getZ()) {
                                    int damage = 120;
                                    player.dealDamage(new Hit(damage, Hitmask.FIRE, CombatIcon.NONE));
                                    player.msgRed("You were frozen by the Blizzard");
                                    player.performGraphic(new Graphic(369));
                                    player.getMovementQueue().stun(5);
                                    player.forceChat("IM FREEZING!!");
                                }

                                if (p.getX() == 3665) {
                                    y[0]--;
                                }
                                if (p.getX() == 3665 && y[0] == 5421) {
                                    boss.setChargingAttack(false);
                                    this.stop();
                                }
                            }
                        }
                    });
                    this.stop();
                }
            });
        }
    }

    static {
        for (int y = 5421; y >= 5403; y--) {
            for (int x = 3665; x <= 3687; x++) {
                tiles.add(new Position(x, y));
            }
        }
    }


    public void removeExitPoints(List<Position> tiles, int hole1, int hole2, int hole3, int hole4) {
        if (tiles.isEmpty()) {
            return;
        }
        if (hole1 < tiles.size()) {
            tiles.remove(hole1);
        }
        if (hole2 < tiles.size()) {
            tiles.remove(hole2);
        }
        if (hole3 < tiles.size()) {
            tiles.remove(hole3);
        }
        if (hole4 < tiles.size()) {
            tiles.remove(hole4);
        }
    }


    @Override
    public int attackDelay(Character entity) {
        return 6;
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
