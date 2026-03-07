package com.ruse.world.content.Necromancy;

import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class RuneDropTables {

    private static int INSTANCE_TICKETS = 2706;

    private static int CURSE_RUNE = 20010;
    private static int SOUL_RUNE = 20014;
    private static int CRYPT_RUNE = 20015;
    private static int SHADOW_RUNE = 20012;
    private static int WRAITH_RUNE = 20011;
    private static int VOID_RUNE = 20013;


    public static void handleNpcKill(Player p, int killednpc) {

        switch (killednpc){

            /** Curse + Crypt Drops **/
            case RuneNpcData.Nythor:
            case RuneNpcData.Terran:
            case RuneNpcData.Aqualorn:
            case RuneNpcData.Ferna:
                handle_Curse_Crypt_Drop(p);
                break;
            /** Slayer Handle**/
            case RuneNpcData.ELEOPARD:
            case RuneNpcData.FLEOPARD:
            case RuneNpcData.WLEOPARD:
            case RuneNpcData.ECAYMAN:
            case RuneNpcData.FCAYMAN:
            case RuneNpcData.WCAYMAN:
            case RuneNpcData.EARTHHOUND:
            case RuneNpcData.FIREHOUND:
            case RuneNpcData.WATERHOUND:
                slayer_handle_Curse_Crypt_Drop(p);
                break;

            /** Curse + Soul Drops **/
            case RuneNpcData.Ignox:
            case RuneNpcData.Crystalis:
            case RuneNpcData.Ember:
            case RuneNpcData.Xerces:
                handle_Curse_Soul_Drop(p);
                break;
            /** Slayer Handle**/
            case RuneNpcData.EWOLF:
            case RuneNpcData.FWOLF:
            case RuneNpcData.WWOLF:
            case RuneNpcData.EDEVOURER:
            case RuneNpcData.FDEVOURER:
            case RuneNpcData.WDEVOURER:
            case RuneNpcData.EARTHCHIN:
            case RuneNpcData.FIRECHIN:
            case RuneNpcData.WATERCHIN:
                slayer_handle_Curse_Soul_Drop(p);
                break;

            /** Curse + Shadow + Crypt Drops **/
            case RuneNpcData.Marina:
            case RuneNpcData.Kezel:
            case RuneNpcData.Hydrora:
                handle_Curse_Shadow_Crypt_Drop(p);
                break;
            /** Slayer Handle**/
            case RuneNpcData.EBEAST:
            case RuneNpcData.FBEAST:
            case RuneNpcData.WBEAST:
            case RuneNpcData.ETURTLE:
            case RuneNpcData.FTURTLE:
            case RuneNpcData.WTURTLE:
            case RuneNpcData.EBRUTE:
            case RuneNpcData.FBRUTE:
            case RuneNpcData.WBRUTE:
                slayer_handle_Curse_Shadow_Crypt_Drop(p);
                break;

            /** Curse + Soul + Wraith Drops **/
            case RuneNpcData.Infernus:
            case RuneNpcData.Tellurion:
            case RuneNpcData.Marinus:
            case RuneNpcData.Pyrox:
                handle_Curse_Soul_Wraith_Drop(p);
                break;

            /** Void + Wraith + Shadow Drops **/
            case RuneNpcData.Astaran:
            case RuneNpcData.Nereus:
            case RuneNpcData.Volcar:
            case RuneNpcData.Lagoon:
            case RuneNpcData.Incendia:
            case RuneNpcData.Terra:
            case RuneNpcData.Abyss:
            case RuneNpcData.Pyra:
            case RuneNpcData.Geode:
            case RuneNpcData.Cerulean:
            case RuneNpcData.Scorch:
            case RuneNpcData.Geowind:
            case RuneNpcData.Goliath:
            case RuneNpcData.Volcanus:
            case RuneNpcData.Nautilus:
            case RuneNpcData.Quake:
            case RuneNpcData.Scaldor:
            case RuneNpcData.Seabane:
            case RuneNpcData.Rumble:
            case RuneNpcData.Moltron:
            case RuneNpcData.Hydrox:
                handle_Void_Wraith_Shadow_Drop(p);
                break;
            /** Beast Hunter Handle**/
            case RuneNpcData.BRIMSTONE:
            case RuneNpcData.EVERTHORN:
            case RuneNpcData.BASILISK:
                slayer_handle_Void_Wraith_Shadow_Drop(p);
                break;
        }

    }


    /** CURSE + CRYPT **/
    public static void handle_Curse_Crypt_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,100);
        int runeSelection = Misc.random(0,1);
        int curse_rune_amount = Misc.random(1,4);
        int crypt_rune_amount = Misc.random(1,4);
        int instance_ticket_chance = Misc.random(0,20);
        int instance_ticket_amount = Misc.random(0,10);
    /*    if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/
        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setCryptRunes(p.getCryptRunes() + crypt_rune_amount);
                else p.getInventory().add(CRYPT_RUNE, crypt_rune_amount);
            }
        }
    }
    public static void slayer_handle_Curse_Crypt_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,25);
        int runeSelection = Misc.random(0,1);
        int curse_rune_amount = Misc.random(2,4);
        int crypt_rune_amount = Misc.random(2,4);
        int instance_ticket_chance = Misc.random(0,10);
        int instance_ticket_amount = Misc.random(0,25);
       /* if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/
        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setCryptRunes(p.getCryptRunes() + crypt_rune_amount);
                else p.getInventory().add(CRYPT_RUNE, crypt_rune_amount);
            }
        }
    }

    /** CURSE + SOUL **/
    public static void handle_Curse_Soul_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,100);
        int runeSelection = Misc.random(0,1);
        int curse_rune_amount = Misc.random(1,4);
        int soul_rune_amount = Misc.random(1,4);
        int instance_ticket_chance = Misc.random(0,20);
        int instance_ticket_amount = Misc.random(0,20);
    /*    if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/
        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setSoulRunes(p.getSoulRunes() + soul_rune_amount);
                else p.getInventory().add(SOUL_RUNE, soul_rune_amount);
            }
        }
    }

    public static void slayer_handle_Curse_Soul_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,25);
        int runeSelection = Misc.random(0,1);
        int curse_rune_amount = Misc.random(2,4);
        int soul_rune_amount = Misc.random(2,4);
        int instance_ticket_chance = Misc.random(0,10);
        int instance_ticket_amount = Misc.random(0,50);
      /*  if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/
        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setSoulRunes(p.getSoulRunes() + soul_rune_amount);
                else p.getInventory().add(SOUL_RUNE, soul_rune_amount);
            }
        }
    }

    /** CURSE + SHADOW + CRYPT **/
    public static void handle_Curse_Shadow_Crypt_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,100);
        int runeSelection = Misc.random(0,2);
        int curse_rune_amount = Misc.random(1,4);
        int shadow_rune_amount = Misc.random(1,4);
        int crypt_rune_amount = Misc.random(1,3);

        int instance_ticket_chance = Misc.random(0,20);
        int instance_ticket_amount = Misc.random(0,35);
     /*   if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/

        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setShadowRunes(p.getShadowRunes() + shadow_rune_amount);
                else p.getInventory().add(SHADOW_RUNE, shadow_rune_amount);
            }
            if (runeSelection == 2) {
                if (pouch_in_inventory) p.setCryptRunes(p.getCryptRunes() + crypt_rune_amount);
                else p.getInventory().add(CRYPT_RUNE, crypt_rune_amount);
            }
        }
    }
    public static void slayer_handle_Curse_Shadow_Crypt_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,25);
        int runeSelection = Misc.random(0,2);
        int curse_rune_amount = Misc.random(2,4);
        int shadow_rune_amount = Misc.random(2,4);
        int crypt_rune_amount = Misc.random(2,3);
        int instance_ticket_chance = Misc.random(0,10);
        int instance_ticket_amount = Misc.random(0,50);
      /*  if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/

        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setShadowRunes(p.getShadowRunes() + shadow_rune_amount);
                else p.getInventory().add(SHADOW_RUNE, shadow_rune_amount);
            }
            if (runeSelection == 2) {
                if (pouch_in_inventory) p.setCryptRunes(p.getCryptRunes() + crypt_rune_amount);
                else p.getInventory().add(CRYPT_RUNE, crypt_rune_amount);
            }
        }
    }

      /** CURSE + SOUL + WRAITH **/
      public static void handle_Curse_Soul_Wraith_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,100);
        int runeSelection = Misc.random(0,2);
        int curse_rune_amount = Misc.random(1,4);
        int soul_rune_amount = Misc.random(1,4);
        int wraith_rune_amount = Misc.random(1,3);
        int instance_ticket_chance = Misc.random(0,20);
        int instance_ticket_amount = Misc.random(0,35);
      /*    if (instance_ticket_chance == 0){
              p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

          }*/

        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setSoulRunes(p.getSoulRunes() + soul_rune_amount);
                else p.getInventory().add(SOUL_RUNE, soul_rune_amount);
            }
            if (runeSelection == 2) {
                if (pouch_in_inventory) p.setWraithRunes(p.getWraithRunes() + wraith_rune_amount);
                else p.getInventory().add(WRAITH_RUNE, wraith_rune_amount);
            }
        }
    }
    public static void slayer_handle_Curse_Soul_Wraith_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,25);
        int runeSelection = Misc.random(0,2);
        int curse_rune_amount = Misc.random(2,4);
        int soul_rune_amount = Misc.random(2,4);
        int wraith_rune_amount = Misc.random(2,3);
        int instance_ticket_chance = Misc.random(0,10);
        int instance_ticket_amount = Misc.random(0,50);
    /*    if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/

        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setCurseRunes(p.getCurseRunes() + curse_rune_amount);
                else p.getInventory().add(CURSE_RUNE, curse_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setSoulRunes(p.getSoulRunes() + soul_rune_amount);
                else p.getInventory().add(SOUL_RUNE, soul_rune_amount);
            }
            if (runeSelection == 2) {
                if (pouch_in_inventory) p.setWraithRunes(p.getWraithRunes() + wraith_rune_amount);
                else p.getInventory().add(WRAITH_RUNE, wraith_rune_amount);
            }
        }
    }

    /** VOID + WRAITH + SHADOW **/
    public static void handle_Void_Wraith_Shadow_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,200);
        int runeSelection = Misc.random(0,2);
        int void_rune_amount = Misc.random(1,2);
        int wraith_rune_amount = Misc.random(1,4);
        int shadow_rune_amount = Misc.random(1,4);
        int instance_ticket_chance = Misc.random(0,20);
        int instance_ticket_amount = Misc.random(0,30);
       /* if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);

        }*/

        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setVoidRunes(p.getVoidRunes() + void_rune_amount);
                else p.getInventory().add(VOID_RUNE, void_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setWraithRunes(p.getWraithRunes() + wraith_rune_amount);
                else p.getInventory().add(WRAITH_RUNE, wraith_rune_amount);
            }
            if (runeSelection == 2) {
                if (pouch_in_inventory) p.setShadowRunes(p.getShadowRunes() + shadow_rune_amount);
                else p.getInventory().add(SHADOW_RUNE, shadow_rune_amount);
            }
        }
    }
    public static void slayer_handle_Void_Wraith_Shadow_Drop(Player p) {
        boolean pouch_in_inventory = p.getInventory().contains(19622);

        int chance = Misc.random(0,50);
        int runeSelection = Misc.random(0,2);
        int void_rune_amount = Misc.random(1,2);
        int wraith_rune_amount = Misc.random(1,4);
        int shadow_rune_amount = Misc.random(1,4);
        int instance_ticket_chance = Misc.random(0,15);
        int instance_ticket_amount = Misc.random(0,75);
      /*  if (instance_ticket_chance == 0){
            p.getInventory().add(INSTANCE_TICKETS,instance_ticket_amount);
        }*/

        if (chance == 0){
            if (runeSelection == 0) {
                if (pouch_in_inventory) p.setVoidRunes(p.getVoidRunes() + void_rune_amount);
                else p.getInventory().add(VOID_RUNE, void_rune_amount);
            }
            if (runeSelection == 1) {
                if (pouch_in_inventory) p.setWraithRunes(p.getWraithRunes() + wraith_rune_amount);
                else p.getInventory().add(WRAITH_RUNE, wraith_rune_amount);
            }
            if (runeSelection == 2) {
                if (pouch_in_inventory) p.setShadowRunes(p.getShadowRunes() + shadow_rune_amount);
                else p.getInventory().add(SHADOW_RUNE, shadow_rune_amount);
            }
        }
    }
}
