package com.ruse.world.content.skill.impl.slayer;

import com.ruse.model.Position;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.util.Misc;
import com.ruse.world.entity.impl.player.Player;

import java.util.*;

/**
 * @author LOYAL 1/23/2022
 */

public enum SlayerTasks {

	NO_TASK(null, -1, null, -1, null),
	//BEGINNER SLAYER
	//LEOPARD
	ELEOPARD(SlayerMaster.BEGINNER_SLAYER, 450, "Earth Leopard", 175, new Position(3293, 4108)),
	ELEOPARD2(SlayerMaster.BEGINNER_SLAYER, 450, "Earth Leopard", 175, new Position(3293, 4108, 4)),

	Nythor(SlayerMaster.BEGINNER_SLAYER, 13747, "Nythor", 35, new Position(2138, 4653, 1)),
	FLEOPARD(SlayerMaster.BEGINNER_SLAYER, 4001, "Lava Leopard", 175, new Position(2117, 4774)),
	Kezel(SlayerMaster.BEGINNER_SLAYER, 9815, "Kezel", 47, new Position(2986, 4244)),

	FLEOPARD2(SlayerMaster.BEGINNER_SLAYER, 4001, "Lava Leopard", 175, new Position(2117, 4774, 4)),

	Terran(SlayerMaster.BEGINNER_SLAYER, 1801, "Terran", 38, new Position(3105, 4112)),
	WLEOPARD(SlayerMaster.BEGINNER_SLAYER, 4000, "Aqua Leopard", 175, new Position(2272, 4083)),
	WLEOPARD2(SlayerMaster.BEGINNER_SLAYER, 4000, "Aqua Leopard", 175, new Position(2272, 4083, 4)),

	Ferna(SlayerMaster.BEGINNER_SLAYER, 1802, "Ferna", 41, new Position(2972, 4110)),

	Aqualorn(SlayerMaster.BEGINNER_SLAYER, 9027, "Aqualorn", 40, new Position(2148, 4119)),

	//CAYMAN
	ECAYMAN(SlayerMaster.BEGINNER_SLAYER, 188, "Earth Cayman", 175, new Position(3287, 4124)),
	ECAYMAN2(SlayerMaster.BEGINNER_SLAYER, 188, "Earth Cayman", 175, new Position(3287, 4124, 4)),

	Ignox(SlayerMaster.BEGINNER_SLAYER, 13458, "Ignox", 42, new Position(2142, 4509)),
	FCAYMAN(SlayerMaster.BEGINNER_SLAYER, 606, "Lava Cayman", 175, new Position(2117, 4772)),

	Hydrora(SlayerMaster.BEGINNER_SLAYER, 1741, "Hydrora", 48, new Position(2134, 3739)),
	FCAYMAN2(SlayerMaster.BEGINNER_SLAYER, 606, "Lava Cayman", 175, new Position(2117, 4772, 4)),

	Crystalis(SlayerMaster.BEGINNER_SLAYER, 8006, "Crystalis", 43, new Position(2148, 3985)),
	WCAYMAN(SlayerMaster.BEGINNER_SLAYER, 3004, "Aqua Cayman", 175, new Position(2257, 4068)),
	WCAYMAN2(SlayerMaster.BEGINNER_SLAYER, 3004, "Aqua Cayman", 175, new Position(2257, 4068, 4)),

	Ember(SlayerMaster.BEGINNER_SLAYER, 688, "Ember", 44, new Position(1950, 4444)),

	//HOUNDS
	EARTHHOUND(SlayerMaster.BEGINNER_SLAYER, 3688, "Earth Hound", 175, new Position(3295, 4152)),

	Infernus(SlayerMaster.BEGINNER_SLAYER, 12228, "Infernus", 49, new Position(2730, 3360)),
	EARTHHOUND2(SlayerMaster.BEGINNER_SLAYER, 3688, "Earth Hound", 175, new Position(3295, 4152, 4)),

