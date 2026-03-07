package com.ruse.world.content;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

import java.util.Random;

public class RiftChest {
    private static final int CHEST_ID = 406;
    private static final int KEY_ID = 5585;
    private static final  int[] SUPER_RARE = new int[] {20000, 3507, 3508, 3509, 20001, 20002, 438};

    private static final  int[] MORE_RARE = new int[] {12427, 458, 12423, 12424, 16415, 16416, 2619, 16417, 16418, 16419, 16420,16421, 3007};
    private static final int[] RARE = new int[] {20082, 457, 23171,10946,15668,15670, 2619};
    private static final int[] UNCOMMON = new int[] {17582, 17584, 20066, 20067, 2009, 17586, 20069, 20071, 20068, 1448,1446};
    private static final int[] COMMON = new int[] {17582, 17584, 17586, 20069, 20071, 20068};

    public static void handleChest(Player p) {
        if (!p.getClickDelay().elapsed(3000))
            return;
        if (!p.getInventory().contains(KEY_ID)) { //key id
            p.getPacketSender().sendMessage("This chest can only be opened with an Rift Key.");
            return;
        }

        p.performAnimation(new Animation(827));
        p.performGraphic(new Graphic(1322));
        p.getInventory().delete(KEY_ID, 1);
        p.getPacketSender().sendMessage("You open the Rift Chest..");
        p.getDailyTaskInterface().MiscTasksCompleted(0, 1);
        generateReward(p);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_5_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_25_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_50_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_100_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_250_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_500_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_750_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_1000_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_1250_RIFT_CHESTS, 1);
        Achievements.doProgress(p, Achievements.Achievement.OPEN_1500_RIFT_CHESTS, 1);
        p.getBoxTracker().add(new BoxesTracker.BoxEntry(KEY_ID, 1));
        if (p.getStarterTasks().hasCompletedAll() && !p.getMediumTasks().hasCompletedAll()) {
            MediumTasks.doProgress(p, MediumTasks.MediumTaskData.OPEN_50_RIFT_KEYS, 1);
        }
        if (p.getMediumTasks().hasCompletedAll() && !p.getEliteTasks().hasCompletedAll()) {
            EliteTasks.doProgress(p, EliteTasks.EliteTaskData.OPEN_100_RIFT_KEYS, 1);
        }

        p.sendMessage("You have now opened " + BoxesTracker.getTotalBoxesOpenedForItem(KEY_ID, p) + " " + ItemDefinition.forId(KEY_ID).getName());

        p.setTotalKeysOpened(p.getTotalKeysOpened() + 1);
    }

    public static void openAll(Player p, int max) {
        int max_opens = Math.min(max, 100);

        for (int i = 0; i < max_opens; i++) {
            handleChest(p);
        }
    }

    public static void generateReward(Player player) {
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
        Random rng = new Random();
        if (Misc.getRandom(8500) == 1) {
            int superrares = SUPER_RARE[rng.nextInt(3)];
            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[RIFT]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a @red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Rift Chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.RIFT_KEY, new Item(superrares));
        } else if (Misc.getRandom(4000) == 1) {
            int morerares = MORE_RARE[rng.nextInt(MORE_RARE.length)];
            player.getInventory().addDropIfFull(morerares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[RIFT]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a @red@" +  ItemDefinition.forId(morerares).getName() + " <col=AF70C3><shad=0>from the Rift Chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.RIFT_KEY, new Item(morerares));
        } else if (Misc.getRandom(100) == 1) {
            int rares = RARE[rng.nextInt(RARE.length)];
            player.getInventory().addDropIfFull(rares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[RIFT]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a @red@" +  ItemDefinition.forId(rares).getName() + " <col=AF70C3><shad=0>from the Rift Chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.RIFT_KEY, new Item(rares));
            player.getInventory().addDropIfFull(995, 1000);
            player.msgFancyPurp("You received 1000 Bonus coins from the chest!");
        } else if (Misc.getRandom(50) == 1) {
            int uncommon = UNCOMMON[rng.nextInt(UNCOMMON.length)];
            player.getInventory().addDropIfFull(uncommon, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.RIFT_KEY, new Item(uncommon));
            player.getInventory().addDropIfFull(995, 500);
            player.msgFancyPurp("You received 500 Bonus coins from the chest!");

        } else {
            int common = COMMON[rng.nextInt(COMMON.length)];
            player.getInventory().addDropIfFull(common, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.RIFT_KEY, new Item(common));
            if (Misc.random(2) == 1){
                player.getInventory().addDropIfFull(995, 100);
                player.msgFancyPurp("You received 100 Bonus coins from the chest!");
            }
        }
    }



    public static final Item[] loot = {
            new Item(17582, 1),
            new Item(17584, 1),
            new Item(17586, 1),
            new Item(20069, 1),
            new Item(20071, 1),
            new Item(20068, 1),
            new Item(20082, 1),
            new Item(23171, 1),
            new Item(20066, 1),
            new Item(20067, 1),
            new Item(10946, 1),
            new Item(1448, 1),
            new Item(1446, 1),
            new Item(15668, 1),
            new Item(15670, 1),
            new Item(2619, 1),

            new Item(457, 1),
            new Item(458, 1),

            new Item(16415, 1),
            new Item(16416, 1),
            new Item(16417, 1),
            new Item(16418, 1),
            new Item(16419, 1),
            new Item(16420, 1),
            new Item(16421, 1),

            new Item(12427, 1),
            new Item(12423, 1),
            new Item(12424, 1),


            new Item(3507, 1),
            new Item(3508, 1),
            new Item(3509, 1),



            new Item(20000, 1), //VOID
            new Item(20001, 1), //VOID
            new Item(20002, 1), //VOID
    };
}
