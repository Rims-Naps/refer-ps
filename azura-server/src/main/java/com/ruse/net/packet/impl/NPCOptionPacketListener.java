package com.ruse.net.packet.impl;

import com.ruse.GameSettings;
import com.ruse.engine.task.impl.WalkToTask;
import com.ruse.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Shop;
import com.ruse.model.container.impl.Shop.ShopManager;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.BlueEgg;
import com.ruse.world.content.DailyBosses.DailyBossHandler;
import com.ruse.world.content.Easter.EasterQuest;
import com.ruse.world.content.RedEgg;
import com.ruse.world.content.ZoneProgression.ModeSwapper;
import com.ruse.world.content.ZoneProgression.NpcRequirements;
import com.ruse.world.content.combat.CombatFactory;
import com.ruse.world.content.combat.magic.CombatSpell;
import com.ruse.world.content.combat.magic.CombatSpells;
import com.ruse.world.content.combat.weapon.CombatSpecial;
import com.ruse.world.content.dailytasks_new.DailyTasks;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.groupironman.GroupConfig;
import com.ruse.world.content.groupironman.GroupManager;
import com.ruse.world.content.pos.PlayerOwnedShopManager;
import com.ruse.world.content.quests.impl.Christmas2023;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.ArcanaOrb;
import com.ruse.world.content.skill.impl.construction.ConstructionActions;
import com.ruse.world.content.skill.impl.fishing.Fishing;
import com.ruse.world.content.skill.impl.old_dungeoneering.UltimateIronmanHandler;
import com.ruse.world.content.skill.impl.slayer.SlayerDialogues;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.skill.impl.summoning.Summoning;
import com.ruse.world.content.skill.impl.summoning.SummoningData;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.ruse.world.content.combat.CombatType.RANGED;

public class NPCOptionPacketListener implements PacketListener {

