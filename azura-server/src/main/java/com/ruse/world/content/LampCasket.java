package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.util.Misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LampCasket {
    public enum BoxData {
        DEFAULT2(1, 995, 2),
        DEFAULT22(1, 995, 3),
        DEFAULT23(1, 995, 4),

        DEFAULT3(5, 20067, 2),
        DEFAULT33(5, 20067, 3),
        DEFAULT34(5, 20067, 4),
        DEFAULT4(5, 20068, 2),
        DEFAULT45(5, 20068, 3),
        DEFAULT5(5, 20068, 4),

        DEFAULT54(5, 20069, 2),
        DEFAULT6(5, 20069, 3),
        DEFAULT65(5, 20069, 4),
        DEFAULT7(5, 20071, 2),
        DEFAULT8(5, 20071, 3),
        DEFAULT9(5, 20071, 4),
        DEFAULT93(5, 20066, 1),
        DEFAULT94(5, 20066, 1),
        DEFAULT95(5, 20066, 1);

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
        plr.getBoxTracker().add(new BoxesTracker.BoxEntry(733, 1));
        plr.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(733, plr) + " " + ItemDefinition.forId(733).getName());

        if (plr.getInventory().getFreeSlots() < 2) {
            plr.msgRed("Make some space in your inventory before opening this Casket!");
            return;
        }
        plr.getInventory().add(new Item(itemsObtained.get(0).getId(), itemsObtained.get(0).getAmount()));
        plr.getInventory().delete(733, 1);
        //HolidayManager.increaseMeter(plr,.03);

    }
}

