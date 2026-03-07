package com.ruse.world.content.megaChests;

public class ChestRates {
    public static double[] BronzeRates = new double[]{
            0, //legendary
            0,  //diamond
            .9, //golden - 1/111
            11.1,  //silver - 1/10
            88, //bronze - 90%
    };
    public static double[] SilverRates = new double[]{
            0, //legendary
            .9,  //diamond
            9.1, //golden
            40,  //silver
            50, //bronze
    };
    public static double[] GoldRates = new double[]{
            0.2, //legendary
            4.8,  //diamond
            25, //golden
            70,  //silver
            0, //bronze
    };
    public static double[] diamondRates = new double[]{
            0.5, //legendary
            10.5,  //diamond
            41, //golden
            48,  //silver
            0, //bronze
    };
}


