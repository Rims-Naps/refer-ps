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

public class Tier8Chest {
    private static final int KEY_ID = 20112;
    private static final  int[] SUPER_RARE = new int[] {
            1494, 1495, 1496, 1497, 1498, 1499,
            1500, 1501, 1502, 1503,1504, 1505,
            1506, 1507, 1508, 1509, 1510, 1511,
            1512, 1513, 1514, 1515,1516, 1517,
            1518, 1519, 1520, 1521, 1522, 1524};
    private static final int[] RARE = new int[] {
            1488, 1489, 1490, 1491, 1492, 1493,//t7 rares
            1470, 1471, 1472, 1473, 1474, 1475};//t7 rares
    private static final int[] UNCOMMON = new int[] {730, 10258,995, 20066, 20067,20071};
    private static final int[] COMMON = new int[] {995,10258};

    public static int getRandomItem(int[] array) {
        return array[Misc.getRandom(array.length - 1)];
    }

    public static void handleChest(Player p) {
        if (!p.getClickDelay().elapsed(3000))
            return;
        if (!p.getInventory().contains(KEY_ID)) { //key id
            p.getPacketSender().sendMessage("<shad=0>@red@You need a key to open the chest!");
            return;
        }
        p.performAnimation(new Animation(827));
        p.performGraphic(new Graphic(1322));
        p.getInventory().delete(KEY_ID, 1);
        p.getPacketSender().sendMessage("<shad=0><col=AF70C3>You open the Chest..");
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
        if (Misc.getRandom(200) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[Elemental]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Elemental Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_8, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(100) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_8, new Item(rares));

        }

        else if (Misc.getRandom(50) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommons, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_8, new Item(uncommons));

        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().addDropIfFull(commons, 1);
            player.getInventory().addDropIfFull(995, 100);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_8, new Item(commons));

        }
    }

    public static final Item[] itemRewards = {
            // SUPER RARE items
            new Item(1494, 1), new Item(1495, 1), new Item(1496, 1), new Item(1497, 1), new Item(1498, 1), new Item(1499, 1), //
            new Item(1500, 1), new Item(1501, 1), new Item(1502, 1), new Item(1503, 1), new Item(1504, 1), new Item(1505, 1), //
            new Item(1506, 1), new Item(1507, 1), new Item(1508, 1), new Item(1509, 1), new Item(1510, 1), new Item(1511, 1), //

            new Item(1512, 1), new Item(1513, 1), new Item(1514, 1), new Item(1515, 1), new Item(1516, 1), new Item(1517, 1), //
            new Item(1518, 1), new Item(1519, 1), new Item(1520, 1), new Item(1521, 1), new Item(1522, 1), new Item(1524, 1), //

            // RARE items
            new Item(1488, 1), new Item(1489, 1), new Item(1490, 1), new Item(1491, 1), new Item(1492, 1), new Item(1493, 1), //
            new Item(1470, 1), new Item(1471, 1), new Item(1472, 1), new Item(1473, 1), new Item(1474, 1), new Item(1475, 1), //

            // UNCOMMON items
            new Item(730, 1),new Item(10258, 1), new Item(20066, 1), new Item(20067, 1),new Item(20071, 1),

            // COMMON item
            new Item(995, 1)
    };



}
