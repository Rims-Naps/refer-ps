package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.util.Misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CashBag {
    public enum BoxData {
        DEFAULT2(1, 995, 24),
        DEFAULT3(5, 995, 29),
        DEFAULT4(5, 995, 32),
        DEFAULT5(5, 995, 37),
        DEFAULT6(75, 995, 44),
        DEFAULT7(85, 995, 76),
        DEFAULT8(90, 995, 103),
        DEFAULT9(93, 995, 116),
        DEFAULT10(99, 995, 1000);
        int chance, itemId, quantity;

        BoxData(int chance, int id, int amount) {
            this.chance = chance;
            this.itemId = id;
            this.quantity = amount;
        }

        public int getChance() {
            return chance;
        }

        public int getItemId() {
            return itemId;
        }

        public int getQuantity() {
            return quantity;
        }
    }

    public static void openAll(Player p, int max) {
        int max_opens = Math.min(max, 100);

        for (int i = 0; i < max_opens; i++) {
            openbox(p);
        }
    }

    public static void openbox(Player plr) {
        int bestChanced = 0;
        ArrayList<Item> itemsObtained = new ArrayList<>();
        List<BoxData> boxData = new ArrayList<>();
        for (BoxData box : BoxData.values()) {
            boxData.add(box);
        }
        Collections.shuffle(boxData);
        itemsObtained.add(new Item(995, 5)); //Default Item
        for (int i = 0; i < boxData.size(); i++) {
            int random = Misc.random(1, boxData.get(i).getChance());
            if (random == 1) {
                if (boxData.get(i).getChance() > bestChanced) {
                    itemsObtained.remove(0);
                    bestChanced = boxData.get(i).getChance();
                    itemsObtained.add(new Item(boxData.get(i).getItemId(), boxData.get(i).getQuantity()));
                }
            }
        }
        if (plr.getInventory().getFreeSlots() < 2) {
            plr.msgRed("Make some space in your inventory before opening this Casket!");
            return;
        }
        if (plr.getInventory().getFreeSlots() > 2) {
            plr.getInventory().add(new Item(itemsObtained.get(0).getId(), itemsObtained.get(0).getAmount()));
            plr.getInventory().delete(10258, 1);
        }
    }
}

