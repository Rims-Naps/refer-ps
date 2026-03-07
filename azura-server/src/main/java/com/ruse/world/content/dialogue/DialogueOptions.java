package com.ruse.world.content.dialogue;

import com.ruse.GameSettings;
import com.ruse.donation.DonatorRanks;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.BonusExperienceTask;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Shop.ShopManager;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.input.impl.*;
import com.ruse.util.Misc;
import com.ruse.util.RandomUtility;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.ExpLamps.ExpLamps;
import com.ruse.world.content.Gambling.FlowersData;
import com.ruse.world.content.PrayerMinigame.PrayerMinigame;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.bossfights.impl.*;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.dailytasks_new.DailyTasks;
import com.ruse.world.content.dialogue.impl.AgilityTicketExchange;
import com.ruse.world.content.dialogue.impl.Mandrith;
import com.ruse.world.content.dialogue.impl.Tutorial;
import com.ruse.world.content.groupironman.GroupManager;
import com.ruse.world.content.minigames.impl.*;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.quests.impl.Christmas2023;
import com.ruse.world.content.skill.impl.construction.Construction;
import com.ruse.world.content.skill.impl.mining.Mining;
import com.ruse.world.content.skill.impl.old_dungeoneering.Dungeoneering;
import com.ruse.world.content.skill.impl.old_dungeoneering.DungeoneeringFloor;
import com.ruse.world.content.skill.impl.slayer.BossSlayerDialogues;
import com.ruse.world.content.skill.impl.slayer.Slayer;
import com.ruse.world.content.skill.impl.slayer.SlayerDialogues;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.summoning.SummoningTab;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.npc.NpcAggression;
import com.ruse.world.entity.impl.player.Player;



public class DialogueOptions {


    public static int FIRST_OPTION_OF_FIVE = 2494;
    public static int SECOND_OPTION_OF_FIVE = 2495;
    public static int THIRD_OPTION_OF_FIVE = 2496;
    public static int FOURTH_OPTION_OF_FIVE = 2497;
    public static int FIFTH_OPTION_OF_FIVE = 2498;
    public static int FIRST_OPTION_OF_FOUR = 2482;
    public static int SECOND_OPTION_OF_FOUR = 2483;
    public static int THIRD_OPTION_OF_FOUR = 2484;
    public static int FOURTH_OPTION_OF_FOUR = 2485;
    public static int FIRST_OPTION_OF_THREE = 2471;
    public static int SECOND_OPTION_OF_THREE = 2472;
    public static int THIRD_OPTION_OF_THREE = 2473;
    public static int FIRST_OPTION_OF_TWO = 2461;
    public static int SECOND_OPTION_OF_TWO = 2462;

