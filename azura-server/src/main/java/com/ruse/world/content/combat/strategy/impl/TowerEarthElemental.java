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
import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Random;

public class TowerEarthElemental implements CombatStrategy {
    private boolean attack1Running;
    private boolean groundAttackRunning;

    private boolean prayerAttackRunning;

    public int npcId() {
        return 2019;
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
                regularAttack(player, boss);
            } else if (random == 1 || random == 2 || random == 3) {
                groundAttack(player, boss);
            } else if (random == 4 || random == 5 || random == 6) {
                prayerAttack(player, boss);
            }
        }
        return true;
    }

    private void regularAttack(Player player, NPC boss) {
        if (groundAttackRunning){
            return;
        }
        if (prayerAttackRunning){
            return;
        }
        new Projectile(boss, player, 1243, 250, 5, 75, 10, 0).sendProjectile();
        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        if (!player.getCurseActive()[CurseHandler.BLOCK_MAGIC]) {
            if (!player.is_dead_in_tower()) {
                boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
                int modifier = 1;
                if (doubleDamage)
                    modifier = 2;
                if (boss.isRegistered()) {
                    player.dealDamage(new Hit(Misc.random(1 * modifier, 50 * modifier), Hitmask.DARK_PURPLE, CombatIcon.MAGIC));
                }
            }
        }
    }


    ArrayList<Position> pos = new ArrayList<>();
    private int delay = 0;


    private void groundAttack(Player player, NPC boss) {

        if (prayerAttackRunning){
            return;
        }

        boss.forceChat("YOU DARE CHALLENGE MOTHER EARTH!!!");
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            int xOffset = random.nextInt(6) - 3;
            int yOffset = random.nextInt(6) - 3;
            pos.add(new Position(3488 + xOffset + i, 3422 + yOffset, player.getPosition().getZ()));
            pos.add(new Position(3488 - xOffset - i, 3422 + yOffset, player.getPosition().getZ()));
            pos.add(new Position(3488 + xOffset, 3422 + yOffset + i, player.getPosition().getZ()));
            pos.add(new Position(3488 + xOffset, 3422 - yOffset - i, player.getPosition().getZ()));
        }
        pos.forEach(position -> player.getPacketSender().sendGraphic(new Graphic(552), position));
        new Projectile(boss, player, 550, 50, 3, 75, 2, 0).sendProjectile();
        boss.performAnimation(new Animation(boss.getDefinition().getAttackAnimation()));
        if (!player.is_dead_in_tower()) {
            boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
            int modifier = 1;
            if (doubleDamage)
                modifier = 2;
            player.dealDamage(new Hit(Misc.random(1 * modifier, 12 * modifier), Hitmask.EARTH, CombatIcon.NONE));
        }
        TaskManager.submit(new Task(1) {
            int ticks = 0;

            @Override
            protected void execute() {
                if (ticks == 5) {
                    if (player != null) {
                        pos.forEach(position -> player.getPacketSender().sendGraphic(new Graphic(266), position));
                        if (pos.contains(player.getPosition())) {
                            if (!player.is_dead_in_tower()) {
                                boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
                                int modifier = 1;
                                if (doubleDamage)
                                    modifier = 2;
                                player.dealDamage(new Hit(50 * modifier, Hitmask.CRITICAL, CombatIcon.NONE));
                            }
                        }
                    }
                    stop();
                }
                ticks++;
            }
            @Override
            public void stop() {
                ticks = 0;
                player.setGfxattackrunning(false);
                delay = 0;
                pos.clear();
                setEventRunning(false);
            }
        });
    }

    private void prayerAttack(Player player, NPC boss) {

        if (groundAttackRunning){
            return;
        }

        new Projectile(boss, player, 1000, 150, 5, 65, 35, 0).sendProjectile();
        int prayeroff = Misc.random(1, 4);
        if (prayeroff == 1) {
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
            player.sendMessage("<shad=0>@red@Earth Elemental disabled your prayers..");
        }
        boss.performAnimation(new Animation(7834));


        TaskManager.submit(new Task(5, player, false) {
            @Override
            public void execute() {
                boolean doubleDamage = player.getTowerParty().getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.MARK_OF_VALOR);
                int modifier = 1;
                if (doubleDamage)
                    modifier = 2;
                if (!player.is_dead_in_tower()) {
                    if (player.getCurseActive()[CurseHandler.EMBERBLAST]) {
                        player.dealDamage(new Hit(0, Hitmask.NONE, CombatIcon.BLOCK));
                        player.sendMessage("<shad=0>@gre@You successfully blocked Earth Elemental's Earth attack");
                    } else {
                        player.dealDamage(new Hit(35 * modifier, Hitmask.CRITICAL, CombatIcon.RANGED));
                        player.sendMessage("<shad=0>@red@You failed to block Earth Elemental's Earth attack");
                        player.msgRed("Use Emberblast to avoid massive damage!");

                    }
                }
                this.stop();
            }
        });
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
