package com.ruse.world.content;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

public class DissolveBossSpawner
{
    @Setter@Getter
    public static boolean isSpawning = false;
    public static void startDissolveBoss(final Player p) {
        if (isSpawning)
            return;
        setSpawning(true);

        TaskManager.submit(new Task(1, p, false) {
            int tick = 0;
            @Override
            public void execute() {
                if(tick == 0) {
                    World.sendMessage("@red@<shad=0>The Void Rift will open in 1 minute!");
                }
                if(tick == 50) {
                    World.sendMessage("@red@<shad=0>The Void Rift will open in 30 seconds");
                }
                if(tick == 80) {
                    World.sendMessage("@red@<shad=0>The Void Rift will open in 10 seconds");
                }
                if(tick >= 100) {
                    if (Misc.random(0, 10) == 0) {
                        CorruptRiftEvent.isAlive = false;
                        CorruptRiftEvent.spawnedCount = 0;
                        CorruptRiftEvent.eventstarted = false;
                        CorruptRiftEvent.spawnNextBoss();
                        setSpawning(false);
                        this.stop();
                    } else {
                        RiftEvent.isAlive = false;
                        RiftEvent.spawnedCount = 0;
                        RiftEvent.eventstarted = false;
                        RiftEvent.spawnNextBoss();
                        setSpawning(false);
                        this.stop();
                    }
                }
                tick++;
            }
        });
    }
}
