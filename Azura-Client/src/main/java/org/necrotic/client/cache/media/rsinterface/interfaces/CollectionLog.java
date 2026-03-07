package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.ProgressBar;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.TextDrawingArea;

public class CollectionLog extends RSInterface {

    public static void draw(TextDrawingArea[] tda) {
        int interID = 131000;
        RSInterface tab = addInterface(interID);
        int id = interID + 1;
        int c = 0;
        int x = 35;
        int y = 25;
        tab.totalChildren(23);

        addSpriteLoader(id, 3420);
        tab.child(c++, id++, 0 + x, 0 + y);

        addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
        tab.child(c++, id++, 405 + x, 1 + y);
        addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
        tab.child(c++, id++, 405 + x, 1 + y);
        id++;

        addText(id, "Collection Log", tda, 2, 0xAF70C3, true, true);
        tab.child(c++, id++, 216 + x, 2 + y);

        addText(id, "Total: (0/0)", tda, 0, 0xAF70C3, false, true);
        tab.child(c++, id++, 10 + x, 6 + y);

        addText(id, "Name", tda, 2, ColorConstants.YELLOW, false, true);
        tab.child(c++, id++, 183 + x, 60 + y);

        new ProgressBar(id, 120, 15, new int[]{0xB472C4}, true, false, "", new int[]{0x6D4677});
        tab.child(c++, id++, 181 + x, 79 + y);

        addTextRight(id, "Kills: 0", tda, 0, 0xAF70C3, true);
        tab.child(c++, id++, 452 + x, 82 + y);

        String[] tabs = new String[]{"Monsters", "Zones", "Bosses", "Minigames", "Boxes"};

        for (int i = 0; i < 5; i++) {
            addConfigButtonWSpriteLoader(id, interID, 1506, 1507, 79, 20, tabs[i], i, 5, 2388);
            tab.child(c++, id++, 13 + x, 28 + y);
            addText(id, tabs[i], 0xAF70C3, true, true, 100, tda, 2);
            tab.child(c++, id++, 53 + x, 31 + y);
            x += 81;
        }
        x = 35;

        addText(id, "Obtained: ", tda, 0, ColorConstants.YELLOW, true, true);
        tab.child(c++, id++, 240 + x, 81 + y);

        addText(id, "Reward(s):", tda, 2, ColorConstants.YELLOW, false, true);
        tab.child(c++, id++, 183 + x, 245 + y);

        itemGroup(id, 5, 1, 1, 1);
        tab.child(c++, id++, 251 + x, 238 + y);

        tab.child(c++, 131050, 11 + x, 58 + y);
        tab.child(c++, 131250, 179 + x, 97 + y);

        interID = 131050;
        RSInterface list = addTabInterface(interID);
        list.totalChildren(200);
        list.height = 213;
        list.width = 160 - 16;
        list.scrollMax = 259;
        id = interID + 1;
        x = 0;
        y = 0;
        c = 0;
        for (int i = 0; i < 100; i++) {
            addConfigButtonWSpriteLoader(id, interID, (i % 2 == 0) ? 3421 : 3422, 5599, 188, 17, "Select", i, 5, 2451);
            list.child(c++, id++, 0 + x, 0 + y);
            addText(id, "", 0xAF70C3, false, true, 100, tda, 1);
            list.child(c++, id++, 1 + x, 1 + y);
            y += 17;
        }

        interID = 131250;
        RSInterface list2 = addTabInterface(interID);
        list2.totalChildren(180);
        list2.height = 138;
        list2.width = 240 - 16;
        list2.scrollMax = 290;
        id = interID + 1;
        x = 0;
        y = 0;
        c = 0;

        for (int z = 0; z < 30; z++) {
            for (int r = 0; r < 6; r++) {
                itemGroup(id, 1, 1, 1, 1);
                list2.child(c++, id++, 2 + x, 3 + y);
                x += 38;
            }
            x = 0;
            y += 36;
        }

    }

}
