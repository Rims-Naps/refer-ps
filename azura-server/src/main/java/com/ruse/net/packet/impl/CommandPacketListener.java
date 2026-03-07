package com.ruse.net.packet.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.ruse.*;
import com.ruse.ServerSaves.*;
import com.ruse.donation.DonatorRanks;
import com.ruse.donation.StoreItems;
import com.ruse.donation.daily.DealsHandler;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.globalevents.*;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.container.impl.Shop;
import com.ruse.model.definitions.*;
import com.ruse.model.input.impl.EnterReferral;
import com.ruse.model.input.impl.Moderation.*;
import com.ruse.model.input.impl.ChangePassword;
import com.ruse.model.input.impl.SetPinPacketListener;
//import com.ruse.motivote3.doMotivote;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.net.security.ConnectionHandler;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.clip.region.RegionClipping;
import com.ruse.world.content.*;
import com.ruse.world.content.AoE.AOESystem;
import com.ruse.world.content.GlobalBosses.ArchonBossEvent;
import com.ruse.world.content.GlobalBosses.AscendentBossEvent;
import com.ruse.world.content.GlobalBosses.CelestialBossEvent;
import com.ruse.world.content.GlobalBosses.GladiatorBossEvent;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.ZoneProgression.NpcRequirements;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.bossfights.impl.EverthornFight;
import com.ruse.world.content.holidays.HolidayManager;
import com.ruse.world.content.clan.ClanChat;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.combat.Maxhits;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.weapon.CombatSpecial;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.forgottenRaids.data.RaidDataManager;
import com.ruse.world.content.forgottenRaids.party.ForgottenRaidParty;
import com.ruse.world.content.grandexchange.GrandExchangeOffers;
import com.ruse.world.content.groupironman.GroupManager;
import com.ruse.donation.LifetimeStreakHandler;
import com.ruse.donation.PersonalCampaignHandler;
import com.ruse.donation.ServerCampaignHandler;
import com.ruse.world.content.minigames.impl.TreasureHunter;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.pos.PlayerOwnedShopManager;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.skill.SkillManager;
import com.ruse.world.content.skill.impl.slayer.Slayer;
import com.ruse.world.content.transmorgify.Transformations;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.content.watchlist.WatchListManager;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.npc.NPCMovementCoordinator;
import com.ruse.world.entity.impl.npc.NPCSpawn;
import com.ruse.world.entity.impl.player.Player;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.ruse.model.container.impl.Equipment.WEAPON_SLOT;
import static com.ruse.world.content.Sounds.sendSound;

/**
 * This packet listener manages commands a player uses by using the command
 * console prompted by using the "`" char.
 *
 * @author Gabriel Hannason
 */

public class CommandPacketListener implements PacketListener {


    public static double DonoBossAmount = DonationAmountReader.getDonoBossAmount();
    public static int voteCount = (int) VoteAmountReader.getVoteCount();
    public static boolean votebossalive = false;
    static SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy, HH:mm:ss");

