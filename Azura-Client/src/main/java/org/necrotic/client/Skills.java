package org.necrotic.client;
public final class Skills {

    public static int ATTACK = 0;
    public static int DEFENCE = 1;
    public static int STRENGTH = 2;
    public static int HITPOINTS = 3;
    public static int RANGED = 4;
    public static int PRAYER = 5;
    public static int MAGIC = 6;
    public static int COOKING = 7;
    public static int WOODCUTTING = 8;
    public static int FLETCHING = 9;
    public static int FISHING = 10;
    public static int FIREMAKING = 11;
    public static int CRAFTING = 12;
    public static int JOURNEYMAN = 13;
    public static int MINING = 14;
    public static int HERBLORE = 15;
    public static int AGILITY = 16;
    public static int THIEVING = 17;
    public static int SLAYER = 18;
    public static int FARMING = 19;
    public static int RUNECRAFTING = 20;
    public static int ALCHEMY = 21;
    public static int BEAST_HUNTER = 22;
    public static int NECROMANCY = 23;
	public static int SOULRENDING = 24;
	public static int COMING_SOON_1 = 25;


    public static final int SKILL_COUNT = 26;

	public static String[] SKILL_NAMES = {
        "Attack", "Strength", "Defence", "Magic", "Ranged", "Prayer", "Necromancy", "Slayer", "Beast Master", "Hitpoints",
        "Mining", "Woodcutting", "Journeyman", "Herblore", "Alchemy", "Soulrending", "Arcana"
	};

	public static int SKILL_ID(int ids) {
		int[] hoverIds = {
            86132, 86133, 86134, 86135, 86136, 86137, 86138, 86139, 86140, 86141,
            86332, 86333, 86334, 86335, 86336, 86337, 86338
        };
		for (int hover = 0; hover < hoverIds.length; hover++) {
			if (hoverIds[hover] == ids) {
				ids = hover;
				return hoverIds[ids];
			}
		}
		return 0;
	}

	public static final boolean[] SKILLS_ENABLED = {
			true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true, true, true, true, true, true,
			true, true, true, true, true
	};

	public static final int[][] goalData = new int[SKILL_COUNT][3];
	public static String goalType = "Target Level: ";
	public static int selectedSkillId = -1;

	static {
		for (int i = 0; i < goalData.length; i++) {
			goalData[i][0] = -1;
			goalData[i][1] = -1;
			goalData[i][2] = -1;
		}
	}

	public static int SKILL_ID_NAME(String name) {
		name = name.toLowerCase().trim();
		int[] skillID = {
            ATTACK, STRENGTH, DEFENCE, MAGIC, RANGED, PRAYER, NECROMANCY, SLAYER, BEAST_HUNTER, HITPOINTS,
            MINING, WOODCUTTING, JOURNEYMAN, HERBLORE, ALCHEMY, SOULRENDING, COMING_SOON_1
		};
		String[] names = {
            "Attack", "Strength", "Defence", "Magic", "Ranged", "Prayer", "Necromancy", "Slayer", "Beast Hunter", "Hitpoints",
            "Mining", "Woodcutting", "Journeyman", "Herblore", "Alchemy" , "Soulrending", "Arcana"
		};
		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase(name)) {
				return skillID[i];
			}
		}
		return 0;
	}
}
