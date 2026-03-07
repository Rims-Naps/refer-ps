package com.ruse.world.content;

import com.ruse.donation.ServerCampaignHandler;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Graphic;
import com.ruse.model.Position;
import com.ruse.util.TimeUtils;
import com.ruse.world.World;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class DonobossSpawner
{
    @Setter@Getter
    public static boolean isSpawning = false;
    public static void startDonoboss(final Player p) {
        TaskManager.submit(new Task(1, p, false) {
            int tick = ServerCampaignHandler.getLastDeath().plusMinutes(1).isAfter(LocalDateTime.now()) ? 80 : 0;
            @Override
            public void execute() {
                if(tick == 0) {
                    DiscordManager.sendMessage("Owner Boss will spawn in 1 Minute ::donoboss <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                    World.sendMessage("@red@<img=868><shad=0>Owner Boss will spawn in 1 Minute ::donoboss");
                }
                if(tick == 50) {
                    World.sendMessage("@red@<img=868><shad=0>Owner Boss will spawn in 30 seconds ::donoboss");
                }
                if(tick == 80) {
                    World.sendMessage("@red@<img=868><shad=0>Owner Boss will spawn in 10 seconds ::donoboss");
                }
                if(tick >= 100) {
                    NPC n = new NPC(2341, new Position(3617, 3356, 0));
                    World.register(n);
                    DiscordManager.sendMessage("Owner Boss has been summoned! ::donoboss <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                    World.sendMessage("@red@<img=868><shad=0>Owner Boss has been summoned! ::donoboss");
                    for (Player players : World.getPlayers()) {
                        if (players == null) {
                            continue;
                        }

                        players.getPacketSender().sendBroadCastMessage("Owner Boss has been summoned! ::donoboss", 50);

                    }
                    this.stop();
                }
                tick++;
            }
        });
    }
}
