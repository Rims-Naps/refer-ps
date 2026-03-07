package org.necrotic.client.cache.media.rsinterface.dropdowns.impl;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.necrotic.Configuration;
import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;

import java.awt.event.KeyEvent;

public class Keybinding {
    public static final int MIN_FRAME = 86709;
    public static final int RESTORE_DEFAULT = 86704;
    public static final int ESCAPE_CONFIG = 86706;
    public static final String[] OPTIONS = {"None", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "ESC"};

    public static final int KEYS[] = {-1, KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7, KeyEvent.VK_F8,
        KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_ESCAPE};


    public static int[] KEYBINDINGS;

    static {
        restoreDefault();
    }

    public static void restoreDefault() {
        KEYBINDINGS = new int[]{
            KeyEvent.VK_F5,
            -1,
            -1,
            KeyEvent.VK_F1,
            KeyEvent.VK_F2,
            KeyEvent.VK_F3,
            KeyEvent.VK_F4,
            KeyEvent.VK_F6,
            KeyEvent.VK_F7,
            KeyEvent.VK_F8,
            KeyEvent.VK_F9,
            KeyEvent.VK_F10,
            KeyEvent.VK_F11,
            KeyEvent.VK_F12,
        };
        Configuration.escapeCloseInterface = true;
    }

    public static void checkDuplicates(int key, int index) {
        for (int i = 0; i < KEYBINDINGS.length; i++) {
            if (KEYS[key] == KEYBINDINGS[i] && i != index && KEYBINDINGS[i] != -1) {
                KEYBINDINGS[i] = -1;
                RSInterface.interfaceCache[MIN_FRAME + 3 * i].dropdown.setSelected("None");
            }
        }
        if (KEYS[key] == KeyEvent.VK_ESCAPE && Configuration.escapeCloseInterface) {
            Configuration.escapeCloseInterface = false;
            RSInterface.interfaceCache[ESCAPE_CONFIG].active = Configuration.escapeCloseInterface;
        }
        if (index != -1 && KEYS[key] == KeyEvent.VK_ESCAPE && Configuration.escapeCloseInterface) {
            Configuration.escapeCloseInterface = !Configuration.escapeCloseInterface;
            RSInterface.interfaceCache[ESCAPE_CONFIG].active = Configuration.escapeCloseInterface;
        }
    }

    public static void bind(int index, int key) {
        checkDuplicates(key, index);
        KEYBINDINGS[index] = KEYS[key];
    }

    public static boolean isBound(int key) {
        for (int i = 0; i < KEYBINDINGS.length; i++) {
            if (key == KEYBINDINGS[i]) {
                Client.setTab(i);
                return true;
            }
        }
        return false;
    }

    public static void updateInterface() {

        for (int i = 0; i < OPTIONS.length; i++) {

            int key = KEYBINDINGS[i];
            String current = "None";

            if (key == -1) {
                // None
            } else {
                // F1 - F12: 112 - 123
                if (key == 27)
                    current = OPTIONS[13];
                else
                    current = OPTIONS[key - 111];
            }

            RSInterface.interfaceCache[MIN_FRAME + 3 * i].dropdown.setSelected(current);
        }
        RSInterface.interfaceCache[ESCAPE_CONFIG].active = Configuration.escapeCloseInterface;
    }
}
