package org.necrotic.client.cache.media;
import org.necrotic.ColorConstants;
import org.necrotic.Configuration;
import org.necrotic.client.*;
import org.necrotic.client.cache.Archive;
import org.necrotic.client.cache.definition.EntityDef;
import org.necrotic.client.cache.definition.ItemDef;
import org.necrotic.client.cache.media.rsinterface.interfaces.*;
import org.necrotic.client.collection.MRUNodes;
import org.necrotic.client.net.Stream;
import org.necrotic.client.media.AnimationSkeleton;
import org.necrotic.client.media.renderable.actor.Player;
import org.necrotic.client.util.Milestone;
import org.necrotic.client.util.RSFontSystem;
import org.necrotic.client.util.TextUtilities;
import org.necrotic.client.cache.media.rsinterface.CustomInterfaces;
import org.necrotic.client.cache.media.rsinterface.Interfaces;
import org.necrotic.client.cache.media.rsinterface.SummoningInterfaceData;
import org.necrotic.client.cache.media.rsinterface.dropdowns.Dropdown;
import org.necrotic.client.cache.media.rsinterface.dropdowns.DropdownMenu;
import org.necrotic.client.media.renderable.Model;
import java.util.Arrays;

public class RSInterface {

	public boolean hovers;

    public static void findEmptyIds() {
        int freeChildCount = 0;
        int longestRunCount = 0;
        int longestRunStart = -1;
        int curRunStart = 0;
        int curRunCount = 0;
        int sandwichId = -1;
        int range = 0;
        for (int i = 0; i < interfaceCache.length; i++) {
            if (interfaceCache[i] == null) {
                //System.out.println(i); // prints out this id is unused
                freeChildCount++;
                range++;
                if (curRunStart == -1) {
                    curRunStart = i;
                }
                curRunCount++;
            } else {
                if (curRunCount == 1 && sandwichId < 0) {
                    sandwichId = i - 1;
                }
                if (curRunCount > longestRunCount) {
                    longestRunCount = curRunCount;
                    longestRunStart = curRunStart;
                }
                if (range > 0)
                    System.out.println("Range of Id's unusued: " + curRunStart + " - " + (curRunStart + range));
                range = 0;
                curRunStart = -1;
                curRunCount = 0;

            }
        }
        System.out.println(freeChildCount + " unused interface childs");
        System.out.println(longestRunStart + " onwards has " + longestRunCount + " unused interface childs in a row");
        System.out.println(sandwichId + " is a single unused interface child surrounded by used ones.");
    }

	public static void addProperConfigButton(int id, int sprite, int selectedSprite, String
			tooltip, int configId, int configFrame) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 5;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (sprite != -1) {
			tab.disabledSprite = Client.spritesMap.get(sprite);
			tab.enabledSprite =  Client.spritesMap.get(selectedSprite);
		}
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.tooltip = tooltip;

