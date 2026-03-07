package org.necrotic.client.cache.media.rsinterface.movement;

import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.gameframe.GameFrame;

import java.util.HashMap;
import java.util.Map;

public class InterfaceMovementManager {

    private static Map<Integer, ModifiedInterfacePosition> modifiedPositions = new HashMap<>();

    public static Map<Integer, ModifiedInterfacePosition> getModifiedPositions() {
        return modifiedPositions;
    }

    public static void moveInterface(int frame, int x, int y) {
        if (modifiedPositions.get(frame) == null) {
            modifiedPositions.put(frame, new ModifiedInterfacePosition());
            return;
        }
        ScreenOffset offset = modifiedPositions.get(frame).getOffsetsForScreenMode(GameFrame.getScreenMode());
        offset.x += x;
        offset.y += y;

        checkInterfacePosition(frame);

    }

    private static void checkInterfacePosition(int frame) {

        RSInterface interf = RSInterface.interfaceCache[frame];
        if (interf == null)
            return;

        Sprite mainSprite = RSInterface.interfaceCache[interf.children[0]].enabledSprite;

        boolean fixed = GameFrame.getScreenMode() == GameFrame.ScreenMode.FIXED;

        int width = fixed ? 512 : Client.instance.getScreenWidth();
        int height = fixed ? 334 : Client.instance.getScreenHeight();

        int xMinus = fixed ? 0 : ((width - 512) / 2);
        int yMinus = fixed ? 0 : ((height - 334) / 2);

        ScreenOffset offset = modifiedPositions.get(frame).getOffsetsForScreenMode(GameFrame.getScreenMode());



        if (offset.x < 0 - xMinus - interf.childX[0]) {
            offset.x = 0 - xMinus - interf.childX[0];
        }
        if (offset.y < 0 - yMinus - interf.childY[0]) {
            offset.y = 0 - yMinus - interf.childY[0];
        }

        if (getInterfaceWidth(frame) != -1 || getInterfaceHeight(frame) != -1) {

            if (offset.x > (width - xMinus - getInterfaceWidth(frame) - interf.childX[0])) {
                offset.x = (width - xMinus - getInterfaceWidth(frame) - interf.childX[0]);
            }
            if (offset.y > (height - yMinus - getInterfaceHeight(frame) - interf.childY[0])) {
                offset.y = (height - yMinus - getInterfaceHeight(frame) - interf.childY[0]);
            }

        } else
        if (mainSprite != null) {
            if (offset.x > (width - xMinus - mainSprite.myWidth - interf.childX[0])) {
                offset.x = (width - xMinus - mainSprite.myWidth - interf.childX[0]);
            }
            if (offset.y > (height - yMinus - mainSprite.myHeight - interf.childY[0])) {
                offset.y = (height - yMinus - mainSprite.myHeight - interf.childY[0]);
            }
        }
    }

    public static void checkAllInterfacePositions() {
        for (Map.Entry<Integer, ModifiedInterfacePosition> e : modifiedPositions.entrySet()) {
            checkInterfacePosition(e.getKey());
        }
    }

    public static void resetModifications() {
        modifiedPositions.clear();
    }

    public static class ModifiedInterfacePosition {

        public ModifiedInterfacePosition() {

        }

        private ScreenOffset fixedOffsets = new ScreenOffset(0, 0);

        private ScreenOffset resizableOffsets = new ScreenOffset(0, 0);

        private ScreenOffset fullScreenOffsets = new ScreenOffset(0, 0);

        public ScreenOffset getOffsetsForScreenMode(GameFrame.ScreenMode screenMode) {

            switch (screenMode) {
                case FIXED:
                    return fixedOffsets;
                case RESIZABLE_CLASSIC:
                    return resizableOffsets;
                case RESIZABLE_MODERN:
                    return fullScreenOffsets;
            }

            return null;
        }

        public int getXOffset() {
            return getOffsetsForScreenMode(GameFrame.getScreenMode()).getXOffset();
        }

        public int getYOffset() {
            return getOffsetsForScreenMode(GameFrame.getScreenMode()).getYOffset();
        }

    }

    public static class ScreenOffset {

        public ScreenOffset(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private int x;

        public int getXOffset() {
            return x;
        }

        private int y;

        public int getYOffset() {
            return y;
        }

    }


    private static int getInterfaceWidth(int rsi) {
        switch (rsi) {
            case 3824:
            case 7424:
            case 3323:
                return 488;
        }
        return -1;
    }

    private static int getInterfaceHeight(int rsi) {
        switch (rsi) {
            case 3824:
            case 3323:
                return 300;
            case 7424:
                return 305;
        }
        return -1;
    }
}
