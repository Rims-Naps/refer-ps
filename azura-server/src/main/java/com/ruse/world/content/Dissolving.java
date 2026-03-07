package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.model.Animation;
import com.ruse.model.Item;
import com.ruse.model.Skill;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.entity.impl.player.Player;

public class Dissolving {

    private Player player;

    public Dissolving(Player player) {
        this.player = player;
    }

    static int anim = 713;
    static int shards = 19062;
    static int slay_essence = 3576;
    static int corrupt_essence = 3502;
    static int spectral_essence = 2064;


    public enum DissolvingData {

        CinderHelm(18588, new Item[]{new Item(shards, 1)}, 150, anim),
        CinderBody(18589, new Item[]{new Item(shards, 1)}, 150, anim),
        CinderLegs(18590, new Item[]{new Item(shards, 1)}, 150, anim),
        CinderGloves(18591, new Item[]{new Item(shards, 1)}, 150, anim),
        CinderBoots(18592, new Item[]{new Item(shards, 1)}, 150, anim),
        CinderBlade(16024, new Item[]{new Item(shards, 1)}, 150, anim),
        CinderBow(13042, new Item[]{new Item(shards, 1)}, 150, anim),
        CinderStaff(14915, new Item[]{new Item(shards, 1)}, 150, anim),

        StoneWardHelm(15895, new Item[]{new Item(shards, 1)}, 150, anim),
        StoneWardBody(15840, new Item[]{new Item(shards, 1)}, 150, anim),
        StoneWardLegs(15800, new Item[]{new Item(shards, 1)}, 150, anim),
        StoneWardGloves(16188, new Item[]{new Item(shards, 1)}, 150, anim),
        StoneWardBoots(15789, new Item[]{new Item(shards, 1)}, 150, anim),
        StoneWardBlade(12930, new Item[]{new Item(shards, 1)}, 150, anim),
        StoneWardBow(16879, new Item[]{new Item(shards, 1)}, 150, anim),
        StoneWardStaff(23144, new Item[]{new Item(shards, 1)}, 150, anim),

        TideWaveHelm(15645, new Item[]{new Item(shards, 1)}, 150, anim),
        TideWaveBody(15646, new Item[]{new Item(shards, 1)}, 150, anim),
        TideWaveLegs(15647, new Item[]{new Item(shards, 1)}, 150, anim),
        TideWaveGloves(9057, new Item[]{new Item(shards, 1)}, 150, anim),
        TideWaveBoots(9058, new Item[]{new Item(shards, 1)}, 150, anim),
        TideWaveBlade(23145, new Item[]{new Item(shards, 1)}, 150, anim),
        TideWaveBow(23146, new Item[]{new Item(shards, 1)}, 150, anim),
        TideWaveStaff(23033, new Item[]{new Item(shards, 1)}, 150, anim),

        VerdantHelm(23139, new Item[]{new Item(shards, 2)}, 150, anim),
        VerdantBody(23140, new Item[]{new Item(shards, 2)}, 150, anim),
        VerdantLegs(23141, new Item[]{new Item(shards, 2)}, 150, anim),
        VerdantGloves(23142, new Item[]{new Item(shards, 2)}, 150, anim),
        VerdantBoots(23143, new Item[]{new Item(shards, 2)}, 150, anim),
        VerdantAxe(18332, new Item[]{new Item(shards, 2)}, 150, anim),
        VerdantBow(3745, new Item[]{new Item(shards, 2)}, 150, anim),
        VerdantStaff(3743, new Item[]{new Item(shards, 2)}, 150, anim),

        FlameStrikeHelm(13051, new Item[]{new Item(shards, 2)}, 150, anim),
        FlameStrikeBody(13052, new Item[]{new Item(shards, 2)}, 150, anim),
        FlameStrikeLegs(13053, new Item[]{new Item(shards, 2)}, 150, anim),
        FlameStrikeGloves(13054, new Item[]{new Item(shards, 2)}, 150, anim),
        FlameStrikeBoots(13055, new Item[]{new Item(shards, 2)}, 150, anim),
        FlameStrikeBlade(23039, new Item[]{new Item(shards, 2)}, 150, anim),
        FlameStrikeBow(23064, new Item[]{new Item(shards, 2)}, 150, anim),
        FlameStrikeStaff(23065, new Item[]{new Item(shards, 2)}, 150, anim),

