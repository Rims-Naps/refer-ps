package com.ruse.world.content.zonechests;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BoxesTracker;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

public class CorruptKeyChest {

    //TODO LOOT
    private static final int KEY_ID = 3512;
    private static final  int[] SUPER_RARE = new int[] {2651, 2653, 2655, 430};
    private static final int[] RARE = new int[] {2657, 2658, 2659, 2660, 2661, 2662, 2619, 3503, 3504, 3507, 3508, 3509 , 2009};
    private static final int[] UNCOMMON = new int[] {12423, 12424, 12427, 12834, 12835, 12836, 12830, 12831, 12832};
    private static final int[] COMMON = new int[] {995,995,1302, 1303, 1304, 995, 19062, 3576, 6466, 3502, 1448};
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
        // p.msgPurp("You open the Chest..");
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
        if (Misc.random(player_save_chance) == 1 && player.getMembershipTier().isMember()) {
            player.msgFancyPurp("Your Membership Saved your key upon opening!");
            player.getInventory().addDropIfFull(KEY_ID, 1);
        }

        if (player.getNodesUnlocked() != null) {
            if (player.getSkillTree().isNodeUnlocked(Node.DEFT_HANDS)) {
                if (Misc.random(85 ) == 1) {
                    player.msgFancyPurp("Your skill tree perk saved a key");
                    player.getInventory().addDropIfFull(KEY_ID, 1);
                }
            }
        }

        if (Misc.getRandom(800) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("@red@<shad=0>[CORRUPT]<shad=0>@red@ " + player.getUsername() + " @red@<shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " @red@<shad=0>from the Corrupt Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_KEY, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(250) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_KEY, new Item(rares));

        }
        else if (Misc.getRandom(75) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommons, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_KEY, new Item(uncommons));


        } else {

            int commons = getRandomItem(COMMON);
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_KEY, new Item(commons));

            if (ItemDefinition.forId(commons).getId() == 6466){
                int amount_essence = Misc.random(5,45);
                player.getInventory().addDropIfFull(commons, amount_essence);
                player.sendMessage("rolled " + amount_essence + " Beast Essence from chest");
            } else if (ItemDefinition.forId(commons).getId() == 3576){
                int amount_slay_essence = Misc.random(5,65);
                player.getInventory().addDropIfFull(commons, amount_slay_essence);
                player.sendMessage("rolled " + amount_slay_essence + " Slayer Essence from chest");
            } else if (ItemDefinition.forId(commons).getId() == 19062){
                int amount_monster_essence = Misc.random(5,25);
                player.getInventory().addDropIfFull(commons, amount_monster_essence);
                player.sendMessage("rolled " + amount_monster_essence + " Monster Essence from chest");
            } else if (ItemDefinition.forId(commons).getId() == 3502){
                int amount_corrupt_essence = Misc.random(5,15);
                player.getInventory().addDropIfFull(commons, amount_corrupt_essence);
                player.sendMessage("rolled " + amount_corrupt_essence + " Corrupt Essence from chest");
            } else if (ItemDefinition.forId(commons).getId() == 995){
                int amount_coins = Misc.random(325,1500);
                player.getInventory().addDropIfFull(commons, amount_coins);
                player.sendMessage("rolled " + amount_coins + " Coins from chest");
            } else {
                player.getInventory().addDropIfFull(commons, 1);
            }
        }
    }

    public static final Item[] itemRewards = {

            new Item(1302, 1),
            new Item(1303, 1),
            new Item(1304, 1),
            new Item(995, 1),
            new Item(19062, 35),
            new Item(3576, 1),
            new Item(6466, 1),
            new Item(3502, 1),
            new Item(1448, 1),
            new Item(12423, 1),
            new Item(12424, 1),
            new Item(12427, 1),
            new Item(12834, 1),
            new Item(12835, 1),
            new Item(12836, 1),
            new Item(12830, 1),
            new Item(12831, 1),
            new Item(12832, 1),
            new Item(2657, 1),
            new Item(2658, 1),
            new Item(2659, 1),
            new Item(2660, 1),
            new Item(2661, 1),
            new Item(2662, 1),
            new Item(2619, 1),
            new Item(3503, 1),
            new Item(3504, 1),
            new Item(3507, 1),
            new Item(3508, 1),
            new Item(3509, 1),
            new Item(2651, 1),
            new Item(2653, 1),
            new Item(2655, 1),
            new Item(430, 1),

    };
}
