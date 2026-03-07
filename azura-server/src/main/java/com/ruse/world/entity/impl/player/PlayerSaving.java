package com.ruse.world.entity.impl.player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.ruse.GameServer;
import com.ruse.GameSettings;
import com.ruse.PlayerSetting;
import com.ruse.util.Misc;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.tradingpost.PlayerShopRepo;
import org.apache.commons.lang3.text.WordUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;

public class PlayerSaving {


	public static void save(Player player) {
		if (player.newPlayer())
			return;
		// Create the path and file objects.
		Path path = Paths.get("./data/saves/characters/", player.getUsername() + ".json");
		File file = path.toFile();
		file.getParentFile().setWritable(true);

		// Attempt to make the player save directory if it doesn't
		// exist.
		if (!file.getParentFile().exists()) {
			try {
				file.getParentFile().mkdirs();
			} catch (SecurityException e) {
				// System.out.println("Unable to create directory for player data!");
			}
		}

		try {
			PlayerShopRepo.serialize(player.getPlayerShops().getShop());
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try (FileWriter writer = new FileWriter(file)) {

			Gson builder = new GsonBuilder().setPrettyPrinting().create();
			JsonObject object = new JsonObject();
			object.addProperty("total-play-time-ms", player.getTotalPlayTime());
			object.addProperty("username", player.getUsername().trim());

			if (GameSettings.BCRYPT_HASH_PASSWORDS) {
				object.addProperty("hash", BCrypt.hashpw(player.getPassword(), player.getSalt()));
			} else {
				object.addProperty("password", player.getPassword().trim());
			}

			object.addProperty("email", player.getEmailAddress() == null ? "null" : player.getEmailAddress().trim());
			object.addProperty("staff-rights", player.getRights().name());
			object.addProperty("game-mode", player.getGameMode().name());
            object.addProperty("membership-tier", player.getMembershipTier().name());
			object.addProperty("xp-mode", player.getXpmode().name());
			object.addProperty("path", player.getBoostMode().name());
			object.addProperty("crit", player.getCritchance());


			object.add("currency-pouch", builder.toJsonTree(player.getCurrencyPouch()));

			object.addProperty("tutDmgSaltMined", player.getTutDmgSaltMined());
			object.addProperty("tutDrSaltMined", player.getTutDrSaltMined());
			object.addProperty("tutCritSaltMined", player.getTutCritSaltMined());

			object.addProperty("holyGrailDroprate", player.isHolyGrailDroprate());


			object.addProperty("curserunes", player.getCurseRunes());
			object.addProperty("soulrunes", player.getSoulRunes());
			object.addProperty("voidrunes", player.getVoidRunes());
			object.addProperty("shadowrunes", player.getShadowRunes());
			object.addProperty("cryptrunes", player.getCryptRunes());
			object.addProperty("wraithrunes", player.getWraithRunes());

			object.addProperty("damageSalts", player.getDamageSalts());
			object.addProperty("criticalSalts", player.getCriticalSalts());
			object.addProperty("droprateSalts", player.getDroprateSalts());

			object.addProperty("corruptRaidsDone", player.getCorruptCompletions());
			object.addProperty("corruptMediumRaidsDone", player.getCorruptMediumCompletions());
			object.addProperty("corruptHardRaidsDone", player.getCorruptHardCompletions());



			object.addProperty("voidRaidsDone", player.getVoidCompletions());
			object.addProperty("voidMediumRaidsDone", player.getVoidMediumCompletions());
			object.addProperty("voidHardRaidsDone", player.getVoidHardCompletions());

			object.addProperty("coedone", player.getCOECompletions());


			object.addProperty("spectral-essence", player.getSpectralEssence());


			object.addProperty("fireshards", player.getFireessence());
			object.addProperty("watershards", player.getWateressence());
			object.addProperty("earthshards", player.getEarthessence());
			object.addProperty("slayershards", player.getSlayeressence());
			object.addProperty("monstershards", player.getMonsteressence());


			object.addProperty("beastEssence", player.getBeastEssence());
			object.addProperty("corruptEssence", player.getCorruptEssence());

			object.addProperty("enchantedEssence", player.getEnchantedEssence());


			object.addProperty("vorpalAmmo", player.getVorpalAmmo());
			object.addProperty("bloodstainedAmmo", player.getBloodstainedAmmo());
			object.addProperty("symbioteammo", player.getSymbioteAmmo());
			object.addProperty("netherammo", player.getNetherAmmo());



			object.addProperty("infiniteprayer", player.isInfinitePrayer());


			object.addProperty("easyTasksDone", player.getEasyTasksDone());
			object.addProperty("mediumTasksDone", player.getMediumTasksDone());
			object.addProperty("eliteTasksDone", player.getEliteTasksDone());
			object.addProperty("beastTasksDone", player.getBeastTasksDone());
			object.addProperty("totalKeysOpened", player.getTotalKeysOpened());
			object.addProperty("totalBoxesOpened", player.getTotalBoxesOpened());
			object.addProperty("totalSoulbaneRuns", player.getTotalSoulbaneRuns());

			object.addProperty("xmasTasks", player.getEasyXmasTasks());

			object.addProperty("souls", player.getSouls());



			object.addProperty("loyalty-title", player.getLoyaltyTitle().name());
			object.add("collection-log", builder.toJsonTree(player.getCollectionLogManager().getLogProgress()));
			object.add("claimed-logs", builder.toJsonTree(player.getCollectionLogManager().getClaimedCollectionRewards()));
			object.addProperty("fixedFerry", player.isFixedFerry());
			object.addProperty("claimedLantern", player.isClaimedLantern());
			object.addProperty("posAbility", player.isPosAbility());
			/** HEX YELL COLORS **/
			object.add("position", builder.toJsonTree(player.getPosition()));
			object.addProperty("npc-killcount", player.getPointsHandler().getNPCKILLCount());
			object.addProperty("online-status", player.getRelations().getStatus().name());
			object.addProperty("given-starter", player.didReceiveStarter());
			object.addProperty("openedstarterbox", player.isOpenedStarterBox());
			object.addProperty("has-pin2", player.getHasPin());
			object.addProperty("saved-pin2", player.getSavedPin());
			object.addProperty("saved-ip", player.getSavedIp());
			object.addProperty("donated", (long) player.getAmountDonated());
			object.addProperty("door-picks-left", player.getDoorPicksLeft());
			object.addProperty("MyFairy", builder.toJson(player.getCompanion().getMyCompanion()));
			object.addProperty("dr-boost", player.getDrBoost());
			object.addProperty("dmg-boost", player.getDmgBoost());
			object.addProperty("upg-boost", player.getUpgBoost());
			object.addProperty("pouchmessagesenabled", player.isPouchMessagesEnabled());
			object.addProperty("globalDropMessagesEnabled", player.isGlobalDropMessagesEnabled());
			object.addProperty("critmessageenabled", player.isCritMessagesEnabled());

			object.addProperty("madeDRpot", player.isMadeDRpot());
			object.addProperty("madeDivinePotion", player.isMadeDivinePotion());
			object.addProperty("madeCritPot", player.isMadeCritPot());
			object.addProperty("madeDmgPot", player.isMadeDmgPot());

			object.addProperty("fury-time-left", player.getFury().getTimeForTask());
			object.addProperty("rage-time-left", player.getRage().getTimeForTask());

			object.addProperty("voteboost-time-left", player.getVoteBoost().getTimeForTask());

			object.addProperty("noob-time-left", player.getNoob().getTimeForTask());

			object.addProperty("killboost-time-left", player.getKillBoost().getTimeForTask());
			object.addProperty("raidboost-time-left", player.getRaidBoost().getTimeForTask());
			object.addProperty("necroboost-time-left", player.getNecroBoost().getTimeForTask());
			object.addProperty("slayerboost-time-left", player.getSlayerBoost().getTimeForTask());


			object.addProperty("divine-time-left", player.getDivine().getTimeForTask());

			object.addProperty("dr-time-left", player.getDroprateTimer().getTimeForTask());
			object.addProperty("crit-time-left", player.getCritTimer().getTimeForTask());
			object.addProperty("dmg-time-left", player.getDamageTimer().getTimeForTask());
			object.addProperty("ice-time-left", player.getIceBornTimer().getTimeForTask());
			object.addProperty("love-time-left", player.getLoveTImer().getTimeForTask());
			object.addProperty("egg-time-left", player.getEasterTimer().getTimeForTask());


			object.addProperty("magma-time-left", player.getMagma().getTimeForTask());
			object.addProperty("overgrown-time-left", player.getOvergrown().getTimeForTask());
			object.addProperty("nautic-time-left", player.getNautic().getTimeForTask());



			object.addProperty("current-boss-task", player.getCurrentBossTask());
			object.addProperty("current-boss-amount", player.getCurrentBossTaskAmount());
			object.addProperty("has-completed-boss-task", player.isHasPlayerCompletedBossTask());
			object.addProperty("current-daily-task-id", player.getCurrentDailyTask());
			object.addProperty("current-daily-task-amount", player.getCurrentDailyTaskAmount());
			object.addProperty("yellhexcolor", player.getYellHex() == null ? "AF70C3" : player.getYellHex());
			object.addProperty("stack-shards", player.getstackCharges());
			object.addProperty("slayer-master", player.getSlayer().getSlayerMaster().name());
			object.addProperty("slayer-task", player.getSlayer().getSlayerTask().name());
			object.addProperty("prev-slayer-task", player.getSlayer().getLastTask().name());
			object.addProperty("task-amount", player.getSlayer().getAmountToSlay());
			object.addProperty("task-streak", player.getSlayer().getTaskStreak());
			object.addProperty("task-streak-Beast-MasterI", player.getSlayer().getTaskStreakBeastI());
			object.addProperty("task-streak-Beast-MasterX", player.getSlayer().getTaskStreakBeastX());
			object.addProperty("godstreak", player.getSlayer().getGodstreak());
			object.addProperty("dragonstreak", player.getSlayer().getDragonstreak());

			object.addProperty("killmesssageamount", player.getKillmessageamount());
			object.addProperty("unlockedSkeletalSpells", player.isUnlockedSkeletalSpells());
			object.addProperty("unlockedDemonicSpells", player.isUnlockedDemonicSpells());
			object.addProperty("unlockedOgreSpells", player.isUnlockedOgreSpells());
			object.addProperty("unlockedDragonicSpells", player.isUnlockedSpectralSpells());
			object.addProperty("unlockedMasterSpells", player.isUnlockedMasterSpells());


			object.addProperty("holidayTask", player.getHolidayTaskHandler().isHasTask());
			object.addProperty("holidayPoints", player.getHolidayPoints());
			object.addProperty("holidayNpc", player.getHolidayTaskHandler().getTaskNpc());
			object.addProperty("holidayAmount", player.getHolidayTaskHandler().getTaskAmt());
			object.addProperty("holidayReward", player.getHolidayTaskHandler().getTaskRewardPt());



			object.addProperty("unlockedGaiablessingPrayer", player.isUnlockedGaiablessingPrayer());
			object.addProperty("unlockedSerenityPrayer", player.isUnlockedSerenityPrayer());
			object.addProperty("unlockedRockheartPrayer", player.isUnlockedRockheartPrayer());
			object.addProperty("unlockedGeovigorPrayer", player.isUnlockedGeovigorPrayer());
			object.addProperty("unlockedStonehavenPrayer", player.isUnlockedStonehavenPrayer());
			object.addProperty("unlockedEbbflowPrayer", player.isUnlockedEbbflowPrayer());
			object.addProperty("unlockedAquastrikePrayer", player.isUnlockedAquastrikePrayer());
			object.addProperty("unlockedRiptidePrayer", player.isUnlockedRiptidePrayer());
			object.addProperty("unlockedSeaslicerPrayer", player.isUnlockedSeaslicerPrayer());
			object.addProperty("unlockedSwifttidePrayer", player.isUnlockedSwifttidePrayer());
			object.addProperty("unlockedCindersTouch", player.isUnlockedCindersTouch());
			object.addProperty("unlockedEmberblastPrayer", player.isUnlockedEmberblastPrayer());
			object.addProperty("unlockedInfernifyPrayer", player.isUnlockedInfernifyPrayer());
			object.addProperty("unlockedBlazeupPrayer", player.isUnlockedBlazeupPrayer());
			object.addProperty("unlockedInfernoPrayer", player.isUnlockedInfernoPrayer());
			object.addProperty("unlockedVoid1Prayer", player.isUnlockedMalevolencePrayer());
			object.addProperty("unlockedVoid2Prayer", player.isUnlockedDesolationPrayer());


			object.addProperty("presets-owned", player.getPresetManager().getPresetsOwned());
			for (int i = 0; i < player.getPresetManager().getPresetsOwned(); i++) {
				object.add("presets-" + i, builder.toJsonTree(player.getPresetManager().getPresets()[i]));

			}
			object.add("equipment", builder.toJsonTree(player.getEquipment().getItems()));
			object.add("cosmetics", builder.toJsonTree(player.getCosmetics().getItems()));
			object.add("presets", builder.toJsonTree(player.getPresetManager().getPresets()));
			object.addProperty("wearing-preset", player.getPresetManager().getWearingPreset());
			object.add("preset-names", builder.toJsonTree(player.getPresetManager().getPresetNames()));


			object.addProperty("current-daily-task-x-pos", player.getxPosDailyTask());
			object.addProperty("current-daily-task-y-pos", player.getyPostDailyTask());
			object.addProperty("current-daily-task-z-pos", player.getzPosDailyTask());
			object.addProperty("current-daily-task-reward", player.getRewardDailyTask());
			object.add("starter-task-amount", builder.toJsonTree(player.getStarterTasks().getAmountRemaining()));
			object.add("starter-task-completed", builder.toJsonTree(player.getStarterTasks().getCompleted()));

			object.add("medium-task-amount", builder.toJsonTree(player.getMediumTasks().getAmountRemaining()));
			object.add("medium-task-completed", builder.toJsonTree(player.getMediumTasks().getCompleted()));

			object.add("elite-task-amount", builder.toJsonTree(player.getEliteTasks().getAmountRemaining()));
			object.add("elite-task-completed", builder.toJsonTree(player.getEliteTasks().getCompleted()));

			object.add("master-task-amount", builder.toJsonTree(player.getMasterTasks().getAmountRemaining()));
			object.add("master-task-completed", builder.toJsonTree(player.getMasterTasks().getCompleted()));

			object.add("uim-bank", builder.toJsonTree(player.getUimBankItems()));
			object.add("days-voted", builder.toJsonTree(player.getStreak().getDaysVoted()));
            object.addProperty("last-vote", String.valueOf(player.getStreak().getLastVote()));
            object.addProperty("next-countable-vote", String.valueOf(player.getStreak().getNextCountableVote()));
            object.addProperty("vote-streak", player.getStreak().getCurrentStreak());
			object.addProperty("daysVoted", player.getDaysVoted());
			object.addProperty("totalTimesClaimed", player.getTotalTimesClaimed());
			object.addProperty("longestDaysVoted", player.getLongestDaysVoted());
			object.addProperty("timeUntilNextReward", player.getTimeUntilNextReward());
			object.addProperty("lastVoted", player.getLastVoted());
			object.addProperty("current-voting-streak", player.getCurrentVotingStreak());
			object.addProperty("entriesCurrency", player.getEntriesCurrency());
			object.addProperty("amount-donated-today", player.getAmountDonatedToday());
			object.addProperty("claimed-first", player.claimedFirst);
			object.addProperty("claimed-second", player.claimedSecond);
			object.addProperty("claimed-third", player.claimedThird);
			object.addProperty("last-donation", player.lastDonation);
			object.addProperty("last-time-reset", player.lastTimeReset);
			object.addProperty("lastlogin", player.lastLogin);
			object.addProperty("lastdailyclaim", player.lastDailyClaim);
			object.addProperty("lastvotetime", player.lastVoteTime);
			object.addProperty("hasvotedtoday", player.hasVotedToday);
			object.addProperty("day1claimed", player.day1Claimed);
			object.addProperty("day2claimed", player.day2Claimed);
			object.addProperty("day3claimed", player.day3Claimed);
			object.addProperty("day4claimed", player.day4Claimed);
			object.addProperty("day5claimed", player.day5Claimed);
			object.addProperty("day6claimed", player.day6Claimed);
			object.addProperty("day7claimed", player.day7Claimed);
			object.addProperty("minutes-bonus-exp", player.getMinutesBonusExp());
			object.addProperty("minutes-voting-dr", player.getMinutesVotingDR());
			object.addProperty("minutes-voting-dmg", player.getMinutesVotingDMG());
			object.addProperty("total-gained-exp", player.getSkillManager().getTotalGainedExp());
			object.addProperty("barrows-points", player.getPointsHandler().getBarrowsPoints());
			object.addProperty("donator-points", player.getPointsHandler().getDonatorPoints());
			object.addProperty("refund-points", player.getPointsHandler().getRefundPoints());
			object.addProperty("Skilling-points", player.getPointsHandler().getSkillingPoints());
			object.addProperty("prestige-points", player.getPointsHandler().getPrestigePoints());
			object.addProperty("achievement-points", player.getPointsHandler().getAchievementPoints());
			object.addProperty("dung-tokens", player.getPointsHandler().getDungeoneeringTokens());
			object.addProperty("commendations", player.getPointsHandler().getCommendations());
			object.addProperty("loyalty-points", player.getPointsHandler().getLoyaltyPoints());




			object.addProperty("vgTutStage", player.getVgTutStage());

			object.addProperty("marinaTaskID", player.getMarinaTaskID());

			object.addProperty("dailyMarinaTasks", player.getDailyMarinaTasks());


			object.addProperty("gaiaCrystalStage", player.getGaiaCrystalStage());
			object.addProperty("lavaCrystalStage", player.getLavaCrystalStage());
			object.addProperty("aquaCrystalStage", player.getAquaCrystalStage());
			object.addProperty("voidCrystalStage", player.getVoidCrystalStage());


			object.addProperty("prayerMinigameBossInstanceActive", player.isPrayerMinigameBossInstanceActive());
			object.addProperty("prayerMinigameMinionKills", player.getPrayerMinigameMinionKills());
			object.addProperty("prayerMinigameBossKillsLeft", player.getPrayerMinigameBossKillsLeft());
			object.addProperty("aresTrialsInstanceActive", player.isAresTrialsInstanceActive());

			object.addProperty("soulbaneStreak", player.getSoulbaneStreak());

			object.addProperty("completedbeginner", player.isCompletedBeginner());
			object.addProperty("claimedGear", player.isClaimedGear());

			object.addProperty("changedGamemodes", player.isHasChangedGameMode());

			object.addProperty("cosmeticHeadOn", player.isCosmeticHeadOn());
			object.addProperty("cosmeticBodyOn", player.isCosmeticBodyOn());
			object.addProperty("cosmeticLegsOn", player.isCosmeticLegsOn());
			object.addProperty("cosmeticGlovesOn", player.isCosmeticGlovesOn());
			object.addProperty("cosmeticBootsOn", player.isCosmeticBootsOn());
			object.addProperty("cosmeticCapeOn", player.isCosmeticCapeOn());
			object.addProperty("cosmeticWeaponOn", player.isCosmeticWeaponOn());
			object.addProperty("cosmeticShieldOn", player.isCosmeticShieldOn());
			object.addProperty("cosmeticAmuletOn", player.isCosmeticAmuletOn());


			object.addProperty("unlockedIntermediateZones", player.isUnlockedIntermediateZones());
			object.addProperty("unlockedEliteZones", player.isUnlockedEliteZones());
			object.addProperty("unlockedMasterZones", player.isUnlockedMasterZones());
			object.addProperty("unlockedSpectralZones", player.isUnlockedSpectralZones());

			object.addProperty("trials2Unlocked", player.isTrials2Unlocked());
			object.addProperty("trials3Unlocked", player.isTrials3Unlocked());
			object.addProperty("trials4Unlocked", player.isTrials4Unlocked());
			object.addProperty("trials5Unlocked", player.isTrials5Unlocked());


			object.addProperty("startedTutorial", player.isStartedTutorial());
			object.addProperty("receivedprayerunlock", player.isReceivedprayerunlock());
			object.addProperty("receivedarmor", player.isReceivedarmor());

			object.addProperty("tutTask1Started", player.isTutTask1Started());
			object.addProperty("tutTask1Ready", player.isTutTask1Ready());
			object.addProperty("tutTask1Complete", player.isTutTask1Complete());

			object.addProperty("tutTask2Started", player.isTutTask2Started());
			object.addProperty("tutTask2Ready", player.isTutTask2Ready());
			object.addProperty("tutTask2Complete", player.isTutTask2Complete());

			object.addProperty("tutTask3Started", player.isTutTask3Started());
			object.addProperty("tutTask3Ready", player.isTutTask3Ready());
			object.addProperty("tutTask3Complete", player.isTutTask3Complete());

			object.addProperty("madeCritPotion", player.isSandStage2());
			object.addProperty("madeDropratePotion", player.isSandStage2());
			object.addProperty("madeDamagePotion", player.isSandStage2());

			object.addProperty("tutTask4Started", player.isTutTask4Started());
			object.addProperty("tutTask4Ready", player.isTutTask4Ready());
			object.addProperty("tutTask4Complete", player.isTutTask4Complete());

			object.addProperty("tutTask5Started", player.isTutTask5Started());
			object.addProperty("tutTask5Ready", player.isTutTask5Ready());
			object.addProperty("tutTask5Complete", player.isTutTask5Complete());

			object.addProperty("tutTask6Started", player.isTutTask6Started());
			object.addProperty("tutTask6Ready", player.isTutTask6Ready());
			object.addProperty("tutTask6Complete", player.isTutTask6Complete());

			object.addProperty("defeatedbeast", player.isDefeatedbeast());
			object.addProperty("completedtutorial", player.isCompletedtutorial());

			object.addProperty("skippedTutorial", player.isSkippedTutorial());
			object.addProperty("claimedSkipPrayer", player.isClaimedSkipPrayer());
			object.addProperty("claimedSkipArmor", player.isClaimedSkipArmor());
			object.addProperty("hasManualPrayer", player.isHasManualPrayer());
			object.addProperty("hasManualArmor", player.isHasManualArmor());



			object.addProperty("unlockedWolfCamp", player.isUnlockedWolfCamp());
			object.addProperty("unlockedDemonCamp", player.isUnlockedDemonCamp());
			object.addProperty("unlockedTitanCamp", player.isUnlockedTitanCamp());

			object.addProperty("dailyForestTaskAmount", player.getDailyForestTaskAmount());
			object.addProperty("afkstall1", player.getAfkStallCount1());


			object.addProperty("enchantedtasksdone", player.getEnchantedTasksDone());

			object.addProperty("mysticMushTaskAmount", player.getMysticMushTaskAmount());
			object.addProperty("corruptMushTaskAmount", player.getCorruptMushTaskAmount());



			object.addProperty("permDroprate", player.isClaimedPermDroprate());
			object.addProperty("permDamage", player.isClaimedPermDamage());


			object.addProperty("receivedExtras", player.isReceivedExtras());
			object.addProperty("receivedEaster", player.isReceivedEaster());

			object.addProperty("madeCandyBoost", player.isMadeCandyBoost());

			object.addProperty("godmodetime", player.getGodModeTimer());
			object.addProperty("voting-points", player.getPointsHandler().getVotingPoints());
			object.addProperty("total-prestiges", player.getPointsHandler().getTotalPrestiges());
			object.addProperty("slayer-spree", player.getPointsHandler().getSlayerSpree());

			object.addProperty("reset-season-year", player.getBattlePass().isResetSeasonOne());
			object.addProperty("valpassType", player.getBattlePass().getType().name());//battlePassExp
			object.addProperty("valpasslevel", player.getBattlePass().getType() == BattlePassType.TIER2 ? player.getBattlePass().getBattlePass_Tier2_Level() : player.getBattlePass().getBattlePass_Tier1_Level());//battlePassExp
			object.addProperty("valPassExp", player.getBattlePass().getType() == BattlePassType.TIER2 ? player.getBattlePass().getBattlePass_Tier2_XP() : player.getBattlePass().getBattlePass_Tier1_XP());//
			object.addProperty("valPasst1Claimed", player.BattlePassClaimed);
			object.addProperty("valPasst1Expires", player.BattlePassExpires);
			object.addProperty("valPasst2Claimed", player.BattlePassTier2Claimed);
			object.addProperty("valPasst2Expires", player.BattlePassTier2Expires);

			object.addProperty("event-points", player.getPointsHandler().getEventPoints());
			object.addProperty("boss-points", player.getPointsHandler().getBossPoints());
			object.addProperty("slayer-points", player.getPointsHandler().getSlayerPoints());
			object.addProperty("player-kills", player.getPlayerKillingAttributes().getPlayerKills());
			object.addProperty("player-killstreak", player.getPlayerKillingAttributes().getPlayerKillStreak());
			object.addProperty("player-deaths", player.getPlayerKillingAttributes().getPlayerDeaths());
			object.addProperty("target-percentage", player.getPlayerKillingAttributes().getTargetPercentage());
			object.addProperty("bh-rank", player.getAppearance().getBountyHunterSkull());
			object.addProperty("gender", player.getAppearance().getGender().name());
			object.addProperty("spell-book", player.getSpellbook().name());
			object.addProperty("prayer-book", player.getPrayerbook().name());
			object.addProperty("running", player.isRunning());
			object.addProperty("run-energy", player.getRunEnergy());
			object.addProperty("music", player.musicActive());
			object.addProperty("sounds", player.soundsActive());
			object.addProperty("auto-retaliate", player.isAutoRetaliate());
			object.addProperty("xp-locked", player.experienceLocked());
			object.addProperty("veng-cast", player.hasVengeance());
			object.addProperty("last-veng", player.getLastVengeance().elapsed());
			object.addProperty("fight-type", player.getFightType().name());
			object.addProperty("sol-effect", player.getStaffOfLightEffect());
			object.addProperty("skull-timer", player.getSkullTimer());
			object.addProperty("accept-aid", player.isAcceptAid());
			object.addProperty("poison-damage", player.getBurndamage());
			object.addProperty("poison-immunity", player.getPoisonImmunity());
			object.addProperty("effects-on", player.isEffectson());
			object.addProperty("show-chance", player.isShowMyChance());
			object.addProperty("double-dr-timer", player.getDropRateTimerOld());
			object.addProperty("double-ddr-timer", player.getDoubleDDRTimer());
			object.addProperty("double-dmg-timer", player.getDoubleDMGTimer());
			object.addProperty("fire-immunity", player.getFireImmunity());
			object.addProperty("fire-damage-mod", player.getFireDamageModifier());
			object.addProperty("prayer-renewal-timer", player.getPrayerRenewalPotionTimer());
			object.addProperty("teleblock-timer", player.getTeleblockTimer());
			object.addProperty("special-amount", player.getSpecialPercentage());
			object.addProperty("entered-gwd-room", player.getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom());
			object.addProperty("gwd-altar-delay", player.getMinigameAttributes().getGodwarsDungeonAttributes().getAltarDelay());
			object.add("gwd-killcount", builder.toJsonTree(player.getMinigameAttributes().getGodwarsDungeonAttributes().getKillcount()));
			object.addProperty("effigy", player.getEffigy());
			object.addProperty("summon-npc", player.getSummoning().getFamiliar() != null ? player.getSummoning().getFamiliar().getSummonNpc().getId() : -1);
			object.addProperty("summon-death", player.getSummoning().getFamiliar() != null ? player.getSummoning().getFamiliar().getDeathTimer() : -1);
			object.addProperty("process-farming", player.shouldProcessFarming());
			object.addProperty("clanchat", player.getClanChatName() == null ? "f" : player.getClanChatName().trim());
			object.addProperty("autocast", player.isAutocast());
			object.addProperty("autocast-spell",player.getAutocastSpell() != null ? player.getAutocastSpell().spellId() : -1);
			object.addProperty("duo-partner", player.getSlayer().getDuoPartner() == null ? "null" : player.getSlayer().getDuoPartner());
			object.addProperty("double-slay-xp", player.getSlayer().doubleSlayerXP);
			object.add("killed-players", builder.toJsonTree(player.getPlayerKillingAttributes().getKilledPlayers()));
			object.add("vod-brother", builder.toJsonTree(player.getMinigameAttributes().getVoidOfDarknessAttributes().getBarrowsData()));
			object.addProperty("vod-killcount", player.getMinigameAttributes().getVoidOfDarknessAttributes().getKillcount());
			object.addProperty("hov-killcount", player.getMinigameAttributes().getHallsOfValorAttributes().getKillcount());
			object.add("barrows-brother", builder.toJsonTree(player.getMinigameAttributes().getBarrowsMinigameAttributes().getBarrowsData()));
			object.addProperty("random-coffin", player.getMinigameAttributes().getBarrowsMinigameAttributes().getRandomCoffin());
			object.addProperty("barrows-killcount", player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount());
			object.add("nomad", builder.toJsonTree(player.getMinigameAttributes().getNomadAttributes().getQuestParts()));
			object.addProperty("clue-progress", player.getClueProgress());
			object.add("dung-items-bound", builder.toJsonTree(player.getMinigameAttributes().getDungeoneeringAttributes().getBoundItems()));
			object.add("tracker", builder.toJsonTree(player.getTrackers(), new com.google.common.reflect.TypeToken<HashMap<String, Integer>>(){}.getType()));
			object.add("holy-prayers-unlocked", builder.toJsonTree(player.getUnlockedHolyPrayers()));
			object.add("daily-data", builder.toJsonTree(player.getDailyEntries()));
			object.addProperty("daily-data-timer", player.getStartDailyTimer());
			object.addProperty("rune-ess", player.getStoredRuneEssence());
			object.addProperty("pure-ess", player.getStoredPureEssence());
			object.addProperty("has-bank-pin", player.getBankPinAttributes().hasBankPin());
			object.addProperty("last-pin-attempt", player.getBankPinAttributes().getLastAttempt());
			object.addProperty("invalid-pin-attempts", player.getBankPinAttributes().getInvalidAttempts());
			object.add("bank-pin", builder.toJsonTree(player.getBankPinAttributes().getBankPin()));
			object.add("appearance", builder.toJsonTree(player.getAppearance().getLook()));
			object.add("agility-obj", builder.toJsonTree(player.getCrossedObstacles()));
			object.add("skills", builder.toJsonTree(player.getSkillManager().getSkills()));
			object.add("inventory", builder.toJsonTree(player.getInventory().getItems()));
			object.add("equipment", builder.toJsonTree(player.getEquipment().getItems()));
			object.add("cosmetics", builder.toJsonTree(player.getCosmetics().getItems()));
			object.add("preset-equipment", builder.toJsonTree(player.getPreSetEquipment().getItems()));
			object.add("offences", builder.toJsonTree(player.getOffences()));
			object.add("bank-0", builder.toJsonTree(player.getBank(0).getValidItems()));
			object.add("bank-1", builder.toJsonTree(player.getBank(1).getValidItems()));
			object.add("bank-2", builder.toJsonTree(player.getBank(2).getValidItems()));
			object.add("bank-3", builder.toJsonTree(player.getBank(3).getValidItems()));
			object.add("bank-4", builder.toJsonTree(player.getBank(4).getValidItems()));
			object.add("bank-5", builder.toJsonTree(player.getBank(5).getValidItems()));
			object.add("bank-6", builder.toJsonTree(player.getBank(6).getValidItems()));
			object.add("bank-7", builder.toJsonTree(player.getBank(7).getValidItems()));
			object.add("bank-8", builder.toJsonTree(player.getBank(8).getValidItems()));

			object.add("ge-slots", builder.toJsonTree(player.getGrandExchangeSlots()));

			/** STORE SUMMON **/
			if (player.getSummoning().getBeastOfBurden() != null) {
				object.add("store", builder.toJsonTree(player.getSummoning().getBeastOfBurden().getValidItems()));
			}
			object.add("charm-imp", builder.toJsonTree(player.getSummoning().getCharmImpConfigs()));

			object.add("friends", builder.toJsonTree(player.getRelations().getFriendList().toArray()));
			object.add("ignores", builder.toJsonTree(player.getRelations().getIgnoreList().toArray()));
			object.add("loyalty-titles", builder.toJsonTree(player.getUnlockedLoyaltyTitles()));
			object.add("kills", builder.toJsonTree(player.getKillsTracker().toArray()));
			object.add("drops", builder.toJsonTree(player.getDropLog().toArray()));

			object.addProperty("spiritdebug", player.isSpiritDebug());
			object.addProperty("reffered", player.gotReffered());
			object.addProperty("indung", player.isInDung());
			object.addProperty("toggledglobalmessages", player.toggledGlobalMessages());
			object.addProperty("difficulty", player.getDifficulty().name());
			object.addProperty("gambling-pass", player.getGamblingPass());
			object.addProperty("newYear2017", player.getNewYear2017());
			object.add("hcimdunginventory", builder.toJsonTree(player.getDungeoneeringIronInventory().getItems()));
			object.add("hcimdungequipment", builder.toJsonTree(player.getDungeoneeringIronEquipment().getItems()));
			object.addProperty("bonecrusheffect", player.getBonecrushEffect());
			object.add("p-tps", builder.toJsonTree(player.getPreviousTeleports()));
			object.addProperty("yell-tit", player.getYellTitle() == null ? "null" : player.getYellTitle());
			object.add("achievements-points", builder.toJsonTree(player.getAchievements().getPoints()));
			object.add("achievements-amount", builder.toJsonTree(player.getAchievements().getAmountRemaining()));
			object.add("achievements-completed", builder.toJsonTree(player.getAchievements().getCompleted()));
			object.addProperty("achievements-daily", player.getAchievements().getDailyAchievementsDate());
			object.add("progression-zones", builder.toJsonTree(player.getProgressionZones()));
			object.add("zones-complete", builder.toJsonTree(player.getZonesComplete()));
			object.addProperty("gamble-banned", player.isGambleBanned());
			object.addProperty("skillingtools", player.isClaimedskillingtools());
			object.addProperty("eventstoggle", player.isEventstoggle());
			object.add("currency-pouch", builder.toJsonTree(player.getCurrencyPouch()));
			if (player.getIronmanGroup() != null) {
				object.addProperty("group-ironman-id", player.getIronmanGroup().getUniqueId());
			}
			object.addProperty("group-ironman-locked", player.isGroupIronmanLocked());
			object.addProperty("lastInstanceNpc", player.lastInstanceNpc);
			object.add("daily-task", builder.toJsonTree(player.dailies));
			object.addProperty("daily-skips", player.dailySkips);
			object.addProperty("dailies-done", player.dailiesCompleted);
			object.addProperty("daily-task-info", player.taskInfo);
			object.addProperty("level-notifications", player.levelNotifications);
			object.add("dailies-received-times", builder.toJsonTree(player.getTaskReceivedTimes()));
			object.add("obtained-pets", builder.toJsonTree(player.getObtainedPets()));
            object.add("monthly-claimed", builder.toJsonTree(player.getClaimed()));
            object.addProperty("monthly-donated", player.getDonatedThroughMonth());
            object.addProperty("membership-end-date", String.valueOf(player.getMembership().getEndDate()));
            object.addProperty("daily-deals-claimed", player.getDailyDealsClaimed());
            object.add("lifetime-streaks", builder.toJsonTree(player.getLifetimeStreakVal()));
            object.addProperty("autoBank", player.getAutoBank());
            object.addProperty("autoDissolve", player.getAutoDissolve());
            object.addProperty("totalDonatedThroughStore", player.getTotalDonatedThroughStore());
            object.addProperty("christmas2023stage", player.getChristmasQuest2023Stage());
            object.addProperty("christmascompleted", player.isCompletedChristmas());
            object.add("boxes", builder.toJsonTree(player.getBoxTracker().toArray()));
            object.addProperty("rock-cake-charges", player.getRockCakeCharges());
            object.addProperty("best-tower-time", player.getBest_tower_time());
            object.addProperty("tower-runs", player.getTotal_tower_completions());
            object.addProperty("tower-wave", player.getBest_tower_wave());
            object.add("tower-invocations-unlocked", builder.toJsonTree(player.getInvocations().getTower_invocations()));
            object.add("tower-invocations-in-use", builder.toJsonTree(player.getInvocations().getTower_invocations_s()));


			object.addProperty("easterQuestStage", player.getEasterQuestStage());
			object.addProperty("searchedKingsWardrobe", player.isSearchedKingsWardrobe());
			object.addProperty("rabidBunniesKilled", player.getRabidBunniesKilled());
			object.addProperty("foundFinalEgg", player.isFoundFinalEgg());
			object.addProperty("acceptedEasterReward", player.isAcceptedEasterReward());



			object.add("nodes-unlocked", builder.toJsonTree(player.getNodesUnlocked()));
            object.add("bridges-unlocked", builder.toJsonTree(player.getBridgesUnlocked()));
            object.addProperty("current-section", player.getCurrentSection().name());
            object.addProperty("current-path", player.getCurrentPath().name());
            object.addProperty("last-path", player.getLastPath().name());
            object.addProperty("tree-unlocked", player.isTreeUnlocked());
            object.addProperty("last-free-box", String.valueOf(player.getLastFreeBox()));
            object.addProperty("aoe-size-increase", player.getAoeToken().getAoe_size());
			if (player.getForestResetTime() != null) {
				object.addProperty("reset-forest-time", player.getForestResetTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
			} else {
				object.addProperty("reset-forest-time", "null");
			}
			object.addProperty("basilisk-kills", player.getBassilisk_kills());
            object.addProperty("brimstone-kills", player.getBrimstone_kills());
            object.addProperty("everthorn-kills", player.getEverthorn_kills());
            object.addProperty("last-campaign", player.getLastCampaignCompleted());
            object.addProperty("last-campaign-reset", String.valueOf(player.getLastCampaignResetDate()));


			object.add("player-settings", builder.toJsonTree(player.getPlayerSettings(), new TypeToken<LinkedHashMap<PlayerSetting, Integer>>() {
			}.getType()));

			writer.write(builder.toJson(object));

		} catch (Exception e) {
			// An error happened while saving.
			GameServer.getLogger().log(Level.WARNING, "An error has occured while saving a character file!", e);
		}
	}

	public static boolean playerExists(String p) {
		p = Misc.formatPlayerName(p.toLowerCase());
		p = WordUtils.capitalizeFully(p);
		p.replaceAll(" ", "\\ ");
		// // System.out.println("./data/saves/characters/"+p+".json ....... "+ new
		// File("./data/saves/characters/"+p+".json").exists());
		return new File("./data/saves/characters/" + p + ".json").exists();
	}
}
