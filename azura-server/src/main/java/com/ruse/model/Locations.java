package com.ruse.model;

import com.ruse.GameSettings;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPunishment.Jail;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.minigames.impl.Barrows;
import com.ruse.world.content.minigames.impl.CircleOfElements;
import com.ruse.world.content.minigames.impl.TheSix;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.minigames.impl.dungeoneering.DungeoneeringParty;
import com.ruse.world.content.new_raids_system.instances.CorruptRaid;
import com.ruse.world.content.new_raids_system.instances.VoidRaid;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.parties.PartyService;
import com.ruse.world.entity.Entity;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.instance.MapInstance;

public class Locations {
	public static boolean inside(Position start, int startSize, Position target, int targetSize) {
		if (target == null) {
			return false;
		}
		int distanceX = start.getX() - target.getX();
		int distanceY = start.getY() - target.getY();
		if (distanceX < targetSize && distanceX > -startSize && distanceY < targetSize && distanceY > -startSize) {
			return true;
		}
		return false;
	}
	public static void login(Player player) {
		player.setLocation(Location.getLocation(player));
		player.getLocation().login(player);
		player.getLocation().enter(player);
	}

	public static void logout(Player player) {
		player.getLocation().logout(player);
		if (player.getRegionInstance() != null) {
			player.getRegionInstance().destruct();
		}
			player.getLocation().leave(player);
	}


	public static int PLAYERS_IN_DUEL_ARENA;

	public enum Location {

		HOME(12599, false, true, true, false, false, false) {
		},

		BRIMSTONE(new int[] { 3866, 3881 }, new int[] { 2775, 2791 }, true, true, false, false, false, false) {

			@Override
			public void login(Player player) {
				player.moveTo(GameSettings.DEFAULT_POSITION);
			}
			@Override
			public void leave(Player player) {
				if (player.getBossFight() != null) {
					player.getBossFight().endFight();
				}
			}
		},

		BASILISK(10542, true, true, false, false, false, false) {

			@Override
			public void login(Player player) {
				player.moveTo(GameSettings.DEFAULT_POSITION);
			}
			@Override
			public void leave(Player player) {
				if (player.getBossFight() != null) {
					player.getBossFight().endFight();
				}
			}
		},
		DAILY_BOSSES(10037, true, true, false, false, false, false) {
		},

		DONATION_BOSS(14388, true, true, false, false, false, false) {
		},

		ARCHON_BOSS(9527, true, true, false, false, false, false) {
		},
		CELESTIAL_BOSS(10039, true, true, false, false, false, false) {
		},
		ASCENDANT_BOSS(10551, true, true, false, false, false, false) {
		},
		GLADIATOR_BOSS(11063, true, true, false, false, false, false) {
		},


		GLOBAL_BOSSES(11343, true, true, false, false, false, false) {
		},
		SKELETAL_DEMON(7238, true, true, false, false, false, false) {
		},
		EMERALD_CHAMPION(10841, true, true, false, false, false, false) {
		},

		SPRING_BOSS(13113, true, true, false, false, false, false) {
		},

		VOID_BOSS1(new int[]{3391, 3421}, new int[]{5252, 5277}, true, true, false, false, false, false) {
		},

		VOID_BOSS2(new int[]{3426, 3457}, new int[]{5250, 5276}, true, true, false, false, false, false) {
		},

		SLAYER_BEAST(6722, true, true, false, false, false, false) {
		},

		ORACLE(13910, true, true, false, false, false, false) {
		},
		ROCKMAW(14164, true, true, false, false, false, false) {
		},
		GRIMLASH(14676, true, true, false, false, false, false) {
		},

		DREADFLESH(12621, true, true, false, false, false, false) {
		},
		XAROTH(12619, true, true, false, false, false, false) {
		},

		HELLFIRE(8022, true, true, false, false, false, false) {
		},
		CRYOS(11920, true, true, false, false, false, false) {
		},
		TOXUS(9362, true, true, false, false, false, false) {
		},

		GEMSTONE(12181, true, true, false, false, false, false) {
		},

		CORRUPT_DUNGEON(new int[]{1587, 1728}, new int[]{5626, 5774}, true, true, false, false, false, true) {
		},

		SPECTRAL_DUNGEON(new int[]{2479, 2637}, new int[]{9391, 9546}, true, true, false, false, false, true) {
		},

		ENCHANTED_FOREST(new int[]{2621, 2754}, new int[]{3766, 3907}, false, true, false, false, false, true) {
		},


		CUPID(8498, true, true, false, false, false, false) {
		},

		AFKBOSS(10535, true, true, false, false, false, false) {
		},

		EASTER_BOSS(10043, true, true, false, false, false, false) {
		},

		FALLEN_KNIGHT(9009, true, true, false, false, false, false) {
		},


		NEW_BARROWS_LOBBY(new int[]{2718, 3023}, new int[]{2470, 2677}, false, true, false, false, false, true) {
			@Override
			public void enter(Player player) {
				CircleOfElements.resetBarrows(player);
				player.vod.start();
				player.getPacketSender().sendWalkableInterface(126500, true);
			}

			@Override
			public boolean canTeleport(Player player) {
				return true;
			}

			@Override
			public void leave(Player player) {
					CircleOfElements.resetBarrows(player);
					player.getPacketSender().sendCameraNeutrality();
					player.getPacketSender().sendWalkableInterface(126500, false);
					CircleOfElements.updateInterface(player);
			}
			@Override
			public void login(Player player) {
				player.getPacketSender().sendWalkableInterface(126500, true);
			}
			@Override
			public void logout(Player player) {
				player.getPacketSender().sendWalkableInterface(126500, false);
			}


		},
		WAVE_MINIGAME(new int[]{4444, 4444}, new int[]{4444, 4444}, true, false, false, false, false, true) {
		},



		FRENZY_BRIDGE_1(new int[] { 2511, 2515 }, new int[] { 2775, 2792 }, true, true, false, false, false, false) {
		},

