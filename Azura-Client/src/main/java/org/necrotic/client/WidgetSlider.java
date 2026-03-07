package org.necrotic.client;

import org.necrotic.client.cache.media.Sprite;
import org.necrotic.client.util.MathUtils;

public class WidgetSlider {

    private final float minValue;
    private final float maxValue;
    private float value;
    private final int sliderWidth;
    private final Sprite background;
    private final Sprite handle;
    private boolean moving = false;


    public WidgetSlider(int minValue, int maxValue, int defaultValue, Sprite background, Sprite handle) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.value = defaultValue;
        this.background = background;
        this.handle = handle;
        this.sliderWidth = background.myWidth;
    }

    public void render(int x, int y) {
        background.drawSprite(x, y);
        int handleX = (int) MathUtils.map(value, minValue, maxValue, 0, sliderWidth - (handle.myWidth / 2f));
        handle.drawSprite(x + handleX, y - 1);
    }

    public void handleSlider(int x) {
        setMoving(true);
        value = MathUtils.map(x, 0, sliderWidth - 1, minValue, maxValue); // mapping to sliderWidth - 1 because x goes from 0 to width - 1
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