        WaveBreakerHelm(17608, new Item[]{new Item(shards, 3)}, 150, anim),
        WaveBreakerBody(17610, new Item[]{new Item(shards, 3)}, 150, anim),
        WaveBreakerLegs(17612, new Item[]{new Item(shards, 3)}, 150, anim),
        WaveBreakerGloves(5071, new Item[]{new Item(shards, 3)}, 150, anim),
        WaveBreakerBoots(5072, new Item[]{new Item(shards, 3)}, 150, anim),
        WaveBreakerBlade(23066, new Item[]{new Item(shards, 3)}, 150, anim),
        WaveBreakerBow(23067, new Item[]{new Item(shards, 3)}, 150, anim),
        WaveBreakerStaff(14065, new Item[]{new Item(shards, 3)}, 150, anim),

        MagmiteHelm(13044, new Item[]{new Item(shards, 4)}, 150, anim),
        MagmiteBody(13045, new Item[]{new Item(shards, 4)}, 150, anim),
        MagmiteLegs(13046, new Item[]{new Item(shards, 4)}, 150, anim),
        MagmiteGloves(13047, new Item[]{new Item(shards, 4)}, 150, anim),
        MagmiteBoots(13048, new Item[]{new Item(shards, 4)}, 150, anim),
        MagmiteBlade(14004, new Item[]{new Item(shards, 4)}, 150, anim),
        MagmiteBow(18799, new Item[]{new Item(shards, 4)}, 150, anim),
        MagmiteStaff(5010, new Item[]{new Item(shards, 4)}, 150, anim),

        MossHeartHelm(19811, new Item[]{new Item(shards, 5)}, 150, anim),
        MossHeartBody(19473, new Item[]{new Item(shards, 5)}, 150, anim),
        MossHeartLegs(19472, new Item[]{new Item(shards, 5)}, 150, anim),
        MossHeartGloves(19946, new Item[]{new Item(shards, 5)}, 150, anim),
        MossHeartBoots(19945, new Item[]{new Item(shards, 5)}, 150, anim),
        MossHeartBlade(23055, new Item[]{new Item(shards, 5)}, 150, anim),
        MossHeartBow(23056, new Item[]{new Item(shards, 5)}, 150, anim),
        MossHeartStaff(13943, new Item[]{new Item(shards, 5)}, 150, anim),

        SurgeHelm(21015, new Item[]{new Item(shards, 6)}, 150, anim),
        SurgeBody(21016, new Item[]{new Item(shards, 6)}, 150, anim),
        SurgeLegs(21017, new Item[]{new Item(shards, 6)}, 150, anim),
        SurgeGloves(21040, new Item[]{new Item(shards, 6)}, 150, anim),
        SurgeBoots(21041, new Item[]{new Item(shards, 6)}, 150, anim),
        SurgeBlade(13049, new Item[]{new Item(shards, 6)}, 150, anim),
        SurgeBow(13056, new Item[]{new Item(shards, 6)}, 150, anim),
        SurgeStaff(13063, new Item[]{new Item(shards, 6)}, 150, anim),


        MossBornHelm(23134, new Item[]{new Item(shards, 7)}, 150, anim),
        MossBornBody(23135, new Item[]{new Item(shards, 7)}, 150, anim),
        MossBornLegs(23136, new Item[]{new Item(shards, 7)}, 150, anim),
        MossBornGloves(23137, new Item[]{new Item(shards, 7)}, 150, anim),
        MossBornBoots(23138, new Item[]{new Item(shards, 7)}, 150, anim),
        MossBornBlade(13015, new Item[]{new Item(shards, 7)}, 150, anim),
        MossBornBow(13022, new Item[]{new Item(shards, 7)}, 150, anim),
        MossBornStaff(13029, new Item[]{new Item(shards, 7)}, 150, anim),

