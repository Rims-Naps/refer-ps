package org.necrotic.client.cache.definition;

import org.necrotic.client.Client;
import org.necrotic.client.cache.config.VarBit;
import org.necrotic.client.collection.MRUNodes;
import org.necrotic.client.net.Stream;
import org.necrotic.client.media.AnimationSkeleton;
import org.necrotic.client.Signlink;
import org.necrotic.client.cache.Archive;
import org.necrotic.client.net.requester.OnDemandFetcher;
import org.necrotic.client.media.renderable.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;

public final class ObjectDef {

	private static final int[] showBlack = {3735, 26346, 26347, 26348, 26358, 26359, 26360, 26361, 26362, 26363, 26364};
	private static final Model[] aModelArray741s = new Model[4];
	public static boolean lowDetail;
	public static Client clientInstance;
	public static MRUNodes mruNodes2 = new MRUNodes(30);
	public static MRUNodes baseModels = new MRUNodes(500);
	private static int[] streamIndices;
	private static int[] streamIndices667;

    private static int[] streamIndicesOSRS;
	private static Stream stream667;
	private static Stream stream;

    private static Stream streamOSRS;
	public boolean obstructsGround;
	public String name;
	public int width;
	public int mapFunctionID;
	public int configID;
	public int type;
	public boolean impenetrable;
	public int mapSceneID;
	public int[] morphisms;
	public int length;
	public boolean adjustToTerrain;
	public boolean occludes;
	public boolean solid;
	public int plane;
	public int varbitIndex;
	public int decorDisplacement;
	public byte[] description;
	public boolean interactive;
	public boolean castsShadow;
	public int animation;
	public String[] actions;
	private byte brightness;
	private int offsetX;
	private int modelSizeY;
	private byte contrast;
	private int offsetH;
	private int[] originalModelColors;
	private int modelSizeX;
	private boolean aBoolean751;
	private int supportItems;
	public boolean isSolidObject;
	private boolean nonFlatShading;
	private int modelSizeH;
	public int[] modelIds;
	private int[] models;
	private int offsetY;
	private int[] modifiedModelColors;

	public static HashMap<Integer, ObjectDef> map = new HashMap<>();

	public void copy(int id) {
		this.setDefaults();
		ObjectDef target = ObjectDef.forID(id);
		this.modelIds = target.modelIds;
		this.models = target.models;
		this.name = target.name;
		this.description = target.description;
		this.width = target.width;
		this.length = target.length;
		this.solid = target.solid;
		this.impenetrable = target.impenetrable;
		this.interactive = target.interactive;
		this.actions = target.actions;
		this.adjustToTerrain = target.adjustToTerrain;
		this.nonFlatShading = target.nonFlatShading;
		this.occludes = target.occludes;
		this.animation = target.animation;
		this.decorDisplacement = target.decorDisplacement;
		this.brightness = target.brightness;
		this.contrast = target.contrast;
		this.modifiedModelColors = target.modifiedModelColors;
		this.originalModelColors = target.originalModelColors;
		this.mapFunctionID = target.mapFunctionID;
		this.aBoolean751 = target.aBoolean751;
		this.castsShadow = target.castsShadow;
		this.modelSizeX = target.modelSizeX;
		this.modelSizeH = target.modelSizeH;
		this.modelSizeY = target.modelSizeY;
		this.mapSceneID = target.mapSceneID;
		this.plane = target.plane;
		this.offsetX = target.offsetX;
		this.offsetH = target.offsetH;
		this.offsetY = target.offsetY;
		this.obstructsGround = target.obstructsGround;
		this.isSolidObject = target.isSolidObject;
		this.supportItems = target.supportItems;
		this.varbitIndex = target.varbitIndex;
		this.configID = target.configID;
		this.morphisms = target.morphisms;
	}

	public static ObjectDef forID(int id) {
		ObjectDef definition = map.get(id);
		if (definition != null) {
			return definition;
		}
		definition = new ObjectDef();


		boolean loadNew = (
				/*id == 8550 || id == 8551 || id == 7847 || id == 8150 || */id == 32159 || id == 32157 || id == 36672 || id == 36675 || id == 36692 || id == 34138 || id >= 39260 && id <= 39271 || id == 39229 || id == 39230 || id == 39231 || id == 36676 || id == 36692 || id > 11915 && id <= 11929 || id >= 11426 && id <= 11444 || id >= 14835 && id <= 14845 || id >= 11391 && id <= 11397 || id >= 12713 && id <= 12715);
		if (id < 0) {
			id = 0;
		}
		//    stream.position = streamIndices[id];

		try {
/*            if (id >= 110000) {
                streamOSRS.position = streamIndicesOSRS[id - 110000];
            } else */if (id > streamIndices.length || loadNew) {
				stream667.position = streamIndices667[id];
			} else {
				stream.position = streamIndices[id];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		definition.type = id;
		definition.setDefaults();
		//    definition.readValues(stream);
/*        if (id >= 110000) {
            definition.readValues(streamOSRS, true);
        } else */if (id > streamIndices.length || loadNew) {
			definition.readValues(stream667, false);
		} else {
			definition.readValues(stream, false);
		}
		for (int element : showBlack) {
			if (id == element) {
				definition.modifiedModelColors = new int[1];
				definition.originalModelColors = new int[1];
				definition.modifiedModelColors[0] = 0;
				definition.originalModelColors[0] = 1;
			}

		}

		if (definition.name == null) {
			definition.name = "";
			definition.interactive = false;
		}

		int[][] shootingStars = {{2165}, {42166}, {42163}, {42164}, { 42160}, { 42159}, { 42168}, { 42169},};

/*		for (int[] i : shootingStars) {
			if (id == i[0]) {
				stream.position = streamIndices[3514];
				definition.setDefaults();
				definition.readValues(stream);
				definition.modelIds = new int[1];
				definition.modelIds[0] = i[1];
				definition.width = 2;
				definition.length = 2;
				definition.name = "Crashed star";
				definition.actions = new String[5];
				definition.actions[0] = "Mine";
				definition.description = "A crashed star!".getBytes();
			}
		}*/
		loadEvilTree(definition);
		if (definition.description == null) {
			definition.description = ("It's a " + definition.name + ".").getBytes();
		}
		if (definition.name != null) {
			definition.name = "<col=AF70C3>" + definition.name;
		}

			if (definition.actions == null || definition.actions.length < 5) {
			String[] newActions = new String[5];
			if (definition.actions != null) {
				for (int i = 0; i < 5; i++) {
					if (i >= definition.actions.length) {
						newActions[i] = null;
					} else {
						newActions[i] = definition.actions[i];
					}
				}
			}
			definition.actions = newActions;
		}


	//	boolean removeObject = definition.type == 1442 || definition.type == 1433 || definition.type == 1443 || definition.type == 1441 || definition.type == 26916 || definition.type == 26917 || definition.type == 5244 || definition.type == 2623 || definition.type == 2956 || definition.type == 463 || definition.type == 462 || definition.type == 10527 || definition.type == 10529 || definition.type == 40257 || definition.type == 296 || definition.type == 300 || definition.type == 1747 || definition.type == 7332 || definition.type == 7326 || definition.type == 7325 || definition.type == 7385 || definition.type == 7331 || definition.type == 7385 || definition.type == 7320 || definition.type == 7317 || definition.type == 7323 || definition.type == 7354 || definition.type == 1536 || definition.type == 1537 || definition.type == 5126 || definition.type == 1551 || definition.type == 1553 || definition.type == 1516 || definition.type == 1519 || definition.type == 7126 || definition.type == 733 || definition.type == 14233 || definition.type == 14235 || definition.type == 1596 || definition.type == 1597 || definition.type == 14751 || definition.type == 14752 || definition.type == 14923 || definition.type == 36844 || definition.type == 30864 || definition.type == 2514 || definition.type == 1805 || definition.type == 15536 || definition.type == 2399 || definition.type == 14749 || definition.type == 29315 || definition.type == 29316 || definition.type == 29319 || definition.type == 29320 || definition.type == 29360 || definition.type == 1528 || definition.type == 36913 || definition.type == 36915 || definition.type == 15516 || definition.type == 35549 || definition.type == 35551 || definition.type == 26808 || definition.type == 26910 || definition.type == 26913 || definition.type == 24381 || definition.type == 15514 || definition.type == 25891 || definition.type == 1530 || definition.type == 16776 || definition.type == 16778 || definition.type == 28589 || definition.type == 1533 || definition.type == 17089 || definition.type == 1600 || definition.type == 1601 || definition.type == 11707 || definition.type == 24376 || definition.type == 24378 || definition.type == 40108 || definition.type == 59 || definition.type == 2069 || definition.type == 36846 || definition.type == 1506 || definition.type == 9299 || definition.type == 1508 || definition.type == 4031 || definition.type == 11470 || (definition.name.equalsIgnoreCase("gate") && definition.type != 1558 && definition.type != 1557 && definition.type != 2391 && definition.type != 2392 && definition.type != 1561 && definition.type != 1589   && definition.type != 2688 && definition.type != 2687 && definition.type != 26081 && definition.type != 26082 && definition.type != 13322  && definition.type != 15604 && definition.type != 15605 && definition.type != 37668);
		//if (removeObject && definition.type != 167 && definition.type != 166) {
	//		definition.modelIds = null;
	//		definition.interactive = false;
	//		definition.solid = false;
	//		return definition;
	//	}
        /*if(definition.varbitIndex <= 484 && definition.varbitIndex >= 469) {
            definition.configID = definition.varbitIndex;
            definition.varbitIndex = -1;
        }*/
		if (definition.type == 13831) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = false;


		}
		if (definition.type == 13833) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = false;


		}
		if (definition.type == 10019) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "Magic Pillar";

		}
		if (definition.type == 20389) {
			definition.actions = new String[]{"Enter", null, null, null, null};
			definition.name = "Magic Gate";
			definition.interactive = true;

		}
		if (definition.type == 33395) {
			definition.actions = new String[]{"Cross", null, null, null, null};
			definition.name = "Fire Path";
			definition.interactive = true;

		}
		if (definition.type == 42019) {
			definition.copy(90);
			definition.interactive = true;
			definition.actions = new String[]{"Pass", null, null, null, null};

			definition.name = "<col=1097BF>Void Barrier";
	/*		definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;*/
		}

			if (definition.type == 42611) {
			definition.actions = new String[]{"@cya@Enter", null, null, null, null};
			definition.name = "@cya@Aqua Chamber";
			definition.interactive = true;
		}

	/*	if (definition.type == 1093) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "@cya@Chair";
		}
		if (definition.type == 25123) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "@cya@Candle Stand";
		}
		if (definition.type == 5358) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "@cya@Skeleton";
		}*/

		if (definition.type == 1093) {
			definition.name = "<col=AF70C3>Void Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 25123) {
			definition.name = "<col=AF70C3>Void Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 5358) {
			definition.name = "<col=AF70C3>Void Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}

		if (definition.type == 7352) {
			definition.modelIds = new int[]{11231, 11235};
			definition.animation = 3174;
			definition.offsetX = 0;
			definition.offsetH = 0;
			definition.offsetY = 65;
			definition.modelSizeX = 165;
			definition.modelSizeH = 165;
			definition.modelSizeY = 165;
			definition.name = "Void Chamber";
			definition.actions = new String[5];
			definition.actions[0] = "Enter";
		}

		if (definition.type == 13626) {
			definition.actions = new String[]{"@cya@Enter", null, null, null, null};
			definition.name = "@cya@Void Chamber";
			definition.interactive = true;
		}
		if (definition.type == 15601) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = false;

		}

		if (definition.type == 21432 ) {
			definition.actions = new String[]{"Enchant", null, null, null, null};
			definition.interactive = true;
			definition.impenetrable = false;
			definition.solid = false;
			definition.isSolidObject = false;
			definition.obstructsGround = false;
			definition.width = 1;
			definition.name = "Enchanted Rock";
		}

		if (definition.type == 21433 ) {
			definition.actions = new String[]{"Siphon", null, null, null, null};
			definition.interactive = true;
			definition.name = "Crystal Outcrop";
		}

