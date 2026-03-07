package com.ruse.world.content.combat;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

import com.ruse.model.*;
import com.ruse.model.container.impl.Equipment;
import com.ruse.util.Misc;
import com.ruse.world.content.AoE.AOEHandler;
import com.ruse.world.content.CritUtils;
import com.ruse.world.content.PlayerAmmoHandler;
import com.ruse.world.content.forgottenRaids.boss.ForgottenRaidBoss;
import com.ruse.world.content.forgottenRaids.boss.impl.*;
import com.ruse.world.content.skill.impl.summoning.Familiar;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;

import com.ruse.world.entity.impl.player.Player;

/**
 * A container that holds all of the data needed for a single combat hook.
 * 
 * @author lare96
 */
public class CombatContainer {

	/** The attacker in this combat hook. */
	private Character attacker;

	/** The victim in this combat hook. */
	private Character victim;

	/** The hits that will be dealt during this combat hook. */
	private ContainerHit[] hits;

	/** The skills that will be given experience. */
	private int[] experience;

	/** The combat type that is being used during this combat hook. */
	private CombatType combatType;

	/** If accuracy should be taken into account. */
	private boolean checkAccuracy;

	/** If at least one hit in this container is accurate. */
	private boolean accurate;

	/** The modified damage, used for bolt effects etc **/
	private int modifiedDamage;

	/** The delay before the hit is executed **/
	private int hitDelay;

	/**
	 * Create a new {@link CombatContainer}.
	 * 
	 * @param attacker      the attacker in this combat hook.
	 * @param victim        the victim in this combat hook.
	 * @param hitAmount     the amount of hits to deal this combat hook.
	 * @param hitType       the combat type that is being used during this combat
	 *                      hook
	 * @param checkAccuracy if accuracy should be taken into account.
	 */
	public CombatContainer(Character attacker, Character victim, int hitAmount, CombatType hitType,
			boolean checkAccuracy) {
		this.attacker = attacker;
		this.victim = victim;
		this.combatType = hitType;
		this.checkAccuracy = checkAccuracy;
		this.hits = prepareHits(hitAmount);
		this.experience = getSkills(hitType);
		this.hitDelay = hitType == CombatType.MELEE ? 0
				: hitType == CombatType.RANGED ? 1
						: hitType == CombatType.MAGIC || hitType == CombatType.DRAGON_FIRE ? 2 : 1;
	}

	public CombatContainer(Character attacker, Character victim, int hitAmount, int hitDelay, CombatType hitType,
			boolean checkAccuracy) {
		this.attacker = attacker;
		this.victim = victim;
		this.combatType = hitType;
		this.checkAccuracy = checkAccuracy;
		this.hits = prepareHits(hitAmount);
		this.experience = getSkills(hitType);
		this.hitDelay = hitDelay;
	}

	/**
	 * Create a new {@link CombatContainer} that will deal no hit this turn. Used
	 * for things like spells that have special effects but don't deal damage.
	 * 
	 * @param checkAccuracy if accuracy should be taken into account.
	 */
	public CombatContainer(Character attacker, Character victim, CombatType hitType, boolean checkAccuracy) {
		this(attacker, victim, 0, hitType, checkAccuracy);
	}

