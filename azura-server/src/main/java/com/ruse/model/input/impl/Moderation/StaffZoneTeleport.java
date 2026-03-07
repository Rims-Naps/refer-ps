package com.ruse.model.input.impl.Moderation;

import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;

public class StaffZoneTeleport {
    public static void teleport(Player player) {
        TeleportHandler.teleportPlayer(player, new Position(3167, 3545, 4), TeleportType.NORMAL);
    }

    public static void teleportAll(Player player) {
        player.getPacketSender().sendMessage("Teleporting all staff to staffzone.");
        for (Player players : World.getPlayers()) {
            if (players != null) {
                if (players.getRights().isStaff()) {
                    TeleportHandler.teleportPlayer(players, new Position(3167, 3545, 4), TeleportType.NORMAL);
                }
            }
        }
    }
}
