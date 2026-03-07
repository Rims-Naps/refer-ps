package com.ruse.world.content.combat.strategy;
import com.ruse.world.content.combat.strategy.impl.*;
import com.ruse.world.content.minigames.impl.dungeoneering.DungeoneeringBossNpc;
import com.ruse.world.content.quests.impl.JackFrostScript;
import com.zaros.world.entity.actor.combat.strategy.impl.dung.DungeoneeringBossCombat;

import java.util.HashMap;
import java.util.Map;


public class CombatStrategies {
	private static final DefaultMeleeCombatStrategy defaultMeleeCombatStrategy = new DefaultMeleeCombatStrategy();
	private static final DefaultMagicCombatStrategy defaultMagicCombatStrategy = new DefaultMagicCombatStrategy();
	private static final DefaultRangedCombatStrategy defaultRangedCombatStrategy = new DefaultRangedCombatStrategy();
	private static final Map<Integer, CombatStrategy> STRATEGIES = new HashMap<>();
	private static final EmptyCombatStrategy emptyCombatStrategy = new EmptyCombatStrategy();
	public static void init() {
		DefaultMagicCombatStrategy defaultMagicStrategy = new DefaultMagicCombatStrategy();
		DefaultRangedCombatStrategy defaultRangedStrategy = new DefaultRangedCombatStrategy();
		DefaultMeleeCombatStrategy defaultMeleeCombatStrategy = new DefaultMeleeCombatStrategy();
		STRATEGIES.put(13, defaultMagicStrategy);
		STRATEGIES.put(6220, defaultRangedStrategy);
		STRATEGIES.put(174, defaultMeleeCombatStrategy);
		STRATEGIES.put(DungeoneeringBossNpc.Constants.BOSS_NOHEADICON, DungeoneeringBossCombat.INSTANCE);
		STRATEGIES.put(DungeoneeringBossNpc.Constants.BOSS_PROT_MAGE, DungeoneeringBossCombat.INSTANCE);
		STRATEGIES.put(DungeoneeringBossNpc.Constants.BOSS_PROT_RANGE, DungeoneeringBossCombat.INSTANCE);
		STRATEGIES.put(DungeoneeringBossNpc.Constants.BOSS_PROT_MELEE, DungeoneeringBossCombat.INSTANCE);

		//LAVA BOSS
		STRATEGIES.put(8002, new LavaBossScript());
		//AQUABOSS
		STRATEGIES.put(8000, new AquaBossScript());
		STRATEGIES.put(8520, new PoseidonScript());
		//EARTHBOSS
		STRATEGIES.put(8004, new EarthBossScript());

		STRATEGIES.put(8000, new Azgoth());


		//AFK BOSS
		STRATEGIES.put(576, new AfkBossScript());

		STRATEGIES.put(640, new VoidCrystalBoss());
		STRATEGIES.put(643, new VoidCrystalBoss());
		STRATEGIES.put(644, new SpringBossScript());

		STRATEGIES.put(1037, new CorruptBossOne());
		STRATEGIES.put(1041, new CorruptHounds());
		STRATEGIES.put(1038, new CorruptBossTwo());
		STRATEGIES.put(1039, new CorruptBossThree());
		STRATEGIES.put(1040, new CorruptBossFour());

		STRATEGIES.put(2115, new CorruptBossOne());
		STRATEGIES.put(2116, new CorruptBossOne());
		STRATEGIES.put(2117, new CorruptBossOne());
		STRATEGIES.put(2118, new CorruptBossOne());
		STRATEGIES.put(2119, new CorruptBossOne());


		//CHALLENGER TIER
		STRATEGIES.put(2111, new HellfireScript());
		STRATEGIES.put(2112, new CryosScript());
		STRATEGIES.put(2113, new ToxusScript());


		STRATEGIES.put(2097, new FallenKnightScript());
		STRATEGIES.put(2201, new FallenMageScript());
		STRATEGIES.put(2200, new FallenRangeScript());



		//BEAST HUNTER BOSSES
		//BRIMSTONE
		STRATEGIES.put(2017, new BrimstoneScript());
		//EVERTHORNn
		STRATEGIES.put(6323, new EverthornScript());
		//BASILISK
		STRATEGIES.put(2018, new BasiliskScript());


		//ORACLE
		STRATEGIES.put(2465, new OracleScript());
		//ROCKMAW
		STRATEGIES.put(2466, new RockmawScript());
		//GRIMLASH
		STRATEGIES.put(2467, new GrimScript());

        //JACKFROST
        STRATEGIES.put(8517, new JackFrostScript());

		STRATEGIES.put(9813, new PyromancerScript());

		STRATEGIES.put(12239, new GeomancerScript());
		STRATEGIES.put(9814, new AquamancerScript());

		STRATEGIES.put(309, new SoulBaneMinionScript());
		STRATEGIES.put(310, new SoulBaneMinionScript());
		STRATEGIES.put(314, new SoulBaneMinionScript());
		STRATEGIES.put(315, new SoulBaneMinionScript());

		STRATEGIES.put(316, new SoulBaneScript());
		STRATEGIES.put(6326, new RiftBosses());
		STRATEGIES.put(2745, new RiftBosses());
		STRATEGIES.put(8009, new RiftBosses());
		STRATEGIES.put(9006, new RiftBosses());
		STRATEGIES.put(1807, new RiftBosses());
		STRATEGIES.put(1084, new RiftBosses());
		STRATEGIES.put(4444, new RiftBosses());
		STRATEGIES.put(601, new RiftBosses());

		STRATEGIES.put(1081, new VoidBossScript());//MULTIBOSS 4

		STRATEGIES.put(3117, new DailyEarthScript());//MULTIBOSS 3
		STRATEGIES.put(3172, new DailyEarthScript());//DAILY
		STRATEGIES.put(3169, new DailyEarthScript());//PRAY BOSS


		STRATEGIES.put(9800, new DailyWaterScript());//MULTIBOSS 2
		STRATEGIES.put(3173, new DailyWaterScript());//DAILY
		STRATEGIES.put(3170, new DailyWaterScript());//PRAY BOSS


		STRATEGIES.put(6330, new DailyFireScript());//MULTIBOSS 1
		STRATEGIES.put(3174, new DailyFireScript());//DAILY
		STRATEGIES.put(3171, new DailyFireScript());//PRAY BOSS


		STRATEGIES.put(457, new DailyFireScript());//DREADFLESH
		STRATEGIES.put(458, new DailyWaterScript());//XAROTH


		STRATEGIES.put(1061, new SantaBossScript());//SANTA BOSS
		STRATEGIES.put(3307, new VoteBossScript());//BONE BOSS
		STRATEGIES.put(3308, new VoteBossScript());//BONE BOSS
		STRATEGIES.put(2342, new VoteBossScript());//VOTE BOSS
		STRATEGIES.put(2341, new DonoBossScript());//DONO BOSS

		STRATEGIES.put(555, new DonatorZoneBossScript());//ARCHON
		STRATEGIES.put(556, new DonatorZoneBossScript());//CELESTIAL
		STRATEGIES.put(557, new DonatorZoneBossScript());//ASCENDANT
		STRATEGIES.put(559, new DonatorZoneBossScript());//GLADIATOR

		STRATEGIES.put(1789, new EasterGlobalScript());//BUNNY GLOBAL


		STRATEGIES.put(3000, new CosmicCupidScript());//VALENTINES GLOBAL


		STRATEGIES.put(1023, new VoteBossScript());//FRENZY BOSS
		STRATEGIES.put(1022, new VoteBossScript());//STREAM BOSS


		STRATEGIES.put(2034, new TowerSuperior());//FIRE ELEMENTAL

		//TOWER MOBS
		STRATEGIES.put(2021, new TowerFireElemental());//FIRE ELEMENTAL
		STRATEGIES.put(2019, new TowerEarthElemental());//EARTH ELEMENTAL
		STRATEGIES.put(2022, new TowerWaterElemental());//WATER ELEMENTAL

		STRATEGIES.put(2023, new EarthGolem());//EARTH GOLEM
		STRATEGIES.put(2024, new WaterGolem());//WATER GOLEM
		STRATEGIES.put(2025, new FireGolem());//FIRE GOLEM

		STRATEGIES.put(2034, new TowerSuperior());//WATER ELEMENTAL

		//CORRUPT NPCS
		STRATEGIES.put(4401, new CorruptMinionScript());
		STRATEGIES.put(4402, new CorruptMinionScript());
		STRATEGIES.put(4403, new CorruptMinionScript());
		STRATEGIES.put(4404, new CorruptMinionScript());
		STRATEGIES.put(4405, new CorruptMinionScript());
		STRATEGIES.put(4406, new CorruptMinionScript());
		STRATEGIES.put(4407, new CorruptMinionScript());
		STRATEGIES.put(4408, new CorruptMinionScript());
		STRATEGIES.put(4409, new CorruptMinionScript());

		//SPECTRAL NPCS
		STRATEGIES.put(2120, new SpectralMinionScript());
		STRATEGIES.put(2121, new SpectralMinionScript());
		STRATEGIES.put(2122, new SpectralMinionScript());
		STRATEGIES.put(2090, new SpectralMinionScript());
		STRATEGIES.put(2124, new SpectralMinionScript());
		STRATEGIES.put(2091, new SpectralMinionScript());
		STRATEGIES.put(2126, new SpectralMinionScript());
		STRATEGIES.put(2127, new SpectralMinionScript());
		STRATEGIES.put(2128, new SpectralMinionScript());



		//ZONE NPCS
		STRATEGIES.put(13747, new ZoneNpcScript());
		STRATEGIES.put(1801, new ZoneNpcScript());
		STRATEGIES.put(9027, new ZoneNpcScript());
		STRATEGIES.put(1802, new ZoneNpcScript());
		STRATEGIES.put(13458, new ZoneNpcScript());
		STRATEGIES.put(8006, new ZoneNpcScript());
		STRATEGIES.put(688, new ZoneNpcScript());
		STRATEGIES.put(350, new ZoneNpcScript());
		STRATEGIES.put(182, new ZoneNpcScript());

		STRATEGIES.put(9815, new ZoneNpcScript());
		STRATEGIES.put(1741, new ZoneNpcScript());
		STRATEGIES.put(12228, new ZoneNpcScript());
		STRATEGIES.put(9026, new ZoneNpcScript());
		STRATEGIES.put(1150, new ZoneNpcScript());
		STRATEGIES.put(9837, new ZoneNpcScript());
		STRATEGIES.put(9002, new ZoneNpcScript());
		STRATEGIES.put(7000, new ZoneNpcScript());
		STRATEGIES.put(1821, new ZoneNpcScript());

		STRATEGIES.put(1727, new ZoneNpcScript());
		STRATEGIES.put(1729, new ZoneNpcScript());
		STRATEGIES.put(1730, new ZoneNpcScript());
		STRATEGIES.put(1731, new ZoneNpcScript());
		STRATEGIES.put(1735, new ZoneNpcScript());
		STRATEGIES.put(5539, new ZoneNpcScript());
		STRATEGIES.put(5547, new ZoneNpcScript());
		STRATEGIES.put(5533, new ZoneNpcScript());
		STRATEGIES.put(5553, new ZoneNpcScript());


		STRATEGIES.put(1072, new ZoneNpcScript());
		STRATEGIES.put(1073, new ZoneNpcScript());
		STRATEGIES.put(1074, new ZoneNpcScript());
		STRATEGIES.put(1075, new ZoneNpcScript());
		STRATEGIES.put(1076, new ZoneNpcScript());
		STRATEGIES.put(1077, new ZoneNpcScript());
		STRATEGIES.put(1078, new ZoneNpcScript());
		STRATEGIES.put(1079, new ZoneNpcScript());
		STRATEGIES.put(1080, new ZoneNpcScript());


		STRATEGIES.put(2114, new ZoneNpcScript());

	}
	public static CombatStrategy getStrategy(int npc) {
		if (STRATEGIES.get(npc) != null) {
			return STRATEGIES.get(npc);
		}
		return defaultMeleeCombatStrategy;
	}
	public static CombatStrategy getDefaultMeleeStrategy() {
		return defaultMeleeCombatStrategy;
	}
	public static CombatStrategy getDefaultMagicStrategy() {
		return defaultMagicCombatStrategy;
	}
	public static CombatStrategy getDefaultRangedStrategy() {
		return defaultRangedCombatStrategy;
	}
	public static CombatStrategy getEmptyCombatStrategy() {
		return emptyCombatStrategy;
	}
}
