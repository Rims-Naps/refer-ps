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

public class Tier1Chest {
    private static final int KEY_ID = 20104;
    private static final  int[] SUPER_RARE = new int[] {
            23139, 23140, 23141, 23142, 23143, 18332, 3745, 3743,//VERDANT
            13051, 13052, 13053, 13054,13055, 23039, 23064, 23065,//FLAMESTRIKE
            17608, 17610, 17612, 5071, 5072, 23066, 23067, 14065};//WAVEBREAKER
    private static final int[] RARE = new int[] {
            18588, 18589, 18590, 18591,18592, 16024, 13042, 14915,//CINDER
            15895, 15840, 15800, 16188,15789, 12930, 16879, 23144,//STONEWARD
            15645, 15646, 15647, 9057,9058, 23145, 23146, 23033};//TIDEWAVE
    private static final int[] UNCOMMON = new int[] {730, 10258,995, 20066, 20067,20071};
    private static final int[] COMMON = new int[] {995,10258};

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
        Achievements.doProgress(p, Achievements.Achievement.OPEN_50_ELEMENTAL_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_250_ELEMENTAL_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_750_ELEMENTAL_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_1500_ELEMENTAL_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_3000_ELEMENTAL_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_5000_ELEMENTAL_CHESTS, 1);
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

        if (Misc.getRandom(150) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().addDropIfFull(superrares, 1);

            World.sendMessage("<col=AF70C3><shad=0>[Elemental]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Elemental Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_1, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(75) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_1, new Item(rares));

        }
        else if (Misc.getRandom(50) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommons, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_1, new Item(uncommons));


        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().addDropIfFull(commons, 1);
            player.getInventory().addDropIfFull(995, 20);

            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_1, new Item(commons));

        }
    }

    public static final Item[] itemRewards = {
            // SUPER RARE items
            new Item(23139, 1), new Item(23140, 1), new Item(23141, 1), new Item(23142, 1),
            new Item(23143, 1), new Item(18332, 1), new Item(3745, 1), new Item(3743, 1), // VERDANT
            new Item(13051, 1), new Item(13052, 1), new Item(13053, 1), new Item(13054, 1),
            new Item(13055, 1), new Item(23039, 1), new Item(23064, 1), new Item(23065, 1), // FLAMESTRIKE
            new Item(17608, 1), new Item(17610, 1), new Item(17612, 1), new Item(5071, 1),
            new Item(5072, 1), new Item(23066, 1), new Item(23067, 1), new Item(14065, 1), // WAVEBREAKER

            // RARE items
            new Item(18588, 1), new Item(18589, 1), new Item(18590, 1), new Item(18591, 1),
            new Item(18592, 1), new Item(16024, 1), new Item(13042, 1), new Item(14915, 1), // CINDER
            new Item(15895, 1), new Item(15840, 1), new Item(15800, 1), new Item(16188, 1),
            new Item(15789, 1), new Item(12930, 1), new Item(16879, 1), new Item(23144, 1), // STONEWARD
            new Item(15645, 1), new Item(15646, 1), new Item(15647, 1), new Item(9057, 1),
            new Item(9058, 1), new Item(23145, 1), new Item(23146, 1), new Item(23033, 1), // TIDEWAVE

            // UNCOMMON items
            new Item(730, 1),new Item(10258, 1), new Item(20066, 1), new Item(20067, 1),new Item(20071, 1),
            // COMMON items
            new Item(995, 1), new Item(20066, 1), new Item(20068, 1)
    };


}
