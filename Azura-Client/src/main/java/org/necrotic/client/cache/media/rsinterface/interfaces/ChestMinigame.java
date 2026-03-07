package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.cache.media.RSInterface;

public class ChestMinigame extends RSInterface {

    private static final int STARTING_POINT = 42200;

    public static void unpack() {
        main_tab();
    }

    public static void main_tab() {
        int localId = STARTING_POINT;
        int childId = 0;
        RSInterface main = addInterface(localId++);
        main.totalChildren(13);
        addSpriteLoader(localId, 5612);
        main.child(childId++, localId++, 27,33);
        addCloseButton(localId);
        main.child(childId++, localId++, 459, 42);
        addText(localId, "Loot Editor", ColorConstants.PURPLE, true, true, 52, 2);
        main.child(childId++, localId++, 256, 43);
        addText(localId, "Tier 1 Loot", ColorConstants.PURPLE, true, true, 52, 0);
        main.child(childId++, localId++, 109, 77);
        addText(localId, "Tier 2 Loot", ColorConstants.PURPLE, true, true, 52, 0);
        main.child(childId++, localId++, 256, 77);
        addText(localId, "Tier 3 Loot", ColorConstants.PURPLE, true, true, 52, 0);
        main.child(childId++, localId++, 403, 77);
        itemGroupAutoScroll(localId++, localId--, 3, 20, 15, 8, 3, new String[] { null, null, null, null, null }, true);
        main.child(childId++, localId++, 41, 100);
        localId++;
        itemGroupAutoScroll(localId++, localId--, 3, 20, 15, 8, 3, new String[] { null, null, null, null, null }, true);
        main.child(childId++, localId++, 188, 100);
        localId++;
        itemGroupAutoScroll(localId++, localId--, 3, 20, 15, 8, 3, new String[] { null, null, null, null, null }, true);
        main.child(childId++, localId++, 335, 100);
        localId++;
        add_hovered_button_string(localId, 5526, 5527, "Clear Tier 1", true);
        main.child(childId++, localId++, 64, 225);
        add_hovered_button_string(localId, 5526, 5527, "Clear Tier 2", true);
        main.child(childId++, localId++, 211, 225);
        add_hovered_button_string(localId, 5526, 5527, "Clear Tier 3", true);
        main.child(childId++, localId++, 358, 225);
        add_hovered_button_string(localId, 5526, 5527, "Editting: Tier 1", true);
        main.child(childId++, localId++, 64, 265);

    }

}
