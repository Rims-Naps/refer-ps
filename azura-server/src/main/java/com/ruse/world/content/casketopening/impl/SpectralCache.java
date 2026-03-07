package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.casketopening.Box;

public class SpectralCache {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, 1000, 75),
            new Box(10945, 1, 10, false),


            new Box(15667, 1, 5, false),
            new Box(1448, 1, 1, false),
            new Box(1446, 1, .5, false),

            new Box(15668, 1, .35, true),

            //SOULS
            new Box(2061, 1, .45, false),
            new Box(2064, 25, .45, false),
            new Box(2064, 50, .45, false),

            new Box(10946, 1, .35, false),

            //BIG MATS(hilt)
            new Box(2072, 1, .25, true),
            new Box(2073, 1, .25, true),
            new Box(2074, 1, .25, true),
            //BIG MATS(hilt)
            new Box(2065, 1, .25, true),
            new Box(2066, 1, .25, true),
            new Box(2067, 1, .25, true),

            new Box(2058, 1, .16, true),
            new Box(2059, 1, .16, true),
            new Box(2060, 1, .16, true),


            //WEPS
            new Box(2086, 1, .10, true),
            
    };

}
