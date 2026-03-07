package com.ruse.donation;

import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class DonationInterface {
    private static int tick = 0;
    public static void process() {
        tick++;
        if (tick % 8 == 0) {
            tick = 0;
            for (Player plr : World.getPlayers()) {
                if (plr != null) {
                    LifetimeStreakHandler.updateInterface(plr);
                    ServerCampaignHandler.updateInterface(plr);
                    ServerCampaignHandler.trySpawns(plr);
                    PersonalCampaignHandler.updateInterface(plr);
                }
            }
        }
    }
}
