package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.cache.media.RSInterface;

public class Parties extends RSInterface {

    private static final int STARTING_POINT = 48500;

    public static void unpack() {
        current_parties();
        viewing_parties();
    }

    public static void current_parties() {
        int localId = STARTING_POINT;
        RSInterface main = addInterface(localId++);
        int childId = 0;
        main.totalChildren(10);
        addSprite(localId, 5754);
        main.child(childId++, localId++, 13, 8);
        addText(localId, "Current Parties", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(childId++, localId++, 256, 17);
        addCloseButton(localId);
        main.child(childId++, localId++, 475, 17);
        addText(localId, "Host", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 42, 50);
        addText(localId, "Size", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 150, 50);
        addText(localId, "Completions", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 275, 50);
        addText(localId, "Best Wave", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 400, 50);
        RSInterface scroller = addInterface(localId++);
        scroller.height = 218;
        scroller.width = 463 - 16;
        scroller.scrollMax = 500;
        scroller.totalChildren(125);
        int childIdS = 0;
        int x = 0;
        int y = 0;
        int x_text_off = 12;
        int y_text_off = 7;
        for (int i = 0; i < 25; i++) {
            add_button(localId, i == 0 ? 5759 : i % 2 == 0 ? 5759 : 5760, "View Party");
            scroller.child(childIdS++, localId++, x, y);
            y += 20;
        }
        y = 0;
        for (int i = 0; i < 25; i++) {
            addText(localId, "Brad", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
            scroller.child(childIdS++, localId++, x + x_text_off, y + y_text_off);
            x_text_off += 125;
            addText(localId, "0/3", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            scroller.child(childIdS++, localId++, x + x_text_off, y + y_text_off);
            x_text_off += 135;
            addText(localId, "99", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            scroller.child(childIdS++, localId++, x + x_text_off, y + y_text_off);
            x_text_off += 128;
            addText(localId, "3:01:21", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            scroller.child(childIdS++, localId++, x + x_text_off, y + y_text_off);
            y_text_off += 20;
            x_text_off = 12;
        }

        add_hovered_button_string(localId, 5756, 5755, "Refresh", true);
        main.child(childId++, localId++, 113, 284);
        add_hovered_button_string(localId, 5756, 5755, "Make Party", true);
        main.child(childId++, localId++, 300, 284);
        main.child(childId++, scroller.parentID, 25, 66);
    }

    public static void viewing_parties() {
        int localId = STARTING_POINT + 300;
        RSInterface main = addInterface(localId++);
        int childId = 0;
        main.totalChildren(15);
        addSprite(localId, 5758);
        main.child(childId++, localId++, 13, 8);
        addText(localId, "PlayerNames Party", ColorConstants.VANGUARD_PURPLE, true, true, 52, 3);
        main.child(childId++, localId++, 256, 17);
        addCloseButton(localId);
        main.child(childId++, localId++, 475, 17);
        addText(localId, "Names", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 42, 50);
        addText(localId, "Combat", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 152, 50);
        addText(localId, "Melee", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 212, 50);
        addText(localId, "Range", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 272, 50);
        addText(localId, "Magic", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 332, 50);
        addText(localId, "Best Wave", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
        main.child(childId++, localId++, 392, 50);
        RSInterface myParty = addInterface(localId++);
        myParty.totalChildren(35);
        int childMyP = 0;
        int y = 5;
        for (int i = 0; i < 5; i++) {
            add_button(localId, 5757, "Kick Member");
            myParty.child(childMyP++, localId++, 0, y);
            addText(localId, "Brad", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
            myParty.child(childMyP++, localId++, 42, y);
            addText(localId, "13", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            myParty.child(childMyP++, localId++, 169, y);
            addText(localId, "900", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            myParty.child(childMyP++, localId++, 226, y);
            addText(localId, "700", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            myParty.child(childMyP++, localId++, 287, y);
            addText(localId, "1200", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            myParty.child(childMyP++, localId++, 345, y);
            addText(localId, "1", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            myParty.child(childMyP++, localId++, 422, y);
            y += 17;
        }
        RSInterface applicants = addInterface(localId++);
        applicants.width = 488 - 16;
        applicants.height = 112;
        applicants.scrollMax = 202;
        applicants.totalChildren(84);
        int childApp = 0;
        y = 5;
        for (int i = 0; i < 12; i++) {
            add_button(localId, 5757, "Accept Invite");
            applicants.child(childApp++, localId++, 0, y);
            addText(localId, "Brad", ColorConstants.VANGUARD_PURPLE, false, true, 52, 0);
            applicants.child(childApp++, localId++, 42, y);
            addText(localId, "13", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            applicants.child(childApp++, localId++, 169, y);
            addText(localId, "900", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            applicants.child(childApp++, localId++, 226, y);
            addText(localId, "700", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            applicants.child(childApp++, localId++, 287, y);
            addText(localId, "1200", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            applicants.child(childApp++, localId++, 345, y);
            addText(localId, "1", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
            applicants.child(childApp++, localId++, 422, y);
            y += 17;
        }
        main.child(childId++, myParty.id, 0, 64);
        main.child(childId++, applicants.id, 0, 171);
        addText(localId, "Applicants", ColorConstants.VANGUARD_PURPLE, true, true, 52, 0);
        main.child(childId++, localId++, 256, 155);
        add_hovered_button_string(localId, 5756, 5755, "Back", true);
        main.child(childId++, localId++, 66, 284);
        add_hovered_button_string(localId, 5756, 5755, "Refresh", true);
        main.child(childId++, localId++, 206 , 284);
        add_hovered_button_string(localId, 5756, 5755, "Disband", true);
        main.child(childId++, localId++, 347, 284);
    }


}
