package com.ruse.world.content.GlobalBosses;

import com.ruse.GameSettings;
import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.TimeUnit;

public class GalaxyEvent {

    public static NPC currentBoss = null;
    public static boolean isAlive = false;
    // Negative because if you make it zero it will increase
    // before the world is created and it wont spawn on server boot for 3 hrs
    public static int tick = -2000;

    public static Position SPAWN_POINT = new Position(2591, 3186,0);

    public static int[] BOSS_IDS = {
            9014
    };

    public static String timeLeft() {
        int ticks = 6000 - (tick % 6000);
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
        if (!isAlive && tick % 8500 == 6970) {
           // String message = "Overlord will spawn in 30 seconds!";
          //  World.sendMessage(message);
        }

        if (!isAlive && tick % 8500 == 6940) {
          //  String message = "Overlord will spawn in 1 minute!";
           // World.sendMessage(message);
        }

        if (!isAlive && tick % 8500 == 6900) {
           // String message = "Overlord will spawn in 5 minutes!";
           // World.sendMessage(message);
        }
        // Spawn every 20 minutes
        if (tick % 8500 == 0) {
            if(currentBoss == null || currentBoss.isDying() || !currentBoss.isRegistered() && !isAlive) {
                int boss = BOSS_IDS[Misc.random(BOSS_IDS.length - 1)];
                NPC npc = new NPC(boss, SPAWN_POINT);
                currentBoss = npc;
                isAlive = true;
                World.register(npc);
                System.out.println("Spawning galaxy");

//2A110D
                String message = ""; // u want to just use one and ill swap them? ok
                String message2 = "";

                if (boss == 9014) {
                    message = "Overlord is here!! 75k Kills Needed";
                    message2 = "<img=5603><shad=6C1894>@bla@[GLOBAL]<img=5603><shad=6C1894><col=6C1894> Galaxy Overlord has landed!! ::galaxy Requires 75k NPC kills";
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