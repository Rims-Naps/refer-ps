package com.ruse.model.definitions;

import com.ruse.util.Misc;
import com.ruse.world.content.Necromancy.RuneNpcData;
import com.ruse.world.entity.impl.player.Player;

public class NewDropSystem {

    private static int COINS = 995;
    private static int MONSTER_ESS = 19062;
    private static int DREAM_TICKET = 2706;

    public static void handleNpcKill(Player p, int killednpc) {
        switch (killednpc) {
            //beginner
            case RuneNpcData.Nythor:
            case RuneNpcData.Terran:
            case RuneNpcData.Aqualorn:
            case RuneNpcData.Ferna:
            case RuneNpcData.Ignox:
            case RuneNpcData.Crystalis:
            case RuneNpcData.Ember:
            case RuneNpcData.Xerces:
            case RuneNpcData.Marina:
                handleCoinDropBeginner(p);
                handleEssDropBeginner(p);
                handleDreamDropBeginner(p);
                break;

//intermediate
            case RuneNpcData.Kezel:
            case RuneNpcData.Hydrora:
            case RuneNpcData.Infernus:
            case RuneNpcData.Tellurion:
            case RuneNpcData.Marinus:
            case RuneNpcData.Pyrox:
            case RuneNpcData.Astaran:
            case RuneNpcData.Nereus:
            case RuneNpcData.Volcar:
                handleCoinDropInter(p);
                handleEssDropInter(p);
                handleDreamDropInter(p);
                break;

            //elite

            case RuneNpcData.Lagoon:
            case RuneNpcData.Incendia:
            case RuneNpcData.Terra:
            case RuneNpcData.Abyss:
            case RuneNpcData.Pyra:
            case RuneNpcData.Geode:
            case RuneNpcData.Cerulean:
            case RuneNpcData.Scorch:
            case RuneNpcData.Geowind:
                handleCoinDropElite(p);
                handleEssDropElite(p);
                handleDreamDropElite(p);
                break;

            //Master
            case RuneNpcData.Goliath:
            case RuneNpcData.Volcanus:
            case RuneNpcData.Nautilus:
            case RuneNpcData.Quake:
            case RuneNpcData.Scaldor:
            case RuneNpcData.Seabane:
            case RuneNpcData.Rumble:
            case RuneNpcData.Moltron:
            case RuneNpcData.Hydrox:
                handleCoinDropMaster(p);
                handleEssDropMaster(p);
                handleDreamDropMaster(p);
                break;
            //challenger

        }
    }
    public static void handleCoinDropBeginner(Player p) {
        int chance = Misc.random(0,200);
        int amount = Misc.random(1,120);
        if (chance >= 150) {
            p.getInventory().add(COINS, amount);
            p.sendMessage("You received a bonus coin drop!");
        }
    }
    public static void handleCoinDropInter(Player p) {
        int chance = Misc.random(0,200);
        int amount = Misc.random(1,200);
        if (chance >= 150) {
            p.getInventory().add(COINS, amount);
            p.sendMessage("You received a bonus coin drop!");
        }
    }
    public static void handleCoinDropElite(Player p) {
        int chance = Misc.random(0,200);
        int amount = Misc.random(1,240);
        if (chance >= 150) {
            p.getInventory().add(COINS, amount);
            p.sendMessage("You received a bonus coin drop!");
        }
    }
    public static void handleCoinDropMaster(Player p) {
        int chance = Misc.random(0,100);
        int amount = Misc.random(1,320);
        if (chance >= 85) {
            p.getInventory().add(COINS, amount);
            p.sendMessage("You received a bonus coin drop!");
        }
    }
    public static void handleEssDropBeginner(Player p) {
        int chance = Misc.random(0,300);
        int amount = Misc.random(1,4);
        if (chance >= 280) {
            p.getInventory().add(MONSTER_ESS, amount);
            p.sendMessage("You received a bonus essence drop!");
        }
    }
    public static void handleEssDropInter(Player p) {
        int chance = Misc.random(0,200);
        int amount = Misc.random(1,7);
        if (chance >= 185) {
            p.getInventory().add(MONSTER_ESS, amount);
            p.sendMessage("You received a bonus essence drop!");
        }
    }
    public static void handleEssDropElite(Player p) {
        int chance = Misc.random(0,200);
        int amount = Misc.random(1,15);
        if (chance >= 180) {
            p.getInventory().add(MONSTER_ESS, amount);
            p.sendMessage("You received a bonus essence drop!");
        }
    }
    public static void handleEssDropMaster(Player p) {
        int chance = Misc.random(0,300);
        int amount = Misc.random(1,25);
        if (chance >= 280) {
            p.getInventory().add(MONSTER_ESS, amount);
            p.sendMessage("You received a bonus essence drop!");
        }
    }
    public static void handleDreamDropBeginner(Player p) {
        int chance = Misc.random(0,400);
        int amount = 1;
        if (chance >= 399) {
            p.getInventory().add(DREAM_TICKET,amount);
            p.sendMessage("You received a bonus dream ticket drop!");
        }
    }
    public static void handleDreamDropInter(Player p) {
        int chance = Misc.random(0,400);
        int amount = 1;
        if (chance >= 399) {
            p.getInventory().add(DREAM_TICKET,amount);
            p.sendMessage("You received a bonus dream ticket drop!");
        }
    }
    public static void handleDreamDropElite(Player p) {
        int chance = Misc.random(0,400);
        int amount = 1;
        if (chance >= 399) {
            p.getInventory().add(DREAM_TICKET,amount);
            p.sendMessage("You received a bonus dream ticket drop!");
        }
    }
    public static void handleDreamDropMaster(Player p) {
        int chance = Misc.random(0,400);
        int amount = 1;
        if (chance >= 399) {
            p.getInventory().add(DREAM_TICKET, amount);
            p.sendMessage("You received a bonus dream ticket drop!");
        }
    }
}
