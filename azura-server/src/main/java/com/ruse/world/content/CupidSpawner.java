/*
package com.ruse.world.content;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

public class CupidSpawner
{
    @Setter@Getter
    public static boolean isSpawning = false;
    public static void startCupid(final Player p) {
        if (isSpawning)
            return;
        setSpawning(true);

        TaskManager.submit(new Task(1, p, false) {
            int tick = 0;
            @Override
            public void execute() {
                if(tick == 0) {
                   // DiscordManager.sendMessage("Cosmic Cupid will spawn in 1 Minute ::cupid <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                    World.sendMessage("<col=AF70C3><img=868><shad=0>Cosmic Cupid will spawn in 1 Minute ::cupid");
                }
                if(tick == 50) {
                    World.sendMessage("<col=AF70C3><img=868><shad=0>Cosmic Cupid will spawn in 30 seconds ::cupid");
                }
                if(tick == 80) {
                    World.sendMessage("<col=AF70C3><img=868><shad=0>Cosmic Cupid will spawn in 10 seconds ::cupid");
                }
                if(tick >= 100) {
                    NPC n = new NPC(3000, new Position(2142, 3229, 1));
                    World.register(n);
                   // DiscordManager.sendMessage("Cosmic Cupid has been summoned! ::cupid <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                    World.sendMessage("<col=AF70C3><img=868><shad=0>Cosmic Cupid has been summoned! ::cupid");
                    this.stop();
                }
                tick++;
            }
        });
    }
}
*/
