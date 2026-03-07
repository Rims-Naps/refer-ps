package com.ruse.world.content.AoE;

import java.util.Iterator;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.util.Misc;
import com.ruse.util.RandomUtility;
import com.ruse.world.clip.region.RegionClipping;
import com.ruse.world.content.CritUtils;
import com.ruse.world.content.combat.ElementalUtils;
import com.ruse.world.content.combat.Maxhits;
import com.ruse.world.content.combat.effect.BurnEffect;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import static com.ruse.world.content.combat.CombatFactory.npcsDeathDartDontWork;
import static com.ruse.world.content.combat.CombatType.*;

/**
 * 861 100 3000 15 Range

861 = Item ID
100 = Minimum damage
3000 = maximum damage
15 = radius
Range = Weapon combat icon that it'll show.
 * @author HP Laptop
 *
 */
public class AOEHandler {

	public static void handleAttack(Character attacker, Character victim, int minimumDamage, int maximumDamage,
			int radius, CombatIcon combatIcon) {

		Player pp = ((Player) attacker);
		boolean corrupt = pp.getEquipment().contains(2651) || pp.getEquipment().contains(2653) || pp.getEquipment().contains(2655);
		boolean spectral = pp.getEquipment().contains(2086) || pp.getEquipment().contains(2087) || pp.getEquipment().contains(2088);
		boolean bloodlust = pp.getEquipment().contains(441) || pp.getEquipment().contains(450) || pp.getEquipment().contains(451);
		boolean undertaker = pp.getEquipment().contains(2079) || pp.getEquipment().contains(2080) || pp.getEquipment().contains(2081);

		if (pp.getLocation() == Locations.Location.CORRUPT_DUNGEON) {
			if (corrupt){
				radius = 3;
			} else radius = 2 + pp.getAoeToken().getAoe_size();
		}
		if (pp.getLocation() == Locations.Location.SPECTRAL_DUNGEON || pp.getPosition().getRegionId() == 12181) {
			if (spectral){
				radius = 3;
			} else radius = 2 + pp.getAoeToken().getAoe_size();
		}

		if (pp.getLocation() == Locations.Location.CORRUPT_RAID_ROOM_1 || pp.getLocation() == Locations.Location.CORRUPT_RAID_ROOM_2 || pp.getLocation() == Locations.Location.CORRUPT_RAID_ROOM_3) {
			if (pp.getCorruptRaidDifficulty() == 3){
				if (bloodlust){
					radius = 3;
				} else radius = 2 + pp.getAoeToken().getAoe_size();
			}
		}

		if (pp.getLocation() == Locations.Location.VOID_RAID_ROOM_1 || pp.getLocation() == Locations.Location.VOID_RAID_ROOM_2 || pp.getLocation() == Locations.Location.VOID_RAID_ROOM_3) {
			if (pp.getVoidRaidDifficulty() == 3){
				if (undertaker){
					radius = 3;
				} else radius = 2 + pp.getAoeToken().getAoe_size();
			}
		}

			// if no radius, loc isn't multi, stops.
		if (radius == 0 || !Locations.Location.inMulti(victim)) {
			// System.out.println("Radius 0");
			return;
		}

		// We passed the checks, so now we do multiple target stuff.
		Iterator<? extends Character> it = null;
		if (attacker.isPlayer() && victim.isPlayer()) {
			it = ((Player) attacker).getLocalPlayers().iterator();
		} else if (attacker.isPlayer() && victim.isNpc()) {
			it = ((Player) attacker).getLocalNpcs().iterator();
			if (((Player)attacker).getForgottenRaidParty() != null)
				return;

			for (Iterator<? extends Character> $it = it; $it.hasNext();) {
				Character next = $it.next();


				if (next == null) {
					continue;
				}


				if (next.isNpc()) {
					NPC n = (NPC) next;
					if (attacker.getPosition().getRegionId() == 8788 || attacker.getPosition().getRegionId() == 9023|| attacker.getPosition().getRegionId() == 9022 || attacker.getPosition().getRegionId() == 13376 || attacker.getPosition().getRegionId() == 13120){
						continue;
					}


					//ADD HERE TYLER
					if ( n.getId() == 450 || n.getId() == 928 || n.getId() == 4001 || n.getId() == 4000 || n.getId() == 188 || n.getId() == 606 || n.getId() == 3004 || n.getId() == 3688 || n.getId() == 9838 || n.getId() == 6306 || n.getId() == 1737 || n.getId() == 202 || n.getId() == 452 || n.getId() == 1739 || n.getId() == 1726 || n.getId() == 1725 || n.getId() == 203 || n.getId() == 8010 || n.getId() == 4401|| n.getId() == 4402|| n.getId() == 4403 || n.getId() == 4404|| n.getId() == 4405|| n.getId() == 4406 || n.getId() == 4407|| n.getId() == 4408|| n.getId() == 4409
							|| n.getId() == 2120 || n.getId() == 2121 || n.getId() == 2122 || n.getId() == 2090 || n.getId() == 2124 || n.getId() == 2091 || n.getId() == 2126 || n.getId() == 2127 || n.getId() == 2128) {
						if (((Player) attacker).getSlayer().getSlayerTask().getNpcId() != n.getId()) {
							continue;
						}
					}

					if (n.getId() == 1745 || n.getId() == 5040 || n.getId() == 2019 || n.getId() == 2022|| n.getId() == 2021|| n.getId() == 2023 || n.getId() == 2024 || n.getId() == 2025 || n.getId() == 1717 || n.getId() == 1718|| n.getId() == 1719|| n.getId() == 1716){
						continue;
					}


					if (!n.getDefinition().isAttackable() || n.isSummoningNpc()) {
						continue;
					}
				} else {
					Player p = (Player) next;
					if (p.getLocation() != Locations.Location.WILDERNESS || !Locations.Location.inMulti(p)) {
						continue;
					}
				}

				if (next.getPosition().isWithinDistance(victim.getPosition(), radius) && !next.equals(attacker)
						&& !next.equals(victim) && next.getConstitution() > 0) {
					if (next.isNpc() && next.getConstitution() <= 0 && ((NPC)next).isDying()){
						continue;
					}

					if(!RegionClipping.canProjectileAttack(attacker, next)) {
						continue;
					}

					if (attacker.getLocation() == Locations.Location.CORRUPT_DUNGEON) {
						radius = 0 + pp.getAoeToken().getAoe_size();
					}
					if (attacker.getLocation() == Locations.Location.SPECTRAL_DUNGEON) {
						radius = 0 + pp.getAoeToken().getAoe_size();
					}

					//if (next.getConstitution() <= 0 && !((NPC)next).isDying()){
					//	next.setConstitution(((NPC)next).getDefinition().getHitpoints());
					//}
					int maxhit = maximumDamage;
                    Player player = (Player) attacker;


					switch (((Player) attacker).getLastCombatType()) {
						case MELEE:
                            if (player.getNodesUnlocked() != null) {
                                if (player.getSkillTree().isNodeUnlocked(Node.CHAOTIC_RADIANCE)) {
                                    maxhit = Maxhits.melee(attacker, victim) / 8;
                                } else {
                                    maxhit = Maxhits.melee(attacker, victim) / 10;
                                }
                            } else {
                                maxhit = Maxhits.melee(attacker, victim) / 10;
                            }
							break;
						case RANGED:
                            if (player.getNodesUnlocked() != null) {
                                if (player.getSkillTree().isNodeUnlocked(Node.CHAOTIC_RADIANCE)) {
                                    maxhit = Maxhits.ranged(attacker, victim) / 8;
                                } else {
                                    maxhit = Maxhits.melee(attacker, victim) / 10;
                                }
                            } else {
                                maxhit = Maxhits.ranged(attacker, victim) / 10;
                            }
							break;
						case MAGIC:
                            if (player.getNodesUnlocked() != null) {
                                if (player.getSkillTree().isNodeUnlocked(Node.CHAOTIC_RADIANCE)) {
                                    maxhit = Maxhits.magic(attacker, victim) / 8;
                                } else {
                                    maxhit = Maxhits.melee(attacker, victim) / 10;
                                }
                            } else {
                                maxhit = Maxhits.magic(attacker, victim) / 10;
                            }
							break;
					}



					/*if (player.getEquipment().contains(2653)){
						radius = radius + 2;
					}*/


					int calc = RandomUtility.inclusiveRandom(minimumDamage, maxhit * 5);
					if (player.getEquipment().contains(22006) && player.getLastCombatType() == RANGED){
						NPC npc = (NPC) victim;
						if (!npcsDeathDartDontWork(npc)) {
							calc = (int) victim.getConstitution();
						} else {
							player.sendMessage("The Death-touch dart didn't work on this.");
						}
					}
					next.dealDamage(new Hit(calc, ElementalUtils.getTypingForHit(player), combatIcon));
					next.getCombatBuilder().addDamage(attacker, calc);
					next.getCombatBuilder().attack(attacker);


					if (player.getShockwave().isActive()){
						int chance = Misc.random(0,5);
						if ( chance == 0){
							TaskManager.submit(new Task(2, false) {
								@Override
								public void execute() {
										next.performGraphic(new Graphic(2102));
										next.dealDamage(new Hit(Misc.random((int) (next.getConstitution() / 10), (int) (next.getConstitution() / 5)), Hitmask.CRITICAL, CombatIcon.MAGIC));
										this.stop();
								}
							});
						}
					}

					double meleeHitt = Maxhits.melee(player, player);
					double rangedHitt = Maxhits.ranged(player, player);
					double magicHitt = Maxhits.magic(player, player);
					double averageHitt = (meleeHitt + rangedHitt + magicHitt) / 3;

					if (player.getEquipment().contains(2651)){
						if (next.getConstitution() > meleeHitt) {
							next.dealDamage(new Hit(Misc.random((int) meleeHitt / 4, (int) meleeHitt) / 2, Hitmask.FIRE, CombatIcon.NONE));
						}
					}
					if (player.getEquipment().contains(2653)){
						if (next.getConstitution() > rangedHitt) {
							next.dealDamage(new Hit(Misc.random((int) rangedHitt / 4, (int) rangedHitt) / 2, Hitmask.FIRE, CombatIcon.NONE));
						}
					}
					if (player.getEquipment().contains(2655)){
						if (next.getConstitution() > magicHitt) {
							next.dealDamage(new Hit(Misc.random((int) magicHitt / 4, (int) magicHitt) / 2, Hitmask.FIRE, CombatIcon.NONE));
						}
					}


					//FIRE PRAYER 1
					if (CurseHandler.isActivated(player, CurseHandler.CINDERSTOUCH)) {
						if (next.isOnFire()){
							continue;
						}
						if (!CurseHandler.isActivated(player, CurseHandler.INFERNIFY)) {
							next.setBurnDamage(BurnEffect.BurnType.BASE_PRAYER.getDamage());
							TaskManager.submit(new BurnEffect(next));
							next.performGraphic(new Graphic(453));
						}
						if (CurseHandler.isActivated(player, CurseHandler.INFERNIFY)) {
							if (next.isOnFire()){
								continue;
							}
							next.setBurnDamage(40);
							TaskManager.submit(new BurnEffect(next));
						}
					}

                    if (player.getNodesUnlocked() != null) {
                        if (!player.nodeSpecialRunning && player.getSkillTree().isNodeUnlocked(Node.DRUIDIC_ENTANGLEMENT)) {
                            player.setNodeSpecialRunning(true);
                            TaskManager.submit(new Task(2, false) {
                                @Override
                                public void execute() {
                                    if (Misc.random(1, 125) == 1) {
                                        next.performGraphic(new Graphic(2051));
                                        next.dealDamage(new Hit(Misc.random((int) (next.getConstitution() / 15), (int) (next.getConstitution() / 10)), Hitmask.CRITICAL, CombatIcon.MAGIC));
                                        player.setNodeSpecialRunning(false);
                                        this.stop();
                                    }
                                }
                            });
                        }
                    }

					//FIRE PRAYER 2
					if (player.emberspecialrunning && CurseHandler.isActivated(player, CurseHandler.EMBERBLAST)){
						TaskManager.submit(new Task(2, false) {
							@Override
							public void execute() {
								if (CurseHandler.isActivated(player, CurseHandler.INFERNIFY)) {
									next.performGraphic(new Graphic(2051));
									next.dealDamage(new Hit(Misc.random((int) (next.getConstitution() / 12), (int) (next.getConstitution() / 7)), Hitmask.CRITICAL, CombatIcon.MAGIC));
									player.setEmberspecialrunning(false);
									this.stop();
								}
								if (!CurseHandler.isActivated(player, CurseHandler.INFERNIFY)) {
									next.performGraphic(new Graphic(2051));
									next.dealDamage(new Hit(Misc.random((int) (next.getConstitution() / 15), (int) (next.getConstitution() / 10)), Hitmask.CRITICAL, CombatIcon.MAGIC));
									player.setEmberspecialrunning(false);
									this.stop();
								}
							}
						});
					}




					//FIRE PRAYER 5
					if (CurseHandler.isActivated(player, CurseHandler.INFERNO)) {
						if( next.isOnFire()){
							continue;
						}
						next.setBurnDamage(50);
						TaskManager.submit(new BurnEffect(next));
						next.performGraphic(new Graphic(453));
					}

					//FIRE PRAYER 5
					if (player.emberspecialrunning && CurseHandler.isActivated(player, CurseHandler.INFERNO)){
						TaskManager.submit(new Task(2, false) {
							@Override
							public void execute() {
									next.performGraphic(new Graphic(2051));
									next.dealDamage(new Hit(Misc.random((int) (next.getConstitution() / 10), (int) (next.getConstitution() / 6)), Hitmask.CRITICAL, CombatIcon.MAGIC));
									player.setEmberspecialrunning(false);
									this.stop();
							}
						});
					}

					//ICEBORN SPECIAL PROC
					if (player.iceBornSpecialRunning && player.getIceBornTimer().isActive()){
						TaskManager.submit(new Task(2, false) {
							@Override
							public void execute() {
								next.performGraphic(new Graphic(369));
								next.dealDamage(new Hit(Misc.random((int) (next.getConstitution() / 10), (int) (next.getConstitution() / 6)), Hitmask.CRITICAL, CombatIcon.MAGIC));
								player.setIceBornSpecialRunning(false);
								this.stop();
							}
						});
					}


					//DESOLATION
					if (CurseHandler.isActivated(player, CurseHandler.DESOLATION) ) {

						if (Misc.random(0, 1000) == 0) {
							player.forceChat("FEEL MY WRATH!");
							if (player.getLastCombatType() == MELEE) {
								next.dealDamage(new Hit((Misc.random(Maxhits.melee(player, victim) * 3, Maxhits.melee(player, victim) * 4)), Hitmask.VOID, CombatIcon.MELEE));
								next.performGraphic(new Graphic(190));
							}
							if (player.getLastCombatType() == RANGED) {
								next.dealDamage(new Hit((Misc.random(Maxhits.ranged(player, victim) * 3, Maxhits.ranged(player, victim) * 4)), Hitmask.VOID, CombatIcon.RANGED));
								next.performGraphic(new Graphic(190));
							}
							if (player.getLastCombatType() == MAGIC) {
								next.dealDamage(new Hit((Misc.random(Maxhits.magic(player, victim) * 3, Maxhits.magic (player, victim) * 4)), Hitmask.VOID, CombatIcon.MAGIC));
								next.performGraphic(new Graphic(190));
							}
						} else {
							double critChance = CritUtils.criticalbonus(player);
							critChance = Math.min(critChance, 100);
							if (Misc.random(100) < critChance) {
								if (player.getLastCombatType() == MELEE) {
									next.dealDamage(new Hit((Misc.random(Maxhits.melee(player, victim), Maxhits.melee(player, victim))), Hitmask.VOID, CombatIcon.MELEE));
									next.performGraphic(new Graphic(197));
								}
								if (player.getLastCombatType() == RANGED) {
									next.dealDamage(new Hit((Misc.random(Maxhits.ranged(player, victim), Maxhits.ranged(player, victim))), Hitmask.VOID, CombatIcon.RANGED));
									next.performGraphic(new Graphic(197));
								}
								if (player.getLastCombatType() == MAGIC) {
									next.dealDamage(new Hit((Misc.random(Maxhits.magic(player, victim), Maxhits.magic(player, victim))), Hitmask.VOID, CombatIcon.MAGIC));
									next.performGraphic(new Graphic(197));
								}
							}
						}
					}

					//WATER PRAYER 2
					if (CurseHandler.isActivated(player, CurseHandler.AQUASTRIKE) || CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE )) {
						double critChance = CritUtils.criticalbonus(player);
						critChance = Math.min(critChance, 100);
						if (Misc.random(100) < critChance) {
							if (player.getLastCombatType() == MELEE) {
								next.dealDamage(new Hit((Misc.random(Maxhits.melee(player, victim) / 2 , Maxhits.melee(player, victim))), Hitmask.CRITICAL, CombatIcon.MELEE));
								next.performGraphic(new Graphic(1026));
							}
							if (player.getLastCombatType() == RANGED) {
								next.dealDamage(new Hit((Misc.random(Maxhits.ranged(player, victim) / 2 , Maxhits.ranged(player, victim))), Hitmask.CRITICAL, CombatIcon.RANGED));
								next.performGraphic(new Graphic(1026));
							}
							if (player.getLastCombatType() == MAGIC) {
								next.dealDamage(new Hit((Misc.random(Maxhits.magic(player, victim) / 2 , Maxhits.magic(player, victim))), Hitmask.CRITICAL, CombatIcon.MAGIC));
								next.performGraphic(new Graphic(1026));
							}
						}
					}



					//WATER PRAYER 1
					if (CurseHandler.isActivated(player, CurseHandler.EBBFLOW) || CurseHandler.isActivated(player, CurseHandler.SWIFTTIDE )) {
						int chance = Misc.random(1, 5);
						int recDamage = Math.round((float) (Misc.random(maxhit / 10, maxhit/ 5)));
						if (chance == 1) {
							if (recDamage < 1) {
								recDamage = 1;
							}
							if (recDamage > attacker.getConstitution())
								recDamage = (int) attacker.getConstitution();
							next.dealDamage(new Hit(recDamage, Hitmask.CRITICAL, CombatIcon.DEFLECT));
						}
					}
				}
			}
		}
	}
}
