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

public class ToxusScript implements CombatStrategy {
    private boolean meleeRunning;
    private boolean mageRunning;
    private boolean rangeRunning;

    public int npcId() {
        return 2111;
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

        int random = Misc.random(0, 12);
        if (random == 0) {
          meleeAttack(player, boss);
        } else if (random == 0 ||random == 1 || random == 2 || random == 3 || random == 4 || random == 5 || random == 6 || random == 7|| random == 8 || random == 9 || random == 10) {
            rangeAttack(player, boss);
        } else if (random == 11 ) {
            mageAttack(player, boss);
        } else if (random == 12 ) {
            specialAttack(player, boss);
        }
        return true;
    }

    private void rangeAttack(Player player, NPC boss) {
        if (mageRunning || meleeRunning)
            return;

        rangeRunning = true;
        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
            new Projectile(boss, p, 1000, 150, 5, 65, 35, 0).sendProjectile();
            int prayeroff = Misc.random(1, 50);
            if (prayeroff == 1) {
                p.msgRed("Toxus disabled your prayers..");
                player.dealDamage(new Hit(Misc.random(20,30), Hitmask.VOID, CombatIcon.MELEE));
                PrayerHandler.deactivateAll(p);
                CurseHandler.deactivateAll(p);
            }

            p.dealDamage(new Hit(Misc.random(2,8), Hitmask.VOID, CombatIcon.RANGED));


            boss.performAnimation(new Animation(381));

            TaskManager.submit(new Task(5, p, false) {
                @Override
                public void execute() {
                    if (p.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                        p.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                        p.sendMessage("<shad=0>@gre@You successfully blocked Toxuss's range attack");
                    } else {
                        p.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.RANGED));
                        p.sendMessage("<shad=0>@red@You failed to block Toxuss's range attack");
                    }
                    rangeRunning = false;
                    this.stop();
                }
            });
        }
    }

    private void mageAttack(Player player, NPC boss) {
        if (rangeRunning || meleeRunning)
            return;

        mageRunning = true;

        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {
            new Projectile(boss, p, 1010, 150, 5, 65, 35, 0).sendProjectile();
            int prayeroff = Misc.random(1, 50);
            if (prayeroff == 1) {
                p.msgRed("Toxus disabled your prayers..");
                player.dealDamage(new Hit(Misc.random(20,30), Hitmask.VOID, CombatIcon.MAGIC));
                PrayerHandler.deactivateAll(p);
                CurseHandler.deactivateAll(p);
            }

            p.dealDamage(new Hit(Misc.random(2,8), Hitmask.VOID, CombatIcon.MAGIC));

            boss.performAnimation(new Animation(381));

            TaskManager.submit(new Task(5, p, false) {
                @Override
                public void execute() {
                    if (p.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                        p.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                        p.sendMessage("<shad=0>@gre@You successfully blocked Toxus's Magic attack");
                    } else {
                        p.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.MAGIC));
                        p.msgRed("You failed to block Toxus's Magic attack");
                    }
                    mageRunning = false;
                    this.stop();
                }
            });
        }
    }
    private void specialAttack(Player player, NPC boss) {
        boss.forceChat("YOU BETTER RUN!");
        boss.performAnimation(new Animation(11922));

        for (Player p : World.getNearbyPlayers(boss.getPosition(), 20)) {

            p.msgRed("YOU BETTER RUN!");
            Position initialPosition = p.getPosition().copy();
            TaskManager.submit(new Task(1, true) {
                int tick = 0;

                @Override
                protected void execute() {
                    if (tick == 2) {
                        p.getPA().sendGraphic(new Graphic(1935), initialPosition);
                    }

                    if (tick == 6) {
                        if (p.getPosition().equals(initialPosition)) {
                            p.dealDamage(new Hit(Misc.random(120, 120), Hitmask.DARK_CRIT, CombatIcon.NONE));
                            p.performGraphic(new Graphic(1028));
                        }
                    }
                    if (tick == 7) {
                        stop();
                    } else {
                        tick++;
                    }
                }
            }.bind(p));
        }
    }

    private void meleeAttack(Player player, NPC boss) {
        if (rangeRunning || mageRunning)
            return;

        meleeRunning = true;
        new Projectile(boss, player, 1617, 150, 10, 110, 0, 0).sendProjectile();
        int prayeroff = Misc.random(1, 50);
        if (prayeroff == 1) {
            player.msgRed("Toxus disabled your prayers..");
            player.dealDamage(new Hit(Misc.random(20,30), Hitmask.VOID, CombatIcon.MELEE));
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
        }

        player.dealDamage(new Hit(Misc.random(2,8), Hitmask.VOID, CombatIcon.RANGED));


        boss.performAnimation(new Animation(381));
        TaskManager.submit(new Task(5, player, false) {
            @Override
            public void execute() {
                if (player.getCurseActive()[CurseHandler.BLOCK_MELEE]) {
                    player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                    player.sendMessage("<shad=0>@gre@You successfully blocked Toxus's Melee Attack");
                } else {
                    player.dealDamage(new Hit(120, Hitmask.CRITICAL, CombatIcon.MELEE));
                    player.sendMessage("<shad=0>@red@You failed to block Toxus's Melee Attack");
                }
                meleeRunning = false;
                this.stop();

            }
        });
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
