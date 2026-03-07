package com.ruse.world.content.combat.range;

import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.world.entity.impl.player.Player;

/**
 * A table of constants that hold data for all ranged ammo.
 * 
 * @author lare96
 */
public class CombatRangedAmmo {

	public enum RangedWeaponData {
		NORMAL_BOW(new int[] { 5001, 5004, 5007, 13042, 16879,23146,3745,23064, 23067,18799,23056,13056,18593,13022,
				               13964,17714,16337, 21023,18749,12994, 16871,13329,16873, 20001, 16418 ,16415, 16421,
				               17108, 17112, 17113, 17102, 17105, 17107 , 1481, 1493, 1505, 751,

				1570, 1571, 1572, 1573, 1574, 1575, 1576, 1577, 1578, 2086,2079,2080,2081
		},
				new AmmunitionData[] { AmmunitionData.ZARYTE_AMMO }, RangedWeaponType.SHORTBOW),

		CROSSBOW(new int[] { 2653}, new AmmunitionData[] { AmmunitionData.CORRUPT_CROSSBOW }, RangedWeaponType.CROSSBOW),

		FIRE_EASTERGUN(new int[] {711},
				new AmmunitionData[] { AmmunitionData.FIRE_EASTEREGGS }, RangedWeaponType.BSOAT),
		WATER_EASTERGUN(new int[] {712},
				new AmmunitionData[] { AmmunitionData.WATER_EASTEREGGS }, RangedWeaponType.BSOAT),
		EARTH_EASTERGUN(new int[] {713},
				new AmmunitionData[] { AmmunitionData.EARTH_EASTEREGGS }, RangedWeaponType.BSOAT),
		VOID_EASTERGUN(new int[] {714},
				new AmmunitionData[] { AmmunitionData.VOID_EASTEREGGS }, RangedWeaponType.BSOAT),

		SNOWBALLS(new int[] {1462},
				new AmmunitionData[] { AmmunitionData.SNOWBALL }, RangedWeaponType.SHORTBOW);
		RangedWeaponData(int[] weaponIds, AmmunitionData[] ammunitionData, RangedWeaponType type) {
			this.weaponIds = weaponIds;
			this.ammunitionData = ammunitionData;
			this.type = type;
		}

		private int[] weaponIds;
		private AmmunitionData[] ammunitionData;
		private RangedWeaponType type;

		public int[] getWeaponIds() {
			return weaponIds;
		}

		public AmmunitionData[] getAmmunitionData() {
			return ammunitionData;
		}

		public RangedWeaponType getType() {
			return type;
		}

		public static RangedWeaponData getData(Player p) {
			int weapon = p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
			for (RangedWeaponData data : RangedWeaponData.values()) {
				for (int i : data.getWeaponIds()) {
					if (i == weapon)
						return data;
				}
			}

			String weaponName = p.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition().getName().toLowerCase();

			   if (weaponName.contains("snow")) {
			     	return RangedWeaponData.SNOWBALLS;
			   }

				if (weaponName.contains(" bow") || weaponName.contains(" crossbow")) {
				if (p.getWeapon() == WeaponInterfaces.WeaponInterface.CROSSBOW) {
					return RangedWeaponData.NORMAL_BOW;
				}

				if (p.getWeapon() == WeaponInterfaces.WeaponInterface.LONGBOW
						|| p.getWeapon() == WeaponInterfaces.WeaponInterface.SHORTBOW) {
					return RangedWeaponData.NORMAL_BOW;
				}
			}

			return null;
		}

		public static AmmunitionData getAmmunitionData(Player p) {
			RangedWeaponData data = p.getRangedWeaponData();
			if (data != null) {
				if (data.getType() != RangedWeaponType.BLOWPIPE){
					RangedWeaponData data1 = getData(p);
					return data1.getAmmunitionData()[data1.getAmmunitionData().length - 1];
				}
				int ammunition = p.getEquipment().getItems()[data.getType() == RangedWeaponType.BLOWPIPE
						? Equipment.WEAPON_SLOT
						: Equipment.AMMUNITION_SLOT].getId();
				for (AmmunitionData ammoData : AmmunitionData.values()) {
					for (int i : ammoData.getItemIds()) {
						if (i == ammunition)
							return ammoData;
					}
				}
			}
			return AmmunitionData.BRONZE_ARROW;
		}
	}

	public enum AmmunitionData {
		ZARYTE_AMMO(new int[] { 78 }, 250, 249, 3, 44, 200, 43, 31),

		CORRUPT_CROSSBOW(new int[] { -1 }, 311, 374, 3, 44, 200, 10, 15),

		SNOWBALL(new int[] { -1 }, -1, 1209, 3, 44, 200, 43, 31),


		FIRE_EASTEREGGS(new int[] { -1 }, -1, 978, 12, 44, 200, 43, 31),
		WATER_EASTEREGGS(new int[] { -1 }, -1, 979, 12, 44, 200, 43, 31),
		EARTH_EASTEREGGS(new int[] { -1 }, -1, 977, 12, 44, 200, 43, 31),
		VOID_EASTEREGGS(new int[] { -1 }, -1, 980, 12, 44, 200, 43, 31),


		BRONZE_ARROW(new int[] { 882 }, 19, 10, 3, 44, 1, 43, 31),;;

		AmmunitionData(int[] itemIds, int startGfxId, int projectileId, int projectileSpeed, int projectileDelay,
				int strength, int startHeight, int endHeight) {
			this.itemIds = itemIds;
			this.startGfxId = startGfxId;
			this.projectileId = projectileId;
			this.projectileSpeed = projectileSpeed;
			this.projectileDelay = projectileDelay;
			this.strength = strength;
			this.startHeight = startHeight;
			this.endHeight = endHeight;
		}

		private int[] itemIds;
		private int startGfxId;
		private int projectileId;
		private int projectileSpeed;
		private int projectileDelay;
		private int strength;
		private int startHeight;
		private int endHeight;

		public int[] getItemIds() {
			return itemIds;
		}

		public boolean hasSpecialEffect() {
			return getItemIds().length >= 2;
		}

		public int getStartGfxId() {
			return startGfxId;
		}

		public int getProjectileId() {
			return projectileId;
		}

		public int getProjectileSpeed() {
			return projectileSpeed;
		}

		public int getProjectileDelay() {
			return projectileDelay;
		}

		public int getStrength() {
			return strength;
		}

		public int getStartHeight() {
			return startHeight;
		}

		public int getEndHeight() {
			return endHeight;
		}
	}

	public enum RangedWeaponType {

		LONGBOW(5, 5), SHORTBOW(5, 4),
		CROSSBOW(5, 4), ARMADYLXBOW(5, 5),
		THROW(4, 3), DARK_BOW(5, 5),
		HAND_CANNON(5, 2),
		BLOWPIPE(4, 3),
		BSOAT(8, 1);

		RangedWeaponType(int distanceRequired, int attackDelay) {
			this.distanceRequired = distanceRequired;
			this.attackDelay = attackDelay;
		}

		private int distanceRequired;
		private int attackDelay;

		public int getDistanceRequired() {
			return distanceRequired;
		}

		public int getAttackDelay() {
			return attackDelay;
		}
	}
}