	/**
	 * Prepares the hits that will be dealt this combat hook.
	 * 
	 * @param hitAmount the amount of hits to deal, maximum 4 and minimum 0.
	 * @return the hits that will be dealt this combat hook.
	 */
	private ContainerHit[] prepareHits(int hitAmount) {

		// Check the hit amounts.
		if (hitAmount > 4) {
			throw new IllegalArgumentException("Illegal number of hits! The maximum number of hits per turn is 4.");
		} else if (hitAmount < 0) {
			throw new IllegalArgumentException("Illegal number of hits! The minimum number of hits per turn is 0.");
		}

		// No hit for this turn, but we still need to calculate accuracy.
		if (hitAmount == 0) {
			accurate = !checkAccuracy || CombatFactory.rollAccuracy(attacker, victim, combatType);
			return new ContainerHit[] {};
		}

		// Create the new array of hits, and populate it. Here we do the maximum
		// hit and accuracy calculations.
		ContainerHit[] array = new ContainerHit[hitAmount];
		for (int i = 0; i < array.length; i++) {
			boolean accuracy = !checkAccuracy || CombatFactory.rollAccuracy(attacker, victim, combatType);
			array[i] = new ContainerHit(CombatFactory.getHit(attacker, victim, combatType), accuracy);
			if (array[i].isAccurate()) {
				accurate = true;
			}
		}


		if(attacker instanceof Player && victim.isNpc()) {
			Player k = (Player) attacker;
			double meleeHitt = Maxhits.melee(k, k);
			double rangedHitt = Maxhits.ranged(k, k);
			double magicHitt = Maxhits.magic(k, k);
			double averageHitt = (meleeHitt + rangedHitt + magicHitt) / 3;

			if (k != null && victim != null) {
				if (k.getLocation() == Locations.Location.CORRUPT_DUNGEON || k.getLocation() == Locations.Location.BASILISK || k.getLocation() == Locations.Location.BRIMSTONE
					|| k.getPosition().getRegionId() == 11588 || k.getLocation() == Locations.Location.ORACLE || k.getLocation() == Locations.Location.ROCKMAW ||  k.getLocation() == Locations.Location.GRIMLASH) {
					if (k.getEquipment().contains(2651)) {
						if (victim.getConstitution() > meleeHitt) {
							victim.dealDamage(new Hit(Misc.random((int) meleeHitt / 2, (int) meleeHitt) / 1, Hitmask.FIRE, CombatIcon.MELEE));
						}
					}
					if (k.getEquipment().contains(2653)) {
						if (victim.getConstitution() > rangedHitt) {
							victim.dealDamage(new Hit(Misc.random((int) rangedHitt / 2, (int) rangedHitt) / 1, Hitmask.FIRE, CombatIcon.RANGED));
						}
					}
					if (k.getEquipment().contains(2655)) {
						if (victim.getConstitution() > magicHitt) {
							victim.dealDamage(new Hit(Misc.random((int) magicHitt / 2, (int) magicHitt) / 1, Hitmask.FIRE, CombatIcon.MAGIC));
						}
					}
				}
			}
		}

		if(attacker instanceof Player && victim.isNpc()) {
			Player k = (Player) attacker;
			double meleeHitt = Maxhits.melee(k, k);
			double rangedHitt = Maxhits.ranged(k, k);
			double magicHitt = Maxhits.magic(k, k);
			double averageHitt = (meleeHitt + rangedHitt + magicHitt) / 3;

			if (k != null && victim != null) {
				if (k.getLocation() == Locations.Location.SPECTRAL_DUNGEON) {
					if (k.getEquipment().contains(2086)) {
						if (victim.getConstitution() > meleeHitt) {
							victim.dealDamage(new Hit(Misc.random((int) meleeHitt / 2, (int) meleeHitt) / 1, Hitmask.FIRE, CombatIcon.MELEE));
						}
					}
					if (k.getEquipment().contains(2087)) {
						if (victim.getConstitution() > rangedHitt) {
							victim.dealDamage(new Hit(Misc.random((int) rangedHitt / 2, (int) rangedHitt) / 1, Hitmask.FIRE, CombatIcon.RANGED));
						}
					}
					if (k.getEquipment().contains(2088) || k.getEquipment().contains(23188)) {
						if (victim.getConstitution() > magicHitt) {
							victim.dealDamage(new Hit(Misc.random((int) magicHitt / 2, (int) magicHitt) / 1, Hitmask.FIRE, CombatIcon.MAGIC));
						}
					}
				}
			}
		}



		if(attacker instanceof Player && victim.isNpc()) {
			Player k = (Player) attacker;
			double meleeHit = Maxhits.melee(k, k);
			double rangedHit = Maxhits.ranged(k, k);
			double magicHit = Maxhits.magic(k, k);
			double averageHit = (meleeHit + rangedHit + magicHit) / 3;
			int chance_to_doubleHit = Misc.random(0,32);
			boolean blitzEnergyEquipped = k.getEquipment().contains(17019);

			if (k != null && victim != null && chance_to_doubleHit == 0 && blitzEnergyEquipped) {
				if (!k.getEquipment().contains(2611)) {
					victim.dealDamage(new Hit(Misc.random((int) averageHit / 8, (int) averageHit) / 4, Hitmask.DARK_PURPLE, CombatIcon.NONE));
				} else {
					victim.dealDamage(new Hit(Misc.random((int) averageHit / 6, (int) averageHit) / 4, Hitmask.DARK_PURPLE, CombatIcon.NONE));
				}
			}
		}

		if(attacker instanceof Player) {

				Familiar pet = ((Player) attacker).getSummoning().getFamiliar();
				int base_attack_speed = Misc.random(0,100);

				if (pet != null && ((Player) attacker).getSummon() != null && ((Player) attacker).getSummon().isCanAttack()) {
					if (victim != null && ((Player) attacker).getSummon().getHit() != null) {
						int speed = ((Player) attacker).getSummon().getAttackSpeed();
						int damage = (int) ((Player) attacker).getSummon().getHit().getDamage();
						if (speed >= base_attack_speed) {
							if (victim.getConstitution() > 0 && victim.getConstitution() > ((Player) attacker).getSummon().getHit().getDamage()) {
								pet.getSummonNpc().setPositionToFace(victim.getPosition());
                                pet.getSummonNpc().setEntityInteraction(victim);
                                pet.getSummonNpc().getMovementQueue().setFollowCharacter(victim);
								pet.getSummonNpc().performForcedAnimation(((Player) attacker).getSummon().getAttackAnimation());
								((Player) attacker).getSummon().projectile(pet.getSummonNpc(), victim).sendProjectile();
								boolean undead_energy = ((Player) attacker).getEquipment().contains(2614);
								if (!undead_energy) {
									victim.dealDamage(new Hit(Misc.random(damage / 2, damage * 2)));
								} else {
									victim.dealDamage(new Hit(Misc.random(damage / 1, damage * 3)));
								}
							}
						}
					}
				}
			}



		if (attacker.isPlayer()) {
			Player p = (Player) attacker;
			if (p != null) {
				PlayerAmmoHandler ammoHandler = new PlayerAmmoHandler(p);
				ammoHandler.handleAmmo();
			}
		}


		//VORPAL ARROWS EFFECT
		if (attacker.isPlayer()) {
			Player p = (Player) attacker;
			boolean vorpal_ammo_equipped = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1428;
			boolean vorpal_quiver = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && p.getVorpalAmmo() > 1 && p.getQuiverMode() == 1;

			if (vorpal_ammo_equipped || vorpal_quiver) {
				if (p != null){
					new Projectile(p, victim, 553 , 40, 3, 10, 0, 0).sendProjectile();
				}
			}
		}

		//BLOODSTAINED ARROWS EFFECT
		if (attacker.isPlayer()) {
			Player p = (Player) attacker;
			int blood_damage = Misc.random(1,5);
			boolean bloodstained_ammo_equipped = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1430;
			boolean bloodstaind_quiver = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && p.getBloodstainedAmmo() > 1 && p.getQuiverMode() == 2;

			if (bloodstained_ammo_equipped || bloodstaind_quiver ) {
				if (p != null) {
					new Projectile(p, victim, 373, 40, 3, 10, 0, 0).sendProjectile();
					//SOUL ENERGY(2) HANDLE
					if (p.getEquipment().contains(2613)) {
						if (p.getConstitution() >= 25) {
							p.dealDamage(new Hit(blood_damage, Hitmask.FIRE, CombatIcon.MELEE));
						}
					} else {
                        p.dealDamage(new Hit(blood_damage, Hitmask.FIRE, CombatIcon.MELEE));
                    }
				}
			}
		}



		//SYMBIOTE ARROWS EFFECT
		if (attacker.isPlayer()) {
			Player p = (Player) attacker;
			boolean symbiote_ammo_equipped = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1429;
			boolean sym_quiver = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && p.getSymbioteAmmo() > 1 && p.getQuiverMode() == 3;

			if (symbiote_ammo_equipped || sym_quiver) {
				if (p != null) {
					new Projectile(p, victim, 334 , 40, 3, 35, 31, 0).sendProjectile();
					if (Misc.getRandom(25) == 1) {
						p.symbioticCharges++;
						p.msgGreen("You feel the energy building...");
						if (p.symbioticCharges == 5) {
							p.performGraphic(new Graphic(524));
							AOEHandler.handleAttack(p, victim, Maxhits.ranged(p, victim) / 20, Maxhits.ranged(p, victim) / 10, 2, CombatIcon.RANGED);
							p.symbioticCharges = 0;
							p.msgGreen("Symbiotic Energy surges through your weapon!");
						}
					}
				}
			}
		}


		//NETHER ARROWS EFFECT
		if (attacker.isPlayer()) {
			Player p = (Player) attacker;
			boolean nether_ammo_equipped = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1431;
			boolean nether_ammo = p.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056 && p.getNetherAmmo() > 1 && p.getQuiverMode() == 4;

			if (nether_ammo_equipped || nether_ammo) {
				if (p != null) {
					new Projectile(p, victim, 2188 , 40, 3, 35, 31, 0).sendProjectile();
					if (Misc.getRandom(50) == 1) {
						AOEHandler.handleAttack(p, victim, Maxhits.ranged(p, victim) / 10, Maxhits.ranged(p, victim) / 5, 2, CombatIcon.MAGIC);
					}
				}
			}
		}

        if (attacker.isPlayer() && victim.isNpc()) {
            Player p = (Player) attacker;
            NPC npc = (NPC) victim;
            if (p != null && npc != null) {
                if (p.getNodesUnlocked() != null) {
                    if (p.getSkillTree().isNodeUnlocked(Node.BEAST_CRUSHER)) {
                        if (npc.getId() == 2017 || npc.getId() == 2018 || npc.getId() == 6323) {
                            if (Misc.random(1, 500) == 1) {
                                if (victim.getConstitution() > victim.getConstitution() / 2) {
                                    victim.dealDamage(new Hit((int) (victim.getConstitution() - (victim.getConstitution() * 0.15)), Hitmask.WATER, CombatIcon.MAGIC));

                                }
                            }
                        }
                    }
                }
            }
        }




		if (attacker.isPlayer() && ((Player) attacker).getCritchance() > 0) {
			Player p = (Player) attacker;
			for (ContainerHit containerHit : array) {
				double critChance = CritUtils.criticalbonus(p);
				critChance = Math.min(critChance, 200);
				if (Misc.random(200) < critChance) {
					if (combatType == CombatType.MELEE) {
                        if (p.getNodesUnlocked() != null) {
                            if (p.getSkillTree().isNodeUnlocked(Node.BLADE_DANCER)) {
                                containerHit.setHit(new Hit((int) ((containerHit.getHit().getDamage()  + Misc.random(Maxhits.melee(p, victim) / 2, Maxhits.melee(p, victim))) * 1.05), Hitmask.CRITICAL, CombatIcon.MELEE));
                            } else {
                                containerHit.setHit(new Hit((int) (containerHit.getHit().getDamage()  + Misc.random(Maxhits.melee(p, victim) / 2, Maxhits.melee(p, victim))), Hitmask.CRITICAL, CombatIcon.MELEE));
                            }
                        } else {
                            containerHit.setHit(new Hit((int) (containerHit.getHit().getDamage()  + Misc.random(Maxhits.melee(p, victim) / 2, Maxhits.melee(p, victim))), Hitmask.CRITICAL, CombatIcon.MELEE));
                        }
						if (p.isCritMessagesEnabled()) {
							p.sendMessage("You landed a critical strike!");
						}
					}
					if (combatType == CombatType.RANGED) {
                        if (p.getNodesUnlocked() != null) {
                            if (p.getSkillTree().isNodeUnlocked(Node.BLADE_DANCER)) {
                                containerHit.setHit(new Hit((int) ((containerHit.getHit().getDamage()  + Misc.random(Maxhits.ranged(p, victim) / 2, Maxhits.ranged(p, victim))) * 1.05), Hitmask.CRITICAL, CombatIcon.RANGED));
                            } else {
                                containerHit.setHit(new Hit((int) (containerHit.getHit().getDamage()  + Misc.random(Maxhits.ranged(p, victim) / 2, Maxhits.ranged(p, victim))), Hitmask.CRITICAL, CombatIcon.RANGED));
                            }
                        } else {
                            containerHit.setHit(new Hit((int) (containerHit.getHit().getDamage()  + Misc.random(Maxhits.ranged(p, victim) / 2, Maxhits.ranged(p, victim))), Hitmask.CRITICAL, CombatIcon.RANGED));
                        }
						if (p.isCritMessagesEnabled()) {
							p.sendMessage("You landed a critical strike!");
						}
					}
					if (combatType == CombatType.MAGIC) {
                        if (p.getNodesUnlocked() != null) {
                            if (p.getSkillTree().isNodeUnlocked(Node.BLADE_DANCER)) {
                                containerHit.setHit(new Hit((int) ((containerHit.getHit().getDamage()  + Misc.random(Maxhits.magic(p, victim) / 2, Maxhits.magic(p, victim))) * 1.05), Hitmask.CRITICAL, CombatIcon.MAGIC));
                            } else {
                                containerHit.setHit(new Hit((int) (containerHit.getHit().getDamage()  + Misc.random(Maxhits.magic(p, victim) / 2, Maxhits.magic(p, victim))), Hitmask.CRITICAL, CombatIcon.MAGIC));
                            }
                        } else {
                            containerHit.setHit(new Hit((int) (containerHit.getHit().getDamage()  + Misc.random(Maxhits.magic(p, victim) / 2, Maxhits.magic(p, victim))), Hitmask.CRITICAL, CombatIcon.MAGIC));
                        }
						if (p.isCritMessagesEnabled()) {
							p.sendMessage("You landed a critical strike!");
						}
					}
				}
			}
		}

        if (attacker.isPlayer() && ((Player) attacker).getNodesUnlocked() != null) {
            Player p = (Player) attacker;
            for (ContainerHit containerHit : array) {
                if (Misc.random(150) == 1) {
                    if (combatType == CombatType.MELEE) {
                        if (p.getSkillTree().isNodeUnlocked(Node.RUTHLESS_WRATH)) {
                            containerHit.setHit(new Hit((int) ((containerHit.getHit().getDamage()  + Misc.random(Maxhits.magic(p, victim) / 2, Maxhits.magic(p, victim))) * 1.05), Hitmask.FIRE, CombatIcon.MELEE));
                            if (p.isCritMessagesEnabled()) {
                                p.sendMessage("You unleash Cinder's Wrath!");
                            }
                        }
                    }
                    if (combatType == CombatType.RANGED) {
                        if (p.getSkillTree().isNodeUnlocked(Node.RUTHLESS_WRATH)) {
                            containerHit.setHit(new Hit((int) ((containerHit.getHit().getDamage()  + Misc.random(Maxhits.magic(p, victim) / 2, Maxhits.magic(p, victim))) * 1.05), Hitmask.FIRE, CombatIcon.RANGED));
                            if (p.isCritMessagesEnabled()) {
                                p.sendMessage("You unleash Cinder's Wrath!");
                            }
                        }
                    }
                    if (combatType == CombatType.MAGIC) {
                        if (p.getSkillTree().isNodeUnlocked(Node.RUTHLESS_WRATH)) {
                            containerHit.setHit(new Hit((int) ((containerHit.getHit().getDamage()  + Misc.random(Maxhits.magic(p, victim) / 2, Maxhits.magic(p, victim))) * 1.05), Hitmask.FIRE, CombatIcon.MAGIC));
                            if (p.isCritMessagesEnabled()) {
                                p.sendMessage("You unleash Cinder's Wrath!");
                            }
                        }
                    }
                }
            }
        }

		//SALTS
		if (attacker.isPlayer()) {
			Player p = (Player) attacker;
				if (p.getInventory().getAmount(23119) >= 1) {
					p.getInventory().delete(23119, 1);
				}
				if (p.getInventory().getAmount(23121) >= 1) {
					p.getInventory().delete(23121, 1);
				}
				if (p.getInventory().getAmount(23122) >= 1) {
					p.getInventory().delete(23122, 1);
				}
			if (p.getInventory().getAmount(357) >= 1) {
				p.getInventory().delete(357, 1);
			}
		}

		if(attacker instanceof NPC) {
			NPC npc = (NPC) attacker;

			// The wolf should do 10 damage every attack
			if(npc.getId() == 6047) {
				for (int i = 0; i < hitAmount; i++) {
					array[i].getHit().setDamage(100);
					array[i].setAccurate(true);
				}
			}
		}

		/** SPECS **/


		if (attacker.isPlayer() && victim.isNpc()) {
			Player p = (Player) attacker;
			NPC npc = (NPC) victim;

			if (p.getForgottenRaidParty() != null && p.getForgottenRaidParty().isInRaid()) {
				if (p.getForgottenRaidParty().getRaid().getCurrentBoss() != null) {
					ForgottenRaidBoss forgottenRaidBoss = p.getForgottenRaidParty().getRaid().getCurrentBoss();
					if (npc.getId() == forgottenRaidBoss.npcId()) {
						if (forgottenRaidBoss instanceof RaidBossOne) {
							RaidBossOne raidBoss = (RaidBossOne) forgottenRaidBoss;

						} else if (forgottenRaidBoss instanceof RaidBossTwo) {
							RaidBossTwo raidBoss = (RaidBossTwo) forgottenRaidBoss;
							if (!raidBoss.killedMinions()) {
								for (int i = 0; i < hitAmount; i++)
									array[i].getHit().setDamage(0);
							}
						} else if (forgottenRaidBoss instanceof RaidBossThree) {
							RaidBossThree raidBoss = (RaidBossThree) forgottenRaidBoss;
							boolean onTile = false;
							for (RaidBossThree.MarkedTile markedTile : raidBoss.getMarkedTileList()) {
								if (markedTile.onTile(p)) {
									onTile = true;
									break;
								}
							}
							if (!onTile)
								for (int i = 0; i < hitAmount; i++)
									array[i].getHit().setDamage(0);
						} else if (forgottenRaidBoss instanceof RaidBossFour) {
							RaidBossFour raidBoss = (RaidBossFour) forgottenRaidBoss;

						} else if (forgottenRaidBoss instanceof RaidBossFive) {
							RaidBossFive raidBoss = (RaidBossFive) forgottenRaidBoss;
							if (!raidBoss.killedMinions()) {
								for (int i = 0; i < hitAmount; i++)
									array[i].getHit().setDamage(0);
							}
						}
					}
				}
			}
		}
		return array;
	}

