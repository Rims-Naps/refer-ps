package com.ruse.donation.daily;

public enum DailyDealItem {
    BOND_10(23057),
    BOND_25(23058),
    BOND_50(23059),
    NECRO_CRATES(23173),
    BOX_OF_TREASURES(15668),
    BOND_CASKETS(15671),
    BOX_OF_BLESSINGS(15669),
    T1_MEMBERSHIP(2701);

    int itemId;

    DailyDealItem(int id) {
        this.itemId = id;
    }
}
