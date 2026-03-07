package com.ruse.world.content;

import com.ruse.donation.DonatorRanks;
import com.ruse.model.PlayerRights;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.entity.impl.player.Player;

public class BondHandler {

	public static void checkForRankUpdate(Player player) {
		if (player.getRights().isStaff() || player.getRights() == PlayerRights.YOUTUBER) {
			return;
		}
		PlayerRights rights = null;
		if (player.getAmountDonated() >= DonatorRanks.ADEPT_AMOUNT)
			rights = PlayerRights.ADEPT;
		if (player.getAmountDonated() >= DonatorRanks.ETHEREAL_AMOUNT)
			rights = PlayerRights.ETHEREAL;
		if (player.getAmountDonated() >= DonatorRanks.MYTHIC_AMOUNT)
			rights = PlayerRights.MYTHIC;
		if (player.getAmountDonated() >= DonatorRanks.ARCHON_AMOUNT)
			rights = PlayerRights.ARCHON;
		if (player.getAmountDonated() >= DonatorRanks.CELESTIAL_AMOUNT)
			rights = PlayerRights.CELESTIAL;
		if (player.getAmountDonated() >= DonatorRanks.ASCENDANT_AMOUNT)
			rights = PlayerRights.ASCENDANT;
		if (player.getAmountDonated() >= DonatorRanks.GLADIATOR_AMOUNT)
			rights = PlayerRights.GLADIATOR;

		if (player.getAmountDonated() >= DonatorRanks.COSMIC_AMOUNT)
			rights = PlayerRights.COSMIC;
		if (player.getAmountDonated() >= DonatorRanks.GUARDIAN_AMOUNT)
			rights = PlayerRights.GUARDIAN;
		if (player.getAmountDonated() >= DonatorRanks.CORRUPT_AMOUNT)
			rights = PlayerRights.CORRUPT;
		if (rights != null && rights != player.getRights()) {
			player.getPacketSender().sendMessage("<shad=0><col=AF70C3>You have become a@red@ " + Misc.formatText(rights.toString().toLowerCase()) + "<shad=0><col=AF70C3>! Congratulations!");
			player.setRights(rights);
			player.getPacketSender().sendRights();
		}
	}

	public static boolean confirmationDialogue(Player player, int item) {
		new SelectionDialogue(player,"Claim Bond?", new SelectionDialogue.Selection("Claim Bond", 0, p -> {
			if (BondHandler.handleBond(player, item, false)){
				p.getPacketSender().sendChatboxInterfaceRemoval();
				return;
			}

		}),
		new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
		return false;
	}

	public static boolean confirmationDialogueAll(Player player, int item) {
		new SelectionDialogue(player,"Claim Bond?", new SelectionDialogue.Selection("Claim All", 0, p -> {
			if (BondHandler.handleBondAll(player, item, true)){
				p.getPacketSender().sendChatboxInterfaceRemoval();
				return;
			}

		}),
				new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
		return false;
	}


		public static boolean handleBond(Player player, int item, boolean claimAll) {
		if (!player.getInventory().contains(item)) {
			return false;
		}
		switch (item) {
			case 10945:
			case 10946:
			case 23057:
			case 23058:
			case 23059:
			case 23060:
				player.bondClicked = item;
				player.bondClickedClaimAll = claimAll;
				BondHandler.claimBond(player);
				break;
		}
		return false;
	}

	public static boolean handleBondAll(Player player, int item, boolean claimAll) {
		if (!player.getInventory().contains(item)) {
			return false;
		}
		switch (item) {
			case 10945:
			case 10946:
			case 23057:
			case 23058:
			case 23059:
			case 23060:
				player.bondClicked = item;
				player.bondClickedClaimAll = claimAll;
				BondHandler.claimBond(player);
				break;
		}
		return false;
	}


	public static void claimBond(Player player) {
		int item = player.getBondClicked();
		boolean claimAll = player.isBondClickedClaimAll();
		if (player.getInventory().contains(item)) {
			PlayerLogs.log(player.getUsername(), "Has just redeemed a " + ItemDefinition.forId(item).getName() + " successfully!");

			int funds =
					item == 10945 ? 1:
					item == 10946 ? 5:
					item == 23057 ? 10:
					item == 23058 ? 25:
					item == 23059 ? 100:
					item == 23060 ? 250 :
					-1;

			int amount = claimAll ? player.getInventory().getAmount(item) : 1;
			if (claimAll) {
				funds *= amount;
			}
			player.getInventory().delete(item, amount);
			player.incrementAmountDonated(funds);
			player.incrementAmountDonatedToday(funds);
			player.getPointsHandler().setDonatorPoints(funds, true);
			player.getPacketSender().sendMessage("<shad=0><col=AF70C3>Your account has gained funds worth @red@$" + (funds) + ". <shad=0><col=AF70C3>Your total is now at @red@$" + player.getAmountDonated() + ".");
			if (player.getRights() != PlayerRights.DEVELOPER && player.getRights() != PlayerRights.OWNER && player.getRights() != PlayerRights.ADMINISTRATOR &&
					player.getRights() != PlayerRights.CO_OWNER && player.getRights() != PlayerRights.MANAGER && player.getRights() != PlayerRights.MODERATOR &&
					player.getRights() != PlayerRights.SUPPORT && player.getRights() != PlayerRights.YOUTUBER) {
				checkForRankUpdate(player);
			}

			PlayerPanel.refreshPanel(player);
		}
		player.getPacketSender().sendInterfaceRemoval();
		player.bondClicked = -1;
		player.bondClickedClaimAll = false;
	}
}
