package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.client.cache.media.RSInterface;

public class SkillTree extends RSInterface {

    private static int STARTING_POINT = 90000;

    public static void unpack() {
        main_view();
    }

    public static void main_view() {
        int localId = STARTING_POINT;
        RSInterface main = addInterface(localId++);
        main.totalChildren(65);
        int childId = 0;
        addAdvancedSprite(localId, 1645);//Shell
        main.child(childId++, localId++, 4, 4);
        addAdvancedSprite(localId, 1647);//Nodes n Lines
        main.child(childId++, localId++, 4, 4);
        addClose_Button(localId, 1016, 1017);//Self Explanatory
        main.child(childId++, localId++, 490, 8);
        /**
         * Bridge Buttons
         */
        add_hovered_button(localId, 1678, 1677, "Bridge");
        main.child(childId++, localId++, 190, 43);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1677, 1648);
        main.child(childId++, localId++, 190, 43);
        add_hovered_button(localId, 1678, 1677, "Bridge");
        main.child(childId++, localId++, 286, 43);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1677, 1648);
        main.child(childId++, localId++, 286, 43);
        add_hovered_button(localId, 1678, 1677, "Bridge");
        main.child(childId++, localId++, 188, 168);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1677, 1648);
        main.child(childId++, localId++, 188, 168);
        add_hovered_button(localId, 1678, 1677, "Bridge");
        main.child(childId++, localId++, 289, 168);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1677, 1648);
        main.child(childId++, localId++, 289, 168);
        /**
         * Components of Interface
         */
        //Path 1
        //Section 1
        add_node(localId, 1681, 1682, "Node 1"); //Node 1
        main.child(childId++, localId++, 223, 277);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 223, 277);
        add_node(localId, 1681, 1682, "Node 2"); //Node 2
        main.child(childId++, localId++, 197, 252);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 197, 252);
        add_node(localId, 1681, 1682, "Node 3"); //Node 3
        main.child(childId++, localId++, 171, 227);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 171, 227);
        //Section 2
        add_node(localId, 1649, 1650, "Node 4"); //Node 4
        main.child(childId++, localId++, 142, 198);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1650, 1648);
        main.child(childId++, localId++, 142, 198);
        add_node(localId, 1681, 1682, "Node 5"); //Node 5
        main.child(childId++, localId++, 153, 180);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 153, 180);
        add_node(localId, 1681, 1682, "Node 6"); //Node 6
        main.child(childId++, localId++, 153, 150);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 153, 150);
        add_node(localId, 1681, 1682, "Node 7"); //Node 7
        main.child(childId++, localId++, 153, 121);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 153, 121);
        //Section 3
        add_node(localId, 1653, 1654, "Node 8"); //Node 8
        main.child(childId++, localId++, 142, 78);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1654, 1648);
        main.child(childId++, localId++, 142, 78);

        //Path 2
        //Section 1
        add_node(localId, 1681, 1682, "Node 1"); //Node 1
        main.child(childId++, localId++, 249, 265);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 249, 265);
        add_node(localId, 1681, 1682, "Node 2"); //Node 2
        main.child(childId++, localId++, 249, 225);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 249, 225);
        add_node(localId, 1681, 1682, "Node 3"); //Node 3
        main.child(childId++, localId++, 249, 192);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 249, 192);
        //Section 2
        add_node(localId, 1661, 1662, "Node 4"); //Node 4
        main.child(childId++, localId++, 238, 146);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1661, 1648);
        main.child(childId++, localId++, 238, 146);
        add_node(localId, 1681, 1682, "Node 5"); //Node 5
        main.child(childId++, localId++, 249, 124);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 249, 124);
        add_node(localId, 1681, 1682, "Node 6"); //Node 6
        main.child(childId++, localId++, 249, 88);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 249, 88);
        add_node(localId, 1681, 1682, "Node 7"); //Node 7
        main.child(childId++, localId++, 249, 56);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 249, 56);
        //Section 3
        add_node(localId, 1665, 1666, "Node 8"); //Node 8
        main.child(childId++, localId++, 238, 19);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1665, 1648);
        main.child(childId++, localId++, 238, 19);

        //Path 3
        //Section 1
        add_node(localId, 1681, 1682, "Node 1"); //Node 1
        main.child(childId++, localId++, 276, 278);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 276, 278);
        add_node(localId, 1681, 1682, "Node 2"); //Node 2
        main.child(childId++, localId++, 303, 252);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 303, 252);
        add_node(localId, 1681, 1682, "Node 3"); //Node 3
        main.child(childId++, localId++, 328, 228);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 328, 228);
        //Section 2
        add_node(localId, 1669, 1670, "Node 4"); //Node 4
        main.child(childId++, localId++, 335, 198);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1669, 1648);
        main.child(childId++, localId++, 335, 198);
        add_node(localId, 1681, 1682, "Node 5"); //Node 5
        main.child(childId++, localId++, 346, 180);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 346, 180);
        add_node(localId, 1681, 1682, "Node 6"); //Node 6
        main.child(childId++, localId++, 346, 150);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 346, 150);
        add_node(localId, 1681, 1682, "Node 7"); //Node 7
        main.child(childId++, localId++, 346, 121);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1681, 1648);
        main.child(childId++, localId++, 346, 121);
        //Section 3
        add_node(localId, 1673, 1674, "Node 8"); //Node 8
        main.child(childId++, localId++, 335, 78);
        add_tooltip(localId, "Testing a crazy ass system that could potentially do some sick shit. Hopefully this works", 1673, 1648);
        main.child(childId++, localId++, 335, 78);

        add_node(localId, 1657, 1658, "Starter Node");
        main.child(childId++, localId++, 239, 286);
        add_tooltip(localId, "Purchase access to the Skill Tree", 1657, 1648);
        main.child(childId++, localId++, 239, 286);

        addToItemGroup(localId, 1, 1, 6, 6, true, new String[] { null, null, null, null, null }, true);
        main.child(childId++, localId++, 192, 43);
        addToItemGroup(localId, 1, 1, 6, 6, true, new String[] { null, null, null, null, null }, true);
        main.child(childId++, localId++, 288, 43);
        addToItemGroup(localId, 1, 1, 6, 6, true, new String[] { null, null, null, null, null }, true);
        main.child(childId++, localId++, 190, 168);
        addToItemGroup(localId, 1, 1, 6, 6, true, new String[] { null, null, null, null, null }, true);
        main.child(childId++, localId++, 291, 168);

    }

}
