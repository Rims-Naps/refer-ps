package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.casketopening.Box;

public class NecroCrate {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, 500, 75),
            new Box(20066, 1, 50, false),
            new Box(621, 500, 25, false),

            new Box(20006, 1, 5, true),
            new Box(20007, 1, 5, true),
            new Box(20008, 1, 5, true),
            new Box(20009, 1, 5, true),
            new Box(20061, 1, 4, true),
            new Box(20065, 1, 3, true),
            new Box(20062, 1, 2, true),
            new Box(20063, 1, 2, true),
            new Box(20064, 1, 1, true),

    };

}
