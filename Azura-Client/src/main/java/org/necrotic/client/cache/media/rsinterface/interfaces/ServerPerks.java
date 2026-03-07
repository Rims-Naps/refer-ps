package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.cache.media.RSInterface;

public class ServerPerks extends RSInterface {
    private static final int STARTING_POINT = 19900;
    private static final int WIDTH = 183;
    private static final int HEIGHT = 255;

    private static final boolean IS_HOLIDAY = false;


    public static void unpack() {
        if (IS_HOLIDAY) {
            holiday_server_perk_main();
            holiday_voting();
        } else {
            server_perk_main();
            voting();
        }
    }

    public static void server_perk_main() {
        int localIds = STARTING_POINT;
        int localIds2 = STARTING_POINT + 100;
        RSInterface main = addInterface(localIds++);
        main.totalChildren(3);
        int childId = 0;
        int childId2 = 0;
        int childId3 = 0;
        Object[][] information = {
            {"Critical\\nIncrease critical\\nhit chance by 10%", 5731},
            {"Necro\\nEarn double\\nnecromancy points", 5732},
            {"Droprate\\nGain a 5% bonus to\\ndrop rates", 5733},
            {"Experience\\nEarn 25% more\\nexperience", 5734},
            {"Slayer\\nEarn 20% more\\nslayer tickets", 5735},
            {"Damage\\nDeal 20% more\\ndamage", 5736},
            {"Skilling\\nGain a 50% bonus\\nto skilling", 5737},
            {"Vitality\\n2x Vote rewards\\nBonds for donating", 5738},
            {"Frenzy\\n1 in 25 chance to\\ndouble kills", 5739},
            {"Raids\\n2x Raids keys", 5740}
        };
        RSInterface graphicsComp = addInterface(localIds++);
        graphicsComp.totalChildren(6);
        RSInterface scroller = addInterface(localIds2++);
        scroller.width = 160;
        scroller.height = 122;
        scroller.scrollMax = information.length * 36 + 3;
        int textOff = 3;
        int spriteOff = 3;
        scroller.totalChildren(information.length * 2);
        for (int i = 0; i < information.length; i++) {
            addText(localIds2, (String) information[i][0], ColorConstants.RS_ORANGE, false, true, 52, 0);
            scroller.child(childId3++, localIds2++, 51, 0 + textOff - 3);
            textOff += 36;
            addSpriteLoader(localIds2, (Integer) information[i][1]);
            scroller.child(childId3++, localIds2++, 16, spriteOff - 3);
            spriteOff += 36;
        }
        addSpriteLoader(localIds, 5752);
        graphicsComp.child(childId2++, localIds++, 12, 7 - 3);
        addText(localIds, "Current Server Perk", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 15 - 3);
        addText(localIds, "Perk Data\\nTesting Lines for Perk Data\\nThird Line BB", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 66 - 3);
        addSpriteLoader(localIds, 5731); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 79, 30 - 3);
        addSpriteLoader(localIds, -1);
        graphicsComp.child(childId2++, localIds++, 13, 95 - 3);
        addText(localIds, "TimeLife ASDF", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 102 - 3);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds++, 4, 3);
        main.child(childId++, graphicsComp.id, 0, 10 - 3);
        main.child(childId++, scroller.id, 0, 132 - 6);
