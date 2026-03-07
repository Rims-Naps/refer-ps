package com.ruse.world.content.combat.strategy.impl;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import static com.ruse.model.container.impl.Equipment.AMMUNITION_SLOT;

public class AfkBossScript implements CombatStrategy {
    public int npcId() {
        return 576;
    }
    public boolean aggrostage;
    public void regularAttack(Player player, NPC afkBoss) {
        afkBoss.getMovementQueue().setLockMovement(true).reset();
        afkBoss.performAnimation(new Animation(7853));
        new Projectile(afkBoss, player, 999, 50, 5, 45, 0, 0).sendProjectile();
        player.getCombatBuilder().attack(afkBoss);
        player.getInventory().add(5020, Misc.random(1, 6));

        //EXTRA TICKETS HERE
        /*if (player.getEquipment().get(AMMUNITION_SLOT).getId() == 2736 || player.getEquipment().get(AMMUNITION_SLOT).getId() == 2738) {
                player.getInventory().add(5020, Misc.random(0, 15));
        }*/
    }

    @Override
    public boolean customContainerAttack(Character entity, Character victim) {
        NPC boss = (NPC) entity;

        int healthPercentage = (int) (((double) boss.getConstitution() / (double) boss.getDefaultConstitution()) * 100D);
        boolean attack0 = (healthPercentage >= 90);


        if (attack0 && GameSettings.aggrostage) {
            for (Player p : World.getNearbyPlayers(boss.getPosition(), 8)) {
                p.getCombatBuilder().attack(boss);
            }
        }

        for (Player p: World.getNearbyPlayers(boss.getPosition(), 12)) {
            if (p != null) {
                regularAttack(p, boss);
            }
        }
        return true;
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
    public int attackDelay(Character entity) {
        return 8;
    }

    @Override
    public int attackDistance(Character entity) {
        return 15;
    }
    public int currentStagemegaman = 0;

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }
}