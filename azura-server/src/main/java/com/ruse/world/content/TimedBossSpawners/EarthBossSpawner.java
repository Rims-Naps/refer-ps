package com.ruse.world.content.TimedBossSpawners;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class EarthBossSpawner {
    public static Position PORTAL_SPOT = new Position(3332, 4134, 4);
    public static NPC portal = new NPC(5040, new Position(PORTAL_SPOT));

    public static boolean eventstarted;

    public static void startEarthBoss(final Player p, boolean end) {
        if (end) {
            if (portal != null && portal.getPosition().getX() == 2180 && portal.getPosition().getY() == 4751 && portal.getPosition().getZ() == 4 && portal.getId() == 5040) {
                World.deregister(portal);
                return;
            }
        }
        TaskManager.submit(new Task(1, p, false) {
            int tick = 0;

            @Override
            public void execute() {
                if (tick == 0) {
                    World.register(portal);
                    eventstarted = true;
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 450 || players.getSlayer().getSlayerTask().getNpcId() == 188
                                || players.getSlayer().getSlayerTask().getNpcId() == 3688 || players.getSlayer().getSlayerTask().getNpcId() == 9846
                                || players.getSlayer().getSlayerTask().getNpcId() == 203 || players.getSlayer().getSlayerTask().getNpcId() == 5002
                                || players.getSlayer().getSlayerTask().getNpcId() == 8010 || players.getSlayer().getSlayerTask().getNpcId() == 1739
                                || players.getSlayer().getSlayerTask().getNpcId() == 5080){
                            players.sendMessage("<shad=0>@blu@Gaia Boss will spawn at the center of the Earth Dungeon in 1 Minute!");
                        }
                    }
                }

                if (tick == 50) {
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 450 || players.getSlayer().getSlayerTask().getNpcId() == 188
                                || players.getSlayer().getSlayerTask().getNpcId() == 3688 || players.getSlayer().getSlayerTask().getNpcId() == 9846
                                || players.getSlayer().getSlayerTask().getNpcId() == 203 || players.getSlayer().getSlayerTask().getNpcId() == 5002
                                || players.getSlayer().getSlayerTask().getNpcId() == 8010 || players.getSlayer().getSlayerTask().getNpcId() == 1739
                                || players.getSlayer().getSlayerTask().getNpcId() == 5080){
                            players.sendMessage("<shad=0>@blu@Gaia Boss will spawn at the center of the Earth Dungeon in 1 Minute!");
                        }

                    }
                }

                if (tick == 80) {
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 450 || players.getSlayer().getSlayerTask().getNpcId() == 188
                                || players.getSlayer().getSlayerTask().getNpcId() == 3688 || players.getSlayer().getSlayerTask().getNpcId() == 9846
                                || players.getSlayer().getSlayerTask().getNpcId() == 203 || players.getSlayer().getSlayerTask().getNpcId() == 5002
                                || players.getSlayer().getSlayerTask().getNpcId() == 8010 || players.getSlayer().getSlayerTask().getNpcId() == 1739
                                || players.getSlayer().getSlayerTask().getNpcId() == 5080){
                            players.sendMessage("<shad=0>@blu@Gaia Boss will spawn at the center of the Earth Dungeon in 1 Minute!");
                        }
                    }
                }

                if (tick >= 100) {
                    NPC n = new NPC(8004, new Position(3332, 4134, 0));
                    World.register(n);
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 450 || players.getSlayer().getSlayerTask().getNpcId() == 188
                                || players.getSlayer().getSlayerTask().getNpcId() == 3688 || players.getSlayer().getSlayerTask().getNpcId() == 9846
                                || players.getSlayer().getSlayerTask().getNpcId() == 203 || players.getSlayer().getSlayerTask().getNpcId() == 5002
                                || players.getSlayer().getSlayerTask().getNpcId() == 8010 || players.getSlayer().getSlayerTask().getNpcId() == 1739
                                || players.getSlayer().getSlayerTask().getNpcId() == 5080){
                            players.sendMessage("<shad=0>@blu@Gaia Boss will spawn at the center of the Earth Dungeon in 1 Minute!");
                        }

                    }
                    stop();
                }
                tick++;
            }
        });
    }
}
