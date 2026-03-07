package com.ruse.world.content.combat.strategy.impl;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.RiftEvent;
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

public class CryosScript implements CombatStrategy {
    public static boolean TILE_ATTACK_RUNNING = false;
    public static boolean GROUND_ATTACK_RUNNING = false;
    public static boolean REGULAR_ATTACK_RUNNING = false;
    public static int SAFE_SPOT = 0;

    public int npcId() {
        return 2112;
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

        TILE_ATTACK_RUNNING = true;
        boss.performAnimation(new Animation(11922));
        boss.forceChat("I hope you brought a sweater!");


        for (Player p : World.getNearbyPlayers(boss.getPosition(), 25)) {
            p.performGraphic(new Graphic(369));
            p.dealDamage(new Hit(Misc.random(15, 55), Hitmask.WATER, CombatIcon.MAGIC));
            p.getMovementQueue().freeze(5);
            p.msgRed("Quickly activate Inferno to survive the chilling attack.");
            TaskManager.submit(new Task(1, false) {
                int tick = 0;

                @Override
                public void execute() {
                    if (tick == 4){
                        if (p.getCurseActive()[CurseHandler.INFERNO]) {
                            p.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                        } else {
                            p.dealDamage(new Hit(120, Hitmask.WATER, CombatIcon.MAGIC));
                        }
                        p.performGraphic(new Graphic(1014));
                        TILE_ATTACK_RUNNING = false;
                        this.stop();
                    } else {
                        tick++;
                    }
                }
            });

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

            new Projectile(boss, p, 1351, 150, 10, 110, 0, 0).sendProjectile();
            int prayeroff = Misc.random(1, 35);
            if (prayeroff == 1) {
                p.dealDamage(new Hit(Misc.random(20, 30), Hitmask.WATER, CombatIcon.MAGIC));
                PrayerHandler.deactivateAll(p);
                CurseHandler.deactivateAll(p);
                p.msgRed("Cryos disabled your prayers!");
            }

            p.dealDamage(new Hit(Misc.random(10, 20), Hitmask.WATER, CombatIcon.MAGIC));

            boss.performAnimation(new Animation(10961));
            TaskManager.submit(new Task(5, p, false) {
                @Override
                public void execute() {
                    if (p.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                        p.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                    } else {
                        p.dealDamage(new Hit(Misc.random(55, 120), Hitmask.WATER, CombatIcon.MAGIC));
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
