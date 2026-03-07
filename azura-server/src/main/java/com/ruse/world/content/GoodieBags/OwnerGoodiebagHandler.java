package com.ruse.world.content.GoodieBags;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.entity.impl.player.Player;

public class OwnerGoodiebagHandler {


    private static int[] AMULET_TABLE =
            {17122, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int[] RING_TABLE =
            {17123, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int[] CAPE_TABLE =
            {19944, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int[] HAT_TABLE =
            {15792, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int[] BODY_TABLE =
            {15793, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int[] LEGS_TABLE =
            {15794, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int[] GLOVES_TABLE =
            {15795, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int[] BOOTS_TABLE =
            {15796, 15668, 17129, 23057, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946, 10946};
    private static int OWNER_GOODIEBAG = 3578;

    public static boolean open(Player player) {
        if (!player.getClickDelay().elapsed(1000))
            return false;
        
        if (!player.getInventory().containsAny(OWNER_GOODIEBAG, 1))
            return false;

        if (!player.getViewing().equals(Player.INTERFACES.GOODIEBAG))
            return false;

        player.getClickDelay().reset();
        /** 1-4 roll is done to choose loot table that will fill the interface **/
        int TABLES_CHANCE = Misc.random(1, 8);

        switch(TABLES_CHANCE){
            case 1:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = AMULET_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: AMULET TABLE");
                break;
            case 2:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = RING_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: RING TABLE");
                break;
            case 3:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = CAPE_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: CAPE TABLE");
                break;
            case 4:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = HAT_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: HAT TABLE");
                break;
            case 5:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = BODY_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: BODY TABLE");
                break;
            case 6:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = LEGS_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: LEGS TABLE");
                break;
            case 7:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = GLOVES_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: GLOVES TABLE");
                break;
            case 8:
                player.getGoodieBag().boxId = OWNER_GOODIEBAG;
                player.getGoodieBag().rewards = BOOTS_TABLE;
                player.getGoodieBag().open();
                System.out.println("TABLE ROLLED: GLOVES TABLE");
                break;
        }

        return false;
    }

    public static Box[] loot = {
            new Box(15792, 1, 0.5, false),
            new Box(15793, 1, 0.5, false),
            new Box(15794, 1, 0.5, false),
            new Box(15795, 1, 0.5, false),
            new Box(15796, 1, 0.5, false),
            new Box(17122, 1, 0.5, false),
            new Box(17123, 1, 0.5, false),
            new Box(19944, 1, 0.5, false),
            new Box(10946, 1, 0.5, false),
            new Box(17129, 1, 0.5, false),
            new Box(23057, 1, 0.5, false),

    };
}
