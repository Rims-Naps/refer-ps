package com.ruse.donation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ruse.GameSettings;
import com.ruse.model.Item;
import com.ruse.model.container.impl.Bank;
import com.ruse.util.TimeUtils;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class PersonalCampaignHandler {

    private static LocalDateTime endDate = getEndDate();
    public static Map<String, List<DataSet>> monthlyRewards;

    // Load rewards based on the current month
    static {
        loadMonthlyRewards();
    }

    // Get the current month's end date (last day of the month at 23:59)
    private static LocalDateTime getEndDate() {
        LocalDate today = LocalDate.now();
        return LocalDateTime.of(today.getYear(), today.getMonth(), today.lengthOfMonth(), 23, 59);
    }

    public static void loadMonthlyRewards() {
        try {
            // Load the JSON file for the current month
            String month = LocalDate.now().getMonth().name().toLowerCase();
            FileReader reader = new FileReader("./data/donations/rewards_" + month + ".json");
            // Use TypeToken to specify the type of the map being deserialized
            Type type = new TypeToken<Map<String, List<DataSet>>>() {}.getType();
            monthlyRewards = new Gson().fromJson(reader, type);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class DataSet {
        @Getter
        private int maxThreshold;
        @Getter
        private Item[] items;

        public DataSet(int maxThreshold, Item[] items) {
            this.maxThreshold = maxThreshold;
            this.items = items;
        }
    }

    // Reset the campaign data at the start of a new month
    public static void reset(Player plr) {
        LocalDate lastReset = plr.getLastCampaignResetDate();
        LocalDate now = LocalDate.now();

        if (lastReset == null || lastReset.getMonthValue() != now.getMonthValue()) {
            plr.claimed = new boolean[getDataSets().size()];
            plr.setDonatedThroughMonth(0);
            plr.setLastCampaignResetDate(now);
            plr.msgFancyPurp("Your Personal Rewards Campaign has reset.");
            loadMonthlyRewards();
        }
    }

    public static void attemptGive(Player plr, int amount) {
        plr.setDonatedThroughMonth(plr.getDonatedThroughMonth() + amount);
        updateInterface(plr);

        // Prevents deals being given after the ending date is reached.
        if (LocalDateTime.now().isAfter(endDate)) {
            return;
        }
        checkAndUpdateClaimedArray(plr);
        for (DataSet data : getDataSets()) {
            if (plr.getDonatedThroughMonth() >= data.getMaxThreshold() && !plr.claimed[getDataSets().indexOf(data)]) {
                plr.getBank(0).add(data.getItems());
                plr.setClaimed(getDataSets().indexOf(data), true);
            }
        }
    }

    public static void updateInterface(Player plr) {
        // Initialize milestones array based on the size of the DataSet list
        List<DataSet> dataSets = getDataSets();
        Milestone[] milestones = new Milestone[dataSets.size()];

        int maxVal = 0;
        int highestCurVal = 0;

        // Iterate over the data sets to populate milestones and determine max and current values
        for (int i = 0; i < dataSets.size(); i++) {
            DataSet data = dataSets.get(i);
            milestones[i] = new Milestone(data.getMaxThreshold(), data.getItems(), Milestone.MilestoneType.ITEMS);

            if (data.getMaxThreshold() > maxVal) {
                maxVal = data.getMaxThreshold();
            }
        }

        int donatedThruMonth = 0;
        if (plr.getDonatedThroughMonth() >= maxVal)
            donatedThruMonth = maxVal;
        else
            donatedThruMonth = plr.getDonatedThroughMonth();


        // Send the custom progress bar with milestones to the client
        plr.getPacketSender().sendCustomProgressBarWMilestones(28604, donatedThruMonth, milestones, maxVal);
    }

    private static List<DataSet> getDataSets() {
        String month = LocalDate.now().getMonth().name().toLowerCase();
        List<DataSet> defaultValue = new java.util.ArrayList<>();
        return monthlyRewards.getOrDefault(month, defaultValue);
    }

    public static void checkAndUpdateClaimedArray(Player plr) {
        List<DataSet> dataSets = getDataSets(); // Get the current list of DataSet
        int newSize = dataSets.size();

        // If the new size is larger than the existing claimed array, resize it
        if (plr.claimed.length < newSize) {
            boolean[] newClaimedArray = new boolean[newSize];

            // Copy the old values to the new array
            System.arraycopy(plr.claimed, 0, newClaimedArray, 0, plr.claimed.length);

            // Set the new array back to the player
            plr.claimed = newClaimedArray;
        }
    }
}