	Xerces(SlayerMaster.BEGINNER_SLAYER, 350, "Xerces", 45, new Position(3159, 4254)),
	FIREHOUND(SlayerMaster.BEGINNER_SLAYER, 9838, "Lava Hound", 175, new Position(2142, 4761)),
	FIREHOUND2(SlayerMaster.BEGINNER_SLAYER, 9838, "Lava Hound", 175, new Position(2142, 4761, 4)),

	Marina(SlayerMaster.BEGINNER_SLAYER, 182, "Marina", 46, new Position(2091, 3864)),
	WATERHOUND(SlayerMaster.BEGINNER_SLAYER, 6306, "Aqua Hound", 175, new Position(2283, 4062)),

	Tellurion(SlayerMaster.BEGINNER_SLAYER, 9026, "Tellurion", 50, new Position(2525, 2520, 4)),
	WATERHOUND2(SlayerMaster.BEGINNER_SLAYER, 6306, "Aqua Hound", 175, new Position(2283, 4062, 4)),

	//MEDIUM SLAYER
	//WOLF
	EWOLF(SlayerMaster.MEDIUM_SLAYER, 9846, "Earth Wolf", 300, new Position(3346, 4101)),
	EWOLF2(SlayerMaster.MEDIUM_SLAYER, 9846, "Earth Wolf", 300, new Position(3346, 4101, 4)),

	Marinus(SlayerMaster.MEDIUM_SLAYER, 1150, "Marinus", 51, new Position(3753, 3040, 4)),
	FWOLF(SlayerMaster.MEDIUM_SLAYER, 1738, "Lava Wolf", 300, new Position(2166, 4743)),
	FWOLF2(SlayerMaster.MEDIUM_SLAYER, 1738, "Lava Wolf", 300, new Position(2166, 4743, 4)),

	Pyrox(SlayerMaster.MEDIUM_SLAYER, 9837, "Pyrox", 52, new Position(2522, 3224)),

	WWOLF(SlayerMaster.MEDIUM_SLAYER, 1737, "Aqua Wolf", 300, new Position(2283, 4062)),
	WWOLF2(SlayerMaster.MEDIUM_SLAYER, 1737, "Aqua Wolf", 300, new Position(2283, 4062, 4)),

	Astaran(SlayerMaster.MEDIUM_SLAYER, 9002, "Astaran", 53, new Position(2593, 2649)),


	//DEVOURERS
	EDEVOURER(SlayerMaster.MEDIUM_SLAYER, 203, "Earth Devourer", 300, new Position(3342, 4127)),
	EDEVOURER2(SlayerMaster.MEDIUM_SLAYER, 203, "Earth Devourer", 300, new Position(3342, 4127, 4)),

	Volcar(SlayerMaster.MEDIUM_SLAYER, 1821, "Volcar", 55, new Position(2644, 3244, 1)),


	Nereus(SlayerMaster.MEDIUM_SLAYER, 7000, "Nereus", 54, new Position(3875, 2914)),

	FDEVOURER(SlayerMaster.MEDIUM_SLAYER, 603, "Lava Devourer", 300, new Position(2166, 4760)),
	FDEVOURER2(SlayerMaster.MEDIUM_SLAYER, 603, "Lava Devourer", 300, new Position(2166, 4760, 4)),

	Lagoon(SlayerMaster.MEDIUM_SLAYER, 1727, "Lagoon", 60, new Position(2528, 2905)),

	WDEVOURER(SlayerMaster.MEDIUM_SLAYER, 185, "Aqua Devourer", 300, new Position(2264, 4052)),
	WDEVOURER2(SlayerMaster.MEDIUM_SLAYER, 185, "Aqua Devourer", 300, new Position(2264, 4052, 4)),

	Incendia(SlayerMaster.MEDIUM_SLAYER, 1729, "Incendia", 65, new Position(2333, 4441)),


