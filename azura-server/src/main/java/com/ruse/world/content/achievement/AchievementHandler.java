package com.ruse.world.content.achievement;

import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.content.achievement.Achievements.Achievement;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

public class AchievementHandler {
    public static final int MAXIMUM_TIER_ACHIEVEMENTS = 100;
    public static final int MAXIMUM_TIERS = 6;
    private final Player player;
    /**
     * WARNING: ADD TO THE END OF THE LIST.
     */
    private final int[][] boughtItems = {{7409, -1}, {13659, -1}, {20120, -1}, {88, -1}, {13281, -1}, {2379, -1}, {20235, -1}, {13845, -1}, {13846, -1}, {13847, -1},
            {13848, -1}, {13849, -1}, {13850, -1}, {13851, -1}, {13852, -1}, {13853, -1}, {13854, -1}, {13855, -1}, {13856, -1}, {13857, -1}, {20220, -1},
            {20221, -1}, {20222, -1},};
    public int currentInterface = 0;
    public boolean clickedAchievement = false;
    private int toView;
    private int[][] amountRemaining = new int[MAXIMUM_TIERS][MAXIMUM_TIER_ACHIEVEMENTS];
    private boolean[][] completed = new boolean[MAXIMUM_TIERS][MAXIMUM_TIER_ACHIEVEMENTS];
    private int points;
    //	private static final long numDaily = Achievement.ACHIEVEMENTS.stream().filter(n -> n.getDifficulty().ordinal() == 3).count();
    private long dailyAchievementsDate;
    @Getter
    private ArrayList<Achievement> achievementsShown = new ArrayList<>();

    private long lastClickedClaimAll;

    public AchievementHandler(Player player) {
        this.player = player;
    }

