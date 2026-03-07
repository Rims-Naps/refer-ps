package com.ruse.world.content.TimedBossSpawners;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class AquaBossSpawner {

    public static boolean eventstarted;
    public static Position PORTAL_SPOT = new Position(2273, 4038, 4);
    public static NPC portal = new NPC(5040, new Position(PORTAL_SPOT));


    public static void startAquaBoss(final Player p, boolean end) {
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
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4000 || players.getSlayer().getSlayerTask().getNpcId() == 3004
                                || players.getSlayer().getSlayerTask().getNpcId() == 6306 || players.getSlayer().getSlayerTask().getNpcId() == 1737
                                || players.getSlayer().getSlayerTask().getNpcId() == 185 || players.getSlayer().getSlayerTask().getNpcId() == 3005
                                || players.getSlayer().getSlayerTask().getNpcId() == 352 || players.getSlayer().getSlayerTask().getNpcId() == 452
                                || players.getSlayer().getSlayerTask().getNpcId() == 1726) {
                            players.sendMessage("<shad=0>@blu@Aqua Boss will spawn at the center of the Aqua Dungeon in 1 Minute!");
                        }
                    }
                }

                if (tick == 50) {
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4000 || players.getSlayer().getSlayerTask().getNpcId() == 3004
                                || players.getSlayer().getSlayerTask().getNpcId() == 6306 || players.getSlayer().getSlayerTask().getNpcId() == 1737
                                || players.getSlayer().getSlayerTask().getNpcId() == 185 || players.getSlayer().getSlayerTask().getNpcId() == 3005
                                || players.getSlayer().getSlayerTask().getNpcId() == 352 || players.getSlayer().getSlayerTask().getNpcId() == 452
                                || players.getSlayer().getSlayerTask().getNpcId() == 1726) {
                            players.sendMessage("<shad=0>@blu@Aqua Boss will spawn at the center of the Aqua Dungeon in 30 Seconds!");
                        }

                    }
                }

                if (tick == 80) {
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4000 || players.getSlayer().getSlayerTask().getNpcId() == 3004
                                || players.getSlayer().getSlayerTask().getNpcId() == 6306 || players.getSlayer().getSlayerTask().getNpcId() == 1737
                                || players.getSlayer().getSlayerTask().getNpcId() == 185 || players.getSlayer().getSlayerTask().getNpcId() == 3005
                                || players.getSlayer().getSlayerTask().getNpcId() == 352 || players.getSlayer().getSlayerTask().getNpcId() == 452
                                || players.getSlayer().getSlayerTask().getNpcId() == 1726) {
                            players.sendMessage("<shad=0>@blu@Aqua Boss will spawn at the center of the Aqua Dungeon in 10 Seconds!");
                        }
                    }
                }

                if (tick >= 100) {
                    NPC n = new NPC(8000, new Position(2273, 4038, 0));
                    World.register(n);
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4000 || players.getSlayer().getSlayerTask().getNpcId() == 3004
                                || players.getSlayer().getSlayerTask().getNpcId() == 6306 || players.getSlayer().getSlayerTask().getNpcId() == 1737
                                || players.getSlayer().getSlayerTask().getNpcId() == 185 || players.getSlayer().getSlayerTask().getNpcId() == 3005
                                || players.getSlayer().getSlayerTask().getNpcId() == 352 || players.getSlayer().getSlayerTask().getNpcId() == 452
                                || players.getSlayer().getSlayerTask().getNpcId() == 1726) {
                            players.sendMessage("<shad=0>@blu@Aqua Boss spawned at the Center of the Aqua Dungeon!");

                        }

                    }

                    stop();
                }
                tick++;
            }
        });
    }
}

