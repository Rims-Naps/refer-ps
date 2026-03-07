package com.ruse.world.content.upgrading;

import com.ruse.model.Item;
import lombok.Getter;

import java.util.ArrayList;

import static com.ruse.world.content.upgrading.Upgradeables.UpgradeType.*;

@Getter
public enum Upgradeables {
// easy
    EAXE(WEAPON, new Item(23079, 1), new Item(17734, 1), 5000, 45),
    EBOW(WEAPON, new Item(8088, 1), new Item(8001, 1), 5000, 45),
    ESTAFF(WEAPON, new Item(13941, 1), new Item(19331, 1), 5000, 45),




    GOKUHEAD(ARMOUR, new Item(13323, 1), new Item(17614, 1), 25000, 40),
    GOKUBODY(ARMOUR, new Item(13324, 1), new Item(17616, 1), 25000, 40),
    GOKULEGS(ARMOUR, new Item(13325, 1), new Item(17618, 1), 25000, 40),
    GOKUGLOVES(ARMOUR, new Item(13326, 1), new Item(19988, 1), 25000, 40),
    GOKUBOOTS(ARMOUR, new Item(13327, 1), new Item(19989, 1), 25000, 40),

    FORGOTTENPHAT(ARMOUR, new Item(18417, 1), new Item(13277, 1), 85000, 35),
    EXOTICMASK(ARMOUR, new Item(13277, 1), new Item(18975, 1), 500000, 15),

    ASCENSIONWINGS(ARMOUR, new Item(8830, 1), new Item(19052, 1), 10000000, 50),
    ASCENSIONWINGST(ARMOUR, new Item(19052, 1), new Item(19053, 1), 20000000, 35),

    // med
    STONETOOTHAXES(WEAPON, new Item(17698, 1), new Item(4888, 1), 15000, 40),
    MARSHLANDBOW(WEAPON, new Item(17672, 1), new Item(19149, 1), 15000, 40),
    GHASTKATANA(WEAPON, new Item(13634, 1), new Item(13630, 1), 15000, 40),
//elite
    HOLYBLADE(WEAPON, new Item(14915, 1), new Item(17724, 1), 40000, 45),
    TYPHOONSTAFFF(WEAPON, new Item(22087, 1), new Item(12904, 1), 40000, 45),
    NIGHTMAREBOW(WEAPON, new Item(5011, 1), new Item(17654, 1), 40000, 45),
  //  HEARTBREAKER(WEAPON, new Item(17704, 1), new Item(19136, 1), 25000, 55),
//rings/necks
    GODLYAURA(ACCESSORY, new Item(12630, 1), new Item(12610, 1), 50000, 35),
    HATRED(ACCESSORY, new Item(23094, 1), new Item(23093, 1), 15000, 45),
    HATREDX(ACCESSORY, new Item(23093, 1), new Item(23092, 1), 75000, 35),
    LUCK_1(ACCESSORY, new Item(23087, 1), new Item(23090, 1), 25000, 50),
    ENRAGED(ACCESSORY, new Item(23090, 1), new Item(21068, 1), 50000, 35),
//pots
    OVL1(MISC, new Item(23124, 1), new Item(23125, 1), 25000, 40),
    OVL2(MISC, new Item(23125, 1), new Item(23126, 1), 125000, 30),
    HEALTH_1(MISC, new Item(23118, 1), new Item(23119, 1), 7500, 45),
    HEALTH_2(MISC, new Item(23119, 1), new Item(23120, 1), 15000, 45),
    HOLY_1(MISC, new Item(23121, 1), new Item(23122, 1), 15000, 45),
    HOLY_2(MISC, new Item(23122, 1), new Item(23123, 1), 100000, 35),
    TRIBRIDPETT1(MISC, new Item(23114, 1), new Item(23112, 1), 75000, 35),
    DRBOOST(MISC, new Item(23174, 1), new Item(19019, 1), 100000, 15),
    DMGSCROLL(MISC, new Item(15359, 1), new Item(15357, 1), 10000, 35),
    BOXOFBOXES(MISC, new Item(6833, 1), new Item(3687, 1), 15000, 15),






    ;


    private final UpgradeType type;
    private final Item required;
    private final Item reward;
    private final int cost;
    private final int successRate;
    private final boolean rare;

    Upgradeables(UpgradeType type, Item required, Item reward, int cost, int successRate, boolean rare) {
        this.type = type;
        this.required = required;
        this.reward = reward;
        this.cost = cost;
        this.successRate = successRate;
        this.rare = rare;
    }

    Upgradeables(UpgradeType type, Item required, Item reward, int cost, int successRate) {
        this.type = type;
        this.required = required;
        this.reward = reward;
        this.cost = cost;
        this.successRate = successRate;
        this.rare = false;
    }

    public static ArrayList<Upgradeables> getForType(UpgradeType type){
        ArrayList<Upgradeables> upgradeablesArrayList = new ArrayList<>();
        for (Upgradeables upgradeables : values()){
            if (upgradeables.getType() == type){
                upgradeablesArrayList.add(upgradeables);
            }
        }
        return upgradeablesArrayList;
    }


    public enum UpgradeType{

        WEAPON, ARMOUR, ACCESSORY, MISC

    }


}