    /**
     * Gets tomorrow's date
     */
    public static String getTimeLeft() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("EST"));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1);

        long millis = cal.getTimeInMillis() - System.currentTimeMillis();

        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                        - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    /**
     * Draws the achievement interface
     *
     * @param tier
     */
    public void drawInterface(int tier) {
        refreshInterface(tier);
    }

    public void refreshInterface(int tier) {
        int c = 0;
        int completed = 0;

        achievementsShown.clear();

        List<Achievement> achievementList = Achievement.getAchievementFromTier(tier);
        if(achievementList == null) {
            refreshInterface(0);
            player.getPacketSender().sendConfig(5332,0);
            return;
        }
        for(int i = 0; i < 100; i++) {
            int icon = 117208;
            int text = 117308;
            int progressBar = 117408;
            int completedText = 117508;

            if(achievementList.size() > i) {
                Achievement achievement = achievementList.get(i);
                int currentAmount = getAmountRemaining(achievement.getDifficulty().ordinal(), achievement.getId());
                boolean done = false;

                int percentage = (int) ((100 * (double) currentAmount) / (double) achievement.getAmount());//percentage calculator
                if (currentAmount >= achievement.getAmount()) {
                    currentAmount = achievement.getAmount();
                    percentage = 100;
                    completed++;
                    done = true;
                }


                player.getPacketSender().sendString(completedText+i, (done ? "@gre@" : "@whi@") + currentAmount + "/" + achievement.getAmount());//Progress
                player.getPacketSender().sendSpriteChange(icon+i, achievement.getSpriteID());
                player.getPacketSender().sendString(text+i, (done ? "@str@@gre@" : "") + Misc.ucFirst(achievement.name().toUpperCase().replaceAll("_", " ")));
                player.getPacketSender().sendProgressBar(progressBar+i, 0, percentage, 0);
                player.getPacketSender().sendInterfaceItems(117808+i, achievement.getRewards());
                c++;
                achievementsShown.add(achievement);
            } else {
                player.getPacketSender().sendString(completedText+i, "");
                player.getPacketSender().sendSpriteChange(icon+i, 738);
                player.getPacketSender().sendString(text+i,"");
                player.getPacketSender().sendProgressBar(progressBar+i, 0, 0, 0);
                player.getPacketSender().sendItemOnInterface(117808+i, -1, 0);
            }
        }

        player.getPacketSender().sendString(117013, "Achievements completed: " + completed + "/" + c);
        player.getPacketSender().setScrollBar(117107, Math.max(201, 39 * c));
        player.getPacketSender().sendInterface(117000);
    }


    public void sendInformation(int index) {

        if (getAchievementsShown() != null)
            player.sendMessage(getAchievementsShown().get(index).getDescription());

    }

    private void claimReward(int index) {
        if (getAchievementsShown() != null) {
            Achievements.claimReward(player, getAchievementsShown().get(index), true);
        }
    }

    public void claimAll() {
        for(int i = 0; i < getAchievementsShown().size(); i++) {
            Achievements.claimReward(player, getAchievementsShown().get(i), false);
        }
    }

    public boolean isComplete(int tier, int index) {
        return completed[tier][index];
    }

    public void setComplete(int tier, int index, boolean state) {
        this.completed[tier][index] = state;
    }

    public boolean[][] getCompleted() {
        return completed;
    }

    public void setCompleted(boolean[][] completed) {
        this.completed = completed;
    }

    public int getAmountRemaining(int tier, int index) {
        return amountRemaining[tier][index];
    }

    public int[][] getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(int[][] amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public void setAmountRemaining(int tier, int index, int amountRemaining) {
        this.amountRemaining[tier][index] = amountRemaining;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isAchievementItem(int itemId) {
        for (int[] boughtItem : boughtItems)
            if (boughtItem[0] == itemId)
                return true;
        return false;
    }

    public boolean hasBoughtItem(int itemId) {
        for (int[] boughtItem : boughtItems)
            if (boughtItem[0] == itemId)
                if (boughtItem[1] != -1)
                    return true;
        return false;
    }

    public void setBoughtItem(int itemId) {
        for (int i = 0; i < boughtItems.length; i++)
            if (boughtItems[i][0] == itemId)
                boughtItems[i][1] = 1;
    }

    public int[][] getBoughtItems() {
        return this.boughtItems;
    }

    public void setBoughtItem(int index, int value) {
        if (index > this.boughtItems.length - 1)
            return;
        this.boughtItems[index][1] = value;
    }

    /**
     * Gets today's date
     *
     * @return
     */
    public int getTodayDate() {
        Calendar cal = new GregorianCalendar();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        return (month * 100 + day);
    }

    public long getDailyAchievementsDate() {
        return dailyAchievementsDate;
    }

    public void setDailyTaskDate(long dailyTaskDate) {
        this.dailyAchievementsDate = dailyTaskDate;
    }

    public boolean handleButtonClick(int buttonId) {
        if(buttonId >= 117608 && buttonId <= 117707) {
            int index = Math.abs(117608 - buttonId);
            sendInformation(index);
            return true;
        } else if(buttonId >= 117708 && buttonId <= 117807) {
            int index = Math.abs(117708 - buttonId);
            claimReward(index);
            return true;
        }

        switch (buttonId) {
            case 117005:
                player.getAchievements().clickedAchievement = true;
                player.getAchievements().currentInterface = 0;
                player.getAchievements().drawInterface(0);
                return true;
            case 117006:
                player.getAchievements().clickedAchievement = true;
                player.getAchievements().currentInterface = 1;
                player.getAchievements().drawInterface(1);
                return true;
            case 117007:
                player.getAchievements().clickedAchievement = true;
                player.getAchievements().currentInterface = 2;
                player.getAchievements().drawInterface(2);
                return true;
            case 117014:
                player.getAchievements().clickedAchievement = true;
                player.getAchievements().currentInterface = 3;
                player.getAchievements().drawInterface(3);
                return true;
            case 117016:
                player.getAchievements().clickedAchievement = true;
                player.getAchievements().currentInterface = 4;
                player.getAchievements().drawInterface(4);
                return true;
            case 117909:
                if((System.currentTimeMillis() < lastClickedClaimAll + 1000))
                    return false;

                    claimAll();
                    lastClickedClaimAll = System.currentTimeMillis();
                return true;
        }
        return false;
    }

}