		FRENZY_BRIDGE_2(new int[] { 2511, 2533 }, new int[] { 2793, 2797 }, true, true, false, false, false, false) {
		},

		FRENZYBOSS(new int[] { 2507, 2519 }, new int[] { 2766, 2775 }, true, true, false, false, false, false) {

		},
		FRENZYZONE2(new int[] { 2534, 2541 }, new int[] { 2790, 2800 }, true, true, false, false, false, false) {

		},

		STREAM_BOSS(new int[] { 2527, 2539 }, new int[] { 2769, 2781 }, true, true, false, false, false, false) {

		},

		PRAYER_MINIGAME_MINION_1(new int[] { 2619, 2694 }, new int[] { 3964, 4052 }, true, true, false, false, false, false) {

		},
		PRAYER_MINIGAME_BOSS_1(new int[] { 1931, 1975 }, new int[] { 5007, 5042 }, false, true, false, false, false, false) {
			@Override
			public void login(Player player) {
				player.moveTo(2650, 3990, Misc.random(0, 1) * 4);
			}
			@Override
			public void leave(Player player) {
				player.getPacketSender().sendWalkableInterface(4958, false);
			}
		},
		PRAYER_MINIGAME_BOSS_2(new int[] { 1933, 1974 }, new int[] { 5139, 5166 }, true, true, false, false, false, false) {

			@Override
			public void login(Player player) {
				player.moveTo(2650, 3990, Misc.random(0, 1) * 4);
			}

			@Override
			public void leave(Player player) {
				player.getPacketSender().sendWalkableInterface(4958, false);
			}
		},
		PRAYER_MINIGAME_BOSS_3(new int[] { 1805, 1849 }, new int[] { 5070, 5111 }, true, true, false, false, false, false) {


			@Override
			public void login(Player player) {
				player.moveTo(2650, 3990, Misc.random(0, 1) * 4);
			}
			@Override
			public void leave(Player player) {
				player.getPacketSender().sendWalkableInterface(4958, false);
			}
		},


		THORN_BLUE(new int[] { 2891, 2899 }, new int[] { 4398, 4403 }, false, true, false, false, false, false) {

			public void login(Player player) {
				player.moveTo(GameSettings.DEFAULT_POSITION);
			}
		},
		THORN_BROWN(new int[] { 2928, 2935 }, new int[] { 4400, 4404 }, false, true, false, false, false, false) {
			public void login(Player player) {
				player.moveTo(GameSettings.DEFAULT_POSITION);
			}
		},
		THORN_BLACK(new int[] { 2922, 2928 }, new int[] { 4357, 4361 }, false, true, false, false, false, false) {
			public void login(Player player) {
				player.moveTo(GameSettings.DEFAULT_POSITION);
			}
		},
		THORN_PURPLE(new int[] { 2884, 2889 }, new int[] { 4359, 4366 }, false, true, false, false, false, false) {
			public void login(Player player) {
				player.moveTo(GameSettings.DEFAULT_POSITION);
			}
		},

		EARTH_1(12352, true, true, false, false, false, false) {
		},
		EARTH_2(11840, true, true, false, false, false, false) {
		},
		EARTH_3(12610, true, true, false, false, false, false) {
		},
		EARTH_4(11842, true, true, false, false, false, false) {
		},
		EARTH_5(10023, true, true, false, false, false, false) {
		},
		EARTH_6(10281, true, true, false, false, false, false) {
		},
		EARTH_7(10539, true, true, false, false, false, false) {
		},
		WATER_1(8512, true, true, false, false, false, false) {
		},
		WATER_2(8510, true, true, false, false, false, false) {
		},
		WATER_3(8252, true, true, false, false, false, false) {
		},
		WATER_4(8506, true, true, false, false, false, false) {
		},
		WATER_5(14895, true, true, false, false, false, false) {
		},
		WATER_6(15405, true, true, false, false, false, false) {
		},
		FIRE_1(8520, true, true, false, false, false, false) {
		},
		FIRE_2(8518, true, true, false, false, false, false) {
		},
		FIRE_3(7749, true, true, false, false, false, false) {
		},
		FIRE_4(9289, true, true, false, false, false, false) {
		},
		FIRE_5(10804, true, true, false, false, false, false) {
		},
		FIRE_6(10034, true, true, false, false, false, false) {
		},
		FIRE_7(10546, true, true, false, false, false, false) {
		},
		ELITE_11(11854, true, true, false, false, false, false) {
		},
		ELITE_1(10029, true, true, false, false, false, false) {
		},
		ELITE_2(9285, true, true, false, false, false, false) {
		},
		ELITE_3(7756, true, true, false, false, false, false) {
		},
		ELITE_4(11051, true, true, false, false, false, false) {
		},
		ELITE_5(6989, true, true, false, false, false, false) {
		},
		ELITE_6(7243, true, true, false, false, false, false) {
		},
		ELITE_7(6741, true, true, false, false, false, false) {
		},
		MASTER1(10321, true, true, false, false, false, false) {
		},
		MASTER2(9553, true, true, false, false, false, false) {
		},
		MASTER3(9811, true, true, false, false, false, false) {
		},
		MASTER4(9300, true, true, false, false, false, false) {
		},
		MASTER5(10318, true, true, false, false, false, false) {
		},
		MASTER6(10830, true, true, false, false, false, false) {
		},
		MASTER7(9553, true, true, false, false, false, false) {
		},
		MASTER8(11341, true, true, false, false, false, false) {
		},
		MASTER9(9806, true, true, false, false, false, false) {
		},
		VOID_GUARDIAN(13639, true, true, false, false, false, false) {
		},

		MBOSS_1(6995, true, true, false, false, false, false) {
		},

		MBOSS_2(9775, true, true, false, false, false, false) {
		},
		MBOSS_3(6480, true, true, false, false, false, false) {
		},