        PulsarHelm(523, new Item[]{new Item(shards, 8)}, 150, anim),
        PulsarBody(524, new Item[]{new Item(shards, 8)}, 150, anim),
        PulsarLegs(525, new Item[]{new Item(shards, 8)}, 150, anim),
        PulsraGloves(12860, new Item[]{new Item(shards, 8)}, 150, anim),
        PulsarBoots(12565, new Item[]{new Item(shards, 8)}, 150, anim),
        PulsarBlade(13035, new Item[]{new Item(shards, 8)}, 150, anim),
        PulsarBow(13964, new Item[]{new Item(shards, 8)}, 150, anim),
        PulsarStaff(21070, new Item[]{new Item(shards, 8)}, 150, anim),

        MoltarokHelm(13065, new Item[]{new Item(shards, 9)}, 150, anim),
        MoltarokBody(13066, new Item[]{new Item(shards, 9)}, 150, anim),
        MoltarokLegs(13067, new Item[]{new Item(shards, 9)}, 150, anim),
        MoltarokGloves(13068, new Item[]{new Item(shards, 9)}, 150, anim),
        MoltarokBoots(13069, new Item[]{new Item(shards, 9)}, 150, anim),
        MoltarokBlade(18629, new Item[]{new Item(shards, 9)}, 150, anim),
        MoltarokBow(17714, new Item[]{new Item(shards, 9)}, 150, anim),
        MoltarokStaff(15485, new Item[]{new Item(shards, 9)}, 150, anim),

        CliffbreakerHelm(19828, new Item[]{new Item(shards, 10)}, 150, anim),
        CliffbreakerBody(19823, new Item[]{new Item(shards, 10)}, 150, anim),
        CliffbreakerLegs(19822, new Item[]{new Item(shards, 10)}, 150, anim),
        CliffbreakerGloves(19815, new Item[]{new Item(shards, 10)}, 150, anim),
        CliffbreakerBoots(19817, new Item[]{new Item(shards, 10)}, 150, anim),
        CliffbreakerBlade(16867, new Item[]{new Item(shards, 10)}, 150, anim),
        CliffbreakerBow(16337, new Item[]{new Item(shards, 10)}, 150, anim),
        CliffbreakerStaff(17620, new Item[]{new Item(shards, 10)}, 150, anim),

        TidalHelm(11009, new Item[]{new Item(shards, 11)}, 150, anim),
        TidalBody(11010, new Item[]{new Item(shards, 11)}, 150, anim),
        TidalLegs(11011, new Item[]{new Item(shards, 11)}, 150, anim),
        TidalGloves(21066, new Item[]{new Item(shards, 11)}, 150, anim),
        TidalBoots(21067, new Item[]{new Item(shards, 11)}, 150, anim),
        TidalAxe(20171, new Item[]{new Item(shards, 11)}, 150, anim),
        TidalBow(21023, new Item[]{new Item(shards, 11)}, 150, anim),
        TidalStaff(16875, new Item[]{new Item(shards, 11)}, 150, anim),

        MercurialHelm(18937, new Item[]{new Item(shards, 12)}, 150, anim),
        MercuraulBody(18938, new Item[]{new Item(shards, 12)}, 150, anim),
        MercurialLegs(18939, new Item[]{new Item(shards, 12)}, 150, anim),
        MercurialGloves(18970, new Item[]{new Item(shards, 12)}, 150, anim),
        MercurialBoots(18971, new Item[]{new Item(shards, 12)}, 150, anim),
        MercurialBlade(14005, new Item[]{new Item(shards, 12)}, 150, anim),
        MercurialBow(18749, new Item[]{new Item(shards, 12)}, 150, anim),
        MercurialStaff(18748, new Item[]{new Item(shards, 12)}, 150, anim),

        VireHelm(23034, new Item[]{new Item(shards, 13)}, 150, anim),
        VireBody(23035, new Item[]{new Item(shards, 13)}, 150, anim),
        VireLegs(23036, new Item[]{new Item(shards, 13)}, 150, anim),
        VireGloves(23037, new Item[]{new Item(shards, 13)}, 150, anim),
        VireBoots(23038, new Item[]{new Item(shards, 13)}, 150, anim),
        VireBlade(18638, new Item[]{new Item(shards, 13)}, 150, anim),
        VireBow(12994, new Item[]{new Item(shards, 13)}, 150, anim),
        VireStaff(18009, new Item[]{new Item(shards, 13)}, 150, anim),

