package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.TimeUnit;

public class StarterEvent {

    public static NPC currentBoss = null;

    // Negative because if you make it zero it will increase
    // before the world is created and it wont spawn on server boot for 3 hrs
    public static int tick = -150;
    public static boolean isAlive = false;

    public static Position SPAWN_POINT = new Position(2911, 2848,0);

    public static int[] BOSS_IDS = {
            1880
    };

    public static String timeLeft() {
        int ticks = 3000 - (tick % 3000);
        ticks /= 100;
        ticks *= 60;

        long ms = ticks ;
        String  m = String.format("%dh %dm", TimeUnit.SECONDS.toHours(ms),
                TimeUnit.SECONDS.toMinutes(ms) - TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(ms)),
                TimeUnit.SECONDS.toSeconds(ms) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(ms)));

        if (tick < 0) {
            m = "@yel@GET READY!";
        }
        return m;
    }

    public static void sequence() {
        tick++;


        if (tick % 3000 == 0) {
            // Only if its dead
            if(currentBoss == null || currentBoss.isDying() || !currentBoss.isRegistered()) {
                int boss = BOSS_IDS[Misc.random(BOSS_IDS.length - 1)];
                NPC npc = new NPC(boss, SPAWN_POINT);
                currentBoss = npc;
                isAlive = true;
                World.register(npc);

//2A110D
                String message = "";
                String message2 = "";

                if (boss == 1880)
                    message = "Starter Boss is alive!! ::sb";
                message2 = "<img=5604><shad=6C1894>@bla@[STARTER]<img=5604><shad=6C1894><col=6C1894> Tar'oroth has Respawned Defeat him now ::sb";


                for (Player players : World.getPlayers()) {
                    if (players == null) {
                        continue;
                    }
                    players.getPacketSender().sendBroadCastMessage(message, 50);

                }
                GameSettings.broadcastMessage = message;
                GameSettings.broadcastTime = 100;
                World.sendMessage(message2);
            }
        }
    }

}