	//CHINS
	EARTHCHIN(SlayerMaster.MEDIUM_SLAYER, 5002, "Earth Chinchompa", 300, new Position(3317, 4127)),
	EARTHCHIN2(SlayerMaster.MEDIUM_SLAYER, 5002, "Earth Chinchompa", 300, new Position(3317, 4127, 4)),

	Terra(SlayerMaster.MEDIUM_SLAYER, 1730, "Terra", 70, new Position(1952, 4891)),
	FIRECHIN(SlayerMaster.MEDIUM_SLAYER, 202, "Lava Chinchompa", 300, new Position(2182, 4772)),
	FIRECHIN2(SlayerMaster.MEDIUM_SLAYER, 202, "Lava Chinchompa", 300, new Position(2182, 4772, 4)),

	Abyss(SlayerMaster.MEDIUM_SLAYER, 1731, "Abyss", 80, new Position(2782, 2773)),
	WATERCHIN(SlayerMaster.MEDIUM_SLAYER, 3005, "Aqua Chinchompa", 300, new Position(2282, 4031)),
	WATERCHIN2(SlayerMaster.MEDIUM_SLAYER, 3005, "Aqua Chinchompa", 300, new Position(2282, 4031, 4)),




	//ELITE SLAYER
	//BEASTS
	EBEAST(SlayerMaster.ELITE_SLAYER, 8010, "Earth Beast", 725, new Position(3354, 4125)),
	EBEAST2(SlayerMaster.ELITE_SLAYER, 8010, "Earth Beast", 725, new Position(3354, 4125, 4)),

	Pyra(SlayerMaster.ELITE_SLAYER, 1735, "Pyra", 85, new Position(1761, 4952)),

	FBEAST(SlayerMaster.ELITE_SLAYER, 928, "Lava Beast", 725, new Position(2203, 4769)),
	FBEAST2(SlayerMaster.ELITE_SLAYER, 928, "Lava Beast", 725, new Position(2203, 4769, 4)),

	Geode(SlayerMaster.ELITE_SLAYER, 5539, "Geode", 90, new Position(1819, 4821)),

	WBEAST(SlayerMaster.ELITE_SLAYER, 352, "Aqua Beast", 725, new Position(2287, 4027)),
	WBEAST2(SlayerMaster.ELITE_SLAYER, 352, "Aqua Beast", 725, new Position(2287, 4027, 4)),

	Cerulean(SlayerMaster.ELITE_SLAYER, 5547, "Cerulean", 95, new Position(1701, 5460)),
	Hydrox(SlayerMaster.ELITE_SLAYER, 1080, "Hydrox", 155, new Position(2464, 5021)),

	//TURTLE
	ETURTLE(SlayerMaster.ELITE_SLAYER, 1739, "Earth Tortoise", 725, new Position(3366, 4130)),
	ETURTLE2(SlayerMaster.ELITE_SLAYER, 1739, "Earth Tortoise", 725, new Position(3366, 4130, 4)),
	Moltron(SlayerMaster.ELITE_SLAYER, 1079, "Moltron", 150, new Position(2719, 5016)),

	Scorch(SlayerMaster.ELITE_SLAYER, 5533, "Scorch", 100, new Position(2335, 4693)),
	FTURTLE(SlayerMaster.ELITE_SLAYER, 201, "Lava Tortoise", 725, new Position(2228, 4781)),

	Geowind(SlayerMaster.ELITE_SLAYER, 5553, "Geowind", 105, new Position(2666, 2783)),
	Rumble(SlayerMaster.ELITE_SLAYER, 1078, "Rumble", 145, new Position(2473, 5347)),

	FTURTLE2(SlayerMaster.ELITE_SLAYER, 201, "Lava Tortoise", 725, new Position(2228, 4781, 4)),
	WTURTLE(SlayerMaster.ELITE_SLAYER, 452, "Aqua Tortoise", 725, new Position(2252, 4007)),

