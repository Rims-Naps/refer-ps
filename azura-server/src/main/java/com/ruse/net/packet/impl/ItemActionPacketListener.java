package com.ruse.net.packet.impl;

import com.ruse.GameSettings;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Bank;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.BeastCasket;
import com.ruse.world.content.BeastCasketU;
import com.ruse.world.content.ExpLamps.ExpLamps;
import com.ruse.world.content.ExpLamps.NecroScrolls;
import com.ruse.world.content.ExpLamps.PrayerUnlockScrolls;
import com.ruse.world.content.FallenEventSpawner;
import com.ruse.world.content.GoodieBags.OwnerGoodiebagHandler;
import com.ruse.world.content.GoodieBags.UpgOwnerHandler;
import com.ruse.world.content.HuntersPurse;
import com.ruse.world.content.SlayerCasket;
import com.ruse.world.content.SlayerCasketU;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.box.SupplyCrate;
import com.ruse.world.content.casketopening.CasketOpening;
import com.ruse.world.content.casketopening.RewardViewer;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.instanceMananger.InstanceData;
import com.ruse.world.content.instanceMananger.InstanceInterfaceHandler;
import com.ruse.world.content.skill.impl.hunter.JarData;
import com.ruse.world.content.skill.impl.hunter.PuroPuro;
import com.ruse.world.content.skill.impl.prayer.Prayer;
import com.ruse.world.content.skill.impl.slayer.Slayer;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.content.skill.impl.summoning.SummoningData;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

import static com.ruse.world.content.ZoneProgression.TierRequirements.hasChallengerReqs;


public class ItemActionPacketListener implements PacketListener {
    private static long lastClickedClaimAll;
    public static final int THIRD_ITEM_ACTION_OPCODE = 75;
    public static final int FIRST_ITEM_ACTION_OPCODE = 122;
    public static final int SECOND_ITEM_ACTION_OPCODE = 16;

