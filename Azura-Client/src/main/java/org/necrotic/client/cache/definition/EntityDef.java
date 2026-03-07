package org.necrotic.client.cache.definition;

import org.necrotic.Configuration;
import org.necrotic.client.Client;
import org.necrotic.client.cache.config.VarBit;
import org.necrotic.client.collection.MRUNodes;
import org.necrotic.client.net.Stream;
import org.necrotic.client.media.AnimationSkeleton;
import org.necrotic.client.cache.Archive;
import org.necrotic.client.media.renderable.Model;

import java.util.HashMap;

public final class EntityDef {
	public int rdc = 0;
	public int rdc2 = 0;
	public int rdc3 = 0;
	public static Client clientInstance;
	public static MRUNodes mruNodes = new MRUNodes(30);
	private static Stream buffer;
	private static int[] streamIndices;
	public static HashMap<Integer, EntityDef> map = new HashMap<>();

	public static void applyTexturing1(Model model, int id) {
		switch (id) {


			//	case 1614:
			//model.setTexture(72);
			//	break;
		}
	}

	//NEW HOME NPCS MODELS

	//FEMALE - 64758

	public static EntityDef get(int id) {
		EntityDef definition = map.get(id);
		if (definition != null) {
			return definition;
		}
		definition = new EntityDef();
		if (id >= streamIndices.length) {
			return null;
		}
		buffer.position = streamIndices[id];
		definition.id = id;
		definition.readValues(buffer);


		if (definition.name != null && definition.name.toLowerCase().contains("bank")) {
			if (definition.actions != null) {
				for (int l = 0; l < definition.actions.length; l++) {
					if (definition.actions[l] != null && definition.actions[l].equalsIgnoreCase("Collect")) {
						definition.actions[l] = null;
					}
				}
			}
		}



		try {
			switch (id) {
				case 2097:
					definition.setDefault();
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.name = "Fallen Knight";
					definition.npcModels = new int[]{132153};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 185;
					definition.scaleY = 185;
					break;
				case 2200:
					definition.setDefault();
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.name = "Fallen Minion Range";
					definition.npcModels = new int[]{132155};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 2201:
					definition.setDefault();
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.name = "Fallen Minion Mage";
					definition.npcModels = new int[]{132154};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 2341:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.name = "Dono Boss";
					definition.npcModels = new int[]{33336,130980,130982,130983,130984,130986,130987,130988};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 320;
					definition.scaleY = 320;
					break;
				case 2342:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132029};
					definition.name = "Vote Boss";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					break;

				case 555:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132209};
					definition.name = "Archonic C'thulu";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					break;
				case 556:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132206};
					definition.name = "Celestial Orc";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 225;
					definition.scaleY = 225;
					break;
				case 557:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132208};
					definition.name = "Ascendant Berserker";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					break;
				case 559:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132034};
					definition.name = "Gladiator";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					break;
				case 786:
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.name = "Refund Shop";
					break;
				case 1779:
					definition.npcModels = new int[]{130595};//GAIA CRYSTAL 1
					definition.name = "Gaia Minion 1";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1780:
					definition.npcModels = new int[]{130598};//GAIA CRYSTAL 2
					definition.name = "Gaia Minion 2";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1781:
					definition.npcModels = new int[]{130602};//GAIA CRYSTAL 3
					definition.name = "Gaia Minion 3";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1782:
					definition.npcModels = new int[]{130596};//LAVA CRYSTAL 1
					definition.name = "Lava Minion 1";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1783:
					definition.npcModels = new int[]{130600};//LAVA CRYSTAL 2
					definition.name = "Lava Minion 2";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1775:
					definition.npcModels = new int[]{130601};//LAVA CRYSTAL 3
					definition.name = "Lava Minion 3";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				//101869 = new brutal upgrader
				case 1776:
					definition.npcModels = new int[]{130599};//AQUA CRYSTAL 1
					definition.name = "Aqua Minion 1";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					break;
				case 1777:
					definition.npcModels = new int[]{130597};//AQUA CRYSTAL 2
					definition.name = "Aqua Minion 2";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1778:
					definition.npcModels = new int[]{130603};//AQUA CRYSTAL 3
					definition.name = "Aqua Minion 3";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;


