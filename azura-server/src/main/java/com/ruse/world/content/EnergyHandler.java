package com.ruse.world.content;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Handles a player's run energy
 * 
 * @author Gabriel Hannason Thanks to Russian for formula!
 */
public class EnergyHandler {

	public static void processPlayerEnergy(Player p) {
		if(p.isRunning() && p.getMovementQueue().isMoving()) {

			int energy = p.getRunEnergy();
			if (energy > 0) {
				energy = (energy);
				p.setRunEnergy(energy);
				p.getPacketSender().sendRunEnergy(energy);
				if (!p.isInFrozenCavern) {
					p.setFrozenSteps(0);

				}
				if (!p.isInInfernalCavern) {
					p.setInfernalSteps(0);

				}
				if (!p.isInOvergrownGardens) {
					p.setEarthSteps(0);

				}

				if (p.isInFrozenCavern) {

					if (p.getInBattle() == 0) {
						p.setFrozenSteps(p.getFrozenSteps() + 1);
						//System.out.println("Frozen Steps: " + p.getFrozenSteps());
					}

					if (p.getFrozenSteps() > 50) {
						p.setFrozenSteps(0);
					}

					if (p.getInBattle() == 0) {
						int randomStep = Misc.random(5, 50);
						if (p.getFrozenSteps() == randomStep) {
							p.setFrozenSteps(0);
							p.setInBattle(1);

							//System.out.println("Spawning water monster");
							p.getPacketSender().sendFadeTransition(5, 5, 5);
							TaskManager.submit(new Task(1, p, false) {
								@Override
								protected void execute() {
									p.getPacketSender().sendFadeTransition(5, 5, 5);

									stop();
								}
							});



							List<Integer> waternpcs = Arrays.asList(6820, 6833, 6865,6809,7363);
							List<Integer> legendaries = Arrays.asList(7377, 6869, 6843,6807,6887);
							int chance1 = Misc.random(1, 100); // lets try sure

							if (chance1 == 99) {
								int randomNpcId = legendaries.get(new Random().nextInt(legendaries.size()));
								NPC npc = new NPC(randomNpcId, new Position(p.getPosition().getX(), p.getPosition().getY(), 0)).setSpawnedFor(p);
								World.register(npc);
								p.sendMessage("A Legendary Pokemon has Appeared!!");
								p.setInBattle(1);
							} else {
								int randomNpcId = waternpcs.get(new Random().nextInt(waternpcs.size()));
								NPC npc = new NPC(randomNpcId, new Position(p.getPosition().getX(), p.getPosition().getY(), 0)).setSpawnedFor(p);
								World.register(npc);
								p.sendMessage("A Pokemon has Appeared!!");
								p.setInBattle(1);
							}
						}
					}
				}

				if (p.isInInfernalCavern) {

					if (p.getInBattle() == 0){
						p.setInfernalSteps(p.getInfernalSteps() + 1);
					//System.out.println("Infernal Steps: " + p.getInfernalSteps());
				}

					if (p.getInfernalSteps() > 50) {
						p.setInfernalSteps(0);
					}

					if (p.getInBattle() == 0) {
						int randomStep = Misc.random(5, 50);
						if (p.getInfernalSteps() == randomStep) {
							p.setInfernalSteps(0);
							p.setInBattle(1);

							//System.out.println("Spawning fire monster");
							p.getPacketSender().sendFadeTransition(5, 5, 5);
							TaskManager.submit(new Task(1, p, false) {
								@Override
								protected void execute() {
									p.getPacketSender().sendFadeTransition(5, 5, 5);

									stop();
								}
							});


							List<Integer> firenpcs = Arrays.asList(8003, 9488 ,6822, 6849);
							List<Integer> legendaries = Arrays.asList(7377, 6869, 6843,6807,6887);
							int chance1 = Misc.random(1, 100); // lets try sure

							if (chance1 == 99) {
								int randomNpcId = legendaries.get(new Random().nextInt(legendaries.size()));
								NPC npc = new NPC(randomNpcId, new Position(p.getPosition().getX(), p.getPosition().getY(), 0)).setSpawnedFor(p);
								World.register(npc);
								p.sendMessage("A Legendary Pokemon has Appeared!!");
								p.setInBattle(1);
							} else {
								int randomNpcId = firenpcs.get(new Random().nextInt(firenpcs.size()));
								NPC npc = new NPC(randomNpcId, new Position(p.getPosition().getX(), p.getPosition().getY(), 0)).setSpawnedFor(p);
								World.register(npc);
								p.sendMessage("A Pokemon has Appeared!!");
								p.setInBattle(1);

							}
						}
					}
				}

				if (p.isInOvergrownGardens) {

					if (p.getInBattle() == 0) {
						p.setEarthSteps(p.getEarthSteps() + 1);
					//	System.out.println("Earth Steps: " + p.getEarthSteps());
					}

					if (p.getEarthSteps() > 50) {
						p.setEarthSteps(0);
					}
					if (p.getInBattle() == 0) {
						int randomStep = Misc.random(5, 50);
						if (p.getEarthSteps() == randomStep) {
							p.setEarthSteps(0);
							//System.out.println("Spawning earth monster");
								p.getPacketSender().sendFadeTransition(5, 5, 5);
								TaskManager.submit(new Task(1, p, false) {
									@Override
									protected void execute() {
										p.getPacketSender().sendFadeTransition(5, 5, 5);

										stop();
									}
								});


							List<Integer> earthnpcs = Arrays.asList(6992, 6818, 7337,6991,6875);
							List<Integer> legendaries = Arrays.asList(7377, 6869, 6843,6807,6887);
							int chance1 = Misc.random(1, 100); // lets try sure

							if (chance1 == 99) {
								int randomNpcId = legendaries.get(new Random().nextInt(legendaries.size()));
								NPC npc = new NPC(randomNpcId, new Position(p.getPosition().getX(), p.getPosition().getY(), 0)).setSpawnedFor(p);
								World.register(npc);
								p.sendMessage("A Legendary Pokemon has Appeared!!");
								p.setInBattle(1);
							} else {
								int randomNpcId = earthnpcs.get(new Random().nextInt(earthnpcs.size()));
								NPC npc = new NPC(randomNpcId, new Position(p.getPosition().getX(), p.getPosition().getY(), 0)).setSpawnedFor(p);
								World.register(npc);
								p.sendMessage("An Wild Pokemon has Appeared!!");
								p.setInBattle(1);
							}
						}
					}
				}

				int chance5 = Misc.getRandom(9);

		/*		if (chance5 == 1) {
					if (p.getLocation() == Locations.Location.KHAROPART1 && !p.rockarefalling) {
						int chance = Misc.getRandom(10);
						Position playerspot = new Position(p.getPosition().getX(), p.getPosition().getY(), p.getPosition().getZ());
						p.setRockarefalling(true);
						Graphic gfx = new Graphic(3);
						p.getPA().sendGraphic(new Graphic(406), playerspot);

						TaskManager.submit(new Task(2, false) {
							@Override
							public void execute() {
								p.setRockarefalling(false);
								if (chance == 1) {
									p.dealDamage(new Hit(Misc.random(5, 25), Hitmask.NEUTRAL, CombatIcon.MAGIC));
								}
								this.stop();

							}
						});
					}
				}*/



			} else {
				p.setRunning(false);
				p.getPacketSender().sendRunStatus();
				p.getPacketSender().sendRunEnergy(0);
			}
		}
		if (p.getRunEnergy() < 100) {
			if (System.currentTimeMillis() >= (restoreEnergyFormula(p) + p.getLastRunRecovery().getTime())) {
				int energy = p.getRunEnergy() + 1;
				p.setRunEnergy(energy);
				p.getPacketSender().sendRunEnergy(energy);
				p.getLastRunRecovery().reset();
			}
		}
	}

	public static void rest(Player player) {
		if (player.busy() || player.getCombatBuilder().isBeingAttacked() || player.getCombatBuilder().isAttacking()) {
			player.getPacketSender().sendMessage("You cannot do this right now.");
			return;
		}
		player.getMovementQueue().reset();
		player.setResting(true);
		player.performAnimation(new Animation(11786));
		player.getPacketSender().sendMessage("You begin resting..");
	}

	public static double restoreEnergyFormula(Player p) {
		return 2260 - (p.getSkillManager().getCurrentLevel(Skill.AGILITY) * (p.isResting() ? 13000 : 10));
	}
}
