package org.necrotic.client.cache.media.rsinterface;

import org.necrotic.ColorConstants;
import org.necrotic.client.*;
import org.necrotic.client.cache.definition.ItemDef;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.TextDrawingArea;


public class CustomInterfaces extends RSInterface {

	private static final int CLOSE_BUTTON = 580, CLOSE_BUTTON_HOVER = 581;

	public static TextDrawingArea[] tda;

	public CustomInterfaces(TextDrawingArea[] tda) {
		CustomInterfaces.tda = tda;
	}


	private static void worldMap() {
		RSInterface map = addInterface(57350);
		map.totalChildren(3);

		addWorldMap(57351);
		addSpriteLoader(57352, 1422);

		setBounds(57351, 0, -52, 0, map);
		setBounds(57352, 46, 0, 1, map);
		map.child(2, 14650, 442, 7);
	}

	public static void addWorldMap(int id) {
		RSInterface box = addInterface(id);
		box.type = 1319;
		box.width = 400;
		box.height = 400;
		box.atActionType = 1;
	}

//	public static void commands() {
//		RSInterface tab = RSInterface.addInterface(39800);
//		RSInterface scrollTableft = RSInterface.addInterface(39840);
//		RSInterface.addSprite(39801, 44);
//		RSInterface.addText(39802, "Commands", 0xff9933, true, true, -1, tda, 0);
//
//		//Scoll bar size, witdh and scrolling size.
//		scrollTableft.width = 140;
//		scrollTableft.height = 227;
//		scrollTableft.scrollMax = 450;
//
//
//		int npcList = 50;
//		int y = 1;
//
//		for (int i = 0; i < npcList; i++) {
//			RSInterface.addText(39841 + i, "hey", tda, 1, ClientConstants.GOLD, false, false, ClientConstants.WHITE, "Select", 150);
//			//	addClickableText(39371 + i, "", "Select", tda, 2, 0xeb981f, false, true, 632);
//
//		}
//
//		int text = 50;
//		int y1 = 1;
//
//		RSInterface.setChildren(npcList, scrollTableft);
//
//		for (int i = 0; i < text; i++) {
//			scrollTableft.child(i, 39841 + i, 3, y1);
//			y1 += 15;
//		}
//
//
//		RSInterface.setChildren(3, tab);
//		tab.child(0, 39801, 10, 10);
//		tab.child(1, 39802, 244, 15);
//		tab.child(2, 39840, 471, 38);
//
//
//	}

	private static void examiner() {
		RSInterface main = addInterface(39800);
		addSpriteLoader(39801, 5765);
		addText(39802, "@mag@Athens God Abilities", 0xff8624, false, true, 52, tda, 2);

		addText(39803, "@mag@Poseidon Ability", 0xff8624, false, true, 52, tda, 2);

		addText(39804, "@mag@Future Ability", 0xff8624, false, true, 52, tda, 2);

		addText(39805, "@mag@Future Ability 2", 0xff8624, false, true, 52, tda, 2);
		main.totalChildren(5);
		main.child(0, 39801, 5, 5);
		main.child(1, 39802, 180, 10);
		main.child(2, 39803, 200, 50);
		main.child(3, 39804, 200, 80);
		main.child(4, 39805, 200, 110);



	}

	private static void dailyTask() {
		RSInterface main = addInterface(4509);
		addSpriteLoader(4510, 5531);

		addHoverButtonWSpriteLoader(4511, 90, 15, 20, "Close", -1, 4512, 1);
		addHoveredImageWSpriteLoader(4512, 55, 15, 20, 4513);

		addText(4514, "@yel@Daily Task Manager", 0xff8624, false, true, 52, tda, 2);

		addConfigButtonWSpriteLoader(4515, 4509, 5528, 5529, 110, 28, "Select Easy", 0, 5, 1098);
		addConfigButtonWSpriteLoader(4516, 4509, 5528, 5529, 110, 28, "Select Medium", 1, 5, 1098);

		addConfigButtonWSpriteLoader(4517, 4509, 5528, 5529, 110, 28, "Select Elite", 2, 5, 1098);
		addConfigButtonWSpriteLoader(4518, 4509, 5528, 5529, 110, 28, "Select Master", 3, 5, 1098);

		addSpriteLoader(4519, 5611);
		addSpriteLoader(4520, 5532);
		addSpriteLoader(4521, 5533);
		addSpriteLoader(4522, 5534);

		addText(4523, "Easy Task", 0xff8624, false, true, 52, tda, 0);
		addText(4524, "Medium Task", 0xff8624, false, true, 52, tda, 0);
		addText(4525, "Elite Task", 0xff8624, false, true, 52, tda, 0);
		addText(4526, "Master Task", 0xff8624, false, true, 52, tda, 0);

		RSInterface scroll = addInterface(4527);
		scroll.width = 407;
		scroll.height = 223;
		scroll.scrollMax = 290;

		addSpriteLoader(4528, 5607);
		addSpriteLoader(4529, 5608);

		 addHoverButtonWSpriteLoader( 4530, 5527, 132, 30, "Accept", -1, 4531, 1);
		addHoveredImageWSpriteLoader(4531, 5526, 132, 30, 4532);

		addHoverButtonWSpriteLoader(4533, 5527, 132, 30, "Rewards", -1, 4534, 1);
		addHoveredImageWSpriteLoader(4534, 5526, 132, 30, 4535);

		addHoverButtonWSpriteLoader(4536, 5527, 132, 30, "Accept", -1, 4537, 1);
		addHoveredImageWSpriteLoader(4537, 5526, 132, 30, 4538);

		addHoverButtonWSpriteLoader(4539, 5527, 132, 30, "Rewards", -1, 4540, 1);
		addHoveredImageWSpriteLoader(4540, 5526, 132, 30, 4541);

		addHoverButtonWSpriteLoader(4542, 5527, 132, 30, "Accept", -1, 4543, 1);
		addHoveredImageWSpriteLoader(4543, 5526, 132, 30, 4544);

		addHoverButtonWSpriteLoader(4545, 5527, 132, 30, "Rewards", -1, 4546, 1);
		addHoveredImageWSpriteLoader(4546, 5526, 132, 30, 4547);

		addHoverButtonWSpriteLoader(4548, 5527, 132, 30, "Accept", -1, 4549, 1);
		addHoveredImageWSpriteLoader(4549, 5526, 132, 30, 4550);

		addHoverButtonWSpriteLoader(4551, 5527, 132, 30, "Rewards", -1, 4552, 1);
		addHoveredImageWSpriteLoader(4552, 5526, 132, 30, 4553);

		addHoverButtonWSpriteLoader(4554, 5527, 132, 30, "Accept", -1, 4555, 1);
		addHoveredImageWSpriteLoader(4555, 5526, 132, 30, 4556);

		addHoverButtonWSpriteLoader(4557, 5527, 132, 30, "Rewards", -1, 4558, 1);
		addHoveredImageWSpriteLoader(4558, 5526, 132, 30, 4559);

		addText(4560, "Accept Task", 0xff8624, false, true, 52, tda, 0);
		addText(4561, "Rewards", 0xff8624, false, true, 52, tda, 0);

		addSpriteLoader(4562, 5609);
		addSpriteLoader(4563, 5609);
		addSpriteLoader(4564, 5609);
		addSpriteLoader(4565, 5609);
		addSpriteLoader(4566, 5609);

		addPercentageBar(4567, 300, 100, 0x000000, 0xc98b17, 7, false);
		addPercentageBar(4568, 300, 100, 0x000000, 0xc98b17, 7, false);
		addPercentageBar(4569, 300, 100, 0x000000, 0xc98b17, 7, false);
		addPercentageBar(4570, 300, 100, 0x000000, 0xc98b17, 7, false);
		addPercentageBar(4571, 300, 100, 0x000000, 0xc98b17, 7, false);

		addText(4572,
				"Here is some text that i am writing right now, it can be\\nwhatever i want but this text is mine whatever text comes \\nnext is my own",
				0xff8624, false, true, 52, tda, 1);
		addText(4573,
				"Here is some text that i am writing right now, it can be\\nwhatever i want but this text is mine whatever text comes \\nnext is my own",
				0xff8624, false, true, 52, tda, 1);
		addText(4574,
				"Here is some text that i am writing right now, it can be\\nwhatever i want but this text is mine whatever text comes \\nnext is my own",
				0xff8624, false, true, 52, tda, 1);
		addText(4575,
				"Here is some text that i am writing right now, it can be\\nwhatever i want but this text is mine whatever text comes \\nnext is my own",
				0xff8624, false, true, 52, tda, 1);
		addText(4576,
				"Here is some text that i am writing right now, it can be\\nwhatever i want but this text is mine whatever text comes \\nnext is my own",
				0xff8624, false, true, 52, tda, 1);

		scroll.totalChildren(50);
		scroll.child(0, 4528, 0, 0);
		scroll.child(1, 4529, 0, 58);
		scroll.child(2, 4528, 0, 116);
		scroll.child(3, 4529, 0, 174);
		scroll.child(4, 4528, 0, 232);

		scroll.child(5, 4530, 313, 3);
		scroll.child(6, 4531, 313, 3);
		scroll.child(7, 4533, 313, 30);
		scroll.child(8, 4534, 313, 30);

		scroll.child(9, 4536, 313, 62);
		scroll.child(10, 4537, 313, 62);
		scroll.child(11, 4539, 313, 89);
		scroll.child(12, 4540, 313, 89);

		scroll.child(13, 4542, 313, 120);
		scroll.child(14, 4543, 313, 120);
		scroll.child(15, 4545, 313, 147);
		scroll.child(16, 4546, 313, 147);

		scroll.child(17, 4548, 313, 178);
		scroll.child(18, 4549, 313, 178);
		scroll.child(19, 4551, 313, 205);
		scroll.child(20, 4552, 313, 205);

		scroll.child(21, 4554, 313, 237);
		scroll.child(22, 4555, 313, 237);
		scroll.child(23, 4557, 313, 264);
		scroll.child(24, 4558, 313, 264);

		scroll.child(25, 4560, 330, 9);
		scroll.child(26, 4561, 337, 36);

		scroll.child(27, 4560, 330, 67);
		scroll.child(28, 4561, 337, 95);

		scroll.child(29, 4560, 330, 125);
		scroll.child(30, 4561, 337, 153);

		scroll.child(31, 4560, 330, 183);
		scroll.child(32, 4561, 337, 211);

		scroll.child(33, 4560, 330, 242);
		scroll.child(34, 4561, 337, 270);

		scroll.child(35, 4562, 5, 42);
		scroll.child(36, 4563, 5, 100);
		scroll.child(37, 4564, 5, 159);
		scroll.child(38, 4565, 5, 217);
		scroll.child(39, 4566, 5, 275);

		scroll.child(40, 4567, 5, 42);
		scroll.child(41, 4568, 5, 100);
		scroll.child(42, 4569, 5, 159);
		scroll.child(43, 4570, 5, 217);
		scroll.child(44, 4571, 5, 275);

		scroll.child(45, 4572, 5, 7);
		scroll.child(46, 4573, 5, 65);
		scroll.child(47, 4574, 5, 124);
		scroll.child(48, 4575, 5, 182);
		scroll.child(49, 4576, 5, 240);

		RSInterface childmain = addInterface(4580);
		addSpriteLoader(4581, 5610);

		addText(4582, "Rewards", 0xff8624, false, true, 52, tda, 2);

		addHoverButtonWSpriteLoader(4583, 90, 15, 20, "Close", -1, 4584, 1);
		addHoveredImageWSpriteLoader(4584, 55, 15, 20, 4585);
		addToItemGroup(4586, 5, 100, 5, 6, true, new String[] { null, null, null, null, null });

		childmain.totalChildren(5);
		childmain.child(0, 4581, 55, 5);
		childmain.child(1, 4582, 135, 15);
		childmain.child(2, 4583, 247, 14);
		childmain.child(3, 4584, 247, 14);
		childmain.child(4, 4586, 75, 50);

		addText(4591, "Timer is here", 0xff8624, false, true, 52, tda, 1);

		main.totalChildren(19);
		main.child(0, 4510, 30, 15);
		main.child(1, 4511, 452, 24);
		main.child(2, 4512, 452, 24);
		main.child(3, 4514, 200, 24);
		main.child(4, 4515, 40, 53);
		main.child(5, 4516, 147, 53);
		main.child(6, 4517, 254, 53);
		main.child(7, 4518, 361, 53);
		main.child(8, 4519, 45, 57);
		main.child(9, 4520, 152, 57);
		main.child(10, 4521, 259, 57);
		main.child(11, 4522, 367, 57);
		main.child(12, 4523, 65, 59);
		main.child(13, 4524, 172, 59);
		main.child(14, 4525, 279, 59);
		main.child(15, 4526, 387, 59);
		main.child(16, 4527, 42, 82);
		main.child(17, 4580, 75, 75);
		main.child(18, 4591, 360, 25);

	}

	public static void gameModeSelection(TextDrawingArea[] tda) {
		RSInterface main = addInterface(48400);

		addSpriteLoader(48401, 4163);

		addHoverButtonWSpriteLoader(48402, 3559, 100, 20, "Next", 0, 48403, 5);
		addHoveredImageWSpriteLoader(48403, 3558, 100, 20, 48404);
		addHoverButtonWSpriteLoader(48405, 3559, 100, 20, "Next", 0, 48406, 1);
		addHoveredImageWSpriteLoader(48406, 3558, 100, 20, 48407);
		addHoverButtonWSpriteLoader(48408, 3559, 100, 20, "Next", 0, 48409, 1);
		addHoveredImageWSpriteLoader(48409, 3558, 100, 20, 48410);

		addText(48414, "Welcome to Athens", tda, 2, 0xAF70C3, true, true);

		addText(48415, "@or2@Normal", tda, 1, 0xffffff, true, true);

		addText(48416, "@or2@Easy", tda, 1, 0xffffff, true, true);

		addText(48417, "@or2@Sinister", tda, 1, 0xffffff, true, true);

		addText(48419, "Mode Selection", tda, 1, 0xffffff, true, true);

		addText(48420, "Description", tda, 1, 0xffffff, true, true);

		addText(48421, "x", tda, 0, 0xffffff, false, true);

		addText(48422, "x", tda, 0, 0xffffff, false, true);

		addText(48423, "x", tda, 0, 0xffffff, false, true);

		addHoverButtonWSpriteLoader(48425, 4166, 83, 20, "Confirm", 0, 48426, 1);
		addHoveredImageWSpriteLoader(48426, 4167, 83, 20, 48427);
		addText(48428, "Confirm", tda, 1, 0xffffff, true, true);

		addChar(48429, 700);

		main.totalChildren(20);
		main.child(0, 48401, 39, 25);


		main.child(1, 48402, 124, 118);
		main.child(2, 48403, 124, 118);
		main.child(3, 48405, 124, 148);
		main.child(4, 48406, 124, 148);
		main.child(5, 48408, 124, 178);
		main.child(6, 48409, 124, 178);
		main.child(7, 48414, 265, 45);
		main.child(8, 48415, 164, 120);
		main.child(9, 48416, 164, 150);
		main.child(10, 48417, 164, 181);
		main.child(11, 48419, 164, 93);
		main.child(12, 48420, 112, 214);
		main.child(13, 48421, 68, 238);
		main.child(14, 48422, 68, 254);
		main.child(15, 48423, 68, 270);
		main.child(16, 48425, 188, 212);
		main.child(17, 48426, 188, 212);
		main.child(18, 48428, 230, 214);
		main.child(19, 48429, 305, 175);
	}

    public static void underwater_overlay() {
        RSInterface fuck = addInterface(18824);
        addRectangle(18825, 190, 0xB8DCFE, true, 512, 334);
        fuck.totalChildren(1);
        fuck.child(0, 18825, 0, 0);
    }

	public static void upgradeInterface(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(62200);

		addSpriteLoader(62201, 1244);
		addText(62211, "How do I Empower", tda, 1, 0xFFA500, true, true);

		addButtonWSpriteLoader(62202, 1245, "Empower X1", 241, 30);
		addButtonWSpriteLoader(62247, 1245, "Empower All", 241, 30);

		hoverButton(62202, 1457, 1458, "Empower x1", 2, 0xff8624, "Empower x1");
		hoverButton(62247, 1457, 1458, "Empower All", 2, 0xff8624, "Empower All");

		addButtonWSpriteLoader(62241, 1344, "Select 0", 87, 33);
		addButtonWSpriteLoader(62235, 1344, "Select 1", 87, 32);
		addButtonWSpriteLoader(62236, 1344, "Select 2", 87, 32);
		addButtonWSpriteLoader(62240, 1344, "Select 3", 87, 32);
		addButtonWSpriteLoader(62243, 1344, "Select 4", 87, 32);
		addButtonWSpriteLoader(62244, 1345, "Select MAX", 50, 32);

		addConfigButtonWSpriteLoader(62241, 62200, 1506, 1507, 79, 20, "Select", 0, 5, 1355);
		addConfigButtonWSpriteLoader(62235, 62200, 1506, 1507, 79, 20, "Select", 1, 5, 1355);
		addConfigButtonWSpriteLoader(62236, 62200, 1506, 1507, 79, 20, "Select", 2, 5, 1355);
		addConfigButtonWSpriteLoader(62240, 62200, 1506, 1507, 79, 20, "Select", 3, 5, 1355);
		addConfigButtonWSpriteLoader(62243, 62200, 1506, 1507, 79, 20, "Select", 4, 5, 1355);
		addConfigButtonWSpriteLoader(62244, 62200, 1506, 1507, 79, 20, "Select", 5, 5, 1355);


		addText(62204, "", tda, 2, 0xFFA500);
		addText(62205, "Choose the item you would like to Empower", tda, 1, 0xFFA500, true, true);
		addText(62206, "", tda, 2, 0xFFA500);
		addText(62248, "", tda, 2, 0xFFA500);
		addText(62203, "Product", tda, 2, 0xFFA500, true, true);
		addText(62231, "Coins Needed", tda, 1, 0x000000, true, true);
		addText(62234, "Chance: ", tda, 1, 0x000000, true, true);
		addText(62232, "Make sure the Item is in your Inventory!", tda, 0, 0xe6e5e6, true, true);
		addText(62233, "Empower orbs are obtained from dissolving items, PvM drops.", tda, 0, 0xe6e5e6, true, true);
		addText(62242, "Tier 1", tda, 1, 0xFFA500);
		addText(62237, "Tier 2", tda, 1, 0xFFA500);
		addText(62238, "Tier 3", tda, 1, 0xFFA500);
		addText(62239, "Tier 4", tda, 1, 0xFFA500);
		addText(62245, "Tier 5", tda, 1, 0xFFA500);
		addText(62246, "Empower", tda, 1, 0xFFA500);
		addToItemGroup(62209, 6, 10, 8, 5, true, new String[]{"Select", null, null, null, null});

		addToItemGroup(62210, 3, 10, 17, 3, true, new String[]{"Select", null, null, null, null}); // try now.

		addButtonWSpriteLoader(62230, 1238, "Close");

		tab.totalChildren(28);
		int x = 10, y = 10;
		tab.child(0, 62201, 10, 10); // sprite bg

		tab.child(3, 62204, 215, 18); // empty

		tab.child(2, 62203, 373 + 29, 70); // reward if successful

		tab.child(4, 62205, 141 + 18, 66); //select the item you want to upgrade
		tab.child(11, 62232, 141 + 18, 79);//the item must be in ur inventory

		tab.child(1, 62202, 314 + 29, 206);//upgrade x1
		tab.child(5, 62206, 360, 229);//upgrade x1 text

		tab.child(6, 62208, 32, 92); // items
		tab.child(7, 62210, 355 + 29, 111); // reward item
		tab.child(9, 62230, 480, 13);

		tab.child(10, 62231, 373 + 29, 170);//dust requred
		tab.child(13, 62234, 373 + 29, 185);//upgrade %

		tab.child(8, 62211, 259, 284);//how to get pixue
		tab.child(12, 62233, 259, 298);//pixue dust is obtained from


		tab.child(14, 62241, 21, 35);// 00 hover
		tab.child(15, 62235, 100, 35);//01 hover
		tab.child(16, 62236, 179, 35);//02 hover
		tab.child(17, 62240, 258, 35);//03 hover
		tab.child(18, 62243, 337, 35);// 04 hover
		tab.child(19, 62244, 416, 35);// MAX hover

		tab.child(20, 62242, 28, 39);// 00 text
		tab.child(21, 62237, 107, 39);// 01 text
		tab.child(22, 62238, 186, 39);// 02 text
		tab.child(23, 62239, 265, 39);//03 text
		tab.child(24, 62245, 344, 39);// 04 text
		tab.child(25, 62246, 423, 39);// max text

		tab.child(26, 62247, 343, 237); // upgrade all
		tab.child(27, 62248, 360, 259); // upgrade all text

		RSInterface scrollInterface = addTabInterface(62208);
		scrollInterface.parentID = 62200;
		scrollInterface.width = 273 - 16;
		scrollInterface.height = 170;
		scrollInterface.scrollMax = 1000;
		scrollInterface.totalChildren(1);
		scrollInterface.child(0, 62209, 5, 5);

	}



	private void shopInterface() {
		RSInterface rsinterface = addTabInterface(3824);
		setChildren(6, rsinterface);
		addSpriteLoader(3825, 736);
		addCloseButton(3902, 19689, 19690);
		addText(3901, "", tda, 2, 0xAF70C3, true, true);

		setBounds(3825, 6, 8, 0, rsinterface);
		setBounds(3902, 483, 15, 1, rsinterface);
		setBounds(3902, 483, 15, 2, rsinterface);
		setBounds(3900, 26, 50, 3, rsinterface);
		setBounds(3901, 265, 17, 4, rsinterface);
		setBounds(19689, 483, 15, 5, rsinterface);
		rsinterface = interfaceCache[3900];
		rsinterface.inv = new int[50];
		rsinterface.invStackSizes = new int[50];
		rsinterface.drawInfinity = true;
		rsinterface.invSpritePadX = 15;
		rsinterface.width = 10;
		rsinterface.height = 4;
		rsinterface.invSpritePadY = 25;

	}

	public static void opacityInterface() {
		RSInterface rsi = addTabInterface(35555);
		setChildren(1, rsi);
		addRectangle(35556, 128, 0x000000, true, 30, 34);
		setBounds(35556, 0, 0, 0, rsi);
	}

	public static void staffTabInterface(TextDrawingArea[] tda) {
		RSInterface tab = addTabInterface(46343);
		RSInterface scroll = addTabInterface(32385);
		addText(32386, "Staff Team", tda, 1, 0xAF70C3, true, true);
		//"Friends MRUNodes", tda, 1, 0xff9933, true, true);
		addText(32394, "", tda, 0, 0xff9933, true, true);
		addText(32395, "PM Staff any Questions", tda, 0, ColorConstants.RS_ORANGE, true, true);
		addText(32396, "Online Staff: 1", tda, 2, 0xAF70C3, true, true);

		addSpriteLoader(32391, 1240);
		addSpriteLoader(32392, 1241);
		addSpriteLoader(32393, 1240);

		scroll.totalChildren(50);
		scroll.width = 174;
		scroll.height = 164;
		scroll.scrollMax = 176;
		tab.totalChildren(9);

		tab.child(0, 32386, 95, 4);// title
		tab.child(1, 32392, 0, 25);// bg
		tab.child(2, 32391, 0, 25);// first line
		tab.child(3, 32393, 0, 193);// middle line
		tab.child(4, 32393, 0, 250);// bottom line
		tab.child(5, 32385, 0, 28);// scroll
		tab.child(6, 32394, 87, 200);
		tab.child(7, 32395, 94, 210);
		tab.child(8, 32396, 94, 230);

		int child = 0;
		int y = 3;
		for (int i = 0; i < 50; i++) {
			scroll.child(child + i, 32410 + i, 15, y);
			y += 16;
			addClickableText(32410 + i, "", null, tda, 1, 0xFF8900, 130, 13);
			//addText(32410 + i, "Staff - " + (i + 1), tda, 1, 0xff9933, true, false);
		}
	}

	public static void panelInterfaceInterfaces() {
		int interID = 111500;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 0;
		int y = 0;

		tab.totalChildren(4);

		addSpriteLoader(id, 1563);
		tab.child(c++, id++, 2 + x, 30 + y);

		tab.child(c++, 111100, x, y);

		addText(id, "", tda, 2, 0xA4A4A4, true, true);
		tab.child(c++, id++, 95 + x, 34 + y);

		tab.child(c++, 111600, 4 + x, 58 + y);



		interID = 111600;
		RSInterface info = addInterface(interID);
		info.width = 182 - 16;
		info.height = 195;
		info.scrollMax = 100;
		id = interID + 1;
		c = 0;
		x = 3;
		y = 3;
		info.totalChildren(40);

		String[] names = new String[]{"Achievements", "Collection Log", "Drop Tables", "Enchantment","Items List", "Leaderboards", "Loot Tables", "Progression Tasks", "Skill Tree", "Teleports"};
		int[] spriteIds = { 859, 3423, 859, 3423, 859, 3423, 859, 3423, 859, 3423};



		for (int i = 0; i < 10; i++) {
			addRectangle(id, 256, 0x000000, true, 168, 23);
			info.child(c++, id++, 1 + x, y);

			addRectangleClickable(id, 256, 0x1E1A15, true, 166, 21);
			info.child(c++, id++, 2 + x, y + 1);

			addText(id, names[i], 0xAF70C3, false, true, 100, tda, 1);
			info.child(c++, id++, 21 + x, y + 4);

			addSpriteLoader(id, spriteIds[i]);  // Access sprite ID from the array
			info.child(c++, id++, 4 + x, y + 4);
			y += 26;
			//iz++;
		}
	}


	static void membershipInterface() {
		int interID = 49800;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		tab.totalChildren(3);
		addSpriteLoader(id, 2020);
		tab.child(c++, id++, 0, 205);
		addHoverButtonWSpriteLoader(id, 2013, 22, 22, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 487, 208);
		addHoverButtonWSpriteLoader(id, 2012, 16, 16, "Open Store", -1, id + 2, 1);
		tab.child(c++, id++, 232, 295);
	}

	static void staffPanel() {
		int interID = 46800;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		tab.totalChildren(18);
		addSpriteLoader(id, 2021);
		tab.child(c++, id++, 50, 50);
		hoverButton(id, 1016, 1017, "Close", 2, 0xff8624, "");
		tab.child(c++, id++, 445, 58);


		//COLUMN 1
		hoverButton(id, 1608, 1609, "Ban", 2, 0xff8624, "Ban");
		tab.child(c++, id++, 75, 130);
		hoverButton(id, 1608, 1609, "Un-Ban", 2, 0xff8624, "Un-Ban");
		tab.child(c++, id++, 75, 170);
		hoverButton(id, 1608, 1609, "Mute", 2, 0xff8624, "Mute");
		tab.child(c++, id++, 75, 210);
		hoverButton(id, 1608, 1609, "Un-Mute", 2, 0xff8624, "Un-Mute");
		tab.child(c++, id++, 75, 250);

		//COLUMN 2
		hoverButton(id, 1608, 1609, "Kick", 2, 0xff8624, "Kick");
		tab.child(c++, id++, 175, 130);
		hoverButton(id, 1608, 1609, "Jail", 2, 0xff8624, "Jail");
		tab.child(c++, id++, 175, 170);
		hoverButton(id, 1608, 1609, "Un-Jail", 2, 0xff8624, "Un-Jail");
		tab.child(c++, id++, 175, 210);
		hoverButton(id, 1608, 1609, "Alert", 2, 0xff8624, "Alert");
		tab.child(c++, id++, 175, 250);

		//COLUMN 3
		hoverButton(id, 1608, 1609, "Alts", 2, 0xff8624, "Alts");
		tab.child(c++, id++, 275, 130);
		hoverButton(id, 1608, 1609, "StaffZone", 2, 0xff8624, "StaffZone");
		tab.child(c++, id++, 275, 170);
		hoverButton(id, 1608, 1609, "Move Home", 2, 0xff8624, "Move Home");
		tab.child(c++, id++, 275, 210);
		hoverButton(id, 1608, 1609, "Kill", 2, 0xff8624, "Kill");
		tab.child(c++, id++, 275, 250);

		//COLUMN 4
		hoverButton(id, 1608, 1609, "Invis", 2, 0xff8624, "Invis");
		tab.child(c++, id++, 375, 130);
		hoverButton(id, 1608, 1609, "TeleTo", 2, 0xff8624, "TeleTo");
		tab.child(c++, id++, 375, 170);
		hoverButton(id, 1608, 1609, "TeleToMe", 2, 0xff8624, "TeleToMe");
		tab.child(c++, id++, 375, 210);
		hoverButton(id, 1608, 1609, "-", 2, 0xff8624, "-");
		tab.child(c++, id++, 375, 250);

	}