		if (definition.type == 1345 ) {
			definition.actions = new String[]{"Chop", null, null, null, null};
			definition.interactive = true;
			definition.name = "Undead Tree";
		}
		if (definition.type == 49806) {
			definition.actions = new String[]{"Mine", null, null, null, null};
			definition.interactive = true;
			definition.name = "Soulstone Rock";
		}
		if (definition.type == 49780 ) {
			definition.actions = new String[]{"Mine", null, null, null, null};
			definition.interactive = true;
			definition.name = "Blurite Rock";
		}
		if (definition.type == 40568 ) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = true;
			definition.name = "Eternal Flame";
		}
		if (definition.type == 13407  ) {
			definition.actions = new String[]{"Fish", null, null, null, null};
			definition.interactive = true;
			definition.name = "Pond";
		}

		if (definition.type == 20001) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = true;
			definition.name = "Fire";
			definition.modelSizeX = 175;
			definition.modelSizeH = 175;
			definition.modelSizeY = 175;
		}

		if (definition.type == 16089) {
			definition.actions[0] = "@whi@Enter";
			definition.interactive = true;
			definition.modelSizeY = 200;
		}
		if (definition.type == 10252) {
			definition.actions = new String[]{"@cya@Enter", null, null, null, null};
			definition.name = "@cya@Boss Portal";
			definition.interactive = true;
		}
		if (definition.type == 3913) {
			definition.actions = new String[]{"@cya@Pick", null, null, null, null};
			definition.name = "@cya@Mystic Mushrooms";
			definition.interactive = true;
		}
		if (definition.type == 3917) {
			definition.actions = new String[]{"@red@Pick", null, null, null, null};
			definition.name = "@red@Corrupt Mushrooms";
			definition.interactive = true;
		}
		if (definition.type == 10252) {
			definition.actions = new String[]{"@cya@Enter", null, null, null, null};
			definition.name = "@cya@Boss Portal";
			definition.interactive = true;
		}
		if (definition.type == 6490) {
			definition.actions = new String[]{"@cya@Travel", null, null, null, null};
			definition.name = "@cya@Enchanted Obelisk";
			definition.interactive = true;
		}
		if (definition.type == 4388) {
			definition.actions = new String[]{"@cya@Enter", null, null, null, null};
			definition.name = "@cya@Event Portal";
			definition.interactive = true;
		}
		if (definition.type == 27073) {
			definition.name = "Flowers";
			definition.solid = false;
		}
		if (definition.type == 2469) {
			definition.actions = new String[]{"@cya@Enter", null, null, null, null};
			definition.name = "@cya@Afk Portal";
			definition.interactive = true;
		}

		if (definition.type == 3782) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Gate";
		}
		if (definition.type == 3783) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Gate";
		}
		if (definition.type == 1558) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Gate";
		}
		if (definition.type == 23117) {
			definition.actions = new String[]{"@yel@Go Down", null, null, null, null};
			definition.name = "@cya@The Rabbit Hole";
		}


		if (definition.type == 1557) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Gate";
		}
		if (definition.type == 4069) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Wall";

		}
		if (definition.type == 2341) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Wall";
		}
		if (definition.type == 1586) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Wall";
		}
		if (definition.type == 2629) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Wall";
		}
		if (definition.type == 1584) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Wall";
		}

		if (definition.type == 124) {
			definition.adjustToTerrain = true;
			definition.nonFlatShading = true;
		}



		if (definition.type == 1140) {
			definition.modelIds = new int[]{100880};
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.interactive = true;
			definition.name = "<col=AB1433>Monstrous Chest";
		}
		if (definition.type == 1141) {
			definition.modelIds = new int[]{100882};
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.interactive = true;
			definition.name = "<col=E1AAE3>Magnificent Chest";
		}
		if (definition.type == 1142) {
			definition.modelIds = new int[]{100884};
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.interactive = true;
			definition.name = "<col=77BC8C>Mysterious Chest";
		}
		if (definition.type == 12603) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Bush";
		}
		if (definition.type == 30141) {
			definition.originalModelColors = new int[]{49463, 49463, 49463, 49463};
		}
		if (definition.type == 28214) {
			definition.actions = new String[]{"Travel", null, null, null, null};
			definition.name = "@blu@Mystic Zone";
		}
		if (definition.type == 10251) {
			definition.actions = new String[]{"Travel", null, null, null, null};
			definition.name = "@red@Power Zone";
		}
		if (definition.type == 27254) {
			definition.actions = new String[]{"Travel", null, null, null, null};
			definition.name = "@gre@Vision Zone";
		}

		if (definition.type == 415) {
			definition.modelIds = new int[]{100865};
			definition.name = "@red@<img=12>Beast <col=0FF74D>Hunter @yel@Chest<img=12>";
			definition.modelSizeX = 200;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.actions[0] = "@whi@Open";

		}
		if (definition.type == 6836) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.name = "Jail Cell";
		}
		if (definition.type == 3195) {
			definition.actions = new String[]{"Throw Tomato", null, null, null, null};
			definition.name = "Stand";
		}
		if (definition.type == 8441) {
			definition.actions = new String[]{null, null, null, null, null};
		}


		//if (definition.type == 47748) {
			//definition.modelIds = new int[]{32964};
			//definition.name = "";

			//definition.modelSizeX = 135;
			//definition.modelSizeH = 135;
			//definition.modelSizeY = 135;
			//definition.description = "@cya@Burrr it's cold outside...".getBytes();
			//definition.actions = new String[]{null, null, null, null, null};
		//}

		if (definition.type == 19038) {
			definition.name = "@gre@Christmas Tree";
			definition.modelSizeX = 450;
			definition.modelSizeH = 450;
			definition.modelSizeY = 450;
			definition.description = "@cya@Burrr it's cold outside...".getBytes();
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 507) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 1363) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 1366) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 3508) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 3510) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 1171) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 3501) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 3505) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 1173) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 3508) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 684) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 3512) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 1368) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 5006) {
			definition.name = "";
			definition.modelIds = new int[]{1610};
			definition.actions = new String[]{null, null, null, null, null};
			definition.solid = false;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 41582) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 41583) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 1291) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 47746) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 567) {
			definition.name = "@red@Tom Thumb Statue";
			definition.description = "AIN'T NO WAY!".getBytes();

			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 28486) {
			definition.name = "Campfire";
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 10739) {
			definition.name = "@gre@::rspsguy";
			definition.description = "Mickey smells...".getBytes();

			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 47745) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 47744) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 14604) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 14532) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 667) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 5282) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 42893) {
			definition.solid = true;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 21181) {
			definition.name = "Snow Shed";
			definition.description = "@cya@Burrr it's cold outside...".getBytes();
			definition.actions = new String[]{null, null, null, null, null};

		}
		if (definition.type == 5238) {
			definition.originalModelColors = new int[]{25, 15,};
		}
		if (definition.type == 5456) {
			definition.originalModelColors = new int[]{25, 15,};
		}
		if (definition.type == 5236) {
			definition.originalModelColors = new int[]{25, 15,25};
		}

		if (definition.type == 28294) {
			definition.description = "@cya@Burrr it's cold outside...".getBytes();
			definition.actions = new String[]{null, null, null, null, null};

		}
		if (definition.type == 3045) {
			definition.name = "<col=6C1894>Bank Of Athens";

		}
		if (definition.type == 19435) {
			definition.name = "Pile of snow";
			definition.description = "@cya@Burrr it's cold outside...".getBytes();
			definition.actions = new String[]{null, null, null, null, null};

		}

		if (definition.type == 22973) {
			definition.actions = new String[]{"Restore", null, null, null, null};
		}

		if (definition.type == 52650) {
			definition.interactive = true;
			definition.name = "Perks Statue";
			definition.actions = new String[5];
			definition.actions[0] = "Open Perks";
			definition.modelIds = new int[]{100055};
		}
		if (definition.type == 13291) {
			definition.name = "@yel@Master Treasure Chest";
			definition.originalModelColors = new int[]{5055, 5088, 5055};
			definition.modifiedModelColors = new int[]{51111, 8128, 7093};
			definition.actions = new String[]{"@yel@Open", null, null, null, null};
			definition.modelSizeX = 160;
			definition.modelSizeH = 160;
			definition.modelSizeY = 160;
		}
		if (definition.type == 22361) {
			definition.originalModelColors = new int[]{35, 31, 26, 22, 26, 22};
		}
		if (definition.type == 28788) {
			definition.originalModelColors = new int[]{22};
		}


		if (definition.type == 42687) {
			definition.name = "";
			definition.modelIds = new int[]{32964};
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelSizeX = 25;
			definition.modelSizeH = 25;
			definition.modelSizeY = 25;
		}
		if (definition.type == 22823) {
			definition.name = "@yel@Cosmetic Overload";
			definition.modelIds = new int[]{16371};
			definition.modelSizeX = 160;
			definition.modelSizeH = 160;
			definition.modelSizeY = 160;
			definition.actions = new String[]{"@red@Shop", null, null, null, null};
		}

		if (definition.type == 4468) {
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.actions = new String[]{"Pass", null, null, null, null};
		}
		if (definition.type == 4470) {
			definition.actions = new String[]{"Pass", null, null, null, null};
		}


		if (definition.type == 8469) {
			definition.name = "<col=AF70C3>Frenzy Zone";
			definition.actions = new String[]{"Enter", null, null, null, null};
			definition.modelIds = new int[]{2318};
			definition.animation = 504;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.interactive = true;

		}


		if (definition.type == 52) { //earth barrier 1
			definition.name = "<col=5FD857>Gaia Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 996) { //earth barrier2
			definition.name = "<col=5FD857>Gaia Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}

		if (definition.type == 59) { //fire barrier1
			definition.name = "<col=D45F4B>Fire Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 53) { //fire barrier 2
			definition.name = "<col=D45F4B>Fire Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}

		if (definition.type == 90) { //water barrier 1
			definition.name = "<col=1097BF>Aqua Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 94) { //water barrier 2
			definition.name = "<col=1097BF>Aqua Barrier";
			definition.modelIds = new int[]{38325};
			definition.animation = 9652;
			definition.modelSizeX = 128;
			definition.modelSizeH = 128;
			definition.modelSizeY = 128;
			definition.actions = new String[]{"Pass", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 41200) {//RED COFFIN
			definition.interactive = true;
			definition.modelIds = new int[]{131858};
			definition.name = "Coffin";
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.modelSizeX = 225;
			definition.modelSizeH = 225;
			definition.modelSizeY = 225;
		}
		if (definition.type == 29577) {//
			definition.modelSizeX = 225;
			definition.modelSizeH = 225;
			definition.modelSizeY = 225;
			definition.offsetX = 60;
			definition.offsetH = 0;
			definition.offsetY = 0;
			definition.interactive = true;
			definition.obstructsGround = false;

		}
		if (definition.type == 27306) {
			definition.setDefaults();
			definition.interactive = true;
			definition.offsetH = 150;
			definition.offsetY = 65;
			definition.name = "Energy";
			definition.modelSizeH = 350;
			definition.modelSizeY = 350;
			definition.modelSizeX = 350;
			definition.modelIds = new int[]{41472};
			definition.animation = 10136;
			definition.isSolidObject = false;
			definition.obstructsGround = false;
			definition.castsShadow = false;
			definition.actions = new String[]{"Siphon", null, null, null, null};
		}
		if (definition.type == 52768) {
			definition.name = "Boss Lair";
			definition.interactive = true;
			definition.modelIds = new int[]{12923};

			definition.actions = new String[]{"Enter", null, null, null, null};

		}

			if (definition.type == 1309) {
			definition.isSolidObject = false;
			definition.obstructsGround = false;
			definition.castsShadow = false;
			definition.actions = new String[]{"Chop", null, null, null, null};
		}
		if (definition.type == 1281) {
			definition.isSolidObject = false;
			definition.obstructsGround = false;
			definition.castsShadow = false;
			definition.actions = new String[]{"Chop", null, null, null, null};
		}
		if (definition.type == 4060) {
			definition.obstructsGround = false;
			definition.isSolidObject = false;

			definition.actions = new String[]{"Chop", null, null, null, null};
		}
		if (definition.type == 10019) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 38150) {
			definition.actions = new String[]{"Enter", null, null, null, null};
		}
		if (definition.type == 43667) {//
			definition.interactive = true;
			definition.castsShadow = false;

			definition.name = "Barrows";
			definition.actions = new String[]{"Enter", null, null, null, null};
		}
		if (definition.type == 41201) {//BLUE COFFIN
			definition.interactive = true;
			definition.modelIds = new int[]{131859};
			definition.name = "Coffin";
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.modelSizeX = 225;
			definition.modelSizeH = 225;
			definition.modelSizeY = 225;
		}
		if (definition.type == 41202) {//GREEN COFFIN
			definition.interactive = true;
			definition.modelIds = new int[]{131860};
			definition.name = "Coffin";
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.modelSizeX = 225;
			definition.modelSizeH = 225;
			definition.modelSizeY = 225;
		}

		if (definition.type == 2) {
			definition.modelIds = new int[]{100083};
		}

		if (definition.type == 22942) { //RAID
			definition.name = "";
			definition.modelIds = new int[]{6357};
			definition.actions = new String[]{"@red@Start Game", "@red@Configure Invocations", null, null, null};

		}

		if (definition.type == 28115) {
			definition.interactive = true;
			definition.name = "Tower Party";
			definition.actions = new String[]{"View", null, null, null, null};
		}

		if (definition.type == 406) {
			definition.name = "<col=AF70C3>Rift Chest";
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.modelSizeX = 300;
			definition.modelSizeH = 300;
			definition.modelSizeY = 300;
			definition.modelIds = new int[]{62751};

		}

        if (definition.type == 407) {
            definition.name = "Jack frost";
            definition.actions = new String[]{"Rip out heart", null, null, null, null};
            definition.modelSizeX = 300;
            definition.modelSizeH = 300;
            definition.modelSizeY = 300;
            definition.modelIds = new int[]{45189};
            definition.animation = 11053;

        }
/*		if (definition.type == 408) {
			definition.modelIds = new int[]{101474};
			definition.name = "@bla@Headless Pumpkin Man";
			definition.actions = new String[]{"Talk", null, null, null, null};
		}*/
		if (definition.type == 41204) {
			definition.interactive = true;
			definition.isSolidObject = true;
			definition.modelSizeX = 100;
			definition.modelSizeH = 100;
			definition.modelSizeY = 100;
			definition.modelIds = new int[]{56642};
			definition.name = "Master Treasure Chest";
			definition.actions = new String[]{"Travel", null, null, null, null};
		}
		if (definition.type == 7311) {
			definition.interactive = true;
			definition.modelSizeX = 238;
			definition.modelSizeH = 238;
			definition.modelSizeY = 238;
			definition.name = "@red@Skeletal Offering";
			definition.actions = new String[]{"@red@Offer", null, null, null, null};
			//definition.rdc = 1;

		}
		if (definition.type == 41203) {
			definition.interactive = true;
			definition.modelIds = new int[]{16024};
			definition.modelSizeX = 238;
			definition.modelSizeH = 238;
			definition.modelSizeY = 238;
			definition.name = "The Arcade Gauntlet";
			definition.actions = new String[]{"Enter", null, null, null, null};
		}
		if (definition.type == 16187) {
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = false;
		}

		if (definition.type == 41205) {
			definition.setDefaults();
			definition.interactive = true;
			definition.isSolidObject = true;
			definition.modelSizeX = 50;
			definition.modelSizeH = 50;
			definition.modelSizeY = 50;
			definition.modelIds = new int[]{15681};
			definition.name = "Gamble Zone";
			definition.actions = new String[]{"Travel", null, null, null, null};
		}
		if (definition.type == 41206) {
			definition.setDefaults();
			definition.interactive = true;
			definition.isSolidObject = true;
			definition.length = 1;
			definition.width = 1;
			definition.modelIds = new int[]{100201};
			definition.name = "Halls of Athens Chest";
			definition.actions = new String[]{"Open", null, null, null, null};
		}
		if (definition.type == 41207) {
			definition.setDefaults();
			definition.interactive = true;
			definition.isSolidObject = true;
			definition.length = 1;
			definition.width = 1;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.modelSizeX = 200;
			definition.modelIds = new int[]{100236};
			definition.name = "Treasure Hunter Chest";
			definition.actions = new String[]{"Open", null, null, null, null};
		}

		if (definition.type == 13192) {
			definition.actions = new String[]{"Pray", null, null, null, null};
		}

		if (definition.type == 23928) {
			definition.name = "Player Owned Stores";
		}
		if (definition.type == 5282) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};

		}

		if (definition.type == 30035) {
			definition.interactive = true;
			definition.name = "AFK Stall (3)";
			definition.actions = new String[5];
			definition.actions[0] = "Steal from";
			definition.actions[4] = "Check rewards";
			definition.width = 4;
			definition.length = 4;
			definition.modelSizeH = 300;
			definition.modelSizeY = 300;
			definition.modelSizeX = 300;
		}
		if (definition.type == 41900) {
			definition.name = "SlayerDungeon";
			definition.modelSizeH = 80;
			definition.modelSizeY = 80;
			definition.modelSizeX = 80;
			definition.width = 2;
			definition.length = 2;
		}


		if (definition.name != null && definition.type != 591) {
			String s = definition.name.toLowerCase();
			if (s.contains("bank") && !s.contains("closed")) {
				definition.actions = new String[5];
				definition.actions[0] = "Use";
			}
		}
		if (definition.type == 2213) {
			definition.name = "<col=AF70C3>Athens Bank";
			definition.modelIds = new int[]{132204};
			definition.animation = 3030;
		}
		if (definition.type == 6282) {
			definition.name = "@red@Lava Chamber";
			definition.actions = new String[]{"Enter", null, null, null, null};
			definition.interactive = true;

		}
		if (definition.type == 6283) {
			definition.name = "Athens Pillar";
		}
		if (definition.type == 59732) {
			definition.name = "<col=AF70C3>Bank booth";
			definition.actions = new String[5];
			definition.actions[0] = "Use";
			definition.modelIds = new int[]{99545};
		}
		if (definition.type == 2882 || definition.type == 2883) {
			definition.name = "Gate";
			definition.actions = new String[5];
			definition.actions[0] = "Open";
			definition.actions[1] = null;
		}
		if (definition.type == 14817) {
			definition.name = "@red@AFK Mining ore";

			definition.modelSizeX = 50;
			definition.modelSizeH = 100;
			definition.modelSizeY = 50;
			definition.width = 1;
			definition.length = 1;
			definition.actions = new String[5];
			definition.actions[0] = "Mine";
			definition.actions[1] = null;
		}
		if (definition.type == 27330) {
			definition.name = "World Portal";

			definition.actions = new String[5];
			definition.actions[0] = "Quick Travels";
			definition.actions[1] = null;
		}
		if (definition.type == 24690 || definition.type == 67) {
			definition.name = "";

			definition.actions = new String[5];
			definition.actions[0] = null;
			definition.actions[1] = null;
			definition.modelSizeX = 170;
			definition.modelSizeH = 140;
			definition.modelSizeY = 170;
		}
		if (definition.type == 20040) {
		//	definition.name = "@yel@Upgrader";
			definition.actions = new String[5];
			definition.actions[0] = "@yel@Upgrade";
			definition.actions[1] = "@red@Combine";
			definition.modelSizeX = 100;
			definition.modelSizeH = 100;
			definition.modelSizeY = 100;
		}

		if (definition.type == 24836) {
			definition.modelIds = new int[]{25967};
		}
		if (definition.type == 25808) {
			definition.name = "<col=AF70C3>Bank Booth";
			definition.modelIds = new int[]{99545};
			definition.length = 1;
			definition.width = 1;
		}
		if (definition.type == 39431) {
			definition.name = "Rock of Afk";
			definition.modelIds = new int[]{62525};
			definition.modelSizeH = 300;
			definition.modelSizeY = 150;
			definition.modelSizeX = 150;
			definition.length = 1;
			definition.width = 1;
			definition.actions = new String[5];
			definition.actions[0] = "Mine";
		}
		if (definition.type == 13129) {
			definition.name = "";
			// definition.modelIds = new int[] { 62525 };
			//   definition.modelSizeH = 300;
			//   definition.modelSizeY=70;
			//   definition.modelSizeX=70;
			definition.length = 1;
			definition.width = 1;
			definition.actions = new String[5];
			definition.actions[0] = null;
			definition.actions[1] = null;
		}
		if (definition.type == 45293) {
			definition.name = "Broken Ladder";
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Climb";

		}
		if (definition.type == 1317) {
			definition.name = "Waystone";
			definition.actions = new String[]{"Teleports", "Rejuvenate", null, null, null};
			definition.modelSizeH = 160;
			definition.modelSizeY = 160;
			definition.modelSizeX = 160;
			definition.modelIds = new int[]{132199};
			definition.animation = 3174;
			definition.castsShadow = false;
			definition.description = "Used to Travel around Athens".getBytes();
			//definition.modelIds = new int[]{62525}; cool crystal model

		}




		if (definition.type == 2525) {
			definition.name = "Narnia";
			definition.actions = new String[5];
			definition.actions[0] = "Enter";

		}
		if (definition.type == 4814) {
			definition.name = "";
			definition.modelSizeH = 60;
			definition.modelSizeY = 60;
			definition.modelSizeX = 60;
			definition.length = 1;
			definition.width = 1;

		}
		if (definition.type == 16124 || definition.type == 16123 || definition.type == 16066 || definition.type == 16065 || definition.type == 16089  || definition.type == 16044 || definition.type == 16043) {

			definition.modelSizeX = 120;
			definition.modelSizeH = 20;
			definition.modelSizeY = 120;
			definition.name = "Barrier";
			definition.width = 1;
			definition.length = 1;
			definition.actions = new String[5];
			definition.actions[0] = null;
			definition.actions[1] = null;
			definition.solid = false;


		}

		if (definition.type == 16090) {
			definition.modelSizeX = 575;
			definition.modelSizeH = 60;
			definition.modelSizeY = 275;
			definition.name = "@yel@Frenzy Barrier";
			definition.solid = true;
			definition.width = 1;
			definition.length = 1;
			definition.actions = new String[]{"@gre@Enter", "@red@Exit", null, null, null};
		}

		if (definition.type == 17246) {
			definition.name = "@mag@Slot Machine";
			definition.actions = new String[]{"@whi@Gamble", null, null, null, null};
		}
		if (definition.type == 453) {
			definition.actions = new String[]{null, null, null, null, null};

		}
		if (definition.type == 477) {
			definition.name = "@yel@Gate";
			definition.actions = new String[]{"Enter@red@(2)", null, null, null, null};
			definition.modelIds = new int[]{15579};

		}
		if (definition.type == 478) {
			definition.name = "@yel@Gate";
			definition.actions = new String[]{"Enter@red@(3)", null, null, null, null};
			definition.modelIds = new int[]{15579};

		}
		if (definition.type == 479) {
			definition.name = "@yel@Token Room";
			definition.actions = new String[]{"Enter", null, null, null, null};
			definition.modelIds = new int[]{15579};

		}

		if (definition.type == 473) {
			definition.name = "@yel@Gate";
			definition.actions = new String[]{"Enter@red@(4)", null, null, null, null};
			definition.modelIds = new int[]{15579};


		}

		if (definition.type == 622) {
			definition.name = "<col=AF70C3>Bank Booth";
			definition.solid = false;
			definition.mapFunctionID = 5;
			definition.modelSizeX = 70;
			definition.modelSizeH = 70;
			definition.modelSizeY = 70;
			definition.actions = new String[5];
			definition.actions[0] = "Use";
			definition.actions[1] = "Use Quickly";
			definition.modelIds = new int[]{132204};
			definition.animation = 3030;
		}
		if (definition.type == 16958) {
			definition.modelIds = new int[]{101794};
			definition.name = "@yel@Christmas Chest";
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Open";
		}



		if (definition.type == 11666) {
			definition.actions = new String[5];
			definition.actions[0] = "Smelt";
			//definition.actions[1] = "Craft";
		}
		if (definition.type == 23963) {
			definition.actions = new String[5];
			definition.actions[0] = "Smelt";
		}
		if (definition.type == 28426 || definition.type == 28427) {
			definition.name = "Rocky plinth";
			definition.actions = new String[5];
			definition.description = "A very old rock formation.".getBytes();
		}
		if (definition.type == 28449 || definition.type == 28474 || definition.type == 28448) {
			definition.name = "Dense brush";
			definition.actions = new String[5];
			definition.description = "Too thick to walk through.".getBytes();
		}
		if (definition.type == 10318) {
			definition.name = null;
			definition.actions = new String[5];

		}
		if (definition.type == 2856) {
			definition.modelIds = new int[]{101796};
			definition.name = "Narnia Chest";
			definition.actions = new String[5];
			definition.solid = true;
			definition.interactive = true;


		}
		if (definition.type == 57262) {
			definition.name = null;
			definition.actions = new String[5];
			definition.solid = false;

		}
		if (definition.type == 57263) {
			definition.name = null;
			definition.actions = new String[5];
			definition.solid = false;

		}
		if (definition.type == 7475) {
			definition.modelIds = new int[]{2318};
			definition.name = "Next Room";
			definition.actions[0] = "Quick Travel-to";
			definition.modelSizeX = 70;
			definition.modelSizeH = 100;
			definition.modelSizeY = 70;
			definition.interactive = true;
			//  definition.rdc2 = 2552;

		}
		if (definition.type == 7476) {
			definition.modelIds = new int[]{2318};
			definition.name = "Next Room";
			definition.actions[0] = "Quick Travel-to";
			definition.modelSizeX = 70;
			definition.modelSizeH = 100;
			definition.modelSizeY = 70;
			definition.interactive = true;
			//  definition.rdc2 = 2552;

		}
		if (definition.type == 7477) {
			definition.modelIds = new int[]{2318};
			definition.name = "Next Room";
			definition.actions[0] = "Quick Travel-to";
			definition.modelSizeX = 70;
			definition.modelSizeH = 100;
			definition.modelSizeY = 70;
			definition.interactive = true;
			//  definition.rdc2 = 2552;

		}

		if (definition.type == 7478) {
			definition.modelIds = new int[]{2318};
			definition.name = "@red@Boss Room";
			definition.actions[0] = "Quick Travel-to";
			definition.modelSizeX = 70;
			definition.modelSizeH = 100;
			definition.modelSizeY = 70;
			definition.interactive = true;
			//  definition.rdc2 = 2552;

		}


		if (definition.type == 7479) {
			definition.name = "@yel@Tree";
			definition.modelIds = new int[]{1681};
			definition.actions = new String[5];
			definition.modelSizeX = 70;
			definition.modelSizeH = 70;
			definition.modelSizeY = 70;
			definition.actions[0] = null;

		}
		if (definition.type == 1994) {

			definition.name = "@gre@<shad=355>Master Treasure Chest<shad=-1>";
			definition.actions[0] = "Search";
			definition.modelIds = new int[]{15717};
			definition.modelSizeX = 150;
			definition.modelSizeH = 150;
			definition.modelSizeY = 150;
			definition.interactive = true;
			//  definition.rdc2 = 2552;

		}
		if (definition.type == 3378) {

			definition.name = "@red@Darkness Chest";
			definition.actions[0] = "Search";
			definition.modelIds = new int[]{100339};
			definition.modelSizeX = 300;
			definition.modelSizeH = 300;
			definition.modelSizeY = 300;
			definition.interactive = true;
			//  definition.rdc2 = 2552;

		}

		if (definition.type == 28457 || definition.type == 28458 || definition.type == 28459 || definition.type == 28460 || definition.type == 28461 || definition.type == 28462 || definition.type == 28463 || definition.type == 28464 || definition.type == 28465 || definition.type == 28466 || definition.type == 28467 || definition.type == 28468 || definition.type == 28469 || definition.type == 28470 || definition.type == 28471 || definition.type == 28472 || definition.type == 28473 || definition.type == 28456) {
			definition.name = "Murky water";
			definition.description = "This water doesn't look clean...".getBytes();
		}
		if (definition.type == 134 || definition.type == 135) {
			definition.name = "Heavy door";
			definition.actions = new String[5];
			definition.actions[0] = "Push";
		}
		if (definition.type == 17953) { //zulrah boat
			definition.actions = new String[5];
			definition.name = "Charons Ferry";
			definition.actions[0] = "Fix";
			definition.description = "I want you to know that I was rooting for you, mate. Know that.".getBytes();
		}
		if (definition.type == 57225) {
			definition.actions = new String[5];
			definition.actions[0] = "Climb-over";
		}
		if (definition.type == 2305) {
			definition.actions = new String[5];
			definition.actions[0] = "Escape";
		}
		if (definition.type == 589) {
			definition.actions = new String[5];
			definition.actions[0] = "Search";
		}
		if (definition.type == 11678) {
			definition.actions = new String[5];
			definition.actions[0] = "Inspect";
		}
		if (definition.type == 11339) {
			definition.actions = new String[5];
			definition.actions[0] = "Search";
		}
		if (definition.type == 173) {
			definition.actions = new String[5];
			definition.actions[0] = "Search";
		}
		if (definition.type == 5595) {
			definition.actions = new String[5];
			definition.actions[0] = "Search";
		}
		if (definition.type == 2725) {
			definition.actions = new String[5];
			definition.actions[0] = "Search";
		}
		if (definition.type == 1106) {
			definition.actions = new String[5];
			definition.actions[0] = "Sit";
		}
		if (definition.type == 423) {
			definition.actions = new String[5];
			definition.actions[0] = "Search";
		}
		if (definition.type == 57258) {
			definition.actions = new String[5];
			definition.actions[0] = "Climb";
		}
		if (definition.type == 1739) {
			definition.actions = new String[5];
			definition.actions[0] = "Climb-up";
			definition.actions[1] = "Climb-down";
			//definition.actions[4] = "Climb-down";
		}
		if (definition.type == 11698) {
			definition.name = null;
			definition.interactive = false;
			definition.actions = null;
			definition.modifiedModelColors = new int[]{6817, 6697, 6693, 7580};
			definition.originalModelColors = new int[]{21543, 21547, 45, 7341};
			definition.modelIds = new int[]{5013};
		}
		if (definition.type == 15640) { //balloons
			definition.name = null;
			definition.interactive = false;
			definition.actions = null;
			definition.modelIds = new int[]{2227};
			definition.animation = 498;
		}
		if (definition.type == 452) {
			definition.name = "Depleted Rock";
			definition.modelIds = new int[]{1391};
			definition.modelSizeX = 80;
			definition.modelSizeH = 80;
			definition.modelSizeY = 80;
			definition.interactive = false;
		}
		if (definition.type == 305) {
			definition.actions = new String[5];
			definition.name = "Rejunvination Altar";
			definition.actions[0] = "Restore";
			definition.modelSizeX = 50;
			definition.modelSizeH = 100;
			definition.modelSizeY = 50;
			definition.modelIds = new int[]{65206};//same
		}



			if (definition.type == 8770) {
			definition.actions = new String[5];
			definition.name = "Deck chair";
			//  definition.actions[0] = "Restore";
			// definition.modelSizeX = 100;
			//  definition.modelSizeH = 100;
			//  definition.modelSizeY = 100;
			definition.modelIds = new int[]{65311};//same
		}
		if (definition.type == 11699) {
			definition.name = null;
			definition.interactive = false;
			definition.actions = null;
			definition.modifiedModelColors = new int[]{74, 43117};
			definition.originalModelColors = new int[]{21543, 21547};
			definition.modelIds = new int[]{1424};
		}
		if (definition.type == 1187) {
			definition.modelIds = new int[] {1610};
			definition.castsShadow = false;
		}
		if (definition.type == 5259) {
			definition.actions = new String[5];
			definition.solid = false;
			definition.actions = new String[]{"@red@Enter", null, null, null, null};
			definition.name = "@yel@Boss Portal";

		}
		if (definition.type == 1011) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "Book Shelf";
		}
		if (definition.type == 594) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "Table";
		}
		if (definition.type == 6822) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "@cya@Sarcophagus";
			definition.interactive = true;
		}
		if (definition.type == 597) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "@cya@Table";
		}

		if (definition.type == 708) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "@cya@Stolen Chest";
			definition.modelSizeX = 250;
			definition.modelSizeH = 250;
			definition.modelSizeY = 250;
			definition.modelIds = new int[]{101667};//same

		}
		if (definition.type == 710) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "Common Chest";
			definition.modelSizeX = 250;
			definition.modelSizeH = 250;
			definition.modelSizeY = 250;
			definition.modelIds = new int[]{102015};//same

		}
		if (definition.type == 434) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "@gre@Rare Chest";
			definition.modelSizeX = 250;
			definition.modelSizeH = 250;
			definition.modelSizeY = 250;
			definition.modelIds = new int[]{101993};//same

		}
		if (definition.type == 10805 || definition.type == 10806 || definition.type == 10807) {
			definition.name = "Grand Exchange clerk";
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Use";
		}
		if (definition.type == 10805 || definition.type == 10806 || definition.type == 10807) {
			definition.name = "Grand Exchange clerk";
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Use";
		}
		if (definition.type == 10091) {
			definition.actions = new String[]{"Bait", null, null, null, null};
			definition.name = "@yel@Rocktail fishing spot";
		}
		if (definition.type == 7836 || definition.type == 7808) {
			definition.interactive = true;
			definition.actions = new String[]{"Dump-weeds", null, null, null, null};
			definition.name = "Compost bin";
		}
		if (definition.type == 26945) {
			definition.actions = new String[]{"Investigate", null, null, null, null};
			definition.name = "Well of Goods & boss";
			definition.modelIds = new int[]{132210};//same
			//definition.modelSizeX = -50;
			//definition.modelSizeY = -50;
			definition.width = 5;
			definition.length = 5;

		}

		if (definition.type == 1864) {
			definition.actions = new String[]{"Enter", null, null, null, null};
			definition.name = "Raid Lobby";

		}
		if (definition.type == 25014 || definition.type == 25026 || definition.type == 25020 || definition.type == 25019 || definition.type == 25024 || definition.type == 25025 || definition.type == 25016 || definition.type == 5167 || definition.type == 5168) {
			definition.actions = new String[5];
		}
