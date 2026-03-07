package com.ruse.model;

import com.ruse.world.content.skill.impl.old_dungeoneering.UltimateIronmanHandler;
import com.ruse.world.entity.impl.player.Player;

public enum GameMode {

	NORMAL, IRONMAN, EXILED, GROUP_IRON, EZMODE, YOUTUBER;

	public boolean isIronman() {
		return this == IRONMAN | this == GROUP_IRON | this == EZMODE | this == EXILED;
	}

	public static void set(Player player, GameMode newMode, boolean death) {
		if (UltimateIronmanHandler.hasItemsStored(player)) {
			player.getPacketSender().sendMessage("You must claim your stored items at Home first.");
			player.setPlayerLocked(false);
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}
		if (!death && !player.getClickDelay().elapsed(1000))
			return;
		player.getClickDelay().reset();
		player.getPacketSender().sendInterfaceRemoval();
		if (player.getGameMode() == IRONMAN || player.getGameMode() == EZMODE  && newMode != NORMAL && (!(player.getRights().OwnerDeveloperOnly()))) {
			player.getPacketSender().sendMessage("As an Lone Wolf, you can only set your game mode to Normal mode.");
			player.setPlayerLocked(false);
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}

		if (newMode != player.getGameMode() || death) {
		}
		player.setGameMode(newMode);
		player.getPacketSender().sendIronmanMode(newMode.ordinal());
		if (!death) {
			player.getPacketSender()
					.sendMessage(
							"You have set your Gamemode to " + newMode.name().toLowerCase().replaceAll("_", " ") + ".")
					.sendMessage("If you wish to change it, please talk to the town crier at home.");
		} else {
			player.getPacketSender().sendMessage("Your account progress has been reset.");
		}
		if (player.newPlayer()) {
			player.setPlayerLocked(true);
			
		} else {
			player.setPlayerLocked(false);
		}
	}
}
