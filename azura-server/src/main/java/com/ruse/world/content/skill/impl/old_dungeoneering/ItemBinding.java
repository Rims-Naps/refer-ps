/*
package com.ruse.world.content.skill.impl.old_dungeoneering;

import com.ruse.model.Skill;
import com.ruse.util.Misc;
import com.ruse.world.entity.impl.player.Player;

public class ItemBinding {

	private static final int[][] BINDABLE_ITEMS = {

	};

	public static int getRandomBindableItem() {
		int index = Misc.getRandom(BINDABLE_ITEMS.length - 1);
		*/
/*
		 * if(ItemDefinition.forId(BINDABLE_ITEMS[index][0]).getName().toLowerCase().
		 * contains("body") ||
		 * ItemDefinition.forId(BINDABLE_ITEMS[index][0]).getName().toLowerCase().
		 * contains("legs") ||
		 * ItemDefinition.forId(BINDABLE_ITEMS[index][0]).getName().toLowerCase().
		 * contains("skirt")) { index = index + 1 >= BINDABLE_ITEMS[0].length ? index-1
		 * : index + 1; }
		 *//*

		return BINDABLE_ITEMS[index][0];
	}

	public static boolean isBindable(int item) {
		for (int i = 0; i < BINDABLE_ITEMS.length; i++) {
			if (BINDABLE_ITEMS[i][0] == item)
				return true;
		}
		return false;
	}

	public static boolean isBoundItem(int item) {
		for (int i = 0; i < BINDABLE_ITEMS.length; i++) {
			if (BINDABLE_ITEMS[i][1] == item)
				return true;
		}
		return false;
	}

	public static int getItem(int currentId) {
		for (int i = 0; i < BINDABLE_ITEMS.length; i++) {
			if (BINDABLE_ITEMS[i][0] == currentId)
				return BINDABLE_ITEMS[i][1];
		}
		return -1;
	}

	public static void unbindItem(Player p, int item) {
		if (Dungeoneering.doingOldDungeoneering(p)) {
			for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
				if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] == item) {
					p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] = 0;
					p.getPacketSender().sendMessage("You unbind the item..");
					break;
				}
			}
		}
	}

	public static void bindItem(Player p, int item) {
		if (Dungeoneering.doingOldDungeoneering(p)) {
			int amountBound = 0;
			for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
				if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] != 0)
					amountBound++;
			}
			if (amountBound >= 5) {
				p.getPacketSender().sendMessage("You have already bound four items, which is the maximum.");
				return;
			} else if (amountBound == 4 && p.getSkillManager().getCurrentLevel(Skill.DUNGEONEERING) < 95) {
				p.getPacketSender().sendMessage("You need a Dungeoneering level of at least 95 to have 5 bound items.");
				return;
			} else if (amountBound == 3 && p.getSkillManager().getCurrentLevel(Skill.DUNGEONEERING) < 80) {
				p.getPacketSender().sendMessage("You need a Dungeoneering level of at least 80 to have 4 bound items.");
				return;
			} else if (amountBound == 2 && p.getSkillManager().getCurrentLevel(Skill.DUNGEONEERING) < 60) {
				p.getPacketSender().sendMessage("You need a Dungeoneering level of at least 60 to have 3 bound items.");
				return;
			} else if (amountBound == 1 && p.getSkillManager().getCurrentLevel(Skill.DUNGEONEERING) < 40) {
				p.getPacketSender().sendMessage("You need a Dungeoneering level of at least 40 to have 2 bound items.");
				return;
			}
			int bind = getItem(item);
			int index = -1;
			for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
				if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] != 0)
					continue;
				index = i;
				break;
			}
			if (bind != -1 && index != -1) {
				p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[index] = bind;
				p.getInventory().delete(item, 1).add(bind, 1);
				p.getPacketSender().sendMessage("You bind the item..");
			}
		}
	}

	public static void onDungeonEntrance(Player p) {
		for (int i = 0; i < p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems().length; i++) {
			if (p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i] != 0) {
				p.getInventory().add(p.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()[i], 1);
			}
		}
	}
}
*/
