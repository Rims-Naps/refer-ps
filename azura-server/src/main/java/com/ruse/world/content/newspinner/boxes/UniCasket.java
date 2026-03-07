package com.ruse.world.content.newspinner.boxes;

import com.ruse.model.Item;
import com.ruse.util.Misc;

import java.util.ArrayList;

public class UniCasket implements MysteryBox {


    private ArrayList<Item> common_items = new ArrayList<Item>() {
        {
            add(new Item(23059, 1));
            add(new Item(19101, 35));
            add(new Item(995, 100000));
            add(new Item(21204, 30));
            add(new Item(8018, 1));
            add(new Item(19051, 1));
            add(new Item(10246, 1));
            add(new Item(19023, 8));
            add(new Item(19024, 5));
            add(new Item(23058, 1));
            add(new Item(8018, 1));
            add(new Item(995, 125000));
            add(new Item(23111, 1));
            add(new Item(15357, 5));
            add(new Item(23123, 1));
            add(new Item(23174, 1));
            add(new Item(23120, 1));
            add(new Item(995, 135000));
            add(new Item(23125, 1));
        }
    };



    private ArrayList<Item> uncommon_items = new ArrayList<Item>() {
        {
            add(new Item(19023, 10));
            add(new Item(19024, 6));
            add(new Item(18990, 1));
            add(new Item(995, 150000));
            add(new Item(12630, 1));
            add(new Item(20427, 1));
            add(new Item(20260, 1));
            add(new Item(20095, 1));
            add(new Item(995, 165000));
            add(new Item(19101, 20));
            add(new Item(18949, 15));
            add(new Item(18975, 1));
        }
    };
    private ArrayList<Item> rare_items = new ArrayList<Item>() {
        {
            add(new Item(19019, 1));
            add(new Item(995, 175000));
            add(new Item(8018, 10));
            add(new Item(28, 75));
            add(new Item(6183, 5));
            add(new Item(995, 185000));
            add(new Item(6833, 15));
            add(new Item(15815, 1));
            add(new Item(12610, 1));
            add(new Item(5020, 500000));
        }
    };

    private ArrayList<Item> super_rare_items = new ArrayList<Item>() {
        {
            add(new Item(17724, 1));
            add(new Item(17654, 1));
            add(new Item(6545, 1));
            add(new Item(995, 200000));
            add(new Item(23060, 1));
            add(new Item(9059, 1));
            add(new Item(13640, 1));
            add(new Item(14056, 1));
            add(new Item(19048, 25));
            add(new Item(995, 215000));
            add(new Item(23126, 1));
        }
    };
    @Override
    public String getName() {
        return "Universal Casket";
    }

    @Override
    public int getId() {
        return 10248;
    }

    @Override
    public ArrayList<Item> getCommon_items() {
        return common_items;
    }

    @Override
    public ArrayList<Item> getUncommon_items() {
        return uncommon_items;
    }

    @Override
    public ArrayList<Item> getRare_items() {
        return rare_items;
    }

    @Override
    public ArrayList<Item> getSuper_rare_items() {
        return super_rare_items;
    }
}
