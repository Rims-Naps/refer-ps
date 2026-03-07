package com.ruse.world.content.TimedBossSpawners;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class LavaBossSpawner {
    public static boolean eventstarted;
    public static Position PORTAL_SPOT = new Position(2180, 4751, 4);
    public static NPC portal = new NPC(5040, new Position(PORTAL_SPOT));

    public static void startLavaBoss(final Player p, boolean end) {
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
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4001 || players.getSlayer().getSlayerTask().getNpcId() == 606
                                || players.getSlayer().getSlayerTask().getNpcId() == 9838 || players.getSlayer().getSlayerTask().getNpcId() == 1738
                                || players.getSlayer().getSlayerTask().getNpcId() == 603 || players.getSlayer().getSlayerTask().getNpcId() == 202
                                || players.getSlayer().getSlayerTask().getNpcId() == 928 || players.getSlayer().getSlayerTask().getNpcId() == 201
                                || players.getSlayer().getSlayerTask().getNpcId() == 1725){
                            players.sendMessage("<shad=0>@blu@Lava Boss will spawn at the center of the Lava Dungeon in 1 Minute!");
                        }
                    }
                }

                if (tick == 50) {
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4001 || players.getSlayer().getSlayerTask().getNpcId() == 606
                                || players.getSlayer().getSlayerTask().getNpcId() == 9838 || players.getSlayer().getSlayerTask().getNpcId() == 1738
                                || players.getSlayer().getSlayerTask().getNpcId() == 603 || players.getSlayer().getSlayerTask().getNpcId() == 202
                                || players.getSlayer().getSlayerTask().getNpcId() == 928 || players.getSlayer().getSlayerTask().getNpcId() == 201
                                || players.getSlayer().getSlayerTask().getNpcId() == 1725){
                            players.sendMessage("<shad=0>@blu@Lava Boss will spawn at the center of the Lava Dungeon in 30 Seconds!");
                        }

                    }
                }

                if (tick == 80) {
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4001 || players.getSlayer().getSlayerTask().getNpcId() == 606
                                || players.getSlayer().getSlayerTask().getNpcId() == 9838 || players.getSlayer().getSlayerTask().getNpcId() == 1738
                                || players.getSlayer().getSlayerTask().getNpcId() == 603 || players.getSlayer().getSlayerTask().getNpcId() == 202
                                || players.getSlayer().getSlayerTask().getNpcId() == 928 || players.getSlayer().getSlayerTask().getNpcId() == 201
                                || players.getSlayer().getSlayerTask().getNpcId() == 1725){
                            players.sendMessage("<shad=0>@blu@Lava Boss will spawn at the center of the Lava Dungeon in 10 Seconds!");
                        }
                    }
                }

                if (tick >= 100) {
                    NPC n = new NPC(8002, new Position(2180, 4751, 0));
                    World.register(n);

                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }
                        if (players.getSlayer().getSlayerTask().getNpcId() == 4001 || players.getSlayer().getSlayerTask().getNpcId() == 606
                                || players.getSlayer().getSlayerTask().getNpcId() == 9838 || players.getSlayer().getSlayerTask().getNpcId() == 1738
                                || players.getSlayer().getSlayerTask().getNpcId() == 603 || players.getSlayer().getSlayerTask().getNpcId() == 202
                                || players.getSlayer().getSlayerTask().getNpcId() == 928 || players.getSlayer().getSlayerTask().getNpcId() == 201
                                || players.getSlayer().getSlayerTask().getNpcId() == 1725){
                            players.sendMessage("<shad=0>@blu@Lava Boss spawned at the Center of the Lava Dungeon!");
                        }

                    }
                    stop();
                }
                tick++;
            }
        });
    }
}
