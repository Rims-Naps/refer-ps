package com.ruse.world.content.newspinner.boxes;

import com.ruse.model.Item;
import com.ruse.util.Misc;

import java.util.ArrayList;

public class InfinCasket implements MysteryBox {

    private ArrayList<Item> common_items = new ArrayList<Item>() {
        {
            add(new Item(995, 175000));
            add(new Item(19101, 30));
            add(new Item(10248, 1));
            add(new Item(12610, 1));
            add(new Item(995,185000));
            add(new Item(19019, 1));
            add(new Item(10246, 2));
            add(new Item(23173, 5));
            add(new Item(19051, 10));
            add(new Item(18991, 1));
            add(new Item(19023, 9));
            add(new Item(19024, 7));
            add(new Item(15357, 10));
            add(new Item(5020, 1500000));
            add(new Item(995, 195000));
            add(new Item(3578, 3));
            add(new Item(995, 200000));
            add(new Item(10834, 350));
            add(new Item(23059, 1));
        }
    };

    private ArrayList<Item> uncommon_items = new ArrayList<Item>() {
        {
            add(new Item(20427, 1));
            add(new Item(20260, 1));
            add(new Item(20095, 1));
            add(new Item(995, 225000));
            add(new Item(18949, 20));
            add(new Item(19005, 1));
            add(new Item(23112, 1));
            add(new Item(6183, 15));
            add(new Item(19048, 30));
            add(new Item(6798, 1));
            add(new Item(19024, 8));
            add(new Item(995, 215000));
        }
    };
    private ArrayList<Item> rare_items = new ArrayList<Item>() {
        {
            add(new Item(23060, 1));
            add(new Item(22111, 1));
            add(new Item(6545, 1));
            add(new Item(3592, 2));
            add(new Item(995, 250000));
            add(new Item(8018, 13));
            add(new Item(995, 235000));
            add(new Item(9059, 1));
            add(new Item(14056, 1));
            add(new Item(13640, 1));
        }
    };

    private ArrayList<Item> super_rare_items = new ArrayList<Item>() {
        {
            add(new Item(19023, 25));
            add(new Item(18768, 1));
            add(new Item(23040, 1));
            add(new Item(23002, 1));
            add(new Item(3598, 4));
            add(new Item(13150, 3));
            add(new Item(4357, 1));
            add(new Item(995, 350000));
            add(new Item(6466, 1));
            add(new Item(11048, 1));
            add(new Item(23065, 1));
        }
    };
    @Override
    public String getName() {
        return "Infinity Casket";
    }

    @Override
    public int getId() {
        return 10250;
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
