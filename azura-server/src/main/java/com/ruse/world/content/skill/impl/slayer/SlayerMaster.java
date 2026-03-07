package com.ruse.world.content.skill.impl.slayer;

import com.ruse.model.Position;
import com.ruse.model.Skill;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.entity.impl.player.Player;

public enum SlayerMaster {
	BEGINNER_SLAYER(1, 1597, new Position(2853, 9374)),

	MEDIUM_SLAYER(75, 8275, new Position(2174, 5016)),


	ELITE_SLAYER(95, 9085, new Position(2019, 5009)),

	CORRUPT_SLAYER(110, 300, new Position(2667, 4015)),

	SPECTRAL_SLAYER(115, 300, new Position(2667, 4015)),


	BEAST_MASTER(100, 215, new Position(2667, 4015)),

	BEAST_MASTER_X(100, 301, new Position(2667, 4015)),

	SPECTRAL_BEAST(100, 455, new Position(2667, 4015)),

	ENCHANTED_MASTER(1, 931, new Position(2690, 3840)),




	;
	private SlayerMaster(int slayerReq, int npcId, Position telePosition) {
		this.slayerReq = slayerReq;
		this.npcId = npcId;
		this.position = telePosition;
	}

	private int slayerReq;
	private int npcId;
	private Position position;

	public static SlayerMaster forNpcId(int id) {
		for (SlayerMaster master : SlayerMaster.values()) {
			if (master.npcId == id) {
				return master;
			}
		}
		return BEGINNER_SLAYER;
	}

	public int getSlayerReq() {
		return this.slayerReq;
	}

	public int getNpcId() {
		return this.npcId;
	}

	public Position getPosition() {
		return this.position;
	}
	public String getSlayerMasterName() {
		String name = "";
		NpcDefinition def = NpcDefinition.forId(getNpcId());
		if(def != null && def.getName() != null) {
			name = def.getName();
		}
		return name;
	}
	public static SlayerMaster forId(int id) {
		for (SlayerMaster master : SlayerMaster.values()) {
			if (master.ordinal() == id) {
				return master;
			}
		}
		return null;
	}

	public static void changeSlayerMaster(Player player, SlayerMaster master) {
		player.getPacketSender().sendInterfaceRemoval();

		int level = master.getSlayerReq();

		String masterName = "";
		if(master == SlayerMaster.MEDIUM_SLAYER) {
			masterName = "Medium";
		} else if(master == SlayerMaster.CORRUPT_SLAYER) {
			masterName = "Corrupt";
		} else if(master == SlayerMaster.SPECTRAL_SLAYER) {
			masterName = "Spectral";
		}else if(master == SlayerMaster.ELITE_SLAYER) {
			masterName = "Elite";
		} else if(master == SlayerMaster.BEGINNER_SLAYER) {
			masterName = "Beginner";
		} else if(master == SlayerMaster.BEAST_MASTER || master == SlayerMaster.BEAST_MASTER_X || master == SlayerMaster.SPECTRAL_BEAST) {
			masterName = "Beast Hunter";
		}

		if (player.getSkillManager().getCurrentLevel(Skill.SLAYER) < level) {
			player.getPacketSender().sendMessage("You need a Slayer level of at least " + master.getSlayerReq() + " to use " + masterName + ".");
			player.getPacketSender().sendInterfaceRemoval();
			return;
		}

		if(player.getSlayer().getSlayerMaster() != master) {
			player.getPacketSender().sendMessage("You have successfully changed Slayer master.");
		}
		player.getSlayer().setSlayerMaster(master);
		PlayerPanel.refreshPanel(player);

	}
}