		tab.valueCompareType = new int[1];
		tab.requiredValues = new int[1];
		tab.valueCompareType[0] = 1;
		tab.requiredValues[0] = configId;
		tab.valueIndexArray = new int[1][3];
		tab.valueIndexArray[0][0] = 5;
		tab.valueIndexArray[0][1] = configFrame;
		tab.valueIndexArray[0][2] = 0;
	}

	public static final int DEFAULT_COLOR = 0xFE972F;

	public int viewableColumns;
	public int frameTimer;
	public String aString228;
	public int progress;
	public int firstColor;
	public int secondColor;
	public boolean verticle;
	public boolean hoverable;
	public boolean selected;
	public int invDefaultScrollMax;
	//public ItemDto invItems[];
	public boolean invDynamicScrollbar;
	public int invStartIndex;

	//public boolean deleteOnDrag2;
	public boolean hideInvStackSizes;
	public boolean itemSearch;
	public boolean inputField;
	public String inputFieldString;
	public int inputFieldStringX;
	public int inputFieldStringY;
	public boolean clickingChangesConfig = true;

	private static void bestInSlot() {
		final int STARTING_POINT = 27245;
		String[] data = {"Attack bonus", "Stab: +0", "Slash: +0", "Crush: +0", "Magic: +0", "Range: +0", "Defence bonus", "Stab: +0", "Slash: +0", "Crush: +0", "Magic: +0", "Range: +0", "Other bonus", "Melee strength: +0", "Ranged strength: +0", "Magic damage: +0.0%", "Prayer: +0", ""};
		RSInterface main = addInterface(STARTING_POINT);
		main.totalChildren(18 + data.length);
		main.child(0, STARTING_POINT + 1, 20, 10); // 1294 -> 5582
		addSpriteLoader(STARTING_POINT + 1, 5582);
		int xPos = 30;
		int yPos = 49;
		for (int i = 0; i < 10; i++) {
			if (i == 5) {
				xPos += 53;
				yPos = 49;
			}
			addHoverableConfigSprite(STARTING_POINT + 2 + i, 5585, 5586, true, "Select", i, 3000);
			main.child(i + 1, STARTING_POINT + 2 + i, xPos, yPos);
			yPos += 53;
		}
		addToItemGroup(STARTING_POINT + 12, 2, 5, 22, 21, false, null, null, null);
		main.child(11, STARTING_POINT + 12, 36, 56);
		Arrays.fill(interfaceCache[STARTING_POINT + 12].inv, 6200);
		Arrays.fill(interfaceCache[STARTING_POINT + 12].invStackSizes, 1);

		RSInterface scroll = addInterface(STARTING_POINT + 13);
		main.child(12, STARTING_POINT + 13, 136, 50);
		scroll.width = 164;
		scroll.height = 257;
		scroll.scrollMax = 42 * 40;

		scroll.totalChildren(301);
		yPos = 2;
		for (int i = 0; i < 150; i++) {
			addHoverableConfigSprite(STARTING_POINT + 14 + i, 5583, 5584, true, "Select", i, 3200);
			scroll.child(i, STARTING_POINT + 14 + i, 4, yPos);
			yPos += 42;
		}

		yPos = 12;
		for (int i = 0; i < 150; i++) {
			addTextComponent(STARTING_POINT + 164 + i, "Some random text", fonts, 1, 0xAF70C3, false, true, 0, 0);
			scroll.child(150 + i, STARTING_POINT + 164 + i, 46, yPos);
			yPos += 42;
		}

		addToItemGroup(STARTING_POINT + 314, 1, 150, 0, 10, false, null, null, null);
		scroll.child(300, STARTING_POINT + 314, 9, 7);
		Arrays.fill(interfaceCache[STARTING_POINT + 314].inv, 4152);
		Arrays.fill(interfaceCache[STARTING_POINT + 314].invStackSizes, 1);

		xPos = 332;
		yPos = 55;
		for (int i = 0; i < data.length; i++) {
			String str = data[i];
			boolean title = str.endsWith("bonus");
			boolean last = i == data.length - 1;
			int fontIndex = title ? 2 : 1;
			int xOffset = title ? 0 : 11;
			addTextComponent(STARTING_POINT + 315 + i, str, fonts, fontIndex, 0xAF70C3, false, true, 0, 0);
			main.child(13 + i, STARTING_POINT + 315 + i, xPos + xOffset, last ? yPos + 4 : yPos);
			yPos += title ? 15 : 13;
		}

		addTextComponent(STARTING_POINT + 335, "Item Tier List", fonts, 2, 0xAF70C3, false, true, 0, 0);
		main.child(31, STARTING_POINT + 335, 208, 20);
		addCloseButton1(STARTING_POINT + 336);
		main.child(32, STARTING_POINT + 336, 471, 19);


		xPos = 28;
		int index = 0;
		for (int i = 0; i < 6; i += 2) {
			addHoverableConfigSprite(STARTING_POINT + 337 + index, 5588 + i, 5589 + i, true, "Select", i, 3300);
			main.child(33 + index, STARTING_POINT + 337 + index, xPos, 19);
			xPos += 25;
			index++;
		}

	}
	public static RSInterface addTextComponent(int id, String text, TextDrawingArea[] tda, int idx, int color,
                                               boolean center, boolean shadow, int width, int height) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = width;
		tab.height = height;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.enabledMessage = "";
		tab.disabledColor = color;
		tab.enabledColor = 0;
		tab.disabledMouseOverColor = 0;
		tab.enabledMouseOverColor = 0;
		return tab;
	}


	public static void mysteryBoxSpinner(TextDrawingArea[] tda) {

		RSInterface base = addTabInterface(47000);

		addSpriteLoader(47001, 1638);
		addSpriteLoader(47002, 1639);
		addButtonWSpriteLoader(47004, 1640, "Spin", 114, 25);
		addText(47005, "Spin Now!", tda, 0, 0xff9040, true);
		addText(47006, "Possible Rewards:", tda, 2, 0xff9040, true);
		addText(47007, "Mystery Box", tda, 2, 0xff9040, true);
		addText(47008, "", tda, 2, 0xff9040, true);
		addButtonWSpriteLoader(47009, 1640, "Buy boxes", 114, 25);
		addText(47010, "Buy Boxes!", tda, 0, 0xff9040, true);
		addText(47011, "Status: Inspecting box.", tda, 0, 0xff9040, true);

		addButtonWSpriteLoader(47014, CLOSE_BUTTON, "Close");
		addText(47015, "My Winnings:", tda, 2, 0xff9040, true);
		addHoverText(47016, "", "", tda, 0, 0xff9040, true, false, 75, 10);

		int baseChild = 0;
		int startX = 39;
		int startY = 21;
		base.totalChildren(16);
		base.child(baseChild++, 47001, startX, startY);
		base.child(baseChild++, 47004, 219 + startX, 100 + startY);
		base.child(baseChild++, 47005, 263 + startX, 106 + startY);
		base.child(baseChild++, 47006, 103 + startX, 96 + startY);
		base.child(baseChild++, 47007, 257, 9 + startY);
		base.child(baseChild++, 47008, 351 + startX, 180 + startY);
		base.child(baseChild++, 47009, 319 + startX, 100 + startY);
		base.child(baseChild++, 47010,  363 + startX, 106 + startY);
		base.child(baseChild++, 47011,  320 + startX, 131 + startY);
		base.child(baseChild++, 47014, 409 + startX, 9 + startY) ;
		base.child(baseChild++, 47015, 320 + startX, 152 + startY);
		base.child(baseChild++, 47016, 27 + startX, 21 + startY);
		base.child(baseChild++, 47018, 19 + startX, 114 + startY);
		base.child(baseChild++, 47023, 10 + startX, 14 + startY);
		base.child(baseChild++, 47029, 211 + startX, 170 + startY);
		base.child(baseChild++, 47020, 15 + startX, 43 + startY);


		RSInterface possibleRewards = addInterface(47018);

		addToItemGroup(47019, 4, 20, 9, 0, false, "", "", "");
		possibleRewards.totalChildren(1);
		possibleRewards.child(0, 47019, 0, 0);
		possibleRewards.width = 162;
		possibleRewards.height = 167;
		possibleRewards.scrollMax = 700;

		RSInterface rewardStrip = addInterface(47020);

		addSprite(47021, 1644);
		addToItemGroup(47022, 300, 1, 9, 0, false, "", "", "");
		rewardStrip.totalChildren(2);
		rewardStrip.child(0, 47021, 0, 0);
		rewardStrip.child(1, 47022, 3, 3);
		rewardStrip.width = 406;

		RSInterface reward = addInterface(47023);
		reward.hideWidget = true;

		addSprite(47024, 1643);
		addToItemGroup(47026, 1, 1, 0, 0, false, "", "", "");

		addText(47027, "You won:", tda, 2, 0xff9040, true);
		addText(47028, "Item name", tda, 1, 0xff9040, true);

		reward.totalChildren(4);
		reward.child(0, 47024, 140, 120);
		reward.child(1, 47026, 228, 137);
		reward.child(2, 47027, 244, 184);
		reward.child(3, 47028, 244, 200);

		RSInterface winnings = addInterface(47029);

		addToItemGroup(47030, 5, 20, 9, 0, false, "", "", "");
		winnings.totalChildren(1);
		winnings.child(0, 47030, 0, 1);
		winnings.width = 197;
		winnings.height = 111;
		winnings.scrollMax = 300;
	}
	protected static void statCheckerInterface() {
		final int STARTING_POINT = 37500;
		RSInterface main = addInterface(STARTING_POINT);
		addSpriteLoader(STARTING_POINT + 1, 1380);
		main.totalChildren(6);
		main.child(0, STARTING_POINT + 1, 10, 10);

		addTextComponent(STARTING_POINT + 2, "Balloon drop party", fonts, 2, 16777215, true, true, 0, 0);
		main.child(1, STARTING_POINT + 2, 254, 20);
		addHoverableSprite(STARTING_POINT + 3, 1384, 1313, true, "Accept");
		main.child(2, STARTING_POINT + 3, 398, 245);
		addTextComponent(STARTING_POINT + 4, "Accept", fonts, 1, 3362227, false, true, 0, 0);
		main.child(3, STARTING_POINT + 4, 421, 248);

		main.child(4, STARTING_POINT + 10, 15, 45);

		addTextComponent(STARTING_POINT + 6, "Hey there! We are currently at $535/$1000 for a Drop Party", fonts, 1, 16777215, true, true, 0, 0);
		main.child(5, STARTING_POINT + 6, 252, 283);


		RSInterface scroll = addInterface(STARTING_POINT + 10);
		scroll.width = 455;
		scroll.height = 196;
		scroll.scrollMax = 300;

		addToItemGroup(STARTING_POINT + 11, 10, 5, 5, 7, false, null, null, null);
		scroll.totalChildren(1);
		scroll.child(0, STARTING_POINT + 11, 3, 5);
		Arrays.fill(RSInterface.interfaceCache[STARTING_POINT + 11].inv, 6200);
		Arrays.fill(RSInterface.interfaceCache[STARTING_POINT + 11].invStackSizes, 1);

	}


	public Sprite unrevealedSprite;
	private static void customServerPerks(TextDrawingArea[] font) {
		int STARTING_POINT = 42050;
		RSInterface main = addInterface(STARTING_POINT);
		addSpriteLoader(STARTING_POINT + 1, 1516); // 1229
		addText(STARTING_POINT + 2, "Server Perks", font, 2, 16746020, true, true);
		addText(STARTING_POINT + 4, "Once the pool has been filled", font, 0, 16746020, true, true);
		addText(STARTING_POINT + 5, "Perk will be activated Worldwide!", font, 0, 16746020, true, true);
		addHoverButtonWSpriteLoader(STARTING_POINT + 6, 1519, 90, 35, "Contribute", -1, STARTING_POINT + 7, 1);
		addHoverButtonWSpriteLoader(STARTING_POINT + 12, 714, 50, 50, "Close", -1, STARTING_POINT + 12, 1);
		addHoveredImageWSpriteLoader(STARTING_POINT + 7, 1520, 90, 35, STARTING_POINT + 8);
		addText(STARTING_POINT + 9, "Contribute!", font, 0, 16746020, true, true);
		addProgressBar(STARTING_POINT + 10, 165, 30, 75, 16711680, 5492557);
		addText(STARTING_POINT + 11, "250 / 250", font, 1, 16777215, true, true);
		main.totalChildren(11);
		main.child(0, STARTING_POINT + 1, 155, 10);
		main.child(1, STARTING_POINT + 2, 254, 20);
		main.child(2, STARTING_POINT + 4, 254, 235);
		main.child(3, STARTING_POINT + 5, 254, 245);
		main.child(4, STARTING_POINT + 6, 210, 263);
		main.child(5, STARTING_POINT + 7, 210, 263);
		main.child(6, STARTING_POINT + 9, 254, 270);
		main.child(7, STARTING_POINT + 15, 120, 49);
		main.child(8, STARTING_POINT + 10, 173, 189);
		main.child(9, STARTING_POINT + 11, 254, 200);
		main.child(10, STARTING_POINT + 12, 330, 20);
		RSInterface perkScroll = addInterface(STARTING_POINT + 15);
		perkScroll.totalChildren(18);
		int idStart = STARTING_POINT + 16;
		int yPos = 0;

		for (int i = 0; i < 6; i++) {
			addHoverableConfigSprite(idStart + i, 1517, 1518, true, "Select", i, 4000);
			perkScroll.child(i, idStart + i, 53, yPos);
			yPos += 35;
		}

		String[] PERK_NAMES = {"DMG BOOST", "DR BOOST", "SLAYER", "COMBO", "2X KILLS", "2X DROPS"};
		int child = 6;
		int index = 0;
		yPos = 5;
		idStart = STARTING_POINT + 50;
		for (int j = 0; j < 12; j += 2) {
			addText(idStart + j, PERK_NAMES[index], font, 2, 16746020, true, true);
			addText(idStart + j + 1, "", font, 1, 32768, false, true);
			perkScroll.child(child, idStart + j, 125, yPos);
			perkScroll.child(child + 1, idStart + j + 1, 110, yPos + 14);
			child += 2;
			yPos += 35;
			index++;
		}
		perkScroll.width = 200;
		perkScroll.height = 162;
		perkScroll.scrollMax = 230;
		// Add hover effect for unselected perks

		}


	public static RSInterface addHoverableConfigSprite(int id, int mainSprite, int hoverSprite, boolean clickable, String tooltip, int configId, int configFrame) {
		//  System.out.println("Added hoverable sprite with param: " + clickable);
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 111;
		tab.atActionType = 5;
		//   System.out.println(tab.atActionType + " ->");
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverable = true;
		tab.disabledSprite = Client.spritesMap.get(mainSprite);
		tab.enabledSprite = Client.spritesMap.get(hoverSprite);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.tooltip = tooltip;
		tab.valueCompareType = new int[1];
		tab.requiredValues = new int[1];
		tab.valueCompareType[0] = 1;
		tab.requiredValues[0] = configId;
		tab.valueIndexArray = new int[1][3];
		tab.valueIndexArray[0][0] = 5;
		tab.valueIndexArray[0][1] = configFrame;
		tab.valueIndexArray[0][2] = 0;
		return tab;
	}


	public static void addProgressBar(int id, int width, int height, int currentPercent, int firstColor, int secondColor) {
		RSInterface rsi = addInterface(id);
		rsi.type = 287;
		rsi.width = width;
		rsi.height = height;
		rsi.progress = currentPercent;
		rsi.firstColor = firstColor;
		rsi.secondColor = secondColor;
	}


	private static void perkOverlays(TextDrawingArea[] tda) {
		int STARTING_POINT_1 = 42112;
		int STARTING_POINT_2 = 42120;
		addPerkOverlay(STARTING_POINT_1, 387, 25, tda);
		addPerkOverlay(STARTING_POINT_2, 387, 67, tda);
	}

	private static void addPerkOverlay(int startingPoint, int x, int y, TextDrawingArea[] tda) {
		RSInterface main = addInterface(startingPoint);
		addSpriteLoader(startingPoint + 4, -1);

		addText(startingPoint + 2, "", tda, 0, ColorConstants.YELLOW, true, true);
		addText(startingPoint + 3, "", tda, 0, ColorConstants.CYAN, true, true);
		main.totalChildren(4);
		main.child(0, startingPoint + 1, x, y);
		main.child(1, startingPoint + 2, x + 92, y + 5);
		main.child(2, startingPoint + 3, x + 92, y - 9);
		main.child(3, startingPoint + 4, x + 76, y - 25);

	}

	public static void addToItemGroup(int id, int w, int h, int x, int y, boolean hasActions, boolean displayAmount, String[] actions, boolean displayExamine) {
		RSInterface widget = addInterface(id);
		widget.width = w;
		widget.height = h;
		widget.inv = new int[w * h];
		widget.invStackSizes = new int[w * h];
		widget.usableItemInterface = false;
		widget.invSpritePadX = x;
		widget.invSpritePadY = y;
		widget.spritesX = new int[20];
		widget.spritesY = new int[20];
		widget.sprites = new Sprite[20];
		// rsi.actions = new String[5];
		if (hasActions) {
			widget.actions = actions;
		}
		widget.type = 2;
		widget.displayExamine = displayExamine;
	}

	public boolean displayExamine = true;

	private static CustomInterfaces customInterfaces;

	public static CustomInterfaces getCustomInterfaces() {
		return customInterfaces;
	}

	public static void newHoverButton(int id, String tooltip, int enabledSprite, int disabledSprite, int atActionType) {
		RSInterface tab = addInterface(id);
		tab.tooltip = tooltip;
		tab.atActionType = atActionType;
		tab.type = 200;
		tab.enabledSprite = Client.spritesMap.get(enabledSprite);
		tab.disabledSprite = Client.spritesMap.get(disabledSprite);
		tab.width = Client.spritesMap.get(enabledSprite).myWidth;
		tab.height = Client.spritesMap.get(enabledSprite).myHeight;
		tab.toggled = false;
	}



	public static void hoverButton(int id, int regularSpriteId, int hoveredSpriteId, String hoverTooltip, int font, int color, String buttonText) {
		RSInterface tab = addInterface(id);
		tab.tooltip = hoverTooltip;
		tab.atActionType = 1;
		tab.type = 42;
		tab.disabledSprite = Client.spritesMap.get(regularSpriteId);
		tab.enabledSprite = Client.spritesMap.get(hoveredSpriteId);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.messageOffsetX = tab.width / 2;
		tab.messageOffsetY = tab.height / 2 + 5;
		tab.message = buttonText;
		tab.rsFont = getFont(font);
		tab.textDrawingAreas = getOldFont(font);
		tab.enabledColor = color;
		tab.centerText = true;
	}

	public static void hoverButton(int id, int regularSpriteId, int hoveredSpriteId, String hoverTooltip) {
		hoverButton(id, regularSpriteId, hoveredSpriteId, hoverTooltip, 0, 0, "");
	}

	public static RSFontSystem getFont(int fontType) {
		switch (fontType) {
			case 0:
			default:
				return Client.instance.newSmallFont;
			case 1:
				return Client.instance.newRegularFont;
			case 2:
				return Client.instance.newBoldFont;
			case 3:
				return Client.instance.newFancyFont;
		}

	}

	public static TextDrawingArea getOldFont(int fontType) {
		switch (fontType) {
			case 0:
			default:
				return Client.instance.smallText;
			case 1:
				return Client.instance.normalText;
			case 2:
				return Client.instance.boldText;
			case 3:
				return Client.instance.aTextDrawingArea_1273;
		}
	}

	public static void itemGroup(int id, int w, int h, int x, int y, int... itemId) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.type = 2;
		if (itemId != null && itemId.length > 0) {
			for (int i = 0; i < rsi.inv.length; i++) {
				rsi.inv[i] = itemId[0] + 1;
				rsi.invStackSizes[i] = 1;
			}
		}
	}

	//sec lets test the client thing
	public static void addNewCloseButton(TextDrawingArea[] tda) {
		hoverButton(14650, tda, 1238, 1239, "Close", 0, 16750623, "");
	}

	public static void killTracker(TextDrawingArea[] tda) {
		RSInterface main = addInterface(33300);
		addSpriteLoader(33301, 1400);

		hoverButton(33302, tda, 1401, 1402, "Show Monsters", 0, 16750623, "@or1@Show Monsters");
		hoverButton(33303, tda, 1401, 1402, "Show Bosses", 0, 16750623, "@or1@Show Bosses");
		hoverButton(33304, tda, 1401, 1402, "Show Others", 0, 16750623, "@or1@Show Others");
		hoverSpriteWithText(33305, tda, 1405, 1406, 0, 0, "");
		hoverSpriteWithText(33306, tda, 1403, 1404, 0, 0, ""); //2 different buttons
//these were diff btw not both supposed to be 1405 i guess
		addText(33307, "Your personal kill tracker", tda, 2, 16750623, false, true);
		addText(33308, "Currently Viewing: Chaos Elemental", tda, 1, 16750623, true, true);
		addText(33309, "Chaos Elemental kills: 15", tda, 1, 16750623, true, true);
		addText(33310, "Total Npc Kills: 15,521", tda, 0, 16750623, false, true);
		addNpc(33311, 50);
		addNewCloseButton(tda);


		main.totalChildren(13);
		main.child(0, 33301, 10, 10);
		main.child(1, 33302, 137, 50);
		main.child(2, 33303, 239, 50);
		main.child(3, 33304, 342, 50);
		main.child(4, 33305, 142, 115);
		main.child(5, 33306, 15, 291);

		main.child(6, 33307, 169, 21);
		main.child(7, 33308, 285, 95);
		main.child(8, 33309, 285, 283);
		main.child(9, 33310, 19, 298);
		main.child(10, 33311, 214, 134);
		main.child(11, 33320, 17, 52);
		main.child(12, 14650, 419, 19);

		RSInterface nameList = addInterface(33320);
		nameList.totalChildren(100);
		nameList.width = 106;
		nameList.height = 237;
		nameList.scrollMax = 1500;
		int y = 3;
		for (int i = 0; i < 100; i++) {
			hoverButton(33321 + i, tda, 1401, 1402, "Select", 1, 16750623, "");
			nameList.child(i, 33321 + i, 0, y);
			y += 29;
		}
	}

	public int messageOffsetX;

	public int messageOffsetY;

	public boolean widgetActive;

	public static void hoverSpriteWithText(int id, TextDrawingArea[] tda, int disabledSprite, int hoverSprite, int font, int color, String buttonText) {
		RSInterface tab = addInterface(id);
		tab.type = 42;
		tab.disabledSprite = Client.spritesMap.get(disabledSprite);
		tab.enabledSprite = Client.spritesMap.get(hoverSprite);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.messageOffsetX = tab.width / 2;
		tab.messageOffsetY = (tab.height / 2) + 5;
		tab.message = buttonText;
		tab.textDrawingAreas = tda[font];
		tab.disabledColor = color;
		tab.centerText = true;
		tab.widgetActive = true;
	}

	public static void hoverButton(int id, TextDrawingArea[] tda, int disabledSprite, int hoverSprite, String hoverTooltip, int idx, int color, String buttonText) {
		RSInterface tab = addInterface(id);
		tab.tooltip = hoverTooltip;
		tab.atActionType = 1;
		tab.type = 42;
		tab.disabledSprite = Client.spritesMap.get(disabledSprite);
		tab.enabledSprite = Client.spritesMap.get(hoverSprite);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.messageOffsetX = tab.width / 2;
		tab.messageOffsetY = (tab.height / 2) + 5;
		tab.message = buttonText;
		tab.textDrawingAreas = tda[idx];
		tab.disabledColor = color;
		tab.centerText = true;
		tab.widgetActive = true;
	}

	public static void goodiebagInterface(TextDrawingArea[] tda) { // open urcache,.
		final int STARTING_POINT = 49200;
		RSInterface main = addInterface(STARTING_POINT);

		final int ROWS = 5;
		final int COLUMNS = 4;
		addSpriteLoader(STARTING_POINT + 1, 3036);
		addText(STARTING_POINT + 2, "            - Hyperion Goodiebag -", tda, 2, 0xAF70C3, false, true);
		addText(STARTING_POINT + 3, "", tda, 2, 0xAF70C3, false, true);
		addHoverButtonWSpriteLoader(STARTING_POINT + 4, 1394, 88, 36, "Claim Prize!", -1, STARTING_POINT + 5, 1);
		addHoveredImageWSpriteLoader(STARTING_POINT + 5, 1395, 88, 36, STARTING_POINT + 6);
		addText(STARTING_POINT + 7, "Claim", tda, 2, 0xAF70C3, true, true);
		addText(STARTING_POINT + 8, "", tda, 2, 0xAF70C3, false, false);
		addText(STARTING_POINT + 10, "", tda, 1, 0xAF70C3, true, false);
		addCloseButtonSmall(STARTING_POINT + 71, STARTING_POINT + 72, STARTING_POINT + 73);
		main.totalChildren(12 + ((ROWS * COLUMNS) * 2));
		main.child(0, STARTING_POINT + 1, 75, 10);
		main.child(1, STARTING_POINT + 2, 145, 20);
		main.child(2, STARTING_POINT + 3, 305, 125);
		main.child(3, STARTING_POINT + 4, 326, 165);
		main.child(4, STARTING_POINT + 5, 326, 165);
		main.child(5, STARTING_POINT + 7, 371, 170);
		main.child(6, STARTING_POINT + 8, 315, 165);
		main.child(7, STARTING_POINT + 9, 315, 180);
		main.child(8, STARTING_POINT + 10, 367, 185);
		main.child(49, STARTING_POINT + 70, 100, 63);
		main.child(50, STARTING_POINT + 71, 417, 21);
		main.child(51, STARTING_POINT + 72, 417, 21);

		RSInterface line = addInterface(STARTING_POINT + 9);
		line.type = -1;
		line.width = -1;
		line.height = -1;

		int childStart = 9;
		int yPos = 55;
		for (int y = 0; y < ROWS; y++) {
			int xPos = 93;
			for (int x = 0; x < COLUMNS; x++) {
				int id = x + y * COLUMNS;
				addButtonWSpriteLoader(STARTING_POINT + 11 + id, 3037, "Choose"); // this is why i needed the order so i can remember easily
				setBounds(STARTING_POINT + 11 + id, xPos, yPos, childStart + id, main);
				xPos += 57;
			}
			yPos += 50;
		}

		int xPos1 = 116;
		int yPos1 = 69;
		int childStart1 = 29;
		for (int i = 1; i <= 20; i++) {
			addText(STARTING_POINT + 32 + i, String.valueOf(i), tda, 2, 0xAF70C3, true, false);
			main.child(childStart1 + (i - 1), STARTING_POINT + 32 + i, xPos1, yPos1);
			xPos1 += 57;
			if (i % 4 == 0) {
				xPos1 = 116;
				yPos1 += 50;
			}
		}

		addToItemGroup(STARTING_POINT + 70, 4, 5, 25, 18, true, new String[]{null, null, null, null, null});


	}

	public static void customCollectionLog(TextDrawingArea[] tda) {
		final int STARTING_POINT = 30360;
		RSInterface main = addInterface(STARTING_POINT);
		addSpriteLoader(STARTING_POINT + 1, 1409);
		addHoverButtonWSpriteLoader(STARTING_POINT + 2, 1410, 159, 29, "Search Npc", -1, STARTING_POINT + 3, 1);
		addHoveredImageWSpriteLoader(STARTING_POINT + 3, 1411, 159, 29, STARTING_POINT + 4);
		addText(STARTING_POINT + 5, "Npc", tda, 2, 0xAF70C3, false, true);
		addText(STARTING_POINT + 6, "Search Npc", tda, 1, 0xAF70C3, true, true);
		addText(STARTING_POINT + 7, "@gre@Drops: @gre@0/20", tda, 0, 0xAF70C3, false, true);
		addText(STARTING_POINT + 8, "Monster Name", tda, 2, 0xAF70C3, false, true);
		addText(STARTING_POINT + 9, "@red@Kills: @red@159", tda, 0, 0xAF70C3, false, true);
		addText(STARTING_POINT + 10, "", tda, 2, 0xAF70C3, false, true);
		addCloseButtonSmall(STARTING_POINT + 11, STARTING_POINT + 12, STARTING_POINT + 13);
		addText(STARTING_POINT + 14, "Athens Progression", tda, 2, 0xAF70C3, true, true);
		//RSInterface.interfaceCache[STARTING_POINT + 15].hideStackSize = true;
		main.totalChildren(14);
		main.child(0, STARTING_POINT + 1, 10 + 27, 10 + 4);
		main.child(1, STARTING_POINT + 2, 19 + 27, 46+ 4);
		main.child(2, STARTING_POINT + 3, 19 + 27, 46+ 4);
		main.child(3, STARTING_POINT + 5, 75 + 27, 83+ 4);
		main.child(4, STARTING_POINT + 6, 99 + 27, 53+ 4);
		main.child(5, STARTING_POINT + 7, 345 + 27, 75+ 4);
		main.child(6, STARTING_POINT + 8, 188 + 27, 51+ 4);
		main.child(7, STARTING_POINT + 9, 188 + 27, 75+ 4);
		main.child(8, STARTING_POINT + 10, 367 + 27, 52+ 4);
		main.child(9, STARTING_POINT + 11, 437, 19+ 4);
		main.child(10, STARTING_POINT + 12, 437, 19+ 4);
		main.child(11, STARTING_POINT + 14, 223 + 27, 20+ 4);
		main.child(12, STARTING_POINT + 30, 3 + 27, 103+ 4);
		main.child(13, STARTING_POINT + 25, 204 + 8, 96);

		RSInterface items = addInterface(STARTING_POINT + 25);
		items.totalChildren(1);
		items.height = 206;
		items.width = 224;
		items.scrollMax = 280;
		addToItemGroup(STARTING_POINT + 15, 6, 10, 6, 6, true, new String[]{null, null, null, null, null});
		items.child(0, STARTING_POINT + 15, 0, 5);

		RSInterface scroll = addInterface(STARTING_POINT + 30);
		scroll.totalChildren(300);
		scroll.width = 158;
		scroll.height = 195;
		scroll.scrollMax = 1100;

		int spriteY = 0;
		for (int i = 0; i < 150; i++) {
			addSpriteLoader(STARTING_POINT + 31 + i, i % 2 != 0 ? 1412 : 1413);
			scroll.child(i, STARTING_POINT + 31 + i, 19, spriteY);
			spriteY += 22;
		}
		int textY = 4;
		for (int i = 0; i < 150; i++) {
			addText(STARTING_POINT + 200 + i, "", fonts, 1, 0xAF70C3, false, true, ColorConstants.WHITE, "Select", 130);
			//public static void addText(int id, String text, TextDrawingArea tda[], int idx, int color, boolean center,
			//boolean ambient, int hoverColour, String tooltip, int widthHover) {
			scroll.child(150 + i, STARTING_POINT + 200 + i, 23, textY);
			textY += 22;
		}

	}

	public static void addHoverableText(int id, String text, TextDrawingArea[] tda, int idx, int color, int width, int height) {
		RSInterface Tab = addTabInterface(id);
		Tab.parentID = id;
		Tab.id = id;
		Tab.atActionType = 1;
		Tab.type = 4;
		Tab.width = width;
		Tab.height = height;
		Tab.contentType = 0;
		Tab.hoverType = 0;
		Tab.centerText = false;
		Tab.textDrawingAreas = tda[idx];
		Tab.message = text;
		Tab.disabledColor = color;
	}

	public static void addHoverableText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, boolean center, boolean textShadowed, int width, int disabledColor, int enabledColor) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = 13;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.inputText = "";
		rsinterface.disabledColor = disabledColor;
		rsinterface.enabledColor = 0;
		rsinterface.disabledMouseOverColor = enabledColor;
		rsinterface.enabledMouseOverColor = 0;
		rsinterface.tooltip = tooltip;
	}


	public static void dropGroup(int id, int w, int h, int x, int y) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.type = 2;

		rsi.inv[0] = 6200;
		rsi.invStackSizes[0] = 2;
	}

	public static void DropRateTimerInterface(TextDrawingArea[] tda) {

		RSInterface main = addInterface(48308);
		addSpriteLoader(48309, 3347);
		addText(48310, "", tda, 0, ColorConstants.SKY_BLUE, false, true);
		addText(48311, "", tda, 0, ColorConstants.SNOW_WHITE, false, true);
		main.totalChildren(3);
		if (!Client.isHorizontalLayout) {
			main.child(0, 48309, 465, 88);
			main.child(1, 48310, 467, 121);
			main.child(2, 48311, 454, 105);
		} else {
			main.child(0, 48309, 100, 88);
			main.child(1, 48310, 100, 121);
			main.child(2, 48311, 100, 105);
		}
	}

    public static void necroTimer(TextDrawingArea[] tda) {
        RSInterface main = addInterface(48312);
        addText(48313, "0s", tda, 0, ColorConstants.SKY_BLUE, false, true);
        main.totalChildren(1);
        main.child(0, 48313, 465, 88);
    }

	public static void DamageBoostTimerInterface(TextDrawingArea[] tda) {
		RSInterface main = addInterface(48304);
		addSpriteLoader(48305, 3346);
		addText(48306, "", tda, 0, ColorConstants.SKY_BLUE, false, true);
		addText(48307, "", tda, 0, ColorConstants.SNOW_WHITE, false, true);
		main.totalChildren(3);
		if (!Client.isHorizontalLayout) {
			main.child(0, 48305, 464, 138);
			main.child(1, 48306, 465, 171);
			main.child(2, 48307, 454, 155);
		} else {
			main.child(0, 48305, 152, 88);
			main.child(1, 48306, 150, 121);
			main.child(2, 48307, 150, 105);
		}
	}

	public static void OverloadTimerInterface(TextDrawingArea[] tda) {
		RSInterface main = addInterface(48300);
		addSpriteLoader(48301, 3348);
		addText(48302, "", tda, 0, ColorConstants.SKY_BLUE, false, true);
		addText(48303, "", tda, 0, ColorConstants.SNOW_WHITE, false, true);
		main.totalChildren(3);
		if (!Client.isHorizontalLayout) {
			main.child(0, 48301, 465, 188);
			main.child(1, 48302, 466, 221);
			main.child(2, 48303, 454, 205);
		} else {
			main.child(0, 48301, 202, 88);
			main.child(1, 48302, 201, 121);
			main.child(2, 48303, 200, 105);
		}
	}

	public static void pvmbingoInterface(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(45000); // Main interface ID
		int childId = 0;
		addSpriteLoader(45001, 5770); // line 913

		// Set the number of children for the interface (25 cells for item models + 25 for optional marked overlays)
		tab.totalChildren(26);
		tab.child(childId++, 45001, 100, 10);

		// Add the 5x5 grid (25 cells with item models)
		int baseX = 40; // Starting X position
		int baseY = 40; // Starting Y position
		int cellSize = 60; // Size of each cell (width/height)
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 5; col++) {
				// Item model for the cell
				int itemDisplayId = 45008 + (row * 5 + col); // Unique ID for item model (45008 to 45032)
				//addItemOnInterface(itemDisplayId, 7777, new String[]{});
				addToItemGroup(itemDisplayId, 5, 5, 10, 1, false, new String[]{null, null, null, null, null});
				tab.child(childId++, itemDisplayId, baseX + (col * cellSize) + 10, baseY + (row * cellSize) + 10);
			}
		}
	}


	public static void warriorGuild(TextDrawingArea[] tda) {
		RSInterface tab = addInterface(54500);
		addItemOnInterface(54503, 7777, new String[]{});
		addSpriteLoader(54501, 4670);

		addText(54502, "Shield", 0xff9933, true, true, -1, tda, 2);
		addText(54504, "Dropping:", 0xff9933, false, true, -1, tda, 0);

		tab.totalChildren(4);
		tab.child(0, 54501, 1, 247);
		tab.child(1, 54502, 75, 252);
		tab.child(2, 54503, 60, 292);
		tab.child(3, 54504, 54, 268);
	}

	public RSFontSystem rsFont;

	public int currentFrame;

	private static Archive aClass44;
	private static MRUNodes aMRUNodes_238;
	private static final MRUNodes aMRUNodes_264 = new MRUNodes(30);
	public static TextDrawingArea[] fonts;
	public static RSInterface[] interfaceCache;
	public InterfaceTextInput textInput;
	public boolean inputToggled = false;
	public String inputText = "";
	public String defaultText = "";
	public static RSInterface[] inputFields = new RSInterface[20];
	public boolean hasInputField = false;



	public String fillBarStatus;
	public int fillBarChance;
	public boolean fillBarActive = false;

	public static RSInterface addFillBar(int id, int width, int percentageTotal, int height, boolean vertical) {
		RSInterface rs = new RSInterface();
		interfaceCache[id] = rs;
		rs.id = id;
		rs.parentID = id;
		rs.type = 73;
		rs.atActionType = 0;
		rs.contentType = 0;
		rs.opacity = 0;
		rs.fillBarStatus = "";
		rs.hoverType = 0;
		rs.percentageCompleted = 0;
		rs.percentageTotal = percentageTotal;
		rs.verticalBar = vertical;
		rs.width = width;
		rs.height = height;
		return rs;
	}

	public int[] itemsToDraw;

	public static void drawItemArray(int interfaceId, int[] items) {
		RSInterface rsi = addInterface(interfaceId);
		rsi.type = 282;
		rsi.itemsToDraw = items;

	}


	public static void addNpc(int ID, int npcId) {
		RSInterface petCanvas = interfaceCache[ID] = new RSInterface();
		petCanvas.id = ID;
		petCanvas.parentID = ID;
		petCanvas.type = 6;
		petCanvas.atActionType = 0;
		petCanvas.contentType = 3291;
		petCanvas.width = 136;
		petCanvas.height = 168;
		// petCanvas.transparancy = 0;
		petCanvas.modelZoom = 1400;
		petCanvas.modelRotationY = 150;
		petCanvas.modelRotationX = 0;
		petCanvas.disabledAnimationId = -1;
		petCanvas.enabledAnimationId = -1;
		petCanvas.npcDisplay = npcId;
	}

	public static void addProperNpc(int ID, int npcId) {
		RSInterface petCanvas = interfaceCache[ID] = new RSInterface();
		petCanvas.id = ID;
		petCanvas.parentID = ID;
		petCanvas.type = 6;
		petCanvas.atActionType = 0;
		petCanvas.contentType = 3600;
		petCanvas.width = 136;
		petCanvas.height = 168;
		// petCanvas.transparancy = 0;
		petCanvas.modelZoom = 600;
		petCanvas.modelRotationY = 150;
		petCanvas.modelRotationX = 100;
		petCanvas.disabledAnimationId = -1;
		petCanvas.enabledAnimationId = -1;
		petCanvas.npcDisplay = npcId;
		petCanvas.contentId = npcId;
	}

	public static void addNpcOld(int ID, int npcId) {
		RSInterface petCanvas = interfaceCache[ID] = new RSInterface();
		petCanvas.id = ID;
		petCanvas.parentID = ID;
		petCanvas.type = 6;
		petCanvas.atActionType = 0;
		petCanvas.contentType = 32921;
		petCanvas.width = 136;
		petCanvas.height = 168;
		// petCanvas.transparancy = 0;
		petCanvas.modelZoom = 1400;
		petCanvas.modelRotationY = 150;
		petCanvas.modelRotationX = 0;
		petCanvas.disabledAnimationId = -1;
		petCanvas.enabledAnimationId = -1;
		petCanvas.npcDisplay = npcId;
	}
	public static void gamblingInterface(TextDrawingArea[] tda) {

		RSInterface main = addInterface(57150);

		// main sprite
		addSpriteLoader(57151, 1330);
		main.totalChildren(21);
		main.child(0, 57151, 10, 10);
		main.child(1, 57152, 235, 63);
		main.child(2, 57153, 235, 113);
		main.child(3, 57154, 17, 20);
		main.child(4, 57155, 72, 177);
		main.child(5, 57156, 200, 177);

		main.child(6, 57157, 200, 200);
		main.child(7, 57158, 200, 235);
		main.child(8, 57159, 200, 270);
		// scroll(right side item scroll)
		main.child(9, 57170, 0, 45);
		// scroll (left side item scroll)
		main.child(10, 57180, 0, 45);
		main.child(11, 57175, 191, 150);

		// game types
		main.child(12, 57190, 20, 200);
		main.child(13, 57191, 20, 220);

		main.child(14, 57192, 43, 202);
		main.child(15, 57193, 43, 222);

		main.child(16, 57194, 20, 240);

		main.child(17, 57195, 43, 242);

		main.child(18, 57196, 20, 260);

		main.child(19, 57197, 43, 262);

		main.child(20, 57198, 205, 295);

		// accept, deciline clickable text
        addClickableText(57152, "Accept", "Select", tda, 1, 0x00C000, 45, 13);
		addClickableText(57153, "Deciline", "Select", tda, 1, 0xC00000, 45, 13);

		// gambling with child
		addText(57154, "Gambling with: name", 0xff9933, false, true, 52, tda, 2);

		// Select a game type child
		addText(57155, "Select a game type", 0xff9933, true, true, 52, tda, 1);
		// -Player name has accepted-
		addText(57175, "---a", ColorConstants.WHITE, false, true, 52, tda, 0);

		// rules for "game"
		final String rules = " \"Game Name\" ";
		addText(57156, "Rules for" + rules, 0xff9933, false, true, 52, tda, 1);
		// description fields(under rules)
		addText(57157, "First Line description(can use a extra line)", ColorConstants.WHITE, false, true, 52, tda, 1);
		addText(57158, "Second Line(can use a extra line)", ColorConstants.WHITE, false, true, 52, tda, 1);
		addText(57159, "Third Line(can use a extra line)", ColorConstants.WHITE, false, true, 52, tda, 1);

		// right side item scroll
		RSInterface rightScroll = addInterface(57170);
		rightScroll.width = 154;
		rightScroll.height = 121;
		rightScroll.scrollMax = 250;
		rightScroll.totalChildren(1);
		rightScroll.child(0, 57171, 18, 0);
		addToItemGroup(57171, 3, 10, 14, 16, true, new String[]{"Remove 1", "Remove 5", "Remove 10", "Remove All", "Remove X"});

		// left side item scroll
		RSInterface leftScroll = addInterface(57180);
		leftScroll.width = 475;
		leftScroll.height = 121;
		leftScroll.scrollMax = 250;
		leftScroll.totalChildren(1);
		leftScroll.child(0, 57181, 340, 0);
		addToItemGroup(57181, 3, 10, 14, 16, true, new String[]{"Remove 1", "Remove 5", "Remove 10", "Remove All", "Remove X"});

		// game types
		// addConfigButtonWSpriteLoader(57190, 57150, 1077, 1078, 119, 15, "Select
		// 55x2(U host)", 1700, 1, 1430);
		// addConfigButtonWSpriteLoader(57191, 57150, 1077, 1078, 119, 15, "Select
		// 55x2(Other player hosts)", 1701, 1, 1430);
		// int id, int sprite, int setconfig, int width, int height, String s
		addToggleButton1(57190, 1332, 1700, 119, 15, "Flower Poker");
		addToggleButton1(57191, 1332, 1701, 119, 15, "Dice Duel(ft3)");

		// game type descriptions
		addText(57192, "Flower poker", ColorConstants.WHITE, false, true, 52, tda, 0);
		addText(57193, "Dice Duel(ft3)", ColorConstants.WHITE, false, true, 52, tda, 0);

		addToggleButton1(57194, 1332, 1702, 119, 15, "Blackjack");
		addText(57195, "Blackjack (playername hosts)", ColorConstants.WHITE, false, true, 52, tda, 0);
		addToggleButton1(57196, 1332, 1703, 119, 15, "Blackjack");
		addText(57197, "55x2 (playername hosts)", ColorConstants.WHITE, false, true, 52, tda, 0);

		addText(57198, "NOTE: Who hosts depends on who accepts first", ColorConstants.LIME, false, true, 52, tda, 0);

	}



	public static void addToggleButton1(int id, int sprite, int setconfig, int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.disabledSprite = Client.spritesMap.get(sprite);
		rsi.enabledSprite = Client.spritesMap.get(sprite + 1);
		rsi.requiredValues = new int[1];
		rsi.requiredValues[0] = 1;
		rsi.valueCompareType = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.hoverType = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

    public static void addConfigButton(int id, int disabled, int enabled, int setconfig, int width, int height, String s) {
        RSInterface rsi = addInterface(id);
        rsi.disabledSprite = Client.spritesMap.get(disabled);
        rsi.enabledSprite = Client.spritesMap.get(enabled);
        rsi.requiredValues = new int[1];
        rsi.requiredValues[0] = 1;
        rsi.valueCompareType = new int[1];
        rsi.valueCompareType[0] = 1;
        rsi.valueIndexArray = new int[1][3];
        rsi.valueIndexArray[0][0] = 5;
        rsi.valueIndexArray[0][1] = setconfig;
        rsi.valueIndexArray[0][2] = 0;
        rsi.atActionType = 4;
        rsi.width = width;
        rsi.hoverType = -1;
        rsi.parentID = id;
        rsi.id = id;
        rsi.type = 5;
        rsi.height = height;
        rsi.tooltip = s;
    }

	public static void addOutlinedColorBox(int id, int color, int width, int height, int transparency) {
		RSInterface tab = addInterface(id);
		tab.width = width;
		tab.height = height;
		tab.type = 19;
		tab.opacity = (byte) transparency;
	}

	private boolean petFlag;

	public static void setScrollableItems(RSInterface tab, int[][] loot) {
		RSInterface parent = interfaceCache[tab.parentID];
		tab.atActionType = 1430;
		tab.contentType = 1430;
		tab.scrollMax = ((loot.length * 32) + (loot.length * tab.invSpritePadX)) - parent.width;
		for (int i = 0; i < loot.length; ++i) {
			tab.inv[i] = loot[i][0] + 1;
			tab.invStackSizes[i] = loot[i][1];
		}
	}


	private static final int CLOSE_BUTTON = 1238, CLOSE_BUTTON_HOVER = 1239;

	public int npcDisplay = 0;

	public int contentId;

	public static void drawNpcOnInterface(int childId, int npcId, int zoom, boolean pet) {
		final RSInterface rsInterface = interfaceCache[childId] = new RSInterface();
		rsInterface.id = childId;
		rsInterface.parentID = childId;
		rsInterface.type = 6;
		rsInterface.atActionType = 3291;
		rsInterface.contentId = npcId;
		rsInterface.petFlag = pet;
		rsInterface.width = 136;
		rsInterface.height = 168;
		rsInterface.opacity = 0;
		rsInterface.disabledMouseOverColor = 0;
		rsInterface.modelZoom = zoom;
		rsInterface.modelRotationY = 150; // these should be right, didnt cross reference but yh
		rsInterface.modelRotationX = 0;
		rsInterface.npcDisplay = rsInterface.npcDisplay = EntityDef.get(npcId).standAnimation;
	}

	public static void drawNpcOnInterface(int childId, int npcId, int zoom) {
		final RSInterface rsInterface = interfaceCache[childId] = new RSInterface();
		rsInterface.id = childId;
		rsInterface.parentID = childId;
		rsInterface.type = 6;
		rsInterface.atActionType = 3291;
		rsInterface.contentType = 3291;
		rsInterface.contentId = npcId;
		rsInterface.width = 136;
		rsInterface.height = 168;
		rsInterface.opacity = 0;
		rsInterface.disabledMouseOverColor = 0;
		rsInterface.modelZoom = zoom;
		rsInterface.modelRotationY = 150;
		rsInterface.modelRotationX = 0;
		rsInterface.npcDisplay =  EntityDef.get(npcId).walkAnimation;
	}

	public static void addTextInput(int id, int w, int h, int text, String defaultText, RSInterface parent, InterfaceTextInput input) {
		RSInterface i = addInterface(id);
		i.type = 77;
		i.width = w;
		i.height = h;
		i.rsFont = newFonts[text];
		i.textInput = input;
		i.tooltip = "Toggle input";
		i.atActionType = 1;
		i.contentType = 0;
		i.inputText = i.defaultText = defaultText;
		parent.hasInputField = true;
		for (int b = 0; b < 20; b++) {
			if (inputFields[b] == null) {
				inputFields[b] = i;
				break;
			}
		}
	}

	public static void addActionButton(int id, int sprite, int sprite2, int width, int height, String s) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.disabledSprite = Client.spritesMap.get(sprite);

		if (sprite2 != -1) {
			rsi.enabledSprite = Client.spritesMap.get(sprite == sprite2 ? sprite + 1 : sprite2);
		}

		rsi.tooltip = s;
		rsi.contentType = 0;
		rsi.atActionType = 1;
		rsi.width = width;
		rsi.hoverType = 52;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
	}

	public static void addClanChatListTextWithOptions(int id, String text, String ignore, boolean owner, TextDrawingArea[] tda, int idx, int color, int width, int height) {
		RSInterface Tab = addTabInterface(id);
		Tab.parentID = id;
		Tab.id = id;
		Tab.type = 4;
		Tab.atActionType = 1;
		Tab.width = width;
		Tab.height = height;
		Tab.contentType = 0;
		Tab.hoverType = 0;
		// Tab.mOverInterToTrigger = -1;
		Tab.centerText = false;
		// Tab.enabledText = true;
		Tab.textDrawingAreas = tda[idx];
		Tab.message = text;
		// Tab.aString228 = "";
		Tab.disabledColor = color;
		String s = Tab.message;
		if (s.contains("<img")) {
			int prefix = s.indexOf("<img=");
			int suffix = s.indexOf(">");
			s = s.replaceAll(s.substring(prefix + 5, suffix), "");
			s = s.replaceAll("</img>", "");
			s = s.replaceAll("<img=>", "");
		}
		if (!s.equals(ignore)) {
			if (owner) {
				Tab.actions = new String[]{"Promote to Recruit @or1@" + s + "", "Promote to Corporal @or1@" + s + "", "Promote to Sergeant @or1@" + s + "", "Promote to Lieutenant @or1@" + s + "", "Promote to Captain @or1@" + s + "", "Promote to General @or1@" + s + "", "Demote @or1@" + s + "", "Kick @or1@" + s + ""};
			} else {
				Tab.actions = new String[]{"Kick @or1@" + s + ""};
			}
		}
	}

	public static void addToItemGroup(RSInterface rsi, int w, int h, int x, int y, boolean actions, String action1, String action2, String action3) {
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.actions = new String[5];
		if (actions) {
			rsi.actions[0] = action1;
			rsi.actions[1] = action2;
			rsi.actions[2] = action3;
		}
		rsi.type = 2;
	}
	
	protected static Sprite imageLoader(int i, String s) {
		long l = (TextUtilities.method585(s) << 8) + i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null)
			return sprite;
		try {
			sprite = new Sprite(s + " " + i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception exception) {
			return null;
		}
		return sprite;
	}

	public static void addCurseWithTooltip(int i, int configId, int configFrame, int requiredValues, int prayerSpriteID, String PrayerName, int Hover) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 4;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.hoverType = Hover;
		Interface.disabledSprite = Client.spritesMap.get(927);
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 1;
		Interface.requiredValues[0] = configId;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 5;
		Interface.valueIndexArray[0][1] = configFrame;
		Interface.valueIndexArray[0][2] = 0;
		Interface.tooltip = "Activate@lre@ " + PrayerName;
		Interface = addTabInterface(i + 1);
		Interface.id = i + 1;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.disabledSprite = Client.spritesMap.get(947 + prayerSpriteID);
		Interface.enabledSprite = Client.spritesMap.get(927 + prayerSpriteID);

		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 2;
		Interface.requiredValues[0] = requiredValues;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 2;
		Interface.valueIndexArray[0][1] = 5;
		Interface.valueIndexArray[0][2] = 0;
	}

	public static void addTooltip(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.type = 0;
		rsi.interfaceShown = true;
		rsi.hoverType = -1;
		addTooltipBox(id + 1, text);
		rsi.totalChildren(1);
		rsi.child(0, id + 1, 0, 0);
	}
	
	private static void addTooltipBox(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 9;
		rsi.tooltipBoxText = text;
	}
	
	public static void addHolyTooltipWithSprite(int i, int configID, int configFrame,
			int requiredValue, int prayerSpriteID, String PrayerName, int hover) {
		
	}

	
	public static void addHolyWithTooltip(int i, int configId, int configFrame, 
			int requiredValues, int prayerSpriteID, String PrayerName, int Hover) {
		RSInterface Interface = addTabInterface(i);
		Interface.id = i;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 4;
		Interface.contentType = 0;
		Interface.opacity = 0;
		Interface.hoverType = Hover;
		Interface.disabledSprite = Client.spritesMap.get(927);//background sprite for prayer
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 1;
		Interface.requiredValues[0] = configId;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 5;
		Interface.valueIndexArray[0][1] = configFrame;
		Interface.valueIndexArray[0][2] = 0;
		Interface.tooltip = "Activate@lre@ " + PrayerName;
		Interface = addTabInterface(i + 1);
		Interface.id = i + 1;
		Interface.parentID = 22500;
		Interface.type = 5;
		Interface.atActionType = 0;
		Interface.contentType = 0;
		Interface.opacity = 0;
		if(prayerSpriteID != -1) {
		Interface.disabledSprite = Client.spritesMap.get(prayerSpriteID);
		Interface.enabledSprite = Client.spritesMap.get(prayerSpriteID);
		}
		Interface.width = 34;
		Interface.height = 34;
		Interface.valueCompareType = new int[1];
		Interface.requiredValues = new int[1];
		Interface.valueCompareType[0] = 2;
		Interface.requiredValues[0] = requiredValues + 1;
		Interface.valueIndexArray = new int[1][3];
		Interface.valueIndexArray[0][0] = 2;
		Interface.valueIndexArray[0][1] = 5;
		Interface.valueIndexArray[0][2] = 0;
	}


	public static void setBoundry(int frame, int ID, int X, int Y, RSInterface RSInterface) {
		RSInterface.children[frame] = ID;
		RSInterface.childX[frame] = X;
		RSInterface.childY[frame] = Y;
	}

	protected static final int[] SETTING_CONFIGS = {516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526, 527, 528, 529, 530, 531, 532};

	public static void addCheckmarkHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId, int hoverSpriteId2, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.opacity = 0;
		hover.hoverType = hoverid;
		hover.disabledSprite = Client.spritesMap.get(spriteId);
		hover.enabledSprite = Client.spritesMap.get(spriteId2);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.interfaceShown = true;
		hover.hoverType = -1;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, configId, configFrame);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 0, 0, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);

	}

	public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId, int hoverSpriteId2, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.opacity = 0;
		hover.hoverType = hoverid;
		hover.disabledSprite = Client.spritesMap.get(spriteId);
		hover.enabledSprite = Client.spritesMap.get(spriteId2);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.interfaceShown = true;
		hover.hoverType = -1;
		addSprite(hoverId2, hoverSpriteId, hoverSpriteId2, configId, configFrame);
		addHoverBox(hoverId3, interfaceID, hoverDisabledText, hoverEnabledText, configId, configFrame);
		setChildren(2, hover);
		setBounds(hoverId2, 15, 60, 0, hover);
		setBounds(hoverId3, X, Y, 1, hover);
	}

	public static void addBankHover(int interfaceID, int actionType, int hoverid, int spriteId, int spriteId2, String NAME, int Width, int Height, int configFrame, int configId, String Tooltip, int hoverId2, int hoverSpriteId, int hoverSpriteId2, String hoverSpriteName, int hoverId3, String hoverDisabledText, String hoverEnabledText, int X, int Y, int sprite1, int sprite2) {
		RSInterface hover = addTabInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = actionType;
		hover.contentType = 0;
		hover.hoverType = hoverid;
		hover.enabledSprite = Client.spritesMap.get(sprite1);
		hover.disabledSprite = Client.spritesMap.get(sprite2);
		hover.width = Width;
		hover.tooltip = Tooltip;
		hover.height = Height;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
		hover = addTabInterface(hoverid);
		hover.parentID = hoverid;
		hover.id = hoverid;
		hover.type = 0;
		hover.atActionType = 0;
		hover.width = 550;
		hover.height = 334;
		hover.interfaceShown = true;
		hover.hoverType = -1;
	}

	public static void addClickableTextHeight(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow, int width, int height) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 1;
		tab.width = width;
		tab.height = height;
		tab.contentType = 0;
		// tab.aByte254 = 0;
		// tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.enabledMessage = "";
		tab.disabledColor = color;
		tab.enabledColor = 0;
		tab.disabledMouseOverColor = 0xffffff;
		tab.enabledMouseOverColor = 0;
		tab.tooltip = tooltip;
	}

	public static void addSprite(int ID, int i, int i2, String name, int configId, int configFrame, int sprite1, int sprite2) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		if (name == null) {
			/*
			 * Tab.itemSpriteZoom1 = -1; Tab.itemSpriteId1 = i; Tab.itemSpriteZoom2 = 70;
			 * Tab.itemSpriteId2 = i2;
			 */
		} else {
			// Tab.disabledSprite = imageLoader(i, name);
			// Tab.enabledSprite = imageLoader(i2, name);
			Tab.enabledSprite = Client.spritesMap.get(sprite1);
			Tab.disabledSprite = Client.spritesMap.get(sprite2);
		}
	}

	public static void addPet(int ID) {
		RSInterface petCanvas = interfaceCache[ID] = new RSInterface();
		petCanvas.id = ID;
		petCanvas.parentID = ID;
		petCanvas.type = 6;
		petCanvas.atActionType = 0;
		petCanvas.contentType = 3291;
		petCanvas.width = 136;
		petCanvas.height = 168;
		petCanvas.hoverType = 0;
		petCanvas.modelZoom = 875;
		petCanvas.modelRotationY = 40;
		petCanvas.modelRotationX = 1800;
		petCanvas.mediaType = 2;
		petCanvas.mediaID = 4000;
	}

	public static void itemGroupAutoScroll(int id, int itemsId, int w, int h, int x, int y, int viewableColumns,
										   String[] actions, boolean vertical) {
		RSInterface items = RSInterface.addInterface(id);
		RSInterface rsi = addInterface(itemsId);
		rsi.parentID = id;
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.type = 2;
		rsi.contentType = 6060;
		rsi.viewableColumns = viewableColumns; // reused variable
		int scrollItemAmount;

		rsi.verticalBar = vertical;
		rsi.actions = actions;
		if (vertical ) {
			scrollItemAmount = w - viewableColumns;
			rsi.scrollMax = (32 + y) * scrollItemAmount;
			items.width = 32 * w + (w - 1) * x;
			items.height = 32 * viewableColumns + (viewableColumns - 1) * y;
		} else {
			scrollItemAmount = h - viewableColumns;
			rsi.scrollMax = (32 + x) * scrollItemAmount;
			items.width = 32 * viewableColumns + (viewableColumns - 1) * x;
			items.height = 32 * h + (h - 1) * y;
		}
		items.totalChildren(1);
		items.child(0, itemsId, 0, 0);
	}

	public static void itemGroupAutoScroll(int id, int itemsId, int w, int h, int x, int y, int viewableColumns,
										   String[] actions, boolean vertical, int maxWidth) {
		RSInterface items = RSInterface.addInterface(id);
		RSInterface rsi = addInterface(itemsId);
		rsi.parentID = id;
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.type = 2;
		rsi.contentType = 6060;
		rsi.viewableColumns = viewableColumns; // reused variable
		int scrollItemAmount;

		rsi.verticalBar = vertical;
		rsi.actions = actions;
		if (vertical ) {
			scrollItemAmount = w - viewableColumns;
			rsi.scrollMax = (32 + y) * scrollItemAmount;
			items.width = maxWidth;
			items.height = 32 * viewableColumns + (viewableColumns - 1) * y;
		} else {
			scrollItemAmount = h - viewableColumns;
			rsi.scrollMax = (32 + x) * scrollItemAmount;
			items.width = maxWidth;
			items.height = 32 * h + (h - 1) * y;
		}
		items.totalChildren(1);
		items.child(0, itemsId, 0, 0);
	}

	public static void itemGroupAutoScroll(int id, int itemsId, int w, int h, int x, int y, int viewableColumns,
										   String[] actions, boolean vertical, int maxWidth, int maxHeight) {
		RSInterface items = RSInterface.addInterface(id);
		RSInterface rsi = addInterface(itemsId);
		rsi.parentID = id;
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.type = 2;
		rsi.contentType = 6060;
		rsi.viewableColumns = viewableColumns; // reused variable
		int scrollItemAmount;

		rsi.verticalBar = vertical;
		rsi.actions = actions;
		if (vertical) {
			scrollItemAmount = w - viewableColumns;
			rsi.scrollMax = (32 + y) * scrollItemAmount;
			items.width = maxWidth;
			items.height = maxHeight;
		} else {
			scrollItemAmount = h - viewableColumns;
			rsi.scrollMax = (32 + x) * scrollItemAmount;
			items.width = maxWidth;
			items.height = maxHeight;
		}
		items.totalChildren(1);
		items.child(0, itemsId, 0, 0);
	}

	public static void addFamiliarHead(int interfaceID, int width, int height, int zoom) {
		RSInterface rsi = addTabInterface(interfaceID);
		rsi.type = 6;
		rsi.mediaType = 2;
		rsi.mediaID = 4000;
		rsi.modelZoom = zoom;
		rsi.modelRotationY = 40;
		rsi.modelRotationX = 1800;
		rsi.height = height;
		rsi.width = width;
	}

	public static void addButton(int id, int sid, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.disabledSprite = Client.spritesMap.get(sid);
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addSkillChatSprite(int id, int skill) {
		addSpriteLoader(id, 755 + skill);
	}

	public static void addRectangle(int id, int opacity, int color, boolean filled, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.disabledColor = color;
		tab.filled = filled;
		tab.id = id;
		tab.parentID = id;
		tab.type = 3;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.width = width;
		tab.height = height;
	}

	public static void addRectangle(int id, int width, int height, int colour, int alpha, boolean filled) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.disabledColor = colour;
		tab.filled = filled;
		tab.id = id;
		tab.parentID = id;
		tab.type = 3;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) alpha;
		tab.width = width;
		tab.height = height;

	}

	public static void addBankItem(int index) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[5];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		rsi.invSpritePadX = 24;
		rsi.invSpritePadY = 24;
		rsi.height = 5;
		rsi.width = 6;
		rsi.id = index;
		rsi.type = 2;
		rsi.hideStackSize = true;
		rsi.hideExamine = true;
	}

	public static void itemGroup1(int id, int w, int h, int x, int y, boolean drag, boolean examine) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		/*
		 * rsi.aBoolean235 = drag; rsi.examine = examine;
		 */
		rsi.type = 2;
	}

	public static void addButtonWSpriteLoader(int id, int sprite, String tooltip, int w, int h) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (sprite != -1) {
			tab.disabledSprite = Client.spritesMap.get(sprite);
			tab.enabledSprite = Client.spritesMap.get(sprite);
		}
		tab.width = w;
		tab.height = h;
		tab.tooltip = tooltip;
	}

	public static void addHoverButtonWSpriteLoader(int i, int spriteId, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void createHover(int id, int x, int width) {
		RSInterface hover = addInterface(id);
		hover.type = 10;
		hover.contentType = x;
		hover.width = width;
		hover.height = 28;
	}

	public static void createSkillHover(int id, int x) {
		RSInterface hover = addInterface(id);
		hover.type = 10;
		hover.contentType = x;
		hover.width = 60;
		hover.height = 28;
	}

	public static void addSkillText(int id, boolean max, int skill) {
		RSInterface text = addInterface(id);
		text.id = id;
		text.parentID = id;
		text.type = 4;
		text.atActionType = 0;
		text.width = 15;
		text.height = 12;
		text.textDrawingAreas = fonts[0];
		text.textShadow = true;
		text.centerText = false;
        text.rightText = true;
		text.disabledColor = ColorConstants.VANGUARD_PURPLE;
		if (!max) {
			text.valueIndexArray = new int[1][];
			text.valueIndexArray[0] = new int[3];
			text.valueIndexArray[0][0] = 1;
			text.valueIndexArray[0][1] = skill;
			text.valueIndexArray[0][2] = 0;
		} else {
			text.valueIndexArray = new int[2][];
			text.valueIndexArray[0] = new int[3];
			text.valueIndexArray[0][0] = 1;
			text.valueIndexArray[0][1] = skill;
			text.valueIndexArray[0][2] = 0;
			text.valueIndexArray[1] = new int[1];
			text.valueIndexArray[1][0] = 0;
		}
		text.message = "%1";
	}

	public static void addButton(int id, int spriteId, int spriteId1, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 553;
		tab.atActionType = 1;
		tab.opacity = (byte) 0;
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.enabledSprite = Client.spritesMap.get(spriteId1);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.enabledSprite.myHeight;
		tab.tooltip = tooltip;
	}

	public static void addButton(int ID, int type, int hoverID, int dS, int eS, int W, int H, String text, int configFrame, int configId) {
		RSInterface rsinterface = addInterface(ID);
		rsinterface.id = ID;
		rsinterface.parentID = ID;
		rsinterface.type = 5;
		rsinterface.atActionType = type;
		rsinterface.opacity = 0;
		rsinterface.hoverType = hoverID;
		if (dS >= 0) {
			rsinterface.disabledSprite = Client.spritesMap.get(dS);
		}
		if (eS >= 0) {
			rsinterface.enabledSprite = Client.spritesMap.get(eS);
		}
		rsinterface.width = W;
		rsinterface.height = H;
		rsinterface.tooltip = text;
		rsinterface.interfaceShown = true;
		rsinterface.valueCompareType = new int[1];
		rsinterface.requiredValues = new int[1];
		rsinterface.valueCompareType[0] = 1;
		rsinterface.requiredValues[0] = configId;
		rsinterface.valueIndexArray = new int[1][3];
		rsinterface.valueIndexArray[0][0] = 5;
		rsinterface.valueIndexArray[0][1] = configFrame;
		rsinterface.valueIndexArray[0][2] = 0;
	}

	private static void addCacheSprite(int id, int sprite1, int sprite2, String sprites) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.disabledSprite = method207(sprite1, aClass44, sprites);
		rsi.enabledSprite = method207(sprite2, aClass44, sprites);
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}

	private final static int[] headAnims = new int[]{9846, 9742, 9827, 9841, 9851, 9745, 9785, 9805, 9810, 9815, 9820, 9860, 9835, 9845, 9850, 9855, 9864, 9851};
	public int plrJaw, gender;

	public static void addCharEquipment(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.opacity = 0;
		t.hoverType = 0;
		t.modelZoom = 560;
		t.modelRotationY = 500;
		t.modelRotationX = 0;
		t.disabledAnimationId = -1;
		t.enabledAnimationId = -1;
	}

	public static void addChar(int ID, int zoom) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.opacity = 0;
		t.hoverType = 0;
		t.modelZoom = zoom;
		t.modelRotationY = 500;
		t.modelRotationX = 0;
		t.disabledAnimationId = -1;
		t.enabledAnimationId = -1;
	}

	public static void addChar(int ID) {
		RSInterface t = interfaceCache[ID] = new RSInterface();
		t.id = ID;
		t.parentID = ID;
		t.type = 6;
		t.atActionType = 0;
		t.contentType = 328;
		t.width = 136;
		t.height = 168;
		t.opacity = 0;
		t.hoverType = 0;
		t.modelZoom = 560;
		t.modelRotationY = 150;
		t.modelRotationX = 0;
		t.disabledAnimationId = -1;
		t.enabledAnimationId = -1;
	}

	public static void addConfigButton(int ID, int pID, int spriteID, int clickedSpriteID, int width, int height, String hoveredText, int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.opacity = 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.disabledSprite = Client.spritesMap.get(spriteID);
		Tab.enabledSprite = Client.spritesMap.get(clickedSpriteID);
		Tab.tooltip = hoveredText;
	}

	public static void addConfigButton(int ID, int spriteID, int clickedSpriteID, int width, int height, String hoveredText, int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);

		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.opacity = 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.disabledSprite = Client.spritesMap.get(spriteID);
		Tab.enabledSprite = Client.spritesMap.get(clickedSpriteID);
		Tab.tooltip = hoveredText;
	}

	public static void dropdownMenu(int id, int width, int defaultOption, String[] options, Dropdown d) {
		dropdownMenu(id, width, defaultOption, options, d, new int[] { 0x0d0d0b, 0x464644, 0x473d32, 0x51483c, 0x787169 }, false);
	}

	public static void dropdownMenu(int id, int width, int defaultOption, String[] options, Dropdown d,
									int[] dropdownColours, boolean centerText) {
		RSInterface menu = addInterface(id);
		menu.type = TYPE_DROPDOWN;
		menu.dropdown = new DropdownMenu(width, false, defaultOption, options, d);
		menu.atActionType = OPTION_DROPDOWN;
		menu.dropdownColours = dropdownColours;
		menu.centerText = centerText;
	}

	public static RSInterface addHoverableSprite(int id, int mainSprite, int hoverSprite, boolean clickable, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 111;
		tab.atActionType = clickable ? 1 : 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverable = true;
		tab.disabledSprite = Client.spritesMap.get(mainSprite);
		tab.enabledSprite = Client.spritesMap.get(hoverSprite);
		tab.width = tab.disabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight;
		tab.tooltip = tooltip;
		return tab;
	}

	/**
	 * Adds newlines to a text for a certain TextDrawingArea so each line is never longer than width.
	 * Param tda The textDrawing Area for the text, basically the font
	 * Param text The text to convert to wrapped text
	 * Param width The width above which wrapping is applied
	 * Return The wrapped text
	 */
	public static String getWrappedText(TextDrawingArea tda, String text, int width) {
		if (text.contains("\\n") || tda.getTextWidth(text) <= width) {
			return text;
		}
		int spaceWidth = tda.getTextWidth(" ");
		StringBuilder result = new StringBuilder(text.length());
		StringBuilder line = new StringBuilder();
		int lineLength = 0;
		int curIndex = 0;
		while (true) {
			int spaceIndex = text.indexOf(' ', curIndex);
			int newLength = lineLength;
			boolean last = false;
			String curWord;
			if (spaceIndex < 0) {
				last = true;
				curWord = text.substring(curIndex);
			} else {
				curWord = text.substring(curIndex, spaceIndex);
				newLength += spaceWidth;
			}
			curIndex = spaceIndex + 1;
			int w = tda.getTextWidth(curWord);
			newLength += w;
			if (newLength > width) {
				result.append(line);
				result.append("\\n");
				line = new StringBuilder(curWord);
				line.append(' ');
				lineLength = w;
			} else {
				line.append(curWord);
				line.append(' ');
				lineLength = newLength;
			}
			if (last) {
				break;
			}
		}
		result.append(line);
		return result.toString();
	}

	public static RSInterface addWrappingText(int id, String text, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow, int width) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.atActionType = 0;
		tab.width = width;
		tab.height = 11;
		tab.type = 17;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = getWrappedText(tda[idx], text, tab.width);
		tab.enabledMessage = "";
		tab.disabledColor = color;
		return tab;
	}

	public static void addText(int childId, String text, int color, boolean center, boolean shadow, TextDrawingArea rsFont) {
		RSInterface rsi = RSInterface.addInterface(childId);
		rsi.parentID = childId;
		rsi.id = childId;
		rsi.type = 4;
		rsi.atActionType = 0;
		rsi.width = 0;
		rsi.height = 11;
		rsi.contentType = 0;
		rsi.opacity = 0;
		rsi.hoverType = -1;
		rsi.centerText = center;
		rsi.textShadow = shadow;
		rsi.textDrawingAreas = rsFont;
		rsi.message = "";
		rsi.disabledColor = color;
	}

	public static void addCloseButton1(int id) {
		RSInterface component = addHoverableSprite(id, 714, 715, true, "Close");
		component.atActionType = 3;
	}

	public static void addCloseButtonSmall(int child, int hoverChild, int hoverImageChild) {
		addHoverButtonWSpriteLoader(child, 714, 16, 16, "Close", 250, hoverChild, 3);
		addHoveredImageWSpriteLoader(hoverChild, 715, 16, 16, hoverImageChild);
	}

	public static void addBigButtonSmall(int child, int hoverChild, int hoverImageChild) {
		addHoverButtonWSpriteLoader(child, 1208, 21, 21, "Close", 250, hoverChild, 3);
		addHoveredImageWSpriteLoader(hoverChild, 1209, 21, 21, hoverImageChild);
	}





	public static void addTransparentLayer(int id, int layerColor, int layerTransparency) {
		RSInterface transparentLayer = addInterface(id);
		transparentLayer.layerColor = layerColor;
		transparentLayer.layerTransparency = layerTransparency;
		transparentLayer.type = 130;
		transparentLayer.setVisible(true);
	}

	public static void addTransparentLayer(int id, int layerColor, int layerTransparency, int width, int height) {
		RSInterface transparentLayer = addInterface(id);
		transparentLayer.layerColor = layerColor;
		transparentLayer.layerTransparency = layerTransparency;
		transparentLayer.type = 130;
		transparentLayer.width = width;
		transparentLayer.height = height;
	}

	public int layerColor = -1;
	public int layerTransparency = -1;

	public static final int TYPE_RGB_PICKER = 117;

	public RGBWidgetColorPicker rgbPicker;

	public static void addRGBPicker(int id, int width, int height, int hueWidth, int pickedCircleRadius, int hueCircleRadius, String tooltip) {
		RSInterface widget = addInterface(id);
		widget.rgbPicker = new RGBWidgetColorPicker(width, height, hueWidth, pickedCircleRadius, hueCircleRadius);
		widget.width = width + hueWidth;
		widget.height = height;
		widget.type = TYPE_RGB_PICKER;
		widget.atActionType = 9;
		widget.tooltip = tooltip;
	}

	public WidgetSlider widgetSlider;

	public static final int TYPE_SLIDER = 116;

	public static void addSlider(int id, int minValue, int maxValue, int defaultValue, int backgroundSprite, int handleSprite, String tooltip) {
		RSInterface widget = addInterface(id);
		Sprite background = Client.spritesMap.get(backgroundSprite);
		Sprite handle = Client.spritesMap.get(handleSprite);
		widget.widgetSlider = new WidgetSlider(minValue, maxValue, defaultValue, background, handle);
		widget.width = background.myWidth;
		widget.height = background.myHeight;
		widget.type = TYPE_SLIDER;
		widget.atActionType = 9;
		widget.tooltip = tooltip;

	}


	private static void customCombiner(TextDrawingArea[] tda) {
		final int STARTING_POINT = 30330;
		RSInterface main = addInterface(STARTING_POINT);
		addSpriteLoader(STARTING_POINT + 1, 1397);
		addHoverButtonWSpriteLoader(STARTING_POINT + 2, 1398, 127, 34, "Combine", -1, STARTING_POINT + 3, 1);
		addHoveredImageWSpriteLoader(STARTING_POINT + 3, 1399, 127, 34, STARTING_POINT + 4);
		addText(STARTING_POINT + 5, "Combine", tda, 2, 0xAF70C3);
		addToItemGroup(STARTING_POINT + 6, 1, 1, 1, 1, true, new String[]{null, null, null, null, null});
		addText(STARTING_POINT + 7, "Combineable items", tda, 0, 0xAF70C3);
		addText(STARTING_POINT + 8, "Required items", tda, 0, 0xAF70C3);
		addText(STARTING_POINT + 9, "Reward", tda, 0, 0xAF70C3);
		addToItemGroup(STARTING_POINT + 10, 6, 4, 7, 7, true, new String[]{null, null, null, null, null});
		addCloseButtonSmall(STARTING_POINT + 11, STARTING_POINT + 12, STARTING_POINT + 13);
		main.totalChildren(12);
		main.child(0, STARTING_POINT + 1, 10, 10);
		main.child(1, STARTING_POINT + 2, 280, 273);
		main.child(2, STARTING_POINT + 3, 280, 273);
		main.child(3, STARTING_POINT + 5, 319, 282);
		main.child(4, STARTING_POINT + 6, 324, 210);
		main.child(5, STARTING_POINT + 20, 0, 71);
		main.child(6, STARTING_POINT + 7, 59, 56);
		main.child(7, STARTING_POINT + 8, 300, 56);
		main.child(8, STARTING_POINT + 9, 324, 178);
		main.child(9, STARTING_POINT + 10, 228, 74);
		main.child(10, STARTING_POINT + 11, 465, 19);
		main.child(11, STARTING_POINT + 12, 465, 19);

		RSInterface scroll = addInterface(STARTING_POINT + 20);
		scroll.width = 165;
		scroll.height = 232;
		scroll.scrollMax = 750;

		scroll.totalChildren(1);
		scroll.child(0, STARTING_POINT + 21, 25, 0);//done

		addToItemGroup(STARTING_POINT + 21, 4, 32, 5, 7, true, new String[]{"Select", null, null, null, null});
	}


	public static void addHoverImage(int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.enabledSprite = method207(j, aClass44, name);
		tab.disabledSprite = method207(k, aClass44, name);
	}

	public static void addHoverImage(int a, int i, int j, int k, String name) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.disabledSpriteId = j;
		tab.enabledSpriteId = k;
		// tab.setSprite = 638;
		// tab.savedFirstSpriteId = j;
	}

	public static void addHoveredButton(int i, String imageName, int j, int w, int h, int IMAGEID) {
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		if (i != 24655) {
			addHoverImage(IMAGEID, j, j, imageName);
		} else {
			addHoverImage(1, IMAGEID, j, j, imageName);
		}
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHovered(int i, int j, String imageName, int w, int h, int IMAGEID) {
		addHoveredButton(i, imageName, j, w, h, IMAGEID);
	}

	private static void addHover(int interfaceId, int actionType, int contentType, int hoverid, int spriteId, int W, int H, String tip) {
		RSInterface rsinterfaceHover = addInterface(interfaceId);
		rsinterfaceHover.id = interfaceId;
		rsinterfaceHover.parentID = interfaceId;
		rsinterfaceHover.type = 5;
		rsinterfaceHover.atActionType = actionType;
		rsinterfaceHover.contentType = contentType;
		rsinterfaceHover.hoverType = hoverid;
		rsinterfaceHover.disabledSprite = rsinterfaceHover.enabledSprite = Client.spritesMap.get(spriteId);
		rsinterfaceHover.width = W;
		rsinterfaceHover.height = H;
		rsinterfaceHover.tooltip = tip;
	}

	private static void addHoverBox(int id, int ParentID, String text, String text2, int configId, int configFrame) {
		RSInterface rsi = addTabInterface(id);
		rsi.id = id;
		rsi.parentID = ParentID;
		rsi.type = 8;
		rsi.enabledMessage = text;// disabledText
		rsi.tooltipBoxText = text2;
		rsi.message = text;
		rsi.valueCompareType = new int[1];
		rsi.requiredValues = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.requiredValues[0] = configId;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = configFrame;
		rsi.valueIndexArray[0][2] = 0;
	}

	public static void addItemOnInterface(int childId, int interfaceId, String[] options) {
		RSInterface rsi = interfaceCache[childId] = new RSInterface();
		rsi.actions = new String[10];
		rsi.spritesX = new int[20];
		rsi.inv = new int[30];
		rsi.invStackSizes = new int[25];
		rsi.spritesY = new int[20];
		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];
		for (int i = 0; i < rsi.actions.length; i++) {
			if (i < options.length) {
				if (options[i] != null) {
					rsi.actions[i] = options[i];
				}
			}
		}
		rsi.centerText = true;
		rsi.filled = false;
		rsi.dragDeletes = false;
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.allowSwapItems = false;
		rsi.invSpritePadX = 23;
		rsi.invSpritePadY = 22;
		rsi.height = 5;
		rsi.width = 6;
		rsi.parentID = interfaceId;
		rsi.id = childId;
		rsi.type = 2;
	}

	public static void addHoverText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, boolean center, boolean textShadowed, int width, int height) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = height;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.disabledColor = color;
		rsinterface.tooltip = tooltip;
	}

	public static void addHoverText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, boolean center, boolean textShadowed, int width, int height, int hoverColor) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = height;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.disabledMouseOverColor = hoverColor;
		rsinterface.disabledColor = color;
		rsinterface.tooltip = tooltip;
	}

	public static void addHoverButton(int interfaceId, int spriteId, int width, int height, String text, int contentType, int hoverOver, int actionType) {
		// hoverable button
		RSInterface tab = addTabInterface(interfaceId);
		tab.id = interfaceId;
		tab.parentID = interfaceId;
		tab.type = 5;
		tab.atActionType = actionType;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;

		if (spriteId >= 0) {
			tab.disabledSprite = Client.spritesMap.get(spriteId);
			tab.enabledSprite = Client.spritesMap.get(spriteId);
		}

		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoverButton(int i, int disabledSprite, int enabledSprite, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.disabledSprite = Client.spritesMap.get(disabledSprite);
		tab.enabledSprite = Client.spritesMap.get(enabledSprite);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredButton(int i, int disabledSprite, int enabledSprite, int w, int h, int IMAGEID) {
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, disabledSprite, enabledSprite);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void addHoveredButton(int i, int hoverSpriteId, int width, int height, int IMAGEID) {
		// hoverable button
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = width;
		tab.height = height;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		addHoverImage(IMAGEID, hoverSpriteId, hoverSpriteId);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	private static void addHoverImage(int i, int j, int k) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.disabledSprite = Client.spritesMap.get(j);
		tab.enabledSprite = Client.spritesMap.get(k);
	}

	public static void addPixels(int id, int color, int width, int height, int alpha, boolean filled) {
		RSInterface rsi = addInterface(id);
		rsi.disabledColor = color;
		rsi.filled = filled;
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 3;
		rsi.atActionType = 0;
		rsi.contentType = 0;
		rsi.opacity = (byte) alpha;
		rsi.width = width;
		rsi.height = height;
	}

	public static RSInterface addInterface(int id) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();
		rsi.id = id;
		rsi.parentID = id;
		rsi.width = 512;
		rsi.height = 334;
		return rsi;
	}

	// done
	public static void addLunar2RunesSmallBox(int ID, int r1, int r2, int ra1, int ra2, int rune1, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast On";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[3];
		rsInterface.requiredValues = new int[3];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = lvl;
		rsInterface.valueIndexArray = new int[3][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[3];
		rsInterface.valueIndexArray[2][0] = 1;
		rsInterface.valueIndexArray[2][1] = 6;
		rsInterface.valueIndexArray[2][2] = 0;
		rsInterface.disabledSprite = Client.spritesMap.get(sid);
		rsInterface.enabledSprite = Client.spritesMap.get(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(7, INT);
		addLunarSprite(ID + 2, 205);
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 37, 35, 3, INT);// Rune
		setBounds(rune1, 112, 35, 4, INT);// Rune
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 50, 66, 5, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 123, 66, 6, INT);
	}

	// done
	public static void addLunar3RunesBigBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.disabledSprite = Client.spritesMap.get(sid);
		rsInterface.enabledSprite = Client.spritesMap.get(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 206);
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 21, 2, INT);
		setBounds(30016, 14, 48, 3, INT);
		setBounds(rune1, 74, 48, 4, INT);
		setBounds(rune2, 130, 48, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 79, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 79, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 79, 8, INT);
	}

	// done
	public static void addLunar3RunesLargeBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.disabledSprite = Client.spritesMap.get(sid);
		rsInterface.enabledSprite = Client.spritesMap.get(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 207);
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 34, 2, INT);
		setBounds(30016, 14, 61, 3, INT);
		setBounds(rune1, 74, 61, 4, INT);
		setBounds(rune2, 130, 61, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 92, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 92, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 92, 8, INT);
	}

    public static void addNecro3Rune(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int rune3, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 27585;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.spellUsableOn = suo;
        rsInterface.selectedActionName = "Cast on";
        rsInterface.width = 32;
        rsInterface.height = 32;
        rsInterface.tooltip = "Cast @gre@" + name;
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[4];
        rsInterface.requiredValues = new int[4];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = ra2;
        rsInterface.valueCompareType[2] = 3;
        rsInterface.requiredValues[2] = ra3;
        rsInterface.valueCompareType[3] = 3;
        rsInterface.requiredValues[3] = lvl;
        rsInterface.valueIndexArray = new int[4][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[4];
        rsInterface.valueIndexArray[1][0] = 4;
        rsInterface.valueIndexArray[1][1] = 3214;
        rsInterface.valueIndexArray[1][2] = r2;
        rsInterface.valueIndexArray[1][3] = 0;
        rsInterface.valueIndexArray[2] = new int[4];
        rsInterface.valueIndexArray[2][0] = 4;
        rsInterface.valueIndexArray[2][1] = 3214;
        rsInterface.valueIndexArray[2][2] = r3;
        rsInterface.valueIndexArray[2][3] = 0;
        rsInterface.valueIndexArray[3] = new int[3];
        rsInterface.valueIndexArray[3][0] = 1;
        rsInterface.valueIndexArray[3][1] = 6;
        rsInterface.valueIndexArray[3][2] = 0;
        rsInterface.disabledSprite = Client.spritesMap.get(sid);
        rsInterface.enabledSprite = Client.spritesMap.get(sid);
        RSInterface INT = addInterface(ID + 1);
        INT.interfaceShown = true;
        INT.hoverType = -1;
        setChildren(9, INT);
        addLunarSprite(ID + 2, 205);
        setBounds(ID + 2, 0, 0, 0, INT);
        addText(ID + 3, "Level " + lvl + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
        setBounds(ID + 3, 90, 4, 1, INT);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
        setBounds(ID + 4, 90, 19, 2, INT);
        setBounds(rune1, 16, 33, 3, INT);
        setBounds(rune2, 74, 33, 4, INT);
        setBounds(rune3, 130, 33, 5, INT);
        addRuneText(ID + 5, ra1 , r1, TDA);
        setBounds(ID + 5, 32, 66, 6, INT);
        addRuneText(ID + 6, ra2, r2, TDA);
        setBounds(ID + 6, 90, 66, 7, INT);
        addRuneText(ID + 7, ra3, r3, TDA);
        setBounds(ID + 7, 147, 66, 8, INT);
    }

    public static void addNecro2Rune(int ID, int r1, int r2,int ra1, int ra2, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 27585;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.spellUsableOn = suo;
        rsInterface.selectedActionName = "Cast on";
        rsInterface.width = 32;
        rsInterface.height = 32;
        rsInterface.tooltip = "Cast @gre@" + name;
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[4];
        rsInterface.requiredValues = new int[4];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = ra2;
        rsInterface.valueCompareType[2] = 3;
        rsInterface.requiredValues[2] = lvl;
        rsInterface.valueIndexArray = new int[4][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[4];
        rsInterface.valueIndexArray[1][0] = 4;
        rsInterface.valueIndexArray[1][1] = 3214;
        rsInterface.valueIndexArray[1][2] = r2;
        rsInterface.valueIndexArray[1][3] = 0;
        rsInterface.valueIndexArray[2] = new int[3];
        rsInterface.valueIndexArray[2][0] = 1;
        rsInterface.valueIndexArray[2][1] = 6;
        rsInterface.valueIndexArray[2][2] = 0;
        rsInterface.disabledSprite = Client.spritesMap.get(sid);
        rsInterface.enabledSprite = Client.spritesMap.get(sid);
        RSInterface INT = addInterface(ID + 1);
        INT.interfaceShown = true;
        INT.hoverType = -1;
        setChildren(7, INT);
        addLunarSprite(ID + 2, 205);
        setBounds(ID + 2, 0, 0, 0, INT);
        addText(ID + 3, "Level " + lvl + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
        setBounds(ID + 3, 90, 4, 1, INT);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
        setBounds(ID + 4, 90, 19, 2, INT);
        setBounds(rune1, 46, 33, 3, INT);
        setBounds(rune2, 106, 33, 4, INT);
        addRuneText(ID + 5, ra1, r1, TDA);
        setBounds(ID + 5, 61, 66, 5, INT);
        addRuneText(ID + 6, ra2, r2, TDA);
        setBounds(ID + 6, 122, 66, 6, INT);
    }

    public static void addNecroRune(int ID, int r1, int ra1, int rune1, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
        RSInterface rsInterface = addInterface(ID);
        rsInterface.id = ID;
        rsInterface.parentID = 27585;
        rsInterface.type = 5;
        rsInterface.atActionType = type;
        rsInterface.contentType = 0;
        rsInterface.hoverType = ID + 1;
        rsInterface.spellUsableOn = suo;
        rsInterface.selectedActionName = "Cast on";
        rsInterface.width = 32;
        rsInterface.height = 32;
        rsInterface.tooltip = "Cast @gre@" + name;
        rsInterface.spellName = name;
        rsInterface.valueCompareType = new int[4];
        rsInterface.requiredValues = new int[4];
        rsInterface.valueCompareType[0] = 3;
        rsInterface.requiredValues[0] = ra1;
        rsInterface.valueCompareType[1] = 3;
        rsInterface.requiredValues[1] = lvl;
        rsInterface.valueIndexArray = new int[4][];
        rsInterface.valueIndexArray[0] = new int[4];
        rsInterface.valueIndexArray[0][0] = 4;
        rsInterface.valueIndexArray[0][1] = 3214;
        rsInterface.valueIndexArray[0][2] = r1;
        rsInterface.valueIndexArray[0][3] = 0;
        rsInterface.valueIndexArray[1] = new int[3];
        rsInterface.valueIndexArray[1][0] = 1;
        rsInterface.valueIndexArray[1][1] = 6;
        rsInterface.valueIndexArray[1][2] = 0;
        rsInterface.disabledSprite = Client.spritesMap.get(sid);
        rsInterface.enabledSprite = Client.spritesMap.get(sid);
        RSInterface INT = addInterface(ID + 1);
        INT.interfaceShown = true;
        INT.hoverType = -1;
        setChildren(5, INT);
        addLunarSprite(ID + 2, 205);
        setBounds(ID + 2, 0, 0, 0, INT);
        addText(ID + 3, "Level " + lvl + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
        setBounds(ID + 3, 90, 4, 1, INT);
        addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
        setBounds(ID + 4, 90, 19, 2, INT);
        setBounds(rune1, 74, 33, 3, INT);
        addRuneText(ID + 5, ra1, r1, TDA);
        setBounds(ID + 5, 90, 66, 4, INT);
    }

	// done
	public static void addLunar3RunesSmallBox(int ID, int r1, int r2, int r3, int ra1, int ra2, int ra3, int rune1, int rune2, int lvl, String name, String descr, TextDrawingArea[] TDA, int sid, int suo, int type) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.spellUsableOn = suo;
		rsInterface.selectedActionName = "Cast on";
		rsInterface.width = 20;
		rsInterface.height = 20;
		rsInterface.tooltip = "Cast @gre@" + name;
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[4];
		rsInterface.requiredValues = new int[4];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = ra2;
		rsInterface.valueCompareType[2] = 3;
		rsInterface.requiredValues[2] = ra3;
		rsInterface.valueCompareType[3] = 3;
		rsInterface.requiredValues[3] = lvl;
		rsInterface.valueIndexArray = new int[4][];
		rsInterface.valueIndexArray[0] = new int[4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = r1;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.valueIndexArray[1] = new int[4];
		rsInterface.valueIndexArray[1][0] = 4;
		rsInterface.valueIndexArray[1][1] = 3214;
		rsInterface.valueIndexArray[1][2] = r2;
		rsInterface.valueIndexArray[1][3] = 0;
		rsInterface.valueIndexArray[2] = new int[4];
		rsInterface.valueIndexArray[2][0] = 4;
		rsInterface.valueIndexArray[2][1] = 3214;
		rsInterface.valueIndexArray[2][2] = r3;
		rsInterface.valueIndexArray[2][3] = 0;
		rsInterface.valueIndexArray[3] = new int[3];
		rsInterface.valueIndexArray[3][0] = 1;
		rsInterface.valueIndexArray[3][1] = 6;
		rsInterface.valueIndexArray[3][2] = 0;
		rsInterface.disabledSprite = Client.spritesMap.get(sid);
		rsInterface.enabledSprite = Client.spritesMap.get(sid + 39);
		RSInterface INT = addInterface(ID + 1);
		INT.interfaceShown = true;
		INT.hoverType = -1;
		setChildren(9, INT);
		addLunarSprite(ID + 2, 205);
		setBounds(ID + 2, 0, 0, 0, INT);
		addText(ID + 3, "Level " + (lvl + 1) + ": " + name, 0xFF981F, true, true, 52, TDA, 1);
		setBounds(ID + 3, 90, 4, 1, INT);
		addText(ID + 4, descr, 0xAF6A1A, true, true, 52, TDA, 0);
		setBounds(ID + 4, 90, 19, 2, INT);
		setBounds(30016, 14, 35, 3, INT);
		setBounds(rune1, 74, 35, 4, INT);
		setBounds(rune2, 130, 35, 5, INT);
		addRuneText(ID + 5, ra1 + 1, r1, TDA);
		setBounds(ID + 5, 26, 66, 6, INT);
		addRuneText(ID + 6, ra2 + 1, r2, TDA);
		setBounds(ID + 6, 87, 66, 7, INT);
		addRuneText(ID + 7, ra3 + 1, r3, TDA);
		setBounds(ID + 7, 142, 66, 8, INT);
	}

	// done
	private static void addLunarSprite(int i, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.id = i;
		RSInterface.parentID = i;
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = (byte) 0;
		RSInterface.hoverType = 52;
		RSInterface.disabledSprite = Client.spritesMap.get(j);
		RSInterface.width = 500;
		RSInterface.height = 500;
		RSInterface.tooltip = "";
        RSInterface.opaqueSprite = true;
	}

	// done
	private static void addRuneText(int ID, int runeAmount, int RuneID, TextDrawingArea[] font) {
		RSInterface rsInterface = addInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 4;
		rsInterface.atActionType = 0;
		rsInterface.contentType = 0;
		rsInterface.width = 0;
		rsInterface.height = 14;
		rsInterface.opacity = 0;
		rsInterface.hoverType = -1;
		rsInterface.valueCompareType = new int[1];
		rsInterface.requiredValues = new int[1];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = runeAmount;
		rsInterface.valueIndexArray = new int[1][4];
		rsInterface.valueIndexArray[0][0] = 4;
		rsInterface.valueIndexArray[0][1] = 3214;
		rsInterface.valueIndexArray[0][2] = RuneID;
		rsInterface.valueIndexArray[0][3] = 0;
		rsInterface.centerText = true;
		rsInterface.textDrawingAreas = font[0];
		rsInterface.textShadow = true;
		rsInterface.message = "%1/" + runeAmount + "";
		rsInterface.enabledMessage = "";
		rsInterface.disabledColor = 12582912;
		rsInterface.enabledColor = 49152;
	}

	public static void addSprite(int id, int spriteId) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;

		if (spriteId != -1) {
			tab.disabledSprite = Client.spritesMap.get(spriteId);
			tab.enabledSprite = Client.spritesMap.get(spriteId);
		}

		tab.width = 512;
		tab.height = 334;
	}

	public static void addTransparentSprite(int id, int spriteId, int spriteId2, int op) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 13;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.hoverType = 52;
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.enabledSprite = Client.spritesMap.get(spriteId2);
		tab.width = 512;
		tab.height = 334;
		tab.opacity = (byte) op;
	}

	public static void addSprite(int ID, int i, int i2, int configId, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.id = ID;
		Tab.parentID = ID;
		Tab.type = 5;
		Tab.atActionType = 0;
		Tab.contentType = 0;
		Tab.width = 512;
		Tab.height = 334;
		Tab.opacity = (byte) 0;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configId;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.disabledSprite = Client.spritesMap.get(i);
		Tab.enabledSprite = Client.spritesMap.get(i2);
	}

	public static RSInterface addTabInterface(int id) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;// 250
		tab.parentID = id;// 236
		tab.type = 0;// 262
		tab.atActionType = 0;// 217
		tab.contentType = 0;
		tab.width = 512;// 220
		tab.height = 700;// 267
		tab.opacity = (byte) 0;
		tab.hoverType = -1;// Int 230
		return tab;
	}

	public static void addClickableText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, int width, int height) {
		RSInterface Tab = addTabInterface(id);
		Tab.parentID = id;
		Tab.id = id;
		Tab.type = 4;
		Tab.atActionType = 1;
		Tab.width = width;
		Tab.height = height;
		Tab.contentType = 0;
		Tab.hoverType = 0;
		// Tab.mOverInterToTrigger = -1;
		Tab.centerText = false;
		// Tab.enabledText = true;
		Tab.textDrawingAreas = tda[idx];
		Tab.message = text;
		Tab.tooltip = tooltip;
		// Tab.aString228 = "";
		Tab.disabledColor = color;
	}

	public static void addSkillButton(int id, String skillGuide) {
		RSInterface button = addTabInterface(id);
		button.type = 5;
		button.atActionType = 5;
		button.contentType = 0;
		button.width = 60;
		button.height = 28;
		button.disabledSprite = Client.spritesMap.get(622);
		button.tooltip = "@whi@View @or1@" + skillGuide + " @whi@Options";
	}

	public static void addClickableText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow, int width) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 1;
		tab.width = width;
		tab.height = 12;
		tab.contentType = 0;
		// tab.aByte254 = 0;
		// tab.mOverInterToTrigger = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.enabledMessage = "";
		tab.disabledColor = color;
		tab.enabledColor = 0;
		tab.disabledMouseOverColor = 0xffffff;
		tab.enabledMouseOverColor = 0;
		tab.tooltip = tooltip;
	}

	public static void addText(int id, String text, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow, int hoverColour, String tooltip, int widthHover) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 1;
		tab.width = widthHover;
		tab.height = 15;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.enabledMessage = "";
		tab.disabledColor = color;
		tab.enabledColor = 0;
		tab.disabledMouseOverColor = 0xffffff;
		tab.enabledMouseOverColor = 0;
		tab.tooltip = tooltip;
	}
	public static RSInterface addPercentageBar(int id, int percentageDimension, int percentageTotal,
											   int percentageSpriteEmpty, int percentageSpriteFull, int h, boolean verticleBar) {
		RSInterface rs = interfaceCache[id] = new RSInterface();
		rs.id = id;
		rs.parentID = id;
		rs.type = 72;
		rs.atActionType = 0;
		rs.contentType = 0;
		rs.opacity = 0;
		rs.hoverType = 0;
		rs.percentageCompleted = 0;
		rs.percentageDimension = percentageDimension;
		rs.colorFull = percentageSpriteFull;
		rs.colorEmpty = percentageSpriteEmpty;
		rs.percentageTotal = percentageTotal;
		rs.verticalBar = verticleBar;
		rs.width = 0;
		rs.height = h;
		return rs;
	}
	public int percentageCompleted;
	public int percentageTotal;
	public int colorEmpty;
	public int colorFull;
	public int percentageDimension;
	public boolean verticalBar;


	public static void addText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = a;
		RSInterface.centerText = l;
		RSInterface.textShadow = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.enabledMessage = "";
		RSInterface.disabledColor = k;
	}

	public static void addText(int id, String text, TextDrawingArea[] tda, int idx, int color, boolean centered) {
		RSInterface rsi = interfaceCache[id] = new RSInterface();

		if (centered) {
			rsi.centerText = true;
		}

		rsi.textShadow = true;
		rsi.textDrawingAreas = tda[idx];
		rsi.message = text;
		rsi.disabledColor = color;
		rsi.id = id;
		rsi.type = 4;
	}
	public static RSInterface addTextRight(int id, String text, TextDrawingArea tda[], int idx, int color, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = false;
		tab.rightText = true;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.enabledMessage = "";
		tab.disabledColor = color;
		tab.enabledColor = 0;
		tab.disabledMouseOverColor = 0;
		tab.enabledMouseOverColor = 0;
		return tab;
	}
	public static void addText(int id, String text, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.enabledMessage = "";
		tab.disabledColor = color;
		tab.enabledColor = 0;
		tab.disabledMouseOverColor = 0;
		tab.enabledMouseOverColor = 0;
	}

	public static void addText(int id, String text, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow, int s, int t) {
		RSInterface tab = addTabInterface(id);
		tab.parentID = id;
		tab.id = id;
		tab.type = 4;
		tab.atActionType = 0;
		tab.width = 0;
		tab.height = 11;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.centerText = center;
		tab.textShadow = shadow;
		tab.textDrawingAreas = tda[idx];
		tab.message = text;
		tab.enabledMessage = "";
		tab.disabledColor = color;
		tab.enabledColor = 0;
		tab.disabledMouseOverColor = 0;
		tab.enabledMouseOverColor = 0;
	}

	public static void addText(int id, String text, TextDrawingArea[] wid, int idx, int color) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 0;
		rsinterface.width = 174;
		rsinterface.height = 11;
		rsinterface.contentType = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = false;
		rsinterface.textShadow = true;
		rsinterface.textDrawingAreas = wid[idx];
		rsinterface.message = text;
		rsinterface.disabledColor = color;
	}

	public static void addTransparentSpriteWSpriteLoader(int id, int spriteId, int opacity) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 3;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.customOpacity = opacity;
		tab.hoverType = 52;
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.enabledSprite.myHeight - 2;
		// tab.drawsTransparent = true;
	}

	public static void addTransparentSpriteWSpriteLoader1(int id, int spriteId, int opacity) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 88;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.customOpacity = opacity;
		tab.hoverType = 52;
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.enabledSprite.myHeight - 2;
		tab.drawsTransparent = true;
	}

	public boolean drawsTransparent = false;


	public int customOpacity = 0;

	public static void addConfigButtonWSpriteLoader(int ID, int pID, int bID, int bID2, int width, int height, String tT, int configID, int aT, int configFrame) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.hoverType = -1;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.disabledSprite = Client.spritesMap.get(bID);
		Tab.enabledSprite = Client.spritesMap.get(bID2);
		Tab.tooltip = tT;
	}

	public static void addButtonWSpriteLoader(int id, int spriteId, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight - 2;
		tab.tooltip = tooltip;
	}

	public static void addButton1(int id, int spriteId, int spriteId2, String tooltip) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 553;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.enabledSprite = Client.spritesMap.get(spriteId2);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.disabledSprite.myHeight - 2;
		tab.tooltip = tooltip;
	}

	public static void addToggleButton(int id, int sprite, int setconfig, int width, int height, String s) {
		RSInterface rsi = addInterface(id);
		rsi.disabledSprite = Client.spritesMap.get(sprite);
		rsi.enabledSprite = Client.spritesMap.get(sprite + 1);
		rsi.requiredValues = new int[1];
		rsi.requiredValues[0] = 1;
		rsi.valueCompareType = new int[1];
		rsi.valueCompareType[0] = 1;
		rsi.valueIndexArray = new int[1][3];
		rsi.valueIndexArray[0][0] = 5;
		rsi.valueIndexArray[0][1] = setconfig;
		rsi.valueIndexArray[0][2] = 0;
		rsi.atActionType = 4;
		rsi.width = width;
		rsi.hoverType = -1;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
		rsi.height = height;
		rsi.tooltip = s;
	}

	public static void addBankItem(int index, Boolean hasOption) {
		RSInterface rsi = interfaceCache[index] = new RSInterface();
		rsi.actions = new String[5];
		rsi.spritesX = new int[20];
		rsi.invStackSizes = new int[30];
		rsi.inv = new int[30];
		rsi.spritesY = new int[20];

		rsi.children = new int[0];
		rsi.childX = new int[0];
		rsi.childY = new int[0];

		// rsi.hasExamine = false;

		rsi.invSpritePadX = 24;
		rsi.invSpritePadY = 24;
		rsi.height = 5;
		rsi.width = 6;
		rsi.parentID = 5382;
		rsi.id = index;
		rsi.type = 2;
	}
	

	public static void constructLunar() {
		RSInterface Interface = addTabInterface(29999);
		setChildren(62, Interface);
		setBounds(30017, 11, 10, 0, Interface);
		setBounds(30025, 40, 10, 1, Interface);
		setBounds(30032, 71, 12, 2, Interface);
		setBounds(30040, 103, 10, 3, Interface);
		setBounds(30048, 135, 12, 4, Interface);
		setBounds(30056, 165, 10, 5, Interface);
		setBounds(30099, 8, 38, 6, Interface);
		setBounds(30122, 39, 39, 7, Interface);
		setBounds(30130, 71, 39, 8, Interface);
		setBounds(30154, 103, 39, 9, Interface);
		setBounds(30178, 135, 39, 10, Interface);
		setBounds(30186, 165, 37, 11, Interface);
		setBounds(30194, 12, 68, 12, Interface);
		setBounds(30202, 42, 68, 13, Interface);
		setBounds(30210, 71, 68, 14, Interface);
		setBounds(30218, 103, 68, 15, Interface);
		setBounds(30242, 135, 68, 16, Interface);
		setBounds(30282, 165, 68, 17, Interface);
		setBounds(30290, 11, 97, 18, Interface);//
		setBounds(30298, 41, 97, 19, Interface);//
		setBounds(30306, 70, 97, 20, Interface);
		setBounds(30322, 100, 97, 21, Interface);

		// setBounds(30322, 104, 184, 39, Interface);
		setBounds(30001, 5, 120, 22, Interface);// hover
		setBounds(30018, 5, 120, 23, Interface);// hover
		setBounds(30026, 5, 120, 24, Interface);// hover
		setBounds(30033, 5, 120, 25, Interface);// hover
		setBounds(30041, 5, 120, 26, Interface);// hover
		setBounds(30049, 5, 120, 27, Interface);// hover
		setBounds(30057, 5, 120, 28, Interface);// hover
		setBounds(30065, 5, 120, 29, Interface);// hover
		setBounds(30076, 5, 120, 30, Interface);// hover
		setBounds(30084, 5, 120, 31, Interface);// hover
		setBounds(30092, 5, 120, 32, Interface);// hover
		setBounds(30100, 5, 120, 33, Interface);// hover
		setBounds(30107, 5, 120, 34, Interface);// hover
		setBounds(30115, 5, 120, 35, Interface);// hover
		setBounds(30123, 5, 120, 36, Interface);// hover
		setBounds(30131, 5, 120, 37, Interface);// hover
		setBounds(30139, 5, 120, 38, Interface);// hover
		setBounds(30147, 5, 120, 39, Interface);// hover
		setBounds(30155, 5, 120, 40, Interface);// hover
		setBounds(30163, 5, 120, 41, Interface);// hover
		setBounds(30171, 5, 120, 42, Interface);// hover
		setBounds(30179, 5, 120, 43, Interface);// hover
		setBounds(30187, 5, 120, 44, Interface);// hover
		setBounds(30195, 5, 120, 45, Interface);// hover
		setBounds(30203, 5, 120, 46, Interface);// hover
		setBounds(30211, 5, 120, 47, Interface);// hover
		setBounds(30219, 5, 120, 48, Interface);// hover
		setBounds(30227, 5, 120, 49, Interface);// hover
		setBounds(30235, 5, 120, 50, Interface);// hover
		setBounds(30243, 5, 120, 51, Interface);// hover
		setBounds(30251, 5, 120, 52, Interface);// hover
		setBounds(30259, 5, 120, 53, Interface);// hover
		setBounds(30267, 5, 120, 54, Interface);// hover
		setBounds(30275, 5, 120, 55, Interface);// hover
		setBounds(30283, 5, 120, 56, Interface);// hover
		setBounds(30291, 5, 120, 57, Interface);// hover
		setBounds(30299, 5, 5, 58, Interface);// hover
		setBounds(30307, 5, 5, 59, Interface);// hover
		setBounds(30323, 5, 5, 60, Interface);// hover
		setBounds(30315, 5, 5, 61, Interface);// hover
	}

	public static void drawRune(int i, int id) {
		RSInterface RSInterface = addInterface(i);
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.disabledSprite = Client.spritesMap.get(id);
		RSInterface.width = 500;
		RSInterface.height = 500;
	}

	public static void drawRune(int i, int id, String runeName) {
		RSInterface RSInterface = addTabInterface(i);
		RSInterface.type = 5;
		RSInterface.atActionType = 0;
		RSInterface.contentType = 0;
		RSInterface.opacity = 0;
		RSInterface.hoverType = 52;
		RSInterface.disabledSpriteId = 195 + id - 1;
		RSInterface.width = 500;
		RSInterface.height = 500;
	}


	private static Sprite method207(int i, Archive streamLoader, String s) {
		long l = (TextUtilities.method585(s) << 8) + i;
		Sprite sprite = (Sprite) aMRUNodes_238.insertFromCache(l);
		if (sprite != null) {
			return sprite;
		}
		try {
			sprite = new Sprite(streamLoader, s, i);
			aMRUNodes_238.removeFromCache(sprite, l);
		} catch (Exception _ex) {
			return null;
		}
		return sprite;
	}

	public static void clearModelCache(boolean flag, Model model) {
		int i = 0;// was parameter
		int j = 5;// was parameter
		if (flag) {
			return;
		}
		aMRUNodes_264.clear();
		if (model != null && j != 4) {
			aMRUNodes_264.removeFromCache(model, (j << 16) + i);
		}
	}

	public static void removeSomething(int id) {
		interfaceCache[id] = new RSInterface();
	}

	public static void removeSomething(RSInterface child) {
		interfaceCache[child.id] = new RSInterface();
	}

	public static void setBounds(int id, int x, int y, int index, RSInterface RSinterface) {
		RSinterface.children[index] = id;
		RSinterface.childX[index] = x;
		RSinterface.childY[index] = y;
	}

	public String[] actions() {
		return actions;
	}

	public void setActions(String[] actions) {
		this.actions = actions;
	}

	public static RSInterface get(int i) {
		return interfaceCache[i];
	}

	public static void setChildren(int total, RSInterface i) {
		i.children = new int[total];
		i.childX = new int[total];
		i.childY = new int[total];
	}

	public void setChildren(int total) {
		children = new int[total];
		childX = new int[total];
		childY = new int[total];
	}

	public static void addSpriteLoaderButtonWithTooltipBox(int childId, int spriteId, String tooltip, int hoverSpriteId, int tooltipBoxChildId, String tooltipBoxText, int tooltipx, int tooltipy) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = 1;
		rsi.contentType = 0;
		rsi.hoverType = tooltipBoxChildId;
		rsi.enabledSprite = Client.spritesMap.get(spriteId);
		rsi.disabledSprite = Client.spritesMap.get(spriteId);
		rsi.width = rsi.enabledSprite.myWidth;
		rsi.height = rsi.disabledSprite.myHeight - 2;
		rsi.tooltip = tooltip;
		// rsi.isFalseTooltip = true;
		addTooltip2(tooltipBoxChildId, tooltipBoxText, tooltipx, tooltipy);
	}

	public static void addTooltip2(int id, String text, int x, int y) {
		RSInterface rsinterface = addTabInterface(id);
		rsinterface.parentID = id;
		rsinterface.type = 0;
		rsinterface.interfaceShown = true;
		rsinterface.hoverType = -1;
		addTooltipBox2(id + 1, text);
		rsinterface.totalChildren(1);
		rsinterface.child(0, id + 1, x, y);
	}

	public static void addTooltipBox2(int id, String text) {
		RSInterface rsi = addInterface(id);
		rsi.id = id;
		rsi.parentID = id;
		rsi.type = 12;
		rsi.message = text;
	}
