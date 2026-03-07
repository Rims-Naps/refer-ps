package com.ruse.world.content;

import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.entity.impl.player.Player;

public class OwnerSetDyes {
    private static int HAT = 15792;
    private static int BODY = 15793;
    private static int LEGS = 15794;
    private static int GLOVES = 15795;
    private static int BOOTS = 15796;
    private static int CAPE = 19944;


    private static int C_HAT = 9950;
    private static int C_BODY = 9951;
    private static int C_LEGS = 9952;
    private static int C_GLOVES = 9953;
    private static int C_BOOTS = 9954;
    private static int C_CAPE = 3522;


    private static int I_HAT = 9955;
    private static int I_BODY = 9956;
    private static int I_LEGS = 9957;
    private static int I_GLOVES = 9958;
    private static int I_BOOTS = 9959;

    private static int I_CAPE = 3521;


    private static int P_HAT = 9960;
    private static int P_BODY = 9961;
    private static int P_LEGS = 9962;
    private static int P_GLOVES = 9963;
    private static int P_BOOTS = 9964;

    private static int P_CAPE = 3520;


    private static int CORRUPT_DYE = 3507;
    private static int ICY_DYE = 3508;
    private static int POISON_DYE = 3509;


    public static void corruptHatDye(Player p) {
        if (p.getInventory().contains(HAT) && p.getInventory().contains(CORRUPT_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Hat(Corrupt)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(HAT,1);
                        p.getInventory().delete(CORRUPT_DYE,1);
                        p.getInventory().add(C_HAT,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }
    public static void corruptBodyDye(Player p) {
        if (p.getInventory().contains(BODY) && p.getInventory().contains(CORRUPT_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Body(Corrupt)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(BODY,1);
                        p.getInventory().delete(CORRUPT_DYE,1);
                        p.getInventory().add(C_BODY,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void corruptLegsDye(Player p) {
        if (p.getInventory().contains(LEGS) && p.getInventory().contains(CORRUPT_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Legs(Corrupt)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(LEGS,1);
                        p.getInventory().delete(CORRUPT_DYE,1);
                        p.getInventory().add(C_LEGS,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void corruptGlovesDye(Player p) {
        if (p.getInventory().contains(GLOVES) && p.getInventory().contains(CORRUPT_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Gloves(Corrupt)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(GLOVES,1);
                        p.getInventory().delete(CORRUPT_DYE,1);
                        p.getInventory().add(C_GLOVES,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void corruptBootsDye(Player p) {
        if (p.getInventory().contains(BOOTS) && p.getInventory().contains(CORRUPT_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Boots(Corrupt)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(BOOTS,1);
                        p.getInventory().delete(CORRUPT_DYE,1);
                        p.getInventory().add(C_BOOTS,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void corruptCapeDye(Player p) {
        if (p.getInventory().contains(CAPE) && p.getInventory().contains(CORRUPT_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Cape(Corrupt)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(CAPE,1);
                        p.getInventory().delete(CORRUPT_DYE,1);
                        p.getInventory().add(C_CAPE,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }


    public static void poisonHatDye(Player p) {
        if (p.getInventory().contains(HAT) && p.getInventory().contains(POISON_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Hat(Poison)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(HAT,1);
                        p.getInventory().delete(POISON_DYE,1);
                        p.getInventory().add(P_HAT,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void poisonBodyDye(Player p) {
        if (p.getInventory().contains(BODY) && p.getInventory().contains(POISON_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Body(Poison)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(BODY,1);
                        p.getInventory().delete(POISON_DYE,1);
                        p.getInventory().add(P_BODY,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void poisonLegsDye(Player p) {
        if (p.getInventory().contains(LEGS) && p.getInventory().contains(POISON_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Legs(Poison)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(LEGS,1);
                        p.getInventory().delete(POISON_DYE,1);
                        p.getInventory().add(P_LEGS,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void poisonGlovesDye(Player p) {
        if (p.getInventory().contains(GLOVES) && p.getInventory().contains(POISON_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Gloves(Poison)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(GLOVES,1);
                        p.getInventory().delete(POISON_DYE,1);
                        p.getInventory().add(P_GLOVES,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void poisonBootsDye(Player p) {
        if (p.getInventory().contains(BOOTS) && p.getInventory().contains(POISON_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Gloves(Poison)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(BOOTS,1);
                        p.getInventory().delete(POISON_DYE,1);
                        p.getInventory().add(P_BOOTS,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void poisonCapeDye(Player p) {
        if (p.getInventory().contains(CAPE) && p.getInventory().contains(POISON_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Cape(Poison)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(CAPE,1);
                        p.getInventory().delete(POISON_DYE,1);
                        p.getInventory().add(P_CAPE,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }


    public static void icyHatDye(Player p) {
        if (p.getInventory().contains(HAT) && p.getInventory().contains(ICY_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Hat(Icy)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(HAT,1);
                        p.getInventory().delete(ICY_DYE,1);
                        p.getInventory().add(I_HAT,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void icyBodyDye(Player p) {
        if (p.getInventory().contains(BODY) && p.getInventory().contains(ICY_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Body(Icy)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(BODY,1);
                        p.getInventory().delete(ICY_DYE,1);
                        p.getInventory().add(I_BODY,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void icyLegsDye(Player p) {
        if (p.getInventory().contains(LEGS) && p.getInventory().contains(ICY_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Legs(Icy)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(LEGS,1);
                        p.getInventory().delete(ICY_DYE,1);
                        p.getInventory().add(I_LEGS,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void icyGlovesDye(Player p) {
        if (p.getInventory().contains(GLOVES) && p.getInventory().contains(ICY_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Gloves(Icy)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(GLOVES,1);
                        p.getInventory().delete(ICY_DYE,1);
                        p.getInventory().add(I_GLOVES,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }
    public static void icyBootsDye(Player p) {
        if (p.getInventory().contains(BOOTS) && p.getInventory().contains(ICY_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Boots(Icy)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(BOOTS,1);
                        p.getInventory().delete(ICY_DYE,1);
                        p.getInventory().add(I_BOOTS,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void icyCapeDye(Player p) {
        if (p.getInventory().contains(CAPE) && p.getInventory().contains(ICY_DYE)) {
            new SelectionDialogue(p, "",
                    new SelectionDialogue.Selection("Dye Cape(Icy)", 0, player -> {
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                        p.getInventory().delete(CAPE,1);
                        p.getInventory().delete(ICY_DYE,1);
                        p.getInventory().add(I_CAPE,1);
                    }),
                    new SelectionDialogue.Selection("Not right now...", 1, player -> p.getPacketSender().sendChatboxInterfaceRemoval())).start();
        }
    }

    public static void sendConfirmation(Player p, int armor_piece) {


    }
}
