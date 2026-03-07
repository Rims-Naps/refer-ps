package com.ruse.world.entity.impl.player;

import com.ruse.GameSettings;
import com.ruse.donation.PersonalCampaignHandler;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.RegionInstance.RegionInstanceType;
import com.ruse.util.Misc;
import com.ruse.world.content.Consumables;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.content.PlayerPunishment;
import com.ruse.world.content.PrayerMinigame.PrayerMinigame;
import com.ruse.world.content.achievement.AchievementHandler;
import com.ruse.world.content.combat.effect.BurnEffect;
import com.ruse.world.content.combat.pvp.BountyHunter;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.impl.construction.House;
import com.ruse.world.entity.impl.GroundItemManager;

import java.util.concurrent.TimeUnit;

import static com.ruse.model.Skill.*;
import static com.ruse.model.container.impl.Equipment.AMMUNITION_SLOT;

public class PlayerProcess {
	private int TWENTY_FOUR_HOUR_MILLI = 86400000;

	/*
	 * The player (owner) of this instance
	 */
	private Player player;

	/*
	 * The loyalty tick, once this reaches 6, the player will be given loyalty
	 * points. 6 equals 3.6 seconds.
	 */
	private int loyaltyTick;

	/*
	 * The timer tick, once this reaches 2, the player's total play time will be
	 * updated. 2 equals 1.2 seconds.
	 */
	private int timerTick;


	/*
	 * Makes sure ground items are spawned on height change
	 */
	private int previousHeight;

	public PlayerProcess(Player player) {
		this.player = player;
		this.previousHeight = player.getPosition().getZ();
	}