/*        add_progress_bar(localIds, 50, 100, 5742, 5743, 166, 20);
        main.child(childId++, localIds++, 12, 231);
        addText(localIds, "Christmas Spirit", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(childId, localIds, 95, 212);*/
    }

    public static void voting() {
        int localIds = STARTING_POINT + 200;
        int localIds2 = STARTING_POINT + 300;
        RSInterface main = addInterface(localIds++);
        main.totalChildren(3);
        int childId = 0;
        int childId2 = 0;
        int childId3 = 0;
        Object[][] information = {
            {"Critical\\nIncrease critical\\nhit chance by 10%", 5731},
            {"Necro\\nEarn double\\nnecromancy points", 5732},
            {"Droprate\\nGain a 5% bonus to\\ndrop rates", 5733},
            {"Experience\\nEarn 25% more\\nexperience", 5734},
            {"Slayer\\nEarn 20% more\\nslayer tickets", 5735},
            {"Damage\\nDeal 20% more\\ndamage", 5736},
            {"Skilling\\nGain a 50% bonus\\nto skilling", 5737},
            {"Vitality\\n2x Vote rewards\\nBonds for donating", 5738},
            {"Frenzy\\n1 in 25 chance to\\ndouble kills", 5739},
                {"Raids\\n2x Raids keys", 5740}
        };
        RSInterface graphicsComp = addInterface(localIds++);
        graphicsComp.totalChildren(10);
        RSInterface scroller = addInterface(localIds2++);
        scroller.width = 160;
        scroller.height = 122;
        scroller.scrollMax = information.length * 36 + 3;
        int textOff = 3;
        int spriteOff = 3;
        scroller.totalChildren(information.length * 2);
        for (int i = 0; i < information.length; i++) {
            addText(localIds2, (String) information[i][0], ColorConstants.RS_ORANGE, false, true, 52, 0);
            scroller.child(childId3++, localIds2++, 51, 0 + textOff - 3);
            textOff += 36;
            addSpriteLoader(localIds2, (Integer) information[i][1]);
            scroller.child(childId3++, localIds2++, 16, spriteOff - 3);
            spriteOff += 36;
        }
        addSpriteLoader(localIds, 5752);
        graphicsComp.child(childId2++, localIds++, 12, 7 - 3);
        addText(localIds, "Current Server Perk", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 15 - 3);
        add_button(localIds, 5731, "Vote for this perk"); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 79, 30 - 3);
        add_button(localIds, 5731, "Vote for this perk"); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 43, 30 - 3);
        add_button(localIds, 5731, "Vote for this perk"); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 115, 30 - 3);
        addText(localIds, "0", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 97, 66 - 3);
        addText(localIds, "0", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 61, 66 - 3);
        addText(localIds, "0", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 133, 66 - 3);
        addSpriteLoader(localIds, -1);
        graphicsComp.child(childId2++, localIds++, 13, 95 - 3);
        addText(localIds, "TimeLife ASDF", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 102 - 3);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds++, 4, 3);
        main.child(childId++, graphicsComp.id, 0, 10 - 3);
        main.child(childId++, scroller.id, 0, 132 - 6);