	public void setHits(ContainerHit[] hits) {
		this.hits = hits;
		prepareHits(hits.length);
	}


	protected final void allHits(Consumer<ContainerHit> c) {
		Arrays.stream(hits).filter(Objects::nonNull).forEach(c);
	}

	public final int getDamage() {
		int damage = 0;
		for (ContainerHit hit : hits) {
			if (hit == null)
				continue;
			if (!hit.accurate) {
				int absorb = (int) hit.getHit().getAbsorb();
				hit.hit = new Hit(0, Hitmask.NEUTRAL, CombatIcon.BLOCK);
				hit.hit.setAbsorb(absorb);
			}
			damage += hit.hit.getDamage();
		}
		return damage;
	}

	public final void dealDamage() {
		if (hits.length == 1) {
			victim.dealDamage(hits[0].getHit());
		} else if (hits.length == 2) {
			victim.dealDoubleDamage(hits[0].getHit(), hits[1].getHit());
		} else if (hits.length == 3) {
			victim.dealTripleDamage(hits[0].getHit(), hits[1].getHit(), hits[2].getHit());
		} else if (hits.length == 4) {
			victim.dealQuadrupleDamage(hits[0].getHit(), hits[1].getHit(), hits[2].getHit(), hits[3].getHit());
		}
	}

