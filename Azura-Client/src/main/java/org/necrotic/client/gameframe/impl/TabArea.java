package org.necrotic.client.gameframe.impl;

import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.constants.GameFrameConstants;
import org.necrotic.client.constants.GameFrameConstants.GameFrameType;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.gameframe.GameFrame;
import org.necrotic.client.media.Rasterizer3D;

public class TabArea extends GameFrame {

	public TabArea(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
	}

	/**
	 * Draws the RedStones based on tabID
	 *
	 * @param client
	 * @param screenMode
	 */
	private void drawRedStones(Client client, ScreenMode screenMode) {
		if (isVisible()) {
			if (Client.tabID == -1) {
				return;
			}

            int[][] fixed = new int[][]{{8, 0, 0}, {12, 38, 0}, {12, 71, 0}, {12, 104, 0}, {12, 137, 0}, {12, 170, 0}, {9, 203, 0},
                {10, 0, 298}, {12, 38, 298}, {12, 71, 298}, {12, 104, 298}, {12, 137, 298}, {12, 170, 298}, {11, 203, 298}};
            int[][] classic = new int[][]{{8, -5, 1}, {12, 33, 1}, {12, 66, 1}, {12, 99, 1}, {12, 132, 1}, {12, 165, 1}, {9, 198, 1},
                {10, -5, 299}, {12, 33, 299}, {12, 66, 299}, {12, 99, 299}, {12, 132, 299}, {12, 165, 299}, {11, 198, 299}};
            int[][] modern = new int[][]{{12, 0, 260}, {12, 33, 260}, {12, 66, 260}, {12, 99, 260}, {12, 132, 260}, {12, 165, 260}, {12, 198, 260},
                {12, 0, 296}, {12, 33, 296}, {12, 66, 296}, {12, 99, 296}, {12, 132, 296}, {12, 165, 296}, {12, 198, 296}};

            if (client.invOverlayInterfaceID == -1 && Client.tabInterfaceIDs[Client.tabID] != -1) {
                for (int i = 0; i < 14; i++) {
                    int tabID = i;
                    if (i == 7) {
                        tabID = 13;
                    } else if (i == 10) {
                        //	tabID = 7;
                    } else if (i == 13) {
                        tabID = 15;
                    }
                    int[][] dataset = screenMode == ScreenMode.FIXED ? fixed : screenMode == ScreenMode.RESIZABLE_CLASSIC ? classic : modern;

                    if (Client.tabID == tabID) {
                        Client.spritesMap.get(dataset[i][0]).drawSprite(getOffSetX() + 6 + dataset[i][1], getOffSetY() + dataset[i][2]);
                    }
                }
			}
		}
	}

	/**
	 * Draws the side icons
	 *
	 * @param client
	 * @param screenMode
	 */
	private void drawSideIcons(Client client, ScreenMode screenMode) {
		if (isVisible()) {
            for (int i = 0; i < 14; i++) {
                if (client.invOverlayInterfaceID == -1) {
                    Sprite sideIcon = getSideIconForOldFrame(i);
                    if (sideIcon != null) {
                        int[] xx = getSideIconPosForOldFrame(screenMode, i);
                        sideIcon.drawSprite(xx[0], xx[1]);
                    }
                }
            }
		}
	}

	@Override
	public boolean isHovering(Client client, ScreenMode screenMode) {
		if (!isVisible()) {
			return false;
		}
        switch (screenMode) {
            case FIXED:
                boolean hovering = client.mouseX >= getxPos() && client.mouseX <= getxPos() + getWidth() && client.mouseY >= getyPos() && client.mouseY <= getyPos() + getHeight();
                    return hovering;
            case RESIZABLE_CLASSIC:
                if (client.inSprite(false, Client.spritesMap.get(5699), getOffSetX(), getOffSetY()))
                    return true;
            case RESIZABLE_MODERN:
                if (client.inSprite(false, Client.spritesMap.get(7), getOffSetX() + 33, getOffSetY() - 13) && !componentHidden())
                    return true;
        }
        /**
         * draw redstones hovers
         */
        int[][] fixed = new int[][]{{8, 0, 0}, {12, 38, 0}, {12, 71, 0}, {12, 104, 0}, {12, 137, 0}, {12, 170, 0}, {9, 203, 0},
            {10, 0, 298}, {12, 38, 298}, {12, 71, 298}, {12, 104, 298}, {12, 137, 298}, {12, 170, 298}, {11, 203, 298}};
        int[][] classic = new int[][]{{8, -5, 1}, {12, 33, 1}, {12, 66, 1}, {12, 99, 1}, {12, 132, 1}, {12, 165, 1}, {9, 198, 1},
            {10, -5, 299}, {12, 33, 299}, {12, 66, 299}, {12, 99, 299}, {12, 132, 299}, {12, 165, 299}, {11, 198, 299}};
        int[][] modern = new int[][]{{12, 0, 260}, {12, 33, 260}, {12, 66, 260}, {12, 99, 260}, {12, 132, 260}, {12, 165, 260}, {12, 198, 260},
            {12, 0, 296}, {12, 33, 296}, {12, 66, 296}, {12, 99, 296}, {12, 132, 296}, {12, 165, 296}, {12, 198, 296}};
        int[][] dataset = screenMode == ScreenMode.FIXED ? fixed : screenMode == ScreenMode.RESIZABLE_CLASSIC ? classic : modern;


        for (int i = 0; i < 14; i++) {
            if (client.inSprite(false, Client.spritesMap.get(dataset[i][0]), getxPos() + 6 + dataset[i][1], getOffSetY() + dataset[i][2])) {
                return true;
            }
        }
		return false;
	}

