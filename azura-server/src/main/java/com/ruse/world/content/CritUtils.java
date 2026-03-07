package com.ruse.world.content;

import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.entity.impl.player.Player;

import java.util.List;

public class CritUtils {

    public static int criticalbonus(Player player) {
        int  critboost = player.getCritchance();

        if (player.getCritchance() > 0) {
            critboost += player.getCritchance();
        }

        if (player.getNoob().isActive()) {
            critboost += 5;
        }

        //CANDY BOOST
        if (player.getInventory().contains(2626)) {
            critboost += 2;
        }
        if (player.fullFallen()){
            critboost += 4;
        }

        //PINK - MELEE - FIRE
        if (player.getCosmetics().contains(15741)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15747)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15780)) {
            critboost += 2;
        }
        //RED - MELEE - FIRE
        if (player.getCosmetics().contains(15742)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15752)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15783)) {
            critboost += 3;
        }
        //RED - MELEE - FIRE
        if (player.getCosmetics().contains(15737)) {
            critboost += 4;
        }
        if (player.getCosmetics().contains(15757)) {
            critboost += 4;
        }
        if (player.getCosmetics().contains(15788)) {
            critboost += 4;
        }

        //BLUE - MAGIC - WATER
        if (player.getCosmetics().contains(15725)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15745)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15778)) {
            critboost += 2;
        }
        //BLUE - MAGIC - WATER
        if (player.getCosmetics().contains(15729)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15754)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15781)) {
            critboost += 3;
        }
        //BLUE - MAGIC - WATER
        if (player.getCosmetics().contains(15735)) {
            critboost += 4;
        }
        if (player.getCosmetics().contains(15755)) {
            critboost += 4;
        }
        if (player.getCosmetics().contains(15786)) {
            critboost += 4;
        }


        //GREEN - RANGE - EARTH
        if (player.getCosmetics().contains(15726)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15746)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15776)) {
            critboost += 2;
        }
        //GREEN - RANGE - EARTH
        if (player.getCosmetics().contains(15731)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15753)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15782)) {
            critboost += 3;
        }

        //GREEN - RANGE - EARTH
        if (player.getCosmetics().contains(15736)) {
            critboost += 4;
        }
        if (player.getCosmetics().contains(15756)) {
            critboost += 4;
        }
        if (player.getCosmetics().contains(15787)) {
            critboost += 4;
        }


        //PURPLE - SLAYER
        if (player.getCosmetics().contains(15727)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15748)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(15777)) {
            critboost += 2;
        }
        //PURPLE - SLAYER
        if (player.getCosmetics().contains(15732)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15751)) {
            critboost += 3;
        }
        if (player.getCosmetics().contains(15784)) {
            critboost += 3;
        }
        //VOID COSMETIC
        if (player.getCosmetics().contains(15785)) {
            critboost += 3;
        }
        //MYTHIC COSMETIC - Slayer
        if (player.getCosmetics().contains(15759)) {
            critboost += 5;
        }
        //MYTHIC COSMETIC - Beast
        if (player.getCosmetics().contains(15758)) {
            critboost += 5;
        }
        //MYTHIC COSMETIC - Main Crit
        if (player.getCosmetics().contains(15791)) {
            critboost += 8;
        }





        boolean vorpal_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getVorpalAmmo() > 1 && player.getQuiverMode() == 1;
        //VORPAL AMMO HANDLE
        if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1428 || vorpal_quiver ) {
            critboost += 3;
        }
        //OWNER AMULET
        if (player.getEquipment().contains(17122)) {
            critboost += 4;
        }

            //OWNER AMULET(U)
        if (player.getEquipment().contains(17124)) {
            critboost += 6;
        }

        //OWNER RING
        if (player.getEquipment().contains(17123)) {
            critboost += 4;
        }

        //OWNER RING(U)
        if (player.getEquipment().contains(10724)) {
            critboost += 6;
        }

        //OWNER WEPS
        if (player.getEquipment().contains(750)) {
            critboost += 3;
        }
        if (player.getEquipment().contains(751)) {
            critboost += 3;
        }
        if (player.getEquipment().contains(752)) {
            critboost += 3;
        }

        //NAKIO's WAND
        if (player.getCosmetics().contains(10228)) {
            critboost += 2;
        }
        //NAKIO's WAND
        if (player.getCosmetics().contains(7266)) {
            critboost += 2;
        }

        //NATHAN
        if (player.getCosmetics().contains(18940)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(18941)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(18942)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(18943)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(18944)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(18946)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(18948)) {
            critboost += 2;
        }


        //AVION
        if (player.getCosmetics().contains(3030)) {
            critboost += 10;
        }
        if (player.getCosmetics().contains(3031)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(3032)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(3033)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(3034)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(3035)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(3036)) {
            critboost += 2;
        }


        //DILLON
        if (player.getCosmetics().contains(350)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(351)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(352)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(353)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(354)) {
            critboost += 2;
        }
        if (player.getCosmetics().contains(355)) {
            critboost += 2;
        }

        //HOLY Hat
        if (player.getEquipment().contains(3020)) {
            critboost += 10;
        }
        //HOLY Body
        if (player.getEquipment().contains(3021)) {
            critboost += 10;
        }
        //HOLY Legs
        if (player.getEquipment().contains(3022)) {
            critboost += 10;
        }
        //HOLY Gloves
        if (player.getEquipment().contains(3023)) {
            critboost += 10;
        }
        //HOLY Boots
        if (player.getEquipment().contains(3024)) {
            critboost += 10;
        }

        //OWNER Hat
        if (player.getEquipment().contains(15792)) {
            critboost += 2;
        }
        if (player.getEquipment().contains(9950) || player.getEquipment().contains(9955) || player.getEquipment().contains(9960)) {
            critboost += 3;
        }
        //OWNER Body
        if (player.getEquipment().contains(15793)) {
            critboost += 2;
        }
        if (player.getEquipment().contains(9951) || player.getEquipment().contains(9956) || player.getEquipment().contains(9961)) {
            critboost += 3;
        }
        //OWNER Legs
        if (player.getEquipment().contains(15794)) {
            critboost += 2;
        }
        if (player.getEquipment().contains(9952) || player.getEquipment().contains(9957) || player.getEquipment().contains(9962)) {
            critboost += 3;
        }
        //OWNER Gloves
        if (player.getEquipment().contains(15795)) {
            critboost += 2;
        }
        if (player.getEquipment().contains(9953) || player.getEquipment().contains(9958) || player.getEquipment().contains(9963)) {
            critboost += 3;
        }

        //OWNER Boots
        if (player.getEquipment().contains(15796)) {
            critboost += 2;
        }
        if (player.getEquipment().contains(9954) || player.getEquipment().contains(9959) || player.getEquipment().contains(9964)) {
            critboost += 3;
        }



        //OWNER Cape
        if (player.getEquipment().contains(19944)) {
            critboost += 5;
        }
        //Appreciation Cape
        if (player.getCosmetics().contains(3519)) {
            critboost += 1;
        }

        if (player.getEquipment().contains(3520) || player.getEquipment().contains(3521) || player.getEquipment().contains(3522)) {
            critboost += 6;
        }

        if (player.getCritTimer().isActive() || player.getDivine().isActive()) {
            critboost += 2;
        }


        if (player.getVoteBoost().isActive()) {
            critboost += 2;
        }

        if (player.getIceBornTimer().isActive()) {
            critboost += 2;
        }
        if (player.getEasterTimer().isActive()) {
            critboost += 2;
        }
        if (player.getNautic().isActive()) {
            critboost += 10;
        }

        if (player.getMembershipTier().getCrit_chance() > 0)
            critboost += player.getMembershipTier().getCrit_chance();

        if (CurseHandler.isActivated(player, CurseHandler.SEASLICER)) {
            critboost += 2;
        }
        if (CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE)) {
            critboost += 4;
        }

        if (CurseHandler.isActivated(player, CurseHandler.MALEVOLENCE)) {
            critboost += 6;
        }

        //AQUA CRYSTAL TIERS
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12841) {
            critboost += 1;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17007) {
            critboost += 2;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17008) {
            critboost += 3;
        }

        //VOID CRYSTAL TIERS
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12842) {
            critboost += 3;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17009) {
            critboost += 4;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17010) {
            critboost += 5;
        }

        //CORRUPT CRYSTAL TIERS
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 763) {
            critboost += 4;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 764) {
            critboost += 5;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 765) {
            critboost += 6;
        }

        //TIER 1 RINGS - F-W-E
        if (player.getEquipment().contains(7247) ||  player.getEquipment().contains(7249) || player.getEquipment().contains(7245)) {
            critboost += 1;
        }

        //VOID RING 1
        if (player.getEquipment().contains(1531)) {
            critboost += 2;
        }


        //TIER 1 AMULETS - F-W-E
        if (player.getEquipment().contains(7238) ||  player.getEquipment().contains(7241) || player.getEquipment().contains(7236)) {
            critboost += 1;
        }
        //VOID AMULET 1
        if (player.getEquipment().contains(1528)) {
            critboost += 2;
        }



        //CORRUPT AMULET 1
        if (player.getEquipment().contains(2683)) {
            critboost += 4;
        }
        //CORRUPT AMULET 2
        if (player.getEquipment().contains(2684)) {
            critboost += 5;
        }
        //CORRUPT AMULET 3
        if (player.getEquipment().contains(2685)) {
            critboost += 6;
        }

        //CORRUPT RING 1
        if (player.getEquipment().contains(2680)) {
            critboost += 4;
        }

        //DIVINE RING
        if (player.getEquipment().contains(2075)) {
            critboost += 4;
        }
        //BARBARIC RING
        if (player.getEquipment().contains(2076)) {
            critboost += 4;
        }
        //FROZEN RING
        if (player.getEquipment().contains(2077)) {
            critboost += 8;
        }
        //SPECTRAL RING
        if (player.getEquipment().contains(2078)) {
            critboost += 6;
        }

        //DIVINE AMULET
        if (player.getEquipment().contains(2068)) {
            critboost += 4;
        }
        //BARBARIC AMULET
        if (player.getEquipment().contains(2069)) {
            critboost += 4;
        }
        //FROZEN AMULET
        if (player.getEquipment().contains(2070)) {
            critboost += 8;
        }
        //SPECTRAL AMULET
        if (player.getEquipment().contains(2071)) {
            critboost += 6;
        }




        //TIER 1 SLAYER HELMETS - F-W-E
        if (player.getEquipment().contains(22000) ||  player.getEquipment().contains(22001) || player.getEquipment().contains(22002)) {
            critboost += 1;
        }
        //TIER 2 SLAYER HELMETS - F-W-E
        if (player.getEquipment().contains(12460) ||  player.getEquipment().contains(12462) || player.getEquipment().contains(12464)) {
            critboost += 2;
        }
        //TIER 3 SLAYER HELMETS - F-W-E
        if (player.getEquipment().contains(12461) ||  player.getEquipment().contains(12463) || player.getEquipment().contains(12465)) {
            critboost += 2;
        }
        //TIER 1 VOID HELMET
        if (player.getEquipment().contains(22003)) {
            critboost += 2;
        }
        //TIER 2 VOID HELMET
        if (player.getEquipment().contains(12466)) {
            critboost += 2;
        }
        //TIER 3 VOID HELMET
        if (player.getEquipment().contains(12467)) {
            critboost += 2;
        }


        //TIER 1 CORRUPT HELMET
        if (player.getEquipment().contains(2677)) {
            critboost += 3;
        }
        //TIER 2 CORRUPT HELMET
        if (player.getEquipment().contains(2678)) {
            critboost += 4;
        }
        //TIER 3 CORRUPT HELMET
        if (player.getEquipment().contains(2679)) {
            critboost += 5;
        }


        //AQUATIC HELM
        if (player.getEquipment().contains(3010)) {
            critboost += 7;
        }
        //MAGMA HELM
        if (player.getEquipment().contains(3011)) {
            critboost += 7;
        }
        //OVERGROWN HELM
        if (player.getEquipment().contains(3012)) {
            critboost += 7;
        }
        //SPECTRAL HELM(1)
        if (player.getEquipment().contains(3013)) {
            critboost += 10;
        }
        //OVERGROWN HELM(2)
        if (player.getEquipment().contains(3014)) {
            critboost += 12;
        }
        //OVERGROWN HELM(3)
        if (player.getEquipment().contains(3015)) {
            critboost += 15;
        }

        if (PerkManager.currentPerk != null) {
            if (PerkManager.currentPerk.getName().equalsIgnoreCase("Critical")) {
                critboost += 10;
            }
        }


        //DIVINE SALT
        if (player.getInventory().contains(23122) || player.getInventory().contains(357) ) {
            critboost += 4;
        }




        return critboost;
    }
}