	public void sequence() {

		/** COMBAT **/
		player.getCombatBuilder().process();
		player.getControllerManager().process();

		/** SKILLS **/
		if (player.shouldProcessFarming()) {
			player.getFarming().sequence();
		}

		if (previousHeight != player.getPosition().getZ()) {
			GroundItemManager.handleRegionChange(player);
			previousHeight = player.getPosition().getZ();
		}
		if (player.getInterfaceId() == 36000 && player.getAchievements().currentInterface == 3) {
			player.getPA().sendString(36503, "Time Left: " + AchievementHandler.getTimeLeft());
		}

        player.getMembership().process(player);

		Position location = player.getPosition().copy();

		if (loyaltyTick >= 2) {
			if (player.getLocation() == Location.CORRUPT_DUNGEON) {
				int chance_for_damage = Misc.random(2);
				boolean corrupt_helm = player.getEquipment().contains(2677) ||  player.getEquipment().contains(2678) || player.getEquipment().contains(2679);
				if (chance_for_damage == 0 && !corrupt_helm){
					player.dealDamage(new Hit(Misc.random(1, 2), Hitmask.FIRE, CombatIcon.NONE));
				}
			}
		}

			if (!player.isInActive()) {
			boolean slayer = player.getPosition().getRegionId() == 9022 || player.getPosition().getRegionId() == 9023 || player.getPosition().getRegionId() == 13376 || player.getPosition().getRegionId() == 13120 || player.getPosition().getRegionId() == 8522 || player.getPosition().getRegionId() == 8522 || player.getPosition().getRegionId() == 8778 || player.getLocation() == Location.SPECTRAL_DUNGEON || player.getLocation() == Location.CORRUPT_DUNGEON || player.getLocation() == Location.ENCHANTED_FOREST
					|| player.getPosition().getRegionId() == 10542 || player.getPosition().getRegionId() == 15403 || player.getPosition().getRegionId() == 14164 || player.getPosition().getRegionId() == 11588 || player.getPosition().getRegionId() == 13910 || player.getPosition().getRegionId() == 14676;
			if (loyaltyTick >= 6) {
					if (player.getLocation() != Location.INSTANCE1 || !slayer) {
					PrayerMinigame.updateMinionAreaInterface(player);
					PrayerMinigame.updateBossAreaInterface(player);
				}
				if (slayer){
					player.getPacketSender().sendWalkableInterface(4958, true);
					player.getPacketSender().sendString(4960, " " + player.getSlayer().getSlayerTask().getName());
					player.getPacketSender().sendString(4961, "Task Amount:");
					player.getPacketSender().sendString(4962, player.getSlayer().getAmountToSlay());
				}

					if (player.getLocation() == Location.INSTANCE1){
					if (player.getCurrentInstanceAmount() > 0){
						player.getPacketSender().sendWalkableInterface(4958, true);
						player.getPacketSender().sendString(4960, ""  + player.getCurrentInstanceNpcName());
						player.getPacketSender().sendString(4961, "Kills left:");
						player.getPacketSender().sendString(4962, player.getCurrentInstanceAmount());
					}
				}

                if (player.getFury().isActive()) {
                    Consumables.levelIncrease(player, ATTACK, player.getSkillManager().getMaxLevel(ATTACK) + 12);
                    Consumables.levelIncrease(player, Skill.STRENGTH, player.getSkillManager().getMaxLevel(STRENGTH) + 12);
                    Consumables.levelIncrease(player, Skill.DEFENCE, player.getSkillManager().getMaxLevel(DEFENCE) + 12);
                    Consumables.levelIncrease(player, Skill.RANGED, player.getSkillManager().getMaxLevel(RANGED) + 12);
                    Consumables.levelIncrease(player, Skill.MAGIC, player.getSkillManager().getMaxLevel(MAGIC) + 12);
                } else if (player.getRage().isActive()) {
                    Consumables.levelIncrease(player, ATTACK, player.getSkillManager().getMaxLevel(ATTACK) + 18);
                    Consumables.levelIncrease(player, Skill.STRENGTH, player.getSkillManager().getMaxLevel(STRENGTH) + 18);
                    Consumables.levelIncrease(player, Skill.DEFENCE, player.getSkillManager().getMaxLevel(DEFENCE) + 18);
                    Consumables.levelIncrease(player, Skill.RANGED, player.getSkillManager().getMaxLevel(RANGED) + 18);
                    Consumables.levelIncrease(player, Skill.MAGIC, player.getSkillManager().getMaxLevel(MAGIC) + 18);
                }
				PlayerPanel.refreshPanel(player);
                PersonalCampaignHandler.reset(player);
				//LoyaltyProgramme.incrementPoints(player);
				loyaltyTick = 0;
			}
			loyaltyTick++;
		}

		timerTick++;

		if (player.getRegionInstance() != null
				&& (player.getRegionInstance().getType() == RegionInstanceType.CONSTRUCTION_HOUSE
				|| player.getRegionInstance().getType() == RegionInstanceType.CONSTRUCTION_DUNGEON)) {
			((House) player.getRegionInstance()).process();
		}

		if (PlayerPunishment.Jail.isJailed(player.getUsername()) && !Locations.Location.inLocation(player, Location.JAIL)) {
			player.moveTo(new Position(2507, 9324));
		}



		if (!player.getCombatBuilder().isAttacking() && !player.getCombatBuilder().isBeingAttacked()) {
			if (!player.getAfkCombatChecker().isAnsweringQuestions())
				player.getAfkCombatChecker().getAfkCombatTimer().reset();
		}

		player.getAfkCombatChecker().check();


		if (player.getStartDailyTimer() + TWENTY_FOUR_HOUR_MILLI < System.currentTimeMillis()) {
			player.setStartDailyTimer(System.currentTimeMillis());
			player.getDailyEntries().clear();
			player.getDailyTaskInterface().randomizeTask(1);
			player.getDailyTaskInterface().randomizeTask(9);
			player.getDailyTaskInterface().randomizeTask(10);
			player.getDailyTaskInterface().randomizeTask(0);
			player.getSlotSystem().setDailyGambles(0);
			player.setDailyBattles(0);
		}

		int second = (int) TimeUnit.MILLISECONDS
				.toSeconds((player.getStartDailyTimer() + TWENTY_FOUR_HOUR_MILLI) - System.currentTimeMillis())
				% 60;
		int hour = (int) TimeUnit.MILLISECONDS
				.toHours((player.getStartDailyTimer() + TWENTY_FOUR_HOUR_MILLI) - System.currentTimeMillis()) % 24;
		int minute = (int) TimeUnit.MILLISECONDS
				.toMinutes((player.getStartDailyTimer() + TWENTY_FOUR_HOUR_MILLI) - System.currentTimeMillis())
				% 60;
		if (player.getInterfaceId() == 4509) {
			player.getPacketSender().sendString(4591, "Reset: " + hour + ":" + minute + ":" + second);
		}
		/*if (player.afkTicks >= 500 && !player.afk) {
			player.moveTo(new Position(2658, 3987, 0));
			player.afk = true;
		}*/

		// Process callables
		player.executeProcessCallables();

	}
}
