package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.world.World;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class MediumWeaponBox {

    private static final int BOX_ID = 18;

    private static final int SUPER_RARE = 17672;
    private static final int[] RARE = new int[] {5010, 23132};
    private static final int UNCOMMON = 14065;
    private static final int[] COMMON = new int[] {16249, 23066};

    public static void handleBox(Player p) {
        if (!p.getClickDelay().elapsed(3000))
            return;
        if (!p.getInventory().contains(BOX_ID)) { //key id
            p.getPacketSender().sendMessage("You need a medium weapon box to do this.");
            return;
        }

        p.getInventory().delete(BOX_ID, 1);
        p.getPacketSender().sendMessage("You open the medium weapon box...");

        generateReward(p);
    }

    public static void generateReward(Player player){
        Random rng = new Random();
        int roll = new Random().nextInt(100);

        if (roll > 97){
            player.getInventory().add(SUPER_RARE, 1);
            World.sendMessage("@cya@[SUPER RARE] @red@" + player.getUsername() + " got a SUPER RARE reward from the medium weapon box!");
        } else if (roll > 85){
            player.getInventory().add(RARE[rng.nextInt(RARE.length)], 1);
        } else if (roll > 50){
            player.getInventory().add(UNCOMMON, 1);
        } else {
            player.getInventory().add(COMMON[rng.nextInt(COMMON.length)], 1);
        }
    }

    public static final Item[] itemRewards = {
            new Item(16249, 1), // white
            new Item(23066, 1), // blue
            new Item(14065, 1), // yellow
            new Item(5010, 1), // red
            new Item(23132, 1), // purp
            new Item(17672, 1), // green
    };

    public static Box[] loot = { //
            new Box(16249, 1, 35, false),
            new Box(23066, 1, 35, false),
            new Box(14065, 1, 20, false),
            new Box(5010, 1, 4.75, false),
            new Box(23132, 1, 4.75, false),
            new Box(17672, 1, 0.5, true),
    };
}
