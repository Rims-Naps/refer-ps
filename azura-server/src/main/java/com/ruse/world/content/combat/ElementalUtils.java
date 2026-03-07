package com.ruse.world.content.combat;

import com.ruse.model.Hitmask;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.player.Player;

public class ElementalUtils {

    public enum Elemental {
        APPRENTICE_BLADE_E(5000, 0.001, ElementalTyping.EARTH),
        APPRENTICE_BOW_E(5001, 0.001, ElementalTyping.EARTH),
        APPRENTICE_STAFF_E(5002, 0.001, ElementalTyping.EARTH),
        APPRENTICE_BLADE_W(5003, 0.001, ElementalTyping.WATER),
        APPRENTICE_BOW_W(5004, 0.001, ElementalTyping.WATER),
        APPRENTICE_STAFF_W(5005, 0.001, ElementalTyping.WATER),
        APPRENTICE_BLADE_F(5006, 0.001, ElementalTyping.FIRE),
        APPRENTICE_BOW_F(5007, 0.001, ElementalTyping.FIRE),
        APPRENTICE_STAFF_F(5008, 0.001, ElementalTyping.FIRE),
        CinderHelm(18588, 0.01, ElementalTyping.FIRE),
        CinderBody(18589, 0.01, ElementalTyping.FIRE),
        CinderLegs(18590, 0.01, ElementalTyping.FIRE),
        CinderGloves(18591, 0.01, ElementalTyping.FIRE),
        CinderBoots(18592, 0.01, ElementalTyping.FIRE),
        CinderBlade(16024, 0.01, ElementalTyping.FIRE),
        CinderBow(13042, 0.01, ElementalTyping.FIRE),
        CinderStaff(14915, 0.01, ElementalTyping.FIRE),
//

        StoneWardHelm(15895, 0.01, ElementalTyping.EARTH),
        StoneWardBody(15840, 0.01, ElementalTyping.EARTH),
        StoneWardLegs(15800, 0.01, ElementalTyping.EARTH),
        StoneWardGloves(16188, 0.01, ElementalTyping.EARTH),
        StoneWardBoots(15789, 0.01, ElementalTyping.EARTH),
        StoneWardBlade(12930, 0.01, ElementalTyping.EARTH),
        StoneWardBow(16879, 0.01, ElementalTyping.EARTH),
        StoneWardStaff(23144, 0.01, ElementalTyping.EARTH),
//

        TideWaveHelm(15645, 0.01, ElementalTyping.WATER),
        TideWaveBody(15646, 0.01, ElementalTyping.WATER),
        TideWaveLegs(15647, 0.01, ElementalTyping.WATER),
        TideWaveGloves(9057, 0.01, ElementalTyping.WATER),
        TideWaveBoots(9058, 0.01, ElementalTyping.WATER),
        TideWaveBlade(23145, 0.01, ElementalTyping.WATER),
        TideWaveBow(23146, 0.01, ElementalTyping.WATER),
        TideWaveStaff(23033, 0.01, ElementalTyping.WATER),
//

