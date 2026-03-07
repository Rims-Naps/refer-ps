package com.ruse.world.content.newspinner.boxes;

import com.ruse.model.Item;
import com.ruse.util.Misc;

import java.util.ArrayList;

public class GalCasket implements MysteryBox {

    private ArrayList<Item> common_items = new ArrayList<Item>() {
        {
            add(new Item(8018, 3));
            add(new Item(15357, 3));
            add(new Item(23058, 1));
            add(new Item(23058, 1));
            add(new Item(8018, 3));
            add(new Item(21204, 15));
            add(new Item(23058, 1));
            add(new Item(23173, 5));
            add(new Item(15357, 3));
            add(new Item(23173, 5));
            add(new Item(23058, 1));
            add(new Item(8018, 3));
            add(new Item(21204, 15));
            add(new Item(15357, 3));
            add(new Item(23173, 5));
            add(new Item(8018, 3));
            add(new Item(21204, 15));
            add(new Item(15357, 3));
            add(new Item(23058, 1));
        }
    };

    private ArrayList<Item> uncommon_items = new ArrayList<Item>() {
        {
            add(new Item(28, 30));
            add(new Item(23059, 1));
            add(new Item(6183, 3));
            add(new Item(23059, 1));
            add(new Item(23059, 1));
            add(new Item(28, 30));
            add(new Item(13630, 1));
            add(new Item(4888, 1));
            add(new Item(19149, 1));
            add(new Item(995, 35000));
            add(new Item(6183 , 3));
            add(new Item(23059, 1));
        }
    };
    private ArrayList<Item> rare_items = new ArrayList<Item>() {
        {
            add(new Item(19023, 5));
            add(new Item(19024, 2));
            add(new Item(21816, 1));
            add(new Item(11304, 1));
            add(new Item(17604, 1));
            add(new Item(22109, 1));
            add(new Item(995, 50000));
            add(new Item(19023, 5));
            add(new Item(11304, 1));
            add(new Item(19024, 2));
        }
    };

    private ArrayList<Item> super_rare_items = new ArrayList<Item>() {
        {
            add(new Item(21816, 1));
            add(new Item(19024, 1));
            add(new Item(19023, 1));
            add(new Item(11304, 1));
            add(new Item(18975, 1));
            add(new Item(19024, 1));
            add(new Item(995, 75000));
            add(new Item(18991, 1));
            add(new Item(18975, 1));
            add(new Item(995, 75000));
            add(new Item(19005, 1));
        }
    };

    @Override
    public String getName() {
        return "Galaxy Casket";
    }

    @Override
    public int getId() {
        return 10246;
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
