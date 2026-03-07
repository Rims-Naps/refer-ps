package com.ruse.net.packet;

import com.ruse.PlayerSetting;
import com.ruse.world.entity.impl.player.Player;

public class ClientSettingsChangePacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        for (int i = 0; i < PlayerSetting.VALUES.length; i++) {
            PlayerSetting setting = PlayerSetting.getSetting(i);
            if (i < PlayerSetting.TOGGLEABLES) {
                player.getPlayerSettings()
                        .put(setting, packet.readUnsignedByte());
            } else {
                player.getPlayerSettings().put(setting, packet.readInt());
            }
        }
    }
}
