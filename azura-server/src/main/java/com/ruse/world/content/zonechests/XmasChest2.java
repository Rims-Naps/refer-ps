package com.ruse.world.content.zonechests;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

public class XmasChest2 {
    private static final int KEY_ID = 1452;
    private static final  int[] SUPER_RARE = new int[] {1455, 1456, 1457, 1458 ,1459, 1460, 1466, 1461, 1462, 1463};
    private static final int[] RARE = new int[] {10945, 15667, 1452, 15668, 1453, 1465, 1454};
    private static final int[] UNCOMMON = new int[] {1450};
    private static final int[] COMMON = new int[] {995};

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
        if (Misc.random(player_save_chance) == 1 && player.getMembershipTier().isMember()) {
            player.msgFancyPurp("Your Membership Saved your key upon opening!");
            player.getInventory().add(KEY_ID, 1);
        }
        if (player.getNodesUnlocked() != null) {
            if (player.getSkillTree().isNodeUnlocked(Node.DEFT_HANDS)) {
                if (Misc.random(85 ) == 1) {
                    player.msgFancyPurp("Your skill tree perk saved a key");
                    player.getInventory().add(KEY_ID, 1);
                }
            }
        }

        //HolidayManager.increaseMeter(player,.03);

        if (Misc.getRandom(450) == 1) {
            int superrares = getRandomItem(SUPER_RARE);

            player.getInventory().add(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[CHRISTMAS]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Christmas Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            //player.getCollectionLogManager().addItem(CollectionLogs.CRHSITMAS_KEY_2, new Item(superrares));
            player.getInventory().add(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(125) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().add(rares, 1);
            //player.getCollectionLogManager().addItem(CollectionLogs.CRHSITMAS_KEY_2, new Item(rares));

        }
        else if (Misc.getRandom(20) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().add(uncommons, Misc.random(5,30));
            //player.getCollectionLogManager().addItem(CollectionLogs.CRHSITMAS_KEY_2, new Item(uncommons));
        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().add(commons, Misc.random(50,250));
            //player.getCollectionLogManager().addItem(CollectionLogs.CRHSITMAS_KEY_2, new Item(commons));

        }
    }

    public static final Item[] itemRewards = {

            // SUPER RARE items
            new Item(1455, 1),
            new Item(1456, 1),
            new Item(1457, 1),
            new Item(1458, 1),
            new Item(1459, 1),
            new Item(1460, 1),
            new Item(1466, 1),
            new Item(1461, 1),
            new Item(1462, 1),
            new Item(1463, 1),
            // RARE items
            new Item(1453, 1),
            new Item(15668, 1),
            new Item(10945, 1),
            new Item(15667, 1),
            new Item(1452, 1),
            // UNCOMMON items
            new Item(1450, 1),
            new Item(1465, 1),
            new Item(1454, 1),

            // COMMON items
            new Item(995, 25),
    };
}