	static void cosmeticPanel() {
		int interID = 42600;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;

		int buttonSpriteID = 2024;
		int buttonHoverSpriteId = 2023;
		int firstColumX = 114;
		int secondColumX = 352;
		int allY1 = 55;
		int allY2 = 95;
		int allY3 = 135;
		int allY4 = 175;
		int allY5 = 215;

		tab.totalChildren(13);
		addSpriteLoader(id, 2022);
		tab.child(c++, id++, 106, 25);
		hoverButton(id, 1016, 1017, "Close", 2, 0xff8624, "");
		tab.child(c++, id++, 398, 32);

		//COLUMN 1
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Helmet", 2, 0xff8624, "Helmet");
		tab.child(c++, id++, firstColumX, allY1);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Body", 2, 0xff8624, "Body");
		tab.child(c++, id++, firstColumX, allY2);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Legs", 2, 0xff8624, "Legs");
		tab.child(c++, id++, firstColumX, allY3);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Gloves", 2, 0xff8624, "Gloves");
		tab.child(c++, id++, firstColumX, allY4);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Boots", 2, 0xff8624, "Boots");
		tab.child(c++, id++, firstColumX, allY5);
		//COLUMN 2
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Shield", 2, 0xff8624, "Shield");
		tab.child(c++, id++, secondColumX, allY1);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Weapon", 2, 0xff8624, "Weapon");
		tab.child(c++, id++, secondColumX, allY2);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Cape", 2, 0xff8624, "Cape");
		tab.child(c++, id++, secondColumX, allY3);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Amulet", 2, 0xff8624, "Amulet");
		tab.child(c++, id++, secondColumX, allY4);
		hoverButton(id, buttonSpriteID, buttonHoverSpriteId, "Other", 2, 0xff8624, "Other");
		tab.child(c++, id++, secondColumX, allY5);

		addChar(15125, 700);
		tab.child(c++, 15125, 200, 150);
	}

	public static void raids(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(58000);
		int c = 0;
		int x = 0;
		int y = 0;
		tab.totalChildren(8);
		int id = 58001;
		id++;

		addText(id, "@whi@Athens Raids Party: 0", fonts, 2, 16750623, true, true);
		tab.child(c++, id++, 95 + x, 10 + y);
		tab.child(c++, 58015, 0 + x, 35 + y);

		addHoverButtonWSpriteLoader(id, 5723, 50, 24, "Invite", -1, id + 1, 1);
		tab.child(c++, id++, 30 + x, 234 + y);
		addHoveredImageWSpriteLoader(id, 5724, 50, 24, id + 1);
		tab.child(c++, id++, 30 + x, 234 + y);
		id++;

		addHoverButtonWSpriteLoader(id, 5723, 50, 24, "Leave", -1, id + 1, 1);
		tab.child(c++, id++, 110 + x, 234 + y);
		addHoveredImageWSpriteLoader(id, 5724, 50, 24, id + 1);
		tab.child(c++, id++, 110 + x, 234 + y);
		id++;

		addText(id, "@gre@Create", tda, 1, 0xFFA500, true, true);
		tab.child(c++, id++, 55 + x, 238 + y);

		addText(id, "@red@Leave", tda, 1, 0xFFA500, true, true);
		tab.child(c++, id++, 135 + x, 238 + y);

		RSInterface scroll = addInterface(58015);
		scroll.totalChildren(48);
		scroll.width = 173;
		scroll.height = 190;
		scroll.scrollMax = 12 * 20;
		y = 0;
		c = 0;
		id = 58016;
		for (int i = 0; i < 12; i++) {
			int color = i % 2 == 0 ? 0x564c42 : 0x483f33;

			RSInterface.addRectangle(id, 0, color, true, 198, 20);
			RSInterface.interfaceCache[id].hovers = true;
			RSInterface.interfaceCache[id].enabledOpacity = 100;
			RSInterface.interfaceCache[id].hoverType = id;
			RSInterface.interfaceCache[id].enabledColor = 0x22105C;
			scroll.child(c++, id++, 0, 0 + y);

			addText(id, "", tda, 0, 0xFFA500, false, true);
			scroll.child(c++, id++, 10, 4 + y);

			addText(id, "", tda, 0, 0xFFA500, true, true);
			scroll.child(c++, id++, 76 + x, 4 + y);

			addText(id, "", tda, 0, 0xFFA500, true, true);
			scroll.child(c++, id++, 137 + x, 4 + y);
			y += 20;
		}
	}

	public static void panelInterfaceAccountInfo() {
		int interID = 111300;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 0;
		int y = 0;

		tab.totalChildren(4);

		addSpriteLoader(id, 1563);
		tab.child(c++, id++, 2 + x, 30 + y);

		tab.child(c++, 111100, x, y);

		addText(id, "", tda, 2, 0xAF70C3, true, true);
		tab.child(c++, id++, 95 + x, 34 + y);

		tab.child(c++, 111400, 4 + x, 58 + y);

		interID = 111400;
		RSInterface info = addInterface(interID);
		info.width = 182 - 16;
		info.height = 195;
		info.scrollMax = 200;
		id = interID + 1;
		c = 0;
		x = -5;
		y = 3;
		info.totalChildren(27);

		addText(id, "Main", tda, 2, 0xAF70C3, true, true);
		info.child(c++, id++, 85 + x, y);
		y += 20;

		for (int i = 0; i < 9; i++) {
			addText(id, "id: " + id, tda, 1, 0xAF70C3, false, true);
			info.child(c++, id++, 10 + x, y);
			y += 17;
		}

		y += 5;
		addText(id, "Points", tda, 2, 0xAF70C3, true, true);
		info.child(c++, id++, 85 + x, y);
		y += 20;

		for (int i = 0; i < 6; i++) {
			addText(id, "id: " + id, tda, 1, 0xAF70C3, false, true);
			info.child(c++, id++, 10 + x, y);
			y += 17;
		}

		y += 5;
		addText(id, "Slayer", tda, 2, 0xAF70C3, true, true);
		info.child(c++, id++, 85 + x, y);
		y += 20;

		for (int i = 0; i < 9; i++) {
			addText(id, "", tda, 1, 0xAF70C3, false, true);
			info.child(c++, id++, 10 + x, y);
			y += 17;
		}
	}
// USED PLAYER PANEL
public static void panelInterface() {
	int interID = 111000;
	RSInterface tab = addInterface(interID);
	int id = interID + 1;
	int c = 0;
	int x = 0;
	int y = 0;
	tab.totalChildren(4);

	addSpriteLoader(id, 1563);
	tab.child(c++, id++, 2 + x, 30 + y);

	tab.child(c++, 111100, x, y);

	addText(id, "", tda, 2, 0xAF70C3, true, true);
	tab.child(c++, id++, 95 + x, 34 + y);

	tab.child(c++, 111200, 4 + x, 58 + y);


	interID = 111200;
	RSInterface info = addInterface(interID);
	info.width = 182 - 16;
	info.height = 195;
	info.scrollMax = 290; // Increase scrollMax to accommodate the added lines
	id = interID + 1;
	c = 0;
	x = -5;
	y = 3;
	info.totalChildren(20); // Increase totalChildren to accommodate the added lines

	addText(id, "Main", tda, 2, 0xAF70C3, true, true);
	info.child(c++, id++, 85 + x, y);
	y += 20;

	for (int i = 0; i < 8; i++) { // Increase the loop iterations from 2 to 7
		addText(id, "id: " + id, tda, 1, 0xAF70C3, false, true);
		info.child(c++, id++, 10 + x, y);
		y += 17;
	}
    id += 11;

	y += 9; // Change this value to 9
	addText(id, "Useful Links", tda, 2, 0xAF70C3, true, true);
	info.child(c++, id++, 85 + x, y);
	y += 20;
	for (int i = 0; i < 10; i++) { // Increase the loop iterations from 5 to 10
		teleportText(id, "", "Select", fonts, 1, 0xAF70C3, false, true, 154, 17);
		info.child(c++, id++, 10 + x, y);
		y += 17;
	}


		interID = 111100;
		RSInterface list = addInterface(interID);
		list.width = 190;
		list.height = 44;
		list.scrollMax = 44;
		id = interID + 1;
		c = 0;
		x = 0;
		y = 0;
		list.totalChildren(10);

		addConfigButtonWSpriteLoader(id, interID, 1564, 1565, 31, 27, "View World Info", 0, 5, 6000);
		list.child(c++, id++, 48 + x, 5 + y);
		addConfigButtonWSpriteLoader(id, interID, 1564, 1565, 31, 27, "View Account Info", 1, 5, 6000);
		list.child(c++, id++, 78 + x, 5 + y);
		addConfigButtonWSpriteLoader(id, interID, 1564, 1565, 31, 27, "View Interfaces", 2, 5, 6000);
		list.child(c++, id++, 108 + x, 5 + y);

		addText(id, "", tda, 2, 0xAF70C3, true, true);
	//	addConfigButtonWSpriteLoader(id, interID, 1564, 1565, 31, 27, "View Achievements", 3, 5, 6000);
		list.child(c++, id++, 108 + x, 5 + y);
		addText(id, "", tda, 2, 0xAF70C3, true, true);
		//addConfigButtonWSpriteLoader(id, interID, 1564, 1565, 31, 27, "View Raids", 4, 5, 6000);
		list.child(c++, id++, 138 + x, 5 + y);


		addSpriteLoader(id, 1566);
		list.child(c++, id++, 56 + x, 11 + y);
		addSpriteLoader(id, 1569);
		list.child(c++, id++, 83 + x, 8 + y);
		addSpriteLoader(id, 1568);
		list.child(c++, id++, 113 + x, 7 + y);
		addText(id, "", tda, 2, 0xAF70C3, true, true);
		//addSpriteLoader(id, 1567);
		list.child(c++, id++, 115 + x, 10 + y);
		addText(id, "", tda, 2, 0xAF70C3, true, true);
		//addSpriteLoader(id, 1570);
		list.child(c++, id++, 145 + x, 10 + y);


	}

	static void
	possibleLoot() {
		int interID = 101000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 35;
		int y = 35;
		tab.totalChildren(8);

		addSpriteLoader(id, 1439);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 418 + x, 3 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 418 + x, 3 + y);
		id++;

		addText(id, "Loot Rewards", tda, 2, 0xAF70C3, true, true);
		tab.child(c++, id++, 239 + x, 4 + y);

		addText(id, "Items", tda, 2, 0xAF70C3, true, true);
		tab.child(c++, id++, 85 + x, 29 + y);

		addText(id, "Rewards", tda, 2, 0xAF70C3, true, true);
		tab.child(c++, id++, 295 + x, 29 + y);

		tab.child(c++, 101250, 11 + x, 47 + y);
		tab.child(c++, 101100, 162 + x, 47 + y);

		interID = 101100;
		RSInterface items = addTabInterface(interID);
		items.totalChildren(105);
		items.height = 208;
		items.width = 269 - 16;
		items.scrollMax = 500;
		id = interID + 1;
		y = 2;
		c = 0;
		x = 1;

		for (int z = 0; z < 15; z++) {
			for (int i = 0; i < 7; i++) {
				dropGroup(id, 1, 1, 1, 1);
				items.child(c++, id++, x, y);
				x += 36;
			}
			x = 0;
			y += 35;
		}
		x = 25;
		y = 20;

		interID = 101250;
		RSInterface list = addTabInterface(interID);
		list.totalChildren(124);
		list.height = 208;
		list.width = 144 - 16;
		list.scrollMax = 480;
		id = interID + 1;
		y = 0;
		c = 0;
		for (int i = 0; i < 22; i++) {
			addSpriteLoader(id, 1426);
			list.child(c++, id++, 0, y);
			y += 80;
		}

