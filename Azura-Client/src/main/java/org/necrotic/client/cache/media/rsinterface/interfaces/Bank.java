package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.Configuration;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.cache.media.TextDrawingArea;

public class Bank extends RSInterface {

    public static TextDrawingArea[] tda;

    public static void unpack(TextDrawingArea[] text) {
        tda = text;
        display();
    }

    private static void display() {
        interfaceCache[5383].message = "     The Bank Of " + Configuration.CLIENT_NAME + "";
        interfaceCache[5383].disabledColor = ColorConstants.VANGUARD_PURPLE;
        RSInterface rsinterface = addTabInterface(5292);
        setChildren(29, rsinterface);
        interfaceCache[5385].height = 206;
        interfaceCache[5385].width = 474;
        interfaceCache[5382].width = 10;
        interfaceCache[5382].invSpritePadX = 12;
        interfaceCache[5382].height = 100;
        interfaceCache[5382].inv = new int[10 * 100];
        interfaceCache[5382].invStackSizes = new int[10 * 100];
        setBounds(5293, 13, 8, 0, rsinterface);
        setBounds(5383, 170, 10, 1, rsinterface);
        setBounds(5385, 0, 69, 2, rsinterface);
        addSpriteLoader(5293, 5711);

        add_hovered_button(5384, 714, 715, "Close Window");
        setBounds(5384, 476, 11, 3, rsinterface);

        add_hovered_button_active_string(22008, 5724, 5723, "Swap", "Swap", true);
        add_hovered_button_active_string(22009, 5723, 5724, "Insert", "Insert", false);
        setBounds(22008, 19, 294, 4, rsinterface);
        setBounds(22009, 69, 294, 5, rsinterface);

        add_hovered_button_active_string(569, 5724, 5723, "Item", "Item", true);
        add_hovered_button_active_string(570, 5723, 5724, "Note", "Note", false);
        setBounds(569, 119, 294, 6, rsinterface);
        setBounds(570, 169, 294, 7, rsinterface);

        add_hovered_button_active_string(571, 5722, 5721, "1", "1", true);
        add_hovered_button_active_string(572, 5721, 5722, "5", "5", false);
        add_hovered_button_active_string(573, 5721, 5722, "10", "10", false);
        add_hovered_button_active_string(574, 5721, 5722, "X", "X", false);
        add_hovered_button_active_string(575, 5721, 5722, "All", "All", false);
        setBounds(571, 219, 294, 8, rsinterface);
        setBounds(572, 244, 294, 9, rsinterface);
        setBounds(573, 269, 294, 10, rsinterface);
        setBounds(574, 294, 294, 11, rsinterface);
        setBounds(575, 319, 294, 12, rsinterface);

        add_hovered_button_active_icon(22004, 5719, 5720, "Search your bank", 5727, false);
        setBounds(22004, 347, 280, 13, rsinterface);

        add_hovered_button_active_string(78395, 2019, 2018, "Toggle Placeholders", "", true);
        setBounds(78395, 384, 280, 14, rsinterface);

        add_hovered_button_active_icon(22012, 5719, 5720, "Deposit carried items", 5725, false);
        setBounds(22012, 421, 280, 15, rsinterface);

        add_hovered_button_active_icon(27005, 5719, 5720, "Deposit worn items", 5726, false);
        setBounds(27005, 458, 280, 16, rsinterface);

        add_bank_nav_component(27014, 5716, 5714,"View contents of this bank tab", 5712, true);
        setBounds(27014, 82, 29, 17, rsinterface);
        add_bank_nav_component(27015, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27015, 122, 29, 18, rsinterface);
        add_bank_nav_component(27016, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27016, 162, 29, 19, rsinterface);
        add_bank_nav_component(27017, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27017, 202, 29, 20, rsinterface);
        add_bank_nav_component(27018, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27018, 242, 29, 21, rsinterface);
        add_bank_nav_component(27019, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27019, 282, 29, 22, rsinterface);
        add_bank_nav_component(27020, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27020, 322, 29, 23, rsinterface);
        add_bank_nav_component(27021, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27021, 362, 29, 24, rsinterface);
        add_bank_nav_component(27022, 5714, 5716,"Drag an item here to create a new tab", 5713, false);
        setBounds(27022, 402, 29, 25, rsinterface);

        addSprite(576, 5729);
        setBounds(576, 25, 33, 26, rsinterface);
        addText(22033, "134", tda, 0, 0xb4965a, true, false);
        setBounds(22033, 39, 36, 27, rsinterface);
        addText(22034, "496", tda, 0, 0xb4965a, true, false);
        setBounds(22034, 39, 48, 28, rsinterface);
    }

    public static void inverse_nav_comp(int buttonId) {
        if (RSInterface.interfaceCache[buttonId].isNavActive) {
            return;
        }
        for (int i = 27014; i < 27023; i++) {
            if (RSInterface.interfaceCache[i].isNavActive) {
                Sprite newOn = RSInterface.interfaceCache[i].bankOff;
                Sprite newOff = RSInterface.interfaceCache[i].bankOn;
                RSInterface.interfaceCache[i].bankOff = newOff;
                RSInterface.interfaceCache[i].bankOn = newOn;
                RSInterface.interfaceCache[i].isNavActive = false;
            }
            if (!RSInterface.interfaceCache[i].isNavActive && buttonId == i) {
                Sprite newOn = RSInterface.interfaceCache[i].bankOff;
                Sprite newOff = RSInterface.interfaceCache[i].bankOn;
                RSInterface.interfaceCache[i].bankOff = newOff;
                RSInterface.interfaceCache[i].bankOn = newOn;
                RSInterface.interfaceCache[i].isNavActive = true;
            }
        }
    }

    public static void inverse_swap_comp(int buttonId) {
        if (RSInterface.interfaceCache[buttonId].isNavActive) {
            return;
        }
        for (int i = 22008; i < 22010; i++) {
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
            }
        }
    }

    public static void inverse_note_comp(int buttonId) {
        if (RSInterface.interfaceCache[buttonId].isNavActive) {
            return;
        }
        for (int i = 569; i < 571; i++) {
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
            }
        }
    }

    public static void onToggle() {
        Sprite newOn = RSInterface.interfaceCache[78395].buttonOff;
        Sprite newOff = RSInterface.interfaceCache[78395].buttonOn;
        RSInterface.interfaceCache[78395].buttonOff = newOff;
        RSInterface.interfaceCache[78395].buttonOn = newOn;
        RSInterface.interfaceCache[78395].isNavActive = false;
    }
    public static void offToggle() {
        Sprite newOn = RSInterface.interfaceCache[78395].buttonOff;
        Sprite newOff = RSInterface.interfaceCache[78395].buttonOn;
        RSInterface.interfaceCache[78395].buttonOff = newOff;
        RSInterface.interfaceCache[78395].buttonOn = newOn;
        RSInterface.interfaceCache[78395].isNavActive = true;
    }
        public static void placeHolderToggle() {
            if (RSInterface.interfaceCache[78395].isNavActive) {
                onToggle();
            } else {
                offToggle();
            }
    }

    public static void inverse_quantity_comp(int buttonId) {
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
