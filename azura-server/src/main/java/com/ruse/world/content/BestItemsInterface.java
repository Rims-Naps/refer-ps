package com.ruse.world.content;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BestItemsInterface {
    private static String[] text = new String[]{"Stab", "Slash", "Crush", "Magic", "Range", "Stab", "Slash", "Crush",
            "Magic", "Range", "Strength", "Ranged Str", "Magic Damage"};
    public static List<Integer> itemsToIgnore = new ArrayList() {
        {

            add(23055);//Ophidian sword
            add(23132);//Creator's scythe
            add(12535);//Execution Vitur
            add(23159);//Melee max cape
            add(20549);//Shikruu Katana
            add(14019);//Max Cape
            add(23050);//Ophidian helm
            add(23051);//Ophidian body
            add(23052);//Ophidian legs
            add(23073);//Sacred Slayer helm (T6)
            add(23095);//Mercenary gloves
            add(23127);//Creator's helm
            add(23128);//Creator's body
            add(23129);//Creator's legs
            add(23072);//Roseblood Slayer helm (T5)
            add(23074);//Sea urchin Slayer helm (T4)
            add(22084);//Blade Trinity
            add(23070);//Deep blue Slayer helm (T3)
            add(4684);//Black parthat
            add(4685);//Execution body
            add(4686);//Execution legs
            add(23069);//Cactus Slayer helm (T2)
            add(23053);//Ophidian gloves
            add(23054);//Ophidian boots
            add(23056);//Ophidian shield
            add(23130);//Creator's gloves
            add(23131);//Creator's boots
            add(23133);//Creator's wings
            add(23071);//Golden Slayer helm (T1)
            add(19161);//Combatant legs
            add(19163);//Combatant body
            add(19164);//Combatant hood
            add(14915);//Demonic Sword
            add(23094);//Valor's warrior ring
            add(23102);//War gauntlets (T4)
            add(11421);//Belligerent helm
            add(11422);//Belligerent body
            add(11423);//Belligerent legs
            add(8273);//Execution gloves
            add(8274);//Execution boots
            add(9939);//Execution cape
            add(11304);//Defender cape
            add(8334);//Art's boots
            add(15005);//Gladiator full helm
            add(15006);//Gladiator fighterbody
            add(15007);//Gladiator fighterlegs
            add(15008);//Gladiator kiteshield
            add(23090);//Upgraded collectors necklace
            add(23101);//War gauntlets (T3)
            add(18001);//COL Torva body
            add(18003);//COL Torva legs
            add(18005);//COL wings
            add(18007);//COL kiteshield
            add(18009);//COL boots
            add(18011);//COL whip
            add(16254);//Promethium spear (b)
            add(16255);//Promethium spear (p) (b)
            add(16256);//Promethium spear (p+) (b)
            add(16257);//Promethium spear (p++) (b)
            add(17135);//Promethium spear
            add(17137);//Promethium spear (p)
            add(17139);//Promethium spear (p+)
            add(17141);//Promethium spear (p++)
            add(4367);//Art's cape
            add(8819);//Bulwark gloves
            add(13999);//Hydra claws
            add(22074);//Floreox scimitar
            add(16043);//Penguin rapier
            add(19784);//Korasi's sword
            add(21037);//Mystic body
            add(21039);//Mystic cape
            add(16250);//Gorgonite spear (b)
            add(16251);//Gorgonite spear (p) (b)
            add(16252);//Gorgonite spear (p+) (b)
            add(16253);//Gorgonite spear (p++) (b)
            add(17127);//Gorgonite spear
            add(22114);//Dark Sanguinesti Staff
            add(7995);//Owner's cape
            add(17013);//Light Sanguinesti Staff
            add(17011);//Sanguinesti Execution Staff
            add(13640);//Groudon-flame staff
            add(23026);//Magic staff
            add(23176);//Master max cape
            add(17600);//transform staff
            add(15645);//Elite helm (mage)
            add(15646);//Elite body (mage)
            add(15647);//Elite legs (mage)
            add(18629);//Corrupt moonlight staff
            add(23021);//Magic helm
            add(23022);//Magic body
            add(23023);//Magic legs
            add(23158);//Magic max cape
            add(3726);//Azure helm
            add(3728);//Azure body
            add(3730);//Azure legs
            add(14019);//Max Cape
            add(23073);//Sacred Slayer helm (T6)
            add(23075);//Elven helm
            add(23076);//Elven body
            add(23077);//Elven legs
            add(23095);//Mercenary gloves
            add(23127);//Creator's helm
            add(23128);//Creator's body
            add(23129);//Creator's legs
            add(3107);//Groudon-flame boots
            add(13964);//Groudon-flame shield
            add(15448);//Groudon-flame power
            add(19913);//Groudon-flame legs
            add(19918);//Groudon-flame body
            add(21934);//Groudon-flame helm
            add(21037);//Mystic body
            add(21039);//Mystic cape
            add(18623);//Corrupt moonlight robebottom
            add(18631);//Corrupt moonlight hood
            add(18637);//Corrupt moonlight robetop
            add(23072);//Roseblood Slayer helm (T5)
            add(17712);//Angels Flamethrower
            add(23074);//Sea urchin Slayer helm (T4)
            add(23024);//Magic gloves
            add(23025);//Magic boots
            add(23027);//Magic book
            add(23070);//Deep blue Slayer helm (T3)
            add(23069);//Cactus Slayer helm (T2)
            add(23096);//Wizard gloves
            add(23130);//Creator's gloves
            add(23131);//Creator's boots
            add(23133);//Creator's wings
            add(23071);//Golden Slayer helm (T1)
            add(17704);//Astrogun (Halo)
            add(19161);//Combatant legs
            add(19163);//Combatant body
            add(19164);//Combatant hood
            add(22091);//Legion scythe
            add(14924);//Icey staff of festive
            add(23092);//Valor's wizard ring
            add(23102);//War gauntlets (T4)
            add(17608);//Darkrealm helm
            add(17610);//Darkrealm body
            add(17612);//Darkrealm legs
            add(11421);//Belligerent helm
            add(11422);//Belligerent body
            add(11423);//Belligerent legs
            add(14385);//Staff (class 5)
            add(15922);//Maleficent helm
            add(15933);//Maleficent legs
            add(16021);//Maleficent body
            add(8806);//Ganopurp visor
            add(8809);//Tormented staff
            add(11304);//Defender cape
            add(22113);//Dark Twisted Bow
            add(5011);//Iron Bow
            add(9941);//Execution blowpipe
            add(22006);//Deathtouch Darts
            add(8253);//Defiled minigun
            add(22089);//Assault Rifle
            add(8001);//Custom Extreme Crossbow
            add(8088);//Athens Crossbow
            add(9929);//Lytsu void Rifle
            add(18636);//Corrupt archie minigun
            add(19843);//Archie minigun
            add(5073);//gearset7Rpg
            add(23176);//Master max cape
            add(23066);//Avian crossbow
            add(12930);//Swoodo Shield
            add(19149);//Swoodo bow
            add(21054);//Cursed Ballista
            add(21055);//Hidden Ballista
            add(15642);//Elite helm (ranged)
            add(15643);//Elite body (ranged)
            add(15644);//Elite legs (ranged)
            add(21056);//Damned Ballista
            add(11340);//Max range halo
            add(11341);//Max range body
            add(11342);//Max range legs
            add(21053);//forbidden Ballista
            add(3723);//Herbal helm
            add(3724);//Herbal body
            add(3725);//Herbal legs
            add(14019);//Max Cape
            add(23073);//Sacred Slayer helm (T6)
            add(23095);//Mercenary gloves
            add(23127);//Creator's helm
            add(23128);//Creator's body
            add(23129);//Creator's legs
            add(8336);//eatmycaca helm
            add(8337);//eatmycaca body
            add(8338);//eatmycaca chaps
            add(5068);//gearset7helm
            add(5069);//gearset7body
            add(5070);//gearset7legs
            add(6818);//Bow Sword of 1k Truths
            add(22090);//Golden Rifle
            add(14172);//Optimus suit body
            add(14174);//Optimus suit legs
            add(14176);//Optimus suit helmet
            add(18638);// Corrupt archie chaps
            add(18748);//Corrupt archie body
            add(18749);//Corrupt archie helm
            add(23072);//Roseblood Slayer helm (T5)
            add(18623);//Corrupt moonlight robebottom
            add(18631);//Corrupt moonlight hood
            add(18637);//Corrupt moonlight robetop
            add(23074);//Sea urchin Slayer helm (T4)
            add(22083);//BlastBomb Cannon
            add(15785);//Frozen shortbow
            add(671);//Imp HandCannon
            add(8330);//T4 Range helm
            add(8331);//T4 Range body
            add(8332);//T4 Range legs
            add(11001);//Hiddenvally coif
            add(11002);//Hiddenvally leatherbody
            add(11003);//Hiddenvally leatherchaps
            add(23070);//Deep blue Slayer helm (T3)
            add(19474);//Archie chaps
            add(19618);//Archie body
            add(19619);//Archie helm
            add(14060);//gearset8 helm
            add(14061);//gearset8 body
            add(14062);//gearset8 legs
            add(22088);//AK-47
            add(23069);//Cactus Slayer helm (T2)
            add(23130);//Creator's gloves
            add(23131);//Creator's boots
            add(23133);//Creator's wings
            add(5071);//gearset7gloves
            add(5072);//gearset7boots
            add(5424);
            add(9253);
            add(22071);
            add(22076);
            add(21604);
            add(17694);
            add(17694);
            add(17694);
            add(17694);
            add(17694);
            add(17694);
            add(17694);
            add(17694);
            add(17694);
            add(17694);


            add(17694);
            add(17696);
            add(21603);
            add(5423);
            add(5432);
            add(7014);
            add(19800);
            add(19802);
            add(20060);
            add(20062);
            add(20063);
            add(20073);
            add(7642);
            add(17678);
            add(7643);
            add(20533);
            add(20551);
            add(20552);
            add(20558);
            add(11179);
            add(11181);
            add(11182);
            add(11183);
            add(11184);
            add(11759);
            add(11762);
            add(5594);

            add(12285);

            add(21780);
            add(21605);
            add(21602);
            add(21601);
            add(21600);
            add(22093);
            add(21606);
            add(15219);
            add(1485);
            add(22073);
            add(14055);
            add(14054);
            add(14053);
            add(14052);
            add(14051);
            add(14050);


            add(17700);//Wrath Hammer Offhand
            add(18881);//Supreme Boots
            add(18883);//Supreme gloves
            add(19810);//Supreme spirit shield
            add(22118);//Group Ironman's Aura
            add(22119);//Ultimate Ironman's Aura
            add(22120);//Ironman's Aura
            add(21058);//dualsaber
            add(11320);//Max melee helm
            add(11321);//Max melee body
            add(11322);//Max melee legs
            add(3720);//Scarlet helm
            add(3721);//Scarlet body
            add(3722);//Scarlet legs
            add(4018);//Extreme Offhand
            add(15115);//Extreme helm
            add(15116);//Extreme body
            add(15117);//Extreme legs
            add(15118);//Extreme gloves
            add(15119);//Extreme boots
            add(21607);//Custom Extreme helm
            add(21608);//Custom Extreme body
            add(21609);//Custom Extreme legs
            add(21610);//Custom Extreme Teddy bear
            add(21611);//Custom Extreme gloves
            add(21612);//Custom Extreme boots
            add(734);//Demon big maul
            add(17708);//Joyxox Sword
            add(7543);//Perfect cell Rifle
            add(7544);//Perfect Cell Teddybear
            add(7545);//Perfect Cell apparatus
            add(9478);//Super buu helm
            add(9479);//Super 	 body
            add(9480);//Super buu legs
            add(9481);//Perfect cell helm
            add(9482);//Perfect cell body
            add(9483);//Perfect cell legs
            add(11763);//Freiza helm
            add(11764);//Freiza body
            add(11765);//Freiza legs
            add(11766);//Freiza gloves
            add(11767);//Freiza boots
            add(13323);//Goku head
            add(13324);//Goku body
            add(13325);//Goku legs
            add(14066);//Crystalized wings
            add(15278);//Super Buu Pet
            add(15832);//Super buu ward
            add(16265);//Super buu boots
            add(17702);//Freiza claws
            add(18838);//Dollar Chain
            add(22079);//ankoue maul
            add(21036);//Exorcism helm
            add(21038);//Exorcism legs
            add(523);//Saggy helm
            add(524);//Saggy body
            add(525);//Saggy legs
            add(5420);//Madman helm
            add(5422);//Madman body
            add(5428);//Madman legs
            add(14068);//Garfield mask
            add(14069);//Garfield body
            add(14070);//Garfield legs
            add(18751);//Corrupt Maxiblood legs
            add(18752);//Corrupt Maxiblood platebody
            add(18753);//Corrupt Maxiblood helm
            add(22075);//Starlight sword
            add(15449);//Extreme Aura
            add(15450);//Custom Extreme Aura
            add(22080);//Heavy chainsaw
            add(15230);//Zeus full helm
            add(15231);//Zeus body
            add(15232);//Zeus legs
            add(15234);//Zeus shield
            add(15235);//jinx gloves
            add(15236);//jinx boots
            add(17662);//zeus flagcape
            add(19984);//LegendarySlayer helm
            add(19985);//LegendarySlayer body


            add(19986);//LegendarySlayer legs
            add(22078);//Demon maul
            add(13023);//Grinch legs
            add(13025);//Grinch head
            add(13027);//Grinch body
            add(26);//Sassy Aura
            add(522);//Saggy's broomstick
            add(12608);//SS Aura
            add(21031);//jinx full helm
            add(21032);//jinx body
            add(21033);//jinx legs
            add(21035);//jinx shield
            add(17718);//Batman's bow
            add(17734);//GreatRealm Axe
            add(9250);//Yogifus helm
            add(9251);//Yogifus body
            add(9252);//Yogifus legs
            add(13300);//Satanic full helm
            add(13301);//Satanic hell body
            add(13304);//Satanic hell legs
            add(13306);//Satanic anti-hellshield
            add(19165);//Darthvader legs
            add(19166);//Darthvader body
            add(19468);//Darthvader mask
            add(19994);//LegendarySlayer cape
            add(21050);//Slayermaster hood
            add(21051);//Slayermaster body
            add(21052);//Slayermaster legs
            add(21062);//Subzzero Helmet
            add(21063);//Subzzero body
            add(21064);//Subzzero legs
            add(19940);//Maxiblood legs
            add(19941);//Maxiblood platebody
            add(19943);//Maxiblood helm
            add(20790);//Golden mining trousers
            add(20791);//Golden mining top
            add(20792);//Golden mining helmet
            add(9054);//Creeper helm
            add(9055);//Creeper body
            add(9056);//Creeper legs
            add(9057);//Creeper gloves
            add(9058);//Creeper boots
            add(11303);//Defender kiteshield
            add(11305);//Earthquake full helm
            add(11306);//Earthquake platebody
            add(11307);//Earthquake platelegs
            add(11308);//Earthquake battlesword
            add(14018);//Tornado Katana
            add(20555);//Scythe of vitur
            add(23097);//Warrior gloves
            add(676);//Lord longsword
            add(17726);//COL sword II
            add(21071);//Subzzero cape
            add(17724);//COL sword I
            add(13931);//Gilded spear
            add(8857);//Nebula helm
            add(8858);//Nebula body
            add(8859);//Nebula legs
            add(9254);//Madman helm
            add(9255);//Madman platebody
            add(9256);//Madman legs
            add(17714);//Lucien Axe
            add(22081);//Wooden chainsaw
            add(13925);//Gilded longsword
            add(14056);//Alex Athens bow


            add(16045);//Frozen rapier
            add(19987);//L.S spirit shield
            add(19988);//LegendarySlayer gloves
            add(19989);//LegendarySlayer boots
            add(22004);//Master Slayer Helmet [5]
            add(4555);//Riddler's helm
            add(4556);//Riddler's body
            add(4557);//Riddler's legs
            add(8940);//Bulwark helm
            add(8941);//Bulwark body
            add(8942);//Bulwark legs
            add(20559);//Rabies spreader (offhand)
            add(19957);//Tetsu legs
            add(19958);//Tetsu body
            add(19959);//Tetsu helm
            add(22070);//Blizzardspike longsword
            add(4558);//Riddler's Offhand
            add(8834);//Predator Reaper
            add(8835);//Predator Offhand
            add(11798);//Dinh's bulwark
            add(13760);//Elder Maul
            add(18823);//Collector ring II
            add(19888);//Collector necklace II
            add(20086);//Cursed helm
            add(20087);//Cursed body
            add(20088);//Cursed legs
            add(20089);//Cursed boots
            add(20090);//Cursed spirit shield
            add(20091);//Cursed gloves
            add(20092);//Cursed amulet
            add(20093);//Cursed ring
            add(20099);//Cursed cape
            add(21025);//Samurai head
            add(21026);//Samurai body
            add(21027);//Samurai legs
            add(21028);//eSamurai head
            add(21029);//eSamurai body
            add(21030);//eSamurai legs
            add(19919);//Arc rapier
            add(21057);//lightsaber
            add(22077);//Brutal Tentacle
            add(13326);//Goku gloves
            add(13327);//Goku boots
            add(8816);//Bulwark helm
            add(8817);//Bulwark body
            add(8818);//Bulwark legs
            add(15877);//Nuetron Dagger
            add(13302);//Satanic gloves
            add(13305);//Satanic boots
            add(14071);//Garfield gloves
            add(14072);//Garfield boots
            add(15511);//Royal amulet



            add(15818);//Lucien Defender
            add(15924);//Lucien helm
            add(15935);//Lucien legs
            add(16023);//Lucien platebody
            add(17686);//Lucien Crypt wings
            add(18416);//Water Partyhat
            add(18417);//Firehell Partyhat
            add(18418);//Velvet Partyhat
            add(18419);//Universal Partyhat
            add(18683);//Satanic tokhaar-kal
            add(18817);//Ring of sauron (hell)
            add(22003);//Master Slayer Helmet [4]
            add(17624);//Shadow whip
            add(17730);//COL shield II
            add(14305);//Dagger (class 5)
            add(8329);//Art's Defender
            add(9257);//Epic Defender
            add(1486);//Creeper Cape
            add(2021);//Angelic wings
            add(15593);//Angelic platelegs
            add(16140);//Angelic amulet
            add(17638);//Angelic helm
            add(17640);//Angelic body
            add(17684);//Wyvern Cape
            add(19992);//L.S ring
            add(21065);//Subzzero spirit shield
            add(21066);//Subzzero gloves
            add(21067);//Subzzero boots
            add(21068);//Subzzero amulet
            add(21069);//Subzzero ring
            add(22002);//Master Slayer Helmet [3]
            add(14024);//Drygore Rapier
            add(14303);//Dagger (class 4)
            add(13905);//Vesta's spear
            add(13907);//Vesta's spear (deg)
            add(13929);//Corrupt vesta's spear
            add(16258);//Primal spear (b)
            add(16259);//Primal spear (p) (b)
            add(16260);//Primal spear (p+) (b)
            add(16261);//Primal spear (p++) (b)
            add(17143);//Primal spear
            add(17145);//Primal spear (p)
            add(17147);//Primal spear (p+)
            add(17149);//Primal spear (p++)
            add(18790);//Tsutsaroth spear
            add(17728);//COL shield I
            add(17999);//COL Torva helm

        }
    };

    public static boolean buttonClicked(Player player, int buttonId) {
        if (buttonId >= 100010 && buttonId <= 100014) {
            int index = (buttonId - 100010);
            openInterface(player, index);
            return true;
        } else if (buttonId >= 100016 && buttonId <= 100020) {
            int index = (buttonId - 100016) + 5;
            openInterface(player, index);
            return true;
        } else if (buttonId == 100022) {
            openInterface(player, 14);
            return true;
        } else if (buttonId == 100023) {
            openInterface(player, 15);
            return true;
        } else if (buttonId == 100024) {
            openInterface(player, 17);
            return true;
        }
        return false;
    }

    public static void openInterface(Player player, int bonus) {

        for (int i = 0; i < 5; i++) {
            player.getPacketSender().sendString(100010 + i, (bonus == i ? "@whi@Check " : "Check ") + text[i]);
        }
        for (int i = 5; i < 10; i++) {
            player.getPacketSender().sendString(100011 + i, (bonus == i ? "@whi@Check " : "Check ") + text[i]);
        }
        player.getPacketSender().sendString(100022, (bonus == 14 ? "@whi@Check " : "Check ") + text[10]);
        player.getPacketSender().sendString(100023, (bonus == 15 ? "@whi@Check " : "Check ") + text[11]);
        player.getPacketSender().sendString(100024, (bonus == 17 ? "@whi@Check " : "Check ") + text[12]);

        ArrayList<ItemDefinition> objects = new ArrayList<>();

        for (ItemDefinition i : ItemDefinition.getDefinitions()) {
            if (i != null) {
                if (!i.isNoted() && i.getBonus()[bonus] > 0 && !itemsToIgnore.contains(i.getId()))// && i.getEquipmentSlot() ==
                    // Equipment.BODY_SLOT)
                    objects.add(i);
            }
        }

        Collections.sort(objects, new Comparator<ItemDefinition>() {
            @Override
            public int compare(ItemDefinition p, ItemDefinition p1) {
                if (p.getBonus()[bonus] == p1.getBonus()[bonus]) {
                    return 0;
                } else if (p.getBonus()[bonus] > p1.getBonus()[bonus]) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        int interId = 100102;
        int size = (objects.size() >= 100 ? 100 : objects.size());
        for (int i = 0; i < size; i++) {
            player.getPacketSender().sendString(interId++,
                    "" + objects.get(i).getBonus()[bonus]);
            player.getPacketSender().sendString(interId++, "" + objects.get(i).getName());
            player.getPacketSender().sendItemOnInterface(interId++, objects.get(i).getId(), 1);

            System.out.println("add(" + objects.get(i).getId() + ");//" + objects.get(i).getName());
            interId++;
        }
        player.getPacketSender().sendInterface(100000);

        player.getPacketSender().setScrollBar(100050, size * 40);

    }

}