package com.ruse.world.content.GoodieBags;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.entity.impl.player.Player;

public class UpgOwnerHandler {

    private static int[] VOID_BLADE =
            {20000, 15668, 17129, 23057, 10946, 10946, 23058, 10946, 10946, 23058, 23058, 23058, 23058, 23058, 10946, 23058, 23058, 10946, 10946, 10946};
    private static int[] VOID_STAFF =
            {20002, 15668, 17129, 23057, 10946, 10946, 23058, 10946, 23058, 10946, 23058, 10946, 23058, 10946, 23058, 23058, 23058, 23058, 10946, 10946};
    private static int[] VOID_BOW =
            {20001, 15668, 17129, 23057, 10946, 23058, 23058, 23058, 10946, 10946, 23058, 23058, 10946, 10946, 23058, 10946, 10946, 10946, 23058, 10946};
    private static int[] AMULET_TABLE =
            {17124, 15668, 17129, 23057, 10946, 23058, 10946, 10946, 23058, 23058, 23058, 23058, 23058, 10946, 23058, 23058, 10946, 10946, 23058, 10946};
    private static int[] RING_TABLE =
            {10724, 15668, 17129, 23057, 10946, 23058, 10946, 23058, 10946, 23058, 10946, 23058, 23058, 10946, 10946, 23058, 23058, 10946, 23058, 10946};
    private static int[] CAPE_TABLE =
            {19944, 15668, 17129, 23057, 10946, 23058, 23058, 10946, 23058, 10946, 23058, 10946, 10946, 23058, 10946, 23058, 23058, 23058, 10946, 10946};
    private static int[] HAT_TABLE =
            {15792, 15668, 17129, 23057, 23058, 23058, 10946, 23058, 10946, 23058, 23058, 23058, 23058, 10946, 23058, 10946, 10946, 10946, 23058, 10946};
    private static int[] BODY_TABLE =
            {15793, 15668, 17129, 23057, 23058, 10946, 23058, 23058, 10946, 23058, 23058, 23058, 10946, 23058, 23058, 10946, 10946, 10946, 23058, 23058};
    private static int[] LEGS_TABLE =
            {15794, 15668, 17129, 23057, 23058, 10946, 10946, 23058, 23058, 23058, 23058, 10946, 23058, 23058, 23058, 10946, 10946, 10946, 23058, 23058};
    private static int[] GLOVES_TABLE =
            {15795, 15668, 17129, 23057, 10946, 23058, 23058, 23058, 23058, 23058, 10946, 10946, 23058, 10946, 10946, 10946, 10946, 10946, 23058, 10946};
    private static int[] BOOTS_TABLE =
            {15796, 15668, 17129, 23057, 10946, 23058, 23058, 23058, 23058, 10946, 23058, 23058, 10946, 23058, 10946, 10946, 10946, 23058, 23058, 10946};
    private static int OWNER_GOODIEBAG = 4022;

    public static boolean open(Player player) {
        if (!player.getClickDelay().elapsed(1000))
            return false;

        if (!player.getInventory().containsAny(OWNER_GOODIEBAG, 1))
            return false;

        if (!player.getViewing().equals(Player.INTERFACES.GOODIEBAG_U))
            return false;

        player.getClickDelay().reset();
        /** 1-4 roll is done to choose loot table that will fill the interface **/
        int TABLES_CHANCE = Misc.random(1, 11);

        switch(TABLES_CHANCE){
            case 1:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = AMULET_TABLE;
                player.getGoodieBagU().open();
                break;
            case 2:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = RING_TABLE;
                player.getGoodieBagU().open();
                break;
            case 3:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = CAPE_TABLE;
                player.getGoodieBagU().open();
                break;
            case 4:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = HAT_TABLE;
                player.getGoodieBagU().open();
                break;
            case 5:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = BODY_TABLE;
                player.getGoodieBagU().open();
                break;
            case 6:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = LEGS_TABLE;
                player.getGoodieBagU().open();
                break;
            case 7:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = GLOVES_TABLE;
                player.getGoodieBagU().open();
                break;
            case 8:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = BOOTS_TABLE;
                player.getGoodieBagU().open();
                break;
            case 9:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = VOID_BLADE;
                player.getGoodieBagU().open();
                break;
            case 10:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = VOID_STAFF;
                player.getGoodieBagU().open();
                break;
            case 11:
                player.getGoodieBagU().boxId = OWNER_GOODIEBAG;
                player.getGoodieBagU().rewards = VOID_BOW;
                player.getGoodieBagU().open();
                break;
        }

        return false;
    }

    public static Box[] loot = {
            new Box(20000, 1, 0.5, false),
            new Box(20001, 1, 0.5, false),
            new Box(20002, 1, 0.5, false),
            new Box(15792, 1, 0.5, false),
            new Box(15793, 1, 0.5, false),
            new Box(15794, 1, 0.5, false),
            new Box(15795, 1, 0.5, false),
            new Box(15796, 1, 0.5, false),
            new Box(17124, 1, 0.5, false),
            new Box(10724, 1, 0.5, false),
            new Box(19944, 1, 0.5, false),
            new Box(10946, 1, 0.5, false),
            new Box(17129, 1, 0.5, false),
            new Box(23057, 1, 0.5, false),

    };
}