	/**
	 * Processes the tab click
	 *
	 * @param client
	 * @param screenMode
	 */
	public void processTabClick(Client client, ScreenMode screenMode) {
		if (isVisible()) {
			if (client.clickMode3 == 1) {
                for (int i = 0; i < 14; i++) {
                    int tabID = i;
                    if (i == 7) {
                        tabID = 13;
                    } else if (i == 10) {
                        //tabID = 7;
                    } else if (i == 13) {
                        tabID = 15;
                    }
                    int[][] fixed = new int[][]{{8, 0, 0}, {12, 38, 0}, {12, 71, 0}, {12, 104, 0}, {12, 137, 0}, {12, 170, 0}, {9, 203, 0},
                        {10, 0, 298}, {12, 38, 298}, {12, 71, 298}, {12, 104, 298}, {12, 137, 298}, {12, 170, 298}, {11, 203, 298}};
                    int[][] classic = new int[][]{{8, -5, 1}, {12, 33, 1}, {12, 66, 1}, {12, 99, 1}, {12, 132, 1}, {12, 165, 1}, {9, 198, 1},
                        {10, -5, 299}, {12, 33, 299}, {12, 66, 299}, {12, 99, 299}, {12, 132, 299}, {12, 165, 299}, {11, 198, 299}};
                    int[][] modern = new int[][]{{12, 0, 260}, {12, 33, 260}, {12, 66, 260}, {12, 99, 260}, {12, 132, 260}, {12, 165, 260}, {12, 198, 260},
                        {12, 0, 296}, {12, 33, 296}, {12, 66, 296}, {12, 99, 296}, {12, 132, 296}, {12, 165, 296}, {12, 198, 296}};
                    int[][] dataset = screenMode == ScreenMode.FIXED ? fixed : screenMode == ScreenMode.RESIZABLE_CLASSIC ? classic : modern;
                    if (client.inSprite(true, Client.spritesMap.get(dataset[i][0]), getxPos() + 6 + dataset[i][1], getyPos() + dataset[i][2])) {
                        if (screenMode == ScreenMode.RESIZABLE_MODERN) {
                            setHideComponent(Client.tabID != i ? false : componentHidden() ? false : true);
                        }
                        if (componentHidden()) {
                            tabID = -1;
                        }
                        Client.setTab(tabID);
                        break;
                    }
                }
			}
		}
	}