	Goliath(SlayerMaster.ELITE_SLAYER, 1072, "Goliath", 110, new Position(2600, 5218)),

	WTURTLE2(SlayerMaster.ELITE_SLAYER, 452, "Aqua Tortoise", 725, new Position(2252, 4007, 4)),
	Seabane(SlayerMaster.ELITE_SLAYER, 1077, "Seabane", 140, new Position(2850, 4953)),

	//BRUTE
	EBRUTE(SlayerMaster.ELITE_SLAYER, 5080, "Earth Brute", 725, new Position(3366, 4130)),
	EBRUTE2(SlayerMaster.ELITE_SLAYER, 5080, "Earth Brute", 725, new Position(3366, 4130, 4)),

	Volcanus(SlayerMaster.ELITE_SLAYER, 1073, "Volcanus", 115, new Position(2336, 5397)),

	FBRUTE(SlayerMaster.ELITE_SLAYER, 1725, "Lava Brute", 725, new Position(2226, 4746)),
	FBRUTE2(SlayerMaster.ELITE_SLAYER, 1725, "Lava Brute", 725, new Position(2226, 4746, 4)),

	Nautilus(SlayerMaster.ELITE_SLAYER, 1074, "Nautilus", 120, new Position(2982, 5015)),

	WBRUTE(SlayerMaster.ELITE_SLAYER, 1726, "Aqua Brute", 725, new Position(2251, 4001)),
	Scaldor(SlayerMaster.ELITE_SLAYER, 1076, "Scaldor", 135, new Position(2592, 5018)),

	WBRUTE2(SlayerMaster.ELITE_SLAYER, 1726, "Aqua Brute", 725, new Position(2251, 4001, 4)),

	Quake(SlayerMaster.ELITE_SLAYER, 1075, "Quake", 125, new Position(2406, 5215)),

	BRIMSTONE(SlayerMaster.BEAST_MASTER, 2017, "Brimstone", 3000, new Position(2251, 4001, 4)),
	EVERTHORN(SlayerMaster.BEAST_MASTER, 6323, "Everthorn", 3000, new Position(2251, 4001, 4)),
	BASILISK(SlayerMaster.BEAST_MASTER, 2018, "Basilisk", 3000, new Position(2251, 4001, 4)),


	ORACLE(SlayerMaster.BEAST_MASTER_X, 2465, "Oracle", 15000, new Position(2251, 4001, 4)),
	ROCKMAW(SlayerMaster.BEAST_MASTER_X, 2466, "Rockmaw", 15000, new Position(2251, 4001, 4)),
	GRIMLASH(SlayerMaster.BEAST_MASTER_X, 2467, "Grimlash", 15000, new Position(2251, 4001, 4)),


	DREADFLESH(SlayerMaster.SPECTRAL_BEAST, 457, "Dreadflesh", 25000, new Position(3164, 4964)),
	XAROTH(SlayerMaster.SPECTRAL_BEAST, 458, "Xaroth", 25000, new Position(3168, 4829)),
	//x3(SlayerMaster.SPECTRAL_BEAST, 459, "x", 25000, new Position(2251, 4001, 4)),