	/**
	 * Gets all of the skills that will be trained.
	 * 
	 * @param type the combat type being used.
	 * 
	 * @return an array of skills that this attack will train.
	 */
	private int[] getSkills(CombatType type) {
		if (attacker.isNpc()) {
			return new int[] {};
		}
		return ((Player) attacker).getFightType().getStyle().skill(type);
	}

	public void setModifiedDamage(int modifiedDamage) {
		this.modifiedDamage = modifiedDamage;
	}

	public int getModifiedDamage() {
		return modifiedDamage;
	}

	/**
	 * A dynamic method invoked when the victim is hit with an attack. An example of
	 * usage is using this to do some sort of special effect when the victim is hit
	 * with a spell. <b>Do not reset combat builder in this method!</b>
	 * 
	 * @param damage   the damage inflicted with this attack, always 0 if the attack
	 *                 isn't accurate.
	 * @param accurate if the attack is accurate.
	 */
	public void onHit(int damage, boolean accurate) {
	}

	/**
	 * Gets the hits that will be dealt during this combat hook.
	 * 
	 * @return the hits that will be dealt during this combat hook.
	 */
	public final ContainerHit[] getHits() {
		return hits;
	}

	/**
	 * Gets the skills that will be given experience.
	 * 
	 * @return the skills that will be given experience.
	 */
	public final int[] getExperience() {
		return experience;
	}

