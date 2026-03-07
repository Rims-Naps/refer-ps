package com.ruse.world.content.GlobalBosses;

import com.ruse.GameSettings;
import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.TimeUnit;

public class CelestialBossEvent {

    public static NPC currentBoss = null;
    public static boolean isAlive = false;

    public static int tick = -10_000;

    public static Position SPAWN_POINT = new Position(2527, 3540,0);

    public static int[] BOSS_IDS = {
            556
    };

    public static String timeLeft() {

        int ticks = 10_000 - (tick % 10_000);
        ticks /= 100;
        ticks *= 60;

        long ms = ticks ;
        String  m = String.format("%dh %dm", TimeUnit.SECONDS.toHours(ms),
                TimeUnit.SECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(ms)),
                TimeUnit.SECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(ms)));

        if (tick < 0) {
            m = "Soon";
        }
        return m;
    }

    public static void sequence() {

        tick++;

        if (!isAlive && tick % 10_000 == 99_970) {
            String message = "Celestial Orc will spawn in 30 seconds!";
            World.sendMessage(message);
        }
        if (!isAlive && tick % 10_000 == 99_940) {
            String message = "Celestial Orc will spawn in 1 minute!";
            World.sendMessage(message);
        }

        if (!isAlive && tick % 10_000 == 99_500) {
            String message = "Celestial Orc will spawn in 5 minutes!";
            World.sendMessage(message);
        }

        if (tick % 10_000 == 0) {
            if(currentBoss == null || currentBoss.isDying() || !currentBoss.isRegistered() && !isAlive) {
                int boss = BOSS_IDS[Misc.random(BOSS_IDS.length - 1)];
                NPC npc = new NPC(boss, SPAWN_POINT);
                currentBoss = npc;
                isAlive = true;
                World.register(npc);
                String message = "";
                String message2 = "";

                if (boss == 556) {
                    message = "Celestial Orc has spawned!";
                    message2 = "<shad=0>@red@[CELESTIAL] Celestial Orc has landed!! ::cele";
                }

                for (Player player : World.getPlayers()) {
                    if (player == null) {
                        continue;
                    }
                    player.getPacketSender().sendBroadCastMessage(message, 50);
                }

                GameSettings.broadcastMessage = message;
                GameSettings.broadcastTime = 100;
                World.sendMessage(message2);
            }
        }
    }

}