package com.ruse.world.entity.impl.player;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ruse.PlayerSetting;
import com.ruse.donation.Membership;
import com.ruse.engine.task.impl.FamiliarSpawnTask;
import com.ruse.model.*;
import com.ruse.model.PlayerRelations.PrivateChatStatus;
import com.ruse.model.container.impl.Bank;
import com.ruse.net.login.LoginResponses;
import com.ruse.util.TimeUtils;
import com.ruse.world.content.*;
import com.ruse.world.content.DropLog.DropLogEntry;
import com.ruse.world.content.Fairies.myCompanion;
import com.ruse.world.content.KillsTracker.KillsEntry;
import com.ruse.world.content.LoyaltyProgramme.LoyaltyTitles;
import com.ruse.world.content.NewDaily.DailyEntry;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.collectionlogs.CollectionLogManager;
import com.ruse.world.content.combat.magic.CombatSpells;
import com.ruse.world.content.combat.weapon.FightType;
import com.ruse.world.content.dailytasks_new.DailyTask;
import com.ruse.world.content.dailytasks_new.TaskChallenge;
import com.ruse.world.content.grandexchange.GrandExchangeSlot;
import com.ruse.world.content.groupironman.GroupManager;
import com.ruse.world.content.groupironman.IronmanGroup;
import com.ruse.world.content.skill.SkillManager.Skills;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.tree.Bridge;
import com.ruse.world.content.tree.Node;
import com.ruse.world.content.tree.Section;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.ruse.world.content.achievement.AchievementHandler.MAXIMUM_TIERS;
import static com.ruse.world.content.achievement.AchievementHandler.MAXIMUM_TIER_ACHIEVEMENTS;

public class PlayerLoading {

    private final static Path SAVE_DIR = Paths.get("./data/saves/characters/");

    public static boolean accountExists(String name) {
        return SAVE_DIR.resolve(name + ".json").toFile().exists();
    }

    public static int getResult(Player player) {
    	return getResult(player, false);
    }

