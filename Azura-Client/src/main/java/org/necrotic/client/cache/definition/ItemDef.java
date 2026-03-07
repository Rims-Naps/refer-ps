package org.necrotic.client.cache.definition;

import org.necrotic.Configuration;
import org.necrotic.client.Client;
import org.necrotic.client.cache.definition.items.ArmorDefinitions;
import org.necrotic.client.cache.definition.items.CustomItems;
import org.necrotic.client.cache.definition.items.WeaponDefinitions;
import org.necrotic.client.collection.MRUNodes;
import org.necrotic.client.cache.Archive;
import org.necrotic.client.media.Raster;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.net.Stream;
import org.necrotic.client.media.renderable.Model;
import org.necrotic.client.media.Rasterizer3D;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class ItemDef {


	public int rdc = 0;
	public int rdc2 = 0;
	public int rdc3 = 0;
	private static final int[] BLACK_FIX = {};
	private static Stream buffer;
	boolean animateInventory = true;
	private Object lentID;
	public static boolean isMembers = true;
	public static MRUNodes mruNodes1 = new MRUNodes(100);
	public static MRUNodes mruNodes2 = new MRUNodes(50);
	private static int[] streamIndices;
	public static int totalItems;

	public int xTranslation;
	public int yTranslation;
	public int zTranslation;

	public void updateColor(int originalColor, int targetColor) {
		java.util.List<Integer> indices = getColorIndices(originalColor);
		indices.forEach(i -> newModelColors[i] = targetColor);
	}

	private java.util.List<Integer> getColorIndices(int color) {
		java.util.List<Integer> indices = new ArrayList<>();

		for (int i = 0; i < originalModelColors.length; i++) {
			if (originalModelColors[i] == color) {
				indices.add(i);
			}
		}
		return indices;
	}

	public static void applyTexturing(Model model, int id) {
		switch (id) {
			case 20090:
			case 20091:
			case 20092:
			case 20093:
			case 20094:
				model.setTexture(150);
				break;
			case 534:
				model.setTexture(141);
				break;
			case 7305:
				model.setTexture(145);
				break;
			case 7306:
				model.setTexture(147);
				break;
			case 7307:
			case 23164:
			case 15438:
				model.setTexture(143);
				break;
			case 7308:
				model.setTexture(159);
				break;
			case 7309:
			case 7053:
				model.setTexture(133);
				break;

			//case 4155:
			case 4587:
			case 23118:
			case 2706:
			case 4278:
			case 4279:
			case 4281:
				model.setTexture(124);
				break;
			case 2708:
				model.setTexture(142);
				break;

		}
	}

	public static void dumpItemModelsForId(int i) {
		try {
			ItemDef d = get(i);

			if (d != null) {
				int[] models = {d.maleEquip1, d.femaleEquip1, d.modelID,};

				for (int ids : models) {// 13655
					if (ids > 0) {
						try {
							System.out.println("Dumping item model: " + ids);
							byte[] abyte = Client.instance.decompressors[1].read(ids);
							File map = new File("./models/" + ids + ".gz");
							FileOutputStream fos = new FileOutputStream(map);
							fos.write(abyte);
							fos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static HashMap<Integer, ItemDef> map = new HashMap<>();

	public static ItemDef get(int id) {
		ItemDef itemDef = map.get(id);
		if (itemDef != null) {
			return itemDef;
		}
		itemDef = new ItemDef();

		if (id > streamIndices.length) {
			id = streamIndices.length - 1;
		}

		buffer.position = streamIndices[id];
		itemDef.id = id;
		itemDef.setDefaults();
		itemDef.readValues(buffer);
		if (itemDef.originalModelColors != null) {
			for (int i2 = 0; i2 < itemDef.originalModelColors.length; i2++) {
				if (itemDef.newModelColors[i2] == 0) {
					itemDef.newModelColors[i2] = 1;
				}
			}
		}

		for (int a : BLACK_FIX) {
			if (itemDef.id == a) {
				itemDef.originalModelColors = new int[1];
				itemDef.newModelColors = new int[1];
				itemDef.originalModelColors[0] = 0;
				itemDef.newModelColors[0] = 1;
			}
		}

		int customId = itemDef.id;
		itemDef = ArmorDefinitions.newIDS(itemDef, id);
		itemDef = WeaponDefinitions.newIDS1(itemDef, id);
		if (customId >= 13700 && customId <= 13709) {
			itemDef.certID = -1;
			itemDef.certTemplateID = -1;
			itemDef.stackable = false;
		}

		CustomItems.addCustomItems(itemDef, customId);

		switch (customId) {
			case 16517:
				itemDef.name = "@red@Warrior Totem(1)";
				itemDef.modelID = 130520;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;

			case 7053:
				itemDef.name = "Charons Lantern";
				itemDef.actions = new String[]{"Harvest Souls", null,"Check", null, "Drop"};
				itemDef.description = "A lantern that collects souls of the perished....".getBytes();
				itemDef.element = 2;
				itemDef.stackable = false;
				break;
			case 16518:
				itemDef.name = "@red@Warrior Totem(2)";
				itemDef.modelID = 130524;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16519:
				itemDef.name = "@red@Warrior Totem(3)";
				itemDef.modelID = 130525;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16520:
				itemDef.name = "@gre@Sharpshooter Totem(1)";
				itemDef.modelID = 130521;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16508:
				itemDef.name = "@gre@Sharpshooter Totem(2)";
				itemDef.modelID = 130526;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16509:
				itemDef.name = "@gre@Sharpshooter Totem(3)";
				itemDef.modelID = 130527;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16510:
				itemDef.name = "@cya@Sorcerer Totem(1)";
				itemDef.modelID = 130522;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16511:
				itemDef.name = "@cya@Sorcerer Totem(2)";
				itemDef.modelID = 130528;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 23164:
				itemDef.name = "<col=1097BF>Wrath Of Poseidon";
				itemDef.modelID = 130184;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.element = 3;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				break;

			case 23165:
				itemDef.name = "$100 Pre Release Package";
				itemDef.actions = new String[]{"@yel@Open", null, null, null, null};
				itemDef.modelID = 131679;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.rdc2 = 800;
				break;
			case 23166:
				itemDef.name = "$250 Pre Release Package";
				itemDef.actions = new String[]{"@yel@Open", null, null, null, null};
				itemDef.modelID = 131679;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.rdc2 = 4656;
				break;
			case 23167:
				itemDef.name = "$350 Pre Release Package";
				itemDef.actions = new String[]{"@yel@Open", null, null, null, null};
				itemDef.modelID = 131679;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.rdc3 = 4656;
				break;
			case 23168:
				itemDef.name = "$500 Pre Release Package";
				itemDef.actions = new String[]{"@yel@Open", null, null, null, null};
				itemDef.modelID = 131679;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.rdc2 = 5000;
				itemDef.element = 16;
				break;
			case 16512:
				itemDef.name = "@cya@Sorcerer Totem(3)";
				itemDef.modelID = 130529;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16513:
				itemDef.name = "<col=AF70C3>Master Totem(1)";
				itemDef.modelID = 130523;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16514:
				itemDef.name = "<col=AF70C3>Master Totem(2)";
				itemDef.modelID = 130530;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16515:
				itemDef.name = "<col=AF70C3>Master Totem(3)";
				itemDef.modelID = 130531;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 16516:
				itemDef.name = "Blank Totem";
				itemDef.modelID = 103039;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 480;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 1;
				itemDef.stackable = false;
				break;
			case 3580:
				itemDef.name = "Low Tier Rune Pack";
				itemDef.modelID = 103030;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 4688;
				itemDef.translate_yz = -11;
				itemDef.rotation_z = 0;
				itemDef.rotation_x = 19;
				itemDef.rotation_y = 77;
				break;
			case 3582:
				itemDef.name = "High Tier Rune Pack";
				itemDef.modelID = 103031;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 4688;
				itemDef.translate_yz = -11;
				itemDef.rotation_z = 0;
				itemDef.rotation_x = 19;
				itemDef.rotation_y = 77;
				break;
			case 23020:
				itemDef.copyItem(19670);
				itemDef.name = "@bla@Vote Scroll";
				itemDef.modelID = 101764;
				itemDef.maleEquip1 = 101764;
				itemDef.femaleEquip1 = 101764;
				itemDef.actions = new String[]{"Claim", null, "Claim All", null, "Drop"};

				break;

			case 20425:
				itemDef.name = "@whi@Un-Charged Urn";
				itemDef.modelID = 130475;
				itemDef.modelZoom = 1025;
				itemDef.rotation_x = 135;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.translate_x = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20426:
				itemDef.name = "@mag@Charged Urn";
				itemDef.modelID = 130474;
				itemDef.modelZoom = 1025;
				itemDef.rotation_x = 135;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.translate_x = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 6545:
				itemDef.modelID = 101002;
				//	itemDef.translate_x = 2;
				itemDef.translate_yz = -9;
				itemDef.rotation_y = 204;
				//	itemDef.rotation_x = 20;
				itemDef.modelZoom = 735;
				itemDef.name = "@red@Chaos Pet@yel@(2X KC)";
				itemDef.actions = new String[]{null, null, "Summon", null, "Drop"};
				itemDef.scaleZ = 25;
				itemDef.scaleX = 25;
				itemDef.scaleY = 25;
				break;
			//20_000 - 20_100 usable
			case 20090:
				itemDef.name = "Embermourne";
				itemDef.modelID = 10850;
				itemDef.modelZoom = 1744;
				itemDef.rotation_x = 192;
				itemDef.rotation_y = 354;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 3;
				itemDef.translate_x = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20091:
				itemDef.name = "Frostbane";
				itemDef.modelID = 10853;
				itemDef.modelZoom = 1744;
				itemDef.rotation_x = 192;
				itemDef.rotation_y = 352;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 3;
				itemDef.translate_x = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20092:
				itemDef.name = "Mossheart";
				itemDef.modelID = 40916;
				itemDef.modelZoom = 848;
				itemDef.rotation_x = 138;
				itemDef.rotation_y = 267;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20093:
				itemDef.name = "Thundervale";
				itemDef.modelID = 10852;
				itemDef.modelZoom = 1744;
				itemDef.rotation_x = 192;
				itemDef.rotation_y = 354;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 3;
				itemDef.translate_x = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20094:
				itemDef.name = "Abyssalite";
				itemDef.modelID = 40914;
				itemDef.modelZoom = 789;
				itemDef.rotation_x = 186;
				itemDef.rotation_y = 225;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -10;
				itemDef.translate_x = 4;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;

			case 9719:
				itemDef.name = "Task Skip";
				break;
			case 621:
				itemDef.name = "<col=AF70C3>Necro Points";
				itemDef.modelID = 130660;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 15;
				itemDef.rotation_y = 450;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;

			case 20006:
				itemDef.name = "@bla@Necro Page(1)";
				itemDef.modelID = 4508;
				itemDef.modelZoom = 680;
				itemDef.rotation_x = 80;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20007:
				itemDef.name = "@bla@Necro Page(2)";
				itemDef.modelID = 4508;
				itemDef.modelZoom = 680;
				itemDef.rotation_x = 80;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20008:
				itemDef.name = "@bla@Necro Page(3)";
				itemDef.modelID = 4508;
				itemDef.modelZoom = 680;
				itemDef.rotation_x = 80;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20009:
				itemDef.name = "@bla@Necro Page(4)";
				itemDef.modelID = 4508;
				itemDef.modelZoom = 680;
				itemDef.rotation_x = 80;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 3578:
				itemDef.name = "<col=AF70C3>Hyperion Goodiebag";
				itemDef.actions = new String[]{"Open", null,null, "Rewards", "Drop"};
				itemDef.modelID = 130180;
				itemDef.modelZoom = 2250;
				itemDef.element = 4;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 4022:
				itemDef.name = "<col=AF70C3>Hyperion Goodiebag(u)";
				itemDef.actions = new String[]{"Open", null, null, "Rewards", "Drop"};
				itemDef.modelID = 131660;
				itemDef.modelZoom = 2250;
				itemDef.element = 4;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 20010:
				itemDef.name = "Curse Rune";
				itemDef.modelID = 130484;
				itemDef.modelZoom = 740;
				itemDef.rotation_x = 1012;
				itemDef.rotation_y = 528;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20011:
				itemDef.name = "Wraith Rune";
				itemDef.modelID = 130485;
				itemDef.modelZoom = 740;
				itemDef.rotation_x = 1012;
				itemDef.rotation_y = 528;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20012:
				itemDef.name = "Shadow Rune";
				itemDef.modelID = 130486;
				itemDef.modelZoom = 740;
				itemDef.rotation_x = 1012;
				itemDef.rotation_y = 528;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20013:
				itemDef.name = "Void Rune";
				itemDef.modelID = 130487;
				itemDef.modelZoom = 740;
				itemDef.rotation_x = 1012;
				itemDef.rotation_y = 528;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20014:
				itemDef.name = "Soul Rune";
				itemDef.modelID = 130488;
				itemDef.modelZoom = 740;
				itemDef.rotation_x = 1012;
				itemDef.rotation_y = 528;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20015:
				itemDef.name = "Crypt Rune";
				itemDef.modelID = 130489;
				itemDef.modelZoom = 2825;
				itemDef.rotation_x = 1012;
				itemDef.rotation_y = 528;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 20016:
				break;
			case 20030:
				itemDef.name = "<col=5FD857>Gaia's Blessing";
				itemDef.modelID = 130476;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20031:
				itemDef.name = "<col=5FD857>Serenity Unlock";
				itemDef.modelID = 130476;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20032:
				itemDef.name = "<col=5FD857>Rockheart Unlock";
				itemDef.modelID = 130476;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20033:
				itemDef.name = "<col=5FD857>Geovigor Unlock";
				itemDef.modelID = 130476;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20034:
				itemDef.name = "<col=5FD857>Stonehaven Unlock";
				itemDef.modelID = 130477;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20035:
				itemDef.name = "<col=1097BF>Ebb & Flow Unlock";
				itemDef.modelID = 130480;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20036:
				itemDef.name = "<col=1097BF>Aquastrike Unlock";
				itemDef.modelID = 130480;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20037:
				itemDef.name = "<col=1097BF>Riptide Unlock";
				itemDef.modelID = 130480;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20038:
				itemDef.name = "<col=1097BF>SeaSlicer Unlock";
				itemDef.modelID = 130480;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20039:
				itemDef.name = "<col=1097BF>SwiftTide Unlock";
				itemDef.modelID = 130481;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20040:
				itemDef.name = "<col=D45F4B>Cinder's Touch Unlock";
				itemDef.modelID = 130478;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;

			case 20041:
				itemDef.name = "<col=D45F4B>EmberBlast Unlock";
				itemDef.modelID = 130478;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20042:
				itemDef.name = "<col=D45F4B>Infernify Unlock";
				itemDef.modelID = 130478;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20043:
				itemDef.name = "<col=D45F4B>Blazeup Unlock";
				itemDef.modelID = 130478;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20044:
				itemDef.name = "<col=D45F4B>Inferno Unlock";
				itemDef.modelID = 130479;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;

			case 20047:
				// Your code for case 20047
				break;
			case 20048:
				// Your code for case 20048
				break;
			case 20049:
				itemDef.name = "@red@Rare Gems";
				itemDef.modelID = 130605;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.modelZoom = 1000;
				itemDef.rotation_x = 0;
				itemDef.translate_x = 0;
				break;


			case 20017:
				itemDef.name = "Tier 1 Starter Kit";
				itemDef.modelID = 130455;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 1292;
				itemDef.rotation_x = 0;
				break;
			case 20018:
				itemDef.name = "Tier 2 Starter Kit";
				itemDef.modelID = 130456;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 1292;
				itemDef.rotation_x = 0;
				break;
			case 20019:
				itemDef.name = "Tier 3 Starter Kit";
				itemDef.modelID = 130457;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 1292;
				itemDef.rotation_x = 0;
				break;
			case 20050:
				itemDef.name = "<col=AF70C3>Legacy Bundle";
				itemDef.modelID = 130902;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				break;
			case 20060:
				itemDef.name = "<col=AF70C3>Skeletal Unlock";
				itemDef.modelID = 130458;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				itemDef.modelZoom = 1480;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 17;
				break;
			case 20061:
				itemDef.name = "@gre@Demonic Unlock";
				itemDef.modelID = 130459;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				itemDef.modelZoom = 1480;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 17;
				break;
			case 20062:
				itemDef.name = "@yel@Ogre Unlock";
				itemDef.modelID = 130460;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				itemDef.modelZoom = 1480;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 17;
				break;
			case 20063:
				itemDef.name = "@cya@Spectral Unlock";
				itemDef.modelID = 130461;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				itemDef.modelZoom = 1480;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 17;
				break;
			case 3007:
				itemDef.name = "@yel@Fallen Token";
				itemDef.modelID = 132156;
				itemDef.actions = new String[]{"Break", null, null, null, "Drop"};
				itemDef.modelZoom = 900;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 511;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				break;
			case 20064:
				itemDef.name = "@red@Master Unlock";
				itemDef.modelID = 130462;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				itemDef.modelZoom = 1480;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 17;
				break;
			case 20065:
				itemDef.name = "<col=AF70C3>Nocturne Amulet";
				itemDef.modelID = 130463;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelZoom = 536;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 381;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 28;
				break;

			case 20066:
				itemDef.name = "<col=AF70C3>Necromancy Lamp";
				itemDef.modelID = 130464;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 2850;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 20067:
				itemDef.name = "@gre@Herblore Lamp";
				itemDef.modelID = 130465;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 2850;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 20068:
				itemDef.name = "@or2@Large Slayer Lamp";
				itemDef.modelID = 130466;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 2850;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 17127:
				itemDef.name = "@or2@Small Slayer Lamp";
				itemDef.modelID = 130466;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 3325;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 17128:
				itemDef.name = "@or2@Medium Slayer Lamp";
				itemDef.modelID = 130466;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 3100;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 20069:
				itemDef.name = "@blu@Mining Lamp";
				itemDef.modelID = 130467;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 2850;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 20070:
				itemDef.name = "Xp Lamp 5";
				itemDef.modelID = 130468;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 2850;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 20071:
				itemDef.name = "@yel@Prayer Lamp";
				itemDef.modelID = 130469;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 2850;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 20072:
				itemDef.name = "Xp Lamp 7";
				itemDef.modelID = 130470;
				itemDef.actions = new String[]{"Rub", null, "Rub All", null, "Drop"};
				itemDef.modelZoom = 2850;
				itemDef.rotation_x = 228;
				itemDef.rotation_y = 28;
				itemDef.translate_x = 4;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;

			case 20080:
				itemDef.name = "Tier 1 Totem";
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelID = 130439;
				itemDef.modelZoom = 811;
				itemDef.rotation_x = 1124;
				itemDef.rotation_y = 153;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -9;
				break;
			case 20081:
				itemDef.name = "Tier 2 Totem";
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelID = 130440;
				itemDef.modelZoom = 811;
				itemDef.rotation_x = 1124;
				itemDef.rotation_y = 153;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -9;
				break;
			case 20082:
				itemDef.name = "Tier 3 Totem";
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelID = 130441;
				itemDef.modelZoom = 811;
				itemDef.rotation_x = 1124;
				itemDef.rotation_y = 153;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -9;
				break;
			case 3781:
				itemDef.setDefaults();
				itemDef.name = "@yel@Enhanced Totem";
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelID = 131830;
				itemDef.modelZoom = 811;
				itemDef.rotation_x = 1124;
				itemDef.rotation_y = 153;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -9;
				break;



			case 1267:
				itemDef.name = "Tier 1 Pickaxe";
				itemDef.modelID = 65224;
				itemDef.maleEquip1 = 65225;
				itemDef.femaleEquip1 = 65225;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.newModelColors = new int[1];
				itemDef.originalModelColors = new int[1];
				itemDef.originalModelColors[0] = 60;
				itemDef.newModelColors[0] = 115;
				itemDef.stackable = false;
				itemDef.modelZoom = 1570;
				itemDef.rotation_x = 997;
				itemDef.rotation_y = 660;
				itemDef.translate_x = 10;
				itemDef.translate_yz = 1;
				itemDef.rotation_z = 0;
				break;
			case 1269:
				itemDef.name = "Tier 2 Pickaxe";
				itemDef.modelID = 65224;
				itemDef.maleEquip1 = 65225;
				itemDef.femaleEquip1 = 65225;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.newModelColors = new int[1];
				itemDef.originalModelColors = new int[1];
				itemDef.originalModelColors[0] = 60;
				itemDef.newModelColors[0] = 114;
				itemDef.stackable = false;
				itemDef.modelZoom = 1570;
				itemDef.rotation_x = 997;
				itemDef.rotation_y = 660;
				itemDef.translate_x = 10;
				itemDef.translate_yz = 1;
				itemDef.rotation_z = 0;
				break;
			case 1273:
				itemDef.name = "Tier 3 Pickaxe";
				itemDef.modelID = 65224;
				itemDef.maleEquip1 = 65225;
				itemDef.femaleEquip1 = 65225;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.newModelColors = new int[1];
				itemDef.originalModelColors = new int[1];
				itemDef.originalModelColors[0] = 60;
				itemDef.newModelColors[0] = 106;
				itemDef.stackable = false;
				itemDef.modelZoom = 1570;
				itemDef.rotation_x = 997;
				itemDef.rotation_y = 660;
				itemDef.translate_x = 10;
				itemDef.translate_yz = 1;
				itemDef.rotation_z = 0;
				break;
			case 1271:
				itemDef.name = "Tier 4 Pickaxe";
				itemDef.modelID = 65224;
				itemDef.maleEquip1 = 65225;
				itemDef.femaleEquip1 = 65225;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.newModelColors = new int[1];
				itemDef.originalModelColors = new int[1];
				itemDef.originalModelColors[0] = 60;
				itemDef.newModelColors[0] = 119;
				itemDef.stackable = false;
				itemDef.modelZoom = 1570;
				itemDef.rotation_x = 997;
				itemDef.rotation_y = 660;
				itemDef.translate_x = 10;
				itemDef.translate_yz = 1;
				itemDef.rotation_z = 0;
				break;
			case 2705:
				itemDef.name = "@red@Enchanted Heart";
				itemDef.modelID = 130200;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 1387;
				itemDef.rotation_y = 359;
				itemDef.actions = new String[]{"Heal", null, "Upgrade", null, "Drop"};
				break;
			case 15666:
				itemDef.name = "Beginner Weapon Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130922;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 86;
				itemDef.translate_yz = 0;
				break;
			case 23171:
				itemDef.name = "Medium Weapon Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130209;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 86;
				break;
			case 23172:
				itemDef.name = "Elite Weapon Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130208;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 86;
				break;
			case 23173:
				itemDef.name = "Necromancy Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130899;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 86;
				break;
			case 17129:
				itemDef.name = "@gre@Elemental Cache";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130775;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 2624:
				itemDef.setDefaults();
				itemDef.name = "@yel@Elemental Cache(u)";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 131680;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 2622:
				itemDef.name = "@red@Lover's Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 131679;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 2626:
				itemDef.name = "<col=E86AA7>Candy Boost";
				itemDef.modelID = 131681;
				itemDef.modelZoom = 739;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 208;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -4;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				break;
			case 17130:
				itemDef.setDefaults();
				itemDef.name = "<col=AF70C3>Pandora's Box";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130776;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				break;
			case 23174:
				itemDef.name = "Box 4";
				break;

			case 3590:
				itemDef.name = "<col=E08E8E>Valentines Pass(T1)";
				itemDef.modelID = 131669;
				itemDef.modelZoom = 1036;
				itemDef.rotation_x = 1264;
				itemDef.rotation_y = 132;
				itemDef.rotation_z = 126;
				itemDef.translate_x = -2;
				itemDef.translate_yz = -17;
				itemDef.actions = new String[]{"@yel@Claim", null, null, null, "Drop"};
				itemDef.newModelColors = new int[119];
				itemDef.originalModelColors = new int[55];
				break;
			case 3594:
				itemDef.name = "@red@Valentines Pass(T2)";
				itemDef.modelID = 131670;
				itemDef.modelZoom = 1036;
				itemDef.rotation_x = 1264;
				itemDef.rotation_y = 132;
				itemDef.rotation_z = 126;
				itemDef.translate_x = -2;
				itemDef.translate_yz = -17;
				itemDef.actions = new String[]{"@yel@Claim", null, null, null, "Drop"};
				itemDef.newModelColors = new int[119];
				itemDef.originalModelColors = new int[55];
				break;
			case 2601:
				itemDef.name = "<col=E86AA7>Love Serum";
				itemDef.modelID = 131682;
				itemDef.modelZoom = 961;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.actions = new String[]{"Drink", null, null, null, null};
				break;
			case 2602:
				itemDef.name = "Valentines Weapon";
				itemDef.modelID = 130202;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				break;
			case 2701:
				itemDef.name = "Tier 1 Membership Scroll";
				itemDef.modelID = 130202;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				break;
			case 2702:
				itemDef.name = "Tier 2 Membership Scroll";
				itemDef.modelID = 130201;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				break;
			case 2703:
				itemDef.name = "Tier 3 Membership Scroll";
				itemDef.modelID = 130206;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				break;
			case 2704:
				itemDef.name = "Tier 4 Membership Scroll";
				itemDef.modelID = 130203;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				break;
			case 23119:
				itemDef.name = "@gre@Dr Salt";
				itemDef.modelID = 130193;
				itemDef.modelZoom = 1300;
				itemDef.rotation_x = 414;
				itemDef.rotation_y = 133;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.hintTier = 1;
				break;
			case 23121:
				itemDef.name = "@red@Dmg salt";
				itemDef.modelZoom = 1300;
				itemDef.modelID = 130194;
				itemDef.rotation_x = 344;
				itemDef.rotation_y = 154;
				itemDef.translate_x = -2;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.hintTier = 1;
				break;
			case 23122:
				itemDef.name = "@cya@Crit Salt";
				itemDef.modelZoom = 1300;
				itemDef.modelID = 130196;
				itemDef.rotation_x = 114;
				itemDef.rotation_y = 152;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.hintTier = 1;
				break;
			case 17582:
				itemDef.name = "@red@Damage Potion";
				itemDef.modelID = 130406;
				itemDef.actions = new String[]{"Drink", null, null, null, null};
				itemDef.hintTier = 1;
				break;
			case 17584:
				itemDef.name = "@cya@Crit Potion";
				itemDef.modelID = 130408;
				itemDef.actions = new String[]{"Drink", null, null, null, null};
				itemDef.hintTier = 1;
				break;
			case 17586:
				itemDef.name = "@gre@Droprate Potion";
				itemDef.modelID = 130407;
				itemDef.actions = new String[]{"Drink", null, null, null, null};
				itemDef.hintTier = 1;
				break;
			case 17490:
				itemDef.name = "Elemental Vial";
				itemDef.actions = new String[]{null, null, null, null, null};

				break;
			case 12839:
				itemDef.name = "<col=5FD857>Gaia Crystal(1)";
				itemDef.modelID = 130183;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 1;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 17003:
				itemDef.name = "<col=5FD857>Gaia Crystal(2)";
				itemDef.modelID = 130540;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 1;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 17004:
				itemDef.name = "<col=5FD857>Gaia Crystal(3)";
				itemDef.modelID = 130541;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.element = 1;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 12840:
				itemDef.name = "<col=D45F4B>Lava Crystal(1)";
				itemDef.modelID = 130182;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 2;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 17005:
				itemDef.name = "<col=D45F4B>Lava Crystal(2)";
				itemDef.modelID = 130542;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 2;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 17006:
				itemDef.name = "<col=D45F4B>Lava Crystal(3)";
				itemDef.modelID = 130543;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.element = 2;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 12841:
				itemDef.name = "<col=1097BF>Aqua Crystal(1)";
				itemDef.modelID = 130184;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 3;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 17007:
				itemDef.name = "<col=1097BF>Aqua Crystal(2)";
				itemDef.modelID = 130544;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 3;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 17008:
				itemDef.name = "<col=1097BF>Aqua Crystal(3)";
				itemDef.modelID = 130545;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.element = 3;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 12842:
				itemDef.name = "<col=AF70C3>Void Crystal(1)";
				itemDef.modelID = 130185;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 4;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;

				break;
			case 17009:
				itemDef.name = "<col=AF70C3>Void Crystal(2)";
				itemDef.modelID = 130546;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 4;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;

				break;
			case 17010:
				itemDef.name = "<col=AF70C3>Void Crystal(3)";
				itemDef.modelID = 130547;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.element = 4;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;

				break;

			//
			case 17011:
				itemDef.name = "Uncharged Crystal";
				itemDef.modelID = 130532;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				break;
			case 17012:
				itemDef.name = "@red@Warrior Fragment";
				itemDef.modelID = 130534;
				itemDef.modelZoom = 924;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 17013:
				itemDef.name = "@gre@Sharpshooter Fragment";
				itemDef.modelID = 130533;
				itemDef.modelZoom = 924;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 17014:
				itemDef.name = "@cya@Sorcerer Fragment";
				itemDef.modelID = 130535;
				itemDef.modelZoom = 924;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 17015:
				itemDef.name = "<col=5FD857>Gaia Shard";
				itemDef.modelID = 130536;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 17016:
				itemDef.name = "<col=1097BF>Aqua Shard";
				itemDef.modelID = 130538;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 17017:
				itemDef.name = "<col=D45F4B>Lava Shard";
				itemDef.modelID = 130537;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 17018:
				itemDef.name = "@red@Slayer Energy(1)";
				itemDef.modelID = 130548;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				break;
			case 2610:
				itemDef.name = "@red@Slayer Energy@yel@(2)";
				itemDef.modelID = 131672;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 17019:
				itemDef.name = "<col=6307B9>Blitz Energy(1)";
				itemDef.modelID = 130549;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				break;
			case 2611:
				itemDef.name = "<col=6307B9>Blitz Energy@yel@(2)";
				itemDef.modelID = 131673;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 17020:
				itemDef.name = "@gre@Luck Energy(1)";
				itemDef.modelID = 130550;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				break;
			case 2612:
				itemDef.name = "@gre@Luck Energy@yel@(2)";
				itemDef.modelID = 131674;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 17021:
				itemDef.name = "@cya@Soul Energy(1)";
				itemDef.modelID = 130551;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				break;
			case 2613:
				itemDef.name = "@cya@Soul Energy@yel@(2)";
				itemDef.modelID = 131675;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 17022:
				itemDef.name = "<col=AF70C3>Undead Energy(1)";
				itemDef.modelID = 130552;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				break;
			case 2614:
				itemDef.name = "<col=AF70C3>Undead Energy@yel@(2)";
				itemDef.modelID = 131676;
				itemDef.maleEquip1 = -1;
				itemDef.femaleEquip1 = -1;
				itemDef.modelZoom = 650;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 200;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 17059:
				itemDef.name = "<col=5FD857>Gaia Sigil";
				itemDef.modelID = 130607;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				break;
			case 17060:
				itemDef.name = "<col=5FD857>Gaia fragment";
				itemDef.modelID = 130661;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 17061:
				itemDef.name = "<col=D45F4B>Lava Sigil";
				itemDef.modelID = 130608;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				break;
			case 17062:
				itemDef.name = "<col=D45F4B>Lava fragment";
				itemDef.modelID = 130663;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 17063:
				itemDef.name = "<col=1097BF>Aqua Sigil";
				itemDef.modelID = 130606;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				break;
			case 17064:
				itemDef.name = "<col=1097BF>Aqua fragment";
				itemDef.modelID = 130662;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;

			case 19944:
				itemDef.name = "Hyperion Cape";
			/*	itemDef.modelID = 130881;
				itemDef.maleEquip1 = 130881;
				itemDef.femaleEquip1 = 130881;*/
				itemDef.modelID = 131975;
				itemDef.maleEquip1 = 131975;
				itemDef.femaleEquip1 = 131975;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 4;
				itemDef.modelZoom = 2325;
				itemDef.rotation_x = 1000;
				itemDef.rotation_y = 9;
				itemDef.rotation_z = 0;
				itemDef.translate_x = -3;
				itemDef.translate_yz = 9;
				break;

			case 17122:
				itemDef.name = "<col=AF70C3>Hyperion Amulet";
				itemDef.modelID = 130763;
				itemDef.maleEquip1 = 130761;
				itemDef.femaleEquip1 = 130761;
				itemDef.modelZoom = 463;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 791;
				itemDef.translate_x = -1;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 4;
				break;
			case 17124:
				itemDef.name = "<col=AF70C3>Hyperion Amulet(u)";
				itemDef.modelID = 130764;
				itemDef.maleEquip1 = 130765;
				itemDef.femaleEquip1 = 130765;
				itemDef.modelZoom = 463;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 791;
				itemDef.translate_x = -1;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 4;
				break;
			case 17123:
				itemDef.name = "<col=AF70C3>Hyperion Ring";
				itemDef.modelID = 130760;
				itemDef.maleEquip1 = 130760;
				itemDef.femaleEquip1 = 130760;
				itemDef.modelZoom = 829;
				itemDef.rotation_x = 154;
				itemDef.rotation_y = 328;
				itemDef.translate_x = -4;
				itemDef.translate_yz = -5;
				itemDef.rotation_z = 0;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 4;
				break;

			case 10724:
				itemDef.name = "<col=AF70C3>Hyperion Ring(u)";
				itemDef.modelID = 130766;
				itemDef.maleEquip1 = 130766;
				itemDef.femaleEquip1 = 130766;
				itemDef.modelZoom = 829;
				itemDef.rotation_x = 154;
				itemDef.rotation_y = 328;
				itemDef.translate_x = -4;
				itemDef.translate_yz = -5;
				itemDef.rotation_z = 0;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 4;
				break;
			case 10725:
				itemDef.maleWieldY = -17;
				break;
			case 17125:
				itemDef.name = "@yel@Cosmetic Ticket";
				itemDef.modelID = 130767;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.modelZoom = 622;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 502;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				break;
			case 17126:
				itemDef.name = "Crate";
				break;

			case 12429:
				itemDef.name = "<col=AF70C3>Void Energy";
				itemDef.modelID = 130191;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 4;
				itemDef.modelZoom = 832;
				itemDef.rotation_x = 423;
				itemDef.rotation_y = 300;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 30;
				itemDef.stackable = false;
				break;
			case 12529:
				itemDef.name = "Fancy Potion";
				itemDef.modelID = 130192;
				itemDef.modelZoom = 738;
				itemDef.actions = new String[]{"Drink", null, null, null, "Drop"};
				itemDef.element = 1;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				break;
			case 12533:
				itemDef.name = "Test Prayer Item";
				itemDef.modelID = 130177;
				itemDef.modelZoom = 2250;
				itemDef.actions = new String[]{"@yel@Open", null, null, null, null};
				itemDef.element = 5;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 6804:
				itemDef.name = "Placeholderxx";
				itemDef.modelID = 130177;
				itemDef.modelZoom = 2250;
				itemDef.actions = new String[]{"@yel@Open", null, null, null, null};
				itemDef.element = 1;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 6805:
				itemDef.name = "<col=5FD857>Terra Tales(1)";
				itemDef.modelID = 132005;
				itemDef.maleEquip1 = 132006;
				itemDef.femaleEquip1 = 132006;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 1;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 15700:
				itemDef.name = "<col=5FD857>Terra Tales(2)";
				itemDef.modelID = 132005;
				itemDef.maleEquip1 = 132006;
				itemDef.femaleEquip1 = 132006;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 1;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 15701:
				itemDef.name = "<col=5FD857>Terra Tales(3)";
				itemDef.modelID = 132005;
				itemDef.maleEquip1 = 132006;
				itemDef.femaleEquip1 = 132006;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.element = 1;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 6806:
				itemDef.name = "<col=1097BF>Nautic Memoirs(1)";
				itemDef.modelID = 131987;
				itemDef.maleEquip1 = 131988;
				itemDef.femaleEquip1 = 131988;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 3;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 15703:
				itemDef.name = "<col=1097BF>Nautic Memoirs(2)";
				itemDef.modelID = 131987;
				itemDef.maleEquip1 = 131988;
				itemDef.femaleEquip1 = 131988;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 3;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 15704:
				itemDef.name = "<col=1097BF>Nautic Memoirs(3)";
				itemDef.modelID = 131987;
				itemDef.maleEquip1 = 131988;
				itemDef.femaleEquip1 = 131988;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				itemDef.element = 3;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 6807:
				itemDef.name = "<col=D45F4B>Infernal Chronicles(1)";
				itemDef.modelID = 131985;
				itemDef.maleEquip1 = 131986;
				itemDef.femaleEquip1 = 131986;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 2;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 15705:
				itemDef.name = "<col=D45F4B>Infernal Chronicles(2)";
				itemDef.modelID = 131985;
				itemDef.maleEquip1 = 131986;
				itemDef.femaleEquip1 = 131986;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 2;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 15706:
				itemDef.name = "<col=D45F4B>Infernal Chronicles(3)";
				itemDef.modelID = 131985;
				itemDef.maleEquip1 = 131986;
				itemDef.femaleEquip1 = 131986;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				itemDef.element = 2;
				itemDef.modelZoom = 880;
				itemDef.rotation_x = 116;
				itemDef.rotation_y = 244;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -21;
				itemDef.translate_x = 1;
				break;
			case 6808:
				itemDef.name = "<col=AF70C3>Void Defender";
				itemDef.modelID = 132003;
				itemDef.maleEquip1 = 132003;
				itemDef.femaleEquip1 = 132003;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 4;
				itemDef.modelZoom = 785;
				itemDef.rotation_x = 289;
				itemDef.rotation_y = 753;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 35;
				itemDef.translate_x = -4;
				itemDef.maleWieldX = 5;
				itemDef.maleWieldY = 5;
				itemDef.maleWieldZ = 0;
				break;
			case 15707:
				itemDef.name = "<col=AF70C3>Void Defender(2)";
				itemDef.modelID = 132003;
				itemDef.maleEquip1 = 132003;
				itemDef.femaleEquip1 = 132003;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 4;
				itemDef.modelZoom = 785;
				itemDef.rotation_x = 289;
				itemDef.rotation_y = 753;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 35;
				itemDef.translate_x = -4;
				itemDef.maleWieldX = 5;
				itemDef.maleWieldY = 5;
				itemDef.maleWieldZ = 0;
				break;
			case 15708:
				itemDef.name = "<col=AF70C3>Void Defender(3)";
				itemDef.modelID = 132003;
				itemDef.maleEquip1 = 132003;
				itemDef.femaleEquip1 = 132003;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				itemDef.element = 4;
				itemDef.modelZoom = 785;
				itemDef.rotation_x = 289;
				itemDef.rotation_y = 753;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 35;
				itemDef.translate_x = -4;
				itemDef.maleWieldX = 5;
				itemDef.maleWieldY = 5;
				itemDef.maleWieldZ = 0;
				break;

			case 10256:
				itemDef.name = "<col=5FD857>Earth Crate";
				itemDef.modelID = 130177;
				itemDef.modelZoom = 2250;
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.element = 1;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 10260:
				itemDef.name = "<col=1097BF>Water Crate";
				itemDef.modelID = 130178;
				itemDef.modelZoom = 2250;
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.element = 3;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 10262:
				itemDef.name = "<col=D45F4B>Fire Crate";
				itemDef.modelID = 130179;
				itemDef.modelZoom = 2250;
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.element = 2;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 12830:
				itemDef.name = "<col=5FD857>Gaia Headpiece";
				itemDef.element = 1;
				itemDef.modelID = 130171;
				itemDef.maleEquip1 = 130171;
				itemDef.femaleEquip1 = 130171;
				itemDef.modelZoom = 1041;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 12832:
				itemDef.name = "<col=D45F4B>Lava Headpiece";
				itemDef.element = 2;
				itemDef.modelID = 130173;
				itemDef.maleEquip1 = 130173;
				itemDef.femaleEquip1 = 130173;
				itemDef.modelZoom = 1041;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 12831:
				itemDef.name = "<col=1097BF>Aqua Headpiece";
				itemDef.element = 3;
				itemDef.modelID = 130172;
				itemDef.maleEquip1 = 130172;
				itemDef.femaleEquip1 = 130172;
				itemDef.modelZoom = 1041;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;

			case 3503:
				itemDef.name = "@red@Corrupt Headpiece";
				itemDef.element = 9;
				itemDef.modelID = 131814;
				itemDef.maleEquip1 = 131814;
				itemDef.femaleEquip1 = 131814;
				itemDef.modelZoom = 1041;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 400;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 12834:
				itemDef.name = "<col=5FD857>Gaia Skull";
				itemDef.element = 1;
				itemDef.modelID = 130174;
				itemDef.maleEquip1 = 130174;
				itemDef.femaleEquip1 = 130174;
				itemDef.modelZoom = 752;
				itemDef.rotation_x = 425;
				itemDef.rotation_y = 480;
				itemDef.translate_x = 3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 12835:
				itemDef.name = "<col=1097BF>Aqua Skull";
				itemDef.element = 3;
				itemDef.modelID = 130175;
				itemDef.maleEquip1 = 130175;
				itemDef.femaleEquip1 = 130175;
				itemDef.modelZoom = 752;
				itemDef.rotation_x = 425;
				itemDef.rotation_y = 480;
				itemDef.translate_x = 3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 12836:
				itemDef.name = "<col=D45F4B>Lava Skull";
				itemDef.element = 2;
				itemDef.modelID = 130176;
				itemDef.maleEquip1 = 130176;
				itemDef.femaleEquip1 = 130176;
				itemDef.modelZoom = 752;
				itemDef.rotation_x = 425;
				itemDef.rotation_y = 480;
				itemDef.translate_x = 3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 3504:
				itemDef.name = "@red@Corrupt Skull";
				itemDef.element = 9;
				itemDef.modelID = 131815;
				itemDef.maleEquip1 = 131815;
				itemDef.femaleEquip1 = 131815;
				itemDef.modelZoom = 752;
				itemDef.rotation_x = 425;
				itemDef.rotation_y = 480;
				itemDef.translate_x = 3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 12838:
				itemDef.name = "Placeholderxx";
				break;

			case 7236:
				itemDef.name = "<col=5FD857>Nature Amulet";
				itemDef.modelID = 130159;
				itemDef.maleEquip1 = 130159;
				itemDef.femaleEquip1 = 130159;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 1;
				break;
			case 12468:
				itemDef.name = "<col=5FD857>Apprentice Amulet";
				itemDef.modelID = 132026;
				itemDef.maleEquip1 = 132026;
				itemDef.femaleEquip1 = 132026;
				itemDef.maleWieldY = 3;
				itemDef.maleWieldZ = 3;
				itemDef.modelZoom = 570;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 68;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 1;
				break;
			case 12825:
				itemDef.name = "<col=5FD857>Gaia Amulet(3)";
				itemDef.modelID = 130159;
				itemDef.maleEquip1 = 130159;
				itemDef.femaleEquip1 = 130159;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 1;
				itemDef.stackable = false;

				break;
			case 7238:
				itemDef.name = "<col=D45F4B>Hades Amulet";
				itemDef.modelID = 130161;
				itemDef.maleEquip1 = 130161;
				itemDef.femaleEquip1 = 130161;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 2;
				break;
			case 12826:
				itemDef.name = "<col=D45F4B>Amulet";
				itemDef.modelID = 130161;
				itemDef.maleEquip1 = 130161;
				itemDef.femaleEquip1 = 130161;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 2;
				break;
			case 12827:
				itemDef.name = "<col=D45F4B>Lava Amulet(3)";
				itemDef.modelID = 130161;
				itemDef.maleEquip1 = 130161;
				itemDef.femaleEquip1 = 130161;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 2;
				break;
			case 7241:
				itemDef.name = "<col=1097BF>Atlantis Amulet";
				itemDef.modelID = 130160;
				itemDef.maleEquip1 = 130160;
				itemDef.femaleEquip1 = 130160;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 3;
				break;
			case 12828:
				itemDef.name = "<col=1097BF>Amulet";
				itemDef.modelID = 130160;
				itemDef.maleEquip1 = 130160;
				itemDef.femaleEquip1 = 130160;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 3;
				break;
			case 12829:
				itemDef.name = "<col=1097BF>Aqua Amulet(3)";
				itemDef.modelID = 130160;
				itemDef.maleEquip1 = 130160;
				itemDef.femaleEquip1 = 130160;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 3;
				break;
			case 7245:
				itemDef.name = "<col=5FD857>Ring of Nature";
				itemDef.modelID = 130446;
				itemDef.modelZoom = 680;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 340;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 1;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 12452:
				itemDef.name = "<col=5FD857>Gaia Ring(2)";
				itemDef.modelID = 130167;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 186;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 1;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;

				break;
			case 12453:
				itemDef.name = "<col=5FD857>Gaia Ring(3)";
				itemDef.modelID = 130168;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 186;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 1;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;

				break;
			case 12454:
				itemDef.name = "<col=5FD857>Gaia Ring(4)";
				itemDef.stackable = false;
				break;
			case 12455:
				itemDef.name = "<col=5FD857>Gaia Ring(5)";
				break;
			case 7247:
				itemDef.name = "<col=D45F4B>Ring of Hades";
				itemDef.modelID = 130445;
				itemDef.modelZoom = 680;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 340;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 2;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 12456:
				itemDef.name = "<col=D45F4B>Lava Ring(2)";
				itemDef.modelID = 130166;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 186;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 2;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				break;
			case 12457:
				itemDef.name = "<col=D45F4B>Lava Ring(3)";
				itemDef.modelID = 130169;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 186;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 2;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				break;

			case 7249:
				itemDef.name = "<col=1097BF>Ring of Atlantis";
				itemDef.modelID = 130447;
				itemDef.modelZoom = 680;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 340;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 3;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 12488:
				itemDef.name = "<col=1097BF>Aqua Ring(2)";
				itemDef.modelID = 130165;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 186;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 3;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				break;
			case 12489:
				itemDef.name = "<col=1097BF>Aqua Ring(3))";
				itemDef.modelID = 130170;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 186;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 3;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				break;


			case 1528:
				itemDef.name = "<col=AF70C3>Void Amulet";
				itemDef.element = 4;
				itemDef.modelID = 131627;
				itemDef.maleEquip1 = 131627;
				itemDef.femaleEquip1 = 131627;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 1529:
				itemDef.name = "<col=AF70C3>Void Amulet(2)";
				itemDef.element = 4;
				itemDef.modelID = 131627;
				itemDef.maleEquip1 = 131627;
				itemDef.femaleEquip1 = 131627;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				break;
			case 1530:
				itemDef.name = "<col=AF70C3>Void Amulet(3)";
				itemDef.element = 4;
				itemDef.modelID = 131627;
				itemDef.maleEquip1 = 131627;
				itemDef.femaleEquip1 = 131627;
				itemDef.modelZoom = 465;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 75;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				break;
			case 1531:
				itemDef.name = "<col=AF70C3>Ring of Void";
				itemDef.element = 4;
				itemDef.modelID = 132028;
				itemDef.modelZoom = 860;
				itemDef.rotation_x = 1931;
				itemDef.rotation_y = 1660;
				itemDef.translate_x = 0;
				itemDef.translate_yz = -5;
				itemDef.rotation_z = 0;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 1532:
				itemDef.name = "<col=AF70C3>Void Ring(2)";
				itemDef.element = 4;
				itemDef.modelID = 131625;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 148;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				break;
			case 1533:
				itemDef.name = "<col=AF70C3>Void Ring(3)";
				itemDef.element = 4;
				itemDef.modelID = 131626;
				itemDef.modelZoom = 1027;
				itemDef.rotation_x = 78;
				itemDef.rotation_y = 148;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.stackable = false;
				break;


			case 4155:
				itemDef.name = "<col=AF70C3>Slayer Skull";
				itemDef.modelID = 64555;
				itemDef.actions = new String[]{"<col=AF70C3>Task Travel", null, null, "<col=AF70C3>Master", "Drop"};
				itemDef.modelZoom = 511;
				itemDef.translate_yz = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_x = -1;
				itemDef.rotation_x = 175;
				itemDef.rotation_y = 148;
				itemDef.scaleX = 64;
				itemDef.scaleY = 64;
				itemDef.scaleZ = 64;
				break;
			case 995:
				itemDef.name = "<col=AF70C3>Coins";
				itemDef.modelID = 132202;
				itemDef.modelZoom = 1600;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 539;
				itemDef.translate_x = 3;
				itemDef.stackAmounts = null;
				itemDef.stackIDs = null;//*
				break;
			case 10945:
				itemDef.name = "@cya@$1 Bond";
				itemDef.actions = new String[]{"@yel@Claim", null, "Claim all", null, "Drop"};
				itemDef.modelID = 130903;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 490;
				itemDef.translate_yz = 17;
				itemDef.translate_x = 3;
				itemDef.modelZoom = 2600;
				break;
			case 10946:
				itemDef.name = "@gre@$5 Bond";
				itemDef.actions = new String[]{"@yel@Claim", null, "Claim all", null, "Drop"};
				itemDef.modelID = 130115;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 490;
				itemDef.translate_yz = 17;
				itemDef.translate_x = 3;
				itemDef.modelZoom = 2600;
				break;
			case 23057:
				itemDef.name = "@red@$10 Bond";
				itemDef.actions = new String[]{"@yel@Claim", null, "Claim all", null, "Drop"};
				itemDef.modelID = 130116;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 490;
				itemDef.translate_yz = 17;
				itemDef.translate_x = 3;
				itemDef.modelZoom = 2600;
				break;
			case 23058:
				itemDef.name = "@cya@$25 Bond";
				itemDef.actions = new String[]{"@yel@Claim", null, "Claim all", null, "Drop"};
				itemDef.modelID = 130117;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 490;
				itemDef.translate_yz = 17;
				itemDef.translate_x = 3;
				itemDef.modelZoom = 2600;
				itemDef.stackable = true;
				break;
			case 23059:
				itemDef.name = "@or2@$100 Bond";
				itemDef.actions = new String[]{"@yel@Claim", null, "Claim all", null, "Drop"};
				itemDef.modelID = 130118;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 490;
				itemDef.translate_yz = 17;
				itemDef.translate_x = 3;
				itemDef.modelZoom = 2600;
				itemDef.stackable = true;
				break;
			case 23060:
				itemDef.name = "@mag@$250 Bond";
				itemDef.actions = new String[]{"@yel@Claim", null, "Claim all", null, "Drop"};
				itemDef.modelID = 130119;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 490;
				itemDef.translate_yz = 17;
				itemDef.translate_x = 3;
				itemDef.modelZoom = 2600;
				itemDef.stackable = true;
				break;
			case 3600:
				itemDef.name = "<col=D45F4B>Apprentice Kit";
				itemDef.modelID = 120100;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 4500;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.rotation_x = 100;
				itemDef.rotation_y = 150;
				break;
			case 3595:
				itemDef.name = "<col=5FD857>Apprentice Kit";
				itemDef.modelID = 120101;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 4500;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.rotation_x = 100;
				itemDef.rotation_y = 150;
				break;
			case 3601:
				itemDef.name = "<col=1097BF>Apprentice Kit";
				itemDef.modelID = 120102;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelZoom = 4500;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.rotation_x = 100;
				itemDef.rotation_y = 150;
				break;

			case 22004:
				itemDef.copyItem(15496);
				itemDef.name = "Boss Slayer Helmet [5]";
				itemDef.rdc = 800;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 22005:
				itemDef.copyItem(18818);
				itemDef.name = "Infernal ring";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 22006: // Deathtouch darts
				itemDef.copyItem(11230);
				itemDef.name = "Deathtouch Darts";
				itemDef.newModelColors = new int[]{5409, 920, 914, 929, 10452, 10293};
				itemDef.originalModelColors = new int[]{943, 3866, 914, 3866, 943, 943};
				break;
			//case 4278:
			//case 4279:
			case 2706:
				itemDef.name = "@yel@Dream Ticket";
				itemDef.actions = new String[]{"Activate", null, "Last Monster", null, "Drop"};
				itemDef.stackable = false;
				itemDef.modelID = 3663;
				itemDef.modelZoom = 1344;
				itemDef.rotation_x = 319;
				itemDef.rotation_y = 515;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 1;
				break;
			case 2708:
				itemDef.name = "@cya@Dream Ticket(u)";
				itemDef.actions = new String[]{"Activate", null, "Last Monster", null, "Drop"};
				itemDef.stackable = false;
				itemDef.modelID = 3663;
				itemDef.modelZoom = 1344;
				itemDef.rotation_x = 319;
				itemDef.rotation_y = 515;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 1;
				break;
			case 12855:
				itemDef.name = "@yel@Upgrade Tokens";
				itemDef.modelID = 77799;
				itemDef.modelZoom = 3000;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 23120:
				itemDef.copyItem(23118);
				itemDef.name = "Alpha Potion";
				itemDef.modelID = 103027;
				itemDef.modelZoom = 1075;
				itemDef.actions = new String[]{"Drink", null, null, null, "Drop"};
				break;
			case 534:
				itemDef.name = "@gre@Easy Bones";
				itemDef.actions = new String[]{"Bury", null, "Crush", null, "Drop"};
				itemDef.modelID = 65285;
				itemDef.modelZoom = 1800;
				break;
			case 7305:
				itemDef.name = "@red@Medium Bones";
				itemDef.modelID = 65285;
				itemDef.modelZoom = 1800;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_x = 648;
				itemDef.rotation_y = 216;
				itemDef.actions = new String[]{"Bury", null, "Crush", null, "Drop"};

				break;
			case 7306:
				itemDef.name = "@or2@Elite Bones";
				itemDef.modelID = 65285;
				itemDef.modelZoom = 1800;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_x = 648;
				itemDef.rotation_y = 216;
				itemDef.actions = new String[]{"Bury", null, "Crush", null, "Drop"};
				break;
			case 7307:
				itemDef.name = "@cya@Master Bones";
				itemDef.modelID = 65285;
				itemDef.modelZoom = 1800;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_x = 648;
				itemDef.rotation_y = 216;
				itemDef.actions = new String[]{"Bury", null, "Crush", null, "Drop"};
				break;
			case 7308:
				itemDef.name = "@red@Corrupt Bones";
				itemDef.modelID = 65285;
				itemDef.modelZoom = 1800;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_x = 648;
				itemDef.rotation_y = 216;
				itemDef.actions = new String[]{"Bury", null, "Crush", null, "Drop"};
				break;
			case 7309:
				itemDef.name = "<col=AF70C3>Spectral Bones";
				itemDef.modelID = 65285;
				itemDef.modelZoom = 1800;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_x = 648;
				itemDef.rotation_y = 216;
				itemDef.actions = new String[]{"Bury", null, "Crush", null, "Drop"};
				break;

			case 16905:
				itemDef.name = "<col=AF70C3>Amulet Upgrade Frag";
				itemDef.modelID = 130901;
				itemDef.modelZoom = 1233;
				itemDef.translate_yz = 5;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 563;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 16906:
				itemDef.name = "@cya@Cape Upgrade Frag";
				itemDef.modelID = 130962;
				itemDef.modelZoom = 1500;
				itemDef.translate_yz = 5;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_x = 200;
				itemDef.rotation_y = 563;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 5585:
				itemDef.name = "<col=AF70C3>Rift Key";
				itemDef.modelID = 130453;
				itemDef.modelZoom = 1259;
				itemDef.rotation_x = 1408;
				itemDef.rotation_y = 462;
				itemDef.rotation_z = 0;
				itemDef.translate_x = -5;
				itemDef.translate_yz = 2;
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				break;
			case 3512:
				itemDef.name = "@red@Corrupt Slayer Key";
				itemDef.modelID = 131820;
				itemDef.modelZoom = 1259;
				itemDef.rotation_x = 1408;
				itemDef.rotation_y = 462;
				itemDef.rotation_z = 0;
				itemDef.translate_x = -5;
				itemDef.translate_yz = 2;
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				break;
			case 19062:
				itemDef.name = "@yel@Monster Essence";
				itemDef.modelZoom = 1259;
				itemDef.rotation_x = 522;
				itemDef.rotation_y = 907;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 2;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 3576:
				itemDef.name = "@red@Slayer Essence";
				itemDef.modelZoom = 1259;
				itemDef.rotation_x = 657;
				itemDef.rotation_y = 907;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 2;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 11052:
				itemDef.name = "<col=5FD857>Earth Essence";
				itemDef.modelZoom = 694;
				itemDef.element = 1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 11054:
				itemDef.name = "<col=D45F4B>Fire Essence";
				itemDef.modelZoom = 1500;
				itemDef.element = 2;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 11056:
				itemDef.name = "<col=1097BF>Water Essence";
				itemDef.modelZoom = 967;
				itemDef.element = 3;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 2699:
				itemDef.name = "@cya@Enchanted Essence";
				itemDef.modelID = 41528;
				itemDef.modelZoom = 1665;
				itemDef.rotation_x = 552;
				itemDef.rotation_y = 267;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.element = 8;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 2554:
				itemDef.name = "@cya@Small Exp Gem";
				itemDef.modelZoom = 1100;
				itemDef.modelID = 131842;
				itemDef.actions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 372;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				break;
			case 2556:
				itemDef.name = "@gre@Companion Exp Gem";
				itemDef.modelZoom = 850;
				itemDef.modelID = 131843;
				itemDef.actions = new String[]{"Claim", null, null, null, "Drop"};
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 372;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				break;
			case 17819:
				itemDef.name = "@cya@Enchanted Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 131841;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 7780:
				itemDef.name = "<col=AF70C3>Soulstone Bar";
				itemDef.modelID = 131850;
				itemDef.modelZoom = 867;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 7782:
				itemDef.name = "<col=AF70C3>Soulstone Hilt";
				itemDef.modelID = 131851;
				itemDef.modelZoom = 3873;
				itemDef.rotation_x = 28;
				itemDef.rotation_y = 552;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -14;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 7783:
				itemDef.name = "<col=AF70C3>Necrotic Energy";
				itemDef.modelID = 131849;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 7784:
				itemDef.name = "<col=AF70C3>Soulstone Blade(1)";
				itemDef.stackable = false;
				itemDef.modelID = 131853;
				itemDef.maleEquip1 = 131853;
				itemDef.femaleEquip1 = 131853;
				itemDef.modelZoom = 857;
				itemDef.rotation_x = 270;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 57;
				itemDef.translate_yz = 33;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 7785:
				itemDef.name = "<col=AF70C3>Soulstone Blade(2)";
				itemDef.stackable = false;
				itemDef.modelID = 131854;
				itemDef.maleEquip1 = 131854;
				itemDef.femaleEquip1 = 131854;
				itemDef.modelZoom = 857;
				itemDef.rotation_x = 270;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 57;
				itemDef.translate_yz = 33;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 7786:
				itemDef.name = "<col=AF70C3>Soulstone Blade(3)";
				itemDef.stackable = false;
				itemDef.modelID = 131855;
				itemDef.maleEquip1 = 131855;
				itemDef.femaleEquip1 = 131855;
				itemDef.modelZoom = 857;
				itemDef.rotation_x = 270;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 57;
				itemDef.translate_yz = 33;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 311:
				itemDef.name = "Harpoon";
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 373:
				itemDef.setDefaults();
				itemDef.name = "Raw SoulFish";
				itemDef.modelID = 131857;
				itemDef.modelZoom = 1270;
				itemDef.rotation_x = 1928;
				itemDef.rotation_y = 400;
				itemDef.translate_x = -3;
				itemDef.translate_yz = 18;
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 375:
				itemDef.setDefaults();
				itemDef.name = "<col=AF70C3>SoulFish";
				itemDef.modelID = 131856;
				itemDef.modelZoom = 1270;
				itemDef.rotation_x = 1928;
				itemDef.rotation_y = 400;
				itemDef.translate_x = -3;
				itemDef.translate_yz = 18;
				itemDef.actions = new String[]{"Eat", null, null, null, null};
				break;
			case 7766:
				itemDef.name = "<col=AF70C3>Soulstone Ore";
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelID = 131844;
				itemDef.modelZoom = 2000;
				itemDef.rotation_x = 1622;
				itemDef.rotation_y = 463;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 7788:
				itemDef.name = "<4D7099>Blurite Ore";
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelID = 131845;
				itemDef.modelZoom = 2000;
				itemDef.rotation_x = 1622;
				itemDef.rotation_y = 463;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 7747:
				itemDef.name = "<col=AF70C3>Soulstone Logs";
				itemDef.modelID = 131846;
				itemDef.modelZoom = 1180;
				itemDef.rotation_x = 1852;
				itemDef.rotation_y = 120;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -7;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 8044:
				itemDef.name = "<col=AF70C3>Soulstone Pickaxe";
				itemDef.modelID = 131847;
				itemDef.maleEquip1 = 131847;
				itemDef.femaleEquip1 = 131847;
				itemDef.modelZoom = 1245;
				itemDef.rotation_x = 1332;
				itemDef.rotation_y = 231;
				itemDef.translate_x = -45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 192;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 8046:
				itemDef.name = "<col=AF70C3>Soulstone Axe";
				itemDef.maleWieldX = -6;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -2;
				itemDef.modelID = 131848;
				itemDef.maleEquip1 = 131848;
				itemDef.femaleEquip1 = 131848;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 251;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 47;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;

			case 15386:
				itemDef.name = "@cya@Demonic Horn";
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 18678:
				itemDef.name = "Wolven Fang";
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 7871:
				itemDef.name = "Titanic Bone";
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 17821:
				itemDef.name = "@cya@Mystic Mushroom";
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 18681:
				itemDef.name = "Enchanted Wand";
				itemDef.actions = new String[]{"Summon", null, "Call", "Menu", null};
				break;

			case 6749:
				itemDef.name = "Damage Tome";
				itemDef.actions = new String[]{"Summon", null, "Call", "Menu", null};
				break;
			case 13151:
				itemDef.name = "Droprate Tome";
				itemDef.actions = new String[]{"Summon", null, "Call", "Menu", null};
				break;
			case 19705:
				itemDef.name = "Experience Tome";
				itemDef.actions = new String[]{"Summon", null, "Call", "Menu", null};
				break;
			case 17407:
				itemDef.name = "@red@Corrupt Mushroom";
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 20104:
				itemDef.name = "@gre@Elemental Key(1)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 130664;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 20105:
				itemDef.name = "@yel@Elemental Key(2)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 130665;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 20106:
				itemDef.name = "@or2@Elemental Key(3)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 130666;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 20107:
				itemDef.name = "@red@Elemental Key(4)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 130667;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 20108:
				itemDef.name = "@red@Elemental Key(5)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 130753;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 20109:
				itemDef.name = "@red@Elemental Key(6)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 130754;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 20111:
				itemDef.name = "@blu@Elemental Key(7)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 131537;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 20112:
				itemDef.name = "@cya@Elemental Key(8)";
				itemDef.actions = new String[]{null, null, null, "Rewards", null};
				itemDef.modelID = 131538;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 1350;
				itemDef.rotation_y = 450;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				break;
			case 18015:
				itemDef.name = "Essence Pouch";
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Check", null, "Empty(t1)", "Empty(t2)", "Drop"};
				break;
			case 19622:
				itemDef.name = "Rune Pouch";
				itemDef.stackable = false;
				itemDef.modelID = 102093;
				itemDef.modelZoom = 850;
				itemDef.rotation_x = 512;
				itemDef.rotation_y = 475;
				itemDef.rotation_z = 13;
				itemDef.translate_yz = 1;
				itemDef.translate_x = -13;
				itemDef.actions = new String[]{"Check", null, "Empty", null, "Fill"};
				break;
			case 1308:
				itemDef.name = "Salt Pouch";
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Check", null, "Empty", null, "Drop"};
				itemDef.modelID = 2678;
				itemDef.modelZoom = 550;
				itemDef.rotation_x = 1844;
				itemDef.rotation_y = 116;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -4;
				itemDef.translate_x = -2;
				break;
			case 357:
				itemDef.name = "<col=AF70C3>Divine Salt";
				itemDef.modelID = 132062;
				itemDef.modelZoom = 1300;
				itemDef.rotation_x = 414;
				itemDef.rotation_y = 133;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.hintTier = 1;
				itemDef.stackable = false;
				itemDef.hintTier = 1;

				break;
			case 358:
				itemDef.name = "<col=AF70C3>Divine Potion";
				itemDef.modelID = 132063;
				itemDef.actions = new String[]{"Drink", null, null, null, null};
				itemDef.modelZoom = 670;
				itemDef.rotation_x = 1996;
				itemDef.rotation_y = 84;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 1;
				itemDef.hintTier = 1;

				break;
			case 2688:
				itemDef.name = "@gre@25% Droprate Boost";
				itemDef.description = "Boosts your current Droprate by 25% of your total droprate! ".getBytes();
				itemDef.modelID = 131695;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				break;
			case 2689:
				itemDef.name = "@red@50% Maxhit Boost";
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				itemDef.modelID = 131696;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 15670:
				itemDef.name = "@red@Bond Casket";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130770;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = 1;
				break;
			case 19118:
				itemDef.name = "<col=AF70C3>Void Crate ";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 131661;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = 1;
				break;
			case 15667:
				itemDef.name = "@yel@Box of Plunders";
				itemDef.actions = new String[]{"Open", null, "Open 100", null, null};
				itemDef.modelID = 130777;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 6754:
				itemDef.name = "@gre@Frenzy Key";
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 15671:
				itemDef.name = "@yel@Bond Casket(e)";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130771;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = 1;
				break;
			case 15668:
				itemDef.name = "@mag@Box of Treasures";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 130774;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 23169:
				itemDef.name = "@yel@ Plutus's Pot Of Greed";
				itemDef.modelID = 132201;
				itemDef.modelZoom = 1000;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.element = 16;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 6466:
				itemDef.name = "@or1@Beast Essence";
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};

				break;
			case 10258:
				itemDef.name = "@yel@Coin Casket";
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				itemDef.modelID = 130773;
				itemDef.modelZoom = 3319;
				itemDef.rotation_x = 135;
				itemDef.rotation_y = 57;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -22;
				itemDef.translate_x = 1;
				break;
			case 15669:
				itemDef.name = "@cya@Box of Blessings";
				itemDef.actions = new String[]{"Open", null, null, "Rewards", null};
				itemDef.modelID = 130772;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 76;
				itemDef.rotation_y = 86;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -17;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;


			case 15720:
				itemDef.name = "@cya@Blue Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130820;
				itemDef.maleEquip1 = 130820;
				itemDef.femaleEquip1 = 130820;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15721:
				itemDef.name = "@red@Red Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130821;
				itemDef.maleEquip1 = 130821;
				itemDef.femaleEquip1 = 130821;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15722:
				itemDef.name = "@yel@Yellow Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130822;
				itemDef.maleEquip1 = 130822;
				itemDef.femaleEquip1 = 130822;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15723:
				itemDef.name = "<col=AF70C3>Purple Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130823;
				itemDef.maleEquip1 = 130823;
				itemDef.femaleEquip1 = 130823;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15724:
				itemDef.name = "@gre@Lime Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130920;
				itemDef.maleEquip1 = 130920;
				itemDef.femaleEquip1 = 130920;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15725:
				itemDef.name = "@cya@Fabled Blue Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130825;
				itemDef.maleEquip1 = 130825;
				itemDef.femaleEquip1 = 130825;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 15726:
				itemDef.name = "@gre@Fabled Green Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130826;
				itemDef.maleEquip1 = 130826;
				itemDef.femaleEquip1 = 130826;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 15727:
				itemDef.name = "<col=AF70C3>Fabled Purple Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130827;
				itemDef.maleEquip1 = 130827;
				itemDef.femaleEquip1 = 130827;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15728:
				itemDef.name = "@yel@Fabled Yellow Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130828;
				itemDef.maleEquip1 = 130828;
				itemDef.femaleEquip1 = 130828;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 15741:
				itemDef.name = "@mag@Fabled Pink Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130921;
				itemDef.maleEquip1 = 130921;
				itemDef.femaleEquip1 = 130921;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 15742:
				itemDef.name = "@cya@Exotic Rage Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130840;
				itemDef.maleEquip1 = 130840;
				itemDef.femaleEquip1 = 130840;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 15729:
				itemDef.name = "@cya@Exotic Blue Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130830;
				itemDef.maleEquip1 = 130830;
				itemDef.femaleEquip1 = 130830;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 15730:
				itemDef.name = "<col=D45F4B>Exotic Fire Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130831;
				itemDef.maleEquip1 = 130831;
				itemDef.femaleEquip1 = 130831;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15731:
				itemDef.name = "@gre@Exotic Green Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130832;
				itemDef.maleEquip1 = 130832;
				itemDef.femaleEquip1 = 130832;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15732:
				itemDef.name = "<col=AF70C3>Exotic Purple Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130833;
				itemDef.maleEquip1 = 130833;
				itemDef.femaleEquip1 = 130833;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15733:
				itemDef.name = "@red@Mythic Red Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130834;
				itemDef.maleEquip1 = 130834;
				itemDef.femaleEquip1 = 130834;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15734:
				itemDef.name = "<col=AF70C3>Mythic Galaxy Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130835;
				itemDef.maleEquip1 = 130835;
				itemDef.femaleEquip1 = 130835;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15735:
				itemDef.name = "<col=AF70C3>Mythic Void Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130836;
				itemDef.maleEquip1 = 130836;
				itemDef.femaleEquip1 = 130836;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15736:
				itemDef.name = "@mag@Mythic Rainbow Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130837;
				itemDef.maleEquip1 = 130837;
				itemDef.femaleEquip1 = 130837;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15737:
				itemDef.name = "@whi@Mythic Spotted Phat";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130839;
				itemDef.maleEquip1 = 130839;
				itemDef.femaleEquip1 = 130839;
				itemDef.modelZoom = 300;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 96;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15738:
				itemDef.name = "Hat 19";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};

				itemDef.modelID = 0;
				itemDef.modelZoom = 0;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15739:
				itemDef.name = "@cya@Blue Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130841;
				itemDef.maleEquip1 = 130841;
				itemDef.femaleEquip1 = 130841;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;
			case 15740:
				itemDef.name = "@gre@Green Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130842;
				itemDef.maleEquip1 = 130842;
				itemDef.femaleEquip1 = 130842;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15743:
				itemDef.name = "@red@Red Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130843;
				itemDef.maleEquip1 = 130843;
				itemDef.femaleEquip1 = 130843;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15744:
				itemDef.name = "@yel@Yellow Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130844;
				itemDef.maleEquip1 = 130844;
				itemDef.femaleEquip1 = 130844;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15760:
				itemDef.name = "@cya@Cyan Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130918;
				itemDef.maleEquip1 = 130918;
				itemDef.femaleEquip1 = 130918;
				itemDef.modelZoom = 660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15745:
				itemDef.name = "@cya@Fabled Blue Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130845;
				itemDef.maleEquip1 = 130845;
				itemDef.femaleEquip1 = 130845;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15746:
				itemDef.name = "@gre@Fabled Green Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130846;
				itemDef.maleEquip1 = 130846;
				itemDef.femaleEquip1 = 130846;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15747:
				itemDef.name = "@mag@Fabled Pink Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130847;
				itemDef.maleEquip1 = 130847;
				itemDef.femaleEquip1 = 130847;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15748:
				itemDef.name = "<col=AF70C3>Fabled Purple Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130848;
				itemDef.maleEquip1 = 130848;
				itemDef.femaleEquip1 = 130848;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15749:
				itemDef.name = "@yel@Fabled Yellow Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130849;
				itemDef.maleEquip1 = 130849;
				itemDef.femaleEquip1 = 130849;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15750:
				itemDef.name = "<col=D45F4B>Exotic Fire Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130850;
				itemDef.maleEquip1 = 130850;
				itemDef.femaleEquip1 = 130850;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15751:
				itemDef.name = "<col=AF70C3>Exotic Purple Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130852;
				itemDef.maleEquip1 = 130852;
				itemDef.femaleEquip1 = 130852;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15752:
				itemDef.name = "@red@Exotic Red Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130880;
				itemDef.maleEquip1 = 130880;
				itemDef.femaleEquip1 = 130880;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15753:
				itemDef.name = "@gre@Exotic Forest Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130874;
				itemDef.maleEquip1 = 130874;
				itemDef.femaleEquip1 = 130874;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15754:
				itemDef.name = "@cya@Exotic Sky Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130875;
				itemDef.maleEquip1 = 130875;
				itemDef.femaleEquip1 = 130875;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15755:
				itemDef.name = "@mag@Mythic Rainbow Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130851;
				itemDef.maleEquip1 = 130851;
				itemDef.femaleEquip1 = 130851;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15756:
				itemDef.name = "<col=AF70C3>Mythic Galaxy Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130876;
				itemDef.maleEquip1 = 130876;
				itemDef.femaleEquip1 = 130876;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15757:
				itemDef.name = "<col=AF70C3>Mythic Void Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130877;
				itemDef.maleEquip1 = 130877;
				itemDef.femaleEquip1 = 130877;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15758:
				itemDef.name = "@whi@Mythic Spotted Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130878;
				itemDef.maleEquip1 = 130878;
				itemDef.femaleEquip1 = 130878;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;

			case 15759:
				itemDef.name = "<col=AF70C3>Mythic Purple Mask";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130879;
				itemDef.maleEquip1 = 130879;
				itemDef.femaleEquip1 = 130879;
				itemDef.modelZoom =660;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 79;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				break;




			case 15770:
				itemDef.name = "@cya@Blue Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130853;
				itemDef.maleEquip1 = 130853;
				itemDef.femaleEquip1 = 130853;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15771:
				itemDef.name = "@red@Red Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130854;
				itemDef.maleEquip1 = 130854;
				itemDef.femaleEquip1 = 130854;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15772:
				itemDef.name = "<col=AF70C3>Purple Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130855;
				itemDef.maleEquip1 = 130855;
				itemDef.femaleEquip1 = 130855;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15773:
				itemDef.name = "@yel@Yellow Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130856;
				itemDef.maleEquip1 = 130856;
				itemDef.femaleEquip1 = 130856;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15774:
				itemDef.name = "@gre@Green Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130857;
				itemDef.maleEquip1 = 130857;
				itemDef.femaleEquip1 = 130857;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;



			case 15776:
				itemDef.name = "@gre@Fabled Green Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130858;
				itemDef.maleEquip1 = 130858;
				itemDef.femaleEquip1 = 130858;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;

			case 15777:
				itemDef.name = "<col=AF70C3>Fabled Purple Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130859;
				itemDef.maleEquip1 = 130859;
				itemDef.femaleEquip1 = 130859;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;

			case 15778:
				itemDef.name = "@cya@Fabled Sky Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130860;
				itemDef.maleEquip1 = 130860;
				itemDef.femaleEquip1 = 130860;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;

			case 15779:
				itemDef.name = "@yel@Fabled Yellow Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130861;
				itemDef.maleEquip1 = 130861;
				itemDef.femaleEquip1 = 130861;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;

			case 15780:
				itemDef.name = "@mag@Fabled Pink Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130862;
				itemDef.maleEquip1 = 130862;
				itemDef.femaleEquip1 = 130862;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;




			case 15781:
				itemDef.name = "@cya@Exotic Blue Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130863;
				itemDef.maleEquip1 = 130863;
				itemDef.femaleEquip1 = 130863;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15782:
				itemDef.name = "@gre@Exotic Green Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130864;
				itemDef.maleEquip1 = 130864;
				itemDef.femaleEquip1 = 130864;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15783:
				itemDef.name = "@red@Exotic Red Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130865;
				itemDef.maleEquip1 = 130865;
				itemDef.femaleEquip1 = 130865;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;

			case 15784:
				itemDef.name = "<col=AF70C3>Exotic Purple Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130873;
				itemDef.maleEquip1 = 130873;
				itemDef.femaleEquip1 = 130873;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;

			case 15785:
				itemDef.name = "<col=AF70C3>Exotic Void Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130867;
				itemDef.maleEquip1 = 130867;
				itemDef.femaleEquip1 = 130867;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;




			case 15786:
				itemDef.name = "<col=D45F4B>Mythic Fire Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130868;
				itemDef.maleEquip1 = 130868;
				itemDef.femaleEquip1 = 130868;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15787:
				itemDef.name = "<col=AF70C3>Mythic Galaxy Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130869;
				itemDef.maleEquip1 = 130869;
				itemDef.femaleEquip1 = 130869;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15788:
				itemDef.name = "@mag@Mythic Rainbow Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130870;
				itemDef.maleEquip1 = 130870;
				itemDef.femaleEquip1 = 130870;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15791:
				itemDef.name = "@whi@Mythic Spotted Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130871;
				itemDef.maleEquip1 = 130871;
				itemDef.femaleEquip1 = 130871;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;
			case 15790:
				itemDef.name = "<col=AF70C3>Mythic Athens Santa";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 130872;
				itemDef.maleEquip1 = 130872;
				itemDef.femaleEquip1 = 130872;
				itemDef.modelZoom =471;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 124;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				break;

			//IDS 1300-2000 FREE FOR NEW ITEMS
			case 18979:
				itemDef.name = "@gre@Protection Gem";
				itemDef.modelID = 130907;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 372;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				break;
			case 2617:
				itemDef.name = "@yel@Skill Point";
				itemDef.modelZoom = 1300;
				itemDef.modelID = 131677;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 372;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.element = 15;

				break;
			case 2620:
				itemDef.name = "Skill Tree Reset";
				itemDef.modelZoom = 1100;
				itemDef.modelID = 56773;
				itemDef.actions = new String[]{"Reset", null, null, null, "Drop"};
				itemDef.rotation_x = 1766;
				itemDef.rotation_y = 183;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 33;
				itemDef.translate_x = 17;
				itemDef.stackable = false;
				break;
			case 744:
				itemDef.name = "Candy Heart(1)";
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 745:
				itemDef.name = "Candy Heart(2)";
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 2619:
				itemDef.name = "@gre@Bridge Gem";
				itemDef.modelZoom = 1200;
				itemDef.modelID = 131678;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 372;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				break;
			case 1300:
				itemDef.name = "@cya@Empowered Heart";
				itemDef.modelID = 130905;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 1387;
				itemDef.rotation_y = 359;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Heal", null, "Upgrade", null, "Drop"};
				break;

			case 1301:
				itemDef.name = "@yel@Enraged Heart";
				itemDef.modelID = 130906;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 1387;
				itemDef.rotation_y = 359;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Heal", null, null, null, "Drop"};
				break;

			case 1302:
				itemDef.name = "@gre@Beginner Slayer Key";
				itemDef.modelID = 130908;
				itemDef.modelZoom = 1326;
				itemDef.rotation_x = 201;
				itemDef.rotation_y = 552;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 2;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, "Rewards", "Drop"};
				break;
			case 1303:
				itemDef.name = "@cya@Medium Slayer Key";
				itemDef.modelID = 130909;
				itemDef.modelZoom = 1326;
				itemDef.rotation_x = 201;
				itemDef.rotation_y = 552;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 2;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, "Rewards", "Drop"};
				break;
			case 1304:
				itemDef.name = "@red@Elite Slayer Key";
				itemDef.modelID = 130910;
				itemDef.modelZoom = 1326;
				itemDef.rotation_x = 201;
				itemDef.rotation_y = 552;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 2;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, "Rewards", "Drop"};
				break;
			case 1305:
				itemDef.name = "<col=AF70C3>Beast Key";
				itemDef.modelID = 130911;
				itemDef.modelZoom = 1326;
				itemDef.rotation_x = 201;
				itemDef.rotation_y = 552;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 2;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, "Rewards", "Drop"};
				break;
			case 1306:
				itemDef.name = "@or2@Slayer Casket";
				itemDef.modelID = 130912;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 1307:
				itemDef.name = "@red@Slayer Casket(u)";
				itemDef.modelID = 130913;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 298:
				itemDef.name = "<col=AF70C3>Fallen Star";
				itemDef.modelID = 132152;
				itemDef.modelZoom = 476;
				itemDef.rotation_x = 77;
				itemDef.rotation_y = 347;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -10;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Salvage"};
				break;
			case 300:
				itemDef.name = "<col=AF70C3>Beast Casket";
				itemDef.modelID = 132150;
				itemDef.modelZoom = 2080;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 154;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 301:
				itemDef.name = "<col=AF70C3>Beast Casket(u)";
				itemDef.modelID = 132151;
				itemDef.modelZoom = 2080;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 154;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 1310:
				itemDef.name = "@red@Preset Addon";
				itemDef.modelID = 130914;
				itemDef.modelZoom = 622;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 502;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Claim", null, null, null, "Drop"};
				break;

			case 1315:
				itemDef.name = "@red@Infinite Prayer Unlock";
				itemDef.modelID = 130914;
				itemDef.modelZoom = 622;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 502;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.rotation_z = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Claim", null, null, null, "Drop"};
				break;

			case 1321:
				itemDef.name = "@red@Fury Potion";
				itemDef.modelID = 130990;
				itemDef.modelZoom = 775;
				itemDef.actions = new String[]{"Drink", null, null, null, "Drop"};
				itemDef.element = 1;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.element = 9;
				itemDef.hintTier = 1;
				break;
			case 1323:
				itemDef.name = "<col=AF70C3>Rage Potion";
				itemDef.modelID = 130991;
				itemDef.modelZoom = 775;
				itemDef.actions = new String[]{"Drink", null, null, null, "Drop"};
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.element = 4;
				itemDef.hintTier = 1;
				break;

			//NEW SKILLING WOODCUTTING
			case 1400:
				itemDef.name = "<col=B37E83>Vorpal Logs";
				itemDef.modelID = 131007;
				itemDef.modelZoom = 1180;
				itemDef.rotation_x = 1852;
				itemDef.rotation_y = 120;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -7;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1401:
				itemDef.name = "<col=B73C36>Bloodstained Logs";
				itemDef.modelID = 131005;
				itemDef.modelZoom = 1180;
				itemDef.rotation_x = 1852;
				itemDef.rotation_y = 120;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -7;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1402:
				itemDef.name = "<col=3F8C79>Symbiotic Logs";
				itemDef.modelID = 131004;
				itemDef.modelZoom = 1180;
				itemDef.rotation_x = 1852;
				itemDef.rotation_y = 120;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -7;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1403:
				itemDef.name = "<col=7C44B7>Nether Logs";
				itemDef.modelID = 131006;
				itemDef.modelZoom = 1180;
				itemDef.rotation_x = 1852;
				itemDef.rotation_y = 120;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -7;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1404:
				itemDef.name = "<col=B37E83>Vorpal Shaft";
				itemDef.modelID = 131014;
				itemDef.modelZoom = 1220;
				itemDef.rotation_x = 100;
				itemDef.rotation_y = 216;
				itemDef.translate_x = 6;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -29;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1405:
				itemDef.name = "<col=B73C36>Bloodstained Shaft";
				itemDef.modelID = 131024;
				itemDef.modelZoom = 1220;
				itemDef.rotation_x = 100;
				itemDef.rotation_y = 216;
				itemDef.translate_x = 6;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -29;
				itemDef.stackable = true;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1406:
				itemDef.name = "<col=3F8C79>Symbiotic Shaft";
				itemDef.modelID = 131009;
				itemDef.modelZoom = 1220;
				itemDef.rotation_x = 100;
				itemDef.rotation_y = 216;
				itemDef.translate_x = 6;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -29;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1407:
				itemDef.name = "<col=7C44B7>Nether Shaft";
				itemDef.modelID = 131019;
				itemDef.modelZoom = 1220;
				itemDef.rotation_x = 100;
				itemDef.rotation_y = 216;
				itemDef.translate_x = 6;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -29;
				itemDef.stackable = true;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;

			//NEW SKILLING MINING
			case 1408:
				itemDef.name = "<col=B37E83>Vorpal Scraps";
				itemDef.modelID = 131122;
				itemDef.modelZoom = 2000;
				itemDef.rotation_x = 1622;
				itemDef.rotation_y = 463;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1409:
				itemDef.name = "<col=B73C36>Bloodstained Scraps";
				itemDef.modelID = 131124;
				itemDef.modelZoom = 2000;
				itemDef.rotation_x = 1622;
				itemDef.rotation_y = 463;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1410:
				itemDef.name = "<col=3F8C79>Symbiotic Scraps";
				itemDef.modelID = 131123;
				itemDef.modelZoom = 2000;
				itemDef.rotation_x = 1622;
				itemDef.rotation_y = 463;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1411:
				itemDef.name = "<col=7C44B7>Nether Scraps";
				itemDef.modelID = 131121;
				itemDef.modelZoom = 2000;
				itemDef.rotation_x = 1622;
				itemDef.rotation_y = 463;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1412:
				itemDef.name = "<col=B37E83>Vorpal Ingot";
				itemDef.modelID = 131118;
				itemDef.modelZoom = 867;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1413:
				itemDef.name = "<col=B73C36>Bloodstained Ingot";
				itemDef.modelID = 131120;
				itemDef.modelZoom = 867;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1414:
				itemDef.name = "<col=3F8C79>Symbiotic Ingot";
				itemDef.modelID = 131119;
				itemDef.modelZoom = 867;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1415:
				itemDef.name = "<col=7C44B7>Nether Ingot";
				itemDef.modelID = 131117;
				itemDef.modelZoom = 867;
				itemDef.rotation_x = 57;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1416:
				itemDef.name = "<col=B37E83>Vorpal Arrowhead";
				itemDef.modelID = 131152;
				itemDef.modelZoom = 962;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 308;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -45;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1417:
				itemDef.name = "<col=B73C36>Bloodstained Arrowhead";
				itemDef.modelID = 131153;
				itemDef.modelZoom = 962;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 308;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -45;
				itemDef.stackable = true;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1418:
				itemDef.name = "<col=3F8C79>Symbiotic Arrowhead";
				itemDef.modelID = 131151;
				itemDef.modelZoom = 962;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 308;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -45;
				itemDef.stackable = true;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1419:
				itemDef.name = "<col=7C44B7>Nether Arrowhead";
				itemDef.modelID = 131150;
				itemDef.modelZoom = 962;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 308;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -45;
				itemDef.stackable = true;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1420:
				itemDef.name = "<col=B37E83>Vorpal Axe";
				itemDef.maleWieldX = -6;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -2;
				itemDef.modelID = 131031;
				itemDef.maleEquip1 = 131031;
				itemDef.femaleEquip1 = 131031;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 251;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 47;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1421:
				itemDef.name = "<col=B73C36>Bloodstained Axe";
				itemDef.maleWieldX = -6;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -2;
				itemDef.modelID = 131028;
				itemDef.maleEquip1 = 131028;
				itemDef.femaleEquip1 = 131028;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 251;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 47;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1422:
				itemDef.name = "<col=3F8C79>Symbiotic Axe";
				itemDef.maleWieldX = -6;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -2;
				itemDef.modelID = 131030;
				itemDef.maleEquip1 = 131030;
				itemDef.femaleEquip1 = 131030;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 251;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 47;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1423:
				itemDef.name = "<col=7C44B7>Nether Axe";
				itemDef.maleWieldX = -6;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -2;
				itemDef.modelID = 131029;
				itemDef.maleEquip1 = 131029;
				itemDef.femaleEquip1 = 131029;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 251;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 47;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1424:
				itemDef.name = "<col=B37E83>Vorpal Pickaxe";
				itemDef.modelID = 131111;
				itemDef.maleEquip1 = 131111;
				itemDef.femaleEquip1 = 131111;
				itemDef.modelZoom = 1245;
				itemDef.rotation_x = 1332;
				itemDef.rotation_y = 231;
				itemDef.translate_x = -45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 192;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1425:
				itemDef.name = "<col=B73C36>Bloodstained Pickaxe";
				itemDef.modelID = 131112;
				itemDef.maleEquip1 = 131112;
				itemDef.femaleEquip1 = 131112;
				itemDef.modelZoom = 1245;
				itemDef.rotation_x = 1332;
				itemDef.rotation_y = 231;
				itemDef.translate_x = -45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 192;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1426:
				itemDef.name = "<col=3F8C79>Symbiotic Pickaxe";
				itemDef.modelID = 131110;
				itemDef.maleEquip1 = 131110;
				itemDef.femaleEquip1 = 131110;
				itemDef.modelZoom = 1245;
				itemDef.rotation_x = 1332;
				itemDef.rotation_y = 231;
				itemDef.translate_x = -45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 192;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1427:
				itemDef.name = "<col=7C44B7>Nether Pickaxe";
				itemDef.modelID = 131109;
				itemDef.maleEquip1 = 131109;
				itemDef.femaleEquip1 = 131109;
				itemDef.modelZoom = 1245;
				itemDef.rotation_x = 1332;
				itemDef.rotation_y = 231;
				itemDef.translate_x = -45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 192;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1428:
				itemDef.name = "<col=B37E83>Vorpal Ammo";
				itemDef.modelID = 131101;
				itemDef.modelZoom = 1580;
				itemDef.rotation_x = 1832;
				itemDef.rotation_y = 336;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 20;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1429:
				itemDef.name = "<col=3F8C79>Symbiotic Ammo";
				itemDef.modelID = 131102;
				itemDef.modelZoom = 1580;
				itemDef.rotation_x = 1832;
				itemDef.rotation_y = 336;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 20;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1430:
				itemDef.name = "<col=B73C36>Bloodstained Ammo";
				itemDef.modelID = 131103;
				itemDef.modelZoom = 1580;
				itemDef.rotation_x = 1832;
				itemDef.rotation_y = 336;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 20;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1431:
				itemDef.name = "<col=7C44B7>Nether Ammo";
				itemDef.modelID = 131100;
				itemDef.modelZoom = 1580;
				itemDef.rotation_x = 1832;
				itemDef.rotation_y = 336;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 20;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1432:
				itemDef.name = "Beginner Axe";
				itemDef.modelID = 131162;
				itemDef.maleEquip1 = 131162;
				itemDef.femaleEquip1 = 131162;
				itemDef.maleWieldX = -6;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -2;
				itemDef.modelZoom = 1150;
				itemDef.rotation_x = 251;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 47;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1433:
				itemDef.name = "Beginner Pickaxe";
				itemDef.modelID = 131163;
				itemDef.maleEquip1 = 131163;
				itemDef.femaleEquip1 = 131163;
				itemDef.modelZoom = 1245;
				itemDef.rotation_x = 1332;
				itemDef.rotation_y = 231;
				itemDef.translate_x = -45;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 192;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 1434:
				break;
			case 1435:
				itemDef.name = "LumberJack Hat";
				itemDef.modelID = 130070;
				itemDef.maleEquip1 = 130070;
				itemDef.femaleEquip1 = 130070;
				itemDef.modelZoom = 820;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 9;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 80;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1436:
				itemDef.name = "LumberJack Body";
				itemDef.modelID = 131513;
				itemDef.maleEquip1 = 131513;
				itemDef.femaleEquip1 = 131513;
				itemDef.armsModel = 10980;
				itemDef.modelZoom = 945;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 56;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1437:
				itemDef.name = "LumberJack Legs";
				itemDef.modelID = 131515;
				itemDef.maleEquip1 = 131515;
				itemDef.femaleEquip1 = 131515;
				itemDef.modelZoom = 1880;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 8;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1438:
				itemDef.name = "LumberJack Gloves";
				itemDef.modelID = 130073;
				itemDef.maleEquip1 = 130073;
				itemDef.femaleEquip1 = 130073;
				itemDef.modelZoom = 900;
				itemDef.rotation_x = 386;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 45;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1439:
				itemDef.name = "LumberJack Boots";
				itemDef.modelID = 131516;
				itemDef.maleEquip1 = 131516;
				itemDef.femaleEquip1 = 131516;
				itemDef.modelZoom = 963;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 43;
				itemDef.translate_x = 0;
				itemDef.translate_yz = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1440:
				break;
			case 1441:
				itemDef.name = "Miner Hat";
				itemDef.modelID = 130517;
				itemDef.maleEquip1 = 130517;
				itemDef.femaleEquip1 = 130517;
				itemDef.modelZoom = 1008;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 366;
				itemDef.translate_x = 0;
				itemDef.translate_yz = -60;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1442:
				itemDef.name = "Miner Body";
				itemDef.modelID = 131508;
				itemDef.maleEquip1 = 131508;
				itemDef.femaleEquip1 = 131508;
				itemDef.armsModel = 131509;
				itemDef.modelZoom = 1039;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 56;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1443:
				itemDef.name = "Miner Legs";
				itemDef.modelID = 131510;
				itemDef.maleEquip1 = 131510;
				itemDef.femaleEquip1 = 131510;
				itemDef.modelZoom = 1786;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 1;
				itemDef.translate_yz = 8;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1444:
				itemDef.name = "Miner Gloves";
				itemDef.modelID = 131512;
				itemDef.maleEquip1 = 131512;
				itemDef.femaleEquip1 = 131512;
				itemDef.modelZoom = 900;
				itemDef.rotation_x = 386;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 45;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1445:
				itemDef.name = "Miner Boots";
				itemDef.modelID = 131511;
				itemDef.maleEquip1 = 131511;
				itemDef.femaleEquip1 = 131511;
				itemDef.modelZoom = 869;
				itemDef.rotation_x = 173;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;


			case 1446:
				itemDef.name = "Resource Pack(t1)";
				itemDef.modelID = 131520;
				itemDef.modelZoom = 4218;
				itemDef.rotation_x = 135;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 1447:
				itemDef.name = "Resource Pack(t2)";
				itemDef.modelID = 131521;
				itemDef.modelZoom = 4218;
				itemDef.rotation_x = 135;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				break;
			case 1448:
				itemDef.name = "Potion Pack";
				itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
				itemDef.modelID = 131522;
				itemDef.modelZoom = 2250;
				itemDef.element = 4;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				break;
			case 1449:
				break;
			case 1450:
				itemDef.name = "@cya@Frost Fragments";
				itemDef.modelID = 131530;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 1451:
				itemDef.name = "@cya@Christmas Key(1)";
				itemDef.modelID = 131528;
				itemDef.modelZoom = 1326;
				itemDef.rotation_x = 201;
				itemDef.rotation_y = 552;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 2;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1452:
				itemDef.name = "@gre@Christmas Key(2)";
				itemDef.modelID = 131529;
				itemDef.modelZoom = 1326;
				itemDef.rotation_x = 201;
				itemDef.rotation_y = 552;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 2;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1453:
				itemDef.name = "@cya@Frost Crate";
				itemDef.modelID = 131526;
				itemDef.modelZoom = 2250;
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.element = 3;
				itemDef.rotation_x = 115;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				break;
			case 1454:
				itemDef.name = "@cya@Frozen Casket";
				itemDef.modelID = 131527;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 1455:
				itemDef.name = "Santa Hat";
				itemDef.modelID = 2537;
				itemDef.maleEquip1 = 189;
				itemDef.femaleEquip1 = 366;
				itemDef.modelZoom = 540;
				itemDef.rotation_x = 136;
				itemDef.rotation_y = 72;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -3;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1456:
				itemDef.name = "Santa Body";
				itemDef.modelID = 45273;
				itemDef.armsModel = 45188;
				itemDef.maleEquip1 = 45192;
				itemDef.femaleEquip1 = 45199;
				itemDef.modelZoom = 1360;
				itemDef.rotation_x = 6;
				itemDef.rotation_y = 561;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 3;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1457:
				itemDef.name = "Santa Legs";
				itemDef.modelID = 45275;
				itemDef.maleEquip1 = 45195;
				itemDef.femaleEquip1 = 45202;
				itemDef.modelZoom = 1872;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 541;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 4;
				itemDef.translate_x = -1;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1458:
				itemDef.name = "Santa Gloves";
				itemDef.modelID = 45280;
				itemDef.maleEquip1 = 45196;
				itemDef.femaleEquip1 = 45203;
				itemDef.modelZoom = 659;
				itemDef.rotation_x = 828;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 97;
				itemDef.translate_yz = -1;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1459:
				itemDef.name = "Santa Boots";
				itemDef.modelID = 45272;
				itemDef.maleEquip1 = 45191;
				itemDef.femaleEquip1 = 45198;
				itemDef.modelZoom = 770;
				itemDef.rotation_x = 124;
				itemDef.rotation_y = 62;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 4;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1460:
				itemDef.name = "Santa's Bag";
				itemDef.modelID = 131525;
				itemDef.maleEquip1 = 131525;
				itemDef.femaleEquip1 = 131525;
				itemDef.modelZoom = 1601;
				itemDef.rotation_x = 1408;
				itemDef.rotation_y = 289;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 34;
				itemDef.translate_yz = -100;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1466:
				itemDef.name = "@cya@Tome of Frost";
				itemDef.modelZoom = 976;
				itemDef.rotation_x = 1607;
				itemDef.rotation_y = 240;
				itemDef.translate_x = -3;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 100;
				itemDef.modelID = 56777;
				itemDef.femaleEquip1 = 56026;
				itemDef.maleEquip1 = 56092;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null , "Equip", null, null, "Drop"};
				break;
			case 1461:
				//itemDef.name = "Melee Wep";
				itemDef.name = "@red@Santa's Scythe";
				itemDef.modelZoom = 2905;
				itemDef.rotation_x = 1824;
				itemDef.rotation_y = 1776;
				itemDef.translate_x = 25;
				itemDef.rotation_z = 9;
				itemDef.translate_yz = 30;
				itemDef.modelID = 131326;
				itemDef.femaleEquip1 = 131326;
				itemDef.maleEquip1 = 131326;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null , "Equip", null, null, "Drop"};
				break;
			case 1462:
				itemDef.name = "@red@Snowballs";
				itemDef.modelID = 20470;
				itemDef.maleEquip1 = 20504;
				itemDef.femaleEquip1 = 20504;
				itemDef.modelZoom = 470;
				itemDef.rotation_x = 72;
				itemDef.rotation_y = 120;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -10;
				itemDef.translate_x = -5;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, null};
				break;
			case 1463:
				itemDef.name = "@red@Candy Wand";
				itemDef.stackable = false;
				itemDef.modelZoom = 1744;
				itemDef.rotation_x = 1670;
				itemDef.rotation_y = 240;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -24;
				itemDef.modelID = 51166;
				itemDef.femaleEquip1 = 51311;
				itemDef.maleEquip1 = 51311;
				itemDef.actions = new String[]{null , "Equip", null, null, "Drop"};
				break;

			case 1464:
				itemDef.name = "Xmas Booster";
				break;
			case 1465:
				itemDef.name = "@cya@Iceborn Serum";
				itemDef.modelID = 131531;
				itemDef.modelZoom = 850;
				itemDef.actions = new String[]{"Drink", null, null, null, "Drop"};
				itemDef.element = 3;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 1467:
				itemDef.name = "@cya@Snowflake";
				itemDef.modelID = 30296;
				itemDef.maleEquip1 = 30296;
				itemDef.femaleEquip1 = 30296;
				itemDef.modelZoom = 2376;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.element = 3;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -39;
				itemDef.stackable = false;
				break;
			case 1468:
				itemDef.name = "@cya@Frozen Heart";
				itemDef.modelID = 131536;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 1387;
				itemDef.rotation_y = 359;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 1469:
				itemDef.name = "@cya@Snow Globe";
				itemDef.modelID = 30309;
				itemDef.modelZoom = 400;
				itemDef.rotation_x = 269;
				itemDef.rotation_y = 96;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -1;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Shake", null, null, null, "Drop"};
				break;
			case 1525:
				itemDef.name = "@yel@Vote Boost";
				itemDef.modelID = 131604;
				itemDef.modelZoom = 646;
				itemDef.rotation_x = 1042;
				itemDef.rotation_y = 558;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				break;
			case 1526:
				itemDef.name = "Sacrificial Effigy";
				itemDef.modelID = 131605;
				itemDef.modelZoom = 1298;
				itemDef.rotation_x = 135;
				itemDef.rotation_y = 173;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -8;
				itemDef.stackable = false;
				break;
			case 19065:
				itemDef.name = "@yel@Treasure Token";
				itemDef.modelID = 131606;
				itemDef.modelZoom = 1298;
				itemDef.rotation_x = 761;
				itemDef.rotation_y = 512;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 7509:
				itemDef.name = "Ascension Cake";
				itemDef.modelID = 131623;
				itemDef.modelZoom = 448;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 289;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				break;
			case 1540:
				itemDef.name = "Global Fragment";
				itemDef.modelID = 131628;
				itemDef.modelZoom = 448;
				itemDef.rotation_x = 856;
				itemDef.rotation_y = 174;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;

			case 10228:
				itemDef.name = "<col=AF70C3>Nakio's Wand";
				itemDef.modelID = 101810;
				itemDef.maleEquip1 = 101810;
				itemDef.femaleEquip1 = 101810;
				itemDef.modelZoom = 1036;
				itemDef.rotation_x = 811;
				itemDef.rotation_y = 135;
				itemDef.translate_x = -5;
				itemDef.translate_yz = 114;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};

				itemDef.maleWieldX = 0;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -5;
				break;
			case 7266:
				itemDef.name = "<col=AF70C3>Nakio's Tome";
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelID = 131629;
				itemDef.translate_yz = 4;
				itemDef.maleEquip1 = 131630;
				itemDef.femaleEquip1 = 131630;
				itemDef.modelZoom = 1065;
				break;

			case 14646:
				itemDef.name = "Solar Aether";
				itemDef.stackable = true;
				itemDef.element = 2;
				break;

			case 14639:
				itemDef.name = "@cya@Oceanic Aether";
				itemDef.actions = new String[]{"@cya@Combine", null, null, null, null};
				itemDef.stackable = true;
				itemDef.element = 3;
				break;

			case 23187:
				itemDef.name = "<col=AF70C3>Arcana Tome";
				itemDef.actions = new String[]{null, "Open", null, null, "Drop"};
				itemDef.modelID = 131629;
				itemDef.translate_yz = 4;
				itemDef.maleEquip1 = 131630;
				itemDef.femaleEquip1 = 131630;
				itemDef.modelZoom = 1065;
				itemDef.element = 4;
				break;

			case 1542:
				itemDef.name = "<col=AF70C3>Dream Trinket";
				itemDef.modelID = 131640;
				itemDef.modelZoom = 825;
				itemDef.rotation_x = 199;
				itemDef.rotation_y = 560;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 12423:
				itemDef.name = "<col=5FD857>Overgrown Soul";
				itemDef.modelID = 130188;
				itemDef.actions = new String[]{"Consume", null, null, null, "Drop"};
				itemDef.element = 1;
				itemDef.modelZoom = 832;
				itemDef.rotation_x = 423;
				itemDef.rotation_y = 300;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 30;
				itemDef.stackable = false;
				break;
			case 12424:
				itemDef.name = "<col=1097BF>Aquatic Soul";
				itemDef.modelID = 130190;
				itemDef.actions = new String[]{"Consume", null, null, null, "Drop"};
				itemDef.element = 3;
				itemDef.modelZoom = 832;
				itemDef.rotation_x = 423;
				itemDef.rotation_y = 300;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 30;
				itemDef.stackable = false;
				break;
			case 12427:
				itemDef.name = "<col=D45F4B>Magma Soul";
				itemDef.modelID = 130189;
				itemDef.actions = new String[]{"Consume", null, null, null, "Drop"};
				itemDef.element = 2;
				itemDef.modelZoom = 832;
				itemDef.rotation_x = 423;
				itemDef.rotation_y = 300;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 30;
				itemDef.stackable = false;
				break;
			case 1600:
				itemDef.name = "Stream Points";
				itemDef.modelID = 131651;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.modelZoom = 452;
				itemDef.rotation_x = 132;
				itemDef.rotation_y = 320;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 6;
				itemDef.translate_yz = -2;
				itemDef.stackable = false;
				break;
			case 1601:
				itemDef.name = "@red@Tower Points";
				itemDef.modelID = 131652;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				itemDef.modelZoom = 1209;
				itemDef.rotation_x = 423;
				itemDef.rotation_y = 280;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -37;
				itemDef.stackable = false;
				break;

			case 2000:
				itemDef.setDefaults();
				itemDef.name = "@red@Unlock Double Double";
				itemDef.modelID = 131663;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 2001:
				itemDef.setDefaults();
				itemDef.name = "@red@Unlock Double Trouble";
				itemDef.modelID = 131664;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 2002:
				itemDef.setDefaults();
				itemDef.name = "Elemental Overdrive Unlock";
				itemDef.modelID = 131665;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 2003:
				itemDef.setDefaults();
				itemDef.name = "@red@Demonic Aggression Unlock";
				itemDef.modelID = 131666;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 2004:
				itemDef.setDefaults();
				itemDef.name = "@red@Warlock's Pact Unlock";
				itemDef.modelID = 131667;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 2005:
				itemDef.setDefaults();
				itemDef.name = "@red@Mark of Valor Unlock";
				itemDef.modelID = 131668;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 2006:
				itemDef.setDefaults();
				itemDef.name = "@whi@Royal Membership Scroll";
				itemDef.modelID = 131683;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 825;
				itemDef.rotation_y = 1008;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Redeem", null, null, null, "Drop"};
				break;
			case 2023:
				itemDef.setDefaults();
				itemDef.name = "Custom Item Ticket";
				itemDef.modelID = 131699;
				itemDef.modelZoom = 1433;
				itemDef.rotation_x = 805;
				itemDef.rotation_y = 930;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 2007:
				itemDef.setDefaults();
				itemDef.name = "AoE Token";
				itemDef.modelID = 13838;
				itemDef.modelZoom = 530;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 415;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{"Imbue Self", null, null, null, "Drop"};
				break;
			case 2008:
				itemDef.setDefaults();
				itemDef.name = "Supply Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, null, null};
				itemDef.modelID = 131698;
				itemDef.modelZoom = 4218;
				itemDef.rotation_x = 135;
				itemDef.rotation_y = 135;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				break;
			case 2009:
				itemDef.setDefaults();
				itemDef.name = "@red@Corrupt Crate";
				itemDef.modelID = 131697;
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = 1;
				break;



			case 3507:
				itemDef.setDefaults();
				itemDef.name = "@red@Corrupt Dye";
				itemDef.modelID = 131816;
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelZoom = 760;
				itemDef.rotation_x = 1920;
				itemDef.rotation_y = 124;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.translate_x = 0;
				break;
			case 3508:
				itemDef.setDefaults();
				itemDef.name = "@cya@Icy Dye";
				itemDef.modelID = 131818;
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelZoom = 760;
				itemDef.rotation_x = 1920;
				itemDef.rotation_y = 124;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.translate_x = 0;
				break;
			case 3509:
				itemDef.setDefaults();
				itemDef.name = "@cya@Poison Dye";
				itemDef.modelID = 131817;
				itemDef.actions = new String[]{null, null, null, null, null};
				itemDef.modelZoom = 760;
				itemDef.rotation_x = 1920;
				itemDef.rotation_y = 124;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -2;
				itemDef.translate_x = 0;
				break;
			case 3519:
				itemDef.setDefaults();
				itemDef.name = "@gre@Appreciation Cape";
				itemDef.modelID = 132030;
				itemDef.maleEquip1 = 132030;
				itemDef.femaleEquip1 = 132030;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 7;
				itemDef.modelZoom = 2140;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 98;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = 3;
				itemDef.maleWieldZ = 7;
				break;
			case 3520:
				itemDef.setDefaults();
				itemDef.name = "@gre@Hyperion Cape";
				itemDef.modelID = 131977;
				itemDef.maleEquip1 = 131977;
				itemDef.femaleEquip1 = 131977;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 7;
				itemDef.modelZoom = 2140;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 98;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = 3;
				break;
			case 3521:
				itemDef.setDefaults();
				itemDef.name = "@cya@Hyperion Cape";
				itemDef.modelID = 131976;
				itemDef.maleEquip1 = 131976;
				itemDef.femaleEquip1 = 131976;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 8;
				itemDef.modelZoom = 2140;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 98;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = 3;
				break;
			case 3522:
				itemDef.setDefaults();
				itemDef.name = "@red@Hyperion Cape";
				itemDef.modelID = 131978;
				itemDef.maleEquip1 = 131978;
				itemDef.femaleEquip1 = 131978;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 9;
				itemDef.modelZoom = 2140;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 98;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = 3;
				break;


			case 3523:
				itemDef.setDefaults();
				itemDef.name = "@red@Corrupt Cape(1)";
				itemDef.modelID = 131823;
				itemDef.maleEquip1 = 131823;
				itemDef.femaleEquip1 = 131823;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 9;
				itemDef.modelZoom = 2140;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 98;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = 3;
				itemDef.hintTier = 2;
				break;
			case 3524:
				itemDef.setDefaults();
				itemDef.name = "@red@Corrupt Cape";
				itemDef.modelID = 131979;
				itemDef.maleEquip1 = 131979;
				itemDef.femaleEquip1 = 131979;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 9;
				itemDef.modelZoom = 2140;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 98;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = 3;
				itemDef.maleWieldZ = 5;
				break;
			case 3525:
				itemDef.setDefaults();
				itemDef.name = "@red@Corrupt Cape(3)";
				itemDef.modelID = 131823;
				itemDef.maleEquip1 = 131823;
				itemDef.femaleEquip1 = 131823;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.stackable = false;
				itemDef.element = 9;
				itemDef.modelZoom = 2140;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 98;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = 3;
				itemDef.hintTier = 2;
				break;
			case 3526:
				itemDef.name = "@red@Corrupt Defender";
				itemDef.modelID = 132004;
				itemDef.maleEquip1 = 132004;
				itemDef.femaleEquip1 = 132004;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 9;
				itemDef.modelZoom = 785;
				itemDef.rotation_x = 289;
				itemDef.rotation_y = 753;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 35;
				itemDef.translate_x = -4;
				itemDef.maleWieldX = 5;
				itemDef.maleWieldY = 5;
				itemDef.maleWieldZ = 0;
				break;
			case 3527:
				itemDef.name = "@red@Corrupt Defender(2)";
				itemDef.modelID = 132004;
				itemDef.maleEquip1 = 132004;
				itemDef.femaleEquip1 = 132004;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 9;
				itemDef.modelZoom = 785;
				itemDef.rotation_x = 289;
				itemDef.rotation_y = 753;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 35;
				itemDef.translate_x = -4;
				itemDef.maleWieldX = 5;
				itemDef.maleWieldY = 5;
				itemDef.maleWieldZ = 0;
				break;
			case 3528:
				itemDef.setDefaults();
				itemDef.name = "@red@Corrupt Defender(3)";
				itemDef.modelID = 132004;
				itemDef.maleEquip1 = 132004;
				itemDef.femaleEquip1 = 132004;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.element = 9;
				itemDef.modelZoom = 785;
				itemDef.rotation_x = 289;
				itemDef.rotation_y = 753;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 35;
				itemDef.translate_x = -4;
				itemDef.maleWieldX = 5;
				itemDef.maleWieldY = 5;
				itemDef.maleWieldZ = 0;
				break;




			case 700:
				itemDef.setDefaults();
				itemDef.name = "<col=1097BF>Aqua Artifact";//junk store
				itemDef.modelID = 24411;
				itemDef.modelZoom = 789;
				itemDef.rotation_x = 1946;
				itemDef.rotation_y = 277;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 22;
				itemDef.translate_x = -9;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 701:
				itemDef.setDefaults();
				itemDef.name = "<col=D45F4B>Lava Artifact";//junk store
				itemDef.modelID = 24415;
				itemDef.modelZoom = 1438;
				itemDef.rotation_x = 1618;
				itemDef.rotation_y = 360;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = -4;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 702:
				itemDef.setDefaults();
				itemDef.name = "<col=5FD857>Gaia Artifact";//junk store
				itemDef.modelID = 24417;
				itemDef.modelZoom = 1296;
				itemDef.rotation_x = 1411;
				itemDef.rotation_y = 152;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -19;
				itemDef.translate_x = -1;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 703:
				itemDef.setDefaults();
				itemDef.name = "<col=AF70C3>Void Artifact";//junk store
				itemDef.modelID = 24409;
				itemDef.modelZoom = 984;
				itemDef.rotation_x = 249;
				itemDef.rotation_y = 124;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.translate_x = 4;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 704:
				itemDef.setDefaults();
				itemDef.name = "<col=1097BF>Aqua Visage";
				itemDef.modelID = 131904;
				itemDef.modelZoom = 1697;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 567;
				itemDef.rotation_z = 2047;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 705:
				itemDef.setDefaults();
				itemDef.name = "<col=1097BF>Aqua Relic";
				itemDef.modelID = 131909;
				itemDef.modelZoom = 753;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 238;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 706:
				itemDef.setDefaults();
				itemDef.name = "<col=D45F4B>Lava Visage";
				itemDef.modelID = 131905;
				itemDef.modelZoom = 1697;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 567;
				itemDef.rotation_z = 2047;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 707:
				itemDef.setDefaults();
				itemDef.name = "<col=D45F4B>Lava Relic";
				itemDef.modelID = 131908;
				itemDef.modelZoom = 753;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 238;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 708:
				itemDef.setDefaults();
				itemDef.name = "<col=5FD857>Gaia Visage";
				itemDef.modelID = 131906;
				itemDef.modelZoom = 1697;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 567;
				itemDef.rotation_z = 2047;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 709:
				itemDef.setDefaults();
				itemDef.name = "<col=5FD857>Gaia Relic";
				itemDef.modelID = 131910;
				itemDef.modelZoom = 753;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 238;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 710:
				itemDef.setDefaults();
				itemDef.name = "<col=AF70C3>Void Relic";
				itemDef.modelID = 131911;
				itemDef.modelZoom = 753;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 238;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;

			case 723:
				itemDef.setDefaults();
				itemDef.name = "<col=AF70C3>Void Visage";
				itemDef.modelID = 131907;
				itemDef.modelZoom = 1697;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 567;
				itemDef.rotation_z = 2047;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;


			case 20045:
				itemDef.name = "<col=AF70C3>Malevolence Unlock";
				itemDef.modelID = 130483;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;
			case 20046:
				itemDef.name = "<col=AF70C3>Desolation Unlock";
				itemDef.modelID = 130482;
				itemDef.modelZoom = 1246;
				itemDef.rotation_x = 907;
				itemDef.rotation_y = 689;
				itemDef.translate_x = 1;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 5;
				itemDef.actions = new String[]{"Activate", null, null, null, "Drop"};
				break;






			//EASTER
			case 711:
				itemDef.name = "<col=D45F4B>Lava Blaster";
				itemDef.element = 2;
				itemDef.stackable = false;
				itemDef.modelID = 131921;
				itemDef.maleEquip1 = 131921;
				itemDef.femaleEquip1 = 131921;
				itemDef.modelZoom = 1880;
				itemDef.rotation_x = 450;
				itemDef.rotation_y = 96;
				itemDef.rotation_z = 5;
				itemDef.translate_yz = 12;
				itemDef.translate_x = 36;
				itemDef.actions = new String[]{null, "Equip", null, null, "Salvage"};
				break;
			case 712:
				itemDef.name = "<col=1097BF>Aqua Blaster";
				itemDef.element = 3;
				itemDef.stackable = false;
				itemDef.modelID = 131922;
				itemDef.maleEquip1 = 131922;
				itemDef.femaleEquip1 = 131922;
				itemDef.modelZoom = 1880;
				itemDef.rotation_x = 450;
				itemDef.rotation_y = 96;
				itemDef.rotation_z = 5;
				itemDef.translate_yz = 12;
				itemDef.translate_x = 36;
				itemDef.actions = new String[]{null, "Equip", null, null, "Salvage"};
				break;
			case 713:
				itemDef.name = "<col=5FD857>Gaia Blaster";
				itemDef.element = 1;
				itemDef.stackable = false;
				itemDef.modelID = 131923;
				itemDef.maleEquip1 = 131923;
				itemDef.femaleEquip1 = 131923;
				itemDef.modelZoom = 1880;
				itemDef.rotation_x = 450;
				itemDef.rotation_y = 96;
				itemDef.rotation_z = 5;
				itemDef.translate_yz = 12;
				itemDef.translate_x = 36;
				itemDef.actions = new String[]{null, "Equip", null, null, "Salvage"};
				break;
			case 714:
				itemDef.name = "<col=AF70C3>Void Blaster";
				itemDef.element = 4;
				itemDef.stackable = false;
				itemDef.modelID = 131920;
				itemDef.maleEquip1 = 131920;
				itemDef.femaleEquip1 = 131920;
				itemDef.modelZoom = 1880;
				itemDef.rotation_x = 450;
				itemDef.rotation_y = 96;
				itemDef.rotation_z = 5;
				itemDef.translate_yz = 12;
				itemDef.translate_x = 36;
				itemDef.actions = new String[]{null, "Equip", null, null, "Salvage"};
				break;
			case 21036:
				itemDef.name = "Bunny Mask";
				itemDef.modelID = 101873;
				itemDef.maleEquip1 = 101873;
				itemDef.femaleEquip1 = 101873;
				itemDef.modelZoom = 935;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 79;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 715:
				itemDef.name = "@cya@Easter Fragments";
				itemDef.modelID = 131924;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 716:
				itemDef.name = "@cya@Easter Serum";
				itemDef.modelID = 131925;
				itemDef.modelZoom = 850;
				itemDef.actions = new String[]{"Drink", null, null, null, "Drop"};
				itemDef.element = 3;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 76;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 717:
				itemDef.name = "Secret Note";
				break;
			case 12637:
				itemDef.name = "Easter Egg";
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 719:
				itemDef.name = "Carrot Pendant";
				itemDef.modelID = 101817;
				itemDef.maleEquip1 = 101822;
				itemDef.femaleEquip1 = 101822;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.modelZoom = 562;
				itemDef.rotation_x = 15;
				itemDef.rotation_y = 132;
				itemDef.translate_yz = -5;
				itemDef.translate_x = 0;
				break;
			case 720:
				itemDef.name = "@gre@Egg Crate";
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				itemDef.modelID = 101812;
				itemDef.modelZoom = 473;
				itemDef.rotation_y = 128;
				itemDef.translate_yz = -4;
				itemDef.translate_x = 1;
				break;
			case 760:
				itemDef.name = "<col=AF70C3>Void Shard";
				itemDef.modelID = 131935;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 761:
				itemDef.name = "<col=AF70C3>Void fragment";
				itemDef.modelID = 131936;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 18647:
				itemDef.name = "Gate Key";
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				itemDef.stackable = false;
				break;
			case 762:
				itemDef.name = "<col=AF70C3>Void Sigil";
				itemDef.modelID = 131937;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.rotation_z = 0;
				itemDef.translate_x = 0;
				itemDef.translate_yz = 0;
				itemDef.actions = new String[]{null,null, null, null, "Drop"};
				break;
			case 763:
				itemDef.name = "@red@Corrupt Crystal(1)";
				itemDef.modelID = 131938;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 9;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 764:
				itemDef.name = "@red@Corrupt Crystal(2)";
				itemDef.modelID = 131939;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", "Upgrade", null, "Drop"};
				itemDef.element = 9;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 765:
				itemDef.name = "@red@Corrupt Crystal(3)";
				itemDef.modelID = 131940;
				itemDef.modelZoom = 924;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.element = 9;
				itemDef.rotation_x = 262;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -6;
				itemDef.stackable = false;
				itemDef.hintTier = 1;
				break;
			case 725:
				itemDef.setDefaults();
				itemDef.name = "Circle of Elements";
				itemDef.modelID = 131907;
				itemDef.modelZoom = 1697;
				itemDef.rotation_x = 152;
				itemDef.rotation_y = 567;
				itemDef.rotation_z = 2047;
				itemDef.translate_yz = -5;
				itemDef.translate_x = -5;
				itemDef.actions = new String[]{null, null, null, null, "Drop"};
				break;
			case 730:
				itemDef.setDefaults();
				itemDef.name = "Favor Casket";
				itemDef.modelID = 131926;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 5020:
				itemDef.name = "Afk Ticket";
				break;
			case 731:
				itemDef.setDefaults();
				itemDef.name = "Vote Crate";
				itemDef.modelID = 131927;
				itemDef.modelZoom = 375;
				itemDef.rotation_x = 43;
				itemDef.rotation_y = 26;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				break;
			case 733:
				itemDef.setDefaults();
				itemDef.name = "Lamp Casket";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 750:
				itemDef.setDefaults();
				itemDef.name = "@red@Hyperion Blade";
				itemDef.modelID = 131933;
				itemDef.maleEquip1 = 131933;
				itemDef.femaleEquip1 = 131933;;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 816;
				itemDef.rotation_y = 173;
				itemDef.rotation_z = 207;
				itemDef.translate_x = -45;
				itemDef.translate_yz = 51;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 751:
				itemDef.setDefaults();
				itemDef.name = "@gre@Hyperion Bow";
				itemDef.modelID = 131932;
				itemDef.maleEquip1 = 131932;
				itemDef.femaleEquip1 = 131932;;
				itemDef.modelZoom = 1330;
				itemDef.rotation_x = 1969;
				itemDef.rotation_y = 0;
				itemDef.translate_x = 28;
				itemDef.translate_yz = 27;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				break;
			case 752:
				itemDef.setDefaults();
				itemDef.name = "@cya@Hyperion Wand";
				itemDef.modelID = 131931;
				itemDef.maleEquip1 = 131931;
				itemDef.femaleEquip1 = 131931;;
				itemDef.modelZoom = 658;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 115;
				itemDef.translate_x = 40;
				itemDef.translate_yz = 57;
				itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
				itemDef.maleWieldX = 0;
				itemDef.maleWieldY = 11;
				itemDef.maleWieldZ = -5;
				break;
			case 753:
				itemDef.setDefaults();
				itemDef.name = "Hyperion Tribrid Wep";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 754:
				itemDef.setDefaults();
				itemDef.name = "Hyperion Weapon Crate";
				itemDef.modelID = 131943;
				itemDef.modelZoom = 1716;
				itemDef.rotation_x = 96;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = -16;
				itemDef.translate_x = 1;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"@yel@Open", null, null, "Rewards", null};
				break;
			case 755:
				itemDef.setDefaults();
				itemDef.name = "Spring Crate";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", "Rewards", "Drop"};
				break;




			case 771:
				itemDef.setDefaults();
				itemDef.name = "Material 2";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 772:
				itemDef.setDefaults();
				itemDef.name = "Recipe Item 1";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 773:
				itemDef.setDefaults();
				itemDef.name = "Challenger Token";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 774:
				itemDef.setDefaults();
				itemDef.name = "Material 3";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 775:
				itemDef.setDefaults();
				itemDef.name = "Material 4";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 776:
				itemDef.setDefaults();
				itemDef.name = "Recipe Item 2";
				itemDef.modelID = 131929;
				itemDef.modelZoom = 5665;
				itemDef.rotation_x = 1853;
				itemDef.rotation_y = 77;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.translate_x = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{"Open", null, "Open 100", null, "Drop"};
				break;
			case 777:
				itemDef.setDefaults();
				itemDef.name = "<col=D45F4B>Fire Shard";
				itemDef.modelID = 130537;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 778:
				itemDef.setDefaults();
				itemDef.name = "<col=1097BF>Water Shard";
				itemDef.modelID = 130538;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, null};
				break;
			case 779:
				itemDef.setDefaults();
				itemDef.name = "<col=5FD857>Earth Shard";
				itemDef.modelID = 130536;
				itemDef.modelZoom = 1200;
				itemDef.rotation_x = 0;
				itemDef.rotation_y = 420;
				itemDef.translate_x = 0;
				itemDef.rotation_z = 0;
				itemDef.translate_yz = 0;
				itemDef.stackable = false;
				itemDef.actions = new String[]{null, null, null, null, null};
				break;




		}


	/*	if (itemDef.certTemplateID != -1) {
		//	itemDef.toNote();
		}

		if (itemDef.lendTemplateID != -1) {
		//	itemDef.toLend();
		}*/

		if (!isMembers && itemDef.membersObject) {
			itemDef.name = "Members Object";
			itemDef.description = "Login to a members' server to use this object.".getBytes();
			itemDef.groundActions = null;
			itemDef.actions = null;
			itemDef.team = 0;
		}

		switch (itemDef.id) {

			case 20147:
				itemDef.originalModelColors = new int[2];
				itemDef.newModelColors = new int[2];
				itemDef.originalModelColors[0] = 4550;
				itemDef.newModelColors[0] = 1;
				itemDef.originalModelColors[1] = 4540;
				itemDef.newModelColors[1] = 1;
				break;
		}
		ArmorDefinitions.newIDS(itemDef, id);
		map.put(id, itemDef);
		return itemDef; //
		//	return ItemDef3.newIDS1(itemDef, id);

	}

	public static Sprite getSprite(int itemId, int stackAmounts, int k) {
		if (k == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(itemId);

			if (sprite != null && sprite.maxHeight != stackAmounts && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}

			if (sprite != null) {
				return sprite;
			}
		}

		if (itemId > ItemDef.totalItems) {
			return null;
		}
		ItemDef definition = get(itemId);

		if (definition.stackIDs == null) {
			stackAmounts = -1;
		}

		if (stackAmounts > 1) {
			int i1 = -1;

			for (int j1 = 0; j1 < 10; j1++) {
				if (stackAmounts >= definition.stackAmounts[j1] && definition.stackAmounts[j1] != 0) {
					i1 = definition.stackIDs[j1];
				}
			}

			if (i1 != -1) {
				definition = get(i1);
			}
		}

		Model model = definition.getInventoryModel(1);

		if (model == null) {
			return null;
		}

		Sprite sprite = null;

	/*	if (definition.certTemplateID != -1) {
			sprite = getSprite(definition.certID, 10, -1);

			if (sprite == null) {
				return null;
			} else {
				Client.dumpImage(sprite, definition);
			}
		}

		if (definition.lendTemplateID != -1) {
			sprite = getSprite(definition.lendID, 50, 0);

			if (sprite == null) {
				return null;
			} else {
				Client.dumpImage(sprite, definition);
			}
		}*/

		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int[] ai = Rasterizer3D.lineOffsets;
		int[] ai1 = Raster.raster;
		float[] depthBuffer = Raster.depthBuffer;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.clipLeft;
		int l2 = Raster.clipRight;
		int i3 = Raster.clipTop;
		int j3 = Raster.clipBottom;
		Rasterizer3D.notTextured = false;
		Raster.initDrawingArea(32, 32, sprite2.myPixels);
		Raster.drawPixels(32, 0, 0, 0, 32);
		Rasterizer3D.clearDepthBuffer();
		Rasterizer3D.method364();
		int k3 = definition.modelZoom;

		if (k == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (k > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		int l3 = Rasterizer3D.SINE[definition.rotation_y] * k3 >> 16;
		int i4 = Rasterizer3D.COSINE[definition.rotation_y] * k3 >> 16;
		model.renderSingle(definition.rotation_x,
				definition.rotation_z,
				definition.rotation_y,
				definition.translate_x,
				l3 + model.modelHeight / 2 + definition.translate_yz,
				i4 + definition.translate_yz);

		if (Configuration.ITEM_OUTLINES) {

			if (definition.element == 15) {//GREEN - EARTH
				sprite2.outline(0xEBE471);
				sprite2.shadow(333333);
			}
			if (definition.element == 1) {//GREEN - EARTH
				sprite2.outline(0x5FD857);
				sprite2.shadow(333333);
			}
			if (definition.element == 2) {//RED - FIRE
				sprite2.outline(0xD45F4B);
				sprite2.shadow(333333);
			}
			if (definition.element == 3) {//BLUE - WATER
				sprite2.outline(0x1097BF);
				sprite2.shadow(333333);
			}
			if (definition.element == 4) {//PURPLE - VOID
				sprite2.outline(0xAF70C3);
				sprite2.shadow(333333);
			}

			if (definition.element == 7) {//ENCHANTED GREEN
				sprite2.outline(0x6BEC6D);
				sprite2.shadow(333333);
			}

			if (definition.element == 8) {//ENCHANTED BLUE
				sprite2.outline(0x10E8CA);
				sprite2.shadow(333333);
			}
			if (definition.element == 9) {//ENCHANTED RED
				sprite2.outline(0xD30641);
				sprite2.shadow(333333);
			}
			if (definition.element == 16) {//UNIQUES
				sprite2.outline(0xF5AD05);
				sprite2.shadow(333333);
			}

		}

		sprite2.outline(1);
		if (k > 0) {
			sprite2.outline(16777215);
		}
		if (k == 0) {
			sprite2.shadow(3153952);
		}
		Raster.initDrawingArea(32, 32, sprite2.myPixels);

/*		if (definition.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		if (definition.lendTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = 32;
			sprite.maxHeight = 32;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}*/

		/*
		 * if (k == 0) { mruNodes1.removeFromCache(sprite2, i); }
		 */
		// Client.instance.method37(i, -1);
		if (k == 0 && !definition.animateInventory) {
			mruNodes1.removeFromCache(sprite2, (long) itemId);
		}
		Raster.initDrawingArea(j2, i2, ai1);
		Raster.setBounds(k2, i3, l2, j3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.lineOffsets = ai;
		Rasterizer3D.notTextured = true;

		if (definition.stackable) {
			sprite2.maxWidth = 33;
		} else {
			sprite2.maxWidth = 32;
		}

		sprite2.maxHeight = stackAmounts;
		Client.dumpImage(sprite2, definition);
		return sprite2;
	}

	public static Sprite getSizedSprite(int i, int j, int k, int width, int height) {
		if (k == 0) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);

			if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}

			if (sprite != null) {
				return sprite;
			}
		}

		if (i > ItemDef.totalItems) {
			return null;
		}
		ItemDef definition = get(i);

		if (definition.stackIDs == null) {
			j = -1;
		}

		if (j > 1) {
			int i1 = -1;

			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= definition.stackAmounts[j1] && definition.stackAmounts[j1] != 0) {
					i1 = definition.stackIDs[j1];
				}
			}

			if (i1 != -1) {
				definition = get(i1);
			}
		}

		Model model = definition.getInventoryModel(1);

		if (model == null) {
			return null;
		}

		Sprite sprite = null;

		if (definition.certTemplateID != -1) {
			sprite = getSprite(definition.certID, 10, -1);

			if (sprite == null) {
				return null;
			} else {
				Client.dumpImage(sprite, definition);
			}
		}

		if (definition.lendTemplateID != -1) {
			sprite = getSprite(definition.lendID, 50, 0);

			if (sprite == null) {
				return null;
			} else {
				Client.dumpImage(sprite, definition);
			}
		}

		Sprite sprite2 = new Sprite(width, height);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int[] ai = Rasterizer3D.lineOffsets;
		int[] ai1 = Raster.raster;
		float[] depthBuffer = Raster.depthBuffer;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.clipLeft;
		int l2 = Raster.clipRight;
		int i3 = Raster.clipTop;
		int j3 = Raster.clipBottom;
		Rasterizer3D.notTextured = false;
		Raster.initDrawingArea(height, width, sprite2.myPixels);
		Raster.drawPixels(height, 0, 0, 0, width);
		Rasterizer3D.clearDepthBuffer();
		Rasterizer3D.method364();
		int k3 = definition.modelZoom;

		if (k == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (k > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		k3 /= (width / 32D);

		int l3 = Rasterizer3D.SINE[definition.rotation_y] * k3 >> 16;
		int i4 = Rasterizer3D.COSINE[definition.rotation_y] * k3 >> 16;
		model.renderSingle(definition.rotation_x, definition.rotation_z, definition.rotation_y, definition.translate_x, l3 + model.modelHeight / 2 + definition.translate_yz, i4 + definition.translate_yz);

		sprite2.outline(1);
		if (k > 0) {
			sprite2.outline(16777215);
		}
		if (k == 0) {
			sprite2.shadow(3153952);
		}
		Raster.initDrawingArea(height, width, sprite2.myPixels);

		if (definition.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = width;
			sprite.maxHeight = height;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		if (definition.lendTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = width;
			sprite.maxHeight = height;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		/*
		 * if (k == 0) { mruNodes1.removeFromCache(sprite2, i); }
		 */
		// Client.instance.method37(i, -1);
		if (k == 0 && !definition.animateInventory) {
			mruNodes1.removeFromCache(sprite2, (long) i);
		}
		Raster.initDrawingArea(j2, i2, ai1);//here
		Raster.setBounds(k2, i3, l2, j3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.lineOffsets = ai;
		Rasterizer3D.notTextured = true;

		if (definition.stackable) {
			sprite2.maxWidth = width;
		} else {
			sprite2.maxWidth = width;
		}

		sprite2.maxHeight = j;
		Client.dumpImage(sprite2, definition);
		return sprite2;
	}

	public static HashMap<Integer, Sprite> spriteCacheEffectTimers = new HashMap<>();

	public static Sprite getSprite(int i, int j, int k, double zoom, boolean effectTimers) {
		int spriteSizeX = effectTimers ? 24 : 32;
		int spriteSizeY = effectTimers ? 24 : 32;
		if (k == 0 && zoom != -1) {
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);

			if (effectTimers) {
				sprite = spriteCacheEffectTimers.get(i);
			}

			if (sprite != null && sprite.maxHeight != j && sprite.maxHeight != -1) {
				sprite.unlink();
				sprite = null;
			}

			if (sprite != null) {
				return sprite;
			}
		}

		ItemDef definition = get(i);

		if (definition.stackIDs == null) {
			j = -1;
		}

		if (j > 1) {
			int i1 = -1;

			for (int j1 = 0; j1 < 10; j1++) {
				if (j >= definition.stackAmounts[j1] && definition.stackAmounts[j1] != 0) {
					i1 = definition.stackIDs[j1];
				}
			}

			if (i1 != -1) {
				definition = get(i1);
			}
		}

		Model model = definition.getInventoryModel(1);

		if (model == null) {
			return null;
		}

		Sprite sprite = null;

		if (definition.certTemplateID != -1) {
			sprite = getSprite(definition.certID, 10, -1);

			if (sprite == null) {
				return null;
			} else {
				Client.dumpImage(sprite, definition);
			}
		}

		if (definition.lendTemplateID != -1) {
			sprite = getSprite(definition.lendID, 50, 0);

			if (sprite == null) {
				return null;
			} else {
				Client.dumpImage(sprite, definition);
			}
		}

		Sprite sprite2 = new Sprite(spriteSizeX, spriteSizeY);
		int k1 = Rasterizer3D.textureInt1;
		int l1 = Rasterizer3D.textureInt2;
		int[] ai = Rasterizer3D.lineOffsets;
		int[] ai1 = Raster.raster;
		float[] depthBuffer = Raster.depthBuffer;
		int i2 = Raster.width;
		int j2 = Raster.height;
		int k2 = Raster.clipLeft;
		int l2 = Raster.clipRight;
		int i3 = Raster.clipTop;
		int j3 = Raster.clipBottom;
		Rasterizer3D.notTextured = false;
		Raster.initDrawingArea(spriteSizeX, spriteSizeY, sprite2.myPixels);
		Raster.drawPixels(spriteSizeX, 0, 0, 0, 32);
		Rasterizer3D.clearDepthBuffer();
		Rasterizer3D.method364();
		int k3 = definition.modelZoom;
		if (zoom != -1 && zoom != 0) {
			k3 = (int) ((k3 * 100) / zoom);
		}
		if (k == -1) {
			k3 = (int) (k3 * 1.5D);
		}

		if (k > 0) {
			k3 = (int) (k3 * 1.04D);
		}

		int l3 = Rasterizer3D.SINE[definition.rotation_y] * k3 >> 16;
		int i4 = Rasterizer3D.COSINE[definition.rotation_y] * k3 >> 16;
		model.renderSingle(definition.rotation_x, definition.rotation_z, definition.rotation_y, definition.translate_x, l3 + model.modelHeight / 2 + definition.translate_yz, i4 + definition.translate_yz);

		sprite2.outline(1);
		if (k > 0) {
			sprite2.outline(16777215);
		}
		if (k == 0) {
			sprite2.shadow(3153952);
		}
		Raster.initDrawingArea(spriteSizeX, spriteSizeY, sprite2.myPixels);

		if (definition.certTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = spriteSizeX;
			sprite.maxHeight = spriteSizeY;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		if (definition.lendTemplateID != -1) {
			int l5 = sprite.maxWidth;
			int j6 = sprite.maxHeight;
			sprite.maxWidth = spriteSizeX;
			sprite.maxHeight = spriteSizeY;
			sprite.drawSprite(0, 0);
			sprite.maxWidth = l5;
			sprite.maxHeight = j6;
		}

		/*
		 * if (k == 0) { mruNodes1.removeFromCache(sprite2, i); }
		 */
/*
        if (k == 0 && !definition.animateInventory) {
            mruNodes1.removeFromCache(sprite2, (long) i);
        }*/

		// Client.instance.method37(i, -1);

		if (k == 0 && i != 5572 && i != 5573 && i != 640 && i != 650 && i != 630 && !definition.animateInventory) {
			if (effectTimers) {
				spriteCacheEffectTimers.put(i, sprite2);
			} else {
				mruNodes1.removeFromCache(sprite2, (long) i);
			}
		}
		Raster.initDrawingArea(j2, i2, ai1);
		Raster.setBounds(k2, i3, l2, j3);
		Rasterizer3D.textureInt1 = k1;
		Rasterizer3D.textureInt2 = l1;
		Rasterizer3D.lineOffsets = ai;
		Rasterizer3D.notTextured = true;

		if (definition.stackable) {
			sprite2.maxWidth = 33;
		} else {
			sprite2.maxWidth = 32;
		}

		sprite2.maxHeight = j;
		Client.dumpImage(sprite2, definition);
		return sprite2;
	}

	public static void nullify() {
		mruNodes2 = null;
		mruNodes1 = null;
		streamIndices = null;
		map.clear();
		map = null;
		buffer = null;
		spriteCacheEffectTimers = null;

	}

	public static void unpackConfig(Archive streamLoader) {
		buffer = new Stream(streamLoader.getDataForName("obj.dat"));
		Stream stream = new Stream(streamLoader.getDataForName("obj.idx"));
		totalItems = stream.getUnsignedShort() + 10000;
		streamIndices = new int[totalItems];
		int i = 2;

		for (int j = 0; j < totalItems; j++) {
			streamIndices[j] = i;
			i += stream.getUnsignedShort();
			//dump();
		}
	}

	public String[] actions;
	public String[] equipOptions;
	private int anInt162;
	int anInt164;
	public int maleEquip1;
	private int maleHead2;
	public int scaleX;
	private int femaleHead2;
	private int maleHead;
	private int contrast;
	private int anInt185;
	public int armsModel; // male arms
	public int scaleZ;
	public int scaleY;
	private int ambient;
	private int femaleHead;
	public int femaleEquip1;
	public int rotation_z;
	public int certID;
	public int certTemplateID;
	public byte[] description;
	public byte femaleWieldX;
	public byte femaleWieldY;
	public byte femaleWieldZ;
	public String[] groundActions;
	public int id;
	public int lendID;
	private int lendTemplateID;
	public byte maleWieldX;
	public byte maleWieldY;
	public byte maleWieldZ;
	public boolean membersObject;
	public int modelID;

	public int element;
	public int translate_x;
	public int translate_yz;
	public int rotation_y;
	public int rotation_x;
	public int modelZoom;
	public int[] originalModelColors;
	public String name;
	public int[] newModelColors;
	public boolean stackable;
	public int[] stackAmounts;
	public int[] stackIDs;
	public int team;
	public int value;
	//fuck u 
	public int hintTier = 0;

	public ItemDef() {
		id = -1;
	}

	public void copyItem(int id) {
		this.setDefaults();
		ItemDef target = ItemDef.get(id);
		this.modelID = target.modelID;
		this.maleEquip1 = target.maleEquip1;
		this.femaleEquip1 = target.femaleEquip1;
		this.modelZoom = target.modelZoom;
		this.rotation_y = target.rotation_y;
		this.rotation_x = target.rotation_x;
		this.translate_x = target.translate_x;
		this.rotation_z = target.rotation_z;
		this.translate_yz = target.translate_yz;
		this.actions = target.actions;
		this.maleHead = target.maleHead;
		this.stackable = target.stackable;
		this.scaleX = target.scaleX;
		this.scaleY = target.scaleY;
		this.scaleZ = target.scaleZ;
	}

	/*public static String testName() {
		String name = "Owner Cape"; // Example name
		if (ItemDef.renderCycleItems > 0){
			applyColorTransition(name, ItemDef.renderCycleItems);
		}
		return applyColorTransition(name, renderCycleItems);
	}
	public static int renderCycleItems;

	// Update method to be called regularly (e.g., every frame)

	public static String applyColorTransition(String text, int renderCycle) {
		int color = 0xff0000; // Default to red

		System.out.println("Attempting To apply colors");
		System.out.println(ItemDef.renderCycleItems);

		// Calculate the transition color based on the cycle
		if (ItemDef.renderCycleItems < 50) {
			color = 0xff0000 + 1280 * ItemDef.renderCycleItems; // Transition from red to yellow
		} else if (ItemDef.renderCycleItems < 100) {
			color = 0xffff00 - 0x50000 * (ItemDef.renderCycleItems - 50); // Transition from yellow to green
		} else if (ItemDef.renderCycleItems < 150) {
			color = 65280 + 5 * (ItemDef.renderCycleItems - 100); // Transition from green to a lighter green
		}

		// Convert the color to a hex string
		String hexColor = String.format("#%06X", (0xFFFFFF & color));


		// Apply the color to the text (HTML/CSS example)
		return hexColor + text ;
	}*/
	public Model getInventoryModel(int amount) {
		if (stackIDs != null && amount > 1) {
			int id = -1;

			for (int i = 0; i < 10; i++) {
				if (amount >= stackAmounts[i] && stackAmounts[i] != 0) {
					id = stackIDs[i];
				}
			}

			if (id != -1) {
				return get(id).getInventoryModel(1);
			}
		}

		Model model = (Model) mruNodes2.insertFromCache(id);

		if (model != null) {
			return model;
		}

		model = Model.fetchModel(modelID);

		if (model == null) {
			return null;
		}

		if (scaleX != 128 || scaleY != 128 || scaleZ != 128) {
			model.scaleT(scaleX, scaleZ, scaleY);
		}

		if (originalModelColors != null) {
			for (int l = 0; l < originalModelColors.length; l++) {
				model.method476(originalModelColors[l], newModelColors[l]);
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
		applyTexturing(model, id);
		model.light(64 + ambient, 768 + contrast, -50, -10, -50, true);
		model.aBoolean1659 = true;
		mruNodes2.removeFromCache(model, id);
		return model;
	}

	public boolean dialogueModelFetched(int j) {
		int k = maleHead;
		int l = maleHead2;

		if (j == 1) {
			k = femaleHead;
			l = femaleHead2;
		}

		if (k == -1) {
			return true;
		}

		boolean flag = true;

		if (!Model.isModelFetched(k)) {
			flag = false;
		}

		if (l != -1 && !Model.isModelFetched(l)) {
			flag = false;
		}

		return flag;
	}

	public Model method194(int j) {
		int k = maleHead;
		int l = maleHead2;

		if (j == 1) {
			k = femaleHead;
			l = femaleHead2;
		}

		if (k == -1) {
			return null;
		}

		Model model = Model.fetchModel(k);
		if (rdc > 0) {
			model.method1337(rdc);
		}
		if (rdc2 != 0) {
			model.method1338(rdc2);
		}
		if (rdc3 != 0) {
			model.method1339(rdc3);
		}
		applyTexturing(model, id);
		if (l != -1) {
			Model model_1 = Model.fetchModel(l);
			Model[] models = {model, model_1};
			model = new Model(2, models);
		}

		if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++) {
				model.method476(originalModelColors[i1], newModelColors[i1]);
			}
		}
		applyTexturing(model, id);
		return model;
	}

	public boolean method195(int j) {
		int k = maleEquip1;
		int l = armsModel;
		int i1 = anInt185;

		if (j == 1) {
			k = femaleEquip1;
			l = anInt164;
			i1 = anInt162;
		}

		if (k == -1) {
			return true;
		}

		boolean flag = true;

		if (!Model.isModelFetched(k)) {
			flag = false;
		}

		if (l != -1 && !Model.isModelFetched(l)) {
			flag = false;
		}

		if (i1 != -1 && !Model.isModelFetched(i1)) {
			flag = false;
		}

		return flag;
	}

	public Model getEquipModel(int i) {
		int j = maleEquip1;
		int k = armsModel;
		int l = anInt185;

		if (i == 1) {
			j = femaleEquip1;
			k = anInt164;
			l = anInt162;
		}

		if (j == -1) {
			return null;
		}

		Model model = Model.fetchModel(j);
		if (rdc > 0) {
			model.method1337(rdc);
		}
		if (rdc2 != 0) {
			model.method1338(rdc2);
		}
		if (rdc3 != 0) {
			model.method1339(rdc3);
		}
		applyTexturing(model, id);
		if (k != -1) {
			if (l != -1) {
				Model model_1 = Model.fetchModel(k);
				Model model_3 = Model.fetchModel(l);
				Model[] model_1s = {model, model_1, model_3};
				model = new Model(3, model_1s);
			} else {
				Model model_2 = Model.fetchModel(k);
				Model[] models = {model, model_2};
				model = new Model(2, models);
			}
		}

		if (i == 0 && (maleWieldX != 0 || maleWieldY != 0 || maleWieldZ != 0)) {
			model.translate(maleWieldX, maleWieldY, maleWieldZ);
		}

		if (i == 1 && (femaleWieldX != 0 || femaleWieldY != 0 || femaleWieldZ != 0)) {
			model.translate(femaleWieldX, femaleWieldY, femaleWieldZ);
		}

		if (xTranslation != 0 || yTranslation != 0 || zTranslation != 0) {
			model.translate(xTranslation, yTranslation, zTranslation);
		}

		if (originalModelColors != null) {
			for (int i1 = 0; i1 < originalModelColors.length; i1++) {
				model.method476(originalModelColors[i1], newModelColors[i1]);
			}
		}
		applyTexturing(model, id);
		return model;
	}

	public Model method202(int i) {
		if (stackIDs != null && i > 1) {
			int j = -1;

			for (int k = 0; k < 10; k++) {
				if (i >= stackAmounts[k] && stackAmounts[k] != 0) {
					j = stackIDs[k];
				}
			}

			if (j != -1) {
				return get(j).method202(1);
			}
		}

		Model model = Model.fetchModel(modelID);
		if (rdc > 0) {
			model.method1337(rdc);
		}
		if (rdc2 != 0) {
			model.method1338(rdc2);
		}
		if (rdc3 != 0) {
			model.method1339(rdc3);
		}
		applyTexturing(model, id);
		if (model == null) {
			return null;
		}

		if (originalModelColors != null) {
			for (int l = 0; l < originalModelColors.length; l++) {
				model.method476(originalModelColors[l], newModelColors[l]);
			}
		}
		applyTexturing(model, id);
		return model;
	}

	private void readValues(Stream buffer) {
		do {
			int opcode = buffer.getUnsignedByte();

			if (opcode == 0) {
				return;
			} else if (opcode == 1) {
				modelID = buffer.getUnsignedShort();
			} else if (opcode == 2) {
				name = buffer.getString();
			} else if (opcode == 3) {
				description = buffer.getBytes();
			} else if (opcode == 4) {
				modelZoom = buffer.getUnsignedShort();
			} else if (opcode == 5) {
				rotation_y = buffer.getUnsignedShort();
			} else if (opcode == 6) {
				rotation_x = buffer.getUnsignedShort();
			} else if (opcode == 7) {
				translate_x = buffer.getUnsignedShort();

				if (translate_x > 32767) {
					translate_x -= 0x10000;
				}
			} else if (opcode == 8) {
				translate_yz = buffer.getUnsignedShort();

				if (translate_yz > 32767) {
					translate_yz -= 0x10000;
				}
			} else if (opcode == 10) {
				buffer.getUnsignedShort();
			} else if (opcode == 11) {
				stackable = true;
			} else if (opcode == 12) {
				value = buffer.getIntLittleEndian();
			} else if (opcode == 16) {
				membersObject = true;
			} else if (opcode == 23) {
				maleEquip1 = buffer.getUnsignedShort();
				maleWieldY = buffer.getSignedByte();
			} else if (opcode == 24) {
				armsModel = buffer.getUnsignedShort();
			} else if (opcode == 25) {
				femaleEquip1 = buffer.getUnsignedShort();
				femaleWieldY = buffer.getSignedByte();
			} else if (opcode == 26) {
				anInt164 = buffer.getUnsignedShort();
			} else if (opcode >= 30 && opcode < 35) {
				if (groundActions == null) {
					groundActions = new String[5];
				}

				groundActions[opcode - 30] = buffer.getString();

				if (groundActions[opcode - 30].equalsIgnoreCase("hidden")) {
					groundActions[opcode - 30] = null;
				}
			} else if (opcode >= 35 && opcode < 40) {
				if (actions == null) {
					actions = new String[5];
				}

				actions[opcode - 35] = buffer.getString();
			} else if (opcode == 40) {
				int size = buffer.getUnsignedByte();
				originalModelColors = new int[size];
				newModelColors = new int[size];

				for (int k = 0; k < size; k++) {
					originalModelColors[k] = buffer.getUnsignedShort();
					newModelColors[k] = buffer.getUnsignedShort();
				}
			} else if (opcode == 78) {
				anInt185 = buffer.getUnsignedShort();
			} else if (opcode == 79) {
				anInt162 = buffer.getUnsignedShort();
			} else if (opcode == 90) {
				maleHead = buffer.getUnsignedShort();
			} else if (opcode == 91) {
				femaleHead = buffer.getUnsignedShort();
			} else if (opcode == 92) {
				maleHead2 = buffer.getUnsignedShort();
			} else if (opcode == 93) {
				femaleHead2 = buffer.getUnsignedShort();
			} else if (opcode == 95) {
				rotation_z = buffer.getUnsignedShort();
			} else if (opcode == 97) {
				certID = buffer.getUnsignedShort();
			} else if (opcode == 98) {
				certTemplateID = buffer.getUnsignedShort();
			} else if (opcode >= 100 && opcode < 110) {
				if (stackIDs == null) {
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}

				stackIDs[opcode - 100] = buffer.getUnsignedShort();
				stackAmounts[opcode - 100] = buffer.getUnsignedShort();
			} else if (opcode == 110) {
				scaleX = buffer.getUnsignedShort();
			} else if (opcode == 111) {
				scaleY = buffer.getUnsignedShort();
			} else if (opcode == 112) {
				scaleZ = buffer.getUnsignedShort();
			} else if (opcode == 113) {
				ambient = buffer.getSignedByte();
			} else if (opcode == 114) {
				contrast = buffer.getSignedByte() * 5;
			} else if (opcode == 115) {
				team = buffer.getUnsignedByte();
			} else if (opcode == 121) {
				lendID = buffer.getUnsignedShort();
			} else if (opcode == 122) {
				lendTemplateID = buffer.getUnsignedShort();
			}
		} while (true);
	}



	public void setDefaults() {
		equipOptions = new String[70];
		equipOptions[1] = "Operate";
		modelID = 0;
		name = null;
		description = null;
		newModelColors = null;
		originalModelColors = null;
		modelZoom = 2000;
		rotation_y = 0;
		rotation_x = 0;
		rotation_z = 0;
		translate_x = 0;
		translate_yz = 0;
		stackable = false;
		value = 1;
		membersObject = false;
		groundActions = null;
		actions = null;
		lendID = -1;
		lendTemplateID = -1;
		maleEquip1 = -1;
		armsModel = -1;
		femaleEquip1 = -1;
		anInt164 = -1;
		anInt185 = -1;
		anInt162 = -1;
		maleHead = -1;
		maleHead2 = -1;
		femaleHead = -1;
		femaleHead2 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		scaleX = 128;
		scaleY = 128;
		scaleZ = 128;
		ambient = 0;
		contrast = 0;
		team = 0;
		rdc = 0;
		rdc2 = 0;
		rdc3 = 0;
		femaleWieldY = 0;
		femaleWieldX = 0;
		femaleWieldZ = 0;
		maleWieldX = 0;
		maleWieldZ = 0;
		maleWieldY = 0;
	}

	private void toLend() {
		ItemDef itemDef = get(lendTemplateID);
		actions = new String[5];
		modelID = itemDef.modelID;
		translate_x = itemDef.translate_x;
		rotation_x = itemDef.rotation_x;
		translate_yz = itemDef.translate_yz;
		modelZoom = itemDef.modelZoom;
		rotation_y = itemDef.rotation_y;
		rotation_z = itemDef.rotation_z;
		value = 0;
		ItemDef definition = get(lendID);
		maleHead2 = definition.maleHead2;
		newModelColors = definition.newModelColors;
		anInt185 = definition.anInt185;
		femaleEquip1 = definition.femaleEquip1;
		femaleHead2 = definition.femaleHead2;
		maleHead = definition.maleHead;
		groundActions = definition.groundActions;
		maleEquip1 = definition.maleEquip1;
		name = definition.name;
		armsModel = definition.armsModel;
		membersObject = definition.membersObject;
		femaleHead = definition.femaleHead;
		anInt164 = definition.anInt164;
		anInt162 = definition.anInt162;
		originalModelColors = definition.originalModelColors;
		team = definition.team;

		if (definition.actions != null) {
			for (int i = 0; i < 4; i++) {
				actions[i] = definition.actions[i];
			}
		}

		actions[4] = "Discard";
	}

	private void toNote() {
		ItemDef definition = get(certTemplateID);
		modelID = definition.modelID;
		modelZoom = definition.modelZoom;
		rotation_y = definition.rotation_y;
		rotation_x = definition.rotation_x;
		rotation_z = definition.rotation_z;
		translate_x = definition.translate_x;
		translate_yz = definition.translate_yz;
		originalModelColors = definition.originalModelColors;
		newModelColors = definition.newModelColors;
		definition = get(certID);
		name = definition.name;
		membersObject = definition.membersObject;
		value = definition.value;
		String s = "a";
		char c = definition.name.charAt(0);

		if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
			s = "an";
		}

		description = ("Swap this note at any bank for " + s + " " + definition.name + ".").getBytes();
		stackable = true;
	}

	public static void printDefinitionsForId(int itemId) {
		/* Print out Grain */
		ItemDef dumpitem = ItemDef.get(itemId);
		if (dumpitem.name != null) {
			System.out.println("Dumping: " + dumpitem.name);
		} else {
			System.out.println("ItemDefinition.get(" + itemId + ").name == null");
		}
		System.out.println("itemId: " + dumpitem.id);
		System.out.println("modelId: " + dumpitem.modelID);
		System.out.println("maleEquip1: " + dumpitem.maleEquip1);
		System.out.println("femaleEquip1: " + dumpitem.femaleEquip1);
		System.out.println("modelOffset1: " + dumpitem.translate_x);
		System.out.println("modelOffSetX: " + dumpitem.rotation_z);
		System.out.println("modelOffSetY: " + dumpitem.translate_yz);
		System.out.println("modelRotationY: " + dumpitem.rotation_y);
		System.out.println("modelRotationX: " + dumpitem.rotation_x);
		System.out.println("modelZoom: " + dumpitem.modelZoom);
		// System.out.println("def "+dumpitem);
		if (dumpitem.originalModelColors != null) {
			for (int i = 0; i < dumpitem.originalModelColors.length; i++) {
				System.out.println("modifiedModelColors[" + i + "]: " + dumpitem.originalModelColors[i]);
			}
		}
		if (dumpitem.newModelColors != null) {
			for (int i = 0; i < dumpitem.newModelColors.length; i++) {
				System.out.println("originalModelColors[" + i + "]: " + dumpitem.newModelColors[i]);
			}
		}
		if (dumpitem.actions != null) {
			for (int i = 0; i < dumpitem.actions.length; i++) {
				System.out.println("Action[" + i + "]: " + dumpitem.actions[i]);
			}
		}
		if (dumpitem.groundActions != null) {
			for (int i = 0; i < dumpitem.groundActions.length; i++) {
				System.out.println("groundAction[" + i + "]: " + dumpitem.groundActions[i]);
			}
		}
	}

	public static void dump() {
		File f = new File("itemnames.txt");
		//System.out.println("Dumping Item names..");
		// String[] variableNames = new String[] { "name", };
		try {
			f.createNewFile();
			BufferedWriter bf = new BufferedWriter(new FileWriter(f));
			for (int i = 0; i < streamIndices.length; i++) {
				ItemDef item = get(i);
				if (item != null)
					if (item.name != null && !item.name.equalsIgnoreCase("dwarf remains")) {
						bf.write("Item Id: " + item.id);
						bf.newLine();
						bf.write("name: " + item.name);
						bf.newLine();
						bf.write("modelID: " + item.modelID);
						bf.newLine();
						bf.newLine();
					}
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("Dumping Complete!");
	}

}
