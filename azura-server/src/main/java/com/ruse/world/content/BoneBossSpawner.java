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

public class BoneBossSpawner {
    public static void startBoneBossEvent(final Player p) {
        TaskManager.submit(new Task(1, p, false) {
            int tick = 0;
            @Override
            public void execute() {
                if(tick == 0) {
                    World.sendMessage("@red@<shad=0>The Skeletal Demon will spawn in 1 Minute ::Demon");
                    DiscordManager.sendMessage("The Skeletal Demon has been summoned! ::Demon <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);

                }
                if(tick == 50) {
                    World.sendMessage("@red@<shad=0>The Skeletal Demon will spawn in 30 seconds ::Demon");
                }
                if(tick == 80) {
                    World.sendMessage("@red@<shad=0>The Skeletal Demon will spawn in 10 seconds ::Demon");
                }
                if(tick >= 100) {
                    NPC n = new NPC(3307, new Position(1823, 4510, 0));
                    World.register(n);
                    World.sendMessage("@red@<shad=0>The Skeletal Demon has been summoned! ::Demon");
                    DiscordManager.sendMessage("The Skeletal Demon has been summoned! ::Demon <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }

                        players.getPacketSender().sendBroadCastMessage("The Skeletal Demon has been summoned! ::Demon", 50);

                    }
                    this.stop();
                }
                tick++;
            }
        });
    }
}