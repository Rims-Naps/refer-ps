package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.casketopening.Box;

public class CorruptCrate {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, 1000, 75),
            new Box(10945, 1, 10, false),


            new Box(15667, 1, 5, false),
            new Box(1448, 1, 1, false),
            new Box(1446, 1, .5, false),

            new Box(15668, 1, .35, true),

            //SOULS
            new Box(12423, 1, .45, false),
            new Box(12424, 1, .45, false),
            new Box(12427, 1, .45, false),

            //SIGILS
            new Box(2660, 1, .35, true),
            new Box(2661, 1, .35, true),
            new Box(2662, 1, .35, true),

            //BIG MATS(hilt)
            new Box(2657, 1, .25, true),
            new Box(2658, 1, .25, true),
            new Box(2659, 1, .25, true),

            new Box(3507, 1, .16, true),
            new Box(3508, 1, .16, true),
            new Box(3509, 1, .16, true),


            //WEPS
            new Box(2651, 1, .12, true),
            new Box(2653, 1, .12, true),
            new Box(2655, 1, .12, true),



    };

}
