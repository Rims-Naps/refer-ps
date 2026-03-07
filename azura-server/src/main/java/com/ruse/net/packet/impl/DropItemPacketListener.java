package com.ruse.net.packet.impl;

import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.Sounds;
import com.ruse.world.content.Sounds.Sound;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.GroundItemManager;
import com.ruse.world.entity.impl.player.Player;

/**
 * This packet listener is called when a player drops an item they have placed
 * in their inventory.
 * 
 * @author relex lawl
 */

public class DropItemPacketListener implements PacketListener {

	@Override
	public void handleMessage(Player player, Packet packet) {
		int id = packet.readUnsignedShortA();
		int interfaceIndex = packet.readUnsignedShort();
		int itemSlot = packet.readUnsignedShortA();
		if (player.getConstitution() <= 0 || player.getInterfaceId() > 0)
			return;
		if (itemSlot < 0 || itemSlot > player.getInventory().capacity())
			return;
		if (player.getConstitution() <= 0 || player.isTeleporting())
			return;
		if (player.isPlayerLocked())
			return;
		Item item = player.getInventory().getItems()[itemSlot];
		if (item == null)
			return;
		if (item.getId() != id) {
			return;
		}
		if (!player.getControllerManager().canDropItem(item)) {
			return;
		}

		player.getPacketSender().sendInterfaceRemoval();
		player.getCombatBuilder().cooldown(false);
		if (player.getLocation() != null && player.getLocation() == Location.DUEL_ARENA) {
			player.getPacketSender().sendMessage("You cannot drop items here");
			return;
		}
		switch (item.getId()) {
			case 19622:
				if (!player.getClickDelay().elapsed(1000)) {
					return;
				}
				player.getClickDelay().reset();

				player.setCurseRunes(player.getCurseRunes() + player.getInventory().getAmount(20010));
				player.getInventory().delete((20010), player.getInventory().getAmount(20010));
				player.setSoulRunes(player.getSoulRunes() + player.getInventory().getAmount(20014));
				player.getInventory().delete((20014), player.getInventory().getAmount(20014));
				player.setCryptRunes(player.getCryptRunes() + player.getInventory().getAmount(20015));
				player.getInventory().delete((20015), player.getInventory().getAmount(20015));
				player.setShadowRunes(player.getShadowRunes() + player.getInventory().getAmount(20012));
				player.getInventory().delete((20012), player.getInventory().getAmount(20012));
				player.setWraithRunes(player.getWraithRunes() + player.getInventory().getAmount(20011));
				player.getInventory().delete((20011), player.getInventory().getAmount(20011));
				player.setVoidRunes(player.getVoidRunes() + player.getInventory().getAmount(20013));
				player.getInventory().delete((20013), player.getInventory().getAmount(20013));
				break;
			case 10946:
			case 23057:
			case 23058:
			case 23059:
			case 23060:
				player.getPacketSender().sendMessage("@red@You can not drop bonds, you can only trade via player owned shops!");
				return;
		}
		if (item.getId() != -1 && item.getAmount() >= 1) {
			if (item.tradeable()) {
				player.getDissolving().handle(item.getId(), item.getAmount(), true);
			} else
				player.getDissolving().handle(item.getId(), item.getAmount(), true);
		}
	}

	public static void destroyItemInterface(Player player, Item item) {// Destroy item created by Remco
		player.setUntradeableDropItem(item);
		String[][] info = { // The info the dialogue gives
				{ "Are you sure you want to drop this item?", "14174" }, { "Yes", "14175" }, { "No", "14176" },
				{ "", "14177" }, { "This item will be permanently deleted", "14182" },
				{ "You cannot get it back if deleted", "14183" }, { item.getDefinition().getName(), "14184" } };
		player.getPacketSender().sendItemOnInterface(14171, item.getId(), 0, item.getAmount());
		for (int i = 0; i < info.length; i++)
			player.getPacketSender().sendString(Integer.parseInt(info[i][1]), info[i][0]);
		player.getPacketSender().sendChatboxInterface(14170);
	}
}
