package org.necrotic;

public class Configuration {
	/**
	 * Client Dimensions
	 */		
	public static int 
		clientSize = 0,
		clientWidth = 765,
		clientHeight = 503;
	
	public static int getClientWidth() {
		return clientWidth;
	}
	
	public static int getClientHeight() {
		return clientHeight;
	}
	
	public static final boolean IS_RUNNING_WINDOWS = true;
	public static final boolean BETA = false;


	public static final boolean DUMP_INVENTORY_IMAGES = true;
	public static final boolean DROPBOX_MODE = false;

	public final static String CLIENT_NAME = Configuration.BETA ? "SolakBeta" : "Solak | Season 1: The Forgotten Realm";
	public final static String CACHE_DIRECTORY_NAME = Configuration.BETA ? "SolakBeta" : ".SolakRelease";
	public static final String SETTINGS_DIRECTORY_NAME = Configuration.BETA ? "SolakBeta_settings" : "SolakClientRelease";

	public final static int NPC_BITS = 18;
	public static final boolean SEND_HASH = true;
	public static final int[] REPACK_INDICIES = {
        6
	};
	public static boolean SAVE_ACCOUNTS = true;
	public static boolean NEW_HITMARKS = false;
	public static boolean CONSTITUTION_ENABLED = true;
	public static boolean NEW_HEALTH_BARS = false;
	public static boolean MONEY_POUCH_ENABLED = false;
	public static boolean NOTIFICATIONS_ENABLED = false;
	public static boolean NEW_CURSORS = false;
	public static boolean NEW_FUNCTION_KEYS = true;
	public static boolean FOG_ENABLED = false;
	public static boolean GROUND_TEXT = false;
	public static boolean HIGH_DETAIL = false;
	public static boolean hdTexturing = true;
	public static boolean DISPLAY_USERNAMES_ABOVE_HEAD = false;
	public static boolean DISPLAY_HP_ABOVE_HEAD = false;
	public static boolean PARTICLES_ENABLED = false;

//ds
	public static boolean hdMinimap = false;
	public static boolean hdShading = false;
	public static boolean TOGGLE_FOV = true;
	public final static int CLIENT_VERSION = 9;
	public static boolean localHost = true;


	//public static boolean PLAYER_EQUIPMENT = true;

	
	/**
     * Display Settings
	 *
     */
    public static boolean ANIMATE_TEXTURES = true;
    public static boolean RENDER_SELF = true;
    public static boolean RENDER_PLAYERS = true;
    public static boolean RENDER_NPCS = true;
    public static boolean RENDER_DISTANCE = true;
    public static int MODEL_CLAMPING = 4700;
    public static int TILE_CLAMPING = 90;
    public static int MAX_TILE_CLAMPING = TILE_CLAMPING;
    public static int RENDER_DISTANCE_CLAMP = 1000;


    /**
     * Chat Settings
     */
    public static boolean SPLIT_CHAT = false;
    public static boolean TIME_STAMPS = true;
    public static boolean SMILIES_ENABLED = false;
    public static boolean HIGHLIGHT_USERNAME = true;

    /**
     * Toggle Settings
     */
    public static boolean DRAW_EFFECT_TIMERS = true;

    /**
     * General Settings
     */
    public static boolean USERNAMES_ABOVE_HEAD = false;
    public static boolean TWEENING_ENABLED = true;
    public static boolean TOGGLE_ROOF_OFF = false;
    public static boolean ITEM_OUTLINES = true;
    public static boolean HP_BAR = true;

    /**
     * Attack option priorities
     * 0 -> Depends on combat level
     * 1 -> Always right-click
     * 2 -> Left-click where available
     * 3 -> Hidden
     */
    public static int playerAttackOptionPriority = 0;
    public static int npcAttackOptionPriority = 2;

    public static int activeBankButton = 571;

    /**
     * Does the escape key close current interface?
     */
    public static boolean escapeCloseInterface = false;

	public static String SERVER_HOST() {

		return localHost ? "127.0.0.1" : "127.0.0.1"; //209.237.141.131
	}
	public static int CLIENT_PORT = 43594;
	public final static boolean JAGCACHED_ENABLED = false;
	public final static String JAGCACHED_HOST = "";
}
