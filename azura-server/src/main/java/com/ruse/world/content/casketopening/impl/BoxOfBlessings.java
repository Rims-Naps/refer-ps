package com.ruse.world.content.casketopening.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.casketopening.Box;

public class BoxOfBlessings {

    public static Box[] loot = { //
            new Box(ItemDefinition.COIN_ID, Misc.random(100, 5000), 90),

            new Box(20031, 1, 5.0, true),
            new Box(20036, 1, 5.0, true),
            new Box(20041, 1, 5.0, true),

            new Box(20032, 1, 2.0, true),
            new Box(20037, 1, 2.0, true),
            new Box(20042, 1, 2.0, true),

            new Box(20033, 1, 1.0, true),
            new Box(20038, 1, 1.0, true),
            new Box(20043, 1, 1.0, true),



    };

}