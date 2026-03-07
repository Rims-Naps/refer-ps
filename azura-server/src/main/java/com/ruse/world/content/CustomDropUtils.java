package com.ruse.world.content;

import com.ruse.donation.DonatorRanks;
import com.ruse.model.container.impl.Equipment;
import com.ruse.world.content.holidays.HolidayManager;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;
import java.util.List;

public class
CustomDropUtils {


    /**
     * Increases Drop Rate
     *
     */
    public static int drBonus(Player player, int npc) {

        int percentBoost = 0;

        List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();

        if (player.getNoob().isActive()) {
            percentBoost += 5;
        }

        if (player.getLuck().isActive()) {
            percentBoost += 2;
        }
        if (player.getDroprateTimer().isActive() || player.getDivine().isActive() ) {
            percentBoost += 3;
        }



        if (player.getVoteBoost().isActive()) {
            percentBoost += 4;
        }

        if (player.getIceBornTimer().isActive()) {
            percentBoost += 3;
        }

        if (player.getEasterTimer().isActive()) {
            percentBoost += 3;
        }

        if (player.getLoveTImer().isActive()) {
            percentBoost += 3;
        }

        if (player.getOvergrown().isActive()) {
            percentBoost += 10;
        }

        if (player.getNodesUnlocked() != null) {
            if (player.getSkillTree().isNodeUnlocked(Node.VERDANT_VITALITY)) {
                percentBoost += 2;
            }
        }



        switch (HolidayManager.getBoostStage()) {
            case 0:
                percentBoost += 2;
                break;
            case 1:
                percentBoost += 4;
                break;
            case 2:
                percentBoost += 6;
                break;
            case 3:
                percentBoost += 8;
                break;
            case 4:
                percentBoost += 10;
                break;
        }

        //LUCK ENERGY(2)
        if (player.getEquipment().contains(2612)) {
            percentBoost += 4;
        }

        //CORRUPT WEAPONS
        if (player.getEquipment().contains(2651)) {
            percentBoost += 8;
        }
        if (player.getEquipment().contains(2653)) {
            percentBoost += 8;
        }
        if (player.getEquipment().contains(2655)) {
            percentBoost += 8;
        }


        //HOLY GRAIL HANDLE - TYLER
        if (player.getEquipment().contains(436) && player.isHolyGrailDroprate()) {
            percentBoost += 5;
        }
        if (player.getEquipment().contains(436) && !player.isHolyGrailDroprate()) {
            percentBoost -= 5;
        }


        //EASTER WEPS
        if (player.getEquipment().contains(711)) {
            percentBoost += 6;
        }
        if (player.getEquipment().contains(712)) {
            percentBoost += 6;
        }
        if (player.getEquipment().contains(713)) {
            percentBoost += 6;
        }
        if (player.getEquipment().contains(714)) {//Void
            percentBoost += 10;
        }

        if (player.getCosmetics().contains(21036)|| player.getEquipment().contains(21036)) {
            percentBoost += 7;//Bunny Mask
        }
        if (player.getCosmetics().contains(719)) {
            percentBoost += 5;//Carrot Pendant
        }


        //COSMETIC SHOP
        //NORMAL TIER
        //BLUE - MAGIC - WATER
        if (player.getCosmetics().contains(15720)) {
            percentBoost += 1;//
        }
        if (player.getCosmetics().contains(15739)) {
            percentBoost += 1;//
        }
        if (player.getCosmetics().contains(15770)) {
            percentBoost += 1;//
        }
        //GREEN - RANGE - EARTH
        if (player.getCosmetics().contains(15724)) {
            percentBoost += 1;//
        }
        if (player.getCosmetics().contains(15740)) {
            percentBoost += 1;//
        }
        if (player.getCosmetics().contains(15774)) {
            percentBoost += 1;//
        }
        //RED - MELEE - FIRE
        if (player.getCosmetics().contains(15721)) {
            percentBoost += 1;//
        }
        if (player.getCosmetics().contains(15743)) {
            percentBoost += 1;//
        }
        if (player.getCosmetics().contains(15771)) {
            percentBoost += 1;//
        }
        //GOLD - DR
        if (player.getCosmetics().contains(15722)) {
            percentBoost += 3;//
        }
        if (player.getCosmetics().contains(15744)) {
            percentBoost += 3;//
        }
        if (player.getCosmetics().contains(15773)) {
            percentBoost += 3;//
        }
        //PURPLE - SLAYER
        if (player.getCosmetics().contains(15723)) {
            percentBoost += 1;//
        }
        if (player.getCosmetics().contains(15772)) {
            percentBoost += 1;//
        }
        //CYAN
        if (player.getCosmetics().contains(15760)) {
            percentBoost += 1;//
        }

        //FABLED TIER
        //BLUE - MAGIC - WATER
        if (player.getCosmetics().contains(15725)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15745)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15778)) {
            percentBoost += 2;//
        }
        //GREEN - RANGE - EARTH
        if (player.getCosmetics().contains(15726)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15746)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15776)) {
            percentBoost += 2;//
        }
        //PINK - MELEE - FIRE
        if (player.getCosmetics().contains(15741)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15747)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15780)) {
            percentBoost += 2;//
        }
        //GOLD - DR
        if (player.getCosmetics().contains(15728)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15749)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15779)) {
            percentBoost += 5;//
        }
        //PURPLE - SLAYER
        if (player.getCosmetics().contains(15727)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15748)) {
            percentBoost += 2;//
        }
        if (player.getCosmetics().contains(15777)) {
            percentBoost += 2;//
        }


        //EXOTIC TIER
        //BLUE - MAGIC - WATER
        if (player.getCosmetics().contains(15729)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15754)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15781)) {
            percentBoost += 4;//
        }
        //GREEN - RANGE - EARTH
        if (player.getCosmetics().contains(15731)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15753)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15782)) {
            percentBoost += 4;//
        }
        //RED - MELEE - FIRE
        if (player.getCosmetics().contains(15742)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15752)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15783)) {
            percentBoost += 4;//
        }
        //GOLD - DR
        if (player.getCosmetics().contains(15730)) {
            percentBoost += 8;//
        }
        if (player.getCosmetics().contains(15750)) {
            percentBoost += 8;//
        }
        //PURPLE - SLAYER
        if (player.getCosmetics().contains(15732)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15751)) {
            percentBoost += 4;//
        }
        if (player.getCosmetics().contains(15784)) {
            percentBoost += 4;//
        }
        //VOID
        if (player.getCosmetics().contains(15785)) {
            percentBoost += 7;//
        }



        //MYTHIC
        //BLUE - MAGIC - WATER
        if (player.getCosmetics().contains(15735)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15755)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15786)) {
            percentBoost += 5;//
        }
        //GREEN - RANGE - EARTH
        if (player.getCosmetics().contains(15736)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15756)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15787)) {
            percentBoost += 5;//
        }
        //RED - MELEE - FIRE
        if (player.getCosmetics().contains(15737)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15757)) {
            percentBoost += 5;//
        }
        if (player.getCosmetics().contains(15788)) {
            percentBoost += 5;//
        }
        //DR
        if (player.getCosmetics().contains(15790)) {
            percentBoost += 10;//
        }
        // SLAYER
        if (player.getCosmetics().contains(15759)) {
            percentBoost += 5;//
        }
        //BEAST
        if (player.getCosmetics().contains(15758)) {
            percentBoost += 5;//
        }
        //CRIT+DR
        if (player.getCosmetics().contains(15791)) {
            percentBoost += 3;//
        }







        //CANDY BOOST
        if (player.getInventory().contains(2626)) {
            percentBoost += 2;
        }

        //SANTA Hat
        if (player.getCosmetics().contains(1455)|| player.getEquipment().contains(1455 )) {
            percentBoost += 2;
        }
        //SANTA Body
        if (player.getCosmetics().contains(1456)|| player.getEquipment().contains(1456 )) {
            percentBoost += 2;
        }
        //SANTA Legs
        if (player.getCosmetics().contains(1457)|| player.getEquipment().contains(1457) ) {
            percentBoost += 2;
        }
        //SANTA Gloves
        if (player.getCosmetics().contains(1458)|| player.getEquipment().contains(1458) ) {
            percentBoost += 2;
        }
        //SANTA Boots
        if (player.getCosmetics().contains(1459)|| player.getEquipment().contains(1459) ) {
            percentBoost += 2;
        }
        //SANTA Book
        if (player.getCosmetics().contains(1466)|| player.getEquipment().contains(1466) ) {
            percentBoost += 3;
        }
        //SANTA CAPE
        if (player.getCosmetics().contains(1460) || player.getEquipment().contains(1460) ) {
            percentBoost += 3;
        }
        //SANTA SCYTHE
        if (player.getCosmetics().contains(1461) || player.getEquipment().contains(1461)) {
            percentBoost += 4;
        }
        //SANTA SNOWBALL
        if (player.getCosmetics().contains(1462)|| player.getEquipment().contains(1462)) {
            percentBoost += 4;
        }
        //SANTA WAND
        if (player.getCosmetics().contains(1463)|| player.getEquipment().contains(1463)) {
            percentBoost += 4;
        }

        //VORPAL AMMO HANDLE
        boolean vorpal_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getVorpalAmmo() > 1 && player.getQuiverMode() == 1;
        boolean sym_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getSymbioteAmmo() > 1 && player.getQuiverMode() == 3;

        if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1428 || vorpal_quiver) {
            percentBoost += 3;
        }

        //SYMBIOTIC AMMO HANDLE
        if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1429 || sym_quiver) {
            percentBoost += 3;
        }

        if (player.getAmountDonated() >= DonatorRanks.COSMIC_AMOUNT) {
            percentBoost += 2;
        }
        if (player.getAmountDonated() >= DonatorRanks.GUARDIAN_AMOUNT) {
            percentBoost += 3;
        }
        if (player.getAmountDonated() >= DonatorRanks.CORRUPT_AMOUNT) {
            percentBoost += 4;
        }

        //OWNER WEPS
        if (player.getEquipment().contains(750)) {
            percentBoost += 15;
        }
        if (player.getEquipment().contains(751)) {
            percentBoost += 15;
        }
        if (player.getEquipment().contains(752)) {
            percentBoost += 15;
        }

        //NAKIO's WAND
        if (player.getCosmetics().contains(10228)) {
            percentBoost += 4;
        }
        //NAKIO's WAND
        if (player.getCosmetics().contains(7266)) {
            percentBoost += 4;
        }

        //OWNER AMULET
        if (player.getEquipment().contains(17122)) {
            percentBoost += 3;
        }

        //OWNER AMULET(U)
        if (player.getEquipment().contains(17124)) {
            percentBoost += 6;
        }

        //OWNER RING
        if (player.getEquipment().contains(17123)) {
            percentBoost += 3;
        }

        //OWNER RING(U)
        if (player.getEquipment().contains(10724)) {
            percentBoost += 6;
        }

        //OWNER Hat
        if (player.getEquipment().contains(15792)) {
            percentBoost += 2;
        }
        if (player.getEquipment().contains(9950) || player.getEquipment().contains(9955) || player.getEquipment().contains(9960)) {
            percentBoost += 3;
        }

        //NATHAN
        if (player.getCosmetics().contains(18940)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(18941)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(18942)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(18943)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(18944)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(18946)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(18948)) {
            percentBoost += 2;
        }


        //AVION
        if (player.getCosmetics().contains(3030)) {
            percentBoost += 10;
        }
        if (player.getCosmetics().contains(3031)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(3032)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(3033)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(3034)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(3035)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(3036)) {
            percentBoost += 2;
        }



        //DILLON
        if (player.getCosmetics().contains(350)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(351)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(352)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(353)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(354)) {
            percentBoost += 2;
        }
        if (player.getCosmetics().contains(355)) {
            percentBoost += 2;
        }

        //OWNER Body
        if (player.getEquipment().contains(15793)) {
            percentBoost += 2;
        }
        if (player.getEquipment().contains(9951) || player.getEquipment().contains(9956) || player.getEquipment().contains(9961)) {
            percentBoost += 3;
        }



        //OWNER Legs
        if (player.getEquipment().contains(15794)) {
            percentBoost += 2;
        }
        if (player.getEquipment().contains(9952) || player.getEquipment().contains(9957) || player.getEquipment().contains(9962)) {
            percentBoost += 3;
        }

        //OWNER Gloves
        if (player.getEquipment().contains(15795)) {
            percentBoost += 2;
        }
        if (player.getEquipment().contains(9953) || player.getEquipment().contains(9958) || player.getEquipment().contains(9963)) {
            percentBoost += 3;
        }

        //OWNER Boots
        if (player.getEquipment().contains(15796)) {
            percentBoost += 2;
        }
        if (player.getEquipment().contains(9954) || player.getEquipment().contains(9959) || player.getEquipment().contains(9964)) {
            percentBoost += 3;
        }

        //OWNER CAPE
        if (player.getEquipment().contains(19944)) {
            percentBoost += 10;
        }
        if (player.getEquipment().contains(3520) || player.getEquipment().contains(3521) || player.getEquipment().contains(3522)) {
            percentBoost += 11;
        }
        //Appreciation Cape
        if (player.getCosmetics().contains(3519)) {
            percentBoost += 1;
        }



        //SKELETAL
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEATHWALKER.npcId) {
            percentBoost += 1;
        }
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ARROWSHADE.npcId) {
            percentBoost += 2;
        }
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BONEMANCER.npcId) {
            percentBoost += 3;
        }

        //DEMONIC
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SHADOWFIEND.npcId) {
            percentBoost += 4;
        }
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEVILSPAWN.npcId) {
            percentBoost += 5;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ABYSSAL_TORMENTOR.npcId) {
            percentBoost += 5;
        }


        //OGRE
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.GRUNT_MAULER.npcId) {
            percentBoost += 6;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BRUTE_CRUSHER.npcId) {
            percentBoost += 6;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.VINESPLITTER.npcId) {
            percentBoost += 7;
        }



        //SPECTRAL
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.PHANTOM_DRIFTER.npcId) {
            percentBoost += 8;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.WHISPERING_WRAITH.npcId) {
            percentBoost += 9;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BANSHEE_KING.npcId) {
            percentBoost += 10;
        }


        //MASTER
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.EYE_OF_BEYOND.npcId) {
            percentBoost += 11;
        }
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.TALONWING.npcId) {
            percentBoost += 12;
        }
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.UMBRAL_BEAST.npcId) {
            percentBoost += 13;
        }

        if (player.getMembershipTier().getDroprate() > 0)
            percentBoost += player.getMembershipTier().getDroprate();

        //GAIA CRYSTAL TIERS
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12839) {
            percentBoost += 1;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17003) {
            percentBoost += 2;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17004) {
            percentBoost += 3;
        }

        //VOID CRYSTAL TIERS
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12842) {
            percentBoost += 3;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17009) {
            percentBoost += 4;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17010) {
            percentBoost += 5;
        }


        //CORRUPT CRYSTAL TIERS
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 763) {
            percentBoost += 4;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 764) {
            percentBoost += 5;
        }
        if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 765) {
            percentBoost += 6;
        }

        if (CurseHandler.isActivated(player, CurseHandler.GEOVIGOR)) {
            percentBoost += 2;
        }

        if (CurseHandler.isActivated(player, CurseHandler.MALEVOLENCE)) {
            percentBoost += 6;
        }

        if (CurseHandler.isActivated(player, CurseHandler.STONEHAVEN)) {
            percentBoost += 4;
        }

        percentBoost += player.getCompanion().baseDrBoost();

        //ENCHANT WEP 1
        if (player.getEquipment().contains(16415)) {
            percentBoost += 1;
        }
        //ENCHANT WEP 2
        if (player.getEquipment().contains(16416)) {
            percentBoost += 2;
        }
        //ENCHANT WEP 3
        if (player.getEquipment().contains(16417)) {
            percentBoost += 3;
        }
        //ENCHANT WEP 4
        if (player.getEquipment().contains(16418)) {
            percentBoost += 4;
        }
        //ENCHANT WEP 5
        if (player.getEquipment().contains(16419)) {
            percentBoost += 5;
        }
        //ENCHANT WEP 6
        if (player.getEquipment().contains(16420)) {
            percentBoost += 6;
        }
        //ENCHANT WEP 7
        if (player.getEquipment().contains(16421)) {
            percentBoost += 7;
        }

        //ELITE WEP 1
        if (player.getEquipment().contains(17101)) {
            percentBoost += 7;
        }
        //ELITE WEP 2
        if (player.getEquipment().contains(17102)) {
            percentBoost += 8;
        }
        //ELITE WEP 3
        if (player.getEquipment().contains(17103)) {
            percentBoost += 9;
        }

        //ELITE WEP 4
        if (player.getEquipment().contains(17104)) {
            percentBoost += 10;
        }
        //ELITE WEP 5
        if (player.getEquipment().contains(17105)) {
            percentBoost += 11;
        }
        //ELITE WEP 6
        if (player.getEquipment().contains(17106)) {
            percentBoost += 12;
        }

        //Spectral WEP 1
        if (player.getEquipment().contains(2086)) {
            percentBoost += 13;
        }
        //Spectral WEP 2
        if (player.getEquipment().contains(2087)) {
            percentBoost += 14;
        }
        //Spectral WEP 3
        if (player.getEquipment().contains(2088)) {
            percentBoost += 15;
        }
        if (player.getEquipment().contains(23188)) {
            percentBoost += 15;
        }

        //Spectral Quiver
        if (player.getEquipment().contains(2088)) {
            percentBoost += 4;
        }
        if (player.getEquipment().contains(23188)) {
            percentBoost += 4;
        }

        if (player.fullMysticTier1()){
            percentBoost += 1;
        }
        if (player.fullMysticTier2()){
            percentBoost += 2;
        }
        if (player.fullMysticTier3()){
            percentBoost += 3;
        }
        if (player.fullMysticTier4()){
            percentBoost += 4;
        }
        if (player.fullMysticTier5()){
            percentBoost += 5;
        }
        if (player.fullMysticTier6()){
            percentBoost += 6;
        }
        if (player.fullMysticTier7()){
            percentBoost += 7;
        }
        if (player.fullEliteTier1()){
            percentBoost += 8;
        }
        if (player.fullEliteTier2()){
            percentBoost += 9;
        }
        if (player.fullEliteTier3()){
            percentBoost += 10;
        }
        if (player.fullFallen()){
            percentBoost += 10;
        }
        if (player.fullEliteTier4()){
            percentBoost += 11;
        }
        if (player.fullEliteTier5()){
            percentBoost += 12;
        }
        if (player.fullEliteTier6()){
            percentBoost += 13;
        }
        if (player.fullSpectralTier1()){
            percentBoost += 14;
        }
        if (player.fullSpectralTier2()){
            percentBoost += 15;
        }
        if (player.fullSpectralTier3()){
            percentBoost += 16;
        }


        if (player.getCosmetics().contains(3000)) {
            percentBoost += 3;
        }
        if (player.getCosmetics().contains(3001)) {
            percentBoost += 3;
        }
        if (player.getCosmetics().contains(3002)) {
            percentBoost += 3;
        }
        if (player.getCosmetics().contains(3003)) {
            percentBoost += 3;
        }
        if (player.getCosmetics().contains(3004)) {
            percentBoost += 3;
        }
        if (player.getCosmetics().contains(3005)) {
            percentBoost += 15;
        }





        //TIER 1 RINGS - F-W-E
        if (player.getEquipment().contains(7247) ||  player.getEquipment().contains(7249) || player.getEquipment().contains(7245)) {
            percentBoost += 3;
        }

        //VOID RING 1
        if (player.getEquipment().contains(1531)) {
            percentBoost += 5;
        }


        //TIER 1 AMULETS - F-W-E
        if (player.getEquipment().contains(7238) ||  player.getEquipment().contains(7241) || player.getEquipment().contains(7236)) {
            percentBoost += 3;
        }

        //VOID AMULET 1
        if (player.getEquipment().contains(1528)) {
            percentBoost += 5;
        }



        //CORRUPT AMULET
        if (player.getEquipment().contains(2683)) {
            percentBoost += 8;
        }
        //DIVINE AMULET
        if (player.getEquipment().contains(2068)) {
            percentBoost += 16;
        }
        //BARBARIC AMULET
        if (player.getEquipment().contains(2069)) {
            percentBoost += 8;
        }
        //FROZEN AMULET
        if (player.getEquipment().contains(2070)) {
            percentBoost += 8;
        }
        //SPECTRAL AMULET
        if (player.getEquipment().contains(2071)) {
            percentBoost += 12;
        }

        //CORRUPT RING
        if (player.getEquipment().contains(2680)) {
            percentBoost += 8;
        }
        //DIVINE RING
        if (player.getEquipment().contains(2075)) {
            percentBoost += 16;
        }
        //BARBARIC RING
        if (player.getEquipment().contains(2076)) {
            percentBoost += 8;
        }
        //FROZEN RING
        if (player.getEquipment().contains(2077)) {
            percentBoost += 8;
        }
        //SPECTRAL RING
        if (player.getEquipment().contains(2078)) {
            percentBoost += 12;
        }






        //TIER 1 SLAYER HELMETS - F-W-E
        if (player.getEquipment().contains(22000) ||  player.getEquipment().contains(22001) || player.getEquipment().contains(22002)) {
            percentBoost += 2;
        }
        //TIER 2 SLAYER HELMETS - F-W-E
        if (player.getEquipment().contains(12460) ||  player.getEquipment().contains(12462) || player.getEquipment().contains(12464)) {
            percentBoost += 3;
        }
        //TIER 3 SLAYER HELMETS - F-W-E
        if (player.getEquipment().contains(12461) ||  player.getEquipment().contains(12463) || player.getEquipment().contains(12465)) {
            percentBoost += 4;
        }


        //TIER 1 VOID HELMET
        if (player.getEquipment().contains(22003)) {
            percentBoost += 4;
        }
        //TIER 2 VOID HELMET
        if (player.getEquipment().contains(12466)) {
            percentBoost += 5;
        }
        //TIER 3 VOID HELMET
        if (player.getEquipment().contains(12467)) {
            percentBoost += 6;
        }


        //TIER 1 CORRUPT HELMET
        if (player.getEquipment().contains(2677)) {
            percentBoost += 7;
        }
        //TIER 2 CORRUPT HELMET
        if (player.getEquipment().contains(2678)) {
            percentBoost += 8;
        }
        //TIER 3 CORRUPT HELMET
        if (player.getEquipment().contains(2679)) {
            percentBoost += 9;
        }

        //AQUATIC HELM
        if (player.getEquipment().contains(3010)) {
            percentBoost += 10;
        }
        //MAGMA HELM
        if (player.getEquipment().contains(3011)) {
            percentBoost += 10;
        }
        //OVERGROWN HELM
        if (player.getEquipment().contains(3012)) {
            percentBoost += 10;
        }
        //SPECTRAL HELM(1)
        if (player.getEquipment().contains(3013)) {
            percentBoost += 15;
        }
        //OVERGROWN HELM(2)
        if (player.getEquipment().contains(3014)) {
            percentBoost += 20;
        }
        //OVERGROWN HELM(3)
        if (player.getEquipment().contains(3015)) {
            percentBoost += 25;
        }


        //HOLY Hat
        if (player.getEquipment().contains(3020)) {
            percentBoost += 10;
        }
        //HOLY Body
        if (player.getEquipment().contains(3021)) {
            percentBoost += 10;
        }
        //HOLY Legs
        if (player.getEquipment().contains(3022)) {
            percentBoost += 10;
        }
        //HOLY Gloves
        if (player.getEquipment().contains(3023)) {
            percentBoost += 10;
        }
        //HOLY Boots
        if (player.getEquipment().contains(3024)) {
            percentBoost += 10;
        }

        if (player.getPosition().getRegionId() == 1488 || player.getPosition().getRegionId() == 11343) {
            percentBoost *= 3;
        }




        if (player.isClaimedPermDroprate()) {
            percentBoost *= 1.25;
        }

        if (PerkManager.currentPerk != null) {
            if (PerkManager.currentPerk.getName().equalsIgnoreCase("Droprate")) {
                percentBoost += 5;
            }
        }

        
        //DROPRATE SALT
        if (player.getInventory().contains(23119) || player.getInventory().contains(357)) {
            percentBoost += 4;
        }


        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            percentBoost += 2;
        }
        if (CurseHandler.isActivated(player, CurseHandler.CINDERSTOUCH)) {
            percentBoost += 2;
        }
        if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW)) {
            percentBoost += 2;
        }
        if (player.getDrBoost() > 0) {
            percentBoost += player.getDrBoost();
        }
        if (activePerks.contains(ServerPerks.Perk.DROP_RATE)) {
            percentBoost *= 1.10;
        }

        if (player.getPosition().getRegionId() == 10027){
            percentBoost *= 2;
        }

        if (player.getPosition().getRegionId() == 10535){
            percentBoost = 25;
        }

        if (percentBoost < 0){
            percentBoost = 0;
        }

        return percentBoost;

    }

}
