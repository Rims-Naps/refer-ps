package com.ruse.world.content.skill.impl.summoning;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.FamiliarSpawnTask;
import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Locations.Location;
import com.ruse.model.Position;
import com.ruse.model.Skill;
import com.ruse.model.container.impl.BeastOfBurden;
import com.ruse.model.movement.MovementQueue;
import com.ruse.world.World;
import com.ruse.world.content.Pets.SmnBuilder;
import com.ruse.world.content.skill.impl.summoning.BossPets.BossPet;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;



public class Summoning {

	Player player;

	public Summoning(Player p) {

		this.player = p;
	}

	public void summon(final FamiliarData familiar, boolean renew, boolean login) {
		if(familiar == null)
			return;
		if(!player.getLocation().isSummoningAllowed()) {
			player.getPacketSender().sendMessage("You cannot summon familiars here.");
			return;
		}
		if(!login && !player.getLastSummon().elapsed(1000))
			return;
		if(getFamiliar() != null && !renew && !login) {
			player.getPacketSender().sendMessage("You already have a familiar.");
			return;
		}
		if (login || player.getSkillManager().getMaxLevel(Skill.NECROMANCY) >= familiar.levelRequired) {

			if(!login) {
				if (player.getSkillManager().getCurrentLevel(Skill.NECROMANCY) >= familiar.summoningPointsRequired) {
					player.getSkillManager().setCurrentLevel(Skill.NECROMANCY, player.getSkillManager().getCurrentLevel(Skill.NECROMANCY) - familiar.summoningPointsRequired);
					player.getInventory().delete(familiar.getPouchId(), 1);
					if(renew && getFamiliar() != null) {
						player.getPacketSender().sendMessage("You have renewed your familiar.");
						getFamiliar().setDeathTimer(SummoningData.getFollowerTimer(familiar.npcId));
						getFamiliar().getSummonNpc().performGraphic(new Graphic(1315));
						getFamiliar().getSummonNpc().setConstitution(getFamiliar().getSummonNpc().getDefaultConstitution());
						return;
					}
				} else {
					player.getPacketSender().sendMessage("You do not have enough Summoning points to summon this familiar.");
					player.getPacketSender().sendMessage("You can recharge your Summoning points at an obelisk.");
					return;
				}
			}

			int deathTime = login && getFamiliar() != null && getFamiliar().getDeathTimer() > 0 ? getFamiliar().getDeathTimer() : SummoningData.getFollowerTimer(familiar.npcId);

			unsummon(true, false);

			NPC foll = new NPC(familiar.npcId, new Position(player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ()));
			foll.performGraphic(new Graphic(1315));
			foll.setPositionToFace(player.getPosition());
			foll.setSummoningNpc(true);
			foll.setEntityInteraction(player);
			foll.getMovementQueue().setFollowCharacter(player);
			World.register(foll);

			setFamiliar(new Familiar(player, foll, deathTime));
			player.setSummon(SmnBuilder.buildSummon(foll.getId()));


			int store = SummoningData.getStoreAmount(foll.getId());
			if(bob == null || bob.capacity() < store) {
				if(store > 0)
					this.bob = new BeastOfBurden(player, store);
			}
			processFamiliar();

			player.getPacketSender().sendString(54028, ""+familiar.name().replaceAll("_", " "));
			player.getPacketSender().sendString(54045, " "+player.getSkillManager().getCurrentLevel(Skill.NECROMANCY)+"/"+player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
			if(player.getSummoning().getFamiliar() != null) {
				player.getPacketSender().sendNpcHeadOnInterface(player.getSummoning().getFamiliar().getSummonNpc().getId(), 54021); // 60 = invisable head to remove it
			}
			player.getPacketSender().sendString(0, "[SUMMOtrue");

			player.getLastSummon().reset();
		} else {
			player.getPacketSender().sendMessage("You need a Summoning level of at least " + familiar.levelRequired + " to summon this familiar.");
		}
	}


	public void summonPet(BossPet bossPet, boolean login) {
		if(getFamiliar() != null) {

			player.getSummoning().unsummon(true, true);
			player.getPacketSender().sendMessage("You've dismissed your familiar.");
			return;
		}
		if(!login && !player.getInventory().contains(bossPet.getItemId()))
			return;
		if(!player.getLocation().isSummoningAllowed() && !login) {
			player.getPacketSender().sendMessage("You cannot summon familiars here.");
			return;
		}
		if(!login && !player.getLastSummon().elapsed(1000))
			return;

		if(!login) {
			unsummon(true, false);
			player.getInventory().delete(bossPet.getItemId(), 1);
		}

		NPC foll = new NPC(bossPet.getSpawnNpcId(), new Position(player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ()));
		foll.performGraphic(new Graphic(1315));
		foll.setPositionToFace(player.getPosition());
		foll.setSummoningNpc(true);
		foll.setEntityInteraction(player);
		foll.getMovementQueue().setFollowCharacter(player);
		World.register(foll);
		setFamiliar(new Familiar(player, foll));
		processFamiliar();
		player.getPacketSender().sendString(54019, "Boosts:\\n\\n" + bossPet.getBoost());
		player.getPacketSender().sendString(54028, ""+bossPet.name().replaceAll("_", " "));
		player.getPacketSender().sendString(54045, " "+player.getSkillManager().getCurrentLevel(Skill.NECROMANCY)+"/"+player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
		player.getPacketSender().sendString(0, "[SUMMOtrue");
		player.getPacketSender().sendString(54043, "");
		player.getPacketSender().sendNpcOnInterface(54021, bossPet.npcId, bossPet.getZoom() ); // 60 = invisable head to remove it
		//player.getPacketSender().sendInterfaceReset();
		player.setSummon(SmnBuilder.buildSummon(foll.getId()));

	}

	public void unsummon(boolean full, boolean dropItems) {
			World.deregister(getFamiliar().getSummonNpc());
		if(full) {
			setFamiliar(null);
			clearInterface();
			player.getPacketSender().sendString(0, "[SUMMOfalse");
		}
	}

	public void processFamiliar() {
		final NPC n = familiar.getSummonNpc();
		TaskManager.submit(new Task(1, n, true) {
			int clockTimer = 2;
			@Override
			protected void execute() {

				if(familiar == null || n == null || n.getConstitution() <= 0 || !n.isRegistered() || player.getConstitution() <= 0 || !player.isRegistered()) {
					unsummon(true, true);
					stop();
					return;
				}

				boolean underAttack = player.getCombatBuilder().isBeingAttacked()
						&& player.getCombatBuilder().getLastAttacker() != null
						&& player.getCombatBuilder().getLastAttacker().getCombatBuilder().getVictim() != null
						&& player.getCombatBuilder().getLastAttacker().getCombatBuilder().getVictim() == player;
				boolean attacking = player.getCombatBuilder().isAttacking();

				if(!familiar.isPet() && n.getDefinition().isAttackable() && (underAttack || attacking)) {
					if(n.getLocation() != Location.WILDERNESS || Location.inMulti(player)) {
						n.setSummoningCombat(true);
						n.getCombatBuilder().attack(attacking ? player.getCombatBuilder().getVictim() : player.getCombatBuilder().getLastAttacker());

						n.setEntityInteraction(n.getCombatBuilder().getVictim());
					}
				} else {
					if(n.getCombatBuilder().isAttacking()) {
						n.getCombatBuilder().reset(true);
					}
					n.setSummoningCombat(false);
					n.setEntityInteraction(player);
					n.getMovementQueue().setFollowCharacter(player);
				}
			}
		});
	}



	public void store() {
		if(player.busy()) {
			player.getPacketSender().sendMessage("Please finish what you're doing first.");
			return;
		}
		if(getFamiliar() != null && getFamiliar().getSummonNpc() != null && bob != null)
			bob.open();
		else
			player.getPacketSender().sendMessage("You do not have a familiar which can hold items.");
	}

	public void toInventory() {
		if(player == null)
			return;
		if(player.getInventory().getFreeSlots() <= 0) {
			player.getPacketSender().sendMessage("You do not have any free space in your inventory.");
			return;
		}
		if((!player.busy() || player.getInterfaceId() == BeastOfBurden.INTERFACE_ID) && player.getLocation().isSummoningAllowed()) {
			if(getFamiliar() == null || !SummoningData.beastOfBurden(getFamiliar().getSummonNpc().getId()) || bob == null) {
				player.getPacketSender().sendMessage("You do not have a Beast of Burden.");
				return;
			}
			if(bob.getValidItems().size() == 0) {
				player.getPacketSender().sendMessage("Your familiar is not currently holding any items for you.");
				return;
			}
			player.performAnimation(new Animation(827));
			player.setInterfaceId(-BeastOfBurden.INTERFACE_ID);
			bob.moveItems(player.getInventory(), false, true);
			bob.refreshItems();
			player.getPacketSender().sendInterfaceRemoval();
		} else
			player.getPacketSender().sendMessage("You cannot do this right now.");
	}

	public static String getTimer(int seconds) {
		int minuteCounter = 0;
		int secondCounter = 0;
		for(int j = seconds; j > 0; j--) {
			if(secondCounter >= 59) {
				minuteCounter++;
				secondCounter = 0;
			} else
				secondCounter++;
		}
		if(minuteCounter == 0 && secondCounter == 0)
			return "";
		String secondString = ""+secondCounter;
		if(secondCounter < 10)
			secondString = "0"+secondCounter+"";
		return " "+minuteCounter+":"+secondString;
	}

	public void moveFollower(boolean forced) {
		if(getFamiliar() != null && getFamiliar().getSummonNpc() != null) {
			final Position movePos = new Position(player.getPosition().getX(), player.getPosition().getY() + 1, player.getPosition().getZ());
			if(forced || canSpawn(getFamiliar().getSummonNpc(), movePos)) {
				getFamiliar().getSummonNpc().moveTo(movePos);
				getFamiliar().getSummonNpc().performGraphic(new Graphic(1315));
				getFamiliar().getSummonNpc().setLocation(Location.getLocation(getFamiliar().getSummonNpc()));
				player.getLastSummon().reset();
				if(!forced) {
					player.getPacketSender().sendMessage("You've called your familiar.");
				}
			} else {
				getFamiliar().getSummonNpc().moveTo(movePos);
				getFamiliar().getSummonNpc().performGraphic(new Graphic(1315));
				getFamiliar().getSummonNpc().setLocation(Location.getLocation(getFamiliar().getSummonNpc()));
				player.getLastSummon().reset();			}
		}
	}

	public static boolean canSpawn(NPC n, Position pos) {
		return MovementQueue.canWalk(n.getPosition(), pos, n.getSize());
	}

	public void login() {
		clearInterface();
		if(spawnTask != null) {
			TaskManager.submit(spawnTask);
		}
		spawnTask = null;
	}

	public void clearInterface() {
		player.getPacketSender().sendString(54045, "");
		player.getPacketSender().sendString(54043, "");
		player.getPacketSender().sendString(54028, "");
		player.getPacketSender().sendString(54024, "0");
		player.getPacketSender().sendNpcHeadOnInterface(60, 54021); // 60 = invisable head to remove it
		player.getPacketSender().sendString(18045, player.getSkillManager().getMaxLevel(Skill.NECROMANCY) < 10 ? "   "+player.getSkillManager().getCurrentLevel(Skill.NECROMANCY)+"/"+player.getSkillManager().getMaxLevel(Skill.NECROMANCY) :  " "+player.getSkillManager().getCurrentLevel(Skill.NECROMANCY)+"/"+player.getSkillManager().getMaxLevel(Skill.NECROMANCY));
	}

	private FamiliarSpawnTask spawnTask;
	private Familiar familiar;
	private BeastOfBurden bob;

	public FamiliarSpawnTask getSpawnTask() {
		return spawnTask;
	}

	public FamiliarSpawnTask setFamiliarSpawnTask(FamiliarSpawnTask spawnTask) {
		this.spawnTask = spawnTask;
		return this.spawnTask;
	}

	public Familiar getFamiliar() {
		return this.familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public BeastOfBurden getBeastOfBurden() {
		return bob;
	}

	private int[] charmImpConfigs = new int[] {0, 0, 0, 0};

	public void setCharmImpConfig(int index, int config) {
		this.charmImpConfigs[index] = config;
	}

	public void setCharmimpConfig(int[] charmImpConfig) {
		this.charmImpConfigs = charmImpConfig;
	}

	public int getCharmImpConfig(int index) {
		return charmImpConfigs[index];
	}

	public int[] getCharmImpConfigs() {
		return charmImpConfigs;
	}
}