	/**
	 * Sets the amount of hits that will be dealt during this combat hook.
	 * 
	 * @param hitAmount the amount of hits that will be dealt during this combat
	 *                  hook.
	 */
	public final void setHitAmount(int hitAmount) {
		this.hits = prepareHits(hitAmount);
	}

	/**
	 * Gets the combat type that is being used during this combat hook.
	 * 
	 * @return the combat type that is being used during this combat hook.
	 */
	public final CombatType getCombatType() {
		return combatType;
	}

	/**
	 * Sets the combat type that is being used during this combat hook.
	 * 
	 * @param combatType the combat type that is being used during this combat hook.
	 */
	public final void setCombatType(CombatType combatType) {
		this.combatType = combatType;
	}

	/**
	 * Gets if accuracy should be taken into account.
	 * 
	 * @return true if accuracy should be taken into account.
	 */
	public final boolean isCheckAccuracy() {
		return checkAccuracy;
	}

	/**
	 * Sets if accuracy should be taken into account.
	 * 
	 * @param checkAccuracy true if accuracy should be taken into account.
	 */
	public final void setCheckAccuracy(boolean checkAccuracy) {
		this.checkAccuracy = checkAccuracy;
	}

	/**
	 * Gets if at least one hit in this container is accurate.
	 * 
	 * @return true if at least one hit in this container is accurate.
	 */
	public final boolean isAccurate() {
		return accurate;
	}

