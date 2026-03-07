package com.ruse.world.content.skill.impl.prayer;

import com.ruse.model.Item;
import com.ruse.model.Skill;
import com.ruse.world.entity.impl.player.Player;

import static com.ruse.world.content.Consumables.drinkInfinitePrayerPotion;

/**
 * The prayer skill is based upon burying the corpses of enemies. Obtaining a
 * higher level means more prayer abilities being unlocked, which help out in
 * combat.
 * 
 * @author Gabriel Hannason
 */

public class Prayer {

	public static boolean isBone(int bone) {
		return BonesData.forId(bone) != null;
	}

	public static void buryBone(Player player, int itemId) {
		if (!player.getClickDelay().elapsed(2000))
			return;
		BonesData currentBone = BonesData.forId(itemId);
		if (currentBone == null)
			return;
		player.getSkillManager().stopSkilling();
		player.getPacketSender().sendInterfaceRemoval();
		player.getPacketSender().sendMessage("You bury the bones in the ground...");
		Item bone = new Item(itemId);
		player.getInventory().delete(bone);
		player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP());
		player.getClickDelay().reset();
		if ( bone.getId() == 534) {
			drinkInfinitePrayerPotion(player,15);
		}
		if ( bone.getId() == 7305) {
			drinkInfinitePrayerPotion(player,25);
		}
		if ( bone.getId() == 7306) {
			drinkInfinitePrayerPotion(player,32);
		}
		if ( bone.getId() == 7307) {
			drinkInfinitePrayerPotion(player,38);
		}
		if ( bone.getId() == 7308) {
			drinkInfinitePrayerPotion(player,50);
		}
		if ( bone.getId() == 7309) {
			drinkInfinitePrayerPotion(player,58);
		}

	}
}