        SeafireHelm(23021, new Item[]{new Item(shards, 14)}, 150, anim),
        SeafireBody(23022, new Item[]{new Item(shards, 14)}, 150, anim),
        SeafireLegs(23023, new Item[]{new Item(shards, 14)}, 150, anim),
        SeafireGloves(23024, new Item[]{new Item(shards, 14)}, 150, anim),
        SeafireBoots(23025, new Item[]{new Item(shards, 14)}, 150, anim),
        SeafireBlade(8087, new Item[]{new Item(shards, 14)}, 150, anim),
        SeafireBow(16871, new Item[]{new Item(shards, 14)}, 150, anim),
        SeafireStaff(14006, new Item[]{new Item(shards, 14)}, 150, anim),

        TorridHelm(13010, new Item[]{new Item(shards, 15)}, 150, anim),
        TorridBody(13011, new Item[]{new Item(shards, 15)}, 150, anim),
        TorridLegs(13012, new Item[]{new Item(shards, 15)}, 150, anim),
        TorridGloves(13013, new Item[]{new Item(shards, 15)}, 150, anim),
        TorridBoots(13014, new Item[]{new Item(shards, 15)}, 150, anim),
        TorridBlade(13328, new Item[]{new Item(shards, 15)}, 150, anim),
        TorridBow(13329, new Item[]{new Item(shards, 15)}, 150, anim),
        TorridStaff(13330, new Item[]{new Item(shards, 15)}, 150, anim),


        AquarionHelm(17065, new Item[]{new Item(shards, 16)}, 200, anim),
        AquarionBody(17066, new Item[]{new Item(shards, 16)}, 200, anim),
        AquarionLegs(17067, new Item[]{new Item(shards, 16)}, 200, anim),
        AquarionGloves(17068, new Item[]{new Item(shards, 16)}, 200, anim),
        AquarionBoots(17069, new Item[]{new Item(shards, 16)}, 200, anim),
        AquarionWeapon(17108, new Item[]{new Item(shards, 16)}, 225, anim),

        IgnitionHelm(17070, new Item[]{new Item(shards, 17)}, 225, anim),
        IgnitionBody(17071, new Item[]{new Item(shards, 17)}, 225, anim),
        IgnitionLegs(17072, new Item[]{new Item(shards, 17)}, 225, anim),
        IgnitionGloves(17073, new Item[]{new Item(shards, 17)}, 225, anim),
        IgnitionBoots(17074, new Item[]{new Item(shards, 17)}, 225, anim),
        IgnitionWeapon(17109, new Item[]{new Item(shards, 17)}, 250, anim),

        BedrockHelm(17075, new Item[]{new Item(shards, 18)}, 250, anim),
        BedrockBody(17076, new Item[]{new Item(shards, 18)}, 250, anim),
        BedrockLegs(17077, new Item[]{new Item(shards, 18)}, 250, anim),
        BedrockGloves(17078, new Item[]{new Item(shards, 18)}, 250, anim),
        BedrockBoots(17079, new Item[]{new Item(shards, 18)}, 250, anim),
        BedrockWeapon(17110, new Item[]{new Item(shards, 18)}, 275, anim),

        CascadeHelm(17081, new Item[]{new Item(shards, 19)}, 275, anim),
        CascadeBody(17082, new Item[]{new Item(shards, 19)}, 275, anim),
        CascadeLegs(17083, new Item[]{new Item(shards, 19)}, 275, anim),
        CascadeGloves(17084, new Item[]{new Item(shards, 19)}, 275, anim),
        CascadeBoots(17085, new Item[]{new Item(shards, 19)}, 275, anim),
        CascadeWeapon(17111, new Item[]{new Item(shards, 19)}, 300, anim),

