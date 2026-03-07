package com.ruse.world.content.GlobalBosses;

import com.ruse.GameSettings;
import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.TimeUnit;

public class ArchonBossEvent {

    public static NPC currentBoss = null;
    public static boolean isAlive = false;

    public static int tick = -90_00;

    public static Position SPAWN_POINT = new Position(2399, 3540,0);

    public static int[] BOSS_IDS = {
            555
    };

    public static String timeLeft() {

        int ticks = 90_00 - (tick % 90_00);
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

        if (!isAlive && tick % 90_00 == 89_70) {
            String message = "Archonic C'thulu will spawn in 30 seconds!";
            World.sendMessage(message);
        }
        if (!isAlive && tick % 90_00 == 89_40) {
            String message = "Archonic C'thulu will spawn in 1 minute!";
            World.sendMessage(message);
        }

        if (!isAlive && tick % 90_00 == 85_00) {
            String message = "Archonic C'thulu will spawn in 5 minutes!";
            World.sendMessage(message);
        }

        if (tick % 90_00 == 0) {
            if(currentBoss == null || currentBoss.isDying() || !currentBoss.isRegistered() && !isAlive) {
                int boss = BOSS_IDS[Misc.random(BOSS_IDS.length - 1)];
                NPC npc = new NPC(boss, SPAWN_POINT);
                currentBoss = npc;
                isAlive = true;
                World.register(npc);
                String message = "";
                String message2 = "";

                if (boss == 555) {
                    message = "Archonic C'thulu has spawned!";
                    message2 = "<shad=0>@red@[ARCHON] Archonic C'thulu has landed!! ::archon";
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