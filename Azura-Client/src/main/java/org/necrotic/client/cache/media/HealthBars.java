package org.necrotic.client.cache.media;

import org.necrotic.client.Client;
import org.necrotic.client.gameframe.GameFrame;

public class HealthBars {

    enum HealthBarData {
        RIFT(0x403548, 0x734A88, 6326,2745,8009,9006,1807, 1022,1084, 555, 556, 557,559,601,4444, 2341,2342,1061,8000,
                8002, 8004, 3308, 3307, 3000,2019,2022,2021,2034,
                4412,4413,4414,4415);
        int[] npcId;
        int colorBackground;
        int colorForeground;

        HealthBarData(int bg, int fg, int... npcId) {
            this.colorBackground = bg;
            this.colorForeground = fg;
            this.npcId = npcId;
        }
    }

    public static boolean isAdavancedNPC(int npcId) {
        for (HealthBarData hbd : HealthBarData.values()) {
            for (Integer i : hbd.npcId) {
                if (i == npcId) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int getForegroundColor(int npcId) {
        for (HealthBarData hbd : HealthBarData.values()) {
            for (Integer i : hbd.npcId) {
                if (i == npcId) {
                    return hbd.colorForeground;
                }
            }
        }
        return 0;
    }

    private static int getBackgroundColor(int npcId) {
        for (HealthBarData hbd : HealthBarData.values()) {
            for (Integer i : hbd.npcId) {
                if (i == npcId) {
                    return hbd.colorBackground;
                }
            }
        }
        return 0;
    }

    public static void drawAdvancedProgressBar(int currentHp, int maxHp, int npcId, String name) {
        double percentOfHpLeft = (((double) currentHp / (double) maxHp) * 374);
        double percentOfHpLost = 374 - percentOfHpLeft;
        double percent = (((double) currentHp / (double) maxHp) * 100);
        int xPos = 70;
        int yPos = 4;
        int width = 375;
        int height = 19;
        int hpBarXPps = xPos + 1;
        int hpBarYPos = yPos + 12;
        if (percentOfHpLeft >= width) {
            percentOfHpLeft = width - 25;
        }
        if (GameFrame.getScreenMode() != GameFrame.ScreenMode.FIXED) {
            xPos += (Client.instance.getScreenWidth() / 2) - width;
            hpBarXPps += (Client.instance.getScreenWidth()  / 2) - width;

            if (Client.instance.getScreenWidth() >= 1000){
                yPos += 10;
            }

        }
        Client.instance.newFancyFont.drawCenteredString(name, xPos + (width / 2), yPos + 13, 16777215, 65);
        TextDrawingArea.fillPixels(xPos, width, height, 0, yPos + 15); //Outline
        TextDrawingArea.drawAlphaFilledPixels(hpBarXPps + (int) percentOfHpLeft, hpBarYPos + yPos, (int) percentOfHpLost, height - 2, getBackgroundColor(npcId), 200);
        TextDrawingArea.drawAlphaFilledPixels(hpBarXPps, hpBarYPos + yPos, (int) percentOfHpLeft, height - 2 , getForegroundColor(npcId), 200);
        Client.instance.newRegularFont.drawCenteredString((int) percent + "%", xPos + (width / 2), hpBarYPos + 13 + yPos, 16777215, 0);
    }
}
