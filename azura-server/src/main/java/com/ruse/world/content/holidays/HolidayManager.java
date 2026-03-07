package com.ruse.world.content.holidays;

import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.holidays.boss.impl.BossData;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class HolidayManager {

    @Getter
    @Setter
    public static boolean meterPaused = false;

    @Getter
    @Setter
    private static double meter = 0;

    @Getter
    @Setter
    private static int boostStage = 0;

    @Getter
    @Setter
    private static boolean spawnChests = false;

    @Getter
    @Setter
    private static boolean isKilled = false;

    @Getter
    @Setter
    private static boolean spawnedNormally = false;

    @Getter
    @Setter
    private static Map<String, Double> contributors = new HashMap<>();

    public static void increaseMeter(Player player, double amount) {
        if (meterPaused) return;
        if (player != null) {
            if (contributors.containsKey(player.getUsername()))
                contributors.put(player.getUsername(), contributors.get(player.getUsername()) + amount);
            else contributors.put(player.getUsername(), amount);
        }
        meter += amount;
        checkMeter();
        //System.out.println("meter increased by" + amount);
    }

    public static void RandomGiveaway() {
        Player player = null;
        int i = 0;
        while (player == null && i < 500) {
            player = World.getPlayers().get(Misc.getRandom(1 + World.getPlayers().size() - 1));
            i++;
            if (player != null) {
                player.getInventory().add(15668, 1);
                World.sendMessage("<col=AF70C3><shad=0>[Easter] The Server has given @red@<shad=0>" + player.getUsername() + "<col=AF70C3> a @red@<shad=0>Box of Treasures!");
            }
        }
    }

    public static void reset() {
        if (!isKilled() && isSpawnedNormally()) {
            HolidayManager.setBoostStage(0);
            HolidayManager.setMeter(0);
            setKilled(true);
            setSpawnedNormally(false);
        }
//        for (int i = 0; i < 30; i++) {
//            if (isSpawnChests())
//                continue;
//            int randomX = Misc.random(2552, 2572);
//            int randomY = Misc.random(4595, 4615);
//            NPC npc = new NPC(767, new Position(randomX, randomY, 0));
//            World.register(npc);
//        }
        setSpawnChests(true);
    }

    private static void checkMeter() {
        if (meter >= 100) {
            setKilled(false);
            setSpawnChests(false);
            setSpawnedNormally(true);
            World.sendMessage("<col=AF70C3><shad=0>[Easter] Da Bunny has arrived!! Type ::bunny attend.");
            World.sendBroadcastMessage("Da Bunny! Type ::bunny to attend.");
            meter = 0;
            boostStage = 0;
            new BossData().spawn();
            RandomGiveaway();
        } else if (meter >= 80 && boostStage == 3) {
            World.sendMessage("<col=AF70C3><shad=0>[Easter] The Easter Meter has reached 80%, Da Bunny is almost here!");
            RandomGiveaway();
            boostStage++;
        } else if (meter >= 60 && boostStage == 2) {
            RandomGiveaway();
            World.sendMessage("<col=AF70C3><shad=0>[Easter] The Easter Meter has reached 60%! Droprate/Maxhit increased.");
            boostStage++;
        } else if (meter >= 40 && boostStage == 1) {
            World.sendMessage("<col=AF70C3><shad=0>[Easter] The Easter Meter has reached 40%!");
            RandomGiveaway();
            boostStage++;
        } else if (meter >= 20 && boostStage == 0) {
            World.sendMessage("<col=AF70C3><shad=0>[Easter] The Easter Meter reached 20%! Kill Monsters to fill the meter!");
            boostStage++;
        }
    }
}