	@Override
	protected void render(Client client, ScreenMode screenMode) {
		if (isVisible()) {
			if (screenMode == ScreenMode.FIXED) {
				if (client.tabAreaIP != null) {
					client.tabAreaIP.initDrawingArea();
				}
			}
			Rasterizer3D.lineOffsets = client.anIntArray1181;
            int[][] modern = new int[][]{{12, 0, 260}, {12, 33, 260}, {12, 66, 260}, {12, 99, 260}, {12, 132, 260}, {12, 165, 260}, {12, 198, 260},
                {12, 0, 296}, {12, 33, 296}, {12, 66, 296}, {12, 99, 296}, {12, 132, 296}, {12, 165, 296}, {12, 198, 296}};
            if (screenMode == ScreenMode.FIXED) {
                Client.spritesMap.get(5).drawSprite(getOffSetX(), getOffSetY());
            } else if (screenMode == ScreenMode.RESIZABLE_CLASSIC) {
                Client.spritesMap.get(5699).drawSprite(getOffSetX(), getOffSetY());
            } else {
                for (int i = 0; i < 14; i++) {
                    Client.spritesMap.get(6).drawSprite(getOffSetX() + 6 + modern[i][1], getOffSetY() + modern[i][2]);
                }
            }

			if (!componentHidden() && screenMode == ScreenMode.RESIZABLE_MODERN) {
				Client.spritesMap.get(18).drawTransparentSprite(getOffSetX() + 39, getOffSetY() + -9, 100);
				Client.spritesMap.get(7).drawSprite(getOffSetX() + 33, getOffSetY() - 15);

			}
            int data[][] = new int[][]{{31, 36}};
            int data2[][] = new int[][]{{26, 38}};
            int data3[][] = new int[][]{{40, -8}};
            int[][] dataset = screenMode == ScreenMode.FIXED ? data : screenMode == ScreenMode.RESIZABLE_CLASSIC ? data2 : data3;
			drawRedStones(client, screenMode);

			drawSideIcons(client, screenMode);
			if (!componentHidden() || screenMode == ScreenMode.FIXED) {
				if (client.invOverlayInterfaceID != -1) {
					client.drawInterface(0, getOffSetX() + dataset[0][0],
							RSInterface.interfaceCache[client.invOverlayInterfaceID],
							getOffSetY() + dataset[0][1]);
				} else if (Client.tabInterfaceIDs[Client.tabID] != -1) {
					RSInterface tab = RSInterface.interfaceCache[Client.tabInterfaceIDs[Client.tabID]];
					client.drawInterface(0, getOffSetX() + dataset[0][0], tab, getOffSetY() + dataset[0][1]);
				}
				if (client.menuOpen && client.menuScreenArea == 1) {
					client.drawMenu();
				}
			}
			if (screenMode == ScreenMode.FIXED) {
				if (client.tabAreaIP != null) {
					client.tabAreaIP.drawGraphics(client.canvas.getGraphics(), getxPos(), getyPos());
				}
			}
			if (client.tabAreaIP != null) {
				client.gameScreenIP.initDrawingArea();
			}
			Rasterizer3D.lineOffsets = client.anIntArray1182;
		}
	}

	private Sprite getSideIconForOldFrame(int tab) {
		int spriteIndex = tab;
		if (tab >= 3) {
			spriteIndex++;
		}
		spriteIndex += 528;
		switch (tab) {
			case 7:
				spriteIndex = 781;
				break;
			case 8:
                spriteIndex = (Client.instance.tabInterfaceIDs[RSInterface.FRIEND_TAB] == 5065 ? 536 : 537);
            break;
			case 9:
				spriteIndex = 542;
				break;
			case 10:
				spriteIndex = 541;
				break;
			case 11:
				spriteIndex = 538;
				break;
			case 12:
				spriteIndex = 539;
				break;
			case 13:
				spriteIndex = 1197;
				break;
		}
		if (tab == 2 && spriteIndex == 530) {
			if (Client.getClient().doingDungeoneering) {
				spriteIndex = 1031;
			}
		}
		return Client.spritesMap.get(spriteIndex);
	}

    private int[] getSideIconPosForOldFrame(ScreenMode screenMode, int i) {
        int[][] fixed = new int[][]{
            {16, 7}, {47, 7}, {83, 8}, {113, 5}, {146, 2}, {180, 3}, {215, 6},
            {15, 302}, {49, 305}, {82, 304}, {114, 303}, {148, 305}, {183, 303}, {217, 304}};
        int[][] classic = new int[][]{
            {11, 8}, {42, 8}, {78, 9}, {108, 6}, {141, 3}, {175, 4}, {210, 7},
            {10, 303}, {44, 306}, {77, 305}, {109, 304}, {143, 306}, {178, 304}, {212, 305}};
        int[][] modern = new int[][]{
            {12, 268}, {42, 267}, {78, 267}, {108, 265}, {141, 262}, {175, 263}, {210, 266},
            {12, 302}, {44, 303}, {77, 302}, {109, 301}, {143, 303}, {178, 301}, {211, 303}};;
        int[][] dataset = screenMode == ScreenMode.FIXED ? fixed : screenMode == ScreenMode.RESIZABLE_CLASSIC ? classic : modern;

        int x = getOffSetX() + dataset[i][0];
        int y = getOffSetY() + dataset[i][1];
        return new int[]{x, y};
    }
}