        VerdantHelm(23139, 0.01, ElementalTyping.EARTH),
        VerdantBody(23140, 0.01, ElementalTyping.EARTH),
        VerdantLegs(23141, 0.01, ElementalTyping.EARTH),
        VerdantGloves(23142, 0.01, ElementalTyping.EARTH),
        VerdantBoots(23143, 0.01, ElementalTyping.EARTH),
        VerdantAxe(18332, 0.01, ElementalTyping.EARTH),
        VerdantBow(3745, 0.01, ElementalTyping.EARTH),
        VerdantStaff(3743, 0.01, ElementalTyping.EARTH),
//
        FlameStrikeHelm(13051, 0.01, ElementalTyping.FIRE),
        FlameStrikeBody(13052, 0.01, ElementalTyping.FIRE),
        FlameStrikeLegs(13053, 0.01, ElementalTyping.FIRE),
        FlameStrikeGloves(13054, 0.01, ElementalTyping.FIRE),
        FlameStrikeBoots(13055, 0.01, ElementalTyping.FIRE),
        FlameStrikeBlade(23039, 0.01, ElementalTyping.FIRE),
        FlameStrikeBow(23064, 0.01, ElementalTyping.FIRE),
        FlameStrikeStaff(23065, 0.01, ElementalTyping.FIRE),
//
        WaveBreakerHelm(17608, 0.01, ElementalTyping.WATER),
        WaveBreakerBody(17610, 0.01, ElementalTyping.WATER),
        WaveBreakerLegs(17612, 0.01, ElementalTyping.WATER),
        WaveBreakerGloves(5071, 0.01, ElementalTyping.WATER),
        WaveBreakerBoots(5072, 0.01, ElementalTyping.WATER),
        WaveBreakerBlade(23066, 0.01, ElementalTyping.WATER),
        WaveBreakerBow(23067, 0.01, ElementalTyping.WATER),
        WaveBreakerStaff(14065, 0.01, ElementalTyping.WATER),
//
        MagmiteHelm(13044, 0.01, ElementalTyping.FIRE),
        MagmiteBody(13045, 0.01, ElementalTyping.FIRE),
        MagmiteLegs(13046, 0.01, ElementalTyping.FIRE),
        MagmiteGloves(13047, 0.01, ElementalTyping.FIRE),
        MagmiteBoots(13048, 0.01, ElementalTyping.FIRE),
        MagmiteBlade(14004, 0.01, ElementalTyping.FIRE),
        MagmiteBow(18799, 0.01, ElementalTyping.FIRE),
        MagmiteStaff(5010, 0.01, ElementalTyping.FIRE),
//
        MossHeartHelm(19811, 0.01, ElementalTyping.EARTH),
        MossHeartBody(19473, 0.01, ElementalTyping.EARTH),
        MossHeartLegs(19472, 0.01, ElementalTyping.EARTH),
        MossHeartGloves(19946, 0.01, ElementalTyping.EARTH),
        MossHeartBoots(19945, 0.01, ElementalTyping.EARTH),
        MossHeartBlade(23055, 0.01, ElementalTyping.EARTH),
        MossHeartBow(23056, 0.01, ElementalTyping.EARTH),
        MossHeartStaff(13943, 0.01, ElementalTyping.EARTH),
//
        SurgeHelm(21015, 0.01, ElementalTyping.WATER),
        SurgeBody(21016, 0.01, ElementalTyping.WATER),
        SurgeLegs(21017, 0.01, ElementalTyping.WATER),
        SurgeGloves(21040, 0.01, ElementalTyping.WATER),
        SurgeBoots(21041, 0.01, ElementalTyping.WATER),
        SurgeBlade(13049, 0.01, ElementalTyping.WATER),
        SurgeBow(13056, 0.01, ElementalTyping.WATER),
        SurgeStaff(13063, 0.01, ElementalTyping.WATER),
//

//
        MossBornHelm(23134, 0.01, ElementalTyping.EARTH),
        MossBornBody(23135, 0.01, ElementalTyping.EARTH),
        MossBornLegs(23136, 0.01, ElementalTyping.EARTH),
        MossBornGloves(23137, 0.01, ElementalTyping.EARTH),
        MossBornBoots(23138, 0.01, ElementalTyping.EARTH),
        MossBornBlade(13015, 0.01, ElementalTyping.EARTH),
        MossBornBow(13022, 0.01, ElementalTyping.EARTH),
        MossBornStaff(13029, 0.01, ElementalTyping.EARTH),
//
        PulsarHelm(523, 0.01, ElementalTyping.WATER),
        PulsarBody(524, 0.01, ElementalTyping.WATER),
        PulsarLegs(525, 0.01, ElementalTyping.WATER),
        PulsarGloves(12860, 0.01, ElementalTyping.WATER),
        PulsarBoots(12565, 0.01, ElementalTyping.WATER),
        PulsarBlade(13035, 0.01, ElementalTyping.WATER),
        PulsarBow(13964, 0.01, ElementalTyping.WATER),
        PulsarStaff(21070, 0.01, ElementalTyping.WATER),
//
        MoltarokHelm(13065, 0.01, ElementalTyping.FIRE),
        MoltarokBody(13066, 0.01, ElementalTyping.FIRE),
        MoltarokLegs(13067, 0.01, ElementalTyping.FIRE),
        MoltarokGloves(13068, 0.01, ElementalTyping.FIRE),
        MoltarokBoots(13069, 0.01, ElementalTyping.FIRE),
        MoltarokBlade(18629, 0.01, ElementalTyping.FIRE),
        MoltarokBow(17714, 0.01, ElementalTyping.FIRE),
        MoltarokStaff(15485, 0.01, ElementalTyping.FIRE),
//
        CliffbreakerHelm(19828, 0.01, ElementalTyping.EARTH),
        CliffbreakerBody(19823, 0.01, ElementalTyping.EARTH),
        CliffbreakerLegs(19822, 0.01, ElementalTyping.EARTH),
        CliffbreakerGloves(19815, 0.01, ElementalTyping.EARTH),
        CliffbreakerBoots(19817, 0.01, ElementalTyping.EARTH),
        CliffbreakerBlade(16867, 0.01, ElementalTyping.EARTH),
        CliffbreakerBow(16337, 0.01, ElementalTyping.EARTH),
        CliffbreakerStaff(17620, 0.01, ElementalTyping.EARTH),
//
        TidalHelm(11009, 0.01, ElementalTyping.WATER),
        TidalBody(11010, 0.01, ElementalTyping.WATER),
        TidalLegs(11011, 0.01, ElementalTyping.WATER),
        TidalGloves(21066, 0.01, ElementalTyping.WATER),
        TidalBoots(21067, 0.01, ElementalTyping.WATER),
        TidalAxe(20171, 0.01, ElementalTyping.WATER),
        TidalBow(21023, 0.01, ElementalTyping.WATER),
        TidalStaff(16875, 0.01, ElementalTyping.WATER),
//
        MercurialHelm(18937, 0.01, ElementalTyping.FIRE),
        MercurialBody(18938, 0.01, ElementalTyping.FIRE),
        MercurialLegs(18939, 0.01, ElementalTyping.FIRE),
        MercurialGloves(18970, 0.01, ElementalTyping.FIRE),
        MercurialBoots(18971, 0.01, ElementalTyping.FIRE),
        MercurialBlade(14005, 0.01, ElementalTyping.FIRE),
        MercurialBow(18749, 0.01, ElementalTyping.FIRE),
        MercurialStaff(18748, 0.01, ElementalTyping.FIRE),
//
        VireHelm(23034, 0.01, ElementalTyping.EARTH),
        VireBody(23035, 0.01, ElementalTyping.EARTH),
        VireLegs(23036, 0.01, ElementalTyping.EARTH),
        VireGloves(23037, 0.01, ElementalTyping.EARTH),
        VireBoots(23038, 0.01, ElementalTyping.EARTH),
        VireBlade(18638, 0.01, ElementalTyping.EARTH),
        VireBow(12994, 0.01, ElementalTyping.EARTH),
        VireStaff(18009, 0.01, ElementalTyping.EARTH),
//
        SeafireHelm(23021, 0.01, ElementalTyping.WATER),
        SeafireBody(23022, 0.01, ElementalTyping.WATER),
        SeafireLegs(23023, 0.01, ElementalTyping.WATER),
        SeafireGloves(23024, 0.01, ElementalTyping.WATER),
        SeafireBoots(23025, 0.01, ElementalTyping.WATER),
        SeafireBlade(8087, 0.01, ElementalTyping.WATER),
        SeafireBow(16871, 0.01, ElementalTyping.WATER),
        SeafireStaff(14006, 0.01, ElementalTyping.WATER),
//
        TorridHelm(13010, 0.01, ElementalTyping.FIRE),
        TorridBody(13011, 0.01, ElementalTyping.FIRE),
        TorridLegs(13012, 0.01, ElementalTyping.FIRE),
        TorridGloves(13013, 0.01, ElementalTyping.FIRE),
        TorridBoots(13014, 0.01, ElementalTyping.FIRE),
        TorridBlade(13328, 0.01, ElementalTyping.FIRE),
        TorridBow(13329, 0.01, ElementalTyping.FIRE),
        TorridStaff(13330, 0.01, ElementalTyping.FIRE),
//
        //ELITE
        AquarionHelm(17065, 0.01, ElementalTyping.WATER),
        AquarionBody(17066, 0.01, ElementalTyping.WATER),
        AquarionLegs(17067, 0.01, ElementalTyping.WATER),
        AquarionGloves(17068, 0.01, ElementalTyping.WATER),
        AquarionBoots(17069, 0.01, ElementalTyping.WATER),
        AquarionWeapon(17108, 0.01, ElementalTyping.WATER),

