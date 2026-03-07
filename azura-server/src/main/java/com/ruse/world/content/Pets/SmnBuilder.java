package com.ruse.world.content.Pets;

import com.ruse.model.*;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.util.Misc;

public class SmnBuilder {
    public static Summon buildSummon(int id) {
        switch (id) {
            case DEATHWALKER: return new Summon.SummonBuilder(id).setCanAttack(true)
                        .setAttackAnimation(new Animation(5500))
                        .setAttackGfx(new Graphic(2188))
                        .setHit(new Hit(150, Hitmask.VOID, CombatIcon.MAGIC))
                        .setAttackSpeed(40)
                        .build();
            case ARROWSHADE:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(5512))
                        .setAttackGfx(new Graphic(2188))
                        .setHit(new Hit(200, Hitmask.VOID, CombatIcon.MAGIC))
                        .setAttackSpeed(45)
                        .build();
            case BONEMANCER:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(5528))
                        .setAttackGfx(new Graphic(2188))
                        .setHit(new Hit(250, Hitmask.VOID, CombatIcon.MAGIC))
                        .setAttackSpeed(50)
                        .build();
            case SHADOWFIEND:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(-1))
                        .setAttackGfx(new Graphic(551))
                        .setHit(new Hit(400, Hitmask.EARTH, CombatIcon.MAGIC))
                        .setAttackSpeed(50)
                        .build();
            case DEVILSPAWN:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(64))
                        .setAttackGfx(new Graphic(551))
                        .setHit(new Hit(350, Hitmask.EARTH, CombatIcon.MAGIC))
                        .setAttackSpeed(55)
                        .build();
            case ABYSSAL_TORMENTOR:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(64))
                        .setAttackGfx(new Graphic(551))
                        .setHit(new Hit(400, Hitmask.EARTH, CombatIcon.MAGIC))
                        .setAttackSpeed(60)
                        .build();
            case GRUNT_MAULER:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(1007))
                        .setAttackGfx(new Graphic(198))
                        .setHit(new Hit(450, Hitmask.EARTH, CombatIcon.MAGIC))
                        .setAttackSpeed(60)
                        .build();
            case BRUTE_CRUSHER:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(1007))
                        .setAttackGfx(new Graphic(500))
                        .setHit(new Hit(500, Hitmask.EARTH, CombatIcon.MAGIC))
                        .setAttackSpeed(65)
                        .build();
             case VINESPLITTER:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(1007))
                        .setAttackGfx(new Graphic(500))
                        .setHit(new Hit(550, Hitmask.EARTH, CombatIcon.MAGIC))
                        .setAttackSpeed(65)
                        .build();
            case PHANTOM_DRIFTER:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(5540))
                        .setAttackGfx(new Graphic(605))
                        .setHit(new Hit(600, Hitmask.WATER, CombatIcon.MAGIC))
                        .setAttackSpeed(70)
                        .build();
            case WHISPERING_WRAITH:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(5540))
                        .setAttackGfx(new Graphic(605))
                        .setHit(new Hit(650, Hitmask.WATER, CombatIcon.MAGIC))
                        .setAttackSpeed(70)
                        .build();
            case BANSHEE_KING:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(5540))
                        .setAttackGfx(new Graphic(605))
                        .setHit(new Hit(700, Hitmask.WATER, CombatIcon.MAGIC))
                        .setAttackSpeed(75)
                        .build();
            case EYE_OF_BEYOND:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(14892))
                        .setAttackGfx(new Graphic(1641))
                        .setHit(new Hit(750, Hitmask.FIRE, CombatIcon.MAGIC))
                        .setAttackSpeed(85)
                        .build();
            case TALONWING:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(5024))
                        .setAttackGfx(new Graphic(1641))
                        .setHit(new Hit(800, Hitmask.FIRE, CombatIcon.MAGIC))
                        .setAttackSpeed(90)
                        .build();
            case UMBRAL_BEAST:
                return new Summon.SummonBuilder(id)
                        .setCanAttack(true)
                        .setAttackAnimation(new Animation(7467))
                        .setAttackGfx(new Graphic(1641))
                        .setHit(new Hit(950, Hitmask.FIRE, CombatIcon.MAGIC))
                        .setAttackSpeed(90)
                        .build();
        }
        return null;
    }

    //ROW 1
    public static final int DEATHWALKER = 3151;
    public static final int ARROWSHADE = 3154;
    public static final int BONEMANCER = 3153;
    public static final int SHADOWFIEND = 3155;
    public static final int DEVILSPAWN = 3156;
    public static final int ABYSSAL_TORMENTOR = 3158;
    public static final int GRUNT_MAULER = 3159;
    public static final int BRUTE_CRUSHER = 3160;
    public static final int VINESPLITTER = 3161;
    public static final int PHANTOM_DRIFTER = 3162;
    public static final int WHISPERING_WRAITH = 3163;
    public static final int BANSHEE_KING = 3164;
    public static final int EYE_OF_BEYOND = 3165;
    public static final int TALONWING = 3166;
    public static final int UMBRAL_BEAST = 3167;

}
