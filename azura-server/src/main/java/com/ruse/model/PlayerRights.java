package com.ruse.model;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.ruse.donation.DonatorRanks;
import com.ruse.world.entity.impl.player.Player;

/**
 * Represents a player's privilege rights.
 * 
 * @author Gabriel Hannason
 */

public enum PlayerRights {

	//Be sure when adding extra rights to add them onto the bottom under extras regardless if donator or not.
	PLAYER(-1, null ,0),
	MODERATOR(-1, "<col=0><shad=6C1894>", 0),
	ADMINISTRATOR(-1, "<col=0><shad=6C1894>", 0),
	SUPPORT(-1, "<col=0><shad=6C1894>", 0),
	OWNER(-1, "<col=0><shad=6C1894>", 0),
	MANAGER(-1, "<col=0><shad=6C1894>", 0),
	MANAGER_2(-1, "<col=0><shad=6C1894>", 0),
	CO_OWNER(-1, "<col=0><shad=6C1894>", 0),
	DEVELOPER(-1, "<col=0><shad=6C1894>", 0),

	YOUTUBER(-1, "<col=0><shad=6C1894>", 821),

	ADEPT(60, "@blu@<shad=0>", 0),
	ETHEREAL(45, "@gre@<shad=0>", 0),
	MYTHIC(30, "@red@<shad=0>", 0),
	ARCHON(15, "@whi@<shad=0>", 0),
	CELESTIAL(-1, "@bla@<shad=0>", 0),
	ASCENDANT(-1, "@bla@<shad=0>", 0),
	GLADIATOR(-1, "@bla@<shad=0>", 0),
	//Extras
	EVENT_HOST(-1, "<col=0><shad=6C1894>", 5644),
	EVENT_ADMINISTRATOR(-1, "<col=0><shad=6C1894>", 5643),

	COSMIC(-1, "<col=0><shad=6C1894>", 0),
	GUARDIAN(-1, "<col=0><shad=6C1894>", 0),
	CORRUPT(-1, "<col=0><shad=6C1894>", 0),

	;




	PlayerRights(int yellDelaySeconds, String yellHexColorPrefix, int spriteId) {
		this.yellDelay = yellDelaySeconds;
		this.yellHexColorPrefix = yellHexColorPrefix;
		this.spriteId = spriteId;
	}

	private static final ImmutableSet<PlayerRights> STAFF = Sets.immutableEnumSet(SUPPORT, MODERATOR, ADMINISTRATOR, MANAGER_2, MANAGER, CO_OWNER, DEVELOPER, OWNER);
	private static final ImmutableSet<PlayerRights> HIGHTIERDONATORS = Sets.immutableEnumSet(CELESTIAL, ASCENDANT, GLADIATOR, COSMIC, GUARDIAN, CORRUPT, YOUTUBER);

	private static final ImmutableSet<PlayerRights> MEMBERS = Sets.immutableEnumSet(ADEPT, ETHEREAL, MYTHIC, ARCHON, SUPPORT, SUPPORT, MODERATOR, ADMINISTRATOR, MANAGER_2, EVENT_HOST, EVENT_ADMINISTRATOR, CO_OWNER, MANAGER, CELESTIAL, COSMIC, GUARDIAN, CORRUPT, GLADIATOR, ASCENDANT, DEVELOPER, YOUTUBER);
	private static final ImmutableSet<PlayerRights> REGULAR = Sets.immutableEnumSet(PLAYER);
	private static final ImmutableSet<PlayerRights> DEVELOPERONLY = Sets.immutableEnumSet(DEVELOPER);
	private static final ImmutableSet<PlayerRights> OWNERDEVELOPERONLY = Sets.immutableEnumSet(DEVELOPER,OWNER);

	private static final ImmutableSet<PlayerRights> SUPPORTS = Sets.immutableEnumSet(SUPPORT,MODERATOR,ADMINISTRATOR,MANAGER,OWNER,CO_OWNER);
	private static final ImmutableSet<PlayerRights> MODERATORS = Sets.immutableEnumSet(MODERATOR,ADMINISTRATOR,MANAGER,OWNER,CO_OWNER);
	private static final ImmutableSet<PlayerRights> ADMINS = Sets.immutableEnumSet(ADMINISTRATOR,MANAGER,OWNER,CO_OWNER);



	private static final ImmutableSet<PlayerRights> MANAGEMENT = Sets.immutableEnumSet(MANAGER, CO_OWNER, DEVELOPER, OWNER);
	/*
	 *
	 * The yell delay for the rank The amount of seconds the player with the
	 * specified rank must wait before sending another yell message.
	 */
	private int spriteId;
	private int yellDelay;
	private String yellHexColorPrefix;

	public int getYellDelay() {
		return yellDelay;
	}

	/*
	 * The player's yell message prefix. Color and shadowing.
	 */

	public String getYellPrefix() {
		return yellHexColorPrefix;
	}

	public boolean isStaff() {
		return STAFF.contains(this);
	}

	public boolean isHighTierDono() {
		return HIGHTIERDONATORS.contains(this);
	}

	public boolean isDeveloperOnly() {
		return DEVELOPERONLY.contains(this);
	}

	public boolean isSupport() {
		return SUPPORTS.contains(this);
	}
	public boolean isModerator() {
		return MODERATORS.contains(this);
	}
	public boolean isAdmin() {
		return ADMINS.contains(this);
	}

    public boolean isManagement() {
        return MANAGEMENT.contains(this);
    }



	public boolean OwnerDeveloperOnly() {
		return OWNERDEVELOPERONLY.contains(this);
	}

	/**
	 * Gets the rank for a certain id.
	 * 
	 * @param id The id (ordinal()) of the rank.
	 * @return rights.
	 */
	public static PlayerRights forId(int id) {
		for (PlayerRights rights : PlayerRights.values()) {
			if (rights.ordinal() == id) {
				return rights;
			}
		}
		return null;
	}

	public int getNecromancyTicketBonus(Player player) {

		/*
		 * Donator Rank bonusses
		 */

		if(player.getAmountDonated() >= DonatorRanks.GLADIATOR_AMOUNT) {
			return 200;
		}

		else if(player.getAmountDonated() >= DonatorRanks.COSMIC_AMOUNT) {
			return 225;
		}
		else if(player.getAmountDonated() >= DonatorRanks.GUARDIAN_AMOUNT) {
			return 250;
		}
		else if(player.getAmountDonated() >= DonatorRanks.CORRUPT_AMOUNT) {
			return 300;
		}

		else if(player.getAmountDonated() >= DonatorRanks.ASCENDANT_AMOUNT) {
			return 150;
		} else if(player.getAmountDonated() >= DonatorRanks.CELESTIAL_AMOUNT) {
			return 125;
		} else if(player.getAmountDonated() >= DonatorRanks.ARCHON_AMOUNT) {
			return 100;
		} else if(player.getAmountDonated() >= DonatorRanks.MYTHIC_AMOUNT) {
			return 75;
		} else if(player.getAmountDonated() >= DonatorRanks.ETHEREAL_AMOUNT) {
			return 50;
		} else if(player.getAmountDonated() >= DonatorRanks.ADEPT_AMOUNT) {
			return 25;
		} else {
			return 0;
		}
	}
}
