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

public class Tier4Chest {
    private static final int KEY_ID = 20107;
    private static final  int[] SUPER_RARE = new int[] {
            23021, 23022, 23023, 23024, 23025, 8087, 16871, 14006,//SEAFIRE
            13010, 13011, 13012, 13013,13014, 13328, 13329, 16875, 13330};//13330
    private static final int[] RARE = new int[] {
            18937, 18938, 18939, 18970, 18971, 14005, 18749, 18748,//MERCURIAL
            23034, 23035, 23036, 23037,23038, 18638, 12994, 18009, 18638, 16871};//VIRE
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
        if (Misc.getRandom(175) == 1) {
            int superrares = getRandomItem(SUPER_RARE);
            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[Elemental]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Elemental Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_4, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");

        }
        else if (Misc.getRandom(100) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_4, new Item(rares));

        }

        else if (Misc.getRandom(50) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommons, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_4, new Item(uncommons));

        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().addDropIfFull(commons, 1);
            player.getInventory().addDropIfFull(995, 40);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_4, new Item(commons));

        }
    }

    public static final Item[] itemRewards = {
            // SUPER RARE items
            new Item(23021, 1), new Item(23022, 1), new Item(23023, 1), new Item(23024, 1), new Item(23025, 1),
            new Item(13010, 1), new Item(13011, 1), new Item(13012, 1), new Item(13013, 1), new Item(13014, 1),
            new Item(18638, 1), new Item(16871, 1), new Item(13330, 1),
            // RARE items
            new Item(18937, 1), new Item(18938, 1), new Item(18939, 1), new Item(18970, 1), new Item(18971, 1),
            new Item(23034, 1), new Item(23035, 1), new Item(23036, 1), new Item(23037, 1), new Item(23038, 1),

            // UNCOMMON items
            new Item(730, 1),new Item(10258, 1), new Item(20066, 1), new Item(20067, 1),new Item(20071, 1),
            // COMMON item
            new Item(995, 1)
    };

}