/*	public static addContainer(int id, int contentType, int width, int height, int xPad, int yPad, boolean move, String... actions) {
		container = addInterface(id);
		container.parentID = id;
		container.type = 2;
		container.contentType = contentType;
		container.width = width;
		container.height = height;
		container.sprites = new Sprite[20];
		container.spritesX = new int[20];
		container.spritesY = new int[20];
		container.invSpritePadX = xPad;
		container.invSpritePadY = yPad;
		container.inv = new int[width * height];
		container.invStackSizes = new int[width * height];
		container.invItems = new ItemDto[width * height];
		container.actions = actions;
		container.deleteOnDrag2 = move;
		return container;
	}*/

	public static void addSpriteLoader(int childId, int spriteId) {
		RSInterface rsi = RSInterface.interfaceCache[childId] = new RSInterface();
		rsi.id = childId;
		rsi.parentID = childId;
		rsi.type = 5;
		rsi.atActionType = 0;
		rsi.contentType = 0;
		rsi.enabledSprite = Client.spritesMap.get(spriteId);
		rsi.disabledSprite = Client.spritesMap.get(spriteId);

		// rsi.sprite1.spriteLoader = rsi.sprite2.spriteLoader = true;
		// rsi.hoverSprite1 = Client.spritesMap.get(hoverSpriteId);
		// rsi.hoverSprite2 = Client.spritesMap.get(hoverSpriteId);
		// rsi.hoverSprite1.spriteLoader = rsi.hoverSprite2.spriteLoader = true;
		// rsi.sprite1 = rsi.sprite2 = spriteId;
		// rsi.hoverSprite1Id = rsi.hoverSprite2Id = hoverSpriteId;
		rsi.width = rsi.enabledSprite.myWidth;
		rsi.height = rsi.disabledSprite.myHeight - 2;
		// rsi.isFalseTooltip = true;
	}

	public static void addCloseButton(int id) {
		RSInterface component = addHoverableSprite(id, 1016, 1017, true, "Close");
		component.atActionType = 3;
	}

    public static void addClose_Button(int id, int on, int off) {
        RSInterface component = addHoverableSprite(id, on, off, true, "Close");
        component.atActionType = 3;
    }

	public static void addCloseButton(int child, int hoverChild, int hoverImageChild) {
		addHoverButtonWSpriteLoader(child, 1016, 21, 21, "Close", 250, hoverChild, 3);
		addHoveredImageWSpriteLoader(hoverChild, 1017, 21, 21, hoverImageChild);
	}


	public static void hoverButton2(int id, int spriteId, int hoverSprite, int width, int height, String hoverTooltip) {
		RSInterface tab = addInterface(id);
		tab.tooltip = hoverTooltip;
		tab.atActionType = 1;
		tab.type = 42;
		tab.disabledSprite = Client.cacheSprite[spriteId];
		tab.enabledSprite = Client.cacheSprite[hoverSprite];
		tab.messageOffsetX = tab.width / 2;
		tab.messageOffsetY = (tab.height / 2) + 5;
		tab.disabledSpriteId = spriteId;
		tab.enabledSpriteId = hoverSprite;
		tab.width = width;
		tab.height = height;
	}
	public static void addHoveredImageWSpriteLoader(int i, int spriteId, int w, int h, int imgInterface) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		tab.interfaceShown = true;
		tab.width = w;
		tab.height = h;
		addHoverImageWSpriteLoader(imgInterface, spriteId);
		tab.totalChildren(1);
		tab.child(0, imgInterface, 0, 0);
	}

	public static void addHoverImageWSpriteLoader(int i, int spriteId) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.width = 512;
		tab.height = 334;
		tab.opacity = 0;
		tab.hoverType = 52;
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.disabledSprite = Client.spritesMap.get(spriteId);
	}

	public static void addHoverSpriteLoaderButton(int i, int spriteId, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHoveredSpriteLoaderButton(int i, int w, int h, int IMAGEID, int spriteId) {
		RSInterface tab = addTabInterface(i);
		tab.parentID = i;
		tab.id = i;
		tab.type = 0;
		tab.atActionType = 0;
		tab.width = w;
		tab.height = h;
		tab.interfaceShown = true;
		tab.opacity = 0;
		tab.hoverType = -1;
		tab.scrollMax = 0;
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.totalChildren(1);
		tab.child(0, IMAGEID, 0, 0);
	}

	public static void Sidebar0a(int id, int id2, int id3, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
	// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xAF70C3, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xAF70C3, false);
		addText(id2 + 12, text2, tda, 0, 0xAF70C3, false);
		addText(id2 + 13, text3, tda, 0, 0xAF70C3, false);
		addText(id2 + 14, text4, tda, 0, 0xAF70C3, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 15;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.disabledSprite = Client.spritesMap.get(82);
			rsi.enabledSprite = Client.spritesMap.get(83);
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0b(int id, int id2, String text1, String text2, String text3, String text4, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int str4x, int str4y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, int img4x, int img4y, TextDrawingArea[] tda) // 4button
	// nospec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xAF70C3, true); // 2426
		addText(id2 + 11, text1, tda, 0, 0xAF70C3, false);
		addText(id2 + 12, text2, tda, 0, 0xAF70C3, false);
		addText(id2 + 13, text3, tda, 0, 0xAF70C3, false);
		addText(id2 + 14, text4, tda, 0, 0xAF70C3, false);

		rsi.width = 190;
		rsi.height = 261;

		int last = 14;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 46);
		frame++; // 2429
		rsi.child(frame, id2 + 4, 104, 99);
		frame++; // 2430
		rsi.child(frame, id2 + 5, 21, 99);
		frame++; // 2431
		rsi.child(frame, id2 + 6, 105, 46);
		frame++; // 2432

		rsi.child(frame, id2 + 7, img1x, img1y);
		frame++; // bottomright 2433
		rsi.child(frame, id2 + 8, img2x, img2y);
		frame++; // topleft 2434
		rsi.child(frame, id2 + 9, img3x, img3y);
		frame++; // bottomleft 2435
		rsi.child(frame, id2 + 10, img4x, img4y);
		frame++; // topright 2436

		rsi.child(frame, id2 + 11, str1x, str1y);
		frame++; // chop 2437
		rsi.child(frame, id2 + 12, str2x, str2y);
		frame++; // slash 2438
		rsi.child(frame, id2 + 13, str3x, str3y);
		frame++; // lunge 2439
		rsi.child(frame, id2 + 14, str4x, str4y);
		frame++; // block 2440

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon 2426

		for (int i = id2 + 3; i < id2 + 7; i++) { // 2429 - 2433
			rsi = interfaceCache[i];
			rsi.disabledSprite = Client.spritesMap.get(82);
			rsi.enabledSprite = Client.spritesMap.get(83);
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void Sidebar0c(int id, int id2, int id3, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) // 3button
	// spec
	{
		RSInterface rsi = addInterface(id); // 2423
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xAF70C3, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xAF70C3, false);
		addText(id2 + 10, text2, tda, 0, 0xAF70C3, false);
		addText(id2 + 11, text3, tda, 0, 0xAF70C3, false);
		/* specialBar(ID); */
		rsi.specialBar(id3); // 7599

		rsi.width = 190;
		rsi.height = 261;

		int last = 12;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 21, 99);
		frame++;
		rsi.child(frame, id2 + 4, 105, 46);
		frame++;
		rsi.child(frame, id2 + 5, 21, 46);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // chop
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // slash
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // lunge

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
		rsi.child(frame, id3, 21, 205);
		frame++; // special attack 7599

		for (int i = id2 + 3; i < id2 + 6; i++) {
			rsi = interfaceCache[i];
			rsi.disabledSprite = Client.spritesMap.get(82);
			rsi.enabledSprite = Client.spritesMap.get(83);
			rsi.width = 68;
			rsi.height = 44;
		}
	}

	public static void itemGroup(int id, int w, int h, int x, int y, boolean drag, boolean examine) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		/*
		 * rsi.aBoolean235 = drag; rsi.examine = examine;
		 */
		rsi.type = 2;
	}

	public static void itemGroup(int id, int w, int h, int x, int y) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.type = 2;
	}

	public static void addToItemGroup(int id, int w, int h, int x, int y, boolean actions, String action1, String action2, String action3) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.interfaceShown = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		rsi.actions = new String[5];
		if (actions) {
			rsi.actions[0] = action1;
			rsi.actions[1] = action2;
			rsi.actions[2] = action3;
		}
		rsi.type = 2;
	}

	public static void addInventoryItemGroup2(int id, int h, int w) {
		RSInterface Tab = interfaceCache[id] = new RSInterface();
		Tab.inv = new int[w * h];
		Tab.invStackSizes = new int[w * h];
		for (int i1 = 0; i1 < w * h; i1++) {
			Tab.invStackSizes[i1] = 0; // inv item stack size
			Tab.inv[i1] = 0; // inv item ids
		}
		Tab.spritesY = new int[28];
		Tab.spritesX = new int[28];
		for (int i2 = 0; i2 < 28; i2++) {
			Tab.spritesY[i2] = 8;
			Tab.spritesX[i2] = 16;
		}
		Tab.invSpritePadX = 7;
		Tab.invSpritePadY = 4;
		Tab.actions = new String[]{"Store 1", "Store 5", "Store 10", "Store All", null};
		Tab.width = w;
		Tab.hoverType = -1;
		Tab.parentID = id;
		Tab.id = id;
		Tab.scrollMax = 0;
		Tab.type = 2;
		Tab.height = h;
	}

	public static void addToItemGroup(int id, int w, int h, int x, int y, boolean hasActions, String[] actions) {
		RSInterface rsi = addInterface(id);
		rsi.width = w;
		rsi.height = h;
		rsi.inv = new int[w * h];
		rsi.invStackSizes = new int[w * h];
		rsi.usableItemInterface = false;
		rsi.isInventoryInterface = false;
		rsi.interfaceShown = false;
		rsi.invSpritePadX = x;
		rsi.invSpritePadY = y;
		rsi.spritesX = new int[20];
		rsi.spritesY = new int[20];
		rsi.sprites = new Sprite[20];
		if (hasActions) {
			rsi.actions = actions;
		}
		rsi.type = 2;
	}

    public static boolean resizeSprite = false;

    public static void addToItemGroup(int id, int w, int h, int x, int y, boolean hasActions, String[] actions, boolean resizeSprite) {
        RSInterface rsi = addInterface(id);
        rsi.width = w;
        rsi.height = h;
        rsi.inv = new int[w * h];
        rsi.invStackSizes = new int[w * h];
        rsi.usableItemInterface = false;
        rsi.isInventoryInterface = false;
        rsi.interfaceShown = false;
        rsi.invSpritePadX = x;
        rsi.invSpritePadY = y;
        rsi.spritesX = new int[20];
        rsi.spritesY = new int[20];
        rsi.sprites = new Sprite[20];
        if (hasActions) {
            rsi.actions = actions;
        }
        rsi.resizeSprite = resizeSprite;
        rsi.type = 2;
    }

	public static void opacityInterface() {
		RSInterface rsi = addTabInterface(35555);
		setChildren(1, rsi);
		addRectangle(35556, 128, 0x000000, true, 30, 34);
		setBounds(35556, 0, 0, 0, rsi);
	}

	public static void Sidebar0d(int id, int id2, int id3, String text1, String text2, String text3, int str1x, int str1y, int str2x, int str2y, int str3x, int str3y, int img1x, int img1y, int img2x, int img2y, int img3x, int img3y, TextDrawingArea[] tda) {
		RSInterface rsi = addInterface(id); // 2423
        RSInterface specBarOfOtherInterface = interfaceCache[id3];
		/* addText(ID, "Text", tda, Size, Colour, Centered); */
		addText(id2, "", tda, 3, 0xAF70C3, true); // 2426
		addText(id2 + 9, text1, tda, 0, 0xAF70C3, false);
		addText(id2 + 10, text2, tda, 0, 0xAF70C3, false);
		addText(id2 + 11, text3, tda, 0, 0xAF70C3, false);
        rsi.specialBar(id3, true);

		// addText(353, "Spell", tda, 0, 0xff981f, false);
        removeSomething(350);
        removeSomething(351);
        removeSomething(352);
		removeSomething(353);

		addCacheSprite(337, 19, 0, "combaticons");
		addCacheSprite(338, 13, 0, "combaticons2");
		addCacheSprite(339, 14, 0, "combaticons2");

		/* addToggleButton(id, sprite, config, width, height, tooltip); */
		// addToggleButton(349, 349, 108, 68, 44, "Select");
		removeSomething(349);
		addToggleButton(350, 68, 108, 68, 44, "Select");

		rsi.width = 190;
		rsi.height = 261;

		int last = 13;
		int frame = 0;
		rsi.totalChildren(last, last, last);

		rsi.child(frame, id2 + 3, 20, 115);
		frame++;
		rsi.child(frame, id2 + 4, 20, 80);
		frame++;
		rsi.child(frame, id2 + 5, 20, 45);
		frame++;

		rsi.child(frame, id2 + 6, img1x, img1y);
		frame++; // topleft
		rsi.child(frame, id2 + 7, img2x, img2y);
		frame++; // bottomleft
		rsi.child(frame, id2 + 8, img3x, img3y);
		frame++; // topright

		rsi.child(frame, id2 + 9, str1x, str1y);
		frame++; // bash
		rsi.child(frame, id2 + 10, str2x, str2y);
		frame++; // pound
		rsi.child(frame, id2 + 11, str3x, str3y);
		frame++; // focus

		rsi.child(frame, 349, 105, 46);
		frame++; // spell1

		rsi.child(frame, 19300, 0, 0);
		frame++; // stuffs
		rsi.child(frame, id2, 94, 4);
		frame++; // weapon
        rsi.child(frame, specBarOfOtherInterface.id, 21, 205);
        frame++;//SpecBar
	}

	public static void sprite1(int id, int sprite) {
		RSInterface class9 = interfaceCache[id];
		class9.disabledSprite = Client.spritesMap.get(sprite);
	}

	public static void textSize(int id, TextDrawingArea[] tda, int idx) {
		RSInterface rsi = interfaceCache[id];
		rsi.textDrawingAreas = tda[idx];
	}

	public static RSFontSystem[] newFonts;

	public static void unpack(Archive streamLoader, TextDrawingArea[] textDrawingAreas, Archive streamLoader_1, RSFontSystem[] newFontSystem) {
		aMRUNodes_238 = new MRUNodes(50000);
		fonts = textDrawingAreas;
		newFonts = newFontSystem;
		Stream stream = new Stream(streamLoader.getDataForName("data"));
		int i = -1;
		stream.getUnsignedShort();
		// int j = stream.getUnsignedShort();
		interfaceCache = new RSInterface[150000];
		while (stream.position < stream.buffer.length) {
			int k = stream.getUnsignedShort();
			if (k == 65535) {
				i = stream.getUnsignedShort();
				k = stream.getUnsignedShort();
			}
			RSInterface rsInterface = interfaceCache[k] = new RSInterface();
			rsInterface.id = k;
			rsInterface.parentID = i;
			rsInterface.type = stream.getUnsignedByte();
			rsInterface.atActionType = stream.getUnsignedByte();
			rsInterface.contentType = stream.getUnsignedShort();
			rsInterface.width = stream.getUnsignedShort();
			rsInterface.height = stream.getUnsignedShort();
			rsInterface.opacity = (byte) stream.getUnsignedByte();
			rsInterface.hoverType = stream.getUnsignedByte();
			if (rsInterface.hoverType != 0) {
				rsInterface.hoverType = (rsInterface.hoverType - 1 << 8) + stream.getUnsignedByte();
			} else {
				rsInterface.hoverType = -1;
			}

			int i1 = stream.getUnsignedByte();

			if (i1 > 0) {
				rsInterface.valueCompareType = new int[i1];
				rsInterface.requiredValues = new int[i1];

				for (int j1 = 0; j1 < i1; j1++) {
					rsInterface.valueCompareType[j1] = stream.getUnsignedByte();
					rsInterface.requiredValues[j1] = stream.getUnsignedShort();
				}
			}

			int k1 = stream.getUnsignedByte();

			if (k1 > 0) {
				rsInterface.valueIndexArray = new int[k1][];
				for (int l1 = 0; l1 < k1; l1++) {
					int i3 = stream.getUnsignedShort();
					rsInterface.valueIndexArray[l1] = new int[i3];
					for (int l4 = 0; l4 < i3; l4++) {
						rsInterface.valueIndexArray[l1][l4] = stream.getUnsignedShort();
					}
				}
			}

			if (rsInterface.type == 0) {
				rsInterface.scrollMax = stream.getUnsignedShort();
				rsInterface.interfaceShown = stream.getUnsignedByte() == 1;
				int i2 = stream.getUnsignedShort();
				rsInterface.children = new int[i2];
				rsInterface.childX = new int[i2];
				rsInterface.childY = new int[i2];

				for (int j3 = 0; j3 < i2; j3++) {
					rsInterface.children[j3] = stream.getUnsignedShort();
					rsInterface.childX[j3] = stream.getSignedShort();
					rsInterface.childY[j3] = stream.getSignedShort();
				}
			}

			if (rsInterface.type == 1) {
				stream.getUnsignedShort();
				stream.getUnsignedByte();
			}

			if (rsInterface.type == 2) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				//rsInterface.invItems = new ItemDto[rsInterface.width * rsInterface.height];
				//rsInterface.deleteOnDrag2 = stream.getUnsignedByte() == 1;
				rsInterface.allowSwapItems = stream.getUnsignedByte() == 1;
				rsInterface.isInventoryInterface = stream.getUnsignedByte() == 1;
				rsInterface.usableItemInterface = stream.getUnsignedByte() == 1;
				rsInterface.dragDeletes = stream.getUnsignedByte() == 1;
				rsInterface.invSpritePadX = stream.getUnsignedByte();
				rsInterface.invSpritePadY = stream.getUnsignedByte();
				rsInterface.spritesX = new int[20];
				rsInterface.spritesY = new int[20];
				rsInterface.sprites = new Sprite[20];

				for (int j2 = 0; j2 < 20; j2++) {
					int k3 = stream.getUnsignedByte();

					if (k3 == 1) {
						rsInterface.spritesX[j2] = stream.getSignedShort();
						rsInterface.spritesY[j2] = stream.getSignedShort();
						String s1 = stream.getString();

						if (streamLoader_1 != null && s1.length() > 0) {
							int i5 = s1.lastIndexOf(",");
							rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
						}
					}
				}

				rsInterface.actions = new String[5];

				for (int l3 = 0; l3 < 5; l3++) {
					rsInterface.actions[l3] = stream.getString();
					if (rsInterface.actions[l3].length() == 0) {
						rsInterface.actions[l3] = null;
					}
					if (rsInterface.parentID == 3824) {
						rsInterface.actions[4] = "Buy X";
					}
					if (rsInterface.parentID == 3822) {
						rsInterface.actions[4] = "Sell X";
						rsInterface.hideExamine = true;
					}
				}

				if (rsInterface.parentID == 1644) {
					rsInterface.actions[2] = "Operate";
				}
			}

			if (rsInterface.type == 3) {
				rsInterface.filled = stream.getUnsignedByte() == 1;
			}

			if (rsInterface.type == 4 || rsInterface.type == 1) {
				rsInterface.centerText = stream.getUnsignedByte() == 1;
				int k2 = stream.getUnsignedByte();
				if (textDrawingAreas != null) {
					rsInterface.textDrawingAreas = textDrawingAreas[k2];
				}
				rsInterface.textShadow = stream.getUnsignedByte() == 1;
			}

			if (rsInterface.type == 4 || rsInterface.type == 17) {
				rsInterface.message = stream.getString().replaceAll("RuneScape", Configuration.CLIENT_NAME);
				rsInterface.enabledMessage = stream.getString();
			}

			if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4 || rsInterface.type == 17) {
				rsInterface.disabledColor = stream.getIntLittleEndian();
			}

			if (rsInterface.type == 3 || rsInterface.type == 4 || rsInterface.type == 17) {
				rsInterface.enabledColor = stream.getIntLittleEndian();
				rsInterface.disabledMouseOverColor = stream.getIntLittleEndian();
				rsInterface.enabledMouseOverColor = stream.getIntLittleEndian();
			}

			if (rsInterface.type == 5) {
				String s = stream.getString();

				if (streamLoader_1 != null && s.length() > 0) {
					int i4 = s.lastIndexOf(",");
					rsInterface.disabledSprite = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4));
				}

				s = stream.getString();

				if (streamLoader_1 != null && s.length() > 0) {
					int j4 = s.lastIndexOf(",");
					rsInterface.enabledSprite = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4));
				}
			}

			if (rsInterface.type == 6) {
				int l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.mediaType = 1;
					rsInterface.mediaID = (l - 1 << 8) + stream.getUnsignedByte();
				}

				l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.anInt255 = 1;
					rsInterface.anInt256 = (l - 1 << 8) + stream.getUnsignedByte();
				}

				l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.disabledAnimationId = (l - 1 << 8) + stream.getUnsignedByte();
				} else {
					rsInterface.disabledAnimationId = -1;
				}

				l = stream.getUnsignedByte();

				if (l != 0) {
					rsInterface.enabledAnimationId = (l - 1 << 8) + stream.getUnsignedByte();
				} else {
					rsInterface.enabledAnimationId = -1;
				}

				rsInterface.modelZoom = stream.getUnsignedShort();
				rsInterface.modelRotationY = stream.getUnsignedShort();
				rsInterface.modelRotationX = stream.getUnsignedShort();
			}

			if (rsInterface.type == 7) {
				rsInterface.inv = new int[rsInterface.width * rsInterface.height];
				rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
				rsInterface.centerText = stream.getUnsignedByte() == 1;
				int l2 = stream.getUnsignedByte();
				if (textDrawingAreas != null) {
					rsInterface.textDrawingAreas = textDrawingAreas[l2];
				}
				rsInterface.textShadow = stream.getUnsignedByte() == 1;
				rsInterface.disabledColor = stream.getIntLittleEndian();
				rsInterface.invSpritePadX = stream.getSignedShort();
				rsInterface.invSpritePadY = stream.getSignedShort();
				rsInterface.isInventoryInterface = stream.getUnsignedByte() == 1;
				rsInterface.actions = new String[5];

				for (int k4 = 0; k4 < 5; k4++) {
					rsInterface.actions[k4] = stream.getString();
					if (rsInterface.actions[k4].length() == 0) {
						rsInterface.actions[k4] = null;
					}
				}
			}

			if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
				rsInterface.selectedActionName = stream.getString();
				rsInterface.spellName = stream.getString();
				rsInterface.spellUsableOn = stream.getUnsignedShort();
			}

			if (rsInterface.type == 8) {
				rsInterface.message = stream.getString();
			}

			if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
				rsInterface.tooltip = stream.getString();

				if (rsInterface.tooltip.length() == 0) {
					if (rsInterface.atActionType == 1) {
						rsInterface.tooltip = "Ok";
					}
					if (rsInterface.atActionType == 4) {
						rsInterface.tooltip = "Select";
					}
					if (rsInterface.atActionType == 5) {
						rsInterface.tooltip = "Select";
					}
					if (rsInterface.atActionType == 6) {
						rsInterface.tooltip = "Continue";
					}
				}
			}
		}


		aClass44 = streamLoader;
		try {
			OverloadTimerInterface(textDrawingAreas);
			DamageBoostTimerInterface(textDrawingAreas);
			DropRateTimerInterface(textDrawingAreas);
            necroTimer(textDrawingAreas);
			customCollectionLog(textDrawingAreas);
			customCombiner(textDrawingAreas);
			killTracker(textDrawingAreas);
			mysteryBoxSpinner(textDrawingAreas);
			customServerPerks(textDrawingAreas);
			perkOverlays(textDrawingAreas);
			bestInSlot();
			ItemUpgradeInterface.create(textDrawingAreas);
			statCheckerInterface();
			//warriorGuild(textDrawingAreas);
			//pvmbingoInterface(textDrawingAreas);
			CollectionLog.draw(textDrawingAreas);
			SettingsInterface.unpack();
            DonationTab.unpack();
            ServerPerks.unpack();
            Necronimcon.unpack(textDrawingAreas);
            Bank.unpack(textDrawingAreas);
			customInterfaces = new CustomInterfaces(textDrawingAreas);
			customInterfaces.loadCustoms();
			Interfaces.loadInterfaces(textDrawingAreas);
            OSRSCreationMenu.build();
            SkillTab.unpack();
            KeybindInterface.keybinding();
            WeaponTab.unpack();
            Parties.unpack();
            Invocations.unpack();
			DonationTab.unpack();
            SkillTree.unpack();
            ChestMinigame.unpack();
/*            findEmptyIds();*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		aMRUNodes_238.clear();
		aMRUNodes_238 = null;
	}

	private static void printFreeInterfaceIds(int start, int end) {
		int currentCount = 0;
		int longestCount = 0;
		int longestStartingIndex = 0;
		for (int i = start; i < end; i++) {
			if (interfaceCache[i] != null) {
				if (currentCount > longestCount) {
					longestCount = currentCount;
					longestStartingIndex = i - longestCount;
				}
				currentCount = 0;
			} else {
				currentCount++;
			}
		}
		System.out.printf("longest count was %d (%d-%d)", longestCount, longestStartingIndex, longestCount + longestStartingIndex);
	}

    private static void calcFreeSpots() {
        int currentCount = 0;
        int startingIndexBeforeUsed = 0;
        for (int i = 0; i < 65535; i++) {
            if (interfaceCache[i] != null) {
                startingIndexBeforeUsed = i - currentCount;
                System.out.println("Free id's in range of " + startingIndexBeforeUsed + " - " + i);
                currentCount = 0;
            } else {
                currentCount++;
            }
        }
    }

	public static final int BH_OFFSET = 17;

	public static void addSummoningText(int i, String s, int k, boolean l, boolean m, int a, TextDrawingArea[] TDA, int j) {

		RSInterface RSInterface = addTabInterface(i);
		RSInterface.parentID = i;
		RSInterface.id = i;
		RSInterface.type = 4;
		RSInterface.atActionType = 0;
		RSInterface.width = 0;
		RSInterface.height = 0;
		RSInterface.contentType = 0;
		RSInterface.hoverType = a;
		RSInterface.centerText = l;
		// RSInterface.dis = m;
		RSInterface.textDrawingAreas = TDA[j];
		RSInterface.message = s;
		RSInterface.message = s;
		RSInterface.disabledColor = k;
		RSInterface.interfaceShown = true;
		RSInterface.hoverType = -1;
	}

	public static void addInAreaHoverSpriteLoader(int i, int sprite, int w, int h, String text, int contentType, int actionType) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = actionType;
		tab.contentType = contentType;
		tab.hoverType = i;
		tab.disabledSprite = Client.spritesMap.get(sprite);
		tab.width = w;
		tab.height = h;
		tab.tooltip = text;
	}

	public static void addPouch(int ID, int[] r1, int ra1, int r2, int lvl, String name, TextDrawingArea[] TDA, int imageID, int type) {
		RSInterface rsInterface = addTabInterface(ID);
		rsInterface.id = ID;
		rsInterface.parentID = 1151;
		rsInterface.type = 5;
		rsInterface.atActionType = type;
		rsInterface.contentType = 0;
		rsInterface.hoverType = ID + 1;
		rsInterface.width = 32;
		rsInterface.height = 32;
		rsInterface.tooltip = (new StringBuilder()).append("Infuse @or1@").append(name).toString();
		rsInterface.spellName = name;
		rsInterface.valueCompareType = new int[2];
		rsInterface.requiredValues = new int[2];
		rsInterface.valueCompareType[0] = 3;
		rsInterface.requiredValues[0] = ra1;
		rsInterface.valueCompareType[1] = 3;
		rsInterface.requiredValues[1] = lvl - 1;
		rsInterface.summonReq = lvl - 1;
		rsInterface.valueIndexArray = new int[2 + r1.length][];
		for (int i = 0; i < r1.length; i++) {
			rsInterface.valueIndexArray[i] = new int[4];
			rsInterface.valueIndexArray[i][0] = 4;
			rsInterface.valueIndexArray[i][1] = 3214;
			rsInterface.valueIndexArray[i][2] = r1[i];
			rsInterface.valueIndexArray[i][3] = 0;
		}
		rsInterface.valueIndexArray[1] = new int[3];
		rsInterface.valueIndexArray[1][0] = 1;
		rsInterface.valueIndexArray[1][1] = 6;
		rsInterface.valueIndexArray[1][2] = 0;
		rsInterface.itemSpriteZoom = 150;
		rsInterface.itemSpriteId = r2;
		rsInterface.itemSpriteIndex = imageID;
		// rsInterface.greyScale = true;
		RSInterface hover = addTabInterface(ID + 1);
		hover.hoverType = -1;
		hover.interfaceShown = true;
		if (imageID < SummoningInterfaceData.summoningItemRequirements.length) {
			addSprite(ID + 6, SummoningInterfaceData.summoningItemRequirements[imageID][0], null, 150, 150);
			addSprite(ID - 1200, SummoningInterfaceData.summoningItemRequirements[imageID][1], null, 150, 150);
			addSprite(ID - 1201, SummoningInterfaceData.summoningItemRequirements[imageID][2], null, 150, 150);
			addRuneText(ID - 1202, SummoningInterfaceData.summoningItemAmountRequirements[imageID][0], SummoningInterfaceData.summoningItemRequirements[imageID][0], TDA);
			addRuneText(ID - 1203, SummoningInterfaceData.summoningItemAmountRequirements[imageID][1], SummoningInterfaceData.summoningItemRequirements[imageID][1], TDA);
			if (SummoningInterfaceData.summoningItemAmountRequirements[imageID][2] > 0) {
				addRuneText(ID - 1204, SummoningInterfaceData.summoningItemAmountRequirements[imageID][2], SummoningInterfaceData.summoningItemRequirements[imageID][2], TDA);
			}
			setChildren(SummoningInterfaceData.summoningItemAmountRequirements[imageID][2] > 0 ? 9 : 8, hover);
			setBounds(ID + 6, 14, 33, 3, hover);
			setBounds(ID - 1200, 70, 33, 4, hover);
			setBounds(ID - 1201, 120, 33, 5, hover);
			setBounds(ID - 1202, 30, 65, 6, hover);
			setBounds(ID - 1203, 85, 65, 7, hover);
			if (SummoningInterfaceData.summoningItemAmountRequirements[imageID][2] > 0) {
				setBounds(ID - 1204, 133, 65, 8, hover);
			}
		} else {
			setChildren(3, hover);
		}
		addSpriteLoader(ID + 2, 1007);
		addText(ID + 3, (new StringBuilder()).append("Level ").append(lvl).append(": ").append(name).toString(), 0xff981f, true, true, 52, 1);
		addText(ID + 4, "This item requires:", 0xaf6a1a, true, true, 52, 0);
		setBounds(ID + 2, 0, 0, 0, hover);
		setBounds(ID + 3, 90, 4, 1, hover);
		setBounds(ID + 4, 90, 19, 2, hover);
	}

	public static void addSprite1(int id, int spriteId, String spriteName) {
		addSprite(id, spriteId, spriteName, -1, -1);
	}

	public static void addSprite(int id, int spriteId, String spriteName, int zoom1, int zoom2) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (spriteName == null) {
			tab.itemSpriteZoom = zoom1;
			tab.itemSpriteId = spriteId;
		}
		tab.width = 512;
		tab.height = 334;
	}

	public static void addSprite(int a, int id, int spriteId, String spriteName, boolean l) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		if (spriteId > 0) {
			tab.disabledSprite = Client.spritesMap.get(spriteId);
			tab.enabledSprite = Client.spritesMap.get(spriteId);
		}
		tab.width = 512;
		tab.height = 334;
	}

	public static void addText(int i, String message, int disabledColor, boolean center, boolean shadow, int hoverType, int fonts) {
		try {
			RSInterface rsinterface = addTabInterface(i);
			rsinterface.parentID = i;
			rsinterface.id = i;
			rsinterface.type = 4;
			rsinterface.atActionType = 0;
			rsinterface.width = 0;
			rsinterface.height = 0;
			rsinterface.contentType = 0;
			rsinterface.hoverType = hoverType;
			rsinterface.centerText = center;
			rsinterface.textShadow = shadow;
			rsinterface.textDrawingAreas = RSInterface.fonts[fonts];
			rsinterface.message = message;
			rsinterface.disabledColor = disabledColor;
		} catch (Exception e) {
		}
	}

	public static void addHoverButton(int i, String imageName, int j, int width, int height, String text, int contentType, int hoverOver, int aT) {// hoverable
		// button
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = aT;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = hoverOver;
		// tab.disabledSprite = imageLoader(j, imageName);
		// tab.enabledSprite = imageLoader(j, imageName);
		tab.width = width;
		tab.height = height;
		tab.tooltip = text;
	}

	public static void addHDSprite(int id, int spriteId, int sprite2) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.advancedSprite = true;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.enabledSprite = Client.spritesMap.get(sprite2);
		tab.width = 512;
		tab.height = 1024;
	}

    public static void addAdvancedSprite(int id, int spriteId) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = 5;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.advancedSprite = true;

        if (spriteId != -1) {
            tab.disabledSprite = Client.spritesMap.get(spriteId);
            tab.enabledSprite = Client.spritesMap.get(spriteId);
        }

        tab.width = 512;
        tab.height = 334;
    }

	/**
	 * Insert a new child into an already existing interface
	 *
	 * @param inter
	 */
	public static void insertNewChild(RSInterface inter, int id, int x, int y) {
		int[] newChildren = new int[inter.children.length + 1];
		int[] newChildX = new int[inter.childX.length + 1];
		int[] newChildY = new int[inter.childY.length + 1];
		for (int i = 0; i < inter.children.length; i++) {
			newChildren[i] = inter.children[i];
		}
		for (int i = 0; i < inter.childX.length; i++) {
			newChildX[i] = inter.childX[i];
		}
		for (int i = 0; i < inter.childY.length; i++) {
			newChildY[i] = inter.childY[i];
		}

		inter.children = newChildren;
		inter.childX = newChildX;
		inter.childY = newChildY;
		inter.children[inter.children.length - 1] = id;
		inter.childX[inter.childX.length - 1] = x;
		inter.childY[inter.childY.length - 1] = y;
	}

	public boolean filled;
	public String[] actions;
	public boolean allowSwapItems;
	public int anInt208;
	public int disabledMouseOverColor;
	public int enabledColor;
	public int mediaType;
	public int enabledMouseOverColor;
	public int anInt246;
	private int anInt255;
	private int anInt256;
	public int disabledAnimationId;
	public int enabledAnimationId;
	public String enabledMessage;
	public int atActionType;
	public boolean centerText;
	public boolean rightText;
	public int[] children;
	public int[] childX;
	public int[] childY;
	public int contentType;
	public boolean dragDeletes;
	public boolean drawSecondary;
	public int height;
	public int hoverType;
	public int id;
	public boolean interfaceShown;
	public int[] inv;
	public int invSpritePadX;
	public int invSpritePadY;
	public int[] invStackSizes;
	public boolean isInventoryInterface;
	public boolean drawInfinity;
	public int mediaID;
	public String message;
	public int modelRotationY;
	public int modelRotationX;
	public int modelZoom;
	public byte opacity;

	public int parentID;
	public int[] requiredValues;
	public int scrollMax;
	public boolean sideScroll;
	public int scrollPosition;
	public String secondaryText;
	public String selectedActionName;
	public String spellName;
	int spellUsableOn;
	public Sprite disabledSprite;
	public Sprite enabledSprite;
	public Sprite[] sprites;
	public int[] spritesX;
	public int[] spritesY;
	public int disabledColor;
	public TextDrawingArea textDrawingAreas;
	public boolean textShadow;
	public String tooltip;
	public String tooltipBoxText;
	public int type;
	public boolean usableItemInterface;
	public int[] valueCompareType;
	public int[][] valueIndexArray;
	public int width;
	public int xOffset;
	public int yOffset;
	public boolean hideStackSize;
    public boolean hideExamine;
	public boolean advancedSprite;
	public int summonReq;
	public boolean greyScale;
	public int itemSpriteId;
	public int itemSpriteZoom;
	public int itemSpriteIndex;
	public String originalSpriteName;
	public boolean active = true;
	public boolean toggled = false;

	public RSInterface() {
		enabledSpriteId = disabledSpriteId = -1;
	}

	public int enabledSpriteId, disabledSpriteId;

	public int pauseTicks = 20;
	public boolean endReached = false;

	public boolean displayedOnInterface = true;

	public DropdownMenu dropdown;
	public int[] dropdownColours;
	public boolean hovered = false;
	public RSInterface dropdownOpen;
	public int dropdownHover = -1;
	public boolean inverted;
	public static void addRectangleClickable(int id, int opacity, int color,
											 boolean filled, int width, int height) {
		RSInterface tab = interfaceCache[id] = new RSInterface();
		tab.disabledColor = color;
		tab.filled = filled;
		tab.id = id;
		tab.parentID = id;
		tab.type = 3;
		tab.atActionType = 5;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.width = width;
		tab.height = height;
		tab.tooltip = "Select";
	}

	public static void addForceSprite(int id, Sprite sprite) {
		RSInterface rsi = RSInterface.interfaceCache[id] = new RSInterface();
		rsi.enabledSprite = sprite;
		rsi.disabledSprite = sprite;
		rsi.parentID = id;
		rsi.id = id;
		rsi.type = 5;
	}
	public static void addInAreaHover(int i, int sId, int sId2, int w, int h, String text, int contentType, int actionType) {
		RSInterface tab = addTabInterface(i);
		tab.id = i;
		tab.parentID = i;
		tab.type = 5;
		tab.atActionType = actionType;
		tab.contentType = contentType;
		tab.opacity = 0;
		tab.hoverType = i;
		if (sId != -1) {
			tab.disabledSprite = Client.spritesMap.get(sId);
		}
		tab.enabledSprite = Client.spritesMap.get(sId2);
		tab.width = w;
		tab.height = h;
		tab.tooltip = text;
	}

	public void child(int id, int interID, int x, int y) {
		children[id] = interID;
		childX[id] = x;
		childY[id] = y;
	}

	private Model getMediaModel(int i, int j) {
		Model model = (Model) aMRUNodes_264.insertFromCache((i << 16) + j);
		if (model != null) {
			return model;
		}
		if (i == 1) {
			model = Model.fetchModel(j);
		}
		if (i == 2) {
            try {
                model = EntityDef.get(j).method160();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		if (i == 3) {
			model = Client.myPlayer.getPlayerModel();
		}
		if (i == 4) {
			model = ItemDef.get(j).method202(50);
		}
		if(i == 13) {
			Player mini = Client.getClient().getPlayersMini();
			if(mini != null) {
				model = mini.getRotatedModel();
			}
		}
		if (i == 5) {
			model = null;
		}
		if (model != null) {
			aMRUNodes_264.removeFromCache(model, (i << 16) + j);
		}
		if (i == 10 || i == 11) {
			Player p = new Player();
			p.visible = true;
			p.equipment[0] = i == 10 ? (j + 256) : (j + 512);
			if (p.myGender == 0) {
				p.equipment[1] = plrJaw + 256;
			}
			p.myGender = gender;
			model = p.getPlayerModel();
		}
		return model;
	}

	public Model method209(int j, int k, boolean flag) {
		Model model;
		if (flag) {
			model = getMediaModel(anInt255, anInt256);
		} else {
			//what's the interface id for the mini viewer
			if(Client.getClient().openInterfaceID == 38650) {
				return getMediaModel(13, mediaID);
			} else {
				model = getMediaModel(mediaType, mediaID);
			}
		}
		if (model == null) {
			return null;
		}
		if (k == -1 && j == -1 && model.colors == null) {
			return model;
		}
		Model model_1 = new Model(true, AnimationSkeleton.isNullFrame(k) & AnimationSkeleton.isNullFrame(j), false, model);
		if (Client.instance.cardPack.getScale() != 128)
			model_1.scaleT(Client.instance.cardPack.getScale(), Client.instance.cardPack.getScale(), Client.instance.cardPack.getScale());
		if (k != -1 || j != -1) {
			model_1.createBones();
		}
		if (k != -1) {
			model_1.applyTransform(k);
		}
		if (j != -1) {
			model_1.applyTransform(j);
		}
		model_1.light(64, 768, -50, -10, -50, true);
		return model_1;
	}
    private static void specialBar(int id) {
        specialBar(id, false);
    }

	private static void specialBar(int id, boolean isMagic) {
        RSInterface specBarOfOtherInterface = interfaceCache[id];
        if (isMagic) {

        }
        addActionButton(id - 12, 70, -1, 150, 26, "Use @gre@Special Attack");

        for (int i = id - 11; i < id; i++) {
            removeSomething(i);
        }

        RSInterface rsi = interfaceCache[id - 12];
        rsi.width = 150;
        rsi.height = 26;
        rsi = interfaceCache[id];
        rsi.width = 150;
        rsi.height = 26;
        rsi.child(0, id - 12, 0, 0);
        rsi.child(12, id + 1, 3, 7);
        rsi.child(23, id + 12, 16, 8);

        for (int i = 13; i < 23; i++) {
            rsi.childY[i] -= 1;
        }

        rsi = interfaceCache[id + 1];
        rsi.type = 5;
        rsi.disabledSprite = Client.spritesMap.get(71);

        for (int i = id + 2; i < id + 12; i++) {
            rsi = interfaceCache[i];
            rsi.type = 5;
        }

        sprite1(id + 2, 72);
        sprite1(id + 3, 73);
        sprite1(id + 4, 74);
        sprite1(id + 5, 75);
        sprite1(id + 6, 76);
        sprite1(id + 7, 77);
        sprite1(id + 8, 78);
        sprite1(id + 9, 79);
        sprite1(id + 10, 80);
        sprite1(id + 11, 81);
	}

	public void swapInventoryItems(int i, int j) {
		int k = inv[i];
		inv[i] = inv[j];
		inv[j] = k;
		k = invStackSizes[i];
		invStackSizes[i] = invStackSizes[j];
		invStackSizes[j] = k;
	}

	public void totalChildren(int t) {
		children = new int[t];
		childX = new int[t];
		childY = new int[t];
	}

	public void totalChildren(int id, int x, int y) {
		children = new int[id];
		childX = new int[x];
		childY = new int[y];
	}

	public static void teleportText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, boolean center, boolean textShadowed, int width, int height) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = height;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = center;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.enabledMessage = "";
		rsinterface.disabledColor = color;
		rsinterface.enabledColor = 0;
		rsinterface.disabledMouseOverColor = 0xEECB38;
		rsinterface.enabledMouseOverColor = 0;
		rsinterface.tooltip = tooltip;
	}

	public static void hoverText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, boolean center, boolean textShadowed, int width, int height, boolean u) {
		RSInterface rsinterface = addInterface(id);
		rsinterface.id = id;
		rsinterface.parentID = id;
		rsinterface.type = 4;
		rsinterface.atActionType = 1;
		rsinterface.width = width;
		rsinterface.height = height;
		rsinterface.contentType = 0;
		rsinterface.opacity = 0;
		rsinterface.hoverType = -1;
		rsinterface.centerText = true;
		rsinterface.textShadow = textShadowed;
		rsinterface.textDrawingAreas = tda[idx];
		rsinterface.message = text;
		rsinterface.enabledMessage = "";
		rsinterface.disabledColor = color;
		rsinterface.enabledColor = 0;
		rsinterface.disabledMouseOverColor = 0xffffff;
		rsinterface.enabledMouseOverColor = 0;
		rsinterface.tooltip = tooltip;
	}

	public static void addCloseButton2(int child, int hoverChild, int hoverImageChild) {
		addHoverButtonWSpriteLoader(child, 1016, 21, 21, "Close", 250, hoverChild, 3);
		addHoveredImageWSpriteLoader(hoverChild, 1017, 21, 21, hoverImageChild); //12x12 for bank
	}

	private static final int WHITE_TEXT = 0xffffff;
	public static final int YELLOW_TEXT = 0xff9040;
	private static final int ORANGE_TEXT = 0xff981f;



	public static void addConfigButtonWSpriteLoader(int ID, int pID, int bID, int bID2, int width, int height, String tT, int configID, int aT, int configFrame, int hoverType, int opacity) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.hoverType = hoverType;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.advancedSprite = true;
		Tab.disabledSprite = Client.spritesMap.get(bID);
		Tab.enabledSprite = Client.spritesMap.get(bID2);
		Tab.tooltip = tT;
		Tab.opacity = (byte) opacity;
		Tab.customOpacity = opacity;
		Tab.drawsTransparent = true;
	}

	private static void addTransparentOverlay(int id, int width, int height, int color, int opacity) {
		RSInterface rsi = RSInterface.interfaceCache[id] = new RSInterface();
		rsi.type = 831;
		rsi.width = width;
		rsi.height = height;
		rsi.enabledColor = color;
		rsi.customOpacity = opacity;
	}

	public static void addLine(int id, int color, int width) {
		RSInterface tab = addInterface(id);
		tab.width = width;
		tab.enabledColor = color;
		tab.type = 22;
		tab.contentType = 0;
	}

	public static void accountSetup() {
		int id = 47000;
		RSInterface tab = addTabInterface(id);
		id++;
		int frame = 0;
		addSpriteLoader(id, 1048);

		tab.totalChildren(55);

		tab.child(frame, id, 9, 25);
		frame++;
		id++;

		addText(id, "NO Restrictions\\n\\nNO Benefitss\\n\\n\\nRecommended Mode", fonts, 0, WHITE_TEXT, true, true);
		tab.child(frame, id, 93, 225);
		frame++;
		id++;

		addText(id, "Exp. Rate: x1000\\n\\nNO Challenge\\nNO Benefits\\n\\nRecommended Mode", fonts, 0, WHITE_TEXT, true, true);
		tab.child(frame, id, 255, 225);
		frame++;
		id++;

		addConfigButtonWSpriteLoader(id, 47000, 1049, 1050, 124, 34, "Select Normal Mode", 0, 5, 1085, id + 200);
		setBounds(id, 31, 99, frame, tab);
		frame++;
		addHoveredImageWSpriteLoader(id + 200, 1050, 124, 34, id + 201);
		setBounds(id + 200, 31, 99, frame, tab);
		frame++;
		id++;

		addConfigButtonWSpriteLoader(id, 47000, 1049, 1050, 124, 34, "Select Ironman Mode", 1, 5, 1085, id + 300);
		setBounds(id, 31, 137, frame, tab);
		frame++;
		addHoveredImageWSpriteLoader(id + 300, 1050, 124, 34, id + 301);
		setBounds(id + 300, 31, 137, frame, tab);
		frame++;
		id++;

		addText(id, "", fonts, 0, 0xFF981F, true, true);
		//  addConfigButtonWSpriteLoader(id, 47000, 1049, 1050, 124, 34, "Select Hardcore Mode", 2, 5, 1085, id + 400);
		setBounds(id, 31, 156, frame, tab);
		frame++;
		addText(id + 400, "", fonts, 0, 0xFF981F, true, true);
//        addHoveredImageWSpriteLoader(id + 400, 1050, 124, 34, id + 401);
		setBounds(id + 400, 31, 156, frame, tab);
		frame++;
		id++;

		addConfigButtonWSpriteLoader(id, 47001, 1049, 1050, 124, 34, "Select Easy Exp. Rate", 0, 5, 1086, id + 500);
		setBounds(id, 193, 80, frame, tab);
		frame++;
		addHoveredImageWSpriteLoader(id + 500, 1050, 124, 34, id + 501);
		setBounds(id + 500, 193, 80, frame, tab);
		frame++;
		id++;

		addConfigButtonWSpriteLoader(id, 47001, 1049, 1050, 124, 34, "Select Intermediate Exp. Rate", 1, 5, 1086, id + 600);
		setBounds(id, 193, 118, frame, tab);
		frame++;
		addHoveredImageWSpriteLoader(id + 600, 1050, 124, 34, id + 601);
		setBounds(id + 600, 193, 118, frame, tab);
		frame++;
		id++;

		addConfigButtonWSpriteLoader(id, 47001, 1049, 1050, 124, 34, "Select Hard Exp. Rate", 2, 5, 1086, id + 700);
		setBounds(id, 193, 156, frame, tab);
		frame++;
		addHoveredImageWSpriteLoader(id + 700, 1050, 124, 34, id + 701);
		setBounds(id + 700, 193, 156, frame, tab);
		frame++;
		id++;

		hoverButton(id, 1376, 1377, "Confirm selections", 2, 0xFF981F, "Confirm");
		setBounds(id, 359, 271, frame, tab);
		frame++;
		id++;

		addText(id, "Welcome to Athens", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 254, 29);
		frame++;
		id++;


		addText(id, "Game Modes", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 92, 51);
		frame++;
		id++;

		addText(id, "Exp Rates", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 254, 51);
		frame++;
		id++;

		addText(id, "Starter Kit", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 416, 51);
		frame++;
		id++;


		addText(id, "Game Mode Info", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 92, 200);
		frame++;
		id++;

		addText(id, "Exp Rate Info", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 254, 200);
		frame++;
		id++;


		addText(id, "Normal", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 92, 108);
		frame++;
		id++;
		addText(id, "Ironman", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 92, 146);
		frame++;
		id++;
		addText(id, "", fonts, 0, 0xFF981F, true, true);
		tab.child(frame, id, 92, 165);
		frame++;
		id++;


		addText(id, "Easy", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 254, 89);
		frame++;
		id++;
		addText(id, "Intermediate", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 254, 127);
		frame++;
		id++;
		addText(id, "Hard", fonts, 2, 0xFF981F, true, true);
		tab.child(frame, id, 254, 165);
		frame++;
		id++;


		id += 100;

		/**
		 * First row of items
		 */
		int x = 353;
		for (int i = 59025; i < 59029; i++) {
			tab.child(frame, i, x, 87);
			frame++;
			x += 33;
		}

		int x2 = 353;
		for (int i = 59029; i < 59033; i++) {
			tab.child(frame, i, x2, 121);
			frame++;
			x2 += 33;
		}

		int x3 = 353;
		for (int i = 59033; i < 59037; i++) {
			tab.child(frame, i, x3, 155);
			frame++;
			x3 += 33;
		}

		int x4 = 353;
		for (int i = 59037; i < 59041; i++) {
			tab.child(frame, i, x4, 189);
			frame++;
			x4 += 33;
		}

		int x5 = 353;
		for (int i = 59041; i < 59045; i++) {
			tab.child(frame, i, x5, 223);
			frame++;
			x5 += 33;
		}

		int x6 = 353;
		for (int i = 59045; i < 59052; i++) {
			tab.child(frame, i, x6, 257);
			frame++;
			x6 += 33;
		}
	}

	public static void addConfigButtonWSpriteLoader(int ID, int pID, int bID, int bID2, int width, int height, String tT, int configID, int aT, int configFrame, int hoverType) {
		RSInterface Tab = addTabInterface(ID);
		Tab.parentID = pID;
		Tab.id = ID;
		Tab.type = 5;
		Tab.atActionType = aT;
		Tab.contentType = 0;
		Tab.width = width;
		Tab.height = height;
		Tab.hoverType = hoverType;
		Tab.valueCompareType = new int[1];
		Tab.requiredValues = new int[1];
		Tab.valueCompareType[0] = 1;
		Tab.requiredValues[0] = configID;
		Tab.valueIndexArray = new int[1][3];
		Tab.valueIndexArray[0][0] = 5;
		Tab.valueIndexArray[0][1] = configFrame;
		Tab.valueIndexArray[0][2] = 0;
		Tab.advancedSprite = true;
		Tab.disabledSprite = Client.spritesMap.get(bID);
		Tab.enabledSprite = Client.spritesMap.get(bID2);
		Tab.tooltip = tT;
	}


	public static RSInterface copy(int id, int id1) {
		RSInterface tab = addTabInterface(id);
		RSInterface copy = RSInterface.interfaceCache[id1];
		tab.parentID = id;
		tab.id = id;
		tab.type = copy.type;
		tab.atActionType = copy.atActionType;
		tab.width = copy.width;
		tab.height = copy.height;
		tab.contentType = copy.contentType;
		tab.opacity = copy.opacity;
		tab.hoverType = copy.hoverType;
		tab.centerText = copy.centerText;
		tab.textDrawingAreas = copy.textDrawingAreas;
		tab.message = copy.message;
		tab.enabledMessage = copy.enabledMessage;
		tab.disabledColor = copy.disabledColor;
		tab.enabledColor = copy.enabledColor;
		tab.disabledMouseOverColor = copy.disabledMouseOverColor;
		tab.enabledMouseOverColor = copy.enabledMouseOverColor;

		tab.inv = copy.inv;
		tab.invStackSizes = copy.invStackSizes;
		tab.usableItemInterface = copy.usableItemInterface;
		tab.isInventoryInterface = copy.isInventoryInterface;
		tab.invSpritePadX = copy.invSpritePadX;
		tab.invSpritePadY = copy.invSpritePadY;
		tab.spritesX = copy.spritesX;
		tab.spritesY = copy.spritesY;
		tab.sprites = copy.sprites;
		tab.valueCompareType = copy.valueCompareType;
		tab.requiredValues = copy.requiredValues;
		tab.valueIndexArray = copy.valueIndexArray;
		tab.disabledSpriteId = copy.disabledSpriteId;
		tab.enabledSpriteId = copy.enabledSpriteId;
		tab.tooltip = copy.tooltip;

		tab.actions = copy.actions;
		return tab;
	}

	public boolean hideWidget = false;
	public int enabledOpacity;

	//WHEEL

	private boolean visible = true;

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public WheelOfFortune wheel;
	public static final int DRAW_REGULAR_MODEL = 1, DRAW_NPC_MODEL = 2, DRAW_PLAYER_MODEL = 3, DRAW_ITEM_MODEL = 4;
	;

    public static final int ATTACK_TAB = 0,
        SKILLS_TAB = 1,
        PLAYER_TAB = 2,
        ACHIEVEMENT_TAB = 15,
        INVENTORY_TAB = 3,
        EQUIPMENT_TAB = 4,
        PRAYER_TAB = 5,
        MAGIC_TAB = 6,
        NECROMANCY_SPAWN = 13,
        FRIEND_TAB = 8,
        DONATION_TAB = 9,
        SERVER_TAB = 10,
        LOGOUT = 14,
        SETTINGS_TAB = 11,
        EMOTES_TAB = 12,
        STAFF_TAB = 15;

    public enum COLOR_TYPES {
        HITPOINTS,
        CLANCHAT,
        SPLITCHAT;
    }

    public static int stoneOffset(int spriteId, boolean xOffset) {
        Sprite stone = Client.spritesMap.get(6);
        Sprite icon = Client.spritesMap.get(spriteId);

        if (xOffset) {
            return (stone.myWidth / 2) - icon.myWidth / 2;
        }
        return (stone.myHeight / 2) - icon.myHeight / 2;
    }

    public static void closeButton(int id, int enabledSprite, int disabledSprite) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = NEW_BUTTON_COMPONENT;
        tab.buttonOn = Client.spritesMap.get(enabledSprite);
        tab.buttonOff = Client.spritesMap.get(disabledSprite);
        tab.width = Client.spritesMap.get(enabledSprite).myWidth;
        tab.height = Client.spritesMap.get(disabledSprite).myHeight;
        tab.tooltipBoxText = "Close";
        tab.hoverable = true;
        tab.atActionType = OPTION_CLOSE;
    }

    public static void keybindingDropdown(int id, int width, int defaultOption, String[] options, Dropdown d,
                                          boolean inverted) {
        RSInterface widget = addInterface(id);
        widget.type = TYPE_KEYBINDS_DROPDOWN;
        widget.dropdown = new DropdownMenu(width, true, defaultOption, options, d);
        widget.atActionType = OPTION_DROPDOWN;
        widget.inverted = inverted;
    }

    public static final int OPTION_OK = 1;
    public static final int OPTION_USABLE = 2;
    public static final int OPTION_CLOSE = 3;
    public static final int OPTION_TOGGLE_SETTING = 4;
    public static final int OPTION_RESET_SETTING = 5;
    public static final int OPTION_CONTINUE = 6;
    public static final int OPTION_DROPDOWN = 7;

    public static final int TYPE_CONTAINER = 0;
    public static final int TYPE_MODEL_LIST = 1;
    public static final int TYPE_INVENTORY = 2;
    public static final int TYPE_RECTANGLE = 3;
    public static final int TYPE_TEXT = 4;
    public static final int TYPE_SPRITE = 5;
    public static final int TYPE_MODEL = 6;
    public static final int TYPE_ITEM_LIST = 7;
    public static final int TYPE_OTHER = 8;
    public static final int TYPE_HOVER = 9;
    public static final int TYPE_CONFIG = 5;
    public static final int TYPE_CONFIG_HOVER = 11;
    public static final int TYPE_DROPDOWN = 13;
    public static final int TYPE_ROTATING = 14;
    public static final int TYPE_KEYBINDS_DROPDOWN = 15;
    public static final int TYPE_TICKER = 16;
    public static final int TYPE_ADJUSTABLE_CONFIG = 17;
    public static final int TYPE_BOX = 18;
    public static final int TYPE_MAP = 19;
    public static final int WINDOW_COMPONENT = 24;
    public static final int NAV_COMPONENT = 121;
    public static final int CHAT_COLOR_OPTIONS_COMPONENT = 122;
    public static final int PROGRESS_BAR_W_MILESTONES = 123;
    public static final int BANK_NAV_COMPONENT = 124;
    public static final int NEW_BUTTON_COMPONENT = 125;
    public static final int NEW_BUTTON_COMPONENT_WITH_ICON = 126;
    public static final int NEW_BUTTON_COMPONENT_WITH_ACTIVE_ICON = 127;
    public static final int NEW_BUTTON_COMPONENT_WITH_ACTIVE_STRING = 128;
    public static final int NEW_CLICKABLE_BUTTON = 129;
    public static final int DRAW_FILLED_PIXELS_W_ALPHA = 130;
    public static final int PROGRESS_BAR_CUT_SYS = 131;
    public static final int NEW_BUTTON_COMPONENT_STRING = 132;
    public static final int NEW_COMBAT_COMPONENT = 133;
    public static final int INVOCATION_COMPONENT = 134;
    public static final int TOOLTIP_COMPONENT = 135;
    public static final int NODE_COMPONENT = 136;

    public Sprite icon;
    public String text;
    public int[] spriteMap;

    public int colorWidth, colorHeight;

    public boolean isNavActive;

    public int hsb;

    public int progressBarBack, progressBarFill;
    public int progressBarCurrent, progressBarMax, progressBarIconEnabled, progressBarIconDisabled, progressBarWidth, progressBarHeight;
    public Milestone[] milestones;

    public COLOR_TYPES colorTypes;

    public Sprite bankOn, bankOff;

    public Sprite buttonOn, buttonOff;


    boolean modernWindow, transparentWindow, customWindow, classicWindow;

    public boolean opaqueSprite;
    public String description;
    public boolean isLocked;
    public boolean isCentered;

    public static void add_nav_bar_componenet(int componentId, int[] spriteMap, int[] sizes, String tooltip, int iconId, boolean active) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NAV_COMPONENT;
        tab.spriteMap = spriteMap;
        tab.width = sizes[0];
        tab.height = sizes[1];
        tab.tooltip = tooltip;
        tab.icon = Client.spritesMap.get(iconId);
        tab.hoverable = true;
        tab.atActionType = 1;
        tab.isNavActive = active;
    }

    public static void add_bank_nav_component(int componentId, int enabled, int disabled, String tooltip, int iconId, boolean active) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = BANK_NAV_COMPONENT;
        tab.bankOn = Client.spritesMap.get(enabled);
        tab.bankOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.tooltip = tooltip;
        tab.icon = Client.spritesMap.get(iconId);
        tab.hoverable = true;
        tab.atActionType = 1;
        tab.isNavActive = active;
        tab.actions = new String[5];
        tab.spritesX = new int[20];
        tab.invStackSizes = new int[30];
        tab.inv = new int[30];
        tab.spritesY = new int[20];
    }

    public static void add_button(int componentId, int spriteId, String tooltip) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NEW_CLICKABLE_BUTTON;
        tab.enabledSprite = Client.spritesMap.get(spriteId);
        tab.disabledSprite = Client.spritesMap.get(spriteId);
        tab.width = Client.spritesMap.get(spriteId).myWidth;
        tab.height = Client.spritesMap.get(spriteId).myHeight;
        tab.tooltip = tooltip;
        tab.hoverable = true;
        tab.atActionType = 1;
    }

    public static void add_hovered_button(int componentId, int enabled, int disabled, String tooltip) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NEW_BUTTON_COMPONENT;
        tab.buttonOn = Client.spritesMap.get(enabled);
        tab.buttonOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.tooltip = tooltip;
        tab.hoverable = true;
        tab.atActionType = 1;
    }

    public static void add_combat_button(int componentId, int enabled, int disabled, String tooltip, boolean isNavActive) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NEW_COMBAT_COMPONENT;
        tab.buttonOn = Client.spritesMap.get(enabled);
        tab.buttonOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.tooltip = tooltip;
        tab.text = tooltip.replaceAll("Train ", "");
        tab.isNavActive = isNavActive;
        tab.hoverable = true;
        tab.atActionType = 1;
    }

    public static void add_hovered_button_string(int componentId, int enabled, int disabled, String tooltip, boolean center) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NEW_BUTTON_COMPONENT_STRING;
        tab.buttonOn = Client.spritesMap.get(enabled);
        tab.buttonOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.message = tooltip;
        tab.tooltip = tooltip;
        tab.hoverable = true;
        tab.atActionType = 1;
        tab.centerText = center;
    }

    public static void add_hovered_button_with_icon(int componentId, int enabled, int disabled, String tooltip, int iconId, boolean isNavActive) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NEW_BUTTON_COMPONENT_WITH_ICON;
        tab.buttonOn = Client.spritesMap.get(enabled);
        tab.buttonOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.tooltip = tooltip;
        tab.icon = Client.spritesMap.get(iconId);
        tab.hoverable = true;
        tab.atActionType = 1;
    }

    public static void add_hovered_button_active_icon(int componentId, int enabled, int disabled, String tooltip, int iconId, boolean isNavActive) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NEW_BUTTON_COMPONENT_WITH_ACTIVE_ICON;
        tab.buttonOn = Client.spritesMap.get(enabled);
        tab.buttonOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.tooltip = tooltip;
        tab.icon = Client.spritesMap.get(iconId);
        tab.hoverable = true;
        tab.atActionType = 1;
        tab.isNavActive = isNavActive;
    }

    public static void add_hovered_button_active_string(int componentId, int enabled, int disabled, String tooltip, String text, boolean isNavActive) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NEW_BUTTON_COMPONENT_WITH_ACTIVE_STRING;
        tab.buttonOn = Client.spritesMap.get(enabled);
        tab.buttonOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.tooltip = tooltip;
        tab.text = text;
        tab.hoverable = true;
        tab.atActionType = 1;
        tab.isNavActive = isNavActive;
        tab.disabledColor = ColorConstants.RS_ORANGE;
        tab.textShadow = true;
    }

    public static void add_progress_bar(int componentId, int current, int max, int fill, int bg, int width, int height) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = PROGRESS_BAR_CUT_SYS;
        tab.progressBarFill = fill;
        tab.progressBarBack = bg;
        tab.progressBarWidth = width;
        tab.progressBarHeight = height;
        tab.progressBarCurrent = current;
        tab.progressBarMax = max;
    }

    public boolean is_modern_window() {
        return this.modernWindow;
    }

    public boolean isTransparentWindow() {
        return this.transparentWindow;
    }

    public boolean isCustomWindow() {
        return this.customWindow;
    }

    public boolean isClassicWindow() {
        return this.classicWindow;
    }

    public static RSInterface addWindow(int id, int width, int height, boolean modernBorder) {
        return RSInterface.addWindow(id, width, height, modernBorder, false, false);
    }

    public static RSInterface addWindow(int id, int width, int height, boolean modernBorder, boolean transparentWindow, boolean wrap) {
        RSInterface rsi = wrap ? RSInterface.addWrapper(id, width, height, id + 1) : RSInterface.addInterface(id);
        rsi.id = id;
        rsi.type = WINDOW_COMPONENT;
        rsi.width = width;
        rsi.height = height;
        rsi.modernWindow = modernBorder;
        rsi.transparentWindow = transparentWindow;
        return rsi;
    }

    public static RSInterface addWindow(int id, int width, int height, int type, boolean transparentWindow, boolean wrap) {
        RSInterface rsi = wrap ? RSInterface.addWrapper(id, width, height, id + 1) : RSInterface.addInterface(id);
        rsi.id = id;
        rsi.type = WINDOW_COMPONENT;
        rsi.width = width;
        rsi.height = height;
        if (type == 0)
            rsi.classicWindow = true;
        if (type == 1)
            rsi.modernWindow = true;
        if (type == 2)
            rsi.customWindow = true;
        rsi.transparentWindow = transparentWindow;
        return rsi;
    }

    public static RSInterface addWrapper(int wrapperId, int width, int height, int interfaceId) {
        RSInterface wrapper = RSInterface.addInterface(wrapperId);
        wrapper.width = width;
        wrapper.height = height;
        wrapper.totalChildren(1);
        RSInterface.setBounds(interfaceId, 0, 0, 0, wrapper);
        RSInterface rsi = RSInterface.addInterface(interfaceId);
        wrapper.parentID = wrapperId;
        return rsi;
    }

    public static void addColorBox(int componentId, int rgb, int width, int height, COLOR_TYPES type) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.hoverable = true;
        tab.type = CHAT_COLOR_OPTIONS_COMPONENT;
        tab.hsb = rgb;
        tab.colorWidth = width;
        tab.colorHeight = height;
        tab.colorTypes = type;
    }

    public static void setTab(int tab, int id) {
        Client.instance.tabInterfaceIDs[tab] = id;
    }

    public static void addProgressBar(int componentId, int current, int max, int fill, int bg, int iconEnabled, int iconDisabled, int width, int height) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = PROGRESS_BAR_W_MILESTONES;
        tab.progressBarFill = fill;
        tab.progressBarBack = bg;
        tab.progressBarIconDisabled = iconDisabled;
        tab.progressBarIconEnabled = iconEnabled;
        tab.progressBarWidth = width;
        tab.progressBarHeight = height;
        tab.progressBarCurrent = current;
        tab.progressBarMax = max;
    }

    public static void addProgressBarMilestones(int componentId, int current, int max, int fill, int iconEnabled, int iconDisabled, int width, int height, Milestone[] milestones) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = PROGRESS_BAR_W_MILESTONES;
        tab.progressBarFill = fill;
        tab.progressBarIconDisabled = iconDisabled;
        tab.progressBarIconEnabled = iconEnabled;
        tab.progressBarWidth = width;
        tab.progressBarHeight = height;
        tab.progressBarCurrent = current;
        tab.progressBarMax = max;
        tab.milestones = milestones;
    }

    public static void configButton(int id, String tooltip, int enabledSprite, int disabledSprite) {
        RSInterface tab = addInterface(id);
        tab.tooltip = tooltip;
        tab.atActionType = OPTION_TOGGLE_SETTING;
        tab.type = TYPE_CONFIG;
        tab.enabledSprite = Client.spritesMap.get(enabledSprite);
        tab.disabledSprite = Client.spritesMap.get(disabledSprite);
        tab.width = tab.enabledSprite.myWidth;
        tab.height = tab.disabledSprite.myHeight;
        tab.active = false;
    }

    public static void add_invocation(int componentId, String name, String description, boolean isLocked) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = INVOCATION_COMPONENT;
        tab.enabledSprite = Client.spritesMap.get(5761);
        tab.disabledSprite = Client.spritesMap.get(5762);
        tab.width = Client.spritesMap.get(5761).myWidth;
        tab.height = Client.spritesMap.get(5761).myHeight;
        tab.tooltip = name;
        tab.description = description;
        tab.isLocked = isLocked;
        tab.hoverable = true;
        tab.atActionType = 1;
    }

    public static void add_tooltip(int componentId, String messageOnTooltip, int imageForXY, int image) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = TOOLTIP_COMPONENT;
        tab.message = messageOnTooltip;
        tab.width = Client.spritesMap.get(imageForXY).myWidth;
        tab.height = Client.spritesMap.get(imageForXY).myHeight;
        tab.buttonOn = Client.spritesMap.get(image);
    }

    public static void add_node(int componentId, int enabled, int disabled, String tooltip) {
        RSInterface tab = interfaceCache[componentId] = new RSInterface();
        tab.id = componentId;
        tab.parentID = componentId;
        tab.type = NODE_COMPONENT;
        tab.buttonOn = Client.spritesMap.get(enabled);
        tab.buttonOff = Client.spritesMap.get(disabled);
        tab.width = Client.spritesMap.get(enabled).myWidth;
        tab.height = Client.spritesMap.get(enabled).myHeight;
        tab.tooltip = tooltip;
        tab.hoverable = true;
        tab.atActionType = 1;
        tab.isLocked = true;
    }
}