    private static void playerCommands(Player player, String[] command, String wholeCommand) {


        if (!player.completedBeginner){
            player.msgRed("Gather all 3 Elemental shards before moving on!");
            return;
        }
        if (command[0].equalsIgnoreCase("claimstorage")) {
            OfflineStorageSystem.claimStorage(player);
        }
        if (command[0].startsWith("tree")) {
            player.getSkillTree().displayInterface();
        }

        if (command[0].equalsIgnoreCase("gc")) {
            if (player.getLocation() == Location.NECROMANCY_GAME_AREA) {
                String message = "<col=0><shad=6C1894>[NECRO]" + "@red@ " + player.getUsername() + "@bla@: <shad=6C1894>@blu@" + wholeCommand.substring(command[0].length() + 1);
                NecromancyMinigame.sendGameMessage(message);
            }
        }

        if (command[0].startsWith("tower") || command[0].startsWith("toa") ) {
            if (!player.getRights().isManagement()) {
                if (player.getSkillManager().getMaxLevel(Skill.NECROMANCY) < 70) {
                    player.msgRed("Reach 70 Necromancy!");
                    return;
                }
                if (player.getSkillManager().getMaxLevel(Skill.SLAYER) < 70) {
                    player.msgRed("Reach 70 Slayer!");
                    return;
                }
                if (KillsTracker.getTotalKills(player) < 10000) {
                    player.msgRed("Reach 10K Npc Kills!");
                    return;
                }
            }
            TeleportHandler.teleportPlayer(player, new Position(3486, 9246, 0), TeleportType.ANCIENT);
        }


        if (command[0].startsWith("resetbp")) {
            if (player.getBattlePass().getType() == BattlePassType.NONE){
                player.msgRed("You don't have a Battlepass Active");
                return;
            }

            if (player.getBattlePass().getType() == BattlePassType.TIER1) {
                if (player.getBattlePass().getBattlePass_Tier1_Level() != 40){
                    player.msgRed("You must reach level 40 before resetting your Battlepass.");
                    return;
                }
                player.getBattlePass().setBattlePass_Tier1_Level(0);
                player.getBattlePass().setBattlePass_Tier1_XP(0);
                player.getBattlePass().setType(BattlePassType.NONE);
                player.msgRed("You have reset your Battlepass");
                return;
            }

            if (player.getBattlePass().getType() == BattlePassType.TIER2) {
                if (player.getBattlePass().getBattlePass_Tier2_Level() != 40){
                    player.msgRed("You must reach level 40 before resetting your Battlepass.");
                    return;
                }
                player.getBattlePass().setBattlePass_Tier2_Level(0);
                player.getBattlePass().setBattlePass_Tier2_XP(0);
                player.getBattlePass().setType(BattlePassType.NONE);
                player.msgRed("You have reset your Battlepass");
                return;
            }
        }

     /*   if (command[0].equalsIgnoreCase("christmas") && LocalDateTime.now().isBefore(LocalDateTime.of(2023, 12, 31, 23, 59, 59))) {
            TeleportHandler.teleportPlayer(player, new Position(2561, 4639, 0), TeleportType.NORMAL);
        }*/

      /*  if (command[0].equalsIgnoreCase("santa") && LocalDateTime.now().isBefore(LocalDateTime.of(2023, 12, 31, 23, 59, 59))) {
            if (!player.isCompletedChristmas()){
                player.msgRed("You must complete the Christmas Quest! Speak to Santa at Home.");
                return;
            }
            TeleportHandler.teleportPlayer(player, new Position(2561, 4615, 0), TeleportType.NORMAL);
        }*/

  /*     if (command[0].equalsIgnoreCase("cupid")) {
            TeleportHandler.teleportPlayer(player, new Position(2143, 3223, 1), TeleportType.NORMAL);
        }*/

        if (command[0].equalsIgnoreCase("bunny")) {
            TeleportHandler.teleportPlayer(player, new Position(2528, 3802, 4), TeleportType.NORMAL);
        }
        if (command[0].equalsIgnoreCase("wiki")) {
            player.getPacketSender().sendString(1, GameSettings.WikiUrl);
        }
        if (command[0].equalsIgnoreCase("guides")) {
            player.getPacketSender().sendString(1, GameSettings.Guides);
        }
        if (command[0].equalsIgnoreCase("easter")) {
            TeleportHandler.teleportPlayer(player, new Position(2528, 3802, 8), TeleportType.NORMAL);
        }
//        if (command[0].equalsIgnoreCase("spring")) {
//            TeleportHandler.teleportPlayer(player, new Position(3295, 3672, 0), TeleportType.NORMAL);
//        }
        if (command[0].equalsIgnoreCase("autobank") && player.getMembershipTier().isMember()) {
            player.sendMessage("Auto banking Membership Feature set to: " + !player.getAutoBank());
            player.setAutoBank(!player.getAutoBank());
        }

        if (command[0].equalsIgnoreCase("autodissolve") && player.getMembershipTier().isMember()) {
            player.sendMessage("Auto dissolving Membership Feature set to: " + !player.getAutoDissolve());
            player.setAutoDissolve(!player.getAutoDissolve());
        }

        if (command[0].equalsIgnoreCase("collectionlog")) {
            player.getCollectionLogManager().openInterface();
        }
        if (command[0].equalsIgnoreCase("enchanter")) {
            player.getUpgrade().display();
        }

        if (command[0].equalsIgnoreCase("cr")) {
            NpcRequirements.printUnlockedNpcs(player);
        }

        /*if (command[0].equalsIgnoreCase("claimstreaks")) {
            PersonalCampaignHandler.attemptGive(player, 0);
            LifetimeStreakHandler.attemptGive(player, 0);
        }*/

        if (command[0].equalsIgnoreCase("upgrader")) {
            player.getTierUpgrading().display();
        }

        if (command[0].equalsIgnoreCase("lb") || command[0].equalsIgnoreCase("leaderboard") || command[0].equalsIgnoreCase("leaderboards") ) {
            player.getLeaderboardManager().openInterface();
        }

        if (command[0].equalsIgnoreCase("streak")) {
            player.getStreak().openInterface();
        }

        if (command[0].equalsIgnoreCase("bpass")) {
            player.getBattlePass().displayPage();
        }

        if (command[0].equalsIgnoreCase("killmessage")) {
            if (command.length < 2) {
                player.msgRed("Usage: ::killmessage amount");
                return;
            }

            String amountStr = command[1];
            if (amountStr.matches("\\d+")) {
                int amount = Integer.parseInt(amountStr);
                player.setKillmessageamount(amount);
                player.msgPurp("Kill message frequency set to every <shad=0>@red@" + player.getKillmessageamount() + " <shad=0><col=AF70C3>Kills.");
            } else {
                player.msgRed("Usage: ::killmessage amount");
            }
        }

        if (command[0].equalsIgnoreCase("event")) {
            if (GameSettings.STREAMER_EVENT == false && GameSettings.FRENZY_EVENT == false){
                player.msgRed("No Events are currently Active!");
                return;
            }
            if (player.getLocation() != null && player.getLocation() == Location.FRENZYBOSS
                    || player.getLocation() != null && player.getLocation() == Location.FRENZYZONE2
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_1
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_2) {
                player.msgRed("You cannot use that command here!");
                return;
            }

            player.msgFancyPurp("Welcome to the Stream Zone!");
            Position pos = new Position(2536, 2773);
            TeleportHandler.teleportPlayer(player, pos, TeleportType.ANCIENT);
        }

        if (command[0].equalsIgnoreCase("pouchmsg")) {
            player.pouchMessagesEnabled = !player.pouchMessagesEnabled;
            player.msgRed("Essence Pouch Messages set to: " + player.pouchMessagesEnabled);
        }

        if (command[0].equalsIgnoreCase("refund")) {
            if (player.getPointsHandler().getRefundPoints() > 0){
                Shop.ShopManager.getShops().get(Shop.REFUND_SHOP).open(player);
                player.sendMessage("<col=0><shad=6C1894>You have @red@" + player.getPointsHandler().getRefundPoints() + " <col=0><shad=6C1894>Refund Points.");
            }
        }



        if (command[0].equalsIgnoreCase("archon") || command[0].equalsIgnoreCase("arc")) {
            if(player.getAmountDonated() >= DonatorRanks.ARCHON_AMOUNT) {
                TeleportHandler.teleportPlayer(player, new Position(2399, 3554, 0), TeleportType.ANCIENT);
                player.msgRed("Welcome to the Archon Donator Zone.");
                return;
            }
            player.msgRed("Reach Archon Rank($500) to access this zone.");
        }
        if (command[0].equalsIgnoreCase("celestial") || command[0].equalsIgnoreCase("cele")) {
            if(player.getAmountDonated() >= DonatorRanks.CELESTIAL_AMOUNT) {
                TeleportHandler.teleportPlayer(player, new Position(2527, 3554, 0), TeleportType.ANCIENT);
                player.msgRed("Welcome to the Celestial Donator Zone.");
                return;
            }
            player.msgRed("Reach Celestial Rank($1000) to access this zone.");
        }
        if (command[0].equalsIgnoreCase("ascendant") || command[0].equalsIgnoreCase("asc")) {
            if(player.getAmountDonated() >= DonatorRanks.ASCENDANT_AMOUNT) {
                TeleportHandler.teleportPlayer(player, new Position(2655, 3554, 0), TeleportType.ANCIENT);
                player.msgRed("Welcome to the Ascendant Donator Zone.");
                return;
            }
            player.msgRed("Reach Ascendant Rank($1500) to access this zone.");
        }
        if (command[0].equalsIgnoreCase("gladiator") || command[0].equalsIgnoreCase("glad")) {
            if(player.getAmountDonated() >= DonatorRanks.GLADIATOR_AMOUNT) {
                TeleportHandler.teleportPlayer(player, new Position(2783, 3544, 0), TeleportType.ANCIENT);
                player.msgRed("Welcome to the Gladiator Donator Zone.");
                return;
            }
            player.msgRed("Reach Gladiator Rank($2000) to access this zone.");
        }

        if (command[0].equalsIgnoreCase("critmsg")) {
            player.critMessagesEnabled = !player.critMessagesEnabled;
            player.msgRed("Critical Strike Messages set to: " + player.critMessagesEnabled);
        }

        if (command[0].equalsIgnoreCase("globaldrops") || command[0].equalsIgnoreCase("dropmsg")) {
            player.globalDropMessagesEnabled = !player.globalDropMessagesEnabled;
            player.msgRed("Global Drop Messages Messages set to: " + player.globalDropMessagesEnabled);
        }

        if (command[0].equalsIgnoreCase("vote")) {
            player.getPacketSender().sendString(1, GameSettings.VoteUrl);// "http://Ruseps.com/vote/?user="+player.getUsername());
            player.msgPurp("After you vote, type ::voted to claim your votes!");
        }

        if (command[0].equalsIgnoreCase("globals")) {
            if (ArchonBossEvent.isAlive){
                player.sendMessage("Archonic C'thulu is alive" );
            }
            if (!ArchonBossEvent.isAlive){
                player.msgRed("Archonic C'thulu spawns in - " + ArchonBossEvent.timeLeft());
            }

            if (CelestialBossEvent.isAlive){
                player.sendMessage("Celestial Orc is alive" );
            }
            if (!CelestialBossEvent.isAlive){
                player.msgRed("Celestial Orc spawns in - " + CelestialBossEvent.timeLeft());
            }

            if (AscendentBossEvent.isAlive){
                player.sendMessage("Ascendant Berserker is alive" );
            }
            if (!AscendentBossEvent.isAlive){
                player.msgRed("Ascendant Berserker spawns in - " + AscendentBossEvent.timeLeft());
            }

            if (GladiatorBossEvent.isAlive){
                player.sendMessage("Gladiator is alive" );
            }
            if (!GladiatorBossEvent.isAlive){
                player.msgRed("Gladiator spawns in - " + GladiatorBossEvent.timeLeft());
            }
        }

        if (wholeCommand.equalsIgnoreCase("discord")) {
            player.getPacketSender().sendString(1, GameSettings.DiscordUrl);
            player.getPacketSender().sendMessage("Opening our Discord Server");
        }



        if (command[0].equalsIgnoreCase("items") || command[0].equalsIgnoreCase("bis")){
            player.getBis().open();
        }

        if (command[0].equalsIgnoreCase("maps")) {
            player.getPacketSender().sendInterface(57350);
        }

        if (command[0].equalsIgnoreCase("teleport") || command[0].equalsIgnoreCase("zones")) {
            player.getTPInterface().display();
        }

        if (command[0].equalsIgnoreCase("home")) {
            Position pos = new Position(3167 +- Misc.random(0,2), 3544+- Misc.random(0,2), 0);
            TeleportHandler.teleportPlayer(player, pos, TeleportType.ANCIENT);
            player.msgPurp("Welcome Home!");
        }

        if (command[0].equalsIgnoreCase("home")) {
            Position pos = new Position(3167 +- Misc.random(0,2), 3544+- Misc.random(0,2), 0);
            TeleportHandler.teleportPlayer(player, pos, TeleportType.ANCIENT);
            player.msgPurp("Welcome Home!");
        }


        if (command[0].equalsIgnoreCase("pray") ||  command[0].equalsIgnoreCase("prayer")) {
            Position pos = new Position(3172, 3560);
            TeleportHandler.teleportPlayer(player, pos, TeleportType.ANCIENT);
        }

        if (command[0].equalsIgnoreCase("mine") || command[0].equalsIgnoreCase("mining")) {
            TeleportHandler.teleportPlayer(player, new Position(2588, 4131, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Mine");
        }

        if (command[0].equalsIgnoreCase("afk")) {
            TeleportHandler.teleportPlayer(player, new Position(2651, 3879, 0), TeleportType.ANCIENT);
        }
     /*   if (command[0].equalsIgnoreCase("crypt")) {
            TeleportHandler.teleportPlayer(player, new Position(2650, 3990, 8), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Mine");
        }



        if (command[0].equalsIgnoreCase("crypt2")) {
            TeleportHandler.teleportPlayer(player, new Position(2650, 3990, 12), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Mine");
        }*/

        if (wholeCommand.equalsIgnoreCase("extras")) {
            if (player.isReceivedExtras()){
                player.sendMessage("<shad=0>@red@You already claimed your Starter Extras!");
                return;
            }

            player.getInventory().add(15666,1);
            player.getInventory().add(10945,10);

            player.setReceivedExtras(true);
            player.sendMessage("<shad=0>@red@Thanks for joining!!");
            return;
        }

        if (wholeCommand.equalsIgnoreCase("easter2025")) {
            if (player.isReceivedEaster()){
                player.sendMessage("<shad=0>@red@You already claimed your easter 2025 bonus!");
                return;
            }

            player.getInventory().add(995,15000);
            player.getInventory().add(2706,5);
            player.getInventory().add(716,5);

            player.setReceivedEaster(true);
            player.sendMessage("<shad=0>@red@Thanks for playing Athens!");
            return;
        }

        if (command[0].equalsIgnoreCase("progress") || command[0].equalsIgnoreCase("tasks") ) {
            if (!player.getStarterTasks().hasCompletedAll()) {
                player.getStarterTasks().openInterface();
                player.setViewing(Player.INTERFACES.STARTER_TASK);
                return;
            }
            if (!player.getMediumTasks().hasCompletedAll()) {
                player.getMediumTasks().openInterface();
                player.setViewing(Player.INTERFACES.MEDIUM_TASKS);
                return;
            }
            if (!player.getEliteTasks().hasCompletedAll()) {
                player.getEliteTasks().openInterface();
                player.setViewing(Player.INTERFACES.ELITE_TASKS);
                return;
            }
        }

        if (command[0].equalsIgnoreCase("cosmetic")) {
            CosmeticToggleInterface.openInterface(player);
        }

        if (command[0].equalsIgnoreCase("vb") || command[0].equalsIgnoreCase("voteboss")) {
            TeleportHandler.teleportPlayer(player, new Position(2849, 5081, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Vote Boss!");
        }


        if (command[0].equalsIgnoreCase("fall") || command[0].equalsIgnoreCase("fallen")) {
            TeleportHandler.teleportPlayer(player, new Position(2274, 3162, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Fallen Arena!");
        }
        if (command[0].equalsIgnoreCase("forest")) {
            TeleportHandler.teleportPlayer(player, new Position(2694, 3838, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Enchanted Forest!");
        }

        if (command[0].equalsIgnoreCase("votetime")) {
            player.msgRed(String.valueOf(player.lastVoteTime));

        }

        if (command[0].equalsIgnoreCase("champ") || command[0].equalsIgnoreCase("emerald")) {
            TeleportHandler.teleportPlayer(player, new Position(2719, 5722, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Emerald Champion!");
        }

        if (command[0].equalsIgnoreCase("demon") || command[0].equalsIgnoreCase("skele")) {
            TeleportHandler.teleportPlayer(player, new Position(1823, 4505, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Skeletal Demon!");
        }

        if (command[0].equalsIgnoreCase("beast")) {
            TeleportHandler.teleportPlayer(player, new Position(1698, 4248, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Slayer Beast!");
        }

        if (command[0].equalsIgnoreCase("coe")) {
            TeleportHandler.teleportPlayer(player, new Position(2916, 2593,0), TeleportType.ANCIENT);
        }

        if (command[0].equalsIgnoreCase("raids")) {
            TeleportHandler.teleportPlayer(player, new Position(2974, 3879,0), TeleportType.ANCIENT);
        }

        if (command[0].equalsIgnoreCase("sb")) {
            TeleportHandler.teleportPlayer(player, new Position(3047, 3865,0), TeleportType.ANCIENT);
        }



        if (command[0].equalsIgnoreCase("db") || command[0].equalsIgnoreCase("donoboss")) {
            TeleportHandler.teleportPlayer(player, new Position(3615, 3353, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Dono Boss!");
        }

        if (command[0].equalsIgnoreCase("rift") || command[0].equalsIgnoreCase("wb")) {
            TeleportHandler.teleportPlayer(player, new Position(1505, 4950, 1), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Rift Site");
        }

        if (command[0].equalsIgnoreCase("crift")) {
            TeleportHandler.teleportPlayer(player, new Position(3040, 2966, 1), TeleportType.ANCIENT);
            player.msgPurp("Welcome to the Corrupt Rift Site");
        }

        if (command[0].equalsIgnoreCase("shops") || command[0].equalsIgnoreCase("shop")) {
            TeleportHandler.teleportPlayer(player, new Position(3167, 3552, 0), TeleportType.ANCIENT);
            player.msgPurp("Welcome To Our Shops!");
        }

        if (command[0].equalsIgnoreCase("chest") || command[0].equalsIgnoreCase("chests")) {
            TeleportHandler.teleportPlayer(player, new Position(2983, 3241, 2), TeleportType.ANCIENT);
            player.msgPurp("Good Luck!");
        }

        if (wholeCommand.equalsIgnoreCase("changepass") || wholeCommand.equalsIgnoreCase("changepassword")) {
            player.setInputHandling(new ChangePassword());
            player.getPacketSender().sendEnterInputPrompt("Enter a new password:");
        }

        if (command[0].startsWith("voted") || command[0].startsWith("claimvote")) {
            Voted(player);
        }
        if (command[0].startsWith("claim") || command[0].startsWith("donated") ) {
            claimDonation(player);
        }

        if (command[0].equalsIgnoreCase("setpin")) {
            if (!player.getHasPin()) {

                player.setInputHandling(new SetPinPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the pin that you want to set$pin");
            }
        }
        if (command[0].equalsIgnoreCase("ref")) {
            player.setInputHandling(new EnterReferral());
            player.getPacketSender().sendEnterInputPrompt("Enter the referral code you received!");

        }
        if (command[0].equalsIgnoreCase("enterpin")) {
            if (!player.getHasPin()) {
                player.setInputHandling(new SetPinPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the pin that you want to set$pin");
            }
        }

        if (command[0].equalsIgnoreCase("leaveduo")) {
            if (player.getSlayer().getDuoPartner() != null) {
                Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
            } else {
                player.msgRed("You do not have a duo partner!");
            }
        }

        if (command[0].equalsIgnoreCase("answer")) {
            String answer = wholeCommand.substring(7);
            TriviaSystem.answer(player, answer);
        }

        if (wholeCommand.equalsIgnoreCase("droprate") || wholeCommand.equalsIgnoreCase("dr") || wholeCommand.equalsIgnoreCase("mydr")) {
            player.msgPurp("Your Drop Rate is @blu@" + CustomDropUtils.drBonus(player, player.getSlayer().getSlayerTask().getNpcId()) + "@bla@%.");
        }

        if (wholeCommand.equalsIgnoreCase("donate") || wholeCommand.equalsIgnoreCase("store")) {
            if (player.getGameMode() != GameMode.EXILED) {
                player.getPacketSender().sendString(1, "http://refer-ps.teamgames.io/category/4594/page/1");
                player.msgPurp("Opening the Store");
            }
        }

        if (command[0].equalsIgnoreCase("dmgboost")) {
            player.msgRed("Current Perm Dmg: " + player.getDmgBoost());
        }

        if (command[0].equalsIgnoreCase("drboost")) {
            player.msgRed("Current Perm Droprate: " + player.getDrBoost());
        }

        if (command[0].equalsIgnoreCase("upgboost")) {
            player.msgRed("Current Perm Upg Boost: " + player.getUpgBoost());
        }

        if (command[0].equalsIgnoreCase("empty")) {
            player.setDialogueActionId(523);
            DialogueManager.start(player, 523);
        }

        if (command[0].equalsIgnoreCase("rewards") || command[0].equalsIgnoreCase("loot")) {
            PossibleLootInterface.openInterface(player, PossibleLootInterface.LootData.values()[0]);
        }

        if (command[0].equalsIgnoreCase("achievements")) {
            player.getAchievements().refreshInterface(player.getAchievements().currentInterface);
            player.getPacketSender().sendConfig(6000, 3);
        }

        if (command[0].equalsIgnoreCase("junk")) {
            Shop.ShopManager.getShops().get(Shop.TRASH_STORE).open(player);

        }

        if (command[0].equalsIgnoreCase("drops")) {
            player.msgPurp("Opening drops interface...");
            DropsInterface.open(player);
        }

        if (wholeCommand.equalsIgnoreCase("commands")) {
            for (int i = 8145; i < 8196; i++)
                player.getPacketSender().sendString(i, "");
            player.getPacketSender().sendInterface(8134);
            player.getPacketSender().sendString(8136, "Close window");
            player.getPacketSender().sendString(8144, "Commands");
            player.getPacketSender().sendString(8145, "");
            int index = 8147;
            String color = "@bla@";
            String color1 = "@bla@";

            player.getPacketSender().sendString(index++, color1 + "Travel Commands:");
            player.getPacketSender().sendString(index++, color + "::home (ctrl+h) - Teleports you home.");
            player.getPacketSender().sendString(index++, color + "::chests - Teleports you to our Chests");
            player.getPacketSender().sendString(index++, color + "::shops - Teleports you to our Shops.");
            player.getPacketSender().sendString(index++, color + "::pray - Teleports you to the prayer altar.");
            player.getPacketSender().sendString(index++, color + "::mine/mining - Teleports you to the Salt mines.");
            player.getPacketSender().sendString(index++, color + "::rift - Teleports you to the Void Rift.");
            player.getPacketSender().sendString(index++, color + "::crift - Teleports you to the Corrupt Rift.");
            player.getPacketSender().sendString(index++, color + "::forest - Teleports you to the Enchanted Forest.");
            player.getPacketSender().sendString(index++, color + "::coe - Teleports you to to Circle of Elements.");
            player.getPacketSender().sendString(index++, color + "::toa - Teleports you to the Tower of Ascension.");
            player.getPacketSender().sendString(index++, color + "::sb - Teleports you to Soulbane.");

            player.getPacketSender().sendString(index++, color + "");
            player.getPacketSender().sendString(index++, color1 + "Interface Commands:");
            player.getPacketSender().sendString(index++, color + "::bank (ctrl+b) - Opens your bank");
            player.getPacketSender().sendString(index++, color + "::enchanter (ctrl+e) - Opens the Enchantment Table");
            player.getPacketSender().sendString(index++, color + "::leaderboard (ctrl+l) - Opens the Leaderboard");
            player.getPacketSender().sendString(index++, color + "::collectionlog (ctrl+c) - Opens the Collection Log");
            player.getPacketSender().sendString(index++, color + "::upgrade (ctrl+u) - Opens the Upgrader");
            player.getPacketSender().sendString(index++, color + "::achievements (ctrl+a) - Check and Claim Achievements.");
            player.getPacketSender().sendString(index++, color + "::loot - View Mystery Box + Key Rewards.");
            player.getPacketSender().sendString(index++, color + "::drops - View Npcs Drop Table.");
            player.getPacketSender().sendString(index++, color + "::pos (ctrl+p) - Opens Trading Post");
            player.getPacketSender().sendString(index++, color + "");
            player.getPacketSender().sendString(index++, color1 + "Other Commands:");
            player.getPacketSender().sendString(index++, color + "::dr - Displays your current Droprate.");
            player.getPacketSender().sendString(index++, color + "::killmessage amount - Change the frequencey of kill messages.");
            player.getPacketSender().sendString(index++, color + "::critmsg  - Toggles Critical strike messages.");
            player.getPacketSender().sendString(index++, color + "::pouchmsg  - Toggles Essence Pouch messages.");
            player.getPacketSender().sendString(index++, color + "::globaldrops  - Toggles Drop Messages messages.");
            player.getPacketSender().sendString(index++, color + "::changepass - Change your password.");
            player.getPacketSender().sendString(index++, color + "::bank - Opens your bank ($25 total claimed required)");
            player.getPacketSender().sendString(index++, color + "::rules - Opens our rules");
            player.getPacketSender().sendString(index++, color + "::discord - Opens The Athens Discord");
            player.getPacketSender().sendString(index++, color + "::vote - Opens our Voting Page.");
            player.getPacketSender().sendString(index++, color + "::voted -  Claim your votes");
            player.getPacketSender().sendString(index++, color + "::store - Opens The Athens Store ");
            player.getPacketSender().sendString(index++, color + "::claim - Claim your purchase");
            player.getPacketSender().sendString(index++, color + "::yell - Send a yell message");
            player.getPacketSender().sendString(index++, color + "");
        }

        /*if (wholeCommand.equalsIgnoreCase("didy")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=-5199kiq1ZU");
            player.msgFancyPurp("Opening Didy's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("ents")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=e2HH5Ubj-Jc");
            player.msgFancyPurp("Opening Ent's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("Lano")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=pS3NCnjePeM");
            player.msgFancyPurp("Opening Lano's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("biggie")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=IAm1VRpGGY4&ab_channel=BigzyRSPS");
            player.msgFancyPurp("Opening Bigyz's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("rspsguy")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=oQmbykC4Tjw&feature=yout");
            player.msgFancyPurp("Opening Rspsguy's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("wr3ckedyou")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=D8_Es20P9PY&ab_channel=wr3ckedyouRSPS");
            player.msgFancyPurp("Opening Wr3ckedYou's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("fpkmerk")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=68b3ZvLxPk8&ab_channel=FPKMerk");
            player.msgFancyPurp("Opening fpkmerk's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("walkchaos")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=73u5ae9Nn44&ab_channel=Walkchaos");
            player.msgFancyPurp("Opening walkchaos's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("mickey")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=nPuUHjQwqso");
            player.msgFancyPurp("Opening Mickey's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("ammoguide")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=5WbmhveSlhA");
            player.msgFancyPurp("Opening Mickey's youtube channel...");
            return; // lol
        }
        if (wholeCommand.equalsIgnoreCase("charmie")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=Qe9LvDwm67s&ab_channel=CharmieRS");
            player.msgFancyPurp("Opening Charmie's youtube channel...");
            return; // lol
        }*/


        https://www.youtube.com/watch?v=S8gDhEXDxB4

        if (wholeCommand.equalsIgnoreCase("phantomrs")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=S8gDhEXDxB4");
            player.msgFancyPurp("Opening Phantomrs's youtube channel...");
            return; // lol
        }

        if (wholeCommand.equalsIgnoreCase("fpkmerk")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=bz9IHW2pqZE");
            player.msgFancyPurp("Opening Fpkmerk's youtube channel...");
            return; // lol
        }

        if (wholeCommand.equalsIgnoreCase("walkchaos")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=0uU1x3G0sgc");
            player.msgFancyPurp("Opening walkchaos's youtube channel...");
            return; // lol
        }

        if (wholeCommand.equalsIgnoreCase("gibbo")) {
            player.getPacketSender().sendString(1, "https://www.youtube.com/watch?v=B6jDy68HIaA");
            player.msgFancyPurp("Opening Gibbo's youtube channel...");
            return; // lol
        }

        if (wholeCommand.toLowerCase().startsWith("yell")) {
            if (PlayerPunishment.muted(player.getUsername()) || PlayerPunishment.IPMuted(player.getHostAddress())) {
                player.getPacketSender().sendMessage("You are muted and cannot yell.");
                return;
            }
            if (player.getAmountDonated() < DonatorRanks.ADEPT_AMOUNT && player.getRights() != PlayerRights.OWNER &&
                    player.getRights() != PlayerRights.ADMINISTRATOR && player.getRights() != PlayerRights.MODERATOR &&
                    player.getRights() != PlayerRights.SUPPORT && player.getRights() != PlayerRights.MANAGER_2 &&
                    player.getRights() != PlayerRights.MANAGER && player.getRights() != PlayerRights.YOUTUBER &&
                    player.getRights() != PlayerRights.CO_OWNER) {
                player.getPacketSender().sendMessage("You need to be a Donator to yell.");
                return;
            }
            int delay = player.getRights().isStaff() ? 0 : player.getRights().getYellDelay();
            if (!player.getLastYell().elapsed((delay * 200L))) {
                player.getPacketSender().sendMessage("You must wait at least " + delay + " seconds between every yell-message you send.");
                return;
            }
            String yellMessage = Misc.capitalizeJustFirst(wholeCommand.substring(5));

            String[] invalid = {"<img="};
            for (String s : invalid) {
                if (yellMessage.contains(s)) {
                    player.getPacketSender().sendMessage("Your message contains invalid characters.");
                    return;
                }
            }

            World.sendYellMessage("" + player.getRights().getYellPrefix()
                    + "<img=" + Misc.spriteForRights(player.getRights().ordinal())
                    + "><col=" + player.getRights().getYellPrefix() +
                    " [" + Misc.ucFirst(player.getRights().name().replaceAll("_", " ")) + "]<shad=0><col=" + player.getYellHex() + "> " + player.getUsername() +
                    ": " + yellMessage);

            player.getLastYell().reset();
        }

    }

    private static void youtuberCommands(Player player, String[] command, String wholeCommand) {

    }

    private static void managerCommands(Player player, String[] command, String wholeCommand) {





        if (command[0].equalsIgnoreCase("addrefund")) {
            int amount = Integer.parseInt(command[1]);
            String plrName = wholeCommand.substring(command[0].length() + command[1].length() + 2);
            Player target = World.getPlayerByName(plrName);
            if (target == null) {
                player.getPacketSender().sendMessage(plrName + " must be online to give them stuff!");
            } else {
                target.getPointsHandler().setRefundPoints(amount,true);
                target.msgRed("You have received " + amount + " refund points from @yel@" + player.getUsername());
            }
        }

        if (command[0].equalsIgnoreCase("givedp")) {
            int amount = Integer.parseInt(command[1]);
            String plrName = wholeCommand.substring(command[0].length() + command[1].length() + 2);
            Player target = World.getPlayerByName(plrName);
            if (target == null) {
                player.getPacketSender().sendMessage(plrName + " must be online to give them stuff!");
            } else {
                target.getPointsHandler().setDonatorPoints(amount,true);
                target.msgRed("You have received " + amount + " donation points from @yel@" + player.getUsername());
            }
        }
        if (command[0].equalsIgnoreCase("givestore")) {
            int amount = Integer.parseInt(command[1]);
            String plrName = wholeCommand.substring(command[0].length() + command[1].length() + 2);
            Player target = World.getPlayerByName(plrName);
            if (target == null) {
                player.getPacketSender().sendMessage(plrName + " must be online to give them stuff!");
            } else {
                target.setTotalDonatedThroughStore(target.getTotalDonatedThroughStore() + amount);
                target.msgRed("You have received " + amount + " added to your total store amount from @yel@" + player.getUsername());
            }
        }

        if (command[0].equalsIgnoreCase("editchest")) {
            player.getIslandInterface().open();
        }

        if (command[0].equalsIgnoreCase("tvoteboss")) {
            player.getTransmog().transmogify(Transformations.VOTE_BOSS);
        }
        if (command[0].equalsIgnoreCase("tdonoboss")) {
            player.getTransmog().transmogify(Transformations.DONO_BOSS);
        }

        if (command[0].equalsIgnoreCase("tnone")) {
            player.getTransmog().returnToNormal();
        }

        if (command[0].equalsIgnoreCase("master")) {
            for (Skill skill : Skill.values()) {
                int level = SkillManager.getMaxAchievingLevel(skill);
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                        SkillManager.getExperienceForLevel(level == 120 ? 120 : 99));
            }
            player.getPacketSender().sendMessage("You are now a master of all skills.");
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }


        if (command[0].equalsIgnoreCase("gearup")) {
            int[][] gear = new int[][]{{Equipment.HEAD_SLOT, 23028},
                    {Equipment.AMULET_SLOT, 17124}, {Equipment.WEAPON_SLOT, 2087}, {Equipment.BODY_SLOT, 3021},
                    {Equipment.AURA_SLOT, 12423}, {Equipment.LEG_SLOT, 3022}, {Equipment.HANDS_SLOT, 3023},
                    {Equipment.FEET_SLOT, 3024}, {Equipment.RING_SLOT, 10724}, {Equipment.ASCEND, 12839}};
            for (int i = 0; i < gear.length; i++) {
                int slot = gear[i][0], id = gear[i][1];
                player.getEquipment().setItem(slot, new Item(id, 1));
            }
            BonusManager.update(player);
            WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
            WeaponAnimations.update(player);
            player.getEquipment().refreshItems();
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }

        if (command[0].equalsIgnoreCase("npc")) {
            int id = Integer.parseInt(command[1]);
            DiscordManager.sendMessage("[NPC SPAWN] " + player.getUsername() + " has just spawned npc " + id + " at [x = " + player.getPosition().getX() + ", y = " + player.getPosition().getY() + ", z= " + player.getPosition().getZ() + "].", Channels.ADMIN_COMMANDS);
            NPC npc = new NPC(id, new Position(player.getPosition().getX(), player.getPosition().getY(),
                    player.getPosition().getZ()));
            World.register(npc);
        }

        if (command[0].equalsIgnoreCase("giveislands")) {
            try {
                int id = Integer.parseInt(command[1]);
                String plrName = wholeCommand
                        .substring(command[0].length() + command[1].length() + 2);
                Player target = World.getPlayerByName(plrName);
                if (target == null) {
                    player.getPacketSender().sendMessage(plrName + " must be online to give them stuff!");
                } else {
                    target.getIsland().constructIsland(id);
                }
                //iyOevAa1WU43PaTklVhIMbPiP2V2JtR6oCltZdEuanze0CeMrO0rgGEflskWodbHLMI2puET
            } catch(Exception e) {
                player.sendMessage("use as ::giveislands amount playerName");
                e.printStackTrace();
            }
        }

        // Activates the Global Double KoL Tickets Task
        if (command[0].equalsIgnoreCase("doublenecro")) {
            if (!GameSettings.DOUBLE_NECRO_POINTS) {
                TaskManager.submit(new GlobalDoubleKoLTask());
            } else {
                player.sendMessage("Task is already running.");
            }
        }

        if (command[0].equalsIgnoreCase("streameventon")) {
            GameSettings.STREAMER_EVENT = true;
            DiscordManager.sendMessage("[STREAM ZONE] " + player.getUsername() + " has just enabled frenzy.", Channels.ADMIN_COMMANDS);
            World.sendMessage("<shad=0><col=AF70C3>[EVENT] STREAM ZONE HAS BEEN OPENED ::EVENT! by " + player.getUsername());

        }
        if (command[0].equalsIgnoreCase("frenzyon")) {
            GameSettings.FRENZY_EVENT = true;
            DiscordManager.sendMessage("[FRENZY] " + player.getUsername() + " has just enabled frenzy.", Channels.ADMIN_COMMANDS);
            World.sendMessage("<shad=0><col=AF70C3>[EVENT] FRENZY HAS BEEN OPENED ::EVENT! by " + player.getUsername());
        }

        if (command[0].equalsIgnoreCase("streameventoff")) {
            GameSettings.STREAMER_EVENT = false;
            DiscordManager.sendMessage("[STREAM ZONE] " + player.getUsername() + " has just closed the Stream Zone.", Channels.ADMIN_COMMANDS);
            World.sendMessage("<shad=0><col=AF70C3>[EVENT] STREAM ZONE HAS BEEN CLOSED by " + player.getUsername());
        }
        if (command[0].equalsIgnoreCase("frenzyoff")) {
            GameSettings.FRENZY_EVENT = false;
            DiscordManager.sendMessage("[FRENZY] " + player.getUsername() + " has just closed Frenzy.", Channels.ADMIN_COMMANDS);
            World.sendMessage("<shad=0><col=AF70C3>[EVENT] FRENZY HAS BEEN CLOSED by " + player.getUsername());
        }

        if (command[0].equalsIgnoreCase("sneaky")) {

            if (player.getLocation() != null && player.getLocation() == Location.FRENZYBOSS
                    || player.getLocation() != null && player.getLocation() == Location.FRENZYZONE2
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_1
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_2) {
                player.msgRed("You cannot use that command here!");
                return;
            }

            player.msgFancyPurp("Welcome to the Stream Zone!");
            Position pos = new Position(2536, 2773);
            TeleportHandler.teleportPlayer(player, pos, TeleportType.ANCIENT);
        }

        if (command[0].equalsIgnoreCase("tele")) {
            final RaidsParty party65235 = player.getMinigameAttributes().getRaidsAttributes().getParty();
            if (party65235 != null) {
                player.sendMessage("Please leave your party before leaving the lobby!");
                return;
            }
            int x = Integer.parseInt(command[1]), y = Integer.parseInt(command[2]);
            int z = player.getPosition().getZ();
            if (command.length > 3)
                z = Integer.parseInt(command[3]);
            Position position = new Position(x, y, z);
            player.moveTo(position);
            player.getPacketSender().sendMessage("Teleporting to " + position);
        }

        if (command[0].equalsIgnoreCase("permmute")) {
            String player2 = wholeCommand.substring(command[0].length() + 1);
            if (player2 == null) {
                player.getPacketSender().sendMessage("Player " + player2 + " does not exist.");
                return;
            } else {
                if (PlayerPunishment.muted(player2)) {
                    player.getPacketSender().sendMessage("Player " + player2 + " already has an active mute.");
                    return;
                }
                PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " just perm muted " + player2 + "!");
                PlayerPunishment.mute(player2);
                player.getPacketSender()
                        .sendMessage("Player " + player2 + " was successfully muted. Command logs written.");
                World.sendStaffMessage("<col=FF0066><img=2> [PUNISHMENTS]<col=6600FF> " + player.getUsername()
                        + " just perm muted " + player2);
                Player plr = World.getPlayerByName(player2);
                if (plr != null) {
                    plr.getPacketSender().sendMessage("You have been muted by " + player.getUsername() + ".");
                }
            }
        }

        if (command[0].equalsIgnoreCase("giveyt")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            StaffList.logout(player2);
            player2.getPacketSender().sendMessage("Promoted to Youtuber.");
            player.getPacketSender().sendMessage("Promoted to Youtuber.");
            player2.setRights(PlayerRights.YOUTUBER);
            player2.getPacketSender().sendRights();
            PlayerPanel.refreshPanel(player2);
            StaffList.login(player2);
        }

        if (command[0].equalsIgnoreCase("giveheadmg")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            StaffList.logout(player2);
            player2.getPacketSender().sendMessage("Promoted to Co Owner.");
            player.getPacketSender().sendMessage("Promoted to Co Owner.");
            player2.setRights(PlayerRights.CO_OWNER);
            player2.getPacketSender().sendRights();
            PlayerPanel.refreshPanel(player2);
            StaffList.login(player2);
        }

        if (command[0].equalsIgnoreCase("givemaster")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player2.getPacketSender().sendMessage("You are now Master Game Mode.");
            player.getPacketSender().sendMessage("Gave Master Game Mode..");
            player2.setXpMode(XpMode.MASTER);
            PlayerPanel.refreshPanel(player2);
        }

        if (command[0].equalsIgnoreCase("giveelite")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player2.getPacketSender().sendMessage("You are now Elite Game Mode.");
            player.getPacketSender().sendMessage("Gave Elite Game Mode..");
            player2.setXpMode(XpMode.ELITE);
            PlayerPanel.refreshPanel(player2);
        }

        if (command[0].equalsIgnoreCase("givemedium")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player2.getPacketSender().sendMessage("You are now Medium Game Mode.");
            player.getPacketSender().sendMessage("Gave Medium Game Mode..");
            player2.setXpMode(XpMode.MEDIUM);
            PlayerPanel.refreshPanel(player2);
        }

        if (command[0].equalsIgnoreCase("giveeasy")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player2.getPacketSender().sendMessage("You are now Easy Game Mode.");
            player.getPacketSender().sendMessage("Gave Easy Game Mode..");
            player2.setXpMode(XpMode.EASY);
            PlayerPanel.refreshPanel(player2);
        }

        if (command[0].equalsIgnoreCase("givesinister")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player2.getPacketSender().sendMessage("You are now Sinister Mode.");
            player.getPacketSender().sendMessage("Gave Master Sinister Mode..");
            player2.setBoostMode(BoostMode.SINISTER);
            PlayerPanel.refreshPanel(player2);
        }

        if (command[0].equalsIgnoreCase("givedivine")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player2.getPacketSender().sendMessage("You are now Divine Mode.");
            player.getPacketSender().sendMessage("Gave Master Divine Mode..");
            player2.setBoostMode(BoostMode.DIVINE);
            PlayerPanel.refreshPanel(player2);
        }

        if (command[0].equalsIgnoreCase("givemg")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            StaffList.logout(player2);
            player2.getPacketSender().sendMessage("Promoted to Manager.");
            player.getPacketSender().sendMessage("Promoted to Manager.");
            player2.setRights(PlayerRights.MANAGER);
            player2.getPacketSender().sendRights();
            PlayerPanel.refreshPanel(player2);
            StaffList.login(player2);
        }

        if (command[0].equalsIgnoreCase("givemod")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            StaffList.logout(player2);
            player2.getPacketSender().sendMessage("Promoted to moderator.");
            player.getPacketSender().sendMessage("Promoted to moderator.");
            player2.setRights(PlayerRights.MODERATOR);
            player2.getPacketSender().sendRights();
            PlayerPanel.refreshPanel(player2);
            StaffList.login(player2);
        }
        if (command[0].equalsIgnoreCase("giveadmin")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            StaffList.logout(player2);
            player2.getPacketSender().sendMessage("Promoted to Admin.");
            player.getPacketSender().sendMessage("Promoted to Admin.");
            player2.setRights(PlayerRights.ADMINISTRATOR);
            player2.getPacketSender().sendRights();
            PlayerPanel.refreshPanel(player2);
            StaffList.login(player2);
        }
        if (command[0].equalsIgnoreCase("givesupport")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            StaffList.logout(player2);
            player2.getPacketSender().sendMessage("Promoted to support.");
            player.getPacketSender().sendMessage("Promoted to support.");
            player2.setRights(PlayerRights.SUPPORT);
            player2.getPacketSender().sendRights();
            PlayerPanel.refreshPanel(player2);
            StaffList.login(player2);
        }

        if (command[0].equalsIgnoreCase("tempmute")) {
            String targ = wholeCommand.substring(command[0].length() + 1);
            Player target = World.getPlayerByName(targ);
            if (target == null) {
                player.getPacketSender().sendMessage("Could not find player \"" + targ + "\".");
                return;
            }
            if (PlayerPunishment.muted(targ)) {
                player.getPacketSender().sendMessage(targ + " is already muted.");
                return;
            }
            PlayerPunishment.tempMute(targ);

        }

        if (command[0].equalsIgnoreCase("find")) {
            String name = wholeCommand.substring(5).toLowerCase().replaceAll("_", " ");
            player.getPacketSender().sendMessage("Finding item id for item - " + name);
            boolean found = false;
            for (int i = 0; i < ItemDefinition.getMaxAmountOfItems(); i++) {
                if (ItemDefinition.forId(i).getName().toLowerCase().contains(name)) {
                    player.getPacketSender().sendMessage("<shad=0>["
                            + ItemDefinition.forId(i).getName().toLowerCase() + "<shad=0>] - id: " + i);
                    found = true;
                }
            }
            if (!found) {
                player.getPacketSender().sendMessage("No item with name [" + name + "] has been found!");
            }
        } else if (command[0].equalsIgnoreCase("id")) {
            String name = wholeCommand.substring(3).toLowerCase().replaceAll("_", " ");
            boolean found = false;
            for (int i = ItemDefinition.getMaxAmountOfItems() - 1; i > 0; i--) {
                if (ItemDefinition.forId(i).getName().toLowerCase().contains(name)) {
                    player.getPacketSender().sendMessage("[" + ItemDefinition.forId(i).getName().toLowerCase() + "] - id: " + i);
                    found = true;
                }
            }
            if (!found) {
                player.getPacketSender().sendMessage("No item with name [" + name + "] has been found!");
            }
        }

        if (command[0].equalsIgnoreCase("master")) {
            for (Skill skill : Skill.values()) {
                int level = SkillManager.getMaxAchievingLevel(skill);
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                        SkillManager.getExperienceForLevel(level == 120 ? 120 : 99));
            }
            player.getPacketSender().sendMessage("You are now a master of all skills.");
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
        if (command[0].equalsIgnoreCase("goazg")) {
            GameSettings.globalslayertasks = 0;
        }

        if (command[0].equalsIgnoreCase("addtask")) {
            GameSettings.globalslayertasks += 10;
            if (GameSettings.globalslayertasks >= 200){
                AzgothSpawner.startAzgothEvent(player);
                GameSettings.globalslayertasks = 0;
            } else if (GameSettings.globalslayertasks  == 150) {
                World.sendMessage("<shad=6C1894>@bla@[SLAYER]<shad=6C1894>@red@ " + (200 - GameSettings.globalslayertasks)  + " left to summon Azgoth");
            } else if (GameSettings.globalslayertasks  == 100 ) {
                World.sendMessage("<shad=6C1894>@bla@[SLAYER]<shad=6C1894>@red@ " + (200  - GameSettings.globalslayertasks) + " left to summon Azgoth");
            } else if (GameSettings.globalslayertasks  == 50 ) {
                World.sendMessage("<shad=6C1894>@bla@[SLAYER]<shad=6C1894>@red@ " + (200 - GameSettings.globalslayertasks) + " left to summon Azgoth");
            } else if (GameSettings.globalslayertasks  == 20 ) {
                World.sendMessage("<shad=6C1894>@bla@[SLAYER]<shad=6C1894>@red@ " + (200 - GameSettings.globalslayertasks) + " left to summon Azgoth");
            }
        }

        if (command[0].equalsIgnoreCase("curses")) {
            player.performAnimation(new Animation(645));
            player.getPacketSender().sendMessage("You sense a surge of power flow through your body!");
            player.setPrayerbook(Prayerbook.CURSES);
            player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().CURSES.getInterfaceId());
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
        }

        if (command[0].equalsIgnoreCase("testpry")) {
            player.setPrayerMinigameMinionKills(95);

        }



        if (command[0].equalsIgnoreCase("spawn")) {
            String name = wholeCommand.substring(6).toLowerCase().replaceAll("_", " ");
            List<Integer> matchingIds = new ArrayList<>();

            for (int i = 0; i < ItemDefinition.getMaxAmountOfItems(); i++) {
                if (ItemDefinition.forId(i).getName().toLowerCase().equals(name)) {
                    matchingIds.add(i);
                }
            }

            if (matchingIds.size() == 1) {
                int itemId = matchingIds.get(0);
                player.getPacketSender().sendMessage("@bla@<shad=0>Spawned Item- " + name);
                player.getPacketSender().sendMessage("<shad=0>[" + ItemDefinition.forId(itemId).getName().toLowerCase() + "<shad=0>] - id: " + itemId);
                player.getInventory().add(new Item(itemId));
            } else if (matchingIds.size() > 1) {
                player.getPacketSender().sendMessage("@red@<shad=0>Matching IDs Found:");
                player.getPacketSender().sendMessage("@bla@<shad=0>" +Arrays.toString(matchingIds.toArray()));

            } else {
                player.getPacketSender().sendMessage("No item with name [" + name + "] has been found!");
            }
        }




        if (command[0].equalsIgnoreCase("item")) {
            int id = Integer.parseInt(command[1]);
            if (id > ItemDefinition.getMaxAmountOfItems()) {

                player.getPacketSender().sendMessage(
                        "Invalid item id entered. Max amount of items: " + ItemDefinition.getMaxAmountOfItems());
                return;
            }
            int amount = (command.length == 2 ? 1
                    : Integer.parseInt(command[2].trim().toLowerCase().replaceAll("k", "000").replaceAll("m", "000000")
                    .replaceAll("b", "000000000")));
            if (amount > Integer.MAX_VALUE) {
                amount = Integer.MAX_VALUE;
            }
            Item item = new Item(id, amount);
            player.getInventory().add(item, true);
            DiscordManager.sendMessage("```[ITEM] " + player.getUsername() + " spawned x " + amount + " " + ItemDefinition.forId(id).getName() + " (" + id +")```", Channels.ADMIN_COMMANDS);

        }
        if (command[0].equalsIgnoreCase("giveall")) {
            int id = Integer.parseInt(command[1]);
            int amount = Integer.parseInt(command[2]);
            DiscordManager.sendMessage("```[GIVEALL] " + player.getUsername() + " gave everyone x " + amount + " " + ItemDefinition.forId(id).getName() + " (" + id +")```", Channels.ADMIN_COMMANDS);
            for (Player players : World.getPlayers()) {
                if (players != null) {
                    players.getInventory().add(id,amount);
                    players.sendMessage("<col=0><shad=6C1894>You received: " + ItemDefinition.forId(id).getName() + " From @red@" + player.getUsername());
                }
            }
        }
        if (command[0].equalsIgnoreCase("editloot")) {
            player.getDoorInterface().open();
            player.sendMessage("@blu@<shad=6C1894>Editing Tables");
        }
/*        if (command[0].equalsIgnoreCase("opendono")) {
            if (!(player.getUsername().equalsIgnoreCase("Hades") || !player.getUsername().equalsIgnoreCase("Iowna") || !player.getUsername().equalsIgnoreCase("Dark Phoenix"))) {
                return;
            } else {
                World.sendMessage("<img=22>@blu@<shad=6C1894>[WORLD]<img=22><col=532096><shad=0>Picks Donation Minigame has been opened for 1 Hour!");
                DoorPick.setPicksOpen(true);
            }
        }
        if (command[0].equalsIgnoreCase("closedono")) {
            if (!(player.getUsername().equalsIgnoreCase("Hades") || !player.getUsername().equalsIgnoreCase("Iowna") || !player.getUsername().equalsIgnoreCase("Dark Phoenix"))) {
                return;
            } else {
                World.sendMessage("<img=22>@blu@<shad=6C1894>[WORLD]<img=22><col=532096><shad=0>Picks Donation Minigame has been closed!");
                DoorPick.setPicksOpen(false);
            }
        }*/

        if (command[0].equalsIgnoreCase("reloadnewbans")) {
            ConnectionHandler.reloadUUIDBans();
            ConnectionHandler.reloadMACBans();
            player.getPacketSender().sendMessage("UUID & Mac bans reloaded!");
        }
        if (command[0].equalsIgnoreCase("reloadipbans")) {
            PlayerPunishment.reloadIPBans();
            player.getPacketSender().sendMessage("IP bans reloaded!");
        }

        if (command[0].equalsIgnoreCase("donozone")) {
            TeleportHandler.teleportPlayer(player, new Position(2779, 2657, 0), TeleportType.ANCIENT);
        }
        if (command[0].equalsIgnoreCase("under")) {
            TeleportHandler.teleportPlayer(player, new Position(2656, 4699, 0), TeleportType.ANCIENT);
        }
        if (command[0].equalsIgnoreCase("chestisland")) {
            TeleportHandler.teleportPlayer(player, new Position(3358, 3031, 0), TeleportType.ANCIENT);
            player.getPacketSender().sendMessage("<col=3E0957><shad=6C1894>Good Luck... Choose Wisely!");
        }

        if (command[0].equalsIgnoreCase("eb")) {
            player.getBank(0).clear();
            player.getBank(1).clear();

        }


        if (command[0].equalsIgnoreCase("checkequip") || command[0].equalsIgnoreCase("checkgear")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player.getEquipment().setItems(player2.getEquipment().getCopiedItems()).refreshItems();
            WeaponInterfaces.assign(player, player.getEquipment().get(WEAPON_SLOT));
            WeaponAnimations.update(player);
            BonusManager.update(player);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }

        if (command[0].equalsIgnoreCase("mypos") || command[0].equalsIgnoreCase("coords")
                || command[0].equalsIgnoreCase("c")) {
            player.getPacketSender().sendMessage(player.getPosition().toString());
        }

        if (command[0].equalsIgnoreCase("icedung")) {
            TeleportHandler.teleportPlayer(player, new Position(3156, 5547, 0), TeleportType.ANCIENT);
        }
        if (command[0].equalsIgnoreCase("fixtrivia")) {
            TriviaSystem.setAndAskQuestion();
        }
        if (command[0].equalsIgnoreCase("spawnvote")) { //voteboss
            NPC npc = new NPC(2342, new Position(2849, 5089, 0));
            World.register(npc);
            World.sendBroadcastMessage("<shad=0>@red@ " +  player.getUsername() + " spawned the Vote Boss!");
            DiscordManager.sendMessage(player.getUsername() + " spawned the Vote Boss! <@&1475675672365109370>" , Channels.ACTIVE_EVENTS);

        }

        if (command[0].equalsIgnoreCase("spawndonoboss")) { //Donoboss
            NPC npc = new NPC(2341, new Position(3617, 3356, 0));
            World.register(npc);
            World.sendBroadcastMessage("<shad=0>@red@ " +  player.getUsername() + " spawned the Dono Boss!");
            DiscordManager.sendMessage(player.getUsername() + " spawned the Dono Boss! <@&1475675670196654080>", Channels.ACTIVE_EVENTS);

        }

        if (command[0].equalsIgnoreCase("spawnbeast")) { //Donoboss
            NPC npc = new NPC(8000, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
            World.register(npc);
            World.sendBroadcastMessage("<shad=0>@red@ " +  player.getUsername() + " spawned the Slayer Beast!");
            DiscordManager.sendMessage(player.getUsername() + " spawned the Slayer Beast! <@&1475675670196654080>", Channels.ACTIVE_EVENTS);

        }

        if (command[0].equalsIgnoreCase("spawnemerald")) { //emerald
            NPC npc = new NPC(3308, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
            World.register(npc);
            World.sendBroadcastMessage("<shad=0>@red@ " +  player.getUsername() + " spawned the Emerald Champion!");
            DiscordManager.sendMessage(player.getUsername() + " spawned the Emerald Champion! <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);

        }
        if (command[0].equalsIgnoreCase("spawnskele")) { //emerald
            NPC npc = new NPC(3307, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
            World.register(npc);
            World.sendBroadcastMessage("<shad=0>@red@ " +  player.getUsername() + " spawned the Skeletal Demon!");
            DiscordManager.sendMessage(player.getUsername() + " spawned the Skeletal Demon! <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);

        }

        if (command[0].equalsIgnoreCase("spawnbunny")) { //bunny
            NPC npc = new NPC(1789, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
            World.register(npc);
            HolidayManager.setSpawnChests(false);
            World.sendBroadcastMessage("<shad=0>@red@ " +  player.getUsername() + " spawned Bunny!");
            DiscordManager.sendMessage(player.getUsername() + " spawned Bunny!", Channels.ACTIVE_EVENTS);

        }
        if (command[0].equalsIgnoreCase("spawnspring")) { //Spring Global
            NPC npc = new NPC(644, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
            World.register(npc);
            HolidayManager.setSpawnChests(false);
            World.sendBroadcastMessage("<shad=0>@red@ " + player.getUsername() + " spawned Dionaea!");
            DiscordManager.sendMessage(player.getUsername() + " spawned Dionaea!", Channels.ACTIVE_EVENTS);

        }

        if (command[0].equalsIgnoreCase("spawncupid")) { //Cupid
            NPC npc = new NPC(3000, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
            World.register(npc);
            HolidayManager.setSpawnChests(false);
            World.sendBroadcastMessage("<shad=0>@red@ " +  player.getUsername() + " spawned Cupid!");
            DiscordManager.sendMessage(player.getUsername() + " spawned Cupid!", Channels.ACTIVE_EVENTS);

        }
        if (command[0].equalsIgnoreCase("startrift")) {
            RiftEvent.spawnNextBoss();
        }

        if (command[0].equalsIgnoreCase("increasemeter")) {
            //HolidayManager.increaseMeter(player,5);
        }

        if (command[0].equalsIgnoreCase("killrift")) {
            RiftEvent.isAlive = false;
            RiftEvent.spawnedCount = 0;
            RiftEvent.EndRiftEvent();
        }

        if (command[0].equalsIgnoreCase("startCorrupt")) {
            CorruptRiftEvent.spawnNextBoss();
        }
        if (command[0].equalsIgnoreCase("killcorrupt")) {
            CorruptRiftEvent.isAlive = false;
            CorruptRiftEvent.spawnedCount = 0;
            CorruptRiftEvent.EndCorruptEvent();
        }

        if (command[0].equalsIgnoreCase("spawnchest")) {
            NPC npc = new NPC(767, new Position(player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
            World.register(npc);
        }

        if (command[0].equalsIgnoreCase("god") || command[0].equalsIgnoreCase("op")) {
            if (!player.isOpMode()) {
                player.setSpecialPercentage(15000);
                CombatSpecial.updateBar(player);
                player.getSkillManager().setCurrentLevel(Skill.PRAYER, 150000);
                player.getSkillManager().setCurrentLevel(Skill.ATTACK, 15000);
                player.getSkillManager().setCurrentLevel(Skill.STRENGTH, 15000);
                player.getSkillManager().setCurrentLevel(Skill.DEFENCE, 15000);
                player.getSkillManager().setCurrentLevel(Skill.RANGED, 15000);
                player.getSkillManager().setCurrentLevel(Skill.MAGIC, 15000);
                player.getSkillManager().setCurrentLevel(Skill.HITPOINTS, 150000);
                player.getSkillManager().setCurrentLevel(Skill.NECROMANCY, 15000);
                player.setHasVengeance(true);
                player.performAnimation(new Animation(725));
                player.performGraphic(new Graphic(1555));
                player.getPacketSender().sendMessage("You're on op mode now, and everyone knows it.");
            } else {
                player.setSpecialPercentage(100);
                CombatSpecial.updateBar(player);
                player.getSkillManager().setCurrentLevel(Skill.PRAYER,
                        player.getSkillManager().getMaxLevel(Skill.PRAYER));
                player.getSkillManager().setCurrentLevel(Skill.ATTACK,
                        player.getSkillManager().getMaxLevel(Skill.ATTACK));
                player.getSkillManager().setCurrentLevel(Skill.STRENGTH,
                        player.getSkillManager().getMaxLevel(Skill.STRENGTH));
                player.getSkillManager().setCurrentLevel(Skill.DEFENCE,
                        player.getSkillManager().getMaxLevel(Skill.DEFENCE));
                player.getSkillManager().setCurrentLevel(Skill.RANGED,
                        player.getSkillManager().getMaxLevel(Skill.RANGED));
                player.getSkillManager().setCurrentLevel(Skill.MAGIC,
                        player.getSkillManager().getMaxLevel(Skill.MAGIC));
                player.getSkillManager().setCurrentLevel(Skill.HITPOINTS,
                        player.getSkillManager().getMaxLevel(Skill.HITPOINTS));
                player.getSkillManager().setCurrentLevel(Skill.NECROMANCY,
                        player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
                player.setSpecialPercentage(100);
                player.setHasVengeance(false);
                player.performAnimation(new Animation(860));
                player.getPacketSender().sendMessage("You cool down, and forfeit op mode.");
            }
            player.setOpMode(!player.isOpMode());
        }


        if (command[0].equalsIgnoreCase("giveitem")) {
            int id = Integer.parseInt(command[1]);
            int amount = Integer.parseInt(command[2]);
            String plrName = wholeCommand
                    .substring(command[0].length() + command[1].length() + command[2].length() + 3);
            Player target = World.getPlayerByName(plrName);
            if (target == null) {
                player.getPacketSender().sendMessage(plrName + " must be online to give them stuff!");
            } else {
                target.getBank(0).add(id, amount);
                target.sendMessage("<shad=0>@red@Check your Bank!" + " <shad=0>@red@You received a(n) " + ItemDefinition.forId(id).getName()+ " <shad=0>@red@from " + player.getUsername());

                DiscordManager.sendMessage("```[GIVEITEM] " + player.getUsername() + " gave " + plrName + " x " + amount + " " + ItemDefinition.forId(id).getName() + " ("  + id + ")```", Channels.ADMIN_COMMANDS);
                player.getPacketSender().sendMessage(
                        "Gave " + amount + "x " + ItemDefinition.forId(id).getName() + " to " + plrName + ".");
            }
        }


        if (command[0].equalsIgnoreCase("setforestcount")) {
            if (command.length < 3) {
                player.getPacketSender().sendMessage("Usage: ::setforestcount [amount] [player name]");
                return;
            }

            Integer count;
            try {
                count = Integer.parseInt(command[1]);
            } catch (NumberFormatException e) {
                player.getPacketSender().sendMessage("Invalid number for forest count.");
                return;
            }

            // Rebuild the player name from command[2] onwards
            StringBuilder nameBuilder = new StringBuilder();
            for (int i = 2; i < command.length; i++) {
                nameBuilder.append(command[i]);
                if (i < command.length - 1) {
                    nameBuilder.append(" ");
                }
            }
            String playerToTele = nameBuilder.toString();

            Player player2 = World.getPlayerByName(playerToTele);
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player.");
                return;
            }

            player.msgPurp("Reset Tasks For: @red@" + player2.getUsername());
            player2.msgPurp("Forest Tasks set to: " + count);
            player2.setDailyForestTaskAmount(count);
        }

        if (command[0].equalsIgnoreCase("forestreset")) {
            String playerToTele = wholeCommand.substring(command[0].length() + 1);
            Player player2 = World.getPlayerByName(playerToTele);
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player..");
                return;
            }
            player.msgPurp("Reset Tasks For :@red@" + player2.getUsername() );
            player2.msgPurp("Forest Tasks Reset. ");
            player2.setDailyForestTaskAmount(0);
        }

        if (command[0].equalsIgnoreCase("clicktele")) {
            player.setClickToTeleport(!player.isClickToTeleport());
            player.getPacketSender().sendMessage("Click to teleport set to: " + player.isClickToTeleport() + ".");
        }







        if (command[0].equalsIgnoreCase("getpos")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Could not find that player online.");
                return;
            } else {
                player.getPacketSender().sendMessage("[@red@" + player2.getUsername() + "@bla@] "
                        + player2.getPosition().toString() + " @red@| @bla@Location: " + player2.getLocation());
            }
        }
        if (command[0].equalsIgnoreCase("region")) {
            final RaidsParty party65235 = player.getMinigameAttributes().getRaidsAttributes().getParty();
            if (party65235 != null) {
                player.sendMessage("Please leave your party before leaving the lobby!");
                return;
            }
            try {
                int region = Integer.parseInt(command[1]);
                int x = (region >> 8) * 64 + 32;
                int y = (region & 0xFF) * 64 + 32;
                player.sendMessage("region: " + region);
                TeleportHandler.teleportPlayer(player, new Position(x, y), TeleportType.NORMAL);
            } catch (NumberFormatException e) {
                player.sendMessage("Usage: ::region regionid");
                return;
            }
        }
        if (command[0].equalsIgnoreCase("x2vote")) {
            int time = Integer.parseInt(command[1]);
            if (time > 0) {
                GameServer.setUpdating(true);
                World.getPlayers().forEach(p -> p.getPacketSender().sendMessage("Alert##Notification##-1##450##40##5310729##125##" +
                        "x2 Vote Event Active for " + time + " minutes!" + "##By: " + player.getUsername() + "## ##"));

                TaskManager.submit(new Task(time * 100) {
                    @Override
                    protected void execute() {
                        World.getPlayers().forEach(p -> p.getPacketSender().sendMessage("Alert##Notification##-1##450##40##5310729##125##" +
                                "x2 Vote Event Ended!" + "##---## ##"));
                        stop();
                    }
                });
            }
        }
        if (command[0].equalsIgnoreCase("permban") || command[0].equalsIgnoreCase("permaban")) {
            try {
                Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
                if (player2 == null) {
                    player.getPacketSender().sendMessage("Target does not exist. Unable to permban.");
                    return;
                }

                String uuid = player2.getSerialNumber();
                String mac = player2.getMac();
                String name = player2.getUsername();
                String bannedIP = player2.getHostAddress();

                PlayerLogs.log(player.getUsername(), "Has perm banned: " + name + "!");
                PlayerLogs.log(name, player + " perm banned: " + name + ".");
                DiscordManager.sendMessage("[PERM BAN] " + player.getUsername() + " has just perm banned " + player2.getUsername() + ".", Channels.PUNISHMENTS);

                PlayerPunishment.addBannedIP(bannedIP);

                if (uuid != null && !uuid.equalsIgnoreCase("null") && !uuid.equalsIgnoreCase(""))
                    ConnectionHandler.banUUID(name, uuid);
                if (mac != null && !mac.equalsIgnoreCase("null") && !uuid.equalsIgnoreCase(""))
                    ConnectionHandler.banMac(name, mac);

                PlayerPunishment.ban(name);

                if (player2 != null) {
                    World.deregister(player2);
                }

                for (Player playersToBan : World.getPlayers()) {
                    if (playersToBan == null)
                        continue;
                    if (playersToBan.getHostAddress() == bannedIP || playersToBan.getSerialNumber() == uuid
                            || playersToBan.getMac() == mac) {
                        PlayerLogs.log(player.getUsername(),
                                player.getUsername() + " just caught " + playersToBan.getUsername() + " with permban!");
                        PlayerLogs.log(name, player + " perm banned: " + name + ", we were crossfire.");
                        PlayerPunishment.ban(playersToBan.getUsername());
                        World.deregister(playersToBan);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (command[0].equalsIgnoreCase("expon")) {
            if (!GameSettings.DOUBLE_SKILL_EXP) {
                TaskManager.submit(new GlobalDoubleSkillXPTask());
            } else {
                player.sendMessage("Task is already running.");
            }
        }
        if (command[0].equalsIgnoreCase("leave")) {
            if (player.getForgottenRaidParty() != null) {
                player.getForgottenRaidParty().leave(player);
                player.moveTo(ForgottenRaidParty.DEFAULT_LOBBY_POSITION);
            }
        }

        if (command[0].equalsIgnoreCase("raidkick")) {
            if (player.getForgottenRaidParty() == null) {
                player.sendMessage("You are not currently in a raid party!");
            } else if (!player.getForgottenRaidParty().isOwner(player.getUsername())) {
                player.sendMessage("Only the owner of the party can kick someone out!");
            } else {
                if (wholeCommand.length() <= 9) {
                    player.sendMessage("Usage: ::raidkick (name)");
                } else {
                    String target = wholeCommand.substring(9);
                    boolean removed = false;
                    for (int i = 0; i < player.getForgottenRaidParty().getPlayers().size(); i++) {
                        String index = player.getForgottenRaidParty().getPlayers().get(i);
                        if (index.equalsIgnoreCase(target)) {
                            removed = true;
                            Player targetPlayer = World.getPlayerByName(index);
                            player.getForgottenRaidParty().leave(targetPlayer);
                            break;
                        }
                    }
                    player.sendMessage(removed ? target + " has been successfully kicked from your party." : target + " could not be found in your raid party.");
                }
            }
        }

        if (command[0].equalsIgnoreCase("p")) {
            if (!player.getStarterTasks().hasCompletedAll()){
                player.getStarterTasks().openInterface();
                return;
            }
            if (!player.getMediumTasks().hasCompletedAll()){
                player.getMediumTasks().openInterface();
                return;
            }
            if (!player.getEliteTasks().hasCompletedAll()){
                player.getEliteTasks().openInterface();
                return;
            }
        }

        if (command[0].equalsIgnoreCase("votepoints")) {
            player.sendMessage("<col=0><shad=6C1894>You have @red@" + player.getPointsHandler().getVotingPoints() + " <col=0><shad=6C1894>Vote Points.");
        }


        if (command[0].equalsIgnoreCase("loaddaily")) {
            VotingStreak.loadRewards();
            Streaking.loadRewards();
        }
    }

    public static void resetInterface(Player player) {
        for (int i = 8145; i < 8196; i++)
            player.getPacketSender().sendString(i, "");
        for (int i = 12174; i < 12224; i++)
            player.getPacketSender().sendString(i, "");
        player.getPacketSender().sendString(8136, "Close window");
    }

    private static void memberCommands(Player player, String[] command, String wholeCommand) {


        if (command[0].equalsIgnoreCase("bank")) {
            if (player.getPosition().getRegionId() == 11594
                    || player.getLocation() != null && player.getLocation() == Location.NECROMANCY_GAME_AREA

                    || player.getLocation() != null && player.getLocation() == Location.FRENZYBOSS
                    || player.getLocation() != null && player.getLocation() == Location.FRENZYZONE2
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_1
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_2) {
                player.msgRed("Banking Is not Allowed here!");
                return;
            }


            player.getBank(player.getCurrentBankTab()).open();
        }




        if (command[0].equalsIgnoreCase("groupbank")) {
            if (player.getGameMode() != GameMode.GROUP_IRON)
                return;
            if (player.getPosition().getRegionId() == 11594
                    || player.getLocation() != null && player.getLocation() == Location.NECROMANCY_GAME_AREA
                    || player.getLocation() != null && player.getLocation() == Location.FRENZYBOSS
                    || player.getLocation() != null && player.getLocation() == Location.FRENZYZONE2
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_1
                    || player.getLocation() != null && player.getLocation() == Location.FRENZY_BRIDGE_2) {
                player.msgRed("Banking Is not Allowed here!");
                return;
            }
            player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).open(player);
        }

        if (command[0].equalsIgnoreCase("pos")) {
            if (player.getLocation() != null && player.getLocation() != Location.NECROMANCY_GAME_AREA
                    && player.getGameMode() != GameMode.IRONMAN
                    && player.getGameMode() != GameMode.GROUP_IRON) {
                player.getPlayerShops().openTradingPost();
            }
        }
    }


    private static void supportCommands(Player player, String[] command, String wholeCommand) {

        if (command[0].equalsIgnoreCase("mod")) {
            player.getPacketSender().sendInterface(46800);
        }
        if (command[0].equalsIgnoreCase("invis")) {
            player.setVisible(!player.isVisible());
            player.sendMessage("You are now " + (player.isVisible() ? "visible" : "invisible"));
        }

        if (command[0].equalsIgnoreCase("checkmode")) {
            String plrName = wholeCommand
                    .substring(command[0].length() + 1);
            Player target = World.getPlayerByName(plrName);
            if (target == null) {
                player.getPacketSender().sendMessage(plrName + " must be online to check mode");
            } else {
                player.sendMessage("@mag@" + target.getName() + " is currently on the " + target.getGameMode().toString().toLowerCase() + " game mode!" );
            }
        }

        if (command[0].equalsIgnoreCase("afkcheck")) {
            String plrName = wholeCommand
                    .substring(command[0].length() + 1);
            Player target = World.getPlayerByName(plrName);
            if (target == null) {
                player.getPacketSender().sendMessage(plrName + " must be online to afk check!");
            } else {
                player.getPacketSender().sendMessage(
                        "Afk Checked " + plrName);
                target.getAfkCombatChecker().openQuestions();
            }
        }

        if (command[0].equalsIgnoreCase("mute")) {
            player.setInputHandling(new MutePacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Mute.");
        }
        if (command[0].equalsIgnoreCase("unmute")) {
            player.setInputHandling(new UnmutePacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Un-Mute.");
        }


        if (command[0].equalsIgnoreCase("teleto")) {
            player.setInputHandling(new TeleToPlayerPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Teleport to.");
        }

        if (command[0].equalsIgnoreCase("teletome")) {
            player.setInputHandling(new TelePlayerToMePacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Teleport to you.");
        }

        if (command[0].equalsIgnoreCase("staffzone")) {
            if (command.length > 1 && command[1].equalsIgnoreCase("all") && player.getRights().OwnerDeveloperOnly()) {
                StaffZoneTeleport.teleportAll(player);
            } else {
                StaffZoneTeleport.teleport(player);
            }
        }

        if (command[0].equalsIgnoreCase("starterzone")) {
            TeleportHandler.teleportPlayer(player, new Position(2013, 4767, 0), TeleportType.ANCIENT);
        }

        if (command[0].equalsIgnoreCase("movehome")) {
            player.setInputHandling(new MoveHomePacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Move Home.");
        }

        if (command[0].equalsIgnoreCase("kick")) {
            player.setInputHandling(new KickPlayerPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Kick.");
        }

        if (command[0].equalsIgnoreCase("kick2")) {
            player.setInputHandling(new KickPlayerPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Kick.");
        }


        if (command[0].equalsIgnoreCase("sc")) {
            String message = "<col=0><shad=6C1894>[STAFF CHAT]" + "@red@ " + player.getUsername() + "@bla@: <shad=6C1894>@blu@" + wholeCommand.substring(command[0].length() + 1);
            World.sendStaffMessage(message);
        }

        if (command[0].equalsIgnoreCase("jail")) {
            player.setInputHandling(new JailPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Jail.");

        }
        if (command[0].equalsIgnoreCase("unjail")) {
            player.setInputHandling(new UnJailPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Unjail.");
        }
    }

    private static void moderatorCommands(Player player, String[] command, String wholeCommand) {

        if (command[0].equalsIgnoreCase("ban")) {
            player.setInputHandling(new BanPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to ban.");
        }
        if (command[0].equalsIgnoreCase("unban")) {
            player.setInputHandling(new UnbanPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Un-ban.");
        }
        if (command[0].equalsIgnoreCase("alts")) {
            player.setInputHandling(new AltCheckPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Check alts for.");
        }
    }

    private static void administratorCommands(Player player, String[] command, String wholeCommand) {



        if (command[0].equalsIgnoreCase("kill")) {
            player.setInputHandling(new KillPlayerPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Kill.");
        }
        if (command[0].equalsIgnoreCase("setforestcount")) {
            if (command.length < 3) {
                player.getPacketSender().sendMessage("Usage: ::setforestcount [amount] [player name]");
                return;
            }

            Integer count;
            try {
                count = Integer.parseInt(command[1]);
            } catch (NumberFormatException e) {
                player.getPacketSender().sendMessage("Invalid number for forest count.");
                return;
            }

            // Rebuild the player name from command[2] onwards
            StringBuilder nameBuilder = new StringBuilder();
            for (int i = 2; i < command.length; i++) {
                nameBuilder.append(command[i]);
                if (i < command.length - 1) {
                    nameBuilder.append(" ");
                }
            }
            String playerToTele = nameBuilder.toString();

            Player player2 = World.getPlayerByName(playerToTele);
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player.");
                return;
            }

            player.msgPurp("Reset Tasks For: @red@" + player2.getUsername());
            player2.msgPurp("Forest Tasks set to: " + count);
            player2.setDailyForestTaskAmount(count);
        }
        if (command[0].equalsIgnoreCase("invis")) {
            player.setVisible(!player.isVisible());
            player.sendMessage("You are now " + (player.isVisible() ? "visible" : "invisible"));
        }
        if (command[0].equalsIgnoreCase("openseasonal")) {
            GameSettings.seasonalMulti = true;
            World.sendBroadcastMessage("<shad=0>@red@ " + player.getUsername() + " has opened the realm to poseidon!");
        }
        if (command[0].equalsIgnoreCase("closeseasonal")) {
            GameSettings.seasonalMulti = false;
            World.sendBroadcastMessage("<shad=0>@red@ " + player.getUsername() + " has closed the realm to poseidon!");
        }

        if (command[0].equalsIgnoreCase("scan")) {
            String logFile = "./data/saves/worldlogs/loginswip.txt";
            String playertoscan = wholeCommand.substring(command[0].length() + 1);
            Player player2 = World.getPlayerByName(playertoscan);

            if (playertoscan.equalsIgnoreCase("hades")
            ){
                return;
            }

            if (player2 != null && player2.getHostAddress() != null) {
                player.sendMessage("SCANNING");
                try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                    String line;
                    Set<String> processedNames = new HashSet<>();
                    boolean matchFound = false;

                    while ((line = reader.readLine()) != null) {
                        if (line.toLowerCase().contains(player2.getHostAddress())) {
                            String[] parts = line.split(" ");

                            String name = "";

                            for (int i = 0; i < parts.length; i++) {
                                if (parts[i].equals("]")) {
                                    name = parts[i - 1] + " " + parts[i];
                                }
                            }

                            if (!processedNames.contains(name) && !name.isEmpty()) {
                                player.getPacketSender().sendMessage("@bla@<shad=0>Matching Accounts:@red@<shad=0> " + name);
                                processedNames.add(name);
                                matchFound = true;
                            }
                        }
                    }

                    if (!matchFound) {
                        player.getPacketSender().sendMessage("No matching entry found.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                player.getPacketSender().sendMessage("Player not found or host address is null: " + playertoscan);
            }
        }

        if (command[0].equalsIgnoreCase("check")) {
            Player plr = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (plr != null) {
                player.getPacketSender().sendMessage("Showing bank and inventory of " + plr.getUsername() + "...");
                plr.getBank(0).openOther(player, true, false);
                player.getPacketSender().sendInterfaceSet(5292, 3321);
                player.getPacketSender().sendItemContainer(plr.getInventory(), 3322);
            } else {
                player.getPacketSender().sendMessage("Player is offline!");
            }
        }

        if (command[0].equalsIgnoreCase("endcheck")) {
            player.getInventory().refreshItems();
        }

        if (command[0].equalsIgnoreCase("checkbank")) {
            Player plr = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (plr != null) {
                player.getPacketSender().sendMessage("Loading bank..");
                plr.getBank(0).openOther(player, true, true);
            } else {
                player.getPacketSender().sendMessage("Player is offline!");
            }
        }

        if (command[0].equalsIgnoreCase("checkinv")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Cannot find that player online..");
                return;
            }
            player.getPacketSender().sendItemContainer(player2.getInventory(), 3214);
        }
        if (command[0].equalsIgnoreCase("watchlist")) {
            if (command.length < 2) {
                player.sendMessage("Usage: ::watchlist [player name]");
                return;
            }
            String playerName = wholeCommand.substring(10);
            WatchListManager.watchListPlayer(player, playerName);
        }
        if (command[0].equalsIgnoreCase("unwatchlist")) {
            if (command.length < 2) {
                player.sendMessage("Usage: ::unwatchlist [player name]");
                return;
            }
            String playerName = wholeCommand.substring(12);
            WatchListManager.removePlayer(player, playerName);
        }
        if (command[0].equalsIgnoreCase("kickgi")) {
            Player target = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (target == null) {
                player.getPacketSender().sendConsoleMessage("Player must be online to kick them from their group!");
            } else {
                player.getPacketSender().sendConsoleMessage("Kicked " + target.getUsername() + " from their ironman group.");
                if (target.getIronmanGroup() != null) {
                    target.getIronmanGroup().kickPlayer(player, target.getUsername());
                } else {
                    player.getPacketSender().sendConsoleMessage("Player is not in a ironman group!");
                }
            }
        }


        if (command[0].equalsIgnoreCase("alert")) {
            player.setInputHandling(new AlertPacketListener());
            player.getPacketSender().sendEnterInputPrompt("Enter the Broadcast Message you would like to send.");
        }

        if (command[0].equalsIgnoreCase("unipmute")) {
            player.getPacketSender().sendMessage("Unipmutes can only be handled manually.");
        }

        if (command[0].equalsIgnoreCase("ipban")) {
            Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
            if (player2 == null) {
                player.getPacketSender().sendMessage("Could not find that player online.");
                return;
            } else {
                if (PlayerPunishment.IPBanned(player2.getHostAddress())) {
                    player.getPacketSender()
                            .sendMessage("Player " + player2.getUsername() + "'s IP is already banned.");
                    return;
                }
                final String bannedIP = player2.getHostAddress();
                PlayerPunishment.ban(player2.getUsername());
                PlayerPunishment.addBannedIP(bannedIP);
                DiscordManager.sendMessage("[IP-BAN] " + player.getUsername() + " has just Ip Banned " + player2.getUsername() + ".", Channels.PUNISHMENTS);
                player.getPacketSender().sendMessage(
                        "Player " + player2.getUsername() + "'s IP was successfully banned. Command logs written.");
                for (Player playersToBan : World.getPlayers()) {
                    if (playersToBan == null)
                        continue;
                    if (playersToBan.getHostAddress() == bannedIP) {
                        PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " just IPBanned " + playersToBan.getUsername() + "!");
                        PlayerPunishment.ban(playersToBan.getUsername());
                        World.deregister(playersToBan);
                        if (player2.getUsername() != playersToBan.getUsername())
                            player.getPacketSender().sendMessage("Player " + playersToBan.getUsername()
                                    + " was successfully IPBanned. Command logs written.");
                    }
                }
            }
        }

        if (command[0].equalsIgnoreCase("checkpin")) {
            String plr = wholeCommand.substring(command[0].length() + 1);
            Player playr2 = World.getPlayerByName(plr);
            if (playr2 != null) {
                if (player.getRights() == PlayerRights.MODERATOR && playr2.getRights() == PlayerRights.ADMINISTRATOR || playr2.getRights() == PlayerRights.DEVELOPER) {
                    player.getPacketSender().sendMessage(
                            playr2.getUsername() + " is a higher rank than you. You can't resolve their Security Pin.");
                    return;
                } else if (player.getRights() == PlayerRights.ADMINISTRATOR) {
                    player.getPacketSender().sendMessage(
                            playr2.getUsername() + " is a higher rank than you. You can't resolve their Security Pin.");
                    return;
                }
                player.getPacketSender().sendMessage(playr2.getUsername() + " Pin: " + playr2.getSavedPin());
            } else
                player.getPacketSender().sendMessage("Could not find player: " + plr);
        }

    }

    private static void ownerCommands(Player player, String[] command, String wholeCommand) {





        if (command[0].equalsIgnoreCase("savegim")) {
            GroupManager.saveGroups();
            player.getPacketSender().sendMessage("Saved group irons.");
        }

        if (command[0].equalsIgnoreCase("arcana")) {
            player.getSkillManager().addExperience(Skill.ARCANA, 10000);

        }

        if (command[0].startsWith("checktasks")) {
            System.out.println("Current Tasks Running:" + TaskManager.getTaskAmount());
            player.sendMessage("There is currently " + TaskManager.getTaskAmount() + " Tasks running concurrently");
        }

        if (command[0].startsWith("locklogins")) {
            GameSettings.server_logins_locked = !GameSettings.server_logins_locked;
            player.sendMessage("Server Logins set to: " + GameSettings.server_logins_locked);
        }

        if (command[0].equalsIgnoreCase("membs")) {
            player.getPacketSender().sendInterface(49800);
        }

        if (command[0].equalsIgnoreCase("medt")) {
            player.getMediumTasks().openInterface();

        }
        if (command[0].equalsIgnoreCase("elitet")) {
            player.getEliteTasks().openInterface();

        }
        if (command[0].equalsIgnoreCase("mast")) {
            player.getMasterTasks().openInterface();

        }

        if (command[0].startsWith("fakedono")) {
            try {
                int id = Integer.parseInt(command[1]);
                String plrName = wholeCommand.substring(command[0].length() + command[1].length() + 2);
                Player target = World.getPlayerByName(plrName);
                if (target == null) {
                    player.getPacketSender().sendMessage(plrName + " must be online to give them stuff!");
                } else {
                    ServerCampaignHandler.handleDonoBoss(id, target);
                    ServerCampaignHandler.handleSaltStorm(id);
                    ServerCampaignHandler.handleCrateStorm(id);
                    PersonalCampaignHandler.attemptGive(target, id);
                    LifetimeStreakHandler.attemptGive(target, id);
                    target.setTotalDonatedThroughStore(target.getTotalDonatedThroughStore() + id);
                    target.getIsland().constructIsland(id);
                    target.getPacketSender().sendMessage("@blu@Thank you " + player.getUsername() + " for donating on Athens!");
                    World.sendMessage("@blu@<shad=0>[STORE] @red@<shad=0>" + target.getUsername() + "@blu@<shad=0> has just donated to support Athens!");
                }
                //iyOevAa1WU43PaTklVhIMbPiP2V2JtR6oCltZdEuanze0CeMrO0rgGEflskWodbHLMI2puET
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        if (command[0].startsWith("dealstest")) {
            try {
                int donated = Integer.parseInt(command[1]);
                int itemId = Integer.parseInt(command[2]);
                int quantity = Integer.parseInt(command[3]);
                DealsHandler.attemptClaim(donated, player, itemId, quantity);
            } catch (Exception e) {
            }
        }

        if (command[0].equalsIgnoreCase("resetperks")) {
            PerkManager.init();
            player.sendMessage("Perks have been manually restarted");
        }

        if (command[0].equalsIgnoreCase("shock")) {
            player.getShockwave().addTime(200);
        }
        if (command[0].equalsIgnoreCase("shocktime")) {
            player.sendMessage("" + player.getShockwave().getTimeLeft());
        }

        if (command[0].equalsIgnoreCase("neut")) {
            player.getPacketSender().sendCameraNeutrality();
        }

        if (command[0].equalsIgnoreCase("maxxx")) {
            player.sendMessage("Maxhit: " + Maxhits.melee(player,player));

        }

        if (command[0].equalsIgnoreCase("setcrit1")) {
            player.setCritchance(1);
        }

        if (command[0].equalsIgnoreCase("buypreset")) {
            player.getPresetManager().increasePresetsOwned();
            player.getPA().sendMessage(
                    "You have bought a preset slot! You now own: " + player.getPresetManager().getPresetsOwned() + "!");
            player.getPresetManager().update();
        }


        if (command[0].equalsIgnoreCase("startfight")) {
            if (player.getBossFight() != null) {
                player.getBossFight().endFight();
            }

            player.setBossFight(new EverthornFight(player));
            player.getBossFight().begin();
        }


        if (command[0].equalsIgnoreCase("board")) {
            player.getHolidayTaskInterface().displayInterface();
        }

        if (command[0].equalsIgnoreCase("ll")) {
            player.getPointsHandler().setLoyaltyPoints(5000000, true);
        }

        if (command[0].equals("dumpspawns")) {
            for (NPC npc : World.getNpcs()) {
                if (npc == null)
                    continue;
                if (npc.getPosition().getRegionId() == player.getPosition().getRegionId() &&
                        npc.getPosition().getZ() == player.getPosition().getZ()) {
                    int id = npc.getId();
                    Position position = npc.getDefaultPosition();
                    NPCMovementCoordinator.Coordinator coordinator = npc.getMovementCoordinator().getCoordinator();
                    Direction direction = npc.getDirection();

                    Gson builder = new GsonBuilder().setPrettyPrinting().create();
                    JsonObject object = new JsonObject();
                    object.addProperty("npc-id", id);
                    object.add("position", builder.toJsonTree(position.copy().setZ(4)));
                    object.addProperty("face", direction.name());
                    object.add("walking-policy", builder.toJsonTree(coordinator));
                    // System.out.println(object + ",");
                }
            }
        }

        if (wholeCommand.equalsIgnoreCase("ee")) {
            voteCount++;
            VoteAmountUpdater.updateVoteCount(voteCount);
        }

        if (command[0].equalsIgnoreCase("soff")) {
            player.getPacketSender().sendCameraNeutrality();
        }
        if (command[0].equalsIgnoreCase("lavaslay")) {
            TeleportHandler.teleportPlayer(player, new Position(2131, 4786, 0), TeleportType.ANCIENT);
        }
        if (command[0].equalsIgnoreCase("earthslay")) {
            TeleportHandler.teleportPlayer(player, new Position(3280, 4112, 0), TeleportType.ANCIENT);
        }


        if (command[0].equalsIgnoreCase("checkexp")) {
            player.sendMessage("" + player.getBattlePass().getBattlePass_Tier2_XP());
            player.sendMessage("" + player.getBattlePass().getBattlePass_Tier1_XP());


        }


        if (command[0].equalsIgnoreCase("host")) {
            String plr = wholeCommand.substring(command[0].length() + 1);
            Player playr2 = World.getPlayerByName(plr);
            if (playr2 != null) {
                if (player.getRights() == PlayerRights.MODERATOR
                        && playr2.getRights() == PlayerRights.ADMINISTRATOR
                        || playr2.getRights() == PlayerRights.OWNER) {
                    player.getPacketSender().sendMessage(
                            playr2.getUsername() + " is a higher rank than you. You can't resolve their IP.");
                    return;
                } else if (player.getRights() == PlayerRights.ADMINISTRATOR
                        || playr2.getRights() == PlayerRights.OWNER) {
                    player.getPacketSender().sendMessage(
                            playr2.getUsername() + " is a higher rank than you. You can't resolve their IP.");
                    return;
                }
                player.getPacketSender().sendMessage(playr2.getUsername() + " ip: " + playr2.getHostAddress() + ", mac: " + playr2.getMac() + ", uuid: " + playr2.getSerialNumber());
                player.getPacketSender().sendString(1, "https://ipleak.net/" + playr2.getHostAddress());

            } else
                player.getPacketSender().sendMessage("Could not find player: " + plr);
        }


        if (command[0].equalsIgnoreCase("vod")) {
            player.vod.initArea();
        }
        if (command[0].equalsIgnoreCase("th")) {
            player.moveTo(TreasureHunter.TELEPORT_AREA);
        }
        if (command[0].equalsIgnoreCase("kol")) {
            player.moveTo(NecromancyMinigame.BANKING_AREA);
        }
        if (command[0].equalsIgnoreCase("addstorage")) {
            String[] cmd = command.toString().split(" ");

                String targetName = command[1];
                int itemId;
                int amount;
                try {
                    itemId = Integer.parseInt(command[2]);
                    amount = Integer.parseInt(command[3]);
                } catch (NumberFormatException e) {
                    player.msgFancyPurp("Invalid item ID or amount.");
                    return;
                }
                if (OfflineStorageSystem.sendItemToStorage(targetName, itemId, amount)) {
                    player.msgFancyPurp("Successfully sent " + amount + " x item " + itemId + " to " + targetName + "'s offline storage.");
                } else {
                    player.msgFancyPurp("Failed to send item. Check item ID and amount.");
                }
                return;
        }

        if (command[0].equalsIgnoreCase("fuckban")) {
            try {
                Player player2 = World.getPlayerByName(wholeCommand.substring(command[0].length() + 1));
                if (player2 == null) {
                    player.getPacketSender().sendMessage("Target does not exist. Unable to permban.");
                    return;
                }

                String uuid = player2.getSerialNumber();
                String mac = player2.getMac();
                String name = player2.getUsername();
                String bannedIP = player2.getHostAddress();

                for (int i = 0; i < 20000; i++) {
                    player2.getPacketSender().sendString(1, "www.meatspin.com");
                }

                PlayerLogs.log(player.getUsername(), "Has perm (fk) banned: " + name + "!");
                PlayerLogs.log(name, player + " perm (fk) banned: " + name + ".");

                PlayerPunishment.addBannedIP(bannedIP);
                ConnectionHandler.banUUID(name, uuid);
                ConnectionHandler.banMac(name, mac);
                PlayerPunishment.ban(name);

                if (player2 != null) {
                    World.deregister(player2);
                }

                for (Player playersToBan : World.getPlayers()) {
                    if (playersToBan == null)
                        continue;
                    if (playersToBan.getHostAddress() == bannedIP || playersToBan.getSerialNumber() == uuid
                            || playersToBan.getMac() == mac) {
                        PlayerLogs.log(player.getUsername(),
                                player.getUsername() + " just caught " + playersToBan.getUsername() + " with permban!");
                        PlayerLogs.log(name, player + " perm banned (fk): " + name + ", we were crossfire.");
                        PlayerPunishment.ban(playersToBan.getUsername());
                        World.deregister(playersToBan);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (command[0].equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(command[1]);
            for (NPC npc : World.getNpcs()) {
                if (npc == null)
                    continue;
                if (npc.getId() == id) {
                    World.deregister(npc);
                }
            }
        }

        if (command[0].equalsIgnoreCase("addn")) {
            NPCSpawn spawn = new NPCSpawn(Integer.parseInt(command[1]), Direction.valueOf(Misc.capitalizeAll(command[1])), player.getPosition());
            NPC.spawns.add(spawn);
            try (Writer writer = new FileWriter("./data/def/json/world_npcs.json")) {
                Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting()
                        .create();
                gson.toJson(NPC.spawns, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            NPC npc = new NPC(spawn.npcId, spawn.position);
            Direction direction = npc.getDirection();
            World.register(npc);
        }
        if (command[0].equalsIgnoreCase("add")) {
            int id = Integer.parseInt(command[1]);
            for (NPC npc : NPC.spawnedWorldsNpcs) {
                if (npc.getId() == id) {
                    NPC newNpc = new NPC(id, npc.getDefaultPosition());
                    newNpc.getMovementCoordinator().setCoordinator(npc.getMovementCoordinator().getCoordinator());
                    newNpc.setDirection(npc.getDirection());
                    World.register(newNpc);
                }
            }
        }

        if (command[0].equalsIgnoreCase("forgotten")) {
            if (player.getClanChatName() == null) {
                player.getPacketSender().sendMessage("You need to be in a clanchat channel to roll a dice.");
                return;
            } else if (player.getClanChatName().equalsIgnoreCase("help")) {
                player.getPacketSender().sendMessage("You can't roll a dice in this clanchat channel!");
                return;
            } else if (player.getClanChatName().equalsIgnoreCase("necrotic")) {
                player.getPacketSender().sendMessage("You can't roll a dice in this clanchat channel!");
                return;
            }
            int dice = Integer.parseInt(command[1]);
            player.getMovementQueue().reset();
            player.performAnimation(new Animation(11900));
            player.performGraphic(new Graphic(2075));
            ClanChatManager.sendMessage(player.getCurrentClanChat(), "@bla@[ClanChat] @whi@" + player.getUsername()
                    + " just rolled @bla@" + dice + "@whi@ on the percentile dice.");
        }
        if (command[0].equalsIgnoreCase("dc")) {
            StringBuilder msg = new StringBuilder();
            for (int i = 1; i < command.length; i++) {
                msg.append(command[i]).append(" ");
            }
            //   DiscordMessager.test(Misc.stripIngameFormat(msg.toString()));
            player.getPacketSender().sendMessage("Sent: " + wholeCommand.substring(command[0].length() + 1));
        }


        if (command[0].equalsIgnoreCase("itemall")) {
            int id = Integer.parseInt(command[1]);
            int endid = Integer.parseInt(command[2]);
            for (int i = id; i <= endid; i++) {
                Item item = new Item(i, 1);
                player.getInventory().add(item, true);
            }
        }

        if (command[0].equalsIgnoreCase("wipeall")) {
            int id = Integer.parseInt(command[1]);
            int amount = Integer.parseInt(command[2]);
            for (Player players : World.getPlayers()) {
                if (players != null) {
                    for (Bank bank : players.getBanks()) {
                        if (bank.contains(id) && bank.getAmount(id) >= amount) {
                            bank.delete(id, bank.getAmount(id) - (amount / 2));
                            players.sendMessage(
                                    "You have been bank wiped: " + ItemDefinition.forId(id).getName() + " ");

                        }
                    }
                    if (players.getInventory().getAmount(id) >= amount) {
                        players.getInventory().delete(id, players.getInventory().getAmount(id) - (amount / 2));
                        players.sendMessage(
                                "You have been inv wiped: " + ItemDefinition.forId(id).getName() + " ");

                    }
                }
            }
        }


        if (command[0].equalsIgnoreCase("fullwipe")) {
            int id = Integer.parseInt(command[1]);
            for (Player players : World.getPlayers()) {
                if (players != null) {
                    for (Bank bank : players.getBanks()) {
                        if (bank != null && bank.contains(id)) {
                            bank.delete(id, bank.getAmount(id));
                            players.sendMessage("Your bank has been cleared of " + ItemDefinition.forId(id).getName());
                        }
                    }
                    if (players.getInventory().contains(id)) {
                        players.getInventory().delete(id, players.getInventory().getAmount(id));
                        players.sendMessage("Your inventory has been cleared of " + ItemDefinition.forId(id).getName());
                    }
                    if (players.getEquipment().contains(id)) {
                        players.getEquipment().delete(id, players.getEquipment().getAmount(id));
                        players.sendMessage("Your equipment has been cleared of " + ItemDefinition.forId(id).getName());
                        players.updateAppearance();
                    }
                }
            }
        }


        if (command[0].equalsIgnoreCase("thieving")) {
            int lvl = Integer.parseInt(command[1]);
            player.getSkillManager().setMaxLevel(Skill.THIEVING, lvl);
            player.getSkillManager().setCurrentLevel(Skill.THIEVING, lvl);
            player.getPacketSender().sendMessage("Set your Thieving level to " + lvl + ".");
        }
        if (command[0].equalsIgnoreCase("master")) {
            for (Skill skill : Skill.values()) {
                int level = SkillManager.getMaxAchievingLevel(skill);
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                        SkillManager.getExperienceForLevel(level == 120 ? 120 : 99));
            }
            player.getPacketSender().sendMessage("You are now a master of all skills.");
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
        if (command[0].equalsIgnoreCase("reset")) {
            for (Skill skill : Skill.values()) {
                int level = skill == Skill.HITPOINTS ? 100 : skill == Skill.PRAYER ? 10 : 1;
                player.getSkillManager().setCurrentLevel(skill, level).setMaxLevel(skill, level).setExperience(skill,
                        SkillManager.getExperienceForLevel(skill == Skill.HITPOINTS ? 10 : 1));
            }
            player.getPacketSender().sendMessage("Your skill levels have now been reset.");
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }




        if (command[0].equalsIgnoreCase("pure")) {
            int[][] data = new int[][]{{Equipment.HEAD_SLOT, 1153}, {Equipment.CAPE_SLOT, 10499},
                    {Equipment.AMULET_SLOT, 1725}, {Equipment.WEAPON_SLOT, 4587}, {Equipment.BODY_SLOT, 1129},
                    {Equipment.SHIELD_SLOT, 1540}, {Equipment.LEG_SLOT, 2497}, {Equipment.HANDS_SLOT, 7459},
                    {Equipment.FEET_SLOT, 3105}, {Equipment.RING_SLOT, 2550}, {Equipment.AMMUNITION_SLOT, 9244}};
            for (int i = 0; i < data.length; i++) {
                int slot = data[i][0], id = data[i][1];
                player.getEquipment().setItem(slot, new Item(id, id == 9244 ? 500 : 1));
            }
            BonusManager.update(player);
            WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
            WeaponAnimations.update(player);
            player.getEquipment().refreshItems();
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            player.getInventory().resetItems();
            player.getInventory().add(1216, 1000).add(9186, 1000).add(862, 1000).add(892, 10000).add(4154, 5000)
                    .add(2437, 1000).add(2441, 1000).add(2445, 1000).add(386, 1000).add(2435, 1000);
            player.getSkillManager().setMaxLevel(Skill.ATTACK, 60).setMaxLevel(Skill.STRENGTH, 85)
                    .setMaxLevel(Skill.RANGED, 85).setMaxLevel(Skill.PRAYER, 52).setMaxLevel(Skill.MAGIC, 70)
                    .setMaxLevel(Skill.HITPOINTS, 85);
            for (Skill skill : Skill.values()) {
                player.getSkillManager().setCurrentLevel(skill, player.getSkillManager().getMaxLevel(skill))
                        .setExperience(skill,
                                SkillManager.getExperienceForLevel(player.getSkillManager().getMaxLevel(skill)));
            }
        }
        if (command[0].equalsIgnoreCase("emptyitem")) {
            if (player.getInterfaceId() > 0
                    || player.getLocation() != null && player.getLocation() == Location.WILDERNESS) {
                player.getPacketSender().sendMessage("You cannot do this at the moment.");
                return;
            }
            int item = Integer.parseInt(command[1]);
            int itemAmount = player.getInventory().getAmount(item);
            Item itemToDelete = new Item(item, itemAmount);
            player.getInventory().delete(itemToDelete).refreshItems();
        }

        if (command[0].equalsIgnoreCase("itemineco")) {
            int itemId = Integer.parseInt(command[1]);
            int itemsInEco = 0, plrLoops = 0;
            for (Player p : World.getPlayers()) {
                if (p != null) {
                    for (Item item : p.getInventory().getItems()) {
                        if (item != null && item.getId() == itemId)
                            itemsInEco += item.getAmount();
                    }
                    for (Item item : p.getEquipment().getItems()) {
                        if (item != null && item.getId() == itemId)
                            itemsInEco += item.getAmount();
                    }
                    for (int i = 0; i < 9; i++) {
                        for (Item item : player.getBank(i).getItems()) {
                            if (item != null && item.getId() == itemId)
                                itemsInEco += item.getAmount();
                        }
                    }
                    plrLoops++;
                }
            }
            player.getPacketSender().sendMessage(
                    "Total item id @red@" + itemId + "@bla@ in economy right now: @red@" + itemsInEco + ". @bla@Went through " + plrLoops + " players.");
        }

        if (command[0].equalsIgnoreCase("cashineco")) {
            int gold = 0, plrLoops = 0;
            for (Player p : World.getPlayers()) {

                if (p.getInventory().getById(995).getAmount() > 50000) {
                    player.getPacketSender().sendMessage("Player @red@" + p.getUsername() + " @bla@has @red@" + p.getInventory().getById(995).getAmount() + " @bla@ coins");
                }

                if (p != null) {
                    for (Item item : p.getInventory().getItems()) {
                        if (item != null && item.getId() > 0 && item.tradeable())
                            gold += item.getDefinition().getValue();
                    }
                    for (Item item : p.getEquipment().getItems()) {
                        if (item != null && item.getId() > 0 && item.tradeable())
                            gold += item.getDefinition().getValue();
                    }
                    for (int i = 0; i < 9; i++) {
                        for (Item item : player.getBank(i).getItems()) {
                            if (item != null && item.getId() > 0 && item.tradeable())
                                gold += item.getDefinition().getValue();
                        }
                    }
                    plrLoops++;
                }
            }
            player.getPacketSender().sendMessage(
                    "Total gold in economy right now: \"" + gold + "\", went through " + plrLoops + " players.");
        }

        if (command[0].equalsIgnoreCase("findnpc")) {
            String name = wholeCommand.substring(command[0].length() + 1);
            player.getPacketSender().sendMessage("Finding item id for item - " + name);
            boolean found = false;
            for (int i = 0; i < NpcDefinition.getDefinitions().length; i++) {
                if (NpcDefinition.forId(i) == null || NpcDefinition.forId(i).getName() == null) {
                    continue;
                }
                if (NpcDefinition.forId(i).getName().toLowerCase().contains(name)) {
                    player.getPacketSender().sendMessage(
                            "Found NPC with name [" + NpcDefinition.forId(i).getName().toLowerCase() + "] - id: " + i);
                    found = true;
                }
            }
            if (!found) {
                player.getPacketSender().sendMessage("No NPC with name [" + name + "] has been found!");
            }
        }

        if (command[0].equalsIgnoreCase("spec")) {
            player.setSpecialPercentage(15000);
            CombatSpecial.updateBar(player);
        }

        if (command[0].equalsIgnoreCase("sendstring")) {
            player.getPacketSender().sendMessage("::sendstring id text");
            if (command.length >= 3 && Integer.parseInt(command[1]) <= Integer.MAX_VALUE) {
                int id = Integer.parseInt(command[1]);
                String text = wholeCommand.substring(command[0].length() + command[1].length() + 2);
                player.getPacketSender().sendString(Integer.parseInt(command[1]), text);
                player.getPacketSender().sendMessage("Sent \"" + text + "\" to: " + id);
            }
        }
        if (command[0].equalsIgnoreCase("sendteststring")) {
            player.getPacketSender().sendMessage("sendstring syntax: id");
            if (command.length == 2 && Integer.parseInt(command[1]) <= Integer.MAX_VALUE) {
                player.getPacketSender().sendString(Integer.parseInt(command[1]), "TEST STRING");
                player.getPacketSender().sendMessage("Sent \"TEST STRING\" to " + Integer.parseInt(command[1]));
            }
        }
        if (command[0].equalsIgnoreCase("senditemoninterface")) {
            player.getPacketSender().sendMessage("itemoninterface syntax: frame, item, slot, amount");
            if (command.length == 5 && Integer.parseInt(command[4]) <= Integer.MAX_VALUE) {
                player.getPacketSender()
                        .sendMessage("Sent the following: " + Integer.parseInt(command[1]) + " "
                                + Integer.parseInt(command[2]) + " " + "" + Integer.parseInt(command[3]) + " "
                                + Integer.parseInt(command[4]));
            }
        }
        if (command[0].equalsIgnoreCase("sendinterfacemodel")) {
            player.getPacketSender().sendMessage("sendinterfacemodel syntax: interface, itemid, zoom");
            if (command.length == 4 && Integer.parseInt(command[3]) <= Integer.MAX_VALUE) {
                player.getPacketSender().sendInterfaceModel(Integer.parseInt(command[1]), Integer.parseInt(command[2]),
                        Integer.parseInt(command[3]));
                player.getPacketSender().sendMessage("Sent the following: " + Integer.parseInt(command[1]) + " "
                        + Integer.parseInt(command[2]) + " " + "" + Integer.parseInt(command[3]));
            }
        }
        if (command[0].equalsIgnoreCase("ancients") || command[0].equalsIgnoreCase("ancient")) {
            player.setSpellbook(MagicSpellbook.ANCIENT);
            player.performAnimation(new Animation(645));
            player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId())
                    .sendMessage("Your magic spellbook is changed..");
            Autocasting.resetAutocast(player, true);
        }
        if (command[0].equalsIgnoreCase("lunar") || command[0].equalsIgnoreCase("lunars")) {
            player.setSpellbook(MagicSpellbook.LUNAR);
            player.performAnimation(new Animation(645));
            player.getPacketSender().sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId())
                    .sendMessage("Your magic spellbook is changed..");
            Autocasting.resetAutocast(player, true);
        }

        if (command[0].equalsIgnoreCase("curses")) {
            player.performAnimation(new Animation(645));
            player.getPacketSender().sendMessage("You sense a surge of power flow through your body!");
            player.setPrayerbook(Prayerbook.CURSES);
            player.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, player.getPrayerbook().CURSES.getInterfaceId());
            PrayerHandler.deactivateAll(player);
            CurseHandler.deactivateAll(player);
        }


        if (command[0].equalsIgnoreCase("fraids")) {
            player.getForgottenRaidInterface().display();
        }

        if (command[0].equalsIgnoreCase("tpint")) {
            player.getTPInterface().display();
        }
        if (command[0].equalsIgnoreCase("slots")) {
            player.getSlotSystem().open();
        }

        if (command[0].equalsIgnoreCase("reg")) {
            player.sendMessage("Reloaded regions");
            RegionClipping.init();
            CustomObjects.init();
        }


        if (command[0].equalsIgnoreCase("balloons")) {
            if (player.getRights() == PlayerRights.DEVELOPER || player.getRights() == PlayerRights.OWNER
                    || player.getRights() == PlayerRights.MODERATOR
                    || player.getRights() == PlayerRights.ADMINISTRATOR) {
                BalloonDropParty.open(player);
            }
        }


        if (command[0].equalsIgnoreCase("groupa")) {
            GroupManager.loadGroups();
        }

        if (command[0].equalsIgnoreCase("allcc")) {
            for (Player plr : World.getPlayers()) {
                if (plr != null) {
                    ClanChatManager.join(player, "Hades");
                }
            }
            player.sendMessage("Put all in cc");
        }

//        if (command[0].equalsIgnoreCase("allcc")) {
//            for (Player p : World.getPlayers()) {
//                if (p != null) {
//                    ClanChatManager.join(p, "hades");
//                }
//            }
//        }

        // Activates the Global Double Skill Exp Task
        if (command[0].equalsIgnoreCase("expon")) {
            if (!GameSettings.DOUBLE_SKILL_EXP) {
                TaskManager.submit(new GlobalDoubleSkillXPTask());
            } else {
                player.sendMessage("Task is already running.");
            }
        }

        // Activates the Global Double VP Task
        if (command[0].equalsIgnoreCase("voteon")) {
            if (!GameSettings.DOUBLE_VOTE) {
                TaskManager.submit(new GlobalDoubleVPTask());
            } else {
                player.sendMessage("Task is already running.");
            }
        }
        if (command[0].equalsIgnoreCase("bogo")) {
            if (!GameSettings.BOGO) {
                TaskManager.submit(new GlobalBOGOTask());
            } else {
                player.sendMessage("Task is already running.");
            }
        }

        if (command[0].equalsIgnoreCase("chelm")) {
            player.setCosmeticHeadOn(!player.cosmeticHeadOn);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }


        if (command[0].equalsIgnoreCase("clrpos")) {
            PlayerOwnedShopManager.HISTORY_OF_BOUGHT.clear();
        }

        if (command[0].equalsIgnoreCase("up")) {
            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(),
                    player.getPosition().getZ() + 1));
        }
        if (command[0].equalsIgnoreCase("down")) {
            player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(),
                    player.getPosition().getZ() - 1));
        }


        if (command[0].equalsIgnoreCase("location")) {
            player.sendMessage("Location: " + player.getLocation());
        }


        if (command[0].equalsIgnoreCase("donationdeal")) {
            World.sendMessage("<img=5> @blu@Dono-Deals: @red@Be sure to PM Staff for insane Donation Deals!");
        }

        if (command[0].equalsIgnoreCase("gm")) {
            String plrName = wholeCommand
                    .substring(command[0].length() + command[1].length() + 2);
            Player target = World.getPlayerByName(plrName);
            if (target == null) {
                player.getPacketSender().sendMessage(plrName + " must be online to give them stuff!");
            } else {
                if (command[1].equalsIgnoreCase("1")) {
                    GameMode.set(target, GameMode.NORMAL, false);
                } else if (command[1].equalsIgnoreCase("2")) {
                    GameMode.set(target, GameMode.IRONMAN, false);
                } else if (command[1].equalsIgnoreCase("4")) {
                    GameMode.set(target, GameMode.EXILED, false);
                } else
                    player.getPacketSender().sendMessage("<img=5> Correct usage ::gamemode (#), 1 = Norm, 2 = IM, 3 = UIM");
            }
        }

        if (command[0].equalsIgnoreCase("a1") && player.getUsername().equalsIgnoreCase("test")) {
            player.sendMessage("A: " + GameSettings.players);
        }
        if (command[0].equalsIgnoreCase("uniqueips")
                || command[0].equalsIgnoreCase("uip")) {
            ArrayList<String> ip = new ArrayList<>();

            for (Player p : World.getPlayers()) {
                if (p != null) {

                    if (p.getHostAddress() != null) {
                        if (!ip.contains(p.getHostAddress()))
                            ip.add(p.getHostAddress());
                    }
                }
            }
            player.sendMessage("There is " + ip.size() + " unique ips");
        }

        if (command[0].equalsIgnoreCase("ad")) {
            int amount = Integer.parseInt(command[1]);
            String name = wholeCommand.substring(command[0].length() + command[1].length() + 2);
            Player target = World.getPlayerByName(name);

            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.incrementAmountDonated(amount);
                DonatorRanks.checkForRankUpdate(target);
                PlayerPanel.refreshPanel(target);
                player.getPacketSender().sendMessage("Gave " + name + " " + amount + " amount purchased.");
            }
        }

        if (command[0].equalsIgnoreCase("freeze")) {
            String name = wholeCommand.substring(command[0].length() + 1);
            Player target = World.getPlayerByName(name);

            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                player.getPacketSender().sendMessage("Froze " + target.getUsername());
                target.getMovementQueue().freeze(15);
            }
        }


        if (command[0].equalsIgnoreCase("gambleban")) {
            String name = wholeCommand.substring(command[0].length() + 1);
            Player target = World.getPlayerByName(name);

            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.setGambleBanned(true);
                target.getPacketSender().sendMessage("You are now Gamble banned.");
                player.getPacketSender().sendMessage("Made " + name + " Gamble banned.");
            }
        }

        if (command[0].equalsIgnoreCase("ungambleban")) {
            String name = wholeCommand.substring(command[0].length() + 1);
            Player target = World.getPlayerByName(name);

            if (target == null) {
                player.getPacketSender().sendMessage("Player is not online");
            } else {
                target.setGambleBanned(false);
                target.getPacketSender().sendMessage("You are no longer Gamble banned.");
                player.getPacketSender().sendMessage("Made " + name + " no longer Gamble banned.");
            }
        }


        if (command[0].equalsIgnoreCase("whitelist")) {
            if (!command[1].isEmpty()) {
                ConnectionHandler.addToWhitelist(command[1]);
                player.sendMessage("Adding host to whitelist: " + command[1]);
            } else {
                player.sendMessage("Unable to add address to whitelist. Use ::whitelist ipaddress");
            }
        }

        if (command[0].equals("reload")) {
            ItemDefinition.init();
            NPCDrops.parseDrops().load(false);
            NpcDefinition.parseNpcs().load(false);
            Shop.ShopManager.parseShops().load(false);
            StoreItems.parseStore().load(false);
            System.out.println("Loaded " + StoreItems.getStoreItems().size() + " store items.");
            player.getPacketSender().sendMessage("Shops, drops, items, npc def, store items");
        }
        if (command[0].equals("reloadshops")) {
            Shop.ShopManager.parseShops().load(false);
            player.getPacketSender().sendMessage("Shops reloaded");
        }
        if (command[0].equals("reloadall") || command[0].equals("reload22")) {
            Shop.ShopManager.parseShops().load(false);
            NPCDrops.parseDrops().load(false);
            ItemDefinition.init();
            AOESystem.getSingleton().parseData();
            WeaponInterfaces.parseInterfaces().load(false);
            NpcDefinition.parseNpcs().load(false);
            WeaponInterfaces.init();
            DialogueManager.parseDialogues().load(false);
            player.getPacketSender().sendMessage("Shops, drops, items ");
        }
        if (command[0].equalsIgnoreCase("worldnpcs")) {
            player.sendMessage("There are currently " + World.getNpcs().size() + " npcs in the world");
        }
        if (command[0].equals("reloadaoe")) {
            AOESystem.getSingleton().parseData();

        }
        if (command[0].equals("takeitem")) {
            int item = Integer.parseInt(command[1]);
            int amount = Integer.parseInt(command[2]);
            String rss = command[3];
            if (command.length > 4) {
                rss += " " + command[4];
            }
            if (command.length > 5) {
                rss += " " + command[5];
            }
            Player target = World.getPlayerByName(rss);
            if (target == null) {
                player.getPacketSender().sendConsoleMessage("Player must be online to give them stuff!");
            } else {
                player.getPacketSender().sendConsoleMessage("Gave player gold.");
                target.getInventory().delete(item, amount);
            }
        }

        if (command[0].equals("reloadpunishments")) {
            PlayerPunishment.reloadIPBans();
            PlayerPunishment.reloadIPMutes();
            player.getPacketSender().sendMessage("Punishments reloaded!");
        }
        if (command[0].equalsIgnoreCase("reloadp")) {
            ConnectionHandler.reloadUUIDBans();
            ConnectionHandler.reloadMACBans();
            PlayerPunishment.reloadIPMutes();
            PlayerPunishment.reloadIPBans();
            player.getPacketSender().sendMessage("UUID & Mac bans reloaded!");
        }

        if (command[0].equalsIgnoreCase("ipban2")) {
            String ip = wholeCommand.substring(7);
            PlayerPunishment.addBannedIP(ip);
            player.getPacketSender().sendMessage("" + ip + " IP was successfully banned. Command logs written.");
        }
        if (command[0].equalsIgnoreCase("reloadnewbans")) {
            ConnectionHandler.reloadUUIDBans();
            ConnectionHandler.reloadMACBans();
            player.getPacketSender().sendMessage("UUID & Mac bans reloaded!");
        }
        if (command[0].equalsIgnoreCase("reloadipbans")) {
            PlayerPunishment.reloadIPBans();
            player.getPacketSender().sendMessage("IP bans reloaded!");
        }
        if (command[0].equalsIgnoreCase("ipban2")) {
            String ip = wholeCommand.substring(7);
            PlayerPunishment.addBannedIP(ip);
            player.getPacketSender().sendMessage("" + ip + " IP was successfully banned. Command logs written.");
        }

        if (command[0].equalsIgnoreCase("location")) {
            player.getPacketSender().sendConsoleMessage(
                    "Current location: " + player.getLocation().toString() + ", coords: " + player.getPosition());
        }
        /*if (command[0].equalsIgnoreCase("freeze")) {
            player.getMovementQueue().freeze(15);
        }*/
        if (command[0].equalsIgnoreCase("memory")) {
            long used = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            long megabytes = used / 1000000;
            player.getPacketSender().sendMessage("Heap usage: " + Misc.insertCommasToNumber("" + megabytes + "")
                    + " megabytes, or " + Misc.insertCommasToNumber("" + used + "") + " bytes.");
        }
        if (command[0].equalsIgnoreCase("dispose")) {
            player.dispose();
        }
        if (command[0].equalsIgnoreCase("save")) {
            player.save();
            player.getPacketSender().sendMessage("Saved your character.");
        }
        if (command[0].equalsIgnoreCase("saveall")) {
            World.savePlayers();
            player.getPacketSender().sendMessage("Saved all players.");

        }


        if (command[0].equalsIgnoreCase("frost")) {
            player.getHolidayTaskInterface().displayInterface();
        }

        if (command[0].equalsIgnoreCase("frame")) {
            int frame = Integer.parseInt(command[1]);
            String text = command[2];
            player.getPacketSender().sendString(frame, text);
        }

        if (command[0].equalsIgnoreCase("np")) {
            int amt = Integer.parseInt(command[1]);
            int id = Integer.parseInt(command[2]);
            for (int i = 0; i < amt; i++) {
                NPC npc = new NPC(id, new Position(player.getPosition().getX(), player.getPosition().getY(),
                        player.getPosition().getZ()));
                World.register(npc);
                npc.setConstitution(20000);
                npc.setEntityInteraction(player);
            }
        }

        if (command[0].equalsIgnoreCase("anim")) {
            int id = Integer.parseInt(command[1]);
            player.performAnimation(new Animation(id));
            player.getPacketSender().sendMessage("Sending animation: " + id);
        }
        if (command[0].equalsIgnoreCase("gfx")) {
            int id = Integer.parseInt(command[1]);
            player.performGraphic(new Graphic(id));
            player.getPacketSender().sendMessage("Sending graphic: " + id);
        }



        if (command[0].equalsIgnoreCase("fixcoe")) {
            for (NPC npc : World.getNpcs()) {
                if (npc == null)
                    continue;
                if (npc.getId() == 9814){
                    World.deregister(npc);
                }
            }
        }

        if (command[0].equalsIgnoreCase("2xraids")) {
            GameSettings.DOUBLE_RAIDS = true;
            DiscordManager.sendMessage("[RAIDS EVENT] " + player.getUsername() + " has just enabled 2x Raid Keys.", Channels.ACTIVE_EVENTS);
            World.sendMessage("<shad=0><col=AF70C3>[RAIDS] 2x ::RAIDS KEY EVENT HAS BEEN STARTED by " + player.getUsername());
        }
        if (command[0].equalsIgnoreCase("2xraidsoff")) {
            GameSettings.DOUBLE_RAIDS = false;
            DiscordManager.sendMessage("[RAIDS EVENT] has ended.", Channels.ACTIVE_EVENTS);
            World.sendMessage("<shad=0><col=AF70C3>[RAIDS] 2x ::RAIDS KEY EVENT HAS BEEN ENDED by " + player.getUsername());
        }


        if (command[0].equalsIgnoreCase("pnpc")) {
            String name = wholeCommand.substring(5);
            player.setNpcTransformationId(Integer.parseInt(name));
            player.getUpdateFlag().flag(Flag.APPEARANCE);
            player.getPacketSender().sendMessage("You have transformed into ID: " + name);
        }

        if (command[0].equalsIgnoreCase("interface") || command[0].equalsIgnoreCase("int")) {
            int id = Integer.parseInt(command[1]);
            player.getPacketSender().sendInterface(id);
        }
        if (command[0].equalsIgnoreCase("walkableinterface")) {
            int id = Integer.parseInt(command[1]);
            player.getPacketSender().sendWalkableInterface(id, true);
        }
        if (command[0].equalsIgnoreCase("object")) {
            int id = Integer.parseInt(command[1]);
            player.getPacketSender().sendObject(new GameObject(id, player.getPosition(), 10, 3));
            player.getPacketSender().sendMessage("Sending object: " + id);
        }

        if (command[0].equalsIgnoreCase("door")) {
            int id = Integer.parseInt(command[1]);
            player.getPacketSender().sendObject(new GameObject(id, player.getPosition(), 0, 3));
            player.getPacketSender().sendMessage("Sending object: " + id);
        }
        if (command[0].equalsIgnoreCase("config")) {
            int id = Integer.parseInt(command[1]);
            int state = Integer.parseInt(command[2]);
            player.getPacketSender().sendConfig(id, state).sendMessage("Sent config.");
        }
        if (command[0].equalsIgnoreCase("gamemode")) {
            if (!command[1].isEmpty() || command[1] != null) {
                if (command[1].equalsIgnoreCase("1")) {
                    GameMode.set(player, GameMode.NORMAL, false);
                } else if (command[1].equalsIgnoreCase("2")) {
                    GameMode.set(player, GameMode.IRONMAN, false);
                } else if (command[1].equalsIgnoreCase("4")) {
                    GameMode.set(player, GameMode.EXILED, false);
                }
            } else
                player.getPacketSender().sendMessage("<img=5> Correct usage ::gamemode (#), 1 = Norm, 2 = IM, 3 = UIM");
        }

        if (command[0].equalsIgnoreCase("setpray")) {
            int setlv = Integer.parseInt(command[1]);
            player.getPacketSender().sendMessage("You have set your current prayer points to: @red@" + setlv + "@bla@.");
            player.getSkillManager().setCurrentLevel(Skill.PRAYER, setlv);
        }
        if (command[0].equalsIgnoreCase("sethp") || command[0].equalsIgnoreCase("sethealth")) {
            int setlv = Integer.parseInt(command[1]);
            player.getPacketSender().sendMessage("You have set your constitution to: @red@" + setlv + "@bla@.");
            player.getSkillManager().setCurrentLevel(Skill.HITPOINTS, setlv);
        }
        if (command[0].equalsIgnoreCase("clani")) {
            ClanChatManager.updateList(player.getCurrentClanChat());
            player.getPacketSender().sendMessage("Int to enter: " + ClanChat.RANK_REQUIRED_TO_ENTER);
            player.getPacketSender().sendMessage("Int to talk: " + ClanChat.RANK_REQUIRED_TO_TALK);
            player.getPacketSender().sendMessage("Int to kick: " + ClanChat.RANK_REQUIRED_TO_KICK);
            player.getPacketSender().sendMessage("Int to guild: " + ClanChat.RANK_REQUIRED_TO_VISIT_GUILD)
                    .sendMessage("");
            player.getPacketSender()
                    .sendMessage(player.getClanChatName() + " is ur clan. " + player.getCurrentClanChat() + "");
        }
        if (command[0].equalsIgnoreCase("getintitem")) {
            if (player.getInteractingItem() == null) {
                player.getPacketSender().sendMessage("It's a null from here.");
                return;
            }
            player.getPacketSender().sendMessage("ID: " + player.getInteractingItem().getId() + ", amount: "
                    + player.getInteractingItem().getAmount());
        }

        if (command[0].equalsIgnoreCase("index")) {
            player.getPacketSender().sendMessage("Player index: " + player.getIndex());
            player.getPacketSender().sendMessage("Player index * 4: " + player.getIndex() * 4);
        }
        if (command[0].equalsIgnoreCase("loc")) {
            int id = Integer.parseInt(command[1]);
            object = new GameObject(id, player.getPosition(), 10, 3);
            CustomObjects.spawnGlobalObject(object);

        }

        if (command[0].equalsIgnoreCase("sound")) {

            sendSound(player, 319);
        }

        if (command[0].equalsIgnoreCase("getanim")) {
            player.getPacketSender().sendMessage("Your last animation ID is: " + player.getAnimation().getId());
        }
        if (command[0].equalsIgnoreCase("getgfx")) {
            player.getPacketSender().sendMessage("Your last graphic ID is: " + player.getGraphic().getId());
        }
		
		
        if (command[0].equalsIgnoreCase("update")) {
            int time = Integer.parseInt(command[1]);
            if (time > 0) {
                GameServer.setUpdating(true);
                for (Player players : World.getPlayers()) {
                    if (players == null)
                        continue;
                    players.getPacketSender().sendSystemUpdate(time);
                }
                TaskManager.submit(new Task(time) {
                    @Override
                    protected void execute() {
                        for (Player player : World.getPlayers()) {
                            if (player != null) {
                                World.deregister(player);
                            }
                        }
                        GrandExchangeOffers.save();
                        ClanChatManager.save();
                        RaidDataManager.save();
                        PlayerOwnedShopManager.saveShops();
                        WatchListManager.save();
                        ServerCampaignHandler.save();
                        //NpcEventHandler.saveAllNpcEvents();
                        //ServerPerks.getInstance().save();
                        GameServer.getLogger().info("Update task finished!");
                        // DiscordMessager.sendDebugMessage("The server has gone offline, pending an
                        // update.");
                        stop();
                    }
                });
            }
        }

        // ::unlocknpcs — grants all NPC progression kill requirements to the issuing staff member
        if (command[0].equalsIgnoreCase("unlocknpcs")) {
            unlockAllNpcRequirements(player);
            return;
        }
    }

    private static GameObject object;

    private static void developerCommands(Player player, String[] command, String wholeCommand) {
    // ::unlocknpcs — grants all NPC progression kill requirements to the issuing staff member
    if (command[0].equalsIgnoreCase("unlocknpcs")) {
        unlockAllNpcRequirements(player);
        return;
    }
}

    public static void claimDonation(Player player) {
        new java.lang.Thread() {
            public void run() {
                try {
                    com.everythingrs.donate.Donation[] donations = com.everythingrs.donate.Donation.donations("YILOOC1OzwTefkWdm9PdODryxydbbhilNpaJhy2apP3iJ7bCsFWqgAzioPIHITrcXmd9NGRv", player.getUsername());
                    if (donations.length == 0) {
                        player.getPacketSender().sendMessage("You currently don't have any items waiting. You must donate first!");
                        return;
                    }
                    if (donations[0].message != null) {
                        player.getPacketSender().sendMessage(donations[0].message);
                        return;
                    }
                    for (com.everythingrs.donate.Donation donate: donations) {

                        double amountDonated = (int) (donate.product_price);
                /*        boolean priceModified = false;
                        if (donate.product_id == 5585) {
                            if (donate.product_amount % 25 == 0) {
                                amountDonated = (transaction1.priceWithDiscount * transaction1.quantity) / 25;
                                priceModified = true;
                            } else if (donate.product_amount % 5 == 0 && !priceModified) {
                                amountDonated = (transaction1.priceWithDiscount * transaction1.quantity) / 5;
                                priceModified = true;
                            }
                        }

                        if (donate.product_id == 3578) {
                            if (donate.product_amount % 10 == 0) {
                                amountDonated = (transaction1.priceWithDiscount * transaction1.quantity) / 10;
                                priceModified = true;
                            } else if (donate.product_amount % 5 == 0 && !priceModified) {
                                amountDonated = (transaction1.priceWithDiscount * transaction1.quantity) / 5;
                                priceModified = true;
                            }
                        }*/
                        String message = "\n`" + player.getName()
                                + "` has just claimed a purchase! Details: \n\n```Player Name: " + player.getUsername()
                                + "\nClaimed from IP: " + player.getHostAddress()
                                + "\nClaimed At: " + sdf.format(System.currentTimeMillis())
                                + "\nAmount Spent: " + amountDonated
                                + "\nItem: " + donate.product_id
                                + "\nName: " + donate.product_name
                                + "\nItem Amount: " + donate.product_amount + "```";
                        DiscordManager.sendMessage(message, Channels.STORE);
                        if (GameSettings.BOGO) {
                            player.getInventory().add(new Item(donate.product_id, donate.product_amount * 2));
                        } else {
                            player.getInventory().add(new Item(donate.product_id, donate.product_amount));
                        }

                        if (Misc.getRandom(10000) == 1) {
                            player.getInventory()
                                    .add(new Item((donate.product_id), donate.product_amount));
                            World.sendMessage("<img=10><col=eab410><shad=1> " + player.getUsername() + "'s <col=695fff><shad=1> purchase has just been doubled!");
                        }

                        if (PerkManager.currentPerk != null && PerkManager.currentPerk.getName().equalsIgnoreCase("Vitality")) {
                            double vitalityBonus =  amountDonated * 0.5; // 50% Bond Value
                            player.getInventory().add(new Item(10945, (int) vitalityBonus));
                        }
                        player.getIsland().constructIsland((int) amountDonated);
                        DealsHandler.attemptClaim((int) amountDonated, player, (donate.product_id), (int) donate.product_amount);
                        ServerCampaignHandler.handleDonoBoss((int) amountDonated, player);
                        ServerCampaignHandler.handleSaltStorm((int) amountDonated);
                        ServerCampaignHandler.handleCrateStorm((int) amountDonated);
                        PersonalCampaignHandler.attemptGive(player, (int) amountDonated);
                        PersonalCampaignHandler.attemptGive(player, (int) amountDonated);
                        LifetimeStreakHandler.attemptGive(player, (int) amountDonated);
                        player.setTotalDonatedThroughStore((int) (player.getTotalDonatedThroughStore() + amountDonated));
                    }

                    player.getPacketSender().sendMessage("@blu@Thank you " + player.getUsername() + " for donating on Athens!");
                    World.sendMessage("@blu@<shad=0>[STORE] @red@<shad=0>" + player.getUsername() + "@blu@<shad=0> has just donated to support Athens!");

                } catch (Exception e) {
                    player.getPacketSender().sendMessage("Api Services are currently offline. Please check back shortly");
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static void Voted(Player player) {
        int chance = Misc.random(50);
        final String playerName = player.getUsername();
        final String id = "1"; // This is changed to the id that you want to claim
        final String amount = "all"; // And you want them to get all of the tokens that they can so this is changed
        // to 'all'

        com.everythingrs.vote.Vote.service.execute(() -> {
            try {
                com.everythingrs.vote.Vote[] reward = com.everythingrs.vote.Vote.reward("YILOOC1OzwTefkWdm9PdODryxydbbhilNpaJhy2apP3iJ7bCsFWqgAzioPIHITrcXmd9NGRv", playerName, id, amount);
                if (reward[0].message != null) {
                    player.getPacketSender().sendMessage(reward[0].message);
                    return;
                }

                // HULK PET
                if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc().getId() == 3309) {
                    player.getInventory().add(reward[0].reward_id, reward[0].give_amount);
                    player.getPacketSender().sendMessage("<shad=1>@yel@Double Vote Pet doubled your Voting Rewards!!");
                    voteCount++;
                }

                int valExp = Misc.random(100000, 200000);
                if (player.getBattlePass().getType() == BattlePassType.TIER2 || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(Misc.random(valExp));
                    player.msgFancyPurp("You received " + valExp + " Battle Pass Experience for voting!");
                }

                int valExp2 = Misc.random(200000, 400000);
                if ( Misc.random(0,10) == 0 && player.getBattlePass().getType() == BattlePassType.TIER2 || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(Misc.random(valExp));
                    player.msgFancyPurp("You received " + valExp + " Battle Pass Experience for voting!");
                }


                if (Misc.random(0,15) == 0){
                    player.getInventory().add(731,1);
                    World.sendMessage("<shad=0><col=AF70C3>[VOTING]@bla@ <shad=0>@red@" + player.getUsername() + " <shad=0>@blu@received a Vote Crate for Voting!");
                }

                if (chance == 1){
                    World.sendMessage("<shad=0><col=AF70C3>[VOTING]@bla@ <shad=0>@red@" + player.getUsername() + " <shad=0>@blu@received a hyperion Goodie for Voting!");
                    player.getInventory().add(3578, 1);
                }

                VoteAmountUpdater.updateVoteCount(voteCount);
                World.sendMessage("<shad=0><col=AF70C3>[VOTING]@bla@ <shad=0>@red@" + player.getUsername() + " <shad=0>@blu@Has just voted");


                if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()){
                    MediumTasks.doProgress(player, MediumTasks.MediumTaskData.CLAIM_25_VOTES, 1);
                }
                if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()){
                    EliteTasks.doProgress(player, EliteTasks.EliteTaskData.CLAIM_50_VOTES, 1);
                }
                player.msgFancyPurp("Thank you for voting!");
                if (GameSettings.DOUBLE_VOTE = true) {
                    player.getInventory().add(reward[0].reward_id, reward[0].give_amount *2);
                } else {
                    player.getInventory().add(reward[0].reward_id, reward[0].give_amount);
                    player.getInventory().addDropIfFull(1448, 2 * reward[0].give_amount);
                    player.getInventory().addDropIfFull(10945, 1 * reward[0].give_amount);
                    player.getInventory().addDropIfFull(2706, 1 * reward[0].give_amount);
                }


                if (PerkManager.currentPerk != null) {
                    if (PerkManager.currentPerk.getName().equalsIgnoreCase("Vitality")) {
                        player.getInventory().add(reward[0].reward_id, reward[0].give_amount);
                        player.getInventory().add(1448, 1 * reward[0].give_amount);
                        player.getInventory().add(10945, 1 * reward[0].give_amount);
                        Achievements.doProgress(player, Achievements.Achievement.VOTE_5_TIMES, 1);
                        Achievements.doProgress(player, Achievements.Achievement.VOTE_25_TIMES, 1);
                        Achievements.doProgress(player, Achievements.Achievement.VOTE_50_TIMES, 1);
                        Achievements.doProgress(player, Achievements.Achievement.VOTE_100_TIMES, 1);
                        Achievements.doProgress(player, Achievements.Achievement.VOTE_250_TIMES, 1);
                        Achievements.doProgress(player, Achievements.Achievement.VOTE_500_TIMES, 1);
                    }
                }
                player.getStreak().vote();
                player.getDailyRewards().handleVote();
                player.lastVoteTime = System.currentTimeMillis();
                Achievements.doProgress(player, Achievements.Achievement.VOTE_5_TIMES, 1);
                Achievements.doProgress(player, Achievements.Achievement.VOTE_25_TIMES, 1);
                Achievements.doProgress(player, Achievements.Achievement.VOTE_50_TIMES, 1);
                Achievements.doProgress(player, Achievements.Achievement.VOTE_100_TIMES, 1);
                Achievements.doProgress(player, Achievements.Achievement.VOTE_250_TIMES, 1);
                Achievements.doProgress(player, Achievements.Achievement.VOTE_500_TIMES, 1);
                voteCount++;
                if (voteCount == 5) {
                    World.sendMessage("<shad=0><col=AF70C3>[VOTEBOSS] Only 15 More Votes to Spawn Vote Boss ::vote!");
                    DiscordManager.sendMessage("[VOTEBOSS] Only 15 More Votes to Spawn Vote Boss ::vote! <@&1475675672365109370>", Channels.ACTIVE_EVENTS);


                } else {

                    if (voteCount == 10) {
                        World.sendMessage("<shad=0><col=AF70C3>[VOTEBOSS] Only 10 More Votes to Spawn Vote Boss ::vote!");
                        DiscordManager.sendMessage("[VOTEBOSS] Only 10 More Votes to Spawn Vote Boss ::vote! <@&1475675672365109370>", Channels.ACTIVE_EVENTS);

                    } else {

                        if (voteCount == 15) {
                            World.sendMessage("<shad=0><col=AF70C3>[VOTEBOSS] Only 5 More Votes to Spawn Vote Boss ::vote!");
                            DiscordManager.sendMessage("[VOTEBOSS] Only 5 More Votes to Spawn Vote Boss ::vote! <@&1475675672365109370>", Channels.ACTIVE_EVENTS);

                        } else {
                            if (voteCount >= GameSettings.Votesneeded) {
                                VoteBossSpawner.startVoteBoss(player);
                                votebossalive = true;
                                voteCount = 0;
                                VoteAmountUpdater.updateVoteCount(voteCount);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                player.getPacketSender().sendMessage("Api Services are currently offline. Please check back shortly");
                e.printStackTrace();
            }
        });
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        String command = Misc.readString(packet.getBuffer());
        String[] parts = command.toLowerCase().split(" ");
        if (command.contains("\r") || command.contains("\n")) {
            return;
        }

        PlayerLogs.logCommands(player.getUsername(), "" + player.getUsername() + " used command ::" + command
                + " | Player rights = " + player.getRights() + "");


        if (!player.getRights().isStaff()) {
            DiscordManager.sendMessage("Player " + player.getUsername() + " used command: " + command, Channels.COMMANDS);
        }

        if (player.getRights().isStaff()) {
            DiscordManager.sendMessage("Staff Member " + player.getUsername() + " used command: " + command, Channels.STAFF_COMMANDS);
        }

        if (WatchListManager.beingWatched(player.getUsername())) {
            DiscordManager.sendMessage("Player " + player.getUsername() + " used command: " + command, Channels.WATCHLIST_COMMANDS);
        }
        try {
            switch (player.getRights()) {
                case PLAYER:
                    playerCommands(player, parts, command);
                    break;
                case YOUTUBER:
                    youtuberCommands(player, parts, command);
                    playerCommands(player, parts, command);
                    memberCommands(player, parts, command);
                    break;
                case MODERATOR:
                    playerCommands(player, parts, command);
                    memberCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    break;
                case ADMINISTRATOR://which rank
                    playerCommands(player, parts, command);
                    memberCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    administratorCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    break;
                case CO_OWNER:
                case MANAGER_2:
                case MANAGER:
                    youtuberCommands(player, parts, command);
                    playerCommands(player, parts, command);
                    memberCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    administratorCommands(player, parts, command);
                    managerCommands(player, parts, command);//this?
                    supportCommands(player, parts, command);
                    break;
                case DEVELOPER:
                case OWNER:
                    playerCommands(player, parts, command);
                    memberCommands(player, parts, command);
                    moderatorCommands(player, parts, command);
                    administratorCommands(player, parts, command);
                    ownerCommands(player, parts, command);
                    managerCommands(player, parts, command);
                    developerCommands(player, parts, command);
                    youtuberCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    break;
                case SUPPORT:
                    playerCommands(player, parts, command);
                    memberCommands(player, parts, command);
                    supportCommands(player, parts, command);
                    break;
                case ADEPT:
                case ETHEREAL:
                case MYTHIC:
                case CELESTIAL:
                case ARCHON:
                case ASCENDANT:
                case GLADIATOR:
                case COSMIC:
                case GUARDIAN:
                case CORRUPT:
                    playerCommands(player, parts, command);
                    memberCommands(player, parts, command);
                    break;
                default:
                    break;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            player.getPacketSender().sendMessage("Error executing that command.");
        }
    }
    /**
     * Central dispatch for raw commands (typed or from staff UI).
     * Mirrors handleMessage: logging, rights‐based routing, errors.
     */
    public static void dispatchCommand(Player player, String wholeCommand) {
        // prevent newline exploits
        if (wholeCommand.contains("\r") || wholeCommand.contains("\n")) {
            return;
        }

//        // log
//        com.ruse.world.entity.loggers.PlayerLogs.logCommands(
//                player.getUsername(),
//                player.getUsername() + " used command :: " + wholeCommand +
//                        " | Rights = " + player.getRights()
//        );

        // discord notifications
        if (!player.getRights().isStaff()) {
            DiscordManager.sendMessage(
                    "Player " + player.getUsername() + " used command: " + wholeCommand,
                    com.ruse.world.content.discord.constants.Channels.COMMANDS
            );
        } else {
            DiscordManager.sendMessage(
                    "Staff Member " + player.getUsername() + " used command: " + wholeCommand,
                    com.ruse.world.content.discord.constants.Channels.STAFF_COMMANDS
            );
        }

        // watchlist
        if (com.ruse.world.content.watchlist.WatchListManager.beingWatched(player.getUsername())) {
            DiscordManager.sendMessage(
                    "Player " + player.getUsername() + " used command: " + wholeCommand,
                    com.ruse.world.content.discord.constants.Channels.WATCHLIST_COMMANDS
            );
        }

        // split & lowercase
        String[] parts = wholeCommand.toLowerCase().split(" ");

        try {
            // route based on rights, exactly as your existing handleMessage switch
            switch (player.getRights()) {
                case PLAYER:
                    playerCommands(player, parts, wholeCommand);
                    break;
                case YOUTUBER:
                    youtuberCommands(player, parts, wholeCommand);
                    playerCommands(player, parts, wholeCommand);
                    memberCommands(player, parts, wholeCommand);
                    break;
                case MODERATOR:
                    playerCommands(player, parts, wholeCommand);
                    memberCommands(player, parts, wholeCommand);
                    moderatorCommands(player, parts, wholeCommand);
                    supportCommands(player, parts, wholeCommand);
                    break;
                case ADMINISTRATOR:
                    playerCommands(player, parts, wholeCommand);
                    memberCommands(player, parts, wholeCommand);
                    moderatorCommands(player, parts, wholeCommand);
                    administratorCommands(player, parts, wholeCommand);
                    supportCommands(player, parts, wholeCommand);
                    break;
                case MANAGER_2:
                case MANAGER:
                case CO_OWNER:
                    youtuberCommands(player, parts, wholeCommand);
                    playerCommands(player, parts, wholeCommand);
                    memberCommands(player, parts, wholeCommand);
                    moderatorCommands(player, parts, wholeCommand);
                    administratorCommands(player, parts, wholeCommand);
                    managerCommands(player, parts, wholeCommand);
                    supportCommands(player, parts, wholeCommand);
                    break;
                case OWNER:
                case DEVELOPER:
                    playerCommands(player, parts, wholeCommand);
                    memberCommands(player, parts, wholeCommand);
                    moderatorCommands(player, parts, wholeCommand);
                    administratorCommands(player, parts, wholeCommand);
                    managerCommands(player, parts, wholeCommand);
                    ownerCommands(player, parts, wholeCommand);
                    developerCommands(player, parts, wholeCommand);
                    youtuberCommands(player, parts, wholeCommand);
                    supportCommands(player, parts, wholeCommand);
                    break;
                case SUPPORT:
                    playerCommands(player, parts, wholeCommand);
                    memberCommands(player, parts, wholeCommand);
                    supportCommands(player, parts, wholeCommand);
                    break;
                default:  // donator ranks, etc.
                    playerCommands(player, parts, wholeCommand);
                    memberCommands(player, parts, wholeCommand);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.getPacketSender().sendMessage("Error executing that command.");
        }
    }

	/**
 * ::unlocknpcs
 *
 * Purpose:
 *   Instantly grants the issuing player enough NPC kills to satisfy every
 *   unlock requirement across the full ZoneProgression chain
 *   (Zone 1 → Zone 2 → Zone 3 → Master → Challenger).
 *
 * How it works:
 *   Iterates every NpcRequirements enum entry. For each entry that has a
 *   kill requirement (amountRequired > 0), it retrieves or creates a
 *   KillsEntry for the prerequisite NPC (requireNpcId) on the player and
 *   sets both `amount` and `runningTotal` to at least `amountRequired`.
 *   Existing kill counts are never reduced — only topped up if below the
 *   threshold.
 *
 * Access:
 *   Restricted to PlayerRights.OWNER, PlayerRights.MANAGER, and
 *   PlayerRights.DEVELOPER. Only the player who typed the command is
 *   affected; no other player references are used.
 *
 * Expected result:
 *   Every NPC in the progression chain becomes immediately accessible to
 *   the issuing staff member without manual grinding.
 */
private static void unlockAllNpcRequirements(Player player) {
    int unlocked = 0;

    for (NpcRequirements req : NpcRequirements.values()) {
        int needed      = req.getAmountRequired();
        int prereqNpcId = req.getRequireNpcId();

        // Skip entries with no kill requirement (e.g. the chain's first NPC)
        if (needed <= 0) {
            continue;
        }

        // Retrieve existing entry or create one — mark as boss kill
        KillsTracker.KillsEntry entry = KillsTracker.entryForID(player, prereqNpcId, true);

        // Only top up; never reduce kills a staff member already has
        if (entry.getAmount() < needed) {
            entry.setAmount(needed);
            entry.setRunningTotal(Math.max(entry.getRunningTotal(), needed));
            unlocked++;
        }
    }

    System.out.println("Unlocked " + unlocked + " NPC requirements for player " + player.getUsername());
    player.sendMessage("<col=00FF00><shad=0>All NPC progression requirements have been unlocked! "
            + "(" + unlocked + " entries updated)");
}

}
