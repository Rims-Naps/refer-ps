package com.ruse.world.content.instanceMananger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InstanceData {

	//EASY
	Nythor(13747, "Nythor", 1125),
	Terran(1801, "Terran", 2375),
	Aqualorn(9027, "Aqualorn", 2000),
	Ferna( 1802, "Ferna", 2500),
	Ignox( 13458, "Ignox", 1200),
	Crystalis(8006, "Crystalis", 1200),
	Ember(688, "Ember", 1000),
	Xerces(350, "Xerces", 1100),
	Marina(182, "Marina", 2500),

	//INTERMEDIATE
	Kezel(9815, "Kezel", 1200),
	Hydrora(1741, "Hydrora", 1200),
	Infernus(12228, "Infernus", 2500),
	Tellurion(9026, "Tellurion", 1200),
	Marinus(1150, "Marinus", 1200),
	Pyrox(9837, "Pyrox", 1200),
	Astaran(9002, "Astaran", 1300),
	Nereus(7000, "Nereus", 1200),
	Volcar(1821, "Volcar", 1200),

	//ELITE
	Lagoon(1727, "Lagoon", 1200),
	Incendia(1729, "Incendia", 1200),
	Terra(1730, "Terra", 1200),
	Abyss(1731, "Abyss", 1200),
	Pyra(1735, "Pyra", 1265),
	Geode(5539, "Geode", 1200),
	Cerulean(5547, "Cerulean", 1165),
	Scorch(5533, "Scorch", 1200),
	Geowind(5553, "Geowind", 1200),

	//MASTER
	Goliath(1072, "Goliath", 1200),
	Volcanus(1073, "Volcanus", 1200),
	Nautilus(1074, "Nautilus", 1200),
	Quake(1075, "Quake", 1200),
	Scaldor(1076, "Scaldor", 1200),
	Seabane(1077, "Seabane", 1200),
	Rumble(1078, "Rumble", 1200),
	Moltron(1079, "Moltron", 1200),
	Hydrox(1080, "Hydrox", 1200),

/*
	Easter_Rabbit(1194, "Easter Rabbit", 460),
*/

	;

	private int npcId;
	private String name;
	private int zoom;
}
