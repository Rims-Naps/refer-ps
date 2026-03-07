package com.ruse.world.content.combat;

import com.ruse.model.*;
import com.ruse.model.container.impl.Equipment;
import com.ruse.world.content.holidays.HolidayManager;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.weapon.FightStyle;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.DropUtils;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.skill.impl.summoning.Familiar;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.List;

public class Maxhits {

    public static int melee(Character entity, Character victim) {
        double maxHit = 0;
        List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();

        //START MELEE
        if (entity.isNpc()) {
            NPC npc = (NPC) entity;
            maxHit = npc.getDefinition().getMaxHit();
            if (npc.getStrengthWeakened()[0]) {
                maxHit -= (int) (0.10 * maxHit);
            } else if (npc.getStrengthWeakened()[1]) {
                maxHit -= (int) (0.20 * maxHit);
            } else if (npc.getStrengthWeakened()[2]) {
                maxHit -= (int) (0.30 * maxHit);
            }
        } else if (entity.isPlayer()) {
            Player player = (Player) entity;

            double base = 0;
            double effective = getEffectiveStr(player);
            double strengthBonus = player.getBonusManager().getOtherBonus()[0];
            double specialBonus = 1;


            // Use our multipliers to adjust the maxhit...

            base = 1.6 + effective / 10 + strengthBonus / 80 + effective * strengthBonus / 640;

            // Special effects also affect maxhit
           /* if (player.isSpecialActivated() && player.getCombatSpecial().getCombatType() == CombatType.MELEE) {
                specialBonus = player.getCombatSpecial().getStrengthBonus();
            }*/

            if (specialBonus > 1) {
                base = Math.round(base) * specialBonus;
            } else {
                base = (int) base;
            }

            Familiar playerFamiliar = player.getSummoning().getFamiliar();

            if (playerFamiliar != null) {
                double bonus = DropUtils.getDamageBonus(playerFamiliar.getSummonNpc().getId());
                base *= bonus;
            }
            double modifier = 1.0;
            maxHit = base * 3;
            modifier += player.getCompanion().baseDmgBoost()/10;


            if (player.getNoob().isActive()) {
                modifier += .5;
            }
            if (player.getDamageTimer().isActive() || player.getDivine().isActive()) {
                modifier += .07;
            }
            if (player.getVoteBoost().isActive()) {
                modifier += .05;
            }
            if (player.getIceBornTimer().isActive()) {
                modifier += .07;
            }
            if (player.getEasterTimer().isActive()) {
                modifier += .07;
            }
            if (player.getLoveTImer().isActive()) {
                modifier += .07;
            }
            if (player.getBlitz().isActive()) {
                modifier += .05;
            }
            if (player.getMagma().isActive()) {
                modifier += .10;
            }

            if (player.isClaimedPermDamage()) {
                modifier += .50;
            }

            //BLOODLUST AXE
            if (player.getPosition().getRegionId() == 13365 || player.getPosition().getRegionId() == 13364 || player.getPosition().getRegionId() == 13363){
                if (player.getEquipment().contains(441) || player.getEquipment().contains(450) || player.getEquipment().contains(451)){
                    modifier += .2;
                }
            }


            //SALT
            if (player.getInventory().contains(23121) || player.getInventory().contains(357)) {
                modifier += .04;
            }

            List<Integer> holidayNpcIds = Arrays.asList(1051, 1052, 1053, 1054, 1055, 1006, 1057, 1058, 1059);



                if (victim.isNpc()) {
                int npcId = ((NPC) victim).getId();

                    if (npcId == 2021 || npcId == 2025) {//WATER PRAYERS VS FIRE ELEMENTAL
                        if (player.getCurseActive()[CurseHandler.EBBFLOW] || player.getCurseActive()[CurseHandler.AQUASTRIKE] || player.getCurseActive()[CurseHandler.RIPTIDE] || player.getCurseActive()[CurseHandler.SEASLICER] || player.getCurseActive()[CurseHandler.SWIFTTIDE]) {
                            modifier += .10;
                        }
                    }

                    if (npcId == 2022 || npcId == 2024) {//EARTH PRAYERS VS WATER ELEMENTAL
                        if (player.getCurseActive()[CurseHandler.GAIABLESSING] || player.getCurseActive()[CurseHandler.SERENITY] || player.getCurseActive()[CurseHandler.ROCKHEART] || player.getCurseActive()[CurseHandler.GEOVIGOR] || player.getCurseActive()[CurseHandler.STONEHAVEN]) {
                            modifier += .10;
                        }
                    }

                    if (npcId == 2019 || npcId == 2023) {//FIRE PRAYERS VS EARTH ELEMENTAL
                        if (player.getCurseActive()[CurseHandler.CINDERSTOUCH] || player.getCurseActive()[CurseHandler.EMBERBLAST] || player.getCurseActive()[CurseHandler.INFERNIFY] || player.getCurseActive()[CurseHandler.BLAZEUP] || player.getCurseActive()[CurseHandler.INFERNO]) {
                            modifier += .10;
                        }
                    }

                if (holidayNpcIds.contains(npcId)) {

                    //SANTA Hat
                    if (player.getCosmetics().contains(1455)|| player.getEquipment().contains(1455 )) {
                        modifier += .06;
                    }
                    //SANTA Body
                    if (player.getCosmetics().contains(1456)|| player.getEquipment().contains(1456 )) {
                        modifier += .06;
                    }
                    //SANTA Legs
                    if (player.getCosmetics().contains(1457)|| player.getEquipment().contains(1457) ) {
                        modifier += .06;
                    }
                    //SANTA Gloves
                    if (player.getCosmetics().contains(1458)|| player.getEquipment().contains(1458) ) {
                        modifier += .06;
                    }
                    //SANTA Boots
                    if (player.getCosmetics().contains(1459)|| player.getEquipment().contains(1459) ) {
                        modifier += .06;
                    }
                    //SANTA Book
                    if (player.getCosmetics().contains(1466)|| player.getEquipment().contains(1466) ) {
                        modifier += .06;
                    }
                    //SANTA CAPE
                    if (player.getCosmetics().contains(1460) || player.getEquipment().contains(1460) ) {
                        modifier += .08;
                    }
                    //SANTA SCYTHE
                    if (player.getEquipment().contains(1461) || player.getEquipment().contains(1461)) {
                        modifier += .1;
                    }
                    //SANTA SNOWBALL
                    if (player.getEquipment().contains(1462)|| player.getEquipment().contains(1462)) {
                        modifier += .1;
                    }
                    //SANTA WAND
                    if (player.getEquipment().contains(1463)|| player.getEquipment().contains(1463)) {
                        modifier += .1;
                    }
                }
            }

            switch (HolidayManager.getBoostStage()) {
                case 0:
                    modifier += .02;
                    break;
                case 1:
                    modifier += .04;
                    break;
                case 2:
                    modifier += .06;
                    break;
                case 3:
                    modifier += .08;
                    break;
                case 4:
                    modifier += .10;
                    break;
            }

            //HOLY GRAIL HANDLE - TYLER
            if (player.getEquipment().contains(436) && !player.isHolyGrailDroprate()) {
                modifier += .05;
            }
            if (player.getEquipment().contains(436) && player.isHolyGrailDroprate()) {
                modifier -= .05;
            }


            //RING OF HADES
            if (player.getEquipment().contains(7247)) {
                modifier += .02;
            }

            //AMULET OF HADES
            if (player.getEquipment().contains(7238)) {
                modifier += .02;
            }


            //RED - MELEE - FIRE
            if (player.getCosmetics().contains(15721)) {
                modifier += .02;
            }
            if (player.getCosmetics().contains(15743)) {
                modifier += .02;
            }
            if (player.getCosmetics().contains(15771)) {
                modifier += .02;
            }
            //PINK - MELEE - FIRE
            if (player.getCosmetics().contains(15741)) {
                modifier += .04;
            }
            if (player.getCosmetics().contains(15747)) {
                modifier += .04;
            }
            if (player.getCosmetics().contains(15780)) {
                modifier += .04;
            }
            //RED - MELEE - FIRE
            if (player.getCosmetics().contains(15742)) {
                modifier += .06;
            }
            if (player.getCosmetics().contains(15752)) {
                modifier += .06;
            }
            if (player.getCosmetics().contains(15783)) {
                modifier += .06;
            }
            //RED - MELEE - FIRE
            if (player.getCosmetics().contains(15737)) {
                modifier += .10;
            }
            if (player.getCosmetics().contains(15757)) {
                modifier += .10;
            }
            if (player.getCosmetics().contains(15788)) {
                modifier += .10;
            }
            //OWNER WEPS
            if (player.getEquipment().contains(750)) {
                modifier += .04;
            }
            if (player.getEquipment().contains(751)) {
                modifier += .04;
            }
            if (player.getEquipment().contains(752)) {
                modifier += .04;
            }

            //OWNER Hat
            if (player.getEquipment().contains(15792)) {
                modifier += .02;
            }
            //OWNER Body
            if (player.getEquipment().contains(15793)) {
                modifier += .02;
            }
            //OWNER Legs
            if (player.getEquipment().contains(15794)) {
                modifier += .02;
            }
            //OWNER Gloves
            if (player.getEquipment().contains(15795)) {
                modifier += .02;
            }
            //OWNER Boots
            if (player.getEquipment().contains(15796)) {
                modifier += .02;
            }
            //OWNER CAPE
            if (player.getEquipment().contains(19944)) {
                modifier += .06;
            }

            //OWNER Hat
            if (player.getEquipment().contains(9950) || player.getEquipment().contains(9955) || player.getEquipment().contains(9960)) {
                modifier += .03;
            }
            //OWNER Body
            if (player.getEquipment().contains(9951) || player.getEquipment().contains(9956) || player.getEquipment().contains(9961)) {
                modifier += .03;
            }
            //OWNER Legs
            if (player.getEquipment().contains(9952) || player.getEquipment().contains(9957) || player.getEquipment().contains(9962)) {
                modifier += .03;
            }
            //OWNER Gloves
            if (player.getEquipment().contains(9953) || player.getEquipment().contains(9958) || player.getEquipment().contains(9963)) {
                modifier += .03;
            }
            //OWNER Boots
            if (player.getEquipment().contains(9954) || player.getEquipment().contains(9959) || player.getEquipment().contains(9964)) {
                modifier += .03;
            }
            //OWNER CAPE
            if (player.getEquipment().contains(3520) || player.getEquipment().contains(3521) || player.getEquipment().contains(3522)) {
                modifier += .065;
            }
            //HOLY Hat
            if (player.getEquipment().contains(3020)) {
                modifier += .05;
            }
            //HOLY Body
            if (player.getEquipment().contains(3021)) {
                modifier += .05;
            }
            //HOLY Legs
            if (player.getEquipment().contains(3022)) {
                modifier += .05;
            }
            //HOLY Gloves
            if (player.getEquipment().contains(3023)) {
                modifier += .05;
            }
            //HOLY Boots
            if (player.getEquipment().contains(3024)) {
                modifier += .05;
            }

            player.checkOwnerItems();
            if (player.isWearingFullOwner()) {
                modifier += .04;
            }

            if (player.fullMysticTier1()){
                modifier += .01;
            }
            if (player.fullMysticTier2()){
                modifier += .02;
            }
            if (player.fullMysticTier3()){
                modifier += .03;
            }
            if (player.fullMysticTier4()){
                modifier += .04;
            }
            if (player.fullMysticTier5()){
                modifier += .05;
            }
            if (player.fullMysticTier6()){
                modifier += .06;
            }
            if (player.fullMysticTier7()){
                modifier += .07;
            }
            if (player.fullEliteTier1()){
                modifier += .08;
            }
            if (player.fullEliteTier2()){
                modifier += .09;
            }
            if (player.fullEliteTier3()){
                modifier += .1;
            }
            if (player.fullFallen()){
                modifier += .1;
            }
            if (player.fullEliteTier4()){
                modifier += .11;
            }
            if (player.fullEliteTier5()){
                modifier += .12;
            }
            if (player.fullEliteTier6()){
                modifier += .12;
            }
            if (player.fullSpectralTier1()){
                modifier += .13;
            }
            if (player.fullSpectralTier2()){
                modifier += .14;
            }
            if (player.fullSpectralTier3()){
                modifier += .15;
            }

            boolean vorpal_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getVorpalAmmo() > 1 && player.getQuiverMode() == 1;


            //VORPAL AMMO HANDLE
            if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1428 ||vorpal_quiver ) {
                modifier += .03;
            }

            boolean bloodstaind_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getBloodstainedAmmo() > 1 && player.getQuiverMode() == 2;

            //BLOODSTAIN AMMO HANDLE
            if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1430 || bloodstaind_quiver) {
                modifier += .10;
            }





