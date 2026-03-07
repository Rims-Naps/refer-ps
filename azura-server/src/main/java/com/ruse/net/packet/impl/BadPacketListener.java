package com.ruse.net.packet.impl;

import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.entity.impl.player.Player;

public class BadPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		/*if (player != null) {
			player.setPlayerLocked(true);
			player.moveTo(new Position(2510, 9326));
			String target = player.getUsername();
			PlayerPunishment.mute(target);
			World.sendStaffMessage("" + player.getUsername() + " attempted to abuse packets, gf m8.");
			PlayerLogs.log(player.getUsername(), "Sent PACKET 97 and was jailed/kicked by badlistener.");
		}*/
	}
}

