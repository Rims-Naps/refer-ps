package com.ruse.world.content.combat.strategy.impl;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class FallenRangeScript implements CombatStrategy {

    public int npcId() {
        return 2200;
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
        NPC cB = (NPC) entity;
        if (victim.getConstitution() <= 0) {
            return true;
        }
        if (cB.isChargingAttack()) {
            return true;
        }
        for(Player otherVictims : World.getNearbyPlayers(cB.getPosition(), 25)) {
            new Projectile(cB, otherVictims, 335, 75, 10, 110, 0, 0).sendProjectile();
            cB.performAnimation(new Animation(cB.getDefinition().getAttackAnimation()));

            TaskManager.submit(new Task(3, otherVictims, false) {
                @Override
                public void execute() {
                    if (!otherVictims.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                        otherVictims.dealDamage(new Hit(Misc.random(5, 15), Hitmask.EARTH, CombatIcon.RANGED));
                    }
                    if (otherVictims.getCurseActive()[CurseHandler.BLOCK_RANGE]) {
                        otherVictims.dealDamage(new Hit(Misc.random(0), Hitmask.EARTH, CombatIcon.BLOCK));
                    }
                    this.stop();
                }
            });
        }


        return true;
    }


    @Override
    public int attackDelay(Character entity) {
        return 6;
    }

    @Override
    public int attackDistance(Character entity) {
        return 25;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }

}
