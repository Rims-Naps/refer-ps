package com.ruse.world.content.skill.impl.mining;

import com.ruse.model.Skill;
import com.ruse.model.container.impl.Equipment;
import com.ruse.world.entity.impl.player.Player;

public class MiningData {

	public enum Pickaxe {
		BEGINNER(1433, 1, 629, 20),
		VORPAL(1424, 1, 629, 30),
		BLOOD_STAINED(1425, 35, 629, 40),
		SYMBIOTIC(1426, 75, 629, 50),
		NETHER(1427, 95, 629, 75),


		HARPOON(311, 1, 618, 2),

		SOULSTONE(8044, 1, 629, 2);
		private final int id, req, anim;
		private int extraore;
		Pickaxe(int id, int req, int anim,  int extraore) {
			this.id = id;
			this.req = req;
			this.anim = anim;
			this.extraore = extraore;

		}

		public int getId() {
			return id;
		}
		public int getReq() {
			return req;
		}
		public int getAnim() {
			return anim;
		}
		public int getExtraOre() {
			return extraore;
		}
	}

	public static Pickaxe forPick(int id) {
		for (Pickaxe p : Pickaxe.values()) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	public enum Ores {
		DROPRATE_ROCK(new int[] {7591}, 1, 25, 23119, 30, 1500),
		DAMAGE_ROCK(new int[] {38662}, 1, 25, 23121, 30, 1500),
		CRITICAL_ROCK(new int[] { 38661}, 1, 25, 23122, 30, 1500),


		GAUNTLET_FISH(new int[] {2026}, 1, 25, 373, 30, 200),




		FRACTAL_ROCK(new int[] {49806}, 1, 25, 7766, 5, 200),
		GLARITE_ROCK(new int[] {49780}, 1, 25, 7788, 5, 200);
		;
		private final int[] objid;
		private final int itemid, req, xp, ticks, respawnTimer;

		Ores(int[] objid, int req, int xp, int itemid, int ticks, int respawnTimer) {
			this.objid = objid;
			this.req = req;
			this.xp = xp;
			this.itemid = itemid;
			this.ticks = ticks;
			this.respawnTimer = respawnTimer;
		}
		public int getRespawn() {
			return respawnTimer;
		}
		public int getLevelReq() {
			return req;
		}
		public int getXpAmount() {
			return xp;
		}
		public int getItemId() {
			return itemid;
		}
		public int getTicks() {
			return ticks;
		}
	}

	public static Ores forRock(int id) {
		for (Ores ore : Ores.values()) {
			for (int obj : ore.objid) {
				if (obj == id) {
					return ore;
				}
			}
		}
		return null;
	}

	public static int getPickaxe(Player plr) {
		for (Pickaxe p : Pickaxe.values()) {
			if (plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == p.getId())
				return p.getId();
		}
		return -1;
	}

	public static boolean isHoldingPickaxe(Player player) {
		for (Pickaxe p : Pickaxe.values()) {
			if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == p.getId()) {
				return true;
			}
		}
		return false;
	}
}