        IgnitionHelm(17070, 0.01, ElementalTyping.FIRE),
        IgnitionBody(17071, 0.01, ElementalTyping.FIRE),
        IgnitionLegs(17072, 0.01, ElementalTyping.FIRE),
        IgnitionGloves(17073, 0.01, ElementalTyping.FIRE),
        IgnitionBoots(17074, 0.01, ElementalTyping.FIRE),
        IgnitionWeapon(17109, 0.01, ElementalTyping.FIRE),

        BedrockHelm(17075, 0.01, ElementalTyping.EARTH),
        BedrockBody(17076, 0.01, ElementalTyping.EARTH),
        BedrockLegs(17077, 0.01, ElementalTyping.EARTH),
        BedrockGloves(17078, 0.01, ElementalTyping.EARTH),
        BedrockBoots(17079, 0.01, ElementalTyping.EARTH),
        BedrockWeapon(17110, 0.01, ElementalTyping.EARTH),

        CascadeHelm(17081, 0.01, ElementalTyping.WATER),
        CascadeBody(17083, 0.01, ElementalTyping.WATER),
        CascadeLegs(17084, 0.01, ElementalTyping.WATER),
        CascadeGloves(17085, 0.01, ElementalTyping.WATER),
        CascadeBoots(17086, 0.01, ElementalTyping.WATER),
        CascadeWeapon(17111, 0.01, ElementalTyping.WATER),