        TOWER_LOBBY(new int[] { 3480, 3494 }, new int[] { 9239, 9253 }, false, false, false, false, false, false) {
            @Override
            public void leave(Player player) {
                if (player.getLocation() != TOWER_LOBBY && player.getLocation() != TOWER_GAME && player.getLocation() != TOWER_LOOT) {
                    if (player.getTowerParty() != null) {
                        if (player.getTowerParty().getOwner() == player)
                            player.getTowerParty().disbandParty();

                        if (PartyService.playerIsInParty(player))
                            player.getTowerParty().leaveParty(player);
                    }
                }
            }
        },
        TOWER_LOOT(new int[] { 2374, 2413 }, new int[] { 9223, 9283 }, false, false, false, false, false, false) {
            @Override
            public void leave(Player player) {
                if (player.getLocation() != TOWER_LOBBY && player.getLocation() != TOWER_GAME && player.getLocation() != TOWER_LOOT) {
                    if (player.getTowerParty() != null) {
                        if (player.getTowerParty().getOwner() == player)
                            player.getTowerParty().disbandParty();

                        if (PartyService.playerIsInParty(player))
                            player.getTowerParty().leaveParty(player);
                    }
                }
            }
        },

        TOWER_GAME(new int[] { 3470, 3507 }, new int[] { 3402, 3443 }, true, false, false, false, false, false) {
            @Override
            public void process(Player player) {
                //TODO: Scoreboard
            }

            @Override
            public void leave(Player player) {
                if (player.getLocation() != TOWER_LOBBY && player.getLocation() != TOWER_LOOT && player.getLocation() != TOWER_GAME) {
                    if (player.getTowerParty() != null) {
                        if (player.getTowerParty().getOwner() == player)
                            player.getTowerParty().disbandParty();

                        if (PartyService.playerIsInParty(player))
                            player.getTowerParty().leaveParty(player);
                    }
                }
            }

            @Override
            public void enter(Player player) {
                CurseHandler.deactivateAll(player);
            }

            @Override
            public boolean canTeleport(Player player) {
                return true;
            }

            @Override
            public void logout(Player player) {
                if (PartyService.playerIsInParty(player)) {
                    if (PartyService.isPlayerPartyOwner(player)) {
                        for (Player partyPlayer : player.getTowerParty().getPlayers()) {
                            if (partyPlayer == player.getTowerParty().getOwner())
                                continue;
                            player.getTowerParty().leaveParty(partyPlayer);
                            partyPlayer.moveTo(new Position(3486, 9246, 0));
                            partyPlayer.sendMessage("Party Owner has logged out resulting in Tower Ending.");
                        }
                        player.getTowerParty().disbandParty();
                    } else {
                        player.getTowerParty().leaveParty(player);
                    }
                }
            }

            @Override
            public void login(Player player) {
                player.moveTo(new Position(3486, 9246, 0));
            }

            @Override
            public void onDeath(Player player) {
                if (PartyService.playerIsInParty(player)) {
                    player.getTowerParty().getOwner().getTowerRaid().handleDeath(player);
                }
            }
        },

        SANTA_BOSS_AREA(new int[] { 2541, 2579 }, new int[] { 4589, 4627 }, true, true, false, false, false, false) {
            @Override
            public void enter(Player plr) {
                if (!plr.isCompletedChristmas()) {
                    plr.getPacketSender().sendMessage("You must complete the Christmas Quest to gain access to the boss area");
                    plr.moveTo(2561, 4633, 0);
                }
            }
        },

        SANTA_SLAYER_AREA(new int[] { 2574, 2577 }, new int[] { 4632, 4677 }, true, true, false, false, false, false) {
            @Override
            public void enter(Player plr) {
                if (!plr.isCompletedChristmas()) {
                    plr.getPacketSender().sendMessage("You must complete the Christmas Quest to gain access to the slayer area");
                    plr.moveTo(2561, 4651, 0);
                }
            }
        },

		RIFT(5965, true, true, false, false, false, false) {
		},

		CRIFT(12078, true, true, false, false, false, false) {
		},

		TREASURE_HUNTER(new int[] { 1986, 2045 }, new int[] { 4994, 5052 }, true, true, false, false, false, false) {
		},
		PURO_PURO(new int[] { 2556, 2630 }, new int[] { 4281, 4354 }, false, true, false, false, false, true) {
		},
		HOME_BANK(new int[]{2635, 2675}, new int[]{3965, 4031}, true, true, false, false, false, false) {
		},
		VARROCK(10000, true, true, false, false, false, true) {
		},

		READYLOBBY(new int[]{3225, 3238}, new int[]{3353, 3366}, false, true, false, false, false, true) {
		},
		NARNIARAIDS(new int[]{3267, 3389}, new int[]{3460, 3581}, true, true, false, false, false, true) {
			@Override
			public void enter(Player player) {
			}
		},

/*		NECRO_ISLAND_AREA(new int[]{2887, 2901}, new int[]{4745, 4788}, true, true, false, false, false, true) {
			@Override
			public void leave(Player player) {
				if (player.getLocation() != NECROMANCY_GAME_AREA) {
					NecromancyMinigame.leave(player, true);
				}
			}

			@Override
			public void logout(Player player) {
				NecromancyMinigame.leave(player, true);
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC n) {
				return true;
			}

			@Override
			public void onDeath(Player player) {
				NecromancyMinigame.leave(player, true);
			}
		},*/


		NECROMANCY_LOBBY_AREA(new int[]{3020, 3032}, new int[]{3853, 3869}, false, true, false, false, false, true) {
			@Override
			public void process(Player player) {
			}

			@Override
			public boolean canTeleport(Player player) {
				return true;
			}

			@Override
			public void leave(Player player) {
				if (player.getLocation() != NECROMANCY_GAME_AREA) {
					NecromancyMinigame.leave(player, true);
				}
			}

			@Override
			public void logout(Player player) {
				NecromancyMinigame.leave(player, true);
			}
		},

