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

public class CorruptRaidChest {
    private static final int KEY_ID = 770;

    private static final  int[] AXE_RARE = new int[] {441};
    private static final  int[] RING_RARE = new int[] {7245, 7247, 7249};
    private static final  int[] SUPER_RARE = new int[] {417, 406, 407, 408, 409, 410, 10256, 10260, 10262, 15668,16415,16416,16417,17024,17025,17026,17027,17028,17029,17030,17031,17032,17033,17034,17035,17036,17037,17038};
    private static final int[] RARE = new int[] {10945};
    private static final int[] UNCOMMON = new int[] {416, 401, 402, 403, 404, 405};
    private static final int[] COMMON = new int[] { 15667, 1446, 20104, 20105, 20106};

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
        //TODO NEW ACHIEVE HANDLING
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

        if (Misc.getRandom(400) == 1) {
            int axerares = getRandomItem(AXE_RARE);

            player.getInventory().addDropIfFull(axerares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[Raids]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(axerares).getName() + " <col=AF70C3><shad=0>from the Raid Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(axerares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_RAID, new Item(axerares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(200) == 1) {
            int ringRares = getRandomItem(RING_RARE);
            player.getInventory().addDropIfFull(ringRares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[Raids]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(ringRares).getName() + " <col=AF70C3><shad=0>from the Raid Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(ringRares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_RAID, new Item(ringRares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(100) == 1) {
            int superrares = getRandomItem(SUPER_RARE);

            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[Raids]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Raid Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_RAID, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(75) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_RAID, new Item(rares));

        }
        else if (Misc.inclusiveRandom(12) == 1) {
            player.getInventory().addDropIfFull(416, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_RAID, new Item(416));

        } else {
            int commons = getRandomItem(COMMON);
            player.getInventory().addDropIfFull(commons, 1);
            player.getInventory().addDropIfFull(995, Misc.random(20,50));
            player.getCollectionLogManager().addItem(CollectionLogs.CORRUPT_RAID, new Item(commons));

        }
    }

    public static final Item[] itemRewards = {
            // SUPER RARE items
            new Item(995, 50),
            new Item(15667, 1),
            new Item(1446, 1),
            new Item(20104, 1),
            new Item(20105, 1),
            new Item(20106, 1),
            new Item(416, 1),
            new Item(401, 1),
            new Item(402, 1),
            new Item(403, 1),
            new Item(404, 1),
            new Item(405, 1),
            new Item(406, 1),
            new Item(407, 1),
            new Item(408, 1),
            new Item(409, 1),
            new Item(410, 1),
            new Item(10945, 1),
            new Item(417, 1),
            new Item(10256, 1),
            new Item(10260, 1),
            new Item(10262, 1),
            new Item(15668, 1),
            new Item(7245, 1),
            new Item(7247, 1),
            new Item(7249, 1),
            new Item(441, 1),

            new Item(16415, 1),
            new Item(16416, 1),
            new Item(16517, 1),
            new Item(17024, 1),
            new Item(17025, 1),
            new Item(17026, 1),
            new Item(17027, 1),
            new Item(17028, 1),
            new Item(17029, 1),
            new Item(17030, 1),
            new Item(17031, 1),
            new Item(17032, 1),
            new Item(17033, 1),
            new Item(17034, 1),
            new Item(17035, 1),
            new Item(17036, 1),
            new Item(17037, 1),
            new Item(17038, 1),




    };


}
