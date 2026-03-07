package com.ruse.world.content.skill.impl.prayer;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Skill;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.input.impl.EnterAmountOfBonesToSacrifice;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.entity.impl.player.Player;

public class BonesOnAltar {

	public static void openInterface(Player player, int itemId) {
		player.getSkillManager().stopSkilling();
		player.setSelectedSkillingItem(itemId);
		player.setInputHandling(new EnterAmountOfBonesToSacrifice());
		player.getPacketSender().sendString(2799, ItemDefinition.forId(itemId).getName())
				.sendInterfaceModel(1746, itemId, 150).sendChatboxInterface(4429);
		player.getPacketSender().sendString(2800, "How many would you like to offer?");
	}

	public static void offerBones(Player player, int amount) {
		int boneId = player.getSelectedSkillingItem();
		player.getSkillManager().stopSkilling();
		BonesData currentBone = BonesData.forId(boneId);
		if (currentBone == null)
			return;
		player.getPacketSender().sendInterfaceRemoval();
		player.setCurrentTask(new Task(2, player, true) {
			int amountSacrificed = 0;

			@Override
			public void execute() {
				if (amountSacrificed >= 500) {
					stop(); // 456 457
					return;
				}
				if (!player.getInventory().contains(boneId)) {
					player.getPacketSender()
							.sendMessage("You have run out of " + ItemDefinition.forId(boneId).getName() + ".");
					stop();
					return;
				}
				if (player.getInteractingObject() != null) {
					player.setPositionToFace(player.getInteractingObject().getPosition().copy());
					player.getInteractingObject().performGraphic(new Graphic(624));
				}
				amountSacrificed++;
				player.performAnimation(new Animation(713));
				player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
				player.getInventory().delete(boneId, 1);

				if (boneId == 7305) {
					if (player.getStarterTasks().hasCompletedAll() && !player.getMediumTasks().hasCompletedAll()) {
						MediumTasks.doProgress(player, MediumTasks.MediumTaskData.OFFER_25K_MEDIUM_BONES, 1);
					}
				}
				if (boneId == 7306) {
					if (player.getMediumTasks().hasCompletedAll() && !player.getEliteTasks().hasCompletedAll()) {
						EliteTasks.doProgress(player, EliteTasks.EliteTaskData.OFFER_25K_ELITE_BONES, amount);
					}
				}

/*
					if (player.getAmountDonated() >= ARCHON_AMOUNT && player.getAmountDonated() < ETHEREAL_AMOUNT){
						if (Misc.getRandom(250) == 1) {
							player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 4);
							player.msgFancyPurp("Your donator rank doubled your bone offering!");
							player.getInventory().delete(boneId, 2);
						} else if (Misc.getRandom(1000) == 1) {
							player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 6);
							player.msgFancyPurp("Your donator rank tripled your bone offering!");
							player.getInventory().delete(boneId, 3);
						} else {
							player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
							player.getInventory().delete(boneId, 1);
						}
					}


				if (player.getAmountDonated() >= ETHEREAL_AMOUNT && player.getAmountDonated() < MYTHIC_AMOUNT){
					if (Misc.getRandom(225) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 4);
						player.msgFancyPurp("Your donator rank doubled your bone offering!");
						player.getInventory().delete(boneId, 2);
					} else if (Misc.getRandom(800) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 6);
						player.msgFancyPurp("Your donator rank tripled your bone offering!");
						player.getInventory().delete(boneId, 3);
					} else {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
						player.getInventory().delete(boneId, 1);
					}
				}

				if (player.getAmountDonated() >= MYTHIC_AMOUNT && player.getAmountDonated() < ARCHON_AMOUNT){
					if (Misc.getRandom(200) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 4);
						player.msgFancyPurp("Your donator rank doubled your bone offering!");
						player.getInventory().delete(boneId, 2);
					} else if (Misc.getRandom(700) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 6);
						player.msgFancyPurp("Your donator rank tripled your bone offering!");
						player.getInventory().delete(boneId, 3);
					} else {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
						player.getInventory().delete(boneId, 1);
					}
				}


				if (player.getAmountDonated() >= ARCHON_AMOUNT && player.getAmountDonated() < CELESTIAL_AMOUNT){
					if (Misc.getRandom(175) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 4);
						player.msgFancyPurp("Your donator rank doubled your bone offering!");
						player.getInventory().delete(boneId, 2);
					} else if (Misc.getRandom(600) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 6);
						player.msgFancyPurp("Your donator rank tripled your bone offering!");
						player.getInventory().delete(boneId, 3);

					} else {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
						player.getInventory().delete(boneId, 1);
					}
				}


				if (player.getAmountDonated() >= CELESTIAL_AMOUNT && player.getAmountDonated() < ASCENDANT_AMOUNT){
					if (Misc.getRandom(150) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 4);
						player.msgFancyPurp("Your donator rank doubled your bone offering!");
						player.getInventory().delete(boneId, 2);
					} else if (Misc.getRandom(500) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 6);
						player.msgFancyPurp("Your donator rank tripled your bone offering!");
						player.getInventory().delete(boneId, 3);
					} else {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
						player.getInventory().delete(boneId, 1);
					}
				}


				if (player.getAmountDonated() >= ASCENDANT_AMOUNT && player.getAmountDonated() < TRANSCENDENT_AMOUNT){
					if (Misc.getRandom(100) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 4);
						player.msgFancyPurp("Your donator rank doubled your bone offering!");
						player.getInventory().delete(boneId, 2);


					} else if (Misc.getRandom(450) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 8);
						player.msgFancyPurp("Your donator rank 4x'd your bone offering!");
						player.getInventory().delete(boneId, 4);
					} else {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
						player.getInventory().delete(boneId, 1);
					}
				}


				if (player.getAmountDonated() >= TRANSCENDENT_AMOUNT) {
					if (Misc.getRandom(50) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 4);
						player.msgFancyPurp("Your donator rank doubled your bone offering!");
						player.getInventory().delete(boneId, 2);


					} else if (Misc.getRandom(350) == 1) {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 8);
						player.msgFancyPurp("Your donator rank 4x'd your bone offering!");
						player.getInventory().delete(boneId, 4);
					} else {
						player.getSkillManager().addExperience(Skill.PRAYER, currentBone.getBuryingXP() * 2);
						player.getInventory().delete(boneId, 1);
					}
				}*/
			}

			@Override
			public void stop() {
				setEventRunning(false);
				player.getPacketSender().sendMessage("You have pleased the gods of Athens with your "
						+ (amountSacrificed == 1 ? "sacrifice" : "sacrifices") + ".");
			}
		});
		TaskManager.submit(player.getCurrentTask());
	}
}
