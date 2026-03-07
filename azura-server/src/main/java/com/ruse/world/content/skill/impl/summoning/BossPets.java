package com.ruse.world.content.skill.impl.summoning;

import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import com.ruse.world.content.skill.impl.summoning.BossPets.BossPet;
import lombok.Getter;

public class BossPets {

	@Getter
	public enum BossPet {
		//NECROMANCY PETS
		//ROW 1
		DEATHWALKER(3151, 3151, 6545,"+1% Maxhit/Droprate"),
		ARROWSHADE(3154, 3154, 6545,"+2% Maxhit/Droprate"),
		BONEMANCER(3153, 3153, 6545,"+3% Maxhit/Droprate"),

		SKELETAL_SERVANT(3700, 3700, 6545,""),
		DEMONIC_SERVANT(3701, 3701, 6545,""),
		OGRE_SERVANT(3702, 3702, 6545,""),
		SPECTRAL_SERVANT(3703, 3703, 6545,""),
		MASTER_SERVANT(3704, 3704, 6545,""),


		//ROW 2
		SHADOWFIEND(3155, 3155, 6545,"+4% Maxhit/Droprate"),
		DEVILSPAWN(3156, 3156, 6545,"+5% Maxhit/Droprate"),
		ABYSSAL_TORMENTOR(3158, 3158, 6545,"+5% Maxhit/Droprate"),

		//ROW 3
		GRUNT_MAULER(3159, 3159, 6545,"+6% Maxhit/Droprate"),
		BRUTE_CRUSHER(3160, 3160, 6545,"+6% Maxhit/Droprate"),
		VINESPLITTER(3161, 3161, 6545,"+7% Maxhit/Droprate"),

		//ROW 3
		PHANTOM_DRIFTER(3162, 3162, 6545,"+8% Maxhit/Droprate"),
		WHISPERING_WRAITH(3163, 3163, 6545,"+9% Maxhit/Droprate"),
		BANSHEE_KING(3164, 3164, 6545,"+10% Maxhit/Droprate"),

		//ROW 3
		EYE_OF_BEYOND(3165, 3165, 6545,"+11% Maxhit/Droprate"),
		TALONWING(3166, 3166, 6545,"+12% Maxhit/Droprate"),
		UMBRAL_BEAST(3167, 3167, 6545,"+13% Maxhit/Droprate"),

		;

		public final int npcId;
		private final int spawnNpcId;
		private final int itemId;
		private boolean isPet;
		private boolean isHealing;
		BossPet(int npcId, int spawnNpcId, int itemId, String boost) {
			this.npcId = npcId;
			this.spawnNpcId = spawnNpcId;
			this.itemId = itemId;
			this.boost = boost;
			this.isPet = true;
			this.isHealing = true;
		}
		BossPet(int npcId, int spawnNpcId, int itemId, int zoom, String boost, boolean isPet, boolean isHealing) {
			this.npcId = npcId;
			this.spawnNpcId = spawnNpcId;
			this.itemId = itemId;
			this.zoom = zoom;
			this.boost = boost;
			this.isPet = isPet;
			this.isHealing = isHealing;
		}

		public int zoom = 800;
		public String boost = "None";

		public static BossPet forId(int itemId) {
			for (BossPet p : BossPet.values()) {
				if (p != null && p.itemId == itemId) {
					return p;
				}
			}
			return null;
		}

		public static BossPet forSpawnId(int spawnNpcId) {
			for (BossPet p : BossPet.values()) {
				if (p != null && p.npcId == spawnNpcId) {
					return p;
				}
			}
			return null;
		}

		public boolean isCanAttack() {
			return isPet;
		}

		public boolean isCanHeal() {
			return isHealing;
		}

		public int getSpawnNpcId() {
			return spawnNpcId;
		}

		public int getItemId() {
			return itemId;
		}
	}


	public static boolean pickup(Player player, NPC npc) {
		BossPet pet = BossPet.forSpawnId(npc.getId());
		if (pet != null) {
			if (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null
					&& player.getSummoning().getFamiliar().getSummonNpc().isRegistered()) {
				if (player.getSummoning().getFamiliar().getSummonNpc().getId() == pet.getSpawnNpcId()
						&& player.getSummoning().getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
					player.getSummoning().unsummon(true, true);
					player.getPacketSender().sendMessage("Your follower has been dismissed");
					player.getPacketSender().sendNpcOnInterface(54021, 60, 1000 ); // 60 = invisable head to remove it
					player.setNecrotimeleft(0);
					return true;

				} else {
					player.getPacketSender().sendMessage("This is not your follower to dismiss.");
				}
			} else {
				player.getPacketSender().sendMessage("This is not your follower to dismiss.");
			}
		}
		return false;
	}
	public static void onLogout(Player player) {
		if (player.getSummoning().getFamiliar() != null && player.getSummoning().getFamiliar().getSummonNpc() != null && player.getSummoning().getFamiliar().getSummonNpc().isRegistered()) {
			pickup(player, player.getSummoning().getFamiliar().getSummonNpc());
		}
	}

}