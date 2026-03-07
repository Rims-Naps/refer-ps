package org.necrotic.client.cache.media.rsinterface.dropdowns;

import org.necrotic.Configuration;
import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.rsinterface.dropdowns.impl.Keybinding;
import org.necrotic.client.gameframe.GameFrame;

public enum Dropdown {

    KEYBIND_SELECTION() {
        @Override
        public void selectOption(int selected, RSInterface dropdown) {
            Keybinding.bind((dropdown.id - Keybinding.MIN_FRAME) / 3, selected);
        }
    },


    REASON_FOR_HELP() {
        @Override
        public void selectOption(int selected, RSInterface r) {
            Client.getOut().putOpcode(185);
            Client.getOut().putInt(r.id * 2 + selected);
        }
    },

    STAFF_TICKET_FILTER() {
        @Override
        public void selectOption(int selected, RSInterface r) {
            Client.getOut().putOpcode(185);
            Client.getOut().putInt(r.id * 2 + selected);
        }
    },

    SCREEN_ELEMENTS() {
        @Override
        public void selectOption(int selected, RSInterface r) {
            if (selected == 0) {
                Client.instance.toggleSize(GameFrame.ScreenMode.FIXED);
            } else if (selected == 1) {
                Client.instance.toggleSize(GameFrame.ScreenMode.RESIZABLE_CLASSIC);
            } else if (selected == 2) {
                Client.instance.toggleSize(GameFrame.ScreenMode.RESIZABLE_MODERN);
            }
        }
    },
    NPC_ATTACK_OPTION_PRIORITY() {
        @Override
        public void selectOption(int selected, RSInterface r) {
            Configuration.npcAttackOptionPriority = selected;
        }
    };


    private Dropdown() {
    }

    public abstract void selectOption(int selected, RSInterface r);
}
