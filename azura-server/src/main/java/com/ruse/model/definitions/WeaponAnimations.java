package com.ruse.model.definitions;

import com.ruse.model.CharacterAnimations;
import com.ruse.model.Item;
import com.ruse.model.container.impl.Equipment;
import com.ruse.world.entity.impl.player.Player;


public final class WeaponAnimations {

	public static void update(Player player) {
		player.setCharacterAnimations(getUpdateAnimation(player));
	}
	public static void assign(Player player, Item item) {
		player.getCharacterAnimations().reset();
		player.setCharacterAnimations(getUpdateAnimation(item));
	}

	public static CharacterAnimations getUpdateAnimation(Item item) {
		String weaponName = item.getDefinition().getName().toLowerCase();
		int weaponId = item.getDefinition().getId();

		int playerStandIndex = 0x328;
		int playerWalkIndex = 0x333;
		int playerRunIndex = 0x338;
		int playerTurnIndex = 0x337;
		int playerTurn180Index = 0x334;
		int playerTurn90CWIndex = 0x335;
		int playerTurn90CCWIndex = 0x336;


		if (weaponName.contains("staff")) {
			playerStandIndex = 8980;
			playerRunIndex = 1210;
			playerWalkIndex = 1146;
		}

		if (weaponId == 2088){
			playerStandIndex = 809;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}
		if (weaponName.toLowerCase().contains("axe")) {
			playerStandIndex = 13217;
			playerWalkIndex = 13218;
			playerRunIndex = 13220;
		}

		if (weaponName.toLowerCase().contains("rapier")) {
			playerStandIndex = 808;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}

		if (weaponName.contains("blade")) {
			playerStandIndex = 808;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}


		if (weaponName.contains("bow")) {
			playerStandIndex = 808;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}

		if (weaponName.contains("snow")) {
			playerStandIndex = 808;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}

		if (weaponId == 441 || weaponId == 450 || weaponId == 451 ){
			playerStandIndex = 13217;
			playerWalkIndex = 13218;
			playerRunIndex = 13220;
		}


		return new CharacterAnimations(playerStandIndex, playerWalkIndex, playerRunIndex, playerTurnIndex,
				playerTurn180Index, playerTurn90CWIndex, playerTurn90CCWIndex);
	}
	public static CharacterAnimations getUpdateAnimation(Player player) {
		int weaponId = player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
		String weaponName = ItemDefinition.forId(weaponId).getName().toLowerCase();
		int playerStandIndex = 0x328;
		int playerWalkIndex = 0x333;
		int playerRunIndex = 0x338;
		int playerTurnIndex = 0x337;
		int playerTurn180Index = 0x334;
		int playerTurn90CWIndex = 0x335;
		int playerTurn90CCWIndex = 0x336;

		if (weaponName.toLowerCase().contains("axe")) {
			playerStandIndex = 13217;
			playerWalkIndex = 13218;
			playerRunIndex = 13220;
		}

		if (weaponName.contains("blade")) {
			playerStandIndex = 15069;// 12021;
			playerRunIndex = 15070;// 12023;
			playerWalkIndex = 15073; // 12024;
		}
		if (weaponId == 2088){
			playerStandIndex = 809;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}

		if (weaponName.toLowerCase().contains("rapier")) {
			playerStandIndex = 15069;// 12021;
			playerRunIndex = 15070;// 12023;
			playerWalkIndex = 15073; // 12024;
		}

		if (weaponName.contains("staff")) {
			playerStandIndex = 8980;
			playerRunIndex = 1210;
			playerWalkIndex = 1146;
		}

		if (weaponName.contains("bow")) {
			playerStandIndex = 808;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}

		if (weaponName.contains("snow")) {
			playerStandIndex = 808;
			playerWalkIndex = 819;
			playerRunIndex = 824;
		}
		if (weaponId == 441 || weaponId == 450 || weaponId == 451){
			playerStandIndex = 809;
			playerWalkIndex = 1146;
			playerRunIndex = 1210;
		}


		return new CharacterAnimations(playerStandIndex, playerWalkIndex, playerRunIndex, playerTurnIndex,
				playerTurn180Index, playerTurn90CWIndex, playerTurn90CCWIndex);
	}

	public static int getAttackAnimation(Player c) {
		int weaponId = c.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
		String weaponName = ItemDefinition.forId(weaponId).getName().toLowerCase();
		switch (weaponId){
			case 711:
			case 712:
			case 713:
			case 714:
			case 2653:
				return 4230;
		}

		if (weaponId == 441 || weaponId == 450 || weaponId == 451 || weaponId == 23188)
			return 440;



		if (weaponId == 1462)
			return 9943;
		return c.getFightType().getAnimation();
	}

	public static int getBlockAnimation(Player c) {
		return 424;
	}
}
