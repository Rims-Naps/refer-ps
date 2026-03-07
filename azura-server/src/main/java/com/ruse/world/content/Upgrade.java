package com.ruse.world.content;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.entity.impl.player.Player;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Upgrade {
    private final Player p;

    private final int EFFIGY = 1526;

    private Type type;
    private Upgradeable selection;
    private CopyOnWriteArrayList<Item> arr;
    private CopyOnWriteArrayList<Item> arr2;

    public enum Type {
        Weaponry, Armoury, Misc;
    }

    public enum Upgradeable {

        //SLAYER HELMETS
        GAIA_SLAYER_HELM(new Item(22002, 1), new Item(12830, 1), new Item(12834, 1), new Item(3576, 3500), 100, 12500, Type.Armoury, true, null),
        AQUA_SLAYER_HELM(new Item(22001, 1), new Item(12831, 1), new Item(12835, 1), new Item(3576, 3500), 100, 12500, Type.Armoury, true, null),
        LAVA_SLAYER_HELM(new Item(22000, 1), new Item(12832, 1), new Item(12836, 1), new Item(3576, 3500), 100, 12500, Type.Armoury, true, null),
        VOID_SLAYER_HELM(new Item(22003, 1), new Item(12465, 1), new Item(12463, 1), new Item(12461, 1), 100, 75000, Type.Armoury, true, null),

        CORRUPT_SLAYER_HELM(new Item(2677, 1), new Item(3503, 1), new Item(3504, 1), new Item(3502, 1000), 100, 200000, Type.Armoury, true, new Item(12467,1)),

        AQUATIC_SLAYER_HELM(new Item(3010, 1), new Item(12424, 15), new Item(2061, 3), new Item(2064, 10000), 100, 500000, Type.Armoury, true, new Item(22001,1)),
        MAGMA_SLAYER_HELM(new Item(3011, 1), new Item(12427, 15), new Item(2061, 3), new Item(2064, 10000), 100, 500000, Type.Armoury, true, new Item(22000,1)),
        OVERGROWN_SLAYER_HELM(new Item(3012, 1), new Item(12423, 15), new Item(2061, 3), new Item(2064, 10000), 100, 500000, Type.Armoury, true, new Item(22002,1)),

        SPECTRAL_SLAYER_HELM(new Item(3013, 1), new Item(3010, 1), new Item(3011, 1), new Item(3012, 1), 100, 1000000, Type.Armoury, true, new Item(2679,1)),



        // ARMOR TIERS WITH PREVIOUS ITEM REQUIREMENTS
        HELM_1(new Item(17024, 1), new Item(18588, 1), new Item(401, 1), new Item(15645, 1), 100, 5000, Type.Armoury, true, null),
        BODY_1(new Item(17025, 1), new Item(18589, 1), new Item(402, 1), new Item(15646, 1), 100, 5000, Type.Armoury, true, null),
        LEGS_1(new Item(17026, 1), new Item(18590, 1), new Item(403, 1), new Item(15647, 1), 100, 5000, Type.Armoury, true, null),
        GLOVES_1(new Item(17027, 1), new Item(18591, 1), new Item(404, 1), new Item(9057, 1), 100, 5000, Type.Armoury, true, null),
        BOOTS_1(new Item(17028, 1), new Item(18592, 1), new Item(405, 1), new Item(9058, 1), 100, 5000, Type.Armoury, true, null),

        HELM_2(new Item(17029, 1), new Item(13051, 1), new Item(401, 1), new Item(17608, 1), 100, 10000, Type.Armoury, true, new Item(17024, 1)),
        BODY_2(new Item(17030, 1), new Item(13052, 1), new Item(402, 1), new Item(17610, 1), 100, 10000, Type.Armoury, true, new Item(17025, 1)),
        LEGS_2(new Item(17031, 1), new Item(13053, 1), new Item(403, 1), new Item(17612, 1), 100, 10000, Type.Armoury, true, new Item(17026, 1)),
        GLOVES_2(new Item(17032, 1), new Item(13054, 1), new Item(404, 1), new Item(5071, 1), 100, 10000, Type.Armoury, true, new Item(17027, 1)),
        BOOTS_2(new Item(17033, 1), new Item(13055, 1), new Item(405, 1), new Item(5072, 1), 100, 10000, Type.Armoury, true, new Item(17028, 1)),

        HELM_3(new Item(17034, 1), new Item(13044, 1), new Item(401, 2), new Item(21015, 1), 100, 15000, Type.Armoury, true, new Item(17029, 1)),
        BODY_3(new Item(17035, 1), new Item(13045, 1), new Item(402, 2), new Item(21016, 1), 100, 15000, Type.Armoury, true, new Item(17030, 1)),
        LEGS_3(new Item(17036, 1), new Item(13046, 1), new Item(403, 2), new Item(21017, 1), 100, 15000, Type.Armoury, true, new Item(17031, 1)),
        GLOVES_3(new Item(17037, 1), new Item(13047, 1), new Item(404, 2), new Item(21040, 1), 100, 15000, Type.Armoury, true, new Item(17032, 1)),
        BOOTS_3(new Item(17038, 1), new Item(13048, 1), new Item(405, 2), new Item(21041, 1), 100, 15000, Type.Armoury, true, new Item(17033, 1)),

        HELM_4(new Item(17039, 1), new Item(23134, 1), new Item(401, 2), new Item(523, 1), 100, 20000, Type.Armoury, true, new Item(17034, 1)),
        BODY_4(new Item(17040, 1), new Item(23135, 1), new Item(402, 2), new Item(524, 1), 100, 20000, Type.Armoury, true, new Item(17035, 1)),
        LEGS_4(new Item(17041, 1), new Item(23136, 1), new Item(403, 2), new Item(525, 1), 100, 20000, Type.Armoury, true, new Item(17036, 1)),
        GLOVES_4(new Item(17042, 1), new Item(23137, 1), new Item(404, 2), new Item(12860, 1), 100, 20000, Type.Armoury, true, new Item(17037, 1)),
        BOOTS_4(new Item(17043, 1), new Item(23138, 1), new Item(405, 2), new Item(12565, 1), 100, 20000, Type.Armoury, true, new Item(17038, 1)),

        HELM_5(new Item(17044, 1), new Item(13065, 1), new Item(406, 1), new Item(11009, 1), 100, 25000, Type.Armoury, true, new Item(17039, 1)),
        BODY_5(new Item(17045, 1), new Item(13066, 1), new Item(407, 1), new Item(11010, 1), 100, 25000, Type.Armoury, true, new Item(17040, 1)),
        LEGS_5(new Item(17046, 1), new Item(13067, 1), new Item(408, 1), new Item(11011, 1), 100, 25000, Type.Armoury, true, new Item(17041, 1)),
        GLOVES_5(new Item(17047, 1), new Item(13068, 1), new Item(409, 1), new Item(21066, 1), 100, 25000, Type.Armoury, true, new Item(17042, 1)),
        BOOTS_5(new Item(17048, 1), new Item(13069, 1), new Item(410, 1), new Item(21067, 1), 100, 25000, Type.Armoury, true, new Item(17043, 1)),

        HELM_6(new Item(17049, 1), new Item(23034, 1), new Item(406, 1), new Item(23021, 1), 100, 35000, Type.Armoury, true, new Item(17044, 1)),
        BODY_6(new Item(17050, 1), new Item(23035, 1), new Item(407, 1), new Item(23022, 1), 100, 35000, Type.Armoury, true, new Item(17045, 1)),
        LEGS_6(new Item(17051, 1), new Item(23036, 1), new Item(408, 1), new Item(23023, 1), 100, 35000, Type.Armoury, true, new Item(17046, 1)),
        GLOVES_6(new Item(17052, 1), new Item(23037, 1), new Item(409, 1), new Item(23024, 1), 100, 35000, Type.Armoury, true, new Item(17047, 1)),
        BOOTS_6(new Item(17053, 1), new Item(23038, 1), new Item(410, 1), new Item(23025, 1), 100, 30000, Type.Armoury, true, new Item(17048, 1)),

        HELM_7(new Item(17054, 1), new Item(13010, 1), new Item(406, 2), new Item(23034, 1), 100, 50000, Type.Armoury, true, new Item(17049, 1)),
        BODY_7(new Item(17055, 1), new Item(13011, 1), new Item(407, 2), new Item(23035, 1), 100, 50000, Type.Armoury, true, new Item(17050, 1)),
        LEGS_7(new Item(17056, 1), new Item(13012, 1), new Item(408, 2), new Item(23036, 1), 100, 50000, Type.Armoury, true, new Item(17051, 1)),
        GLOVES_7(new Item(17057, 1), new Item(13013, 1), new Item(409, 2), new Item(23037, 1), 100, 50000, Type.Armoury, true, new Item(17052, 1)),
        BOOTS_7(new Item(17058, 1), new Item(13014, 1), new Item(410, 2), new Item(23038, 1), 100, 50000, Type.Armoury, true, new Item(17053, 1)),

        HELM_8(new Item(12300, 1), new Item(17070, 1), new Item(406, 2), new Item(17075, 1), 100, 75000, Type.Armoury, true, new Item(17054, 1)),
        BODY_8(new Item(12301, 1), new Item(17071, 1), new Item(407, 2), new Item(17076, 1), 100, 75000, Type.Armoury, true, new Item(17055, 1)),
        LEGS_8(new Item(12302, 1), new Item(17072, 1), new Item(408, 2), new Item(17077, 1), 100, 75000, Type.Armoury, true, new Item(17056, 1)),
        GLOVES_8(new Item(12303, 1), new Item(17073, 1), new Item(409, 2), new Item(17078, 1), 100, 75000, Type.Armoury, true, new Item(17057, 1)),
        BOOTS_8(new Item(12304, 1), new Item(17074, 1), new Item(410, 2), new Item(17079, 1), 100, 75000, Type.Armoury, true, new Item(17058, 1)),

        HELM_9(new Item(12305, 1), new Item(17081, 1), new Item(406, 3), new Item(17091, 1), 100, 95000, Type.Armoury, true, new Item(12300, 1)),
        BODY_9(new Item(12306, 1), new Item(17082, 1), new Item(407, 3), new Item(17092, 1), 100, 95000, Type.Armoury, true, new Item(12301, 1)),
        LEGS_9(new Item(12307, 1), new Item(17083, 1), new Item(408, 3), new Item(17093, 1), 100, 95000, Type.Armoury, true, new Item(12302, 1)),
        GLOVES_9(new Item(12308, 1), new Item(17084, 1), new Item(409, 3), new Item(17094, 1), 100, 95000, Type.Armoury, true, new Item(12303, 1)),
        BOOTS_9(new Item(12309, 1), new Item(17085, 1), new Item(410, 3), new Item(17095, 1), 100, 95000, Type.Armoury, true, new Item(12304, 1)),

        HELM_10(new Item(12310, 1), new Item(17096, 1), new Item(411, 1), new Item(23028, 1), 100, 125000, Type.Armoury, true, new Item(12305, 1)),
        BODY_10(new Item(12311, 1), new Item(17097, 1), new Item(412, 1), new Item(23029, 1), 100, 125000, Type.Armoury, true, new Item(12306, 1)),
        LEGS_10(new Item(12312, 1), new Item(17098, 1), new Item(413, 1), new Item(23030, 1), 100, 125000, Type.Armoury, true, new Item(12307, 1)),
        GLOVES_10(new Item(12313, 1), new Item(17099, 1), new Item(414, 1), new Item(23031, 1), 100, 125000, Type.Armoury, true, new Item(12308, 1)),
        BOOTS_10(new Item(12314, 1), new Item(17100, 1), new Item(415, 1), new Item(23032, 1), 100, 125000, Type.Armoury, true, new Item(12309, 1)),

        HELM_11(new Item(12315, 1), new Item(1476, 1), new Item(411, 1), new Item(1482, 1), 100, 150000, Type.Armoury, true, new Item(12310, 1)),
        BODY_11(new Item(12316, 1), new Item(1477, 1), new Item(412, 1), new Item(1483, 1), 100, 150000, Type.Armoury, true, new Item(12311, 1)),
        LEGS_11(new Item(12317, 1), new Item(1478, 1), new Item(413, 1), new Item(1484, 1), 100, 150000, Type.Armoury, true, new Item(12312, 1)),
        GLOVES_11(new Item(12318, 1), new Item(1479, 1), new Item(414, 1), new Item(1485, 1), 100, 150000, Type.Armoury, true, new Item(12313, 1)),
        BOOTS_11(new Item(12319, 1), new Item(1480, 1), new Item(415, 1), new Item(1523, 1), 100, 150000, Type.Armoury, true, new Item(12314, 1)),

        HELM_12(new Item(12320, 1), new Item(1488, 1), new Item(411, 2), new Item(1500, 1), 100, 175000, Type.Armoury, true, new Item(12315, 1)),
        BODY_12(new Item(12321, 1), new Item(1489, 1), new Item(412, 2), new Item(1501, 1), 100, 175000, Type.Armoury, true, new Item(12316, 1)),
        LEGS_12(new Item(12322, 1), new Item(1490, 1), new Item(413, 2), new Item(1502, 1), 100, 175000, Type.Armoury, true, new Item(12317, 1)),
        GLOVES_12(new Item(12323, 1), new Item(1491, 1), new Item(414, 2), new Item(1503, 1), 100, 175000, Type.Armoury, true, new Item(12318, 1)),
        BOOTS_12(new Item(12324, 1), new Item(1492, 1), new Item(415, 2), new Item(1504, 1), 100, 175000, Type.Armoury, true, new Item(12319, 1)),

        HELM_13(new Item(12325, 1), new Item(1512, 1), new Item(411, 2), new Item(1518, 1), 100, 200000, Type.Armoury, true, new Item(12320, 1)),
        BODY_13(new Item(12326, 1), new Item(1513, 1), new Item(412, 2), new Item(1519, 1), 100, 200000, Type.Armoury, true, new Item(12321, 1)),
        LEGS_13(new Item(12327, 1), new Item(1514, 1), new Item(413, 2), new Item(1520, 1), 100, 200000, Type.Armoury, true, new Item(12322, 1)),
        GLOVES_13(new Item(12328, 1), new Item(1515, 1), new Item(414, 2), new Item(1521, 1), 100, 200000, Type.Armoury, true, new Item(12323, 1)),
        BOOTS_13(new Item(12329, 1), new Item(1516, 1), new Item(415, 2), new Item(1522, 1), 100, 200000, Type.Armoury, true, new Item(12324, 1)),

        SPECTRAL_HELM_1(new Item(2025, 1), new Item(2061, 2), new Item(2043, 1), new Item(2064, 5000), 100, 200000, Type.Armoury, true, new Item(12325, 1)),
        SPECTRAL_BODY_1(new Item(2026, 1), new Item(2061, 2), new Item(2044, 1), new Item(2064, 5000), 100, 200000, Type.Armoury, true, new Item(12326, 1)),
        SPECTRAL_LEGS_1(new Item(2027, 1), new Item(2061, 2), new Item(2045, 1), new Item(2064, 5000), 100, 200000, Type.Armoury, true, new Item(12327, 1)),
        SPECTRAL_GLOVES_1(new Item(2028, 1), new Item(2061, 2), new Item(2046, 1), new Item(2064, 5000), 100, 200000, Type.Armoury, true, new Item(12328, 1)),
        SPECTRAL_BOOTS_1(new Item(2029, 1), new Item(2061, 2), new Item(2047, 1), new Item(2064, 5000), 100, 200000, Type.Armoury, true, new Item(12329, 1)),

        SPECTRAL_HELM_2(new Item(2030, 1), new Item(2061, 5), new Item(2043, 2), new Item(2064, 10000), 100, 200000, Type.Armoury, true, new Item(2025, 1)),
        SPECTRAL_BODY_2(new Item(2031, 1), new Item(2061, 5), new Item(2044, 2), new Item(2064, 10000), 100, 200000, Type.Armoury, true, new Item(2026, 1)),
        SPECTRAL_LEGS_2(new Item(2032, 1), new Item(2061, 5), new Item(2045, 2), new Item(2064, 10000), 100, 200000, Type.Armoury, true, new Item(2027, 1)),
        SPECTRAL_GLOVES_2(new Item(2033, 1), new Item(2061, 5), new Item(2046, 2), new Item(2064, 10000), 100, 200000, Type.Armoury, true, new Item(2028, 1)),
        SPECTRAL_BOOTS_2(new Item(2034, 1), new Item(2061, 5), new Item(2047, 2), new Item(2064, 10000), 100, 200000, Type.Armoury, true, new Item(2029, 1)),

        SPECTRAL_HELM_3(new Item(2035, 1), new Item(2061, 10), new Item(2043, 4), new Item(2064, 15000), 100, 200000, Type.Armoury, true, new Item(2030, 1)),
        SPECTRAL_BODY_3(new Item(2036, 1), new Item(2061, 10), new Item(2044, 4), new Item(2064, 15000), 100, 200000, Type.Armoury, true, new Item(2031, 1)),
        SPECTRAL_LEGS_3(new Item(2037, 1), new Item(2061, 10), new Item(2045, 4), new Item(2064, 15000), 100, 200000, Type.Armoury, true, new Item(2032, 1)),
        SPECTRAL_GLOVES_3(new Item(2038, 1), new Item(2061, 10), new Item(2046, 4), new Item(2064, 15000), 100, 200000, Type.Armoury, true, new Item(2033, 1)),
        SPECTRAL_BOOTS_3(new Item(2039, 1), new Item(2061, 10), new Item(2047, 4), new Item(2064, 15000), 100, 200000, Type.Armoury, true, new Item(2034, 1)),


        // WEAPON TIERS WITH PREVIOUS ITEM REQUIREMENTS

        ENCHANTED_BOW_1(new Item(16415, 1), new Item(16024, 1), new Item(416, 1), new Item(23033, 1), 100, 5000, Type.Weaponry, true, null),
        ENCHANTED_BLADE_2(new Item(16416, 1), new Item(23039, 1), new Item(416, 1), new Item(23067, 1), 100, 7500, Type.Weaponry, true, new Item(16415, 1)),
        ENCHANTED_STAFF_3(new Item(16417, 1), new Item(18799, 1), new Item(416, 2), new Item(13049, 1), 100, 15000, Type.Weaponry, true, new Item(16416, 1)),
        ENCHANTED_BOW_4(new Item(16418, 1), new Item(13022, 1), new Item(416, 2), new Item(21070, 1), 100, 25000, Type.Weaponry, true, new Item(16417, 1)),
        ENCHANTED_BLADE_5(new Item(16419, 1), new Item(15485, 1), new Item(417, 1), new Item(20171, 1), 100, 50000, Type.Weaponry, true, new Item(16418, 1)),
        ENCHANTED_STAFF_6(new Item(16420, 1), new Item(12994, 1), new Item(417, 1), new Item(14006, 1), 100, 75000, Type.Weaponry, true, new Item(16419, 1)),
        ENCHANTED_BOW_7(new Item(16421, 1), new Item(13329, 1), new Item(417, 2), new Item(18638, 1), 100, 100000, Type.Weaponry, true, new Item(16420, 1)),
        ENCHANTED_BLADE_8(new Item(17101, 1), new Item(17109, 1), new Item(417, 2), new Item(17110, 1), 100, 125000, Type.Weaponry, true, new Item(16421, 1)),
        ENCHANTED_BOW_9(new Item(17102, 1), new Item(17112, 1), new Item(417, 3), new Item(17113, 1), 100, 150000, Type.Weaponry, true, new Item(17101, 1)),
        ENCHANTED_BOW_10(new Item(17103, 1), new Item(17114, 1), new Item(418, 1), new Item(16137, 1), 100, 200000, Type.Weaponry, true, new Item(17102, 1)),
        ENCHANTED_BLADE_11(new Item(17104, 1), new Item(1481, 1), new Item(418, 1), new Item(1487, 1), 100, 225000, Type.Weaponry, true, new Item(17103, 1)),
        ENCHANTED_BOW_12(new Item(17105, 1), new Item(1493, 1), new Item(418, 2), new Item(1505, 1), 100, 250000, Type.Weaponry, true, new Item(17104, 1)),
        ENCHANTED_STAFF_13(new Item(17106, 1), new Item(1517, 1), new Item(418, 2), new Item(1524, 1), 100, 300000, Type.Weaponry, true, new Item(17105, 1)),


        SPECTRAL_BOW_1(new Item(2086, 1), new Item(2061, 2), new Item(2040, 2), new Item(19062, 1000), 100, 300000, Type.Weaponry, true, new Item(17106, 1)),
        SPECTRAL_STAFF_2(new Item(2087, 1), new Item(2061, 4), new Item(2040, 4), new Item(19062, 1500), 100, 500000, Type.Weaponry, true, new Item(2086, 1)),
        SPECTRAL_SCYTHE_3(new Item(2088, 1), new Item(2061, 8), new Item(2040, 8), new Item(19062, 2000), 100, 750000, Type.Weaponry, true, new Item(2087, 1)),

        THORNWOOD_AXE(new Item(450, 1), new Item(441, 1), new Item(452, 1), new Item(453, 1), 100, 50000, Type.Weaponry, true, null),
        THORNWOOD_AXE_2(new Item(451, 1), new Item(450, 1), new Item(454, 1), new Item(455, 1), 100, 150000, Type.Weaponry, true, null),


        SPECTRAL_RAID_WEP(new Item(2080, 1), new Item(2049, 1), new Item(2050, 1), new Item(2064, 2500), 100, 100000, Type.Weaponry, true, new Item(2079, 1)),
        SPECTRAL_RAID_WEP_1(new Item(2081, 1), new Item(2051, 1), new Item(2052, 1), new Item(2064, 7500), 100, 250000, Type.Weaponry, true, new Item(2080, 1)),

        MAGMA_BLADE(new Item(1560, 1), new Item(20000, 1), new Item(12427, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),
        OVERGROWN_BLADE(new Item(1566, 1), new Item(20000, 1), new Item(12423, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),
        NAUTIC_BLADE(new Item(1563, 1), new Item(20000, 1), new Item(12424, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),

        MAGMA_BOW(new Item(1570, 1), new Item(20001, 1), new Item(12427, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),
        OVERGROWN_BOW(new Item(1576, 1), new Item(20001, 1), new Item(12423, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),
        NAUTIC_BOW(new Item(1573, 1), new Item(20001, 1), new Item(12424, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),

        MAGMA_STAFF(new Item(1580, 1), new Item(20002, 1), new Item(12427, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),
        OVERGROWN_STAFF(new Item(1586, 1), new Item(20002, 1), new Item(12423, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),
        NAUTIC_STAFF(new Item(1583, 1), new Item(20002, 1), new Item(12424, 10), new Item(19062, 1200), 100, 450000, Type.Weaponry, true,null),

        CORRUPT_BLADE(new Item(2651, 1), new Item(2660, 2), new Item(2657, 1), new Item(19062, 650), 100, 150000, Type.Weaponry, true,null),
        CORRUPT_BOW(new Item(2653, 1), new Item(2661, 2), new Item(2658, 1), new Item(19062, 650), 100, 150000, Type.Weaponry, true,null),
        CORRUPT_STAFF(new Item(2655, 1), new Item(2662, 2), new Item(2659, 1), new Item(19062, 650), 100, 150000, Type.Weaponry, true,null),


        // MISCELLANEOUS UPGRADES
        MELEE_TOTEM_T1(new Item(16517, 1), new Item(16516, 1), new Item(17012, 1), new Item(19062, 200), 100, 10000, Type.Misc, true, null),
        RANGE_TOTEM_T1(new Item(16520, 1), new Item(16516, 1), new Item(17013, 1), new Item(19062, 200), 100, 10000, Type.Misc, true, null),
        MAGIC_TOTEM_T1(new Item(16510, 1), new Item(16516, 1), new Item(17014, 1), new Item(19062, 200), 100, 10000, Type.Misc, true, null),
        MASTER_TOTEM_T1(new Item(16513, 1), new Item(16519, 1), new Item(16509, 1), new Item(16512, 1), 100, 50000, Type.Misc, true, null),
        GAIA_CRYSTAL(new Item(12839, 1), new Item(17011, 1), new Item(17015, 1), new Item(11052, 100), 100, 10000, Type.Misc, true, null),
        AQUA_CRYSTAL(new Item(12841, 1), new Item(17011, 1), new Item(17016, 1), new Item(11056, 100), 100, 10000, Type.Misc, true, null),
        LAVA_CRYSTAL(new Item(12840, 1), new Item(17011, 1), new Item(17017, 1), new Item(11054, 100), 100, 10000, Type.Misc, true, null),
        VOID_CRYSTAL(new Item(12842, 1), new Item(17004, 1), new Item(17006, 1), new Item(17008, 1), 100, 50000, Type.Misc, true, null),
        CORRUPT_CRYSTAL(new Item(763, 1), new Item(17010, 1), new Item(762, 1), new Item(19062, 1500), 100, 250000, Type.Misc, true, null),
        OWNER_BOX_U(new Item(4022, 1), new Item(3578, 3), new Item(19062, 250), new Item(15667, 5), 95, 50000, Type.Misc, true, null),
        CORRUPT_CRATE(new Item(2009, 1), new Item(3512, 25), new Item(19062, 250), new Item(3502, 1000), 95, 50000, Type.Misc, true, null),
        CORRUPT_SLAYER_SKULL(new Item(3500, 1), new Item(4155, 1), new Item(3576, 50000), new Item(19062, 1500), 100, 150000, Type.Misc, true, null),
        GAIA_BOOK(new Item(6805, 1), new Item(20006, 1), new Item(11052, 150), new Item(19062, 100), 100, 2500, Type.Misc, true, null),
        AQUA_BOOK(new Item(6806, 1), new Item(20006, 1), new Item(11056, 150), new Item(19062, 100), 100, 2500, Type.Misc, true, null),
        LAVA_BOOK(new Item(6807, 1), new Item(20006, 1), new Item(11054, 150), new Item(19062, 100), 100, 2500, Type.Misc, true, null),
        VOID_BOOK(new Item(6808, 1), new Item(15701, 1), new Item(15704, 1), new Item(15706, 1), 100, 50000, Type.Misc, true, null),
        CORRUPT_BOOK(new Item(3526, 1), new Item(15708, 1), new Item(3502, 2500), new Item(19062, 1500), 100, 150000, Type.Misc, true, null),
        VOID_CAPE(new Item(15717, 1), new Item(15710, 1), new Item(15713, 1), new Item(15715, 1), 100, 50000, Type.Misc, true, null),
        CORRUPT_CAPE(new Item(3524, 1), new Item(15717, 1), new Item(3502, 2500), new Item(19062, 750), 100, 150000, Type.Misc, true, null),
        VOID_RING(new Item(1531, 1), new Item(7247, 1), new Item(7249, 1), new Item(7245, 1), 100, 200000, Type.Misc, true, null),
        CORRUPT_RING(new Item(2680, 1), new Item(1531, 1), new Item(3502, 5000), new Item(19062, 850), 100, 350000, Type.Misc, true, null),
        VOID_AMULET(new Item(1528, 1), new Item(7238, 1), new Item(7236, 1), new Item(7241, 1), 100, 200000, Type.Misc, true, null),
        CORRUPT_AMULET(new Item(2683, 1), new Item(1528, 1), new Item(3502, 5000), new Item(19062, 850), 100, 350000, Type.Misc, true, null),
        DAMAGE_TOME(new Item(6749, 1), new Item(15386, 1), new Item(2699, 500), new Item(19062, 650), 85, 10000, Type.Misc, true, null),
        DROPRATE_TOME(new Item(13151, 1), new Item(18678, 1), new Item(2699, 500), new Item(19062, 650), 85, 10000, Type.Misc, true, null),
        EXPERIENCE_TOME(new Item(19705, 1), new Item(7871, 1), new Item(2699, 500), new Item(19062, 650), 85, 10000, Type.Misc, true, null),
        NECRO_PAGE_2(new Item(20007, 1), new Item(20006, 1), new Item(621, 20000), new Item(19062, 100), 35, 10000, Type.Misc, true, null),
        NECRO_PAGE_3(new Item(20008, 1), new Item(20007, 1), new Item(621, 35000), new Item(19062, 100), 20, 10000, Type.Misc, true, null),
        HELMET_MOULD_2(new Item(406, 1), new Item(401, 4), new Item(3576, 10), new Item(19062, 5), 100, 7500, Type.Armoury, true, null),
        PLATEBODY_MOULD_2(new Item(407, 1), new Item(402, 4), new Item(3576, 10), new Item(19062, 5), 100, 7500, Type.Armoury, true, null),
        LEGS_MOULD_2(new Item(408, 1), new Item(403, 4), new Item(3576, 10), new Item(19062, 5), 100, 7500, Type.Armoury, true, null),
        GLOVES_MOULD_2(new Item(409, 1), new Item(404, 4), new Item(3576, 10), new Item(19062, 5), 100, 7500, Type.Armoury, true, null),
        BOOTS_MOULD_2(new Item(410, 1), new Item(405, 4), new Item(3576, 10), new Item(19062, 5), 100, 7500, Type.Armoury, true, null),
        WEAPON_MOULD_2(new Item(417, 1), new Item(416, 4), new Item(3576, 10), new Item(19062, 5), 100, 7500, Type.Weaponry, true, null),
        HELMET_MOULD_3(new Item(411, 1), new Item(406, 4), new Item(3576, 25), new Item(19062, 15), 100, 12500, Type.Armoury, true, null),
        PLATEBODY_MOULD_3(new Item(412, 1), new Item(407, 4), new Item(3576, 25), new Item(19062, 15), 100, 12500, Type.Armoury, true, null),
        LEGS_MOULD_3(new Item(413, 1), new Item(408, 4), new Item(3576, 25), new Item(19062, 15), 100, 12500, Type.Armoury, true, null),
        GLOVES_MOULD_3(new Item(414, 1), new Item(409, 4), new Item(3576, 25), new Item(19062, 15), 100, 12500, Type.Armoury, true, null),
        BOOTS_MOULD_3(new Item(415, 1), new Item(410, 4), new Item(3576, 25), new Item(19062, 15), 100, 12500, Type.Armoury, true, null),
        WEAPON_MOULD_3(new Item(418, 1), new Item(417, 4), new Item(3576, 25), new Item(19062, 15), 100, 12500, Type.Weaponry, true, null),

        SPECTRAL_WEAPON_MOLD(new Item(2040, 1), new Item(2064, 1500), new Item(3576, 10000), new Item(19062, 750), 100, 10000, Type.Weaponry, true, new Item(418, 1)),

        SPECTRAL_HELMET_MOULD_3(new Item(2043, 1), new Item(2064, 2250), new Item(3576, 5000), new Item(19062, 500), 100, 10000, Type.Armoury, true, new Item(411, 1)),
        SPECTRAL_PLATEBODY_MOULD_3(new Item(2044, 1), new Item(2064, 2250), new Item(3576, 5000), new Item(19062, 500), 100, 10000, Type.Armoury, true, new Item(412, 1)),
        SPECTRAL_LEGS_MOULD_3(new Item(2045, 1), new Item(2064, 2250), new Item(3576, 5000), new Item(19062, 500), 100, 10000, Type.Armoury, true, new Item(413, 1)),
        SPECTRAL_GLOVES_MOULD_3(new Item(2046, 1), new Item(2064, 2250), new Item(3576, 5000), new Item(19062, 500), 100, 10000, Type.Armoury, true, new Item(414, 1)),
        SPECTRAL_BOOTS_MOULD_3(new Item(2047, 1), new Item(2064, 2250), new Item(3576, 5000), new Item(19062, 500), 100, 10000, Type.Armoury, true, new Item(415, 1)),

        HOLY_HAT(new Item(3020, 1), new Item(9950, 1), new Item(9955, 1), new Item(9960, 1), 100, 1000000, Type.Armoury, true, null),
        HOLY_BODY(new Item(3021, 1), new Item(9951, 1), new Item(9956, 1), new Item(9961, 1), 100, 1000000, Type.Armoury, true, null),
        HOLY_LEGS(new Item(3022, 1), new Item(9952, 1), new Item(9957, 1), new Item(9962, 1), 100, 1000000, Type.Armoury, true, null),
        HOLY_GLOVES(new Item(3023, 1), new Item(9953, 1), new Item(9958, 1), new Item(9963, 1), 100, 1000000, Type.Armoury, true, null),
        HOLY_BOOTS(new Item(3024, 1), new Item(9954, 1), new Item(9959, 1), new Item(9964, 1), 100, 1000000, Type.Armoury, true, null),


        SPECTRAL_QUIVER(new Item(2056, 1), new Item(2058, 1), new Item(2059, 1), new Item(2060, 1), 100, 375000, Type.Misc, true, null),

        DIVINE_RING(new Item(2075, 1), new Item(2072, 1), new Item(2064, 10000), new Item(19062, 500), 100, 200000, Type.Misc, true, new Item(2680, 1)),
        BARBARIC_RING(new Item(2076, 1), new Item(2073, 1), new Item(2064, 10000), new Item(19062, 500), 100, 200000, Type.Misc, true, new Item(2680, 1)),
        FROZEN_RING(new Item(2077, 1), new Item(2074, 1), new Item(2064, 10000), new Item(19062, 500), 100, 200000, Type.Misc, true, new Item(2680, 1)),
        SPECTRAL_RING(new Item(2078, 1), new Item(2075, 1), new Item(2076, 1), new Item(2077, 1), 100, 350000, Type.Misc, true, null),



        DIVINE_AMULET(new Item(2068, 1), new Item(2065, 1), new Item(2064, 10000), new Item(19062, 500), 100, 200000, Type.Misc, true, new Item(2683, 1)),
        BARBARIC_AMULET(new Item(2069, 1), new Item(2067, 1), new Item(2064, 10000), new Item(19062, 500), 100, 200000, Type.Misc, true, new Item(2683, 1)),
        FROZEN_AMULET(new Item(2070, 1), new Item(2066, 1), new Item(2064, 10000), new Item(19062, 500), 100, 200000, Type.Misc, true, new Item(2683, 1)),
        SPECTRAL_AMULET(new Item(2071, 1), new Item(2068, 1), new Item(2069, 1), new Item(2070, 1), 100, 350000, Type.Misc, true, null),

        SPECTRAL_UNLOCK(new Item(2057, 1), new Item(3576, 35000), new Item(6466, 10000), new Item(19062, 1200), 100, 300000, Type.Misc, true, new Item(17106, 1)),

        ;

        private Item upgradedItem;
        private Item reqItemOne;
        private Item reqItemTwo;
        private Item reqItemThree;
        private int successChance;
        private int cashReq;
        private Type type;
        private boolean toAnnounce;
        private Item previousTierItem;

        Upgradeable(Item upgradedItem, Item reqItemOne, Item reqItemTwo, Item reqItemThree, int successChance, int cashReq, Type type, boolean toAnnounce, Item previousTierItem) {
            this.upgradedItem = upgradedItem;
            this.reqItemOne = reqItemOne;
            this.reqItemTwo = reqItemTwo;
            this.reqItemThree = reqItemThree;
            this.successChance = successChance;
            this.cashReq = cashReq;
            this.type = type;
            this.toAnnounce = toAnnounce;
            this.previousTierItem = previousTierItem;
        }
    }

    public void display() {
        p.getPacketSender().sendConfig(1091, 2);
        type = Type.Weaponry;
        arr = new CopyOnWriteArrayList<>();
        arr2 = new CopyOnWriteArrayList<>();
        selection = null;
        for (Upgradeable upgradeable : Upgradeable.values()) {
            if (upgradeable.type == type) {
                arr.add(upgradeable.upgradedItem);
            }
        }
        p.getPacketSender().sendInterfaceItems(30924, arr2);
        p.getPacketSender().sendInterfaceItems(30919, arr);
        p.getPacketSender().sendString(30915, "--");
        p.getPacketSender().sendString(30916, "--");
        p.getPacketSender().sendItemOnInterface(30925, -1, 0);

        p.getPacketSender().sendInterface(30900);
        arr.clear();
    }

    public boolean typeSwitch(int id) {
        switch (id) {
            case 30907:
                type = Type.Weaponry;
                return true;
            case 30906:
                type = Type.Armoury;
                return true;
            case 30905:
                type = Type.Misc;
                return true;
        }
        return false;
    }

    public boolean handleBtnClick(int id) {
        switch (id) {
            case 30920:
                upgrade();
                return true;
            case 30902:
                p.getPacketSender().sendInterfaceRemoval();
                return true;
        }
        return false;
    }

    public void selection(Item item) {
        selection = null;
        for (Upgradeable upgradeable : Upgradeable.values()) {
            if (upgradeable.upgradedItem.getId() == item.getId()) {
                selection = upgradeable;
                arr2.add(selection.reqItemOne);
                if (selection.reqItemTwo != null) {
                    arr2.add(selection.reqItemTwo);
                }
                if (selection.reqItemThree != null) {
                    arr2.add(selection.reqItemThree);
                }

                if (selection.previousTierItem != null) {
                    String itemName = ItemDefinition.forId(selection.previousTierItem.getId()).getName();
                    p.msgRed("You must sacrifice the previous tier item: " + itemName + ".");
                    p.msgRed("Keep it in your inventory while attempting your upgrade!");
                    p.getPacketSender().sendItemOnInterface(30926, selection.previousTierItem.getId(), selection.previousTierItem.getAmount());
                } else {
                    p.getPacketSender().sendItemOnInterface(30926, -1, 0);
                }

                p.getPacketSender().sendInterfaceItems(30924, arr2);
                p.getPacketSender().sendString(30915, "@gre@" + Misc.sendCashToString(selection.cashReq) + " @or2@required.");
                p.getPacketSender().sendString(30916, "@gre@" + selection.successChance + "% @or2@success chance.");
                p.getPacketSender().sendItemOnInterface(30925, selection.upgradedItem.getId(), selection.upgradedItem.getAmount());
                arr2.clear();
                return;
            }
        }
    }


    public void upgrade() {
        if (selection == null || type == null) {
            p.sendMessage("@red@Please choose an item to enchant...");
            return;
        }

        List<Item> requiredItems = new ArrayList<>(Arrays.asList(selection.reqItemOne, selection.reqItemTwo, selection.reqItemThree));
        requiredItems.removeAll(Collections.singleton(null));

        // Check for required items
        for (Item requiredItem : requiredItems) {
            int itemId = requiredItem.getId();
            int amountRequired = requiredItem.getAmount();

            if (isEssenceItem(itemId)) {
                if (!p.getInventory().contains(18015)){
                    p.msgRed("Please place your Essence pouch in your inventory!");
                    return;
                }
                if (!hasRequiredEssence(requiredItem)) {
                    sendMissingEssenceMessage(requiredItem);
                    return;
                }
            } else {
                if (!p.getInventory().containsAll(requiredItem)) {
                    String itemName = ItemDefinition.forId(itemId).getName();
                    p.sendMessage("@red@You are missing " + amountRequired + " x " + itemName + " from your inventory.");
                    return;
                }
            }
        }

        // Check if the player has enough cash for the upgrade
        if (!p.getInventory().contains(995, selection.cashReq)) {
            p.sendMessage("@red@You need " + selection.cashReq + " coins to attempt this upgrade.");
            return;
        }

        // Check for previous tier item
        if (selection.previousTierItem != null) {
            if (!p.getInventory().contains(selection.previousTierItem.getId())) {
                String itemName = ItemDefinition.forId(selection.previousTierItem.getId()).getName();
                p.sendMessage("@red@You need to sacrifice " + itemName + ", you must have it in your inventory!");
                return;
            }
            p.getInventory().delete(selection.previousTierItem.getId(), 1);
        }

        // Deduct the required items and cash
        for (Item requiredItem : requiredItems) {
            if (isEssenceItem(requiredItem.getId())) {
                deductEssenceFromPouch(requiredItem);
            } else {
                p.getInventory().delete(requiredItem.getId(), requiredItem.getAmount());
            }
        }

        // Deduct the cash required for the upgrade
        p.getInventory().delete(995, selection.cashReq);

        // Perform the upgrade
        performUpgrade();
        p.getInventory().refreshItems();
        arr2.clear();
    }




    private boolean hasRequiredEssence(Item item) {
        int itemId = item.getId();
        int amount = item.getAmount();
        switch (itemId) {
            case 19062: // Monster Essence
                return p.getMonsteressence() >= amount;
            case 11054: // Fire Essence
                return p.getFireessence() >= amount;
            case 11056: // Water Essence
                return p.getWateressence() >= amount;
            case 11052: // Earth Essence
                return p.getEarthessence() >= amount;
            case 3576: // Slayer Essence
                return p.getSlayeressence() >= amount;
            case 6466: // Beast Essence
                return p.getBeastEssence() >= amount;
            case 3502: // Corrupt Essence
                return p.getCorruptEssence() >= amount;
            case 2064: // Spectral Essence
                return p.getSpectralEssence() >= amount;
            default:
                return false;
        }
    }

    private void deductEssenceFromPouch(Item item) {
        int itemId = item.getId();
        int amount = item.getAmount();
        switch (itemId) {
            case 19062: // Monster Essence
                p.setMonsteressence(p.getMonsteressence() - amount);
                break;
            case 11054: // Fire Essence
                p.setFireessence(p.getFireessence() - amount);
                break;
            case 11056: // Water Essence
                p.setWateressence(p.getWateressence() - amount);
                break;
            case 11052: // Earth Essence
                p.setEarthessence(p.getEarthessence() - amount);
                break;
            case 3576: // Slayer Essence
                p.setSlayeressence(p.getSlayeressence() - amount);
                break;
            case 6466: // Beast Essence
                p.setBeastEssence(p.getBeastEssence() - amount);
                break;
            case 3502: // Corrupt Essence
                p.setCorruptEssence(p.getCorruptEssence() - amount);
                break;
            case 2064: // Spectral Essence
                p.setSpectralEssence(p.getSpectralEssence() - amount);
                break;
        }
    }

    private void sendMissingEssenceMessage(Item item) {
        int itemId = item.getId();
        int amountRequired = item.getAmount();
        switch (itemId) {
            case 19062:
                p.sendMessage("@red@You need " + (amountRequired - p.getMonsteressence()) + " more Monster Essence in your pouch.");
                break;
            case 11054:
                p.sendMessage("@red@You need " + (amountRequired - p.getFireessence()) + " more Fire Essence in your pouch.");
                break;
            case 11056:
                p.sendMessage("@red@You need " + (amountRequired - p.getWateressence()) + " more Water Essence in your pouch.");
                break;
            case 11052:
                p.sendMessage("@red@You need " + (amountRequired - p.getEarthessence()) + " more Earth Essence in your pouch.");
                break;
            case 3576:
                p.sendMessage("@red@You need " + (amountRequired - p.getSlayeressence()) + " more Slayer Essence in your pouch.");
                break;
            case 6466:
                p.sendMessage("@red@You need " + (amountRequired - p.getBeastEssence()) + " more Beast Essence in your pouch.");
                break;
            case 3502:
                p.sendMessage("@red@You need " + (amountRequired - p.getCorruptEssence()) + " more Corrupt Essence in your pouch.");
                break;
            case 2064:
                p.sendMessage("@red@You need " + (amountRequired - p.getSpectralEssence()) + " more Spectral Essence in your pouch.");
                break;
        }
    }


    private boolean isEssenceItem(int itemId) {
        return itemId == 19062 || itemId == 11054 || itemId == 11056 || itemId == 11052 ||
                itemId == 3576 || itemId == 6466 || itemId == 3502 || itemId == 2064;
    }

    private void performUpgrade() {
        Random rand = new Random();
        if (rand.nextInt(100) < selection.successChance) {
            if (selection.toAnnounce) {
                announceUpgrade();
            }
            p.getInventory().add(selection.upgradedItem.getId(), selection.upgradedItem.getAmount());
        } else {
            p.sendMessage("@red@You failed to upgrade the item.");
            World.sendMessage("<col=FF0000><shad=0>[ENCHANT] " + p.getUsername() + " <col=FF0000><shad=0>attempted to enchant a @red@" + ItemDefinition.forId(selection.upgradedItem.getId()).getName() + "<col=FF0000><shad=0> but failed!");
        }
    }


    private void announceUpgrade() {
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_2_ITEMS, 1);
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_5_ITEMS, 1);
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_10_ITEMS, 1);
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_25_ITEMS, 1);
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_50_ITEMS, 1);
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_100_ITEMS, 1);
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_250_ITEMS, 1);
        Achievements.doProgress(p, Achievements.Achievement.UPGRADE_500_ITEMS, 1);
        World.sendMessage("<col=AF70C3><shad=0>[ENCHANT] " + p.getUsername() + " <col=AF70C3><shad=0>has successfully enchanted a @red@" + ItemDefinition.forId(selection.upgradedItem.getId()).getName() + "<col=AF70C3><shad=0>!");
        if (p.getStarterTasks().hasCompletedAll() && !p.getMediumTasks().hasCompletedAll()) {
            MediumTasks.doProgress(p, MediumTasks.MediumTaskData.UPGRADE_5_ITEMS, 1);
        }
        if (p.getMediumTasks().hasCompletedAll() && !p.getEliteTasks().hasCompletedAll()) {
            EliteTasks.doProgress(p, EliteTasks.EliteTaskData.UPGRADE_10_ITEMS, 1);
        }
    }

    public void displayItems() {
        for (Upgradeable upgradeable : Upgradeable.values()) {
            if (upgradeable.type == type) {
                arr.add(upgradeable.upgradedItem);
            }
        }
        p.getPacketSender().sendInterfaceItems(30919, arr);
        arr.clear();
    }
}