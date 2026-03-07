package org.necrotic.client.gameframe.impl;

import org.necrotic.Configuration;
import org.necrotic.client.Client;
import org.necrotic.client.cache.definition.EntityDef;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.collection.NodeList;
import org.necrotic.client.util.PlayerUtilities;
import org.necrotic.client.media.renderable.actor.Player;
import org.necrotic.client.media.Raster;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.util.TextUtilities;
import org.necrotic.client.gameframe.GameFrame;
import org.necrotic.client.media.renderable.actor.Npc;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Iterator;

public class MapArea extends GameFrame {

	public class Orb {

		private int drawX, drawY;
		private double fillOrb;
		private boolean orbActive;
		private final OrbType type;
		private OrbValue values;

		public Orb(OrbType type) {
			this.type = type;
		}

		public void draw(Client client, int xPos, int yPos, ScreenMode screenMode) {
			drawX = xPos;
			drawY = yPos;
			int currentValue = values.getCurrentValue();
			int maxValue = values.getMaxValue();
            //System.out.println("currentValue: " + currentValue);
            //System.out.println("maxValue: " + maxValue);
			if (type == OrbType.HITPOINTS) {
				if (!Configuration.CONSTITUTION_ENABLED) {
					currentValue = currentValue / 10;
					maxValue = maxValue / 10;
				}
			}
			int level = (int) ((double) currentValue / (double) maxValue * 100D);
            if (type == OrbType.SPEC) {
                Client.spritesMap.get(1688).drawSprite(xPos - 2, yPos - 2);
            }else if (type != OrbType.NECRO)
			    Client.spritesMap.get(screenMode == ScreenMode.FIXED ? isHovering(client, xPos, yPos) && type != OrbType.HITPOINTS ? 334 : 16 : isHovering(client, xPos, yPos) && type != OrbType.HITPOINTS ? 366 : 15).drawSprite(xPos, yPos);
            else
                Client.spritesMap.get(screenMode == ScreenMode.FIXED ? isHovering(client, xPos, yPos) && type != OrbType.HITPOINTS ? 5708 : 5710 : isHovering(client, xPos, yPos) && type != OrbType.HITPOINTS ? 5709 : 5707).drawSprite(xPos, yPos);
            if (type != OrbType.NECRO && type != OrbType.SPEC) {
                if (level >= 101) {
                    client.newSmallFont.drawCenteredString(Integer.toString(currentValue), xPos + (screenMode == ScreenMode.FIXED ? 42 : 15), yPos + 26, 65280, 0);
                } else if (level <= 100 && level >= 75) {
                    client.newSmallFont.drawCenteredString(Integer.toString(currentValue), xPos + (screenMode == ScreenMode.FIXED ? 42 : 15), yPos + 26, 65280, 0);
                } else if (level <= 74 && level >= 50) {
                    client.newSmallFont.drawCenteredString(Integer.toString(currentValue), xPos + (screenMode == ScreenMode.FIXED ? 42 : 15), yPos + 26, 0xffff00, 0);
                } else if (level <= 49 && level >= 25) {
                    client.newSmallFont.drawCenteredString(Integer.toString(currentValue), xPos + (screenMode == ScreenMode.FIXED ? 42 : 15), yPos + 26, 0xfca607, 0);
                } else if (level <= 24 && level >= 0) {
                    client.newSmallFont.drawCenteredString(Integer.toString(currentValue), xPos + (screenMode == ScreenMode.FIXED ? 42 : 15), yPos + 26, 0xf50d0d, 0);
                }
            } else if (type == OrbType.NECRO){
                client.newSmallFont.drawCenteredString(RSInterface.interfaceCache[48313].message, xPos + (screenMode == ScreenMode.FIXED ? 42 : 15) + 5, yPos + 26, 0xAF70C3, 0);
            }
            if (type == OrbType.SPEC) {
                Client.spritesMap.get(orbActive ? type.getSpriteIDs()[1] : type.getSpriteIDs()[0]).drawSprite(xPos + 3, yPos + 3);
                double percent = level / 100D;
                fillOrb = 27 * percent;
                int depleteFill = 27 - (int) fillOrb;
                Client.spritesMap.get(isHovering(client, xPos, yPos, true) ? type.getSpriteIDs()[1] : type.getSpriteIDs()[0]).drawSprite(xPos + 3, yPos + 3);
                Client.spritesMap.get(327).myHeight = depleteFill;
                Client.spritesMap.get(327).drawSprite(xPos + 3, yPos + 3);
                Client.spritesMap.get(type.getSpriteIDs()[2]).drawSprite(xPos + 8 + type.getOffSets()[0], yPos + 8 + type.getOffSets()[1]);
            } else if (type != OrbType.NECRO) {
                Client.spritesMap.get(orbActive ? type.getSpriteIDs()[1] : type.getSpriteIDs()[0]).drawSprite(xPos + (screenMode == ScreenMode.FIXED ? 3 : 27), yPos + 3);
                double percent = level / 100D;
                fillOrb = 27 * percent;
                int depleteFill = 27 - (int) fillOrb;
                Client.spritesMap.get(327).myHeight = depleteFill;
                Client.spritesMap.get(327).drawSprite(xPos + (screenMode == ScreenMode.FIXED ? 3 : 27), yPos + 3);
                Client.spritesMap.get(type.getSpriteIDs()[type == OrbType.RUN ? orbActive ? 3 : 2 : 2]).drawSprite(xPos + (screenMode == ScreenMode.FIXED ? 9 : 33) + type.getOffSets()[0], yPos + 9 + type.getOffSets()[1]);

            } else {
                Client.spritesMap.get(orbActive ? type.getSpriteIDs()[1] : type.getSpriteIDs()[0]).drawSprite(xPos + (screenMode == ScreenMode.FIXED ? 3 : 37), yPos + 3);
                Client.spritesMap.get(type.getSpriteIDs()[type == OrbType.RUN ? orbActive ? 3 : 2 : 2]).drawSprite(xPos + (screenMode == ScreenMode.FIXED ? 9 : 43) + type.getOffSets()[0], yPos + 9 + type.getOffSets()[1]);

            }
        }

