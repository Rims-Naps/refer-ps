package com.ruse.world.content.combat.weapon;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.PlayerSpecialAmountTask;
import com.ruse.model.*;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.WeaponInterfaces.WeaponInterface;
import com.ruse.world.content.AoE.AOEHandler;
import com.ruse.world.content.combat.CombatContainer;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.Maxhits;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.minigames.impl.Dueling;
import com.ruse.world.content.minigames.impl.Dueling.DuelRule;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.player.Player;

import java.util.Arrays;

public enum CombatSpecial {

	MYSTIC_BOW_1(new int[] { 16415 }, 25, CombatType.RANGED, WeaponInterface.LONGBOW) {

		@Override
		public CombatContainer container(Player player, Character target) {
			player.performGraphic(new Graphic(1678));
			player.msgRed("You unleash a Barrage of Energy!");
			TaskManager.submit(new Task(1) {
				int ticks = 0;

				@Override
				protected void execute() {
					if (ticks == 0) {
						new Projectile(player, target, 334 , 40, 3, 35, 31, 0).sendProjectile();
					}
					if (ticks == 1) {
						new Projectile(player, target, 334, 40, 2, 15, 25, 0).sendProjectile();
					}
					if (ticks == 2) {
						new Projectile(player, target, 335 , 40, 2, 30, 10, 0).sendProjectile();
					}
					if (ticks == 3) {
						new Projectile(player, target, 336, 40, 2, 40, 10, 0).sendProjectile();
					}
					if (ticks >= 4) {
						AOEHandler.handleAttack(player, target, Maxhits.ranged(player, target) / 20, Maxhits.ranged(player, target) / 10, 2, CombatIcon.RANGED);
						stop();
					}
					ticks++;
				}
			});
			return new CombatContainer(player, target, 4, CombatType.RANGED, true);
		}
	},


	MYSTIC_BLADE_2(new int[] { 16416, 17101,17104 }, 25, CombatType.MELEE, WeaponInterface.SWORD) {

		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(10202));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {
						player.msgRed("Your enemies tremble with fear!");

					} else if (tick == 1) {
						//target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
						for (int i = 0; i < 2; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(76), new Position(target.getPosition().getX() + i, target.getPosition().getY(), target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(76), new Position(target.getPosition().getX(), target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(76), new Position(target.getPosition().getX() - i, target.getPosition().getY() + i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(76), new Position(target.getPosition().getX() + i, target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(76), new Position(target.getPosition().getX() - i, target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(76), new Position(target.getPosition().getX() + i, target.getPosition().getY() + i, target.getPosition().getZ()));

						}
					}  else if(tick == 3) {
						AOEHandler.handleAttack(player, target, Maxhits.melee(player, target) / 20, Maxhits.melee(player, target) / 10, 5, CombatIcon.MELEE);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 1, CombatType.MELEE, true);
		}
	},

	MYSTIC_STAFF_3(new int[]{ 16417 }, 25, CombatType.MAGIC, WeaponInterface.STAFF) {
		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(426));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {
						new Projectile(player, target, 311, 44, 3, 32, 31, 0).sendProjectile();
						new Projectile(player, target, 311, 60, 3, 32, 31, 0).sendProjectile();
						player.msgRed("This is gonna Hurt!");

					} else if (tick >= 1) {
						target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
						for (int i = 0; i < 3; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(83),
									new Position(target.getPosition().getX() + i, target.getPosition().getY(),
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(83),
									new Position(target.getPosition().getX(), target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(83),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() + i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(83),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(83),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(83),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() + i,
											target.getPosition().getZ()));

						}
						AOEHandler.handleAttack(player, target, Maxhits.magic(player, target) / 10,
								Maxhits.magic(player, target) / 10, 3, CombatIcon.MAGIC);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 4, CombatType.MAGIC, true);
		}
	},

