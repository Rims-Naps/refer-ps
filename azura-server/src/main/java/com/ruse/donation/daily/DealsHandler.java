package com.ruse.donation.daily;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;

import java.util.List;

public class DealsHandler {

    public static void attemptClaim(int amtDonated, Player plr, int itemDonatedFor, int quantity) {
        List<DealType> deals = DailyDealSelector.getDailyDeals();
        List<Item> item = DailyDealSelector.getItemForSpend();
        ItemDefinition def = ItemDefinition.forId(itemDonatedFor);
        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
        // Use the 'deals' list as needed in your server code
        // For example, iterate through the list and perform actions based on the daily deals
        for (DealType deal : deals) {
            // Perform actions based on each deal type
            int toGive = 0;
            switch (deal) {
                case SPEND_25:
                case SPEND_50:
                case SPEND_100:
                    plr.getInventory().add(item.get(0));
                    plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    break;
                case GET_30_PERCENT_BONDS:
                    toGive = (int) (amtDonated * 0.30);
                    plr.getInventory().add(new Item(10945, toGive));
                    plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    break;
                case BUY_OWNER_GB_U_GET_1_FREE:
                    if (def.getId() == 4022) {
                        plr.getInventory().add(itemDonatedFor, quantity);
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                break;
                case DONATE_100_GET_2_FREE_ELE_CACHE:
                    if (amtDonated >= 100) {
                        plr.getInventory().add(17129, 2);
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                break;
                case BUY_1_ELE_CACHE_U_GET_1_FREE:
                    if (def.getId() == 2624) {
                        plr.getInventory().add(itemDonatedFor, quantity);
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                break;
                case BUY_T3_MEM_GET_FREE_VOID_CRATE:
                    if (def.getId() == 2703) {
                        plr.getInventory().add(19118, 1);
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                break;
                case SPENT_50_GET_EXTRA_DONO_ISLAND_PULL:
                    if (amtDonated >= 50 && amtDonated < 100) {
                        if (plr.getIsland().getTierOfPick() != null) {
                            plr.setIslandPicks(plr.getIslandPicks() + 1);
                            plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                        }
                    } else if (amtDonated >= 100) {
                        if (plr.getIsland().getTierOfPick() != null) {
                            plr.setIslandPicks(plr.getIslandPicks() + 1);
                            plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                        }
                    }
                break;
                case HALF_COST_COSMETIC_TICKETS:
                    toGive = amtDonated / 250;
                    plr.getInventory().add(new Item(17125, toGive));
                    plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    break;
                case BUY_2_GET_1_FREE_BOXES:
                    toGive = quantity / 2;
                    if (def.getName().contains("box") || def.getName().contains("crate")) {
                        plr.getInventory().add(new Item(itemDonatedFor, toGive));
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                    break;
                case OWNER_GOODIEBAG:
                    if (amtDonated >= 100) {
                        plr.getInventory().add(new Item(3578, 1));
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                    break;
                case PURCHASE_GET_SAME_BOX_FREE:
                    if (def.getName().contains("box") || def.getName().contains("crate")) {
                        plr.getInventory().add(new Item(itemDonatedFor, quantity));
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                    break;
                case DOUBLE_MEMBERSHIP_PURCHASE:
                    if (def.getName().contains("members")) {
                        plr.getInventory().add(new Item(itemDonatedFor, quantity));
                        plr.setDailyDealsClaimed(plr.getDailyDealsClaimed() + 1);
                    }
                    break;
            }
        }
    }
}
