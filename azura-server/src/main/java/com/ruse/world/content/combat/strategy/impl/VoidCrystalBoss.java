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

public class VoidCrystalBoss implements CombatStrategy {
    private boolean mageRunning;
    private boolean specialRunning;

    public int npcId() {
        return 640;
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
        mageAttack(player, boss);
        return true;
    }

    private void mageAttack(Player player, NPC boss) {
        if (specialRunning || mageRunning)
            return;

        mageRunning = true;
        int prayeroff = Misc.random(1, 20);
        if (prayeroff == 1) {
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
            player.msgRed("Void Boss disabled your prayers!");
        }

        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        TaskManager.submit(new Task(2, player, false) {
            @Override
            public void execute() {
                if (player.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
                    player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                } else {
                    player.dealDamage(new Hit(Misc.random(45,120), Hitmask.CRITICAL, CombatIcon.MAGIC));
                }
                mageRunning = false;
                this.stop();

            }
        });
    }
    @Override
    public int attackDelay(Character entity) {
        return 7;
    }
    @Override
    public int attackDistance(Character entity) {
        return 10;
    }
    @Override
    public CombatType getCombatType() {
        return CombatType.MAGIC;
    }
}
