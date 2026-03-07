package com.ruse.net.packet.impl;

import com.ruse.model.PlayerRights;
import com.ruse.model.definitions.NPCDrops;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.content.DropsInterface;
import com.ruse.world.entity.impl.player.Player;

public class ExamineNpcPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int npc = packet.readShort();
		if (npc <= 0) {
			return;
		}
		NpcDefinition npcDef = NpcDefinition.forId(npc);
		if (player.getRights() == PlayerRights.DEVELOPER) {
			player.getPA().sendMessage("NPC ID: " + npc);
		}
		if (npcDef != null) {
			if (npcDef.isAttackable()) {
				if (NPCDrops.forId(npcDef.getId()) == null) {
					player.sendMessage("This NPC doesn't have drops.");
					return;
				}
				player.getPacketSender().sendMessage(npcDef.getExamine());
				DropsInterface.open(player);
				player.setCurrentNpcId(npcDef.getId()); // Store the npcId
				DropsInterface.buildRightSide(player, npcDef.getId());
			}
		}
	}

}
