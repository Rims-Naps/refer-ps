package org.necrotic.client;

import org.necrotic.client.cache.media.RSInterface;

public class UpgradeInterfaceSpinner {
    private final int STARTING_POINT = 33425;
    private boolean shifting = false;
    private int shifted = 0;
    private int speed = 8;

    private int target;

    public void init(int winningIndex) {
        shifted = 0;
        shifting = true;
        speed = 8;
        target = winningIndex * 45;
        RSInterface rsInterface = RSInterface.interfaceCache[STARTING_POINT + 25];
        rsInterface.childX[51] = 7;
        int x = 0;
        for (int i = 1; i < 51; i++) {
            rsInterface.childX[i] = x;
            x += 2880;
        }
    }

    public void shift() {
        if (!shifting) {
            return;
        }
        if (shifted >= target) {
            onFinish();
        }
        RSInterface rsInterface = RSInterface.interfaceCache[STARTING_POINT + 25];
        for (int i = 1; i < 52; i++) {
            rsInterface.childX[i] -= speed;
        }
        shifted += speed;
        determineSpeed();
        //System.out.println("shifted = " + shifted);
    }

    private void determineSpeed() {
        int percentage = getPercentage(shifted, target);
        if (percentage > 98) {
            speed = 1;
        } else if (percentage > 94) {
            speed = 2;
        } else if (percentage > 85) {
            speed = 4;
        } else if (percentage > 75) {
            speed = 5;
        } else if (percentage > 60) {
            speed = 7;
        } else if (percentage > 0) {
            speed = 13;
        }
    }

    private int getPercentage(int n, int total) {
        float proportion = ((float) n) / ((float) total);
        return (int) (proportion * 100f);
    }

    private void onFinish() {
        shifting = false;
        Client.getOut().putOpcode(242);
        Client.getOut().putShort(target / 45);
    }
}