    private static void firstClick(Player player, Packet packet) {
        int index = packet.readLEShort();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;
        player.setEntityInteraction(npc);
        if (player.getRights() == PlayerRights.DEVELOPER)
            player.getPacketSender().sendMessage("First click npc id: " + npc.getId());
        if (BossPets.pickup(player, npc)) {
            player.getMovementQueue().reset();
            return;
        }

        switch (npc.getId()) {
            case 1716:
                if (!player.isInActiveInstance()){
                    player.setPowerUpSpawned(false);
                    player.sendMessage("@gre@<shad=0>POOF!" );
                    player.getPA().sendEntityHintRemoval(true);
                    World.deregister(npc);
                    player.getMovementQueue().reset();
                    player.setEntityInteraction(null);
                    return;
                }
                player.setPowerUpSpawned(false);
                player.sendMessage("@gre@<shad=0>You activated @gre@<shad=0>" + npc.getDefinition().getName() + "!"  );
                player.getPA().sendEntityHintRemoval(true);
                World.deregister(npc);
                player.getGrace().addTime(60);
                player.getMovementQueue().reset();
                player.setEntityInteraction(null);
                break;
            case 1717:
                if (!player.isInActiveInstance()){
                    player.setPowerUpSpawned(false);
                    player.sendMessage("@gre@<shad=0>POOF!" );
                    player.getPA().sendEntityHintRemoval(true);
                    World.deregister(npc);
                    player.getMovementQueue().reset();
                    player.setEntityInteraction(null);
                    return;
                }
                player.setPowerUpSpawned(false);
                player.sendMessage("@gre@<shad=0>You activated @gre@<shad=0>" + npc.getDefinition().getName() + "!"  );
                player.getPacketSender().sendEntityHintRemoval(true);
                World.deregister(npc);
                player.getShockwave().addTime(60);
                player.getMovementQueue().reset();
                player.setEntityInteraction(null);
                break;
            case 1718:
                if (!player.isInActiveInstance()){
                    player.setPowerUpSpawned(false);
                    player.sendMessage("@gre@<shad=0>POOF!" );
                    player.getPA().sendEntityHintRemoval(true);
                    World.deregister(npc);
                    player.getMovementQueue().reset();
                    player.setEntityInteraction(null);
                    return;
                }
                player.setPowerUpSpawned(false);
                player.sendMessage("@gre@<shad=0>You activated @gre@<shad=0>" + npc.getDefinition().getName() + "!"  );
                player.getPA().sendEntityHintRemoval(true);
                World.deregister(npc);
                player.getBlitz().addTime(60);
                player.getMovementQueue().reset();
                player.setEntityInteraction(null);
                break;
            case 1719:
                if (!player.isInActiveInstance()){
                    player.setPowerUpSpawned(false);
                    player.sendMessage("@gre@<shad=0>POOF!" );
                    player.getPA().sendEntityHintRemoval(true);
                    World.deregister(npc);
                    player.getMovementQueue().reset();
                    player.setEntityInteraction(null);
                    return;
                }
                player.setPowerUpSpawned(false);
                player.sendMessage("@gre@<shad=0>You activated @gre@<shad=0>" + npc.getDefinition().getName() + "!"  );
                player.getPA().sendEntityHintRemoval(true);
                World.deregister(npc);
                player.getLuck().addTime(60);
                player.getMovementQueue().reset();
                player.setEntityInteraction(null);
                break;
        }


            player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (!player.getControllerManager().processNPCClick1(npc)) {
                    return;
                }

                if (ConstructionActions.handleFirstClickNpc(player, npc)) {
                    return;
                }
                int Stage = player.getEasterQuestStage();

                switch (npc.getId()) {
                    case 100:
                        if (player.getMarinaTaskID() == 7)
                        MarinasTasks.handleTasks(player);
                        break;
                    case 409:
                        if (player.getRabidBunniesKilled() >= 25) {
                            player.msgRed("You've already completed this part of the quest!");
                            player.getCombatBuilder().reset(true);
                            return;
                        }
                        break;

                    case 6313:
                        ArcanaOrb.handleOrbClick(6313, player);
                        break;

                    case 8521:
                        if (player.fixedFerry == false) {
                            DialogueManager.sendStatement(player, "Please help me fix my ferry south of home...Hades is waiting for me!");
                        }

                        if (player.fixedFerry == true && player.claimedLantern == false) {
                            DialogueManager.sendStatement(player, "Thank you! I can now deliver these souls to hades, take my lantern as a gift!");
                        player.getInventory().add(7053,1);
                        player.setClaimedLantern(true);
                        }
                        break;

                    case 9028:
                        player.sendMessage("" + Stage);

                        switch (Stage){
                            case 0:
                                EasterQuest.stage0(player);
                                break;
                            case 1:
                                EasterQuest.stage1Requirements(player);
                                break;
                            case 2:
                                EasterQuest.stage2Requirements(player);
                                break;
                            case 3:
                                EasterQuest.stage3Requirements(player);
                                break;
                            case 4:
                                EasterQuest.stage4Requirements(player);
                                break;
                            case 5:
                                EasterQuest.stage5Requirements(player);
                                break;
                            case 6:
                                EasterQuest.completion(player);
                                break;
                            case 7:
                                npc.forceChat("Happy Easter!");
                                break;
                        }
                        break;

                    case 1597:
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEGINNER_SLAYER)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEGINNER_SLAYER);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEGINNER_SLAYER)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEGINNER_SLAYER);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if (yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if (thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;

                    case 8275:
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.MEDIUM_SLAYER)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.MEDIUM_SLAYER);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.MEDIUM_SLAYER)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.MEDIUM_SLAYER);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;
                    case 9085:
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.ELITE_SLAYER)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.ELITE_SLAYER);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.ELITE_SLAYER)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.ELITE_SLAYER);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;
                    case 300:
                        if (!player.getInventory().contains(3500)){
                            player.msgRed("Come back when you've obtained a Corrupt Slayer Skull!");
                            return;
                        }
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.CORRUPT_SLAYER)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.CORRUPT_SLAYER);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.CORRUPT_SLAYER)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.CORRUPT_SLAYER);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;
                    case 2130:
                        if (!player.isUnlockedSpectralZones()){
                            player.msgRed("You must unlock Challenger Zones to access this Content.");
                            return;
                        }
                        if (!player.getInventory().contains(3500)){
                            player.msgRed("Come back when you've obtained a Corrupt Slayer Skull!");
                            return;
                        }
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_SLAYER)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.SPECTRAL_SLAYER);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_SLAYER)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.SPECTRAL_SLAYER);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;

                    case 6314:
                        BlueEgg.handleChestClick(6314, player);
                        World.deregister(npc);
                        if (player.getEasterQuestStage() == 5){
                            if (!player.foundFinalEgg){
                                player.getInventory().add(12637,1);
                                player.setFoundFinalEgg(true);
                                player.msgFancyPurp("You found the Easter Bunnies missing Egg!");
                            }
                        }
                        break;

                    case 6307:
                        RedEgg.handleChestClick(6307, player);
                        World.deregister(npc);
                        if (player.getEasterQuestStage() == 5){
                            if (!player.foundFinalEgg){
                                player.getInventory().add(12637,1);
                                player.setFoundFinalEgg(true);
                                player.msgFancyPurp("You found the Easter Bunnies missing Egg!");
                            }
                        }
                        break;
                    case 554:
                        break;
                    case 13628:
                    case 13629:
                    case 13630:
                        player.getIsland().clickPortal(npc.getId());
                    break;
                    case 13631:
                        player.getIsland().clickChest(npc);
                    break;
                    case 1853:
                    case 1854:
                    case 1855:
                        //player.getTowerParty().getOwner().getTowerRaid().openChest(player, npc.getPosition(), npc);
                    break;
                    case 1060:
                        Christmas2023.handleSanta(player);
                    break;
                    case 786:
                        ShopManager.getShops().get(Shop.REFUND_SHOP).open(player);
                        player.sendMessage("<col=0><shad=6C1894>You have @red@" + player.getPointsHandler().getRefundPoints() + " <col=0><shad=6C1894>Refund Points.");
                        break;
                    case 231:
                        ShopManager.getShops().get(Shop.WOOCUTTING_SHOP).open(player);
                        break;
                    case 230:
                        ShopManager.getShops().get(Shop.MINE_SHOP).open(player);
                        break;
                    case 2209:
                        ShopManager.getShops().get(Shop.MINING_SHOP).open(player);
                        break;
                    case 2262:
                        ShopManager.getShops().get(Shop.NECRO_SHOP).open(player);
                        break;
                    case 210:
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + player.getPointsHandler().getVotingPoints() + " <col=0><shad=6C1894>Vote points. Do ::vote to earn more!");
                        ShopManager.getShops().get(Shop.VOTE_STORE).open(player);
                        break;
                    case 305:
                        ShopManager.getShops().get(31).open(player);
                        break;
                    case 306:
                        ShopManager.getShops().get(30).open(player);
                        break;
                    case 931:
                        ShopManager.getShops().get(Shop.ENCHANTED_SHOP).open(player);
                        break;
                    case 211:
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + player.getPointsHandler().getDonatorPoints() + " <col=0><shad=6C1894>Donator Points!");
                        ShopManager.getShops().get(80).open(player);
                        break;
                    case 212:
                        ShopManager.getShops().get(30).open(player);
                        break;
               /*     case 212:
                        ShopManager.getShops().get(Shop.STREAM_SHOP).open(player);
                        break;*/
                    case 102:
                        ShopManager.getShops().get(Shop.TRASH_STORE).open(player);
                        break;
                    case 214:
                        int monsterEssence = player.getMonsteressence() + player.getInventory().getAmount(19062);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + monsterEssence + " <col=0><shad=6C1894>Monster Essence!");
                        ShopManager.getShops().get(Shop.MONSTER_ESSENCE_SHOP).open(player);
                        break;
                    case 700:
                        ShopManager.getShops().get(Shop.CHRISTMAS_SHOP).open(player);
                        break;
                    case 568:
                        player.getCosmeticShop().openInterface(CosmeticShop.CosmeticShopTier.ORDINARY);
                       // ShopManager.getShops().get(Shop.COSMETIC_SHOP).open(player);
                        break;
                    case 3378:
                        ShopManager.getShops().get(Shop.IRONMAN_SHOP).open(player);
                        break;
                    case 216:
                        player.getUpgrade().display();
                        //ShopManager.getShops().get(Shop.SHOP_3).open(player);
                        break;
                    case 248:
                        player.getPacketSender().sendString(1, GameSettings.WikiUrl);
                        player.getPacketSender().sendMessage("Opening our Wiki");
                        break;
                    case 249:
                        if (player.getInventory().contains(777) && (player.getInventory().contains(778) && (player.getInventory().contains(779)))) {
                            player.getInventory().delete(777,99999);
                            player.getInventory().delete(778,99999);
                            player.getInventory().delete(779,99999);
                            player.setCompletedBeginner(true);
                            DialogueManager.sendStatement(player, "You are now free to travel ::home.");
                            player.msgRed("You are now free to travel ::home");
                            return;
                        }
                        if (!player.completedBeginner) {
                            DialogueManager.sendStatement(player, "Grab some gear from the crate, return with all 3 Elemental shards.");
                        }
                        break;
                        

                  /*  case 249:
                        if (player.startedTutorial && player.completedtutorial){
                            player.sendMessage("finished tutorial message");
                            return;
                        }


                        if (player.getVgTutStage() == 2){
                            player.sendMessage("Head North West to the Salt Mines");
                            return;
                        }
                        if (player.getVgTutStage() == 3){
                            player.sendMessage("Head to the Church and speak to Father");
                            return;
                        }
                        if (player.getVgTutStage() == 4){
                            player.sendMessage("Speak to the Miner");
                            return;
                        }
                        if (player.getVgTutStage() == 5){
                            player.sendMessage("Speak to the Alchemist");
                            return;
                        }
                        if (player.getVgTutStage() == 6){
                            player.sendMessage("Speak to the Beast Master");
                            return;
                        }
                        if (player.getVgTutStage() == 7){
                            player.sendMessage("You're ready to take on the Mighty Beast...");
                            return;
                        }
                        if (player.getVgTutStage() == 8){
                            DialogueManager.start(player, 5055);
                            player.setDialogueActionId(5058);
                            return;
                        }

                        if (player.getVgTutStage() == 9){
                            DialogueManager.start(player, 5059);
                            player.setDialogueActionId(5060);
                            return;
                        }
                        if (player.startedTutorial){
                            player.sendMessage("Started Tut already");
                            return;
                        }

                        DialogueManager.start(player, 5000);
                        player.setDialogueActionId(5006);
                        break;

                    case 2899:
                        if (!player.startedTutorial){
                            player.sendMessage("Speak to Wizard Drendor first!");
                            return;
                        }

                        if (player.getVgTutStage() == 6){
                            player.sendMessage("Speak to the Beast Master in the North to continue your quest...");
                            return;
                        }
                        if (player.getVgTutStage() == 7){
                            player.sendMessage("You're ready to take on the Mighty Beast...");
                            return;
                        }

                        if (player.completedtutorial){
                            player.sendMessage("finished tutorial message");
                            return;
                        }

                        if (!player.tutTask1Ready){
                            player.sendMessage("Head to the Dungeon North west to continue");
                            return;
                        }

                        if(player.tutTask2Started && !player.tutTask2Ready){
                            player.sendMessage("Sacrifice the urn on the altar");
                            return;
                        }

                        if (player.tutTask2Ready && !player.receivedprayerunlock){
                            player.getInventory().delete(20426, 28);
                            player.setTutTask2Complete(true);
                            DialogueManager.start(player, 5018);
                            player.setDialogueActionId(5019);
                            return;
                        }

                        if (player.receivedprayerunlock && player.getVgTutStage() == 4){
                            player.sendMessage("You should go and speak to the Miner to the North West.");
                            return;
                        }
                        if (player.getVgTutStage() == 5){
                            player.sendMessage("You should go and speak to the Alchemist!");
                            return;
                        }


                        if (player.isTutTask1Ready() && !player.tutTask2Ready)
                        DialogueManager.start(player, 5007);
                        player.setDialogueActionId(5016);
                        break;

                    case 1396:
                        if (!player.startedTutorial){
                            player.sendMessage("Speak to Wizard Drendor first!");
                            return;
                        }

                        if (player.getVgTutStage() == 6){
                            player.sendMessage("Speak to the Beast Master in the North to continue your quest...");
                            return;
                        }
                        if (player.getVgTutStage() == 7){
                            player.sendMessage("You're ready to take on the Mighty Beast...");
                            return;
                        }
                        if (!player.tutTask1Complete){
                            player.sendMessage("He doesn't seem interested in speaking to you...");
                            return;
                        }
                        if (!player.tutTask2Complete){
                            player.sendMessage("He doesn't seem interested in speaking to you...");
                            return;
                        }
                        if (!player.receivedprayerunlock){
                            player.sendMessage("He doesn't seem interested in speaking to you...");
                            return;
                        }
                        if (player.tutTask3Complete){
                            player.sendMessage("Speak to the Alchemist to continue your quest");
                            return;
                        }
                        if (!player.tutTask3Started && player.receivedprayerunlock){
                            DialogueManager.start(player, 5024);
                            player.setDialogueActionId(5031);
                            return;
                        }
                        if (player.tutTask3Started && !player.tutTask3Complete) {
                            if (player.getInventory().contains(23119, 4999) && player.getInventory().contains(23121, 4999) && player.getInventory().contains(23122, 4999)) {
                                player.setTutTask3Ready(true);
                                player.setTutTask3Complete(true);
                               // player.getInventory().delete(23119, 100);
                               // player.getInventory().delete(23121, 100);
                               // player.getInventory().delete(23122, 100);
                                DialogueManager.start(player, 5033);
                                player.getPA().sendEntityHintRemoval(true);
                                Position tutorialstart = new Position(2380, 3397, 0);
                                player.getPacketSender().sendPositionalHint(tutorialstart, 3);
                                player.setVgTutStage(5);
                                player.getPacketSender().sendString(32753, "Alchemical Power: Stage 5");//STAGE TITLE
                                player.getPacketSender().sendString(32752, "Speak to the Alchemist");//STAGE HINT
                                player.performGraphic(new Graphic(190));
                                Position position45 = new Position(2379, 3390, 0);
                                player.moveTo(position45);
                                return;
                            }
                            player.sendMessage("Bring the Miner 5000 of each Salt to continue..");

                        }

                        break;

                    case 8459:
                        if (!player.startedTutorial){
                            player.sendMessage("Speak to Wizard Drendor first!");
                            return;
                        }
                        if (player.getVgTutStage() == 6){
                            player.sendMessage("Speak to the Beast Master in the North to continue your quest...");
                            return;
                        }
                        if (player.getVgTutStage() == 7){
                            player.sendMessage("You're ready to take on the Mighty Beast...");
                            return;
                        }

                        if (!player.tutTask1Complete){
                            player.sendMessage("The Alchemist doesn't seem interested in speaking to you...");
                            return;
                        }
                        if (!player.tutTask2Complete){
                            player.sendMessage("The Alchemist doesn't seem interested in speaking to you...");
                            return;
                        }
                        if (!player.receivedprayerunlock){
                            player.sendMessage("The Alchemist doesn't seem interested in speaking to you...");
                            return;
                        }
                        if (!player.tutTask3Complete){
                            player.sendMessage("The Alchemist doesn't seem interested in speaking to you...");
                            return;
                        }
                        if (player.tutTask4Started){
                            if (!player.madeDropratePotion){
                                player.sendMessage("Make sure to craft a Droprate Potion...");
                                return;
                            }
                            if (!player.madeCritPotion){
                                player.sendMessage("Make sure to craft a Critical Potion...");
                                return;
                            }
                            if (!player.madeDamagePotion){
                                player.sendMessage("Make sure to craft a Damage Potion...");
                                return;
                            }
                        }

                        if (!player.tutTask4Complete && player.madeCritPotion && player.madeDamagePotion && player.madeDropratePotion && player.tutTask4Ready){
                            player.getPA().sendEntityHintRemoval(true);
                            player.setTutTask4Complete(true);
                            DialogueManager.start(player, 5044);
                            player.setVgTutStage(6);
                            Position beast_master = new Position(2388,3437,0);
                            player.getPacketSender().sendPositionalHint(beast_master, 3);
                            return;
                        }

                        if (!player.tutTask4Started) {
                            DialogueManager.start(player, 5034);
                            player.setDialogueActionId(5041);
                        }

                        break;

                    case 217:
                        if (!player.startedTutorial){
                            player.sendMessage("Speak to Wizard Drendor first!");
                            return;
                        }
                        if (player.getVgTutStage() == 7){
                            player.sendMessage("You're ready to take on the Mighty Beast...");
                            return;
                        }
                        if (player.getVgTutStage() != 6){
                            player.sendMessage("The Beast Master doesn't seem interested in speaking to you...");
                            return;
                        }

                        if (player.getVgTutStage() == 6) {
                            DialogueManager.start(player, 5046);
                            player.setDialogueActionId(5049);
                            return;
                        }
                        break;*/

                    case 4885:
                        npc.forceChat("Please Help me!");
                        break;
                    case 5040:
                        World.deregister(npc);
                        break;



                    //TUTORIAL START




                    //EVERTHORN PORTALS
                    case 7347:
                        if (player.isEverthornattackrunning()) {
                            player.getCombatBuilder().reset(true);
                            player.moveTo(2895, 4400, player.getPosition().getZ());
                        }
                        break;
                    case 7365:
                        if (player.isEverthornattackrunning()) {
                            player.getCombatBuilder().reset(true);
                            player.moveTo(2931, 4402, player.getPosition().getZ());
                        }
                        break;
                    case 7359:
                        if (player.isEverthornattackrunning()) {
                            player.getCombatBuilder().reset(true);
                            player.moveTo(2925, 4359, player.getPosition().getZ());
                        }
                        break;
                    case 6804:
                        if (player.isEverthornattackrunning()) {
                            player.getCombatBuilder().reset(true);
                            player.moveTo(2887, 4362, player.getPosition().getZ());
                        }
                        break;


                    case 215:
                        if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 100) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 100 Slayer!");
                            player.sendMessage("Come back when you've reached 100 Slayer!");
                            return;
                        }
                        int beastEssence = player.getBeastEssence() + player.getInventory().getAmount(6466);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + beastEssence + " <col=0><shad=6C1894>Beast Essence!");
                        ShopManager.getShops().get(Shop.BEAST_HUNTER).open(player);
                        break;

                    case 301:
                        if (player.getSkillManager().getCurrentLevel(Skill.BEAST_HUNTER) < 75) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 75 Beast Hunter!");
                            player.sendMessage("Come back when you've reached 75 Beast Hunter!");
                            return;
                        }
                        int beastEssence2 = player.getBeastEssence() + player.getInventory().getAmount(6466);
                        int corruptEssence2 = player.getCorruptEssence() + player.getInventory().getAmount(3502);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + beastEssence2 + " <col=0><shad=6C1894>Beast Essence!");
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + corruptEssence2 + " <col=0><shad=6C1894>Corrupt Essence!");
                        ShopManager.getShops().get(Shop.BEAST_HUNTER).open(player);
                        break;
                    case 455:
                        if (player.getSkillManager().getCurrentLevel(Skill.BEAST_HUNTER) < 75) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 75 Beast Hunter!");
                            player.sendMessage("Come back when you've reached 75 Beast Hunter!");
                            return;
                        }
                        int beastEssence23 = player.getBeastEssence() + player.getInventory().getAmount(6466);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + beastEssence23 + " <col=0><shad=6C1894>Beast Essence!");
                        ShopManager.getShops().get(Shop.BEAST_HUNTER).open(player);
                        break;
                    case 1199:
                        ShopManager.getShops().get(Shop.AFK_SHOP).open(player);
                        break;

                    case 104:
                        if (player.talkedtoghost && player.getInventory().contains(7254)){
                            DialogueManager.start(player, 3017);
                            player.setDialogueActionId(3018);
                            return;
                        }
                        if (player.isStartedSandQuest() && !player.talkedtoghost && !player.isSandStage2()){
                            player.setDialogueActionId(3015);
                            DialogueManager.start(player, 3009);
                            return;
                        }
                        break;
                    case 18:
                            player.sendMessage("He doesn't seem too interested in speaking to you...");
                        break;
                    case 2397:
                        if (!player.talkedtosuleimghost && player.movedaftermagic){
                            DialogueManager.start(player, 3040);
                            player.setDialogueActionId(3043);
                            return;
                        }
                        player.sendMessage("He doesn't seem too interested in speaking to you...");
                        break;



                    case 710:
                    case 711:
                    case 712:
                    case 720:
                    case 883:
                    case 884:
                    case 891:
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
                            player.sendMessage("Make a party before fighting the Boss!");
                            return;
                        }
                        if (player.getMinigameAttributes().getRaidsAttributes().getParty().getOwner() != player) {
                            player.sendMessage("Only the leader can initiated the Boss Fight");
                            return;
                        }
                        DialogueManager.start(player, 155);
                        player.setDialogueActionId(155);
                        break;




                    case 3373:
                        DialogueManager.start(player, 8005);
                        player.setDialogueActionId(8005);
                        break;


                    case 9022:
                        ServerPerks.getInstance().open(player);
                        break;
                    case GroupConfig.NPC_ID:
                        if (player.getGameMode() == GameMode.GROUP_IRON) {
                            if (GroupManager.isInGroup(player)) {
                                GroupManager.openInterface(player);
                            } else {
                                DialogueManager.start(player, 8001);
                                player.setDialogueActionId(8001);
                            }
                        } else {
                            player.msgRed("You must be a group ironman to do this.");
                        }
                        break;

                    case 4601:
                        player.setDialogueActionId(8);
                        DialogueManager.start(player, 13);
                        break;
                    case 4651:
                        if (player.getGameMode() != GameMode.NORMAL) {
                           player.sendMessage("@red@You cannot access the POS as an Ironman!");
                            return;
                        }
                            player.getPlayerShops().openTradingPost();
                        break;
                    case 22:
                        if (player.getEnchantedTasksDone() < 10) {
                            DialogueManager.sendStatement(player, "Come back when you've become one with the Forest. (10 Tasks)");
                            player.msgPurp("You've Done " + player.getEnchantedTasksDone() + " Forest Tasks!");
                            return;
                        }
                        if (player.getCompanion().getMyCompanion() != null) {
                            player.msgRed("You already have a Companion!");
                            player.getPacketSender().sendChatboxInterfaceRemoval();
                            return;
                        }
                        new SelectionDialogue(player, "Choose a Companion",
                                new SelectionDialogue.Selection("Choose Fairy", 0, p -> {
                                    player.getCompanion().receiveCompanion(1);
                                    player.giveItem(18681, 1);
                                    player.msgPurp("You've Chosen a Fairy Companion!");
                                    player.msgPurp("Be sure to keep it with you at all times!");
                                    p.getPacketSender().sendChatboxInterfaceRemoval();

                                }),
                                new SelectionDialogue.Selection("Choose Imp", 1, p -> {
                                    player.getCompanion().receiveCompanion(2);
                                    player.giveItem(18681, 1);
                                    player.msgPurp("You've Chosen an Imp Companion!");
                                    player.msgPurp("Be sure to keep it with you at all times!");
                                    p.getPacketSender().sendChatboxInterfaceRemoval();

                                }),
                                new SelectionDialogue.Selection("Choose Bird", 2, p -> {
                                    player.getCompanion().receiveCompanion(3);
                                    player.giveItem(18681, 1);
                                    player.msgPurp("You've Chosen a Bird Companion!");
                                    player.msgPurp("Be sure to keep it with you at all times!");
                                    p.getPacketSender().sendChatboxInterfaceRemoval();
                                }),
                                new SelectionDialogue.Selection("Nevermind...", 3, p -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
                        break;

                    case 6807:
                    case 6994:
                    case 6995:
                    case 6867:
                    case 6868:
                    case 6794:
                    case 6795:
                    case 6815:
                    case 6816:
                    case 6874:
                    case 6873:
                    case 3594:
                    case 3590:
                    case 3596:
                        if (player.getSummoning().getFamiliar() == null
                                || player.getSummoning().getFamiliar().getSummonNpc() == null
                                || player.getSummoning().getFamiliar().getSummonNpc().getIndex() != npc.getIndex()) {
                            player.getPacketSender().sendMessage("That is not your familiar.");
                            return;
                        }
                        player.getSummoning().store();
                        break;
                    case 6970:
                        player.setDialogueActionId(3);
                        DialogueManager.start(player, 3);
                        break;
                    case 2676:
                        player.getPacketSender().sendInterface(3559);
                        player.getAppearance().setCanChangeAppearance(true);
                        break;
                    case 494:
                    case 1360:
                        if (player.getGameMode() == GameMode.GROUP_IRON
                                && player.getIronmanGroup() != null) {
                            DialogueManager.start(player, 8002);
                            player.setDialogueActionId(8002);
                        } else {
                            player.getBank(player.getCurrentBankTab()).open();
                        }
                        break;
                }
                if (!(npc.getId() >= 8705 && npc.getId() <= 8710) && npc.getId() != 5040
                        && npc.getId() != 1853 && npc.getId() != 1854 && npc.getId() != 1855) {
                    npc.setPositionToFace(player.getPosition());
                }
                player.setPositionToFace(npc.getPosition());
            }
        }));
    }

    private static void attackNPC(Player player, Packet packet) {
        int index = packet.readShortA();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        NPC interact = World.getNpcs().get(index);
        if (interact == null)
            return;

        if (!NpcDefinition.getDefinitions()[interact.getId()].isAttackable()) {
            return;
        }

        if (interact.getConstitution() <= 0 && !interact.isDying()){
            interact.setConstitution(interact.getDefinition().getHitpoints());
        }

        if (interact.getConstitution() <= 0) {
            player.getMovementQueue().reset();
            return;
        }

        if (player.getEquipment().contains(22006) && player.getLastCombatType() == RANGED) {
            if (CombatFactory.npcsDeathDartDontWork(interact)) {
                player.getMovementQueue().reset();
                return;
            }
        }
        if (player.getCombatBuilder().getStrategy() == null) {
            player.getCombatBuilder().determineStrategy();
        }

        if (CombatFactory.checkAttackDistance(player, interact)) {
            player.getMovementQueue().reset();
        }
        if (UltimateIronmanHandler.hasItemsStored(player) && player.getLocation() != Location.DUNGEONEERING) {
            player.getPacketSender().sendMessage("You must claim your stored items at Dungeoneering first.");
            player.getMovementQueue().reset();
            return;
        }

        if (interact.getId() == 576){
            player.msgRed("You can simply afk and auto retaliate will do the rest!");
            player.getCombatBuilder().reset(true);
            return;
        }

    /*    if (!player.startedTutorial){
            player.sendMessage("Not started tutorial");
            DialogueManager.sendStatement(player, "Not started tutorial");
            player.getCombatBuilder().reset(true);
            return;
        }*/
     /*   if (!player.isTutTask1Complete()){
            player.sendMessage("I should come back when im a bit stronger...");
            DialogueManager.sendStatement(player, "I should come back when im a bit stronger...");
            player.setTutTask1Ready(true);
            player.getCombatBuilder().reset(true);
            player.setVgTutStage(3);
            player.getPacketSender().sendString(32753, "A Divine Discovery: Stage 3");//STAGE TITLE
            player.getPacketSender().sendString(32752, "Head to the Church and speak to Father");//STAGE HINT
            return;
        }
        if (!player.isTutTask2Complete()){
            player.sendMessage("Not quite strong enough yet");
            player.getCombatBuilder().reset(true);
            return;
        }
        if (!player.isTutTask3Complete()){
            player.sendMessage("Still not strong enough yet");
            player.getCombatBuilder().reset(true);
            return;
        }
        if (!player.isTutTask4Complete()){
            player.sendMessage("Keep progressing to attack the Mighty Beast");
            player.getCombatBuilder().reset(true);
            return;
        }

        if (!player.isReceivedarmor()){
            player.sendMessage("Speak to the Beast Master to get some Armor first!");
            player.getCombatBuilder().reset(true);
            return;
        }*/



        if (interact.getId() == 2097) {
            if (GameSettings.fallenMinionActive) {
                player.getCombatBuilder().reset(true);
                player.msgRed("Defeat the Fallen Minions in order to defeat the Boss!");
                return;
            }
        }



        if (interact.getId() == 316 || interact.getId() == 309 || interact.getId() == 310 || interact.getId() == 314 || interact.getId() == 315){
            if (!player.getEquipment().contains(7784) && !player.getEquipment().contains(7785) && !player.getEquipment().contains(7786)) {
                player.msgRed("Equip a Soulstone Blade to attack this monster....");
                player.getCombatBuilder().reset(true);
                return;
            }
        }

        if (interact.getId() == 409) {
            if (player.getEasterQuestStage() != 3){
                player.msgRed("You shouldn't attack these without being told to do so.");
                player.getCombatBuilder().reset(true);
                return;
            }
            if (player.getRabidBunniesKilled() >= 25) {
                player.msgRed("You've already completed this part of the quest!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }


            if (interact.getId() == 1022){
            if (GameSettings.STREAMER_EVENT == false){
                player.msgRed("The Stream Event is not active!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }


            if (interact.getId() == 1023){
            if (GameSettings.FRENZY_EVENT == false){
                player.msgRed("The Frenzy Event is not active!");
                player.getCombatBuilder().reset(true);
                return;
            }

            if (player.getLocation() != Location.FRENZYBOSS) {
                player.msgRed("Enter the Boss Lobby before attacking!");
                player.getCombatBuilder().reset(true);
                return;
            }

            if (player.getInventory().contains(6754)) {
                player.msgRed("Open the Frenzy Chest before attacking Frenzy Boss again!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }

        if (interact.getId() == 1783){
            if(player.getLavaCrystalStage() != 1){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }
        if (interact.getId() == 1775){
            if(player.getLavaCrystalStage() != 2){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }


        if (interact.getId() == 1777){
            if(player.getAquaCrystalStage() != 1){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }
        if (interact.getId() == 1778){
            if(player.getAquaCrystalStage() != 2){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }


        if (interact.getId() == 641){
            if(player.getVoidCrystalStage() != 1){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }
        if (interact.getId() == 642){
            if(player.getVoidCrystalStage() != 2){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }
        if (interact.getId() == 643){
            if(player.getVoidCrystalStage() != 3){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }


        if (interact.getId() == 1780){
            if(player.getGaiaCrystalStage() != 1){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }
        if (interact.getId() == 1781){
            if(player.getGaiaCrystalStage() != 2){
                player.msgRed("You have not reached this room yet!");
                player.getCombatBuilder().reset(true);
                return;
            }
        }
        if (interact.getId() == 554){
                player.getCombatBuilder().reset(true);
                return;
        }



       /* if (player.isDefeatedbeast() && !player.completedtutorial){
            player.sendMessage("Completed beast kill already");
            player.getCombatBuilder().reset(true);
            return;
        }*/

        if (interact.getId() == 3169 || interact.getId() == 3170 || interact.getId() == 3171){
            if (interact.getLocation() == Location.PRAYER_MINIGAME_MINION_1){
                player.getCombatBuilder().reset(true);
                return;
            }
        }

        if (interact.getId() == 3172){
            if (DailyBossHandler.CURRENT_DAY != 1){
                player.sendMessage("@red@<shad=0>This Daily Boss is Currently Locked");
                player.getCombatBuilder().reset(true);
                return;
            }

        }

        if (interact.getId() == 3174){
            if (DailyBossHandler.CURRENT_DAY != 2){
                player.sendMessage("@red@<shad=0>This Daily Boss is Currently Locked");
                player.getCombatBuilder().reset(true);
                return;
            }

        }

        if (interact.getId() == 3173){
            if (DailyBossHandler.CURRENT_DAY != 3){
                player.sendMessage("@red@<shad=0>This Daily Boss is Currently Locked");
                player.getCombatBuilder().reset(true);
                return;
            }

        }



            if (interact.getId() == 3168){
            int total = player.getPrayerMinigameMinionKills();
            if (total >= 1000){
                player.sendMessage("You reached the maximum amount of minion kills, time to fight the bosses!");
                return;
            }
        }

            if (interact.getId() == 3168){
            if (player.isPrayerMinigameBossInstanceActive()){
                player.sendMessage("<col=AF70C3><shad=0>You still have @red@<shad=0>" + player.getPrayerMinigameBossKillsLeft() + " <col=AF70C3><shad=0>boss spawns left!");
                return;
            }
        }

        if   (interact.getId() == 9838 || interact.getId() == 6306 || interact.getId() == 3688
            ||interact.getId() == 203 || interact.getId() == 185 || interact.getId() == 603
            ||interact.getId() == 202 || interact.getId() == 3005 || interact.getId() == 5002
            ||interact.getId() == 606 || interact.getId() == 3004 || interact.getId() == 188
            ||interact.getId() == 8010 || interact.getId() == 352 || interact.getId() == 928
            ||interact.getId() == 450 || interact.getId() == 4000 || interact.getId() == 4001
            ||interact.getId() == 9846 || interact.getId() == 1737 || interact.getId() == 1738
            ||interact.getId() == 1739 || interact.getId() == 452 || interact.getId() == 201
            ||interact.getId() == 1725 || interact.getId() == 1726 || interact.getId() == 5080
            || interact.getId() == 2465|| interact.getId() == 2466|| interact.getId() == 2467
            ||  interact.getId() == 143|| interact.getId() == 1472|| interact.getId() == 7329
            || interact.getId() == 4401|| interact.getId() == 4402|| interact.getId() == 4403
            || interact.getId() == 4404|| interact.getId() == 4405|| interact.getId() == 4406
            || interact.getId() == 4407|| interact.getId() == 4408|| interact.getId() == 4409
            || interact.getId() == 2120 || interact.getId() == 2121 || interact.getId() == 2122
            || interact.getId() == 2090 || interact.getId() == 2124 || interact.getId() == 2091
            || interact.getId() == 2126 || interact.getId() == 2127 || interact.getId() == 2128
            || interact.getId() == 457 || interact.getId() == 458) {
             if (player.getSlayer().getSlayerTask().getNpcId() != interact.getId()) {
                    player.msgRed("You can only attack this Monster on task!");
                    return;
                }
            }

        if (interact.getId() == 1051 || interact.getId() == 1052 || interact.getId() == 1053
                || interact.getId() == 1054 || interact.getId() == 1055 || interact.getId() == 1056
                || interact.getId() == 1057 || interact.getId() == 1058 || interact.getId() == 1059) {
            if (player.getHolidayTaskHandler().getTaskNpc() == -1){
                player.getCombatBuilder().reset(true);
                player.msgRed("You must be assigned this Monster to attack them");
                return;
            }

            if (player.getHolidayTaskHandler().getTaskNpc() != interact.getId()) {
                player.getCombatBuilder().reset(true);
                player.msgRed("You must be assigned this Monster to attack them");
                return;
            }
        }

        if (player.getPosition().getX() == interact.getPosition().getX() && player.getPosition().getY() == interact.getPosition().getY() &&
                player.getPosition().getZ() == interact.getPosition().getZ()){
            player.sendMessage("@red@You can't attack an NPC while standing underneath it!");
            return;
        }
        if (interact.getId() == 8520 && !GameSettings.seasonalMulti) {
            player.sendMessage("This multi boss is currently not attackable!");
            player.sendMessage("Please wait for a staff member to activate!");
            return;
        }
        if (player.getRights() != PlayerRights.OWNER && player.getRights() != PlayerRights.MANAGER_2 && player.getRights() != PlayerRights.MANAGER && player.getRights() != PlayerRights.CO_OWNER) {
            for (NpcRequirements req : NpcRequirements.values()) {
                if (interact.getId() == req.getNpcId()) {
                    if (req.getKillCount() > 0){
                        if (player.getPointsHandler().getNPCKILLCount() < req.getKillCount()) {
                            player.sendMessage("You need atleast " + req.getKillCount() + "NPC kills to attack this. (" + player.getPointsHandler().getNPCKILLCount() + "/"
                                    + req.getKillCount() + ")");
                            return;
                        }
                    }else {
                        int npc = req.getRequireNpcId();
                        int total = KillsTracker.getTotalKillsForNpc(npc, player);
                        if (total < req.getAmountRequired()) {
                            player.sendMessage("You need atleast " + req.getAmountRequired() + " "
                                    + NpcDefinition.forId(npc).getName() + " kills to attack this. (" + total + "/"
                                    + req.getAmountRequired() + ")");
                            return;
                        }
                    }
                    break;
                }
            }
       }


        player.getCombatBuilder().attack(interact);


    }


    public void handleSecondClick(Player player, Packet packet) {
        int index = packet.readLEShortA();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;



        player.setEntityInteraction(npc);
        int npcId = npc.getId();

        if (player.getRights() == PlayerRights.DEVELOPER)
            player.getPacketSender().sendMessage("Second click npc id: " + npcId);

        NpcRequirements[] requirements = NpcRequirements.values();
        for (int i = 0; i < requirements.length - 1; i++) {
            if (npcId == requirements[i].getNpcId()) {
                NpcRequirements nextRequirement = requirements[i + 1];
                player.sendMessage("<shad=0><col=AF70C3>You currently have @red@" + KillsTracker.getTotalKillsForNpc(npcId, player) + "@red@/@red@" + nextRequirement.getAmountRequired() + "<shad=0><col=AF70C3> required kills of @red@" + npc.getDefinition().getName());
                player.getMovementQueue().reset();
                player.setEntityInteraction(null);
                return;
            }
        }


        player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (!player.getControllerManager().processNPCClick2(npc)) {
                    return;
                }



                switch (npc.getId()) {
                    case 3378:
                        ModeSwapper.optionSelection(player);
                        break;
                    case 249:
                        new SelectionDialogue(player, "",
                                new SelectionDialogue.Selection("Skip Tutorial?", 0, player -> {
                                    player.setStartedTutorial(true);
                                    player.setReceivedprayerunlock(true);
                                    player.setReceivedarmor(true);
                                    player.setTutTask1Started(true);
                                    player.setTutTask1Ready(true);
                                    player.setTutTask1Complete(true);
                                    player.setTutTask2Started(true);
                                    player.setTutTask2Ready(true);
                                    player.setTutTask2Complete(true);
                                    player.setTutTask3Started(true);
                                    player.setTutTask3Ready(true);
                                    player.setTutTask3Complete(true);
                                    player.setTutTask4Started(true);
                                    player.setTutTask4Ready(true);
                                    player.setTutTask4Complete(true);
                                    player.setMadeCritPotion(true);
                                    player.setMadeDropratePotion(true);
                                    player.setMadeDamagePotion(true);
                                    player.setTutTask5Started(true);
                                    player.setTutTask5Ready(true);
                                    player.setTutTask5Complete(true);
                                    player.setTutTask6Started(true);
                                    player.setTutTask6Ready(true);
                                    player.setTutTask6Complete(true);
                                    player.setDefeatedbeast(true);
                                    player.setCompletedtutorial(true);
                                    player.setVgTutStage(10);
                                    player.setSkippedTutorial(true);
                                    player.getInventory().delete(23119, 999999);
                                    player.getInventory().delete(23121, 999999);
                                    player.getInventory().delete(23122, 999999);
                                    player.getInventory().delete(17582, 999999);
                                    player.getInventory().delete(17584, 999999);
                                    player.getInventory().delete(17586, 999999);
                                    player.getInventory().delete(17490, 999999);
                                    TeleportHandler.teleportPlayer(player, new Position(3168 +- Misc.random(1,2), 3544 +- Misc.random(1,2)), TeleportType.ANCIENT);
                                    player.msgRed("You travel to the Athens Mainland.");
                                    player.msgRed("Speak to Wizard Drendor to Claim your Tutorial Items");
                                    player.getPacketSender().sendWalkableInterface(32750, false);
                                    player.getPacketSender().sendChatboxInterfaceRemoval();
                                }),
                                new SelectionDialogue.Selection("Nevermind...", 1, player -> player.getPacketSender().sendChatboxInterfaceRemoval())).start();
                        break;
                    case 248:
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
                     /*   if (!player.getMasterTasks().hasCompletedAll()){
                            player.getMasterTasks().openInterface();
                            return;
                        }*/
                        break;
                    case 1597:
                        int slayerEssence = player.getSlayeressence() + player.getInventory().getAmount(3576);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + slayerEssence + " <col=0><shad=6C1894>Slayer Essence!");

                        ShopManager.getShops().get(Shop.BEGINNER_SLAYER).open(player);
                        break;
                    case 8275:
                        if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 75) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 75 Slayer!");
                            player.msgRed("Come back when you've reached 75 Slayer!");
                            return;
                        }
                        int slayerEssence3 = player.getSlayeressence() + player.getInventory().getAmount(3576);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + slayerEssence3 + " <col=0><shad=6C1894>Slayer Essence!");
                        ShopManager.getShops().get(Shop.MEDIUM_SLAYER).open(player);
                        break;
                    case 9085:
                        if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 95) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 95 Slayer!");
                            player.msgRed("Come back when you've reached 95 Slayer!");
                            return;
                        }
                        int slayerEssence2 = player.getSlayeressence() + player.getInventory().getAmount(3576);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + slayerEssence2 + " <col=0><shad=6C1894>Slayer Essence!");
                        ShopManager.getShops().get(Shop.ELITE_SLAYER).open(player);
                        break;
                    case 2130:
                        if (!player.isUnlockedSpectralZones()){
                            player.msgRed("You must unlock Challenger Zones to access this Content.");
                            return;
                        }
                        if (!player.getInventory().contains(3500)){
                            player.msgRed("Come back when you've obtained a Corrupt Slayer Skull!");
                            return;
                        }
                        if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 118) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 118 Slayer!");
                            player.msgRed("Come back when you've reached 118 Slayer!");
                            return;
                        }
                        int spectralEssence = player.getSpectralEssence() + player.getInventory().getAmount(2064);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + spectralEssence + " <col=0><shad=6C1894>Spectral Essence!");
                        ShopManager.getShops().get(Shop.SPECTRAL_SLAYER).open(player);
                        break;
                    case 300:
                        if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < 110) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 110 Slayer!");
                            player.msgRed("Come back when you've reached 110 Slayer!");
                            if (!player.getInventory().contains(3500)){
                                player.msgRed("You also need to obtain a Corrupt Slayer Skull");
                            }
                            return;
                        }
                        if (!player.getInventory().contains(3500)){
                            player.msgRed("Come back when you've obtained a Corrupt Slayer Skull!");
                            return;
                        }
                        int corruptEssence = player.getCorruptEssence() + player.getInventory().getAmount(3502);
                        player.getPacketSender().sendMessage("<col=0><shad=6C1894>You have @red@" + corruptEssence + " <col=0><shad=6C1894>Corrupt Essence!");
                        ShopManager.getShops().get(Shop.CORRUPT_SHOP).open(player);
                        break;

                    case 22:
                        player.msgRed("This feature is not available yet!");
                        break;
                    case 931:
                        LocalDateTime now = LocalDateTime.now();

                        if (player.getDailyForestTaskAmount() >= 10){
                            player.msgRed("You can only complete 10 Enchanted Forest Tasks daily!");
                            Duration durationUntilReset = Duration.between(LocalDateTime.now(), player.getForestResetTime());
                            long hoursUntilReset = durationUntilReset.toHours();

                            // Notify the player of the remaining time until reset
                            player.msgFancyPurp("You have " + hoursUntilReset + " hour(s) remaining until your Daily Forest Tasks reset.");
                            return;
                        }
                        if (player.getCorruptMushTaskAmount() > 0){
                            DialogueManager.sendStatement(player, "You still need to retrieve " + player.getCorruptMushTaskAmount() + " Corrupt Mushrooms");
                            return;
                        }
                        if (player.getMysticMushTaskAmount() > 0){
                            DialogueManager.sendStatement(player, "You still need to retrieve " + player.getMysticMushTaskAmount() + " Mystic Mushrooms");
                            return;
                        }

                        if (Misc.random(0,10) == 0){
                            int mushChoice = Misc.random(0,1);
                            int mushAmount = Misc.random(8,20);
                            if (mushChoice == 0){
                                player.setMysticMushTaskAmount(mushAmount);
                                DialogueManager.sendStatement(player, "Come back when you have retrieved "  + player.getMysticMushTaskAmount() + " Mystic Mushrooms");
                                return;
                            }
                            if (mushChoice == 1){
                                player.setCorruptMushTaskAmount(mushAmount);
                                DialogueManager.sendStatement(player, "Come back when you have retrieved "  + player.getCorruptMushTaskAmount() + " Corrupt Mushrooms");
                                return;
                            }
                            return;
                        }




                        if (player.getDailyForestTaskAmount() >= 10){
                            player.msgRed("You can only complete 10 Enchanted Forest Tasks daily!");
                            return;
                        }
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.ENCHANTED_MASTER)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.ENCHANTED_MASTER);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.ENCHANTED_MASTER)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.ENCHANTED_MASTER);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;

                    case 215:
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEAST_MASTER);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEAST_MASTER);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;

                    case 301:
                        if (player.getSkillManager().getCurrentLevel(Skill.BEAST_HUNTER) < 75) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 75 Beast Hunter!");
                            player.sendMessage("Come back when you've reached 75 Beast Hunter!");
                            return;
                        }
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEAST_MASTER_X);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.BEAST_MASTER_X);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;
                    case 455:
                        if (!player.unlockedSpectralZones) {
                            player.msgRed("You must unlock Challenger/Spectral Zones to access this Master.");
                            return;
                        }
                        if (player.getSkillManager().getCurrentLevel(Skill.BEAST_HUNTER) < 105) {
                            DialogueManager.sendStatement(player, "Come back when you've reached 105 Beast Hunter!");
                            player.sendMessage("Come back when you've reached 105 Beast Hunter!");
                            return;
                        }
                        if (!player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST)
                                && player.getSlayer().getSlayerTask().equals(SlayerTasks.NO_TASK)) {
                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.SPECTRAL_BEAST);
                        }
                        if (player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST)) {

                            SlayerMaster.changeSlayerMaster(player, SlayerMaster.SPECTRAL_BEAST);
                            if (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK)
                                player.getSlayer().assignTask();

                            else
                                DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        } else {
                            SlayerMaster yourMaster = player.getSlayer().getSlayerMaster();
                            SlayerMaster thisMaster = SlayerMaster.forNpcId(npc.getId());
                            String yourMastersName = "";
                            String thisMasterName = "";
                            int reqSlayer = 0;
                            if(yourMaster != null) {
                                yourMastersName = yourMaster.getSlayerMasterName();
                            }
                            if(thisMaster != null) {
                                reqSlayer = thisMaster.getSlayerReq();
                                thisMasterName = thisMaster.getSlayerMasterName();
                            }
                            if(player.getSkillManager().getCurrentLevel(Skill.SLAYER) < reqSlayer) {
                                DialogueManager.sendStatement(player, "You need " + reqSlayer + " Slayer to use " + thisMasterName  + ".");
                            } else {
                                DialogueManager.sendStatement(player, "You currently have an assignment with " + yourMastersName);
                            }
                        }
                        break;
                    case 18:
                        if (player.isSandStage2()) {
                            return;
                        }

                        if (!player.isSandStage2() && player.talkedtoghost) {
                            int chance = Misc.getRandom(20);
                            player.performAnimation(new Animation(881));
                            player.setPositionToFace(npc.getPosition());

                            if (chance != 1) {
                                player.getMovementQueue().stun(5);
                                npc.forceChat("Halt!!");
                                npc.setPositionToFace(player.getPosition());
                                npc.performAnimation(new Animation(422)); // punch anim
                                player.performGraphic(new Graphic(254));
                                player.dealDamage(new Hit(10));
                                return;
                            }

                            player.getPacketSender().sendMessage("You should return to the Mysterious Figure with your findings...");
                            DialogueManager.sendStatement(player, "You manage to steal a Tablet from the Guard's Pocket...");
                            player.getInventory().add(7254, 1);
                            player.setSandStage2(true);
                            return;
                        }
                        break;

                    case 767:
                        ChestSpawner.handleChestClick(npcId, player);
                        World.deregister(npc);
                        break;
                    case 289: //DAILY TASK
                        DailyTasks.claimReward(player);
                        break;
                    case 845:
                        DialogueManager.start(player, SlayerDialogues.findAssignment(player));
                        break;
                    case PlayerOwnedShopManager.NPC_ID:
                        player.getPacketSender().sendMessage("POS is currently disabled.");
                        break;
                    case 788:
                        break;
                    case 1394:
                        int[] items = {1053, 1057, 1055, 1038, 1040, 1042, 1044, 1046, 1048, 1050, 14008, 14009, 14010,
                                14484, 19115, 19114, 13736, 13744, 13738, 13742, 13740, 6293, 18754, 11694, 11696, 11698, 11700,
                                15018, 15019, 15020, 15220, 12601, 12603, 12605, 20000, 20001, 20002, 6769, 10942, 10934,
                                455};
                        player.getPacketSender().sendInterface(52300);
                        for (int i = 0; i < items.length; i++)
                            player.getPacketSender().sendItemOnInterface(52302, items[i], i, 1);
                        break;

                    case 3101:
                        DialogueManager.start(player, 95);
                        player.setDialogueActionId(57);
                        break;
                    case 318:
                    case 316:
                    case 313:
                    case 312:
                    case 5748:
                    case 2067:
                        player.setEntityInteraction(npc);
                        Fishing.setupFishing(player, Fishing.forSpot(npc.getId(), true));
                        break;
                    case 6970:
                        player.setDialogueActionId(35);
                        DialogueManager.start(player, 63);
                        break;

                }
                npc.setPositionToFace(player.getPosition());
                player.setPositionToFace(npc.getPosition());
            }
        }));
    }

    public void handleThirdClick(Player player, Packet packet) {
        int index = packet.readShort();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
         NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;
        player.setEntityInteraction(npc).setPositionToFace(npc.getPosition().copy());
        npc.setPositionToFace(player.getPosition());

        if (player.getRights() == PlayerRights.DEVELOPER)
            player.getPacketSender().sendMessage("Third click npc id: " + npc.getId());
        player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (!player.getControllerManager().processNPCClick3(npc)) {
                    return;
                }

                switch (npc.getId()) {

                    case 248:
                        if (player.hasManualPrayer && player.hasManualArmor){
                            player.msgRed("You don't have anymore Tutorial items to claim.");
                            return;
                        }
                        if (player.claimedSkipPrayer && player.claimedSkipArmor){
                            player.msgRed("You don't have anymore Tutorial items to claim.");
                            return;
                        }
                        if (player.completedtutorial && player.skippedTutorial){
                            if (!player.claimedSkipPrayer && !player.hasManualPrayer){
                                DialogueManager.start(player, 8901);
                                player.setDialogueActionId(8901);
                                return;
                            }
                            if (!player.claimedSkipArmor && !player.hasManualArmor){
                                DialogueManager.start(player, 8995);
                                player.setDialogueActionId(8995);
                                return;
                            }
                        }
                        break;
                    case PlayerOwnedShopManager.NPC_ID:
                        player.getPacketSender().sendMessage("POS is currently disabled.");
                        //player.getPlayerOwnedShopManager().openEditor();
                        break;
                    case 4653:
                        player.getPacketSender()
                                .sendMessage("Unfortunately, ship charters are still being established. Check back soon.");
                        break;


                    case 3777:
                        player.getDonatorShop().openInterface(DonatorShop.DonatorShopType.WEAPON);
                        player.sendMessage("<col=0><shad=6C1894>You have @red@" + player.getPointsHandler().getDonatorPoints() + " <col=0><shad=6C1894>Store Points.");
                        break;
                    case 961:
                        if (player.getRights() == PlayerRights.PLAYER) {
                            player.getPacketSender().sendMessage("This feature is currently only available for members.");
                            return;
                        }
                        boolean restore = player.getSpecialPercentage() < 100;
                        if (restore) {
                            player.setSpecialPercentage(100);
                            CombatSpecial.updateBar(player);
                            player.getPacketSender().sendMessage("Your special attack energy has been restored.");
                        }
                        for (Skill skill : Skill.values()) {
                            if (player.getSkillManager().getCurrentLevel(skill) < player.getSkillManager()
                                    .getMaxLevel(skill)) {
                                player.getSkillManager().setCurrentLevel(skill,
                                        player.getSkillManager().getMaxLevel(skill));
                                restore = true;
                            }
                        }
                        if (restore) {
                            player.performGraphic(new Graphic(1302));
                            player.getPacketSender().sendMessage("Your stats have been restored.");
                        } else
                            player.getPacketSender().sendMessage("Your stats do not need to be restored at the moment.");
                        break;
                    case 605:
                        player.getPacketSender().sendMessage("Coming soon!");
                        // player.getPacketSender().sendMessage("").sendMessage("You currently have
                        // "+player.getPointsHandler().getVotingPoints()+" Voting
                        // points.").sendMessage("You can earn points and coins by voting. To do so,
                        // simply use the ::vote command.");;
                        // ShopManager.getShops().get(90).open(player);
                        break;
                }
                npc.setPositionToFace(player.getPosition());
                player.setPositionToFace(npc.getPosition());
            }
        }));
    }

    public void handleFourthClick(Player player, Packet packet) {
        int index = packet.readLEShort();
        if (index < 0 || index > World.getNpcs().capacity())
            return;
        NPC npc = World.getNpcs().get(index);
        if (npc == null)
            return;
        if (SummoningData.beastOfBurden(npc.getId())) {
            Summoning summoning = player.getSummoning();
            if (summoning.getBeastOfBurden() != null && summoning.getFamiliar() != null
                    && summoning.getFamiliar().getSummonNpc() != null
                    && summoning.getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
                summoning.store();
                player.getMovementQueue().reset();
            } else {
                player.getPacketSender().sendMessage("That familiar is not yours!");
            }
            return;
        }
        if (BossPets.pickup(player, npc)) { // done in ur NPCDef just change pick up option index from 1 to 3 and ur
            // fine (or if it was 0 before change to 3 )
            player.getMovementQueue().reset();
            return;
        }

        player.setEntityInteraction(npc);
        if (player.getRights() == PlayerRights.DEVELOPER)
            player.getPacketSender().sendMessage("Fourth click npc id: " + npc.getId());

        player.setWalkToTask(new WalkToTask(player, npc.getPosition(), npc.getSize(), new FinalizedMovementTask() {
            @Override
            public void execute() {
                if (!player.getControllerManager().processNPCClick4(npc)) {
                    return;
                }
                switch (npc.getId()) {


                    case PlayerOwnedShopManager.NPC_ID:
                        player.getPacketSender().sendMessage("POS is currently disabled.");
                        //player.getPlayerOwnedShopManager().claimEarnings();
                        // player.getPlayerOwnedShopManager().claimEarnings();
                        break;

                    case 3777:

                        player.sendMessage("<shad=1>@yel@<img=14>Please check out the donation deals in our ::Discord - #Donation-deals");
                        player.sendMessage("<shad=1>@yel@<img=14>Please check out the donation deals in our ::Discord - #Donation-deals");
                        break;
                    case 605:
                    case 4601:
                        LoyaltyProgramme.open(player);
                        break;

                }
                npc.setPositionToFace(player.getPosition());
                player.setPositionToFace(npc.getPosition());
                // DropsInterface.open(player);
                // DropsInterface.getList(NpcDefinition.getDefinitions().getClass().getName());

            }
        }));
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.isTeleporting() || player.isPlayerLocked() || player.getMovementQueue().isLockMovement())
            return;
        player.afkTicks = 0;
        player.afk = false;
        switch (packet.getOpcode()) {
            case ATTACK_NPC:
                attackNPC(player, packet);
                break;
            case FIRST_CLICK_OPCODE:
                firstClick(player, packet);
                break;
            case SECOND_CLICK_OPCODE:
                handleSecondClick(player, packet);
                break;
            case THIRD_CLICK_OPCODE:
                handleThirdClick(player, packet);
                break;
            case FOURTH_CLICK_OPCODE:
                handleFourthClick(player, packet);
                break;
            case MAGE_NPC:
                int npcIndex = packet.readLEShortA();
                int spellId = packet.readShortA();

                if (npcIndex < 0 || spellId < 0 || npcIndex > World.getNpcs().capacity()) {
                    return;
                }

                NPC n = World.getNpcs().get(npcIndex);
                player.setEntityInteraction(n);

                if (player != null && n != null && player.getRights().OwnerDeveloperOnly()) {
                    player.getPacketSender().sendMessage("Used spell id: " + spellId + " on npc: " + n.getId());
                }

                CombatSpell spell = CombatSpells.getSpell(spellId);

                if (n == null || spell == null) {
                    player.getMovementQueue().reset();
                    return;
                }

                if (!NpcDefinition.getDefinitions()[n.getId()].isAttackable()) {
                    player.getMovementQueue().reset();
                    return;
                }

                if (n.getConstitution() <= 0) {
                    player.getMovementQueue().reset();
                    return;
                }


                player.setPositionToFace(n.getPosition());
                player.setCastSpell(spell);
                if (player.getCombatBuilder().getStrategy() == null) {
                    player.getCombatBuilder().determineStrategy();
                }
                if (CombatFactory.checkAttackDistance(player, n)) {
                    player.getMovementQueue().reset();
                }
                player.getCombatBuilder().resetCooldown();
                player.getCombatBuilder().attack(n);
                break;
        }
    }

    public static final int ATTACK_NPC = 72, FIRST_CLICK_OPCODE = 155, MAGE_NPC = 131, SECOND_CLICK_OPCODE = 17,
            THIRD_CLICK_OPCODE = 21, FOURTH_CLICK_OPCODE = 18;
}
