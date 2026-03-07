package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.util.Misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HuntersPurse {
    public enum BoxData {

        DEFAULT1(1, 3576, 5),
        DEFAULT11(1, 3576, 10),
        DEFAULT111(2, 3576, 15),
        DEFAULT1112(2, 3576, 20),
        DEFAULT2(2, 3576, 14),
        DEFAULT3(5, 3576, 19),
        DEFAULT4(5, 3576, 12),
        DEFAULT5(5, 3576, 37),
        DEFAULT6(5, 3576, 10),
        DEFAULT7(5, 3576, 15),
        DEFAULT8(15, 3576, 73),
        DEFAULT9(15, 3576, 15),
        DEFAULT98(50, 3576, 76),
        DEFAULT988(50, 3576, 10),
        DEFAULT9888(75, 3576, 90),
        DEFAULT98888(75, 3576, 10),
        DEFAULT988884(93, 3576, 15),
        DEFAULT9888854(93, 3576, 10),
        DEFAULT98887854(93, 3576, 10),
        DEFAULT10(99, 3576, 10);
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
        itemsObtained.add(new Item(3576, 1)); //Default Item
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
            plr.sendMessage("Make some space in your inventory before opening this Casket!");
        }
        if (plr.getInventory().getFreeSlots() > 2) {
            if (plr.getInventory().contains(18015, 1)) {
                plr.getInventory().delete(456, 1);
                plr.setSlayeressence(plr.getSlayeressence() + itemsObtained.get(0).getAmount());
                plr.msgPurp(itemsObtained.get(0).getAmount() + " Slayer Essence added to your pouch");
                return;
            }
            plr.getInventory().add(new Item(itemsObtained.get(0).getId(), itemsObtained.get(0).getAmount()));
            plr.getInventory().delete(456, 1);
        }
    }
}