        EmberglowHelm(17086, new Item[]{new Item(shards, 20)}, 300, anim),
        EmberGlowBody(17087, new Item[]{new Item(shards, 20)}, 300, anim),
        EmberGlowLegs(17088, new Item[]{new Item(shards, 20)}, 300, anim),
        EmberGlowGloves(17089, new Item[]{new Item(shards, 20)}, 300, anim),
        EmberGlowBoots(17090, new Item[]{new Item(shards, 20)}, 300, anim),
        EmberGlowWeapon(17112, new Item[]{new Item(shards, 20)}, 325, anim),

        TerravaultHelm(17091, new Item[]{new Item(shards, 21)}, 325, anim),
        TerravaultBody(17092, new Item[]{new Item(shards, 21)}, 325, anim),
        TerravaultLegs(17093, new Item[]{new Item(shards, 21)}, 325, anim),
        TerravaultGloves(17094, new Item[]{new Item(shards, 21)}, 325, anim),
        TerravaultBoots(17095, new Item[]{new Item(shards, 21)}, 325, anim),
        TerravaultWeapon(17113, new Item[]{new Item(shards, 21)}, 350, anim),

        PoseidonHelm(17096, new Item[]{new Item(shards, 22)}, 350, anim),
        PoseidonBody(17097, new Item[]{new Item(shards, 22)}, 350, anim),
        PoseidonLegs(17098, new Item[]{new Item(shards, 22)}, 350, anim),
        PoseidonGloves(17099, new Item[]{new Item(shards, 22)}, 350, anim),
        PoseidonBoots(17100, new Item[]{new Item(shards, 22)}, 350, anim),
        PoseidonWeapon(17114, new Item[]{new Item(shards, 22)}, 375, anim),


        BlazenHelm(13058, new Item[]{new Item(shards, 23)}, 375, anim),
        BlazenBody(13059, new Item[]{new Item(shards, 23)}, 375, anim),
        BlazenLegs(13060, new Item[]{new Item(shards, 23)}, 375, anim),
        BlazenGloves(13061, new Item[]{new Item(shards, 23)}, 375, anim),
        BlazenBoots(13062, new Item[]{new Item(shards, 23)}, 375, anim),
        BlazenRapier(18974, new Item[]{new Item(shards, 23)}, 400, anim),

        WardenHelm(23028, new Item[]{new Item(shards, 24)}, 400, anim),
        WardenBody(23029, new Item[]{new Item(shards, 24)}, 400, anim),
        WardenLegs(23030, new Item[]{new Item(shards, 24)}, 400, anim),
        WardenGloves(23031, new Item[]{new Item(shards, 24)}, 400, anim),
        WardenBoots(23032, new Item[]{new Item(shards, 24)}, 400, anim),
        WardenBlade(16137, new Item[]{new Item(shards, 24)}, 425, anim),


        MasterHelm1(1470, new Item[]{new Item(shards, 25)}, 550, anim),
        MasterBody1(1471, new Item[]{new Item(shards, 25)}, 550, anim),
        MasterLegs1(1472, new Item[]{new Item(shards, 25)}, 550, anim),
        MasterGloves1(1473, new Item[]{new Item(shards, 25)}, 550, anim),
        MasterBoots1(1474, new Item[]{new Item(shards, 25)}, 550, anim),
        MasterWeapon1(1475, new Item[]{new Item(shards, 25)}, 570, anim),

        MasterHelm2(1476, new Item[]{new Item(shards, 26)}, 570, anim),
        MasterBody2(1477, new Item[]{new Item(shards, 26)}, 570, anim),
        MasterLegs2(1478, new Item[]{new Item(shards, 26)}, 570, anim),
        MasterGloves2(1479, new Item[]{new Item(shards, 26)}, 570, anim),
        MasterBoots2(1480, new Item[]{new Item(shards, 26)}, 570, anim),
        MasterWeapon2(1481, new Item[]{new Item(shards, 26)}, 580, anim),

        MasterHelm3(1482, new Item[]{new Item(shards, 27)}, 580, anim),
        MasterBody3(1483, new Item[]{new Item(shards, 27)}, 580, anim),
        MasterLegs3(1484, new Item[]{new Item(shards, 27)}, 580, anim),
        MasterGloves3(1485, new Item[]{new Item(shards, 27)}, 580, anim),
        MasterBoots3(1523, new Item[]{new Item(shards, 27)}, 590, anim),