    public static void handle(Player player, int id) {
        if (player.getRights() == PlayerRights.DEVELOPER) {
            player.getPacketSender()
                    .sendMessage("Dialogue button id: " + id + ", action id: " + player.getDialogueActionId())
                    .sendConsoleMessage("Dialogue button id: " + id + ", action id: " + player.getDialogueActionId());
        }
        if (Effigies.handleEffigyAction(player, id)) {
            return;
        }
        //System.out.println("Button click id: "+id);
        //System.out.println("Player dialogue aciton id: "+player.getDialogueActionId());
        if (id == FIRST_OPTION_OF_FIVE /*|| id ==  FIRST_OPTION_OF_FOUR || id ==  FIRST_OPTION_OF_THREE || id ==  FIRST_OPTION_OF_TWO */) {
            switch (player.getDialogueActionId()) {
                case 5005:

                    break;





                case 4000:
                    if (player.getMonsteressence() <=0){
                        player.getPacketSender().sendMessage("You dont have any Monster Essence in your Pouch!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    int monstershards = player.getMonsteressence();
                    if (player.getInventory().getFreeSlots() <= 1) {
                        player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(19062, monstershards);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setMonsteressence(0);
                    break;





                case 9907: // Minigames, Option 1//2322, 5028
                    player.sendMessage("YAYAYAYAYAYAY");
                    break;
                case 9910: // Donation zones, Option 1
                    TeleportHandler.teleportPlayer(player, new Position(2527, 3935, 0), TeleportType.ANCIENT);
                    player.sendMessage("Welcome to the Cash Zone!");
                    break;
                case 9911: // Donation zones, Option 1
                    if (player.getAmountDonated() < DonatorRanks.GLADIATOR_AMOUNT) {
                        player.sendMessage("You don't meet the requirements to access this Zone");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2839, 2784, 0), TeleportType.ANCIENT);
                    player.sendMessage("Welcome to the Godly Zone!");
                    break;
                case 101:
                    player.getPlayerOwnedShopManager().openMain();
                    break;

                case 831:
                    break;
                case 2461:
                    break;
                case 216:
                    break;
                case 44:
                    break;
                case 150:
                    break;
                case 151:
                    break;
                case 153:
                    break;
                case 152:
                    break;

                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(3420, 3510),
                            TeleportType.ANCIENT);
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(3235, 3295),
                            TeleportType.ANCIENT);
                    break;
                case 9:
                    DialogueManager.start(player, 16);
                    break;
                case 10:
                    break;
                case 11:
                    Scoreboards.open(player, Scoreboards.TOP_KC);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(3087, 3517),
                            TeleportType.ANCIENT);
                    break;
                case 1712:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.sendMessage("You have Chosen Hole Number: " + player.getDoorPick().getDoorSelected());
                    player.getDoorPick().openDoor();
                    break;
                case 13:
                    player.setDialogueActionId(78);
                    DialogueManager.start(player, 124);
                    break;
                case 14: // Edgeville
                    TeleportHandler.teleportPlayer(player, new Position(1859 + Misc.getRandom(1), 5237 + Misc.getRandom(1)),
                            TeleportType.ANCIENT);
                    break;
                case 15:
                    player.getPacketSender().sendInterfaceRemoval();
                    int total = player.getSkillManager().getMaxLevel(Skill.ATTACK)
                            + player.getSkillManager().getMaxLevel(Skill.STRENGTH);
                    boolean has99 = player.getSkillManager().getMaxLevel(Skill.ATTACK) >= 99
                            || player.getSkillManager().getMaxLevel(Skill.STRENGTH) >= 99;
                    if (total < 130 && !has99) {
                        player.getPacketSender().sendMessage("");
                        player.getPacketSender().sendMessage("You do not meet the requirements of a Warrior.");
                        player.getPacketSender()
                                .sendMessage("You need to have a total Attack and Strength level of at least 140.");
                        player.getPacketSender().sendMessage("Having level 99 in either is fine aswell.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2855, 3543),
                            TeleportType.ANCIENT);
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEGINNER_SLAYER);
                    break;
                case 36:
                    TeleportHandler.teleportPlayer(player, new Position(2871, 5318, 2),
                            TeleportType.ANCIENT);
                    break;
                case 38:
                    TeleportHandler.teleportPlayer(player, new Position(2273, 4681),
                            TeleportType.ANCIENT);
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(3476, 9502),
                            TeleportType.ANCIENT);
                    break;
                case 48:
                    break;
                case 195:
                    TeleportHandler.teleportPlayer(player, new Position(3088, 3506), TeleportType.RING_TELE);
                    player.getClickDelay().reset();
                    break;
                case 60:
                case 202:
                    player.setDialogueActionId(61);
                    DialogueManager.start(player, 102);
                    break;
                case 67:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
                                .equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
                                    .setDungeoneeringFloor(DungeoneeringFloor.FIRST_FLOOR);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
                                    .sendMessage("The party leader has changed floor.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
                                .equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(1);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
                                    .sendMessage("The party leader has changed complexity.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
                case 78:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (!player.getUnlockedLoyaltyTitles()[LoyaltyProgramme.LoyaltyTitles.VETERAN.ordinal()]) {
                        player.getPacketSender().sendMessage(
                                "You must have unlocked the 'Exiled' Loyalty Title in order to buy this cape.");
                        return;
                    }
                    boolean usePouch = false;
                    if (!usePouch && player.getInventory().getAmount(ItemDefinition.COIN_ID) < 75000000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    if (usePouch) {
                    } else
                        player.getInventory().delete(ItemDefinition.COIN_ID, 75000000);
                    player.getInventory().add(14021, 1);
                    player.getPacketSender().sendMessage("You have purchased a Exiled cape.");
                    DialogueManager.start(player, 122);
                    player.setDialogueActionId(76);
                    break;
                case 0: // Novite longsword
                    if (player.getClickDelay().elapsed(1000)) {
                        PlayerPanel.refreshPanel(player);
                        DialogueManager.start(player, Tutorial.get(player, 0));
                        player.getClickDelay().reset();
                    }
                    break;
                case 88:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getAmountDonated() < DonatorRanks.ADEPT_AMOUNT) {
                        player.getPacketSender().sendMessage("You need to be a member to teleport to this zone.")
                                .sendMessage("To become a member, use the command ::store and browse our store.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, GameSettings.MEMBER_ZONE,
                            TeleportType.ANCIENT);
                    break;
            }
        } else if (id == SECOND_OPTION_OF_FIVE) {
            switch (player.getDialogueActionId()) {
                case 4000:
                    if (player.getFireessence() <=0){
                        player.getPacketSender().sendMessage("You dont have any Fire Essence in your Pouch!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    int fireessence = player.getFireessence();
                    if (player.getInventory().getFreeSlots() <= 1) {
                        player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(11054, fireessence);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setFireessence(0);
                    break;
                case 101:
                    player.getPlayerOwnedShopManager().openEditor();
                    break;
                case 9910: // Donation zones, Option 2
                    TeleportHandler.teleportPlayer(player, new Position(2464, 3997, 0), TeleportType.ANCIENT);
                    player.sendMessage("Welcome to the Superior Zone!");
                    break;

                case 9907: // Minigames, Option 2
                    TeleportHandler.teleportPlayer(player, new Position(1954, 5011,0), TeleportType.ANCIENT);
                    break;


                case 9924:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(1));
                    player.getPacketSender().sendEnterAmountPrompt("How many Pvm tickets would you like to withdraw?");
                    break;
                case 9925:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(5));
                    player.getPacketSender().sendEnterAmountPrompt("How many Pet fragments would you like to withdraw?");
                    break;
                case 831:
                    break;
                case 150:
                    break;
                case 151:
                    break;

                case 153:
                    break;
                case 152:
                    break;
                case 0:
                    TeleportHandler.teleportPlayer(player,
                            new Position(3557 + (Misc.getRandom(2)), 9946 + Misc.getRandom(2)),
                            TeleportType.ANCIENT);
                    break;
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(2933, 9849),
                            TeleportType.ANCIENT);
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(2802, 9148),
                            TeleportType.ANCIENT);
                    break;
                case 10:
                    player.setDialogueActionId(59);
                    DialogueManager.start(player, 100);
                    break;
                case 11:
                    Scoreboards.open(player, Scoreboards.TOP_DONATION);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(2980 + Misc.getRandom(3), 3596 + Misc.getRandom(3)),
                            TeleportType.ANCIENT);
                    break;

                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(2041, 5242, 0), TeleportType.NORMAL);
                    break;
                case 15:
                    TeleportHandler.teleportPlayer(player, new Position(2663 + Misc.getRandom(1), 2651 + Misc.getRandom(1)),
                            TeleportType.ANCIENT);
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.MEDIUM_SLAYER);
                    // System.out.println("CALLED");
                    break;
                case 30:
                    player.getPacketSender().sendString(36030,
                            "Current Points:   " + player.getPointsHandler().getSlayerPoints());
                    //  player.getPacketSender().sendInterface(36000);
                    break;
                case 36:
                    TeleportHandler.teleportPlayer(player, new Position(1908, 4367),
                            TeleportType.ANCIENT);
                    break;
                case 38:
                    DialogueManager.start(player, 70);
                    player.setDialogueActionId(39);
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(2839, 9557),
                            TeleportType.ANCIENT);
                    break;
                case 48:
                    break;
                case 195:
                    TeleportHandler.teleportPlayer(player, new Position(2918, 3176), TeleportType.RING_TELE);
                    player.getClickDelay().reset();
                    break;
                case 78:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (!player.getUnlockedLoyaltyTitles()[LoyaltyProgramme.LoyaltyTitles.MAXED.ordinal()]) {
                        player.getPacketSender()
                                .sendMessage("You must have unlocked the 'Maxed' Loyalty Title in order to buy this cape.");
                        return;
                    }
                    if (player.getInventory().getAmount(ItemDefinition.COIN_ID) < 50000000) {
                        player.getPacketSender().sendMessage("You do not have enough coins.");
                        return;
                    }
                    player.getInventory().delete(ItemDefinition.COIN_ID, 50000000);
                    player.getInventory().add(14019, 1);
                    player.getPacketSender().sendMessage("You have purchased a Max cape.");
                    break;
                case 60:
                case 202:
                    player.setDialogueActionId(62);
                    DialogueManager.start(player, 102);
                    break;

                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
                                .equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(2);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
                                    .sendMessage("The party leader has changed complexity.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
                case 87: // Novite Rapier
                    if (player.getClickDelay().elapsed(1000)) {
                        Difficulty.set(player, Difficulty.REGULAR, true);
                        PlayerPanel.refreshPanel(player);
                        DialogueManager.start(player, Tutorial.get(player, 0));
                        player.getClickDelay().reset();
                    }
                    break;
                case 88:
                    ShopManager.getShops().get(80).open(player);
                    break;
            }
        } else if (id == THIRD_OPTION_OF_FIVE) {
            switch (player.getDialogueActionId()) {
                case 4000:
                    if (player.getWateressence() <=0){
                        player.getPacketSender().sendMessage("You dont have any Water Essence in your Pouch!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    int wateressence = player.getWateressence();
                    if (player.getInventory().getFreeSlots() <= 1) {
                        player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(11056, wateressence);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setWateressence(0);
                    break;
                case 3060: //BEAST HUNTER BOSS 3
                    if (player.getSlayer().getSlayerTask().getNpcId() != 2018){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().endFight();
                    }
                    player.setBossFight(new BasiliskFight(player));
                    player.getBossFight().begin();
                    player.sendMessage("You travel to Basilisk's Island");
                    break;
                case 3061: //BEAST HUNTER BOSS 8
                    if (player.getSlayer().getSlayerTask().getNpcId() != 59){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2527, 3935, 0), TeleportType.ANCIENT);
                    player.sendMessage("You travel to Boss 7!");
                    break;
                case 101:
                    player.getPlayerOwnedShopManager().claimEarnings();
                    break;

                case 9907: // Minigames, Option 3 2195, 5037
                    TeleportHandler.teleportPlayer(player, new Position(3080, 3496, 0), TeleportType.ANCIENT);
                    break;
                case 9910: // Donation zones, Option 1
                    TeleportHandler.teleportPlayer(player, new Position(2534, 3995, 0), TeleportType.ANCIENT);
                    player.sendMessage("Welcome to the Investor Zone!");
                    break;

                case 9924:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(2));
                    player.getPacketSender().sendEnterAmountPrompt("How many Afk tickets would you like to withdraw?");
                    break;
                case 9925:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(6));
                    player.getPacketSender().sendEnterAmountPrompt("How many Instance tokens would you like to withdraw?");
                    break;
                case 831:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 150:
                    break;
                case 151:
                    break;
                case 153:
                    break;
                case 152:

                    break;
                case 0:
                    TeleportHandler.teleportPlayer(player,
                            new Position(3204 + (Misc.getRandom(2)), 3263 + Misc.getRandom(2)),
                            TeleportType.ANCIENT);
                    break;
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(3259, 3228),
                            TeleportType.ANCIENT);
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(2793, 2773),
                            TeleportType.ANCIENT);
                    break;
                case 10:
                    DialogueManager.start(player, Mandrith.getDialogue(player));
                    break;
                case 11:
                    Scoreboards.open(player, Scoreboards.TOP_TOTAL_EXP);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(3239 + Misc.getRandom(2), 3619 + Misc.getRandom(2)),
                            TeleportType.ANCIENT);
                    break;
                case 78:
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(2132, 5256),
                            TeleportType.ANCIENT);
                    break;
                case 15:
                    TeleportHandler.teleportPlayer(player, new Position(3368 + Misc.getRandom(5), 3267 + Misc.getRandom(3)),
                            TeleportType.ANCIENT);
                    break;
                case 29:
                    SlayerMaster.changeSlayerMaster(player, SlayerMaster.ELITE_SLAYER);
                    break;
                case 36:
                    player.setDialogueActionId(37);
                    DialogueManager.start(player, 70);
                    break;
                case 38:
                    TeleportHandler.teleportPlayer(player, new Position(2547, 9448),
                            TeleportType.ANCIENT);
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(2368, 4949, 0),
                            TeleportType.ANCIENT);
                    break;
                case 48:
                    break;
                case 195:
                    TeleportHandler.teleportPlayer(player, new Position(3105, 3251), TeleportType.RING_TELE);
                    player.getClickDelay().reset();
                    break;
                case 60:
                case 202:
                    player.setDialogueActionId(63);
                    DialogueManager.start(player, 102);
                    break;
                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
                                .equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(3);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty()
                                    .sendMessage("The party leader has changed complexity.");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
                case 87: // Novite Maul
                    if (player.getClickDelay().elapsed(1000)) {
                        Difficulty.set(player, Difficulty.HARD, true);
                        PlayerPanel.refreshPanel(player);
                        DialogueManager.start(player, Tutorial.get(player, 0));
                        player.getClickDelay().reset();
                    }
                    break;
                case 88:
                    ShopManager.getShops().get(25).open(player);
                    break;
            }
        } else if (id == FOURTH_OPTION_OF_FIVE) {
            switch (player.getDialogueActionId()) {
                case 4000:
                    if (player.getEarthessence() <=0){
                        player.getPacketSender().sendMessage("You dont have any Earth Essence in your Pouch!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    int earthessence = player.getEarthessence();
                    if (player.getInventory().getFreeSlots() <= 1) {
                        player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(11052, earthessence);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setEarthessence(0);
                    break;
                case 3060: //BEAST HUNTER BOSS 4
                    if (player.getSlayer().getSlayerTask().getNpcId() != 55){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2527, 3935, 0), TeleportType.ANCIENT);
                    player.sendMessage("You travel to Boss 4!");
                    break;
                case 3061: //BEAST HUNTER BOSS 8
                    if (player.getSlayer().getSlayerTask().getNpcId() != 60){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2527, 3935, 0), TeleportType.ANCIENT);
                    player.sendMessage("You travel to Boss 8!");
                    break;
                case 101:
                    player.getPlayerOwnedShopManager().openHistory();
                    break;
                case 9910: // Donation zones, Option 1
                    if (player.getAmountDonated() < DonatorRanks.ASCENDANT_AMOUNT) {
                        player.sendMessage("You don't meet the requirements to access this Zone");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(3422, 3103, 0), TeleportType.ANCIENT);
                    player.sendMessage("Welcome to the Executive Zone!");

                    break;

              case 9907: // Minigames, Option 4
                   TeleportHandler.teleportPlayer(player, new Position(2399, 2976, 0), TeleportType.ANCIENT);
                  break;


                case 9924:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(3));
                    player.getPacketSender().sendEnterAmountPrompt("How many Slayer tickets would you like to withdraw?");
                    break;
                case 9925:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(7));
                    player.getPacketSender().sendEnterAmountPrompt("How many Gold charms would you like to withdraw?");
                    break;
                case 831:
                    break;
                case 150:
                    break;
                case 151:
                    break;
                case 153:
                    TeleportHandler.teleportPlayer(player, new Position(3184, 5471),
                            TeleportType.ANCIENT);
                    // Chaos tunnels here
                    break;
                case 152:
                    break;
                case 0:
                    TeleportHandler.teleportPlayer(player,
                            new Position(3173 - (Misc.getRandom(2)), 2981 + Misc.getRandom(2)),
                            TeleportType.ANCIENT);
                    break;
                case 1:
                    TeleportHandler.teleportPlayer(player, new Position(3279, 2964),
                            TeleportType.ANCIENT);
                    break;
                case 2:
                    TeleportHandler.teleportPlayer(player, new Position(3085, 9672),
                            TeleportType.ANCIENT);
                    break;
                case 10:
                    ShopManager.getShops().get(26).open(player);
                    break;
                case 11:
                    Scoreboards.open(player, Scoreboards.TOP_ACHIEVER);
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player,
                            new Position(3329 + Misc.getRandom(2), 3660 + Misc.getRandom(2), 0),
                            TeleportType.ANCIENT);
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(2360, 5214),
                            TeleportType.ANCIENT);
                    break;
                case 15:
                    TeleportHandler.teleportPlayer(player, new Position(3565, 3313),
                            TeleportType.ANCIENT);
                    break;
                case 17:
                    player.setInputHandling(new ChangePassword());
                    player.getPacketSender().sendEnterInputPrompt("Enter a new password:");
                    break;
                case 29:
                    player.sendMessage("To remove");
                    break;
                case 36:
                    TeleportHandler.teleportPlayer(player, new Position(2717, 9805),
                            TeleportType.ANCIENT);
                    break;
                case 38:
                    TeleportHandler.teleportPlayer(player, new Position(1891, 3177),
                            TeleportType.ANCIENT);
                    break;
                case 40:
                    TeleportHandler.teleportPlayer(player, new Position(3050, 9573),
                            TeleportType.ANCIENT);
                    break;
                case 48:
                    break;
                case 195:
                    TeleportHandler.teleportPlayer(player, new Position(3292, 3176), TeleportType.RING_TELE);
                    player.getClickDelay().reset();
                    break;
                case 60:
                case 202:
                    player.setDialogueActionId(64);
                    DialogueManager.start(player, 102);
                    break;
                case 78:
                    break;
                case 68:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() != null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty().getOwner().getUsername()
                                .equals(player.getUsername())) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().setComplexity(4);
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().sendMessage(
                                    "The party leader has changed complexity to " + player.getMinigameAttributes()
                                            .getDungeoneeringAttributes().getParty().getComplexity() + ".");
                            player.getMinigameAttributes().getDungeoneeringAttributes().getParty().refreshInterface();
                        }
                    }
                    break;
                case 87: // Magic shortbow
                    if (player.getClickDelay().elapsed(1000)) {
                        Difficulty.set(player, Difficulty.EXTREME, true);
                        PlayerPanel.refreshPanel(player);
                        DialogueManager.start(player, Tutorial.get(player, 0));
                        player.getClickDelay().reset();
                    }
                    break;
                case 88:
                    // ShopManager.getShops().get(24).open(player); //DONATOR SHOP 3 HERE
                    player.getDonationDeals().displayReward();
                    player.getDonationDeals().displayTime();
                    player.getPacketSender().sendString(57277, "@yel@$" + player.getAmountDonatedToday());
                    break;
            }
        } else if (id == FIFTH_OPTION_OF_FIVE) {
            switch (player.getDialogueActionId()) {
                case 4000:
                    if (player.getSlayeressence() <=0){
                        player.getPacketSender().sendMessage("You dont have any Slayer Essence in your Pouch!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    int slayeressence = player.getSlayeressence();
                    if (player.getInventory().getFreeSlots() <= 1) {
                        player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(3576, slayeressence);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setSlayeressence(0);
                    break;

                case 9910: // Donation zones, Option 1
                    DialogueManager.start(player, 9911);
                    player.setDialogueActionId(9911);
                    break;
                case 9911: // Donation zones, Option 1
                    DialogueManager.start(player, 9912);
                    player.setDialogueActionId(9912);
                    break;
                case 9924:
                case 9925:
                    player.setDialogueActionId(player.getDialogueActionId() + 1);
                    DialogueManager.next(player);
                    break;
                case 9907: // Minigames, Option 5
                    player.sendMessage("@bla@<shad=0>Welcome to the Shields Minigame!");
                    TeleportHandler.teleportPlayer(player, new Position(2641, 3722, 0), TeleportType.ANCIENT);
                    break;

                case 150:
                    player.setDialogueActionId(151);
                    DialogueManager.next(player);
                    break;
                case 151:
                    player.setDialogueActionId(152);
                    DialogueManager.next(player);
                    break;
                case 153:
                    player.setDialogueActionId(14);
                    DialogueManager.next(player);
                    break;
                case 152:
                    player.setDialogueActionId(150);
                    DialogueManager.next(player);
                    break;
                case 0:
                    player.setDialogueActionId(1);
                    DialogueManager.next(player);
                    break;
                case 1:
                    player.setDialogueActionId(2);
                    DialogueManager.next(player);
                    break;
                case 2:
                    player.setDialogueActionId(0);
                    DialogueManager.start(player, 0);
                    break;
                case 202:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setBonecrushEffect(!player.getBonecrushEffect());
                    player.getPacketSender()
                            .sendMessage("<img=5> You have " + (player.getBonecrushEffect() ? "activated" : "disabled")
                                    + " your cape's Bonecrusher effect.");
                    break;
                case 9:
                case 10:
                case 11:
                case 13:
                case 213:
                case 17:
                case 78:
                case 29:
                case 48:
                case 195:
                case 60:
                case 67:
                case 68:
                case 88:
                case 101:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 12:
                    TeleportHandler.teleportPlayer(player, new Position(3651, 3486, 0),
                            TeleportType.ANCIENT);
                    break;
                case 14:
                    DialogueManager.next(player);
                    player.setDialogueActionId(153);
                    break;
                case 15:
                    DialogueManager.start(player, 32);
                    player.setDialogueActionId(18);
                    break;
                case 36:
                    DialogueManager.start(player, 66);
                    player.setDialogueActionId(38);
                    break;
                case 38:
                    DialogueManager.start(player, 68);
                    player.setDialogueActionId(40);
                    break;
                case 40:
                    DialogueManager.start(player, 69);
                    player.setDialogueActionId(41);
                    break;

                case 83:
                    DialogueManager.start(player, 138);
                    break;
                case 87: // Air Staff
                    if (player.getClickDelay().elapsed(1000)) {
                        player.getClickDelay().reset();
                    }
                    return;
            }
        } else if (id == FIRST_OPTION_OF_FOUR) {
            switch (player.getDialogueActionId()) {



                case 3060: //BRIMSTONE
                    if (player.getSlayer().getSlayerTask().getNpcId() != 2017){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().endFight();
                    }
                    player.setBossFight(new BrimstoneFight(player));
                    player.getBossFight().begin();
                    player.sendMessage("You travel to Brimstone's Chamber!");
                    break;
                case 3061: //BEAST HUNTER BOSS 6
                    if (player.getSlayer().getSlayerTask().getNpcId() != 2465){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(3487, 5530, 0), TeleportType.ANCIENT);
                    player.sendMessage("You travel to the Oracle!");
                    break;
                case 8901://GAIAS SKIP
                    player.setClaimedSkipPrayer(true);
                    player.getInventory().add(20030, 1); //GAIA'S BLESSING PRAYER UNLOCK
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 5019:
                    if (!player.receivedprayerunlock) {
                        player.setHasManualPrayer(true);
                        player.setReceivedprayerunlock(true);
                        player.getInventory().add(20030, 1); //GAIA'S BLESSING PRAYER UNLOCK
                        player.getPacketSender().sendInterfaceRemoval();
                        DialogueManager.start(player, 5020);
                        player.getPA().sendEntityHintRemoval(true);
                        Position cave_position = new Position(2327,3423,0);
                        player.getPacketSender().sendPositionalHint(cave_position, 3);
                        player.setVgTutStage(4);
                        player.getPacketSender().sendString(32753, "Back to the Mines: Stage 4");//STAGE TITLE
                        player.getPacketSender().sendString(32752, "Speak to the Miner");//STAGE HINT
                    }
                    break;
                case 8995:
                    player.setClaimedSkipArmor(true);
                    player.getInventory().add(3595, 1);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 5049:
                    if (!player.claimedGear){
                        player.setClaimedGear(true);
                        player.getInventory().add(3595, 1);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.msgRed("Gather all 3 Fragments, then speak to the Wizard to travel home.");
                        player.getSkillManager().addExperience(Skill.STRENGTH, 50);
                        player.getSkillManager().addExperience(Skill.ATTACK, 50);
                        player.getSkillManager().addExperience(Skill.DEFENCE, 50);
                        player.getSkillManager().addExperience(Skill.RANGED, 50);
                        player.getSkillManager().addExperience(Skill.MAGIC, 50);
                        return;
                    }
                    if (player.claimedGear){
                        player.msgRed("Gather all 3 Fragments, then speak to the Wizard to travel home.");
                        return;
                    }
                        break;

                case 3055:
                    if (!player.getInventory().contains(12438,1)){
                        player.sendMessage("<shad=0>@red@You need a shard crate to open this!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getInventory().contains(12438,1)) {
                        player.getInventory().delete(12438, 1);
                        player.getInventory().add(12431, 1);
                        player.sendMessage("<shad=0><col=AF70C3>You open the box and obtain a Melee upgrade Shard!");
                        player.getPacketSender().sendInterfaceRemoval();
                    }
                    break;
                case 9926:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(8));
                    player.getPacketSender().sendEnterAmountPrompt("How many Green charms would you like to withdraw?");
                    break;



                case 6097:
                    int dmgSalts = player.getDamageSalts();
                    if (dmgSalts <= 0){
                        player.msgRed("You dont have any Damage Salts in your Pouch!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getInventory().getFreeSlots() <= 1) {
                        player.msgRed("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(23121, dmgSalts);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setDamageSalts(0);
                    break;
                case 8:
                    ShopManager.getShops().get(110).open(player);
                    break;
                case 9:
                    TeleportHandler.teleportPlayer(player, new Position(3184, 3434),
                            TeleportType.ANCIENT);
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(2871, 5318, 2),
                            TeleportType.ANCIENT);
                    break;
                case 17:
                    if (player.getBankPinAttributes().hasBankPin()) {
                        DialogueManager.start(player, 12);
                        player.setDialogueActionId(8);
                    } else {
                        BankPin.init(player, false);
                    }
                    break;
                case 18:
                    TeleportHandler.teleportPlayer(player, new Position(2439 + Misc.getRandom(2), 5171 + Misc.getRandom(2)),
                            TeleportType.ANCIENT);
                    break;
                case 26:
                    TeleportHandler.teleportPlayer(player, new Position(2480, 3435),
                            TeleportType.ANCIENT);
                    break;
                case 27:
                    ClanChatManager.createClan(player);
                    break;
                case 28:
                    player.setDialogueActionId(29);
                    DialogueManager.start(player, 62);
                    break;
                case 30:
                    player.getSlayer().assignTask();
                    // System.out.println("TAsk assigned: - Master: " + player.getSlayer().getSlayerMaster().toString());
                    break;
                case 31:
                    DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                    break;
                case 3011:
                    DialogueManager.start(player, BossSlayerDialogues.findAssignment(player));
                    break;
                case 41:
                    DialogueManager.start(player, 76);
                    break;
                case 45:// or atally leave it fck it ok, thats all then.where to change rewards? here
                    GameMode.set(player, GameMode.NORMAL, false);
                    PlayerPanel.refreshPanel(player);
                    break;
                case 79:
                    DialogueManager.start(player, 128);
                    player.setDialogueActionId(80);
                    break;

                case 83:
                    TeleportHandler.teleportPlayer(player, new Position(3163, 3796),
                            TeleportType.ANCIENT);
                    break;
                case 84:
                    TeleportHandler.teleportPlayer(player, new Position(3406, 2794, 0),
                            TeleportType.ANCIENT);
                    // TeleportHandler.teleportPlayer(player, new Position(3420, 2777,
                    // (player.getIndex()+1)*4), TeleportType.ANCIENT); //zulrah
                    // instance
                    break;
                case 87:
                    Construction.enterHouse(player, false);
                    break;
            }
        } else if (id == SECOND_OPTION_OF_FOUR) {
            switch (player.getDialogueActionId()) {
                case 8901://EBB SKIP
                    player.setClaimedSkipPrayer(true);
                    player.getInventory().add(20035, 1); //EBB AND FLOW PRAYER UNLOCK
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 3060: //EVERTHORN
                    if (player.getSlayer().getSlayerTask().getNpcId() != 6323){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().endFight();
                    }
                    player.setBossFight(new EverthornFight(player));
                    player.getBossFight().begin();
                    player.sendMessage("You travel to Everthorn's Island");
                    break;
                case 3061: //ROCKMAW
                    if (player.getSlayer().getSlayerTask().getNpcId() != 2466){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(3553, 5400, 0), TeleportType.ANCIENT);
                    player.sendMessage("You travel to Rockmaw's Lair!");
                    break;
                case 6097:
                    int drSalts = player.getDroprateSalts();
                    if (drSalts <= 0){
                        player.msgRed("You dont have any Drop Rate Salts in your Pouch!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getInventory().getFreeSlots() <= 1) {
                        player.msgRed("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(23119, drSalts);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setDroprateSalts(0);
                    break;
                case 5019:
                    if (!player.receivedprayerunlock) {
                        player.setHasManualPrayer(true);
                        player.setReceivedprayerunlock(true);
                        player.getInventory().add(20035, 1); //EBB AND FLOW PRAYER UNLOCK
                        player.getPacketSender().sendInterfaceRemoval();
                        DialogueManager.start(player, 5021);
                        player.getPA().sendEntityHintRemoval(true);
                        Position cave_position = new Position(2327,3423,0);
                        player.getPacketSender().sendPositionalHint(cave_position, 3);
                        player.setVgTutStage(4);
                        player.getPacketSender().sendString(32753, "Back to the Mines: Stage 4");//STAGE TITLE
                        player.getPacketSender().sendString(32752, "Speak to the Miner");//STAGE HINT
                    }
                    break;
                case 8995:
                    player.setClaimedSkipArmor(true);
                    player.getInventory().add(3601, 1);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 5049:
                    if (!player.claimedGear){
                        player.setClaimedGear(true);
                        player.getInventory().add(3601, 1);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.msgRed("Gather all 3 Fragments, then speak to the Wizard to travel home.");
                        player.getSkillManager().addExperience(Skill.STRENGTH, 50);
                        player.getSkillManager().addExperience(Skill.ATTACK, 50);
                        player.getSkillManager().addExperience(Skill.DEFENCE, 50);
                        player.getSkillManager().addExperience(Skill.RANGED, 50);
                        player.getSkillManager().addExperience(Skill.MAGIC, 50);
                        return;
                    }
                    if (player.claimedGear){
                        player.msgRed("Gather all 3 Fragments, then speak to the Wizard to travel home.");
                        return;
                    }
                    break;
                case 3055:
                    if (!player.getInventory().contains(12438,1)){
                        player.sendMessage("<shad=0>@red@You need a shard crate to open this!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }

                        if (player.getInventory().contains(12438,1)){
                        player.getInventory().delete(12438,1);
                        player.getInventory().add(12432,1);
                        player.sendMessage("<shad=0><col=AF70C3>You open the box and obtain a Range upgrade Shard!");
                        player.getPacketSender().sendInterfaceRemoval();
                    }
                    break;
                case 9926:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(9));
                    player.getPacketSender().sendEnterAmountPrompt("How many Crimson charms would you like to withdraw?");
                    break;
                case 8:
                    LoyaltyProgramme.open(player);
                    break;
                case 9:
                    DialogueManager.start(player, 14);
                    break;

                case 30:
                    if(player.getSlayer().getSlayerMaster().getNpcId() == 1597) {
                        ShopManager.getShops().get(47).open(player);
                    }
                    if(player.getSlayer().getSlayerMaster().getNpcId() == 9085) {
                        ShopManager.getShops().get(472).open(player);
                    }
                    if(player.getSlayer().getSlayerMaster().getNpcId() == 8275) {
                        ShopManager.getShops().get(471).open(player);
                    }
                    if(player.getSlayer().getSlayerMaster().getNpcId() == 9000) {
                        ShopManager.getShops().get(107).open(player);
                    }
                    break;
                case 14:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 50) {
                        player.getPacketSender()
                                .sendMessage("You need a Slayer level of at least 50 to visit this dungeon.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2731, 5095),
                            TeleportType.ANCIENT);
                    break;
                case 17:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getBankPinAttributes().hasBankPin()) {
                        player.getPacketSender()
                                .sendMessage("Please visit the nearest bank and enter your pin before doing this.");
                        return;
                    }
                    if (player.getSummoning().getFamiliar() != null) {
                        player.getPacketSender().sendMessage("Please dismiss your familiar first.");
                        return;
                    }
                    if (player.getGameMode() == GameMode.NORMAL) {
                        DialogueManager.start(player, 83);
                    } else {
                        player.setDialogueActionId(46);
                        DialogueManager.start(player, 84);
                    }
                    break;
                case 18:
                    TeleportHandler.teleportPlayer(player, new Position(2399, 5177),
                            TeleportType.ANCIENT);
                    break;
                case 26:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getMaxLevel(Skill.AGILITY) < 35) {
                        player.getPacketSender()
                                .sendMessage("You need an Agility level of at least level 35 to use this course.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2552, 3556),
                            TeleportType.ANCIENT);
                    break;
                case 27:
                    ClanChatManager.clanChatSetupInterface(player, true);
                    break;
                case 28:
                    if (player.getSlayer().getSlayerMaster().getPosition() != null) {
                        TeleportHandler.teleportPlayer(player,
                                new Position(player.getSlayer().getSlayerMaster().getPosition().getX(),
                                        player.getSlayer().getSlayerMaster().getPosition().getY(),
                                        player.getSlayer().getSlayerMaster().getPosition().getZ()),
                                TeleportType.ANCIENT);
                        if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) <= 50)
                            player.getPacketSender().sendMessage("")
                                    .sendMessage("You can train Slayer with a friend by using a Slayer gem on them.")
                                    .sendMessage("Slayer gems can be bought from all Slayer masters.");
                    }
                    break;
                case 31:
                    DialogueManager.start(player, SlayerDialogues.resetTaskDialogue(player));
                    break;
                case 45:
                    GameMode.set(player, GameMode.IRONMAN, false);
                    PlayerPanel.refreshPanel(player);
                    break;
                case 79:
                    player.getPacketSender().sendInterfaceRemoval();
                    Barrows.resetBarrows(player);
                    CircleOfElements.resetBarrows(player);
                    DialogueManager.start(player, 133);
                    break;
                case 80:
                    new TheSix(player).enter(false);
                    break;
                case 83:
                    TeleportHandler.teleportPlayer(player, new Position(3009, 3767),
                            TeleportType.ANCIENT);
                    break;
                case 84:
                    TeleportHandler.teleportPlayer(player, new Position(3683, 9888, (player.getIndex() + 1) * 4),
                            TeleportType.ANCIENT); // kraken instance
                    break;
                case 87:
                    Construction.enterHouse(player, true);
                    break;
            }
        } else if (id == THIRD_OPTION_OF_FOUR) {
            switch (player.getDialogueActionId()) {

                case 3060: //BASILISK
                    if (player.getSlayer().getSlayerTask().getNpcId() != 2018){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().endFight();
                    }
                    player.setBossFight(new BasiliskFight(player));
                    player.getBossFight().begin();
                    player.sendMessage("You travel to Basilisk's Island");
                    break;
                case 3061: //GRIMLASH
                    if (player.getSlayer().getSlayerTask().getNpcId() != 2467){
                        player.sendMessage("You can only kill this monster on task!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(3677, 5406, 0), TeleportType.ANCIENT);
                    player.sendMessage("You travel to Grimlash's Lair!");
                    break;


                case 6097:
                int critSalts = player.getCriticalSalts();
                if (critSalts <= 0){
                    player.msgRed("You dont have any Critical Salts in your Pouch!");
                    player.getPacketSender().sendInterfaceRemoval();
                    return;
                }
                if (player.getInventory().getFreeSlots() <= 1) {
                    player.msgRed("You do not have enough free inventory space to do this.");
                    return;
                }
                player.getInventory().add(23122, critSalts);
                player.getPacketSender().sendInterfaceRemoval();
                player.setCriticalSalts(0);
                break;

                case 8901://CINDER SKIP
                    player.setClaimedSkipPrayer(true);
                    player.getInventory().add(20040, 1); //CINDER'S TOUCH PRAYER UNLOCK
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 5019:
                    if (!player.receivedprayerunlock) {
                        player.setHasManualPrayer(true);
                        player.setReceivedprayerunlock(true);
                        player.getInventory().add(20040, 1); //CINDER'S TOUCH PRAYER UNLOCK
                        player.getPacketSender().sendInterfaceRemoval();
                        DialogueManager.start(player, 5022);
                        player.getPA().sendEntityHintRemoval(true);
                        Position cave_position = new Position(2327,3423,0);
                        player.getPacketSender().sendPositionalHint(cave_position, 3);
                        player.setVgTutStage(4);
                        player.getPacketSender().sendString(32753, "Back to the Mines: Stage 4");//STAGE TITLE
                        player.getPacketSender().sendString(32752, "Speak to the Miner");//STAGE HINT
                    }
                    break;
                case 8995:
                    player.setClaimedSkipArmor(true);
                    player.getInventory().add(3600, 1);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 5049:
                    if (!player.claimedGear){
                        player.setClaimedGear(true);
                        player.getInventory().add(3600, 1);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.msgRed("Gather all 3 Fragments, then speak to the Wizard to travel home.");
                        player.getSkillManager().addExperience(Skill.STRENGTH, 50);
                        player.getSkillManager().addExperience(Skill.ATTACK, 50);
                        player.getSkillManager().addExperience(Skill.DEFENCE, 50);
                        player.getSkillManager().addExperience(Skill.RANGED, 50);
                        player.getSkillManager().addExperience(Skill.MAGIC, 50);
                        return;
                    }
                    if (player.claimedGear){
                        player.msgRed("Gather all 3 Fragments, then speak to the Wizard to travel home.");
                        return;
                    }
                    break;

                case 3055:
                    if (!player.getInventory().contains(12438,1)){
                        player.sendMessage("<shad=0>@red@You need an Essence crate to open this!");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getInventory().contains(12438,1)){
                        player.getInventory().delete(12438,1);
                        player.getInventory().add(12433,1);
                        player.sendMessage("<shad=0>@blu@You open the box and obtain a Mage upgrade Shard!");
                        player.getPacketSender().sendInterfaceRemoval();
                    }
                    break;
                case 9926:
                    player.setInputHandling(new WithdrawCurrencyFromCurrencyPouch(10));
                    player.getPacketSender().sendEnterAmountPrompt("How many Blue charms would you like to withdraw?");
                    break;
                case 8:
                    LoyaltyProgramme.reset(player);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 9:
                    ShopManager.getShops().get(41).open(player);
                    break;
                case 14:
                    TeleportHandler.teleportPlayer(player, new Position(1745, 5325),
                            TeleportType.ANCIENT);
                    break;
                case 17:
                    player.setInputHandling(new ChangePassword());
                    player.getPacketSender().sendEnterInputPrompt("Enter a new password:");
                    break;
                case 18:
                    TeleportHandler.teleportPlayer(player, new Position(3503, 3562),
                            TeleportType.ANCIENT);
                    break;
                case 26:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getMaxLevel(Skill.AGILITY) < 55) {
                        player.getPacketSender()
                                .sendMessage("You need an Agility level of at least level 55 to use this course.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2998, 3914),
                            TeleportType.ANCIENT);
                    break;
                case 27:
                    ClanChatManager.deleteClan(player);
                    break;
                case 28:
                    TeleportHandler.teleportPlayer(player, new Position(3427, 3537, 0),
                            TeleportType.ANCIENT);
                    break;
                case 30:
                    ShopManager.getShops().get(40).open(player);
                    break;
                case 31:
                    DialogueManager.start(player, SlayerDialogues.totalPointsReceived(player));
                    break;
                case 41:
                    break;

                /*case 65:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSlayer().getDuoPartner() != null) { // slayer
                        Player partner = World.getPlayerByName(player.getSlayer().getDuoPartner());
                        boolean inPos = (player.getLocation() == Location.HOME_BANK
                                || player.getLocation() == Location.NEW_MEMBER_ZONE);
                        if (!inPos || player.busy() || player.getCombatBuilder().isBeingAttacked()) {
                            player.getPacketSender().sendMessage(
                                    "You may only teleport to your target from the Home bank, or Member Zone.");
                            break;
                        }
                        if (partner.busy() || partner.getLocation() == Location.WILDERNESS
                                || partner.getLocation() == Location.DUNGEONEERING
                                || partner.getLocation() == Location.DUEL_ARENA || partner.getLocation() == Location.JAIL
                                || partner.getLocation() == Location.BARROWS || partner.getLocation() == Location.KRAKEN
                                || partner.getLocation() == Location.NEW_MEMBER_ZONE
                                || partner.getLocation() == Location.MEMBER_ZONE
                                || partner.getLocation() == Location.FREE_FOR_ALL_ARENA
                                || partner.getLocation() == Location.FREE_FOR_ALL_WAIT
                                || partner.getLocation() == Location.GODWARS_DUNGEON
                                || partner.getLocation() == Location.PEST_CONTROL_GAME
                                || partner.getLocation() == Location.TRIO_ZONE || partner.getLocation() == Location.THE_SIX
                                || partner.getLocation() == Location.WARRIORS_GUILD
                                || partner.getLocation() == Location.ZULRAH) {
                            player.getPacketSender().sendMessage(
                                    "Your partner cannot be teleported to at the moment. Are they in combat/minigame/wild?");
                            break;
                        } else {
                            TeleportHandler.teleportPlayer(player, new Position(partner.getPosition().getX(),
                                    partner.getPosition().getY(), partner.getPosition().getZ()), TeleportType.NORMAL);
                            player.getPacketSender().sendMessage("Teleporting you to " + partner.getUsername() + "!");
                        }
                    } else {
                        player.getPacketSender().sendMessage("You need to be in a team with a partner first!");
                    }
                    break;*/
                case 79:
                    player.getPacketSender().sendMessage("<shad=336600>You currently have "
                            + player.getPointsHandler().getBarrowsPoints() + " Barrows points.");
                    ShopManager.getShops().get(79).open(player);
                    break;
                case 80:
                    DialogueManager.start(player, 129);
                    break;
                case 83:
                    TeleportHandler.teleportPlayer(player, new Position(3005, 3732),
                            TeleportType.ANCIENT);
                    break;
                case 84:
                    TeleportHandler.teleportPlayer(player, new Position(2849, 9640),
                            TeleportType.ANCIENT);
                    break;
                case 87:
                    player.setInputHandling(new EnterFriendsHouse());
                    player.getPacketSender().sendEnterInputPrompt("Enter a friend's username:");
                    break;
            }
        } else if (id == FOURTH_OPTION_OF_FOUR) {
            switch (player.getDialogueActionId()) {
                case 3060:
                    DialogueManager.start(player, 3061);
                    player.setDialogueActionId(3061);
                    break;
                case 3061:
                    DialogueManager.start(player, 3060);
                    player.setDialogueActionId(3060);
                    break;
                case 8:
                case 9:
                case 17:
                case 26:
                case 27:
                case 28:
                case 41:
                case 79:
                case 80:
                case 84:
                case 87:
                case 9926:
                case 5019:
                case 8995:
                case 5049:
                case 6097:
                case 8901:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 14:
                    player.setDialogueActionId(14);
                    DialogueManager.start(player, 22);
                    break;
                case 18:
                    DialogueManager.start(player, 25);
                    player.setDialogueActionId(15);
                    break;
                case 30:
                case 31:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSlayer().getDuoPartner() != null) {
                        Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
                    }
                    break;
                case 45:
                    player.getPacketSender().sendString(1, GameSettings.IronManModesUrl);
                    break;
                case 83:
                    player.setDialogueActionId(84);
                    DialogueManager.start(player, 138);
                    break;
            }
        } else if (id == FIRST_OPTION_OF_TWO) {
            switch (player.getDialogueActionId()) {
                case 8834:
                    if(!player.acceptedEasterReward){
                        player.setAcceptedEasterReward(true);
                        player.setEasterQuestStage(7);
                        player.getInventory().add(720,2);
                        player.getInventory().add(715,250);

                        player.getPacketSender().sendInterface(4909);
                        player.getPacketSender().sendString(4913, " You completed The Easter Quest!");
                        player.getPacketSender().sendString(4911, "          You are awarded:");
                        player.getPacketSender().sendString(4914, "      Access to Bunny Instances");
                        player.getPacketSender().sendString(4915, "    2 Egg Crate/250 Easter Fragments");
                        player.getPacketSender().sendString(4918, "            Happy Easter!");
                        player.getPacketSender().sendString(4916, "");
                        return;
                    }
                    break;
                case 8807:
                    if (player.getEasterQuestStage() != 0) {
                        player.msgRed("Please Report this Error to Hades");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    player.setEasterQuestStage(1);
                    DialogueManager.start(player, 8810);

                    break;
                case 3051:
                    if (player.getBattlePass().getType() == BattlePassType.TIER1) {
                        player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Battle Pass(T1).");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBattlePass().getType() == BattlePassType.TIER2) {
                        player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Battle Pass(T2).");
                        player.getPacketSender().sendInterfaceRemoval();
                        return;
                    }
                    player.getBattlePass().displayPage();
                    player.getBattlePass().unlockTier1();
                    player.getInventory().delete(3590, 28);
                    break;
                case 3052:
                    if (player.getBattlePass().getType() == BattlePassType.TIER1) {
                        player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Battle Pass(T1).");
                        return;
                    }
                    if (player.getBattlePass().getType() == BattlePassType.TIER2) {
                        player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Battle Pass(T2).");
                        return;
                    }
                    player.getBattlePass().displayPage();
                    player.getBattlePass().unlockTier2();
                    player.getInventory().delete(3594, 28);
                    break;
                case 5555:
                    player.setChristmasQuest2023Stage(1);
                    DialogueManager.start(player, Christmas2023.santa_response_6(player));
                    player.setDialogueActionId(-1);
                    break;
                case 6095:
                    int curseRunes = player.getCurseRunes();
                    int soulRunes = player.getSoulRunes();
                    int cryptRunes = player.getCryptRunes();
                    int shadowRunes = player.getShadowRunes();
                    int wraithRunes = player.getWraithRunes();
                    int voidRunes = player.getVoidRunes();

                    if (player.getInventory().getFreeSlots() <= 6) {
                        player.msgRed("You do not have enough free inventory space to do this.");
                        return;
                    }
                    player.getInventory().add(20010, curseRunes);
                    player.getInventory().add(20014, soulRunes);
                    player.getInventory().add(20015, cryptRunes);
                    player.getInventory().add(20012, shadowRunes);
                    player.getInventory().add(20011, wraithRunes);
                    player.getInventory().add(20013, voidRunes);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setCurseRunes(0);
                    player.setSoulRunes(0);
                    player.setCryptRunes(0);
                    player.setShadowRunes(0);
                    player.setWraithRunes(0);
                    player.setVoidRunes(0);
                    break;

                case 6090:
                    PrayerMinigame.setInstanceBossKillsAmount(player);
                    if (player.getPrayerMinigameBossKillsLeft() <= 0){
                        player.sendMessage("@red@<shad=0>You don't have any boss kills remaining!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().endFight();
                    }
                    player.setBossFight(new PrayerEarthBoss(player));
                    player.getBossFight().begin();

                    player.sendMessage("<col=AF70C3><shad=0>You travel to the Earth Boss Room");
                    player.sendMessage("@red@<shad=0>" + player.getPrayerMinigameBossKillsLeft() + ": <col=AF70C3><shad=0>Boss kills remaining!");
                    player.setPrayerMinigameBossInstanceActive(true);
                    break;
                case 6091:
                    PrayerMinigame.setInstanceBossKillsAmount(player);
                    if (player.getPrayerMinigameBossKillsLeft() <= 0){
                        player.sendMessage("@red@<shad=0>You don't have any boss kills remaining!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().endFight();
                    }
                    player.setBossFight(new PrayerWaterBoss(player));
                    player.getBossFight().begin();

                    player.sendMessage("<col=AF70C3><shad=0>You travel to the Water Boss Room");
                    player.sendMessage("@red@<shad=0>" + player.getPrayerMinigameBossKillsLeft() + ": <col=AF70C3><shad=0>Boss kills remaining!");
                    player.setPrayerMinigameBossInstanceActive(true);
                    break;
                case 6092:
                    PrayerMinigame.setInstanceBossKillsAmount(player);
                    if (player.getPrayerMinigameBossKillsLeft() <= 0){
                        player.sendMessage("@red@<shad=0>You don't have any boss kills remaining!");
                        player.getPA().sendInterfaceRemoval();
                        return;
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().endFight();
                    }
                    player.setBossFight(new PrayerFireBoss(player));
                    player.getBossFight().begin();

                    player.sendMessage("<col=AF70C3><shad=0>You travel to the Fire Boss Room");
                    player.sendMessage("@red@<shad=0>" + player.getPrayerMinigameBossKillsLeft() + ": <col=AF70C3><shad=0>Boss kills remaining!");
                    player.setPrayerMinigameBossInstanceActive(true);
                    break;

                case 6060:
                    ExpLamps.handleRubAll(player, player.getRubAllLampId());
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 5058:
                    if (player.getVgTutStage() != 8){
                        return;
                    }
                    player.getPacketSender().sendInterface(4909);
                    player.getPacketSender().sendString(4913, " You completed The Athens Tutorial!");
                    player.getPacketSender().sendString(4911, "           You are awarded:");
                    player.getPacketSender().sendString(4914, "       Access to Athens Mainland");
                    player.getPacketSender().sendString(4915, "    ");
                    player.getPacketSender().sendString(4918, "      ");
                    player.getPacketSender().sendString(4916, "");
                    player.setVgTutStage(9);
                    break;
                case 5060:
                    if (player.getVgTutStage() != 9) {
                        return;
                    }
                    player.getInventory().delete(23119, 999999);
                    player.getInventory().delete(23121, 999999);
                    player.getInventory().delete(23122, 999999);
                    player.getInventory().delete(17582, 999999);
                    player.getInventory().delete(17584, 999999);
                    player.getInventory().delete(17586, 999999);
                    player.getInventory().delete(17490, 999999);

                    player.setCompletedtutorial(true);
                    player.setVgTutStage(10);
                    TeleportHandler.teleportPlayer(player, new Position(3168 +- Misc.random(1,2), 3544 +- Misc.random(1,2)), TeleportType.ANCIENT);
                    player.sendMessage("You travel to the Athens Mainland.");
                    player.getPacketSender().sendWalkableInterface(32750, false);

                    break;
                case 5041:
                    if (!player.startedTutorial){
                        return;
                    }
                    if (!player.tutTask3Complete){
                        return;
                    }

                    if (!player.tutTask4Started) {
                        player.setTutTask4Started(true);
                        DialogueManager.start(player, 5042);
                        player.getInventory().add(17490, 10);
                        return;
                    }

                    break;
                case 5031:
                    if (!player.startedTutorial){
                        return;
                    }
                    if (!player.tutTask3Started){
                        player.setTutTask3Started(true);
                        player.getInventory().add(1267, 1);
                        DialogueManager.start(player, 5032);
                        return;
                    }
                    break;
                case 5006:
                    if (player.startedTutorial){
                        player.sendMessage("Tutorial Started Already");
                        return;
                    }
                    player.getPA().sendEntityHintRemoval(true);
                    Position church_position = new Position(2328,3428,0);
                    player.sendMessage("Quest Accepted");
                    player.performGraphic(new Graphic(190));
                    Position position45 = new Position(2326, 3433, 0);
                    player.moveTo(position45);
                    TeleportHandler.teleportPlayer(player, position45, TeleportType.ANCIENT);

                    player.setStartedTutorial(true);
                    player.setTutTask1Started(true);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendPositionalHint(church_position, 3);
                    player.setVgTutStage(2);
                    player.getPacketSender().sendString(32753, "Digging Deeper : Stage 2");//STAGE TITLE
                    player.getPacketSender().sendString(32752, "Head North West to the Salt Mines & Attack the Beast");//STAGE HINT
                    break;

                case 5016:
                    if (!player.startedTutorial){
                        return;
                    }
                    DialogueManager.start(player, 5017);

                    player.sendMessage("The priest hands you a mysterious looking urn...");
                    player.getInventory().add(20425,1);
                    player.setTutTask2Started(true);
                    player.setTutTask1Complete(true);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 6000:
                    if (player.getPosition().getRegionId() == 9269 ) {

                        if (player.isCompletedtutorial()) {
                            player.sendMessage("You probably don't want to go back in there...");
                            return;
                        }

                        if (player.getBossFight() != null) {
                            player.getBossFight().endFight();
                        }

                        player.getPA().sendEntityHintRemoval(true);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.performAnimation(new Animation(844));
                        player.getPacketSender().sendFadeTransition(50, 50, 50);
                        TaskManager.submit(new Task(3, player, false) {
                            @Override
                            public void execute() {
                                player.setBossFight(new TutorialBossFight(player));
                                player.getBossFight().begin();
                                player.getPacketSender().sendInterfaceRemoval();
                                stop();
                                return;
                            }
                        });
                    }


                    if (player.getPosition().getRegionId() == 13636 ) {
                        if (player.getBossFight() != null) {
                            player.getBossFight().endFight();
                        }
                        player.getPacketSender().sendInterfaceRemoval();
                        player.performAnimation(new Animation(844));
                        player.getPacketSender().sendFadeTransition(50, 50, 50);
                        Position church_location = new Position(2374,3375,0);

                        if (player.isTutTask1Ready() && !player.isTutTask1Complete()){
                            player.getPacketSender().sendPositionalHint(church_location, 3);

                        }

                        if (player.receivedprayerunlock && !player.tutTask3Started) {
                            Position cave_position = new Position(2323, 3426, 0);
                            player.getPacketSender().sendPositionalHint(cave_position, 3);
                        }
                        TaskManager.submit(new Task(3, player, false) {
                            @Override
                            public void execute() {
                                player.moveTo(2325, 3428, 0);
                                stop();
                            }
                        });
                    }
                    break;


                case 3050:
                    if(!player.completedwhisperquest){
                        player.setCompletedwhisperquest(true);
                        player.getPacketSender().sendInterface(4909);
                        player.getPacketSender().sendString(4913, " You completed Whispers In the Sand!");
                        player.getPacketSender().sendString(4911, "           You are awarded:");
                        player.getPacketSender().sendString(4914, "       Access to Kharalos' Tomb");
                        player.getPacketSender().sendString(4915, "     Summer Splashdown Unlocked");
                        player.getPacketSender().sendString(4918, "      Have an awesome Summer!");
                        player.getPacketSender().sendString(4916, "");
                        return;
                    }

                    break;

                case 3036:
                    player.setStartedOraclePart(true);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setDialogueActionId(-1);
                    DialogueManager.start(player, 3037);
                    break;
                case 3027:
                    if (player.hasenchantedtablet) {
                        player.setTalkedtoKaden2ndtime(true);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.setDialogueActionId(-1);
                        DialogueManager.start(player, 3028);
                    }
                    break;

                case 3018:
                    if (!player.hasenchantedtablet) {
                        player.getInventory().delete(7254, 1);
                        player.getInventory().add(7256, 1);
                        player.sendMessage("The Ghost takes the Tablet from you quickly...");
                        player.setHasenchantedtablet(true);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.setDialogueActionId(-1);
                        DialogueManager.start(player, 3019);
                    }
                    break;
                case 3015:
                    player.setTalkedtoghost(true);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setDialogueActionId(-1);
                    DialogueManager.start(player, 3016);
                    break;
                case 3005:
                    player.setStartedSandQuest(true);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setDialogueActionId(-1);
                    DialogueManager.start(player, 3006);
                    break;

                case 65:

                    break;
                case 171:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(2750));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);
                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                            public void execute() {
                                player.setInOvergrownGardens(true);
                                player.setInInfernalCavern(false);
                                player.setInFrozenCavern(false);
                                player.setFrozenSteps(0);
                                player.setInfernalSteps(0);
                                player.setEarthSteps(0);
                                player.moveTo(2551, 4638, 0);
                                player.sendMessage("You jump over the gate, and make it into the Overgrown Gardens..");
                                stop();
                            }
                        });
                    break;
                case 170:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(2750));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);

                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                        public void execute() {
                            player.setInOvergrownGardens(false);
                            player.setInInfernalCavern(false);
                            player.setInFrozenCavern(false);
                            player.setFrozenSteps(0);
                            player.setInfernalSteps(0);
                            player.setEarthSteps(0);
                            player.moveTo(2555, 4638, 0);
                            player.sendMessage("You jump over the gate, and make it back to the Mainland..");
                            stop();
                        }
                    });

                    break;
                case 163:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(11042));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);

                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                        public void execute() {
                            player.moveTo(2637, 4642, 0);
                            player.setInOvergrownGardens(false);
                            player.setInInfernalCavern(false);
                            player.setInFrozenCavern(false);
                            player.setFrozenSteps(0);
                            player.setInfernalSteps(0);
                            player.setEarthSteps(0);
                            player.sendMessage("You squeeze through the cave, and manage to make it to the surface..");
                            stop();
                        }
                    });
                    break;
                case 162:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(11042));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);

                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                        public void execute() {
                            player.setInOvergrownGardens(false);
                            player.setInInfernalCavern(false);
                            player.setInFrozenCavern(true);
                            player.setFrozenSteps(0);
                            player.setInfernalSteps(0);
                            player.setEarthSteps(0);
                            player.sendMessage("You squeeze through the cave, and manage to make it to the bottom..");
                            player.moveTo(2637, 9248, 0);
                            stop();
                        }
                    });
                    break;
                case 168:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(10023));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);

                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                        public void execute() {
                            player.setInOvergrownGardens(true);
                            player.setInInfernalCavern(false);
                            player.setInFrozenCavern(false);
                            player.setFrozenSteps(0);
                            player.setInfernalSteps(0);
                            player.setEarthSteps(0);
                            player.sendMessage("You climb up the ladder, and enter the Overgrown Gardens..");
                            player.moveTo(2506, 4623, 0);
                            stop();
                        }
                    });
                    break;
                case 167:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(9727));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);

                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                        public void execute() {
                            player.setInOvergrownGardens(false);
                            player.setInInfernalCavern(false);
                            player.setInFrozenCavern(true);
                            player.setFrozenSteps(0);
                            player.setInfernalSteps(0);
                            player.setEarthSteps(0);
                            player.sendMessage("You climb down the ladder, and enter the Frozen Cavern..");
                            player.moveTo(2653, 9269, 0);
                            stop();
                        }
                    });
                    break;
                case 169:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(9727));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);

                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                        public void execute() {
                            player.setInOvergrownGardens(false);
                            player.setInInfernalCavern(true);
                            player.setInFrozenCavern(false);
                            player.setFrozenSteps(0);
                            player.setInfernalSteps(0);
                            player.setEarthSteps(0);
                            player.sendMessage("You climb down the ladder, and enter the Infernal Cavern..");
                            player.moveTo(3169, 4298, 0);
                            stop();
                        }
                    });
                    break;
                case 164:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.performAnimation(new Animation(10023));
                    player.getPacketSender().sendFadeTransition(50, 50, 50);

                    TaskManager.submit(new Task(3, player, false) {
                        @Override
                        public void execute() {
                            player.setInOvergrownGardens(false);
                            player.setInInfernalCavern(true);
                            player.setInFrozenCavern(false);
                            player.setFrozenSteps(0);
                            player.setInfernalSteps(0);
                            player.setEarthSteps(0);
                            player.sendMessage("You climb up the ladder, and enter the Infernal Cavern..");
                            player.moveTo(3161, 4307, 0);
                            stop();
                        }
                    });
                    break;
                case 158:
                    if (player.getInventory().contains(6651,1)){
                        player.getInventory().delete(6651,1);
                        player.getInventory().add(2720,1);
                        player.setSacrificedmastersoul(true);
                        player.sendMessage("You Empty the energy from your Soul...");
                        player.sendMessage("You can now access the Soul shop!");
                        player.getPacketSender().sendInterfaceRemoval();

                        return;
                    }
                    player.getPacketSender().sendInterfaceRemoval();
                    player.sendMessage("Make sure you have a Master Soul in your Inventory!");
                    break;




                case 1712:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.sendMessage("You have chosen Hole Number: " + player.getDoorPick().getDoorSelected());
                    player.getDoorPick().openDoor();
                    break;


                case 617:
                    player.addItemToAny(9906,1);
                    player.setDialogueActionId(621);
                    DialogueManager.start(player, 621);
                    player.getMinigameAttributes().getHalloweenAttributes().setPartFinished(0, true);
                    break;
                case 618:
                    player.moveTo(new Position(2595, 3425, 0));
                    player.sendMessage("@bla@<shad=0>You feel a weird energy around you... This place is Haunted");
                   // player.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.getPA().sendInterfaceRemoval();
                    break;
                case 620:
                    player.getPA().sendInterfaceRemoval();
                    player.addItemToAny(2733, 1); //reward item added to inventory
                    player.sendMessage("@bla@<shad=0>Thanks for the help, here is your reward");
                    player.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.setHalloweenQ2(true);
                    player.getMinigameAttributes().getHalloweenAttributes().setPartFinished(2, true);
                    break;
                case 820:
                    player.getPA().sendInterfaceRemoval();
                    player.addItemToAny(2829, 1); //reward item added to inventory
                    player.addItemToAny(995, 75000); //reward item added to inventory
                    player.addItemToAny(12425, 5); //reward item added to inventory


                    DialogueManager.sendStatement(player, "That didn't go as planned.... Here just take the stinkin potion..");
                    player.sendMessage("That didn't go as planned.... Here just take the stinkin potion..");
                    player.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.setEasterstage1(true);
                    player.getMinigameAttributes().getEasterQuestAtributes().setPartFinished(2, true);
                    break;

                case 621:
                    player.moveTo(new Position(2634, 3416, 0));
                    player.sendMessage("@bla@<shad=0>Be Careful..The Undead roam here");
                   // player.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.getPA().sendInterfaceRemoval();
                    player.getMinigameAttributes().getHalloweenAttributes().setPartFinished(0, true);
                    break;
                case 817:
                    player.setDialogueActionId(821);
                    DialogueManager.start(player, 821);
                    player.getMinigameAttributes().getEasterQuestAtributes().setPartFinished(0, true);
                    break;
                case 818:
                    player.moveTo(new Position(3114, 3540, 0));
                    player.sendMessage("@bla@Welcome to the Enchanted Forest");
                    player.performGraphic(new Graphic(2009));
                    player.getPA().sendInterfaceRemoval();
                    player.getMinigameAttributes().getEasterQuestAtributes().setPartFinished(0, true);
                    break;

                case 821:
                    player.moveTo(new Position(3167, 3499, 0));
                    player.sendMessage("There should be a Bunny around here that knows what's going on...");
                    player.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.getPA().sendInterfaceRemoval();
                    player.getMinigameAttributes().getEasterQuestAtributes().setPartFinished(0, true);
                    break;
                case 67:
                case 753:
                    break;

                case 9908: // Skilling, Forgotten Skilling Island
                  //  TeleportHandler.teleportPlayer(player, new Position(2816, 2600, 0), TeleportType.ANCIENT);
                    player.sendMessage("Custom Mining + Woodcutting Coming Soon!");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;






                case 8005:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (!player.getSkillManager().maxed()) {
                        DialogueManager.sendStatement(player, "You must be maxed in all skills to do this.");
                        return;
                    }
                    /*if (!player.getAchievements().hasCompletedAll()){
                                              DialogueManager.sendStatement(player, "You must complete all achievements to do this.");
                        return;
                    }*/
                    if (player.getInventory().contains(ItemDefinition.COIN_ID, 120_000_000)) {
                        player.getInventory().delete(ItemDefinition.COIN_ID, 120_000_000);
                    } else if (player.getInventory().contains(ItemDefinition.TOKEN_ID, 120000)) {
                        player.getInventory().delete(ItemDefinition.TOKEN_ID, 120000);
                    } else {
                        DialogueManager.sendStatement(player, "You need 120m coins to do this.");
                        return;
                    }

                    player.getInventory().add(14019, 1);
                    player.getPacketSender().sendMessage("You have purchased a Max cape.");

                    break;
                case 568:
                    ShopManager.getShops().get(207).open(player);
                    break;
                case 9928:
                    if (player.getSlayer().getAmountToSlay() > 0) {
                        if (player.getInventory().contains(ItemDefinition.COIN_ID, 250000)) {
                            Position pos = player.getSlayer().getSlayerTask().getTaskPosition();
                            TeleportHandler.teleportPlayer(player, pos, TeleportType.TELE_TAB);
                            player.getInventory().delete(ItemDefinition.COIN_ID, 250000);
                        } else {
                            DialogueManager.sendStatement(player, "You need 250,000 coins to do this.");
                        }
                    } else {
                        DialogueManager.sendStatement(player, "You do not currently have a task.");
                    }
                    break;
                case 668://yes
                    if(player.getSlayer().getSlayerTask() == null) {
                        player.sendMessage("You do not have a slayer task!");
                        return;
                    }
                    if (player.getInventory().getAmount(ItemDefinition.COIN_ID) >= 250_000 && player.getInventory().contains(ItemDefinition.COIN_ID)) {
                        player.getInventory().delete(ItemDefinition.COIN_ID, 250_000);
                        TeleportHandler.teleportPlayer(player, player.getSlayer().getSlayerTask().getTaskPosition(), TeleportType.ANCIENT);
                    } else {
                        player.sendMessage("You do not have enough coins!");
                    }
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 103:
                    BondHandler.claimBond(player);
                    break;
                case 8002:
                    player.getIronmanGroup().getBank(player.getCurrentGroupBankTab()).open(player);
                    break;
                case 8001:
                    GroupManager.createGroup(player);
                    break;
                case 670:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getGroupInvitationId() != -1 && GroupManager.getGroup(player.getGroupInvitationId()) != null) {
                        GroupManager.getGroup(player.getGroupInvitationId()).addPlayer(player);
                    }
                    if (GroupManager.isInGroup(player)) {
                        GroupManager.openInterface(player);
                    }

                    if (player.getIronmanGroup().getOwner() != null) {
                        player.getIronmanGroup().getOwner().sendMessage("@blu@" + player.getUsername() + " has joined your Ironman group!");
                        GroupManager.openInterface(player.getIronmanGroup().getOwner());
                    }

                    player.setGroupInvitationId(-1);
                    break;
               /* case 6969:
                    if (player.getInventory().contains(19000)) {
                        int amount = player.getInventory().getAmount(19000);
                        player.getInventory().delete(19000, amount);
                        player.getInventory().add(ItemDefinition.MILL_ID, amount * 100);
                        player.sendMessage("You exchanged x" + amount + " Pet fragements for " + (amount * 100) + " Forgotten tokens.");
                    }
                    player.getPacketSender().sendInterfaceRemoval();
                    break;*/
                case 9923:
                    //PetUpgrading.upgrade(player);
                    break;
                case 523:
                    player.getPacketSender().sendInterfaceRemoval().sendMessage("You clear your inventory.");
                    player.getSkillManager().stopSkilling();
                    for (int i = 0; i < player.getInventory().capacity(); i++) {
                        if (player.getInventory().get(i) != null && player.getInventory().get(i).getId() > 0) {
                            if (ItemDefinition.forId(player.getInventory().get(i).getId()) != null
                                    && ItemDefinition.forId(player.getInventory().get(i).getId()).getName() != null) {
                                PlayerLogs.log(player.getUsername(),
                                        "Emptied item (id:" + player.getInventory().get(i).getId() + ", amount:"
                                                + player.getInventory().get(i).getAmount() + ") -- "
                                                + ItemDefinition.forId(player.getInventory().get(i).getId()).getName());
                            } else {
                                PlayerLogs.log(player.getUsername(), "Emptied item (id:" + player.getInventory().get(i).getId()
                                        + ", amount:" + player.getInventory().get(i).getAmount() + ")");
                            }
                        }

                        player.getInventory().resetItems().refreshItems();
                    }
                    break;
                case 920:
                    int random = RandomUtility.inclusiveRandom(1, 100);
                    player.getGambling().bjScore += random;
                    player.forceChat("I roll a " + random + " and my score is now: " + player.getGambling().bjScore);
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getGambling().bjScore < 100) {
                        TaskManager.submit(new Task(3) {
                            @Override
                            protected void execute() {
                                player.setDialogueActionId(920);
                                DialogueManager.start(player, 216);
                                stop();
                                return;
                            }
                        });
                    } else {
                        if (player.getGambling().bjTurn == 1) {
                            player.getGambling().setHostTurn();
                        } else {
                            player.getGambling().getBlackjackWinner();
                            // System.out.println("Blackjack has ended ->");
                        }
                    }
                    player.performAnimation(new Animation(11900));
                    player.performGraphic(new Graphic(2075));
                    break;
                case 211:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getEaster2017() == 7) {
                        player.getInventory().add(4565, 1);
                        player.getInventory().add(1037, 1);
                        player.setEaster2017(8);
                        World.sendMessage("<img=5> " + player.getUsername() + " has just completed the Easter event!");
                    }
                    break;
                case 100000:
                    player.getPacketSender().sendInterfaceRemoval();
                    TeleportHandler.teleportPlayer(player, new Position(2665, 4020), TeleportType.NORMAL);
                    break;
                case 210:
                    if (player.getInventory().contains(22051)) {
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getPacketSender().sendMessage("I already have a letter, and should read it.");
                        return;
                    }
                    if (!player.getInventory().isFull()) {
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getInventory().add(22051, 1);
                        player.getPacketSender()
                                .sendMessage("<img=5> Take a look at the Bunny's letter for more instructions!");
                        player.setEaster2017(1);
                    } else {
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getPacketSender().sendMessage("You need at least 1 free inventory slot.");
                    }
                    break;
                case 198:
                    break;
                case 189:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendMessage("Party Pete hands you a gift...");
                    if (player.getInventory().isFull()) {
                        player.getPacketSender().sendMessage("Have at least 1 free slot before accepting his gift.");
                    } else {
                        player.setNewYear2017(1);
                        player.getInventory().add(2946, 1);
                        World.sendMessage("<img=5> <shad=0>@whi@" + player.getUsername()
                                + " has completed the New Year 2017 mini-event.");
                    }
                    break;
                case 187:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendMessage("Santa hands you a Present...");
                    if (player.getInventory().isFull()) {
                        player.getPacketSender().sendMessage("Make room in your inventory before accepting his gift!");
                    } else {
                        player.setchristmas2016(7);
                        player.getInventory().add(15420, 1);
                        World.sendMessage("<img=5> <shad=0>@red@" + player.getUsername()
                                + " has completed the @gre@Christmas 2016 event!");
                    }
                    break;
                case 505050:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendMessage("Jack wants me to get the following...");
                    player.getPacketSender().sendMessage("@red@<shad=0>Item list:");
                    player.getPacketSender()
                            .sendMessage("@gre@<shad=0>100 Law, 100 Cosmic, 100 Nature runes, 1 Wizard Mind Bomb.");
                    player.getPacketSender()
                            .sendMessage("@whi@<shad=0>Runes can be purchased. Mind Bomb is sold on the docks, South.");
                    break;
                case 180:
                    player.setchristmas2016(2);
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender().sendMessage("@red@<shad=0>Item list:");
                    player.getPacketSender()
                            .sendMessage("@gre@<shad=0>100 Law, 100 Cosmic, 100 Nature runes, 1 Wizard Mind Bomb.");
                    break;
                case 178:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getPacketSender()
                            .sendMessage("Unfortunately, ship charters are still being established. Check back soon.");
                    break;
                case 173:
                    DialogueManager.start(player, 174);
                    player.setDialogueActionId(180);
                    return;

                case 3:
                    ShopManager.getShops().get(22).open(player);
                    break;
                case 4:
                    SummoningTab.handleDismiss(player, true);
                    break;
                case 7:
                    BankPin.init(player, false);
                    break;
                case 8:
                    BankPin.deletePin(player);
                    break;
                case 16:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getBarrowsMinigameAttributes().getKillcount() < 5) {
                        player.getPacketSender()
                                .sendMessage("You must have a killcount of at least 5 to enter the tunnel.");
                        return;
                    }
                    player.moveTo(new Position(3552, 9692));
                    break;
                case 20:
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.start(player, 39);
                    player.getMinigameAttributes().getRecipeForDisasterAttributes().setPartFinished(0, true);
                    PlayerPanel.refreshPanel(player);
                    break;
                case 23:
                    DialogueManager.start(player, 50);
                    player.getMinigameAttributes().getNomadAttributes().setPartFinished(0, true);
                    player.setDialogueActionId(24);
                    PlayerPanel.refreshPanel(player);
                    break;
                case 24:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 33:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getSlayer().resetSlayerTask();
                    break;
                case 34:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getSlayer().handleInvitation(true);
                    break;
                case 37:
                    TeleportHandler.teleportPlayer(player, new Position(2961, 3882),
                            TeleportType.ANCIENT);
                    break;
                case 39:
                    TeleportHandler.teleportPlayer(player, new Position(3281, 3914),
                            TeleportType.ANCIENT);
                    break;
                case 42:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getInteractingObject() != null && player.getInteractingObject().getDefinition() != null
                            && player.getInteractingObject().getDefinition().getName().equalsIgnoreCase("flowers")) {
                        if (CustomObjects.objectExists(player.getInteractingObject().getPosition())) {
                            player.getInventory().add(FlowersData.forObject(player.getInteractingObject().getId()).itemId,
                                    1);
                            CustomObjects.deleteGlobalObject(player.getInteractingObject());
                            player.setInteractingObject(null);
                        }
                    }
                    break;
                case 44:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getMinigameAttributes().getGodwarsDungeonAttributes().setHasEnteredRoom(true);
                    player.moveTo(new Position(2911, 5203));
                    player.getPacketSender().sendMessage("You enter Nex's lair..");
                    NpcAggression.target(player);
                    break;
                case 46:
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.start(player, 82);
                    player.setPlayerLocked(true).setDialogueActionId(45);
                    break;

                case 66:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getLocation() == Location.DUNGEONEERING
                            && player.getMinigameAttributes().getDungeoneeringAttributes().getParty() == null) {
                        if (player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation() != null) {
                            player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().add(player);
                            player.getPacketSender().sendMessage("You have joined the party!");
                        }
                    }
                    player.getMinigameAttributes().getDungeoneeringAttributes().setPartyInvitation(null);
                    break;
                case 71:
                    if (player.getClickDelay().elapsed(1000)) {
                        if (Dungeoneering.doingOldDungeoneering(player)) {
                            Dungeoneering.leave(player, false, true);
                            player.getClickDelay().reset();
                        }
                    }
                    break;
                case 72:
                    if (player.getClickDelay().elapsed(1000)) {
                        if (Dungeoneering.doingOldDungeoneering(player)) {
                            Dungeoneering.leave(player, false, player.getMinigameAttributes().getDungeoneeringAttributes()
                                    .getParty().getOwner() != player);
                            player.getClickDelay().reset();
                        }
                    }
                    break;
                case 73:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.moveTo(new Position(3653, player.getPosition().getY()));
                    break;
                case 74:
                    player.getPacketSender().sendMessage("The ghost teleports you away.");
                    player.getPacketSender().sendInterfaceRemoval();
                    player.moveTo(new Position(3651, 3486));
                    break;
                case 76:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getRights().isStaff()) {
                        player.getPacketSender().sendMessage("You cannot change your rank.");
                        return;
                    }
                    player.setRights(PlayerRights.SUPPORT);
                    player.getPacketSender().sendRights();
                    break;
                case 81:
                    new TheSix(player).joinClan();
                    break;
                case 102:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getInventory().getAmount(11180) < 1) {
                        player.getPacketSender().sendMessage("You do not have enough tokens.");
                        return;
                    } else
                        player.getInventory().delete(11180, 1);
                    // So we grab the players pID too determine what Z they will be getting. Not
                    // sure how kraken handles it, but this is how we'll handle it.
                    player.moveTo(new Position(3025, 5231));
                    // player.getPacketSender().sendMessage("Index: " + player.getIndex());
                    // player.getPacketSender().sendMessage("Z: " + player.getIndex() * 4);
                    player.getPacketSender().sendMessage("Teleporting to Trio...");
                    player.getPacketSender()
                            .sendMessage("@red@Warning:@bla@ you @red@will@bla@ lose your items on death here!");
                    // Will sumbit a task to handle token remove, once they leave the minigame the
                    // task will be removed.
                    trioMinigame.handleTokenRemoval(player);
                    break;
                case 154:
                    player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(), 2));
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
            }

        } else if (id == SECOND_OPTION_OF_TWO) {
            switch (player.getDialogueActionId()) {
                case 5555:
                    DialogueManager.start(player, Christmas2023.santa_response_7(player));
                    player.setDialogueActionId(-1);
                break;
                case 155:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 167:
                case 168:
                case 169:
                case 170:
                case 171:
                case 3005:
                case 3015:
                case 3017:
                case 3018:
                case 3027:
                case 3036:
                case 3039:
                case 3043:
                case 3046:
                case 3050:
                case 3051:
                case 3052:
                case 3053:
                case 3054:
                case 6000:
                case 5006:
                case 5031:
                case 5058:
                case 5060:
                case 6060:
                case 6090:
                case 6091:
                case 6092:
                case 6095:
                case 8807:
                case 8834:

                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 569:
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "LEAVEE!");
                    break;
                case 157:
                    DialogueManager.start(player, 158);
                    player.setDialogueActionId(158);
                    break;

                case 158:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 567:
                    player.getPacketSender().sendInterfaceRemoval();
                    DialogueManager.sendStatement(player, "Come Back When Your Ready");
                    break;

                case 67:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getRaidsAttributes().getPartyInvitation() != null
                            && player.getMinigameAttributes().getRaidsAttributes().getPartyInvitation().getOwner() != null)
                        player.getMinigameAttributes().getRaidsAttributes().getPartyInvitation().getOwner()
                                .getPacketSender()
                                .sendMessage("" + player.getUsername() + " has declined your invitation.");
                    player.getMinigameAttributes().getRaidsAttributes().setPartyInvitation(null);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 1712:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 668://no
                    player.getPacketSender().sendInterfaceRemoval();
                    break;

                case 9908: // Skilling, Option 2
                  //  TeleportHandler.teleportPlayer(player, new Position(1, 1, 0), TeleportType.ANCIENT);
                    player.sendMessage("Custom Mining + Woodcutting coming soon!");
                    player.getPacketSender().sendInterfaceRemoval();
                    break;


                case 622:
                case 617:

                case 722:

                case 817:
                case 822:

                case 1017:
                case 1022:
                    player.getPA().sendInterfaceRemoval();
                    break;
                case 620:
                    player.sendMessage("@red@The quest will be completed when you accept your reward " + player.getUsername());
                    break;
                case 618:
                    player.moveTo(new Position(2634, 3416, 0));
                    player.sendMessage("@bla@<shad=0>Be Careful..The Undead roam here");
                   // player.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.getPA().sendInterfaceRemoval();
                    player.getMinigameAttributes().getHalloweenAttributes().setPartFinished(0, true);
                    break;
                case 621:
                    player.moveTo(new Position(2634, 3416, 0));
                    player.sendMessage("@bla@<shad=0>Be Careful..The Undead roam here");
                   // player.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.getPA().sendInterfaceRemoval();
                    player.getMinigameAttributes().getHalloweenAttributes().setPartFinished(0, true);
                    break;
                case 818:
                    player.moveTo(new Position(3104, 3499, 0));
                    player.sendMessage("There should be a Bunny around here that knows what's going on...");
                    //.performAnimation(new Animation(866));
                    player.performGraphic(new Graphic(2009));
                    player.getPA().sendInterfaceRemoval();
                    player.getMinigameAttributes().getEasterQuestAtributes().setPartFinished(0, true);
                    break;

                case 820:
                    player.sendMessage("@red@The quest wilL be completed when you accept your reward " + player.getUsername());
                    break;


                case 8005:
                case 568:
                case 9928:
                case 666:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 8002:
                    player.getBank(player.getCurrentBankTab()).open();
                    break;
                case 8001:
                case 6969:
                case 9923:
                case 71260:
                case 2012:
                case 522:
                case 523:
                case 103:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 920:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getGambling().bjTurn == 1) {
                        player.getGambling().setHostTurn();
                    } else {
                        player.getGambling().getBlackjackWinner();
                        // System.out.println("Declaring winner");
                    }

                    break;
                case 215:
                    break;
                case 178:
                    player.getPacketSender().sendInterfaceRemoval();
                    ShopManager.getShops().get(85).open(player);
                    break;
                case 505050:
                case 173:
                    player.getPacketSender().sendInterfaceRemoval();
                    ShopManager.getShops().get(28).open(player);
                    break;
                case 100000:
                    player.getPacketSender().sendInterfaceRemoval();
                    KillsTracker.open(player);
                    break;
                case 3:
                    ShopManager.getShops().get(23).open(player);
                    break;
                case 211:
                case 210:
                case 198:
                case 189:
                case 187:
                case 180:
                case 4:
                case 16:
                case 20:
                case 23:
                case 33:
                case 37:
                case 39:
                case 42:
                case 44:
                case 46:
                case 57:
                case 71:
                case 72:
                case 73:
                case 74:
                case 76:
                case 78:
                case 81:
                case 102:
                case 7:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 8:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getBank(player.getCurrentBankTab()).open();
                    break;
                case 24:
                    Nomad.startFight(player);
                    break;
                case 34:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getSlayer().handleInvitation(false);
                    break;
                case 66:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation() != null && player
                            .getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().getOwner() != null)
                        player.getMinigameAttributes().getDungeoneeringAttributes().getPartyInvitation().getOwner()
                                .getPacketSender()
                                .sendMessage("" + player.getUsername() + " has declined your invitation.");
                    player.getMinigameAttributes().getDungeoneeringAttributes().setPartyInvitation(null);
                    break;
                case 154:
                    player.moveTo(new Position(player.getPosition().getX(), player.getPosition().getY(), 0));
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
            }
        } else if (id == FIRST_OPTION_OF_THREE) {
            switch (player.getDialogueActionId()) {

                case 524:
                    if (player.getAttemptDissolve() > 0 && player.getInventory().contains(player.getAttemptDissolve())) {
                        Dissolving.DissolvingData data = Dissolving.DissolvingData.forId(player.getAttemptDissolve());
                        if (data != null) {
                            if (player.getInventory().contains(18015, 1)) {
                                player.getSkillManager().addExperience(Skill.ALCHEMY, (data.getExperience()));


                                player.sendMessage("<shad=0><col=AF70C3>You Salvaged @red@" + ItemDefinition.forId(player.getAttemptDissolve()).getName() + " <shad=0><col=AF70C3>X @red@ " + ItemDefinition.forId(data.getId()).getName() + " <shad=0><col=AF70C3>for x" + data.getRewards()[0].getAmount() + " " + ItemDefinition.forId(data.getRewards()[0].getId()).getName());
                                player.getInventory().delete(player.getAttemptDissolve(), 1);
                                player.getPacketSender().sendInterfaceRemoval();
                                player.performAnimation(new Animation(data.getAnimation()));
                                player.performGraphic(new Graphic(113));

                                if (data.getRewards()[0].getId() != 3576 && data.getRewards()[0].getId() != 3502  && data.getRewards()[0].getId() != 2064) {
                                    player.sendMessage("<shad=0><col=AF70C3>" + (data.getRewards()[0].getAmount() + "<shad=0><col=AF70C3>X Monster Essence has been added to your Pouch!"));
                                    player.setMonsteressence(player.getMonsteressence() +data.getRewards()[0].getAmount());
                                }
                                if (data.getRewards()[0].getId() == 3576) {
                                    player.sendMessage("<shad=0><col=AF70C3>" + (data.getRewards()[0].getAmount() + "<shad=0><col=AF70C3>X Slayer Essence has been added to your Pouch!"));
                                    player.setSlayeressence(player.getSlayeressence() + data.getRewards()[0].getAmount());
                                }

                                if (data.getRewards()[0].getId() == 3502) {
                                    player.sendMessage("<shad=0><col=AF70C3>" + data.getRewards()[0].getAmount() + "<shad=0><col=AF70C3>X Corrupt Essence has been added to your Pouch!");
                                    player.setCorruptEssence(player.getCorruptEssence() + data.getRewards()[0].getAmount());
                                }
                                if (data.getRewards()[0].getId() == 2064) {
                                    player.sendMessage("<shad=0><col=AF70C3>" + data.getRewards()[0].getAmount() + "<shad=0><col=AF70C3>X Spectral Essence has been added to your Pouch!");
                                    player.setSpectralEssence(player.getSpectralEssence() + data.getRewards()[0].getAmount());
                                }


                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_25_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_50_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_150_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_250_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_500_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_1000_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_2500_ITEMS, 1);
                                Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5000_ITEMS, 1);
                                StarterTasks.doProgress(player, StarterTasks.StarterTask.SALAGE_5_ITEMS, 1);
                                if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
                                    MediumTasks.doProgress(player, MediumTasks.MediumTaskData.SALVAGE_50_ITEMS, 1);
                                }
                                if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
                                    EliteTasks.doProgress(player, EliteTasks.EliteTaskData.SALVAGE_100_ITEMS, 1);
                                }


                                //HANDLE DISSOLVE BOSS INCREMENTING
                                if (data.getRewards()[0].getId() == 19062) {
                                    DissolveBossHandler.increase(player, data.getRewards()[0].getAmount());
                                }
                                //System.out.println("GLOBAL DISSOLVE AMOUNT: " + GameSettings.DISSOLVE_AMOUNT);
                                return;
                            }

                            player.getInventory().delete(player.getAttemptDissolve(), 1);
                            player.getInventory().add(data.getRewards()[0].getId(), (data.getRewards()[0].getAmount()));
                            player.performAnimation(new Animation(data.getAnimation()));
                            player.performGraphic(new Graphic(113));
                            player.getSkillManager().addExperience(Skill.ALCHEMY, (data.getExperience()));
                            player.getPacketSender().sendInterfaceRemoval();
                            player.sendMessage("<col=AF70C3><shad=0>You Salvaged @red@ " + ItemDefinition.forId(player.getAttemptDissolve()).getName() + " <col=AF70C3><shad=0>for x<col=AF70C3><shad=0>" + data.getRewards()[0].getAmount() + " <col=AF70C3><shad=0>" + ItemDefinition.forId(data.getRewards()[0].getId()).getName());
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_25_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_50_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_150_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_250_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_500_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_1000_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_2500_ITEMS, 1);
                            Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5000_ITEMS, 1);
                            StarterTasks.doProgress(player, StarterTasks.StarterTask.SALAGE_5_ITEMS, 1);
                            if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
                                MediumTasks.doProgress(player, MediumTasks.MediumTaskData.SALVAGE_50_ITEMS, 1);
                            }
                            if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
                                EliteTasks.doProgress(player, EliteTasks.EliteTaskData.SALVAGE_100_ITEMS, 1);
                            }

                            //HANDLE DISSOLVE BOSS INCREMENTING
                            if (data.getRewards()[0].getId() == 19062) {
                                DissolveBossHandler.increase(player, data.getRewards()[0].getAmount());
                            }
                            //System.out.println("GLOBAL DISSOLVE AMOUNT: " + GameSettings.DISSOLVE_AMOUNT);
                        }
                    }
                    break;
                case 9909: // Raids, Raids
                    player.getPacketSender().sendMessage("@red@<shad=6C1894>Welcome to the CorruptRaid Raids Lobby.");
                    TeleportHandler.teleportPlayer(player, new Position(2974, 3877, 0), TeleportType.ANCIENT);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 9905:
                    player.skillingTask = true;
                    DailyTasks.checkTask(player);
                    break;

                case 9906: // Global Bosses, Global boss
                    final RaidsParty party65235 = player.getMinigameAttributes().getRaidsAttributes().getParty();
                    if (party65235 != null) {
                        player.sendMessage("Please leave your party before leaving the lobby!");
                        return;
                    }
                    if (!player.isEventstoggle()){
                        player.sendMessage("You have event Teleports Disabled, toggle them by typing ::eventson or ::eventsoff");
                        return;
                    }
                    if (player.getForgottenRaidParty() != null ) {
                        player.sendMessage("Please leave your party by doing ::leave to exit");
                        return;
                    }
                    if (RiftEvent.isAlive) {
                        player.getPacketSender().sendMessage("<shad=1>@red@Welcome to the Rift!");
                        TeleportHandler.teleportPlayer(player, new Position(3435, 2960, 3), TeleportType.ANCIENT);
                        return;
                    }
                    if (CorruptRiftEvent.isAlive) {
                        player.getPacketSender().sendMessage("<shad=1>@red@Welcome to the Corrupt Rift!");
                        TeleportHandler.teleportPlayer(player, new Position(3435, 2960, 3), TeleportType.ANCIENT);
                        return;
                    }
                    else {
                        player.sendMessage("No Global Events are Currently Active!");
                    }
                    break;
                case 65:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSlayer().getDuoPartner() != null) {
                        player.getPacketSender().sendMessage("You already have a duo partner.");
                        return;
                    }
                    player.getPacketSender()
                            .sendMessage("<img=5> To do Social slayer, simply use your Slayer gem on another player.");
                    break;
                case 30:
                    player.getSlayer().assignTask();
                    // System.out.println("TAsk assigned: - Master: " + player.getSlayer().getSlayerMaster().toString());
                    break;

                case 196:
                    break;
                case 197:
                    break;
                case 214:
                    TeleportHandler.teleportPlayer(player, new Position(3037, 9545),
                            TeleportType.ANCIENT);
                    break;
                case 15:
                    DialogueManager.start(player, 35);
                    player.setDialogueActionId(19);
                    break;
                case 19:
                    DialogueManager.start(player, 33);
                    player.setDialogueActionId(21);
                    break;
                case 21:
                    TeleportHandler.teleportPlayer(player, new Position(3080, 3498),
                            TeleportType.ANCIENT);
                    break;
                case 22:
                    TeleportHandler.teleportPlayer(player, new Position(1891, 3177),
                            TeleportType.ANCIENT);
                    break;
                case 25:
                    TeleportHandler.teleportPlayer(player, new Position(2589, 4319), TeleportType.PURO_PURO);
                    break;
                case 35:
                    player.getPacketSender()
                            .sendEnterAmountPrompt("How many shards would you like to buy? (You can use K, M, B prefixes)");
                    player.setInputHandling(new BuyShards());
                    break;
                case 36:
                    player.setDialogueActionId(83);
                    DialogueManager.start(player, 137);
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, GameSettings.CORP_CORDS,
                            TeleportType.ANCIENT);
                    break;
                case 47:
                    TeleportHandler.teleportPlayer(player, new Position(2911, 4832),
                            TeleportType.ANCIENT);
                    break;
                case 48:
                    if (player.getInteractingObject() != null) {
                        Mining.startMining(player, new GameObject(24444, player.getInteractingObject().getPosition()));
                    }
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 56:
                    TeleportHandler.teleportPlayer(player, new Position(2561, 3867),
                            TeleportType.ANCIENT);
                    break;
                case 58:
                    DialogueManager.start(player, AgilityTicketExchange.getDialogue(player));
                    break;

                case 69:
                    ShopManager.getShops().get(44).open(player);
                    player.getPacketSender().sendMessage("<img=5> <col=660000>You currently have "
                            + player.getPointsHandler().getDungeoneeringTokens() + " Dungeoneering tokens.");
                    break;
                case 70:
                case 71:
                    if (player.getInventory().contains(23020) && player.getClickDelay().elapsed(700)) {
                        int amt = player.getDialogueActionId() == 70 ? 1 : player.getInventory().getAmount(23020);
                        player.getPacketSender().sendInterfaceRemoval();
                        player.getInventory().delete(23020, amt);
                        player.getPacketSender().sendMessage(
                                "You claim the " + (amt > 1 ? "scrolls" : "scroll") + " and receive your reward.");
                        int minutes = player.getGameMode() == GameMode.NORMAL ? 10 : 5;
                        if (player.getMinutesBonusExp() < 1) {
                            player.getBonusXp().init();
                        }
                        BonusExperienceTask.addBonusXp(player, minutes * amt);
                        player.getPacketSender().sendString(48402, "" + player.getMinutesBonusExp() + " minutes");
                        player.getClickDelay().reset();
                    }
                    break;
                case 86:
                    Construction.buyHouse(player);
                    break;
                case 99:
                    DialogueManager.start(player, 147);
                    player.setDialogueActionId(102);
                    break;
            }
        } else if (id == SECOND_OPTION_OF_THREE) {
            switch (player.getDialogueActionId()) {
                case 524:
                    int itemId = player.getAttemptDissolve();
                    int itemAmount = player.getDissolving().getDissolvableItemsInInv(itemId);
                    player.getDissolving().handle(itemId, itemAmount, false);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 9905:
                    player.skillingTask = false;
                    DailyTasks.checkTask(player);
                    break;
                case 9909: // Raids, Raids
                    player.sendMessage("COMING SOON");
                   // TeleportHandler.teleportPlayer(player, new Position(3249, 3358, 0), TeleportType.ANCIENT);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 9906: // Global Bosses, Vote boss
                    TeleportHandler.teleportPlayer(player, new Position(2970, 2776, 0), TeleportType.ANCIENT);
                    player.sendMessage("Face the Vote Boss! Thanks for your support.");
                    break;


                case 65:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSlayer().getDuoPartner() != null) {
                        Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
                    } else {
                        player.sendMessage("<img=5> You do not have a duo partner!");
                    }
                    break;
                case 30:
                    ShopManager.getShops().get(40).open(player);
                    break;
                case 196:
                    break;
                case 197:
                    break;
                case 15:
                    DialogueManager.start(player, 25);
                    player.setDialogueActionId(15);
                    break;
                case 214:
                    TeleportHandler.teleportPlayer(player, new Position(2738, 3467),
                            TeleportType.ANCIENT);
                    break;
                case 13:
                    player.setDialogueActionId(78);
                    DialogueManager.start(player, 199);
                    break;
                case 21:
                    RecipeForDisaster.openQuestLog(player);
                    break;
                case 19:
                    DialogueManager.start(player, 33);
                    player.setDialogueActionId(22);
                    break;
                case 22:
                    Nomad.openQuestLog(player);
                    break;
                case 25:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getCurrentLevel(Skill.BEAST_HUNTER) < 23) {
                        player.getPacketSender()
                                .sendMessage("You need a Hunter level of at least 23 to visit the hunting area.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2922, 2885),
                            TeleportType.ANCIENT);
                    break;
                case 35:
                    player.getPacketSender().sendEnterAmountPrompt(
                            "How many shards would you like to sell? (You can use K, M, B prefixes)");
                    player.setInputHandling(new SellShards());
                    break;
                case 41:
                    TeleportHandler.teleportPlayer(player, new Position(2903, 5204),
                            TeleportType.ANCIENT);
                    break;
                case 47:
                    TeleportHandler.teleportPlayer(player, new Position(2979, 3237),
                            TeleportType.ANCIENT);
                    player.getPacketSender().sendMessage("Welcome to the new Mining area.");
                    break;
                case 48:
                    if (player.getInteractingObject() != null) {
                        Mining.startMining(player, new GameObject(24445, player.getInteractingObject().getPosition()));
                    }
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 56:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < 60) {
                        player.getPacketSender()
                                .sendMessage("You need a Woodcutting level of at least 60 to teleport there.");
                        return;
                    }
                    TeleportHandler.teleportPlayer(player, new Position(2558, 3884),
                            TeleportType.ANCIENT);
                    break;
                case 58:
                    ShopManager.getShops().get(39).open(player);
                    break;
                case 69:
                    if (player.getClickDelay().elapsed(1000)) {
                        Dungeoneering.start(player);
                    }
                    break;
                case 70:
                case 71:
                    boolean all = player.getDialogueActionId() == 71;
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getInventory().getFreeSlots() == 0) {
                        player.getPacketSender().sendMessage("You do not have enough free inventory space to do that.");
                        return;
                    }
                    if (player.getInventory().contains(23020) && player.getClickDelay().elapsed(700)) {
                        int amt = !all ? 1 : player.getInventory().getAmount(23020);
                        player.getInventory().delete(23020, amt);
                        player.getPointsHandler().incrementVotingPoints(amt);
                        PlayerPanel.refreshPanel(player);
                        player.getPacketSender().sendMessage(
                                "You claim the " + (amt > 1 ? "scrolls" : "scroll") + " and receive your reward.");
                        player.getClickDelay().reset();
                    }
                    player.getPacketSender().sendString(48402, "" + player.getMinutesBonusExp() + " minutes");
                    break;
                case 36:
                    DialogueManager.start(player, 65);
                    break;
                case 99:
                    player.getPacketSender().sendInterfaceRemoval();
                    if (player.getInventory().getAmount(11180) < 1) {
                        player.getPacketSender().sendMessage("You do not have enough tokens.");
                        return;
                    } else
                        player.getInventory().delete(11180, 1);
                    // So we grab the players pID too determine what Z they will be getting. Not
                    // sure how kraken handles it, but this is how we'll handle it.
                    player.moveTo(new Position(3025, 5231));
                    // player.getPacketSender().sendMessage("Index: " + player.getIndex());
                    // player.getPacketSender().sendMessage("Z: " + player.getIndex() * 4);
                    player.getPacketSender().sendMessage("Teleporting to Trio...");
                    player.getPacketSender()
                            .sendMessage("@red@Warning:@bla@ you @red@will@bla@ lose your items on death here!");
                    // Will sumbit a task to handle token remove, once they leave the minigame the
                    // task will be removed.
                    trioMinigame.handleTokenRemoval(player);
                    break;
            }
        } else if (id == THIRD_OPTION_OF_THREE) {
            switch (player.getDialogueActionId()) {
                case 9909: // Raids, Raids
                    player.getPacketSender().sendMessage("<col=3E0957><shad=6C1894>Welcome to the Narnia Raids Lobby!");
                     TeleportHandler.teleportPlayer(player, new Position(3249, 3358, 0), TeleportType.ANCIENT);
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 9906: // Global Bosses, Event boss
                    TeleportHandler.teleportPlayer(player, new Position(3126, 3473, 0), TeleportType.ANCIENT);
                    player.getPacketSender().sendMessage("Welcome to the Event Boss Area");
                    break;

                case 9912: // Donation zones, Option 1
                    DialogueManager.start(player, 9910);
                    player.setDialogueActionId(9910);
                    break;
                case 9905:
                case 9902:
                case 30:
                case 10:
                case 13:
                case 15:
                case 19:
                case 21:
                case 22:
                case 25:
                case 35:
                case 36:
                case 47:
                case 48:
                case 56:
                case 58:
                case 61:
                case 62:
                case 63:
                case 64:
                case 69:
                case 70:
                case 71:
                case 77:
                case 86:
                case 99:
                case 196:
                case 197:
                case 65:
                case 524:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case 41:
                    player.setDialogueActionId(36);
                    DialogueManager.start(player, 65);
                    break;
            }
        }
    }

}
