package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.rsinterface.dropdowns.Dropdown;

public class SettingsInterface extends RSInterface {
    private static final int STARTING_POINT = 26253;
    private static final int WIDTH = 183;
    private static final int HEIGHT = 235;


    public static void unpack() {
        navigation();
        graphics();
        audio();
        chat();
        general();
    }

    public static void navigation() {
        RSInterface main = addInterface(STARTING_POINT);
        add_nav_bar_componenet(STARTING_POINT + 1, new int[]{3357, 3358, 3359, 3365, 3366, 3367}, new int[] {32, 21}, "Display Settings", 3364, true);
        add_nav_bar_componenet(STARTING_POINT + 2, new int[]{3365, 3366, 3367, 3357, 3358, 3359}, new int[] {32, 21}, "Toggle Settings", 3368, false);
        add_nav_bar_componenet(STARTING_POINT + 3, new int[]{3365, 3366, 3367, 3357, 3358, 3359}, new int[] {32, 21}, "Chat Settings", 3370, false);
        add_nav_bar_componenet(STARTING_POINT + 4, new int[]{3365, 3366, 3367, 3357, 3358, 3359}, new int[] {32, 21}, "General Settings", 3369, false);
        addRectangle(STARTING_POINT + 5, 150, 0, true, WIDTH - 12, HEIGHT - 12);

        main.totalChildren(4);
        main.child(0, STARTING_POINT + 1, 31, 2);
        main.child(1, STARTING_POINT + 2, 63, 2);
        main.child(2, STARTING_POINT + 3, 95, 2);
        main.child(3, STARTING_POINT + 4, 127, 2);
    }