    public static int count = 0;
    private static void firstAction(Player player, Packet packet) {
        int interfaceId = packet.readUnsignedShort();
        int slot = packet.readShort();
        int itemId = packet.readShort();


        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        Item item = player.getInventory().getItems()[slot];
        player.setInteractingItem(item);

        if (!player.getControllerManager().processItemClick1(slot, item)) {
            return;
        }
        if (Prayer.isBone(itemId)) {
            Prayer.buryBone(player, itemId);
            return;
        }


        if (player.getCompanion().addAttachment(itemId))
            return;

        if (ExpLamps.handleRub(player, itemId)){
            return;
        }

        if (NecroScrolls.handleClaim(player, itemId)){
            return;
        }

        if (PrayerUnlockScrolls.handlePrayerClaim(player, itemId)){
            return;
        }

        if (player.getMembership().usableScroll(itemId)) {
            player.getMembership().redeemMembership(player, itemId);
            return;
        }
        if (player.getInvocations().is_correct_item(itemId)) {
            player.getInvocations().redeem_scroll(itemId);
            return;
        }
        switch (itemId) {
            case 401:
            case 402:
            case 403:
            case 404:
            case 405:
            case 406:
            case 407:
            case 408:
            case 409:
            case 410:
            case 411:
            case 412:
            case 413:
            case 414:
            case 415:
                player.getUpgrade().display();
                break;
            case 14639:
                int aetherAmount = player.getInventory().getAmount(14639);
                int amountNeeded = 10;

                if (aetherAmount >= amountNeeded) {
                    player.getInventory().delete(14639, amountNeeded); // Remove the required items

                    int[] rewardItems = {23180, 23181, 23182, 23183, 23184, 23185,23186}; // Replace with actual item IDs
                    int randomIndex = Misc.random(rewardItems.length - 1);
                    int rewardId = rewardItems[randomIndex];

                    player.getInventory().add(rewardId, 1); // Give 1 of the random item

                    player.sendMessage("You received: " + new Item(rewardId).getDefinition().getName() + "!");
                    World.sendMessage("<shad=0><col=AF70C3>[Oceanic] " + player.getUsername() + " has recieved:" + new Item(rewardId).getDefinition().getName() + "!");
                } else {
                    player.sendMessage("You need 10x oceanic aether");
                }
                break;
            case 23165:
                if (player.getInventory().contains(23165)) {
                    player.getInventory().delete(23165, 1);
                    player.msgFancyPurp("Your package was added to your bank!");
                        player.depositItemBank(new Item(23058,3), false);
                    player.depositItemBank(new Item(1448,5), false);
                    player.depositItemBank(new Item(3580,5), false);
                    player.depositItemBank(new Item(463,1), false);
                    player.depositItemBank(new Item(2702,1), false);
                    player.depositItemBank(new Item(5585,5), false);
                    player.depositItemBank(new Item(2706,5), false);
                    player.depositItemBank(new Item(17129,1), false);
                    player.depositItemBank(new Item(460,2), false);
                }
                break;

            case 23166:
                if (player.getInventory().contains(23166)) {
                    player.getInventory().delete(23166, 1);
                    player.msgFancyPurp("Your package was added to your bank!");
                    player.depositItemBank(new Item(23058,8), false);
                    player.depositItemBank(new Item(1448,10), false);
                    player.depositItemBank(new Item(3580,10), false);
                    player.depositItemBank(new Item(464,1), false);
                    player.depositItemBank(new Item(2703,1), false);
                    player.depositItemBank(new Item(5585,15), false);
                    player.depositItemBank(new Item(2706,10), false);
                    player.depositItemBank(new Item(17129,2), false);
                    player.depositItemBank(new Item(460,2), false);
                    player.depositItemBank(new Item(461,2), false);
                }
                break;

            case 23167:
                if (player.getInventory().contains(23167)) {
                    player.getInventory().delete(23167, 1);
                    player.msgFancyPurp("Your package was added to your bank!");
                    player.depositItemBank(new Item(23058,12), false);
                    player.depositItemBank(new Item(1448,15), false);
                    player.depositItemBank(new Item(3580,15), false);
                    player.depositItemBank(new Item(464,1), false);
                    player.depositItemBank(new Item(2704,1), false);
                    player.depositItemBank(new Item(5585,30), false);
                    player.depositItemBank(new Item(2708,10), false);
                    player.depositItemBank(new Item(17129,3), false);
                    player.depositItemBank(new Item(460,2), false);
                    player.depositItemBank(new Item(461,2), false);
                    player.depositItemBank(new Item(462,1), false);
                    player.depositItemBank(new Item(2007,1), false);
                }
                break;

            case 23168:
                if (player.getInventory().contains(23168)) {
                    player.getInventory().delete(23168, 1);
                    player.msgFancyPurp("Your package was added to your bank!");
                    player.depositItemBank(new Item(23059,4), false);
                    player.depositItemBank(new Item(23058,2), false);
                    player.depositItemBank(new Item(1448,25), false);
                    player.depositItemBank(new Item(3580,25), false);
                    player.depositItemBank(new Item(464,1), false);
                    player.depositItemBank(new Item(2704,1), false);
                    player.depositItemBank(new Item(5585,50), false);
                    player.depositItemBank(new Item(2708,20), false);
                    player.depositItemBank(new Item(17129,5), false);
                    player.depositItemBank(new Item(460,3), false);
                    player.depositItemBank(new Item(461,3), false);
                    player.depositItemBank(new Item(462,2), false);
                    player.depositItemBank(new Item(2007,2), false);
                    player.depositItemBank(new Item(2688,1), false);
                }
                break;


            case 3007:
                if (!player.getClickDelay().elapsed(500)) {
                    return;
                }
                if (GameSettings.fallentEventActive){
                    player.msgRed("The Fallen Event is already Active!");
                    return;
                }
                player.getInventory().delete(3007,1);
                player.getClickDelay().reset();
                GameSettings.fallentEventActive = true;
                FallenEventSpawner.StartFallenEvent(player);
                break;

            case 438:
                TreasureTrinket.openTrinket(player);
                break;

            case 375:
                if (!player.getClickDelay().elapsed(1100)) {
                    return;
                }
                player.getInventory().delete(375,1);
                player.getClickDelay().reset();
                player.performAnimation(new Animation(829));
                player.heal(16);
                break;

            case 3604:
            case 3605:
            case 3607:
                player.getMegaChest().handleChestClick(itemId);
                break;


            case 11052:
            case 11054:
            case 11056:
            case 3576:
            case 19062:
            case 6466:
            case 3502:
            player.getUpgrade().display();
            break;
            case 2057:
                hasChallengerReqs(player);
                break;
            case 460:
            case 461:
            case 462:
                player.getMegaChest().handleChestClick(itemId);
                break;


            case 5733:
                player.getSkillTree().resetScroll();
            break;

            /**
             * AoE Token
             */
            case 2007:
                player.getAoeToken().useToken();
            break;

            case 2008:
                if (!player.getClickDelay().elapsed(200)) {
                    return;
                }
                player.getClickDelay().reset();
                SupplyCrate.rollCrate(player);
            break;

            case 2620://Reset Tree
                player.getSkillTree().resetScroll();
                break;
            case 463: //Battle PASS t1
                if (player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.msgRed("You currently have a @yel@Battle Pass(T1) active.");
                    player.getBattlePass().displayPage();
                    return;
                }
                if (player.getBattlePass().getType() == BattlePassType.TIER2) {
                    player.msgRed("You currently have a @yel@Battle Pass(T2) active.");
                    player.getBattlePass().displayPage();
                    return;
                }
                DialogueManager.start(player, 3051);
                player.setDialogueActionId(3051);
                break;

            case 464: //BattlePass PASS t2
                if (player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.msgRed("You currently have a @yel@Battle Pass(T1) active.");
                    player.getBattlePass().displayPage();
                    return;
                }
                if (player.getBattlePass().getType() == BattlePassType.TIER2) {
                    player.msgRed("You currently have a @yel@Battle Pass(T2) active.");
                    player.getBattlePass().displayPage();
                    return;
                }
                DialogueManager.start(player, 3052);
                player.setDialogueActionId(3052);
                break;

            case 2688://PERM DROPRATE
                if (player.claimedPermDroprate){
                    player.msgRed("You've already Claimed a permanent Drop Rate Scroll!");
                    return;
                }
                new SelectionDialogue(player,"",
                        new SelectionDialogue.Selection("Claim Scroll", 0, p -> {
                            p.msgFancyPurp("You have unlocked Permanent 25% Droprate Increase, thanks for your support!");
                            p.getInventory().delete(2688,1);
                            p.getPacketSender().sendChatboxInterfaceRemoval();
                            p.setClaimedPermDroprate(true);
                        }),
                        new SelectionDialogue.Selection("Not right now...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();

                break;




            case 2689://PERM DAMAGE
                if (player.claimedPermDamage){
                    player.msgRed("You've already Claimed a permanent Damage Scroll!");
                    return;
                }
                new SelectionDialogue(player,"",
                        new SelectionDialogue.Selection("Claim Scroll", 0, p -> {
                            p.msgFancyPurp("You have unlocked Permanent 50% Maxhit Increase, thanks for your support!");
                            p.getInventory().delete(2689,1);
                            p.getPacketSender().sendChatboxInterfaceRemoval();
                            p.setClaimedPermDamage(true);

                        }),
                        new SelectionDialogue.Selection("Not right now...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();

                break;

            case 1315://INFINITE PRAYER UNLOCK
                new SelectionDialogue(player,"Claim Infinite Scroll?",
                        new SelectionDialogue.Selection("Claim Infinite Scroll", 0, p -> {
                            player.setInfinitePrayer(true);
                            p.msgFancyPurp("You have unlocked infinite prayer, thanks for your support!");
                            p.getInventory().delete(1315,1);
                            p.getPacketSender().sendChatboxInterfaceRemoval();
                        }),
                        new SelectionDialogue.Selection("Not right now...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();

                break;



            case 1310://PRESET ADDON
                new SelectionDialogue(player,"Claim Preset Scroll?",
                        new SelectionDialogue.Selection("Yes", 0, p -> {
                            p.getPresetManager().increasePresetsOwned();
                            p.msgFancyPurp("You have unlocked a preset slot! You now have: @red@<shad=0>" + p.getPresetManager().getPresetsOwned() + "!");
                            p.getPresetManager().update();
                            p.getInventory().delete(1310,1);
                            p.getPacketSender().sendChatboxInterfaceRemoval();
                        }),
                        new SelectionDialogue.Selection("Not right now...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
                break;


            case 3580://RUNE CRATE 1
                int chance = Misc.getRandom(0,2);
                player.getInventory().delete(3580, 1);
                if (player.getInventory().contains(19622)){
                    player.msgFancyPurp("Rune Pack Rewards have been added to your Pouch.");
                    if (chance == 0) {
                        player.setCurseRunes(player.getCurseRunes() + Misc.random(5,50));
                        player.setCryptRunes(player.getCryptRunes() + Misc.random(5,50));
                        player.setSoulRunes(player.getSoulRunes() + Misc.random(5,50));
                        player.setWraithRunes(player.getWraithRunes() + Misc.random(5,50));
                        player.setShadowRunes(player.getShadowRunes() + Misc.random(5,50));
                        player.setVoidRunes(player.getVoidRunes() + Misc.random(5,50));
                    }
                    if (chance == 1) {
                        player.setCurseRunes(player.getCurseRunes() + Misc.random(15,75));
                        player.setCryptRunes(player.getCryptRunes() + Misc.random(15,75));
                        player.setSoulRunes(player.getSoulRunes() + Misc.random(15,75));
                        player.setWraithRunes(player.getWraithRunes() + Misc.random(15,75));
                        player.setShadowRunes(player.getShadowRunes() + Misc.random(15,75));
                        player.setVoidRunes(player.getVoidRunes() + Misc.random(15,75));
                    }
                    if (chance == 2) {
                        player.setCurseRunes(player.getCurseRunes() + Misc.random(35,100));
                        player.setCryptRunes(player.getCryptRunes() + Misc.random(35,100));
                        player.setSoulRunes(player.getSoulRunes() + Misc.random(35,100));
                        player.setWraithRunes(player.getWraithRunes() + Misc.random(35,100));
                        player.setShadowRunes(player.getShadowRunes() + Misc.random(35,100));
                        player.setVoidRunes(player.getVoidRunes() + Misc.random(35,100));
                    }
                    return;
                }

                player.msgFancyPurp("Rune Pack Rewards have been added to your Bank.");
                if (chance == 0) {
                    player.depositItemBank(new Item(20010, Misc.random(5,50)), false);
                    player.depositItemBank(new Item(20015, Misc.random(5,50)), false);
                    player.depositItemBank(new Item(20014, Misc.random(5,50)), false);
                    player.depositItemBank(new Item(20011, Misc.random(5,50)), false);
                    player.depositItemBank(new Item(20012, Misc.random(5,50)), false);
                    player.depositItemBank(new Item(20013, Misc.random(5,50)), false);
                }
                if (chance == 1) {
                    player.depositItemBank(new Item(20010, Misc.random(15,75)), false);
                    player.depositItemBank(new Item(20015, Misc.random(15,75)), false);
                    player.depositItemBank(new Item(20014, Misc.random(15,75)), false);
                    player.depositItemBank(new Item(20011, Misc.random(15,75)), false);
                    player.depositItemBank(new Item(20012, Misc.random(15,75)), false);
                    player.depositItemBank(new Item(20013, Misc.random(15,75)), false);
                }
                if (chance == 2) {
                    player.depositItemBank(new Item(20010, Misc.random(35,100)), false);
                    player.depositItemBank(new Item(20015, Misc.random(35,100)), false);
                    player.depositItemBank(new Item(20014, Misc.random(35,100)), false);
                    player.depositItemBank(new Item(20011, Misc.random(35,100)), false);
                    player.depositItemBank(new Item(20012, Misc.random(35,100)), false);
                    player.depositItemBank(new Item(20013, Misc.random(35,100)), false);

                }
                break;

            case 3582://RUNE CRATE 2
                int chance2 = Misc.getRandom(0,2);
                player.getInventory().delete(3582, 1);

                if (player.getInventory().contains(19622)){
                    player.msgFancyPurp("Rune Pack Rewards have been added to your Pouch.");
                    if (chance2 == 0) {
                        player.setCurseRunes(player.getCurseRunes() + Misc.random(45,125));
                        player.setCryptRunes(player.getCryptRunes() + Misc.random(45,125));
                        player.setSoulRunes(player.getSoulRunes() + Misc.random(45,125));
                        player.setWraithRunes(player.getWraithRunes() + Misc.random(45,125));
                        player.setShadowRunes(player.getShadowRunes() + Misc.random(45,125));
                        player.setVoidRunes(player.getVoidRunes() + Misc.random(45,125));
                    }
                    if (chance2 == 1) {
                        player.setCurseRunes(player.getCurseRunes() + Misc.random(55,165));
                        player.setCryptRunes(player.getCryptRunes() + Misc.random(55,165));
                        player.setSoulRunes(player.getSoulRunes() + Misc.random(55,165));
                        player.setWraithRunes(player.getWraithRunes() + Misc.random(55,165));
                        player.setShadowRunes(player.getShadowRunes() + Misc.random(55,165));
                        player.setVoidRunes(player.getVoidRunes() + Misc.random(55,165));
                    }
                    if (chance2 == 2) {
                        player.setCurseRunes(player.getCurseRunes() + Misc.random(65,175));
                        player.setCryptRunes(player.getCryptRunes() + Misc.random(65,175));
                        player.setSoulRunes(player.getSoulRunes() + Misc.random(65,175));
                        player.setWraithRunes(player.getWraithRunes() + Misc.random(65,175));
                        player.setShadowRunes(player.getShadowRunes() + Misc.random(65,175));
                        player.setVoidRunes(player.getVoidRunes() + Misc.random(65,175));
                    }
                    return;
                }

                player.msgFancyPurp("Rune Pack Rewards have been added to your Bank.");
                if (chance2 == 0) {
                    player.depositItemBank(new Item(20010, Misc.random(45,125)), false);
                    player.depositItemBank(new Item(20015, Misc.random(45,125)), false);
                    player.depositItemBank(new Item(20014, Misc.random(45,125)), false);
                    player.depositItemBank(new Item(20011, Misc.random(45,125)), false);
                    player.depositItemBank(new Item(20012, Misc.random(45,125)), false);
                    player.depositItemBank(new Item(20013, Misc.random(45,125)), false);
                }
                if (chance2 == 1) {
                    player.depositItemBank(new Item(20010, Misc.random(55,165)), false);
                    player.depositItemBank(new Item(20015, Misc.random(55,165)), false);
                    player.depositItemBank(new Item(20014, Misc.random(55,165)), false);
                    player.depositItemBank(new Item(20011, Misc.random(55,165)), false);
                    player.depositItemBank(new Item(20012, Misc.random(55,165)), false);
                    player.depositItemBank(new Item(20013, Misc.random(55,165)), false);
                }
                if (chance2 == 2) {
                    player.depositItemBank(new Item(20010, Misc.random(65,175)), false);
                    player.depositItemBank(new Item(20015, Misc.random(65,175)), false);
                    player.depositItemBank(new Item(20014, Misc.random(65,175)), false);
                    player.depositItemBank(new Item(20011, Misc.random(65,175)), false);
                    player.depositItemBank(new Item(20012, Misc.random(65,175)), false);
                    player.depositItemBank(new Item(20013, Misc.random(65,175)), false);
                }
                break;


            case 1446://RESOURCE PACK T1
                player.msgFancyPurp("Resource Pack Rewards have been added to your Bank.");

                int tab = Bank.getTabForItem(player, item);
                int rng = Misc.random(0,4);

                player.getInventory().delete(1446, 1);

                if (rng == 0 || rng == 1 || rng == 2) {
                    player.depositItemBank(new Item(1400, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1408, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1401, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1409, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1402, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1410, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1403, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1411, Misc.random(0,25)), false);
                }
                if (rng == 3 ) {
                    player.depositItemBank(new Item(1404, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1412, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1416, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1405, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1413, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1417, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1406, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1414, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1418, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1407, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1415, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1419, Misc.random(0,25)), false);
                }
                if (rng == 4) {
                    player.depositItemBank(new Item(1400, Misc.random(0,100)), false);
                    player.depositItemBank(new Item(1400, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1400, Misc.random(0,25)), false);
                    player.depositItemBank(new Item(1400, Misc.random(0,25)), false);
                }
                break;

            case 1447://RESOURCE PACK T1
                int tab2 = Bank.getTabForItem(player, item);
                int rng2 = Misc.random(0,4);
                player.getInventory().delete(1447, 1);

                player.msgFancyPurp("Resource Pack Rewards have been added to your bank.");

                if (rng2 == 0 || rng2 == 1 || rng2 == 2) {
                    player.depositItemBank(new Item(1400, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1408, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1401, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1409, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1402, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1410, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1403, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1411, Misc.random(0,65)), false);
                }
                if (rng2 == 3 ) {
                    player.depositItemBank(new Item(1404, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1412, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1416, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1405, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1413, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1417, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1406, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1414, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1418, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1407, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1415, Misc.random(0,65)), false);
                    player.depositItemBank(new Item(1419, Misc.random(0,65)), false);
                }
                if (rng2 == 4) {
                    player.depositItemBank(new Item(1428, Misc.random(25,125)), false);
                    player.depositItemBank(new Item(1430, Misc.random(25,125)), false);
                    player.depositItemBank(new Item(1429, Misc.random(25,125)), false);
                    player.depositItemBank(new Item(1431, Misc.random(25,125)), false);

                }
                break;

            case 1448://POTION PACK

                player.msgFancyPurp("Potion Pack rewards have been added to your Bank.");
                int tab3 = Bank.getTabForItem(player, item);
                int rng3 = Misc.random(0,5);

                player.getInventory().delete(1448, 1);
                if (rng3 == 0 || rng3 == 1 || rng3 == 2 || rng3 == 3) {
                    player.depositItemBank(new Item(23119, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(23122, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(23121, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(17586, Misc.random(0,4)), false);
                    player.depositItemBank(new Item(17584, Misc.random(0,4)), false);
                    player.depositItemBank(new Item(17582, Misc.random(0,4)), false);
                    player.depositItemBank(new Item(17490, Misc.random(0,4)), false);

                }
                if (rng3 == 4 ) {
                    player.depositItemBank(new Item(23119, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(23122, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(23121, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(1321, Misc.random(0,4)), false);
                    player.depositItemBank(new Item(17586, Misc.random(0,8)), false);
                    player.depositItemBank(new Item(17584, Misc.random(0,8)), false);
                    player.depositItemBank(new Item(17582, Misc.random(0,8)), false);
                    player.depositItemBank(new Item(17490, Misc.random(0,4)), false);

                }
                if (rng3 == 5) {
                    player.depositItemBank(new Item(23119, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(23122, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(23121, Misc.random(0,500)), false);
                    player.depositItemBank(new Item(1321, Misc.random(0,4)), false);
                    player.depositItemBank(new Item(1323, Misc.random(0,4)), false);
                    player.depositItemBank(new Item(17586, Misc.random(0,12)), false);
                    player.depositItemBank(new Item(17584, Misc.random(0,12)), false);
                    player.depositItemBank(new Item(17582, Misc.random(0,12)), false);
                    player.depositItemBank(new Item(17490, Misc.random(0,4)), false);
                }
                break;
            case 2554:
                if(player.getCompanion().getMyCompanion() == null) {
                    player.msgRed("You must have a Companion to use these.");
                    return;
                }
                if(!player.getCompanion().isHasCompanion()) {
                    player.msgRed("@red@Summon your Companion before doing this!");
                    return;
                }
                int xp = Misc.random(1000, 2500);
                if (!player.getClickDelay().elapsed(400)) {
                    return;
                }
                player.getClickDelay().reset();
                if (player.getInventory().contains(2554)){
                    player.getInventory().delete(2554,1);
                    player.getCompanion().addExp(xp);
                    player.msgFancyPurp("You crush the gem and gain " + xp + "X Experience for your companion!");
                }
                break;
            case 2556:
                if(player.getCompanion().getMyCompanion() == null) {
                    player.msgRed("You must have a Companion to use these.");
                    return;
                }
                if(!player.getCompanion().isHasCompanion()) {
                    player.msgRed("@red@Summon your Companion before doing this!");
                    return;
                }

                int exp = Misc.random(5000, 10000);
                if (!player.getClickDelay().elapsed(400)) {
                    return;
                }
                player.getClickDelay().reset();
                if (player.getInventory().contains(2556)){
                    player.getInventory().delete(2556,1);
                    player.getCompanion().addExp(exp);
                    player.msgFancyPurp("You crush the gem and gain " + exp + "X Experience for your companion!");
                }
                break;

            case 952:
                if (!player.getClickDelay().elapsed(1800)) {
                    return;
                }
                player.getClickDelay().reset();
                MarinasTasks.dig(player);

                break;
            case 3578://OWNER GOODIEBAG
                OwnerGoodiebagHandler.open(player);
                player.setViewing(Player.INTERFACES.GOODIEBAG);
                break;
            case 4022://U GOODIEBAG
                UpgOwnerHandler.open(player);
                player.setViewing(Player.INTERFACES.GOODIEBAG_U);
                break;
            case 7509: //Rock Cake - Ascension Cake
                if (player.getRockCakeCharges() == 0) {
                    player.getPacketSender().sendMessage("You need more Ascension cake charges to eat the cake.");
                    return;
                }
                player.setRockCakeCharges(player.getRockCakeCharges() - 1);
                player.getSkillManager().setCurrentLevel(Skill.HITPOINTS, 10);
                player.getPacketSender().sendMessage("You take a bit of the Ascension cake, and break a tooth!");
                player.getPacketSender().sendMessage("You now have " + player.getRockCakeCharges() + " charges remaining!");
            break;
            case 17819:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.ENCHANTED_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 17129:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.ELEMENTAL_CACHE);
                player.getCasketOpening().openInterface();
                break;
            case 2622:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.LOVERS_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 7053:
                if (player.getSouls()< 5000) {
                    player.sendMessage("You need 5000 souls to harvest the lantern!");
                    return;
                }
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.CHARONS_LANTERN);
                player.getCasketOpening().openInterface();
                break;
            case 23164:
                if (player.posAbility == false) {
                    player.setPosAbility(true);
                    player.getInventory().delete(23164, 1);
                    World.sendMessage("<col=AF70C3><shad=0>[Poseidon] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>Just unlocked the Wrath of Poseidon");
                } else {
                    player.sendMessage("You already unlocked this ability!");
                }
                break;
            case 2624:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.ELEMENTAL_CACHE_U);
                player.getCasketOpening().openInterface();
                break;
            case 17130:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.PANDORAS_BOX);
                player.getCasketOpening().openInterface();
                break;
            case 23173:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.NECROMANCY_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 23171:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.LOW_TIER_WEAPON_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 23172:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.HIGH_TIER_WEAPON_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 15670:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.BOND_CASKET);
                player.getCasketOpening().openInterface();
                break;
            case 15666:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.NOVICE_WEAPON_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 15671:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.BOND_CASKET_ENCHANTED);
                player.getCasketOpening().openInterface();
                break;
            case 19118:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.VOID_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 2090:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.SPECTRAL_CACHE);
                player.getCasketOpening().openInterface();
                break;
            case 15668:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.BOX_OF_TREASURES);
                player.getCasketOpening().openInterface();
                break;
            case 15667:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.BOX_OF_PLUNDERS);
                player.getCasketOpening().openInterface();
                break;
            case 15669:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.BOX_OF_BLESSINGS);
                player.getCasketOpening().openInterface();
                break;
            case 10256:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.EARTH_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 10260:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.WATER_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 1453:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.FROST_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 10262:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.FIRE_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 2009:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.CORRUPT_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 754:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.OWNER_WEP_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 720:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.EGG_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 731:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.VOTE_CRATE);
                player.getCasketOpening().openInterface();
                break;
            case 1306:
                if (!player.getClickDelay().elapsed(150))
                    break;
                if (player.getInventory().getFreeSlots() <= 3) {
                    player.getPacketSender().sendMessage("You'll need at least 3 free spaces to do that.");
                    break;
                }
                player.getClickDelay().reset();
                SlayerCasket.openbox(player);
                break;
            case 1307:
                if (!player.getClickDelay().elapsed(150))
                    break;
                if (player.getInventory().getFreeSlots() <= 3) {
                    player.getPacketSender().sendMessage("You'll need at least 3 free spaces to do that.");
                    break;
                }
                player.getClickDelay().reset();
                SlayerCasketU.openbox(player);
                break;
            case 300:
                if (!player.getClickDelay().elapsed(150))
                    break;
                if (player.getInventory().getFreeSlots() <= 3) {
                    player.getPacketSender().sendMessage("You'll need at least 3 free spaces to do that.");
                    break;
                }
                player.getClickDelay().reset();
                BeastCasket.openbox(player);
                break;
            case 301:
                if (!player.getClickDelay().elapsed(150))
                    break;
                if (player.getInventory().getFreeSlots() <= 3) {
                    player.getPacketSender().sendMessage("You'll need at least 3 free spaces to do that.");
                    break;
                }
                player.getClickDelay().reset();
                BeastCasketU.openbox(player);
                break;
            case 18015:
                if (!player.getClickDelay().elapsed(250)) {
                    return;
                }
                player.getClickDelay().reset();
                player.sendMessage("<shad=0>Monster Essence: <shad=0>" + player.getMonsteressence());
                player.sendMessage("<shad=0>Fire Essence: <shad=0>" + player.getFireessence());
                player.sendMessage("<shad=0>Water Essence: <shad=0>" + player.getWateressence());
                player.sendMessage("<shad=0>Earth Essence: <shad=0>" + player.getEarthessence());
                player.sendMessage("<shad=0>Slayer Essence: <shad=0>" + player.getSlayeressence());
                player.sendMessage("<shad=0>Beast Essence: <shad=0>" + player.getBeastEssence());
                player.sendMessage("<shad=0>Corrupt Essence: <shad=0>" + player.getCorruptEssence());
                player.sendMessage("<shad=0>Enchanted Essence: <shad=0>" + player.getEnchantedEssence());
                player.sendMessage("<shad=0>Spectral Essence: <shad=0>" + player.getSpectralEssence());
                break;
            case 2056:
                if (!player.getClickDelay().elapsed(250)) {
                    return;
                }
                player.getClickDelay().reset();
                player.sendMessage("<shad=0>Vorpal Ammo: <shad=0>" + player.getVorpalAmmo());
                player.sendMessage("<shad=0>Bloodstained Ammo: <shad=0>" + player.getBloodstainedAmmo());
                player.sendMessage("<shad=0>Symbiote Ammo: <shad=0>" + player.getSymbioteAmmo());
                player.sendMessage("<shad=0>Nether Ammo: <shad=0>" + player.getNetherAmmo());;
                break;
            case 19622:
                if (!player.getClickDelay().elapsed(250)) {
                    return;
                }
                player.getClickDelay().reset();
                player.sendMessage("<shad=0>Curse Runes: <shad=0>" + player.getCurseRunes());
                player.sendMessage("<shad=0>Soul Runes: <shad=0>" + player.getSoulRunes());
                player.sendMessage("<shad=0>Crypt Runes: <shad=0>" + player.getCryptRunes());
                player.sendMessage("<shad=0>Shadow Runes: <shad=0>" + player.getShadowRunes());
                player.sendMessage("<shad=0>Wraith Runes: <shad=0>" + player.getWraithRunes());
                player.sendMessage("<shad=0>Void Runes: <shad=0>" + player.getVoidRunes());
                break;
            case 1308:
                if (!player.getClickDelay().elapsed(250)) {
                    return;
                }
                player.getClickDelay().reset();
                player.sendMessage("<shad=0>Damage Salts: <shad=0>" + player.getDamageSalts());
                player.sendMessage("<shad=0>Droprate Salts: <shad=0>" + player.getDroprateSalts());
                player.sendMessage("<shad=0>Critical Salts: <shad=0>" + player.getCriticalSalts());
                break;
            case 18681:
                player.getCompanion().spawn(player.getCompanion().getMyCompanion().getCompanionId());
                break;
            case 2705:
                if (player.getLocation() == Location.NECROMANCY_GAME_AREA){
                    player.msgRed("That doesn't seem to work here...");
                    return;
                }
                if (!player.getClickDelay().elapsed(2600)) {
                    return;
                }
                player.heal(20);
                player.performAnimation(new Animation(829));
                player.getClickDelay().reset();
                break;
            case 1300:
                if (player.getLocation() == Location.NECROMANCY_GAME_AREA){
                    player.msgRed("That doesn't seem to work here...");
                    return;
                }
                if (!player.getClickDelay().elapsed(2300)) {
                    return;
                }
                player.heal(27);
                player.performAnimation(new Animation(829));
                player.getClickDelay().reset();
                break;
            case 1301:
                if (player.getLocation() == Location.NECROMANCY_GAME_AREA){
                    player.msgRed("That doesn't seem to work here...");
                    return;
                }
                if (!player.getClickDelay().elapsed(2000)) {
                    return;
                }
                player.heal(35);
                player.performAnimation(new Animation(829));
                player.getClickDelay().reset();
                break;

                //HANDLE BONDS
            case 10945:
            case 10946:
            case 23057:
            case 23058:
            case 23059:
            case 23060:
                player.setDialogueActionId(-1);
             /*   if (player.getRights() == PlayerRights.YOUTUBER){
                    player.getPacketSender().sendMessage("<shad=0><col=AF70C3>You cannot claim bonds with a Youtuber Rank!");
                    return;
                }*/
                BondHandler.checkForRankUpdate(player);
                if (BondHandler.confirmationDialogue(player, itemId)){
                    return;
                }
                break;
            case 19023:
                if (player.getDrBoost() >= 200) {
                    player.sendMessage("@red@<shad=0>You have Reached the Cap for Dr Bonus");
                    return;
                }
                if (System.currentTimeMillis() < lastClickedClaimAll + 5000) {
                    player.getPacketSender().sendMessage("@red@Please wait 5 second before clicking claim all again.");
                } else {
                    player.addDrBoost(1);
                    player.getInventory().delete(19023, 1);
                    lastClickedClaimAll = System.currentTimeMillis();
                }
                break;
            case 19024:
                if (player.getDmgBoost() >= 50) {
                    player.sendMessage("@red@<shad=0>You have Reached the Cap for Maxhit Bonus");
                    return;
                }
                if (System.currentTimeMillis() < lastClickedClaimAll + 5000) {
                    player.getPacketSender().sendMessage("@red@Please wait 5 second before clicking claim all again.");
                } else {
                    player.addDmgBoost(1.01);
                    player.getInventory().delete(19024, 1);
                    lastClickedClaimAll = System.currentTimeMillis();
                }
                break;
            case 3595:
               /* if (player.openedStarterBox){
                    player.getInventory().delete(3595, 28);
                    return;
                }*/
                if (!player.getClickDelay().elapsed(400)) {
                    player.getPacketSender().sendMessage("Please wait a few seconds.");
                    break;
                }

                if (player.getInventory().getFreeSlots() >= 14) {
                    player.getInventory().delete(3595, 9999);

                    //ARMOR
                    player.getInventory().add(19153, 1);
                    player.getInventory().add(19142, 1);
                    player.getInventory().add(19141, 1);
                    player.getInventory().add(23097, 1);
                    player.getInventory().add(14914, 1);
                    //WEPS
                    player.getInventory().add(5000, 1);

                    //ACCESORIES
                    player.getInventory().add(12468, 1);
                    player.getInventory().add(439, 1);
                    player.getInventory().add(10069, 1);
                    player.getInventory().add(6805, 1);

                    //EXTRAS
                    player.getInventory().add(2705, 1);
                    player.getInventory().add(4155, 1);
                    player.getInventory().add(18015, 1);
                    player.getInventory().add(19622, 1);
                    player.getInventory().add(20060, 1);
                    player.getInventory().add(20030, 1);

                    player.setOpenedStarterBox(true);
                    break;
                } else {
                    player.getPacketSender().sendMessage("You'll need at least 14 free spaces to do that.");

                }
                player.getClickDelay().reset();
                break;
            case 3600:
               /* if (player.openedStarterBox){
                    player.getInventory().delete(3600, 28);
                    return;
                }*/
                if (!player.getClickDelay().elapsed(400)) {
                    player.getPacketSender().sendMessage("Please wait a few seconds.");
                    break;
                }

                if (player.getInventory().getFreeSlots() >= 14) {
                    player.getInventory().delete(3600, 9999);

                    //ARMOR
                    player.getInventory().add(13037, 1);
                    player.getInventory().add(13038, 1);
                    player.getInventory().add(13039, 1);
                    player.getInventory().add(13040, 1);
                    player.getInventory().add(13041, 1);
                    //WEPS
                    player.getInventory().add(5006, 1);

                    //ACCESORIES
                    player.getInventory().add(12468, 1);
                    player.getInventory().add(439, 1);
                    player.getInventory().add(10071, 1);
                    player.getInventory().add(6807, 1);


                    //EXTRAS
                    player.getInventory().add(2705, 1);
                    player.getInventory().add(4155, 1);
                    player.getInventory().add(18015, 1);
                    player.getInventory().add(19622, 1);
                    player.getInventory().add(20060, 1);
                    player.getInventory().add(20040, 1);

                    player.setOpenedStarterBox(true);
                    break;

                } else {
                    player.getPacketSender().sendMessage("You'll need at least 14 free spaces to do that.");

                }
                player.getClickDelay().reset();
                break;
            case 3601:
                if (player.openedStarterBox){
                    player.getInventory().delete(3601, 28);
                    return;
                }

                if (!player.getClickDelay().elapsed(400)) {
                    player.getPacketSender().sendMessage("Please wait a few seconds.");
                    break;
                }

                if (player.getInventory().getFreeSlots() >= 14) {
                    player.getInventory().delete(3601, 9999);
                    //ARMOR
                    player.getInventory().add(13030, 1);
                    player.getInventory().add(13031, 1);
                    player.getInventory().add(13032, 1);
                    player.getInventory().add(13033, 1);
                    player.getInventory().add(13034, 1);
                    //WEPS
                    player.getInventory().add(5003, 1);

                    //ACCESORIES
                    player.getInventory().add(12468, 1);
                    player.getInventory().add(439, 1);
                    player.getInventory().add(10074, 1);
                    player.getInventory().add(6806, 1);

                    //EXTRAS
                    player.getInventory().add(2705, 1);
                    player.getInventory().add(4155, 1);
                    player.getInventory().add(18015, 1);
                    player.getInventory().add(19622, 1);
                    player.getInventory().add(20060, 1);
                    player.getInventory().add(20035, 1);

                    player.setOpenedStarterBox(true);
                    break;
                } else {
                    player.getPacketSender().sendMessage("You'll need at least 14 free spaces to do that.");

                }
                player.getClickDelay().reset();
                break;
            case 9719:
                if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK){
                    player.msgRed("You don't currently have a task.");
                    return;
                }
                switch(player.getSlayer().getSlayerMaster()){
                    case BEGINNER_SLAYER:
                    case MEDIUM_SLAYER:
                    case ELITE_SLAYER:
                    case ENCHANTED_MASTER:
                    case CORRUPT_SLAYER:
                    case SPECTRAL_SLAYER:

                        new SelectionDialogue(player,"", new SelectionDialogue.Selection("Skip Task", 0, p -> {
                            p.getPacketSender().sendChatboxInterfaceRemoval();
                            player.getSlayer().resetSlayerTask();}),
                        new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
                        break;
                    case BEAST_MASTER:
                    case BEAST_MASTER_X:
                    case SPECTRAL_BEAST:
                        if (player.getSlayeressence() <= 999){
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            player.msgRed("You need 1000 Slayer Essence in your pouch to skip a Beast Task.");
                            return;
                        }
                        new SelectionDialogue(player,"", new SelectionDialogue.Selection("Skip Task", 0, p -> {
                            player.setSlayeressence(player.getSlayeressence() - 1000);
                            player.msgFancyPurp("You pay 1000 Slayer Essence to skip your Beast Task");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            player.getSlayer().resetSlayerTask();}),
                        new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
                        break;
                }
                break;


                //T1 CRATE
            case 20017:

                if (!player.getClickDelay().elapsed(400)) {
                    player.getPacketSender().sendMessage("Please wait a few seconds.");
                    break;
                }

                if (player.getInventory().getFreeSlots() >= 16) {
                    player.getInventory().delete(20017, 1);
                    //MEMBERSHIP
                    player.getInventory().add(2703, 1);
                    //BRONZE CASE
                    player.getInventory().add(460, 1);
                    //$25 BOND
                    player.getInventory().add(23058, 2);
                    //MYSTIC 1 SET
                    player.getInventory().add(17024, 1);
                    player.getInventory().add(17025, 1);
                    player.getInventory().add(17026, 1);
                    player.getInventory().add(17027, 1);
                    player.getInventory().add(17028, 1);
                    //MYSTIC BOW
                    player.getInventory().add(16415, 1);
                    //POTION PACKS
                    player.getInventory().add(1448, 4);
                    //DREAM TICKETS
                    player.getInventory().add(2706, 2);
                    World.sendMessage("<col=AF70C3><shad=0>[RELEASE] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>Just opened a Tier 1 Release Pack");
                    break;
                } else {
                    player.getPacketSender().sendMessage("You'll need at least 16 free spaces to do that.");

                }
                player.getClickDelay().reset();
                break;


            //T2 CRATE
            case 20018:

                if (!player.getClickDelay().elapsed(400)) {
                    player.getPacketSender().sendMessage("Please wait a few seconds.");
                    break;
                }

                if (player.getInventory().getFreeSlots() >= 16) {
                    player.getInventory().delete(20018, 1);

                    //MEMBERSHIP
                    player.getInventory().add(2704, 1);
                    //BRONZE CASE
                    player.getInventory().add(460, 2);
                    //$100 BOND
                    player.getInventory().add(23059, 1);
                    //MYSTIC 2 SET
                    player.getInventory().add(17029, 1);
                    player.getInventory().add(17030, 1);
                    player.getInventory().add(17031, 1);
                    player.getInventory().add(17032, 1);
                    player.getInventory().add(17033, 1);
                    //MYSTIC 2 WEAPON
                    player.getInventory().add(16416, 1);
                    //POTION PACKS
                    player.getInventory().add(1448, 6);
                    //DREAM TICKETS
                    player.getInventory().add(2706, 4);

                    //EXTRAS
                    //player.getInventory().add(2706, 8000);
                    World.sendMessage("<col=AF70C3><shad=0>[STARTER] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>Just opened a Tier 2 Starter Pack");

                    break;
                } else {
                    player.getPacketSender().sendMessage("You'll need at least 16 free spaces to do that.");

                }
                player.getClickDelay().reset();
                break;

            //T3 CRATE
            case 20019:

                if (!player.getClickDelay().elapsed(400)) {
                    player.getPacketSender().sendMessage("Please wait a few seconds.");
                    break;
                }

                if (player.getInventory().getFreeSlots() >= 16) {
                    player.getInventory().delete(20019, 1);

                    //MEMBERSHIP
                    player.getInventory().add(2006, 1);
                    //SILVER CASE
                    player.getInventory().add(461, 1);
                    //2X $100 BOND
                    player.getInventory().add(23059, 2);
                    //MYSTIC 2 SET
                    player.getInventory().add(17029, 1);
                    player.getInventory().add(17030, 1);
                    player.getInventory().add(17031, 1);
                    player.getInventory().add(17032, 1);
                    player.getInventory().add(17033, 1);
                    //MYSTIC 2 WEAPON
                    player.getInventory().add(16416, 1);
                    //POTION PACKS
                    player.getInventory().add(1448, 8);
                    //DREAM TICKETS
                    player.getInventory().add(2706, 6);

                    //EXTRAS
                    //player.getInventory().add(2706, 15000);
                    World.sendMessage("<col=AF70C3><shad=0>[STARTER] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>Just opened a Tier 3 Starter Pack");

                    break;
                } else {
                    player.getPacketSender().sendMessage("You'll need at least 16 free spaces to do that.");

                }
                player.getClickDelay().reset();
                break;


            //LEGACY BUNDLE
            case 20050:

                if (!player.getClickDelay().elapsed(400)) {
                    player.getPacketSender().sendMessage("Please wait a few seconds.");
                    break;
                }

                if (player.getInventory().getFreeSlots() >= 16) {
                    player.getInventory().delete(20050, 1);

                    //MEMBERSHIP
                    player.getInventory().add(2006, 1);
                    //GOLD CASE
                    player.getInventory().add(462, 1);
                    //3X $250 BOND
                    player.getInventory().add(23060, 3);
                    //MYSTIC 3 SET
                    player.getInventory().add(17034, 1);
                    player.getInventory().add(17035, 1);
                    player.getInventory().add(17036, 1);
                    player.getInventory().add(17037, 1);
                    player.getInventory().add(17038, 1);
                    //BLOODLUST
                    player.getInventory().add(441, 1);
                    //POTION PACKS
                    player.getInventory().add(1448, 12);
                    //DREAM TICKETS
                    player.getInventory().add(2706, 8);
                    //OWNER GOODIEBAG
                    player.getInventory().add(3578, 2);
                    //EXTRAS
                   // player.getInventory().add(2706, 50000);
                    World.sendMessage("<col=AF70C3><shad=0>[RELEASE] @red@<shad=0>" + player.getUsername() + " <col=AF70C3><shad=0>Just opened a LEGACY BUNDLE!");

                    break;
                } else {
                    player.getPacketSender().sendMessage("You'll need at least 16 free spaces to do that.");

                }
                player.getClickDelay().reset();
                break;
            case 358:
                if (player.getDamageTimer().isActive()){
                    player.msgRed("You currently have the effects of a Damage Potion active.");
                    return;
                }
                if (player.getCritTimer().isActive()){
                    player.msgRed("You currently have the effects of a Critical Potion active.");
                    return;
                }
                if (player.getDroprateTimer().isActive()){
                    player.msgRed("You currently have the effects of a Droprate Potion active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getDivine().append();
                break;
            case 17582:
                if (player.getDivine().isActive()){
                    player.msgRed("You currently have the effects of a Divine Potion active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getDamageTimer().append();
                break;
            case 17584:
                if (player.getDivine().isActive()){
                    player.msgRed("You currently have the effects of a Divine Potion active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getCritTimer().append();
                break;
            case 17586:
                if (player.getDivine().isActive()){
                    player.msgRed("You currently have the effects of a Divine Potion active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getDroprateTimer().append();
                break;
            case 1321:
                if (player.getRage().isActive()){
                    player.msgRed("You currently have the effects of a Rage Potion active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getFury().append();
               break;

               //
            case 2092:
                if (player.getRaidBoost().isActive()){
                    player.msgRed("You currently have the effects of a Raid Boost active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getRaidBoost().append();
                break;
            case 2093:
                if (player.getNecroBoost().isActive()){
                    player.msgRed("You currently have the effects of a Necro Boost active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getNecroBoost().append();
                break;
            case 2094:
                if (player.getSlayerBoost().isActive()){
                    player.msgRed("You currently have the effects of a Slayer Boost active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getSlayerBoost().append();
                break;
            case 2095:
                if (player.getKillBoost().isActive()){
                    player.msgRed("You currently have the effects of a Kill Boost active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getKillBoost().append();
                break;

            case 1323:
                if (player.getFury().isActive()){
                    player.msgRed("You currently have the effects of a Fury Potion active.");
                    return;
                }
                if (!player.getClickDelay().elapsed(250))
                    return;
                player.getRage().append();
                break;
            case 1465:
                if (!player.getClickDelay().elapsed(1750))
                    return;
                player.getIceBornTimer().append();
                break;
            case 716:
                if (!player.getClickDelay().elapsed(1750))
                    return;
                player.getEasterTimer().append();
                break;
            case 2601:
                if (!player.getClickDelay().elapsed(1750))
                    return;
                player.getLoveTImer().append();
                break;
            case 12427:
                if (!player.getClickDelay().elapsed(1750))
                    return;
                player.getMagma().append();
                break;
            case 12423:
                if (!player.getClickDelay().elapsed(1750))
                    return;
                player.getOvergrown().append();
                break;
            case 12424:
                if (!player.getClickDelay().elapsed(1750))
                    return;
                player.getNautic().append();
                break;

            case 19759:
                player.getInventory().delete(19759, 1);
                int num = 15000 / Difficulty.getDifficultyModifier(player, Skill.SLAYER);
                player.getSkillManager().addExperience(Skill.SLAYER, num);
                player.getPacketSender().sendMessage("You have Been rewarded with some slayer XP.");
                break;
            case 1469:
                if (player.getHolidayTaskHandler().getTaskNpc() == -1){
                    player.msgRed("You don't have a Frost Task to travel to..");
                    return;
                }
                int randomLocation = new Random().nextInt(3);
                int randomLoc = Misc.getRandom(2);
                Position[] FROST_TITAN = {
                        new Position(3165, 5516, 0),
                        new Position(3165, 5516, 4),
                        new Position(3165, 5516, 8),
                };
                Position[] FROST_LORD = {
                        new Position(3165, 5516, 0),
                        new Position(3165, 5516, 4),
                        new Position(3165, 5516, 8),
                };
                Position[] FROST_DEMON = {
                        new Position(3158, 5533, 0),
                        new Position(3158, 5533, 4),
                        new Position(3158, 5533, 8),
                };
                Position[] FROST_FIEND = {
                        new Position(3175, 5560, 0),
                        new Position(3175, 5560, 4),
                        new Position(3175, 5560, 8),
                };
                Position[] FROST_GUARDIAN = {
                        new Position(3189, 5535, 0),
                        new Position(3189, 5535, 4),
                        new Position(3189, 5535, 8),
                };
                Position[] FROST_ELEMENTAL = {
                        new Position(3213, 5535, 0),
                        new Position(3213, 5535, 4),
                        new Position(3213, 5535, 8),
                };
                Position[] FROST_GIANT = {
                        new Position(3219, 5510, 0),
                        new Position(3219, 5510, 4),
                        new Position(3219, 5510, 8),
                };
                Position[] FROST_DRAGON = {
                        new Position(3238, 5539, 0),
                        new Position(3238, 5539, 4),
                        new Position(3238, 5539, 8),
                };
                Position[] FROST_IMP = {
                        new Position(3238, 5539, 0),
                        new Position(3238, 5539, 4),
                        new Position(3238, 5539, 8),
                };

                if (player.getHolidayTaskHandler().getTaskNpc() == 1051){
                    Position selectedLocation = FROST_TITAN[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1052){
                    Position selectedLocation = FROST_LORD[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1053){
                    Position selectedLocation = FROST_DEMON[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1054){
                    Position selectedLocation = FROST_FIEND[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1055){
                    Position selectedLocation = FROST_GUARDIAN[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1056){
                    Position selectedLocation = FROST_ELEMENTAL[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1057){
                    Position selectedLocation = FROST_GIANT[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1058){
                    Position selectedLocation = FROST_DRAGON[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                if (player.getHolidayTaskHandler().getTaskNpc() == 1059){
                    Position selectedLocation = FROST_IMP[randomLoc];
                    TeleportHandler.teleportPlayer(player, selectedLocation, TeleportType.NORMAL);
                    return;
                }
                break;
            case 4155:
                if (player.getLocation() == Location.NECROMANCY_GAME_AREA)
                    return;

                if (player.getSlayer().getSlayerMaster() == SlayerMaster.CORRUPT_SLAYER){
                    player.msgRed("You can only travel to Corrupt Tasks with a Corrupt Skull!");
                    return;
                }
               /* if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK){
                    player.sendMessage("<shad=1><col=AF70C3>You don't have a task to teleport to..");
                    return;
                }*/

                player.getSlayer().handleSlayerRingTP(itemId);
                break;
            case 3500:
                if (player.getLocation() == Location.NECROMANCY_GAME_AREA)
                    return;
            /*    if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK){
                    player.sendMessage("<shad=1><col=AF70C3>You don't have a task to teleport to..");
                    return;
                }*/
                player.getSlayer().handleSlayerRingTP(itemId);
                break;
            case 2706:
                if (player.getLocation() == Location.JAIL) {
                    player.sendMessage("<shad=1>@cya@You can't start an instance while your in jail.");
                    return;
                }
                if (player.getLocation() != Location.HOME) {
                    player.sendMessage("<shad=1>@cya@You must be at home to start a new instance!.");
                    return;
                }
                if (player.currentInstanceAmount >= 1) {
                    player.sendMessage("<shad=1>@red@You can't start a new instance until this one ends");
                    return;
                }
                player.getInstanceManager().ticketID = 2706;
                new InstanceInterfaceHandler(player).open();
                break;
            case 2708:
                if (player.getLocation() == Location.JAIL) {
                    player.sendMessage("<shad=1>@cya@You can't start an instance while your in jail.");
                    return;
                }
                if (player.getLocation() != Location.HOME) {
                    player.sendMessage("<shad=1>@cya@You must be at home to start a new instance!.");
                    return;
                }
                if (player.currentInstanceAmount >= 1) {
                    player.sendMessage("<shad=1>@red@You can't start a new instance until this one ends");
                    return;
                }
                player.getInstanceManager().ticketID = 2708;
                new InstanceInterfaceHandler(player).open();
                break;
            case 23020:
                if (!player.getClickDelay().elapsed(250))
                    return;
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You can not do this right now.");
                    return;
                }
                if (!player.getInventory().contains(23020) || player.getInventory().getAmount(23020) < 1) {
                    return;
                }
                player.getClickDelay().reset();
                player.getInventory().delete(23020, 1);
                player.getDailyTaskInterface().MiscTasksCompleted(2, 1);
                player.msgFancyPurp("You are rewarded 1 Vote Point!");
                player.msgFancyPurp("Added 30 minutes of Voting Boost!");
                player.getVoteBoost().append();
                player.getPointsHandler().incrementVotingPoints(1);
                StarterTasks.doProgress(player, StarterTasks.StarterTask.VOTE_FOR_VANGUARD, 1);
                break;
            case 7304:
                if (player.busy()) {
                    player.getPacketSender().sendMessage("You can not do this right now.");
                    return;
                }
                if (!player.getInventory().contains(7304) || player.getInventory().getAmount(7304) < 1) {
                    return;
                }
                int amt2 = player.getInventory().getAmount(7304);
                player.getInventory().delete(7304, amt2);
                player.getPointsHandler().incrementMegaPoints(amt2);
                player.getClickDelay().reset();
                break;
            case 15084:
                Gambling.rollDice(player);
                break;
            case 15098:
                Gambling.ScamrollDice(player);
                break;
            case 299:
                Gambling.plantSeed(player);
                break;
            case 10258:
                if (!player.getClickDelay().elapsed(150))
                    break;
                player.getClickDelay().reset();
                CashBag.openbox(player);
                break;
            case 456:
                if (!player.getClickDelay().elapsed(150))
                    break;
                player.getClickDelay().reset();
                HuntersPurse.openbox(player);
                break;
            case 1454:
                if (!player.getClickDelay().elapsed(150))
                    break;
                player.getClickDelay().reset();
                FrozenCasket.openbox(player);
                break;
            case 730:
                if (!player.getClickDelay().elapsed(150))
                    break;
                player.getClickDelay().reset();
                FavorCasket.openbox(player);
                break;
            case 733:
                if (!player.getClickDelay().elapsed(150))
                    break;
                player.getClickDelay().reset();
                LampCasket.openbox(player);
                break;
        }
    }

    public static void thirdAction(Player player, Packet packet) {
        int interfaceId = packet.readLEShortA();
        int slot = packet.readLEShort();
        int itemId = packet.readShortA();

        if (slot < 0 || slot > player.getInventory().capacity()) {
            return;
        }

        if (player.getInventory().getItems()[slot].getId() != itemId) {
            return;
        }

        player.setInteractingItem(player.getInventory().getItems()[slot]);

        if (SummoningData.isPouch(player, itemId, 2)) {
            return;
        }

        switch (itemId) {
            case 731:
            case 5585:
            case 770:
            case 17129:
            case 2624:
            case 725:
            case 754:
            case 17819:
            case 2009:
            case 17130:
            case 23173:
            case 10256:
            case 10260:
            case 10262:
            case 3578:
            case 4022:
            case 15666:
            case 23171:
            case 23172:
            case 19118:
            case 15669:
            case 15668:
            case 15670:
            case 20104:
            case 20105:
            case 20106:
            case 20107:
            case 20108:
            case 20109:
            case 20111:
            case 20112:
            case 1302:
            case 1303:
            case 1304:
            case 1305:
            case 3512:

            case 791:
            case 792:

            case 460:
            case 461:
            case 462:

            case 2053:
            case 2054:
            case 2055:
            case 2062:
            case 2090:


                RewardViewer.handleBox(player,itemId);
                break;
            case 18681:
                player.getCompanion().displayInterface();
                break;
            case 4155:
            case 3500:
                if (player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER && player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER_X && player.getSlayer().getSlayerMaster() != SlayerMaster.SPECTRAL_BEAST) {
                    Position position15 = new Position(2718, 3113, 0);
                    TeleportHandler.teleportPlayer(player, position15, TeleportType.NORMAL);
                }
                if (player.getSlayer().getSlayerMaster()  == SlayerMaster.BEAST_MASTER ||  player.getSlayer().getSlayerMaster()  == SlayerMaster.BEAST_MASTER_X  ||  player.getSlayer().getSlayerMaster()  == SlayerMaster.SPECTRAL_BEAST) {
                    TeleportHandler.teleportPlayer(player, new Position(3167, 3530,0), TeleportType.ANCIENT);
                }
                break;


            case 18819:
                player.getSlayer().handleSlayerRingTP2(itemId);
                break;

            case 18015:

                new SelectionDialogue(player,"Empty Essence?",
                        new SelectionDialogue.Selection("Beast Essence", 0, p -> {
                            if (player.getBeastEssence() <=0){
                                player.getPacketSender().sendMessage("You don't have any Beast Essence in your Pouch!");
                                player.getPacketSender().sendInterfaceRemoval();
                                return;
                            }
                            int beastEssence = player.getBeastEssence();
                            if (player.getInventory().getFreeSlots() <= 1) {
                                player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                                return;
                            }
                            player.getInventory().add(6466, beastEssence);
                            player.getPacketSender().sendInterfaceRemoval();
                            player.setBeastEssence(0);

                        }),
                        new SelectionDialogue.Selection("Corrupt Essence", 1, p -> {
                            if (player.getCorruptEssence() <=0){
                                player.getPacketSender().sendMessage("You don't have any Corrupt Essence in your Pouch!");
                                player.getPacketSender().sendInterfaceRemoval();
                                return;
                            }
                            int corruptEssence = player.getCorruptEssence();
                            if (player.getInventory().getFreeSlots() <= 1) {
                                player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                                return;
                            }
                            player.getInventory().add(3502, corruptEssence);
                            player.getPacketSender().sendInterfaceRemoval();
                            player.setCorruptEssence(0);
                        }),
                        new SelectionDialogue.Selection("Enchanted Essence", 2, p -> {
                            if (player.getEnchantedEssence() <=0){
                                player.getPacketSender().sendMessage("You don't have any Enchanted Essence in your Pouch!");
                                player.getPacketSender().sendInterfaceRemoval();
                                return;
                            }
                            int enchantedEssence = player.getEnchantedEssence();
                            if (player.getInventory().getFreeSlots() <= 1) {
                                player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                                return;
                            }
                            player.getInventory().add(2699, enchantedEssence);
                            player.getPacketSender().sendInterfaceRemoval();
                            player.setEnchantedEssence(0);
                        }),
                        new SelectionDialogue.Selection("Spectral Essence", 3, p -> {
                            if (player.getSpectralEssence() <=0){
                                player.getPacketSender().sendMessage("You don't have any Spectral Essence in your Pouch!");
                                player.getPacketSender().sendInterfaceRemoval();
                                return;
                            }
                            int spectralEssence = player.getSpectralEssence();
                            if (player.getInventory().getFreeSlots() <= 1) {
                                player.getPacketSender().sendMessage("You do not have enough free inventory space to do this.");
                                return;
                            }
                            player.getInventory().add(2064, spectralEssence);
                            player.getPacketSender().sendInterfaceRemoval();
                            player.setSpectralEssence(0);
                        }),
                        new SelectionDialogue.Selection("Nevermind...", 4, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();

                break;
                //DialogueManager.start(player, 4000);
                //player.setDialogueActionId(4000);
        }
    }

    public void secondAction(Player player, Packet packet) {
        int itemId = packet.readShortA();
        int slot = packet.readLEShortA();
        int interfaceId = packet.readLEShortA();
        if (slot < 0 || slot > player.getInventory().capacity())
            return;
        if (player.getInventory().getItems()[slot].getId() != itemId)
            return;
        if (JarData.forJar(itemId) != null) {
            PuroPuro.lootJar(player, new Item(itemId, 1), JarData.forJar(itemId));
            return;
        }
        if (SummoningData.isPouch(player, itemId, 3)) {
            return;
        }

        if (ExpLamps.handleRubAll(player, itemId)){
            return;
        }



        switch (itemId) {

            case 22000:
            case 12460:
            case 22001:
            case 12462:
            case 22002:
            case 12464:
            case 16517:
            case 16518:
            case 16520:
            case 16508:
            case 16510:
            case 16511:
            case 16513:
            case 16514:
            case 12839:
            case 17003:
            case 12840:
            case 17005:
            case 12841:
            case 17007:
            case 12842:
            case 17009:
            case 7236:
            case 7238:
            case 7241:
            case 7245:
            case 7247:
            case 7249:
            case 6805:
            case 15700:
            case 6806:
            case 15703:
            case 6807:
            case 15705:
            case 15710:
            case 15713:
            case 15715:
            case 10067:
            case 15717:
            case 6808:
            case 15707:

            case 2705:

            case 1560:
            case 1561:
            case 1562:
            case 1563:
            case 1564:
            case 1565:
            case 1566:
            case 1567:
            case 1568:

            case 1570:
            case 1571:
            case 1572:
            case 1573:
            case 1574:
            case 1575:
            case 1576:
            case 1577:
            case 1578:

            case 1580:
            case 1581:
            case 1582:
            case 1583:
            case 1584:
            case 1585:
            case 1586:
            case 1587:
            case 1588:
            case 1300:

            case 1528:
            case 1529:
            case 1530:

            case 1531:
            case 1532:
            case 1533:

            case 17018:
            case 17019:
            case 17020:
            case 17021:
            case 17022:



            case 22003:
            case 12466:

            case 3013:
            case 3014:
            case 3015:
                player.getTierUpgrading().display();
                break;
            case 401:
            case 402:
            case 403:
            case 404:
            case 405:
            case 406:
            case 407:
            case 408:
            case 409:
            case 410:
            case 411:
            case 412:
            case 413:
            case 414:
            case 415:
            case 416:
            case 417:
            case 418:
                player.getTierUpgrading().display();
                break;
            case 10258:
                CashBag.openAll(player, player.getInventory().getAmount(10258));
                break;
            case 456:
                HuntersPurse.openAll(player, player.getInventory().getAmount(456));
                break;
            case 1306:
                SlayerCasket.openAll(player, player.getInventory().getAmount(1306));
                break;
            case 300:
                BeastCasket.openAll(player, player.getInventory().getAmount(300));
                break;
            case 301:
                BeastCasketU.openAll(player, player.getInventory().getAmount(301));
                break;
            case 1307:
                SlayerCasketU.openAll(player, player.getInventory().getAmount(1307));
                break;
            case 7053:
                player.sendMessage("Your lantern has collected a total of " + player.getSouls() + " souls.");
                break;
            case 1454:
                FrozenCasket.openAll(player, player.getInventory().getAmount(1454));
                break;
            case 730:
                FavorCasket.openAll(player, player.getInventory().getAmount(730));
                break;
            case 733:
                LampCasket.openAll(player, player.getInventory().getAmount(733));
                break;
            case 15667:
                player.getCasketOpening().setCurrentCasket(CasketOpening.Caskets.BOX_OF_PLUNDERS);
                player.getCasketOpening().openAll(player.getInventory().getAmount(15667));
                break;


            case 23020:
                int voteScrollamt = player.getInventory().getAmount(23020);
                new SelectionDialogue(player,"Claim Votes?", new SelectionDialogue.Selection("Claim All", 0, p -> {
                    if (voteScrollamt < 1) {
                        return;
                    }
                    player.getClickDelay().reset();
                    player.getInventory().delete(23020, voteScrollamt);
                    player.getDailyTaskInterface().MiscTasksCompleted(23020, voteScrollamt);
                    player.msgFancyPurp("You are rewarded " + voteScrollamt + " Vote Point(s)!");
                    player.getVoteBoost().addTime(1800 * voteScrollamt);
                    player.getPointsHandler().incrementVotingPoints(voteScrollamt);
                    player.getPacketSender().sendChatboxInterfaceRemoval();
                    StarterTasks.doProgress(player, StarterTasks.StarterTask.VOTE_FOR_VANGUARD, voteScrollamt);

                }),
             new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
                break;


            //HANDLE BONDS
            case 10945:
            case 10946:
            case 23057:
            case 23058:
            case 23059:
            case 23060:
              /*  if (player.getRights() == PlayerRights.YOUTUBER){
                    player.getPacketSender().sendMessage("<shad=0><col=AF70C3>You cannot claim bonds with a Youtuber Rank!");
                    return;
                }*/
                BondHandler.checkForRankUpdate(player);
                if (BondHandler.confirmationDialogueAll(player, itemId)){
                    return;
                }
                break;

            case 534:
            case 7305:
            case 7306:
            case 7307:
            case 7308:
            case 7309:

                int bones_needed = 25_000;
                if (player.getInventory().getAmount(itemId) < 10000) {
                    player.msgRed("Crush at least 10k bones to create an offering!");
                    return;
                }
                    player.getInventory().add(19065, 1);
                    player.getInventory().delete(itemId, 10000);
                break;

            case 18015:
                DialogueManager.start(player, 4000);
                player.setDialogueActionId(4000);
                break;
            case 19622:
                DialogueManager.start(player, 6095);
                player.setDialogueActionId(6095);
                break;
            case 1308:
                DialogueManager.start(player, 6097);
                player.setDialogueActionId(6097);
                break;
            case 18681:
                player.getCompanion().reFollow();
                break;
            case 4278:
            case 2708:
            case 2706:
                if (player.getLocation() == Location.JAIL) {
                    player.sendMessage("<shad=1>@cya@You can't start an instance while your in jail.");
                    return;
                }
                if (player.getLocation() != Location.HOME) {
                    player.sendMessage("<shad=1>@cya@You must be at home to start a new instance!.");
                    return;
                }
                if (player.currentInstanceAmount >= 1) {
                    player.sendMessage("<shad=1>@red@You can't start a new instance until this one ends");
                    return;
                }

                if (player.lastInstanceNpc == -1) {
                    player.sendMessage("<shad=1>@red@You need a valid last instance before doing this!");
                    return;
                }
                if (!player.getLastRunRecovery().elapsed(5000)) {
                    player.sendMessage("Instance on cooldown...");
                    return;
                }

                boolean validNpc = false;
                for (InstanceData data : InstanceData.values()) {
                    if (data.getNpcId() == 1194){
                        if (player.getInventory().getAmount(715) < 50) {
                            player.msgRed("You need 50 Easter Fragments to Start a Rabbit Instance!");
                            return;
                        }
                    }
                    if (data.getNpcId() == player.lastInstanceNpc) {
                        validNpc = true;
                        break;
                    }
                }


                if (!validNpc) {
                    player.sendMessage("<shad=1>@red@You need a valid last instance before doing this!");
                    return;
                }
                for (InstanceData data : InstanceData.values()) {
                    if (data.getNpcId() == 1194){
                        player.getInventory().delete(715,50);
                    }
                }

                    player.getInstanceManager().ticketID = itemId;
                    player.getInstanceManager().createInstance(player.lastInstanceNpc, RegionInstance.RegionInstanceType.INSTANCE);
                    break;
            case 4178:
                player.getInventory().delete(4178, 1).add(22092, 1);
                player.getPacketSender().sendMessage("You have Transformed your Weapon");
                break;
            case 22092:
                player.getInventory().delete(22092, 1).add(12283, 1);
                player.getPacketSender().sendMessage("You have Transformed your Weapon");
                break;
            case 12283:
                player.getInventory().delete(12283, 1).add(4178, 1);
                player.getPacketSender().sendMessage("You have Transformed your Weapon");
                break;
            //TRANSFORMATIONSBITCHH
            case 4155:
                if (player.getSlayer().getDuoPartner() != null) {
                    Slayer.resetDuo(player, World.getPlayerByName(player.getSlayer().getDuoPartner()));
                } else {
                    player.sendMessage("<img=12> You do not have a duo partner! <img=12>");
                }
                break;
        }
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;

        switch (packet.getOpcode()) {
            case THIRD_ITEM_ACTION_OPCODE:
                thirdAction(player, packet);
                break;
            case FIRST_ITEM_ACTION_OPCODE:
                firstAction(player, packet);
                break;
            case SECOND_ITEM_ACTION_OPCODE:
                secondAction(player, packet);
                break;
        }
    }

}
