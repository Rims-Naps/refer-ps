package com.ruse.world.content.skill.impl.woodcutting;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.GameObject;
import com.ruse.model.Locations;
import com.ruse.model.Skill;
import com.ruse.model.container.impl.Equipment;
import com.ruse.util.Misc;
import com.ruse.world.content.*;
import com.ruse.world.entity.impl.player.Player;

public class Woodcutting {

	public static void cutWood(Player player, GameObject object, boolean restarting) {
		player.getSkillManager().stopSkilling();
		player.getPacketSender().sendInterfaceRemoval();


		if (!Locations.goodDistance(player.getPosition().copy(), object.getPosition(), 1))
			return;
		if (player.busy() || player.getCombatBuilder().isBeingAttacked() || player.getCombatBuilder().isAttacking()) {
			player.getPacketSender().sendMessage("You cannot do that right now.");
			return;
		}
		if (player.getInventory().getFreeSlots() == 0) {
			player.getPacketSender().sendMessage("You don't have enough free inventory space.");
			return;
		}


		player.setInteractingObject(object);
		player.setPositionToFace(object.getPosition());

		int objId = object.getId();
		WoodcuttingData.Hatchet h = WoodcuttingData.Hatchet.forId(WoodcuttingData.getHatchet(player));
		if (h != null) {
			if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= h.getRequiredLevel()) {
				WoodcuttingData.Trees t = WoodcuttingData.Trees.forId(objId);
				if (t != null) {
					player.setEntityInteraction(object);
					if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) >= t.getReq()) {
						final int chopTimer = WoodcuttingData.getChopTimer(player, h); // Get the chop timer based on hatchet speed

						player.setCurrentTask(new Task(1, player, false) {
							int cycle = 0;
							final int treeLifeCycle = 200; // 5 minutes in ticks
							final int extraLogs = h.getExtraLogs();

							@Override
							public void execute() {
								if (player.getInteractingObject() == null
										|| player.getInteractingObject().getId() != object.getId()) {
									player.getSkillManager().stopSkilling();
									player.performAnimation(new Animation(65535));
									stop();
									return;
								}
								if (player.getInventory().getFreeSlots() == 0) {
									player.performAnimation(new Animation(65535));
									stop();
									player.getPacketSender().sendMessage("You don't have enough free inventory space.");
									return;
								}
								cycle++;
								player.performAnimation(new Animation(11970));
								if (Misc.random(2) >= 0 ) {
									if (objId == 1345){
										if (player.getNecroBoost().isActive()){
											SkillingPetBonuses.checkSkillingPet(player,100);
										}
										SkillingPetBonuses.checkSkillingPet(player,100);
									}
									player.getSkillManager().addExperience(Skill.WOODCUTTING, (int) (t.getXp()));
									player.getInventory().add(t.getReward(), extraLogs);
									player.getInventory().add(t.getReward(), Misc.random(1, 2));
									player.getPacketSender().sendMessage("You get some logs...");
								}


								if (cycle >= treeLifeCycle) { // after 5 minutes
									treeRespawn(player, object);
									player.getPacketSender().sendMessage("You have chopped the tree down.");
									player.performAnimation(new Animation(65535));
									stop();
								} else {
									player.performAnimation(new Animation(11970));
								}
							}
						});
						TaskManager.submit(player.getCurrentTask());
					} else {
						player.getPacketSender().sendMessage(
								"You need a Woodcutting level of at least " + t.getReq() + " to cut this tree.");
					}
				}
			} else {
				player.getPacketSender().sendMessage(
						"You do not have a hatchet which you have the required Woodcutting level to use.");
			}
		} else {
			player.getPacketSender().sendMessage("You do not have a hatchet that you can use.");
		}
	}

	public static void treeRespawn(Player player, GameObject oldTree) {
		if (oldTree == null || oldTree.getPickAmount() >= WoodcuttingData.Trees.forId(oldTree.getId()).getMaxLogs())
			return;
		oldTree.setPickAmount(WoodcuttingData.Trees.forId(oldTree.getId()).getMaxLogs());
		for (Player players : player.getLocalPlayers()) {
			if (players == null)
				continue;
			if (players.getInteractingObject() != null && players.getInteractingObject().getPosition()
					.equals(player.getInteractingObject().getPosition().copy())) {
				players.getSkillManager().stopSkilling();
				players.getPacketSender().sendClientRightClickRemoval();
			}
		}
		player.getPacketSender().sendClientRightClickRemoval();
		player.getSkillManager().stopSkilling();
		CustomObjects.globalObjectRespawnTask(new GameObject(1343, oldTree.getPosition().copy(), 10, 0), oldTree,
				WoodcuttingData.Trees.forId(oldTree.getId()).getMaxLogs() / 100);
	}
}
