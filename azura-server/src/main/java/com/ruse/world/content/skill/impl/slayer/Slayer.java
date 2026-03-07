package com.ruse.world.content.skill.impl.slayer;

import com.ruse.GameSettings;
import com.ruse.ServerSaves.SlayerGlobalUpdater;
import com.ruse.donation.DonatorRanks;
import com.ruse.model.*;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.container.impl.Shop.ShopManager;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.ZoneProgression.NpcRequirements;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.slayercontent.SlayerHelmets;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Slayer {

    private Player player;

    public Slayer(Player p) {
        this.player = p;
    }

    private SlayerTasks slayerTask = SlayerTasks.NO_TASK, lastTask = SlayerTasks.NO_TASK;
    private SlayerMaster slayerMaster = SlayerMaster.BEGINNER_SLAYER;
    private int amountToSlay, taskStreak, taskStreakX, taskStreakI, astralstreak, dragonstreak, godstreak;
    private String duoPartner, duoInvitation;

    public void assignTask() {
        boolean hasTask = getSlayerTask() != SlayerTasks.NO_TASK && player.getSlayer().getLastTask() != getSlayerTask();
        boolean duoSlayer = duoPartner != null;
        if (duoSlayer && !player.getSlayer().assignDuoSlayerTask())
            return;
        if (hasTask) {
            player.getPacketSender().sendInterfaceRemoval();
            return;
        }
        SlayerTaskData taskData = SlayerTasks.getNewTaskData(slayerMaster, player);
        int slayerTaskAmount = taskData.getSlayerTaskAmount();
        SlayerTasks taskToSet = taskData.getTask();
        if (duoSlayer) {
            slayerTaskAmount *= 1.4;
        }
        NpcDefinition npcDef = NpcDefinition.forId(taskToSet.getNpcId());
        if (taskToSet == player.getSlayer().getLastTask() || npcDef != null && npcDef.getSlayerLevel() > player.getSkillManager().getMaxLevel(Skill.SLAYER)) {
            assignTask();
            return;
        }

        for (NpcRequirements req : NpcRequirements.values()) {
            if (taskToSet.getNpcId() == req.getNpcId()) {
                if (req.getKillCount() > 0) {
                    if (player.getPointsHandler().getNPCKILLCount() < req.getKillCount()) {
                        assignTask();
                        return;
                    }
                } else {
                    int total = KillsTracker.getTotalKillsForNpc(req.getRequireNpcId(), player);
                    if (total < req.getAmountRequired()) {
                        assignTask();
                        return;
                    }
                }
                break;
            }
        }

        player.getPacketSender().sendInterfaceRemoval();
        this.amountToSlay = slayerTaskAmount;
        this.slayerTask = taskToSet;
        player.setSuperiorisspawned(false);

        boolean hasZoneRequirement = false;
        for (NpcRequirements req : NpcRequirements.values()) {
            if (taskToSet.getNpcId() == req.getNpcId()) {
                amountToSlay *= 3;
                hasZoneRequirement = true;
                break;
            }
        }

        DialogueManager.start(player, SlayerDialogues.receivedTask(player, getSlayerMaster(), getSlayerTask()));
        PlayerPanel.refreshPanel(player);

        if (duoSlayer) {
            Player duo = World.getPlayerByName(duoPartner);
            duo.getSlayer().setSlayerTask(taskToSet);

            int duoAmountToSlay = slayerTaskAmount;
            if (hasZoneRequirement) {
                duoAmountToSlay *= 3;
            }
            duo.getSlayer().setAmountToSlay(duoAmountToSlay);

            duo.getPacketSender().sendInterfaceRemoval();
            DialogueManager.start(duo, SlayerDialogues.receivedTask(duo, slayerMaster, taskToSet));
            PlayerPanel.refreshPanel(duo);
        }
    }

    public static int SKIP_TOKEN = 9719;

    public void resetSlayerTask() {
        SlayerTasks task = getSlayerTask();

        if (task == SlayerTasks.NO_TASK)
            return;
        if (player.getInventory().getAmount(SKIP_TOKEN) < 1) {
            player.getPacketSender().sendMessage("You must have a skip scroll to reset a task.");
            return;
        }


        this.slayerTask = SlayerTasks.NO_TASK;
        this.amountToSlay = 0;

        if (slayerMaster == SlayerMaster.ENCHANTED_MASTER){
            player.setDailyForestTaskAmount(player.getDailyForestTaskAmount() + 1);
            player.msgRed("You skip your Forest Task and sacrifice 1 of your daily tasks.");
        }


        player.getInventory().delete(SKIP_TOKEN, 1);

        PlayerPanel.refreshPanel(player);
        Player duo = duoPartner == null ? null : World.getPlayerByName(duoPartner);

        if (duo != null) {
            duo.getSlayer().setSlayerTask(SlayerTasks.NO_TASK).setAmountToSlay(0);
            duo.msgPurp("Your partner reset your team's Slayer task.");
            PlayerPanel.refreshPanel(duo);
            player.msgPurp("You have successfully reset your team's Slayer task.");
        } else {
            player.msgPurp("Your Slayer task has been reset.");
        }
    }

    public void killedNpc(NPC npc) {
        if (slayerTask != SlayerTasks.NO_TASK) {
            if (slayerTask.getNpcId() == npc.getId()) {
                if (slayerMaster != SlayerMaster.BEAST_MASTER && slayerMaster != SlayerMaster.BEAST_MASTER_X && slayerMaster != SlayerMaster.SPECTRAL_BEAST) {
                    player.getSkillManager().addExperience(Skill.SLAYER, slayerTask.getXP());
                }
                if (slayerMaster == SlayerMaster.BEAST_MASTER || slayerMaster == SlayerMaster.BEAST_MASTER_X || slayerMaster == SlayerMaster.SPECTRAL_BEAST) {
                    player.getSkillManager().addExperience(Skill.BEAST_HUNTER, slayerTask.getXP());
                }
                handleReward();
            }
        }
    }


  /*  //BATTLE PASS SLAYER HANDLE
            if (player.getBattlePass().getType() == BattlePassType.TIER2 || player.getBattlePass().getType() == BattlePassType.TIER1) {
        player.getBattlePass().addExperience(Misc.inclusiveRandom(125, 350));
    }*/
    private void handleReward() {

        handleSlayerTaskDeath(true);

        if (slayerTask.getTaskMaster() != SlayerMaster.ENCHANTED_MASTER) {
            if (Misc.random(0,50) == 0){
                player.getInventory().add(1306, 1);
                player.msgFancyPurp("You received a " + ItemDefinition.forId(1306).getName() + "!");
            }
            if (Misc.random(0,100) == 0){
                player.getInventory().add(1307, 1);
                player.msgFancyPurp("You received a " + ItemDefinition.forId(1307).getName() + "!");
            }
        }

        switch (slayerMaster){
            case BEGINNER_SLAYER:
                SlayerHelmets.process(player,1302);
                break;
            case MEDIUM_SLAYER:
                SlayerHelmets.process(player,1303);
                break;
            case ELITE_SLAYER:
                SlayerHelmets.process(player,1304);
                break;
            case CORRUPT_SLAYER:
                SlayerHelmets.process(player,3512);
                break;
            case SPECTRAL_SLAYER:
                SlayerHelmets.process(player,2062);
                break;
            case BEAST_MASTER:
            case BEAST_MASTER_X:
            case SPECTRAL_BEAST:
                SlayerHelmets.process(player,1305);
                break;
        }

        if (duoPartner != null) {
            Player duo = World.getPlayerByName(duoPartner);
            if (duo != null) {
                if (checkDuoSlayer(player, false)) {
                    duo.getSlayer().handleSlayerTaskDeath(
                            Locations.goodDistance(player.getPosition(), duo.getPosition(), 50));
                } else {
                    resetDuo(player, duo);
                }
            }
        }
    }

    public void handleSlayerTaskDeath(boolean giveXp) {
        int xp = slayerTask.getXP();
        boolean notEnchanted = slayerTask.getTaskMaster() != SlayerMaster.ENCHANTED_MASTER;

        if (amountToSlay > 1) {
            amountToSlay--;
        } else {
            int amountOfTickets = 0;
            GameSettings.globalslayertasks++;
            SlayerGlobalUpdater.updateAzgothAmount(GameSettings.globalslayertasks);

            // global notifications...
            if (GameSettings.globalslayertasks >= 100) {
                AzgothSpawner.startAzgothEvent(player);
                GameSettings.globalslayertasks = 0;
                SlayerGlobalUpdater.updateAzgothAmount(GameSettings.globalslayertasks);
            } else if (GameSettings.globalslayertasks == 95) {
                World.sendMessage("<shad=0><col=AF70C3>[SLAYER]<shad=0>@red@ " +
                        (100 - GameSettings.globalslayertasks) +
                        "<shad=0><col=AF70C3> tasks left to summon the Slayer Beast!");
            } else if (GameSettings.globalslayertasks == 75) {
                World.sendMessage("<shad=0><col=AF70C3>[SLAYER]<shad=0>@red@ " +
                        (100 - GameSettings.globalslayertasks) +
                        "<shad=0><col=AF70C3> tasks left to summon the Slayer Beast!");
            } else if (GameSettings.globalslayertasks == 50) {
                World.sendMessage("<shad=0><col=AF70C3>[SLAYER]<shad=0>@red@ " +
                        (100 - GameSettings.globalslayertasks) +
                        "<shad=0><col=AF70C3> tasks left to summon the Slayer Beast!");
            } else if (GameSettings.globalslayertasks == 25) {
                World.sendMessage("<shad=0><col=AF70C3>[SLAYER]<shad=0>@red@ " +
                        (100 - GameSettings.globalslayertasks) +
                        "<shad=0><col=AF70C3> tasks left to summon the Slayer Beast!");
                DiscordManager.sendMessage(
                        "[SLAYER] " + (100 - GameSettings.globalslayertasks) +
                                " tasks left to summon the Slayer Beast! <@&1475675670196654080>",
                        Channels.ACTIVE_EVENTS);
            }

            // completion messages...
            if (slayerTask.getTaskMaster() != SlayerMaster.BEAST_MASTER
                    && slayerTask.getTaskMaster() != SlayerMaster.BEAST_MASTER_X
                    && slayerTask.getTaskMaster() != SlayerMaster.SPECTRAL_BEAST) {
                player.getPacketSender().sendMessage(
                        "<shad=0><col=AF70C3>You have completed your Slayer task! " +
                                "Return to your master for another one.");
            } else {
                player.getPacketSender().sendMessage(
                        "<shad=0><col=AF70C3>You have completed your Beast Hunter task! " +
                                "Return to your master for another one.");
            }

            // ───── calculate base tickets ─────────────────────────────────
            if (slayerTask.getTaskMaster() == SlayerMaster.BEGINNER_SLAYER) {
                amountOfTickets += 70;
                int valExp = Misc.random(5000, 10000);
                if (player.getBattlePass().getType() == BattlePassType.TIER2
                        || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(valExp);
                    player.msgFancyPurp("You received " + valExp +
                            " Battle Pass Experience for completing an Easy Task!");
                }
            }
            if (slayerTask.getTaskMaster() == SlayerMaster.MEDIUM_SLAYER) {
                amountOfTickets += 105;
                int valExp = Misc.random(10000, 20000);
                if (player.getBattlePass().getType() == BattlePassType.TIER2
                        || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(valExp);
                    player.msgFancyPurp("You received " + valExp +
                            " Battle Pass Experience for completing a Medium Task!");
                }
            }
            if (slayerTask.getTaskMaster() == SlayerMaster.ELITE_SLAYER) {
                amountOfTickets += 225;
                int valExp = Misc.random(20000, 40000);
                if (player.getBattlePass().getType() == BattlePassType.TIER2
                        || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(valExp);
                    player.msgFancyPurp("You received " + valExp +
                            " Battle Pass Experience for completing an Elite Task!");
                }
            }
            if (slayerTask.getTaskMaster() == SlayerMaster.CORRUPT_SLAYER) {
                amountOfTickets += 25;
                int valExp = Misc.random(30000, 50000);
                if (player.getBattlePass().getType() == BattlePassType.TIER2
                        || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(valExp);
                    player.msgFancyPurp("You received " + valExp +
                            " Battle Pass Experience for completing a Corrupt Task!");
                }
            }
            if (slayerTask.getTaskMaster() == SlayerMaster.SPECTRAL_SLAYER) {
                amountOfTickets += 25;
                int valExp = Misc.random(30000, 50000);
                if (player.getBattlePass().getType() == BattlePassType.TIER2
                        || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(valExp);
                    player.msgFancyPurp("You received " + valExp +
                            " Battle Pass Experience for completing a Spectral Task!");
                }
            }
            if (slayerTask.getTaskMaster() == SlayerMaster.ENCHANTED_MASTER) {
                amountOfTickets += 5;
            }
            if (slayerTask.getTaskMaster() == SlayerMaster.BEAST_MASTER
                    || slayerTask.getTaskMaster() == SlayerMaster.BEAST_MASTER_X
                    || slayerTask.getTaskMaster() == SlayerMaster.SPECTRAL_BEAST) {
                amountOfTickets += 150;
                int valExp = Misc.random(50000, 50000);
                if (player.getBattlePass().getType() == BattlePassType.TIER2
                        || player.getBattlePass().getType() == BattlePassType.TIER1) {
                    player.getBattlePass().addExperience(valExp);
                    player.msgFancyPurp("You received " + valExp +
                            " Battle Pass Experience for completing a Beast Task!");
                }
            }
            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.MAXIMUM_OVERDRIVE)) {
                    if (Misc.random(100) < 10) {  // 10% chance
                        amountOfTickets *= 2;
                        player.sendMessage("@gre@Maximum Overdrive activated! " +
                                "Your Slayer tickets have been doubled!");
                    }
                }
            }

            if (player.getEquipment().getItems()[Equipment.AURA_SLOT].getId() == 17018 && notEnchanted) {
                amountOfTickets += Misc.random(25, 55);
            }
            if (player.getEquipment().getItems()[Equipment.AURA_SLOT].getId() == 2610 && notEnchanted) {
                amountOfTickets += Misc.random(25, 55);
            }
            if (player.getRights() == PlayerRights.YOUTUBER && notEnchanted) {
                amountOfTickets += Misc.random(15, 15);
            }
            if (player.getAmountDonated() >= DonatorRanks.COSMIC_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(50, 35);
            }
            if (player.getAmountDonated() >= DonatorRanks.GUARDIAN_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(55, 40);
            }
            if (player.getAmountDonated() >= DonatorRanks.CORRUPT_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(60, 45);
            }
            if (player.getAmountDonated() >= DonatorRanks.GLADIATOR_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(40, 25);
            }
            if (player.getAmountDonated() >= DonatorRanks.ASCENDANT_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(30, 20);
            }
            if (player.getAmountDonated() >= DonatorRanks.CELESTIAL_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(20, 15);
            }
            if (player.getAmountDonated() >= DonatorRanks.ARCHON_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(15, 12);
            }
            if (player.getAmountDonated() >= DonatorRanks.MYTHIC_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(12, 7);
            }
            if (player.getAmountDonated() >= DonatorRanks.ETHEREAL_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(8, 6);
            }
            if (player.getAmountDonated() >= DonatorRanks.ADEPT_AMOUNT && notEnchanted) {
                amountOfTickets += Misc.random(4, 4);
            }
            if (player.getSlayerBoost().isActive()) {
                amountOfTickets *= 1.18;
            }
            if (amountOfTickets > 0) {
                // update streaks...
                if (slayerTask.getTaskMaster() == SlayerMaster.BEGINNER_SLAYER) taskStreak++;
                if (slayerTask.getTaskMaster() == SlayerMaster.MEDIUM_SLAYER)   taskStreakI++;
                if (slayerTask.getTaskMaster() == SlayerMaster.ELITE_SLAYER)    taskStreakX++;
                if (slayerTask.getTaskMaster() == SlayerMaster.CORRUPT_SLAYER)  dragonstreak++;
                if (slayerTask.getTaskMaster() == SlayerMaster.SPECTRAL_SLAYER) godstreak++;
                if (slayerTask.getTaskMaster() == SlayerMaster.ENCHANTED_MASTER) {
                    player.setEnchantedTasksDone(player.getEnchantedTasksDone() + 1);
                    player.setDailyForestTaskAmount(player.getDailyForestTaskAmount() + 1);
                    if (player.getDailyForestTaskAmount() >= 10) {
                        player.msgRed("You've completed your 10 daily Forest Tasks, come back tomorrow for another adventure!");
                        player.setForestResetTime(LocalDateTime.now().with(LocalTime.MAX));
                    }
                }
                if (slayerTask.getTaskMaster() == SlayerMaster.BEAST_MASTER
                        || slayerTask.getTaskMaster() == SlayerMaster.BEAST_MASTER_X
                        || slayerTask.getTaskMaster() == SlayerMaster.SPECTRAL_BEAST) {
                    godstreak++;
                }
            }


            if (player.getMarinaTaskID() == 9){
                MarinasTasks.handleTasks(player);
            }

            if (player.getMembershipTier().isMember())
                amountOfTickets *= player.getMembershipTier().getExtra_points_tickets();
            if (player.getSlayer().slayerMaster == SlayerMaster.BEGINNER_SLAYER) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_25_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_50_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_150_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_250_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_375_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_650_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_800_EASY_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_1000_EASY_TASKS, 1);
                StarterTasks.doProgress(player, StarterTasks.StarterTask.COMPLETE_5_EASY_TASKS, 1);
                player.setEasyTasksDone(player.getEasyTasksDone() + 1);

            }

            if (player.getSlayer().slayerMaster == SlayerMaster.MEDIUM_SLAYER) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_25_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_50_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_150_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_250_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_375_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_650_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_800_MEDIUM_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_1000_MEDIUM_TASKS, 1);
                player.setMediumTasksDone(player.getMediumTasksDone() + 1);

                if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
                    MediumTasks.doProgress(player, MediumTasks.MediumTaskData.COMPLETE_50_MEDIUM_SLAYER, 1);
                }
            }

            if (player.getSlayer().slayerMaster == SlayerMaster.ELITE_SLAYER) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_25_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_50_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_150_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_250_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_375_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_650_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_800_ELITE_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_1000_ELITE_TASKS, 1);
                player.setEliteTasksDone(player.getEliteTasksDone() + 1);
                if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
                    EliteTasks.doProgress(player, EliteTasks.EliteTaskData.COMPLETE_50_ELITE_SLAYER, 1);
                }
            }

            if (player.getSlayer().slayerMaster == SlayerMaster.CORRUPT_SLAYER) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_25_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_50_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_150_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_250_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_375_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_650_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_800_CORRUPT_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_1000_CORRUPT_TASKS, 1);
            }

            if (player.getSlayer().slayerMaster == SlayerMaster.BEAST_MASTER) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_2_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_10_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_20_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_40_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_60_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_80_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_200_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_300_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_400_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_BEAST_TASKS, 1);
                player.setBeastTasksDone(player.getBeastTasksDone() + 1);
            }

            if (player.getSlayer().slayerMaster == SlayerMaster.BEAST_MASTER_X) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_2_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_10_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_20_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_40_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_60_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_80_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_200_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_300_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_400_CORRUPT_BEAST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_CORRUPT_BEAST_TASKS, 1);
                player.setBeastTasksDone(player.getBeastTasksDone() + 1);
            }
            if (player.getSlayer().slayerMaster == SlayerMaster.SPECTRAL_BEAST) {
                //TODO NEW ACHIEVES
                player.setBeastTasksDone(player.getBeastTasksDone() + 1);
            }

            if (player.getSlayer().slayerMaster == SlayerMaster.ENCHANTED_MASTER) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_10_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_25_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_50_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_150_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_200_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_300_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_400_FOREST_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_FOREST_TASKS, 1);
            }

            if (player.getSlayer().slayerMaster == SlayerMaster.SPECTRAL_SLAYER) {
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_5_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_25_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_50_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_100_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_150_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_250_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_375_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_500_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_650_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_800_SPECTRAL_TASKS, 1);
                Achievements.doProgress(player, Achievements.Achievement.COMPLETE_1000_SPECTRAL_TASKS, 1);
            }


            player.getPointsHandler().incrementSlayerSpree(1);
            player.getDailyTaskInterface().MiscTasksCompleted(4, 1);
            player.getDailyTaskInterface().MiscTasksCompleted(6, 1);
            player.getDailyTaskInterface().MiscTasksCompleted(13, 1);
            lastTask = slayerTask;
            slayerTask = SlayerTasks.NO_TASK;
            amountToSlay = 0;

            if (PerkManager.currentPerk != null) {
                if (PerkManager.currentPerk.getName().equalsIgnoreCase("Slayer")&& notEnchanted) {
                    amountOfTickets *= 1.20;
                }
            }

            if (player.getSlayer().getSlayerMaster() == SlayerMaster.CORRUPT_SLAYER) {
                String essenceMessage = "<col=AF70C3><shad=0>You have received " + amountOfTickets + "X Corrupt essence for completing your task";

                if (player.getNodesUnlocked() != null) {
                    if (player.getSkillTree().isNodeUnlocked(Node.ESSENCE_REAVER)) {
                        amountOfTickets *= 1.10;
                    }
                }
                if (player.getMembershipTier().isMember()) {
                    amountOfTickets *= player.getMembershipTier().getExtra_points_tickets();
                }

                if (player.getInventory().contains(18015)) {
                    player.setCorruptEssence(player.getCorruptEssence() + amountOfTickets);
                    player.sendMessage(essenceMessage);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Corrupt essence has been added to your pouch!");
                } else {
                    player.getInventory().addDropIfFull(3502, amountOfTickets);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Corrupt essence has been added to your Inventory!");
                }
                givePoints(player.getSlayer().getLastTask().getTaskMaster());
            }

            if (player.getSlayer().getSlayerMaster() == SlayerMaster.SPECTRAL_SLAYER) {
                String essenceMessage = "<col=AF70C3><shad=0>You have received " + amountOfTickets + "X Spectral essence for completing your task";

                if (player.getNodesUnlocked() != null) {
                    if (player.getSkillTree().isNodeUnlocked(Node.ESSENCE_REAVER)) {
                        amountOfTickets *= 1.10;
                    }
                }
                if (player.getMembershipTier().isMember()) {
                    amountOfTickets *= player.getMembershipTier().getExtra_points_tickets();
                }

                if (player.getInventory().contains(18015)) {
                    player.setSpectralEssence(player.getSpectralEssence() + amountOfTickets);
                    player.sendMessage(essenceMessage);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Spectral essence has been added to your pouch!");
                } else {
                    player.getInventory().addDropIfFull(2064, amountOfTickets);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Spectral essence has been added to your Inventory!");
                }
                givePoints(player.getSlayer().getLastTask().getTaskMaster());
            }


            if (player.getSlayer().getSlayerMaster() == SlayerMaster.ENCHANTED_MASTER) {
                String essenceMessage = "<col=AF70C3><shad=0>You have received " + amountOfTickets + "X Enchanted essence for completing your task";

                if (player.getInventory().contains(18015)) {
                    player.setEnchantedEssence(player.getEnchantedEssence() + amountOfTickets);
                    player.sendMessage(essenceMessage);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Enchanted essence has been added to your pouch!");
                } else {
                    player.getInventory().addDropIfFull(2699, amountOfTickets);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Enchanted essence has been added to your Inventory!");
                }
                givePoints(player.getSlayer().getLastTask().getTaskMaster());
            }


            if (player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER && player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER_X && player.getSlayer().getSlayerMaster() != SlayerMaster.CORRUPT_SLAYER && player.getSlayer().getSlayerMaster() != SlayerMaster.ENCHANTED_MASTER && player.getSlayer().getSlayerMaster() != SlayerMaster.SPECTRAL_SLAYER && player.getSlayer().getSlayerMaster() != SlayerMaster.SPECTRAL_BEAST) {
                String essenceMessage = "<col=AF70C3><shad=0>You have received " + amountOfTickets + "X Slayer essence for completing your task";

                if (player.getNodesUnlocked() != null) {
                    if (player.getSkillTree().isNodeUnlocked(Node.ESSENCE_REAVER)) {
                        amountOfTickets *= 1.10;
                    }
                }
                if (player.getMembershipTier().isMember()) {
                    amountOfTickets *= player.getMembershipTier().getExtra_points_tickets();
                }

                if (player.getInventory().contains(18015)) {
                    player.setSlayeressence(player.getSlayeressence() + amountOfTickets);
                    player.sendMessage(essenceMessage);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Slayer essence has been added to your pouch!");
                } else {
                    player.getInventory().addDropIfFull(3576, amountOfTickets);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Slayer essence has been added to your Inventory!");
                }
                givePoints(player.getSlayer().getLastTask().getTaskMaster());
            }


            if (player.getSlayer().getSlayerMaster() == SlayerMaster.BEAST_MASTER || player.getSlayer().getSlayerMaster() == SlayerMaster.BEAST_MASTER_X || player.getSlayer().getSlayerMaster() == SlayerMaster.SPECTRAL_BEAST) {
                String essenceMessage = "<col=AF70C3><shad=0>You have received " + amountOfTickets + "X Beast essence for completing your task";

                if (player.getNodesUnlocked() != null) {
                    if (player.getSkillTree().isNodeUnlocked(Node.ESSENCE_REAVER)) {
                        amountOfTickets *= 1.10;
                    }
                }
                if (player.getMembershipTier().isMember()) {
                    amountOfTickets *= player.getMembershipTier().getExtra_points_tickets();
                }

                if (player.getInventory().contains(18015)) {
                    player.setBeastEssence(player.getBeastEssence() + amountOfTickets);
                    player.sendMessage(essenceMessage);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Beast essence has been added to your pouch!");
                } else {
                    player.getInventory().addDropIfFull(6466, amountOfTickets);
                    player.sendMessage("<col=AF70C3><shad=0>" + amountOfTickets + "X Beast essence has been added to your Inventory!");
                }
                givePoints(player.getSlayer().getLastTask().getTaskMaster());
            }


        }

        PlayerPanel.refreshPanel(player);
    }

    public void checkPouch(Item item) {
        if (player.getInventory().contains(18015, 1)) {
            if (player.pouchMessagesEnabled) {
                player.msgFancyPurp(item.getAmount() + "X " + item.getDefinition().getName() + " has been added to your Pouch!");
            }
            switch (item.getId()){
                case 3576:
                    player.setSlayeressence(player.getSlayeressence() + item.getAmount());
                    break;
                case 3502:
                    player.setCorruptEssence(player.getCorruptEssence() + item.getAmount());
                    break;
                case 2064:
                    player.setSpectralEssence(player.getSpectralEssence() + item.getAmount());
                    break;
                case 6466:
                    player.setBeastEssence(player.getBeastEssence() + item.getAmount());
                    break;
                case 2699:
                    player.setEnchantedEssence(player.getEnchantedEssence() + item.getAmount());
                    break;
            }
        } else {
            if (player.getInventory().canHold(item)) {
                if (player.pouchMessagesEnabled) {
                    player.msgFancyPurp(item.getAmount() + "X " + item.getDefinition().getName() + " has been added to your Inventory!");
                }
                player.getInventory().add(item);
            } else {
                player.depositItemBank(item);
                if (player.pouchMessagesEnabled) {
                    player.msgFancyPurp("Your inventory is full, so your Essence went to your bank.");
                }
            }
        }
    }


        @SuppressWarnings("incomplete-switch")
    public void givePoints(SlayerMaster master) {

        if (player.getSlayer().getSlayerMaster() == SlayerMaster.BEGINNER_SLAYER) {

                Player p = player;
            int streakBeginner = player.getSlayer().getTaskStreak();
            String easyMessage = "You received Bonus Essence due to your " + streakBeginner + "X Easy Task Streak!";

            if (p.getSlayer().getTaskStreak() % 10 == 0) {
                p.msgFancyPurp("You received Bonus Essence for completing 10 tasks in a row!");
                checkPouch(new Item(3576, 75));
            }

            switch (streakBeginner){
                case 10:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 75));
                    break;
                case 25:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 250));
                    break;
                case 50:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 500));
                    break;
                case 100:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 750));
                    break;
                case 150:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 1000));
                    break;
                case 200:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 1200));
                    break;
                case 300:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 1350));
                    break;
                case 400:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 1400));
                    break;
                case 500:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 1500));
                    break;
                case 750:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 3500));
                    break;
                case 1000:
                    p.msgFancyPurp(easyMessage);
                    checkPouch(new Item(3576, 6500));
                    break;

            }
        }

            if (player.getSlayer().getSlayerMaster() == SlayerMaster.MEDIUM_SLAYER) {

                Player p = player;
                int streakMedium = player.getSlayer().getTaskStreakBeastI();
                String mediumMessage = "You received Bonus Essence due to your " + streakMedium + "X Medium Task Streak!";

                if (p.getSlayer().getTaskStreakBeastI() % 10 == 0) {
                    p.msgFancyPurp("You received Bonus Essence for completing 10 tasks in a row!");
                    checkPouch(new Item(3576, 75));
                }

                switch (streakMedium){
                    case 10:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 75));
                        break;
                    case 25:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 250));
                        break;
                    case 50:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 500));
                        break;
                    case 100:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 750));
                        break;
                    case 150:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 1000));
                        break;
                    case 200:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 1200));
                        break;
                    case 300:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 1350));
                        break;
                    case 400:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 1400));
                        break;
                    case 500:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 1500));
                        break;
                    case 750:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 3500));
                        break;
                    case 1000:
                        p.msgFancyPurp(mediumMessage);
                        checkPouch(new Item(3576, 6500));
                        break;

                }
            }

            if (player.getSlayer().getSlayerMaster() == SlayerMaster.ELITE_SLAYER) {
                Player p = player;
                int streakElite = player.getSlayer().getTaskStreakBeastX();
                String eliteMessage = "You received Bonus Essence due to your " + streakElite + "X Elite Task Streak!";

                if (p.getSlayer().getTaskStreakBeastX() % 10 == 0) {
                    p.msgFancyPurp("You received Bonus Essence for completing 10 tasks in a row!");
                    checkPouch(new Item(3576, 75));
                }

                switch (streakElite){
                    case 10:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 75));
                        break;
                    case 25:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 250));
                        break;
                    case 50:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 500));
                        break;
                    case 100:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 750));
                        break;
                    case 150:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 1000));
                        break;
                    case 200:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 1200));
                        break;
                    case 300:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 1350));
                        break;
                    case 400:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 1400));
                        break;
                    case 500:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 1500));
                        break;
                    case 750:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 3500));
                        break;
                    case 1000:
                        p.msgFancyPurp(eliteMessage);
                        checkPouch(new Item(3576, 6500));
                        break;

                }
            }
            if (player.getSlayer().getSlayerMaster() == SlayerMaster.ENCHANTED_MASTER) {


                Player p = player;
                int enchantedTasks = player.getEnchantedTasksDone();
                String enchantedMessage = "You received Bonus Essence due to completing " + enchantedTasks + "X Enchanted Tasks!";
                switch (enchantedTasks){
                    case 10:
                        p.msgFancyPurp(enchantedMessage);
                        checkPouch(new Item(2699, 20));
                        break;
                    case 25:
                        p.msgFancyPurp(enchantedMessage);
                        checkPouch(new Item(2699, 30));
                        if (!p.unlockedWolfCamp) {
                            p.setUnlockedWolfCamp(true);
                            p.msgFancyPurp("You've unlocked Fast Travel access to the Wolf Camp!");
                        }
                        break;
                    case 50:
                        p.msgFancyPurp(enchantedMessage);
                        checkPouch(new Item(2699, 40));
                        break;
                    case 100:
                        if (!p.unlockedDemonCamp) {
                            p.setUnlockedDemonCamp(true);
                            p.msgFancyPurp("You've unlocked Fast Travel access to the Demon Camp!");
                        }
                        p.msgFancyPurp(enchantedMessage);
                        checkPouch(new Item(2699, 50));
                        break;
                    case 150:
                        if (!p.unlockedTitanCamp) {
                            p.setUnlockedTitanCamp(true);
                            p.msgFancyPurp("You've unlocked Fast Travel access to the Titan Camp!");
                        }
                        p.msgFancyPurp(enchantedMessage);
                        checkPouch(new Item(2699, 75));
                        break;
                    case 200:
                        p.msgFancyPurp(enchantedMessage);
                        checkPouch(new Item(2699, 100));
                        break;
                    case 300:
                        p.msgFancyPurp(enchantedMessage);
                        checkPouch(new Item(2699, 150));
                        break;
                }
            }


                if (player.getSlayer().getSlayerMaster() == SlayerMaster.CORRUPT_SLAYER) {
                Player p = player;
                int streakCorrupt = player.getSlayer().getDragonstreak();
                String corruptMessage = "You received Bonus Essence due to your " + streakCorrupt + "X Corrupt Task Streak!";

                    if (p.getSlayer().getDragonstreak() % 10 == 0) {
                        p.msgFancyPurp("You received Bonus Essence for completing 10 tasks in a row!");
                        checkPouch(new Item(3502, 25));
                    }

                switch (streakCorrupt){
                    case 10:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 40));
                        break;
                    case 25:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 250));
                        break;
                    case 50:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 500));
                        break;
                    case 100:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 750));
                        break;
                    case 150:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 1000));
                        break;
                    case 200:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 1200));
                        break;
                    case 300:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 1350));
                        break;
                    case 400:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 1400));
                        break;
                    case 500:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 1500));
                        break;
                    case 750:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 3500));
                        break;
                    case 1000:
                        p.msgFancyPurp(corruptMessage);
                        checkPouch(new Item(3502, 6500));
                        break;

                }
            }

            if (player.getSlayer().getSlayerMaster() == SlayerMaster.SPECTRAL_SLAYER) {
                Player p = player;
                int streakSpectral = player.getSlayer().getGodstreak();
                String SpectralMessage = "You received Bonus Essence due to your " + streakSpectral + "X Spectral Task Streak!";

                if (p.getSlayer().getGodstreak() % 10 == 0) {
                    p.msgFancyPurp("You received Bonus Essence for completing 10 tasks in a row!");
                    checkPouch(new Item(2064, 25));
                }

                switch (streakSpectral){
                    case 10:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 40));
                        break;
                    case 25:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 250));
                        break;
                    case 50:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 500));
                        break;
                    case 100:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 750));
                        break;
                    case 150:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 1000));
                        break;
                    case 200:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 1200));
                        break;
                    case 300:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 1350));
                        break;
                    case 400:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 1400));
                        break;
                    case 500:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 1500));
                        break;
                    case 750:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 3500));
                        break;
                    case 1000:
                        p.msgFancyPurp(SpectralMessage);
                        checkPouch(new Item(2064, 6500));
                        break;

                }
            }




            if (player.getSlayer().getSlayerMaster() == SlayerMaster.BEAST_MASTER || slayerTask.getTaskMaster() == SlayerMaster.BEAST_MASTER_X || slayerTask.getTaskMaster() == SlayerMaster.SPECTRAL_BEAST) {
                Player p = player;
                int streakBeast = player.getSlayer().getGodstreak();
                String beastMessage = "You received Bonus Essence due to your " + streakBeast + "X Beast Task Streak!";

                if (p.getSlayer().getGodstreak() % 10 == 0) {
                    p.msgFancyPurp("You received Bonus Essence for completing 10 tasks in a row!");
                    checkPouch(new Item(6466, 25));
                }
                switch (streakBeast){
                    case 10:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 40));
                        break;
                    case 25:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 250));
                        break;
                    case 50:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 500));
                        break;
                    case 100:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 750));
                        break;
                    case 150:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 1000));
                        break;
                    case 200:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 1200));
                        break;
                    case 300:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 1350));
                        break;
                    case 400:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 1400));
                        break;
                    case 500:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 1500));
                        break;
                    case 750:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 2500));
                        break;
                    case 1000:
                        p.msgFancyPurp(beastMessage);
                        checkPouch(new Item(6466, 4000));
                        break;


                }


            }


        // interm
        PlayerPanel.refreshPanel(player);
    }

        public boolean assignDuoSlayerTask() {
        player.getPacketSender().sendInterfaceRemoval();
        if (player.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
            player.getPacketSender().sendMessage("You already have a task.");
            return false;
        }
        Player partner = World.getPlayerByName(duoPartner);
        if (partner == null) {
            player.getPacketSender().sendMessage("");
            player.getPacketSender().sendMessage("You can only get a new task when your duo partner is online.");
            return false;
        }
        if (partner.getSlayer().getDuoPartner() == null || !partner.getSlayer().getDuoPartner().equals(player.getUsername())) {
            resetDuo(player, null);
            return false;
        }
        if (partner.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK) {
            player.getPacketSender().sendMessage("Your partner already has a task.");
            return false;
        }
        if (partner.getSlayer().getSlayerMaster() != player.getSlayer().getSlayerMaster()) {
            player.getPacketSender().sendMessage("You and your partner need to have the same Slayer Master.");
            return false;
        }
        if (partner.getInterfaceId() > 0) {
            player.getPacketSender().sendMessage("Your partner must close all their open interfaces.");
            return false;
        }
        return true;
    }

    public static boolean checkDuoSlayer(Player p, boolean login) {
        if (p.getSlayer().getDuoPartner() == null) {
            return false;
        }
        Player partner = World.getPlayerByName(p.getSlayer().getDuoPartner());
        if (partner == null) {
            return false;
        }
        if (partner.getSlayer().getDuoPartner() == null
                || !partner.getSlayer().getDuoPartner().equals(p.getUsername())) {
            resetDuo(p, null);
            return false;
        }
        if (partner.getSlayer().getSlayerMaster() != p.getSlayer().getSlayerMaster()) {
            resetDuo(p, partner);
            return false;
        }
        if (login) {
            p.getSlayer().setSlayerTask(partner.getSlayer().getSlayerTask());
            p.getSlayer().setAmountToSlay(partner.getSlayer().getAmountToSlay());
        }
        return true;
    }

    public static void resetDuo(Player player, Player partner) {
        if (partner != null) {
            if (partner.getSlayer().getDuoPartner() != null
                    && partner.getSlayer().getDuoPartner().equals(player.getUsername())) {
                partner.getSlayer().setDuoPartner(null);
                partner.getPacketSender().sendMessage("Your Slayer duo team has been disbanded.");
                PlayerPanel.refreshPanel(partner);
            }
        }
        player.getSlayer().setDuoPartner(null);
        player.getPacketSender().sendMessage("Your Slayer duo team has been disbanded.");
        PlayerPanel.refreshPanel(player);
    }

    public void handleInvitation(boolean accept) {
        if (duoInvitation != null) {
            Player inviteOwner = World.getPlayerByName(duoInvitation);
            if (inviteOwner != null) {
                if (accept) {
                    if (duoPartner != null) {
                        player.getPacketSender().sendMessage("You already have a Slayer duo partner.");
                        inviteOwner.getPacketSender().sendMessage("" + player.getUsername() + " already has a Slayer duo partner.");
                        return;
                    }
                    inviteOwner.getPacketSender().sendMessage("" + player.getUsername() + " has joined your duo Slayer team.").sendMessage("Seek respective Slayer master for a task.");
                    inviteOwner.getSlayer().setDuoPartner(player.getUsername());
                    PlayerPanel.refreshPanel(inviteOwner);
                    player.getPacketSender().sendMessage("You have joined " + inviteOwner.getUsername() + "'s duo Slayer team.");
                    player.getSlayer().setDuoPartner(inviteOwner.getUsername());
                    PlayerPanel.refreshPanel(player);
                } else {
                    player.getPacketSender().sendMessage("You have declined the invitation.");
                    inviteOwner.getPacketSender().sendMessage("" + player.getUsername() + " has declined your invitation.");
                }
            } else
                player.getPacketSender().sendMessage("Failed to handle the invitation.");
        }
    }

    public void handleSlayerRingTP2(int itemId) {
        if (!player.getClickDelay().elapsed(4500))
            return;
        if (player.getMovementQueue().isLockMovement())
            return;
        SlayerTasks task = getSlayerTask();
        if (task == SlayerTasks.NO_TASK)
            return;
        Position slayerTaskPos = new Position(task.getTaskPosition().getX(), task.getTaskPosition().getY(),
                task.getTaskPosition().getZ());
        if (!TeleportHandler.checkReqs(player, slayerTaskPos))
            return;
        TeleportHandler.teleportPlayer(player, slayerTaskPos, TeleportType.ANCIENT);
    }

    public void handleSlayerRingTP(int itemId) {
        if (!player.getClickDelay().elapsed(4500))
            return;
        if (player.getMovementQueue().isLockMovement())
            return;
        SlayerTasks task = getSlayerTask();
        if (task == SlayerTasks.NO_TASK) {
            if (player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER && player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER_X && player.getSlayer().getSlayerMaster() != SlayerMaster.ENCHANTED_MASTER && player.getSlayer().getSlayerMaster() != SlayerMaster.SPECTRAL_BEAST) {
                Position position15 = new Position(2718, 3113, 0);
                TeleportHandler.teleportPlayer(player, position15, TeleportType.NORMAL);
            }
            if (player.getSlayer().getSlayerMaster()  == SlayerMaster.BEAST_MASTER ||  player.getSlayer().getSlayerMaster() == SlayerMaster.BEAST_MASTER_X || player.getSlayer().getSlayerMaster() == SlayerMaster.SPECTRAL_BEAST ) {
                TeleportHandler.teleportPlayer(player, new Position(3167, 3530,0), TeleportType.ANCIENT);
            }
            if (player.getSlayer().getSlayerMaster()  == SlayerMaster.ENCHANTED_MASTER) {
                TeleportHandler.teleportPlayer(player, new Position(2693, 3838,0), TeleportType.ANCIENT);
            }
            return;
        }
        Position slayerTaskPos = new Position(task.getTaskPosition().getX(), task.getTaskPosition().getY(), task.getTaskPosition().getZ());
        if (!TeleportHandler.checkReqs(player, slayerTaskPos)) {
            return;
        }

        if (player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER && player.getSlayer().getSlayerMaster() != SlayerMaster.BEAST_MASTER_X && player.getSlayer().getSlayerMaster() != SlayerMaster.ENCHANTED_MASTER && player.getSlayer().getSlayerMaster() != SlayerMaster.SPECTRAL_BEAST) {
            TeleportHandler.teleportPlayer(player, slayerTaskPos, TeleportType.ANCIENT);
        }
        if (player.getSlayer().slayerMaster == SlayerMaster.BEAST_MASTER || slayerTask.getTaskMaster() == SlayerMaster.BEAST_MASTER_X || slayerTask.getTaskMaster() == SlayerMaster.SPECTRAL_BEAST) {
            TeleportHandler.teleportPlayer(player, new Position(3167, 3530,0), TeleportType.ANCIENT);
        }
        if (player.getSlayer().getSlayerMaster()  == SlayerMaster.ENCHANTED_MASTER) {
            TeleportHandler.teleportPlayer(player, new Position(2693, 3838,0), TeleportType.ANCIENT);
        }
    }

    public int getAmountToSlay() {
        return this.amountToSlay;
    }

    public Slayer setAmountToSlay(int amountToSlay) {
        this.amountToSlay = amountToSlay;
        return this;
    }

    public int getTaskStreak() {
        return this.taskStreak;
    }

    public int getTaskStreakBeastX() {
        return this.taskStreakX;
    }
    public Slayer setTaskStreakX(int taskStreakX) {
        this.taskStreakX = taskStreakX;
        return this;
    }
    public int getTaskStreakBeastI() {
        return this.taskStreakI;
    }
    public Slayer setTaskStreakI(int taskStreakI) {
        this.taskStreakI = taskStreakI;
        return this;
    }

    public Slayer setTaskStreak(int taskStreak) {
        this.taskStreak = taskStreak;
        return this;
    }

    public int getAstralstreak() {
        return this.astralstreak;
    }
    public Slayer setAstralstreak(int astralstreak) {
        this.astralstreak = astralstreak;
        return this;
    }

    public int getDragonstreak() {
        return this.dragonstreak;
    }
    public Slayer setDragonstreak(int dragonstreak) {
        this.dragonstreak = dragonstreak;
        return this;
    }

    public int getGodstreak() {
        return this.godstreak;
    }
    public Slayer setGodstreak(int godstreak) {
        this.godstreak = godstreak;
        return this;
    }


    public SlayerTasks getLastTask() {
        return this.lastTask;
    }

    public void setLastTask(SlayerTasks lastTask) {
        this.lastTask = lastTask;
    }

    public boolean doubleSlayerXP = false;

    public Slayer setDuoPartner(String duoPartner) {
        this.duoPartner = duoPartner;
        return this;
    }

    public String getDuoPartner() {
        return duoPartner;
    }

    public SlayerTasks getSlayerTask() {
        return slayerTask;
    }

    public Slayer setSlayerTask(SlayerTasks slayerTask) {
        this.slayerTask = slayerTask;
        return this;
    }

    public SlayerMaster getSlayerMaster() {
        return slayerMaster;
    }


    public void setSlayerMaster(SlayerMaster master) {
        this.slayerMaster = master;
    }

    public void setDuoInvitation(String player) {
        this.duoInvitation = player;
    }

    // TODO remove points related stuff
    public static boolean handleRewardsInterface(Player player, int button) {
        if (player.getInterfaceId() == 36000) {
            switch (button) {
                case -29534:
                    player.getPacketSender().sendInterfaceRemoval();
                    break;
                case -29531:
                    ShopManager.getShops().get(47).open(player);
                    break;
            }
            player.getPacketSender().sendString(36030,
                    "Current Points:   " + player.getPointsHandler().getSlayerPoints());
            return true;
        }
        return false;
    }
}