            //SKELETAL
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEATHWALKER.npcId) {
                modifier += .01;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ARROWSHADE.npcId) {
                modifier += .02;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BONEMANCER.npcId) {
                modifier += .03;
            }

            //DEMONIC
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SHADOWFIEND.npcId) {
                modifier += .04;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEVILSPAWN.npcId) {
                modifier += .05;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ABYSSAL_TORMENTOR.npcId) {
                modifier += .05;
            }


            //OGRE
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.GRUNT_MAULER.npcId) {
                modifier += .06;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BRUTE_CRUSHER.npcId) {
                modifier += .06;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.VINESPLITTER.npcId) {
                modifier += .07;
            }



            //SPECTRAL
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.PHANTOM_DRIFTER.npcId) {
                modifier += .08;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.WHISPERING_WRAITH.npcId) {
                modifier += .09;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BANSHEE_KING.npcId) {
                modifier += .10;
            }


            //MASTER
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.EYE_OF_BEYOND.npcId) {
                modifier += .11;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.TALONWING.npcId) {
                modifier += .12;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.UMBRAL_BEAST.npcId) {
                modifier += .13;
            }

            //SOUL ENERGY AURA
            boolean soulEnergyEquiped = player.getEquipment().contains(17021);

            if (player.getPosition().getRegionId() != 13877  &&  player.getLocation() != Locations.Location.ENCHANTED_FOREST){
                if (player.getEquipment().contains(2613) || player.getEquipment().contains(17021)) {
                    modifier += (player.getSkillManager().getMaxLevel(Skill.HITPOINTS) - player.getSkillManager().getCurrentLevel(Skill.HITPOINTS)) * 0.005;
                }
            }

            //MELEE BOOSTER 1
            if (player.getEquipment().contains(16517)) {
                modifier += .02;
            }

            //MELEE BOOSTER 2
            if (player.getEquipment().contains(16518)) {
                modifier += .03;
            }

            //MELEE BOOSTER 3
            if (player.getEquipment().contains(16519)) {
                modifier += .05;
            }

            //TRIBRID BOOSTER 1
            if (player.getEquipment().contains(16513)) {
                modifier += .05;
            }

            //TRIBRID BOOSTER 2
            if (player.getEquipment().contains(16514)) {
                modifier += .07;
            }

            //TRIBRID BOOSTER 3
            if (player.getEquipment().contains(16515)) {
                modifier += .10;
            }

            //LAVA CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12840) {
                modifier += 0.2;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17005) {
                modifier += 0.3;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17006) {
                modifier += 0.4;
            }

            //VOID CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12842) {
                modifier += 0.4;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17009) {
                modifier += 0.5;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17010) {
                modifier += 0.6;
            }

            //CORRUPT CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 763) {
                modifier += 0.7;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 764) {
                modifier += 0.8;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 765) {
                modifier += 0.9;
            }

            if (PerkManager.currentPerk != null) {
                if (PerkManager.currentPerk.getName().equalsIgnoreCase("Damage")) {
                    modifier += 0.20;
                }
            }

            //BARBARIC AMULET
            if (player.getEquipment().contains(2069)) {
                modifier += 0.10;
            }
            //SPECTRAL AMULET
            if (player.getEquipment().contains(2071)) {
                modifier += 0.05;
            }
            //BARBARIC RING
            if (player.getEquipment().contains(2076)) {
                modifier += 0.10;
            }
            //SPECTRAL RING
            if (player.getEquipment().contains(2078)) {
                modifier += 0.05;
            }



       /*     if (victim.isNpc() ) {
                NPC mob = (NPC) entity;
                for (Item item : player.getEquipment().getItems()) {
                    if (item.getDefinition().isFire() && mob.getDefinition().isEarth()){ // HANDLE FIRE VS EARTH
                        modifier += 1;
                        player.sendMessage("HANDLE FIRE VS EARTH");
                    }
                    if (item.getDefinition().isWater() && mob.getDefinition().isFire()){ // Water VS Fire
                        modifier += 1;
                        player.sendMessage("HANDLE Water VS Fire");

                    }
                    if (item.getDefinition().isEarth() && mob.getDefinition().isWater()){ // HANDLE Earth VS Water
                        modifier += 1;
                        player.sendMessage("HANDLE Earth VS Water");
                    }
                }
            }*/

            if (CurseHandler.isActivated(player, CurseHandler.BLAZEUP)) {
                modifier += .05;
            }
            if (CurseHandler.isActivated(player, CurseHandler.INFERNO)) {
                modifier += .06;
            }
            if (CurseHandler.isActivated(player, CurseHandler.MALEVOLENCE)) {
                modifier += .06;
            }

            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.PERFECTIONIST)) {
                    modifier += .02;
                }
            }

            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.BERSERKER)) {
                    modifier += .025;
                }
            }

                //BEAST HUNTER MAXHITS
            if (victim.isNpc()) {
                NPC mob = (NPC) victim;
                modifier += ElementalUtils.getTotalModifier(player, mob.getDefinition().isFire(), mob.getDefinition().isWater(), mob.getDefinition().isEarth(), false);

                if (((NPC) victim).getId() == player.getSlayer().getSlayerTask().getNpcId() && player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER) || player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X ) || player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST )) {


                    //SLAYER ENERGY(2)
                    if (player.getEquipment().contains(2610)) {
                        modifier += .05;
                    }


                    //MYTHIC COSMETIC
                    if (player.getCosmetics().contains(15758)) {
                        modifier += .5;
                    }
                    if (player.getNodesUnlocked() != null) {
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_FURY)) {
                            modifier += .05;
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_MASTERY)) {
                            modifier += .05;
                        }
                    }
                }
            }

            //SPECTRAL SCYTHE
            if (player.getEquipment().contains(2088) || player.getEquipment().contains(23188)) {
                modifier += .16;
            }
            //OWNER BLADE
            if (player.getEquipment().contains(750)) {
                modifier += .20;
            }

            if (player.getXpmode() != null) {
                switch (player.getXpmode()) {
                    case MEDIUM:
                        modifier += .05;
                        break;
                    case ELITE:
                        modifier += .08;
                        break;
                    case MASTER:
                        modifier += .1;
                        break;
                }
            }

            if (player.getCosmetics().contains(3030)) {
                modifier += .1;
            }

            //SLAYER MAXHITS
            if (victim.isNpc()) {
                if (((NPC) victim).getId() == player.getSlayer().getSlayerTask().getNpcId() && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER) && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X) && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST)) {

                    //PURPLE COSMETICS
                    if (player.getCosmetics().contains(15723)) {
                        modifier += .02;
                    }
                    if (player.getCosmetics().contains(15772)) {
                        modifier += .02;
                    }
                    //PURPLE - SLAYER
                    if (player.getCosmetics().contains(15727)) {
                        modifier += .04;
                    }
                    if (player.getCosmetics().contains(15748)) {
                        modifier += .04;
                    }
                    if (player.getCosmetics().contains(15777)) {
                        modifier += .04;
                    }
                    //PURPLE - SLAYER
                    if (player.getCosmetics().contains(15732)) {
                        modifier += .06;
                    }
                    if (player.getCosmetics().contains(15751)) {
                        modifier += .06;
                    }
                    if (player.getCosmetics().contains(15784)) {
                        modifier += .06;
                    }

                    //SLAYER ENERGY(2)
                    if (player.getEquipment().contains(2610)) {
                        modifier += .05;
                    }
                    //TIER 1 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(22000) ||  player.getEquipment().contains(22001) || player.getEquipment().contains(22002)) {
                        modifier += .06;
                    }
                    //TIER 2 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(12460) ||  player.getEquipment().contains(12462) || player.getEquipment().contains(12464)) {

                        modifier += .08;
                    }
                    //TIER 3 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(12461) ||  player.getEquipment().contains(12463) || player.getEquipment().contains(12465)) {
                        modifier += .10;
                    }
                    //TIER 1 VOID HELMET
                    if (player.getEquipment().contains(22003)) {
                        modifier += .10;
                    }
                    //TIER 2 VOID HELMET
                    if (player.getEquipment().contains(12466)) {
                        modifier += .12;
                    }
                    //TIER 3 VOID HELMET
                    if (player.getEquipment().contains(12467)) {
                        modifier += .14;
                    }
                    //CORRUPT BLADE
                    if (player.getEquipment().contains(2651)) {
                        modifier += .15;
                    }
                    //TIER 1 CORRUPT HELMET
                    if (player.getEquipment().contains(2677)) {
                        modifier += .15;
                    }
                    //TIER 2 CORRUPT HELMET
                    if (player.getEquipment().contains(2678)) {
                        modifier += .16;
                    }
                    //TIER 3 CORRUPT HELMET
                    if (player.getEquipment().contains(2679)) {
                        modifier += .17;
                    }

                    //AQUATIC HELM
                    if (player.getEquipment().contains(3010)) {
                        modifier += .20;
                    }
                    //MAGMA HELM
                    if (player.getEquipment().contains(3011)) {
                        modifier += .20;
                    }
                    //OVERGROWN HELM
                    if (player.getEquipment().contains(3012)) {
                        modifier += .20;
                    }
                    //SPECTRAL HELM(1)
                    if (player.getEquipment().contains(3013)) {
                        modifier += .22;
                    }
                    //SPECTRAL HELM(2)
                    if (player.getEquipment().contains(3014)) {
                        modifier += .25;
                    }
                    //SPECTRAL HELM(3)
                    if (player.getEquipment().contains(3015)) {
                        modifier += .30;
                    }


                    if (player.getNodesUnlocked() != null) {
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_FURY)) {
                            modifier += .05;
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_MASTERY)) {
                            modifier += .05;
                        }
                    }
                }

            }
                maxHit *= modifier;

            maxHit *= (1D+((double)player.getDmgBoost()/100D));
        }

        if (victim != null && victim.isNpc()) {
            maxHit = NpcMaxHitLimit.limit((NPC) victim, maxHit, CombatType.MELEE);
        }


        return (int) Math.floor(maxHit);
    }

    public static int ranged(Character entity, Character victim) {
        double maxHit = 0;
        List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();

        //START RANGED
        if (entity.isNpc()) {
            NPC npc = (NPC) entity;
            maxHit = npc.getDefinition().getMaxHit() / 10;
        } else if (entity.isPlayer()) {
            Player player = (Player) entity;

            double rangedStrength = player.getBonusManager().getOtherBonus()[1];
            double rangeLevel = player.getSkillManager().getCurrentLevel(Skill.RANGED);
            double modifier = 1.0;
            // Prayers
            double prayerMod = 1.0;
            if (PrayerHandler.isActivated(player, PrayerHandler.SHARP_EYE)) {
                prayerMod = 1.05;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.HAWK_EYE)) {
                prayerMod = 1.10;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.EAGLE_EYE)) {
                prayerMod = 1.15;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                prayerMod = 1.23;
            } else if (CurseHandler.isActivated(player, CurseHandler.DESOLATION)) {
                prayerMod = 1.25;

            }

            double otherBonuses = 1;


            // Do calculations of maxhit...
            double effectiveRangeDamage = (int) (rangeLevel * prayerMod * otherBonuses);

            double baseDamage = 1.3 + effectiveRangeDamage / 10 + rangedStrength / 80 + effectiveRangeDamage * rangedStrength / 640;

            maxHit = (int) baseDamage * 3;
            modifier += player.getCompanion().baseDmgBoost()/10;


            if (player.getNoob().isActive()) {
                modifier += .5;
            }
            if (player.getDamageTimer().isActive() || player.getDivine().isActive()) {
                modifier += .07;
            }

            if (player.getVoteBoost().isActive()) {
                modifier += .05;
            }
            if (player.getIceBornTimer().isActive()) {
                modifier += .07;
            }
            if (player.getEasterTimer().isActive()) {
                modifier += .07;
            }
            if (player.getLoveTImer().isActive()) {
                modifier += .07;
            }
            if (player.getMagma().isActive()) {
                modifier += .10;
            }
            if (player.getBlitz().isActive()) {
                modifier += .05;
            }

            if (player.isClaimedPermDamage()) {
                modifier += .50;
            }

            //UNDERTAKER BOW
            if (player.getPosition().getRegionId() == 13365 || player.getPosition().getRegionId() == 13364 || player.getPosition().getRegionId() == 13363){
                if (player.getEquipment().contains(2079) || player.getEquipment().contains(2080) || player.getEquipment().contains(2081)){
                    modifier += .2;
                }
            }

            //SALT
            if (player.getInventory().contains(23121) || player.getInventory().contains(357)) {
                modifier += .04;
            }

            List<Integer> holidayNpcIds = Arrays.asList(1051, 1052, 1053, 1054, 1055, 1006, 1057, 1058, 1059);

            if (victim.isNpc()) {
                int npcId = ((NPC) victim).getId();

                if (npcId == 2021 || npcId == 2025) {//WATER PRAYERS VS FIRE ELEMENTAL
                    if (player.getCurseActive()[CurseHandler.EBBFLOW] || player.getCurseActive()[CurseHandler.AQUASTRIKE] || player.getCurseActive()[CurseHandler.RIPTIDE] || player.getCurseActive()[CurseHandler.SEASLICER] || player.getCurseActive()[CurseHandler.SWIFTTIDE]) {
                        modifier += .10;
                    }
                }

                if (npcId == 2022 || npcId == 2024) {//EARTH PRAYERS VS WATER ELEMENTAL
                    if (player.getCurseActive()[CurseHandler.GAIABLESSING] || player.getCurseActive()[CurseHandler.SERENITY] || player.getCurseActive()[CurseHandler.ROCKHEART] || player.getCurseActive()[CurseHandler.GEOVIGOR] || player.getCurseActive()[CurseHandler.STONEHAVEN]) {
                        modifier += .10;
                    }
                }

                if (npcId == 2019 || npcId == 2023) {//FIRE PRAYERS VS EARTH ELEMENTAL
                    if (player.getCurseActive()[CurseHandler.CINDERSTOUCH] || player.getCurseActive()[CurseHandler.EMBERBLAST] || player.getCurseActive()[CurseHandler.INFERNIFY] || player.getCurseActive()[CurseHandler.BLAZEUP] || player.getCurseActive()[CurseHandler.INFERNO]) {
                        modifier += .10;
                    }
                }
                if (holidayNpcIds.contains(npcId)) {

                    //SANTA Hat
                    if (player.getCosmetics().contains(1455)|| player.getEquipment().contains(1455 )) {
                        modifier += .06;
                    }
                    //SANTA Body
                    if (player.getCosmetics().contains(1456)|| player.getEquipment().contains(1456 )) {
                        modifier += .06;
                    }
                    //SANTA Legs
                    if (player.getCosmetics().contains(1457)|| player.getEquipment().contains(1457) ) {
                        modifier += .06;
                    }
                    //SANTA Gloves
                    if (player.getCosmetics().contains(1458)|| player.getEquipment().contains(1458) ) {
                        modifier += .06;
                    }
                    //SANTA Boots
                    if (player.getCosmetics().contains(1459)|| player.getEquipment().contains(1459) ) {
                        modifier += .06;
                    }
                    //SANTA Book
                    if (player.getCosmetics().contains(1466)|| player.getEquipment().contains(1466) ) {
                        modifier += .06;
                    }
                    //SANTA CAPE
                    if (player.getCosmetics().contains(1460) || player.getEquipment().contains(1460) ) {
                        modifier += .08;
                    }
                    //SANTA SCYTHE
                    if (player.getEquipment().contains(1461) || player.getEquipment().contains(1461)) {
                        modifier += .1;
                    }
                    //SANTA SNOWBALL
                    if (player.getEquipment().contains(1462)|| player.getEquipment().contains(1462)) {
                        modifier += .1;
                    }
                    //SANTA WAND
                    if (player.getEquipment().contains(1463)|| player.getEquipment().contains(1463)) {
                        modifier += .1;
                    }
                }
            }

            switch (HolidayManager.getBoostStage()) {
                case 0:
                    modifier += .02;
                    break;
                case 1:
                    modifier += .04;
                    break;
                case 2:
                    modifier += .06;
                    break;
                case 3:
                    modifier += .08;
                    break;
                case 4:
                    modifier += .10;
                    break;
            }


            //HOLY GRAIL HANDLE - TYLER
            if (player.getEquipment().contains(436) && !player.isHolyGrailDroprate()) {
                modifier += .05;
            }
            if (player.getEquipment().contains(436) && player.isHolyGrailDroprate()) {
                modifier -= .05;
            }


            //RING OF NATURE
            if (player.getEquipment().contains(7245)) {
                modifier += .02;
            }

            //AMULET OF NATURE
            if (player.getEquipment().contains(7236)) {
                modifier += .02;
            }

            //BARBARIC AMULET
            if (player.getEquipment().contains(2069)) {
                modifier += 0.10;
            }
            //SPECTRAL AMULET
            if (player.getEquipment().contains(2071)) {
                modifier += 0.05;
            }
            //BARBARIC RING
            if (player.getEquipment().contains(2076)) {
                modifier += 0.10;
            }
            //SPECTRAL RING
            if (player.getEquipment().contains(2078)) {
                modifier += 0.05;
            }


            //GREEN - RANGE - EARTH
            if (player.getCosmetics().contains(15724)) {
                modifier += .02;
            }
            if (player.getCosmetics().contains(15740)) {
                modifier += .02;
            }
            if (player.getCosmetics().contains(15774)) {
                modifier += .02;
            }
            //GREEN - RANGE - EARTH
            if (player.getCosmetics().contains(15726)) {
                modifier += .04;
            }
            if (player.getCosmetics().contains(15746)) {
                modifier += .04;
            }
            if (player.getCosmetics().contains(15776)) {
                modifier += .04;
            }
            //GREEN - RANGE - EARTH
            if (player.getCosmetics().contains(15731)) {
                modifier += .06;
            }
            if (player.getCosmetics().contains(15753)) {
                modifier += .06;
            }
            if (player.getCosmetics().contains(15782)) {
                modifier += .06;
            }
            //GREEN - RANGE - EARTH
            if (player.getCosmetics().contains(15736)) {
                modifier += .10;
            }
            if (player.getCosmetics().contains(15756)) {
                modifier += .10;
            }
            if (player.getCosmetics().contains(15787)) {
                modifier += .10;
            }

            //OWNER WEPS
            if (player.getEquipment().contains(750)) {
                modifier += .04;
            }
            if (player.getEquipment().contains(751)) {
                modifier += .04;
            }
            if (player.getEquipment().contains(752)) {
                modifier += .04;
            }

            //OWNER Hat
            if (player.getEquipment().contains(15792)) {
                modifier += .02;
            }
            //OWNER Body
            if (player.getEquipment().contains(15793)) {
                modifier += .02;
            }
            //OWNER Legs
            if (player.getEquipment().contains(15794)) {
                modifier += .02;
            }
            //OWNER Gloves
            if (player.getEquipment().contains(15795)) {
                modifier += .02;
            }
            //OWNER Boots
            if (player.getEquipment().contains(15796)) {
                modifier += .02;
            }
            //OWNER CAPE
            if (player.getEquipment().contains(19944)) {
                modifier += .06;
            }

            //OWNER Hat
            if (player.getEquipment().contains(9950) || player.getEquipment().contains(9955) || player.getEquipment().contains(9960)) {
                modifier += .03;
            }
            //OWNER Body
            if (player.getEquipment().contains(9951) || player.getEquipment().contains(9956) || player.getEquipment().contains(9961)) {
                modifier += .03;
            }
            //OWNER Legs
            if (player.getEquipment().contains(9952) || player.getEquipment().contains(9957) || player.getEquipment().contains(9962)) {
                modifier += .03;
            }
            //OWNER Gloves
            if (player.getEquipment().contains(9953) || player.getEquipment().contains(9958) || player.getEquipment().contains(9963)) {
                modifier += .03;
            }
            //OWNER Boots
            if (player.getEquipment().contains(9954) || player.getEquipment().contains(9959) || player.getEquipment().contains(9964)) {
                modifier += .03;
            }
            //OWNER CAPE
            if (player.getEquipment().contains(3520) || player.getEquipment().contains(3521) || player.getEquipment().contains(3522)) {
                modifier += .065;
            }
            //HOLY Hat
            if (player.getEquipment().contains(3020)) {
                modifier += .05;
            }
            //HOLY Body
            if (player.getEquipment().contains(3021)) {
                modifier += .05;
            }
            //HOLY Legs
            if (player.getEquipment().contains(3022)) {
                modifier += .05;
            }
            //HOLY Gloves
            if (player.getEquipment().contains(3023)) {
                modifier += .05;
            }
            //HOLY Boots
            if (player.getEquipment().contains(3024)) {
                modifier += .05;
            }

            player.checkOwnerItems();
            if (player.isWearingFullOwner()) {
                modifier += .04;
            }


                if (player.fullMysticTier1()){
                modifier += .01;
            }
            if (player.fullMysticTier2()){
                modifier += .02;
            }
            if (player.fullMysticTier3()){
                modifier += .03;
            }
            if (player.fullMysticTier4()){
                modifier += .04;
            }
            if (player.fullMysticTier5()){
                modifier += .05;
            }
            if (player.fullMysticTier6()){
                modifier += .06;
            }
            if (player.fullMysticTier7()){
                modifier += .07;
            }
            if (player.fullEliteTier1()){
                modifier += .08;
            }
            if (player.fullEliteTier2()){
                modifier += .09;
            }
            if (player.fullEliteTier3()){
                modifier += .1;
            }
            if (player.fullFallen()){
                modifier += .1;
            }
            if (player.fullEliteTier4()){
                modifier += .11;
            }
            if (player.fullEliteTier5()){
                modifier += .12;
            }
            if (player.fullEliteTier6()){
                modifier += .12;
            }
            if (player.fullSpectralTier1()){
                modifier += .13;
            }
            if (player.fullSpectralTier2()){
                modifier += .14;
            }
            if (player.fullSpectralTier3()){
                modifier += .15;
            }

            boolean vorpal_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getVorpalAmmo() > 1 && player.getQuiverMode() == 1;


            //VORPAL AMMO HANDLE
            if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1428 ||vorpal_quiver ) {
                modifier += .03;
            }

            boolean bloodstaind_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getBloodstainedAmmo() > 1 && player.getQuiverMode() == 2;

            //BLOODSTAIN AMMO HANDLE
            if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1430 || bloodstaind_quiver) {
                modifier += .10;
            }

            //SKELETAL
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEATHWALKER.npcId) {
                modifier += .01;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ARROWSHADE.npcId) {
                modifier += .02;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BONEMANCER.npcId) {
                modifier += .03;
            }

            //DEMONIC
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SHADOWFIEND.npcId) {
                modifier += .04;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEVILSPAWN.npcId) {
                modifier += .05;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ABYSSAL_TORMENTOR.npcId) {
                modifier += .05;
            }


            //OGRE
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.GRUNT_MAULER.npcId) {
                modifier += .06;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BRUTE_CRUSHER.npcId) {
                modifier += .06;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.VINESPLITTER.npcId) {
                modifier += .07;
            }



            //SPECTRAL
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.PHANTOM_DRIFTER.npcId) {
                modifier += .08;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.WHISPERING_WRAITH.npcId) {
                modifier += .09;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BANSHEE_KING.npcId) {
                modifier += .10;
            }


            //MASTER
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.EYE_OF_BEYOND.npcId) {
                modifier += .11;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.TALONWING.npcId) {
                modifier += .12;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.UMBRAL_BEAST.npcId) {
                modifier += .13;
            }

            boolean soulEnergyEquiped = player.getEquipment().contains(17021);

            if (player.getPosition().getRegionId() != 13877&&  player.getLocation() != Locations.Location.ENCHANTED_FOREST){
                if (player.getEquipment().contains(2613) || player.getEquipment().contains(17021)) {
                    modifier += (player.getSkillManager().getMaxLevel(Skill.HITPOINTS) - player.getSkillManager().getCurrentLevel(Skill.HITPOINTS)) * 0.005;
                }
            }

            //RANGE BOOSTER 1
            if (player.getEquipment().contains(16520)) {
                modifier += .02;
            }

            //RANGE BOOSTER 2
            if (player.getEquipment().contains(16508)) {
                modifier += .03;
            }

            //RANGE BOOSTER 3
            if (player.getEquipment().contains(16509)) {
                modifier += .05;
            }

            //TRIBRID BOOSTER 1
            if (player.getEquipment().contains(16513)) {
                modifier += .05;
            }

            //TRIBRID BOOSTER 2
            if (player.getEquipment().contains(16514)) {
                modifier += .07;
            }

            //TRIBRID BOOSTER 3
            if (player.getEquipment().contains(16515)) {
                modifier += .10;
            }



            //LAVA CRYSTAL TIERS
            //LAVA CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12840) {
                modifier += 0.2;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17005) {
                modifier += 0.3;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17006) {
                modifier += 0.4;
            }

            //VOID CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12842) {
                modifier += 0.4;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17009) {
                modifier += 0.5;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17010) {
                modifier += 0.6;
            }
            //CORRUPT CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 763) {
                modifier += 0.7;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 764) {
                modifier += 0.8;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 765) {
                modifier += 0.9;
            }

            if (PerkManager.currentPerk != null) {
                if (PerkManager.currentPerk.getName().equalsIgnoreCase("Damage")) {
                    modifier += 0.20;
                }
            }


            if (CurseHandler.isActivated(player, CurseHandler.BLAZEUP)) {
                modifier += .05;
            }
            if (CurseHandler.isActivated(player, CurseHandler.INFERNO)) {
                modifier += .06;
            }
            if (CurseHandler.isActivated(player, CurseHandler.MALEVOLENCE)) {
                modifier += .06;
            }

            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.PERFECTIONIST)) {
                    modifier += .02;
                }
            }

            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.HAWKEYE)) {
                    modifier += .025;
                }
            }

            //BEAST HUNTER MAXHITS
            if (victim.isNpc()) {
                NPC mob = (NPC) victim;
                modifier += ElementalUtils.getTotalModifier(player, mob.getDefinition().isFire(), mob.getDefinition().isWater(), mob.getDefinition().isEarth(), false);

                if (((NPC) victim).getId() == player.getSlayer().getSlayerTask().getNpcId() && player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER) || player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X) || player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST)) {



                    if (player.getNodesUnlocked() != null) {
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_FURY)) {
                            modifier += .05;
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_MASTERY)) {
                            modifier += .05;
                        }
                    }
                }
            }


            //SPECTRAL BOW
            if (player.getEquipment().contains(2086)) {
                modifier += .12;
            }
            //OWNER BOW
            if (player.getEquipment().contains(751)) {
                modifier += .20;
            }

            if (player.getXpmode() != null) {
                switch (player.getXpmode()) {
                    case MEDIUM:
                        modifier += .05;
                        break;
                    case ELITE:
                        modifier += .08;
                        break;
                    case MASTER:
                        modifier += .1;
                        break;
                }
            }

            if (player.getCosmetics().contains(3030)) {
                modifier += .1;
            }

            //SLAYER MAXHITS
            if (victim.isNpc()) {
                if (((NPC) victim).getId() == player.getSlayer().getSlayerTask().getNpcId() && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER) && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X) && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST)) {


                    //PURPLE COSMETICS
                    if (player.getCosmetics().contains(15723)) {
                        modifier += .02;
                    }
                    if (player.getCosmetics().contains(15772)) {
                        modifier += .02;
                    }
                    //PURPLE - SLAYER
                    if (player.getCosmetics().contains(15727)) {
                        modifier += .04;
                    }
                    if (player.getCosmetics().contains(15748)) {
                        modifier += .04;
                    }
                    if (player.getCosmetics().contains(15777)) {
                        modifier += .04;
                    }
                    //PURPLE - SLAYER
                    if (player.getCosmetics().contains(15732)) {
                        modifier += .06;
                    }
                    if (player.getCosmetics().contains(15751)) {
                        modifier += .06;
                    }
                    if (player.getCosmetics().contains(15784)) {
                        modifier += .06;
                    }
                    //Mythic
                    if (player.getCosmetics().contains(15759)) {
                        modifier += .08;
                    }

                    //SLAYER ENERGY(2)
                    if (player.getEquipment().contains(2610)) {
                        modifier += .05;
                    }
                    //TIER 1 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(22000) ||  player.getEquipment().contains(22001) || player.getEquipment().contains(22002)) {
                        modifier += .06;
                    }
                    //TIER 2 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(12460) ||  player.getEquipment().contains(12462) || player.getEquipment().contains(12464)) {

                        modifier += .08;
                    }
                    //TIER 3 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(12461) ||  player.getEquipment().contains(12463) || player.getEquipment().contains(12465)) {
                        modifier += .10;
                    }
                    //SLAYER ENERGY(2)
                    if (player.getEquipment().contains(2610)) {
                        modifier += .05;
                    }
                    //TIER 1 VOID HELMET
                    if (player.getEquipment().contains(22003)) {
                        modifier += .10;
                    }
                    //TIER 2 VOID HELMET
                    if (player.getEquipment().contains(12466)) {
                        modifier += .12;
                    }
                    //TIER 3 VOID HELMET
                    if (player.getEquipment().contains(12467)) {
                        modifier += .14;
                    }
                    //CORRUPT BOW
                    if (player.getEquipment().contains(2653)) {
                        modifier += .15;
                    }

                    //TIER 1 CORRUPT HELMET
                    if (player.getEquipment().contains(2677)) {
                        modifier += .15;
                    }
                    //TIER 2 CORRUPT HELMET
                    if (player.getEquipment().contains(2678)) {
                        modifier += .16;
                    }
                    //TIER 3 CORRUPT HELMET
                    if (player.getEquipment().contains(2679)) {
                        modifier += .17;
                    }
                    //AQUATIC HELM
                    if (player.getEquipment().contains(3010)) {
                        modifier += .20;
                    }
                    //MAGMA HELM
                    if (player.getEquipment().contains(3011)) {
                        modifier += .20;
                    }
                    //OVERGROWN HELM
                    if (player.getEquipment().contains(3012)) {
                        modifier += .20;
                    }
                    //SPECTRAL HELM(1)
                    if (player.getEquipment().contains(3013)) {
                        modifier += .22;
                    }
                    //SPECTRAL HELM(2)
                    if (player.getEquipment().contains(3014)) {
                        modifier += .25;
                    }
                    //SPECTRAL HELM(3)
                    if (player.getEquipment().contains(3015)) {
                        modifier += .30;
                    }
                    if (player.getNodesUnlocked() != null) {
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_FURY)) {
                            modifier += .05;
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_MASTERY)) {
                            modifier += .05;
                        }
                    }
                }
            }

            maxHit *= modifier;
            maxHit *= (1D+((double)player.getDmgBoost()/100D));
            if (victim != null && victim.isNpc()) {
                maxHit = NpcMaxHitLimit.limit((NPC) victim, maxHit, CombatType.MELEE);
            }

        }
        return (int) Math.floor(maxHit);
    }

    public static int magic(Character entity, Character victim) {
        double maxHit = 0;
        List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();

        //START MAGIC

        if (entity.isNpc()) {
            NPC npc = (NPC) entity;
            maxHit = npc.getDefinition().getMaxHit() / 10;
        } else if (entity.isPlayer()) {
            Player player = (Player) entity;

            double magicStrength = player.getBonusManager().getOtherBonus()[3];
            double magicLevel = player.getSkillManager().getCurrentLevel(Skill.MAGIC);

            // Prayers
            double prayerMod = 1.0;
            if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_WILL)) {
                prayerMod = 1.05;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_LORE)) {
                prayerMod = 1.10;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_MIGHT)) {
                prayerMod = 1.15;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                prayerMod = 1.23;
            } else if (CurseHandler.isActivated(player, CurseHandler.DESOLATION)) {
                prayerMod = 1.25;
            }

            double otherBonuses = 1;
            double effectiveMagicDamage = (int) (magicLevel * prayerMod * otherBonuses);
            double baseDamage = 1.3 + effectiveMagicDamage / 10 + magicStrength / 80 + effectiveMagicDamage * magicStrength / 640;

            maxHit = (int) baseDamage * 3;
            double modifier = 1.0;

            modifier += player.getCompanion().baseDmgBoost()/10;

            if (player.getNoob().isActive()) {
                modifier += .5;
            }
            if (player.getDamageTimer().isActive() || player.getDivine().isActive()) {
                modifier += .07;
            }
            if (player.getVoteBoost().isActive()) {
                modifier += .05;
            }
            if (player.getIceBornTimer().isActive()) {
                modifier += .07;
            }
            if (player.getEasterTimer().isActive()) {
                modifier += .07;
            }
            if (player.getLoveTImer().isActive()) {
                modifier += .07;
            }
            if (player.getMagma().isActive()) {
                modifier += .10;
            }

            if (player.getBlitz().isActive()) {
                modifier += .05;
            }

            if (player.isClaimedPermDamage()) {
                modifier += .50;
            }







            //SALT
            if (player.getInventory().contains(23121) || player.getInventory().contains(357)) {
                modifier += .04;
            }

            List<Integer> holidayNpcIds = Arrays.asList(1051, 1052, 1053, 1054, 1055, 1006, 1057, 1058, 1059);

            if (victim.isNpc()) {
                int npcId = ((NPC) victim).getId();

                if (npcId == 2021 || npcId == 2025) {//WATER PRAYERS VS FIRE ELEMENTAL
                    if (player.getCurseActive()[CurseHandler.EBBFLOW] || player.getCurseActive()[CurseHandler.AQUASTRIKE] || player.getCurseActive()[CurseHandler.RIPTIDE] || player.getCurseActive()[CurseHandler.SEASLICER] || player.getCurseActive()[CurseHandler.SWIFTTIDE]) {
                        modifier += .10;
                    }
                }

                if (npcId == 2022 || npcId == 2024) {//EARTH PRAYERS VS WATER ELEMENTAL
                    if (player.getCurseActive()[CurseHandler.GAIABLESSING] || player.getCurseActive()[CurseHandler.SERENITY] || player.getCurseActive()[CurseHandler.ROCKHEART] || player.getCurseActive()[CurseHandler.GEOVIGOR] || player.getCurseActive()[CurseHandler.STONEHAVEN]) {
                        modifier += .10;
                    }
                }

                if (npcId == 2019 || npcId == 2023) {//FIRE PRAYERS VS EARTH ELEMENTAL
                    if (player.getCurseActive()[CurseHandler.CINDERSTOUCH] || player.getCurseActive()[CurseHandler.EMBERBLAST] || player.getCurseActive()[CurseHandler.INFERNIFY] || player.getCurseActive()[CurseHandler.BLAZEUP] || player.getCurseActive()[CurseHandler.INFERNO]) {
                        modifier += .10;
                    }
                }
                if (holidayNpcIds.contains(npcId)) {


                    //SANTA Hat
                    if (player.getCosmetics().contains(1455)|| player.getEquipment().contains(1455 )) {
                        modifier += .06;
                    }
                    //SANTA Body
                    if (player.getCosmetics().contains(1456)|| player.getEquipment().contains(1456 )) {
                        modifier += .06;
                    }
                    //SANTA Legs
                    if (player.getCosmetics().contains(1457)|| player.getEquipment().contains(1457) ) {
                        modifier += .06;
                    }
                    //SANTA Gloves
                    if (player.getCosmetics().contains(1458)|| player.getEquipment().contains(1458) ) {
                        modifier += .06;
                    }
                    //SANTA Boots
                    if (player.getCosmetics().contains(1459)|| player.getEquipment().contains(1459) ) {
                        modifier += .06;
                    }
                    //SANTA Book
                    if (player.getCosmetics().contains(1466)|| player.getEquipment().contains(1466) ) {
                        modifier += .06;
                    }
                    //SANTA CAPE
                    if (player.getCosmetics().contains(1460) || player.getEquipment().contains(1460) ) {
                        modifier += .08;
                    }
                    //SANTA SCYTHE
                    if (player.getEquipment().contains(1461) || player.getEquipment().contains(1461)) {
                        modifier += .1;
                    }
                    //SANTA SNOWBALL
                    if (player.getEquipment().contains(1462)|| player.getEquipment().contains(1462)) {
                        modifier += .1;
                    }
                    //SANTA WAND
                    if (player.getEquipment().contains(1463)|| player.getEquipment().contains(1463)) {
                        modifier += .1;
                    }
                }
            }

            switch (HolidayManager.getBoostStage()) {
                case 0:
                    modifier += .02;
                    break;
                case 1:
                    modifier += .04;
                    break;
                case 2:
                    modifier += .06;
                    break;
                case 3:
                    modifier += .08;
                    break;
                case 4:
                    modifier += .10;
                    break;
            }

            //HOLY GRAIL HANDLE - TYLER
            if (player.getEquipment().contains(436) && !player.isHolyGrailDroprate()) {
                modifier += .05;
            }
            if (player.getEquipment().contains(436) && player.isHolyGrailDroprate()) {
                modifier -= .05;
            }

            //RING OF ATLANTIS
            if (player.getEquipment().contains(7249)) {
                modifier += .02;
            }

            //AMULET OF ATLANTIS
            if (player.getEquipment().contains(7241)) {
                modifier += .02;
            }

            //BLUE - MAGIC - WATER
            if (player.getCosmetics().contains(15720)) {
                modifier += .02;
            }
            if (player.getCosmetics().contains(15739)) {
                modifier += .02;
            }
            if (player.getCosmetics().contains(15770)) {
                modifier += .02;
            }
            //BLUE - MAGIC - WATER
            if (player.getCosmetics().contains(15725)) {
                modifier += .04;
            }
            if (player.getCosmetics().contains(15745)) {
                modifier += .04;
            }
            if (player.getCosmetics().contains(15778)) {
                modifier += .04;
            }
            //BLUE - MAGIC - WATER
            if (player.getCosmetics().contains(15729)) {
                modifier += .06;
            }
            if (player.getCosmetics().contains(15754)) {
                modifier += .06;
            }
            if (player.getCosmetics().contains(15781)) {
                modifier += .06;
            }
            //BLUE - MAGIC - WATER
            if (player.getCosmetics().contains(15735)) {
                modifier += .10;
            }
            if (player.getCosmetics().contains(15755)) {
                modifier += .10;
            }
            if (player.getCosmetics().contains(15786)) {
                modifier += .10;
            }


            //BARBARIC AMULET
            if (player.getEquipment().contains(2069)) {
                modifier += 0.10;
            }
            //SPECTRAL AMULET
            if (player.getEquipment().contains(2071)) {
                modifier += 0.05;
            }
            //BARBARIC RING
            if (player.getEquipment().contains(2076)) {
                modifier += 0.10;
            }
            //SPECTRAL RING
            if (player.getEquipment().contains(2078)) {
                modifier += 0.05;
            }

            //OWNER WEPS
            if (player.getEquipment().contains(750)) {
                modifier += .04;
            }
            if (player.getEquipment().contains(751)) {
                modifier += .04;
            }
            if (player.getEquipment().contains(752)) {
                modifier += .04;
            }

            //OWNER Hat
            if (player.getEquipment().contains(15792)) {
                modifier += .02;
            }
            //OWNER Body
            if (player.getEquipment().contains(15793)) {
                modifier += .02;
            }
            //OWNER Legs
            if (player.getEquipment().contains(15794)) {
                modifier += .02;
            }
            //OWNER Gloves
            if (player.getEquipment().contains(15795)) {
                modifier += .02;
            }
            //OWNER Boots
            if (player.getEquipment().contains(15796)) {
                modifier += .02;
            }
            //OWNER CAPE
            if (player.getEquipment().contains(19944)) {
                modifier += .06;
            }

            //OWNER Hat
            if (player.getEquipment().contains(9950) || player.getEquipment().contains(9955) || player.getEquipment().contains(9960)) {
                modifier += .03;
            }
            //OWNER Body
            if (player.getEquipment().contains(9951) || player.getEquipment().contains(9956) || player.getEquipment().contains(9961)) {
                modifier += .03;
            }
            //OWNER Legs
            if (player.getEquipment().contains(9952) || player.getEquipment().contains(9957) || player.getEquipment().contains(9962)) {
                modifier += .03;
            }
            //OWNER Gloves
            if (player.getEquipment().contains(9953) || player.getEquipment().contains(9958) || player.getEquipment().contains(9963)) {
                modifier += .03;
            }
            //OWNER Boots
            if (player.getEquipment().contains(9954) || player.getEquipment().contains(9959) || player.getEquipment().contains(9964)) {
                modifier += .03;
            }
            //OWNER CAPE
            if (player.getEquipment().contains(3520) || player.getEquipment().contains(3521) || player.getEquipment().contains(3522)) {
                modifier += .065;
            }
            //HOLY Hat
            if (player.getEquipment().contains(3020)) {
                modifier += .05;
            }
            //HOLY Body
            if (player.getEquipment().contains(3021)) {
                modifier += .05;
            }
            //HOLY Legs
            if (player.getEquipment().contains(3022)) {
                modifier += .05;
            }
            //HOLY Gloves
            if (player.getEquipment().contains(3023)) {
                modifier += .05;
            }
            //HOLY Boots
            if (player.getEquipment().contains(3024)) {
                modifier += .05;
            }

            player.checkOwnerItems();
            if (player.isWearingFullOwner()) {
                modifier += .04;
            }

            if (player.fullMysticTier1()){
                modifier += .01;
            }
            if (player.fullMysticTier2()){
                modifier += .02;
            }
            if (player.fullMysticTier3()){
                modifier += .03;
            }
            if (player.fullMysticTier4()){
                modifier += .04;
            }
            if (player.fullMysticTier5()){
                modifier += .05;
            }
            if (player.fullMysticTier6()){
                modifier += .06;
            }
            if (player.fullMysticTier7()){
                modifier += .07;
            }
            if (player.fullEliteTier1()){
                modifier += .08;
            }
            if (player.fullEliteTier2()){
                modifier += .09;
            }
            if (player.fullEliteTier3()){
                modifier += .1;
            }
            if (player.fullFallen()){
                modifier += .1;
            }
            if (player.fullEliteTier4()){
                modifier += .11;
            }
            if (player.fullEliteTier5()){
                modifier += .12;
            }
            if (player.fullEliteTier6()){
                modifier += .13;
            }
            if (player.fullSpectralTier1()){
                modifier += .13;
            }
            if (player.fullSpectralTier2()){
                modifier += .14;
            }
            if (player.fullSpectralTier3()){
                modifier += .15;
            }

            boolean vorpal_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getVorpalAmmo() > 1 && player.getQuiverMode() == 1;


            //VORPAL AMMO HANDLE
            if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1428 ||vorpal_quiver ) {
                modifier += .03;
            }

            boolean bloodstaind_quiver = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && player.getBloodstainedAmmo() > 1 && player.getQuiverMode() == 2;

            //BLOODSTAIN AMMO HANDLE
            if (player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1430 || bloodstaind_quiver) {
                modifier += .10;
            }

            //SKELETAL
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEATHWALKER.npcId) {
                modifier += .01;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ARROWSHADE.npcId) {
                modifier += .02;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BONEMANCER.npcId) {
                modifier += .03;
            }

            //DEMONIC
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SHADOWFIEND.npcId) {
                modifier += .04;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEVILSPAWN.npcId) {
                modifier += .05;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.ABYSSAL_TORMENTOR.npcId) {
                modifier += .05;
            }


            //OGRE
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.GRUNT_MAULER.npcId) {
                modifier += .06;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BRUTE_CRUSHER.npcId) {
                modifier += .06;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.VINESPLITTER.npcId) {
                modifier += .07;
            }



            //SPECTRAL
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.PHANTOM_DRIFTER.npcId) {
                modifier += .08;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.WHISPERING_WRAITH.npcId) {
                modifier += .09;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.BANSHEE_KING.npcId) {
                modifier += .10;
            }


            //MASTER
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.EYE_OF_BEYOND.npcId) {
                modifier += .11;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.TALONWING.npcId) {
                modifier += .12;
            }
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                    && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.UMBRAL_BEAST.npcId) {
                modifier += .13;
            }

            boolean soulEnergyEquiped = player.getEquipment().contains(17021);

            if (player.getPosition().getRegionId() != 13877&&  player.getLocation() != Locations.Location.ENCHANTED_FOREST){
                if (player.getEquipment().contains(2613) || player.getEquipment().contains(17021)) {
                    modifier += (player.getSkillManager().getMaxLevel(Skill.HITPOINTS) - player.getSkillManager().getCurrentLevel(Skill.HITPOINTS)) * 0.005;
                }
            }

            //MAGIC BOOSTER 1
            if (player.getEquipment().contains(16510)) {
                modifier += .02;
            }

            //MAGIC BOOSTER 2
            if (player.getEquipment().contains(16511)) {
                modifier += .03;
            }

            //MAGIC BOOSTER 3
            if (player.getEquipment().contains(16512)) {
                modifier += .05;
            }

            //TRIBRID BOOSTER 1
            if (player.getEquipment().contains(16513)) {
                modifier += .05;
            }

            //TRIBRID BOOSTER 2
            if (player.getEquipment().contains(16514)) {
                modifier += .07;
            }

            //TRIBRID BOOSTER 3
            if (player.getEquipment().contains(16515)) {
                modifier += .10;
            }

            //LAVA CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12840) {
                modifier += 0.2;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17005) {
                modifier += 0.3;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17006) {
                modifier += 0.4;
            }

            //VOID CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 12842) {
                modifier += 0.4;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17009) {
                modifier += 0.5;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 17010) {
                modifier += 0.6;
            }

            //CORRUPT CRYSTAL TIERS
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 763) {
                modifier += 0.7;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 764) {
                modifier += 0.8;
            }
            if (player.getEquipment().getItems()[Equipment.ASCEND].getId() == 765) {
                modifier += 0.9;
            }



            if (CurseHandler.isActivated(player, CurseHandler.BLAZEUP)) {
                modifier += .05;
            }
            if (CurseHandler.isActivated(player, CurseHandler.INFERNO)) {
                modifier += .06;
            }
            if (CurseHandler.isActivated(player, CurseHandler.MALEVOLENCE)) {
                modifier += .06;
            }

            if (PerkManager.currentPerk != null) {
                if (PerkManager.currentPerk.getName().equalsIgnoreCase("Damage")) {
                    modifier += 0.20;
                }
            }
            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.PERFECTIONIST)) {
                    modifier += .02;
                }
            }

            if (player.getNodesUnlocked() != null) {
                if (player.getSkillTree().isNodeUnlocked(Node.SORCERERS_VEIL)) {
                    modifier += .025;
                }
            }

            //BEAST HUNTER MAXHITS
            if (victim.isNpc()) {

                NPC mob = (NPC) victim;
                modifier += ElementalUtils.getTotalModifier(player, mob.getDefinition().isFire(), mob.getDefinition().isWater(), mob.getDefinition().isEarth(), false);

                if (((NPC) victim).getId() == player.getSlayer().getSlayerTask().getNpcId() && player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER) || player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X) || player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST)) {
                    if (player.getNodesUnlocked() != null) {
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_FURY)) {
                            modifier += .05;
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_MASTERY)) {
                            modifier += .05;
                        }
                    }
                }
            }


            //SPECTRAL STAFF
            if (player.getEquipment().contains(2087)) {
                modifier += .14;
            }
            //OWNER STAFF
            if (player.getEquipment().contains(752)) {
                modifier += .20;
            }
            if (player.getXpmode() != null) {
                switch (player.getXpmode()) {
                    case MEDIUM:
                        modifier += .05;
                        break;
                    case ELITE:
                        modifier += .08;
                        break;
                    case MASTER:
                        modifier += .1;
                        break;
                }
            }

            if (player.getCosmetics().contains(3030)) {
                modifier += .1;
            }

            //SLAYER MAXHITS
            if (victim.isNpc()) {
                if (((NPC) victim).getId() == player.getSlayer().getSlayerTask().getNpcId() && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER) && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.BEAST_MASTER_X) && !player.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_BEAST)) {


                    //PURPLE COSMETICS
                    if (player.getCosmetics().contains(15723)) {
                        modifier += .02;
                    }
                    if (player.getCosmetics().contains(15772)) {
                        modifier += .02;
                    }
                    //PURPLE - SLAYER
                    if (player.getCosmetics().contains(15727)) {
                        modifier += .04;
                    }
                    if (player.getCosmetics().contains(15748)) {
                        modifier += .04;
                    }
                    if (player.getCosmetics().contains(15777)) {
                        modifier += .04;
                    }
                    //PURPLE - SLAYER
                    if (player.getCosmetics().contains(15732)) {
                        modifier += .06;
                    }
                    if (player.getCosmetics().contains(15751)) {
                        modifier += .06;
                    }
                    if (player.getCosmetics().contains(15784)) {
                        modifier += .06;
                    }
                    //SLAYER ENERGY(2)
                    if (player.getEquipment().contains(2610)) {
                        modifier += .05;
                    }
                    //TIER 1 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(22000) ||  player.getEquipment().contains(22001) || player.getEquipment().contains(22002)) {
                        modifier += .06;
                    }
                    //TIER 2 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(12460) ||  player.getEquipment().contains(12462) || player.getEquipment().contains(12464)) {

                        modifier += .08;
                    }
                    //TIER 3 SLAYER HELMETS - F-W-E
                    if (player.getEquipment().contains(12461) ||  player.getEquipment().contains(12463) || player.getEquipment().contains(12465)) {
                        modifier += .10;
                    }
                    //SLAYER ENERGY(2)
                    if (player.getEquipment().contains(2610)) {
                        modifier += .05;
                    }
                    //TIER 1 VOID HELMET
                    if (player.getEquipment().contains(22003)) {
                        modifier += .10;
                    }
                    //TIER 2 VOID HELMET
                    if (player.getEquipment().contains(12466)) {
                        modifier += .12;
                    }
                    //TIER 3 VOID HELMET
                    if (player.getEquipment().contains(12467)) {
                        modifier += .14;
                    }
                    //CORRUPT STAFF
                    if (player.getEquipment().contains(2655)) {
                        modifier += .15;
                    }
                    //TIER 1 CORRUPT HELMET
                    if (player.getEquipment().contains(2677)) {
                        modifier += .15;
                    }
                    //TIER 2 CORRUPT HELMET
                    if (player.getEquipment().contains(2678)) {
                        modifier += .16;
                    }
                    //TIER 3 CORRUPT HELMET
                    if (player.getEquipment().contains(2679)) {
                        modifier += .17;
                    }
                    //AQUATIC HELM
                    if (player.getEquipment().contains(3010)) {
                        modifier += .20;
                    }
                    //MAGMA HELM
                    if (player.getEquipment().contains(3011)) {
                        modifier += .20;
                    }
                    //OVERGROWN HELM
                    if (player.getEquipment().contains(3012)) {
                        modifier += .20;
                    }
                    //SPECTRAL HELM(1)
                    if (player.getEquipment().contains(3013)) {
                        modifier += .22;
                    }
                    //SPECTRAL HELM(2)
                    if (player.getEquipment().contains(3014)) {
                        modifier += .25;
                    }
                    //SPECTRAL HELM(3)
                    if (player.getEquipment().contains(3015)) {
                        modifier += .30;
                    }

                    if (player.getNodesUnlocked() != null) {
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_FURY)) {
                            modifier += .05;
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.FERAL_MASTERY)) {
                            modifier += .05;
                        }
                    }
                }

            }

            maxHit *= modifier;
            maxHit *= (1D+((double)player.getDmgBoost()/100D));
            if (victim != null && victim.isNpc()) {
                maxHit = NpcMaxHitLimit.limit((NPC) victim, maxHit, CombatType.MAGIC);
            }
        }

        return (int) Math.floor(maxHit);
    }

    public static double getEffectiveStr(Player player) {
        double styleBonus = 0;
        FightStyle style = player.getFightType().getStyle();

        double otherBonus = 1;

        double prayerMod = 1.0;
        double random = Math.random() * 10;
        if (PrayerHandler.isActivated(player, PrayerHandler.BURST_OF_STRENGTH)) {
            prayerMod = 1.05;
        } else if (PrayerHandler.isActivated(player, PrayerHandler.SUPERHUMAN_STRENGTH)) {
            prayerMod = 1.1;
        } else if (PrayerHandler.isActivated(player, PrayerHandler.ULTIMATE_STRENGTH)) {
            prayerMod = 1.15;
        } else if (PrayerHandler.isActivated(player, PrayerHandler.CHIVALRY)) {
            prayerMod = 1.18;
        } else if (PrayerHandler.isActivated(player, PrayerHandler.PIETY)) {
            prayerMod = 1.23;
        } else if (CurseHandler.isActivated(player, CurseHandler.DESOLATION)) {
            prayerMod = 1.25;
        }

        int number = (int) (player.getSkillManager().getCurrentLevel(Skill.STRENGTH) * prayerMod * otherBonus + styleBonus);
        return number;
    }

}