        EmberglowHelm(17086, 0.01, ElementalTyping.FIRE),
        EmberGlowBody(17087, 0.01, ElementalTyping.FIRE),
        EmberGlowLegs(17088, 0.01, ElementalTyping.FIRE),
        EmberGlowGloves(17089, 0.01, ElementalTyping.FIRE),
        EmberGlowBoots(17090, 0.01, ElementalTyping.FIRE),
        EmberGlowWeapon(17112, 0.01, ElementalTyping.FIRE),

        TerravaultHelm(17091, 0.01, ElementalTyping.EARTH),
        TerravaultBody(17092, 0.01, ElementalTyping.EARTH),
        TerravaultLegs(17093, 0.01, ElementalTyping.EARTH),
        TerravaultGloves(17094, 0.01, ElementalTyping.EARTH),
        TerravaultBoots(17095, 0.01, ElementalTyping.EARTH),
        TerravaultWeapon(17113, 0.01, ElementalTyping.EARTH),

        PoseidonHelm(17096, 0.01, ElementalTyping.WATER),
        PoseidonBody(17097, 0.01, ElementalTyping.WATER),
        PoseidonLegs(17098, 0.01, ElementalTyping.WATER),
        PoseidonGloves(17099, 0.01, ElementalTyping.WATER),
        PoseidonBoots(17100, 0.01, ElementalTyping.WATER),
        PoseidonWeapon(17114, 0.01, ElementalTyping.WATER),