		NECROMANCY_GAME_AREA(new int[]{2754, 2878}, new int[]{3969, 4106}, true, true, false, false, true, true) {

			@Override
			public void leave(Player player) {
					NecromancyMinigame.leave(player, true);
			}

			@Override
			public void logout(Player player) {
				NecromancyMinigame.leave(player, true);
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC n) {
				return true;
			}

			@Override
			public void onDeath(Player player) {
				NecromancyMinigame.leave(player, true);
			}
		},


		DONORISLE(new int[]{9999, 9999}, new int[]{9999, 9999}, true, true, true, false, false, false) {
			@Override
			public void leave(Player player) {
				player.getInventory().delete(5586, 1);
			//	player.moveTo(new Position(3098, 3489, 0));
				player.getRegionInstance().destruct();
			}
			@Override
			public void logout(Player player) {
			   if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.DONORISLE)) {
				   if (player.getRegionInstance() != null)
					   player.getRegionInstance().destruct();
				}
			}
		},


		CORRUPT_RAID_LOBBY(11836, false, false, false, false, false, true) {

		@Override
		public void process(Player player) {
			if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
				player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
		}

			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().enter(player);

			}
			@Override
			public void leave(Player player) {
			player.getRaidsOne().getRaidsConnector().leaveRaidsOneEntrance();
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					if (player.getPosition().getRegionId() != 13365 && player.getPosition().getRegionId() != 13364 && player.getPosition().getRegionId() != 13363) {

						party.remove(player, false, true);
					}
				}
			}
			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveRaidsOneEntrance();
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
				return true;
			}

			@Override
			public void onDeath(Player player) {
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}

			}
			@Override
			public void enter(Player player) {
				player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
				player.getSkillManager().setCurrentLevel(Skill.HITPOINTS, player.getSkillManager().getMaxLevel(Skill.HITPOINTS), true);
				player.getPacketSender().sendMessage("You have been rejuvenated.");
				player.performGraphic(new Graphic(1310));
				player.performAnimation(new Animation(7270));

				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}

				player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 58000)
						.sendTab(GameSettings.ACHIEVEMENT_TAB);

				int id = 58016;
				for (int i = 58016; i < 58064; i++) {
					id++;
					player.getPacketSender().sendString(id++, "");
					player.getPacketSender().sendString(id++, "");
					player.getPacketSender().sendString(id++, "");
				}
				player.getPacketSender().sendString(58009, "Create");
				player.getPacketSender().sendString(58002, "Raid Party: @whi@0");

			}


		},

		CORRUPT_RAID_ROOM_1(13365, true, false, false, false, false, true) {

			@Override
			public void process(Player player) {
				if ( player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
					player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
				}

				final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				int totalKills = 0;

				if (party != null) {
					for (Player partyMember : party.getPlayers()) {
						totalKills += partyMember.getMinigameAttributes().getRaidsAttributes().getKillcount();
					}
				}

				//System.out.println(totalKills);
				if (totalKills >= 5) {
					CorruptRaid.sendPhaseTwo(party);
				}
			}


			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.moveTo(new Position(2976, 3879, 0));
				player.getRaidsOne().getRaidsConnector().enter(player);
				player.getPacketSender().sendMessage("@red@You have been moved back to the Lobby since you logged out.");
			}

			@Override
			public void logout(Player player) {
				player.getRaidsOne().getRaidsConnector().leave(true);
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
			}


			@Override
			public void onDeath(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.getRaidsOne().getRaidsConnector().leave(true);
				RaidsParty raidParty = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (raidParty != null) {
					raidParty.remove(player, false, true);
				}
				player.moveTo(new Position(2976, 3879, 0));
				player.getMinigameAttributes().getRaidsAttributes().incrementDeaths();
			}



			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveRaidsOneEntrance();
				RaidsParty partyy = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (partyy != null) {
					partyy.remove(player, false, true);
				}
				return true;
			}


			@Override
			public void enter(Player player) {
				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}
			}



		},

		CORRUPT_RAID_ROOM_2(13364, true, false, false, false, false, false) {

			@Override
			public void process(Player player) {
				if ( player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
					player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
				}

				final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				int totalKills = 0;

				if (party != null) {
					for (Player partyMember : party.getPlayers()) {
						totalKills += partyMember.getMinigameAttributes().getRaidsAttributes().getKillcount();
					}
				}

				if (totalKills >= 10) {
					CorruptRaid.sendPhaseThree(party);
				}
			}

			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.moveTo(new Position(2976, 3879, 0));
				player.getRaidsOne().getRaidsConnector().enter(player);
				player.getPacketSender().sendMessage("@red@You have been moved back to the Lobby since you logged out.");
			}

			@Override
			public void logout(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.getRaidsOne().getRaidsConnector().leave(true);
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
			}

			@Override
			public void onDeath(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.getRaidsOne().getRaidsConnector().leave(true);
				RaidsParty raidParty = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (raidParty != null) {
					raidParty.remove(player, false, true);
				}
				player.moveTo(new Position(2976, 3879, 0));
				player.getMinigameAttributes().getRaidsAttributes().incrementDeaths();
			}



			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveRaidsOneEntrance();

				RaidsParty partyy = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (partyy != null) {
					partyy.remove(player, false, true);
				}
				return true;
			}


			@Override
			public void enter(Player player) {
				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}
			}
		},

		CORRUPT_RAID_ROOM_3(13363, true, false, false, false, false, true) {

			@Override
			public void process(Player player) {
				if ( player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
					player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
				}

				final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				int totalKills = 0;

				if (party != null) {
					for (Player partyMember : party.getPlayers()) {
						totalKills += partyMember.getMinigameAttributes().getRaidsAttributes().getKillcount();
					}
				}

				if (totalKills >= 15) {
					CorruptRaid.exitRaidsOne(player);
				}
			}

			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.moveTo(new Position(2976, 3879, 0));
				player.getRaidsOne().getRaidsConnector().enter(player);
				player.getPacketSender().sendMessage("@red@You have been moved back to the Lobby since you logged out.");
			}

			@Override
			public void logout(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.getRaidsOne().getRaidsConnector().leave(true);
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
			}


			@Override
			public void onDeath(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
				player.getRaidsOne().getRaidsConnector().leave(true);
				RaidsParty raidParty = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (raidParty != null) {
					raidParty.remove(player, false, true);
				}
				player.moveTo(new Position(2976, 3879, 0));
				player.getMinigameAttributes().getRaidsAttributes().incrementDeaths();
			}



			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveRaidsOneEntrance();

				RaidsParty partyy = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (partyy != null) {
					partyy.remove(player, false, true);
				}
				return true;
			}

			@Override
			public void enter(Player player) {
				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}
			}

		},

		VOID_RAID_LOBBY(10059, false, false, false, false, false, true) {

			@Override
			public void process(Player player) {
				if (player.getMinigameAttributes().getRaidsAttributes().getParty() != null)
					player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
			}

			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().enter(player);

			}
			@Override
			public void leave(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveVoidEntrance();
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					if (player.getPosition().getRegionId() != 9800 && player.getPosition().getRegionId() != 10312 && player.getPosition().getRegionId() != 10824) {
						party.remove(player, false, true);
					}
				}
			}
			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveVoidEntrance();
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
				return true;
			}

			@Override
			public void onDeath(Player player) {
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}

			}
			@Override
			public void enter(Player player) {
				player.getSkillManager().setCurrentLevel(Skill.PRAYER, player.getSkillManager().getMaxLevel(Skill.PRAYER), true);
				player.getSkillManager().setCurrentLevel(Skill.HITPOINTS, player.getSkillManager().getMaxLevel(Skill.HITPOINTS), true);
				player.getPacketSender().sendMessage("You have been rejuvenated.");
				player.performGraphic(new Graphic(1310));
				player.performAnimation(new Animation(7270));

				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}

				player.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 58000)
						.sendTab(GameSettings.ACHIEVEMENT_TAB);

				int id = 58016;
				for (int i = 58016; i < 58064; i++) {
					id++;
					player.getPacketSender().sendString(id++, "");
					player.getPacketSender().sendString(id++, "");
					player.getPacketSender().sendString(id++, "");
				}
				player.getPacketSender().sendString(58009, "Create");
				player.getPacketSender().sendString(58002, "Raid Party: @whi@0");

			}


		},

		VOID_RAID_ROOM_1(9800, true, false, false, false, false, true) {

			@Override
			public void process(Player player) {
				if ( player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
					player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
				}

				final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				int totalKills = 0;

				if (party != null) {
					for (Player partyMember : party.getPlayers()) {
						totalKills += partyMember.getMinigameAttributes().getRaidsAttributes().getKillcount();
					}
				}

				//System.out.println(totalKills);
				if (totalKills >= 5) {
					VoidRaid.sendPhaseTwo(party);
				}
			}


			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.moveTo(new Position(2521, 4833, 0));
				player.getRaidsOne().getRaidsConnector().enter(player);
				player.getPacketSender().sendMessage("@red@You have been moved back to the Lobby since you logged out.");
			}

			@Override
			public void logout(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveVoid(true);
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
			}


			@Override
			public void onDeath(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.getRaidsOne().getRaidsConnector().leaveVoid(true);
				RaidsParty raidParty = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (raidParty != null) {
					raidParty.remove(player, false, true);
				}
				player.moveTo(new Position(2521, 4833, 0));
				player.getMinigameAttributes().getRaidsAttributes().incrementDeaths();
			}



			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveVoidEntrance();
				RaidsParty partyy = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (partyy != null) {
					partyy.remove(player, false, true);
				}
				return true;
			}


			@Override
			public void enter(Player player) {
				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}
			}



		},

		VOID_RAID_ROOM_2(10312, true, false, false, false, false, false) {

			@Override
			public void process(Player player) {
				if ( player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
					player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
				}

				final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				int totalKills = 0;

				if (party != null) {
					for (Player partyMember : party.getPlayers()) {
						totalKills += partyMember.getMinigameAttributes().getRaidsAttributes().getKillcount();
					}
				}

				if (totalKills >= 10) {
					VoidRaid.sendPhaseThree(party);
				}
			}

			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.moveTo(new Position(2521, 4833, 0));
				player.getRaidsOne().getRaidsConnector().enter(player);
				player.getPacketSender().sendMessage("@red@You have been moved back to the Lobby since you logged out.");
			}

			@Override
			public void logout(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.getRaidsOne().getRaidsConnector().leaveVoid(true);
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
			}

			@Override
			public void onDeath(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.getRaidsOne().getRaidsConnector().leaveVoid(true);
				RaidsParty raidParty = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (raidParty != null) {
					raidParty.remove(player, false, true);
				}
				player.moveTo(new Position(2521, 4833, 0));
				player.getMinigameAttributes().getRaidsAttributes().incrementDeaths();
			}



			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveVoidEntrance();

				RaidsParty partyy = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (partyy != null) {
					partyy.remove(player, false, true);
				}
				return true;
			}


			@Override
			public void enter(Player player) {
				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}
			}
		},

		VOID_RAID_ROOM_3(10824, true, false, false, false, false, true) {

			@Override
			public void process(Player player) {
				if ( player.getMinigameAttributes().getRaidsAttributes().getParty() != null) {
					player.getMinigameAttributes().getRaidsAttributes().getParty().refreshInterface();
				}

				final RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				int totalKills = 0;

				if (party != null) {
					for (Player partyMember : party.getPlayers()) {
						totalKills += partyMember.getMinigameAttributes().getRaidsAttributes().getKillcount();
					}
				}

				if (totalKills >= 15) {
					VoidRaid.exitRaidsOne(player);
				}
			}

			@Override
			public void login(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.moveTo(new Position(2521, 4833, 0));
				player.getRaidsOne().getRaidsConnector().enter(player);
				player.getPacketSender().sendMessage("@red@You have been moved back to the Lobby since you logged out.");
			}

			@Override
			public void logout(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.getRaidsOne().getRaidsConnector().leaveVoid(true);
				RaidsParty party = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (party != null) {
					party.remove(player, false, true);
				}
			}


			@Override
			public void onDeath(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
				player.getRaidsOne().getRaidsConnector().leaveVoid(true);
				RaidsParty raidParty = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (raidParty != null) {
					raidParty.remove(player, false, true);
				}
				player.moveTo(new Position(2521, 4833, 0));
				player.getMinigameAttributes().getRaidsAttributes().incrementDeaths();
			}



			@Override
			public boolean canTeleport(Player player) {
				player.getRaidsOne().getRaidsConnector().leaveVoidEntrance();

				RaidsParty partyy = player.getMinigameAttributes().getRaidsAttributes().getParty();
				if (partyy != null) {
					partyy.remove(player, false, true);
				}
				return true;
			}

			@Override
			public void enter(Player player) {
				if(player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null) {
					player.getSummoning().moveFollower(false);
				}
			}

		},

		INSTANCE1(new int[]{2250, 2269}, new int[]{3800, 3818}, true, true, true, false, false, false) {
			@Override
			public void logout(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
					World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE1, player.getIndex() * 4, player));
				}
			}

			@Override
			public void leave(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
					World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE1, player.getIndex() * 4, player));
				}
				player.getLastRunRecovery().reset();
			}

			@Override
			public void login(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
					World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE1, player.getIndex() * 4, player));
				}
				player.moveTo(3167 +- Misc.random(0,3), 3544+- Misc.random(0,3), 0);
			}

			@Override
			public void onDeath(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
					World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE1, player.getIndex() * 4, player));
				}
			}
		},

		INSTANCE2(new int[]{3594, 3611}, new int[]{3224, 3242}, true, true, true, false, false, false) {
			@Override
			public void logout(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
				}
				World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE2, player.getIndex() * 4, player));
				player.moveTo(3167 +- Misc.random(0,3), 3544+- Misc.random(0,3), 0);
			}

			@Override
			public void leave(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
				}
				World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE2, player.getIndex() * 4, player));
				player.getLastRunRecovery().reset();
			}

			@Override
			public void login(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
				}
				World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE2, player.getIndex() * 4, player));
				player.moveTo(3167 +- Misc.random(0,3), 3544+- Misc.random(0,3), 0);
			}

			@Override
			public void onDeath(Player player) {
				if (player.getRegionInstance() != null && player.getRegionInstance().equals(RegionInstance.RegionInstanceType.INSTANCE)) {
					player.getInstanceManager().onLogout();
				}
				World.getNpcs().forEach(n -> n.removeInstancedNpcs(Location.INSTANCE2, player.getIndex() * 4, player));
			}
		},

		DUNGEONEERING(new int[]{9999, 9999}, new int[]{9999, 9999}, true, false, true, false, true, false) {
			@Override
			public void login(Player player) {
				player.getPacketSender().sendDungeoneeringTabIcon(true).sendTabInterface(GameSettings.PLAYER_TAB, 27224).sendTab(GameSettings.PLAYER_TAB);

				DungeoneeringParty.clearInterface(player);
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.INVITE) {
					player.getPacketSender().sendInteractionOption("Invite", 2, true);
				}
			}

			@Override
			public void leave(Player player) {
				com.ruse.world.content.minigames.impl.dungeoneering.Dungeoneering.Companion.leaveLobby(player);
			}

			@Override
			public void enter(Player player) {
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.INVITE) {
					player.getPacketSender().sendInteractionOption("Invite", 2, true);
				}

				if (player.getMinigameAttributes().getDungeoneeringAttributes().getParty() == null) {
					player.getPacketSender().sendDungeoneeringTabIcon(true).sendTabInterface(GameSettings.PLAYER_TAB, 27224).sendTab(GameSettings.PLAYER_TAB);
					DungeoneeringParty.clearInterface(player);
				}
			}


		},
		DUNGEONEERING_ROOM(new int[]{2240, 2303}, new int[]{4992, 5027}, true, false, true, false, true, false) {},

		JAIL(10129, false, false, false, false, false, true) {
			@Override
			public boolean canTeleport(Player player) {
				if (player.getRights().isStaff()) {
					player.getPacketSender().sendMessage("Staff can leave at any time.");
					return true;
				}
				return !Jail.isJailed(player.getUsername());
			}
		},

		GAMBLE(11817, false, false, false, false, false, true) {
			@Override
			public void enter(Player player) {
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.GAMBLE_WITH) {
					player.getPacketSender().sendInteractionOption("Gamble with", 6, false);
				}
			}

			@Override
			public void leave(Player player) {
				player.getPacketSender().sendInteractionOption("null", 6, false);
			}
		},

		WILDERNESS(new int[] { 9999, 9999 }, new int[] { 9999, 9999 }, true, true, false, false, false, false) {

		},

		BARROWS(new int[] { 3520, 3598, 3543, 3584, 3543, 3560 }, new int[] { 9653, 9750, 3265, 3314, 9685, 9702 }, false, true, true, true, true, true) {
			@Override
			public void process(Player player) {
				if (player.getWalkableInterfaceId() != 37200)
					player.getPacketSender().sendWalkableInterface(37200, true);


			}

			@Override
			public boolean canTeleport(Player player) {
				return true;
			}

			@Override
			public void leave(Player player) {
				player.getPacketSender().sendWalkableInterface(37200, false);
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {
				Barrows.killBarrowsNpc(killer, npc, true);
				return true;
			}
		},

		THE_SIX(new int[] { 2376, 2395 }, new int[] { 4711, 4731 }, false, true, true, true, true, true) {
			@Override
			public void process(Player player) {
				if (player.getWalkableInterfaceId() != 37200)
					player.getPacketSender().sendWalkableInterface(37200, true);
			}

			@Override
			public boolean canTeleport(Player player) {
				return true;
			}

			@Override
			public void leave(Player player) {
				if (!player.doingClanBarrows()) {
					if (player.getRegionInstance() != null) {
						player.getRegionInstance().destruct();
					}
					new TheSix(player).leave(false);
				} else if (player.getCurrentClanChat() != null && player.getCurrentClanChat().doingClanBarrows()) {
					new TheSix(player).leave(false);
				}
			}

			@Override
			public boolean handleKilledNPC(Player killer, NPC npc) {
				boolean respawn = false;

				if (!killer.doingClanBarrows()) {
					Barrows.killBarrowsNpc(killer, npc, true);
					if (new TheSix(killer).allKilled()) {
						respawn = true;
					}
				} else if (killer.getCurrentClanChat() != null && killer.getCurrentClanChat().doingClanBarrows()) {
					for (Player p : killer.getCurrentClanChat().getMembers()) {
						if (p == null || !p.doingClanBarrows()) {
							continue;
						}
						Barrows.killBarrowsNpc(p, npc, true);
						if (new TheSix(p).allKilled()) {
							respawn = true;
						}
					}
				}

				if (respawn) {
					new TheSix(killer).spawn(killer.doingClanBarrows());
				}

				return true;
			}
		},

		DUEL_ARENA(new int[] { 9999, 9999, 9999, 9999, 9999, 9999 }, new int[] { 9999, 9999, 9999, 9999, 9999, 9999 }, false, false, false, false, false, false) {
			@Override
			public void process(Player player) {
				if (player.getWalkableInterfaceId() != 201)
					player.getPacketSender().sendWalkableInterface(201, true);
				if (player.getDueling().duelingStatus == 0) {
					if (player.getPlayerInteractingOption() != PlayerInteractingOption.CHALLENGE)
						player.getPacketSender().sendInteractionOption("Challenge", 2, false);

				} else if (player.getPlayerInteractingOption() != PlayerInteractingOption.ATTACK)
					player.getPacketSender().sendInteractionOption("Attack", 2, true);
			}

			@Override
			public void enter(Player player) {
				PLAYERS_IN_DUEL_ARENA++;
				player.getPacketSender().sendMessage(
						"<img=5> <col=996633>Warning! Do not stake items which you are not willing to lose.");
			}

			@Override
			public boolean canTeleport(Player player) {
				if (player.getDueling().duelingStatus == 5) {
					player.getPacketSender().sendMessage("To forfiet a duel, run to the west and use the trapdoor.");
					return false;
				}
				return true;
			}

			@Override
			public void logout(Player player) {
				boolean dc = false;
				if (player.getDueling().inDuelScreen && player.getDueling().duelingStatus != 5) {
					player.getDueling().declineDuel(player.getDueling().duelingWith > 0 ? true : false);
				} else if (player.getDueling().duelingStatus == 5) {
					if (player.getDueling().duelingWith > -1) {
						Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
						if (duelEnemy != null) {
							duelEnemy.getDueling().duelVictory();
						} else {
							dc = true;
						}
					}
				}
				player.moveTo(new Position(3368, 3268));
				if (dc) {
					World.removePlayer(player);
				}
			}

			@Override
			public void leave(Player player) {
				if (player.getDueling().duelingStatus == 5) {
					onDeath(player);
				}
				PLAYERS_IN_DUEL_ARENA--;
			}

			@Override
			public void onDeath(Player player) {
				if (player.getDueling().duelingStatus == 5) {
					if (player.getDueling().duelingWith > -1) {
						Player duelEnemy = World.getPlayers().get(player.getDueling().duelingWith);
						if (duelEnemy != null) {
							duelEnemy.getDueling().duelVictory();
							duelEnemy.getPacketSender().sendMessage("You won the duel! Congratulations!");
						}
					}
					PlayerLogs.log(player.getUsername(), "Has lost their duel.");
					player.getPacketSender().sendMessage("You have lost the duel.");
					player.getDueling().arenaStats[1]++;
					player.getDueling().reset();
				}
				player.moveTo(new Position(3368 + Misc.getRandom(5), 3267 + Misc.getRandom(3)));
				player.getDueling().reset();
			}

			@Override
			public boolean canAttack(Player player, Player target) {
				if (target.getIndex() != player.getDueling().duelingWith) {
					player.getPacketSender().sendMessage("That player is not your opponent!");
					return false;
				}
				if (player.getDueling().timer != -1) {
					player.getPacketSender().sendMessage("You cannot attack yet!");
					return false;
				}
				return player.getDueling().duelingStatus == 5 && target.getDueling().duelingStatus == 5;
			}
		},
		DEFAULT(null, null, false, true, true, true, true, true) {
		};

		Location(int[] x, int[] y, boolean multi, boolean summonAllowed, boolean followingAllowed,
				 boolean cannonAllowed, boolean firemakingAllowed, boolean aidingAllowed) {
			this.regionId = -1;
			this.x = x;
			this.y = y;
			this.multi = multi;
			this.summonAllowed = summonAllowed;
			this.followingAllowed = followingAllowed;
			this.cannonAllowed = cannonAllowed;
			this.firemakingAllowed = firemakingAllowed;
			this.aidingAllowed = aidingAllowed;
		}

		Location(int regionId, boolean multi, boolean summonAllowed, boolean followingAllowed,
				 boolean cannonAllowed, boolean firemakingAllowed, boolean aidingAllowed) {
			this.regionId = regionId;
			this.x = new int[] {0, 0};
			this.y = new int[] {0, 0};
			this.multi = multi;
			this.summonAllowed = summonAllowed;
			this.followingAllowed = followingAllowed;
			this.cannonAllowed = cannonAllowed;
			this.firemakingAllowed = firemakingAllowed;
			this.aidingAllowed = aidingAllowed;
		}

		private final int[] x, y;
		public final int regionId;
		private final boolean multi;
		private final boolean summonAllowed;
		private final boolean followingAllowed;
		private final boolean cannonAllowed;
		private final boolean firemakingAllowed;
		private final boolean aidingAllowed;

		public int getRegionId() {
			return regionId;
		}

		public int[] getX() {
			return x;
		}

		public int[] getY() {
			return y;
		}

		public static boolean inMulti(Character gc) {
			if (gc.isForceMultiArea()) {
				return true;
			}
			MapInstance mapInstance = gc.getMapInstance();
			if (mapInstance != null && mapInstance.isMultiArea(gc)) {
				return true;
			}

            return gc.getLocation().multi;
        }

		public boolean isSummoningAllowed() {
			return summonAllowed;
		}

		public boolean isFollowingAllowed() {
			return followingAllowed;
		}

		public boolean isCannonAllowed() {
			return cannonAllowed;
		}

		public boolean isFiremakingAllowed() {
			return firemakingAllowed;
		}

		public boolean isAidingAllowed() {
			return aidingAllowed;
		}

		public static Location getLocation(Entity gc) {
			for (Location location : Location.values()) {
				if (location != Location.DEFAULT) {
					if (inLocation(gc, location)) {
						return location;
					}
				}
			}
			return Location.DEFAULT;
		}

		public static boolean inLocation(int regionId, Location location) {
			return regionId == location.getRegionId();
		}

		public static boolean inLocation(Entity gc, Location location) {
			if (location == Location.DEFAULT) {
				return getLocation(gc) == Location.DEFAULT;
			}

			if (location.regionId > -1) {
				return inLocation(gc.getPosition().getRegionId(), location);
			}

			return inLocation(gc.getPosition().getX(), gc.getPosition().getY(), location);
		}

		public static boolean inLocation(int absX, int absY, Location location) {
			int checks = location.getX().length - 1;
			for (int i = 0; i <= checks; i += 2) {
				if (absX >= location.getX()[i] && absX <= location.getX()[i + 1]) {
					if (absY >= location.getY()[i] && absY <= location.getY()[i + 1]) {
						return true;
					}
				}
			}
			return false;
		}

		public void process(Player player) {

		}

		public boolean canTeleport(Player player) {
			return true;
		}

		public void login(Player player) {

		}

		public void enter(Player player) {

		}

		public void leave(Player player) {

		}

		public void logout(Player player) {

		}

		public void onDeath(Player player) {

		}

		public boolean handleKilledNPC(Player killer, NPC npc) {
			return false;
		}

		public boolean canAttack(Player player, Player target) {
			return false;
		}

		/**
		 * SHOULD AN ENTITY FOLLOW ANOTHER ENTITY NO MATTER THE DISTANCE BETWEEN THEM?
		 **/
		public static boolean ignoreFollowDistance(Character character) {
			Location location = character.getLocation();
			return location == Location.DUEL_ARENA;
		}
	}

	public static void process(Character gc) {
		Location newLocation = Location.getLocation(gc);
		if (gc.getLocation() == newLocation) {
			if (gc.isPlayer()) {
				Player player = (Player) gc;
				gc.getLocation().process(player);
				if (Location.inMulti(player)) {
					if (player.getMultiIcon() != 1)
						player.getPacketSender().sendMultiIcon(1);
				} else if (player.getMultiIcon() == 1)
					player.getPacketSender().sendMultiIcon(0);
			}
		} else {
			Location prev = gc.getLocation();
			if (gc.isPlayer()) {
				Player player = (Player) gc;
				if (player.getMultiIcon() > 0)
					player.getPacketSender().sendMultiIcon(0);
				if (player.getWalkableInterfaceId() > 0 && player.getWalkableInterfaceId() != 37400
						&& player.getWalkableInterfaceId() != 50000)
					player.getPacketSender().sendWalkableInterface(50000, false);
				if (player.getPlayerInteractingOption() != PlayerInteractingOption.NONE)
					player.getPacketSender().sendInteractionOption("null", 2, true);
			}

			gc.setLocation(newLocation);
			if (gc.isPlayer()) {
					prev.leave(((Player) gc));
				}
				gc.getLocation().enter(((Player) gc));
			}
		}

	public static boolean goodDistance(Position start, int startSize, Position target, int targetSize, int distance) {
		if (target == null) {
			return false;
		}
		if (start.getZ() != target.getZ()) {
			return false;
		}
		int deltaX1 = start.getX() - (target.getX() + targetSize - 1) - distance;
		int deltaX2 = start.getX() + startSize - 1 - target.getX() + distance;
		int deltaY1 = start.getY() + startSize - 1 - target.getY() + distance;
		int deltaY2 = start.getY() - (target.getY() + targetSize - 1) - distance;

		boolean correctX = !(deltaX1 > 0) && !(deltaX2 < 0);
		boolean correctY = !(deltaY1 < 0) && !(deltaY2 > 0);
		return correctX && correctY;
	}
	public static boolean goodDistance(int objectX, int objectY, int playerX, int playerY, int distance) {
		if (playerX == objectX && playerY == objectY)
			return true;
		for (int i = 0; i <= distance; i++) {
			for (int j = 0; j <= distance; j++) {
				if ((objectX + i) == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if ((objectX - i) == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				} else if (objectX == playerX
						&& ((objectY + j) == playerY || (objectY - j) == playerY || objectY == playerY)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean goodDistance(Position pos1, Position pos2, int distanceReq) {
        return pos1.getZ() == pos2.getZ() && goodDistance(pos1.getX(), pos1.getY(), pos2.getX(), pos2.getY(), distanceReq);
    }

	public static boolean goodDistance(Character entity, Character entity2, int distance) {
		return goodDistance(entity.getPosition(), entity.getSize(), entity2.getPosition(), entity2.getSize(), distance);
	}


	public static int distanceTo(Position position, Position destination, int size) {
		int x = position.getX();
		int y = position.getY();
		int otherX = destination.getX();
		int otherY = destination.getY();
		int distX, distY;
		if (x < otherX)
			distX = otherX - x;
		else if (x > otherX + size)
			distX = x - (otherX + size);
		else
			distX = 0;
		if (y < otherY)
			distY = otherY - y;
		else if (y > otherY + size)
			distY = y - (otherY + size);
		else
			distY = 0;
		if (distX == distY)
			return distX + 1;
		return distX > distY ? distX : distY;
	}
}
