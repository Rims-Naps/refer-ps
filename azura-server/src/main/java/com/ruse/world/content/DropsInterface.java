package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.definitions.NPCDrops;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.model.input.impl.EnterSyntaxToSearchItemDrops;
import com.ruse.util.Misc;
import com.ruse.world.entity.impl.player.Player;
import lombok.var;

import java.util.ArrayList;
import java.util.List;

public class DropsInterface {

	public static int INTERFACE_ID = 33000;
	private static int SEARCHED_STRING = 33006, HEADER = 33005, SEARCHED_BUTTON = 132509, CLOSE_BUTTON = -32534, ITEM_MODEL = 34010,
			RATES_BUTTON = -30521, ITEM_NAME = 34100, ITEM_AMOUNT = 34200, ITEM_CHANCE = 34300, ITEM_VALUE = 34400,
			STRING_AMOUNT = 34003, STRING_CHANCE = 34004, STRING_VALUE = 34005,
			NPC_BUTTON_START = -32528, NPC_BUTTON_END = -32480;

	public static boolean SearchedNpcString(int i) {
		return i >= 33108 && i <= 33138;
	}

	public static boolean SearchedNpcButton(int i) {
		return i >= NPC_BUTTON_START && i <= NPC_BUTTON_END;
	}

	public static boolean handleButton(int id) {
		return (id >= CLOSE_BUTTON && id <= NPC_BUTTON_END) || id == SEARCHED_BUTTON;
	}

	private static int getAdjustedChance(Player player, int npcId, int i) {
		int chance = NPCDrops.forId(npcId).getDropList()[i].getChance();
		double drBonus = CustomDropUtils.drBonus(player, npcId);
		double divide = (drBonus / 100.0) + 1;
		return (int) (chance / divide);
	}

	public static void buildRightSide(Player player, int npcId) {
		player.getPacketSender().sendString(HEADER, " " + NpcDefinition.forId(npcId).getName());
		player.getPacketSender().sendString(STRING_AMOUNT, "");
		player.getPacketSender().sendString(STRING_CHANCE, "");
		player.getPacketSender().sendString(STRING_VALUE, "");
		player.setCurrentNpcId(npcId);

		int scrollAmount = 0;
		for (int i = 0; i < 80; i++) {
			if (i > NPCDrops.forId(npcId).getDropList().length - 1) continue;
			scrollAmount++;
		}
		player.getPacketSender().setScrollBar(34000, 37 * scrollAmount);

		for (int i = 0; i < 80; i++) {
			if (i > NPCDrops.forId(npcId).getDropList().length - 1) {
				player.getPacketSender().sendItemOnInterface(ITEM_MODEL + i, -1, 0, 1);
				player.getPacketSender().sendString(ITEM_NAME + i, "");
				player.getPacketSender().sendString(ITEM_AMOUNT + i, "");
				player.getPacketSender().sendString(ITEM_CHANCE + i, "");
				player.getPacketSender().sendString(ITEM_VALUE + i, "");
			} else {
				Item item = NPCDrops.forId(npcId).getDropList()[i].getItem();
				if (item.getDefinition() == null) continue;

				int min = NPCDrops.forId(npcId).getDropList()[i].getCount()[0];
				int amount = NPCDrops.forId(npcId).getDropList()[i].getCount()[NPCDrops.forId(npcId).getDropList()[i].getCount().length - 1];
				int chance = NPCDrops.forId(npcId).getDropList()[i].getChance();
				int adjustedChance = getAdjustedChance(player, npcId, i);

				player.getPacketSender().sendItemOnInterface(ITEM_MODEL + i, item.getId(), 0, amount);
				player.getPacketSender().sendString(ITEM_NAME + i, item.getDefinition().getName());
				player.getPacketSender().sendString(ITEM_AMOUNT + i, (min == amount ? Misc.formatNumber(amount) : Misc.formatNumber(min) + "-" + Misc.formatNumber(amount)));
				player.getPacketSender().sendString(ITEM_CHANCE + i, "1/" + (player.isShowMyChance() ? (adjustedChance == 0 ? "1" : adjustedChance) : (chance == 0 ? "1" : chance)));
				player.getPacketSender().sendString(ITEM_VALUE + i, Misc.format(amount * item.getDefinition().getValue()) + "");
			}
		}
	}

