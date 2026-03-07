package com.ruse.world.content.combat.magic;

import java.util.Arrays;
import java.util.Optional;

import com.ruse.model.Item;
import com.ruse.model.Skill;
import com.ruse.util.Misc;
import com.ruse.world.entity.Entity;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.player.Player;

public abstract class Spell {

	public boolean canCast(Player player, boolean delete) {

	

			//SKELETAL FIRST ROW
		boolean skeletal = player.isUnlockedSkeletalSpells();
		if (spellId() == 27587 || spellId() == 27595 || spellId() == 27603 || spellId() == 27611 ){
			if (!skeletal){
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have Skeletal Spells unlocked!");
				return false;
			}
		}

		//DEMONIC SECOND ROW
		boolean demonic = player.isUnlockedDemonicSpells();
		if (spellId() == 27619 || spellId() == 27627 || spellId() == 27635 || spellId() == 27643 ){
			if (!demonic){
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have Demonic Spells unlocked!");
				return false;
			}
		}

		//OGRE THIRD ROW
		boolean ogre = player.isUnlockedOgreSpells();
		if (spellId() == 27651 || spellId() == 27659 || spellId() == 27667 || spellId() == 27675 ){
			if (!ogre){
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have Ogre Spells unlocked!");
				return false;
			}
		}

		//SPECTRAL FOURTH ROW
		boolean spectral = player.isUnlockedSpectralSpells();
		if (spellId() == 27683 || spellId() == 27691 || spellId() == 27699 || spellId() == 27707 ){
			if (!spectral){
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have Spectral Spells unlocked!");
				return false;
			}
		}

		//MASTER FIFTH ROW
		boolean master = player.isUnlockedMasterSpells();
		if (spellId() == 27715 || spellId() == 27723 || spellId() == 27731 || spellId() == 27739 ){
			if (!master){
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have Master Spells unlocked!");
				return false;
			}
		}

		// We first check the level required.
		if (player.getSkillManager().getCurrentLevel(Skill.NECROMANCY) < levelRequired()) {
			player.getPacketSender().sendMessage("<col=AF70C3><shad=0>You need a Necromancy level of@red@<shad=0> " + levelRequired() + " <col=AF70C3><shad=0>to cast this spell.");
			player.getCombatBuilder().reset(true);
			return false;
		}

		// Then we check the runes required from the players rune pouch.
		if (itemsRequired(player).isPresent()) {
			if (!player.getInventory().contains(19622)){
				player.msgRed("You don't have a rune pouch in your inventory");
				return false;
			}

			int CurseRunes = calculateRequiredCurseRunes(player, itemsRequired(player).get());
			int WraithRunes = calculateRequiredWraithRunes(player, itemsRequired(player).get());
			int ShadowRunes = calculateRequiredShadowRunes(player, itemsRequired(player).get());
			int VoidRunes = calculateRequiredVoidRunes(player, itemsRequired(player).get());
			int SoulRunes = calculateRequiredSoulRunes(player, itemsRequired(player).get());
			int CryptRunes = calculateRequiredCryptRunes(player, itemsRequired(player).get());

			if (player.getCurseRunes() < CurseRunes) {
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have the required curse runes to cast this spell!");
				player.getPacketSender().sendMessage("@red@<shad=0>Add them to your Rune Pouch!");
				resetPlayerSpell(player);
				player.getCombatBuilder().reset(true);
				return false;
			}
			if (player.getWraithRunes() < WraithRunes) {
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have the required Wraith runes to cast this spell!");
				player.getPacketSender().sendMessage("@red@<shad=0>Add them to your Rune Pouch!");
				resetPlayerSpell(player);
				player.getCombatBuilder().reset(true);
				return false;
			}
			if (player.getShadowRunes() < ShadowRunes) {
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have the required Shadow runes to cast this spell!");
				player.getPacketSender().sendMessage("@red@<shad=0>Add them to your Rune Pouch!");
				resetPlayerSpell(player);
				player.getCombatBuilder().reset(true);
				return false;
			}
			if (player.getVoidRunes() < VoidRunes) {
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have the required Void runes to cast this spell!");
				player.getPacketSender().sendMessage("@red@<shad=0>Add them to your Rune Pouch!");
				resetPlayerSpell(player);
				player.getCombatBuilder().reset(true);
				return false;
			}
			if (player.getSoulRunes() < SoulRunes) {
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have the required Soul runes to cast this spell!");
				player.getPacketSender().sendMessage("@red@<shad=0>Add them to your Rune Pouch!");
				resetPlayerSpell(player);
				player.getCombatBuilder().reset(true);
				return false;
			}
			if (player.getCryptRunes() < CryptRunes) {
				player.getPacketSender().sendMessage("@red@<shad=0>You do not have the required Crypt runes to cast this spell!");
				player.getPacketSender().sendMessage("@red@<shad=0>Add them to your Rune Pouch!");
				resetPlayerSpell(player);
				player.getCombatBuilder().reset(true);
				return false;
			}
				player.setCurseRunes(player.getCurseRunes() - CurseRunes);
				player.setWraithRunes(player.getWraithRunes() - WraithRunes);
				player.setShadowRunes(player.getShadowRunes() - ShadowRunes);
				player.setVoidRunes(player.getVoidRunes() - VoidRunes);
				player.setSoulRunes(player.getSoulRunes() - SoulRunes);
				player.setCryptRunes(player.getCryptRunes() - CryptRunes);

		}
		return true;
	}

