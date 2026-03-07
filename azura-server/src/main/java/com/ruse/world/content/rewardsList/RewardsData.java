package com.ruse.world.content.rewardsList;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Adam_#6723 Enum which holds attributes for the reward interface.
 *
 */

public enum RewardsData {

	MBOX(0, new int[] { 995},
			"Mystery Box"),
	ASSASIN_BOX(1,
			new int[] { 995},
			"Drop Rate Box"),
	BaphometBox(2,
			new int[] { 995 },
			"PVM Box"),
	DonatorBox(3,
			new int[] { 995},
			"Vote Box"),
	DonkeyBox(4,
			new int[] { 995},
			"Super MBox"),
	GodzillaBOX(5,
			new int[] { 995},
			"Extreme MBox"),
	Infernal_Groudon(6,
			new int[] { 995},
			"Grand MBox"),
	MYSTERY_BOX(7,
			new int[] { 995 },
			"Raids Box"),;

	private int index;
	private String RewardName;
	private int[] item;
	private int amount;

	public int getIndex() {
		return index;
	}

	public int[] getItemID() {
		return item;
	}

	public String getText() {
		return RewardName;
	}

	public int getAmount() {
		return amount;
	}

	RewardsData(int index, int[] item, String RewardName) {
		this.index = (index);
		this.item = (item);
		this.RewardName = (RewardName);
	}

	static final Map<Integer, RewardsData> byId = new HashMap<>();

	static {
		for (RewardsData e : RewardsData.values()) {
			if (byId.put(e.getIndex(), e) != null) {
				throw new IllegalArgumentException("duplicate id: " + e.getIndex());
			}
		}
	}

	public static RewardsData getById(int id) {
		if (byId.get(id) == null) {
			return byId.get(0);
		}
		return byId.get(id);
	}

}