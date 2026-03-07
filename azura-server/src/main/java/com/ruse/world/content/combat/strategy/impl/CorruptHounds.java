package com.ruse.world.content.combat.strategy.impl;

import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class CorruptHounds implements CombatStrategy {

    private static final Graphic gfx1 = new Graphic(1207, 3, GraphicHeight.LOW);

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
                    cB.performAnimation(new Animation(6581));
                    //otherVictims.performGraphic(gfx1);
                    otherVictims.dealDamage(new Hit(Misc.random(0,5), Hitmask.DARK_RED, CombatIcon.MAGIC));
                    new Projectile(cB, otherVictims, 1002, 150, 50, 25, 0, 5).sendProjectile();

            }


        return true;
    }


    @Override
    public int attackDelay(Character entity) {
        return 7;
    }

    @Override
    public int attackDistance(Character entity) {
        return 15;
    }

    @Override
    public CombatType getCombatType() {
        return CombatType.MIXED;
    }

}
