package com.ruse.net.packet.impl;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.WalkToTask;
import com.ruse.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Shop;
import com.ruse.model.definitions.GameObjectDefinition;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.clip.region.RegionClipping;
import com.ruse.world.content.*;
import com.ruse.world.content.SlayerChests.BeastChest;
import com.ruse.world.content.SlayerChests.BeginnerSlayerChest;
import com.ruse.world.content.SlayerChests.EliteSlayerChest;
import com.ruse.world.content.SlayerChests.MediumSlayerChest;
import com.ruse.world.content.ZoneProgression.ZoneHeightTeleporter;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.new_raids_system.instances.CorruptRaid;
import com.ruse.world.content.new_raids_system.instances.VoidRaid;
import com.ruse.world.content.parties.PartyInterface;
import com.ruse.world.content.parties.PartyService;
import com.ruse.world.content.parties.PartyType;
import com.ruse.world.content.raids.invocation.impl.TowerInvocations;
import com.ruse.world.content.skill.impl.construction.Construction;
import com.ruse.world.content.skill.impl.mining.Mining;
import com.ruse.world.content.skill.impl.mining.MiningData;
import com.ruse.world.content.skill.impl.mining.Prospecting;
import com.ruse.world.content.skill.impl.thieving.Stalls;
import com.ruse.world.content.skill.impl.woodcutting.Woodcutting;
import com.ruse.world.content.skill.impl.woodcutting.WoodcuttingData;
import com.ruse.world.content.skill.skillable.impl.Journeymen;
import com.ruse.world.content.tower.TowerController;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.content.zonechests.*;
import com.ruse.world.content.zonechests.CorruptKeyChest;
import com.ruse.world.content.zonechests.EnhancedTotem;
import com.ruse.world.content.zonechests.FrenzyChest;
import com.ruse.world.content.zonechests.SpectralChest;
import com.ruse.world.content.zonechests.Tier1Totem;
import com.ruse.world.content.zonechests.Tier2Totem;
import com.ruse.world.content.zonechests.Tier3Totem;
import com.ruse.world.entity.impl.player.Player;

/**
 * This packet listener is called when a player clicked on a game object.
 *
 * @author relex lawl
 */

public class ObjectActionPacketListener implements PacketListener {

