package com.ruse.world.content.newupgrade;

import com.ruse.world.entity.impl.player.Player;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemUpgrader {

    private final List<Integer> items = new ArrayList<>();

    private final SecureRandom random = new SecureRandom();

    private final int STARTING_POINT = 33425;

    private final Player player;

    public ItemUpgrader(Player player) {
        this.player = player;
    }

    public void open() {
        player.getPacketSender().sendInterface(STARTING_POINT);
        updateInterface();
    }

    private void updateInterface() {
        player.getPacketSender()
                .resetItemsOnInterface(STARTING_POINT + 9, 1);
        player.getPacketSender()
                .sendString(STARTING_POINT + 6, "Bags required: @gre@0");
        player.getPacketSender()
                .sendString(STARTING_POINT + 7, "Upgrade Chance:");
        currentUpgrade = null;
        int[] items = ItemUpgradeData.getItems();
        player.getPacketSender().sendInterfaceIntArray(STARTING_POINT + 21, items);
    }

    private int winningIndex = -1;

    private boolean spinning = false;

    public boolean isSpinning() {
        return spinning;
    }

    public int getWinningIndex() {
        return winningIndex;
    }

    private ItemUpgradeData currentUpgrade;

    public void handleSelection(int index) {
        if (spinning) {
            return;
        }
        currentUpgrade = ItemUpgradeData.getByIndex(index);
        player.getPacketSender()
                .sendItemOnInterface(STARTING_POINT + 9, currentUpgrade.getReward(), 1);
        player.getPacketSender()
                .sendString(STARTING_POINT + 6, "Bags required: @gre@" + currentUpgrade.getBagsRequired());
        player.getPacketSender()
                .sendString(STARTING_POINT + 7, "Upgrade Chance: @gre@" + currentUpgrade.getChance() + "%");
    }

    public void onFinish(int index) {
        if (index != winningIndex) {
            System.out.println("Wrong index " + index + " | " + winningIndex);
            return;
        }

        boolean gotReward = items.get(index + 1) != -1;
        if (gotReward) {
            player.getInventory().add(currentUpgrade.getReward(), 1);
            player.sendMessage("Your upgrade was successful :)");
        } else {
            player.sendMessage("Your upgrade was unsuccessful :(");
        }

        spinning = false;
    }

    public void upgrade() {
        if (currentUpgrade == null) {
            player.sendMessage("You haven't selected an item to upgrade");
            return;
        }
        if (spinning) {
            return;
        }

        if (player.getInventory().getFreeSlots() == 0) {
            player.sendMessage("@red@You have no free inventory slots");
            return;
        }

        if (player.getInventory().contains(currentUpgrade.getRequiredId()) && player.getInventory()
                .contains(995, currentUpgrade.getBagsRequired())) {
            player.getInventory().delete(currentUpgrade.getRequiredId(), 1);
            player.getInventory()
                    .delete(995, currentUpgrade.getBagsRequired());
            setupItems();
            winningIndex = 4 + random.nextInt((int) ((items.size() / 2.5D)));
            player.getPacketSender().sendUpgradeSpinNotification(winningIndex);
            spinning = true;
        } else {
            if (!player.getInventory().contains(currentUpgrade.getRequiredId())) {
                player.sendMessage("You don't have the required item for this upgrade");
            } else {
                player.sendMessage("@red@You don't have enough tax bags for this upgrade, required amount: <col=AF70C3>" + currentUpgrade
                        .getBagsRequired());
            }
        }
    }

    private void setupItems() {
        items.clear();
        int reward = currentUpgrade.getReward();
        int chance = currentUpgrade.getChance();

        int count = 30 + random.nextInt(50);

        for (int i = 0; i < count; i++) {
            if (random.nextInt(100) < chance) {
                items.add(reward);
            } else {
                items.add(-1);
            }
        }

        Collections.shuffle(items);
        player.getPacketSender().sendInterfaceItemList(STARTING_POINT + 85, items);

    }


}
