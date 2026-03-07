package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.util.Misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeastCasket {
    public enum BoxData {
        SLAYER_ESSENCE(1, 6466, 4),
        SLAYER_ESSENCE1(1, 6466, 9),
        SLAYER_ESSENCE2(1, 6466, 12),
        SLAYER_ESSENCE3(1, 6466, 8),
        SLAYER_ESSENCE4(1, 6466, 24),
        SLAYER_ESSENCE5(1, 6466, 10),
        SLAYER_ESSENCE6(1, 6466, 16),
        SLAYER_ESSENCE7(1, 6466, 6),
        SLAYER_ESSENCE8(1, 6466, 18),
        COINS(5, 995, 10),
        COINS1(5, 995, 30),
        CO45INS1(5, 995, 24),
        COINS2(5, 995, 40),
        COINS34(5, 995, 20),
        COIN7S(5, 995, 70),
        COI5NS(5, 995, 30),
        COIN6S(5, 995, 24),
        SLAYER_ESS2ENCE8(4, 6466, 4),
        SLAYER_ESSE2NCE8(4, 6466, 20),
        SLAYER_ESS3ENCE8(4, 6466, 30),
        SLAY45ER_ESSENCE8(4, 6466, 40),
        SLAYE3R_ESSENCE8(4, 6466, 50),
        SLAYER_ESSENCE48(4, 6466, 60),
        SLAYERq_ESSENCE48(4, 6466, 70),
        SLAYER_ESSEdNCE48(4, 6466, 80),
        BEAST_KEY(90, 1305, 1),
        BEAST_KEY1(90, 1305, 3),
        MED_KEY(90, 1303, 4),
        MED_KEY1(92, 1303, 8),
        ELITE_KEY(95, 1304, 2),
        ELITE_KEY2(96, 1304, 4),
        BOND_1(98, 10945, 2),
        COINS_HIGH(99, 995, 5000),
        ESSENCE_HIGH(99, 6466, 1000);

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
        itemsObtained.add(new Item(995, 1)); // Default Item

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

        boolean hasPouch = plr.getInventory().contains(18015); // Check if player has a pouch

        for (Item item : itemsObtained) {
            boolean itemAddedToPouch = false;

            // Check if the item can be added to the pouch
            if (hasPouch) {
                switch (item.getId()) {
                    case 6466: // Beast Essence
                        plr.setBeastEssence(plr.getBeastEssence() + item.getAmount());
                        plr.sendMessage("You have received " + item.getAmount() + " Beast Essence, added to your pouch.");
                        itemAddedToPouch = true;
                        break;
                    // Add more cases for different types of items that go into the pouch if needed
                }
            }

            // If the item was not added to the pouch, add it to the inventory
            if (!itemAddedToPouch) {
                plr.getInventory().add(item);
            }
        }

        // Remove the casket from inventory after opening
        plr.getInventory().delete(300, 1);
    }
}