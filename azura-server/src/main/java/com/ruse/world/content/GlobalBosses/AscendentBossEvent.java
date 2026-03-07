package com.ruse.world.content.GlobalBosses;

import com.ruse.GameSettings;
import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.TimeUnit;

public class AscendentBossEvent {

    public static NPC currentBoss = null;
    public static boolean isAlive = false;
    // Negative because if you make it zero it will increase
    // before the world is created and it wont spawn on server boot for 3 hrs
    public static int tick = -11_000;

    public static Position SPAWN_POINT = new Position(2655, 3540,0);

    public static int[] BOSS_IDS = {
            557
    };

    public static String timeLeft() {

        int ticks = 11_000 - (tick % 11_000);
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

        if (!isAlive && tick % 11_000 == 10_970) {
            String message = "Ascendant Berserker will spawn in 30 seconds!";
            World.sendMessage(message);
        }
        if (!isAlive && tick % 11_000 == 10_940) {
            String message = "Ascendant Berserker will spawn in 1 minute!";
            World.sendMessage(message);
        }

        if (!isAlive && tick % 11_000 == 10_500) {
            String message = "Ascendant Berserker will spawn in 5 minutes!";
            World.sendMessage(message);
        }

        if (tick % 11_000 == 0) {
            if(currentBoss == null || currentBoss.isDying() || !currentBoss.isRegistered() && !isAlive) {
                int boss = BOSS_IDS[Misc.random(BOSS_IDS.length - 1)];
                NPC npc = new NPC(boss, SPAWN_POINT);
                currentBoss = npc;
                isAlive = true;
                World.register(npc);
                String message = "";
                String message2 = "";

                if (boss == 557) {
                    message = "Ascendant Berserker has spawned!";
                    message2 = "<shad=0>@red@[ASCENDANT] Ascendant Berserker has landed!! ::asc";
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