	/**
	 * Gets the hit delay before the hit is executed.
	 * 
	 * @return the hit delay.
	 */
	public int getHitDelay() {
		return hitDelay;
	}

	/**
	 * A single hit that is dealt during a combat hook.
	 * 
	 * @author lare96
	 */
	public static class ContainerHit {

		/** The actual hit that will be dealt. */
		private Hit hit;

		/** The accuracy of the hit to be dealt. */
		private boolean accurate;

		/**
		 * Create a new {@link ContainerHit}.
		 * 
		 * @param hit      the actual hit that will be dealt.
		 * @param accurate the accuracy of the hit to be dealt.
		 */
		public ContainerHit(Hit hit, boolean accurate) {
			this.hit = hit;
			this.accurate = accurate;
		}

		/**
		 * Gets the actual hit that will be dealt.
		 * 
		 * @return the actual hit that will be dealt.
		 */
		public Hit getHit() {
			return hit;
		}

		/**
		 * Sets the actual hit that will be dealt.
		 * 
		 * @param hit the actual hit that will be dealt.
		 */
		public void setHit(Hit hit) {
			this.hit = hit;
		}

		/**
		 * Gets the accuracy of the hit to be dealt.
		 * 
		 * @return the accuracy of the hit to be dealt.
		 */
		public boolean isAccurate() {
			return accurate;
		}

		/**
		 * Sets the accuracy of the hit to be dealt.
		 * 
		 * @param accurate the accuracy of the hit to be dealt.
		 */
		public void setAccurate(boolean accurate) {
			this.accurate = accurate;
		}
	}
}