    public static void graphics() {
        int localIds = STARTING_POINT + 100;
        RSInterface main = addTab(localIds++, 2);
        String[] options = new String[]{"Fixed", "Resizable - Classic layout", "Resizable - Modern layout"};
        String[] configTexts = new String[] {"View Distance", "Render Self", "Render Players", "Render Pets", "Texture Animation"};
        int[] minValues = {120};
        int[] maxValues = {60};
        int[] defaultValues = {80};
        int childId = 2;
        RSInterface graphicsComp = addInterface(localIds++);
        graphicsComp.totalChildren(1 + (configTexts.length  * 2));
        int childId2 = 0;
        int y_toggle = 20;
        int y_text = 22;
        int configFrame = 2277;

        for (int i = 0; i < configTexts.length; i++) {
            addText(localIds, configTexts[i], ColorConstants.VANGUARD_PURPLE, false, true, 52, 1);
            graphicsComp.child(childId2++, localIds++, 12, y_text);
            y_text += 20;
            addConfigButton(localIds, 3486, 3487, configFrame++, 18, 18, configTexts[i]);
            graphicsComp.child(childId2++, localIds++, 160, y_toggle);
            y_toggle += 20;
        }
        dropdownMenu(localIds, 160, 0, options, Dropdown.SCREEN_ELEMENTS);
        graphicsComp.child(childId2, localIds++, 15, 0);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds, 4, 23);
        main.child(childId, graphicsComp.id, 0, 30);
    }

    public static void audio() {
        int localIds = STARTING_POINT + 200;
        RSInterface main = addTab(localIds++, 2);
        String[] configTexts = new String[] {"Show Timers", "Pouch Messages", "Drop Messages", "[$]Auto Bank", "[$]Auto Salvage"};
        int childId = 2;
        RSInterface togglesComp = addInterface(localIds++);
        togglesComp.totalChildren((configTexts.length * 2) + 1);
        int childId2 = 0;
        int y_toggle = 0;
        int y_text = 2;
        int configFrame = 2800;
        for (int i = 0; i < configTexts.length; i++) {
            addText(localIds, configTexts[i], ColorConstants.VANGUARD_PURPLE, false, true, 52, 1);
            togglesComp.child(childId2++, localIds++, 12, y_text);
            y_text += 20;
            addConfigButton(localIds, 3486, 3487, configFrame++, 18, 18, configTexts[i]);
            togglesComp.child(childId2++, localIds++, 160, y_toggle);
            y_toggle += 20;
        }
        add_hovered_button_string(localIds, 5751, 5750, "Change Keybinds", true);
        togglesComp.child(childId2++, localIds++, 22, y_toggle);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds, 4, 23);
        main.child(childId, togglesComp.id, 0, 30);
    }

    public static void chat() {
        int localIds = STARTING_POINT + 300;
        RSInterface main = addTab(localIds++, 2);
        int childId = 2;
        RSInterface chatComp = addInterface(localIds++);
        String[] configTexts = new String[] {"Split Chat", "Chat Timestamps", "Username Highlighting", "Emojis"};
        chatComp.totalChildren((configTexts.length * 2) + 6);
        int childId2 = 0;
        int y_toggle = 0;
        int y_text = 2;
        int configFrame = 2990;
        for (int i = 0; i < configTexts.length; i++) {
            addText(localIds, configTexts[i], ColorConstants.VANGUARD_PURPLE, false, true, 52, 1);
            chatComp.child(childId2++, localIds++, 12, y_text);
            y_text += 20;
            addConfigButton(localIds, 3486, 3487, configFrame++, 18, 18, configTexts[i]);
            chatComp.child(childId2++, localIds++, 160, y_toggle);
            y_toggle += 20;
        }
        addColorBox(localIds, 0, 42, 42, COLOR_TYPES.SPLITCHAT);
        chatComp.child(childId2++, localIds++, 23, 153);
        addButtonWSpriteLoader(localIds, 5700, "Change Split Chat Color");
        chatComp.child(childId2++, localIds++, 20, 150);
        addColorBox(localIds, 0, 42, 42, COLOR_TYPES.CLANCHAT);
        chatComp.child(childId2++, localIds++, 123, 153);
        addButtonWSpriteLoader(localIds, 5700, "Change Clan Chat Color");
        chatComp.child(childId2++, localIds++, 120, 150);
        addText(localIds, "Split Chat", ColorConstants.VANGUARD_PURPLE, true, true, 52, 1);
        chatComp.child(childId2++, localIds++, 45, 137);
        addText(localIds, "Clan Chat", ColorConstants.VANGUARD_PURPLE, true, true, 52, 1);
        chatComp.child(childId2++, localIds++, 145, 137);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds, 4, 23);
        main.child(childId, chatComp.id, 0, 30);
    }

    public static void general() {
        int localIds = STARTING_POINT + 400;
        RSInterface main = addTab(localIds++, 2);
        String[] configTexts = new String[] {"Usernames Above Head", "Item Outlines", "Health Bar Indicators"};
        int childId = 2;
        RSInterface generalComp = addInterface(localIds++);
        int childId2 = 0;
        int y_toggle = 0;
        int y_text = 2;
        int configFrame = 3000;
        String[] options = {"Depends on combat levels", "Always right-click", "Left-click where available", "Hidden"};
        generalComp.totalChildren((configTexts.length * 2) + 5);
        for (int i = 0; i < configTexts.length; i++) {
            addText(localIds, configTexts[i], ColorConstants.VANGUARD_PURPLE, false, true, 52, 1);
            generalComp.child(childId2++, localIds++, 12, y_text);
            y_text += 20;
            addConfigButton(localIds, 3486, 3487, configFrame++, 18, 18, configTexts[i]);
            generalComp.child(childId2++, localIds++, 160, y_toggle);
            y_toggle += 20;
        }
        addColorBox(localIds, 0, 42, 42, COLOR_TYPES.HITPOINTS);
        generalComp.child(childId2++, localIds++, 73, 153);
        addButtonWSpriteLoader(localIds, 5700, "Change HitPoints Color");
        generalComp.child(childId2++, localIds++, 70, 150);
        addText(localIds, "Health Bar Color", ColorConstants.VANGUARD_PURPLE, true, true, 52, 1);
        generalComp.child(childId2++, localIds++, 95, 137);
        addText(localIds, "NPC Attack Options", ColorConstants.VANGUARD_PURPLE, false, true, 52, 1);
        generalComp.child(childId2++, localIds++, 12, y_text);
        y_text += 20;
        dropdownMenu(localIds, 166, 2, options, Dropdown.NPC_ATTACK_OPTION_PRIORITY);
        generalComp.child(childId2, localIds++, 12, y_text);
        addWindow(localIds, WIDTH - 1, HEIGHT, 2, true, false);
        main.child(childId++, localIds, 4, 23);
        main.child(childId, generalComp.id, 0, 30);
    }

    private static RSInterface addTab(int id, int children) {
        RSInterface tab = addTabInterface(id);
        tab.totalChildren(2 + children);
        tab.child(0, STARTING_POINT + 5, 10, 29);
        tab.child(1, STARTING_POINT, 0, 0);
        return tab;
    }

    public static void setTab(int buttonId) {
        int tab = buttonId - 26254;
        int[] tabs = new int[]{STARTING_POINT + 100, STARTING_POINT + 200, STARTING_POINT + 300, STARTING_POINT + 400};
        inverseSprites(buttonId);
        Client.instance.tabInterfaceIDs[RSInterface.SETTINGS_TAB] = tabs[tab];
    }

    public static void inverseSprites(int buttonId) {
        if (RSInterface.interfaceCache[buttonId].isNavActive) {
            return;
        }
        for (int i = 26254; i < 26258; i++) {
            int[] spritesData = RSInterface.interfaceCache[i].spriteMap;
            if (RSInterface.interfaceCache[i].isNavActive) {
                int[] modified = new int[]{spritesData[3], spritesData[4], spritesData[5], spritesData[0],spritesData[1], spritesData[2]};
                RSInterface.interfaceCache[i].spriteMap = modified;
                RSInterface.interfaceCache[i].isNavActive = false;
            }
            if (!RSInterface.interfaceCache[i].isNavActive && buttonId == i) {
                int[] modified = new int[]{spritesData[3], spritesData[4], spritesData[5], spritesData[0],spritesData[1], spritesData[2]};
                RSInterface.interfaceCache[i].spriteMap = modified;
                RSInterface.interfaceCache[i].isNavActive = true;
            }
        }
    }

}
