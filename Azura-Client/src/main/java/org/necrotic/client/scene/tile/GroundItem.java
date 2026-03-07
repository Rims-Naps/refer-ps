package org.necrotic.client.scene.tile;

import org.necrotic.client.media.renderable.Renderable;

public final class GroundItem {

	public int anInt45;
	public int anInt46;
	public int anInt47;
	public Renderable aClass30_Sub2_Sub4_48;
	public Renderable aClass30_Sub2_Sub4_49;
	public Renderable aClass30_Sub2_Sub4_50;
	public int uid;
	public int anInt52;
	private int newuid;

	/**
	 * Mutator method for the newuid variable
	 *
	 * @param newUIDReplacement the value assigned towards the newuid variable
	 * @return newuid new universal identification
	 */

	public int setNewUID(int newUIDReplacement) {
		return newuid = newUIDReplacement;
	}

	/**
	 * Gets the new uid
	 *
	 * @return newuid the new universal identifier
	 */
	public int getNewUID() {
		return newuid;
	}

	public GroundItem() {
	}

}
