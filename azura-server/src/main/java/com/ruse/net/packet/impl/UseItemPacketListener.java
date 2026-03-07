package com.ruse.net.packet.impl;

import com.ruse.GameSettings;
import com.ruse.PlayerSetting;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.WalkToTask;
import com.ruse.engine.task.impl.WalkToTask.FinalizedMovementTask;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.definitions.GameObjectDefinition;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.clip.region.RegionClipping;
import com.ruse.world.content.*;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.forgottenRaids.boss.impl.RaidBossFour;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.skill.impl.cooking.Cooking;
import com.ruse.world.content.skill.impl.cooking.CookingData;
import com.ruse.world.content.skill.impl.firemaking.Firelighter;
import com.ruse.world.content.skill.impl.firemaking.Firemaking;
import com.ruse.world.content.skill.impl.fletching.BoltData;
import com.ruse.world.content.skill.impl.fletching.Fletching;
import com.ruse.world.content.skill.impl.herblore.Crushing;
import com.ruse.world.content.skill.impl.herblore.PotionCombinating;
import com.ruse.world.content.skill.impl.herblore.WeaponPoison;
import com.ruse.world.content.skill.impl.prayer.BonesOnAltar;
import com.ruse.world.content.skill.impl.prayer.Prayer;
import com.ruse.world.content.skill.impl.slayer.SlayerDialogues;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.content.skill.skillable.impl.Journeymen;
import com.ruse.world.content.zonechests.Tier1Chest;
import com.ruse.world.content.zonechests.Tier1Totem;
import com.ruse.world.content.zonechests.Tier2Totem;
import com.ruse.world.content.zonechests.Tier3Totem;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class UseItemPacketListener implements PacketListener {
    public final static int USE_ITEM = 122;
    public final static int ITEM_ON_NPC = 57;
    public final static int ITEM_ON_ITEM = 53;
    public final static int ITEM_ON_OBJECT = 192;
    public final static int ITEM_ON_GROUND_ITEM = 25;
    public static final int ITEM_ON_PLAYER = 14;

    private static void useItem(Player player, Packet packet) {
        if (player.isTeleporting() || player.getConstitution() <= 0)
            return;
        int interfaceId = packet.readLEShortA();
        int slot = packet.readShortA();
        int id = packet.readLEShort();
    }

    private static void itemOnItem(Player player, Packet packet) {
        int usedWithSlot = packet.readUnsignedShort();
        int itemUsedSlot = packet.readUnsignedShortA();
        if (usedWithSlot < 0 || itemUsedSlot < 0 || itemUsedSlot > player.getInventory().capacity()
                || usedWithSlot > player.getInventory().capacity())
            return;
        Item usedWith = player.getInventory().getItems()[usedWithSlot];
        Item itemUsedWith = player.getInventory().getItems()[itemUsedSlot];
        if (player.getRights() == PlayerRights.DEVELOPER) {
            player.getPacketSender().sendMessage("ItemOnItem - <shad=000000><col=ffffff>[<col=ff774a>"
                    + ItemDefinition.forId(itemUsedWith.getId()).getName() + ":" + itemUsedWith.getId() + ":"
                    + itemUsedWith.getAmount() + " <col=ffffff>was used on <col=4AD2FF>"
                    + ItemDefinition.forId(usedWith.getId()).getName() + ":" + usedWith.getId() + ":"
                    + usedWith.getAmount() + "<col=ffffff>]");
        }

        if (!player.getControllerManager().canUseItemOnItem(usedWith, itemUsedWith)) {
            return;
        }

        if (Journeymen.fletchLog(player, usedWith.getId(), itemUsedWith.getId()) && !player.getCombatBuilder().isAttacking())
            return;

        if (Journeymen.makeArrow(player, usedWith.getId(), itemUsedWith.getId()) && !player.getCombatBuilder().isAttacking())
            return;

        int drSalt = 23119;
        int divineSalt = 357;
        int dmgSalt = 23121;
        int critSalt = 23122;
        int slayess = 3576;
        int monsteress = 19062;
        int frostfrag = 1450;
        int easterFrag = 715;

        int candy1 = 744;
        int candy2 = 745;


        int rockCake = 7509;

        int vialId = 17490;
        int drsaltamount = player.getInventory().getAmount(drSalt);
        int divinesaltamount = player.getInventory().getAmount(divineSalt);
        int dmgsaltamount = player.getInventory().getAmount(dmgSalt);
        int critsaltamount = player.getInventory().getAmount(critSalt);
        int slayessamount = player.getInventory().getAmount(slayess);
        int monsteressamount = player.getInventory().getAmount(monsteress);
        int frostfragamount = player.getInventory().getAmount(frostfrag);
        int easterFragamount = player.getInventory().getAmount(easterFrag);

        int candy1amount = player.getInventory().getAmount(candy1);
        int candy2amount = player.getInventory().getAmount(candy2);


        int requiredAmount = 5000;
        int amountToCreate = 1;
        int vialrequired = 1;

        if (usedWith.getId() == drSalt && itemUsedWith.getId() == rockCake || usedWith.getId() == rockCake && itemUsedWith.getId() == drSalt) {
            if (drsaltamount >= 500_000) {
                if ((player.getRockCakeCharges() + 25) > 100) {
                    player.sendMessage("You can only have a maximum of 100 charges on the Ascension Cake");
                    return;
                }
                player.getInventory().delete(drSalt, 500_000);
                player.setRockCakeCharges(player.getRockCakeCharges() + 25);
                player.sendMessage("You add 25 Ascension Cake charges. You now have " + player.getRockCakeCharges() + " charges remaining");
            }
        }

        if (usedWith.getId() == dmgSalt && itemUsedWith.getId() == rockCake || usedWith.getId() == rockCake && itemUsedWith.getId() == dmgSalt) {
            if (dmgsaltamount >= 500_000) {
                if ((player.getRockCakeCharges() + 25) > 100) {
                    player.sendMessage("You can only have a maximum of 100 charges on the Ascension Cake");
                    return;
                }
                player.getInventory().delete(dmgSalt, 500_000);
                player.setRockCakeCharges(player.getRockCakeCharges() + 25);
                player.sendMessage("You add 25 Ascension Cake charges. You now have " + player.getRockCakeCharges() + " charges remaining");
            }
        }

        if (usedWith.getId() == critSalt && itemUsedWith.getId() == rockCake || usedWith.getId() == rockCake && itemUsedWith.getId() == critSalt) {
            if (critsaltamount >= 500_000) {
                if ((player.getRockCakeCharges() + 25) > 100) {
                    player.sendMessage("You can only have a maximum of 100 charges on the Ascension Cake");
                    return;
                }
                player.getInventory().delete(critSalt, 500_000);
                player.setRockCakeCharges(player.getRockCakeCharges() + 25);
                player.sendMessage("You add 25 Ascension Cake charges. You now have " + player.getRockCakeCharges() + " charges remaining");
            }
        }

        if (usedWith.getId() == 7782 && itemUsedWith.getId() == 7780) {
            String message = player.getUsername() + " forged a Soulstone Blade!";
            NecromancyMinigame.sendGameMessage(message);
            player.performAnimation(new Animation(713));
            player.getInventory().delete(7782,1);
            player.getInventory().delete(7780,1);
            player.msgFancyPurp("You forged a Soulstone Blade!");
            player.getInventory().add(7784,1);
            player.setForgedGauntletWeapon(true);

        }
        if (usedWith.getId() == 7780 && itemUsedWith.getId() == 7782) {
            String message = player.getUsername() + " forged a Soulstone Blade!";
            NecromancyMinigame.sendGameMessage(message);
            player.performAnimation(new Animation(713));
            player.getInventory().delete(7782,1);
            player.getInventory().delete(7780,1);
            player.msgFancyPurp("You forged a Soulstone Blade!");
            player.getInventory().add(7784,1);
            player.setForgedGauntletWeapon(true);
        }

        if (usedWith.getId() == 432 && itemUsedWith.getId() == 430) {
            TreasureTrinket.forgeTrinket(player);
        }

        int id1 = usedWith.getId();
        int id2 = itemUsedWith.getId();
        int requiredPotAmount = 1;
        Set<Integer> validIds = new HashSet<>(Arrays.asList(17582, 17584, 17586));

        if (validIds.contains(id1) && validIds.contains(id2) && id1 != id2) {
            if (player.getSkillManager().getMaxLevel(Skill.HERBLORE) < 85) {
                player.msgRed("You need at least 85 Herblore to create a Divine potion.");
                return;
            }
            int dmgPotCount = player.getInventory().getAmount(17582);
            int critPotCount = player.getInventory().getAmount(17584);
            int drPotCount = player.getInventory().getAmount(17586);
            int maxDivinePotions = Math.min(Math.min(dmgPotCount, critPotCount), drPotCount);
            if (maxDivinePotions >= requiredPotAmount) {
                player.getInventory().delete(17582, maxDivinePotions);
                player.getInventory().delete(17584, maxDivinePotions);
                player.getInventory().delete(17586, maxDivinePotions);
                player.getInventory().add(358, maxDivinePotions);
                player.msgFancyPurp("You created " + maxDivinePotions + " Divine Potion(s)!");
                player.performAnimation(new Animation(363));
                player.getSkillManager().addExperience(Skill.HERBLORE, 2250 * maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, maxDivinePotions);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, maxDivinePotions);
            } else {
                player.msgFancyPurp("You need at least 1 of each potion to craft a Divine potion.");
            }
        }

        int requiredSaltsAmount = 10000;
        Set<Integer> saltIds = new HashSet<>(Arrays.asList(23119, 23121, 23122));

        if (saltIds.contains(id1) && saltIds.contains(id2) && id1 != id2) {
            if (player.getSkillManager().getMaxLevel(Skill.HERBLORE) < 85) {
                player.msgRed("You need at least 85 Herblore to create Divine Salts.");
                return;
            }
            int dmgSaltCount = player.getInventory().getAmount(23119);
            int critSaltCount = player.getInventory().getAmount(23121);
            int drSaltCount = player.getInventory().getAmount(23122);
            int maxDivineSalts = Math.min(Math.min(dmgSaltCount, critSaltCount), drSaltCount);
            if (maxDivineSalts >= requiredSaltsAmount) {
                int numDivineSalts = maxDivineSalts / requiredSaltsAmount;
                player.getInventory().delete(23119, numDivineSalts * requiredSaltsAmount);
                player.getInventory().delete(23121, numDivineSalts * requiredSaltsAmount);
                player.getInventory().delete(23122, numDivineSalts * requiredSaltsAmount);
                player.getInventory().add(357, numDivineSalts * requiredSaltsAmount);
                player.msgFancyPurp("You created " + numDivineSalts + " Divine Salt(s)!");
                player.performAnimation(new Animation(363));
                player.getSkillManager().addExperience(Skill.HERBLORE, 2 * numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, numDivineSalts);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, numDivineSalts);
            } else {
                player.msgFancyPurp("You need 10k of each Salt to craft Divine Salts.");
            }
        }

        //CORRUPT OWNER DYE
        if (usedWith.getId() == 15792 && itemUsedWith.getId() == 3507) {
            OwnerSetDyes.corruptHatDye(player);
        }
        if (usedWith.getId() == 15793 && itemUsedWith.getId() == 3507) {
            OwnerSetDyes.corruptBodyDye(player);
        }
        if (usedWith.getId() == 15794 && itemUsedWith.getId() == 3507) {
            OwnerSetDyes.corruptLegsDye(player);
        }
        if (usedWith.getId() == 15795 && itemUsedWith.getId() == 3507) {
            OwnerSetDyes.corruptGlovesDye(player);
        }
        if (usedWith.getId() == 15796 && itemUsedWith.getId() == 3507) {
            OwnerSetDyes.corruptBootsDye(player);
        }

        if (usedWith.getId() == 19944 && itemUsedWith.getId() == 3507) {
            OwnerSetDyes.corruptCapeDye(player);
        }

        //POISON OWNER DYE
        if (usedWith.getId() == 15792 && itemUsedWith.getId() == 3509) {
            OwnerSetDyes.poisonHatDye(player);
        }
        if (usedWith.getId() == 15793 && itemUsedWith.getId() == 3509) {
            OwnerSetDyes.poisonBodyDye(player);
        }
        if (usedWith.getId() == 15794 && itemUsedWith.getId() == 3509) {
            OwnerSetDyes.poisonLegsDye(player);
        }
        if (usedWith.getId() == 15795 && itemUsedWith.getId() == 3509) {
            OwnerSetDyes.poisonGlovesDye(player);
        }
        if (usedWith.getId() == 15796 && itemUsedWith.getId() == 3509) {
            OwnerSetDyes.poisonBootsDye(player);
        }
        if (usedWith.getId() == 19944 && itemUsedWith.getId() == 3509) {
            OwnerSetDyes.poisonCapeDye(player);
        }

        //ICY OWNER DYE
        if (usedWith.getId() == 15792 && itemUsedWith.getId() == 3508) {
            OwnerSetDyes.icyHatDye(player);
        }
        if (usedWith.getId() == 15793 && itemUsedWith.getId() == 3508) {
            OwnerSetDyes.icyBodyDye(player);
        }
        if (usedWith.getId() == 15794 && itemUsedWith.getId() == 3508) {
            OwnerSetDyes.icyLegsDye(player);
        }
        if (usedWith.getId() == 15795 && itemUsedWith.getId() == 3508) {
            OwnerSetDyes.icyGlovesDye(player);
        }
        if (usedWith.getId() == 15796 && itemUsedWith.getId() == 3508) {
            OwnerSetDyes.icyBootsDye(player);
        }
        if (usedWith.getId() == 19944 && itemUsedWith.getId() == 3508) {
            OwnerSetDyes.icyCapeDye(player);
        }

            //DMG POTION
        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 23121 || usedWith.getId() == 23121 && itemUsedWith.getId() == 17490) {
            if (dmgsaltamount < requiredAmount) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + requiredAmount + " @red@<shad=0>Damage salts to create a Damage potion.");
                return;
            }
            int totalVialRequired = vialrequired * amountToCreate;
            if (player.getInventory().getAmount(vialId) < totalVialRequired) {
                player.sendMessage("@red@<shad=0>You need vials to create potions!@red@<shad=0>");
                return; // Return if vial requirements are not met
            }

                player.getInventory().delete(dmgSalt, requiredAmount);
                player.getInventory().delete(vialId, amountToCreate);
                player.getInventory().add(17582, amountToCreate);
                player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0>Damage Potion");
                player.performAnimation(new Animation(363));
            if (!player.madeDmgPot){
                player.setMadeDmgPot(true);
            }

            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(17582, amountToCreate);
            }

                player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
        }

        //CRIT POTION
        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 23122 || usedWith.getId() == 23122 && itemUsedWith.getId() == 17490) {
            if (critsaltamount < requiredAmount) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + requiredAmount + " @red@<shad=0> Crit salts to create a Crit potion.");
                return;
            }
            int totalVialRequired = vialrequired * amountToCreate;
            if (player.getInventory().getAmount(vialId) < totalVialRequired) {
                player.sendMessage("@red@<shad=0>You need vials to create potions! @red@<shad=0>");
                return; // Return if vial requirements are not met
            }
                player.getInventory().delete(critSalt, requiredAmount);
                player.getInventory().delete(vialId, amountToCreate);
                player.getInventory().add(17584, amountToCreate);
                player.performAnimation(new Animation(363));
            if (!player.madeCritPot){
                player.setMadeCritPot(true);
            }

            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(17584, amountToCreate);
            }
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0>Crit Potion");
                player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
            }


        if (usedWith.getId() == 744 && itemUsedWith.getId() == 745) {
            if (player.madeCandyBoost){
                player.msgRed("You've already made a Candy Boost.");
                return;
            }
            if (player.getInventory().getAmount(744) < 500){
                player.msgRed("You need at least 500 of each Candy heart to forge a Candy Boost!");
                return;
            }
            if (player.getInventory().getAmount(745) < 500){
                player.msgRed("You need at least 500 of each Candy heart to forge a Candy Boost!");
                return;
            }
            World.sendMessage("<col=AF70C3><shad=0>[VALENTINES] " + player.getUsername() + " Just forged a Candy Boost!");
            player.msgFancyPurp("You have successfully forged a Candy Boost!");
            player.setMadeCandyBoost(true);
            player.getInventory().delete(744, 500);
            player.getInventory().delete(745, 500);
            player.getInventory().add(2626, 1);

        }


        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 744) {
            if (player.getInventory().getAmount(744) < 25) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + 25 + " @red@<shad=0>Candy Hearts to create an Love Serum.");
                return;
            }
            player.getInventory().delete(744, 25);
            player.getInventory().delete(17490, 1);
            player.getInventory().add(2601, 1);
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0> Love Serum");
            player.performAnimation(new Animation(363));
            player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);

            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(2601, amountToCreate);
            }
        }
        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 745) {
            if (player.getInventory().getAmount(745) < 25) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + 25 + " @red@<shad=0>Candy Hearts to create an Love Serum.");
                return;
            }
            player.getInventory().delete(745, 25);
            player.getInventory().delete(17490, 1);
            player.getInventory().add(2601, 1);
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0> Love Serum");
            player.performAnimation(new Animation(363));
            player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(2601, amountToCreate);
            }
        }


            //DROP RATE POTION
        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 23119 || usedWith.getId() == 23119 && itemUsedWith.getId() == 17490) {
            if (drsaltamount < requiredAmount) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + requiredAmount + " @red@<shad=0>Droprate salts to create a Drop Rate potion.");
                return;
            }
            int totalVialRequired = vialrequired * amountToCreate;
            if (player.getInventory().getAmount(vialId) < totalVialRequired) {
                player.sendMessage("@red@<shad=0>You need vials to create potions! @red@<shad=0>");
                return; // Return if vial requirements are not met
            }
                player.getInventory().delete(drSalt, requiredAmount);
                player.getInventory().delete(vialId, amountToCreate);
                player.getInventory().add(17586, amountToCreate);
                player.performAnimation(new Animation(363));
                if (!player.madeDRpot){
                    player.setMadeDRpot(true);
                }

            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(17586, amountToCreate);
            }
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0>Drop Rate Potion");
            player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
                Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
        }

        //divine
        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 357 || usedWith.getId() == 23119 && itemUsedWith.getId() == 357) {
            if (divinesaltamount < requiredAmount) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + requiredAmount + " @red@<shad=0>divine salts to create a divine potion.");
                return;
            }
            int totalVialRequired = vialrequired * amountToCreate;
            if (player.getInventory().getAmount(vialId) < totalVialRequired) {
                player.sendMessage("@red@<shad=0>You need vials to create potions! @red@<shad=0>");
                return; // Return if vial requirements are not met
            }
            player.getInventory().delete(divineSalt, requiredAmount);
            player.getInventory().delete(vialId, amountToCreate);
            player.getInventory().add(358, amountToCreate);
            player.performAnimation(new Animation(363));
            if (!player.madeDivinePotion){
                player.setMadeDivinePotion(true);
            }

            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(358, amountToCreate);
            }
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0>divine Potion");
            player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
        }


        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 3576) {
            if (player.getSkillManager().getMaxLevel(Skill.HERBLORE) < 75) {
                player.msgRed("You need at least 75 Herblore to create a Fury potion.");
                return;
            }
            if (slayessamount < 100) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + 100 + " @red@<shad=0>Slayer Essence to create a Fury potion.");
                return;
            }
            if (player.getInventory().getAmount(vialId) < 1) {
                player.sendMessage("@red@<shad=0>You need vials to create potions!@red@<shad=0>");
                return;
            }
            player.getInventory().delete(3576, 100);
            player.getInventory().delete(17490, 1);
            player.getInventory().add(1321, 1);
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0>Fury Potion");
            player.performAnimation(new Animation(363));
            player.getSkillManager().addExperience(Skill.HERBLORE, 2000);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(1321, amountToCreate);
            }
        }

        if (usedWith.getId() == 1321 && itemUsedWith.getId() == 19062) {
            if (player.getSkillManager().getMaxLevel(Skill.HERBLORE) < 95) {
                player.msgRed("You need at least 95 Herblore to create a Rage potion.");
                return;
            }
            if (monsteressamount < 50) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + 50 + " @red@<shad=0>Monster Essence to create a Rage potion.");
                return;
            }
            player.getInventory().delete(19062, 50);
            player.getInventory().delete(1321, 1);
            player.getInventory().add(1323, 1);
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0>Rage Potion");
            player.performAnimation(new Animation(363));
            player.getSkillManager().addExperience(Skill.HERBLORE, 4000);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(1323, amountToCreate);
            }
        }

        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 1450) {
            if (frostfragamount < 100) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + 150 + " @red@<shad=0>Frost Fragments to create an Iceborn Serum.");
                return;
            }
            player.getInventory().delete(1450, 100);
            player.getInventory().delete(17490, 1);
            player.getInventory().add(1465, 1);
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0> Iceborn Serum");
            player.performAnimation(new Animation(363));
            player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(1465, amountToCreate);
            }
        }


        if (usedWith.getId() == 17490 && itemUsedWith.getId() == 715) {
            if (easterFragamount < 100) {
                player.sendMessage("@red@<shad=0>You need @red@<shad=0>" + 100 + " @red@<shad=0>Easter Fragments to create an Easter Serum.");
                return;
            }
            player.getInventory().delete(715, 100);
            player.getInventory().delete(17490, 1);
            player.getInventory().add(716, 1);
            player.sendMessage("@gre@<shad=0>You successfully created @red@<shad=0>" + amountToCreate + " @gre@<shad=0> Easter Serum");
            player.performAnimation(new Animation(363));
            player.getSkillManager().addExperience(Skill.HERBLORE, 1000);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_5_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_25_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_50_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_100_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_250_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1000_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_1500_POTIONS, 1);
            Achievements.doProgress(player, Achievements.Achievement.MAKE_2500_POTIONS, 1);
            int pendant = 434;
            if (player.getEquipment().contains(pendant)){
                player.getInventory().add(716, amountToCreate);
            }
        }

        //DEPOSITING RUNES INTO POUCH
        if (usedWith.getId() == 19622 && itemUsedWith.getId() == 20010) {
            int invCurseRunes = player.getInventory().getAmount(20010);
            player.getInventory().delete(20010, invCurseRunes);
            player.setCurseRunes(player.getCurseRunes() + invCurseRunes);
            player.sendMessage("You added " + invCurseRunes + " Curse Runes to your Rune pouch!");
        }

        if (usedWith.getId() == 19622 && itemUsedWith.getId() == 20014) {
            int invSoulRunes = player.getInventory().getAmount(20014);
            player.getInventory().delete(20014, invSoulRunes);
            player.setSoulRunes(player.getSoulRunes() + invSoulRunes);
            player.sendMessage("You added " + invSoulRunes + " Soul Runes to your Rune pouch!");
        }

        if (usedWith.getId() == 19622 && itemUsedWith.getId() == 20015) {
            int invCryptRunes = player.getInventory().getAmount(20015);
            player.getInventory().delete(20015, invCryptRunes);
            player.setCryptRunes(player.getCryptRunes() + invCryptRunes);
            player.sendMessage("You added " + invCryptRunes + " Crypt Runes to your Rune pouch!");
        }

        if (usedWith.getId() == 19622 && itemUsedWith.getId() == 20012) {
            int invShadowRunes = player.getInventory().getAmount(20012);
            player.getInventory().delete(20012, invShadowRunes);
            player.setShadowRunes(player.getShadowRunes() + invShadowRunes);
            player.sendMessage("You added " + invShadowRunes + " Shadow Runes to your Rune pouch!");
        }

        if (usedWith.getId() == 19622 && itemUsedWith.getId() == 20011) {
            int invWraithRunes = player.getInventory().getAmount(20011);
            player.getInventory().delete(20011, invWraithRunes);
            player.setWraithRunes(player.getWraithRunes() + invWraithRunes);
            player.sendMessage("You added " + invWraithRunes + " Wraith Runes to your Rune pouch!");
        }

        if (usedWith.getId() == 19622 && itemUsedWith.getId() == 20013) {
            int invVoidRunes = player.getInventory().getAmount(20013);
            player.getInventory().delete(20013, invVoidRunes);
            player.setVoidRunes(player.getVoidRunes() + invVoidRunes);
            player.sendMessage("You added " + invVoidRunes + " Void Runes to your Rune pouch!");
        }

        //////
        //DEPOSITING AMMO INTO QUIVER
        if (usedWith.getId() == 2056 && itemUsedWith.getId() == 1428) {
            int invvorpalAmmo = player.getInventory().getAmount(1428);
            player.getInventory().delete(1428, invvorpalAmmo);
            player.setVorpalAmmo(player.getVorpalAmmo() + invvorpalAmmo);
            player.sendMessage("You added " + invvorpalAmmo + " Vorpal Ammo to your Quiver!");
        }

        if (usedWith.getId() == 2056 && itemUsedWith.getId() == 1430) {
            int invBsAmmo = player.getInventory().getAmount(1430);
            player.getInventory().delete(1430, invBsAmmo);
            player.setBloodstainedAmmo(player.getBloodstainedAmmo() + invBsAmmo);
            player.sendMessage("You added " + invBsAmmo + " Bloodstained Ammo to your Quiver!");
        }

        if (usedWith.getId() == 2056 && itemUsedWith.getId() == 1429) {
            int invSymAmmo = player.getInventory().getAmount(1429);
            player.getInventory().delete(1429, invSymAmmo);
            player.setSymbioteAmmo(player.getSymbioteAmmo() + invSymAmmo);
            player.sendMessage("You added " + invSymAmmo + " Symbiote Ammo to your Quiver!");
        }

        if (usedWith.getId() == 2056 && itemUsedWith.getId() == 1431) {
            int invNetherAmmo = player.getInventory().getAmount(1431);
            player.getInventory().delete(1431, invNetherAmmo);
            player.setNetherAmmo(player.getNetherAmmo() + invNetherAmmo);
            player.sendMessage("You added " + invNetherAmmo + " Nether Ammo to your Quiver!");
        }






        //DEPOSITING ESSENCE INTO POUCH
        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 11052) {
            int invearthshards = player.getInventory().getAmount(11052);
            player.getInventory().delete(11052, invearthshards);
            player.setEarthessence(player.getEarthessence() + invearthshards);
            player.sendMessage("You added " + invearthshards + " Earth Essence to your essence pouch!");
        }

        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 11054) {
            int invfireshards = player.getInventory().getAmount(11054);
            player.getInventory().delete(11054, invfireshards);
            player.setFireessence(player.getFireessence() + invfireshards);
            player.sendMessage("You added " + invfireshards + " Fire Essence to your essence pouch!");
        }

        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 11056) {
            int invwatershards = player.getInventory().getAmount(11056);
            player.getInventory().delete(11056, invwatershards);
            player.setWateressence(player.getWateressence() + invwatershards);
            player.sendMessage("You added " + invwatershards + " Water Essence to your essence pouch!");
        }


        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 3576) {
            int invslayershards = player.getInventory().getAmount(3576);
            player.getInventory().delete(3576, invslayershards);
            player.setSlayeressence(player.getSlayeressence() + invslayershards);
            player.sendMessage("You added " + invslayershards + " Slayer Essence to your essence pouch!");
        }

        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 6466) {
            int invBeastShards = player.getInventory().getAmount(6466);
            player.getInventory().delete(6466, invBeastShards);
            player.setBeastEssence(player.getBeastEssence() + invBeastShards);
            player.sendMessage("You added " + invBeastShards + " Beast Essence to your essence pouch!");
        }

        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 3502) {
            int invCorruptshards = player.getInventory().getAmount(3502);
            player.getInventory().delete(3502, invCorruptshards);
            player.setCorruptEssence(player.getCorruptEssence() + invCorruptshards);
            player.sendMessage("You added " + invCorruptshards + " Corrupt Essence to your essence pouch!");
        }

        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 2699) {
            int invEnchantedShards = player.getInventory().getAmount(2699);
            player.getInventory().delete(2699, invEnchantedShards);
            player.setEnchantedEssence(player.getEnchantedEssence() + invEnchantedShards);
            player.sendMessage("You added " + invEnchantedShards + " Enchanted Essence to your essence pouch!");
        }

        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 2064) {
            int invSpectralShards = player.getInventory().getAmount(2064);
            player.getInventory().delete(2064, invSpectralShards);
            player.setSpectralEssence(player.getSpectralEssence() + invSpectralShards);
            player.sendMessage("You added " + invSpectralShards + " Spectral Essence to your essence pouch!");
        }


        if (usedWith.getId() == 18015 && itemUsedWith.getId() == 19062) {
            int invmonstershards = player.getInventory().getAmount(19062);
            player.getInventory().delete(19062, invmonstershards);
            player.setMonsteressence(player.getMonsteressence() + invmonstershards);
            player.sendMessage("You added " + invmonstershards + " Monster Essence to your essence pouch!");
        }

        //DEPOSITING SALTS INTO POUCH
        if (usedWith.getId() == 1308 && itemUsedWith.getId() == 23121) {
            int invDamageSalts = player.getInventory().getAmount(23121);
            player.getInventory().delete(23121, invDamageSalts);
            player.setDamageSalts(player.getDamageSalts() + invDamageSalts);
            player.sendMessage("You added " + invDamageSalts + " Damage Salts to your Salt pouch!");
        }

        if (usedWith.getId() == 1308 && itemUsedWith.getId() == 23119) {
            int invDroprateSalts = player.getInventory().getAmount(23119);
            player.getInventory().delete(23119, invDroprateSalts);
            player.setDroprateSalts(player.getDroprateSalts() + invDroprateSalts);
            player.sendMessage("You added " + invDroprateSalts + " Droprate Salts to your Salt pouch!");
        }

        if (usedWith.getId() == 1308 && itemUsedWith.getId() == 23122) {
            int invCriticalSalts = player.getInventory().getAmount(23122);
            player.getInventory().delete(23122, invCriticalSalts);
            player.setCriticalSalts(player.getCriticalSalts() + invCriticalSalts);
            player.sendMessage("You added " + invCriticalSalts + " Critcal Salts to your Salt pouch!");
        }




        for (int i = 0; i < Firelighter.values().length; i++) {
            if (usedWith.getId() == Firelighter.values()[i].getLighterId()
                    || itemUsedWith.getId() == Firelighter.values()[i].getLighterId()) {
                Firelighter.handleFirelighter(player, i);
                break;
            }
        }

        for (int i = 0; i < Crushing.values().length; i++) {
            if (usedWith.getId() == Crushing.values()[i].getInput()
                    || itemUsedWith.getId() == Crushing.values()[i].getInput()) {
                Crushing.handleCrushing(player, i);
                break;
            }
        }

        for (int i = 0; i < BoltData.values().length; i++) {
            if (usedWith.getId() == BoltData.values()[i].getTip()
                    || itemUsedWith.getId() == BoltData.values()[i].getTip()) {
                Fletching.tipBolt(player, BoltData.values()[i].getTip());
                break;
            }
        }

        WeaponPoison.execute(player, itemUsedWith.getId(), usedWith.getId());
        if (itemUsedWith.getId() == 590 || usedWith.getId() == 590) {
            Firemaking.lightFire(player, itemUsedWith.getId() == 590 ? usedWith.getId() : itemUsedWith.getId(), false, 1);
        }

        if (itemUsedWith.getDefinition().getName().contains("(") && usedWith.getDefinition().getName().contains("(")) {
            PotionCombinating.combinePotion(player, usedWith.getId(), itemUsedWith.getId());
        }
    }

    private static void itemOnObject(Player player, Packet packet) {
        int interfaceType = packet.readShort();
        int objectId = packet.readInt();
        int objectY = packet.readLEShortA();
        int itemSlot = packet.readLEShort();
        int objectX = packet.readLEShortA();
        int itemId = packet.readShort();

        if (itemSlot < 0 || itemSlot > player.getInventory().capacity()) {
            return;
        }

        Item item = player.getInventory().getItems()[itemSlot];
        if (item == null) {
            return;
        }

        GameObject gameObject = new GameObject(objectId, new Position(objectX, objectY, player.getPosition().getZ()));

        if (objectId > 0 && objectId != 6 && !RegionClipping.objectExists(gameObject)) {
            return;
        }

        player.setInteractingObject(gameObject);
        if (player.getRights() == PlayerRights.DEVELOPER) {
            if (GameObjectDefinition.forId(gameObject.getId()) != null
                    && GameObjectDefinition.forId(gameObject.getId()).getName() != null) {
                player.getPacketSender()
                        .sendMessage("ItemOnObject - <shad=000000><col=ffffff>[<col=ff774a>"
                                + ItemDefinition.forId(itemId).getName() + ":" + itemId
                                + " <col=ffffff>was used on <col=4AD2FF>"
                                + GameObjectDefinition.forId(gameObject.getId()).getName() + ":" + gameObject.getId()
                                + "<col=ffffff>]");
            } else {
                player.getPacketSender().sendMessage("ItemOnObject - <shad=000000><col=ffffff>[<col=ff774a>"
                        + ItemDefinition.forId(itemId).getName() + ":" + itemId
                        + " <col=ffffff>was used on <col=4AD2FF>" + gameObject.getId()
                        + "<col=ffffff>] @red@(null obj. def)");
            }
        }
        player.setWalkToTask(new WalkToTask(player, gameObject.getPosition().copy(), gameObject.getSize(),
                new FinalizedMovementTask() {
                    @Override
                    public void execute() {

                        if (!player.getControllerManager().handleItemOnObject(gameObject, item)) {
                            return;
                        }

                        if (CookingData.forFish(item.getId()) != null && CookingData.isRange(objectId)) {
                            player.setPositionToFace(gameObject.getPosition());
                            Cooking.selectionInterface(player, CookingData.forFish(item.getId()));
                            return;
                        }

                        if (Journeymen.smithBar(player, objectId, item.getId())) {
                            return;
                        }

                        if (objectId == 20001 && item.getId() == 373) {
                            int rawFishAmount =  player.getInventory().getAmount(373);
                            int chanceForCook = Misc.random(0,100);
                            player.getInventory().delete(373,rawFishAmount);

                            if (chanceForCook >= 0){
                                player.getInventory().add(375,rawFishAmount);
                                player.msgPurp("You successfully cooked all of your Soul Fish!");
                            } else {
                                player.msgRed("You manage to cook a some fish, but you lost most of them in the process..");
                                player.getInventory().add(375, rawFishAmount / 3);
                            }
                        }


                            if (objectId == 6189 && item.getId() == 7788) {
                            int ore1Inventory = player.getInventory().getAmount(7766);
                            int ore2Inventory = player.getInventory().getAmount(7788);
                            if (ore1Inventory < 10){
                                player.msgRed("You need atleast 10 Soulstone Ore to forge a bar!");
                                return;
                            }
                            if (ore2Inventory < 10){
                                player.msgRed("You need atleast 10 Blurite Ore to forge a bar!");
                                return;
                            }

                            int chanceToFail = Misc.random(0,999999);

                            player.getInventory().delete(7766, 99999);
                            player.getInventory().delete(7788, 99999);
                            player.performAnimation(new Animation(20898));
                            TaskManager.submit(new Task(4, player, false) {
                                @Override
                                protected void execute() {
                                    if (chanceToFail == 0){
                                        player.msgRed("You failed to forge a bar!");
                                        String message = player.getUsername() + " failed to forge a Soulstone bar...";
                                        NecromancyMinigame.sendGameMessage(message);
                                        stop();
                                        return;
                                    }
                                    String message = player.getUsername() + "  forged a Soulstone bar!";
                                    NecromancyMinigame.sendGameMessage(message);
                                    player.msgRed("You sacrifice 10 of each material to forge a Soulstone Bar!");
                                    player.getInventory().add(7780,1);
                                    stop();
                                }
                            });
                        }

                        if (objectId == 6189 && item.getId() == 7766) {
                            int ore1Inventory = player.getInventory().getAmount(7766);
                            int ore2Inventory = player.getInventory().getAmount(7788);
                            if (ore1Inventory < 10){
                                player.msgRed("You need atleast 10 Soulstone Ore to forge a bar!");
                                return;
                            }
                            if (ore2Inventory < 10){
                                player.msgRed("You need atleast 10 Blurite Ore to forge a bar!");
                                return;
                            }

                            int chanceToFail = Misc.random(0,999999);

                            player.getInventory().delete(7766, 99999);
                            player.getInventory().delete(7788, 99999);
                            player.performAnimation(new Animation(20898));
                            TaskManager.submit(new Task(4, player, false) {
                                @Override
                                protected void execute() {
                                    if (chanceToFail == 0){
                                        String message = player.getUsername() + " failed to forge a Soulstone bar...";
                                        NecromancyMinigame.sendGameMessage(message);
                                        player.msgRed("You failed to forge a bar!");
                                        stop();
                                        return;
                                    }

                                    String message = player.getUsername() + "  forged a Soulstone bar!";
                                    NecromancyMinigame.sendGameMessage(message);
                                    player.msgRed("You sacrifice 10 of each material to forge a Soulstone Bar!");
                                    player.getInventory().add(7780,1);
                                    stop();
                                }
                            });
                        }


                        if (objectId == 40568 && item.getId() == 7747) {
                            int logs1Inventory = player.getInventory().getAmount(7747);
                            if (logs1Inventory < 20){
                                player.msgRed("You need atleast 20 Soulstone Logs to forge a Hilt!");
                                return;
                            }


                            int chanceToFail = Misc.random(0,999999);

                            player.getInventory().delete(7747, 99999);
                            player.performAnimation(new Animation(20898));
                            TaskManager.submit(new Task(4, player, false) {
                                @Override
                                protected void execute() {
                                    if (chanceToFail == 0){
                                        String message = player.getUsername() + " failed to forge a Soulstone Hilt...";
                                        NecromancyMinigame.sendGameMessage(message);
                                        player.msgRed("You failed to forge a Hilt!");
                                        stop();
                                        return;
                                    }

                                    String message = player.getUsername() + "  forged a Soulstone Hilt!";
                                    NecromancyMinigame.sendGameMessage(message);
                                    player.msgRed("You sacrifice 20 Logs to forge a Soulstone Hilt!");
                                    player.getInventory().add(7782,1);
                                    stop();
                                }
                            });
                        }


                            if (objectId == 26289 && item.getId() == 534) {
                            int bones_to_sacrifice = player.getSkillManager().getMaxLevel(Skill.NECROMANCY) * 2;
                            if (player.getInventory().getAmount(534) < bones_to_sacrifice){
                                player.msgRed("You need atleast " + bones_to_sacrifice + "Bones to make this offering!");
                                return;
                            }

                            player.getInventory().delete(534, bones_to_sacrifice);
                            player.getSkillManager().addExperience(Skill.PRAYER, 500 * bones_to_sacrifice);
                            player.performAnimation(new Animation(713));
                            player.msgFancyPurp("You sacrificed: " + bones_to_sacrifice + " bones with a Necromancy level of " + player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
                        }

                        if (objectId == 26289 && item.getId() == 7305) {
                            int bones_to_sacrifice = player.getSkillManager().getMaxLevel(Skill.NECROMANCY) * 2;
                            if (player.getInventory().getAmount(7305) < bones_to_sacrifice){
                                player.msgRed("You need atleast " + bones_to_sacrifice + "Bones to make this offering!");
                                return;
                            }
                            if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
                                MediumTasks.doProgress(player, MediumTasks.MediumTaskData.OFFER_25K_MEDIUM_BONES, bones_to_sacrifice);
                            }
                            player.getInventory().delete(7305, bones_to_sacrifice);
                            player.getSkillManager().addExperience(Skill.PRAYER, 1000 * bones_to_sacrifice);
                            player.performAnimation(new Animation(713));
                            player.msgFancyPurp("You sacrificed: " + bones_to_sacrifice + " bones with a Necromancy level of " + player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
                        }

                        if (objectId == 26289 && item.getId() == 7306) {
                            int bones_to_sacrifice = player.getSkillManager().getMaxLevel(Skill.NECROMANCY) * 2;
                            if (player.getInventory().getAmount(7306) < bones_to_sacrifice){
                                player.msgRed("You need atleast " + bones_to_sacrifice + "Bones to make this offering!");
                                return;
                            }
                            if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
                                EliteTasks.doProgress(player, EliteTasks.EliteTaskData.OFFER_25K_ELITE_BONES, bones_to_sacrifice);
                            }
                            player.getInventory().delete(7306, bones_to_sacrifice);
                            player.getSkillManager().addExperience(Skill.PRAYER, 1500 * bones_to_sacrifice);
                            player.performAnimation(new Animation(713));
                            player.msgFancyPurp("You sacrificed: " + bones_to_sacrifice + " bones with a Necromancy level of " + player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
                        }


                        if (objectId == 26289 && item.getId() == 7307) {
                            int bones_to_sacrifice = player.getSkillManager().getMaxLevel(Skill.NECROMANCY) * 2;
                            if (player.getInventory().getAmount(7307) < bones_to_sacrifice){
                                player.msgRed("You need atleast " + bones_to_sacrifice + "Bones to make this offering!");
                                return;
                            }
                            player.getInventory().delete(7307, bones_to_sacrifice);
                            player.getSkillManager().addExperience(Skill.PRAYER, 2500 * bones_to_sacrifice);
                            player.performAnimation(new Animation(713));
                            player.msgFancyPurp("You sacrificed: " + bones_to_sacrifice + " bones with a Necromancy level of " + player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
                        }

                        if (objectId == 26289 && item.getId() == 7308) {
                            int bones_to_sacrifice = player.getSkillManager().getMaxLevel(Skill.NECROMANCY) * 2;
                            if (player.getInventory().getAmount(7308) < bones_to_sacrifice){
                                player.msgRed("You need atleast " + bones_to_sacrifice + "Bones to make this offering!");
                                return;
                            }
                            player.getInventory().delete(7308, bones_to_sacrifice);
                            player.getSkillManager().addExperience(Skill.PRAYER, 10000 * bones_to_sacrifice);
                            player.performAnimation(new Animation(713));
                            player.msgFancyPurp("You sacrificed: " + bones_to_sacrifice + " bones with a Necromancy level of " + player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
                        }
                        if (objectId == 26289 && item.getId() == 7309) {
                            int bones_to_sacrifice = player.getSkillManager().getMaxLevel(Skill.NECROMANCY) * 2;
                            if (player.getInventory().getAmount(7309) < bones_to_sacrifice){
                                player.msgRed("You need atleast " + bones_to_sacrifice + "Bones to make this offering!");
                                return;
                            }
                            player.getInventory().delete(7309, bones_to_sacrifice);
                            player.getSkillManager().addExperience(Skill.PRAYER, 15000 * bones_to_sacrifice);
                            player.performAnimation(new Animation(713));
                            player.msgFancyPurp("You sacrificed: " + bones_to_sacrifice + " bones with a Necromancy level of " + player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
                        }



                       /* if (objectId == 4008 && item.getId() == 20425) {
                            if (!player.startedTutorial) {
                                return;
                            }
                            if (!player.tutTask2Started) {
                                return;
                            }

                            player.sendMessage("You use the urn on the altar...");
                            player.getInventory().delete(20425, 1);
                            player.setTutTask2Ready(true);
                            int delay = 4; // Change this value to control the delay between each set of graphic and animation.

                            for (int i = 0; i < 4; i++) {
                                final int index = i;

                                player.getMovementQueue().setLockMovement(true);

                                TaskManager.submit(new Task(delay * i, false) {
                                    @Override
                                    public void execute() {
                                        if (index == 1) {
                                            player.performGraphic(new Graphic(1308));
                                            player.performAnimation(new Animation(7392));
                                            this.stop();
                                        }
                                        if (index == 2) {
                                            player.performGraphic(new Graphic(93));
                                            player.performAnimation(new Animation(10184));
                                            this.stop();
                                        }
                                        if (index == 3) {
                                            player.performGraphic(new Graphic(1393));
                                            player.performAnimation(new Animation(9599 ));
                                            player.getMovementQueue().setLockMovement(false);
                                            player.getInventory().add(20426, 1);
                                            player.sendMessage("You feel the urn vanish. It's replaced by a strange symbol.");
                                            this.stop();
                                        }
                                    }
                                });
                            }
                        }*/

                        if (Prayer.isBone(itemId) && (objectId == 409 || objectId == 24343 || objectId == 13192)) {
                            BonesOnAltar.openInterface(player, itemId);
                            return;
                        }

                        if (GameObjectDefinition.forId(objectId) != null
                                && GameObjectDefinition.forId(objectId).getName() != null
                                && GameObjectDefinition.forId(objectId).getName().equalsIgnoreCase("furnace")
                                && ItemDefinition.forId(itemId) != null
                                && ItemDefinition.forId(itemId).getName() != null
                                && ItemDefinition.forId(itemId).getName().contains("ore")) {
                            // Smelting.openInterface(player);
                            return;
                        }
                        if (GameObjectDefinition.forId(objectId) != null
                                && GameObjectDefinition.forId(objectId).getName() != null
                                && GameObjectDefinition.forId(objectId).getName().equalsIgnoreCase("furnace")
                                && itemId == 2357) {
                        }

                        switch (objectId) {
                            case 6461:
                                if (player.getForgottenRaidParty() != null && player.getForgottenRaidParty().isInRaid() && player.getForgottenRaidParty().getRaid().getCurrentBoss() instanceof RaidBossFour) {
                                    ((RaidBossFour)player.getForgottenRaidParty().getRaid().getCurrentBoss()).keyClick(player, itemId);
                                }
                                break;
                        }
                    }
                }));
    }

    private static void itemOnNpc(Player player, Packet packet) {
        int id = packet.readShortA();
        int index = packet.readShortA();
        int slot = packet.readLEShort();
        if (index < 0 || index > World.getNpcs().capacity()) {
            return;
        }
        if (slot < 0 || slot > player.getInventory().getItems().length) {
            return;
        }
        NPC npc = World.getNpcs().get(index);
        if (npc == null) {
            return;
        }
        Item usedItem = player.getInventory().forSlot(slot);
        if (usedItem == null) {
            return;
        }
        if (player.getInventory().getItems()[slot].getId() != id) {
            return;
        }
        if (player.getRights() == PlayerRights.DEVELOPER) {
            player.getPacketSender().sendMessage("ItemOnNPC - <shad=000000><col=ffffff>[<col=ff774a>"
                    + ItemDefinition.forId(id).getName() + ":" + id + " <col=ffffff>was used on <col=4AD2FF>"
                    + npc.getDefinition().getName() + ":" + npc.getId() + "<col=ffffff>]");
        }

        if (!player.getControllerManager().processItemOnNPC(npc, usedItem)) {
            return;
        }

        switch (npc.getId()) {
            case 215:
                if (usedItem.getId() == 459){
                    player.getInventory().delete(459,1);
                    if (player.getInventory().contains(18015)) {
                        player.msgPurp("You return the map to the Beast master, in return for 1k Essence!");
                        player.msgPurp("1k Beast Essence has been added to your pouch.");
                        player.setBeastEssence(player.getBeastEssence() + 1000);
                    } else {
                        player.msgPurp("You return the map to the Beast master, in return for 1k Essence!");
                        player.msgPurp("1k Beast Essence has been added to your inventory..");
                        player.getInventory().add(6466,1000);
                    }
                }
                    break;

            case 301:
                if (usedItem.getId() == 420){
                    player.getInventory().delete(420,1);
                    if (player.getInventory().contains(18015)) {
                        player.msgPurp("You return the Bounty token to the Corrupt Master, in return for 150 Essence!");
                        player.msgPurp("150 Monster Essence has been added to your pouch.");
                        player.setMonsteressence(player.getMonsteressence() + 150);
                    } else {
                        player.msgPurp("You return the Bounty token to the Corrupt Master, in return for 150 Essence!");
                        player.msgPurp("150 Monster Essence has been added to your inventory..");
                        player.getInventory().add(19062,150);
                    }
                }
                break;

            case 2709:
                if (usedItem.getId() == 995){

                    if (player.getMarinaTaskID() == 6) {
                        MarinasTasks.handleTasks(player);
                    }
                }
                    break;
            case 9028:
                int easterFrags = player.getInventory().getAmount(715);
                if (usedItem.getId() == 715){
                    if (easterFrags < 1000){
                        DialogueManager.start(player, 8845);
                        return;
                    }
                    player.getInventory().delete(715,1000);
                    player.getInventory().add(720,1);
                    player.msgPurp("You forged an Egg Crate!");
                    World.sendMessage("<shad=0><col=AF70C3>[EASTER] " + player.getUsername() + " just forged an Egg Crate!");
                }
                    break;



            case 931:
                int mysticMushs = player.getInventory().getAmount(17821);
                int corruptMushs = player.getInventory().getAmount(17407);

                if (usedItem.getId() == 17821){
                    if (player.getMysticMushTaskAmount() > 0){
                        player.setMysticMushTaskAmount(player.getMysticMushTaskAmount() - mysticMushs);
                        player.getInventory().delete(17821, mysticMushs);
                        player.msgPurp("You turned in " + mysticMushs + " Mystic Mushrooms");
                        player.msgPurp(player.getMysticMushTaskAmount() + "X remaining");
                        if (player.getMysticMushTaskAmount() <= 0){
                            player.setMysticMushTaskAmount(0);
                            DialogueManager.sendStatement(player, "You've completed a task for the Forest!");
                            for (Bank bank : player.getBanks()) {
                                if (bank != null && bank.contains(17821)) {
                                    bank.delete(id, bank.getAmount(17821));
                                }
                            }
                        }
                    }
                }



                if (usedItem.getId() == 17407){
                    if (player.getCorruptMushTaskAmount() > 0){
                        player.setCorruptMushTaskAmount(player.getCorruptMushTaskAmount() - corruptMushs);
                        player.getInventory().delete(17407, corruptMushs);
                        player.msgPurp("You turned in " + mysticMushs + " Corrupt Mushrooms");
                        player.msgPurp(player.getCorruptMushTaskAmount() + "X remaining");

                        if (player.getCorruptMushTaskAmount() <= 0){
                            player.setCorruptMushTaskAmount(0);
                            DialogueManager.sendStatement(player, "You've completed a task for the Forest!");
                            player.getCompanion().addExp(Misc.random(1000, 5000));

                            for (Bank bank : player.getBanks()) {
                                if (bank != null && bank.contains(17407)) {
                                    bank.delete(id, bank.getAmount(17407));
                                }
                            }
                        }
                    }
                }
                    break;

            case 100:
                if (usedItem.getId() == 19065){
                    new SelectionDialogue(player,"Bribe the king?",
                            new SelectionDialogue.Selection("Bribe the King with Treasure, unleash the demon.", 0, p -> {
                                GameSettings.KINGS_BONES++;
                                p.getInventory().delete(19065, 1);
                                p.getPacketSender().sendChatboxInterfaceRemoval();
                                int kingsBones = GameSettings.KINGS_BONES;
                                String message = null;
                                switch (kingsBones) {
                                    case 2:
                                        World.sendMessage("<shad=0><col=AF70C3>[SKELETAL] Only 3 more offerings to unleash the Skeletal Demon!");
                                        npc.forceChat("Yes Yes, Bring me more!");
                                        break;
                                    case 4:
                                        World.sendMessage("<shad=0><col=AF70C3>[SKELETAL] Only 1 more offerings to unleash the Skeletal Demon!");
                                        DiscordManager.sendMessage("[SKELETAL] Only 1 more offerings to unleash the Skeletal Demon! <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                                        npc.forceChat("Glorious Riches!!");
                                        break;
                                    default:
                                        if (kingsBones >= 5) {
                                            BoneBossSpawner.startBoneBossEvent(p);
                                            GameSettings.KINGS_BONES = 0;
                                        }
                                }
                            }),
                            new SelectionDialogue.Selection("Nevermind", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                    ).start();
                }
                break;

        }
    }

    private static void itemOnGroundItem(Player player) {
        player.getPacketSender().sendMessage("Nothing interesting happens.");
    }

    private static void itemOnPlayer(Player player, Packet packet) {
        int interfaceId = packet.readUnsignedShortA();
        int targetIndex = packet.readUnsignedShort();
        int itemId = packet.readUnsignedShort();
        int slot = packet.readLEShort();
        if (slot < 0 || slot > player.getInventory().capacity() || targetIndex > World.getPlayers().capacity())
            return;
        Player target = World.getPlayers().get(targetIndex);
        if (target == null)
            return;

        if (!player.getControllerManager().processItemOnPlayer(target, itemId, slot)) {
            return;
        }

        switch (itemId) {
            case 27:
            case 4155:
            case 3500:
                if (player.getSlayer().getSlayerMaster() == SlayerMaster.ENCHANTED_MASTER) {
                    player.msgRed("You can only Duo in Normal/Corrupt Slayer or Beast Hunter(x) Bosses!");
                    return;
                }
                if (player.getSlayer().getSlayerMaster() == SlayerMaster.BEAST_MASTER) {
                    player.msgRed("You can only Duo in Normal/Corrupt Slayer or Beast Hunter(x) Bosses!");
                    return;
                }

                if (player.getSlayer().getDuoPartner() != null) {
                    player.msgRed("You already have a duo partner.");
                    return;
                }
                if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
                    player.msgRed("You already have a Slayer task. You must reset it first.");
                    return;
                }
                Player duoPartner = World.getPlayers().get(targetIndex);
                if (duoPartner != null) {
                    if (duoPartner.getSlayer().getDuoPartner() != null) {
                        player.msgRed("This player already has a duo partner.");
                        return;
                    }
                    if (duoPartner.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
                        player.msgRed("This player already has a Slayer task.");
                        return;
                    }
                    if (duoPartner.getSlayer().getSlayerMaster() != player.getSlayer().getSlayerMaster()) {
                        player.msgRed("You do not have the same Slayer master as that player.");
                        return;
                    }
                    if (duoPartner.busy() || duoPartner.getLocation() == Location.WILDERNESS) {
                        player.msgRed("This player is currently busy.");
                        return;
                    }
                    DialogueManager.start(duoPartner, SlayerDialogues.inviteDuo(duoPartner, player));
                    player.msgFancyPurp("You have invited @red@" + duoPartner.getUsername() + " <col=AF70C3>to join your Slayer duo team.");
                }
                break;
        }
    }
    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;
        switch (packet.getOpcode()) {
            case ITEM_ON_ITEM:
                itemOnItem(player, packet);
                break;
            case USE_ITEM:
                useItem(player, packet);
                break;
            case ITEM_ON_OBJECT:
                itemOnObject(player, packet);
                break;
            case ITEM_ON_GROUND_ITEM:
                itemOnGroundItem(player);
                break;
            case ITEM_ON_NPC:
                itemOnNpc(player, packet);
                break;
            case ITEM_ON_PLAYER:
                itemOnPlayer(player, packet);
                break;
        }
    }
}
