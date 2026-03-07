package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.casketopening.Box;

public class FrostCrate {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, 500, 75),
            new Box(1450, 15, 25, false),


            new Box(1454, 1, 10, false),
            new Box(10945, 1, 5, false),
            new Box(1451, 1, 1, false),
            new Box(1452, 1, .5, false),

            new Box(1465, 1, .35, true),


            //SANTA GEAR
            new Box(1455, 1, .25, true),
            new Box(1456, 1, .25, true),
            new Box(1457, 1, .25, true),
            new Box(1458, 1, .25, true),
            new Box(1459, 1, .25, true),

            //BOOK + CAPE
            new Box(1460, 1, .15, true),
            new Box(1466, 1, .15, true),

            //WEPS
            new Box(1461, 1, .12, true),
            new Box(1462, 1, .12, true),
            new Box(1463, 1, .12, true),



    };

}
