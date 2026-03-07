package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.util.Misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FrozenCasket {
    public enum BoxData {
        DEFAULT2(1, 995, 25),
        DEFAULT3(5, 1450, 5),
        DEFAULT4(10, 1450, 20),
        DEFAULT5(15, 1451, 1),
        DEFAULT6(75, 1451, 1),
        DEFAULT7(85, 1452, 1),
        DEFAULT8(90, 1453, 1),
        DEFAULT9(93, 1465, 1),
        DEFAULT10(99, 1452, 1);
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
        itemsObtained.add(new Item(995, 1)); //Default Item
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
        plr.getBoxTracker().add(new BoxesTracker.BoxEntry(1454, 1));
        plr.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(1454, plr) + " " + ItemDefinition.forId(1454).getName());

        if (plr.getInventory().getFreeSlots() < 2) {
            plr.msgRed("Make some space in your inventory before opening this Casket!");
            return;
        }
            plr.getInventory().add(new Item(itemsObtained.get(0).getId(), itemsObtained.get(0).getAmount()));
            plr.getInventory().delete(1454, 1);
            //HolidayManager.increaseMeter(plr,.03);

    }
}

