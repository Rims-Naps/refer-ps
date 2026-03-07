package com.ruse.world.content.skill.impl.prayer;

public enum BonesData {
	EASY_BONES(534, 500), MEDIUM_BONES(7305, 1000), ELITE_BONES(7306, 1500), MASTER_BONES(7307, 2500) , CORRUPT_BONES(7308, 10000) , SPECTRAL_BONES(7309, 15000);

	BonesData(int boneId, int buryXP) {
		this.boneId = boneId;
		this.buryXP = buryXP;
	}

	private int boneId;
	private int buryXP;

	public int getBoneID() {
		return this.boneId;
	}

	public int getBuryingXP() {
		return this.buryXP;
	}

	public static BonesData forId(int bone) {
		for (BonesData prayerData : BonesData.values()) {
			if (prayerData.getBoneID() == bone) {
				return prayerData;
			}
		}
		return null;
	}

}
