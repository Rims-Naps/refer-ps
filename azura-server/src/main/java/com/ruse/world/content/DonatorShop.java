package com.ruse.world.content;

import com.ruse.model.container.impl.Shop;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

public class DonatorShop {

    public static final int ITEM_CHILD_ID = 33900;
    public static final int ITEM_CHILD_ID_CLICK = -31636;
    public static final int INTERFACE_ID = 118000;

    private Player player;

    public DonatorShop(Player player) {
        this.player = player;
    }

    public boolean handleButton(int buttonId) {

        switch (buttonId) {
            case 118005:
                openInterface(DonatorShopType.WEAPON);
                return true;
            case 118006:
                openInterface(DonatorShopType.ARMOUR);
                return true;
            case 118007:
                openInterface(DonatorShopType.ACCESSORY);
                return true;
            case 118008:
                openInterface(DonatorShopType.MISC);
                return true;
        }

        return false;
    }

    public void openInterface(DonatorShopType type) {
        player.getPacketSender().sendConfig(5333, type.ordinal());
        Shop.ShopManager.getShops().get(type.getShopId()).open(player);
    }
    
    public static Object[] getPrice(int item){
        switch (item) {
            case 754://OWNER WEAPON CRATE
                return new Object[]{150, "Donator points"};
            case 15666://BEGINNER WEAPON CRATE
                return new Object[]{25, "Donator points"};
            case 23171://MEDIUM WEAPON CRATE
                return new Object[]{50, "Donator points"};
            case 23172://ELITE WEAPON CRATE
                return new Object[]{100, "Donator points"};
            case 23055://MOSSHEART BLADE
                return new Object[]{150, "Donator points"};
            case 23056://MOSSHEART BOW
                return new Object[]{150, "Donator points"};
            case 13943://MOSSHEART STAFF
                return new Object[]{150, "Donator points"};
            case 16418://MYSTIC BOW(4)
                return new Object[]{325, "Donator points"};
            case 10260://E CRATE
                return new Object[]{20, "Donator points"};
            case 10262://F CRATE
                return new Object[]{20, "Donator points"};
            case 10256://W CRATE
                return new Object[]{20, "Donator points"};
            case 19811://GEAR
                return new Object[]{30, "Donator points"};
            case 19473://GEAR
                return new Object[]{30, "Donator points"};
            case 19472://GEAR
                return new Object[]{30, "Donator points"};
            case 19945://GEAR
                return new Object[]{30, "Donator points"};
            case 19946://GEAR
                return new Object[]{30, "Donator points"};
            case 12452:
                return new Object[]{75, "Donator points"};
            case 15670://BOND CASKET
                return new Object[]{35, "Donator points"};
            case 15667://BOX OF PLUNDERS
                return new Object[]{2, "Donator points"};
            case 17130://PANDORAS BOX
                return new Object[]{30, "Donator points"};
            case 3578://OWNER GOODIE
                return new Object[]{125, "Donator points"};
            case 1446://RESOURCE T1
                return new Object[]{12, "Donator points"};
            case 1447://RESOURCE T2
                return new Object[]{25, "Donator points"};
            case 5585://RIFT KEY
                return new Object[]{5, "Donator points"};
            case 15668://BOX OF TREASURES
                return new Object[]{25, "Donator points"};
            case 17129://ELEMENTAL CACHE
                return new Object[]{110, "Donator points"};
            case 7245://RINGS
            case 7247:
            case 7249:
                return new Object[]{200, "Donator points"};
            case 7236://AMULETS
            case 7238:
            case 7241:
                return new Object[]{200, "Donator points"};
            default:
                return new Object[]{99999, "Donator points"};
        }
    }

    @Getter
    public enum DonatorShopType {
        WEAPON(80),
        ARMOUR(200),
        ACCESSORY(201),
        MISC(202);
        private int shopId;

        DonatorShopType(int shopId) {
            this.shopId = shopId;
        }
        
        public static boolean isDonatorStore(int id){
            for (DonatorShopType donatorShopType : values()){
                if ( id == donatorShopType.getShopId()){
                    return true;
                }
            }
            return false;
        }
    }

}
