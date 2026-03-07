package org.necrotic.client.scene.tile;

import org.necrotic.client.media.renderable.Renderable;

public final class WallObject {

	public int anInt273;
	public int anInt274;
	public int anInt275;
	public int orientation;
	public int orientation1;
	public Renderable aClass30_Sub2_Sub4_278;
	public Renderable aClass30_Sub2_Sub4_279;
	public int uid;
	public byte aByte281;
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

	public WallObject() {
	}

}