		public int getDrawX() {
			return drawX;
		}

		public int getDrawY() {
			return drawY;
		}

		public boolean getOrbState() {
			return orbActive;
		}

		public boolean isHovering(Client client, int xPos, int yPos) {
           return isHovering(client, xPos, yPos, false);
		}

        public boolean isHovering(Client client, int xPos, int yPos, boolean newType) {
            if (newType) {
                if (getScreenMode() == ScreenMode.FIXED) {
                    return client.inSprite(false, Client.spritesMap.get(1688), getxPos() + xPos, getyPos() + yPos);
                } else {
                    return client.inSprite(false, Client.spritesMap.get(1688), xPos, yPos);
                }
            } else {
                if (getScreenMode() == ScreenMode.FIXED) {
                    return client.inSprite(false, Client.spritesMap.get(15), getxPos() + xPos, getyPos() + yPos);
                } else {
                    return client.inSprite(false, Client.spritesMap.get(15), xPos, yPos);
                }
            }
        }

		public void setOrbState(boolean active) {
			orbActive = active;
		}

		public void setOrbValues(OrbValue values) {
			this.values = values;
		}

	}

	enum OrbType {

		HITPOINTS(new int[]{329, -1, 330}, new int[]{1, 2}),
        PRAYER(new int[]{331, 332, 333}, new int[]{-2, -2}),
        RUN(new int[]{337, 338, 335, 336}, new int[]{0, -1}),
        NECRO(new int[]{367, 369, 368}, new int[]{0, 0}),
        SPEC(new int[]{1685, 1686, 1687}, new int[]{-6, -5});

		private final int[] offsets;
		private final int[] spriteIDs;

		OrbType(int[] spriteIDs, int[] offsets) {
			this.spriteIDs = spriteIDs;
			this.offsets = offsets;
		}

		public int[] getOffSets() {
			return offsets;
		}

		public int[] getSpriteIDs() {
			return spriteIDs;
		}

	}

	class OrbValue {

		private final int currentValue, maxValue;

		public OrbValue(int currentValue, int maxValue) {
			this.currentValue = currentValue;
			this.maxValue = maxValue;
		}

		public int getCurrentValue() {
			return currentValue;
		}

