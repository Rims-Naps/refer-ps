package com.ruse.world.content;

import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;

public class TreasureTrinket {
    public static boolean forgeTrinket(Player player) {

        boolean hasRequiredItems = player.getInventory().contains(CORRUPT_TREASURE_SHARD,1) && player.getInventory().contains(SLAYER_TREASURE_SHARD,1) && player.getInventory().contains(BEAST_TREASURE_SHARD,1);

        if (!hasRequiredItems){
            player.msgRed("You need all 3 Treasure shards to forge a Artifact Trinket.");
            return true;
        }

        player.performAnimation(new Animation(713));
        player.performGraphic(new Graphic(311));
        player.getInventory().delete(CORRUPT_TREASURE_SHARD,1);
        player.getInventory().delete(SLAYER_TREASURE_SHARD,1);
        player.getInventory().delete(BEAST_TREASURE_SHARD,1);
        player.msgFancyPurp("You combine all 3 shards into a Trinket.");
        World.sendMessage("@red@<shad=0>[Artifact] <col=F5AD05>" + player.getUsername() + " @red@forged a <col=F5AD05>Artifact Trinket!");

        player.getInventory().add(TREASURE_TRINKET,1);
        return true;
    }

    public static boolean openTrinket(Player player) {

        boolean hasTrinket = player.getInventory().contains(438,1);

        if (!hasTrinket){
            player.msgRed("You need a Trinket to Open this.");
            return true;
        }

        Item item = loot[Misc.inclusiveRandom(loot.length - 1)];
        player.performAnimation(new Animation(1818));
        player.performGraphic(new Graphic(2051));
        player.getInventory().delete(TREASURE_TRINKET,1);
        player.getInventory().add(item);
        World.sendMessage("@red@<shad=0>[Artifact] " + player.getUsername() + " @red@received a <col=F5AD05>" + item.getDefinition().getName() + "@red@ from a <col=F5AD05>Artifact Trinket!");
        player.msgGold("You manage to open the trinket and find a " + item.getDefinition().getName() + "!");
        return true;
    }

    private static final int UNIQUE_1 = 433;
    private static final int UNIQUE_2 = 434;
    private static final int UNIQUE_3 = 435;
    private static final int UNIQUE_4 = 436;
    private static final int UNIQUE_5 = 2950;

    public static final Item[] loot = {new Item(UNIQUE_1, 1), new Item(UNIQUE_2, 1), new Item(UNIQUE_3, 1), new Item(UNIQUE_4, 1), new Item(UNIQUE_5, 1)};
    private static final int CORRUPT_TREASURE_SHARD = 430;
    private static final int SLAYER_TREASURE_SHARD = 431;
    private static final int BEAST_TREASURE_SHARD = 432;
    private static final int TREASURE_TRINKET = 438;
    
}



