
package com.ruse.world.content.combat.strategy.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class SpectralMinionScript implements CombatStrategy {

    boolean fireRunning = false;
    boolean waterRunning = false;
    boolean earthRunning = false;

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        Player p = (Player) victim;
        NPC boss = (NPC) entity;

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        if (p.isAutoRetaliate() && !p.getCombatBuilder().isAttacking()){
            p.getCombatBuilder().attack(boss);
        }

        switch (boss.getId()) {
            //EASY FIRE
            case COSMOS:
            case NEBULA:
            case ECLIPSE:
                fireAttack(p, boss);
                break;
            //EASY EARTH
            case METEOR:
            case NOVA:
            case PULSAR:
                earthAttack(p, boss);
                break;
            //EASY WATER
            case COMET:
            case ASTROID:
            case ZENITH:
                waterAttack(p, boss);
                break;

        }
        return true;
    }
    private void fireAttack(Player p, NPC boss) {
        if (fireRunning || earthRunning || waterRunning)
            return;

        fireRunning = true;
        new Projectile(boss, p, 1617, 150, 10, 5, 2, 0).sendProjectile();
        int prayeroff = Misc.random(1, 25);
        if (prayeroff == 1) {
            PrayerHandler.deactivateAll(p);
            CurseHandler.deactivateAll(p);
            p.msgRed("Your Prayers were disabled!");
        }

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        TaskManager.submit(new Task(5, p, false) {
            @Override
            public void execute() {
                if (p.getCurseActive()[CurseHandler.BLOCK_MELEE]) {
                    p.dealDamage(new Hit(Misc.random(5,9), Hitmask.FIRE, CombatIcon.BLOCK));
                } else {
                    p.dealDamage(new Hit(Misc.random(45,120), Hitmask.FIRE, CombatIcon.MELEE));
                }
                this.stop();
                fireRunning = false;
            }
        });
    }

    private void waterAttack(Player p, NPC boss) {
        if (fireRunning || earthRunning || waterRunning)
            return;

        waterRunning = true;

        new Projectile(boss, p, 1010, 150, 5, 5, 2, 0).sendProjectile();
        int prayeroff = Misc.random(1, 25);
        if (prayeroff == 1) {
            PrayerHandler.deactivateAll(p);
            CurseHandler.deactivateAll(p);
        }
        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));

        TaskManager.submit(new Task(5, p, false) {
            @Override
            public void execute() {
                if (p.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                    p.dealDamage(new Hit(Misc.random(5,9), Hitmask.WATER, CombatIcon.BLOCK));
                } else {
                    p.dealDamage(new Hit(Misc.random(45,120), Hitmask.WATER, CombatIcon.MAGIC));
                }
                waterRunning = false;
                this.stop();
            }
        });

    }
    private void earthAttack(Player p, NPC boss) {
        if (fireRunning || earthRunning || waterRunning)
            return;

        earthRunning = true;

        new Projectile(boss, p, 1000, 150, 5, 5, 2, 0).sendProjectile();
        int prayeroff = Misc.random(1, 25);
        if (prayeroff == 1) {
            p.msgRed("Your Prayers were disabled!");
            PrayerHandler.deactivateAll(p);
            CurseHandler.deactivateAll(p);
        }

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));

        TaskManager.submit(new Task(5, p, false) {
            @Override
            public void execute() {
                if (p.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                    p.dealDamage(new Hit(Misc.random(5,9), Hitmask.EARTH, CombatIcon.BLOCK));
                } else {
                    p.dealDamage(new Hit(Misc.random(45,120), Hitmask.EARTH, CombatIcon.RANGED));
                }
                earthRunning = false;
                this.stop();
            }
        });

    }
    @Override
    public boolean canAttack(Character entity, Character victim) {return victim.isPlayer();}
    @Override
    public CombatContainer attack(Character entity, Character victim) {return null;}

    @Override
    public int attackDelay(Character entity) {return 5;}

    @Override
    public int attackDistance(Character entity) {return 4;}

    @Override
    public CombatType getCombatType() {return CombatType.MIXED;}

    public static final int COSMOS = 2120;
    public static final int NEBULA = 2121;
    public static final int ECLIPSE = 2122;
    public static final int METEOR = 2090;
    public static final int NOVA = 2124;
    public static final int PULSAR = 2091;
    public static final int COMET = 2126;
    public static final int ASTROID = 2127;
    public static final int ZENITH = 2128;

}