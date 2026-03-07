package com.ruse.world.content.combat.prayer;

import com.ruse.donation.DonatorRanks;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.GameMode;
import com.ruse.model.PlayerRights;
import com.ruse.model.Prayerbook;
import com.ruse.model.Skill;
import com.ruse.util.NameUtils;
import com.ruse.world.content.BonusManager;
import com.ruse.world.content.Sounds;
import com.ruse.world.content.Sounds.Sound;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.minigames.impl.Dueling;
import com.ruse.world.content.minigames.impl.Dueling.DuelRule;
import com.ruse.world.entity.impl.player.Player;

import java.util.HashMap;

/**
 * All of the prayers that can be activated and deactivated. This currently only
 * has support for prayers present in the <b>317 protocol</b>.
 * 
 * @author Gabriel
 */
public class PrayerHandler {

	public static final int HOLY_DESTRUCTION_IDX = 0;
	public static final int HOLY_HUNTERS_EYE_IDX = 1;
	public static final int HOLY_FORTITUDE_IDX = 2;
	public static final int HOLY_GNOMES_GREED_IDX = 3;
	public static final int HOLY_SOUL_LEECH_IDX = 4;
	public static final int HOLY_FURY_SWIPE_IDX = 5;
	public static final int HOLY_SCROLL_DESTRUCTION_ITEM = 23167;
	public static final int HOLY_SCROLL_HUNTERS_EYE_ITEM = 23165;
	public static final int HOLY_SCROLL_FORTITUDE_ITEM = 23168;
	public static final int HOLY_SCROLL_GNOMES_GREED_ITEM = 23170;
	public static final int HOLY_SCROLL_SOUL_LEECH_ITEM = 23169;
	public static final int HOLY_SCROLL_FURY_SWIPE_ITEM = 23166;

    /**
	 * Represents a prayer's configurations, such as their level requirement,
	 * buttonId, configId and drain rate.
	 * 
	 * @author relex lawl
	 */
    public enum PrayerData {
		THICK_SKIN(1, .3, 25000, 83);

		private PrayerData(int requirement, double drainRate, int buttonId, int configId, int... hint) {
			this.requirement = requirement;
			this.drainRate = drainRate;
			this.buttonId = buttonId;
			this.configId = configId;
			if (hint.length > 0)
				this.hint = hint[0];
		}

		/**
		 * The prayer's level requirement for player to be able to activate it.
		 */
		private int requirement;

		/**
		 * The prayer's action button id in prayer tab.
		 */
		private int buttonId;

		/**
		 * The prayer's config id to switch their glow on/off by sending the sendConfig
		 * packet.
		 */
		private int configId;

		/**
		 * The prayer's drain rate as which it will drain the associated player's prayer
		 * points.
		 */
		private double drainRate;

		/**
		 * The prayer's head icon hint index.
		 */
		private int hint = -1;

		/**
		 * The prayer's formatted name.
		 */
		private String name;

		/**
		 * Gets the prayer's formatted name.
		 * 
		 * @return The prayer's name
		 */
		private String getPrayerName() {
			if (name == null)
				return NameUtils.capitalizeWords(toString().toLowerCase().replaceAll("_", " "));
			return name;
		}

		/**
		 * Contains the PrayerData with their corresponding prayerId.
		 */
		private static HashMap<Integer, PrayerData> prayerData = new HashMap<>();

		/**
		 * Contains the PrayerData with their corresponding buttonId.
		 */
		private static HashMap<Integer, PrayerData> actionButton = new HashMap<>();

		/**
		 * Populates the prayerId and buttonId maps.
		 */
		static {
			for (PrayerData pd : PrayerData.values()) {
				prayerData.put(pd.ordinal(), pd);
				actionButton.put(pd.buttonId, pd);
			}
		}
	}

	/**
	 * Gets the protecting prayer based on the argued combat type.
	 * 
	 * @param type the combat type.
	 * @return the protecting prayer.
	 */
	public static int getProtectingPrayer(CombatType type) {
		switch (type) {
		case MELEE:
			return PROTECT_FROM_MELEE;
		case MAGIC:
		case DRAGON_FIRE:
			return PROTECT_FROM_MAGIC;
		case RANGED:
			return PROTECT_FROM_MISSILES;
		default:
			throw new IllegalArgumentException("Invalid combat type: " + type);
		}
	}