	CORRUPT_HOUND(SlayerMaster.CORRUPT_SLAYER, 4401, "Corrupt Hound", 5000, new Position(1630, 5733, 0)),
	CORRUPT_DEVOURER(SlayerMaster.CORRUPT_SLAYER, 4402, "Corrupt Devourer", 5000, new Position(1656, 5719, 0)),
	CORRUPT_CHINCHOMPA(SlayerMaster.CORRUPT_SLAYER, 4403, "Corrupt Chinchompa", 5000, new Position(1693, 5743, 0)),
	CORRUPT_CAYMAN(SlayerMaster.CORRUPT_SLAYER, 4404, "Corrupt Cayman", 5000, new Position(1711, 5720, 0)),
	CORRUPT_BEAST(SlayerMaster.CORRUPT_SLAYER, 4405, "Corrupt Beast", 5000, new Position(1685, 5696, 0)),
	CORRUPT_LEOPARD(SlayerMaster.CORRUPT_SLAYER, 4406, "Corrupt Leopard", 5000, new Position(1711, 5670, 0)),
	CORRUPT_WOLF(SlayerMaster.CORRUPT_SLAYER, 4407, "Corrupt Wolf", 5000, new Position(1687, 5645, 0)),
	CORRUPT_TORTOISE(SlayerMaster.CORRUPT_SLAYER, 4408, "Corrupt Tortoise", 5000, new Position(1652, 5672, 0)),
	CORRUPT_BRUTE(SlayerMaster.CORRUPT_SLAYER, 4409, "Corrupt Brute", 5000, new Position(1619, 5664, 0)),



	COSMOS(SlayerMaster.SPECTRAL_SLAYER, 2120, "Cosmos", 6500, new Position(2599, 9514, 0)),//DONE
	NEBULA(SlayerMaster.SPECTRAL_SLAYER, 2121, "Nebula", 6500, new Position(2596, 9421, 0)),//DONE
	ECLIPSE(SlayerMaster.SPECTRAL_SLAYER, 2122, "Eclipse", 6500, new Position(2533, 9428, 0)),//DONE
	METEOR(SlayerMaster.SPECTRAL_SLAYER, 2090, "Meteor", 6500, new Position(2598, 9485, 0)),//DONE
	NOVA(SlayerMaster.SPECTRAL_SLAYER, 2124, "Nova", 6500, new Position(2539, 9429, 0)),//DONE
	PULSAR(SlayerMaster.SPECTRAL_SLAYER, 2091, "Pulsar", 6500, new Position(2595, 9462, 0)),//DONE
	COMET(SlayerMaster.SPECTRAL_SLAYER, 2126, "Comet", 6500, new Position(2542, 9499, 0)),//DONE
	ASTROID(SlayerMaster.SPECTRAL_SLAYER, 2127, "Astroid", 6500, new Position(2599, 9424, 0)),//DONE
	ZENITH(SlayerMaster.SPECTRAL_SLAYER, 2128, "Zenith", 6500, new Position(2536, 9503, 0)),//DONE



	FOREST_WOLF(SlayerMaster.ENCHANTED_MASTER, 143, "Forest Wolf", 500, new Position(2690, 3840, 0)),
	FOREST_DEMON(SlayerMaster.ENCHANTED_MASTER, 1472, "Forest Demon", 500, new Position(2690, 3840, 0)),
	FOREST_TITAN(SlayerMaster.ENCHANTED_MASTER, 7329, "Forest Titan", 500, new Position(2690, 3840, 0)),

	;

	SlayerTasks(SlayerMaster taskMaster, int npcId, String npcLocation, int XP, Position taskPosition) {
		this.taskMaster = taskMaster;
		this.npcId = npcId;
		this.npcLocation = npcLocation;
		this.XP = XP;
		this.taskPosition = taskPosition;
		this.minigameTask = false;
		this.descriptor = "";
	}

	SlayerTasks(SlayerMaster taskMaster, boolean minigameTask, String descriptor, String npcLocation, int XP, Position taskPosition) {
		this.taskMaster = taskMaster;
		this.npcId = -1;
		this.npcLocation = npcLocation;
		this.XP = XP;
		this.taskPosition = taskPosition;
		this.minigameTask = minigameTask;
		this.descriptor = descriptor;
	}

	public static Map<SlayerMaster, ArrayList<SlayerTasks>> tasks = new HashMap<>();
	static {


		for (SlayerMaster master : SlayerMaster.values()) {
			ArrayList<SlayerTasks> slayerMasterSet = new ArrayList<>();
			for(SlayerTasks t : SlayerTasks.values()) {
				if(t.taskMaster == master) {
					slayerMasterSet.add(t);
				}
			}
			tasks.put(master, slayerMasterSet);
		}
	}
	private SlayerMaster taskMaster;
	private int npcId;
	private String npcLocation;
	private int XP;
	private Position taskPosition;
	private boolean minigameTask;
	private String descriptor;

