package com.ruse.world.content;

import com.ruse.donation.DonatorRanks;
import com.ruse.model.container.impl.Shop;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.skill.impl.summoning.BossPets.BossPet;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

public class CosmeticShop {

    public static final int ITEM_CHILD_ID = 33901;
    public static final int ITEM_CHILD_ID_CLICK = -31635;
    public static final int INTERFACE_ID = 119000;

    private Player player;

    public CosmeticShop(Player player) {
        this.player = player;
    }

    public static Object[] getPrice(int item) {
        switch (item) {
            //HATS
            case 15720:
            case 15721:
            case 15722:
            case 15723:
            case 15724:
            case 15725:
            case 15726:
            case 15727:
            case 15728:
            case 15729:
            case 15730:
            case 15731:
            case 15732:
            case 15738:
            case 15741:
            case 15742:

           //MASKS
            case 15739:
            case 15740:
            case 15743:
            case 15744:
            case 15760:
            case 15745:
            case 15746:
            case 15747:
            case 15748:
            case 15749:
            case 15750:
            case 15751:
            case 15752:
            case 15753:
            case 15754:

                //SANTAS
            case 15770:
            case 15771:
            case 15772:
            case 15773:
            case 15774:
            case 15776:
            case 15777:
            case 15778:
            case 15779:
            case 15780:
            case 15781:
            case 15782:
            case 15783:
            case 15784:
            case 15785:
                return new Object[]{1, "Cosmetic Ticket"};

            //MYTHIC SHOP
            case 15735:
            case 15736:
            case 15737:
            case 15733:
            case 15734:
            case 15755:
            case 15756:
            case 15757:
            case 15758:
            case 15759:
            case 15786:
            case 15787:
            case 15788:
            case 15790:
            case 15791:
                return new Object[]{2, "Cosmetic Ticket"};
        }
        return new Object[]{9999, "Cosmetic Ticket"};
    }

    public boolean handleButton(int buttonId) {

        switch (buttonId) {
            case 119005:
                openInterface(CosmeticShopTier.ORDINARY);
                return true;
            case 119006:
                openInterface(CosmeticShopTier.FABLED);
                return true;
            case 119007:
                openInterface(CosmeticShopTier.EXOTIC);
                return true;
            case 119008:
                openInterface(CosmeticShopTier.MYTHIC);
                return true;
        }

        return false;
    }

    public void openInterface(CosmeticShopTier type) {
        player.getPacketSender().sendConfig(5334, type.ordinal());
        Shop.ShopManager.getShops().get(type.getShopId()).open(player);
    }

    public boolean hasRequirements(int id) {
        switch (id) {
            case 203:
                if(player.getRights().isManagement())
                    return true;

                return true;
            case 204:
                if(player.getRights().isManagement())
                    return true;

                if (player.getAmountDonated() < DonatorRanks.ARCHON_AMOUNT) {
                    player.sendMessage("You need to reach $500 Rank(Archon) to access this shop.");
                    return false;
                }
            return true;
            case 205:
                if(player.getRights().isManagement())
                    return true;

                if (player.getAmountDonated() < DonatorRanks.ASCENDANT_AMOUNT) {
                    player.sendMessage("You need to reach $1500 Rank(Ascendant) to access this shop.");
                    return false;
                }
                return true;
            case 206:
                if(player.getRights().isManagement())
                    return true;

                if (player.getAmountDonated() < DonatorRanks.COSMIC_AMOUNT) {
                    player.sendMessage("You need to reach $5000 Rank(Cosmic) to access this shop.");
                    return false;
                }
                return true;
        }
        return false;
    }

    @Getter
    public enum CosmeticShopTier {
        ORDINARY(203),
        FABLED(204),
        EXOTIC(205),
        MYTHIC(206);
        private int shopId;

        CosmeticShopTier(int shopId) {
            this.shopId = shopId;
        }

        public static boolean isCosmeticShop(int id) {
            for (CosmeticShopTier cosmeticShopTier : values()) {
                if (id == cosmeticShopTier.getShopId()) {
                    return true;
                }
            }
            return false;
        }
    }


    @Getter
    public enum PetRestrictions {
        ;

        private BossPet pet;
        private BossPet[] required;

        PetRestrictions(BossPet pet, BossPet... required) {
            this.pet = pet;
            this.required = required;
        }

        public static BossPet[] getRequired(int id) {
            BossPet pet = BossPet.forId(id);
            for (PetRestrictions data : values()) {
                if (pet == data.getPet()) {
                    return data.getRequired();
                }
            }
            return null;
        }
    }

}
