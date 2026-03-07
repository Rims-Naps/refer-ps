package com.ruse.world.entity.impl;

import com.ruse.model.GroundItem;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class GlobalItemSpawner {

    public static Position ROCKCAKE_POSITION = new Position(3667, 2994, 0);
    private static long timer = System.currentTimeMillis();

    public static void startup() {
        if (System.currentTimeMillis() - timer > 1000 * 60) {
            World.sendGlobalGroundItems();
        }
    }

    public static void spawnGlobalGroundItems(Player player) {
        long a = System.currentTimeMillis();
        long b = System.currentTimeMillis();
        timer = System.currentTimeMillis();
    }

    private static void nullCheckAndSpawn(Player player, Item item, Position pos) {
        if (GroundItemManager.getGroundItem(player, item, pos) == null) {
            //GroundItemManager.spawnGroundItem(player, new GroundItem(item, pos, player.getUsername(), false, 60 * 60, false, 0)); // each player will have
            // an instance of the
            // shovel, will last
            // 60*60 seconds (1 hr)
        }
    }

}
