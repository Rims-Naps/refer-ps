package com.ruse.world.content;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;

public class BoxesTracker {

    public static void submit(Player player, BoxesTracker.BoxEntry[] kills) {
        for (BoxesTracker.BoxEntry kill : kills) {
            if (kill != null)
                submit(player, kill);
        }
    }

    public static void submit(Player player, BoxesTracker.BoxEntry kill) {
        int index = getIndex(player, kill);
        if (index >= 0) {
            player.getBoxTracker().get(index).amount += kill.amount;
        } else {
            player.getBoxTracker().add(kill);
        }
        /*
         * if(player.isKillsTrackerOpen()) { open(player); }
         */
    }

    public static int getIndex(Player player, BoxesTracker.BoxEntry kill) {
        for (int i = 0; i < player.getBoxTracker().size(); i++) {
            if (player.getBoxTracker().get(i).itemName.equals(kill.itemName)) {
                return i;
            }
        }
        return -1;
    }

    public static BoxesTracker.BoxEntry entryForID(Player player, int itemId, boolean boss) {
        //If the task tracker contains a entry for the npc return the entry.
        for (BoxesTracker.BoxEntry killsEntry : player.getBoxTracker()) {
            if(killsEntry.getId() == itemId)
                return killsEntry;
        }
        //If the kill tracker does not contain a entry for the npc return a new entry with 0 kills.
        BoxesTracker.BoxEntry entry = new BoxesTracker.BoxEntry(itemId, 0);
        player.getBoxTracker().add(entry);
        return entry;
    }

    public static class BoxEntry {

        public BoxEntry(int itemId, int amount) {
            this.itemName = ItemDefinition.forId(itemId).getName();
            this.amount = amount;
            this.runningTotal = amount;
            this.itemId = itemId;
        }

        public int getId() {
            return itemId;
        }
        public int getAmount() {
            return amount;
        }
        public int getRunningTotal() {
            return this.runningTotal;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }
        public void setRunningTotal(int amount) {
            this.runningTotal = amount;
        }

        public int runningTotal;
        public int itemId;
        public String itemName;
        public int amount;

    }


    public static int getTotalBoxesOpened(Player player) {
        int totalKills = 0;
        for(BoxesTracker.BoxEntry entry : player.getBoxTracker()) {
            totalKills += entry.getAmount();
        }
        return totalKills;
    }

    public static int getTotalBoxesOpenedForItem(int itemId, Player player) {
        int total = 0;
        for(BoxesTracker.BoxEntry entry : player.getBoxTracker()) {
            if (entry.getId() != itemId) {
                continue;
            }
            total += entry.getAmount();
        }
        return total;
    }
}