    public static int getResult(Player player, boolean force) {

        // Create the path and file objects.
        Path path = Paths.get("./data/saves/characters/", player.getUsername() + ".json");
        File file = path.toFile();

        // If the file doesn't exist, we're logging in for the first
        // time and can skip all of this.
        if (!file.exists()) {
            System.out.println("CREATING NEW ACCOUNT CODE 556699");
            return LoginResponses.NEW_ACCOUNT;
        }

        // Now read the properties from the json parser.
        try (FileReader fileReader = new FileReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TimeUtils.LocalDateTimeAdapter()).registerTypeAdapter(LocalDate.class, new TimeUtils.LocalDateAdapter()).create();
            JsonObject reader = (JsonObject) fileParser.parse(fileReader);

            if (reader.has("username")) {
                player.setUsername(reader.get("username").getAsString());
            }

            if (reader.has("game-mode")) {
                player.setGameMode(GameMode.valueOf(reader.get("game-mode").getAsString()));
            }

            if (reader.has("membership-tier")) {
                player.setMemberShipTier(Membership.Tiers.valueOf(reader.get("membership-tier").getAsString()));
            }

            if (reader.has("xp-mode")) {
                player.setXpMode(XpMode.valueOf(reader.get("xp-mode").getAsString()));
            }

            if (reader.has("path")) {
                player.setBoostMode(BoostMode.valueOf(reader.get("path").getAsString()));
            }

            if (reader.has("crit")) {
                player.setCritchance(reader.get("crit").getAsInt());
            }


            if (reader.has("holyGrailDroprate")) {
                player.setHolyGrailDroprate(reader.get("holyGrailDroprate").getAsBoolean());
            }
            if (reader.has("curserunes")) {
                player.setCurseRunes(reader.get("curserunes").getAsInt());
            }
            if (reader.has("soulrunes")) {
                player.setSoulRunes(reader.get("soulrunes").getAsInt());
            }
            if (reader.has("voidrunes")) {
                player.setVoidRunes(reader.get("voidrunes").getAsInt());
            }
            if (reader.has("shadowrunes")) {
                player.setShadowRunes(reader.get("shadowrunes").getAsInt());
            }
            if (reader.has("cryptrunes")) {
                player.setCryptRunes(reader.get("cryptrunes").getAsInt());
            }
            if (reader.has("wraithrunes")) {
                player.setWraithRunes(reader.get("wraithrunes").getAsInt());
            }

            if (reader.has("damageSalts")) {
                player.setDamageSalts(reader.get("damageSalts").getAsInt());
            }
            if (reader.has("criticalSalts")) {
                player.setCriticalSalts(reader.get("criticalSalts").getAsInt());
            }
            if (reader.has("droprateSalts")) {
                player.setDroprateSalts(reader.get("droprateSalts").getAsInt());
            }

            if (reader.has("xmasTasks")) {
                player.setEasyXmasTasks(reader.get("xmasTasks").getAsInt());
            }

            if (reader.has("souls")) {
                player.setSouls(reader.get("souls").getAsInt());
            }


            if (reader.has("easyTasksDone")) {
                player.setEasyTasksDone(reader.get("easyTasksDone").getAsInt());
            }

            if (reader.has("easyTasksDone")) {
                player.setEasyTasksDone(reader.get("easyTasksDone").getAsInt());
            }

            if (reader.has("mediumTasksDone")) {
                player.setMediumTasksDone(reader.get("mediumTasksDone").getAsInt());
            }
            if (reader.has("eliteTasksDone")) {
                player.setEliteTasksDone(reader.get("eliteTasksDone").getAsInt());
            }
            if (reader.has("beastTasksDone")) {
                player.setBeastTasksDone(reader.get("beastTasksDone").getAsInt());
            }
            if (reader.has("totalKeysOpened")) {
                player.setTotalKeysOpened(reader.get("totalKeysOpened").getAsInt());
            }
            if (reader.has("totalBoxesOpened")) {
                player.setTotalBoxesOpened(reader.get("totalBoxesOpened").getAsInt());
            }
            if (reader.has("totalSoulbaneRuns")) {
                player.setTotalSoulbaneRuns(reader.get("totalSoulbaneRuns").getAsInt());
            }


            if (reader.has("tutDmgSaltMined")) {
                player.setTutDmgSaltMined(reader.get("tutDmgSaltMined").getAsInt());
            }
            if (reader.has("tutDrSaltMined")) {
                player.setTutDrSaltMined(reader.get("tutDrSaltMined").getAsInt());
            }
            if (reader.has("tutCritSaltMined")) {
                player.setTutCritSaltMined(reader.get("tutCritSaltMined").getAsInt());
            }

            if (reader.has("spectral-essence")) {
                player.setSpectralEssence(reader.get("spectral-essence").getAsInt());
            }

            if (reader.has("fireshards")) {
                player.setFireessence(reader.get("fireshards").getAsInt());
            }
            if (reader.has("watershards")) {
                player.setWateressence(reader.get("watershards").getAsInt());
            }
            if (reader.has("earthshards")) {
                player.setEarthessence(reader.get("earthshards").getAsInt());
            }
            if (reader.has("slayershards")) {
                player.setSlayeressence(reader.get("slayershards").getAsInt());
            }
            if (reader.has("monstershards")) {
                player.setMonsteressence(reader.get("monstershards").getAsInt());
            }

            if (reader.has("beastEssence")) {
                player.setBeastEssence(reader.get("beastEssence").getAsInt());
            }
            if (reader.has("corruptEssence")) {
                player.setCorruptEssence(reader.get("corruptEssence").getAsInt());
            }

            if (reader.has("enchantedEssence")) {
                player.setEnchantedEssence(reader.get("enchantedEssence").getAsInt());
            }

            if (reader.has("vorpalAmmo")) {
                player.setVorpalAmmo(reader.get("vorpalAmmo").getAsInt());
            }
            if (reader.has("bloodstainedAmmo")) {
                player.setBloodstainedAmmo(reader.get("bloodstainedAmmo").getAsInt());
            }
            if (reader.has("symbioteammo")) {
                player.setSymbioteAmmo(reader.get("symbioteammo").getAsInt());
            }
            if (reader.has("netherammo")) {
                player.setNetherAmmo(reader.get("netherammo").getAsInt());
            }

            if (reader.has("corruptRaidsDone")) {
                player.setCorruptCompletions(reader.get("corruptRaidsDone").getAsInt());
            }
            if (reader.has("corruptMediumRaidsDone")) {
                player.setCorruptMediumCompletions(reader.get("corruptMediumRaidsDone").getAsInt());
            }
            if (reader.has("corruptHardRaidsDone")) {
                player.setCorruptHardCompletions(reader.get("corruptHardRaidsDone").getAsInt());
            }


            if (reader.has("voidRaidsDone")) {
                player.setVoidCompletions(reader.get("voidRaidsDone").getAsInt());
            }
            if (reader.has("voidMediumRaidsDone")) {
                player.setVoidMediumCompletions(reader.get("voidMediumRaidsDone").getAsInt());
            }
            if (reader.has("voidHardRaidsDone")) {
                player.setVoidHardCompletions(reader.get("voidHardRaidsDone").getAsInt());
            }
            if (reader.has("coedone")) {
                player.setCOECompletions(reader.get("coedone").getAsInt());
            }


            if (reader.has("total-play-time-ms")) {
                player.setTotalPlayTime(reader.get("total-play-time-ms").getAsLong());
            }

            if (reader.has("staff-rights")) {
                String rights = reader.get("staff-rights").getAsString();
                player.setRights(PlayerRights.valueOf(rights));
            }

            if (reader.has("currency-pouch")) {
                player.setCurrencyPouch(builder.fromJson(reader.get("currency-pouch"), CurrencyPouch.class));
            }

            if (reader.has("password")) {
                String password = reader.get("password").getAsString();
                if (!password.equalsIgnoreCase("")) {
                    if (!force) {
                        if (!player.getPassword().equals(password)) {
                            return LoginResponses.LOGIN_INVALID_CREDENTIALS;
                        }
                    }
                    player.setPassword(password);
                }
            } else if (reader.has("hash")) {
                String hash = reader.get("hash").getAsString();
                player.setSalt(hash.substring(0, 29));
                if (BCrypt.checkpw(player.getPassword(), hash)) {
                    // System.out.println("Successfully authenticated hashed pw.");
                } else {
                    // System.out.println("Failed hashed pw authentication.");
                    return LoginResponses.LOGIN_INVALID_CREDENTIALS;
                }
            }

            if (reader.has("has-pin2")) {
                player.setHasPin(reader.get("has-pin2").getAsBoolean());
            }

            if (reader.has("fixedFerry")) {
                player.setFixedFerry(reader.get("fixedFerry").getAsBoolean());
            }
            if (reader.has("posAbility")) {
                player.setPosAbility(reader.get("posAbility").getAsBoolean());
            }

            if (reader.has("claimedLantern")) {
                player.setClaimedLantern(reader.get("claimedLantern").getAsBoolean());
            }
            if (reader.has("saved-pin2")) {
                player.setSavedPin(reader.get("saved-pin2").getAsString());
            }

            if (reader.has("infiniteprayer")) {
                player.setInfinitePrayer(reader.get("infiniteprayer").getAsBoolean());
            }


            if (reader.has("killmesssageamount")) {
                player.setKillmessageamount(reader.get("killmesssageamount").getAsInt());
            }

            if (reader.has("pouchmessagesenabled")) {
                player.setPouchMessagesEnabled(reader.get("pouchmessagesenabled").getAsBoolean());
            }

            if (reader.has("critmessageenabled")) {
                player.setCritMessagesEnabled(reader.get("critmessageenabled").getAsBoolean());
            }

            if (reader.has("madeDRpot")) {
                player.setMadeDRpot(reader.get("madeDRpot").getAsBoolean());
            }

            if (reader.has("madeCritPot")) {
                player.setMadeCritPot(reader.get("madeCritPot").getAsBoolean());
            }

            if (reader.has("madeDmgPot")) {
                player.setMadeDmgPot(reader.get("madeDmgPot").getAsBoolean());
            }


            if (reader.has("globalDropMessagesEnabled")) {
                player.setGlobalDropMessagesEnabled(reader.get("globalDropMessagesEnabled").getAsBoolean());
            }

            if (reader.has("gaiaCrystalStage")) {
                player.setGaiaCrystalStage(reader.get("gaiaCrystalStage").getAsInt());
            }

            if (reader.has("marinaTaskID")) {
                player.setMarinaTaskID(reader.get("marinaTaskID").getAsInt());
            }
            if (reader.has("dailyMarinaTasks")) {
                player.setDailyMarinaTasks(reader.get("dailyMarinaTasks").getAsInt());
            }


            if (reader.has("lavaCrystalStage")) {
                player.setLavaCrystalStage(reader.get("lavaCrystalStage").getAsInt());
            }

            if (reader.has("aquaCrystalStage")) {
                player.setAquaCrystalStage(reader.get("aquaCrystalStage").getAsInt());
            }

            if (reader.has("voidCrystalStage")) {
                player.setVoidCrystalStage(reader.get("voidCrystalStage").getAsInt());
            }


            if (reader.has("vgTutStage")) {
                player.setVgTutStage(reader.get("vgTutStage").getAsInt());
            }

            if (reader.has("prayerMinigameBossInstanceActive")) {
                player.setPrayerMinigameBossInstanceActive(reader.get("prayerMinigameBossInstanceActive").getAsBoolean());
            }
            if (reader.has("aresTrialsInstanceActive")) {
                player.setAresTrialsInstanceActive(reader.get("aresTrialsInstanceActive").getAsBoolean());
            }
            if (reader.has("prayerMinigameMinionKills")) {
                player.setPrayerMinigameMinionKills(reader.get("prayerMinigameMinionKills").getAsInt());
            }

            if (reader.has("prayerMinigameBossKillsLeft")) {
                player.setPrayerMinigameBossKillsLeft(reader.get("prayerMinigameBossKillsLeft").getAsInt());
            }

            if (reader.has("soulbaneStreak")) {
                player.setSoulbaneStreak(reader.get("soulbaneStreak").getAsInt());
            }
            if (reader.has("completedbeginner")) {
                player.setCompletedBeginner(reader.get("completedbeginner").getAsBoolean());
            }

            if (reader.has("changedGamemodes")) {
                player.setHasChangedGameMode(reader.get("changedGamemodes").getAsBoolean());
            }


            if (reader.has("cosmeticHeadOn")) {
                player.setCosmeticHeadOn(reader.get("cosmeticHeadOn").getAsBoolean());
            }
            if (reader.has("cosmeticBodyOn")) {
                player.setCosmeticBodyOn(reader.get("cosmeticBodyOn").getAsBoolean());
            }
            if (reader.has("cosmeticLegsOn")) {
                player.setCosmeticLegsOn(reader.get("cosmeticLegsOn").getAsBoolean());
            }
            if (reader.has("cosmeticGlovesOn")) {
                player.setCosmeticGlovesOn(reader.get("cosmeticGlovesOn").getAsBoolean());
            }
            if (reader.has("cosmeticBootsOn")) {
                player.setCosmeticBootsOn(reader.get("cosmeticBootsOn").getAsBoolean());
            }
            if (reader.has("cosmeticCapeOn")) {
                player.setCosmeticCapeOn(reader.get("cosmeticCapeOn").getAsBoolean());
            }
            if (reader.has("cosmeticWeaponOn")) {
                player.setCosmeticWeaponOn(reader.get("cosmeticWeaponOn").getAsBoolean());
            }
            if (reader.has("cosmeticShieldOn")) {
                player.setCosmeticShieldOn(reader.get("cosmeticShieldOn").getAsBoolean());
            }
            if (reader.has("cosmeticAmuletOn")) {
                player.setCosmeticAmuletOn(reader.get("cosmeticAmuletOn").getAsBoolean());
            }



            if (reader.has("claimedGear")) {
                player.setClaimedGear(reader.get("claimedGear").getAsBoolean());
            }

            if (reader.has("unlockedIntermediateZones")) {
                player.setUnlockedIntermediateZones(reader.get("unlockedIntermediateZones").getAsBoolean());
            }
            if (reader.has("unlockedEliteZones")) {
                player.setUnlockedEliteZones(reader.get("unlockedEliteZones").getAsBoolean());
            }
            if (reader.has("unlockedMasterZones")) {
                player.setUnlockedMasterZones(reader.get("unlockedMasterZones").getAsBoolean());
            }

            if (reader.has("unlockedSpectralZones")) {
                player.setUnlockedSpectralZones(reader.get("unlockedSpectralZones").getAsBoolean());
            }

            if (reader.has("trials2Unlocked")) {
                player.setTrials2Unlocked(reader.get("trials2Unlocked").getAsBoolean());
            }
            if (reader.has("trials3Unlocked")) {
                player.setTrials3Unlocked(reader.get("trials3Unlocked").getAsBoolean());
            }
            if (reader.has("trials4Unlocked")) {
                player.setTrials4Unlocked(reader.get("trials4Unlocked").getAsBoolean());
            }
            if (reader.has("trials5Unlocked")) {
                player.setTrials5Unlocked(reader.get("trials5Unlocked").getAsBoolean());
            }

            if (reader.has("startedTutorial")) {
                player.setStartedTutorial(reader.get("startedTutorial").getAsBoolean());
            }
            if (reader.has("receivedprayerunlock")) {
                player.setReceivedprayerunlock(reader.get("receivedprayerunlock").getAsBoolean());
            }
            if (reader.has("receivedarmor")) {
                player.setReceivedarmor(reader.get("receivedarmor").getAsBoolean());
            }


            if (reader.has("tutTask1Started")) {
                player.setTutTask1Started(reader.get("tutTask1Started").getAsBoolean());
            }
            if (reader.has("tutTask1Ready")) {
                player.setTutTask1Ready(reader.get("tutTask1Ready").getAsBoolean());
            }
            if (reader.has("tutTask1Complete")) {
                player.setTutTask1Complete(reader.get("tutTask1Complete").getAsBoolean());
            }


            if (reader.has("tutTask2Started")) {
                player.setTutTask2Started(reader.get("tutTask2Started").getAsBoolean());
            }
            if (reader.has("tutTask2Ready")) {
                player.setTutTask2Ready(reader.get("tutTask2Ready").getAsBoolean());
            }
            if (reader.has("tutTask2Complete")) {
                player.setTutTask2Complete(reader.get("tutTask2Complete").getAsBoolean());
            }


            if (reader.has("tutTask3Started")) {
                player.setTutTask3Started(reader.get("tutTask3Started").getAsBoolean());
            }
            if (reader.has("tutTask3Ready")) {
                player.setTutTask3Ready(reader.get("tutTask3Ready").getAsBoolean());
            }
            if (reader.has("tutTask3Complete")) {
                player.setTutTask3Complete(reader.get("tutTask3Complete").getAsBoolean());
            }


            if (reader.has("madeCritPotion")) {
                player.setMadeCritPotion(reader.get("madeCritPotion").getAsBoolean());
            }
            if (reader.has("madeDropratePotion")) {
                player.setMadeDropratePotion(reader.get("madeDropratePotion").getAsBoolean());
            }
            if (reader.has("madeDivinePotion")) {
                player.setMadeDivinePotion(reader.get("madeDivinePotion").getAsBoolean());
            }
            if (reader.has("madeDamagePotion")) {
                player.setMadeDamagePotion(reader.get("madeDamagePotion").getAsBoolean());
            }

            if (reader.has("tutTask4Started")) {
                player.setTutTask4Started(reader.get("tutTask4Started").getAsBoolean());
            }
            if (reader.has("tutTask4Ready")) {
                player.setTutTask4Ready(reader.get("tutTask4Ready").getAsBoolean());
            }
            if (reader.has("tutTask4Complete")) {
                player.setTutTask4Complete(reader.get("tutTask4Complete").getAsBoolean());
            }

            if (reader.has("tutTask5Started")) {
                player.setTutTask5Started(reader.get("tutTask5Started").getAsBoolean());
            }
            if (reader.has("tutTask5Ready")) {
                player.setTutTask5Ready(reader.get("tutTask5Ready").getAsBoolean());
            }
            if (reader.has("tutTask5Complete")) {
                player.setTutTask5Complete(reader.get("tutTask5Complete").getAsBoolean());
            }


            if (reader.has("tutTask6Started")) {
                player.setTutTask6Started(reader.get("tutTask6Started").getAsBoolean());
            }
            if (reader.has("tutTask6Ready")) {
                player.setTutTask6Ready(reader.get("tutTask6Ready").getAsBoolean());
            }
            if (reader.has("tutTask6Complete")) {
                player.setTutTask6Complete(reader.get("tutTask6Complete").getAsBoolean());
            }



            if (reader.has("afkstall1"))
                player.setAfkStallCount1(reader.get("afkstall1").getAsInt());

            if (reader.has("defeatedbeast")) {
                player.setDefeatedbeast(reader.get("defeatedbeast").getAsBoolean());
            }
            if (reader.has("completedtutorial")) {
                player.setCompletedtutorial(reader.get("completedtutorial").getAsBoolean());
            }


            if (reader.has("skippedTutorial")) {
                player.setSkippedTutorial(reader.get("skippedTutorial").getAsBoolean());
            }
            if (reader.has("claimedSkipPrayer")) {
                player.setClaimedSkipPrayer(reader.get("claimedSkipPrayer").getAsBoolean());
            }
            if (reader.has("claimedSkipArmor")) {
                player.setClaimedSkipArmor(reader.get("claimedSkipArmor").getAsBoolean());
            }
            if (reader.has("hasManualPrayer")) {
                player.setHasManualPrayer(reader.get("hasManualPrayer").getAsBoolean());
            }
            if (reader.has("hasManualArmor")) {
                player.setHasManualArmor(reader.get("hasManualArmor").getAsBoolean());
            }



            if (reader.has("unlockedWolfCamp")) {
                player.setUnlockedWolfCamp(reader.get("unlockedWolfCamp").getAsBoolean());
            }
            if (reader.has("unlockedDemonCamp")) {
                player.setUnlockedDemonCamp(reader.get("unlockedDemonCamp").getAsBoolean());
            }
            if (reader.has("unlockedTitanCamp")) {
                player.setUnlockedTitanCamp(reader.get("unlockedTitanCamp").getAsBoolean());
            }

            if (reader.has("dailyForestTaskAmount")) {
                player.setDailyForestTaskAmount(reader.get("dailyForestTaskAmount").getAsInt());
            }

            if (reader.has("enchantedtasksdone")) {
                player.setEnchantedTasksDone(reader.get("enchantedtasksdone").getAsInt());
            }

            if (reader.has("mysticMushTaskAmount")) {
                player.setMysticMushTaskAmount(reader.get("mysticMushTaskAmount").getAsInt());
            }

            if (reader.has("corruptMushTaskAmount")) {
                player.setCorruptMushTaskAmount(reader.get("corruptMushTaskAmount").getAsInt());
            }


            if (reader.has("permDroprate")) {
                player.setClaimedPermDroprate(reader.get("permDroprate").getAsBoolean());
            }
            if (reader.has("permDamage")) {
                player.setClaimedPermDamage(reader.get("permDamage").getAsBoolean());
            }


            if (reader.has("receivedExtras")) {
                player.setReceivedExtras(reader.get("receivedExtras").getAsBoolean());
            }

            if (reader.has("receivedEaster")) {
                player.setReceivedEaster(reader.get("receivedEaster").getAsBoolean());
            }

            if (reader.has("madeCandyBoost")) {
                player.setMadeCandyBoost(reader.get("madeCandyBoost").getAsBoolean());
            }

            if (reader.has("unlockedSkeletalSpells")) {
                player.setUnlockedSkeletalSpells(reader.get("unlockedSkeletalSpells").getAsBoolean());
            }
            if (reader.has("unlockedDemonicSpells")) {
                player.setUnlockedDemonicSpells(reader.get("unlockedDemonicSpells").getAsBoolean());
            }
            if (reader.has("unlockedOgreSpells")) {
                player.setUnlockedOgreSpells(reader.get("unlockedOgreSpells").getAsBoolean());
            }
            if (reader.has("unlockedDragonicSpells")) {
                player.setUnlockedSpectralSpells(reader.get("unlockedDragonicSpells").getAsBoolean());
            }
            if (reader.has("unlockedMasterSpells")) {
                player.setUnlockedMasterSpells(reader.get("unlockedMasterSpells").getAsBoolean());
            }



            if (reader.has("holidayTask")) {
                player.getHolidayTaskHandler().setHasTask(reader.get("holidayTask").getAsBoolean());
            }
            if (reader.has("holidayPoints")) {
                player.setHolidayPoints(reader.get("holidayPoints").getAsInt());
            }
            if (reader.has("holidayNpc")) {
                player.getHolidayTaskHandler().setTaskNpc(reader.get("holidayNpc").getAsInt());
            }
            if (reader.has("holidayAmount")) {
                player.getHolidayTaskHandler().setTaskAmt(reader.get("holidayAmount").getAsInt());
            }
            if (reader.has("holidayReward")) {
                player.getHolidayTaskHandler().setTaskRewardPt(reader.get("holidayReward").getAsInt());
            }




            if (reader.has("unlockedGaiablessingPrayer")) {
                player.setUnlockedGaiablessingPrayer(reader.get("unlockedGaiablessingPrayer").getAsBoolean());
            }
            if (reader.has("unlockedSerenityPrayer")) {
                player.setUnlockedSerenityPrayer(reader.get("unlockedSerenityPrayer").getAsBoolean());
            }
            if (reader.has("unlockedRockheartPrayer")) {
                player.setUnlockedRockheartPrayer(reader.get("unlockedRockheartPrayer").getAsBoolean());
            }
            if (reader.has("unlockedGeovigorPrayer")) {
                player.setUnlockedGeovigorPrayer(reader.get("unlockedGeovigorPrayer").getAsBoolean());
            }
            if (reader.has("unlockedStonehavenPrayer")) {
                player.setUnlockedStonehavenPrayer(reader.get("unlockedStonehavenPrayer").getAsBoolean());
            }
            if (reader.has("unlockedEbbflowPrayer")) {
                player.setUnlockedEbbflowPrayer(reader.get("unlockedEbbflowPrayer").getAsBoolean());
            }
            if (reader.has("unlockedAquastrikePrayer")) {
                player.setUnlockedAquastrikePrayer(reader.get("unlockedAquastrikePrayer").getAsBoolean());
            }
            if (reader.has("unlockedRiptidePrayer")) {
                player.setUnlockedRiptidePrayer(reader.get("unlockedRiptidePrayer").getAsBoolean());
            }
            if (reader.has("unlockedSeaslicerPrayer")) {
                player.setUnlockedSeaslicerPrayer(reader.get("unlockedSeaslicerPrayer").getAsBoolean());
            }
            if (reader.has("unlockedSwifttidePrayer")) {
                player.setUnlockedSwifttidePrayer(reader.get("unlockedSwifttidePrayer").getAsBoolean());
            }
            if (reader.has("unlockedCindersTouch")) {
                player.setUnlockedCindersTouch(reader.get("unlockedCindersTouch").getAsBoolean());
            }
            if (reader.has("unlockedEmberblastPrayer")) {
                player.setUnlockedEmberblastPrayer(reader.get("unlockedEmberblastPrayer").getAsBoolean());
            }
            if (reader.has("unlockedInfernifyPrayer")) {
                player.setUnlockedInfernifyPrayer(reader.get("unlockedInfernifyPrayer").getAsBoolean());
            }
            if (reader.has("unlockedBlazeupPrayer")) {
                player.setUnlockedBlazeupPrayer(reader.get("unlockedBlazeupPrayer").getAsBoolean());
            }
            if (reader.has("unlockedInfernoPrayer")) {
                player.setUnlockedInfernoPrayer(reader.get("unlockedInfernoPrayer").getAsBoolean());
            }


            if (reader.has("unlockedVoid1Prayer")) {
                player.setUnlockedMalevolencePrayer(reader.get("unlockedVoid1Prayer").getAsBoolean());
            }

            if (reader.has("unlockedVoid2Prayer")) {
                player.setUnlockedDesolationPrayer(reader.get("unlockedVoid2Prayer").getAsBoolean());
            }

            if (reader.has("saved-ip")) {
                player.setSavedIp(reader.get("saved-ip").getAsString());
            }

            if (reader.has("position")) {
                player.getPosition().setAs(builder.fromJson(reader.get("position"), Position.class));
            }

            if (reader.has("online-status")) {
                player.getRelations().setStatus(PrivateChatStatus.valueOf(reader.get("online-status").getAsString()),
                        false);
            }

            if (reader.has("given-starter")) {
                player.setReceivedStarter(reader.get("given-starter").getAsBoolean());
            }

            if (reader.has("valpassType")) {
                player.getBattlePass().setType(BattlePassType.valueOf(reader.get("valpassType").getAsString()));
            }


            if (reader.has("valpasslevel")) {
                int level = reader.get("valpasslevel").getAsInt();
                if (player.getBattlePass().getType() == BattlePassType.TIER2) {
                    player.getBattlePass().setBattlePass_Tier2_Level(level);
                } else {
                    player.getBattlePass().setBattlePass_Tier1_Level(level);
                }
            }

            if (reader.has("valPassExp")) {
                int xp = reader.get("valPassExp").getAsInt();
                if (player.getBattlePass().getType() == BattlePassType.TIER2) {
                    player.getBattlePass().setBattlePass_Tier2_XP(xp);
                } else {
                    if (player.getBattlePass().getType() == BattlePassType.TIER1) {
                        player.getBattlePass().setBattlePass_Tier1_XP(xp);
                    }
                }
            }

            if (reader.has("valPasst1Claimed")) {
                player.setTier1ValpassClaimed(reader.get("valPasst1Claimed").getAsString());
            }

            if (reader.has("valPasst1Expires")) {
                player.setTier1ValPassExpires(reader.get("valPasst1Expires").getAsString());
            }

            if (reader.has("valPasst2Claimed")) {
                player.setTier2ValPassClaimed(reader.get("valPasst2Claimed").getAsString());
            }

            if (reader.has("valPasst2Expires")) {
                player.setTier2ValPassExpires(reader.get("valPasst2Expires").getAsString());
            }




            if (reader.has("donated")) {
                player.incrementAmountDonated(reader.get("donated").getAsInt());
            }

            if (reader.has("inventory")) {
                player.getInventory()
                        .setItems(builder.fromJson(reader.get("inventory").getAsJsonArray(), Item[].class));
            }
            if (reader.has("equipment")) {
                player.getEquipment()
                        .setItems(builder.fromJson(reader.get("equipment").getAsJsonArray(), Item[].class));
            }
            if (reader.has("cosmetics")) {
                player.getCosmetics()
                        .setItems(builder.fromJson(reader.get("cosmetics").getAsJsonArray(), Item[].class));
            }

            int presetsOwned = 1;
            if (reader.has("presets-owned")) {
                player.getPresetManager().setPresetsOwned(reader.get("presets-owned").getAsInt());
                presetsOwned = reader.get("presets-owned").getAsInt();
            }
            for (int i = 0; i < presetsOwned; i++) {
                if (reader.has("presets-" + i)) {
                    player.getPresetManager().setPreset(i,
                            builder.fromJson(reader.get("presets-" + i).getAsJsonArray(), Item[].class));
                }
            }
            if (reader.has("wearing-preset")) {
                player.getPresetManager().setWearingPreset(reader.get("wearing-preset").getAsInt());
            }

            if (reader.has("preset-names")) {
                player.getPresetManager()
                        .setPresetNames(builder.fromJson(reader.get("preset-names").getAsJsonArray(), String[].class));
            }





            if (reader.has("group-ironman-id")) {
                IronmanGroup group = GroupManager.getGroup((reader.get("group-ironman-id").getAsInt()));
                //System.out.println("ID : " + reader.get("group-ironman-id").getAsInt());
                if (group != null) {
                    System.out.println("ID1 : " + reader.get("group-ironman-id").getAsInt());
                    System.out.println("GROUP: " + group);
                    System.out.println("GROUP name: " + group.getName());
                    System.out.println("agroup.getMembers(): " +group.getMembers().size());
                    System.out.println("player.getUsername(): " +player.getUsername());
                    if (group.getMembers().contains(player.getUsername())) {
                        System.out.println("add name: " + player.getUsername());
                        player.setIronmanGroup(group);
                    }
                }
            }

            if (reader.has("group-ironman-locked")) {
                player.setGroupIronmanLocked((reader.get("group-ironman-locked").getAsBoolean()));
            }

            if (reader.has("lastInstanceNpc")) {
                player.lastInstanceNpc = reader.get("lastInstanceNpc").getAsInt();
            }




            if (reader.has("uim-bank")) {
                Map<Integer, Integer> items = builder.fromJson(reader.get("uim-bank"),
                        new TypeToken<Map<Integer, Integer>>() {
                        }.getType());
                player.setUimBankItems(items);
            }



            if (reader.has("amount-donated-today")) {
                player.setAmountDonatedToday(reader.get("amount-donated-today").getAsInt());
            }


            if (reader.has("godmodetime")) {
                player.setGodModeTimer(reader.get("godmodetime").getAsInt());
            }
            if (reader.has("effects-on")) {
                player.setEffectson(reader.get("effects-on").getAsBoolean());
            }

            if (reader.has("show-chance")) {
                player.setShowMyChance(reader.get("show-chance").getAsBoolean());
            }

           if (reader.has("dr-time-left")) {
               player.getDroprateTimer().setTimeForTask(reader.get("dr-time-left").getAsInt());
           }

            if (reader.has("divine-time-left")) {
                player.getDivine().setTimeForTask(reader.get("divine-time-left").getAsInt());
            }

            if (reader.has("fury-time-left")) {
                player.getFury().setTimeForTask(reader.get("fury-time-left").getAsInt());
            }

            if (reader.has("rage-time-left")) {
                player.getRage().setTimeForTask(reader.get("rage-time-left").getAsInt());
            }

            if (reader.has("voteboost-time-left")) {
                player.getVoteBoost().setTimeForTask(reader.get("voteboost-time-left").getAsInt());
            }

            if (reader.has("dmg-time-left")) {
                player.getDamageTimer().setTimeForTask(reader.get("dmg-time-left").getAsInt());
            }

            if (reader.has("noob-time-left")) {
                player.getNoob().setTimeForTask(reader.get("noob-time-left").getAsInt());
            }

            if (reader.has("killboost-time-left")) {
                player.getKillBoost().setTimeForTask(reader.get("killboost-time-left").getAsInt());
            }
            if (reader.has("raidboost-time-left")) {
                player.getRaidBoost().setTimeForTask(reader.get("raidboost-time-left").getAsInt());
            }
            if (reader.has("necroboost-time-left")) {
                player.getNecroBoost().setTimeForTask(reader.get("necroboost-time-left").getAsInt());
            }
            if (reader.has("slayerboost-time-left")) {
                player.getSlayerBoost().setTimeForTask(reader.get("slayerboost-time-left").getAsInt());
            }



            if (reader.has("crit-time-left")) {
                player.getCritTimer().setTimeForTask(reader.get("crit-time-left").getAsInt());
            }

            if (reader.has("ice-time-left")) {
                player.getIceBornTimer().setTimeForTask(reader.get("ice-time-left").getAsInt());
            }

            if (reader.has("egg-time-left")) {
                player.getEasterTimer().setTimeForTask(reader.get("egg-time-left").getAsInt());
            }

            if (reader.has("love-time-left")) {
                player.getLoveTImer().setTimeForTask(reader.get("love-time-left").getAsInt());
            }

            if (reader.has("magma-time-left")) {
                player.getMagma().setTimeForTask(reader.get("magma-time-left").getAsInt());
            }
            if (reader.has("overgrown-time-left")) {
                player.getOvergrown().setTimeForTask(reader.get("overgrown-time-left").getAsInt());
            }
            if (reader.has("nautic-time-left")) {
                player.getNautic().setTimeForTask(reader.get("nautic-time-left").getAsInt());
            }


            if (reader.has("player-settings")) {
                player.setPlayerSettings(builder.fromJson(reader.get("player-settings"), new TypeToken<LinkedHashMap<PlayerSetting, Integer>>() {
                }.getType()));
            }
            if (reader.has("dr-boost")) {
                player.setDrBoost(reader.get("dr-boost").getAsInt());
            }
            if (reader.has("dmg-boost")) {
                player.setDmgBoost(reader.get("dmg-boost").getAsInt());
            }
            if (reader.has("upg-boost")) {
                player.setUpgBoost(reader.get("upg-boost").getAsInt());
            }
            if (reader.has("door-picks-left")) {
                player.incrementDoorPicks(reader.get("door-picks-left").getAsInt());
            }
            if (reader.has("daily-data")) {
                DailyEntry[] dailyEntryData = builder.fromJson(reader.get("daily-data"), DailyEntry[].class);
                for (DailyEntry entry : dailyEntryData) {
                    player.getDailyEntries().add(entry);
                }
            }
            if (reader.has("daily-data-timer")) {
                player.setStartDailyTimer(reader.get("daily-data-timer").getAsLong());
            }

            if (reader.has("claimed-first")) {
                player.claimedFirst = reader.get("claimed-first").getAsBoolean();
            }

            if (reader.has("claimed-second")) {
                player.claimedSecond = reader.get("claimed-second").getAsBoolean();
            }

            if (reader.has("claimed-third")) {
                player.claimedThird = reader.get("claimed-third").getAsBoolean();
            }

            if (reader.has("last-donation")) {
                player.lastDonation = reader.get("last-donation").getAsLong();
            }

            if (reader.has("last-time-reset")) {
                player.lastDonation = reader.get("last-time-reset").getAsLong();
            }

            if (reader.has("email")) {
                player.setEmailAddress(reader.get("email").getAsString());
            }





            if (reader.has("tracker")) {
                player.setTrackers(builder.fromJson(reader.get("tracker"),
                        new com.google.common.reflect.TypeToken<HashMap<String, Integer>>() {
                        }.getType()));
            }

            if (reader.has("holy-prayers-unlocked")) {
                player.setUnlockedHolyPrayers(
                        builder.fromJson(reader.get("holy-prayers-unlocked").getAsJsonArray(), boolean[].class));
            }

            if (reader.has("current-boss-task")) {
                player.setCurrentBossTask(reader.get("current-boss-task").getAsInt());
            }

            if (reader.has("current-boss-amount")) {
                player.setCurrentBossTaskAmount(reader.get("current-boss-amount").getAsInt());
            }

            if (reader.has("has-completed-boss-task")) {
                player.setHasPlayerCompletedBossTask(reader.get("has-completed-boss-task").getAsBoolean());
            }

            if (reader.has("current-daily-task-id")) {
                player.setCurrentDailyTask(reader.get("current-daily-task-id").getAsString());
            }

            if (reader.has("current-daily-task-amount")) {
                player.setCurrentDailyTaskAmount(reader.get("current-daily-task-amount").getAsInt());
            }

            if (reader.has("current-daily-task-x-pos")) {
                player.setxPosDailyTask(reader.get("current-daily-task-x-pos").getAsInt());
            }

            if (reader.has("current-daily-task-y-pos")) {
                player.setyPostDailyTask(reader.get("current-daily-task-y-pos").getAsInt());
            }

            if (reader.has("current-daily-task-z-pos")) {
                player.setzPosDailyTask(reader.get("current-daily-task-z-pos").getAsInt());
            }
            if (reader.has("current-daily-task-reward")) {
                player.setRewardDailyTask(reader.get("current-daily-task-reward").getAsInt());
            }

            if (reader.has("daysVoted")) {
                player.setDaysVoted(reader.get("daysVoted").getAsInt());
            }

         /*   if (reader.has("unlocked-titles")) {
                Titles titles[] = builder.fromJson(reader.get("unlocked-titles").getAsJsonArray(), Titles[].class);
                for (Titles l : titles) {
                    if (!player.getTitlesManager().getObtainedTitles().contains(l))
                        player.getTitlesManager().getObtainedTitles().add(l);
                }
            }
            if (reader.has("player-title")) {
                if (reader.get("player-title").getAsString().equalsIgnoreCase("none"))
                    player.setPlayerTitle(Titles.NONE);
                else {
                    player.setPlayerTitle(Titles.valueOf(reader.get("player-title").getAsString()));
                    //player.setTitle(Colours.convertColours(player.getPlayerTitle().getName()));
                }
            }*/

            if (reader.has("starter-task-amount")) {
                int[] amountRemaining = builder.fromJson(reader.get("starter-task-amount").getAsJsonArray(),
                        int[].class);
                player.getStarterTasks().setAmountRemaining(amountRemaining);
            }
            if (reader.has("starter-task-completed")) {
                boolean[] completed = builder.fromJson(reader.get("starter-task-completed").getAsJsonArray(),
                        boolean[].class);
                player.getStarterTasks().setCompleted(completed);
            }

            if (reader.has("medium-task-amount")) {
                int[] amountRemaining = builder.fromJson(reader.get("medium-task-amount").getAsJsonArray(),
                        int[].class);
                player.getMediumTasks().setAmountRemaining(amountRemaining);
            }
            if (reader.has("medium-task-completed")) {
                boolean[] completed = builder.fromJson(reader.get("medium-task-completed").getAsJsonArray(),
                        boolean[].class);
                player.getMediumTasks().setCompleted(completed);
            }

            if (reader.has("elite-task-amount")) {
                int[] amountRemaining = builder.fromJson(reader.get("elite-task-amount").getAsJsonArray(),
                        int[].class);
                player.getEliteTasks().setAmountRemaining(amountRemaining);
            }
            if (reader.has("elite-task-completed")) {
                boolean[] completed = builder.fromJson(reader.get("elite-task-completed").getAsJsonArray(),
                        boolean[].class);
                player.getEliteTasks().setCompleted(completed);
            }

            if (reader.has("master-task-amount")) {
                int[] amountRemaining = builder.fromJson(reader.get("master-task-amount").getAsJsonArray(),
                        int[].class);
                player.getMasterTasks().setAmountRemaining(amountRemaining);
            }
            if (reader.has("master-task-completed")) {
                boolean[] completed = builder.fromJson(reader.get("master-task-completed").getAsJsonArray(),
                        boolean[].class);
                player.getMasterTasks().setCompleted(completed);
            }

            if (reader.has("MyFairy")) {
                myCompanion fairy = builder.fromJson(reader.get("MyFairy").getAsString(), myCompanion.class);
                player.getCompanion().setMyCompanion(fairy);
            }

            if (reader.has("days-voted")) {
                boolean[] completed = builder.fromJson(reader.get("days-voted").getAsJsonArray(),
                        boolean[].class);
                player.getStreak().setDaysVoted(completed);
            }

            if (reader.has("last-vote")) {
                player.getStreak().setLastVote(builder.fromJson(reader.get("last-vote"), LocalDateTime.class));
            }

            if (reader.has("next-countable-vote")) {
                player.getStreak().setNextCountableVote(builder.fromJson(reader.get("next-countable-vote"), LocalDateTime.class));
            }

            if (reader.has("vote-streak")) {
                player.getStreak().setCurrentStreak(reader.get("vote-streak").getAsInt());
            }

            if (reader.has("totalTimesClaimed")) {
                player.setTotalTimesClaimed(reader.get("totalTimesClaimed").getAsInt());
            }

            if (reader.has("longestDaysVoted")) {
                player.setLongestDaysVoted(reader.get("longestDaysVoted").getAsInt());
            }

            if (reader.has("timeUntilNextReward")) {
                player.setTimeUntilNextReward(reader.get("timeUntilNextReward").getAsInt());
            }

            if (reader.has("lastVoted")) {
                player.setLastVoted(reader.get("lastVoted").getAsString());
            }

            if (reader.has("current-voting-streak")) {
                player.setCurrentVotingStreak(reader.get("current-voting-streak").getAsInt());
            }

            if (reader.has("entriesCurrency")) {
                player.setEntriesCurrency(reader.get("entriesCurrency").getAsDouble());
            }

            if (reader.has("loyalty-title")) {
                player.setLoyaltyTitle(LoyaltyTitles.valueOf(reader.get("loyalty-title").getAsString()));
            }



            if (reader.has("minutes-bonus-exp")) {
                player.setMinutesBonusExp(reader.get("minutes-bonus-exp").getAsInt(), false);
            }
            if (reader.has("minutes-voting-dr")) {
                player.setMinutesVotingDR(reader.get("minutes-voting-dr").getAsInt(), false);
            }
            if (reader.has("minutes-voting-dmg")) {
                player.setMinutesVotingDMG(reader.get("minutes-voting-dmg").getAsInt(), false);
            }

            if (reader.has("total-gained-exp")) {
                player.getSkillManager().setTotalGainedExp(reader.get("total-gained-exp").getAsInt());
            }

            if (reader.has("dung-tokens")) {
                player.getPointsHandler().setDungeoneeringTokens(reader.get("dung-tokens").getAsInt(), false);
            }

            if (reader.has("barrows-points")) {
                player.getPointsHandler().setBarrowsPoints(reader.get("barrows-points").getAsInt(), false);
            }

            if (reader.has("donator-points")) {
                player.getPointsHandler().setDonatorPoints(reader.get("donator-points").getAsInt(), false);
            }
            if (reader.has("refund-points")) {
                player.getPointsHandler().setRefundPoints(reader.get("refund-points").getAsInt(), false);
            }

            if (reader.has("prestige-points")) {
                player.getPointsHandler().setPrestigePoints(reader.get("prestige-points").getAsInt(), false);
            }
            if (reader.has("Skilling-points")) {
                player.getPointsHandler().setSkillingPoints(reader.get("Skilling-points").getAsInt(), false);
            }

            if (reader.has("achievement-points")) {
                player.getPointsHandler().setAchievementPoints(reader.get("achievement-points").getAsInt(), false);
            }

            if (reader.has("commendations")) {
                player.getPointsHandler().setCommendations(reader.get("commendations").getAsInt(), false);
            }

            if (reader.has("loyalty-points")) {
                player.getPointsHandler().setLoyaltyPoints(reader.get("loyalty-points").getAsInt(), false);
            }


            if (reader.has("openedstarterbox")) {
                player.setOpenedStarterBox(reader.get("openedstarterbox").getAsBoolean());
            }

            if (reader.has("voting-points")) {
                player.getPointsHandler().setVotingPoints(reader.get("voting-points").getAsInt(), false);
            }
            if (reader.has("spawn-killcount")) {
                player.getPointsHandler().setSPAWNKILLCount(reader.get("spawn-killcount").getAsInt(), false);
            }
            if (reader.has("lord-killcount")) {
                player.getPointsHandler().setLORDKILLCount(reader.get("lord-killcount").getAsInt(), false);
            }
            if (reader.has("demon-killcount")) {
                player.getPointsHandler().setDEMONKILLCount(reader.get("demon-killcount").getAsInt(), false);
            }
            if (reader.has("dragon-killcount")) {
                player.getPointsHandler().setDRAGONKILLCount(reader.get("dragon-killcount").getAsInt(), false);
            }
            if (reader.has("beast-killcount")) {
                player.getPointsHandler().setBEASTKILLCount(reader.get("beast-killcount").getAsInt(), false);
            }
            if (reader.has("king-killcount")) {
                player.getPointsHandler().setKINGKILLCount(reader.get("king-killcount").getAsInt(), false);
            }
            if (reader.has("avatar-killcount")) {
                player.getPointsHandler().setAVATARKILLCount(reader.get("avatar-killcount").getAsInt(), false);
            }
            if (reader.has("angel-killcount")) {
                player.getPointsHandler().setANGELKILLCount(reader.get("angel-killcount").getAsInt(), false);
            }
            if (reader.has("lucien-killcount")) {
                player.getPointsHandler().setLUCIENKILLCount(reader.get("lucien-killcount").getAsInt(), false);
            }
            if (reader.has("hercules-killcount")) {
                player.getPointsHandler().setHERCULESKILLCount(reader.get("hercules-killcount").getAsInt(), false);
            }
            if (reader.has("satan-killcount")) {
                player.getPointsHandler().setSATANKILLCount(reader.get("satan-killcount").getAsInt(), false);
            }
            if (reader.has("zeus-killcount")) {
                player.getPointsHandler().setZEUSKILLCount(reader.get("zeus-killcount").getAsInt(), false);
            }
            if (reader.has("mini-lucifer-killcount")) {
                player.getPointsHandler().setMiniLuciferkillcount(reader.get("mini-lucifer-killcount").getAsInt());
            }
            if (reader.has("lucifer-killcount")) {
                player.getPointsHandler().setLuciferkillcount(reader.get("lucifer-killcount").getAsInt());
            }
            if (reader.has("npc-killcount")) {
                player.getPointsHandler().setNPCKILLCount(reader.get("npc-killcount").getAsInt(), false);
            }
            if (reader.has("total-prestiges")) {
                player.getPointsHandler().setTotalPrestiges(reader.get("total-prestiges").getAsInt(), false);
            }
            if (reader.has("slayer-spree")) {
                player.getPointsHandler().setSlayerSpree(reader.get("slayer-spree").getAsInt(), false);
            }
            if (reader.has("event-points")) {
                player.getPointsHandler().setEventPoints(reader.get("event-points").getAsInt(), false);
            }
            if (reader.has("boss-points")) {
                player.getPointsHandler().setBossPoints(reader.get("boss-points").getAsInt(), false);
            }
            if (reader.has("shilling-rate")) {
                player.getPointsHandler().setSHILLINGRate(reader.get("shilling-rate").getAsInt(), false);
            }

            if (reader.has("slayer-points")) {
                player.getPointsHandler().setSlayerPoints(reader.get("slayer-points").getAsInt(), false);
            }

            if (reader.has("pk-points")) {
                player.getPointsHandler().setPkPoints(reader.get("pk-points").getAsInt(), false);
            }

            if (reader.has("player-kills")) {
                player.getPlayerKillingAttributes().setPlayerKills(reader.get("player-kills").getAsInt());
            }

            if (reader.has("player-killstreak")) {
                player.getPlayerKillingAttributes().setPlayerKillStreak(reader.get("player-killstreak").getAsInt());
            }

            if (reader.has("player-deaths")) {
                player.getPlayerKillingAttributes().setPlayerDeaths(reader.get("player-deaths").getAsInt());
            }

            if (reader.has("target-percentage")) {
                player.getPlayerKillingAttributes().setTargetPercentage(reader.get("target-percentage").getAsInt());
            }

            if (reader.has("bh-rank")) {
                player.getAppearance().setBountyHunterSkull(reader.get("bh-rank").getAsInt());
            }

            if (reader.has("gender")) {
                player.getAppearance().setGender(Gender.valueOf(reader.get("gender").getAsString()));
            }

            if (reader.has("spell-book")) {
                player.setSpellbook(MagicSpellbook.valueOf(reader.get("spell-book").getAsString()));
            }

            if (reader.has("prayer-book")) {
                player.setPrayerbook(Prayerbook.valueOf(reader.get("prayer-book").getAsString()));
            }
            if (reader.has("running")) {
                player.setRunning(reader.get("running").getAsBoolean());
            }
            if (reader.has("run-energy")) {
                player.setRunEnergy(reader.get("run-energy").getAsInt());
            }
            if (reader.has("music")) {
                player.setMusicActive(reader.get("music").getAsBoolean());
            }
            if (reader.has("sounds")) {
                player.setSoundsActive(reader.get("sounds").getAsBoolean());
            }
            if (reader.has("auto-retaliate")) {
                player.setAutoRetaliate(reader.get("auto-retaliate").getAsBoolean());
            }
            if (reader.has("xp-locked")) {
                player.setExperienceLocked(reader.get("xp-locked").getAsBoolean());
            }
            if (reader.has("veng-cast")) {
                player.setHasVengeance(reader.get("veng-cast").getAsBoolean());
            }
            if (reader.has("last-veng")) {
                player.getLastVengeance().reset(reader.get("last-veng").getAsLong());
            }
            if (reader.has("fight-type")) {
                player.setFightType(FightType.valueOf(reader.get("fight-type").getAsString()));
            }
            if (reader.has("sol-effect")) {
                player.setStaffOfLightEffect(Integer.valueOf(reader.get("sol-effect").getAsInt()));
            }
            if (reader.has("skull-timer")) {
                player.setSkullTimer(reader.get("skull-timer").getAsInt());
            }
            if (reader.has("accept-aid")) {
                player.setAcceptAid(reader.get("accept-aid").getAsBoolean());
            }
            if (reader.has("poison-damage")) {
                player.setBurnDamage(reader.get("poison-damage").getAsInt());
            }
            if (reader.has("poison-immunity")) {
                player.setPoisonImmunity(reader.get("poison-immunity").getAsInt());
            }
            if (reader.has("double-dr-timer")) {
                player.setDropRateTimerOld(reader.get("double-dr-timer").getAsInt());
            }
            if (reader.has("double-ddr-timer")) {
                player.setDoubleDDRTimer(reader.get("double-ddr-timer").getAsInt());
            }
            if (reader.has("double-dmg-timer")) {
                player.setDoubleDMGTimer(reader.get("double-dmg-timer").getAsInt());
            }
            if (reader.has("fire-immunity")) {
                player.setFireImmunity(reader.get("fire-immunity").getAsInt());
            }
            if (reader.has("fire-damage-mod")) {
                player.setFireDamageModifier(reader.get("fire-damage-mod").getAsInt());
            }
            if (reader.has("prayer-renewal-timer")) {
                player.setPrayerRenewalPotionTimer(reader.get("prayer-renewal-timer").getAsInt());
            }
            if (reader.has("teleblock-timer")) {
                player.setTeleblockTimer(reader.get("teleblock-timer").getAsInt());
            }
            if (reader.has("special-amount")) {
                player.setSpecialPercentage(reader.get("special-amount").getAsInt());
            }

            if (reader.has("entered-gwd-room")) {
                player.getMinigameAttributes().getGodwarsDungeonAttributes()
                        .setHasEnteredRoom(reader.get("entered-gwd-room").getAsBoolean());
            }

            if (reader.has("gwd-altar-delay")) {
                player.getMinigameAttributes().getGodwarsDungeonAttributes()
                        .setAltarDelay(reader.get("gwd-altar-delay").getAsLong());
            }

            if (reader.has("gwd-killcount")) {
                player.getMinigameAttributes().getGodwarsDungeonAttributes()
                        .setKillcount(builder.fromJson(reader.get("gwd-killcount"), int[].class));
            }

            if (reader.has("effigy")) {
                player.setEffigy(reader.get("effigy").getAsInt());
            }

            if (reader.has("summon-npc")) {
                int npc = reader.get("summon-npc").getAsInt();
                if (npc > 0)
                    player.getSummoning().setFamiliarSpawnTask(new FamiliarSpawnTask(player)).setFamiliarId(npc);
            }
            if (reader.has("summon-death")) {
                int death = reader.get("summon-death").getAsInt();
                if (death > 0 && player.getSummoning().getSpawnTask() != null)
                    player.getSummoning().getSpawnTask().setDeathTimer(death);
            }
            if (reader.has("process-farming")) {
                player.setProcessFarming(reader.get("process-farming").getAsBoolean());
            }

            if (reader.has("clanchat")) {
                String clan = reader.get("clanchat").getAsString();
                if (!clan.equals("null"))
                    player.setClanChatName(clan);
            }
            if (reader.has("autocast")) {
                player.setAutocast(reader.get("autocast").getAsBoolean());
            }
            if (reader.has("autocast-spell")) {
                int spell = reader.get("autocast-spell").getAsInt();
                if (spell != -1)
                    player.setAutocastSpell(CombatSpells.getSpell(spell));
            }

            if (reader.has("dfs-charges")) {
                player.incrementDfsCharges(reader.get("dfs-charges").getAsInt());
            }



            if (reader.has("kills")) {
                KillsTracker.submit(player, builder.fromJson(reader.get("kills").getAsJsonArray(), KillsEntry[].class));
            }

            if (reader.has("drops")) {
                DropLog.submit(player, builder.fromJson(reader.get("drops").getAsJsonArray(), DropLogEntry[].class));
            }

            if (reader.has("collection-log")) {
                CollectionLogManager.LogProgress[] logs = builder.fromJson(reader.get("collection-log").getAsJsonArray(), CollectionLogManager.LogProgress[].class);
                for (CollectionLogManager.LogProgress l : logs) {
                    player.getCollectionLogManager().addLogProgress(l);
                }
            }

            if (reader.has("claimed-logs")) {
                String[] logs = builder.fromJson(reader.get("claimed-logs").getAsJsonArray(), String[].class);
                for (String l : logs) {
                    player.getCollectionLogManager().getClaimedCollectionRewards().add(l);
                }
            }



            if (reader.has("slayer-master")) {
                player.getSlayer().setSlayerMaster(SlayerMaster.valueOf(reader.get("slayer-master").getAsString()));
            }

            if (reader.has("slayer-task")) {
                player.getSlayer().setSlayerTask(SlayerTasks.valueOf(reader.get("slayer-task").getAsString()));
            }

            if (reader.has("lastlogin"))
                player.lastLogin = (reader.get("lastlogin").getAsLong());
            if (reader.has("lastdailyclaim"))
                player.lastDailyClaim = (reader.get("lastdailyclaim").getAsLong());

            if (reader.has("day1claimed"))
                player.day1Claimed = (reader.get("day1claimed").getAsBoolean());

            if (reader.has("day2claimed"))
                player.day2Claimed = (reader.get("day2claimed").getAsBoolean());

            if (reader.has("day3claimed"))
                player.day3Claimed = (reader.get("day3claimed").getAsBoolean());

            if (reader.has("day4claimed"))
                player.day4Claimed = (reader.get("day4claimed").getAsBoolean());

            if (reader.has("day5claimed"))
                player.day5Claimed = (reader.get("day5claimed").getAsBoolean());

            if (reader.has("day6claimed"))
                player.day6Claimed = (reader.get("day6claimed").getAsBoolean());

            if (reader.has("day7claimed"))
                player.day7Claimed = (reader.get("day7claimed").getAsBoolean());

            if (reader.has("lastvotetime"))
                player.lastVoteTime = (reader.get("lastvotetime").getAsLong());

            if (reader.has("hasvotedtoday"))
                player.hasVotedToday = (reader.get("hasvotedtoday").getAsBoolean());

            if (reader.has("prev-slayer-task")) {
                player.getSlayer().setLastTask(SlayerTasks.valueOf(reader.get("prev-slayer-task").getAsString()));
            }

            if (reader.has("task-amount")) {
                player.getSlayer().setAmountToSlay(reader.get("task-amount").getAsInt());
            }

            if (reader.has("task-streak")) {
                player.getSlayer().setTaskStreak(reader.get("task-streak").getAsInt());
            }
            if (reader.has("task-streak-Beast-MasterI")) {
                player.getSlayer().setTaskStreakI(reader.get("task-streak-Beast-MasterI").getAsInt());
            }
            if (reader.has("task-streak-Beast-MasterX")) {
                player.getSlayer().setTaskStreakX(reader.get("task-streak-Beast-MasterX").getAsInt());
            }

            if (reader.has("astralstreak")) {
                player.getSlayer().setAstralstreak(reader.get("astralstreak").getAsInt());
            }

            if (reader.has("dragonstreak")) {
                player.getSlayer().setDragonstreak(reader.get("dragonstreak").getAsInt());
            }

            if (reader.has("godstreak")) {
                player.getSlayer().setGodstreak(reader.get("godstreak").getAsInt());
            }



            if (reader.has("duo-partner")) {
                String partner = reader.get("duo-partner").getAsString();
                player.getSlayer().setDuoPartner(partner.equals("null") ? null : partner);
            }

            if (reader.has("double-slay-xp")) {
                player.getSlayer().doubleSlayerXP = reader.get("double-slay-xp").getAsBoolean();
            }

            if (reader.has("killed-players")) {
                List<String> list = new ArrayList<>();
                String[] killed_players = builder.fromJson(reader.get("killed-players").getAsJsonArray(),
                        String[].class);
                for (String s : killed_players)
                    list.add(s);
                player.getPlayerKillingAttributes().setKilledPlayers(list);
            }


            if (reader.has("vod-brother")) {
                player.getMinigameAttributes().getVoidOfDarknessAttributes().setBarrowsData(
                        builder.fromJson(reader.get("vod-brother").getAsJsonArray(), int[][].class));
            }
            if (reader.has("halloween")) {
                player.getMinigameAttributes().getHalloweenAttributes()
                        .setQuestParts(builder.fromJson(reader.get("halloween").getAsJsonArray(), boolean[].class));
            }



            if (reader.has("vod-killcount")) {
                player.getMinigameAttributes().getVoidOfDarknessAttributes()
                        .setKillcount((reader.get("vod-killcount").getAsInt()));
            }
            if (reader.has("hov-killcount")) {
                player.getMinigameAttributes().getHallsOfValorAttributes()
                        .setKillcount((reader.get("hov-killcount").getAsInt()));
            }
            if (reader.has("barrows-brother")) {
                player.getMinigameAttributes().getBarrowsMinigameAttributes().setBarrowsData(
                        builder.fromJson(reader.get("barrows-brother").getAsJsonArray(), int[][].class));
            }

            if (reader.has("random-coffin")) {
                player.getMinigameAttributes().getBarrowsMinigameAttributes()
                        .setRandomCoffin((reader.get("random-coffin").getAsInt()));
            }

            if (reader.has("barrows-killcount")) {
                player.getMinigameAttributes().getBarrowsMinigameAttributes()
                        .setKillcount((reader.get("barrows-killcount").getAsInt()));
            }


            if (reader.has("dung-items-bound")) {
                player.getMinigameAttributes().getDungeoneeringAttributes()
                        .setBoundItems(builder.fromJson(reader.get("dung-items-bound").getAsJsonArray(), int[].class));
            }

            if (reader.has("rune-ess")) {
                player.setStoredRuneEssence((reader.get("rune-ess").getAsInt()));
            }

            if (reader.has("pure-ess")) {
                player.setStoredPureEssence((reader.get("pure-ess").getAsInt()));
            }


            if (reader.has("bank-pin")) {
                player.getBankPinAttributes()
                        .setBankPin(builder.fromJson(reader.get("bank-pin").getAsJsonArray(), int[].class));
            }

            if (reader.has("has-bank-pin")) {
                player.getBankPinAttributes().setHasBankPin(reader.get("has-bank-pin").getAsBoolean());
            }
            if (reader.has("last-pin-attempt")) {
                player.getBankPinAttributes().setLastAttempt(reader.get("last-pin-attempt").getAsLong());
            }
            if (reader.has("invalid-pin-attempts")) {
                player.getBankPinAttributes().setInvalidAttempts(reader.get("invalid-pin-attempts").getAsInt());
            }

            if (reader.has("bank-pin")) {
                player.getBankPinAttributes()
                        .setBankPin(builder.fromJson(reader.get("bank-pin").getAsJsonArray(), int[].class));
            }

            if (reader.has("appearance")) {
                player.getAppearance().set(builder.fromJson(reader.get("appearance").getAsJsonArray(), int[].class));
            }

            if (reader.has("agility-obj")) {
                player.setCrossedObstacles(
                        builder.fromJson(reader.get("agility-obj").getAsJsonArray(), boolean[].class));
            }

            if (reader.has("skills")) {
                player.getSkillManager().setSkills(builder.fromJson(reader.get("skills"), Skills.class));
            }

            if (reader.has("offences")) {
                ArrayList<String> list = new ArrayList<>();
                String[] killed_players = builder.fromJson(reader.get("offences").getAsJsonArray(), String[].class);
                for (String s : killed_players)
                    list.add(s);
                player.setOffences(list);
            }

            /** BANK **/
            for (int i = 0; i < 9; i++) {
                if (reader.has("bank-" + i + ""))
                    player.setBank(i, new Bank(player)).getBank(i).addItems(
                            builder.fromJson(reader.get("bank-" + i + "").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-0")) {
                player.setBank(0, new Bank(player)).getBank(0)
                        .addItems(builder.fromJson(reader.get("bank-0").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-1")) {
                player.setBank(1, new Bank(player)).getBank(1)
                        .addItems(builder.fromJson(reader.get("bank-1").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-2")) {
                player.setBank(2, new Bank(player)).getBank(2)
                        .addItems(builder.fromJson(reader.get("bank-2").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-3")) {
                player.setBank(3, new Bank(player)).getBank(3)
                        .addItems(builder.fromJson(reader.get("bank-3").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-4")) {
                player.setBank(4, new Bank(player)).getBank(4)
                        .addItems(builder.fromJson(reader.get("bank-4").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-5")) {
                player.setBank(5, new Bank(player)).getBank(5)
                        .addItems(builder.fromJson(reader.get("bank-5").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-6")) {
                player.setBank(6, new Bank(player)).getBank(6)
                        .addItems(builder.fromJson(reader.get("bank-6").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-7")) {
                player.setBank(7, new Bank(player)).getBank(7)
                        .addItems(builder.fromJson(reader.get("bank-7").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("bank-8")) {
                player.setBank(8, new Bank(player)).getBank(8)
                        .addItems(builder.fromJson(reader.get("bank-8").getAsJsonArray(), Item[].class), false);
            }

            if (reader.has("ge-slots")) {
                GrandExchangeSlot[] slots = builder.fromJson(reader.get("ge-slots").getAsJsonArray(),
                        GrandExchangeSlot[].class);
                player.setGrandExchangeSlots(slots);
            }

            if (reader.has("store")) {
                Item[] validStoredItems = builder.fromJson(reader.get("store").getAsJsonArray(), Item[].class);
                if (player.getSummoning().getSpawnTask() != null) {
                    player.getSummoning().getSpawnTask().setValidItems(validStoredItems);
                }
            }

            if (reader.has("charm-imp")) {
                int[] charmImpConfig = builder.fromJson(reader.get("charm-imp").getAsJsonArray(), int[].class);
                player.getSummoning().setCharmimpConfig(charmImpConfig);
            }

            if (reader.has("friends")) {
                long[] friends = builder.fromJson(reader.get("friends").getAsJsonArray(), long[].class);

                for (long l : friends) {
                    player.getRelations().getFriendList().add(l);
                }
            }
            if (reader.has("ignores")) {
                long[] ignores = builder.fromJson(reader.get("ignores").getAsJsonArray(), long[].class);

                for (long l : ignores) {
                    player.getRelations().getIgnoreList().add(l);
                }
            }

            if (reader.has("loyalty-titles")) {
                player.setUnlockedLoyaltyTitles(
                        builder.fromJson(reader.get("loyalty-titles").getAsJsonArray(), boolean[].class));
            }



            if (reader.has("yellhexcolor")) {
                player.setYellHex(reader.get("yellhexcolor").getAsString());
            }

            if (reader.has("yell-tit")) {
                player.setYellTitle(reader.get("yell-tit").getAsString());
            }

            if (reader.has("reffered")) {
                player.setReffered(reader.get("reffered").getAsBoolean());
            }

            if (reader.has("indung")) {
                player.setInDung(reader.get("indung").getAsBoolean());
            }

            if (reader.has("toggledglobalmessages")) {
                player.setToggledGlobalMessages(reader.get("toggledglobalmessages").getAsBoolean());
            }

            if (reader.has("barrowschests")) {
                player.getPointsHandler().setBarrowsChests(reader.get("barrowschests").getAsInt(), false);
            }

            if (reader.has("cluesteps")) {
                player.getPointsHandler().setClueSteps(reader.get("cluesteps").getAsInt(), false);
            }

            if (reader.has("currency-pouch")) {
                player.setCurrencyPouch(builder.fromJson(reader.get("currency-pouch"), CurrencyPouch.class));
            }

            if (reader.has("gambling-pass")) {
                player.setGamblingPass(reader.get("gambling-pass").getAsInt());
            }

            if (reader.has("hcimdunginventory")) {
                player.getDungeoneeringIronInventory()
                        .setItems(builder.fromJson(reader.get("hcimdunginventory").getAsJsonArray(), Item[].class));
            }

            if (reader.has("hcimdungequipment")) {
                player.getDungeoneeringIronEquipment()
                        .setItems(builder.fromJson(reader.get("hcimdungequipment").getAsJsonArray(), Item[].class));
            }

            if (reader.has("bonecrusheffect")) {
                player.setBonecrushEffect(reader.get("bonecrusheffect").getAsBoolean());
            }

            if (reader.has("attributes")) {
                JsonElement attributes = reader.get("attributes");
                Map<String, Object> map = builder.fromJson(attributes, new TypeToken<Map<String, Object>>() {}.getType());

                map.forEach((key, val) -> {
                    AttributeKey<Object> attr = new AttributeKey<>(key);
                    Object value = (val instanceof Double) ? (int) Math.round((Double) val) : val;
                    player.getAttributeMap().set(attr, value, false);
                });
            }

            if (reader.has("p-tps")) {
                player.setPreviousTeleports(builder.fromJson(reader.get("p-tps").getAsJsonArray(), int[].class));
            }

            if (reader.has("achievements-points")) {
                int points = reader.get("achievements-points").getAsInt();
                player.getAchievements().setPoints(points);
            }
            if (reader.has("achievements-amount")) {
                int[][] amountRemaining = builder.fromJson(reader.get("achievements-amount").getAsJsonArray(),
                        int[][].class);
                int[][] temp = new int[MAXIMUM_TIERS][MAXIMUM_TIER_ACHIEVEMENTS];
                for(int i = 0; i < amountRemaining.length; i++) {
                    for(int k = 0; k < amountRemaining[i].length; k++) {
                        temp[i][k] = amountRemaining[i][k];
                    }
                }
                player.getAchievements().setAmountRemaining(temp);
            }

            if (reader.has("achievements-completed")) {
                boolean[][] completed = builder.fromJson(reader.get("achievements-completed").getAsJsonArray(),
                        boolean[][].class);
                boolean[][] temp = new boolean[MAXIMUM_TIERS][MAXIMUM_TIER_ACHIEVEMENTS];
                for(int i = 0; i < completed.length; i++) {
                    for(int k = 0; k < completed[i].length; k++) {
                        temp[i][k] = completed[i][k];
                    }
                }
                player.getAchievements().setCompleted(temp);
            }
            if (reader.has("achievements-daily")) {
                player.getAchievements().setDailyTaskDate(reader.get("achievements-daily").getAsLong());
            }

            if (reader.has("progression-zones")) {
                player.setProgressionZones(builder.fromJson(reader.get("progression-zones"), int[].class));
            }
            if (reader.has("zones-complete")) {
                player.setZonesComplete(builder.fromJson(reader.get("zones-complete"), boolean[].class));
            }

            if (reader.has("gamble-banned")) {
                player.setGambleBanned(reader.get("gamble-banned").getAsBoolean());
            }


            if (reader.has("skillingtools")) {
                player.setClaimedskillingtools(reader.get("skillingtools").getAsBoolean());
            }
            if (reader.has("eventstoggle")) {
                player.setEventstoggle(reader.get("eventstoggle").getAsBoolean());
            }



            if (reader.has("lastInstanceNpc")) {
                player.lastInstanceNpc = reader.get("lastInstanceNpc").getAsInt();
            }

            if (reader.has("daily-task")) {
                HashMap<DailyTask, TaskChallenge> dailyTasks = builder.fromJson(reader.get("daily-task"),
                        new TypeToken<HashMap<DailyTask, TaskChallenge>>() {
                        }.getType());
                player.setDailies(dailyTasks);
            }

            if (reader.has("daily-skips")) {
                player.dailySkips = reader.get("daily-skips").getAsInt();
            }

            if (reader.has("dailies-done")) {
                player.dailiesCompleted = reader.get("dailies-done").getAsInt();
            }

            if (reader.has("daily-task-info")) {
                player.taskInfo = reader.get("daily-task-info").getAsString();
            }

            if (reader.has("level-notifications")) {
                player.levelNotifications = reader.get("level-notifications").getAsBoolean();
            }

            if (reader.has("dailies-received-times")) {
                player.setTaskReceivedTimes(builder.fromJson(reader.get("dailies-received-times").getAsJsonArray(), long[].class));
            }

            if (reader.has("obtained-pets")) {
                BossPets.BossPet[] data = builder.fromJson(reader.get("obtained-pets").getAsJsonArray(), BossPets.BossPet[].class);
                for (BossPets.BossPet l : data) {
                    player.getObtainedPets().add(l);
                }
            }

            if (reader.has("monthly-claimed")) {
                boolean[] claimed = builder.fromJson(reader.get("monthly-claimed").getAsJsonArray(),
                    boolean[].class);
                player.setClaimed(claimed);
            }

            if (reader.has("monthly-donated")) {
                player.setDonatedThroughMonth(reader.get("monthly-donated").getAsInt());
            }

            if (reader.has("membership-end-date")) {
                player.getMembership().endDate = builder.fromJson(reader.get("membership-end-date"), LocalDateTime.class);
            }

            if (reader.has("daily-deals-claimed")) {
                player.setDailyDealsClaimed(reader.get("daily-deals-claimed").getAsInt());
            }

            if (reader.has("lifetime-streaks")) {
                player.setLifetimeStreakVal(builder.fromJson(reader.get("lifetime-streaks"), int[].class));
            }

            if (reader.has("autoBank")) {
                player.setAutoBank(reader.get("autoBank").getAsBoolean());
            }

            if (reader.has("autoDissolve")) {
                player.setAutoDissolve(reader.get("autoDissolve").getAsBoolean());
            }
            if (reader.has("totalDonatedThroughStore")) {
                player.setTotalDonatedThroughStore(reader.get("totalDonatedThroughStore").getAsInt());
            }

            if (reader.has("christmas2023stage")) {
                player.setChristmasQuest2023Stage(reader.get("christmas2023stage").getAsInt());
            }


            if (reader.has("easterQuestStage")) {
                player.setEasterQuestStage(reader.get("easterQuestStage").getAsInt());
            }
            if (reader.has("searchedKingsWardrobe")) {
                player.setSearchedKingsWardrobe(reader.get("searchedKingsWardrobe").getAsBoolean());
            }
            if (reader.has("rabidBunniesKilled")) {
                player.setRabidBunniesKilled(reader.get("rabidBunniesKilled").getAsInt());
            }
            if (reader.has("foundFinalEgg")) {
                player.setFoundFinalEgg(reader.get("foundFinalEgg").getAsBoolean());
            }
            if (reader.has("acceptedEasterReward")) {
                player.setAcceptedEasterReward(reader.get("acceptedEasterReward").getAsBoolean());
            }



            if (reader.has("christmascompleted")) {
                player.setCompletedChristmas(reader.get("christmascompleted").getAsBoolean());
            }

            if (reader.has("boxes")) {
                BoxesTracker.submit(player, builder.fromJson(reader.get("boxes").getAsJsonArray(), BoxesTracker.BoxEntry[].class));
            }

            if (reader.has("rock-cake-charges")) {
                player.setRockCakeCharges(reader.get("rock-cake-charges").getAsInt());
            }

            if (reader.has("best-tower-time")) {
                player.setBest_tower_time(reader.get("best-tower-time").getAsInt());
            }

            if (reader.has("tower-runs")) {
                player.setTotal_tower_completions(reader.get("tower-runs").getAsInt());
            }

            if (reader.has("tower-wave")) {
                player.setBest_tower_wave(reader.get("tower-wave").getAsInt());
            }

            if (reader.has("tower-invocations-unlocked")) {
                player.getInvocations().setTower_invocations(builder.fromJson(reader.get("tower-invocations-unlocked"), boolean[].class));
            }

            if (reader.has("tower-invocations-in-use")) {
                player.getInvocations().setTower_invocations_s(builder.fromJson(reader.get("tower-invocations-in-use"), boolean[].class));
            }
            if (reader.has("nodes-unlocked")) {
                player.setNodesUnlocked(builder.fromJson(reader.get("nodes-unlocked"), Node[].class));
            }
            if (reader.has("bridges-unlocked")) {
                player.setBridgesUnlocked(builder.fromJson(reader.get("bridges-unlocked"), Bridge[].class));
            }

            if (reader.has("current-section")) {
                String rights = reader.get("current-section").getAsString();
                player.setCurrentSection(Section.valueOf(rights));
            }

            if (reader.has("current-path")) {
                String rights = reader.get("current-path").getAsString();
                player.setCurrentPath(com.ruse.world.content.tree.Path.valueOf(rights));
            }
            if (reader.has("last-path")) {
                String rights = reader.get("last-path").getAsString();
                player.setLastPath(com.ruse.world.content.tree.Path.valueOf(rights));
            }
            if (reader.has("tree-unlocked")) {
                player.setTreeUnlocked(reader.get("tree-unlocked").getAsBoolean());
            }

            if (reader.has("last-free-box")) {
                if (!reader.get("last-free-box").getAsString().equalsIgnoreCase("null")) {
                    player.setLastFreeBox(builder.fromJson(reader.get("last-free-box"), LocalDateTime.class));
                }
            }

            if (reader.has("reset-forest-time")) {
                String raw = reader.get("reset-forest-time").getAsString();

                if ("null".equalsIgnoreCase(raw)) {
                    player.setForestResetTime(null);
                    player.setDailyForestTaskAmount(0);
                } else {
                    try {
                        LocalDateTime parsedDate = LocalDateTime.parse(raw, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                        player.setForestResetTime(parsedDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (reader.has("aoe-size-increase")) {
                player.getAoeToken().setAoe_size(reader.get("aoe-size-increase").getAsInt());
            }

            if (reader.has("basilisk-kills")) {
                player.setBassilisk_kills(reader.get("basilisk-kills").getAsInt());
            }
            if (reader.has("brimstone-kills")) {
                player.setBrimstone_kills(reader.get("brimstone-kills").getAsInt());
            }
            if (reader.has("everthorn-kills")) {
                player.setEverthorn_kills(reader.get("everthorn-kills").getAsInt());
            }

            if (reader.has("last-campaign")) {
                player.setLastCampaignCompleted(reader.get("last-campaign").getAsInt());
            }

            if (reader.has("last-campaign-reset")) {
                if (!reader.get("last-campaign-reset").getAsString().equalsIgnoreCase("null")) {
                    player.setLastCampaignResetDate(builder.fromJson(reader.get("last-campaign-reset"), LocalDate.class));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return LoginResponses.LOGIN_SUCCESSFUL;
        }
        return LoginResponses.LOGIN_SUCCESSFUL;
    }
}