/*		if (definition.type == 400) {
			definition.name = "<col=E51A4D>Beta Zone";
			definition.actions = new String[]{"@yel@Travel", null, null, null, null};
			definition.modelIds = new int[]{100630};//same
			definition.modelSizeX = 250;
			definition.modelSizeH = 250;
			definition.modelSizeY = 250;
		}*/
		if (definition.type == 589) {
			definition.name = "Oracles lense";
			definition.actions = new String[]{null, null, null, null, null};

		}


		if (definition.type == 13592) {
		    definition.actions = new String[]{null, null, null, null, null};

	    }
		if (definition.type == 13593) {
			definition.actions = new String[]{null, null, null, null, null};

		}
		if (definition.type == 401) {
			definition.name = "@cya@Umbrella";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1366};//same100634
			definition.modelSizeX = 330;
			definition.modelSizeH = 330;
			definition.modelSizeY = 330;

		}

		if (definition.type == 402) {
			definition.name = "@red@Umbrella";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1363};//same100634
			definition.modelSizeX = 330;
			definition.modelSizeH = 330;
			definition.modelSizeY = 330;

		}
		if (definition.type == 403) {
			definition.name = "@cya@Beach Ball";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1363};//same
			definition.castsShadow = false;

			//definition.modelSizeX = 250;
			//definition.modelSizeH = 250;
			//definition.modelSizeY = 250;
		}
		if (definition.type == 404) {
			definition.name = "@cya@Chair";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1368};//same
			definition.modelSizeX = 250;
			definition.modelSizeH = 250;
			definition.modelSizeY = 250;
			definition.castsShadow = false;

		}
		if (definition.type == 2991) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1664};
		}
		if (definition.type == 2992) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1587};
		}
		if (definition.type == 2994) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1708};
		}
		if (definition.type == 58) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1697};
		}
		if (definition.type == 2013) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1561};
		}
		if (definition.type == 2990) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1631};
		}
		if (definition.type == 4827) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{1627};
			definition.castsShadow = false;
		}
		if (definition.type == 4752) {
			definition.name = "";
			definition.actions = new String[]{null, null, null, null, null};
			definition.modelIds = new int[]{37330};
		}

		if (definition.type == 1948) {
			definition.name = "Wall";
		}
		if (definition.type == 25029) {
			definition.actions = new String[5];
			definition.actions[0] = "Go-through";
		}
		if (definition.type == 19187 || definition.type == 19175) {
			definition.actions = new String[5];
			definition.actions[0] = "Dismantle";
		}
		if (definition.type == 6434) {
			definition.actions = new String[5];
			definition.actions[0] = "Enter";
		}
		if (definition.type == 2182) {
			definition.actions = new String[5];
			definition.actions[0] = "Search";
			definition.name = "Chest";
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.modelSizeX = 200;
		}
		if(definition.type == 24557) {
			definition.actions = new String[5];
			definition.actions[0] = null;
			definition.interactive = false;
			definition.isSolidObject = true;
			definition.length = 1;
			definition.width = 1;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.modelSizeX = 200;
			definition.modelIds = new int[]{50139};
			definition.name = "Narnia Raids";
		}

		if (definition.type == 10177) {
			definition.actions = new String[5];
			definition.actions[0] = "Climb-down";
			definition.actions[1] = "Climb-up";
		}
		if (definition.type == 39515) {
			definition.name = "Damage Zones";
		}
		if (definition.type == 31169) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 31167) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 31168) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 1289) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 1289) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 1306) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 1276) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 1307) {
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 2026) {
			definition.actions = new String[5];
			definition.actions[0] = "Fish";
		}
		if (definition.type == 2029) {
			definition.actions = new String[5];
			definition.actions[0] = "Lure";
			definition.actions[1] = "Bait";
		}
		if (definition.type == 2030) {
			definition.actions = new String[5];
			definition.actions[0] = "Cage";
			definition.actions[1] = "Harpoon";
		}

		if (definition.type == 11356) {
			definition.name = "Dark Beast Portal";
		}
		if (definition.type == 47120) {
			definition.name = "Altar";
			definition.actions = new String[5];
			definition.actions[0] = "Craft-rune";
		}
		if (definition.type == 11325 || definition.type == 11328 || definition.type == 37943 || definition.type == 37940 || definition.type == 11325) {
			definition.interactive = false;
		}
		if (definition.type == 22772) {
			definition.actions = new String[5];
			definition.actions[0] = "Steal-from";
			definition.name = "AFK (T2)";
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.modelSizeX = 200;
		}
		if (definition.type == 22774) {
			definition.actions = new String[5];
			definition.actions[0] = "Steal-from";
			definition.name = "AFK (T3)";
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.modelSizeX = 200;
		}
		if (definition.type == 47180) {
			definition.name = "Extra Zones";
			definition.actions = new String[5];
			definition.actions[0] = "Tele";
			definition.modelIds = new int[]{504};

		}
		if (definition.type == 8702) {
			definition.name = "Rocktail Barrel";
			definition.actions = new String[5];
			definition.actions[0] = "Fish-from";
		}
		if (definition.type == 2782 ||
				definition.type == 2783) {
			definition.interactive = true;
			definition.name = "Anvil";
			definition.actions = new String[5];
			definition.actions[0] = "Smith-on";
		}
		if (definition.type == 172) {
			definition.modelIds = new int[]{14941};
			definition.modelSizeX = 150;
			definition.modelSizeH = 150;
			definition.modelSizeY = 150;
			definition.name = "Upgrader";
			definition.actions = new String[]{"Upgrade", null, null, null, null};
		}
		if (definition.type == 6714) {
			definition.interactive = true;
			definition.name = "Door";
			definition.actions[0] = "Open";
		}
		if (definition.type == 1749) {
			definition.modelIds = new int[]{15707};
			definition.interactive = true;
			definition.name = "Starter Chest";
			definition.actions[0] = "Open";
		}

		if (definition.type == 8550 || definition.type == 8150 || definition.type == 8551 || definition.type == 7847 || definition.type == 8550) {
			definition.actions = new String[]{null, "Inspect", null, "Guide", null};

			definition.interactive = true;

		}
		if (definition.type == 42151 || definition.type == 42160) {
			definition.name = "Rocks";
			definition.interactive = true;
			definition.mapSceneID = 11;
		}
		if (definition.type == 42158 || definition.type == 42157) {
			definition.name = "Rocks";
			definition.interactive = true;
			definition.mapSceneID = 12;
		}
		if (definition.type == 42123 || definition.type == 42124 || definition.type == 42119 || definition.type == 42120 || definition.type == 42118 || definition.type == 42122) {
			definition.name = "Tree";
			definition.interactive = true;
			definition.actions = new String[]{"Cut", null, null, null, null};
			definition.mapSceneID = 0;
		}
		if (definition.type == 42127 || definition.type == 42131 || definition.type == 42133 || definition.type == 42129 || definition.type == 42134) {
			definition.name = "Tree";
			definition.interactive = true;
			definition.actions = new String[]{"Cut", null, null, null, null};
			definition.mapSceneID = 6;
		}
		if (definition.type == 42192) {
			definition.name = "<col=AF70C3>Bank chest";
			definition.interactive = true;
			definition.actions = new String[]{"Open", null, null, null, null};
			definition.mapSceneID = 0;
		}


		if (definition.type == 42082 || definition.type == 42083) {
			definition.mapSceneID = 0;
		}
		if (definition.type >= 42087 && definition.type <= 42117) {
			definition.mapSceneID = 4;
		}
		if (definition.type > 30000 && definition.name != null && definition.name.toLowerCase().contains("gravestone")) {
			definition.mapSceneID = 34;
		}
		if (definition.type == 36676) {
			definition.modelIds = new int[]{17374, 17383};
			//    definition.objectModelTypes = null;
		}

		if (definition.type == 2067) {
			definition.actions = new String[]{null, null, null, null, null};
			//    definition.objectModelTypes = null;
		}

		if (definition.type == 16189) {
			definition.actions = new String[]{null, null, null, null, null};
			//    definition.objectModelTypes = null;
		}
		if (definition.type == 2067) {
			definition.actions = new String[]{null, null, null, null, null};
			//    definition.objectModelTypes = null;
		}

		if (definition.type == 29943) {
			definition.actions = new String[]{"Open", "Open 100", null, null, null};
			definition.name = "<col=923FFF>Magic Chest";
			definition.modelIds = new int[]{130020};
			definition.modelSizeX = 150;
			definition.modelSizeY = 150;
			definition.modelSizeH = 150;
			definition.description = "Opens every Key in Athens".getBytes();

		}
		if (definition.type == 1145) {
			definition.actions = new String[]{"Open", "Open 100", null, null, null};
			definition.name = "@red@Raid Chest";
			definition.modelIds = new int[]{131959};
			definition.modelSizeX = 150;
			definition.modelSizeY = 150;
			definition.modelSizeH = 150;
			definition.description = "Corrupt Raid's Chest".getBytes();
			definition.interactive = true;
		}
		if (definition.type == 13621) {
			definition.actions = new String[]{"Start", "Quick", null, null, null};
			definition.name = "@red@Raid Portal";
			definition.description = "Corrupt Raids Portal".getBytes();
			definition.interactive = true;
		}
		if (definition.type == 8876) {
			definition.isSolidObject = false;
			definition.impenetrable = false;
		}
		if (definition.type == 980) {
			definition.isSolidObject = false;
			definition.impenetrable = false;
		}
		if (definition.type == 11851) {
			definition.isSolidObject = false;
			definition.impenetrable = false;
		}
		if (definition.type == 13637) {
			definition.actions = new String[]{"Start", "Quick", null, null, null};
			definition.name = "@red@Spectral Raids Portal";
			definition.description = "Spectral Raids Portal".getBytes();
			definition.modelSizeX = 175;
			definition.modelSizeY = 175;
			definition.modelSizeH = 175;
			definition.animation = 10099;
			definition.modelIds = new int[]{41319};
			definition.interactive = true;
		}
		if (definition.type == 16814) {
			definition.actions = new String[]{"Open", "Open 100", null, null, null};
			definition.name = "<col=AF70C3>Spectral Raid Chest";
			definition.modelSizeX = 190;
			definition.modelSizeY = 190;
			definition.modelSizeH = 190;
			definition.description = "<col=AF70C3>Spectral Raids Chest".getBytes();
			definition.interactive = true;
		}

		if (definition.type == 18835) {
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.name = "Armor Crate";
			definition.interactive = true;
		}
		if (definition.type == 60) { // ICE CAVE ENTRANCE
			definition.actions = new String[]{"@whi@Climb down", null, null, null, null};
			definition.interactive = true;
		}

		if (definition.type == 195) { // ICE CAVE ENTRANCE
			definition.actions = new String[]{"@whi@Climb Up", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 6440) { // ICE CAVE ENTRANCE
			definition.name = "@cya@Frozen Cavern";
			definition.actions = new String[]{"@whi@Enter", "@whi@Enter", "@whi@Enter", null, null};
			definition.interactive = true;

		}

		//NEW SKILLING TREES
		if (definition.type == 709) { // Depleted Tree
			definition.modelIds = new int[]{131160};
			definition.name = null;
			definition.modelSizeX = 200;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = false;
		}

		if (definition.type == 711) { // Vorpal
			definition.name = "<col=B37E83>Vorpal Tree";
			definition.description = "Vorpal Tree".getBytes();

			definition.modelIds = new int[]{131503};
			definition.modelSizeX = 200;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.actions = new String[]{"Chop", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 712) { // Bloodstained
			definition.name = "<col=B73C36>Bloodstained Tree";
			definition.description = "Bloodstained Tree".getBytes();
			definition.modelIds = new int[]{131501};
			definition.modelSizeX = 200;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.actions = new String[]{"Chop", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 713) { //Symbiotic
			definition.name = "<col=3F8C79>Symbiotic Tree";
			definition.description = "Symbiotic Tree".getBytes();

			definition.modelIds = new int[]{131502};
			definition.modelSizeX = 200;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.actions = new String[]{"Chop", null, null, null, null};
			definition.interactive = true;
		}
		if (definition.type == 694) {//Nether
			definition.name = "<col=7C44B7>Nether Tree";
			definition.description = "Nether Tree".getBytes();

			definition.modelIds = new int[]{131500};
			definition.modelSizeX = 200;
			definition.modelSizeH = 200;
			definition.modelSizeY = 200;
			definition.actions = new String[]{"Chop", null, null, null, null};
			definition.interactive = true;
		}

		//NEW SKILLING ROCKS
		if (definition.type == 7644) {// Depleted
			definition.name = null;
			definition.modelIds = new int[]{131161};
			definition.modelSizeX = 70;
			definition.modelSizeH = 70;
			definition.modelSizeY = 70;
			definition.actions = new String[]{null, null, null, null, null};
			definition.interactive = false;
		}


		if (definition.type == 3042) { // Vorpal
			definition.name = "<col=B37E83>Vorpal Rock";
			definition.modelIds = new int[]{131505};
			definition.modelSizeX = 70;
			definition.modelSizeH = 70;
			definition.modelSizeY = 70;
            definition.mapSceneID = 12;
			definition.interactive = true;
			definition.actions = new String[]{"Mine", null, null, null, null};

		}
		if (definition.type == 2102) { // Bloodstained
			definition.name = "<col=B73C36>Bloodstained Rock";
			definition.modelIds = new int[]{131507};
			definition.modelSizeX = 70;
			definition.modelSizeH = 70;
			definition.modelSizeY = 70;
			definition.width = 1;
			definition.length = 1;
			definition.interactive = true;
			definition.actions = new String[]{"Mine", null, null, null, null};
		}
		if (definition.type == 2100) { // Symbiotic
			definition.name = "<col=3F8C79>Symbiotic Rock";
			definition.modelIds = new int[]{131506};
			definition.modelSizeX = 70;
			definition.modelSizeH = 70;
			definition.modelSizeY = 70;
			definition.interactive = true;
			definition.actions = new String[]{"Mine", null, null, null, null};
		}
		if (definition.type == 2104) {
			definition.name = "<col=7C44B7>Nether Rock";
			definition.modelIds = new int[]{131504};
			definition.modelSizeX = 70;
			definition.modelSizeH = 70;
			definition.modelSizeY = 70;
			definition.width = 1;
			definition.interactive = true;
			definition.actions = new String[]{"Mine", null, null, null, null};
		}



		if (definition.type == 7591) { // Droprate
			definition.name = "@gre@Droprate Rock";
			definition.description = "Droprate Rock".getBytes();
			definition.modelIds = new int[]{15710};
			definition.modelSizeX = 80;
			definition.modelSizeH = 80;
			definition.modelSizeY = 80;
			definition.width = 1;
			definition.length = 1;
			definition.actions = new String[]{"Mine", null, null, null, null};

		}


		if (definition.type == 38661) { // Critical
			definition.name = "@cya@Critical Rock";
			definition.description = "Critical Rock".getBytes();
			definition.modelIds = new int[]{15709};
			definition.modelSizeX = 80;
			definition.modelSizeH = 80;
			definition.modelSizeY = 80;
			definition.width = 1;
			definition.length = 1;
			definition.actions = new String[]{"Mine", null, null, null, null};
		}

		if (definition.type == 7878) {
			definition.name = "Diseased Flowers";
			definition.actions = new String[]{"Pick", null, null, null, null};
		}

		if (definition.type == 38662) { // Damage
			definition.name = "@red@Damage Rock";
			definition.description = "Damage Rock".getBytes();
			definition.modelIds = new int[]{15716};
			definition.modelSizeX = 80;
			definition.modelSizeH = 80;
			definition.modelSizeY = 80;
			definition.width = 1;
			definition.length = 1;
			definition.actions = new String[]{"Mine", null, null, null, null};
		}

        if (definition.type == 50193) {
            definition.name = "Ancient Forge";
			definition.isSolidObject = true;
			definition.impenetrable = true;
            definition.actions = new String[]{"Smelt", null, null, null, null};
        }



		if
		    (definition.type == 416) { // GREEN
			definition.actions = new String[5];
			definition.name = "@cya@Loot Item";
			definition.actions[0] = "@whi@Open the loot";
			definition.modelIds = new int[]{101255};
			definition.modelSizeX = 725;
			definition.modelSizeY = 725;
			definition.modelSizeH = 725;
		}

		if (definition.type == 417) { // GREEN
			// definition.actions = new String[5];
			definition.name = "@cya@Loot Item";
			definition.actions[0] = "@whi@Open the loot";
			definition.modelIds = new int[]{101255};
			definition.modelSizeX = 725;
			definition.modelSizeY = 725;
			definition.modelSizeH = 725;
		}

		/////////////////////CUSTOM ORES

		if (definition.type == 34255) {
			definition.configID = 8002;
			definition.morphisms = new int[]{15385};
		}

		if (definition.type == 13830) {
			//definition.modelIds = new int[] {12199};
			definition.configID = 8003;
			definition.morphisms = new int[]{13217, 13218, 13219, 13220, 13221, 13222, 13223};
		}


		if (definition.type == 7602) {
			definition.name = "@yel@Mysterious Plant";
			definition.actions = new String[]{"Search", null, null, null, null};
			definition.description = "A Mysterious Plant".getBytes();

		}
		if (definition.type == 7613) {
			definition.name = null;
			definition.actions = new String[]{null, null, null, null, null};
			definition.description = null;

		}
		if (definition.type == 42646) {
			definition.name = "@yel@Mysterious Boxes";
			definition.actions[0] = "Search";
		}
		if (definition.type == 480) {
			definition.modelIds = new int[]{101343};
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Open";
			definition.modelSizeX = 50;
			definition.modelSizeH = 50;
			definition.modelSizeY = 50;
			definition.name = "@yel@Forge";

		}

		if (definition.type == 10284) {
			definition.modelIds = new int[]{101794};
			definition.name = "@yel@Frenzy Chest";
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Open";
		}
		if (definition.type == 6774) {
			definition.modelIds = new int[]{131650};
			definition.name = "@yel@Frenzy Chest";
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Open";
		}
		if (definition.type == 22721) {
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Smelt";
		}
		if (definition.type == 16473) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 24875) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 2305) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 2725) {
			definition.interactive = true;
			definition.actions = new String[]{"Light", null, null, null, null};
		}
		if (definition.type == 18038) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 18037) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 5278) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 1286) {
			definition.interactive = false;
			definition.actions = new String[]{null, null, null, null, null};
		}
		if (definition.type == 7837) {
			definition.interactive = true;
			definition.actions = new String[5];
		}
		if (definition.type == 26280) {
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Study";
		}

		if (definition.type == 5625) {
			definition.modelIds = new int[]{65531};
			definition.interactive = false;
			definition.name = null;
			definition.actions = null;
			definition.modelSizeX = 275;
			definition.modelSizeH = 275;
			definition.modelSizeY = 275;
		}
		if (definition.type == 36000) {
			definition.name = "Dungeon Entrance";
			definition.interactive = true;
			definition.actions = new String[5];
			definition.actions[0] = "Enter";
			definition.solid = false;
			definition.nonFlatShading = false;
			definition.castsShadow = false;
		}
		if (definition.type == 5625) {
			definition.modelIds = new int[]{65523};
			definition.interactive = false;
			definition.name = null;
			definition.actions = null;
			definition.modelSizeX = 265;
			definition.modelSizeH = 265;
			definition.modelSizeY = 265;
		}
		if (definition.type == 47762) {
			definition.modelIds = new int[]{65531};
			definition.interactive = false;
			definition.name = null;
			definition.actions = null;
			definition.modelSizeX = 275;
			definition.modelSizeH = 275;
			definition.modelSizeY = 275;
		}