/*				case 1784:
					definition.npcModels = new int[]{101860};//ASTRAL
					definition.name = "<img=12>@mag@Beherit<img=12>";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1788:
					definition.npcModels = new int[]{101862};//ASTRAL
					definition.name = "<img=12>@mag@Asteroth<img=12>";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1786:
					definition.npcModels = new int[]{101861};//ASTRAL
					definition.name = "<img=12>@mag@Lilith<img=12>";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1787:
					definition.npcModels = new int[]{101863};//ASTRAL
					definition.name = "<img=12>@mag@Argon<img=12>";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1790:
					definition.npcModels = new int[]{101841};//ASTRAL
					definition.name = "XNEED @red@Astral Minion 3 XNEED ";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 408:
					definition.name = "@red@Defender Demon(1)";
					definition.npcModels = new int[]{101452};
					definition.combatLevel = 83;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 1;
					break;*/
				case 12239:
					definition.name = "Geomancer";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131902};
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.degreesToTurn = 32;
					break;
				case 9814:
					definition.name = "Aquamancer";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131903};
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.degreesToTurn = 32;
					break;
				case 9813:
					definition.name = "Pyromancer";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131901};
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.degreesToTurn = 32;
					break;
				case 995:
					definition.name = "Pyromancer";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131901};
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.degreesToTurn = 32;
					break;
				case 6307:
					definition.name = "@red@Ember Egg";
					definition.npcModels = new int[]{131912};
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.actions = new String[]{"Open", null, null, null, null};
					break;
				case 6314:
					definition.name = "@cya@Miraculous Egg";
					definition.npcModels = new int[]{131913};
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.actions = new String[]{"Open", null, null, null, null};
					break;
				case 6315:
					definition.name = "@cya@Exotic Egg";
					definition.npcModels = new int[]{131914};
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.actions = new String[]{"Open", null, null, null, null};
					break;
				case 6316:
					definition.name = "@cya@Super Egg";
					definition.npcModels = new int[]{131915};
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.actions = new String[]{"Open", null, null, null, null};
					break;
				case 1789:
					definition.name = "@cya@Bunny Boss";
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 300;
					definition.scaleY = 300;
					definition.npcModels = new int[9];
					definition.npcModels[0] = 101873; //HEAD
					definition.npcModels[2] = 131804; //BODY
					definition.npcModels[7] = 131807; //LEG
					definition.npcModels[4] = 120043; //HAND
					definition.npcModels[8] = 131810; //BOOT
					definition.npcModels[3] = 131825; //CAPE
					definition.npcModels[1] = 101822; //AURA
					definition.npcModels[6] = 5811; //WEP
					definition.npcModels[5] = 5811; //shield
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					break;
				case 1194:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.name = "@cya@Easter Rabbit";
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					break;
				case 409:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.name = "@red@Rabid Bunny";
					definition.npcModels = new int[]{23901};
					definition.standAnimation = 6089;
					definition.walkAnimation = 6088;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					break;
				case 9028:
					definition.name = "Easter Bunny";
					definition.npcModels = new int[]{16067};
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 2;
					break;
				case 2709:
				case 2710:
				case 2711:
					definition.scaleXZ = 140;
					definition.scaleY = 140;
					definition.name = "Elemental Wizard";
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					break;
				case 2262:
					definition.name = "Necromancy Shop";
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.drawYellowDotOnMap = true;
					break;
				case 316:
				    definition.name = "Soulbane";
				    definition.npcModels = new int[]{130438};
				    definition.standAnimation = 5538;
				    definition.walkAnimation = 5539;
				    definition.scaleXZ = 100;
				    definition.scaleY = 100;
				    definition.npcSizeInSquares = 4;
				    definition.combatLevel = 999;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 554:
					definition.name = "Soulbane";
					definition.npcModels = new int[]{130438};
					definition.standAnimation = 5538;
					definition.walkAnimation = 5539;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.npcSizeInSquares = 4;
					definition.combatLevel = 999;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 315:
					definition.name = "Undead Horror";
					definition.npcModels = new int[]{48292};
					definition.standAnimation = 12074;
					definition.walkAnimation = 12078;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 309:
					definition.name = "Undead Guardian";
					definition.npcModels = new int[]{24188, 24050};
					definition.standAnimation = 6113;
					definition.walkAnimation = 6112;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 310:
					definition.name = "Undead Abomination";
					definition.npcModels = new int[]{130437};
					definition.standAnimation = 5616;
					definition.walkAnimation = 5615;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 314://5534 death
					definition.name = "Undead Spirit";
					definition.npcModels = new int[]{21145};
					definition.standAnimation = 5530;
					definition.walkAnimation = 5531;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;

				case 9176:
					definition.name = "Horror";
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					break;
				case 3052:
					break;
				case 1745:
					definition.name = null;
					definition.npcModels = new int[]{43075};
					definition.standAnimation = 10586;
					definition.walkAnimation = 10586;
					definition.scaleXZ = 300;
					definition.scaleY = 300;
					definition.actions = new String[]{null, null, null, null, null};
					definition.drawYellowDotOnMap = false;
					break;
				case 5040:
					definition.npcModels = new int[]{45970};
					definition.name = "Boss Portal";
					definition.standAnimation = 11334;
					definition.walkAnimation = 11334;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 2017:
					definition.name = "Brimstone";
					definition.combatLevel = 999;
					definition.npcModels = new int[]{30536};
					definition.standAnimation = 7831;
					definition.walkAnimation = 7828;
					definition.scaleXZ = 185;
					definition.scaleY = 185;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					break;
				case 2018:
					definition.name = "Basilisk";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[1];
					definition.npcModels[0] = 130435;
					definition.standAnimation = 5070;
					definition.walkAnimation = 5070;
					definition.combatLevel = 725;
					definition.scaleY = 150;
					definition.scaleXZ = 150;
					definition.npcSizeInSquares = 2;
					break;

				case 2465:
					definition.name = "Oracle";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{131684};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 14887;
					definition.walkAnimation = 14888;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					break;
				case 2466:
					definition.name = "Rockmaw";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{55252};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 12998;
					definition.walkAnimation = 12999;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 2467:
					definition.name = "Grimlash";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{55257};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 14381;
					definition.walkAnimation = 14372;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
					//TOWER MOBS
				case 2019:
					definition.name = "Earth Elemental";
					definition.combatLevel = 999;
					definition.npcModels = new int[]{131641};
					definition.standAnimation = 7831;
					definition.walkAnimation = 7828;
					definition.scaleXZ = 185;
					definition.scaleY = 185;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					break;
				case 2022:
					definition.name = "Water Elemental";
					definition.combatLevel = 999;
					definition.npcModels = new int[]{131642};
					definition.standAnimation = 7831;
					definition.walkAnimation = 7828;
					definition.scaleXZ = 185;
					definition.scaleY = 185;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					break;
				case 2021:
					definition.name = "Fire Elemental";
					definition.combatLevel = 999;
					definition.npcModels = new int[]{131643};
					definition.standAnimation = 7831;
					definition.walkAnimation = 7828;
					definition.scaleXZ = 185;
					definition.scaleY = 185;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					break;
				case 6522:
					definition.name = "Fire Elemental";
					definition.combatLevel = 999;
					definition.npcModels = new int[]{131643};
					definition.standAnimation = 7831;
					definition.walkAnimation = 7828;
					definition.scaleXZ = 185;
					definition.scaleY = 185;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					break;

				case 2023:
					definition.name = "Earth Golem";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131644};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;
				case 2024:
					definition.name = "Water Golem";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131645};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;
				case 2025:
					definition.name = "Fire Golem";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131646};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;
				case 2034:
					definition.name = "@red@Tower Superior";
					definition.combatLevel = 999;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131662};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 240;
					definition.scaleY = 240;
					definition.degreesToTurn = 32;
					break;
				case 1853://COMMON RAID
					definition.name = "Common Chest";
					definition.actions = new String[]{"Open", null, null, null, null};
					definition.npcModels = new int[]{131647};
					definition.combatLevel = 0;
					definition.standAnimation = 0;
					definition.walkAnimation = 0;
					break;
				case 1854://Uncommon RAID
					definition.name = "@cya@Uncommon Chest";
					definition.actions = new String[]{"Open", null, null, null, null};
					definition.npcModels = new int[]{131648};
					definition.combatLevel = 0;
					definition.standAnimation = 0;
					definition.walkAnimation = 0;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					break;
				case 1855://RARE RAID
					definition.name = "@red@Rare Chest";
					definition.actions = new String[]{"Open", null, null, null, null};
					definition.npcModels = new int[]{131649};
					definition.combatLevel = 0;
					definition.standAnimation = 0;
					definition.walkAnimation = 0;
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					break;
				case 6808:
					definition.npcModels = new int[]{104036};
					definition.name = "Celestia";
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 170;
					definition.scaleY = 170;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;

					break;
				case 5566:
					definition.name = "Necrosomething";
					//definition.npcModels = new int[]{55741};
					definition.combatLevel = 83;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					break;
				case 6830:
					definition.name = "Easy Superior";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{130216};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;
				case 6841:
					definition.name = "Medium Superior";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{130215};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;
				case 6831:
					definition.name = "Elite Superior";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{130217};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;
				case 3000:
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.name = "@red@Cosmic Cupid";
					definition.npcModels = new int[]{131671};
					definition.combatLevel = 785;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 259;
					definition.scaleY = 259;
					break;
				case 8000:
					definition.name = "Aqua Boss";
					definition.npcModels = new int[]{130063};
					definition.standAnimation = 7772;
					definition.walkAnimation = 7768;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 100;
					break;
				case 8002:
					definition.name = "Lava Boss";
					definition.npcModels = new int[]{130064};
					definition.standAnimation = 7772;
					definition.walkAnimation = 7768;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 100;
					break;
				case 8004:
					definition.name = "Gaia Boss";
					definition.npcModels = new int[]{130062};
					definition.standAnimation = 7772;
					definition.walkAnimation = 7768;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 100;
					break;
				case 13747:
					definition.element = 2;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130123,130124, 130125, 130104, 130127  };
					definition.name = "<col=D45F4B>Nythor";
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					definition.npcSizeInSquares = 1;
					definition.combatLevel = 100;
					break;
				case 1801:
					definition.name = "<col=5FD857>Terran";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{120050};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 100;
					break;
				case 9027:
					definition.name = "<col=1097BF>Aqualorn";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130021};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.drawYellowDotOnMap = true;
					definition.element = 3;
					definition.combatLevel = 100;
					break;
				case 1802:
					definition.name = "<col=5FD857>Ferna";
					definition.npcModels = new int[]{130106};
					definition.combatLevel = 699;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 80;
					definition.scaleY = 80;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.element = 1;
					definition.combatLevel = 150;
					break;
				case 13458:
					definition.name = "<col=D45F4B>Ignox";
					definition.npcModels = new int[]{130022};
					definition.combatLevel = 699;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 170;
					definition.scaleY = 170;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 150;
					break;
				case 8006:
					definition.name = "<col=1097BF>Crystalis";
					definition.npcModels = new int[]{130023};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.drawYellowDotOnMap = true;
					definition.element = 3;
					definition.combatLevel = 150;
					break;
				case 688:
					definition.name = "<col=D45F4B>Ember";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130025};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 192;
					definition.scaleY = 192;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 175;
					break;
				case 350:
					definition.name = "<col=5FD857>Xerces";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130310, 130311, 130312, 130313, 130314};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 175;
					break;
				case 182:
					definition.name = "<col=1097BF>Marina";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130030};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 75;
					definition.scaleY = 75;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 3;
					definition.combatLevel = 175;
					break;
				case 6330:
					definition.name = "<col=D45F4B>Lava Guardian";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130750};
					definition.standAnimation = 8021;
					definition.walkAnimation = 8022;
					definition.scaleXZ = 350;
					definition.scaleY = 350;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 200;
					break;

				case 9815:
					definition.name = "<col=5FD857>Kezel";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130036};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 185;
					definition.scaleY = 185;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 200;
					definition.degreesToTurn = 32;
					break;
				case 1741:
					definition.name = "<col=1097BF>Hydrora";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130037};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 3;
					definition.combatLevel = 200;
					break;
				case 12228:
					definition.name = "<col=D45F4B>Infernus";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130038};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 80;
					definition.scaleY = 80;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 225;
					break;
				case 9026:
					definition.name = "<col=5FD857>Tellurion";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130065, 130066, 130067, 130068, 130069};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 225;
					break;
				case 1150:
					definition.name = "<col=1097BF>Marinus";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130070, 130071, 130072, 130073, 130074};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 3;
					definition.combatLevel = 225;
					break;

				case 9837:
					definition.name = "<col=D45F4B>Pyrox";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130075};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 250;
					break;
				case 9002:
					definition.name = "<col=5FD857>Astaran";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130076};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 250;
					break;
				case 7000:
					definition.name = "<col=1097BF>Nereus";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130077, 130078, 130079, 130080, 130081};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 3;
					definition.combatLevel = 250;
					break;
				case 1821:
					definition.name = "<col=D45F4B>Volcar";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130082, 130083, 130084, 130085, 16275};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 250;
					break;
				case 9800:
					definition.name = "<col=1097BF>Aqua Guardian";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130751};
					definition.standAnimation = 8021;
					definition.walkAnimation = 8022;
					definition.scaleXZ = 350;
					definition.scaleY = 350;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 1727:
					definition.name = "<col=1097BF>Lagoon";
					definition.npcModels = new int[]{130700, 130701, 130702, 130703, 130704};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.drawYellowDotOnMap = true;
					break;
				case 1729:
					definition.name = "<col=D45F4B>Incendia";
					definition.npcModels = new int[]{130705, 130706, 130707, 130709, 130710};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.drawYellowDotOnMap = true;
					break;
				case 1730:
					definition.name = "<col=5FD857>Terra";
					definition.npcModels = new int[]{130710, 130711, 130712, 130713, 130714};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.drawYellowDotOnMap = true;
					break;
				case 1731:
					definition.name = "<col=1097BF>Abyss";
					definition.npcModels = new int[]{130720, 130721, 130722, 130723, 130724};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.drawYellowDotOnMap = true;
					break;
				case 1735:
					definition.name = "<col=D45F4B>Pyra";
					definition.npcModels = new int[]{130725, 130726, 130727, 130728, 130729};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.drawYellowDotOnMap = true;
					definition.headIcon = -1;
					break;
				case 5539:
					definition.name = "<col=5FD857>Geode";
					definition.npcModels = new int[]{130730, 130731, 130732, 130733, 130734};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.drawYellowDotOnMap = true;
					break;
				case 5547:
					definition.name = "<col=1097BF>Cerulean";
					definition.npcModels = new int[]{130735, 130736, 130737, 130738, 130739};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.drawYellowDotOnMap = true;
					break;
				case 5533:
					definition.name = "<col=D45F4B>Scorch";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130031, 130032, 130033 , 130034 , 130035};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 200;
					break;
				case 5553:
					definition.name = "<col=5FD857>Geowind";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130086, 130087, 130088, 130089, 130090};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 3117:
					definition.name = "<col=5FD857>Gaia Guardian";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{130752};
					definition.standAnimation = 8021;
					definition.walkAnimation = 8022;
					definition.scaleXZ = 350;
					definition.scaleY = 350;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;


				case 1072:
					definition.name = "<col=5FD857>Goliath";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131539, 131540, 131541, 131542, 131543};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 1073:
					definition.name = "<col=D45F4B>Volcanus";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131544, 131545, 131546, 131547, 131548};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;

				case 1074:
					definition.name = "<col=1097BF>Nautilus";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131549, 131550, 131551, 131552, 131553};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;

				case 1075:
					definition.name = "<col=5FD857>Quake";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131554, 131555, 131556, 131557, 131558};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;

				case 1076:
					definition.name = "<col=D45F4B>Scaldor";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131559, 131560, 131561, 131562, 131563};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;

				case 1077:
					definition.name = "<col=1097BF>Seabane";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131564, 131565, 131566, 131567, 131568};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;

				case 1078:
					definition.name = "<col=5FD857>Rumble";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131569, 131570, 131571, 131572, 131573};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;

				case 1079:
					definition.name = "<col=D45F4B>Moltron";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131574, 131575, 131576, 131577, 131578};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;

				case 1080:
					definition.name = "<col=1097BF>Hydrox";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131582, 131583, 131584, 131585, 131586};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 1081:
					definition.name = "<col=AF70C3>Void Guardian";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{131603};
					definition.standAnimation = 8021;
					definition.walkAnimation = 8022;
					definition.scaleXZ = 350;
					definition.scaleY = 350;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 1037:
					definition.name = "@red@Del'Heim";
					definition.npcModels = new int[]{101842};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 225;
					definition.scaleY = 225;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1038:
					definition.name = "@red@Zeldrith";
					definition.npcModels = new int[]{101843};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 225;
					definition.scaleY = 225;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1039:
					definition.name = "@red@Nemeios";
					definition.npcModels = new int[]{101844};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 225;
					definition.scaleY = 225;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 262:
					definition.name = "@red@Nemeios";
					definition.npcModels = new int[]{101844};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 225;
					definition.scaleY = 225;
					definition.actions = new String[]{null, null, null, null, null};
					break;
				case 480:
					definition.name = "Water Minion";
					definition.npcSizeInSquares = 1;
					definition.npcModels = new int[]{130501};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 481:
					definition.name = "Fire Minion";
					definition.npcSizeInSquares = 1;
					definition.npcModels = new int[]{130500};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 483:
					definition.name = "Earth Minion";
					definition.npcSizeInSquares = 1;
					definition.npcModels = new int[]{130502};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 180;
					definition.scaleY = 180;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1040:
					definition.name = "@red@Asrian";
					definition.npcModels = new int[]{101845};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 225;
					definition.scaleY = 225;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 1041:
					definition.name = "@red@Fen'Doom";
					definition.npcModels = new int[]{101846};
					EntityDef antwyvs = get(49);
					definition.combatLevel = 40;
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					definition.npcSizeInSquares = 1;
					definition.description = antwyvs.description;
					definition.drawYellowDotOnMap = true;
					definition.standAnimation = antwyvs.standAnimation;
					definition.walkAnimation = antwyvs.walkAnimation;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 1;
					break;

				case 2111:
					definition.name = "Hellfire";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{132070};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					definition.npcSizeInSquares = 2;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2112:
					definition.name = "Cryos";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{132074};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					definition.npcSizeInSquares = 2;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2113:
					definition.name = "Toxus";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{132071};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					definition.npcSizeInSquares = 2;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 457:
					definition.name = "DreadFlesh";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132186};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 255;
					definition.scaleY = 255;
					definition.npcSizeInSquares = 2;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 458:
					definition.name = "Xaroth";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132185};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.npcSizeInSquares = 2;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 459:
					definition.name = "Spectral Beast 3";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132074};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					definition.npcSizeInSquares = 2;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2114:
					definition.name = "Gemstone";
					definition.actions = new String[]{null, "Attack", "Check progress", null, null};
					definition.npcModels = new int[]{132075};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 195;
					definition.scaleY = 195;
					definition.npcSizeInSquares = 2;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2115://RAID BOSS
					definition.name = "Dementor";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132073};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2116://RAID BOSS
					definition.setDefault();
					definition.name = "Wraith";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132130};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2117://RAID BOSS
					definition.name = "Shade";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132131};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2118://RAID BOSS
					definition.name = "Umbral";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132071};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2119://RAID BOSS
					definition.name = "Soulrender";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132073};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;



				case 2120://SLAY 1
					definition.setDefault();
					definition.name = "Cosmos";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132120};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2121://SLAY 2
					definition.setDefault();
					definition.name = "Nebula";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132121};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 350;
					definition.scaleY = 350;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2122://SLAY 3
					definition.setDefault();
					definition.name = "Eclipse";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132122};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 350;
					definition.scaleY = 350;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2090://SLAY 4
					definition.setDefault();
					definition.name = "Meteor";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132123};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 300;
					definition.scaleY = 300;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2124://SLAY 5
					definition.setDefault();
					definition.name = "Nova";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132124};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 325;
					definition.scaleY = 325;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2091://SLAY 6
					definition.setDefault();
					definition.name = "Pulsar";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132125};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2126://SLAY 7
					definition.setDefault();
					definition.name = "Comet";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132126};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2127://SLAY 8
					definition.setDefault();
					definition.name = "Astroid";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132127};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 325;
					definition.scaleY = 325;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2128://SLAY 9
					definition.name = "Zenith";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{132128};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 325;
					definition.scaleY = 325;
					definition.npcSizeInSquares = 1;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 300;
					break;
				case 2130://SPECTRAL MASTER
					definition.setDefault();
					definition.name = "Spectral Master";
					definition.npcModels = new int[]{132143};
					definition.actions = new String[]{"@yel@Task", null, "@yel@Shop", null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;




				case 576:
					definition.name = "Enchanted Guardian";
					definition.combatLevel = 999;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{30500};
					definition.standAnimation = 7849;
					definition.walkAnimation = 7848;
					definition.scaleXZ = 145;
					definition.scaleY = 145;
					definition.degreesToTurn = 32;
					definition.rdc2 = 3216;
					break;


				case 6323:
					definition.name = "Everthorn";
					definition.combatLevel = 999;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{30500};
					definition.standAnimation = 7849;
					definition.walkAnimation = 7848;
					definition.scaleXZ = 145;
					definition.scaleY = 145;
					definition.degreesToTurn = 32;
					definition.rdc2 = 3216;
					break;
				case 7347://BLUE THORN PORTAL
				case 7365://BROWN THORN PORTAL
				case 7359://BLACK THORN PORTAL
				case 6804://PURPLE THORN PORTAL
					definition.name = "Portal";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Enter", null, null, null, null};
					definition.drawYellowDotOnMap = false;
					break;
				case 8459://DRUID HERBLORE
					definition.name = "Alchemist";
					definition.drawYellowDotOnMap = true;
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					break;
				case 4885://TUTORIAL VILLAGER
					definition.name = "@red@Athens Villager";
					definition.drawYellowDotOnMap = true;
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					definition.standAnimation = 2105;
					break;
				case 4886: //TUTORIAL VILLAGER AFTER DEFEATING BEAST
					definition.name = "@gre@Athens Villager";
					definition.npcModels = new int[]{12140, 390, 11360, 11775, 10706, 13347, 8934};
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					break;
				case 655://TUTORIAL BOSS
					definition.name = "Mighty Beast";
					definition.drawYellowDotOnMap = true;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					break;
				case 700:
					definition.name = "Christmas Shop";
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{"Trade", null, null, null, null};
					break;
				case 249:
					definition.name = "Wizard Drendor";
					definition.standAnimation = 808;
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					break;
				case 2899:
					definition.name = "Father Athens";
					definition.standAnimation = 808;
					break;
				case 1716:
					definition.name = "@yel@Grace Powerup";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Activate", null, null, null, null};
					break;
				case 1717:
					definition.name = "@cya@Shockwave Powerup";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Activate", null, null, null, null};
					break;
				case 1718:
					definition.name = "@red@Blitz Powerup";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Activate", null, null, null, null};
					break;
				case 1719:
					definition.name = "@gre@Luck Powerup";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Activate", null, null, null, null};
					break;


				case 13628:
					definition.name = "Portal 1";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 13629:
					definition.name = "Portal 2";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 13630:
					definition.name = "Portal 3";
					definition.npcModels = new int[]{2318};
					definition.standAnimation = 504;
					definition.walkAnimation = 504;
					definition.actions = new String[]{"Enter", null, null, null, null};
					break;
				case 13631:
					definition.name = "Donation Chest";
					definition.npcModels = new int[]{130020};
					definition.scaleXZ = 50;
					definition.scaleY = 50;
					definition.actions = new String[]{"Open", null, null, null, null};
					break;



				//END OF ZONE MOBS

				case 2209:
					definition.name = "<col=AF70C3>Mining Shop";
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					definition.npcSizeInSquares = 2;
					break;


				case 4401:
					definition.name = "@red@Corrupt hound";
					definition.npcModels = new int[]{131700};
					definition.standAnimation = 6561;
					definition.walkAnimation = 6583;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 50;
					break;
				case 4402:
					definition.name = "@red@Corrupt Devourer";
					definition.npcModels = new int[]{131701};
					definition.standAnimation = 7008;
					definition.walkAnimation = 7001;
					definition.scaleXZ = 39;
					definition.scaleY = 39;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 50;
					break;
				case 4403:
					definition.name = "@red@Corrupt Chinchompa";
					definition.npcModels = new int[]{131702};
					definition.standAnimation = 7751;
					definition.walkAnimation = 7750;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 50;
					break;
				case 4404:
					definition.name = "@red@Corrupt Cayman";
					definition.npcModels = new int[]{131703};
					definition.standAnimation = 7938;
					definition.walkAnimation = 7941;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 70;
					break;
				case 4405:
					definition.name = "@red@Corrupt Beast";
					definition.npcModels = new int[]{131704};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 70;
					break;
				case 4406:
					definition.name = "@red@Corrupt Leopard";
					definition.npcModels = new int[]{131705};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 70;
					break;
				case 4407:
					definition.name = "@red@Corrupt Wolf";
					definition.npcModels = new int[]{131706};
					definition.standAnimation = 8301;
					definition.walkAnimation = 8302;
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 100;
					break;
				case 4408:
					definition.name = "@red@Corrupt Tortoise";
					definition.npcModels = new int[]{131707};
					definition.standAnimation = 8284;
					definition.walkAnimation = 8281;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 100;
					break;
				case 4409:
					definition.name = "@red@Corrupt Brute";
					definition.npcModels = new int[]{131708};
					definition.standAnimation = 7772;
					definition.walkAnimation = 7768;
					definition.scaleXZ = 50;
					definition.scaleY = 50;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 100;
					break;

				case 300:
					definition.name = "@red@Corrupt Master";
					definition.npcModels = new int[]{131819};
					definition.actions = new String[]{"@yel@Task", null, "@yel@Shop", null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 4410:
					definition.name = "Corrupt Superior";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{131709};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;
				case 4411:

					definition.name = "Spectral Superior";
					definition.combatLevel = 100;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{132136};
					definition.standAnimation = 4866;
					definition.walkAnimation = 4867;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					break;



				 case 4420:
				    definition.name = null;
				    definition.npcModels = new int[]{43075};
				    definition.standAnimation = 10586;
				    definition.walkAnimation = 10586;
				    definition.scaleXZ = 300;
				    definition.scaleY = 300;
					definition.rdc = 830606;
					 definition.npcSizeInSquares = 2;

					 break;


				//START OF SLAYER MOBS
				case 9838:
					definition.name = "<col=D45F4B>Lava hound";
					definition.npcModels = new int[]{130409};
					definition.standAnimation = 6561;
					definition.walkAnimation = 6583;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 50;
					break;
				case 6306:
					definition.name = "<col=1097BF>Aqua Hound";
					definition.npcModels = new int[]{130039};
					definition.standAnimation = 6561;
					definition.walkAnimation = 6583;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 50;
					break;
				case 3688:
					definition.name = "<col=5FD857>Earth Hound";
					definition.npcModels = new int[]{130040};
					definition.standAnimation = 6561;
					definition.walkAnimation = 6583;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 50;
					break;
				case 203:
					definition.name = "<col=5FD857>Earth Devourer";
					definition.npcModels = new int[]{130041};
					definition.standAnimation = 7008;
					definition.walkAnimation = 7001;
					definition.scaleXZ = 39;
					definition.scaleY = 39;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 50;
					break;
				case 1022:
				    definition.name = "<col=AF70C3>Stream Boss";
				    definition.npcModels = new int[]{131654};
				    definition.combatLevel = 699;
				    definition.standAnimation = 808;
				    definition.walkAnimation = 819;
				    definition.scaleXZ = 145;
				    definition.scaleY = 145;
				    definition.npcSizeInSquares = 2;
				    definition.actions = new String[]{null, "Attack", null, null, null};
				    break;
				case 1023:
					definition.name = "@red@Frenzy Beast";
					definition.npcModels = new int[]{131653};
					definition.standAnimation = 12248;
					definition.walkAnimation = 12246;
					definition.scaleXZ = 50;
					definition.scaleY = 50;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.combatLevel = 999;
					break;
				case 185:
					definition.name = "<col=1097BF>Aqua Devourer";
					definition.npcModels = new int[]{130042};
					definition.standAnimation = 7008;
					definition.walkAnimation = 7001;
					definition.scaleXZ = 39;
					definition.scaleY = 39;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 50;
					break;
				case 603:
					definition.name = "<col=D45F4B>Lava Devourer";
					definition.npcModels = new int[]{130043};
					definition.standAnimation = 7008;
					definition.walkAnimation = 7001;
					definition.scaleXZ = 39;
					definition.scaleY = 39;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 50;
					break;

				case 202:
					definition.name = "<col=D45F4B>Lava Chinchompa";
					definition.npcModels = new int[]{130046};
					definition.standAnimation = 7751;
					definition.walkAnimation = 7750;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 2;
					definition.combatLevel = 50;
					break;
				case 3005:
					definition.name = "<col=1097BF>Aqua Chinchompa";
					definition.npcModels = new int[]{130045};
					definition.standAnimation = 7751;
					definition.walkAnimation = 7750;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 3;
					definition.combatLevel = 50;
					break;
				case 5002:
					definition.name = "<col=5FD857>Earth Chinchompa";
					definition.npcModels = new int[]{130044};
					definition.standAnimation = 7751;
					definition.walkAnimation = 7750;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.element = 1;
					definition.combatLevel = 50;
					break;
				case 606:
					definition.name = "<col=D45F4B>Lava Cayman";
					definition.npcModels = new int[]{130049};
					definition.standAnimation = 7938;
					definition.walkAnimation = 7941;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 70;
					break;
				case 3004:
					definition.name = "<col=1097BF>Aqua Cayman";
					definition.npcModels = new int[]{130048};
					definition.standAnimation = 7938;
					definition.walkAnimation = 7941;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 70;
					break;
				case 188:
					definition.name = "<col=5FD857>Earth Cayman";
					definition.npcModels = new int[]{130047};
					definition.standAnimation = 7938;
					definition.walkAnimation = 7941;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 70;
					break;
				case 8010:
					definition.name = "<col=5FD857>Earth Beast";
					definition.npcModels = new int[]{130050};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 70;
					break;
				case 352:
					definition.name = "<col=1097BF>Aqua Beast";
					definition.npcModels = new int[]{130051};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 70;
					break;
				case 928:
					definition.name = "<col=D45F4B>Lava Beast";
					definition.npcModels = new int[]{130052};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 70;
					break;
				case 450:
					definition.name = "<col=5FD857>Earth Leopard";
					definition.npcModels = new int[]{130053};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 70;
					break;
				case 4000:
					definition.name = "<col=1097BF>Aqua Leopard";
					definition.npcModels = new int[]{130054};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 70;
					break;
				case 4001:
					definition.name = "<col=D45F4B>Lava Leopard";
					definition.npcModels = new int[]{130055};
					definition.standAnimation = 5225;
					definition.walkAnimation = 5226;
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 70;
					break;
				case 9846:
					definition.name = "<col=5FD857>Earth Wolf";
					definition.npcModels = new int[]{130056};
					definition.standAnimation = 8301;
					definition.walkAnimation = 8302;
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 100;
					break;
				case 1737:
					definition.name = "<col=1097BF>Aqua Wolf";
					definition.npcModels = new int[]{130057};
					definition.standAnimation = 8301;
					definition.walkAnimation = 8302;
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 100;
					break;
				case 1738:
					definition.name = "<col=D45F4B>Lava Wolf";
					definition.npcModels = new int[]{130058};
					definition.standAnimation = 8301;
					definition.walkAnimation = 8302;
					definition.scaleXZ = 500;
					definition.scaleY = 500;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 100;
					break;
				case 1739:
					definition.name = "<col=5FD857>Earth Tortoise";
					definition.npcModels = new int[]{130060};
					definition.standAnimation = 8284;
					definition.walkAnimation = 8281;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 100;
					break;
				case 452:
					definition.name = "<col=1097BF>Aqua Tortoise";
					definition.npcModels = new int[]{130059};
					definition.standAnimation = 8284;
					definition.walkAnimation = 8281;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 100;
					break;
				case 201:
					definition.name = "<col=D45F4B>Lava Tortoise";
					definition.npcModels = new int[]{130061};
					definition.standAnimation = 8284;
					definition.walkAnimation = 8281;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 100;
					break;
				case 1725:
					definition.name = "<col=D45F4B>Lava Brute";
					definition.npcModels = new int[]{130064};
					definition.standAnimation = 7772;
					definition.walkAnimation = 7768;
					definition.scaleXZ = 50;
					definition.scaleY = 50;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 100;
					break;
				case 1726:
					definition.name = "<col=1097BF>Aqua Brute";
					definition.npcModels = new int[]{130063};
					definition.standAnimation = 7772;
					definition.walkAnimation = 7768;
					definition.scaleXZ = 50;
					definition.scaleY = 50;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 3;
					definition.combatLevel = 100;
					break;
				case 5080:
					definition.name = "<col=5FD857>Earth Brute";
					definition.npcModels = new int[]{130062};
					definition.standAnimation = 7772;
					definition.walkAnimation = 7768;
					definition.scaleXZ = 50;
					definition.scaleY = 50;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 1;
					definition.combatLevel = 100;
					break;
				//END OF SLAYER MOBS
				case 3307:
					definition.name = "@red@Skeletal Demon";
					definition.combatLevel = 666;
					definition.npcModels = new int[]{16368};
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 380;
					definition.scaleY = 380;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 3308:
					definition.name = "@cya@Emerald Champion";
					definition.combatLevel = 666;
					definition.npcModels = new int[]{132035, 132036};
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 300;
					definition.scaleY = 300;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				//VOID RIFT MOBS
				case 6326:
					definition.name = "Nightwraith";//demon 1
					definition.npcModels = new int[]{130121};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 2745:
					definition.name = "Darkwhisper";// demon 2
					definition.npcModels = new int[]{130120};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 8009:
					definition.name = "Void Phoenix";
					definition.npcModels = new int[]{130434};
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.combatLevel = 235;
					definition.standAnimation = 11074;
					definition.walkAnimation = 11075;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.npcSizeInSquares = 2;
					definition.drawYellowDotOnMap = true;
					break;
				case 9006:
					definition.name = "Nightfallen";
					definition.npcModels = new int[]{130120};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 1807:
					definition.name = "Soulrender";
					definition.npcModels = new int[]{130121};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 1084:
                    definition.setDefault();
					definition.name = "Netherveil";
					definition.npcModels = new int[]{130120};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 601:
					definition.name = "Oblivion";
					definition.npcModels = new int[]{130121};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 4444:
					definition.name = "Murkmaw";
					definition.npcModels = new int[]{130120};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 4412:
					definition.name = "@red@Azazel";
					definition.npcModels = new int[]{131821};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 4413:
					definition.name = "@red@Nocturnis";
					definition.npcModels = new int[]{131822};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 4414:
					definition.name = "@red@Astaroth";
					definition.npcModels = new int[]{131821};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 4415:
					definition.name = "@red@Drakonar";
					definition.npcModels = new int[]{131822};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;



				case 1:
					definition.name = "@gre@EARTH";
					break;
				case 2:
					definition.name = "@cya@WATER";
					break;
				case 3:
					definition.name = "@red@FIRE";
					break;
				case 60:
					definition.scaleXZ = 1;
					definition.scaleY = 1;
					break;
					//SKELETONS
				case 3151://BEGINNER MELEE SKELE
					definition.name = "<col=AF70C3>Deathwalker";
					definition.npcModels = new int[]{21165};
					definition.scaleXZ = 65;
					definition.scaleY = 65;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3154://RANGE SKELE
					definition.name = "<col=AF70C3>Arrowshade";
					definition.npcModels = new int[]{21189};
					definition.standAnimation = 5510;
					definition.walkAnimation = 5511;
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3153://WIZARD SKELE
					definition.name = "<col=AF70C3>Bonemancer";
					definition.npcModels = new int[]{21196};
					definition.standAnimation = 5483;
					definition.walkAnimation = 5479;
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
					//DEMONS
				case 3155://IMP
					definition.name = "@gre@Shadowfiend";
					definition.npcModels = new int[]{34565};
					definition.standAnimation = 9462;
					definition.walkAnimation = 9463;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3156://DEMON
					definition.name = "@gre@Devilspawn";
					definition.npcModels = new int[]{44733};
					definition.standAnimation = 10921;
					definition.walkAnimation = 10920;
					definition.scaleXZ = 65;
					definition.scaleY = 65;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3158://OTHER DEMON
					definition.name = "@gre@Abyssal Tormentor";
					definition.npcModels = new int[]{51098};
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 65;
					definition.scaleY = 65;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
					//OGRES
				case 3159://OGRE STARTER
					definition.name = "@yel@Grunt Mauler";
					definition.npcModels = new int[]{46145};
					definition.standAnimation = 10626;
					definition.walkAnimation = 10625;
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3160://OGRE UPG
					definition.name = "@yel@Brute Crusher";
					definition.npcModels = new int[]{46153};
					definition.standAnimation = 10626;
					definition.walkAnimation = 10625;
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3161://OGRE QUEEN
					definition.name = "@yel@Vinesplitter";
					definition.npcModels = new int[]{46142};
					definition.standAnimation = 10626;
					definition.walkAnimation = 10625;
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
					//SPECTRAL
				case 3162://STARTER GHOST
					definition.name = "@cya@Phantom Drifter";
					definition.npcModels = new int[]{130471};
					definition.standAnimation = 5538;
					definition.walkAnimation = 5539;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3163://UPG GHOST
					definition.name = "@cya@Whispering Wraith";
					definition.npcModels = new int[]{130472};
					definition.combatLevel = 0;
					definition.standAnimation = 5538;
					definition.walkAnimation = 5539;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3164://MASTER GHOST
					definition.name = "@cya@Banshee King";
					definition.npcModels = new int[]{130473};
					definition.standAnimation = 5538;
					definition.walkAnimation = 5539;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;

					//MASTER SPELLS
				case 3165://EYEBALL MF
					definition.name = "@red@Eye of the Beyond";
					definition.npcModels = new int[]{61984};
					definition.standAnimation = 14887;
					definition.walkAnimation = 14888;
					definition.scaleXZ = 55;
					definition.scaleY = 55;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3166://MASTER BIRD 2
					definition.name = "@red@Talonwing";
					definition.npcModels = new int[]{19136};
					definition.standAnimation = 5021;
					definition.walkAnimation = 5022;
					definition.scaleXZ = 45;
					definition.scaleY = 45;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3167://MASTER BEAST
					definition.name = "@red@Umbral Beast";
					definition.npcModels = new int[]{30253};
					definition.standAnimation = 7472;
					definition.walkAnimation = 7473;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3700:
					definition.name = "<col=AF70C3>Skeletal Servant";
					definition.npcModels = new int[]{131610, 131620, 15106, 131622, 131615, 131621, 181};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3701:
					definition.name = "@gre@Demonic Servant";
					definition.npcModels = new int[]{131611, 131620, 15106, 131622, 131616, 131621, 181};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3702:
					definition.name = "@yel@Ogre Servant";
					definition.npcModels = new int[]{131612, 131620, 15106, 131622, 131617, 131621, 181};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3703:
					definition.name = "@cya@Spectral Servant";
					definition.npcModels = new int[]{131613, 131620, 15106, 131622, 131618, 131621, 181};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;
				case 3704:
					definition.name = "@red@Master Servant";
					definition.npcModels = new int[]{131614, 131620, 15106, 131622, 131619, 131621, 181};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, null, null, null, null};
					definition.isPet = true;
					break;


				case 3168:
					definition.name = "Mayhem Minion";
					definition.npcModels = new int[]{130503};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 3169:
					definition.name = "Sharpshooter Boss";
					definition.npcModels = new int[]{130502};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 180;
					definition.scaleY = 180;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 3170:
					definition.name = "Sorcerer Boss";
					definition.npcModels = new int[]{130501};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 180;
					definition.scaleY = 180;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 3171:
					definition.name = "Warrior Boss";
					definition.npcModels = new int[]{130500};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 180;
					definition.scaleY = 180;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 3172:
					definition.name = "Gaia Titan";//DAILY EARTH BOSS
					definition.npcModels = new int[]{30539};
					definition.standAnimation = 8220;
					definition.walkAnimation = 8219;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.degreesToTurn = 32;
					break;
				case 3173:
					definition.name = "Aqua Titan";//DAILY WATER BOSS
					definition.combatLevel = 1057;
					definition.walkAnimation = 6505;
					definition.standAnimation = 6504;//6504 stand, 6503 block, 6502 death, 6501 attack
					definition.npcSizeInSquares = 4;
					definition.npcModels = new int[]{130504};
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.scaleXZ = 80;
					definition.scaleY = 80;
					definition.degreesToTurn = 32;
					break;
				case 3174:
					definition.name = "Lava Titan";//DAILY FIRE BOSS
					definition.npcModels = new int[]{130505};
					definition.combatLevel = 0;
					definition.standAnimation = 1338;
					definition.walkAnimation = 1339;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcSizeInSquares = 2;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.degreesToTurn = 32;
					break;
				case 1203:
					definition.name = "<col=AF70C3>GIM Manager";
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					definition.npcModels = new int[]{130883};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 0;
					break;
				case 494:
					definition.name = "<col=AF70C3>Banker";
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					break;
				case 4651:
					definition.name = "<col=AF70C3>Trading Post";
					definition.actions = new String[]{"Open", null, null, null, null};
					break;
				case 301:
					definition.name = "@red@Corrupt Beast Master";
					definition.npcModels = new int[]{131829};
					definition.actions = new String[]{"Shop", null, "Task", null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 2;
					break;
				case 455:
					definition.name = "<col=AF70C3>Spectral Beast Master";
					definition.npcModels = new int[]{132187};
					definition.actions = new String[]{"Shop", null, "Task", null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 165;
					definition.scaleY = 165;
					definition.npcSizeInSquares = 2;
					break;
				case 215:
					definition.name = "<col=AF70C3>Beast Master";
					definition.npcModels = new int[]{120207};
					definition.actions = new String[]{"Shop", null, "Task", null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					definition.npcSizeInSquares = 2;
					break;
				case 217://TUTORIAL
					definition.name = "<col=AF70C3>Beast Master";
					definition.npcModels = new int[]{120207};
					definition.actions = new String[]{"Talk-To", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					definition.npcSizeInSquares = 2;
					break;
				case 214:
					definition.name = "<col=AF70C3>Essence Shop";
					definition.npcModels = new int[]{120204};
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 216:
					definition.name = "<col=AF70C3>Enchanter";
					definition.npcModels = new int[]{120210, 120211 , 120212, 120213, 120214, 120215};
					definition.actions = new String[]{"Upgrade Items", null, null, null, null};
					definition.standAnimation = 808 ;
					definition.walkAnimation = 819 ;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 248:
					definition.name = "<col=AF70C3>Wizard Drendor";
					definition.actions = new String[]{"Guides", null, "Starter Tasks", null, null};
					definition.npcModels = new int[]{20399, 214, 250, 20405, 3189, 20395, 177, 20402, 181};
					definition.standAnimation = 808;
					definition.walkAnimation = 819 ;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 568:
					definition.name = "<col=AF70C3>Cosmetic Shop";
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.npcModels = new int[]{130768};
					definition.standAnimation = 808;
					definition.walkAnimation = 819 ;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 3378:
					definition.name = "<col=AF70C3>Ironman Shop";
					definition.actions = new String[]{"Trade", null, "@yel@Account", null, null};
					definition.npcModels = new int[]{130769};
					definition.standAnimation = 808;
					definition.walkAnimation = 819 ;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 931:
					definition.name = "<col=AF70C3>Enchanted Master";
					definition.actions = new String[]{"@yel@Shop", null, "@yel@Task", null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 22:
					definition.name = "Oculus";
					definition.actions = new String[]{"Talk-to", null, "Reset Companion", null, null};
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					definition.drawYellowDotOnMap = true;

					break;
				case 1597:
					definition.name = "<col=AF70C3>Beginner Master";
					definition.actions = new String[]{"@yel@Task", null, "@yel@Shop", null, null};
					definition.npcModels = new int[]{130941};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 8275:
					definition.name = "<col=AF70C3>Medium Master";
					definition.actions = new String[]{"@yel@Task", null, "@yel@Shop", null, null};
					definition.npcModels = new int[]{130940};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 9085:
					definition.name = "<col=AF70C3>Elite Master";
					definition.npcModels = new int[]{130942};
					definition.actions = new String[]{"@yel@Task", null, "@yel@Shop", null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 2676:
					definition.npcModels = new int[]{130904};
					definition.name = "<col=AF70C3>Make-over Mage";
					definition.actions = new String[]{"Makeover", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 210:
					definition.name = "<col=AF70C3>Vote Shop";
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.scaleXZ = 140;
					definition.scaleY = 140;
					break;
				case 1199:
					definition.name = "<col=AF70C3>Afk Shop";
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.scaleXZ = 140;
					definition.scaleY = 140;
					break;
				case 230:
					definition.name = "Journeyman";
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.scaleXZ = 140;
					definition.scaleY = 140;
					break;

				case 640:
					definition.name = "Void Boss";
					definition.npcModels = new int[]{130121};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 187;
					definition.scaleY = 187;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 641:
					definition.npcModels = new int[]{131941};//Void CRYSTAL 2
					definition.name = "Void Minion";
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.actions = new String[]{null, "Attack", null, null, null};
					break;
				case 642:
					definition.name = "Void Minion";
					definition.npcModels = new int[]{130120};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 85;
					definition.scaleY = 85;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;
				case 643:
					definition.name = "Void Boss";
					definition.npcModels = new int[]{130120};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 66;
					definition.walkAnimation = 63;
					definition.scaleXZ = 187;
					definition.scaleY = 187;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					break;

				case 644:
					definition.name = "@gre@Dionaea";
					definition.combatLevel = 999;
					definition.scaleXZ = 450;
					definition.scaleY = 450;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.drawYellowDotOnMap = true;
					definition.npcModels = new int[]{30455};
					definition.standAnimation = 8204;
					definition.walkAnimation = 8207;
					definition.degreesToTurn = 32;
					definition.rdc2 = 22;
				    break;


				case 231:
					definition.name = "Forester";
					definition.npcModels = new int[]{131519};
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.scaleXZ = 140;
					definition.scaleY = 140;
					break;
				case 102:
					definition.npcModels = new int[]{130989};
					definition.name = "<col=AF70C3>Junk Shop";
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					definition.combatLevel = 0;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					break;
				case 211:
					definition.name = "<col=AF70C3>Donation Shop";
					definition.npcModels = new int[]{120201};
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 305:
					definition.name = "<col=AF70C3>Stream Shop";
					definition.npcModels = new int[]{131655};
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 306:
					definition.name = "<col=AF70C3>Prince V";
					definition.npcModels = new int[]{131656};
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 150;
					definition.scaleY = 150;
					break;
				case 212:
					definition.name = "<col=AF70C3>Global Shop";
					definition.npcModels = new int[]{130884};
					definition.actions = new String[]{"Trade", null, null, null, null};
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					break;
				case 605:
					definition.name = "<col=AF70C3>Vote Shop";
					definition.actions = new String[5];
					definition.actions[0] = "Trade";
					definition.actions[2] = null;
					definition.actions[3] = null;
					definition.npcModels = new int[]{14959};
					break;
				case 100:
					definition.name = "<col=AF70C3>King V";
					definition.actions = new String[] {"Talk-to", null, null, null, null};
					definition.npcModels = new int[]{120200};
					definition.scaleXZ = 155;
					definition.scaleY = 155;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;;
					break;
				case 101:
					definition.name = "<col=AF70C3>Syndicate Merchant";
					definition.actions = new String[] {"Talk-to", null, null, null, null};
					definition.npcModels = new int[]{120200};
					definition.scaleXZ = 140;
					definition.scaleY = 140;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;;
					break;
					//END OF HOME MOBS


				 case 1051:
				     definition.name = "Frost Titan";
					 definition.npcModels = new int[]{30470};
					 definition.standAnimation = 8186;
					 definition.walkAnimation = 7847;
					 definition.scaleXZ = 100;
					 definition.scaleY = 100;
					 definition.degreesToTurn = 32;
					 definition.drawYellowDotOnMap = true;
					 definition.npcSizeInSquares = 1;
					 definition.actions = new String[]{null, "Attack", null, null, null};
					 definition.combatLevel = 70;
					 break;
				case 1052:
					definition.name = "Frost Lord";
					definition.npcModels = new int[]{21802, 21806};
					definition.standAnimation = 5722;
					definition.walkAnimation = 5721;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.combatLevel = 70;
					break;
				case 1053:
					definition.name = "Frost Demon";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{50771};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 2978;
					definition.walkAnimation = 2979;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1054:
					definition.name = "Frost Fiend";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{33842};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 9145;
					definition.walkAnimation = 8076;
					definition.scaleXZ = 145;
					definition.scaleY = 145;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1055:
					definition.name = "Frost Guardian";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{55388};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 13333;
					definition.walkAnimation = 13334;
					definition.scaleXZ = 175;
					definition.scaleY = 175;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1056:
					definition.name = "Frost Elemental";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{56835};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 11641;
					definition.walkAnimation = 11638;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1057:
					definition.name = "Frost Giant";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{17453, 17442};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 4670;
					definition.walkAnimation = 4669;
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1058:
					definition.name = "Frost Dragon";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{56768, 55294};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 13156;
					definition.walkAnimation = 13157;
					definition.scaleXZ = 90;
					definition.scaleY = 90;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1059:
					definition.name = "Frost Imp";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{30268};
					definition.npcSizeInSquares = 1;
					definition.standAnimation = 171;
					definition.walkAnimation = 168;
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1060://QUEST
					definition.name = "Santa Claus";
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					definition.npcModels = new int[]{131524};
                    definition.dialogueModels = new int[]{45287};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 110;
					definition.scaleY = 110;
					definition.drawYellowDotOnMap = true;
					break;
				case 1061://BOSS
					definition.name = "Santa Claus";
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.npcModels = new int[]{131524};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 190;
					definition.scaleY = 190;
					definition.drawYellowDotOnMap = true;
					definition.combatLevel = 100;
					break;
				case 1062://JACK QUEST
					definition.name = "Jack Frost";
					definition.actions = new String[]{"Talk-to", null, null, null, null};
					definition.npcModels = new int[]{45189};
					definition.npcSizeInSquares = 2;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.drawYellowDotOnMap = true;
					break;
                case 1063:
                    definition.name = "Frozen Demon";
                    definition.actions = new String[]{null, "Attack", null, null, null};
                    definition.npcModels = new int[]{50771};
                    definition.npcSizeInSquares = 1;
                    definition.standAnimation = 2978;
                    definition.walkAnimation = 2979;
                    definition.scaleXZ = 128;
                    definition.scaleY = 128;
                    definition.drawYellowDotOnMap = true;
                    definition.combatLevel = 95;
                break;
				case 57:
					definition.name = "Fairy(1)";
					definition.actions = new String[]{null, null, null, null, null};
					definition.drawYellowDotOnMap = false;
					break;
				case 567:
					definition.name = "Fairy(2)";
					definition.actions = new String[]{null, null, null, null, null};
					definition.drawYellowDotOnMap = false;
					break;
				case 6072:
					definition.name = "Fairy(3)";
					definition.actions = new String[]{null, null, null, null, null};
					definition.npcModels = new int[]{57321};
					definition.standAnimation = 793;
					definition.walkAnimation = 13847;
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.drawYellowDotOnMap = false;
					break;
				case 4437:
					definition.name = "Fairy(4)";
					definition.actions = new String[]{null, null, null, null, null};
					definition.drawYellowDotOnMap = false;
					break;
				case 4433:
					definition.name = "Fairy(5)";
					definition.actions = new String[]{null, null, null, null, null};
					definition.drawYellowDotOnMap = false;
					break;
				case 6055:
					definition.name = "Imp(1)";
					definition.npcModels = new int[]{131832};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.standAnimation = 6613;
					definition.walkAnimation = 6614;
					definition.drawYellowDotOnMap = false;
					break;
				case 6061:
					definition.name = "Imp(2)";
					definition.npcModels = new int[]{131833};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.standAnimation = 6613;
					definition.walkAnimation = 6614;
					definition.drawYellowDotOnMap = false;
					break;
				case 6063:
					definition.name = "Imp(3)";
					definition.npcModels = new int[]{131834};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.standAnimation = 6613;
					definition.walkAnimation = 6614;
					definition.drawYellowDotOnMap = false;
					break;
				case 6064:
					definition.name = "Imp(4)";
					definition.npcModels = new int[]{131835};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.standAnimation = 6613;
					definition.walkAnimation = 6614;
					definition.drawYellowDotOnMap = false;
					break;
				case 6059:
					definition.name = "Imp(5)";
					definition.npcModels = new int[]{131831};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 128;
					definition.scaleY = 128;
					definition.standAnimation = 6613;
					definition.walkAnimation = 6614;
					definition.drawYellowDotOnMap = false;
					break;
				case 5072:
					definition.name = "Bird(1)";
					definition.npcModels = new int[]{131836};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.standAnimation = 6771;
					definition.walkAnimation = 6773;
					definition.drawYellowDotOnMap = false;
					break;
				case 5073:
					definition.name = "Bird(2)";
					definition.npcModels = new int[]{131837};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.standAnimation = 6771;
					definition.walkAnimation = 6773;
					definition.drawYellowDotOnMap = false;
					break;
				case 5074:
					definition.name = "Bird(3)";
					definition.npcModels = new int[]{131838};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.standAnimation = 6771;
					definition.walkAnimation = 6773;
					definition.drawYellowDotOnMap = false;
					break;
				case 5075:
					definition.name = "Bird(4)";
					definition.npcModels = new int[]{131840};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 275;
					definition.scaleY = 275;
					definition.standAnimation = 6771;
					definition.walkAnimation = 6773;
					definition.drawYellowDotOnMap = false;
					break;
				case 5076:
					definition.name = "Bird(5)";
					definition.npcModels = new int[]{131839};
					definition.actions = new String[]{null, null, null, null, null};
					definition.scaleXZ = 250;
					definition.scaleY = 250;
					definition.standAnimation = 6771;
					definition.walkAnimation = 6773;
					definition.drawYellowDotOnMap = false;
					break;


				case 143:
					definition.name = "Forest Wolf";
					definition.scaleXZ = 135;
					definition.scaleY = 135;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 70;
					break;
				case 1472:
					definition.name = "Forest Demon";
					definition.scaleXZ = 86;
					definition.scaleY = 86;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 70;
					break;
				case 7329:
					definition.name = "Forest Titan";
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 70;
					break;

				case 767:
					definition.name = "@cya@Frozen Chest";
					definition.npcModels = new int[]{131535};
					definition.scaleXZ = 55;
					definition.scaleY = 55;
					definition.npcSizeInSquares = 2;
					definition.actions = new String[]{null, null, "@cya@Open", null, null};
					break;
                case 8517:
                    definition.actions = new String[]{null, "Attack", null, null, null};
                    definition.degreesToTurn = 32;
                break;
                case 8518:
                    definition.actions = new String[]{null, "Attack", null, null, null};
                    definition.scaleXZ = 200;
                    definition.scaleY = 200;
                    definition.degreesToTurn = 32;
                break;
                case 8519:
                    definition.actions = new String[]{"Steal heart", null, null, null, null};
                break;
				case 6313:
					definition.name = "@mag@Arcana Portal";
					definition.actions = new String[]{"@mag@Siphon", null, null, null, null};
					definition.npcModels = new int[]{132203};
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.standAnimation = 3174;
					definition.walkAnimation = 3174;
					definition.isPet = false;
					break;
				case 8520:
					definition.name = "Poseidon";
					definition.npcModels = new int[]{132188, 132189, 132190,132191,132192,132193,132200};
					definition.scaleXZ = 200;
					definition.scaleY = 200;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{null, "Attack", null, null, null};
					definition.element = 2;
					definition.combatLevel = 500;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.element = 3;
					break;

				case 8521:
					definition.name = "Charon";
					definition.npcModels = new int[]{132196};
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{"Talk To", null, null, null, null};
					definition.element = 2;
					definition.combatLevel = 500;
					definition.standAnimation = 808;
					definition.walkAnimation = 819;
					definition.element = 3;
					break;
				case 8522:
					definition.name = "Portal Test";
					definition.npcModels = new int[]{132198};
					definition.scaleXZ = 100;
					definition.scaleY = 100;
					definition.degreesToTurn = 32;
					definition.drawYellowDotOnMap = true;
					definition.npcSizeInSquares = 1;
					definition.actions = new String[]{"Teleport", null, null, null, null};
					break;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		if (definition.name != null) {
			if (definition.name.toLowerCase().contains("pet ") || definition.name.toLowerCase().contains(" pet"))
				definition.drawYellowDotOnMap = false;
		}

		map.put(id, definition);
		return definition;
	}

	public void copy(int id) {
		EntityDef other = get(id);
		changedModelColours = other.changedModelColours.clone();
		combatLevel = other.combatLevel;
		degreesToTurn = other.degreesToTurn;
		description = other.description;
		dialogueModels = other.dialogueModels;
		disableRightClick = other.disableRightClick;
		drawYellowDotOnMap = other.drawYellowDotOnMap;
		headIcon = other.headIcon;
		modelLightning = other.modelLightning;
		modelShadowing = other.modelShadowing;
		npcModels = other.npcModels.clone();
		originalModelColours = other.originalModelColours.clone();
		standAnimation = other.standAnimation;
		visibilityOrRendering = other.visibilityOrRendering;
		walkAnimation = other.walkAnimation;
		walkingBackwardsAnimation = other.walkingBackwardsAnimation;
		walkLeftAnimation = other.walkLeftAnimation;
		walkRightAnimation = other.walkRightAnimation;
	}

	public static void nullify() {
		mruNodes = null;
		streamIndices = null;
		map.clear();
		map = null;
		buffer = null;
	}

	public static void load(Archive archive) {
		buffer = new Stream(archive.getDataForName("npc.dat"));
		Stream stream2 = new Stream(archive.getDataForName("npc.idx"));
		int totalNPCs = stream2.getUnsignedShort();
		streamIndices = new int[totalNPCs];
		int position = 2;

		for (int i = 0; i < totalNPCs; i++) {
			streamIndices[i] = position;
			position += stream2.getUnsignedShort();
		}
	}

	public String[] actions;
	public int scaleXZ;
	public int scaleY;
	public int[] changedModelColours;
	public int[] childrenIDs;
	public int combatLevel;
	private int configChild;
	public int degreesToTurn;
	public byte[] description;
	public int[] dialogueModels;
	public boolean disableRightClick;
	public boolean drawYellowDotOnMap;
	public int headIcon;
	private int modelLightning;
	private int modelShadowing;
	public String name;
	public boolean isPet = false;
	public int[] npcModels;
	public byte npcSizeInSquares;
	public int[] originalModelColours;
	public int standAnimation;
	public int id;
	public int element;
	private int varBitChild;
	public boolean visibilityOrRendering;
	public int walkAnimation;


	public int getStandAnimation() {
		return standAnimation;
	}

	public int getWalkAnimation() {
		return walkAnimation;
	}

	public int walkingBackwardsAnimation;
	public int walkLeftAnimation;
	public int walkRightAnimation;

	private EntityDef() {
		walkRightAnimation = -1;
		varBitChild = -1;
		walkingBackwardsAnimation = -1;
		configChild = -1;
		combatLevel = -1;
		walkAnimation = -1;
		npcSizeInSquares = 1;
		headIcon = -1;
		standAnimation = -1;
		id = -1;
		degreesToTurn = 32;
		walkLeftAnimation = -1;
		disableRightClick = true;
		scaleY = 128;
		drawYellowDotOnMap = true;
		scaleXZ = 128;
		visibilityOrRendering = false;
		rdc = 0;
		rdc2 = 0;
		rdc3 = 0;

	}
	private void setDefault() {
		walkRightAnimation = -1;
		varBitChild = -1;
		walkingBackwardsAnimation = -1;
		configChild = -1;
		combatLevel = -1;
		walkAnimation = -1;
		npcSizeInSquares = 1;
		headIcon = -1;
		standAnimation = -1;
		degreesToTurn = 32;
		walkLeftAnimation = -1;
		disableRightClick = true;
		scaleY = 128;
		drawYellowDotOnMap = true;
		scaleXZ = 128;
		visibilityOrRendering = false;
		rdc = 0;
		rdc2 = 0;
		rdc3 = 0;

	}

	public Model method160() {
		if (childrenIDs != null) {
			EntityDef definition = method161();

			if (definition == null) {
				return null;
			} else {
				return definition.method160();
			}
		}

		if (dialogueModels == null) {
			return null;
		}

		boolean flag1 = false;

		for (int i = 0; i < dialogueModels.length; i++) {
			if (!Model.isModelFetched(dialogueModels[i])) {
				flag1 = true;
			}
		}

		if (flag1) {
			return null;
		}

		Model[] aclass30_sub2_sub4_sub6s = new Model[dialogueModels.length];

		for (int j = 0; j < dialogueModels.length; j++) {
			aclass30_sub2_sub4_sub6s[j] = Model.fetchModel(dialogueModels[j]);
		}

		Model model;

		if (aclass30_sub2_sub4_sub6s.length == 1) {
			model = aclass30_sub2_sub4_sub6s[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		}

		if (originalModelColours != null) {
			for (int k = 0; k < originalModelColours.length; k++) {
				model.method476(originalModelColours[k], changedModelColours[k]);
			}
		}
		if (rdc > 0) {
			model.method1337(rdc);
		}
		if (rdc2 != 0) {
			model.method1338(rdc2);
		}
		if (rdc3 != 0) {
			model.method1339(rdc3);
		}
		applyTexturing1(model, id);
		return model;
	}

	private void applyTexturing(Model model, int id) {
	}

	public EntityDef method161() {
		int j = -1;

		try {
			if (varBitChild != -1 && varBitChild < VarBit.cache.length) {
				VarBit varBit = VarBit.cache[varBitChild];
				int k = varBit.configId;
				int l = varBit.configValue;
				int i1 = varBit.anInt650;
				int j1 = Client.anIntArray1232[i1 - l];
				// System.out.println("k: " + k + " l: " + l);
				j = clientInstance.variousSettings[k] >> l & j1;
			} else if (configChild != -1) {
				j = clientInstance.variousSettings[configChild];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
			return null;
		} else {
			return get(childrenIDs[j]);
		}
	}

	public Model method164(int j, int frame, int[] ai, int nextFrame, int cycle1, int cycle2) {
		if (childrenIDs != null) {
			EntityDef entityDef = method161();

			if (entityDef == null) {
				return null;
			} else {
				return entityDef.method164(j, frame, ai, nextFrame, cycle1, cycle2);
			}
		}

		Model model = (Model) mruNodes.insertFromCache(id);

		if (model == null) {
			boolean flag = false;

			for (int i1 = 0; i1 < npcModels.length; i1++) {
				if (!Model.isModelFetched(npcModels[i1])) {
					flag = true;
				}
			}

			if (flag) {
				return null;
			}


			Model[] aclass30_sub2_sub4_sub6s = new Model[npcModels.length];

			for (int j1 = 0; j1 < npcModels.length; j1++) {
				aclass30_sub2_sub4_sub6s[j1] = Model.fetchModel(npcModels[j1]);
			}

			if (aclass30_sub2_sub4_sub6s.length == 1) {
				model = aclass30_sub2_sub4_sub6s[0];
			} else {
				model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
			}

			if (originalModelColours != null) {
				for (int k1 = 0; k1 < originalModelColours.length; k1++) {
					model.method476(originalModelColours[k1], changedModelColours[k1]);
				}
			}
			if (rdc > 0) {
				model.method1337(rdc);
			}
			if (rdc2 != 0) {
				model.method1338(rdc2);
			}
			if (rdc3 != 0) {
				model.method1339(rdc3);
			}
			applyTexturing1(model, id);
			model.createBones();
			model.light(75 + modelLightning, 500 + modelShadowing, -50, -580, -90, true);
		//	model.light(84 + modelLightning, 1000 + modelShadowing, -90, -580, -90, true);
			mruNodes.removeFromCache(model, id);
		}

		Model model_1 = Model.aModel_1621;
		model_1.method464(model, AnimationSkeleton.isNullFrame(frame) & AnimationSkeleton.isNullFrame(j));


		if (frame != -1 && j != -1) {
			model_1.method471(ai, j, frame);
		} else if (frame != -1 && nextFrame != -1 && Configuration.TWEENING_ENABLED) {
			model_1.interpolateFrames(frame, nextFrame, cycle1, cycle2);
		} else if (frame != -1) {
			model_1.applyTransform(frame);
		}

		if (scaleXZ != 128 || scaleY != 128) {
			model_1.scaleT(scaleXZ, scaleXZ, scaleY);
		}

		model_1.method466();
		model_1.triangleGroup = null;
		model_1.vertexGroups = null;

		if (npcSizeInSquares == 1) {
			model_1.aBoolean1659 = true;
		}

		return model_1;
	}

	private void readValues(Stream buffer) {
		do {
			final int opcode = buffer.getUnsignedByte();

			if (opcode == 0) {
				return;
			}

			if (opcode == 1) {
				int j = buffer.getUnsignedByte();
				npcModels = new int[j];

				for (int j1 = 0; j1 < j; j1++) {
					npcModels[j1] = buffer.getUnsignedShort();
				}
			} else if (opcode == 2) {
				name = buffer.getString();
			} else if (opcode == 3) {
				description = buffer.getBytes();
			} else if (opcode == 12) {
				npcSizeInSquares = buffer.getSignedByte();
			} else if (opcode == 13) {
				standAnimation = buffer.getUnsignedShort();
			} else if (opcode == 14) {
				walkAnimation = buffer.getUnsignedShort();
			} else if (opcode == 17) {
				walkAnimation = buffer.getUnsignedShort();
				walkingBackwardsAnimation = buffer.getUnsignedShort();
				walkLeftAnimation = buffer.getUnsignedShort();
				walkRightAnimation = buffer.getUnsignedShort();

				if (walkAnimation == 65535) {
					walkAnimation = -1;
				}

				if (walkingBackwardsAnimation == 65535) {
					walkingBackwardsAnimation = -1;
				}

				if (walkLeftAnimation == 65535) {
					walkLeftAnimation = -1;
				}

				if (walkRightAnimation == 65535) {
					walkRightAnimation = -1;
				}
			} else if (opcode >= 30 && opcode < 40) {
				if (actions == null) {
					actions = new String[5];
				}

				actions[opcode - 30] = buffer.getString();

				if (actions[opcode - 30].equalsIgnoreCase("hidden")) {
					actions[opcode - 30] = null;
				}
			} else if (opcode == 40) {
				int length = buffer.getUnsignedByte();
				changedModelColours = new int[length];
				originalModelColours = new int[length];

				for (int i = 0; i < length; i++) {
					originalModelColours[i] = buffer.getUnsignedShort();
					changedModelColours[i] = buffer.getUnsignedShort();
				}
			} else if (opcode == 60) {
				int length = buffer.getUnsignedByte();
				dialogueModels = new int[length];

				for (int i = 0; i < length; i++) {
					dialogueModels[i] = buffer.getUnsignedShort();
				}
			} else if (opcode == 90) {
				buffer.getUnsignedShort();
			} else if (opcode == 91) {
				buffer.getUnsignedShort();
			} else if (opcode == 92) {
				buffer.getUnsignedShort();
			} else if (opcode == 93) {
				drawYellowDotOnMap = false;
			} else if (opcode == 95) {
				combatLevel = buffer.getUnsignedShort();
			} else if (opcode == 97) {
				scaleXZ = buffer.getUnsignedShort();
			} else if (opcode == 98) {
				scaleY = buffer.getUnsignedShort();
			} else if (opcode == 99) {
				visibilityOrRendering = true;
			} else if (opcode == 100) {
				modelLightning = buffer.getSignedByte();
			} else if (opcode == 101) {
				modelShadowing = buffer.getSignedByte() * 5;
			} else if (opcode == 102) {
				headIcon = buffer.getUnsignedShort();
			} else if (opcode == 103) {
				degreesToTurn = buffer.getUnsignedShort();
			} else if (opcode == 106) {
				varBitChild = buffer.getUnsignedShort();

				if (varBitChild == 65535) {
					varBitChild = -1;
				}

				configChild = buffer.getUnsignedShort();

				if (configChild == 65535) {
					configChild = -1;
				}

				int length = buffer.getUnsignedByte();
				childrenIDs = new int[length + 1];

				for (int i = 0; i <= length; i++) {
					childrenIDs[i] = buffer.getUnsignedShort();

					if (childrenIDs[i] == 65535) {
						childrenIDs[i] = -1;
					}
				}
			} else if (opcode == 107) {
				disableRightClick = false;
			}
		} while (true);
	}

	public static void printDefinitionsForId(int mobId) {
		/*Print out Grain*/
		EntityDef dump = EntityDef.get(mobId);
		if (dump.name != null) {
			System.out.println("Dumping: " + dump.name);
		} else {
			System.out.println("EntityDef.get(" + mobId + ").name == null");
		}
		System.out.println("combatlevel: " + dump.combatLevel);
		System.out.println("id: " + dump.id);
		if (dump.npcModels != null) {
			for (int i = 0; i < dump.npcModels.length; i++) {
				System.out.println("npcModels[" + i + "]: " + dump.npcModels[i]);
			}
		}
		if (dump.actions != null) {
			for (int i = 0; i < dump.actions.length; i++) {
				System.out.println("Action[" + i + "]: " + dump.actions[i]);
			}
		}
		System.out.println("degreesToTurn: " + dump.degreesToTurn);
		System.out.println("headIcon: " + dump.headIcon);
		System.out.println("npcSizeInSquares: " + dump.npcSizeInSquares);
		System.out.println("standAnimation: " + dump.standAnimation);
		System.out.println("walkAnimation: " + dump.walkAnimation);
		System.out.println("walkingBackwardsAnimation: " + dump.walkingBackwardsAnimation);
		System.out.println("walkLeftAnimation: " + dump.walkLeftAnimation);
		System.out.println("walkRightAnimation: " + dump.walkRightAnimation);
		if (dump.originalModelColours != null) {
			for (int i = 0; i < dump.originalModelColours.length; i++) {
				System.out.println("originalModelColours[" + i + "]: " + dump.originalModelColours[i]);
			}
		}
		if (dump.changedModelColours != null) {
			for (int i = 0; i < dump.changedModelColours.length; i++) {
				System.out.println("changedModelColours[" + i + "]: " + dump.changedModelColours[i]);
			}
		}
		if (dump.childrenIDs != null) {
			for (int i = 0; i < dump.childrenIDs.length; i++) {
				System.out.println("childrenIDs[" + i + "]: ");
			}
		}
		System.out.println("configChild: " + dump.configChild);
		System.out.println("varBitChild: " + dump.varBitChild);
		System.out.println("modelLightning: " + dump.modelLightning);
		System.out.println("modelShadowing: " + dump.modelShadowing);
		System.out.println("drawYellowDotOnMap: " + dump.drawYellowDotOnMap);
		System.out.println("disableRightClick: " + dump.disableRightClick);
		System.out.println("visibilityOrRendering: " + dump.visibilityOrRendering);


	}

}