		public int getMaxValue() {
			return maxValue;
		}
	}

	public static class XPGain {

		private int alpha = 0;

		/**
		 * The skill which gained the xp
		 */
		private final int skill;
		/**
		 * The XP Gained
		 */
		private final int xp;
		private int y;

		public XPGain(int skill, int xp) {
			this.skill = skill;
			this.xp = xp;
		}

		public void decreaseAlpha() {
			alpha -= alpha > 0 ? 30 : 0;
			alpha = alpha > 256 ? 256 : alpha;
		}

		public int getAlpha() {
			return alpha;
		}

		public int getSkill() {
			return skill;
		}

		public int getXP() {
			return xp;
		}

		public int getY() {
			return y;
		}

		public void increaseAlpha() {
			alpha += alpha < 256 ? 30 : 0;
			alpha = alpha > 256 ? 256 : alpha;
		}

		public void increaseY() {
			y++;
		}

	}

	private static final DecimalFormat df;

	static {
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setGroupingSeparator(',');
		df = new DecimalFormat("", dfs);
	}

	public Orb hitpoints = new Orb(OrbType.HITPOINTS);
	public Orb prayer = new Orb(OrbType.PRAYER);
	public Orb run = new Orb(OrbType.RUN);
	public Orb necro = new Orb(OrbType.NECRO);
    public Orb spec = new Orb(OrbType.SPEC);
	private final Orb[] allOrbs = {prayer, hitpoints, run, necro, spec};
	private final NumberFormat format = NumberFormat.getInstance();

	public MapArea(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
	}

	public void displayXPCounter(Client client) {
        int x = 0;
        int y = 0;
        int spriteOffY = 0;
        int textOffY = 0;
        int someOff = 0;
        switch (GameFrame.getScreenMode()) {
            case FIXED:
                x = 404;
                spriteOffY = 50;
                textOffY = 63;
                break;
            case RESIZABLE_CLASSIC:
/*                x = Client.clientWidth - 355;
                y = 8;
                spriteOffY = 48 + y;
                textOffY = 61 + y;
                someOff = -20;
                break;*/
            case RESIZABLE_MODERN:
                x = Client.clientWidth - 318;
                y = -36;
                spriteOffY = 48 + y;
                textOffY = 61 + y;
                someOff = -20;
                break;
        }
		int currentIndex = 0;
        int offsetY = 0;
		int stop = 70;
		Client.spritesMap.get(346).drawSprite(x, spriteOffY);
		client.normalText.drawRegularText(true, x + 3, 0xffffff, "XP:", textOffY);
		String str = df.format(PlayerUtilities.totalXP);
		int width = client.normalText.getTextWidth(str);
		if (PlayerUtilities.totalXP >= 0 && PlayerUtilities.totalXP < Long.MAX_VALUE) {
			client.normalText.drawRegularText(true, x + 99 - width, 0xffffff, str, textOffY);
		} else {
			client.normalText.drawRegularText(true, x + 99 - client.normalText.getTextWidth("Lots!"), 0xFF0000, "Lots!", textOffY);
		}

		if (!PlayerUtilities.gains.isEmpty()) {
			Iterator<XPGain> it = PlayerUtilities.gains.iterator();

			while (it.hasNext()) {
				XPGain gain = it.next();

				if (gain.getY() < stop) {
					if (gain.getY() <= 10) {
						gain.increaseAlpha();
					}

					if (gain.getY() >= stop - 10) {
						gain.decreaseAlpha();
					}

					gain.increaseY();
				} else if (gain.getY() == stop) {
					it.remove();
				}

				int spriteId = gain.getSkill() + 498;

				if (gain.getSkill() == 23) {
					spriteId = 393;
				} else if (gain.getSkill() == 24) {
					spriteId = 521;
				}

				Sprite sprite = Client.spritesMap.get(spriteId);

				if (PlayerUtilities.gains.size() > 1) {
					offsetY = someOff + currentIndex * 28;
				}

				if (gain.getY() < stop) {
					sprite.drawSprite(x + 15 - sprite.myWidth / 2, gain.getY() + offsetY + 66 - sprite.myHeight / 2, gain.getAlpha());
					client.newSmallFont.drawBasicString("<trans=" + gain.getAlpha() + ">" + format.format(gain.getXP()) + "xp", x + 30, gain.getY() + offsetY + 70, 0xCC6600, 0, false);
				}

				currentIndex++;
			}
		}
	}

