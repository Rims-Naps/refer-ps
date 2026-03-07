package com.ruse.net.packet.impl;

import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.entity.impl.player.Player;

public class CreationMenuPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int itemId = packet.readInt();
        int amount = packet.readUnsignedByte();
        if (amount == 28)
            amount = 100;
        if (player.getCreationMenu() != null) {
            player.getCreationMenu().execute(itemId, amount);
        }
    }
}
