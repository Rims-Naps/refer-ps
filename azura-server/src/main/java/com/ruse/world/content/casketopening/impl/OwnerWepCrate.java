package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.casketopening.Box;

public class OwnerWepCrate {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, 10000, 75),
            new Box(10945, 10, 10, false),


            new Box(5585, 25, 5, false),
            new Box(1448, 10, 1, false),

            new Box(15668, 1, .35, true),

            //SOULS
            new Box(23057, 1, .45, false),




            //RANDOM BOXES
            new Box(2009, 1, .35, true),
            new Box(2624, 1, .35, true),
            new Box(3578, 1, .35, true),
            new Box(731, 1, .16, true),

            //WEPS
            new Box(750, 1, .12, true),
            new Box(751, 1, .12, true),
            new Box(752, 1, .12, true),



    };

}
