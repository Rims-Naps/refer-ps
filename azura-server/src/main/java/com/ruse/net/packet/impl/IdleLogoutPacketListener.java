package com.ruse.net.packet.impl;

import com.ruse.model.Locations;
import com.ruse.model.PlayerRights;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

//CALLED EVERY 3 MINUTES OF INACTIVITY

public class IdleLogoutPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {

		if (player.getLocation() == Locations.Location.AFKBOSS){
			return;
		}

		if (player.getCombatBuilder().isAttacking()) {
			player.setHouseServant(player.getHouseServant() + 1);
			player.sendMessage(player.getHouseServant() + "");
			if (player.getHouseServant() % 4 ==0) {
				World.sendStaffMessage("<shad=0>@red@> [AFK]@blu@ " + player.getUsername() + " has been idled for more than @red@" + (int)(2.5D * player.getHouseServant()) + "@blu@ minutes (IN COMBAT). ");
				if (player.getHouseServant() % 8 == 0){
					player.getAfkCombatChecker().openQuestions();
				}
			}
		}
		player.setInactive(true);
	}
}
