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

public class Tier5Chest {
    private static final int KEY_ID = 20108;
    private static final  int[] SUPER_RARE = new int[] {
            17075, 17076, 17077, 17078, 17079, 17110,//BEDROCK
            17081, 17083, 17084, 17085,17082, 17111};//CASCADE
    private static final int[] RARE = new int[] {
            17065, 17066, 17067, 17068, 17069, 17108,//AQUARION
            17070, 17071, 17072, 17073,17074, 17109};//IGNITION
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
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_5, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");

        }
        else if (Misc.getRandom(100) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_5, new Item(rares));

        }

        else if (Misc.getRandom(50) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommons, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_5, new Item(uncommons));

        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().addDropIfFull(commons, 1);
            player.getInventory().addDropIfFull(995, 45);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_5, new Item(commons));

        }
    }

    public static final Item[] itemRewards = {
            // SUPER RARE items
            new Item(17075, 1), new Item(17076, 1), new Item(17077, 1), new Item(17078, 1), new Item(17079, 1), new Item(17110, 1), // BEDROCK
            new Item(17081, 1), new Item(17083, 1), new Item(17084, 1), new Item(17085, 1), new Item(17082, 1), new Item(17111, 1), // CASCADE

            // RARE items
            new Item(17065, 1), new Item(17066, 1), new Item(17067, 1), new Item(17068, 1), new Item(17069, 1), new Item(17108, 1), // AQUARION
            new Item(17070, 1), new Item(17071, 1), new Item(17072, 1), new Item(17073, 1), new Item(17074, 1), new Item(17109, 1), // IGNITION

            // UNCOMMON items
            new Item(730, 1),new Item(10258, 1), new Item(20066, 1), new Item(20067, 1),new Item(20071, 1),
            // COMMON item
            new Item(995, 1)
    };


}
