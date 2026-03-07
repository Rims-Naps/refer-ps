package org.necrotic.client.jfx.npcspawneditor.model;

/**
 * Represents a single movement direction.
 *
 * @author Graham
 */
public enum Direction {

    /**
     * North movement.
     */
    NORTH(1),

    /**
     * North east movement.
     */
    NORTH_EAST(2),

    /**
     * East movement.
     */
    EAST(4),

    /**
     * South east movement.
     */
    SOUTH_EAST(7),

    /**
     * South movement.
     */
    SOUTH(6),

    /**
     * South west movement.
     */
    SOUTH_WEST(5),

    /**
     * West movement.
     */
    WEST(3),

    /**
     * North west movement.
     */
    NORTH_WEST(0),

    /**
     * No movement.
     */
    NONE(-1);

    /**
     * The direction as an integer.
     */
    private final int intValue;

    /**
     * Creates the direction.
     *
     * @param intValue The direction as an integer.
     */
    private Direction(int intValue) {
        this.intValue = intValue;
    }

    /**
     * Gets the direction as an integer which the client can understand.
     *
     * @return The movement as an integer.
     */
    public int toInteger() {
        return intValue;
    }

}