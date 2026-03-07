package com.ruse;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PlayerSetting {
    NEW_FUNCTION_KEYS(0, 0), NEW_HEALTH_BARS(0, 1),
    NEW_HIT_MARKS(1, 2), NEW_CURSORS(1, 3),
    USERNAMES_ABOVE_HEAD(1, 4), TOGGLE_PARTICLES(1, 5),
    GROUND_ITEM_NAMES(1, 6), USERNAME_HIGHLIGHTING(1, 7),
    LEVEL_UP_MESSAGES(1, 8), PLACEHOLDER(1, 9),
    VIEW_DISTANCE(25, 10), TEXTURE_ANIMATION_SPEED(3, 11),
    FOG_START_VALUE(2500, 12), FOG_COLOR(0, 13);

    private final int defaultValue;
    private final int index;

    public static final PlayerSetting[] VALUES = values();

    public static PlayerSetting getSetting(int index) {
        return Arrays.stream(VALUES).filter(s -> s.getIndex() == index).findFirst().orElse(null);
    }

    public static final int TOGGLEABLES = 10;
}