        MasterWeapon3(1487, new Item[]{new Item(shards, 28)}, 600, anim),
        MasterHelm4(1488, new Item[]{new Item(shards, 28)}, 600, anim),
        MasterBody4(1489, new Item[]{new Item(shards, 28)}, 600, anim),
        MasterLegs4(1490, new Item[]{new Item(shards, 28)}, 600, anim),
        MasterGloves4(1491, new Item[]{new Item(shards, 28)}, 600, anim),
        MasterBoots4(1492, new Item[]{new Item(shards, 28)}, 600, anim),
        MasterWeapon4(1493, new Item[]{new Item(shards, 28)}, 625, anim),

        MasterHelm5(1494, new Item[]{new Item(shards, 29)}, 625, anim),
        MasterBody5(1495, new Item[]{new Item(shards, 29)}, 625, anim),
        MasterLegs5(1496, new Item[]{new Item(shards, 29)}, 625, anim),
        MasterGloves5(1497, new Item[]{new Item(shards, 29)}, 625, anim),
        MasterBoots5(1498, new Item[]{new Item(shards, 29)}, 625, anim),
        MasterWeapon5(1499, new Item[]{new Item(shards, 29)}, 650, anim),

        MasterHelm6(1500, new Item[]{new Item(shards, 30)}, 650, anim),
        MasterBody6(1501, new Item[]{new Item(shards, 30)}, 650, anim),
        MasterLegs6(1502, new Item[]{new Item(shards, 30)}, 650, anim),
        MasterGloves6(1503, new Item[]{new Item(shards, 30)}, 650, anim),
        MasterBoots6(1504, new Item[]{new Item(shards, 30)}, 650, anim),
        MasterWeapon6(1505, new Item[]{new Item(shards, 30)}, 675, anim),

        MasterHelm7(1506, new Item[]{new Item(shards, 31)}, 675, anim),
        MasterBody7(1507, new Item[]{new Item(shards, 31)}, 675, anim),
        MasterLegs7(1508, new Item[]{new Item(shards, 31)}, 675, anim),
        MasterGloves7(1509, new Item[]{new Item(shards, 31)}, 675, anim),
        MasterBoots7(1510, new Item[]{new Item(shards, 31)}, 675, anim),
        MasterWeapon7(1511, new Item[]{new Item(shards, 31)}, 700, anim),

        MasterHelm8(1512, new Item[]{new Item(shards, 32)}, 700, anim),
        MasterBody8(1513, new Item[]{new Item(shards, 32)}, 700, anim),
        MasterLegs8(1514, new Item[]{new Item(shards, 32)}, 700, anim),
        MasterGloves8(1515, new Item[]{new Item(shards, 32)}, 700, anim),
        MasterBoots8(1516, new Item[]{new Item(shards, 32)}, 700, anim),
        MasterWeapon8(1517, new Item[]{new Item(shards, 32)}, 725, anim),

        MasterHelm9(1518, new Item[]{new Item(shards, 33)}, 725, anim),
        MasterBody9(1519, new Item[]{new Item(shards, 33)}, 725, anim),
        MasterLegs9(1520, new Item[]{new Item(shards, 33)}, 725, anim),
        MasterGloves9(1521, new Item[]{new Item(shards, 33)}, 725, anim),
        MasterBoots9(1522, new Item[]{new Item(shards, 33)}, 725, anim),
        MasterWeapon9(1523, new Item[]{new Item(shards, 33)}, 750, anim),
        MasterWeapon92(1524, new Item[]{new Item(shards, 33)}, 750, anim),


        GAIA_HEADPIECE(12830, new Item[]{new Item(slay_essence, 750)}, 2500, anim),
        GAIA_SKULL(12834, new Item[]{new Item(slay_essence, 750)}, 2500, anim),
        AQUA_HEAD_PIECE(12832, new Item[]{new Item(slay_essence, 750)}, 2500, anim),
        AQUA_SKULL(12835, new Item[]{new Item(slay_essence, 750)}, 2500, anim),
        LAVA_HEADPIECE(12831, new Item[]{new Item(slay_essence, 750)}, 2500, anim),
        LAVA_SKULL(12836, new Item[]{new Item(slay_essence, 750)}, 2500, anim),


