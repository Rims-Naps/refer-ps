package com.ruse.net.packet.impl;

import com.ruse.GameSettings;
import com.ruse.donation.daily.DailyDealSelector;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.container.impl.Bank.BankSearchAttributes;
import com.ruse.model.container.impl.GroupIronmanBank;
import com.ruse.model.definitions.WeaponInterfaces.WeaponInterface;
import com.ruse.model.input.impl.*;
import com.ruse.world.content.CosmeticToggleInterface;
import com.ruse.model.input.impl.Moderation.StaffInterfaceHandler;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.Sounds.Sound;
import com.ruse.world.content.clan.ClanChat;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.clan.Guild;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.combat.magic.NecromancySpells;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.weapon.CombatSpecial;
import com.ruse.world.content.combat.weapon.FightType;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.DialogueOptions;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.grandexchange.GrandExchange;
import com.ruse.world.content.groupironman.GroupManager;
import com.ruse.world.content.instanceMananger.InstanceInterfaceHandler;
//import com.ruse.world.content.loyalty_streak.LoyaltyStreakManager;
import com.ruse.donation.LifetimeStreakHandler;
import com.ruse.donation.PersonalCampaignHandler;
import com.ruse.world.content.minigames.impl.Dueling;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;

import com.ruse.world.content.parties.PartyInterface;
import com.ruse.world.content.parties.PartyService;
import com.ruse.world.content.parties.PartyType;
import com.ruse.world.content.parties.impl.TowerParty;
import com.ruse.world.content.rewardsList.RewardsHandler;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerkContributionInput;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.ChatboxInterfaceSkillAction;
import com.ruse.world.content.skill.impl.old_dungeoneering.Dungeoneering;
import com.ruse.world.content.skill.impl.slayer.Slayer;
import com.ruse.world.content.skill.impl.summoning.SummoningTab;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;

import com.ruse.world.content.tree.Path;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.entity.impl.player.StartScreen;

import java.util.List;

import static com.ruse.world.content.DropsInterface.buildRightSide;

/**
 * This packet listener manages a button that the player has clicked upon.
 *
 *
 * @author Gabriel Hannason
 */

public class ButtonClickPacketListener implements PacketListener {

    public static final int OPCODE = 185;