        BlazenHelm(13058, 0.01, ElementalTyping.FIRE),
        BlazenBody(13059, 0.01, ElementalTyping.FIRE),
        BlazenLegs(13060, 0.01, ElementalTyping.FIRE),
        BlazenGloves(13061, 0.01, ElementalTyping.FIRE),
        BlazenBoots(13062, 0.01, ElementalTyping.FIRE),
        BlazenRapier(18974, 0.01, ElementalTyping.FIRE),
        BlazenBow(18593, 0.01, ElementalTyping.FIRE),
        BlazenStaff(18972, 0.01, ElementalTyping.FIRE),
        
        WardenHelm(23028, 0.01, ElementalTyping.EARTH),
        WardenBody(23029, 0.01, ElementalTyping.EARTH),
        WardenLegs(23030, 0.01, ElementalTyping.EARTH),
        WardenGloves(23031, 0.01, ElementalTyping.EARTH),
        WardenBoots(23032, 0.01, ElementalTyping.EARTH),
        WardenBlade(16137, 0.01, ElementalTyping.EARTH),
        WardenBow(16873, 0.01, ElementalTyping.EARTH),
        WardenStaff(12902, 0.01, ElementalTyping.EARTH),

        TerraSteelHelmet(1470, 0.01, ElementalTyping.EARTH),
        TerraSteelBody(1471, 0.01, ElementalTyping.EARTH),
        TerraSteelLegs(1472, 0.01, ElementalTyping.EARTH),
        TerraSteelGloves(1473, 0.01, ElementalTyping.EARTH),
        TerraSteelBoots(1474, 0.01, ElementalTyping.EARTH),
        TerraSteelStaff(1475, 0.01, ElementalTyping.EARTH),

        AshfireHelmet(1476, 0.01, ElementalTyping.FIRE),
        AshfireBody(1477, 0.01, ElementalTyping.FIRE),
        AshfireLegs(1478, 0.01, ElementalTyping.FIRE),
        AshfireGloves(1479, 0.01, ElementalTyping.FIRE),
        AshfireBoots(1480, 0.01, ElementalTyping.FIRE),
        AshfireBow(1481, 0.01, ElementalTyping.FIRE),

        WavecrestHelmet(1482, 0.01, ElementalTyping.WATER),
        WavecrestBody(1483, 0.01, ElementalTyping.WATER),
        WavecrestLegs(1484, 0.01, ElementalTyping.WATER),
        WavecrestGloves(1485, 0.01, ElementalTyping.WATER),
        WavecrestBoots(1523, 0.01, ElementalTyping.WATER),
        WavecrestBlade(1487, 0.01, ElementalTyping.WATER),

        StoneCloakHelmet(1488, 0.01, ElementalTyping.EARTH),
        StoneCloakBody(1489, 0.01, ElementalTyping.EARTH),
        StoneCloakLegs(1490, 0.01, ElementalTyping.EARTH),
        StoneCloakGloves(1491, 0.01, ElementalTyping.EARTH),
        StoneCloakBoots(1492, 0.01, ElementalTyping.EARTH),
        StoneCloakBow(1493, 0.01, ElementalTyping.EARTH),

        FirelashHelmet(1494, 0.01, ElementalTyping.FIRE),
        FirelashBody(1495, 0.01, ElementalTyping.FIRE),
        FirelashLegs(1496, 0.01, ElementalTyping.FIRE),
        FirelashGloves(1497, 0.01, ElementalTyping.FIRE),
        FirelashBoots(1498, 0.01, ElementalTyping.FIRE),
        FirelashStaff(1499, 0.01, ElementalTyping.FIRE),

        NauticHelmet(1500, 0.01, ElementalTyping.WATER),
        NauticBody(1501, 0.01, ElementalTyping.WATER),
        NauticLegs(1502, 0.01, ElementalTyping.WATER),
        NauticGloves(1503, 0.01, ElementalTyping.WATER),
        NauticBoots(1504, 0.01, ElementalTyping.WATER),
        NauticBow(1505, 0.01, ElementalTyping.WATER),

