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

public class PyromancerScript implements CombatStrategy {
    private boolean meleeRunning;
    private boolean specialRunning;
    public int npcId() {
        return 9813;
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

        int random = Misc.random(0, 5);
        if (random == 0 || random == 1 || random == 2 || random == 3 || random == 4) {
            meleeAttack(player, boss);
        } else if (random == 5 ) {
            specialAttack(player, boss);
        }
        return true;
    }

    private void specialAttack(Player player, NPC boss) {
        if (meleeRunning || specialRunning)
            return;

        specialRunning = true;
        Position playerSpot = player.getPosition();
        int prayeroff = Misc.random(1, 50);
        if (prayeroff == 1) {
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
            player.msgRed("Pyromancer disabled your prayers!");
        }
        boss.forceChat("BURN!");
        new Projectile(boss, player, 2181, 100, 5, 65, 35, 0).sendProjectile();
        boss.performGraphic(new Graphic(1164));
        boss.performAnimation(new Animation(69));
        TaskManager.submit(new Task(1, true) {
                int tick = 0;
                @Override
                protected void execute() {

                    if (tick == 4) {
                        if (player.getPosition().equals(playerSpot)) {
                            player.dealDamage(new Hit(Misc.random(35, 85), Hitmask.DARK_CRIT, CombatIcon.NONE));
                            player.performGraphic(new Graphic(1028));
                        }
                    }
                    if (tick == 5) {
                        stop();
                        specialRunning = false;
                    } else {
                        tick++;
                    }
                }
            }.bind(player));
        }
     private void meleeAttack(Player player, NPC boss) {
        if (meleeRunning || specialRunning)
            return;

        meleeRunning = true;

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getCurseActive()[CurseHandler.BLOCK_MELEE]) {
                    player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                } else {
                    player.dealDamage(new Hit(Misc.random(45,120), Hitmask.CRITICAL, CombatIcon.MELEE));
                }
                meleeRunning = false;
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
        return 2;
    }
    @Override
    public CombatType getCombatType() {
        return CombatType.MELEE;
    }
}