/*		if (definition.type == 27306) {
			definition.modelIds = new int[]{65529};
			definition.interactive = false;
			definition.name = null;
			definition.actions = null;
			definition.modelSizeY = 150;
		}*/
		if (definition.type == 15314 || definition.type == 15313) {
			definition.configID = 8000;
			definition.morphisms = new int[]{definition.type, -1};
		}
		if (definition.type == 15306) {
			definition.configID = 8001;
			definition.morphisms = new int[]{definition.type, -1, 13015};
		}
		if (definition.type == 15305) {
			definition.configID = 8001;
			definition.morphisms = new int[]{definition.type, -1, 13016};
		}
		if (definition.type == 15317) {
			definition.configID = 8001;
			definition.morphisms = new int[]{definition.type, -1, 13096};
		}
		if (definition.type == 8550) {
			definition.morphisms = new int[]{8576, 8575, 8574, 8573, 8576, 8576, 8558, 8559, 8560, 8561, 8562, 8562, 8562, 8580, 8581, 8582, 8583, 8584, 8584, 8584, 8535, 8536, 8537, 8538, 8539, 8539, 8539, 8641, 8642, 8643, 8644, 8645, 8645, 8645, 8618, 8619, 8620, 8621, 8622, 8623, 8624, 8624, 8624, 8595, 8596, 8597, 8598, 8599, 8600, 8601, 8601, 8601, 8656, 8657, 8658, 8659, 8660, 8661, 8662, 8663, 8664, 8664, 8664, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8563, 8564, 8565, 8566, 8576, 8576, 8576, 8585, 8586, 8587, 8588, 8576, 8576, 8576, 8540, 8541, 8542, 8543, 8576, 8576, 8576, 8646, 8647, 8648, 8649, 8576, 8576, 8576, 8625, 8626, 8627, 8628, 8629, 8630, 8576, 8576, 8576, 8602, 8603, 8604, 8605, 8606, 8607, 8576, 8576, 8576, 8665, 8666, 8667, 8668, 8669, 8670, 8671, 8672, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8567, 8568, 8569, 8576, 8576, 8576, 8576, 8589, 8590, 8591, 8576, 8576, 8576, 8576, 8544, 8545, 8546, 8576, 8576, 8576, 8576, 8650, 8651, 8652, 8576, 8576, 8576, 8576, 8631, 8632, 8633, 8634, 8635, 8576, 8576, 8576, 8576, 8608, 8609, 8610, 8611, 8612, 8576, 8576, 8576, 8576, 8673, 8674, 8675, 8676, 8677, 8678, 8679, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8570, 8571, 8572, 8576, 8576, 8576, 8576, 8592, 8593, 8594, 8576, 8576, 8576, 8576, 8547, 8548, 8549, 8576, 8576, 8576, 8576, 8653, 8654, 8655, 8576, 8576, 8576, 8576, 8636, 8637, 8638, 8639, 8640, 8576, 8576, 8576, 8576, 8613, 8614, 8615, 8616, 8617, 8576, 8576, 8576, 8576, 8680, 8681, 8682, 8683, 8684, 8685, 8686, 8576, 8576, 8576, 8576};
		}
		if (definition.type == 8551) {
			definition.morphisms = new int[]{8576, 8575, 8574, 8573, 8576, 8576, 8558, 8559, 8560, 8561, 8562, 8562, 8562, 8580, 8581, 8582, 8583, 8584, 8584, 8584, 8535, 8536, 8537, 8538, 8539, 8539, 8539, 8641, 8642, 8643, 8644, 8645, 8645, 8645, 8618, 8619, 8620, 8621, 8622, 8623, 8624, 8624, 8624, 8595, 8596, 8597, 8598, 8599, 8600, 8601, 8601, 8601, 8656, 8657, 8658, 8659, 8660, 8661, 8662, 8663, 8664, 8664, 8664, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8563, 8564, 8565, 8566, 8576, 8576, 8576, 8585, 8586, 8587, 8588, 8576, 8576, 8576, 8540, 8541, 8542, 8543, 8576, 8576, 8576, 8646, 8647, 8648, 8649, 8576, 8576, 8576, 8625, 8626, 8627, 8628, 8629, 8630, 8576, 8576, 8576, 8602, 8603, 8604, 8605, 8606, 8607, 8576, 8576, 8576, 8665, 8666, 8667, 8668, 8669, 8670, 8671, 8672, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8567, 8568, 8569, 8576, 8576, 8576, 8576, 8589, 8590, 8591, 8576, 8576, 8576, 8576, 8544, 8545, 8546, 8576, 8576, 8576, 8576, 8650, 8651, 8652, 8576, 8576, 8576, 8576, 8631, 8632, 8633, 8634, 8635, 8576, 8576, 8576, 8576, 8608, 8609, 8610, 8611, 8612, 8576, 8576, 8576, 8576, 8673, 8674, 8675, 8676, 8677, 8678, 8679, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8576, 8570, 8571, 8572, 8576, 8576, 8576, 8576, 8592, 8593, 8594, 8576, 8576, 8576, 8576, 8547, 8548, 8549, 8576, 8576, 8576, 8576, 8653, 8654, 8655, 8576, 8576, 8576, 8576, 8636, 8637, 8638, 8639, 8640, 8576, 8576, 8576, 8576, 8613, 8614, 8615, 8616, 8617, 8576, 8576, 8576, 8576, 8680, 8681, 8682, 8683, 8684, 8685, 8686, 8576, 8576, 8576, 8576};
		}
		if (definition.type == 7847) {
			definition.morphisms = new int[]{7843, 7842, 7841, 7840, 7843, 7843, 7843, 7843, 7867, 7868, 7869, 7870, 7871, 7899, 7900, 7901, 7902, 7903, 7883, 7884, 7885, 7886, 7887, 7919, 7920, 7921, 7922, 7923, 7851, 7852, 7853, 7854, 7855, 7918, 7917, 7916, 7915, 41538, 41539, 41540, 41541, 41542, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7872, 7873, 7874, 7875, 7843, 7904, 7905, 7906, 7907, 7843, 7888, 7889, 7890, 7891, 7843, 7924, 7925, 7926, 7927, 7843, 7856, 7857, 7858, 7859, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7876, 7877, 7878, 7843, 7843, 7908, 7909, 7910, 7843, 7843, 7892, 7893, 7894, 7843, 7843, 7928, 7929, 7930, 7843, 7843, 7860, 7861, 7862, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7879, 7880, 7881, 7882, 7843, 7911, 7912, 7913, 7914, 7843, 7895, 7896, 7897, 7898, 7843, 7931, 7932, 7933, 7934, 7843, 7863, 7864, 7865, 7866, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843, 7843};
		}
		if (definition.type == 8150) {
			definition.morphisms = new int[]{8135, 8134, 8133, 8132, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 21101, 21127, 21159, 21178, 21185, 21185, 21185, 17776, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 17777, 17778, 17780, 17781, 17781, 17781, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8144, 8145, 8146, 8147, 8148, 8149, 8144, 8145, 8146, 8144, 8145, 8146, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 9044, 9045, 9046, 9047, 9048, 9048, 9049, 9050, 9051, 9052, 9053, 9054, 8139, 8140, 8141, 8142, 8143, 8143, 8143, 8144, 8145, 8146, 8135, 8135, 8135, 8135, 8135, 8135, -1, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135, 8135};
		}

		switch (definition.type) {
			case 28295:
			case 5816:
			case 5817:
			case 5818:
			case 28297:
				definition.name = "";
				definition.actions = null;
				break;
			case 2470:
				definition.name = "<col=6C1894>Starter Zone";
				definition.actions[0] = "Travel";
				break;


			case 6725:
			case 6714:
			case 6734:
			case 6730:
			case 6749:
			case 6742:
			case 6723:
			case 6747:
			case 6744:
			case 6741:
			case 6779:
			case 6743:
			case 6719:
			case 6717:
			case 6731:
			case 6745:
			case 6718:
			case 6780:
			case 6746:
			case 6750:
			case 6722:
			case 6715:
				definition.name = "Door";
				definition.interactive = true;
				break;
			case 5917:
				definition.actions = new String[5];
				definition.name = "Plasma Vent"; //friday the 13th
				definition.actions[1] = "Search";
				break;
			case 4875:
				definition.name = "Banana Stall";
				break;
			case 4874:
				definition.name = "Ring Stall";
				break;
			case 13493:
				definition.actions = new String[5];
				definition.actions[0] = "Steal from";
				break;
			case 2152:
				definition.actions = new String[5];
				definition.actions[0] = "Infuse Pouches";
				definition.actions[1] = "Renew Points";
				definition.name = "Summoning Obelisk";
				break;
			case 4306:
				definition.actions = new String[5];
				definition.actions[0] = "Use";
				break;
			case 2732:
			case 11404:
			case 11406:
			case 11405:
			case 20000:
				definition.actions = new String[5];
				definition.actions[0] = "Add logs";
				break;
			case 2:
				definition.name = "Entrance";
				break;
			case 38700:
				definition.name = "@gre@Gaia Chamber";
				definition.actions = new String[]{"Enter", null, null, null, null};
				definition.interactive = true;
				break;
			case 4408:
			definition.name = "@gre@Extra Npcs";
			definition.actions[0] = "@gre@Teleport";
			definition.interactive = true;
			break;

		}
		if (id == 5222) {
			definition.copy(5259);
			definition.name = "Barrier";
			definition.actions[0] = "Pass through";
			definition.solid = true;
			definition.interactive = true;
			definition.isSolidObject = true;
			definition.impenetrable = true;
		}
		if (id == 10014) {
			definition.actions[0] = "Touch";
			definition.interactive = true;
		}

		map.put(id, definition);
		return definition;
	}

	public static void nullify() {
		baseModels = null;
		mruNodes2 = null;
		streamIndices = null;
		map.clear();
		map = null;
		stream = null;
        stream667 = null;
        streamOSRS = null;
	}

	public static void unpackConfig(Archive streamLoader) {

		stream = new Stream(streamLoader.getDataForName("loc.dat"));
		Stream stream = new Stream(streamLoader.getDataForName("loc.idx"));
		stream667 = new Stream(streamLoader.getDataForName("667loc.dat"));
		Stream streamIdx667 = new Stream(streamLoader.getDataForName("667loc.idx"));
        //streamOSRS = new Stream(streamLoader.getDataForName("217loc.dat"));
       // Stream streamOSRS = new Stream(streamLoader.getDataForName("217loc.idx"));

		int totalObjects = stream.getUnsignedShort();
		int totalObjects667 = streamIdx667.getUnsignedShort();
       // int totalObjectsOSRS = streamOSRS.getUnsignedShort();

		streamIndices = new int[totalObjects];
		streamIndices667 = new int[totalObjects667];
        //streamIndicesOSRS = new int[totalObjectsOSRS];
       // System.out.println("Objects Length: " + totalObjects);
        //System.out.println("Object Length 667: " + totalObjects667);
        //System.out.println("Object Length OSRS: " + totalObjectsOSRS);

		int i = 2;
		for (int j = 0; j < totalObjects; j++) {
			streamIndices[j] = i;
			i += stream.getUnsignedShort();
		}
		i = 2;
		for (int j = 0; j < totalObjects667; j++) {
			streamIndices667[j] = i;
			i += streamIdx667.getUnsignedShort();
		}
/*        i = 2;
        for (int j = 0; j < totalObjectsOSRS; j++) {
            streamIndicesOSRS[j] = i;
            i += streamOSRS.getUnsignedShort();
        }*/
	}

	public static void loadEvilTree(ObjectDef definition) {
		switch (definition.type) {

			case 11391:
				definition.modelIds = new int[]{45733, 45735};
				definition.models = null;
				definition.name = "Seedling";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1694;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Nurture",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11392:
				definition.modelIds = new int[]{45733, 45731, 45735};
				definition.models = null;
				definition.name = "Sapling";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1695;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Nurture",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11394:
				definition.modelIds = new int[]{45736, 45739, 45735};
				definition.models = null;
				definition.name = "Young tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1697;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Nurture",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11395:

				definition.models = null;
				definition.name = "Young tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1698;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Nurture",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 12713:
				definition.modelIds = new int[]{45759};
				definition.models = null;
				definition.name = "Fallen tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = -1;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = null;
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 12714:
				definition.modelIds = new int[]{45754};
				definition.models = null;
				definition.name = "Fallen tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = -1;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = null;
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 12715:
				definition.modelIds = new int[]{45752};
				definition.models = null;
				definition.name = "Fallen tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = -1;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = null;
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11922:
				definition.modelIds = new int[]{45748};
				definition.models = null;
				definition.name = "Elder evil tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1134;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11919:
				definition.modelIds = new int[]{45750};
				definition.models = null;
				definition.name = "Evil magic tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1679;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11916:
				definition.modelIds = new int[]{45757};
				definition.models = null;
				definition.name = "Evil yew tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1685;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11444:
				definition.modelIds = new int[]{45745};
				definition.models = null;
				definition.name = "Evil maple tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1682;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11441:
				definition.modelIds = new int[]{45762};
				definition.models = null;
				definition.name = "Evil willow tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1688;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11437:
				definition.modelIds = new int[]{45765};
				definition.models = null;
				definition.name = "Evil oak tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1691;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11435:
				definition.modelIds = new int[]{45769};
				definition.models = null;
				definition.name = "Evil tree";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 3;
				definition.length = 3;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 1676;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11433:
				definition.modelIds = new int[]{45743};
				definition.models = null;
				definition.name = "Evil root";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 353;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11432:
				definition.modelIds = new int[]{45743};
				definition.models = null;
				definition.name = "Evil root";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11431:
				definition.modelIds = new int[]{45743};
				definition.models = null;
				definition.name = "Evil root";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 353;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11430:
				definition.modelIds = new int[]{45743};
				definition.models = null;
				definition.name = "Evil root";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11429:
				definition.modelIds = new int[]{45743};
				definition.models = null;
				definition.name = "Evil root";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 353;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;
			case 11428:
				definition.modelIds = new int[]{45743};
				definition.models = null;
				definition.name = "Evil root";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{"Chop",};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				break;

			case 650:
				definition.modelIds = new int[]{24003};
				definition.models = null;
				definition.name = "Easter Egg";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{null,};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 256;
				definition.modelSizeH = 256;
				definition.modelSizeY = 256;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				definition.description = "It's the Easter bunny's eggs!".getBytes();
				break;
			case 651:
				definition.modelIds = new int[]{38053};
				/*definition.modelIds = new int[]{14414};
				definition.models = null;
				definition.name = "Easter Egg";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{null,};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 228;
				definition.modelSizeH = 228;
				definition.modelSizeY = 228;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				definition.description = "It's the Easter bunny's eggs!".getBytes();*/
				break;
			case 652:
				definition.modelIds = new int[]{24004};
				definition.models = null;
				definition.name = "Easter Egg";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{null,};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 228;
				definition.modelSizeH = 228;
				definition.modelSizeY = 228;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				definition.description = "It's the Easter bunny's eggs!".getBytes();
				break;

			case 653:
				definition.modelIds = new int[]{24002};
				definition.models = null;
				definition.name = "Easter Egg";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{null,};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 228;
				definition.modelSizeH = 228;
				definition.modelSizeY = 228;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				definition.description = "It's the Easter bunny's eggs!".getBytes();
				break;
			case 26289://SoulBane
				definition.actions = new String[]{"Sacrifice", null, null, null, null};
				definition.name = "SoulBane Altar";
				definition.description = "Use a Totem on the altar to Sacrifice it".getBytes();
				break;
			case 13412: //Black Tree
				definition.modelIds = new int[]{12945};
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.castsShadow = false;
				break;
			case 13413: //Blue Tree
				definition.modelIds = new int[]{12930};
				definition.modelSizeX = 128;
				definition.modelSizeH = 128;
				definition.modelSizeY = 128;
				definition.castsShadow = false;
				break;
			case 1189:
				definition.castsShadow = false;
				definition.modelIds = new int[]{1699};
				break;
			case 1197:
				definition.castsShadow = false;
				definition.modelIds = new int[]{1658};
				break;
			case 2409:
				definition.castsShadow = false;
				definition.modelIds = new int[]{1637};
				break;
			case 9668:
				definition.castsShadow = false;
				definition.modelIds = new int[]{9543};
				break;
			case 7114:
				definition.castsShadow = false;
				definition.modelIds = new int[]{40641};
				break;
			case 42910://MAIN
				definition.actions = new String[]{"Travel", null, null, null, null};
				definition.modelIds = new int[]{46548};
				definition.name = "Athens Boat";
				definition.nonFlatShading = false;
				definition.castsShadow = false;
				definition.brightness = -60;
				break;
			case 42911://BACK
				definition.actions = new String[]{null, null, null, null, null};
				definition.modelIds = new int[]{46542};
				definition.name = "Athens Boat";
				definition.nonFlatShading = false;
				definition.castsShadow = false;
				definition.brightness = -60;
				break;
			case 42912://FRONT
				definition.actions = new String[]{null, null, null, null, null};
				definition.modelIds = new int[]{46551};
				definition.name = "Athens Boat";
				definition.nonFlatShading = false;
				definition.castsShadow = false;
				definition.brightness = -60;
				break;
			case 1303:
				definition.modelIds = new int[]{1701};
				definition.name = "Tree";
				definition.nonFlatShading = false;
				definition.castsShadow = false;
				break;
			case 39430:
				definition.modelIds = new int[]{16293,16294};
				//definition.name = "Vanguard Boat";
				//definition.interactive = true;
				definition.nonFlatShading = false;
				break;
			case 12477:
				definition.modelIds = new int[]{13831};
				definition.castsShadow = false;
				break;
			case 2980:
				definition.modelIds = new int[]{237};
				definition.castsShadow = false;
				break;
			case 27073:
				definition.modelIds = new int[]{29005};
				definition.castsShadow = false;
				break;
			case 25421:
				definition.modelIds = new int[]{13138};
				definition.castsShadow = false;
				break;
			case 1301:
				definition.modelIds = new int[]{1701};
				definition.castsShadow = false;
				break;
			case 3033:
				definition.modelIds = new int[]{1570};
				definition.actions = new String[]{null, null, null, null, null};
				definition.castsShadow = false;
				break;
			case 19033:
				definition.actions = new String[]{null, null, null, null, null};
				definition.castsShadow = false;
				break;
			case 1291:
				definition.actions = new String[]{null, null, null, null, null};
				definition.castsShadow = false;
				break;
			case 12350:
				definition.actions = new String[]{null, null, null, null, null};
				definition.castsShadow = false;
				break;
			case 28296:
				definition.actions = new String[]{null, null, null, null, null};
				definition.castsShadow = false;
				break;
			case 19035:
				definition.actions = new String[]{null, null, null, null, null};
				definition.castsShadow = false;
				break;
			case 18842:
				definition.modelIds = new int[]{19128};
				definition.castsShadow = false;
				break;
			case 4752:
				definition.modelIds = new int[]{37330};
				definition.castsShadow = false;
				break;
			case 1278:
				definition.modelIds = new int[]{1637};
				//definition.name = "Vanguard Boat";
				//definition.interactive = true;
				definition.nonFlatShading = false;
				definition.castsShadow = false;
				break;
			case 654:
				definition.modelIds = new int[]{24001};
				definition.models = null;
				definition.name = "Easter Egg";
				definition.modifiedModelColors = new int[]{0};
				definition.originalModelColors = new int[]{1};
				definition.width = 1;
				definition.length = 1;
				definition.solid = true;
				definition.impenetrable = true;
				definition.interactive = true;
				definition.adjustToTerrain = false;
				definition.nonFlatShading = false;
				definition.occludes = false;
				definition.animation = 354;
				definition.decorDisplacement = 16;
				definition.brightness = 15;
				definition.contrast = 0;
				definition.actions = new String[]{null,};
				definition.mapFunctionID = -1;
				definition.mapSceneID = -1;
				definition.aBoolean751 = false;
				definition.castsShadow = true;
				definition.modelSizeX = 228;
				definition.modelSizeH = 228;
				definition.modelSizeY = 228;
				definition.plane = 0;
				definition.offsetX = 0;
				definition.offsetH = 0;
				definition.offsetY = 0;
				definition.obstructsGround = false;
				definition.isSolidObject = false;
				definition.supportItems = 1;
				definition.varbitIndex = -1;
				definition.configID = -1;
				definition.morphisms = null;
				definition.description = "It's the Easter bunny's eggs!".getBytes();
				break;
            /*
            case 9263:
                definition.modelSizeX = 228;
                definition.modelSizeH = 512;
                definition.modelSizeY = 228;
                break;
*/
		}

	}

	public static void dumpObjectModels() {
		dumpObjectModels(streamIndices);
		dumpObjectModels(streamIndices667);
	}

	public static void dumpObjectModels(int[] indices) {
		int dumped = 0, exceptions = 0;
		for (int i = 0; i < indices.length - 1; i++) {
			ObjectDef object = forID(i);
			if (object == null) {
				continue;
			}
			if (object.modelIds == null) {
				continue;
			}
			for (int model : object.modelIds) {
				try {
					byte[] abyte = clientInstance.decompressors[1].read(model);
					File modelFile = new File(Signlink.getCacheDirectory().toString() + "/objectModels/" + model + ".gz");
					FileOutputStream fos = new FileOutputStream(modelFile);
					fos.write(abyte);
					fos.close();
					dumped++;
				} catch (Exception e) {
					exceptions++;
				}
			}
		}
		System.out.println("Dumped " + dumped + " object models with " + exceptions + " exceptions.");
	}

	public static void printObjectDefinition(int id) {
		ObjectDef def = forID(id);
		if (def == null) {
			System.out.println("Object definition null for id "+id);
			return;
		}
		System.out.println("Object[\nid="+id+",\nname="+def.name+",\nactions="+(def.actions == null ? "null" : Arrays.toString(def.actions))+",\nmodels="+(def.modelIds == null ? "null" : Arrays.toString(def.modelIds))+"\n]");
	}

	public void method574(OnDemandFetcher class42_sub1) {
		if (modelIds == null) {
			return;
		}
		for (int objectModelID : modelIds) {
			class42_sub1.loadExtra(objectModelID & 0xffff, 0);
		}
	}

	public boolean method577(int i) {
		if (models == null) {
			if (modelIds == null) {
				return true;
			}
			if (i != 10) {
				return true;
			}
			boolean flag1 = true;
			for (int objectModelID : modelIds) {
				flag1 &= Model.isModelFetched(objectModelID & 0xffff);
			}

			return flag1;
		}
		for (int j = 0; j < models.length; j++) {
			if (models[j] == i) {
				return Model.isModelFetched(modelIds[j] & 0xffff);
			}
		}

		return true;
	}

	public Model modelAt(int i, int j, int k, int l, int i1, int j1, int k1) {
		Model model = getAnimatedModel(i, k1, j);
		if (model == null) {
			return null;
		}
		if (adjustToTerrain || nonFlatShading) {
			model = new Model(adjustToTerrain, nonFlatShading, model);
		}
		if (adjustToTerrain) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.verticesCount; i2++) {
				int j2 = model.verticesX[i2];
				int k2 = model.verticesZ[i2];
				int l2 = k + (l - k) * (j2 + 64) / 128;
				int i3 = j1 + (i1 - j1) * (j2 + 64) / 128;
				int j3 = l2 + (i3 - l2) * (k2 + 64) / 128;
				model.verticesY[i2] += j3 - l1;
			}

			model.method467();
		}
		return model;
	}

	public boolean method579() {
		if (modelIds == null) {
			return true;
		}
		boolean flag1 = true;
		for (int objectModelID : modelIds) {
			flag1 &= Model.isModelFetched(objectModelID & 0xffff);
		}
		return flag1;
	}

	public ObjectDef method580() {
		int i = -1;
		if (varbitIndex != -1) {
			VarBit varBit = VarBit.cache[varbitIndex];
			int j = varBit.configId;
			int k = varBit.configValue;
			int l = varBit.anInt650;
			int i1 = Client.anIntArray1232[l - k];
			i = clientInstance.variousSettings[j] >> k & i1;
		} else if (configID != -1) {
			i = clientInstance.variousSettings[configID];
		}
		if (i < 0 || i >= morphisms.length || morphisms[i] == -1) {
			return null;
		} else {
			return forID(morphisms[i]);
		}
	}

	private Model getAnimatedModel(int j, int k, int l) {
		Model model = null;
		long l1;
		if (models == null) {
			if (j != 10) {
				return null;
			}
			l1 = (type << 8) + l + ((long) (k + 1) << 32);
			Model model_1 = (Model) mruNodes2.insertFromCache(l1);
			if (model_1 != null) {
				return model_1;
			}
			if (modelIds == null) {
				return null;
			}
			boolean flag1 = aBoolean751 ^ l > 3;
			int k1 = modelIds.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = modelIds[i2];
				if (flag1) {
					l2 += 0x10000;
				}
				model = (Model) baseModels.insertFromCache(l2);
				if (model == null) {
					model = Model.fetchModel(l2);
					if (model == null) {
						return null;
					}
					if (flag1) {
						model.method477();
					}
					baseModels.removeFromCache(model, l2);
				}
				if (k1 > 1) {
					aModelArray741s[i2] = model;
				}
			}

			if (k1 > 1) {
				model = new Model(k1, aModelArray741s);
			}
		} else {
			int i1 = -1;
			for (int j1 = 0; j1 < models.length; j1++) {
				if (models[j1] != j) {
					continue;
				}
				i1 = j1;
				break;
			}

			if (i1 == -1) {
				return null;
			}
			l1 = (type << 8) + (i1 << 3) + l + ((long) (k + 1) << 32);
			Model model_2 = (Model) mruNodes2.insertFromCache(l1);
			if (model_2 != null) {
				return model_2;
			}
			if (modelIds == null) {
				return null;
			}
			int j2 = modelIds[i1];
			boolean flag3 = aBoolean751 ^ l > 3;
			if (flag3) {
				j2 += 0x10000;
			}
			model = (Model) baseModels.insertFromCache(j2);
			if (model == null) {
				model = Model.fetchModel(j2 & 0xffff);
				if (model == null) {
					return null;
				}
				if (flag3) {
					model.method477();
				}
				baseModels.removeFromCache(model, j2);
			}
		}
		boolean flag;
		flag = modelSizeX != 128 || modelSizeH != 128 || modelSizeY != 128;
		boolean flag2;
		flag2 = offsetX != 0 || offsetH != 0 || offsetY != 0;
		Model model_3 = new Model(modifiedModelColors == null, AnimationSkeleton.isNullFrame(k), l == 0 && k == -1 && !flag && !flag2, model);
		if (k != -1) {
			model_3.createBones();
			model_3.applyTransform(k);
			model_3.triangleGroup = null;
			model_3.vertexGroups = null;
		}
		while (l-- > 0) {
			model_3.method473();
		}
		if (modifiedModelColors != null) {
			for (int k2 = 0; k2 < modifiedModelColors.length; k2++) {
				model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);
			}

		}
		if (flag) {
			model_3.scaleT(modelSizeX, modelSizeY, modelSizeH);
		}
		if (flag2) {
			model_3.translate(offsetX, offsetH, offsetY);
		}
		model_3.light(84 + brightness, 1000 + contrast * 5, -90, -580, -90, !nonFlatShading);

		// model_3.light(64 + brightness, 768 + contrast * 5, -50, -10, -50, !nonFlatShading);
		if (supportItems == 1) {
			model_3.anInt1654 = model_3.modelHeight;
		}
		mruNodes2.removeFromCache(model_3, l1);
		return model_3;
	}

	private void readValues(Stream stream, boolean osrs) {
		int i = -1;
		label0:
		do {
			int opcode;
			do {
				opcode = stream.getUnsignedByte();
				if (opcode == 0) {
					break label0;
				}
				if (opcode == 1) {
					int k = stream.getUnsignedByte();
					if (k > 0) {
						if (modelIds == null || lowDetail) {
							models = new int[k];
							modelIds = new int[k];
							for (int k1 = 0; k1 < k; k1++) {
								modelIds[k1] = stream.getUnsignedShort() + (osrs ? 200000 : 0);
								models[k1] = stream.getUnsignedByte();
							}
						} else {
							stream.position += k * 3;
						}
					}
				} else if (opcode == 2) {
					name = stream.getString();
				} else if (opcode == 3) {
					description = stream.getBytes();
				} else if (opcode == 5) {
					int l = stream.getUnsignedByte();
					if (l > 0) {
						if (modelIds == null || lowDetail) {
							models = null;
							modelIds = new int[l];
							for (int l1 = 0; l1 < l; l1++) {
								modelIds[l1] = stream.getUnsignedShort() + (osrs ? 200000 : 0);
							}
						} else {
							stream.position += l * 2;
						}
					}
				} else if (opcode == 14) {
					width = stream.getUnsignedByte();
				} else if (opcode == 15) {
					length = stream.getUnsignedByte();
				} else if (opcode == 17) {
					solid = false;
				} else if (opcode == 18) {
					impenetrable = false;
				} else if (opcode == 19) {
					i = stream.getUnsignedByte();
					if (i == 1) {
						interactive = true;
					}
				} else if (opcode == 21) {
					adjustToTerrain = true;
				} else if (opcode == 22) {
					nonFlatShading = false;
				} else if (opcode == 23) {
					occludes = true;
				} else if (opcode == 24) {
					animation = stream.getUnsignedShort();
					if (animation == 65535) {
						animation = -1;
					}
				} else if (opcode == 28) {
					decorDisplacement = stream.getUnsignedByte();
				} else if (opcode == 29) {
					brightness = stream.getSignedByte();
				} else if (opcode == 39) {
					contrast = stream.getSignedByte();
				} else if (opcode >= 30 && opcode < 39) {
					if (actions == null) {
						actions = new String[10];
					}
					actions[opcode - 30] = stream.getString();
					if (actions[opcode - 30].equalsIgnoreCase("hidden")) {
						actions[opcode - 30] = null;
					}
                    if (osrs) {
                        if (actions[opcode - 30].equalsIgnoreCase("of titan vault.")) {
                            actions[opcode - 30] = null;
                        }
                    }
				} else if (opcode == 40) {
					int i1 = stream.getUnsignedByte();
					modifiedModelColors = new int[i1];
					originalModelColors = new int[i1];
					for (int i2 = 0; i2 < i1; i2++) {
						modifiedModelColors[i2] = stream.getUnsignedShort();
						originalModelColors[i2] = stream.getUnsignedShort();
					}
				} else if (opcode == 60) {
					mapFunctionID = stream.getUnsignedShort();
				} else if (opcode == 62) {
					aBoolean751 = true;
				} else if (opcode == 64) {
					castsShadow = false;
				} else if (opcode == 65) {
					modelSizeX = stream.getUnsignedShort();
				} else if (opcode == 66) {
					modelSizeH = stream.getUnsignedShort();
				} else if (opcode == 67) {
					modelSizeY = stream.getUnsignedShort();
				} else if (opcode == 68) {
					mapSceneID = stream.getUnsignedShort();
				} else if (opcode == 69) {
					plane = stream.getUnsignedByte();
				} else if (opcode == 70) {
					offsetX = stream.getSignedShort();
				} else if (opcode == 71) {
					offsetH = stream.getSignedShort();
				} else if (opcode == 72) {
					offsetY = stream.getSignedShort();
				} else if (opcode == 73) {
					obstructsGround = true;
				} else if (opcode == 74) {
					isSolidObject = true;
				} else {
					if (opcode != 75) {
						continue;
					}
					supportItems = stream.getUnsignedByte();
				}
				continue label0;
			} while (opcode != 77);
			varbitIndex = stream.getUnsignedShort();
			if (varbitIndex == 65535) {
				varbitIndex = -1;
			}
			configID = stream.getUnsignedShort();
			if (configID == 65535) {
				configID = -1;
			}
			int j1 = stream.getUnsignedByte();
			morphisms = new int[j1 + 1];
            int offset = (osrs ? 20000 : 0);
			for (int j2 = 0; j2 <= j1; j2++) {
				morphisms[j2] = stream.getUnsignedShort() + offset;
				if (morphisms[j2] == 65535) {
					morphisms[j2] = -1;
				}
			}

		} while (true);
		if (i == -1) {
			interactive = modelIds != null && (models == null || models[0] == 10);
			if (actions != null) {
				interactive = true;
			}
		}
		if (isSolidObject) {
			solid = false;
			impenetrable = false;
		}
		if (supportItems == -1) {
			supportItems = solid ? 1 : 0;
		}
	}


    public void readValuesOSRS(Stream stream) {
        int flag = -1;
        do {
            int type = stream.readUnsignedByte();
            if (type == 0)
                break;
            if (type == 1) {
                int len = stream.readUnsignedByte();
                if (len > 0) {
                    if (modelIds == null || lowDetail) {
                        models = new int[len];
                        modelIds = new int[len];
                        for (int k1 = 0; k1 < len; k1++) {
                            modelIds[k1] = stream.getUnsignedShort();
                            models[k1] = stream.readUnsignedByte();
                        }
                    } else {
                        stream.position += len * 3;
                    }
                }
            } else if (type == 2)
                name = stream.getString();
            else if (type == 3)
                description = stream.getBytes();
            else if (type == 5) {
                int len = stream.readUnsignedByte();
                if (len > 0) {
                    if (modelIds == null || lowDetail) {
                        models = null;
                        modelIds = new int[len];
                        for (int l1 = 0; l1 < len; l1++)
                            modelIds[l1] = stream.getUnsignedShort() + 200_000;
                    } else {
                        stream.position += len * 2;
                    }
                }
            } else if (type == 14)
                width = stream.readUnsignedByte();
            else if (type == 15)
                length = stream.readUnsignedByte();
            else if (type == 17)
                solid = false;
            else if (type == 18)
                impenetrable = false;
            else if (type == 19)
                interactive = (stream.readUnsignedByte() == 1);
            else if (type == 21)
                adjustToTerrain = true;
            else if (type == 22)
                nonFlatShading = true;
            else if (type == 23)
                occludes = true;
            else if (type == 24) { // Object Animations
                animation = stream.getUnsignedShort();
                if (animation == 65535)
                    animation = -1;
            } else if (type == 28)
                decorDisplacement = stream.readUnsignedByte();
            else if (type == 29)
                brightness = stream.getSignedByte();
            else if (type == 39)
                contrast = stream.getSignedByte();
            else if (type >= 30 && type < 39) {
                if (actions == null) {
                    actions = new String[10];
                }

                actions[type - 30] = stream.getString();
                if (actions[type - 30].equalsIgnoreCase("of titan vault.") || actions[type - 30].equalsIgnoreCase("hidden")) {
                    actions[type - 30] = null;
                }
            } else if (type == 40) {
                int i1 = stream.readUnsignedByte();
                modifiedModelColors = new int[i1];
                originalModelColors = new int[i1];
                for (int i2 = 0; i2 < i1; i2++) {
                    modifiedModelColors[i2] = stream.getUnsignedShort();
                    originalModelColors[i2] = stream.getUnsignedShort();
                }
            } else if (type == 41) {
                int i1 = stream.readUnsignedByte();
                for (int i2 = 0; i2 < i1; i2++) {
                    stream.getUnsignedShort();
                    stream.getUnsignedShort();
                }
            } else if (type == 61) {
                stream.getUnsignedShort();
            } else if (type == 62)
                aBoolean751 = true;
            else if (type == 64)
                castsShadow = false;
            else if (type == 65)
                modelSizeX = stream.getUnsignedShort();
            else if (type == 66)
                modelSizeH = stream.getUnsignedShort();
            else if (type == 67)
                modelSizeY = stream.getUnsignedShort();
            else if (type == 68) {
                mapSceneID = stream.getUnsignedShort();
            } else if (type == 69)
                plane = stream.readUnsignedByte();
            else if (type == 70)
                offsetX = stream.getSignedShort();
            else if (type == 71)
                offsetH = stream.getSignedShort();
            else if (type == 72)
                offsetY = stream.getSignedShort();
            else if (type == 73)
                obstructsGround = true;
            else if (type == 74)
                isSolidObject = true;
            else if (type == 75)
                supportItems = stream.readUnsignedByte();
            else if (type == 77 || type == 92) {
                varbitIndex = stream.getUnsignedShort();
                if (varbitIndex == 65535)
                    varbitIndex = -1;
                configID = stream.getUnsignedShort();
                if (configID == 65535)
                    configID = -1;
                int var3 = -1;
                if(type == 92)
                    var3 = stream.getUnsignedShort();
                int j1 = stream.readUnsignedByte();
                morphisms = new int[j1 + 2];
                for (int j2 = 0; j2 <= j1; j2++) {
                    morphisms[j2] = stream.getUnsignedShort();
                    if (morphisms[j2] == 65535)
                        morphisms[j2] = -1;
                }
                morphisms[j1 + 1] = var3;
            } else if(type == 78) {//ambient sound
                stream.getUnsignedShort();
                stream.readUnsignedByte();
            } else if(type == 79) {
                stream.position += 5;
                int len = stream.readUnsignedByte();
                stream.position += len * 2;
            } else if(type == 81) {
                stream.readUnsignedByte();
            } else if(type == 82) {
                mapFunctionID = stream.getUnsignedShort();
            } else if (type == 249) {
                int length = stream.readUnsignedByte();

                for (int i = 0; i < length; i++) {
                    boolean isString = stream.readUnsignedByte() == 1;
                    int key = stream.read24BitInt();

                    if (isString) {
                        stream.readOSRSString();
                    } else {
                        stream.read24BitInt();
                    }
                }
            }
        } while (true);
        if (flag == -1 && name != "null" && name != null) {
            interactive = modelIds != null && (models == null || models[0] == 10);
            if (actions != null)
                interactive = true;
        }
        if (isSolidObject) {
            solid = false;
            impenetrable = false;
        }
        if (supportItems == -1)
            supportItems = solid ? 1 : 0;
    }


	private void setDefaults() {
		modelIds = null;
		models = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		width = 1;//sizeX
		length = 1;//sizeY
		solid = true;
		impenetrable = true;
		interactive = false;
		adjustToTerrain = false;
		nonFlatShading = false;
		occludes = false;
		animation = -1;
		decorDisplacement = 16;
		brightness = 0;
		contrast = 0;
		actions = null;
		mapFunctionID = -1;
		mapSceneID = -1;
		aBoolean751 = false;
		castsShadow = true;
		modelSizeX = 128;
		modelSizeH = 128;
		modelSizeY = 128;
		plane = 0;
		offsetX = 0;
		offsetH = 0;
		offsetY = 0;
		obstructsGround = false;
		isSolidObject = false;
		supportItems = -1;
		varbitIndex = -1;
		configID = -1;
		morphisms = null;
	}
}
