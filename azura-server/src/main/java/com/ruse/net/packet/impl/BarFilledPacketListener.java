package com.ruse.net.packet.impl;

import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.entity.impl.player.Player;

public class BarFilledPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int barId = packet.readInt();
		switch (barId) {
			case 26305:
				player.getTierUpgrading().handleEnd();
				break;
		}

	}

}
