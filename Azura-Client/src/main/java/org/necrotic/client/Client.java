package org.necrotic.client;
/*import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;*/
/*import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;*/
import org.apache.commons.lang3.math.NumberUtils;
import org.necrotic.ColorConstants;
import org.necrotic.Configuration;
/*
import org.necrotic.RichPresense;
*/
import org.necrotic.client.Settings.Load;
import org.necrotic.client.Settings.Save;
import org.necrotic.client.cache.Archive;
import org.necrotic.client.cache.Decompressor;
import org.necrotic.client.cache.config.VarBit;
import org.necrotic.client.cache.config.Varp;
import org.necrotic.client.cache.definition.*;
import org.necrotic.client.cache.media.*;
import org.necrotic.client.cache.media.rsinterface.dropdowns.impl.Keybinding;
import org.necrotic.client.cache.media.rsinterface.interfaces.Bank;
import org.necrotic.client.cache.media.rsinterface.interfaces.OSRSCreationMenu;
import org.necrotic.client.cache.media.rsinterface.interfaces.SettingsInterface;
import org.necrotic.client.collection.NodeList;
import org.necrotic.client.collection.Node;
import org.necrotic.client.media.*;
import org.necrotic.client.media.renderable.GameObject;
import org.necrotic.client.util.*;
import org.necrotic.client.net.Stream;
import org.necrotic.client.net.requester.OnDemandFetcher;
import org.necrotic.client.net.requester.OnDemandRequest;
import org.necrotic.client.media.renderable.*;
import org.necrotic.client.media.renderable.actor.Player;
import org.necrotic.client.gameframe.GameFrame;
import org.necrotic.client.gameframe.GameFrame.ScreenMode;
import org.necrotic.client.gameframe.impl.ChatArea;
import org.necrotic.client.gameframe.impl.MapArea;
import org.necrotic.client.gameframe.impl.TabArea;
import org.necrotic.client.cache.media.rsinterface.*;
import org.necrotic.client.cache.media.rsinterface.dropdowns.DropdownMenu;
//import org.necrotic.client.cache.media.rsinterface.movement.InterfaceMovementManager;
import org.necrotic.client.net.ISAACRandomGen;
import org.necrotic.client.media.renderable.actor.Actor;
import org.necrotic.client.media.renderable.actor.Npc;
import org.necrotic.client.net.Socket;
import org.necrotic.client.notification.Alert;
import org.necrotic.client.notification.AlertBox;
import org.necrotic.client.notification.AlertHandler;
import org.necrotic.client.notification.AlertManager;
import org.necrotic.client.cache.media.particles.Particle;
import org.necrotic.client.scene.Region;
import org.necrotic.client.scene.Scene;
import org.necrotic.client.scene.SceneSpawnNode;
import org.necrotic.client.scene.tile.*;
import org.necrotic.client.scene.util.CollisionMap;
import org.necrotic.client.util.task.Task;
import org.necrotic.client.util.task.TaskManager;
import org.necrotic.client.tools.FileUtilities;
import org.necrotic.client.tools.ItemEditor;
import org.necrotic.client.sound.music.Class56;
import org.necrotic.client.sound.music.Class56_Sub1_Sub1;
import org.necrotic.client.sound.soundsystem.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.AppletContext;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.CRC32;


//import org.necrotic.client.graphics.particles.Particle;

public class Client extends GameRenderer {


    public static final Random RANDOM = new Random();
    public static boolean safeRenderer = false;
    public static boolean itemEditing = false;

    private int lastDropDownOpenedP = -1;
    private int lastDropDownOpenedD = -1;

    public int getSpecAmount() {
        return myPlayer.specAmount;
    }

    private int[] validIds = {
        7560,
        7559,
        7558,
        7557,
        7556,
        7555,
        7554,
        7553,
        7552,
        7551
    };

    private int[] specAmounts = {
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
    };

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

    /*public static boolean inMoveButton;

    public void processMoveButton(RSInterface rsi, int xOff, int yOff) {
        if (rsi == null || rsi.children == null)
            return;

        int firstX = rsi.childX[0] + xOff;
        int firstY = rsi.childY[0] + yOff;

        if (firstX >= 16)
            firstX -= 16;
        else
            firstX = 0;

        int spriteId = 0;
        int hoveringSprite = 5630;//Hovering move button sprite, just change these numbers when u add a sprite, want me to make some?
        // i can manage that part for sure, b
        int normalSprite = 5630;//Normal move button sprite

        if (super.mouseX >= firstX && super.mouseX <= firstX + 16 && super.mouseY >= firstY && super.mouseY <= firstY+16) {
            spriteId = hoveringSprite;
            inMoveButton = true;
        } else {
            spriteId = normalSprite;
            inMoveButton = false;
        }

        spritesMap.get(spriteId).drawSprite(firstX, firstY);

    }*/
 //   public BooleanProperty npcSpawnEditing = new SimpleBooleanProperty();

 /*  private ObjectProperty<Point3i> pos = new SimpleObjectProperty<>();

    public ObjectProperty<Point3i> getPos() {
        return pos;
    }

       public void onUpdate() {
        int x = getBaseX() + (myPlayer.x - 6 >> 7);
        int y = getBaseY() + (myPlayer.y - 6 >> 7);
        int z = plane;
        pos.set(new Point3i(x, y, z));
    }
*/
    public Player getPlayersMini() {
        for(Player p : playerArray) {
            if(p == null) continue;
            if(p == myPlayer) continue;
            if(p.isMiniPlayer) {
                String playerName = myPlayer.getName();
                if(p.getName().startsWith("Mini " + playerName)) {
                    return p;
                }
            }
        }
        return myPlayer;
    }

    public static String capitalizeEachFirstLetter(String str) {

        String[] words = str.split(": ");

        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }
        return str = String.join(": ", words);
    }


/*
    public static final RichPresense RICH_PRESENCE = new RichPresense();
*/
    public static final int CACHE_INDEX_COUNT = 5;
    public static final int[] anIntArray1204 = {9104, 10275, 7595, 3610, 7975, 8526, 918, 38802, 24466, 10145, 58654, 5027, 1457, 16565, 34991, 25486};

    public static final int[][] anIntArrayArray1003 = {
        {6798, 107, 10283, 16, 4797, 7744, 5799, 4634, 33697, 22433, 2983, 54193},
        {8741, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003, 25239},
        {25238, 8742, 12, 64030, 43162, 7735, 8404, 1701, 38430, 24094, 10153, 56621, 4783, 1341, 16578, 35003},
        {4626, 11146, 6439, 12, 4758, 10270},
        {4550, 20165, 43678, 16895, 28416, 12231, 947, 60359, 32433}};// skins
    public final static int[] tabInterfaceIDs = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public static final String validUserPassChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"\243$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
    private static final int[] anIntArray1019;
    private static final int[] IDs = {1196, 1199, 1206, 1215, 1224, 1231, 1240, 1249, 1258, 1267, 1274, 1283, 1573, 1290, 1299, 1308, 1315, 1324, 1333, 1340, 1349, 1358, 1367, 1374, 1381, 1388, 1397, 1404, 1583, 12038, 1414, 1421, 1430, 1437, 1446, 1453, 1460, 1469, 15878, 1602, 1613, 1624, 7456, 1478, 1485, 1494, 1503, 1512, 1521, 1530, 1544, 1553, 1563, 1593, 1635, 12426, 12436, 12446, 12456, 6004, 18471,
            /* Ancients */
            12940, 12988, 13036, 12902, 12862, 13046, 12964, 13012, 13054, 12920, 12882, 13062, 12952, 13000, 13070, 12912, 12872, 13080, 12976, 13024, 13088, 12930, 12892, 13096};
    private static final Pattern NAME_PATTERN = Pattern.compile("@.+@");
    private static final int[] runeChildren = {1202, 1203, 1209, 1210, 1211, 1218, 1219, 1220, 1227, 1228, 1234, 1235,
            1236, 1243, 1244, 1245, 1252, 1253, 1254, 1261, 1262, 1263, 1270, 1271, 1277, 1278, 1279, 1286, 1287, 1293,
            1294, 1295, 1302, 1303, 1304, 1311, 1312, 1318, 1319, 1320, 1327, 1328, 1329, 1336, 1337, 1343, 1344, 1345,
            1352, 1353, 1354, 1361, 1362, 1363, 1370, 1371, 1377, 1378, 1384, 1385, 1391, 1392, 1393, 1400, 1401, 1407,
            1408, 1410, 1417, 1418, 1424, 1425, 1426, 1433, 1434, 1440, 1441, 1442, 1449, 1450, 1456, 1457, 1463, 1464,
            1465, 1472, 1473, 1474, 1481, 1482, 1488, 1489, 1490, 1497, 1498, 1499, 1506, 1507, 1508, 1515, 1516, 1517,
            1524, 1525, 1526, 1533, 1534, 1535, 1547, 1548, 1549, 1556, 1557, 1558, 1566, 1567, 1568, 1576, 1577, 1578,
            1586, 1587, 1588, 1596, 1597, 1598, 1605, 1606, 1607, 1616, 1617, 1618, 1627, 1628, 1629, 1638, 1639, 1640,
            6007, 6008, 6011, 8673, 8674, 12041, 12042, 12429, 12430, 12431, 12439, 12440, 12441, 12449, 12450, 12451,
            12459, 12460, 15881, 15882, 15885, 18474, 18475, 18478};
    private static final long serialVersionUID = -1913853327056220406L;
    private static final String VALID_AUTH_KEYS = "0123456789";
    public static boolean controlShiftTeleporting = false;
    public static int chatIncreaseY = 0, chatIncreaseX = 0;

    public static int cameraZoom = 600;
    public static final int MAX_CAMERA_ZOOM = 200;
    /* SHIFT DROPPING */
    public static boolean shiftIsDown = false;
    public static boolean controlIsDown = false;
    public static boolean shiftDrop = true;
    public static long aLong1432;
    public static int anInt1089;
    public static int anInt1211;
    public static int anInt1401 = 256;
    public static int anInt197;
    public static int[] anIntArray1232;
    public static int[] anIntArray385 = new int[]{12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800, 12800};
    public static int clientHeight = 503;
    public static int clientWidth = 765;
    public static boolean flagged;
    public static Client instance;
    public static int viewDistance = 9;
    public String secretKey = "ohitsakey";
    public String salt = "saltiness";
    public static int loopCycle;
    public static Player myPlayer;
    public static int openInterfaceID;
    public static Player mini;
    public static int portOff;
    public static HashMap<String, Boolean> options = new HashMap<String, Boolean>();
    public static boolean tabAreaAltered;
    public static int tabID;
    public static boolean LOOP_MUSIC;
    public boolean hovered = false;
    public static boolean consoleOpen;
    public static String inputString;
    public static int[] myHeadAndJaw = new int[2];
    public static boolean forceRegionLoad;
    public static int TotalRead = 0;
    public static String hash;
    public static String myUsername;
    public static int currentRegionX;
    public static int currentRegionY;
    public static boolean mousePressed;
    public static SpritesMap spritesMap = new SpritesMap();
    public static Sprite[] cacheSprite;
    public static boolean updateChatArea;
    private static boolean aBoolean475;
    private static boolean aBoolean995;
    private static byte[] aByteArray347;
    private static Class25 aClass25_1948;
    private static Class3_Sub7 aClass3_Sub7_1345;
    private static Class3_Sub7_Sub1 aClass3_Sub7_Sub1_1493;
    private static Class5 aClass5_932;
    private static Class56 aClass56_749;
    private static int anInt1005;
    private static int anInt1117;
    private static int anInt1134;
    private static int anInt1142;
    private static int anInt1155;
    private static int anInt116;
    private static int anInt1175;
    private static int anInt1188;
    private static int anInt1226;
    private static int anInt1288;
    private static int anInt139;
    private static int anInt1408;
    private static int anInt1478;
    private static int anInt1526;
    private static int anInt155 = 0;
    private static int anInt2200 = 0;
    private static int anInt478 = -1;
    private static int anInt720 = 0;
    private static int anInt849;
    private static int anInt854;
    private static int anInt924;
    private static int anInt986;
    private static boolean fetchMusic = false;
    private static boolean isMembers = true;
    private static final ArrayList<Character> letterArray = new ArrayList<>();
    private static boolean lowDetail;
    private static byte[] musicData;
    private static int musicVolume2;
    private static int nodeID = 10;
    private static Stream out;
    private static int soundEffectVolume = 127;
    private static int spellID = 0;
    private static RSInterface childHovered = null;
    /**
     * The line ids in order
     */
    private static final int[] lines = {640, 663, 7332, 7333, 7334, 7336, 7383, 7339, 7338, 7340, 7346, 7341, 7342, 7337, 7343, 7335, 7344, 7345, 7347, 7348, 682, 12772, 673, 7352, 17510, 7353, 12129, 8438, 12852, 15841, 7354, 7355, 7356, 8679, 7459, 16149, 6987, 7357, 12836, 7358, 7359, 14169, 10115, 14604, 7360, 12282, 13577, 12839, 7361, 16128, 11857, 7362, 7363, 7364, 10135, 4508, 18517, 11907, 7365, 7366, 7367, 13389, 15487, 7368, 11132, 7369, 12389, 13974, 6027, 7370, 8137, 7371, 12345, 7372, 8115, 18684, 15499, 18306, 668, 8576, 12139, 14912, 7373, 7374, 8969, 15352, 7375, 7376, 15098, 15592, 249, 1740, 15235, 3278, 7378, 6518, 7379, 7380, 7381, 11858, 191, 9927, 6024, 7349, 7350, 7351, 13356

    };
    /*
        private String getMac() throws SocketException {
            getMac1();        if (isMac() || isUnix()) {
                return "MAC USER";
            }
            InetAddress ip;
            String macA = null;
            try {

                ip = InetAddress.getLocalHost();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);

                byte[] mac = network.getHardwareAddress();
                System.out.println("mac: " + mac);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                macA = sb.toString();

            } catch (UnknownHostException e) {

                e.printStackTrace();

            } catch (SocketException e) {

                e.printStackTrace();

            }

            System.out.println("NetworkInterface.getNetworkInterfaces().nextElement(): " +
                    NetworkInterface.getNetworkInterfaces().nextElement());
            return macA;
        }*/
    private static final String OS = System.getProperty("os.name").toLowerCase();

    static {
        anIntArray1019 = new int[99];
        int points = 0;

        for (int i = 0; i < 99; i++) {
            int l = i + 1;
            int i1 = (int) (l + 300D * Math.pow(2D, l / 7D));
            points += i1;
            anIntArray1019[i] = points >> 2;
        }

        anIntArray1232 = new int[32];
        points = 2;

        for (int i = 0; i < 32; i++) {
            anIntArray1232[i] = points - 1;
            points += points;
        }
    }

    public final int[] anIntArray990;
    public final String[] atPlayerActions;
    public final boolean[] atPlayerArray;
    public final byte[] chatRights;
    public final byte[] chatIronman;
    public final byte[] chatDiff;
    public final int[] compassArray1;
    public final int[] compassArray2;
    public final int[] currentExp;
    public final int[] currentStats;
    public final Decompressor[] decompressors;
    public final int[] mapImagePixelCutLeft;
    public final int[] mapImagePixelCutRight;
    public final int[] maxStats;
    public final Sprite[] modIcons;
    public int dropdownInversionFlag;
    public final int REGULAR_WIDTH = 765, REGULAR_HEIGHT = 503, RESIZABLE_WIDTH = 800, RESIZABLE_HEIGHT = 600;
    public final int[] chatColor = new int[500];
    public final int[] curseLevelRequirements = {1, 30, 55, 85, 120, 1, 30, 55, 85, 120, 1, 30, 55, 85, 120, 1, 1, 1, 120, 120};
    public final String[] curseName = {"Gaia's Blessing", "Serenity", "Rockheart", "Geovigor", "Stonehaven", "Ebb & Flow", "Aquastrike", "Riptide", "SeaSlicer", "Swifttide", "Cinder's Touch", "Emberblast", "Infernify", "Blazeup", "Inferno", "Protect Melee", "Protect Range", "Protect Magic", "Malevolence", "Desolation"};
    public final int[] prayerLevelRequirements = {1, 4, 7, 8, 9, 10, 13, 16, 19, 22, 25, 26, 27, 28, 31, 34, 37, 40, 43, 44, 45, 46, 49, 52, 60, 70, 80, 80};
    public final String[] prayerName = {"Thick Skin", "Burst of Strength", "Clarity of Thought", "Sharp Eye", "Mystic Will", "Rock Skin", "Superhuman Strength", "Improved Reflexes", "Rapid Restore", "Rapid Heal", "Protect Item", "Hawk Eye", "Mystic Lore", "Steel Skin", "Ultimate Strength", "Incredible Reflexes", "Protect from Magic", "Protect from Missiles", "Protect from Melee", "Eagle Eye", "Mystic Might", "Retribution", "Redemption", "Smite", "Chivalry", "Piety", "Rigour", "Augury"};
    private final String[] consoleMessages;
    private final boolean aBoolean848;
    private final boolean aBoolean994;
    private final boolean[] QuakeDirectionActive;
    private final RSInterface aClass9_1059;
    private final int anInt975;
    private final int[] QuakeTimes;
    private final int[] settings;
    private final int[] myAppearance;
    private final int[] anIntArray1177 = {0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
    private final int[] QuakeAmplitudes;
    private final int[] QuakeMagnitudes;
    private final int[] Quake4PiOverPeriods;
    private final int[] anIntArray965 = {0xffff00, 0xff0000, 65280, 65535, 0xff00ff, 0xffffff};
    private final int[] anIntArray976;
    private final int[] anIntArray977;
    private final int[] anIntArray978;
    private final int[] anIntArray979;
    private final int[] anIntArray980;
    private final int[] anIntArray981;
    private final int[] anIntArray982;
    private final int[][][] constructRegionData;
    private final String[] aStringArray983;
    private final CRC32 crc32Instance;
    private final int[] expectedCRCs;
    private final long[] ignoreListAsLongs;
    private final MapArea mapArea = new MapArea(519, 0, 0, 0);
    private final int maxPlayers;
    private final int myPlayerIndex;
    private final Sprite[] scrollBar;
    private final Sprite[] scrollPart;
    private final String[] menuPlayerName = new String[500];
    /**
     * Handles the login for the player
     *
     * @param username     The username of the player
     * @param password     The password if the player
     * @param reconnecting The player is reconnecting
     */
    private final Stopwatch clickDelay = new Stopwatch();
    public Sprite alertBack;
    public Sprite alertBorder;
    public Sprite alertBorderH;
    public AlertManager alertManager = new AlertManager(this);
    public AlertHandler alertHandler;
    /* Modifable x */
    public int modifiableXValue;
    /**
     * The split chat color, by default its 65535
     */
    public int splitChatColor = 0;
    public int clanChatColor = 0;
    public int healthpoints_bar_color = 0;
    /**
     * Is looting bag open
     */
    public boolean lootingBag = false;
    /**
     * The split chat interface is open
     */
    public boolean splitChatInterfaceOpen = false;
    public ArrayList<Partyhat> active_list = new ArrayList<>();
    public ArrayList<CustomMinimapIcon> customMinimapIcons = new ArrayList<CustomMinimapIcon>();
    public boolean doingDungeoneering;
    public boolean isMale;
    public boolean aBoolean1149;
    public boolean aBoolean954;
    public Sprite[] aClass30_Sub2_Sub1_Sub1Array1140;
    private static Sound[] aClass26Array1468 = new Sound[50];
    private static Sound aClass1418;
    public String amountOrNameInput;
    public int anInt1009;
    public int anInt1011;
    public int minimapStatus;
    public int anInt1054;
    public int drawMultiwayIcon;
    public int drawXPwayIcon;
    public int anInt1071;
    public int systemUpdateTimer;
    public int hintIconNpcId;
    public int anInt841;
    public int previousPacket;
    public int anInt843;
    public int hintIconDrawType;
    public int hintIconPlayerId;
    public int hintIconX;
    public int hintIconY;
    public int[] anIntArray1072;
    public int[] anIntArray1073;
    public int[] anIntArray1180;
    public int[] anIntArray1181;
    public int[] anIntArray1182;
    public String aString844;
    public int backDialogID;
    public int regionBaseX;
    public int regionBaseY;
    public int cButtonCPos;
    public int cButtonHPos;
    public ProducingGraphicsBuffer chatAreaIP;
    public String[] chatMessages;
    public LocalDateTime[] chatTimes;
    public String[] chatNames;
    public String[] chatTitles;
    public int[] chatPosition;
    public TextDrawingArea boldText;
    public TextDrawingArea aTextDrawingArea_1273;
    public int[] chatTypes;
    public int chatTypeView;
    public String[] clanMembers = new String[100];
    public String clanName;
    public Sprite compass;
    public int destinationX;
    public int destinationY;
    public int dialogID;
    public int energy = 100;
    public int friendCount;
    public long[] friendsListAsLongs;
    public int[] friendsNodeIDs;
    public ProducingGraphicsBuffer gameScreenIP;
    public NodeList[][][] groundArray;
    public int inputDialogState;
    public int invOverlayInterfaceID;
    public int itemSelected;
    public int loadingStage;
    public static boolean loggedIn;
    public ProducingGraphicsBuffer mapAreaIP;
    public Sprite mapDotClan;
    public Sprite mapDotFriend;
    public Sprite mapDotItem;
    public Sprite mapDotNPC;
    public Sprite mapDotPlayer;
    public Sprite mapDotTeam;
    public Sprite mapFlag;
    public Sprite newMapIconshit;
    public Sprite mapMarker;
    public int[] menuActionID;
    public String[] menuActionName;
    public String[] menuActionTitle;
    public int[] menuActionColor;
    public int menuActionRow;
    public boolean menuOpen;
    public int menuScreenArea;
    public boolean messagePromptRaised;
    public Sprite miniMapRegions;
    public int minimapRotation;
    public int minimapZoom;
    public int myRights;
    public int gameMode;
    public int myDiff;
    public RSFontSystem newSmallFont, newRegularFont, newBoldFont, newFancyFont;
    public TextDrawingArea normalText;
    public Npc[] npcArray;
    public int npcCount;
    public int[] npcIndices;
    public OnDemandFetcher onDemandFetcher;
    public int pktSize;
    public int opCode;
    public int plane;
    public Player[] playerArray;
    public int playerCount;
    public int[] playerIndices;
    public int privateChatMode;
    public String promptInput;
    public String promptMessage;
    public int publicChatMode;
    public int reportAbuseInterfaceID;
    public TextDrawingArea smallText;
    public int spellSelected;
    public int splitPrivateChat = 1;
    public TabArea tabArea = new TabArea(516, 168, 250, 335);
    public ProducingGraphicsBuffer tabAreaIP;
    public int terrainRegionX;
    public int terrainRegionY;
    public int tradeMode;
    public int cameraRotation;
    public int xCameraCurve;
    public int xCameraPos;
    public int yCameraCurve;
    public int yCameraPos;
    public int zCameraPos;
    public boolean autoCast = false;
    public int autocastId = 0;
    public boolean oksearchingplayers = false;
    public boolean oksearchingitems = false;
    public RSInterface inputFieldSelected = null;
    public String inputTitle = null;
    public boolean shiftLogin;
    public int duelStatus;
    public int gameScreenDrawX = 4, gameScreenDrawY = 4;
    public CursorData oldCursor;
    private final GrandExchange grandExchange;
    private final boolean vengToggle = true;
    private final boolean drawFlames;
    public int[] quickPrayers = new int[28];
    public int[] quickCurses = new int[20];
    public int prayerInterfaceType;
    public int[] quickConfigIDs = {630, 631, 632, 633, 634, 635, 636, 637, 638, 639, 640, 641, 642, 643, 644, 645, 646, 647, 648, 649, 650, 651, 652, 653, 654, 655, 658, 659};
    public int lastItemHover = -1;
    public boolean hoverShowing = false;
    public String password;
    public boolean rememberUsername;
    public boolean rememberPassword;
    ChatArea chatArea = new ChatArea(0, 338, 516, 150);
    ArrayList<RSInterface> parallelWidgetList = new ArrayList<>();
    boolean loginHover;
    public boolean rememberMeHover;
    public boolean storeHover;
    public boolean discordHover;
    public boolean voteHover;
    public boolean websiteHover;
    boolean textArea1Hover, textArea2Hover;
    boolean backButtonHover;
    boolean[] accountHovers = new boolean[3];
    boolean[] accountDeletion = new boolean[3];
    int lastNpcAmt = 0;
    int skillTabHoverChild;

    int widgetHoverChild;
    int donationTabHoverChild;
    int milestoneHoverComp;
    boolean hovering;
    int pissing_me_off_x;
    int pissing_me_off_y;
    EntityDef npcDisplay = null;
    boolean otherHover;
    private int broadcastMinutes;
    private String broadcastMessage;
    private Sprite stock;
    private final java.util.List<Particle> currentParticles;
    private final java.util.List<Particle> deadParticles;
    private long updateVengTime = 0;
    private long clientId;
    private Sprite mapIcon;
    private boolean pinEnter = false;
    private boolean confirmEnter = false;
    private String consoleInput;
    private int mapX, mapY;
    private int vengTimer = -1;
    private long lastUpdate;
    private final BufferedImage[] loadingImages;
    private int cutCounter = 0;
    private int cutAmount = 0;
    private boolean aBoolean1017;
    private boolean aBoolean1031;
    private boolean aBoolean1080;
    private boolean aBoolean1141;
    private boolean requestMapReconstruct;
    private boolean oriented;
    private boolean aBoolean1242;
    private volatile boolean aBoolean831;
    private boolean httpFallback;
    private boolean aBoolean972;
    private int[] aByteArray912;
    private byte[][] terrainData;
    private byte[][] objectData;
    private CollisionMap[] collisionData;
    private NodeList aClass19_1013;
    private NodeList aClass19_1056;
    private NodeList aClass19_1179;
    private Sprite aClass30_Sub2_Sub1_Sub1_931;
    private Sprite aClass30_Sub2_Sub1_Sub1_932;
    private int activeInterfaceType;
    private long aLong824;
    private long aLong953;
    private int anInt1010;
    private int currentCameraDisplayX;
    private int currentCameraDisplayY;
    private int anInt1016;
    private int walkableInterfaceId;
    private int anInt1026;
    private int anInt1036;
    private int anInt1037;
    private int anInt1039;
    private int anInt1044;// 377
    private int anInt1046;
    private int anInt1048;
    private int anInt1079;
    private int modifiedWidgetId;
    private int selectedInventorySlot;
    private int anInt1087;
    private int anInt1088;
    private int spinPacketX;
    private int spinPacketY;
    private int spinPacketHeight;
    private int spinPacketConstantSpeed;
    private int spinPacketVariableSpeed;
    private int anInt1129;// 377
    private int selectedSpellId;
    private int cameraRotationZ;
    private int cameraRotationLeft;
    private int cameraRotationRight;
    private int anInt1193;
    private int anInt1213;
    private int anInt1249;
    private int anInt1251;
    private int anInt1253;
    private int anInt1264;
    private int renderCycle;
    private int anInt1268;
    private int anInt1269;
    private int anInt1283;
    private int anInt1284;
    private int selectedItemId;
    private int anInt1315;// 377
    private int anInt1500;// 377
    private int anInt1501;// 377
    private int anInt839;
    private int anInt886;
    private int playersToUpdateCount;
    private int anInt900;
    private int anInt913;
    private int hintIconZ;
    private int hintIconLocationArrowRelX;
    private int hintIconLocationArrowRelY;
    private int anInt945;
    private int curveChangeY;
    private int lastKnownPlane;
    private int anInt989;
    private int moveCameraX;
    private int moveCameraY;
    private int moveCameraZ;
    private int moveCameraSpeed;
    private int moveCameraAngle;
    private int[] mapCoordinates;
    private int[] anIntArray840;
    private int[] playersToUpdate;
    private int[][] distances;
    private int[][] anIntArrayArray901;
    private int[][] anIntArrayArray929;
    private int[] unlockedItems = {};
    private ProducingGraphicsBuffer aProducingGraphicsBuffer_1107;
    private ProducingGraphicsBuffer aProducingGraphicsBuffer_1125;
    private java.net.Socket aSocket832;
    private Stream aStream_834;
    private Stream[] aStreamArray895s;
    private int atInventoryIndex;
    private int atInventoryInterface;
    private int atInventoryInterfaceType;
    private int atInventoryLoopCycle;
    private int[] waypointX;
    private int[] waypointY;
    private byte[][][] tileFlags;
    private int cameraOffsetX;
    private int cameraOffsetY;
    private Socket socket;
    private ISAACRandomGen connectionCipher;
    private Sprite[] crosses;
    private int crossIndex;
    private int crossType;
    private int crossX;
    private int crossY;
    private int currentSong;
    private int daysSinceLastLogin;
    private int daysSinceRecovChange;
    private int drawCount;
    private final int[][] drawTimerPos = new int[][]{{475, 90}, {-40, 225}, {260, 334} // center =
            // http://i.imgur.com/R89oEM9.png
    };
    private volatile boolean drawingFlames;
    private int[] floorMap;
    private String floorMaps = "";
    private String[] friendsList;
    private int friendsListAction;
    private int fullscreenInterfaceID;
    private int[] fullScreenTextureArray;
    private int gameAreaWidth = 512, gameAreaHeight = 334;
    private boolean gameFrameVisible = true;
    private Sprite[] headIcons;
    private Sprite[] headIconsHint;
    private int ignoreCount;
    private Stream inputBuffer;
    private int interfaceButtonAction = 0;
    private int[][][] tileHeights;
    private int lastActiveInvInterface;
    private ProducingGraphicsBuffer leftFrame;
    private boolean loadingError;
    private Stream loginBuffer;
    private int loginFailures;
    //private String loginMessage1;
    //private String loginMessage2;
    private String[] loginMessages = new String[]{""};
    private int loginScreenCursorPos;
    private int loginScreenState;
    private int loginState = -1;
    private Background mapBack;
    private Sprite mapEdge;
    private Sprite vengBar;
    private Sprite[] mapFunctions;
    private Background[] mapScenes;
    private int membersInt;
    public int[] menuActionCmd1;
    public int[] menuActionCmd2;
    public int[] menuActionCmd3;
    private int menuHeight;
    private int menuOffsetX;
    private int menuOffsetY;
    private int menuWidth;
    private String message;
    private int mouseInvInterfaceIndex;
    private Sprite multiOverlay;
    private Sprite XPOverlay;
    private int musicVolume = 64;
    private String name;
    private int nextSong;
    private int[] objectMap;
    private String objectMaps = "";
    private int prevSong;
    private ProducingGraphicsBuffer rightFrame;
    private int rights;
    private boolean running;
    private RS2MapLoginRenderer RS2MapLoginRenderer;
    private String selectedItemName;
    private long serverSeed;
    private Sprite[] skullIcons;

    private final int[] sound;

    public int soundCount;
    private final int[] soundDelay;
    private final int[] soundType;
    private String spellTooltip;
    private int spellUsableOn;
    private int spriteDrawX;
    private int spriteDrawY;
    private int titleAlpha;
    private int titleHeight = -1;
    private ProducingGraphicsBuffer titleScreenIP;
    private int[] titleScreenOffsets = null;
    private Archive titleStreamLoader;
    private int titleWidth = -1;
    private ProducingGraphicsBuffer topFrame;
    private int playerId;
    private int unreadMessages;
    private int viewRotationOffset;
    private int weight;
    private boolean welcomeScreenRaised;
    private Scene scene;
    private boolean fpsOn;
    private boolean debug;
    private Sprite bankItemDragSprite;
    private int bankItemDragSpriteX, bankItemDragSpriteY;
    private long rotateTimer = System.currentTimeMillis();
    private final int[] defPray = {0, 5, 13, 24, 25, 26, 27};
    private final int[] strPray = {1, 3, 4, 6, 11, 12, 14, 19, 20, 24, 25, 26, 27};

    private int currentActionMenu;
    private int loadingPercentage;
    private final int[] atkPray = {2, 3, 4, 7, 11, 12, 15, 19, 20, 24, 25, 26, 27};
    private int loginFade;
    private boolean resizing;
    private boolean withdrawingMoneyFromPouch;
    private int[] menuActionCmd4;
    private boolean isLoading;
    private final int[] rangeAndMagePray = {3, 4, 11, 12, 19, 20, 24, 25, 26, 27};
    private int showClanOptions;
    private boolean loggingIn = false;
    private boolean hintMenu;
    private String hintName;
    private String hintDescripter;
    private DummyItem[] hintItems;
    private int hintId;
    private final int[] rangeAndMagePrayOff = {1, 2, 3, 4, 6, 7, 11, 12, 14, 15, 19, 20, 24, 25, 26, 27};
    private final int[] headPray = {16, 17, 18, 21, 22, 23};
    private final int[] superiorPray = {0, 1, 2, 3, 4, 5, 6, 7, 11, 12, 13, 14, 15, 19, 20, 24, 25, 26, 27};
    private final int[][] prayer = {defPray, strPray, atkPray, rangeAndMagePray, headPray};
    private final int[] protect_prayers = {15, 16, 17};
    private final int[] first_row = {0, 5, 10};
    private final int[] second_row = {1, 6, 11,};
    private final int[] third_row = {2, 7 ,12};
    private final int[] fourth_row = {3, 8, 13, 18 };
    private final int[] fifth_row = {4, 9, 14, 19 };
    private final CasketOpening newmapsopening;
    public CardPack cardPack;
    private final int currencyImageAmount = 13;
    private final Sprite[] currencyImage = new Sprite[currencyImageAmount];
    private final int boxImageAmount = 6;
    private Actor currentTarget;

    public static boolean isHorizontalLayout = false;

    private int itemHover = -1;
    private final Sprite[] boxImage = new Sprite[boxImageAmount];
    private int pinCode = -1;
    private boolean showTwoFactorAuth;
    private boolean showCaptcha;
    private String currentPhoneNumber;
    private String currentPinCode = "";
    private Sprite captcha;
    private String serial = "null";
    public int[] variousSettings;
    public int[] positions = new int[2000];
    public int[] landScapes = new int[2000];
    public int[] objects = new int[2000];
    public boolean hovers;

    private UpgradeInterfaceSpinner upgradeInterfaceSpinner;

    public Client() {
        upgradeInterfaceSpinner = new UpgradeInterfaceSpinner();
        newmapsopening = new CasketOpening();
        cardPack = new CardPack();
        currentParticles = new ArrayList<>();
        deadParticles = new ArrayList<>();
        grandExchange = new GrandExchange();
        loadingImages = new BufferedImage[4];
        menuActionCmd4 = new int[500];
        setFullscreenInterfaceID(-1);
        alertHandler = new AlertHandler(this);
        chatRights = new byte[500];
        chatIronman = new byte[500];
        chatDiff = new byte[500];
        chatTypeView = 0;
        cButtonHPos = -1;
        cButtonCPos = 0;
        distances = new int[104][104];
        crc32Instance = new CRC32();
        groundArray = new NodeList[4][104][104];
        aBoolean831 = false;
        aStream_834 = new Stream(new byte[5000]);
        npcArray = new Npc[50000];
        npcIndices = new int[50000];
        anIntArray840 = new int[1000];
        setLoginBuffer(Stream.create());
        aBoolean848 = true;
        openInterfaceID = -1;
        currentExp = new int[Skills.SKILL_COUNT];
        httpFallback = false;
        QuakeMagnitudes = new int[5];
        QuakeDirectionActive = new boolean[5];
        drawFlames = false;
        playerId = -1;
        menuOpen = false;
        inputString = "";
        maxPlayers = 2048;
        myPlayerIndex = 2047;
        friendsNodeIDs = new int[200];
        playerArray = new Player[getMaxPlayers()];
        playerIndices = new int[getMaxPlayers()];
        playersToUpdate = new int[getMaxPlayers()];
        setaStreamArray895s(new Stream[getMaxPlayers()]);
        anIntArrayArray901 = new int[104][104];
        aByteArray912 = new int[16384];
        currentStats = new int[Skills.SKILL_COUNT];
        ignoreListAsLongs = new long[100];
        loadingError = false;
        Quake4PiOverPeriods = new int[5];
        anIntArrayArray929 = new int[104][104];
        chatTypes = new int[500];
        chatNames = new String[500];
        chatMessages = new String[500];
        chatTimes = new LocalDateTime[500];
        chatTitles = new String[500];
        chatPosition = new int[500];
        Arrays.fill(chatTitles, "");
        Arrays.fill(chatPosition, 0);
        Arrays.fill(chatColor, 0);
        aBoolean954 = true;
        friendsListAsLongs = new long[200];
        friendsList = new String[200];
        currentSong = -1;
        drawingFlames = false;
        spriteDrawX = -1;
        spriteDrawY = -1;
        compassArray1 = new int[33];
        decompressors = new Decompressor[CACHE_INDEX_COUNT];
        variousSettings = new int[9000];
        aBoolean972 = false;
        anInt975 = 79;
        anIntArray976 = new int[anInt975];
        anIntArray977 = new int[anInt975];
        anIntArray978 = new int[anInt975];
        anIntArray979 = new int[anInt975];
        anIntArray980 = new int[anInt975];
        anIntArray981 = new int[anInt975];
        anIntArray982 = new int[anInt975];
        aStringArray983 = new String[anInt975];
        setLastKnownPlane(-1);
        anIntArray990 = new int[5];
        aBoolean994 = false;
        amountOrNameInput = "";
        setaClass19_1013(new NodeList());
        aBoolean1017 = false;
        setWalkableInterfaceId(-1);
        QuakeTimes = new int[5];
        aBoolean1031 = false;
        mapFunctions = new Sprite[100];
        dialogID = -1;
        maxStats = new int[Skills.SKILL_COUNT];
        settings = new int[10000]; // use up from 2000 for custom client configs
        isMale = true;
        mapImagePixelCutLeft = new int[152];
        anInt1054 = -1;
        setaClass19_1056(new NodeList());
        compassArray2 = new int[33];
        aClass9_1059 = new RSInterface();
        mapScenes = new Background[100];
        myAppearance = new int[7];
        anIntArray1072 = new int[1000];
        anIntArray1073 = new int[1000];
        aBoolean1080 = false;
        setInputBuffer(Stream.create());
        expectedCRCs = new int[9];
        menuActionCmd2 = new int[500];
        menuActionCmd3 = new int[500];
        menuActionID = new int[500];
        menuActionCmd1 = new int[500];
        headIcons = new Sprite[20];
        skullIcons = new Sprite[7];
        headIconsHint = new Sprite[20];
        scrollPart = new Sprite[12];
        scrollBar = new Sprite[6];
        tabAreaAltered = false;
        promptMessage = "";
        atPlayerActions = new String[7]; // oh i already implemented support for 6 for u, guess we need 7th then
        atPlayerArray = new boolean[7];
        constructRegionData = new int[4][13][13];
        aClass30_Sub2_Sub1_Sub1Array1140 = new Sprite[1000];
        aBoolean1141 = false;
        aBoolean1149 = false;
        crosses = new Sprite[8];
        loggedIn = false;
        requestMapReconstruct = false;
        oriented = false;
        myUsername = "";

        setPassword("");
        reportAbuseInterfaceID = -1;
        setaClass19_1179(new NodeList());
        cameraRotationZ = 128;
        invOverlayInterfaceID = -1;
        setOut(Stream.create());
        menuActionName = new String[500];
        menuActionTitle = new String[500];
        menuActionColor = new int[500];
        QuakeAmplitudes = new int[5];
        sound = new int[50];
        anInt1211 = 78;
        promptInput = "";
        modIcons = new Sprite[17];
        tabID = 3;
        setInputTaken(false);
        mapImagePixelCutRight = new int[152];
        collisionData = new CollisionMap[4];
        soundType = new int[50];
        aBoolean1242 = false;
        soundDelay = new int[50];
        welcomeScreenRaised = false;
        messagePromptRaised = false;
        backDialogID = -1;
        consoleInput = "";
        consoleOpen = false;
        consoleMessages = new String[50];
        waypointX = new int[4000];
        waypointY = new int[4000];
        loginMessages = new String[]{""};
    }

    public static boolean getOption(String s) {
        return options.get(s).booleanValue();
    }

    public static String getFileNameWithoutExtension(String fileName) {
        File tmpFile = new File(fileName);
        tmpFile.getName();
        int whereDot = tmpFile.getName().lastIndexOf('.');
        if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2) {
            return tmpFile.getName().substring(0, whereDot);
        }
        return "";
    }

    public static String capitalize(String s) {
        return s.length() > 0 ? Character.toUpperCase(s.charAt(0)) + s.substring(1) : s;
    }

    private static String combatDiffColor(int myCombatLevel, int targetCombatLevel) {
        int difference = myCombatLevel - targetCombatLevel;

        if (difference < -9) {
            return "@red@";
        }

        if (difference < -6) {
            return "@or3@";
        }

        if (difference < -3) {
            return "@or2@";
        }

        if (difference < 0) {
            return "@or1@";
        }

        if (difference > 9) {
            return "@gre@";
        }

        if (difference > 6) {
            return "@gr3@";
        }

        if (difference > 3) {
            return "@gr2@";
        }

        if (difference > 0) {
            return "@gr1@";
        }

        return "@yel@";
    }

    private static final boolean constructMusic() {
        anInt720 = 20;

        try {
            aClass56_749 = new Class56_Sub1_Sub1();
        } catch (Throwable throwable) {
            return false;
        }

        return true;
    }

    private static final void handleSounds() {
        if (aClass5_932 != null) {
            long l = System.currentTimeMillis();

            if (l > aLong1432) {
                aClass5_932.method489(l);
                int i_0_ = (int) (-aLong1432 + l);
                aLong1432 = l;

                synchronized (Client.aClass1418 != null ? Client.aClass1418 : (Client.aClass1418 = new Sound())) {
                    anInt1526 += anInt197 * i_0_;
                    int i_1_ = (anInt1526 - anInt197 * 2000) / 1000;

                    if (i_1_ > 0) {
                        if (aClass3_Sub7_1345 != null) {
                            aClass3_Sub7_1345.method380(i_1_);
                        }

                        anInt1526 -= i_1_ * 1000;
                    }
                }
            }
        }
    }

    private static String formatValue(double value, int digits) {
        PlayerUtilities.format.setMaximumFractionDigits(digits);
        return PlayerUtilities.format.format(value);
    }

    private static String intToKOrMil(int value) {
        if (value < 0x186a0) {
            return String.valueOf(value);
        }

        if (value < 0x989680) {
            return value / 1000 + "K";
        } else {
            return value / 0xf4240 + "M";
        }
    }

    private static String intToKOrMilLongName(int i) {
        String s = String.valueOf(i);

        for (int k = s.length() - 3; k > 0; k -= 3) {
            s = s.substring(0, k) + "," + s.substring(k);
        }

        if (s.length() > 8) {
            s = "@gre@" + s.substring(0, s.length() - 8) + " million @whi@(" + s + ")";
        } else if (s.length() > 4) {
            s = "@cya@" + s.substring(0, s.length() - 4) + "K @whi@(" + s + ")";
        }

        return " " + s;
    }

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            try {
                    JFrame.setDefaultLookAndFeelDecorated(false);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    //UIManager.setLookAndFeel(new SubstanceOfficeBlack2007LookAndFeel());

            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
        portOff = 0;
        if (!Configuration.HIGH_DETAIL) {
            setHighDetail();
        } else {
            setHighDetail();
        }
        isMembers = true;
        Signlink.storeid = 32;
        try {
            Signlink.startpriv(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            System.err.printf("Unable to determine localhost for your machine [localhost=%s]%n", e.getMessage());
        }
        GameFrame.setScreenMode(ScreenMode.FIXED);
        instance = new Client();
        instance.createClientFrame(clientWidth, clientHeight);
    }

    public static Client getClient() {
        return instance;
    }

    private static int method1004(int i) {
        return (int) (Math.log(i * 0.00390625) * 868.5889638065036 + 0.5);
    }

    private static void method368(int i) {
        if (aClass56_749 != null) {
            if (anInt478 < i) {
                if (anInt720 > 0) {
                    anInt720--;

                    if (anInt720 == 0) {
                        if (aByteArray347 == null) {
                            aClass56_749.method831(256);
                        } else {
                            aClass56_749.method831(anInt1478);
                            anInt478 = anInt1478;
                            aClass56_749.method827(anInt1478, aByteArray347, 0, aBoolean475);
                            aByteArray347 = null;
                        }

                        anInt155 = 0;
                    }
                }
            } else if (anInt720 > 0) {
                anInt155 += anInt2200;
                aClass56_749.method830(anInt478, anInt155);
                anInt720--;

                if (anInt720 == 0) {
                    aClass56_749.method833();
                    anInt720 = 20;
                    anInt478 = -1;
                }
            }

            aClass56_749.method832(i - 122);
        }
    }

    private static final Class3_Sub7_Sub1 method407(Component component) {
        Client.method509(component);
        Class3_Sub7_Sub1 class3_sub7_sub1 = new Class3_Sub7_Sub1();
        method484(class3_sub7_sub1);
        return class3_sub7_sub1;
    }

    private static final synchronized void method484(Class3_Sub7 class3_sub7) {
        aClass3_Sub7_1345 = class3_sub7;
    }

    public static final synchronized void method486(int[] is, int i) {
        int i_2_ = 0;
        i -= 7;

        while (i_2_ < i) {
            is[i_2_++] = 0;
            is[i_2_++] = 0;
            is[i_2_++] = 0;
            is[i_2_++] = 0;
            is[i_2_++] = 0;
            is[i_2_++] = 0;
            is[i_2_++] = 0;
            is[i_2_++] = 0;
        }

        i += 7;

        while (i_2_ < i) {
            is[i_2_++] = 0;
        }

        if (aClass3_Sub7_1345 != null) {
            aClass3_Sub7_1345.method378(is, 0, i);
        }

        method689(i);
    }

    private final void method90() {
        for (int index = 0; soundCount > index; index++) {
            soundDelay[index]--;

            if (soundDelay[index] < -10) {
                soundCount--;

                for (int lastSound = index; soundCount > lastSound; lastSound++) {
                    sound[lastSound] = sound[lastSound + 1];
                    aClass26Array1468[lastSound] = aClass26Array1468[lastSound + 1];
                    soundType[lastSound] = soundType[lastSound + 1];
                    soundDelay[lastSound] = soundDelay[lastSound + 1];
                }

                index--;
            } else {
                Sound class26 = aClass26Array1468[index];

                if (class26 == null) {
                    class26 = Sound.cache[sound[index]];

                    if (class26 == null) {
                        continue;
                    }

                    soundDelay[index] += class26.method652();
                    aClass26Array1468[index] = class26;
                }

                if (soundDelay[index] < 0) {
                    Class3_Sub9_Sub1 class3_sub9_sub1 = class26.method651().method405(aClass25_1948);
                    Class3_Sub7_Sub2 class3_sub7_sub2 = Class3_Sub7_Sub2.method396(class3_sub9_sub1, 100, soundEffectVolume);
                    class3_sub7_sub2.method394(soundType[index] - 1);
                    aClass3_Sub7_Sub1_1493.method384(class3_sub7_sub2);
                    soundDelay[index] = -100;
                }
            }
        }

        if (prevSong > 0) {
            prevSong -= 20;

            if (prevSong < 0) {
                prevSong = 0;
            }

            if (prevSong == 0 && musicVolume != 0 && currentSong != -1) {
                method56(musicVolume, false, currentSong);
            }
        }
    }

    private static synchronized void method49() {
        if (musicIsntNull()) {
            if (fetchMusic) {
                byte[] is = musicData;

                if (is != null) {
                    if (anInt116 >= 0) {
                        method684(aBoolean995, anInt116, musicVolume2, is);
                    } else if (anInt139 >= 0) {
                        method899(anInt139, -1, aBoolean995, is, musicVolume2);
                    } else {
                        method853(musicVolume2, is, aBoolean995);
                    }

                    fetchMusic = false;
                }
            }

            method368(0);
        }
    }

    public void sendClientSettings() {
        getOut().putOpcode(243);
        getOut().putByte(ClientSettings.newFunctionKeys);
        getOut().putByte(ClientSettings.newHealthBars);
        getOut().putByte(ClientSettings.newHitmarks);
        getOut().putByte(ClientSettings.newCursors);
        getOut().putByte(ClientSettings.usernamesAboveHead);
        getOut().putByte(ClientSettings.toggleParticles);
        getOut().putByte(ClientSettings.groundItemNames);
        getOut().putByte(ClientSettings.usernameHighlighting);
        getOut().putByte(ClientSettings.levelUpMessages);
        getOut().putByte(ClientSettings.placeholder);

        getOut().putInt(ClientSettings.viewDistance);
        getOut().putInt(ClientSettings.textureAnimationSpeed);
        getOut().putInt(ClientSettings.fogStartValue);
        getOut().putInt(ClientSettings.fogColor);
    }

    public static final synchronized void method493(int i) {
        if (aClass3_Sub7_1345 != null) {
            aClass3_Sub7_1345.method380(i);
        }

        method689(i);
    }

    private static final void method509(Component component) {
        try {
            Class5_Sub2 class5_sub2 = new Class5_Sub2_Sub2();
            class5_sub2.method502(2048);
            aClass5_932 = class5_sub2;
        } catch (Throwable throwable) {
            try {
                aClass5_932 = new Class5_Sub2_Sub1(component);
            } catch (Throwable throwable_16_) {
                do {
                    if (System.getProperty("java.vendor").toLowerCase().indexOf("microsoft") >= 0) {
                        try {
                           // aClass5_932 = new Class5_Sub1();
                        } catch (Throwable throwable_17_) {
                            break;
                        }

                        return;
                    }
                } while (false);

                aClass5_932 = new Class5(8000);
            }
        }
    }

    private static final synchronized void method55(boolean bool) {
        if (musicIsntNull()) {
            method891(bool);
            fetchMusic = false;
        }
    }

    public static final int method670(int i, int i_0_) {
        if (i > i_0_) {
            int i_2_ = i_0_;
            i_0_ = i;
            i = i_2_;
        }

        for (int i_3_; i != 0; i = i_3_) {
            i_3_ = i_0_ % i;
            i_0_ = i;
        }

        return i_0_;
    }

    private static final void method684(boolean bool, int i, int i_2_, byte[] is) {
        if (aClass56_749 != null) {
            if (anInt478 >= 0) {
                anInt2200 = i;

                if (anInt478 != 0) {
                    int i_4_ = method1004(anInt478);
                    i_4_ -= anInt155;
                    anInt720 = (i_4_ + 3600) / i;

                    if (anInt720 < 1) {
                        anInt720 = 1;
                    }
                } else {
                    anInt720 = 1;
                }

                aByteArray347 = is;
                anInt1478 = i_2_;
                aBoolean475 = bool;
            } else if (anInt720 == 0) {
                method853(i_2_, is, bool);
            } else {
                anInt1478 = i_2_;
                aBoolean475 = bool;
                aByteArray347 = is;
            }
        }
    }

    private static final void method689(int i) {
        Client.anInt1408 += i;

        while (Client.anInt1408 >= Client.anInt197) {
            Client.anInt1408 -= Client.anInt197;
            anInt1526 -= anInt1526 >> 2;
        }

        anInt1526 -= i * 1000;

        if (anInt1526 < 0) {
            anInt1526 = 0;
        }
    }

    public static final void method790() {
        if (aClass56_749 != null) {
            method891(false);

            if (anInt720 > 0) {
                aClass56_749.method831(256);
                anInt720 = 0;
            }

            aClass56_749.method828();
            aClass56_749 = null;
        }
    }

    private static final void method853(int i_2_, byte[] is, boolean bool) {
        if (aClass56_749 != null) {
            if (anInt478 >= 0) {
                aClass56_749.method833();
                anInt478 = -1;
                aByteArray347 = null;
                anInt720 = 20;
                anInt155 = 0;
            }

            if (is != null) {
                if (anInt720 > 0) {
                    aClass56_749.method831(i_2_);
                    anInt720 = 0;
                }

                anInt478 = i_2_;
                aClass56_749.method827(i_2_, is, 0, bool);
            }
        }
    }

    private static final void method891(boolean bool) {
        method853(0, null, bool);
    }

    private static final void method899(int i, int i_29_, boolean bool, byte[] is, int i_30_) {
        if (aClass56_749 != null) {
            if (i_29_ >= (anInt478 ^ 0xffffffff)) {
                i -= 20;

                if (i < 1) {
                    i = 1;
                }

                anInt720 = i;

                if (anInt478 == 0) {
                    anInt2200 = 0;
                } else {
                    int i_31_ = method1004(anInt478);
                    i_31_ -= anInt155;
                    anInt2200 = (anInt2200 - 1 + i_31_ + 3600) / anInt2200;
                }

                aBoolean475 = bool;
                aByteArray347 = is;
                anInt1478 = i_30_;
            } else if (anInt720 != 0) {
                aBoolean475 = bool;
                aByteArray347 = is;
                anInt1478 = i_30_;
            } else {
                method853(i_30_, is, bool);
            }
        }
    }

    private static final void method900(int i) {
        if (aClass56_749 != null) {
            if (anInt720 == 0) {
                if (anInt478 >= 0) {
                    anInt478 = i;
                    aClass56_749.method830(i, 0);
                }
            } else if (aByteArray347 != null) {
                anInt1478 = i;
            }
        }
    }

    private static final boolean musicIsntNull() {
        return aClass56_749 != null;
    }

    public static void setLowDetail() {
        setLowDetail(true);
        //Scene.lowDetail = true;
        //Rasterizer3D.lowDetail = false;
        //Region.lowDetail = true;
        //ObjectDef.lowDetail = true;
        Configuration.HIGH_DETAIL = false;
        Configuration.hdMinimap = false;
        Configuration.hdShading = true;
    }

    public static void setHighDetail() {

        Scene.lowDetail = false;
        Rasterizer3D.lowDetail = false;
        Region.lowDetail = false;
        ObjectDef.lowDetail = false;
        Configuration.hdMinimap = false;// k
        Configuration.hdShading = true;
        Configuration.HIGH_DETAIL = true;
    }

    public static void setTab(int id) {
        tabID = id;
        tabAreaAltered = true;
    }

    private static final void setVolume(int i) {
        if (musicIsntNull()) {
            if (fetchMusic) {
                musicVolume2 = i;
            } else {
                method900(i);
            }
        }
    }

    public static final void sleep(long time) {
        if (time > 0L) {
            if (time % 10L != 0L) {
                threadSleep(time);
            } else {
                threadSleep(time - 1L);
                threadSleep(1L);
            }
        }
    }

    private static final void threadSleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
        }
    }

    public static String getClipboardContents() {
        String result = "";
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if (hasTransferableText) {
            try {
                result = (String) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            /*
             * } if (myPrivilege >= 2) { return result; } else {
             */
            return result;
        }
        return "";
    }

    public static void setClipboardContents(String aString) {
        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public static void disableInputFields() {
        for (int i = 0; i < 20; i++) {
            if (RSInterface.inputFields[i] != null) {
                RSInterface.inputFields[i].inputToggled = false;
                if (RSInterface.inputFields[i].inputText.equals("")) {
                    RSInterface.inputFields[i].inputText = RSInterface.inputFields[i].defaultText;
                }
            }
        }
    }

    public static String optimizeText(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i + 2));
                }
            }
        }
        return s.replace("_", " ");
    }

    public static String capitalizeFirstChar(String s) {
        try {
            if (s != "") {
                return (s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase()).trim();
            }
        } catch (Exception e) {
        }
        return s;
    }

    public static String getStars(String s) {
        StringBuffer stars = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            stars.append("*");
        }
        return stars.toString();
    }

    public static int getChatColor(String hex) {
        int convertHexCode = Integer.parseInt(hex, 16);
        return convertHexCode;
    }

    protected static void processLoadingError(String... msgs) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    Signlink.release();
                    FileUtilities.delete(Signlink.getCacheDirectory());
                } catch (IOException e) {
                    e.printStackTrace();
                    showErrorScreen(instance, "A fatal error occured while attempting to fix the previous loading error", "Please screenshot this message and report it to Crimson immediately", e.getMessage());
                }
            }
        });

        showErrorScreen(instance, msgs);

        try {
            Thread.sleep(10_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    private static void showErrorScreen(Client client, String... messages) {
        client.method4(1);
        Graphics g = canvas.getGraphics();
        Color titleTextColor = new Color(205, 200, 50);
        Color textColor = new Color(30, 191, 30);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 765, 503);
        g.setFont(new Font("Verdana", 1, 18));
        g.setColor(titleTextColor);
        g.drawString("An error has occured while starting " + Configuration.CLIENT_NAME + "...", 30, 35);
        g.drawLine(30, 40, 645, 40);
        g.setFont(new Font("Arial", 1, 90));
        g.setColor(textColor);
        g.setFont(new Font("Verdana", 1, 16));
        int startingX = 30, startingY = 40;
        for (String message : messages) {
            startingY += 40;
            g.drawString(message, startingX, startingY);
        }
    }

    public static final byte[] ReadFile(String s) {
        try {
            byte[] abyte0;
            File file = new File(s);
            int i = (int) file.length();
            abyte0 = new byte[i];
            DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(s)));
            datainputstream.readFully(abyte0, 0, i);
            datainputstream.close();
            TotalRead++;
            return abyte0;
        } catch (Exception e) {
            System.out.println(new StringBuilder().append("Read Error: ").append(s).toString());
            return null;
        }
    }

    public static void loadGoals(String name) {
        try {
            File file = new File(Signlink.getCacheDirectory() + "/" + name.trim()
                    .toLowerCase() + ".goals");
            if (!file.exists()) {
                for (int index = 0; index < Skills.goalData.length; index++) {
                    Skills.goalData[index][0] = -1;
                    Skills.goalData[index][1] = -1;
                    Skills.goalData[index][2] = -1;
                }
                return;
            }
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            for (int index = 0; index < Skills.goalData.length; index++) {
                Skills.goalData[index][0] = in.readInt();
                Skills.goalData[index][1] = in.readInt();
                Skills.goalData[index][2] = in.readInt();
                Skills.goalType = in.readUTF();
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getRandom(int number, boolean greaterThan0) {
        Random random = new Random();
        int randomNr = random.nextInt(number) + (greaterThan0 ? 1 : 0);
        return randomNr;
    }

    public static Stream getOut() {
        return out;
    }

    public static void setOut(Stream out) {
        Client.out = out;
    }

    public static boolean isLowDetail() {
        return lowDetail;
    }

    public static void setLowDetail(boolean lowDetail) {
        Client.lowDetail = lowDetail;
    }

    public static int getAnInt1226() {
        return anInt1226;
    }

    public static void setAnInt1226(int anInt1226) {
        Client.anInt1226 = anInt1226;
    }

    public static int getAnInt1155() {
        return anInt1155;
    }

    public static void setAnInt1155(int anInt1155) {
        Client.anInt1155 = anInt1155;
    }

    public static int getAnInt1188() {
        return anInt1188;
    }

    public static void setAnInt1188(int anInt1188) {
        Client.anInt1188 = anInt1188;
    }

    public static int getAnInt924() {
        return anInt924;
    }

    public static void setAnInt924(int anInt924) {
        Client.anInt924 = anInt924;
    }

    public static int getAnInt1288() {
        return anInt1288;
    }

    public static void setAnInt1288(int anInt1288) {
        Client.anInt1288 = anInt1288;
    }

    public static int getAnInt986() {
        return anInt986;
    }

    public static void setAnInt986(int anInt986) {
        Client.anInt986 = anInt986;
    }

    public static int getAnInt1134() {
        return anInt1134;
    }

    public static void setAnInt1134(int anInt1134) {
        Client.anInt1134 = anInt1134;
    }

    public static int getAnInt1175() {
        return anInt1175;
    }

    public static void setAnInt1175(int anInt1175) {
        Client.anInt1175 = anInt1175;
    }

    public static String getMac() throws SocketException {
        /*
        try {
            InetAddress localHost = null;
            try {
                localHost = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
            byte[] hardwareAddress = ni.getHardwareAddress();
            String[] hexadecimal = new String[hardwareAddress.length];
            for (int i = 0; i < hardwareAddress.length; i++) {
                hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
            }
            String macAddress = String.join("-", hexadecimal);
            return macAddress;
        } catch (Exception e) {
            return "badmac";
        }*/

        try{
            final Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
                while (e.hasMoreElements()) {
                    final byte[] mac = e.nextElement().getHardwareAddress();
                    if (mac != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < mac.length; i++) {
                            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                        }
                        //System.out.println(sb);
                        return sb.toString();
                    }
                }
        } catch (Exception e) {
            return "badmac";
        }
        return "badmac";
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    public int getChatBackIncreaseY() {
        return chatIncreaseY = !(GameFrame.getScreenMode() == ScreenMode.FIXED) ? getScreenHeight() - 503 : 0;
    }

    public int mouseX() {
        return super.mouseX;
    }

    public int mouseY() {
        return super.mouseY;
    }

    public boolean mouseInRegion(int x1, int x2, int y1, int y2) {
        return super.mouseX >= x1 && super.mouseX <= x2 && super.mouseY >= y1 && super.mouseY <= y2;
    }

    public void addObject(int x, int y, int objectId, int face, int type, int height) {
        int mX = mapX - 6;
        int mY = mapY - 6;
        int x2 = x - mX * 8;
        int y2 = y - mY * 8;
        int i15 = 40 >> 2;
        int l17 = anIntArray1177[i15];
        if (y2 > 0 && y2 < 103 && x2 > 0 && x2 < 103) {
            method130(-1, objectId, face, l17, y2, type, height, x2, 0);
        }
    }

    public static int offsetForRights(int rights) {
        switch (rights) {
            case 1://Moderator
            case 2://Admin
            case 3://Support
            case 4://Owner
            case 5://Manager
            case 6://Manager2
            case 7://Head_Manager
            case 8://Developer
                return -1;
            case 9://Youtuber
            case 10://Adept
            case 11://Ethereal
            case 12://Mythic
            case 13://Archon
            case 14://Celestial
            case 15://Ascendant
            case 16://Gladiator
            case 17://Event host
            case 18://event admin

            case 19://cosmic
            case 20://guardian
            case 21://corrupt

                return 0;
            //ADD RANKS UNDER HERE
        }
        return -1;
    }

    public static int spriteForRights(int rights) {
        switch (rights) {
            case 1://Moderator
                return 828;
            case 2://Admin
                return 829;
            case 3://Support
                return 830;
            case 4://Owner
                return 831;
            case 5://Manager
            case 6://Manager
            case 7://Head_Manager
                return 833;
            case 8://Developer
                return 834;
            case 9://Youtuber
                return 837;
            case 10://Adept
                return 842;
            case 11://Ethereal
                return 843;
            case 12://Mythic
                return 844;
            case 13://Archon
                return 845;
            case 14://Celestial
                return 846;
            case 15://Ascendant
                return 5645;
            case 16://Gladiator
                return 5646;
            case 17://Event host
                return 5644;
            case 18://event admin
                return 5643;

            case 19://cosmic
                return 3437;
            case 20://guardian
                return 3438;
            case 21://corrupt
                return 3439;
            //ADD RANKS UNDER HERE
        }
        return -1;
    }

    public static int spriteForIronman(int ironman) {

        switch (ironman) {
            case 1:
                return 839;
            case 2:
                return 840;
            case 3:
                return 838;
        }

        return -1;
    }

    public static int spriteForDiff(int rights) {
        switch (rights) {
            case 0://EASY
                return 3323;
            case 1://MED
                return 3322;
            case 2://HARD
                return 3321;
            case 3://ELITE
                return 3320;
        }
        return -1;
    }

    public String indexLocation(int cacheIndex, int index) {
        return Signlink.getCacheDirectory() + "/index" + cacheIndex + "/" + (index != -1 ? index + ".gz" : "");

    }

    public void renameIndices(int cacheIndex) throws IOException {
        System.out.println("Started renaming index " + cacheIndex + ".");
        int indexLength = new File(indexLocation(cacheIndex, -1)).listFiles().length;
        File[] file = new File(indexLocation(cacheIndex, -1)).listFiles();
        // File (or directory) with new name

        if (file == null || file.length == 0) {
            return;
        }
        try {
            for (int index = 0; index < indexLength; index++) {
                if (new File(indexLocation(cacheIndex, index + 200000)).exists())
                    throw new java.io.IOException("file exists");
                boolean success = file[index].renameTo(new File(indexLocation(cacheIndex, index + 200000)));
                if (success)
                    System.out.println(file[index] + " successfully renamed!");
            }
        } catch (Exception e) {
            System.out.println("Error packing cache index " + cacheIndex + ".");
        }
        System.out.println("Finished renaming " + cacheIndex + ".");
    }

    public void repackCacheIndex(int cacheIndex) {
        System.out.println("Started repacking index " + cacheIndex + ".");
        int indexLength = new File(indexLocation(cacheIndex, -1)).listFiles().length;
        File[] file = new File(indexLocation(cacheIndex, -1)).listFiles();
        if (file == null || file.length == 0) {
            return;
        }
        try {
            for (int index = 0; index < indexLength; index++) {
                int fileIndex = Integer.parseInt(getFileNameWithoutExtension(file[index].toString()));
                byte[] data = fileToByteArray(cacheIndex, fileIndex);
                if (data != null && data.length > 0) {
                    decompressors[cacheIndex].write(data.length, data, fileIndex);
                    System.out.println("Repacked Archive: " + cacheIndex + " File: " + fileIndex + ".");
                } else {
                    System.out.println("Unable to locate index " + fileIndex + ".");
                }
            }
        } catch (Exception e) {
            System.out.println("Error packing cache index " + cacheIndex + ".");
        }
        System.out.println("Finished repacking " + cacheIndex + ".");
    }

    public void updateCacheButton() {
        Bank.inverse_quantity_comp(Configuration.activeBankButton);
    }

    public void updateSetting(int settingI, boolean b) {
        int l2 = RSInterface.interfaceCache[settingI].valueIndexArray[0][1];
        variousSettings[l2] = b ? 1 : 0;
        Save.settings(Client.getClient());
    }

    public void updateSettingsInterface() {
        updateSetting(26356, Configuration.RENDER_DISTANCE);
        updateSetting(26358, Configuration.RENDER_SELF);
        updateSetting(26360, Configuration.RENDER_PLAYERS);
        updateSetting(26362, Configuration.RENDER_NPCS);
        updateSetting(26364, Configuration.ANIMATE_TEXTURES);
        updateSetting(26456, Configuration.DRAW_EFFECT_TIMERS);
        updateSetting(26556, Configuration.SPLIT_CHAT);
        updateSetting(26558, Configuration.TIME_STAMPS);
        updateSetting(26560, Configuration.HIGHLIGHT_USERNAME);
        updateSetting(26562, Configuration.SMILIES_ENABLED);
        updateSetting(26656, Configuration.USERNAMES_ABOVE_HEAD);
        updateSetting(26658, Configuration.ITEM_OUTLINES);
        updateSetting(26660, Configuration.HP_BAR);
        updateCacheButton();
    }

    public byte[] fileToByteArray(int cacheIndex, int index) {
        try {
            if (indexLocation(cacheIndex, index).length() <= 0 || indexLocation(cacheIndex, index) == null) {
                return null;
            }
            File file = new File(indexLocation(cacheIndex, index));
            byte[] fileData = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(fileData);
            fis.close();
            return fileData;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean itemCollected(int id) {
        return unlockedItems != null && Arrays.stream(unlockedItems).anyMatch(i -> i == id);
    }

    private void setAutoCastOff() {
        autoCast = false;
        autocastId = 0;
        getOut().putOpcode(185);
        getOut().putInt(6667);
    }

    public final void addParticle(Particle p) {
        currentParticles.add(p);
    }

    private void drawConsole() {
        if (consoleOpen) {
            TextDrawingArea.transparentBox(334, 0, 0, 5320850, getGameComponent().getWidth(), 0, 97);
            TextDrawingArea.drawPixels(1, 315, 0, 16777215, getGameComponent().getWidth());
            newBoldFont.drawBasicString("-->", 11, 328, 16777215, 0, false);
            if (loopCycle % 20 < 10) {
                newBoldFont.drawBasicString(consoleInput + "|", 38, 328, 16777215, 0, false);
            } else {
                newBoldFont.drawBasicString(consoleInput, 38, 328, 16777215, 0, false);
            }
        }
    }

    private void drawConsoleArea() {
        if (consoleOpen) {
            for (int i = 0, j = 308; i < 17; i++, j -= 18) {
                if (consoleMessages[i] != null) {
                    newRegularFont.drawBasicString(consoleMessages[i], 9, j, 16777215, 0, false);
                    // textDrawingArea.method385(16777215,consoleMessages[i], 9,
                    // j);
                }
            }
        }
    }

    /**
     * @param cacheType
     * @param index
     * @return index location
     */
    public String indexLocation(String cacheType, int index) {
        return Signlink.getCacheDirectory() + "" + cacheType + "/" + (index != -1 ? index + ".gz" : "");

    }

    /**
     * The cache packing method.
     *
     * @param type
     */
    public void repackCache(PackingTypes type) {
        System.out.println("Started repacking the " + type.getType() + ".");
        int indexLength = new File(indexLocation(type.getType(), -1)).listFiles().length;
        File[] file = new File(indexLocation(type.getType(), -1)).listFiles();
        try {
            for (int index = 0; index < indexLength; index++) {
                int fileIndex = Integer.parseInt(getFileNameWithoutExtension(file[index].toString()));
                byte[] data = fileToByteArray(type.getType(), fileIndex);
                if (data != null && data.length > 0) {
                    decompressors[type.getIndex()].write(data.length, data, fileIndex);
                    System.out.println("Repacked " + fileIndex + ".");
                } else {
                    System.out.println("Unable to locate index " + fileIndex + ".");
                }
            }
        } catch (Exception e) {
            System.out.println("Error packing cache index " + type.getIndex() + ".");
        }
        System.out.println("Finished repacking " + type.getIndex() + ".");
    }

    /**
     * @param cacheType
     * @param index
     * @return
     */
    public byte[] fileToByteArray(String cacheType, int index) {
        try {
            if (indexLocation(cacheType, index).length() <= 0 || indexLocation(cacheType, index) == null) {
                return null;
            }
            File file = new File(indexLocation(cacheType, index));
            byte[] fileData = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(fileData);
            fis.close();
            return fileData;
        } catch (Exception e) {
            return null;
        }
    }

    public void printConsoleMessage(String s, int i) {
        if (backDialogID == -1) {
            updateChatArea = true;
        }
        for (int j = 16; j > 0; j--) {
            consoleMessages[j] = consoleMessages[j - 1];
        }
        if (i == 0) {
            consoleMessages[0] = date() + " " + s;
        } else {
            consoleMessages[0] = s;
        }
    }

    public String date() {
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
        return sd.format(date);
    }

    private void sendCommandPacket(String cmd) {
        if (cmd.equalsIgnoreCase("cls") || cmd.equalsIgnoreCase("clear")) {
            for (int j = 0; j < 17; j++) {
                consoleMessages[j] = null;
            }
        }

        if (ModelUtil.parseClientCommand(cmd)) {
            return;
        }

        if (cmd.startsWith("vngt")) {
            vengTimer = ((int) (49 * 0.65D));
            pushMessage("Sent vengTimer", 0, "");
        }
        if (cmd.startsWith("groundtext")) {
            Configuration.GROUND_TEXT = !Configuration.GROUND_TEXT;
            pushMessage("You've set ground item text to: " + Configuration.GROUND_TEXT + ".", 0, "");
        }
        if (cmd.startsWith("dumpnpc")) {
            if (myRights == 4) {
                String cutCmd = cmd.substring(8);
                if (NumberUtils.isNumber(cutCmd)) {
                    EntityDef.printDefinitionsForId(Integer.parseInt(cutCmd));
                }
            }
        }
        if (cmd.startsWith("dumpid") || cmd.startsWith("dpitem")) {
            if (myRights == 4) {
                String cutCmd = cmd.substring(7);
                if (NumberUtils.isNumber(cutCmd)) {
                    ItemDef.printDefinitionsForId(Integer.parseInt(cutCmd));
                }
            }
        }
        String[] args = cmd.split(" ");
        String commandStart = args[0].toLowerCase();
        switch (commandStart) {
            case "m":
                int itemid = Integer.parseInt(args[1]);
                ItemDef ittt = ItemDef.get(itemid);
                System.out.println("Itemid: " + itemid);
                System.out.println("name: " + ittt.name);
                System.out.println("model: " + (ittt.modelID));
                System.out.println("male model: " + (ittt.maleEquip1));
                System.out.println("female model: " + (ittt.femaleEquip1));
                System.out.println("model zoom: " + (ittt.modelZoom));
                System.out.println("model OffsetX: " + (ittt.rotation_z));
                System.out.println("model OffsetY: " + (ittt.translate_yz));
                System.out.println("model OffsetZ: " + (ittt.translate_x));
                System.out.println("model rotationx: " + (ittt.rotation_x));
                System.out.println("model rotationy: " + (ittt.rotation_y));

                System.out.println("model scale x: " + (ittt.scaleX));
                System.out.println("model scale y: " + (ittt.scaleY));
                System.out.println("model scale z: " + (ittt.scaleZ));
                System.out.println("arms model: " + (ittt.armsModel));

                System.out.println("rdc 1: " + (ittt.rdc));
                System.out.println("rdc 2: " + (ittt.rdc2));
                System.out.println("rdc 3: " + (ittt.rdc3));



                if (ittt.modelID > 0) {
                    Model getfaces = Model.fetchModel(ittt.modelID);

                    if (getfaces != null) {
                        System.out.println("getfaces: " + getfaces);
                    }
                   /* for (int i = 0; i < getfaces.anIntArray1640.length; i++) {
                        System.out.println("i: " + getfaces.anIntArray1640[i]);
                    }*/
                }
                break;
            case "n":
                int mobid = Integer.parseInt(args[1]);
                EntityDef mob = EntityDef.get(mobid);
                assert mob != null;
                System.out.println("---------------------------------");
                System.out.println("case " + mobid + ":");
                System.out.println("definition.name = " + mob.name + ";");
                System.out.println("definition.npcModels = new int[]" + Arrays.toString(mob.npcModels) + ";");
                System.out.println("definition.standAnimation = " + mob.standAnimation + ";");
                System.out.println("definition.walkAnimation = " + mob.walkAnimation + ";");
                System.out.println("definition.scaleXZ = " + mob.scaleXZ + ";");
                System.out.println("definition.scaleY = " + mob.scaleY + ";");
                System.out.println("definition.dialogueModels = new int[]" + Arrays.toString(mob.dialogueModels) + ";");
                System.out.println("break;");
                System.out.println("---------------------------------");



                System.out.println("definition.combatLevel = " + mob.combatLevel + ";");

                break;

            case "o":
                int objectid = Integer.parseInt(args[1]);

                System.out.println("OBJ ANIM: " + ObjectDef.forID(objectid).animation);
                System.out.println("OBJ MODEL :" + Arrays.toString(ObjectDef.forID(objectid).modelIds));
                System.out.println("OBJ MINIMAP SCENE :" + ObjectDef.forID(objectid).mapSceneID);
                System.out.println("OBJ MINIMAP ICON :" + ObjectDef.forID(objectid).mapFunctionID);

                break;
            case "dumpobj":
                if (args.length < 2) {
                    pushMessage("Usage: dumpobj (id)");
                } else {
                    int objectId = Integer.parseInt(args[1]);
                    ObjectDef.printObjectDefinition(objectId);
                }
                break;

            case "dumpgraphics":
                AnimatedGraphic.dumpGraphics();
            break;
            case "a":
                int animId = Integer.parseInt(args[1]);
                AnimationDefinition animData = AnimationDefinition.cache[animId];
                System.out.println("FrameCount: " + animData.frameCount);
                System.out.print("FrameIDS: ");
                for (int i : animData.frameIDs) {
                    System.out.print(i + ", ");
                }
                System.out.println("");
                System.out.print("FrameIDS2: ");
                for (int i : animData.frameIDs2) {
                    System.out.print(i + ", ");
                }
                System.out.println("");
                System.out.print("Delays: ");
                for (int i : animData.delays) {
                    System.out.print(i + ", ");
                }
                System.out.println("");
                System.out.println("Loopdelay: " + animData.loopDelay);

                System.out.println("");
                System.out.println("OneSquareAnimation: " + animData.oneSquareAnimation);
                System.out.println("ForcedPriority: " + animData.forcedPriority);
                System.out.println("LeftHandItem: " + animData.leftHandItem);
                System.out.println("RightHandItem: " + animData.rightHandItem);
                System.out.println("FrameStep: " + animData.frameStep);
                System.out.println("ResetWalk: " + animData.resetWhenWalk);
                System.out.println("Priority: " + animData.priority);
                System.out.println("delayType: " + animData.delayType);
                break;

        }

        switch (cmd) {

            case "repackanims":
                repackCache(PackingTypes.ANIMATIONS);
                break;
            case "repackmodels":
                repackCache(PackingTypes.MODELS);
                break;
            case "repackmaps":
                repackCache(PackingTypes.MAPS);
                break;
            case "repackindex4":
                repackCacheIndex(4);


                // loadRegion();
                break;
            case "hitmarks":
                Configuration.NEW_HITMARKS = !Configuration.NEW_HITMARKS;
                break;
            case "customobj":
                CustomObjects.spawn();
                break;
            case "cursors":
                Configuration.NEW_CURSORS = !Configuration.NEW_CURSORS;
                break;
            case "debug":
                debug = !debug;
                break;
            case "printvertices":
                Model playerModel = myPlayer.getRotatedModel();
                int vertices = playerModel.verticesCount;
                int faces = playerModel.numberOfTriangleFaces;

                System.out.println("vertices " + vertices + " faces " + faces);
                break;
            case "fps":
            case "data":
                fpsOn = !fpsOn;
                break;

            case "dumpitemnames":
                ItemDef.dump();
                break;
            case "testslot":
                SlotSystem.testSlotChange();
                break;
            case "noclip":
                if (myRights == 4) {
                    for (int k1 = 0; k1 < 4; k1++) {
                        for (int i2 = 1; i2 < 103; i2++) {
                            for (int k2 = 1; k2 < 103; k2++) {
                                collisionData[k1].flags[i2][k2] = 0;
                            }

                        }
                    }
                }
                break;
//            case "uitest":
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        new RSPSGuideUI();  // Display the updates UI
//                    }
//                });
//                break;
            case "freeid":
                new Thread(() -> {
                    int firstChild = -1;
                    int freeCount = 0;
                    for (int i = 0; i < RSInterface.interfaceCache.length; i++) {
                        if (RSInterface.interfaceCache[i] == null) {
                            if (firstChild == -1) {
                                firstChild = i;
                            } else {
                                freeCount++;
                            }
                        } else {
                            if (firstChild != -1) {
                                System.out.println("Null interface component "+firstChild+" has "+freeCount+" null components after it.");
                                firstChild = -1;
                                freeCount = 0;
                            }
                        }
                    }
                }).start();
                break;
            case "rsi":
                Archive streamLoader_1 = getArchive(3, "interface", "interface", expectedCRCs[3], 35);
                Archive mediaArchive = getArchive(4, "2d graphics", "media", expectedCRCs[4], 40);
                TextDrawingArea[] aclass30_sub2_sub1_sub4s = {smallText, normalText, boldText, aTextDrawingArea_1273};
                RSFontSystem[] newFonts = {newSmallFont, newRegularFont, newBoldFont};
                RSInterface.unpack(streamLoader_1, aclass30_sub2_sub1_sub4s, mediaArchive, newFonts);
                break;
        }
        /** Add Commands Here **/
        if (loggedIn) {
            getOut().putOpcode(103);
            getOut().putByte(cmd.length() + 1);
            getOut().putString(cmd);
        }
    }

    private void addFriend(long nameAsLong) {
        try {
            if (nameAsLong == 0L) {
                return;
            }

            if (friendCount >= 100 && anInt1046 != 1) {
                pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
                return;
            }

            if (friendCount >= 200) {
                pushMessage("Your friendlist is full. Max of 100 for free users, and 200 for members", 0, "");
                return;
            }

            String s = TextUtilities.fixName(TextUtilities.nameForLong(nameAsLong));

            if (s != null) {
                if (s.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    s = name.substring(prefixSubstring);
                }
                if (s.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    s = name.substring(prefixSubstring);
                }
            }

            for (int i = 0; i < friendCount; i++) {
                if (friendsListAsLongs[i] == nameAsLong) {
                    pushMessage(s + " is already on your friend list", 0, "");
                    return;
                }
            }

            for (int i = 0; i < ignoreCount; i++) {
                if (ignoreListAsLongs[i] == nameAsLong) {
                    pushMessage("Please remove " + s + " from your ignore list first", 0, "");
                    return;
                }
            }

            if (s.equals(myPlayer.name)) {
                return;
            } else {
                friendsList[friendCount] = s;
                friendsListAsLongs[friendCount] = nameAsLong;
                friendsNodeIDs[friendCount] = 0;
                friendCount++;
                getOut().putOpcode(188);
                getOut().putLong(nameAsLong);
                return;
            }
        } catch (RuntimeException ex) {
            Signlink.reportError("15283, " + (byte) 68 + ", " + nameAsLong + ", " + ex.toString());
            ex.printStackTrace();
        }

        throw new RuntimeException();
    }

    private void addIgnore(long nameAsLong) {
        try {
            if (nameAsLong == 0L) {
                return;
            }

            if (ignoreCount >= 100) {
                pushMessage("Your ignore list is full. Max of 100 hit", 0, "");
                return;
            }

            String name = TextUtilities.fixName(TextUtilities.nameForLong(nameAsLong));

            if (name != null) {
                if (name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    name = name.substring(prefixSubstring);
                }
                if (name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    name = name.substring(prefixSubstring);
                }
            }

            for (int i = 0; i < ignoreCount; i++) {
                if (ignoreListAsLongs[i] == nameAsLong) {
                    pushMessage(name + " is already on your ignore list", 0, "");
                    return;
                }
            }

            for (int i = 0; i < friendCount; i++) {
                if (friendsListAsLongs[i] == nameAsLong) {
                    pushMessage("Please remove " + name + " from your friend list first", 0, "");
                    return;
                }
            }

            ignoreListAsLongs[ignoreCount++] = nameAsLong;
            getOut().putOpcode(133);
            getOut().putLong(nameAsLong);
            return;
        } catch (RuntimeException ex) {
            Signlink.reportError("45688, " + nameAsLong + ", " + 4 + ", " + ex.toString());
        }

        throw new RuntimeException();
    }

   /* private boolean canBuildMenu(RSInterface rsInterface) {
        boolean build = true;
        int startX = 0;
        int startY = 0;
        int endX = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenWidth() : 512;
        int endY = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenHeight() : 334;


        boolean isSprite = (rsInterface.children != null && rsInterface.children.length > 0
                    && RSInterface.interfaceCache[rsInterface.children[0]].enabledSprite != null);

        if (isSprite) {
            startX = (gameScreenDrawX + (endX - 512) / 2) + rsInterface.childX[0];
            startY = (gameScreenDrawY + (endY - 334) / 2) + rsInterface.childY[0];
            endX = startX + (RSInterface.interfaceCache[(rsInterface.children[0])].enabledSprite.myWidth);
            endY = startY + (RSInterface.interfaceCache[(rsInterface.children[0])].enabledSprite.myHeight);


            if (InterfaceMovementManager.getModifiedPositions().get(openInterfaceID) != null) {
                startX += InterfaceMovementManager.getModifiedPositions()
                        .get(openInterfaceID)
                        .getXOffset();
                startY += InterfaceMovementManager.getModifiedPositions()
                        .get(openInterfaceID)
                        .getYOffset();
            }
        }

        switch (rsInterface.id) {
            case 3824:
            case 3323:
            case 3443:
            case 3559:
                endX = startX + 488;
                endY = startY + 300;
                break;
            case 7424:
                endX = startX + 488;
                endY = startY + 305;
                break;

        }

        build = (super.mouseX > startX && super.mouseX < endX && super.mouseY > startY && super.mouseY < endY);
        return build;
    }*/

    private void build3dScreenMenu() {
        /*if (inMoveButton)
            return;*/
        boolean skipWalkHere = false;
        if(GameFrame.getScreenMode() != ScreenMode.FIXED) {
            if(chatArea.isHovering(this, GameFrame.getScreenMode())) {
                skipWalkHere = true;
            }
        }
        if (itemSelected == 0 && spellSelected == 0 && !skipWalkHere) {
            menuActionName[menuActionRow] = "Walk here";
            menuActionID[menuActionRow] = 516;
            menuActionCmd2[menuActionRow] = super.mouseX;
            menuActionCmd3[menuActionRow] = super.mouseY;
            menuActionRow++;
        }
        int j = -1;
        for (int k = 0; k < Model.objectsRendered; k++) {
            int modelData = Model.anIntArray1688[k];// data
            int x = modelData & 0x7f;// x
            int y = modelData >> 7 & 0x7f;// y
            int face = modelData >> 29 & 3;// face
            int index = -1;// objId
            if (face != 2) {
                index = modelData >> 14 & 32767;
            }
            if (modelData == j) {
                continue;
            }
            j = modelData;

            // objects
            if (face == 2 && scene.fetchObjectIDTagForPosition(plane, x, y, modelData) >= 0) {
                index = Model.mapObjIds[k];
                ObjectDef class46 = ObjectDef.forID(index);
                if (class46.morphisms != null) {
                    class46 = class46.method580();
                }

                if (regionBaseX + x == 3090 && regionBaseY + y == 3956) {
                    menuActionName[menuActionRow] = "Pull @cya@Lever";
                    menuActionID[menuActionRow] = 502;
                    menuActionCmd1[menuActionRow] = modelData;
                    menuActionCmd2[menuActionRow] = x;
                    menuActionCmd3[menuActionRow] = y;
                    menuActionCmd4[menuActionRow] = 5959;
                    menuActionRow++;

                    menuActionName[menuActionRow] = "Examine @cya@ Lever";
                    menuActionID[menuActionRow] = 1226;
                    menuActionCmd1[menuActionRow] = 950;
                    menuActionCmd2[menuActionRow] = x;
                    menuActionCmd3[menuActionRow] = y;
                    menuActionCmd4[menuActionRow] = 5959;
                    menuActionRow++;
                    return;
                }
                if (regionBaseX + x == 2539 && regionBaseY + y == 4712) {

                    menuActionName[menuActionRow] = "Pull @cya@Lever";
                    menuActionID[menuActionRow] = 502;
                    menuActionCmd1[menuActionRow] = modelData;
                    menuActionCmd2[menuActionRow] = x;
                    menuActionCmd3[menuActionRow] = y;
                    menuActionCmd4[menuActionRow] = 5960;
                    menuActionRow++;

                    menuActionName[menuActionRow] = "Examine @cya@Lever";
                    menuActionID[menuActionRow] = 1226;
                    menuActionCmd1[menuActionRow] = 950;
                    menuActionCmd2[menuActionRow] = x;
                    menuActionCmd3[menuActionRow] = y;
                    menuActionCmd4[menuActionRow] = 5960;
                    menuActionRow++;
                    return;
                }
                if (class46 == null) {
                    continue;
                }
                if (itemSelected == 1) {
                    menuActionName[menuActionRow] = "Use " + selectedItemName + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "("+selectedItemId+")" : "") +" with @cya@" + class46.name + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "("+class46.type+")" : "");
                    menuActionID[menuActionRow] = 62;
                    menuActionCmd1[menuActionRow] = modelData;
                    menuActionCmd2[menuActionRow] = x;
                    menuActionCmd3[menuActionRow] = y;
                    menuActionCmd4[menuActionRow] = index;
                    menuActionRow++;
                } else if (spellSelected == 1) {
                    if ((spellUsableOn & 4) == 4) {
                        menuActionName[menuActionRow] = spellTooltip + " @cya@" + class46.name;
                        menuActionID[menuActionRow] = 956;
                        menuActionCmd1[menuActionRow] = modelData;
                        menuActionCmd2[menuActionRow] = x;
                        menuActionCmd3[menuActionRow] = y;
                        menuActionCmd4[menuActionRow] = index;
                        menuActionRow++;
                    }
                } else {
                    if (class46 != null && class46.actions != null) {
                        for (int i2 = 4; i2 >= 0; i2--) {
                            if (class46.actions[i2] != null) {
                                menuActionName[menuActionRow] = class46.actions[i2] + " @cya@" + class46.name;

                                if (i2 == 0) {
                                    menuActionID[menuActionRow] = 502;
                                }

                                if (i2 == 1) {
                                    menuActionID[menuActionRow] = 900;
                                }

                                if (i2 == 2) {
                                    menuActionID[menuActionRow] = 113;
                                }

                                if (i2 == 3) {
                                    menuActionID[menuActionRow] = 872;
                                }

                                if (i2 == 4) {
                                    menuActionID[menuActionRow] = 1062;
                                }

                                menuActionCmd1[menuActionRow] = modelData;
                                menuActionCmd2[menuActionRow] = x;
                                menuActionCmd3[menuActionRow] = y;
                                menuActionCmd4[menuActionRow] = index;
                                menuActionRow++;
                            }
                        }
                    }

                    menuActionName[menuActionRow] = myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "Examine @cya@" + class46.name + " @gre@(@whi@" + class46.type + "@gre@) (@whi@" + (x + regionBaseX) + "," + (y + regionBaseY) + ") @or1@(" + class46.animation + ")" + "@gre@)" : "Examine @cya@" + class46.name;
                    menuActionID[menuActionRow] = 1226;
                    menuActionCmd1[menuActionRow] = class46 == null ? -1 : class46.type << 14;
                    menuActionCmd2[menuActionRow] = x;
                    menuActionCmd3[menuActionRow] = y;
                    menuActionCmd4[menuActionRow] = index;
                    menuActionRow++;
                }
            }

            /**
             * npcs
             */
            if (face == 1) {
                Npc npc = npcArray[index];
                boolean resize = !GameFrame.isFixed();
                if (npc.definitionOverride.npcSizeInSquares == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
                    for (int j2 = 0; j2 < npcCount; j2++) {
                        Npc npc2 = npcArray[npcIndices[j2]];
                        if (npc2 != null && npc2 != npc && npc2.definitionOverride.npcSizeInSquares == 1 && npc2.x == npc.x && npc2.y == npc.y) {
                            buildAtNPCMenu(npc2.definitionOverride, npcIndices[j2], y, x);
                        }
                    }

                    for (int l2 = 0; l2 < playerCount; l2++) {
                        Player player = playerArray[playerIndices[l2]];

                        if (player != null && player.x == npc.x && player.y == npc.y) {
                            buildAtPlayerMenu(x, playerIndices[l2], player, y);
                        }
                    }
                }
                buildAtNPCMenu(npc.definitionOverride, index, y, x);
            }

            /**
             * Players
             */
            if (face == 0) {
                Player player = playerArray[index];

                if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
                    for (int k2 = 0; k2 < npcCount; k2++) {
                        Npc class30_sub2_sub4_sub1_sub1_2 = npcArray[npcIndices[k2]];

                        try {
                            if (class30_sub2_sub4_sub1_sub1_2 != null && class30_sub2_sub4_sub1_sub1_2.definitionOverride.npcSizeInSquares == 1 && class30_sub2_sub4_sub1_sub1_2.x == player.x && class30_sub2_sub4_sub1_sub1_2.y == player.y) {
                                buildAtNPCMenu(class30_sub2_sub4_sub1_sub1_2.definitionOverride, npcIndices[k2], y, x);
                            }
                        } catch (Exception _ex) {
                        }
                    }

                    for (int i3 = 0; i3 < playerCount; i3++) {
                        Player player1 = playerArray[playerIndices[i3]];

                        if (player1 != null && player1 != player && player1.x == player.x && player1.y == player.y) {
                            buildAtPlayerMenu(x, playerIndices[i3], player1, y);
                        }
                    }
                }

                buildAtPlayerMenu(x, index, player, y);

            }

            /**
             * i assume items
             */
            if (face == 3) {
                NodeList class19 = groundArray[plane][x][y];

                if (class19 != null) {
                    for (Item item = (Item) class19.getFirst(); item != null; item = (Item) class19.getNext()) {
                        ItemDef itemDef = ItemDef.get(item.id);

                        if (itemSelected == 1) {
                            menuActionName[menuActionRow] = "Use " + selectedItemName + (myRights == 4 || myRights == 5  || myRights == 8||myRights == 7 ? "("+selectedItemId+")" : "") + " with @lre@" + itemDef.name + (myRights == 4  || myRights == 5  || myRights == 8 ||myRights == 7 ? "@whi@("+itemDef.id+")" : "");
                            menuActionID[menuActionRow] = 511;
                            menuActionCmd1[menuActionRow] = item.id;
                            menuActionCmd2[menuActionRow] = x;
                            menuActionCmd3[menuActionRow] = y;
                            menuActionCmd4[menuActionRow] = index;
                            menuActionRow++;
                        } else if (spellSelected == 1) {
                            if ((spellUsableOn & 1) == 1) {
                                menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDef.name;
                                menuActionID[menuActionRow] = 94;
                                menuActionCmd1[menuActionRow] = item.id;
                                menuActionCmd2[menuActionRow] = x;
                                menuActionCmd3[menuActionRow] = y;
                                menuActionCmd4[menuActionRow] = index;
                                menuActionRow++;
                            }
                        } else {
                            for (int j3 = 4; j3 >= 0; j3--) {
                                if (itemDef.groundActions != null && itemDef.groundActions[j3] != null) {
                                    menuActionName[menuActionRow] = itemDef.groundActions[j3] + " @lre@" + itemDef.name;

                                    if (j3 == 0) {
                                        menuActionID[menuActionRow] = 652;
                                    }

                                    if (j3 == 1) {
                                        menuActionID[menuActionRow] = 567;
                                    }

                                    if (j3 == 2) {
                                        menuActionID[menuActionRow] = 234;
                                    }

                                    if (j3 == 3) {
                                        menuActionID[menuActionRow] = 244;
                                    }

                                    if (j3 == 4) {
                                        menuActionID[menuActionRow] = 213;
                                    }

                                    menuActionCmd1[menuActionRow] = item.id;
                                    menuActionCmd2[menuActionRow] = x;
                                    menuActionCmd3[menuActionRow] = y;
                                    menuActionCmd4[menuActionRow] = index;
                                    menuActionRow++;
                                } else if (j3 == 2) {
                                    menuActionName[menuActionRow] = "Take @lre@" + itemDef.name;
                                    menuActionID[menuActionRow] = 234;
                                    menuActionCmd1[menuActionRow] = item.id;
                                    menuActionCmd2[menuActionRow] = x;
                                    menuActionCmd3[menuActionRow] = y;
                                    menuActionCmd4[menuActionRow] = index;
                                    menuActionRow++;
                                }
                            }


                            menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name;// + (myRights == 4 ? " (" + itemDef.id + ") @or1@(" + itemDef.maleEquip1 + ") @or2@(" + itemDef.maleEquip1 + ")" : "");
                            menuActionID[menuActionRow] = 1448;
                            menuActionCmd1[menuActionRow] = item.id;
                            menuActionCmd2[menuActionRow] = x;
                            menuActionCmd3[menuActionRow] = y;
                            menuActionCmd4[menuActionRow] = index;
                            menuActionRow++;


                        }
                    }
                }
            }
        }
    }

    String[] asdf = {"attack", "check progress"};

    private boolean isOptionToRemove(String option) {
        for (int i = 0; i < asdf.length; i++) {
            if (option.equalsIgnoreCase(asdf[i])) {
                return true;
            }
        }
        return false;
    }

    private void buildAtNPCMenu(EntityDef entityDef, int index, int y, int x) {

        if (menuActionRow >= 400) {
            return;
        }

        if (entityDef.childrenIDs != null) {
            entityDef = entityDef.method161();
        }
        if (entityDef == null) {
            return;
        }
        if (!entityDef.disableRightClick) {
            return;
        }
        String s = entityDef.name;
        if (entityDef.combatLevel != 0) {
            s = s + combatDiffColor(myPlayer.combatLevel, entityDef.combatLevel) + " <col=AF70C3>(level:<col=AF70C3> " + entityDef.combatLevel + "<col=AF70C3>)";
        }

        if (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7) {
            s += " @whi@(@gre@" + entityDef.id + "@whi@)" + entityDef.npcModels[0] + "";
        }
        if (itemSelected == 1) {
            menuActionName[menuActionRow] = "Use " + selectedItemName + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "("+selectedItemId+")" : "") +" with @yel@" + s + (myRights == 4 || myRights == 5 || myRights == 8 ? "("+entityDef.id+")" : "");
            menuActionID[menuActionRow] = 582;
            menuActionCmd1[menuActionRow] = index;
            menuActionCmd2[menuActionRow] = x;
            menuActionCmd3[menuActionRow] = y;
            menuActionRow++;
            return;
        }

        if (spellSelected == 1) {
            if ((spellUsableOn & 2) == 2) {
                menuActionName[menuActionRow] = spellTooltip + " @yel@" + s;
                menuActionID[menuActionRow] = 413;
                menuActionCmd1[menuActionRow] = index;
                menuActionCmd2[menuActionRow] = x;
                menuActionCmd3[menuActionRow] = y;
                menuActionRow++;
            }
        } else {
            if (entityDef.actions != null) {
                for (int l = 4; l >= 0; l--) {
                    if (entityDef.actions[l] != null && !isOptionToRemove(entityDef.actions[l])) {
                        menuActionName[menuActionRow] = entityDef.actions[l] + " @yel@" + s;
                        if (l == 0) {
                            menuActionID[menuActionRow] = 20;
                        }
                        if (l == 1) {
                            menuActionID[menuActionRow] = 412;
                        }
                        if (l == 2) {
                            menuActionID[menuActionRow] = 225;
                        }
                        if (l == 3) {
                            menuActionID[menuActionRow] = 965;
                        }
                        if (l == 4) {
                            menuActionID[menuActionRow] = 478;
                        }
                        menuActionCmd1[menuActionRow] = index;
                        menuActionCmd2[menuActionRow] = x;
                        menuActionCmd3[menuActionRow] = y;
                        menuActionRow++;

                    }
                }

            }
            if (entityDef.actions != null) {
                for (int i1 = 4; i1 >= 0; i1--) {
                    if (entityDef.actions[i1] != null && isOptionToRemove(entityDef.actions[i1])) {
                        char c = '\0';
                        if (Configuration.npcAttackOptionPriority == 0) {
                            if (entityDef.combatLevel > myPlayer.combatLevel)
                                c = '\u07D0';
                        } else if (Configuration.npcAttackOptionPriority == 1) {
                            c = '\u07D0';
                        } else if (Configuration.npcAttackOptionPriority == 3) {
                            continue;
                        }
                        menuActionName[menuActionRow] = entityDef.actions[i1] + " @yel@" + s;
                        if (i1 == 0) {
                            menuActionID[menuActionRow] = 20 + c;
                        }
                        if (i1 == 1) {
                            menuActionID[menuActionRow] = 412 + c;
                        }
                        if (i1 == 2) {
                            menuActionID[menuActionRow] = 225 + c;
                        }
                        if (i1 == 3) {
                            menuActionID[menuActionRow] = 965 + c;
                        }
                        if (i1 == 4) {
                            menuActionID[menuActionRow] = 478 + c;
                        }
                        menuActionCmd1[menuActionRow] = index;
                        menuActionCmd2[menuActionRow] = x;
                        menuActionCmd3[menuActionRow] = y;
                        menuActionRow++;
                    }
                }
// all the pets
            }




            if (entityDef.id == 9021 ||entityDef.id == 5079 ||entityDef.id == 6824  ||entityDef.id == 6851 ||entityDef.id == 9842||entityDef.id == 6800 || entityDef.id == 9833|| entityDef.id == 189|| entityDef.id == 1805|| entityDef.id == 1850||entityDef.id == 9812||entityDef.id == 9822|| entityDef.id == 1898|| entityDef.id == 6430|| entityDef.id == 9011|| entityDef.id == 7375|| entityDef.id == 7349||entityDef.id == 9012|| entityDef.id == 9013|| entityDef.id == 9017|| entityDef.id == 9834|| entityDef.id == 9827|| entityDef.id == 9828|| entityDef.id == 9029 || entityDef.id == 9822 || entityDef.id == 9826 || entityDef.id == 9033 || entityDef.id == 6302 || entityDef.id == 6303 || entityDef.id == 6305 || entityDef.id == 9031 || entityDef.id == 449 || entityDef.id == 9016 || entityDef.id == 9820 || entityDef.id == 9821 || entityDef.id == 9841 || entityDef.id == 9842 || entityDef.id == 9848 || entityDef.id == 9809 || entityDef.id == 9832 || entityDef.id == 770 || entityDef.id == 9809 || entityDef.id == 9819 || entityDef.id == 9829 || entityDef.id == 771 || entityDef.id == 769 || entityDef.id == 6960 || entityDef.id == 6958 || entityDef.id == 6968 || entityDef.id == 6964 ||entityDef.id == 271 || entityDef.id == 133 || entityDef.id == 105 || entityDef.id == 4414 || entityDef.id == 189 || entityDef.id == 1902 || entityDef.id == 3309 || entityDef.id == 1898 || entityDef.id == 302 || entityDef.id == 1890 || entityDef.id == 1894 || entityDef.id == 1893 || entityDef.id == 1892 || entityDef.id == 1904 || entityDef.id == 3001 || entityDef.id == 5001 || entityDef.id == 3377 || entityDef.id == 4969 || entityDef.id == 6913 || entityDef.id == 6919 || entityDef.id == 6942 || entityDef.id == 6945 || entityDef.id == 3033 || entityDef.id == 3030 || entityDef.id == 3031 ||  entityDef.id == 3034 || entityDef.id == 3035 || entityDef.id == 3036  || entityDef.id == 3038 || entityDef.id == 3039 || entityDef.id == 3040 || entityDef.id == 3047 || entityDef.id == 3048 || entityDef.id == 3050 ||  entityDef.id == 3052 || entityDef.id == 3053 || entityDef.id == 3054 ||  entityDef.id == 3059 || entityDef.id == 3060 || entityDef.id == 3061|| entityDef.id == 6312 || entityDef.id == 6313 || entityDef.id == 6315 || entityDef.id == 6316 || entityDef.id == 6317 || entityDef.id == 6318 || entityDef.id == 6319 || entityDef.id == 6320 || entityDef.id == 6311 || entityDef.id == 9805 || entityDef.id == 6431|| entityDef.id == 10 // null
                    || entityDef.id == 3062

                    // NECRO
                    || entityDef.id == 3151
                    || entityDef.id == 3154
                    || entityDef.id == 3153
                    || entityDef.id == 3155
                    || entityDef.id == 3156
                    || entityDef.id == 3158
                    || entityDef.id == 3159
                    || entityDef.id == 3160
                    || entityDef.id == 3161
                    || entityDef.id == 3162
                    || entityDef.id == 3163
                    || entityDef.id == 3164
                    || entityDef.id == 3165
                    || entityDef.id == 3166
                    || entityDef.id == 3167
                    || entityDef.id == 3700
                    || entityDef.id == 3701
                    || entityDef.id == 3702
                    || entityDef.id == 3703
                    || entityDef.id == 3704


                 /*   || entityDef.id == 57
                    || entityDef.id == 567
                    || entityDef.id == 6072
                    || entityDef.id == 4437
                    || entityDef.id == 4433


                    || entityDef.id == 6055
                    || entityDef.id == 6061
                    || entityDef.id == 6063
                    || entityDef.id == 6064
                    || entityDef.id == 6059

                    || entityDef.id == 5072
                    || entityDef.id == 5073
                    || entityDef.id == 5074
                    || entityDef.id == 5075
                    || entityDef.id == 5076*/


            ) {
                entityDef.isPet = true;
                menuActionName[menuActionRow] = "@yel@Pick-up " + entityDef.name;
                menuActionID[menuActionRow] = 1075;
                menuActionCmd1[menuActionRow] = index;
                menuActionCmd2[menuActionRow] = x;
                menuActionCmd3[menuActionRow] = y;
                menuActionRow += 1;

            }

            if
            (!entityDef.isPet
                    && entityDef.id != 925
                    && entityDef.id != 451
                    && entityDef.id != 9085
                    && entityDef.id != 1597
                    //
                    && entityDef.id != 3301
                    && entityDef.id != 3302
                    && entityDef.id != 3055
                    && entityDef.id != 3056
                    && entityDef.id != 3057
                    && entityDef.id != 3058
                    && entityDef.id != 3515
                    && entityDef.id != 3515
                    && entityDef.id != 662
                    && entityDef.id != 4651
                    && entityDef.id != 6430
                    && entityDef.id != 6302
                    && entityDef.id != 9015
                    && entityDef.id != 961
                    && entityDef.id != 5604
                    && entityDef.id != 520
                    && entityDef.id != 605
                    && entityDef.id != 3777
                    && entityDef.id != 2009
                    && entityDef.id != 0
                    && entityDef.id != 28
                    && entityDef.id != 494
                    && entityDef.id != 1697
                    && entityDef.id != 9017
                    && entityDef.id != 9013
                    && entityDef.id != 9012
                    && entityDef.id != 9011
                    && entityDef.id != 1898
                    && entityDef.id != 189
                    && entityDef.id != 7349
                    && entityDef.id != 1404
                    && entityDef.id != 3043
                    && entityDef.id != 3044
                    && entityDef.id != 3045
                    && entityDef.id != 7375
                    && entityDef.id != 210
                    && entityDef.id != 220
                    && entityDef.id != 1320
                    && entityDef.id != 9028
                    && entityDef.id != 1850
                    && entityDef.id != 9812
                    && entityDef.id != 1805

                    && entityDef.id != 710
                    && entityDef.id != 711
                    && entityDef.id != 712
                    && entityDef.id != 720
                    && entityDef.id != 883
                    && entityDef.id != 884
                    && entityDef.id != 891
                    && entityDef.id != 539
                    && entityDef.id != 540
                    && entityDef.id != 541
                    && entityDef.id != 542
                    && entityDef.id != 544
                    && entityDef.id != 19
                    && entityDef.id != 837
                    && entityDef.id != 1745
                    && entityDef.id != 9841
                    && entityDef.id != 436
                    && entityDef.id != 9022
                    && entityDef.id != 18
                    && entityDef.id != 104
                    && entityDef.id != 459
                    && entityDef.id != 1014
                    && entityDef.id != 94
                    && entityDef.id != 5079
                    && entityDef.id != 6824
                    && entityDef.id != 9842
                    && entityDef.id != 6800
                    && entityDef.id != 6851
                    && entityDef.id != 6105
                    && entityDef.id != 6104
                    && entityDef.id != 5388
                    && entityDef.id != 6091
                    && entityDef.id != 5389
                    && entityDef.id != 5411
                    && entityDef.id != 5391
                    && entityDef.id != 91
                    && entityDef.id != 100
                    && entityDef.id != 211
                    && entityDef.id != 212
                    && entityDef.id != 213
                    && entityDef.id != 214
                    && entityDef.id != 215
                    && entityDef.id != 5040
                    && entityDef.id != 2676
                    && entityDef.id != 249
                    && entityDef.id != 2899
                    && entityDef.id != 8459
                    && entityDef.id != 217

                    && entityDef.id != 4885
                    && entityDef.id != 655

                    && entityDef.id != 216
                    && entityDef.id != 248
                    && entityDef.id != 1203
                    && entityDef.id != 568
                    && entityDef.id != 3378


                    && entityDef.id != 1717
                    && entityDef.id != 1718
                    && entityDef.id != 1719
                    && entityDef.id != 1721

                    && entityDef.id != 2209
                    && entityDef.id != 8275
                    && entityDef.id != 1396
                    && entityDef.id != 2262
                   /* && entityDef.id != 309
                    && entityDef.id != 310
                    && entityDef.id != 315
                    && entityDef.id != 314
                    && entityDef.id != 316*/
                    && entityDef.id != 3168
                    && entityDef.id != 102
                    && entityDef.id != 230
                    && entityDef.id != 231
                    && entityDef.id != 1060
                    && entityDef.id != 767
                    && entityDef.id != 1716
                    && entityDef.id != 305
                    && entityDef.id != 306
                    && entityDef.id != 2019
                    && entityDef.id != 2022
                    && entityDef.id != 2021
                    && entityDef.id != 2023
                    && entityDef.id != 2024
                    && entityDef.id != 2025
                    && entityDef.id != 1853
                    && entityDef.id != 1854
                    && entityDef.id != 1855

                    && entityDef.id != 13628
                    && entityDef.id != 13629
                    && entityDef.id != 13630
                    && entityDef.id != 13631
                    && entityDef.id != 300
                    && entityDef.id != 301

                    && entityDef.id != 57
                    && entityDef.id != 567
                    && entityDef.id != 6072
                    && entityDef.id != 4437
                    && entityDef.id != 4433


                    && entityDef.id != 6055
                    && entityDef.id != 6061
                    && entityDef.id != 6063
                    && entityDef.id != 6064
                    && entityDef.id != 6059



                    && entityDef.id != 5072
                    && entityDef.id != 5073
                    && entityDef.id != 5074
                    && entityDef.id != 5075
                    && entityDef.id != 5076

                    && entityDef.id != 931
                    && entityDef.id != 22
                    && entityDef.id != 554

                    && entityDef.id != 2709
                    && entityDef.id != 2710
                    && entityDef.id != 2711
                    && entityDef.id != 1199
                    && entityDef.id != 2130


                    && entityDef.id != 599) {
                    menuActionName[menuActionRow] = "<col=AF70C3>View Drops";
                    menuActionID[menuActionRow] = 1025;
                    menuActionCmd1[menuActionRow] = index;
                    menuActionCmd2[menuActionRow] = x;
                    menuActionCmd3[menuActionRow] = y;
                    menuActionRow++;
                }
            }
            }



    private void buildAtPlayerMenu(int i, int j, Player player, int k) {
        if (player == myPlayer) {
            return;
        }

        if (menuActionRow >= 400) {
            return;
        }

        if (!Configuration.RENDER_PLAYERS)
            return;

        String menuTooltip = "";
        String title = "";

        if (player.loyaltyTitle != null && !player.loyaltyTitle.isEmpty()) {
            title = "<col=" + Integer.toHexString(player.loyaltyColor) + ">" + player.loyaltyTitle.trim() + "</col> ";
        }


        if (player.playerRights == 0) {
            menuTooltip = menuTooltip + "";
        }
        if (player.playerRights == 1) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Mod]@red@ ";
        }
        if (player.playerRights == 2) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Admin@red@ ";
        }
        if (player.playerRights == 3) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Support@red@ ";
        }
        if (player.playerRights == 4) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Owner]@red@ ";
        }
        if (player.playerRights == 5) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Manager]@whi@ ";
        }
        if (player.playerRights == 6) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Manager]@whi@ ";
        }
        if (player.playerRights == 7) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Co Owner]@whi@ ";
        }
        if (player.playerRights == 8) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Developer]@whi@ ";
        }
        if (player.playerRights == 9) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Youtuber]@whi@ ";
        }
        if (player.playerRights == 10) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Adept]@whi@ ";
        }
        if (player.playerRights == 11) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Ethereal]@whi@ ";
        }
        if (player.playerRights == 12) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Mythic]@whi@ ";
        }
        if (player.playerRights == 13) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "<col=AF70C3>[Archon]@whi@ ";
        }
        if (player.playerRights == 14) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Celestial]@whi@ ";
        }
        if (player.playerRights == 15) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Ascendant]@whi@ ";
        }
        if (player.playerRights == 16) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Gladiator]@whi@ ";
        }
        if (player.playerRights == 17) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Events Host]@whi@ ";
        }
        if (player.playerRights == 18) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Events Admin]@red@ ";
        }

        if (player.playerRights == 19) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) +"><col=AF70C3>[Cosmic]@whi@ ";
        }
        if (player.playerRights == 20) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Guardian]@whi@ ";
        }
        if (player.playerRights == 21) {
            menuTooltip = menuTooltip + "<img=" + spriteForRights(player.playerRights) + "><col=AF70C3>[Corrupt]@whi@ ";
        }



        if (player.combatLevel == 0) {
            menuTooltip = title + player.name + combatDiffColor(myPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
        } else {
            menuTooltip += title + player.name + combatDiffColor(myPlayer.combatLevel, player.combatLevel) + " (level-" + player.combatLevel + ")";
        }

        if (itemSelected == 1) {
            menuActionName[menuActionRow] = "Use " + selectedItemName + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "("+selectedItemId+")" : "") + " with @whi@" + menuTooltip;
            menuActionID[menuActionRow] = 491;
            menuActionCmd1[menuActionRow] = j;
            menuActionCmd2[menuActionRow] = i;
            menuActionCmd3[menuActionRow] = k;
            menuActionRow++;
        } else if (spellSelected == 1) {
            if ((spellUsableOn & 8) == 8) {
                menuActionName[menuActionRow] = spellTooltip + " @whi@" + menuTooltip;
                menuActionID[menuActionRow] = 365;
                menuActionCmd1[menuActionRow] = j;
                menuActionCmd2[menuActionRow] = i;
                menuActionCmd3[menuActionRow] = k;
                menuActionRow++;
            }
        } else {
            for (int index = 6; index >= 0; index--) {
                if (atPlayerActions[index] != null) {
                    menuActionName[menuActionRow] = atPlayerActions[index] + " @whi@" + menuTooltip;
                    char c = '\0';

                    if (atPlayerActions[index].equalsIgnoreCase("attack")) {
                        // if (player.combatLevel > myPlayer.combatLevel) { //1 CLICK ATTACK
                        // c = '\u07D0';
                        // }
                        if (myPlayer.team != 0 && player.team != 0) {
                            if (myPlayer.team == player.team) {
                                c = '\u07D0';
                            } else {
                                c = '\0';
                            }
                        }
                    } else if (atPlayerArray[index]) {
                        c = '\u07D0';
                    }

                    if (index == 0) {
                        menuActionID[menuActionRow] = 561 + c;
                    }

                    if (index == 1) {
                        menuActionID[menuActionRow] = 779 + c;
                    }

                    if (index == 2) {
                        menuActionID[menuActionRow] = 27 + c;
                    }

                    if (index == 3) {
                        menuActionID[menuActionRow] = 577 + c;
                    }

                    if (index == 4) {
                        menuActionID[menuActionRow] = 729 + c;
                    }

                    if (index == 5) {
                        menuActionID[menuActionRow] = 8300;
                    }
                    if (index == 6) {
                        menuActionID[menuActionRow] = 8305;
                    }
                    menuActionCmd1[menuActionRow] = j;
                    menuActionCmd2[menuActionRow] = i;
                    menuActionCmd3[menuActionRow] = k;
                    menuActionRow++;
                }
            }
        }

        for (int i1 = 0; i1 < menuActionRow; i1++) {
            if (menuActionID[i1] == 516) {
                menuActionName[i1] = "Walk here @whi@" + menuTooltip;
                return;
            }
        }
    }

    private void buildChatAreaMenu(int yOffset) {
        if (!isGameFrameVisible() || chatArea.componentHidden()) {
            return;
        }

        if (inputDialogState == 5) {
            return;
        }


        int messages = 0;

        for (int index = 0; index < 500; index++) {
            if (chatMessages[index] == null) {
                continue;
            }

            int currentType = chatTypes[index];
            int k1 = 70 - messages * 14 + 42 + anInt1089 + 4 + 5;
            if (k1 < -23) {
                break;
            }

            String player_name = chatNames[index];

            if (chatTypeView == 1) {
                buildPublicChat(yOffset);
                break;
            }

            if (chatTypeView == 2) {
                buildFriendChat(yOffset);
                break;
            }

            if (chatTypeView == 3 || chatTypeView == 4) {
                buildDuelorTrade(yOffset);
                break;
            }

            if (inputDialogState == 3) {
                getGrandExchange().buildItemSearch(yOffset);
                break;
            }

            if (chatTypeView == 5) {
                break;
            }

            if (player_name != null) {
                if (player_name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(player_name);
                    player_name = player_name.substring(prefixSubstring);
                }
                if (player_name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(player_name);
                    player_name = player_name.substring(prefixSubstring);
                }
            }
            if (player_name != null && player_name.startsWith("<col=")) {
                player_name = player_name.substring(player_name.indexOf("</col>") + 6);
            }
            if (currentType == 0) {
                messages++;
            }

            if ((currentType == 1 || currentType == 2) && (currentType == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(player_name))) {
                if (yOffset > k1 - 14 && yOffset <= k1 && !myPlayer.name.equals(NAME_PATTERN.matcher(player_name)
                        .replaceAll(""))) {
                    if (!isFriendOrSelf(player_name)) {
                        menuActionName[menuActionRow] = "Add ignore @whi@" + player_name;
                        menuActionID[menuActionRow] = 42;
                        menuActionRow++;
                        menuActionName[menuActionRow] = "Add friend @whi@" + player_name;
                        menuActionID[menuActionRow] = 337;
                        menuActionRow++;
                    } else if (isFriendOrSelf(player_name)) {
                        menuActionName[menuActionRow] = "Message @whi@" + player_name;
                        menuActionID[menuActionRow] = 2639;
                        menuActionRow++;
                    }
                }

                messages++;
            }

            if ((currentType == 3 || currentType == 7) && splitPrivateChat == 0 && (currentType == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(player_name))) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    if (!isFriendOrSelf(player_name)) {
                        menuActionName[menuActionRow] = "Add ignore @whi@" + player_name;
                        menuActionID[menuActionRow] = 42;
                        menuActionRow++;
                        menuActionName[menuActionRow] = "Add friend @whi@" + player_name;
                        menuActionID[menuActionRow] = 337;
                        menuActionRow++;
                    } else if (isFriendOrSelf(player_name)) {
                        menuActionName[menuActionRow] = "Message @whi@" + player_name;
                        menuActionID[menuActionRow] = 2639;
                        menuActionRow++;
                    }

                }

                messages++;
            }

            if (currentType == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(player_name))) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept trade @whi@" + player_name;
                    menuActionID[menuActionRow] = 484;
                    menuActionRow++;
                }

                messages++;
            }

            if ((currentType == 5 || currentType == 6) && splitPrivateChat == 0 && privateChatMode < 2) {
                messages++;
            }
            if (currentType == 8 && (duelStatus == 0 || duelStatus == 1 && isFriendOrSelf(player_name))) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept challenge @whi@" + player_name;
                    menuActionID[menuActionRow] = 6;
                    menuActionRow++;
                }

                messages++;
            }

            if (currentType == 21) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept gamble @whi@" + player_name;
                    menuActionID[menuActionRow] = 1673;
                    menuActionRow++;
                }
                messages++;
            }

            if (currentType == 22) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept raid request @whi@" + player_name;
                    menuActionID[menuActionRow] = 1773;
                    menuActionRow++;
                }
                messages++;
            }

        }
    }

    private void buildDuelorTrade(int yOffset) {
        int l = 0;

        for (int i1 = 0; i1 < 500; i1++) {
            if (chatMessages[i1] == null) {
                continue;
            }

            if (chatTypeView != 3 && chatTypeView != 4) {
                continue;
            }

            int chatType = chatTypes[i1];
            String name = chatNames[i1];
            int k1 = 70 - l * 14 + 42 + anInt1089 + 4 + 5;

            if (k1 < -23) {
                break;
            }

            if (name != null) {
                if (name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    name = name.substring(prefixSubstring);
                }
                if (name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    name = name.substring(prefixSubstring);
                }
            }

            if (chatTypeView == 3 && chatType == 4 && (tradeMode == 0 || tradeMode == 1 && isFriendOrSelf(name))) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept trade @whi@" + name;
                    menuActionID[menuActionRow] = 484;
                    menuActionRow++;
                }

                l++;
            }

            if (chatTypeView == 4 && chatType == 8 && (duelStatus == 0 || duelStatus == 1 && isFriendOrSelf(name))) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept challenge @whi@" + name;
                    menuActionID[menuActionRow] = 6;
                    menuActionRow++;
                }

                l++;
            }

            if (chatType == 12) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Go-to @blu@" + name;
                    menuActionID[menuActionRow] = 915;
                    menuActionRow++;
                }

                l++;
            }

            if (chatType == 21) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept gamble @whi@" + name;
                    menuActionID[menuActionRow] = 1673;
                    menuActionRow++;
                }
                l++;
            }

            if (chatType == 22) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    menuActionName[menuActionRow] = "Accept raid request @whi@" + name;
                    menuActionID[menuActionRow] = 1773;
                    menuActionRow++;
                }
                l++;
            }

        }
    }

    private void drawParallelWidgets() {
        if (this.parallelWidgetList.size() <= 0) {
            return;
        }
        for (RSInterface widget : this.parallelWidgetList) {
            if (widget != null) {

                // Only works on main window interfaces! if widget.height = 700
                if (widget.xOffset + widget.width > 512)
                    widget.xOffset = 512 - widget.width;
                if (widget.yOffset + widget.height > 334)
                    widget.yOffset = 334 - widget.height;
                if (widget.yOffset < 0)
                    widget.yOffset = 0;
                if (widget.xOffset < 0)
                    widget.xOffset = 0;
                switch (widget.id) {
                    case 112000:
                        widget.xOffset = GameFrame.getScreenMode() != ScreenMode.FIXED ? 0 : 0;
                        widget.yOffset = GameFrame.getScreenMode() != ScreenMode.FIXED ? clientHeight - 500 : 0;
                        break;
                    case 32750:
                        widget.xOffset = GameFrame.getScreenMode() != ScreenMode.FIXED ? 0 : 0;
                        widget.yOffset = GameFrame.getScreenMode() != ScreenMode.FIXED ? clientHeight - 500 : 0;
                        break;
                    case 126500:
                        widget.xOffset = GameFrame.getScreenMode() != ScreenMode.FIXED ? clientWidth - 775 : 0;
                        widget.yOffset = GameFrame.getScreenMode() != ScreenMode.FIXED ?  0 : 0;
                        break;

                    case 28710:
                        if (RSInterface.interfaceCache[28714].message.length() < 1)
                            continue;
                        widget.xOffset = GameFrame.getScreenMode() == ScreenMode.FIXED ? 392 : clientWidth - 150;
                        if (RSInterface.interfaceCache[28740].interfaceShown) {
                            widget.yOffset = GameFrame.getScreenMode() == ScreenMode.FIXED ? 280 : 180;
                        } else {
                            widget.yOffset = GameFrame.getScreenMode() == ScreenMode.FIXED ? 163 : 180;
                        }
                        break;
                    case 48308:
                    case 48304:
                    case 48300:
                    case 42112:
                    case 42120:
                        if(GameFrame.getScreenMode() == ScreenMode.RESIZABLE_CLASSIC || GameFrame.getScreenMode() == ScreenMode.RESIZABLE_MODERN) {
                            widget.xOffset = -445;
                        }
                        break;

                }
                drawInterface(0, widget.xOffset, widget, widget.yOffset);
            }
        }
    }

    private void buildFriendChat(int yOffset) {
        int count = 0;

        for (int index = 0; index < 500; index++) {
            if (chatMessages[index] == null) {
                continue;
            }

            if (chatTypeView != 2) {
                continue;
            }

            int type = chatTypes[index];
            String player_name = chatNames[index];
            int k1 = 70 - count * 14 + 42 + anInt1089 + 4 + 5;
            if (k1 < -23) {
                break;
            }

            if (player_name != null) {
                if (player_name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    player_name = player_name.substring(prefixSubstring);
                }
                if (player_name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    player_name = player_name.substring(prefixSubstring);
                }
            }

            if ((type == 5 || type == 6) && (splitPrivateChat == 0 || chatTypeView == 2) && (type == 6 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(player_name))) {
                count++;
            }

            if ((type == 3 || type == 7) && (splitPrivateChat == 0 || chatTypeView == 2) && (type == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(player_name))) {
                if (yOffset > k1 - 14 && yOffset <= k1) {
                    if (!isFriendOrSelf(player_name)) {
                        menuActionName[menuActionRow] = "Add ignore @whi@" + player_name;
                        menuActionID[menuActionRow] = 42;
                        menuActionRow++;
                        menuActionName[menuActionRow] = "Add friend @whi@" + player_name;
                        menuActionID[menuActionRow] = 337;
                        menuActionRow++;
                    } else if (isFriendOrSelf(player_name)) {
                        menuActionName[menuActionRow] = "Message @whi@" + player_name;
                        menuActionID[menuActionRow] = 2639;
                        menuActionRow++;
                    }
                }

                count++;
            }
        }
    }

    private boolean buildFriendsListMenu(RSInterface class9) {
        int i = class9.contentType;

        if (i >= 1 && i <= 200 || i >= 701 && i <= 900) {
            if (i >= 801) {
                i -= 701;
            } else if (i >= 701) {
                i -= 601;
            } else if (i >= 101) {
                i -= 101;
            } else {
                i--;
            }

            menuActionName[menuActionRow] = "Remove @whi@" + friendsList[i];
            menuActionID[menuActionRow] = 792;
            menuActionRow++;
            menuActionName[menuActionRow] = "Message @whi@" + friendsList[i];
            menuActionID[menuActionRow] = 639;
            menuActionRow++;
            return true;
        }

        if (i >= 401 && i <= 500) {
            menuActionName[menuActionRow] = "Remove @whi@" + class9.message;
            menuActionID[menuActionRow] = 322;
            menuActionRow++;
            return true;
        } else {
            return false;
        }
    }

    private void scratch(int x, int y, int radius, Sprite sprite) {
        int radiusSquare = radius * radius;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                if (dx * dx + dy * dy < radiusSquare) {
                    int finalX = x + dx;
                    int finalY = y + dy;

                    if (finalX < 0 || finalX >= sprite.myWidth) {
                        continue;
                    }
                    if (finalY < 0 || finalY >= sprite.myHeight) {
                        continue;
                    }
                    sprite.setPixels(finalX, finalY, 0);
                }
            }
        }
    }

    private void buildInterfaceMenu(int xPadding, RSInterface rsInterface, int xPos, int yPadding, int yPos, int scrollPoint) {
        if (rsInterface == null) {
            rsInterface = RSInterface.interfaceCache[21356];
        }

        if (rsInterface.type != 0 || rsInterface.children == null || rsInterface.interfaceShown || !rsInterface.isVisible()) {
            return;
        } // oh

        if (xPos < xPadding || yPos < yPadding || xPos > xPadding + rsInterface.width || yPos > yPadding + rsInterface.height) {
            return;
        }

        int totalChildren = rsInterface.children.length;

        for (int index = 0; index < totalChildren; index++) {
            int childX = (rsInterface.childX[index] + xPadding) - (rsInterface.sideScroll ? scrollPoint : 0);
            int childY = (rsInterface.childY[index] + yPadding) - (rsInterface.sideScroll ? 0 : scrollPoint);
            RSInterface child = RSInterface.interfaceCache[rsInterface.children[index]];
            if (child == null) {
                continue;
            }

            if (!child.active) {
                continue;
            }

            int offset = child.xOffset;
            childX += offset;
            childY += child.yOffset;

            if (super.clickMode3 != 0) {
                xPos = super.clickX;
                yPos = super.clickY;
            }

            if ((child.hoverType >= 0 || child.disabledMouseOverColor != 0) && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                if (child.hoverType >= 0) {
                    anInt886 = child.hoverType;
                } else {
                    anInt886 = child.id;
                }
            }

            if ((child.type == 8 || child.type == 10) && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                anInt1315 = child.id;
                if (child.id != skillTabHoverChild) {
                    skillTabHoverChild = child.id;
                }
            }
            if (child.type == RSInterface.TOOLTIP_COMPONENT && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                anInt1315 = child.id;
                if (child.id != widgetHoverChild) {
                    widgetHoverChild = child.id;
                }
            }
            if (child.type == RSInterface.PROGRESS_BAR_W_MILESTONES) {
                if (child.milestones != null) {
                    for (int i = 0; i < child.milestones.length; i++) {
                        int perce = (int) MathUtils.calculatePercentage(child.milestones[i].getMilestone(), child.milestones[child.milestones.length - 1].getMilestone());
                        if (perce > 100)
                            perce = 100;
                        int x_mod = getPercent(child.progressBarWidth, perce);
                        int mouseMod = 0;
                        if (x_mod >= 8)
                            mouseMod = -3;
                        if (x_mod < 7) //Prevents the first milestone from drawing in a bad location. If the Value of the first milestone is low compared to later.
                            x_mod = 3;
                        if (i == child.milestones.length -1) //Prevents the last milestone from drawing in a slightly bad location.
                            mouseMod = -10;
                        if (mouseInRegion(childX + x_mod + mouseMod, childX + x_mod + mouseMod + 5, childY + 1, childY + 24)) {
                            hovering = true;
                            if (milestoneHoverComp != i)
                                milestoneHoverComp = i;
                            if (donationTabHoverChild != child.id)
                                donationTabHoverChild = child.id;
                        }
                    }
                }
            }
            if (child.type == 0) {
                buildInterfaceMenu(childX, child, xPos, childY, yPos, child.scrollPosition);

                if (child.sideScroll) {
                    if (child.scrollMax > child.width) {
                        scrollInterfaceBottom(childX, child.width, xPos, yPos, child, childY + child.height, true, child.scrollMax);
                    }
                } else {
                    if (child.scrollMax > child.height) {
                        scrollInterface(childX + child.width, child.height, xPos, yPos, child, childY, true, child.scrollMax);
                    }
                }
            } else {


                if (child.type == 100 && mousePressed && child.unrevealedSprite != null) {
                    // System.out.println("Called");
                    //   System.out.println("Isnt null!");
                    if (xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) { // checks if mouse is inside the sprite

                        int scratchX = mouseX - childX;
                        int scratchY = mouseY - childY;

                        scratch(scratchX, scratchY, 20, child.unrevealedSprite);
                    }
                }

                child.selected = false;

                if (child.hoverable && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                    child.selected = true;

                    if (child.atActionType == 0) {
                        String tooltip = child.tooltip;
                        if (tooltip != null) {
                            if (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ) {
                                if (!tooltip.isEmpty()) {
                                    tooltip += " - Id: " + child.id;
                                }
                            }
                            menuActionName[menuActionRow] = tooltip;
                            menuActionID[menuActionRow] = 777;
                            menuActionCmd3[menuActionRow] = child.id;
                            menuActionRow++;
                        }
                    }

                }

                //if(child.tooltip != null && child.tooltip.contains("Upgrade"))
                //System.out.println(child.atActionType);
                if ((child.atActionType == 1 || child.atActionType == 9) && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                    if (child.parentID >= 113500 && child.parentID < 114000 && child.message.equalsIgnoreCase("")) {

                    } else {
                        if (child.type == 42) {
                            childHovered = child;
                        }
                        boolean flag = false, flag1 = false;
                        if (child.contentType != 0) {
                            flag = buildFriendsListMenu(child);
                        }

                        if (!flag && !flag1) {
                            if (child.actions != null) {
                                for (int i = child.actions.length - 1; i >= 0; i--) {
                                    String s = child.actions[i];
                                    if (s != null) {
                                        menuActionName[menuActionRow] = s;
                                        menuActionID[menuActionRow] = 222;
                                        menuActionCmd3[menuActionRow] = child.id;
                                        currentActionMenu = menuActionRow;
                                        menuActionRow++;
                                    }
                                }
                            }
                            String tooltip = child.tooltip;
                            if (tooltip != null) {
                                if (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7) {
                                    tooltip += " - Id: " + child.id;
                                }
                                if (tooltip.contains("[GE")) {
                                    continue;
                                }
                                menuActionName[menuActionRow] = tooltip;
                                menuActionID[menuActionRow] = 315;
                                menuActionCmd3[menuActionRow] = child.id;
                                menuActionRow++;
                            }
                        }
                    }
                }
                if (child.atActionType == 2 && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                    String s = child.selectedActionName;

                    if (s.indexOf(" ") != -1) {
                        s = s.substring(0, s.indexOf(" "));
                    }

                    if (child.spellName.endsWith("Rush") || child.spellName.endsWith("Burst") || child.spellName.endsWith("Blitz") || child.spellName.endsWith("Barrage") || child.spellName.endsWith("strike") || child.spellName.endsWith("bolt") || child.spellName.equals("Crumble undead") || child.spellName.endsWith("blast") || child.spellName.endsWith("wave") || child.spellName.equals("Claws of Guthix") || child.spellName.equals("Flames of Zamorak") || child.spellName.equals("Magic Dart")) {
                        menuActionName[menuActionRow] = "Autocast @gre@" + child.spellName;
                        menuActionID[menuActionRow] = 104;
                        menuActionCmd3[menuActionRow] = child.id;
                        menuActionRow++;
                    }

                    menuActionName[menuActionRow] = s + " @gre@" + child.spellName + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? ", " + child.id : "");
                    menuActionID[menuActionRow] = 626;
                    menuActionCmd3[menuActionRow] = child.id;
                    menuActionRow++;
                }

                if (child.atActionType == RSInterface.OPTION_CLOSE && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                    menuActionName[menuActionRow] = "Close";
                    menuActionID[menuActionRow] = 200;
                    menuActionCmd3[menuActionRow] = child.id;
                    menuActionRow++;
                }

                if (child.atActionType == 4 && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                    if (child.id == 15281) {
                        child.tooltip = (cosmeticTab ? "Switch to Equipment Tab" : "Switch to Cosmetic Tab");
                    }
                    menuActionName[menuActionRow] = child.tooltip + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? ", " + child.id : "");
                    menuActionID[menuActionRow] = 169;
                    menuActionCmd3[menuActionRow] = child.id;
                    menuActionRow++;

                    if (child.tooltipBoxText != null) {
                        // drawHoverBox(k, l, class9_1.hoverText);
                    }
                }

                if (child.atActionType == 5 && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                    childHovered = child;
                    menuActionName[menuActionRow] = child.tooltip + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? ", " + child.id : "");
                    menuActionID[menuActionRow] = 646;
                    menuActionCmd3[menuActionRow] = child.id;
                    menuActionRow++;
                }

                if (child.atActionType == 6 && !aBoolean1149 && xPos >= childX && yPos >= childY && xPos < childX + child.width && yPos < childY + child.height) {
                    menuActionName[menuActionRow] = child.tooltip + (myRights == 4 || myRights == 5 || myRights == 8  ||myRights == 7? ", " + child.id : "");
                    menuActionID[menuActionRow] = 679;
                    menuActionCmd3[menuActionRow] = child.id;
                    menuActionRow++;
                }

                if (mousePressed && child.type == RSInterface.TYPE_RGB_PICKER) {
                    if (mouseX >= childX && mouseY >= childY
                            && mouseX < childX + child.width && mouseY < childY + child.height) {
                        child.rgbPicker.handlePick(mouseX - childX, mouseY - childY);
                    }
                }

                if (mousePressed & child.type == RSInterface.TYPE_SLIDER) {
                    if (mouseX >= childX && mouseY >= childY
                            && mouseX < childX + child.width && mouseY < childY + child.height) {
                        int sliderX = mouseX - childX;
                        child.widgetSlider.handleSlider(sliderX);
                    }
                }

                if (child.atActionType == RSInterface.OPTION_DROPDOWN) {
                    boolean flag = false;
                    child.hovered = false;
                    child.dropdownHover = -1;

                    if (child.dropdown.isOpen()) {

                        // Inverted keybinds dropdown
                        if (child.type == RSInterface.TYPE_KEYBINDS_DROPDOWN && child.inverted && xPos >= childX &&
                            xPos < childX + (child.dropdown.getWidth() - 16) && yPos >= childY - child.dropdown.getHeight() - 10 && yPos < childY) {

                            int yy = yPos - (childY - child.dropdown.getHeight());

                            if (xPos > childX + (child.dropdown.getWidth() / 2)) {
                                child.dropdownHover = ((yy / 15) * 2) + 1;
                            } else {
                                child.dropdownHover = (yy / 15) * 2;
                            }
                            flag = true;
                        } else if (!child.inverted && xPos >= childX && xPos < childX + (child.dropdown.getWidth() - 16) &&
                            yPos >= childY + 19 && yPos < childY + 19 + child.dropdown.getHeight()) {

                            int yy = yPos - (childY + 19);

                            if (child.type == RSInterface.TYPE_KEYBINDS_DROPDOWN && child.dropdown.doesSplit()) {
                                if (xPos > childX + (child.dropdown.getWidth() / 2)) {
                                    child.dropdownHover = ((yy / 15) * 2) + 1;
                                } else {
                                    child.dropdownHover = (yy / 15) * 2;
                                }
                            } else {
                                child.dropdownHover = yy / 14; // Regular dropdown hover
                            }
                            flag = true;
                        }
                        if (flag) {
                            if (menuActionRow != 1) {
                                menuActionRow = 1;
                            }
                            menuActionName[menuActionRow] = "Select";
                            menuActionID[menuActionRow] = 770;
                            menuActionCmd3[menuActionRow] = child.id;
                            menuActionCmd2[menuActionRow] = child.dropdownHover;
                            menuActionCmd1[menuActionRow] = child.parentID;
                            menuActionRow++;
                        }
                    }
                    if (xPos >= childX && yPos >= childY && xPos < childX + child.dropdown.getWidth() && yPos < childY + 24 && menuActionRow == 1) {
                        child.hovered = true;
                        menuActionName[menuActionRow] = child.dropdown.isOpen() ? "Hide" : "Show";
                        menuActionID[menuActionRow] = 769;
                        menuActionCmd3[menuActionRow] = child.id;
                        menuActionCmd1[menuActionRow] = child.parentID;
                        menuActionRow++;
                    }
                }

                if (child.type == RSInterface.PROGRESS_BAR_W_MILESTONES) {
                    if (milestoneHoverComp > -1) {
                        drawHintMenuDono(child);
                    }
                }

                if (child.type == 2 && child.parentID != 0) {
                    int containerSlot = 0;

                    for (int l2 = 0; l2 < child.height; l2++) {
                        for (int i3 = 0; i3 < child.width; i3++) {
                            int j3 = childX + i3 * (32 + child.invSpritePadX);
                            int k3 = childY + l2 * (32 + child.invSpritePadY);

                            if (containerSlot < child.spritesX.length) {
                                j3 += child.spritesX[containerSlot];
                                k3 += child.spritesY[containerSlot];
                            }

                            if (xPos >= j3 && yPos >= k3 && xPos < j3 + 32 && yPos < k3 + 32) {
                                mouseInvInterfaceIndex = containerSlot;
                                lastActiveInvInterface = child.id;
                                if (child.inv[containerSlot] > 0) {
                                    int id = child.inv[containerSlot] - 1;
                                    if (id > ItemDef.totalItems) {
                                        continue;
                                    }
                                    ItemDef itemDef = ItemDef.get(id);

                                    if (itemSelected == 1 && child.isInventoryInterface) {
                                        if (child.id != anInt1284 || containerSlot != anInt1283) {
                                            menuActionName[menuActionRow] = "Use " + selectedItemName + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "("+selectedItemId+")" : "") + " with @lre@" + itemDef.name + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "@whi@("+itemDef.id+")" : "");
                                            menuActionID[menuActionRow] = 870;
                                            menuActionCmd1[menuActionRow] = itemDef.id;
                                            menuActionCmd2[menuActionRow] = containerSlot;
                                            menuActionCmd3[menuActionRow] = child.id;
                                            menuActionRow++;
                                        }
                                    } else if (spellSelected == 1 && child.isInventoryInterface) {
                                        if ((spellUsableOn & 0x10) == 16) {
                                            menuActionName[menuActionRow] = spellTooltip + " @lre@" + itemDef.name;
                                            menuActionID[menuActionRow] = 543;
                                            menuActionCmd1[menuActionRow] = itemDef.id;
                                            menuActionCmd2[menuActionRow] = containerSlot;
                                            menuActionCmd3[menuActionRow] = child.id;
                                            menuActionRow++;
                                        }
                                    } else {/*
                                     * if (children.isInventoryInterface) { if (openInterfaceID != 24700 &&
                                     * openInterfaceID != 2700) { for (int l3 = 4; l3 >= 3; l3--) { if
                                     * (definition.actions != null && definition.actions[l3] != null ||
                                     * lootingBag) { if (lootingBag) { menuActionCmd1[menuActionRow] =
                                     * definition.id; menuActionCmd2[menuActionRow] = k2;
                                     * menuActionCmd3[menuActionRow] = children.id;
                                     *
                                     * if (l3 == 1) { menuActionName[menuActionRow] = "Deposit" + " @lre@" +
                                     * definition.name; } else { menuActionName[menuActionRow] = null; }
                                     *
                                     * if (l3 == 3) { menuActionID[menuActionRow] = 493; }
                                     *
                                     * if (l3 == 4) { menuActionID[menuActionRow] = 847; }
                                     *
                                     * menuActionCmd1[menuActionRow] = definition.id;
                                     * menuActionCmd2[menuActionRow] = k2; menuActionCmd3[menuActionRow] =
                                     * children.id; menuActionRow++; continue; } menuActionName[menuActionRow] =
                                     * definition.actions[l3] + " @lre@" + definition.name;
                                     *
                                     * if (l3 == 3) { menuActionID[menuActionRow] = 493; }
                                     *
                                     * if (l3 == 4) { menuActionID[menuActionRow] = 847; }
                                     *
                                     * menuActionCmd1[menuActionRow] = definition.id;
                                     * menuActionCmd2[menuActionRow] = k2; menuActionCmd3[menuActionRow] =
                                     * children.id; menuActionRow++; } else if (l3 == 4) {
                                     * menuActionName[menuActionRow] = "Drop @lre@" + definition.name;
                                     * menuActionID[menuActionRow] = 847; menuActionCmd1[menuActionRow] =
                                     * definition.id; menuActionCmd2[menuActionRow] = k2;
                                     * menuActionCmd3[menuActionRow] = children.id; menuActionRow++; } } } }
                                     */

                                        if (child.isInventoryInterface && !(shiftIsDown && shiftDrop)) {
                                            if (openInterfaceID != 24700 && openInterfaceID != 2700 && openInterfaceID != 42200) {
                                                for (int l3 = 4; l3 >= 3; l3--) {
                                                    if (itemDef.actions != null && itemDef.actions[l3] != null) {
                                                        menuActionName[menuActionRow] = itemDef.actions[l3] + " @lre@" + itemDef.name + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "@whi@("+itemDef.id+")" : "");

                                                        if (l3 == 3) {
                                                            menuActionID[menuActionRow] = 493;
                                                        }

                                                        if (l3 == 4) {
                                                            menuActionID[menuActionRow] = 847;
                                                        }

                                                        menuActionCmd1[menuActionRow] = itemDef.id;
                                                        menuActionCmd2[menuActionRow] = containerSlot;
                                                        menuActionCmd3[menuActionRow] = child.id;
                                                        menuActionRow++;
                                                    } else if (l3 == 4) {
                                                        menuActionName[menuActionRow] = "Drop @lre@" + itemDef.name;
                                                        menuActionID[menuActionRow] = 847;
                                                        menuActionCmd1[menuActionRow] = itemDef.id;
                                                        menuActionCmd2[menuActionRow] = containerSlot;
                                                        menuActionCmd3[menuActionRow] = child.id;
                                                        menuActionRow++;
                                                    }
                                                }
                                            }
                                        }

                                        if (child.usableItemInterface) {
                                            if (openInterfaceID == 24700) {
                                                menuActionName[menuActionRow] = "Offer @lre@" + itemDef.name;
                                                menuActionID[menuActionRow] = 24700;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                getGrandExchange().itemSelected = itemDef.id;
                                            } else if (openInterfaceID == 2700) {
                                                menuActionName[menuActionRow] = "Store @lre@" + itemDef.name;
                                                menuActionID[menuActionRow] = 2700;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                            } else if (openInterfaceID == 42200) {
                                                menuActionName[menuActionRow] = "Store @lre@" + itemDef.name;
                                                menuActionID[menuActionRow] = 42200;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                            }
                                            else {
                                                menuActionName[menuActionRow] = "Use @lre@" + itemDef.name;
                                                menuActionID[menuActionRow] = 447;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                            }
                                            menuActionCmd2[menuActionRow] = containerSlot;
                                            menuActionCmd3[menuActionRow] = child.id;
                                            menuActionRow++;
                                        }

                                        if (openInterfaceID != 24700 && openInterfaceID != 42200 && child.isInventoryInterface && itemDef.actions != null) {
                                            for (int i4 = 2; i4 >= 0; i4--) {
                                                if (itemDef.actions[i4] != null) {
                                                    menuActionName[menuActionRow] = itemDef.actions[i4] + " @lre@" + itemDef.name + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "@whi@("+itemDef.id+")" : "");

                                                    if (i4 == 0) {
                                                        menuActionID[menuActionRow] = 74;
                                                    }

                                                    if (i4 == 1) {
                                                        menuActionID[menuActionRow] = 454;
                                                    }

                                                    if (i4 == 2) {
                                                        menuActionID[menuActionRow] = 539;
                                                    }

                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = containerSlot;
                                                    menuActionCmd3[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                }
                                            }
                                        }


                                        if (child.isInventoryInterface && shiftIsDown && shiftDrop) {
                                            for (int l3 = 4; l3 >= 3; l3--) {
                                                if (itemDef.actions != null && itemDef.actions[l3] != null) {
                                                    menuActionName[menuActionRow] = itemDef.actions[l3] + " @lre@" + itemDef.name + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "@whi@("+itemDef.id+")" : "");

                                                    if (l3 == 3) {
                                                        menuActionID[menuActionRow] = 493;
                                                    }

                                                    if (l3 == 4) {
                                                        menuActionID[menuActionRow] = 847;
                                                    }

                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = containerSlot;
                                                    menuActionCmd3[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                } else if (l3 == 4) {
                                                    menuActionName[menuActionRow] = "Drop @lre@" + itemDef.name;
                                                    menuActionID[menuActionRow] = 847;
                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = containerSlot;
                                                    menuActionCmd3[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                }
                                            }
                                        } else if (child.actions != null) {
                                            int interfaceId = child.id;
                                            for (int j4 = 4; j4 >= 0; j4--) {
                                                if ((child.actions[j4] != null
                                                        && !child.actions[j4].equalsIgnoreCase("operate"))
                                                        || (itemDef.equipOptions[menuActionRow] != null
                                                        && child.id == 1688)) {
                                                    if (child.id == 1688) {
                                                        if (itemDef.equipOptions[menuActionRow] != null) {
                                                            menuActionName[menuActionRow] = itemDef.equipOptions[menuActionRow]
                                                                    + " @lre@" + itemDef.name;
                                                        } else {
                                                            if (child.actions[j4] != null) {
                                                                menuActionName[menuActionRow] = child.actions[j4]
                                                                        + " @lre@" + itemDef.name;
                                                            }
                                                        }
                                                        if (j4 == 0)
                                                            menuActionID[menuActionRow] = 632;
                                                        if (j4 == 1)
                                                            menuActionID[menuActionRow] = 661;
                                                        if (j4 == 2)
                                                            menuActionID[menuActionRow] = 662;
                                                        if (j4 == 3)
                                                            menuActionID[menuActionRow] = 663;
                                                        if (j4 == 4)
                                                            menuActionID[menuActionRow] = 664;
                                                    } else {
                                                        if (openInterfaceID != 5292) {
                                                            menuActionName[menuActionRow] = child.actions[j4] + " @lre@" + itemDef.name + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "@whi@(I: "+itemDef.id+")" + "(M:" + itemDef.modelID + ")" : "");;
                                                            if (j4 == 0)
                                                                menuActionID[menuActionRow] = 632;

                                                            if (j4 == 1)
                                                                menuActionID[menuActionRow] = 78;

                                                            if (j4 == 2)
                                                                menuActionID[menuActionRow] = 867;

                                                            if (j4 == 3)
                                                                menuActionID[menuActionRow] = 431;

                                                            if (j4 == 4)
                                                                menuActionID[menuActionRow] = 53;

                                                            if (j4 == 5)
                                                                menuActionID[menuActionRow] = 54;
                                                        } else {
                                                            menuActionName[menuActionRow] = child.actions[j4] + " @lre@" + itemDef.name + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "@whi@(I: "+itemDef.id+")" + "(M:" + itemDef.modelID + ")" : "");;
                                                            if (j4 == 0) {
                                                                if (RSInterface.interfaceCache[571].isNavActive) {
                                                                    menuActionName[menuActionRow] = (child.parentID == 5063 ? "Deposit 1 @lre@" : "Withdraw 1 @lre@") + itemDef.name + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "@whi@(I: "+itemDef.id+")" + "(M:" + itemDef.modelID + ")" : "");;
                                                                    menuActionID[menuActionRow] = 632;
                                                                }
                                                                if (RSInterface.interfaceCache[572].isNavActive) {
                                                                    menuActionName[menuActionRow] = (child.parentID == 5063 ? "Deposit 5 @lre@" : "Withdraw 5 @lre@") + itemDef.name + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "@whi@(I: "+itemDef.id+")" + "(M:" + itemDef.modelID + ")" : "");;
                                                                    menuActionID[menuActionRow] = 78;
                                                                }
                                                                if (RSInterface.interfaceCache[573].isNavActive) {
                                                                    menuActionName[menuActionRow] = (child.parentID == 5063 ? "Deposit 10 @lre@" : "Withdraw 10 @lre@") + itemDef.name + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "@whi@(I: "+itemDef.id+")" + "(M:" + itemDef.modelID + ")" : "");;
                                                                    menuActionID[menuActionRow] = 867;
                                                                }
                                                                if (RSInterface.interfaceCache[574].isNavActive) {
                                                                    menuActionName[menuActionRow] = (child.parentID == 5063 ? "Deposit X @lre@" : "Withdraw X @lre@") + itemDef.name + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "@whi@(I: "+itemDef.id+")" + "(M:" + itemDef.modelID + ")" : "");;
                                                                    menuActionID[menuActionRow] = 53;
                                                                }
                                                                if (RSInterface.interfaceCache[575].isNavActive) {
                                                                    menuActionName[menuActionRow] = (child.parentID == 5063 ? "Deposit All @lre@" : "Withdraw All @lre@") + itemDef.name + (myRights == 4 || myRights == 5  || myRights == 8 ||myRights == 7 ? "@whi@(I: "+itemDef.id+")" + "(M:" + itemDef.modelID + ")" : "");;
                                                                    menuActionID[menuActionRow] = 431;
                                                                }
                                                            }

                                                            if (j4 == 1)
                                                                menuActionID[menuActionRow] = 78;

                                                            if (j4 == 2)
                                                                menuActionID[menuActionRow] = 867;

                                                            if (j4 == 3)
                                                                menuActionID[menuActionRow] = 431;

                                                            if (j4 == 4)
                                                                menuActionID[menuActionRow] = 53;

                                                            if (j4 == 5)
                                                                menuActionID[menuActionRow] = 54;
                                                        }
                                                    }
                                                    if (child.parentID == 5292 || child.parentID == 5063) {
                                                        if (child.actions.length > 5) {
                                                            if (j4 == 6) {
                                                                menuActionID[menuActionRow] = 300;
                                                            }
                                                        }
                                                    }

                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = containerSlot;
                                                    menuActionCmd3[menuActionRow] = interfaceId;
                                                    menuActionRow++;
                                                }
                                            }
                                        }
                                        if (openInterfaceID != 24700 && openInterfaceID != 42200) {
                                            if (!child.hideExamine && child.displayExamine) {
                                                menuActionName[menuActionRow] = "Examine @lre@" + itemDef.name;// + (myRights == 4 ? " (" + itemDef.id + ") @or1@(" + itemDef.maleEquip1 + ")" : "");
                                                menuActionID[menuActionRow] = 1125;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                            } else {
                                                if (child.parentID == 3822 && openInterfaceID == 3824) {
                                                    menuActionName[menuActionRow] = "Sell All @lre@" + itemDef.name;
                                                    menuActionID[menuActionRow] = 1126;
                                                    menuActionCmd1[menuActionRow] = itemDef.id;
                                                    menuActionCmd2[menuActionRow] = containerSlot;
                                                    menuActionCmd3[menuActionRow] = child.id;
                                                    menuActionRow++;
                                                }
                                            }
                                            if (child.id == 32621) {
                                                menuActionName[menuActionRow] = "Buy X @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 431;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Buy 10 @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 300;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Buy 5 @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 291;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Buy 1 @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 867;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Value @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 632;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                            }

                                            if (child.id == 33621) {
                                                menuActionName[menuActionRow] = "Set price @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 78;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Withdraw X @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 431;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Withdraw 10 @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 300;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Withdraw 5 @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 291;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Withdraw 1 @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 867;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                                menuActionName[menuActionRow] = "Value @lre@" + itemDef.name + "";
                                                menuActionID[menuActionRow] = 632;
                                                menuActionCmd1[menuActionRow] = itemDef.id;
                                                menuActionCmd2[menuActionRow] = containerSlot;
                                                menuActionCmd3[menuActionRow] = child.id;
                                                menuActionRow++;
                                            }
                                        }

                                        //


                                        List<Integer> dontHover = new ArrayList<Integer>() {
                                            {
                                                add(1);
                                                add(2);
                                            }
                                        };
                                        itemHover = itemDef.id;

                                        if (itemHover > -1) {
                                            drawHintMenu();
                                        }

                                        if (openInterfaceID == 5292 && child.invStackSizes[containerSlot] == 0) {
                                            menuActionRow = 3;
                                            menuActionName[0] = "Cancel";
                                            menuActionID[0] = 1107;
                                            menuActionCmd1[0] = itemDef.id;
                                            menuActionCmd2[0] = containerSlot;
                                            menuActionCmd3[0] = child.id;

                                            if (child.displayExamine) {
                                                menuActionName[1] = "Examine @lre@" + itemDef.name;
                                                menuActionID[1] = 1125;
                                                menuActionCmd1[1] = itemDef.id;
                                                menuActionCmd2[1] = containerSlot;
                                                menuActionCmd3[1] = child.id;
                                            }

                                            menuActionName[2] = "Release @lre@" + itemDef.name;
                                            menuActionID[2] = 633;
                                            menuActionCmd1[2] = itemDef.id;
                                            menuActionCmd2[2] = containerSlot;
                                            menuActionCmd3[2] = child.id;
                                        }

                                    }
                                    itemHover = child.inv[containerSlot] - 1;
                                    if (itemHover != lastItemHover) {
                                        hintName = itemDef.name;
                                        hintId = itemDef.id;
                                        hintTier = itemDef.hintTier;
                                        drawHintMenu();
                                        lastItemHover = child.inv[containerSlot] - 1;
                                    }
                                }
                            }
                            containerSlot++;
                        }
                    }
                }
            }
        }
    }

    private boolean cosmeticTab;

    private void buildPublicChat(int yOffset) {
        int messages = 0;

        for (int index = 0; index < 500; index++) {
            if (chatMessages[index] == null) {
                continue;
            }

            if (chatTypeView != 1) {
                continue;
            }

            int type = chatTypes[index];
            String name = chatNames[index];
            int k1 = 70 - messages * 14 + 42 + anInt1089 + 4 + 5;

            if (k1 < -23) {
                break;
            }

            if (name != null) {
                if (name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    name = name.substring(prefixSubstring);
                }
                if (name.indexOf("@") == 0) {
                    int prefixSubstring = getPrefixSubstringLength(name);
                    name = name.substring(prefixSubstring);
                }
            }

            if ((type == 1 || type == 2) && (type == 1 || publicChatMode == 0 || publicChatMode == 1 && isFriendOrSelf(name))) {
                if (yOffset > k1 - 14 && yOffset <= k1 && !name.equals(myPlayer.name)) {
                    if (!isFriendOrSelf(name)) {
                        menuActionName[menuActionRow] = "Add ignore @whi@" + name;
                        menuActionID[menuActionRow] = 42;
                        menuActionRow++;
                        menuActionName[menuActionRow] = "Add friend @whi@" + name;
                        menuActionID[menuActionRow] = 337;
                        menuActionRow++;
                    } else if (isFriendOrSelf(name)) {
                        menuActionName[menuActionRow] = "Message @whi@" + name;
                        menuActionID[menuActionRow] = 2639;
                        menuActionRow++;
                    }
                }

                messages++;
            }
        }
    }

    private void buildSplitPrivateChatMenu() {
        if (splitPrivateChat == 0) {
            return;
        }

        int yOffsetPos = 0;

        if (systemUpdateTimer != 0) {
            yOffsetPos += 1;
        }
        if (broadcastMinutes != 0) {
            yOffsetPos += 1;
        }

        for (int index = 0; index < 500; index++) {
            if (chatMessages[index] != null) {
                int type = chatTypes[index];
                String name = chatNames[index];
                boolean fixed = GameFrame.getScreenMode() == ScreenMode.FIXED;

                if (name != null && name.indexOf("@") == 0) {
                    name = NAME_PATTERN.matcher(name).replaceAll("");
                }

                if ((type == 3 || type == 7) && (type == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(name))) {
                    int l = chatArea.getyPos() - 9 - yOffsetPos * 13;

                    if (super.mouseX > (fixed ? 4 : 0) && super.mouseY - (fixed ? 4 : 0) > l - 10 && super.mouseY - (fixed ? 4 : 0) <= l + 3) {
                        int i1 = normalText.getTextWidth("From:  " + name + chatMessages[index]) + 25;

                        if (i1 > 450) {
                            i1 = 450;
                        }

                        if (super.mouseX < (fixed ? 4 : 0) + i1) {
                            if (!isFriendOrSelf(name)) {
                                menuActionName[menuActionRow] = "Add ignore @whi@" + name;
                                menuActionID[menuActionRow] = 2042;
                                menuActionRow++;
                                menuActionName[menuActionRow] = "Add friend @whi@" + name;
                                menuActionID[menuActionRow] = 2337;
                                menuActionRow++;
                            } else if (isFriendOrSelf(name)) {
                                menuActionName[menuActionRow] = "Message @whi@" + name;
                                menuActionID[menuActionRow] = 2639;
                                menuActionRow++;
                            }
                        }
                    }

                    if (++yOffsetPos >= 5) {
                        return;
                    }
                }

                if ((type == 5 || type == 6) && privateChatMode < 2 && ++yOffsetPos >= 5) {
                    return;
                }
            }
        }
    }

    private void calcCameraPos() {
        int xPos = spinPacketX * 128 + 64;
        int yPos = spinPacketY * 128 + 64;
        int zPos = method42(plane, yPos, xPos) - spinPacketHeight;

        if (xCameraPos < xPos) {
            xCameraPos += spinPacketConstantSpeed + (xPos - xCameraPos) * spinPacketVariableSpeed / 1000;

            if (xCameraPos > xPos) {
                xCameraPos = xPos;
            }
        }

        if (xCameraPos > xPos) {
            xCameraPos -= spinPacketConstantSpeed + (xCameraPos - xPos) * spinPacketVariableSpeed / 1000;

            if (xCameraPos < xPos) {
                xCameraPos = xPos;
            }
        }

        if (zCameraPos < zPos) {
            zCameraPos += spinPacketConstantSpeed + (zPos - zCameraPos) * spinPacketVariableSpeed / 1000;

            if (zCameraPos > zPos) {
                zCameraPos = zPos;
            }
        }

        if (zCameraPos > zPos) {
            zCameraPos -= spinPacketConstantSpeed + (zCameraPos - zPos) * spinPacketVariableSpeed / 1000;

            if (zCameraPos < zPos) {
                zCameraPos = zPos;
            }
        }

        if (yCameraPos < yPos) {
            yCameraPos += spinPacketConstantSpeed + (yPos - yCameraPos) * spinPacketVariableSpeed / 1000;

            if (yCameraPos > yPos) {
                yCameraPos = yPos;
            }
        }

        if (yCameraPos > yPos) {
            yCameraPos -= spinPacketConstantSpeed + (yCameraPos - yPos) * spinPacketVariableSpeed / 1000;

            if (yCameraPos < yPos) {
                yCameraPos = yPos;
            }
        }

        xPos = moveCameraX * 128 + 64;
        yPos = moveCameraY * 128 + 64;
        zPos = method42(plane, yPos, xPos) - moveCameraZ;
        int l = xPos - xCameraPos;
        int i1 = zPos - zCameraPos;
        int j1 = yPos - yCameraPos;
        int k1 = (int) Math.sqrt(l * l + j1 * j1);
        int l1 = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
        int i2 = (int) (Math.atan2(l, j1) * -325.94900000000001D) & 0x7ff;

        if (l1 < 128) {
            l1 = 128;
        }

        if (l1 > 383) {
            l1 = 383;
        }

        if (yCameraCurve < l1) {
            yCameraCurve += moveCameraSpeed + (l1 - yCameraCurve) * moveCameraAngle / 1000;

            if (yCameraCurve > l1) {
                yCameraCurve = l1;
            }
        }

        if (yCameraCurve > l1) {
            yCameraCurve -= moveCameraSpeed + (yCameraCurve - l1) * moveCameraAngle / 1000;

            if (yCameraCurve < l1) {
                yCameraCurve = l1;
            }
        }

        int j2 = i2 - xCameraCurve;

        if (j2 > 1024) {
            j2 -= 2048;
        }

        if (j2 < -1024) {
            j2 += 2048;
        }

        if (j2 > 0) {
            xCameraCurve += moveCameraSpeed + j2 * moveCameraAngle / 1000;
            xCameraCurve &= 0x7ff;
        }

        if (j2 < 0) {
            xCameraCurve -= moveCameraSpeed + -j2 * moveCameraAngle / 1000;
            xCameraCurve &= 0x7ff;
        }

        int k2 = i2 - xCameraCurve;

        if (k2 > 1024) {
            k2 -= 2048;
        }

        if (k2 < -1024) {
            k2 += 2048;
        }

        if (k2 < 0 && j2 > 0 || k2 > 0 && j2 < 0) {
            xCameraCurve = i2;
        }
    }

    private void calcEntityScreenPos(int i, int j, int l) {
        //Scene.viewDistance = Rasterizer3D.textureInt1;
        if (i < 128 || l < 128 || i > 13056 || l > 13056) {
            spriteDrawX = -1;
            spriteDrawY = -1;
            return;
        }

        int i1 = method42(plane, l, i) - j;
        i -= xCameraPos;
        i1 -= zCameraPos;
        l -= yCameraPos;
        int j1 = Rasterizer3D.SINE[yCameraCurve];
        int k1 = Rasterizer3D.COSINE[yCameraCurve];
        int l1 = Rasterizer3D.SINE[xCameraCurve];
        int i2 = Rasterizer3D.COSINE[xCameraCurve];
        int j2 = l * l1 + i * i2 >> 16;
        l = l * i2 - i * l1 >> 16;
        i = j2;
        j2 = i1 * k1 - l * j1 >> 16;
        l = i1 * j1 + l * k1 >> 16;
        i1 = j2;

        if (l >= 50) {
            spriteDrawX = Rasterizer3D.textureInt1 + (i << Scene.viewDistance) / l;
            spriteDrawY = Rasterizer3D.textureInt2 + (i1 << Scene.viewDistance) / l;
        } else {
            spriteDrawX = -1;
            spriteDrawY = -1;
        }
        //Scene.viewDistance = 512;
    }

    private void chatJoin(long l) {
        try {
            if (l == 0L) {
                return;
            }

            getOut().putOpcode(60);
            getOut().putLong(l);
            return;
        } catch (RuntimeException runtimeexception) {
            Signlink.reportError("47229, " + 3 + ", " + l + ", " + runtimeexception.toString());
        }

        throw new RuntimeException();
    }

    public int ticksSinceLastResize = 0;

    public boolean isInResizeable() {
        return GameFrame.getScreenMode() == ScreenMode.RESIZABLE_CLASSIC || GameFrame.getScreenMode() == ScreenMode.RESIZABLE_MODERN;//Configuration.clientSize == 1;
    }

    private void checkSize() {
        if (isInResizeable()) {
            if (Configuration.clientWidth != (isWebclient() ? getGameComponent().getWidth() : gameFrame.getFrameWidth())) {
                Configuration.clientWidth = (isWebclient() ? getGameComponent().getWidth() : gameFrame.getFrameWidth());
                gameAreaWidth = Configuration.clientWidth;
                ticksSinceLastResize = 0;
            }
            if (Configuration.clientHeight != (isWebclient() ? getGameComponent().getHeight() : gameFrame.getFrameHeight())) {
                Configuration.clientHeight = (isWebclient() ? getGameComponent().getHeight() : gameFrame.getFrameHeight());
                gameAreaHeight = Configuration.clientHeight;
                ticksSinceLastResize = 0;
            }
        }

        // Check on Resize for mac computers
        if (!Configuration.IS_RUNNING_WINDOWS) {
            if (ticksSinceLastResize == 50) {
                if (isInResizeable()) {
                    rebuildFrame(1, Configuration.clientWidth, Configuration.clientHeight);
                }
            }
        } else { // Check on Windows Computers
            if (ticksSinceLastResize == 10) {
                updateGameArea();
                //updateGame();
            }
        }

        ticksSinceLastResize++;
    }


    public void rebuildFrame(int size, int width, int height) {
        try {
            gameAreaWidth = (size == 0) ? 512 : width;
            gameAreaHeight = (size == 0) ? 334 : height;
            Configuration.clientWidth = width;
            Configuration.clientHeight = height;
            instance.rebuildFrame(size == 2, width, height, size == 1, size >= 1);
            updateGameArea();//updateGame();
            super.mouseX = super.mouseY = -1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void cleanUpForQuit() {
        Signlink.reporterror = false;
        mapIcon = null;
        try {
            if (getConnection() != null) {
                getConnection().close();
            }
            spritesMap.close();
            spritesMap = null;
        } catch (Exception _ex) {
        }
        setConnection(null);
        stopMidi();
        playLoginScreenMusic();

        if (onDemandFetcher != null) {
            onDemandFetcher.dispose();
        }

        onDemandFetcher = null;
        aStream_834 = null;
        setOut(null);
        setLoginBuffer(null);
        setInputBuffer(null);
        mapCoordinates = null;
        terrainData = null;
        objectData = null;
        floorMap = null;
        objectMap = null;
        tileHeights = null;
        tileFlags = null;
        scene = null;
        collisionData = null;
        anIntArrayArray901 = null;
        distances = null;
        waypointX = null;
        waypointY = null;
        aByteArray912 = null;
        tabAreaIP = null;
        leftFrame = null;
        topFrame = null;
        rightFrame = null;
        mapAreaIP = null;
        gameScreenIP = null;
        chatAreaIP = null;
        aProducingGraphicsBuffer_1125 = null;
        mapBack = null;
        compass = null;
        headIcons = null;
        skullIcons = null;
        headIconsHint = null;
        crosses = null;
        mapDotItem = null;
        mapDotNPC = null;
        mapDotPlayer = null;
        mapDotFriend = null;
        mapDotTeam = null;
        mapDotClan = null;
        newMapIconshit = null;
        alertBack = null;
        alertBorder = null;
        alertBorderH = null;
        mapScenes = null;
        mapFunctions = null;
        anIntArrayArray929 = null;
        playerArray = null;
        playerIndices = null;
        playersToUpdate = null;
        setaStreamArray895s(null);
        anIntArray840 = null;
        npcArray = null;
        npcIndices = null;
        groundArray = null;
        setaClass19_1179(null);
        setaClass19_1013(null);
        setaClass19_1056(null);
        menuActionCmd2 = null;
        menuActionCmd3 = null;
        menuActionCmd4 = null;
        menuActionID = null;
        menuActionCmd1 = null;
        menuActionName = null;
        variousSettings = null;
        anIntArray1072 = null;
        anIntArray1073 = null;
        aClass30_Sub2_Sub1_Sub1Array1140 = null;
        miniMapRegions = null;
        friendsList = null;
        friendsListAsLongs = null;
        friendsNodeIDs = null;
        aProducingGraphicsBuffer_1107 = null;
        titleScreenIP = null;
        multiOverlay = null;
        XPOverlay = null;
        nullLoader();
        ObjectDef.nullify();
        EntityDef.nullify();
        ItemDef.nullify();
        AnimationSkeleton.nullify();
        IdentityKit.cache = null;
        RSInterface.interfaceCache = null;
        AnimationDefinition.cache = null;
        AnimatedGraphic.cache = null;
        AnimatedGraphic.modelCache = null;
        Varp.setCache(null);
        super.fullGameScreen = null;
        Player.mruNodes = null;
        Rasterizer3D.nullify();
        Scene.nullify();
        Model.nullify();
        Texture.reset();
        System.gc();
    }

    public void closeGameInterfaces() {
        getOut().putOpcode(130);

        if (invOverlayInterfaceID != -1) {
            invOverlayInterfaceID = -1;
            aBoolean1149 = false;
            tabAreaAltered = true;
        }

        if (backDialogID != -1) {
            backDialogID = -1;
            updateChatArea = true;
            aBoolean1149 = false;
        }

        openInterfaceID = -1;
        setFullscreenInterfaceID(-1);
    }

    private void compareCrcValues() {
        int secondsToWait = 5;
        expectedCRCs[8] = 0;
        int checksumCount = 0;

        while (expectedCRCs[8] == 0) {
            String error = "Unknown problem";
            //// drawSmoothLoading(20, "Connecting to web server");

            try {
                DataInputStream in = openJagGrabInputStream("crc" + (int) (Math.random() * 99999999D) + "-" + 317);
                Stream buffer = new Stream(new byte[40]);
                in.readFully(buffer.buffer, 0, 40);
                in.close();

                for (int index = 0; index < 9; index++) {
                    expectedCRCs[index] = buffer.getIntLittleEndian();
                }

                int checksumValue = buffer.getIntLittleEndian();
                int expectedValue = 1234;

                for (int index = 0; index < 9; index++) {
                    expectedValue = (expectedValue << 1) + expectedCRCs[index];
                }

                if (checksumValue != expectedValue) {
                    error = "checksum problem";
                    expectedCRCs[8] = 0;
                }
            } catch (EOFException _ex) {
                error = "EOF problem";
                expectedCRCs[8] = 0;
            } catch (IOException _ex) {
                error = "socket problem";
                expectedCRCs[8] = 0;
            } catch (Exception _ex) {
                error = "logic problem";
                expectedCRCs[8] = 0;

                if (!Signlink.reporterror) {
                    return;
                }
            }

            if (expectedCRCs[8] == 0) {
                checksumCount++;

                for (int seconds = secondsToWait; seconds > 0; seconds--) {
                    if (checksumCount >= 10) {
                        // drawSmoothLoading(10, "Game updated - please reload page");
                        seconds = 10;
                    } else {
                        // drawSmoothLoading(10, error + " - Will retry in " + seconds + " secs.");
                    }

                    try {
                        Thread.sleep(1000L);
                    } catch (Exception _ex) {
                    }
                }

                secondsToWait *= 2;

                if (secondsToWait > 60) {
                    secondsToWait = 60;
                }

                httpFallback = !httpFallback;
            }
        }
    }

    /*
     * scene_draw_x = spriteDrawX scene_draw_y = spriteDrawY SRC:
     * https://www.rune-server.ee/runescape-development/rs2-client/help/656573-hp-
     * overlay.html
     */

    /*
     * private void get_raster_position(int raster_x, int height, int raster_y)
     * {//calcEntityScreenPos if (raster_x < 128 || raster_y < 128 || raster_x >
     * 13056 || raster_y > 13056) { scene_draw_x = -1; scene_draw_y = -1; return; }
     * int tile_bounds = method42(plane, raster_y, raster_x) - height; raster_x -=
     * absoluteX; tile_bounds -= anchor; raster_y -= absoluteY; int sine_y =
     * Model.SINE[yCameraCurve]; int cosine_y = Model.COSINE[yCameraCurve]; int
     * sine_x = Model.SINE[xCameraCurve]; int cosine_x = Model.COSINE[xCameraCurve];
     * int pos = raster_y * sine_x + raster_x * cosine_x >> 16; raster_y = raster_y
     * * cosine_x - raster_x * sine_x >> 16; raster_x = pos; pos = tile_bounds *
     * cosine_y - raster_y * sine_y >> 16; raster_y = tile_bounds * sine_y +
     * raster_y * cosine_y >> 16; tile_bounds = pos; if (raster_y >= 50) {
     * scene_draw_x = Rasterizer3D.textureInt1 + (raster_x << Scene.viewDistance)
     * / raster_y; scene_draw_y = Rasterizer3D.textureInt2 + (tile_bounds <<
     * Scene.viewDistance) / raster_y; } else { scene_draw_x = -1; scene_draw_y
     * = -1; } }
     */

    public java.net.Socket createFileServerSocket(int port) throws IOException {
        return new java.net.Socket(InetAddress.getByName(Configuration.JAGCACHED_HOST), port);
    }

    public java.net.Socket createGameServerSocket(int port) throws IOException {
        return new java.net.Socket(InetAddress.getByName(Configuration.SERVER_HOST()), port);
    }

    public void hitmarkDrawOld(int spriteDrawX, int spriteDrawY, int j1, Actor e) {
        if (spriteDrawX > -1) {
            if (j1 == 1) {
                spriteDrawY -= 20;
            }
            if (j1 == 2) {
                spriteDrawX -= 15;
                spriteDrawY -= 10;
            }
            if (j1 == 3) {
                spriteDrawX += 15;
                spriteDrawY -= 10;
            }
            int dmg = e.hitArray[j1];
            if (dmg > 0) {
                if (!Configuration.CONSTITUTION_ENABLED) {
                    dmg = dmg / 10;
                    if (dmg == 0) {
                        dmg = 1;
                    }
                }
                spritesMap.get(784).drawSprite(spriteDrawX - 11, spriteDrawY - 12);
            } else {
                spritesMap.get(785).drawSprite(spriteDrawX - 12, spriteDrawY - 13);
            }
            smallText.drawText(0, String.valueOf(dmg), spriteDrawY + 4, spriteDrawX);
            smallText.drawText(0xffffff, String.valueOf(dmg), spriteDrawY + 3, spriteDrawX - 1);
        }
    }

    public static int getIcon(int icon) {
        switch(icon) {
            case 0:
                return 786;
            case 1:
                return 787;
            case 2:
                return 788;
            case 3:
                return 789;
            case 4:
                return 790;
            case 5:
                return 791;
            default:
                return 0;
        }
    }

    public static int getBack(int id) {
        switch(id) {
            case 0:
                return 792;
            case 1:
                return 795;
            case 2:
                return 798;
            case 3:
                return 801;
            case 4:
                return 804;
            case 5:
                return 807;
            case 6:
                return 810;
            case 7:
                return 813;
            case 8:
                return 3560;
            case 9:
                return 3566;
            case 10:
                return 3563;
            case 11:
                return 3569;
            default:
                return 0;
        }
    }


    public void hitmarkDrawNew(Actor e, int hitLength, int type, int icon, int damage, int soak, int move, int opacity, int mask) {
        if (spriteDrawX > -1) {
            int drawPos = 0;
            if (mask == 0) {
                e.hitMarkPos[0] = spriteDrawY + move;
                drawPos = e.hitMarkPos[0];
            }
            if (mask != 0) {
                e.hitMarkPos[mask] = e.hitMarkPos[0] + (19 * mask);
                drawPos = e.hitMarkPos[mask];
            }
            if (soak > 0) {
                // if(!getOption("absorb_damage")) {
                // soak = 0;
                // }
                soak = 0;
            }
            if (damage > 0) {
                Sprite end1 = null, middle = null, end2 = null;
                int x = 0;
                if (!Configuration.CONSTITUTION_ENABLED) {
                    damage = (damage / 10);
                    if (damage == 0) {
                        damage = 1;
                    }
                }
                int offset = 0;
                switch (hitLength) {
                    /* Trial and error shit, terrible hardcoding :( */
                    case 1:
                        x = 8;
                        offset = 3;
                        break;
                    case 2:
                        x = 4;
                        offset = 4;
                        break;
                    case 3:
                        x = 1;
                        offset = 4;
                        break;
                    case 4:
                        offset = 7;
                        break;
                    case 5:
                        offset = 10;
                        break;
                }
                if (soak > 0) {
                    x -= 16;
                }
                end1 = spritesMap.get(getBack(type));
                middle = spritesMap.get(getBack(type) + 1);
                end2 = spritesMap.get(getBack(type) + 2);
                if (icon != 255 && icon != 8) {
                    spritesMap.get(getIcon(icon)).drawSprite3(spriteDrawX - 31 + x, drawPos - 9, opacity);
                }
                end1.drawSprite3(spriteDrawX - 12 + x, drawPos - 12, opacity);
                x += 4;
                for (int i = 0; i < hitLength * 2; i++) {
                    middle.drawSprite3(spriteDrawX - 12 + x, drawPos - 12, opacity);
                    x += 4;
                }
                end2.drawSprite3(spriteDrawX - 12 + x, drawPos - 12, opacity);
                newRegularFont.drawCenteredString(String.valueOf(damage),
                        spriteDrawX + offset,
                        drawPos + 3,
                        0xffffff,
                        0);
                if (soak > 0) {
                    drawSoak(soak, opacity, drawPos, x);
                }
            } else {
                int decrX = soak > 0 ? 26 : 12;
                spritesMap.get(543).drawSprite3(spriteDrawX - decrX, drawPos - 14, opacity);
                if (soak > 0) {
                    drawSoak(soak, opacity, drawPos, 4);
                }
            }
        }
    }

    public void drawSoak(int damage, int opacity, int drawPos, int x) {
        x -= 12;
        int soakLength = String.valueOf(damage).length();
        spritesMap.get(793 + 5).drawSprite3(spriteDrawX + x, drawPos - 12, opacity);
        x += 20;
        spritesMap.get(792 + 30).drawSprite3(spriteDrawX + x, drawPos - 12, opacity);
        x += 4;
        for (int i = 0; i < soakLength * 2; i++) {
            spritesMap.get(792 + 31).drawSprite3(spriteDrawX + x, drawPos - 12, opacity);
            x += 4;
        }
        spritesMap.get(792 + 32).drawSprite3(spriteDrawX + x, drawPos - 10, opacity);
        if (damage > 99) {
            x -= 5;
        }
        if (damage > 999) {
            x -= 5;
        }
    }

    private void delFriend(long nameHash) {
        try {
            if (nameHash == 0L) {
                return;
            }

            for (int i = 0; i < friendCount; i++) {
                if (friendsListAsLongs[i] != nameHash) {
                    continue;
                }

                friendCount--;

                for (int n = i; n < friendCount; n++) {
                    friendsList[n] = friendsList[n + 1];
                    friendsNodeIDs[n] = friendsNodeIDs[n + 1];
                    friendsListAsLongs[n] = friendsListAsLongs[n + 1];
                }

                getOut().putOpcode(215);
                getOut().putLong(nameHash);
                break;
            }
        } catch (RuntimeException runtimeexception) {
            Signlink.reportError("18622, " + false + ", " + nameHash + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    private void delIgnore(long nameHash) {
        try {
            if (nameHash == 0L) {
                return;
            }

            for (int i = 0; i < ignoreCount; i++) {
                if (ignoreListAsLongs[i] == nameHash) {
                    ignoreCount--;
                    System.arraycopy(ignoreListAsLongs, i + 1, ignoreListAsLongs, i, ignoreCount - i);
                    getOut().putOpcode(74);
                    getOut().putLong(nameHash);
                    return;
                }
            }

            return;
        } catch (RuntimeException runtimeexception) {
            Signlink.reportError("47229, " + 3 + ", " + nameHash + ", " + runtimeexception.toString());
        }

        throw new RuntimeException();
    }

    private void determineMenuSize() {
        int width = newBoldFont.getTextWidth("Choose Option");
        for (int index = 0; index < menuActionRow; index++) {
            int menuWidth = newBoldFont.getTextWidth(menuActionName[index]);
            if (menuPlayerName[index] != null) {
                menuWidth += newBoldFont.getTextWidth(menuPlayerName[index]);
            }
            if (menuActionTitle[index] != null) {
                menuWidth += newBoldFont.getTextWidth(menuActionTitle[index]);
            }
            if (menuWidth > width) {
                width = menuWidth;
            }
        }
        width += 8;
        int menHeight = 15 * menuActionRow + 21;
        if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
            if (super.saveClickX > 4 && super.saveClickY > 4 && super.saveClickX < 516 && super.saveClickY < 338) {
                int offsetX = super.saveClickX - 4 - width / 2;
                if (offsetX + width > 512) {
                    offsetX = 512 - width;
                }
                if (offsetX < 0) {
                    offsetX = 0;
                }
                int offsetY = super.saveClickY - 4;
                if (offsetY + menHeight > 334) {
                    offsetY = 334 - menHeight;
                }
                if (offsetY < 0) {
                    offsetY = 0;
                }
                menuOpen = true;
                menuScreenArea = 0;
                menuOffsetX = offsetX;
                menuOffsetY = offsetY;
                menuWidth = width;
                menuHeight = 15 * menuActionRow + 22;
            }
            if (super.saveClickX > 519 && super.saveClickY > 168 && super.saveClickX < 765 && super.saveClickY < 503) {
                int offsetX = super.saveClickX - 519 - width / 2;
                if (offsetX < 0) {
                    offsetX = 0;
                } else if (offsetX + width > 245) {
                    offsetX = 245 - width;
                }
                int offsetY = super.saveClickY - 168;
                if (offsetY < 0) {
                    offsetY = 0;
                } else if (offsetY + menHeight > 333) {
                    offsetY = 333 - menHeight;
                }
                menuOpen = true;
                menuScreenArea = 1;
                menuOffsetX = offsetX;
                menuOffsetY = offsetY;
                menuWidth = width;
                menuHeight = 15 * menuActionRow + 22;
            }
            if (super.saveClickX > 0 && super.saveClickY > 338 && super.saveClickX < 516 && super.saveClickY < 503) {
                int offsetX = super.saveClickX - width / 2;
                if (offsetX < 0) {
                    offsetX = 0;
                } else if (offsetX + width > 516) {
                    offsetX = 516 - width;
                }
                int offsetY = super.saveClickY - 338;
                if (offsetY < 0) {
                    offsetY = 0;
                } else if (offsetY + menHeight > 165) {
                    offsetY = 165 - menHeight;
                }
                menuOpen = true;
                menuScreenArea = 2;
                menuOffsetX = offsetX;
                menuOffsetY = offsetY;
                menuWidth = width;
                menuHeight = 15 * menuActionRow + 22;
            }
            // if(super.saveClickX > 0 && super.saveClickY > 338 &&
            // super.saveClickX < 516 && super.saveClickY < 503) {
            if (super.saveClickX > 519 && super.saveClickY > 0 && super.saveClickX < 765 && super.saveClickY < 168) {
                int offsetX = super.saveClickX - 519 - width / 2;
                if (offsetX < 0) {
                    offsetX = 0;
                } else if (offsetX + width > 245) {
                    offsetX = 245 - width;
                }
                int offsetY = super.saveClickY;
                if (offsetY < 0) {
                    offsetY = 0;
                } else if (offsetY + menHeight > 168) {
                    offsetY = 168 - menHeight;
                }
                menuOpen = true;
                menuScreenArea = 3;
                menuOffsetX = offsetX;
                menuOffsetY = offsetY;
                menuWidth = width;
                menuHeight = 15 * menuActionRow + 22;
            }
        } else {
            if (super.saveClickX > 0 && super.saveClickY > 0 && super.saveClickX < getScreenWidth() && super.saveClickY < getScreenHeight()) {
                int offsetX = super.saveClickX - width / 2;
                if (offsetX + width > getScreenWidth()) {
                    offsetX = getScreenWidth() - width;
                }
                if (offsetX < 0) {
                    offsetX = 0;
                }
                int offsetY = super.saveClickY;
                if (offsetY + menHeight > getScreenHeight()) {
                    offsetY = getScreenHeight() - menHeight;
                }
                if (offsetY < 0) {
                    offsetY = 0;
                }
                menuOpen = true;
                menuScreenArea = 0;
                menuOffsetX = offsetX;
                menuOffsetY = offsetY;
                menuWidth = width;
                menuHeight = 15 * menuActionRow + 22;
            }
        }
    }

    public String getMoneyInPouch() {
        if (RSInterface.interfaceCache[8135].message == null) {
            return "";
        }
        String Cash = RSInterface.interfaceCache[8135].message;

        try {
            long convertedMoney = Long.parseLong(Cash);
            Cash = formatAmount(convertedMoney);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return Cash;
    }

    public final String formatAmount(long amount) {
        String format = "Too high!";
        if (amount >= 0 && amount < 100000) {
            format = String.valueOf(amount);
        } else if (amount >= 100000 && amount < 1000000) {
            format = amount / 1000 + "K";
        } else if (amount >= 1000000 && amount < 1000000000L) {
            format = amount / 1000000 + "M";
        } else if (amount >= 1000000000L && amount < 1000000000000L) {
            format = amount / 1000000000 + "B";
        } else if (amount >= 10000000000000L && amount < 10000000000000000L) {
            format = amount / 1000000000000L + "T";
        } else if (amount >= 10000000000000000L && amount < 1000000000000000000L) {
            format = amount / 1000000000000000L + "QD";
        } else if (amount >= 1000000000000000000L && amount < Long.MAX_VALUE) {
            format = amount / 1000000000000000000L + "QT";
        }
        return format;
    }

    private void doAction(int actionId) {
        if (actionId < 0) {
            return;
        }

        if (inputDialogState != 0 && inputDialogState != 5) {
            inputDialogState = 0;
            updateChatArea = true;
        }

        bankItemDragSprite = null;

        int nodeId = menuActionCmd1[actionId];
        int slot = menuActionCmd2[actionId];
        int interfaceId = menuActionCmd3[actionId];
        int fourthMenuAction = menuActionCmd4[actionId];
        int action = menuActionID[actionId];
        int x = slot;
        int y = interfaceId;
        int id = nodeId > 0x7fff ? fourthMenuAction : nodeId >> 14 & 0x7fff;

/*        pushMessage("Action: " + action + " nodeId: " + nodeId + " slot: " + slot + " interfaceId: " + interfaceId + " fourthMenuAction: " + fourthMenuAction);*/

        if (interfaceId == 2458) {
            sendClientSettings();
        }


        if (action == 1075) {
            getOut().putOpcode(18);
            getOut().writeUnsignedWordBigEndian(nodeId);
        }

        if (action == 291) {
            getOut().putOpcode(140);
            getOut().writeUnsignedWordBigEndian(interfaceId);
            getOut().writeUnsignedWordA(nodeId);
            getOut().writeUnsignedWordA(slot);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 42200) {
            getOut().putOpcode(145);
            getOut().writeUnsignedWordA(42200);
            getOut().writeUnsignedWordA(slot);
            getOut().writeUnsignedWordA(nodeId);
        }
        if (action == 769) {
            RSInterface d = RSInterface.interfaceCache[interfaceId];
            RSInterface p = RSInterface.interfaceCache[nodeId];
            if (lastDropDownOpenedD != -1) {
                RSInterface lastD = RSInterface.interfaceCache[lastDropDownOpenedD];
                RSInterface lastP = RSInterface.interfaceCache[lastDropDownOpenedP];
                if (!lastD.dropdown.isOpen()) {
                    if (lastP.dropdownOpen != null) {
                        lastP.dropdownOpen.dropdown.setOpen(false);
                    }
                    lastP.dropdownOpen = lastD;
                } else {
                    lastP.dropdownOpen = null;
                }
                lastD.dropdown.setOpen(false);
            }
            if (!d.dropdown.isOpen()) {
                if (p.dropdownOpen != null) {
                    p.dropdownOpen.dropdown.setOpen(false);
                }
                p.dropdownOpen = d;
            } else {
                p.dropdownOpen = null;
            }
            d.dropdown.setOpen(!d.dropdown.isOpen());
            lastDropDownOpenedD = interfaceId;
            lastDropDownOpenedP = nodeId;
        } else if (action == 770) {
            RSInterface d = RSInterface.interfaceCache[interfaceId];
            RSInterface p = RSInterface.interfaceCache[nodeId];
            if (slot >= d.dropdown.getOptions().length)
                return;
            d.dropdown.setSelected(d.dropdown.getOptions()[slot]);
            d.dropdown.setOpen(false);
            d.dropdown.getDrop().selectOption(slot, d);
            p.dropdownOpen = null;
        }

        if (action == 300) {
            getOut().putOpcode(141);
            getOut().writeUnsignedWordA(slot);
            getOut().putShort(interfaceId);
            getOut().writeUnsignedWordA(nodeId);
            getOut().putInt(modifiableXValue);
        }

        if (action == 1045) {// Toggle quick prayers / curses
            if (openInterfaceID != -1) {
                pushMessage("Please close the open interface first.", 0, "");
            } else {
                if ((currentStats[5]) > 0) {
                    handleQuickAidsActive();
                } else {
                    pushMessage("You need to recharge your Prayer points at an altar.", 0, "");
                }
                return;
            }
            return;
        } else if (action == 1046) {// Select quick prayers / curses
            toggleQuickAidsSelection();
            return;
        }

        if (action >= 2000) {
            action -= 2000;
        }

        if (interfaceId == 24630 || interfaceId == 24632) {
            if (inputDialogState == 3) {
                getGrandExchange().searching = false;
                getGrandExchange().totalItemResults = 0;
                amountOrNameInput = "";
            }
        }

        if (action == 22700 || action == 1251) {
            getGrandExchange().searching = false;
            getOut().putOpcode(204);
            getOut().putShort(getGrandExchange().itemSelected);
            return;
        }

        if (action >= 990 && action <= 992) {
            getOut().putOpcode(8);
            getOut().putInt(action);
            inputString = "";
            privateChatMode = action - 990;
            return;
        }

        if (action == 712) {
            Configuration.MONEY_POUCH_ENABLED = !Configuration.MONEY_POUCH_ENABLED;
        } else if (action == 714) {
            sendPacket185(27656);
        } else if (action == 713) {
            if (openInterfaceID > 0 && openInterfaceID != 3323 && openInterfaceID != 6575) {
                pushMessage("Please close the interface you have open before opening another one.", 0, "");
                return;
            }
            inputTitle = "Enter amount of coins to withdraw:";
            amountOrNameInput = "";
            interfaceButtonAction = 557;
            // showInput = false;
            inputDialogState = 1;
            updateChatArea = true;
            withdrawingMoneyFromPouch = true;
            return;
        } else if (action == 715) {
            String cash = getMoneyInPouch();
            pushMessage("Your money pouch currently contains " + cash + " (" + Long.parseLong(RSInterface.interfaceCache[8135].message) + ") coins.", 0, "");
        } else {
            withdrawingMoneyFromPouch = false;
        }

        switch (action) {
            case 1042:
            case 1716:
            case 1037:
            case 1038:
            case 1039:
            case 1040:
            case 1041:
            case 1095:
            case 1717:
                getOut().putOpcode(185);
                getOut().putInt(action);
                break;
            case 1050:
                getOut().putOpcode(185);
                getOut().putInt(152);
                break;
            case 1013:
                PlayerUtilities.totalXP = 0;
                getOut().putOpcode(185);
                getOut().putInt(1013);
                break;
            case 1036:
                getOut().putOpcode(185);
                getOut().putInt(1036);
                break;
        }

        if (action == 476) {
            alertHandler.close();
            //alertManager.close();
        }
        if (action == 471) {
            broadcastMessage = null;
            broadcastMinutes = 0;
        }

        if (action == 1014) {
            setNorth();
        }

        if (action == 1007) {
            PlayerUtilities.canGainXP = !PlayerUtilities.canGainXP;
        }

        if (action == 1006 && !PlayerUtilities.showBonus) {
            if (!PlayerUtilities.gains.isEmpty()) {
                PlayerUtilities.gains.removeAll(PlayerUtilities.gains);
            }
            PlayerUtilities.showXP = !PlayerUtilities.showXP;
        }

        if (action == 1030 && !PlayerUtilities.showXP) {
            PlayerUtilities.showBonus = !PlayerUtilities.showBonus;
        }

        if (action == 104) {
            spellID = interfaceId;
            if (!autoCast || (autocastId != spellID)) {
                autoCast = true;
                autocastId = spellID;
                sendPacket185(autocastId, -1, 135);
                // pushMessage("Autocast spell selected.", 0, "");
            } else if (autocastId == spellID) {
                setAutoCastOff();
            }
        }

        if (action == 582) {
            Npc npc = npcArray[nodeId];

            if (npc != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(57);
                getOut().writeUnsignedWordA(selectedItemId);
                getOut().writeUnsignedWordA(nodeId);
                getOut().writeUnsignedWordBigEndian(anInt1283);
                getOut().writeUnsignedWordA(anInt1284);
            }
        }

        if (action == 234) {
            boolean flag1 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, interfaceId, myPlayer.smallX[0], false, x);

            if (!flag1) {
                flag1 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, interfaceId, myPlayer.smallX[0], false, x);
            }

            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            getOut().putOpcode(236);
            getOut().writeUnsignedWordBigEndian(interfaceId + regionBaseY);
            getOut().putShort(nodeId);
            getOut().writeUnsignedWordBigEndian(slot + regionBaseX);
        }

        if (action == 62 && method66(nodeId, y, x, id)) {
            getOut().putOpcode(192);
            getOut().putShort(anInt1284);
            getOut().putInt(id);
            getOut().writeSignedBigEndian(y + regionBaseY);
            getOut().writeUnsignedWordBigEndian(anInt1283);
            getOut().writeSignedBigEndian(x + regionBaseX);
            getOut().putShort(selectedItemId);
        }

        if (action == 511) {
            boolean flag2 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, y, myPlayer.smallX[0], false, x);

            if (!flag2) {
                flag2 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, y, myPlayer.smallX[0], false, x);
            }

            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            getOut().putOpcode(25);
            getOut().writeUnsignedWordBigEndian(anInt1284);
            getOut().writeUnsignedWordA(selectedItemId);
            getOut().putShort(nodeId);
            getOut().writeUnsignedWordA(y + regionBaseY);
            getOut().writeSignedBigEndian(anInt1283);
            getOut().putShort(x + regionBaseX);
        }

        if (action == 74) {
            getOut().putOpcode(122);
            getOut().putShort(interfaceId);
            getOut().putShort(slot);
            getOut().putShort(nodeId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = y;
            atInventoryIndex = x;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[y].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[y].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (action == 222) {
            getOut().putOpcode(222);
            getOut().putShort(interfaceId);
            getOut().putByte(currentActionMenu);
        }
        if (action == 315) {
            switch (interfaceId) {
                case 24654:
                    amountOrNameInput = "";
                    getGrandExchange().totalItemResults = 0;
                    getGrandExchange().searching = !getGrandExchange().searching;// inputDialogState == 3 ? false : true;
                    inputDialogState = inputDialogState == 3 ? 0 : 3;
                    break;
            }

            RSInterface class9 = RSInterface.interfaceCache[interfaceId];

            if (class9.type == 77) {
                boolean check = !class9.inputToggled;
                disableInputFields();
                class9.inputToggled = check;
                if (class9.inputToggled) {
                    this.inputFieldSelected = class9;
                }
                if (class9.inputToggled && class9.inputText.equals(class9.defaultText)) {
                    class9.inputText = "";
                } else if (!class9.inputToggled && class9.inputText.equalsIgnoreCase("")) {
                    class9.inputText = class9.defaultText;
                }
            }

            if (class9.id >= 44008 && class9.id <= 44027) {
                RSInterface.interfaceCache[44000].children[6] = ((class9.id - 44008) * 200) + 44100;
            }

            boolean flag8 = true;
            if ((class9.contentType == 1321) || (class9.contentType == 1322) || (class9.contentType == 1323)) {
                int index = class9.id - 79924;
                if (index >= 50) {
                    index -= 50;
                }
                if (index >= 25) {
                    index -= 25;
                }
                Skills.selectedSkillId = Skills.SKILL_ID_NAME(Skills.SKILL_NAMES[index]);
            }
            if (class9.contentType > 0) {
                flag8 = promptUserForInput(class9);
            }

            if (flag8) {
                if (interfaceId >= 26254 && interfaceId <= 26257) {
                    SettingsInterface.setTab(interfaceId);
                }
                if (interfaceId >= 27014 && interfaceId <= 27022) {
                    Bank.inverse_nav_comp(interfaceId);
                }
                if (interfaceId >= 569 && interfaceId <= 570) {
                    Bank.inverse_note_comp(interfaceId);
                }
                if (interfaceId >= 78395) {
                    Bank.placeHolderToggle();
                }
                if (interfaceId >= 22008 && interfaceId <= 22009) {
                    Bank.inverse_swap_comp(interfaceId);
                }
                if (interfaceId >= 571 && interfaceId <= 575) {
                    Bank.inverse_quantity_comp(interfaceId);
                }
                switch (interfaceId) {
                    case 21341:
                        sendFrame248(21172, 3213);
                        resetInterfaceAnimation(21172);
                        updateChatArea = true;
                        break;
                    case 251:
                        RSInterface.setTab(RSInterface.FRIEND_TAB, 5715);
                    break;
                    case 252:
                        RSInterface.setTab(RSInterface.FRIEND_TAB, 5065);
                    break;
                    case 26465:
                        Keybinding.updateInterface();
                        openInterfaceID = 86700;
                    break;
                    case 86704:
                        Keybinding.restoreDefault();
                        Keybinding.updateInterface();
                    break;
                    case 86002:
                        RSInterface.setTab(RSInterface.SKILLS_TAB, 86100);
                    break;
                    case 86003:
                        RSInterface.setTab(RSInterface.SKILLS_TAB, 86300);
                        break;
                    case 17231:// Quick prayer confirm
                        saveQuickSelection();
                        break;
                    case 26564:
                        changeSplitChatSelectionBox(this);
                        break;
                    case 26566:
                        changeClanChatSelectionBox(this);
                        break;
                    case 26662:
                        changeHealthSelectionBox(this);
                    break;
                    default:
                        Interfaces.buttonClicked(interfaceId);
                        if (interfaceId >= 17202 && interfaceId <= 17227 || interfaceId == 17279 || interfaceId == 17280) {
                            try {
                                togglePrayerState(interfaceId);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            getOut().putOpcode(185);
                            getOut().putInt(interfaceId);
                        }
                        break;
                }
            }
        }

        if (action == 561) {
            Player player = playerArray[nodeId];

            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                setAnInt1188(getAnInt1188() + id);

                if (getAnInt1188() >= 90) {
                    getOut().putOpcode(136);
                    setAnInt1188(0);
                }

                getOut().putOpcode(128);
                getOut().putShort(nodeId);
            }
        }

        if (action == 20) {
            Npc npc = npcArray[nodeId];
            if (npc != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(155);
                getOut().writeUnsignedWordBigEndian(nodeId);
            }
        }

        if (action == 779) {
            Player player = playerArray[nodeId];

            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(153);
                getOut().writeUnsignedWordBigEndian(nodeId);
            }
        }

        if (action == 516) {
            if (!menuOpen) {
                scene.click(super.saveClickY - 4, super.saveClickX - 4);
            } else {
                scene.click(interfaceId - 4, slot - 4);
            /*if (!inMoveButton) {
                if (!menuOpen) {
                    scene.click(super.saveClickY - 4, super.saveClickX - 4);*/
            }

        }

        if (action == 1062) {
            setAnInt924(getAnInt924() + regionBaseX);

            if (getAnInt924() >= 113) {
                getOut().putOpcode(183);
                getOut().putDWordBigEndian(0xe63271);
                setAnInt924(0);
            }

            method66(nodeId, y, x, id);
            getOut().putOpcode(228);
            getOut().writeUnsignedWordA(id);
            getOut().writeUnsignedWordA(y + regionBaseY);
            getOut().putShort(x + regionBaseX);
        }
        if (action == 679 && !aBoolean1149) {
            getOut().putOpcode(40);
            getOut().putShort(interfaceId);
            aBoolean1149 = true;
        }

        if (action == 431) {
            getOut().putOpcode(129);
            getOut().writeUnsignedWordA(slot);
            getOut().putShort(interfaceId);
            getOut().writeUnsignedWordA(nodeId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 337 || action == 42 || action == 792 || action == 322) {
            String s = menuActionName[actionId];
            int k1 = s.indexOf("@whi@");

            if (k1 != -1) {
                long l3 = TextUtilities.longForName(NAME_PATTERN.matcher(s.substring(k1 + 5))
                        .replaceAll(""));

                if (action == 337) {
                    addFriend(l3);
                } else if (action == 42) {
                    addIgnore(l3);
                } else if (action == 792) {
                    delFriend(l3);
                } else if (action == 322) {
                    delIgnore(l3);
                }
            }
        }
        if (action == 53) {
            getOut().putOpcode(135);
            getOut().writeUnsignedWordBigEndian(slot);
            getOut().writeUnsignedWordA(interfaceId);
            getOut().writeUnsignedWordBigEndian(nodeId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 54) {
            int newId = RSInterface.interfaceCache[interfaceId].parentID == 5292 ? 11 : RSInterface.interfaceCache[interfaceId].parentID == 5063 ? 12 : interfaceId;
            getOut().putOpcode(135);
            getOut().writeUnsignedWordBigEndian(slot);
            getOut().writeUnsignedWordA(newId);
            getOut().writeUnsignedWordBigEndian(nodeId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 539) {
            getOut().putOpcode(16);
            getOut().writeUnsignedWordA(nodeId);
            getOut().writeSignedBigEndian(slot);
            getOut().writeSignedBigEndian(interfaceId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 484 || action == 6) {
            String s1 = menuActionName[actionId];
            int l1 = s1.indexOf("@whi@");

            if (l1 != -1) {
                s1 = s1.substring(l1 + 5).trim();
                String s7 = TextUtilities.fixName(TextUtilities.nameForLong(TextUtilities.longForName(s1)));
                boolean flag9 = false;

                for (int j3 = 0; j3 < playerCount; j3++) {
                    Player player = playerArray[playerIndices[j3]];

                    if (player == null || player.name == null || !player.name.equalsIgnoreCase(s7)) {
                        continue;
                    }

                    doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);

                    if (action == 484) {
                        getOut().putOpcode(139);
                        getOut().writeUnsignedWordBigEndian(playerIndices[j3]);
                    }

                    if (action == 6) {
                        setAnInt1188(getAnInt1188() + nodeId);

                        if (getAnInt1188() >= 90) {
                            getOut().putOpcode(136);
                            setAnInt1188(0);
                        }

                        getOut().putOpcode(128);
                        getOut().putShort(playerIndices[j3]);
                    }

                    flag9 = true;
                    break;
                }

                if (!flag9) {
                    pushMessage("Unable to find " + s7, 0, "");
                }
            }
        }

        if (action == 1673) {
            String s1 = menuActionName[actionId];
            int l1 = s1.indexOf("@whi@");
            if (l1 != -1) {
                s1 = s1.substring(l1 + 5).trim();
                String s7 = TextUtilities.fixName(TextUtilities.nameForLong(TextUtilities.longForName(s1)));
                boolean flag9 = false;
                for (int j3 = 0; j3 < playerCount; j3++) {
                    Player class30_sub2_sub4_sub1_sub2_7 = playerArray[playerIndices[j3]];
                    if (class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.name == null || !class30_sub2_sub4_sub1_sub2_7.name.equalsIgnoreCase(s7)) {
                        continue;
                    }
                    doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_7.smallX[0]);
                    getOut().putOpcode(193);
                    getOut().writeUnsignedWordBigEndian(playerIndices[j3]);
                    flag9 = true;
                    break;
                }

                if (!flag9) {
                    pushMessage("Unable to find " + s7 + ".", 0, "");
                }
            }
        }

        if (action == 870) {
            getOut().putOpcode(53);
            getOut().putShort(slot);
            getOut().writeUnsignedWordA(anInt1283);
            getOut().writeSignedBigEndian(nodeId);
            getOut().putShort(anInt1284);
            getOut().writeUnsignedWordBigEndian(selectedItemId);
            getOut().putShort(interfaceId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 1773) {
            String s1 = menuActionName[actionId];
            int l1 = s1.indexOf("@whi@");
            if (l1 != -1) {
                s1 = s1.substring(l1 + 5).trim();
                String s7 = TextUtilities.fixName(TextUtilities.nameForLong(TextUtilities.longForName(s1)));
                boolean flag9 = false;
                for (int j3 = 0; j3 < playerCount; j3++) {
                    Player class30_sub2_sub4_sub1_sub2_7 = playerArray[playerIndices[j3]];
                    if (class30_sub2_sub4_sub1_sub2_7 == null || class30_sub2_sub4_sub1_sub2_7.name == null || !class30_sub2_sub4_sub1_sub2_7.name.equalsIgnoreCase(s7)) {
                        continue;
                    }
                    doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_7.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_7.smallX[0]);
                    getOut().putOpcode(220);
                    getOut().writeUnsignedWordBigEndian(playerIndices[j3]);
                    flag9 = true;
                    break;
                }

                if (!flag9) {
                    pushMessage("Unable to find " + s7 + ".", 0, "");
                }
            }
        }

        if (action == 847) {
            getOut().putOpcode(87);
            getOut().writeUnsignedWordA(nodeId);
            getOut().putShort(interfaceId);
            getOut().writeUnsignedWordA(slot);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 626) {
            RSInterface class9_1 = RSInterface.interfaceCache[interfaceId];
            spellSelected = 1;
            spellUsableOn = Integer.parseInt(MagicInterfaceData.getSpellData(interfaceId, "spellUsableOn"));
            itemSelected = 0;
            spellID = interfaceId;
            String s4 = MagicInterfaceData.getSpellData(interfaceId, "selectedActionName");
            if (s4.indexOf(" ") != -1) {
                s4 = s4.substring(0, s4.indexOf(" "));
            }
            String s8 = MagicInterfaceData.getSpellData(interfaceId, "selectedActionName");
            if (s8.indexOf(" ") != -1) {
                s8 = s8.substring(s8.indexOf(" ") + 1);
            }
            spellTooltip = s4 + " " + MagicInterfaceData.getSpellData(interfaceId, "spellname") + " " + s8;
            if (spellUsableOn == 16) {
                tabID = 3;
                tabAreaAltered = true;
            }
            selectedSpellId = spellID;
            return;
        }

        if (action == 78) {
            getOut().putOpcode(117);
            getOut().writeSignedBigEndian(interfaceId);
            getOut().writeSignedBigEndian(nodeId);
            getOut().writeUnsignedWordBigEndian(slot);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 27) {
            Player class30_sub2_sub4_sub1_sub2_2 = playerArray[nodeId];

            if (class30_sub2_sub4_sub1_sub2_2 != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, class30_sub2_sub4_sub1_sub2_2.smallY[0], myPlayer.smallX[0], false, class30_sub2_sub4_sub1_sub2_2.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                setAnInt986(getAnInt986() + nodeId);

                if (getAnInt986() >= 54) {
                    getOut().putOpcode(189);
                    getOut().putByte(234);
                    setAnInt986(0);
                }

                getOut().putOpcode(73);
                getOut().writeUnsignedWordBigEndian(nodeId);
            }
        }

        if (action == 213) {
            boolean flag3 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, interfaceId, myPlayer.smallX[0], false, slot);

            if (!flag3) {
                flag3 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, interfaceId, myPlayer.smallX[0], false, slot);
            }

            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            getOut().putOpcode(79);
            getOut().writeUnsignedWordBigEndian(interfaceId + regionBaseY);
            getOut().putShort(nodeId);
            getOut().writeUnsignedWordA(slot + regionBaseX);
        }

        if (action == 633) {
            if (menuOpen) {
                action = 632;
            } else {
                determineMenuSize();
            }
        }
        if (action == 632) {
            if (openInterfaceID == 53700 || openInterfaceID == 54700) {
                if (interfaceId == 53781) {
                    interfaceId = 2901;
                    slot = 0;
                } else if (interfaceId == 53782) {
                    interfaceId = 2901;
                    slot = 1;
                } else if (interfaceId == 54781) {
                    interfaceId = 2902;
                    slot = 0;
                } else if (interfaceId == 54782) {
                    interfaceId = 2902;
                    slot = 1;
                }
            }
            getOut().putOpcode(145);
            getOut().writeUnsignedWordA(interfaceId);
            getOut().writeUnsignedWordA(slot);
            getOut().writeUnsignedWordA(nodeId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 1004) {
            if (tabInterfaceIDs[10] != -1) {
                tabID = 10;
                tabAreaAltered = true;
            }
        }

        if (action == 1003) {
            updateChatArea = true;
        }

        if (action == 1002) {
            updateChatArea = true;
        }

        if (action == 1001) {
            updateChatArea = true;
        }

        if (action == 1000) {
            chatArea.toggleButton(this, 4);
            cButtonCPos = 4;
            chatTypeView = 11;
            updateChatArea = true;
        }

        if (action == 999) {
            chatArea.toggleButton(this, 0);
            cButtonCPos = 0;
            chatTypeView = 0;
            updateChatArea = true;
        }

        if (action == 998) {
            chatArea.toggleButton(this, 1);
            cButtonCPos = 1;
            chatTypeView = 5;
            updateChatArea = true;
        }

        if (action == 997) {
            publicChatMode = 3;
            updateChatArea = true;
        }

        if (action == 996) {
            publicChatMode = 2;
            updateChatArea = true;
        }

        if (action == 995) {
            publicChatMode = 1;
            updateChatArea = true;
        }

        if (action == 994) {
            publicChatMode = 0;
            updateChatArea = true;
        }

        if (action == 993) {
            chatArea.toggleButton(this, 2);
            cButtonCPos = 2;
            chatTypeView = 1;
            updateChatArea = true;
        }

        if (action == 992) {
            privateChatMode = 2;
            updateChatArea = true;
            privateChatMode = 2;
            getOut().putOpcode(95);
            getOut().putByte(publicChatMode);
            getOut().putByte(privateChatMode);
            getOut().putByte(tradeMode);
        }

        if (action == 991) {
            privateChatMode = 1;
            updateChatArea = true;
            getOut().putOpcode(95);
            getOut().putByte(publicChatMode);
            getOut().putByte(privateChatMode);
            getOut().putByte(tradeMode);
        }

        if (action == 990) {
            privateChatMode = 0;
            updateChatArea = true;
            getOut().putOpcode(95);
            getOut().putByte(publicChatMode);
            getOut().putByte(privateChatMode);
            getOut().putByte(tradeMode);
        }

        if (action == 989) {
            chatArea.toggleButton(this, 3);
            cButtonCPos = 3;
            chatTypeView = 2;
            updateChatArea = true;
            getOut().putOpcode(95);
            getOut().putByte(publicChatMode);
            getOut().putByte(privateChatMode);
            getOut().putByte(tradeMode);
        }

        if (action == 987) {
            tradeMode = 2;
            updateChatArea = true;
        }

        if (action == 986) {
            tradeMode = 1;
            updateChatArea = true;
        }

        if (action == 985) {
            tradeMode = 0;
            updateChatArea = true;
        }

        if (action == 984) {
            chatArea.toggleButton(this, 5);
            cButtonCPos = 5;
            chatTypeView = 3;
            updateChatArea = true;
        }

        if (action == 983) {
            duelStatus = 2;
        }
        if (action == 982) {
            duelStatus = 1;
        }
        if (action == 981) {
            duelStatus = 0;
        }

        if (action == 980) {
            chatArea.toggleButton(this, 6);
            cButtonCPos = 6;
            chatTypeView = 14;
            updateChatArea = true;
        }

        if (action == 983) {
            updateChatArea = true;
        }

        if (action == 982) {
            updateChatArea = true;
        }

        if (action == 981) {
            updateChatArea = true;
        }

        if (action == 980) {
            chatArea.toggleButton(this, 6);
            cButtonCPos = 6;
            chatTypeView = 4;
            updateChatArea = true;
        }

        if (action == 493) {
            getOut().putOpcode(75);
            getOut().writeSignedBigEndian(interfaceId);
            getOut().writeUnsignedWordBigEndian(slot);
            getOut().writeUnsignedWordA(nodeId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 652) {
            boolean flag4 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, interfaceId, myPlayer.smallX[0], false, slot);

            if (!flag4) {
                flag4 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, interfaceId, myPlayer.smallX[0], false, slot);
            }

            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            getOut().putOpcode(156);
            getOut().writeUnsignedWordA(slot + regionBaseX);
            getOut().writeUnsignedWordBigEndian(interfaceId + regionBaseY);
            getOut().writeSignedBigEndian(nodeId);
        }

        if (action == 94) {
            boolean flag5 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, interfaceId, myPlayer.smallX[0], false, slot);

            if (!flag5) {
                flag5 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, interfaceId, myPlayer.smallX[0], false, slot);
            }

            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            getOut().putOpcode(181);
            getOut().writeUnsignedWordBigEndian(interfaceId + regionBaseY);
            getOut().putShort(nodeId);
            getOut().writeUnsignedWordBigEndian(slot + regionBaseX);
            getOut().writeUnsignedWordA(selectedSpellId);
        }

        if (action == 646) {
            Interfaces.buttonClicked(interfaceId);
            getOut().putOpcode(185);
            getOut().putInt(interfaceId);
            if (interfaceId <= RSInterface.interfaceCache.length) {
                RSInterface class9_2 = RSInterface.interfaceCache[interfaceId];

                if (class9_2.valueIndexArray != null && class9_2.valueIndexArray[0][0] == 5) {
                    int i2 = class9_2.valueIndexArray[0][1];

                    if (variousSettings[i2] != class9_2.requiredValues[0]) {
                        variousSettings[i2] = class9_2.requiredValues[0];
                        updateConfig(i2);
                    }
                    int weaponry = 980;
                    int armoury = 982;
                    int accessories = 984;
                    int miscellaneous = 986;
                    int default_enabled = 0;
                    int enabled_when_clicked = 1;
                    int disabled = 2;
                    switch (interfaceId) {

                        case 29844://comeback
                        case 28562:
                            variousSettings[weaponry] = default_enabled;
                            updateConfig(weaponry);
                            variousSettings[armoury] = disabled;
                            updateConfig(armoury);
                            variousSettings[accessories] = disabled;
                            updateConfig(accessories);
                            variousSettings[miscellaneous] = disabled;
                            updateConfig(miscellaneous);
                            break;
                        case 29846:
                        case 28563:
                            variousSettings[weaponry] = disabled;
                            updateConfig(weaponry);
                            variousSettings[armoury] = enabled_when_clicked;
                            updateConfig(armoury);
                            variousSettings[accessories] = disabled;
                            updateConfig(accessories);
                            variousSettings[miscellaneous] = disabled;
                            updateConfig(miscellaneous);
                            break;
                        case 29848:
                        case 28564:
                            variousSettings[weaponry] = disabled;
                            updateConfig(weaponry);
                            variousSettings[armoury] = disabled;
                            updateConfig(armoury);
                            variousSettings[accessories] = enabled_when_clicked;
                            updateConfig(accessories);
                            variousSettings[miscellaneous] = disabled;
                            updateConfig(miscellaneous);
                            break;
                        case 29850:
                        case 28565:
                            variousSettings[weaponry] = disabled;
                            updateConfig(weaponry);
                            variousSettings[armoury] = disabled;
                            updateConfig(armoury);
                            variousSettings[accessories] = disabled;
                            updateConfig(accessories);
                            variousSettings[miscellaneous] = 1;
                            updateConfig(miscellaneous);
                            break;
                        case 25841:// More options
                            i2 = openInterfaceID == 26000 ? 0 : 1;
                            sendFrame36(175, i2);


                            if (i2 == 1) {
                                openInterfaceID = 26000;
                            } else {
                                openInterfaceID = -1;
                            }

                            break;
                    }
                }
            }
        }

        if (action == 225) {
            Npc npc = npcArray[nodeId];

            if (npc != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                setAnInt1226(getAnInt1226() + nodeId);
                getOut().putOpcode(17);
                getOut().writeSignedBigEndian(nodeId);
            }
        }

        if (action == 965) {
            Npc npc = npcArray[nodeId];

            if (npc != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                setAnInt1134(getAnInt1134() + 1);
                getOut().putOpcode(21);
                getOut().putShort(nodeId);
            }
        }

        if (action == 413) {
            Npc npc = npcArray[nodeId];

            if (npc != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(131);
                getOut().writeSignedBigEndian(nodeId);
                getOut().writeUnsignedWordA(selectedSpellId);
            }
        }

        if (action == 200) {
            closeGameInterfaces();
        }

        if (action == 1025) {
            Npc npc = npcArray[nodeId];
            if (npc != null) {
                EntityDef entityDef = npc.definitionOverride;
                if (entityDef != null) {
                    getOut().putOpcode(6); // examine npc
                    getOut().putShort(entityDef.id);
                }
            }
        }

        if (action == 900) {
            method66(nodeId, y, x, id);
            getOut().putOpcode(252);
            getOut().writeSignedBigEndian(id);
            getOut().writeUnsignedWordBigEndian(y + regionBaseY);
            getOut().writeUnsignedWordA(x + regionBaseX);

        }

        if (action == 412) {
            Npc npc = npcArray[nodeId];

            if (npc != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(72);
                getOut().writeUnsignedWordA(nodeId);
            }
        }

        if (action == 365) {
            Player player = playerArray[nodeId];

            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(249);
                getOut().writeUnsignedWordA(nodeId);
                getOut().writeUnsignedWordBigEndian(selectedSpellId);
            }
        }

        if (action == 729) {
            Player player = playerArray[nodeId];

            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(39);
                getOut().writeUnsignedWordBigEndian(nodeId);
            }
        }

        if (action == 6300) {
            Player player = playerArray[nodeId]; // player index in the player array
            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(191);
                getOut().putShort(nodeId);
            }
        }

        if (action == 6305) {

            Player player = playerArray[nodeId]; // player index in the player array
            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(216);
                getOut().putShort(nodeId);
            }
        }

        if (action == 577) {
            Player player = playerArray[nodeId];

            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(139);
                getOut().writeUnsignedWordBigEndian(nodeId);
            }
        }

        if (action == 956 && method66(nodeId, y, x, id)) {
            getOut().putOpcode(35);
            getOut().writeUnsignedWordBigEndian(slot + regionBaseX);
            getOut().writeUnsignedWordA(selectedSpellId);
            getOut().writeUnsignedWordA(interfaceId + regionBaseY);
            getOut().writeUnsignedWordBigEndian(id);
        }

        if (action == 567) {
            boolean flag6 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, interfaceId, myPlayer.smallX[0], false, slot);

            if (!flag6) {
                flag6 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, interfaceId, myPlayer.smallX[0], false, slot);
            }

            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            getOut().putOpcode(23);
            getOut().writeUnsignedWordBigEndian(interfaceId + regionBaseY);
            getOut().writeUnsignedWordBigEndian(nodeId);
            getOut().writeUnsignedWordBigEndian(slot + regionBaseX);
        }

        if (action == 867) {
            if ((id & 3) == 0) {
                setAnInt1175(getAnInt1175() + 1);
            }

            if (getAnInt1175() >= 59) {
                getOut().putOpcode(200);
                getOut().putShort(25501);
                setAnInt1175(0);
            }

            getOut().putOpcode(43);
            getOut().writeUnsignedWordBigEndian(interfaceId);
            getOut().writeUnsignedWordA(nodeId);
            getOut().writeUnsignedWordA(slot);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 543) {
            getOut().putOpcode(237);
            getOut().putShort(slot);
            getOut().writeUnsignedWordA(nodeId);
            getOut().putShort(interfaceId);
            getOut().writeUnsignedWordA(selectedSpellId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 491) {
            Player player = playerArray[nodeId];

            if (player != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, player.smallY[0], myPlayer.smallX[0], false, player.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;
                getOut().putOpcode(14);
                getOut().writeUnsignedWordA(anInt1284);
                getOut().putShort(nodeId);
                getOut().putShort(selectedItemId);
                getOut().writeUnsignedWordBigEndian(anInt1283);
            }
        }

        if (action == 639) {
            String s3 = menuActionName[actionId];
            int k2 = s3.indexOf("@whi@");

            if (k2 != -1) {
                long l4 = TextUtilities.longForName(s3.substring(k2 + 5).trim());
                int k3 = -1;

                for (int i4 = 0; i4 < friendCount; i4++) {
                    if (friendsListAsLongs[i4] != l4) {
                        continue;
                    }
                    k3 = i4;
                    break;
                }

                if (k3 != -1 && friendsNodeIDs[k3] > 0) {
                    updateChatArea = true;
                    inputDialogState = 0;
                    messagePromptRaised = true;
                    promptInput = "";
                    friendsListAction = 3;
                    aLong953 = friendsListAsLongs[k3];
                    promptMessage = "Enter message to send to " + friendsList[k3];
                }
            }
        }

        if (action == 454) {
            getOut().putOpcode(41);
            getOut().putShort(nodeId);
            getOut().writeUnsignedWordA(slot);
            getOut().writeUnsignedWordA(interfaceId);
            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;

            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }

            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }

        if (action == 478) {
            Npc npc = npcArray[nodeId];

            if (npc != null) {
                doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, npc.smallY[0], myPlayer.smallX[0], false, npc.smallX[0]);
                crossX = super.saveClickX;
                crossY = super.saveClickY;
                crossType = 2;
                crossIndex = 0;

                if ((id & 3) == 0) {
                    setAnInt1155(getAnInt1155() + 1);
                }

                if (getAnInt1155() >= 53) {
                    getOut().putOpcode(85);
                    getOut().putByte(66);
                    setAnInt1155(0);
                }

                getOut().putOpcode(18);
                getOut().writeUnsignedWordBigEndian(nodeId);
            }
        }

        if (action == 113) {
            method66(nodeId, y, x, id);
            getOut().putOpcode(70);
            getOut().writeUnsignedWordBigEndian(x + regionBaseX);
            getOut().putShort(y + regionBaseY);
            getOut().writeSignedBigEndian(id);
        }

        if (action == 872) {
            method66(nodeId, y, x, id);
            getOut().putOpcode(234);
            getOut().writeSignedBigEndian(x + regionBaseX);
            getOut().writeUnsignedWordA(id);
            getOut().writeSignedBigEndian(y + regionBaseY);
        }

        if (action == 502) {
            method66(nodeId, y, x, id);
            getOut().putOpcode(132);
            getOut().writeSignedBigEndian(x + regionBaseX);
            getOut().putShort(id);
            getOut().writeUnsignedWordA(y + regionBaseY);
        }

        if (action == 661 || action == 662 || action == 663 || action == 664) {
            getOut().putOpcode(43);
            getOut().writeUnsignedWordBigEndian(interfaceId);
            getOut().writeUnsignedWordA(nodeId);

            int slot_r = 0;
            if (action == 661) {
                slot_r = 1;
            } else if (action == 662) {
                slot_r = 2;
            } else if (action == 663) {
                slot_r = 3;
            } else if (action == 664) {
                slot_r = 4;
            }
            getOut().writeUnsignedWordA(slot_r);

        }

        if (action == 1125) {
            ItemDef definition = ItemDef.get(nodeId);
            if (interfaceId == 38274) {
                getOut().putOpcode(122);
                getOut().putShort(interfaceId);
                getOut().putShort(slot);
                getOut().putShort(nodeId);
            } else {
                getOut().putOpcode(2); // examine item
                getOut().putShort(definition.id);
            }
        }
        if (action == 1126) {
            getOut().putOpcode(138);
            getOut().writeUnsignedWordA(interfaceId);
            getOut().writeUnsignedWordA(slot);
            getOut().writeUnsignedWordA(nodeId);

            atInventoryLoopCycle = 0;
            atInventoryInterface = interfaceId;
            atInventoryIndex = slot;
            atInventoryInterfaceType = 2;
            if (RSInterface.interfaceCache[interfaceId].parentID == openInterfaceID) {
                atInventoryInterfaceType = 1;
            }
            if (RSInterface.interfaceCache[interfaceId].parentID == backDialogID) {
                atInventoryInterfaceType = 3;
            }
        }
        if (action == 169) {
            switch (interfaceId) {
                case 26356:
                    Configuration.RENDER_DISTANCE = !Configuration.RENDER_DISTANCE;
                    updateSetting(interfaceId, !Configuration.RENDER_DISTANCE);
                    if (Configuration.RENDER_DISTANCE) {
                        Configuration.TILE_CLAMPING = 90;
                        Configuration.MODEL_CLAMPING = 4700;
                        Configuration.RENDER_DISTANCE_CLAMP = 1000;
                    } else {
                        Configuration.TILE_CLAMPING = 45;
                        Configuration.MODEL_CLAMPING = 3500;
                        Configuration.RENDER_DISTANCE_CLAMP = 500;
                    }
                    updateGameArea();
                    break;
                case 26358:
                    Configuration.RENDER_SELF = !Configuration.RENDER_SELF;
                    updateSetting(interfaceId, !Configuration.RENDER_SELF);
                    break;
                case 26360:
                    Configuration.RENDER_PLAYERS = !Configuration.RENDER_PLAYERS;
                    updateSetting(interfaceId, !Configuration.RENDER_PLAYERS);
                    break;
                case 26362:
                    Configuration.RENDER_NPCS = !Configuration.RENDER_NPCS;
                    updateSetting(interfaceId, !Configuration.RENDER_NPCS);
                    break;
                case 26364:
                    Configuration.ANIMATE_TEXTURES = !Configuration.ANIMATE_TEXTURES;
                    updateSetting(interfaceId, !Configuration.ANIMATE_TEXTURES);
                    break;
                case 26456:
                    Configuration.DRAW_EFFECT_TIMERS = !Configuration.DRAW_EFFECT_TIMERS;
                    updateSetting(interfaceId, !Configuration.DRAW_EFFECT_TIMERS);
                    break;
                case 26556:
                    Configuration.SPLIT_CHAT = !Configuration.SPLIT_CHAT;
                    variousSettings[287] = variousSettings[502] = variousSettings[502] == 1 ? 0 : 1;
                    updateConfig(287);
                    Save.settings(Client.getClient());
                    updateSetting(interfaceId, !Configuration.SPLIT_CHAT);
                    break;
                case 26558:
                    Configuration.TIME_STAMPS = !Configuration.TIME_STAMPS;
                    updateSetting(interfaceId, !Configuration.TIME_STAMPS);
                    break;
                case 26560:
                    Configuration.HIGHLIGHT_USERNAME = !Configuration.HIGHLIGHT_USERNAME;
                    updateSetting(interfaceId, !Configuration.HIGHLIGHT_USERNAME);
                    break;
                case 26562:
                    Configuration.SMILIES_ENABLED = !Configuration.SMILIES_ENABLED;
                    updateSetting(interfaceId, !Configuration.SMILIES_ENABLED);
                    break;
                case 26656:
                    Configuration.USERNAMES_ABOVE_HEAD = !Configuration.USERNAMES_ABOVE_HEAD;
                    updateSetting(interfaceId, !Configuration.USERNAMES_ABOVE_HEAD);
                    break;
                case 26658:
                    Configuration.ITEM_OUTLINES = !Configuration.ITEM_OUTLINES;
                    updateSetting(interfaceId, !Configuration.ITEM_OUTLINES);
                    break;
                case 26660:
                    Configuration.HP_BAR = !Configuration.HP_BAR;
                    updateSetting(interfaceId, !Configuration.HP_BAR);
                break;
                case 15281:
                    cosmeticTab = !cosmeticTab;
                    sendPacket185(15281);
                    break;
                default:
                    Interfaces.buttonClicked(interfaceId);
                    getOut().putOpcode(185);
                    getOut().putInt(interfaceId);
                    break;

            }

            if (interfaceId == 26003) {
                return;
            }

            RSInterface rsinterface = RSInterface.interfaceCache[interfaceId];

            if (rsinterface.valueIndexArray != null && rsinterface.valueIndexArray[0][0] == 5) {
                int l2 = rsinterface.valueIndexArray[0][1];
                variousSettings[l2] = 1 - variousSettings[l2];
                updateConfig(l2);
                Interfaces.configButton(l2, variousSettings[l2]);
            }
        }

        if (action == 447) {
            itemSelected = 1;
            anInt1283 = slot;
            anInt1284 = interfaceId;
            selectedItemId = nodeId;
            selectedItemName = ItemDef.get(nodeId).name;
            spellSelected = 0;
            return;
        }

        if (action == 1226) {
            ObjectDef definition = ObjectDef.forID(id);
            String examine;

            if (definition.description != null) {
                examine = new String(definition.description);
            } else {
                examine = "It's a " + definition.name + ".";
            }

            pushMessage(examine, 0, "");
        }

        if (action == 244) {
            boolean flag7 = doWalkTo(2, 0, 0, 0, myPlayer.smallY[0], 0, 0, interfaceId, myPlayer.smallX[0], false, slot);

            if (!flag7) {
                flag7 = doWalkTo(2, 0, 1, 0, myPlayer.smallY[0], 1, 0, interfaceId, myPlayer.smallX[0], false, slot);
            }

            crossX = super.saveClickX;
            crossY = super.saveClickY;
            crossType = 2;
            crossIndex = 0;
            getOut().putOpcode(253);
            getOut().writeUnsignedWordBigEndian(slot + regionBaseX);
            getOut().writeSignedBigEndian(interfaceId + regionBaseY);
            getOut().writeUnsignedWordA(nodeId);
        }

        if (action == 1448) {
            ItemDef definition = ItemDef.get(nodeId);
            String examine;

            if (definition.description != null) {
                examine = new String(definition.description);
            } else {
                examine = "It's a " + definition.name + ".";
            }

            pushMessage(examine, 0, "");
        }

        itemSelected = 0; // RIGHT HERE

        if (action != 626) {
            spellSelected = 0;
        }
        if (interfaceId == 26274) {
            variousSettings[287] = variousSettings[502] = variousSettings[502] == 1 ? 0 : 1;
            updateConfig(287);
            Save.settings(Client.getClient());
        }
        if (interfaceId == 26273) {
            safeRenderer = !safeRenderer;
            pushMessage("You are " + (safeRenderer ? "now using" : "no longer using") + " safe rendering.");
           // inputString = "";
        }


        if (interfaceId >= 26265 && interfaceId <= 26274) {
            ClientSettings.newFunctionKeys = variousSettings[1710];
            ClientSettings.newHealthBars = variousSettings[1711];
            ClientSettings.newHitmarks = variousSettings[1712];
            ClientSettings.newCursors = variousSettings[1713];
            ClientSettings.usernamesAboveHead = variousSettings[1714];
            ClientSettings.toggleParticles = variousSettings[1715];
            ClientSettings.groundItemNames = variousSettings[1716];
            ClientSettings.usernameHighlighting = variousSettings[1717];
            ClientSettings.levelUpMessages = variousSettings[1718];
            ClientSettings.placeholder = variousSettings[1719];
            sendClientSettings();
        }

    }

    private void doFlamesDrawing() {
    }

    public void sendPacket185(int buttonID) {
        getOut().putOpcode(185);
        getOut().putInt(buttonID);
        RSInterface rsi = RSInterface.interfaceCache[buttonID];
        if (rsi == null) {
            return;
        }
        if (rsi.valueIndexArray != null && rsi.valueIndexArray[0][0] == 5) {
            int configID = rsi.valueIndexArray[0][1];
            variousSettings[configID] = 1 - variousSettings[configID];
        }
    }

    public void sendPacket185(int button, int toggle, int type) {
        if (type == 135) {
            RSInterface class9 = RSInterface.interfaceCache[button];
            boolean flag8 = true;
            if (class9.contentType > 0) {
                flag8 = promptUserForInput(class9);
            }
            if (flag8) {
                Interfaces.buttonClicked(button);
                getOut().putOpcode(185);
                getOut().putInt(button);
            }
        }
    }

    private boolean doWalkTo(int movementType, int orientation, int height, int type, int initialY, int width, int surroundings, int finalY, int initialX, boolean flag, int finalX) {
        byte mapWidth = 104;
        byte mapLength = 104;
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapLength; y++) {
                anIntArrayArray901[x][y] = 0;
                distances[x][y] = 0x5f5e0ff;
            }
        }

        int currentX = initialX;
        int currentY = initialY;
        /**
         * This is not in standard 317 Call
         */
        if (initialX >= anIntArrayArray901.length || initialY >= anIntArrayArray901[initialX].length) {
            return true;
        }
        anIntArrayArray901[initialX][initialY] = 99;
        distances[initialX][initialY] = 0;
        int nextIndex = 0;
        int currentIndex = 0;
        waypointX[nextIndex] = initialX;
        waypointY[nextIndex++] = initialY;
        boolean reached = false;
        int waypoints = waypointX.length;
        int[][] adjancencies = collisionData[plane].flags;


        while (currentIndex != nextIndex) {
            currentX = waypointX[currentIndex];
            currentY = waypointY[currentIndex];
            currentIndex = (currentIndex + 1) % waypoints;

            if (currentX == finalX && currentY == finalY) {
                reached = true;
                break;
            }

            if (type != 0) {
                if ((type < 5 || type == 10) && collisionData[plane].reachedWall(finalX, currentX, currentY, orientation, type - 1, finalY)) {
                    reached = true;
                    break;
                }

                if (type < 10 && collisionData[plane].reachedDecoration(finalX, finalY, currentY, type - 1, orientation, currentX)) {
                    reached = true;
                    break;
                }
            }

            if (width != 0 && height != 0 && collisionData[plane].readObject(finalY, finalX, currentX, height, surroundings, width, currentY)) {
                reached = true;
                break;
            }

            int distance = distances[currentX][currentY] + 1;

            if (currentX > 0 && anIntArrayArray901[currentX - 1][currentY] == 0 && (adjancencies[currentX - 1][currentY] & 0x1280108) == 0) {
                waypointX[nextIndex] = currentX - 1;
                waypointY[nextIndex] = currentY;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX - 1][currentY] = 2;
                distances[currentX - 1][currentY] = distance;
            }

            if (currentX < mapWidth - 1 && anIntArrayArray901[currentX + 1][currentY] == 0 && (adjancencies[currentX + 1][currentY] & 0x1280180) == 0) {
                waypointX[nextIndex] = currentX + 1;
                waypointY[nextIndex] = currentY;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX + 1][currentY] = 8;
                distances[currentX + 1][currentY] = distance;
            }

            if (currentY > 0 && anIntArrayArray901[currentX][currentY - 1] == 0 && (adjancencies[currentX][currentY - 1] & 0x1280102) == 0) {
                waypointX[nextIndex] = currentX;
                waypointY[nextIndex] = currentY - 1;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX][currentY - 1] = 1;
                distances[currentX][currentY - 1] = distance;
            }

            if (currentY < mapLength - 1 && anIntArrayArray901[currentX][currentY + 1] == 0 && (adjancencies[currentX][currentY + 1] & 0x1280120) == 0) {
                waypointX[nextIndex] = currentX;
                waypointY[nextIndex] = currentY + 1;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX][currentY + 1] = 4;
                distances[currentX][currentY + 1] = distance;
            }

            if (currentX > 0 && currentY > 0 && anIntArrayArray901[currentX - 1][currentY - 1] == 0 && (adjancencies[currentX - 1][currentY - 1] & 0x128010e) == 0 && (adjancencies[currentX - 1][currentY] & 0x1280108) == 0 && (adjancencies[currentX][currentY - 1] & 0x1280102) == 0) {
                waypointX[nextIndex] = currentX - 1;
                waypointY[nextIndex] = currentY - 1;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX - 1][currentY - 1] = 3;
                distances[currentX - 1][currentY - 1] = distance;
            }

            if (currentX < mapWidth - 1 && currentY > 0 && anIntArrayArray901[currentX + 1][currentY - 1] == 0 && (adjancencies[currentX + 1][currentY - 1] & 0x1280183) == 0 && (adjancencies[currentX + 1][currentY] & 0x1280180) == 0 && (adjancencies[currentX][currentY - 1] & 0x1280102) == 0) {
                waypointX[nextIndex] = currentX + 1;
                waypointY[nextIndex] = currentY - 1;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX + 1][currentY - 1] = 9;
                distances[currentX + 1][currentY - 1] = distance;
            }

            if (currentX > 0 && currentY < mapLength - 1 && anIntArrayArray901[currentX - 1][currentY + 1] == 0 && (adjancencies[currentX - 1][currentY + 1] & 0x1280138) == 0 && (adjancencies[currentX - 1][currentY] & 0x1280108) == 0 && (adjancencies[currentX][currentY + 1] & 0x1280120) == 0) {
                waypointX[nextIndex] = currentX - 1;
                waypointY[nextIndex] = currentY + 1;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX - 1][currentY + 1] = 6;
                distances[currentX - 1][currentY + 1] = distance;
            }

            if (currentX < mapWidth - 1 && currentY < mapLength - 1 && anIntArrayArray901[currentX + 1][currentY + 1] == 0 && (adjancencies[currentX + 1][currentY + 1] & 0x12801e0) == 0 && (adjancencies[currentX + 1][currentY] & 0x1280180) == 0 && (adjancencies[currentX][currentY + 1] & 0x1280120) == 0) {
                waypointX[nextIndex] = currentX + 1;
                waypointY[nextIndex] = currentY + 1;
                nextIndex = (nextIndex + 1) % waypoints;
                anIntArrayArray901[currentX + 1][currentY + 1] = 12;
                distances[currentX + 1][currentY + 1] = distance;
            }
        }
        anInt1264 = 0;


        if (!reached) {
            if (flag) {
                int i5 = 100;
                /**
                 * Somewhere in here would require a call to calculate closest path to obstruction, Similar to above calls with Walls.
                 * This would then set your finalX/finalY to the above result and recalculate movement :)
                 */
                for (int delta = 1; delta < 2; delta++) {
                    for (int x = finalX - delta; x <= finalX + delta; x++) {
                        for (int y = finalY - delta; y <= finalY + delta; y++) {
                            if (x >= 0 && y >= 0 && x < 104 && y < 104 && distances[x][y] < i5) {
                                i5 = distances[x][y];
                                currentX = x;
                                currentY = y;
                                anInt1264 = 1;
                                reached = true;
                            }
                        }
                    }

                    if (reached) {
                        break;
                    }
                }

            }

            if (!reached) { //Returns if the path isn't valid.
                return false;
            }
        }
        currentIndex = 0;
        waypointX[currentIndex] = currentX;
        waypointY[currentIndex++] = currentY;
        int l5;

        for (int j5 = l5 = anIntArrayArray901[currentX][currentY]; currentX != initialX || currentY != initialY; j5 = anIntArrayArray901[currentX][currentY]) {
            if (j5 != l5) {
                l5 = j5;
                waypointX[currentIndex] = currentX;
                waypointY[currentIndex++] = currentY;
            }

            if ((j5 & 2) != 0) {
                currentX++;
            } else if ((j5 & 8) != 0) {
                currentX--;
            }

            if ((j5 & 1) != 0) {
                currentY++;
            } else if ((j5 & 4) != 0) {
                currentY--;
            }
        }

        if (currentIndex > 0) {
            int waypointCount = currentIndex;

            if (waypointCount > 25) {
                waypointCount = 25;
            }

            currentIndex--;
            int k6 = waypointX[currentIndex];
            int i7 = waypointY[currentIndex];
            setAnInt1288(getAnInt1288() + waypointCount);

            if (getAnInt1288() >= 92) {
                getOut().putOpcode(36);
                getOut().putInt(0);
                setAnInt1288(0);
            }

            if (movementType == 0) {
                getOut().putOpcode(229);
                getOut().putByte(plane);
                getOut().putOpcode(164);
                getOut().putByte(waypointCount + waypointCount + 3);
            }

            if (movementType == 1) {
                getOut().putOpcode(229);
                getOut().putByte(plane);
                getOut().putOpcode(248); //Minimap Walking
                getOut().putByte(waypointCount + waypointCount + 3 + 14);
            }

            if (movementType == 2) {
                getOut().putOpcode(229);
                getOut().putByte(plane);
                getOut().putOpcode(98);
                getOut().putByte(waypointCount + waypointCount + 3);
            }

            getOut().writeSignedBigEndian(k6 + regionBaseX);
            destinationX = waypointX[0];
            destinationY = waypointY[0];

            for (int j7 = 1; j7 < waypointCount; j7++) {
                currentIndex--;
                getOut().putByte(waypointX[currentIndex] - k6);
                getOut().putByte(waypointY[currentIndex] - i7);
            }

            getOut().writeUnsignedWordBigEndian(i7 + regionBaseY);
            getOut().method424(super.keyArray[5] != 1 ? 0 : 1);
            return true;
        }

        return movementType != 1;
    }

    private void draw3dScreen() {
        if (!chatArea.componentHidden()) {
            drawSplitPrivateChat();
        }

        alertHandler.processAlerts();
        alertManager.processAlerts();

        if (spin) {
            startSpinner();
        }

        if (crossType == 1) {
            crosses[crossIndex / 100].drawSprite(crossX - 8 - 4, crossY - 8 - 4);
            anInt1142++;

            if (anInt1142 > 67) {
                anInt1142 = 0;
            }
        }

        if (crossType == 2) {
            crosses[4 + crossIndex / 100].drawSprite(crossX - 8 - 4, crossY - 8 - 4);
        }

        if (getWalkableInterfaceId() > 0) {
            processInterfaceAnimation(anInt945, getWalkableInterfaceId());
            if (getWalkableInterfaceId() == 15892 && GameFrame.getScreenMode() != ScreenMode.FIXED) {
                drawInterface(0, getScreenWidth() / 2 - RSInterface.interfaceCache[getWalkableInterfaceId()].width + 20, RSInterface.interfaceCache[getWalkableInterfaceId()], 0);
            } else if ((getWalkableInterfaceId() == 201 || getWalkableInterfaceId() == 197) && GameFrame.getScreenMode() != ScreenMode.FIXED) {
                drawInterface(0, getScreenWidth() - 765 + 15, RSInterface.interfaceCache[getWalkableInterfaceId()], -255 + 10 + 4);
            } else if (getWalkableInterfaceId() == 197) {
                drawInterface(0, getScreenWidth() - 765 - (GameFrame.getScreenMode() != ScreenMode.FIXED ? 30 : 0), RSInterface.interfaceCache[42020], 10);
            } else {
                drawInterface(0, 0, RSInterface.interfaceCache[getWalkableInterfaceId()], 0);
            }

        }

        drawParallelWidgets();
        if (openInterfaceID != -1) {
            processInterfaceAnimation(anInt945, openInterfaceID);
            RSInterface rsInterface = RSInterface.interfaceCache[openInterfaceID];

            int width = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenWidth() : 516;
            int height = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenHeight() : 338;

            if (GameFrame.getScreenMode() != ScreenMode.FIXED) {
                drawInterface(0, (width - 765) / 2, rsInterface, (height - 503) / 2);
            } else {
                drawInterface(0, 0, rsInterface, 0);// first 1
            }
            widgetHoverChild = -1;
        } else {
            if (openInterfaceID == 5292) {
                drawOnBankInterface();
                if (bankItemDragSprite != null) {
                    bankItemDragSprite.drawSprite(bankItemDragSpriteX, bankItemDragSpriteY);
                }
            }
            getGrandExchange().drawGrandExchange();
        }

        // method70();

        childHovered = null;


        EffectTimers.draw();


        if (!menuOpen) {
            processRightClick();
            drawTooltip();
        } else if (menuScreenArea == 0) {
            drawMenu();
        }

        if (drawMultiwayIcon == 1) {
            multiOverlay.drawSprite(GameFrame.getScreenMode() == ScreenMode.FIXED ? 472 : getScreenWidth() - 40, GameFrame.getScreenMode() == ScreenMode.FIXED ? 296 : 175);
        }
        if (drawXPwayIcon == 1) {
            XPOverlay.drawSprite(GameFrame.getScreenMode() == ScreenMode.FIXED ? 472 : getScreenWidth() - 40, GameFrame.getScreenMode() == ScreenMode.FIXED ? 296 : 175);
        }

        if (Objects.nonNull(currentTarget)) {
            showCombatBox();
        }

        int x = regionBaseX + (myPlayer.x - 6 >> 7);
        int y = regionBaseY + (myPlayer.y - 6 >> 7);
        if (debug) {
            int minus = 45;
            normalText.method385(0xffff00, "Fps: " + super.fps, 285 - minus, 5);
            Runtime runtime = Runtime.getRuntime();
            int textColor = 0xFFFF00;
            int memory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            if (memory > 0x2000000 && lowDetail) {
                textColor = 0xff0000;
            }

            normalText.method385(textColor, "Mem: " + memory + "k", 299 - minus, 5);
            normalText.method385(0xffff00, "Mouse X: " + super.mouseX + " , Mouse Y: " + super.mouseY, 314 - minus, 5);
            normalText.method385(0xffff00, "Coords: " + x + ", " + y, 329 - minus, 5);
            normalText.method385(0xffff00, "Client resolution: " + getScreenWidth() + "x" + getScreenHeight(), 344 - minus, 5);
            normalText.method385(0xffff00, "Object Maps: " + objectMaps + ";", 359 - minus, 5);
            normalText.method385(0xffff00, "Floor Maps: " + floorMaps + ";", 374 - minus, 5);
        }
        if (fpsOn) {
            int textX = mapArea.getxPos() - 90;
            int textY = 20;
            int textColor = 0xffff00;
            if (super.fps < 15) {
                textColor = 0xff0000;
            }
            normalText.method385(textColor, "Fps:" + super.fps, textY, textX);
            textY += 15;
            Runtime runtime = Runtime.getRuntime();
            int memory = (int) ((runtime.totalMemory() - runtime.freeMemory()) / 1024L);
            if (memory > 0x2000000 && lowDetail) {
                textColor = 0xff0000;
            }
            normalText.method385(textColor, "Mem:" + memory + "k", textY, textX);
            textY += 15;
            normalText.method385(textColor, "MouseX:" + super.mouseX + "", textY, textX);
            textY += 15;
            normalText.method385(textColor, "MouseY:" + super.mouseY + "", textY, textX);
            textY += 15;
            normalText.method385(0xffff00, "Object Maps: " + objectMaps + ";", textY, 5);
            textY += 15;
            normalText.method385(0xffff00, "Floor Maps: " + floorMaps + ";", textY, 5);
        }

        if (systemUpdateTimer != 0) {
            int j = systemUpdateTimer / 50;
            int l = j / 60;
            j %= 60;

            if (j < 10) {
                normalText.method385(0xffff00, "System update in: " + l + ":0" + j, GameFrame.isFixed() ? 40 : getScreenHeight() - 40, 4);
            } else {
                normalText.method385(0xffff00, "System update in: " + l + ":" + j, GameFrame.isFixed() ? 40 : getScreenHeight() - 40, 4);
            }

            if (++anInt849 > 75) {
                anInt849 = 0;
                getOut().putOpcode(148);
            }
        } else {
            if (broadcastMinutes == 0) {
                broadcastMessage = null;
            }

            if (broadcastMessage != null && systemUpdateTimer == 0) {
                normalText.method385(0xffff00, broadcastMessage, GameFrame.isFixed() ? 329 : getScreenHeight() - 168, 4);
            }

        }
    }

    private void drawAnimatedWorldBackground(boolean display) {
        if (display) {
            int centerX = getScreenWidth() / 2;
            int centerY = getScreenHeight() / 2;

            if (getScriptManager() == null) {
                loginScreenBG(true);
            }

            int canvasCenterX = Rasterizer3D.textureInt1;
            int canvasCenterY = Rasterizer3D.textureInt2;
            int[] canvasPixels = Rasterizer3D.lineOffsets;

            if (titleScreenOffsets != null && (titleWidth != getScreenWidth() || titleHeight != getScreenHeight())) {
                titleScreenOffsets = null;
            }

            if (titleScreenOffsets == null) {
                titleWidth = getScreenWidth();
                titleHeight = getScreenHeight();
                titleScreenOffsets = Rasterizer3D.getOffsets(titleWidth, titleHeight);
            }

            Rasterizer3D.textureInt1 = centerX;
            Rasterizer3D.textureInt2 = centerY;
            Rasterizer3D.lineOffsets = titleScreenOffsets;

            if (loadingStage == 2 && Region.currentPlane != plane) {
                loadingStage = 1;
            }

            if (!loggedIn && loadingStage == 1) {
                getMapLoadingState();
            }

            if (!loggedIn && loadingStage == 2 && plane != getLastKnownPlane()) {
                setLastKnownPlane(plane);
                renderedMapScene(plane);
            }

            if (loadingStage == 2) {
                try {
                    scene.render(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, getHighestPlane(), yCameraCurve);
                    drawFadeTransition();
                    scene.clearObj5Cache();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            if (getScriptManager() != null && loadingStage == 2 && plane == getLastKnownPlane() && !loggedIn) {
                getScriptManager().cycle();
            }

            Rasterizer3D.textureInt1 = canvasCenterX;
            Rasterizer3D.textureInt2 = canvasCenterY;
            Rasterizer3D.lineOffsets = canvasPixels;
        }
    }

    private int fadeOutEndCycle, fadeInEndCycle, fadeInStartCycle, hiddenEndCycle;
    public void drawFadeTransition() {
        if(fadeOutEndCycle < loopCycle) {
            return;
        }
        int alpha;
        if(fadeInEndCycle >= loopCycle) {
            alpha = (loopCycle - fadeInStartCycle << 8) / (fadeInEndCycle - fadeInStartCycle);
        } else if(hiddenEndCycle >= loopCycle) {
            alpha = 255;
        } else {
            alpha = 255 - (loopCycle - hiddenEndCycle << 8) / (fadeOutEndCycle - hiddenEndCycle);
        }
      //  System.out.println("Alpha: " + alpha + " - " + fadeInEndCycle);
        TextDrawingArea.fillRectangle(0, 0, gameAreaWidth, gameAreaHeight, alpha, 0);
    }

    private void drawBlackBox(int x, int y) {
        TextDrawingArea.drawPixels(71, y - 1, x - 2, 0x726451, 1);
        TextDrawingArea.drawPixels(69, y, x + 174, 0x726451, 1);
        TextDrawingArea.drawPixels(1, y - 2, x - 2, 0x726451, 178);
        TextDrawingArea.drawPixels(1, y + 68, x, 0x726451, 174);
        TextDrawingArea.drawPixels(71, y - 1, x - 1, 0x2E2B23, 1);
        TextDrawingArea.drawPixels(71, y - 1, x + 175, 0x2E2B23, 1);
        TextDrawingArea.drawPixels(1, y - 1, x, 0x2E2B23, 175);
        TextDrawingArea.drawPixels(1, y + 69, x, 0x2E2B23, 175);
        TextDrawingArea.method335(0, y, 174, 68, 220, x);
    }

    private void drawChatArea() {
        chatArea.setxPos(0);
        chatArea.setyPos(GameFrame.getScreenMode() == ScreenMode.FIXED ? 338 : getScreenHeight() - 165);
        chatArea.render(this);
    }

    private void drawFlames() {
        drawingFlames = true;

        try {
            long l = System.currentTimeMillis();
            int i = 0;
            int j = 20;

            while (aBoolean831) {
                doFlamesDrawing();

                if (++i > 10) {
                    long l1 = System.currentTimeMillis();
                    int k = (int) (l1 - l) / 10 - j;
                    j = 40 - k;

                    if (j < 5) {
                        j = 5;
                    }

                    i = 0;
                    l = l1;
                }

                try {
                    Thread.sleep(j);
                } catch (Exception _ex) {
                }
            }
        } catch (Exception _ex) {
        }

        drawingFlames = false;
    }

    private void drawFriendsListOrWelcomeScreen(RSInterface rsi) {
        int j = rsi.contentType;

        if (j >= 205 && j <= 231) {
            j -= 205;
            if (rsi.message != null)
                if (rsi.message.contains("logout"))
                    return;
            rsi.message = setMessage(j);
            return;
        }

        if (j >= 1 && j <= 100 || j >= 701 && j <= 800) {
            if (j == 1 && getAnInt900() == 0) {
                rsi.message = "Loading friend list";
                rsi.atActionType = 0;
                return;
            }

            if (j == 1 && getAnInt900() == 1) {
                rsi.message = "Connecting to friendserver";
                rsi.atActionType = 0;
                return;
            }

            if (j == 2 && getAnInt900() != 2) {
                rsi.message = "Please wait...";
                rsi.atActionType = 0;
                return;
            }

            int k = friendCount;

            if (getAnInt900() != 2) {
                k = 0;
            }

            if (j > 700) {
                j -= 601;
            } else {
                j--;
            }

            if (j >= k) {
                rsi.message = "";
                rsi.atActionType = 0;
                return;
            } else {
                rsi.message = friendsList[j];
                rsi.atActionType = 1;
                return;
            }
        }

        if (j == 6060) {
            if (rsi.verticalBar) {
                int itemCount = 0;
                for (int i = 0; i < rsi.inv.length; i++) {
                    if (rsi.inv[i] > 0) {
                        itemCount++;
                    }
                }
                if (itemCount > rsi.width || rsi.scrollPosition != 0) {
                    if (rsi.frameTimer < 0) { // move left
                        if (--rsi.scrollPosition <= -(32 + rsi.invSpritePadX)
                                * (itemCount / rsi.width - rsi.viewableColumns + (itemCount % 2 == 0 ? 0 : 1))) {
                            rsi.frameTimer = 1;
                        }

                    } else if (rsi.frameTimer == 0) { // move right
                        if (++rsi.scrollPosition >= 0) {
                            rsi.frameTimer = 101;
                        }
                    } else {
                        rsi.frameTimer++;
                        if (rsi.frameTimer == 200) {
                            rsi.frameTimer = -1; // start move left
                        } else if (rsi.frameTimer == 100) {
                            rsi.frameTimer = 0; // start move right
                        }
                    }
                    RSInterface parent = RSInterface.interfaceCache[rsi.parentID];
                    parent.childY[0] = rsi.scrollPosition;
                }
            } else {
                int itemCount = 0;
                for (int i = 0; i < rsi.inv.length; i++) {
                    if (rsi.inv[i] > 0) {
                        itemCount++;
                    }
                }
                itemCount --;
                if (itemCount > rsi.viewableColumns || rsi.scrollPosition != 0) {
                    if (rsi.frameTimer < 0) { // move left
                        if (--rsi.scrollPosition <= -(32 + rsi.invSpritePadX + 1)
                                * (itemCount / rsi.height - rsi.viewableColumns + 1)) {
                            rsi.frameTimer = 1;
                        }

                    } else if (rsi.frameTimer == 0) { // move right
                        if (++rsi.scrollPosition >= 0) {
                            rsi.frameTimer = 101;
                        }
                    } else {
                        rsi.frameTimer++;
                        if (rsi.frameTimer == 200) {
                            rsi.frameTimer = -1; // start move left
                        } else if (rsi.frameTimer == 100) {
                            rsi.frameTimer = 0; // start move right
                        }
                    }
                    RSInterface parent = RSInterface.interfaceCache[rsi.parentID];
                    parent.childX[0] = rsi.scrollPosition;
                }
            }

        }

        if (j == 901) {
            rsi.message = friendCount + "";
            return;
        }

        if (j == 902) {
            rsi.message = ignoreCount + "";
            return;
        }

        if (j >= 101 && j <= 200 || j >= 801 && j <= 900) {
            int l = friendCount;

            if (getAnInt900() != 2) {
                l = 0;
            }

            if (j > 800) {
                j -= 701;
            } else {
                j -= 101;
            }

            if (j >= l) {
                rsi.message = "";
                rsi.atActionType = 0;
                return;
            }

            if (friendsNodeIDs[j] == 0) {
                rsi.message = "@red@Offline";
            } else if (friendsNodeIDs[j] == nodeID) {
                rsi.message = "@gre@Online"/* + (friendsNodeIDs[j] - 9) */;
            } else {
                rsi.message = "@red@Offline"/* + (friendsNodeIDs[j] - 9) */;
            }

            rsi.atActionType = 1;
            return;
        }

        if (j == 203) {
            int i1 = friendCount;

            if (getAnInt900() != 2) {
                i1 = 0;
            }

            rsi.scrollMax = i1 * 15 + 20;

            if (rsi.scrollMax <= rsi.height) {
                rsi.scrollMax = rsi.height + 1;
            }

            return;
        }

        if (j >= 401 && j <= 500) {
            if ((j -= 401) == 0 && getAnInt900() == 0) {
                rsi.message = "Loading ignore list";
                rsi.atActionType = 0;
                return;
            }

            if (j == 1 && getAnInt900() == 0) {
                rsi.message = "Please wait...";
                rsi.atActionType = 0;
                return;
            }

            int j1 = ignoreCount;

            if (getAnInt900() == 0) {
                j1 = 0;
            }

            if (j >= j1) {
                rsi.message = "";
                rsi.atActionType = 0;
                return;
            } else {
                rsi.message = TextUtilities.fixName(TextUtilities.nameForLong(ignoreListAsLongs[j]));
                rsi.atActionType = 1;
                return;
            }
        }

        if (j == 503) {
            rsi.scrollMax = ignoreCount * 15 + 20;

            if (rsi.scrollMax <= rsi.height) {
                rsi.scrollMax = rsi.height + 1;
            }

            return;
        }

        if (j == 327) {
            rsi.modelRotationY = 150;
            rsi.modelRotationX = (int) (Math.sin(loopCycle / 40D) * 256D) & 0x7FF;

            if (aBoolean1031) {
                for (int k1 = 0; k1 < 7; k1++) {
                    int l1 = myAppearance[k1];

                    if (l1 >= 0 && !IdentityKit.cache[l1].method537()) {
                        return;
                    }
                }

                aBoolean1031 = false;
                Model[] aclass30_sub2_sub4_sub6s = new Model[7];
                int i2 = 0;

                for (int j2 = 0; j2 < 7; j2++) {
                    int k2 = myAppearance[j2];

                    if (k2 >= 0) {
                        aclass30_sub2_sub4_sub6s[i2++] = IdentityKit.cache[k2].method538();
                    }
                }

                Model model = new Model(i2, aclass30_sub2_sub4_sub6s);

                for (int l2 = 0; l2 < 5; l2++) {
                    if (anIntArray990[l2] != 0) {
                        model.method476(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);

                        if (l2 == 1) {
                            model.method476(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
                        }
                    }
                }

                model.createBones();
                model.applyTransform(AnimationDefinition.cache[myPlayer.anInt1511].frameIDs[0]);
                model.light(64, 850, -30, -50, -30, true);
                rsi.mediaType = 5;
                rsi.mediaID = 0;
                RSInterface.clearModelCache(aBoolean994, model);
            }

            return;
        }

        if (j == 3292) {
            if (!(PetSystem.petSelected > 0)) {
                return;
            }
            PetSystem petDef = new PetSystem(EntityDef.get(PetSystem.petSelected));
            rsi.modelRotationY = 150;
            rsi.modelRotationX = (int) (loopCycle / 100D * 1024D) & 2047;
            Model model;
            final Model[] parts = new Model[petDef.getModelArrayLength()];
            for (int i = 0; i < petDef.getModelArrayLength(); i++) {
                parts[i] = Model.fetchModel(petDef.getModelArray()[i]);
            }
            if (parts.length == 1) {
                model = parts[0];
            } else {
                model = new Model(parts.length, parts);
            }

            if (model == null) {
                return;
            }

            model.createBones();
            model.scale2((int) 1.5);
            model.applyTransform(AnimationDefinition.cache[petDef.getAnimation()].frameIDs[PetSystem.animationFrame]);
            model.light(64, 850, -30, -50, -30, true);
            rsi.mediaType = 5;
            rsi.mediaID = 0;
            RSInterface.clearModelCache(aBoolean994, model);
            return;
        }
        if (j == 1430 && rsi.scrollMax > 5) {
            if (rsi.pauseTicks > 0) {
                rsi.pauseTicks--;
                return;
            }
            RSInterface parent = RSInterface.interfaceCache[rsi.parentID];
            if (rsi.scrollPosition == -rsi.scrollMax) {
                rsi.endReached = true;
                rsi.pauseTicks = 20;
            }
            if (rsi.endReached) {
                if (rsi.scrollPosition == 0) {
                    rsi.endReached = false;
                    rsi.pauseTicks = 20;
                }
                rsi.scrollPosition++;
            } else {
                rsi.scrollPosition--;
            }
            parent.childX[0] = rsi.scrollPosition;
        }
        if (j == 3500) {
            rsi.modelRotationY = ItemDef.get(rsi.mediaID).rotation_y;
            rsi.modelRotationX = ItemDef.get(rsi.mediaID).rotation_x;
            rsi.modelZoom = ItemDef.get(rsi.mediaID).modelZoom / 2;
            rsi.xOffset = 0;
            rsi.yOffset = 0;
        }
        if (j == 3600) {
            final EntityDef npc = EntityDef.get(rsi.contentId);
            InterfaceNPC petDef = new InterfaceNPC(npc);
            Model model;
            final Model[] parts = new Model[petDef.getModelArrayLength()];
            for (int i = 0; i < petDef.getModelArrayLength(); i++) {
                parts[i] = Model.fetchModel(petDef.getModelArray()[i]);
            }
            if (parts.length == 1) {
                model = parts[0];
            } else {
                model = new Model(parts.length, parts);
            }
            if (model == null) {
                return;
            }
            model.createBones();
            model.applyTransform(AnimationDefinition.cache[petDef.getAnimation()].frameIDs[InterfaceNPC.animationFrame]);
            rsi.mediaType = 5;
            rsi.mediaID = 0;
            RSInterface.clearModelCache(aBoolean994, model);
            if (!InterfaceNPC.isPetAnimationRunning) {
                InterfaceNPC.updateAnimations();
            }
            return;
        }

        if (j == 3291) {
            RSInterface rsInterface = rsi;
            // int npcDisplay = EntityDef.get(npcDisplay);
            final EntityDef npc = EntityDef.get(rsi.contentId);
            if (npc.npcModels == null) {
                return;
            }
            InterfaceNPC petDef = new InterfaceNPC(npc);
            int verticleTilt = 150;
            rsInterface.modelRotationY = verticleTilt;
            rsInterface.modelRotationX = (int) (loopCycle / 100D * 1024D) & 2047;
            Model model;
            final Model[] parts = new Model[petDef.getModelArrayLength()];
            for (int i = 0; i < petDef.getModelArrayLength(); i++) {
                parts[i] = Model.fetchModel(petDef.getModelArray()[i]);
            }
            if (parts.length == 1) {
                model = parts[0];
            } else {
                model = new Model(parts.length, parts);
            }

            if (model == null) {
                return;
            }


            if (npc.originalModelColours != null) {
                for (int k1 = 0; k1 < npc.originalModelColours.length; k1++) {
                    model.method476(npc.originalModelColours[k1], npc.changedModelColours[k1]);
                }
            }
            if (npc.rdc > 0) {
                model.method1337(npc.rdc);
            }
            if (npc.rdc2 != 0) {
                model.method1338(npc.rdc2);
            }
            if (npc.rdc3 != 0) {
                model.method1339(npc.rdc3);
            }

            if (npc.scaleXZ != 128 || npc.scaleY != 128) {
                model.scaleT(npc.scaleXZ, npc.scaleXZ, npc.scaleY);
            }

            if (npc.id == 9819) {
                model.translate(0, -100, 0);
            }


            double size = 1.5;
            model.createBones();
            model.scale2((int) size);
            model.applyTransform(AnimationDefinition.cache[petDef.getAnimation()].frameIDs[InterfaceNPC.animationFrame]);
            rsInterface.mediaType = 5;
            rsInterface.mediaID = 0;
            RSInterface.clearModelCache(aBoolean994, model);
            if (!InterfaceNPC.isPetAnimationRunning) {
                InterfaceNPC.updateAnimations();
            }
            return;
        }
        if (j == 32921) {
            RSInterface rsInterface = rsi;
            npcDisplay = EntityDef.get(rsInterface.npcDisplay);

            if (rsInterface.npcDisplay != -1 && rsInterface.npcDisplay != 5090) {
                InterfaceNPC petDef = new InterfaceNPC(npcDisplay);
                int verticleTilt = 150;
                rsInterface.modelRotationY = verticleTilt;
                rsInterface.modelRotationX = (int) (loopCycle / 100D * 1024D) & 2047;

                Model model;


                final Model[] parts = new Model[petDef.getModelArrayLength()];
                for (int i = 0; i < petDef.getModelArrayLength(); i++) {
                    parts[i] = Model.fetchModel(petDef.getModelArray()[i]);
                }
                if (parts.length == 1) {
                    model = parts[0];
                } else {
                    model = new Model(parts.length, parts);
                }
                if (model == null) {
                    return;
                }
                if (npcDisplay.originalModelColours != null) {
                    for (int k1 = 0; k1 < npcDisplay.originalModelColours.length; k1++) {
                        model.recolour(npcDisplay.originalModelColours[k1], npcDisplay.changedModelColours[k1]);
                    }
                }
                model.createBones();

                //    InterfaceNPC.cached_Models.put(npcDisplay.id, model);

                model.applyTransform(AnimationDefinition.cache[petDef.getAnimation()].frameIDs[InterfaceNPC.animationFrame]);
                // model.light(80, 850, -30, -150, -30, true); // 100% OSRS/667
                // model.light(64, 100, 100, 100, 100, true); // 100% OSRS/667
                rsInterface.mediaType = 5;
                rsInterface.mediaID = 0;
                RSInterface.clearModelCache(aBoolean994, model);
                if (!InterfaceNPC.isPetAnimationRunning) {
                    InterfaceNPC.updateAnimations();
                }
                return;
            } else {
                rsInterface.mediaType = 5;
                rsInterface.mediaID = 0;
                RSInterface.clearModelCache(aBoolean994, null);

            }
        }

        if (j == 328) {
            RSInterface rsInterface = rsi;
            int verticleTilt = 150;
            int animationSpeed = (int) (Math.sin(loopCycle / 40D) * 256D) & 0x7ff;
            rsInterface.modelRotationY = verticleTilt;
            rsInterface.modelRotationX = animationSpeed;

            if (aBoolean1031) {
                Model characterDisplay = myPlayer.method452();

                for (int l2 = 0; l2 < 5; l2++) {
                    if (anIntArray990[l2] != 0) {
                        characterDisplay.method476(anIntArrayArray1003[l2][0], anIntArrayArray1003[l2][anIntArray990[l2]]);
                        if (l2 == 1) {
                            characterDisplay.method476(anIntArray1204[0], anIntArray1204[anIntArray990[l2]]);
                        }
                    }
                }

                int staticFrame = myPlayer.anInt1511;
                if (myPlayer.method452() != null) {
                    myPlayer.method452().createBones();
                    myPlayer.method452().applyTransform(AnimationDefinition.cache[staticFrame].frameIDs[0]);
                    rsInterface.mediaType = 5;
                    rsInterface.mediaID = 0;
                    RSInterface.clearModelCache(aBoolean994, myPlayer.method452());
                }
            }

            return;
        }

        if (j == 324) {
            if (aClass30_Sub2_Sub1_Sub1_931 == null) {
                aClass30_Sub2_Sub1_Sub1_931 = rsi.disabledSprite;
                aClass30_Sub2_Sub1_Sub1_932 = rsi.enabledSprite;
            }

            if (isMale) {
                rsi.disabledSprite = aClass30_Sub2_Sub1_Sub1_932;
                return;
            } else {
                rsi.disabledSprite = aClass30_Sub2_Sub1_Sub1_931;
                return;
            }
        }

        if (j == 325) {
            if (aClass30_Sub2_Sub1_Sub1_931 == null) {
                aClass30_Sub2_Sub1_Sub1_931 = rsi.disabledSprite;
                aClass30_Sub2_Sub1_Sub1_932 = rsi.enabledSprite;
            }

            if (isMale) {
                rsi.disabledSprite = aClass30_Sub2_Sub1_Sub1_931;
                return;
            } else {
                rsi.disabledSprite = aClass30_Sub2_Sub1_Sub1_932;
                return;
            }
        }

        if (j == 650 || j == 655) {
            if (anInt1193 != 0) {
                String lastVisit;

                if (daysSinceLastLogin == 0) {
                    lastVisit = "earlier today";
                } else if (daysSinceLastLogin == 1) {
                    lastVisit = "yesterday";
                } else {
                    lastVisit = daysSinceLastLogin + " days ago";
                }

                rsi.message = "You last logged in " + lastVisit + " from: " + Signlink.dns;
            } else {
                rsi.message = "";
            }
        }

        if (j == 651) {
            if (unreadMessages == 0) {
                rsi.message = "0 unread messages";
                rsi.disabledColor = 0xffff00;
            } else if (unreadMessages == 1) {
                rsi.message = "1 unread message";
                rsi.disabledColor = 65280;
            } else if (unreadMessages > 1) {
                rsi.message = unreadMessages + " unread messages";
                rsi.disabledColor = 65280;
            }
        }

        if (j == 652) {
            if (daysSinceRecovChange == 201) {
                if (membersInt == 1) {
                    rsi.message = "@yel@This is a non-members world: @whi@Since you are a member we";
                } else {
                    rsi.message = "";
                }
            } else if (daysSinceRecovChange == 200) {
                rsi.message = "You have not yet set any password recovery questions.";
            } else {
                String s1;

                if (daysSinceRecovChange == 0) {
                    s1 = "Earlier today";
                } else if (daysSinceRecovChange == 1) {
                    s1 = "Yesterday";
                } else {
                    s1 = daysSinceRecovChange + " days ago";
                }

                rsi.message = s1 + " you changed your recovery questions";
            }
        }

        if (j == 653) {
            if (daysSinceRecovChange == 201) {
                if (membersInt == 1) {
                    rsi.message = "@whi@recommend you use a members world instead. You may use";
                } else {
                    rsi.message = "";
                }
            } else if (daysSinceRecovChange == 200) {
                rsi.message = "We strongly recommend you do so now to secure your account.";
            } else {
                rsi.message = "If you do not remember making this change then cancel it immediately";
            }
        }

        if (j == 654) {
            if (daysSinceRecovChange == 201) {
                if (membersInt == 1) {
                    rsi.message = "@whi@this world but member benefits are unavailable whilst here.";
                    return;
                } else {
                    rsi.message = "";
                    return;
                }
            }

            if (daysSinceRecovChange == 200) {
                rsi.message = "Do this from the 'account management' area on our front webpage";
                return;
            }

            rsi.message = "Do this from the 'account management' area on our front webpage";
        }
    }

    private void drawGameScreen() {
        if (getFullscreenInterfaceID() != -1 && (loadingStage == 2 || super.fullGameScreen != null)) {
            if (loadingStage == 2) {
                processInterfaceAnimation(anInt945, getFullscreenInterfaceID());
                if (openInterfaceID != -1) {
                    processInterfaceAnimation(anInt945, openInterfaceID);
                }
                anInt945 = 0;
                resetAllImageProducers();
                super.fullGameScreen.initDrawingArea();
                Rasterizer3D.lineOffsets = fullScreenTextureArray;
                TextDrawingArea.setAllPixelsToZero();
                welcomeScreenRaised = true;
                if (openInterfaceID != -1) {
                    RSInterface rsInterface_1 = RSInterface.interfaceCache[openInterfaceID];
                    if (rsInterface_1.width == 512 && rsInterface_1.height == 334 && rsInterface_1.type == 0) {
                        rsInterface_1.width = 765;
                        rsInterface_1.height = 503;
                    }
                    drawInterface(0, 0, rsInterface_1, 8);
                }
                RSInterface rsInterface = RSInterface.interfaceCache[getFullscreenInterfaceID()];
                if (rsInterface.width == 512 && rsInterface.height == 334 && rsInterface.type == 0) {
                    rsInterface.width = 765;
                    rsInterface.height = 503;
                }
                drawInterface(0, 0, rsInterface, 8);

                if (!menuOpen) {
                    processRightClick();
                    drawTooltip();
                } else {
                    drawMenu();
                }
            }
            drawCount++;
            super.fullGameScreen.drawGraphics(canvas.getGraphics(), 0, 0);
            return;
        } else {
            if (drawCount != 0) {
                resetImageProducers2();
            }
        }
        if (welcomeScreenRaised) {
            welcomeScreenRaised = false;
            if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                topFrame.drawGraphics(canvas.getGraphics(), 0, 0);
                leftFrame.drawGraphics(canvas.getGraphics(), 0, 4);

                rightFrame.drawGraphics(canvas.getGraphics(), 516, 4);
                rightFrame.drawGraphics(canvas.getGraphics(), 515, 4);
            }

            setInputTaken(true);
            tabAreaAltered = true;

            if (loadingStage != 2) {
                if (!resizing) {
                    gameScreenIP.drawGraphics(canvas.getGraphics(), gameScreenDrawX, gameScreenDrawY);
                }
                mapAreaIP.drawGraphics(canvas.getGraphics(), mapArea.getxPos(), mapArea.getyPos());
            }
        }

        drawTabArea();

        if (backDialogID == -1 && inputDialogState == 3) {
            int position = getGrandExchange().totalItemResults * 14 + 7;
            aClass9_1059.scrollPosition = getGrandExchange().itemResultScrollPos;
            if (super.mouseX > 478 && super.mouseX < 580 && super.mouseY > (clientHeight - 161)) {
                scrollInterface(494, 110, super.mouseX, super.mouseY - (clientHeight - 155), aClass9_1059, 0, false,
                        getGrandExchange().totalItemResults);
            }
            int scrollPosition = aClass9_1059.scrollPosition;
            if (scrollPosition < 0) {
                scrollPosition = 0;
            }
            if (scrollPosition > position - 110) {
                scrollPosition = position - 110;
            }
            if (getGrandExchange().itemResultScrollPos != scrollPosition) {
                getGrandExchange().itemResultScrollPos = scrollPosition;
                updateChatArea = true;
            }
        }
        if (backDialogID == -1 && inputDialogState != 3) {
            aClass9_1059.scrollPosition = anInt1211 - anInt1089 - 110;
            if (super.mouseX > chatArea.getxPos() + 478 && super.mouseX < chatArea.getxPos() + 580
                    && super.mouseY > chatArea.getyPos() + 4) {
                scrollInterface(494, 110, super.mouseX - chatArea.getxPos(), super.mouseY - chatArea.getyPos() - 10,
                        aClass9_1059, 0, false, anInt1211);
            }
            int i = anInt1211 - 110 - aClass9_1059.scrollPosition;
            if (i < 0) {
                i = 0;
            }
            if (i > anInt1211 - 110) {
                i = anInt1211 - 110;
            }
            if (anInt1089 != i) {
                anInt1089 = i;
                setInputTaken(true);
            }
        }
        if (backDialogID != -1) {
            boolean flag2 = processInterfaceAnimation(anInt945, backDialogID);
            if (flag2) {
                setInputTaken(true);
            }
        }

        if (atInventoryInterfaceType == 3) {
            setInputTaken(true);
        }

        if (activeInterfaceType == 3) {
            setInputTaken(true);
        }

        if (aString844 != null) {
            setInputTaken(true);
        }

        if (menuOpen && menuScreenArea == 2) {
            setInputTaken(true);
        }

        if (updateChatArea) {
            drawChatArea();
            drawConsoleArea();
            setInputTaken(false);
        }

        if (loadingStage == 2) {
            render();
        }

        if (loadingStage == 2) {
            if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                drawMinimap();
                if (mapArea.isVisible()) {
                    mapAreaIP.drawGraphics(canvas.getGraphics(), mapArea.getxPos(), mapArea.getyPos());
                }
            }
        }

        if (anInt1054 != -1) {
            tabAreaAltered = true;
        }

        if (tabAreaAltered) {
            if (anInt1054 != -1 && anInt1054 == tabID) {
                anInt1054 = -1;
                getOut().putOpcode(120);
                getOut().putByte(tabID);
            }

            tabAreaAltered = false;
            aProducingGraphicsBuffer_1125.initDrawingArea();
            gameScreenIP.initDrawingArea();
        }

        if (menuOpen) {
            drawMenu();
        }

        anInt945 = 0;
    }

    private void drawHeadIcon() {
        if (hintIconDrawType != 2) {
            return;
        }

        calcEntityScreenPos((hintIconX - regionBaseX << 7) + hintIconLocationArrowRelX, hintIconZ * 2, (hintIconY - regionBaseY << 7) + hintIconLocationArrowRelY);

        if (spriteDrawX > -1 && loopCycle % 20 < 10) {
            headIconsHint[0].drawSprite(spriteDrawX - 12, spriteDrawY - 28);
        }
    }

    private void drawHoverBox(int xPos, int yPos, String text) {
        String[] results = text.split("\n");
        int height = results.length * 16 + 6;
        int width = boldText.getTextWidth(results[0]) + 6;

        for (int i = 1; i < results.length; i++) {
            if (width <= boldText.getTextWidth(results[i]) + 6) {
                width = boldText.getTextWidth(results[i]) + 6;
            }
        }

        TextDrawingArea.drawPixels(height, yPos, xPos, 0x25211B, width);
        TextDrawingArea.fillPixels(xPos, width, height, 0, yPos);
        yPos += 14;

        for (String result : results) {
            normalText.drawRegularText(false, xPos + 3, 0, "@vag@" + result, yPos);
            yPos += 16;
        }
    }

    private void drawProgressBar(int xPos, int yPos, int width, int height, int currentPercent, int firstColor, int secondColor, int strokeWidth) {
        TextDrawingArea.drawAlphaFilledPixels(xPos, yPos, width, height, firstColor, 255);
        TextDrawingArea.drawAlphaFilledPixels(xPos, yPos, (int) (width * (currentPercent / 100.0f)), height, secondColor, 255);
        TextDrawingArea.drawStroke(xPos - strokeWidth, yPos, width + strokeWidth, height, 0x141414, strokeWidth);
    }

    public void drawInterface(int scrollOffset, int interfaceX, RSInterface class9, int interfaceY) {
        if (class9 == null) {
            return;
        }

        if (class9.children == null || class9.type != 0 || !class9.isVisible()) {
            return;
        }

        if (class9.interfaceShown && anInt1026 != class9.id && anInt1048 != class9.id && anInt1039 != class9.id) {
            return;
        }

        int i1 = TextDrawingArea.clipLeft;
        int j1 = TextDrawingArea.clipTop;
        int k1 = TextDrawingArea.clipRight;
        int l1 = TextDrawingArea.clipBottom;

        TextDrawingArea.setBounds(interfaceX, interfaceY, interfaceX + class9.width, interfaceY + class9.height);

        int i2 = class9.children.length;
        boolean fixed = GameFrame.getScreenMode() == ScreenMode.FIXED;
        for (int j2 = 0; j2 < i2; j2++) {
            int childX = (class9.childX[j2] + interfaceX) - (class9.sideScroll ? scrollOffset : 0);
            int childY = (class9.childY[j2] + interfaceY) - (class9.sideScroll ? 0 : scrollOffset);
            RSInterface child = RSInterface.interfaceCache[class9.children[j2]];
            if (child == null || child.hideWidget || !child.active)
                continue;
            // System.out.println(child.type);
            childX += child.xOffset;
            childY += child.yOffset;

            if (child.contentType > 0) {
                drawFriendsListOrWelcomeScreen(child);
            }

            for (int m5 = 0; m5 < IDs.length; m5++) {
                if (child.id == IDs[m5] + 1) {
                    if (m5 > 61) {
                        drawBlackBox(childX + 1, childY);
                    } else {
                        drawBlackBox(childX, childY + 1);
                    }
                }
            }

            for (int element : runeChildren) {
                if (child.id == element) {
                    child.modelZoom = 775;
                }
            }

            if (child.id == 1194 || child.id == 12856) // Removes black box when not hovering in
            // spellbooks
            {
                continue;
            }
            if (child.type == 282) {
                if (child.itemsToDraw != null) {
                    for (int i = 0; i < child.itemsToDraw.length; i++) {
                        Sprite invSprite = ItemDef.getSprite(child.itemsToDraw[i], 0, 0);
                        Sprite.getCutted(invSprite, invSprite.myWidth, cutAmount).drawSprite(childX + 30 + i * 50, childY + 3);
                    }

                    if (cutCounter % 3 == 0) {
                        cutAmount++;
                    }
                    cutCounter++;
                    System.out.println("Cut amount: " + cutAmount);
                    if (cutAmount >= 40) {
                        child.itemsToDraw = null;
                        getOut().putOpcode(199);
                        getOut().putByte(1);
                        cutAmount = 0;// TODO rest.
                    }

                }
            }

            if (child.type == 287) {
                drawProgressBar(childX, childY, child.width, child.height, child.progress, child.firstColor, child.secondColor, 1);
                // System.out.println("Drawing: " + this.hueShift(particleTick).getRGB());
            }
            if (child.type == 150) {
                child.wheel.render(childX, childY);
            }

            if (child.type == 100 && child.unrevealedSprite != null) {

                child.unrevealedSprite.drawSprite(childX + 2, childY + 2);
                // System.out.println("Unrevealed sprite drawn");
            }

            if (child.type == 831) {
                TextDrawingArea.drawAlphaFilledPixels(childX, childY, child.width, child.height, child.enabledColor, child.customOpacity);
            }
            if (child.type == 0) {
                // System.out.println(child.id);

                if (child.sideScroll) {
                    if (child.scrollPosition > child.scrollMax - child.width) {
                        child.scrollPosition = child.scrollMax - child.width;
                    }
                } else {
                    if (child.scrollPosition > child.scrollMax - child.height) {
                        child.scrollPosition = child.scrollMax - child.height;
                    }
                }

                if (child.scrollPosition < 0) {
                    child.scrollPosition = 0;
                }

                drawInterface(child.scrollPosition, childX, child, childY);

                if (child.sideScroll) {
                    if (child.scrollMax > child.width) {
                        drawScrollbarBottom(child.width, child.scrollPosition, childY + child.height, childX, child.scrollMax, false, false);
                    }
                } else {
                    if (child.scrollMax > child.height) {
                        drawScrollbar(child.height, child.scrollPosition, childY, childX + child.width, child.scrollMax, false, false);
                    }
                }
            } else if (child.type != 1) {
                if (child.type == 1319) {
                    TextDrawingArea.drawPixels(childX + 335, childY + 52, childX + 51, 0, 411);
                    miniMapRegions.drawSprite(childX, childY);

                    int mapX = childX + 40 + myPlayer.x / 32;
                    int mapY = childY + 458 - myPlayer.y / 32;
                    spritesMap.get(1421).drawSprite(mapX - 10, mapY - 10);
                }
                if (child.type == 2) {
                    int i3 = 0;
                    for (int l3 = 0; l3 < child.height; l3++) {
                        for (int l4 = 0; l4 < child.width; l4++) {
                            int k5 = childX + l4 * (32 + child.invSpritePadX);
                            int j6 = childY + l3 * (32 + child.invSpritePadY);

                            if (i3 < 20) {
                                k5 += child.spritesX[i3];
                                j6 += child.spritesY[i3];
                            }

                            if (i3 < child.inv.length && child.inv[i3] > 0) {
                                if ((child.id >= 110101 && child.id <= 110221)) { /*
                                 * Shop interface hardcode
                                 */
                                    if (stock == null) {
                                        stock = new Sprite("/ok/stock");
                                    }
                                    stock.drawSprite(k5 - 8, j6 - 4);
                                }
                                int k6 = 0;
                                int j7 = 0;
                                int j9 = child.inv[i3] - 1;

                                if (k5 > TextDrawingArea.clipLeft - 32 && k5 < TextDrawingArea.clipRight && j6 > TextDrawingArea.clipTop - 32 && j6 < TextDrawingArea.clipBottom || activeInterfaceType != 0 && selectedInventorySlot == i3) {
                                    int l9 = 0;
                                    if (itemSelected == 1 && anInt1283 == i3 && anInt1284 == child.id) {
                                        l9 = 0xffffff;
                                    }

                                    int itemOpacity = 256;

                                    if (openInterfaceID == 5292) {
                                        if (child.invStackSizes[i3] == 0) {
                                            itemOpacity = 110;
                                        }
                                    }
                                    if (child.id >= 131251 && child.id <= 131431)
                                        if (child.invStackSizes[i3] == 0)
                                            itemOpacity = 110;

                                    Sprite selectedItem;

                                    if (child.id == 30375 && itemCollected(child.inv[i3] - 1)) {
                                        child.opacity = 50;
                                    }

                                    if (child.id == 25412) {
                                        selectedItem = ItemDef.getSizedSprite(j9, child.invStackSizes[i3], l9, 64, 64);
                                    } else {
                                        if (child.id >= 28500 && child.id <= 29000)
                                            selectedItem = ItemDef.getSizedSprite(j9, child.invStackSizes[i3], l9, 28, 28);
                                        else
                                            selectedItem = ItemDef.getSprite(j9, child.invStackSizes[i3], l9);
                                    }
                                    if (selectedItem != null) {
                                        if (activeInterfaceType != 0 && selectedInventorySlot == i3 && modifiedWidgetId == child.id) {
                                            k6 = super.mouseX - anInt1087;
                                            j7 = super.mouseY - anInt1088;

                                            if (k6 < 5 && k6 > -5) {
                                                k6 = 0;
                                            }

                                            if (j7 < 5 && j7 > -5) {
                                                j7 = 0;
                                            }

                                            if (anInt989 < 10) {
                                                k6 = 0;
                                                j7 = 0;
                                            }

                                            selectedItem.drawSprite1(k5 + k6, j6 + j7);
                                            int yy = GameFrame.getScreenMode() == ScreenMode.FIXED ? 40 : (getScreenHeight() - 503) / 2;
                                            // int y = GameFrame.isFixed() ? 40 :(getScreenHeight() - 503) / 2;

                                            if (openInterfaceID == 5292) {
                                                if (super.mouseY >= yy && super.mouseY <= yy + 24) {
                                                    bankItemDragSprite = selectedItem;
                                                    bankItemDragSpriteX = super.mouseX - 20;
                                                    bankItemDragSpriteY = super.mouseY - 20;
                                                } else {
                                                    if (bankItemDragSprite != null) {
                                                        bankItemDragSprite = null;
                                                    }
                                                }
                                            }
                                            if (j6 + j7 < TextDrawingArea.clipTop && class9.scrollPosition > 0) {
                                                int i10 = anInt945 * (TextDrawingArea.clipTop - j6 - j7) / 3;

                                                if (i10 > anInt945 * 10) {
                                                    i10 = anInt945 * 10;
                                                }

                                                if (i10 > class9.scrollPosition) {
                                                    i10 = class9.scrollPosition;
                                                }

                                                class9.scrollPosition -= i10;
                                                anInt1088 += i10;
                                            }

                                            if (j6 + j7 + 32 > TextDrawingArea.clipBottom && class9.scrollPosition < class9.scrollMax - class9.height) {
                                                int j10 = anInt945 * (j6 + j7 + 32 - TextDrawingArea.clipBottom) / 3;

                                                if (j10 > anInt945 * 10) {
                                                    j10 = anInt945 * 10;
                                                }

                                                if (j10 > class9.scrollMax - class9.height - class9.scrollPosition) {
                                                    j10 = class9.scrollMax - class9.height - class9.scrollPosition;
                                                }

                                                class9.scrollPosition += j10;
                                                anInt1088 -= j10;
                                            }
                                        } else if (atInventoryInterfaceType != 0 && atInventoryIndex == i3 && atInventoryInterface == child.id) {
                                            selectedItem.drawSprite1(k5, j6);
                                        } else {
                                            if(openInterfaceID == 40428 && child.invStackSizes[i3] <= 0) {
                                                selectedItem.drawSprite1(k5, j6);
                                            } else {
                                                if (child.id == 30375 && child.invStackSizes[i3] == 0) {
                                                    selectedItem.drawSpriteWithOpacity(k5, j6, 75);
                                                } else {
                                                    if (itemOpacity == 256) {
                                                        selectedItem.drawSprite(k5, j6);
                                                    } else {
                                                        selectedItem.drawSpriteWithOpacity(k5, j6, itemOpacity);
                                                    }
                                                }
                                            }
                                        }

                                        if (selectedItem.maxWidth == 33 || child.invStackSizes[i3] > 1 || (openInterfaceID == 5292 && child.invStackSizes[i3] == 0)) {
                                            int k10 = child.invStackSizes[i3];
                                            if ((openInterfaceID == 5292 && k10 == 0) || (child.id == 30375 && k10 > 1 || !child.hideStackSize)) {
                                                if (openInterfaceID == 5292 && k10 == 0) { // Placeholders
                                                    newSmallFont.drawBasicString("<trans=170>" + intToKOrMil(k10) + "", k5 + k6, j6 + 9 + j7, 0xFFFF00, 0, false);
                                                } else if (k10 >= 1500000000 && child.drawInfinity) {
                                                    spritesMap.get(780).drawSprite(k5, j6);
                                                } else {
                                                    smallText.method385(0, intToKOrMil(k10), j6 + 10 + j7, k5 + 1 + k6);
                                                    if (k10 > 99999 && k10 < 10000000) {
                                                        smallText.method385(0xFFFFFF, intToKOrMil(k10), j6 + 9 + j7, k5 + k6);
                                                    } else if (k10 > 9999999) {
                                                        smallText.method385(0x00ff80, intToKOrMil(k10), j6 + 9 + j7, k5 + k6);
                                                    } else {
                                                        smallText.method385(0xFFFF00, intToKOrMil(k10), j6 + 9 + j7, k5 + k6);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else if (child.sprites != null && i3 < 20) {
                                Sprite class30_sub2_sub1_sub1_1 = child.sprites[i3];

                                if (class30_sub2_sub1_sub1_1 != null) {
                                    class30_sub2_sub1_sub1_1.drawSprite(k5, j6);
                                }
                            }

                            i3++;
                        }
                    }
                } else if (child.type == 73) {
                    boolean vertical = child.verticalBar;
                    boolean active = child.fillBarActive;
                    int percentageDimension = vertical ? child.height : child.width;
                    int completed = child.percentageCompleted;
                    int total = child.percentageTotal;
                    int complete = (completed * percentageDimension) / total;
                    int color;
                    if (active) {
                        color = 0xe5e800;
                    } else {
                        color = child.colorFull;
                    }
                    if (vertical) {
                        TextDrawingArea.transparentBox(complete, childY + child.height - complete, childX, color, child.width, ((completed*1.5) > 100 ? 100 : (int)(completed*1.5)));
                    } else {
                        TextDrawingArea.transparentBox(child.height, childY, childX, color, child.width, ((completed*1.5) > 100 ? 100 : (int)(completed*1.5)));
                    }

                } else if (child.type == 72) {
//                    System.out.println("Drawing percentage bar");
                    TextDrawingArea textDrawingArea = child.textDrawingAreas;
                    RSFontSystem font = newSmallFont;
                    int size = 2;
                    if (textDrawingArea == smallText) {
                        font = newSmallFont;
                        size = 3;
                    }
                    int completed = child.percentageCompleted;
//                    System.out.println("Completed amount: "+completed);
                    int total = child.percentageTotal;
                    int complete = (completed * child.percentageDimension) / total;
                    int percentage = (completed * 100) / total;
                    boolean verticle = child.verticalBar;
                    TextDrawingArea.drawFilledPixels(childX, childY, child.percentageDimension, child.height,
                            child.colorEmpty);
                    TextDrawingArea.drawFilledPixels(childX, childY, complete, child.height, child.colorFull);
                    TextDrawingArea.fillPixels(childX, child.percentageDimension, child.height, 0x000000, childY);

                    if (verticle) {
                        int xWidth = cacheSprite[child.colorEmpty].myWidth;
                        font.drawCenteredString(percentage + "%", childX + (xWidth / 2),
                                childY + 20 + size + (child.percentageDimension) + 6, 0xffffff, 0);
                    } else {
                        font.drawCenteredString(percentage + "%", childX + (child.percentageDimension / 2) + 3,
                                childY + (child.height / 2) + 2 + size, 0xffffff, 0);
                    }

                } else if (child.type == 3) {
                    boolean flag = false;

                    if (anInt1039 == child.id || anInt1048 == child.id || anInt1026 == child.id || child == childHovered) {
                        flag = true;
                    }

                    int j3;

                    if (interfaceIsSelected(child)) {
                        j3 = child.enabledColor;

                        if (flag && child.enabledMouseOverColor != 0) {
                            j3 = child.enabledMouseOverColor;
                        }
                    } else {
                        j3 = child.disabledColor;

                        if (flag && child.disabledMouseOverColor != 0) {
                            j3 = child.disabledMouseOverColor;
                        }
                    }

                    if (child.opacity == 0) {
                        if (child.filled) {
                            TextDrawingArea.drawPixels(child.height, childY, childX, j3, child.width);
                        } else {
                            TextDrawingArea.fillPixels(childX, child.width, child.height, j3, childY);
                        }
                    } else if (child.filled) {
                        TextDrawingArea.method335(j3, childY, child.width, child.height, 256 - (child.opacity & 0xff), childX);
                    } else {
                        TextDrawingArea.method338(childY, child.height, 256 - (child.opacity & 0xff), j3, child.width, childX);
                    }
                } else if (child.type == RSInterface.TYPE_RGB_PICKER) {
                    RGBWidgetColorPicker colorPicker = child.rgbPicker;
                    colorPicker.render(childX, childY);
                } else if (child.type == RSInterface.TYPE_SLIDER) {
                    WidgetSlider widgetSlider = child.widgetSlider;
                    widgetSlider.render(childX, childY);
                } else if (child.type == RSInterface.TYPE_DROPDOWN) {

                    DropdownMenu d = child.dropdown;

                    int bgColour = child.dropdownColours[2];
                    int fontColour = 0xfe971e;
                    int downArrow = 5553;

                    if (child.hovered || d.isOpen()) {
                        downArrow = 5553;
                        fontColour = 0xffb83f;
                        bgColour = child.dropdownColours[3];
                    }

                    Rasterizer3D.drawPixels(20, childY, childX, child.dropdownColours[0], d.getWidth());
                    Rasterizer3D.drawPixels(18, childY + 1, childX + 1, child.dropdownColours[1], d.getWidth() - 2);
                    Rasterizer3D.drawPixels(16, childY + 2, childX + 2, bgColour, d.getWidth() - 4);

                    int xOffset = child.centerText ? 3 : 16;
                    newSmallFont.drawCenteredString(d.getSelected(), childX + (d.getWidth() - xOffset) / 2, childY + 14,
                            fontColour, 0);

                    if (d.isOpen()) {
                        // Up arrow
                        Client.spritesMap.get(5552)
                                .drawSprite(childX + d.getWidth() - 18, childY + 2);

                        Rasterizer3D.drawPixels(d.getHeight(), childY + 19, childX, child.dropdownColours[0],
                                d.getWidth());
                        Rasterizer3D.drawPixels(d.getHeight() - 2, childY + 20, childX + 1, child.dropdownColours[1],
                                d.getWidth() - 2);
                        Rasterizer3D.drawPixels(d.getHeight() - 4, childY + 21, childX + 2, child.dropdownColours[3],
                                d.getWidth() - 4);

                        int yy = 2;
                        for (int i = 0; i < d.getOptions().length; i++) {
                            if (child.dropdownHover == i) {
                                if (child.id == 28102) {
                                    Rasterizer3D.transparentBox(childX + 2, childY + 19 + yy, d.getWidth() - 4, 13,
                                            0xd0914d, 80);
                                } else {
                                    Rasterizer3D.drawPixels(13, childY + 19 + yy, childX + 2, child.dropdownColours[4],
                                            d.getWidth() - 4);
                                }
                                newSmallFont.drawCenteredString(d.getOptions()[i], childX + (d.getWidth() - xOffset) / 2,
                                        childY + 29 + yy, 0xffb83f, 0);
                            } else {
                                Rasterizer3D.drawPixels(13, childY + 19 + yy, childX + 2, child.dropdownColours[3],
                                        d.getWidth() - 4);
                                newSmallFont.drawCenteredString(d.getOptions()[i], childX + (d.getWidth() - xOffset) / 2,
                                        childY + 29 + yy, 0xfe971e, 0);
                            }
                            yy += 14;
                        }

                        drawScrollbar(d.getHeight() - 4, child.scrollPosition, childY + 21, childX + d.getWidth() - 18, d.getHeight() - 5, false, false);
                    } else {
                        Client.spritesMap.get(downArrow)
                                .drawSprite(childX + d.getWidth() - 18, childY + 2);
                    }
                } else if (child.type == RSInterface.TYPE_KEYBINDS_DROPDOWN) {

                    DropdownMenu d = child.dropdown;

                    // If dropdown inverted, don't draw following 2 menus
                    if (dropdownInversionFlag > 0) {
                        dropdownInversionFlag--;
                        continue;
                    }

                    Rasterizer3D.drawPixels(18, childY + 1, childX + 1, 0x544834, d.getWidth() - 2);
                    Rasterizer3D.drawPixels(16, childY + 2, childX + 2, 0x2e281d, d.getWidth() - 4);
                    newRegularFont.drawBasicString(d.getSelected(), childX + 7, childY + 15, 0xff8a1f, 0);
                    spritesMap.get(5552).drawSprite(childX + d.getWidth() - 18, childY + 2); // Arrow

                    if (d.isOpen()) {

                        RSInterface.interfaceCache[child.id - 1].active = true; // Alter stone colour

                        int yPos = childY + 18;

                        // Dropdown inversion for lower stones
                        if (child.inverted) {
                            yPos = childY - d.getHeight() - 10;
                            dropdownInversionFlag = 2;
                        }

                        Rasterizer3D.drawPixels(d.getHeight() + 12, yPos, childX + 1, 0x544834, d.getWidth() - 2);
                        Rasterizer3D.drawPixels(d.getHeight() + 10, yPos + 1, childX + 2, 0x2e281d, d.getWidth() - 4);

                        int yy = 2;
                        int xx = 0;
                        int bb = d.getWidth() / 2;

                        for (int i = 0; i < d.getOptions().length; i++) {

                            int fontColour = 0xff981f;
                            if (child.dropdownHover == i) {
                                fontColour = 0xffffff;
                            }

                            if (xx == 0) {
                                newRegularFont.drawBasicString(d.getOptions()[i], childX + 5, yPos + 14 + yy, fontColour, 0x2e281d);
                                xx = 1;

                            } else {
                                newRegularFont.drawBasicString(d.getOptions()[i], childX + 5 + bb, yPos + 14 + yy, fontColour, 0x2e281d);
                                xx = 0;
                                yy += 15;
                            }
                        }
                    } else {
                        RSInterface.interfaceCache[child.id - 1].active = false;
                    }
                } else if (child.type == 4 || child.type == 17) {
                    TextDrawingArea textDrawingArea = child.textDrawingAreas;
                    String s = child.message;
                    if (child.type == 17) {
                        s = Interfaces.getWrappedText(child.textDrawingAreas, child.message, child.width);
                    } else {
                        s = child.message;
                    }
                    int xOffset = 0;
                    int imageDraw = 0;
                    final String INITIAL_MESSAGE = s;
                    if (s.contains("<img=")) {
                        int prefix = s.indexOf("<img=");
                        int suffix = s.indexOf(">");
                        try {
                            imageDraw = Integer.parseInt(s.substring(prefix + 5, suffix));
                            s = s.replaceAll(s.substring(prefix + 5, suffix), "");
                            s = s.replaceAll("</img>", "");
                            s = s.replaceAll("<img=>", "");
                        } catch (NumberFormatException | IllegalStateException nfe) {
                            // System.out.println("Unable to draw player crown on interface. Unable to read
                            // rights.");
                            s = INITIAL_MESSAGE;
                        } // System.out.println("Unable to draw player crown on interface, rights too low
                        // or high.");

                        if (suffix > prefix) {
                            xOffset += 14;
                        }
                    }
                    boolean flag1 = false;
                    if (anInt1039 == child.id || anInt1048 == child.id || anInt1026 == child.id || childHovered == child) {
                        flag1 = true;
                    }
                    int messageColor;
                    if (interfaceIsSelected(child)) {
                        messageColor = child.enabledColor;
                        if (flag1 && child.enabledMouseOverColor != 0) {
                            messageColor = child.enabledMouseOverColor;
                        }
                        if (child.enabledMessage.length() > 0) {
                            s = child.enabledMessage;
                        }
                    } else {
                        if (child.drawSecondary) {
                            messageColor = child.enabledColor;
                        } else {
                            messageColor = child.disabledColor;
                        }
                        if (flag1 && child.disabledMouseOverColor != 0) {
                            messageColor = child.disabledMouseOverColor;
                        }
                    }
                    if (child.atActionType == 6 && aBoolean1149) {
                        s = "Please wait...";
                        messageColor = child.disabledColor;
                    }
                    if (TextDrawingArea.width == 516) {
                        if (messageColor == 0xffff00) {
                            messageColor = 255;
                        }
                        if (messageColor == 49152) {
                            messageColor = 0xffffff;
                        }
                    }


                    if (child.id >= 35613 && child.id < 35813) {
                        if (RSInterface.interfaceCache[3900].inv[child.id - 35613] > 0) {
                            String[] data = s.split(",");
                            int currency = 0;
                            if (data.length > 1) {
                                currency = Integer.parseInt(data[1]);
                            }
                            if (currency != -1) {
                                if (currencyImage[currency] == null) {
                                    currencyImage[currency] = new Sprite("currency" + currency);
                                }
                                currencyImage[currency].drawSprite(childX - 19, childY);
                                int value = Integer.parseInt(data[0]);
                                if (value <= 0) {
                                    smallText.drawRegularText(true, childX - 5, 0xffff00, "FREE", childY + 10);
                                } else if (value < 100000) {
                                    smallText.drawRegularText(true, childX - 5, 0xffff00, value + "", childY + 10);
                                } else if (value < 10000000) {
                                    smallText.drawRegularText(true, childX - 5, 0xffff00, (value / 1000) + "", childY + 10);
                                } else {
                                    smallText.drawRegularText(true, childX - 5, 0xffff00, (value / 1000000) + "", childY + 10);
                                }

                            }
                        }
                        continue;
                    }
                    if (child.parentID == 1151 || child.parentID == 12855) {
                        switch (messageColor) {
                            case 16773120:
                                messageColor = 0xFE981F;
                                break;
                            case 7040819:
                                messageColor = 0xAF6A1A;
                                break;
                        }
                    }
                    for (int childYLoop = childY + textDrawingArea.anInt1497; s.length() > 0; childYLoop += textDrawingArea.anInt1497) {
                        if (s.contains("%")) {
                            do {
                                int k7 = s.indexOf("%1");
                                if (k7 == -1) {
                                    break;
                                }
                                if (child.id < 86000 || child.id > 87000 && child.id != 13921 && child.id != 13922 && child.id != 12171 && child.id != 12172) {
                                    s = s.substring(0, k7) + methodR(extractInterfaceValues(child, 0)) + s.substring(k7 + 2);
                                } else {
                                    s = s.substring(0, k7) + interfaceIntToString(extractInterfaceValues(child, 0)) + s.substring(k7 + 2);
                                }
                            } while (true);
                            do {
                                int l7 = s.indexOf("%2");
                                if (l7 == -1) {
                                    break;
                                }
                                s = s.substring(0, l7) + interfaceIntToString(extractInterfaceValues(child, 1)) + s.substring(l7 + 2);
                            } while (true);
                            do {
                                int i8 = s.indexOf("%3");
                                if (i8 == -1) {
                                    break;
                                }
                                s = s.substring(0, i8) + interfaceIntToString(extractInterfaceValues(child, 2)) + s.substring(i8 + 2);
                            } while (true);
                            do {
                                int j8 = s.indexOf("%4");
                                if (j8 == -1) {
                                    break;
                                }
                                s = s.substring(0, j8) + interfaceIntToString(extractInterfaceValues(child, 3)) + s.substring(j8 + 2);
                            } while (true);
                            do {
                                int k8 = s.indexOf("%5");
                                if (k8 == -1) {
                                    break;
                                }
                                s = s.substring(0, k8) + interfaceIntToString(extractInterfaceValues(child, 4)) + s.substring(k8 + 2);
                            } while (true);
                        }
                        int l8 = s.indexOf("\\n");
                        String s1;
                        if (l8 != -1) {
                            s1 = s.substring(0, l8);
                            s = s.substring(l8 + 2);
                        } else {
                            s1 = s;
                            s = "";
                        }
                        if (imageDraw > 0 && xOffset > 0) {
                            int drawImageY = childY + 14;
                            if (imageDraw >= 841 && imageDraw <= 849) { // Clan chat images
                                xOffset -= 5;
                                drawImageY -= 7;
                            }
                            newRegularFont.drawBasicString("<img=" + imageDraw + ">", childX, drawImageY, true);
                        }
                        if (child.centerText) {
                            textDrawingArea.drawCenteredText(messageColor, childX + child.width / 2 + xOffset, s1, childYLoop, child.textShadow);
                        } else if (child.rightText){
                            textDrawingArea.drawRightAlignedString(s1, childX + xOffset, childYLoop, messageColor);
                        } else {
                            textDrawingArea.drawRegularText(child.textShadow, childX + xOffset, messageColor, s1, childYLoop);
                        }
                    }
                } else if (child.type == 42) {

                    boolean flag1 = false;
                    if (anInt1039 == child.id || anInt1048 == child.id || anInt1026 == child.id || childHovered == child) {
                        flag1 = true;
                    }
                    if (!flag1) {
                        child.disabledSprite.drawAdvancedSprite(childX, childY);
                    } else {
                        child.enabledSprite.drawAdvancedSprite(childX, childY);
                    }


                    if (child.centerText) {
                        child.textDrawingAreas.drawCenteredText(child.enabledColor, childX + child.messageOffsetX, child.message, childY + child.messageOffsetY, true);
                    } else {
                        child.textDrawingAreas.drawText(child.enabledColor, child.message, childX + 5 + child.messageOffsetX, childY + child.messageOffsetY);
                    }
                }
                if (child.type == 130) {
                    RSInterface mainSprite = RSInterface.interfaceCache[class9.children[0]];
                    TextDrawingArea.drawAlphaFilledPixels(childX, childY, mainSprite.width, mainSprite.height + 2, child.layerColor, child.layerTransparency);
                }

                if (child.type == 5) {
                    Sprite sprite;

                    if (child.itemSpriteId > 0 && child.disabledSprite == null && child.enabledSprite == null) {
                        child.disabledSprite = ItemDef.getSprite(child.itemSpriteId, 1, (child.itemSpriteZoom == -1) ? 0 : -1, child.itemSpriteZoom, false);
                        child.enabledSprite = ItemDef.getSprite(child.itemSpriteId, 1, (child.itemSpriteZoom == -1) ? 0 : -1, child.itemSpriteZoom, false);

                    }

                    if (interfaceIsSelected(child)) {
                        sprite = child.enabledSprite;
                    } else {
                        sprite = child.disabledSprite;
                    }

                    if (child.id == 1164 || child.id == 1167 || child.id == 1170 || child.id == 1174 || child.id == 1540 || child.id == 1541 || child.id == 7455 || child.id == 18470 || child.id == 13035 || child.id == 13045 || child.id == 13053 || child.id == 13061 || child.id == 13069 || child.id == 13079 || child.id == 30064 || child.id == 30075 || child.id == 30083 || child.id == 30106 || child.id == 30114 || child.id == 30106 || child.id == 30170 || child.id == 13087 || child.id == 30162 || child.id == 13095) {
                        sprite = child.enabledSprite;
                    }

                    if (child.summonReq > 0 && child.disabledSprite != null && child.enabledSprite != null) {
                        child.greyScale = (child.summonReq > maxStats[23]);
                        if (child.greyScale) {
                            child.disabledSprite.greyScale();
                            sprite = child.disabledSprite;
                        } else {
                            sprite = child.enabledSprite;
                        }
                    }

                    if (spellSelected == 1 && child.id == spellID && spellID != 0 && sprite != null) {
                        sprite.drawSprite(childX, childY, 0xffffff);
                    } else {
                        if (sprite != null) {
                            if (child.advancedSprite) {
                                sprite.drawAdvancedSprite(childX, childY);
                            } else {
                                sprite.drawSprite(childX, childY);
                            }
                        }
                    }
                    if (autoCast && child.id == autocastId) {
                        spritesMap.get(497)
                                .drawSprite(childX - 3, childY - 3);//autocast enabled box
                    }
                } else if (child.type == 553) {//hitsplat melee
                    Sprite sprite = null;
                    sprite = child.disabledSprite;
                    sprite.drawHoverSprite(childX, childY, child.enabledSprite);
                } else if (child.type == 554) {//hitsplat range

                    ProgressBar inter = (ProgressBar) child;
                    int displayColor = inter.colorTypes[inter.progressBarState];
                    int defaultBarColor = inter.defaultColorTypes[inter.defaultBarState];

                    if (inter.drawHorizontal) {
                        int drawingWidth = inter.progressBarPercentage * inter.width / 100;
                        TextDrawingArea.drawPixels(inter.height, childY, childX, defaultBarColor, inter.width);
                        TextDrawingArea.drawPixels(inter.height, childY, childX, displayColor, drawingWidth);
                        TextDrawingArea.fillPixels(childX, inter.width, inter.height, 0, childY);

                        // TextDrawingArea.fillRect(childX, childY, inter.width, inter.height, defaultBarColor);
                        //  TextDrawingArea.fillRect(childX, childY, drawingWidth, inter.height, displayColor);
                        //  TextDrawingArea.drawRect(childX, childY, inter.width, inter.height, 0);
                    } else {
                        int drawingWidth = inter.progressBarPercentage;
                        RSGraphics.fillRect(childX, childY, inter.width, inter.height, defaultBarColor);
                        RSGraphics.fillRect(childX, childY, inter.width, drawingWidth, displayColor);
                        RSGraphics.drawRect(childX, childY, inter.width, inter.height, 0);
                    }
                } else if (child.type == RSInterface.TYPE_MODEL) {

                    int k3 = Rasterizer3D.textureInt1;
                    int j4 = Rasterizer3D.textureInt2;
                    Rasterizer3D.textureInt1 = childX + child.width / 2;
                    Rasterizer3D.textureInt2 = childY + child.height / 2;
                    int i5 = Rasterizer3D.SINE[child.modelRotationY] * child.modelZoom >> 16;
                    int l5 = Rasterizer3D.COSINE[child.modelRotationY] * child.modelZoom >> 16;
                    boolean flag2 = interfaceIsSelected(child);
                    int i7;
                    if (flag2) {
                        i7 = child.enabledAnimationId;
                    } else {
                        i7 = child.disabledAnimationId;
                    }
                    Model model;
                    if (i7 == -1) {
                        model = child.method209(-1, -1, flag2);
                    } else {
                        AnimationDefinition animation = AnimationDefinition.cache[i7];
                        model = child.method209(animation.frameIDs2[child.anInt246], animation.frameIDs[child.anInt246], flag2);
                    }
                    if (model != null) {
                        Rasterizer3D.clearDepthBuffer();
                        model.renderSingle(child.modelRotationX, 0, child.modelRotationY, 0, i5, l5);
                    }
                    Rasterizer3D.textureInt1 = k3;
                    Rasterizer3D.textureInt2 = j4;
                } else if (child.type == 42) {
                    if (child.widgetActive) {
                        if (mouseX >= childX && mouseY >= childY && mouseX < childX + child.width && mouseY < childY + child.height) {
                            child.enabledSprite.drawSprite(childX, childY);
                        } else {
                            child.disabledSprite.drawSprite(childX, childY);
                        }
                        if (child.centerText)
                        // child.textDrawingAreas.drawText(colour, txt, x, y);
                        {
                            child.textDrawingAreas.drawCenteredText(child.disabledColor, childX + child.messageOffsetX, child.message, childY + child.messageOffsetY, true);
                        } else {
                            child.textDrawingAreas.drawText(child.disabledColor, child.message, childX + 5 + child.messageOffsetX, childY + child.messageOffsetY);
                        }
                    }
                } else if (child.type == 7) {
                    // test 1 by 1 if u want
                    TextDrawingArea textDrawingArea_1 = child.textDrawingAreas;
                    int k4 = 0;
                    for (int j5 = 0; j5 < child.height; j5++) {
                        for (int i6 = 0; i6 < child.width; i6++) {
                            if (child.inv[k4] > 0) {
                                ItemDef itemDef = ItemDef.get(child.inv[k4] - 1);
                                String s2 = itemDef.name;
                                if (itemDef.stackable || child.invStackSizes[k4] != 1) {
                                    s2 = s2 + " x" + intToKOrMilLongName(child.invStackSizes[k4]);
                                }
                                int i9 = childX + i6 * (115 + child.invSpritePadX);
                                int k9 = childY + j5 * (12 + child.invSpritePadY);
                                if (child.centerText) {
                                    textDrawingArea_1.drawCenteredText(child.disabledColor, i9 + child.width / 2, s2, k9, child.textShadow);
                                } else {
                                    textDrawingArea_1.drawRegularText(child.textShadow, i9, child.disabledColor, s2, k9);
                                }
                            }
                            k4++;
                        }
                    }
                } else if (child.type == 77) {
                    if (child.inputToggled) {
                        TextDrawingArea.drawPixels(child.height, childY, childX, -5331456, child.width);
                    } else {
                        TextDrawingArea.drawPixels(child.height, childY, childX, -14738666, child.width);
                    }

                    TextDrawingArea.drawPixels(child.height - 2, childY + 1, childX + 1, -13554910, child.width - 2);
                    TextDrawingArea.drawPixels(child.height - 4, childY + 2, childX + 2, -13752543, child.width - 4);
                    if (child.inputToggled) {
                        child.rsFont.drawBasicString((loopCycle % 40 < 20) ? child.inputText + "|" : child.inputText, childX + 6, childY + (child.height / 2) + 5, 0xffffff, 0, true);
                    } else {
                        child.rsFont.drawBasicString(child.inputText, childX + 6, childY + (child.height / 2) + 5, 0xffffff, 0, true);
                    }

                } else if (child.type == 8 && (anInt1500 == child.id || anInt1044 == child.id || anInt1129 == child.id) && anInt1501 == 30 && !menuOpen) {
                    int boxWidth = 0;
                    int boxHeight = 0;
                    TextDrawingArea textDrawingArea_2 = normalText;
                    for (String s1 = child.message; s1.length() > 0; ) {
                        if (s1.indexOf("%") != -1) {
                            do {
                                int k7 = s1.indexOf("%1");
                                if (k7 == -1) {
                                    break;
                                }
                                s1 = s1.substring(0, k7) + interfaceIntToString(extractInterfaceValues(child, 0)) + s1.substring(k7 + 2);
                            } while (true);
                            do {
                                int l7 = s1.indexOf("%2");
                                if (l7 == -1) {
                                    break;
                                }
                                s1 = s1.substring(0, l7) + interfaceIntToString(extractInterfaceValues(child, 1)) + s1.substring(l7 + 2);
                            } while (true);
                            do {
                                int i8 = s1.indexOf("%3");
                                if (i8 == -1) {
                                    break;
                                }
                                s1 = s1.substring(0, i8) + interfaceIntToString(extractInterfaceValues(child, 2)) + s1.substring(i8 + 2);
                            } while (true);
                            do {
                                int j8 = s1.indexOf("%4");
                                if (j8 == -1) {
                                    break;
                                }
                                s1 = s1.substring(0, j8) + interfaceIntToString(extractInterfaceValues(child, 3)) + s1.substring(j8 + 2);
                            } while (true);
                            do {
                                int k8 = s1.indexOf("%5");
                                if (k8 == -1) {
                                    break;
                                }
                                s1 = s1.substring(0, k8) + interfaceIntToString(extractInterfaceValues(child, 4)) + s1.substring(k8 + 2);
                            } while (true);
                        }
                        int l7 = s1.indexOf("\\n");
                        String s4;
                        if (l7 != -1) {
                            s4 = s1.substring(0, l7);
                            s1 = s1.substring(l7 + 2);
                        } else {
                            s4 = s1;
                            s1 = "";
                        }
                        int j10 = textDrawingArea_2.getTextWidth(s4);
                        if (j10 > boxWidth) {
                            boxWidth = j10;
                        }
                        boxHeight += textDrawingArea_2.anInt1497 + 1;
                    }
                    boxWidth += 6;
                    boxHeight += 7;
                    int xPos = childX + child.width - 5 - boxWidth;
                    int yPos = childY + child.height + 5;
                    if (xPos < childX + 5) {
                        xPos = childX + 5;
                    }
                    if (xPos + boxWidth > interfaceX + class9.width) {
                        xPos = interfaceX + class9.width - boxWidth;
                    }
                    if (yPos + boxHeight > interfaceY + class9.height) {
                        yPos = childY - boxHeight;
                    }
                    TextDrawingArea.drawPixels(boxHeight, yPos, xPos, 0xFFFFA0, boxWidth);
                    TextDrawingArea.fillPixels(xPos, boxWidth, boxHeight, 0, yPos);
                    String s2 = child.message;
                    for (int j11 = yPos + textDrawingArea_2.anInt1497 + 2; s2.length() > 0; j11 += textDrawingArea_2.anInt1497 + 1) {// anInt1497
                        if (s2.indexOf("%") != -1) {
                            do {
                                int k7 = s2.indexOf("%1");
                                if (k7 == -1) {
                                    break;
                                }
                                s2 = s2.substring(0, k7) + interfaceIntToString(extractInterfaceValues(child, 0)) + s2.substring(k7 + 2);
                            } while (true);
                            do {
                                int l7 = s2.indexOf("%2");
                                if (l7 == -1) {
                                    break;
                                }
                                s2 = s2.substring(0, l7) + interfaceIntToString(extractInterfaceValues(child, 1)) + s2.substring(l7 + 2);
                            } while (true);
                            do {
                                int i8 = s2.indexOf("%3");
                                if (i8 == -1) {
                                    break;
                                }
                                s2 = s2.substring(0, i8) + interfaceIntToString(extractInterfaceValues(child, 2)) + s2.substring(i8 + 2);
                            } while (true);
                            do {
                                int j8 = s2.indexOf("%4");
                                if (j8 == -1) {
                                    break;
                                }
                                s2 = s2.substring(0, j8) + interfaceIntToString(extractInterfaceValues(child, 3)) + s2.substring(j8 + 2);
                            } while (true);
                            do {
                                int k8 = s2.indexOf("%5");
                                if (k8 == -1) {
                                    break;
                                }
                                s2 = s2.substring(0, k8) + interfaceIntToString(extractInterfaceValues(child, 4)) + s2.substring(k8 + 2);
                            } while (true);
                        }
                        int l11 = s2.indexOf("\\n");
                        String s5;
                        if (l11 != -1) {
                            s5 = s2.substring(0, l11);
                            s2 = s2.substring(l11 + 2);
                        } else {
                            s5 = s2;
                            s2 = "";
                        }
                        if (child.centerText) {
                            textDrawingArea_2.drawCenteredText(yPos, xPos + child.width / 2, s5, j11, false);
                        } else {
                            if (s5.contains("\\r")) {
                                String text = s5.substring(0, s5.indexOf("\\r"));
                                String text2 = s5.substring(s5.indexOf("\\r") + 2);
                                textDrawingArea_2.drawRegularText(false, xPos + 3, 0, text, j11);
                                int rightX = boxWidth + xPos - textDrawingArea_2.getTextWidth(text2) - 2;
                                textDrawingArea_2.drawRegularText(false, rightX, 0, text2, j11);
                            } else {
                                textDrawingArea_2.drawRegularText(false, xPos + 3, 0, s5, j11);
                            }
                        }
                    }
                } else if (child.type == RSInterface.TYPE_HOVER) {
                    try {
                        Sprite sprite;
                        boolean hovered = child.selected;
                        if (interfaceIsSelected(child) || hovered) {
                            sprite = child.enabledSprite;
                        } else {
                            sprite = child.disabledSprite;
                        }
                        if (sprite != null) {
                            sprite.drawSprite(childX, childY);
                        } else {
                            drawHoverBox(childX, childY, child.tooltipBoxText);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (child.type == 88) {
                    Sprite sprite;
                    if (interfaceIsSelected(child)) {
                        sprite = child.enabledSprite;
                    } else {
                        sprite = child.disabledSprite;
                    }
                    if (sprite != null) {
                        if (child.drawsTransparent) {
                            sprite.drawTransparentSprite(childX, childY, child.customOpacity);
                        } else {
                            sprite.drawSprite(childX, childY);
                        }
                    }
                } else if (child.type == 10 && !menuOpen && skillTabHoverChild > 0 && anInt1501 == 30) {
                    if (child.id != skillTabHoverChild) {
                        continue;
                    }
                    int boxWidth = 0;
                    int boxHeight = 0;
                    TextDrawingArea textDrawingArea_2 = normalText;
                    for (String s1 = child.message; s1.length() > 0; ) {
                        int l7 = s1.indexOf("\\n");
                        String s4;
                        if (l7 != -1) {
                            s4 = s1.substring(0, l7);
                            s1 = s1.substring(l7 + 2);
                        } else {
                            s4 = s1;
                            s1 = "";
                        }
                        int j10 = textDrawingArea_2.getTextWidth(s4);
                        if (j10 > boxWidth) {
                            boxWidth = j10;
                        }
                        boxHeight += textDrawingArea_2.anInt1497 + 1;
                    }
                    boxWidth += 6;
                    boxHeight += 7;
                    boolean canDrawPercent = currentExp[skillIdForButton(child.id)] < 1000000000 && Skills.goalData[skillIdForButton(child.id)][0] != -1 && Skills.goalData[skillIdForButton(child.id)][1] != -1 && Skills.goalData[skillIdForButton(child.id)][2] != -1;
                    int xPos = (childX + child.width) - 5 - boxWidth;
                    int yPos = childY + child.height + 5;
                    if (canDrawPercent && Skills.SKILL_ID(child.id) == child.id) {
                        boxHeight += canDrawPercent ? 11 : 0;
                    } else {
                        boxHeight += -2;
                        canDrawPercent = false;
                    }
                    if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                        if (xPos < childX + 5) {
                            xPos = childX + 5;
                        }
                        if (xPos + boxWidth > interfaceX + class9.width) {
                            xPos = (interfaceX + class9.width) - boxWidth;
                        }
                        if (yPos + boxHeight > interfaceY + class9.height) {
                            yPos = (childY - boxHeight);
                        }
                        if (xPos + boxWidth + interfaceX + class9.width > 765) {
                            xPos = 765 - boxWidth - interfaceX - class9.width - 3;
                        }
                        if (yPos + boxHeight > 503 - yPos + boxHeight - 118) {
                            yPos -= boxHeight + 35;
                        }
                    } else {
                        if (xPos < childX + 5) {
                            xPos = childX + 5;
                        }
                        if (xPos > 1560 && xPos < 1600) {
                            xPos -= 0;
                        } else if (xPos >= 1600) {
                            xPos -= 0;
                        }


                        if (xPos + boxWidth > clientWidth) {
                            xPos -= (xPos + boxWidth) - clientWidth;
                        }

                        if (yPos + boxHeight > interfaceY + class9.height) {
                            yPos = childY - boxHeight;
                        }

                    }
                    TextDrawingArea.drawPixels(boxHeight, yPos, xPos, 0x25211B, boxWidth);
                    TextDrawingArea.fillPixels(xPos, boxWidth, boxHeight, 0, yPos);
                    String s2 = child.message;
                    for (int j11 = yPos + textDrawingArea_2.anInt1497 + 2; s2.length() > 0; j11 += textDrawingArea_2.anInt1497 + 1) {
                        int l11 = s2.indexOf("\\n");
                        String s5;
                        if (l11 != -1) {
                            s5 = s2.substring(0, l11);
                            s2 = s2.substring(l11 + 2);
                        } else {
                            s5 = s2;
                            s2 = "";
                        }
                        textDrawingArea_2.drawRegularText(false, xPos + 3, 0, "@vag@" + s5, j11);
                    }
                } else if (child.type == 12) {
                    // if (interfaceIsSelected(child)) {
                    // } else {
                    try {
                        drawHoverBox(childX, childY, child.message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /// }
                } else if (child.type == 13) {
                    Sprite sprite;
                    if (interfaceIsSelected(child)) {
                        sprite = child.enabledSprite;
                    } else {
                        sprite = child.disabledSprite;
                    }
                    if (sprite != null) {
                        if (child.opacity > 0) {
                            sprite.drawTransparentSprite(childX, childY, child.opacity);
                        } else {
                            sprite.drawSprite(childX, childY);
                        }
                    }

                }

                if (child.type == 111) {
                    //  System.out.println("Type ->");
                    boolean hovered = child.selected;
                    Sprite sprite = child.disabledSprite;
                    if (interfaceIsSelected(child) || hovered) {
                        sprite = child.enabledSprite;
                    }

                    sprite.drawSprite(childX, childY);
                }

                if (child.type == RSInterface.DRAW_FILLED_PIXELS_W_ALPHA) {
                    RSInterface mainSprite = RSInterface.interfaceCache[class9.children[0]];
                    TextDrawingArea.drawAlphaFilledPixels(childX, childY, mainSprite.width, mainSprite.height + 2, child.layerColor, child.layerTransparency);
                }

                if (child.type == 100 && child.unrevealedSprite != null) {

                    child.unrevealedSprite.drawSprite(childX + 2, childY + 2);
                    // System.out.println("Unrevealed sprite drawn");
                }

                if (child.type == RSInterface.WINDOW_COMPONENT) {
                    if (!child.isTransparentWindow()) {
                        spritesMap.get(5649).repeatBoth(childX, childY, child.width, child.height);
                    }
                    if (child.is_modern_window()) {
                        spritesMap.get(5662).repeatX(childX, childY, child.width);
                        spritesMap.get(5663).repeatX(childX, child.height + childY - spritesMap.get(5663).myHeight, child.width);
                        spritesMap.get(5664).repeatY(childX, childY, child.height);
                        spritesMap.get(5665).repeatY(child.width + childX - spritesMap.get(5665).myWidth, childY, child.height);
                        spritesMap.get(5658).drawSprite(childX, childY);
                        spritesMap.get(5659).drawSprite(child.width + childX - spritesMap.get(5659).myWidth, childY);
                        spritesMap.get(5660).drawSprite(childX, child.height + childY - spritesMap.get(5660).myHeight);
                        spritesMap.get(5661).drawSprite(child.width + childX - spritesMap.get(5661).myWidth, child.height + childY - spritesMap.get(5661).myHeight);
                    }
                    if (child.isClassicWindow()) {
                        spritesMap.get(5654).repeatX(childX, childY, child.width);
                        spritesMap.get(5655).repeatX(childX, child.height + childY - spritesMap.get(5655).myHeight, child.width);
                        spritesMap.get(5656).repeatY(childX, childY, child.height);
                        spritesMap.get(5657).repeatY(child.width + childX - spritesMap.get(5657).myWidth, childY, child.height);
                        spritesMap.get(5650).drawSprite(childX, childY);
                        spritesMap.get(5651).drawSprite(child.width + childX - spritesMap.get(5651).myWidth, childY);
                        spritesMap.get(5652).drawSprite(childX, child.height + childY - spritesMap.get(5652).myHeight);
                        spritesMap.get(5653).drawSprite(child.width + childX - spritesMap.get(5653).myWidth, child.height + childY - spritesMap.get(5653).myHeight);
                    }
                    if (child.isCustomWindow()) {
                        spritesMap.get(5670).repeatX(childX, childY, child.width);
                        spritesMap.get(5671).repeatX(childX, child.height + childY - spritesMap.get(5671).myHeight, child.width);
                        spritesMap.get(5672).repeatY(childX, childY, child.height);
                        spritesMap.get(5673).repeatY(child.width + childX - spritesMap.get(5673).myWidth, childY, child.height);
                        spritesMap.get(5666).drawSprite(childX, childY);
                        spritesMap.get(5667).drawSprite(child.width + childX - spritesMap.get(5667).myWidth, childY);
                        spritesMap.get(5668).drawSprite(childX, child.height + childY - spritesMap.get(5668).myHeight);
                        spritesMap.get(5669).drawSprite(child.width + childX - spritesMap.get(5669).myWidth, child.height + childY - spritesMap.get(5669).myHeight);
                    }
                }

                if (child.type == RSInterface.NAV_COMPONENT) {
                    Sprite left = spritesMap.get(child.spriteMap[0]);
                    Sprite mid = spritesMap.get(child.spriteMap[1]);;
                    Sprite right = spritesMap.get(child.spriteMap[2]);;
                    boolean hovered = child.selected;
                    if (interfaceIsSelected(child) || hovered && !child.isNavActive) {
                        left = spritesMap.get(child.spriteMap[3]);
                        mid = spritesMap.get(child.spriteMap[4]);
                        right = spritesMap.get(child.spriteMap[5]);
                    }
                    if (child.width < 12) {
                        child.width = 12;
                    }
                    int amountOfLoops = (int) Math.ceil((child.width - 8) / 4);
                    int x_offset = 4;
                    int icon_off = (child.width - child.icon.myWidth) / 2;
                    //int end_component_offset = (child.width - (((amountOfLoops - 1) * 20) + 4));
                    left.drawSprite(childX, childY);
                    for (int i = 0; i < amountOfLoops; i++) {
                        mid.drawSprite(childX + x_offset, childY);
                        x_offset += 4;
                    }
                    right.drawSprite(childX + x_offset, childY);
                    child.icon.drawSprite(childX + icon_off, childY);
                }

                if (child.type == RSInterface.CHAT_COLOR_OPTIONS_COMPONENT) {
                    switch (child.colorTypes) {
                        case SPLITCHAT:
                            Raster.fillRect(childX + 1, childY + 1, child.colorWidth, child.colorHeight, COLOR_BOXES[splitChatColor], 255);
                            Raster.fillPixels(childX, child.colorWidth + 1, child.colorHeight, 0, childY);
                        break;
                        case CLANCHAT:
                            Raster.fillRect(childX + 1, childY + 1, child.colorWidth, child.colorHeight, COLOR_BOXES[clanChatColor], 255);
                            Raster.fillPixels(childX, child.colorWidth + 1, child.colorHeight, 0, childY);
                        break;
                        case HITPOINTS:
                            Raster.fillRect(childX + 1, childY + 1, child.colorWidth, child.colorHeight, HIT_POINT_COLORS[healthpoints_bar_color][0], 255);
                            Raster.fillPixels(childX, child.colorWidth + 1, child.colorHeight, 0, childY);
                            break;
                    }
                }

                if (child.type == RSInterface.PROGRESS_BAR_W_MILESTONES) {
                    int percent = (int) MathUtils.calculatePercentage(child.progressBarCurrent, child.progressBarMax);
                    if (percent > 100)
                        percent = 100;
                    Sprite theSprite = spritesMap.get(child.progressBarFill);
                    double fuck = (double) percent / 100;
                    int math_of_bar = (int) ((child.progressBarWidth - 2) * fuck);
                    Sprite cuttedSprite = Sprite.getCutted(theSprite, math_of_bar, child.progressBarHeight);
                    cuttedSprite.drawSprite(childX + 1, childY + 1);
                    int maxVal = 0;
                    if (child.milestones != null) {
                        for (int i = 0; i < child.milestones.length; i++)
                        maxVal = child.milestones[i].getMilestoneGoal();
                    }

                    boldText.drawCenteredText(ColorConstants.VANGUARD_PURPLE, childX + (child.progressBarWidth / 2),  child.progressBarCurrent + "/" + maxVal, childY + 24, true);
                    if (child.milestones != null) {
                        for (int i = 0; i < child.milestones.length; i++) {
                            int milestoneGoal = child.milestones[i].getMilestone();
                            int milestoneMax = child.milestones[i].getMilestoneGoal();
                            int perce = (int) MathUtils.calculatePercentage(milestoneGoal, milestoneMax);
                            if (perce > 100)
                                perce = 100;
                            int x_mod = getPercent(child.progressBarWidth, perce);
                            Sprite sprite = spritesMap.get(2499); //Milestone node
                            int mouseMod = 0;
                            if (x_mod >= 5)
                                mouseMod = -2;
                            if (x_mod < 3)
                                x_mod = 3;
                            if (i == child.milestones.length -1)
                                mouseMod = -10;
                            sprite.drawSprite(childX + x_mod + mouseMod, childY  + 1);
                        }
                    }
                }


                if (child.type == RSInterface.PROGRESS_BAR_CUT_SYS) {
                    int percent = (int) MathUtils.calculatePercentage(child.progressBarCurrent, child.progressBarMax);
                    if (percent > 100)
                        percent = 100;
                    Sprite theSprite = spritesMap.get(child.progressBarFill);
                    double fuck = (double) percent / 100;
                    int math_of_bar = (int) (child.progressBarWidth * fuck);
                    Sprite cuttedSprite = Sprite.getCutted(theSprite, math_of_bar, child.progressBarHeight);
                    spritesMap.get(child.progressBarBack).drawSprite(childX, childY);
                    cuttedSprite.drawSprite(childX, childY);
                    boldText.drawCenteredText(ColorConstants.BLACK, childX + (child.progressBarWidth / 2),  child.progressBarCurrent + "/" + child.progressBarMax, childY + 15, true);

                }

                if (child.type == RSInterface.BANK_NAV_COMPONENT) {
                    boolean hovered = child.selected;
                    int icon_off_x = (child.width - child.icon.myWidth) / 2;
                    int icon_off_y = (child.height - child.icon.myHeight) / 2;
                    if (child.id == 27014 || child.id == 27015) {
                        if (interfaceIsSelected(child) || hovered && !child.isNavActive)
                            child.bankOn.drawSprite(childX, childY);
                        else
                            child.bankOff.drawSprite(childX, childY);
                        if (child.inv[0] < 1)
                            child.icon.drawSprite(childX + icon_off_x, childY + icon_off_y);
                    } else {
                        if (RSInterface.interfaceCache[child.id - 1].inv[0] > 0) {
                            if (interfaceIsSelected(child) || hovered && !child.isNavActive)
                                child.bankOn.drawSprite(childX, childY);
                            else
                                child.bankOff.drawSprite(childX, childY);
                            if (child.inv[0] < 1)
                                child.icon.drawSprite(childX + icon_off_x, childY + icon_off_y);
                        }
                    }
                    icon_off_x = (child.width - 32) / 2;
                    icon_off_y = (child.height - 32) / 2;
                    if (child.inv[0] > 0) {
                        child.tooltip = "View contents of this bank tab";
                        Sprite item;
                        item = ItemDef.getSprite(child.inv[0] -1, 0, 0);
                        if (item != null) {
                            item.drawSprite(childX + icon_off_x, childY + icon_off_y);
                        }
                    }
                }

                if (child.type == RSInterface.NEW_BUTTON_COMPONENT) {
                    boolean hovered = child.selected;
                    if (interfaceIsSelected(child) || hovered)
                        child.buttonOn.drawAdvancedSprite(childX, childY);
                    else
                        child.buttonOff.drawAdvancedSprite(childX, childY);
                }

                if (child.type == RSInterface.NEW_BUTTON_COMPONENT_STRING) {
                    boolean hovered = child.selected;
                    if (interfaceIsSelected(child) || hovered)
                        child.buttonOn.drawSprite(childX, childY);
                    else
                        child.buttonOff.drawSprite(childX, childY);
                    int text_off_x = (child.width) / 2;
                    int text_off_y = (child.height) / 2;
                    if (child.centerText)
                        smallText.drawCenteredText(ColorConstants.VANGUARD_PURPLE, childX + text_off_x, child.message, childY + text_off_y + 3, true);
                    else
                        smallText.drawText(ColorConstants.VANGUARD_PURPLE, child.message, childX + 25, childY + 30);
                }

                if (child.type == RSInterface.NEW_BUTTON_COMPONENT_WITH_ICON) {
                    boolean hovered = child.selected;
                    int icon_off_x = (child.width - (child.icon.myWidth)) / 2;
                    int icon_off_y = (child.height - child.icon.myHeight) / 2;
                    if (interfaceIsSelected(child) || hovered)
                        child.buttonOn.drawSprite(childX, childY);
                    else
                        child.buttonOff.drawSprite(childX, childY);
                    child.icon.drawSprite(childX + icon_off_x, childY + icon_off_y);
                }

                if (child.type == RSInterface.NEW_BUTTON_COMPONENT_WITH_ACTIVE_ICON) {
                    boolean hovered = child.selected;
                    int icon_off_x = (child.width - child.icon.myWidth) / 2;
                    int icon_off_y = (child.height - child.icon.myHeight) / 2;
                    if (interfaceIsSelected(child) || hovered && !child.isNavActive)
                        child.buttonOn.drawSprite(childX, childY);
                    else
                        child.buttonOff.drawSprite(childX, childY);
                    child.icon.drawSprite(childX + icon_off_x, childY + icon_off_y);
                }

                if (child.type == RSInterface.NEW_BUTTON_COMPONENT_WITH_ACTIVE_STRING) {
                    boolean hovered = child.selected;
                    int text_off_x = (child.width) / 2;
                    if (interfaceIsSelected(child) || hovered && !child.isNavActive)
                        child.buttonOn.drawSprite(childX, childY);
                    else
                        child.buttonOff.drawSprite(childX, childY);
                    smallText.drawCenteredText(child.disabledColor, childX + text_off_x, child.text, childY + 15, child.textShadow);
                }

                if (child.type == RSInterface.NEW_CLICKABLE_BUTTON) {
                    child.enabledSprite.drawAdvancedSprite(childX, childY);
                }

                if (child.type == RSInterface.NEW_COMBAT_COMPONENT) {
                    boolean hovered = child.selected;
                    if (interfaceIsSelected(child) || hovered && !child.isNavActive)
                        child.buttonOn.drawSprite(childX, childY);
                    else
                        child.buttonOff.drawSprite(childX, childY);
                    int text_off_x = (child.width) / 2;
                    smallText.drawCenteredText(ColorConstants.VANGUARD_PURPLE, childX + text_off_x, child.text, childY + child.height - 3, child.textShadow);
                }

                if (child.type == RSInterface.INVOCATION_COMPONENT) {
                    if (child.isLocked) {
                        child.disabledSprite.drawSpriteWithOpacity(childX, childY, 50);
                        int text_off_x = (child.width) / 2;
                        int icon_off_x = (child.width - spritesMap.get(5728).myWidth) / 2;
                        int icon_off_y = (child.height - spritesMap.get(5728).myHeight) / 2;
                        int y_off = 32;
                        String ender = " ";
                        newSmallFont.drawCenteredString("<trans=50>" + child.tooltip, childX + text_off_x, childY + 15, ColorConstants.VANGUARD_PURPLE, 0);
                        if (newSmallFont.getTextWidth(child.description) > 180) {
                            String[] asdf = child.description.split(" ");
                            StringBuilder builder = new StringBuilder();
                            for (int i = 0; i < asdf.length; i++) {
                                if (newSmallFont.getTextWidth(builder.toString()) + newSmallFont.getTextWidth(asdf[i]) > 180) {
                                    newSmallFont.drawCenteredString("<trans=50>" + builder.toString(), childX + text_off_x, childY + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                                    y_off += 16;
                                    builder = new StringBuilder();
                                }
                                if (i == asdf.length - 1) {
                                    ender = ".";
                                }
                                builder.append(asdf[i] + ender);
                            }
                            newSmallFont.drawCenteredString("<trans=50>" + builder.toString(), childX + text_off_x, childY + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                        } else {
                            newSmallFont.drawCenteredString("<trans=50>" + child.description, childX + text_off_x, childY + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                        }
                        spritesMap.get(5728).drawSprite(childX + icon_off_x, childY + icon_off_y);
                    } else {
                        child.disabledSprite.drawSprite(childX, childY);
                        int text_off_x = (child.width) / 2;
                        int y_off = 32;
                        String ender = " ";
                        newSmallFont.drawCenteredString(child.tooltip, childX + text_off_x, childY + 15, ColorConstants.VANGUARD_PURPLE, 0);
                        if (newSmallFont.getTextWidth(child.description) > 180) {
                            String[] asdf = child.description.split(" ");
                            StringBuilder builder = new StringBuilder();
                            for (int i = 0; i < asdf.length; i++) {
                                if (newSmallFont.getTextWidth(builder.toString()) + newSmallFont.getTextWidth(asdf[i]) > 180) {
                                    newSmallFont.drawCenteredString(builder.toString(), childX + text_off_x, childY + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                                    y_off += 16;
                                    builder = new StringBuilder();

                                }
                                if (i == asdf.length - 1) {
                                    ender = ".";
                                }
                                builder.append(asdf[i] + ender);
                            }
                            newSmallFont.drawCenteredString(builder.toString(), childX + text_off_x, childY + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                        } else {
                            newSmallFont.drawCenteredString(child.description, childX + text_off_x, childY + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                        }
                    }
                }

                if (child.type == RSInterface.TOOLTIP_COMPONENT && !menuOpen && widgetHoverChild > 0) {
                    if (child.id != widgetHoverChild) {
                        continue;
                    }
                    int xPos = interfaceX + 15;
                    int yPos = interfaceY + 247;
                    int y_off = 18;
                    String ender = " ";
/*                    child.buttonOn.drawSprite(xPos, yPos);*/
                    if (newSmallFont.getTextWidth(child.message) > 135) {
                        String[] asdf = child.message.split(" ");
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < asdf.length; i++) {
                            if (newSmallFont.getTextWidth(builder.toString()) + newSmallFont.getTextWidth(asdf[i]) > 135) {
                                newSmallFont.drawBasicString(builder.toString(), xPos + 9, yPos + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                                y_off += 15;
                                builder = new StringBuilder();

                            }
                            if (i == asdf.length - 1) {
                                ender = ".";
                            }
                            builder.append(asdf[i] + ender);
                        }
                        newSmallFont.drawBasicString(builder.toString(), xPos + 9, yPos + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                    } else {
                        newSmallFont.drawBasicString(child.message, xPos + 9, yPos + y_off, ColorConstants.VANGUARD_PURPLE, 0);
                    }
                }

                if (child.type == RSInterface.NODE_COMPONENT) {
                    boolean hovered = child.selected;
                    if (interfaceIsSelected(child) || hovered)
                        child.buttonOn.drawAdvancedSprite(childX, childY);
                    else
                        child.buttonOff.drawAdvancedSprite(childX, childY);
                }
            }
        }
        TextDrawingArea.setBounds(i1, j1, k1, l1);
    }

    private int getPercent(int width, int v) {
        double fuck = (double) v / 100;
        int math_of_bar = (int) (width * fuck);
        return math_of_bar;
    }
    private int hoverSpriteId = -1;
    private boolean hovered(RSInterface rsi) {
        return (rsi.id == this.hoverSpriteId) && (rsi.hovers);
    }


    public void drawOnBankInterface() {
        if (openInterfaceID == 5292 && RSInterface.interfaceCache[27000].message.equals("1")) {
            int i = Integer.parseInt(RSInterface.interfaceCache[27001].message);
            int j = Integer.parseInt(RSInterface.interfaceCache[27002].message);
            for (int k = 0; k <= i; k++) {
                RSInterface.interfaceCache[27014 + k].disabledSprite = spritesMap.get(1009);
                RSInterface.interfaceCache[27014 + k].message = (new StringBuilder()).append("Click here to select tab ")
                        .append(k + 1)
                        .toString();
            }

            for (int l = i + 1; l <= 8; l++) {
                RSInterface.interfaceCache[27014 + l].disabledSprite = null;
                RSInterface.interfaceCache[27014 + l].message = "";
            }

            if (i != 8) {
                RSInterface.interfaceCache[27015 + i].disabledSprite = spritesMap.get(1010);
                RSInterface.interfaceCache[27015 + i].message = "Drag an item here to create a new tab";
            }
            if (j == -1) {
                RSInterface.interfaceCache[27013].disabledSprite = spritesMap.get(1011);
            } else if (j > 0) {
                RSInterface.interfaceCache[27014 + j].disabledSprite = spritesMap.get(1012);
                RSInterface.interfaceCache[27014].disabledSprite = spritesMap.get(1011);
            } else {
                RSInterface.interfaceCache[27014].disabledSprite = spritesMap.get(1008);
            }
            RSInterface.interfaceCache[27000].message = "0";
        }
    }

    List<String> loadingStrings = Arrays.asList("Hades has spent countless hours working his ass off.",
        "Athens was built off the greek mythology!");

    public String loadingStringsRandom() {
        Random rand = new Random();
        String hey = loadingStrings.get(rand.nextInt(loadingStrings.size()));
        return hey;
    }

    public void displayLoadingScreen(boolean updatingCache) {
        Graphics graphics = this.getGraphics() != null ? this.getGraphics() : canvas.getGraphics();
        if (graphics == null) {
            return;
        }

        graphics.setColor(Color.WHITE);

        if (loadingImages[0] != null)
            graphics.drawImage(loadingImages[0], 0, 0, null);
        if (loadingPercentage > 0) {
            int scaleX = (loadingPercentage * 790 / 560) * 4;
            if (scaleX > 559) {
                scaleX = 559;
            }

            if (!updatingCache)
                graphics.drawString("Loading Percent " + loadingPercentage + "%", getScreenWidth() / 2 - 75, getScreenHeight() / 2 - 50);
        }
    }



    public int skillIdForButton(int buttonId) {
        int[] buttonIds = {
            86102, 86105, 86108, 86111, 86114, 86117, 86120, 86123, 86126, 86129,
                86302, 86305, 86308, 86311, 86314, 86317
        };
        int[] skillID = {
            ATTACK, STRENGTH, DEFENCE, MAGIC, RANGED, PRAYER, NECROMANCY, SLAYER, BEAST_HUNTER, HITPOINTS,
                MINING, WOODCUTTING, JOURNEYMAN, HERBLORE, ALCHEMY, SOULRENDING
        };
        for (int i = 0; i < buttonIds.length; i++) {
            if (buttonIds[i] == buttonId) {
                buttonId = i;
                return skillID[buttonId];
            }
        }
        return 0;
    }

    public void recreateClientFrame(boolean undecorative, int width, int height, boolean resizable, int displayMode, boolean toggle) {
        recreateClientFrame(undecorative, width, height, resizable);
    }

    public void handleHovers(boolean alertScreen) {
        int cursor = -1;
        oldCursor = null;
        //System.out.println(mouseX  + "   " + mouseY );

        otherHover = loginHover = rememberMeHover = textArea1Hover = textArea2Hover = backButtonHover = websiteHover = storeHover = voteHover = discordHover = false ; // Reset hovers
        for (int i = 0; i < accountHovers.length; i++) {
            accountHovers[i] = false;
            accountDeletion[i] = false;
        }

        if (!alertScreen) {
            if (mouseX >= 323 && mouseX <= 440 && mouseY >= 329 && mouseY <= 361) {
                cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                // login button hover
                loginHover = true;
            }

            if (showTwoFactorAuth) {
                if (mouseX >= 488 && mouseX <= 514 && mouseY >= 224 && mouseY <= 242) {
                    //cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                    otherHover = true;
                }
                if (mouseX >= 250 && mouseX <= 488) {
                    if (mouseY >= 277 && mouseY <= 313) {
                        cursor = Cursor.TEXT_CURSOR;
                        textArea1Hover = true;
                    }
                }
            } else {
                if (mouseX >= 254 && mouseX <= 506) {
                    if (mouseY >= 222 && mouseY <= 255) {
                        // username
                        cursor = Cursor.TEXT_CURSOR;
                        textArea1Hover = true;
                    } else if (mouseY >= 288 && mouseY <= 318) {
                        //password
                        cursor = Cursor.TEXT_CURSOR;
                        textArea2Hover = true;
                    }
                }
                if (mouseX >= 313 && mouseX <= 326 && mouseY >= 386 && mouseY <= 400) {
                    cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                    //remember me button hover
                    rememberMeHover = true;
                }
                if (mouseX >= 77 && mouseX <= 149 && mouseY >= 466 && mouseY <= 493) {
                    cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                    websiteHover = true;
                }
                if (mouseX >= 608 && mouseX <= 682 && mouseY >= 466 && mouseY <= 493) {
                    cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                    discordHover = true;
                }
                if (mouseX >= 454 && mouseX <= 499 && mouseY >= 466 && mouseY <= 493) {
                    cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                    voteHover = true;
                }
                if (mouseX >= 259 && mouseX <= 312 && mouseY >= 466 && mouseY <= 493) {
                    cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                    storeHover = true;
                }
            }

        } else {
            if (mouseX >= 330 && mouseX <= 440 && mouseY >= 270 && mouseY <= 325) {
                cursor = Configuration.NEW_CURSORS ? 1061 : Cursor.HAND_CURSOR;
                backButtonHover = true;
            }
        }

        if (cursor == Cursor.HAND_CURSOR || cursor == Cursor.TEXT_CURSOR) {
            getGameComponent().setCursor(new Cursor(cursor));
        } else if (cursor == 1061) {
            super.setCursor(CursorData.CURSOR_55);
        } else {
            if (Configuration.NEW_CURSORS) {
                super.setCursor(CursorData.CURSOR_0);
            } else {
                getGameComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    }

    public void playLoginScreenMusic() {
        if (musicVolume == 0) {
            return;
        }

        nextSong = 16; // 16 (normal theme)
        //nextSong = 547; //Scape santa (xmas)
        //nextSong = 321; //Scape scared (h'ween)

        onDemandFetcher.requestFileData(2, nextSong);
    }

    private final AnimatedSprite DANCING_GIF = new AnimatedSprite(Signlink.getCacheDirectory() + "ricardogif.gif");

    public void drawLoginScreen(boolean flag) {
        resetImageProducers();
        titleScreenIP.initDrawingArea();
        TextDrawingArea.drawFilledPixels(0, 0, getScreenWidth(), getScreenHeight(), 0x000000);
        int centerX = getScreenWidth() / 2;
        int centerY = getScreenHeight() / 2;
        titleAlpha += titleAlpha < 250 ? 8 : 0;

        if (titleAlpha < 250) {
            spritesMap.get(449)
                    .drawTransparentSprite(centerX - spritesMap.get(449).myWidth / 2, centerY - spritesMap.get(449).myHeight / 2, titleAlpha);
        } else {
            handleHovers(false);
            spritesMap.get(449).drawAdvancedSprite(0, 0);

            if (loginHover) {
                spritesMap.get(464).drawAdvancedSprite(324, 329);//LOGINEDIT
            }

            if (Configuration.SAVE_ACCOUNTS) {
                spritesMap.get(1600).drawAdvancedSprite(313, 389);
            }
           // spritesMap.get(471).drawAdvancedSprite(245, 341);
      //      normalText.method385(0xffff00, "Mouse X: " + super.mouseX + " , Mouse Y: " + super.mouseY, 314, 5);

            if (showTwoFactorAuth) {
                if (loginScreenCursorPos == 0 && loopCycle % 45 < 10) {
                    boldText.drawRegularText(true, 280, 16777215, currentPinCode + "|", 300);
                } else {
                    boldText.drawRegularText(true, 280, 16777215, currentPinCode, 300);
                }
            } else {

                if (loginScreenCursorPos == 0 && loopCycle % 45 < 10) {
                    boldText.drawRegularText(true, 265, 16777215, myUsername + "|", 245);
                } else {
                    boldText.drawRegularText(true, 265, 16777215, myUsername, 245);
                }

                if (loginScreenCursorPos == 1 && loopCycle % 45 < 10) {
                    boldText.drawRegularText(true, 265, 16777215, getStars(password) + "|", 306);
                } else {
                    boldText.drawRegularText(true, 265, 16777215, getStars(password), 306);
                }
            }

            if (!showTwoFactorAuth) {
                for (int i = 0; i < loginMessages.length; i++) {
                    String s = loginMessages[i];
                    if (s == null || s.length() == 0) {
                        continue;
                    }
                    int y = clientHeight / 2 + i * 20;
                    boldText.drawRegularText(true, clientWidth / 2 - boldText.getTextWidth(s) / 2, 16777215, s, y);
                }
            } else if (showCaptcha) {
                drawColorBox(0xFFFFFF, (clientWidth - captcha.myWidth) / 2, 205, captcha.myWidth, captcha.myHeight + 10);
                captcha.drawAdvancedSprite((clientWidth - captcha.myWidth) / 2, 210);

                if (otherHover) {
                    newRegularFont.drawCenteredString("Back", 500, 235, 0xFFD700, 0);
                } else {
                    newRegularFont.drawCenteredString("Back", 500, 235, 0xffffff, 0);
                }
            }
        }

        //DANCING_GIF.drawAdvancedSprite(200, 200);

        if (!resizing) {
            titleScreenIP.drawGraphics(canvas.getGraphics(), 0, 0);
        }
        if (welcomeScreenRaised) {
            welcomeScreenRaised = false;
        }
    }

    public void drawColorBox(int color, int xPos, int yPos, int width, int height) {
        TextDrawingArea.fillRectangle(color, yPos, width, height, 256, xPos);
    }

    public int getXTextOffset(int index) {
        switch (index) {
            case 1:
                return 280;
            case 2:
                return 351;
            case 3:
                return 427;
            case 4:
                return 495;
        }
        return 206;
    }

    private void drawLogo() {
        resetImageProducers();
        System.gc();
    }

    public void drawMenu() {
        int xPos = menuOffsetX;
        int yPos = menuOffsetY;
        int menuW = menuWidth;
        int x = super.mouseX;
        int y = super.mouseY;
        int menuH = menuHeight + 1;
        if (menuScreenArea == 1 && GameFrame.getScreenMode() != ScreenMode.FIXED) {
            xPos += 519;// +extraWidth;
            yPos += 168;// +extraHeight;
        }
        if (menuScreenArea == 2 && GameFrame.getScreenMode() != ScreenMode.FIXED) {
            yPos += 338;
        }
        if (menuScreenArea == 3 && GameFrame.getScreenMode() != ScreenMode.FIXED) {
            xPos += 515;
            yPos += 0;
        }
        if (menuScreenArea == 0) {
            if ((GameFrame.getScreenMode() == ScreenMode.FIXED)) {
                x -= 4;
                y -= 2;
            }
            if ((GameFrame.getScreenMode() != ScreenMode.FIXED)) {
                x -= 4;
                y -= -4;
            }
        }
        if (menuScreenArea == 1) {
            if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                x -= 519;
                y -= 168;
            }
        }
        if (menuScreenArea == 2) {
            if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                x -= 17;
                y -= 338;
            }
        }
        if (menuScreenArea == 3 && GameFrame.getScreenMode() == ScreenMode.FIXED) {
            x -= 515;
            y -= 0;
        }
        TextDrawingArea.drawPixels(menuH - 4, yPos + 2, xPos, 0x706a5e, menuW);
        TextDrawingArea.drawPixels(menuH - 2, yPos + 1, xPos + 1, 0x706a5e, menuW - 2);
        TextDrawingArea.drawPixels(menuH, yPos, xPos + 2, 0x706a5e, menuW - 4);
        TextDrawingArea.drawPixels(menuH - 2, yPos + 1, xPos + 3, 0x2d2822, menuW - 6);
        TextDrawingArea.drawPixels(menuH - 4, yPos + 2, xPos + 2, 0x2d2822, menuW - 4);
        TextDrawingArea.drawPixels(menuH - 6, yPos + 3, xPos + 1, 0x2d2822, menuW - 2);
        TextDrawingArea.drawPixels(menuH - 22, yPos + 19, xPos + 2, 0x524a3d, menuW - 4);
        TextDrawingArea.drawPixels(menuH - 22, yPos + 20, xPos + 3, 0x524a3d, menuW - 6);
        TextDrawingArea.drawPixels(menuH - 23, yPos + 20, xPos + 3, 0x2b271c, menuW - 6);
        TextDrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x2a291b, yPos + 2);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a261b, yPos + 3);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x252116, yPos + 4);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x211e15, yPos + 5);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1e1b12, yPos + 6);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x1a170e, yPos + 7);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 2, 0x15120b, yPos + 8);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x100d08, yPos + 10);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 11);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x080703, yPos + 12);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 13);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 14);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 15);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x070802, yPos + 16);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x090a04, yPos + 17);
        TextDrawingArea.fillPixels(xPos + 2, menuW - 4, 1, 0x2a291b, yPos + 18);
        TextDrawingArea.fillPixels(xPos + 3, menuW - 6, 1, 0x564943, yPos + 19);
        newBoldFont.drawBasicString("Choose Option", xPos + 3, yPos + 14, 0xc6b895, 0, true);
        int beforeX = xPos;
        for (int index = 0; index < menuActionRow; index++) {
            int yOffset = yPos + 31 + (menuActionRow - 1 - index) * 15;
            int color = 0xffffff;
            if (x > xPos && x < xPos + menuW && y > yOffset - 11 && y < yOffset + 5) {
                TextDrawingArea.drawPixels(15, yOffset - 11, xPos + 3, 0x6f695d, menuWidth - 6);
                detectCursor(menuActionName[index]);
                color = 0xffff00;
                currentActionMenu = index;
            }
            xPos += 3;
            newBoldFont.drawBasicString(menuActionName[index], xPos, yOffset, color, 0, true);
            xPos = beforeX;
        }
    }

    public void detectCursor(String tooltip) {
        if (!Configuration.NEW_CURSORS) {
            return;
        }
        if (tooltip == null || tooltip.isEmpty()) {
            return;
        }
        CursorData newCursor = null;
        tooltip = tooltip.replaceAll("@gre@", "");
        tooltip = tooltip.replaceAll("@yel@", "");
        for (CursorData cursorData : CursorData.values()) {
            if (tooltip.startsWith(cursorData.tooltip)) {
                newCursor = cursorData;
                break;
            }
        }
        if (newCursor == null) {
            newCursor = CursorData.CURSOR_0;
        }
        super.setCursor(newCursor);
    }

    private void drawMinimap() {
        switch (GameFrame.getScreenMode()) {
            case FIXED:
                mapArea.setxPos(519);
                mapArea.setyPos(0);
                break;
            case RESIZABLE_CLASSIC:
/*                mapArea.setxPos(getScreenWidth() - 246);
                mapArea.setyPos(1);
                break;*/
            case RESIZABLE_MODERN:
                mapArea.setxPos(getScreenWidth() - 170);
                mapArea.setyPos(1);
                break;
        }
        mapArea.render(this);
    }

    public void refreshMinimap(Sprite sprite, int j, int k) {
        int l = k * k + j * j;
        if (l > 4225 && l < 0x15f90) {
            int i1 = cameraRotation + minimapRotation & 0x7ff;
            int j1 = Rasterizer3D.SINE[i1];
            int k1 = Rasterizer3D.COSINE[i1];
            j1 = j1 * 256 / (minimapZoom + 256);
            k1 = k1 * 256 / (minimapZoom + 256);
        } else {
            markMinimap(sprite, k, j);
        }
    }

    private void drawScrollbarBottom(int barWidth, int scrollPos, int yPos, int xPos, int contentHeight, boolean newScroller, boolean isTransparent) {

        int backingAmount = (barWidth - 32) / 5;
        int scrollPartHeight = ((barWidth - 32) * barWidth) / contentHeight;

        if (scrollPartHeight < 10) {
            scrollPartHeight = 10;
        }

        int scrollPartAmount = (scrollPartHeight / 5) - 2;
        int scrollPartPos = ((barWidth - 32 - scrollPartHeight) * scrollPos) / (contentHeight - barWidth) + 16 + xPos;
        /* Bar fill */
        for (int i = 0, xxPos = xPos + 16; i <= backingAmount; i++, xxPos += 5) {
            spritesMap.get(1108).drawSprite(xxPos, yPos);
        }
        /* Top of bar */
        spritesMap.get(1107).drawSprite(scrollPartPos, yPos);//
        scrollPartPos += 5;
        /* Middle of bar */
        for (int i = 0; i <= scrollPartAmount; i++) {
            spritesMap.get(1110).drawSprite(scrollPartPos, yPos);// scrollPartPos
            scrollPartPos += 5;
        }
        scrollPartPos = ((barWidth - 32 - scrollPartHeight) * scrollPos) / (contentHeight - barWidth) + 16 + xPos + (scrollPartHeight - 5);
        /* Bottom of bar */
        spritesMap.get(1109).drawSprite(scrollPartPos, yPos);
        /* Arrows */
        spritesMap.get(1112).drawSprite(xPos, yPos);

        spritesMap.get(1111).drawSprite((xPos + barWidth) - 16, yPos);

    }

    public void drawScrollbar(int barHeight, int scrollPos, int yPos, int xPos, int contentHeight, boolean newScroller, boolean isTransparent) {
        if (contentHeight <= 0)
            return;
        int backingAmount = (barHeight - 32) / 5;
        int scrollPartHeight = (barHeight - 32) * barHeight / contentHeight;
        int scrollerID;
        if (newScroller) {
            scrollerID = 4;
        } else if (isTransparent) {
            scrollerID = 8;
        } else {
            scrollerID = 0;
        }
        if (scrollPartHeight < 10) {
            scrollPartHeight = 10;
        }
        int scrollPartAmount = scrollPartHeight / 5 - 2;
        int scrollPartPos = (barHeight - 32 - scrollPartHeight) * scrollPos / (contentHeight - barHeight) + 16 + yPos;
        /* Bar fill */
        for (int i = 0, yyPos = yPos + 16; i <= backingAmount; i++, yyPos += 5) {
            scrollPart[scrollerID + 1].drawSprite(xPos, yyPos);
        }
        /* Top of bar */
        scrollPart[scrollerID + 2].drawSprite(xPos, scrollPartPos);
        scrollPartPos += 5;
        /* Middle of bar */
        for (int i = 0; i <= scrollPartAmount; i++) {
            scrollPart[scrollerID + 3].drawSprite(xPos, scrollPartPos);
            scrollPartPos += 5;
        }
        scrollPartPos = (barHeight - 32 - scrollPartHeight) * scrollPos / (contentHeight - barHeight) + 16 + yPos + scrollPartHeight - 5;
        /* Bottom of bar */
        scrollPart[scrollerID].drawSprite(xPos, scrollPartPos);
        /* Arrows */
        if (newScroller) {
            scrollBar[2].drawSprite(xPos, yPos);
            scrollBar[3].drawSprite(xPos, yPos + barHeight - 16);
        } else if (isTransparent) {
            scrollBar[4].drawSprite(xPos, yPos);
            scrollBar[5].drawSprite(xPos, yPos + barHeight - 16);
        } else {
            scrollBar[0].drawSprite(xPos, yPos);
            scrollBar[1].drawSprite(xPos, yPos + barHeight - 16);
        }
    }

    private void drawSplitPrivateChat() {
        if (splitPrivateChat == 0) {
            return;
        }
        TextDrawingArea textDrawingArea = normalText;
        int messages = 0;
        if (systemUpdateTimer != 0) {
            messages += 1;
        }

        if (broadcastMinutes != 0) {
            messages += 1;
        }
        for (int index = 0; index < 100; index++) {
            if (chatMessages[index] != null) {
                int type = chatTypes[index];
                String name = chatNames[index];
                String prefixName = name;
                int rights = 0;
                if (name != null) {
                    if (name.indexOf("@") == 0) {
                        int prefixSubstring = getPrefixSubstringLength(name);
                        name = name.substring(prefixSubstring);
                    }
                    if (name.indexOf("@") == 0) {
                        int prefixSubstring = getPrefixSubstringLength(name);
                        name = name.substring(prefixSubstring);
                    }
                }

                int paddingY = GameFrame.getScreenMode() == ScreenMode.FIXED ? 0 : getScreenHeight() - 500;

                if ((type == 3 || type == 7) && (type == 7 || privateChatMode == 0 || privateChatMode == 1 && isFriendOrSelf(name))) {
                    int yOffset = 329 + paddingY - messages * 13;
                    int xOffset = 4;
                    textDrawingArea.method385(0, "From", yOffset, xOffset);
                    textDrawingArea.method385(ChatArea.SPLIT_CHAT_COLORS[splitChatColor], "From", yOffset - 1, xOffset);
                    xOffset += textDrawingArea.getTextWidth("From ");

                    if (prefixName != null && prefixName.indexOf("@") == 0) {
                        int substringLength = Client.getClient()
                                .getPrefixSubstringLength(prefixName);
                        rights = Client.getClient()
                                .getPrefixRights(prefixName.substring(0, prefixName.indexOf(name)), substringLength == 6);
                    }

                    if (rights != 0) {
                        modIcons[rights].drawTransparentSprite(xOffset, yOffset - 12, 255);
                        xOffset += modIcons[rights].maxWidth + 2;
                    }

                    textDrawingArea.method385(0, name + ": " + chatMessages[index], yOffset, xOffset);
                    textDrawingArea.method385(ChatArea.SPLIT_CHAT_COLORS[splitChatColor], name + ": " + chatMessages[index], yOffset - 1, xOffset);

                    if (++messages >= 5) {
                        return;
                    }
                }

                if (type == 5 && privateChatMode < 2) {
                    int yOffset = 329 + paddingY - messages * 13;
                    textDrawingArea.method385(0, chatMessages[index], yOffset, 4);
                    textDrawingArea.method385(ChatArea.SPLIT_CHAT_COLORS[splitChatColor], chatMessages[index], yOffset - 1, 4);

                    if (++messages >= 5) {
                        return;
                    }
                }

                if (type == 6 && privateChatMode < 2) {
                    int yOffset = 329 + paddingY - messages * 13;
                    int xOffset = 4;
                    textDrawingArea.method385(0, "To", yOffset, xOffset);
                    textDrawingArea.method385(ChatArea.SPLIT_CHAT_COLORS[splitChatColor], "To", yOffset - 1, xOffset);
                    xOffset += textDrawingArea.getTextWidth("To ");

                    if (rights != 0) {
                        modIcons[rights].drawTransparentSprite(xOffset, yOffset - 12, 255);
                        xOffset += 12;
                    }

                    textDrawingArea.method385(0, name + ": " + chatMessages[index], yOffset, xOffset);
                    textDrawingArea.method385(ChatArea.SPLIT_CHAT_COLORS[splitChatColor], name + ": " + chatMessages[index], yOffset - 1, xOffset);

                    if (++messages >= 5) {
                        return;
                    }
                }
            }
        }
    }

    public void updateBankInterface() {
        int tabAmount = settings[2000] & 0xFF;
        int activeTab = settings[2000] >> 8 & 0xFF;
        Sprite activeTabSprite = spritesMap.get(617);
        Sprite tabSprite = spritesMap.get(613);
        Sprite newTabSprite = spritesMap.get(614);
        Sprite emptySprite = new Sprite(0, 0);
        for (int i = 0; i < 9; i++) {
            if (i < tabAmount) {
                RSInterface.interfaceCache[41018 + i].disabledSprite = tabSprite;
                RSInterface.interfaceCache[41018 + i].tooltip = "Click here to select tab " + (i + 1);
            } else {
                RSInterface.interfaceCache[41018 + i].disabledSprite = emptySprite;
                RSInterface.interfaceCache[41018 + i].tooltip = "";
            }
            RSInterface.interfaceCache[41027 + i].inv[0] = -1;
            RSInterface.interfaceCache[41027 + i].invStackSizes[0] = 0;
        }
        if (tabAmount < 9) {
            RSInterface.interfaceCache[41018 + tabAmount].disabledSprite = newTabSprite;
            RSInterface.interfaceCache[41018 + tabAmount].tooltip = "Drag an item here to create a new tab";
        }
        RSInterface.interfaceCache[41018 + activeTab].disabledSprite = activeTabSprite;

    }

    public void processBankInterface() {
        if (openInterfaceID != 5292) {
            return;
        }
        boolean fixed = GameFrame.getScreenMode() == ScreenMode.FIXED;
        int offsetX = fixed ? 0 : (getScreenWidth() - 765) / 2;
        int offsetY = fixed ? 0 : (getScreenHeight() - 503) / 2;
        int[] offsets = {74, 121, 168, 215, 262, 309, 356, 403, 450};
        if (modifiedWidgetId == 5382 && super.mouseY >= 40 + offsetY && super.mouseY <= 77 + offsetY) {
            for (int i = 0; i < offsets.length; i++) {
                if (super.mouseX < offsets[i] + offsetX) {
                   /* getOut().putOpcode(214);
                    getOut().writeSignedBigEndian(5383);
                    getOut().method424(0);
                    getOut().writeSignedBigEndian(anInt1085);
                    getOut().writeUnsignedWordBigEndian(i);*/
                    break;
                }
            }
        }
    }

    private void drawTabArea() {
        switch (GameFrame.getScreenMode()) {
            case FIXED:
                tabArea.setxPos(516);
                tabArea.setyPos(168);
            break;
            case RESIZABLE_CLASSIC:
                tabArea.setxPos(getScreenWidth() - tabArea.getWidth() + 8);
                tabArea.setyPos(getScreenHeight() - tabArea.getHeight() - 1);
                break;
            case RESIZABLE_MODERN:
                tabArea.setxPos(getScreenWidth() - tabArea.getWidth() + 12);
                tabArea.setyPos(getScreenHeight() - tabArea.getHeight() + 2);
                break;
        }
        tabArea.render(this);
    }

    private void drawTooltip() {
        // XXX: here tooltip
        if (menuActionRow < 2 && itemSelected == 0 && spellSelected == 0) {
            if (Configuration.NEW_CURSORS) {
                super.setCursor(CursorData.CURSOR_0);
            }
            return;
        }
        String tooltip;
        if (itemSelected == 1 && menuActionRow < 2) {
            tooltip = "Use " + selectedItemName + (myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7 ? "("+selectedItemId+")" : "") + " with...";
        } else if (spellSelected == 1 && menuActionRow < 2) {
            tooltip = spellTooltip + "...";
        } else {
            tooltip = menuActionName[menuActionRow - 1];
        }
        if (menuActionRow > 2) {
            tooltip = tooltip + "@whi@ / " + (menuActionRow - 2) + " more options";
        }
        newBoldFont.drawBasicString(tooltip, 4, 15, 0xFFFFFF, 0, true);
        // boldText.method390(4, 0xffffff, tooltip, loopCycle / 1000,
        // 15);
        if (Configuration.NEW_CURSORS) {
            detectCursor(menuActionName[menuActionRow - 1]);
        }
    }

    private void dropClient() {
        if (anInt1011 > 0) {
            resetLogout();
            return;
        }
        if (gameScreenIP != null) {
            gameScreenIP.initDrawingArea();
        }
        // TextDrawingArea.fillPixels(2, 229, 39, 0xffffff, 2); // white box around
        // TextDrawingArea.drawPixels(37, 3, 3, 0, 227); // black fill
        // normalText.drawText(0, "Socket lost.", 19, 120);
        // normalText.drawText(0xffffff, "Socket lost.", 18, 119);
        // normalText.drawText(0, "Please wait - attempting to reestablish.", 34, 117);
        // normalText.drawText(0xffffff, "Please wait - attempting to reestablish.", 34,
        // 116);
        spritesMap.get(1105).drawSprite(7, 4);
        if (!resizing && gameScreenIP != null) {
            gameScreenIP.drawGraphics(canvas.getGraphics(), gameScreenDrawX, gameScreenDrawY);
        }
        minimapStatus = 0;
        destinationX = 0;
        Socket rsSocket = getConnection();
        loggedIn = false;
        setLoginFailures(0);
        login(getPassword(), true, myUsername, this);

        if (!loggedIn) {
            resetLogout();
        }

        try {
            rsSocket.close();
        } catch (Exception _ex) {
        }
    }

    public int skillIdForInterfaceId(int id) {
        int[] skillId = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            10, 11, 12, 13, 14, 15
        };
        int[] skillID = {
            ATTACK, STRENGTH, DEFENCE, MAGIC, RANGED, PRAYER, NECROMANCY, SLAYER, BEAST_HUNTER, HITPOINTS,
                MINING, WOODCUTTING, JOURNEYMAN, HERBLORE, ALCHEMY, SOULRENDING
        };
        for (int i = 0; i < skillId.length; i++) {
            if (skillId[i] == id) {
                id = i;
                return skillID[id];
            }
        }
        return 0;
    }


    private int extractInterfaceValues(RSInterface class9, int j) {
        if (class9.valueIndexArray == null || j >= class9.valueIndexArray.length) {
            return -2;
        }

        try {
            int[] ai = class9.valueIndexArray[j];
            int k = 0;
            int l = 0;
            int i1 = 0;

            do {
                int j1 = ai[l++];
                int k1 = 0;
                byte byte0 = 0;
                if (j1 == 0) {
                    return k;
                }
                if (j1 == 1) {
                    k1 = currentStats[skillIdForInterfaceId(ai[l++])];
                }
                if (j1 == 2) {
                    k1 = maxStats[ai[l++]];
                }
                if (j1 == 3) {
                    k1 = currentExp[ai[l++]];
                }
                if (j1 == 4) {
                    RSInterface class9_1 = RSInterface.interfaceCache[ai[l++]];
                    int k2 = ai[l++];
                    if (k2 >= 0 && k2 < ItemDef.totalItems && (!ItemDef.get(k2).membersObject || isMembers)) {
                        for (int j3 = 0; j3 < class9_1.inv.length; j3++) {
                            if (class9_1.inv[j3] == k2 + 1) {
                                k1 += class9_1.invStackSizes[j3];
                            }
                        }

                    }
                }
                if (j1 == 5 || j1 == 111) {
                    k1 = variousSettings[ai[l++]];
                }
                if (j1 == 6) {
                    k1 = anIntArray1019[maxStats[ai[l++]] - 1];
                }
                if (j1 == 7) {
                    k1 = variousSettings[ai[l++]] * 100 / 46875;
                }
                if (j1 == 8) {
                    k1 = myPlayer.combatLevel;
                }
                if (j1 == 9) {
                    for (int l1 = 0; l1 < Skills.SKILL_COUNT; l1++) {
                        if (Skills.SKILLS_ENABLED[l1]) {
                            k1 += maxStats[l1];
                        }
                    }

                }
                if (j1 == 10) {
                    RSInterface class9_2 = RSInterface.interfaceCache[ai[l++]];
                    int l2 = ai[l++] + 1;
                    if (l2 >= 0 && l2 < ItemDef.totalItems && (!ItemDef.get(l2).membersObject || isMembers)) {
                        for (int element : class9_2.inv) {
                            if (element != l2) {
                                continue;
                            }
                            k1 = 0x3b9ac9ff;
                            break;
                        }

                    }
                }
                if (j1 == 11) {
                    k1 = energy;
                }
                if (j1 == 12) {
                    k1 = weight;
                }
                if (j1 == 13) {
                    int i2 = variousSettings[ai[l++]];
                    int i3 = ai[l++];
                    k1 = (i2 & 1 << i3) == 0 ? 0 : 1;
                }
                if (j1 == 14) {
                    int j2 = ai[l++];
                    VarBit varBit = VarBit.cache[j2];
                    int l3 = varBit.configId;
                    int i4 = varBit.configValue;
                    int j4 = varBit.anInt650;
                    int k4 = anIntArray1232[j4 - i4];
                    k1 = variousSettings[l3] >> i4 & k4;
                }
                if (j1 == 15) {
                    byte0 = 1;
                }
                if (j1 == 16) {
                    byte0 = 2;
                }
                if (j1 == 17) {
                    byte0 = 3;
                }
                if (j1 == 18) {
                    k1 = (myPlayer.x >> 7) + regionBaseX;
                }
                if (j1 == 19) {
                    k1 = (myPlayer.y >> 7) + regionBaseY;
                }
                if (j1 == 20) {
                    k1 = ai[l++];
                }
                if (byte0 == 0) {
                    if (i1 == 0) {
                        k += k1;
                    }
                    if (i1 == 1) {
                        k -= k1;
                    }
                    if (i1 == 2 && k1 != 0) {
                        k /= k1;
                    }
                    if (i1 == 3) {
                        k *= k1;
                    }
                    i1 = 0;
                } else {
                    i1 = byte0;
                }
            } while (true);
        } catch (Exception _ex) {
            return -1;
        }
    }

    public void generateWorld(int x, int y) {
        terrainRegionX = x;
        terrainRegionY = y;
        requestMapReconstruct = false;
        if (currentRegionX == x && currentRegionY == y && loadingStage == 2) {
            return;
        }
        currentRegionX = x;
        currentRegionY = y;
        regionBaseX = (currentRegionX - 6) * 8;
        regionBaseY = (currentRegionY - 6) * 8;
        aBoolean1141 = (currentRegionX / 8 == 48 || currentRegionX / 8 == 49) && currentRegionY / 8 == 48;
        if (currentRegionX / 8 == 48 && currentRegionY / 8 == 148) {
            aBoolean1141 = true;
        }
        loadingStage = 1;
        aLong824 = System.currentTimeMillis();
        int k16 = 0;
        for (int i21 = (currentRegionX - 6) / 8; i21 <= (currentRegionX + 6) / 8; i21++) {
            for (int k23 = (currentRegionY - 6) / 8; k23 <= (currentRegionY + 6) / 8; k23++) {
                k16++;
            }
        }
        terrainData = new byte[k16][];
        objectData = new byte[k16][];
        mapCoordinates = new int[k16];
        floorMap = new int[k16];
        objectMap = new int[k16];
        k16 = 0;
        for (int l23 = (currentRegionX - 6) / 8; l23 <= (currentRegionX + 6) / 8; l23++) {
            for (int j26 = (currentRegionY - 6) / 8; j26 <= (currentRegionY + 6) / 8; j26++) {
                mapCoordinates[k16] = (l23 << 8) + j26;
                if (aBoolean1141 && (j26 == 49 || j26 == 149 || j26 == 147 || l23 == 50 || l23 == 49 && j26 == 47)) {
                    floorMap[k16] = -1;
                    objectMap[k16] = -1;
                    k16++;
                } else {
                    int k28 = floorMap[k16] = onDemandFetcher.getMapCount(0, j26, l23);
                    if (k28 != -1) {
                        onDemandFetcher.requestFileData(3, k28);
                    }
                    int j30 = objectMap[k16] = onDemandFetcher.getMapCount(1, j26, l23);
                    if (j30 != -1) {
                        onDemandFetcher.requestFileData(3, j30);
                    }
                    k16++;
                }
            }
        }
        int i17 = regionBaseX - anInt1036;
        int j21 = regionBaseY - anInt1037;
        anInt1036 = regionBaseX;
        anInt1037 = regionBaseY;
        for (int j24 = 0; j24 < 16384; j24++) {
            Npc npc = npcArray[j24];
            if (npc != null) {
                for (int j29 = 0; j29 < 10; j29++) {
                    npc.smallX[j29] -= i17;
                    npc.smallY[j29] -= j21;
                }
                npc.x -= i17 * 128;
                npc.y -= j21 * 128;
            }
        }
        for (int i27 = 0; i27 < getMaxPlayers(); i27++) {
            Player player = playerArray[i27];
            if (player != null) {
                for (int i31 = 0; i31 < 10; i31++) {
                    player.smallX[i31] -= i17;
                    player.smallY[i31] -= j21;
                }
                player.x -= i17 * 128;
                player.y -= j21 * 128;
            }
        }
        aBoolean1080 = true;
        byte byte1 = 0;
        byte byte2 = 104;
        byte byte3 = 1;
        if (i17 < 0) {
            byte1 = 103;
            byte2 = -1;
            byte3 = -1;
        }
        byte byte4 = 0;
        byte byte5 = 104;
        byte byte6 = 1;
        if (j21 < 0) {
            byte4 = 103;
            byte5 = -1;
            byte6 = -1;
        }
        for (int k33 = byte1; k33 != byte2; k33 += byte3) {
            for (int l33 = byte4; l33 != byte5; l33 += byte6) {
                int i34 = k33 + i17;
                int j34 = l33 + j21;
                for (int k34 = 0; k34 < 4; k34++) {
                    if (i34 >= 0 && j34 >= 0 && i34 < 104 && j34 < 104) {
                        groundArray[k34][k33][l33] = groundArray[k34][i34][j34];
                    } else {
                        groundArray[k34][k33][l33] = null;
                    }
                }
            }
        }
        for (SceneSpawnNode sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetFirst(); sceneSpawnNode != null; sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetNext()) {
            sceneSpawnNode.anInt1297 -= i17;
            sceneSpawnNode.anInt1298 -= j21;
            if (sceneSpawnNode.anInt1297 < 0 || sceneSpawnNode.anInt1298 < 0 || sceneSpawnNode.anInt1297 >= 104 || sceneSpawnNode.anInt1298 >= 104) {
                sceneSpawnNode.unlink();
            }
        }
        if (destinationX != 0) {
            destinationX -= i17;
            destinationY -= j21;
        }
        oriented = false;
    }

    @Override
    public AppletContext getAppletContext() {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp.getAppletContext();
        } else {
            return super.getAppletContext();
        }
    }

    private Archive getArchive(int index, String fileName, String cacheArchive, int crc, int loadingBarValue) {
        byte[] buffer = null;
        int timeToWait = 5;

        try {
            if (decompressors[0] != null) {
                buffer = decompressors[0].read(index);
            }
        } catch (Exception _ex) {
        }

        if (buffer != null) {
            if (Configuration.JAGCACHED_ENABLED) {
                crc32Instance.reset();
                crc32Instance.update(buffer);
                int crcValue = (int) crc32Instance.getValue();

                if (crcValue != crc) {
                    buffer = null;
                }
            }
        }

        if (buffer != null) {
            Archive streamLoader = new Archive(buffer);
            return streamLoader;
        }

        int errorCount = 0;

        while (buffer == null) {
            String error = "Unknown error";
            // drawSmoothLoading(loadingBarValue, "Requesting " + fileName);

            try {
                int lastPercentage = 0;
                DataInputStream datainputstream = openJagGrabInputStream(cacheArchive + crc);
                byte[] temp = new byte[6];
                datainputstream.readFully(temp, 0, 6);
                Stream stream = new Stream(temp);
                stream.position = 3;
                int totalLength = stream.getTribyte() + 6;
                int currentLength = 6;
                buffer = new byte[totalLength];
                System.arraycopy(temp, 0, buffer, 0, 6);

                while (currentLength < totalLength) {
                    int remainingAmount = totalLength - currentLength;

                    if (remainingAmount > 1000) {
                        remainingAmount = 1000;
                    }

                    int remaining = datainputstream.read(buffer, currentLength, remainingAmount);

                    if (remaining < 0) {
                        error = "Length error: " + currentLength + "/" + totalLength;
                        throw new IOException("EOF");
                    }

                    currentLength += remaining;
                    int percentage = currentLength * 100 / totalLength;

                    if (percentage != lastPercentage) {
                        // drawSmoothLoading(loadingBarValue, "Loading " + fileName + " - " + percentage
                        // + "%");
                    }

                    lastPercentage = percentage;
                }

                datainputstream.close();

                try {
                    if (decompressors[0] != null) {
                        decompressors[0].write(buffer.length, buffer, index);
                    }
                } catch (Exception _ex) {
                    decompressors[0] = null;
                }

                if (buffer != null) {
                    if (Configuration.JAGCACHED_ENABLED) {
                        crc32Instance.reset();
                        crc32Instance.update(buffer);
                        int currentCrc = (int) crc32Instance.getValue();

                        if (currentCrc != crc) {
                            buffer = null;
                            errorCount++;
                            error = "Checksum error: " + currentCrc;
                        }
                    }
                }
            } catch (IOException ioexception) {
                if (error.equals("Unknown error")) {
                    error = "Socket error";
                }

                buffer = null;
            } catch (NullPointerException _ex) {
                error = "Null error";
                buffer = null;

                if (!Signlink.reporterror) {
                    return null;
                }
            } catch (ArrayIndexOutOfBoundsException _ex) {
                error = "Bounds error";
                buffer = null;

                if (!Signlink.reporterror) {
                    return null;
                }
            } catch (Exception _ex) {
                error = "Unexpected error";
                buffer = null;

                if (!Signlink.reporterror) {
                    return null;
                }
            }
            if (buffer == null) {
                for (int seconds = timeToWait; seconds > 0; seconds--) {
                    if (errorCount >= 3) {
                        // drawSmoothLoading(loadingBarValue, "Game updated - please reload page");
                        seconds = 10;
                    } else {
                        throw new RuntimeException("Unable to find archive: " + name);
                    }

                    try {
                        Thread.sleep(1000L);
                    } catch (Exception _ex) {
                    }
                }

                timeToWait *= 2;

                if (timeToWait > 60) {
                    timeToWait = 60;
                }

                httpFallback = !httpFallback;
            }
        }

        Archive archive = new Archive(buffer);
        return archive;
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL(Configuration.SERVER_HOST() + ":" + (80 + portOff));
        } catch (Exception _ex) {
        }
        return null;
    }

    public String getDocumentBaseHost() {
        return Signlink.mainapp != null ? Signlink.mainapp.getDocumentBase()
                .getHost()
                .toLowerCase() : "";
    }

    private int getExperienceForLevel(int level) {
        int points = 0;
        int output = 0;

        for (int lvl = 1; lvl <= level; lvl++) {
            points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));

            if (lvl >= level) {
                return output;
            }

            output = (int) Math.floor(points / 4);
        }

        return 0;
    }

    @Override
    public Container getGameComponent() {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp;
        }
        return this;
    }

    @Override
    public String getParameter(String s) {
        if (Signlink.mainapp != null) {
            return Signlink.mainapp.getParameter(s);
        } else {
            return super.getParameter(s);
        }
    }

    public String getPrayerBook() {
        return tabInterfaceIDs[5] == 5608 ? "Prayers" : "Curses";
    }

    private String getPrefix(int rights) {
        String prefix = "cr";

        if (rights > 10) {
            prefix = "c";
        }

        return "@" + prefix + rights + "@";
    }

    public int getPrefixSubstringLength(String prefix) {
        if (prefix == null) {
            return 5;
        }
        return prefix.contains("cr10") || prefix.contains("cr11") ? 6 : 5;
    }

    public byte getPrefixRights(String prefix, boolean highRights) {
        byte rights = 0;
        int start = 3;
        int end = highRights ? 5 : 4;
        if (!prefix.contains("cr")) {
            start = 2;
        }
        try {
            rights = (byte) Integer.parseInt(prefix.substring(start, end));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rights;
    }

    public void mouseWheelDragged(int i, int j) {
        if (!mouseWheelDown) {
            return;
        }

        this.cameraRotationLeft += i * 3;
        this.cameraRotationRight += (j << 1);
    }

    private boolean inCircle(int circleX, int circleY, int clickX, int clickY, int radius) {
        return Math.pow(circleX + radius - clickX, 2) + Math.pow(circleY + radius - clickY, 2) < Math.pow(radius, 2);
    }

    @Override
    public void init() {
        try {
            nodeID = 10;
            portOff = 0;
            setHighDetail();
            isMembers = true;
            Signlink.storeid = 32;
            instance = this;
            Signlink.startpriv(InetAddress.getLocalHost());
            GameFrame.setScreenMode(ScreenMode.FIXED);
            initApplet(765, 503);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.exit(1);
        }
    }

    public boolean inSprite(boolean Click, Sprite sprite, int xCoord, int yCoord) {
        if (Click && super.clickMode3 != 1) {
            return false;
        }

        return (Click ? saveClickX : mouseX) >= xCoord && (Click ? saveClickX : mouseX) <= xCoord + sprite.myWidth && (Click ? saveClickY : mouseY) >= yCoord && (Click ? saveClickY : mouseY) <= yCoord + sprite.myHeight;
    }

    private String interfaceIntToString(int j) {
        if (j < 0x3B9AC9FF) {
            return String.valueOf(j);
        } else {
            return "*";
        }
    }

    private boolean isInInterface(RSInterface class9, int childId, int xOff, int yOff) {
        if (class9 == null)
            return false;
        for (int i = 0; i < class9.children.length; i++) {
            RSInterface child = RSInterface.interfaceCache[class9.children[i]];
            int childX = class9.childX[i] + xOff;
            int childY = class9.childY[i] + yOff;
            if (child.id == childId) {
                return super.mouseX >= childX && super.mouseX <= childX + child.width && super.mouseY >= childY && super.mouseY <= childY + child.height;
            }
        }
        return false;
    }

    private boolean interfaceIsSelected(RSInterface class9) {
        if (class9.valueCompareType == null) {
            return false;
        }

        for (int i = 0; i < class9.valueCompareType.length; i++) {
            int j = extractInterfaceValues(class9, i);
            int k = class9.requiredValues[i];

            if (class9.valueCompareType[i] == 2) {
                if (j >= k) {
                    return false;
                }
            } else if (class9.valueCompareType[i] == 3) {
                if (j <= k) {
                    return false;
                }
            } else if (class9.valueCompareType[i] == 4) {
                if (j == k) {
                    return false;
                }
            } else if (j != k) {
                return false;
            }
        }

        return true;
    }

    public boolean isFriendOrSelf(String name) {
        if (name == null) {
            return false;
        }

        if (name.contains("@")) {
            name = NAME_PATTERN.matcher(name).replaceAll("");
        }

        for (int i = 0; i < friendCount; i++) {
            if (name.equalsIgnoreCase(friendsList[i])) {
                return true;
            }
        }

        return name.equalsIgnoreCase(myPlayer.name);
    }

    public boolean isGameFrameVisible() {
        return gameFrameVisible;
    }

    public boolean isWebclient() {
        return super.gameFrame == null;
    }

    private void launchURL(String url) {
        String osName = System.getProperty("os.name");

        try {
            if (osName.startsWith("Mac OS")) {
                Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", String.class);
                openURL.invoke(null, url);
            } else if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else {
                // assume Unix or Linux
                String[] browsers = {"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape", "safari"};
                String browser = null;

                for (int count = 0; count < browsers.length && browser == null; count++) {
                    if (Runtime.getRuntime()
                            .exec(new String[]{"which", browsers[count]})
                            .waitFor() == 0) {
                        browser = browsers[count];
                    }
                }

                if (browser == null) {
                    throw new Exception("Could not find web browser");
                } else {
                    Runtime.getRuntime().exec(new String[]{browser, url});
                }
            }
        } catch (Exception e) {
            pushMessage("Failed to open URL.", 0, "");
        }
    }

    public int highestAmtToLoad = 0;
    private void loadingStages() {
        if (isLowDetail() && loadingStage == 2 && Region.currentPlane != plane) {
            gameScreenIP.initDrawingArea();
            spritesMap.get(1105).drawSprite(8, 9);
            if (!resizing) {
                gameScreenIP.drawGraphics(canvas.getGraphics(), gameScreenDrawX, gameScreenDrawY);
            }
            fadeInStartCycle = loopCycle - 1;
            loadingStage = 1;
            aLong824 = System.currentTimeMillis();
        }

        if (loadingStage == 1) {
            int todo = onDemandFetcher.getRemaining();
            if (todo > highestAmtToLoad) {
                highestAmtToLoad = todo;
            }
            double percentage = (((double) todo / (double) highestAmtToLoad) * 100D);
            if(percentage < 100)
                hiddenEndCycle = loopCycle + 10;
            //normalFont.drawRegularText(false, 180 - 36, 0xc8c8c8, "(" + (100 - (int) percentage) + "%)", 30); don't think you want this, but it draws the loading screen % at top corner if you do
            if(percentage == 100) {
                fadeOutEndCycle = loopCycle + 75;
            }
            if (!resizing) {
                gameScreenIP.drawGraphics(canvas.getGraphics(), gameScreenDrawX, gameScreenDrawY);
            }
            int j = getMapLoadingState();
            if (j != 0 && System.currentTimeMillis() - aLong824 > 0x57e40L) {
                Signlink.reportError(myUsername + " glcfb " + getServerSeed() + "," + j + "," + isLowDetail() + "," + decompressors[0] + "," + onDemandFetcher.getRemaining() + "," + plane + "," + currentRegionX + "," + currentRegionY);
                aLong824 = System.currentTimeMillis();
            }
        }

        if (loadingStage == 2 && plane != getLastKnownPlane()) {
            setLastKnownPlane(plane);
            renderedMapScene(plane);
        }
    }

    private void loadTitleScreen() {
        /*
         * byte abyte0[] = titleStreamLoader.getDataForName("title.dat"); backGround =
         * new Sprite(abyte0, this); aBackground_966 = new Background(titleStreamLoader,
         * "titlebox", 0); aBackground_967 = new Background(titleStreamLoader,
         * "titlebutton", 0); aBackgroundArray1152s = new Background[12]; int j = 0; try
         * { j = Integer.parseInt(getParameter("fl_icon")); } catch (Exception _ex) { }
         * if (j == 0) { for (int k = 0; k < 12; k++) aBackgroundArray1152s[k] = new
         * Background(titleStreamLoader, "runes", k);
         *
         * } else { for (int l = 0; l < 12; l++) aBackgroundArray1152s[l] = new
         * Background(titleStreamLoader, "runes", 12 + (l & 3));
         *
         * } new Sprite(128, 265); new Sprite(128, 265);
         *
         * anIntArray851 = new int[256]; for (int k1 = 0; k1 < 64; k1++)
         * anIntArray851[k1] = k1 * 0x40000;
         *
         * for (int l1 = 0; l1 < 64; l1++) anIntArray851[l1 + 64] = 0xff0000 + 1024 *
         * l1;
         *
         * for (int i2 = 0; i2 < 64; i2++) anIntArray851[i2 + 128] = 0xffff00 + 4 * i2;
         *
         * for (int j2 = 0; j2 < 64; j2++) anIntArray851[j2 + 192] = 0xffffff;
         *
         * anIntArray852 = new int[256]; for (int k2 = 0; k2 < 64; k2++)
         * anIntArray852[k2] = k2 * 1024;
         *
         * for (int l2 = 0; l2 < 64; l2++) anIntArray852[l2 + 64] = 65280 + 4 * l2;
         *
         * for (int i3 = 0; i3 < 64; i3++) anIntArray852[i3 + 128] = 65535 + 0x40000 *
         * i3;
         *
         * for (int j3 = 0; j3 < 64; j3++) anIntArray852[j3 + 192] = 0xffffff;
         *
         * anIntArray853 = new int[256]; for (int k3 = 0; k3 < 64; k3++)
         * anIntArray853[k3] = k3 * 4;
         *
         * for (int l3 = 0; l3 < 64; l3++) anIntArray853[l3 + 64] = 255 + 0x40000 * l3;
         *
         * for (int i4 = 0; i4 < 64; i4++) anIntArray853[i4 + 128] = 0xff00ff + 1024 *
         * i4;
         *
         * for (int j4 = 0; j4 < 64; j4++) anIntArray853[j4 + 192] = 0xffffff;
         *
         * anIntArray1190 = new int[32768]; anIntArray1191 = new int[32768];
         */
        // randomizeBackground(null);
        // drawSmoothLoading(10, "Connecting to fileserver");
    }

    private void loginScreenBG(boolean b) {
        xCameraPos = 6100;
        yCameraPos = 6867;
        zCameraPos = -750;
        xCameraCurve = 2040;
        yCameraCurve = 383;
        resetWorld(0);

        if (b || getScriptManager() == null) {
            setScriptManager(new RS2MapLoginRenderer(this));
        } else {
            getScriptManager().update();
        }

        plane = getScriptManager().regionPlane;
        generateWorld(getScriptManager().terrainRegionX, getScriptManager().terrainRegionY);
        resetWorld(1);
    }

    private void magicOnItem(int id) {
        spellSelected = 1;
        spellID = id;
        selectedSpellId = id;
        spellUsableOn = 16;
        itemSelected = 0;
        spellTooltip = "Cast on";
        tabID = 3;
        tabAreaAltered = true;
    }

    public static final int INTERFACE_ID = 47000;
    public static final int BOXES64 = 28; // 28 * 64 boxes
    private boolean spinClick;
    private int spins;
    private int spinNum;

    public void setSpinClick(boolean spinClick) {
        this.spinClick = spinClick;
    }

    private boolean spin = false;
    private float spinSpeed = 1;

    private void startSpinner() {
        RSInterface w2 = RSInterface.interfaceCache[47020];
        RSInterface reward = RSInterface.interfaceCache[47023];
        RSInterface winnings = RSInterface.interfaceCache[47029];

        if (w2.childX[0] >= -600) {
            w2.childX[0] -= 25;
            w2.childX[1] -= 25;
        }
        if (w2.childX[0] >= -1512 && w2.childX[0] <= -601) {
            w2.childX[0] -= (25 / spinSpeed);
            w2.childX[1] -= (25 / spinSpeed);
            spinSpeed = spinSpeed + 0.07f;
        }
        if (w2.childX[0] >= -1600 && w2.childX[0] < -1503) {
            w2.childX[0] -= (25 / spinSpeed);
            w2.childX[1] -= (25 / spinSpeed);
            spinSpeed = spinSpeed + 2f;
        }
        if (w2.childX[0] <= -1503) {
            spin = false;
        }
    }

    private void shift(RSInterface items, RSInterface boxes, int shiftAmount) {
        items.childX[0] -= shiftAmount;
        for (int i = 0; i < BOXES64; i++) {
            boxes.childX[i] -= shiftAmount;
        }
        spins++;
    }

    private void spinComplete() {
        // Reset
        spins = 0;
        spinClick = false;
        spinNum++;
        // Notify server: spin complete
        getOut().putOpcode(145);
        getOut().putOpcode(696969);
        getOut().putOpcode(0);
        getOut().putOpcode(0);
    }

    private int lastBrightness = -1;

    private void mainGameProcessor() {
        if (openInterfaceID == 24600 && !getGrandExchange().searching && interfaceButtonAction != 1558 && interfaceButtonAction != 1557 && inputDialogState != 1 && inputDialogState != 4) {
            inputDialogState = 0;
        }
        if (systemUpdateTimer > 1) {
            systemUpdateTimer--;
        }

        if (broadcastMinutes >= 1) {
            broadcastMinutes--;
        }

        if (anInt1011 > 0) {
            anInt1011--;
        }

        for (int j = 0; j < 100; j++) {
            if (!parsePacket()) {
                break;
            }
        }

        if (!loggedIn) {
            return;
        }

     /*   onUpdate();*/

        newmapsopening.spin();

        upgradeInterfaceSpinner.shift();

        if (((vengTimer != -1)) && (System.currentTimeMillis() - lastUpdate > 1000L)) {
            if (vengTimer != -1) {
                vengTimer -= 1;
            }
            lastUpdate = System.currentTimeMillis();
        }

        if (anInt1016 > 0) {
            anInt1016--;
        }

        if (super.keyArray[1] == 1 || super.keyArray[2] == 1 || super.keyArray[3] == 1 || super.keyArray[4] == 1) {
            aBoolean1017 = true;
        }

        if (aBoolean1017 && anInt1016 <= 0) {
            anInt1016 = 20;
            aBoolean1017 = false;
        }

        if (super.awtFocus && !aBoolean954) {
            aBoolean954 = true;
            getOut().putOpcode(3);
            getOut().putByte(1);
        }

        if (!super.awtFocus && aBoolean954) {
            aBoolean954 = false;
            getOut().putOpcode(3);
            getOut().putByte(0);
        }

        loadingStages();
        method115();
        method90();
        anInt1009++;

        if (anInt1009 > 750) {
            dropClient();
        }

        if (TaskManager.getTaskAmount() > 0) {
            TaskManager.sequence();
        }

        updatePlayerInstances();
        readNPCUpdateBlockForced();
        processTextCycles();

        if (tabID >= 0 && tabInterfaceIDs[tabID] == 29322) {
            if (!PetSystem.isPetAnimationRunning) {
                PetSystem.updateAnimations();
            }
        }

        anInt945++;

        if (crossType != 0) {
            crossIndex += 20;

            if (crossIndex >= 400) {
                crossType = 0;
            }
        }

        if (atInventoryInterfaceType != 0) {
            atInventoryLoopCycle++;

            if (atInventoryLoopCycle >= 15) {
                if (atInventoryInterfaceType == 3) {
                    setInputTaken(true);
                }
                atInventoryInterfaceType = 0;
            }
        }

        if (activeInterfaceType != 0) {
            anInt989++;

            if (super.mouseX > anInt1087 + 5 || super.mouseX < anInt1087 - 5 || super.mouseY > anInt1088 + 5 || super.mouseY < anInt1088 - 5) {
                aBoolean1242 = true;
            }

            if (super.getClickMode2() == 0) {
                if (activeInterfaceType == 3) {
                    setInputTaken(true);
                }

                activeInterfaceType = 0;

                if (aBoolean1242 && anInt989 >= 15) {
                    bankItemDragSprite = null;
                    lastActiveInvInterface = -1;
                    processRightClick();
                    processBankInterface();

                    if (lastActiveInvInterface == modifiedWidgetId && mouseInvInterfaceIndex == selectedInventorySlot) {
                        if (menuActionRow > 0) {
                            doAction(menuActionRow - 1);
                        }
                    }

                    bankItemDragSprite = null;
                    int x = GameFrame.isFixed() ? 0 : (getScreenWidth() - 765) / 2;
                    int y = GameFrame.isFixed() ? 0 : (getScreenHeight() - 503) / 2;
                    if (modifiedWidgetId == 5382) {// check
                        // if
                        // bank
                        // interface
                        if (isInInterface(RSInterface.interfaceCache[5292], 27014, x, y)) {// tab
                            // 1
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(5);// 5 = maintab
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27015, x, y)) {// tab 2
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(13);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27016, x, y)) {// tab 3
                            if (RSInterface.interfaceCache[27015].inv[0] < 1)
                                return;
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(26);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27017, x, y)) {// tab 4
                            if (RSInterface.interfaceCache[27016].inv[0] < 1)
                                return;
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(39);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27018, x, y)) {// tab 5
                            if (RSInterface.interfaceCache[27017].inv[0] < 1)
                                return;
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(52);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27019, x, y)) {// tab 6
                            if (RSInterface.interfaceCache[27018].inv[0] < 1)
                                return;
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(65);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27020, x, y)) {// tab 7
                            if (RSInterface.interfaceCache[27019].inv[0] < 1)
                                return;
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(78);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27021, x, y)) {// tab 8
                            if (RSInterface.interfaceCache[27020].inv[0] < 1)
                                return;
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(91);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                        if (isInInterface(RSInterface.interfaceCache[5292], 27022, x, y)) {// tab 9
                            if (RSInterface.interfaceCache[27021].inv[0] < 1)
                                return;
                            getOut().putOpcode(214);
                            getOut().writeSignedBigEndian(104);// tab # x 13 (originally
                            // movewindow)
                            getOut().method424(0);
                            getOut().writeSignedBigEndian(selectedInventorySlot);// Selected item slot
                            getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);// unused

                        }
                    }

                    if (lastActiveInvInterface == modifiedWidgetId && mouseInvInterfaceIndex != selectedInventorySlot) {
                        RSInterface class9 = RSInterface.interfaceCache[modifiedWidgetId];
                        int j1 = 0;
                        if (anInt913 == 1 && class9.contentType == 206) {
                            j1 = 1;
                        }
                        if (class9.inv[mouseInvInterfaceIndex] <= 0) {
                            j1 = 0;
                        }
                        if (class9.dragDeletes) {
                            int l2 = selectedInventorySlot;
                            int l3 = mouseInvInterfaceIndex;
                            class9.inv[l3] = class9.inv[l2];
                            class9.invStackSizes[l3] = class9.invStackSizes[l2];
                            class9.inv[l2] = -1;
                            class9.invStackSizes[l2] = 0;
                        } else if (j1 == 1) {
                            int i3 = selectedInventorySlot;
                            for (int i4 = mouseInvInterfaceIndex; i3 != i4; ) {
                                if (i3 > i4) {
                                    class9.swapInventoryItems(i3, i3 - 1);
                                    i3--;
                                } else if (i3 < i4) {
                                    class9.swapInventoryItems(i3, i3 + 1);
                                    i3++;
                                }
                            }

                        } else {
                            class9.swapInventoryItems(selectedInventorySlot, mouseInvInterfaceIndex);
                        }
                        getOut().putOpcode(214);
                        getOut().writeSignedBigEndian(modifiedWidgetId);
                        getOut().method424(j1);
                        getOut().writeSignedBigEndian(selectedInventorySlot);
                        getOut().writeUnsignedWordBigEndian(mouseInvInterfaceIndex);
                    }
                } else if ((anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2) {
                    determineMenuSize();
                } else if (menuActionRow > 0) {
                    doAction(menuActionRow - 1);
                }

                atInventoryLoopCycle = 10;
                super.clickMode3 = 0;
            }
        }

        if (Scene.clickedTileX != -1) {
            int k = Scene.clickedTileX;
            int k1 = Scene.clickedTileY;
          //  if(BossOverlay.clickOverlay(mouseX,mouseY)) {
            //    Scene.clickedTileX = -1;
            //    return;
          // }
            if ((myRights == 4 || myRights == 5 || myRights == 8 || myRights == 7) && controlShiftTeleporting) {
                teleport(regionBaseX + k, regionBaseY + k1);
                Scene.clickedTileX = -1;
            } else {
                boolean flag = doWalkTo(0, 0, 0, 0, myPlayer.smallY[0], 0, 0, k1, myPlayer.smallX[0], true, k);
                Scene.clickedTileX = -1;

                if (flag) {
                    crossX = super.saveClickX;
                    crossY = super.saveClickY;
                    crossType = 1;
                    crossIndex = 0;
                }
            }
        }

        if (super.clickMode3 == 1 && aString844 != null) {
            aString844 = null;
            setInputTaken(true);
            super.clickMode3 = 0;
        }

        if (!processMenuClick()) {
            processMainScreenClick();
            tabArea.processTabClick(this, GameFrame.getScreenMode());
        }

        if (super.getClickMode2() == 1 || super.clickMode3 == 1) {
            anInt1213++;
        }

        if (anInt1500 != 0 || anInt1044 != 0 || anInt1129 != 0) {
            if (anInt1501 < 30 && !menuOpen) {
                if (++anInt1501 == 30) {
                    if (anInt1500 != 0) {
                        updateChatArea = true;
                    }
                }
            }
        } else if (anInt1501 > 0) {
            anInt1501--;
        }
        // System.out.println(":anInt150: "+anInt1500);

        if (PlayerUtilities.quedBalloonX > -1 && PlayerUtilities.quedBalloonY > -1) {
            int x = (myPlayer.x >> 7) + regionBaseX;
            int y = (myPlayer.y >> 7) + regionBaseY;

            if (PlayerUtilities.quedBalloonX == x && PlayerUtilities.quedBalloonY == y) {
                getOut().putOpcode(159);
                getOut().putShort(PlayerUtilities.quedBalloonX);
                getOut().putShort(PlayerUtilities.quedBalloonY);
                PlayerUtilities.quedBalloonX = PlayerUtilities.quedBalloonY = -1;
            }
        }

        if (loadingStage == 2) {
            method108();
        }

        if (loadingStage == 2 && oriented) {
            calcCameraPos();
        }

        for (int i1 = 0; i1 < 5; i1++) {
            QuakeTimes[i1]++;
        }

        manageTextInput();
        super.idleTime++;

        if (super.idleTime > 9000) {
            anInt1011 = 250;
            super.idleTime = 0;
            getOut().putOpcode(202);
        }

        if (cameraOffsetX < -50) {
        }

        if (cameraOffsetX > 50) {
        }

        if (cameraOffsetY < -55) {
        }

        if (cameraOffsetY > 55) {
        }

        if (viewRotationOffset < -40) {
        }

        if (viewRotationOffset > 40) {
        }

        if (minimapRotation < -60) {
        }

        if (minimapRotation > 60) {
        }

        if (minimapZoom < -20) {
        }

        if (minimapZoom > 10) {
        }

        if (++anInt1010 > 50) {
            getOut().putOpcode(0);
        }

        try {
            if (getConnection() != null && getOut().position > 0) {
                getConnection().queueBytes(getOut().position, getOut().buffer);
                getOut().position = 0;
                anInt1010 = 0;
            }
        } catch (IOException _ex) {
            dropClient();
        } catch (Exception exception) {
            resetLogout();
        }
    }

    public void markMinimap(Sprite sprite, int x, int y) {
        if (sprite == null) {
            return;
        }
        boolean fixed = GameFrame.getScreenMode() == ScreenMode.FIXED;
        int rotation = cameraRotation + minimapRotation & 0x7ff;
        int xPadding = mapArea.getOffSetX();
        int yPadding = mapArea.getyPos();
        int distance = x * x + y * y;

        if (distance > 5000) {
            return;
        }

        int spriteX = Rasterizer3D.SINE[rotation];
        int spriteY = Rasterizer3D.COSINE[rotation];
        spriteX = spriteX * 256 / (minimapZoom + 256);
        spriteY = spriteY * 256 / (minimapZoom + 256);
        int drawX = y * spriteX + x * spriteY >> 16;
        int drawY = y * spriteY - x * spriteX >> 16;
        int finalX = (!fixed ? 83 : 104) + drawX - sprite.maxWidth / 2 + 4;
        int finalY = (!fixed ? 85 : 89) - drawY - sprite.maxHeight / 2 - 4;

        try {
            sprite.drawSprite(finalX + xPadding, finalY + yPadding);
        } catch (Exception _ex) {
            _ex.printStackTrace();
        }
    }

    private boolean menuHasAddFriend(int j) {
        if (j < 0) {
            return false;
        }

        int k = menuActionID[j];

        if (k >= 2000) {
            k -= 2000;
        }

        return k == 337;
    }

    private void appendFocusDest(Actor actor) {
        if (actor.anInt1504 == 0) {
            return;
        }
        if (actor.interactingEntity != -1 && actor.interactingEntity < 32768) {
            try {
                Npc npc = npcArray[actor.interactingEntity];

                if (npc != null) {
                    int i1 = actor.x - npc.x;
                    int k1 = actor.y - npc.y;

                    if (i1 != 0 || k1 != 0) {
                        actor.turnDirection = (int) (Math.atan2(i1, k1) * 325.94900000000001D) & 0x7ff;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        if (actor.interactingEntity >= 32768) {
            int j = actor.interactingEntity - 32768;
            if (j == playerId) {
                j = myPlayerIndex;
            }
            Player player = playerArray[j];
            if (player != null) {
                int l1 = actor.x - player.x;
                int i2 = actor.y - player.y;
                if (l1 != 0 || i2 != 0) {
                    actor.turnDirection = (int) (Math.atan2(l1, i2) * 325.94900000000001D) & 0x7ff;
                }
            }
        }

        if ((actor.anInt1538 != 0 || actor.anInt1539 != 0) && (actor.smallXYIndex == 0 || actor.anInt1503 > 0)) {
            int k = actor.x - (actor.anInt1538 - regionBaseX - regionBaseX) * 64;
            int j1 = actor.y - (actor.anInt1539 - regionBaseY - regionBaseY) * 64;

            if (k != 0 || j1 != 0) {
                actor.turnDirection = (int) (Math.atan2(k, j1) * 325.94900000000001D) & 0x7ff;
            }

            actor.anInt1538 = 0;
            actor.anInt1539 = 0;
        }

        int l = actor.turnDirection - actor.anInt1552 & 0x7ff;

        if (l != 0) {
            if (l < actor.anInt1504 || l > 2048 - actor.anInt1504) {
                actor.anInt1552 = actor.turnDirection;
            } else if (l > 1024) {
                actor.anInt1552 -= actor.anInt1504;
            } else {
                actor.anInt1552 += actor.anInt1504;
            }

            actor.anInt1552 &= 0x7ff;

            if (actor.anInt1517 == actor.anInt1511 && actor.anInt1552 != actor.turnDirection) {
                if (actor.anInt1512 != -1) {
                    actor.anInt1517 = actor.anInt1512;
                    return;
                }

                actor.anInt1517 = actor.anInt1554;
            }
        }
    }

    private void appendAnimation(Actor actor) {
        try {
            actor.aBoolean1541 = false;

            if (actor.anInt1517 != -1) {
                if (actor.anInt1517 > AnimationDefinition.cache.length) {
                    actor.anInt1517 = 0;
                }
                AnimationDefinition animation = AnimationDefinition.cache[actor.anInt1517];
                actor.frameDelay++;

                if (actor.currentForcedAnimFrame < animation.frameCount && actor.frameDelay > animation.getFrameLength(actor.currentForcedAnimFrame)) {
                    actor.frameDelay = 1; // this is the frame delay. 0 is what it's normally at. higher number =
                    // faster animations.
                    actor.currentForcedAnimFrame++;
                    actor.nextIdleAnimationFrame++;
                }
                actor.nextIdleAnimationFrame = actor.currentForcedAnimFrame + 1;
                if (actor.nextIdleAnimationFrame >= animation.frameCount) {
                    if (actor.nextIdleAnimationFrame >= animation.frameCount) {
                        actor.nextIdleAnimationFrame = 0;
                    }
                }
                if (actor.currentForcedAnimFrame >= animation.frameCount) {
                    actor.frameDelay = 1;
                    actor.currentForcedAnimFrame = 0;
                }
            }

            if (actor.gfxId != -1 && loopCycle >= actor.graphicDelay) {
                if (actor.currentAnim < 0) {
                    actor.currentAnim = 0;
                }

                AnimationDefinition animation_1 = AnimatedGraphic.cache[actor.gfxId].animation;
                for (actor.animCycle++; actor.currentAnim < animation_1.frameCount && actor.animCycle > animation_1.getFrameLength(actor.currentAnim); actor.currentAnim++) {
                    actor.animCycle -= animation_1.getFrameLength(actor.currentAnim);
                }

                if (actor.currentAnim >= animation_1.frameCount ) {

                    if (actor instanceof Player && ModelUtil.keepPlaying) {
                        actor.currentAnim = 0;
                    } else
                        actor.gfxId = -1;
                }
                actor.nextGraphicsAnimationFrame = actor.currentAnim + 1;
                if (actor.nextGraphicsAnimationFrame >= animation_1.frameCount) {
                    if (actor instanceof Player && ModelUtil.keepPlaying) {
                        //  System.out.println("Im here fella ight");
                        actor.nextGraphicsAnimationFrame = 0;
                    } else
                        actor.gfxId = -1;
                }
            }

            if (actor.anim != -1 && actor.animationDelay <= 1) {
                AnimationDefinition animation_2 = AnimationDefinition.cache[actor.anim];

                if (animation_2.resetWhenWalk == 1 && actor.anInt1542 > 0 && actor.anInt1547 <= loopCycle && actor.anInt1548 < loopCycle) {
                    actor.animationDelay = 1;
                    return;
                }
            }

            if (actor.anim != -1 && actor.animationDelay == 0) {
                AnimationDefinition animation_3 = AnimationDefinition.cache[actor.anim];

                for (actor.anInt1528++; actor.currentAnimFrame < animation_3.frameCount && actor.anInt1528 > animation_3.getFrameLength(actor.currentAnimFrame); actor.currentAnimFrame++) {
                    actor.anInt1528 -= animation_3.getFrameLength(actor.currentAnimFrame);
                }

                if (actor.currentAnimFrame >= animation_3.frameCount) {
                    actor.currentAnimFrame -= animation_3.loopDelay;
                    actor.anInt1530++;

                    if (actor.anInt1530 >= animation_3.frameStep) {
                        actor.anim = -1;
                    }

                    if (actor.currentAnimFrame < 0 || actor.currentAnimFrame >= animation_3.frameCount) {
                        actor.anim = -1;
                    }
                }
                actor.nextAnimationFrame = actor.currentAnimFrame + 1;
                if (actor.nextAnimationFrame >= animation_3.frameCount) {
                    if (actor.anInt1530 >= animation_3.frameStep) {
                        actor.anim = -1;
                    }
                    actor.anim = -1;
                }
                actor.aBoolean1541 = animation_3.oneSquareAnimation;
            }

            if (actor.animationDelay > 0) {
                actor.animationDelay--;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processAnimableObjects() {
        AnimatedGameObject class30_sub2_sub4_sub3 = (AnimatedGameObject) getIncompleteAnimables().reverseGetFirst();

        for (; class30_sub2_sub4_sub3 != null; class30_sub2_sub4_sub3 = (AnimatedGameObject) getIncompleteAnimables().reverseGetNext()) {
            if (class30_sub2_sub4_sub3.anInt1560 != plane || class30_sub2_sub4_sub3.aBoolean1567) {
                class30_sub2_sub4_sub3.unlink();
            } else if (loopCycle >= class30_sub2_sub4_sub3.anInt1564) {
                class30_sub2_sub4_sub3.method454(anInt945);

                if (class30_sub2_sub4_sub3.aBoolean1567) {
                    class30_sub2_sub4_sub3.unlink();
                } else {
                    scene.method285(class30_sub2_sub4_sub3.anInt1560, 0, class30_sub2_sub4_sub3.anInt1563, -1, class30_sub2_sub4_sub3.anInt1562, 60, class30_sub2_sub4_sub3.anInt1561, class30_sub2_sub4_sub3, false);
                }
            }
        }
    }

    private void readPlayerUpdateMask(int bitmap, int j, Stream buffer, Player player) {
        if ((bitmap & 0x400) != 0) {
            player.anInt1543 = buffer.method428();
            player.anInt1545 = buffer.method428();
            player.anInt1544 = buffer.method428();
            player.anInt1546 = buffer.method428();
            player.anInt1547 = buffer.getShortBigEndianA() + loopCycle;
            player.anInt1548 = buffer.method435() + loopCycle;
            player.anInt1549 = buffer.method428();
            player.method446();
        }

        if ((bitmap & 0x100) != 0) {
            player.gfxId = buffer.getShortBigEndian(); // GFX Id
            int k = buffer.getIntLittleEndian();
            player.graphicHeight = k >> 16;
            player.graphicDelay = loopCycle + (k & 0xffff);
            player.currentAnim = 0;
            player.animCycle = 0;

            if (player.graphicDelay > loopCycle && !ModelUtil.keepPlaying) {
                player.currentAnim = -1;
            }

            if (player.graphicDelay > loopCycle) {
                player.currentAnim = -1;
            }

            if (player.gfxId == 65535) {
                player.gfxId = -1;
            }
        }

        if ((bitmap & 8) != 0) {
            int l = buffer.getShortBigEndian();

            if (l == 65535) {
                l = -1;
            }

            int i2 = buffer.method427();

            if (l == player.anim && l != -1) {
                int i3 = AnimationDefinition.cache[l].delayType;

                if (i3 == 1) {
                    player.currentAnimFrame = 0;
                    player.anInt1528 = 0;
                    player.animationDelay = i2;
                    player.anInt1530 = 0;
                }

                if (i3 == 2) {
                    player.anInt1530 = 0;
                }
            } else if (l == -1 || player.anim == -1 || AnimationDefinition.cache[l].forcedPriority >= AnimationDefinition.cache[player.anim].forcedPriority) {
                player.anim = l;
                player.currentAnimFrame = 0;
                player.anInt1528 = 0;
                player.animationDelay = i2;
                player.anInt1530 = 0;
                player.anInt1542 = player.smallXYIndex;
            }
        }

        if ((bitmap & 4) != 0) {
            player.textSpoken = buffer.getString();

            if (player.textSpoken.charAt(0) == '~') {
                player.textSpoken = player.textSpoken.substring(1);
                pushMessage(player.textSpoken, 2, player.name);
            } else if (player == myPlayer) {
                pushMessage(player.textSpoken, 2, player.name);
            }

            player.anInt1513 = 0;
            player.anInt1531 = 0;
            player.textCycle = 150;
        }

        if ((bitmap & 0x80) != 0) {
            int effects = buffer.getShortBigEndian();
            int rights = buffer.getUnsignedByte();
            int ironman = buffer.getUnsignedByte();
            int diff = buffer.getUnsignedByte();
            int chatTextSize = buffer.method427();
            int currentOffset = buffer.position;

            if (player.name != null && player.visible) {
                long l3 = TextUtilities.longForName(player.name);
                boolean flag = false;

                if (rights <= 1) {
                    for (int i = 0; i < ignoreCount; i++) {
                        if (ignoreListAsLongs[i] != l3) {
                            continue;
                        }

                        flag = true;
                        break;
                    }
                }

                if (!flag && anInt1251 == 0) {
                    try {
                        aStream_834.position = 0;
                        buffer.method442(chatTextSize, 0, aStream_834.buffer);
                        aStream_834.position = 0;
                        String s = ChatEncoder.readChatboxText(chatTextSize, aStream_834);
                        player.textSpoken = s;
                        player.anInt1513 = effects >> 8;
                        // player.privilegeLevel = j2;
                        player.anInt1531 = effects & 0xff;
                        player.textCycle = 150;
                        if (Configuration.HIGHLIGHT_USERNAME) {
                            pushMessage(s.replace(myPlayer.name.toLowerCase(), "<col=ff0000>" + myPlayer.name.toLowerCase() + "</col>")
                                    .replace(myPlayer.name, "<col=ff0000>" + myPlayer.name + "</col>"), 2, player.name, player.loyaltyTitle, player.loyaltyColor, player.loyaltyPosition, (byte) rights, (byte) ironman, (byte) diff);
                        } else {
                            pushMessage(s, 2, player.name, player.loyaltyTitle, player.loyaltyColor, player.loyaltyPosition, (byte) rights, (byte) ironman, (byte) diff);
                        }
                    } catch (Exception exception) {
                        Signlink.reportError("cde2");
                    }
                }
            }

            buffer.position = currentOffset + chatTextSize;
        }

        if ((bitmap & 1) != 0) {
            player.interactingEntity = buffer.getShortBigEndian();

            if (player.interactingEntity == 65535) {
                player.interactingEntity = -1;
            }
        }

        if ((bitmap & 0x10) != 0) {
            int j1 = buffer.method427();
            byte[] abyte0 = new byte[j1];
            Stream stream_1 = new Stream(abyte0);
            buffer.getBytes(j1, 0, abyte0);
            buffer.increaseCapacity(1);
            getaStreamArray895s()[j] = stream_1;
            player.updatePlayer(stream_1);
        }

        if ((bitmap & 2) != 0) {
            player.anInt1538 = buffer.getShortBigEndianA();
            player.anInt1539 = buffer.getShortBigEndian();
        }

        if ((bitmap & 0x20) != 0) {

            int k1 = getInputBuffer().getByteA();
            int k2 = buffer.getUnsignedByte();
            int icon = buffer.getUnsignedByte();
            int soakDamage = getInputBuffer().getByteA();
            player.updateHitData(k2, k1, loopCycle, icon, soakDamage);
            player.loopCycleStatus = loopCycle + 300;
            player.currentHealth = getInputBuffer().getByteA();
            player.maxHealth = getInputBuffer().getByteA();
        }

        if ((bitmap & 0x200) != 0) {
            int l1 = buffer.getByteA();
            int l2 = buffer.getUnsignedByte();
            int icon = buffer.getUnsignedByte();
            int soakDamage = buffer.getByteA();
            player.updateHitData(l2, l1, loopCycle, icon, soakDamage);
            player.loopCycleStatus = loopCycle + 300;
            player.currentHealth = buffer.getByteA();
            player.maxHealth = buffer.getByteA();
        }
    }

    private void method108() {
        try {
            int cameraDisplayX = myPlayer.x + cameraOffsetX;
            int cameraDisplayY = myPlayer.y + cameraOffsetY;

            if (currentCameraDisplayX - cameraDisplayX < -500 || currentCameraDisplayX - cameraDisplayX > 500 || currentCameraDisplayY - cameraDisplayY < -500 || currentCameraDisplayY - cameraDisplayY > 500) {
                currentCameraDisplayX = cameraDisplayX;
                currentCameraDisplayY = cameraDisplayY;
            }

            if (currentCameraDisplayX != cameraDisplayX) {
                currentCameraDisplayX += (cameraDisplayX - currentCameraDisplayX) / 16;
            }

            if (currentCameraDisplayY != cameraDisplayY) {
                currentCameraDisplayY += (cameraDisplayY - currentCameraDisplayY) / 16;
            }

            if (super.keyArray[1] == 1) {
                cameraRotationLeft += (-24 - cameraRotationLeft) / 2;
            } else if (super.keyArray[2] == 1) {
                cameraRotationLeft += (24 - cameraRotationLeft) / 2;
            } else {
                cameraRotationLeft /= 2;
            }

            if (super.keyArray[3] == 1) {
                cameraRotationRight += (12 - cameraRotationRight) / 2;
            } else if (super.keyArray[4] == 1) {
                cameraRotationRight += (-12 - cameraRotationRight) / 2;
            } else {
                cameraRotationRight /= 2;
            }

            cameraRotation = cameraRotation + cameraRotationLeft / 2 & 0x7ff;
            cameraRotationZ += cameraRotationRight / 2;

            if (GameFrame.getScreenMode() == ScreenMode.FIXED && cameraRotationZ < 137) {
                cameraRotationZ = 137;
            }

            if (GameFrame.getScreenMode() == ScreenMode.FIXED && cameraRotationZ > 383) {
                cameraRotationZ = 383;
            }

            if (GameFrame.getScreenMode() != ScreenMode.FIXED && cameraRotationZ < 150) {
                cameraRotationZ = 150;
            }

            if (GameFrame.getScreenMode() != ScreenMode.FIXED && cameraRotationZ > 383) {
                cameraRotationZ = 383;
            }

            int l = currentCameraDisplayX >> 7;
            int i1 = currentCameraDisplayY >> 7;
            int j1 = method42(plane, currentCameraDisplayY, currentCameraDisplayX);
            int k1 = 0;

            if (l > 3 && i1 > 3 && l < 100 && i1 < 100) {
                for (int l1 = l - 4; l1 <= l + 4; l1++) {
                    for (int k2 = i1 - 4; k2 <= i1 + 4; k2++) {
                        int l2 = plane;

                        if (l2 < 3 && (tileFlags[1][l1][k2] & 2) == 2) {
                            l2++;
                        }

                        int i3 = j1 - tileHeights[l2][l1][k2];

                        if (i3 > k1) {
                            k1 = i3;
                        }
                    }
                }
            }

            anInt1005++;

            if (anInt1005 > 1512) {
                anInt1005 = 0;
                getOut().putOpcode(77);
                getOut().putByte(0);
                int i2 = getOut().position;
                getOut().putByte((int) (Math.random() * 256D));
                getOut().putByte(101);
                getOut().putByte(233);
                getOut().putShort(45092);

                if ((int) (Math.random() * 2D) == 0) {
                    getOut().putShort(35784);
                }

                getOut().putByte((int) (Math.random() * 256D));
                getOut().putByte(64);
                getOut().putByte(38);
                getOut().putShort((int) (Math.random() * 65536D));
                getOut().putShort((int) (Math.random() * 65536D));
                getOut().putVariableSizeByte(getOut().position - i2);
            }

            int j2 = k1 * 192;

            if (j2 > 0x17f00) {
                j2 = 0x17f00;
            }
            if (j2 < 32768) {
                j2 = 32768;
            }
            if (j2 > curveChangeY) {
                curveChangeY += (j2 - curveChangeY) / 24;
                return;
            }
            if (j2 < curveChangeY) {
                curveChangeY += (j2 - curveChangeY) / 80;
            }
        } catch (Exception _ex) {
            Signlink.reportError("glfc_ex " + myPlayer.x + "," + myPlayer.y + "," + currentCameraDisplayX + "," + currentCameraDisplayY + "," + currentRegionX + "," + currentRegionY + "," + regionBaseX + "," + regionBaseY);
            throw new RuntimeException("eek");
        }
    }

    private void updatePlayerInstances() {
        for (int i = -1; i < playerCount; i++) {
            int j;

            if (i == -1) {
                j = getMyPlayerIndex();
            } else {
                j = playerIndices[i];
            }

            Player player = playerArray[j];

            if (player != null) {
                entityUpdateBlock(player);
            }
        }
    }

    private void method115() {
        if (loadingStage == 2) {
            for (SceneSpawnNode sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetFirst(); sceneSpawnNode != null; sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetNext()) {
                if (sceneSpawnNode.anInt1294 > 0) {
                    sceneSpawnNode.anInt1294--;
                }
                if (sceneSpawnNode.anInt1294 == 0) {
                    if (sceneSpawnNode.anInt1299 < 0 || Region.method178(sceneSpawnNode.anInt1299, sceneSpawnNode.anInt1301)) {
                        method142(sceneSpawnNode.anInt1298, sceneSpawnNode.anInt1295, sceneSpawnNode.anInt1300, sceneSpawnNode.anInt1301, sceneSpawnNode.anInt1297, sceneSpawnNode.anInt1296, sceneSpawnNode.anInt1299);
                        sceneSpawnNode.unlink();
                    }
                } else {
                    if (sceneSpawnNode.anInt1302 > 0) {
                        sceneSpawnNode.anInt1302--;
                    }
                    if (sceneSpawnNode.anInt1302 == 0 && sceneSpawnNode.anInt1297 >= 1 && sceneSpawnNode.anInt1298 >= 1 && sceneSpawnNode.anInt1297 <= 102 && sceneSpawnNode.anInt1298 <= 102 && (sceneSpawnNode.anInt1291 < 0 || Region.method178(sceneSpawnNode.anInt1291, sceneSpawnNode.anInt1293))) {
                        method142(sceneSpawnNode.anInt1298, sceneSpawnNode.anInt1295, sceneSpawnNode.anInt1292, sceneSpawnNode.anInt1293, sceneSpawnNode.anInt1297, sceneSpawnNode.anInt1296, sceneSpawnNode.anInt1291);
                        sceneSpawnNode.anInt1302 = -1;
                        if (sceneSpawnNode.anInt1291 == sceneSpawnNode.anInt1299 && sceneSpawnNode.anInt1299 == -1) {
                            sceneSpawnNode.unlink();
                        } else if (sceneSpawnNode.anInt1291 == sceneSpawnNode.anInt1299 && sceneSpawnNode.anInt1292 == sceneSpawnNode.anInt1300 && sceneSpawnNode.anInt1293 == sceneSpawnNode.anInt1301) {
                            sceneSpawnNode.unlink();
                        }
                    }
                }
            }

        }
    }

    private void updatePlayerMovement(Stream stream) {
        stream.initBitAccess();
        int j = stream.getBits(1);
        if (j == 0) {
            return;
        }
        int k = stream.getBits(2);
        if (k == 0) {
            playersToUpdate[playersToUpdateCount++] = getMyPlayerIndex();
            return;
        }
        if (k == 1) {
            int l = stream.getBits(3);
            myPlayer.moveInDir(false, l);
            int k1 = stream.getBits(1);
            if (k1 == 1) {
                playersToUpdate[playersToUpdateCount++] = getMyPlayerIndex();
            }
            return;
        }
        if (k == 2) {
            int i1 = stream.getBits(3);
            myPlayer.moveInDir(true, i1);
            int l1 = stream.getBits(3);
            myPlayer.moveInDir(true, l1);
            int j2 = stream.getBits(1);
            if (j2 == 1) {
                playersToUpdate[playersToUpdateCount++] = getMyPlayerIndex();
            }
            return;
        }
        if (k == 3) {
            plane = stream.getBits(2);
            if (getLastKnownPlane() != plane) {
                loadingStage = 1;
            }
            setLastKnownPlane(plane);
            int j1 = stream.getBits(1);
            int i2 = stream.getBits(1);
            if (i2 == 1) {
                playersToUpdate[playersToUpdateCount++] = getMyPlayerIndex();
            }
            int k2 = stream.getBits(7);
            int l2 = stream.getBits(7);
            myPlayer.setPos(l2, k2, j1 == 1);
        }
    }

    private boolean processInterfaceAnimation(int i, int j) {
        boolean flag1 = false;
        if (j < 0 || j > RSInterface.interfaceCache.length) {
            return false;
        }
        RSInterface class9 = RSInterface.interfaceCache[j];
        if (class9 == null) {
            return false;
        }
        if (class9.children == null) {
            return false;
        }
        for (int element : class9.children) {
            if (element == -1) {
                break;
            }
            RSInterface class9_1 = RSInterface.interfaceCache[element];
            if (class9_1 == null)
                continue;
            if (class9_1.type == 1) {
                flag1 |= processInterfaceAnimation(i, class9_1.id);
            }
            if (class9_1.type == 6 && (class9_1.disabledAnimationId != -1 || class9_1.enabledAnimationId != -1)) {
                boolean flag2 = interfaceIsSelected(class9_1);
                int l;
                if (flag2) {
                    l = class9_1.enabledAnimationId;
                } else {
                    l = class9_1.disabledAnimationId;
                }
                if (l != -1) {
                    AnimationDefinition animation = AnimationDefinition.cache[l];
                    for (class9_1.anInt208 += i; class9_1.anInt208 > animation.getFrameLength(class9_1.anInt246); ) {
                        class9_1.anInt208 -= animation.getFrameLength(class9_1.anInt246) + 1;
                        class9_1.anInt246++;
                        if (class9_1.anInt246 >= animation.frameCount) {
                            class9_1.anInt246 -= animation.loopDelay;
                            if (class9_1.anInt246 < 0 || class9_1.anInt246 >= animation.frameCount) {
                                class9_1.anInt246 = 0;
                            }
                        }
                        flag1 = true;
                    }

                }
            }
        }

        return flag1;
    }

    private int getRenderHeight() {
        int j = 3;
        if (yCameraCurve < 310) {
            int k = xCameraPos >> 7;
            int l = yCameraPos >> 7;
            int i1 = myPlayer.x >> 7;
            int j1 = myPlayer.y >> 7;

            if (k < 0 || l < 0 || k >= 104 || l >= 104) {
                return plane;
            }

            if (i1 < 0 || j1 < 0 || i1 >= 104 || j1 >= 104) {
                return plane;
            }

            try {
                if ((tileFlags[plane][k][l] & 4) != 0) {
                    j = plane;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                //System.err.println(plane + ", " + k + ", " + l);
            }
            int k1;
            if (i1 > k) {
                k1 = i1 - k;
            } else {
                k1 = k - i1;
            }
            int l1;
            if (j1 > l) {
                l1 = j1 - l;
            } else {
                l1 = l - j1;
            }
            if (k1 > l1) {
                int i2 = l1 * 0x10000 / k1;
                int k2 = 32768;
                while (k != i1) {
                    if (k < i1) {
                        k++;
                    } else if (k > i1) {
                        k--;
                    }
                    try {
                        if ((tileFlags[plane][k][l] & 4) != 0) {
                            j = plane;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        //System.err.println(plane + ", " + k + ", " + l);
                    }
                    k2 += i2;
                    if (k2 >= 0x10000) {
                        k2 -= 0x10000;
                        if (l < j1) {
                            l++;
                        } else if (l > j1) {
                            l--;
                        }
                        try {
                            if ((tileFlags[plane][k][l] & 4) != 0) {
                                j = plane;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            //System.err.println(plane + ", " + k + ", " + l);
                        }
                    }
                }
            } else {
                if (l1 == 0) // /zero crash fix
                    l1 = 1;
                int j2 = k1 * 0x10000 / l1;
                int l2 = 32768;
                while (l != j1) {
                    if (l < j1) {
                        l++;
                    } else if (l > j1) {
                        l--;
                    }
                    try {
                        if ((tileFlags[plane][k][l] & 4) != 0) {
                            j = plane;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        //System.err.println(plane + ", " + k + ", " + l);
                    }
                    l2 += j2;
                    if (l2 >= 0x10000) {
                        l2 -= 0x10000;
                        if (k < i1) {
                            k++;
                        } else if (k > i1) {
                            k--;
                        }
                        try {
                            if ((tileFlags[plane][k][l] & 4) != 0) {
                                j = plane;
                            }
                        } catch (ArrayIndexOutOfBoundsException e) {
                            //System.err.println(plane + ", " + k + ", " + l);
                        }
                    }
                }
            }
        }
        try {
            if ((tileFlags[plane][myPlayer.x >> 7][myPlayer.y >> 7] & 4) != 0) {
                j = plane;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            //System.err.println(plane + ", " + (myPlayer.x >> 7) + ", " + (myPlayer.y >> 7));
        }
        return j;
    }

    private int getHighestPlane() {
        int j = method42(plane, yCameraPos, xCameraPos);
        int x = xCameraPos >> 7;
        int y = yCameraPos >> 7;

        if (x < 0 || y < 0 || x >= 104 || y >= 104) {
            return plane;
        }

        if (j - zCameraPos < 800 && (tileFlags[plane][x][y] & 4) != 0) {
            return plane;
        } else {
            return 3;
        }
    }

    private void method130(int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2) {
        SceneSpawnNode sceneSpawnNode = null;
        for (SceneSpawnNode sceneSpawnNode_1 = (SceneSpawnNode) getaClass19_1179().reverseGetFirst(); sceneSpawnNode_1 != null; sceneSpawnNode_1 = (SceneSpawnNode) getaClass19_1179().reverseGetNext()) {
            if (sceneSpawnNode_1.anInt1295 != l1 || sceneSpawnNode_1.anInt1297 != i2 || sceneSpawnNode_1.anInt1298 != j1 || sceneSpawnNode_1.anInt1296 != i1) {
                continue;
            }
            sceneSpawnNode = sceneSpawnNode_1;
            break;
        }

        if (sceneSpawnNode == null) {
            sceneSpawnNode = new SceneSpawnNode();
            sceneSpawnNode.anInt1295 = l1;
            sceneSpawnNode.anInt1296 = i1;
            sceneSpawnNode.anInt1297 = i2;
            sceneSpawnNode.anInt1298 = j1;
            method89(sceneSpawnNode);
            getaClass19_1179().insertHead(sceneSpawnNode);
        }
        sceneSpawnNode.anInt1291 = k;
        sceneSpawnNode.anInt1293 = k1;
        sceneSpawnNode.anInt1292 = l;
        sceneSpawnNode.anInt1302 = j2;
        sceneSpawnNode.anInt1294 = j;
    }

    private void updatePlayer(Stream stream) {
        int j = stream.getBits(8);
        if (j < playerCount) {
            for (int k = j; k < playerCount; k++) {
                anIntArray840[anInt839++] = playerIndices[k];
            }

        }
        if (j > playerCount) {
            Signlink.reportError(myUsername + " Too many players");
            throw new RuntimeException("eek");
        }
        playerCount = 0;
        for (int l = 0; l < j; l++) {
            int i1 = playerIndices[l];
            Player player = playerArray[i1];
            int j1 = stream.getBits(1);
            if (j1 == 0) {
                playerIndices[playerCount++] = i1;
                player.loopCycle = loopCycle;
            } else {
                int k1 = stream.getBits(2);
                if (k1 == 0) {
                    playerIndices[playerCount++] = i1;
                    player.loopCycle = loopCycle;
                    playersToUpdate[playersToUpdateCount++] = i1;
                } else if (k1 == 1) {
                    playerIndices[playerCount++] = i1;
                    player.loopCycle = loopCycle;
                    int l1 = stream.getBits(3);
                    player.moveInDir(false, l1);
                    int j2 = stream.getBits(1);
                    if (j2 == 1) {
                        playersToUpdate[playersToUpdateCount++] = i1;
                    }
                } else if (k1 == 2) {
                    playerIndices[playerCount++] = i1;
                    player.loopCycle = loopCycle;
                    int i2 = stream.getBits(3);
                    player.moveInDir(true, i2);
                    int k2 = stream.getBits(3);
                    player.moveInDir(true, k2);
                    int l2 = stream.getBits(1);
                    if (l2 == 1) {
                        playersToUpdate[playersToUpdateCount++] = i1;
                    }
                } else if (k1 == 3) {
                    anIntArray840[anInt839++] = i1;
                }
            }
        }
    }

    private void renderGroundItemNames() {
        if (ClientSettings.groundItemNames == 1) {
            for (int x = 0; x < 104; x++) {
                for (int y = 0; y < 104; y++) {
                    NodeList node = groundArray[plane][x][y]; // groundarray confirmt because ground items were showing
                    // with fucked raster pos
                    int offset = -13;
                    if (node != null) {
                        for (Item item = (Item) node.getFirst(); item != null; item = (Item) node.getNext()) {
                            ItemDef itemDef = ItemDef.get(item.id);
                            calcEntityScreenPos((x << 7) + 64, 64, (y << 7) + 64);
                            newSmallFont.drawCenteredString(itemDef.name + (item.amount > 1 ? " (" + intToKOrMil(item.amount) + ")" : ""), spriteDrawX, spriteDrawY - offset, 0xffffff, 1);
                            offset += 12;
                        }
                    }
                }
            }
        }
    }

    private void parseEntityPacket(Stream stream, int j) {
        if (j == 84) {
            int k = stream.getUnsignedByte();
            int j3 = anInt1268 + (k >> 4 & 7);
            int i6 = anInt1269 + (k & 7);
            int l8 = stream.getUnsignedShort();
            int k11 = stream.getUnsignedShort();
            int l13 = stream.getUnsignedShort();
            if (j3 >= 0 && i6 >= 0 && j3 < 104 && i6 < 104) {
                NodeList class19_1 = groundArray[plane][j3][i6];
                if (class19_1 != null) {
                    for (Item class30_sub2_sub4_sub2_3 = (Item) class19_1.reverseGetFirst(); class30_sub2_sub4_sub2_3 != null; class30_sub2_sub4_sub2_3 = (Item) class19_1.reverseGetNext()) {
                        if (class30_sub2_sub4_sub2_3.id != (l8 & 0x7fff) || class30_sub2_sub4_sub2_3.amount != k11) {
                            continue;
                        }
                        class30_sub2_sub4_sub2_3.amount = l13;
                        break;
                    }

                    spawnGroundItem(j3, i6);
                }
            }
            return;
        }
        if (j == 105) {
            int l = stream.getUnsignedByte();
            int k3 = anInt1268 + (l >> 4 & 7);
            int j6 = anInt1269 + (l & 7);
            int i9 = stream.getUnsignedShort();
            int l11 = stream.getUnsignedByte();
            int i14 = l11 >> 4 & 0xf;
            int i16 = l11 & 7;
            if (myPlayer.smallX[0] >= k3 - i14 && myPlayer.smallX[0] <= k3 + i14 && myPlayer.smallY[0] >= j6 - i14 && myPlayer.smallY[0] <= j6 + i14 && soundEffectVolume != 0 && aBoolean848 && !isLowDetail() && soundCount < 50) {
                sound[soundCount] = i9;
                soundType[soundCount] = i16;
                soundDelay[soundCount] = Sounds.anIntArray326[i9];
                aClass26Array1468[soundCount] = null;
                soundCount++;
            }
        }
        if (j == 215) {
            int i1 = stream.method435();
            int l3 = stream.method428();
            int k6 = anInt1268 + (l3 >> 4 & 7);
            int j9 = anInt1269 + (l3 & 7);
            int i12 = stream.method435();
            int j14 = stream.getUnsignedShort();
            if (k6 >= 0 && j9 >= 0 && k6 < 104 && j9 < 104 && i12 != playerId) {
                Item class30_sub2_sub4_sub2_2 = new Item();
                class30_sub2_sub4_sub2_2.id = i1;
                class30_sub2_sub4_sub2_2.amount = j14;
                if (groundArray[plane][k6][j9] == null) {
                    groundArray[plane][k6][j9] = new NodeList();
                }
                groundArray[plane][k6][j9].insertHead(class30_sub2_sub4_sub2_2);
                spawnGroundItem(k6, j9);
            }
            return;
        }
        if (j == 156) {
            int j1 = stream.method426();
            int i4 = anInt1268 + (j1 >> 4 & 7);
            int l6 = anInt1269 + (j1 & 7);
            int k9 = stream.getUnsignedShort();
            if (i4 >= 0 && l6 >= 0 && i4 < 104 && l6 < 104) {
                NodeList class19 = groundArray[plane][i4][l6];
                if (class19 != null) {
                    for (Item item = (Item) class19.reverseGetFirst(); item != null; item = (Item) class19.reverseGetNext()) {
                        if (item.id != (k9 & 0x7fff)) {
                            continue;
                        }
                        item.unlink();
                        break;
                    }

                    if (class19.reverseGetFirst() == null) {
                        groundArray[plane][i4][l6] = null;
                    }
                    spawnGroundItem(i4, l6);
                }
            }
            return;
        }
        if (j == 160) {
            int k1 = stream.method428();
            int j4 = anInt1268 + (k1 >> 4 & 7);
            int i7 = anInt1269 + (k1 & 7);
            int l9 = stream.method428();
            int j12 = l9 >> 2;
            int k14 = l9 & 3;
            int objectType = anIntArray1177[j12];
            int j17 = stream.method435();
            if (j4 >= 0 && i7 >= 0 && j4 < 103 && i7 < 103) {
                int j18 = tileHeights[plane][j4][i7];
                int i19 = tileHeights[plane][j4 + 1][i7];
                int l19 = tileHeights[plane][j4 + 1][i7 + 1];
                int k20 = tileHeights[plane][j4][i7 + 1];
                if (objectType == 0) {
                    WallObject class10 = scene.method296(plane, j4, i7);
                    if (class10 != null) {
                        int k21 = class10.uid >> 14 & 0x7fff;
                        if (j12 == 2) {
                            class10.aClass30_Sub2_Sub4_278 = new GameObject(k21, 4 + k14, 2, i19, l19, j18, k20, j17, false);
                            class10.aClass30_Sub2_Sub4_279 = new GameObject(k21, k14 + 1 & 3, 2, i19, l19, j18, k20, j17, false);
                        } else {
                            class10.aClass30_Sub2_Sub4_278 = new GameObject(k21, k14, j12, i19, l19, j18, k20, j17, false);
                        }
                    }
                }

                if (objectType == 1) {
                    DecorativeObject class26 = scene.method297(j4, i7, plane);
                    if (class26 != null) {
                        class26.aClass30_Sub2_Sub4_504 = new GameObject(class26.uid >> 14 & 0x7fff, 0, 4, i19, l19, j18, k20, j17, false);
                    }
                }

                if (objectType == 2) {
                    org.necrotic.client.scene.tile.GameObject class28 = scene.method298(j4, i7, plane);

                    if (j12 == 11) {
                        j12 = 10;
                    }

                    if (class28 != null) {
                        class28.aClass30_Sub2_Sub4_521 = new org.necrotic.client.media.renderable.GameObject(class28.uid >> 14 & 0x7fff, k14, j12, i19, l19, j18, k20, j17, false);
                    }
                }

                if (objectType == 3) {
                    GroundObject class49 = scene.method299(i7, j4, plane);

                    if (class49 != null) {
                        class49.aClass30_Sub2_Sub4_814 = new org.necrotic.client.media.renderable.GameObject(class49.uid >> 14 & 0x7fff, k14, 22, i19, l19, j18, k20, j17, false);
                    }
                }
            }

            return;
        }

        if (j == 147) {
            int l1 = stream.method428();
            int k4 = anInt1268 + (l1 >> 4 & 7);
            int j7 = anInt1269 + (l1 & 7);
            int i10 = stream.getUnsignedShort();
            byte byte0 = stream.method430();
            int l14 = stream.getShortBigEndian();
            byte byte1 = stream.method429();
            int k17 = stream.getUnsignedShort();
            int k18 = stream.method428();
            int j19 = k18 >> 2;
            int i20 = k18 & 3;
            int l20 = anIntArray1177[j19];
            byte byte2 = stream.getSignedByte();
            int l21 = stream.getUnsignedShort();
            byte byte3 = stream.method429();
            Player player;

            if (i10 == playerId) {
                player = myPlayer;
            } else {
                player = playerArray[i10];
            }

            if (player != null) {
                ObjectDef class46 = ObjectDef.forID(l21);
                int i22 = tileHeights[plane][k4][j7];
                int j22 = tileHeights[plane][k4 + 1][j7];
                int k22 = tileHeights[plane][k4 + 1][j7 + 1];
                int l22 = tileHeights[plane][k4][j7 + 1];
                Model model = class46.modelAt(j19, i20, i22, j22, k22, l22, -1);

                if (model != null) {
                    method130(k17 + 1, -1, 0, l20, j7, 0, plane, k4, l14 + 1);
                    player.anInt1707 = l14 + loopCycle;
                    player.anInt1708 = k17 + loopCycle;
                    player.aModel_1714 = model;
                    int i23 = class46.width;
                    int j23 = class46.length;

                    if (i20 == 1 || i20 == 3) {
                        i23 = class46.length;
                        j23 = class46.width;
                    }

                    player.anInt1711 = k4 * 128 + i23 * 64;
                    player.anInt1713 = j7 * 128 + j23 * 64;
                    player.anInt1712 = method42(plane, player.anInt1713, player.anInt1711);

                    if (byte2 > byte0) {
                        byte byte4 = byte2;
                        byte2 = byte0;
                        byte0 = byte4;
                    }

                    if (byte3 > byte1) {
                        byte byte5 = byte3;
                        byte3 = byte1;
                        byte1 = byte5;
                    }

                    player.anInt1719 = k4 + byte2;
                    player.anInt1721 = k4 + byte0;
                    player.anInt1720 = j7 + byte3;
                    player.anInt1722 = j7 + byte1;
                }
            }
        }

        if (j == 151) {
            int i2 = stream.method426();
            int l4 = anInt1268 + (i2 >> 4 & 7);
            int k7 = anInt1269 + (i2 & 7);
            int objectId = stream.readInt();
            int k12 = stream.method428();
            int i15 = k12 >> 2;
            int k16 = k12 & 3;
            int l17 = anIntArray1177[i15];
            if (objectId < -1) {
                objectId = 0;
            }

            if (l4 >= 0 && k7 >= 0 && l4 < 104 && k7 < 104) {
                method130(-1, objectId, k16, l17, k7, i15, plane, l4, 0);
            }

            return;
        }

        if (j == 4) {
            int j2 = stream.getUnsignedByte();
            int i5 = anInt1268 + (j2 >> 4 & 7);
            int l7 = anInt1269 + (j2 & 7);
            int k10 = stream.getUnsignedShort();
            int l12 = stream.getUnsignedByte();
            int j15 = stream.getUnsignedShort();

            if (i5 >= 0 && l7 >= 0 && i5 < 104 && l7 < 104) {
                i5 = i5 * 128 + 64;
                l7 = l7 * 128 + 64;
                AnimatedGameObject class30_sub2_sub4_sub3 = new AnimatedGameObject(plane, loopCycle, j15, k10, method42(plane, l7, i5) - l12, l7, i5);
                getIncompleteAnimables().insertHead(class30_sub2_sub4_sub3);
            }

            return;
        }

        if (j == 44) {
            int k2 = stream.getShortBigEndianA();
            long j5 = stream.getLong(); // stream.getUnsignedShort();
            int i8 = stream.getUnsignedByte();
            int l10 = anInt1268 + (i8 >> 4 & 7);
            int i13 = anInt1269 + (i8 & 7);

            if (l10 >= 0 && i13 >= 0 && l10 < 104 && i13 < 104) {
                Item class30_sub2_sub4_sub2_1 = new Item();
                class30_sub2_sub4_sub2_1.id = k2;
                class30_sub2_sub4_sub2_1.amount = (int) j5;

                if (groundArray[plane][l10][i13] == null) {
                    groundArray[plane][l10][i13] = new NodeList();
                }

                groundArray[plane][l10][i13].insertHead(class30_sub2_sub4_sub2_1);
                spawnGroundItem(l10, i13);
            }

            return;
        }

        if (j == 101) {
            int l2 = stream.method427();
            int k5 = l2 >> 2;
            int j8 = l2 & 3;
            int i11 = anIntArray1177[k5];
            int j13 = stream.getUnsignedByte();
            int k15 = anInt1268 + (j13 >> 4 & 7);
            int l16 = anInt1269 + (j13 & 7);

            if (k15 >= 0 && l16 >= 0 && k15 < 104 && l16 < 104) {
                method130(-1, -1, j8, i11, l16, k5, plane, k15, 0);
            }

            return;
        }

        if (j == 117) {
            int i3 = stream.getUnsignedByte();
            int l5 = anInt1268 + (i3 >> 4 & 7);
            int k8 = anInt1269 + (i3 & 7);
            int j11 = l5 + stream.getSignedByte();
            int k13 = k8 + stream.getSignedByte();
            int l15 = stream.getSignedShort();
            int i17 = stream.getUnsignedShort();
            int i18 = stream.getUnsignedByte() * 4;
            int l18 = stream.getUnsignedByte() * 4;
            int k19 = stream.getUnsignedShort();
            int j20 = stream.getUnsignedShort();
            int i21 = stream.getUnsignedByte();
            int j21 = stream.getUnsignedByte();

            if (l5 >= 0 && k8 >= 0 && l5 < 104 && k8 < 104 && j11 >= 0 && k13 >= 0 && j11 < 104 && k13 < 104 && i17 != 65535) {
                l5 = l5 * 128 + 64;
                k8 = k8 * 128 + 64;
                j11 = j11 * 128 + 64;
                k13 = k13 * 128 + 64;
                Projectile class30_sub2_sub4_sub4 = new Projectile(i21, l18, k19 + loopCycle, j20 + loopCycle, j21, plane, method42(plane, k8, l5) - i18, k8, l5, l15, i17);
                class30_sub2_sub4_sub4.method455(k19 + loopCycle, k13, method42(plane, k13, j11) - l18, j11);
                getProjectiles().insertHead(class30_sub2_sub4_sub4);
            }
        }
    }

    private void updateNPCAmount(Stream stream) {
        stream.initBitAccess();
        int npcAmt = stream.getBits(8);
        if (npcAmt < npcCount) {
            for (int l = npcAmt; l < npcCount; l++) {
                anIntArray840[anInt839++] = npcIndices[l];
            }

        }
        if (npcAmt > npcCount) {
            System.out.println(myUsername + " Too many npcs");
            throw new RuntimeException("eek");
        }
        npcCount = 0;
        lastNpcAmt = npcAmt;
        for (int i1 = 0; i1 < npcAmt; i1++) {
            int j1 = npcIndices[i1];
            Npc npc = npcArray[j1];
            int k1 = stream.getBits(1);
            if (k1 == 0) {
                npcIndices[npcCount++] = j1;
                npc.loopCycle = loopCycle;
            } else {
                int l1 = stream.getBits(2);
                if (l1 == 0) {
                    npcIndices[npcCount++] = j1;
                    npc.loopCycle = loopCycle;
                    playersToUpdate[playersToUpdateCount++] = j1;
                } else if (l1 == 1) {
                    npcIndices[npcCount++] = j1;
                    npc.loopCycle = loopCycle;
                    int i2 = stream.getBits(3);
                    npc.moveInDir(false, i2);
                    int k2 = stream.getBits(1);
                    if (k2 == 1) {
                        playersToUpdate[playersToUpdateCount++] = j1;
                    }
                } else if (l1 == 2) {
                    npcIndices[npcCount++] = j1;
                    npc.loopCycle = loopCycle;
                    int j2 = stream.getBits(3);
                    npc.moveInDir(true, j2);
                    int l2 = stream.getBits(3);
                    npc.moveInDir(true, l2);
                    int i3 = stream.getBits(1);
                    if (i3 == 1) {
                        playersToUpdate[playersToUpdateCount++] = j1;
                    }
                } else if (l1 == 3) {
                    anIntArray840[anInt839++] = j1;
                }
            }
        }

    }

    private void method142(int i, int j, int k, int l, int i1, int j1, int k1) {
        if (i1 >= 1 && i >= 1 && i1 <= 102 && i <= 102) {
            if (isLowDetail() && j != plane) {
                return;
            }

            int i2 = 0;

            if (j1 == 0) {
                i2 = scene.method300(j, i1, i);
            }

            if (j1 == 1) {
                i2 = scene.method301(j, i1, i);
            }

            if (j1 == 2) {
                i2 = scene.method302(j, i1, i);
            }

            if (j1 == 3) {
                i2 = scene.method303(j, i1, i);
            }

            if (i2 != 0) {
                int i3 = scene.fetchObjectIDTagForPosition(j, i1, i, i2);
                int j2 = i2 >> 14 & 0x7fff;
                int k2 = i3 & 0x1f;
                int l2 = i3 >> 6;

                if (j1 == 0) {
                    scene.method291(i1, j, i, (byte) -119);
                    ObjectDef class46 = ObjectDef.forID(j2);

                    if (class46.solid) {
                        collisionData[j].method215(l2, k2, class46.impenetrable, i1, i);
                    }
                }

                if (j1 == 1) {
                    scene.method292(i, j, i1);
                }

                if (j1 == 2) {
                    scene.method293(j, i1, i);
                    ObjectDef class46_1 = ObjectDef.forID(j2);

                    if (i1 + class46_1.width > 103 || i + class46_1.width > 103 || i1 + class46_1.length > 103 || i + class46_1.length > 103) {
                        return;
                    }

                    if (class46_1.solid) {
                        collisionData[j].method216(l2, class46_1.width, i1, i, class46_1.length, class46_1.impenetrable);
                    }
                }

                if (j1 == 3) {
                    scene.method294(j, i, i1);
                    ObjectDef class46_2 = ObjectDef.forID(j2);

                    if (class46_2.solid && class46_2.interactive) {
                        collisionData[j].method218(i, i1);
                    }
                }
            }

            if (k1 >= 0) {
                int j3 = j;

                if (j3 < 3 && (tileFlags[1][i1][i] & 2) == 2) {
                    j3++;
                }

                if (j == 4) {
                    j = 0;
                }
                Region.method188(scene, k, i, l, j3, collisionData[j], tileHeights, i1, k1, j);
            }
        }
    }

    private void renderParticles() {
        Iterator<Particle> it = currentParticles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            if (p != null) {
                p.tick();
                if (p.isDead()) {
                    deadParticles.add(p);
                } else {
                    int x = p.getPosition().getX();
                    int y = p.getPosition().getY();
                    int z = p.getPosition().getZ();
                    int alpha = (int) (p.getAlpha() * 255.0F);
                    float size = p.getSize();

                    int[] projection = particleProjection(x, y, z);

                    TextDrawingArea.fillCircle(projection[0], projection[1], projection[2], (int) (size * 4), p.getColor(), alpha);
                }
            }
        }

        currentParticles.removeAll(deadParticles);
        deadParticles.clear();
    }

    private int[] particleProjection(int i, int j, int l) {
        if (i < 128 || l < 128 || i > 13056 || l > 13056) {
            return new int[]{-1, -1, -1, -1, -1, -1, -1};
        }
        int i1 = method42(plane, l, i) - j;
        i -= xCameraPos;
        i1 -= zCameraPos;
        l -= yCameraPos;
        int j1 = Rasterizer3D.SINE[yCameraCurve];
        int k1 = Rasterizer3D.COSINE[yCameraCurve];
        int l1 = Rasterizer3D.SINE[xCameraCurve];
        int i2 = Rasterizer3D.COSINE[xCameraCurve];
        int j2 = l * l1 + i * i2 >> 16;
        l = l * i2 - i * l1 >> 16;
        i = j2;
        j2 = i1 * k1 - l * j1 >> 16;
        l = i1 * j1 + l * k1 >> 16;
        if (l >= 50) {
            return new int[]{Rasterizer3D.textureInt1 + (i << viewDistance) / l, Rasterizer3D.textureInt2 + (j2 << viewDistance) / l, l, Rasterizer3D.textureInt1 + (i / 2 << viewDistance) / l, Rasterizer3D.textureInt2 + (j2 / 2 << viewDistance) / l, Rasterizer3D.textureInt1 + (i / 2 << viewDistance) / l, Rasterizer3D.textureInt2 + (j2 / 2 << viewDistance) / l};
        } else {
            return new int[]{-1, -1, -1, -1, -1, -1, -1};
        }
    }

    private void render() {
        renderCycle++;
    /*    ItemDef.renderCycleItems = renderCycle;
        ItemDef.testName();*/
        int j = 0;
        int cameraX = xCameraPos;
        int cameraZ = zCameraPos;
        int cameraY = yCameraPos;
        int yCurve = yCameraCurve;
        int xCurve = xCameraCurve;

        processPlayerAdditions(true);
        processNpcAdditions(true);
        processPlayerAdditions(false);
        processNpcAdditions(false);
        processProjectiles();
        processAnimableObjects();

        if (!oriented) {
            int roll = cameraRotationZ;

            if (curveChangeY / 256 > roll) {
                roll = curveChangeY / 256;
            }

            if (QuakeDirectionActive[4] && QuakeAmplitudes[4] + 128 > roll) {
                roll = QuakeAmplitudes[4] + 128;
            }

            int orientation = cameraRotation + viewRotationOffset & 0x7ff;
            int zoom = roll + (Configuration.TOGGLE_FOV ? 1200 : 650) - getScreenHeight() / 400;
            zoom += cameraZoom;
            setCameraPos(cameraZoom + roll * ((Scene.viewDistance == 9) && (GameFrame.getScreenMode() == ScreenMode.RESIZABLE_CLASSIC) ? 2 : Scene.viewDistance == 10 ? 5 : 3), roll, currentCameraDisplayX,
                    method42(plane, myPlayer.y, myPlayer.x) - 100, orientation, currentCameraDisplayY);
           // System.out.println(roll);
               /*setCameraPos(
                		GameFrame.getScreenMode() == ScreenMode.FIXED ? 600 + roll * 3 
                				+ cameraZoom : getScreenWidth() >= 1024 ? zoom : 450
                						+ roll * 3 + cameraZoom, roll,
                						currentCameraDisplayX, 
                						method42(plane, myPlayer.y, myPlayer.x) - 50, 
                						orientation, currentCameraDisplayY);*/
        }

        if (!oriented) {
            j = getRenderHeight();
        } else {
            j = getHighestPlane();
        }

        for (int i2 = 0; i2 < 5; i2++) {
            if (QuakeDirectionActive[i2]) {
                int j2 = (int) ((Math.random() * (double) (QuakeMagnitudes[i2] * 2 + 1) - (double) QuakeMagnitudes[i2])
                        + Math.sin((double) QuakeTimes[i2] * ((double) Quake4PiOverPeriods[i2] / 100D))
                        * (double) QuakeAmplitudes[i2]);
                if (i2 == 0) {
                    xCameraPos += j2;
                }
                if (i2 == 1) {
                    zCameraPos += j2;
                }
                if (i2 == 2) {
                    yCameraPos += j2;
                }
                if (i2 == 3) {
                    xCameraCurve = xCameraCurve + j2 & 0x7ff;
                }
                if (i2 == 4) {
                    yCameraCurve += j2;
                    if (yCameraCurve < 128) {
                        yCameraCurve = 128;
                    }
                    if (yCameraCurve > 383) {
                        yCameraCurve = 383;
                    }
                }
            }
        }
        Scene.viewDistance = 9;
        int k2 = Rasterizer3D.colourCount;
        Model.objectExists = true;
        Model.objectsRendered = 0;
        Model.currentCursorX = super.mouseX - 4;
        Model.currentCursorY = super.mouseY - 4;


        scene.render(xCameraPos, yCameraPos, xCameraCurve, zCameraPos, j, yCameraCurve);
        drawFadeTransition();
        Rasterizer3D.drawFog(ClientSettings.fogColor, 3500, 5300);
        //Fog.draw(ClientSettings.fogColor);
       // if (ClientSettings.toggleParticles == 1) {
           // renderParticles();
       // }

            /*if (Configuration.DEPTH_BUFFER && Configuration.FOG_ENABLED) {
                Fog.draw(0xC8C0A8);
            }*/
            /*if (Configuration.FOG_ENABLED) {
                Rasterizer3D.drawFog(0xc8c0a8, 2800, 3300);
            } else {
                Rasterizer3D.drawFog(0xc8c0a8, 7700, 7700);

            }*/


        Scene.viewDistance = 9;
        scene.clearObj5Cache();
        updateEntities();
        drawTimers();
        drawHeadIcon();

        // method37(k2, 1);
        //if (!isLowDetail())
        animateTextures();
        // drawModIcon(myPlayer);

        if (GameFrame.getScreenMode() != ScreenMode.FIXED && loggedIn) {
            drawTabArea();
            drawChatArea();
            drawMinimap();
        }

        if (loggedIn) {
            renderGroundItemNames();
            draw3dScreen();
            drawConsoleArea();
            drawConsole();


            BossOverlay.displayOverlays(-1,-1);
        }



        if (PlayerUtilities.showXP && loggedIn) {
            mapArea.displayXPCounter(this);
        }

        if (loggedIn) {
            if (!resizing) {
                gameScreenIP.drawGraphics(canvas.getGraphics(), gameScreenDrawX, gameScreenDrawY);
            }
            xCameraPos = cameraX;
            zCameraPos = cameraZ;
            yCameraPos = cameraY;
            yCameraCurve = yCurve;
            xCameraCurve = xCurve;
        }
    }

    private void sendBasicPing() {
        getOut().putOpcode(0);
    }

    private void loadRegion() {//wait, when u undid the comment, it crashed teleing from 4 to 1? yeah but i was spam clicking the map so idk what exactly happened then its not related to the load region method, I have reported the minimap spam clicking when teleing bug and u said no need to fix it rn sec lets make sure
        try {
            highestAmtToLoad = 0;
            setLastKnownPlane(-1);
            getIncompleteAnimables().removeAll();
            getProjectiles().removeAll();
            Rasterizer3D.clearTextureCache();
            unlickCaches();
            scene.initToNull();
            System.gc();

            for (int i = 0; i < 4; i++) {
                collisionData[i].setDefault();
            }

            for (int l = 0; l < 4; l++) {
                for (int k1 = 0; k1 < 104; k1++) {
                    for (int j2 = 0; j2 < 104; j2++) {
                        tileFlags[l][k1][j2] = 0;
                    }
                }
            }

            Region region = new Region(tileFlags, tileHeights);
            int k2 = terrainData.length;

            if (loggedIn) {
                sendBasicPing();
            }

            if (!requestMapReconstruct) {
                for (int i3 = 0; i3 < k2; i3++) {
                    int i4 = (mapCoordinates[i3] >> 8) * 64 - regionBaseX;
                    int k5 = (mapCoordinates[i3] & 0xff) * 64 - regionBaseY;
                    byte[] abyte0 = terrainData[i3];
                    if (abyte0 != null) {
                        region.method180(abyte0, k5, i4, (currentRegionX - 6) * 8, (currentRegionY - 6) * 8, collisionData);
                    }
                }

                for (int j4 = 0; j4 < k2; j4++) {
                    int l5 = (mapCoordinates[j4] >> 8) * 62 - regionBaseX;
                    int k7 = (mapCoordinates[j4] & 0xff) * 62 - regionBaseY;
                    byte[] abyte2 = terrainData[j4];

                    if (abyte2 == null && currentRegionY < 800) {
                        region.initiateVertexHeights(k7, 64, 64, l5);
                    }
                }
                if (loggedIn) {
                    sendBasicPing();
                }

                for (int i6 = 0; i6 < k2; i6++) {
                    byte[] abyte1 = objectData[i6];
                    if (abyte1 != null) {
                        int l8 = (mapCoordinates[i6] >> 8) * 64 - regionBaseX;
                        int k9 = (mapCoordinates[i6] & 0xff) * 64 - regionBaseY;
                        region.method190(l8, collisionData, k9, scene, abyte1);
                    }
                }

                CustomObjects.spawn();

            } else {
                for (int plane = 0; plane < 4; plane++) {
                    for (int x = 0; x < 13; x++) {
                        for (int y = 0; y < 13; y++) {
                            int chunkBits = constructRegionData[plane][x][y];

                            if (chunkBits != -1) {
                                int z = chunkBits >> 24 & 3;
                                int rotation = chunkBits >> 1 & 3;
                                int xCoord = chunkBits >> 14 & 0x3ff;
                                int yCoord = chunkBits >> 3 & 0x7ff;
                                int mapRegion = (xCoord / 8 << 8) + yCoord / 8;

                                for (int l11 = 0; l11 < mapCoordinates.length; l11++) {
                                    if (mapCoordinates[l11] != mapRegion || terrainData[l11] == null) {
                                        continue;
                                    }

                                    region.method179(z, rotation, collisionData, x * 8, (xCoord & 7) * 8, terrainData[l11], (yCoord & 7) * 8, plane, y * 8);
                                    break;
                                }
                            }
                        }
                    }
                }

                for (int xChunk = 0; xChunk < 13; xChunk++) {
                    for (int yChunk = 0; yChunk < 13; yChunk++) {
                        int tileBits = constructRegionData[0][xChunk][yChunk];

                        if (tileBits == -1) {
                            region.initiateVertexHeights(yChunk * 8, 8, 8, xChunk * 8);
                        }
                    }
                }

                if (loggedIn) {
                    getOut().putOpcode(0);
                }

                for (int chunkZ = 0; chunkZ < 4; chunkZ++) {
                    for (int chunkX = 0; chunkX < 13; chunkX++) {
                        for (int chunkY = 0; chunkY < 13; chunkY++) {
                            int tileBits = constructRegionData[chunkZ][chunkX][chunkY];

                            if (tileBits != -1) {
                                int plane = tileBits >> 24 & 3;
                                int rotation = tileBits >> 1 & 3;
                                int coordX = tileBits >> 14 & 0x3ff;
                                int coordY = tileBits >> 3 & 0x7ff;
                                int mapRegion = (coordX / 8 << 8) + coordY / 8;

                                for (int idx = 0; idx < mapCoordinates.length; idx++) {
                                    if (mapCoordinates[idx] != mapRegion || objectData[idx] == null) {
                                        continue;
                                    }
                                    region.readObjectMap(collisionData, scene, plane, chunkX * 8, (coordY & 7) * 8, chunkZ, objectData[idx], (coordX & 7) * 8, rotation, chunkY * 8);
                                    break;
                                }
                            }
                        }
                    }
                }
                requestMapReconstruct = false;
            }

            if (loggedIn) {
                getOut().putOpcode(0);
            }

            sendBasicPing();
            region.createRegionScene(collisionData, scene);

            if (loggedIn) {
                gameScreenIP.initDrawingArea();
            }

            if (loggedIn) {
                sendBasicPing();
            }

            int k3 = Region.maximumPlane;

            if (k3 > plane) {
                k3 = plane;
            }

            if (k3 < plane - 1) {
                k3 = plane - 1;
            }

            if (isLowDetail()) {
                scene.initTiles(Region.maximumPlane);
            } else {
                scene.initTiles(0);
            }

            for (int i5 = 0; i5 < 104; i5++) {
                for (int i7 = 0; i7 < 104; i7++) {
                    spawnGroundItem(i5, i7);
                }
            }
            cleanObjectSpawnRequests();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        //Model.mapRS2.clear();
        ObjectDef.baseModels.clear();
        if (loggedIn) {
            getOut().putOpcode(210);
            getOut().putInt(0x3f008edd);
        }
        System.gc();
        Rasterizer3D.initiateRequestBuffers();
        onDemandFetcher.clearExtras();

        int startRegionX = (currentRegionX - 6) / 8 - 1;
        int endRegionX = (currentRegionX + 6) / 8 + 1;
        int startRegionY = (currentRegionY - 6) / 8 - 1;
        int endRegionY = (currentRegionY + 6) / 8 + 1;

        for (int regionX = startRegionX; regionX <= endRegionX; regionX++) {
            for (int regionY = startRegionY; regionY <= endRegionY; regionY++) {
                if (regionX == startRegionX || regionX == endRegionX || regionY == startRegionY || regionY == endRegionY) {
                    int floorMapId = onDemandFetcher.getMapCount(0, regionY, regionX);

                    if (floorMapId != -1) {
                        onDemandFetcher.loadExtra(floorMapId, 3);
                    }

                    int objectMapId = onDemandFetcher.getMapCount(1, regionY, regionX);

                    if (objectMapId != -1) {
                        onDemandFetcher.loadExtra(objectMapId, 3);
                    }
                }
            }
        }
    }

    private void renderedMapScene(int i) {
        int[] ai = miniMapRegions.myPixels;
        int j = ai.length;

        for (int k = 0; k < j; k++) {
            ai[k] = 0;
        }

        for (int l = 1; l < 103; l++) {
            int i1 = 24628 + (103 - l) * 512 * 4;

            for (int k1 = 1; k1 < 103; k1++) {
                if ((tileFlags[i][k1][l] & 0x18) == 0) {
                    scene.method309(ai, i1, i, k1, l);
                }

                if (i < 3 && (tileFlags[i + 1][k1][l] & 8) != 0) {
                    scene.method309(ai, i1, i + 1, k1, l);
                }

                i1 += 4;
            }
        }

        int j1 = Color.WHITE.getRGB();
        int l1 = Color.RED.getRGB();

        if (loggedIn) {
            miniMapRegions.method343();
        }

        for (int i2 = 1; i2 < 103; i2++) {
            for (int j2 = 1; j2 < 103; j2++) {
                if ((tileFlags[i][j2][i2] & 0x18) == 0) {
                    method50(i2, j1, j2, l1, i);
                }

                if (i < 3 && (tileFlags[i + 1][j2][i2] & 8) != 0) {
                    method50(i2, j1, j2, l1, i + 1);
                }
            }
        }

        if (loggedIn) {
            gameScreenIP.initDrawingArea();
        }

        anInt1071 = 0;

        for (int k2 = 0; k2 < 104; k2++) {
            for (int l2 = 0; l2 < 104; l2++) {
                int i3 = scene.method303(plane, k2, l2);

                if (i3 != 0) {
                    i3 = i3 >> 14 & 0x7fff;
                    int j3 = ObjectDef.forID(i3).mapFunctionID;

                    if (j3 >= 0) {
                        aClass30_Sub2_Sub1_Sub1Array1140[anInt1071] = mapFunctions[j3];
                        anIntArray1072[anInt1071] = k2;
                        anIntArray1073[anInt1071] = l2;
                        anInt1071++;
                    }
                }
            }
        }
    }

    private void processNpcAdditions(boolean flag) {
        for (int i = 0; i < npcCount; i++) {
            Npc npc = npcArray[npcIndices[i]];
            int k = 0x20000000 + (npcIndices[i] << 14);
            boolean petCheck = false;
            EntityDef entityDef = npc.definitionOverride;
            if (entityDef.id == 9016
                    || entityDef.id == 449
                    || entityDef.id == 9821
                    || entityDef.id == 9841
                    || entityDef.id == 9842
                    || entityDef.id == 9848
                    || entityDef.id == 9832
                    || entityDef.id == 770
                    || entityDef.id == 9809
                    || entityDef.id == 9819
                    || entityDef.id == 9829
                    || entityDef.id == 771
                    || entityDef.id == 769
                    || entityDef.id == 6960
                    || entityDef.id == 6958
                    || entityDef.id == 6968
                    || entityDef.id == 6964
                    || entityDef.id == 271
                    || entityDef.id == 133
                    || entityDef.id == 105
                    || entityDef.id == 4414 || entityDef.id == 189 || entityDef.id == 1902 ||  entityDef.id == 6302 || entityDef.id == 6303 || entityDef.id == 6305 || entityDef.id == 1898 || entityDef.id == 302 || entityDef.id == 1890 || entityDef.id == 1894 || entityDef.id == 1893 || entityDef.id == 1892 || entityDef.id == 1904 || entityDef.id == 3001 || entityDef.id == 5001 || entityDef.id == 3377 || entityDef.id == 4969 || entityDef.id == 6913 || entityDef.id == 6919 || entityDef.id == 6942 || entityDef.id == 6945 || entityDef.id == 3033 || entityDef.id == 3030 || entityDef.id == 3031 ||  entityDef.id == 3034 || entityDef.id == 3035 || entityDef.id == 3036 ||  entityDef.id == 3038 || entityDef.id == 3039 || entityDef.id == 3040 || entityDef.id == 3047 || entityDef.id == 3048 || entityDef.id == 3050 ||  entityDef.id == 3052 || entityDef.id == 3053 || entityDef.id == 3054    || entityDef.id == 3060 || entityDef.id == 3061  || entityDef.id == 6312 || entityDef.id == 6313 || entityDef.id == 6315 || entityDef.id == 6316 || entityDef.id == 6317 || entityDef.id == 6318 || entityDef.id == 6319 || entityDef.id == 6320 || entityDef.id == 6311 || entityDef.id == 184 || entityDef.id == 6431|| entityDef.id == 10 // null

                    || entityDef.id == 3062) {
                petCheck = true;
            }
            //the isPet wont toggle till a player hovers over the npc and gets that option  may be another way
            try {
                if (!npc.isVisible() || petCheck && !Configuration.RENDER_NPCS) {
                    continue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            int l = npc.x >> 7;
            int i1 = npc.y >> 7;

            if (l < 0 || l >= 104 || i1 < 0 || i1 >= 104) {
                continue;
            }

            if (npc.anInt1540 == 1 && (npc.x & 0x7f) == 64 && (npc.y & 0x7f) == 64) {
                if (anIntArrayArray929[l][i1] == renderCycle) {
                    continue;
                }

                anIntArrayArray929[l][i1] = renderCycle;
            }

            if (!npc.definitionOverride.disableRightClick) {
                k += 0x80000000;
            }

            scene.method285(plane, npc.anInt1552, method42(plane, npc.y, npc.x), k, npc.y, (npc.anInt1540 - 1) * 64 + 60, npc.x, npc, npc.aBoolean1541);
        }
    }

    private void animateTextures() {
        if (!Configuration.ANIMATE_TEXTURES)
            return;

        // textures
        AnimatedTextureStore[] var4;
        int var3 = (var4 = AnimatedTextureStore.values()).length;

        for (int var2 = 0; var2 < var3; ++var2) {
            AnimatedTextureStore data = var4[var2];
            if (Rasterizer3D.loadedTextures[data.getId()] == null) {
                //System.err.println("Texture id: "+data.getId()+" is null.");
                continue;
            }
            if (Rasterizer3D.anIntArray1480[data.getId()] >= data.getId()) {
                Background background = Rasterizer3D.loadedTextures[data.getId()];
                int indexes = background.imgWidth * background.imgHeight - 1;
                int noise = background.imgWidth * this.anInt945 * data.getSpeed();
                int[] current = background.imgPixels;
                int[] next = this.aByteArray912;

                for (int i2 = 0; i2 <= indexes; ++i2) {
                    next[i2] = current[i2 - noise & indexes];
                }

                background.imgPixels = next;
                this.aByteArray912 = current;
                Rasterizer3D.method370(data.getId());
            }
        }

    }

    private void processTextCycles() {
        for (int i = -1; i < playerCount; i++) {
            int j;
            if (i == -1) {
                j = getMyPlayerIndex();
            } else {
                j = playerIndices[i];
            }
            Player player = playerArray[j];
            if (player != null && player.textCycle > 0) {
                player.textCycle--;
                if (player.textCycle == 0) {
                    player.textSpoken = null;
                }
            }
        }
        for (int k = 0; k < npcCount; k++) {
            int l = npcIndices[k];
            Npc npc = npcArray[l];
            if (npc != null && npc.textCycle > 0) {
                npc.textCycle--;
                if (npc.textCycle == 0) {
                    npc.textSpoken = null;
                }
            }
        }
    }

    private int method42(int i, int j, int k) {
        int l = k >> 7;
        int i1 = j >> 7;
        if (l < 0 || i1 < 0 || l > 103 || i1 > 103) {
            return 0;
        }
        int j1 = i;
        if (j1 < 3 && (tileFlags[1][l][i1] & 2) == 2) {
            j1++;
        }
        int k1 = k & 0x7f;
        int l1 = j & 0x7f;
        int i2 = tileHeights[j1][l][i1] * (128 - k1) + tileHeights[j1][l + 1][i1] * k1 >> 7;
        int j2 = tileHeights[j1][l][i1 + 1] * (128 - k1) + tileHeights[j1][l + 1][i1 + 1] * k1 >> 7;
        return i2 * (128 - l1) + j2 * l1 >> 7;
    }

    public void method45() {
        aBoolean1031 = true;
        for (int j = 0; j < 7; j++) {
            myAppearance[j] = -1;
            for (int k = 0; k < IdentityKit.getLength(); k++) {
                if (IdentityKit.cache[k].isNonSelectable() || IdentityKit.cache[k].getBodyPartId() != j + (isMale ? 0 : 7)) {
                    continue;
                }
                myAppearance[j] = k;
                break;
            }
        }
    }

    private void updateNpcMovement(int i, Stream stream) {
        int asdf = 0;
        while (stream.bitPosition + 21 < i * 8) {
            int k = stream.getBits(14);
            if (k == 16383) {
                break;
            }
            if (npcArray[k] == null) {
                npcArray[k] = new Npc();
            }
            Npc npc = npcArray[k];
            npcIndices[npcCount++] = k;
            npc.loopCycle = loopCycle;
            int l = stream.getBits(5);
            if (l > 15) {
                l -= 32;
            }
            int i1 = stream.getBits(5);
            if (i1 > 15) {
                i1 -= 32;
            }
            int j1 = stream.getBits(1);
            int npcid = stream.getBits(Configuration.NPC_BITS);
            npc.definitionOverride = EntityDef.get(npcid);
            int k1 = stream.getBits(1);
            if (k1 == 1) {
                playersToUpdate[playersToUpdateCount++] = k;
            }
            npc.anInt1540 = npc.definitionOverride.npcSizeInSquares;
            npc.anInt1504 = npc.definitionOverride.degreesToTurn;
            npc.anInt1554 = npc.definitionOverride.walkAnimation;
            npc.anInt1555 = npc.definitionOverride.walkingBackwardsAnimation;
            npc.anInt1556 = npc.definitionOverride.walkLeftAnimation;
            npc.anInt1557 = npc.definitionOverride.walkRightAnimation;
            npc.anInt1511 = npc.definitionOverride.standAnimation;
            npc.setPos(myPlayer.smallX[0] + i1, myPlayer.smallY[0] + l, j1 == 1);
        }
        stream.finishBitAccess();
    }

    private void processPlayerAdditions(boolean flag) {
        if (myPlayer.x >> 7 == destinationX && myPlayer.y >> 7 == destinationY) {
            destinationX = 0;
        }
        int j = playerCount;
        if (flag) {
            j = 1;
        }
        for (int l = 0; l < j; l++) {
            Player player;
            int i1;
            if (flag) {
                player = myPlayer;
                i1 = getMyPlayerIndex() << 14;
            } else {
                player = playerArray[playerIndices[l]];
                i1 = playerIndices[l] << 14;
            }
            if (player == null || !player.isVisible() || (!Configuration.RENDER_PLAYERS && player != myPlayer) || !Configuration.RENDER_SELF && player == myPlayer) {
                continue;
            }
            // here
            player.aBoolean1699 = playerCount > 200 && !flag && player.anInt1517 == player.anInt1511;
            int j1 = player.x >> 7;
            int k1 = player.y >> 7;
            if (j1 < 0 || j1 >= 104 || k1 < 0 || k1 >= 104) {
                continue;
            }
            if (player.aModel_1714 != null && loopCycle >= player.anInt1707 && loopCycle < player.anInt1708) {
                player.aBoolean1699 = false;
                player.anInt1709 = method42(plane, player.y, player.x);
                scene.method286(plane, player.y, player, player.anInt1552, player.anInt1722, player.x, player.anInt1709, player.anInt1719, player.anInt1721, i1, player.anInt1720);
                continue;
            }
            if ((player.x & 0x7f) == 64 && (player.y & 0x7f) == 64) {
                if (anIntArrayArray929[j1][k1] == renderCycle) {
                    continue;
                }
                anIntArrayArray929[j1][k1] = renderCycle;
            }
            player.anInt1709 = method42(plane, player.y, player.x);
            scene.method285(plane, player.anInt1552, player.anInt1709, i1, player.y, 60, player.x, player, player.aBoolean1541);
        }
    }

    private void processPlayerUpdating(Stream stream) {
        for (int j = 0; j < playersToUpdateCount; j++) {
            int k = playersToUpdate[j];
            Player player = playerArray[k];
            int l = stream.getUnsignedByte();
            if ((l & 0x40) != 0) {
                l += stream.getUnsignedByte() << 8;
            }
            readPlayerUpdateMask(l, k, stream, player);
        }
    }

    private void method50(int y, int primaryColor, int x, int secondaryColor, int z) {
        int uid = scene.method300(z, x, y);
        if ((uid ^ 0xffffffffffffffffL) != -1L) {
            int resourceTag = scene.fetchObjectIDTagForPosition(z, x, y, uid);
            int direction = resourceTag >> 6 & 3;// direction
            int type = resourceTag & 0x1f;// type
            int color = primaryColor;// color
            if (uid > 0) {
                color = secondaryColor;
            }
            int[] mapPixels = miniMapRegions.myPixels;
            int pixel = 24624 + x * 4 + (103 - y) * 512 * 4;
            int objectId = scene.fetchWallDecorationNewUID(z, x, y);
            ObjectDef objDef = ObjectDef.forID(objectId);
            if ((objDef.mapSceneID ^ 0xffffffff) == 0) {
                if (type == 0 || type == 2) {
                    if (direction == 0) {
                        mapPixels[pixel] = color;
                        mapPixels[pixel + 512] = color;
                        mapPixels[1024 + pixel] = color;
                        mapPixels[1536 + pixel] = color;
                    } else if ((direction ^ 0xffffffff) == -2) {
                        mapPixels[pixel] = color;
                        mapPixels[pixel + 1] = color;
                        mapPixels[pixel + 2] = color;
                        mapPixels[3 + pixel] = color;
                    } else if (direction == 2) {
                        mapPixels[pixel - -3] = color;
                        mapPixels[3 + pixel + 512] = color;
                        mapPixels[3 + pixel + 1024] = color;
                        mapPixels[1536 + pixel - -3] = color;
                    } else if (direction == 3) {
                        mapPixels[pixel + 1536] = color;
                        mapPixels[pixel + 1536 + 1] = color;
                        mapPixels[2 + pixel + 1536] = color;
                        mapPixels[pixel + 1539] = color;
                    }
                }
                if (type == 3) {
                    if (direction == 0) {
                        mapPixels[pixel] = color;
                    } else if (direction == 1) {
                        mapPixels[pixel + 3] = color;
                    } else if (direction == 2) {
                        mapPixels[pixel + 3 + 1536] = color;
                    } else {
                        mapPixels[pixel + 1536] = color;
                    }
                }
                if (type == 2) {
                    if (direction == 3) {
                        mapPixels[pixel] = color;
                        mapPixels[pixel + 512] = color;
                        mapPixels[pixel + 1024] = color;
                        mapPixels[pixel + 1536] = color;
                    } else if (direction == 0) {
                        mapPixels[pixel] = color;
                        mapPixels[pixel + 1] = color;
                        mapPixels[pixel + 2] = color;
                        mapPixels[pixel + 3] = color;
                    } else if (direction == 1) {
                        mapPixels[pixel + 3] = color;
                        mapPixels[pixel + 3 + 512] = color;
                        mapPixels[pixel + 3 + 1024] = color;
                        mapPixels[pixel + 3 + 1536] = color;
                    } else {
                        mapPixels[pixel + 1536] = color;
                        mapPixels[pixel + 1536 + 1] = color;
                        mapPixels[pixel + 1536 + 2] = color;
                        mapPixels[pixel + 1536 + 3] = color;
                    }
                }
            }
        }
        uid = scene.method302(z, x, y);
        if (uid != 0) {
            int resourceTag = scene.fetchObjectIDTagForPosition(z, x, y, uid);
            int direction = resourceTag >> 6 & 3;
            int type = resourceTag & 0x1f;
            int objectId = scene.fetchObjectMeshNewUID(z, x, y);
            ObjectDef objDef = ObjectDef.forID(objectId);
            if (objDef.mapSceneID != -1) {
                Background scene = mapScenes[objDef.mapSceneID];
                if (scene != null) {
                    int sceneX = (objDef.width * 4 - scene.imgWidth) / 2;
                    int sceneY = (objDef.length * 4 - scene.imgHeight) / 2;
                    scene.method361(48 + x * 4 + sceneX, 48 + (104 - y - objDef.length) * 4 + sceneY);
                }
            } else if (type == 9) {
                int color = 0xeeeeee;
                if (uid > 0) {
                    color = 0xee0000;
                }
                int[] mapPixels = miniMapRegions.myPixels;
                int pixel = 24624 + x * 4 + (103 - y) * 512 * 4;
                if (direction == 0 || direction == 2) {
                    mapPixels[pixel + 1536] = color;
                    mapPixels[pixel + 1024 + 1] = color;
                    mapPixels[pixel + 512 + 2] = color;
                    mapPixels[pixel + 3] = color;
                } else {
                    mapPixels[pixel] = color;
                    mapPixels[pixel + 512 + 1] = color;
                    mapPixels[pixel + 1024 + 2] = color;
                    mapPixels[pixel + 1536 + 3] = color;
                }
            }
        }
        uid = scene.fetchGroundDecorationNewUID(z, x, y);
        if (uid > 0) {
            ObjectDef objDef = ObjectDef.forID(uid);
            if (objDef.mapSceneID != -1) {
                Background scene = mapScenes[objDef.mapSceneID];
                if (scene != null) {
                    int sceneX = (objDef.width * 4 - scene.imgWidth) / 2;
                    int sceneY = (objDef.length * 4 - scene.imgHeight) / 2;
                    scene.method361(48 + x * 4 + sceneX, 48 + (104 - y - objDef.length) * 4 + sceneY);
                }
            }
        }
    }

    private int getMapLoadingState() {
        if (!floorMaps.equals("") || !objectMaps.equals("")) {
            floorMaps = "";
            objectMaps = "";
        }
        for (int i = 0; i < terrainData.length; i++) {
            floorMaps += "  " + floorMap[i];
            objectMaps += "  " + objectMap[i];
            if (terrainData[i] == null && floorMap[i] != -1) {
                return -1;
            }
            if (objectData[i] == null && objectMap[i] != -1) {
                return -2;
            }
        }
        boolean flag = true;
        for (int j = 0; j < terrainData.length; j++) {
            byte[] abyte0 = objectData[j];
            if (abyte0 != null) {
                int k = (mapCoordinates[j] >> 8) * 64 - regionBaseX;
                int l = (mapCoordinates[j] & 0xff) * 64 - regionBaseY;
                if (requestMapReconstruct) {
                    k = 10;
                    l = 10;
                }
                flag &= Region.method189(k, abyte0, l);
            }
        }
        if (!flag) {
            return -3;
        }
        if (aBoolean1080) {
            return -4;
        } else {
            loadingStage = 2;
            Region.currentPlane = plane;
            loadRegion();
            // if (loggedIn) {
            getOut().putOpcode(121);
            // }
            return 0;
        }
    }

    private void processProjectiles() {
        for (Projectile class30_sub2_sub4_sub4 = (Projectile) getProjectiles().reverseGetFirst(); class30_sub2_sub4_sub4 != null; class30_sub2_sub4_sub4 = (Projectile) getProjectiles().reverseGetNext()) {
            if (class30_sub2_sub4_sub4.anInt1597 != plane || loopCycle > class30_sub2_sub4_sub4.anInt1572) {
                class30_sub2_sub4_sub4.unlink();
            } else if (loopCycle >= class30_sub2_sub4_sub4.anInt1571) {
                if (class30_sub2_sub4_sub4.anInt1590 > 0) {
                    Npc npc = npcArray[class30_sub2_sub4_sub4.anInt1590 - 1];
                    if (npc != null && npc.x >= 0 && npc.x < 13312 && npc.y >= 0 && npc.y < 13312) {
                        class30_sub2_sub4_sub4.method455(loopCycle, npc.y, method42(class30_sub2_sub4_sub4.anInt1597, npc.y, npc.x) - class30_sub2_sub4_sub4.anInt1583, npc.x);
                    }
                }
                if (class30_sub2_sub4_sub4.anInt1590 < 0) {
                    int j = -class30_sub2_sub4_sub4.anInt1590 - 1;
                    Player player;
                    if (j == playerId) {
                        player = myPlayer;
                    } else {
                        player = playerArray[j];
                    }
                    if (player != null && player.x >= 0 && player.x < 13312 && player.y >= 0 && player.y < 13312) {
                        class30_sub2_sub4_sub4.method455(loopCycle, player.y, method42(class30_sub2_sub4_sub4.anInt1597, player.y, player.x) - class30_sub2_sub4_sub4.anInt1583, player.x);
                    }
                }
                class30_sub2_sub4_sub4.method456(anInt945);
                scene.method285(plane, class30_sub2_sub4_sub4.rotationY, (int) class30_sub2_sub4_sub4.aDouble1587, -1, (int) class30_sub2_sub4_sub4.aDouble1586, 60, (int) class30_sub2_sub4_sub4.aDouble1585, class30_sub2_sub4_sub4, false);
            }
        }

    }

    private final synchronized void method56(int i, boolean bool, int music) {
        if (musicIsntNull()) {
            nextSong = music;
            onDemandFetcher.requestFileData(2, nextSong);
            musicVolume2 = i;
            anInt139 = -1;
            aBoolean995 = true;
            anInt116 = -1;
        }
    }

    private final synchronized void method58(int i_30_, int volume, boolean bool, int music) {
        if (musicIsntNull()) {
            nextSong = music;
            onDemandFetcher.requestFileData(2, nextSong);
            musicVolume2 = volume;
            anInt139 = -1;
            aBoolean995 = true;
            anInt116 = i_30_;
        }
    }

    private void resetInterfaceAnimation(int i) {
        RSInterface class9 = RSInterface.interfaceCache[i];
        if (class9 == null || class9.children == null) {
            return;
        }
        for (int element : class9.children) {
            if (element == -1) {
                break;
            }
            RSInterface class9_1 = RSInterface.interfaceCache[element];
            if (class9_1 == null) {
                System.out.println("Null class: " + element);
                continue;
            }
            if (class9_1.type == 1) {
                resetInterfaceAnimation(class9_1.id);
            }
            class9_1.anInt246 = 0;
            class9_1.anInt208 = 0;
        }
    }

    private void cleanObjectSpawnRequests() {
        SceneSpawnNode sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetFirst();
        for (; sceneSpawnNode != null; sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetNext()) {
            if (sceneSpawnNode.anInt1294 == -1) {
                sceneSpawnNode.anInt1302 = 0;
                method89(sceneSpawnNode);
            } else {
                sceneSpawnNode.unlink();
            }
        }

    }

    private void scrollInterfaceBottom(int maxWidth, int width, int mouseX, int mouseY, RSInterface class9, int maxHeight, boolean flag, int scrollMax) {
        int anInt992;
        if (aBoolean972) {
            anInt992 = 32;
        } else {
            anInt992 = 0;
        }
        aBoolean972 = false;

        if (mouseX >= maxWidth && mouseX < maxWidth + 16 && mouseY >= maxHeight && mouseY < maxHeight + 16) {
            class9.scrollPosition -= anInt1213 * 4;
            if (flag) {
                //  needDrawTabArea = true;
            }
        } else if (mouseY >= maxHeight && mouseY < maxHeight + 16 && mouseX >= (maxWidth + width) - 16 && mouseX < maxWidth + width) {
            class9.scrollPosition += anInt1213 * 4;
            if (flag) {
                // needDrawTabArea = true;
            }
        } else if (mouseY >= maxHeight - anInt992 && mouseY < maxHeight + 16 + anInt992 && mouseX >= maxWidth + 16 && mouseX < (maxWidth + width) - 16 && anInt1213 > 0) {
            int l1 = ((width - 32) * width) / scrollMax;
            if (l1 < 8) {
                l1 = 8;
            }
            int i2 = mouseX - maxWidth - 16 - l1 / 2;
            int j2 = width - 32 - l1;
            class9.scrollPosition = ((scrollMax - width) * i2) / j2;
            if (flag) {
                //  needDrawTabArea = true;
            }
            aBoolean972 = true;
        }
    }

    private void scrollInterface(int i, int j, int k, int l, RSInterface class9, int i1, boolean flag, int j1) {

        int anInt992;
        if (aBoolean972) {
            anInt992 = 32;
        } else {
            anInt992 = 0;
        }
        aBoolean972 = false;
        if (k >= i && k < i + 16 && l >= i1 && l < i1 + 16) {
            class9.scrollPosition -= anInt1213 * 4;
            if (flag) {
            }
        } else if (k >= i && k < i + 16 && l >= i1 + j - 16 && l < i1 + j) {
            class9.scrollPosition += anInt1213 * 4;
            if (flag) {
            }
        } else if (k >= i - anInt992 && k < i + 16 + anInt992 && l >= i1 + 16 && l < i1 + j - 16 && anInt1213 > 0) {
            int l1 = (j - 32) * j / j1;
            if (l1 < 8) {
                l1 = 8;
            }
            int i2 = l - i1 - 16 - l1 / 2;
            int j2 = j - 32 - l1;
            class9.scrollPosition = (j1 - j) * i2 / j2;
            if (flag) {
            }
            aBoolean972 = true;
        }
    }

    // might be needed
    private boolean method66(int i, int j, int k, int id) {
        int j1 = scene.fetchObjectIDTagForPosition(plane, k, j, i);
        if (i == -1) {
            return false;
        }
        int k1 = j1 & 0x1f;
        int l1 = j1 >> 6 & 3;
        if (k1 == 10 || k1 == 11 || k1 == 22) {
            ObjectDef class46 = ObjectDef.forID(id);
            int i2;
            int j2;
            if (l1 == 0 || l1 == 2) {
                i2 = class46.width;
                j2 = class46.length;
            } else {
                i2 = class46.width;
                j2 = class46.length;
            }
            int k2 = class46.plane;
            if (l1 != 0) {
                k2 = (k2 << l1 & 0xf) + (k2 >> 4 - l1);
            }
            doWalkTo(2, 0, j2, 0, myPlayer.smallY[0], i2, k2, j, myPlayer.smallX[0], false, k);
        } else {
            doWalkTo(2, l1, 0, k1 + 1, myPlayer.smallY[0], 0, 0, j, myPlayer.smallX[0], false, k);
        }
        crossX = super.saveClickX;
        crossY = super.saveClickY;
        crossType = 2;
        crossIndex = 0;
        return true;
    }

    private void method70() {
        /**
         * @depreciated This stops players from seeing messages in particular areas.
         *              Tutorial Island, etc.
         */
        anInt1251 = 0;
        int j = (myPlayer.x >> 7) + regionBaseX;
        int k = (myPlayer.y >> 7) + regionBaseY;
        if (j >= 3053 && j <= 3156 && k >= 3056 && k <= 3136) {
            anInt1251 = 1;
        }
        if (j >= 3072 && j <= 3118 && k >= 9492 && k <= 9535) {
            anInt1251 = 1;
        }
        if (anInt1251 == 1 && j >= 3139 && k >= 3008 && k <= 3062) {
            anInt1251 = 0;
        }
    }

    public void sendString(int identifier, String text) {
        text = identifier + "," + text;
        getOut().putOpcode(127);
        getOut().putByte(text.length() + 1);
        getOut().putString(text);
    }

    private void manageTextInput() {
        do {
            int key = readChar(-796);

            if (key == -1) {
                break;
            }

            if (openInterfaceID != -1) {
                try {
                    int[] interfaces = {
                            openInterfaceID, invOverlayInterfaceID
                    };
                    for (int interfaceId : interfaces) {
                        if (interfaceId == -1)
                            continue;
                        RSInterface open = RSInterface.get(interfaceId);
                        if (open.children != null) {
                            for (int i = 0; i < open.children.length; i++) {
                                RSInterface child = RSInterface.get(open.children[i]);
                                if (child == null)
                                    continue;
                                if (child.inputField) {
                                    if (child.inputFieldString.equalsIgnoreCase("Enter a name to search")) {
                                        child.inputFieldString = "";
                                    }
                                    if (key == 8) {
                                        if (child.inputFieldString.length() > 0) {
                                            if (child.inputFieldString.equalsIgnoreCase(""))
                                                return;
                                            child.inputFieldString = child.inputFieldString.substring(0, child.inputFieldString.length() - 1);
                                            Interfaces.inputFieldEdited(open.children[i]);
                                        }
                                    } else {
                                        if (key >= 32 && key <= 122) {
                                            if (child.inputFieldString.length() < 21) {
                                                child.inputFieldString += (char) key;
                                                Interfaces.inputFieldEdited(open.children[i]);
                                            }
                                        }
                                    }
                                    return;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Signlink.reportError("Error handling text input");
                }
            }


            if (key == 167 || key == 96) {
                if (myRights == 4 || myRights == 8) {
                    consoleOpen = !consoleOpen;
                }
                return;
            }
            if (inputDialogState == 0) {
                if (backDialogID == 2459 || backDialogID == 2469 || backDialogID == 2480 || backDialogID == 2492 || backDialogID == 14170) {
                    if (key >= 49 && key <= 53 && clickDelay.elapsed(200)) {
                        clickDelay.reset();
                        sendPacket185(backDialogID + key - 47);
                    }
                }
            }


            if (consoleOpen) {
                if (key == 8 && consoleInput.length() > 0) {
                    consoleInput = consoleInput.substring(0, consoleInput.length() - 1);
                }
                if (key >= 32 && key <= 122 && consoleInput.length() < 80) {
                    consoleInput += (char) key;
                }

                if ((key == 13 || key == 10) && consoleInput.length() >= 1) {
                    printConsoleMessage(consoleInput, 0);
                    sendCommandPacket(consoleInput);
                    consoleInput = "";
                    updateChatArea = true;
                }
                return;
            } else if (messagePromptRaised) {
                if (key >= 32 && key <= 122 && promptInput.length() < 80) {
                    promptInput += (char) key;
                    updateChatArea = true;
                }

                if (key == 8 && promptInput.length() > 0) {
                    promptInput = promptInput.substring(0, promptInput.length() - 1);
                    updateChatArea = true;
                }
                if (key == 13 || key == 10) {
                    messagePromptRaised = false;
                    updateChatArea = true;

                    if (friendsListAction == 1) {
                        long l = TextUtilities.longForName(promptInput);
                        addFriend(l);
                    }

                    if (friendsListAction == 2 && friendCount > 0) {
                        long l1 = TextUtilities.longForName(promptInput);
                        delFriend(l1);
                    }

                    if (interfaceButtonAction == 6199 && promptInput.length() > 0) {
                        String inp = "";
                        inp = inputString;
                        inputString = "::[CN] " + promptInput;
                        sendPacket(103);
                        inputString = inp;
                    }

                    if (interfaceButtonAction == 557 && promptInput.length() > 0) {
                        int length = promptInput.length();
                        char lastChar = promptInput.charAt(length - 1);

                        if (lastChar == 'k') {
                            promptInput = promptInput.substring(0, length - 1) + "000";
                        } else if (lastChar == 'm') {
                            promptInput = promptInput.substring(0, length - 1) + "000000";
                        } else if (lastChar == 'b') {
                            promptInput = promptInput.substring(0, length - 1) + "000000000";
                        }
                        inputString = "::mpremove " + promptInput;
                        sendPacket(103);
                    }


                    if (interfaceButtonAction == 558 && promptInput.length() > 0) {
                        int length = promptInput.length();
                        char lastChar = promptInput.charAt(length - 1);

                        if (lastChar == 'k') {
                            promptInput = promptInput.substring(0, length - 1) + "000";
                        } else if (lastChar == 'm') {
                            promptInput = promptInput.substring(0, length - 1) + "000000";
                        } else if (lastChar == 'b') {
                            promptInput = promptInput.substring(0, length - 1) + "000000000";
                        }
                        inputString = "::mpadd " + promptInput;
                        sendPacket(103);
                    }

                    if (interfaceButtonAction == 6199 && promptInput.length() > 0) {
                        String inp = inputString;
                        inputString = "::changeclanname " + promptInput;
                        sendPacket(103);
                        inputString = inp;
                    }

                    if (friendsListAction == 3 && promptInput.length() > 0) {
                        getOut().putOpcode(126);
                        getOut().putByte(0);
                        int k = getOut().position;
                        getOut().putLong(aLong953);
                        ChatEncoder.writeChatboxText(promptInput, getOut());
                        getOut().putVariableSizeByte(getOut().position - k);
                        promptInput = ChatEncoder.processText(promptInput);
                        // promptInput = Censor.doCensor(promptInput);
                        pushMessage(promptInput, 6, TextUtilities.fixName(TextUtilities.nameForLong(aLong953)));
                        if (privateChatMode == 2) {
                            privateChatMode = 1;
                            getOut().putOpcode(95);
                            getOut().putByte(publicChatMode);
                            getOut().putByte(privateChatMode);
                            getOut().putByte(tradeMode);
                        }
                    }

                    if (friendsListAction == 4 && ignoreCount < 100) {
                        long l2 = TextUtilities.longForName(promptInput);
                        addIgnore(l2);
                    }

                    if (friendsListAction == 5 && ignoreCount > 0) {
                        long l3 = TextUtilities.longForName(promptInput);
                        delIgnore(l3);
                    }

                    if (friendsListAction == 6) {
                        long l3 = TextUtilities.longForName(promptInput);
                        chatJoin(l3);
                    } else if ((this.friendsListAction == 12) && (this.promptInput.length() > 0)) {
                        if (promptInput.toLowerCase().matches("\\d+")) {
                            int goalLevel = Integer.parseInt(this.promptInput);
                            if (goalLevel > 99) {
                                goalLevel = 99;
                            }
                            int currentMaxLevel = maxStats[Skills.selectedSkillId];
                            if ((goalLevel < 0) || (currentMaxLevel >= goalLevel)) {
                                Skills.selectedSkillId = -1;
                                return;
                            }
                            Skills.goalType = "Target Level: ";
                            Skills.goalData[Skills.selectedSkillId][0] = currentExp[Skills.selectedSkillId];
                            Skills.goalData[Skills.selectedSkillId][1] = PlayerUtilities.getXPForLevel(goalLevel) + 1;
                            Skills.goalData[Skills.selectedSkillId][2] = (Skills.goalData[Skills.selectedSkillId][0] / Skills.goalData[Skills.selectedSkillId][1]) * 100;
                            saveGoals(myUsername);
                            Skills.selectedSkillId = -1;
                        }
                    } else if ((this.friendsListAction == 13) && (this.promptInput.length() > 0) && ((this.promptInput.toLowerCase()
                            .matches("\\d+[a-z&&[kmb]]")) || (this.promptInput.matches("\\d+")))) {
                        int goalExp = 0;
                        try {
                            goalExp = Integer.parseInt(promptInput.trim()
                                    .toLowerCase()
                                    .replaceAll("k", "000")
                                    .replaceAll("m", "000000")
                                    .replaceAll("b", "000000000"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if ((goalExp <= 0) || (goalExp <= currentExp[Skills.selectedSkillId])) {
                            Skills.selectedSkillId = -1;
                            return;
                        } else if (goalExp > 1000000000) {
                            goalExp = 1000000000;
                        }
                        Skills.goalType = "Target Exp: ";
                        Skills.goalData[Skills.selectedSkillId][0] = currentExp[Skills.selectedSkillId];
                        Skills.goalData[Skills.selectedSkillId][1] = goalExp;
                        Skills.goalData[Skills.selectedSkillId][2] = (Skills.goalData[Skills.selectedSkillId][0] / Skills.goalData[Skills.selectedSkillId][1] * 100);
                        saveGoals(myUsername);
                        Skills.selectedSkillId = -1;
                    }
                }
            } else if (inputFieldSelected != null && ((openInterfaceID != -1 && RSInterface.interfaceCache[openInterfaceID] != null && RSInterface.interfaceCache[openInterfaceID].hasInputField) || (tabInterfaceIDs[tabID] != -1 && RSInterface.interfaceCache[tabInterfaceIDs[tabID]].hasInputField))) {
                RSInterface rsi = inputFieldSelected;
                if (rsi.inputToggled) {
                    if (key >= 32 && key <= 122 && rsi.rsFont.getTextWidth(rsi.inputText) < (rsi.width - 20)) {
                        rsi.inputText += (char) key;
                        updateChatArea = true;
                        rsi.textInput.handleInput();
                    }
                    if (key == 8 && rsi.inputText.length() > 0) {
                        rsi.inputText = rsi.inputText.substring(0, rsi.inputText.length() - 1);
                        updateChatArea = true;
                        rsi.textInput.handleInput();
                    }
                    if ((key == 13 || key == 10)) {
                        rsi.inputToggled = false;
                        updateChatArea = true;
                        rsi.textInput.handleInput();
                        if (rsi.inputText.equals("")) {
                            rsi.inputText = rsi.defaultText;
                        }
                    }
                    return;
                }
            } else if (inputDialogState == 1) {
                if (key >= 48 && key <= 57 && amountOrNameInput.length() < 10) {
                    amountOrNameInput += (char) key;
                    updateChatArea = true;
                }

                if (!amountOrNameInput.toLowerCase()
                        .contains("k") && !amountOrNameInput.toLowerCase()
                        .contains("m") && !amountOrNameInput.toLowerCase()
                        .contains("b") && (key == 107 || key == 109) || key == 98) {
                    amountOrNameInput += (char) key;
                    updateChatArea = true;
                }

                if (key == 8 && amountOrNameInput.length() > 0) {
                    amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
                    updateChatArea = true;
                }

                if (key == 13 || key == 10) {
                    if (amountOrNameInput.length() > 0) {
                        if (amountOrNameInput.toLowerCase().contains("k")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
                        } else if (amountOrNameInput.toLowerCase().contains("m")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
                        } else if (amountOrNameInput.toLowerCase().contains("b")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");

                        } else if (amountOrNameInput.toLowerCase().contains("t")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("t", "000000000000");

                        }
                        long l = Long.valueOf(amountOrNameInput);

                        if (l > 2147483647) {
                            amountOrNameInput = "2147483647";
                        }

                        long amount1 = 0;

                        try {
                            amount1 = Long.parseLong(amountOrNameInput);

                            // overflow concious code
                            if (amount1 < 0) {
                                amount1 = 0;
                            } else if (amount1 > Integer.MAX_VALUE) {
                                amount1 = Integer.MAX_VALUE;
                            }
                        } catch (Exception ignored) {
                        }

                        if (OSRSCreationMenu.selectingAmount) {
                            if (amount1 == 0) {
                                amount1 = 1;
                            }
                            if (amount1 >= 28) {
                                OSRSCreationMenu.quantity = "All";
                            } else {
                                OSRSCreationMenu.quantity = String.valueOf(amount1);
                            }
                            OSRSCreationMenu.selectingAmount = false;
                            Client.instance.inputDialogState = 5;
                            Client.updateChatArea = true;
                            return;
                        }

                        int amount = 0;
                        amount = Integer.parseInt(amountOrNameInput);
                        if (interfaceButtonAction == 557 && withdrawingMoneyFromPouch) {
                            getOut().putOpcode(7);
                            getOut().putInt(amount);
                            inputDialogState = 0;
                            updateChatArea = true;
                            withdrawingMoneyFromPouch = false;
                            return;
                        }
                        getOut().putOpcode(208);
                        getOut().putInt(amount);
                        modifiableXValue = amount;
                    }

                    inputDialogState = 0;
                    updateChatArea = true;
                }
            } else if (inputDialogState == 4) {
                System.out.println("inputDialogState == 4");
                if (key >= 48 && key <= 57 && amountOrNameInput.length() < 19) {
                    amountOrNameInput += (char) key;
                    updateChatArea = true;
                }

                if (!amountOrNameInput.toLowerCase()
                        .contains("k") && !amountOrNameInput.toLowerCase()
                        .contains("m") && !amountOrNameInput.toLowerCase()
                        .contains("b") && (key == 107 || key == 109) || key == 98) {
                    amountOrNameInput += (char) key;
                    updateChatArea = true;
                }

                if (key == 8 && amountOrNameInput.length() > 0) {
                    amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
                    updateChatArea = true;
                }

                if (key == 13 || key == 10) {
                    if (amountOrNameInput.length() > 0) {
                        if (amountOrNameInput.toLowerCase().contains("k")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("k", "000");
                        } else if (amountOrNameInput.toLowerCase().contains("m")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("m", "000000");
                        } else if (amountOrNameInput.toLowerCase().contains("b")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("b", "000000000");
                        } else if (amountOrNameInput.toLowerCase().contains("t")) {
                            amountOrNameInput = amountOrNameInput.replaceAll("t", "000000000000");
                        }
                        BigInteger bi = new BigInteger(amountOrNameInput);
                        long amount = 0;
                        try {
                            bi.longValueExact();
                            amount = Long.valueOf(amountOrNameInput);
                        } catch (ArithmeticException e) {
                            amount = Long.MAX_VALUE;
                        }
                        getOut().putOpcode(209);
                        getOut().putLong(amount);
                        System.out.println("putting amount: " + amount);
                        modifiableXValue = (int) amount;
                    }

                    inputDialogState = 0;
                    updateChatArea = true;
                }
            } else if (inputDialogState == 2) {
                if (key >= 32 && key <= 122 && amountOrNameInput.length() < 70) {
                    amountOrNameInput += (char) key;
                    updateChatArea = true;
                }

                if (key == 8 && amountOrNameInput.length() > 0) {
                    amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
                    updateChatArea = true;
                }

                if (key == 13 || key == 10) {
                    if (amountOrNameInput.length() > 0) {
                        getOut().putOpcode(60);
                        getOut().putByte(amountOrNameInput.length() + 1);
                        getOut().putString(amountOrNameInput);
                    }

                    inputDialogState = 0;
                    updateChatArea = true;
                }
            } else if (inputDialogState == 3) {
                if (key == 10) {
                    getGrandExchange().totalItemResults = 0;
                    amountOrNameInput = "";
                    inputDialogState = 0;
                    updateChatArea = true;
                }
                if (key == 13 || key == 10) {
                    if (amountOrNameInput.length() == 0) {
                        getGrandExchange().searching = false;
                        interfaceButtonAction = 0;
                    }
                }
                if (key >= 32 && key <= 122 && amountOrNameInput.length() < 40) {
                    amountOrNameInput += (char) key;
                    updateChatArea = true;
                }
                if (key == 8 && amountOrNameInput.length() > 0) {
                    amountOrNameInput = amountOrNameInput.substring(0, amountOrNameInput.length() - 1);
                    updateChatArea = true;
                }
            } else if (backDialogID == -1) {
                if (key >= 32 && key <= 122 && inputString.length() < 80) {
                    inputString += (char) key;
                    updateChatArea = true;
                }

                if (key == 8 && inputString.length() > 0) {
                    inputString = inputString.substring(0, inputString.length() - 1);
                    updateChatArea = true;
                }

                if (key == 9) {
                    tabToReplyPm();
                }

                if ((key == 13 || key == 10) && inputString.length() > 0) {
                    if (inputString.startsWith("/")) {
                        getOut().putOpcode(5);
                        getOut().putByte(inputString.substring(1).length() + 1);
                        getOut().putString(inputString.substring(1));
                        inputString = "";
                        return;
                    }

                    if (inputString.equalsIgnoreCase("::hideall")) {
                        safeRenderer = !safeRenderer;
                        pushMessage("You are " + (safeRenderer ? "now using" : "no longer using") + " safe rendering.");
                        inputString = "";
                    }
                    if (inputString.equalsIgnoreCase("::reload")) {
                        reloadInterface();
                        inputString = "";
                        return;
                    }

                    if (inputString.equalsIgnoreCase("::depth")) {
                        Rasterizer3D.depthBufferEnabled = !Rasterizer3D.depthBufferEnabled;
                        inputString = "";
                        return;
                    }

                    if (inputString.startsWith("::ie")) {
                        String string = inputString.replace("::ie ", "");
                        ItemEditor.getInstance(Integer.parseInt(string));
                        inputString = "";
                        return;
                    }

                    if (inputString.startsWith("::") || inputString.startsWith(";;") || inputString.startsWith(":;") || inputString.startsWith(";:") && !inputString.startsWith("::[")) {
                        getOut().putOpcode(103);
                        getOut().putByte(inputString.length() - 1);
                        getOut().putString(inputString.substring(2));
                    } else {

                        String s = inputString.toLowerCase();
                        int j2 = 0;
                        if (s.startsWith("yellow:")) {
                            j2 = 0;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("red:")) {
                            j2 = 1;
                            inputString = inputString.substring(4);
                        } else if (s.startsWith("green:")) {
                            j2 = 2;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("cyan:")) {
                            j2 = 3;
                            inputString = inputString.substring(5);
                        } else if (s.startsWith("purple:")) {
                            j2 = 4;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("white:")) {
                            j2 = 5;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("flash1:")) {
                            j2 = 6;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("flash2:")) {
                            j2 = 7;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("flash3:")) {
                            j2 = 8;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("glow1:")) {
                            j2 = 9;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("glow2:")) {
                            j2 = 10;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("glow3:")) {
                            j2 = 11;
                            inputString = inputString.substring(6);
                        }
                        s = inputString.toLowerCase();
                        int i3 = 0;
                        if (s.startsWith("wave:")) {
                            i3 = 1;
                            inputString = inputString.substring(5);
                        } else if (s.startsWith("wave2:")) {
                            i3 = 2;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("shake:")) {
                            i3 = 3;
                            inputString = inputString.substring(6);
                        } else if (s.startsWith("scroll:")) {
                            i3 = 4;
                            inputString = inputString.substring(7);
                        } else if (s.startsWith("slide:")) {
                            i3 = 5;
                            inputString = inputString.substring(6);
                        }
                        getOut().putOpcode(4);
                        getOut().putByte(0);
                        int j3 = getOut().position;
                        getOut().method425(i3);

                        getOut().method425(j2);

                        aStream_834.position = 0;
                        ChatEncoder.writeChatboxText(inputString, aStream_834);
                        getOut().method441(0, aStream_834.buffer, aStream_834.position);
                        getOut().putVariableSizeByte(getOut().position - j3);
                        inputString = ChatEncoder.processText(inputString);
                        // inputString = Censor.doCensor(inputString);
                        myPlayer.textSpoken = inputString;
                        myPlayer.anInt1513 = j2;
                        myPlayer.anInt1531 = i3;
                        myPlayer.textCycle = 150;

                        pushMessage(myPlayer.textSpoken, 2, myPlayer.name, myPlayer.loyaltyTitle, myPlayer.loyaltyColor, myPlayer.loyaltyPosition, (byte) myRights, (byte) gameMode, (byte) myDiff);

                        if (publicChatMode == 2) {
                            publicChatMode = 3;
                            getOut().putOpcode(95);
                            getOut().putByte(publicChatMode);
                            getOut().putByte(privateChatMode);
                            getOut().putByte(tradeMode);
                        }
                    }
                    inputString = "";
                    updateChatArea = true;
                    withdrawingMoneyFromPouch = false;
                }
            }
        } while (true);
    }

    public void reloadInterface() {
        Archive streamLoader_1 = getArchive(3, "interface", "interface", expectedCRCs[3], 35);
        Archive mediaArchive = getArchive(4, "2d graphics", "media", expectedCRCs[4], 40);
        TextDrawingArea[] aclass30_sub2_sub1_sub4s = {smallText, normalText, boldText, aTextDrawingArea_1273};
        RSFontSystem[] newFonts = {newSmallFont, newRegularFont, newBoldFont};
        RSInterface.unpack(streamLoader_1, aclass30_sub2_sub1_sub4s, mediaArchive, newFonts);
    }

    private void readNPCUpdateMask(Stream stream) {
        for (int j = 0; j < playersToUpdateCount; j++) {
            int k = playersToUpdate[j];
            Npc npc = npcArray[k];
            int l = stream.getUnsignedByte();
            if ((l & 0x10) != 0) {
                int i1 = stream.getShortBigEndian();
                if (i1 == 65535) {
                    i1 = -1;
                }
                int i2 = stream.getUnsignedByte();
                if (i1 == npc.anim && i1 != -1) {
                    int l2 = AnimationDefinition.cache[i1].delayType;
                    if (l2 == 1) {
                        npc.currentAnimFrame = 0;
                        npc.anInt1528 = 0;
                        npc.animationDelay = i2;
                        npc.anInt1530 = 0;
                    }
                    if (l2 == 2) {
                        npc.anInt1530 = 0;
                    }
                } else if (i1 == -1 || npc.anim == -1 || AnimationDefinition.cache[i1].forcedPriority >= AnimationDefinition.cache[npc.anim].forcedPriority) {
                    npc.anim = i1;
                    npc.currentAnimFrame = 0;
                    npc.anInt1528 = 0;
                    npc.animationDelay = i2;
                    npc.anInt1530 = 0;
                    npc.anInt1542 = npc.smallXYIndex;
                }
            }
            if ((l & 8) != 0) {
                int j1 = getInputBuffer().readInt();
                int j2 = stream.getUnsignedByte();
                int icon = stream.getUnsignedByte();
                npc.updateHitData(j2, j1, loopCycle, icon, 0);
                npc.loopCycleStatus = loopCycle + 300;
                npc.currentHealth = getInputBuffer().readInt();
                npc.maxHealth = getInputBuffer().readInt();

            } // Restart Server and client for dev
            if ((l & 0x80) != 0) {
                npc.gfxId = stream.getUnsignedShort();
                int k1 = stream.getIntLittleEndian();
                npc.graphicHeight = k1 >> 16;
                npc.graphicDelay = loopCycle + (k1 & 0xffff);
                npc.currentAnim = 0;
                npc.animCycle = 0;
                if (npc.graphicDelay > loopCycle) {
                    npc.currentAnim = -1;
                }
                if (npc.gfxId == 65535) {
                    npc.gfxId = -1;
                }
            }
            if ((l & 0x20) != 0) {
                npc.interactingEntity = stream.getUnsignedShort();
                if (npc.interactingEntity == 65535) {
                    npc.interactingEntity = -1;
                }
            }
            if ((l & 1) != 0) {
                npc.textSpoken = stream.getString();
                npc.textCycle = 100;
            }
            if ((l & 0x40) != 0) {
                int l1 = getInputBuffer().readInt();
                int k2 = stream.getUnsignedByte();
                int icon = stream.getUnsignedByte();
                npc.updateHitData(k2, l1, loopCycle, icon, 0);
                npc.loopCycleStatus = loopCycle + 300;
                npc.currentHealth = getInputBuffer().readInt();
                npc.maxHealth = getInputBuffer().readInt();// As for damage on interface....
            }
            if ((l & 2) != 0) {
                npc.definitionOverride = EntityDef.get(stream.getShortBigEndianA());
                npc.anInt1540 = npc.definitionOverride.npcSizeInSquares;
                npc.anInt1504 = npc.definitionOverride.degreesToTurn;
                npc.anInt1554 = npc.definitionOverride.walkAnimation;
                npc.anInt1555 = npc.definitionOverride.walkingBackwardsAnimation;
                npc.anInt1556 = npc.definitionOverride.walkLeftAnimation;
                npc.anInt1557 = npc.definitionOverride.walkRightAnimation;
                npc.anInt1511 = npc.definitionOverride.standAnimation;
            }
            if ((l & 4) != 0) {
                npc.anInt1538 = stream.getShortBigEndian();
                npc.anInt1539 = stream.getShortBigEndian();
            }
        }
    }

    private void method89(SceneSpawnNode sceneSpawnNode) {
        int i = 0;
        int j = -1;
        int k = 0;
        int l = 0;
        if (sceneSpawnNode.anInt1296 == 0) {
            i = scene.method300(sceneSpawnNode.anInt1295, sceneSpawnNode.anInt1297, sceneSpawnNode.anInt1298);
        }
        if (sceneSpawnNode.anInt1296 == 1) {
            i = scene.method301(sceneSpawnNode.anInt1295, sceneSpawnNode.anInt1297, sceneSpawnNode.anInt1298);
        }
        if (sceneSpawnNode.anInt1296 == 2) {
            i = scene.method302(sceneSpawnNode.anInt1295, sceneSpawnNode.anInt1297, sceneSpawnNode.anInt1298);
        }
        if (sceneSpawnNode.anInt1296 == 3) {
            i = scene.method303(sceneSpawnNode.anInt1295, sceneSpawnNode.anInt1297, sceneSpawnNode.anInt1298);
        }
        if (i != 0) {
            int i1 = scene.fetchObjectIDTagForPosition(sceneSpawnNode.anInt1295, sceneSpawnNode.anInt1297, sceneSpawnNode.anInt1298, i);
            j = i >> 14 & 0x7fff;
            k = i1 & 0x1f;
            l = i1 >> 6;
        }
        sceneSpawnNode.anInt1299 = j;
        sceneSpawnNode.anInt1301 = k;
        sceneSpawnNode.anInt1300 = l;
    }


    private void updatePlayerMovement(Stream stream, int i) {
        while (stream.bitPosition + 10 < i * 8) {
            int j = stream.getBits(11);
            if (j == 2047) {
                break;
            }
            if (playerArray[j] == null) {
                playerArray[j] = new Player();
                if (getaStreamArray895s()[j] != null) {
                    playerArray[j].updatePlayer(getaStreamArray895s()[j]);
                }
            }
            playerIndices[playerCount++] = j;
            Player player = playerArray[j];
            player.loopCycle = loopCycle;
            int k = stream.getBits(1);
            if (k == 1) {
                playersToUpdate[playersToUpdateCount++] = j;
            }
            int l = stream.getBits(1);
            int i1 = stream.getBits(5);
            if (i1 > 15) {
                i1 -= 32;
            }
            int j1 = stream.getBits(5);
            if (j1 > 15) {
                j1 -= 32;
            }
            player.setPos(myPlayer.smallX[0] + j1, myPlayer.smallY[0] + i1, l == 1);
        }
        stream.finishBitAccess();
    }

    private void readNPCUpdateBlockForced() {
        for (int j = 0; j < npcCount; j++) {
            int k = npcIndices[j];
            Npc npc = npcArray[k];
            if (npc != null) {
                entityUpdateBlock(npc);
            }
        }
    }

    private void entityUpdateBlock(Actor actor) {
        if (actor.x < 128 || actor.y < 128 || actor.x >= 13184 || actor.y >= 13184) {
            actor.anim = -1;
            actor.gfxId = -1;
            actor.anInt1547 = 0;
            actor.anInt1548 = 0;
            actor.x = actor.smallX[0] * 128 + actor.anInt1540 * 64;
            actor.y = actor.smallY[0] * 128 + actor.anInt1540 * 64;
            actor.method446();
        }
        if (actor == myPlayer && (actor.x < 1536 || actor.y < 1536 || actor.x >= 11776 || actor.y >= 11776)) {
            actor.anim = -1;
            actor.gfxId = -1;
            actor.anInt1547 = 0;
            actor.anInt1548 = 0;
            actor.x = actor.smallX[0] * 128 + actor.anInt1540 * 64;
            actor.y = actor.smallY[0] * 128 + actor.anInt1540 * 64;
            actor.method446();
        }
        if (actor.anInt1547 > loopCycle) {
            updateEntityPos(actor);
        } else if (actor.anInt1548 >= loopCycle) {
            updateEntityFace(actor);
        } else {
            processWalkingStep(actor);
        }
        appendFocusDest(actor);
        appendAnimation(actor);
    }

    private void updateEntityPos(Actor actor) {
        int i = actor.anInt1547 - loopCycle;
        int j = actor.anInt1543 * 128 + actor.anInt1540 * 64;
        int k = actor.anInt1545 * 128 + actor.anInt1540 * 64;
        actor.x += (j - actor.x) / i;
        actor.y += (k - actor.y) / i;
        actor.anInt1503 = 0;
        if (actor.anInt1549 == 0) {
            actor.turnDirection = 1024;
        }
        if (actor.anInt1549 == 1) {
            actor.turnDirection = 1536;
        }
        if (actor.anInt1549 == 2) {
            actor.turnDirection = 0;
        }
        if (actor.anInt1549 == 3) {
            actor.turnDirection = 512;
        }
    }

    private void updateEntityFace(Actor actor) {
        if (actor.anInt1548 == loopCycle || actor.anim == -1 || actor.animationDelay != 0 || actor.anInt1528 + 1 > AnimationDefinition.cache[actor.anim].getFrameLength(actor.currentAnimFrame)) {
            int i = actor.anInt1548 - actor.anInt1547;
            int j = loopCycle - actor.anInt1547;
            int k = actor.anInt1543 * 128 + actor.anInt1540 * 64;
            int l = actor.anInt1545 * 128 + actor.anInt1540 * 64;
            int i1 = actor.anInt1544 * 128 + actor.anInt1540 * 64;
            int j1 = actor.anInt1546 * 128 + actor.anInt1540 * 64;
            actor.x = (k * (i - j) + i1 * j) / i;
            actor.y = (l * (i - j) + j1 * j) / i;
        }
        actor.anInt1503 = 0;
        if (actor.anInt1549 == 0) {
            actor.turnDirection = 1024;
        }
        if (actor.anInt1549 == 1) {
            actor.turnDirection = 1536;
        }
        if (actor.anInt1549 == 2) {
            actor.turnDirection = 0;
        }
        if (actor.anInt1549 == 3) {
            actor.turnDirection = 512;
        }
        actor.anInt1552 = actor.turnDirection;
    }

    private void processWalkingStep(Actor actor) {
        actor.anInt1517 = actor.anInt1511;
        if (actor.smallXYIndex == 0) {
            actor.anInt1503 = 0;
            return;
        }
        if (actor.anim != -1 && actor.animationDelay == 0) {
            AnimationDefinition animation = AnimationDefinition.cache[actor.anim];
            /*
             * for (int i = 0; i < animation.anIntArray357.length; i++) {
             * animation.anIntArray357[i] = -1; }
             */
            if (actor.anInt1542 > 0 && animation.resetWhenWalk == 0) {
                actor.anInt1503++;
                return;
            }
            if (actor.anInt1542 <= 0 && animation.priority == 0) {
                actor.anInt1503++;
                return;
            }
        }
        int i = actor.x;
        int j = actor.y;
        int k = actor.smallX[actor.smallXYIndex - 1] * 128 + actor.anInt1540 * 64;
        int l = actor.smallY[actor.smallXYIndex - 1] * 128 + actor.anInt1540 * 64;
        if (k - i > 256 || k - i < -256 || l - j > 256 || l - j < -256) {
            actor.x = k;
            actor.y = l;
            return;
        }
        if (i < k) {
            if (j < l) {
                actor.turnDirection = 1280;
            } else if (j > l) {
                actor.turnDirection = 1792;
            } else {
                actor.turnDirection = 1536;
            }
        } else if (i > k) {
            if (j < l) {
                actor.turnDirection = 768;
            } else if (j > l) {
                actor.turnDirection = 256;
            } else {
                actor.turnDirection = 512;
            }
        } else if (j < l) {
            actor.turnDirection = 1024;
        } else {
            actor.turnDirection = 0;
        }
        int i1 = actor.turnDirection - actor.anInt1552 & 0x7ff;
        if (i1 > 1024) {
            i1 -= 2048;
        }
        int j1 = actor.anInt1555;
        if (i1 >= -256 && i1 <= 256) {
            j1 = actor.anInt1554;
        } else if (i1 >= 256 && i1 < 768) {
            j1 = actor.anInt1557;
        } else if (i1 >= -768 && i1 <= -256) {
            j1 = actor.anInt1556;
        }
        if (j1 == -1) {
            j1 = actor.anInt1554;
        }
        actor.anInt1517 = j1;
        int k1 = 4;
        if (actor.anInt1552 != actor.turnDirection && actor.interactingEntity == -1 && actor.anInt1504 != 0) {
            k1 = 2;
        }
        if (actor.smallXYIndex > 2) {
            k1 = 6;
        }
        if (actor.smallXYIndex > 3) {
            k1 = 8;
        }
        if (actor.anInt1503 > 0 && actor.smallXYIndex > 1) {
            k1 = 8;
            actor.anInt1503--;
        }
        if (actor.aBooleanArray1553[actor.smallXYIndex - 1]) {
            k1 <<= 1;
        }
        if (k1 >= 8 && actor.anInt1517 == actor.anInt1554 && actor.runAnimation != -1) {
            actor.anInt1517 = actor.runAnimation;
        }
        if (i < k) {
            actor.x += k1;
            if (actor.x > k) {
                actor.x = k;
            }
        } else if (i > k) {
            actor.x -= k1;
            if (actor.x < k) {
                actor.x = k;
            }
        }
        if (j < l) {
            actor.y += k1;
            if (actor.y > l) {
                actor.y = l;
            }
        } else if (j > l) {
            actor.y -= k1;
            if (actor.y < l) {
                actor.y = l;
            }
        }
        if (actor.x == k && actor.y == l) {
            actor.smallXYIndex--;
            if (actor.anInt1542 > 0) {
                actor.anInt1542--;
            }
        }
    }

    public final String methodR(int i) {
        if (i >= 0 && i < 10000) {
            return String.valueOf(i);
        }

        if (i >= 10000 && i < 10000000) {
            return i / 1000 + "K";
        }

        if (i >= 10000000) {
            return i / 1000000 + "M";
        }

        if (i > Integer.MAX_VALUE) {
            return "*";
        } else {
            return "?";
        }
    }

    public boolean mouseInCircle(int centerX, int centerY, int radius) {
        return (super.mouseX - centerX) * (super.mouseX - centerX) + (super.mouseY - centerY) * (super.mouseY - centerY) < radius * radius;
    }

    private void npcScreenPos(Actor actor, int i) {
        calcEntityScreenPos(actor.x, i, actor.y);
    }

    private void nullLoader() {
        aBoolean831 = false;
        while (drawingFlames) {
            aBoolean831 = false;
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
            }
        }

        // anIntArray1190 = null;
        // anIntArray1191 = null;
    }

    private DataInputStream openJagGrabInputStream(String s) throws IOException {
        // if(!aBoolean872)
        // if(signlink.mainapp != null)
        // return signlink.openurl(s);
        // else
        // return new DataInputStream((new URL(getCodeBase(), s)).openStream());
        if (aSocket832 != null) {
            try {
                aSocket832.close();
            } catch (Exception _ex) {
            }
            aSocket832 = null;
        }
        aSocket832 = createFileServerSocket(Configuration.CLIENT_PORT);
        aSocket832.setSoTimeout(10000);
        java.io.InputStream inputstream = aSocket832.getInputStream();
        OutputStream outputstream = aSocket832.getOutputStream();
        outputstream.write(("JAGGRAB /" + s + "\n\n").getBytes());
        return new DataInputStream(inputstream);
    }

    private void startBarFill(int bar, boolean success) {
        RSInterface.interfaceCache[bar].fillBarActive = true;
        RSInterface.interfaceCache[bar].percentageCompleted = 0;
        TaskManager.submit(new Task(1, true) {
            int tick = 0;

            @Override
            protected void execute() {
                if (tick % 2 == 0) {
                    RSInterface.interfaceCache[bar].percentageCompleted += 1;
                }

                tick++;
                if (tick == 200) {
                    RSInterface.interfaceCache[bar].fillBarActive = false;
                    RSInterface.interfaceCache[bar].colorFull = (success ? 0x0fd900 : 0xd90a00);
                    getOut().putOpcode(124);
                    getOut().putInt(bar);
                    stop();
                }
            }

            @Override
            public void stop() {
                setEventRunning(false);
            }
        });
    }

    private boolean parsePacket() {
        if (getConnection() == null) {
            return false;
        }
        try {
            int available = getConnection().available();

            if (available == 0) {
                return false;
            }

            if (opCode == -1) {
                getConnection().flushInputStream(getInputBuffer().buffer, 1);
                opCode = getInputBuffer().buffer[0] & 0xff;

                if (getConnectionCipher() != null) {
                    opCode = opCode - getConnectionCipher().next() & 0xff;
                }

                getConnection().flushInputStream(getInputBuffer().buffer, 4);
                getInputBuffer().position = 0;
                pktSize = getInputBuffer().readInt();

                available-= 5;
            }


            if (available < pktSize) {
                return false;
            }

            getInputBuffer().position = 0;
            getConnection().flushInputStream(getInputBuffer().buffer, pktSize);
            anInt1009 = 0;
            anInt843 = previousPacket;
            previousPacket = anInt841;
            anInt841 = opCode;//oh u dont hold packet sizes on client

            switch (opCode) {
                case 167:
                    int items = getInputBuffer().readUnsignedByte();
                    OSRSCreationMenu.items = new ArrayList<>(items);
                    for (int i = 0; i < items; i++) {
                        int itemId = getInputBuffer().readInt();
                        OSRSCreationMenu.items.add(itemId);
                    }
                    inputDialogState = 5;
                    updateChatArea = true;
                    opCode = -1;
                return true;
                case 90:
                    int interId = getInputBuffer().getInt();
                    String message2 = getInputBuffer().getString();
                    if (RSInterface.interfaceCache[interId] != null) {
                        RSInterface.interfaceCache[interId].message = message2;
                    }
                    opCode = -1;
                return true;
                case 91:
                    int interId3 = getInputBuffer().getInt();
                    int buttonOnId = getInputBuffer().getInt();
                    int buttonOffId = getInputBuffer().getInt();
                    if (RSInterface.interfaceCache[interId3] != null) {
                        RSInterface.interfaceCache[interId3].buttonOn = spritesMap.get(buttonOnId);
                        RSInterface.interfaceCache[interId3].buttonOff = spritesMap.get(buttonOffId);
                    }
                    opCode = -1;
                    return true;
                case 92:
                    int interId4 = getInputBuffer().getInt();
                    String message4 = getInputBuffer().getString();
                    if (RSInterface.interfaceCache[interId4] != null) {
                        RSInterface.interfaceCache[interId4].tooltip = message4;
                    }
                    opCode = -1;
                    return true;
                case 100:
                    int interId2 = getInputBuffer().getInt();
                    int curVal = getInputBuffer().getShort();
                    int maxVal = getInputBuffer().getShort();
                    RSInterface.interfaceCache[interId2].progressBarCurrent = curVal;
                    RSInterface.interfaceCache[interId2].progressBarMax = maxVal;
                    opCode = -1;
                return true;
                case 111:
                    curVal = getInputBuffer().getInt();
                    maxVal = getInputBuffer().getInt();
                    int amountOfMilestones = getInputBuffer().getInt();
                    int interfaceComp = getInputBuffer().getInt();
                    Milestone[] milestones = new Milestone[amountOfMilestones];
                    for (int counter = 0; counter < amountOfMilestones; counter++) {
                        int milestoneCost = getInputBuffer().getInt();
                        int type = getInputBuffer().getInt();
                        Milestone.MilestoneType mileType = type == 1 ? Milestone.MilestoneType.ITEMS : Milestone.MilestoneType.DESCRIPTION;
                        if (mileType.equals(Milestone.MilestoneType.DESCRIPTION)) {
                            String desc = getInputBuffer().getString();
                            milestones[counter] = new Milestone(milestoneCost, desc, mileType, maxVal);

                        }
                        if (mileType.equals(Milestone.MilestoneType.ITEMS)) {
                            int amountOfItems = getInputBuffer().getInt();
                            DummyItem[] milestoneItems = new DummyItem[amountOfItems];
                            for (int counter2 = 0; counter2 < amountOfItems; counter2++) {
                                int itemId = getInputBuffer().readInt();
                                int amount = getInputBuffer().readInt();
                                milestoneItems[counter2] = new DummyItem(itemId, amount);
                            }
                            milestones[counter] = new Milestone(milestoneCost, milestoneItems, mileType, maxVal);
                        }
                    }
                    RSInterface.interfaceCache[interfaceComp].progressBarCurrent = curVal;
                    RSInterface.interfaceCache[interfaceComp].progressBarMax = maxVal;
                    RSInterface.interfaceCache[interfaceComp].milestones = milestones;
                    opCode = -1;
                    return true;
                case 82:
                    fadeInStartCycle = loopCycle - 1;
                    fadeInEndCycle = fadeInStartCycle + getInputBuffer().readByte();
                    hiddenEndCycle = fadeInEndCycle + getInputBuffer().readByte();
                    fadeOutEndCycle = hiddenEndCycle + getInputBuffer().readByte();
                    //System.out.println("Parsing fade packet");
                    opCode = -1;
                    return true;
                case 235:
                    int barId = getInputBuffer().readInt();
                    int bValue = getInputBuffer().readByte();
                    if (bValue == 2) {
                        RSInterface.interfaceCache[barId].fillBarActive = false;
                        RSInterface.interfaceCache[barId].percentageCompleted = 0;
                    } else {
                        boolean succezzful = bValue == 1;
                        startBarFill(barId, succezzful);
                    }
                    opCode = -1;
                    return true;
                case 179: // Opcode you've defined for layout toggle
                    //DropRateTimerInterface(tda);
                   // DamageBoostTimerInterface(tda);
                    //OverloadTimerInterface(tda);
                    isHorizontalLayout = !isHorizontalLayout;
                    opCode = -1; // Prepare for next packet
                    return true;
                case 6:
                    int interfaceScrollId = getInputBuffer().readInt();
                    int interfaceScrollMax = getInputBuffer().getUnsignedShort();
                    RSInterface scrollinter = RSInterface.get(interfaceScrollId);
                    if (scrollinter == null) {
                        Signlink.reportError("Scroll max packet no interface: " + interfaceScrollId + " " + interfaceScrollMax);
                        opCode = -1;
                        return true;
                    }
                    scrollinter.scrollMax = interfaceScrollMax;
                    opCode = -1;
                    return true;
                case 119:
                    int childIdentifier = getInputBuffer().readInt();
                    int modelZoom = getInputBuffer().readUnsignedWord();
                    RSInterface.get(childIdentifier).modelZoom = modelZoom;
                    opCode = -1;
                    return true;
                case 11:
                    int interfaceId = getInputBuffer().readInt();
                    boolean interfaceFlag1 = getInputBuffer().getUnsignedByte() == 1;
                    RSInterface.get(interfaceId).active = interfaceFlag1;
                    opCode = -1;
                    return true;
                case 220:
                    if (invOverlayInterfaceID != -1) {
                        invOverlayInterfaceID = -1;
                        tabAreaAltered = true;
                    }
                    if (backDialogID != -1) {
                        backDialogID = -1;
                        updateChatArea = true;
                    }
                    if (inputDialogState != 0) {
                        inputDialogState = 0;
                        updateChatArea = true;
                    }
                    opCode = -1;
                    return true;
                case 81:
                    updatePlayers(pktSize, getInputBuffer());
                    sendFrame36(175, openInterfaceID == 26000 ? 1 : 0);
                    aBoolean1080 = false;
                    opCode = -1;
                    return true;

                case 88:
                    int xface = getInputBuffer().getSignedShort();
                    int yface = getInputBuffer().getSignedShort();
                    int npcindex = getInputBuffer().getShortBigEndian();
                    if (npcindex < npcArray.length) {
                        Npc npc = npcArray[npcindex];
                        if (npc != null) {
                            npc.anInt1538 = xface;
                            npc.anInt1539 = yface;
                        }
                    }
                    opCode = -1;
                    return true;

                case 190:
                    int intId = getInputBuffer().getInt();
                    int npcId = getInputBuffer().getShort();
                    int adjustedZoom = getInputBuffer().getShort();
                    RSInterface npcOnInterface1 = RSInterface.interfaceCache[intId];

                    npcOnInterface1.npcDisplay = npcId;
                    npcOnInterface1.contentId = npcId;
                    npcOnInterface1.modelZoom = adjustedZoom;

                    opCode = -1;
                    return true;

                case 199:
                    int firstItem = getInputBuffer().getShort();
                    int secondItem = getInputBuffer().getShort();
                    int thirdItem = getInputBuffer().getShort();
                    System.out.println("Reading from server: " + firstItem + "," + secondItem + "," + thirdItem);
                    RSInterface.interfaceCache[23635].itemsToDraw = new int[]{firstItem, secondItem, thirdItem};
                    opCode = -1;
                    return true;

                case 139:
                    int slotType = getInputBuffer().getShort();
                    if (slotType == 0) {
                       // RSInterface.getCustomInterfaces().slotItemPositions();
                    } else {
                        SlotSystem.testSlotChange();
                    }
                    opCode = -1;
                    return true;

                case 176:
                    daysSinceRecovChange = getInputBuffer().method427();
                    unreadMessages = getInputBuffer().method435();
                    membersInt = getInputBuffer().getUnsignedByte();
                    anInt1193 = getInputBuffer().method440();
                    daysSinceLastLogin = getInputBuffer().getUnsignedShort();

                    opCode = -1;
                    return true;

                case 64:
                    anInt1268 = getInputBuffer().method427();
                    anInt1269 = getInputBuffer().method428();

                    for (int j = anInt1268; j < anInt1268 + 8; j++) {
                        for (int l9 = anInt1269; l9 < anInt1269 + 8; l9++) {
                            if (groundArray[plane][j][l9] != null) {
                                groundArray[plane][j][l9] = null;
                                spawnGroundItem(j, l9);
                            }
                        }
                    }

                    for (SceneSpawnNode sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetFirst(); sceneSpawnNode != null; sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetNext()) {
                        if (sceneSpawnNode.anInt1297 >= anInt1268 && sceneSpawnNode.anInt1297 < anInt1268 + 8 && sceneSpawnNode.anInt1298 >= anInt1269 && sceneSpawnNode.anInt1298 < anInt1269 + 8 && sceneSpawnNode.anInt1295 == plane) {
                            sceneSpawnNode.anInt1294 = 0;
                        }
                    }
                    opCode = -1;
                    return true;

                case 185:

                    int k = getInputBuffer().getShortBigEndianA();

                    RSInterface.interfaceCache[k].mediaType = 3;
                    if (myPlayer.desc == null) {
                        RSInterface.interfaceCache[k].mediaID = (myPlayer.anIntArray1700[0] << 25) + (myPlayer.anIntArray1700[4] << 20) + (myPlayer.equipment[0] << 15) + (myPlayer.equipment[8] << 10) + (myPlayer.equipment[11] << 5) + myPlayer.equipment[1];
                    } else {
                        RSInterface.interfaceCache[k].mediaID = (int) (0x12345678L + myPlayer.desc.id);
                    }
                    opCode = -1;
                    return true;
                case 233:
                    int modelComponentId = getInputBuffer().getShort();
                    int item = getInputBuffer().getShort();
                    RSInterface.interfaceCache[modelComponentId].mediaID = item;
                    cardPack.startExpand();
                    opCode = -1;
                    return true;
                case 232:
                    int componentId = getInputBuffer().getShort();
                    boolean visible = getInputBuffer().getByte() == 1;
                    RSInterface.interfaceCache[componentId].setVisible(visible);
                    opCode = -1;
                    return true;

                case 225:
                    int widgetId = getInputBuffer().getShort();
                    WheelOfFortune wheel = RSInterface.interfaceCache[widgetId].wheel;
                    byte index = getInputBuffer().getByte();
                    byte len = getInputBuffer().getByte();
                    int[] items2 = new int[len];
                    for (int j = 0; j < len; j++) {
                        items2[j] = getInputBuffer().getShort();
                    }
                    wheel.setItems(items2);
                    wheel.setIndex(index);
                    wheel.init();
                    opCode = -1;
                    return true;

                /* Clan chat packet */
                case 217:
                    try {
                        name = getInputBuffer().getString();
                        message = getInputBuffer().getString();
                        clanName = getInputBuffer().getString();
                        rights = getInputBuffer().getUnsignedShort();
                        /*
                         * String addon = null; if(rights < 4) addon = "@cr"+rights+"@";
                         */
                        String tag = name.replaceAll("null", "");
                        message = ChatEncoder.processText(message);
                        // message = Censor.doCensor(message);
                        pushMessage(message, 16, tag);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    opCode = -1;
                    return true;

                case 107:
                    oriented = false;
                    for (int l = 0; l < 5; l++) {
                        QuakeDirectionActive[l] = false;
                    }
                    opCode = -1;
                    return true;

                case 72:
                    int i1 = getInputBuffer().getShortBigEndian();
                    RSInterface class9 = RSInterface.interfaceCache[i1];
                    for (int k15 = 0; k15 < class9.inv.length; k15++) {
                        class9.inv[k15] = -1;
                        class9.inv[k15] = 0;
                    }
                    Interfaces.containerUpdated(i1);
                    opCode = -1;
                    return true;

                case 214:
                    ignoreCount = getInputBuffer().getShort();
                    for (int i2 = 0; i2 < ignoreCount; i2++) {
                        ignoreListAsLongs[i2] = Long.parseLong(getInputBuffer().getString());
                    }
                    opCode = -1;
                    return true;

                case 244:
                    String data = getInputBuffer().getString();
                    getGrandExchange().update(data);
                    opCode = -1;
                    return true;

                case 243:
                    ClientSettings.newFunctionKeys = getInputBuffer().readByte();
                    ClientSettings.newHealthBars = getInputBuffer().readByte();
                    ClientSettings.newHitmarks = getInputBuffer().readByte();
                    ClientSettings.newCursors = getInputBuffer().readByte();
                    ClientSettings.usernamesAboveHead = getInputBuffer().readByte();
                    ClientSettings.toggleParticles = getInputBuffer().readByte();
                    ClientSettings.groundItemNames = getInputBuffer().readByte();
                    ClientSettings.usernameHighlighting = getInputBuffer().readByte();
                    ClientSettings.levelUpMessages = getInputBuffer().readByte();
                    ClientSettings.placeholder = getInputBuffer().readByte();

                    ClientSettings.viewDistance = getInputBuffer().readInt();
                    ClientSettings.textureAnimationSpeed = getInputBuffer().readInt();
                    int asfd = getInputBuffer().readInt();
                    int bbb = getInputBuffer().readInt();
                    ClientSettings.clamp();
                   // System.out.println("Received " + ClientSettings.viewDistance);
                    opCode = -1;
                    return true;


                case 166:
                    int l7 = getInputBuffer().readInt();
                    resetInterfaceAnimation(l7);

                    if (invOverlayInterfaceID != -1) {
                        invOverlayInterfaceID = -1;
                        tabAreaAltered = true;
                    }

                    if (backDialogID != -1) {
                        backDialogID = -1;
                        updateChatArea = true;
                    }

                    if (inputDialogState != 0) {
                        inputDialogState = 0;
                        updateChatArea = true;
                    }
                    openInterfaceID = l7;
                    aBoolean1149 = false;
                    opCode = -1;
                    return true;

                case 134:
                    // needDrawTabArea = true;

                    int skillId = getInputBuffer().getUnsignedByte();
                    int exp = getInputBuffer().method439();
                    int level = getInputBuffer().getUnsignedShort();
                    int maxLevel = getInputBuffer().getUnsignedShort();
                    int gainedExperience = exp - currentExp[skillId];
                    currentExp[skillId] = exp;
                    currentStats[skillId] = level;
                    maxStats[skillId] = maxLevel;
                    if (gainedExperience > 0) {
                        PlayerUtilities.addXP(skillId, gainedExperience);
                    }
                    opCode = -1;
                    return true;

                case 71:
                    int l1 = getInputBuffer().readInt();
                    int j10 = getInputBuffer().method426();
                    if (l1 == 65535) {
                        l1 = -1;
                    }
                    tabInterfaceIDs[j10] = l1;
                    prayerInterfaceType = tabInterfaceIDs[5];
                    tabAreaAltered = true;
                    opCode = -1;
                    return true;

                case 74:
                    int songId = getInputBuffer().getShortBigEndian();
                    if (songId == 65535) {
                        songId = -1;
                    }
                    if (songId != -1 || prevSong != 0) {
                        if (songId != -1 && currentSong != songId && musicVolume != 0 && prevSong == 0) {
                            method58(10, musicVolume, false, songId);
                        }
                    } else {
                        method55(false);
                    }
                    currentSong = songId;
                    opCode = -1;
                    return true;

                case 121:
                    int tempSongId = getInputBuffer().getShortBigEndianA();
                    int tempSongDelay = getInputBuffer().method435();
                    if (tempSongId == 65535) {
                        tempSongId = -1;
                    }
                    if (musicVolume != 0 && tempSongDelay != -1) {
                        method56(musicVolume, false, tempSongId);
                        prevSong = tempSongDelay * 20;
                    }
                    opCode = -1;
                    return true;

                case 109:
                    resetLogout();
                    opCode = -1;
                    return false;

                case 70:
                    int modifierX = getInputBuffer().getSignedShort();
                    int modifierY = getInputBuffer().method437();
                    int widgetId1 = getInputBuffer().getShortBigEndian();
                    RSInterface widget = RSInterface.interfaceCache[widgetId1];
                    for (int i = 0; i < validIds.length; i++) {
                        if (validIds[i] == widgetId1) {
                            if (modifierX == 500) {
                                specAmounts[i] = 10;
                            }
                            if (modifierX == 0) {
                                specAmounts[i] = 0;
                            }
                        }

                    }
                    widget.xOffset = modifierX;
                    widget.yOffset = modifierY;
                    opCode = -1;
                    return true;

                case 73:
                case 241:
                    int l2 = currentRegionX;
                    int i11 = currentRegionY;
                    if (opCode == 73) {
                        l2 = mapX = getInputBuffer().method435();
                        i11 = mapY = getInputBuffer().getUnsignedShort();
                        requestMapReconstruct = false;
                    }
                    if (opCode == 241) {
                        l2 = getInputBuffer().getUnsignedShort();
                        i11 = getInputBuffer().method435();
                        for (int j16 = 0; j16 < 4; j16++) {
                            for (int l20 = 0; l20 < 13; l20++) {
                                for (int j23 = 0; j23 < 13; j23++) {
                                    int emptyFloor = getInputBuffer().getUnsignedByte();
                                    if (emptyFloor == 1) {
                                        constructRegionData[j16][l20][j23] = getInputBuffer().getInt();
                                    } else {
                                        constructRegionData[j16][l20][j23] = -1;
                                    }
                                }
                            }
                        }
                        requestMapReconstruct = true;
                    }
                    if (currentRegionX == l2 && currentRegionY == i11 && loadingStage == 2) {
                        opCode = -1;
                        return true;
                    }
                    currentRegionX = l2;
                    currentRegionY = i11;
                    regionBaseX = (currentRegionX - 6) * 8;
                    regionBaseY = (currentRegionY - 6) * 8;
                    aBoolean1141 = (currentRegionX / 8 == 48 || currentRegionX / 8 == 49) && currentRegionY / 8 == 48;
                    if (currentRegionX / 8 == 48 && currentRegionY / 8 == 148) {
                        aBoolean1141 = true;
                    }
                    loadingStage = 1;
                    aLong824 = System.currentTimeMillis();
                    gameScreenIP.initDrawingArea();
                    spritesMap.get(1105).drawSprite(8, 9);
                    // drawLoadingMessages(1, "Loading - please wait.", null);
                    if (!resizing) {
                        gameScreenIP.drawGraphics(canvas.getGraphics(), gameScreenDrawX, gameScreenDrawY);
                    }
                    if (opCode == 73) {
                        int k16 = 0;
                        for (int i21 = (currentRegionX - 6) / 8; i21 <= (currentRegionX + 6) / 8; i21++) {
                            for (int k23 = (currentRegionY - 6) / 8; k23 <= (currentRegionY + 6) / 8; k23++) {
                                k16++;
                            }
                        }
                        terrainData = new byte[k16][];
                        objectData = new byte[k16][];
                        mapCoordinates = new int[k16];
                        floorMap = new int[k16];
                        objectMap = new int[k16];
                        k16 = 0;
                        for (int l23 = (currentRegionX - 6) / 8; l23 <= (currentRegionX + 6) / 8; l23++) {
                            for (int j26 = (currentRegionY - 6) / 8; j26 <= (currentRegionY + 6) / 8; j26++) {
                                mapCoordinates[k16] = (l23 << 8) + j26;
                                if (aBoolean1141 && (j26 == 49 || j26 == 149 || j26 == 147 || l23 == 50 || l23 == 49 && j26 == 47)) {
                                    floorMap[k16] = -1;
                                    objectMap[k16] = -1;
                                    k16++;
                                } else {
                                    int k28 = floorMap[k16] = onDemandFetcher.getMapCount(0, j26, l23);
                                    if (k28 != -1) {
                                        onDemandFetcher.requestFileData(3, k28);
                                    }
                                    int j30 = objectMap[k16] = onDemandFetcher.getMapCount(1, j26, l23);
                                    if (j30 != -1) {
                                        onDemandFetcher.requestFileData(3, j30);
                                    }
                                    k16++;
                                }
                            }
                        }
                    }
                    if (opCode == 241) {
                        int l16 = 0;
                        int[] ai = new int[676];
                        for (int i24 = 0; i24 < 4; i24++) {
                            for (int k26 = 0; k26 < 13; k26++) {
                                for (int l28 = 0; l28 < 13; l28++) {
                                    int k30 = constructRegionData[i24][k26][l28];
                                    if (k30 != -1) {
                                        int k31 = k30 >> 14 & 0x3ff;
                                        int i32 = k30 >> 3 & 0x7ff;
                                        int k32 = (k31 / 8 << 8) + i32 / 8;
                                        for (int j33 = 0; j33 < l16; j33++) {
                                            if (ai[j33] != k32) {
                                                continue;
                                            }
                                            k32 = -1;

                                        }
                                        if (k32 != -1) {
                                            ai[l16++] = k32;
                                        }
                                    }
                                }
                            }
                        }
                        terrainData = new byte[l16][];
                        objectData = new byte[l16][];
                        mapCoordinates = new int[l16];
                        floorMap = new int[l16];
                        objectMap = new int[l16];
                        for (int l26 = 0; l26 < l16; l26++) {
                            int i29 = mapCoordinates[l26] = ai[l26];
                            int l30 = i29 >> 8 & 0xff;
                            int l31 = i29 & 0xff;
                            int j32 = floorMap[l26] = onDemandFetcher.getMapCount(0, l31, l30);
                            if (j32 != -1) {
                                onDemandFetcher.requestFileData(3, j32);
                            }
                            int i33 = objectMap[l26] = onDemandFetcher.getMapCount(1, l31, l30);
                            if (i33 != -1) {
                                onDemandFetcher.requestFileData(3, i33);
                            }
                        }
                    }
                    int i17 = regionBaseX - anInt1036;
                    int j21 = regionBaseY - anInt1037;
                    anInt1036 = regionBaseX;
                    anInt1037 = regionBaseY;
                    for (int j24 = 0; j24 < 16384; j24++) {
                        Npc npc = npcArray[j24];
                        if (npc != null) {
                            for (int j29 = 0; j29 < 10; j29++) {
                                npc.smallX[j29] -= i17;
                                npc.smallY[j29] -= j21;
                            }
                            npc.x -= i17 * 128;
                            npc.y -= j21 * 128;
                        }
                    }
                    for (int i27 = 0; i27 < getMaxPlayers(); i27++) {
                        Player player = playerArray[i27];
                        if (player != null) {
                            for (int i31 = 0; i31 < 10; i31++) {
                                player.smallX[i31] -= i17;
                                player.smallY[i31] -= j21;
                            }
                            player.x -= i17 * 128;
                            player.y -= j21 * 128;
                        }
                    }
                    aBoolean1080 = true;
                    byte byte1 = 0;
                    byte byte2 = 104;
                    byte byte3 = 1;
                    if (i17 < 0) {
                        byte1 = 103;
                        byte2 = -1;
                        byte3 = -1;
                    }
                    byte byte4 = 0;
                    byte byte5 = 104;
                    byte byte6 = 1;
                    if (j21 < 0) {
                        byte4 = 103;
                        byte5 = -1;
                        byte6 = -1;
                    }
                    for (int k33 = byte1; k33 != byte2; k33 += byte3) {
                        for (int l33 = byte4; l33 != byte5; l33 += byte6) {
                            int i34 = k33 + i17;
                            int j34 = l33 + j21;
                            for (int k34 = 0; k34 < 4; k34++) {
                                if (i34 >= 0 && j34 >= 0 && i34 < 104 && j34 < 104) {
                                    groundArray[k34][k33][l33] = groundArray[k34][i34][j34];
                                } else {
                                    groundArray[k34][k33][l33] = null;
                                }
                            }
                        }
                    }
                    for (SceneSpawnNode sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetFirst(); sceneSpawnNode != null; sceneSpawnNode = (SceneSpawnNode) getaClass19_1179().reverseGetNext()) {
                        sceneSpawnNode.anInt1297 -= i17;
                        sceneSpawnNode.anInt1298 -= j21;
                        if (sceneSpawnNode.anInt1297 < 0 || sceneSpawnNode.anInt1298 < 0 || sceneSpawnNode.anInt1297 >= 104 || sceneSpawnNode.anInt1298 >= 104) {
                            sceneSpawnNode.unlink();
                        }
                    }
                    if (destinationX != 0) {
                        destinationX -= i17;
                        destinationY -= j21;
                    }
                    oriented = false;
                    opCode = -1;
                    return true;

                case 80:
                    int childId2 = getInputBuffer().getUnsignedShort();
                    int size = getInputBuffer().getUnsignedShort();
                    final int[][] loot = new int[size][2];
                    for (int i = 0; i < size; i++) {
                        final int itemId = getInputBuffer().getUnsignedShort();
                        final int amount = getInputBuffer().getUnsignedShort();
                        System.out.println("itemId : " + itemId + " amount: " + amount);
                        loot[i][0] = itemId;
                        loot[i][1] = amount;
                    }
                    RSInterface.setScrollableItems(RSInterface.interfaceCache[childId2], loot);
                    opCode = -1;
                    return true;

                case 231:
                    final int npc_id = getInputBuffer().getUnsignedShort();
                    interfaceId = getInputBuffer().getUnsignedShort();
                    final RSInterface npcOnInterface = RSInterface.interfaceCache[interfaceId];
                    if (npcOnInterface != null) {
                        npcOnInterface.contentId = npc_id;
                    }
                    opCode = -1;

                    return true;
                case 140:
                    int childId = getInputBuffer().readInt();
                    int interfaceState = getInputBuffer().getUnsignedShort();
                    int interfaceState3 = getInputBuffer().getUnsignedByte();
                    updateProgressBar(childId, interfaceState, interfaceState3, 0);
                    opCode = -1;
                    return true;
                case 150: // Bingo board data
                    int[][] bingoItems = new int[5][5];
                    boolean[][] bingoMarked = new boolean[5][5];
                    for (int row = 0; row < 5; row++) {
                        for (int col = 0; col < 5; col++) {
                            bingoItems[row][col] = getInputBuffer().readUnsignedWord(); // Read item ID
                            bingoMarked[row][col] = getInputBuffer().readUnsignedByte() == 1; // Read marked state
                        }
                    }
                    updateBingoInterface(bingoItems, bingoMarked);
                    break;

                case 209:
                    int interfaceId_ = getInputBuffer().getInt();
                    int newId = getInputBuffer().getShort();
                    if (RSInterface.interfaceCache[interfaceId_] != null) {
                        RSInterface.interfaceCache[interfaceId_].disabledSprite = newId == 65535 ? null : spritesMap.get(newId);
                        RSInterface.interfaceCache[interfaceId_].enabledSprite = newId == 65535 ? null : spritesMap.get(newId);
                    }

                    opCode = -1;
                    return true;
                case 10: // w.e else u need, that's all okie cool

                    int spriteId = getInputBuffer().getUnsignedShort();
                    boolean remove = getInputBuffer().getUnsignedShort() == 1;
                    Optional<BossOverlay> optionalBossOverlay =
                            BossOverlay.BOSS_OVERLAYS
                                    .parallelStream()
                                    .filter(it -> it.getSpriteId() == spriteId)
                                    .findFirst();
                    if(remove) {
                        if(optionalBossOverlay.isPresent()) {
                          //  System.out.println("Removing");
                        }
                        optionalBossOverlay.ifPresent(BossOverlay::remove);
                    } else {
                        if(!optionalBossOverlay.isPresent()) {
                           // System.out.println("Adding");
                            BossOverlay overlay = new BossOverlay(spriteId);
                            BossOverlay.BOSS_OVERLAYS.add(overlay);
                            BossOverlay.position(overlay);
                        }
                    }
                    opCode = -1;
                    return true;
                case 208:

                    int interfaceId1 = getInputBuffer().readInt();
                    boolean add = getInputBuffer().readUnsignedWord() == 1;
                    RSInterface widget1 = RSInterface.interfaceCache[interfaceId1];

                    if (widget1 != null) {
                        if (add) {
                            if (!parallelWidgetList.contains(widget1)) {
                                parallelWidgetList.add(widget1);
                            }
                        } else {
                            parallelWidgetList.remove(widget1);
                        }
                    }
                    opCode = -1;
                    return true;
                case 188:
                    int length2 = getInputBuffer().getByte();
                    turnOffCurses();
                    mapArea.prayer.setOrbState(false);
                    opCode = -1;
                    return true;
                case 99:
                    minimapStatus = getInputBuffer().getUnsignedByte();
                    opCode = -1;
                    return true;

                case 75:
                    int j3 = getInputBuffer().getShortBigEndianA();
                    int j11 = getInputBuffer().getShortBigEndianA();
                    RSInterface.interfaceCache[j11].mediaType = 2;
                    RSInterface.interfaceCache[j11].mediaID = j3;
                    opCode = -1;
                    return true;

                case 114:
                    systemUpdateTimer = getInputBuffer().getShortBigEndian() * 30;
                    opCode = -1;
                    return true;


                case 120:
                    String bcMessage = getInputBuffer().getString();
                    int broadcastTime = getInputBuffer().getInt();

                    broadcastMinutes = broadcastTime * 30;
                    broadcastMessage = bcMessage;

                    opCode = -1;
                    return true;


                case 60:
                    anInt1269 = getInputBuffer().getUnsignedByte();
                    anInt1268 = getInputBuffer().method427();
                    while (getInputBuffer().position < pktSize) {
                        int k3 = getInputBuffer().getUnsignedByte();
                        parseEntityPacket(getInputBuffer(), k3);
                    }
                    opCode = -1;
                    return true;

                case 35:
                    int l3 = getInputBuffer().getUnsignedByte();
                    int k11 = getInputBuffer().getUnsignedByte();
                    int j17 = getInputBuffer().getUnsignedByte();
                    int k21 = getInputBuffer().getUnsignedByte();
                    QuakeDirectionActive[l3] = true;
                    QuakeMagnitudes[l3] = k11;
                    QuakeAmplitudes[l3] = j17;
                    Quake4PiOverPeriods[l3] = k21;
                    QuakeTimes[l3] = 0;
                    opCode = -1;
                    return true;

                case 175:
                        int id = getInputBuffer().getUnsignedShort();
                        int type = getInputBuffer().getUnsignedByte();
                        int delay = getInputBuffer().getUnsignedShort();
                        sound[soundCount] = id;
                        soundType[soundCount] = type;
                        soundDelay[soundCount] = delay;
                        soundCount++;
                        opCode = -1;
                    System.out.println("sound sent");
                    System.out.println("SOUND ID: " + id);
                    System.out.println("SOUND TYPE: " + type);
                    System.out.println("SOUND DELAY: " + delay);
                    System.out.println("SOUND COUNT: " + soundCount);
                    System.out.println("VOLUME: " + soundEffectVolume);
                        return true;

                case 104:
                    int j4 = getInputBuffer().method427();
                    int i12 = getInputBuffer().method426();
                    String s6 = getInputBuffer().getString();
                    if (j4 >= 1 && j4 <= 7) {
                        if (s6.equalsIgnoreCase("null")) {
                            s6 = null;
                        }
                        atPlayerActions[j4 - 1] = s6;
                        atPlayerArray[j4 - 1] = i12 == 0;
                    }
                    opCode = -1;
                    return true;

                case 78:
                    destinationX = 0;
                    opCode = -1;
                    return true;
                case 253:
                    String s = getInputBuffer().getString();

                    if (s.startsWith("newmapsopening##")) {
                        String[] boxargs = s.split("##");
                        if (boxargs[1] != null) {
                            newmapsopening.handledPacket34(Integer.parseInt(boxargs[1]), Integer.parseInt(boxargs[2]) + 1, Integer.parseInt(boxargs[3]), Integer.parseInt(boxargs[4]));
                        }
                        opCode = -1;
                        return true;
                    }

                    if (s.startsWith("NewAlert##")) {
                        String[] args = s.split("##");
                        String[] args1 = s.split("@");
                        if (args.length == 3) {
                            alertHandler.alert = new Alert("hi", args[1], args[2]);
                        } else if (args.length == 4) {
                            alertHandler.alert = new Alert(args[1], args[2], args[3]);
                        }
                        opCode = -1;
                        return true;
                    }

                    if (s.equals(":loadnewmapss")) {
                        newmapsopening.setSpinClick(true);
                        opCode = -1;
                        return true;
                    }
                    if (s.equals(":resetnewmapss")) {
                        newmapsopening.reset();
                        opCode = -1;
                        return true;
                    }

                    if (consoleOpen) {
                        printConsoleMessage(s, 0);
                    } else if (s.equals(":spinner:")) {
                        RSInterface w2 = RSInterface.interfaceCache[47020];
                        RSInterface reward = RSInterface.interfaceCache[47023];
                        RSInterface winnings = RSInterface.interfaceCache[47029];
                        w2.childX[0] = 0; // sprite
                        w2.childX[1] = 3; // item container
                        spin = true;
                        spinSpeed = 1;
                    } else if (s.equals(":refreshspinner:")) {
                        RSInterface winnings = RSInterface.interfaceCache[47029];
                        RSInterface reward = RSInterface.interfaceCache[47023];
                    } else if (s.endsWith(":tradereq:")) {
                        String s3 = s.substring(0, s.indexOf(":"));
                        long l17 = TextUtilities.longForName(s3);
                        boolean flag2 = false;
                        for (int j27 = 0; j27 < ignoreCount; j27++) {
                            if (ignoreListAsLongs[j27] != l17) {
                                continue;
                            }
                            flag2 = true;

                        }
                        if (!flag2 && anInt1251 == 0) {
                            pushMessage("wishes to trade with you.", 4, s3);
                        }
                    } else if (s.endsWith(":gamblereq:")) {
                        String s21 = s.substring(0, s.indexOf(":"));
                        long l21 = TextUtilities.longForName(s21);
                        boolean flag2 = false;
                        for (int j27 = 0; j27 < ignoreCount; j27++) {
                            if (ignoreListAsLongs[j27] != l21) {
                                continue;
                            }
                            flag2 = true;

                        }
                        if (!flag2 && s21.length() >= 2) {
                            pushMessage("wishes to gamble with you. Click here to accept the invitation.", 21, s21);
                        }
                    } else if (s.endsWith(":raidreq:")) {
                        String s3 = s.substring(0, s.indexOf(":"));
                        long l17 = TextUtilities.longForName(s3);
                        boolean flag2 = false;
                        for (int j27 = 0; j27 < ignoreCount; j27++) {
                            if (ignoreListAsLongs[j27] != l17) {
                                continue;
                            }
                            flag2 = true;

                        }
                        if (!flag2 && anInt1251 == 0) {
                            pushMessage("invited you to their Raids party. Click here to accept the invitation.", 22, s3);
                        }
                    } else if (s.startsWith(":clan:")) {
                        String bracketColor = "<col=16777215>";
                        String clanNameColor = "<col=255>";
                        String nameColor = "@red@";
                        int length = Integer.parseInt(s.substring(6, 8));
                        s = s.substring(8);
                        String originals = s;
                        // System.out.println(originals);
                        s = bracketColor + originals.substring(0, 1) + "@blu@" + originals.substring(1, length) + bracketColor + originals.substring(length, length + 1) + CLAN_CHAT_COLORS[clanChatColor] + "<shad=0>" + originals.substring(length + 1);
                        // System.out.println(originals);
                        /* originals.substring(length)+ChatArea.CLAN_CHAT_COLORS[splitChatColor]+ */
                        // originals.substring(5+length+ChatArea.CLAN_CHAT_COLORS[splitChatColor].length());

                        pushMessage(/*
                         * "<col="+ChatArea.CLAN_CHAT_COLORS[splitChatColor]+">"+ChatArea.
                         * CLAN_CHAT_COLORS[splitChatColor]+""+
                         */s, 16, "");
                    } else if (s.endsWith("#url#")) {
                        String link = s.substring(0, s.indexOf("#"));
                        pushMessage("Join us at: ", 9, link);
                    } else if (s.endsWith(":duelreq:")) {
                        String s4 = s.substring(0, s.indexOf(":"));
                        long l18 = TextUtilities.longForName(s4);
                        boolean flag3 = false;
                        for (int k27 = 0; k27 < ignoreCount; k27++) {
                            if (ignoreListAsLongs[k27] != l18) {
                                continue;
                            }
                            flag3 = true;

                        }
                        if (!flag3 && anInt1251 == 0) {
                            pushMessage("wishes to duel with you.", 8, s4);
                        }
                    } else if (s.endsWith(":chalreq:")) {
                        String s5 = s.substring(0, s.indexOf(":"));
                        long l19 = TextUtilities.longForName(s5);
                        boolean flag4 = false;
                        for (int l27 = 0; l27 < ignoreCount; l27++) {
                            if (ignoreListAsLongs[l27] != l19) {
                                continue;
                            }
                            flag4 = true;

                        }
                        if (!flag4 && anInt1251 == 0) {
                            String s8 = s.substring(s.indexOf(":") + 1, s.length() - 9);
                            pushMessage(s8, 8, s5);
                        }
                    } else if (s.startsWith("scrollreset=")) {
                        try {
                            RSInterface.get(Integer.parseInt(s.replace("scrollreset=", ""))).scrollPosition = 0;
                        } catch (Exception e) {
                            Signlink.reportError("Error resetting scrollbar");
                        }
                        opCode = -1;
                        return true;
                    } else if (s.startsWith("Alert##")) {
                        String[] localObject3 = s.split("##");

                        System.out.println(localObject3.length);
                        //if (localObject3.length == 10) {
                        alertManager.setAlert(new AlertBox(localObject3[1], Integer.parseInt(localObject3[2]), Integer.parseInt(localObject3[3]), Integer.parseInt(localObject3[4]), Integer.parseInt(localObject3[5]), Integer.parseInt(localObject3[6]), localObject3[7], localObject3[8], localObject3[9]));

                        // }

                        opCode = -1;
                        return true;
                    } else {
                        pushMessage(s, 0, "");
                    }
                    opCode = -1;
                    return true;

                case 1:
                    for (int k4 = 0; k4 < playerArray.length; k4++) {
                        if (playerArray[k4] != null) {
                            playerArray[k4].anim = -1;
                        }
                    }
                    for (int j12 = 0; j12 < npcArray.length; j12++) {
                        if (npcArray[j12] != null) {
                            npcArray[j12].anim = -1;
                        }
                    }
                    opCode = -1;
                    return true;

                case 50:
                    long l4 = getInputBuffer().getLong();
                    int i18 = getInputBuffer().getUnsignedByte();
                    String s7 = TextUtilities.fixName(TextUtilities.nameForLong(l4));
                    for (int k24 = 0; k24 < friendCount; k24++) {
                        if (l4 != friendsListAsLongs[k24]) {
                            continue;
                        }
                        if (friendsNodeIDs[k24] != i18) {
                            friendsNodeIDs[k24] = i18;
                              //if (i18 >= 2) { pushMessage(s7 + " has logged in.", 5, ""); } if (i18 <= 1) {
                             // pushMessage(s7 + " has logged out.", 5, ""); }

                        }
                        s7 = null;

                    }
                    if (s7 != null && friendCount < 200) {
                        friendsListAsLongs[friendCount] = l4;
                        friendsList[friendCount] = s7;
                        friendsNodeIDs[friendCount] = i18;
                        friendCount++;
                    }
                    for (boolean flag6 = false; !flag6; ) {
                        flag6 = true;
                        for (int k29 = 0; k29 < friendCount - 1; k29++) {
                            if (friendsNodeIDs[k29] != nodeID && friendsNodeIDs[k29 + 1] == nodeID || friendsNodeIDs[k29] == 0 && friendsNodeIDs[k29 + 1] != 0) {
                                int j31 = friendsNodeIDs[k29];
                                friendsNodeIDs[k29] = friendsNodeIDs[k29 + 1];
                                friendsNodeIDs[k29 + 1] = j31;
                                String s10 = friendsList[k29];
                                friendsList[k29] = friendsList[k29 + 1];
                                friendsList[k29 + 1] = s10;
                                long l32 = friendsListAsLongs[k29];
                                friendsListAsLongs[k29] = friendsListAsLongs[k29 + 1];
                                friendsListAsLongs[k29 + 1] = l32;
                                flag6 = false;
                            }
                        }
                    }
                    opCode = -1;
                    return true;

                case 110:
                    energy = getInputBuffer().getUnsignedByte();
                    opCode = -1;
                    return true;

                case 113:
                    running = getInputBuffer().getUnsignedByte() > 0;
                    variousSettings[173] = running ? 1 : 0;
                    mapArea.run.setOrbState(running);
                    opCode = -1;
                    return true;

                case 129:
                    int progressBarPercentage = getInputBuffer().getUnsignedByte();
                    int childProgressBar = getInputBuffer().readInt();
                    RSInterface.interfaceCache[childProgressBar].percentageCompleted = progressBarPercentage;
                    opCode = -1;
                    return true;

                case 254:
                    hintIconDrawType = getInputBuffer().getUnsignedByte();
                    if (hintIconDrawType == 1) { //Head Hint
                        hintIconNpcId = getInputBuffer().getUnsignedShort();
                    }
                    if (hintIconDrawType >= 2 && hintIconDrawType <= 6) { //Map Positional
                        if (hintIconDrawType == 2) {
                            hintIconLocationArrowRelX = 64;
                            hintIconLocationArrowRelY = 64;
                        }
                        if (hintIconDrawType == 3) {
                            hintIconLocationArrowRelX = 0;
                            hintIconLocationArrowRelY = 64;
                        }
                        if (hintIconDrawType == 4) {
                            hintIconLocationArrowRelX = 128;
                            hintIconLocationArrowRelY = 64;
                        }
                        if (hintIconDrawType == 5) {
                            hintIconLocationArrowRelX = 64;
                            hintIconLocationArrowRelY = 0;
                        }
                        if (hintIconDrawType == 6) {
                            hintIconLocationArrowRelX = 64;
                            hintIconLocationArrowRelY = 128;
                        }
                        hintIconDrawType = 2;
                        hintIconX = getInputBuffer().getUnsignedShort();
                        hintIconY = getInputBuffer().getUnsignedShort();
                        hintIconZ = getInputBuffer().getUnsignedByte();
                    }
                    if (hintIconDrawType == 10) { //Player
                        hintIconPlayerId = getInputBuffer().getUnsignedShort();
                    }
                    opCode = -1;
                    return true;

                case 248:
                case 250:
                    int i5;
                    int k12;
                    if (opCode == 248) {
                        i5 = getInputBuffer().getInt();
                        k12 = getInputBuffer().getUnsignedShort();
                    } else {
                        i5 = getInputBuffer().readInt();
                        k12 = getInputBuffer().readInt();
                    }
                    if (backDialogID != -1) {
                        backDialogID = -1;
                        updateChatArea = true;
                    }
                    if (inputDialogState != 0) {
                        inputDialogState = 0;
                        updateChatArea = true;
                    }
                    openInterfaceID = i5;
                    invOverlayInterfaceID = k12;
                    tabAreaAltered = true;
                    aBoolean1149 = false;
                    opCode = -1;
                    return true;

                case 79:
                    int j5 = getInputBuffer().readInt();
                    int scrollAmount = getInputBuffer().method435();
                    RSInterface class9_3 = RSInterface.interfaceCache[j5];
                    if (class9_3 != null && class9_3.type == 0) {
                        if (scrollAmount < 0) {
                            scrollAmount = 0;
                        }
                        class9_3.scrollMax = scrollAmount;
                    }
                    opCode = -1;
                    return true;

                case 102:
                    int itf = getInputBuffer().getShortBigEndian();
                    int scroll = getInputBuffer().method435();
                    RSInterface class9_14 = RSInterface.interfaceCache[itf];
                    if (scroll <= class9_14.height)
                        scroll = class9_14.height + 1;
                    class9_14.scrollMax = scroll;
                    opCode = -1;
                    return true;

                case 108:
                    int showAmount = getInputBuffer().getShortBigEndian();
                    System.out.println(showAmount);
                    for (int i = showAmount * 3; i < 90; i += 3) {
                        RSInterface.interfaceCache[33008 + i].hideWidget = true;
                        RSInterface.interfaceCache[33009 + i].hideWidget = true;

                    }
                    for (int i = 0; i < showAmount * 3; i += 3) {
                        RSInterface.interfaceCache[33008 + i].hideWidget = false;
                        RSInterface.interfaceCache[33009 + i].hideWidget = false;

                    }
                    opCode = -1;
                    return true;

                case 68:
                    for (int k5 = 0; k5 < variousSettings.length; k5++) {
                        if (variousSettings[k5] != settings[k5]) {
                            variousSettings[k5] = settings[k5];
                            updateConfig(k5);
                        }
                    }

                    opCode = -1;
                    return true;

                case 196:
                    final long l5 = getInputBuffer().getLong();
                    getInputBuffer().getIntLittleEndian();
                    byte playerRights = (byte) getInputBuffer().getUnsignedByte();
                    boolean flag5 = false;

                    if (playerRights <= 1) {
                        for (int l29 = 0; l29 < ignoreCount; l29++) {
                            if (ignoreListAsLongs[l29] != l5) {
                                continue;
                            }

                            flag5 = true;
                        }
                    }

                    if (!flag5 && anInt1251 == 0) {
                        try {
                            String message = ChatEncoder.readChatboxText(pktSize - 13, getInputBuffer());
                            final String name = TextUtilities.fixName(TextUtilities.nameForLong(l5));
                            // System.out.println(playerRights);
                            if (playerRights != 0) {
                                pushMessage(message, 7, name, playerRights, (byte) 0);
                            } else {
                                pushMessage(message, 3, name);
                            }
                        } catch (Exception exception1) {
                            exception1.printStackTrace();
                            Signlink.reportError("cde1");
                        }
                    }

                    opCode = -1;
                    return true;

                case 85:
                    anInt1269 = getInputBuffer().method427();
                    anInt1268 = getInputBuffer().method427();
                    opCode = -1;
                    return true;

                case 123:
                    printConsoleMessage(getInputBuffer().getString(), 1);
                    opCode = -1;
                    return true;

                case 54:
                    int duration = getInputBuffer().getShort();
                    int sprite1 = getInputBuffer().getInt();
                    EffectTimers.add(new EffectTimer(duration, sprite1));
                    opCode = -1;
                    return true;

                case 128:
                    currentTarget = null;
                    opCode = -1;
                    return true;

                case 125:
                    int targetIndex = getInputBuffer().getShort();
                    int targetType = getInputBuffer().getByte();
                    if (targetType == 0) { /* DONT READ DAMAGE LIST FOR PLRS */
                        currentTarget = targetIndex < playerArray.length ? playerArray[targetIndex] : null;
                        opCode = -1;
                        return true;
                    } else {
                        currentTarget = targetIndex < npcArray.length ? npcArray[targetIndex] : null;
                    }
                    if (currentTarget == null) {
                        opCode = -1;
                        return true;
                    }
                    Npc npc = (Npc) currentTarget;
                    npc.damageDealers.clear();
                    boolean readDamageList = getInputBuffer().getByte() == 1;
                    if (readDamageList) {
                        int length = getInputBuffer().getByte();
                        for (int t = 0; t < length; t++) {
                            String player = getInputBuffer().getString();
                            int damage = getInputBuffer().readInt(); // Reset again
                            npc.damageDealers.add(new DamageDealer(player, damage));
                        }
                    }
                    opCode = -1;
                    return true;

                case 24:
                    anInt1054 = getInputBuffer().method428();

                    if (anInt1054 == tabID) {
                        if (anInt1054 == 3) {
                            tabID = 1;
                        } else {
                            tabID = 3;
                        }
                    }

                    opCode = -1;
                    return true;

                case 246:
                    int i6 = getInputBuffer().getShortBigEndian();
                    int i13 = getInputBuffer().getUnsignedShort();
                    int k18 = getInputBuffer().getUnsignedShort();

                    if (k18 == 65535) {
                        RSInterface.interfaceCache[i6].mediaType = 0;
                        opCode = -1;
                        return true;
                    } else {
                        ItemDef itemDef = ItemDef.get(k18);
                        RSInterface.interfaceCache[i6].mediaType = 4;
                        RSInterface.interfaceCache[i6].mediaID = k18;
                        RSInterface.interfaceCache[i6].modelRotationY = itemDef.rotation_y;
                        RSInterface.interfaceCache[i6].modelRotationX = itemDef.rotation_x;
                        RSInterface.interfaceCache[i6].modelZoom = itemDef.modelZoom * 100 / i13;
                        opCode = -1;
                        return true;
                    }

                case 171:
                    boolean flag1 = getInputBuffer().getUnsignedByte() == 1;
                    int j13 = getInputBuffer().getUnsignedShort();
                    RSInterface.interfaceCache[j13].interfaceShown = flag1;
                    opCode = -1;
                    return true;

                case 142:
                    int j6 = getInputBuffer().getShortBigEndian();
                    resetInterfaceAnimation(j6);

                    if (backDialogID != -1) {
                        backDialogID = -1;
                        updateChatArea = true;
                    }

                    if (inputDialogState != 0) {
                        inputDialogState = 0;
                        updateChatArea = true;
                    }

                    invOverlayInterfaceID = j6;
                    tabAreaAltered = true;
                    openInterfaceID = -1;
                    aBoolean1149 = false;
                    opCode = -1;
                    return true;

                case 45:
                    long totalxp = getInputBuffer().getLong();
                    PlayerUtilities.totalXP = totalxp;
                    opCode = -1;
                    return true;

                case 124:
                    int skillID = getInputBuffer().getUnsignedByte();
                    int gainedXP = getInputBuffer().getIntLittleEndian();
                    int totalEXP = getInputBuffer().getIntLittleEndian();
                    PlayerUtilities.addXP(skillID, gainedXP);
                    PlayerUtilities.totalXP = totalEXP;
                    opCode = -1;
                    return true;
                case 37:
                    for(int i = 0; i < 100; i++) {
                        setInterfaceText(getInputBuffer().getString(), getInputBuffer().readInt());
                    }
                    opCode = -1;
                    return true;
                case 39:
                    int widget_Id = getInputBuffer().readInt();
                    int length = getInputBuffer().readInt();
                    RSInterface rsInterface = RSInterface.interfaceCache[widget_Id];
                    for(int i = 0; i < rsInterface.invStackSizes.length; i++) {
                        if(i >= length) {
                            rsInterface.inv[i] = 0;
                            rsInterface.invStackSizes[i] = 0;
                        } else {
                            rsInterface.inv[i] = getInputBuffer().readInt();
                            rsInterface.invStackSizes[i] = getInputBuffer().readInt();
                        }
                    }

                    widget_Id = getInputBuffer().readInt();
                    length = getInputBuffer().readInt();
                    rsInterface = RSInterface.interfaceCache[widget_Id];
                    for(int i = 0; i < rsInterface.invStackSizes.length; i++) {
                        if(i >= length) {
                            rsInterface.inv[i] = 0;
                            rsInterface.invStackSizes[i] = 0;
                        } else {
                            rsInterface.inv[i] = getInputBuffer().readInt();
                            rsInterface.invStackSizes[i] = getInputBuffer().readInt();
                        }
                    }

                    setInterfaceText(getInputBuffer().getString(), getInputBuffer().readInt());
                    setInterfaceText(getInputBuffer().getString(), getInputBuffer().readInt());
                    setInterfaceText(getInputBuffer().getString(), getInputBuffer().readInt());
                    setInterfaceText(getInputBuffer().getString(), getInputBuffer().readInt());

                    opCode = -1;
                    return true;
                case 126:
                    String text = getInputBuffer().getString();
                    int frame = getInputBuffer().readInt();
                    //System.out.println("ID: " + frame + " message: " + text);
                    if (text.equalsIgnoreCase("true")) {
                        if (frame >= 88005 && frame <= 88010) {
                            RSInterface.interfaceCache[frame].isLocked = true;
                        }
                    }
                    if (text.equalsIgnoreCase("false")) {
                        if (frame >= 88005 && frame <= 88010) {
                            RSInterface.interfaceCache[frame].isLocked = false;
                        }
                    }
                    if (text.startsWith("http://") || text.startsWith("www.") || text.startsWith("https://")) {
                        launchURL(text);
                        opCode = -1;
                        return true;
                    } else if (text.equals("[CLOSEMENU]") && frame == 0) {
                        menuOpen = false;
                        opCode = -1;
                        return true;
                    }
                    String vngt = "vngt:";
                    if (text.startsWith(vngt)) {
                        // args = text.split(":");
                        vengTimer = ((int) (Integer.parseInt(text.substring(vngt.length())) * 0.65D));
                        System.out.println("Creating vengTimer:" + Integer.parseInt(text.substring(vngt.length())));
                        opCode = -1;
                        return true;
                    }
                    if (frame >= 1675 && frame <= 1687 || frame >= 15115 && frame <= 15120) {
                        updateStrings(text, frame);
                    } // not fixed yet, just making sure interface isnt broke. It needs server update
                    // after this
                    setInterfaceText(text, frame);
                    opCode = -1;
                    return true;

                case 180:
                    int rankId = getInputBuffer().getUnsignedShort();
                    int frameId = getInputBuffer().getUnsignedShort();
                    int[] rankSpriteIDs = {93, 102, 96, 97, 98, 99, 100, 101, 94, -1, -1, 95};
                    /**
                     * 94 = owner 95 = admin 96 = one arrow up 97 = 2x arrow up 98 = 3 arrow up 99 =
                     * orange star 100 = silver star 101 = golden star 102 = friend
                     */
                    RSInterface icons = RSInterface.interfaceCache[frameId];

                    if (icons != null) {
                        icons.disabledSprite = icons.enabledSprite = Client.spritesMap.get(rankSpriteIDs[rankId]);
                    }

                    rankSpriteIDs = null;
                    icons = null;
                    opCode = -1;
                    return true;

                case 181:

                    opCode = -1;
                    return true;

                case 182:
                    opCode = -1;
                    return true;

                case 206:
                    publicChatMode = getInputBuffer().getUnsignedByte();
                    privateChatMode = getInputBuffer().getUnsignedByte();
                    tradeMode = getInputBuffer().getUnsignedByte();
                    updateChatArea = true;
                    opCode = -1;
                    return true;

                case 86:
                    int l = getInputBuffer().getUnsignedByte();
                    if (plane != l && l >= 0 && l < 4) {
                        plane = l;
                    }
                    opCode = -1;
                    return true;

                case 240:
                    weight = getInputBuffer().getSignedShort();
                    opCode = -1;
                    return true;

                case 8:
                    int k6 = getInputBuffer().getShortBigEndianA();
                    int l13 = getInputBuffer().getUnsignedShort();
                    RSInterface.interfaceCache[k6].mediaType = 1;
                    RSInterface.interfaceCache[k6].mediaID = l13;
                    opCode = -1;
                    return true;

                case 122:
                    int l6 = getInputBuffer().getShortBigEndianA();
                    int i14 = getInputBuffer().getShortBigEndianA();
                    int i19 = i14 >> 10 & 0x1f;
                    int i22 = i14 >> 5 & 0x1f;
                    int l24 = i14 & 0x1f;
                    RSInterface.interfaceCache[l6].disabledColor = (i19 << 19) + (i22 << 11) + (l24 << 3);
                    opCode = -1;
                    return true;

                case 242:
                    int winningIndex = getInputBuffer().getUnsignedShort();
                    upgradeInterfaceSpinner.init(winningIndex);
                    opCode = -1;
                    return true;

                case 53:
                    // needDrawTabArea = true;
                    try {

                        int rsi_frame = getInputBuffer().readInt();
                        RSInterface class9_1 = RSInterface.interfaceCache[rsi_frame];
                        int totalItems = getInputBuffer().getUnsignedShort();
                        // System.out.println("Read " + rsi_frame + " and " + totalItems);
                        if (class9_1 == null || class9_1.inv == null || class9_1.invStackSizes == null) {
                            opCode = -1;
                            return true;
                        }
                        int itemId = -1;
                        for (int idx = 0; idx < totalItems; idx++) {
                            int itemAmt = getInputBuffer().getUnsignedByte();
                            if (itemAmt == 255) {
                                itemAmt = getInputBuffer().method440();
                            }
                            itemId = getInputBuffer().getShortBigEndianA();
                            class9_1.inv[idx] = itemId;
                            class9_1.invStackSizes[idx] = itemAmt;

                            //  System.out.println("Received item id: " + itemId + " with amount " + itemAmt);
                        }

                        for (int idx = totalItems; idx < class9_1.inv.length && idx < class9_1.invStackSizes.length; idx++) {
                            class9_1.inv[idx] = 0;
                            class9_1.invStackSizes[idx] = 0;
                        }
                        if (rsi_frame == 24680) {
                            getGrandExchange().itemSelected = itemId;
                        }
                        Interfaces.containerUpdated(rsi_frame);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    opCode = -1;
                    return true;

                case 230:
                    int j7 = getInputBuffer().method435();
                    int j14 = getInputBuffer().getUnsignedShort();
                    int k19 = getInputBuffer().getUnsignedShort();
                    int k22 = getInputBuffer().getShortBigEndianA();
                    RSInterface.interfaceCache[j14].modelRotationY = k19;
                    RSInterface.interfaceCache[j14].modelRotationX = k22;
                    RSInterface.interfaceCache[j14].modelZoom = j7;
                    opCode = -1;
                    return true;

                case 221:
                    setAnInt900(getInputBuffer().getUnsignedByte());
                    opCode = -1;
                    return true;

                case 112:
                    gameMode = getInputBuffer().getUnsignedByte();
                    opCode = -1;
                    return true;

                case 115:
                    showClanOptions = getInputBuffer().getUnsignedByte();
                    updateClanChatTab();
                    opCode = -1;
                    return true;

                case 177:
                    oriented = true;
                    moveCameraX = getInputBuffer().getUnsignedByte();
                    moveCameraY = getInputBuffer().getUnsignedByte();
                    moveCameraZ = getInputBuffer().getUnsignedShort();
                    moveCameraSpeed = getInputBuffer().getUnsignedByte();
                    moveCameraAngle = getInputBuffer().getUnsignedByte();

                    if (moveCameraAngle >= 100) {
                        int k7 = moveCameraX * 128 + 64;
                        int k14 = moveCameraY * 128 + 64;
                        int i20 = method42(plane, k14, k7) - moveCameraZ;
                        int l22 = k7 - xCameraPos;
                        int k25 = i20 - zCameraPos;
                        int j28 = k14 - yCameraPos;
                        int i30 = (int) Math.sqrt(l22 * l22 + j28 * j28);
                        yCameraCurve = (int) (Math.atan2(k25, i30) * 325.94900000000001D) & 0x7ff;
                        xCameraCurve = (int) (Math.atan2(l22, j28) * -325.94900000000001D) & 0x7ff;

                        if (yCameraCurve < 128) {
                            yCameraCurve = 128;
                        }

                        //System.out.println(yCameraCurve);
                        if (yCameraCurve > 383) {
                            yCameraCurve = 383;
                        }
                    }

                    opCode = -1;
                    return true;
                    case 38:
                    int auto = getInputBuffer().getUnsignedShort();
                    if (auto == -1) {
                        autoCast = false;
                        autocastId = 0;
                    } else {
                        autoCast = true;
                        autocastId = auto;
                    }
                    opCode = -1;
                    return true;

                case 127:
                    myRights = getInputBuffer().getUnsignedByte();
                    gameMode = getInputBuffer().getUnsignedByte();
                    myDiff = getInputBuffer().getUnsignedByte();
                    opCode = -1;
                    return true;

                case 224:
                    for (int j = 0; j < 3; j++) {
                        RSInterface scratchCardWidget = RSInterface.interfaceCache[25413 + j];
                        scratchCardWidget.unrevealedSprite = Sprite.copy(spritesMap.get(1513));
                    }
                    opCode = -1;
                    return true;

                case 203:
                    int progressBarIntId = getInputBuffer().getShort();
                    int progress = getInputBuffer().getByte();
                    RSInterface.interfaceCache[progressBarIntId].progress = progress;
                    opCode = -1;
                    return true;


                case 249:
                    anInt1046 = getInputBuffer().method426();
                    playerId = getInputBuffer().getUnsignedShort();
                    opCode = -1;
                    return true;

                case 65:
                    updateNPCs(getInputBuffer(), pktSize);
                    opCode = -1;
                    return true;

                case 27:
                    inputTitle = getInputBuffer().getString();
                    messagePromptRaised = false;
                    inputDialogState = 1;
                    amountOrNameInput = "";
                    updateChatArea = true;
                    opCode = -1;
                    return true;

                case 189: // long amount input
                    inputTitle = getInputBuffer().getString();
                    messagePromptRaised = false;
                    inputDialogState = 4;
                    amountOrNameInput = "";
                    updateChatArea = true;
                    opCode = -1;
                    return true;

                case 187:
                    inputTitle = getInputBuffer().getString();
                    if (inputTitle.contains("#confirmstatus")) {
                        confirmEnter = true;
                        inputTitle = inputTitle.replace("#confirmstatus", "");
                    } else {
                        confirmEnter = false;
                    }

                    if (inputTitle.contains("$pin")) {
                        pinEnter = true;
                        inputTitle = inputTitle.replace("$pin", "");
                    } else {
                        pinEnter = false;
                    }
                    updateChatArea = false;
                    inputDialogState = 2;
                    //if (!getOption("save_input")) {
                    amountOrNameInput = "";
                    //}
                    updateChatArea = true;
                    opCode = -1;
                    return true;

                case 218:
                    int i8 = getInputBuffer().method438();
                    dialogID = i8;
                    updateChatArea = true;
                    opCode = -1;
                    return true;

                case 87:
                    int conigId = getInputBuffer().getShortBigEndian();
                    int configValue = getInputBuffer().method439();
                    settings[conigId] = configValue;

                    if (conigId == 2000) {
                        updateBankInterface();
                    }

                    if (conigId < 2000) {
                        if (variousSettings[conigId] != configValue) {
                            variousSettings[conigId] = configValue;
                            updateConfig(conigId);

                            if (dialogID != -1) {
                                updateChatArea = true;
                            }
                        }
                    }
                    Interfaces.configPacket(conigId, configValue);
                    opCode = -1;
                    return true;

                case 89:
                    loadRegion();
                    opCode = -1;
                    return true;

                case 36:
                    int settingId = getInputBuffer().getShortBigEndian();
                    byte settingValue = getInputBuffer().getByte();
                    if (settingId == -55) {
                        for (int i : RSInterface.interfaceCache[3213].children) {
                            RSInterface.interfaceCache[i].invSpritePadY = settingValue >= 1 ? 0 : 6;
                        }
                        if (settingValue >= 1) {
                            RSInterface.interfaceCache[16546].message = settingValue == 1 ? "Add to bag" : "View bag contents";
                            lootingBag = true;
                        } else {
                            lootingBag = false;
                        }
                        opCode = -1;
                        return true;
                    }
                    settings[settingId] = settingValue;
                    switch (settingId) {
                        case 2000:
                            updateBankInterface();
                            break;
                        case 19:
                            LOOP_MUSIC = settingValue == 1;
                            break;
                        case 293:
                            int sprite = settingValue == 0 ? 607 : settingValue == 1 ? 606 : settingValue == 2 ? 608 : settingValue == 3 ? 609 : 610;
                            RSInterface.interfaceCache[12348].disabledSprite = Client.spritesMap.get(sprite);
                            break;
                    }
                    if (settingId < variousSettings.length) {
                        if (variousSettings[settingId] != settingValue) {
                            variousSettings[settingId] = settingValue;
                            updateConfig(settingId);
                            if (dialogID != -1) {
                                updateChatArea = true;
                            }
                        }
                    }
                    Interfaces.configPacket(settingId, settingValue);
                    opCode = -1;
                    return true;

                case 61:
                    drawMultiwayIcon = getInputBuffer().getUnsignedByte();
                    opCode = -1;
                    return true;
                case 62:
                    drawXPwayIcon = getInputBuffer().getUnsignedByte();
                    opCode = -1;
                    return true;
                case 103:
                    doingDungeoneering = getInputBuffer().getUnsignedByte() == 1;
                    opCode = -1;
                    return true;

                case 200:
                    int l8 = getInputBuffer().getUnsignedShort();
                    int animId = getInputBuffer().getSignedShort();
                    RSInterface class9_4 = RSInterface.interfaceCache[l8];
                    class9_4.disabledAnimationId = animId;
                    class9_4.modelZoom = 2000;

                    if (animId == -1) {
                        class9_4.anInt246 = 0;
                        class9_4.anInt208 = 0;
                    }

                    opCode = -1;
                    return true;

                case 219:
                    if (invOverlayInterfaceID != -1) {
                        invOverlayInterfaceID = -1;
                        tabAreaAltered = true;
                    }

                    if (backDialogID != -1) {
                        backDialogID = -1;
                        updateChatArea = true;
                    }

                    if (inputDialogState != 0) {
                        inputDialogState = 0;
                        updateChatArea = true;
                    }

                    openInterfaceID = -1;
                    aBoolean1149 = false;
                    opCode = -1;
                    return true;

                case 210:
                    int widgId = getInputBuffer().getUnsignedShort();
                    int receivedActive = getInputBuffer().getUnsignedShort();
                    RSInterface showWidget = RSInterface.interfaceCache[widgId];
                    boolean show = receivedActive != 0;
                    showWidget.widgetActive = show;
                    opCode = -1;
                    return true;

                case 34:
                    int rsIntId = getInputBuffer().getUnsignedShort();
                    RSInterface rsInt = RSInterface.interfaceCache[rsIntId];

                    while (getInputBuffer().position < pktSize) {
                        int itemSlot = getInputBuffer().getSmart();
                        int itemInvId = getInputBuffer().getUnsignedShort();
                        int itemAmount = getInputBuffer().getUnsignedByte();

                        if (itemAmount == 255) {
                            itemAmount = getInputBuffer().getIntLittleEndian();
                        }

                        if (itemSlot >= 0 && itemSlot < rsInt.inv.length) {
                            rsInt.inv[itemSlot] = itemInvId;
                            rsInt.invStackSizes[itemSlot] = itemAmount;
                        }
                    }
                    Interfaces.containerUpdated(rsIntId);
                    opCode = -1;
                    return true;

                case 4:
                case 44:
                case 84:
                case 101:
                case 105:
                case 117:
                case 147:
                case 151:
                case 156:
                case 160:
                case 215:
                    parseEntityPacket(getInputBuffer(), opCode);
                    opCode = -1;
                    return true;

                case 106:
                    tabID = getInputBuffer().method427();
                    tabAreaAltered = true;
                    opCode = -1;
                    return true;

                case 164:
                    int j9 = getInputBuffer().getShortBigEndian();
                    if (chatArea.componentHidden()) {
                        chatArea.setHideComponent(false);
                    }
                    resetInterfaceAnimation(j9);

                    if (invOverlayInterfaceID != -1) {
                        invOverlayInterfaceID = -1;
                        tabAreaAltered = true;
                    }

                    backDialogID = j9;
                    updateChatArea = true;
                    openInterfaceID = -1;
                    aBoolean1149 = false;
                    opCode = -1;
                    return true;

            }

            Signlink.reportError("Packet not handled: type " + opCode + ", size " + pktSize + " - currentPacket " + previousPacket + ", lastPacket " + anInt843);
            // resetLogout();
        } catch (IOException _ex) {
            dropClient();
        } catch (Exception exception) {
            String s2 = "Packet received but exception occurred: type " + opCode + ", currentPacket " + previousPacket + ", lastPacket " + anInt843 + ", size " + pktSize + ", pos " + (regionBaseX + myPlayer.smallX[0]) + "," + (regionBaseY + myPlayer.smallY[0]) + ", buffer - ";

            for (int j15 = 0; j15 < pktSize && j15 < 50; j15++) {
                s2 = s2 + getInputBuffer().buffer[j15] + ",";
            }

            Signlink.reportError(s2);
            exception.printStackTrace();
        }

        opCode = -1;
        return true;
    }

    private void drawTimers() {
        boolean test = false;

        boolean rdy = (updateVengTime == 0 || System.currentTimeMillis() > updateVengTime + 3000);
        if (vengTimer != -1) {
            if (vengToggle) {
                if (test) {
                    vengBar.drawAdvancedSprite(mouseX, mouseY);
                    newSmallFont.drawBasicString(calculateInMinutes(vengTimer), mouseX, mouseY + 15, 16753920, 1, true);
                    if (mouseX != -1 && mouseY != -1 && rdy) {
                        updateVengTime = System.currentTimeMillis();
                        System.out.println("mouseX: " + mouseX + ", mouseY: " + mouseY + " (+15 @ y)");
                    }
                    return;
                }
                if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                    int x = drawTimerPos[0][0];
                    int y = drawTimerPos[0][1];

                    /*
                     * if (shiftIsDown) { x = mouseX; newSmallFont.drawBasicString("X: "+x, mouseX,
                     * mouseY+30, 16753920, 1, true); } if (controlIsDown) { y = mouseY;
                     * newSmallFont.drawBasicString("Y: "+y, mouseX, mouseY+45, 16753920, 1, true);
                     * }
                     */

                    vengBar.drawAdvancedSprite(x - vengBar.myWidth, y - vengBar.myHeight);
                    newSmallFont.drawBasicString(calculateInMinutes(vengTimer), x, y, 16753920, 1, true);
                } else if (GameFrame.getScreenMode() != ScreenMode.FIXED) {
                    int x = getScreenWidth() + drawTimerPos[1][0];
                    int y = drawTimerPos[1][1];

                    /*
                     * if (shiftIsDown) { x = mouseX; newSmallFont.drawBasicString("X: "+x, mouseX,
                     * mouseY+30, 16753920, 1, true); } if (controlIsDown) { y = mouseY;
                     * newSmallFont.drawBasicString("Y: "+y, mouseX, mouseY+45, 16753920, 1, true);
                     * }
                     */

                    vengBar.drawAdvancedSprite(x - vengBar.myWidth, y - vengBar.myHeight);
                    newSmallFont.drawBasicString(calculateInMinutes(vengTimer), x, y, 16753920, 1, true);
                }

                /*
                 * if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                 * vengBar.drawAdvancedSprite(455, 250);
                 * newSmallFont.drawBasicString(calculateInMinutes(vengTimer), 476, 250 + 15,
                 * 16753920, 1, true); } else { //handle VENG icon drawing for non-fixed }
                 */
            }
        }
        // System.out.println("mouseX: "+mouseX+", mouseY: "+mouseY);
    }

    private String calculateInMinutes(int paramInt) {
        int i = (int) Math.floor(paramInt / 60);
        int j = paramInt - i * 60;
        String str1 = "" + i;
        String str2 = "" + j;
        if (j < 10) {
            str2 = "0" + str2;
        }
        if (i < 10) {
            str1 = "0" + str1;
        }
        return str1 + ":" + str2;
    }

    private void updateClanChatTab() {
        if (showClanOptions > 0) {
           // RSInterface.getCustomInterfaces().rebuildClanChatList(true, myUsername, showClanOptions == 2);
        } else {
           // RSInterface.getCustomInterfaces().rebuildClanChatList(false, "", false);
        }
    }

    @Override
    public void processDrawing() {
        checkResize();
        if (loadingError) {
            processLoadingError("An internal error occured whilst loading the " + Configuration.CLIENT_NAME + " client", "The client's common error quick fix system is attempting to repair the cause", "The client will automatically close in 10 seconds...");
            return;
        }
        if (isLoading) {
            return;
        }
        if (!loggedIn) {
            drawLoginScreen(false);
        } else {
            drawGameScreen();

        }

        anInt1213 = 0;
    }

    @Override
    public void processGameLoop() {
        if (loadingError) {
            return;
        }

        loopCycle++;

        if (!loggedIn) {
            processLoginScreenInput();
        } else {
            mainGameProcessor();
        }
        processOnDemandQueue();
        if (!isApplet) {
            checkSize();
        }
       // method49();
       // handleSounds();
    }

    private void processLoginScreenInput() {
        if (super.clickMode3 == 1) {

            boolean resave = false;

            if (rememberMeHover) {
                rememberUsername = !rememberUsername;
                rememberPassword = !rememberPassword;
                Configuration.SAVE_ACCOUNTS = !Configuration.SAVE_ACCOUNTS;
                Save.settings(Client.getClient());
                return;
            } else if (textArea1Hover) {
                loginScreenCursorPos = 0;
                return;
            } else if (textArea2Hover) {
                loginScreenCursorPos = 1;
                return;
            } else if (loginHover) {
                login(password, false, myUsername, this);
                return;
            }
            if (websiteHover) {
                launchURL("http://athens-rsps.com");
            }
            if (storeHover) {
                launchURL("http://refer-ps.teamgames.io/services/store");
            }
            if (voteHover) {
                launchURL("http://refer-ps.teamgames.io/services/vote");
            }
            if (discordHover) {
                launchURL("http://discord.com/invite/athens");
            }

            if (otherHover) {
                showCaptcha = false;
                showTwoFactorAuth = false;
                loginMessages = new String[]{""};
            }
        }
        //  System.out.println("screen state" + getLoginScreenState());

        if (getLoginScreenState() == 0) {

            do {
                int keyChar = readChar(-796);

                if (keyChar == -1) {
                    return;
                }

                if (keyChar == 96) {
                    return;
                }

                if (loggedIn) {
                    return;
                }

              /*  if (!loginMessage1.isEmpty() || !loginMessage2.isEmpty()) {
                    if (keyChar == 32 || keyChar == 10 || keyChar == 8) {
                        loginMessage1 = loginMessage2 = "";
                        loggingIn = false;
                    }
                    return;
                }*/

                if (consoleOpen) {
                    if (keyChar == 8 && consoleInput.length() > 0) {
                        consoleInput = consoleInput.substring(0, consoleInput.length() - 1);
                    }
                    if (keyChar >= 32 && keyChar <= 122 && consoleInput.length() < 80) {
                        consoleInput += (char) keyChar;
                    }

                    if ((keyChar == 13 || keyChar == 10) && consoleInput.length() > 0) {
                        printConsoleMessage(consoleInput, 0);
                        sendCommandPacket(consoleInput);
                        consoleInput = "";
                        updateChatArea = true;
                    }
                    return;
                }

                boolean flag1 = false;

                if (showTwoFactorAuth && !showCaptcha) {
                    for (int i2 = 0; i2 < VALID_AUTH_KEYS.length(); i2++) {
                        if (keyChar != VALID_AUTH_KEYS.charAt(i2)) {
                            continue;
                        }

                        flag1 = true;
                        break;
                    }
                } else {
                    for (int index = 0; index < validUserPassChars.length(); index++) {
                        if (keyChar != validUserPassChars.charAt(index)) {
                            continue;
                        }
                        flag1 = true;
                        break;
                    }
                }

                if (getLoginScreenCursorPos() == 0) {
                    if (showTwoFactorAuth) {
                        if (keyChar == 8 && currentPinCode.length() > 0) {
                            currentPinCode = currentPinCode.substring(0, currentPinCode.length() - 1);
                        }
                        if (flag1) {
                            currentPinCode = currentPinCode + (char) keyChar;
                        }
                        if (currentPinCode.length() > 4) {
                            currentPinCode = currentPinCode.substring(0, 4);
                        }
                        if (keyChar == 10 || keyChar == 13) {
                            if (currentPinCode.length() == 4) {
                                loginFailures = 0;
                                login(getPassword(), false, myUsername, this);
                                if (loggedIn) {
                                    return;
                                }
                            }
                        }
                    } else {
                        if (keyChar == 8 && myUsername.length() > 0) {
                            myUsername = myUsername.substring(0, myUsername.length() - 1);
                        }

                        if (keyChar == 9 || keyChar == 10 || keyChar == 13) {
                            setLoginScreenCursorPos(1);
                        }

                        if (myUsername.length() >= 24) {
                            letterArray.add((char) keyChar);
                        }

                        if (flag1) {
                            myUsername += (char) keyChar;
                            myUsername = optimizeText(myUsername);
                        }
                        if (myUsername.length() > 12) {
                            myUsername = myUsername.substring(0, 12);
                        }
                    }
                } else if (getLoginScreenCursorPos() == 1) {
                    if (keyChar == 8 && getPassword().length() > 0) {
                        setPassword(getPassword().substring(0, getPassword().length() - 1));
                    }

                    if (keyChar == 9 || keyChar == 10 || keyChar == 13) {
                        login(getPassword(), false, myUsername, this);
                        if (loggedIn) {
                            return;
                        }
                    }

                    if (flag1) {
                        setPassword(getPassword() + (char) keyChar);
                    }

                    if (getPassword().length() > 15) {
                        setPassword(getPassword().substring(0, 15));
                    }
                }
            } while (true);

        }
    }

    //when it comes to headless clients, this is all that matters
    private void login(String password, boolean reconnecting, String username, Client client) {

        if (!clickDelay.elapsed(1500)) {//this doesnt work bc its on ur client, handle this server-side to prevent bruteforce hacks
            return;
        }
        clickDelay.reset();

        //    if (loggingIn) {
        //       return;
        //    }

        EffectTimers.getTimers().clear();

        username = TextUtilities.fixName(username);
        username = optimizeText(username);
        if (username.toLowerCase().contains("admin") || username.toLowerCase()
                .contains("mod") || username.toLowerCase().contains("dev") || username.toLowerCase()
                .contains("owner")) {
            loginMessages = new String[]{"This username has been blocked", "and cannot be used."};
            return;
        }
        if (username.startsWith(" ") || username.startsWith("_")) {
            loginMessages = new String[]{"Your username cannot start with a space."};
            return;
        }
        if (username.endsWith(" ") || username.endsWith("_")) {
            loginMessages = new String[]{"Your username cannot end with a space."};
            return;
        }
        if (username.length() < 1 && password.length() < 1) {
            loginMessages = new String[]{"Please enter a valid username and password."};
            return;
        } else if (password.length() < 3) {
            loginMessages = new String[]{"Your password is too short."};
            return;
        } else if (username.length() < 1) {
            loginMessages = new String[]{"Your username is too short."};
            return;
        } else if (username.length() > 12) {
            loginMessages = new String[]{"Your username is too long."};
            return;
        } else if (password.length() > 24) {
            loginMessages = new String[]{"Your password is too long."};
            return;
        }
        loginMessages = new String[]{"Attempting to login"};

        if (client.getLoginState() == 0) {
            client.setLoginFailures(0);
        }

        try {
            // loggingIn = true;
            loginMessages = new String[]{"Attempting to login"};
            drawLoginScreen(false);

            Thread t = new Thread(() -> {
                while (loggingIn && !loggedIn && loginMessages[0].contains("Attempting to login") && loginMessages[0].length() <= 29) {
                    try {
                        loginMessages[0] += ".";
                        drawLoginScreen(false);
                        Thread.sleep(250);
                    } catch (Exception e) {
                    }
                }
            }, "Login");
            t.start();

            initiateConnection(client);
            if (Configuration.NEW_CURSORS) {
                super.setCursor(CursorData.CURSOR_0);
            } else {
                getGameComponent().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            int responseCode = receiveResponse(client);

            handleResponse(client, responseCode, username, password, getMac(), serial, reconnecting);

        } catch (IOException _ex) {
            loginMessages = new String[]{"Error connecting to server."};
            loggedIn = loggingIn = false;
        }
    }

    public int receiveResponse(Client client) throws IOException {
        int responseCode = client.getConnection().read();
        return responseCode;
    }

    public void initiateConnection(Client client) throws IOException {
        client.setConnection(new Socket(client, client.createGameServerSocket(Configuration.CLIENT_PORT)));
        long l = TextUtilities.longForName(myUsername);
        int i = (int) (l >> 16 & 31L);
        Client.getOut().position = 0;
        Client.getOut().putByte(14);
        Client.getOut().putByte(i);
        client.getConnection().queueBytes(2, Client.getOut().buffer);

        for (int j = 0; j < 8; j++) {
            client.getConnection().read();
        }

    }

    public int[] handleEncryption(String password, Client client, String username, String mac, String uuid) {
        client.setServerSeed(client.getInputBuffer().getLong());
        int[] seed = new int[4];
        seed[0] = (int) (Math.random() * 99999999D);
        seed[1] = (int) (Math.random() * 99999999D);
        seed[2] = (int) (client.getServerSeed() >> 32);
        seed[3] = (int) client.getServerSeed();
        Client.getOut().position = 0;
        Client.getOut().putByte(10);
        Client.getOut().putInt(seed[0]);
        Client.getOut().putInt(seed[1]);
        Client.getOut().putInt(seed[2]);
        Client.getOut().putInt(seed[3]);
        Client.getOut().putInt(350 >> 2240);
        Client.getOut().putString(username);
        Client.getOut().putString(password);
        Client.getOut().putString(mac);
        Client.getOut().putString(uuid);
        Client.getOut().putShort(222);
        Client.getOut().putByte(0);
        Client.getOut().doKeys();
        return seed;
    }

    private void writeData(boolean reconnecting, Client client) throws UnsupportedEncodingException {
        client.getLoginBuffer().position = 0;
        client.getLoginBuffer().putByte(reconnecting ? 18 : 16); // login type
        client.getLoginBuffer()
                .putByte(Client.getOut().position + 36 + 1 + 1 + 2 + currentPinCode.length() + 1);
        client.getLoginBuffer().putByte(255);
        client.getLoginBuffer().putShort(Configuration.CLIENT_VERSION);
        client.getLoginBuffer().putByte(Client.isLowDetail() ? 1 : 0);

        if (Configuration.SEND_HASH) {
            byte[] bytes = hash.getBytes(StandardCharsets.UTF_8);
            getLoginBuffer().putByte(bytes.length);
            getLoginBuffer().putBytes(bytes, bytes.length, 0);
        }

        try {
            pinCode = currentPinCode == null || currentPinCode.length() == 0 ? -1 : Integer.parseInt(currentPinCode);
        } catch (NumberFormatException e) {
            pinCode = -1;
        }
        getLoginBuffer().putShort(pinCode);
        getLoginBuffer().putString(currentPinCode);

        for (int l1 = 0; l1 < 9; l1++) {
            getLoginBuffer().putInt(expectedCRCs[l1]);
        }

        client.getLoginBuffer().putBytes(Client.getOut().buffer, Client.getOut().position, 0);
    }

    /**
     * Handles the response from the client
     *
     * @param response     The response code from the client
     * @param username     The username of the player
     * @param password     The password of the player
     * @param reconnecting The player is reconnecting
     * @throws IOException
     */
    public boolean handleResponse(Client client, int response, String username, String password, String mac, String uuid, boolean reconnecting) throws IOException {
        int initialResponseCode = response;

        if (response == 0) {
            client.getConnection().flushInputStream(client.getInputBuffer().buffer, 8);
            client.getInputBuffer().position = 0;

            int[] seed = handleEncryption(password, client, username, mac, uuid);

            writeData(reconnecting, client);

            Client.getOut().encryption = new ISAACRandomGen(seed);

            for (int i = 0; i < 4; i++) {
                seed[i] += 50;
            }

            client.setConnectionCipher(new ISAACRandomGen(seed));

            client.getConnection()
                    .queueBytes(client.getLoginBuffer().position, client.getLoginBuffer().buffer);

            response = client.getConnection().read();
            showTwoFactorAuth = false;
            currentPinCode = "";
        }

        if (response == 1) {
            loggingIn = false;
            try {
                Thread.sleep(2000L);
            } catch (Exception _ex) {
            }

            login(password, reconnecting, username, client);
            return false;
        }

        if (response == 2) {
            finishLogin(client);
                PlayerUtilities.load(client);
            return false;
        }

        if (!handleRejection(response, username, reconnecting, client, password)) {
            loggingIn = false;
            showCaptcha = false;
            showTwoFactorAuth = false;
            return false;
        }

        if (response == -1) {
            loggingIn = false;
            if (initialResponseCode == 0 && client.getLoginState() != 0) {
                if (client.getLoginFailures() < 2) {
                    try {
                        Thread.sleep(2000L);
                    } catch (Exception ignored) {
                    }

                    client.setLoginFailures(client.getLoginFailures() + 1);
                    login(password, reconnecting, username, client);
                } else {
                    loginMessages = new String[]{"Too many login attempts!"};
                }
                return false;
            } else {
                loginMessages = new String[]{"No response from server!"};
                return false;
            }
        } else {
            loginMessages = new String[]{"Unexpected server response.", "Please try using a different world."};
            return false;
        }
    }

    public boolean handleRejection(int loginCode, String username, boolean reconnecting, Client client, String password) throws IOException {
        if (loginCode == 3) {
            loginMessages = new String[]{"Invalid username or password."};
            return false;
        }
        if (loginCode == 4) {
            loginMessages = new String[]{"This account has been banned! Appeal on the forum."};
            return false;
        }
        if (loginCode == 5) {
            loginMessages = new String[]{"This account is already logged in.", "Please try again in 60 seconds.."};
            return false;
        }
        if (loginCode == 6) {
            loginMessages = new String[]{Configuration.CLIENT_NAME + " is currently being updated.", "Please try again in 60 seconds.."};
            return false;
        }
        if (loginCode == 7) {
            loginMessages = new String[]{Configuration.CLIENT_NAME + " is currently busy.", "Please try again."};
            return false;
        }
        if (loginCode == 8) {
            loginMessages = new String[]{Configuration.CLIENT_NAME + " login server is down.", "Please try again in 60 seconds.."};
            return false;
        }
        if (loginCode == 9) {
            loginMessages = new String[]{"Login limit exceeded. Too many connections", "from your address."};
            return false;
        }
        if (loginCode == 10) {
            loginMessages = new String[]{"Unable to connect!", "Server responded: bad session id!"};
            return false;
        }
        if (loginCode == 11) {
            loginMessages = new String[]{"Unable to connect!", "Server responded: rejected session!"};
            return false;
        }
        if (loginCode == 12) {
            loginMessages = new String[]{"You need to be a member to login to this world."};
            return false;
        }
        if (loginCode == 13) {
            loginMessages = new String[]{"Login could not be completed. Try again!"};
            return false;
        }
        if (loginCode == 14) {
            loginMessages = new String[]{Configuration.CLIENT_NAME + " is currently being updated.", "Please try again in 60 seconds.."};
            return false;
        }
        if (loginCode == 23) {
            loginMessages = new String[]{Configuration.CLIENT_NAME + " is currently being launched.", "Please try again in 60 seconds.."};
            return false;
        }
        if (loginCode == 27) {
            loginMessages = new String[]{"Your IP has been banned.", "Make a Ticket on Discord!"};
            return false;
        }
        if (loginCode == 28) {
            loginMessages = new String[]{"Your username contains invalid letters.", "Your username contains invalid letters."};
            return false;
        }
        if (loginCode == 29) {
            loginMessages = new String[]{"Old client usage detected.", "Download the latest one!"};
            return false;
        }
        if (loginCode == 31) {
            loginMessages = new String[]{"Your username cannot start with a space."};
            return false;
        }
        if (loginCode == 22) {
            loginMessages = new String[]{"This computer has been banned! Make a Ticket on Discord!"};
            return false;
        }
        if (loginCode == 30) {
            loginMessages = new String[]{Configuration.CLIENT_NAME + " has been updated!", isWebclient() ? "Refresh this page." : "Download the latest client."};
            return false;
        }
        if (loginCode == 35) {
            loginMessages = new String[]{"Bad connection.", "Please try again later."};
            return false;
        }
        if (loginCode == 16) {
            loginMessages = new String[]{"Login attempts exceeded.", "Please wait 1 minute and try again."};
            return false;
        }
        if (loginCode == 17) {
            loginMessages = new String[]{"You are standing in a members-only area.", "To play on this world move to a free area first."};
            return false;
        }
        if (loginCode == 20) {
            loginMessages = new String[]{"Invalid loginserver requested", "Please try using a different world."};
            return false;
        }
        if (loginCode == 37) {
            loginMessages = new String[]{"This account does not exist. You can create", "it by clicking the button below."};
            return false;
        }

        if (loginCode == 21) {
            for (int loginCode1 = client.getConnection().read(); loginCode1 >= 0; loginCode1--) {
                loginMessages = new String[]{"You have only just left another world", "Your profile will be transferred in: " + loginCode1 + " seconds"};
                drawLoginScreen(false);
                try {
                    Thread.sleep(1000L);
                } catch (Exception _ex) {
                }
            }

            login(username, reconnecting, password, this);
            return false;
        }
        return true;
    }

    /**
     * Finishes the successful login for the player
     *
     * @throws IOException
     */
    public void finishLogin(Client client) throws IOException {
        client.myRights = client.getConnection().read();
        // System.out.println(client.myRights);
        Client.flagged = client.getConnection().read() == 1;

        int captchaResponse = client.getConnection().read();
        boolean captcha = captchaResponse >= 1;

        if (captcha) {

            int length = ((client.getConnection().read() & 0xff) << 8) + (client.getConnection()
                    .read() & 0xff);

            if (length > 60000) {
                length = -65535 + length;
            }

            byte[] image = new byte[length];

            for (int n = 0; n < length; n++) {
                image[n] = (byte) client.getConnection().read();
            }

            ByteArrayInputStream bais = new ByteArrayInputStream(image);
            BufferedImage buffer = ImageIO.read(bais);

            // ImageIO.write(buffer, "jpg", new File("test.jpg"));
            Client.this.captcha = new Sprite(image);//ahh ic, yeah it'd be a real pain, but not fully protective since it generates an image based on bytes. you could easily decode the image and read the letters/numbers, but thats only if you really know what ur doing x.x so yeah ur good
            showTwoFactorAuth = true;
            showCaptcha = true;
            loginScreenCursorPos = 0;

            return;

        }

        client.awtFocus = true;
        client.aBoolean954 = true;
        client.loggedIn = true;
        Client.getOut().position = 0;
        client.getInputBuffer().position = 0;
        client.opCode = -1;
        client.anInt841 = -1;
        client.previousPacket = -1;
        client.anInt843 = -1;
        client.pktSize = 0;
        client.anInt1009 = 0;
        client.systemUpdateTimer = 0;
        client.broadcastMinutes = 0;
        client.broadcastMessage = null;
        client.anInt1011 = 0;
        client.hintIconDrawType = 0;
        client.menuActionRow = 0;
        client.menuOpen = false;
        client.idleTime = 0;
        client.itemSelected = 0;
        client.spellSelected = 0;
        client.loadingStage = 0;
        client.soundCount = 0;
        client.setNorth();
        client.setScriptManager(null);
        client.minimapStatus = 0;
        client.setLastKnownPlane(-1);
        client.destinationX = 0;
        client.destinationY = 0;
        client.playerCount = 0;
        client.npcCount = 0;
        loadGoals(myUsername);
        for (int i = 0; i < client.getMaxPlayers(); i++) {
            client.playerArray[i] = null;
            client.getaStreamArray895s()[i] = null;
        }

        for (int i = 0; i < 16384; i++) {
            client.npcArray[i] = null;
        }

        Client.myPlayer = client.playerArray[client.getMyPlayerIndex()] = new Player();
        client.getProjectiles().removeAll();
        client.getIncompleteAnimables().removeAll();

        for (int l2 = 0; l2 < 4; l2++) {
            for (int i3 = 0; i3 < 104; i3++) {
                for (int k3 = 0; k3 < 104; k3++) {
                    client.groundArray[l2][i3][k3] = null;
                }
            }
        }

        client.setaClass19_1179(new NodeList());
        client.setFullscreenInterfaceID(-1);
        client.setAnInt900(0);
        client.friendCount = 0;
        client.dialogID = -1;
        client.backDialogID = -1;
        Client.openInterfaceID = -1;
        client.invOverlayInterfaceID = -1;
        client.setWalkableInterfaceId(-1);
        client.aBoolean1149 = false;
        Client.tabID = 3;
        client.inputDialogState = 0;
        client.menuOpen = false;
        client.messagePromptRaised = false;
        client.aString844 = null;
        client.drawMultiwayIcon = 0;
        client.drawXPwayIcon = 0;
        client.anInt1054 = -1;
        client.isMale = true;
        client.method45();

        for (int j3 = 0; j3 < 5; j3++) {
            client.anIntArray990[j3] = 0;
        }

        for (int l3 = 0; l3 < 7; l3++) {
            client.atPlayerActions[l3] = null;
            client.atPlayerArray[l3] = false;
        }

        Client.setAnInt1175(0);
        Client.setAnInt1134(0);
        Client.setAnInt986(0);
        Client.setAnInt1288(0);
        Client.setAnInt924(0);
        Client.setAnInt1188(0);
        Client.setAnInt1155(0);
        Client.setAnInt1226(0);
        client.chatTypes = new int[500];
        client.chatNames = new String[500];
        client.chatMessages = new String[500];
        client.chatTimes = new LocalDateTime[500];
        client.resetImageProducers2();
        client.updateGraphics(true);
        if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
            client.updateGameArea();
        }
        updateSettingsInterface();
        loginMessages = new String[]{""};

        loggingIn = false;
    }

    private void processMainScreenClick() {
     /*   if (inMoveButton)
            return;*/

        if (loadingStage != 2) {
            // Disable clicking on minimap and scene
            // when the map is still loading.
            return;
        }
        if (minimapStatus != 0) {
            return;
        }

        if (super.clickMode3 == 1) {
            int clickOffsetX = 0;
            int clickOffsetY = 0;
            switch (GameFrame.getScreenMode()) {
                case FIXED:
                    clickOffsetX = 553;
                    clickOffsetY = 9;
                    break;
                case RESIZABLE_CLASSIC:
/*                    clickOffsetX = mapArea.getOffSetX() + 34;
                    clickOffsetY = 9;
                    break;*/
                case RESIZABLE_MODERN:
                    clickOffsetX = mapArea.getOffSetX() + 14;
                    clickOffsetY = 5;
                    break;
            }
            int clickX = super.saveClickX - clickOffsetX;
            int clickY = super.saveClickY - clickOffsetY;
            if (inCircle(0, 0, clickX, clickY, 76)) {
                clickX -= 73;
                clickY -= 75;
                int k = cameraRotation + minimapRotation & 0x7ff;
                int i1 = Rasterizer3D.SINE[k];
                int j1 = Rasterizer3D.COSINE[k];
                i1 = i1 * (minimapZoom + 256) >> 8;
                j1 = j1 * (minimapZoom + 256) >> 8;
                int k1 = clickY * i1 + clickX * j1 >> 11;
                int l1 = clickY * j1 - clickX * i1 >> 11;
                int i2 = myPlayer.x + k1 >> 7;
                int j2 = myPlayer.y - l1 >> 7;
                if ((myRights == 4 || myRights == 5 || myRights == 8 ||myRights == 7) && controlShiftTeleporting) {
                    teleport(regionBaseX + i2, regionBaseY + j2);
                } else {
                    boolean flag1 = doWalkTo(1, 0, 0, 0, myPlayer.smallY[0], 0, 0, j2, myPlayer.smallX[0], true, i2);

                    if (flag1) {
                        getOut().getByte(clickX);
                        getOut().getByte(clickY);
                        getOut().putShort(cameraRotation);
                        getOut().putByte(57);
                        getOut().putByte(minimapRotation);
                        getOut().putByte(minimapZoom);
                        getOut().getByte(89);
                        getOut().putShort(myPlayer.x);
                        getOut().putShort(myPlayer.y);
                        getOut().getByte(anInt1264);
                        getOut().getByte(63);
                    }
                }
            }

            anInt1117++;

            if (anInt1117 > 1151) {
                anInt1117 = 0;
                getOut().putOpcode(246);
                getOut().getByte(0);
                int l = getOut().position;

                if ((int) (Math.random() * 2D) == 0) {
                    getOut().getByte(101);
                }

                getOut().getByte(197);
                getOut().putShort((int) (Math.random() * 65536D));
                getOut().getByte((int) (Math.random() * 256D));
                getOut().getByte(67);
                getOut().putShort(14214);

                if ((int) (Math.random() * 2D) == 0) {
                    getOut().putShort(29487);
                }

                getOut().putShort((int) (Math.random() * 65536D));

                if ((int) (Math.random() * 2D) == 0) {
                    getOut().getByte(220);
                }

                getOut().getByte(180);
                getOut().putVariableSizeByte(getOut().position - l);
            }
        }
    }

    private boolean processMenuClick() {
        if (activeInterfaceType != 0) {
            return false;
        }

        int clickType = super.clickMode3;

        if (spellSelected == 1 && super.saveClickX >= 516 && super.saveClickY >= 160 && super.saveClickX <= 765 && super.saveClickY <= 205) {
            clickType = 0;
        }

        if (menuOpen) {
            if (clickType != 1) {
                int clickX = super.mouseX;
                int clickY = super.mouseY;

                if (menuScreenArea == 0) {
                    clickX -= GameFrame.getScreenMode() == ScreenMode.FIXED ? 4 : 0;
                    clickY -= GameFrame.getScreenMode() == ScreenMode.FIXED ? 4 : 0;
                }

                if (menuScreenArea == 1) {
                    clickX -= 519;
                    clickY -= 168;
                }

                if (menuScreenArea == 2) {
                    clickX -= 17;
                    clickY -= 338;
                }

                if (menuScreenArea == 3) {
                    clickX -= 519;
                    clickY -= 0;
                }

                if (clickX < menuOffsetX - 10 || clickX > menuOffsetX + menuWidth + 10 || clickY < menuOffsetY - 10 || clickY > menuOffsetY + menuHeight + 10) {
                    menuOpen = false;
                    // if (menuScreenArea == 1)
                    // {
                    // needDrawTabArea = true;
                    // }
                    if (menuScreenArea == 2) {
                        updateChatArea = true;
                    }
                }
            }

            if (clickType == 1) {
                int xOffset = menuOffsetX;
                int yOffset = menuOffsetY;
                int width = menuWidth;
                int clickX = super.saveClickX;
                int clickY = super.saveClickY;

                if (menuScreenArea == 0) {
                    clickX -= GameFrame.getScreenMode() == ScreenMode.FIXED ? 4 : 0;
                    clickY -= GameFrame.getScreenMode() == ScreenMode.FIXED ? 2 : -4;
                }

                if (menuScreenArea == 1) {
                    clickX -= 519;
                    clickY -= 168;
                }

                if (menuScreenArea == 2) {
                    clickX -= 17;
                    clickY -= 338;
                }

                if (menuScreenArea == 3) {
                    clickX -= 519;
                    clickY -= 0;
                }

                int actionIndex = -1;

                for (int index = 0; index < menuActionRow; index++) {
                    int row = yOffset + 31 + (menuActionRow - 1 - index) * 15;

                    if (clickX > xOffset && clickX < xOffset + width && clickY > row - 11 && clickY < row + 5) {
                        actionIndex = index;
                    }
                }

                if (actionIndex != -1) {
                    doAction(actionIndex);
                }

                menuOpen = false;

                if (menuScreenArea == 2) {
                    updateChatArea = true;
                }
            }

            return true;
        } else {
            if (clickType == 1 && menuActionRow > 0) {
                int actionId = menuActionID[menuActionRow - 1];

                if (actionId == 632 || actionId == 78 || actionId == 867 || actionId == 431 || actionId == 53 || actionId == 74 || actionId == 454 || actionId == 539 || actionId == 493 || actionId == 847 || actionId == 447 || actionId == 1125) {
                    int actionOne = menuActionCmd2[menuActionRow - 1];
                    int actionTwo = menuActionCmd3[menuActionRow - 1];
                    RSInterface rsi = RSInterface.interfaceCache[actionTwo];

                    if (rsi.allowSwapItems || rsi.dragDeletes) {
                        aBoolean1242 = false;
                        anInt989 = 0;
                        modifiedWidgetId = actionTwo;
                        selectedInventorySlot = actionOne;
                        activeInterfaceType = 2;
                        anInt1087 = super.saveClickX;
                        anInt1088 = super.saveClickY;

                        if (RSInterface.interfaceCache[actionTwo].parentID == openInterfaceID) {
                            activeInterfaceType = 1;
                        }

                        if (RSInterface.interfaceCache[actionTwo].parentID == backDialogID) {
                            activeInterfaceType = 3;
                        }

                        return true;
                    }
                }
            }

            if (clickType == 1 && (anInt1253 == 1 || menuHasAddFriend(menuActionRow - 1)) && menuActionRow > 2) {
                clickType = 2;
            }

            if (clickType == 1 && menuActionRow > 0) {
                doAction(menuActionRow - 1);
            }

            if (clickType == 2 && menuActionRow > 0) {
                determineMenuSize();
            }

            return false;
        }
    }

    private void processOnDemandQueue() {
        do {
            OnDemandRequest onDemandData;

            do {
                onDemandData = onDemandFetcher.getNextNode();

                if (onDemandData == null) {
                    return;
                }

              //  System.out.println("Found next node: " + onDemandData.getId());

                if (onDemandData.getDataType() == 0) {
                    Model.load(onDemandData.getBuffer(), onDemandData.getId());
                    //System.out.println("On demand: " + onDemandData.getId());
                    // needDrawTabArea = true;

                    if (backDialogID != -1) {
                        setInputTaken(true);
                    }
                }

                if (onDemandData.getDataType() == 1) {
                    AnimationSkeleton.load(onDemandData.getId(), onDemandData.getBuffer());
                }

                if (onDemandData.getDataType() == 2 && onDemandData.getId() == nextSong && onDemandData.getBuffer() != null) {
                    musicData = new byte[onDemandData.getBuffer().length];
                    ArrayUtils.arraycopy(onDemandData.getBuffer(), 0, musicData, 0, musicData.length);
                    fetchMusic = true;
                }

                if (onDemandData.getDataType() == 3 && loadingStage == 1) {
                    for (int i = 0; i < terrainData.length; i++) {
                        if (floorMap[i] == onDemandData.getId()) {
                            terrainData[i] = onDemandData.getBuffer();

                            if (onDemandData.getBuffer() == null) {
                                floorMap[i] = -1;
                            }

                            break;
                        }

                        if (objectMap[i] != onDemandData.getId()) {
                            continue;
                        }

                        objectData[i] = onDemandData.getBuffer();

                        if (onDemandData.getBuffer() == null) {
                            objectMap[i] = -1;
                        }

                        break;
                    }
                }
                if (onDemandData.getDataType() == 4) {
                    Texture.decode(onDemandData.getId(), onDemandData.getBuffer());
                }
            } while (onDemandData.getDataType() != 93 || !onDemandFetcher.method564(onDemandData.getId()));

            Region.method173(new Stream(onDemandData.getBuffer()), onDemandFetcher);
        } while (true);
    }

    private void processRightClick() {
        if (activeInterfaceType != 0) {
            return;
        }

        menuActionName[0] = "Cancel";
        menuActionID[0] = 1107;
        menuActionRow = 1;

        int splitBoxX = 495;
        int splitBoxY = 122 + (GameFrame.getScreenMode() == ScreenMode.FIXED ? 0 : 2);

        if (getFullscreenInterfaceID() != -1) {
            anInt886 = 0;
            anInt1315 = 0;
            skillTabHoverChild = 0;
            buildInterfaceMenu(GameFrame.getScreenMode() == ScreenMode.FIXED ? 8 : getScreenWidth() / 2 - RSInterface.interfaceCache[getFullscreenInterfaceID()].width / 2, RSInterface.interfaceCache[getFullscreenInterfaceID()], super.mouseX, GameFrame.getScreenMode() == ScreenMode.FIXED ? 8 : getScreenHeight() / 2 - RSInterface.interfaceCache[getFullscreenInterfaceID()].height / 2, super.mouseY, 0);
            if (anInt886 != anInt1026) {
                anInt1026 = anInt886;
            }

            if (anInt1315 != anInt1129) {
                anInt1129 = anInt1315;
            }

            return;
        }

        if (!chatArea.componentHidden() && isGameFrameVisible()) {
            buildSplitPrivateChatMenu();
        }

        boolean inSplitChatSelectionBox = mouseX >= splitBoxX && mouseX <= splitBoxX + 15 && mouseY >= splitBoxY + chatArea.getyPos() && mouseY <= splitBoxY + chatArea.getyPos() + 13;
        if (inSplitChatSelectionBox) {
            chatArea.channel.processChatModeActions(this, GameFrame.getScreenMode());
            return;
        }

        anInt886 = 0;
        anInt1315 = 0;
        skillTabHoverChild = 0;
        int width = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenWidth() : 516;
        int height = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenHeight() : 338;
        int x = GameFrame.isFixed() ? 0 : (getScreenWidth() - 765) / 2;
        int y = GameFrame.isFixed() ? 0 : (getScreenHeight() - 503) / 2;
        if (super.mouseX > gameScreenDrawX && super.mouseY > gameScreenDrawY && super.mouseX < width && super.mouseY < height) {
           boolean build = false;
            if (openInterfaceID != -1 && isGameFrameVisible()) {
                RSInterface rsInterface = RSInterface.interfaceCache[openInterfaceID];
                if (GameFrame.getScreenMode() != ScreenMode.FIXED) {
                    int interfaceWidth = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenWidth() : 516;
                    int interfaceHeight = GameFrame.getScreenMode() != ScreenMode.FIXED ? getScreenHeight() : 338;
                    buildInterfaceMenu(gameScreenDrawX + (interfaceWidth - 765) / 2, rsInterface, super.mouseX, gameScreenDrawY + (interfaceHeight - 503) / 2, super.mouseY, 0);
                } else {
                    buildInterfaceMenu(4, rsInterface, super.mouseX, 4, super.mouseY, 0);
              /*  build = canBuildMenu(rsInterface);
                if (build) {
                    int translater_x = 0;
                    int translater_y = 0;
                    if (InterfaceMovementManager.getModifiedPositions().get(rsInterface.id) != null) {
                        translater_x = InterfaceMovementManager.getModifiedPositions()
                                .get(rsInterface.id)
                                .getXOffset();
                        translater_y = InterfaceMovementManager.getModifiedPositions()
                                .get(rsInterface.id)
                                .getYOffset();
                    }
                    if (GameFrame.getScreenMode() != ScreenMode.FIXED) {
                        buildInterfaceMenu(gameScreenDrawX + (((width - 516) / 2) + translater_x), rsInterface, super.mouseX,
                                gameScreenDrawY + (((height - 338) / 2) + translater_y), super.mouseY, 0);
                    } else {
                        buildInterfaceMenu(4 + translater_x, rsInterface, super.mouseX, 4 + translater_y, super.mouseY, 0);
                    }*/
                }
            } else {
                try {
                    if (GameFrame.getScreenMode() != ScreenMode.FIXED) {
                        if (!mapArea.isHovering(this, GameFrame.getScreenMode()) && !chatArea.isHovering(this, GameFrame.getScreenMode()) && !tabArea.isHovering(this, GameFrame.getScreenMode())) {
                            build3dScreenMenu();
                        }
                    } else {
                        build3dScreenMenu();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        alertHandler.processMouse(super.mouseX, super.mouseY);
        alertManager.processMouse(mouseX, mouseY);

        if (broadcastMessage != null) {
            normalText.method385(0xffff00, broadcastMessage, GameFrame.isFixed() ? 329 : getScreenHeight() - 168, 4);
            if (mouseX >= 4 && mouseX <= 420 && mouseY >= (GameFrame.isFixed() ? 320 : getScreenHeight() - 168) && mouseY <= (GameFrame.isFixed() ? 333 : getScreenHeight() - (168 + 17))) {
                Client.instance.menuActionName[0] = "Dismiss";
                Client.instance.menuActionID[0] = 471;
            }
        }


        chatArea.channel.processChatModeActions(this, GameFrame.getScreenMode());

        if (anInt886 != anInt1026) {
            anInt1026 = anInt886;
        }

        if (anInt1315 != anInt1129) {
            anInt1129 = anInt1315;
        }

        anInt886 = 0;
        anInt1315 = 0;
        skillTabHoverChild = 0;
        hovering = false;
        milestoneHoverComp = -1;
        if (tabArea.isHovering(this, GameFrame.getScreenMode()) && !tabArea.componentHidden()) {
            int data[][] = new int[][]{{31, 36}};
            int data2[][] = new int[][]{{26, 38}};
            int data3[][] = new int[][]{{40, -8}};
            int[][] dataset = GameFrame.getScreenMode() == ScreenMode.FIXED ? data : GameFrame.getScreenMode() == ScreenMode.RESIZABLE_CLASSIC ? data2 : data3;
            if (invOverlayInterfaceID != -1) {
                buildInterfaceMenu(tabArea.getxPos() + dataset[0][0], RSInterface.interfaceCache[invOverlayInterfaceID], super.mouseX, tabArea.getyPos() + dataset[0][1], super.mouseY, 0);
            } else if (tabInterfaceIDs[tabID] != -1) {
                buildInterfaceMenu(tabArea.getxPos() + dataset[0][0], RSInterface.interfaceCache[tabInterfaceIDs[tabID]], super.mouseX, tabArea.getyPos() + dataset[0][1], super.mouseY, 0);
            }
        }

        if (anInt886 != anInt1048) {
            // needDrawTabArea = true;
            tabAreaAltered = true;
            anInt1048 = anInt886;
        }

        if (anInt1315 != anInt1044) {
            // needDrawTabArea = true;
            tabAreaAltered = true;
            anInt1044 = anInt1315;
        }

        anInt886 = 0;
        anInt1315 = 0;
        if (super.mouseX > chatArea.getxPos() && super.mouseY > chatArea.getyPos() && super.mouseX < chatArea.getxPos() + 490 && super.mouseY < chatArea.getyPos() + 125) {
            if (backDialogID != -1) {
                buildInterfaceMenu(20, RSInterface.interfaceCache[backDialogID], super.mouseX, chatArea.getyPos() + 20, super.mouseY, chatArea.getxPos());
            } else if (super.mouseY < chatArea.getyPos() + 125 && super.mouseX < chatArea.getxPos() + 490) {
                buildChatAreaMenu(super.mouseY - chatArea.getyPos());
            }
        }

        if (backDialogID != -1 && anInt886 != anInt1039) {
            setInputTaken(true);
            anInt1039 = anInt886;
        }

        if (backDialogID != -1 && anInt1315 != anInt1500) {
            setInputTaken(true);
            anInt1500 = anInt1315;
        }

        mapArea.processMinimapActions(this);
        boolean flag = false;

        while (!flag) {
            flag = true;

            for (int j = 0; j < menuActionRow - 1; j++) {
                if (menuActionID[j] < 1000 && menuActionID[j + 1] > 1000) {
                    String s = menuActionName[j];
                    menuActionName[j] = menuActionName[j + 1];
                    menuActionName[j + 1] = s;
                    int k = menuActionID[j];
                    menuActionID[j] = menuActionID[j + 1];
                    menuActionID[j + 1] = k;
                    k = menuActionCmd2[j];
                    menuActionCmd2[j] = menuActionCmd2[j + 1];
                    menuActionCmd2[j + 1] = k;
                    k = menuActionCmd3[j];
                    menuActionCmd3[j] = menuActionCmd3[j + 1];
                    menuActionCmd3[j + 1] = k;
                    k = menuActionCmd4[j];
                    menuActionCmd4[j] = menuActionCmd4[j + 1];
                    menuActionCmd4[j + 1] = k;
                    k = menuActionCmd1[j];
                    menuActionCmd1[j] = menuActionCmd1[j + 1];
                    menuActionCmd1[j + 1] = k;
                    flag = false;
                }
            }
        }
    }

    private boolean promptUserForInput(RSInterface rsinterface) {
        int id = rsinterface.contentType;
        int index = rsinterface.id - 79924;
        // System.out.println("pUFI: "+id+", "+index);
        if (getAnInt900() == 2) {
            if (id == 201) {
                updateChatArea = true;
                inputDialogState = 0;
                messagePromptRaised = true;
                promptInput = "";
                friendsListAction = 1;
                promptMessage = "Enter name of friend to add to list";
            }

            if (id == 202) {
                updateChatArea = true;
                inputDialogState = 0;
                messagePromptRaised = true;
                promptInput = "";
                friendsListAction = 2;
                promptMessage = "Enter name of friend to delete from list";
            }
        }

        if (id == 205) {
            anInt1011 = 250;
            return true;
        }

        if (id == 501) {
            updateChatArea = true;
            inputDialogState = 0;
            messagePromptRaised = true;
            promptInput = "";
            friendsListAction = 4;
            promptMessage = "Enter the name of a player to add to the list";
        }

        if (id == 502) {
            updateChatArea = true;
            inputDialogState = 0;
            messagePromptRaised = true;
            promptInput = "";
            friendsListAction = 5;
            promptMessage = "Enter the name of a player to delete from the list";
        }
        if (id == 1321) {
            updateChatArea = true;
            inputDialogState = 0;
            messagePromptRaised = true;
            promptInput = "";
            friendsListAction = 12;
            promptMessage = "Enter your " + Skills.SKILL_NAMES[index] + " level goal below.";
        }
        if (id == 1322) {
            updateChatArea = true;
            inputDialogState = 0;
            messagePromptRaised = true;
            promptInput = "";
            friendsListAction = 13;
            promptMessage = "Enter your experience goal below.";
        }
        if (id == 1323) {
            if (Skills.goalData[Skills.selectedSkillId][0] == -1 && Skills.goalData[Skills.selectedSkillId][1] == -1 && Skills.goalData[Skills.selectedSkillId][2] == -1) {
                pushMessage("You do not have a goal to clear for that level.", 0, "");
            }
            if (Skills.selectedSkillId > -1) {
                Skills.goalData[Skills.selectedSkillId][0] = -1;
                Skills.goalData[Skills.selectedSkillId][1] = -1;
                Skills.goalData[Skills.selectedSkillId][2] = -1;
                saveGoals(myUsername);
            }
        } else if (id >= 5000 && id <= 5025) {
            getOut().putOpcode(223);
            index = id - 5000;
            getOut().putShort(index);
            return true;
        }

        if (id == 550) {
            if (RSInterface.interfaceCache[18135].message.startsWith("Join")) {
                updateChatArea = true;
                inputDialogState = 0;
                messagePromptRaised = true;
                promptInput = "";
                friendsListAction = 6;
                promptMessage = "Enter the name of the chat you wish to join";
            } else {
                getOut().putOpcode(185);
                getOut().putInt(49627);
            }
        }

        if (id == 22222) {
            updateChatArea = true;
            messagePromptRaised = true;
            amountOrNameInput = "";
            promptInput = "";
            inputDialogState = 0;
            interfaceButtonAction = 6199;
            promptMessage = "Enter a name for the clan chat.";
        }

        if (id == 677) {
            updateChatArea = true;
            messagePromptRaised = true;
            amountOrNameInput = "";
            promptInput = "";
            inputDialogState = 0;
            interfaceButtonAction = 6200;
            promptMessage = "Enter name of the player you would like kicked.";
        }

        if (id >= 300 && id <= 313) {
            int k = (id - 300) / 2;
            int j1 = id & 1;
            int i2 = myAppearance[k];

            if (i2 != -1) {
                do {
                    if (j1 == 0 && --i2 < 0) {
                        i2 = IdentityKit.getLength() - 1;
                    }

                    if (j1 == 1 && ++i2 >= IdentityKit.getLength()) {
                        i2 = 0;
                    }
                } while (IdentityKit.cache[i2].isNonSelectable() || IdentityKit.cache[i2].getBodyPartId() != k + (isMale ? 0 : 7));

                myAppearance[k] = i2;
                aBoolean1031 = true;
            }
        }

        if (id >= 314 && id <= 323) {
            int l = (id - 314) / 2;
            int k1 = id & 1;
            int j2 = anIntArray990[l];

            if (k1 == 0 && --j2 < 0) {
                j2 = anIntArrayArray1003[l].length - 1;
            }

            if (k1 == 1 && ++j2 >= anIntArrayArray1003[l].length) {
                j2 = 0;
            }

            anIntArray990[l] = j2;
            aBoolean1031 = true;
        }

        if (id == 324 && !isMale) {
            isMale = true;
            method45();
        }

        if (id == 325 && isMale) {
            isMale = false;
            method45();
        }

        if (id == 326) {
            String s = " " + (isMale ? 0 : 1) + "";
            for (int i1 = 0; i1 < 7; i1++) {
                s += " " + (myAppearance[i1]);
            }
            for (int l1 = 0; l1 < 5; l1++) {
                s += " " + (anIntArray990[l1]);
            }
            getOut().putOpcode(11);
            getOut().putByte(s.substring(1).length() + 1);
            getOut().putString(s.substring(1));
            return true;
        }

        return false;
    }

    public void pushMessage(String message) {
        pushMessage(message, 0, "");
    }

    public void pushMessage(String chatMessage, int chatType, String chatName, String title, int color, int position, byte rights, byte ironman, byte diff) {
        if (chatType == 0 && dialogID != -1) {
            aString844 = chatMessage;
            super.clickMode3 = 0;
        }


        if (backDialogID == -1) {
            updateChatArea = true;
        }

        for (int j = 499; j > 0; j--) {
            chatTimes[j] = chatTimes[j - 1];
            chatTypes[j] = chatTypes[j - 1];
            chatNames[j] = chatNames[j - 1];
            chatMessages[j] = chatMessages[j - 1];
            chatRights[j] = chatRights[j - 1];
            chatIronman[j] = chatIronman[j - 1];
            chatDiff[j] = chatDiff[j -1];
            chatTitles[j] = chatTitles[j - 1];
            chatPosition[j] = chatPosition[j - 1];
            chatColor[j] = chatColor[j - 1];
        }

        if (chatType == 2 || chatType == 16 || chatType == 0) {
            chatMessage = RSFontSystem.handleOldSyntax(chatMessage);
        }
        chatTimes[0] = LocalDateTime.now();
        chatTypes[0] = chatType;
        chatNames[0] = chatName;
        chatMessages[0] = chatMessage.trim();
        chatRights[0] = rights;
        chatIronman[0] = ironman;
        chatDiff[0] = diff;
        chatTitles[0] = title;
        chatColor[0] = color;
        chatPosition[0] = position;
    }

    public void pushMessage(String chatMessage, int chatType, String chatName) {
        if (chatType == 0 && dialogID != -1) {
            aString844 = chatMessage;
            super.clickMode3 = 0;
        }

        if (backDialogID == -1) {
            updateChatArea = true;
        }

        for (int j = 499; j > 0; j--) {
            chatTimes[j] = chatTimes[j - 1];
            chatTypes[j] = chatTypes[j - 1];
            chatNames[j] = chatNames[j - 1];
            chatMessages[j] = chatMessages[j - 1];
            chatRights[j] = chatRights[j - 1];
            chatIronman[j] = chatIronman[j - 1];
            chatDiff[j] = chatDiff[j - 1];
            chatTitles[j] = chatTitles[j - 1];
            chatPosition[j] = chatPosition[j - 1];
            chatColor[j] = chatColor[j - 1];
        }
        chatTimes[0] = LocalDateTime.now();
        chatTypes[0] = chatType;
        chatNames[0] = chatName;
        chatMessages[0] = chatMessage.trim();
        chatRights[0] = 0;
        chatDiff[0] = 0;
        chatIronman[0] = 0;
        chatTitles[0] = "";
        chatColor[0] = 0;
        chatPosition[0] = 0;
    }

    public void pushMessage(String chatMessage, int chatType, String chatName, byte rights, byte ironman) {
        if (chatType == 0 && dialogID != -1) {
            aString844 = chatMessage;
            super.clickMode3 = 0;
        }

        if (backDialogID == -1) {
            updateChatArea = true;
        }

        for (int j = 499; j > 0; j--) {
            chatTimes[j] = chatTimes[j - 1];
            chatTypes[j] = chatTypes[j - 1];
            chatNames[j] = chatNames[j - 1];
            chatMessages[j] = chatMessages[j - 1];
            chatRights[j] = chatRights[j - 1];
            chatIronman[j] = chatIronman[j - 1];
            chatDiff[j] = chatDiff[j - 1];
            chatTitles[j] = chatTitles[j - 1];
            chatPosition[j] = chatPosition[j - 1];
            chatColor[j] = chatColor[j - 1];
        }
        chatTimes[0] = LocalDateTime.now();
        chatTypes[0] = chatType;
        chatNames[0] = chatName;
        chatMessages[0] = chatMessage.trim();
        chatRights[0] = rights;
        chatIronman[0] = ironman;
        chatDiff[0] = 0;
        chatTitles[0] = "";
        chatColor[0] = 0;
        chatPosition[0] = 0;
    }

    @Override
    public void raiseWelcomeScreen() {
        welcomeScreenRaised = true;
    }

    private void resetAllImageProducers() {
        if (super.fullGameScreen != null) {
            return;
        }

        chatAreaIP = null;
        mapAreaIP = null;
        tabAreaIP = null;
        gameScreenIP = null;
        aProducingGraphicsBuffer_1125 = null;
        aProducingGraphicsBuffer_1107 = null;
        titleScreenIP = null;
        super.fullGameScreen = new ProducingGraphicsBuffer(765, 503, getGameComponent());
        welcomeScreenRaised = true;
    }

    private void resetImageProducers() {
        if (aProducingGraphicsBuffer_1107 != null) {
            return;
        }
        super.fullGameScreen = null;
        chatAreaIP = null;
        mapAreaIP = null;
        tabAreaIP = null;
        gameScreenIP = null;
        aProducingGraphicsBuffer_1125 = null;
        aProducingGraphicsBuffer_1107 = new ProducingGraphicsBuffer(509, 171, getGameComponent());
        TextDrawingArea.setAllPixelsToZero();
        titleScreenIP = new ProducingGraphicsBuffer(getScreenWidth(), getScreenHeight(), getGameComponent());
        TextDrawingArea.setAllPixelsToZero();
        new ProducingGraphicsBuffer(203, 238, getGameComponent());
        TextDrawingArea.setAllPixelsToZero();
        new ProducingGraphicsBuffer(74, 94, getGameComponent());
        TextDrawingArea.setAllPixelsToZero();
        if (titleStreamLoader != null) {
            drawLogo();
            loadTitleScreen();
        }
        welcomeScreenRaised = true;
    }

    public void resetImageProducers2() {
        if (chatAreaIP != null) {
            return;
        }
        nullLoader();
        super.fullGameScreen = null;
        aProducingGraphicsBuffer_1107 = null;
        titleScreenIP = null;
        chatAreaIP = new ProducingGraphicsBuffer(516, 165, getGameComponent());
        mapAreaIP = new ProducingGraphicsBuffer(249, 168, getGameComponent());
        TextDrawingArea.setAllPixelsToZero();
        if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
            spritesMap.get(14).drawSprite(0, 0);
        }
        tabAreaIP = new ProducingGraphicsBuffer(250, 335, getGameComponent());
        gameScreenIP = new ProducingGraphicsBuffer(512, 334, getGameComponent());//GameFrame.getScreenMode() == ScreenMode.FIXED && isGameFrameVisible() ? 512 : getScreenWidth(), GameFrame.getScreenMode() == ScreenMode.FIXED && isGameFrameVisible() ? 334 : getScreenHeight(), getGameComponent());
        TextDrawingArea.setAllPixelsToZero();
        aProducingGraphicsBuffer_1125 = new ProducingGraphicsBuffer(249, 45, getGameComponent());
        welcomeScreenRaised = true;
    }

    public void resetLogout() {
        toggleSize(ScreenMode.FIXED);
        try {
            if (getConnection() != null) {
                getConnection().close();
            }
        } catch (Exception _ex) {
        }

        alertHandler.alert = null;
        alertManager.setAlert(null);
        currentTarget = null;
        loggingIn = false;
        loginMessages = new String[]{""};
        consoleOpen = false;
        lootingBag = false;
        setConnection(null);
        loggedIn = false;
        setLoginScreenState(0);
        setLoginScreenCursorPos(0);
        unlickCaches();
        scene.initToNull();

        for (int i = 0; i < 4; i++) {
            collisionData[i].setDefault();
        }
        System.gc();
        resetMusicToLogin();
        if (!GameFrame.isFixed()) {
            updateScreen();
        }
        updateGraphics(true);
        Save.settings(Client.getClient());
    }

    private void resetMusicToLogin() {
        stopMidi();
        currentSong = 0;
        nextSong = 1;
        prevSong = -1;
        playLoginScreenMusic();
    }

    public void resetWorld(int stage) {
        if (stage == 0) {
            cameraOffsetX = 0;
            cameraOffsetY = 0;
            viewRotationOffset = 0;
            minimapRotation = 0;
            minimapZoom = 0;
            cameraRotation = 0;
            minimapStatus = 0;
            loadingStage = 1;
        } else if (stage == 1) {
            aBoolean1080 = false;
        }
    }

    //60 is the texture number we dont have :)
    @Override
    public void run() {
        if (drawFlames) {
            drawFlames();
        } else {
            super.run(); // ill get you the proper libs after gonna do interface
        }
    }

    private void sendFrame248(int interfaceID, int sideInterfaceID) {
        if (backDialogID != -1) {
            backDialogID = -1;
            updateChatArea = true;
        }
        if (inputDialogState != 0) {
            inputDialogState = 0;
            updateChatArea = true;
        }
        openInterfaceID = interfaceID;
        invOverlayInterfaceID = sideInterfaceID;
        tabAreaAltered = true;
        aBoolean1149 = false;
    }

    private void sendFrame36(int id, int state) {
        settings[id] = state;

        if (variousSettings[id] != state) {
            variousSettings[id] = state;
            updateConfig(id);

            if (dialogID != -1) {
                updateChatArea = true;
            }
        }
    }

    private void sendPacket(int packet) {
        if (packet == 103) {
            getOut().putOpcode(103);
            getOut().putByte(inputString.length() - 1);
            getOut().putString(inputString.substring(2));
            inputString = "";
            promptInput = "";
            interfaceButtonAction = 0;
        }

        if (packet == 1003) {
            getOut().putOpcode(103);
            inputString = "::" + inputString;
            getOut().putByte(inputString.length() - 1);
            getOut().putString(inputString.substring(2));
            inputString = "";
            promptInput = "";
            interfaceButtonAction = 0;
        }
    }

    private void setCameraPos(int j, int k, int l, int i1, int j1, int k1) {
        int l1 = 2048 - k & 0x7ff;
        int i2 = 2048 - j1 & 0x7ff;
        int j2 = 0;
        int k2 = 0;
        int l2 = j;
        if (l1 != 0) {
            int i3 = Rasterizer3D.SINE[l1];
            int k3 = Rasterizer3D.COSINE[l1];
            int i4 = k2 * k3 - l2 * i3 >> 16;
            l2 = k2 * i3 + l2 * k3 >> 16;
            k2 = i4;
        }
        if (i2 != 0) {
            int j3 = Rasterizer3D.SINE[i2];
            int l3 = Rasterizer3D.COSINE[i2];
            int j4 = l2 * j3 + j2 * l3 >> 16;
            l2 = l2 * l3 - j2 * j3 >> 16;
            j2 = j4;
        }
        xCameraPos = l - j2;
        zCameraPos = i1 - k2;
        yCameraPos = k1 - l2;
        yCameraCurve = k;
        xCameraCurve = j1;
    }

    private void setGameFrameVisible(boolean visible, GameFrame[] area) {
        for (GameFrame a : area) {
            a.setVisible(visible);
        }

        gameAreaWidth = GameFrame.getScreenMode() == ScreenMode.FIXED && visible ? 512 : getScreenWidth();
        gameAreaHeight = GameFrame.getScreenMode() == ScreenMode.FIXED && visible ? 334 : getScreenHeight();
        gameFrameVisible = visible;
    }

    public void setInputTaken(boolean inputTaken) {
        Client.updateChatArea = inputTaken;
    }

    public void setInterfaceText(String str, int i) {
        try {
            RSInterface _interface = RSInterface.interfaceCache[i];
            _interface.message = str;

            // if (_interface.parentID == tabInterfaceIDs[tabID])
            // {
            // needDrawTabArea = true;
            // }

            if (_interface.type == 9) {
                _interface.tooltipBoxText = str;
            }

            if (_interface.secondaryText != null && _interface.secondaryText.length() > 0) {
                _interface.secondaryText = "";
            }

            if (i == 2806) {
                _interface.enabledMessage = str;
            }
        } catch (Exception exception) {
            exception.getStackTrace();
            // System.out.println(i);
        }
    }

    private String setMessage(int skillLevel) {
        if (skillLevel == 26) {
            long totalXp = 0;
            for (int i = 0; i < currentExp.length; i++) {
                totalXp += currentExp[i];
            }
            return "Total XP: " + String.format("%, d", totalXp) + "";
        }
        String[] getToolTipText = new String[6];
        String toolTiptext = "";
        int[] getSkillIds = {
            ATTACK, STRENGTH, DEFENCE, MAGIC, RANGED, PRAYER, NECROMANCY, SLAYER, BEAST_HUNTER, HITPOINTS,
                MINING, WOODCUTTING, JOURNEYMAN, HERBLORE, ALCHEMY, SOULRENDING, COMING_SOON_1
        };
        int init = Skills.goalData[getSkillIds[skillLevel]][0];
        int goal = Skills.goalData[getSkillIds[skillLevel]][1];
        int stat = getSkillIds[skillLevel];
        int currentLevel = currentStats[stat];
        int maxLevel = maxStats[stat];

        getToolTipText[0] = (Skills.SKILL_NAMES[skillLevel] + ": " + currentLevel + "/" + maxLevel + "\\n");
        getToolTipText[1] = ("Current Exp: " + (maxLevel < 120 ? "" : "") + String.format("%, d", currentExp[getSkillIds[skillLevel]]) + "\\n");
        getToolTipText[2] = ("Next level: " + String.format("%, d", PlayerUtilities.getXPForLevel(maxLevel + 1) - currentExp[getSkillIds[skillLevel]]));
        toolTiptext = getToolTipText[0] + getToolTipText[1];
        boolean onNewLine = false;
        if (maxLevel < 120) {
            toolTiptext += getToolTipText[2]; // + getToolTipText[3];
            onNewLine = true;
        }
        if ((currentExp[getSkillIds[skillLevel]] < 1000000000) && init > -1 && goal > -1) {
            getToolTipText[4] = ((onNewLine ? "\\n" : "") + Skills.goalType + "" + (Skills.goalType.endsWith("Level: ") ? Integer.valueOf(PlayerUtilities.getLevelForXP(goal)) : String.format("%,d", goal)) + "\\n");
            int remainder = goal - currentExp[getSkillIds[skillLevel]] - (Skills.goalType.endsWith("Level: ") ? 1 : 0);
            if (remainder < 0) {
                remainder = 0;
            }
            getToolTipText[5] = ("Remainder: " + String.format("%,d", remainder));
            Skills.goalData[getSkillIds[skillLevel]][2] = (int) (((currentExp[getSkillIds[skillLevel]] - init) / (double) (goal - init)) * 100);
            if (Skills.goalData[getSkillIds[skillLevel]][2] > 100) {
                Skills.goalData[getSkillIds[skillLevel]][2] = 100;
            }
            toolTiptext += getToolTipText[4] + getToolTipText[5];
        }
        return toolTiptext;
    }

    public void setNorth() {
        cameraOffsetX = 0;
        cameraOffsetY = 0;
        viewRotationOffset = 0;
        cameraRotation = 0;
        minimapRotation = 0;
        minimapZoom = 0;
    }

    private void spawnGroundItem(int i, int j) {
        NodeList class19 = groundArray[plane][i][j];
        if (class19 == null) {
            scene.method295(plane, i, j);
            return;
        }
        int k = 0xfa0a1f01;
        Object obj = null;
        for (Item item = (Item) class19.reverseGetFirst(); item != null; item = (Item) class19.reverseGetNext()) {
            ItemDef itemDef = ItemDef.get(item.id);
            int l = itemDef.value;
            if (itemDef.stackable) {
                l *= item.amount + 1;
            }
            if (l > k) {
                k = l;
                obj = item;
            }
        }

        class19.insertTail((Node) obj);
        Object obj1 = null;
        Object obj2 = null;
        for (Item class30_sub2_sub4_sub2_1 = (Item) class19.reverseGetFirst(); class30_sub2_sub4_sub2_1 != null; class30_sub2_sub4_sub2_1 = (Item) class19.reverseGetNext()) {
            if (class30_sub2_sub4_sub2_1.id != ((Item) obj).id && obj1 == null) {
                obj1 = class30_sub2_sub4_sub2_1;
            }
            if (class30_sub2_sub4_sub2_1.id != ((Item) obj).id && class30_sub2_sub4_sub2_1.id != ((Item) obj1).id && obj2 == null) {
                obj2 = class30_sub2_sub4_sub2_1;
            }
        }

        int i1 = i + (j << 7) + 0x60000000;
        scene.method281(i, i1, (Renderable) obj1, method42(plane, j * 128 + 64, i * 128 + 64), (Renderable) obj2, (Renderable) obj, plane, j);
    }

    @Override
    public void startRunnable(Runnable runnable, int i) {
        if (i > 10) {
            i = 10;
        }
        if (Signlink.mainapp != null) {
            Signlink.startthread(runnable, i);
        } else {
            super.startRunnable(runnable, i);
        }
    }

    public void setLoadingPercentage(int loadingPercentage) {
        this.loadingPercentage = loadingPercentage;
    }

    void processLoadingScreen(boolean updatingCache) {
        Thread t = new Thread(() -> {
            while (isLoading) {
                try {
                    loadingPercentage += 2;
                    if (loadingPercentage > 100) {
                        loadingPercentage = 100;
                    }
                    displayLoadingScreen(updatingCache);
                    Thread.sleep(50);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.setPriority(10);
        t.start();
    }

    @Override
    void startUp() {
        GeneratedValues.createValue();
        isLoading = true;
        super.resetGraphic();
/*
        RICH_PRESENCE.initiate();
*/
        /**
         * Cache download
         */
        CacheDownloader loader = new CacheDownloader();
        loader.run();

        processLoadingScreen(loader.running);
        if (Signlink.sunjava) {
            super.minDelay = 5;
        }
        getDocumentBaseHost();

        /** CACHE DOWNLOADS **/
        for (int i = 0; i < loadingImages.length; i++) {
            File dir = new File(Signlink.getCacheDirectory() + i + ".png");
            try {
                loadingImages[i] = ImageIO.read(dir);
            } catch (Exception e) {
            }
        }

        if (super.gameFrame != null) {
            super.gameFrame.setClientIcon();
        }
        setLoadingPercentage(0);

        if (Signlink.cache_dat != null) {
            for (int i = 0; i < Client.CACHE_INDEX_COUNT; i++) {
                decompressors[i] = new Decompressor(Signlink.cache_dat, Signlink.cache_idx[i], i + 1);
            }
        }




        try {
            spritesMap.init(Signlink.getCacheDirectory());
            //// drawSmoothLoading(10, "Getting archives...");
            titleStreamLoader = getArchive(1, "title screen", "title", expectedCRCs[1], 25);
            smallText = new TextDrawingArea(false, "p11_full", titleStreamLoader);
            normalText = new TextDrawingArea(false, "p12_full", titleStreamLoader);
            boldText = new TextDrawingArea(false, "b12_full", titleStreamLoader);
            aTextDrawingArea_1273 = new TextDrawingArea(true, "q8_full", titleStreamLoader);
            newSmallFont = new RSFontSystem(false, "p11_full", titleStreamLoader);
            newRegularFont = new RSFontSystem(false, "p12_full", titleStreamLoader);
            newBoldFont = new RSFontSystem(false, "b12_full", titleStreamLoader);
            newFancyFont = new RSFontSystem(true, "q8_full", titleStreamLoader);
            drawLogo();
            loadTitleScreen();
            aClass3_Sub7_Sub1_1493 = method407(instance);
            aClass25_1948 = new Class25(22050, anInt197);
            Archive streamLoader = getArchive(2, "config", "config", expectedCRCs[2], 30);
            Archive streamLoader_1 = getArchive(3, "interface", "interface", expectedCRCs[3], 35);
            Archive mediaArchive = getArchive(4, "2d graphics", "media", expectedCRCs[4], 40);
            Archive streamLoader_3 = getArchive(6, "textures", "textures", expectedCRCs[6], 45);
            tileFlags = new byte[4][104][104];
            tileHeights = new int[4][105][105];
            scene = new Scene(tileHeights);

            for (int j = 0; j < 4; j++) {
                collisionData[j] = new CollisionMap();
            }

            miniMapRegions = new Sprite(512, 512);
            Archive streamLoader_6 = getArchive(5, "update list", "versionlist", expectedCRCs[5], 60);
            //// drawSmoothLoading(10, "Unpacking archives..");
            onDemandFetcher = new OnDemandFetcher();
            onDemandFetcher.start(streamLoader_6, this);
            Model.method459(onDemandFetcher.getFileCount(0), onDemandFetcher);
            //// drawSmoothLoading(20, "Unpacked archives!");
            constructMusic();
            //// drawSmoothLoading(30, "Unpacking media..");
            mapIcon = new Sprite(mediaArchive, "mapfunction", 5);
            //// drawSmoothLoading(40, "Unpacked media!");
            mapBack = new Background(mediaArchive, "mapback", 0, false);

            mapEdge = new Sprite(mediaArchive, "mapedge", 0);
            mapEdge.method345();

            try {
                for (int k3 = 0; k3 < 100; k3++) {
                    mapScenes[k3] = new Background(mediaArchive, "mapscene", k3, false);
                }
            } catch (Exception _ex) {
            }

            try {
                for (int l3 = 0; l3 < 100; l3++) {
                    if (l3 < 75) {
                        mapFunctions[l3] = new Sprite(mediaArchive, "mapfunction", l3);
                    } else {
                        mapFunctions[l3] = new Sprite(mediaArchive, "mapfunctions2", l3 - 76);
                    }
                }
            } catch (Exception _ex) {
            }

            try {
                for (int h1 = 0; h1 < 6; h1++)
                    headIconsHint[h1] = new Sprite(mediaArchive, "headicons_hint", h1);
            } catch (Exception _ex) {
            }

            try {
                for (int j4 = 0; j4 <= 19; j4++) {
                    headIcons[j4] = Client.spritesMap.get(j4 + 948);
                }

                for (int j45 = 0; j45 < 7; j45++) {
                    skullIcons[j45] = new Sprite(mediaArchive, "headicons_pk", j45);
                }
            } catch (Exception _ex) {
            }

            mapFlag = spritesMap.get(5676);
            mapMarker = spritesMap.get(5677);

            for (int k4 = 0; k4 < 8; k4++) {
                crosses[k4] = new Sprite(mediaArchive, "cross", k4);
            }

            mapDotItem = new Sprite(mediaArchive, "mapdots", 0);
            mapDotNPC = new Sprite(mediaArchive, "mapdots", 1);
            mapDotPlayer = new Sprite(mediaArchive, "mapdots", 2);
            mapDotFriend = new Sprite(mediaArchive, "mapdots", 3);
            mapDotTeam = new Sprite(mediaArchive, "mapdots", 4);
            alertBack = new Sprite("alertback");
            alertBorder = new Sprite("alertborder");
            alertBorderH = new Sprite("alertborderh");
            customMinimapIcons.add(new CustomMinimapIcon(2378, 4014, new Sprite(mediaArchive, "mapfunction", 12)));
            customMinimapIcons.add(new CustomMinimapIcon(2378, 4028, new Sprite(mediaArchive, "mapfunction", 12)));
            customMinimapIcons.add(new CustomMinimapIcon(3663, 2981, new Sprite(mediaArchive, "mapfunction", 5)));
            customMinimapIcons.add(new CustomMinimapIcon(3674, 2970, new Sprite(mediaArchive, "mapfunction", 51)));
            customMinimapIcons.add(new CustomMinimapIcon(3676, 2989, new Sprite(mediaArchive, "mapfunction", 54)));
            customMinimapIcons.add(new CustomMinimapIcon(2574, 3880, new Sprite(mediaArchive, "mapfunction", 5)));
            customMinimapIcons.add(new CustomMinimapIcon(2550, 3858, new Sprite(mediaArchive, "mapfunction", 5)));
            customMinimapIcons.add(new CustomMinimapIcon(2557, 3886, new Sprite(mediaArchive, "mapfunction", 34)));

            //SHLIME HERE - CORDS - SPRITE ID
           customMinimapIcons.add(new CustomMinimapIcon(3167, 3524, Client.spritesMap.get((3416))));



            //


            mapDotClan = spritesMap.get(398);
            vengBar = spritesMap.get(297);

            for (int j3 = 0; j3 < 12; j3++) {
                scrollPart[j3] = new Sprite(mediaArchive, "scrollpart", j3);
            }

            for (int id = 0; id < 6; id++) {
                scrollBar[id] = new Sprite(mediaArchive, "scrollbar", id);
            }

            for (int l4 = 0; l4 < modIcons.length; l4++) {
                modIcons[l4] = spritesMap.get(827 + l4);
            }
            modIcons[15] = spritesMap.get(1509);
            modIcons[16] = spritesMap.get(1508);


            multiOverlay = spritesMap.get(1025);
            XPOverlay = spritesMap.get(1025);

            Sprite sprite = new Sprite(mediaArchive, "screenframe", 0);
            leftFrame = new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaArchive, "screenframe", 1);
            topFrame = new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaArchive, "screenframe", 2);
            rightFrame = new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight, getGameComponent());
            sprite.method346(0, 0);
            sprite = new Sprite(mediaArchive, "mapedge", 0);
            new ProducingGraphicsBuffer(sprite.myWidth, sprite.myHeight, getGameComponent());
            sprite.method346(0, 0);
            compass = new Sprite(mediaArchive, "compass", 0);
            clientId = Signlink.uid;

            //// drawSmoothLoading(50, "Unpacking textures..");
            Rasterizer3D.method368(streamLoader_3);
            Rasterizer3D.calculatePalette(0.59999999999999998D);
            Rasterizer3D.initiateRequestBuffers();
            //// drawSmoothLoading(60, "Unpacked textures!");
            AnimationDefinition.unpackConfig(streamLoader);
            //// drawSmoothLoading(70, "Unpacking config..");
            ObjectDef.unpackConfig(streamLoader);
            //Flo.unpackConfig(streamLoader);
            Flo.unpackUnderlays(streamLoader);
            Flo.unpackOverlays(streamLoader);
            OverLayFlo317.load(streamLoader);
            ItemDef.unpackConfig(streamLoader);
            EntityDef.load(streamLoader);
            IdentityKit.unpackConfig(streamLoader);
            AnimatedGraphic.load(streamLoader);
            Varp.load(streamLoader);
            VarBit.unpackConfig(streamLoader);
            Model.initEncrypted();
            Model.preloadCustomEncoded();
            ItemDef.isMembers = isMembers;
            //// drawSmoothLoading(80, "Unpacked config!");
            // ItemDefinition.dumpItemModelsForId(13653);
            // onDemandFetcher.dump();
            //repackCacheIndex(1);
            //renameIndices(6);
            for (int i : Configuration.REPACK_INDICIES) {
                //repackCacheIndex(i);
            }

            //// drawSmoothLoading(95, "Unpacking interfaces");
            TextDrawingArea[] aclass30_sub2_sub1_sub4s = {smallText, normalText, boldText, aTextDrawingArea_1273};

            try {
                RSFontSystem[] newFonts = {newSmallFont, newRegularFont, newBoldFont};
                RSInterface.unpack(streamLoader_1, aclass30_sub2_sub1_sub4s, mediaArchive, newFonts);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //// drawSmoothLoading(100, "Unpacked interfaces!");

            try {
                for (int j6 = 0; j6 < 33; j6++) {
                    int k6 = 999;
                    int i7 = 0;

                    for (int k7 = 0; k7 < 34; k7++) {
                        if (mapBack.imgPixels[k7 + j6 * mapBack.imgWidth] == 0) {
                            if (k6 == 999) {
                                k6 = k7;
                            }

                            continue;
                        }

                        if (k6 == 999) {
                            continue;
                        }

                        i7 = k7;
                        break;
                    }

                    compassArray1[j6] = k6;
                    compassArray2[j6] = i7 - k6;
                }

                for (int l6 = 5; l6 < 156; l6++) {
                    int j7 = 999;
                    int l7 = 0;

                    for (int j8 = 20; j8 < 172; j8++) {
                        if (mapBack.imgPixels[j8 + l6 * mapBack.imgWidth] == 0 && (j8 > 34 || l6 > 34)) {
                            if (j7 == 999) {
                                j7 = j8;
                            }

                            continue;
                        }

                        if (j7 == 999) {
                            continue;
                        }

                        l7 = j8;
                        break;
                    }

                    mapImagePixelCutLeft[l6 - 5] = j7 - 20;
                    mapImagePixelCutRight[l6 - 5] = l7 - j7;

                    if (mapImagePixelCutRight[l6 - 5] == -20) {
                        mapImagePixelCutRight[l6 - 5] = 152;
                    }
                }
            } catch (Exception _ex) {
            }
            ItemStats.readDefinitions();
            updateGameArea();
            GameObject.clientInstance = this;
            ObjectDef.clientInstance = this;
            EntityDef.clientInstance = this;
            Load.settings(Client.getClient());
            try {
                serial = CreateUID.generateUID();
            } catch (Exception e) {
            }
            try {
                hash = ClassCheck.generate();
            } catch (Throwable e1) {
                e1.printStackTrace();
            }

            // loadPlayerData();
            isLoading = false;
            if (Configuration.NEW_CURSORS) {
                super.setCursor(CursorData.CURSOR_0);
            }
            return;
        } catch (Exception exception) {
            isLoading = false;
            exception.printStackTrace();
            Signlink.reportError("loaderror " + anInt1079);
        }

        loadingError = true;
    }

    public void dumpArchive(int archive) {
        int[] ids = new int[]{};
        for (int i : ids) {
            try {
                byte[] abyte = decompressors[archive].read(onDemandFetcher.getModelIndex(i));
                File map = new File(Signlink.getCacheDirectory() + "/itemdata/" + onDemandFetcher.getModelIndex(i) + ".gz");
                FileOutputStream fos = new FileOutputStream(map);
                fos.write(abyte);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopMidi() {
        // Signlink.midifade = 0;
        // Signlink.midi = "stop";
    }

    private void tabToReplyPm() {
        String name = null;

        for (int k = 0; k < 100; k++) {
            if (chatMessages[k] == null) {
                continue;
            }

            int l = chatTypes[k];

            if (l == 3 || l == 7) {
                name = chatNames[k];
                break;
            }
        }

        if (name == null) {
            pushMessage("You haven't received any messages to which you can reply.", 0, "");
            return;
        }

        if (name.indexOf("@") == 0) {
            int prefixSubstring = getPrefixSubstringLength(name);
            name = name.substring(prefixSubstring);
        }
        if (name.indexOf("@") == 0) {
            int prefixSubstring = getPrefixSubstringLength(name);
            name = name.substring(prefixSubstring);
        }

        long nameAsLong = TextUtilities.longForName(name.trim());

        if (nameAsLong != -1) {
            updateChatArea = true;
            inputDialogState = 0;
            messagePromptRaised = true;
            promptInput = "";
            friendsListAction = 3;
            aLong953 = nameAsLong;
            promptMessage = "Enter message to send to " + TextUtilities.fixName(TextUtilities.nameForLong(nameAsLong));
        }
    }

    public void updateScreen() {
        gameAreaWidth = GameFrame.isFixed() ? 512 : getScreenWidth();
        gameAreaHeight = GameFrame.isFixed() ? 334 : getScreenHeight();
        if (gameScreenIP == null || gameScreenIP.width != gameAreaWidth || gameScreenIP.height != gameAreaHeight) {
            gameScreenIP = new ProducingGraphicsBuffer(gameAreaWidth, gameAreaHeight, getGameComponent());
        }
        updateGameArea();
        updateGraphics(false);
    }

    public void setResizing(boolean resizing) {
        if (resizing) {
            this.resizing = true;
            (loggedIn ? gameScreenIP : titleScreenIP).initDrawingArea();
        } else {
            this.resizing = false;
            (loggedIn ? gameScreenIP : titleScreenIP).drawGraphics(canvas.getGraphics(), GameFrame.isFixed() ? 4 : gameScreenDrawX, gameScreenDrawY);
        }
    }

    public void toggleSize(ScreenMode mode) {
        if (isApplet) {
            GameFrame.setScreenMode(mode);
            if (mode == ScreenMode.FIXED) {
                sendFrame36(652, 0);
                gameScreenDrawX = 4;
                gameScreenDrawY = 4;
            } /*else if (mode == ScreenMode.RESIZABLE_CLASSIC) {
                sendFrame36(652, 2);
                gameScreenDrawX = 0;
                gameScreenDrawY = 0;
            }*/ else {
                sendFrame36(652, 1);
                gameScreenDrawX = 0;
                gameScreenDrawY = 0;
            }
            return;
        }
        if (GameFrame.getScreenMode() != mode) {
            setResizing(true);
            super.setClickMode2(0);
            if (mode == ScreenMode.FIXED) {
                GameFrame.setScreenMode(mode);
                gameScreenDrawX = 4;
                gameScreenDrawY = 4;
                forceWidth = REGULAR_WIDTH;
                forceHeight = REGULAR_HEIGHT;
                clientWidth = REGULAR_WIDTH;
                clientHeight = REGULAR_HEIGHT;
                sendFrame36(652, 0);
                recreateClientFrame(false, 765, 503, false, 1, false);
                welcomeScreenRaised = true;
                cameraZoom = 0;
            } else if (mode == ScreenMode.RESIZABLE_CLASSIC) {
                GameFrame.setScreenMode(mode);
                gameScreenDrawX = 0;
                gameScreenDrawY = 0;
                forceWidth = forceHeight = -1;
                sendFrame36(652, 2);
                recreateClientFrame(false, RESIZABLE_WIDTH, RESIZABLE_HEIGHT, true, 0, true);
                cameraZoom = 580;
            } else {
                GameFrame.setScreenMode(mode);
                gameScreenDrawX = 0;
                gameScreenDrawY = 0;
                forceWidth = forceHeight = -1;
                sendFrame36(652, 1);
                recreateClientFrame(false, RESIZABLE_WIDTH, RESIZABLE_HEIGHT, true, 0, true);
                cameraZoom = 580;
            }
            if (mode == ScreenMode.FIXED) {
                updateChatArea = true;
            }
            if (!loggedIn) {
                resetImageProducers();
            } else {
                resetImageProducers2();
            }
            chatArea.setHideComponent(false);
            tabArea.setHideComponent(false);
            updateScreen();


            setResizing(false);
        }
    }

    //
    private void unlickCaches() {
        ObjectDef.baseModels.clear();
        ObjectDef.mruNodes2.clear();
        EntityDef.mruNodes.clear();
        ItemDef.mruNodes2.clear();
        ItemDef.mruNodes1.clear();
        Player.mruNodes.clear();
        AnimatedGraphic.modelCache.clear();
        AnimationSkeleton.mapRS2.clear();
        ObjectDef.map.clear();
        ItemDef.map.clear();
        EntityDef.map.clear();
        spritesMap.clear();
    }

    public void updateConfig(int configId) {
        try {
            int j = 0;
            if (configId < Varp.getCache().length) {
                j = Varp.getCache()[configId].getAnInt709();
            }

            if (j == 0) {
                return;
            }

            int k = variousSettings[configId];

            if (j == 1) {
                if (k == 1) {
                    Rasterizer3D.calculatePalette(0.90000000000000002D);
                } else if (k == 2) {
                    Rasterizer3D.calculatePalette(0.80000000000000004D);
                } else if (k == 3) {
                    Rasterizer3D.calculatePalette(0.69999999999999996D);
                } else if (k == 4) {
                    Rasterizer3D.calculatePalette(0.59999999999999998D);
                }

                ItemDef.mruNodes1.clear();
                ItemDef.spriteCacheEffectTimers.clear();
                welcomeScreenRaised = true;
            } else if (j == 3) {
                int volume = 0;

                if (k == 0) {
                    volume = 255;
                } else if (k == 1) {
                    volume = 192;
                } else if (k == 2) {
                    volume = 128;
                } else if (k == 3) {
                    volume = 64;
                } else if (k == 4) {
                    volume = 0;
                }

                if (volume != musicVolume) {
                    if (musicVolume != 0 || currentSong == -1) {
                        if (volume != 0) {
                            setVolume(volume);
                        } else {
                            method55(false);
                            prevSong = 0;
                        }
                    } else {
                        method56(volume, false, currentSong);
                        prevSong = 0;
                    }

                    musicVolume = volume;
                }
            } else if (j == 4) {
                if (k == 0) {
                    soundEffectVolume = 127;
                } else if (k == 1) {
                    soundEffectVolume = 96;
                } else if (k == 2) {
                    soundEffectVolume = 64;
                } else if (k == 3) {
                    soundEffectVolume = 32;
                } else if (k == 4) {
                    soundEffectVolume = 64;
                }
            } else if (j == 5) {
                anInt1253 = k;
            } else if (j == 6) {
                anInt1249 = k;
            } else if (j == 7) {
                running = !running;
                final boolean run = running;
                mapArea.run.setOrbState(run);
            } else if (j == 8) {
                splitPrivateChat = k;
                updateChatArea = true;
            } else if (j == 9) {
                anInt913 = k;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getSizeForHealth(int health) {
        if (health > 10000) {
            return 50;
        }
        if (health > 7500) {
            return 50;
        }
        if (health > 5000) {
            return 50;
        }
        if (health > 2500) {
            return 50;
        }
        if (health > 2000) {
            return 50;
        }
        if (health > 1500) {
            return 50;
        }
        if (health > 1000) {
            return 50;
        }
        return 30;
    }

    private void updateEntities() {
        try {
            int anInt974 = 0;
            for (int j = -1; j < playerCount + npcCount; j++) {
                Object obj;
                if (j == -1) {
                    obj = myPlayer;
                } else if (j < playerCount) {
                    obj = playerArray[playerIndices[j]];
                } else {
                    obj = npcArray[npcIndices[j - playerCount]];
                }
                if (obj == null || !((Actor) obj).isVisible()) {
                    continue;
                }
                if (obj instanceof Npc) {
                    EntityDef entityDef = ((Npc) obj).definitionOverride;
                    npcScreenPos((Actor) obj, ((Actor) obj).height + 15);
                    if (spriteDrawX > -1) {
                        /*// draw sprites on npc
                        if (entityDef.id == 6537) {
                            spritesMap.get(607).drawSprite(spriteDrawX - 12, spriteDrawY - 30);
                        }
                        if (entityDef.id == 8000 || entityDef.id == 8002) {
                            spritesMap.get(134).drawSprite(spriteDrawX - 12, spriteDrawY - 30);
                        }
                        if (entityDef.id == 13738) {
                            spritesMap.get(519).drawSprite(spriteDrawX - 12, spriteDrawY - 30);

                        }
                        if (entityDef.id == 1086) {
                            spritesMap.get(638).drawSprite(spriteDrawX - 12, spriteDrawY - 40);

                        }
                        if (entityDef.id == 1085) {
                            spritesMap.get(641).drawSprite(spriteDrawX - 12, spriteDrawY - 40);

                        }
                        if (entityDef.id == 1084) {
                            spritesMap.get(625).drawSprite(spriteDrawX - 12, spriteDrawY - 40);

                        }
                        if (entityDef.id == 3777) {
                            spritesMap.get(857).drawSprite(spriteDrawX - 4, spriteDrawY - 16);

                        }
                        if (entityDef.id == 585) {
                            spritesMap.get(915).drawSprite(spriteDrawX - 4, spriteDrawY - 32);

                        }
                        if (entityDef.id == 688) {
                            spritesMap.get(916).drawSprite(spriteDrawX - 4, spriteDrawY - 32);

                        }
                        if (entityDef.id == 125) {
                            spritesMap.get(917).drawSprite(spriteDrawX - 4, spriteDrawY - 32);

                        }
                        if (entityDef.id == 1821) {
                            spritesMap.get(869).drawSprite(spriteDrawX - 4, spriteDrawY - 5);

                        }
                        if (entityDef.id == 925 || entityDef.id == 1988) {
                            spritesMap.get(853).drawSprite(spriteDrawX - 4, spriteDrawY - 16);

                        }
                        if (entityDef.id == 198) {
                            spritesMap.get(856).drawSprite(spriteDrawX - 4, spriteDrawY - 16);

                        }
                        if (entityDef.id == 3306) {
                            spritesMap.get(876).drawSprite(spriteDrawX - 4, spriteDrawY - 16);

                        }
                        if (entityDef.id == 4651) {
                            spritesMap.get(868).drawSprite(spriteDrawX - 4, spriteDrawY - 16);

                        }
                        if (entityDef.id == 6692) {
                            spritesMap.get(364).drawSprite(spriteDrawX - 12, spriteDrawY - 30);

                        }
                        if (entityDef.id == 3112) {
                            spritesMap.get(1229).drawSprite(spriteDrawX - 12, spriteDrawY - 30);

                        }

                        if (entityDef.id == 605) {
                            spritesMap.get(866).drawSprite(spriteDrawX - 4, spriteDrawY - 16);
                        }

                        if (entityDef.id == 2579) {
                            spritesMap.get(838).drawSprite(spriteDrawX - 4, spriteDrawY - 16);
                        }
                        if (entityDef.id == 547) {
                            spritesMap.get(870).drawSprite(spriteDrawX - 4, spriteDrawY - 16);
                        }*/

                        // rock tail are objects
                    }

                    if (entityDef.childrenIDs != null) {
                        entityDef = entityDef.method161();
                    }
                    if (entityDef == null) {
                        continue;
                    }
                }
                if (j < playerCount) {
                    int l = Configuration.USERNAMES_ABOVE_HEAD ? 42 : 32;

                    if (Configuration.USERNAMES_ABOVE_HEAD) {
                        l += 10;
                    }
                    Player player = (Player) obj;
                    if (player.headIcon >= 0) {
                        npcScreenPos((Actor) obj, ((Actor) obj).height + 15);
                        if (player == null || !player.isVisible() || (!Configuration.RENDER_PLAYERS && player != myPlayer) || !Configuration.RENDER_SELF && player == myPlayer) {
                            continue;
                        }
                        if (spriteDrawX > -1) {
                            if (player.skulled) {
                                l += 2;
                                skullIcons[0].drawSprite(spriteDrawX - 12, spriteDrawY - l);
                                l += 19;
                            }
                            if (player.bountyHunterIcon >= 0 && player.bountyHunterIcon <= 4) {
                                spritesMap.get(1026 + player.bountyHunterIcon)
                                        .drawSprite(spriteDrawX - (player.skulled ? 8 : 10), spriteDrawY - l);
                                l += 28;
                            }
                            int[] allowedIndices = {0,4,5,9,10,14,15,16, 17, 19};
                            for (int i : allowedIndices) {
                                if (player.headIcon == i) {
                                    headIcons[player.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - l);
                                    l += 29;
                                }
                            }

                            if (Configuration.USERNAMES_ABOVE_HEAD) {
                                if (player.playerRights != 0) {
                                    newSmallFont.drawCenteredString(player.name != null ? getOverheadPlayerTitle(player) + player.name : "null username", spriteDrawX + getOverheadPlayerOffset(player), spriteDrawY - 10, 0x15FF00, 0x000000);
                                } else {
                                    newSmallFont.drawCenteredString((player.name != null ? player.name : "null username"), spriteDrawX, spriteDrawY - 10, 0x15FF00, 0x000000);
                                }
                            }
                        }
                    }
                    if (j >= 0 && hintIconDrawType == 10 && hintIconPlayerId == playerIndices[j]) {
                        npcScreenPos((Actor) obj, ((Actor) obj).height + 15);
                        if (spriteDrawX > -1) {
                            headIcons[7].drawSprite(spriteDrawX - 12, spriteDrawY - l);
                        }
                    }
                } else {
                    EntityDef entityDef_1 = ((Npc) obj).definitionOverride;
                    if (entityDef_1.headIcon >= 0 && entityDef_1.headIcon < headIcons.length) {
                        npcScreenPos((Actor) obj, ((Actor) obj).height + 15);
                        if (spriteDrawX > -1) {
                            headIcons[entityDef_1.headIcon].drawSprite(spriteDrawX - 12, spriteDrawY - 30);
                        }
                    }
                    if (hintIconDrawType == 1 && hintIconNpcId == npcIndices[j - playerCount] && loopCycle % 20 < 10) {
                        npcScreenPos((Actor) obj, ((Actor) obj).height + 15);
                        if (spriteDrawX > -1) {
                            Client.spritesMap.get(1228).drawSprite(spriteDrawX - 12, spriteDrawY - 28);
                        }
                    }
                }
                if (((Actor) obj).textSpoken != null && (j >= playerCount || publicChatMode == 0 || publicChatMode == 3 || publicChatMode == 1 && isFriendOrSelf(((Player) obj).name))) {
                    npcScreenPos((Actor) obj, ((Actor) obj).height);
                    if (spriteDrawX > -1 && anInt974 < anInt975) {
                        anIntArray979[anInt974] = boldText.method384(((Actor) obj).textSpoken) / 2;
                        anIntArray978[anInt974] = boldText.anInt1497;
                        anIntArray976[anInt974] = spriteDrawX;
                        anIntArray977[anInt974] = spriteDrawY;
                        anIntArray980[anInt974] = ((Actor) obj).anInt1513;
                        anIntArray981[anInt974] = ((Actor) obj).anInt1531;
                        anIntArray982[anInt974] = ((Actor) obj).textCycle;
                        aStringArray983[anInt974++] = ((Actor) obj).textSpoken;
                        if (anInt1249 == 0 && ((Actor) obj).anInt1531 >= 1 && ((Actor) obj).anInt1531 <= 3) {
                            anIntArray978[anInt974] += 10;
                            anIntArray977[anInt974] += 5;
                        }
                        if (anInt1249 == 0 && ((Actor) obj).anInt1531 == 4) {
                            anIntArray979[anInt974] = 60;
                        }
                        if (anInt1249 == 0 && ((Actor) obj).anInt1531 == 5) {
                            anIntArray978[anInt974] += 5;
                        }
                    }
                }
                if (((Actor) obj).loopCycleStatus > loopCycle) {

                    try {
                        npcScreenPos((Actor) obj, ((Actor) obj).height + 15);
                        if (spriteDrawX > -1) {
                            if (Configuration.HP_BAR) {
                                int currentHP = ((Actor) (obj)).currentHealth;
                                int maxHP = ((Actor) (obj)).maxHealth;
                                int percentOfHpLeft = (int) (((double) currentHP / (double) maxHP) * getSizeForHealth(maxHP));
                                if (currentHP <= 1)
                                    percentOfHpLeft = 0;
                                Raster.drawPixels(7, spriteDrawY - 3, spriteDrawX - (getSizeForHealth(maxHP) / 2), HIT_POINT_COLORS[healthpoints_bar_color][1], getSizeForHealth(maxHP));
                                Raster.fillPixels(spriteDrawX - (getSizeForHealth(maxHP) / 2), getSizeForHealth(maxHP), 7, HIT_POINT_COLORS[healthpoints_bar_color][1], spriteDrawY - 3);
                                if (currentHP > 0) {
                                    Raster.drawPixels(7, spriteDrawY - 3, spriteDrawX - (getSizeForHealth(maxHP) / 2), HIT_POINT_COLORS[healthpoints_bar_color][0], Math.min(percentOfHpLeft, getSizeForHealth(maxHP)));
                                    Raster.fillPixels(spriteDrawX - (getSizeForHealth(maxHP) / 2), Math.min(percentOfHpLeft, getSizeForHealth(maxHP)), 7, HIT_POINT_COLORS[healthpoints_bar_color][0], spriteDrawY - 3);
                                }
                                //smallText.drawCenteredText(ColorConstants.VANGUARD_PURPLE, spriteDrawX, percentOfHpLeft + "%", spriteDrawY + 5, true);
                            }
                        }
                    } catch (Exception e) {
                    }
                }

                /*
                 * Hitmarks
                 */
                for (int j1 = 0; j1 < 4; j1++) {
                    if (((Actor) obj).hitsLoopCycle[j1] > loopCycle) {
                        Actor actor = (Actor) obj;
                        npcScreenPos(actor, actor.height / 2);
                        if (actor.moveTimer[j1] == 0) {
                            if (actor.hitmarkMove[j1] > -14) {
                                actor.hitmarkMove[j1]--;
                            }
                            actor.moveTimer[j1] = 2;
                        } else {
                            actor.moveTimer[j1]--;
                        }
                        if (actor.hitmarkMove[j1] <= -14) {
                            actor.hitmarkTrans[j1] -= 10;
                        }
                        hitmarkDrawNew(actor, String.valueOf(actor.hitArray[j1]).length(), actor.hitMarkTypes[j1],
                                actor.hitIcon[j1], actor.hitArray[j1], actor.soakDamage[j1], actor.hitmarkMove[j1],
                                actor.hitmarkTrans[j1], j1);
                    }
                }
            }

            for (int k = 0; k < anInt974; k++) {
                int k1 = anIntArray976[k];
                int l1 = anIntArray977[k];
                int j2 = anIntArray979[k];
                int k2 = anIntArray978[k];
                boolean flag = true;

                while (flag) {
                    flag = false;

                    for (int l2 = 0; l2 < k; l2++) {
                        if (l1 + 2 > anIntArray977[l2] - anIntArray978[l2] && l1 - k2 < anIntArray977[l2] + 2 && k1 - j2 < anIntArray976[l2] + anIntArray979[l2] && k1 + j2 > anIntArray976[l2] - anIntArray979[l2] && anIntArray977[l2] - anIntArray978[l2] < l1) {
                            l1 = anIntArray977[l2] - anIntArray978[l2];
                            flag = true;
                        }
                    }
                }

                spriteDrawX = anIntArray976[k];
                spriteDrawY = anIntArray977[k] = l1;
                String s = aStringArray983[k];

                if (anInt1249 == 0) {
                    int i3 = 0xffff00;

                    if (anIntArray980[k] < 6) {
                        i3 = anIntArray965[anIntArray980[k]];
                    }

                    if (anIntArray980[k] == 6) {
                        i3 = renderCycle % 20 >= 10 ? 0xffff00 : 0xff0000;
                    }

                    if (anIntArray980[k] == 7) {
                        i3 = renderCycle % 20 >= 10 ? 65535 : 255;
                    }

                    if (anIntArray980[k] == 8) {
                        i3 = renderCycle % 20 >= 10 ? 0x80ff80 : 45056;
                    }

                    if (anIntArray980[k] == 9) {
                        int j3 = 150 - anIntArray982[k];

                        if (j3 < 50) {
                            i3 = 0xff0000 + 1280 * j3;
                        } else if (j3 < 100) {
                            i3 = 0xffff00 - 0x50000 * (j3 - 50);
                        } else if (j3 < 150) {
                            i3 = 65280 + 5 * (j3 - 100);
                        }
                    }

                    if (anIntArray980[k] == 10) {
                        int k3 = 150 - anIntArray982[k];

                        if (k3 < 50) {
                            i3 = 0xff0000 + 5 * k3;
                        } else if (k3 < 100) {
                            i3 = 0xff00ff - 0x50000 * (k3 - 50);
                        } else if (k3 < 150) {
                            i3 = 255 + 0x50000 * (k3 - 100) - 5 * (k3 - 100);
                        }
                    }

                    if (anIntArray980[k] == 11) {
                        int l3 = 150 - anIntArray982[k];

                        if (l3 < 50) {
                            i3 = 0xffffff - 0x50005 * l3;
                        } else if (l3 < 100) {
                            i3 = 65280 + 0x50005 * (l3 - 50);
                        } else if (l3 < 150) {
                            i3 = 0xffffff - 0x50000 * (l3 - 100);
                        }
                    }

                    if (anIntArray981[k] == 0) {
                        boldText.drawText(0, s, spriteDrawY + 1, spriteDrawX + 1);
                        boldText.drawText(i3, s, spriteDrawY, spriteDrawX);
                    }

                    if (anIntArray981[k] == 1) {
                        boldText.method386(0, s, spriteDrawX + 1, renderCycle, spriteDrawY + 1);
                        boldText.method386(i3, s, spriteDrawX, renderCycle, spriteDrawY);
                    }

                    if (anIntArray981[k] == 2) {
                        boldText.method387(spriteDrawX + 1, s, renderCycle, spriteDrawY + 1, 0);
                        boldText.method387(spriteDrawX, s, renderCycle, spriteDrawY, i3);
                    }

                    if (anIntArray981[k] == 3) {
                        boldText.method388(150 - anIntArray982[k], s, renderCycle, spriteDrawY + 1, spriteDrawX + 1, 0);
                        boldText.method388(150 - anIntArray982[k], s, renderCycle, spriteDrawY, spriteDrawX, i3);
                    }

                    if (anIntArray981[k] == 4) {
                        int i4 = boldText.method384(s);
                        int k4 = (150 - anIntArray982[k]) * (i4 + 100) / 150;
                        TextDrawingArea.setBounds(spriteDrawX - 50, 0, spriteDrawX + 50, 334);
                        boldText.method385(0, s, spriteDrawY + 1, spriteDrawX + 51 - k4);
                        boldText.method385(i3, s, spriteDrawY, spriteDrawX + 50 - k4);
                        TextDrawingArea.defaultDrawingAreaSize();
                    }

                    if (anIntArray981[k] == 5) {
                        int j4 = 150 - anIntArray982[k];
                        int l4 = 0;

                        if (j4 < 25) {
                            l4 = j4 - 25;
                        } else if (j4 > 125) {
                            l4 = j4 - 125;
                        }

                        TextDrawingArea.setBounds(0, spriteDrawY - boldText.anInt1497 - 1, 512, spriteDrawY + 5);
                        boldText.drawText(0, s, spriteDrawY + 1 + l4, spriteDrawX + 1);
                        boldText.drawText(i3, s, spriteDrawY + l4, spriteDrawX);
                        TextDrawingArea.defaultDrawingAreaSize();
                    }
                } else {
                    boldText.drawText(0, s, spriteDrawY + 1, spriteDrawX + 1);
                    boldText.drawText(0xffff00, s, spriteDrawY, spriteDrawX);
                }
            }
        } catch (Exception e) {
        }
    }

    public String getChatBoxTitle(Player player) {
        if (player.playerRights == 0) {
            return "@whi@ ";
        }
        if (player.playerRights == 1) {
            return "<col=AF70C3>Mod ";
        }
        if (player.playerRights == 2) {
            return "<col=AF70C3>Admin ";
        }
        if (player.playerRights == 3) {
            return "<col=AF70C3>Support ";
        }
        if (player.playerRights == 4) {
            return "<col=AF70C3>Owner ";
        }
        if (player.playerRights == 5) {
            return "<col=AF70C3>Manager ";
        }
        if (player.playerRights == 6) {
            return "<col=AF70C3>Manager ";
        }
        if (player.playerRights == 7) {
            return "<col=AF70C3>Co Owner ";
        }
        if (player.playerRights == 8) {
            return "<col=AF70C3>Dev ";
        }
        if (player.playerRights == 9) {
            return "<col=AF70C3>Youtuber ";
        }
        if (player.playerRights == 10) {
            return "@whi@Adept ";
        }
        if (player.playerRights == 11) {
            return "@whi@Ethereal ";
        }
        if (player.playerRights == 12) {
            return "@whi@Mythic ";
        }
        if (player.playerRights == 13) {
            return "@whi@Archon ";
        }
        if (player.playerRights == 14) {
            return "@whi@Celestial ";
        }
        if (player.playerRights == 15) {
            return "@whi@Ascendant ";
        }
        if (player.playerRights == 16) {
            return  "@whi@Gladiator";
        }
        if (player.playerRights == 17) {
            return  "@whi@Events Host ";
        }
        if (player.playerRights == 18) {
            return  "@whi@Events Admin ";
        }
        if (player.playerRights == 19) {
            return  "@whi@Cosmic ";
        }
        if (player.playerRights == 20) {
            return  "@whi@Guardian ";
        }
        if (player.playerRights == 21) {
            return  "@whi@Corrupt ";
        }

        return "";
    }

    public String getOverheadPlayerTitle(Player player) {

        if (player.playerRights == 0) {
            return "@whi@ ";
        }
        if (player.playerRights == 1) {
            return "<img=1><col=AF70C3>Mod@whi@ ";
        }
        if (player.playerRights == 2) {
            return "<img=2><col=AF70C3>Admin@whi@ ";
        }
        if (player.playerRights == 3) {
            return "<img=3><col=AF70C3>Support@whi@ ";
        }
        if (player.playerRights == 4) {
            return "<img=4><col=AF70C3>Owner@whi@ ";
        }
        if (player.playerRights == 5) {
            return "<img=5><col=AF70C3>Manager@whi@ ";
        }
        if (player.playerRights == 6) {
            return "<img=5><col=AF70C3>Manager@whi@ ";
        }
        if (player.playerRights == 7) {
            return "<img=6><col=AF70C3>Co Owner@whi@ ";
        }
        if (player.playerRights == 8) {
            return "<img=7><col=AF70C3>Developer@whi@ ";
        }
        if (player.playerRights == 9) {
            return "<img=10><col=AF70C3>Youtuber@whi@ ";
        }
        if (player.playerRights == 10) {
            return "<img=15><col=AF70C3>Adept@whi@ ";
        }
        if (player.playerRights == 11) {
            return "<img=16><col=AF70C3>Ethereal@whi@ ";
        }
        if (player.playerRights == 12) {
            return "<img=17><col=AF70C3>Mythic@whi@ ";
        }
        if (player.playerRights == 13) {
            return "<img=18><col=AF70C3>Archon@whi@ ";
        }
        if (player.playerRights == 14) {
            return "<img=19><col=AF70C3>Celestial@whi@ ";
        }
        if (player.playerRights == 15) {
            return "<img=5645><col=AF70C3>Ascendant@whi@ ";
        }
        if (player.playerRights == 16) {
            return  "<img=5646><col=AF70C3>Gladiator@whi@ ";
        }
        if (player.playerRights == 17) {
            return  "<col=AF70C3>Events Host@whi@ ";
        }
        if (player.playerRights == 18) {
            return  "<col=AF70C3>Events Admin@whi@ ";
        }
        if (player.playerRights == 19) {
            return "<img=3437><col=AF70C3>Cosmic@whi@ ";
        }
        if (player.playerRights == 20) {
            return "<img=3438><col=AF70C3>Guardian@whi@ ";
        }
        if (player.playerRights == 21) {
            return "<img=3439><col=AF70C3>Corrupt@whi@ ";
        }
        return "";
    }

    public int getOverheadPlayerOffset(Player player) {
        if (player.playerRights == 0) {
            return 0;
        }
        if (player.playerRights == 1) {
            return 20;
        }
        if (player.playerRights == 2) {
            return 20;
        }
        if (player.playerRights == 3) {
            return 240;
        }
        if (player.playerRights == 4) {
            return 250;
        }
        if (player.playerRights == 5) {
            return 110;
        }
        if (player.playerRights == 6) {
            return 110;
        }
        if (player.playerRights == 7) {
            return 5;
        }
        if (player.playerRights == 8) {
            return 90;
        }
        if (player.playerRights == 9) {
            return 10;
        }
        if (player.playerRights == 10) {
            return 15;
        }
        if (player.playerRights == 11) {
            return 20;
        }
        if (player.playerRights == 12) {
            return 40;
        }
        if (player.playerRights == 13) {
            return 85;
        }
        if (player.playerRights == 14) {
            return 13;
        }
        if (player.playerRights == 15) {
            return 0;
        }
        if (player.playerRights == 16) {
            return 0;
        }
        if (player.playerRights == 17) {
            return 10;
        }
        if (player.playerRights == 18) {
            return 10;
        }
        return 0;
    }

    public void updateGameArea() {
        try {
            Rasterizer3D.setBounds(getScreenWidth(), getScreenHeight());
            fullScreenTextureArray = Rasterizer3D.lineOffsets;
            Rasterizer3D.setBounds(GameFrame.getScreenMode() == ScreenMode.FIXED && isGameFrameVisible() ? chatAreaIP != null ? chatAreaIP.width : 519 : getScreenWidth(), GameFrame.getScreenMode() == ScreenMode.FIXED && isGameFrameVisible() ? chatAreaIP != null ? chatAreaIP.height : 165 : getScreenHeight());
            anIntArray1180 = Rasterizer3D.lineOffsets;
            Rasterizer3D.setBounds(GameFrame.getScreenMode() == ScreenMode.FIXED && isGameFrameVisible() ? tabAreaIP != null ? tabAreaIP.width : 346 : getScreenWidth(), GameFrame.getScreenMode() == ScreenMode.FIXED && isGameFrameVisible() ? tabAreaIP != null ? tabAreaIP.height : 335 : getScreenHeight());
            anIntArray1181 = Rasterizer3D.lineOffsets;
            Rasterizer3D.setBounds(gameAreaWidth, gameAreaHeight);
            anIntArray1182 = Rasterizer3D.lineOffsets;
            int[] ai = new int[9];

            for (int i8 = 0; i8 < 9; i8++) {
                int k8 = 128 + i8 * 32 + 15;
                int l8 = 600 + k8 * 3;
                int i9 = Rasterizer3D.SINE[k8];
                ai[i8] = l8 * i9 >> 16;
            }

            Scene.method310(500, 800, !loggedIn ? getScreenWidth() : gameAreaWidth, !loggedIn ? getScreenHeight() : gameAreaHeight, ai);

            if (loggedIn) {
                gameScreenIP = new ProducingGraphicsBuffer(gameAreaWidth, gameAreaHeight, getGameComponent());
            } else {
                titleScreenIP = new ProducingGraphicsBuffer(getScreenWidth(), getScreenHeight(), getGameComponent());
            }
            updateGraphics(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateNPCs(Stream buffer, int i) {
        anInt839 = 0;
        playersToUpdateCount = 0;
        updateNPCAmount(buffer);
        updateNpcMovement(i, buffer);
        readNPCUpdateMask(buffer);

        for (int k = 0; k < anInt839; k++) {
            int l = anIntArray840[k];

            if (npcArray[l].loopCycle != loopCycle) {
                npcArray[l].definitionOverride = null;
                npcArray[l] = null;
            }
        }

        if (buffer.position != i) {
            Signlink.reportError(myUsername + " size mismatch in getnpcpos - pos:" + buffer.position + " psize:" + i);
            throw new RuntimeException("eek");
        }

        for (int i1 = 0; i1 < npcCount; i1++) {
            if (npcArray[npcIndices[i1]] == null) {
                Signlink.reportError(myUsername + " null entry in npc list - pos:" + i1 + " size:" + npcCount);
                throw new RuntimeException("eek");
            }
        }
    }

    private void updatePlayers(int i, Stream buffer) {
        anInt839 = 0;
        playersToUpdateCount = 0;
        updatePlayerMovement(buffer);
        updatePlayer(buffer);
        updatePlayerMovement(buffer, i);
        processPlayerUpdating(buffer);

        for (int k = 0; k < anInt839; k++) {
            int l = anIntArray840[k];

            if (playerArray[l].loopCycle != loopCycle) {
                playerArray[l] = null;
            }
        }

        if (buffer.position != i) {
            Signlink.reportError("Error packet size mismatch in getplayer pos:" + buffer.position + " psize:" + i);
            throw new RuntimeException("eek");
        }

        for (int i1 = 0; i1 < playerCount; i1++) {
            if (playerArray[playerIndices[i1]] == null) {
                Signlink.reportError(myUsername + " null entry in pl list - pos:" + i1 + " size:" + playerCount);
                throw new RuntimeException("eek");
            }
        }

    }

    private boolean rotateTime() {
        if (System.currentTimeMillis() - rotateTimer > 60000) {
            // System.out.println("Old rotateTimer: "+rotateTimer);
            rotateTimer = System.currentTimeMillis();
            // System.out.println("New rotateTimer: "+rotateTimer);
            return true;
        }
        return false;
    }

    public void rotateCam() {
        if (rotateTime()) {
            // int left = cameraRotationLeft;
            cameraRotationLeft += 25;
            // System.out.println("O.L: "+left+", C.L: "+cameraRotationLeft);
            // System.out.println("["+System.currentTimeMillis()+"] Done rotating camera.");
        }
    }

    private void handleQuickAidsActive() {
        int toggle = -1;
        if (prayerInterfaceType == 5608) {
            if (getQuickPrayersSet() > 0) {
                if (!mapArea.prayer.getOrbState()) {
                    for (int i = 0; i < quickPrayers.length; i++) {
                        int button = i == 26 ? 25104 : i == 27 ? 25108 : (i * 2) + 25000;
                        RSInterface rsInterface = RSInterface.interfaceCache[button];
                        if (rsInterface.valueIndexArray != null && rsInterface.valueIndexArray[0][0] == 5) {
                            toggle = rsInterface.valueIndexArray[0][1];
                            if (variousSettings[toggle] == 0 && quickPrayers[i] == 1) {
                                getOut().putOpcode(185);
                                getOut().putInt(button);
                                mapArea.prayer.setOrbState(true);
                            } else if (quickPrayers[i] == 1 && variousSettings[toggle] == 1) {
                                mapArea.prayer.setOrbState(true);
                            }
                        }
                    }
                } else {
                    turnOffPrayers();
                    mapArea.prayer.setOrbState(false);
                }
            } else {
                if (mapArea.prayer.getOrbState()) {
                    turnOffPrayers();
                } else {
                    pushMessage("You don't have any quick prayers selected.", 0, "");
                    pushMessage("Right-click the Prayer button next to the minimap to select some.", 0, "");
                }
                mapArea.prayer.setOrbState(false);
            }
        } else if (prayerInterfaceType == 32500) {
            if (getQuickCursesSet() > 0) {
                if (!mapArea.prayer.getOrbState()) {
                    for (int i = 0; i < quickCurses.length; i++) {
                        int button = (i * 2) + 32500 + 3;
                        RSInterface rsInterface = RSInterface.interfaceCache[button];
                        if (rsInterface.valueIndexArray != null && rsInterface.valueIndexArray[0][0] == 5) {
                            toggle = rsInterface.valueIndexArray[0][1];
                            if (variousSettings[toggle] == 0 && quickCurses[i] == 1) {
                                getOut().putOpcode(185);
                                getOut().putInt(button);
                                mapArea.prayer.setOrbState(true);
                            }
                        } else if (quickCurses[i] == 1 && variousSettings[toggle] == 1) {
                            mapArea.prayer.setOrbState(true);
                        }
                    }
                } else {
                    turnOffCurses();
                    mapArea.prayer.setOrbState(false);
                }
            } else {
                if (mapArea.prayer.getOrbState()) {
                    turnOffCurses();
                    mapArea.prayer.setOrbState(false);
                } else {
                    pushMessage("You don't have any prayers selected.", 0, "");
                    pushMessage("Right-click the Prayer button next to the minimap to select some.", 0, "");
                }
            }
        }
    }

    private int getQuickPrayersSet() {
        int amount = 0;
        for (int i = 0; i < quickPrayers.length; i++) {
            if (quickPrayers[i] == 1) {
                amount++;
            }
        }
        return amount;
    }

    private int getQuickCursesSet() {
        int amount = 0;
        for (int i = 0; i < quickCurses.length; i++) {
            if (quickCurses[i] == 1) {
                amount++;
            }
        }
        return amount;
    }

    private int[] getCurseTypeForIndex(int index) {
        int[] types = null;
        for (int g = 0; g < protect_prayers.length; g++) {
            if (index == protect_prayers[g]) {
                types = protect_prayers;
            }
        }
        for (int g = 0; g < first_row.length; g++) {
            if (index == first_row[g]) {
                types = first_row;
            }
        }
        for (int g = 0; g < second_row.length; g++) {
            if (index == second_row[g]) {
                types = second_row;
            }
        }
        for (int g = 0; g < third_row.length; g++) {
            if (index == third_row[g]) {
                types = third_row;
            }
        }
        for (int g = 0; g < fourth_row.length; g++) {
            if (index == fourth_row[g]) {
                types = fourth_row;
            }
        }
        for (int g = 0; g < fifth_row.length; g++) {
            if (index == fifth_row[g]) {
                types = fifth_row;
            }
        }
        return types;
    }

    public void togglePrayerState(int button) {
        int index = button == 17279 ? 26 : button == 17280 ? 27 : button - 17202;
        if (prayerInterfaceType == 5608) {
            if ((maxStats[5]) >= prayerLevelRequirements[index]) {
                int[] types = getPrayerTypeForIndex(index);
                if (types != null) {
                    for (int g = 0; g < rangeAndMagePray.length; g++) {
                        if (index == rangeAndMagePray[g]) {
                            types = rangeAndMagePrayOff;
                        }
                    }
                    for (int i = 0; i < types.length; i++) {
                        if (index != types[i]) {
                            if (index == 24 || index == 25) {
                                types = superiorPray;
                            }
                            quickPrayers[types[i]] = 0;
                            variousSettings[quickConfigIDs[types[i]]] = 0;
                            updateConfig(quickConfigIDs[types[i]]);
                            if (dialogID != -1) {
                                updateChatArea = true;
                            }
                        } else {
                            quickPrayers[index] = (quickPrayers[index] + 1) % 2;
                            variousSettings[quickConfigIDs[index]] = quickPrayers[index];
                            updateConfig(quickConfigIDs[index]);
                            if (dialogID != -1) {
                                updateChatArea = true;
                            }
                        }
                    }
                } else {
                    System.out.println(index);
                    quickPrayers[index] = (quickPrayers[index] + 1) % 2;
                    variousSettings[quickConfigIDs[index]] = quickPrayers[index];
                    updateConfig(quickConfigIDs[index]);
                    if (dialogID != -1) {
                        updateChatArea = true;
                    }
                }
            } else {
                pushMessage("You need a Prayer level of atleast " + prayerLevelRequirements[index] + " to use " + prayerName[index] + ".", 0, "");
            }
        } else if (prayerInterfaceType == 17234) {
            if ((maxStats[5]) >= curseLevelRequirements[index]) {
                int[] types = getCurseTypeForIndex(index);
                if (types != null) {
                    for (int i = 0; i < types.length; i++) {
                        if (index != types[i]) {
                            quickCurses[types[i]] = 0;
                            variousSettings[quickConfigIDs[types[i]]] = 0;
                            updateConfig(quickConfigIDs[types[i]]);
                            if (dialogID != -1) {
                                updateChatArea = true;
                            }
                        }
                    }
                }
                System.out.println(index);
                quickCurses[index] = (quickCurses[index] + 1) % 2;
                variousSettings[quickConfigIDs[index]] = quickCurses[index];
                updateConfig(quickConfigIDs[index]);
                if (dialogID != -1) {
                    updateChatArea = true;
                }
            } else {
                pushMessage("You need a Prayer level of atleast " + curseLevelRequirements[index] + " to use " + curseName[index] + ".", 0, "");
            }
        }
    }

    private void turnOffPrayers() {
        int toggle = -1;
        for (int i = 0; i < quickPrayers.length; i++) {
            int x = i == 26 ? 25104 : i == 27 ? 25108 : (i * 2) + 25000;
            RSInterface rsInterface = RSInterface.interfaceCache[x];
            if (rsInterface.valueIndexArray != null && rsInterface.valueIndexArray[0][0] == 5) {
                toggle = rsInterface.valueIndexArray[0][1];
                if (variousSettings[toggle] == 1 && quickPrayers[i] == 1) {
                    getOut().putOpcode(185);
                    getOut().putInt(x);
                }
            }
        }
    }

    private void turnOffCurses() {
        int toggle = -1;
        for (int i = 0; i < quickCurses.length; i++) {
            RSInterface rsInterface = RSInterface.interfaceCache[(i * 2) + 32503];
            if (rsInterface.valueIndexArray != null && rsInterface.valueIndexArray[0][0] == 5) {
                toggle = rsInterface.valueIndexArray[0][1];
                if (variousSettings[toggle] == 1 && quickCurses[i] == 1) {
                    getOut().putOpcode(185);
                    getOut().putInt((i * 2) + 32503);
                }
            }
        }
    }

    private int[] getPrayerTypeForIndex(int index) {
        int[] type = null;
        for (int i = 0; i < prayer.length; i++) {
            for (int il = 0; il < prayer[i].length; il++) {
                if (index == prayer[i][il]) {
                    type = prayer[i];
                }
            }
        }
        return type;
    }

    private void saveQuickSelection() {
        Save.settings(Client.getClient());
        tabInterfaceIDs[5] = 32500;
    }

    public void toggleQuickAidsSelection() {
        boolean inProcess = (tabInterfaceIDs[5] == 17200 || tabInterfaceIDs[5] == 17234);
        if (inProcess) {
            saveQuickSelection();
        } else {
            if (prayerInterfaceType == 5608) {
                tabInterfaceIDs[5] = 17200;
                tabAreaAltered = true;
            } else if (prayerInterfaceType == 32500) {
                tabInterfaceIDs[5] = 17234;
                tabAreaAltered = true;
            }
            tabID = 5;
        }
    }

    private void saveGoals(String name) {
        try {
            File file = new File(Signlink.getCacheDirectory() + "/" + name.trim()
                    .toLowerCase() + ".goals");
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            for (int index = 0; index < Skills.goalData.length; index++) {
                out.writeInt(Skills.goalData[index][0]);
                out.writeInt(Skills.goalData[index][1]);
                out.writeInt(Skills.goalData[index][2]);
                out.writeUTF(Skills.goalType);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStrings(String str, int i) {
        switch (i) {
            case 1675:
                setInterfaceText(str, 17508);
                break;// Stab

            case 1676:
                setInterfaceText(str, 17509);
                break;// Slash

            case 1677:
                setInterfaceText(str, 17510);
                break;// Crush

            case 1678:
                setInterfaceText(str, 17511);
                break;// Magic

            case 1679:
                setInterfaceText(str, 17512);
                break;// Range

            case 1680:
                setInterfaceText(str, 17513);
                break;// Stab

            case 1681:
                setInterfaceText(str, 17514);
                break;// Slash

            case 1682:
                setInterfaceText(str, 17515);
                break;// Crush

            case 1683:
                setInterfaceText(str, 17516);
                break;// Magic

            case 1684:
                setInterfaceText(str, 17517);
                break;// Range

            case 1686:
                setInterfaceText(str, 17518);
                break;// Strength

            case 15120:
                RSInterface.addText(i, str, RSInterface.fonts, 1, 0xFF9200, false, true);// 19153
                break;// magic damage
        }
    }

    private static final DecimalFormat df;

    static {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setGroupingSeparator(',');
        df = new DecimalFormat("", dfs);
    }
    public void showCombatBox() {
        if (openInterfaceID > 0) {
            return;
        }
        int currentHp = currentTarget.currentHealth;
        int maxHp = currentTarget.maxHealth;
        boolean rounded = false;
        int height = 35;
        int width = 106;
        int xPos = 10;
        int yPos = 30;
        int hpBarXPps = (xPos + 3);
        if (currentTarget instanceof Player) {
            Player player = (Player) currentTarget;
        } else {
            Npc npc = (Npc) currentTarget;
            if (npc.definitionOverride == null) {
                currentTarget = null;
                return;
            }
            int npcId = npc.definitionOverride.id;

            if (HealthBars.isAdavancedNPC(npcId)) {
                HealthBars.drawAdvancedProgressBar(currentHp, maxHp, npcId, npc.definitionOverride.name);
                yPos += 10;
                xPos += 5;
                for (int i = 0; i < 5; i++) {
                    newSmallFont.drawBasicString("", xPos, yPos + getYPosAddition(i), false);
                    newSmallFont.drawBasicString("", xPos + 90, yPos + getYPosAddition(i), false);
                    if (i >= npc.damageDealers.size()) {
                        continue;
                    }
                    String p = npc.damageDealers.get(i).getPlayer();
                    int damage = npc.damageDealers.get(i).getDamage();
                    if (!Configuration.CONSTITUTION_ENABLED && damage > 0) {
                        damage /= 10;
                    }
                    String str = df.format(damage);
                    newSmallFont.drawBasicString(p, xPos, yPos + getYPosAddition(i), false);
                    newSmallFont.drawBasicString(str, xPos + 90, yPos + getYPosAddition(i), false);
                }
            }
        }
    }
    public int getYPosAddition(int index) {
        return (index * 10) + 10;
    }

    public Socket getConnection() {
        return socket;
    }

    public void setConnection(Socket socket) {
        this.socket = socket;
    }

    public int getLoginState() {
        return loginState;
    }

    public void setLoginState(int loginState) {
        this.loginState = loginState;
    }

    public int getLoginScreenState() {
        return loginScreenState;
    }

    public void setLoginScreenState(int loginScreenState) {
        this.loginScreenState = loginScreenState;
    }

    public int getLoginScreenCursorPos() {
        return loginScreenCursorPos;
    }

    public void setLoginScreenCursorPos(int loginScreenCursorPos) {
        this.loginScreenCursorPos = loginScreenCursorPos;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Stream getInputBuffer() {
        return inputBuffer;
    }

    public void setInputBuffer(Stream inputBuffer) {
        this.inputBuffer = inputBuffer;
    }

    public long getServerSeed() {
        return serverSeed;
    }

    public void setServerSeed(long serverSeed) {
        this.serverSeed = serverSeed;
    }

    public Stream getLoginBuffer() {
        return loginBuffer;
    }

    public void setLoginBuffer(Stream loginBuffer) {
        this.loginBuffer = loginBuffer;
    }

    public ISAACRandomGen getConnectionCipher() {
        return connectionCipher;
    }

    public void setConnectionCipher(ISAACRandomGen connectionCipher) {
        this.connectionCipher = connectionCipher;
    }

    public int getLoginFailures() {
        return loginFailures;
    }

    public void setLoginFailures(int loginFailures) {
        this.loginFailures = loginFailures;
    }

    public int getWalkableInterfaceId() {
        return walkableInterfaceId;
    }

    public void setWalkableInterfaceId(int anInt1018) {
        this.walkableInterfaceId = anInt1018;
    }

    public int getAnInt900() {
        return anInt900;
    }

    public void setAnInt900(int anInt900) {
        this.anInt900 = anInt900;
    }

    public int getFullscreenInterfaceID() {
        return fullscreenInterfaceID;
    }

    public void setFullscreenInterfaceID(int fullscreenInterfaceID) {
        this.fullscreenInterfaceID = fullscreenInterfaceID;
    }

    public NodeList getaClass19_1179() {
        return aClass19_1179;
    }

    public void setaClass19_1179(NodeList aClass19_1179) {
        this.aClass19_1179 = aClass19_1179;
    }

    public int getLastKnownPlane() {
        return lastKnownPlane;
    }

    public void setLastKnownPlane(int anInt985) {
        this.lastKnownPlane = anInt985;
    }

    public int getMyPlayerIndex() {
        return myPlayerIndex;
    }

    public NodeList getProjectiles() {
        return aClass19_1013;
    }

    public void setaClass19_1013(NodeList aClass19_1013) {
        this.aClass19_1013 = aClass19_1013;
    }

    public Stream[] getaStreamArray895s() {
        return aStreamArray895s;
    }

    public void setaStreamArray895s(Stream[] aStreamArray895s) {
        this.aStreamArray895s = aStreamArray895s;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public NodeList getIncompleteAnimables() {
        return aClass19_1056;
    }

    public void setaClass19_1056(NodeList aClass19_1056) {
        this.aClass19_1056 = aClass19_1056;
    }

    public RS2MapLoginRenderer getScriptManager() {
        return RS2MapLoginRenderer;
    }

    public void setScriptManager(RS2MapLoginRenderer RS2MapLoginRenderer) {
        this.RS2MapLoginRenderer = RS2MapLoginRenderer;
    }

    public GrandExchange getGrandExchange() {
        return grandExchange;
    }
    public void updateBingoInterface(int[][] itemIds, boolean[][] marked) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int itemDisplayId = 45008 + (row * 5 + col); // Item group ID
                RSInterface itemGroup = RSInterface.interfaceCache[itemDisplayId];
                if (itemGroup != null) {
                    // Update the item in the group (each group displays 1 item)
                    itemGroup.inv[0] = itemIds[row][col] + 1; // Add 1 because item IDs are offset in the client
                    itemGroup.invStackSizes[0] = marked[row][col] ? 2 : 1; // Use quantity 2 to indicate marked state
                }
            }
        }
    }
    public void updateProgressBar(int childId, int interfaceState, int percentage, int interfaceState2) {
        RSInterface rsi = RSInterface.interfaceCache[childId];
        if (rsi == null) {
            return;
        }

        ProgressBar inter = (ProgressBar) rsi;
        inter.progressBarState = interfaceState;
        inter.progressBarPercentage = percentage;
        inter.defaultBarState = interfaceState2;
    }

    public void updateProgressBar(int childId, int interfaceState, int percentage, int maxPercentage, int interfaceState2) {
        RSInterface rsi = RSInterface.interfaceCache[childId];
        if (rsi == null) {
            return;
        }

        ProgressBar inter = (ProgressBar) rsi;
        inter.progressBarState = interfaceState;
        inter.progressBarPercentage = percentage;
        inter.defaultBarState = interfaceState2;
    }

    public int getPlane() {
        return plane;
    }

    public int getBaseX() {
        return regionBaseX;
    }

    public int getBaseY() {
        return regionBaseY;
    }

    public boolean isInInstancedRegion() {
        return requestMapReconstruct;
    }

    public int[][][] getInstanceTemplateChunks() {
        return constructRegionData;
    }

    public CollisionMap[] getCollisionMaps() {
        return collisionData;
    }

    public Player getLocalPlayer() {
        return myPlayer;
    }

    public void stopNow() {
        destroy();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void teleport(int x, int y) {
        String string = "::tele " + x + " " + y;
        getOut().putOpcode(103);
        getOut().putByte(string.length() - 1);
        getOut().putString(string.substring(2));
    }

    public int getItemHoverX(boolean inventory, int dx, boolean check) {
        int x = super.mouseX - dx;
        int width = RSInterface.interfaceCache[94021].width;
        if (inventory) {
            if (x > 240 - width) {
                x = 240 - width;
            }
            if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                if (x + width > 460) {
                    x = 460 - width;
                }
            }
        }
        return x;
    }

    public int getItemHoverY(boolean inventory, int dy, boolean check) {
        int y = super.mouseY - dy;
        int height = RSInterface.interfaceCache[94021].height;
        if (inventory) {
            int max = check ? 290 : 180;
            if (y > max) {
                y = max;
            }
            max = check ? (getHeight() - 240) : (getHeight() - 330);
            if (y > max) {
                y = max;
            }
        }
        return y;
    }

    public void drawHintMenuDono(RSInterface child) {
        if (hovering && milestoneHoverComp >= 0 && donationTabHoverChild == child.id) {
            int mouseX = super.mouseX - 80;
            int mouseY = super.mouseY;
            if (child.milestones[milestoneHoverComp].getType().equals(Milestone.MilestoneType.DESCRIPTION)) {
                hintTier = 100;
                hintDescripter = child.milestones[milestoneHoverComp].getDescription();
                hintName = "$" + child.milestones[milestoneHoverComp].getMilestone() + " Milestone";
            }
            if (child.milestones[milestoneHoverComp].getType().equals(Milestone.MilestoneType.ITEMS)) {
                hintTier = 99;
                hintItems = child.milestones[milestoneHoverComp].rewards;
                hintName = "$" + child.milestones[milestoneHoverComp].getMilestone() + " Milestone";
            }
            if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
                if (mouseX > Client.clientWidth - 413) {
                    mouseX = Client.clientWidth - 413;
                }
                if (mouseY > Client.clientHeight - 354) {
                    mouseY = Client.clientHeight - 354;
                }
                drawStatMenu(ColorConstants.VANGUARD_PURPLE, mouseX, mouseY, hintTier);
            } else {
                int mouseXMod = 0;
                int mouseYMod = 0;
                int mouseYSize = 36;
                if (mouseX + 160 > Client.clientWidth) {
                    mouseXMod = (Client.clientWidth - (mouseX + 160));
                }
                if (mouseY + mouseYSize > Client.clientHeight) {
                    mouseYMod = (Client.clientHeight - (mouseY + mouseYSize));
                }
                drawStatMenu(ColorConstants.VANGUARD_PURPLE, mouseX + mouseXMod, mouseY + mouseYMod, hintTier);
            }
        }
    }

    public void drawHintMenu() {
        if (hintName == null || hintId < 0 || hintTier < 0) {
            return;
        }
        if (hintName.startsWith("@")) {
            hintName = hintName.substring(5);
        }

        if (ItemStats.itemstats[itemHover] == null && hintTier == 0)
            return;

        int mouseX = super.mouseX - 80;
        int mouseY = super.mouseY;
        if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
            if (mouseX > Client.clientWidth - 413) {
                mouseX = Client.clientWidth - 413;
            }
            if (mouseY > Client.clientHeight - 354) {
                mouseY = Client.clientHeight - 354;
            }
            if (controlIsDown) {
                drawStatMenu(ColorConstants.VANGUARD_PURPLE, mouseX, mouseY, hintTier);
            } else {
                Raster.fillPixels(mouseX, 160, 36, ColorConstants.VANGUARD_PURPLE, mouseY + 5);
                Raster.transparentBox(34, mouseY + 6, mouseX + 1, 0x26211B, 158, 230);
                newSmallFont.drawCenteredString("<u=AF70C3>" + hintName, mouseX + 80, mouseY + 18, ColorConstants.VANGUARD_PURPLE, 1);
                newSmallFont.drawCenteredString("Press CTRL to view " + (hintTier >= 1 ? "Details" : "Stats"), mouseX + 80, mouseY + 35, ColorConstants.VANGUARD_PURPLE, 1);
            }
        } else {
            int mouseXMod = 0;
            int mouseYMod = 0;
            int mouseYSize = 0;
            if (controlIsDown) {
                if (hintTier == 2) {
                    mouseYSize = 100;
                }
                if (hintTier == 1) {
                    mouseYSize = 60;
                }
                if (hintTier == 0) {
                    mouseYSize = 180;
                }
            } else {
                mouseYSize = 36;
            }
            if (mouseX + 160 > Client.clientWidth) {
                mouseXMod = (Client.clientWidth - (mouseX + 160));
            }
            if (mouseY + mouseYSize > Client.clientHeight) {
                mouseYMod = (Client.clientHeight - (mouseY + mouseYSize));
            }
            if (controlIsDown) {
                drawStatMenu(ColorConstants.VANGUARD_PURPLE, mouseX + mouseXMod, mouseY + mouseYMod, hintTier);
            } else {
                Raster.fillPixels(mouseX + mouseXMod, 160, 36, ColorConstants.VANGUARD_PURPLE, mouseY + 5 + mouseYMod);
                Raster.transparentBox(34, mouseY + 6  + mouseYMod, mouseX + 1 + mouseXMod, 0x26211B, 158, 230);
                newSmallFont.drawCenteredString("<u=AF70C3>" + hintName, mouseX + 80 + mouseXMod, mouseY + 18  + mouseYMod, ColorConstants.VANGUARD_PURPLE, 1);
                newSmallFont.drawCenteredString("Press CTRL to view " + (hintTier >= 1 ? "Details" : "Stats"), mouseX + 80 + mouseXMod, mouseY + 35 + mouseYMod, ColorConstants.VANGUARD_PURPLE, 1);
            }
        }

    }
    public int hintTier;

    public void drawStatMenu(int color, int mouseX, int mouseY, int hintTier) {
        if (hintTier == 100) {
            Raster.fillPixels(mouseX, 160, 35, ColorConstants.VANGUARD_PURPLE, mouseY + 5);
            Raster.transparentBox(33, mouseY + 6, mouseX + 1, 0x26211B, 158, 230);
            newSmallFont.drawCenteredString("<u=AF70C3>" + hintName, mouseX + 80, mouseY + 18, ColorConstants.RS_ORANGE, 1);
            newSmallFont.drawBasicString("<u=AF70C3>" + hintDescripter, mouseX + 10, mouseY + 34, ColorConstants.RS_ORANGE, 1, false);
        } else if (hintTier == 99) {
            int height = 0;
            int amountOfItems = hintItems.length;
            if (amountOfItems > 12) {
                height = 200;
            } else if (amountOfItems > 8) {
                height = 160;
            } else if (amountOfItems > 4) {
                height = 120;
            } else {
                height = 80;
            }
            Raster.fillPixels(mouseX, 160, height, ColorConstants.VANGUARD_PURPLE, mouseY + 5);
            Raster.transparentBox(height - 2, mouseY + 6, mouseX + 1, 0x26211B, 158, 230);
            newSmallFont.drawCenteredString("<u=AF70C3>" + hintName, mouseX + 80, mouseY + 18, ColorConstants.RS_ORANGE, 1);
            newSmallFont.drawBasicString("<u=AF70C3>Rewards:</u>", mouseX + 10, mouseY + 34, ColorConstants.RS_ORANGE, 1, false);
            int x_mod = 0;
            int y_mod = 3;
            int counter =  0;
            for (DummyItem hintItem : hintItems) {
                if (hintItem.getId() == -1)
                    continue;
                Sprite sprite = ItemDef.getSprite(hintItem.getId(), hintItem.getAmount(), 0);
                if (sprite != null) {
                    sprite.drawSprite(mouseX + 5 + x_mod, mouseY + 45 + y_mod);
                    int amount = hintItem.getAmount();
                    if (amount > 99999 && amount < 10000000) {
                        newSmallFont.drawBasicString(intToKOrMil(amount), mouseX + 6 + x_mod, mouseY + 45 + y_mod, 0xFFFFFF, 1, false);
                    } else if (amount > 9999999) {
                        newSmallFont.drawBasicString(intToKOrMil(amount), mouseX + 6 + x_mod, mouseY + 45 + y_mod, 0x00ff80, 1, false);
                    } else if (amount > 1) {
                        newSmallFont.drawBasicString(intToKOrMil(amount), mouseX + 6 + x_mod, mouseY + 45 + y_mod, 0xFFFF00, 1, false);
                    }
                    x_mod += 40;
                    counter++;
                    if(counter == 4) {
                        counter = 0;
                        y_mod += 40;
                        x_mod = 0;
                    }
                }
            }
        } else if (hintTier == 2) {
            Raster.fillPixels(mouseX, 160, 100, ColorConstants.VANGUARD_PURPLE, mouseY + 5);
            Raster.transparentBox(98, mouseY + 6, mouseX + 1, 0x26211B, 158, 230);
            newSmallFont.drawCenteredString("<u=AF70C3>" + hintName, mouseX + 80, mouseY + 18, color, 1);
            newSmallFont.drawBasicString("<u=AF70C3>Cosmetic Bonuses</u>", mouseX + 10, mouseY + 34, color, 1, false);
        } else if (hintTier == 1) {
            int data[] = PotionStats.getDataForId(hintId);
            int droprate = data[0];
            int crit = data[1];
            int damage = data[2];
            int plus_stats = data[3];
            Raster.fillPixels(mouseX, 160, 60, ColorConstants.VANGUARD_PURPLE, mouseY + 5);
            Raster.transparentBox(58, mouseY + 6, mouseX + 1, 0x26211B, 158, 230);
            newSmallFont.drawCenteredString("<u=AF70C3>" + hintName, mouseX + 80, mouseY + 18, color, 1);
            newSmallFont.drawBasicString("<u=AF70C3>Bonuses</u>", mouseX + 10, mouseY + 34, color, 1, false);
            newSmallFont.drawBasicString("DR: " + droprate + "%", mouseX + 10, mouseY + 48, color, 1, false);
            newSmallFont.drawBasicString("Crit: " + crit + "%", mouseX + 85, mouseY + 48, color, 1, false);
            newSmallFont.drawBasicString("DMG: " + damage + "%", mouseX + 10, mouseY + 61, color, 1, false);
            newSmallFont.drawBasicString("OVL: " + plus_stats, mouseX + 85, mouseY + 61, color, 1, false);
        } else {
            int stabAtk = ItemStats.itemstats[itemHover].attackBonus[0];
            int slashAtk = ItemStats.itemstats[itemHover].attackBonus[1];
            int crushAtk = ItemStats.itemstats[itemHover].attackBonus[2];
            int magicAtk = ItemStats.itemstats[itemHover].attackBonus[3];
            int rangedAtk = ItemStats.itemstats[itemHover].attackBonus[4];
            int speed = ItemStats.itemstats[itemHover].speed;
            int stabDef = ItemStats.itemstats[itemHover].defenceBonus[0];
            int slashDef = ItemStats.itemstats[itemHover].defenceBonus[1];
            int crushDef = ItemStats.itemstats[itemHover].defenceBonus[2];
            int magicDef = ItemStats.itemstats[itemHover].defenceBonus[3];
            int rangedDef = ItemStats.itemstats[itemHover].defenceBonus[4];
            int strengthBonus = ItemStats.itemstats[itemHover].strengthBonus;
            int prayerBonus = ItemStats.itemstats[itemHover].prayerBonus;
            int rangeBonus = ItemStats.itemstats[itemHover].rangeBonus;
            int magicBonus = ItemStats.itemstats[itemHover].magicBonus;
            Raster.fillPixels(mouseX, 160, 180, ColorConstants.VANGUARD_PURPLE, mouseY + 5);
            Raster.transparentBox(178, mouseY + 6, mouseX + 1, 0x26211B, 158, 230);
            newSmallFont.drawCenteredString("<u=AF70C3>" + hintName, mouseX + 80, mouseY + 18, color, 1);
            newSmallFont.drawBasicString("<u=AF70C3>Attack Bonuses</u>", mouseX + 10, mouseY + 34, color, 1, false);
            newSmallFont.drawBasicString("Stab: " + stabAtk, mouseX + 10, mouseY + 48, color, 1, false);
            newSmallFont.drawBasicString("Slash: " + slashAtk, mouseX + 10, mouseY + 61, color, 1, false);
            newSmallFont.drawBasicString("Crush: " + crushAtk, mouseX + 10, mouseY + 74, color, 1, false);
            newSmallFont.drawBasicString("Magic: " + magicAtk, mouseX + 85, mouseY + 48, color, 1, false);
            newSmallFont.drawBasicString("Range: " + rangedAtk, mouseX + 85, mouseY + 61, color, 1, false);
            if (speed != 6)
                newSmallFont.drawBasicString("Speed: " + speed, mouseX + 85, mouseY + 74, color, 1, false);
            newSmallFont.drawBasicString("<u=AF70C3>Defence Bonuses</u>", mouseX + 10, mouseY + 91, color, 1, false);
            newSmallFont.drawBasicString("Stab: " + stabDef, mouseX + 10, mouseY + 105, color, 1, false);
            newSmallFont.drawBasicString("Slash: " + slashDef, mouseX + 10, mouseY + 118, color, 1, false);
            newSmallFont.drawBasicString("Crush: " + crushDef, mouseX + 10, mouseY + 131, color, 1, false);
            newSmallFont.drawBasicString("Magic: " + magicDef, mouseX + 85, mouseY + 105, color, 1, false);
            newSmallFont.drawBasicString("Range: " + rangedDef, mouseX + 85, mouseY + 118, color, 1, false);
            newSmallFont.drawBasicString("<u=AF70C3>Other Bonuses</u>", mouseX + 10, mouseY + 148, color, 1, false);
            newSmallFont.drawBasicString("Str: " + strengthBonus, mouseX + 10, mouseY + 162, color, 1, false);
            newSmallFont.drawBasicString("Pray: " + prayerBonus, mouseX + 85, mouseY + 162, color, 1, false);
            newSmallFont.drawBasicString("Magic: " + rangeBonus, mouseX + 10, mouseY + 175, color, 1, false);
            newSmallFont.drawBasicString("Range: " + magicBonus, mouseX + 85, mouseY + 175, color, 1, false);
        }
    }

    public int getScale() {
        return 1 << Scene.viewDistance;
    }


    private static BufferedImage imageToBufferedImage(Image image) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return bufferedImage;
    }

    public static Image makeColorTransparent(BufferedImage im, final Color color) {
        RGBImageFilter filter = new RGBImageFilter() {
            public final int markerRGB = color.getRGB() | 0xFF000000;

            @Override
            public final int filterRGB(int x, int y, int rgb) {
                if ((rgb | 0xFF000000) == markerRGB) {
                    return 0x00FFFFFF & rgb;
                } else {
                    return rgb;
                }
            }
        };
        ImageProducer ip = new FilteredImageSource(im.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(ip);
    }

    public static void dumpImage(Sprite image, ItemDef def) {
        if (!Configuration.DUMP_INVENTORY_IMAGES) {
            return;
        }
        if (image == null) {
            System.out.println("Can't dump: " + def.id);
            return;
        }

        File directory = new File(Signlink.getCacheDirectory() + "dump/");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File out = new File(Signlink.getCacheDirectory() + "dump/" + def.id + ".png");

        BufferedImage bi = new BufferedImage(image.myWidth, image.myHeight, BufferedImage.TYPE_INT_RGB);
        bi.setRGB(0, 0, image.myWidth, image.myHeight, image.myPixels, 0, image.myWidth);
        Image img = makeColorTransparent(bi, new Color(0, 0, 0));
        BufferedImage trans = imageToBufferedImage(img);
        try {
            if (!out.exists()) {
                out = new File(Signlink.getCacheDirectory() + "new/" + def.id + ".png");
                ImageIO.write(trans, "png", out);
            }
        } catch (Exception e) {
            System.out.println("Can't dump: " + def.id);
        }
    }

    public static final int[][] HIT_POINT_COLORS = new int[][]{
        {0x00FF00, 0xFF0000},//Standard
        {0x00D2AC, 0x002620},//Cyan
        {0xF46100, 0x491C00},//Orange
        {0xDBD300, 0x2D2B00},//Yellow
        {0x00A9FF, 0x001617},//Light Blue
        {0x7347CC, 0x150E23},//Purple
        {0x70C613, 0x152603}//Recolored Standard
    };

    public static final int[] COLOR_BOXES = new int[]{65535, // cyan
        16758784, // orange
        2861308, // darkish blue
        14942335, // sort of a pinkish red
        12458710, // purple
        16777215, // white
        0, // black
        51456, // green
        16711680, // red
        16776960, // yellow
        12500669, // gray
    };
    public static final String[] CLAN_CHAT_COLORS = new String[]{"<col=26FEFD>", "<col=FDB432>", "<col=32A8F7>", "<col=E10F7C>", "<col=BA2DD0>", "<col=FFFFFF>", "<col=000000>", "<col=20C531>", "<col=FC001A>", "<col=FEFC42>", "<col=BCBCBB>"};


    public boolean isHoveringMapArea() {
        return mapArea.isHoveringMap(this);
    }

    public boolean isHoveringTabArea() {
        return tabArea.isHovering(this, GameFrame.getScreenMode());
    }

    private void changeClanChatSelectionBox(Client client) {
        client.clanChatColor = client.clanChatColor + 1 == COLOR_BOXES.length ? 0 : client.clanChatColor + 1;
        Save.settings(Client.getClient());
        client.pushMessage("You've changed your clan chat color.", 0, "");
    }

    private void changeSplitChatSelectionBox(Client client) {
        client.splitChatColor = client.splitChatColor + 1 == COLOR_BOXES.length ? 0 : client.splitChatColor + 1;
        Save.settings(Client.getClient());
        client.pushMessage("You've changed your private split-chat color.", 0, "");
    }

    private void changeHealthSelectionBox(Client client) {
        client.healthpoints_bar_color = client.healthpoints_bar_color + 1 == HIT_POINT_COLORS.length ? 0 : client.healthpoints_bar_color + 1;
        Save.settings(Client.getClient());
        client.pushMessage("You've changed your Health bar colors.", 0, "");
    }

    public void sendCreationMenuAction(int itemId, int amount) {
        getOut().putOpcode(166);
        getOut().putInt(itemId);
        getOut().putByte(amount);
    }
}
