package com.ruse.util;

import com.ruse.world.content.combat.Maxhits;
import com.ruse.world.entity.Entity;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.player.Player;

public class HPUtils {

    /**
     * @param time is in seconds
     */
    public static void calculateHitpointsBasedOnTime(Player player, int time) {

    }

    public static int calculateInstanceHP(Player player, Entity victim, int amountsToHit) {
        double melee = Maxhits.melee(player, (Character) victim);
        double range = Maxhits.ranged(player, (Character) victim);
        double magic = Maxhits.magic(player, (Character) victim);
        double average = ((melee + range + magic) / 3);
        double math = amountsToHit * average;
        return (int) math;
    }
}
