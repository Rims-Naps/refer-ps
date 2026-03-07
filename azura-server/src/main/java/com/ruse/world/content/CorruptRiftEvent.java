package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.GameObject;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

/**
 * Credit: loyal,
 */

public class CorruptRiftEvent {
    public static boolean eventstarted = false;
    public static boolean isAlive = false;
    public static NPC currentBoss = null;
    public static int spawnedCount = 0;
    public static Position PORTAL_SPOT = new Position(3041, 2978, 1);
    public static Position SPAWN_POINT = new Position(3040, 2972, 1);
    public static Position CHEST_SPOT = new Position(3040, 2975, 1);
    public static int[] BOSS_IDS = {
            4412,
            4413,
            4414,
            4415
    };

    public static void spawnNextBoss() {
        if (currentBoss == null || currentBoss.isDying() || !currentBoss.isRegistered() && !isAlive) {
            int boss = BOSS_IDS[Misc.random(BOSS_IDS.length - 1)];
            NPC npc = new NPC(boss, SPAWN_POINT);
            currentBoss = npc;
            isAlive = true;
            World.register(npc);

            /** spawn portal and initial Boss Npc **/

            if (spawnedCount == 0) {
                String message = "A Corrupt Rift has been opened near Athens Castle! ::crift";
                NPC portal = new NPC(4420, PORTAL_SPOT);
                eventstarted = true;
                World.register(portal);
                World.sendMessage("@red@<shad=0> A Corrupt Rift has been opened near Athens Castle! ::crift");
                World.sendMessage("NewAlert##ALERT##" + "A Corrupt Rift has been opened near Athens Castle!");
                DiscordManager.sendMessage("A Corrupt Rift has been opened near Athens Castle! ::crift <@&1475675670196654080>", Channels.ACTIVE_EVENTS);

                portal.performGraphic(new Graphic(190));
                portal.setPositionToFace(new Position(3041, 2976));
                for (Player players : World.getPlayers()) {
                    if (players == null) {
                        continue;
                    }

                    players.getPacketSender().sendBroadCastMessage(message, 50);

                }
                GameSettings.broadcastMessage = message;
                GameSettings.broadcastTime = 100;
            }

            spawnedCount++;
            if (spawnedCount >= Misc.random(4, 8)) {
                EndCorruptEvent();
                return;
            }

            /** trigger respawns in npcdeath tasknpc **/

            String message2 = "<shad=0>@red@" + npc.getDefinition().getName() + " @bla@<shad=0>escaped from the Rift!";
            npc.performGraphic(new Graphic(190));
            World.sendMessage(message2);
            GameSettings.broadcastMessage = message2;
            GameSettings.broadcastTime = 100;
        }
    }

    public static void EndCorruptEvent() {



        World.sendMessage("@red@<shad=0>The Corrupt Rift has been Closed!");
        spawnedCount = 0;
        eventstarted = false;

        if (currentBoss != null) {
            World.deregister(currentBoss);
        }

        for (NPC portal : World.getNpcs()) {
            if (portal != null && portal.getPosition().getX() == 3041 && portal.getId() == 4420) {
                GameObject chest = new GameObject(406 , CHEST_SPOT);
                portal.performGraphic(new Graphic(1272));


                TaskManager.submit(new Task(3, false) {
                    @Override
                    public void execute() {
                        portal.performGraphic(new Graphic(1272));
                        World.deregister(portal);
                        CustomObjects.globalObjectRemovalTask(chest, 60);
                        this.stop();
                    }
                });
            }
        }
    }
}
