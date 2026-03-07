package com.ruse.world.content.skill.impl.woodcutting;

import java.util.HashMap;
import java.util.Map;

import com.ruse.model.Skill;
import com.ruse.model.container.impl.Equipment;
import com.ruse.world.entity.impl.player.Player;

public class WoodcuttingData {

	public static enum Hatchet {


		//SOULBANE
		SOULSTONE(8046, 1, 867, 3, 2),

		AFK1(2805, 1, 867, 1, 0),
		AFK2(2807, 30, 867, 2, 1),
		AFK3(2809, 50, 867, 3, 2);



		private int id, req, anim;
		private double speed;
		private int extraLogs;

		private Hatchet(int id, int level, int animation, double speed, int extraLogs) {
			this.id = id;
			this.req = level;
			this.anim = animation;
			this.speed = speed;
			this.extraLogs = extraLogs;
		}

		public static Map<Integer, Hatchet> hatchets = new HashMap<>();

		public static Hatchet forId(int id) {
			return hatchets.get(id);
		}

		static {
			for (Hatchet hatchet : Hatchet.values()) {
				hatchets.put(hatchet.getId(), hatchet);
			}
		}

		public int getId() {
			return id;
		}

		public int getRequiredLevel() {
			return req;
		}

		public int getAnim() {
			return anim;
		}

		public double getSpeed() {
			return speed;
		}

		public int getExtraLogs() {
			return extraLogs;
		}
	}

	public static enum Trees {

		UNDEAD(1, 25, 7747, new int[] { 1345 }, 1500, true),

		PLACEHOLDER(1, 2, 2833, new int[] { 709 }, 1500, true);



		private int[] objects;
		private int req, xp, log, maxLogs;
		private boolean multi;

		private Trees(int req, int xp, int log, int[] obj, int maxLogs, boolean multi) {
			this.req = req;
			this.xp = xp;
			this.log = log;
			this.objects = obj;
			this.maxLogs = maxLogs;
			this.multi = multi;
		}

		public boolean isMulti() {
			return multi;
		}

		public int getMaxLogs() {
			return maxLogs;
		}

		public int getReward() {
			return log;
		}

		public int getXp() {
			return xp;
		}

		public int getReq() {
			return req;
		}

		private static final Map<Integer, Trees> tree = new HashMap<>();

		public static Trees forId(int id) {
			return tree.get(id);
		}

		static {
			for (Trees t : Trees.values()) {
				for (int obj : t.objects) {
					tree.put(obj, t);
				}
			}
		}
	}

	public static int getHatchet(Player p) {
		for (Hatchet h : Hatchet.values()) {
			if (p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == h.getId()) {
				return h.getId();
			} else if (p.getInventory().contains(h.getId())) {
				return h.getId();
			}
		}
		return -1;
	}

	public static int getChopTimer(Player player, Hatchet h) {
		int skillReducement = (int) (player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) * 0.05);
		int axeReducement = (int) h.getSpeed();
		return skillReducement + axeReducement;
	}

	public static int getExtraLogs(Player player, Hatchet h) {
		return h.getExtraLogs();
	}
}
