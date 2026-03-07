package org.necrotic.client.scene.tile;

import org.necrotic.client.media.renderable.Renderable;

public final class DecorativeObject {

	public int anInt499;
	public int anInt500;
	public int anInt501;
	public int anInt502;
	public int anInt503;
	public Renderable aClass30_Sub2_Sub4_504;
	public int uid;
	public byte aByte506;
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

	public DecorativeObject() {
	}

}
