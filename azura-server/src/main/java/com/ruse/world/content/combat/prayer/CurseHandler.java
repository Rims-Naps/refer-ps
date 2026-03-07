package com.ruse.world.content.combat.prayer;

import java.util.HashMap;
import java.util.Map;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.NameUtils;
import com.ruse.world.content.BonusManager;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.minigames.impl.Dueling;
import com.ruse.world.content.minigames.impl.Dueling.DuelRule;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.entity.impl.player.Player;

public class CurseHandler {

	public static boolean isButton(Player player, int buttonId) {
		if (CurseData.buttons.containsKey(buttonId)) {
			CurseData curse = CurseData.buttons.get(buttonId);
			if (player.getCurseActive()[curse.ordinal()])
				deactivateCurse(player, curse);
			else
				activateCurse(player, curse);
			return true;
		}
		return false;
	}


	public static int getProtectingPrayer(CombatType type) {
		switch (type) {
			case MELEE:
			    return BLOCK_MELEE;
			case MAGIC:
				return BLOCK_RANGE;
			case RANGED:
			    return BLOCK_MAGIC;
			default:
				throw new IllegalArgumentException("Invalid combat type: " + type);
		}
	}

    public static void sendLockedPrayers() {

    }

	public static boolean isActivated(Player player, int prayer) {
		return player.getCurseActive()[prayer];
	}

