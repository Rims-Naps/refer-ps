package com.ruse.world.content.skill.impl.fishing;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.Skill;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.dailytasks_new.DailyTask;
import com.ruse.world.entity.impl.player.Player;

public class Fishing {

	public enum Spot {
		AFKNET(2067, new int[] { 5020, 0, 0, 0, 0 , 0}, -1, -1, new int[] { 0 }, false, new int[] { 1 }, 380);


		int npcId, equipment, bait, anim;
		int[] rawFish, fishingReqs, xp;
		boolean second;

		private Spot(int npcId, int[] rawFish, int equipment, int bait, int[] fishingReqs, boolean second, int[] xp,
				int anim) {
			this.npcId = npcId;
			this.rawFish = rawFish;
			this.equipment = equipment;
			this.bait = bait;
			this.fishingReqs = fishingReqs;
			this.second = second;
			this.xp = xp;
			this.anim = anim;
		}

		public int getNPCId() {
			return npcId;
		}

		public int[] getRawFish() {
			return rawFish;
		}

		public int getEquipment() {
			return equipment;
		}

		public int getBait() {
			return bait;
		}

		public int[] getLevelReq() {
			return fishingReqs;
		}

		public boolean getSecond() {
			return second;
		}

		public int[] getXp() {
			return xp;
		}

		public int getAnim() {
			return anim;
		}
	}

	public static Spot forSpot(int npcId, boolean secondClick) {
		for (Spot s : Spot.values()) {
			if (secondClick) {
				if (s.getSecond()) {
					if (s.getNPCId() == npcId) {
						if (s != null) {
							return s;
						}
					}
				}
			} else {
				if (s.getNPCId() == npcId && !s.getSecond()) {
					if (s != null) {
						return s;
					}
				}
			}
		}
		return null;
	}

	public static void setupFishing(Player p, Spot s) {
		if (s == null)
			return;
		if (p.getInventory().getFreeSlots() <= 0) {
			p.getPacketSender().sendMessage("You do not have any free inventory space.");
			p.getSkillManager().stopSkilling();
			return;
		}
		if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= s.getLevelReq()[0]) {
			if (p.getInventory().contains(s.getEquipment()) || (p.getSkillManager().skillCape(Skill.FISHING))) {
				if (s.getBait() != -1) {
					if (p.getInventory().contains(s.getBait())) {
						startFishing(p, s);
					} else {
						String baitName = ItemDefinition.forId(s.getBait()).getName();
						if (baitName.contains("Feather") || baitName.contains("worm"))
							baitName += "s";
						p.getPacketSender().sendMessage("You need some " + baitName + " to fish here.");
						p.performAnimation(new Animation(65535));
					}
				} else {
					startFishing(p, s);
				}
			} else {
				String def = ItemDefinition.forId(s.getEquipment()).getName().toLowerCase();
				p.getPacketSender().sendMessage("You need " + Misc.anOrA(def) + " " + def + " to fish here.");
			}
		} else {
			p.getPacketSender()
					.sendMessage("You need a fishing level of at least " + s.getLevelReq()[0] + " to fish here.");
		}
	}

	public static void startFishing(Player p, Spot s) {
		p.getSkillManager().stopSkilling();
		int fishIndex = Misc.getRandom(100) >= 70 ? getMax(p, s.fishingReqs)
				: (getMax(p, s.fishingReqs) != 0 ? getMax(p, s.fishingReqs) - 1 : 0);
		if (p.getInteractingObject() != null && p.getInteractingObject().getId() != 8702)
			// p.setDirection(s == Spot.MONK_FISH ? Direction.WEST : Direction.NORTH);
			p.performAnimation(new Animation(s.getAnim()));
		p.setCurrentTask(new Task(2, p, false) {
			int cycle = 0, reqCycle = Fishing.getDelay(s.getLevelReq()[fishIndex]), animTick = 0;

			@Override
			public void execute() {
				if (p.getInventory().getFreeSlots() == 0) {
					p.getPacketSender().sendMessage("You have run out of inventory space.");
					stop();
					return;
				}
				if (!p.getInventory().contains(s.getBait())) {
					stop();
					return;
				}
				cycle++;
				if (animTick == 2) {
					p.performAnimation(new Animation(s.getAnim()));
					animTick = 0;
				}
				animTick++;
				if (cycle >= Misc.getRandom(1) + reqCycle) {
					String def = ItemDefinition.forId(s.getRawFish()[fishIndex]).getName();
					if (def.endsWith("s"))
						def = def.substring(0, def.length() - 1);
					p.getPacketSender().sendMessage(
							"You catch " + Misc.anOrA(def) + " " + def.toLowerCase().replace("_", " ") + ".");
					if (s.getBait() != -1)
						p.getInventory().delete(s.getBait(), 1);
					p.getInventory().add(s.getRawFish()[fishIndex], 1);
					if (s.getRawFish()[fishIndex] == 15270) {
					} else if (s.getRawFish()[fishIndex] == 377) {
					} else if (s.getRawFish()[fishIndex] == 383) {
					} else if (s.getRawFish()[fishIndex] == 7944 || s.getRawFish()[fishIndex] == 389) {
						DailyTask.FISH_MONKFISH.tryProgress(p);
					}
					p.getSkillManager().addExperience(Skill.FISHING, s.getXp()[fishIndex]);
					setupFishing(p, s);
					setEventRunning(false);
				}
			}

			@Override
			public void stop() {
				setEventRunning(false);
				p.performAnimation(new Animation(65535));
			}
		});

		TaskManager.submit(p.getCurrentTask());
	}

	public static int getMax(Player p, int[] reqs) {
		int tempInt = -1;
		for (int i : reqs) {
			if (p.getSkillManager().getCurrentLevel(Skill.FISHING) >= i) {
				tempInt++;
			}
		}
		return tempInt;
	}

	private static int getDelay(int req) {
		int timer = 1;
		timer += (int) req * 0.08;
		return timer;
	}

}
