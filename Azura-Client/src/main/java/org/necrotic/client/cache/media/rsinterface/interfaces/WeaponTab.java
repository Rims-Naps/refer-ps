package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.Configuration;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.Sprite;

public class WeaponTab extends RSInterface {
    public static final int STARTING_POINT = 87000;
    public static void unpack() {
        melee();
        range();
        magic();
    }

    public static void melee() {
        int localId = STARTING_POINT;
        RSInterface main = addTabInterface(localId++);
        main.totalChildren(3);
        addText(localId, "Weapon name here", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(0, localId++, 93, 15);
        addText(localId, "Level Here", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
        main.child(1, localId++, 93, 30);
        RSInterface buttons = addTabInterface(localId++);
        buttons.totalChildren(4);
        add_combat_button(localId, 82, 83, "Train Attack", true);
        buttons.child(0, localId++, 24, 0);
        add_combat_button(localId, 82, 83, "Train Strength", false);
        buttons.child(1, localId++, 112, 0);
        add_combat_button(localId, 82, 83, "Train Defence", false);
        buttons.child(2, localId++, 24, 50);
        add_combat_button(localId, 82, 83, "Train Split", false);
        buttons.child(3, localId++, 112, 50);
        main.child(2, buttons.id, 0, 50);
    }

    public static void range() {
        int localId = STARTING_POINT + 50;

    }

    public static void magic() {
        int localId = STARTING_POINT + 100;

    }

    private static void specialBar(int id) {

    }

    public static void inverse_melee_comp(int buttonId) {
        if (RSInterface.interfaceCache[buttonId].isNavActive) {
            return;
        }
        for (int i = 571; i < 576; i++) {
            if (RSInterface.interfaceCache[i].isNavActive) {
                Sprite newOn = RSInterface.interfaceCache[i].buttonOff;
                Sprite newOff = RSInterface.interfaceCache[i].buttonOn;
                RSInterface.interfaceCache[i].buttonOff = newOff;
                RSInterface.interfaceCache[i].buttonOn = newOn;
                RSInterface.interfaceCache[i].isNavActive = false;
            }
            if (!RSInterface.interfaceCache[i].isNavActive && buttonId == i) {
                Sprite newOn = RSInterface.interfaceCache[i].buttonOff;
                Sprite newOff = RSInterface.interfaceCache[i].buttonOn;
                RSInterface.interfaceCache[i].buttonOff = newOff;
                RSInterface.interfaceCache[i].buttonOn = newOn;
                RSInterface.interfaceCache[i].isNavActive = true;
                Configuration.activeBankButton = i;
            }
        }
    }
}