    @Override
    public void handleMessage(Player player, Packet packet) {
        int bankid = 0;
        int id = packet.readInt();
        if (id >= 32768 && id < 65535) {
            id -= 65536;
        }

        if (player.getRights().isManagement()) {
            player.getPacketSender().sendMessage("Clicked button: " + id);
        }

        // System.out.println("ID = " + id);

        if (!player.getControllerManager().processButtonClick(id)) {
            return;
        }

        if (id == PartyInterface.CURRENT_PARTIES_REFRESH) {
            PartyInterface.clearCurrentPartiesInterface(player);
            PartyInterface.populateCurrentPartiesInterface(player);
            return;
        }
        if (id == PartyInterface.CURRENT_PARTIES_MAKE_PARTY) {
            if (PartyService.playerIsInParty(player)) {
                player.sendMessage("Please leave the party you're in before attempting to do this.");
                return;
            }
            player.setTowerParty(new TowerParty());
            player.getTowerParty().createParty(player);
            PartyInterface.clearCurrentPartiesInterface(player);
            PartyInterface.populateCurrentPartiesInterface(player);
            return;
        }

        if (id == PartyInterface.VIEWING_PARTIES_BACK) {
            PartyInterface.openCurrentParties(player, PartyType.TOWER_OF_ASCENSION);
            return;
        }

        if (id == PartyInterface.VIEWING_PARTIES_REFRESH) {
            PartyInterface.clear_viewing_parties_members(player);
            PartyInterface.send_members_list(player);
            if (PartyService.isPlayerPartyOwner(player)) {
                PartyInterface.clear_viewing_parties_applicants(player);
                PartyInterface.refresh_applicants_list(player);
            }
            return;
        }

        if (id == PartyInterface.VIEWING_PARTIES_DISBAND) {
            if (player.getTowerParty().getOwner() == player)
                player.getTowerParty().disbandParty();
            else if (PartyService.playerIsInParty(player))
                player.getTowerParty().leaveParty(player);
            else
                PartyInterface.send_invite(player, id);
            return;
        }

        if (PartyInterface.isKickButton(id)) {
            PartyInterface.kickMember(player, id);
            return;
        }

        if (PartyInterface.is_view_party_button(id)) {
            PartyInterface.view_party(player, id);
            return;
        }

        if (PartyInterface.is_applicants_button(id)) {
            PartyInterface.accept_applicants(player, id);
            PartyInterface.clear_viewing_parties_members(player);
            PartyInterface.send_members_list(player);
            PartyInterface.clear_viewing_parties_applicants(player);
            PartyInterface.refresh_applicants_list(player);
            return;
        }

        if (player.getInvocations().isButton(id)) {
            player.getInvocations().set_invocation_for_raid(id);
            return;
        }

        if (id == -23480) {
            List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();
            if (activePerks.contains(ServerPerks.Perk.COMBO)) {
                player.sendMessage("@red@Cannot Activate While Combo Boost Is Active!");
                return;
            }
            if (activePerks.size() >= 2) {
                player.sendMessage("@red@Cannot activate more than two boosts at once!");
                return;
            }
            player.getPacketSender()
                    .sendEnterAmountPrompt("How much would you like to contribute?");
            player.setInputHandling(new ServerPerkContributionInput());
            return;
        }

        if (player.getAchievements().handleButtonClick(id)) {
            return;
        }

        if (checkHandlers(player, id)) {
            return;
        }


        // if (player.getGambling().handleChoice(id)) {
        //    return;
        // }

        if (player.getPresetManager().handleButton(id)) {
            return;
        }

        if (player.getPlayerOwnedShopManager().handleButton(id)) {
            return;
        }

        if (player.getMegaChest().handleButton(id)) {
            return;
        }

        if (player.getSkillTree().isButton(id)) {
            player.getSkillTree().unlockNode(id);
            return;
        }

        if (player.getAfkCombatChecker().handleButton(id)) {
            return;
        }

        // if (player.getMegaChest().handleButton(id)) {
        //    return;
        // }

        //if (TeleportButtons.isTeleportButton(player, id)) {
        //    return;
        // }

        if (player.getHolidayTaskInterface().handleBtn(id))
            return;

        if (player.getViewing().equals(Player.INTERFACES.GOODIEBAG)) {
            if (player.getGoodieBag().handleClick(id)) {
                return;
            }
        }
        if (player.getViewing().equals(Player.INTERFACES.GOODIEBAG_U)) {
            if (player.getGoodieBagU().handleClick(id)) {
                return;
            }
        }

        if (PossibleLootInterface.handleButton(player, id)) {
            return;
        }

        if (player.getRights().isStaff())
        if (StaffInterfaceHandler.handleButtonClick(player, id)) {
            return;
        }


        if (CosmeticToggleInterface.handleButtonClick(player, id)) {
            return;
        }

        if (player.getUpgradeInterface().handleButton(id)) {
            return;
        }

        if (player.getDonatorShop().handleButton(id)) {
            return;
        }
        if (player.getDailyTaskInterface().handleBtnClick(id)) {
            return;
        }

        if (player.getCosmeticShop().handleButton(id)) {
            return;
        }
        if (player.getUpgrade().typeSwitch(id)) {
            player.getUpgrade().displayItems();
        }
        if (player.getUpgrade().handleBtnClick(id))
            return;

        if (player.getPlayerShops().button(id)) {
            return;
        }

      /*  if (player.getTitlesManager().handleButton(id)) {
            return;
        }*/

        if (player.getLeaderboardManager().handleButton(id)) {
            return;
        }

        if (player.getViewing().equals(Player.INTERFACES.STARTER_TASK)) {
            if (player.getStarterTasks().handleButtonClick(id)) {
                return;
            }
        }

        if (player.getViewing().equals(Player.INTERFACES.MEDIUM_TASKS)) {
            if (player.getMediumTasks().handleButtonClick(id)) {
                return;
            }
        }

        if (player.getViewing().equals(Player.INTERFACES.ELITE_TASKS)) {
            if (player.getEliteTasks().handleButtonClick(id)) {
                return;
            }
        }


            if (player.getBattlePass().handleClick(id)) {
                return;
            }

        if (player.getCollectionLogManager().handleButton(id))
            return;

        new InstanceInterfaceHandler(player).handleButtons(id);
        switch (id) {
            case 90060:
                if (!player.isTreeUnlocked()) {
                    if (player.getMonsteressence() >= 1000 && player.getInventory().contains(995,100_000)) {
                        new SelectionDialogue(player,"Reset Skill Tree?",
                            new SelectionDialogue.Selection("Yes", 0, p -> {
                                player.setMonsteressence(player.getMonsteressence() - 1000);
                                player.getInventory().delete(995,100_000);
                                player.setTreeUnlocked(true);
                                player.getSkillTree().displayInterface();
                            }),
                            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    } else {
                        player.msgRed("You require 1k monster essence and 100k Coins to unlock the skill tree");
                        player.msgRed("Place the Monster Essence in your pouch!");

                    }
                }
                break;
            case -7533:
                if (player.getLocation() == Location.CORRUPT_RAID_LOBBY || player.getLocation() == Location.VOID_RAID_LOBBY ){
                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                        new RaidsParty(player).create();
                        return;
                    }
                }

                if (player.getLocation() == Location.CORRUPT_RAID_LOBBY|| player.getLocation() == Location.VOID_RAID_LOBBY ){
                    if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
                        player.msgRed("Corrupt Raids can only be done Solo!");

                    } else {
                        new RaidsParty(player).create();
                    }
                }
                return;
            case -7530:
                RaidsParty raidParty = player.getMinigameAttributes().getRaidsAttributes().getParty();

                switch (player.getLocation()) {
                    case CORRUPT_RAID_LOBBY:
                    case CORRUPT_RAID_ROOM_3:
                    case CORRUPT_RAID_ROOM_2:
                    case CORRUPT_RAID_ROOM_1:
                        if (raidParty == null) {
                            return;
                        }
                        raidParty.remove(player, false, true);
                        player.sendMessage("@blu@<shad=0>You have left your Raid Party.");
                        raidParty.sendMessage("@blu@" + player.getUsername() + " has left the RaidParty");
                        player.moveTo(new Position(2974, 3879, 0));
                        break;
                    case VOID_RAID_LOBBY:
                    case VOID_RAID_ROOM_3:
                    case VOID_RAID_ROOM_2:
                    case VOID_RAID_ROOM_1:
                        if (raidParty == null) {
                            return;
                        }
                        raidParty.remove(player, false, true);
                        player.sendMessage("@blu@<shad=0>You have left your Raid Party.");
                        raidParty.sendMessage("@blu@" + player.getUsername() + " has left the Raid Party");
                        player.moveTo(new Position(2521, 4833, 0));
                        break;
                }
                break;


            case 88011:
                player.getInvocations().reset_bound_invocations();
                player.getInvocations().sendNumbers();
                break;
            case 90006:
            case 90010:
                if (player.getCurrentPath() != null) {
                    if (player.getCurrentPath().equals(Path.HADES)) {
                        if (!player.getInventory().contains(2619)) {
                            player.msgRed("You need a Bridge Gem to proceed");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        if (!player.getSkillTree().has_requirements_for_first_bridge(id)) {
                            player.msgRed("Reach a Node that connects to a Bridge in order to do this.");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        int finalId = id;
                        new SelectionDialogue(player,"Unlock Node?",
                            new SelectionDialogue.Selection("Yes", 0, p -> {
                                player.getSkillTree().bridge(Path.GAIA, finalId);
                            }),
                            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    }
                    if (player.getCurrentPath().equals(Path.GAIA)) {
                        if (!player.getInventory().contains(2619)) {
                            player.msgRed("You need a Bridge Gem to proceed");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        if (!player.getSkillTree().has_requirements_for_first_bridge(id)) {
                            player.msgRed("Reach a Node that connects to a Bridge in order to do this.");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        int finalId = id;
                        new SelectionDialogue(player,"Unlock Node?",
                            new SelectionDialogue.Selection("Yes", 0, p -> {
                                player.getSkillTree().bridge(Path.HADES, finalId);
                            }),
                            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    }
                } else {
                    player.sendMessage("Please progress before attempting to do this");
                }
                break;
            case 90004:
            case 90008:
                if (player.getCurrentPath() != null) {
                    if (player.getCurrentPath().equals(Path.HADES)) {
                        if (!player.getInventory().contains(2619)) {
                            player.msgRed("You need a Bridge Gem to proceed");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        if (!player.getSkillTree().has_requirements_for_first_bridge(id)) {
                            player.msgRed("Reach a Node that connects to a Bridge in order to do this.");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        int finalId = id;
                        new SelectionDialogue(player,"Unlock Node?",
                            new SelectionDialogue.Selection("Yes", 0, p -> {
                                player.getSkillTree().bridge(Path.PONTUS, finalId);
                            }),
                            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    }
                    if (player.getCurrentPath().equals(Path.PONTUS)) {
                        if (!player.getInventory().contains(2619)) {
                            player.msgRed("You need a Bridge Gem to proceed");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        if (!player.getSkillTree().has_requirements_for_first_bridge(id)) {
                            player.msgRed("Reach a Node that connects to a Bridge in order to do this.");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        int finalId = id;
                        new SelectionDialogue(player,"Unlock Node?",
                            new SelectionDialogue.Selection("Yes", 0, p -> {
                                player.getSkillTree().bridge(Path.HADES, finalId);
                            }),
                            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    }
                } else {
                    player.sendMessage("Please progress before attempting to do this");
                }
                break;
            case 20104:
                PerkManager.voteForPerk(player.getHostAddress(), 0);
            break;
            case 20105:
                PerkManager.voteForPerk(player.getHostAddress(), 1);
            break;
            case 20106:
                PerkManager.voteForPerk(player.getHostAddress(), 2);
            break;

            case 28502:
                PersonalCampaignHandler.updateInterface(player);
            break;
            case 26458:
                player.pouchMessagesEnabled = !player.pouchMessagesEnabled;
                player.msgRed("Essence Pouch Messages set to: " + player.pouchMessagesEnabled);
                player.getPacketSender().sendConfig(2802, player.isPouchMessagesEnabled() ? 1 : 0);
            break;
            case 26460:
                player.setGlobalDropMessagesEnabled(!player.isGlobalDropMessagesEnabled());
                player.msgRed("Global Drop Messages Messages set to: " + player.globalDropMessagesEnabled);
                player.getPacketSender().sendConfig(2801, player.isGlobalDropMessagesEnabled() ? 1 : 0);
                break;
            case 26462:
                player.sendMessage("Auto banking Membership Feature set to: " + !player.getAutoBank());
                player.setAutoBank(!player.getAutoBank());
                player.getPacketSender().sendConfig(2803, player.getAutoBank() ? 1 : 0);
                break;
            case 26464:
                player.sendMessage("Auto dissolving Membership Feature set to: " + !player.getAutoDissolve());
                player.setAutoDissolve(!player.getAutoDissolve());
                player.getPacketSender().sendConfig(2804, player.getAutoDissolve() ? 1 : 0);
                break;
            case 28504:
                LifetimeStreakHandler.updateInterface(player);
            break;
            case -16332:
                if (player.getViewing().equals(Player.INTERFACES.GOODIEBAG)) {
                    if (player.getGoodieBag() != null) {
                        player.getGoodieBag().claim();
                        return;
                    }
                }
                if (player.getViewing().equals(Player.INTERFACES.GOODIEBAG_U)) {
                    if (player.getGoodieBagU() != null) {
                        player.getGoodieBagU().claim();
                        return;
                    }
                }
                break;

            case 28179:
                TeleportHandler.teleportPlayer(player, new Position(3047, 3865,0), TeleportType.ANCIENT);

                break;
            case 86126:
                TeleportHandler.teleportPlayer(player, new Position(3168, 3516,0), TeleportType.ANCIENT);

                break;
            case 86305:
                TeleportHandler.teleportPlayer(player, new Position(3244, 2978,0), TeleportType.ANCIENT);

                break;
            case 86302:
                new SelectionDialogue(player,"Journeyman",
                        new SelectionDialogue.Selection("Salt Mines", 0,
                                p -> {TeleportHandler.teleportPlayer(player, new Position(2589, 4131,0), TeleportType.ANCIENT);}),
                        new SelectionDialogue.Selection("Journeyman Mines", 1,
                                p -> TeleportHandler.teleportPlayer(player, new Position(3034, 3996,0), TeleportType.ANCIENT))
                ).start();
                break;
            case 86308:
                new SelectionDialogue(player,"Journeyman",
                        new SelectionDialogue.Selection("Mining", 0,
                                p -> {TeleportHandler.teleportPlayer(player, new Position(3034, 3994,0), TeleportType.ANCIENT);}),
                        new SelectionDialogue.Selection("Woodcutting", 1,
                                p -> TeleportHandler.teleportPlayer(player, new Position(3244, 2978,0), TeleportType.ANCIENT))
                ).start();
                break;
            case 86117:
                TeleportHandler.teleportPlayer(player, new Position(3171, 3563,0), TeleportType.ANCIENT);

                break;
            case 86314:
                player.getUpgrade().display();
                break;
            case 86120:
                TeleportHandler.teleportPlayer(player, new Position(3047, 3865,0), TeleportType.ANCIENT);

                break;
            case 86123:
                TeleportHandler.teleportPlayer(player, new Position(2709, 3109,0), TeleportType.ANCIENT);

                break;
            case 8666:
                final RaidsParty party65235 = player.getMinigameAttributes().getRaidsAttributes().getParty();
                if (party65235 != null) {
                    player.sendMessage("Please leave your party before leaving the lobby!");
                    return;
                }
                Position pos = new Position(3172, 3560);
                TeleportHandler.teleportPlayer(player, pos, TeleportType.ANCIENT);
                break;

            case -30520:
                if (!player.getClickDelay().elapsed(250)) {
                    player.sendMessage("Slow Down...");
                    return;
                }
                player.getClickDelay().reset();
                if (player.getInstanceManager().selectedInstance != null) {
                    if (!player.getLastRunRecovery().elapsed(5000)) {
                        player.sendMessage("Instance on cooldown...");
                        return;
                    }
                    player.getInstanceManager().createInstance(player.getInstanceManager().selectedInstance.getNpcId(), RegionInstance.RegionInstanceType.INSTANCE);
                } else {
                    player.getPA().sendMessage("Select the boss you'd like to instance.");
                }
                break;
            case 79104:
                if (player.isShowMyChance()) {
                    player.setShowMyChance(false);
                    if (player.getCurrentNpcId() != -1) { // Check if a valid npcId is stored
                        buildRightSide(player, player.getCurrentNpcId());
                    }
                    return;
                }
                player.setShowMyChance(true);
                if (player.getCurrentNpcId() != -1) { // Check if a valid npcId is stored
                    buildRightSide(player, player.getCurrentNpcId());
                }
                break;
            case -28033:
                if (!player.getClickDelay().elapsed(3000)) {
                    return;
                }
                if (player.getRights() != PlayerRights.OWNER && player.getRights() != PlayerRights.MANAGER_2) {
                    return;
                }
                BalloonDropParty.spawn(player);
                player.getPacketSender().sendInterfaceRemoval();
                player.getClickDelay().reset();
                break;


            //  case 30332:
            //   player.getCustomCombiner().combine();
            //  break;

            case -32109:
                player.getItemUpgrader().upgrade();
                break;

            case 15281:
                if (player.getInterfaceId() == 21172) {
                    player.sendMessage("Please close the equipment interface to swap to your cosmetics!");
                    return;
                }
                player.sendMessage("Viewing: " + (player.viewingCosmeticTab ? "Equipment" : "Cosmetic") + " Tab.");
                player.viewingCosmeticTab = !player.viewingCosmeticTab;
                player.getEquipment().refreshItems();
                //player.getUpdateFlag().flag((Flag.APPEARANCE));
                if (player.viewingCosmeticTab) {
                    player.sendMessage("Please use ::cosmetic to toggle which gear slots you want showing as overrides.");
                }
                break;
            case 26070:
                player.levelNotifications = !player.levelNotifications;
                player.getPacketSender()
                        .sendMessage("Level-up notifications toggled: " + (player.levelNotifications ? "on" : "off") + ".");
                break;

            //PLAYER PANEL LINKS
            case 111222:
                player.getPacketSender().sendString(1, GameSettings.WikiUrl);
                break;
            case 111223:
                player.getPacketSender().sendString(1, GameSettings.VoteUrl);
                break;
            case 111224:
                player.getPacketSender().sendString(1, "https://refer-ps.teamgames.io/category/4594/page/1");
                break;
            case 111225:
                player.getPacketSender().sendString(1, GameSettings.DiscordUrl);
                break;
            case -26884:
            case -18522:
            case 27581:
            case 15215:
            case -12286:
            case -23474:
            case 21360:
            case -17492:
            case 106005:
            case -8305:
            case -13233:
            case -3306:
            case -13390:
            case 10162:
            case -18269:
            case -27454:
            case -27534:
            case -16534:
            case 36002:
            case 32606:
            case -31929:
            case -8255:
            case 19705:
            case 28005:
            case 26003:
            case 5384:
            case -14084:
            case 31098:
            case 83507:
                player.getPacketSender().sendInterfaceRemoval();
                break;
            case -18532:
                player.getNewSpinner().findReward();
                break;
            case 1716:
      /*          NpcSpawner npcSpawner = new NpcSpawner(player);
               int npcId = 1150;
               npcSpawner.setNpcId(npcId);
               npcSpawner.spawnNpcWithId(String.valueOf(npcId));*/
               player.getTPInterface().display();
                break;
            // Previous Teleport Button
            case 1717:

                if (!player.getClickDelay().elapsed(4500) || player.getMovementQueue()
                        .isLockMovement())
                    return;

                if (player.lastTeleport != null) {
                    player.sendMessage("You have been teleported to your last teleport location.");
                    TeleportHandler.teleportPlayer(player, player.lastTeleport, player.getSpellbook()
                            .getTeleportType());
                } else {
                    player.sendMessage("You haven't teleported yet.");
                }

                player.getClickDelay().reset(0);
                break;
            case 106009:
                if (!player.isBanking() || player.getInterfaceId() != 106000)
                    return;
                GroupIronmanBank.depositItems(player, player.getInventory(), false);
                break;
            case 106012:
                if (!player.isBanking() || player.getInterfaceId() != 106000)
                    return;
                GroupIronmanBank.depositItems(player, player.getEquipment(), false);
                break;
            case 108007:
                player.getCasketOpening().spin();
                break;
            case 86317:
                TeleportHandler.teleportPlayer(player, new Position(3177, 3546, 0), TeleportType.ANCIENT);
                break;
            case 108008:
                player.getCasketOpening().quickSpin();
                break;
            case 28503:
                DailyDealSelector.sendInterface(player);
            break;
            case 78395:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK)) {
                    player.sendMessage("This feature is not available on the group bank");
                    return;
                }
                player.setPlaceholders(!player.isPlaceholders());
                player.sendMessage(player.isPlaceholders() ? "You are now utilizing the placeholders system" : "You are no longer utilizing the placeholders system");
                break;
            case 111211:
                player.getPacketSender().sendString(1, GameSettings.DomainUrl);
                break;
            case 111212:
                player.getPacketSender().sendString(1, GameSettings.WikiUrl);
                player.getPacketSender().sendMessage("<col=3E0957><shad=6C1894>Opening The Athens Wiki...");
                break;
            case 111213:
                player.getPacketSender().sendString(1, GameSettings.VoteUrl);
                break;
            case 111214:
            case -15733:
                player.getPacketSender().sendString(1, GameSettings.StoreUrl);
                break;
            case 111215:
                player.getPacketSender().sendString(1, GameSettings.DiscordUrl);
                break;
            case -29536:
                player.getAchievements().clickedAchievement = true;
                player.getAchievements().drawInterface(0);
                break;
            case -26365:
                player.getAchievements().clickedAchievement = true;
                player.getAchievements().drawInterface(player.getAchievements().currentInterface);
                break;

            case 111602://ACHIEVEMENTS
                player.getAchievements().refreshInterface(player.getAchievements().currentInterface);
                player.getPacketSender().sendConfig(6000, 3);
                break;
            case 111606://COLL LOG
                player.getCollectionLogManager().openInterface();
                break;
            case 111610://DROPS
                DropsInterface.open(player);
                break;
            case 111614://ENCHANTMENT
                player.getUpgrade().display();
                break;
            case 111618://BIS
            case 15004:
                player.getBis().open();
                break;
            case 111622://LEADERBOARDS
                player.getLeaderboardManager().openInterface();
                break;
            case 111626://LOOT
                PossibleLootInterface.openInterface(player, PossibleLootInterface.LootData.values()[0]);
                break;
            case 111630://STARTER TASKS
                if (!player.getStarterTasks().hasCompletedAll()){
                    player.getStarterTasks().openInterface();
                    player.setViewing(Player.INTERFACES.STARTER_TASK);
                    return;
                }
                if (!player.getMediumTasks().hasCompletedAll()){
                    player.getMediumTasks().openInterface();
                    player.setViewing(Player.INTERFACES.MEDIUM_TASKS);
                    return;
                }
                if (!player.getEliteTasks().hasCompletedAll()){
                    player.getEliteTasks().openInterface();
                    player.setViewing(Player.INTERFACES.ELITE_TASKS);
                    return;
                }
                break;
            case 111638://TELEPORTS
                player.getTPInterface().display();
                break;
            case 111634:
                player.getSkillTree().displayInterface();
                break;

            case 111101:
                player.getPacketSender().sendTabInterface(GameSettings.PLAYER_TAB, 111000);
                player.getPacketSender().sendConfig(6000, 0);
                break;
            case 111102:
                player.getPacketSender().sendTabInterface(GameSettings.PLAYER_TAB, 111300);
                player.getPacketSender().sendConfig(6000, 1);
                break;
            case 111103:
                player.getPacketSender().sendTabInterface(GameSettings.PLAYER_TAB, 111500);
                player.getPacketSender().sendConfig(6000, 2);
                break;
            case 111601:
                player.getAchievements()
                        .refreshInterface(player.getAchievements().currentInterface);
                player.getPacketSender().sendConfig(6000, 3);
                break;
            case 12162:
                TeleportHandler.teleportPlayer(player, new Position(2716, 3112, 0), TeleportType.ANCIENT);
                break;

          //  case -8384: // -8384
                //player.sendMessage("Auto gambling has been disabled.");
                //player.sendMessage("In order to gamble, you have to purchase the dice bag or seeds via ::Store");
               // System.out.println("accepting gamble... " + player.getUsername());
               // player.getGambling().acceptGamble(1);
              //  break;

         //   case -8383:
            //    if (player.getGambling().inGamble())
             //       System.out.println("declining gamble... " + player.getUsername());
             //   player.getGambling().declineGamble(true);
            //    break;

            // GUIDE INTERFACE
            case -8254:
                player.getPacketSender().sendString(1, GameSettings.StoreUrl);
                player.getPacketSender().sendMessage("Attempting to open the store");
                break;
            case -11438:
                player.getPlayerOwnedShopManager().openEditor();
                break;
            case -3292:
                DialogueManager.start(player, 217);
                player.setDialogueActionId(217);
                break;
            case 1036:
                EnergyHandler.rest(player);
                break;
            case 14176:
                player.setUntradeableDropItem(null);
                player.getPacketSender().sendInterfaceRemoval();
                break;

            /**
             * Player panel tab buttons
             */

            case 23835:
            case 23836:
            case 23837:
            case 23838:
                PlayerPanel.refreshPanel(player);
                break;

            case 14175:
                player.getPacketSender().sendInterfaceRemoval();
                if (player.getUntradeableDropItem() != null
                        && player.getInventory()
                        .contains(player.getUntradeableDropItem().getId())) {
                    player.getInventory().delete(player.getUntradeableDropItem());
                    PlayerLogs.log(player.getUsername(),
                            "Player destroying item: " + player.getUntradeableDropItem()
                                    .getId() + ", amount: "
                                    + player.getUntradeableDropItem().getAmount());
                    player.getPacketSender()
                            .sendMessage("Your item vanishes as it hits the floor.");
                    Sounds.sendSound(player, Sound.DROP_ITEM);
                }
                player.setUntradeableDropItem(null);
                break;
            case 1013:
                player.getSkillManager().setTotalGainedExp(0);
                break;
            case -26349:
                KillsTracker.open(player);
                break;
            case -26348:
                DropLog.open(player);
                break;
            case -10531:
                if (player.isKillsTrackerOpen()) {
                    player.setKillsTrackerOpen(false);
                    player.getPacketSender().sendTabInterface(GameSettings.PLAYER_TAB, 111000);
                    PlayerPanel.refreshPanel(player);
                }
                break;
            case 11213:
                player.getPacketSender().sendString(1, GameSettings.DomainUrl);
                player.getPacketSender().sendMessage("Attempting to open the homepage");
                break;
            case 11217:
                player.getPacketSender().sendString(1, GameSettings.DiscordUrl);
                player.getPacketSender().sendMessage("Attempting to open the Discord");
                break;
            case -26337:
            case 11216:
                player.getPacketSender().sendString(1, GameSettings.StoreUrl);
                player.getPacketSender().sendMessage("Attempting to open the Store");
                break;
            case -26338:
                player.getPacketSender().sendString(1, GameSettings.RuleUrl);
                player.getPacketSender().sendMessage("Attempting to open the rules");
                break;
            case -26339:
            case 11214:
                player.getPacketSender().sendString(1, GameSettings.ForumUrl);
                player.getPacketSender().sendMessage("Attempting to open the forums");
                break;
            case -26336:
            case 11215:
                player.getPacketSender().sendString(1, GameSettings.VoteUrl);
                player.getPacketSender().sendMessage("Attempting to open the Vote page");
                break;
            case -26335:
                player.getPacketSender().sendString(1, GameSettings.HiscoreUrl);
                player.getPacketSender().sendMessage("Attempting to open the Hiscore page");
                break;
            case -26334:
                player.getPacketSender().sendString(1, GameSettings.ReportUrl);
                player.getPacketSender().sendMessage("Attempting to open the report page");
                break;
            case 350:
         /*       player.getPacketSender()
                        .sendMessage("To autocast a spell, please right-click it and choose the autocast option.")
                        .sendTab(GameSettings.MAGIC_TAB)
                        .sendConfig(108, player.getAutocastSpell() == null ? 3 : 1);*/
                break;
            case 29335:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                DialogueManager.start(player, 60);
                player.setDialogueActionId(27);
                break;
            case 29455:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                // ClanChatManager.toggleLootShare(player);
                break;
            case 11001:
                final RaidsParty party6 = player.getMinigameAttributes().getRaidsAttributes().getParty();
                if (party6 != null) {
                    player.sendMessage("Please leave your party before leaving the lobby!");

                    return;
                }

                TeleportHandler.teleportPlayer(player, new Position(3167 +- Misc.random(0,3), 3544+- Misc.random(0,3), 0), TeleportType.ANCIENT);
                player.getPacketSender().sendInterfaceRemoval();
                player.sendMessage("<col=AF70C3><shad=0>Welcome Home.");
                break;
            case 28211:
                TeleportHandler.teleportPlayer(player, new Position(3167 +- Misc.random(0,3), 3544+- Misc.random(0,3), 0), TeleportType.ANCIENT);
                break;

            case 27885:
                player.sendMessage("<col=AF70C3><shad=0>Welcome Home.");
                TeleportHandler.teleportPlayer(player, new Position(3167 +- Misc.random(0,3), 3544+- Misc.random(0,3), 0), TeleportType.ANCIENT);
                break;

            case 27587:
            case 27595:
            case 27603:
            case 27611:
            case 27619:
            case 27627:
            case 27635:
            case 27643:
            case 27651:
            case 27659:
            case 27667:
            case 27675:
            case 27683:
            case 27691:
            case 27699:
            case 27707:
            case 27715:
            case 27723:
            case 27731:
            case 27739:
                NecromancySpells.handleMagicSpells(player, id);
                break;
            case 10001:
                if (player.getInterfaceId() == -1) {
                    Consumables.handleHealAction(player);
                } else {
                    player.getPacketSender().sendMessage("You cannot heal yourself right now.");
                }
                break;
            case 3546:
            case 3420:
                if (System.currentTimeMillis() - player.getTrading().lastAction <= 300)
                    return;
                player.getTrading().lastAction = System.currentTimeMillis();
                if (player.getTrading().inTrade()) {
                    player.getTrading().acceptTrade(id == 3546 ? 2 : 1);
                } else {
                    player.getPacketSender().sendInterfaceRemoval();
                }
                break;
            case 14922:
                player.getPacketSender().sendClientRightClickRemoval().sendInterfaceRemoval();
                break;
            case 5294:
                player.getPacketSender().sendClientRightClickRemoval().sendInterfaceRemoval();
                player.setDialogueActionId(player.getBankPinAttributes().hasBankPin() ? 8 : 7);
                DialogueManager.start(player, DialogueManager.getDialogues().get(player.getBankPinAttributes().hasBankPin() ? 12 : 9));
                break;
            case 15002:

                if (!player.busy() && !player.getCombatBuilder().isBeingAttacked()
                        && !Dungeoneering.doingOldDungeoneering(player)) {
                    player.getSkillManager().stopSkilling();
                    player.getPriceChecker().open();
                } else {
                    player.getPacketSender().sendMessage("You cannot open this right now.");
                }
                break;
            case 2735:
            case 1511:
                if (player.getSummoning().getBeastOfBurden() != null) {
                    player.getSummoning().toInventory();
                    player.getPacketSender().sendInterfaceRemoval();
                } else {
                    player.getPacketSender()
                            .sendMessage("You do not have a familiar who can hold items.");
                }
                break;
            case -11501:
            case -11498:
            case -11507:
            case 1020:
            case 1021:
            case 1019:
            case 1018:
                if (id == 1020)
                    SummoningTab.renewFamiliar(player);
                else if (id == 1019 || id == -11507)
                    SummoningTab.callFollower(player);
                else if (id == 1021 || id == -11504)
                    SummoningTab.handleDismiss(player, true);
                    //else if (id == -11507) //
                    //     player.getSummoning().store();
                else if (id == 1018)
                    player.getSummoning().toInventory();
                break;
            case 1042:
                //DialogueManager.start(player, 9007);
               // player.setDialogueActionId(9007);


          /*    final RaidsParty party652356 = player.getMinigameAttributes().getRaidsAttributes().getParty();
                if (party652356 != null) {
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
                else {
                    player.sendMessage("No Global Events are Currently Active!");
                }*/

                break;

            case 1037:
/*                    if (player.getLastNecroSpell() != 0) {
                        NecromancySpells.handleMagicSpells(player, player.getLastNecroSpell());
                    }*/
                break;
            case 1038:
                player.getPA().sendLayoutToggle();
                break;
            case 1039:
                player.setEventstoggle(!player.isEventstoggle());
                if (player.isEventstoggle()) {
                    player.sendMessage("Event Teleports enabled.");
                } else {
                    player.sendMessage("Event Teleports disabled.");
                }
                break;
            case 1040:
                CombatSpecial.activate(player);
                break;
            case 1041://Last Summon
                if (player.getLastNecroSpell() != 0) {
                    NecromancySpells.handleMagicSpells(player, player.getLastNecroSpell());
                }
                break;
            case 1095:
                player.setExperienceLocked(!player.experienceLocked());
                if (player.experienceLocked()) {
                    player.getPacketSender()
                            .sendMessage("Your EXP is now locked, revert this lock to get EXP again.");
                } else {
                    player.getPacketSender()
                            .sendMessage("Your EXP is unlocked, and you will gain EXP as normal.");
                }
                break;
            case 2799:
            case 2798:
            case 1747:
            case 1748:
            case 8890:
            case 8886:
            case 8875:
            case 8871:
            case 8894:
                ChatboxInterfaceSkillAction.handleChatboxInterfaceButtons(player, id);
                break;
            case 14873:
            case 14874:
            case 14875:
            case 14876:
            case 14877:
            case 14878:
            case 14879:
            case 14880:
            case 14881:
            case 14882:
                BankPin.clickedButton(player, id);
                break;
            case 27005:
            case 22012:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK))
                    GroupIronmanBank.depositItems(player, id == 27005 ? player.getEquipment() : player.getInventory(), false);
                else
                    Bank.depositItems(player, id == 27005 ? player.getEquipment() : player.getInventory(), false);
                player.sendMessage("You have deposited the items from your " + (id == 27005 ? "equipped items": "inventory"));
                break;
            case 22008:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setSwapMode(false);
                break;
            case 22009:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setSwapMode(true);
                break;
            case 569:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setNoteWithdrawal(false);
                break;
            case 570:
                if (!player.isBanking() || player.getInterfaceId() != 5292)
                    return;
                player.setNoteWithdrawal(true);
                break;
            case 27014:
            case 27015:
            case 27016:
            case 27017:
            case 27018:
            case 27019:
            case 27020:
            case 27021:
            case 27022:
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK)) {
                    if (!player.isBanking())
                        return;
                    if (player.getGroupBankSearchingAttribtues().isSearchingBank())
                        GroupIronmanBank.GroupBankSearchAttributes.stopSearch(player, true);
                    int bankId = id - 27014;
                    boolean empty = bankId > 0 && GroupIronmanBank.isEmpty(player.getGroupIronmanBank(bankId));
                    if (!empty || bankId == 0) {
                        player.setCurrentGroupBankTab(bankId);
                        player.getPacketSender().sendString(5385, "scrollreset");
                        player.getPacketSender()
                            .sendString(27002, Integer.toString(player.getCurrentBankTab()));
                        player.getPacketSender().sendString(27000, "1");
                        player.getGroupIronmanBank(bankId).open(player);
                    } else
                        player.getPacketSender()
                            .sendMessage("To create a new tab, please drag an item here.");
                } else {
                    if (!player.isBanking())
                        return;
                    if (player.getBankSearchingAttribtues().isSearchingBank())
                        BankSearchAttributes.stopSearch(player, true);
                    int bankId = id - 27014;
                    boolean empty = bankId > 0 && Bank.isEmpty(player.getBank(bankId));
                    if (!empty || bankId == 0) {
                        player.setCurrentBankTab(bankId);
                        player.getPacketSender().sendString(5385, "scrollreset");
                        player.getPacketSender()
                            .sendString(27002, Integer.toString(player.getCurrentBankTab()));
                        player.getPacketSender().sendString(27000, "1");
                        player.getBank(bankId).open();
                    } else
                        player.getPacketSender()
                            .sendMessage("To create a new tab, please drag an item here.");
                }
                break;
            case 22004:
                if (!player.isBanking())
                    return;
                if (player.getViewing().equals(Player.INTERFACES.GROUP_BANK))
                    return;
                if (!player.getBankSearchingAttribtues().isSearchingBank()) {
                    player.getBankSearchingAttribtues().setSearchingBank(true);
                    player.setInputHandling(new EnterSyntaxToBankSearchFor());
                    player.getPacketSender()
                            .sendEnterInputPrompt("What would you like to search for?");
                } else {
                    BankSearchAttributes.stopSearch(player, true);
                }
                break;
            case 22845:
            case 24115:
            case 24010:
            case 24041:
            case 150:
                player.setAutoRetaliate(!player.isAutoRetaliate());
                break;
            case 29332:
                ClanChat clan = player.getCurrentClanChat();
                if (clan == null) {
                    player.getPacketSender().sendMessage("You are not in a clanchat channel.");
                    return;
                }
                ClanChatManager.leave(player, false);
                player.setClanChatName(null);
                break;
            case 29329:
                if (player.getInterfaceId() > 0) {
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before opening another one.");
                    return;
                }
                player.setInputHandling(new EnterClanChatToJoin());
                player.getPacketSender()
                        .sendEnterInputPrompt("Enter the name of the clanchat channel you wish to join:");
                break;
            case 19158:
            case 152:
                if (player.getRunEnergy() <= 1) {
                    player.getPacketSender()
                            .sendMessage("You do not have enough energy to do this.");
                    player.setRunning(false);
                } else
                    player.setRunning(!player.isRunning());
                player.getPacketSender().sendRunStatus();
                break;
            case 15001:
                if(player.viewingCosmeticTab){
                    player.sendMessage("Please Switch back to your regular equipment to show cosmetics!");
                    return;
                }
                if(player.getInterfaceId() == 21172){
                    player.sendMessage("Close this interface to swap to your cosmetics!");
                    return;
                }
                if (player.getInterfaceId() == -1) {
                    player.getSkillManager().stopSkilling();
                    BonusManager.update(player);
                    player.getPacketSender().sendInterface(21172);
                    player.getPresetManager().nullCheck();

                } else
                    player.getPacketSender()
                            .sendMessage("Please close the interface you have open before doing this.");
                break;
            case 2458: // Logout
                if (player.getLocation() != null && player.getLocation() == Location.DONORISLE) {
                    player.sendMessage("NOPE");
                    return;
                }
                if (player.logout()) {
                    World.removePlayer(player);
                }
                break;
            case 29138:
            case 29038:
            case 29063:
            case 29113:
            case 29163:
            case 29188:
            case 29213:
            case 29238:
            case 30007:
            case 48023:
            case 33033:
            case 30108:
            case 7473:
            case 7562:
            case 7487:
            case 7788:
            case 8481:
            case 7612:
            case 7587:
            case 7662:
            case 7462:
            case 7548:
            case 7687:
            case 7537:
            case 12322:
            case 7637:
            case 12311:
                CombatSpecial.activate(player);
                break;
            case 1772: // shortbow & longbow & blowpipe & crossbow
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.BLOWPIPE) {
                    player.setFightType(FightType.BLOWPIPE_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.BSOAT) {
                    player.setFightType(FightType.BSOAT_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.ARMADYLXBOW) {
                    player.setFightType(FightType.ARMADYLXBOW_ACCURATE);
                }
                break;
            case -11504:
                SummoningTab.handleDismiss(player, true, true);
            break;
            case 1771:
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_RAPID);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_RAPID);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_RAPID);
                } else if (player.getWeapon() == WeaponInterface.BLOWPIPE) {
                    player.setFightType(FightType.BLOWPIPE_RAPID);
                } else if (player.getWeapon() == WeaponInterface.BSOAT) {
                    player.setFightType(FightType.BSOAT_RAPID);
                } else if (player.getWeapon() == WeaponInterface.ARMADYLXBOW) {
                    player.setFightType(FightType.ARMADYLXBOW_RAPID);
                }
                break;
            case 1770:
                if (player.getWeapon() == WeaponInterface.SHORTBOW) {
                    player.setFightType(FightType.SHORTBOW_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.LONGBOW) {
                    player.setFightType(FightType.LONGBOW_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.CROSSBOW) {
                    player.setFightType(FightType.CROSSBOW_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.BLOWPIPE) {
                    player.setFightType(FightType.BLOWPIPE_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.BSOAT) {
                    player.setFightType(FightType.BSOAT_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.ARMADYLXBOW) {
                    player.setFightType(FightType.ARMADYLXBOW_LONGRANGE);
                }
                break;
            case 2282: // dagger & sword
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_STAB);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_STAB);
                }
                break;
            case 2285:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_LUNGE);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_LUNGE);
                }
                break;
            case 2284:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_SLASH);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_SLASH);
                }
                break;
            case 2283:
                if (player.getWeapon() == WeaponInterface.DAGGER) {
                    player.setFightType(FightType.DAGGER_BLOCK);
                } else if (player.getWeapon() == WeaponInterface.SWORD) {
                    player.setFightType(FightType.SWORD_BLOCK);
                }
                break;
            case 2429: // scimitar & longsword
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_CHOP);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_CHOP);
                }
                break;
            case 2432:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_SLASH);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_SLASH);
                }
                break;
            case 2431:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_LUNGE);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_LUNGE);
                }
                break;
            case 2430:
                if (player.getWeapon() == WeaponInterface.SCIMITAR) {
                    player.setFightType(FightType.SCIMITAR_BLOCK);
                } else if (player.getWeapon() == WeaponInterface.LONGSWORD) {
                    player.setFightType(FightType.LONGSWORD_BLOCK);
                }
                break;
            case 3802: // mace
                player.setFightType(FightType.MACE_POUND);
                break;
            case 3805:
                player.setFightType(FightType.MACE_PUMMEL);
                break;
            case 3804:
                player.setFightType(FightType.MACE_SPIKE);
                break;
            case 3803:
                player.setFightType(FightType.MACE_BLOCK);
                break;
            case 4454: // knife, thrownaxe, dart & javelin
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_ACCURATE);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_ACCURATE);
                }
                break;
            case 4453:
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_RAPID);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_RAPID);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_RAPID);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_RAPID);
                }
                break;
            case 4452:
                if (player.getWeapon() == WeaponInterface.KNIFE) {
                    player.setFightType(FightType.KNIFE_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.THROWNAXE) {
                    player.setFightType(FightType.THROWNAXE_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.DART) {
                    player.setFightType(FightType.DART_LONGRANGE);
                } else if (player.getWeapon() == WeaponInterface.JAVELIN) {
                    player.setFightType(FightType.JAVELIN_LONGRANGE);
                }
                break;
            case 4685: // spear
                player.setFightType(FightType.SPEAR_LUNGE);
                break;
            case 4688:
                player.setFightType(FightType.SPEAR_SWIPE);
                break;
            case 4687:
                player.setFightType(FightType.SPEAR_POUND);
                break;
            case 4686:
                player.setFightType(FightType.SPEAR_BLOCK);
                break;
            case 4711: // 2h sword
                player.setFightType(FightType.TWOHANDEDSWORD_CHOP);
                break;
            case 4714:
                player.setFightType(FightType.TWOHANDEDSWORD_SLASH);
                break;
            case 4713:
                player.setFightType(FightType.TWOHANDEDSWORD_SMASH);
                break;
            case 4712:
                player.setFightType(FightType.TWOHANDEDSWORD_BLOCK);
                break;
            case 5576: // pickaxe
                player.setFightType(FightType.PICKAXE_SPIKE);
                break;
            case 5579:
                player.setFightType(FightType.PICKAXE_IMPALE);
                break;
            case 5578:
                player.setFightType(FightType.PICKAXE_SMASH);
                break;
            case 5577:
                player.setFightType(FightType.PICKAXE_BLOCK);
                break;
            case 7768: // claws
                player.setFightType(FightType.CLAWS_CHOP);
                break;
            case 7771:
                player.setFightType(FightType.CLAWS_SLASH);
                break;
            case 7770:
                player.setFightType(FightType.CLAWS_LUNGE);
                break;
            case 7769:
                player.setFightType(FightType.CLAWS_BLOCK);
                break;
            case 8466: // halberd
                player.setFightType(FightType.HALBERD_JAB);
                break;
            case 8468:
                player.setFightType(FightType.HALBERD_SWIPE);
                break;
            case 8467:
                player.setFightType(FightType.HALBERD_FEND);
                break;
            case 5862: // unarmed
                player.setFightType(FightType.UNARMED_PUNCH);
                break;
            case 5861:
                player.setFightType(FightType.UNARMED_KICK);
                break;
            case 5860:
                player.setFightType(FightType.UNARMED_BLOCK);
                break;
            case 12298: // whip
                player.setFightType(FightType.WHIP_FLICK);
                break;
            case 12297:
                player.setFightType(FightType.WHIP_LASH);
                break;
            case 12296:
                player.setFightType(FightType.WHIP_DEFLECT);
                break;
            case 336: // staff
                player.setFightType(FightType.STAFF_BASH);
                break;
            case 335:
                player.setFightType(FightType.STAFF_POUND);
                break;
            case 334:
                player.setFightType(FightType.STAFF_FOCUS);
                break;
            case 433: // warhammer
                player.setFightType(FightType.WARHAMMER_POUND);
                break;
            case 432:
                player.setFightType(FightType.WARHAMMER_PUMMEL);
                break;
            case 431:
                player.setFightType(FightType.WARHAMMER_BLOCK);
                break;
            case 782: // scythe
                player.setFightType(FightType.SCYTHE_REAP);
                break;
            case 784:
                player.setFightType(FightType.SCYTHE_CHOP);
                break;
            case 785:
                player.setFightType(FightType.SCYTHE_JAB);
                break;
            case 783:
                player.setFightType(FightType.SCYTHE_BLOCK);
                break;
            case 1704: // battle axe
                player.setFightType(FightType.BATTLEAXE_CHOP);
                break;
            case 1707:
                player.setFightType(FightType.BATTLEAXE_HACK);
                break;
            case 1706:
                player.setFightType(FightType.BATTLEAXE_SMASH);
                break;
            case 1705:
                player.setFightType(FightType.BATTLEAXE_BLOCK);
                break;
        }
    }


    private boolean checkHandlers(Player player, int id) {
        if (player.getForgottenRaidInterface().handleButton(id))
            return true;
        if (player.getTierUpgrading().handleButton(id))
            return true;
        if (player.getTPInterface().handleButton(id))
            return true;
        if (player.getSlotSystem().handleButton(id))
            return true;

        if (player.getBis().handleButtonClick(id)) {
            return true;
        }

        if (ServerPerks.getInstance().handleButton(player, id)) {
            return true;
        }
        if (BestItemsInterface.buttonClicked(player, id)) {
            return true;
        }
        if (KillTrackerInterface.handleButton(player, id))
            return true;

        if (player.getIslandInterface().handleButton(id)) {
            return true;
        }
        switch (id) {
            case 14650:
                player.getPacketSender().removeInterface();
                return true;
            case 2494:
            case 2471:
            case 2461:
            case 2482:
                if (player.getDialogue() != null && player.getDialogue()
                        .type() == DialogueType.OPTION) {
                    player.getDialogue().option(player, 1);
                } else {
                   // System.out.println("Player dialogue is null.");
                }
                DialogueOptions.handle(player, id);
                break;
            case 2462:
            case 2472:
            case 2495:
            case 2483:
                if (player.getDialogue() != null && player.getDialogue()
                        .type() == DialogueType.OPTION) {
                    player.getDialogue().option(player, 2);
                }
                DialogueOptions.handle(player, id);
                break;
            case 2496:
            case 2473:
            case 2484:
                if (player.getDialogue() != null && player.getDialogue()
                        .type() == DialogueType.OPTION) {
                    player.getDialogue().option(player, 3);
                }
                DialogueOptions.handle(player, id);
                break;
            case 2497:
            case 2485:
                if (player.getDialogue() != null && player.getDialogue()
                        .type() == DialogueType.OPTION) {
                    player.getDialogue().option(player, 4);
                }
                DialogueOptions.handle(player, id);
                break;

            case 2498:
                if (player.getDialogue() != null && player.getDialogue()
                        .type() == DialogueType.OPTION) {
                    player.getDialogue().option(player, 5);
                }
                DialogueOptions.handle(player, id);
                break;

            case -8307:
            case -8308:
            case -8309:
            case -8310:
            case -8311:
            case -8312:
                if (DailyRewards.handleRewards(player, id))
                    ;
                return true;

        }

        if (GroupManager.handleButton(player, id)) {
            return true;
        }

        if (player.getModeSelection().isButton(id))
            return true;

        if ((player.isPlayerLocked() || player.isGroupIronmanLocked()) && id != 2458 && !(id >= 116005 && id <= 116010)
                && id != -12780 && id != -12779 && id != -12778 && id != -12763 && id != -12760 && id != -12767) {
            return true;
        }


        if (new RewardsHandler(player).button(id)) {
            return true;
        }
        if (StartScreen.handleButton(player, id)) {
            return true;
        }

        if (DropsInterface.handleButton(id)) {
            DropsInterface.handleButtonClick(player, id);
            return true;
        }
        if (player.isPlayerLocked() && id != 2458) {
            return true;
        }

        if (Sounds.handleButton(player, id)) {
            return true;
        }
        if (PrayerHandler.isButton(id)) {
            PrayerHandler.togglePrayerWithActionButton(player, id);
            return true;
        }
        if (CurseHandler.isButton(player, id)) {
            return true;
        }
        if (Autocasting.handleAutocast(player, id)) {
            return true;
        }

        if (LoyaltyProgramme.handleButton(player, id)) {
            return true;
        }

        if (Emotes.doEmote(player, id)) {
            return true;
        }
        if (player.getLocation() == Location.DUEL_ARENA && Dueling.handleDuelingButtons(player, id)) {
            return true;
        }
        if (Slayer.handleRewardsInterface(player, id)) {
            return true;
        }
        if (ExperienceLamps.handleButton(player, id)) {
            return true;
        }
        if (GrandExchange.handleButton(player, id)) {
            return true;
        }
        if (ClanChatManager.handleClanChatSetupButton(player, id)) {
            return true;
        }
        if (Guild.handleClanButtons(player, id)) {
            return true;
        }


        return false;
    }
}
