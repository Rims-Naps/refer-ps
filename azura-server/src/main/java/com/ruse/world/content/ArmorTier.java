package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

@Getter
public enum ArmorTier {
    GALACTIC(new int[]{19090, 19091, 19092, 19093, 19094, 19095, 19096, 19097}, 100, 1000000, 15000),
    TIER_1(new int[]{11305, 11306, 11307, 11181, 11182, 10200, 10202, 10204}, 55, 1500000, 45000),
    TIER_2(new int[]{3510, 3511, 3512, 3513, 3514, 10205, 10206, 10208}, 45, 2000000, 65000),
    TIER_3(new int[]{3515, 3516, 3517, 3518, 3520, 10210, 10212, 10214}, 35, 2500000, 75000),
    TIER_4(new int[]{3522, 3524, 3525, 3526, 3528, 10218, 10220, 10222}, 25, 5000000, 100000);

    private final int[] itemIds;
    private final double successRate;
    private final int coinCost;
    private final int ticketCost;

    ArmorTier(int[] itemIds, double successRate, int coinCost, int ticketCost) {
        this.itemIds = itemIds;
        this.successRate = successRate;
        this.coinCost = coinCost;
        this.ticketCost = ticketCost;
    }


    public int[] getItemIds() {
        return itemIds;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public int getCoinCost() {
        return coinCost;
    }

    public int getTicketCost() {
        return ticketCost;
    }


    public void handleUpgrade(Player player, Item usedItem) {
        ArmorTier currentTier = null;
        ArmorTier nextTier = null;

        for (ArmorTier tier : ArmorTier.values()) {
            if (IntStream.of(tier.getItemIds()).anyMatch(id -> id == usedItem.getId())) {
                currentTier = tier;
                break;
            }
        }

        if (currentTier == null) {
            player.sendMessage("@bla@<shad=0>This item cannot be upgraded.");
            return;
        }

        if (currentTier.ordinal() < ArmorTier.values().length - 1) {
            nextTier = ArmorTier.values()[currentTier.ordinal() + 1];
        } else {
            player.sendMessage("@bla@<shad=0>This item is already at the maximum upgrade tier.");
            return;
        }

        if (!playerHasRequirements(player, currentTier, nextTier)) {
            player.sendMessage("@bla@<shad=0>You do not meet the requirements for this upgrade.");
            return;
        }

        //UGRADE
        performUpgrade(player, usedItem, currentTier, nextTier);
    }

    private boolean playerHasRequirements(Player player, ArmorTier currentTier, ArmorTier nextTier) {
        if (currentTier == ArmorTier.GALACTIC) {
            return player.getInventory().contains(10224, currentTier.getTicketCost());
        } else {
            return player.getInventory().contains(995, currentTier.getCoinCost()) &&
                    player.getInventory().contains(10224, currentTier.getTicketCost());
        }
    }


    private boolean checkUpgradeRequirements(Player player, ArmorTier currentTier) {
        int missingCoins = currentTier.getCoinCost() - player.getInventory().getAmount(995);
        int missingTickets = currentTier.getTicketCost() - player.getInventory().getAmount(10224);

        if (missingCoins > 0) {
            player.sendMessage("@bla@<shad=0>You are missing @red@<shad=0> " + missingCoins + "@bla@<shad=0> coins to attempt the upgrade.");
        }
        if (missingTickets > 0) {
            player.sendMessage("@bla@<shad=0>You are missing @red@<shad=0> " + missingTickets + "@bla@<shad=0> tickets to attempt the upgrade.");
        }

        return missingCoins <= 0 && missingTickets <= 0;
    }


    private void performUpgrade(Player player, Item usedItem, ArmorTier currentTier, ArmorTier nextTier) {
        if (!checkUpgradeRequirements(player, currentTier)) {
            return;
        }
        TimerTask task = new TimerTask() {
            int tick = 0;

            @Override
            public void run() {
                if (tick == 0) {
                    player.getPacketSender().sendMessage("@bla@<shad=0>You attempt to Empower your item...");
                    // Add player animation here, e.g. player.performAnimation(yourAnimation);
                    player.getInventory().delete(995, currentTier.getCoinCost());
                    player.getInventory().delete(10224, currentTier.getTicketCost());
                } else if (tick == 2) {
                    double successRate = currentTier.getSuccessRate();
                    boolean upgradeSuccess = Math.random() * 100 <= successRate;
                    int index = -1;
                    for (int i = 0; i < currentTier.getItemIds().length; i++) {
                        if (currentTier.getItemIds()[i] == usedItem.getId()) {
                            index = i;
                            break;
                        }
                    }

                    if (upgradeSuccess || currentTier == ArmorTier.GALACTIC) {
                        int upgradedItemId = nextTier.getItemIds()[index];
                        player.getInventory().delete(usedItem);
                        player.getInventory().add(new Item(upgradedItemId));

                        player.sendMessage("@bla@<shad=0>Congratulations! Your <col=AF70C3><shad=0>" + usedItem.getDefinition().getName() + "@bla@<shad=0> upgraded to " +
                                new Item(upgradedItemId).getDefinition().getName() + ".");

                        World.sendMessage("@red@<shad=0><img=12>[ETHEREAL]<img=12><col=AF70C3><shad=0>" + player.getUsername() + " @red@<shad=0> upgraded their <col=AF70C3><shad=0>" +
                                usedItem.getDefinition().getName() + "@red@<shad=0> to <col=AF70C3><shad=0> " +
                                new Item(upgradedItemId).getDefinition().getName() + "@red@<shad=0>!");
                        //ACHIEVEMENTS
                    } else {
                        player.getInventory().delete(usedItem);

                        if (currentTier != ArmorTier.TIER_1) {
                            int downgradedItemId = ArmorTier.values()[currentTier.ordinal() - 1].getItemIds()[index];
                            player.getInventory().add(new Item(downgradedItemId));

                            player.sendMessage("@bla@<shad=0>Unfortunately, your upgrade attempt has failed. Your @red@<shad=0>" + usedItem.getDefinition().getName() +
                                    " @bla@<shad=0>has been downgraded to <col=AF70C3><shad=0>" + new Item(downgradedItemId).getDefinition().getName() + ".");

                            World.sendMessage(" @red@<shad=0><img=12>[ETHEREAL]<img=12>@blu@<shad=0> " + player.getUsername() + " @red@<shad=0>failed their@blu@<shad=0> " +
                                    usedItem.getDefinition().getName() + "@red@<shad=0> to @blu@<shad=0>" +
                                    new Item(nextTier.getItemIds()[index]).getDefinition().getName() +
                                    "@red@<shad=0> and was downgraded to @blu@<shad=0>" +
                                    new Item(downgradedItemId).getDefinition().getName() + "@red@<shad=0>.");
                        } else {
                            player.getInventory().add(usedItem);
                            player.sendMessage("@bla@<shad=0>Unfortunately, your upgrade attempt has failed.");

                            World.sendMessage(" @red@<shad=0><img=12>[ETHEREAL]<img=12>@blu@<shad=0> " + player.getUsername() + " @red@<shad=0>failed their@blu@<shad=0> " +
                                    usedItem.getDefinition().getName() + "@red@<shad=0> to @blu@<shad=0>" +
                                    new Item(nextTier.getItemIds()[index]).getDefinition().getName() + "@bla@<shad=0>.");
                        }
                    }
                    cancel();
                }
                tick++;
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 500, 500);
    }
}
