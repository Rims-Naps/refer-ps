package com.ruse.model;

import com.ruse.GameSettings;
import com.ruse.world.World;
import com.ruse.world.content.minigames.impl.Barrows;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Handles a custom region instance for a player
 * 
 * @author Gabriel
 */
public class RegionInstance {

	public enum RegionInstanceType {
		HALLS_OF_VALOR, BARROWS, THE_SIX, GRAVEYARD, RAID1, FIGHT_CAVE, WARRIORS_GUILD, NOMAD, RECIPE_FOR_DISASTER, CONSTRUCTION_HOUSE,
		CONSTRUCTION_DUNGEON, TRIO, KRAKEN, CORPOREAL_BEAST, KRIL_TSUTSAROTH, GENERAL_GRAARDOR, COMMANDER_ZILYANA,
		KREE_ARRA, ETERNAL, TORMENTED_DEMON, KING_BLACK_DRAGON, CHAOS_ELEMENTAL, SLASH_BASH, FLOREOX_BIRD, ARCADE1, ARCADE2, ARCADE3, ARCADE4, ARCADE5, ARCADE6,
		BANDOS_AVATAR, DAG_PRIME, DAG_SUPREME, DAG_REX, CALLISTO, VETION, VENENATIS, GALVEK, REV_TARRAGON, NEX, KINGS,
		ZULRAH, ZOMBIE, INSTANCE, DUNGEONEERING, DARKNESS_WITHIN, GAUNTLET, DONORISLE, IZONE, XMASBOSS, CORRUPT_RAID_1, CORRUPT_RAID_2, CORRUPT_RAID_3, SLAYERTASK,
		WAVE_MINIGAME , VOID_RAID_1, VOID_RAID_2, VOID_RAID_3,

		EXECUTIVERAID , GODLYRAID, CELESTIALRAID, DIVINERAID, ENLIGHTENEDRAID, GALACTICRAID, UNIVERSALRAID, SUPERMAN, HULK
	}

	private Player owner;
	private RegionInstanceType type;
	private CopyOnWriteArrayList<NPC> npcsList;
	private CopyOnWriteArrayList<Player> playersList;


	public RegionInstance(Player p, RegionInstanceType type) {
		this.owner = p;
		this.type = type;
		this.npcsList = new CopyOnWriteArrayList<>();
		if (type == RegionInstanceType.CONSTRUCTION_HOUSE || type == RegionInstanceType.THE_SIX) {
			this.playersList = new CopyOnWriteArrayList<>();
		}
	}






	public void destruct() {
		for (NPC n : npcsList) {
			if (n != null) { //&& n.getConstitution() > 0 && World.getNpcs().get(n.getIndex()) != null && !n.isDying()) {
			//	// System.out.println("okkk");
				if (type == RegionInstanceType.WARRIORS_GUILD) {

					if (n.getId() >= 4278 && n.getId() <= 4284) {
						owner.getMinigameAttributes().getWarriorsGuildAttributes().setSpawnedArmour(false);
					}

				} else if (type == RegionInstanceType.BARROWS) {

					if (n.getId() >= 2024 && n.getId() <= 2034) {
						Barrows.killBarrowsNpc(owner, n, false);
					}

			//	} else if (type == RegionInstanceType.HALLS_OF_VALOR) {
				//	owner.hov.killBarrowsNpc(n, false);
				}
				World.deregister(n);
				// // System.out.println("Is this running?");
			}
		}

		npcsList.clear();
		owner.setRegionInstance(null);
		// // System.out.println("Is this ru222nning?");
	}


	public void onLogout(Player player) {
		if (player != owner)
			return;
		player.getPA().sendMessage("You have been kicked from instance");
		player.getRegionInstance().destruct();
		//player.getInstanceManager().selectedInstance = (null);
		player.setCurrentInstanceAmount(-1);
		player.setCurrentInstanceNpcId(-1);
		player.setCurrentInstanceNpcName("");
		player.moveTo(GameSettings.HOME_CORDS);
		npcsList.clear();


	}

	public void add(Character c) {
		if (type == RegionInstanceType.CONSTRUCTION_HOUSE) {
			if (c.isPlayer()) {
				playersList.add((Player) c);
			} else if (c.isNpc()) {
				npcsList.add((NPC) c);
			}

			if (c.getRegionInstance() == null || c.getRegionInstance() != this) {
				c.setRegionInstance(this);
			}
		}
	}

	public void remove(Character c) {
		if (type == RegionInstanceType.CONSTRUCTION_HOUSE) {
			if (c.isPlayer()) {
				playersList.remove((Player) c);
				if (owner == ((Player) c)) {
					destruct();
				}
			} else if (c.isNpc()) {
				npcsList.remove((NPC) c);
			}

			if (c.getRegionInstance() != null && c.getRegionInstance() == this) {
				c.setRegionInstance(null);
			}
		}
	}

	public interface InstanceCreatedCallback {
		void onInstanceCreated(RegionInstance instance);
	}

	private InstanceCreatedCallback instanceCreatedCallback;

	public void setInstanceCreatedCallback(InstanceCreatedCallback callback) {
		this.instanceCreatedCallback = callback;
	}

	// Call this method when the instance is fully created
	private void notifyInstanceCreated() {
		if (instanceCreatedCallback != null) {
			instanceCreatedCallback.onInstanceCreated(this);
		}
	}



	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public RegionInstanceType getType() {
		return type;
	}

	public CopyOnWriteArrayList<NPC> getNpcsList() {
		return npcsList;
	}

	public CopyOnWriteArrayList<Player> getPlayersList() {
		return playersList;
	}

	public boolean npcIsRegisteredOnInstance(int id) {
		for (NPC n : getNpcsList()) {
			if (n != null && n.getId() == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object other) {
		return (RegionInstanceType) other == type;
	}
	public void spawnNPC(NPC npc) {
		//System.out.println("spawning NPCID="+npc.getId()+" from = "+new Throwable().getStackTrace()[1].toString());
		World.register(npc);
		getNpcsList().add(npc);
	}
}