    /**
     * The PacketListener logger to debug sendInformation and print out errors.
     */
    private static void firstClick(Player player, Packet packet) {
        int x = packet.readLEShortA();
        int id = packet.readUnsignedShort();
        int y = packet.readUnsignedShortA();
        Position position = new Position(x, y, player.getPosition().getZ());
        GameObject gameObject = new GameObject(id, position);
        String objectName = gameObject.getDefinition() != null ? gameObject.getDefinition().getName() : "Hello";
        if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject)) {
            if (player.getRights().OwnerDeveloperOnly()) {
                player.getPacketSender().sendMessage("A interaction error occured. Error code: " + id);
            } else {
                player.getPacketSender().sendMessage("Nothing interesting happens.");
            }
            return;
        }
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? GameObjectDefinition.forId(id).getSizeX()
                : GameObjectDefinition.forId(id).getSizeY();
        if (size <= 0)
            size = 1;
        gameObject.setSize(size);
        if (player.getMovementQueue().isLockMovement())
            return;

        if (player.getRights() == PlayerRights.DEVELOPER)
            player.getPacketSender().sendMessage("First click object id; [id, position] : [" + id + ", " + position + "]");
        player.setInteractingObject(gameObject)
                .setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), () -> {
                    player.setPositionToFace(gameObject.getPosition());
                    if (player.getSkillManager().startSkillable(gameObject)) {
                        return;
                    }

//                    if (WoodcuttingData.Trees.forId(id) != null) {
//                        Woodcutting.cutWood(player, gameObject, false);
//                        return;
//                    }

                    if (MiningData.forRock(gameObject.getId()) != null) {
                        Mining.startMining(player, gameObject);
                        return;
                    }
                    if (!player.getControllerManager().processObjectClick1(gameObject)) {
                        return;
                    }

                    if (player.getDoorPick().handleObjectClick(id, x, y)) {
                        return;
                    }

                    if (BalloonDropParty.pop(player, gameObject)) {
                        return;
                    }


                    if (gameObject.getDefinition() != null && gameObject.getDefinition().getName() != null
                            && objectName.equalsIgnoreCase("door")
                            && player.getRights().OwnerDeveloperOnly()) {
                        player.getPacketSender().sendMessage("You just clicked a door. ID: " + id);
                    }

                    if (gameObject.getDefinition() != null && gameObject.getDefinition().getName() != null) {
                        if (objectName.toLowerCase().contains("bank")) {
                            if (player.getGameMode() == GameMode.GROUP_IRON
                                    && player.getIronmanGroup() != null) {
                                DialogueManager.start(player, 8002);
                                player.setDialogueActionId(8002);
                            } else {
                                player.getBank(player.getCurrentBankTab()).open();
                            }
                            return;
                        }
                    }

                    switch (id) {
                        case 18835:
                            if (player.claimedGear){
                                player.msgRed("You've already claimed your training gear.");
                                return;
                            }
                            DialogueManager.start(player, 5049);
                            player.setDialogueActionId(5049);
                            break;
                        case 13637:
                            if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                                DialogueManager.sendStatement(player, "You must start a Raid party first.");
                                return;
                            }


                            switch (player.getLocation()){
                                case VOID_RAID_LOBBY:
                                    new SelectionDialogue(player,"",
                                            new SelectionDialogue.Selection("Easy", 0, p -> {
                                                player.setVoidRaidDifficulty(1);
                                                VoidRaid.startRaid(player);
                                            }),
                                            new SelectionDialogue.Selection("Medium", 1, p -> {
                                                if (player.getVoidCompletions() < 50){
                                                    player.msgRed("Complete atleast 50 Easy Raids First!");
                                                    p.getPacketSender().sendChatboxInterfaceRemoval();
                                                    return;
                                                }
                                                player.setVoidRaidDifficulty(2);
                                                VoidRaid.startRaid(player);
                                            }),
                                            new SelectionDialogue.Selection("Hard", 2, p -> {
                                                if (player.getVoidMediumCompletions() < 175){
                                                    player.msgRed("Complete atleast 175 Medium Raids First!");
                                                    p.getPacketSender().sendChatboxInterfaceRemoval();
                                                    return;
                                                }
                                                player.setVoidRaidDifficulty(3);
                                                VoidRaid.startRaid(player);
                                            })).start();
                                    break;
                            }
                            break;
                        case 13621:
                            if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                                DialogueManager.sendStatement(player, "You must start a Raid party first.");
                                return;
                            }


                                switch (player.getLocation()){
                                    case CORRUPT_RAID_LOBBY:
                                        new SelectionDialogue(player,"",
                                                new SelectionDialogue.Selection("Easy", 0, p -> {
                                                    player.setCorruptRaidDifficulty(1);
                                                    CorruptRaid.startRaid(player);
                                                }),
                                                new SelectionDialogue.Selection("Medium", 1, p -> {
                                                    if (player.getCorruptCompletions() < 50){
                                                        player.msgRed("Complete atleast 50 Easy Raids First!");
                                                        p.getPacketSender().sendChatboxInterfaceRemoval();
                                                        return;
                                                    }
                                                    player.setCorruptRaidDifficulty(2);
                                                    CorruptRaid.startRaid(player);
                                                }),
                                                new SelectionDialogue.Selection("Hard", 2, p -> {
                                                    if (player.getCorruptMediumCompletions() < 175){
                                                        player.msgRed("Complete atleast 175 Medium Raids First!");
                                                        p.getPacketSender().sendChatboxInterfaceRemoval();
                                                        return;
                                                    }
                                                    player.setCorruptRaidDifficulty(3);
                                                    CorruptRaid.startRaid(player);
                                                })).start();
                                        break;
                                }
                            break;
                        case 52768:
                            if (!player.getUsername().equalsIgnoreCase("hades")) {
                                if (player.getAfkStallCount1() < 100000) {
                                    player.msgRed("You need to Siphon at least 100,000 Energy before you can Enter.");
                                    player.msgRed("You have " + player.getAfkStallCount1() + " of 100,000 required.");
                                    return;
                                }
                            }

                            player.msgFancyPurp("You Enter the Afk Boss Lair!");
                            TeleportHandler.teleportPlayer(player, new Position(2654, 2522, 0), TeleportType.ANCIENT);
                            break;

                        case 23114:
                            player.getInventory().add(12637, 1);
                            break;

                        case 17953:
                            if (player.fixedFerry == false) {
                                player.performAnimation(new Animation(11175));
                                player.setFixedFerry(true);
                                player.sendMessage("You have fixed Charons Ferry!");
                            } else {
                                player.sendMessage("You have already fixed Charons Ferry");
                                }
                            break;

                        case 27306:
                            Stalls.stealFromAFKStall(player, id, 1);
                            break;
                        case 18793://NEW BARROWS
                            if (player.searchedKingsWardrobe || player.getEasterQuestStage() != 2)
                                return;
                            int chanceToFind = Misc.random(0,5);
                            if (chanceToFind == 0){
                                player.getInventory().add(717,1);
                                player.setSearchedKingsWardrobe(true);
                                player.msgFancyPurp("You manage to find a Quest Material!");
                                return;
                            }
                            break;
                        case 43667://NEW BARROWS

                            if (!player.unlockedGeovigorPrayer) {
                                player.msgRed("You Must have Geovigor Unlocked.");
                                return;
                            }
                            if (!player.unlockedSeaslicerPrayer) {
                                player.msgRed("You Must have SeaSlicer Unlocked.");
                                return;
                            }
                            if (!player.unlockedBlazeupPrayer) {
                                player.msgRed("You Must have BlazeUp Unlocked.");
                                return;
                            }
                            if (!player.unlockedStonehavenPrayer) {
                                player.msgRed("You Must have Stonehaven Unlocked.");
                                return;
                            }
                            if (!player.unlockedSwifttidePrayer) {
                                player.msgRed("You Must have SwiftTide Unlocked.");
                                return;
                            }
                            if (!player.unlockedInfernoPrayer) {
                                player.msgRed("You Must have Inferno Unlocked.");
                                return;
                            }
                            
                            if (player.getSkillManager().getMaxLevel(Skill.NECROMANCY) < 85) {
                                player.msgRed("Reach 85 Necromancy!");
                                return;
                            }

                            new SelectionDialogue(player,"",
                                    new SelectionDialogue.Selection("Enter the Wizards Lair?", 0, p -> {
                                        p.msgFancyPurp("You Enter the Wizard's Lair!");
                                        TeleportHandler.teleportPlayer(player, new Position(2916, 2593, 0), TeleportType.ANCIENT);
                                    }),
                                    new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();

                            break;
                        case 38150://NEW BARROWS
                            TeleportHandler.teleportPlayer(player, new Position(2916, 2593, 0), TeleportType.ANCIENT);

                      /*      new SelectionDialogue(player,"",
                                new SelectionDialogue.Selection("Return to Lobby", 0, p -> {
                                    p.msgFancyPurp("You return to the Wizard's Lair!");
                                    TeleportHandler.teleportPlayer(player, new Position(2912, 2587, 0), TeleportType.ANCIENT);
                                }),
                                new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();*/

                        break;
                        case 41200://NEW BARROWS
                        case 41201://NEW BARROWS
                        case 41202://NEW BARROWS
                        case 29577://NEW BARROWS

                            player.vod.handleGameObjects(gameObject);
                            break;

                        case 2193://GREEN DOOR
                            TeleportHandler.teleportPlayer(player, new Position(2787, 2644, 0), TeleportType.ANCIENT);
                            player.msgFancyPurp("You enter the Green Door!");
                            break;
                        case 2189://BLUE DOOR
                            TeleportHandler.teleportPlayer(player, new Position(2787, 2517, 0), TeleportType.ANCIENT);
                            player.msgFancyPurp("You enter the Blue Door!");
                            break;
                        case 2188://RED DOOR
                            TeleportHandler.teleportPlayer(player, new Position(2851, 2516, 0), TeleportType.ANCIENT);
                            player.msgFancyPurp("You enter the Red Door!");
                            break;
                        case 21432:
                            new SelectionDialogue(player, "Upgrade Blade?",
                                    new SelectionDialogue.Selection("Tier 2 Upgrade (65% Chance) - 10 Energy", 0, p -> {
                                        int chanceForSuccess = Misc.random(0,100);
                                        if (!player.getInventory().contains(7784)){
                                            player.msgPurp("You need a Soulstone Blade(1) to do this!");
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            return;
                                        }
                                        if (player.getInventory().getAmount(7783) < 10) {
                                            player.msgPurp("You need atleast 10 Necrotic Energy to do this!");
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            return;
                                        }
                                        p.getPacketSender().sendChatboxInterfaceRemoval();

                                        player.getInventory().delete(7784,1);
                                        player.getInventory().delete(7783,10);


                                        if (chanceForSuccess >= 45){
                                            player.getInventory().add(7785,1);
                                            p.msgFancyPurp("You successfully upgraded your Soulstone Blade to Tier 2!");
                                            SkillingPetBonuses.checkSkillingPet(player,5000);
                                            if (player.getNecroBoost().isActive()){
                                                SkillingPetBonuses.checkSkillingPet(player,5000);
                                            }
                                            p.setTier2GauntletWeapon(true);
                                            String message = player.getUsername() + "  upgraded their Soulstone Blade to Tier 2!";
                                            NecromancyMinigame.sendGameMessage(message);
                                        } else {
                                            p.msgRed("You failed to upgrade your Soulstone Blade..");
                                            String message = player.getUsername() + "  failed to upgrade their Soulstone Blade to Tier 2!";
                                            NecromancyMinigame.sendGameMessage(message);
                                            player.getInventory().add(7784,1);


                                        }
                                    }),
                                    new SelectionDialogue.Selection("Tier 3 Upgrade (65% Chance) - 25 Energy", 1, p -> {
                                        int chanceForSuccess = Misc.random(0,100);
                                        if (!player.getInventory().contains(7785)){
                                            player.msgPurp("You need a Soulstone Blade(2) to do this!");
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            return;
                                        }
                                        if (player.getInventory().getAmount(7783) < 25) {
                                            player.msgPurp("You need atleast 25 Necrotic Energy to do this!");
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            return;
                                        }

                                        player.getInventory().delete(7785,1);
                                        player.getInventory().delete(7783,25);

                                        if (chanceForSuccess >= 65){
                                            player.getInventory().add(7786,1);
                                            p.msgFancyPurp("You successfully upgraded your Soulstone Blade to Tier 3!");
                                            SkillingPetBonuses.checkSkillingPet(player,10000);
                                            if (player.getNecroBoost().isActive()){
                                                SkillingPetBonuses.checkSkillingPet(player,10000);
                                            }
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            p.setTier2GauntletWeapon(false);
                                            p.setTier3GauntletWeapon(true);
                                            String message = player.getUsername() + "  upgraded their Soulstone Blade to Tier 3!";
                                            NecromancyMinigame.sendGameMessage(message);

                                        } else {
                                            p.msgRed("You failed to upgrade your Soulstone Blade..");
                                            String message = player.getUsername() + "  failed to upgrade their Soulstone Blade to Tier 3!";
                                            NecromancyMinigame.sendGameMessage(message);
                                            player.getInventory().add(7785,1);

                                        }
                                    }),
                                    new SelectionDialogue.Selection("Nevermind...", 2, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
                            break;
                        case 21433:

                            if (!player.getClickDelay().elapsed(2000))
                                return;

                            if (player.crystalRunning == true){
                                return;
                            }
                            int chanceForFrags = Misc.random(0,1);

                            player.getClickDelay().reset();
                            player.crystalRunning = true;
                            player.performAnimation(new Animation(713));

                            TaskManager.submit(new Task(4, player, false) {
                                @Override
                                protected void execute() {
                                    if (chanceForFrags != 0){
                                        stop();
                                        player.crystalRunning = false;
                                        return;
                                    }

                                    player.msgFancyPurp("You manage to siphon some energy from the Crystals!");
                                    player.getInventory().add(7783,1);
                                    player.crystalRunning = false;
                                    stop();
                                }
                            });
                            break;
                        case 3913:
                            if (!player.getClickDelay().elapsed(2000))
                                return;
                            if (player.getMysticMushTaskAmount() <= 0) {
                                player.msgRed("A magical force prevents you from picking the Mushroom!");
                                break;
                            }
                            if (player.getInventory().getFreeSlots() <= 1) {
                                player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                                return;
                            }
                            player.getClickDelay().reset();
                            player.performAnimation(new Animation(2291));

                            player.getInventory().add(17821,1);
                            player.msgFancyPurp("You managed to pick a Mystic Mushroom!");
                            break;



                        case 3917:
                            if (!player.getClickDelay().elapsed(2000))
                                return;
                            if (player.getCorruptMushTaskAmount() <= 0) {
                                player.msgRed("A magical force prevents you from picking the Mushroom!");
                                break;
                            }
                            if (player.getInventory().getFreeSlots() <= 1) {
                                player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                                return;
                            }
                            player.getClickDelay().reset();
                            player.performAnimation(new Animation(2291));
                            player.getInventory().add(17407,1);
                            player.msgFancyPurp("You managed to pick a Corrupt Mushroom!");
                            break;
                        case 6490:
                            new SelectionDialogue(player,"Quick Travel?",
                                    new SelectionDialogue.Selection("Main Camp", 0, p -> {

                                        TeleportHandler.teleportPlayer(player, new Position(2690, 3834, 0), TeleportType.ANCIENT);
                                        player.msgFancyPurp("You travel to the Main Village Camp!");
                                        p.getPacketSender().sendChatboxInterfaceRemoval();

                                    }),
                                    new SelectionDialogue.Selection("Outer Limits", 1, p -> {

                                        TeleportHandler.teleportPlayer(player, new Position(2727, 3839, 0), TeleportType.ANCIENT);
                                        player.msgFancyPurp("You travel to the Outer Limits!");
                                        p.getPacketSender().sendChatboxInterfaceRemoval();

                                    }),
                                    new SelectionDialogue.Selection("Wolf Camp", 2, p -> {

                                        if (!player.unlockedWolfCamp){
                                            player.msgRed("You have not unlocked the Wolf Camp yet!");
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            return;
                                        }
                                        TeleportHandler.teleportPlayer(player, new Position(2729, 3787, 0), TeleportType.ANCIENT);
                                        player.msgFancyPurp("You travel to the Forest Wolf Camp!");
                                        p.getPacketSender().sendChatboxInterfaceRemoval();

                                    }),
                                    new SelectionDialogue.Selection("Demon Camp", 3, p -> {
                                        if (!player.unlockedDemonCamp){
                                            player.msgRed("You have not unlocked the Demon Camp yet!");
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            return;
                                        }
                                        TeleportHandler.teleportPlayer(player, new Position(2712, 3882, 0), TeleportType.ANCIENT);
                                        player.msgFancyPurp("You travel to the Forest Demon Camp!");
                                        p.getPacketSender().sendChatboxInterfaceRemoval();

                                    }),
                                    new SelectionDialogue.Selection("Titan Camp", 4, p -> {
                                        if (!player.unlockedTitanCamp){
                                            player.msgRed("You have not unlocked the Titan Camp yet!");
                                            p.getPacketSender().sendChatboxInterfaceRemoval();
                                            return;
                                        }
                                        player.msgFancyPurp("You travel to the Forest Titan Camp!");
                                        TeleportHandler.teleportPlayer(player, new Position(2666, 3795, 0), TeleportType.ANCIENT);
                                        p.getPacketSender().sendChatboxInterfaceRemoval();

                                    })).start();

                            break;
                        case 28115:
                            PartyInterface.openCurrentParties(player, PartyType.TOWER_OF_ASCENSION);
                        break;

                        case 22942:
                            if (!player.getClickDelay().elapsed(2000))
                                return;
                            if (!PartyService.playerIsInParty(player)) {
                                player.sendMessage("You must be in a party to do this.");
                                return;
                            }

                            if (!PartyService.isPlayerPartyOwner(player)) {
                                player.sendMessage("You must be the party leader to do this.");
                                return;
                            }
                            player.getClickDelay().reset();
                            player.setRaid(new TowerController());
                            player.getTowerRaid().setParty(player.getTowerParty());
                            player.getTowerRaid().start(player);
                        break;
                        case 16090: // FRENZY BARRIER
                            if (!player.getInventory().containsAll(new Item(6754, 1))) {
                                if (player.getLocation() != Location.FRENZYBOSS){
                                    player.moveTo(new Position(2513, 2772, 0));
                                }
                            }
                            break;

                        case 6774: //FRENZY CHEST HANDLE
                            FrenzyChest.handleChest(player);
                            break;

                        case 8469:
                            if (GameSettings.FRENZY_EVENT == false){
                                player.msgRed("Frenzy Event is not active!");
                                return;
                            }
                            if (GameSettings.FRENZY_EVENT == true) {
                                player.msgPurp("You Travel to the Frenzy Boss Room");
                                TeleportHandler.teleportPlayer(player, new Position(2513, 2773, 0), TeleportType.ANCIENT);
                                return;
                            }
                            break;

                        case 407:
                            if (!player.getInventory().contains(1468)) {
                                player.getInventory().add(1468, 1);
                            }
                        break;
                        case 50193:
                            if (Journeymen.smeltBar(player)) {
                                return;
                            }
                        break;
                 case 4468://EARTH GATE PRAYER MINIGAME
                     if (player.getPrayerMinigameMinionKills() <= 99){
                         player.sendMessage("@red@<shad=0>You need atleast 100 Minion kills to Start a boss fight");
                         return;
                     }

                     DialogueManager.start(player, 6090);
                     player.setDialogueActionId(6090);
                     break;
                 case 4469://WATER GATE PRAYER MINIGAME
                     if (player.getPrayerMinigameMinionKills() <= 99){
                         player.sendMessage("@red@<shad=0>You need atleast 100 Minion kills to Start a boss fight");
                         return;
                     }

                     DialogueManager.start(player, 6091);
                     player.setDialogueActionId(6091);
                     break;
                 case 4470://FIRE GATE PRAYER MINIGAME
                     if (player.getPrayerMinigameMinionKills() <= 99){
                         player.sendMessage("@red@<shad=0>You need atleast 100 Minion kills to Start a boss fight");
                         return;
                     }

                     DialogueManager.start(player, 6092);
                     player.setDialogueActionId(6092);
                     break;
                        case 52://GAIA FIRST BARRIER
                            if (player.getGaiaCrystalStage() == 0 && !player.getInventory().contains(17059, 1)){
                                player.msgRed("You need a Gaia Sigil to Enter room 2!");
                                return;
                            }
                            if (player.getInventory().contains(17059, 1)){
                                player.setGaiaCrystalStage(1);
                                player.getInventory().delete(17059, 1);
                                player.moveTo(2212,5330,0);
                                player.msgFancyPurp("You have advanced to Gaia room 2!");
                                DialogueManager.sendStatement(player, "You have advanced to Gaia room 2!");
                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;
                        case 996://GAIA SECOND BARRIER
                            if (player.getGaiaCrystalStage() == 1 && player.getInventory().getAmount(17060) < 5){
                                player.msgRed("You need 5 Gaia Fragments to Enter room 3!");
                                return;
                            }
                            if (player.getInventory().getAmount(17060) >= 5){
                                player.setGaiaCrystalStage(2);
                                player.getInventory().delete(17060, 5);
                                player.moveTo(2216,5356,0);
                                player.getInventory().add(17011, 1);
                                player.msgFancyPurp("Obtain the Aqua Shard to Enchant your Crystal!");
                                player.msgFancyPurp("You have advanced to Gaia room 3!");
                                DialogueManager.sendStatement(player, "You have advanced to Gaia room 3!");
                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;

                        case 59://LAVA FIRST BARRIER
                            if (player.getLavaCrystalStage() == 0 && !player.getInventory().contains(17061)){
                                player.msgRed("You need a Lava Sigil to Enter room 2!");
                                return;
                            }
                            if (player.getInventory().contains(17061, 1)){
                                player.setLavaCrystalStage(1);
                                player.getInventory().delete(17061, 1);
                                player.moveTo(2075,5678,0);
                                player.msgFancyPurp("You have advanced to Lava room 2!");
                                DialogueManager.sendStatement(player, "You have advanced to Lava room 2!");
                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;
                        case 53://LAVA SECOND BARRIER
                            if (player.getLavaCrystalStage() == 1 && player.getInventory().getAmount(17062) < 5){
                                player.msgRed("You need 5 Lava Fragments to Enter room 3!");
                                return;
                            }
                            if (player.getInventory().getAmount(17062) >= 5){
                                player.setLavaCrystalStage(2);
                                player.getInventory().delete(17062, 5);
                                player.moveTo(2071,5657,0);
                                player.getInventory().add(17011, 1);
                                player.msgFancyPurp("Obtain the Lava Shard to Enchant your Crystal!");
                                player.msgFancyPurp("You have advanced to Lava room 3!");
                                DialogueManager.sendStatement(player, "You have advanced to Lava room 3!");
                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;

                        case 90://AQUA FIRST BARRIER
                            if (player.getAquaCrystalStage() == 0 && !player.getInventory().contains(17063, 1)){
                                player.msgRed("You need an Aqua Sigil to Enter room 2!");
                                return;
                            }
                            if (player.getInventory().contains(17063, 1)){
                                player.setAquaCrystalStage(1);
                                player.getInventory().delete(17063, 1);
                                player.moveTo(2407,3733,0);
                                player.msgFancyPurp("You have advanced to Aqua room 2!");
                                DialogueManager.sendStatement(player, "You have advanced to Aqua room 2!");
                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;
                        case 94://AQUA SECOND BARRIER
                            if (player.getAquaCrystalStage() == 1 && player.getInventory().getAmount(17064) < 5){
                                player.msgRed("You need 5 Aqua Fragments to Enter room 3!");
                                return;
                            }
                            if (player.getInventory().getAmount(17064) >= 5){
                                player.setAquaCrystalStage(2);
                                player.getInventory().delete(17064, 5);
                                player.getInventory().add(17011, 1);
                                player.msgFancyPurp("Obtain the Aqua Shard to Enchant your Crystal!");
                                player.moveTo(2388,3732,0);
                                player.msgFancyPurp("You have advanced to Aqua room 3!");
                                DialogueManager.sendStatement(player, "You have advanced to Aqua room 3!");
                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;

                        case 1093://VOID FIRST BARRIER
                            if (player.getVoidCrystalStage() == 0 && !player.getInventory().contains(760, 1)){
                                player.msgRed("You need a Void Shard to Enter room 2!");
                                return;
                            }
                            if (player.getInventory().contains(760, 1)){
                                player.setVoidCrystalStage(1);
                                player.getInventory().delete(760, 1);
                                player.moveTo(3408,5284,0);
                                player.msgFancyPurp("You have advanced to Void room 2!");
                                DialogueManager.sendStatement(player, "You have advanced to Void room 2!");
                                World.sendMessage("@red@<shad=0>" + player.getUsername() + "<col=AF70C3><shad=0> advanced to Void room 2!");

                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;
                        case 25123://VOID SECOND BARRIER
                            if (player.getVoidCrystalStage() == 1 && player.getInventory().getAmount(761) < 10) {
                                player.msgRed("You need 10 Void Fragments to Enter room 3!");
                                return;
                            }
                            if (player.getInventory().getAmount(761) >= 10) {
                                player.setVoidCrystalStage(2);
                                player.getInventory().delete(761, 99999);
                                player.moveTo(3430,5295,0);
                                player.msgFancyPurp("You have advanced to Void room 3!");
                                DialogueManager.sendStatement(player, "You have advanced to Void room 3!");
                                World.sendMessage("@red@<shad=0>" + player.getUsername() + "<col=AF70C3><shad=0> advanced to Void room 3!");

                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;

                        case 5358://VOID THIRD BARRIER
                            int doorChance = Misc.random(0,12);
                            if (player.getVoidCrystalStage() == 2 && !player.getInventory().contains(18647, 1)){
                                player.msgRed("You need a Gate Key to attempt Entering the Final Room!");
                                return;
                            }
                            if (doorChance != 0){
                                player.getInventory().delete(18647, 1);
                                player.moveTo(3430,5295,0);
                                player.msgRed("You failed to make it into the Final Room..");
                                return;
                            }
                            if (doorChance == 0 && player.getInventory().contains(18647, 1)){
                                player.setVoidCrystalStage(3);
                                player.getInventory().delete(18647, 1);
                                player.moveTo(3440,5274,0);
                                player.msgFancyPurp("You have advanced to the Final Room!");
                                DialogueManager.sendStatement(player, "You have advanced to the Final Room!");
                                World.sendMessage("@red@<shad=0>" + player.getUsername() + "<col=AF70C3><shad=0> advanced to the Final Void room!");

                                player.getPacketSender().sendFadeTransition(5, 5, 5);
                                player.getPacketSender().sendCameraShake(3, 2, 3, 2);
                                TaskManager.submit(new Task(6, player, false) {
                                    @Override
                                    protected void execute() {
                                        player.getPacketSender().sendCameraNeutrality();
                                        stop();
                                    }
                                });
                                return;
                            }
                            break;


                        case 7352://VOID PORTAL CRYSTAL MINIGAME
                            switch (player.getVoidCrystalStage()) {
                                case 0:
                                    player.msgPurp("You travel to Void Room 1");
                                    TeleportHandler.teleportPlayer(player, new Position(3405, 5259, 0), TeleportType.ANCIENT);
                                    break;
                                case 1:
                                    player.msgPurp("You travel to Void Room 2");
                                    TeleportHandler.teleportPlayer(player, new Position(3406, 5289, 0), TeleportType.ANCIENT);
                                    break;
                                case 2:
                                    player.msgPurp("You travel to Void Room 3");
                                    TeleportHandler.teleportPlayer(player, new Position(3435, 5290, 0), TeleportType.ANCIENT);
                                    break;
                                case 3:
                                    player.msgPurp("You travel to Void Room 4");
                                    TeleportHandler.teleportPlayer(player, new Position(3442, 5269, 0), TeleportType.ANCIENT);
                                    break;
                            }
                            break;


                        case 38700://EARTH PORTAL CRYSTAL MINIGAME
                            switch (player.getGaiaCrystalStage()) {
                                case 0:
                                    player.msgPurp("You travel to Gaia Room 1");
                                    TeleportHandler.teleportPlayer(player, new Position(2189, 5326, 0), TeleportType.ANCIENT);
                                    break;
                                case 1:
                                    player.msgPurp("You travel to Gaia Room 2");
                                    TeleportHandler.teleportPlayer(player, new Position(2212, 5330, 0), TeleportType.ANCIENT);
                                    break;
                                case 2:
                                    player.msgPurp("You travel to Gaia Room 3");
                                    TeleportHandler.teleportPlayer(player, new Position(2216, 5356, 0), TeleportType.ANCIENT);
                                    break;
                            }
                            break;

                        case 6282://FIRE PORTAL CRYSTAL MINIGAME
                            switch (player.getLavaCrystalStage()) {
                                case 0:
                                    player.msgPurp("You travel to Lava Room 1");
                                    TeleportHandler.teleportPlayer(player, new Position(2102, 5677, 0), TeleportType.ANCIENT);
                                    break;
                                case 1:
                                    player.msgPurp("You travel to Lava Room 2");
                                    TeleportHandler.teleportPlayer(player, new Position(2077, 5677, 0), TeleportType.ANCIENT);
                                    break;
                                case 2:
                                    player.msgPurp("You travel to Lava Room 3");
                                    TeleportHandler.teleportPlayer(player, new Position(2071, 5657, 0), TeleportType.ANCIENT);
                                    break;
                            }
                            break;

                        case 42611://AQUA PORTAL CRYSTAL MINIGAME
                            switch (player.getAquaCrystalStage()) {
                                case 0:
                                    player.msgPurp("You travel to Aqua Room 1");
                                    TeleportHandler.teleportPlayer(player, new Position(2412, 3763, 0), TeleportType.ANCIENT);
                                    break;
                                case 1:
                                    player.msgPurp("You travel to Aqua Room 2");
                                    TeleportHandler.teleportPlayer(player, new Position(2411, 3741, 0), TeleportType.ANCIENT);
                                    break;
                                case 2:
                                    player.msgPurp("You travel to Aqua Room 3");
                                    TeleportHandler.teleportPlayer(player, new Position(2392, 3732, 0), TeleportType.ANCIENT);
                                    break;
                            }
                            break;

                  case 42910:
                        if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 95) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 95 Slayer!");
                            player.sendMessage("Come back when you've reached 95 Slayer!");
                            return;
                        }
                        DialogueManager.start(player, 3060);
                        player.setDialogueActionId(3060);
                        break;

                        case 7878:
                   /*         int chance = Misc.random(0, 3);
                            int amount = Misc.random(0, 10);
                            if (NecromancyMinigame.flower_amount_reached) {
                                player.sendMessage("Your team has already sacrificed enough flowers");
                                return;
                            }

                            if (chance == 1 && !NecromancyMinigame.flower_amount_reached) {
                                player.getInventory().add(8214, amount);
                                player.sendMessage("You manage to pick " + amount + " flowers! Sacrifice these on the fire to the North East.");
                                return;
                            }*/
                            break;
                        case 30141:
                            if (player.getLocation() != Location.NECROMANCY_LOBBY_AREA) {
                                player.moveTo(3031, 3861, 0);
                                NecromancyMinigame.insertWaiting(player);
                                return;
                            }
                            player.moveTo(3034, 3861, 0);
                            NecromancyMinigame.removeWaiting(player, true);
                            break;
                        case 36000:
                            if (!player.startedTutorial){
                                DialogueManager.sendStatement(player, "Speak to Wizard Drendor before entering..");
                                return;
                            }
                            if (player.isCompletedtutorial()){
                                DialogueManager.sendStatement(player,"You probably don't want to go back in there...");
                                return;
                            }
                            player.setDialogueActionId(6000);
                            DialogueManager.start(player, 6000);
                            break;
                        case 3757:
                            player.setDialogueActionId(6000);
                            DialogueManager.start(player, 6000);
                            break;
                        case 4408:
                            ZoneHeightTeleporter.handleObjectAction(player);
                           break;
                        case 1317://BIG V HOME
                            player.getTPInterface().display();
                            break;
                        case 406: //RIFT CHEST
                            RiftChest.handleChest(player);
                            break;
                        case 1145:
                            if (!player.getInventory().contains(770) && !player.getInventory().contains(791)&& !player.getInventory().contains(792)){
                                player.getPacketSender().sendMessage("<shad=0>@red@You need a key to open the chest!");
                                return;
                            }
                            if (player.getInventory().contains(770)) {
                                CorruptRaidChest.handleChest(player);
                            } else if (player.getInventory().contains(791)) {
                                CorruptMediumRaidChest.handleChest(player);
                            } else if (player.getInventory().contains(792)) {
                                CorruptHardRaidChest.handleChest(player);
                            }
                            break;
                        case 16814:
                            if (!player.getInventory().contains(2053) && !player.getInventory().contains(2054)&& !player.getInventory().contains(2055)){
                                player.getPacketSender().sendMessage("<shad=0>@red@You need a key to open the chest!");
                                return;
                            }
                            if (player.getInventory().contains(2053)) {
                                SpectralRaidChest.handleChest(player);
                            } else if (player.getInventory().contains(2054)) {
                                SpectralRaidMediumChest.handleChest(player);
                            } else if (player.getInventory().contains(2055)) {
                                SpectralRaidHardChest.handleChest(player);
                            }
                            break;
                        case 29943: // MAGIC CHEST HOME
                            if (!player.getInventory().contains(20104) && !player.getInventory().contains(20105) && !player.getInventory().contains(20106) && !player.getInventory().contains(20107)&& !player.getInventory().contains(20108)
                                    && !player.getInventory().contains(20109)&& !player.getInventory().contains(5585) && !player.getInventory().contains(1302) && !player.getInventory().contains(1303)
                                    && !player.getInventory().contains(1304) && !player.getInventory().contains(1451) && !player.getInventory().contains(1452) && !player.getInventory().contains(20111) && !player.getInventory().contains(20112)
                                    && !player.getInventory().contains(3512) && !player.getInventory().contains(1305)
                                    && !player.getInventory().contains(770) && !player.getInventory().contains(791) && !player.getInventory().contains(792)
                                    && !player.getInventory().contains(2053) && !player.getInventory().contains(2054) && !player.getInventory().contains(2055)
                                    && !player.getInventory().contains(2062)){
                                player.getPacketSender().sendMessage("<shad=0>@red@You need a key to open the chest!");
                                return;
                            }

                            if (player.getInventory().contains(20104)) {
                                Tier1Chest.handleChest(player);
                            }
                            else if (player.getInventory().contains(20105)) {
                                Tier2Chest.handleChest(player);
                            }
                            else if (player.getInventory().contains(20106)) {
                                Tier3Chest.handleChest(player);
                            }
                            else if (player.getInventory().contains(20107)) {
                                Tier4Chest.handleChest(player);
                            }
                            else if (player.getInventory().contains(20108)) {
                                Tier5Chest.handleChest(player);
                            }
                            else if (player.getInventory().contains(20109)) {
                                Tier6Chest.handleChest(player);
                            }
                            else if (player.getInventory().contains(5585)) {
                                RiftChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(1302)) {
                                BeginnerSlayerChest.handleChest(player);
                            }

                            else if (player.getInventory().contains(1303)) {
                                MediumSlayerChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(1304)) {
                                EliteSlayerChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(1451)) {
                                XmasChest1.handleChest(player);
                            }
                            else if (player.getInventory().contains(1452)) {
                                XmasChest2.handleChest(player);
                            }
                            else if (player.getInventory().contains(20111)) {
                                Tier7Chest.handleChest(player);
                            }
                            else if (player.getInventory().contains(20112)) {
                                Tier8Chest.handleChest(player);
                            }

                            else if (player.getInventory().contains(3512)) {
                                CorruptKeyChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(1305)) {
                                BeastChest.handleChest(player);
                            }

                            else if (player.getInventory().contains(770)) {
                                CorruptRaidChest.handleChest(player);
                            }

                            else if (player.getInventory().contains(791)) {
                                CorruptMediumRaidChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(792)) {
                                CorruptHardRaidChest.handleChest(player);
                            }

                            else if (player.getInventory().contains(2053)) {
                                SpectralRaidChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(2054)) {
                                SpectralRaidMediumChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(2055)) {
                                SpectralRaidHardChest.handleChest(player);
                            }
                            else if (player.getInventory().contains(2062)) {
                                SpectralChest.handleChest(player);
                            }


                            break;
                        case 26289://SOULBANE ALTAR
                            if (!player.getInventory().contains(20080) && !player.getInventory().contains(20081) && !player.getInventory().contains(20082) && !player.getInventory().contains(3781)){
                                player.msgRed("You need a totem to make an offering!");
                                return;
                            }
                            if (player.getInventory().contains(20080)) {
                                Tier1Totem.handleChest(player);
                            }
                            else if (player.getInventory().contains(20081)) {
                                Tier2Totem.handleChest(player);
                            }
                            else if (player.getInventory().contains(20082)) {
                                Tier3Totem.handleChest(player);
                            }
                            else if (player.getInventory().contains(3781)) {
                                EnhancedTotem.handleChest(player);
                            }
                            break;
                        case 409://PRAY ALTAR HOME
                            player.performAnimation(new Animation(645));
                            if (player.getSkillManager().getCurrentLevel(Skill.PRAYER) < player.getSkillManager()
                                    .getMaxLevel(Skill.PRAYER)) {
                                player.getSkillManager().setCurrentLevel(Skill.PRAYER,
                                        player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
                                player.getPacketSender().sendMessage("You recharge your Prayer points.");
                            }
                            break;

                        case 1723:// DOWN STAIRCASE HOME
                            if (player.movementTaskRunning){
                                return;
                            }
                            player.setMovementTaskRunning(true);
                            player.performAnimation(new Animation(7376));
                            player.getPacketSender().sendFadeTransition(50, 50, 50);
                            TaskManager.submit(new Task(3, player, false) {
                                @Override
                                public void execute() {
                                    player.setMovementTaskRunning(false);
                                    player.moveTo(3157, 3556, 0);
                                    stop();
                                }
                            });
                            break;
                        case 16173://UP STAIRCASE HOME
                            if (player.movementTaskRunning){
                                return;
                            }
                            player.setMovementTaskRunning(true);
                            player.performAnimation(new Animation(7376 ));
                            player.getPacketSender().sendFadeTransition(50, 50, 50);
                            TaskManager.submit(new Task(3, player, false) {
                                @Override
                                public void execute() {
                                    player.setMovementTaskRunning(false);
                                    player.moveTo(2965, 3240, 2);
                                    stop();
                                }
                            });
                            break;
                        case 11740://UP LADDER SLAYER
                            if (player.movementTaskRunning){
                                return;
                            }
                            player.setMovementTaskRunning(true);
                            player.performAnimation(new Animation(10023));
                            player.getPacketSender().sendFadeTransition(50, 50, 50);
                            TaskManager.submit(new Task(3, player, false) {
                                @Override
                                public void execute() {
                                    player.setMovementTaskRunning(false);
                                    player.moveTo(3158, 3549, 0);
                                    stop();
                                }
                            });
                            break;

                        case 10://DOWN LADDER HOME
                            if (player.movementTaskRunning){
                                return;
                            }
                            player.setMovementTaskRunning(true);
                            player.performAnimation(new Animation(9727));
                            player.getPacketSender().sendFadeTransition(50, 50, 50);
                            TaskManager.submit(new Task(3, player, false) {
                                @Override
                                public void execute() {
                                    player.setMovementTaskRunning(false);
                                    player.moveTo(2718, 3113, 0);
                                    player.sendMessage("You grab onto the ladder, and slide down into the darkness...");
                                    stop();
                                }
                            });
                            break;


                    }
                }));
    }

    private static void secondClick(Player player, Packet packet) {
        int id = packet.readLEShortA();
        int y = packet.readLEShort();
        int x = packet.readUnsignedShortA();
        Position position = new Position(x, y, player.getPosition().getZ());
        GameObject gameObject = new GameObject(id, position);
        if (id > 0 && id != 6 && !RegionClipping.objectExists(gameObject)) {
            // player.getPacketSender().sendMessage("An error occured. Error code:
            // "+id).sendMessage("Please report the error to a staff member.");
            return;
        }
        player.setPositionToFace(gameObject.getPosition());
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? distanceX : distanceY;
        gameObject.setSize(size);
        if (player.getRights() == PlayerRights.DEVELOPER)
            player.getPacketSender()
                    .sendMessage("Second click object id; [id, position] : [" + id + ", " + position + "]");
        player.setInteractingObject(gameObject)
                .setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
                    public void execute() {
                        if (MiningData.forRock(gameObject.getId()) != null) {
                            Prospecting.prospectOre(player, id);
                            return;
                        }
                        if (!player.getControllerManager().processObjectClick2(gameObject)) {
                            return;
                        }
                        switch (gameObject.getId()) {
                            case 13637:
                                switch (player.getLocation()) {
                                    case VOID_RAID_LOBBY:
                                        if (player.getVoidRaidDifficulty() != 1 && player.getVoidRaidDifficulty() != 2 && player.getVoidRaidDifficulty() != 3) {
                                            player.setVoidRaidDifficulty(1);
                                        }
                                        VoidRaid.startRaid(player);
                                        break;
                                }
                                break;

                            case 13621:
                                switch (player.getLocation()) {
                                    case CORRUPT_RAID_LOBBY:
                                        if (player.getCorruptRaidDifficulty() != 1 && player.getCorruptRaidDifficulty() != 2 && player.getCorruptRaidDifficulty() != 3) {
                                            player.setCorruptRaidDifficulty(1);
                                        }
                                        CorruptRaid.startRaid(player);
                                        break;
                                }

                            break;


                            case 28115:
                            //    Shop.ShopManager.getShops().get(Shop.TOWER_SHOP).open(player);
                            //    player.sendMessage("TOA Points: " + player.getPointsHandler().getRaidsTwoPoints());
                            break;
                            case 22942:
                                TowerInvocations.openInterface(player);
                            break;
                            case 29943: // MAGIC CHEST HOME
                                if (!player.getInventory().contains(20104) && !player.getInventory().contains(20105) && !player.getInventory().contains(20106) && !player.getInventory().contains(20107)&& !player.getInventory().contains(20108) && !player.getInventory().contains(20109)
                                        && !player.getInventory().contains(5585) && !player.getInventory().contains(1451) && !player.getInventory().contains(1452)

                                        && !player.getInventory().contains(1302)  && !player.getInventory().contains(1303)  && !player.getInventory().contains(1304)&& !player.getInventory().contains(20111)&& !player.getInventory().contains(20112)
                                        && !player.getInventory().contains(3512) && !player.getInventory().contains(1305)
                                        && !player.getInventory().contains(770) && !player.getInventory().contains(791) && !player.getInventory().contains(792)
                                        && !player.getInventory().contains(2053)&& !player.getInventory().contains(2054)&& !player.getInventory().contains(2055)
                                        && !player.getInventory().contains(2062)){
                                    player.getPacketSender().sendMessage("<shad=0>@red@You need a key to open the chest!");
                                    return;
                                }

                                if (player.getInventory().contains(20104)) {
                                    Tier1Chest.openAll(player, player.getInventory().getAmount(20104));
                                }
                                else if (player.getInventory().contains(20105)) {
                                    Tier2Chest.openAll(player, player.getInventory().getAmount(20105));
                                }
                                else if (player.getInventory().contains(20106)) {
                                    Tier3Chest.openAll(player, player.getInventory().getAmount(20106));
                                }
                                else if (player.getInventory().contains(20107)) {
                                    Tier4Chest.openAll(player, player.getInventory().getAmount(20107));
                                }
                                else if (player.getInventory().contains(20108)) {
                                    Tier5Chest.openAll(player, player.getInventory().getAmount(20108));
                                }
                                else if (player.getInventory().contains(20109)) {
                                    Tier6Chest.openAll(player, player.getInventory().getAmount(20109));
                                }
                                else if (player.getInventory().contains(5585)) {
                                    RiftChest.openAll(player, player.getInventory().getAmount(5585));
                                }
                                else if (player.getInventory().contains(1451)) {
                                    XmasChest1.openAll(player, player.getInventory().getAmount(1451));
                                }
                                else if (player.getInventory().contains(1452)) {
                                    XmasChest2.openAll(player, player.getInventory().getAmount(1452));
                                }
                                else if (player.getInventory().contains(20111)) {
                                    Tier7Chest.openAll(player, player.getInventory().getAmount(20111));
                                }
                                else if (player.getInventory().contains(20112)) {
                                    Tier8Chest.openAll(player, player.getInventory().getAmount(20112));
                                }

                                else if (player.getInventory().contains(1302)) {
                                    BeginnerSlayerChest.openAll(player, player.getInventory().getAmount(1302));
                                }
                                else if (player.getInventory().contains(1303)) {
                                    MediumSlayerChest.openAll(player, player.getInventory().getAmount(1303));
                                }
                                else if (player.getInventory().contains(1304)) {
                                    EliteSlayerChest.openAll(player, player.getInventory().getAmount(1304));
                                }
                                else if (player.getInventory().contains(3512)) {
                                    CorruptKeyChest.openAll(player, player.getInventory().getAmount(3512));
                                }
                                else if (player.getInventory().contains(1305)) {
                                    BeastChest.openAll(player, player.getInventory().getAmount(1305));
                                }
                                else if (player.getInventory().contains(770)) {
                                    CorruptKeyChest.openAll(player, player.getInventory().getAmount(770));
                                }
                                else if (player.getInventory().contains(791)) {
                                    CorruptMediumRaidChest.openAll(player, player.getInventory().getAmount(791));
                                }
                                else if (player.getInventory().contains(792)) {
                                    CorruptHardRaidChest.openAll(player, player.getInventory().getAmount(792));
                                }

                                else if (player.getInventory().contains(2053)) {
                                    SpectralRaidChest.openAll(player, player.getInventory().getAmount(2053));
                                }
                                else if (player.getInventory().contains(2054)) {
                                    SpectralRaidMediumChest.openAll(player, player.getInventory().getAmount(2054));
                                }
                                else if (player.getInventory().contains(2055)) {
                                    SpectralRaidHardChest.openAll(player, player.getInventory().getAmount(2055));
                                }
                                else if (player.getInventory().contains(2062)) {
                                    SpectralChest.openAll(player, player.getInventory().getAmount(2062));
                                }


                                break;
                            case 1145:
                                if (!player.getInventory().contains(770) && !player.getInventory().contains(791)&& !player.getInventory().contains(792)){
                                    player.getPacketSender().sendMessage("<shad=0>@red@You need a key to open the chest!");
                                    return;
                                }
                                if (player.getInventory().contains(770)) {
                                    CorruptRaidChest.openAll(player, player.getInventory().getAmount(770));
                                } else if (player.getInventory().contains(791)) {
                                    CorruptMediumRaidChest.openAll(player, player.getInventory().getAmount(791));
                                } else if (player.getInventory().contains(792)) {
                                    CorruptHardRaidChest.openAll(player, player.getInventory().getAmount(792));
                                }
                            break;
                            case 16814:
                                if (!player.getInventory().contains(2053) && !player.getInventory().contains(2054)&& !player.getInventory().contains(2055)){
                                    player.getPacketSender().sendMessage("<shad=0>@red@You need a key to open the chest!");
                                    return;
                                }
                                if (player.getInventory().contains(2053)) {
                                    SpectralRaidChest.openAll(player, player.getInventory().getAmount(2053));
                                } else if (player.getInventory().contains(2054)) {
                                    SpectralRaidMediumChest.openAll(player, player.getInventory().getAmount(2054));
                                } else if (player.getInventory().contains(2055)) {
                                    SpectralRaidHardChest.openAll(player, player.getInventory().getAmount(2055));
                                }
                                break;

                            case 16090: // FRENZY BARRRIER
                                if (player.getLocation() == Location.FRENZYBOSS) {
                                    if (!player.getInventory().containsAll(new Item(6754, 1))) {
                                        player.msgRed("You need Frenzy Key in order to exit!");
                                    } else {
                                        player.moveTo(new Position(2513, 2782, 0));
                                    }
                                }
                                break;
                            case 1317:
                                player.setBurnDamage(0);
                                player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
                                player.getSkillManager().setCurrentLevel(Skill.HITPOINTS, player.getSkillManager().getMaxLevel(Skill.HITPOINTS), true);
                                player.getPacketSender().sendMessage("You have been rejuvenated.");
                                player.performGraphic(new Graphic(1310));
                                player.performAnimation(new Animation(7270));
                                break;
                            case 2213:
                                player.sendMessage(player.getGameMode().toString() + ", " + player.getIronmanGroup());
                                player.getBank(player.getCurrentBankTab()).open();
                                break;
                        }
                    }
                }));
    }

    public static void resetInterface(Player player) {
        for (int i = 8145; i < 8196; i++)
            player.getPacketSender().sendString(i, "");
        for (int i = 12174; i < 12224; i++)
            player.getPacketSender().sendString(i, "");
        player.getPacketSender().sendString(8136, "Close window");
    }

    private static void thirdClick(Player player, Packet packet) {
        int x = packet.readShort();
        int y = packet.readShort();
        int id = packet.readLEShortA();

        Position position = new Position(x, y, player.getPosition().getZ());
        GameObject gameObject = new GameObject(id, position);
        if (!Construction.buildingHouse(player)) {
            if (id > 0 && !RegionClipping.objectExists(gameObject)) {
                // player.getPacketSender().sendMessage("An error occured. Error code:
                // "+id).sendMessage("Please report the error to a staff member.");
                return;
            }
        }
        player.setPositionToFace(gameObject.getPosition());
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? distanceX : distanceY;
        gameObject.setSize(size);
        if (player.getRights() == PlayerRights.OWNER) {
            player.getPacketSender()
                    .sendMessage("Third click object id; [id, position] : [" + id + ", " + position + "]");
        }
        player.setInteractingObject(gameObject);
        player.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (!player.getControllerManager().processObjectClick3(gameObject)) {
                    return;
                }
                switch (gameObject.getId()) {
                    case 26945:
                        player.setDialogueActionId(1002);
                        DialogueManager.start(player, 216);
                        break;
                    case 13192:
                        player.performAnimation(new Animation(645));
                        player.setSpellbook(player.getSpellbook() == MagicSpellbook.ANCIENT ? MagicSpellbook.LUNAR
                                : player.getSpellbook() == MagicSpellbook.LUNAR ? MagicSpellbook.NORMAL
                                : MagicSpellbook.ANCIENT);
                        player.getPacketSender()
                                .sendTabInterface(GameSettings.MAGIC_TAB, player.getSpellbook().getInterfaceId())
                                .sendMessage("Your magic spellbook is changed..");
                        Autocasting.resetAutocast(player, true);
                        break;

                }
            }
        }));

    }

    private static void fourthClick(Player player, Packet packet) {
        int x = packet.readShort();
        int y = packet.readShort();
        int id = packet.readLEShortA();

        Position position = new Position(x, y, player.getPosition().getZ());
        GameObject gameObject = new GameObject(id, position);
        //System.out.println("id:" + id + " x " + x + " y " + y);

        if (gameObject == null) {
            return;
        }
        if (!player.getControllerManager().processObjectClick4(gameObject)) {
            return;
        }
    }

    private static void fifthClick(Player player, Packet packet) {
        int id = packet.readUnsignedShortA();
        int y = packet.readUnsignedShortA();
        int x = packet.readShort();
        Position position = new Position(x, y, player.getPosition().getZ());
        GameObject gameObject = new GameObject(id, position);
        if (!Construction.buildingHouse(player)) {
            if (id > 0 && !RegionClipping.objectExists(gameObject)) {
                // player.getPacketSender().sendMessage("An error occured. Error code:
                // "+id).sendMessage("Please report the error to a staff member.");
                return;
            }
        }
        player.setPositionToFace(gameObject.getPosition());
        int distanceX = (player.getPosition().getX() - position.getX());
        int distanceY = (player.getPosition().getY() - position.getY());
        if (distanceX < 0)
            distanceX = -(distanceX);
        if (distanceY < 0)
            distanceY = -(distanceY);
        int size = distanceX > distanceY ? distanceX : distanceY;
        gameObject.setSize(size);
        if (player.getRights() == PlayerRights.DEVELOPER) {
            player.getPacketSender()
                    .sendMessage("Third click object id; [id, position] : [" + id + ", " + position + "]");
        }
        player.setInteractingObject(gameObject);
        player.setWalkToTask(new WalkToTask(player, position, gameObject.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (!player.getControllerManager().processObjectClick5(gameObject)) {
                    return;
                }

                switch (id) {

                }
                Construction.handleFifthObjectClick(x, y, id, player);
            }
        }));
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.isTeleporting() || (player.isPlayerLocked() || player.isGroupIronmanLocked()) || player.getMovementQueue().isLockMovement())
            return;
        player.afkTicks = 0;
        player.afk = false;
        switch (packet.getOpcode()) {
            case FIRST_CLICK:
                firstClick(player, packet);
                if (player.getRights().OwnerDeveloperOnly()) {
                    player.getPacketSender().sendMessage("1st click obj");
                }
                break;
            case SECOND_CLICK:
                secondClick(player, packet);
                if (player.getRights().OwnerDeveloperOnly()) {
                    player.getPacketSender().sendMessage("2nd click obj");
                }
                break;
            case THIRD_CLICK:
                thirdClick(player, packet);
                if (player.getRights().OwnerDeveloperOnly()) {
                    player.getPacketSender().sendMessage("3rd click obj");
                }
                break;
            case FOURTH_CLICK:
                if (player.getRights().OwnerDeveloperOnly()) {
                    player.getPacketSender().sendMessage("4th click obj. no handler");
                }
                // fourthClick(player, packet);
                break;
            case FIFTH_CLICK:
                fifthClick(player, packet);
                if (player.getRights().OwnerDeveloperOnly()) {
                    player.getPacketSender().sendMessage("5th click obj");
                }
                break;
        }
    }
    public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70, FOURTH_CLICK = 234,
            FIFTH_CLICK = 228;
}
