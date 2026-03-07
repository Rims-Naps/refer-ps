package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.util.Misc;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavorCasket {
    public enum BoxData {
        DEFAULT2(1, 995, 5),
        DEFAULT22(1, 995, 10),
        DEFAULT23(1, 995, 25),
        DEFAULT3(5, 19062, 2),
        DEFAULT33(5, 19062, 3),
        DEFAULT34(5, 19062, 6),
        DEFAULT4(5, 11054, 2),
        DEFAULT45(5, 11054, 5),
        DEFAULT5(5, 11052, 2),
        DEFAULT54(5, 11052, 5),
        DEFAULT6(5, 11056, 2),
        DEFAULT65(5, 11056, 5),
        DEFAULT7(85, 15667, 1),
        DEFAULT8(90, 15668, 1),
        DEFAULT9(93, 17130, 1);

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
                    case 19062: // Monster Essence
                        int monsterEssenceAmount = item.getAmount();
                        plr.setMonsteressence(plr.getMonsteressence() + monsterEssenceAmount);
                        itemAddedToPouch = true;
                        plr.sendMessage("You have received " + monsterEssenceAmount + " Monster Essence, added to your pouch.");
                        break;
                    case 11054: // Fire Essence
                        int fireEssenceAmount = item.getAmount();
                        plr.setFireessence(plr.getFireessence() + fireEssenceAmount);
                        itemAddedToPouch = true;
                        plr.sendMessage("You have received " + fireEssenceAmount + " Fire Essence, added to your pouch.");
                        break;
                    case 11056: // Water Essence
                        int waterEssenceAmount = item.getAmount();
                        plr.setWateressence(plr.getWateressence() + waterEssenceAmount);
                        itemAddedToPouch = true;
                        plr.sendMessage("You have received " + waterEssenceAmount + " Water Essence, added to your pouch.");
                        break;
                    case 11052: // Earth Essence
                        int earthEssenceAmount = item.getAmount();
                        plr.setEarthessence(plr.getEarthessence() + earthEssenceAmount);
                        itemAddedToPouch = true;
                        plr.sendMessage("You have received " + earthEssenceAmount + " Earth Essence, added to your pouch.");
                        break;
                    case 3576: // Slayer Essence
                        int slayerEssenceAmount = item.getAmount();
                        plr.setSlayeressence(plr.getSlayeressence() + slayerEssenceAmount);
                        itemAddedToPouch = true;
                        plr.sendMessage("You have received " + slayerEssenceAmount + " Slayer Essence, added to your pouch.");
                        break;
                    case 6466: // Beast Essence
                        int beastEssenceAmount = item.getAmount();
                        plr.setBeastEssence(plr.getBeastEssence() + beastEssenceAmount);
                        itemAddedToPouch = true;
                        plr.sendMessage("You have received " + beastEssenceAmount + " Beast Essence, added to your pouch.");
                        break;
                    case 3502: // Corrupt Essence
                        int corruptEssenceAmount = item.getAmount();
                        plr.setCorruptEssence(plr.getCorruptEssence() + corruptEssenceAmount);
                        itemAddedToPouch = true;
                        plr.sendMessage("You have received " + corruptEssenceAmount + " Corrupt Essence, added to your pouch.");
                        break;
                }
            }

            if (!itemAddedToPouch) {
                plr.getInventory().add(item);
            }
        }

        plr.getBoxTracker().add(new BoxesTracker.BoxEntry(730, 1));
        plr.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(730, plr) + " " + ItemDefinition.forId(730).getName());
        plr.getInventory().delete(730, 1);
        //HolidayManager.increaseMeter(plr, .03);
    }
}


