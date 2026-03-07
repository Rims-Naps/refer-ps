package com.ruse;

import com.ruse.ServerSaves.DissolveAmountReader;
import com.ruse.ServerSaves.SlayerGlobalReader;
import com.ruse.ServerSaves.VoteAmountReader;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.net.security.ConnectionHandler;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GameSettings {

    public static final int GAME_VERSION = 9;
    public static int GAME_PORT = 43594;
    public static boolean DEV_MODE = true;

    public static boolean LOCAL_HOST = true;

    public static boolean BOGO = false;
    public static boolean B2GO = false;

    public static boolean aggrostage = false;
    public static int azgothstage = 0;

    public static int fallenKnightStage = 0;

    public static int fallenKilledCount = 0;

    public static boolean fallenMinionActive = false;

    public static boolean fallentEventActive = false;

    public static int santaStage = 0;
    public static int votebossStage = 0;
    public static int donobossStage = 0;

    public static int cupidBossStage = 0;



    public static int globalslayertasks = (int) SlayerGlobalReader.getAzgothAmount();

    /*
     * Bunch of variables for the Global Events
     */


    public static int DISSOLVE_AMOUNT = (int) DissolveAmountReader.getDissolveAmount();
    public static boolean EVENT_ALERT_SENT = false;

    public static boolean STREAMER_EVENT = false;
    public static boolean FRENZY_EVENT = false;

    public static boolean DOUBLE_VOTE = true;
    public static boolean DOUBLE_DROP = true;
    public static boolean DOUBLE_SKILL_EXP = false;
    public static boolean DOUBLE_NECRO_POINTS = false;
    public static boolean DOUBLE_SLAYER = false;
    public static boolean DROPPARTY = false;
    public static boolean DOUBLE_RAIDS = false;
    public static String broadcastMessage = null;
    public static int broadcastTime = 0;

    public static final String[] CLIENT_HASH = { "d41d8cd98f00b204e9800998ecf8427e" };

    public static final String RSPS_NAME = "Solak";
    public static final String BCRYPT_EXAMPLE_SALT = "$2a$09$kCPIaYJ6vJmzJM/zq8vuSO";
    /**
     * WEB URLS
     */
    public static final String DomainUrl = "https://discord.gg/rRGzCTPv";
    public static final String ForumUrl = "https://discord.gg/rRGzCTPv";
    public static final String StoreUrl = "http://refer-ps.teamgames.io/category/4594/page/1";

    public static final String wr3cked = "https://www.youtube.com/channel/UCNm7R0y8KN8kSn3yVn04pkg";
    public static final String VoteUrl = "https://refer-ps.teamgames.io/services/vote";
    public static final String HiscoreUrl = "https://discord.gg/rRGzCTPv";
    public static final String ReportUrl = "https://discord.gg/rRGzCTPv";
    public static final String RuleUrl = "https://discord.gg/rRGzCTPv";
    public static final String WikiaUrl = "https://wiki.solak-rsps.com";
    public static final String IronManModesUrl = "";
    public static final String DiscordUrl = "https://discord.gg/rRGzCTPv";
    public static final String WikiUrl = "https://wiki.solak-rsps.com";
    public static final String SbUrl = "";
    public static final String TreeUrl = "";
    public static final String ForestUrl = "";
    public static final String CoeUrl = "";
    public static final String SlayerUrl = "";
    public static final String BenefitsUrl = "";
    public static final String UpgradeUrl = "";
    public static final String AmmoUrl = "";
    public static final String Guides = "https://wiki.solak-rsps.com";

    public static final long tempMuteInterval = 86400000;
    public static final int BaseImplingExpMultiplier = 2;
    /**
     * Shop Buy Limit (at one time)
     */
    public static final int Shop_Buy_Limit = 25000;
    public static final int Spec_Restore_Cooldown = 180000; // in milliseconds (180000 = 3 seconds * 60 * 1000)
    public static final int Votesneeded = 20;
    public static boolean seasonalMulti = false;
    public static int KINGS_BONES; //
    public static int EMERALD_CHAMP_AMOUNT; //

    public static final int massMessageInterval = 180000; // in milliseconds (180000 = 3 seconds * 60 * 1000)
    public static final int charcterBackupInterval = 3600000;
    public static final int charcterSavingInterval = 15 * 60000;
    public static final int playerCharacterListCapacity = 5000; // was 1000
    public static final int npcCharacterListCapacity = 50000; // was 2027

    /**
     * The game version
     */
    public static final int GAME_UID = 23; // don't change
    public static final boolean BCRYPT_HASH_PASSWORDS = false;
    public static final int BCRYPT_ROUNDS = Integer.parseInt("04"); // add a 0 for numbers less than 10. IE: 04, 05, 06,
    /**
     * The maximum amount of players that can be logged in on a single game
     * sequence.
     */
    public static final int LOGIN_THRESHOLD = 50;
    /**
     * The maximum amount of players that can be logged in on a single game
     * sequence.
     */
    public static final int LOGOUT_THRESHOLD = 50;
    /**
     * The maximum amount of players who can receive rewards on a single game
     * sequence.
     */
    public static final int VOTE_REWARDING_THRESHOLD = 15;
    // 07, 08, 09, 10, 11, etc.
    /**
     * The maximum amount of connections that can be active at a time, or in other
     * words how many clients can be logged in at once per connection. (0 is counted
     * too)
     */
    public static final int CONNECTION_AMOUNT = 3;
    /**
     * The throttle interval for incoming connections accepted by the
     * {@link ConnectionHandler}.
     */
    public static final long CONNECTION_INTERVAL = 1000;
    /**
     * The number of seconds before a connection becomes idle.
     */
    public static final int IDLE_TIME = 15;
    /**
     * The keys used for encryption on login
     */
    // REGENERATE RSA
    /*
     * This is the server, so in your PrivateKeys.txt Copy the ONDEMAND_MODULUS's
     * value to RSA_MODULUS below. Copy the ONDEMAND_EXPONENT's value to
     * RSA_EXPONENT below.
     */
    public static final BigInteger RSA_MODULUS = new BigInteger(
            "133567728213741143816509511422128583057676855627752897223206244773295600712883732719562506843823211218750380752578336050348277757629131584859245078421579140186623018332242256409998547043484697935288906462270420300277547466511098999079857947706507159245894090452949369807517421080349147184146818764010463810313");
    public static final BigInteger RSA_EXPONENT = new BigInteger(
            "18741914165335055900279559135112904920859916754700636935846996764197725623017208692633120419546183840695004370061345168668122774297839297715269507929335208512554570607386848426474068608268296375734276959109513059016659117858269240413485990150621744581036704243015567064352654789565136769391797590390726828977");
    /**
     * The maximum amount of messages that can be decoded in one sequence.
     */
    public static final int DECODE_LIMIT = 50;
    /**
     * GAME
     **/

    public static final int[] MASSACRE_ITEMS = {4587, 20051 , 13632, 11842, 11868, 14525, 13734, 10828, 1704,
            15365, 15363, 7462, 3842};
    /**
     * Processing the engine
     */
    public static final int ENGINE_PROCESSING_CYCLE_RATE = 600;// 200;
    public static final int GAME_PROCESSING_CYCLE_RATE = 600;
    /**
     * The default position
     */
    public static final Position DEFAULT_POSITION = new Position(3167, 3544, 0);
    public static final Position STARTER = new Position(3167, 3544, 0);
    public static final Position CORP_CORDS = new Position(2900, 4384);
    public static final Position HOME_CORDS = new Position(3167, 3544, 0);
    public static final Position MEMBER_ZONE = new Position(2851, 3348);
    public static final int MAX_STARTERS_PER_IP = 2;
    public static final Item nullItem = new Item(-1, 0);
    public static final int[] UNTRADEABLE_ITEMS = {};
    public static final int[] UNSELLABLE_ITEMS = new int[]{
    };
    public static final int ATTACK_TAB = 0, SKILLS_TAB = 1, PLAYER_TAB = 2, ACHIEVEMENT_TAB = 15, INVENTORY_TAB = 3, EQUIPMENT_TAB = 4, PRAYER_TAB = 5, MAGIC_TAB = 6, NECROMANCY_SPAWN = 13, FRIEND_TAB = 8, DONATION_TAB = 9, SERVER_TAB = 10, LOGOUT = 14, OPTIONS_TAB = 11, EMOTES_TAB = 12, STAFF_TAB = 15;
    public static int players = 0;
    public static boolean DOUBLEDR = true;
    public static boolean Halloween = false;
    public static boolean newYear2017 = false;
    public static boolean LOG_CHAT = true;
    public static boolean LOG_NPCDROPS = true;
    public static boolean LOG_TRADE = true;
    public static boolean LOG_DUEL = true;
    public static boolean LOG_COMMANDS = true;
    public static boolean LOG_KILLS = true;
    public static boolean LOG_LOGINS = true;
    public static boolean LOG_LOGINSWIP = true;
    public static boolean LOG_BONDS = true;
    public static boolean LOG_SPINSWIP = true;
    public static boolean LOG_POS = true;
    public static boolean LOG_PICKUPS = true;
    public static boolean LOG_DROPPED = true;
    public static boolean LOG_DONATIONS = true;
    public static boolean LOG_CLAN = true;

    public static boolean server_logins_locked = false;

    public static int instance_counter = 0;

    public static int COUNTER_FOR_CAMPAIGNS = 0;

    public static int LAST_MONTHLY_CAMPAIGN = 0;

}