	public SlayerMaster getTaskMaster() {
		return this.taskMaster;
	}
	public int getNpcId() {
		return this.npcId;
	}
	public String getNpcLocation() {
		return this.npcLocation;
	}
	public int getXP() {
		return this.XP;
	}
	public Position getTaskPosition() {
		return this.taskPosition;
	}
	public static SlayerTaskData getNewTaskData(SlayerMaster master, Player player) {
		int slayerTaskAmount = 20;
		ArrayList<SlayerTasks> possibleTasks = tasks.get(master);
       /* if (master.equals(SlayerMaster.BEAST_MASTER)) {
			if (!player.getRights().isManagement()) {
				if (player.getBassilisk_kills() < 50 || player.getEverthorn_kills() < 50 || player.getBrimstone_kills() < 50) {
					possibleTasks.clear();
					possibleTasks.add(BRIMSTONE);
					possibleTasks.add(EVERTHORN);
					possibleTasks.add(BASILISK);
					player.msgRed("Kill 50 of each prior boss to unlock the 2nd tier!");
					player.msgPurp("Basilisk Kills: @red@" + String.valueOf(player.getBassilisk_kills()));
					player.msgPurp("Brimstone Kills: @red@" +String.valueOf(player.getBrimstone_kills()));
					player.msgPurp("Everthorn Kills: @red@" +String.valueOf(player.getEverthorn_kills()));
				}
				if (player.getBassilisk_kills() >= 50 && player.getEverthorn_kills() >= 50 && player.getBrimstone_kills() >= 50) {
					possibleTasks.clear();
					player.msgPurp("You have all Bosses unlocked");
					possibleTasks.add(BRIMSTONE);
					possibleTasks.add(EVERTHORN);
					possibleTasks.add(BASILISK);
					possibleTasks.add(ROCKMAW);
					possibleTasks.add(GRIMLASH);
					possibleTasks.add(ORACLE);

				}
			}
		}*/
		SlayerTasks task = possibleTasks.get(Misc.getRandom(possibleTasks.size() - 1));

		/*
		 * Getting a task
		 */
		if (master == SlayerMaster.BEAST_MASTER || master == SlayerMaster.BEAST_MASTER_X  || master == SlayerMaster.SPECTRAL_BEAST ) {
			slayerTaskAmount = 15 + Misc.getRandom(10);
		} else if (master == SlayerMaster.BEGINNER_SLAYER) {
			slayerTaskAmount = 20 + Misc.random(20);
		} else if (master == SlayerMaster.MEDIUM_SLAYER) {
			slayerTaskAmount = 30 + Misc.random(20);
		} else if (master == SlayerMaster.ELITE_SLAYER) {
			slayerTaskAmount = 20 + Misc.random(15);
		} else if (master == SlayerMaster.CORRUPT_SLAYER) {
			slayerTaskAmount = 65 + Misc.random(55);
		} else if (master == SlayerMaster.SPECTRAL_SLAYER) {
			slayerTaskAmount = 65 + Misc.random(55);
		}
		else if (master == SlayerMaster.ENCHANTED_MASTER) {
			slayerTaskAmount = 15 + Misc.random(15);
		}
		return new SlayerTaskData(task, slayerTaskAmount);
	}

	public String getName() {
		NpcDefinition def = NpcDefinition.forId(npcId);
		return def == null ? Misc.formatText(this.toString().toLowerCase().replaceAll("_", " ")) : def.getName();
	}

	public boolean isMinigameTask() {
		return minigameTask;
	}

	public String getDescriptor() {
		return getNpcId() == -1 ? descriptor : getName();
	}
}
