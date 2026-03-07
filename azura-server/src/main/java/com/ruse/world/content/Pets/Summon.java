package com.ruse.world.content.Pets;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Hit;
import com.ruse.model.Projectile;
import com.ruse.world.entity.Entity;
import com.ruse.world.entity.impl.npc.NPC;

public class Summon {
    private final int summonID;
    private final boolean canAttack;
    private final Graphic attackGfx;
    private final Animation attackAnimation;
    private final Hit hit;

    private final int attackSpeed;


    public int getSummonID() {
        return summonID;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public Graphic getAttackGfx() {
        return attackGfx;
    }

    public Animation getAttackAnimation() {
        return attackAnimation;
    }

    public Hit getHit() {
        return hit;
    }

    public Summon(SummonBuilder builder) {
        this.summonID = builder.summonID;
        this.canAttack = builder.canAttack;
        this.attackGfx = builder.attackGfx;
        this.attackAnimation = builder.attackAnimation;
        this.hit = builder.hit;
        this.attackSpeed = builder.attackSpeed;

    }


    public Projectile projectile(NPC pet, Entity victim) {
        return new Projectile(pet, victim,
                attackGfx.getId(), 60, 25, 30, 0, 0);
    }

    public static class SummonBuilder {
        private final int summonID;
        private boolean canAttack;
        private Graphic attackGfx;
        private Animation attackAnimation;
        private Hit hit;
        public int attackSpeed;


        public SummonBuilder setAttackSpeed(int attackSpeed) {
            this.attackSpeed = attackSpeed;
            return this;
        }


        public SummonBuilder setCanAttack(boolean canAttack) {
            this.canAttack = canAttack;
            return this;
        }

        public SummonBuilder setAttackGfx(Graphic attackGfx) {
            this.attackGfx = attackGfx;
            return this;
        }

        public SummonBuilder setAttackAnimation(Animation attackAnimation) {
            this.attackAnimation = attackAnimation;
            return this;
        }

        public SummonBuilder setHit(Hit hit) {
            this.hit = hit;
            return this;
        }


        public SummonBuilder(int summonID) {
            this.summonID = summonID;
        }

        public Summon build() {
            Summon summon = new Summon(this);
            return summon;
        }
    }
}