	private int calculateRequiredCurseRunes(Player player, Item[] items) {
		int totalCurseRunes = 0;

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && itemsRequired(player).get()[i] != null) {
				if (items[i].getId() == 20010) {
					totalCurseRunes += itemsRequired(player).get()[i].getAmount();
				}
			}
		}

		return totalCurseRunes;
	}
	private int calculateRequiredWraithRunes(Player player, Item[] items) {
		int totalWraithRunes = 0;

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && itemsRequired(player).get()[i] != null) {
				if (items[i].getId() == 20011) {
					totalWraithRunes += itemsRequired(player).get()[i].getAmount();
				}
			}
		}

		return totalWraithRunes;
	}

	private int calculateRequiredShadowRunes(Player player, Item[] items) {
		int totalShadowRunes = 0;

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && itemsRequired(player).get()[i] != null) {
				if (items[i].getId() == 20012) {
					totalShadowRunes += itemsRequired(player).get()[i].getAmount();
				}
			}
		}

		return totalShadowRunes;
	}
	private int calculateRequiredVoidRunes(Player player, Item[] items) {
		int totalVoidRunes = 0;

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && itemsRequired(player).get()[i] != null) {
				if (items[i].getId() == 20013) {
					totalVoidRunes += itemsRequired(player).get()[i].getAmount();
				}
			}
		}

		return totalVoidRunes;
	}

	private int calculateRequiredSoulRunes(Player player, Item[] items) {
		int totalSoulRunes = 0;

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && itemsRequired(player).get()[i] != null) {
				if (items[i].getId() == 20014) {
					totalSoulRunes += itemsRequired(player).get()[i].getAmount();
				}
			}
		}

		return totalSoulRunes;
	}

	private int calculateRequiredCryptRunes(Player player, Item[] items) {
		int totalCryptRunes = 0;

		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && itemsRequired(player).get()[i] != null) {
				if (items[i].getId() == 20015) {
					totalCryptRunes += itemsRequired(player).get()[i].getAmount();
				}
			}
		}

		return totalCryptRunes;
	}

	private void resetPlayerSpell(Player player) {
		if (player.getCombatBuilder().isAttacking() || player.getCombatBuilder().isBeingAttacked() && player.isAutocast()) {
			player.setCastSpell(null);
		}
	}

	public abstract int spellId();

	public abstract int levelRequired();

	public abstract int baseExperience();

	public abstract Optional<Item[]> itemsRequired(Player player);

	public abstract Optional<Item[]> equipmentRequired(Player player);

	public abstract void startCast(Character cast, Character castOn);
}
