package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.cache.media.rsinterface.dropdowns.Dropdown;
import org.necrotic.client.cache.media.rsinterface.dropdowns.impl.Keybinding;

public class KeybindInterface extends RSInterface {


    public static void keybinding() {
        RSInterface tab = addTabInterface(86700);

        addSpriteLoader(86701, 5745);
        addText(86702, "Keybinding", fonts, 2, 0xff8a1f, false, true);
        closeButton(86703, 5747, 5746);

        add_hovered_button_string(Keybinding.RESTORE_DEFAULT, 5751, 5750, "Restore Defaults", true);

        addText(86705, "Esc closes current interface", fonts, 1, 0xff8a1f, false, true);
        configButton(Keybinding.ESCAPE_CONFIG, "Select", 5749, 5748);

        tab.totalChildren(48);
        int childNum = 0;

        setBounds(86701, 5, 17, childNum++, tab);
        setBounds(86702, 221, 27, childNum++, tab);
        setBounds(86703, 479, 24, childNum++, tab);
        setBounds(Keybinding.RESTORE_DEFAULT, 343, 275, childNum++, tab);
        setBounds(86705, 60, 285, childNum++, tab);
        setBounds(Keybinding.ESCAPE_CONFIG, 35, 285, childNum++, tab);

        /* Tabs and dropdowns */

        int x = 31;
        int y = 63;
        childNum = 47;

        for (int i = 0; i < 14; i++, y += 43) {

            addSpriteLoader(86707 + 3 * i, getSideIconForOldFrame(i));
            configButton(86708 + 3 * i, "", 12, 6);

            boolean inverted = i == 3 || i == 4 || i == 8 || i == 9 || i == 13;
            keybindingDropdown(86709 + 3 * i, 86, 0, Keybinding.OPTIONS, Dropdown.KEYBIND_SELECTION, inverted);

            setBounds(Keybinding.MIN_FRAME - 2 + 3 * i, x + stoneOffset(getSideIconForOldFrame(i), true), y + stoneOffset(getSideIconForOldFrame(i), false),
                childNum--, tab);
            setBounds(Keybinding.MIN_FRAME - 1 + 3 * i, x, y, childNum--, tab);
            setBounds(Keybinding.MIN_FRAME + 3 * i, x + 39, y + 4, childNum--, tab);

            if (i == 4 || i == 9) {
                x += 160;
                y = 20;
            }
        }
    }

    private static int getSideIconForOldFrame(int tab) {
        int spriteIndex = tab;
        if (tab >= 3) {
            spriteIndex++;
        }
        spriteIndex += 528;
        switch (tab) {
            case 7:
                spriteIndex = 781;
                break;
            case 8:
                spriteIndex = 536;
                break;
            case 9:
                spriteIndex = 542;
                break;
            case 10:
                spriteIndex = 541;
                break;
            case 11:
                spriteIndex = 538;
                break;
            case 12:
                spriteIndex = 539;
                break;
            case 13:
                spriteIndex = 1197;
                break;
        }
        if (tab == 2 && spriteIndex == 530) {
            if (Client.getClient().doingDungeoneering) {
                spriteIndex = 1031;
            }
        }
        return spriteIndex;
    }
}
