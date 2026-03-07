package com.ruse.world.content;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class VoteBossSpawner
{
    public static void startVoteBoss(final Player p) {
        p.getPacketSender().sendInterfaceRemoval();

        TaskManager.submit(new Task(1, p, false) {
            int tick = 0;
            @Override
            public void execute() {
                if(tick == 0) {
                    World.sendMessage("@red@<shad=0>Voteboss will spawn in 1 Minute ::Voteboss");
                }
                if(tick == 50) {
                    World.sendMessage("@red@<shad=0>Voteboss will spawn in 30 seconds ::Voteboss");
                }
                if(tick == 80) {
                    World.sendMessage("@red@<shad=0>Voteboss will spawn in 10 seconds ::Voteboss");
                }
                if(tick >= 100) {
                    NPC n = new NPC(2342, new Position(2849, 5089, 0));
                    World.register(n);
                    World.sendMessage("@red@<shad=0>Voteboss has been summoned! ::Voteboss");
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }

                        players.getPacketSender().sendBroadCastMessage("Voteboss has been summoned! ::Voteboss", 50);

                    }
                    this.stop();
                }
                tick++;
            }
        });
    }
}