        LAVA_HELM_1(22000, new Item[]{new Item(slay_essence, 1250)}, 2500, anim),
        LAVA_HELM_2(12460, new Item[]{new Item(slay_essence, 2000)}, 5000, anim),
        LAVA_HELM_3(12461, new Item[]{new Item(slay_essence, 3500)}, 10000, anim),


        AQUA_HELM_1(22001, new Item[]{new Item(slay_essence, 1250)}, 2500, anim),
        AQUA_HELM_2(12462, new Item[]{new Item(slay_essence, 2000)}, 5000, anim),
        AQUA_HELM_3(12463, new Item[]{new Item(slay_essence, 3500)}, 10000, anim),


        GAIA_HELM_1(22002, new Item[]{new Item(slay_essence, 1250)}, 2500, anim),
        GAIA_HELM_2(12464, new Item[]{new Item(slay_essence, 2000)}, 5000, anim),
        GAIA_HELM_3(12465, new Item[]{new Item(slay_essence, 3500)}, 10000, anim),



        CORRUPT_HEADPIECE(3503, new Item[]{new Item(corrupt_essence, 1000)}, 2500, anim),
        CORRUPT_SKULL(3504, new Item[]{new Item(corrupt_essence, 1000)}, 2500, anim),
        FALLEN_STAR(298, new Item[]{new Item(spectral_essence, 1000)}, 2500, anim),


        ;

        DissolvingData(int id, Item[] rewards, int experience, int animation) {
            this.id = id;
            this.rewards = rewards;
            this.experience = experience;
            this.animation = animation;
            // this.progressions = progressions;
        }

        public static DissolvingData forId(int itemId) {
            for (DissolvingData data : values()) {
                if (data.getId() == itemId)
                    return data;
            }
            return null;
        }

        private int id, experience, animation;
        private Item[] rewards;
        // private int[][] progressions;

        public int getId() {
            return id;
        }

        public int getExperience() {
            return experience;
        }

        public int getAnimation() {
            return animation;
        }

        public Item[] getRewards() {
            return rewards;
        }
    }

    public int getDissolvableItemsInInv(int id) {
        int count = 0;
        for (Item item : player.getInventory().getItems()) {
            if (isDissolvable(item.getId()) && item.getId() == id) {
                count += item.getAmount();
            }
        }
        return count;
    }



    public boolean isDissolvable(int id) {
        if (id == 23776 || id == 23782 || id == 23812)
            return false;
        for (DissolvingData data : DissolvingData.values()) {
            if (data.getId() == id || (id - 1 == data.getId() && ItemDefinition.forId(id).isNoted() && ItemDefinition.forId(id).getName().equalsIgnoreCase(ItemDefinition.forId(data.getId()).getName())))
                return true;
        }
        return false;
    }

