package com.ruse.world.content.box;

import com.ruse.model.Item;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class SupplyCrate {

    public static double[][] items = {
        {23173, 1},
        {10256, 0.2},
        {10262, 0.2},
        {10260, 0.2},
        {15668, 0.1},
        {15669, 0.1},
        {17129, 0.05},
        {23172, 0.04},
        {2624, 0.03},
        {3578, 0.02},
        {19118, 0.01},
    };

    public static void rollCrate(Player player) {
        Random random = new Random();
        Item item = null;
        if (!player.getInventory().contains(2008)) {
            player.sendMessage("You require a supply crate to do this.");
            return;
        }
        for (int i = 0; i < items.length; i++) {
            double randomNumber = random.nextDouble();
            if (randomNumber < items[i][1]) {
                item = new Item((int) items[i][0], 1);
            }
        }
        if (item == null) {
            return;
        }
        int tab_item_potion = Bank.getTabForItem(player, new Item(1448));
        player.getBank(tab_item_potion).add(new Item(1448, 3));
        int tab_item_resource_1 = Bank.getTabForItem(player, new Item(1446));
        player.getBank(tab_item_resource_1).add(new Item(1446, 3));
        int tab_item_resource_2 = Bank.getTabForItem(player, new Item(1447));
        player.getBank(tab_item_resource_2).add(new Item(1447, 2));
        int tab_item_box = Bank.getTabForItem(player, item);
        player.getBank(tab_item_box).add(item);
        player.sendMessage("You receive a " + ItemDefinition.forId(item.getId()).getName() + " from the supply crate.");
        player.getInventory().delete(2008, 1);
    }
}