	MYSTIC_BOW_4(new int[] { 16418 }, 25, CombatType.RANGED, WeaponInterface.LONGBOW) {

		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(426));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {
						player.msgRed("May my arrows find their mark.");
					}
					else if (tick == 1) {
						target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
						for (int i = 0; i < 3; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() + i, target.getPosition().getY(),
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX(), target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() + i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() + i,
											target.getPosition().getZ()));

						}
					}  else if(tick == 7) {
						AOEHandler.handleAttack(player, target, Maxhits.ranged(player, target) / 10,
								Maxhits.ranged(player, target) / 10, 3, CombatIcon.RANGED);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 4, CombatType.RANGED, true);
		}
	},

	MYSTIC_BLADE_5(new int[]{ 16419 }, 25, CombatType.MELEE, WeaponInterface.SWORD) {
		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(401));
			player.getPacketSender().sendGlobalGraphic(new Graphic(55),
					new Position(target.getPosition().getX(), target.getPosition().getY(),
							target.getPosition().getZ()));
			player.getPacketSender().sendGlobalGraphic(new Graphic(2876),
					new Position(target.getPosition().getX(), target.getPosition().getY(),
							target.getPosition().getZ()));
			return new CombatContainer(player, target, 4, CombatType.MELEE, true);
		}
	},

	MYSTIC_STAFF_6(new int[] { 16420, }, 25, CombatType.MAGIC, WeaponInterface.STAFF) {

		@Override
		public CombatContainer container(Player player, Character target) {
			player.performGraphic(new Graphic(93));
			player.msgRed("You unleash a Barrage of Water!");
			TaskManager.submit(new Task(1) {
				int ticks = 0;

				@Override
				protected void execute() {
					if (ticks == 0) {
						new Projectile(player, target, 678 , 40, 3, 35, 31, 0).sendProjectile();
					}
					if (ticks == 1) {
						new Projectile(player, target, 678, 40, 2, 15, 25, 0).sendProjectile();
					}
					if (ticks == 2) {
						new Projectile(player, target, 678 , 40, 2, 30, 10, 0).sendProjectile();
					}
					if (ticks == 3) {
						new Projectile(player, target, 5, 40, 2, 40, 10, 0).sendProjectile();
					}
					if (ticks >= 4) {
						AOEHandler.handleAttack(player, target, Maxhits.magic(player, target) / 20, Maxhits.magic(player, target) / 10, 2, CombatIcon.MAGIC);
						stop();
					}
					ticks++;
				}
			});
			return new CombatContainer(player, target, 4, CombatType.MAGIC, true);
		}
	},

	MYSTIC_BOW_7(new int[]{ 16421, 17102,17105 }, 25, CombatType.RANGED, WeaponInterface.LONGBOW) {
		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(426));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {

					} else if (tick == 1) {
						//target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
						for (int i = 0; i < 5; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(357),
									new Position(target.getPosition().getX() + i, target.getPosition().getY(),
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(357),
									new Position(target.getPosition().getX(), target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(357),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() + i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(357),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(357),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(357),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() + i,
											target.getPosition().getZ()));

						}
					}  else if(tick == 3) {
						AOEHandler.handleAttack(player, target, Maxhits.ranged(player, target) / 10,
								Maxhits.ranged(player, target) / 10, 3, CombatIcon.RANGED);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 4, CombatType.RANGED, true);
		}
	},

	SPECTRAL_BOW(new int[] { 2086,751 }, 25, CombatType.RANGED, WeaponInterface.LONGBOW) {

		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(426));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {
						player.msgRed("May my arrows find their mark.");
					}
					else if (tick == 1) {
						target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
						for (int i = 0; i < 3; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() + i, target.getPosition().getY(),
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX(), target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() + i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(832),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() + i,
											target.getPosition().getZ()));

						}
					}  else if(tick == 7) {
						AOEHandler.handleAttack(player, target, Maxhits.ranged(player, target) / 10,
								Maxhits.ranged(player, target) / 10, 3, CombatIcon.RANGED);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 4, CombatType.RANGED, true);
		}
	},

	SPECTRAL_STAFF(new int[]{2087,17106 ,17103,752}, 25, CombatType.MAGIC, WeaponInterface.STAFF) {
		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(1979));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {
						new Projectile(player, target, 311, 44, 3, 32, 31, 0).sendProjectile();
						new Projectile(player, target, 311, 60, 3, 32, 31, 0).sendProjectile();
						player.msgRed("Feel the Chill!!");

					} else if (tick >= 1) {
						target.performGraphic(new Graphic(369, GraphicHeight.LOW));
						for (int i = 0; i < 3; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(1351),
									new Position(target.getPosition().getX() + i, target.getPosition().getY(),
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(1351),
									new Position(target.getPosition().getX(), target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(1351),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() + i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(1351),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(1351),
									new Position(target.getPosition().getX() - i, target.getPosition().getY() - i,
											target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(1351),
									new Position(target.getPosition().getX() + i, target.getPosition().getY() + i,
											target.getPosition().getZ()));

						}
						AOEHandler.handleAttack(player, target, Maxhits.magic(player, target) / 10,
								Maxhits.magic(player, target) / 10, 3, CombatIcon.MAGIC);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 4, CombatType.MAGIC, true);
		}
	},

	SPECTRAL_SCYTHE(new int[] {2088,750}, 25, CombatType.MELEE, WeaponInterface.SCYTHE) {

		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(10202));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {
						player.msgRed("Your enemies tremble with fear!");

					} else if (tick == 1) {
						//target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
						for (int i = 0; i < 2; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() + i, target.getPosition().getY(), target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX(), target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() - i, target.getPosition().getY() + i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() + i, target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() - i, target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() + i, target.getPosition().getY() + i, target.getPosition().getZ()));

						}
					}  else if(tick == 3) {
						AOEHandler.handleAttack(player, target, Maxhits.melee(player, target) / 20, Maxhits.melee(player, target) / 10, 5, CombatIcon.MELEE);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 3, CombatType.MELEE, true);
		}
	},

	CUSTOM_SCYTHE(new int[] {23188}, 25, CombatType.MELEE, WeaponInterface.CUSTOM_SCYTHE) {

		@Override
		public CombatContainer container(Player player, Character target) {
			player.performAnimation(new Animation(10202));

			TaskManager.submit(new Task(1, player, false) {
				int tick = 0;

				@Override
				public void execute() {
					if (tick == 0) {
						player.msgRed("Your enemies tremble with fear!");

					} else if (tick == 1) {
						//target.performGraphic(new Graphic(2345, GraphicHeight.LOW));
						for (int i = 0; i < 2; i++) {
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() + i, target.getPosition().getY(), target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX(), target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() - i, target.getPosition().getY() + i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() + i, target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() - i, target.getPosition().getY() - i, target.getPosition().getZ()));
							player.getPacketSender().sendGlobalGraphic(new Graphic(136), new Position(target.getPosition().getX() + i, target.getPosition().getY() + i, target.getPosition().getZ()));

						}
					}  else if(tick == 3) {
						AOEHandler.handleAttack(player, target, Maxhits.melee(player, target) / 20, Maxhits.melee(player, target) / 10, 5, CombatIcon.MELEE);
						this.stop();
					}
					tick++;
				}
			});

			return new CombatContainer(player, target, 1, CombatType.MELEE, true);
		}
	},

	DEFAULT(new int[]{}, 50, CombatType.RANGED, WeaponInterface.JAVELIN) {
		@Override
		public CombatContainer container(Player player, Character target) {
			return new CombatContainer(player, target, 1, CombatType.RANGED, true);
		}
	};
	private int[] identifiers;
	private int drainAmount;
	private CombatType combatType;
	private WeaponInterface weaponType;


	private CombatSpecial(int[] identifiers, int drainAmount,
						  CombatType combatType, WeaponInterface weaponType) {
		this.identifiers = identifiers;
		this.drainAmount = drainAmount;
		this.combatType = combatType;
		this.weaponType = weaponType;
	}

	public void onActivation(Player player, Character target) {

	}

	public abstract CombatContainer container(Player player, Character target);

	public static void drain(Player player, int amount) {
		player.decrementSpecialPercentage(amount);
		player.setSpecialActivated(false);
		CombatSpecial.updateBar(player);
		if (!player.isRecoveringSpecialAttack())
			TaskManager.submit(new PlayerSpecialAmountTask(player));
	}

	public static void restore(Player player, int amount) {
		player.incrementSpecialPercentage(amount);
		CombatSpecial.updateBar(player);
	}

	public static void updateBar(Player player) {
		if (player.getWeapon().getSpecialBar() == -1 || player.getWeapon().getSpecialMeter() == -1) {
			return;
		}
		int specialCheck = 10;
		int specialBar = player.getWeapon().getSpecialMeter();
		int specialAmount = player.getSpecialPercentage() / 10;

		for (int i = 0; i < 10; i++) {
			player.getPacketSender().sendInterfaceComponentMoval(specialAmount >= specialCheck ? 500 : 0, 0, --specialBar);
			specialCheck--;
		}
		player.getPacketSender().updateSpecialAttackOrb().sendString(player.getWeapon().getSpecialMeter(), player.isSpecialActivated() ? ("@yel@ Special Attack (" + player.getSpecialPercentage() + "%)") : ("@bla@ Special Attack (" + player.getSpecialPercentage() + "%"));

	}

	public static void assign(Player player) {
		if (player.getWeapon().getSpecialBar() == -1) {
			player.setSpecialActivated(false);
			player.setCombatSpecial(null);
			CombatSpecial.updateBar(player);
			return;
		}

		for (CombatSpecial c : CombatSpecial.values()) {
			if (player.getWeapon() == c.getWeaponType()) {
				if (Arrays.stream(c.getIdentifiers()).anyMatch(id -> player.getEquipment().get(Equipment.WEAPON_SLOT).getId() == id)) {
					player.getPacketSender().sendInterfaceDisplayState(player.getWeapon().getSpecialBar(), false);
					player.setCombatSpecial(c);
					return;
				}
			}
		}

		player.getPacketSender().sendInterfaceDisplayState(player.getWeapon().getSpecialBar(), true);
		player.setCombatSpecial(null);
	}

	public static void activate(Player player) {
		if (Dueling.checkRule(player, DuelRule.NO_SPECIAL_ATTACKS)) {
			player.getPacketSender().sendMessage("Special Attacks have been turned off in this duel.");
			return;
		}
		if (player.getCombatSpecial() == null) {
			return;
		}
		if (player.isSpecialActivated()) {
			player.setSpecialActivated(false);
			CombatSpecial.updateBar(player);
		} else {
			if (player.getSpecialPercentage() < player.getCombatSpecial().getDrainAmount()) {
				player.getPacketSender().sendMessage("You do not have enough special attack energy left!");
				return;
			}

			CombatSpecial spec = player.getCombatSpecial();
			boolean instantSpecial = spec == CombatSpecial.DEFAULT;

			if (player.isAutocast()) {
				Autocasting.resetAutocast(player, true);
			} else if (player.hasStaffOfLightEffect()) {
				player.getPacketSender().sendMessage("You are already being protected by the Staff of Light!");
				return;
			}

			player.setSpecialActivated(true);
			if (instantSpecial) {
				spec.onActivation(player, player.getCombatBuilder().getVictim());
				if (player.getCombatBuilder().isAttacking() && !player.getCombatBuilder().isCooldown()) {
					player.getCombatBuilder().setAttackTimer(0);
					player.getCombatBuilder().attack(player.getCombatBuilder().getVictim());
					player.getCombatBuilder().instant();
				} else
					CombatSpecial.updateBar(player);
			} else {
				CombatSpecial.updateBar(player);
				TaskManager.submit(new Task(1, false) {
					@Override
					public void execute() {
						if (!player.isSpecialActivated()) {
							this.stop();
							return;
						}
						spec.onActivation(player, player.getCombatBuilder().getVictim());
						this.stop();
					}
				}.bind(player));
			}
		}
	}

	public int[] getIdentifiers() {
		return identifiers;
	}

	public int getDrainAmount() {
		return drainAmount;
	}

	public CombatType getCombatType() {
		return combatType;
	}

	public WeaponInterface getWeaponType() {
		return weaponType;
	}
}
