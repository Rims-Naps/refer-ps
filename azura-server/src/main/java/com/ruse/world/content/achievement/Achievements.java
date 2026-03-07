package com.ruse.world.content.achievement;

import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.RewardHandler;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Achievements {

    public static void doProgress(Player player, Achievement achievement) {
        doProgress(player, achievement, 1);
    }

    public static void doProgress(Player player, Achievement achievement, int amount) {
        if (achievement.getRequirement() == null || achievement.getRequirement().isAble(player)) {
            int currentAmount = player.getAchievements().getAmountRemaining(achievement.getDifficulty().ordinal(), achievement.getId());
            int tier = achievement.getDifficulty().ordinal();
            if (currentAmount < achievement.getAmount() && !player.getAchievements().isComplete(achievement.getDifficulty().ordinal(), achievement.getId())) {
                player.getAchievements().setAmountRemaining(tier, achievement.getId(), currentAmount + amount);
                if ((currentAmount + amount) >= achievement.getAmount()) {
                    String name = achievement.name().replaceAll("_", " ");
                    player.getPacketSender().sendMessage(
                            "<col=0XAF70C3><shad=0>[ACHIEVEMENT] <col=0XAF70C3>" + Misc.capitalizeJustFirst(achievement.name().replaceAll("_", " ") + " <col=0XAF70C3>completed, claim your reward!<col=0XAF70C3><shad=0>"));


                        World.sendNewsMessage("@red@<shad=0>" + Misc.capitalizeJustFirst(player.getUsername())
                                + " <col=0><col=0XAF70C3>completed the achievement @red@<shad=0>" + Misc.capitalizeJustFirst(achievement.name().replaceAll("_", " ") + "<col=0><col=0XAF70C3>!"));

                }
            }
        }
    }

    public static void claimReward(Player player, Achievement achievement, boolean showText) {
        if (achievement.getRequirement() == null || achievement.getRequirement().isAble(player)) {
            if (!player.getAchievements().isComplete(achievement.getDifficulty().ordinal(), achievement.getId())) {
                int currentAmount = player.getAchievements().getAmountRemaining(achievement.getDifficulty().ordinal(), achievement.getId());
                int tier = achievement.getDifficulty().ordinal();
                if (currentAmount >= achievement.getAmount()) {
                    if (achievement.getRewards() != null) {
                        player.getAchievements().setComplete(tier, achievement.getId(), true);

                        for (Item item : achievement.getRewards()) {
                            RewardHandler.handleItemReward(player, item);
                        }

                        // Add achievement points or other rewards
                        for (int x = 0; x < achievement.getPoints().length; x++) {
                            if (achievement.getPoints()[x][0].equalsIgnoreCase("achievement")) {
                                player.getPointsHandler().setAchievementPoints(Integer.parseInt(achievement.getPoints()[x][1]), true);
                            } else if (achievement.getPoints()[x][0].equalsIgnoreCase("skill")) {
                                player.getPointsHandler().setSkillingPoints(Integer.parseInt(achievement.getPoints()[x][1]), true);
                            } else if (achievement.getPoints()[x][0].equalsIgnoreCase("voting")) {
                                player.getPointsHandler().setVotingPoints(Integer.parseInt(achievement.getPoints()[x][1]), true);
                            } else if (achievement.getPoints()[x][0].equalsIgnoreCase("slayer")) {
                                player.getPointsHandler().setSlayerPoints(Integer.parseInt(achievement.getPoints()[x][1]), true);
                            }
                        }
                    }
                } else {
                    if (showText) {
                        player.msgRed("You haven't completed this achievement yet.");
                    }
                }
            } else {
                if (showText) {
                    player.msgRed("You have already claimed this reward.");
                }
            }
        }
    }



    public static void reset(Player player, Achievement achievement) {
        if (achievement.getRequirement() == null || achievement.getRequirement().isAble(player)) {
            if (!player.getAchievements().isComplete(achievement.getDifficulty().ordinal(), achievement.getId())) {
                player.getAchievements().setAmountRemaining(achievement.getDifficulty().ordinal(), achievement.getId(), 0);
            }
        }
    }


    public static int getMaximumAchievements() {
        return Achievement.values().length;
    }

    public enum Achievement {


        // Kills achievements
        KILL_10_NPCS(1, 3430, AchievementType.GENERAL, "Kill 10 Npcs", 10, new String[][]{}, new Item(17582, 2), new Item(10258, 5)),
        KILL_50_NPCS(2, 3430, AchievementType.GENERAL, "Kill 50 Npcs", 50, new String[][]{}, new Item(17584, 2), new Item(10258, 10)),
        KILL_250_NPCS(3, 3430, AchievementType.GENERAL, "Kill 250 Npcs", 250, new String[][]{}, new Item(17586, 2), new Item(10258, 20)),
        KILL_500_NPCS(4, 3430, AchievementType.GENERAL, "Kill 500 Npcs", 500, new String[][]{}, new Item(17582, 5), new Item(17584, 5), new Item(17586, 5)),
        KILL_1000_NPCS(5, 3430, AchievementType.GENERAL, "Kill 1000 Npcs", 1000, new String[][]{}, new Item(10946, 1), new Item(20104, 20)),
        KILL_2500_NPCS(6, 3430, AchievementType.GENERAL, "Kill 2500 Npcs", 2500, new String[][]{}, new Item(23173, 1),new Item(3580, 2)),
        KILL_5000_NPCS(7, 3430, AchievementType.GENERAL, "Kill 5000 Npcs", 5000, new String[][]{}, new Item(20106, 100),new Item(10946, 1),new Item(15668, 1)),
        KILL_7500_NPCS(8, 3430, AchievementType.GENERAL, "Kill 7000 Npcs", 7500, new String[][]{}, new Item(23171, 2), new Item(3580, 5), new Item(10946, 1)),
        KILL_10000_NPCS(9, 3430, AchievementType.GENERAL, "Kill 10000 Npcs", 10000, new String[][]{}, new Item(15670, 3), new Item(23172, 1),new Item(17130, 2)),
        KILL_15000_NPCS(10, 3430, AchievementType.GENERAL, "Kill 15000 Npcs", 15000, new String[][]{}, new Item(17582, 35), new Item(17584, 35), new Item(17586, 35)),
        KILL_30000_NPCS(11, 3430, AchievementType.GENERAL, "Kill 30000 Npcs", 30000, new String[][]{}, new Item(3580, 10), new Item(3582, 3),new Item(23173, 3)),
        KILL_50000_NPCS(12, 3430, AchievementType.GENERAL, "Kill 50000 Npcs", 50000, new String[][]{}, new Item(23172, 3), new Item(10946, 4),new Item(3578, 1)),
        KILL_75000_NPCS(13, 3430, AchievementType.GENERAL, "Kill 75000 Npcs", 75000, new String[][]{}, new Item(20108, 100), new Item(20109, 100),new Item(15668, 3)),
        KILL_100000_NPCS(14, 3430, AchievementType.GENERAL, "Kill 100000 Npcs", 100000, new String[][]{}, new Item(23057, 1),new Item(15670, 5),new Item(15669, 5)),
        KILL_150000_NPCS(15, 3430, AchievementType.GENERAL, "Kill 150000 Npcs", 150000, new String[][]{}, new Item(15671, 3),new Item(3582, 3),new Item(3578, 1)),
        KILL_200000_NPCS(16, 3430, AchievementType.GENERAL, "Kill 200000 Npcs", 200000, new String[][]{}, new Item(15668, 5),new Item(17130, 2),new Item(20109, 300)),
        KILL_250000_NPCS(17, 3430, AchievementType.GENERAL, "Kill 250000 Npcs", 250000, new String[][]{}, new Item(3578, 1),new Item(15668, 5), new Item(10946, 3)),
        KILL_300000_NPCS(18, 3430, AchievementType.GENERAL, "Kill 300000 Npcs", 300000, new String[][]{}, new Item(23172, 2),new Item(15671, 3),new Item(23173, 2)),
        KILL_400000_NPCS(19, 3430, AchievementType.GENERAL, "Kill 400000 Npcs", 400000, new String[][]{}, new Item(23058, 2),new Item(20109, 200),new Item(17130, 5)),
        KILL_500000_NPCS(20, 3430, AchievementType.GENERAL, "Kill 500000 Npcs", 500000, new String[][]{}, new Item(15668, 15),new Item(15671, 5),new Item(23172, 3)),

        VOTE_5_TIMES(21, 3430, AchievementType.GENERAL, "Vote 5 Times", 5, new String[][]{}, new Item(23020, 1),new Item(15670, 1),new Item(15666, 1)),
        VOTE_25_TIMES(22, 3430, AchievementType.GENERAL, "Vote 25 Times", 25, new String[][]{}, new Item(23020, 3),new Item(10946, 2),new Item(23171, 1)),
        VOTE_50_TIMES(23, 3430, AchievementType.GENERAL, "Vote 50 Times", 50, new String[][]{}, new Item(23020, 5),new Item(15670, 3),new Item(23172, 1)),
        VOTE_100_TIMES(24, 3430, AchievementType.GENERAL, "Vote 100 Times", 100, new String[][]{}, new Item(23020, 10),new Item(15671, 3),new Item(17129, 1)),
        VOTE_250_TIMES(25, 3430, AchievementType.GENERAL, "Vote 250 Times", 250, new String[][]{}, new Item(23020, 15),new Item(15671, 3),new Item(17129, 3)),
        VOTE_500_TIMES(26, 3430, AchievementType.GENERAL, "Vote 500 Times", 500, new String[][]{}, new Item(23020, 25),new Item(19118, 2),new Item(2624, 1)),
        VOTE_1000_TIMES(27, 3430, AchievementType.GENERAL, "Vote 1k Times", 1000, new String[][]{}, new Item(23020, 50),new Item(754, 3),new Item(17125, 1)),


        //SLAYER
        // Easy task achievements
        COMPLETE_5_EASY_TASKS(1, 3429, AchievementType.SLAYER, "Complete 5 Easy Tasks", 5, new String[][]{}, new Item(3576, 50),new Item(10258, 5)),
        COMPLETE_25_EASY_TASKS(2, 3429, AchievementType.SLAYER, "Complete 25 Easy Tasks", 25, new String[][]{}, new Item(3576, 100),new Item(17127, 5),new Item(15667, 3)),
        COMPLETE_50_EASY_TASKS(3, 3429, AchievementType.SLAYER, "Complete 50 Easy Tasks", 50, new String[][]{}, new Item(9719, 5),new Item(17127, 10),new Item(10946, 1)),
        COMPLETE_100_EASY_TASKS(4, 3429, AchievementType.SLAYER, "Complete 100 Easy Tasks", 100, new String[][]{}, new Item(3576, 200)),
        COMPLETE_150_EASY_TASKS(5, 3429, AchievementType.SLAYER, "Complete 150 Easy Tasks", 150, new String[][]{}, new Item(20105, 25),new Item(9719, 10)),
        COMPLETE_250_EASY_TASKS(6, 3429, AchievementType.SLAYER, "Complete 250 Easy Tasks", 250, new String[][]{}, new Item(10946, 1),new Item(15670, 1)),
        COMPLETE_375_EASY_TASKS(7, 3429, AchievementType.SLAYER, "Complete 375 Easy Tasks", 375, new String[][]{}, new Item(3576, 250),new Item(17128, 10),new Item(9719, 5)),
        COMPLETE_500_EASY_TASKS(8, 3429, AchievementType.SLAYER, "Complete 500 Easy Tasks", 500, new String[][]{}, new Item(15668, 1),new Item(15667, 15)),
        COMPLETE_650_EASY_TASKS(9, 3429, AchievementType.SLAYER, "Complete 650 Easy Tasks", 650, new String[][]{}, new Item(15670, 5),new Item(10946, 1),new Item(15671, 1)),
        COMPLETE_800_EASY_TASKS(10, 3429, AchievementType.SLAYER, "Complete 800 Easy Tasks", 800, new String[][]{}, new Item(15668, 5),new Item(17130, 1)),
        COMPLETE_1000_EASY_TASKS(11, 3429, AchievementType.SLAYER, "Complete 1000 Easy Tasks", 1000, new String[][]{}, new Item(23058, 1),new Item(20107, 100)),

        // Medium task achievements
        COMPLETE_5_MEDIUM_TASKS(12, 3429, AchievementType.SLAYER, "Complete 5 Medium Tasks", 5, new String[][]{}, new Item(3576, 150),new Item(9719, 5)),
        COMPLETE_25_MEDIUM_TASKS(13, 3429, AchievementType.SLAYER, "Complete 25 Medium Tasks", 25, new String[][]{}, new Item(3576, 250),new Item(17128, 5)),
        COMPLETE_50_MEDIUM_TASKS(14, 3429, AchievementType.SLAYER, "Complete 50 Medium Tasks", 50, new String[][]{}, new Item(15670, 1),new Item(15667, 10)),
        COMPLETE_100_MEDIUM_TASKS(15, 3429, AchievementType.SLAYER, "Complete 100 Medium Tasks", 100, new String[][]{}, new Item(10946, 1),new Item(3576, 300)),
        COMPLETE_150_MEDIUM_TASKS(16, 3429, AchievementType.SLAYER, "Complete 150 Medium Tasks", 150, new String[][]{}, new Item(20106, 20),new Item(17128, 5)),
        COMPLETE_250_MEDIUM_TASKS(17, 3429, AchievementType.SLAYER, "Complete 250 Medium Tasks", 250, new String[][]{}, new Item(15668, 2),new Item(9719, 20)),
        COMPLETE_375_MEDIUM_TASKS(18, 3429, AchievementType.SLAYER, "Complete 375 Medium Tasks", 375, new String[][]{}, new Item(15671, 2),new Item(17128, 10)),
        COMPLETE_500_MEDIUM_TASKS(19, 3429, AchievementType.SLAYER, "Complete 500 Medium Tasks", 500, new String[][]{}, new Item(23172, 1),new Item(3576, 1500),new Item(20068, 5)),
        COMPLETE_650_MEDIUM_TASKS(20, 3429, AchievementType.SLAYER, "Complete 650 Medium Tasks", 650, new String[][]{}, new Item(15668, 5),new Item(3576, 2500)),
        COMPLETE_800_MEDIUM_TASKS(21, 3429, AchievementType.SLAYER, "Complete 800 Medium Tasks", 800, new String[][]{}, new Item(15670, 5),new Item(20068, 15)),
        COMPLETE_1000_MEDIUM_TASKS(22, 3429, AchievementType.SLAYER, "Complete 1000 Medium Tasks", 1000, new String[][]{}, new Item(23172, 1),new Item(23058, 1),new Item(15670, 5)),

        // Elite task achievements
        COMPLETE_5_ELITE_TASKS(23, 3429, AchievementType.SLAYER, "Complete 5 Elite Tasks", 5, new String[][]{}, new Item(3576, 1000),new Item(20068, 10)),
        COMPLETE_25_ELITE_TASKS(24, 3429, AchievementType.SLAYER, "Complete 25 Elite Tasks", 25, new String[][]{}, new Item(15667, 20),new Item(3576, 2000)),
        COMPLETE_50_ELITE_TASKS(25, 3429, AchievementType.SLAYER, "Complete 50 Elite Tasks", 50, new String[][]{}, new Item(23171, 1),new Item(7307, 300)),
        COMPLETE_100_ELITE_TASKS(26, 3429, AchievementType.SLAYER, "Complete 100 Elite Tasks", 100, new String[][]{}, new Item(23057, 1),new Item(20068, 15)),
        COMPLETE_150_ELITE_TASKS(27, 3429, AchievementType.SLAYER, "Complete 150 Elite Tasks", 150, new String[][]{}, new Item(3576, 3000),new Item(15668, 3)),
        COMPLETE_250_ELITE_TASKS(28, 3429, AchievementType.SLAYER, "Complete 250 Elite Tasks", 250, new String[][]{}, new Item(15670, 3),new Item(20068, 10), new Item(17130, 1)),
        COMPLETE_375_ELITE_TASKS(29, 3429, AchievementType.SLAYER, "Complete 375 Elite Tasks", 375, new String[][]{}, new Item(20108, 125),new Item(3576, 5000)),
        COMPLETE_500_ELITE_TASKS(30, 3429, AchievementType.SLAYER, "Complete 500 Elite Tasks", 500, new String[][]{}, new Item(15668, 5),new Item(2706, 10)),
        COMPLETE_650_ELITE_TASKS(31, 3429, AchievementType.SLAYER, "Complete 650 Elite Tasks", 650, new String[][]{}, new Item(20068, 30),new Item(7307, 500),new Item(17130, 2)),
        COMPLETE_800_ELITE_TASKS(32, 3429, AchievementType.SLAYER, "Complete 800 Elite Tasks", 800, new String[][]{}, new Item(15671, 2),new Item(3576, 5000)),
        COMPLETE_1000_ELITE_TASKS(33, 3429, AchievementType.SLAYER, "Complete 1000 Elite Tasks", 1000, new String[][]{}, new Item(2706, 20),new Item(23172, 3),new Item(20109, 200)),

        // Beast Hunter task achievements
        COMPLETE_2_BEAST_TASKS(34, 3429, AchievementType.SLAYER, "Complete 2 Beast Tasks", 2, new String[][]{}, new Item(6466, 1000),new Item(23172, 1),new Item(10946, 1)),
        COMPLETE_5_BEAST_TASKS(35, 3429, AchievementType.SLAYER, "Complete 5 Beast Tasks", 5, new String[][]{}, new Item(1307, 15),new Item(1304, 15),new Item(3576, 1500)),
        COMPLETE_10_BEAST_TASKS(36, 3429, AchievementType.SLAYER, "Complete 10 Beast Tasks", 10, new String[][]{}, new Item(995, 10000),new Item(1448, 3),new Item(19062, 250)),
        COMPLETE_20_BEAST_TASKS(37, 3429, AchievementType.SLAYER, "Complete 20 Beast Tasks", 20, new String[][]{}, new Item(6466, 3000),new Item(457, 1),new Item(15671, 3)),
        COMPLETE_40_BEAST_TASKS(38, 3429, AchievementType.SLAYER, "Complete 40 Beast Tasks", 40, new String[][]{}, new Item(3578, 1),new Item(3580, 15),new Item(3582, 10)),
        COMPLETE_60_BEAST_TASKS(39, 3429, AchievementType.SLAYER, "Complete 60 Beast Tasks", 60, new String[][]{}, new Item(457, 2),new Item(995, 20000),new Item(10946, 3)),
        COMPLETE_80_BEAST_TASKS(40, 3429, AchievementType.SLAYER, "Complete 80 Beast Tasks", 80, new String[][]{}, new Item(17129, 5),new Item(23172, 2),new Item(1448, 5)),
        COMPLETE_100_BEAST_TASKS(41, 3429, AchievementType.SLAYER, "Complete 100 Beast Tasks", 100, new String[][]{}, new Item(2703, 1),new Item(6466, 25000)),
        COMPLETE_200_BEAST_TASKS(42, 3429, AchievementType.SLAYER, "Complete 200 Beast Tasks", 200, new String[][]{}, new Item(23059, 1),new Item(3578, 2),new Item(6466, 35000)),
        COMPLETE_300_BEAST_TASKS(43, 3429, AchievementType.SLAYER, "Complete 300 Beast Tasks", 300, new String[][]{}, new Item(23173, 30),new Item(1448, 30),new Item(15668, 30)),
        COMPLETE_400_BEAST_TASKS(44, 3429, AchievementType.SLAYER, "Complete 400 Beast Tasks", 400, new String[][]{}, new Item(2704, 1),new Item(3578, 5),new Item(23172, 10)),
        COMPLETE_500_BEAST_TASKS(45, 3429, AchievementType.SLAYER, "Complete 500 Beast Tasks", 500, new String[][]{}, new Item(19944, 1),new Item(15671, 10),new Item(6466, 50000)),



        COMPLETE_5_CORRUPT_TASKS(46, 3429, AchievementType.SLAYER, "Complete 5 Corrupt Tasks", 5, new String[][]{}, new Item(3502, 250),new Item(3512, 3)),
        COMPLETE_25_CORRUPT_TASKS(47, 3429, AchievementType.SLAYER, "Complete 25 Corrupt Tasks", 25, new String[][]{}, new Item(3502, 500),new Item(3512, 8)),
        COMPLETE_50_CORRUPT_TASKS(48, 3429, AchievementType.SLAYER, "Complete 50 Corrupt Tasks", 50, new String[][]{}, new Item(2009, 1),new Item(1448, 4)),
        COMPLETE_100_CORRUPT_TASKS(49, 3429, AchievementType.SLAYER, "Complete 100 Corrupt Tasks", 100, new String[][]{}, new Item(23057, 1),new Item(3502, 750)),
        COMPLETE_150_CORRUPT_TASKS(50, 3429, AchievementType.SLAYER, "Complete 150 Corrupt Tasks", 150, new String[][]{}, new Item(3512, 15),new Item(3502, 1000)),
        COMPLETE_250_CORRUPT_TASKS(51, 3429, AchievementType.SLAYER, "Complete 250 Corrupt Tasks", 250, new String[][]{}, new Item(17129, 2),new Item(3502, 800), new Item(17130, 1)),
        COMPLETE_375_CORRUPT_TASKS(52, 3429, AchievementType.SLAYER, "Complete 375 Corrupt Tasks", 375, new String[][]{}, new Item(3512, 15),new Item(3502, 2000)),
        COMPLETE_500_CORRUPT_TASKS(53, 3429, AchievementType.SLAYER, "Complete 500 Corrupt Tasks", 500, new String[][]{}, new Item(3502, 2500),new Item(3512, 15)),
        COMPLETE_650_CORRUPT_TASKS(54, 3429, AchievementType.SLAYER, "Complete 650 Corrupt Tasks", 650, new String[][]{}, new Item(19118, 1),new Item(2009, 1)),
        COMPLETE_800_CORRUPT_TASKS(55, 3429, AchievementType.SLAYER, "Complete 800 Corrupt Tasks", 800, new String[][]{}, new Item(15671, 2),new Item(3502, 5000)),
        COMPLETE_1000_CORRUPT_TASKS(56, 3429, AchievementType.SLAYER, "Complete 1000 Corrupt Tasks", 1000, new String[][]{}, new Item(3512, 35),new Item(3502, 10000), new Item(2009, 3)),

        COMPLETE_2_CORRUPT_BEAST_TASKS(57, 3429, AchievementType.SLAYER, "Complete 2 Corrupt Beast Tasks", 2, new String[][]{}, new Item(6466, 250),new Item(1305, 2)),
        COMPLETE_5_CORRUPT_BEAST_TASKS(58, 3429, AchievementType.SLAYER, "Complete 5 Corrupt Beast Tasks", 5, new String[][]{}, new Item(6466, 500),new Item(1305, 4),new Item(3502, 500)),
        COMPLETE_10_CORRUPT_BEAST_TASKS(59, 3429, AchievementType.SLAYER, "Complete 10 Corrupt Beast Tasks", 10, new String[][]{}, new Item(2009, 1),new Item(1448, 4),new Item(6466, 1000)),
        COMPLETE_20_CORRUPT_BEAST_TASKS(60, 3429, AchievementType.SLAYER, "Complete 20 Corrupt Beast Tasks", 20, new String[][]{}, new Item(6466, 3000),new Item(457, 2),new Item(15671, 3)),
        COMPLETE_40_CORRUPT_BEAST_TASKS(61, 3429, AchievementType.SLAYER, "Complete 40 Corrupt Beast Tasks", 40, new String[][]{}, new Item(3502, 1000),new Item(3512, 5),new Item(3582, 10)),
        COMPLETE_60_CORRUPT_BEAST_TASKS(62, 3429, AchievementType.SLAYER, "Complete 60 Corrupt Beast Tasks", 60, new String[][]{}, new Item(2009, 1),new Item(1305, 25)),
        COMPLETE_80_CORRUPT_BEAST_TASKS(63, 3429, AchievementType.SLAYER, "Complete 80 Corrupt Beast Tasks", 80, new String[][]{}, new Item(17129, 5),new Item(23172, 2),new Item(1448, 5)),
        COMPLETE_100_CORRUPT_BEAST_TASKS(64, 3429, AchievementType.SLAYER, "Complete 100 Corrupt Beast Tasks", 100, new String[][]{}, new Item(3502, 2000),new Item(6466, 15000)),
        COMPLETE_200_CORRUPT_BEAST_TASKS(65, 3429, AchievementType.SLAYER, "Complete 200 Corrupt Beast Tasks", 200, new String[][]{}, new Item(3502, 1500),new Item(3512, 15),new Item(6466, 15000)),
        COMPLETE_300_CORRUPT_BEAST_TASKS(66, 3429, AchievementType.SLAYER, "Complete 300 Corrupt Beast Tasks", 300, new String[][]{}, new Item(2009, 2),new Item(1448, 20),new Item(15668, 30)),
        COMPLETE_400_CORRUPT_BEAST_TASKS(67, 3429, AchievementType.SLAYER, "Complete 400 Corrupt Beast Tasks", 400, new String[][]{}, new Item(3502, 4500),new Item(3578, 4),new Item(23172, 10)),
        COMPLETE_500_CORRUPT_BEAST_TASKS(68, 3429, AchievementType.SLAYER, "Complete 500 Corrupt Beast Tasks", 500, new String[][]{}, new Item(17129, 4),new Item(19118, 4),new Item(2009, 4)),
        COMPLETE_10_FOREST_TASKS(69, 3429, AchievementType.SLAYER, "Complete 10 Forest Tasks", 10, new String[][]{}, new Item(2699, 25),new Item(2556, 2)),
        COMPLETE_25_FOREST_TASKS(70, 3429, AchievementType.SLAYER, "Complete 25 Forest Tasks", 25, new String[][]{}, new Item(2699, 50),new Item(2556, 5)),
        COMPLETE_50_FOREST_TASKS(71, 3429, AchievementType.SLAYER, "Complete 50 Forest Tasks", 50, new String[][]{}, new Item(17819, 1),new Item(2699, 25)),
        COMPLETE_100_FOREST_TASKS(72, 3429, AchievementType.SLAYER, "Complete 100 Forest Tasks", 100, new String[][]{}, new Item(2699, 100),new Item(17819, 2)),
        COMPLETE_150_FOREST_TASKS(73, 3429, AchievementType.SLAYER, "Complete 150 Forest Tasks", 150, new String[][]{}, new Item(3578, 1),new Item(2699, 75)),
        COMPLETE_200_FOREST_TASKS(74, 3429, AchievementType.SLAYER, "Complete 200 Forest Tasks", 200, new String[][]{}, new Item(2556, 8),new Item(17819, 2), new Item(2699, 25)),
        COMPLETE_300_FOREST_TASKS(75, 3429, AchievementType.SLAYER, "Complete 300 Forest Tasks", 300, new String[][]{}, new Item(17819, 2),new Item(2556, 10),new Item(17129, 1)),
        COMPLETE_400_FOREST_TASKS(76, 3429, AchievementType.SLAYER, "Complete 400 Forest Tasks", 400, new String[][]{}, new Item(2699, 175),new Item(17819, 2),new Item(2556, 12)),
        COMPLETE_500_FOREST_TASKS(77, 3429, AchievementType.SLAYER, "Complete 500 Forest Tasks", 500, new String[][]{}, new Item(17819, 5),new Item(4022, 1),new Item(2556, 15)),


        COMPLETE_5_SPECTRAL_TASKS(78, 3429, AchievementType.SLAYER, "Complete 5 Spectral Tasks", 5, new String[][]{}, new Item(2064, 250),new Item(2062, 3)),
        COMPLETE_25_SPECTRAL_TASKS(79, 3429, AchievementType.SLAYER, "Complete 25 Spectral Tasks", 25, new String[][]{}, new Item(2064, 500),new Item(2062, 8)),
        COMPLETE_50_SPECTRAL_TASKS(80, 3429, AchievementType.SLAYER, "Complete 50 Spectral Tasks", 50, new String[][]{}, new Item(19118, 1),new Item(1448, 4)),
        COMPLETE_100_SPECTRAL_TASKS(81, 3429, AchievementType.SLAYER, "Complete 100 Spectral Tasks", 100, new String[][]{}, new Item(23057, 1),new Item(2064, 750)),
        COMPLETE_150_SPECTRAL_TASKS(82, 3429, AchievementType.SLAYER, "Complete 150 Spectral Tasks", 150, new String[][]{}, new Item(2062, 15),new Item(2064, 1000)),
        COMPLETE_250_SPECTRAL_TASKS(83, 3429, AchievementType.SLAYER, "Complete 250 Spectral Tasks", 250, new String[][]{}, new Item(17129, 2),new Item(2064, 800), new Item(17130, 1)),
        COMPLETE_375_SPECTRAL_TASKS(84, 3429, AchievementType.SLAYER, "Complete 375 Spectral Tasks", 375, new String[][]{}, new Item(2062, 15),new Item(2064, 2000)),
        COMPLETE_500_SPECTRAL_TASKS(85, 3429, AchievementType.SLAYER, "Complete 500 Spectral Tasks", 500, new String[][]{}, new Item(2064, 2500),new Item(2062, 15)),
        COMPLETE_650_SPECTRAL_TASKS(86, 3429, AchievementType.SLAYER, "Complete 650 Spectral Tasks", 650, new String[][]{}, new Item(19118, 1),new Item(2624, 1)),
        COMPLETE_800_SPECTRAL_TASKS(87, 3429, AchievementType.SLAYER, "Complete 800 Spectral Tasks", 800, new String[][]{}, new Item(15671, 2),new Item(2064, 5000)),
        COMPLETE_1000_SPECTRAL_TASKS(88, 3429, AchievementType.SLAYER, "Complete 1000 Spectral Tasks", 1000, new String[][]{}, new Item(2062, 35),new Item(2064, 10000), new Item(2624, 2)),

        // Looting task achievements for Rift Chests

        OPEN_5_RIFT_CHESTS(1, 3427, AchievementType.LOOTING, "Open 5 Rift Chests", 5, new String[][]{}, new Item(5585, 2),new Item(10258, 5)),
        OPEN_25_RIFT_CHESTS(2, 3427, AchievementType.LOOTING, "Open 25 Rift Chests", 25, new String[][]{}, new Item(5585, 5),new Item(10258, 15)),
        OPEN_50_RIFT_CHESTS(3, 3427, AchievementType.LOOTING, "Open 50 Rift Chests", 50, new String[][]{}, new Item(5585, 10),new Item(10258, 30)),
        OPEN_100_RIFT_CHESTS(4, 3427, AchievementType.LOOTING, "Open 100 Rift Chests", 100, new String[][]{}, new Item(5585, 20),new Item(10260, 1)),
        OPEN_250_RIFT_CHESTS(5, 3427, AchievementType.LOOTING, "Open 250 Rift Chests", 250, new String[][]{}, new Item(5585, 20),new Item(15668, 1)),
        OPEN_500_RIFT_CHESTS(6, 3427, AchievementType.LOOTING, "Open 500 Rift Chests", 500, new String[][]{}, new Item(5585, 30),new Item(10946, 1)),
        OPEN_750_RIFT_CHESTS(7, 3427, AchievementType.LOOTING, "Open 750 Rift Chests", 750, new String[][]{}, new Item(5585, 50),new Item(15668, 3)),
        OPEN_1000_RIFT_CHESTS(8, 3427, AchievementType.LOOTING, "Open 1000 Rift Chests", 1000, new String[][]{}, new Item(5585, 75),new Item(15670, 5)),
        OPEN_1250_RIFT_CHESTS(9, 3427, AchievementType.LOOTING, "Open 1250 Rift Chests", 1250, new String[][]{}, new Item(5585, 75),new Item(17130, 1)),
        OPEN_1500_RIFT_CHESTS(10, 3427, AchievementType.LOOTING, "Open 1500 Rift Chests", 1500, new String[][]{}, new Item(5585, 100),new Item(23058, 1),new Item(15671, 2)),

        // Looting task achievements for Elemental Chests
        OPEN_50_ELEMENTAL_CHESTS(11, 3427, AchievementType.LOOTING, "Open 50 Elemental Chests", 50, new String[][]{}, new Item(20104, 10)),
        OPEN_250_ELEMENTAL_CHESTS(12, 3427, AchievementType.LOOTING, "Open 250 Elemental Chests", 250, new String[][]{}, new Item(20104, 25),new Item(20105, 15)),
        OPEN_750_ELEMENTAL_CHESTS(13, 3427, AchievementType.LOOTING, "Open 750 Elemental Chests", 750, new String[][]{}, new Item(20105, 30),new Item(20106, 20)),
        OPEN_1500_ELEMENTAL_CHESTS(14, 3427, AchievementType.LOOTING, "Open 1500 Elemental Chests", 1500, new String[][]{}, new Item(20106, 50),new Item(20107, 30)),
        OPEN_3000_ELEMENTAL_CHESTS(15, 3427, AchievementType.LOOTING, "Open 3000 Elemental Chests", 3000, new String[][]{}, new Item(20107, 50),new Item(20108, 25),new Item(10256, 1)),
        OPEN_5000_ELEMENTAL_CHESTS(16, 3427, AchievementType.LOOTING, "Open 5000 Elemental Chests", 5000, new String[][]{}, new Item(20109, 100),new Item(15668, 5),new Item(10946, 1)),

        // Looting task achievements for Box Openings
        OPEN_10_BOXES(17, 3427, AchievementType.LOOTING, "Open 10 Boxes", 10, new String[][]{}, new Item(15667, 5),new Item(10258, 5)),
        OPEN_50_BOXES(18, 3427, AchievementType.LOOTING, "Open 50 Boxes", 50, new String[][]{}, new Item(15668, 1),new Item(15667, 10)),
        OPEN_100_BOXES(19, 3427, AchievementType.LOOTING, "Open 100 Boxes", 100, new String[][]{}, new Item(15670, 1),new Item(15668, 1)),
        OPEN_250_BOXES(20, 3427, AchievementType.LOOTING, "Open 250 Boxes", 250, new String[][]{}, new Item(15668, 2),new Item(15670, 1),new Item(15671, 1)),
        OPEN_500_BOXES(21, 3427, AchievementType.LOOTING, "Open 500 Boxes", 500, new String[][]{}, new Item(23171, 1),new Item(15668, 3),new Item(10262, 1)),

        // Minigame achievements for Soulbane Runs
        COMPLETE_2_SOULBANE(1, 3428, AchievementType.MINIGAMES, "Complete Soulbane 2 times", 2, new String[][]{}, new Item(621, 500),new Item(20080, 1)),
        COMPLETE_10_SOULBANE(2, 3428, AchievementType.MINIGAMES, "Complete Soulbane 10 times", 10, new String[][]{}, new Item(621, 1000),new Item(3580, 2),new Item(20080, 1)),
        COMPLETE_25_SOULBANE(3, 3428, AchievementType.MINIGAMES, "Complete Soulbane 25 times", 25, new String[][]{}, new Item(621, 2500),new Item(20066, 3),new Item(20081, 1)),
        COMPLETE_50_SOULBANE(4, 3428, AchievementType.MINIGAMES, "Complete Soulbane 50 times", 50, new String[][]{}, new Item(621, 5000), new Item(10946, 1),new Item(20081, 2)),
        COMPLETE_100_SOULBANE(5, 3428, AchievementType.MINIGAMES, "Complete Soulbane 100 times", 100, new String[][]{}, new Item(621, 7500),new Item(23173, 2),new Item(20082, 1)),
        COMPLETE_150_SOULBANE(6, 3428, AchievementType.MINIGAMES, "Complete Soulbane 150 times", 150, new String[][]{}, new Item(621, 10000),new Item(20066, 5),new Item(20082, 2)),
        COMPLETE_250_SOULBANE(7, 3428, AchievementType.MINIGAMES, "Complete Soulbane 250 times", 250, new String[][]{}, new Item(621, 12500),new Item(3580, 5),new Item(3582, 2)),
        COMPLETE_350_SOULBANE(8, 3428, AchievementType.MINIGAMES, "Complete Soulbane 350 times", 350, new String[][]{}, new Item(621, 15000),new Item(20066, 10),new Item(20082, 5)),
        COMPLETE_500_SOULBANE(9, 3428, AchievementType.MINIGAMES, "Complete Soulbane 500 times", 500, new String[][]{}, new Item(621, 25000),new Item(20082, 8),new Item(3582, 5)),

        // Minigames achievements for Corrupt Raids
        COMPLETE_1_CORRUPT_RAIDS(10, 3428, AchievementType.MINIGAMES, "Complete 1 Corrupt Raid", 1, new String[][]{}, new Item(770, 1)),
        COMPLETE_5_CORRUPT_RAIDS(11, 3428, AchievementType.MINIGAMES, "Complete 5 Corrupt Raid", 5, new String[][]{}, new Item(770, 2),new Item(15668, 1)),
        COMPLETE_25_CORRUPT_RAIDS(12, 3428, AchievementType.MINIGAMES, "Complete 25 Corrupt Raid", 25, new String[][]{}, new Item(770, 5),new Item(17130  , 2)),
        COMPLETE_100_CORRUPT_RAIDS(13, 3428, AchievementType.MINIGAMES, "Complete 100 Corrupt Raid", 100, new String[][]{}, new Item(770, 10),new Item(23171  , 1)),
        COMPLETE_250_CORRUPT_RAIDS(14, 3428, AchievementType.MINIGAMES, "Complete 250 Corrupt Raid", 250, new String[][]{}, new Item(770, 30),new Item(23171  , 2)),
        COMPLETE_500_CORRUPT_RAIDS(15, 3428, AchievementType.MINIGAMES, "Complete 500 Corrupt Raid", 500, new String[][]{}, new Item(770, 50),new Item(23172  , 1)),

        // Minigames achievements for Corrupt Raids
        COMPLETE_1_COE(16, 3428, AchievementType.MINIGAMES, "Complete 1 COE", 1, new String[][]{}, new Item(15669, 1),new Item(15666, 1)),
        COMPLETE_5_COE(17, 3428, AchievementType.MINIGAMES, "Complete 5 COE", 5, new String[][]{}, new Item(15669, 2),new Item(15668, 1)),
        COMPLETE_25_COE(18, 3428, AchievementType.MINIGAMES, "Complete 25 COE", 25, new String[][]{}, new Item(15669, 3),new Item(15668  , 3)),
        COMPLETE_100_COE(19, 3428, AchievementType.MINIGAMES, "Complete 100 COE", 100, new String[][]{}, new Item(15669, 5),new Item(17130  , 1)),
        COMPLETE_250_COE(20, 3428, AchievementType.MINIGAMES, "Complete 250 COE", 250, new String[][]{}, new Item(15669, 10),new Item(17130  , 3)),
        COMPLETE_500_COE(21, 3428, AchievementType.MINIGAMES, "Complete 500 COE", 500, new String[][]{}, new Item(15669, 20),new Item(17130  , 5)),
        // Minigames achievements for Boss Kills


        // Minigames achievements for Corrupt Raids
        COMPLETE_1_SPECTRAL_RAIDS(22, 3428, AchievementType.MINIGAMES, "Complete 1 Spectral Raid", 1, new String[][]{}, new Item(2053, 1)),
        COMPLETE_5_SPECTRAL_RAIDS(23, 3428, AchievementType.MINIGAMES, "Complete 5 Spectral Raid", 5, new String[][]{}, new Item(2053, 2),new Item(15668, 1)),
        COMPLETE_25_SPECTRAL_RAIDS(24, 3428, AchievementType.MINIGAMES, "Complete 25 Spectral Raid", 25, new String[][]{}, new Item(2053, 5),new Item(17130  , 2)),
        COMPLETE_100_SPECTRAL_RAIDS(25, 3428, AchievementType.MINIGAMES, "Complete 100 Spectral Raid", 100, new String[][]{}, new Item(2054, 15),new Item(23171  , 1)),
        COMPLETE_250_SPECTRAL_RAIDS(26, 3428, AchievementType.MINIGAMES, "Complete 250 Spectral Raid", 250, new String[][]{}, new Item(2054, 30),new Item(23171  , 2)),
        COMPLETE_500_SPECTRAL_RAIDS(27, 3428, AchievementType.MINIGAMES, "Complete 500 Spectral Raid", 500, new String[][]{}, new Item(2055, 35),new Item(23172  , 1)),





        // Skilling achievements for Mining Salt
        MINE_5K_SALT(1, 3431, AchievementType.SKILLING, "Mine 5k Salt", 5000, new String[][]{}, new Item(20069, 2),new Item(10258, 10),new Item(15667, 1)),
        MINE_25K_SALT(2, 3431, AchievementType.SKILLING, "Mine 25k Salt", 25000, new String[][]{}, new Item(23121, 1000),new Item(23119, 1000),new Item(23122, 1000)),
        MINE_100K_SALT(3, 3431, AchievementType.SKILLING, "Mine 100k Salt", 100000, new String[][]{}, new Item(23121, 5000),new Item(23119, 5000),new Item(23122, 5000)),
        MINE_250K_SALT(4, 3431, AchievementType.SKILLING, "Mine 250k Salt", 250000, new String[][]{}, new Item(20049, 30),new Item(17490, 5),new Item(20069, 5)),
        MINE_500K_SALT(5, 3431, AchievementType.SKILLING, "Mine 500k Salt", 500000, new String[][]{}, new Item(23121, 15000),new Item(23119, 15000),new Item(23122, 15000)),
        MINE_1M_SALT(6, 3431, AchievementType.SKILLING, "Mine 1M Salt", 1000000, new String[][]{}, new Item(20049, 100),new Item(10946, 1),new Item(20069, 10)),

        // Skilling achievements for Making Potions
        MAKE_5_POTIONS(7, 3431, AchievementType.SKILLING, "Make 5 Potions", 5, new String[][]{}, new Item(17582, 1),new Item(17584, 1),new Item(17586, 1)),
        MAKE_25_POTIONS(8, 3431, AchievementType.SKILLING, "Make 25 Potions", 25, new String[][]{}, new Item(17490, 10),new Item(20067, 5)),
        MAKE_50_POTIONS(9, 3431, AchievementType.SKILLING, "Make 50 Potions", 50, new String[][]{}, new Item(17582, 5),new Item(17584, 5),new Item(17586, 5)),
        MAKE_100_POTIONS(10, 3431, AchievementType.SKILLING, "Make 100 Potions", 100, new String[][]{}, new Item(20049, 25),new Item(20067, 10),new Item(23122, 5000)),
        MAKE_250_POTIONS(11, 3431, AchievementType.SKILLING, "Make 250 Potions", 250, new String[][]{}, new Item(17582, 10),new Item(17584, 10),new Item(17586, 10)),
        MAKE_500_POTIONS(12, 3431, AchievementType.SKILLING, "Make 500 Potions", 500, new String[][]{}, new Item(17490, 20),new Item(10946, 1),new Item(23119, 7500)),
        MAKE_1000_POTIONS(13, 3431, AchievementType.SKILLING, "Make 1000 Potions", 1000, new String[][]{}, new Item(17582, 15),new Item(17584, 15),new Item(17586, 15)),
        MAKE_1500_POTIONS(14, 3431, AchievementType.SKILLING, "Make 1500 Potions", 1500, new String[][]{}, new Item(20049, 100),new Item(17490, 20),new Item(23121, 10000)),
        MAKE_2500_POTIONS(15, 3431, AchievementType.SKILLING, "Make 2500 Potions", 2500, new String[][]{}, new Item(23057, 1),new Item(17130, 1),new Item(15668, 2)),

        // Skilling achievements for Upgrading Items
        UPGRADE_2_ITEMS(16, 3431, AchievementType.SKILLING, "Upgrade 2 Items", 2, new String[][]{}, new Item(11056, 30),new Item(11054, 30),new Item(11052, 30)),
        UPGRADE_5_ITEMS(17, 3431, AchievementType.SKILLING, "Upgrade 5 Items", 5, new String[][]{}, new Item(11056, 100),new Item(11054, 100),new Item(11052, 100)),
        UPGRADE_10_ITEMS(18, 3431, AchievementType.SKILLING, "Upgrade 10 Items", 10, new String[][]{}, new Item(17490, 10),new Item(10258, 50),new Item(19062, 100)),
        UPGRADE_25_ITEMS(19, 3431, AchievementType.SKILLING, "Upgrade 25 Items", 25, new String[][]{}, new Item(20067, 5),new Item(20069, 5),new Item(20071, 5)),
        UPGRADE_50_ITEMS(20, 3431, AchievementType.SKILLING, "Upgrade 50 Items", 50, new String[][]{},  new Item(17582, 10),new Item(17584, 10),new Item(17586, 10)),
        UPGRADE_100_ITEMS(21, 3431, AchievementType.SKILLING, "Upgrade 100 Items", 100, new String[][]{}, new Item(11056, 300),new Item(11054, 300),new Item(11052, 300)),
        UPGRADE_250_ITEMS(22, 3431, AchievementType.SKILLING, "Upgrade 250 Items", 250, new String[][]{}, new Item(17490, 50),new Item(23057, 1),new Item(20071, 15)),
        UPGRADE_500_ITEMS(23, 3431, AchievementType.SKILLING, "Upgrade 500 Items", 500, new String[][]{}, new Item(15668, 5),new Item(23172, 1),new Item(15671, 2)),

        // Skilling achievements for Salvaging Items
        SALVAGE_5_ITEMS(24, 3431, AchievementType.SKILLING, "Salvage 5 Items", 5, new String[][]{}, new Item(20104, 10),new Item(15667, 2),new Item(10258, 10)),
        SALVAGE_25_ITEMS(25, 3431, AchievementType.SKILLING, "Salvage 25 Items", 25, new String[][]{}, new Item(20104, 15),new Item(20105, 10),new Item(10258, 20)),
        SALVAGE_50_ITEMS(26, 3431, AchievementType.SKILLING, "Salvage 50 Items", 50, new String[][]{}, new Item(20071, 3),new Item(19062, 50),new Item(20105, 30)),
        SALVAGE_150_ITEMS(27, 3431, AchievementType.SKILLING, "Salvage 150 Items", 150, new String[][]{}, new Item(11056, 250),new Item(11054, 250),new Item(11052, 250)),
        SALVAGE_250_ITEMS(28, 3431, AchievementType.SKILLING, "Salvage 250 Items", 250, new String[][]{}, new Item(20106, 40),new Item(20107, 30),new Item(15667, 15)),
        SALVAGE_500_ITEMS(29, 3431, AchievementType.SKILLING, "Salvage 500 Items", 500, new String[][]{}, new Item(23171, 1),new Item(3582, 2),new Item(19062, 200)),
        SALVAGE_1000_ITEMS(30, 3431, AchievementType.SKILLING, "Salvage 1000 Items", 1000, new String[][]{}, new Item(11056, 750),new Item(11054, 750),new Item(11052, 750)),
        SALVAGE_2500_ITEMS(31, 3431, AchievementType.SKILLING, "Salvage 2500 Items", 2500, new String[][]{}, new Item(20107, 250),new Item(20108, 150),new Item(10946, 1)),
        SALVAGE_5000_ITEMS(32, 3431, AchievementType.SKILLING, "Salvage 5000 Items", 5000, new String[][]{}, new Item(15671, 5),new Item(17130, 5),new Item(20109, 300)),

        // Skilling achievements for woodcutting
        CUT_500_LOGS(33, 3431, AchievementType.SKILLING, "Cut 500 Logs", 500, new String[][]{}, new Item(1446, 1),new Item(10945, 2)),
        CUT_1k_LOGS(34, 3431, AchievementType.SKILLING, "Cut 1K Logs", 1000, new String[][]{}, new Item(1446, 2),new Item(10945, 2)),
        CUT_5k_LOGS(35, 3431, AchievementType.SKILLING, "Cut 5K Logs", 5000, new String[][]{}, new Item(1446, 3),new Item(10945, 3)),
        CUT_10k_LOGS(36, 3431, AchievementType.SKILLING, "Cut 10K Logs", 10000, new String[][]{}, new Item(1447, 2),new Item(10946, 1)),
        CUT_50k_LOGS(37, 3431, AchievementType.SKILLING, "Cut 50K Logs", 50000, new String[][]{},  new Item(1447, 3),new Item(10946, 1)),
        CUT_100k_LOGS(38, 3431, AchievementType.SKILLING, "Cut 100K Logs", 100000, new String[][]{}, new Item(1447, 5),new Item(10946, 2)),



        ;

        final String[][] points;
        private final AchievementType difficulty;
        private final AchievementRequirement requirement;
        private final String description;
        private final int amount;
        private final int npcId;
        private final int identification;
        @Getter
        private final int spriteID;
        private final Item[] rewards;

        Achievement(int identification, int spriteID, AchievementType difficulty, String description, int amount, String[][] points,
                    Item... rewards) {
            this.identification = identification;
            this.spriteID = spriteID;
            this.difficulty = difficulty;
            this.requirement = null;
            this.npcId = -1;
            this.description = description;
            this.amount = amount;
            this.points = points;
            this.rewards = rewards;

            for (Item b : rewards)
                if (b.getAmount() == 0)
                    b.setAmount(1);

        }

        public static List<Achievement> getAchievementFromTier(int tier) {
            List<Achievement> achievementList = new ArrayList<>();
            for (Achievement a : values())
                if (a.getDifficulty().ordinal() == tier)
                    achievementList.add(a);
            return achievementList;
        }

        public int getNpcId() {
            return npcId;
        }

        public int getId() {
            return identification;
        }

        public AchievementType getDifficulty() {
            return difficulty;
        }

        AchievementRequirement getRequirement() {
            return requirement;
        }

        public String getDescription() {
            return description;
        }

        public int getAmount() {
            return amount;
        }

        public String[][] getPoints() {
            return points;
        }

        public Item[] getRewards() {
            return rewards;
        }
    }
}
