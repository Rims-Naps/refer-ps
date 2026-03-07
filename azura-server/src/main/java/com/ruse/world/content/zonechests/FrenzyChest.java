package com.ruse.world.content.zonechests;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BoxesTracker;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class FrenzyChest {
    private static final int KEY_ID = 6754;
    private static final  int[] SUPER_RARE = new int[] {23058, 23172, 3578};
    private static final int[] RARE = new int[] {23057, 15671, 12427, 17130, 1447};
    private static final int[] UNCOMMON = new int[] {23173, 15669, 10946, 15668, 1448};
    private static final int[] COMMON = new int[] {5585,10258, 15667, 1446, 5585, 10945};

    public static int getRandomItem(int[] array) {
        return array[Misc.getRandom(array.length - 1)];
    }

    public static void handleChest(Player p) {
        if (!p.getClickDelay().elapsed(3000))
            return;
        if (!p.getInventory().contains(KEY_ID)) { //key id
            p.msgRed("<shad=0>@red@You need a key to open the chest!");
            return;
        }
        p.performAnimation(new Animation(827));
        p.performGraphic(new Graphic(1322));
        p.getInventory().delete(KEY_ID, 1);
        p.getDailyTaskInterface().MiscTasksCompleted(0, 1);
        generateReward(p);
        p.getBoxTracker().add(new BoxesTracker.BoxEntry(KEY_ID, 1));
        p.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(KEY_ID, p) + " " + ItemDefinition.forId(KEY_ID).getName());
        p.setTotalKeysOpened(p.getTotalKeysOpened() + 1);

    }

    public static void openAll(Player p, int max) {
        int max_opens = Math.min(max, 100);

        for (int i = 0; i < max_opens; i++) {
            handleChest(p);
        }
    }

    public static void generateReward(Player player){
        int player_save_chance = (int) player.getMembershipTier().getSave_key_chance();

        if (Misc.getRandom(750) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().add(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[FRENZY]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Frenzy Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
        }
        else if (Misc.getRandom(200) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().add(rares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[FRENZY]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(rares).getName() + " <col=AF70C3><shad=0>from the Frenzy Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(rares).getName() + " @bla@<shad=0>from the chest!");
        }
        else if (Misc.getRandom(30) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().add(uncommons, 1);
        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().add(commons, Misc.random(1,3));

        }
    }

    public static final Item[] itemRewards = {
            // SUPER RARE items
            new Item(1, 1),
            new Item(1, 1),
            new Item(1, 1),
            new Item(1, 1),
            new Item(1, 1),
            // RARE items
            new Item(1, 1),
            new Item(1, 1),
            new Item(1, 1),
            new Item(1, 1),
            // UNCOMMON items
            new Item(1, 1),
            new Item(1, 1),
            // COMMON items
            new Item(1, 25),
    };
}