		y = 3;
		for (int i = 0; i < 51; i++) {
			dropGroup(id, 1, 1, 1, 1);
			list.child(c++, id++, 1, y);
			addClickableText(id, "Mystery box", "Select", tda, 1, 0xFF8900, false, true, 130);
			list.child(c++, id++, 5, y + 9);
			y += 40;
		}

	}

	public static void progressionInterface() {
		int interID = 112000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 115;
		int y = 270;
		tab.totalChildren(8);

		addTransparentSpriteWSpriteLoader1(id, 1477, 200);
		tab.child(c++, id++, x, y);

		addText(id, "Npc Name", tda, 2, 0xD2B8E5, true, true);
		tab.child(c++, id++, 103 + x, 13 + y);

		addText(id, "    Rewards", tda, 2, 0xE7DEEE, true, true);
		tab.child(c++, id++, 206 + x, 9 + y);

		new ProgressBar(id, 90, 23, new int[]{0xC696E9}, true, false, "", new int[]{0x705485});
		tab.child(c++, id++, 59 + x, 29 + y);

		addText(id, "50% (10/20)", tda, 0, 0xFFFFFF, true, true);
		tab.child(c++, id++, 100 + x, 35 + y);

		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 159 + x, 24 + y);
		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 196 + x, 24 + y);
		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 233 + x, 24 + y);

	}

	public static void casketInterface() {
		int interID = 108000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 10;
		int y = 14;
		tab.totalChildren(9);

		addSpriteLoader(id, 1459);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 465 + x, 3 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 465 + x, 3 + y);
		id++;

		addText(id, "Athens Rewards", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 247 + x, 4 + y);

		addText(id, "Possible Loot", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 247 + x, 111 + y);

		hoverButton(id, 1457, 1458, "Open", 1, 0xFF981F, "Open");
		tab.child(c++, id++, 348 + x, 34 + y);
		hoverButton(id, 1457, 1458, "Instant Open", 1, 0xFF981F, "Instant Open");
		tab.child(c++, id++, 348 + x, 66 + y);

		tab.child(c++, 108100, 13 + x, 163 - 34 + y);
		tab.child(c++, 108500, 28 + x, 40 + y);

		interID = 108100;
		RSInterface list = addInterface(interID);
		list.width = 462 - 16;
		list.height = 164;
		list.scrollMax = 257;
		id = interID + 1;
		c = 0;
		x = 13;
		y = 9;
		list.totalChildren(320);

		for (int i = 0; i < 20; i++) {
			for (int r = 0; r < 8; r++) {
				dropGroup(id, 1, 1, 1, 1);
				list.child(c++, id++, 2 + x, y);
				x += 55;
			}
			x = 13;
			y += 56;
		}

		for (int xy = 0; xy < 20; xy++) {
			x = 30;
			int ys = 43 + (56 * xy);
			for (int i = 0; i < 8; i++) {
				addText(id, "1/10", 0xffff00, true, false, 52, fonts, 0);
				list.child(c++, id++, x, ys);
				x += 55;
			}
		}

		interID = 108500;
		RSInterface boxes = addInterface(interID);
		boxes.width = 309;
		boxes.height = 48;
		boxes.scrollMax = 48;
		id = interID + 1;
		c = 0;
		x = 0;
		y = 0;
		boxes.totalChildren(2);

		addToItemGroup(id, 1750, 1, 12, 10, false, null, null, null);
		boxes.child(c++, id++, 7 + x, 7 + y);

		addSpriteLoader(id, 1460);
		boxes.child(c++, id++, 130 + x, 2 + y);

	}

	private void HolidayTaskManager() {
		RSInterface main = addInterface(63344);
		addSpriteLoader(63345, 3324);

		addText(63346, "Christmas Slayer", tda, 3, 0xD02828, true, true);
		addHoverButtonWSpriteLoader(63347, 3337, 15, 15, "Close", -1, 63348, 1);
		addHoveredImageWSpriteLoader(63348, 3338, 15, 15, 63349);

		addHoverButtonWSpriteLoader(63350, 3326, 120, 45, "Select", -1, 63351, 1);
		addHoveredImageWSpriteLoader(63351, 3325, 120, 45, 63352);
		addHoverButtonWSpriteLoader(63353, 3328, 120, 45, "Select", -1, 63354, 1);
		addHoveredImageWSpriteLoader(63354, 3327, 120, 45, 63355);
		addHoverButtonWSpriteLoader(63356, 3330, 120, 45, "Select", -1, 63357, 1);
		addHoveredImageWSpriteLoader(63357, 3329, 120, 45, 63358);

		addHoverButtonWSpriteLoader(63362, 3334, 120, 45, "Shop", -1, 63363, 1);
		addHoveredImageWSpriteLoader(63363, 3333, 120, 45, 63364);

		addHoverButtonWSpriteLoader(63365, 3336, 120, 45, "Skip", -1, 63366, 1);
		addHoveredImageWSpriteLoader(63366, 3335, 120, 45, 63367);

		addText(63368, "Easy", tda, 3, 0x55923C, true, true);
		addText(63369, "Medium", tda, 3, 0xE2E3E2, true, true);
		addText(63370, "Elite", tda, 3, 0xD02828, true, true);

		addText(63372, "", tda, 0, 0x37fc50, true, true);
		addText(63374, "", tda, 0, 0x37fc50, true, true);
		addText(63376, "", tda, 0, 0x37fc50, true, true);

		addText(63380, "You've selected an Easy Task", tda, 0, 0xE2E3E2, true, true);
		addText(63381, "Defeat at least 250", tda, 0, 0xE2E3E2, true, true);
		addText(63382, "Dark Beasts", tda, 0, 0xE2E3E2, true, true);

		main.totalChildren(23);
		main.child(0, 63345, 135, 15);
		main.child(1, 63346, 264, 29);
		//CLOSE BUTTON
		main.child(2, 63347, 357, 31);
		main.child(3, 63348, 357, 31);
		//TIER BUTTONS
		main.child(4, 63350, 154, 72);
		main.child(5, 63351, 154, 72);

		main.child(6, 63353, 154, 112);
		main.child(7, 63354, 154, 112);
		main.child(8, 63356, 154, 152);
		main.child(9, 63357, 154, 152);
		//SHOP BUTTON
		main.child(10, 63362, 327, 186);
		main.child(11, 63363, 327, 186);
		//SKIP BUTTON
		main.child(12, 63365, 327, 230);
		main.child(13, 63366, 327, 230);
		//TIER TEXT
		main.child(14, 63368, 212, 78);
		main.child(15, 63369, 212, 118);
		main.child(16, 63370, 212, 157);
		//POINTS
		main.child(17, 63372, 320, 74);
		main.child(18, 63374, 320, 109);
		main.child(19, 63376, 320, 144);
		//TEXT LINES
		main.child(20, 63380, 236, 221);
		main.child(21, 63381, 236, 236);
		main.child(22, 63382, 236, 253);
	}

	static void groupIronman() {
		int interID = 104000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 110;
		int y = 60;
		tab.totalChildren(26);

		addSpriteLoader(id, 1510);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 268 + x, 3 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 268 + x, 3 + y);
		id++;

		addText(id, "Group Ironman", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 146 + x, 4 + y);

		addText(id, "Group Name: T", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 146 + x, 131 + y);

		addText(id, "Name", tda, 0, 0xFF981F, false, true);
		tab.child(c++, id++, 20 + x, 23 + y);
		addText(id, "Points", tda, 0, 0xFF981F, true, true);
		tab.child(c++, id++, 160 + x, 23 + y);
		addText(id, "Status", tda, 0, 0xFF981F, true, true);
		tab.child(c++, id++, 250 + x, 23 + y);


		addText(id, "", tda, 1, 0xFF981F, true, true);
		tab.child(c++, id++, 146 + x, 132 + y);

		hoverButton(id, 1457, 1458, "Change Name", 2, 0xffef0a, "Change Name");
		tab.child(c++, id++, 22 + x, 158 + y);
		hoverButton(id, 1457, 1458, "Invite Player", 2, 0xffef0a, "Invite Player");
		tab.child(c++, id++, 151 + x, 158 + y);


		for (int i = 0; i < 5; i++) {
			addText(id, "Some dude", tda, 1, 0xFF981F, false, true);
			tab.child(c++, id++, 20 + x, 37 + y);
			addText(id, "2499", tda, 1, 0xFF981F, true, true);
			tab.child(c++, id++, 160 + x, 37 + y);
			addText(id, "Online", tda, 1, 0xFF981F, true, true);
			tab.child(c++, id++, 250 + x, 37 + y);
			y += 18;
		}

	}

	private static void groupbankInterface() {
		int interID = 106000;
		RSInterface rsinterface = addTabInterface(interID);
		setChildren(15, rsinterface);

		int c = 0;
		int x = 35;
		int y = 12;
		int id = interID + 1;

		addSpriteLoader(id, 1511);
		setBounds(id++, 13 + x, 13 + y, c++, rsinterface);

		RSInterface thing = copy(id, 5385);
		interfaceCache[id].height = 208;
		interfaceCache[id].width = 380;
		interfaceCache[id].scrollMax = 1000;
		setBounds(id++, 24 + x, 35 + y, c++, rsinterface);

		setChildren(1, thing);

		copy(id, 5382);
		interfaceCache[id].width = 10;
		interfaceCache[id].invSpritePadX = 6;
		interfaceCache[id].height = 200;
		interfaceCache[id].inv = new int[10 * 200];
		interfaceCache[id].invStackSizes = new int[10 * 200];
		interfaceCache[id].height = 200;
		interfaceCache[id].actions = new String[]{"Withdraw-1", "Withdraw-5", "Withdraw-10", "Withdraw-All", "Withdraw-All but one", "Withdraw-X"};
		setBounds(id++, 0, 3, 0, thing);


		addText(id, "Group Ironman Bank", fonts, 2, 0xFF981F, true, true);
		setBounds(id++, 220 + x, 16 + y, c++, rsinterface);


		addHoverButtonWSpriteLoader(id, 1016, 17, 17, "Close Window", 0, id + 1, 1);
		setBounds(id++, 404 + x, 16 + y, c++, rsinterface);
		addHoveredImageWSpriteLoader(id, 1017, 17, 17, id + 1);
		setBounds(id++, 404 + x, 16 + y, c++, rsinterface);

		id++;

		setBounds(22008, 240 + x, 247 + y, c++, rsinterface);
		setBounds(22009, 240 + x, 247 + y, c++, rsinterface);


		setBounds(78395, 295 + x, 246 + y, c++, rsinterface);
		setBounds(78396, 295 + x, 246 + y, c++, rsinterface);

		id++;
		addHoverButtonWSpriteLoader(id, 724, 35, 25, "Deposit carried items", -1, id + 1, 1);
		setBounds(id++, 340 + x, 247 + y, c++, rsinterface);
		addHoveredImageWSpriteLoader(id, 725, 35, 25, id + 1);
		setBounds(id++, 340 + x, 247 + y, c++, rsinterface);


		id++;
		addHoverButtonWSpriteLoader(id, 726, 35, 25, "Deposit worn items", -1, id + 1, 1);
		setBounds(id++, 380 + x, 247 + y, c++, rsinterface);
		addHoveredImageWSpriteLoader(id, 727, 35, 25, id + 1);
		setBounds(id++, 380 + x, 247 + y, c++, rsinterface);


		id++;

		addText(id, "0", fonts, 0, 0xb4965a, true, false);
		setBounds(id++, 42 + x, 249 + y, c++, rsinterface);
		addText(id, "100", fonts, 0, 0xb4965a, true, false);
		setBounds(id++, 42 + x, 263 + y, c++, rsinterface);


	}

	public static void addModel(int interfaceId, int width, int height, int itemId, int zoom, int contentType) {
		RSInterface rsi = addInterface(interfaceId);
		rsi.type = 6;
		rsi.mediaType = RSInterface.DRAW_ITEM_MODEL;
		ItemDef def = ItemDef.get(itemId);
		rsi.mediaID = itemId;
		rsi.modelZoom = zoom;
		rsi.modelRotationX = def.rotation_x;
		rsi.modelRotationY = def.rotation_y;
		rsi.width = width;
		rsi.height = height;
		rsi.contentType = contentType;
	}

	public static void addTransparentLayer(int id, int layerColor, int layerTransparency) {
		RSInterface transparentLayer = addInterface(id);
		transparentLayer.layerColor = layerColor;
		transparentLayer.layerTransparency = layerTransparency;
		transparentLayer.type = 130;
		transparentLayer.setVisible(true);
	}

	public static void addWheel(int id, int width, int height, int strokeWidth, int color, int alpha, int closure, boolean fill, int segments, int spriteId) {
		RSInterface widget = addInterface(id);
		widget.wheel = new WheelOfFortune(width, height, strokeWidth, color, alpha, closure, fill, segments, Client.spritesMap.get(spriteId));
		widget.type = 150;
	}

	private static void OsDropViewer() {
		RSInterface tab = addInterface(33000);
		//String dir = "DropViewer/SPRITE";
		addSprite(33001, 1217); //changeid
		addHoverButton(33002, 55, 16, 16,"Close", 0, 33003, 1); //changeid
		addHoveredButton(33003, 90, 16, 16, 33004); //changeid
		addText(33005, "Loot Viewer", tda, 2, 0xAF70C3, true, true);
		addHoverButton(132509, 1410, 159, 29, "Search Npc", -1, 132510, 1);
		addHoveredButton(253, 1411, 159, 29, 35010);
		addText(132511, "Results", tda, 1, 0xAF70C3, true, true);
		addText(132501, "Item name", tda, 1, 0xAF70C3, false, true);
		addText(132502, "Amount", tda, 1, 0xAF70C3, false, true);
		addText(132503, "Chance", tda, 1, 0xAF70C3, false, true);

		addToggleButton1(79104, 1332, 1701, 12, 15, "Toggle Rates");
		addText(79105, "My Rate", 0xAF70C3, true, true, 52, 1);





		int x = 7, y = 7;
		tab.totalChildren(11);
		tab.child(0, 33001, x, y);
		tab.child(1, 33002, 474+x, 9+y);
		tab.child(2, 33003, 474+x, 9+y);
		tab.child(3, 132509, 8+x, 37+y);
		tab.child(4, 253, 8+x, 37+y);
		tab.child(5, 33005, 250+x, 10+y);
		tab.child(6, 33006, 91+x, 43+y);
		tab.child(7, 34000, 174+x, 57+y);
		tab.child(8, 33007, 6+x, 88+y);
		tab.child(9, 79104, 55+x, 9+y);
		tab.child(10, 79105, 30+x, 9+y);


		RSInterface results = addInterface(33007);
		results.width = 144;
		results.height = 222;
		results.scrollMax = 350;

		results.totalChildren(90);
		int index = 0;
		for (int j = 0; j < 30; j++) {
			addHoverButton(33008 + index, 1529, 136, 23,"Search", 0, 33009 + index, 1); //changeid
			addHoveredButton(33009 + index, 1530, 136, 23, 33210 + index); //changeid
			results.child(j, 33008 + index, 6, 1 + (j*24));
			results.child(j + 30, 33009 + index, 6, 1 + (j*24));
			addText(33108 + j, "", tda, 1, 0xAF70C3, true, true);
			results.child(j + 60, 33108 + j, 75, 5 + (j*24));
			index+=3;
		}

		RSInterface main = addInterface(34000);
		main.totalChildren(720);
		main.width = 298;
		main.height = 253;
		main.scrollMax = 2560;
		addSprite(34001, 1210); //changeid
		addSprite(34002, 1211); //changeid
		for(int i = 0; i < 40; i++) {
			main.child(i, 34001, 0, (i * 74));
			main.child(i + 40, 34002, 0, 37 + (i * 74));
		}
		addText(34003, "", tda, 0, 0xAF70C3, true, true);
		addText(34004, "", tda, 0, 0xAF70C3, true, true);
		addText(34005, "", tda, 0, 0xAF70C3, true, true);
		for (int i = 0; i < 80; i++) {
			itemGroup(34010 + i, 1, 1, 1, 1, false, false);
			interfaceCache[34010 + i].inv[0] = 14485;
			interfaceCache[34010 + i].invStackSizes[0] = 1;
			addText(34100 + i, "Name", tda, 1,0xAF70C3, true, true);
			addText(34200 + i, "Amount", tda, 1, 0xAF70C3, true, true);
			addText(34300 + i, "Rarity", tda, 1, 0xAF70C3, true, true);
			addText(34400 + i, "Value", tda, 0, 0xAF70C3, true, true);
			int yy = (i * 37);
			main.child(80+i, 34010+i, 3, 3+yy);
			main.child(160+i, 34100+i, 107, 10+yy);
			main.child(240+i, 34003, 175, 2+yy);
			main.child(320+i, 34004, 234, 2+yy);
			main.child(400+i, 34005, 293, 2+yy);
			main.child(480+i, 34200+i, 192, 10+yy);
			main.child(560+i, 34300+i, 257, 10+yy);
			main.child(640+i, 34400+i, 345, 14+yy);
		}
		addText(33006, "Search by Npc", tda, 2, 0xAF70C3, true, true);
	}
	public static void starterTasksInterface() {
		int interID = 148000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 47;
		int y = 25;
		tab.totalChildren(5);

		addSpriteLoader(id, 3476);
		tab.child(c++, id++, 0 + x, 0 + y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 387 + x, 3 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 387 + x, 3 + y);
		id++;

		addText(id, "Starter Tasks", tda, 2, 0xAF70C3, true, true);
		tab.child(c++, id++, 207 + x, 4 + y);

		tab.child(c++, 148050, 12 + x, 27 + y);


		interID = 148050;
		RSInterface scroll = addInterface(interID);
		scroll.width = 390 - 16;
		scroll.height = 244;
		scroll.scrollMax = 500;
		scroll.totalChildren(180);
		id = interID + 1;
		c = 0;
		x = 4;
		y = 4;

		for (int z = 0; z < 20; z++) {

			addSpriteLoader(id, 3477);
			scroll.child(c++, id++, 0 + x, 0 + y);

			hoverButton(id, 3478, 3479, "Claim", 2, 0xAF70C3, "");
			scroll.child(c++, id++, 289 + x, 14 + y);

			addText(id, "Claimed", tda, 2, 0x38761d, true, true);
			scroll.child(c++, id++, 322 + x, 20 + y);

			addText(id, "Kill Stuff", tda, 0, 0xAF70C3, true, true);
			scroll.child(c++, id++, 86 + x, 10 + y);

			new ProgressBar(id, 159, 20, new int[]{0xB472C4}, true, true, "asd", new int[]{0x6D4677});
			scroll.child(c++, id++, 6 + x, 25 + y);

			addText(id, "(0/100)", tda, 0, 0xFFFFFF, true, true);
			scroll.child(c++, id++, 86 + x, 30 + y);

			for (int r = 0; r < 3; r++) {
				dropGroup(id, 1, 1, 1, 1); //
				scroll.child(c++, id++, 172 + x, 12 + y);
				x += 36;
			}

			y += 60;
			x = 4;
		}


	}

	private static void equipmentTab() {
		addButton(15281, 4, -1, 5560, 5561, 28 , 28, "View Cosmetics", 1882, 1);

        int[] modify = {
            1650, 1652, 1657, 1658,
            1659, 1660, 1661, 1662,
            1663, 1664, 1665, 1666,
            1667, 1648
        };

		int[] remove = {
				1672, 1669, 1670, 1671,
				1673, 1674, 1675, 1676,
				1677, 1678, 1679, 1680,
				1681, 1682, 1683, 1684,
				1685, 1686, 1687, 1668};
		for (int i : remove) {
			removeSomething(i);
		}
        for (int i : modify) {
            addSprite(i, 5744);
        }
		RSInterface newTab = addTabInterface(15000);
		addHoverButtonWSpriteLoader(15001, 618, 41, 40, "Gear Stats", -1, 15002, 1);
		addHoveredImageWSpriteLoader(15002, 3385, 41, 40, 15003);
		addHoverButtonWSpriteLoader(15004, 3386, 41, 40, "Best in Slot List", -1, 15005, 1);
		addHoveredImageWSpriteLoader(15005, 3387, 41, 40, 15006);
		newTab.totalChildren(6);
		setBounds(15001, 25, 205, 0, newTab);
		setBounds(15002, 25, 205, 1, newTab);
		setBounds(15004, 125, 205, 2, newTab);
		setBounds(15005, 125, 205, 3, newTab);
		setBounds(1644, 0, 0, 4, newTab);
		setBounds(15281, 153, 10, 5, newTab);
	}

	private static void skillTabInterface() {
			RSInterface skill = addTabInterface(3917);
			addSpriteLoader(28100, 623);
			skill.totalChildren(1);
			skill.child(0, 28100, 72, 237);
			int[] logoutID = { 2450, 2451, 2452 };
			int[] logoutID2 = { 2458 };
			for (int i : logoutID) {
				RSInterface Logout = interfaceCache[i];
				Logout.disabledColor = 0xFF981F;
				Logout.contentType = 0;
			}
			for (int i : logoutID2) {
				RSInterface Logout = interfaceCache[i];
				Logout.contentType = 0;
			}
			int[] buttons = { 8654, 8655, 8656, 8657, 8658, 8659, 8660, 8861, 8662, 8663, 8664, 8665, 8666, 8667, 8668,
					8669, 8670, 8671, 8672, 12162, 13928, 28177, 28178, 28179, 28180 };
			int[] hovers = { 4040, 4076, 4112, 4046, 4082, 4118, 4052, 4088, 4124, 4058, 4094, 4130, 4064, 4100, 4136, 4070,
					4106, 4142, 4160, 2832, 13917, 28173, 28174, 28175, 28176 };
			int[][] text = { { 4004, 4005 }, { 4016, 4017 }, { 4028, 4029 }, { 4006, 4007 }, { 4018, 4019 }, { 4030, 4031 },
					{ 4008, 4009 }, { 4020, 4021 }, { 4032, 4033 }, { 4010, 4011 }, { 4022, 4023 }, { 4034, 4035 },
					{ 4012, 4013 }, { 4024, 4025 }, { 4036, 4037 }, { 4014, 4015 }, { 4026, 4027 }, { 4038, 4039 },
					{ 4152, 4153 }, { 12166, 12167 }, { 13926, 13927 }, { 28165, 28169 }, { 28166, 28170 },
					{ 28167, 28171 }, { 28168, 28172 } };

			int[] icons = { 3965, 3966, 3967, 3968, 3969, 3970, 3971, 3972, 3973, 3974, 3975, 3976, 3977, 3978, 3979, 3980,
					3981, 3982, 4151, 12165, 13925, 28181, 28182, 28183, 28184 };

			int[][] buttonCoords = { { 3, 5 }, { 65, 5 }, { 127, 5 }, { 3, 33 }, { 65, 33 }, { 127, 33 }, { 3, 61 },
					{ 65, 61 }, { 127, 61 }, { 3, 89 }, { 65, 89 }, { 127, 89 }, { 3, 117 }, { 65, 117 }, { 127, 117 },
					{ 3, 145 }, { 65, 145 }, { 127, 145 }, { 3, 173 }, { 65, 173 }, { 127, 173 }, { 3, 201 }, { 65, 201 },
					{ 127, 201 }, { 3, 229 } };
			int[][] iconCoords = { { 5, 7 }, { 68, 8 }, { 130, 7 }, { 8, 35 }, { 67, 34 }, { 130, 37 }, { 8, 65 },
					{ 66, 64 }, { 130, 62 }, { 6, 92 }, { 67, 97 }, { 132, 91 }, { 5, 119 }, { 69, 121 }, { 129, 119 },
					{ 5, 148 }, { 68, 147 }, { 131, 147 }, { 5, 174 }, { 68, 174 }, { 129, 175 }, { 5, 203 }, { 68, 202 },
					{ 130, 203 }, { 5, 231 } };
			int[][] textCoords = { { 29, 7, 44, 19 }, { 91, 7, 106, 19 }, { 153, 7, 168, 19 }, { 29, 35, 44, 47 },
					{ 91, 35, 106, 47 }, { 153, 35, 168, 47 }, { 29, 63, 44, 75 }, { 91, 63, 106, 75 },
					{ 153, 63, 168, 75 }, { 29, 91, 44, 103 }, { 91, 91, 106, 103 }, { 153, 91, 168, 103 },
					{ 29, 119, 44, 131 }, { 91, 119, 106, 131 }, { 153, 119, 168, 131 }, { 29, 147, 44, 159 },
					{ 91, 147, 106, 159 }, { 153, 147, 168, 159 }, { 29, 175, 44, 187 }, { 91, 175, 106, 187 },
					{ 153, 175, 168, 187 }, { 29, 203, 44, 215 }, { 91, 203, 106, 215 }, { 153, 203, 168, 215 },
					{ 29, 231, 44, 243 } };
			int[][] newText = { { 28165, 28166, 28167, 28168 }, { 28169, 28170, 28171, 28172 } };
			int[] spriteIds = { 625, 636, 639, 645, 624, 511, 629, 635, 633, 641, 647, 627, 640, 628, 632, 638, 634, 648, 642, 643, 631, 377, 637, 646, 630 };

			int frame = 0;

			for (int i = 0; i < hovers.length; i++) {
				addSkillButton(buttons[i], Skills.SKILL_NAMES[i]);
				createSkillHover(hovers[i], 205 + i);

				addHoverButtonWSpriteLoader(79924 + i, 622, 60, 27, "Set Level Goal", 1321, -1, 1);
				addHoverButtonWSpriteLoader(79949 + i, 622, 60, 27, "Set Exp Goal", 1322, -1, 1);
				addHoverButtonWSpriteLoader(79974 + i, 622, 60, 27, "Clear Goal", 1323, -1, 1);
				addHoverButtonWSpriteLoader(80000 + i, 622, 60, 27, "Prestige", 5000 + i, -1, 1);
				addSpriteLoader(icons[i], spriteIds[i]);
			}

			for (int i = 0; i < 4; i++) {
				addSkillText(newText[0][i], false, i + 21);
				addSkillText(newText[1][i], true, i + 21);
			}
			skill.totalChildren(icons.length + (text.length) + hovers.length + buttons.length * 5 + 1);

			RSInterface totalLevel = addInterface(3984);
			addSpriteLoader(31196, 43);
			createHover(31192, 231, 120);
			addText(31199, "Total Level:", 0xAF70C3, false, true, 52, tda, 0);
			addText(31200, "2475", 0xAF70C3, false, true, 52, tda, 0);

			totalLevel.totalChildren(4);
			totalLevel.child(0, 31196, 65, 229);
			totalLevel.child(1, 31199, 106, 231);
			totalLevel.child(2, 31200, 117, 243);
			totalLevel.child(3, 31192, 38, 230);
			skill.child(frame, 3984, 0, 0);
			frame++;
			for (int i = 0; i < buttons.length; i++) {
				skill.child(frame++, 80000 + i, buttonCoords[i][0], buttonCoords[i][1]);
				skill.child(frame++, 79974 + i, buttonCoords[i][0], buttonCoords[i][1]);
				skill.child(frame++, 79949 + i, buttonCoords[i][0], buttonCoords[i][1]);
				skill.child(frame++, 79924 + i, buttonCoords[i][0], buttonCoords[i][1]);
				skill.child(frame, buttons[i], buttonCoords[i][0], buttonCoords[i][1]);
				frame++;
			}
			for (int i = 0; i < icons.length; i++) {
				skill.child(frame, icons[i], iconCoords[i][0], iconCoords[i][1]);
				frame++;
			}
			for (int i = 0; i < text.length; i++) {
				skill.child(frame, text[i][0], textCoords[i][0] + 8, textCoords[i][1] + 7);
				frame++;
			}
			for (int i = 0; i < hovers.length; i++) {
				skill.child(frame, hovers[i], buttonCoords[i][0], buttonCoords[i][1]);
				frame++;
			}
	}

	private void equipmentScreenInterface() {
		RSInterface tab = addTabInterface(21172);
		addSpriteLoader(21173, 3547);
		addCloseButton(15210);
		addText(15111, "Equipment Interface", tda, 2, 0xff9633, true, true);
		addText(15112, "Strength", tda, 1, 0xAF70C3, false, true);
		addText(15113, "Ranged", tda, 1, 0xAF70C3, false, true);
		addText(15114, "Magic", tda, 1, 0xAF70C3, false, true);
		addText(15115, "Change Preset", tda, 0, 0xAF70C3, true, true);
		addText(15116, "Preset", tda, 0, 0xAF70C3, true, true);
		addText(15117, "Preset Editor", tda, 0, 0xAF70C3, true, true);
		addText(15118, "Presets Owned: 1", tda, 0, 0xAF70C3, true, true);
		addText(15119, "Melee Maxhit: @whi@0", tda, 0, 0xAF70C3, false, true);
		addText(18897, "Range Maxhit: @whi@0", tda, 0, 0xAF70C3, false, true);
		addText(18898, "Magic Maxhit: @whi@0", tda, 0, 0xAF70C3, false, true);
		addText(18899, "Drop Rate:: 0", tda, 0, 0xAF70C3, false, true);
		addText(18900, "Crit Chance: 0", tda, 0, 0xAF70C3, false, true);

		for (int i = 1675; i <= 1684; i++) {
			addText(i, "", tda, 1, 0xAF70C3, false, true);
		}

		addChar(15125, 700);

		int Child = 0;
		tab.totalChildren(45);
		tab.child(Child++, 21173, 15, 1);
		tab.child(Child++, 1644, 9, 59);
		tab.child(Child++, 15210, 478, 6);
		tab.child(Child++, 15111, 256, 6);
		int Y = 257;
		for (int i = 1675; i <= 1679; i++) {
			tab.child(Child++, i, 34, Y);
			Y += 13;
		}
		Y = 257;

		for (int i = 1680; i < 1685; i++) {
			tab.child(Child++, i, 129, Y);
			Y += 13;
		}

		tab.child(Child++, 15112, 193, 255);
		tab.child(Child++, 15113, 193, 270);
		tab.child(Child++, 15114, 193, 285);
		tab.child(Child++, 15119, 312, 256);
		tab.child(Child++, 15115, 418, 45);
		tab.child(Child++, 15116, 418, 82);
		tab.child(Child++, 15118, 418, 62);
		tab.child(Child++, 15117, 418, 143);

		int interfaceId = 39000;

		interfaceId = 39020;

		addHoverButtonWSpriteLoader(interfaceId++, 3552, 91, 20, "Buy Preset Slot", -1, interfaceId, 1);
		addHoveredImageWSpriteLoader(interfaceId++, 3553, 91, 20, interfaceId++);

		tab.child(Child++, interfaceId - 3, 373, 101);
		tab.child(Child++, interfaceId - 2, 373, 101);

		addHoverButtonWSpriteLoader(interfaceId++, 3554, 81, 20, "Rename Preset", -1, interfaceId, 1);
		addHoveredImageWSpriteLoader(interfaceId++, 3555, 81, 20, interfaceId++);
		addText(interfaceId++, "Rename Preset", tda, 0, 0xff9633, true, true);

		tab.child(Child++, interfaceId - 4, 377, 158);
		tab.child(Child++, interfaceId - 3, 377, 158);
		tab.child(Child++, interfaceId - 1, 417, 163);

		addHoverButtonWSpriteLoader(interfaceId++, 3554, 81, 20, "Clear Preset", -1, interfaceId, 1);
		addHoveredImageWSpriteLoader(interfaceId++, 3555, 81, 20, interfaceId++);
		addText(interfaceId++, "Clear Preset", tda, 0, 0xff9633, true, true);

		tab.child(Child++, interfaceId - 4, 377, 180);
		tab.child(Child++, interfaceId - 3, 377, 180);
		tab.child(Child++, interfaceId - 1, 417, 185);

		addHoverButtonWSpriteLoader(interfaceId++, 3554, 81, 20, "Clear All Presets", -1, interfaceId, 1);
		addHoveredImageWSpriteLoader(interfaceId++, 3555, 81, 20, interfaceId++);
		addText(interfaceId++, "Clear All", tda, 0, 0xff9633, true, true);

		tab.child(Child++, interfaceId - 4, 377, 202);
		tab.child(Child++, interfaceId - 3, 377, 202);
		tab.child(Child++, interfaceId - 1, 417, 207);

		addHoverButtonWSpriteLoader(interfaceId++, 3548, 16, 16, "Previous Preset", -1, interfaceId, 1);
		addHoveredImageWSpriteLoader(interfaceId++, 3549, 16, 16, interfaceId++);

		tab.child(Child++, interfaceId - 3, 371, 79);
		tab.child(Child++, interfaceId - 2, 371, 79);

		addHoverButtonWSpriteLoader(interfaceId++, 3550, 16, 16, "Next Preset", -1, interfaceId, 1);
		addHoveredImageWSpriteLoader(interfaceId++, 3551, 16, 16, interfaceId++);

		tab.child(Child++, interfaceId - 3, 450, 79);
		tab.child(Child++, interfaceId - 2, 450, 79);

		addText(interfaceId++, "Buy Preset Slot", tda, 0, 0xff9633, true, true);

		tab.child(Child++, interfaceId - 1, 419, 106);
		tab.child(Child++, 1686, 232, 257);
		tab.child(Child++, 1687, 232, 270);
		tab.child(Child++, 18897, 312, 269);
		tab.child(Child++, 18898, 312, 282);
		tab.child(Child++, 18899, 312, 295);
		tab.child(Child++, 18900, 312, 308);
		//tab.child(Child++, 15215, 478, 6);
		tab.child(Child++, 15125, 200, 125);
		for (int i = 1675; i <= 1684; i++) {
			RSInterface rsi = interfaceCache[i];
			addText(i, "", tda, 0, 0xff9633, false, true);
			rsi.disabledColor = 0xff9633;
			rsi.centerText = false;
		}
		for (int i = 1686; i <= 1687; i++) {
			RSInterface rsi = interfaceCache[i];
			addText(i, "", tda, 0, 0xff9633, false, true);
			rsi.disabledColor = 0xff9633;
			rsi.centerText = false;
		}
	}

	/*
	 * Quest tab [PLAYER PANEL]
	 */
	private static void playerPanel() {
		RSInterface tab = addTabInterface(639);
		RSInterface scroll = addTabInterface(15016);
		addText(39155, "Athens", tda, 0, ColorConstants.AQUA, false, true);
		addSpriteLoader(39156, 650);
		addSpriteLoader(39157, 651);
		addSpriteLoader(39158, 650);
		addButtonWSpriteLoader(23835, 1346, "World & Events tab");
		addButtonWSpriteLoader(23836, 1347, "Account info tab");
		addButtonWSpriteLoader(23837, 1348, "Points & Statistics tab");
		addButtonWSpriteLoader(23838, 1349, "Slayer tab");
		scroll.totalChildren(55);
		scroll.width = 174;
		scroll.height = 224;
		scroll.scrollMax = 835;
		tab.totalChildren(9);

		tab.child(0, 39155, 50, 251);
		tab.child(1, 39156, 0, 22);
		tab.child(2, 39157, 0, 25);
		tab.child(3, 39158, 0, 249);
		tab.child(4, 15016, 0, 25);
		tab.child(5, 23835, 5 + 3, 2); // blue.
		tab.child(6, 23836, 50 + 3, 2); // green.
		tab.child(7, 23837, 95 + 3, 2); // red.
		tab.child(8, 23838, 140 + 3, 2); // yello.

		int k = 0;
		int y = 0;
		for (int i = 39159; i < 39214; i++) {
			scroll.child(k, i, 6, y);
			y += 16;
			k++;
			/*if(i == 39162) {
				addClickableText(i, "", "View", tda, 1, 0xff0000, 167, 13);
			} else if(i == 39165) {
				addClickableText(i, "", "Check", tda, 1, 0xff0000, 167, 13);
			} else if(i == 39173 || i == 39174) {
				addClickableText(i, "", "Toggle", tda, 1, 0xff0000, 167, 13);
			} else if(i == 39187 || i ==39188 || i >= 39196 && i <= 39202) {
				addClickableText(i, "", "Open", tda, 1, 0xff0000, 167, 13);
			} else {
				addText(i, "", tda, 1, 0xff0000, false, true);
			}*/
			if (i == 39160) {
				addText(i, "", tda, 2, 0xff0000, false, true);
			} else {
				addText(i, "", tda, 0, 0xff0000, false, true);
			}
		}
	}

	private static void killsTracker() {
		RSInterface tab = addTabInterface(55000);
		RSInterface scroll = addTabInterface(55010);
		addText(55001, "Kills Tracker", tda, 2, 16750623, false, true);
		addSpriteLoader(55002, 650);
		addSpriteLoader(55003, 651);
		addSpriteLoader(55004, 650);
		addButtonWSpriteLoader(55005, 1024, "Go Back", 26, 26);

		scroll.totalChildren(44);
		scroll.width = 174;
		scroll.height = 224;
		scroll.scrollMax = 710;
		tab.totalChildren(6);

		tab.child(0, 55001, 5, 3);
		tab.child(1, 55002, 0, 22);
		tab.child(2, 55003, 0, 25);
		tab.child(3, 55004, 0, 249);
		tab.child(4, 55005, 164, 0);
		tab.child(5, 55010, 0, 25);

		int k = 0;
		int y = 0;
		for (int i = 55020; i < 55064; i++) {
			scroll.child(k, i, 6, y);
			y += 16;
			k++;
			addText(i, "", tda, 1, 0xff0000, false, true);
		}
	}

	private static void editClan() {
		RSInterface tab = addTabInterface(40172);
		addSpriteLoader(47251, 658);
		addHoverSpriteLoaderButton(47252, 659, 150, 35, "Set name", 22222, 47253, 1);
		addHoveredSpriteLoaderButton(47253, 150, 35, 47254, 659);
		addHoverSpriteLoaderButton(47255, 659, 150, 35, "Anyone", -1, 47256, 1);
		addHoveredSpriteLoaderButton(47256, 150, 35, 47257, 659);

		addHoverButton(48000, "b", 1, 150, 35, "Only me", -1, 47999, 1);
		addHoverButton(48001, "b", 1, 150, 35, "General+", -1, 47999, 1);
		addHoverButton(48002, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
		addHoverButton(48003, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
		addHoverButton(48004, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
		addHoverButton(48005, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
		addHoverButton(48006, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);
		addHoverButton(48007, "b", 1, 150, 35, "Any friends", -1, 47999, 1);

		addHoverSpriteLoaderButton(47258, 660, 150, 35, "Anyone", -1, 47259, 1);
		addHoveredSpriteLoaderButton(47259, 659, 35, 17260, 727);

		addHoverButton(48010, "b", 1, 150, 35, "Only me", -1, 47999, 1);
		addHoverButton(48011, "b", 1, 150, 35, "General+", -1, 47999, 1);
		addHoverButton(48012, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
		addHoverButton(48013, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
		addHoverButton(48014, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
		addHoverButton(48015, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
		addHoverButton(48016, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);
		addHoverButton(48017, "b", 1, 150, 35, "Any friends", -1, 47999, 1);

		addHoverSpriteLoaderButton(47261, 660, 150, 35, "Only me", -1, 47262, 1);
		addHoveredSpriteLoaderButton(47262, 150, 35, 47263, 659);

		// addHoverButton(48020, "b", 1, 150, 35, "Only me", -1, 47999, 1);
		addHoverButton(48021, "b", 1, 150, 35, "General+", -1, 47999, 1);
		addHoverButton(48022, "b", 1, 150, 35, "Captain+", -1, 47999, 1);
		addHoverButton(48023, "b", 1, 150, 35, "Lieutenant+", -1, 47999, 1);
		addHoverButton(48024, "b", 1, 150, 35, "Sergeant+", -1, 47999, 1);
		addHoverButton(48025, "b", 1, 150, 35, "Corporal+", -1, 47999, 1);
		addHoverButton(48026, "b", 1, 150, 35, "Recruit+", -1, 47999, 1);

		addHoverSpriteLoaderButton(47267, 661, 16, 16, "Close", -1, 47268, 1);
		addHoveredSpriteLoaderButton(47268, 16, 16, 47269, 662);

		addText(47800, "Clan name:", tda, 0, 0xff981f, false, true);
		addText(47801, "Who can enter chat?", tda, 0, 0xff981f, false, true);
		addText(47812, "Who can talk in chat?", tda, 0, 0xff981f, false, true);
		addText(47813, "Who can kick from chat?", tda, 0, 0xff981f, false, true);
		addText(47814, "Name", tda, 0, 0xffffff, true, true);
		addText(47815, "Anyone", tda, 0, 0xffffff, true, true);
		addText(47816, "Anyone", tda, 0, 0xffffff, true, true);
		addText(47817, "Only me", tda, 0, 0xffffff, true, true);
		tab.totalChildren(40);
		tab.child(0, 47251, 180, 15);
		tab.child(1, 47252, 190, 47 + 20);
		tab.child(2, 47253, 190, 47 + 20);
		tab.child(3, 47267, 327, 22);
		tab.child(4, 47268, 327, 22);
		tab.child(5, 48000, 190, 87 + 25);
		tab.child(6, 48001, 190, 87 + 25);
		tab.child(7, 48002, 190, 87 + 25);
		tab.child(8, 48003, 190, 87 + 25);
		tab.child(9, 48004, 190, 87 + 25);
		tab.child(10, 48005, 190, 87 + 25);
		tab.child(11, 48006, 190, 87 + 25);
		tab.child(12, 48007, 190, 87 + 25);
		tab.child(13, 47255, 190, 87 + 25);
		tab.child(14, 47256, 190, 87 + 25);
		tab.child(15, 48010, 190, 128 + 30);
		tab.child(16, 48011, 190, 128 + 30);
		tab.child(17, 48012, 190, 128 + 30);
		tab.child(18, 48013, 190, 128 + 30);
		tab.child(19, 48014, 190, 128 + 30);
		tab.child(20, 48015, 190, 128 + 30);
		tab.child(21, 48016, 190, 128 + 30);
		tab.child(22, 48017, 190, 128 + 30);
		tab.child(23, 47258, 190, 128 + 30);
		//tab.child(24, 47259, 190, 128 + 30);
		// tab.child(25, 48020, 25, 168+35);
		tab.child(24, 48021, 190, 168 + 35);
		tab.child(25, 48022, 190, 168 + 35);
		tab.child(26, 48023, 190, 168 + 35);
		tab.child(27, 48024, 190, 168 + 35);
		tab.child(28, 48025, 190, 168 + 35);
		tab.child(29, 48026, 190, 168 + 35);
		tab.child(30, 47261, 190, 168 + 35);
		tab.child(31, 47262, 190, 168 + 35);
		tab.child(32, 47800, 238, 54 + 20);
		tab.child(33, 47801, 215, 95 + 25);
		tab.child(34, 47812, 215, 136 + 30);
		tab.child(35, 47813, 215, 177 + 35);
		tab.child(36, 47814, 265, 54 + 20 + 12);
		tab.child(37, 47815, 265, 95 + 25 + 12);
		tab.child(38, 47816, 265, 136 + 30 + 12);
		tab.child(39, 47817, 265, 177 + 35 + 12);

	}



	private static void expRewardInterface() {
		RSInterface Interface = addTabInterface(38000);
		setChildren(37, Interface);
		addSpriteLoader(38001, 670);

		addHoverButtonWSpriteLoader(38002, 672, 21, 21, "Exit", 0, 38003, 1);
		addHoveredImageWSpriteLoader(38003, 673, 21, 21, 38004);

		addSpriteLoader(38005, 671);
		addText(38006, "Choose XP Type...", tda, 1, 0xE3CCCF, true, true);
		addText(38090, "What sort of XP would you like?", tda, 1, 0xE3CCCF, true, true);
		//Line 1

		addButtonWSpriteLoader(38007, 675, "Choose Attack", 46, 44);
		addButtonWSpriteLoader(38010, 675, "Choose Magic", 46, 44);
		addButtonWSpriteLoader(38013, 675, "Choose Mining", 46, 44);
		addButtonWSpriteLoader(38016, 675, "Choose Woodcutting", 46, 44);
		addButtonWSpriteLoader(38019, 675, "Choose Agility", 46, 44);
		addButtonWSpriteLoader(38022, 675, "Choose Fletching", 46, 44);
		addButtonWSpriteLoader(38025, 675, "Choose Thieving", 46, 44);
		addButtonWSpriteLoader(38028, 675, "Choose Strength", 46, 44);
		addButtonWSpriteLoader(38031, 675, "Choose Ranged", 46, 44);
		addButtonWSpriteLoader(38034, 675, "Choose Smithing", 46, 44);
		addButtonWSpriteLoader(38037, 675, "Choose Firemaking", 46, 44);
		addButtonWSpriteLoader(38040, 675, "Choose Herblore", 46, 44);
		addButtonWSpriteLoader(38043, 675, "Choose Slayer ", 46, 44);
		addButtonWSpriteLoader(38046, 675, "Choose Alchemy", 46, 44);
		addButtonWSpriteLoader(38049, 675, "Choose Defence", 46, 44);
		addButtonWSpriteLoader(38052, 675, "Choose Prayer", 46, 44);
		addButtonWSpriteLoader(38043, 675, "Choose Slayer ", 46, 44);
		addButtonWSpriteLoader(38055, 675, "Choose Fishing", 46, 44);
		addButtonWSpriteLoader(38058, 675, "Choose Crafting", 46, 44);
		addButtonWSpriteLoader(38061, 675, "Choose Farming", 46, 44);
		addButtonWSpriteLoader(38064, 675, "Choose Beast Hunter", 46, 44);
		addButtonWSpriteLoader(38067, 675, "Choose Necromancy", 46, 44);
		addButtonWSpriteLoader(38070, 675, "Choose Hitpoints", 46, 44);
		addButtonWSpriteLoader(38073, 675, "Choose Syndicate", 46, 44);
		addButtonWSpriteLoader(38076, 675, "Choose Cooking", 46, 44);
		addButtonWSpriteLoader(38079, 675, "Choose Runecrafting", 46, 44);

		//Other Stuff

		addHoverButtonWSpriteLoader(38082, 676, 127, 21, "Cancel", -1, 38083, 1);
		addHoveredImageWSpriteLoader(38083, 677, 127, 21, 38084);

		addHoverButtonWSpriteLoader(38085, 678, 127, 21, "Confirm", -1, 38086, 1);
		addHoveredImageWSpriteLoader(38086, 679, 127, 21, 38087);

		addText(38088, "Confirm", tda, 1, 0xE3CCCF, false, true);
		addText(38089, "Not right now", tda, 1, 0xE3CCCF, false, true);

		setBounds(38001, 10, 14, 0, Interface);//background
		setBounds(38002, 470, 20, 1, Interface);//Close Button
		setBounds(38003, 470, 20, 2, Interface);//Close Button
		setBounds(38005, 181, 48, 3, Interface);
		setBounds(38006, 255, 52, 4, Interface);
		//Line 1
		setBounds(38007, 37, 80, 5, Interface);

		setBounds(38010, 102, 80, 6, Interface);
		setBounds(38013, 167, 80, 7, Interface);
		setBounds(38016, 232, 80, 8, Interface);
		setBounds(38019, 297, 80, 9, Interface);
		setBounds(38022, 362, 80, 10, Interface);
		setBounds(38025, 427, 80, 11, Interface);
		//Line 2
		setBounds(38028, 37, 138, 12, Interface);
		setBounds(38031, 102, 138, 13, Interface);
		setBounds(38034, 167, 138, 14, Interface);
		setBounds(38037, 232, 138, 15, Interface);
		setBounds(38040, 297, 138, 16, Interface);
		setBounds(38043, 362, 138, 17, Interface);
		setBounds(38046, 427, 138, 18, Interface);
		//Line 3
		setBounds(38049, 37, 196, 19, Interface);
		setBounds(38052, 102, 196, 20, Interface);
		setBounds(38055, 167, 196, 21, Interface);
		setBounds(38058, 232, 196, 22, Interface);
		setBounds(38061, 297, 196, 23, Interface);
		setBounds(38064, 362, 196, 24, Interface);
		setBounds(38067, 427, 196, 25, Interface);
		//Line 4
		setBounds(38070, 37, 254, 26, Interface);
		setBounds(38073, 102, 254, 27, Interface);
		setBounds(38076, 167, 254, 28, Interface);
		setBounds(38079, 232, 254, 29, Interface);
		//Other Stuff
		setBounds(38082, 322, 280, 30, Interface);
		setBounds(38083, 322, 280, 31, Interface);
		setBounds(38085, 322, 250, 32, Interface);
		setBounds(38086, 322, 250, 33, Interface);
		setBounds(38088, 360, 253, 34, Interface);
		setBounds(38089, 350, 283, 35, Interface);
		setBounds(38090, 256, 24, 36, Interface);
	}

	public static void vodOverlay() {
		RSInterface rsi = addInterface(126500);

		int childId = 126501;

		addPixels(childId++, 0xFFFFFF, 122, 90, 250, true);
		addPixels(childId++, 0xFFFFFF, 3, 96, 250, true);
		addPixels(childId++, 0xFFFFFF, 122, 3, 250, true);
		addPixels(childId++, 0xFFFFFF, 122, 3, 250, true);
		addPixels(childId++, 0xFFFFFF, 3, 96, 250, true);

		addText(childId++, "Void of Darkness", 0xAF70C3, true, true, -1, tda, 2);
		addText(childId++, "Npc Name", ColorConstants.LIME_GREEN, true, true, -1, tda, 0);
		addText(childId++, "Npc Name", ColorConstants.LIME_GREEN, true, true, -1, tda, 0);
		addText(childId++, "Npc Name", ColorConstants.LIME_GREEN, true, true, -1, tda, 0);
		addText(childId++, "Killcount: 0", 0xAF70C3, true, true, -1, tda, 2);


		rsi.totalChildren(childId - 126501);

		childId = 126501;
		int frame = 0;
		rsi.child(frame++, childId++, 412 - 25, 4);
		rsi.child(frame++, childId++, 409 - 25,1);
		rsi.child(frame++, childId++, 412 - 25, 1);
		rsi.child(frame++, childId++, 412 - 25, 2 + 92);
		rsi.child(frame++, childId++, 509, 1);

		rsi.child(frame++, childId++, 418 - 27 + 57, 6);//Background
		rsi.child(frame++, childId++, 418 - 27 + 57, 25);//Title
		rsi.child(frame++, childId++, 418 - 27 + 57, 40);//Title
		rsi.child(frame++, childId++, 418 - 27 + 57, 55);//Titletle
		rsi.child(frame++, childId, 418 - 27 + 57, 75);//Title
	}

	private static void pestControlInterfaces() {
		RSInterface rsinterface = addTabInterface(21100);
		addSpriteLoader(21101, 680);
		addSpriteLoader(21102, 681);
		addSpriteLoader(21103, 682);
		addSpriteLoader(21104, 683);
		addSpriteLoader(21105, 684);
		addSpriteLoader(21106, 685);
		addText(21107, "W", 0xcc00cc, false, true, 52, tda, 1);
		addText(21108, "E", 0x0000FF, false, true, 52, tda, 1);
		addText(21109, "SE", 0xffff44, false, true, 52, tda, 1);
		addText(21110, "SW", 0xcc0000, false, true, 52, tda, 1);
		addText(21111, "250", 0x99ff33, false, true, 52, tda, 1);
		addText(21112, "250", 0x99ff33, false, true, 52, tda, 1);
		addText(21113, "250", 0x99ff33, false, true, 52, tda, 1);
		addText(21114, "250", 0x99ff33, false, true, 52, tda, 1);
		addText(21115, "***", 0x99ff33, false, true, 52, tda, 1);
		addText(21116, "***", 0x99ff33, false, true, 52, tda, 1);
		addText(21117, "Time Remaining:", 0xffffff, false, true, 52, tda, 0);
		addText(21118, "", 0xffffff, false, true, 52, tda, 0);
		byte byte0 = 18;
		rsinterface.children = new int[byte0];
		rsinterface.childX = new int[byte0];
		rsinterface.childY = new int[byte0];
		setBounds(21101, 361, 26, 0, rsinterface);
		setBounds(21102, 396, 26, 1, rsinterface);
		setBounds(21103, 436, 26, 2, rsinterface);
		setBounds(21104, 474, 26, 3, rsinterface);
		setBounds(21105, 3, 21, 4, rsinterface);
		setBounds(21106, 3, 50, 5, rsinterface);
		setBounds(21107, 371, 60, 6, rsinterface);
		setBounds(21108, 409, 60, 7, rsinterface);
		setBounds(21109, 443, 60, 8, rsinterface);
		setBounds(21110, 479, 60, 9, rsinterface);
		setBounds(21111, 362, 10, 10, rsinterface);
		setBounds(21112, 398, 10, 11, rsinterface);
		setBounds(21113, 436, 10, 12, rsinterface);
		setBounds(21114, 475, 10, 13, rsinterface);
		setBounds(21115, 32, 32, 14, rsinterface);
		setBounds(21116, 32, 62, 15, rsinterface);
		setBounds(21117, 8, 88, 16, rsinterface);
		setBounds(21118, 87, 88, 17, rsinterface);
		RSInterface rsinterface2 = addTabInterface(21005);
		addText(21006, "Next Departure:", 0xCCCBCB, false, true, 52, tda, 1);
		addText(21007, "Players Ready:", 0x5BD230, false, true, 52, tda, 1);
		addText(21008, "(Need 5 to 25 players)", 0xDED36A, false, true, 52, tda, 1);
		addText(21009, "Commendations:", 0x99FFFF, false, true, 52, tda, 1);
		byte0 = 4;
		rsinterface2.children = new int[byte0];
		rsinterface2.childX = new int[byte0];
		rsinterface2.childY = new int[byte0];
		setBounds(21006, 15, 13, 0, rsinterface2);
		setBounds(21007, 15, 33, 1, rsinterface2);
		setBounds(21008, 15, 51, 2, rsinterface2);
		setBounds(21009, 15, 69, 3, rsinterface2);

		RSInterface tab1 = addTabInterface(18730);
		addSpriteLoader(18732, 686);

		addButtonWSpriteLoader(18733, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18734, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18735, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18737, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18740, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18741, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18742, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18745, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18743, 688, "Enchant");

		addButtonWSpriteLoader(18728, 689, "Close Window", 16, 16);

		addText(18729, "", tda, 0, 0xFFA500, false, true);

		// addButton(18776, 0, "PestControl/X", 102, 22, "extra", 1);
		tab1.totalChildren(13);
		tab1.child(0, 18731, 4, 16);
		tab1.child(1, 18732, 4, 16);
		tab1.child(2, 18733, 30, 127);
		tab1.child(3, 18734, 30, 201);
		tab1.child(4, 18735, 184, 127);
		tab1.child(5, 18737, 184, 201);

		tab1.child(6, 18740, 184, 274);
		tab1.child(7, 18741, 338, 127);
		tab1.child(8, 18742, 338, 201);
		tab1.child(9, 18743, 56, 231);
		tab1.child(10, 18728, 480, 20);
		tab1.child(11, 18729, 370, 47);
		tab1.child(12, 18745, 338, 274);

		//tab1.child(17, 18776, 334, 46);
		/* Equipment Tab Void */
		RSInterface tab2 = addTabInterface(18746);

		addSpriteLoader(18747, 690);
		addButtonWSpriteLoader(18748, 691, "Back");

		addButtonWSpriteLoader(18749, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18750, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18751, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18752, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18753, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18754, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18755, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18756, 687, "Purchase", 142, 14);
		addButtonWSpriteLoader(18728, 689, "Close Window", 16, 16);

		// addButton(18776, 0, "PestControl/X", 102, 22, "extra", 1);
		tab2.totalChildren(12);
		tab2.child(0, 18747, 4, 16);
		tab2.child(1, 18748, 56, 231);
		tab2.child(2, 18749, 30, 127);
		tab2.child(3, 18750, 184, 127);
		tab2.child(4, 18751, 340, 127);
		tab2.child(5, 18752, 29, 209);
		tab2.child(6, 18753, 184, 209);
		tab2.child(7, 18754, 339, 209);
		tab2.child(8, 18755, 185, 277);
		tab2.child(9, 18756, 340, 277);
		tab2.child(10, 18728, 480, 20);
		tab2.child(11, 18729, 370, 47);

	}

	private static void friendsTabInterface() {
		RSInterface tab = addTabInterface(5065);
		RSInterface list = interfaceCache[5066];
        addButtonWSpriteLoader(251, 5705, "Switch to Ignore");
		addText(5067, "Friends List", tda, 1, 0xAF70C3, true, true);
		addText(5070, "Add Friend", tda, 0, 0xAF70C3, false, true);
		addText(5071, "Delete Friend", tda, 0, 0xAF70C3, false, true);
		addSpriteLoader(16126, 692);
		addSpriteLoader(16127, 693);
		addHoverButtonWSpriteLoader(5068, 694, 72, 32, "Add Friend", 201, 5072, 1);
		addHoveredImageWSpriteLoader(5072, 695, 72, 32, 5073);
		addHoverButtonWSpriteLoader(5069, 694, 72, 32, "Delete Friend", 202, 5074, 1);
		addHoveredImageWSpriteLoader(5074, 695, 72, 32, 5075);
		tab.totalChildren(12);
		tab.child(0, 5067, 95, 4);
		tab.child(1, 16127, 0, 25);
		tab.child(2, 16126, 0, 221);
		tab.child(3, 5066, 0, 24);
		tab.child(4, 16126, 0, 22);
		tab.child(5, 5068, 15, 226);
		tab.child(6, 5072, 15, 226);
		tab.child(7, 5069, 103, 226);
		tab.child(8, 5074, 103, 226);
		tab.child(9, 5070, 25, 237);
		tab.child(10, 5071, 106, 237);
        tab.child(11, 251, 160, 0);
		list.height = 196;
		list.width = 174;
		int id = 5092;
		for (int i = 0; id <= 5191 && i <= 99; i++) {
			list.children[i] = id;
			list.childX[i] = 3;
			list.childY[i] = list.childY[i] - 7;
			id++;
		}

		id = 5192;
		for (int i = 100; id <= 5291 && i <= 199; i++) {
			list.children[i] = id;
			list.childX[i] = 131;
			list.childY[i] = list.childY[i] - 7;
			id++;
		}
	}

	private static void ignoreTabInterface() {
		RSInterface tab = addTabInterface(5715);
		RSInterface list = interfaceCache[5716];
        addButtonWSpriteLoader(252, 5704, "Switch to Friends");
		addText(5717, "Ignore List", tda, 1, 0xAF70C3, true, true);
		addText(5720, "Add Name", tda, 0, 0xAF70C3, false, true);
		addText(5721, "Delete Name", tda, 0, 0xAF70C3, false, true);
		addHoverButtonWSpriteLoader(5718, 694, 72, 32, "Add Name", 501, 5722, 1);
		addHoveredImageWSpriteLoader(5722, 695, 72, 32, 5723);
		addHoverButtonWSpriteLoader(5719, 694, 72, 32, "Delete Name", 502, 5724, 1);
		addHoveredImageWSpriteLoader(5724, 695, 72, 32, 5725);
		tab.totalChildren(12);
		tab.child(0, 5717, 95, 4);
		tab.child(1, 16127, 0, 25);
		tab.child(2, 16126, 0, 221);
		tab.child(3, 5716, 0, 24);
		tab.child(4, 16126, 0, 22);
		tab.child(5, 5718, 15, 226);
		tab.child(6, 5722, 15, 226);
		tab.child(7, 5719, 103, 226);
		tab.child(8, 5724, 103, 226);
		tab.child(9, 5720, 27, 237);
		tab.child(10, 5721, 108, 237);
        tab.child(11, 252, 160, 0);
		list.height = 196;
		list.width = 174;
		int id = 5742;
		for (int i = 0; id <= 5841 && i <= 99; i++) {
			list.children[i] = id;
			list.childX[i] = 3;
			list.childY[i] = list.childY[i] - 7;
			id++;
		}
	}

	private static void clanChatTabInterface() {
		RSInterface tab = addInterface(29328);
		addHoverButtonWSpriteLoader(29329, 698, 18, 18, "Join Clan", -1, 29330, 1);
		addHoveredImageWSpriteLoader(29330, 699, 18, 18, 29331);
		addHoverButtonWSpriteLoader(29332, 700, 18, 18, "Leave Clan", -1, 29333, 1);
		addHoveredImageWSpriteLoader(29333, 701, 18, 18, 29334);
		addHoverButtonWSpriteLoader(29335, 702, 18, 18, "Settings", -1, 29336, 1);
		addHoveredImageWSpriteLoader(29336, 703, 18, 18, 29337);
		addButtonWSpriteLoader(29455, 20, "Toggle Lootshare");
		addButtonWSpriteLoader(29456, 51, "View players online.");
		addText(29338, "Clan Chat", 0xff9b00, true, true, tda[1]);
		addText(29340, "Talking in: @whi@Not in chat", 0xff9b00, false, true, tda[0]);
		addText(29454, "Lootshare: @gre@On", 0xff9b00, false, true, 52, tda, 0);
		addText(29450, "Owner: None", 0xff9b00, false, true, tda[0]);
		addSpriteLoader(29339, 705);
		tab.totalChildren(16);
		tab.child(0, 16126, 0, 236);
		tab.child(1, 16126, 0, 62);
		tab.child(2, 29339, 0, 62);
		tab.child(3, 29343, 0, 62);
		tab.child(4, 29329, 8, 239);
		tab.child(5, 29330, 8, 239);
		tab.child(6, 29332, 25, 239);
		tab.child(7, 29333, 25, 239);
		tab.child(8, 29335, 42, 239);
		tab.child(9, 29336, 42, 239);
		tab.child(10, 29338, 95, 1);
		tab.child(11, 29340, 10, 15);
		tab.child(12, 29450, 10, 41);
		tab.child(13, 29454, 10, 28);
		tab.child(14, 29455, 150, 23);
		tab.child(15, 29456, 110, 23);
		rebuildClanChatList(false, "", false);
	}

	private static void rebuildClanChatList(boolean clickable, String ignore, boolean owner) {
		/* Text area */
		for (int i = 29344; i <= 29444; i++) {
			if (clickable && !RSInterface.interfaceCache[i].message.isEmpty()) {
				addClanChatListTextWithOptions(i, RSInterface.interfaceCache[i].message, ignore, owner, tda, 0, 0xffffff, 200, 11);
			} else {
				addText(i, RSInterface.interfaceCache[i] == null ? "" + i + "" : RSInterface.interfaceCache[i].message, tda, 0, 0xffffff, false, true);
			}
		}
		RSInterface list = addInterface(29343);
		list.totalChildren(100);
		for (int id = 29344, i = 0; id <= 29443 && i <= 99; id++, i++) {
			list.child(id - 29344, id, 5, -1);
			for (int id2 = 29344, i2 = 1; id2 <= 29443 && i2 <= 99; id2++, i2++) {
				list.childY[0] = 2;
				list.childY[i2] = list.childY[i2 - 1] + 14;
			}
		}
		list.height = 174;
		list.width = 174;
		list.scrollMax = 1405;
	}

	private static void redoSpellBooks() {
		RSInterface newInterface = addTabInterface(11000);
		RSInterface spellButtons = interfaceCache[1151];
		RSInterface spellButtons1 = interfaceCache[12424];
		newInterface.totalChildren(13);
		/**
		 * Modern spellbook
		 */
		spellButtons1.scrollMax = 300;
		spellButtons1.height = 300;
		newInterface.child(0, 1151, 13, 32);

int x = 10;
		addHoverButtonWSpriteLoader(11001, 706, 18, 18, "Select", -1, 11002, 1);
		addTooltip(11002, "Travel Home\nTeleport to set home location.");
		newInterface.child(1, 11001, 8 + x, 16);
		newInterface.child(2, 11002, 10 + x, 39);
		addHoverButtonWSpriteLoader(11004, 711, 18, 18, "Select", -1, 11005, 1);
		addTooltip(11005, "Global Bosses\nAccess our Global Bosses!");
		newInterface.child(3, 11004, 34 + x, 16);
		newInterface.child(4, 11005, 30 + x, 39);
		addHoverButtonWSpriteLoader(11008, 710, 18, 18, "Select", -1, 11009, 1);
		addTooltip(11009, "Minigames\nTry our our unique Minigames.");
		newInterface.child(5, 11008, 60 + x, 16);
		newInterface.child(6, 11009, 40 + x, 39);
		addHoverButtonWSpriteLoader(11011, 707, 18, 18, "Select", -1, 11012, 1);
		addTooltip(11012, "Skilling\nAccess our Custom \nSkilling Island");
		newInterface.child(7, 11011, 86 + x, 16);
		newInterface.child(8, 11012, 23 + x, 39);
		addHoverButtonWSpriteLoader(11014, 709, 18, 18, "Select", -1, 11015, 1);
		addTooltip(11015, "Raids\nGet your team together!");
		newInterface.child(9, 11014, 112 + x, 16);
		newInterface.child(10, 11015, 23 + x, 39);
		addHoverButtonWSpriteLoader(11017, 708, 18, 18, "Select", -1, 11018, 1);
		addTooltip(11018, "Donator Zones\nAccess our Custom \nDonation Zones");
		newInterface.child(11, 11017, 138 + x, 16);
		newInterface.child(12, 11018, 34 + x, 39);

		interfaceCache[1164] = interfaceCache[1165];
		interfaceCache[1165] = interfaceCache[1166];
		interfaceCache[1166] = interfaceCache[1168];
		interfaceCache[1167] = interfaceCache[1169];
		interfaceCache[1168] = interfaceCache[1171];
		interfaceCache[1169] = interfaceCache[1172];
		interfaceCache[1170] = interfaceCache[1173];
		interfaceCache[1171] = interfaceCache[1175];
		interfaceCache[1172] = interfaceCache[1176];
		interfaceCache[1173] = interfaceCache[1539];
		interfaceCache[1174] = interfaceCache[1582];
		interfaceCache[1175] = interfaceCache[12037];
		interfaceCache[1176] = interfaceCache[1177];
		interfaceCache[1539] = interfaceCache[1178];
		interfaceCache[1582] = interfaceCache[1179];
		interfaceCache[12037] = interfaceCache[1180];
		interfaceCache[1540] = interfaceCache[1181];
		interfaceCache[1177] = interfaceCache[1182];
		interfaceCache[1178] = interfaceCache[15877];
		interfaceCache[1179] = interfaceCache[1190];
		interfaceCache[1180] = interfaceCache[1191];
		interfaceCache[1541] = interfaceCache[1192];
		interfaceCache[1181] = interfaceCache[1183];
		interfaceCache[1182] = interfaceCache[1184];
		interfaceCache[15877] = interfaceCache[1185];
		interfaceCache[1190] = interfaceCache[1186];
		interfaceCache[1191] = interfaceCache[1542];
		interfaceCache[1192] = interfaceCache[1187];
		interfaceCache[7455] = interfaceCache[1188];
		interfaceCache[1183] = interfaceCache[1543];
		interfaceCache[1184] = interfaceCache[12425];
		interfaceCache[18470] = interfaceCache[1189];
		interfaceCache[1185] = interfaceCache[1592];
		interfaceCache[1186] = interfaceCache[1562];
		interfaceCache[1542] = interfaceCache[1193];
		interfaceCache[1187] = interfaceCache[12435];
		interfaceCache[1188] = interfaceCache[12445];
		interfaceCache[1543] = interfaceCache[6003];
		interfaceCache[12425] = interfaceCache[12455];
		removeSomething(1189);
		removeSomething(1592);
		removeSomething(1562);
		removeSomething(1193);
		removeSomething(12435);
		removeSomething(12445);
		removeSomething(6003);
		removeSomething(12455);


/*	   addHoverButtonWSpriteLoader(11004, 707, 18, 18, "Select", -1, 11005, 1);
		addTooltip(11005, "Skills Teleport\nOpen options of different \nskilling teleports.");
		newInterface.child(3, 11004, 34, 8);
		newInterface.child(4, 11005, 30, 39);
		addHoverButtonWSpriteLoader(11008, 708, 18, 18, "Select", -1, 11009, 1);
		addTooltip(11009, "Training Teleport\nOpen options of different \ntraining teleports.");
		newInterface.child(5, 11008, 60, 8);
		newInterface.child(6, 11009, 40, 39);
		addHoverButtonWSpriteLoader(11011, 709, 18, 18, "Select", -1, 11012, 1);
		addTooltip(11012, "Dungeon Teleport\nOpen options of different\ndungeon teleports.");
		newInterface.child(7, 11011, 86, 8);
		newInterface.child(8, 11012, 23, 39);
		addHoverButtonWSpriteLoader(11014, 710, 18, 18, "Select", -1, 11015, 1);
		addTooltip(11015, "Boss Teleport\nOpen options of different\nboss teleports.");
		newInterface.child(9, 11014, 112, 8);
		newInterface.child(10, 11015, 23, 39);
		addHoverButtonWSpriteLoader(11017, 711, 18, 18, "Select", -1, 11018, 1);
		addTooltip(11018, "Minigame Teleport\nOpen options of different\nminigame teleports.");
		newInterface.child(11, 11017, 138, 8);
		newInterface.child(12, 11018, 34, 39);
		addHoverButtonWSpriteLoader(11020, 712, 18, 18, "Select", -1, 11021, 1);
		addTooltip(11021, "Wilderness Teleport\nOpen options of different\nwilderness teleports.");
		newInterface.child(13, 11020, 164, 8);
		newInterface.child(14, 11021, 40, 39);*/
		/**
		 * Ancient spellbook
		 */
		newInterface = addTabInterface(11500);
		spellButtons = interfaceCache[12855];
		newInterface.totalChildren(13);
		spellButtons.scrollMax = 0;
		spellButtons.height = 400;
		spellButtons.width = 190;
		newInterface.child(0, 12855, 0, 32);

		newInterface.child(1, 11001, 8 + x, 16);
		newInterface.child(2, 11002, 10 + x, 39);
		newInterface.child(3, 11004, 34 + x, 16);
		newInterface.child(4, 11005, 30 + x, 39);
		newInterface.child(5, 11008, 60 + x, 16);
		newInterface.child(6, 11009, 40 + x, 39);
		newInterface.child(7, 11011, 86 + x, 16);
		newInterface.child(8, 11012, 23 + x, 39);
		newInterface.child(9, 11014, 112 + x, 16);
		newInterface.child(10, 11015, 23 + x, 39);
		newInterface.child(11, 11017, 138 + x, 16);
		newInterface.child(12, 11018, 34 + x, 39);

		interfaceCache[13035] = interfaceCache[12901];
		interfaceCache[12901] = interfaceCache[12861];
		interfaceCache[12861] = interfaceCache[12963];
		interfaceCache[13045] = interfaceCache[13011];
		interfaceCache[12963] = interfaceCache[12919];
		interfaceCache[13011] = interfaceCache[12881];
		interfaceCache[13053] = interfaceCache[12951];
		interfaceCache[12919] = interfaceCache[12999];
		interfaceCache[12881] = interfaceCache[12911];
		interfaceCache[13061] = interfaceCache[12871];
		interfaceCache[12951] = interfaceCache[12975];
		interfaceCache[12999] = interfaceCache[13023];
		interfaceCache[13069] = interfaceCache[12929];
		interfaceCache[12911] = interfaceCache[12891];
		removeSomething(interfaceCache[12871]);
		removeSomething(interfaceCache[13079]);
		removeSomething(interfaceCache[12975]);
		removeSomething(interfaceCache[13023]);
		removeSomething(interfaceCache[13087]);
		removeSomething(interfaceCache[12929]);
		removeSomething(interfaceCache[12891]);
		removeSomething(interfaceCache[13095]);


		/*	addHoverButtonWSpriteLoader(11008, 708, 18, 18, "Select", -1, 11009, 1);
		addTooltip(11009, "Training Teleport\nOpen options of different \ntraining teleports.");
		newInterface.child(5, 11008, 60, 16);
		newInterface.child(6, 11009, 40, 39);
		addHoverButtonWSpriteLoader(11011, 709, 18, 18, "Select", -1, 11012, 1);
		addTooltip(11012, "Dungeon Teleport\nOpen options of different\ndungeon teleports.");
		newInterface.child(7, 11011, 86, 16);
		newInterface.child(8, 11012, 23, 39);
		addHoverButtonWSpriteLoader(11014, 710, 18, 18, "Select", -1, 11015, 1);
		addTooltip(11015, "Boss Teleport\nOpen options of different\nboss teleports.");
		newInterface.child(9, 11014, 112, 16);
		newInterface.child(10, 11015, 23, 39);
		addHoverButtonWSpriteLoader(11017, 711, 18, 18, "Select", -1, 11018, 1);
		addTooltip(11018, "Minigame Teleport\nOpen options of different\nminigame teleports.");
		newInterface.child(11, 11017, 138, 16);
		newInterface.child(12, 11018, 34, 39);
		addHoverButtonWSpriteLoader(11020, 712, 18, 18, "Select", -1, 11021, 1);
		addTooltip(11021, "Wilderness Teleport\nOpen options of different\nWilderness teleports.");
		newInterface.child(13, 11020, 164, 16);
		newInterface.child(14, 11021, 40, 39);*/

		/**
		 * Lunar
		 */
		newInterface = addInterface(11800);
		spellButtons = interfaceCache[29999];
		newInterface.totalChildren(13);
		/**
		 * Change spellbook
		 */
		spellButtons.scrollMax = 0;
		spellButtons.height = 400;
		spellButtons.width = 190;
		newInterface.child(0, 29999, 0, 32);

		newInterface.child(1, 11001, 8 + x, 16);
		newInterface.child(2, 11002, 10 + x, 39);
		newInterface.child(3, 11004, 34 + x, 16);
		newInterface.child(4, 11005, 30 + x, 39);
		newInterface.child(5, 11008, 60 + x, 16);
		newInterface.child(6, 11009, 40 + x, 39);
		newInterface.child(7, 11011, 86 + x, 16);
		newInterface.child(8, 11012, 23 + x, 39);
		newInterface.child(9, 11014, 112 + x, 16);
		newInterface.child(10, 11015, 23 + x, 39);
		newInterface.child(11, 11017, 138 + x, 16);
		newInterface.child(12, 11018, 34 + x, 39);
		/*	addHoverButtonWSpriteLoader(11004, 707, 18, 18, "Select", -1, 11005, 1);
		addTooltip(11005, "Skills Teleport\nOpen options of different \nskilling teleports.");
		newInterface.child(3, 11004, 34, 16);
		newInterface.child(4, 11005, 30, 39);
		addHoverButtonWSpriteLoader(11008, 708, 18, 18, "Select", -1, 11009, 1);
		addTooltip(11009, "Training Teleport\nOpen options of different \ntraining teleports.");
		newInterface.child(5, 11008, 60, 16);
		newInterface.child(6, 11009, 40, 39);
		addHoverButtonWSpriteLoader(11011, 709, 18, 18, "Select", -1, 11012, 1);
		addTooltip(11012, "Dungeon Teleport\nOpen options of different\ndungeon teleports.");
		newInterface.child(7, 11011, 86, 16);
		newInterface.child(8, 11012, 23, 39);
		addHoverButtonWSpriteLoader(11014, 710, 18, 18, "Select", -1, 11015, 1);
		addTooltip(11015, "Boss Teleport\nOpen options of different\nboss teleports.");
		newInterface.child(9, 11014, 112, 16);
		newInterface.child(10, 11015, 23, 39);
		addHoverButtonWSpriteLoader(11017, 711, 18, 18, "Select", -1, 11018, 1);
		addTooltip(11018, "Minigame Teleport\nOpen options of different\nminigame teleports.");
		newInterface.child(11, 11017, 138, 16);
		newInterface.child(12, 11018, 34, 39);
		addHoverButtonWSpriteLoader(11020, 712, 18, 18, "Select", -1, 11021, 1);
		addTooltip(11021, "Wilderness Teleport\nOpen options of different\nWilderness teleports.");
		newInterface.child(13, 11020, 164, 16);
		newInterface.child(14, 11021, 40, 39);*/
	}

	public static void leaderboardInterface() {
		int interID = 120000;//154000
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 40;
		int y = 25;
		tab.totalChildren(15);

		addSpriteLoader(id, 3349);
		tab.child(c++, id++, 0 + x, 0 + y);

		addHoverButtonWSpriteLoader(id, 1016, 18, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 418 + x, 1 + y);
		addHoveredImageWSpriteLoader(id, 1017, 18, 16, id + 1);
		tab.child(c++, id++, 418 + x, 1 + y);
		id++;

		String[] tabs = new String[]{"     Main", " Monsters"};

		for (int i = 0; i < tabs.length; i++) {
			addConfigButtonWSpriteLoader(id, interID, 3350, 3351, 79, 20, tabs[i], i, 5, 4511);
			tab.child(c++, id++, 9 + x, 20 + y);
			addText(id, tabs[i], 0xFFA500, false, true, 100, tda, 1);
			tab.child(c++, id++, 20 + x, 24 + y);
			x += 79;
		}
		x = 40;

		addText(id, "Athens Leaderboard", tda, 2, 0xFCFCFC, true, true);
		tab.child(c++, id++, 287 + x, 23 + y);

		addText(id, "Categories", tda, 2, 0xFCFCFC, true, true);
		tab.child(c++, id++, 87 + x, 58 + y);

		addText(id, "@yel@Rank", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 192 + x, 58 + y);

		addText(id, "@yel@Amount", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 256 + x, 58 + y);

		addText(id, "@yel@Player", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 351 + x, 58 + y);


		addTextRight(id, "@gre@PR: ", tda, 2, 0xFF981F, true);
		tab.child(c++, id++, 470 + x, 25 + y);


		tab.child(c++, 120100, 12 + x, 76 + y);
		tab.child(c++, 120500, 170 + x, 77 + y);

		interID = 120100;
		RSInterface scroll = addInterface(interID);
		scroll.width = 150 - 16;
		scroll.height = 194;
		scroll.scrollMax = 500;
		scroll.totalChildren(150);
		id = interID + 1;
		c = 0;
		x = 0;
		y = 0;


		for (int i = 0; i < 50; i++) {
			addSpriteLoader(id, 3352);
			scroll.child(c++, id++, 0 + x, 0 + y);
			y += 36;
		}
		y = 0;

		for (int i = 0; i < 100; i++) {
			teleportText(id, "Test", "Select", fonts, 0, 0xFCFCFC, false, true, 169, 17);
			scroll.child(c++, id++, 2 + x, 3 + y);
			y += 18;
		}



		interID = 120500;
		RSInterface list = addTabInterface(interID);
		list.totalChildren(350);
		list.width = 252 - 16;
		list.height = 193;
		list.scrollMax = 1650;
		id = interID + 1;
		y = 0;
		c = 0;
		int j;
		for (j = 0; j < 50; j++) {
			addSpriteLoader(id, 3353);
			list.child(c++, id++, 0, 0 + y);
			y += 66;
		}
		y = 10;
		for (j = 0; j < 100; j++) {
			addText(id, "@red@#" + (j + 1), 16751360, true, true, 100, fonts, 1);
			list.child(c++, id++, 20, y);
			addText(id, "1,000", 16751360, true, true, 100, fonts, 1);
			list.child(c++, id++, 85, y);
			addText(id, "Test", 16751360, true, true, 100, fonts, 1);
			list.child(c++, id++, 180, y);
			y += 33;
		}




	}

	public void fairy() {
		RSInterface main = addInterface(31090);
		addSpriteLoader(31091, 3355);

		addText(31092, "MyFairy", ColorConstants.SNOW_WHITE, true, true, 52, tda, 2);

		addText(31093, "Dmg:", 0xe6dc27, false, true, 52, tda, 0);
		addText(31094, "Dr:", 0xe6dc27, false, true, 52, tda, 0);

		addPercentageBar(31095, 140, 100, 0x710a0a, 0x2fa407, 9, false);

		addText(31096, "Lvl: 3", ColorConstants.SNOW_WHITE, false, true, 52, tda, 2);

		addPet(31097);

		addHoverButtonWSpriteLoader(31098, 3380, 15, 20, "Close", -1, 31099, 1);
		addHoveredImageWSpriteLoader(31099, 3381, 15, 20, 31100);

		addSpriteLoader(31101, 3356);

		addItemOnInterface(31102, 31090, new String[] { null });
		addItemOnInterface(31103, 31090, new String[] { null });
		addItemOnInterface(31104, 31090, new String[] { null });

		main.totalChildren(15);
		main.child(0, 31091, 140, 45);
		main.child(1, 31092, 250, 68);
		main.child(2, 31093, 210, 155);
		main.child(3, 31094, 259, 155);
		main.child(4, 31095, 181, 207);
		main.child(5, 31096, 234, 85);
		main.child(6, 31097, 183, 97);
		main.child(7, 31098, 309, 105);
		main.child(8, 31099, 309, 105);
		main.child(9, 31101, 197, 168);
		main.child(10, 31101, 232, 168);
		main.child(11, 31101, 267, 168);

		main.child(12, 31102, 199, 169);
		main.child(13, 31103, 234, 169);
		main.child(14, 31104, 269, 169);
	}

	public static void VoteStreaksInterface() {
		int interID = 149000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 60;
		int y = 7;
		tab.totalChildren(96);

		addSpriteLoader(id, 3360);
		tab.child(c++, id++, 0 + x, 0 + y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 367 + x, 3 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 367 + x, 3 + y);
		id++;

		addText(id, "@whi@  Vote Streaks", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 194 + x, 4 + y);

		addText(id, "@yel@Resets: 23h 35m", tda, 2, 0xFFffff, true, true);
		tab.child(c++, id++, 199 + x, 23 + y);


		addText(id, "", tda, 1, 0xFF981F, false, true);
		tab.child(c++, id++, 11 + x, 21 + y);

		int day = 1;
		x += 11;
		y += 39;

		for (int z = 0; z < 5; z++) {
			for (int r = 0; r < 6; r++) {

				addSpriteLoader(id, day <= 5 ? 3362 : day == 6 ? 3361 : 3541);
				tab.child(c++, id++, 0 + x, 0 + y);

				addText(id, "Day " + (day++), tda, 0, 0xF8EA12, true, true);
				tab.child(c++, id++, 29 + x, 2 + y);

				dropGroup(id, 1, 1, 1, 1); //
				tab.child(c++, id++, 14 + x, 17 + y);
				x += 62;
			}
			y += 55;
			x = 72;
		}
	}

	private static void summoningTabInterface() {
		RSInterface rsi = addTabInterface(54017);

		addText(54028, "Summoning familiar", tda, 2, 0xFF981F, true, true);
		addText(54019, "", tda, 0, 16750623, true, true);

		addSpriteLoader(54020, 1576);

		addNpc(54021, 50);
		//addFamiliarHead(54021, 75, 50, 875);
		//	addPet(54021);
		addText(54027, "", tda, 2, 0xFF981F, true, false);
		//addSpriteLoader(54027, 740);

		addHoverButtonWSpriteLoader(54029, 3525, 38, 38, "Call Follower", -1, 54030, 1);
		addHoveredImageWSpriteLoader(54030, 3528, 38, 38, 54031);

		addHoverButtonWSpriteLoader(54032, 3526, 38, 38, "Dismiss Follower", -1, 54033, 1);
		addHoveredImageWSpriteLoader(54033, 3529, 38, 38, 54034);

		addHoverButtonWSpriteLoader(54035, 3527, 38, 38, "Info", -1, 54036, 1);
		addHoveredImageWSpriteLoader(54036, 3530, 38, 38, 54037);

		addSpriteLoader(54039, -1);
		addSpriteLoader(54041, -1);
		addSpriteLoader(54042, -1);

		setChildren(14, rsi);
		int c = 0;
		setBounds(54020, 12, 9, c++, rsi);
		setBounds(54021, 30, 35, c++, rsi);
		setBounds(54027, 12, 144, c++, rsi);

		setBounds(54029, 33, 142, c++, rsi);
		setBounds(54030, 33, 142, c++, rsi);

		setBounds(54032, 77, 142, c++, rsi);
		setBounds(54033, 77, 142, c++, rsi);

		setBounds(54035, 121, 142, c++, rsi);
		setBounds(54036, 121, 142, c++, rsi);

		setBounds(54039, 41, 152, c++, rsi);
		setBounds(54041, 83, 148, c++, rsi);
		setBounds(54042, 131, 150, c++, rsi);

		setBounds(54019, 95, 187, c++, rsi);
		setBounds(54028, 94, 12, c++, rsi);

	}

	private static void loyaltyShop() {
		RSInterface rsi = addTabInterface(43000);
		rsi.totalChildren(74);
		addSpriteLoader(43001, 752);
		addText(43002, "Loyalty Titles", tda, 2, 16750623, false, true);
		addCloseButton(43003, 43121, 43122);
		rsi.child(0, 43001, 10, 20);
		rsi.child(1, 43002, 210, 22);
		rsi.child(2, 43003, 466, 19);

		/** BUY BUTTONS **/
		int id = 43004, child = 3;
		for (int i = 0; i < 12; i++) {
			int y = i == 1 ? 62 : i == 2 ? 83 : i == 3 ? 105 : i == 4 ? 127 : i == 5 ? 149 : i == 6 ? 171 : i == 7 ? 193 : i == 8 ? 215 : i == 9 ? 237 : i == 10 ? 259 : i == 11 ? 281 : -1;

			if (id != 43004) {
				addHoverButtonWSpriteLoader(id, 754, 32, 17, "Buy", -1, id + 1, 1);
				addHoveredImageWSpriteLoader(id + 1, 753, 32, 17, id + 2);
			} else {
				removeSomething(id);
				removeSomething(id + 1);
			}

			addText(id + 3, "", tda, 0, 0xB9B855, false, true);

			rsi.child(child, id, 213, y);
			rsi.child(child + 1, id + 1, 213, y);
			rsi.child(child + 2, id + 3, 158, y + 5);
			child += 3;
			id += 4;
		}
		for (int i = 1; i < 12; i++) {
			int y = i == 1 ? 62 : i == 2 ? 83 : i == 3 ? 105 : i == 4 ? 127 : i == 5 ? 149 : i == 6 ? 171 : i == 7 ? 193 : i == 8 ? 215 : i == 9 ? 237 : i == 10 ? 259 : i == 11 ? 281 : -1;


			addHoverButtonWSpriteLoader(id, 754, 32, 17, "Buy", -1, id + 1, 1);
			addHoveredImageWSpriteLoader(id + 1, 753, 32, 17, id + 2);

			addText(id + 3, "", tda, 0, 0xB9B855, false, true);

			rsi.child(child, id, 428, y);
			rsi.child(child + 1, id + 1, 428, y);
			rsi.child(child + 2, id + 3, 373, y + 5);

			child += 3;
			id += 4;
		}
		addText(43120, "Your Loyalty Points: 0", tda, 0, 0xB9B855, false, true);
		rsi.child(72, 43120, 195, 43);
		rsi.child(73, 43121, 466, 19);
	}

	private static void emoteTab() {
		RSInterface tab = addTabInterface(147);
		RSInterface scroll = addTabInterface(148);
		tab.totalChildren(1);
		tab.child(0, 148, 0, 1);
		addButton(168, 163, "Yes", 41, 47);
		addButton(169, 164, "No", 41, 47);
		addButton(164, 165, "Bow", 41, 47);
		addButton(165, 166, "Angry", 41, 47);
		addButton(162, 167, "Think", 41, 47);
		addButton(163, 168, "Wave", 41, 47);
		addButton(13370, 169, "Shrug", 41, 47);
		addButton(171, 170, "Cheer", 41, 47);
		addButton(167, 171, "Beckon", 41, 47);
		addButton(170, 172, "Laugh", 41, 47);
		addButton(13366, 173, "Jump for Joy", 41, 47);
		addButton(13368, 174, "Yawn", 41, 47);
		addButton(166, 175, "Dance", 41, 47);
		addButton(13363, 176, "Jig", 41, 47);
		addButton(13364, 177, "Spin", 41, 47);
		addButton(13365, 178, "Headbang", 41, 47);
		addButton(161, 179, "Cry", 41, 47);
		addButton(11100, 180, "Blow kiss", 41, 47);
		addButton(13362, 181, "Panic", 41, 47);
		addButton(13367, 182, "Raspberry", 41, 47);
		addButton(172, 183, "Clap", 41, 47);
		addButton(13369, 184, "Salute", 41, 47);
		addButton(13383, 185, "Goblin Bow", 41, 47);
		addButton(13384, 186, "Goblin Salute", 41, 47);
		addButton(667, 187, "Glass Box", 41, 47);
		addButton(6503, 188, "Climb Rope", 41, 47);
		addButton(6506, 189, "Lean On Air", 41, 47);
		addButton(666, 190, "Glass Wall", 41, 47);
		addButton(18464, 191, "Zombie Walk", 41, 47);
		addButton(18465, 192, "Zombie Dance", 41, 47);
		addButton(15166, 997, "Frozen", 41, 47);
		addButton(18686, 194, "Rabbit Hop", 41, 47);
		scroll.totalChildren(32);
		scroll.child(0, 168, 10, 7);
		scroll.child(1, 169, 54, 7);
		scroll.child(2, 164, 98, 14);
		scroll.child(3, 165, 137, 7);
		scroll.child(4, 162, 9, 56);
		scroll.child(5, 163, 48, 56);
		scroll.child(6, 13370, 95, 56);
		scroll.child(7, 171, 137, 56);
		scroll.child(8, 167, 7, 105);
		scroll.child(9, 170, 51, 105);
		scroll.child(10, 13366, 95, 104);
		scroll.child(11, 13368, 139, 105);
		scroll.child(12, 166, 6, 154);
		scroll.child(13, 13363, 50, 154);
		scroll.child(14, 13364, 90, 154);
		scroll.child(15, 13365, 135, 154);
		scroll.child(16, 161, 8, 204);
		scroll.child(17, 11100, 51, 203);
		scroll.child(18, 13362, 99, 204);
		scroll.child(19, 13367, 137, 203);
		scroll.child(20, 172, 10, 253);
		scroll.child(21, 13369, 53, 253);
		scroll.child(22, 13383, 88, 258);
		scroll.child(23, 13384, 138, 252);
		scroll.child(24, 667, 2, 303);
		scroll.child(25, 6503, 49, 302);
		scroll.child(26, 6506, 93, 302);
		scroll.child(27, 666, 137, 302);
		scroll.child(28, 18464, 9, 352);
		scroll.child(29, 18465, 50, 352);
		scroll.child(30, 15166, 94, 354);
		scroll.child(31, 18686, 141, 353);
		scroll.width = 173;
		scroll.height = 258;
		scroll.scrollMax = 400;
	}

	private static void levelUpInterfaces() {
		RSInterface attack = interfaceCache[6247];
		RSInterface defence = interfaceCache[6253];
		RSInterface str = interfaceCache[6206];
		RSInterface hits = interfaceCache[6216];
		RSInterface rng = interfaceCache[4443];
		RSInterface pray = interfaceCache[6242];
		RSInterface mage = interfaceCache[6211];
		RSInterface cook = interfaceCache[6226];
		RSInterface wood = interfaceCache[4272];
		RSInterface flet = interfaceCache[6231];
		RSInterface fish = interfaceCache[6258];
		RSInterface fire = interfaceCache[4282];
		RSInterface craf = interfaceCache[6263];
		RSInterface smit = interfaceCache[6221];
		RSInterface mine = interfaceCache[4416];
		RSInterface herb = interfaceCache[6237];
		RSInterface agil = interfaceCache[4277];
		RSInterface thie = interfaceCache[4261];
		RSInterface slay = interfaceCache[12122];
		RSInterface farm = addTabInterface(5267);
		RSInterface rune = interfaceCache[4267];
		RSInterface cons = interfaceCache[7267];
		RSInterface hunt = addTabInterface(8267);
		RSInterface summ = addTabInterface(9267);
		RSInterface dung = addTabInterface(10267);
		addSkillChatSprite(29578, 0);
		addSkillChatSprite(29579, 1);
		addSkillChatSprite(29580, 2);
		addSkillChatSprite(29581, 3);
		addSkillChatSprite(29582, 4);
		addSkillChatSprite(29583, 5);
		addSkillChatSprite(29584, 6);
		addSkillChatSprite(29585, 7);
		addSkillChatSprite(29586, 8);
		addSkillChatSprite(29587, 9);
		addSkillChatSprite(29588, 10);
		addSkillChatSprite(29589, 11);
		addSkillChatSprite(29590, 12);
		addSkillChatSprite(29591, 13);
		addSkillChatSprite(29592, 14);
		addSkillChatSprite(29593, 15);
		addSkillChatSprite(29594, 16);
		addSkillChatSprite(29595, 17);
		addSkillChatSprite(29596, 18);
		addSkillChatSprite(11897, 19);
		addSkillChatSprite(29598, 20);
		addSkillChatSprite(29599, 21);
		addSkillChatSprite(29600, 22);
		addSkillChatSprite(29601, 23);
		addSkillChatSprite(29602, 24);
		setChildren(4, attack);
		setBounds(29578, 20, 30, 0, attack);
		setBounds(4268, 80, 15, 1, attack);
		setBounds(4269, 80, 45, 2, attack);
		setBounds(358, 95, 75, 3, attack);
		setChildren(4, defence);
		setBounds(29579, 20, 30, 0, defence);
		setBounds(4268, 80, 15, 1, defence);
		setBounds(4269, 80, 45, 2, defence);
		setBounds(358, 95, 75, 3, defence);
		setChildren(4, str);
		setBounds(29580, 20, 30, 0, str);
		setBounds(4268, 80, 15, 1, str);
		setBounds(4269, 80, 45, 2, str);
		setBounds(358, 95, 75, 3, str);
		setChildren(4, hits);
		setBounds(29581, 20, 30, 0, hits);
		setBounds(4268, 80, 15, 1, hits);
		setBounds(4269, 80, 45, 2, hits);
		setBounds(358, 95, 75, 3, hits);
		setChildren(4, rng);
		setBounds(29582, 20, 30, 0, rng);
		setBounds(4268, 80, 15, 1, rng);
		setBounds(4269, 80, 45, 2, rng);
		setBounds(358, 95, 75, 3, rng);
		setChildren(4, pray);
		setBounds(29583, 20, 30, 0, pray);
		setBounds(4268, 80, 15, 1, pray);
		setBounds(4269, 80, 45, 2, pray);
		setBounds(358, 95, 75, 3, pray);
		setChildren(4, mage);
		setBounds(29584, 20, 30, 0, mage);
		setBounds(4268, 80, 15, 1, mage);
		setBounds(4269, 80, 45, 2, mage);
		setBounds(358, 95, 75, 3, mage);
		setChildren(4, cook);
		setBounds(29585, 20, 30, 0, cook);
		setBounds(4268, 80, 15, 1, cook);
		setBounds(4269, 80, 45, 2, cook);
		setBounds(358, 95, 75, 3, cook);
		setChildren(4, wood);
		setBounds(29586, 20, 30, 0, wood);
		setBounds(4268, 80, 15, 1, wood);
		setBounds(4269, 80, 45, 2, wood);
		setBounds(358, 95, 75, 3, wood);
		setChildren(4, flet);
		setBounds(29587, 20, 30, 0, flet);
		setBounds(4268, 80, 15, 1, flet);
		setBounds(4269, 80, 45, 2, flet);
		setBounds(358, 95, 75, 3, flet);
		setChildren(4, fish);
		setBounds(29588, 20, 30, 0, fish);
		setBounds(4268, 80, 15, 1, fish);
		setBounds(4269, 80, 45, 2, fish);
		setBounds(358, 95, 75, 3, fish);
		setChildren(4, fire);
		setBounds(29589, 20, 30, 0, fire);
		setBounds(4268, 80, 15, 1, fire);
		setBounds(4269, 80, 45, 2, fire);
		setBounds(358, 95, 75, 3, fire);
		setChildren(4, craf);
		setBounds(29590, 20, 30, 0, craf);
		setBounds(4268, 80, 15, 1, craf);
		setBounds(4269, 80, 45, 2, craf);
		setBounds(358, 95, 75, 3, craf);
		setChildren(4, smit);
		setBounds(29591, 20, 30, 0, smit);
		setBounds(4268, 80, 15, 1, smit);
		setBounds(4269, 80, 45, 2, smit);
		setBounds(358, 95, 75, 3, smit);
		setChildren(4, mine);
		setBounds(29592, 20, 30, 0, mine);
		setBounds(4268, 80, 15, 1, mine);
		setBounds(4269, 80, 45, 2, mine);
		setBounds(358, 95, 75, 3, mine);
		setChildren(4, herb);
		setBounds(29593, 20, 30, 0, herb);
		setBounds(4268, 80, 15, 1, herb);
		setBounds(4269, 80, 45, 2, herb);
		setBounds(358, 95, 75, 3, herb);
		setChildren(4, agil);
		setBounds(29594, 20, 30, 0, agil);
		setBounds(4268, 80, 15, 1, agil);
		setBounds(4269, 80, 45, 2, agil);
		setBounds(358, 95, 75, 3, agil);
		setChildren(4, thie);
		setBounds(29595, 20, 30, 0, thie);
		setBounds(4268, 80, 15, 1, thie);
		setBounds(4269, 80, 45, 2, thie);
		setBounds(358, 95, 75, 3, thie);
		setChildren(4, slay);
		setBounds(29596, 20, 30, 0, slay);
		setBounds(4268, 80, 15, 1, slay);
		setBounds(4269, 80, 45, 2, slay);
		setBounds(358, 95, 75, 3, slay);
		setChildren(4, farm);
		setBounds(11897, 20, 30, 0, farm);
		setBounds(4268, 80, 15, 1, farm);
		setBounds(4269, 80, 45, 2, farm);
		setBounds(358, 95, 75, 3, farm);
		setChildren(4, rune);
		setBounds(29598, 20, 30, 0, rune);
		setBounds(4268, 80, 15, 1, rune);
		setBounds(4269, 80, 45, 2, rune);
		setBounds(358, 95, 75, 3, rune);
		setChildren(3, cons);
		setBounds(4268, 80, 15, 0, cons);
		setBounds(4269, 80, 45, 1, cons);
		setBounds(358, 95, 75, 2, cons);
		setChildren(4, hunt);
		setBounds(29600, 20, 30, 0, hunt);
		setBounds(4268, 80, 15, 1, hunt);
		setBounds(4269, 80, 45, 2, hunt);
		setBounds(358, 95, 75, 3, hunt);
		setChildren(4, summ);
		setBounds(29601, 20, 30, 0, summ);
		setBounds(4268, 80, 15, 1, summ);
		setBounds(4269, 80, 45, 2, summ);
		setBounds(358, 95, 75, 3, summ);
		setChildren(4, dung);
		setBounds(29602, 20, 30, 0, dung);
		setBounds(4268, 80, 15, 1, dung);
		setBounds(4269, 80, 45, 2, dung);
		setBounds(358, 95, 75, 3, dung);
	}

	private static void optionTab() {
		RSInterface tab = addTabInterface(904);
		RSInterface energy = interfaceCache[149];
		energy.disabledColor = 0xff9933;
		addSprite(905, 300);
		addSprite(907, 301);
		addSprite(909, 302);
		addSprite(951, 303);
		addSprite(953, 304);
		addSprite(955, 305);
		addSprite(947, 335);
		addSprite(949, 306);
		addSprite(950, 496);
		// run button here
		addConfigButton(152, 904, 307, 308, 40, 40, "Toggle-run", 1, 5, 173);

		addConfigButton(25841, 904, 307, 308, 40, 40, "Settings", 1, 5, 175);

		addConfigButton(906, 904, 309, 313, 32, 16, "Brightness - Dark", 1, 5, 166);
		addConfigButton(908, 904, 310, 314, 32, 16, "Brightness - Normal", 2, 5, 166);
		addConfigButton(910, 904, 311, 315, 32, 16, "Brightness - Bright", 3, 5, 166);
		addConfigButton(912, 904, 312, 316, 32, 16, "Brightness - Very Bright", 4, 5, 166);

		addConfigButton(930, 904, 317, 322, 26, 16, "Music Off", 4, 5, 168);
		addConfigButton(931, 904, 318, 323, 26, 16, "Music Level-1", 3, 5, 168);
		addConfigButton(932, 904, 319, 324, 26, 16, "Music Level-2", 2, 5, 168);
		addConfigButton(933, 904, 320, 325, 26, 16, "Music Level-3", 1, 5, 168);
		addConfigButton(934, 904, 321, 326, 24, 16, "Music Level-4", 0, 5, 168);

		addConfigButton(941, 904, 317, 322, 26, 16, "Sound Effects Off", 4, 5, 169);
		addConfigButton(942, 904, 318, 323, 26, 16, "Sound Effects Level-1", 3, 5, 169);
		addConfigButton(943, 904, 319, 324, 26, 16, "Sound Effects Level-2", 2, 5, 169);
		addConfigButton(944, 904, 320, 325, 26, 16, "Sound Effects Level-3", 1, 5, 169);
		addConfigButton(945, 904, 321, 326, 24, 16, "Sound Effects Level-4", 0, 5, 169);
		addTooltip(25843, "More client options,\\nincluding fullscreen");

		addConfigButton(913, 904, 307, 308, 40, 40, "Toggle-Mouse Buttons", 0, 5, 170);
		addConfigButton(915, 904, 307, 308, 40, 40, "Toggle-Chat Effects", 0, 5, 171);
		addConfigButton(957, 904, 307, 308, 40, 40, "Toggle-Split Private Chat", 1, 5, 287);
		addConfigButton(12464, 904, 307, 308, 40, 40, "Toggle-Accept Aid", 0, 5, 427);
		tab.totalChildren(30);
		int x = 0;
		int y = 2;
		tab.child(0, 905, 13 + x, 10 + y);
		tab.child(1, 906, 48 + x, 18 + y);
		tab.child(2, 908, 80 + x, 18 + y);
		tab.child(3, 910, 112 + x, 18 + y);
		tab.child(4, 912, 144 + x, 18 + y);
		tab.child(5, 907, 14 + x, 55 + y);
		tab.child(6, 930, 49 + x, 61 + y);
		tab.child(7, 931, 75 + x, 61 + y);
		tab.child(8, 932, 101 + x, 61 + y);
		tab.child(9, 933, 127 + x, 61 + y);
		tab.child(10, 934, 151 + x, 61 + y);
		tab.child(11, 909, 13 + x, 99 + y);
		tab.child(12, 941, 49 + x, 104 + y);
		tab.child(13, 942, 75 + x, 104 + y);
		tab.child(14, 943, 101 + x, 104 + y);
		tab.child(15, 944, 127 + x, 104 + y);
		tab.child(16, 945, 151 + x, 104 + y);
		tab.child(17, 913, 15, 153);
		tab.child(18, 955, 19, 159);
		tab.child(19, 915, 75, 153);
		tab.child(20, 953, 79, 160);
		tab.child(21, 957, 135, 153);
		tab.child(22, 951, 139, 159);
		tab.child(23, 12464, 15, 208);
		tab.child(24, 949, 20, 213);
		tab.child(25, 152, 75, 208);
		tab.child(26, 947, 87, 212);
		tab.child(27, 149, 80, 231);
		tab.child(28, 25841, 135, 208);
		tab.child(29, 950, 140, 213);
	}

	private static void sidebarInterfaces() {
		Sidebar0a(1698, 1701, 7499, "Chop", "Hack", "Smash", "Block", 42, 75, 127, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(2276, 2279, 7574, "Stab", "Lunge", "Slash", "Block", 43, 75, 124, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(2423, 2426, 7599, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(3796, 3799, 7624, "Pound", "Pummel", "Spike", "Block", 39, 75, 121, 75, 41, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(4679, 4682, 7674, "Lunge", "Swipe", "Pound", "Block", 40, 75, 124, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(4705, 4708, 7699, "Chop", "Slash", "Smash", "Block", 42, 75, 125, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(5570, 5573, 7724, "Spike", "Impale", "Smash", "Block", 41, 75, 123, 75, 39, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0a(7762, 7765, 7800, "Chop", "Slash", "Lunge", "Block", 42, 75, 125, 75, 40, 128, 125, 128, 122, 103, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0b(776, 779, "Reap", "Chop", "Jab", "Block", 42, 75, 126, 75, 46, 128, 125, 128, 122, 103, 122, 50, 40, 103, 40, 50, tda);
		Sidebar0c(425, 428, 7474, "Pound", "Pummel", "Block", 39, 75, 121, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(1749, 1752, 7524, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(1764, 1767, 7549, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(4446, 4449, 7649, "Accurate", "Rapid", "Longrange", 33, 75, 125, 75, 29, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(5855, 5857, 7749, "Punch", "Kick", "Block", 40, 75, 129, 75, 42, 128, 40, 50, 122, 50, 40, 103, tda);
		Sidebar0c(6103, 6132, 6117, "Bash", "Pound", "Block", 43, 75, 124, 75, 42, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(8460, 8463, 8493, "Jab", "Swipe", "Fend", 46, 75, 124, 75, 43, 128, 40, 103, 40, 50, 122, 50, tda);
		Sidebar0c(12290, 12293, 12323, "Flick", "Lash", "Deflect", 44, 75, 127, 75, 36, 128, 40, 50, 40, 103, 122, 50, tda);
		Sidebar0d(328, 331, 7474, "Bash", "Pound", "Focus", 42, 66, 39, 101, 41, 136, 40, 120, 40, 50, 40, 85, tda);

		RSInterface rsi = addInterface(19300);
		/* textSize(ID, wid, Size); */
		textSize(3983, tda, 0);
		/* addToggleButton(id, sprite, config, width, height, wid); */
		addToggleButton(150, 64, 172, 150, 44, "Auto Retaliate");

		rsi.totalChildren(2, 2, 2);
		rsi.child(0, 3983, 52, 25); // combat level
		rsi.child(1, 150, 21, 153); // auto retaliate

		rsi = interfaceCache[3983];
		rsi.centerText = true;
		rsi.disabledColor = 0xff981f;
	}

	private static void curseTabInterface() {
		RSInterface Interface = addTabInterface(32500);
		int index = 0;
		addSpriteLoader(688, 871);
		//addTooltip(19021, "This is the effect that prayers\nand curses have during combat.\nIt includes curses that have\nbeen used against you. The\nadjustment has no effect\noutside of combat. The\npercentage shown is relative to\n your skill level, and may vary\ndepending on the enemy you are\nfighting, and the prayers or\n curses used. Partial\npercentages are not shown.");
		addSpriteLoader(689, 872);
		addText(19025, "  @red@Mystic Prayers", 0xEED30F, false, true, 52, tda, 0);
		addText(690, "690", 0xAF70C3, false, false, -1, tda, 0);
		addText(691, "691", 0xAF70C3, false, false, -1, tda, 0);
		addText(692, "692", 0xAF70C3, false, false, -1, tda, 0);
		addText(693, "693", 0xAF70C3, false, false, -1, tda, 0);
		addText(694, "694", 0xAF70C3, false, false, -1, tda, 0);
		addText(687, "99/99", 0xAF70C3, false, false, -1, tda, 1);
		addSpriteLoader(32502, 870);
		addCurseWithTooltip(32503, 0, 610, 1, 1, "Gaia's Blessing", 32582);
		addCurseWithTooltip(32505, 0, 611, 30, 2, "Serenity", 32544);
		addCurseWithTooltip(32507, 0, 612, 55, 3, "Rockheart", 32546);
		addCurseWithTooltip(32509, 0, 613, 85, 4, "Geovigor", 32548);
		addCurseWithTooltip(32511, 0, 614, 119, 5, "Stonehaven", 32550);
		addCurseWithTooltip(32513, 0, 615, 1, 6, "Ebb & Flow", 32552);
		addCurseWithTooltip(32515, 0, 616, 30, 7, "Aquastrike", 32554);
		addCurseWithTooltip(32517, 0, 617, 55, 8, "Riptide", 32556);
		addCurseWithTooltip(32519, 0, 618, 85, 9, "SeaSlicer", 32558);
		addCurseWithTooltip(32521, 0, 619, 119, 10, "Swifttide", 32560);
		addCurseWithTooltip(32523, 0, 620, 1, 11, "Cinder's Touch", 32562);
		addCurseWithTooltip(32525, 0, 621, 30, 12, "Emberblast", 32564);
		addCurseWithTooltip(32527, 0, 622, 55, 13, "Infernify", 32566);
		addCurseWithTooltip(32529, 0, 623, 85, 14, "Blazeup", 32568);
		addCurseWithTooltip(32531, 0, 624, 119, 15, "Inferno", 32570);
		addCurseWithTooltip(32533, 0, 625, 1, 16, "Protect Melee", 32572);
		addCurseWithTooltip(32535, 0, 626, 1, 17, "Protect Range", 32574);
		addCurseWithTooltip(32537, 0, 627, 1, 18, "Protect Magic", 32576);
		addCurseWithTooltip(32539, 0, 628, 119, 19, "Malevolence", 32578);
		addCurseWithTooltip(32541, 0, 629, 119, 20, "Desolation", 32580);
		addTooltip(32582, "Level 1\nGaia's Blessing\nChannel the power of the earth.\nProvides healing on damage dealt.");
		addTooltip(32544, "Level 30\nSerenity\nChance to full heal hitpoints.");
		addTooltip(32546, "Level 55\nRockheart\nIncreases base healing.");
		addTooltip(32548, "Level 85\nGeovigor\nBoosts Droprate by 2%");
		addTooltip(32550, "Level 120\nStonehaven\nCombines all Earth prayers.");
		addTooltip(32552, "Level 1\nEbb & Flow\nRecoils damage taken.\nChance to recoil AOE!");
		addTooltip(32554, "Level 30\nAquastrike\nChance to crit with AOE.");
		addTooltip(32556, "Level 55\nRiptide \nIncreases weapon speed by one tick.");
		addTooltip(32558, "Level 85\nSeaSlicer\nBoosts Critical Chance by 2%");
		addTooltip(32560, "Level 120\nSwifttide\nCombines all Water prayers.");
		addTooltip(32562, "Level 1\nCinder's Touch\nApplies burn damage, DOT.");
		addTooltip(32564, "Level 30\nEmberblast\nChance for Exploding AOE damage.\nDeals % health damage.");
		addTooltip(32566, "Level 55\nInfernify\nIncreases Burn stacks\nMore damage from Emberblast");
		addTooltip(32568, "Level 85\nBlazeup\nBoosts Maxhit by 5%");
		addTooltip(32570, "Level 120\nInferno\nWatch the world burn!\nCombines all Fire prayers");
		addTooltip(32572, "Level 1\nProtect Melee\nBlocks incoming Melee attacks!");
		addTooltip(32574, "Level 1\nProtect Range\nBlocks incoming Range attacks!");
		addTooltip(32576, "Level 1\nProtect Magic\nBlocks incoming Mage attacks!");
		addTooltip(32578, "Level 120\nMalevolence\n6% Dr/Crit/Damage");
		addTooltip(32580, "Level 120\nDesolation\nDestroy everything in your path.\nNothing remains.");
		setChildren(70, Interface);
		/*curse start*/
		setBounds(689, 0, 217, index, Interface);
		index++;
		//setBounds(701, 0, 217, index, Interface);index++;
		setBounds(687, 85, 241, index, Interface);
		index++;
		setBounds(688, 0, 170, index, Interface);
		index++;
		setBounds(690, 12, 200, index, Interface);
		index++;
		setBounds(691, 51, 200, index, Interface);
		index++;
		setBounds(692, 89, 200, index, Interface);
		index++;
		setBounds(693, 127, 200, index, Interface);
		index++;
		setBounds(694, 167, 200, index, Interface);
		index++;
		setBounds(19025, 47, 220, index, Interface);
		index++;
		//setBounds(19030, 47, 219, index, Interface);index++;

		setBounds(32502, 65, 241, index, Interface);
		index++;


		setBounds(32503, 6, 7, index, Interface);
		index++;
		setBounds(32504, 8, 8, index, Interface);
		index++;
		setBounds(32505, 40, 5, index, Interface);
		index++;
		setBounds(32506, 45, 8, index, Interface);
		index++;
		setBounds(32507, 80, 8, index, Interface);
		index++;
		setBounds(32508, 83, 8, index, Interface);
		index++;
		setBounds(32509, 116, 8, index, Interface);
		index++;
		setBounds(32510, 118, 8, index, Interface);
		index++;
		setBounds(32511, 150, 5, index, Interface);
		index++;
		setBounds(32512, 154, 8, index, Interface);
		index++;
		setBounds(32513, 6, 45, index, Interface);
		index++;
		setBounds(32514, 8, 48, index, Interface);
		index++;
		setBounds(32515, 44, 48, index, Interface);
		index++;
		setBounds(32516, 45, 48, index, Interface);
		index++;
		setBounds(32517, 80, 48, index, Interface);
		index++;
		setBounds(32518, 83, 48, index, Interface);
		index++;
		setBounds(32519, 116, 48, index, Interface);
		index++;
		setBounds(32520, 118, 48, index, Interface);
		index++;
		setBounds(32521, 151, 45, index, Interface);
		index++;
		setBounds(32522, 154, 48, index, Interface);
		index++;
		setBounds(32523, 6, 84, index, Interface);
		index++;
		setBounds(32524, 8, 86, index, Interface);
		index++;
		setBounds(32525, 44, 84, index, Interface);
		index++;
		setBounds(32526, 45, 86, index, Interface);
		index++;
		setBounds(32527, 80, 84, index, Interface);
		index++;
		setBounds(32528, 83, 86, index, Interface);
		index++;
		setBounds(32529, 116, 86, index, Interface);
		index++;
		setBounds(32530, 118, 86, index, Interface);
		index++;
		setBounds(32531, 153, 83, index, Interface);
		index++;
		setBounds(32532, 154, 86, index, Interface);
		index++;
		setBounds(32533, 6, 122, index, Interface);
		index++;
		setBounds(32534, 8, 125, index, Interface);
		index++;
		setBounds(32535, 44, 122, index, Interface);
		index++;
		setBounds(32536, 45, 125, index, Interface);
		index++;
		setBounds(32537, 80, 122, index, Interface);
		index++;
		setBounds(32538, 83, 125, index, Interface);
		index++;
		setBounds(32539, 116, 122, index, Interface);
		index++;
		setBounds(32540, 118, 125, index, Interface);
		index++;
		setBounds(32541, 153, 120, index, Interface);
		index++;
		setBounds(32542, 156, 125, index, Interface);
		index++;
		setBounds(32582, 10, 40, index, Interface);
		index++;
		setBounds(32544, 20, 40, index, Interface);
		index++;
		setBounds(32546, 20, 40, index, Interface);
		index++;
		setBounds(32548, 20, 40, index, Interface);
		index++;
		setBounds(32550, 20, 40, index, Interface);
		index++;
		setBounds(32552, 10, 80, index, Interface);
		index++;
		setBounds(32554, 10, 80, index, Interface);
		index++;
		setBounds(32556, 10, 80, index, Interface);
		index++;
		setBounds(32558, 10, 80, index, Interface);
		index++;
		setBounds(32560, 10, 80, index, Interface);
		index++;
		setBounds(32562, 10, 120, index, Interface);
		index++;
		setBounds(32564, 10, 120, index, Interface);
		index++;
		setBounds(32566, 10, 120, index, Interface);
		index++;
		setBounds(32568, 5, 120, index, Interface);
		index++;
		setBounds(32570, 5, 120, index, Interface);
		index++;
		setBounds(32572, 10, 160, index, Interface);
		index++;
		setBounds(32574, 10, 160, index, Interface);
		index++;
		setBounds(32576, 10, 160, index, Interface);
		index++;
		setBounds(32578, 10, 160, index, Interface);
		index++;
		setBounds(32580, 10, 160, index, Interface);
		index++;
	}

	private static void configureLunar() {
		constructLunar();
		drawRune(30003, 208);
		drawRune(30004, 209);
		drawRune(30005, 210);
		drawRune(30006, 211);
		drawRune(30007, 212);
		drawRune(30008, 213);
		drawRune(30009, 214);
		drawRune(30010, 215);
		drawRune(30011, 216);
		drawRune(30012, 217);
		drawRune(30013, 218);
		drawRune(30014, 219);
		drawRune(30015, 220);
		drawRune(30016, 221);
		addLunar3RunesSmallBox(30017, 9075, 554, 555, 0, 4, 3, 30003, 30004, 64, "Pie", "Bake pies without a stove", tda, 222, 16, 2);
		addLunar2RunesSmallBox(30025, 9075, 557, 0, 7, 30006, 65, "Cure Aids", "Cure disease on farming patch", tda, 223, 4, 2);
		addLunar3RunesBigBox(30032, 9075, 564, 558, 0, 0, 0, 30013, 30007, 65, "Monster Examine", "Detect the combat statistics of a\\nmonster", tda, 224, 2, 2);
		addLunar3RunesSmallBox(30040, 9075, 564, 556, 0, 0, 1, 30013, 30005, 66, "Npc Contact", "Speak with varied NPCs", tda, 225, 0, 2);
		addLunar3RunesSmallBox(30048, 9075, 563, 557, 0, 0, 9, 30012, 30006, 67, "Cure Other", "Cure poisoned players", tda, 226, 8, 2);
		addLunar3RunesSmallBox(30056, 9075, 555, 554, 0, 2, 0, 30004, 30003, 67, "Humidify", "fills certain vessels with water", tda, 227, 0, 5);
		addLunar3RunesSmallBox(30064, 9075, 563, 557, 1, 0, 1, 30012, 30006, 68, "Training Teleport", "Teleport to training areas", tda, 228, 0, 5);
		addLunar3RunesBigBox(30075, 9075, 563, 557, 1, 0, 3, 30012, 30006, 69, "Bosses Teleport", "Teleports to various bosses", tda, 229, 0, 5);
		addLunar3RunesSmallBox(30083, 9075, 563, 557, 1, 0, 5, 30012, 30006, 70, "Skills Teleport", "Teleports to skilling areas", tda, 230, 0, 5);
		addLunar3RunesSmallBox(30091, 9075, 564, 563, 1, 1, 0, 30013, 30012, 70, "Cure Me", "Cures Poison", tda, 231, 0, 5);
		addLunar2RunesSmallBox(30099, 9075, 557, 1, 1, 30006, 70, "Beast Hunter Kit", "Get a kit of hunting gear", tda, 232, 0, 5);
		addLunar3RunesSmallBox(30106, 9075, 563, 555, 1, 0, 0, 30012, 30004, 71, "Mini-Games Teleport", "Teleport to fun Mini-Games", tda, 233, 0, 5);
		addLunar3RunesBigBox(30114, 9075, 563, 555, 1, 0, 4, 30012, 30004, 72, "Pking Teleport", "Teleports to pking areas", tda, 234, 0, 5);
		addLunar3RunesSmallBox(30122, 9075, 564, 563, 1, 1, 1, 30013, 30012, 73, "Cure Group", "Cures Poison on players", tda, 235, 0, 5);
		addLunar3RunesBigBox(30130, 9075, 564, 559, 1, 1, 4, 30013, 30008, 74, "Stat Spy", "Cast on another player to see their\\nskill levels", tda, 236, 8, 2);
		addLunar3RunesBigBox(30138, 9075, 563, 554, 1, 1, 2, 30012, 30003, 74, "Skill teleport", "", tda, 237, 0, 5);
		addLunar3RunesBigBox(30146, 9075, 563, 554, 1, 1, 5, 30012, 30003, 75, "Tele Group Barbarian", "Teleports players to the Barbarian\\noutpost", tda, 238, 0, 5);
		addLunar3RunesSmallBox(30154, 9075, 554, 556, 1, 5, 9, 30003, 30005, 76, "Superglass Make", "Make glass without a furnace", tda, 239, 16, 2);
		addLunar3RunesSmallBox(30162, 9075, 563, 555, 1, 1, 3, 30012, 30004, 77, "City teleport", "", tda, 240, 0, 5);
		addLunar3RunesSmallBox(30170, 9075, 563, 555, 1, 1, 7, 30012, 30004, 78, "Tele Group Khazard", "Teleports players to Port khazard", tda, 241, 0, 5);
		addLunar3RunesBigBox(30178, 9075, 564, 559, 1, 0, 4, 30013, 30008, 78, "Dream", "Take a rest and restore hitpoints 3\\n times faster", tda, 242, 0, 5);
		addLunar3RunesSmallBox(30186, 9075, 557, 555, 1, 9, 4, 30006, 30004, 79, "String Jewellery", "String amulets without wool", tda, 243, 0, 5);
		addLunar3RunesLargeBox(30194, 9075, 557, 555, 1, 9, 9, 30006, 30004, 80, "Stat Restore Pot\\nShare", "Share a potion with up to 4 nearby\\nplayers", tda, 244, 0, 5);
		addLunar3RunesSmallBox(30202, 9075, 554, 555, 1, 6, 6, 30003, 30004, 81, "Magic Imbue", "Combine runes without a talisman", tda, 245, 0, 5);
		addLunar3RunesBigBox(30210, 9075, 561, 557, 2, 1, 14, 30010, 30006, 82, "Fertile Soil", "Fertilise a farming patch with super\\ncompost", tda, 246, 4, 2);
		addLunar3RunesBigBox(30218, 9075, 557, 555, 2, 11, 9, 30006, 30004, 83, "Boost Potion Share", "Shares a potion with up to 4 nearby\\nplayers", tda, 247, 0, 5);
		addLunar3RunesSmallBox(30226, 9075, 563, 555, 2, 2, 9, 30012, 30004, 84, "Fishing Guild Teleport", "Teleports you to the fishing guild", tda, 248, 0, 5);
		addLunar3RunesLargeBox(30234, 9075, 563, 555, 1, 2, 13, 30012, 30004, 85, "Tele Group Fishing\\nGuild", "Teleports players to the Fishing\\nGuild", tda, 249, 0, 5);
		addLunar3RunesSmallBox(30242, 9075, 557, 561, 2, 14, 0, 30006, 30010, 85, "Plank Make", "Turn Logs into planks", tda, 250, 16, 5);
		/******** Cut Off Limit **********/
		addLunar3RunesSmallBox(30250, 9075, 563, 555, 2, 2, 9, 30012, 30004, 86, "Catherby Teleport", "Teleports you to Catherby", tda, 251, 0, 5);
		addLunar3RunesSmallBox(30258, 9075, 563, 555, 2, 2, 14, 30012, 30004, 87, "Tele Group Catherby", "Teleports players to Catherby", tda, 252, 0, 5);
		addLunar3RunesSmallBox(30266, 9075, 563, 555, 2, 2, 7, 30012, 30004, 88, "Ice Plateau Teleport", "Teleports you to Ice Plateau", tda, 253, 0, 5);
		addLunar3RunesBigBox(30274, 9075, 563, 555, 2, 2, 15, 30012, 30004, 89, "Tele Group Ice Plateau", "Teleports players to Ice Plateau", tda, 254, 0, 5);
		addLunar3RunesBigBox(30282, 9075, 563, 561, 2, 1, 0, 30012, 30010, 90, "Energy Transfer", "Spend HP and SA energy to\\n give another SA and run energy", tda, 255, 8, 2);
		addLunar3RunesBigBox(30290, 9075, 563, 565, 2, 2, 0, 30012, 30014, 91, "Heal Other", "Transfer up to 75% of hitpoints\\n to another player", tda, 256, 8, 2);
		addLunar3RunesBigBox(30298, 9075, 560, 557, 2, 1, 9, 30009, 30006, 92, "Vengeance Other", "Allows another player to rebound\\ndamage to an opponent", tda, 257, 8, 2);
		addLunar3RunesSmallBox(30306, 9075, 560, 557, 3, 1, 9, 30009, 30006, 93, "Vengeance", "Rebound damage to an opponent", tda, 258, 0, 5);
		addLunar3RunesBigBox(30314, 9075, 565, 563, 3, 2, 5, 30014, 30012, 94, "Heal Group", "Transfer up to 75% of hitpoints to a group", tda, 259, 0, 5);
		addLunar3RunesBigBox(30322, 9075, 564, 563, 2, 1, 0, 30013, 30012, 95, "Spellbook Swap", "Change to another spellbook for 1\\nspell cast", tda, 260, 0, 5);
	}

	private static void quickPrayersInterface() {
		int frame = 0;
		RSInterface tab = addTabInterface(17200);
		addSpriteLoader(17201, 1188);
		addText(17230, "Select your quick prayers:", tda, 0, 0xff981f, false, true);
		addTransparentSpriteWSpriteLoader(17229, 1189, 50);
		int i = 17202;
		for (int j = 630; j <= 659; j++) {

			addConfigButtonWSpriteLoader(i, 17200, 1185, 1184, 14, 15, "Select", 0, 1, j);
			i += i == 17229 ? 50 : 1;
		}

		addHoverButtonWSpriteLoader(17231, 1186, 190, 24, "Confirm Selection", -1, 17232, 1);
		addHoveredImageWSpriteLoader(17232, 1187, 190, 24, 17233);

		setChildren(62, tab);
		setBounds(25001, 5, 28, frame++, tab);
		setBounds(25003, 44, 28, frame++, tab);
		setBounds(25005, 79, 31, frame++, tab);
		setBounds(25007, 116, 30, frame++, tab);
		setBounds(25009, 153, 29, frame++, tab);
		setBounds(25011, 5, 68, frame++, tab);
		setBounds(25013, 44, 67, frame++, tab);
		setBounds(25015, 79, 69, frame++, tab);
		setBounds(25017, 116, 70, frame++, tab);
		setBounds(25019, 154, 70, frame++, tab);
		setBounds(25021, 4, 104, frame++, tab);
		setBounds(25023, 44, 107, frame++, tab);
		setBounds(25025, 81, 105, frame++, tab);
		setBounds(25027, 117, 105, frame++, tab);
		setBounds(25029, 156, 107, frame++, tab);
		setBounds(25031, 5, 145, frame++, tab);
		setBounds(25033, 43, 144, frame++, tab);
		setBounds(25035, 83, 144, frame++, tab);
		setBounds(25037, 115, 141, frame++, tab);
		setBounds(25039, 154, 144, frame++, tab);
		setBounds(25041, 5, 180, frame++, tab);
		setBounds(25043, 41, 178, frame++, tab);
		setBounds(25045, 79, 183, frame++, tab);
		setBounds(25047, 116, 178, frame++, tab);
		setBounds(25049, 161, 180, frame++, tab);
		setBounds(25051, 4, 219, frame++, tab);
		setBounds(25105, 44, 214, frame++, tab);
		setBounds(25109, 80, 214, frame++, tab);
		setBounds(17229, 0, 25, frame++, tab);
		setBounds(17201, 0, 22, frame++, tab);
		setBounds(17201, 0, 237, frame++, tab);
		setBounds(17202, 2, 25, frame++, tab);
		setBounds(17203, 41, 25, frame++, tab);
		setBounds(17204, 76, 25, frame++, tab);
		setBounds(17205, 113, 25, frame++, tab);
		setBounds(17206, 150, 25, frame++, tab);
		setBounds(17207, 2, 65, frame++, tab);
		setBounds(17208, 41, 65, frame++, tab);
		setBounds(17209, 76, 65, frame++, tab);
		setBounds(17210, 113, 65, frame++, tab);
		setBounds(17211, 150, 65, frame++, tab);
		setBounds(17212, 2, 102, frame++, tab);
		setBounds(17213, 41, 102, frame++, tab);
		setBounds(17214, 76, 102, frame++, tab);
		setBounds(17215, 113, 102, frame++, tab);
		setBounds(17216, 150, 102, frame++, tab);
		setBounds(17217, 2, 141, frame++, tab);
		setBounds(17218, 41, 141, frame++, tab);
		setBounds(17219, 76, 141, frame++, tab);
		setBounds(17220, 113, 141, frame++, tab);
		setBounds(17221, 150, 141, frame++, tab);
		setBounds(17222, 2, 177, frame++, tab);
		setBounds(17223, 41, 177, frame++, tab);
		setBounds(17224, 76, 177, frame++, tab);
		setBounds(17225, 113, 177, frame++, tab);
		setBounds(17226, 150, 177, frame++, tab);
		setBounds(17227, 1, 211, frame++, tab);
		setBounds(17230, 5, 5, frame++, tab);
		setBounds(17231, 0, 237, frame++, tab);
		setBounds(17232, 0, 237, frame++, tab);
		setBounds(17279, 41, 211, frame++, tab);
		setBounds(17280, 76, 211, frame++, tab);
	}

	private static void quickCursesInterface() {
		int frame = 0;
		RSInterface tab = addTabInterface(17234);
		addText(17235, "Select Quick Prayers:", tda, 0, 0xff981f, false, true);
		int i = 17202;
		for (int j = 630; i <= 17222 || j <= 656; j++) {
			addConfigButtonWSpriteLoader(i, 17200, 1185, 1184, 14, 15, "Select", 0, 1, j);
			i++;
		}

		addHoverButtonWSpriteLoader(17231, 1186, 190, 24, "Confirm Selection", -1, 17232, 1);
		addHoveredImageWSpriteLoader(17232, 1187, 190, 24, 17233);


		setChildren(46, tab);
		setBounds(32504, 5, 8 + 17, frame++, tab);
		setBounds(32506, 44, 8 + 20, frame++, tab);
		setBounds(32508, 79, 11 + 19, frame++, tab);
		setBounds(32510, 116, 10 + 18, frame++, tab);
		setBounds(32512, 153, 9 + 20, frame++, tab);
		setBounds(32514, 5, 48 + 18, frame++, tab);
		setBounds(32516, 44, 47 + 21, frame++, tab);
		setBounds(32518, 79, 49 + 20, frame++, tab);
		setBounds(32520, 116, 50 + 19, frame++, tab);
		setBounds(32522, 154, 50 + 20, frame++, tab);
		setBounds(32524, 4, 84 + 21, frame++, tab);
		setBounds(32526, 44, 87 + 19, frame++, tab);
		setBounds(32528, 81, 85 + 20, frame++, tab);
		setBounds(32530, 117, 85 + 20, frame++, tab);
		setBounds(32532, 156, 87 + 18, frame++, tab);
		setBounds(32534, 5, 125 + 19, frame++, tab);
		setBounds(32536, 43, 124 + 19, frame++, tab);
		setBounds(32538, 83, 124 + 20, frame++, tab);
		setBounds(32540, 115, 125 + 21, frame++, tab);
		setBounds(32542, 154, 126 + 22, frame++, tab);
		setBounds(17229, 0, 25, frame++, tab);
		setBounds(17201, 0, 22, frame++, tab);
		setBounds(17201, 0, 237, frame++, tab);
		setBounds(17202, 2, 25, frame++, tab);
		setBounds(17203, 41, 25, frame++, tab);
		setBounds(17204, 76, 25, frame++, tab);
		setBounds(17205, 113, 25, frame++, tab);
		setBounds(17206, 150, 25, frame++, tab);
		setBounds(17207, 2, 65, frame++, tab);
		setBounds(17208, 41, 65, frame++, tab);
		setBounds(17209, 76, 65, frame++, tab);
		setBounds(17210, 113, 65, frame++, tab);
		setBounds(17211, 150, 65, frame++, tab);
		setBounds(17212, 2, 102, frame++, tab);
		setBounds(17213, 41, 102, frame++, tab);
		setBounds(17214, 76, 102, frame++, tab);
		setBounds(17215, 113, 102, frame++, tab);
		setBounds(17216, 150, 102, frame++, tab);
		setBounds(17217, 2, 141, frame++, tab);
		setBounds(17218, 41, 141, frame++, tab);
		setBounds(17219, 76, 141, frame++, tab);
		setBounds(17220, 113, 141, frame++, tab);
		setBounds(17221, 150, 141, frame++, tab);
		setBounds(17235, 5, 5, frame++, tab);
		setBounds(17231, 0, 237, frame++, tab);
		setBounds(17232, 0, 237, frame++, tab);
	}

	public static void afkCheckerInterface() {
		int interID = 116000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 130;
		int y = 70;
		tab.totalChildren(7);

		addSpriteLoader(id, 2025);
		tab.child(c++, id++, 0 + x, 0 + y);

		addText(id, "AFK Checker", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 126 + x, 4 + y);

		addText(id, "Question", tda, 1, 0xcfa75a, true, true);
		tab.child(c++, id++, 126 + x, 29 + y);

		addText(id, "Heres whats up", tda, 1, 0xFF981F, true, true);
		tab.child(c++, id++, 126 + x, 80 + y);

		hoverButton(id, 1307, 1308, "Answer", 2, 0xff8624, "Answer");
		tab.child(c++, id++, 15 + x, 144 + y);

		hoverButton(id, 1307, 1308, "Answer", 2, 0xff8624, "Answer");
		tab.child(c++, id++, 90 + x, 144 + y);

		hoverButton(id, 1307, 1308, "Answer", 2, 0xff8624, "Answer");
		tab.child(c++, id++, 165 + x, 144 + y);

	}


	public static void afkInfoInterface() {
		int interID = 83500;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 130;
		int y = 70;
		tab.totalChildren(7);

		addSpriteLoader(id, 2026);
		tab.child(c++, id++, 0 + x, 0 + y);

		addText(id, "AFK Alert", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 126 + x, 4 + y);

		addText(id, "Information", tda, 1, 0xcfa75a, true, true);
		tab.child(c++, id++, 126 + x, 29 + y);

		addText(id, "You were AFK in combat\\n for over 15 minutes", tda, 1, 0xcfa75a, true, true);
		tab.child(c++, id++, 126 + x, 50 + y);

		addText(id, "Answered", tda, 1, 0xcfa75a, true, true);
		tab.child(c++, id++, 126 + x, 90 + y);

		addText(id, "Total offenses: ", tda, 1, 0xcfa75a, true, true);
		tab.child(c++, id++, 126 + x, 110 + y);

		hoverButton(id, 1457, 1458, "I Understand", 2, 0xff8624, "I Understand");
		tab.child(c++, id++, 67 + x, 148 + y);

	}


	static void achievements() {
		int interID = 117000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 35;
		int y = 25;
		tab.totalChildren(18);

		addSpriteLoader(id, 1540);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 414 + x, 9 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 414 + x, 9 + y);
		id++;

		addConfigButtonWSpriteLoader(id, interID, 1548, 1547, 31, 27, "General", 0, 5, 5332);
		tab.child(c++, id++, 40 + x, 38 + y);
		addConfigButtonWSpriteLoader(id, interID, 1548, 1547, 31, 27, "Slayer", 1, 5, 5332);
		tab.child(c++, id++, 70 + x, 38 + y);
		addConfigButtonWSpriteLoader(id, interID, 1548, 1547, 31, 27, "Looting", 2, 5, 5332);
		tab.child(c++, id++, 100 + x, 38 + y);
		addText(id, "", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, x, y);

		addSpriteLoader(id, 3430);
		tab.child(c++, id++, 39 + x, 35 + y);
		addSpriteLoader(id, 3429);
		tab.child(c++, id++, 69 + x, 35 + y);
		addSpriteLoader(id, 3427);
		tab.child(c++, id++, 99 + x, 35 + y);

		addText(id, "", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, x, y);

		addText(id, "Achievements completed: 54/59", tda, 0, 0XAF70C3, false, true);
		tab.child(c++, id++, 267 + x, 45 + y);

		addConfigButtonWSpriteLoader(id, interID, 1548, 1547, 31, 27, "Minigames", 3, 5, 5332);
		tab.child(c++, id++, 130 + x, 38 + y);
		addSpriteLoader(id, 3428);
		tab.child(c++, id++, 129 + x, 30 + y);

		addConfigButtonWSpriteLoader(id, interID, 1548, 1547, 31, 27, "Skilling", 4, 5, 5332);
		tab.child(c++, id++, 160 + x, 38 + y);
		addSpriteLoader(id, 3431);
		tab.child(c++, id++, 159 + x, 35 + y);

		addHoverText(117909, "Claim all", "Claim all", tda, 0, ColorConstants.YELLOW, true, false, 75, 10);

		tab.child(c++, 117909, 215, 70);

		interID = 117107;


		RSInterface scroll = addInterface(interID);
		tab.child(c++, interID, 47,90);
		scroll.totalChildren(800);
		scroll.width = 416 - 16;
		scroll.height = 200;
		scroll.scrollMax = 1200;
		y = 0;
		x = 0;


		for (int i = 0; i < 100; i++) {
			addSpriteLoader(117108+i, 1541 + (i % 2));
			scroll.child(i, 117108+i, x, y);

			addSpriteLoader(117208+i, 624 + i);
			scroll.child(i+100, 117208+i, 9 + x, 7 + y);

			addText(117308+i, "Would a wood chuck chuck wood?", tda, 0, 0XAF70C3, true, true);
			scroll.child(i+200, 117308+i, 126 + x, 9 + y);

			new ProgressBar(117408+i, 161, 7, new int[]{0xB472C4}, true, false, "", new int[]{0x6D4677});
			scroll.child(i+300, 117408+i, 46 + x, 26 + y);

			addText(117508+i, "45/100", tda, 0, 0xFFFFFF, true, true);
			scroll.child(i+400, 117508+i, 126 + x, 24 + y);

			hoverButton(117608+i, 1545, 1546, "Select");
			scroll.child(i+500, 117608+i, 321 + x, 6 + y);
			hoverButton(117708+i, 1543, 1544, "Claim");
			scroll.child(600+i, 117708+i, 359 + x, 6 + y);

			dropGroup(117808+i, 3, 1, 1, 1);
			scroll.child(700+i, 117808+i, 218 + x, 3 + y);
			y += 39;
		}
	}

	static void donatorShop() {
		int interID = 118000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 45;
		int y = 45;
		tab.totalChildren(16);

		addSpriteLoader(id, 1553);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 394 + x, 9 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 394 + x, 9 + y);
		id++;

		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 0, 5, 5333);
		tab.child(c++, id++, 10 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 1, 5, 5333);
		tab.child(c++, id++, 110 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 2, 5, 5333);
		tab.child(c++, id++, 210 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 3, 5, 5333);
		tab.child(c++, id++, 310 + x, 39 + y);

		addSpriteLoader(id, 1556);
		tab.child(c++, id++, 14 + x, 41 + y);
		addSpriteLoader(id, 1557);
		tab.child(c++, id++, 114 + x, 42 + y);
		addSpriteLoader(id, 1558);
		tab.child(c++, id++, 216 + x, 41 + y);
		addSpriteLoader(id, 1559);
		tab.child(c++, id++, 314 + x, 42 + y);

		addText(id, "Weaponry", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 32 + x, 44 + y);
		addText(id, "Armoury", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 132 + x, 44 + y);
		addText(id, "Accessories", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 232 + x, 44 + y);
		addText(id, "Miscellaneous", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 332 + x, 44 + y);

		tab.child(c++, 118100, 12 + x, 65 + y);

		interID = 118100;

		RSInterface scroll = addInterface(interID);

		scroll.totalChildren(1);
		scroll.width = 395 - 16;
		scroll.height = 171;
		scroll.scrollMax = 1200;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		addToItemGroup(33900, 8, 10, 16, 12, true, new String[]{"Value", "Buy 1", "Buy 5", "Buy 10", "Buy X"});
		scroll.child(c++, 33900, 7 + x, 7 + y);

		RSInterface shopInventory = interfaceCache[33900];
		shopInventory.drawInfinity = true;
	}

	static void CosmeticShop() {
		int interID = 119000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 45;
		int y = 45;
		tab.totalChildren(16);

		addSpriteLoader(id, 1560);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 394 + x, 9 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 394 + x, 9 + y);
		id++;

		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 0, 5, 5334);
		tab.child(c++, id++, 10 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 1, 5, 5334);
		tab.child(c++, id++, 110 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 2, 5, 5334);
		tab.child(c++, id++, 210 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 3, 5, 5334);
		tab.child(c++, id++, 310 + x, 39 + y);

		addSpriteLoader(id, 3389);
		tab.child(c++, id++, 14 + x, 42 + y);
		addSpriteLoader(id, 3390);
		tab.child(c++, id++, 114 + x, 41 + y);
		addSpriteLoader(id, 3391);
		tab.child(c++, id++, 216 + x, 42 + y);
		addSpriteLoader(id, 1559);
		tab.child(c++, id++, 314 + x, 41 + y);

		addText(id, "Ordinary", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 32 + x, 44 + y);
		addText(id, "Fabled", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 132 + x, 44 + y);
		addText(id, "Exotic", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 232 + x, 44 + y);
		addText(id, "Mythic", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 332 + x, 44 + y);

		tab.child(c++, 119100, 12 + x, 65 + y);

		interID = 119100;

		RSInterface scroll = addInterface(interID);

		scroll.totalChildren(1);
		scroll.width = 395 - 16;
		scroll.height = 171;
		scroll.scrollMax = 1200;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		addToItemGroup(33901, 8, 10, 16, 12, true, new String[]{"Value", "Buy 1", "Buy 5", "Buy 10", "Buy X"});
		scroll.child(c++, 33901, 6 + x, 4 + y);

		RSInterface shopInventory = interfaceCache[33901];
		shopInventory.drawInfinity = true;

	}

	/*static void minigameInterface() {
		int interID = 79000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 35;
		int y = 25;
		tab.totalChildren(13);

		addSpriteLoader(id, 1626);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 408 + x, 9 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 408 + x, 9 + y);
		id++;

		hoverButton(id, 1572, 1573, "Start Minigame", 2, 0xff8624, "Start Minigame");
		tab.child(c++, id++, 219 + x, 167 + y);

		int xAdd = 0;
		for (int i = 0; i < 6; i++) {
			hoverButton(id + i, 1624, 1625, "Select minigame", 2, 0xff8624, "Minigame name");
			tab.child(c++, i + id++, 12 + x + xAdd, i < 3 ? 221 + y : 251 + y);
			xAdd += 137;
			if (i == 2)
				xAdd = 0;
		}

		tab.child(c++, 79050, 221 + x, 40 + y);
		tab.child(c++, 79100, 12 + x, 58 + y);
		tab.child(c++, 79200, 12 + x, 140 + y);

		interID = 79050;
		RSInterface npc = addInterface(interID);
		npc.totalChildren(1);
		npc.width = 200 - 16;
		npc.height = 121;
		npc.scrollMax = 121;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		addNpc(id, 50);
		npc.child(c++, id++, 30 + x, 4 + y);

		interID = 79100;
		RSInterface scroll = addInterface(interID);
		scroll.totalChildren(1);
		scroll.width = 201 - 16;
		scroll.height = 56;
		scroll.scrollMax = 105;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		addText(id, "This is a sample text to\\ndescribe the various minigames.", tda, 0, 0x8d8d8d, false, true);
		scroll.child(c++, id++, 2 + x, 2 + y);

		interID = 79200;
		RSInterface scroll1 = addInterface(interID);
		scroll1.totalChildren(25);
		scroll1.width = 201 - 16;
		scroll1.height = 56;
		scroll1.scrollMax = 101;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;

		for (int z = 0; z < 5; z++) {
			for (int i = 0; i < 5; i++) {
				dropGroup(id, 1, 1, 1, 1);
				scroll1.child(c++, id++, 2 + x, 2 + y);
				x += 37;
			}
			x = 0;
			y += 34;
		}
	}*/


	static void upgradeInterface() {//this one
		int interID = 121000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 45;
		int y = 45;
		tab.totalChildren(21);

		addSpriteLoader(id, 1582);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 394 + x, 9 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 394 + x, 9 + y);
		id++;

		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 0, 5, 5334);
		tab.child(c++, id++, 10 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 1, 5, 5334);
		tab.child(c++, id++, 110 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 2, 5, 5334);
		tab.child(c++, id++, 210 + x, 39 + y);
		addConfigButtonWSpriteLoader(id, interID, 1555, 1554, 99, 20, "Select", 3, 5, 5334);
		tab.child(c++, id++, 310 + x, 39 + y);

		addSpriteLoader(id, 1556);
		tab.child(c++, id++, 14 + x, 42 + y);
		addSpriteLoader(id, 1557);
		tab.child(c++, id++, 114 + x, 41 + y);
		addSpriteLoader(id, 1558);
		tab.child(c++, id++, 216 + x, 42 + y);
		addSpriteLoader(id, 1559);
		tab.child(c++, id++, 314 + x, 41 + y);

		addText(id, "Weapons", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 32 + x, 44 + y);
		addText(id, "Armor", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 132 + x, 44 + y);
		addText(id, "Accessories", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 232 + x, 44 + y);
		addText(id, "Misc", tda, 0, 0xAF70C3, false, true);
		tab.child(c++, id++, 332 + x, 44 + y);

		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 335 + x, 101 + y);

		addText(id, "Coins Needed: ", tda, 0, ColorConstants.BLACK, false, true);
		tab.child(c++, id++, 295 + x, 158 + y);
		addText(id, "Chance: ", tda, 0, ColorConstants.BLACK, false, true);
		tab.child(c++, id++, 295 + x, 181 + y);

		hoverButton(id, 1536, 1537, "Empower-All", 0, 0xff8624, "Empower-All");
		tab.child(c++, id++, 312 + x, 208 + y);

		hoverButton(id, 1536, 1537, "Empower", 0, 0xff8624, "Empower");
		tab.child(c++, id++, 312 + x, 208 + y);

		tab.child(c++, 121100, 12 + x, 98 + y);

		interID = 121100;

		RSInterface scroll = addInterface(interID);

		scroll.totalChildren(1);
		scroll.width = 273 - 16;
		scroll.height = 138;
		scroll.scrollMax = 500;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		// addToItemGroup(62209, 9, 10, 9, 7, true, new String[]{"Value", "Buy 1", "Buy 5", "Buy 10", "Buy X"});
		scroll.child(c++, 62209, 6 + x, 4 + y);

	}


	static void teleportInterface() {
		int interID = 122000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 15;
		int y = 25;
		tab.totalChildren(21);

		addSpriteLoader(id, 1583);
		tab.child(c++, id++, x, y);

		addHoverButtonWSpriteLoader(id, 1016, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 455 + x, 9 + y);
		addHoveredImageWSpriteLoader(id, 1017, 16, 16, id + 1);
		tab.child(c++, id++, 455 + x, 9 + y);
		id++;

		addConfigButtonWSpriteLoader(id, interID, 1584, 1585, 91, 20, "Select", 0, 5, 2877);
		tab.child(c++, id++, 11 + x, 38 + y);
		addConfigButtonWSpriteLoader(id, interID, 1584, 1585, 91, 20, "Select", 1, 5, 2877);
		tab.child(c++, id++, 103 + x, 38 + y);
		addConfigButtonWSpriteLoader(id, interID, 1584, 1585, 91, 20, "Select", 2, 5, 2877);
		tab.child(c++, id++, 195 + x, 38 + y);
		addConfigButtonWSpriteLoader(id, interID, 1584, 1585, 91, 20, "Select", 3, 5, 2877);
		tab.child(c++, id++, 287 + x, 38 + y);
		addConfigButtonWSpriteLoader(id, interID, 1584, 1585, 91, 20, "Select", 4, 5, 2877);
		tab.child(c++, id++, 379 + x, 38 + y);
		addText(id, "", tda, 0, 0xAF70C3, true, true);
		tab.child(c++, id++, 433 + x, 42 + y);

		addText(id, "Monsters", tda, 0, 0xAF70C3, true, true);
		tab.child(c++, id++, 56 + x, 42 + y);
		addText(id, "Bosses", tda, 0, 0xAF70C3, true, true);
		tab.child(c++, id++, 148 + x, 42 + y);
		addText(id, "Minigames", tda, 0, 0xAF70C3, true, true);
		tab.child(c++, id++, 240 + x, 42 + y);
		addText(id, "Dungeons", tda, 0, 0xAF70C3, true, true);
		tab.child(c++, id++, 332 + x, 42 + y);
		addText(id, "Misc", tda, 0, 0xAF70C3, true, true);
		tab.child(c++, id++, 424 + x, 42 + y);
		addText(id, "", tda, 0, 0xAF70C3, true, true);
		tab.child(c++, id++, 433 + x, 42 + y);

		hoverButton(id, 1536, 1537, "Teleport", 0, 0xff8624, "Teleport");
		tab.child(c++, id++, 186 + x, 255 + y);
		hoverButton(id, 1536, 1537, "Previous", 0, 0xff8624, "Previous");
		tab.child(c++, id++, 266 + x, 255 + y);

		tab.child(c++, 122050, 165 + x, 113 + y);
		tab.child(c++, 122060, 165 + x, 64 + y);
		tab.child(c++, 122300, 12 + x, 81 + y);
		tab.child(c++, 122200, 368 + x, 81 + y);


		interID = 122050;
		RSInterface npc = addInterface(interID);
		npc.totalChildren(1);
		npc.width = 196 - 16;
		npc.height = 136;
		npc.scrollMax = 136;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		addNpc(id, 50);
		npc.child(c++, id++, 30 + x, 25 + y);

		interID = 122060;
		RSInterface drops = addInterface(interID);
		drops.totalChildren(1);
		drops.width = 196 - 16;
		drops.height = 42;
		drops.scrollMax = 42;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		addToItemGroup(35500, 5, 25, 2, 3, false, new String[]{null, null, null, null, null});
		drops.child(c++, 35500, 5 + x, 5 + y);

		interID = 122200;
		RSInterface favs = addInterface(interID);
		favs.totalChildren(30);
		favs.width = 101;
		favs.height = 200;
		favs.scrollMax = 200;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		for (int i = 0; i < 10; i++) {
			addSpriteLoader(id, 1588 + (i % 2));
			favs.child(c++, id++, x, y);
			addClickableText(id, "Favorite", "Select", tda, 0, 0xFF8900, false, true, 85);
			favs.child(c++, id++, 2, y + 4);
			hoverButton(id, 1592, 1592, "Remove");
			favs.child(c++, id++, 90 + x, 5 + y);
			y += 20;
		}

		interID = 122300;
		RSInterface scroll = addInterface(interID);
		scroll.totalChildren(300);
		scroll.width = 146 - 16;
		scroll.height = 200;
		scroll.scrollMax = 500;
		y = 0;
		c = 0;
		id = interID + 1;
		x = 0;
		for (int i = 0; i < 100; i++) {
			addSpriteLoader(id, 1586 + (i % 2));
			scroll.child(c++, id++, x, y);
			addClickableText(id, "Monster", "Select", tda, 1, 0xFF8900, false, true, 110);
			scroll.child(c++, id++, 3, y + 3);
			addConfigButtonWSpriteLoader(id, interID, 1590, 1591, 15, 14, "Favorite", 0, 4, 5340 + i);
			scroll.child(c++, id++, 113 + x, 3 + y);
			y += 20;
		}

	}

	private static void slotInterface() {
		RSInterface main = addInterface(43300);
		addSpriteLoader(43301, 4922);

		addText(43302, "Athens Slots!", tda, 2, 0xff8624, true, true);
		addText(43303, "Loot Table: ", tda, 0, 0xffffff, true, true);
		addText(43304, "None", tda, 1, 0xffffff, true, true);
		addText(43305, "Possible Loot", tda, 0, 0xffffff, true, true);
		addText(43309, "@yel@SPIN", tda, 0, 0xffffff, true, true);

		addHoverButtonWSpriteLoader(43306, 5526, 90, 23, "Try Your Luck!", -1, 43307, 1);
		addHoveredImageWSpriteLoader(43307, 5527, 90, 23, 43308);

		addText(43309, "@yel@SPIN", tda, 0, 0xffffff, true, true);

		addHoverButtonWSpriteLoader(43310, 4923, 120, 31, "Test", -1, 43311, 1);
		addHoveredImageWSpriteLoader(43311, 4924, 120, 31, 43312);

		addText(43313, "Test", tda, 1, 0xffffff, true, true);

		addHoverButtonWSpriteLoader(43314, 4923, 120, 31, "Your", -1, 43315, 1);
		addHoveredImageWSpriteLoader(43315, 4924, 120, 31, 43316);

		addText(43317, "Your", tda, 1, 0xffffff, true, true);

		addHoverButtonWSpriteLoader(43318, 4923, 120, 31, "Luck", -1, 43319, 1);
		addHoveredImageWSpriteLoader(43319, 4924, 120, 31, 43320);

		addText(43321, "Luck", tda, 1, 0xffffff, true, true);
		slotItemPositions();
		addItemOnInterface(43322, 43223, new String[] { null });
		addItemOnInterface(43324, 43225, new String[] { null });
		addItemOnInterface(43326, 43227, new String[] { null });
		addItemOnInterface(43328, 43229, new String[] { null });
		addItemOnInterface(43330, 43221, new String[] { null });
		addItemOnInterface(43332, 43223, new String[] { null });

		main.totalChildren(31);
		main.child(0, 43301, 56, 2);
		main.child(1, 43400, 76, 48);
		main.child(2, 43550, 121, 48);
		main.child(3, 43700, 166, 48);
		main.child(4, 43850, 211, 48);
		main.child(5, 44000, 256, 48);
		main.child(6, 44150, 300, 48);
		main.child(7, 51452, 431, 11);
		main.child(8, 51453, 431, 11);
		main.child(9, 43302, 256, 12);
		main.child(10, 43303, 395, 52);
		main.child(11, 43304, 394, 65);
		main.child(12, 43305, 394, 95);
		main.child(13, 43306, 349, 245);
		main.child(14, 43307, 349, 245);
		main.child(15, 43309, 394, 251);
		main.child(16, 43310, 67, 288);
		main.child(17, 43311, 67, 288);
		main.child(18, 43313, 117, 296);
		main.child(19, 43314, 196, 288);
		main.child(20, 43315, 196, 288);
		main.child(21, 43317, 256, 296);
		main.child(22, 43318, 325, 288);
		main.child(23, 43319, 325, 288);
		main.child(24, 43321, 385, 296);
		main.child(25, 43322, 357, 115);
		main.child(26, 43324, 400, 115);
		main.child(27, 43326, 357, 157);
		main.child(28, 43328, 400, 157);
		main.child(29, 43330, 357, 199);
		main.child(30, 43332, 400, 199);

	}
	private static void TierUpgradingInterface() {
		RSInterface main = addInterface(26291);
		addSpriteLoader(26292, 5466);
		addText(26293, "Upgrading", 0xffffff, true, true, 52, tda, 2);

		addText(26294, "Success: @gre@100%", 0xffffff, false, true, 52, tda, 0);
		addText(26307, "Protection: @yel@0%", 0xffffff, true, true, 52, tda, 0);

		addItemOnInterface(26295, 26291, new String[] {null, null, null, null, null});
		addItemOnInterface(26296, 26291, new String[] {null, null, null, null, null});
		addItemOnInterface(26297, 26291, new String[] {null, null, null, null, null});
		addItemOnInterface(26298, 26291, new String[] {null, null, null, null, null});
		itemGroupAutoScroll(26299, 26300, 10, 1, 3, 0, 4, new String[] {null, null, null, null, null}, false, 138);

		addHoverButtonWSpriteLoader(26301, 5465, 90, 20, "Upgrade", -1, 26302, 1);
		addHoveredImageWSpriteLoader(26302, 5464, 90, 20, 26303);
		addText(26304, "Upgrade", 0xffffff, true, true, 52, tda, 3);

		addFillBar(26305, 112, 100,  76, true);
		addSpriteLoader(26306, 5467);

		main.totalChildren(16);
		main.child(0, 26292, 174, 49);
		main.child(1, 26293, 256, 59);
		main.child(2, 51452, 314, 58); //Replace with close button id
		main.child(3, 51453, 314, 58); //Replace with close button hover id
		main.child(4, 26294, 221, 93);
		main.child(5, 26305, 200, 131);
		main.child(6, 26306, 186, 115);
		main.child(7, 26295, 240, 119);
		main.child(8, 26296, 240, 167);
		main.child(9, 26297, 188, 167);
		main.child(10, 26298, 292, 167);
		main.child(11, 26299, 188, 209);
		main.child(12, 26301, 211, 256);
		main.child(13, 26302, 211, 256);
		main.child(14, 26304, 256, 257);
		main.child(15, 26307, 256, 244);

	}
	private static final AnimatedSprite ZZ_GIF = new AnimatedSprite(Signlink.getCacheDirectory() + "zzgif.gif");

	private static void teleporting() {
		RSInterface main = addInterface(6542);
		int MAIN_SPRITE_ID = 5463;
        addHoverButtonWSpriteLoader(51452, 1016, 16, 16, "Close Window", 0, 51453, 3);
        addHoveredImageWSpriteLoader(51453, 1017, 16, 16, 51454);
		addSpriteLoader(6543, MAIN_SPRITE_ID);
		addText(6544, "Athens", 0xAF70C3, true, true, 52, tda, 2);

		addText(6545, "Page 1", 0xAF70C3, true, true, 52, tda, 0);
		addText(6546, "Teleports", 0xAF70C3, true, true, 52, tda, 0);

		addHoverButtonWSpriteLoader(6673, 1111, 20, 20, "Next Page", -1, 6674, 1);
		addHoveredImageWSpriteLoader(6674, 1111, 20, 20, 6675);

		addHoverButtonWSpriteLoader(6676, 1112, 20, 20, "Prev Page", -1, 6677, 1);
		addHoveredImageWSpriteLoader(6677, 1112, 20, 20, 6678);


		addPet(6547);
		itemGroupAutoScroll(6548, 6549, 100, 1, 4, 4, 6, new String[]{null, null, null, null, null}, false, 207);
		for (int i = 0; i < 8; i++) {
			interfaceCache[6549].inv[i] = 1290;
		}

		addText(6550, "Info", 0xAF70C3, true, true, 52, tda, 0);
		addText(6551, "Info", 0xAF70C3, true, true, 52, tda, 0);
		addText(6552, "Info", 0xAF70C3, true, true, 52, tda, 0);

		addHoverButtonWSpriteLoader(6553, 5464, 90, 20, "Travel", -1, 6554, 1);
		addHoveredImageWSpriteLoader(6554, 5465, 90, 20, 6555);

		addText(6556, "Travel", 0xAF70C3, true, true, 52, tda, 0);

		addHoverButtonWSpriteLoader(6557, 5464, 90, 20, "Previous Teleport", -1, 6558, 1);
		addHoveredImageWSpriteLoader(6558, 5465, 90, 20, 6559);

		addText(6560, "Previous Tele", 0xAF70C3, true, true, 52, tda, 0);

		RSInterface categories = addInterface(6565);
		categories.width = 86;
		categories.height = 122;
		categories.scrollMax =  180;
		addRectangle(6566, 0, 0x2a251f, true, 85, 20);
		addRectangle(6567, 0, 0x322d25, true, 85, 20);
		int child = 6568;
		categories.totalChildren(16);
		int frame = 0;
		int y = 0;
		for (int i = 0; i < 8; i++) {
			categories.child(frame++, (i%2==0?6566:6567), 0, y);
			addHoverText(child++, "Category", "Select Category", tda, 0, ColorConstants.SNOW_WHITE, false, true,
					85, 15, 0xAF70C3);
			categories.child(frame++, child-1, 3, y+4);
			y+=20;
		}

		RSInterface teleports = addInterface(6600);
		teleports.width = 86;
		teleports.height = 122;
		teleports.scrollMax =  400;
		child = 6601;
		teleports.totalChildren(40);
		frame = 0;
		y = 0;
		for (int i = 0; i < 20; i++) {
			teleports.child(frame++, (i%2==0?6566:6567), 0, y);
			addHoverText(child++, "Travel", "Select Teleport", tda, 0, ColorConstants.SNOW_WHITE, false, true,
					85, 15, 0xAF70C3);//VOLA HOVER
			teleports.child(frame++, child-1, 3, y+4);
			y+=20;
		}
		int button1X = 182; // Adjust X position for Button 1
		int button1Y = 81; // Adjust Y position for Button 1

		int button2X = 100; // Adjust X position for Button 2
		int button2Y = 81; // Adjust Y position for Button 2

		main.totalChildren(25);
		main.child(0, 6543, 87, 40);
		main.child(1, 6544, 256, 50);
		main.child(2, 51452, 400, 49);
		main.child(3, 51453, 400, 49);
		main.child(4, 6545, 149, 84);
		main.child(5, 6546, 256, 84);
		main.child(6, 6547, 298, 70);
		main.child(7, 6548, 99, 238);
		main.child(8, 6550, 363, 178);
		main.child(9, 6551, 363, 193);
		main.child(10, 6552, 363, 208);
		main.child(11, 6553, 318, 233);
		main.child(12, 6554, 318, 233);
		main.child(13, 6556, 363, 238);
		main.child(14, 6557, 318, 258);
		main.child(15, 6558, 318, 258);
		main.child(16, 6560, 363, 263);
		main.child(17, 6565, 99, 100);
		main.child(18, 6600, 206, 100);
		main.child(19, 6673, button1X, button1Y);
		main.child(20, 6674, button1X, button1Y);
		main.child(21, 6676, button2X, button2Y);
		main.child(22, 6677, button2X, button2Y);
        main.child(23, 51452, 401, 49);
        main.child(24, 51453, 401, 49);
	}

	public static void waveMinigameInterface() {
		int interID = 78000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 180;
		int y = 270;
		tab.totalChildren(4);

		addTransparentSpriteWSpriteLoader1(id, 1608, 150);
		tab.child(c++, id++, 0 + x, 0 + y);

		addText(id, "Wave: 10", tda, 3, 0xFF981F, true, true);
		tab.child(c++, id++, 75 + x, 4 + y);

		addText(id, "Tokens earned:", tda, 1, 0xFF981F, false, true);
		tab.child(c++, id++, 5 + x, 18 + y);

		addText(id, "Task: Kill monsters (0/10)", tda, 1, 0xFF981F, false, true);
		tab.child(c++, id++, 5 + x, 32 + y);

	}

	public void enchanttable() {
		RSInterface main = addInterface(30900);
		addSpriteLoader(30901, 3406);

		addHoverButtonWSpriteLoader(30902, 1016, 15, 15, "Close", -1, 30903, 1);
		addHoveredImageWSpriteLoader(30903, 1017, 15, 15, 30904);

		addConfigButtonWSpriteLoader(30905, 30900, 3410, 3411, 140, 20, "Miscellaneous", 0, 5, 1091);
		addConfigButtonWSpriteLoader(30906, 30900, 3410, 3411, 140, 20, "Armoury", 1, 5, 1091);
		addConfigButtonWSpriteLoader(30907, 30900, 3410, 3411, 140, 20, "Weaponry", 2, 5, 1091);

		addSpriteLoader(30908, 3407);
		addSpriteLoader(30909, 3408);
		addSpriteLoader(30910, 3409);

		addText(30911, "Enchantment Table", 0xAF70C3, false, true, 52, tda, 2);

		addText(30912, "Weaponry", 0xAF70C3, false, true, 52, tda, 0);
		addText(30913, "Armoury", 0xAF70C3, false, true, 52, tda, 0);
		addText(30914, "Miscellaneous", 0xAF70C3, false, true, 52, tda, 0);
		addText(30915, "500k Required", 0xAF70C3, false, true, 52, tda, 0);
		addText(30916, "89% Success rate", 0xAF70C3, false, true, 52, tda, 0);
		addText(30917, "Selection", 0xAF70C3, false, true, 52, tda, 2);

		addText(30930, "", 0xAF70C3, false, true, 52, tda, 2);

		RSInterface scroll = addInterface(30918);
		addToItemGroup(30919, 3, 100, 6, 6, true, new String[] { "Select", null, null, null, null });
		scroll.width = 117;
		scroll.height = 139;
		scroll.scrollMax = 1475;
		scroll.totalChildren(1);
		scroll.child(0, 30919, 0, 5);

		addHoverButtonWSpriteLoader(30920, 3414, 132, 30, "Enchant", -1, 30921, 1);
		addHoveredImageWSpriteLoader(30921, 3415, 132, 30, 30922);

		addText(30923, "Enchant", 0xAF70C3, false, true, 52, tda, 0);

		addToItemGroup(30924, 3, 100, 18, 6, true, new String[] { null, null, null, null, null });

		addItemOnInterface(30925, 980, new String[] { null, null, null, null, null });

		addToItemGroup(30926, 1, 1, 0, 0, true, new String[] { "View", null, null, null, null });

		main.totalChildren(24);
		main.child(0, 30901, 100, 16);
		main.child(1, 30902, 394, 25);
		main.child(2, 30903, 394, 25);
		main.child(3, 30905, 110, 100);
		main.child(4, 30906, 110, 77);
		main.child(5, 30907, 110, 54);

		main.child(6, 30908, 117, 58);
		main.child(7, 30909, 117, 82);
		main.child(8, 30910, 117, 105);

		main.child(9, 30911, 204, 25);
		main.child(10, 30912, 137, 62);
		main.child(11, 30913, 137, 84);
		main.child(12, 30914, 137, 107);
		main.child(13, 30915, 283, 66);
		main.child(14, 30916, 283, 84);
		main.child(15, 30917, 150, 132);
		main.child(16, 30918, 117, 151);
		main.child(17, 30920, 287, 258);
		main.child(18, 30921, 287, 258);
		main.child(19, 30923, 313, 265);
		main.child(20, 30924, 268, 105);
		main.child(21, 30925, 317, 215);

		main.child(22, 30930, 268, 190);
		main.child(23, 30926, 317, 164);
	}



	public static void instanceManager() {
		int interID = 35000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 75;
		int y = 10;
		tab.totalChildren(17);

		addSpriteLoader(id, 3399);
		tab.child(c++, id++, 0 + x, 0 + y);

		addText(id, "@whi@Dream Manager", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 182 + x, 4 + y);

		addHoverButtonWSpriteLoader(id, 1016, 20, 14, "Close", 0, id + 1, 3);
		tab.child(c++, id++, 340 + x, 3 + y);
		addHoveredImageWSpriteLoader(id, 1017, 20, 14, id + 1);
		tab.child(c++, id++, 340 + x, 3 + y);
		id++;

		addText(id, "Npc", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 92 + x, 29 + y);

		addText(id, "Name", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 264 + x, 29 + y);

		dropGroup(id, 1, 1, 1, 1);
		tab.child(c++, id++, 185 + x, 179 + y);

		addText(id, "Instance Tickets", tda, 1, 0xff8624, false, true);
		tab.child(c++, id++, 223 + x, 178 + y);

		addText(id, "Amount: X", tda, 1, 0xff8624, false, true);
		tab.child(c++, id++, 223 + x, 194 + y);

		addText(id, "Ticket Cost: 100 Tickets", tda, 1, 0xff8624, true, true);
		tab.child(c++, id++, 263 + x, 217 + y);


		addConfigButtonWSpriteLoader(id, interID, 3400, 3401, 72, 32, "Choose", 0, 5, 1355);
		tab.child(c++, id++, 188 + x, 236 + y);
		addConfigButtonWSpriteLoader(id, interID, 3400, 3401, 72, 32, "Choose", 1, 5, 1355);
		tab.child(c++, id++, 268 + x, 236 + y);


		addText(id, "@gre@4x4", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 188 + 36 + x, 236 + 8 + y);

		addText(id, "@red@6x6", tda, 2, 0xff8624, true, true);
		tab.child(c++, id++, 268 + 36 + x, 236 + 8 + y);

		hoverButton(id, 3402, 3403, "Start", 2, 0xff8624, "@whi@Start");
		tab.child(c++, id++, 199 + x, 277 + y);

		addNpcOld(id, 252);
//        RSInterface.drawNpcOnInterface(id, 100, 1000);
		tab.child(c++, id++, 195 + x, 60 + y);

		tab.child(c++, 35070, 12 + x, 47 + y);


		interID = 35070;
		RSInterface list = addInterface(interID);
		list.width = 160 - 16;
		list.height = 256;
		list.scrollMax = 500;
		id = interID + 1;
		c = 0;
		x = 0;
		y = 0;
		list.totalChildren(200);

		id = 35171;
		for (int i = 0; i < 100; i++) {
			addSpriteLoader(id, 3404);
			list.child(c++, id++, 0 + x, 0 + y);
			y += 36;
		}

		y = 0;
		id = 35071;
		for (int i = 0; i < 100; i++) {
			teleportText(id, "", "Select", fonts, 1, 0xFF9900, false, true, 169, 17);
			list.child(c++, id++, 2 + x, 2 + y);
			y += 18;
		}

	}

	private static void Chest_Minigame_Item_Handler() {
		RSInterface main = addInterface(42200);

		addSpriteLoader(42201, 5612);
		addText(42202, "Loot Editor", tda, 2, 0xff8624, true, true);
		addText(42203, "Common Loot", tda, 0, 0xff8624, true, true);
		addText(42204, "Rare Loot", tda, 0, 0xff8624, true, true);

		itemGroupAutoScroll(42205, 42206, 3, 20, 15, 8, 3, new String[] { null, null, null, null, null }, true);

		itemGroupAutoScroll(42207, 42208, 3, 20, 15, 8, 3, new String[] { null, null, null, null, null }, true);



        addHoverButtonWSpriteLoader(42209, 5526, 120, 31, "Clear Common", -1, 42210, 1);
		addHoveredImageWSpriteLoader(42210, 5527, 120, 31, 42211);

		addHoverButtonWSpriteLoader(42212, 5526, 120, 31, "Clear Rare", -1, 42213, 1);
		addHoveredImageWSpriteLoader(42213, 5527, 120, 31, 42214);

		addText(42215, "Clear Common Loot", tda, 0, 0xff8624, true, true);
		addText(42216, "Clear Rare Loot", tda, 0, 0xff8624, true, true);

		addClickableText(42217, "Editing: Common", "Change Loot Table Being Edited", tda, 0, 0xff8624, 100, 20);
		addClickableText(42218, "Door Picks Enabled: @red@False", "Enable or Disable Door Picks", tda, 0, 0xff8624, 100,
				20);

		main.totalChildren(16);
		main.child(0, 42201, 101, 33);
		main.child(1, 42202, 256, 43);
		main.child(2, 42203, 180, 77);
		main.child(3, 42204, 332, 77);
		main.child(4, 42205, 117, 100);
		main.child(5, 42207, 269, 100);
		main.child(6, 42209, 122, 225);
		main.child(7, 42210, 122, 225);
		main.child(8, 42212, 270, 225);
		main.child(9, 42213, 270, 225);
		main.child(10, 42215, 182, 235);
		main.child(11, 42216, 330, 235);
		main.child(12, 42217, 113, 265);
		main.child(13, 42218, 113, 280);
		main.child(14, 51452, 386, 42);
		main.child(15, 51453, 386, 42);
	}

	private static void slotItemPositions() {
		RSInterface sec = addInterface(43400);
		RSInterface thi = addInterface(43550);
		RSInterface fou = addInterface(43700);
		RSInterface fiv = addInterface(43850);
		RSInterface six = addInterface(44000);
		RSInterface sev = addInterface(44150);

		sec.height = 220;
		sec.width = 38;
		thi.height = 220;
		thi.width = 38;
		fou.height = 220;
		fou.width = 38;
		fiv.height = 220;
		fiv.width = 38;
		six.height = 220;
		six.width = 38;
		sev.height = 220;
		sev.width = 38;
		sec.totalChildren(5);
		thi.totalChildren(10);
		fou.totalChildren(15);
		fiv.totalChildren(20);
		six.totalChildren(25);
		sev.totalChildren(30);
		int j = 0;
		int y = -40;
		for (int i = 0; i < 5; i++) {
			int x = 19;
			addText(43402 + i, "0", tda, 2, 0xffffff, true, true);
			sec.child(j, 43402 + i, x, y);
			j++;
			y -= 46;
		}
		j = 0;
		y = -40;

		for (int i = 0; i < 10; i++) {
			int x = 19;
			addText(43552 + i, "0", tda, 2, 0xffffff, true, true);
			thi.child(j, 43552 + i, x, y);
			j++;
			y -= 46;
		}
		j = 0;
		y = -40;

		for (int i = 0; i < 15; i++) {
			int x = 19;
			addText(43702 + i, "0", tda, 2, 0xffffff, true, true);
			fou.child(j, 43702 + i, x, y);
			j++;
			y -= 46;
		}
		j = 0;
		y = -40;

		for (int i = 0; i < 20; i++) {
			int x = 19;
			addText(43852 + i, "0", tda, 2, 0xffffff, true, true);
			fiv.child(j, 43852 + i, x, y);
			j++;
			y -= 46;
		}
		j = 0;
		y = -40;

		for (int i = 0; i < 25; i++) {
			int x = 19;
			addText(44002 + i, "0", tda, 2, 0xffffff, true, true);
			six.child(j, 44002 + i, x, y);
			j++;
			y -= 46;
		}
		j = 0;
		y = -40;

		for (int i = 0; i < 30; i++) {
			int x = 19;
			addText(44152 + i, "0", tda, 2, 0xffffff, true, true);
			sev.child(j, 44152 + i, x, y);
			y -= 46;
			j++;
		}

	}

	private static void forgottenRaids() {
		RSInterface main = addInterface(13107);
		addSpriteLoader(13108, 3312);
		addText(13109, "Tower of Ascension", tda, 2, 0xffffff, true, true);

		addText(13110, "Party Members", tda, 0, 0xffffff, true, true);

		for (int i = 0; i < 5; i++) {
			addText(13111+i, "@red@None", tda, 0, 0xffffff, true, true);
		}

		addText(13116, "Rewards", tda, 0, 0xffffff, true, true);
		itemGroupAutoScroll(13117, 13118, 3, 10, 3, 3, 4, new String[] {null, null, null, null, null}, true, 112, 100);


		addHoverButtonWSpriteLoader(13119, 5465, 90, 20, "Create Party/Invite Player", -1, 13120, 1);
		addHoveredImageWSpriteLoader(13120, 5464, 90, 20, 13121);
		addHoverButtonWSpriteLoader(13122, 5465, 90, 20, "Leave Party", -1, 13123, 1);
		addHoveredImageWSpriteLoader(13123, 5464, 90, 20, 13124);
		addHoverButtonWSpriteLoader(13125, 5465, 90, 20, "Start Raid", -1, 13126, 1);
		addHoveredImageWSpriteLoader(13126, 5464, 90, 20, 13128);

		addText(13129, "Create Party", tda, 0, 0xffffff, true, true);
		addText(13130, "Leave Party", tda, 0, 0xffffff, true, true);
		addText(13131, "Start Raid", tda, 0, 0xffffff, true, true);

		addText(13132, "Difficulty Selection", tda, 0, 0xffffff, true, true);
		addClickableText(13133, "Easy", "Select Easy", tda, 0, 0xEA952A, true, true, 112);
		addClickableText(13134, "Hard", "Select Easy", tda, 0, 0xEA952A, true, true, 112);

		main.totalChildren(24);
		main.child(0, 13108, 116, 40);
		main.child(1, 13109, 256, 50);
		main.child(2, 51452, 371, 48);
		main.child(3, 51453, 371, 48);
		main.child(4, 13110, 196, 83);
		for (int i = 0; i < 5; i++) {
			main.child(5+i, 13111+i, 196, 104+(i*20));
		}
		main.child(10, 13116, 328, 83);
		main.child(11, 13117, 275, 100);

		main.child(12, 13119, 151, 210);
		main.child(13, 13120, 151, 210);
		main.child(14, 13129, 196, 215);

		main.child(15, 13122, 151, 235);
		main.child(16, 13123, 151, 235);
		main.child(17, 13130, 196, 240);

		main.child(18, 13125, 151, 260);
		main.child(19, 13126, 151, 260);
		main.child(20, 13131, 196, 265);

		main.child(21, 13132, 328, 211);
		main.child(22, 13133, 272, 236);
		main.child(23, 13134, 272, 261);
	}

	protected static void combiner() {
		RSInterface main = addInterface(19700);

		addSpriteLoader(19701, 1340);
		addText(19702, "Grand Max Invention Interface", tda, 2, 0xAF70C3, true, true);

		addText(19703, "Grand Max items", tda, 0, ColorConstants.BLACK, true, true);

		addText(19704, "Grand Max Requirements", tda, 0, ColorConstants.BLACK, true, true);

		addHoverText(19705, "Close", "Close", tda, 1, 0xACACAC, false, true, 100, 14);

		RSInterface scroll = addInterface(19800);
		scroll.width = 200;
		scroll.height = 185;
		scroll.scrollMax = 400;

		addClickableText(19706, "Invent Item", "Invent Item", tda, 1, ColorConstants.BLACK, true, true, 100);
		addClickableText(19707, "Invent Item", "Invent Item", tda, 1, ColorConstants.BLACK, true, true, 100);
		addClickableText(19708, "Invent Item", "Invent Item", tda, 1, ColorConstants.BLACK, true, true, 100);
		addClickableText(19709, "Invent Item", "Invent Item", tda, 1, ColorConstants.BLACK, true, true, 100);

		/**
		 * Crafted item itemgroup
		 */

		addToItemGroup(19710, 1, 4, 0, 23, true, new String[]{null, null, null, null, null}); // int id, int w, int
		/**
		 * Requirements itemgroup
		 */

		addToItemGroup(19711, 4, 12, 28, 21, true, new String[]{null, null, null, null, null});


		main.totalChildren(11);

		main.child(0, 19701, 2, 10);
		main.child(1, 19702, 270, 18);
		main.child(2, 19703, 60, 60);
		main.child(3, 19704, 245, 60);
		main.child(4, 19705, 440, 18);

		main.child(5, 19706, 385, 95);
		main.child(6, 19707, 385, 145);
		main.child(7, 19708, 385, 200);
		main.child(8, 19709, 385, 258);
		main.child(9, 19710, 40, 80);
		main.child(10, 19711, 132, 88);
	}


	public static void cardPacksRewards() {
		int interID = 141000;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		int x = 130;
		int y = 30;
		tab.totalChildren(18);

		addSpriteLoader(id, 2004);
		tab.child(c++, id++, 113, 30);

		addHoverButtonWSpriteLoader(id, 2013, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 375, 33);
		addHoveredImageWSpriteLoader(id, 2013, 16, 16, id + 1);
		tab.child(c++, id++, 375, 33);
		id++;

		addText(id, "", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 258, 33);

		hoverButton(id, 2010, 2011, "Open Again", 2, 0xff8624, "");
		tab.child(c++, id++, 370, 280);
		for (int col = 0; col < 2; col++) {
			for (int row = 0; row < 3; row++) {
				addSpriteLoader(id, 3542 + row + col);
				tab.child(c++, id++, 21 + x, 53 + y);
				itemGroup(id, 1, 1, 1, 1);
				tab.child(c++, id++, 21 + 16 + x + 1, 53 + 27 + y + 2 );
				x += 73;
			}
			x = 130;
			y += 96;
		}

		addModel(25359, 92, 130, 4151, 1050, 3501);
		tab.child(c++, 25359, 169, 56);


	}

	public void tutorial() {
		RSInterface main = addInterface(32750);
		addPercentageBar(32751, 300, 100, 0x000001, 0xAF70C3, 10, false);
		addText(32752, "@whi@Explanation of tutorial step here...", 0xF3F2F3, true, true, 52, tda, 0);
		addText(32753, "@whi@Welcome to Athens!", 0xF3F2F3, true, true, 52, tda, 0);

		main.totalChildren(3);
		main.child(0, 32751, 100, 290);
		main.child(1, 32752, 256, 305);
		main.child(2, 32753, 256, 275);

	}


	public static void cardPacks() {
		int interID = 141500;
		RSInterface tab = addInterface(interID);
		int id = interID + 1;
		int c = 0;
		tab.totalChildren(6);

		addSpriteLoader(id, 2004);
		tab.child(c++, id++, 113, 30);

		addHoverButtonWSpriteLoader(id, 2013, 16, 16, "Close Window", 0, id + 1, 3);
		tab.child(c++, id++, 375, 33);
		addHoveredImageWSpriteLoader(id, 2013, 16, 16, id + 1);
		tab.child(c++, id++, 375, 33);
		id++;

		addText(id, "", tda, 2, 0xFF981F, true, true);
		tab.child(c++, id++, 258, 33);

		hoverButton(id, 2010, 2011, "@yel@Open", 2, 0xff8624, "");
		tab.child(c++, id++, 370, 280);

		addModel(25358, 92, 130, 4151, 1050, 3501);
		tab.child(c++, 25358, 210, 140);

	}

	public static void examineTab() {
		RSInterface tab = addInterface(52100);

		// addSprite(52101, 0, "/Sprites/ExamineTab");

		/// addSprite1(52101, 0, "Interfaces/ExamineTab/SPRITE");
		addSpriteLoader(52101, 1257);

		// addContainer(52102, 0, 1, 80, 30, 2, 100, false, false, false, null, null,
		// null, null, null);
		RSInterface.itemGroup(52102, 1, 80, 30, 2);

		addText(52113, "", tda, 2, 0xff8a1f, false, true);
		addText(52114, "Item Bonuses", tda, 2, 0xff8a1f, false, true);
		addText(52128, "Attack", tda, 2, 0xff8a1f, false, true);
		addText(52129, "Defence", tda, 2, 0xff8a1f, false, true);
		addText(52130, "Other Bonuses", tda, 2, 0xff8a1f, false, true);
		addText(52143, "", tda, 2, 0xff8a1f, false, true);

		addText(52144, "Strength", tda, 1, 0xff8a1f, false, true);
		addText(52145, "Prayer", tda, 1, 0xff8a1f, false, true);

		int y = 142;
		for (int i = 0; i < 9; i++) {
			addText(52103 + i, "", tda, 1, 0xff8a1f, false, true);
		}

		int yy = 116;
		String[] text = {"Stab", "Slash", "Crush", "Magic", "Range"};

		for (int i = 0; i < 5; i++) {
			addText(52131 + i, text[i], tda, 1, 0xff8a1f, false, true);
		}

		int yyy = 116;
		for (int i = 0; i < 5; i++) {
			addText(52138 + i, text[i], tda, 1, 0xff8a1f, false, true);
		}

		addHoverButton(52146, CLOSE_BUTTON, CLOSE_BUTTON, 16, 16, "Close Window", 0, 52147, 1);
		addHoveredButton(52147, CLOSE_BUTTON_HOVER, CLOSE_BUTTON_HOVER, 16, 16, 52148);

		tab.totalChildren(31);
		int childNum = 0;
		setBounds(52101, 75, 20, childNum++, tab);
		setBounds(52102, 104, 71, childNum++, tab);
		setBounds(52113, 110, 120, childNum++, tab);
		setBounds(52114, 320, 70, childNum++, tab);
		setBounds(52128, 279, 94, childNum++, tab);
		setBounds(52129, 369, 94, childNum++, tab);
		setBounds(52130, 320, 220, childNum++, tab);
		setBounds(52143, 154, 86, childNum++, tab);
		setBounds(52144, 275, 240, childNum++, tab);
		setBounds(52145, 275, 255, childNum++, tab);
		setBounds(52146, 445, 28, childNum++, tab);
		setBounds(52147, 445, 28, childNum++, tab);

		for (int i = 0; i < 9; i++) {
			setBounds(52103 + i, 106, y, childNum++, tab);
			y += 15;
		}

		for (int i = 0; i < 5; i++) {
			setBounds(52131 + i, 275, yy, childNum++, tab);
			yy += 15;
		}

		for (int i = 0; i < 5; i++) {
			setBounds(52138 + i, 366, yyy, childNum++, tab);
			yyy += 15;
		}

	}


	public void loadCustoms() {
		waveMinigameInterface();
		leaderboardInterface();
		tutorial();
		starterTasksInterface();
		raids(tda);
		staffPanel();
		cosmeticPanel();

		membershipInterface();
		panelInterface();
		panelInterfaceAccountInfo();
		panelInterfaceInterfaces();
		possibleLoot();
		casketInterface();
		combiner();
		examineTab();
		editClan();
		gameModeSelection(tda);
		expRewardInterface();
		gamblingInterface(tda);
		//skillTabInterface();
		playerPanel();
		killsTracker();
		friendsTabInterface();
		ignoreTabInterface();
		equipmentTab();
		upgradeInterface(tda);
		equipmentScreenInterface();
		clanChatTabInterface();
		configureLunar();
		redoSpellBooks();
		HolidayTaskManager();
		shopInterface();
		fairy();
		VoteStreaksInterface();
		summoningTabInterface();
		goodiebagInterface(tda);
		loyaltyShop();
		emoteTab();
		curseTabInterface();
		cardPacks();
		cardPacksRewards();
		opacityInterface();
		staffTabInterface(tda);
		levelUpInterfaces();
		optionTab();
		sidebarInterfaces();
		quickCursesInterface();
		quickPrayersInterface();
		OsDropViewer();
		instanceManager();
	//	commands();
		statCheckerInterface();
		dailyTask();
		//examiner();
		worldMap();
		slotInterface();
		progressionInterface();
/*		Chest_Minigame_Item_Handler();*/
		enchanttable();
		groupIronman();
		groupbankInterface();
		afkCheckerInterface();
		afkInfoInterface();
		achievements();
		donatorShop();
		CosmeticShop();
		upgradeInterface();
		teleportInterface();
		//minigameInterface();
		vodOverlay();
		pestControlInterfaces();
		teleporting();
		TierUpgradingInterface();
		forgottenRaids();
        underwater_overlay();
	}

}
