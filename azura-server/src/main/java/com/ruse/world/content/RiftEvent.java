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

public class RiftEvent {
    public static boolean eventstarted = false;
    public static boolean isAlive = false;
    public static NPC currentBoss = null;
    public static int spawnedCount = 0;
    public static Position PORTAL_SPOT = new Position(1505, 4963, 1);
    public static Position SPAWN_POINT = new Position(1504, 4957, 1);
    public static Position CHEST_SPOT = new Position(1504, 4960, 1);
    public static int[] BOSS_IDS = {
            6326,
            2745,
            8009,
            9006,
            1807,
            1084,
            601,
            4444
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
                String message = "A Void Rift has been opened near Athens Castle! ::rift";
                NPC portal = new NPC(1745, PORTAL_SPOT);
                eventstarted = true;
                World.register(portal);
                World.sendMessage("<@bla@<shad=9B0CFF> A Void Rift has been opened near Athens Castle! ::rift");
                World.sendMessage("NewAlert##ALERT##" + "A Void Rift has been opened near Athens Castle!");
                DiscordManager.sendMessage("A Void Rift has been opened near Athens Castle! ::rift <@&1475675670196654080>", Channels.ACTIVE_EVENTS);

                portal.performGraphic(new Graphic(190));
                portal.setPositionToFace(new Position(1504, 4959));
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
                EndRiftEvent();
                return;
            }

            /** trigger respawns in npcdeath tasknpc **/

            String message2 = "<shad=0>@red@" + npc.getDefinition().getName() + " @bla@<shad=9B0CFF>escaped from the Rift!";
            npc.performGraphic(new Graphic(190));
            World.sendMessage(message2);
            GameSettings.broadcastMessage = message2;
            GameSettings.broadcastTime = 100;
        }
    }

    public static void EndRiftEvent() {



        World.sendMessage("@bla@<shad=9B0CFF>The Rift has been Closed!");
        spawnedCount = 0;
        eventstarted = false;

        if (currentBoss != null) {
            World.deregister(currentBoss);
        }

        for (NPC portal : World.getNpcs()) {
            if (portal != null && portal.getPosition().getX() == 1505 && portal.getId() == 1745) {
                GameObject chest = new GameObject(406 , CHEST_SPOT);
                portal.performGraphic(new Graphic(1272));
                for (Player p: World.getNearbyPlayers(portal.getPosition(), 50)) {
       /*             p.getInventory().add(1542,1);
                    p.msgFancyPurp("You receive a Dream trinket for your efforts!");*/
                }

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