        RidgeHelmet(1506, 0.01, ElementalTyping.EARTH),
        RidgeBody(1507, 0.01, ElementalTyping.EARTH),
        RidgeLegs(1508, 0.01, ElementalTyping.EARTH),
        RidgeGloves(1509, 0.01, ElementalTyping.EARTH),
        RidgeBoots(1510, 0.01, ElementalTyping.EARTH),
        RidgeBlade(1511, 0.01, ElementalTyping.EARTH),

        IncendraHelmet(1512, 0.01, ElementalTyping.FIRE),
        IncendraBody(1513, 0.01, ElementalTyping.FIRE),
        IncendraLegs(1514, 0.01, ElementalTyping.FIRE),
        IncendraGloves(1515, 0.01, ElementalTyping.FIRE),
        IncendraBoots(1516, 0.01, ElementalTyping.FIRE),
        IncendraStaff(1517, 0.01, ElementalTyping.FIRE),

        AzureHelmet(1518, 0.01, ElementalTyping.WATER),
        AzureBody(1519, 0.01, ElementalTyping.WATER),
        AzureLegs(1520, 0.01, ElementalTyping.WATER),
        AzureGloves(1521, 0.01, ElementalTyping.WATER),
        AzureBoots(1522, 0.01, ElementalTyping.WATER),
        AzureStaff(1524, 0.01, ElementalTyping.WATER),

        VoidBlade1(20000, 0.01, ElementalTyping.VOID),
        VoidRange1(20001, 0.01, ElementalTyping.VOID),
        VoidStaff1(20002, 0.01, ElementalTyping.VOID),


        SPECTRAL1(2086, 0.01, ElementalTyping.VOID),
        SPECTRAL2(2087, 0.01, ElementalTyping.VOID),
        SPECTRAL3(2088, 0.01, ElementalTyping.VOID),




        //MAGMA
        MagmaBlade1(1560, 0.03, ElementalTyping.FIRE),
        MagmaBlade2(1561, 0.04, ElementalTyping.FIRE),
        MagmaBlade3(1562, 0.05, ElementalTyping.FIRE),

        MagmaBow1(1570, 0.03, ElementalTyping.FIRE),
        MagmaBow2(1571, 0.04, ElementalTyping.FIRE),
        MagmaBow3(1572, 0.05, ElementalTyping.FIRE),

        MagmaStaff1(1580, 0.03, ElementalTyping.FIRE),
        MagmaStaff2(1581, 0.04, ElementalTyping.FIRE),
        MagmaStaff3(1582, 0.05, ElementalTyping.FIRE),

        //AQUATIC
        AquaticBlade1(1563, 0.03, ElementalTyping.WATER),
        AquaticBlade2(1564, 0.04, ElementalTyping.WATER),
        AquaticBlade3(1565, 0.05, ElementalTyping.WATER),

        AquaticBow1(1573, 0.03, ElementalTyping.WATER),
        AquaticBow2(1574, 0.04, ElementalTyping.WATER),
        AquaticBow3(1575, 0.05, ElementalTyping.WATER),

        AquaticStaff1(1583, 0.03, ElementalTyping.WATER),
        AquaticStaff2(1584, 0.04, ElementalTyping.WATER),
        AquaticStaff3(1585, 0.05, ElementalTyping.WATER),

        //OVERGROWN
        OvergrownBlade1(1566, 0.03, ElementalTyping.EARTH),
        OvergrownBlade2(1567, 0.04, ElementalTyping.EARTH),
        OvergrownBlade3(1568, 0.05, ElementalTyping.EARTH),

        OvergrownBow1(1576, 0.03, ElementalTyping.EARTH),
        OvergrownBow2(1577, 0.04, ElementalTyping.EARTH),
        OvergrownBow3(1578, 0.05, ElementalTyping.EARTH),

