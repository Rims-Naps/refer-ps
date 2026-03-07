package org.necrotic.client.cache.media.rsinterface;

import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.Signlink;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.cache.media.TextDrawingArea;

import java.util.LinkedList;

public abstract class Interfaces {

	public static final LinkedList<Interfaces> interfaces = new LinkedList<>();

	
	public abstract void load(TextDrawingArea[] fonts);
	
	public abstract boolean button(int button);
	
	public abstract boolean configPacketReceived(int config, int value);
	
	public abstract boolean configButtonClicked(int config, int value);
	
	public boolean onInputFieldEdited(int child) {
		return false;
	}
	
	public boolean containerUpdate(int interfaceId) {
		return false;
	}
	
	public static boolean inputFieldEdited(int child) {
		for (Interfaces i : interfaces) {
			if (i.onInputFieldEdited(child)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean containerUpdated(int interfaceId) {
		for (Interfaces i : interfaces) {
			if (i.containerUpdate(interfaceId)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean buttonClicked(int button) {
		for (Interfaces i : interfaces) {
			if (i.button(button)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean configPacket(int config, int value) {
		for (Interfaces i : interfaces) {
			if (i.configPacketReceived(config, value)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean configButton(int config, int value) {
		for (Interfaces i : interfaces) {
			if (i.configButtonClicked(config, value)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void loadInterfaces(TextDrawingArea[] fonts) {
		if (interfaces.isEmpty()) {
			interfaces.add(new InterfaceTradingPost());
		}

		for (Interfaces i : interfaces) {
			try {
				i.load(fonts);
			} catch (Exception e) {
				for (StackTraceElement ele : e.getStackTrace()) {
					System.out.println(ele.getClassName() + " " + ele.getLineNumber());
				}
				e.printStackTrace();
				Signlink.reportError("Error loading interface " + i.getClass());
			}
		}
	}

	public static void addTransparentSprite(int id, int spriteId, int opacity) {
		RSInterface tab = RSInterface.addInterface(id);
		tab.id = id;
		tab.parentID = id;
		tab.type = 88;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte) opacity;
		tab.customOpacity = opacity;
		tab.hoverType = 52;
		tab.disabledSprite = Client.spritesMap.get(spriteId);
		tab.enabledSprite = Client.spritesMap.get(spriteId);
		tab.width = 512;
		tab.height = 334;
		tab.drawsTransparent = true;
	}

	public static void addPixels(int id, int color, int width, int height, int alpha, boolean filled) {
		RSInterface rsi = RSInterface.addInterface(id);
		rsi.type = 3;
		rsi.opacity = (byte)alpha;
		rsi.disabledColor = color;
		rsi.disabledMouseOverColor = color;
		rsi.enabledColor = color;
		rsi.enabledMouseOverColor = color;
		rsi.filled = filled;
		rsi.width = width;
		rsi.height = height;
	}

	public static RSInterface addText(int id, String s, int color, boolean centerText, TextDrawingArea[] fonts, int fontIndex) {
		return addText(id, s, color, centerText, true, 0, fonts, fontIndex, 0, 1, 1);
	}

	public static void newHoverButton(int id, String tooltip, int enabledSprite, int disabledSprite) {
		RSInterface tab = RSInterface.addInterface(id);
		tab.tooltip = tooltip;
		tab.atActionType = 1;
		tab.type = 200;
		tab.enabledSprite = Client.spritesMap.get(enabledSprite);
		tab.disabledSprite = Client.spritesMap.get(disabledSprite);
		tab.width = Client.spritesMap.get(enabledSprite).myWidth;
		tab.height = Client.spritesMap.get(enabledSprite).myHeight;
		tab.toggled = false;
	}

	public static Sprite addSprite(int id, int sprite) {
		RSInterface tab = RSInterface.addInterface(id);
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 0;
		tab.contentType = 0;
		tab.opacity = (byte)0;
		tab.hoverType = 52;
		tab.enabledSprite = Client.spritesMap.get(sprite);
		tab.disabledSprite = Client.spritesMap.get(sprite);
		tab.width = tab.enabledSprite.myWidth;
		tab.height = tab.enabledSprite.myHeight;
		return Client.spritesMap.get(sprite);
	}

	public static void addConfigSprite(int interfaceID, int spriteId, int spriteId2, int configFrame, int configId) {
		RSInterface hover = RSInterface.addInterface(interfaceID);
		hover.id = interfaceID;
		hover.parentID = interfaceID;
		hover.type = 5;
		hover.atActionType = 0;
		hover.contentType = 0;
		hover.opacity = 0;
		hover.hoverType = 0;
		hover.disabledSprite = Client.spritesMap.get(spriteId);
		hover.enabledSprite = spriteId2 == -1 ? null : Client.spritesMap.get(spriteId2);
		hover.width = Client.spritesMap.get(spriteId).myWidth;
		hover.height = Client.spritesMap.get(spriteId).myHeight;
		hover.valueCompareType = new int[1];
		hover.requiredValues = new int[1];
		hover.valueCompareType[0] = 1;
		hover.requiredValues[0] = configId;
		hover.valueIndexArray = new int[1][3];
		hover.valueIndexArray[0][0] = 5;
		hover.valueIndexArray[0][1] = configFrame;
		hover.valueIndexArray[0][2] = 0;
	}

	public static void addButton(int id, int width, int height, String tooltip) {
		RSInterface tab = RSInterface.addInterface(id);
		tab.id = id;
		tab.parentID = id;
		tab.type = 5;
		tab.atActionType = 1;
		tab.contentType = 0;
		tab.opacity = (byte) 0;
		tab.hoverType = 52;
		tab.enabledSprite = null;
		tab.disabledSprite = null;
		tab.width = width;
		tab.height = height;
		tab.tooltip = tooltip;
	}

	public static RSInterface addItemContainer(int id, int width, int height, String[] actions) {
		return addItemContainer(id, width, height, actions, 10, 4);
	}

	public static RSInterface addItemContainer(int id, int width, int height, String[] actions, int spritePadX, int spritePadY) {
		RSInterface tab = RSInterface.addInterface(id);
		tab.inv = new int[width * height];
		tab.invStackSizes = new int[width * height];
		for (int i1 = 0; i1 < width * height; i1++) {
			tab.invStackSizes[i1] = 0; // inv item stack size
			tab.inv[i1] = 0; // inv item ids
		}
		tab.spritesY = new int[width * height];
		tab.spritesX = new int[width * height];
		/*for (int i2 = 0; i2 < width * height; i2++) {
			Tab.spritesY[i2] = 0;
			Tab.spritesX[i2] = 0;
		}*/
		tab.invSpritePadX = spritePadX;
		tab.invSpritePadY = spritePadY;
		tab.setActions(actions);
		tab.width = width;
		tab.hoverType = -1;
		tab.parentID = id;
		tab.id = id;
		tab.scrollMax = 0;
		tab.type = 2;
		tab.height = height;
		return tab;
	}
	
	public static RSInterface addText(int id, String s, int color, boolean centerText, boolean textShadow, int hoverType,
                                      TextDrawingArea[] fonts, int fontIndex, int unknown, int width, int height) {
        RSInterface rsinterface = RSInterface.addInterface(id);
        rsinterface.parentID = id;
        rsinterface.id = id;
        rsinterface.type = 4;
        rsinterface.atActionType = 1;
        rsinterface.width = width;
        rsinterface.height = height;
        rsinterface.contentType = 0;
        rsinterface.opacity = 0;
        rsinterface.hoverType = hoverType;
        rsinterface.centerText = centerText;
        rsinterface.textShadow = textShadow;
        rsinterface.textDrawingAreas = fonts[fontIndex];
        rsinterface.message = s;
        rsinterface.aString228 = "";
		rsinterface.enabledColor = 0;
        rsinterface.disabledColor = color;
		rsinterface.disabledMouseOverColor = unknown;
		rsinterface.tooltip = s;
		return rsinterface;
    }
	
	public static void addConfigButton(int ID, int pID, int bID, int bID2,
									   String spriteLocation, int width, int height, String tT, int configID, int aT,
									   int configFrame) {
		RSInterface Tab = RSInterface.addTabInterface(ID);
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
		Tab.disabledSprite = Client.spritesMap.get(bID);
		Tab.enabledSprite = Client.spritesMap.get(bID2);
		Tab.tooltip = tT;
	}
	
	public static void addClickableText(int id, String text, String tooltip, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow, int width, int height) {
        RSInterface tab = RSInterface.addInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = 4;
        tab.atActionType = 1;
        tab.width = width;
        tab.height = height;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.centerText = center;
        tab.textShadow = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.message = text;
        tab.aString228 = "";
        tab.disabledColor = color;
        tab.enabledColor = 0;
        tab.disabledMouseOverColor = 0xffffff;
        tab.enabledMouseOverColor = 0;
        tab.tooltip = tooltip;
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
}
