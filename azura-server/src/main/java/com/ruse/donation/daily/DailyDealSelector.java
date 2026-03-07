package com.ruse.donation.daily;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.util.StringUtils;
import com.ruse.util.TimeUtils;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DailyDealSelector {
    private static List<DealType> dailyDeals;
    private static List<Item> itemForSpend;

    private static LocalDateTime startDate;

    public DailyDealSelector() {
        dailyDeals = new ArrayList<>();
        generateDailyDeals();
        startDate = LocalDateTime.now();
    }

    public static void init() {
        dailyDeals = new ArrayList<>();
        itemForSpend = new ArrayList<>();
        generateDailyDeals();
        startDate = LocalDateTime.now();
    }

    public static void sendInterface(Player plr) {
        List<DealType> deals = getDailyDeals();
        resetInterface(plr);
        plr.getPacketSender().sendString(28807, TimeUtils.getTimeRemainingDHM(LocalDateTime.now(), startDate.plusHours(24)));
        int interId = 28802;
        for (DealType deal : deals) {
            ItemDefinition def = ItemDefinition.forId(itemForSpend.get(0).getId());
            if (deal.equals(DealType.SPEND_100) || deal.equals(DealType.SPEND_50) || deal.equals(DealType.SPEND_25))
                plr.getPacketSender().sendString(StringUtils.capitalizeFirst(deal.toString().replaceAll("_", " ").toLowerCase()) + " and get \\n" + itemForSpend.get(0).getAmount() + " " + def.getName() + " free!", interId);
            else if (deal.equals(DealType.PURCHASE_GET_SAME_BOX_FREE))
                plr.getPacketSender().sendString("Buy any box, and get another\\none totally free!", interId);
            else if (deal.equals(DealType.BUY_2_GET_1_FREE_BOXES))
                plr.getPacketSender().sendString("Buy 2 boxes, and get another\\none totally free!", interId);
            else if (deal.equals(DealType.GET_25_PERCENT_BONDS))
                plr.getPacketSender().sendString("Get 25% of the value \\ndonated in bonds!", interId);
            else if (deal.equals(DealType.HALF_COST_COSMETIC_TICKETS))
                plr.getPacketSender().sendString("Donate $250 or more and \\nget a free Cosmetic ticket", interId);
            else if (deal.equals(DealType.DOUBLE_MEMBERSHIP_PURCHASE))
                plr.getPacketSender().sendString("Buy any Membership Scroll \\nand get a second one free!", interId);
            else if (deal.equals(DealType.OWNER_GOODIEBAG))
                plr.getPacketSender().sendString("Donate $100 or more and \\nget a free Owner Goodiebag", interId);
            else if (deal.equals(DealType.BUY_OWNER_GB_U_GET_1_FREE))
                plr.getPacketSender().sendString("Buy a Owner Goodiebag(U) \\nand get 1 free", interId);
            else if (deal.equals(DealType.DONATE_100_GET_2_FREE_ELE_CACHE))
                plr.getPacketSender().sendString("Donate $100 or more and \\nget 2 free Elemental Cache", interId);
            else if (deal.equals(DealType.GET_30_PERCENT_BONDS))
                plr.getPacketSender().sendString("Get 30% of the value \\ndonated in bonds!", interId);
            else if (deal.equals(DealType.BUY_1_ELE_CACHE_U_GET_1_FREE))
                plr.getPacketSender().sendString("Buy 1 Elemental Cache(u) \\nand get 1 free", interId);
            else if (deal.equals(DealType.SPENT_50_GET_EXTRA_DONO_ISLAND_PULL))
                plr.getPacketSender().sendString("Donate $50 or more and \\nget a Extra Island Pull", interId);
            else if (deal.equals(DealType.BUY_T3_MEM_GET_FREE_VOID_CRATE))
                plr.getPacketSender().sendString("Buy a Tier 3 Membership \\nand get a free Void Crate", interId);

            else
                plr.getPacketSender().sendString(deal.toString().replaceAll("_", " "), interId);
            interId++;
        }
    }

    public static void resetInterface(Player plr) {
        List<DealType> deals = getDailyDeals();
        int interId = 28803;
        for (int i = 28802; i < 28806; i++) {
            plr.getPacketSender().sendString("", i);
        }
    }

    public static void process() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (startDate.plusHours(24).isBefore(currentTime) || startDate.plusHours(24).isEqual(currentTime)) {
            regenerateDeals();
            startDate = currentTime;
            World.sendMessage("Daily Deals have now been cycled!");
            World.sendMessage("Forest Tasks have been reset.");

            for (Player player : World.getPlayers()) {
                if (player == null)
                    continue;
                player.setDailyForestTaskAmount(0);
                player.setDailyMarinaTasks(0);
                player.setForestResetTime(null);
            }
        }
    }


    private void sendDealsToDiscord() {

    }

    public static void regenerateDeals() {
        dailyDeals = new ArrayList<>();
        itemForSpend = new ArrayList<>();
        generateDailyDeals();
        startDate = LocalDateTime.now();
    }

    private static void generateItemForSpend(DealType type) {
        switch (type) {
            case SPEND_100:
                addItemNTimes(1, DailyDealItem.BOX_OF_TREASURES, 4);
                addItemNTimes(0.50, DailyDealItem.BOND_50, 1);
                addItemNTimes(0.25, DailyDealItem.BOX_OF_BLESSINGS, 3);
                addItemNTimes(0.1, DailyDealItem.T1_MEMBERSHIP, 1);
                break;
            case SPEND_50:
                addItemNTimes(1, DailyDealItem.BOX_OF_TREASURES, 2);
                addItemNTimes(0.50, DailyDealItem.NECRO_CRATES, 3);
                addItemNTimes(0.50, DailyDealItem.BOND_25, 1);
                addItemNTimes(0.25, DailyDealItem.BOND_CASKETS, 4);
                break;
            case SPEND_25:
                addItemNTimes(1, DailyDealItem.BOX_OF_TREASURES, 1);
                addItemNTimes(0.50, DailyDealItem.NECRO_CRATES, 2);
                addItemNTimes(0.50, DailyDealItem.BOND_10, 1);
                addItemNTimes(0.25, DailyDealItem.BOND_CASKETS, 2);
                break;

        }

    }

    private static void addItemNTimes(double probability, DailyDealItem item, int quantity) {
        Random rand = new Random();
        if (rand.nextDouble() < probability) {
            itemForSpend.clear();
            itemForSpend.add(new Item(item.itemId, quantity));
        }
    }

    private static void generateDailyDeals() {
        //1 of 3 Guarenteed Deals
        int random = Misc.inclusiveRandom(1,3);
        if (random == 1) {
            addDealNTimes(1, DealType.SPEND_100);
            generateItemForSpend(DealType.SPEND_100);
        }
        if (random == 2) {
            addDealNTimes(1, DealType.SPEND_50);
            generateItemForSpend(DealType.SPEND_50);
        }
        if (random == 3) {
            addDealNTimes(1, DealType.SPEND_25);
            generateItemForSpend(DealType.SPEND_25);
        }
        //75% Chance deals
        addDealNTimes(0.75, DealType.GET_30_PERCENT_BONDS);
        // 50% chance deals
        addDealNTimes(0.5, DealType.BUY_OWNER_GB_U_GET_1_FREE);

        addDealNTimes(0.5, DealType.BUY_1_ELE_CACHE_U_GET_1_FREE);

        // 25% chance deals
        addDealNTimes(0.25, DealType.BUY_T3_MEM_GET_FREE_VOID_CRATE);

        // 10% chance deals
        addDealNTimes(0.1, DealType.DONATE_100_GET_2_FREE_ELE_CACHE);
        addDealNTimes(0.1, DealType.SPENT_50_GET_EXTRA_DONO_ISLAND_PULL);

    }

    private static void addDealNTimes(double probability, DealType dealType) {
        Random rand = new Random();
        if (rand.nextDouble() < probability) {
            // Check if the deal is already in the list with lower probability and remove it
            //Only if the list is greater than 4 deals.
            if (dailyDeals.size() > 4)
                dailyDeals.removeIf(existingDeal -> existingDeal.ordinal() > dealType.ordinal());

            // Add the current deal with higher probability
            dailyDeals.add(dealType);
        }
    }

    public static List<DealType> getDailyDeals() {
        return dailyDeals;
    }

    public static List<Item> getItemForSpend() {
        return itemForSpend;
    }
}
