package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.cache.media.RSInterface;

public class Invocations extends RSInterface {
    private static final int STARTING_POINT = 88000;

    public static void unpack() {
        tower_invocation();
    }

    public static void tower_invocation() {
        int localId = STARTING_POINT;
        RSInterface main = addInterface(localId++);
        int childId = 0;
        main.totalChildren(6);
        addSprite(localId, 5763);
        main.child(childId++, localId++, 13, 8);
        addText(localId, "Tower Invocations", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(childId++, localId++, 256, 17);
        addCloseButton(localId);
        main.child(childId++, localId++, 475, 17);
        RSInterface scroller = addInterface(localId++);
        String[] invocation_name = {"Double Double", "Double Trouble", "Elemental Overdrive", "Demonic Aggression", "Warlock's Pact", "Mark of Valor"};
        String[] invocation_desc = {"Doubles the amount of golems spawned per wave", "Doubles the amount of Elementals spawned per Elemental Wave",
            "Elementals spawn every round.", "Elementals are now aggressive when spawned", "Chance to get a Tower Superior instead of an Elemental.",
            "Doubles the damage done by all foes encountered in Tower"};
        scroller.height =  238;
        scroller.width = 463 - 16;
        scroller.scrollMax = 306;
        scroller.totalChildren(invocation_name.length);
        int childId2 = 0;
        int counter = 0;
        int x = 2;
        int y = 2;
        for (int i = 0; i < invocation_name.length; i++) {
            if (counter == 2) {
                counter = 0;
                x = 2;
                y += 102;
            }
            add_invocation(localId, invocation_name[i], invocation_desc[i], true);
            scroller.child(childId2++, localId++, x, y);
            counter++;
            x += 224;
        }

        main.child(childId++, scroller.parentID, 25, 66);
        add_hovered_button_string(localId, 5764, 5764, "Reset Invocations", false);
        main.child(childId++, localId++, 35, 50);
        addText(localId, "0/3", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
        main.child(childId++, localId++, 465, 50);
    }
}