	public static void activateCurse(Player player, CurseData curse) {
		if (player.getPrayerbook() == Prayerbook.NORMAL)
			return;
		if (player.getCurseActive()[curse.ordinal()])
			return;
		if (Dueling.checkRule(player, DuelRule.NO_PRAYER)) {
			player.getPacketSender().sendMessage("Prayer has been disabled in this duel.");
			CurseHandler.deactivateAll(player);
			PrayerHandler.deactivateAll(player);
			return;
		}

		if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) <= 0) {
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You do not have enough Prayer points. You can recharge your points at an altar.");
			return;
		}
		if (player.getSkillManager().getMaxLevel(Skill.PRAYER) < (curse.requirement)) {
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage(
					"You need a Prayer level of at least " + curse.requirement + " to use " + curse.name + ".");
			return;
		}

		if (curse == CurseData.GAIABLESSING && player.getLocation() == Locations.Location.NECROMANCY_GAME_AREA){
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.msgRed("Lord Gaia cannot save you here...");
			return;
		}

		if (curse == CurseData.ROCKHEART && player.getLocation() == Locations.Location.NECROMANCY_GAME_AREA){
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.msgRed("Lord Gaia cannot save you here...");
			return;
		}

		if (curse == CurseData.STONEHAVEN && player.getLocation() == Locations.Location.NECROMANCY_GAME_AREA){
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.msgRed("Lord Gaia cannot save you here...");
			return;
		}

		if (curse == CurseData.GAIABLESSING && player.getPosition().getRegionId() == 13877){
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.msgRed("Lord Gaia cannot save you here...");
			return;
		}


		if (curse == CurseData.STONEHAVEN && player.getPosition().getRegionId() == 13877){
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.msgRed("Lord Gaia cannot save you here...");
			return;
		}

		if (curse == CurseData.GAIABLESSING && !player.isUnlockedGaiablessingPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Gaia's Blessing Unlocked");
			return;
		}
		if (curse == CurseData.SERENITY && !player.isUnlockedSerenityPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Serenity Unlocked");
			return;
		}
		if (curse == CurseData.ROCKHEART && !player.isUnlockedRockheartPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Rockheart Unlocked");
			return;
		}
		if (curse == CurseData.GEOVIGOR && !player.isUnlockedGeovigorPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Geovigor Unlocked");
			return;
		}
		if (curse == CurseData.STONEHAVEN && !player.isUnlockedStonehavenPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Stonehaven Unlocked");
			return;
		}
		if (curse == CurseData.EBBFLOW && !player.isUnlockedEbbflowPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Ebbflow Unlocked");
			return;
		}
		if (curse == CurseData.AQUASTRIKE && !player.isUnlockedAquastrikePrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Aquastrike Unlocked");
			return;
		}
		if (curse == CurseData.RIPTIDE && !player.isUnlockedRiptidePrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Riptide Unlocked");
			return;
		}
		if (curse == CurseData.SEASLICER && !player.isUnlockedSeaslicerPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Seaslicer Unlocked");
			return;
		}
		if (curse == CurseData.SWIFTTIDE && !player.isUnlockedSwifttidePrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Swifttide Unlocked");
			return;
		}
		if (curse == CurseData.CINDERSTOUCH && !player.isUnlockedCindersTouch()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Cinder's Touch Unlocked");
			return;
		}

		if (curse == CurseData.EMBERBLAST && !player.isUnlockedEmberblastPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Emberblast Unlocked");
			return;
		}
		if (curse == CurseData.INFERNIFY && !player.isUnlockedInfernifyPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Infernify Unlocked");
			return;
		}
		if (curse == CurseData.BLAZEUP && !player.isUnlockedBlazeupPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Blazeup Unlocked");
			return;
		}
		if (curse == CurseData.INFERNO && !player.isUnlockedInfernoPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.getPacketSender().sendMessage("You don't have Inferno Unlocked");
			return;
		}


		if (curse == CurseData.MALEVOLENCE && !player.isUnlockedMalevolencePrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.msgRed("You don't have Malevolence Unlocked");
			return;
		}

		if (curse == CurseData.MALEVOLENCE) {
			if (!player.unlockedGeovigorPrayer) {
				player.msgRed("You Must have Geovigor Unlocked.");
				deactivateCurse(player, curse);
				player.getPacketSender().sendConfig(curse.configId, 0);
				return;
			}
			if (!player.unlockedSeaslicerPrayer) {
				player.msgRed("You Must have SeaSlicer Unlocked.");
				deactivateCurse(player, curse);
				player.getPacketSender().sendConfig(curse.configId, 0);
				return;
			}
			if (!player.unlockedBlazeupPrayer) {
				player.msgRed("You Must have BlazeUp Unlocked.");
				deactivateCurse(player, curse);
				player.getPacketSender().sendConfig(curse.configId, 0);
				return;
			}
		}

		if (curse == CurseData.DESOLATION && !player.isUnlockedDesolationPrayer()) {
			deactivateCurse(player, curse);
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.msgRed("You don't have Desolation Unlocked!");
			return;
		}

		if (curse == CurseData.DESOLATION) {
			if (!player.unlockedStonehavenPrayer) {
				player.msgRed("You Must have Stonehaven Unlocked.");
				deactivateCurse(player, curse);
				player.getPacketSender().sendConfig(curse.configId, 0);
				return;
			}
			if (!player.unlockedSwifttidePrayer) {
				player.msgRed("You Must have SwiftTide Unlocked.");
				deactivateCurse(player, curse);
				player.getPacketSender().sendConfig(curse.configId, 0);
				return;
			}
			if (!player.unlockedInfernoPrayer) {
				player.msgRed("You Must have Inferno Unlocked.");
				deactivateCurse(player, curse);
				player.getPacketSender().sendConfig(curse.configId, 0);
				return;
			}
		}

		if (curse.requirement == 120) {
			for (CurseData other : CurseData.values()) {
				if (other != curse
						&& other.requirement == 120
						&& player.getCurseActive()[other.ordinal()]) {
					player.getPacketSender().sendConfig(curse.configId, 0);
					player.sendMessage("@red@You can only have one level-120 curse active at a time.");
					return;
				}
			}
		}

		switch (curse) {
			// ─── skip all deactivations for 120-level curses ───
			case STONEHAVEN:
			case SWIFTTIDE:
			case INFERNO:
			case DESOLATION:
			case MALEVOLENCE:
				// 120-level curses no longer disable any others
				break;
			// ─── all the other curses still disable the conflicting sets ───
			case GAIABLESSING:
			case EBBFLOW:
			case CINDERSTOUCH:
				deactivateCurses(player, OVERHEAD_PRAYERS);
				deactivateCurses(player, COMBINED_PRAYERS);
				break;
			case EMBERBLAST:
			case AQUASTRIKE:
			case SERENITY:
				deactivateCurses(player, SPEC_PRAYERS);
				deactivateCurses(player, COMBINED_PRAYERS);
				break;
			case ROCKHEART:
			case RIPTIDE:
			case INFERNIFY:
				deactivateCurses(player, BOOST_PRAYERS);
				deactivateCurses(player, COMBINED_PRAYERS);
				break;
			case GEOVIGOR:
			case SEASLICER:
			case BLAZEUP:
				deactivateCurses(player, BONUS_PRAYERS);
				deactivateCurses(player, COMBINED_PRAYERS);
				break;
			case BLOCK_MELEE:
			case BLOCK_RANGE:
			case BLOCK_MAGIC:
				deactivateCurses(player, DEFENSE_PRAYERS);
				break;
			default:
				break;
		}


		if (curse.prayerAnimation != null) {
			player.performAnimation(curse.prayerAnimation.animation);
			player.performGraphic(curse.prayerAnimation.graphic);
		}
		player.setCurseActive(curse.ordinal(), true);
		player.getPacketSender().sendConfig(curse.configId, 1);
		int hintId = getHeadHint(player);
		if (hintId != -1)
			player.getAppearance().setHeadHint(hintId);
		if (noActiveCurse(player, curse) && !player.isDrainingPrayer())
			startDrain(player);
		BonusManager.sendCurseBonuses(player);
	}

	public static void deactivateCurse(Player player, CurseData curse) {
		if (!player.getCurseActive()[curse.ordinal()]) {
			return;

		}
		player.getPacketSender().sendConfig(curse.configId, 0);
		player.setCurseActive(curse.ordinal(), false);
		player.getAppearance().setHeadHint(getHeadHint(player));
		BonusManager.sendCurseBonuses(player);

	}

	public static void deactivateCurses(Player player) {
		for (CurseData curse : CurseData.values()) {
			if (player.getCurseActive()[curse.ordinal()]) {
				deactivateCurse(player, curse);
			}
		}
	}

	public static void deactivateAll(Player player) {
		for (CurseData curse : CurseData.values()) {
			player.getPacketSender().sendConfig(curse.configId, 0);
			player.setCurseActive(curse.ordinal(), false);
			player.getAppearance().setHeadHint(getHeadHint(player));
			BonusManager.sendCurseBonuses(player);
		}
        player.getPacketSender().resetQuickCurses();
	}

	private static void deactivateCurses(Player player, CurseData[] curses) {
		for (CurseData curse : curses) {
			if (player.getCurseActive()[curse.ordinal()]) {
				deactivateCurse(player, curse);
			}
		}
	}

	private static boolean noActiveCurse(Player player, CurseData exception) {
		for (CurseData data : CurseData.values()) {
			if (player.getCurseActive()[data.ordinal()] && data != exception) {
				return false;
			}
		}
		return true;
	}

	public static int getHeadHint(Player player) {
		boolean[] active = player.getCurseActive();
		if (active[CurseData.BLOCK_MELEE.ordinal()])
			return 15;
		if (active[CurseData.BLOCK_RANGE.ordinal()])
			return 16;
		if (active[CurseData.BLOCK_MAGIC.ordinal()])
			return 17;
		if (active[CurseData.GAIABLESSING.ordinal()])
			return 0;
		if (active[CurseData.STONEHAVEN.ordinal()])
			return 4;
		if (active[CurseData.EBBFLOW.ordinal()])
			return 5;
		if (active[CurseData.SWIFTTIDE.ordinal()])
			return 9;
		if (active[CurseData.CINDERSTOUCH.ordinal()])
			return 10;
		if (active[CurseData.INFERNO.ordinal()])
			return 14;
		if (active[CurseData.DESOLATION.ordinal()])
			return 19;
		return -1;
	}

	public static int drainticks = 0;
	public static void startDrain(Player player) {

		if (getDrain(player) <= 0)
			return;
		player.setDrainingPrayer(true);
		TaskManager.submit(new Task(1, player, true) {
			@Override
			public void execute() {
				drainticks++;

				if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) <= 0) {
					for (CurseData curse : CurseData.values()) {
						if (player.getCurseActive()[curse.ordinal()]) {
							deactivateCurse(player, curse);
						}
					}
					player.getPacketSender().sendMessage("You have run out of Prayer points!");
					stop();
					return;
				}
				double drain = getDrain(player);

				if (drainticks == 3) {
					int total = (int) (player.getSkillManager().getCurrentLevel(Skill.PRAYER) - drain);
					player.getSkillManager().setCurrentLevel(Skill.PRAYER, total, true);
					drainticks *= 0;
				}
			}

			@Override
			public void stop() {
				setEventRunning(false);
				player.setDrainingPrayer(false);
			}
		});
	}

	private static double getDrain(Player player) {
		double toRemove = .5;
		for (CurseData curse : CurseData.values()) {
			if (player.getCurseActive()[curse.ordinal()]) {
				toRemove += curse.drainRate;
			}
		}
		toRemove *= 0;

		//INF PRAYER
		if (player.getXpmode() == XpMode.MASTER){
			toRemove *= 0;
		}

		//POWERUP INFINITE
		if (player.getGrace().isActive()){
				toRemove *= 0;
		}

        if (PerkManager.currentPerk != null) {
            if (PerkManager.currentPerk.getName().equalsIgnoreCase("Prayer")) {
                toRemove *= 0;
            }
        }

		return toRemove;
	}

	private enum CurseData {
		GAIABLESSING(1, 1, 32503, 610, new PrayerAnimation(new Animation(-1), new Graphic(1308))),
		SERENITY(30, 1, 32505, 611),
		ROCKHEART(55, 1, 32507, 612),
		GEOVIGOR(85, 1, 32509, 613),
		STONEHAVEN(120, 1, 32511, 614),
		EBBFLOW(1, 1, 32513, 615, new PrayerAnimation(new Animation(-1), new Graphic(93))),
		AQUASTRIKE(30, 1, 32515, 616),
		RIPTIDE(55, 1, 32517, 617),
		SEASLICER(85, 1, 32519, 618),
		SWIFTTIDE(120, 1, 32521, 619),
		CINDERSTOUCH(1, 1, 32523, 620, new PrayerAnimation(new Animation(-1), new Graphic(1393))),
		EMBERBLAST(30, 1, 32525, 621),
		INFERNIFY(55, 1, 32527, 622),
		BLAZEUP(85, 1, 32529, 623),
		INFERNO(120, 1, 32531, 624),
		BLOCK_MELEE(1, 1, 32533, 625),
		BLOCK_RANGE(1, 1, 32535, 626),
		BLOCK_MAGIC(1, 1, 32537, 627),
		MALEVOLENCE(120, 1, 32539, 628),
		DESOLATION(120, 1, 32541, 629);

		private CurseData(int requirement, double drainRate, int buttonId, int configId,
						  PrayerAnimation... animations) {
			this.requirement = requirement;
			this.drainRate = drainRate;
			this.buttonId = buttonId;
			this.configId = configId;
			this.prayerAnimation = animations.length > 0 ? animations[0] : null;
			this.name = NameUtils.capitalizeWords(toString().toLowerCase().replaceAll("_", " "));
		}

		private final int requirement;

		private final double drainRate;

		private final int buttonId;

		private final int configId;

		private final PrayerAnimation prayerAnimation;

		private final String name;

		private static Map<Integer, CurseData> buttons = new HashMap<>();

		private static Map<Integer, CurseData> ids = new HashMap<>();

		static {
			for (CurseData data : CurseData.values()) {
				buttons.put(data.buttonId, data);
				ids.put(data.ordinal(), data);
			}
		}
	}

	public static final CurseData[] OVERHEAD_PRAYERS = {
			CurseData.GAIABLESSING, CurseData.EBBFLOW, CurseData.CINDERSTOUCH};
	private static final CurseData[] SPEC_PRAYERS = {
			CurseData.SERENITY, CurseData.AQUASTRIKE, CurseData.EMBERBLAST};
	private static final CurseData[] BOOST_PRAYERS = {
			CurseData.ROCKHEART, CurseData.RIPTIDE, CurseData.INFERNIFY};
	private static final CurseData[] BONUS_PRAYERS = {
			CurseData.GEOVIGOR, CurseData.SEASLICER, CurseData.BLAZEUP};
	private static final CurseData[] DEFENSE_PRAYERS = {
			CurseData.BLOCK_MELEE, CurseData.BLOCK_RANGE, CurseData.BLOCK_MAGIC};

	public static final CurseData[] COMBINED_PRAYERS = {
			CurseData.STONEHAVEN, CurseData.SWIFTTIDE ,CurseData.INFERNO, CurseData.DESOLATION};



	public static final int
			GAIABLESSING = CurseData.GAIABLESSING.ordinal(),
			SERENITY = CurseData.SERENITY.ordinal(),
			ROCKHEART = CurseData.ROCKHEART.ordinal(),
			GEOVIGOR = CurseData.GEOVIGOR.ordinal(),
			STONEHAVEN = CurseData.STONEHAVEN.ordinal(),
			EBBFLOW = CurseData.EBBFLOW.ordinal(),
			AQUASTRIKE = CurseData.AQUASTRIKE.ordinal(),
			RIPTIDE = CurseData.RIPTIDE.ordinal(),
			SEASLICER = CurseData.SEASLICER.ordinal(),
			SWIFTTIDE = CurseData.SWIFTTIDE.ordinal(),
			CINDERSTOUCH = CurseData.CINDERSTOUCH.ordinal(),
			EMBERBLAST = CurseData.EMBERBLAST.ordinal(),
			INFERNIFY = CurseData.INFERNIFY.ordinal(),
			BLAZEUP = CurseData.BLAZEUP.ordinal(),
			INFERNO = CurseData.INFERNO.ordinal(),
			BLOCK_MELEE = CurseData.BLOCK_MELEE.ordinal(),
			BLOCK_RANGE = CurseData.BLOCK_RANGE.ordinal(),
			BLOCK_MAGIC = CurseData.BLOCK_MAGIC.ordinal(),
			MALEVOLENCE = CurseData.MALEVOLENCE.ordinal(),
			DESOLATION = CurseData.DESOLATION.ordinal();

	public static CurseData forId(int id) {
		for (CurseData data : CurseData.values()) {
			if (data.ordinal() == id)
				return data;
		}
		return null;
	}

	private static class PrayerAnimation {

		private PrayerAnimation(Animation animation, Graphic graphic) {
			this.animation = animation;
			this.graphic = graphic;
		}

		private final Animation animation;

		private final Graphic graphic;
	}
}
