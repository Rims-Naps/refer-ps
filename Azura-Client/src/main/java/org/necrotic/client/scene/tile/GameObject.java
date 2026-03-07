package org.necrotic.client.scene.tile;

import org.necrotic.client.media.renderable.Renderable;

public final class GameObject {

	public int anInt517;
	public int anInt518;
	public int anInt519;
	public int anInt520;
	public Renderable aClass30_Sub2_Sub4_521;
	public int anInt522;
	public int anInt523;
	public int anInt524;
	public int anInt525;
	public int anInt526;
	public int anInt527;
	public int anInt528;
	public int uid;
	public byte aByte530;
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

	public GameObject() {
	}

}
