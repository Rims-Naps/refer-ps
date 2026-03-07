package com.ruse.world.content.combat;

import com.ruse.world.entity.impl.npc.NPC;

public class NpcMaxHitLimit {

    public static double limit(NPC npc, double damage, CombatType type) {
        int maxLimit;

        switch (npc.getId()) {
            //FOREST MOBS
            case 143:
            case 1472:
            case 7329:
            case 6799:
                maxLimit = 2500;
                break;

            //SOULBANE
            case 316:

            case 309:
            case 310:
            case 314:
            case 315:
                maxLimit = 300;
            break;

            case 2341:
            case 2342:
            case 3307:
            case 3308:
            case 6326:
            case 2745:
            case 8009:
            case 9006:
            case 1807:
            case 1084:
            case 601:
            case 4444:
            case 4412:
            case 4413:
            case 4414:
            case 4415:
            //FALLEN EVENT
            case 2097:
            case 2200:
            case 2201:
                maxLimit = 10000;
                break;



            default:
                return damage;
        }
        return Math.min(maxLimit, damage);
    }
}
