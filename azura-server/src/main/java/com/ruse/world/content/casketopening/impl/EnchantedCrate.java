package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.casketopening.Box;

public class EnchantedCrate {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, 1000, 75),
            new Box(10945, 1, 10, false),


            new Box(15667, 1, 5, false),
            new Box(1448, 1, 1, false),
            new Box(1446, 1, .5, false),

            new Box(15668, 1, .35, true),

            //SOULS
            new Box(17130, 1, .45, false),
            new Box(10946, 1, .45, false),
            new Box(15670, 1, .45, false),

            //SIGILS
            new Box(2554, 1, .35, true),
            //BIG MATS(hilt)
            new Box(2556, 1, .25, true),

            new Box(15386, 1, .16, true),
            new Box(18678, 1, .16, true),
            new Box(7871, 1, .16, true),


            //WEPS
            new Box(6749, 1, .12, true),
            new Box(13151, 1, .12, true),
            new Box(19705, 1, .12, true),



    };

}
