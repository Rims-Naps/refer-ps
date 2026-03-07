package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.casketopening.Box;

public class EggCrate {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, 1000, 75),
            new Box(715, 15, 25, false),


            new Box(15667, 10, 10, false),
            new Box(10945, 1, 5, false),
            new Box(20109, 25, 1, false),
            new Box(20081, 1, .5, false),

            new Box(716, 1, .35, true),

            new Box(20082, 1, .25, false),

            new Box(17129, 1, .25, true),
            new Box(3578, 1, .25, true),

            new Box(17819, 1, .25, true),
            new Box(3512, 10, .25, true),
            new Box(5585, 10, .25, true),

            //BOOK + CAPE
            new Box(719, 1, .15, true),
            new Box(21036, 1, .15, true),

            //WEPS
            new Box(711, 1, .12, true),
            new Box(712, 1, .12, true),
            new Box(713, 1, .12, true),



    };

}
