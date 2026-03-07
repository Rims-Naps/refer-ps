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


public class Tier2Chest {
    private static final int KEY_ID = 20105;
    private static final  int[] SUPER_RARE = new int[] {
            13044, 13045, 13046, 13047, 13048, 14004, 18799, 5010,//MAGMITE
            19811, 19473, 19472, 19946, 19945, 23055, 23056, 13943,//MOSSHEART
            21015, 21016, 21017, 21040, 21041, 13049, 13056, 13063};//SURGE

    private static final int[] RARE = new int[] {
                13044, 13045, 13046, 13047, 13048, 14004, 18799, 5010,//MAGMITE
                17608, 17610, 17612, 5071, 5072, 23066, 23067, 14065};//WAVEBREAKER
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
        if (Misc.getRandom(150) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[Elemental]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Elemental Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_2, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");

        }
        else if (Misc.getRandom(75) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_2, new Item(rares));

        }

        else if (Misc.getRandom(50) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommons, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_2, new Item(uncommons));

        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().addDropIfFull(commons, 1);
            player.getInventory().addDropIfFull(995, 25);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_2, new Item(commons));

        }
    }

    public static final Item[] itemRewards = {
            // SUPER RARE items
            new Item(13044, 1), new Item(13045, 1), new Item(13046, 1), new Item(13047, 1),
            new Item(13048, 1), new Item(14004, 1), new Item(18799, 1), new Item(5010, 1), // MAGMITE
            new Item(19811, 1), new Item(19473, 1), new Item(19472, 1), new Item(19946, 1),
            new Item(19945, 1), new Item(23055, 1), new Item(23056, 1), new Item(13943, 1), // MOSSHEART
            new Item(21015, 1), new Item(21016, 1), new Item(21017, 1), new Item(21040, 1),
            new Item(21041, 1), new Item(13049, 1), new Item(13056, 1), new Item(13063, 1), // SURGE

            // RARE items
            new Item(13044, 1), new Item(13045, 1), new Item(13046, 1), new Item(13047, 1),
            new Item(13048, 1), new Item(14004, 1), new Item(18799, 1), new Item(5010, 1), // MAGMITE
            new Item(17608, 1), new Item(17610, 1), new Item(17612, 1), new Item(5071, 1),
            new Item(5072, 1), new Item(23066, 1), new Item(23067, 1), new Item(14065, 1), // WAVEBREAKER

            // UNCOMMON items
            new Item(730, 1),new Item(10258, 1), new Item(20066, 1), new Item(20067, 1),new Item(20071, 1),
            // COMMON
            new Item(995, 1) // Coins
    };

}
