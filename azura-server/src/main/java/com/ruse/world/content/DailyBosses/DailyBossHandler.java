package com.ruse.world.content.DailyBosses;

import com.ruse.ServerSaves.DailyBossReader;
import com.ruse.ServerSaves.DailyBossUpdater;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import lombok.Getter;
import lombok.Setter;

public class DailyBossHandler {
    public static boolean BOSSES_REGISTERED = false;

    @Getter
    @Setter
    private static long BOSS_ROTATION_TIME;
    public static int CURRENT_DAY = (int) DailyBossReader.getDailyBosses();
    public static int FULL_DAY_IN_SECONDS = 86_400_000;
    public static int TESTER_TIME_IN_SECONDS = 25_000;
    public static void ProcessEvent() {

        //System.out.println(BOSS_ROTATION_TIME);

        if (!BOSSES_REGISTERED) {
            RegisterBosses();
            return;
        }

        //DAY 1 ROTATION
        if (CURRENT_DAY == 1 && System.currentTimeMillis() > BOSS_ROTATION_TIME + FULL_DAY_IN_SECONDS) {
            RemoveEarthSpawnFire();
            return;
        }

        //DAY 2 ROTATION
        if (CURRENT_DAY == 2 && System.currentTimeMillis() > BOSS_ROTATION_TIME + FULL_DAY_IN_SECONDS) {
            RemoveFireSpawnWater();
            return;
        }

        //DAY 3 ROTATION
        if (CURRENT_DAY == 3 && System.currentTimeMillis() > BOSS_ROTATION_TIME + FULL_DAY_IN_SECONDS) {
            RemoveWaterSpawnEarth();
            return;
        }
/*
        System.out.println(System.currentTimeMillis() - BOSS_ROTATION_TIME);
*/

    }

    private static void RegisterBosses() {
        if (BOSSES_REGISTERED)
            return;
        BOSSES_REGISTERED = true;
        if (CURRENT_DAY == 1 ) {
            RemoveEarthSpawnFire();
            return;
        }
        if (CURRENT_DAY == 2 ) {
            RemoveFireSpawnWater();
            return;
        }
        if (CURRENT_DAY == 3 ) {
            RemoveWaterSpawnEarth();
            return;
        }
    }
    private static void RemoveEarthSpawnFire() {
        if (!BOSSES_REGISTERED)
            return;

        String fire = "<shad=0>@red@[FIRE] The daily boss has rotated, Lava Titan has arrived!";
        World.sendMessage(fire);
        CURRENT_DAY = 2;
        BOSS_ROTATION_TIME = System.currentTimeMillis();
        DailyBossUpdater.updateDailyBosses(2);
    }

    private static void RemoveFireSpawnWater() {
        if (!BOSSES_REGISTERED)
            return;

        String water = "<shad=0>@red@[WATER] The daily boss has rotated, Aqua Titan is now unlocked!";
        World.sendMessage(water);
        CURRENT_DAY = 3;
        BOSS_ROTATION_TIME = System.currentTimeMillis();
        DailyBossUpdater.updateDailyBosses(3);
    }

    private static void RemoveWaterSpawnEarth() {
        if (!BOSSES_REGISTERED)
            return;

        String earth = "<shad=0>@red@[EARTH] The daily boss has rotated, Gaia Titan is now unlocked!";
        World.sendMessage(earth);
        CURRENT_DAY = 1;
        BOSS_ROTATION_TIME = System.currentTimeMillis();
        DailyBossUpdater.updateDailyBosses(1);
    }
}
