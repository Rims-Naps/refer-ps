package com.ruse.net.packet;

import com.ruse.world.entity.impl.player.Player;

public class UpgradeInterfaceSpinEndPacketListener implements PacketListener {
    @Override
    public void handleMessage(Player player, Packet packet) {
        int index = packet.readUnsignedShort();
        player.getItemUpgrader().onFinish(index);
    }
}