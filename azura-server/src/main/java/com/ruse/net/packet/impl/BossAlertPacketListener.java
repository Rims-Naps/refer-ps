package com.ruse.net.packet.impl;

import com.ruse.GameSettings;
import com.ruse.model.Position;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;

public class BossAlertPacketListener implements PacketListener {
    @Override
    public void handleMessage(Player player, Packet packet) {
        int id = packet.readShort();

        switch (id) {
            case 5604:
                //TeleportHandler.teleportPlayer(player, new Position(2135,5015), TeleportType.NORMAL);//CHAOS
                break;
            case 5603:
               // TeleportHandler.teleportPlayer(player, new Position(1955,4704), TeleportType.NORMAL);/GUARDIAN
                break;
            case 5601:
               // TeleportHandler.teleportPlayer(player, new Position(2974,2777), TeleportType.NORMAL);//VOTEBOSS
                break;
            case 5602:
               // TeleportHandler.teleportPlayer(player, new Position(3178,3354), TeleportType.NORMAL);//CHRONOS
                break;
            case 5605:
     /*           int total = player.getPointsHandler().getNPCKILLCount();
                if (total <= 75000){
                    player.moveTo(GameSettings.HOME_CORDS);
                    player.sendMessage("@red@<shad=0>You need 75k NPC Kills to attack Galaxy Overlord!!");
                } else {
                    TeleportHandler.teleportPlayer(player, new Position(2916, 3608, 0), TeleportType.ANCIENT);
                    player.sendMessage("<col=AF70C3><shad=0>Defeat the Mighty Galaxy Overlord!!");

                }*/
                break;
            case 5606:
               // TeleportHandler.teleportPlayer(player, new Position(3126,3474), TeleportType.NORMAL);//ASURA
                break;
        }
    }
}