/*        add_progress_bar(localIds, 50, 100, 5742, 5743, 166, 20);
        main.child(childId++, localIds++, 12, 231);
        addText(localIds, "Christmas Spirit", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(childId, localIds, 95, 212);*/
    }

    public static void holiday_server_perk_main() {
        int localIds = STARTING_POINT;
        int localIds2 = STARTING_POINT + 100;
        RSInterface main = addInterface(localIds++);
        main.totalChildren(5);
        int childId = 0;
        int childId2 = 0;
        int childId3 = 0;
        Object[][] information = {
            {"Critical\\nIncrease critical\\nhit chance by 10%", 5731},
            {"Necro\\nEarn double\\nnecromancy points", 5732},
            {"Droprate\\nGain a 5% bonus to\\ndrop rates", 5733},
            {"Experience\\nEarn 25% more\\nexperience", 5734},
            {"Slayer\\nEarn 20% more\\nslayer tickets", 5735},
            {"Damage\\nDeal 20% more\\ndamage", 5736},
            {"Skilling\\nGain a 50% bonus\\nto skilling", 5737},
            {"Vitality\\n2x Vote rewards\\nBonds for donating", 5738},
            {"Frenzy\\n1 in 25 chance to\\ndouble kills", 5739},
                {"Raids\\n2x Raids keys", 5740}
        };
        RSInterface graphicsComp = addInterface(localIds++);
        graphicsComp.totalChildren(6);
        RSInterface scroller = addInterface(localIds2++);
        scroller.width = 160;
        scroller.height = 83;
        scroller.scrollMax = information.length * 36 + 3;
        int textOff = 3;
        int spriteOff = 3;
        scroller.totalChildren(information.length * 2);
        for (int i = 0; i < information.length; i++) {
            addText(localIds2, (String) information[i][0], ColorConstants.RS_ORANGE, false, true, 52, 0);
            scroller.child(childId3++, localIds2++, 51, 0 + textOff - 3);
            textOff += 36;
            addSpriteLoader(localIds2, (Integer) information[i][1]);
            scroller.child(childId3++, localIds2++, 16, spriteOff - 3);
            spriteOff += 36;
        }
        addSpriteLoader(localIds, 5730);
        graphicsComp.child(childId2++, localIds++, 12, 7 - 3);
        addText(localIds, "Current Server Perk", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 15 - 3);
        addText(localIds, "Perk Data\\nTesting Lines for Perk Data\\nThird Line BB", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 66 - 3);
        addSpriteLoader(localIds, 5731); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 79, 30 - 3);
        addSpriteLoader(localIds, -1);
        graphicsComp.child(childId2++, localIds++, 13, 95 - 3);
        addText(localIds, "TimeLife ASDF", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 102 - 3);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds++, 4, 3);
        main.child(childId++, graphicsComp.id, 0, 10 - 3);
        main.child(childId++, scroller.id, 0, 132 - 6);
        add_progress_bar(localIds, 50, 100, 5742, 5743, 166, 20);
        main.child(childId++, localIds++, 12, 231);
        addText(localIds, "Spring", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(childId, localIds, 95, 212);
    }

    public static void holiday_voting() {
        int localIds = STARTING_POINT + 200;
        int localIds2 = STARTING_POINT + 300;
        RSInterface main = addInterface(localIds++);
        main.totalChildren(5);
        int childId = 0;
        int childId2 = 0;
        int childId3 = 0;
        Object[][] information = {
            {"Critical\\nIncrease critical\\nhit chance by 10%", 5731},
            {"Necro\\nEarn double\\nnecromancy points", 5732},
            {"Droprate\\nGain a 5% bonus to\\ndrop rates", 5733},
            {"Experience\\nEarn 25% more\\nexperience", 5734},
            {"Slayer\\nEarn 20% more\\nslayer tickets", 5735},
            {"Damage\\nDeal 20% more\\ndamage", 5736},
            {"Skilling\\nGain a 50% bonus\\nto skilling", 5737},
            {"Vitality\\n2x Vote rewards\\nBonds for donating", 5738},
            {"Frenzy\\n1 in 25 chance to\\ndouble kills", 5739},
            {"Raids\\n2x Raids keys", 5740}
        };
        RSInterface graphicsComp = addInterface(localIds++);
        graphicsComp.totalChildren(10);
        RSInterface scroller = addInterface(localIds2++);
        scroller.width = 160;
        scroller.height = 83;
        scroller.scrollMax = information.length * 36 + 3;
        int textOff = 3;
        int spriteOff = 3;
        scroller.totalChildren(information.length * 2);
        for (int i = 0; i < information.length; i++) {
            addText(localIds2, (String) information[i][0], ColorConstants.RS_ORANGE, false, true, 52, 0);
            scroller.child(childId3++, localIds2++, 51, 0 + textOff - 3);
            textOff += 36;
            addSpriteLoader(localIds2, (Integer) information[i][1]);
            scroller.child(childId3++, localIds2++, 16, spriteOff - 3);
            spriteOff += 36;
        }
        addSpriteLoader(localIds, 5730);
        graphicsComp.child(childId2++, localIds++, 12, 7 - 3);
        addText(localIds, "Current Server Perk", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 15 - 3);
        add_button(localIds, 5731, "Vote for this perk"); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 79, 30 - 3);
        add_button(localIds, 5731, "Vote for this perk"); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 43, 30 - 3);
        add_button(localIds, 5731, "Vote for this perk"); //Invisible Sprite to allow server to push the sprite
        graphicsComp.child(childId2++, localIds++, 115, 30 - 3);
        addText(localIds, "0", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 97, 66 - 3);
        addText(localIds, "0", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 61, 66 - 3);
        addText(localIds, "0", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 133, 66 - 3);
        addSpriteLoader(localIds, -1);
        graphicsComp.child(childId2++, localIds++, 13, 95 - 3);
        addText(localIds, "TimeLife ASDF", ColorConstants.RS_ORANGE, true, true, 52, 0);
        graphicsComp.child(childId2++, localIds++, 93, 102 - 3);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds++, 4, 3);
        main.child(childId++, graphicsComp.id, 0, 10 - 3);
        main.child(childId++, scroller.id, 0, 132 - 6);
        add_progress_bar(localIds, 50, 100, 5742, 5743, 166, 20);
        main.child(childId++, localIds++, 12, 231);
        addText(localIds, "Spring Meter", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(childId, localIds, 95, 212);
    }
}
