package com.ruse.world.content;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BestItems {
	public BestItems(Player player) {
		this.player = player;
	}
	public List<ItemDefinition> definitions = new ArrayList<>();
	@SuppressWarnings("unchecked")
	public List<Integer> itemsToIgnore = new ArrayList() {
		{

		}
	};
	private int definitionIndex;
	public void fillDefinitions() {
		Collections.addAll(definitions, ItemDefinition.getDefinitions());
		definitions.removeIf(def -> def == null || itemsToIgnore.contains(def.getId()));

		for (int i = 0; i < 5; i++) {
			player.getPacketSender().sendString(100010 + i, (definitionIndex == i ? "@whi@Check " : "Check ") + text[i]);
		}
		for (int i = 5; i < 10; i++) {
			player.getPacketSender().sendString(100011 + i, (definitionIndex == i ? "@whi@Check " : "Check ") + text[i]);
		}
		player.getPacketSender().sendString(100022, (definitionIndex == 14 ? "@whi@Check " : "Check ") + text[10]);
		player.getPacketSender().sendString(100023, (definitionIndex == 15 ? "@whi@Check " : "Check ") + text[11]);
		player.getPacketSender().sendString(100024, (definitionIndex == 17 ? "@whi@Check " : "Check ") + text[12]);
	}
	public void open() {
		definitionIndex = 0;
		definitions.sort(new sortDefinitions(definitionIndex).reversed());
		displayBonuses();
		player.getPA().sendInterface(100000);
	}


	private static String[] text = new String[] { "Stab", "Slash", "Crush", "Magic", "Range", "Stab", "Slash", "Crush",
			"Magic", "Range", "Strength", "Ranged Str", "Magic Damage" };

	private void sortDefinitions(int index) {
		definitionIndex = index;
		definitions.sort(new sortDefinitions(definitionIndex).reversed());

		for (int i = 0; i < 5; i++) {
			player.getPacketSender().sendString(100010 + i, (definitionIndex == i ? "@whi@Check " : "Check ") + text[i]);
		}
		for (int i = 5; i < 10; i++) {
			player.getPacketSender().sendString(100011 + i, (definitionIndex -1== i  ? "@whi@Check " : "Check ") + text[i]);
		}
		player.getPacketSender().sendString(100022, (definitionIndex == 11 ? "@whi@Check " : "Check ") + text[12]);
		player.getPacketSender().sendString(100023, (definitionIndex == 12 ? "@whi@Check " : "Check ") + text[11]);
		player.getPacketSender().sendString(100024, (definitionIndex == 13 ? "@whi@Check " : "Check ") + text[10]);

	}
	
	private void displayBonuses() {
		int bonusRank = 100102;
		int name = 100103;
		int itemModel = 100104;
		for(int i = 0; i < 100; i++) {
			player.getPA().sendString(bonusRank, "" + definitions.get(i).getBonus()[definitionIndex]);
			player.getPA().sendString(name, definitions.get(i).getName());
			player.getPA().sendItemOnInterface(itemModel, definitions.get(i).getId(), 1);
			bonusRank += 4;
			name += 4;
			itemModel += 4;
		}
	}
	public boolean handleButton(int id) {
		if(id >= 100010 && id <= 100024) {
			int index = id - 100010;
			sortDefinitions(index);
			displayBonuses();
		}
		return false;
	}
	
	private final Player player;
}
 class sortDefinitions implements Comparator<ItemDefinition> {
	 private final int bonusIndex;
	 public sortDefinitions(int bonusIndex) {
		 this.bonusIndex = bonusIndex;
	 }
	@Override
	public int compare(ItemDefinition def, ItemDefinition other) {
		
		return (int) (def.getBonus()[bonusIndex] - other.getBonus()[bonusIndex]);
	}
	
}