        OvergrownStaff1(1586, 0.03, ElementalTyping.EARTH),
        OvergrownStaff2(1587, 0.04, ElementalTyping.EARTH),
        OvergrownStaff3(1588, 0.05, ElementalTyping.EARTH),

        LAVA_BLASTER(711, 0.01, ElementalTyping.FIRE),
        AQUA_BLASTER(712, 0.01, ElementalTyping.WATER),
        GAIA_BLASTER(713, 0.01, ElementalTyping.EARTH),
        VOID_BLASTER(714, 0.031, ElementalTyping.VOID),


                ;


        private ElementalTyping typing;
        private int id;
        private double modifier;

        Elemental(int itemId, double damageModifier, ElementalTyping type) {
            this.typing = type;
            this.id = itemId;
            this.modifier = damageModifier;
        }

        public int getId() {
            return id;
        }

        public ElementalTyping getTyping() {
            return typing;
        }

        public double getModifier() {
            return modifier;
        }
    }
    public enum ElementalTyping {
        EARTH,
        FIRE,
        WATER,
        VOID,
        NEUTRAL
    }


    public static ElementalTyping getTypingFromBoolean(boolean isFire, boolean isWater, boolean isEarth, boolean isVoid) {
        if (isFire)
            return ElementalTyping.FIRE;
        if (isWater)
            return ElementalTyping.WATER;
        if (isEarth)
            return ElementalTyping.EARTH;
        if (isVoid)
            return ElementalTyping.VOID;
        return ElementalTyping.NEUTRAL;
    }

    public static Hitmask getTypingForHit(Player player) {
        for (Elemental ele : Elemental.values()) {
            if (player.getEquipment().get(3).getId() == ele.getId()) {
                if (ele.getTyping().equals(ElementalTyping.FIRE)) {
                    return Hitmask.FIRE;
                }
                if (ele.getTyping().equals(ElementalTyping.EARTH)) {
                    return Hitmask.EARTH;
                }
                if (ele.getTyping().equals(ElementalTyping.WATER)) {
                    return Hitmask.WATER;
                }
                if (ele.getTyping().equals(ElementalTyping.VOID)) {
                    return Hitmask.VOID;
                }
            }
        }
        return Hitmask.NEUTRAL;
    }

    public static double getTotalModifier(Player player, boolean isFire, boolean isWater, boolean isEarth, boolean isVoid) {
        double modifier = 0;
        ElementalTyping npcTyping = getTypingFromBoolean(isFire, isWater, isEarth, isVoid);
        for (Elemental ele : Elemental.values()) {
            if (player.getEquipment().contains(ele.getId())) {
                if (isEffective(ele.getTyping(), npcTyping)) {
                    modifier += ele.getModifier();
                    if (player.getNodesUnlocked() != null) {
                        if (player.getSkillTree().isNodeUnlocked(Node.INFERNAL_BLAZE)) {
                            if (ele.getTyping().equals(ElementalTyping.FIRE)) {
                                modifier += 0.1;
                            }
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.CALL_TO_GAIA)) {
                            if (ele.getTyping().equals(ElementalTyping.EARTH)) {
                                modifier += 0.1;
                            }
                        }
                        if (player.getSkillTree().isNodeUnlocked(Node.AQUATIC_SURGE)) {
                            if (ele.getTyping().equals(ElementalTyping.WATER)) {
                                modifier += 0.1;
                            }
                        }
                    }
                }
            }
        }
        return modifier;
    }

    public static boolean isEffective(ElementalTyping type1, ElementalTyping type2) {
        switch(type1) {
            case FIRE:
                switch(type2) {
                    case FIRE:
                    case WATER:
                        return false;
                    case EARTH:
                        return true;
                }
            break;
            case WATER:
                switch(type2) {
                    case EARTH:
                    case WATER:
                        return false;
                    case FIRE:
                        return true;
                }
            break;
            case EARTH:
                switch(type2) {
                    case EARTH:
                    case FIRE:
                        return false;
                    case WATER:
                        return true;
                }
            break;
        }
        return true;
    }
}
