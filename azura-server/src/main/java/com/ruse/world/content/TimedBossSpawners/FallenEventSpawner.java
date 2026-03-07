package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class FallenEventSpawner
{
    public static void StartFallenEvent(final Player p) {
        TaskManager.submit(new Task(1, p, false) {
            int tick = 0;
            @Override
            public void execute() {
                if(tick == 0) {
                    World.sendMessage("@red@<shad=0>The Fallen Event will begin in 1 Minute ::fallen");
                }
                if(tick == 50) {
                    World.sendMessage("@red@<shad=0>The Fallen Event will begin in 30 seconds ::fallen");
                }
                if(tick == 80) {
                    World.sendMessage("@red@<shad=0>The Fallen Event will begin in 10 seconds ::fallen");
                }
                if(tick >= 100) {
                    NPC n = new NPC(2097, new Position(2274, 3167, 0));
                    World.register(n);
                    World.sendMessage("@red@<shad=0>The Fallen Event has begun! ::fallen");
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }

                        players.getPacketSender().sendBroadCastMessage("The Fallen Event has begun! ::fallen", 50);

                    }
                    this.stop();
                }
                tick++;
            }
        });
    }
}