	public static boolean isActivated(Player player, int prayer) {
		//BonusManager.sendCurseBonuses(player);
		return player.getPrayerActive().length > prayer ? player.getPrayerActive()[prayer] : false;
	}

	/**
	 * Activates a prayer with specified <code>buttonId</code>.
	 * 
	 * @param player   The player clicking on prayer button.
	 * @param buttonId The button the player is clicking.
	 */
	public static void togglePrayerWithActionButton(Player player, int buttonId) {
		for (PrayerData pd : PrayerData.values()) {
			if (buttonId == pd.buttonId) {
				if (!player.getPrayerActive()[pd.ordinal()])
					activatePrayer(player, pd.ordinal());
				else
					deactivatePrayer(player, pd.ordinal());
			}
		}
	}

	/**
	 * Activates said prayer with specified <code>prayerId</code> and de-activates
	 * all non-stackable prayers.
	 * 
	 * @param player   The player activating prayer.
	 * @param prayerId The id of the prayer being turned on, also known as the
	 *                 ordinal in the respective enum.
	 */
	public static void activatePrayer(Player player, int prayerId) {
		if (player.getPrayerbook() == Prayerbook.CURSES)
			return;
		if (player.getPrayerActive()[prayerId])
			return;
		if (Dueling.checkRule(player, DuelRule.NO_PRAYER)) {
			player.getPacketSender().sendMessage("Prayer has been disabled in this duel.");
			CurseHandler.deactivateAll(player);
			PrayerHandler.deactivateAll(player);
			//HolyPrayers.deactivateAll(player);
			return;
		}

		PrayerData pd = PrayerData.prayerData.get(prayerId);
		if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) <= 0) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You do not have enough Prayer points. You can recharge your points at an altar.");
			//PrayerHandler.deactivateAll(player);
			return;
		}
		if (player.getSkillManager().getMaxLevel(Skill.PRAYER) < (pd.requirement)) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage(
					"You need a Prayer level of at least " + pd.requirement + " to use " + pd.getPrayerName() + ".");
			return;
		}
		if (prayerId == CHIVALRY && player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 60) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need a Defence level of at least 60 to use Chivalry.");
			return;
		}
		if (prayerId == PIETY && player.getSkillManager().getMaxLevel(Skill.DEFENCE) < 70) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need a Defence level of at least 70 to use Piety.");
			return;
		}


		if(prayerId == DESTRUCTION && !player.isHolyPrayerUnlocked(HOLY_DESTRUCTION_IDX)) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need to unlock Destruction with a holy prayer scroll before using it!");
			return;
		}
		if(prayerId == HUNTERS_EYE && !player.isHolyPrayerUnlocked(HOLY_HUNTERS_EYE_IDX)) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need to unlock Hunter's Eye with a holy prayer scroll before using it!");
			return;
		}
		if(prayerId == FORTITUDE && !player.isHolyPrayerUnlocked(HOLY_FORTITUDE_IDX)) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need to unlock Fortitude with a holy prayer scroll before using it!");
			return;
		}
		if(prayerId == GNOMES_GREED && !player.isHolyPrayerUnlocked(HOLY_GNOMES_GREED_IDX)) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need to unlock Gnome's Greed with a holy prayer scroll before using it!");
			return;
		}
		if(prayerId == SOUL_LEECH && !player.isHolyPrayerUnlocked(HOLY_SOUL_LEECH_IDX)) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need to unlock Coup de grace with a holy prayer scroll before using it!");
			return;
		}
		if(prayerId == FURY_SWIPE && !player.isHolyPrayerUnlocked(HOLY_FURY_SWIPE_IDX)) {
			player.getPacketSender().sendConfig(pd.configId, 0);
			player.getPacketSender().sendMessage("You need to unlock Fury Swipe with a holy prayer scroll before using it!");
			return;
		}
		switch (prayerId) {
		}
		/*
		 * if (player.isPrayerInjured()) { if (prayerId == PROTECT_FROM_MAGIC ||
		 * prayerId == PROTECT_FROM_MISSILES || prayerId == PROTECT_FROM_MELEE) {
		 * player.getPacketSender().
		 * sendMessage("You have been injured and cannot use this prayer!");
		 * player.getPacketSender().sendConfig(pd.configId, 0); return; } }
		 * SoundEffects.sendSoundEffect(player,
		 * SoundEffects.SoundData.ACTIVATE_PRAYER_OR_CURSE, 10, 0);
		 */
		player.setPrayerActive(prayerId, true);
		player.getPacketSender().sendConfig(pd.configId, 1);
		if (hasNoPrayerOn(player, prayerId) && !player.isDrainingPrayer())
			startDrain(player);
		BonusManager.sendCurseBonuses(player);
		if (pd.hint != -1) {
			int hintId = getHeadHint(player);
			player.getAppearance().setHeadHint(hintId);
		}
		Sounds.sendSound(player, Sound.ACTIVATE_PRAYER_OR_CURSE);
	}

	/**
	 * Deactivates said prayer with specified <code>prayerId</code>.
	 * 
	 * @param player   The player deactivating prayer.
	 * @param prayerId The id of the prayer being deactivated.
	 */
	public static void deactivatePrayer(Player player, int prayerId) {
		if (!player.getPrayerActive()[prayerId])
			return;
		PrayerData pd = PrayerData.prayerData.get(prayerId);
		player.getPrayerActive()[prayerId] = false;
		player.getPacketSender().sendConfig(pd.configId, 0);
		BonusManager.sendCurseBonuses(player);
		if (pd.hint != -1) {
			int hintId = getHeadHint(player);
			player.getAppearance().setHeadHint(hintId);
		}
		Sounds.sendSound(player, Sound.DEACTIVATE_PRAYER_OR_CURSE);
	}

	/**
	 * Deactivates every prayer in the player's prayer book.
	 * 
	 * @param player The player to deactivate prayers for.
	 */
	public static void deactivatePrayers(Player player) {
		for (int i = 0; i < player.getPrayerActive().length; i++) {
			if (player.getPrayerActive()[i]) {
				deactivatePrayer(player, i);
			}
		}
	}

	public static void deactivateAll(Player player) {
		for (int i = 0; i < player.getPrayerActive().length; i++) {
			PrayerData pd = PrayerData.prayerData.get(i);
			if (pd == null)
				continue;
			player.getPrayerActive()[i] = false;
			player.getPacketSender().sendConfig(pd.configId, 0);
			if (pd.hint != -1) {
				int hintId = getHeadHint(player);
				player.getAppearance().setHeadHint(hintId);
				BonusManager.sendCurseBonuses(player);
			}
		}
	}

	/**
	 * Gets the player's current head hint if they activate or deactivate a head
	 * prayer.
	 * 
	 * @param player The player to fetch head hint index for.
	 * @return The player's current head hint index.
	 */
	private static int getHeadHint(Player player) {
		boolean[] prayers = player.getPrayerActive();
		return -1;
	}

	/**
	 * Initializes the player's prayer drain once a first prayer has been selected.
	 * 
	 * @param player The player to start prayer drain for.
	 */
	public static void startDrain(Player player) {
		if (getDrain(player) <= 0 && !player.isDrainingPrayer())
			return;
		player.setDrainingPrayer(true);
		TaskManager.submit(new Task(1, player, true) {
			@Override
			public void execute() {
				if (player.switchedPrayerBooks) {
					player.switchedPrayerBooks = false;
					this.stop();
					return;
				}

				if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) <= 0) {
					for (int i = 0; i < player.getPrayerActive().length; i++) {
						if (player.getPrayerActive()[i])
							deactivatePrayer(player, i);
					}
					Sounds.sendSound(player, Sound.RUN_OUT_OF_PRAYER_POINTS);
					player.getPacketSender().sendMessage("You have run out of Prayer points!");
					this.stop();
					return;
				}
				double drainAmount = getDrain(player);
				int total = (int) (player.getSkillManager().getCurrentLevel(Skill.PRAYER) - drainAmount);

				player.getSkillManager().setCurrentLevel(Skill.PRAYER, Math.max(0, total), true);
			}

			@Override
			public void stop() {
				setEventRunning(false);
				player.setDrainingPrayer(false);
			}
		});
	}

	/**
	 * Gets the amount of prayer to drain for <code>player</code>.
	 * 
	 * @param player The player to get drain amount for.
	 * @return The amount of prayer that will be drained from the player.
	 */
	private static double getDrain(Player player) {
		double toRemove = 0.0;

		for (int i = 0; i < player.getPrayerActive().length; i++) {

			if (player.getPrayerActive()[i]) {
				PrayerData prayerData = PrayerData.prayerData.get(i);
				toRemove += prayerData.drainRate;
			}
		}

		if (toRemove > 0) {
			toRemove /= (1 + (0.05 * player.getBonusManager().getOtherBonus()[2]));
		}

		return toRemove;
	}

	/**
	 * Checks if a player has no prayer on.
	 * 
	 * @param player      The player to check prayer status for.
	 * @param exceptionId The prayer id currently being turned on/activated.
	 * @return if <code>true</code>, it means player has no prayer on besides
	 *         <code>exceptionId</code>.
	 */
	private static boolean hasNoPrayerOn(Player player, int exceptionId) {
		int prayersOn = 0;
		for (int i = 0; i < player.getPrayerActive().length; i++) {
			if (player.getPrayerActive()[i] && i != exceptionId)
				prayersOn++;
		}
		return prayersOn == 0;
	}

	/**
	 * Resets <code> prayers </code> with an exception for <code> prayerID </code>
	 * 
	 * @param prayers  The array of prayers to reset
	 * @param prayerID The prayer ID to not turn off (exception)
	 */
	public static void resetPrayers(Player player, int[] prayers, int prayerID) {
		for (int i = 0; i < prayers.length; i++) {
			if (prayers[i] != prayerID)
				deactivatePrayer(player, prayers[i]);
		}
	}

	/**
	 * Checks if action button ID is a prayer button.
	 * 
	 * @param actionButtonID action button being hit.
	 */
	public static boolean isButton(int actionButtonID) {
		return PrayerData.actionButton.containsKey(actionButtonID);
	}

	public static final int THICK_SKIN = 0, BURST_OF_STRENGTH = 1, CLARITY_OF_THOUGHT = 2, SHARP_EYE = 3,
			MYSTIC_WILL = 4, ROCK_SKIN = 5, SUPERHUMAN_STRENGTH = 6, IMPROVED_REFLEXES = 7, RAPID_RESTORE = 8,
			RAPID_HEAL = 9, PROTECT_ITEM = 10, HAWK_EYE = 11, MYSTIC_LORE = 12, STEEL_SKIN = 13, ULTIMATE_STRENGTH = 14,
			INCREDIBLE_REFLEXES = 15, PROTECT_FROM_MAGIC = 16, PROTECT_FROM_MISSILES = 17, PROTECT_FROM_MELEE = 18,
			EAGLE_EYE = 19, MYSTIC_MIGHT = 20, RETRIBUTION = 21, REDEMPTION = 22, SMITE = 23, CHIVALRY = 24, PIETY = 25,
			RIGOUR = 26, AUGURY = 27, DESTRUCTION = 28, HUNTERS_EYE = 29,
			FORTITUDE = 30, GNOMES_GREED = 31,
			SOUL_LEECH = 32, FURY_SWIPE = 33;

	private static final int[] DISABLED_WITH_FURY_SWIPE = {};
	private static final int[] DISABLED_WITH_HUNTERS_EYE = {};
	private static final int[] DISABLED_WITH_DESTRUCTION = {};
	private static final int[] DISABLED_WITH_FORTITUDE = { };
	private static final int[] DISABLE_WITH_SOUL_LEECH = {  };


	/**
	 * Contains every prayer that counts as a defense prayer.
	 */
	private static final int[] DEFENCE_PRAYERS = {  };

	/**
	 * Contains every prayer that counts as a strength prayer.
	 */
	private static final int[] STRENGTH_PRAYERS = {};

	/**
	 * Contains every prayer that counts as an attack prayer.
	 */
	private static final int[] ATTACK_PRAYERS = { };

	/**
	 * Contains every prayer that counts as a ranged prayer.
	 */
	private static final int[] RANGED_PRAYERS = {  };

	/**
	 * Contains every prayer that counts as a magic prayer.
	 */
	private static final int[] MAGIC_PRAYERS = { };

	/**
	 * Contains every prayer that counts as an overhead prayer, excluding protect
	 * from summoning.
	 */
	public static final int[] OVERHEAD_PRAYERS = {};

}