	@Override
	public boolean isHovering(Client client, ScreenMode screenMode) {
		if (!isVisible()) {
			return false;
		}

		for (Orb orb : allOrbs) {
			if (orb.isHovering(client, orb.getDrawX(), orb.getDrawY())) {
				return true;
			}
		}

		if (client.inSprite(false, client.compass, getOffSetX() + (screenMode == ScreenMode.FIXED ? 6 : 5), getOffSetY() + (screenMode == ScreenMode.FIXED ? 8 : 5))) {
			return true;
		}

		return client.mouseInCircle(getOffSetX() + Client.spritesMap.get(13).myWidth / 2, getOffSetY() + Client.spritesMap.get(13).myHeight / 2, Client.spritesMap.get(13).myWidth / 2);
	}
    public boolean isHoveringMap(Client client) {
        if (!isVisible()) {
            return false;
        }
        return client.mouseInCircle(getOffSetX() + Client.spritesMap.get(13).myWidth / 2, getOffSetY() + Client.spritesMap.get(13).myHeight / 2, Client.spritesMap.get(13).myWidth / 2);
    }

	public void loadOrbs(Client client, ScreenMode screenMode) {
		try {
            int[] prayerOff = new int[2];
            int[] hitpointsOff = new int[2];
            int[] runOff = new int[2];
            int[] necroOff = new int[2];
            int[] expIconOff = new int[2];
            int[] specOff = new int[2];

            switch (GameFrame.getScreenMode()) {
                case FIXED:
                    prayerOff = new int[]{186, 54};
                    hitpointsOff = new int[]{172, 15};
                    runOff = new int[]{184, 93};
                    necroOff = new int[]{167, 129};
                    expIconOff = new int[]{2, 46};
                    specOff = new int[]{2, 85};
                    break;
                case RESIZABLE_CLASSIC:
/*                    prayerOff = new int[]{Client.clientWidth - 58, 54};
                    hitpointsOff = new int[]{Client.clientWidth - 72, 15};
                    runOff = new int[]{Client.clientWidth - 60, 93};
                    necroOff = new int[]{Client.clientWidth - 77, 129};
                    expIconOff = new int[]{2, 46};
                    break;*/
                case RESIZABLE_MODERN:
                    prayerOff = new int[]{Client.clientWidth - 215, 73};
                    hitpointsOff = new int[]{Client.clientWidth - 212, 39};
                    runOff = new int[]{Client.clientWidth - 203, 107};
                    necroOff = new int[]{Client.clientWidth - 179, 136};
                    expIconOff = new int[]{40, 2};
                    specOff = new int[]{Client.clientWidth - 74, 153};
                    break;
            }
			prayer.setOrbValues(new OrbValue(client.currentStats[5], client.maxStats[5]));
			prayer.draw(client, prayerOff[0], prayerOff[1], screenMode);
			hitpoints.setOrbValues(new OrbValue(client.currentStats[3], client.maxStats[3]));
			hitpoints.draw(client, hitpointsOff[0], hitpointsOff[1], screenMode);
			run.setOrbValues(new OrbValue(client.energy, 100));
			run.draw(client, runOff[0], runOff[1], screenMode);
			necro.setOrbValues(new OrbValue(0, 0));
			necro.draw(client, necroOff[0], necroOff[1], screenMode);
            spec.setOrbValues(new OrbValue(client.getSpecAmount(), 100));
            spec.draw(client, specOff[0], specOff[1], screenMode);
			if (client.inSprite(false, Client.spritesMap.get(345), getxPos() - expIconOff[0], getyPos() + expIconOff[1])) {
				Client.spritesMap.get(344).drawSprite(getOffSetX() - expIconOff[0], getOffSetY() + expIconOff[1]);
			} else {
				Client.spritesMap.get(345).drawSprite(getOffSetX() - expIconOff[0], getOffSetY() + expIconOff[1]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void processMinimapActions(Client client) {
        int[] compassOffsets = new int[4];
        int worldOffsetX = 0;
        int worldOffsetY = 0;
        int[] expIconOff = new int[2];
        switch (GameFrame.getScreenMode()) {
            case FIXED:
                compassOffsets = new int[]{242, 203, 6, 42};
                worldOffsetX = 6;
                worldOffsetY = 124;
                expIconOff = new int[]{2, 46};
                break;
            case RESIZABLE_CLASSIC:
/*                compassOffsets = new int[]{242, 203, 4, 45};
                worldOffsetX = 7;
                worldOffsetY = 123;
                expIconOff = new int[]{2, 46};
                break;*/
            case RESIZABLE_MODERN:
                compassOffsets = new int[]{164, 129, 0, 37};
                worldOffsetX = 130;
                worldOffsetY = 132;
                expIconOff = new int[]{40, 2};
                break;
        }
		if (run.isHovering(client, run.getDrawX(), run.getDrawY())) {
			client.menuActionName[1] = "Rest";
			client.menuActionID[1] = 1036;
			client.menuActionName[2] = !run.getOrbState() ? "Turn run on" : "Turn run off";
			client.menuActionID[2] = 1050;
			client.menuActionRow = 3;
		}

		if (prayer.isHovering(client, prayer.getDrawX(), prayer.getDrawY())) {
			String prayerType = (client.prayerInterfaceType == 5608) ? "prayerss" : "prayers";
			boolean inProcess = (Client.tabInterfaceIDs[5] == 17200 || Client.tabInterfaceIDs[5] == 17234);
			client.menuActionName[client.menuActionRow] = (inProcess ? "Finish" : "Select") + " " + "quick " + prayerType + (inProcess ? " selection" : "");
			client.menuActionID[client.menuActionRow] = 1046;
			client.menuActionRow++;
			client.menuActionName[client.menuActionRow] = "Turn quick " + prayerType + " " + (prayer.getOrbState() ? "off" : "on");
			client.menuActionID[client.menuActionRow] = 1045;
			client.menuActionRow++;
		}

		if (necro.isHovering(client, necro.getDrawX(), necro.getDrawY())) {
			client.menuActionName[1] = "Summon Last";
			client.menuActionID[1] = 1041;
			client.menuActionName[2] = "Quick Summon";
			client.menuActionID[2] = 1037;
			client.menuActionRow = 2;
		}

        if (spec.isHovering(client, spec.getDrawX(), spec.getDrawY(), true)) {
            client.menuActionName[1] = "Use Special";
            client.menuActionID[1] = 1040;
            client.menuActionRow = 2;
        }

		if (client.getClickMode2() == 1 && client.mouseX >= Client.clientWidth - Client.spritesMap.get(342).myWidth && client.mouseX < Client.clientWidth && client.mouseY >= 0 && client.mouseY <= Client.spritesMap.get(342).myHeight) {
			if (client.tabArea.componentHidden()) {
				client.tabArea.setHideComponent(false);
			}
			Client.setTab(14);
		}

		if (client.inSprite(false, Client.spritesMap.get(345), getxPos() - expIconOff[0], getyPos() + expIconOff[1])) {
			client.menuActionName[1] = "Toggle Exp Lock";
			client.menuActionID[1] = 1095;
			client.menuActionName[2] = "Reset counter";
			client.menuActionID[2] = 1013;
			client.menuActionName[3] = PlayerUtilities.showXP ? "Hide counter" : "Show counter";
			client.menuActionID[3] = 1006;
			client.menuActionRow = 4;
		}
		if (client.inSprite(false, Client.spritesMap.get(457), getxPos() + worldOffsetX, getyPos() + worldOffsetY)) { //handleglobe
            client.menuActionName[2] = "Open Teleport Menu";
            client.menuActionID[2] = 1716;
            client.menuActionName[1] = "Quick Teleport";
            client.menuActionID[1] = 1717;
			client.menuActionRow = 3;
		}

		if (client.mouseX >= Client.clientWidth - compassOffsets[0] && client.mouseX <= Client.clientWidth - compassOffsets[1] && client.mouseY > compassOffsets[2] && client.mouseY < compassOffsets[3]) {
			client.menuActionName[1] = "Face North";
			client.menuActionID[1] = 1014;
			client.menuActionRow = 2;
		}
	}

	@Override
	protected void render(Client client, ScreenMode screenMode) {
		if (isVisible()) {
			client.rotateCam(); //rotate cam
			if (screenMode == ScreenMode.FIXED) {
				client.mapAreaIP.initDrawingArea();
			}

			if (client.minimapStatus == 2) {
				loadOrbs(client, screenMode);
				client.compass.rotate(33, client.cameraRotation, client.compassArray2, 256, client.compassArray1, 25, getOffSetY() + (screenMode == ScreenMode.FIXED ? 8 : 4), getOffSetX() + (screenMode == ScreenMode.FIXED ? 7 : 5), 33, 25);
				client.gameScreenIP.initDrawingArea();
				return;
			}

			int i = client.cameraRotation + client.minimapRotation & 0x7ff;
			int playerPosX = 48 + Client.myPlayer.x / 32;
			int playerPosY = 464 - Client.myPlayer.y / 32;
			/*
			 * for (int j1 = 0; j1 < client.anIntArray1229.length; j1++) {
			 * client.anIntArray1229[j1] = 172; client.anIntArray1052[j1] = -22;
			 * }
			 */
            /**
             * Minimap map drawing, and Compass
             */
            switch (screenMode) {
                case FIXED:
                    client.miniMapRegions.rotate(152, i, client.mapImagePixelCutRight, 256 + client.minimapZoom, client.mapImagePixelCutLeft, playerPosY, getOffSetY() + 10, getOffSetX() + 31, 152, playerPosX);
                    client.compass.rotate(33, client.cameraRotation, client.compassArray2, 256, client.compassArray1, 25, getOffSetY() + 8, getOffSetX() + 7, 33, 25);
                    break;
                case RESIZABLE_CLASSIC:
/*                    client.miniMapRegions.rotate(152, i, client.mapImagePixelCutRight, 256 + client.minimapZoom, client.mapImagePixelCutLeft, playerPosY, getOffSetY() + 9, getOffSetX() + 32, 152, playerPosX);
                    client.compass.rotate(33, client.cameraRotation, client.compassArray2, 256, client.compassArray1, 25, getOffSetY() + 6, getOffSetX() + 8, 33, 25);
                    break;*/
                case RESIZABLE_MODERN:
                    client.miniMapRegions.rotate(152, i, client.mapImagePixelCutRight, 256 + client.minimapZoom, client.mapImagePixelCutLeft, playerPosY, getOffSetY() + 5, getOffSetX() + 11, 152, playerPosX);
                    client.compass.rotate(33, client.cameraRotation, client.compassArray2, 256, client.compassArray1, 25, getOffSetY() + 5, getOffSetX() + 5, 33, 25);
                    break;
            }

			for (int j5 = 0; j5 < client.anInt1071; j5++) {
				try {
					int mapX = client.anIntArray1072[j5] * 4 + 2 - Client.myPlayer.x / 32;
					int mapY = client.anIntArray1073[j5] * 4 + 2 - Client.myPlayer.y / 32;
					client.markMinimap(client.aClass30_Sub2_Sub1_Sub1Array1140[j5], mapX, mapY);
					for (int iconI = 0; iconI < client.customMinimapIcons.size(); iconI++) {
						client.markMinimap(client.customMinimapIcons.get(iconI).getSprite(), ((client.customMinimapIcons.get(iconI).getX() - client.regionBaseX) * 4 + 2) - Client.myPlayer.x / 32, ((client.customMinimapIcons.get(iconI).getY() - client.regionBaseY) * 4 + 2) - Client.myPlayer.y / 32);
					}
				} catch (Exception exception) {
				}
			}

			for (int k5 = 0; k5 < 104; k5++) {
				for (int l5 = 0; l5 < 104; l5++) {
					NodeList class19 = client.groundArray[client.plane][k5][l5];
					if (class19 != null) {

						int l = k5 * 4 + 2 - Client.myPlayer.x / 32;
						int j3 = l5 * 4 + 2 - Client.myPlayer.y / 32;
						client.markMinimap(client.mapDotItem, l, j3);
					}
				}

			}

			for (int i6 = 0; i6 < client.npcCount; i6++) {
				Npc npc = client.npcArray[client.npcIndices[i6]];
				if (npc != null && npc.isVisible()) {
					EntityDef entityDef = npc.definitionOverride;
					if (entityDef.childrenIDs != null) {
						entityDef = entityDef.method161();
					}
					if (entityDef != null && entityDef.drawYellowDotOnMap && entityDef.disableRightClick) {
						int i1 = npc.x / 32 - Client.myPlayer.x / 32;
						int k3 = npc.y / 32 - Client.myPlayer.y / 32;
						client.markMinimap(client.mapDotNPC, i1, k3);
					}
				}
			}

			for (int j6 = 0; j6 < client.playerCount; j6++) {
				Player player = client.playerArray[client.playerIndices[j6]];
				if (player != null && player.isVisible()) {
					int x = player.x / 32 - Client.myPlayer.x / 32;
					int y = player.y / 32 - Client.myPlayer.y / 32;
					boolean isInFriends = false;
					boolean isInClan = false;
					for (int j3 = 0; j3 < client.clanMembers.length; j3++) {
						if (client.clanMembers[j3] == null) {
							continue;
						}
						if (!client.clanMembers[j3].equalsIgnoreCase(player.name)) {
							continue;
						}
						isInClan = true;
						break;
					}
					/*
					 * for (int k6 = 0; k6 < PlayerUtilities.friends.size(); k6++)
					 * { if
					 * (!PlayerUtilities.friends.get(k6).getName().equals(player
					 * .name) || PlayerUtilities.friends.get(k6).getState() == 0)
					 * { continue; } flag1 = true; break; }
					 */
					long l6 = TextUtilities.longForName(player.name);
					for (int k6 = 0; k6 < client.friendCount; k6++) {
						if (l6 != client.friendsListAsLongs[k6] || client.friendsNodeIDs[k6] == 0) {
							continue;
						}
						isInFriends = true;
						break;
					}
					boolean isInTeam = false;
					if (Client.myPlayer.team != 0 && player.team != 0 && Client.myPlayer.team == player.team) {
						isInTeam = true;
					}
					if (isInFriends) {
						client.markMinimap(client.mapDotFriend, x, y);
					} else if (isInClan) {
						client.markMinimap(client.mapDotClan, x, y);
					} else if (isInTeam) {
						client.markMinimap(client.mapDotTeam, x, y);
					} else {
						client.markMinimap(client.mapDotPlayer, x, y);
					}
				}
			}

			if (client.hintIconDrawType != 0 && Client.loopCycle % 20 < 10) {
				if (client.hintIconDrawType == 1 && client.hintIconNpcId >= 0 && client.hintIconNpcId < client.npcArray.length) {
					Npc class30_sub2_sub4_sub1_sub1_1 = client.npcArray[client.hintIconNpcId];
					if (class30_sub2_sub4_sub1_sub1_1 != null) {
						int k1 = class30_sub2_sub4_sub1_sub1_1.x / 32 - Client.myPlayer.x / 32;
						int i4 = class30_sub2_sub4_sub1_sub1_1.y / 32 - Client.myPlayer.y / 32;
                        client.refreshMinimap(client.mapMarker, i4, k1);
					}
				}

				//Drawing the local map region from world map button.
				if (client.hintIconDrawType == 2) {
					int l1 = (client.hintIconX - client.regionBaseX) * 4 + 2 - Client.myPlayer.x / 32;
					int j4 = (client.hintIconY - client.regionBaseY) * 4 + 2 - Client.myPlayer.y / 32;
					client.refreshMinimap(client.mapMarker, j4, l1);
				}

				if (client.hintIconDrawType == 10 && client.hintIconPlayerId >= 0 && client.hintIconPlayerId < client.playerArray.length) {
					Player class30_sub2_sub4_sub1_sub2_1 = client.playerArray[client.hintIconPlayerId];
					if (class30_sub2_sub4_sub1_sub2_1 != null) {
						int i2 = class30_sub2_sub4_sub1_sub2_1.x / 32 - Client.myPlayer.x / 32;
						int k4 = class30_sub2_sub4_sub1_sub2_1.y / 32 - Client.myPlayer.y / 32;
                        client.refreshMinimap(client.mapMarker, k4, i2);
					}
				}
			}

			if (client.destinationX != 0) {
				int j2 = client.destinationX * 4 + 2 - Client.myPlayer.x / 32;
				int l4 = client.destinationY * 4 + 2 - Client.myPlayer.y / 32;
				client.markMinimap(client.mapFlag, j2, l4);
			}
            int logoutButtonXOff = 0;
            int logoutButtonYOff = 0;
            int worldOffsetX = 0;
            int worldOffsetY = 0;

            /**
             * Minimap sprite drawing
             */
            switch (screenMode) {
                case FIXED:
                    Client.spritesMap.get(14).drawSprite(getOffSetX() - 1, getOffSetY());
                    logoutButtonXOff = 225;
                    worldOffsetX = 6;
                    worldOffsetY = 124;
                    break;
                case RESIZABLE_CLASSIC:
/*                    Client.spritesMap.get(14).drawSprite(getOffSetX(), getOffSetY() - 1);
                    logoutButtonXOff = 246 - Client.spritesMap.get(341).myWidth;
                    worldOffsetX = 7;
                    worldOffsetY = 123;
                    break;*/
                case RESIZABLE_MODERN:
                    Client.spritesMap.get(13).drawSprite(getOffSetX(), getOffSetY() - 1);
                    logoutButtonXOff = 149;
                    worldOffsetX = 130;
                    worldOffsetY = 132;
                    break;
            }

			/**
			 * Ring Around World Map Icon
			 */
			if (screenMode != ScreenMode.FIXED) {
				Client.spritesMap.get(461).drawSprite(getOffSetX() + 125, getOffSetY() + 128);
			}
            /**
             * World Map Icon
             */
			if (client.inSprite(false, Client.spritesMap.get(2101), getxPos() + worldOffsetX, getyPos() + worldOffsetY)) {
				Client.spritesMap.get(2102).drawSprite(getOffSetX() + worldOffsetX, getOffSetY() + worldOffsetY);

			} else {
				Client.spritesMap.get(2101).drawSprite(getOffSetX() + worldOffsetX, getOffSetY() + worldOffsetY);
			}

			/*
			 * Orbs
			 */
			loadOrbs(client, screenMode);

			/*
			 * Logout button
			 */
			if (client.mouseX >= Client.clientWidth - Client.spritesMap.get(342).myWidth && client.mouseX < Client.clientWidth && client.mouseY >= 0 && client.mouseY <= Client.spritesMap.get(342).myHeight) {
				Client.spritesMap.get(342).drawSprite(getOffSetX() + logoutButtonXOff, logoutButtonYOff);
			} else {
				Client.spritesMap.get(341).drawSprite(getOffSetX() + logoutButtonXOff, logoutButtonYOff);
			}

			if (client.inSprite(false, Client.spritesMap.get(342), getxPos() + logoutButtonXOff, logoutButtonYOff)) {
				Client.spritesMap.get(342).drawSprite(getOffSetX() + logoutButtonXOff, logoutButtonYOff);
			} else {
				Client.spritesMap.get(341).drawSprite(getOffSetX() + logoutButtonXOff, logoutButtonYOff);
			}


			if (Client.tabID == 14) {
				Client.spritesMap.get(343).drawSprite(getOffSetX() + logoutButtonXOff, logoutButtonYOff);
			}

            switch (screenMode) {
                case FIXED:
                    Raster.drawPixels(3, 76 + 8 + getOffSetY(), 76 + 29 + getOffSetX(), 0xffffff, 3);
                    break;
                case RESIZABLE_CLASSIC:
/*                    Raster.drawPixels(3, 76 + 7 + getOffSetY(), 76 + 30 + getOffSetX(), 0xffffff, 3);
                    break;*/
                case RESIZABLE_MODERN:
                    Raster.drawPixels(3, 76 + 4 + getOffSetY(), 76 + 9 + getOffSetX(), 0xffffff, 3);
                    break;
            }

			if (client.menuOpen && client.menuScreenArea == 3) {
				client.drawMenu();
			}

		}
		client.gameScreenIP.initDrawingArea();

	}

}
