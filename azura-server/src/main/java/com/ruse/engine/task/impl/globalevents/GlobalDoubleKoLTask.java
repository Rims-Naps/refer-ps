package com.ruse.engine.task.impl.globalevents;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.world.World;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;

public class GlobalDoubleKoLTask extends Task {

    public GlobalDoubleKoLTask() {
        super(1, 0, false);
    }

    int timer = 0;

    @Override
    protected void execute() {
        if (timer < 1) {
            timer = 6000;
            World.sendMessage("@red@<shad=0>[SOULBANE] <col=AF70C3>SoulBane 2x tickets event has started for 60 minutes!");
            DiscordManager.sendMessage("[SOULBANE] SoulBane 2x tickets event has started for 60 minutes! <@&1475675674311135243>" , Channels.ACTIVE_EVENTS);

            GameSettings.DOUBLE_NECRO_POINTS = true;
        } else if(timer == 1) {
            World.sendMessage("@red@<shad=0>[SOULBANE] <col=AF70C3>SoulBane 2x tickets event has ended!");
            DiscordManager.sendMessage("[SOULBANE] SoulBane 2x tickets event has ended! <@&1475675674311135243>" , Channels.ACTIVE_EVENTS);
            GameSettings.DOUBLE_NECRO_POINTS = false;
            this.stop();
        } else {
            timer--;
        }
    }
}