    public void handle(int id, int amount, boolean ignoreDialogue) {
        for (DissolvingData data : DissolvingData.values()) {
            if ((data.getId() == id || (id - 1 == data.getId() && ItemDefinition.forId(id).isNoted()
                    && ItemDefinition.forId(id).getName().equalsIgnoreCase(ItemDefinition.forId(data.getId()).getName())))) {
                if (ignoreDialogue) {
                    player.setAttemptDissolve(data.getId());
                    player.setDialogueActionId(524);
                    DialogueManager.start(player, 524);
                    return;
                }
                if (data.getId() == id) {
                    if (player.getInventory().contains(18015, 1)) {
                        player.getSkillManager().addExperience(Skill.ALCHEMY, (data.getExperience() * amount));
                        player.sendMessage("<shad=0><col=AF70C3>You Salvaged @red@" + amount + "<shad=0><col=AF70C3>X @red@" + ItemDefinition.forId(id).getName() + " <shad=0><col=AF70C3>for x" + Misc.insertCommasToNumber(data.getRewards()[0].getAmount() * amount) + " " + ItemDefinition.forId(data.getRewards()[0].getId()).getName());
                        player.getInventory().delete(id, amount);

                        if (data.getRewards()[0].getId() != 3576 && data.getRewards()[0].getId() != 3502  && data.getRewards()[0].getId() != 2064) {
                            player.sendMessage("<shad=0><col=AF70C3>" + data.getRewards()[0].getAmount() * amount + "<shad=0><col=AF70C3>X Monster Essence has been added to your Pouch!");
                            player.setMonsteressence(player.getMonsteressence() + data.getRewards()[0].getAmount() * amount);
                        }
                        if (data.getRewards()[0].getId() == 3576) {
                            player.sendMessage("<shad=0><col=AF70C3>" + data.getRewards()[0].getAmount() * amount + "<shad=0><col=AF70C3>X Slayer Essence has been added to your Pouch!");
                            player.setSlayeressence(player.getSlayeressence() + data.getRewards()[0].getAmount() * amount);
                        }

                        if (data.getRewards()[0].getId() == 3502) {
                            player.sendMessage("<shad=0><col=AF70C3>" + data.getRewards()[0].getAmount() * amount + "<shad=0><col=AF70C3>X Corrupt Essence has been added to your Pouch!");
                            player.setCorruptEssence(player.getCorruptEssence() + data.getRewards()[0].getAmount() * amount);
                        }
                        if (data.getRewards()[0].getId() == 2064) {
                            player.sendMessage("<shad=0><col=AF70C3>" + data.getRewards()[0].getAmount() * amount + "<shad=0><col=AF70C3>X Spectral Essence has been added to your Pouch!");
                            player.setSpectralEssence(player.getSpectralEssence() + data.getRewards()[0].getAmount() * amount);
                        }

                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_25_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_50_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_150_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_250_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_500_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_1000_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_2500_ITEMS, amount);
                        Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5000_ITEMS, amount);
                        StarterTasks.doProgress(player, StarterTasks.StarterTask.SALAGE_5_ITEMS, amount);

                        if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
                            MediumTasks.doProgress(player, MediumTasks.MediumTaskData.SALVAGE_50_ITEMS, amount);
                        }
                        if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
                            EliteTasks.doProgress(player, EliteTasks.EliteTaskData.SALVAGE_100_ITEMS, amount);
                        }

                        //HANDLE DISSOLVE BOSS INCREMENTING
                        if (data.getRewards()[0].getId() == shards) {
                            DissolveBossHandler.increase(player, data.getRewards()[0].getAmount() * amount);
                        }
                        //System.out.println("GLOBAL DISSOLVE AMOUNT: " + GameSettings.DISSOLVE_AMOUNT);
                        return;
                    }
                    player.getInventory().delete(id, amount);
                    player.getInventory().add(data.getRewards()[0].getId(), (data.getRewards()[0].getAmount() * amount));
                    player.getSkillManager().addExperience(Skill.ALCHEMY, (data.getExperience() * amount));
                    player.performAnimation(new Animation(data.getAnimation()));
                    player.sendMessage("<shad=0><col=AF70C3>You Salvaged @red@" + amount + "<shad=0><col=AF70C3>X @red@" + ItemDefinition.forId(id).getName() + " <shad=0><col=AF70C3>for x" + Misc.insertCommasToNumber(data.getRewards()[0].getAmount() * amount) + " " + ItemDefinition.forId(data.getRewards()[0].getId()).getName());
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_25_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_50_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_150_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_250_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_500_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_1000_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_2500_ITEMS, amount);
                    Achievements.doProgress(player, Achievements.Achievement.SALVAGE_5000_ITEMS, amount);
                    StarterTasks.doProgress(player, StarterTasks.StarterTask.SALAGE_5_ITEMS, amount);

                    if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
                        MediumTasks.doProgress(player, MediumTasks.MediumTaskData.SALVAGE_50_ITEMS, amount);
                    }
                    if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
                        EliteTasks.doProgress(player, EliteTasks.EliteTaskData.SALVAGE_100_ITEMS, amount);
                    }

                    //HANDLE DISSOLVE BOSS INCREMENTING

                    if (data.getRewards()[0].getId() == shards) {
                        DissolveBossHandler.increase(player, data.getRewards()[0].getAmount() * amount);
                    }
                    break;
                }
            }
        }
    }
}






