package org.necrotic.client;

import org.necrotic.client.media.Raster;
import org.necrotic.client.util.MathUtils;

public class RGBWidgetColorPicker {
    private final int width;
    private final int height;
    private final int hueWidth;
    private final int pCircleRadius;
    private final int hCircleRadius;
    private float currentHue;
    private float currentSaturation;
    private float currentBrightness;
    private int pickedColor;
    private int lastColorX;
    private int lastColorY;
    private int lastHueY;
    private final int halfCR;
    private final int halfHR;

    public RGBWidgetColorPicker(int width, int height, int hueWidth, int pickedCircleRadius, int hueCircleRadius) {
        this.width = width;
        this.height = height;
        this.hueWidth = hueWidth;
        this.pCircleRadius = pickedCircleRadius;
        this.hCircleRadius = hueCircleRadius;
        this.pickedColor = -1;
        halfCR = pCircleRadius / 2;
        halfHR = hCircleRadius / 2;
    }

    public void render(int x, int y) {
        Raster.drawColorPicker(x, y, width, height, hueWidth, currentHue);

        handleBounds();
        Raster.drawCircle(x + lastColorX - (pCircleRadius / 2f), y + lastColorY - (pCircleRadius / 2f), pCircleRadius, pCircleRadius, 0xFFFFFF, 255, false);
        Raster.drawCircle(x + width + (hueWidth / 2f) - halfHR, y + lastHueY - (hCircleRadius / 2f), hCircleRadius, hCircleRadius, 0xFFFFFF, 255, false);
    }


    public void handlePick(int x, int y) {
        if(x <= width) {
            currentSaturation = MathUtils.map(x, 0, width, 0f, 1f);
            currentBrightness = MathUtils.map(y, 0, height, 1f, 0f);
            lastColorX = x;
            lastColorY = y;
        } else {
            currentHue = MathUtils.map(y, 0, height, 1f, 0f);
            lastHueY = y;
        }
        pickedColor = Raster.HSBtoRGB(currentHue, currentSaturation, currentBrightness);
    }

    private void handleBounds() {
        // color picker(saturation, brightness)
        if (lastColorX >= width - halfCR) {
            lastColorX = width - halfCR - 1;
        }
        if (lastColorX <= halfCR) {
            lastColorX = halfCR + 1;
        }

        if (lastColorY >= height - halfCR) {
            lastColorY = height - halfCR - 1;
        }

        if (lastColorY <= halfCR) {
            lastColorY = halfCR + 1;
        }

        // hue picker

        if (lastHueY >= height - halfHR) {
            lastHueY = height - halfHR - 1;
        }
        if (lastHueY <= halfHR) {
            lastHueY = halfHR + 1;
        }


    }

    public int getPickedColor() {
        return pickedColor;
    }

    public void setPickedColor(int pickedColor) {
        this.pickedColor = pickedColor;
    }
}
