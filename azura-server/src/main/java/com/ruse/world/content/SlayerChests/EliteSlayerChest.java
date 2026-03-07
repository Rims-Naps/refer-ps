package com.ruse.world.content.SlayerChests;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

public class EliteSlayerChest {
    private static final int KEY_ID = 1304;
    private static final  int[] SUPER_RARE = new int[] { 23057, 16419, 406, 407, 408 ,409, 410 };
    private static final int[] RARE = new int[] {15670, 3582, 15668, 17039, 17040, 17041, 17042, 17043, 16418,22000,22001,22002};
    private static final int[] UNCOMMON = new int[] {10945, 17586, 3580, 15669,401,402,403,404,405};
    private static final int[] COMMON = new int[] {995,995,17584, 17586, 17582, 17584, 17586, 17582, 1448};

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

        if (Misc.getRandom(125) == 1) {
            int superrares = getRandomItem(SUPER_RARE);

            player.getInventory().addDropIfFull(superrares, 1);
            World.sendMessage("<col=AF70C3><shad=0>[SLAYER]<shad=0>@red@ " + player.getUsername() + " <col=AF70C3><shad=0>pulled a <shad=0>@red@" +  ItemDefinition.forId(superrares).getName() + " <col=AF70C3><shad=0>from the Slayer Chest!");
            player.sendMessage("@bla@<shad=0>You received a @red@<shad=0>"  + ItemDefinition.forId(superrares).getName() + " @bla@<shad=0>from the chest!");
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_1, new Item(superrares));
            player.getInventory().addDropIfFull(2617, 1);
            player.msgFancyPurp("You have received a Skill point while on your Journey.");
        }
        else if (Misc.getRandom(55) == 1) {
            int rares = getRandomItem(RARE);
            player.getInventory().addDropIfFull(rares, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_1, new Item(rares));

        }
        else if (Misc.getRandom(25) == 1) {
            int uncommons = getRandomItem(UNCOMMON);
            player.getInventory().addDropIfFull(uncommons, 1);
            player.getCollectionLogManager().addItem(CollectionLogs.ELEMENTAL_KEY_1, new Item(uncommons));


        } else {
            int commons = getRandomItem(COMMON);
            if (ItemDefinition.forId(commons).getId() == 995) {
                int amount_coins = Misc.random(135, 400);
                player.getInventory().addDropIfFull(commons, amount_coins);
                player.sendMessage("rolled " + amount_coins + " Coins from chest");
            } else {
                player.getInventory().addDropIfFull(commons, 1);
            }
        }
    }

    public static final Item[] itemRewards = {
            new Item(17584, 1),
            new Item(17586, 1),
            new Item(17582, 1),
            new Item(1448, 1),
            new Item(10945, 1),
            new Item(23057, 1),
            new Item(16906, 1),
            new Item(15670, 1),
            new Item(15668, 1),
            new Item(15669, 1),
            new Item(3580, 1),
            new Item(3582, 1),
            new Item(17039, 1),
            new Item(17040, 1),
            new Item(17041, 1),
            new Item(17042, 1),
            new Item(17043, 1),
            new Item(16418, 1),
            new Item(16419, 1),
            new Item(22000, 1),
            new Item(22001, 1),
            new Item(22002, 1),

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
    };
}
