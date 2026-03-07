package com.ruse.world.content;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class StarterChest {

    private static final int[] RARE = new int[] {10834, 19115, 23172};
    private static final int [] COMMON = new int[] {21204, 28, 6833};

    public static void handleChest(Player p) {
        System.out.println("line 19");
        if (!p.getClickDelay().elapsed(3000))
            return;
        if (!p.getInventory().contains(21215)) { //key id
            p.getPacketSender().sendMessage("This chest can only be opened with a Starter Chest key.");
            return;
        }
//		if (command) {
//			p.performAnimation(new Animation(1818));
        //	} else {
        p.performAnimation(new Animation(827));
        //}
        p.performGraphic(new Graphic(1322));
        p.getInventory().delete(21215, 1);
        p.getPacketSender().sendMessage("You open the chest..");


        //Item reward = itemRewards[Misc.randomMinusOne(itemRewards.length)];
        //p.getInventory().add(reward);
        generateReward(p);
    }


    public static void generateReward(Player player){
        Random rng = new Random();
        int roll = new Random().nextInt(100);

        if (roll > 75) {
            player.getInventory().add(RARE[rng.nextInt(RARE.length)], 1);
        } else {
            player.getInventory().add(COMMON[rng.nextInt(COMMON.length)], 1);
        }

    }
    public static final Item[] itemRewards = {


            new Item(10834, 1), //
            new Item(23172, 1), // 8
            new Item(19115, 1), // 9
            new Item(21204, 1), // 10
            new Item(28, 1), // 10
            new Item(6833, 1), // 10


    };

}


