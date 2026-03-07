package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.ServerSaves.DissolveAmountUpdater;
import com.ruse.world.World;
import com.ruse.world.content.DissolveBossSpawner;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.List;

public class DissolveBossHandler {

    private static int AMOUNT_NEEDED = 2000;

    public static void increase(Player player, int amount) {

        if (amount > 2000) {
            amount = 2000;
        }

        GameSettings.DISSOLVE_AMOUNT = GameSettings.DISSOLVE_AMOUNT + amount;
        DissolveAmountUpdater.updateDissolveAmount(GameSettings.DISSOLVE_AMOUNT);

        if (GameSettings.DISSOLVE_AMOUNT >= AMOUNT_NEEDED /2 && !GameSettings.EVENT_ALERT_SENT){
            World.sendMessage("@red@<shad=0>[RIFT] The Void rift is halfway opened, salvage gear to start the event!");
            DiscordManager.sendMessage("[RIFT] The Void rift is halfway opened, salvage gear to start the event! <@&1475675670196654080>", Channels.ACTIVE_EVENTS);

            GameSettings.EVENT_ALERT_SENT = true;
        }

        if (GameSettings.DISSOLVE_AMOUNT >= AMOUNT_NEEDED) {
            DissolveBossSpawner.startDissolveBoss(player);
            DissolveBossSpawner.setSpawning(false);
            GameSettings.DISSOLVE_AMOUNT = 0;
            GameSettings.EVENT_ALERT_SENT = false;
            DissolveAmountUpdater.updateDissolveAmount(GameSettings.DISSOLVE_AMOUNT);
        }
    }
}