	public static void handleButtonClick(Player player, int id) {
		if (id == CLOSE_BUTTON) {
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		if (id == RATES_BUTTON) {
			player.sendMessage("Rates button clicked.");
		}
		if (SearchedNpcButton(id)) {
			int index = (id - NPC_BUTTON_START);
			if (index > 0) index /= 3;

			if (player.getLootList() != null && player.getLootList().size() > index) {
				Integer npcId = player.getLootList().get(index);
				if (npcId != null) {
					buildRightSide(player, npcId);
				}
			}
			return;
		}

		if (id == SEARCHED_BUTTON) {
			resetLeft(player);
			player.setInputHandling(new EnterSyntaxToSearchItemDrops());
			player.getPacketSender().sendEnterInputPrompt("Which item are you searching for?");
			return;
		}
	}

	public static void resetSearchInterface(Player player) {
		player.getPacketSender().setScrollBar(33007, 350);
		player.getPacketSender().hideDropWidgets(0);
	}

	public static void resetRight(Player player) {
		player.getPacketSender().sendString(HEADER, "Athens Loot Viewer");
		player.getPacketSender().sendString(SEARCHED_STRING, "Search Item");
		player.getPacketSender().sendString(STRING_AMOUNT, "");
		player.getPacketSender().sendString(STRING_CHANCE, "");
		player.getPacketSender().sendString(STRING_VALUE, "");
		for (int i = 0; i < 80; i++) {
			player.getPacketSender().sendItemOnInterface(ITEM_MODEL + i, -1, 0, 1);
			player.getPacketSender().sendString(ITEM_NAME + i, "");
			player.getPacketSender().sendString(ITEM_AMOUNT + i, "");
			player.getPacketSender().sendString(ITEM_CHANCE + i, "");
			player.getPacketSender().sendString(ITEM_VALUE + i, "");
		}
		player.getPacketSender().setScrollBar(34000, 500);
	}

	public static void resetLeft(Player player) {
		for (int i = 33108; i <= 33138; i++) {
			player.getPacketSender().sendString(i, "");
		}
	}

	public static void populateNpcOptions(Player player) {
		List<Integer> list = player.getLootList();
		if (list == null) {
			player.getPacketSender().sendMessage("Unable to load your loot list.");
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			if (i + 33108 > 33138) break;
			player.getPacketSender().sendString(i + 33108, "" + NpcDefinition.forId(list.get(i)).getName());
		}
		player.getPacketSender().setScrollBar(33007, 23 * list.size());
		player.getPacketSender().hideDropWidgets(list.size());
	}

	public static void handleItemSearchInput(Player player, String syntax) {
		player.getPacketSender().sendClientRightClickRemoval();
		List<Integer> list = getNpcListByItem(syntax);
		player.getPacketSender().sendString(SEARCHED_STRING, syntax);
		if (!list.isEmpty()) {
			player.setLootList(list);
			populateNpcOptions(player);
		} else {
			player.getPacketSender().sendMessage("No NPCs drop this item.");
		}
	}

	public static List<Integer> getNpcListByItem(String itemName) {
		List<Integer> npcIds = new ArrayList<>();
		itemName = itemName.toLowerCase();

		for (int npcId = 0; npcId < NpcDefinition.getDefinitions().length; npcId++) {
			NPCDrops drops = NPCDrops.forId(npcId);
			if (drops == null || drops.getDropList() == null) continue;

			for (var drop : drops.getDropList()) {
				if (drop.getItem().getDefinition() == null) continue;
				if (drop.getItem().getDefinition().getName().toLowerCase().contains(itemName)) {
					npcIds.add(npcId);
					break;
				}
			}
		}

		return npcIds;
	}

	public static void open(Player player) {
		resetRight(player);
		resetSearchInterface(player);
		if (player.getLootList() != null) {
			populateNpcOptions(player);
		} else {
			resetLeft(player);
		}
		player.getPacketSender().sendInterface(INTERFACE_ID);
	}
}
