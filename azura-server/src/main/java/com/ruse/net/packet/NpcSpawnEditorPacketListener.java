package com.ruse.net.packet;

import com.ruse.npcspawneditor.NpcSpawnEditor;
import com.ruse.world.entity.impl.player.Player;

public class NpcSpawnEditorPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int id = packet.readUnsignedShort();
        int direction = packet.readUnsignedByte();
        int radius = packet.readUnsignedByte();
        int x = packet.readUnsignedShort();
        int y = packet.readUnsignedShort();
        int z = packet.readUnsignedShort();
        String format = packet.readString();
        if (id == 0xFFFF) {
            NpcSpawnEditor.removeSpawnDefinition(x);
        } else {
            NpcSpawnEditor.addSpawnDefinition(id, direction, radius, x, y, z, format);
        }
    }
}
