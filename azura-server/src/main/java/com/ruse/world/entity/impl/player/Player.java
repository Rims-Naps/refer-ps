package com.ruse.world.entity.impl.player;

import com.google.gson.annotations.Expose;
import com.ruse.GameSettings;
import com.ruse.PlayerSetting;
import com.ruse.donation.Membership;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.PlayerDeathTask;
import com.ruse.engine.task.impl.WalkToTask;
import com.ruse.model.*;
import com.ruse.model.container.impl.*;
import com.ruse.model.container.impl.Bank.BankSearchAttributes;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.WeaponAnimations;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.model.definitions.WeaponInterfaces.WeaponInterface;
import com.ruse.model.input.Input;
import com.ruse.net.PlayerSession;
import com.ruse.net.SessionState;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketSender;
import com.ruse.util.FrameUpdater;
import com.ruse.util.Misc;
import com.ruse.util.Stopwatch;
import com.ruse.world.World;
import com.ruse.world.content.AoE.AoEToken;
import com.ruse.world.content.BattlePass.BattlePass;
import com.ruse.world.content.ProgressTaskManager.*;
import com.ruse.world.content.Fairies.Companion;
import com.ruse.world.content.Pets.Summon;
import com.ruse.world.content.HolidayTasks.HolidayTaskInterface;
import com.ruse.world.content.HolidayTasks.HolidayTaskData;
import com.ruse.world.content.HolidayTasks.HolidayTaskHandler;
import com.ruse.world.content.ascension.TierUpgradeType;
import com.ruse.world.content.ascension.TierUpgrading;
import com.ruse.world.content.bis.BestInSlotInterface;
import com.ruse.world.content.bossfights.BossFight;
import com.ruse.world.content.megaChests.MegaChest;
import com.ruse.world.content.BankPin.BankPinAttributes;
import com.ruse.world.content.*;
import com.ruse.world.content.DropLog.DropLogEntry;
import com.ruse.world.content.KillsTracker.KillsEntry;
import com.ruse.world.content.LoyaltyProgramme.LoyaltyTitles;
import com.ruse.world.content.NewDaily.DailyEntry;
import com.ruse.world.content.NewDaily.DailyTaskInterface;
import com.ruse.world.content.achievement.AchievementHandler;
import com.ruse.world.content.casketopening.CasketOpening;
import com.ruse.world.content.clan.ClanChat;
import com.ruse.world.content.collectionlogs.CollectionLogManager;
import com.ruse.world.content.combat.CombatFactory;
import com.ruse.world.content.combat.CombatType;
import com.ruse.world.content.combat.effect.BurnEffect.BurnData;
import com.ruse.world.content.combat.magic.CombatSpell;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.pvp.PlayerKillingAttributes;
import com.ruse.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.ruse.world.content.combat.strategy.CombatStrategies;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.combat.weapon.CombatSpecial;
import com.ruse.world.content.combat.weapon.FightType;
import com.ruse.world.content.combiner.CustomCombiner;
import com.ruse.world.content.dailytasks_new.DailyTask;
import com.ruse.world.content.dailytasks_new.TaskChallenge;
import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.donoisland.Island;
import com.ruse.world.content.donoisland.IslandInterface;
import com.ruse.world.content.doorpick.DoorInterface;
import com.ruse.world.content.doorpick.DoorPick;
import com.ruse.world.content.forgottenRaids.party.ForgottenRaidParty;
import com.ruse.world.content.forgottenRaids.view.ForgottenRaidInterface;
import com.ruse.world.content.gamblinginterface.GamblingInterface;
import com.ruse.world.content.grandexchange.GrandExchangeSlot;
import com.ruse.world.content.groupironman.IronmanGroup;
import com.ruse.world.content.groupslayer.GroupSlayerParty;
import com.ruse.world.content.instanceMananger.InstanceManager;
//import com.ruse.world.content.itemExchange.ItemExchangeInterface;
import com.ruse.donation.PersonalCampaignHandler;
import com.ruse.world.content.leaderboards.LeaderboardManager;
import com.ruse.world.content.minigames.MinigameAttributes;
import com.ruse.world.content.minigames.impl.Dueling;
import com.ruse.world.content.minigames.impl.CircleOfElements;
import com.ruse.world.content.minigames.impl.dungeoneering.Dungeoneering;
import com.ruse.world.content.new_raids_system.RaidsConnector;
import com.ruse.world.content.new_raids_system.RaidsPartyConnector;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.new_raids_system.saving.RaidsSaving;
import com.ruse.world.content.newgroupironman.GroupIronman;
import com.ruse.world.content.newgroupironman.GroupIronmanGroup;
import com.ruse.world.content.newspinner.MysteryBoxManager;
import com.ruse.world.content.newupgrade.ItemUpgrader;
import com.ruse.world.content.parties.Party;
import com.ruse.world.content.parties.PartyService;
import com.ruse.world.content.parties.impl.TowerParty;
import com.ruse.world.content.pos.PlayerOwnedShopManager;
import com.ruse.world.content.raids.Raid;
import com.ruse.world.content.raids.Reward;
import com.ruse.world.content.raids.invocation.InvocationHandler;
import com.ruse.world.content.skill.SkillManager;
import com.ruse.world.content.skill.impl.construction.HouseFurniture;
import com.ruse.world.content.skill.impl.construction.Portal;
import com.ruse.world.content.skill.impl.construction.Room;
import com.ruse.world.content.skill.impl.farming.Farming;
import com.ruse.world.content.skill.impl.prayer.Prayer;
import com.ruse.world.content.skill.impl.slayer.Slayer;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.skill.impl.summoning.Pouch;
import com.ruse.world.content.skill.impl.summoning.Summoning;
import com.ruse.world.content.skill.skillable.Skillable;
import com.ruse.world.content.slot.SlotSystem;
import com.ruse.world.content.staffhelpdesk.PlayerHelpDesk;
import com.ruse.world.content.staffhelpdesk.StaffHelpDesk;
import com.ruse.world.content.staffhelpdesk.StaffHelpInterface;
import com.ruse.world.content.startertasks.StarterTaskHandler;
import com.ruse.world.content.timers.impl.*;
import com.ruse.world.content.tower.TowerController;
import com.ruse.world.content.tpinterface.TPInterface;
import com.ruse.world.content.tradingpost.PlayerShops;
import com.ruse.world.content.transmorgify.Transmog;
import com.ruse.world.content.tree.*;
import com.ruse.world.content.upgrading.UpgradeInterface;
import com.ruse.world.entity.actor.player.controller.ControllerManager;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.StartScreen.GameModes;
import com.ruse.world.instance.impl.DungeoneeringInstance;
import com.ruse.world.region.Region;
import com.ruse.world.region.RegionManager;
import com.ruse.world.region.dynamic.DynamicRegion;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class Player extends Character {

    @Getter
    private Party towerParty = new TowerParty();

    public void setTowerParty(Party party) {
        this.towerParty = party;
    }

    @Getter
    private Raid towerRaid = new TowerController();

    public void setRaid(Raid raid) {
        this.towerRaid = raid;
    }

    @Getter@Setter
    private Party viewingParty = new TowerParty();

    @Getter@Setter
    private Reward reward = new Reward();

    @Getter@Setter
    private int best_tower_time;

    @Getter@Setter
    private int best_tower_wave;

    @Getter@Setter
    private int total_tower_completions;

    @Getter@Setter
    private boolean is_dead_in_tower;

    @Getter@Setter
    private int raidPoints = 0;

    @Getter@Setter
    private int raidKills = 0;

    @Getter
    private InvocationHandler invocations = new InvocationHandler(this);

    @Getter
    private final ForgottenRaidInterface forgottenRaidInterface = new ForgottenRaidInterface(this);

    @Getter
    @Setter
    private ForgottenRaidParty forgottenRaidParty;

    @Getter
    @Setter
    private String lastForgottenRaidInvite;


    @Getter
    private final TierUpgrading tierUpgrading = new TierUpgrading(this);

    /**
     * Skill Tree System
     */
    @Getter@Setter
    private Section currentSection = Section.NONE;
    @Getter
    private Section[] sectionsUnlocked;
    @Getter@Setter
    private Path currentPath = Path.NONE;
    @Getter
    private final TreeController skillTree = new TreeController(this);
    @Getter@Setter
    private Path lastPath = Path.NONE;
    @Getter@Setter
    private Node[] nodesUnlocked;
    @Getter@Setter
    private Bridge[] bridgesUnlocked;
    @Getter@Setter
    private boolean treeUnlocked;

    public Map<PlayerSetting, Integer> playerSettings = new LinkedHashMap<>();

    public Map<PlayerSetting, Integer> getPlayerSettings() {
        return playerSettings;
    }

    public void setPlayerSettings(Map<PlayerSetting, Integer> playerSettings) {
        this.playerSettings = playerSettings;
    }

    @Getter
    @Setter
    private TierUpgradeType tierUpgradeType = TierUpgradeType.NONE;
    private int drBoost;
    private int critchance;
    private int upgBoost;

    @Setter
    private boolean autoBank = false;

    public boolean getAutoBank() {
        return autoBank;
    }

    @Setter
    private boolean autoDissolve;

    public boolean getAutoDissolve() {
        return autoDissolve;
    }
    private double dmgBoost;
    private Dissolving dissolving = new Dissolving(this);

    private Set<String> bannedRaidMembers = new HashSet<>();

    public Set<String> getBannedRaidMembers() {
        return this.bannedRaidMembers;
    }

    public void setBannedRaidMembers(Set<String> bannedRaidMembers) {
        this.bannedRaidMembers = bannedRaidMembers;
    }

    private boolean inRaid = false;

    public boolean isInRaid() {
        return inRaid;
    }

    public void setInRaid(boolean inRaid) {
        this.inRaid = inRaid;
    }

    @Getter
    public AoEToken aoeToken = new AoEToken(this);

    @Getter@Setter
    public LocalDateTime lastFreeBox;

    @Getter@Setter
    private int bassilisk_kills;
    @Getter@Setter
    private int brimstone_kills;
    @Getter@Setter
    private int everthorn_kills;

    @Getter@Setter
    private Island island = new Island(this);

    @Getter
    private IslandInterface islandInterface = new IslandInterface(this);

    @Getter@Setter
    private int islandPicks = 0;

    @Getter@Setter
    private int lastNecroSpell = 0;

    @Getter@Setter
    private int lastCampaignCompleted = 0;




    public void addItemUnderAnyCircumstance(Item item) {
        if(getInventory().full(item.getId())) {
            getBank(getCurrentBankTab()).add(item);
            sendMessage("X" + item.getAmount() + " " + item.getDefinition().getName() + " has been sent to your bank.");
        } else {
            getInventory().add(item);
        }
    }

    public void checkOwnerItems() {
        int[] ownerItems = {
                //OWNER
                15792, 15793, 15794, 15795, 15796, 19944,17122,17123,
                //ICY
                9955, 9956, 9957, 9958, 9959,
                //CORRUPT
                9950, 9951, 9952, 9953, 9954,
                //POISON
                9960, 9961, 9962, 9963, 9964,

                //HOLY
                3020, 3021, 3022, 3023, 3024
        };
        int[] equipmentSlots = {
                Equipment.HEAD_SLOT,
                Equipment.BODY_SLOT,
                Equipment.LEG_SLOT,
                Equipment.HANDS_SLOT,
                Equipment.FEET_SLOT,
                Equipment.CAPE_SLOT,
                Equipment.RING_SLOT,
                Equipment.AMULET_SLOT
        };

        boolean isWearingAllowedItems = true;

        for (int i = 0; i < equipmentSlots.length; i++) {
            int equippedItemId = getEquipment().getItems()[equipmentSlots[i]].getId();
            if (Arrays.stream(ownerItems).noneMatch(item -> item == equippedItemId)) {
                isWearingAllowedItems = false;
                break;
            }
        }
        setWearingFullOwner(isWearingAllowedItems);
    }


    public boolean wearingNecroGameItems() {
        return getEquipment().containsAny(8044, 8046 , 7784 , 7785 , 7786 , 311);
    }

    public boolean wearingNecroGameItemsonCosmetic() {
        return getCosmetics().containsAny(8044, 8046 , 7784 , 7785 , 7786);
    }


    public boolean fullMysticTier1() {
        return getEquipment().containsAll(17024, 17025, 17026, 17026, 17028);
    }
    public boolean fullMysticTier2() {
        return getEquipment().containsAll(17029, 17030, 17031, 17032, 17033);
    }
    public boolean fullMysticTier3() {
        return getEquipment().containsAll(17034, 17035, 17036, 17037, 17038);
    }
    public  boolean fullMysticTier4() {
        return getEquipment().containsAll(17039, 17040, 17041, 17042, 17043);
    }
    public  boolean fullMysticTier5() {
        return getEquipment().containsAll(17044, 17045, 17046, 17047, 17048);
    }
    public boolean fullMysticTier6() {
        return getEquipment().containsAll(17049, 17050, 17051, 17052, 17053);
    }
    public boolean fullMysticTier7() {
        return getEquipment().containsAll(17054, 17055, 17056, 17057, 17058);
    }
    public boolean fullEliteTier1() {
        return getEquipment().containsAll(12300, 12301, 12302, 12303, 12304);
    }
    public boolean fullEliteTier2() {
        return getEquipment().containsAll(12305, 12306, 12307, 12308, 12309);
    }
    public boolean fullEliteTier3() {return getEquipment().containsAll(12310, 12311, 12312, 12313, 12314);}
    public boolean fullEliteTier4() {
        return getEquipment().containsAll(12315, 12316, 12317, 12318, 12319);
    }
    public boolean fullEliteTier5() {
        return getEquipment().containsAll(12320, 12321, 12322, 12323, 12324);
    }
    public boolean fullEliteTier6() {return getEquipment().containsAll(12325, 12326, 12327, 12328, 12329);
    }

    public boolean fullSpectralTier1() {return getEquipment().containsAll(2025, 2026, 2027, 2028, 2029);
    }
    public boolean fullSpectralTier2() {return getEquipment().containsAll(2030, 2031, 2032, 2033, 2034);
    }
    public boolean fullSpectralTier3() {return getEquipment().containsAll(2035, 2036, 2037, 2038, 2039);
    }


    public boolean fullFallen() {return getCosmetics().containsAll(3000, 3001, 3002, 3003, 3004);
    }

    public boolean fullMinerOutfit() {
        return getEquipment().containsAll(1441, 1442, 1443, 1445);
    }

    public boolean fullWoodcuttingOutfit() {
        return getEquipment().containsAll(1436, 1437, 1439);
    }

    @Getter
    @Setter
    private boolean wearingFullOwner = false;



    @Getter
    @Setter
    private int minigameWave = 1;


    private GroupSlayerParty groupSlayerParty = null;

    public GroupSlayerParty getGroupSlayerParty() {
        return groupSlayerParty;
    }

    public void setGroupSlayerParty(GroupSlayerParty groupSlayerParty) {
        this.groupSlayerParty = groupSlayerParty;
    }

    @Getter
    @Setter
    private int groupSlayerCompletions = 0;

    public void incrementGroupSlayerCompletions(int amount) {
        groupSlayerCompletions += amount;
    }

    /**
     * Group ironman
     */
    private int currentNpcId = -1; // Initialise to -1 or any invalid NPC ID

    private boolean gim;

    public boolean isGim() {
        return gim;
    }

    public int getCurrentNpcId() {
        return this.currentNpcId;
    }

    public void setCurrentNpcId(int npcId) {
        this.currentNpcId = npcId;
    }


    @Getter
    @Setter
    private boolean isShowMyChance;

    @Getter
    @Setter
    private boolean effectson = true;


    private GroupIronmanGroup groupIronmanGroup;

    public GroupIronmanGroup getGroupIronmanGroup() {
        return groupIronmanGroup;
    }

    public void setGroupIronmanGroup(GroupIronmanGroup groupIronmanGroup) {
        this.groupIronmanGroup = groupIronmanGroup;
    }

    private GroupIronmanGroup groupIronmanGroupInvitation = null;

    public void setGroupIronmanGroupInvitation(GroupIronmanGroup groupIronmanGroupInvitation) {
        this.groupIronmanGroupInvitation = groupIronmanGroupInvitation;
    }

    private String groupOwnerName = null;

    public String getGroupOwnerName() {
        return groupOwnerName;
    }

    public void setGroupOwnerName(String groupOwnerName) {
        this.groupOwnerName = groupOwnerName;
    }

    private GroupIronman groupIronman = new GroupIronman(this);

    public GroupIronman getGroupIronman() {
        return groupIronman;
    }

    private CustomCombiner customCombiner = new CustomCombiner(this);

    public CustomCombiner getCustomCombiner() {
        return customCombiner;
    }

    private ItemUpgrader itemUpgrader = new ItemUpgrader(this);

    public ItemUpgrader getItemUpgrader() {
        return itemUpgrader;
    }


    private Player owner;

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    private final BestInSlotInterface bis = new BestInSlotInterface(this);
    public BestInSlotInterface getBis() {
        return bis;
    }

    @Getter
    @Setter
    private ArrayList<String> offences = new ArrayList<>();


    @Getter
    private final ArrayList<BossPets.BossPet> obtainedPets = new ArrayList<>();

    @Getter
    private final DonatorShop donatorShop = new DonatorShop(this);

    @Getter
    private final CosmeticShop cosmeticShop = new CosmeticShop(this);

    @Getter
    private MegaChest megaChest = new MegaChest(this);

    @Getter
    private final UpgradeInterface upgradeInterface = new UpgradeInterface(this);
    @Getter



    public boolean canMysteryBox;
    public boolean switchedPrayerBooks;
    private MysteryBoxManager newSpinner = new MysteryBoxManager(this);

    public MysteryBoxManager getNewSpinner() {
        return newSpinner;
    }


    public int[] mboxLoot = {-1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1};

    public boolean levelNotifications = true;


    private final StaffHelpDesk staffHelpDesk = new StaffHelpDesk(this);
    private final PlayerHelpDesk playerHelpDesk = new PlayerHelpDesk(this);
    private final StaffHelpInterface staffHelpInterface = new StaffHelpInterface(this);
    public StaffHelpDesk getStaffHelpDesk() {
        return staffHelpDesk;
    }
    public StaffHelpInterface getStaffHelpInterface() {
        return staffHelpInterface;
    }
    public PlayerHelpDesk getPlayerHelpDesk() {
        return playerHelpDesk;
    }


    public NPC findSpawnedFor() {
        return findSpawnedFor(position.getRegionId());
    }
    public NPC findSpawnedFor(int regionId) {
        Region region = RegionManager.getRegion(regionId);
        if(region == null) {
            return null;
        }
        for(int npcIndex : region.getVisibleNpcs()) {
            NPC npc = World.getNpcs().get(npcIndex);
            if (npc == null) {
                continue;
            }
            if(this.equals(npc.getSpawnedFor())) {
                return npc;
            }

        }
        return null;
    }
    public int getDungeoneeringPrestige() {
        return dungeoneeringPrestige;
    }

    public void setDungeoneeringPrestige(int dungeoneeringPrestige) {
        this.dungeoneeringPrestige = dungeoneeringPrestige;
    }

    private int dungeoneeringPrestige = 0;


    public int getDungeoneeringFloor() {
        return dungeoneeringFloor;
    }

    public void setDungeoneeringFloor(int dungeoneeringFloor) {
        this.dungeoneeringFloor = dungeoneeringFloor;
    }

    private int dungeoneeringFloor = 0;

    public void incrementDungFloor() {
        if(dungeoneeringFloor < Dungeoneering.Constants.NUMBER_FLOORS) {
            dungeoneeringFloor++;
        } else {
            dungeoneeringFloor = 0;
            dungeoneeringPrestige++;
        }
    }
    private final ControllerManager controllerManager = new ControllerManager(this);
    public ControllerManager getControllerManager() {
        return controllerManager;
    }


    public CircleOfElements vod = new CircleOfElements(this);
    private int perkIndex = 0;

    public int getPerkIndex() {
        return perkIndex;
    }

    public void setPerkIndex(int perkIndex) {
        this.perkIndex = perkIndex;
    }

    @Getter
    @Setter
    private int groupInvitationId;
    @Getter
    @Setter
    private IronmanGroup ironmanGroup = null;
    @Getter
    @Setter
    private boolean groupIronmanLocked;

    public GroupIronmanBank getGroupIronmanBank(int index) {
        return getIronmanGroup() != null ? getIronmanGroup().getBank(index) : null;
    }


    @Getter
    @Setter
    private boolean gambleBanned;

    @Getter
    @Setter
    private int[] gauntletZones = new int[3];

    @Getter
    @Setter
    private int[] progressionZones = new int[4];
    @Getter
    @Setter
    private boolean[] zonesComplete = new boolean[4];


    private final CasketOpening casketOpening = new CasketOpening(this);
    private boolean spinning;
    public CasketOpening getCasketOpening() {
        return casketOpening;
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }
    public static int Amount_Donated;
    private final List<GroundItem> itemsInScene = new CopyOnWriteArrayList<>();
    private final DailyRewards dailyRewards = new DailyRewards(this);
    private final PlayerOwnedShopManager playerOwnedShopManager = new PlayerOwnedShopManager(this);
    // Timers (Stopwatches)
    private final Stopwatch sqlTimer = new Stopwatch();

    private RaidsSaving raidsPlayer = new RaidsSaving(this);

    public RaidsSaving getRaidsSaving() {
        return raidsPlayer;
    }
    public String getName() {
        return getUsername();
    }

    private RaidsPartyConnector raidsPartyConnector = new RaidsPartyConnector(this);

    private RaidsParty raidsParty;

    public RaidsParty getRaidsParty() {
        return raidsParty;
    }

    public void setRaidsParty(RaidsParty raidsParty) {
        this.raidsParty = raidsParty;
    }
    private final Stopwatch foodTimer = new Stopwatch();
    private final Stopwatch potionTimer = new Stopwatch();
    private final Stopwatch lastRunRecovery = new Stopwatch();
    private final Stopwatch clickDelay = new Stopwatch();
    private final Stopwatch lastItemPickup = new Stopwatch();
    private final Stopwatch lastYell = new Stopwatch();
    private final Stopwatch lastVoteClaim = new Stopwatch();
    private final Stopwatch lastVengeance = new Stopwatch();
    private final Stopwatch emoteDelay = new Stopwatch();
    private final Stopwatch specialRestoreTimer = new Stopwatch();
    private final Stopwatch lastSummon = new Stopwatch();
    private final Stopwatch recordedLogin = new Stopwatch();
    private final Stopwatch creationDate = new Stopwatch();
    private final Stopwatch tolerance = new Stopwatch();
    private final Stopwatch lougoutTimer = new Stopwatch();
    private final Stopwatch lastDfsTimer = new Stopwatch();
    /*** INSTANCES ***/
    private final CopyOnWriteArrayList<KillsEntry> killsTracker = new CopyOnWriteArrayList<>();

    private final CopyOnWriteArrayList<BoxesTracker.BoxEntry> boxTracker = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<DropLogEntry> dropLog = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<NPC> npc_faces_updated = new CopyOnWriteArrayList<>();
    private final List<Player> localPlayers = new LinkedList<>();
    private final List<NPC> localNpcs = new LinkedList<>();
    private final PlayerProcess process = new PlayerProcess(this);
    private final PlayerKillingAttributes playerKillingAttributes = new PlayerKillingAttributes(this);
    private final MinigameAttributes minigameAttributes = new MinigameAttributes();
    private final BankPinAttributes bankPinAttributes = new BankPinAttributes();
    private final BankSearchAttributes bankSearchAttributes = new BankSearchAttributes();

    private final GroupIronmanBank.GroupBankSearchAttributes groupBankSearchAttributes = new GroupIronmanBank.GroupBankSearchAttributes();
    private final BonusManager bonusManager = new BonusManager();
    private final PointsHandler pointsHandler = new PointsHandler(this);
    private final PacketSender packetSender = new PacketSender(this);
    private final Appearance appearance = new Appearance(this);
    private final FrameUpdater frameUpdater = new FrameUpdater();
    private CurrencyPouch currencyPouch = new CurrencyPouch();
    public boolean bot;
    public int selectedGoodieBag = -1;

    public int selectedGoodieBagU = -1;

    @Getter@Setter
    public INTERFACES viewing = INTERFACES.NONE;

    public enum INTERFACES {
        STARTER_TASK, MEDIUM_TASKS, ELITE_TASKS, NONE, GOODIEBAG, GOODIEBAG_U, BANK, GROUP_BANK
    }

    public int currentPlayerPanelIndex = 1;
    public boolean day1Claimed = false;
    public boolean day2Claimed = false;
    public boolean day3Claimed = false;
    public boolean day4Claimed = false;
    public boolean day5Claimed = false;
    public boolean day6Claimed = false;
    public boolean day7Claimed = false;
    public long lastLogin;
    public long lastDailyClaim;
    public long lastVoteTime;
    public long lastPurchase;
    public boolean hasVotedToday;
    public boolean claimedFirst;
    public boolean claimedSecond;
    public boolean claimedThird;
    public long lastDonation;
    public long lastTimeReset;

    public double entriesCurrency;
    public MagicSpellbook previousSpellbook;
    public boolean kcMessage = true;
    public String currentTime, date;

    public int daysVoted;
    public int totalTimesClaimed;
    public int longestDaysVoted;
    public long timeUntilNextReward;
    public long minsPlayed;
    public String lastVoted;
    public int currentVotingStreak;
    public int currentVotes;
    /**
     * Instance Manager variables.
     */

    public String currentInstanceNpcName;
    public int currentInstanceNpcId;
    public int currentInstanceAmount;
    /**
     * Stores the players current daily tasks
     */

    public String currentDailyTask;
    public int currentDailyTaskAmount;
    public int dailiesCompleted;

    /**
     * Daily Money Making Variables Getters and setters for enums
     */
    public int xPosDailyTask;
    public int yPostDailyTask;
    public int zPosDailyTask;
    public int rewardDailyTask;

    @Expose
    public HashMap<DailyTask, TaskChallenge> dailies = new HashMap<>();
    public void setDailies(HashMap<DailyTask, TaskChallenge> dailyTasks) {
        dailies = dailyTasks;
    }
    public boolean skillingTask;
    public int dailySkips = 3;
    public String taskInfo;
    public long[] taskReceivedTime = {-1, -1, -1};

    public boolean hasPlayerCompletedBossTask = false;
    public int currentBossTask;
    public int currentBossTaskAmount;
    public boolean starterClaimed;
    // Timers (Stopwatches)
    public long lastHelpRequest;
    //	public GameModes selectedGameMode;
    public GameModes selectedGameMode;
    public boolean dropMessageToggle = true;
    public boolean hasReferral;
    public long lastDonationClaim;
    private boolean placeholders = true;
    private boolean enteredZombieRaids;
    private int zombieRaidsKC;
    private boolean insideRaids;
    private int afkStallCount1;
    private int afkStallCount2;
    private int afkStallCount3;
    @Getter
    @Setter
    private int godModeTimer;
    @Getter
    private final BestItems bestItems = new BestItems(this);
    private final GoodieBag goodieBag = new GoodieBag(this);

    private final GoodiebagU goodieBagU = new GoodiebagU(this);

    @Getter
    @Setter
    private HashMap<String, Integer> trackers = new HashMap<>();
    @Getter
    private final ModeSelection modeSelection = new ModeSelection(this);
    /**
     * Custom Combiner
     */
    private String savedPin;
    private String savedIp;
    private boolean hasPin2 = false;
    @Getter
    private final DoorPick doorPick = new DoorPick(this);
    private final MysteryBoxOpener mysteryBoxOpener = new MysteryBoxOpener(this);

    @Getter
    private DoorInterface doorInterface = new DoorInterface(this);




    private final GamblingInterface gambling = new GamblingInterface(this);

    private final DonationDeals donationDeals = new DonationDeals(this);
    private int amountDonatedToday;
    private boolean opMode;
    private final BonusXp bonusXp = new BonusXp(this); // instance of BonusXp class
    private final DonatorDiscount donatordiscount = new DonatorDiscount(this); // instance of BonusXp class
    private String mac;
    private String uuid;
    /*** STRINGS ***/
    private String username;
    private String password;
    private String salt;
    private String serial_number;
    private String emailAddress;
    private String hostAddress;
    private String clanChatName;
    private String yellHex;
    private String strippedJewelryName;
    private String yellTitle;
    /*** LONGS **/
    private Long longUsername;
    private long soulInPouch;
    private long totalPlayTime;
    private ArrayList<HouseFurniture> houseFurniture = new ArrayList<>();
    private ArrayList<Portal> housePortals = new ArrayList<>();
    private final PlayerSession session;
    private CharacterAnimations characterAnimations = new CharacterAnimations();
    private PlayerRights rights = PlayerRights.PLAYER;
    //private DonatorRights donatorRights = DonatorRights.REGULAR_PLAYER;
    private final SkillManager skillManager = new SkillManager(this);
    private final PlayerRelations relations = new PlayerRelations(this);
    private final ChatMessage chatMessages = new ChatMessage();
    private Inventory inventory = new Inventory(this);
    private Equipment equipment = new Equipment(this);
    private Equipment preSetEquipment = new Equipment(this);
    private DungeoneeringIronInventory dungeoneeringIronInventory = new DungeoneeringIronInventory(this);
    private DungeoneeringIronEquipment dungeoneeringIronEquipment = new DungeoneeringIronEquipment(this);
    private final PriceChecker priceChecker = new PriceChecker(this);
    private final Trading trading = new Trading(this);
    private final Dueling dueling = new Dueling(this);
    private final Slayer slayer = new Slayer(this);
    private final Farming farming = new Farming(this);
    private final Summoning summoning = new Summoning(this);
    private final Bank[] bankTabs = new Bank[9];
    private Room[][][] houseRooms = new Room[5][13][13];
    private PlayerInteractingOption playerInteractingOption = PlayerInteractingOption.NONE;
    private GameMode gameMode = GameMode.NORMAL;

    private XpMode xpmode = XpMode.EASY;

    private BoostMode boostmode = BoostMode.SINISTER;
    private Difficulty difficulty = Difficulty.MODERN; // modern now default difficulty, was extreme
    private CombatType lastCombatType = CombatType.MELEE;
    private FightType fightType = FightType.UNARMED_PUNCH;
    private Prayerbook prayerbook = Prayerbook.NORMAL;
    private MagicSpellbook spellbook = MagicSpellbook.NORMAL;
    private LoyaltyTitles loyaltyTitle = LoyaltyTitles.NONE;
    private ClanChat currentClanChat;
    private Input inputHandling;
    private WalkToTask walkToTask;
    private Shop shop;
    private GameObject interactingObject;
    private Item interactingItem;
    private Dialogue dialogue;
    private DwarfCannon cannon;
    private CombatSpell autocastSpell, castSpell, previousCastSpell;
    private RangedWeaponData rangedWeaponData;
    private CombatSpecial combatSpecial;
    private WeaponInterface weapon;
    private Item untradeableDropItem;
    private Object[] usableObject;
    private GrandExchangeSlot[] grandExchangeSlots = new GrandExchangeSlot[6];
    private Task currentTask;
    private Position resetPosition;
    private Pouch selectedPouch;
    /*** INTS ***/
    private int[] brawlerCharges = new int[11];

    private int stackCharges;

    private int[] ancientArmourCharges = new int[15];
    private int[] forceMovement = new int[7];
    private final int[] leechedBonuses = new int[7];
    private int[] ores = new int[2];
    private int[] constructionCoords;
    private int[] previousTeleports = new int[]{0, 0, 0, 0};
    private int recoilCharges;
    private int forgingCharges;
    private int blowpipeCharges;

    public int symbioticCharges = 0;
    private int runEnergy = 100;
    private int currentBankTab;
    private int currentGroupBankTab;
    private int interfaceId, walkableInterfaceId, multiIcon;
    private int dialogueActionId;

    @Getter
    public AfkCombatChecker afkCombatChecker = new AfkCombatChecker(this);


    @Getter @Setter
    public boolean holyGrailDroprate = true;

    @Getter
    @Setter
    private int curseRunes;

    @Getter
    @Setter
    private int soulRunes;

    @Getter
    @Setter
    private int cryptRunes;

    @Getter
    @Setter
    private int shadowRunes;

    @Getter
    @Setter
    private int wraithRunes;

    @Getter
    @Setter
    private int voidRunes;

    @Getter
    @Setter
    private int damageSalts;

    @Getter
    @Setter
    private int droprateSalts;

    @Getter
    @Setter
    private int criticalSalts;

    @Setter
    @Getter
    private BossFight bossFight;

    @Getter
    @Setter
    private boolean isInfinitePrayer = false;


    @Getter
    @Setter
    private boolean hasChangedGameMode = false;
    @Getter
    @Setter
    private int quiverMode;

    @Getter
    @Setter
    private int vorpalAmmo;

    @Getter
    @Setter
    private int bloodstainedAmmo;

    @Getter
    @Setter
    private int symbioteAmmo;

    @Getter
    @Setter
    private int netherAmmo;

    @Getter
    @Setter
    private int fireessence;

    @Getter
    @Setter
    private int wateressence;

    @Getter
    @Setter
    private int earthessence;

    @Getter
    @Setter
    private int monsteressence;

    @Getter
    @Setter
    private int slayeressence;


    @Getter
    @Setter
    private int beastEssence;


    @Getter
    @Setter
    private int corruptEssence;

    @Getter
    @Setter
    private int enchantedEssence;

    @Getter
    @Setter
    private int spectralEssence;

    @Getter
    @Setter
    private int dropRateTimerOld, doubleDDRTimer, doubleDMGTimer;

    private int  prayerRenewalPotionTimer;
    private int fireImmunity, fireDamageModifier;
    private int amountDonated;
    private int npckillcount;
    private int totalprestiges;
    private int totalsprees;
    private int wildernessLevel;
    private int fireAmmo;
    private int specialPercentage = 100;
    private int skullIcon = -1, skullTimer;
    private int teleblockTimer;
    private int dragonFireImmunity;
    private int poisonImmunity;
    /*
     * Fields
     */
    private int shadowState;
    private int effigy;
    private int dfsCharges;
    private int playerViewingIndex;
    private int staffOfLightEffect;
    private int minutesBonusExp = 0;
    private int minutesVotingDR = 0;
    private int minutesVotingDMG = 0;
    private int selectedGeSlot = -1;
    private int selectedGeItem = -1;
    private int geQuantity;
    private int gePricePerItem;
    private int selectedSkillingItem;
    private int currentBookPage;
    private int storedRuneEssence, storedPureEssence;
    private int trapsLaid;
    private int skillAnimation;
    private int houseServant;
    private int houseServantCharges;
    private int servantItemFetch;
    private int portalSelected;
    private int constructionInterface;
    private int buildFurnitureId;
    private int buildFurnitureX;
    private int buildFurnitureY;
    private int combatRingType;
    private int barrowsKilled;


    private int vodKilled;
    private int hovKilled;
    private int clueProgress;
    private int christmas2016;
    private int christmas2021;
    private int gamblingPass;
    private int gauntletRoom;
    private int newYear2017;
    private int lastTomed;
    private int selectedSkillingItemTwo;
    private int easter2017 = 0;

    private long moneyInPouch;
    private final UIMBank uimBank = new UIMBank(this);
    private boolean[] crossedObstacles = new boolean[7];
    private boolean processFarming;
    private boolean crossingObstacle;
    private boolean targeted;
    private boolean isBanking, noteWithdrawal, swapMode;
    private boolean regionChange, allowRegionChangePacket;
    private boolean isDying;
    private boolean isRunning = true, isResting;
    private boolean experienceLocked;
    private boolean clientExitTaskActive;
    private boolean drainingPrayer;
    private boolean shopping;
    private boolean settingUpCannon;
    private boolean hasVengeance;
    private boolean killsTrackerOpen;
    private boolean acceptingAid;
    private boolean autoRetaliate;
    private boolean autocast;
    private boolean specialActivated;
    private boolean isCoughing;
    private boolean playerLocked;
    private boolean recoveringSpecialAttack;
    private boolean soundsActive, musicActive;
    private boolean newPlayer;
    private boolean openBank;
    private boolean inActive;
    private boolean inConstructionDungeon;
    private boolean isBuildingMode;
    private boolean voteMessageSent;
    private boolean receivedStarter;
    private boolean fri13may16;
    private boolean toggledglobalmessages;
    private boolean spiritdebug;
    private boolean reffered;
    private boolean indung;
    private boolean doingClanBarrows;
    private boolean flying;
    private boolean canFly;
    private boolean ghostWalking;
    private boolean canGhostWalk;
    private boolean[] hween2016 = new boolean[7];
    private boolean doneHween2016;
    private boolean bonecrushEffect = true;
    private List<Integer> lootList;
    private boolean clickToTeleport;

    public RaidsConnector getRaidsOne() {
        return raids3;
    }
    public RaidsPartyConnector getRaidsPartyConnector() {
        return raidsPartyConnector;
    }
    private RaidsConnector raids3 = new RaidsConnector(this);
    public void setRaidsOne(RaidsConnector raids3) {
        this.raids3 = raids3;
    }

    /**
     * Progression Manager
     */

    private Map<Integer, Integer> uimBankItems = new LinkedHashMap<>();
    /**
     * Combiner
     */

    public void forceLeaveRaid(boolean redo) {
        this.getRaidsOne().getRaidsConnector().leaveCorruptRaid();
        this.getRaidsOne().getRaidsConnector().leave(true);
        RaidsParty partyy = this.getMinigameAttributes().getRaidsAttributes().getParty();
        this.moveTo(new Position(3266, 2872, 0));
        this.getMinigameAttributes().getRaidsAttributes().incrementDeaths();
        if (redo)
            partyy.remove(this, false, true);

    }
    private boolean chargingAttack;

    private AchievementHandler achievementHandler;

    public Player(PlayerSession playerIO) {
        super(GameSettings.DEFAULT_POSITION.copy());
        this.session = playerIO;
    }

    public boolean isPlaceholders() {
        return placeholders;
    }

    public void setPlaceholders(boolean placeholders) {
        this.placeholders = placeholders;
    }

    public List<GroundItem> getItemsInScene() {
        return itemsInScene;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public boolean isInsideRaids() {
        return insideRaids;
    }

    public void setInsideRaids(boolean insideRaids) {
        this.insideRaids = insideRaids;
    }

    public int getZombieRaidsKC() {
        return zombieRaidsKC;
    }

    public void setZombieRaidsKC(int zombieRaidsKC) {
        this.zombieRaidsKC = zombieRaidsKC;
    }

    public boolean isEnteredZombieRaids() {
        return enteredZombieRaids;
    }

    public void setEnteredZombieRaids(boolean enteredZombieRaids) {
        this.enteredZombieRaids = enteredZombieRaids;
    }


    public AchievementHandler getAchievements() {
        if (achievementHandler == null)
            achievementHandler = new AchievementHandler(this);
        return achievementHandler;
    }

    public int getAfkStallCount1() {
        return afkStallCount1;
    }

    public void setAfkStallCount1(int afkStallCount1) {
        this.afkStallCount1 = afkStallCount1;
    }

    public int getAfkStallCount2() {
        return afkStallCount2;
    }

    public void setAfkStallCount2(int afkStallCount2) {
        this.afkStallCount2 = afkStallCount2;
    }

    public int getAfkStallCount3() {
        return afkStallCount3;
    }

    public void setAfkStallCount3(int afkStallCount3) {
        this.afkStallCount3 = afkStallCount3;
    }


    public GoodieBag getGoodieBag() {
        return goodieBag;
    }

    public GoodiebagU getGoodieBagU() {
        return goodieBagU;
    }

    public String getSavedIp() {
        return savedIp;
    }

    public void setSavedIp(String savedIp) {
        this.savedIp = savedIp;
    }

    public boolean getHasPin() {
        return hasPin2;
    }

    public void setHasPin(boolean hasPin2) {
        this.hasPin2 = hasPin2;
    }

    public String getSavedPin() {
        return savedPin;
    }

    public void setSavedPin(String savedPin) {
        this.savedPin = savedPin;
    }

    public MysteryBoxOpener getMysteryBoxOpener() {
        return mysteryBoxOpener;
    }


    public void incrementDoorPicks(int doorPicks) {
        this.doorPicksLeft += doorPicks;
    }


    public Position lastTeleport;

    public GamblingInterface getGambling() {
        return gambling;
    }


    public DailyRewards getDailyRewards() {
        return dailyRewards;
    }

    @Getter
    private Streaking streak = new Streaking(this);

    public DonationDeals getDonationDeals() {
        return donationDeals;
    }

    public int getAmountDonatedToday() {
        return amountDonatedToday;
    }

    public void setAmountDonatedToday(int amount) {
        this.amountDonatedToday = amount;
    }

    public void incrementAmountDonatedToday(int amount) {
        this.amountDonatedToday += amount;
    }

    public double getEntriesCurrency() {
        return entriesCurrency;
    }

    public void setEntriesCurrency(double entriesCurrency) {
        this.entriesCurrency = entriesCurrency;
    }

    public int getDaysVoted() {
        return daysVoted;
    }

    public void setDaysVoted(int daysVoted) {
        this.daysVoted = daysVoted;
    }

    public int getTotalTimesClaimed() {
        return totalTimesClaimed;
    }

    public void setTotalTimesClaimed(int totalTimesClaimed) {
        this.totalTimesClaimed = totalTimesClaimed;
    }

    public int getLongestDaysVoted() {
        return longestDaysVoted;
    }

    public void setLongestDaysVoted(int longestDaysVoted) {
        this.longestDaysVoted = longestDaysVoted;
    }

    public long getTimeUntilNextReward() {
        return timeUntilNextReward;
    }

    public void setTimeUntilNextReward(long timeUntilNextReward) {
        this.timeUntilNextReward = timeUntilNextReward;
    }

    public long getMinsPlayed() {
        return minsPlayed;
    }

    public void setMinsPlayed(long minsPlayed) {
        this.minsPlayed = minsPlayed;
    }

    public String getLastVoted() {
        return lastVoted;
    }

    public void setLastVoted(String lastVoted) {
        this.lastVoted = lastVoted;
    }

    public int getCurrentVotingStreak() {
        return currentVotingStreak;
    }

    public void setCurrentVotingStreak(int currentVotingStreak) {
        this.currentVotingStreak = currentVotingStreak;
    }

    public String getTimeString() {
        long playTime = minsPlayed;
        int days = 0;
        int hours = 0;

        if (playTime >= 1440) {
            while (playTime >= 1440) {
                playTime -= 1440;
                days++;
            }
        }
        if (playTime >= 60) {
            while (playTime >= 60) {
                playTime -= 60;
                hours++;
            }
        }
        return days + ":" + hours + ":" + playTime;
    }

    public int getCurrentVotes() {
        return currentVotes;
    }

    @Getter
    private Upgrade upgrade = new Upgrade(this);

    public String getCurrentInstanceNpcName() {
        return currentInstanceNpcName;
    }

    public void setCurrentInstanceNpcName(String currentInstanceNpcName) {
        this.currentInstanceNpcName = currentInstanceNpcName;
    }

    public int getCurrentInstanceAmount() {
        return currentInstanceAmount;
    }

    public void setCurrentInstanceAmount(int currentInstanceAmount) {
        this.currentInstanceAmount = currentInstanceAmount;
    }

    public int getCurrentInstanceNpcId() {
        return currentInstanceNpcId;
    }

    public void setCurrentInstanceNpcId(int currentInstanceNpcId) {
        this.currentInstanceNpcId = currentInstanceNpcId;
    }



    public String getCurrentDailyTask() {
        return currentDailyTask;
    }

    public void setCurrentDailyTask(String currentDailyTask) {
        this.currentDailyTask = currentDailyTask;
    }

    public int getCurrentDailyTaskAmount() {
        return currentDailyTaskAmount;
    }

    public void setCurrentDailyTaskAmount(int currentDailyTaskAmount) {
        this.currentDailyTaskAmount = currentDailyTaskAmount;
    }

    public int getxPosDailyTask() {
        return xPosDailyTask;
    }

    public void setxPosDailyTask(int xPosDailyTask) {
        this.xPosDailyTask = xPosDailyTask;
    }

    public int getyPostDailyTask() {
        return yPostDailyTask;
    }

    public void setyPostDailyTask(int yPostDailyTask) {
        this.yPostDailyTask = yPostDailyTask;
    }

    public int getzPosDailyTask() {
        return zPosDailyTask;
    }

    public void setzPosDailyTask(int zPosDailyTask) {
        this.zPosDailyTask = zPosDailyTask;
    }

    public int getRewardDailyTask() {
        return rewardDailyTask;
    }

    public void setRewardDailyTask(int rewardDailyTask) {
        this.rewardDailyTask = rewardDailyTask;
    }




    public boolean isHasPlayerCompletedBossTask() {
        return hasPlayerCompletedBossTask;
    }

    public void setHasPlayerCompletedBossTask(boolean hasPlayerCompletedBossTask) {
        this.hasPlayerCompletedBossTask = hasPlayerCompletedBossTask;
    }

    public int getCurrentBossTaskAmount() {
        return currentBossTaskAmount;
    }

    public void setCurrentBossTaskAmount(int currentBossTaskAmount) {
        this.currentBossTaskAmount = currentBossTaskAmount;
    }

    public int getCurrentBossTask() {
        return currentBossTask;
    }

    public void setCurrentBossTask(int currentBossTask) {
        this.currentBossTask = currentBossTask;
    }

    public boolean isOpMode() {
        return opMode;
    }

    public void setOpMode(boolean newMode) {
        this.opMode = newMode;
    }

    public BonusXp getBonusXp() { // getter for that instance.
        return bonusXp;
    }

    public DonatorDiscount getdonatordiscount() { // getter for that instance.
        return donatordiscount;
    }




    public PlayerOwnedShopManager getPlayerOwnedShopManager() {
        return playerOwnedShopManager;
    }

    public String getMac() {
        return mac;
    }

    public Player setMac(String mac) {
        this.mac = mac;
        return this;
    }

    public String getUUID() {
        return uuid;
    }

    public Player setUUID(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public void appendDeath() {
        if (!isDying) {
            isDying = true;
            if (!controllerManager.appendDeath())
                return;
            TaskManager.submit(new PlayerDeathTask(this));
        }
    }

    @Override
    public long getConstitution() {
        return getSkillManager().getCurrentLevel(Skill.HITPOINTS);
    }



    @Override
    public Character setConstitution(int constitution) {
        if (isDying) {
            return this;
        }
        skillManager.setCurrentLevel(Skill.HITPOINTS, constitution);
        packetSender.sendSkill(Skill.HITPOINTS);
        if (getConstitution() <= 0 && !isDying)
            appendDeath();
        return this;
    }

    /*** BOOLEANS ***/
    private boolean[] unlockedLoyaltyTitles = new boolean[12];

    @Override
    public void heal(int amount) {
        boolean nexEffect = getEquipment().wearingNexAmours();
        int level = skillManager.getMaxLevel(Skill.HITPOINTS);
        int nexHp = level + 400;
        int currentlevel = skillManager.getCurrentLevel(Skill.HITPOINTS);

        if (currentlevel >= level && !nexEffect) {
            return;
        }
        if (currentlevel >= nexHp && nexEffect) {
            return;
        }

        if ((currentlevel + amount) >= (nexEffect ? nexHp : level)) {
            setConstitution(nexEffect ? nexHp : level);
        } else if ((currentlevel + amount) < (nexEffect ? nexHp : level)) {
            setConstitution(currentlevel + amount);
        }

        getSkillManager().updateSkill(Skill.HITPOINTS);
    }

    @Override
    public int getBaseAttack(CombatType type) {
        if (type == CombatType.RANGED)
            return skillManager.getCurrentLevel(Skill.RANGED);
        else if (type == CombatType.MAGIC)
            return skillManager.getCurrentLevel(Skill.MAGIC);
        return skillManager.getCurrentLevel(Skill.ATTACK);
    }

    @Override
    public int getBaseDefence(CombatType type) {
        if (type == CombatType.MAGIC)
            return skillManager.getCurrentLevel(Skill.MAGIC);
        return skillManager.getCurrentLevel(Skill.DEFENCE);
    }

    @Override
    public int getAttackSpeed() {
        int speed = 4;

            switch (equipment.get(Equipment.WEAPON_SLOT).getId())  {
            case 5000:
            case 5001:
            case 5002:
            case 5003:
            case 5004:
            case 5005:
            case 5006:
            case 5007:
            case 5008:
             //CINDER
            case 16024:
            case 13042:
            case 14915:
            //STONEWARD
            case 12930:
            case 16879:
            case 23144:
            //TIDEWAVE
            case 23145:
            case 23146:
            case 23033:
            //VERDANT
            case 18332:
            case 3745:
            case 3743:
                speed = 3;
                break;
            //FLAMESTRIKE
            case 23039:
            case 23064:
            case 23065:
            //WAVEBREAKER
            case 23066:
            case 23067:
            case 14065:
            //MAGMITE
            case 14004:
            case 18799:
            case 5010:
            //MOSSHEART
            case 23055:
            case 23056:
            case 13943:
            //SURGE
            case 13049:
            case 13056:
            case 13063:

            //MOSSBORN
            case 13015:
            case 13022:
            case 13029:
            //BLOODLUST
            case 441:
            case 450:
            case 451:
            //ENCHANTED 1/2
            case 16415:
            case 16416:
                speed = 3;
                break;
            //PULSAR
            case 13035:
            case 13964:
            case 21070:
                //MOLTAROK
            case 18629:
            case 17714:
            case 15485:
                //CLIFFBREAKER
            case 16867:
            case 16337:
            case 17620:
                //TIDAL
            case 20171:
            case 21023:
            case 16875:
                //MERCURIAL
            case 14005:
            case 18749:
            case 18748:
                //VIRE
            case 18638:
            case 12994:
            case 18009:
                //SEAFIRE
            case 8087:
            case 16871:
            case 14006:
                //TORRID
            case 13328:
            case 13329:
            case 13330:
                //WARDEN
            case 16137:
            case 16873:
            case 12902:
            case 20000:
            case 20001:
            case 20002:
            case 16417:
            case 16418:
            case 16419:
            case 16420:
            case 16421:
            case 17101:
            case 17102:
            case 17103:
            case 17104:
            case 17105:
            case 17106:
            case 17107:
            case 17108:
            case 17109:
            case 17110:
            case 17111:
            case 17112:
            case 17113:
            case 17114:
            case 1475:
            case 1481:
            case 1487:
            case 1493:
            case 1499:
            case 1505:
            case 1511:
            case 1517:
            case 1524:
                //XMAS
            case 1461:
            case 1462:
            case 1463:
            //ELE VOID WEPS
            case 1560:
            case 1561:
            case 1562:
            case 1563:
            case 1564:
            case 1565:
            case 1566:
            case 1567:
            case 1568:
            case 1570:
            case 1571:
            case 1572:
            case 1573:
            case 1574:
            case 1575:
            case 1576:
            case 1577:
            case 1578:
            case 1580:
            case 1581:
            case 1582:
            case 1583:
            case 1584:
            case 1585:
            case 1586:
            case 1587:
            case 1588:
            case 7784:
            case 7785:
            case 7786:

            case 750:
            case 751:
            case 752:
                //BLAZEN
                case 18974:
                case 18593:
                case 18972:
                speed = 2;
                break;
            case 711:
            case 712:
            case 713:
            case 714:
                speed = 3;
                break;
            case 2651:
            case 2653:
            case 2655:
                speed = 2;
                break;

                case 2086:
                case 2087:
                case 2088:
                case 2079:
                case 2080:
                case 2081:
                    speed = 2;
                    break;

                case 23188:
                    speed = 1;
                    break;
            }

        if (getLocation() == Locations.Location.CORRUPT_RAID_ROOM_1 || getLocation() == Locations.Location.CORRUPT_RAID_ROOM_2 || getLocation() == Locations.Location.CORRUPT_RAID_ROOM_3 || getLocation() == Locations.Location.VOID_RAID_ROOM_1 || getLocation() == Locations.Location.VOID_RAID_ROOM_2 || getLocation() == Locations.Location.VOID_RAID_ROOM_3) {
            switch (equipment.get(Equipment.WEAPON_SLOT).getId())  {
                //BLOODLUST
                case 441:
                case 450:
                case 451:
                    speed = 2;
                    break;

            }
        }


        if (CurseHandler.isActivated(this, CurseHandler.RIPTIDE)){
            speed -= 1;
        }
        if (CurseHandler.isActivated(this, CurseHandler.SWIFTTIDE)){
            speed -= 2;
        }
        if (speed < 1){
            speed = 1;
        }
        return speed;
    }
    public void addItemsToAny(Item... items) {
        int totalSlotsRequired = 0;
        for (Item item : items) {
            ItemDefinition itemDef = item.getDefinition();
            int requiredSlots = itemDef.isNoted() ? 1 : itemDef.isStackable() ? 1 : item.getAmount();
            totalSlotsRequired += requiredSlots;
        }

        if (getInventory().getFreeSlots() < totalSlotsRequired) {
            for (int index = 0; index < 7; index++) {
                if (!getBank(index).isFull()) {
                    for (Item item : items) {
                        ItemDefinition itemDef = item.getDefinition();
                        if (Prayer.isBone(item.getId())) {
                            sendMessage(itemDef.getName() + " has been placed in your bank, tab " + (index + 1) + ".");
                        }
                        getBank(index).add(item);
                    }
                    return;
                }
            }
        } else {
            for (Item item : items) {
                getInventory().add(item);
            }
        }
    }
    public void deleteselItems(Item... items) {
        if (getInventory().containsAll(items)) {
            for (Item item : items) {
                getInventory().delete(item);
            }
        } else {
            for (int index = 0; index < 7; index++) {
                if (getBank(index).containsAll(items)) {
                    for (Item item : items) {
                        ItemDefinition itemDef = item.getDefinition();
                        if (Prayer.isBone(item.getId())) {
                            sendMessage(itemDef.getName() + " has been removed from your bank, tab " + (index + 1) + ".");
                        }
                        getBank(index).delete(item);
                    }
                    return;
                }
            }
        }
    }


    public void addItemToAny(int itemid, int amount) {
        ItemDefinition item = ItemDefinition.forId(itemid);
        if (getInventory().getFreeSlots() < (item.isNoted() ? 1 : item.isStackable() ? 1 : amount)) {
            for (int index = 0; index < 7; index++) {
                if (!getBank(index).isFull())
                if (Prayer.isBone(itemid)) {

                    sendMessage(item.getName() + " has been placed in your bank, tab " + (index + 1) + ".");
                }
                return;
            }
        } else {
            getInventory().add(item.getId(), amount);
        }
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player)) {
            return false;
        }

        Player p = (Player) o;
        return p.getIndex() == getIndex() || p.getUsername().equals(username);
    }

    @Override
    public int getSize() {
        return 1;
    }


    @Override
    public void burnvictim(Character victim, CombatType type) {
        if (type == CombatType.MELEE || weapon == WeaponInterface.DART || weapon == WeaponInterface.KNIFE
                || weapon == WeaponInterface.THROWNAXE || weapon == WeaponInterface.JAVELIN) {
            CombatFactory.burnentity(victim, BurnData.getBurnType(equipment.get(Equipment.WEAPON_SLOT)));
        } else if (type == CombatType.RANGED) {
            CombatFactory.burnentity(victim,
                    BurnData.getBurnType(equipment.get(Equipment.AMMUNITION_SLOT)));
        }
    }

    public CombatStrategy getStrategy(int npc) {
        return CombatStrategies.getStrategy(npc);
    }

    @Override
    public CombatStrategy determineStrategy() {
        if (specialActivated && castSpell == null) {

            if (combatSpecial.getCombatType() == CombatType.MELEE) {
                return CombatStrategies.getDefaultMeleeStrategy();
            } else if (combatSpecial.getCombatType() == CombatType.RANGED) {
                setRangedWeaponData(RangedWeaponData.getData(this));
                return CombatStrategies.getDefaultRangedStrategy();
            } else if (combatSpecial.getCombatType() == CombatType.MAGIC) {
                return CombatStrategies.getDefaultMagicStrategy();
            }
        }

        if (castSpell != null || autocastSpell != null) {
            return CombatStrategies.getDefaultMagicStrategy();
        }

        RangedWeaponData data = RangedWeaponData.getData(this);
        if (data != null) {
            setRangedWeaponData(data);
            return CombatStrategies.getDefaultRangedStrategy();
        }

        return CombatStrategies.getDefaultMeleeStrategy();
    }

    public void process() {
        processGodMode();
        process.sequence();

    }

    public boolean isGodMode() {
        return godModeTimer > 0;
    }

    public void initGodMode() {
        if (!isGodMode()) {
            return;
        }
        packetSender.sendWalkableInterface(48300, true);
    }

    public void endGodMode() {
        godModeTimer = 0;
        for (Skill skill : Skill.values()) {
            skillManager.setCurrentLevel(skill, skillManager.getMaxLevel(skill));
        }
        setSpecialPercentage(100);
        sendMessage("Your god mode timer has run out.");
    }

    private void processGodMode() {
        if (!isGodMode()) {
            return;
        }
        skillManager.setCurrentLevel(Skill.PRAYER, 15000);
        skillManager.setCurrentLevel(Skill.ATTACK, 1500);
        skillManager.setCurrentLevel(Skill.STRENGTH, 1500);
        skillManager.setCurrentLevel(Skill.DEFENCE, 1500);
        skillManager.setCurrentLevel(Skill.RANGED, 1500);
        skillManager.setCurrentLevel(Skill.MAGIC, 1500);
        skillManager.setCurrentLevel(Skill.HITPOINTS, 15000);
        godModeTimer--;
        if (godModeTimer < 1) {
            endGodMode();
        }
    }



    public void dispose() {
        // save();
        packetSender.sendLogout();
    }

    public void save() {
        if (session.getState() != SessionState.LOGGED_IN && session.getState() != SessionState.LOGGING_OUT) {
            return;
        }
        PlayerSaving.save(this);
    }


    public boolean logout() {
        boolean debugMessage = true;
        int[] playerXP = new int[Skill.values().length];
        for (int i = 0; i < Skill.values().length; i++) {
            playerXP[i] = this.getSkillManager().getExperience(Skill.forId(i));
        }
        /*if (this.getGameMode() == GameMode.EXILED) {
            com.everythingrs.hiscores.Hiscores.update("B3MkcPlRDNcgBkUydRM5Q7D0HKS64tayioIjhzSNTG2064qz06SUIIMDwnf5efZKxYM8spq5",
                    "Normal Mode", this.getUsername(),
                    this.getRights().ordinal(), playerXP, debugMessage);
            this.getGameMode();
        }*/

        if (getCombatBuilder().isBeingAttacked()) {
            getPacketSender().sendMessage("You must wait a few seconds after being out of combat before doing this.");
            return false;
        }
        if (getConstitution() <= 0 || isDying || settingUpCannon || crossingObstacle) {
            getPacketSender().sendMessage("You cannot log out at the moment.");
            return false;
        }
        if (getLocation() != null && getLocation() == Locations.Location.DONORISLE) {
            World.sendMessage("@red@<shad=0>PLEASE REPORT TO LOYAL OR SHLIME " + asPlayer().getUsername());
            System.out.println("ATTEMPTING TO DUPE IN PLUNDER " + asPlayer().getUsername());
        }

        if (PartyService.playerIsInParty(this)) {
            if (PartyService.isPlayerPartyOwner(this)) {
                towerParty.disbandParty();
            } else {
                towerParty.leaveParty(this);
            }
        }

        if(groupSlayerParty != null) {
            groupSlayerParty.leave(this);
        }


        // new Thread(new Hiscores(this)).start();
        return true;
    }

    public void restart() {
        setStunDelay(0);
        setFreezeDelay(0);
       // setOverloadPotionTimer(0);
        setPrayerRenewalPotionTimer(0);
        setSpecialPercentage(100);
        setSpecialActivated(false);
        CombatSpecial.updateBar(this);
        setHasVengeance(false);
        setSkullTimer(0);
        setSkullIcon(0);
        setTeleblockTimer(0);
        setBurnDamage(0);
        setStaffOfLightEffect(0);
        performAnimation(new Animation(65535));
        WeaponInterfaces.assign(this, getEquipment().get(Equipment.WEAPON_SLOT));
        WeaponAnimations.update(this);
        PrayerHandler.deactivateAll(this);
        CurseHandler.deactivateAll(this);
        getEquipment().refreshItems();
        getInventory().refreshItems();
        if (!isGodMode()) {
            for (Skill skill : Skill.values()) {
                getSkillManager().setCurrentLevel(skill, getSkillManager().getMaxLevel(skill));
            }
        } else {
            this.getSkillManager().setCurrentLevel(Skill.HITPOINTS, this.getSkillManager().getMaxLevel(Skill.HITPOINTS));
        }
        setRunEnergy(100);
        setDying(false);
        getMovementQueue().setLockMovement(false).reset();

        getUpdateFlag().flag(Flag.APPEARANCE);
    }
    public void updateAppearance() {
        getUpdateFlag().flag(Flag.APPEARANCE);
    }

    public boolean busy() {
        return interfaceId > 0 || isBanking || shopping || trading.inTrade() || dueling.inDuelScreen || isResting;
    }


    public UIMBank getUimBank() {
        return uimBank;
    }

    public Map<Integer, Integer> getUimBankItems() {
        return uimBankItems;
    }

    public void setUimBankItems(Map<Integer, Integer> items) {
        uimBankItems = items;
    }

    public PlayerSession getSession() {
        return session;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Equipment getEquipment() {
        return equipment;
    }
    @Getter
    private int doorPicksLeft = 0;




    @Getter
    private VotingStreak votingStreak = new VotingStreak(this);

    @Getter
    private final Stopwatch instanceTimer = new Stopwatch();
    @Getter
    @Setter
    private int instanceID;
    @Getter
    private final TPInterface TPInterface = new TPInterface(this);

    @Getter
    private final SlotSystem slotSystem = new SlotSystem(this);

    @Getter
    public InstanceManager instanceManager = new InstanceManager(this);


    @Getter
    private final PresetManager PresetManager = new PresetManager(this);

    public boolean[] cosmeticVisible = new boolean[14];

    public void cosmLogin() {
        for (int i = 0; i < 14; i++) {
            cosmeticVisible[i] = true;
        }
    }

    public Equipment getCosmetics() {
        return cosmetics;
    }

    @Getter
    @Setter
    private Equipment cosmetics = new Equipment(this);

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    /*
     * Getters and setters
     */

    public DungeoneeringIronInventory getDungeoneeringIronInventory() {
        return dungeoneeringIronInventory;
    }

    public void setDungeoneeringStorage(DungeoneeringIronInventory dungeoneeringIronInventory) {
        this.dungeoneeringIronInventory = dungeoneeringIronInventory;
    }

    public DungeoneeringIronEquipment getDungeoneeringIronEquipment() {
        return dungeoneeringIronEquipment;
    }

    public void setDungeoneeringIronEquipment(DungeoneeringIronEquipment dungeoneeringIronEquipment) {
        this.dungeoneeringIronEquipment = dungeoneeringIronEquipment;
    }

    public Equipment getPreSetEquipment() {
        return preSetEquipment;
    }

    public void setPreSetEquipment(Equipment equipment) {
        this.preSetEquipment = equipment;
    }

    public PriceChecker getPriceChecker() {
        return priceChecker;
    }

    public String getUsername() {
        return username;
    }

    public Player setUsername(String username) {
        this.username = username;
        return this;
    }

    public Long getLongUsername() {
        return longUsername;
    }

    public Player setLongUsername(Long longUsername) {
        this.longUsername = longUsername;
        return this;
    }



    public String getPassword() {
        return password;
    }

    public Player setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String address) {
        this.emailAddress = address;
    }

    public void unlockPkTitles() {
        if (this.getPlayerKillingAttributes().getPlayerKills() >= 1) {
            LoyaltyProgramme.unlock(this, LoyaltyTitles.KILLER);
        }
        if (this.getPlayerKillingAttributes().getPlayerKills() >= 20) {
            LoyaltyProgramme.unlock(this, LoyaltyTitles.SLAUGHTERER);
        }
        if (this.getPlayerKillingAttributes().getPlayerKills() >= 50) {
            LoyaltyProgramme.unlock(this, LoyaltyTitles.GENOCIDAL);
        }
        if (this.getPlayerKillingAttributes().getPlayerKillStreak() >= 15) {
            LoyaltyProgramme.unlock(this, LoyaltyTitles.IMMORTAL);
        }
        PlayerPanel.refreshPanel(this);
    }

    public boolean viewingCosmeticTab = false;


    public void updateGearBonuses() {
        Misc.updateGearBonuses(this);
    }

    public void newStance() {
        WeaponAnimations.update(this);
        this.getUpdateFlag().flag(Flag.APPEARANCE);
        return;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public Player setHostAddress(String hostAddress) {
        this.hostAddress = hostAddress;
        return this;
    }

    public String getSerialNumber() {
        return serial_number;
    }

    public Player setSerialNumber(String serial_number) {
        this.serial_number = serial_number;
        return this;
    }

    public FrameUpdater getFrameUpdater() {
        return this.frameUpdater;
    }

    public PlayerRights getRights() {
        return rights;
    }

    //public DonatorRights getDonatorRights() {return donatorRights;}

    public Player setRights(PlayerRights rights) {
        this.rights = rights;
        return this;
    }

    //public Player setDonatorRights(DonatorRights donatorRights){
    //    this.donatorRights = donatorRights;
    //    return this;
    //}

    public BingoHandler getBingoHandler() {
        return bingoHandler;
    }
    private transient BingoHandler bingoHandler; // Marked as transient because we'll save/load it manually
    private int[][] savedBingoItems; // Temporary storage for saved items
    private boolean[][] savedBingoMarked; // Temporary storage for marked states

    public void initializeBingoHandler() {
        this.bingoHandler = new BingoHandler(this);
    }


    public void setSavedBingoItems(int[][] items) {
        this.savedBingoItems = items;
    }

    public void setSavedBingoMarked(boolean[][] marked) {
        this.savedBingoMarked = marked;
    }

    public int[][] getSavedBingoItems() {
        return savedBingoItems;
    }

    public boolean[][] getSavedBingoMarked() {
        return savedBingoMarked;
    }
    public ChatMessage getChatMessages() {
        return chatMessages;
    }

    public PacketSender getPacketSender() {
        return packetSender;
    }

    public PacketSender getPA() {
        return packetSender;
    }


    public SkillManager getSkillManager() {
        return skillManager;
    }

    public Appearance getAppearance() {
        return appearance;
    }

    public PlayerRelations getRelations() {
        return relations;
    }

    public PlayerKillingAttributes getPlayerKillingAttributes() {
        return playerKillingAttributes;
    }

    public PointsHandler getPointsHandler() {
        return pointsHandler;
    }

    public CurrencyPouch getCurrencyPouch() {
        return currencyPouch;
    }

    public boolean isImmuneToDragonFire() {
        return dragonFireImmunity > 0;
    }

    public int getDragonFireImmunity() {
        return dragonFireImmunity;
    }

    public void setDragonFireImmunity(int dragonFireImmunity) {
        this.dragonFireImmunity = dragonFireImmunity;
    }

    public void incrementDragonFireImmunity(int amount) {
        dragonFireImmunity += amount;
    }

    public void decrementDragonFireImmunity(int amount) {
        dragonFireImmunity -= amount;
    }

    public int getPoisonImmunity() {
        return poisonImmunity;
    }

    public void setPoisonImmunity(int poisonImmunity) {
        this.poisonImmunity = poisonImmunity;
    }

    public void incrementPoisonImmunity(int amount) {
        poisonImmunity += amount;
    }

    public void decrementPoisonImmunity(int amount) {
        poisonImmunity -= amount;
    }

    public boolean isAutoRetaliate() {
        return autoRetaliate;
    }

    public void setAutoRetaliate(boolean autoRetaliate) {
        this.autoRetaliate = autoRetaliate;
    }

    /**
     * @return the castSpell
     */
    public CombatSpell getCastSpell() {
        return castSpell;
    }

    /**
     * @param castSpell the castSpell to set
     */
    public void setCastSpell(CombatSpell castSpell) {
        this.castSpell = castSpell;
    }

    public CombatSpell getPreviousCastSpell() {
        return previousCastSpell;
    }

    public void setPreviousCastSpell(CombatSpell previousCastSpell) {
        this.previousCastSpell = previousCastSpell;
    }

    /**
     * @return the autocast
     */
    public boolean isAutocast() {
        return autocast;
    }

    /**
     * @param autocast the autocast to set
     */
    public void setAutocast(boolean autocast) {
        this.autocast = autocast;
    }

    public boolean couldHeal() {
        boolean nexEffect = getEquipment().wearingNexAmours();
        int level = skillManager.getMaxLevel(Skill.HITPOINTS);
        int nexHp = level + 400;
        int currentlevel = skillManager.getCurrentLevel(Skill.HITPOINTS);

        return (currentlevel < level || nexEffect) && (currentlevel < nexHp || !nexEffect);
    }

    /**
     * @return the skullTimer
     */
    public int getSkullTimer() {
        return skullTimer;
    }

    /**
     * @param skullTimer the skullTimer to set
     */
    public void setSkullTimer(int skullTimer) {
        this.skullTimer = skullTimer;
    }

    public void decrementSkullTimer() {
        skullTimer -= 50;
    }

    /**
     * @return the skullIcon
     */
    public int getSkullIcon() {
        return skullIcon;
    }

    /**
     * @param skullIcon the skullIcon to set
     */
    public void setSkullIcon(int skullIcon) {
        this.skullIcon = skullIcon;
    }

    /**
     * @return the teleblockTimer
     */
    public int getTeleblockTimer() {
        return teleblockTimer;
    }

    /**
     * @param teleblockTimer the teleblockTimer to set
     */
    public void setTeleblockTimer(int teleblockTimer) {
        this.teleblockTimer = teleblockTimer;
    }

    public void decrementTeleblockTimer() {
        teleblockTimer--;
    }

    /**
     * @return the autocastSpell
     */
    public CombatSpell getAutocastSpell() {
        return autocastSpell;
    }

    /**
     * @param autocastSpell the autocastSpell to set
     */
    public void setAutocastSpell(CombatSpell autocastSpell) {
        this.autocastSpell = autocastSpell;
    }

    /**
     * @return the specialPercentage
     */
    public int getSpecialPercentage() {
        return specialPercentage;
    }

    /**
     * @param specialPercentage the specialPercentage to set
     */
    public void setSpecialPercentage(int specialPercentage) {
        this.specialPercentage = specialPercentage;
    }

    /**
     * @return the fireAmmo
     */
    public int getFireAmmo() {
        return fireAmmo;
    }

    /**
     * @param fireAmmo the fireAmmo to set
     */
    public void setFireAmmo(int fireAmmo) {
        this.fireAmmo = fireAmmo;
    }

    public int getWildernessLevel() {
        return wildernessLevel;
    }

    public void setWildernessLevel(int wildernessLevel) {
        this.wildernessLevel = wildernessLevel;
    }

   // @Getter private final ItemExchangeInterface itemExchangeInterface = new ItemExchangeInterface(this);

    /**
     * @return the combatSpecial
     */
    public CombatSpecial getCombatSpecial() {
        return combatSpecial;
    }

    /**
     * @param combatSpecial the combatSpecial to set
     */
    public void setCombatSpecial(CombatSpecial combatSpecial) {
        this.combatSpecial = combatSpecial;
    }

    /**
     * @return the specialActivated
     */
    public boolean isSpecialActivated() {
        return specialActivated;
    }

    /**
     * @param specialActivated the specialActivated to set
     */
    public void setSpecialActivated(boolean specialActivated) {
        this.specialActivated = specialActivated;
    }

    public void decrementSpecialPercentage(int drainAmount) {
        this.specialPercentage -= drainAmount;

        if (specialPercentage < 0) {
            specialPercentage = 0;
        }
    }

    public void incrementSpecialPercentage(int gainAmount) {
        this.specialPercentage += gainAmount;

        if (specialPercentage > 100) {
            specialPercentage = 100;
        }
    }

    /**
     * @return the rangedAmmo
     */
    public RangedWeaponData getRangedWeaponData() {
        return rangedWeaponData;
    }

    public void setRangedWeaponData(RangedWeaponData rangedWeaponData) {
        this.rangedWeaponData = rangedWeaponData;
    }

    /**
     * @return the weapon.
     */
    public WeaponInterface getWeapon() {
        return weapon;
    }

    public void sendParallellInterfaceVisibility(int interfaceId, boolean visible) {
        if (this != null && this.getPacketSender() != null) {
            if (visible) {
                if (walkableInterfaceList.contains(interfaceId)) {
                    return;
                } else {
                    walkableInterfaceList.add(interfaceId);
                }
            } else {
                if (!walkableInterfaceList.contains(interfaceId)) {
                    return;
                } else {
                    walkableInterfaceList.remove((Object) interfaceId);
                }
            }

            getPacketSender().sendWalkableInterface(interfaceId, visible);
        }
    }
    /**
     * @param weapon the weapon to set.
     */
    public void setWeapon(WeaponInterface weapon) {
        this.weapon = weapon;
    }

    public ArrayList<Integer> walkableInterfaceList = new ArrayList<>();
    /**
     * @return the fightType
     */
    public FightType getFightType() {
        return fightType;
    }

    /**
     * @param fightType the fightType to set
     */
    public void setFightType(FightType fightType) {
        this.fightType = fightType;
    }

    public Bank[] getBanks() {return bankTabs;}

    public Bank getBank(int index) {
        return bankTabs[index];
    }

    public Player setBank(int index, Bank bank) {
        this.bankTabs[index] = bank;
        return this;
    }



    public boolean isAcceptAid() {
        return acceptingAid;
    }

    public void setAcceptAid(boolean acceptingAid) {
        this.acceptingAid = acceptingAid;
    }

    public Trading getTrading() {
        return trading;
    }

    public Dueling getDueling() {
        return dueling;
    }

    @Getter
    private final LeaderboardManager leaderboardManager = new LeaderboardManager(this);

    public CopyOnWriteArrayList<KillsEntry> getKillsTracker() {
        return killsTracker;
    }

    public CopyOnWriteArrayList<BoxesTracker.BoxEntry> getBoxTracker() {
        return boxTracker;
    }



   /* @Getter
    private final TitlesManager titlesManager = new TitlesManager(this);

    @Setter
    @Getter
    private Titles playerTitle = Titles.NONE;

    @Getter
    @Setter
    private String title = "";*/


    @Getter
    @Setter
    private List<DailyEntry> dailyEntries = new ArrayList<>();
    @Getter
    private final DailyTaskInterface dailyTaskInterface = new DailyTaskInterface(this);
    @Getter
    @Setter
    private long startDailyTimer;

    @Getter
    @Setter
    private boolean resetDaily;

    public CopyOnWriteArrayList<DropLogEntry> getDropLog() {
        return dropLog;
    }
    public void addDmgBoost (double amount) {
        setDmgBoost(getDmgBoost() + amount);
        sendMessage("@bla@<shad=0>You have now added @red@1% MaxHit Bonus. @bla@You now have @red@" + getDmgBoost() + "%");
    }
    public void addDrBoost(int amount) {
        setDrBoost(getDrBoost() + amount);
        sendMessage("@bla@<shad=0>You have now added @gre@1% Dr Bonus. @bla@You now have @gre@" + getDrBoost() + "%");
    }

    public void addUpgBoost(int amount) {
        setUpgBoost(getUpgBoost() + amount);
        sendMessage("@bla@<shad=0>You have now added @gre@1% Upg Bonus. @bla@You now have @gre@" + getUpgBoost() + "%");
    }


    public void setCritchance(int critchance) {
        this.critchance = critchance;
    }

    public void setDrBoost(int drBoost) {
        this.drBoost = drBoost;
    }

    public void setUpgBoost(int upgBoost) {
        this.upgBoost = upgBoost;
    }

    public int getDmgBoost() {
        return (int) dmgBoost;
    }

    private StarterTaskHandler starterTaskHandler;

    public StarterTaskHandler getStarterTasks() {
        if (starterTaskHandler == null)
            starterTaskHandler = new StarterTaskHandler(this);
        return starterTaskHandler;
    }

    private MediumTaskHandler mediumTaskHandler;

    public MediumTaskHandler getMediumTasks() {
        if (mediumTaskHandler == null)
            mediumTaskHandler = new MediumTaskHandler(this);
        return mediumTaskHandler;
    }


    private EliteTaskHandler eliteTaskHandler;

    public EliteTaskHandler getEliteTasks() {
        if (eliteTaskHandler == null)
            eliteTaskHandler = new EliteTaskHandler(this);
        return eliteTaskHandler;
    }

    private MasterTaskHandler masterTaskHandler;

    public MasterTaskHandler getMasterTasks() {
        if (masterTaskHandler == null)
            masterTaskHandler = new MasterTaskHandler(this);
        return masterTaskHandler;
    }
    public void setDmgBoost(double dmgBoost) {
        this.dmgBoost = dmgBoost;
    }

    public int getDrBoost() {
        return drBoost;
    }

    public int getCritchance() {
        return critchance;
    }
    public int getUpgBoost() {
        return upgBoost;
    }

    public WalkToTask getWalkToTask() {
        return walkToTask;
    }

    public void setWalkToTask(WalkToTask walkToTask) {
        this.walkToTask = walkToTask;
    }

    public MagicSpellbook getSpellbook() {
        return spellbook;
    }

    public Player setSpellbook(MagicSpellbook spellbook) {
        this.spellbook = spellbook;
        return this;
    }

    public Prayerbook getPrayerbook() {
        return prayerbook;
    }

    public Player setPrayerbook(Prayerbook prayerbook) {
        this.prayerbook = prayerbook;
        return this;
    }

    /**
     * The player's local players list.
     */
    public List<Player> getLocalPlayers() {
        return localPlayers;
    }

    /**
     * The player's local npcs list getter
     */
    public List<NPC> getLocalNpcs() {
        return localNpcs;
    }

    public CopyOnWriteArrayList<NPC> getNpcFacesUpdated() {
        return npc_faces_updated;
    }

    public int getInterfaceId() {

        return this.interfaceId;
    }
    public void write(Packet packet) {
    }

    public Player setInterfaceId(int interfaceId) {
        this.interfaceId = interfaceId;
        return this;
    }

    public boolean isDying() {
        return isDying;
    }

    public void setDying(boolean isDying) {
        this.isDying = isDying;
    }

    public int[] getForceMovement() {
        return forceMovement;
    }

    public Player setForceMovement(int[] forceMovement) {
        this.forceMovement = forceMovement;
        return this;
    }

    /**
     * @return the equipmentAnimation
     */
    public CharacterAnimations getCharacterAnimations() {
        return characterAnimations;
    }

    /**
     * @return the equipmentAnimation
     */
    public void setCharacterAnimations(CharacterAnimations equipmentAnimation) {
        this.characterAnimations = equipmentAnimation.clone();
    }

    public LoyaltyTitles getLoyaltyTitle() {
        return loyaltyTitle;
    }

    public void setLoyaltyTitle(LoyaltyTitles loyaltyTitle) {
        this.loyaltyTitle = loyaltyTitle;
    }

    public PlayerInteractingOption getPlayerInteractingOption() {
        return playerInteractingOption;
    }

    public Player setPlayerInteractingOption(PlayerInteractingOption playerInteractingOption) {
        this.playerInteractingOption = playerInteractingOption;
        return this;
    }

    public int getMultiIcon() {
        return multiIcon;
    }

    public Player setMultiIcon(int multiIcon) {
        this.multiIcon = multiIcon;
        return this;
    }

    // This is pretty much useless because walkableinterface is an array and is not saved on logout
    public int getWalkableInterfaceId() {
        return walkableInterfaceId;
    }

    public void setWalkableInterfaceId(int interfaceId2) {
        this.walkableInterfaceId = interfaceId2;
    }

    public boolean soundsActive() {
        return soundsActive;
    }

    public void setSoundsActive(boolean soundsActive) {
        this.soundsActive = soundsActive;
    }

    public boolean musicActive() {
        return musicActive;
    }

    public void setMusicActive(boolean musicActive) {
        this.musicActive = musicActive;
    }

    public BonusManager getBonusManager() {
        return bonusManager;
    }

    public int getRunEnergy() {
        return runEnergy;
    }

    public Player setRunEnergy(int runEnergy) {
        this.runEnergy = runEnergy;
        return this;
    }

    public Stopwatch getLastRunRecovery() {
        return lastRunRecovery;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public Player setRunning(boolean isRunning) {
        this.isRunning = isRunning;
        return this;
    }

    public boolean isResting() {
        return isResting;
    }

    public Player setResting(boolean isResting) {
        this.isResting = isResting;
        return this;
    }


    public long getSoulInPouch() {
        return soulInPouch;
    }

    public void setSoulInPouch(long soulInPouch) {
        this.soulInPouch = soulInPouch;
    }

    public int getSoulInPouchAsInt() {
        return soulInPouch > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) soulInPouch;
    }

    public boolean experienceLocked() {
        return experienceLocked;
    }

    public void setExperienceLocked(boolean experienceLocked) {
        this.experienceLocked = experienceLocked;
    }

    public boolean isClientExitTaskActive() {
        return clientExitTaskActive;
    }

    public void setClientExitTaskActive(boolean clientExitTaskActive) {
        this.clientExitTaskActive = clientExitTaskActive;
    }

    public ClanChat getCurrentClanChat() {
        return currentClanChat;
    }

    public Player setCurrentClanChat(ClanChat clanChat) {
        this.currentClanChat = clanChat;
        return this;
    }

    public String getClanChatName() {
        return clanChatName;
    }

    public Player setClanChatName(String clanChatName) {
        this.clanChatName = clanChatName;
        return this;
    }

    public String getYellHex() {
        return yellHex;
    }

    public Player setYellHex(String yellHex) {
        this.yellHex = yellHex;
        return this;
    }

    public Input getInputHandling() {
        return inputHandling;
    }

    public void setInputHandling(Input inputHandling) {
        this.inputHandling = inputHandling;
    }

    public boolean isDrainingPrayer() {
        return drainingPrayer;
    }

    public void setDrainingPrayer(boolean drainingPrayer) {
        this.drainingPrayer = drainingPrayer;
    }

    public Stopwatch getClickDelay() {
        return clickDelay;
    }

    public int[] getLeechedBonuses() {
        return leechedBonuses;
    }

    public Stopwatch getLastItemPickup() {
        return lastItemPickup;
    }

    public Stopwatch getLastSummon() {
        return lastSummon;
    }

    public BankSearchAttributes getBankSearchingAttribtues() {
        return bankSearchAttributes;
    }

    public GroupIronmanBank.GroupBankSearchAttributes getGroupBankSearchingAttribtues() {
        return groupBankSearchAttributes;
    }



    public BankPinAttributes getBankPinAttributes() {
        return bankPinAttributes;
    }

    public int getCurrentBankTab() {
        return currentBankTab;
    }

    public Player setCurrentBankTab(int tab) {
        this.currentBankTab = tab;
        return this;
    }

    public int getCurrentGroupBankTab() {
        return currentGroupBankTab;
    }

    public Player setCurrentGroupBankTab(int tab) {
        this.currentGroupBankTab = tab;
        return this;
    }

    public boolean isBanking() {
        return isBanking;
    }

    public Player setBanking(boolean isBanking) {
        this.isBanking = isBanking;
        return this;
    }

    public void setNoteWithdrawal(boolean noteWithdrawal) {
        this.noteWithdrawal = noteWithdrawal;
    }

    public boolean withdrawAsNote() {
        return noteWithdrawal;
    }

    public void setSwapMode(boolean swapMode) {
        this.swapMode = swapMode;
    }

    public boolean swapMode() {
        return swapMode;
    }

    public boolean isShopping() {
        return shopping;
    }

    public void setShopping(boolean shopping) {
        this.shopping = shopping;
    }

    public Shop getShop() {
        return shop;
    }

    public Player setShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public GameObject getInteractingObject() {
        return interactingObject;
    }

    public Player setInteractingObject(GameObject interactingObject) {
        this.interactingObject = interactingObject;
        return this;
    }

    public Item getInteractingItem() {
        return interactingItem;
    }

    public void setInteractingItem(Item interactingItem) {
        this.interactingItem = interactingItem;
    }

    public Dialogue getDialogue() {
        return this.dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public int getDialogueActionId() {
        return dialogueActionId;
    }

    public void setDialogueActionId(int dialogueActionId) {
        this.dialogueActionId = dialogueActionId;
    }

    public boolean isSettingUpCannon() {
        return settingUpCannon;
    }

    public void setSettingUpCannon(boolean settingUpCannon) {
        this.settingUpCannon = settingUpCannon;
    }

    public DwarfCannon getCannon() {
        return cannon;
    }

    public Player setCannon(DwarfCannon cannon) {
        this.cannon = cannon;
        return this;
    }


    public int getPrayerRenewalPotionTimer() {
        return prayerRenewalPotionTimer;
    }

    public void setPrayerRenewalPotionTimer(int prayerRenewalPotionTimer) {
        this.prayerRenewalPotionTimer = prayerRenewalPotionTimer;
    }

    public Stopwatch getSpecialRestoreTimer() {
        return specialRestoreTimer;
    }

    public boolean[] getUnlockedLoyaltyTitles() {
        return unlockedLoyaltyTitles;
    }

    public void setUnlockedLoyaltyTitles(boolean[] unlockedLoyaltyTitles) {
        this.unlockedLoyaltyTitles = unlockedLoyaltyTitles;
    }

    public void setUnlockedLoyaltyTitle(int index) {
        unlockedLoyaltyTitles[index] = true;
    }

    public Stopwatch getEmoteDelay() {
        return emoteDelay;
    }

    public MinigameAttributes getMinigameAttributes() {
        return minigameAttributes;
    }

    @Getter @Setter
    private int SupermanKills;

    @Getter
    private HolidayTaskData holidayTaskData = new HolidayTaskData();

    @Getter
    private HolidayTaskHandler holidayTaskHandler = new HolidayTaskHandler(this);

    @Getter
    @Setter
    private int holidayPoints;

    @Getter
    @Setter
    private int easyXmasTasks;

    @Getter
    @Setter
    private int souls;

    @Getter
    @Setter
    private int mediumXmasTasks;

    @Getter
    @Setter
    private int eliteXmasTasks;

    @Getter
    private HolidayTaskInterface holidayTaskInterface = new HolidayTaskInterface(this);


    @Getter @Setter
    private int HulkKills;
    public void increaseHulkKills(int amount) {
        HulkKills += amount;
    }

    @Getter @Setter
    private int NarniaRaids;
    public void increaseNarniaRaids(int amount) {
        NarniaRaids += amount;
    }

    @Getter @Setter
    private int ExecRaids;
    public void increaseExecRaids(int amount) {
        ExecRaids += amount;
    }

    @Getter @Setter
    private int GodRaids;
    public void increaseGodRaids(int amount) {
        GodRaids += amount;
    }

    @Getter @Setter
    private int CelestialRaids;
    public void increaseCelestialRaids(int amount) {
        CelestialRaids += amount;
    }


    @Getter @Setter
    private int DivineRaids;
    public void increaseDivineRaids(int amount) {
        DivineRaids += amount;
    }


    @Getter @Setter
    private int EnlightRaids;
    public void increaseEnlightRaids(int amount) {
        EnlightRaids += amount;
    }


    @Getter @Setter
    private int GalacticRaids;
    public void increaseGalacticRaids(int amount) {
        GalacticRaids += amount;
    }


    @Getter @Setter
    private int UniversalRaids;
    public void increaseUniversalRaids(int amount) {
        UniversalRaids += amount;
    }


    public void incrementCorruptCompletions(int amount) {
        CorruptCompletions += amount;
    }
    public void incrementMediumCorruptCompletions(int amount) {
        CorruptMediumCompletions += amount;
    }
    public void incrementHardCorruptCompletions(int amount) {
        CorruptHardCompletions += amount;
    }



    public void incrementVoidCompletions(int amount) {
        VoidCompletions += amount;
    }
    public void incrementMediumVoidCompletions(int amount) {
        VoidMediumCompletions += amount;
    }
    public void incrementHardVoidCompletions(int amount) {
        VoidHardCompletions += amount;
    }


    @Getter @Setter
    private int CorruptRaidDifficulty;

    @Getter @Setter
    private int VoidRaidDifficulty;

    @Getter @Setter
    private int COECompletions;
    @Getter @Setter
    private int CorruptCompletions;
    @Getter @Setter
    private int CorruptMediumCompletions;
    @Getter @Setter
    private int CorruptHardCompletions;




    @Getter @Setter
    private int VoidCompletions;
    @Getter @Setter
    private int VoidMediumCompletions;
    @Getter @Setter
    private int VoidHardCompletions;


    public void incrementToyCompletions(int amount) {
        ToyCompletions += amount;
    }
    public void incrementToyHardCompletions(int amount) {
        ToyHardCompletions += amount;
    }
    public void incrementToyInsaneCompletions(int amount) {
        ToyInsaneCompletions += amount;
    }

    @Getter @Setter
    private int ToyCompletions;
    @Getter @Setter
    private int ToyHardCompletions;
    @Getter @Setter
    private int ToyInsaneCompletions;

    public int getFireImmunity() {
        return fireImmunity;
    }

    public Player setFireImmunity(int fireImmunity) {
        this.fireImmunity = fireImmunity;
        return this;
    }

    public int getFireDamageModifier() {
        return fireDamageModifier;
    }

    public Player setFireDamageModifier(int fireDamageModifier) {
        this.fireDamageModifier = fireDamageModifier;
        return this;
    }

    public boolean hasVengeance() {
        return hasVengeance;
    }

    public void setHasVengeance(boolean hasVengeance) {
        this.hasVengeance = hasVengeance;
    }

    public Stopwatch getLastVengeance() {
        return lastVengeance;
    }

    public Stopwatch getTolerance() {
        return tolerance;
    }

    public boolean isTargeted() {
        return targeted;
    }

    public void setTargeted(boolean targeted) {
        this.targeted = targeted;
    }

    public Stopwatch getLastYell() {
        return lastYell;
    }

    public Stopwatch getLastVoteClaim() {
        return lastVoteClaim;
    }

    public int getAmountDonated() {
        return amountDonated;
    }

    public void incrementAmountDonated(int amountDonated) {
        this.amountDonated += amountDonated;
    }
    public int getNPCKILLCount() {
        return npckillcount;
    }

    public long getTotalPlayTime() {
        return totalPlayTime;
    }

    public void setTotalPlayTime(long amount) {
        this.totalPlayTime = amount;
    }

    public Stopwatch getRecordedLogin() {
        return recordedLogin;
    }

    public Player setRegionChange(boolean regionChange) {
        this.regionChange = regionChange;
        return this;
    }

    public boolean isChangingRegion() {
        return this.regionChange;
    }

    public boolean isAllowRegionChangePacket() {
        return allowRegionChangePacket;
    }

    public void setAllowRegionChangePacket(boolean allowRegionChangePacket) {
        this.allowRegionChangePacket = allowRegionChangePacket;
    }

    public boolean isKillsTrackerOpen() {
        return killsTrackerOpen;
    }

    public void setKillsTrackerOpen(boolean killsTrackerOpen) {
        this.killsTrackerOpen = killsTrackerOpen;
    }


    public int getShadowState() {
        return shadowState;
    }

    public void setShadowState(int shadow) {
        this.shadowState = shadow;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
    public void setXpMode(XpMode xpmode) {
        this.xpmode = xpmode;
    }
    public XpMode getXpmode() {
        return xpmode;
    }


    public void setBoostMode(BoostMode boostmode) {
        this.boostmode = boostmode;
    }
    public BoostMode getBoostMode() {
        return boostmode;
    }

    @Getter @Setter
    private int starterDropRate;

    @Getter @Setter
    private int starterDamageBonus;

    public int getGameModeIconId() {
        if (this.getGameMode() == GameMode.IRONMAN) {
            return 840;
        }
        if (this.getGameMode() == GameMode.EXILED) {
            return 838;
        }
        if (this.getGameMode() == GameMode.GROUP_IRON) {
            return 1509;
        }
        return -1;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public boolean isPlayerLocked() {
        return playerLocked;
    }

    public Player setPlayerLocked(boolean playerLocked) {
        this.playerLocked = playerLocked;
        return this;
    }

    public Stopwatch getSqlTimer() {
        return sqlTimer;
    }

    public Stopwatch getFoodTimer() {
        return foodTimer;
    }

    public Stopwatch getPotionTimer() {
        return potionTimer;
    }

    public Item getUntradeableDropItem() {
        return untradeableDropItem;
    }

    public void setUntradeableDropItem(Item untradeableDropItem) {
        this.untradeableDropItem = untradeableDropItem;
    }

    public boolean isRecoveringSpecialAttack() {
        return recoveringSpecialAttack;
    }

    public void setRecoveringSpecialAttack(boolean recoveringSpecialAttack) {
        this.recoveringSpecialAttack = recoveringSpecialAttack;
    }

    public CombatType getLastCombatType() {
        return lastCombatType;
    }

    public void setLastCombatType(CombatType lastCombatType) {
        this.lastCombatType = lastCombatType;
    }

    public int getEffigy() {
        return this.effigy;
    }

    public void setEffigy(int effigy) {
        this.effigy = effigy;
    }

    public int getDfsCharges() {
        return dfsCharges;
    }

    public void setDfsCharges(int amount) {
        this.dfsCharges = amount;
    }

    public void incrementDfsCharges(int amount) {
        this.dfsCharges += amount;
    }

    public void setNewPlayer(boolean newPlayer) {
        this.newPlayer = newPlayer;
    }

    public boolean newPlayer() {
        return newPlayer;
    }

    public Stopwatch getLogoutTimer() {
        return lougoutTimer;
    }

    public Player setUsableObject(int index, Object usableObject) {
        this.usableObject[index] = usableObject;
        return this;
    }

    public Object[] getUsableObject() {
        return usableObject;
    }

    public Player setUsableObject(Object[] usableObject) {
        this.usableObject = usableObject;
        return this;
    }

    public int getPlayerViewingIndex() {
        return playerViewingIndex;
    }

    public void setPlayerViewingIndex(int playerViewingIndex) {
        this.playerViewingIndex = playerViewingIndex;
    }

    public boolean hasStaffOfLightEffect() {
        return staffOfLightEffect > 0;
    }

    public int getStaffOfLightEffect() {
        return staffOfLightEffect;
    }

    public void setStaffOfLightEffect(int staffOfLightEffect) {
        this.staffOfLightEffect = staffOfLightEffect;
    }

    public void decrementStaffOfLightEffect() {
        this.staffOfLightEffect--;
    }

    public boolean openBank() {
        return openBank;
    }

    public void setOpenBank(boolean openBank) {
        this.openBank = openBank;
    }

    public int getMinutesBonusExp() {
        return minutesBonusExp;
    }

    public void setMinutesBonusExp(int minutesBonusExp, boolean add) {
        this.minutesBonusExp = (add ? this.minutesBonusExp + minutesBonusExp : minutesBonusExp);
    }

    public int getMinutesVotingDR() {
        return minutesVotingDR;
    }

    public void setMinutesVotingDR(int minutesVotingDR, boolean add) {
        this.minutesVotingDR = (add ? this.minutesVotingDR + minutesVotingDR : minutesVotingDR);
    }

    public int getMinutesVotingDMG() {
        return minutesVotingDMG;
    }

    public void setMinutesVotingDMG(int minutesVotingDMG, boolean add) {
        this.minutesVotingDMG = (add ? this.minutesVotingDMG + minutesVotingDMG : minutesVotingDMG);
    }
    public Dissolving getDissolving() {
        return dissolving;
    }

    public void setInactive(boolean inActive) {
        this.inActive = inActive;
    }

    public boolean isInActive() {
        return inActive;
    }

    public int getSelectedGeItem() {
        return selectedGeItem;
    }

    public void setSelectedGeItem(int selectedGeItem) {
        this.selectedGeItem = selectedGeItem;
    }

    public int getGeQuantity() {
        return geQuantity;
    }

    public void setGeQuantity(int geQuantity) {
        this.geQuantity = geQuantity;
    }

    public int getGePricePerItem() {
        return gePricePerItem;
    }

    public void setGePricePerItem(int gePricePerItem) {
        this.gePricePerItem = gePricePerItem;
    }

    public GrandExchangeSlot[] getGrandExchangeSlots() {
        return grandExchangeSlots;
    }

    public void setGrandExchangeSlots(GrandExchangeSlot[] GrandExchangeSlots) {
        this.grandExchangeSlots = GrandExchangeSlots;
    }

    public void setGrandExchangeSlot(int index, GrandExchangeSlot state) {
        this.grandExchangeSlots[index] = state;
    }

    public int getSelectedGeSlot() {
        return selectedGeSlot;
    }

    public void setSelectedGeSlot(int slot) {
        this.selectedGeSlot = slot;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
    }

    public int getSelectedSkillingItem() {
        return selectedSkillingItem;
    }

    public void setSelectedSkillingItem(int selectedItem) {
        this.selectedSkillingItem = selectedItem;
    }

    public int getSelectedSkillingItemTwo() {
        return selectedSkillingItemTwo;
    }

    public void setSelectedSkillingItemTwo(int selectedItem) {
        this.selectedSkillingItemTwo = selectedItem;
    }

    public boolean shouldProcessFarming() {
        return processFarming;
    }

    public void setProcessFarming(boolean processFarming) {
        this.processFarming = processFarming;
    }

    public Pouch getSelectedPouch() {
        return selectedPouch;
    }

    public void setSelectedPouch(Pouch selectedPouch) {
        this.selectedPouch = selectedPouch;
    }

    public int getCurrentBookPage() {
        return currentBookPage;
    }

    public void setCurrentBookPage(int currentBookPage) {
        this.currentBookPage = currentBookPage;
    }

    public int getStoredRuneEssence() {
        return storedRuneEssence;
    }

    public void setStoredRuneEssence(int storedRuneEssence) {
        this.storedRuneEssence = storedRuneEssence;
    }

    public int getStoredPureEssence() {
        return storedPureEssence;
    }

    public void setStoredPureEssence(int storedPureEssence) {
        this.storedPureEssence = storedPureEssence;
    }

    public int getTrapsLaid() {
        return trapsLaid;
    }

    public void setTrapsLaid(int trapsLaid) {
        this.trapsLaid = trapsLaid;
    }

    public boolean isCrossingObstacle() {
        return crossingObstacle;
    }

    public Player setCrossingObstacle(boolean crossingObstacle) {
        this.crossingObstacle = crossingObstacle;
        return this;
    }

    public boolean[] getCrossedObstacles() {
        return crossedObstacles;
    }

    public void setCrossedObstacles(boolean[] crossedObstacles) {
        this.crossedObstacles = crossedObstacles;
    }

    public boolean[] getHween2016All() {
        return hween2016;
    }

    public void setHween2016All(boolean[] boolAray) {
        this.hween2016 = boolAray;
    }

    public int getChristmas2016() {
        return christmas2016;
    }

    public int getGauntletRoom(){return gauntletRoom;}

    public int getChristmas2021() { return christmas2021;}

    public int getGamblingPass() {return gamblingPass;}


    public boolean getCrossedObstacle(int i) {
        return crossedObstacles[i];
    }

    public boolean getHween2016(int i) {
        return hween2016[i];
    }


    public int getEaster2017() {
        return this.easter2017;
    }

    public void setEaster2017(int easter2017) {
        this.easter2017 = easter2017;
    }

    public Player setCrossedObstacle(int i, boolean completed) {
        crossedObstacles[i] = completed;
        return this;
    }

    public Player setHween2016(int i, boolean completed) {
        hween2016[i] = completed;
        return this;
    }

    public int getSkillAnimation() {
        return skillAnimation;
    }

    public Player setSkillAnimation(int animation) {
        this.skillAnimation = animation;
        return this;
    }

    public int[] getOres() {
        return ores;
    }

    public void setOres(int[] ores) {
        this.ores = ores;
    }

    public Position getResetPosition() {
        return resetPosition;
    }

    public void setResetPosition(Position resetPosition) {
        this.resetPosition = resetPosition;
    }

    public Slayer getSlayer() {
        return slayer;
    }

    public Summoning getSummoning() {
        return summoning;
    }

    public Farming getFarming() {
        return farming;
    }

    public boolean inConstructionDungeon() {
        return inConstructionDungeon;
    }

    public void setInConstructionDungeon(boolean inConstructionDungeon) {
        this.inConstructionDungeon = inConstructionDungeon;
    }

    public int getHouseServant() {
        return houseServant;
    }

    public void setHouseServant(int houseServant) {
        this.houseServant = houseServant;
    }

    public int getHouseServantCharges() {
        return this.houseServantCharges;
    }

    public void setHouseServantCharges(int houseServantCharges) {
        this.houseServantCharges = houseServantCharges;
    }

    public void incrementHouseServantCharges() {
        this.houseServantCharges++;
    }

    public int getServantItemFetch() {
        return servantItemFetch;
    }

    public void setServantItemFetch(int servantItemFetch) {
        this.servantItemFetch = servantItemFetch;
    }

    public int getPortalSelected() {
        return portalSelected;
    }

    public void setPortalSelected(int portalSelected) {
        this.portalSelected = portalSelected;
    }

    public boolean isBuildingMode() {
        return this.isBuildingMode;
    }

    public void setIsBuildingMode(boolean isBuildingMode) {
        this.isBuildingMode = isBuildingMode;
    }

    public int[] getConstructionCoords() {
        return constructionCoords;
    }

    public void setConstructionCoords(int[] constructionCoords) {
        this.constructionCoords = constructionCoords;
    }

    public int getBuildFurnitureId() {
        return this.buildFurnitureId;
    }

    public void setBuildFuritureId(int buildFuritureId) {
        this.buildFurnitureId = buildFuritureId;
    }

    public void setchristmas2016(int christmas2016) {
        this.christmas2016 = christmas2016;
    }

    public void setChristmas2021(int christmas2021){
        this.christmas2021 = christmas2021;
    }

    public void setGamblingPass(int gamblingPass) {this.gamblingPass = gamblingPass;}

    public void setGauntletRoom(int gauntletRoom){
        this.gauntletRoom = gauntletRoom;
    }

    public int getLastTomed() {
        return this.lastTomed;
    }

    public void setLastTomed(int lastTomed) {
        this.lastTomed = lastTomed;
    }

    public int getNewYear2017() {
        return this.newYear2017;
    }

    public void setNewYear2017(int newYear2017) {
        this.newYear2017 = newYear2017;
    }

    public int getBuildFurnitureX() {
        return this.buildFurnitureX;
    }

    public void setBuildFurnitureX(int buildFurnitureX) {
        this.buildFurnitureX = buildFurnitureX;
    }

    public int getBuildFurnitureY() {
        return this.buildFurnitureY;
    }

    public void setBuildFurnitureY(int buildFurnitureY) {
        this.buildFurnitureY = buildFurnitureY;
    }

    public int getCombatRingType() {
        return this.combatRingType;
    }

    public void setCombatRingType(int combatRingType) {
        this.combatRingType = combatRingType;
    }

    public Room[][][] getHouseRooms() {
        return houseRooms;
    }

    public void setHouseRooms(Room[][][] houseRooms) {
        this.houseRooms = houseRooms;
    }

    public ArrayList<Portal> getHousePortals() {
        return housePortals;
    }

    public void setHousePortals(ArrayList<Portal> housePortals) {
        this.housePortals = housePortals;
    }

    public ArrayList<HouseFurniture> getHouseFurniture() {
        return houseFurniture;
    }

    public void setHouseFurniture(ArrayList<HouseFurniture> houseFurniture) {
        this.houseFurniture = houseFurniture;

    }

    public int getConstructionInterface() {
        return this.constructionInterface;
    }

    public void setConstructionInterface(int constructionInterface) {
        this.constructionInterface = constructionInterface;
    }

    public int[] getBrawlerChargers() {
        return this.brawlerCharges;
    }

    public void setBrawlerCharges(int[] brawlerCharges) {
        this.brawlerCharges = brawlerCharges;
    }


    public int getstackCharges() {
        return this.stackCharges;
    }

    public int setstackCharges(int stackCharges) {
        return this.stackCharges = stackCharges;
    }

    public int[] getAncientArmourCharges() {
        return this.ancientArmourCharges;
    }

    public void setAncientArmourCharges(int[] ancientArmourCharges) {
        this.ancientArmourCharges = ancientArmourCharges;
    }

    public int getRecoilCharges() {
        return this.recoilCharges;
    }

    public int setRecoilCharges(int recoilCharges) {
        return this.recoilCharges = recoilCharges;
    }

    public int getBlowpipeCharges() {
        return this.blowpipeCharges;
    }

    public int setBlowpipeCharges(int blowpipeCharges) {
        return this.blowpipeCharges = blowpipeCharges;
    }

    public boolean voteMessageSent() {
        return this.voteMessageSent;
    }

    public void setVoteMessageSent(boolean voteMessageSent) {
        this.voteMessageSent = voteMessageSent;
    }

    public boolean didReceiveStarter() {
        return receivedStarter;
    }

    public boolean didFriday13May2016() {
        return fri13may16;
    }

    public boolean isFlying() {
        return flying;
    }

    public void setFlying(boolean flying) {
        this.flying = flying;
    }

    public boolean canFly() {
        return canFly;
    }

    public boolean isGhostWalking() {
        return ghostWalking;
    }

    public void setGhostWalking(boolean ghostWalking) {
        this.ghostWalking = ghostWalking;
    }

    public boolean canGhostWalk() {
        return canGhostWalk;
    }

    public boolean doneHween2016() {
        return doneHween2016;
    }

    public boolean toggledGlobalMessages() {
        return toggledglobalmessages;
    }

    public boolean isSpiritDebug() {
        return spiritdebug;
    }

    public void setSpiritDebug(boolean spiritdebug) {
        this.spiritdebug = spiritdebug;
    }

    public boolean isInDung() {
        return indung;
    }

    public void setInDung(boolean indung) {
        this.indung = indung;
    }

    public boolean gotReffered() {
        return reffered;
    }

    public boolean checkItem(int slot, int id) {
        return this.getEquipment().getItems()[slot].getId() == id;
    } // (player.getEquipment().getItems()[Equipment.HEAD_SLOT].getId() == 15492

    public void setReceivedStarter(boolean receivedStarter) {
        this.receivedStarter = receivedStarter;
    }

    public void setFriday13May2016(boolean fri13may16) {
        this.fri13may16 = fri13may16;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public void setCanGhostWalk(boolean canGhostWalk) {
        this.canGhostWalk = canGhostWalk;
    }

    public void setDoneHween2016(boolean isDone) {
        this.doneHween2016 = isDone;
    }

    public void setToggledGlobalMessages(boolean toggledglobalmessages) {
        this.toggledglobalmessages = toggledglobalmessages;
    }

    public void setReffered(boolean reffered) {
        this.reffered = reffered;
    }

    public boolean doingClanBarrows() {
        return doingClanBarrows;
    }

    public void setDoingClanBarrows(boolean doingClanBarrows) {
        this.doingClanBarrows = doingClanBarrows;
    }

    public int getBarrowsKilled() {
        return barrowsKilled;
    }

    public void setBarrowsKilled(int barrowsKilled) {
        this.barrowsKilled = barrowsKilled;
    }




    @Getter @Setter
    public boolean sacrificedmastersoul;

    @Getter
    @Setter
    private Summon summon;


    @Getter
    @Setter
    public int marinaTaskID = 0;

    @Getter
    @Setter
    public int dailyMarinaTasks = 0;

    @Getter
    @Setter
    public int gaiaCrystalStage;

    @Getter
    @Setter
    public int lavaCrystalStage;

    @Getter
    @Setter
    public int aquaCrystalStage;


    @Getter
    @Setter
    public int voidCrystalStage;

    @Getter
    @Setter
    public int ExecStage;

    @Getter
    @Setter
    public int FrozenSteps;

    @Getter
    @Setter
    public int InfernalSteps;

    @Getter
    @Setter
    public int EarthSteps;

    @Getter
    @Setter
    public int DailyBattles;

    @Getter
    @Setter
    public int InBattle;

    @Getter
    @Setter
    public boolean emberspecialrunning;

    @Getter
    @Setter
    public boolean nodeSpecialRunning;


    @Getter
    @Setter
    public boolean iceBornSpecialRunning;

    @Getter
    @Setter
    public boolean healingspecialrunning;


    @Getter
    @Setter
    public boolean levelupRunning;



    @Getter
    @Setter
    public boolean forgedGauntletWeapon;

    @Getter
    @Setter
    public boolean tier2GauntletWeapon;

    @Getter
    @Setter
    public boolean tier3GauntletWeapon;

    @Getter
    @Setter
    public boolean crystalRunning = false;

    @Getter
    @Setter
    public int DodgeStage;

    @Getter
    @Setter
    public int BrimstoneStage;

    @Getter
    @Setter
    public int EverthornStage;

    @Getter
    @Setter
    public int BasiliskStage;

    @Getter
    @Setter
    public int safearea;

    public int getVodKilled() {
        return vodKilled;
    }

    public void setVodKilled(int vodK) {
        this.vodKilled = vodK;
    }

    public int getHovKilled() {
        return hovKilled;
    }

    public void setHovKilled(int hovK) {
        this.hovKilled = hovK;
    }

    public int getClueProgress() {
        return clueProgress;
    }

    public void setClueProgress(int clueProgress) {
        this.clueProgress = clueProgress;
    }

    public boolean isChargingAttack() {
        return chargingAttack;
    }

    public String getStrippedJewelryName() {
        return strippedJewelryName;
    }

    public void setStrippedJewelryName(String strippedJewelryName) {
        this.strippedJewelryName = strippedJewelryName;
    }

    public int getForgingCharges() {
        return forgingCharges;
    }

    public int setForgingCharges(int forgingCharges) {
        return this.forgingCharges = forgingCharges;
    }

    public boolean getBonecrushEffect() {
        return bonecrushEffect;
    }

    public void setBonecrushEffect(boolean bonecrushEffect) {
        this.bonecrushEffect = bonecrushEffect;
    }

    public int[] getPreviousTeleports() {
        return previousTeleports;
    }

    public void setPreviousTeleports(int[] previousTeleports) {
        this.previousTeleports = previousTeleports;
    }

    public long[] getTaskReceivedTimes() {
        return taskReceivedTime;
    }

    public void setTaskReceivedTimes(long[] taskReceivedTime) {
        this.taskReceivedTime = taskReceivedTime;
    }

    public int getPreviousTeleportsIndex(int index) {
        return previousTeleports[index];
    }

    public void setPreviousTeleportsIndex(int index, int previousTeleport) {
        this.previousTeleports[index] = previousTeleport;
    }

    public String getYellTitle() {
        return yellTitle;
    }

    public void setYellTitle(String yellTitle) {
        if (yellTitle.equalsIgnoreCase("null")) {
            this.yellTitle = "";
        }
        this.yellTitle = yellTitle;
    }

    public List<Integer> getLootList() {
        return lootList;
    }

    public void setLootList(List<Integer> lootList) {
        this.lootList = lootList;
    }

    public boolean isClickToTeleport() {
        return clickToTeleport;
    }

    public void setClickToTeleport(boolean clickToTeleport) {
        this.clickToTeleport = clickToTeleport;
    }

    public Stopwatch getLastDfsTimer() {
        return lastDfsTimer;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void giveItem(int itemId, int itemAmount) {

        ItemDefinition definition = ItemDefinition.forId(itemId);

        if (definition == null) {
            sendMessage("@red@[Error]: Could not find definition (" + itemId + "-" + itemAmount + ")");
            sendMessage("@red@Please take a screenshot and post it on the forums.");
            return;
        }

        int occupiedSlots = definition.isNoted() || definition.isStackable() ? 1 : itemAmount;

        if (inventory.getFreeSlots() >= occupiedSlots) {
            inventory.add(itemId, itemAmount).refreshItems();
        } else if (new Bank(this).getFreeSlots() >= occupiedSlots) {
            boolean added = false;
            for (Bank bank : getBanks()) {
                if (!added && !Bank.isEmpty(bank)) {
                    bank.add(itemId, itemAmount).refreshItems();
                    added = true;
                }
            }
        } else {
            sendMessage("@red@[Error]: Could not give (" + itemId + "-" + itemAmount + ")");
            sendMessage("@red@Please take a screenshot and post it on the forums.");
            World.sendStaffMessage("@red@[Error]: Could not give (" + itemId + "-" + itemAmount + ") to " + username);
        }
    }

    @Override
    public void loadMap() {
        loadMap(false);
    }

    public void loadMap(boolean login) {
        // note(walied):
        // Another way that this can be reworked to if you want to sacrifice memory is doing it using `Chunk` objects,
        // which makes some stuff easier however, it will cost a lot more memory which I think we cannot afford much of
        // it since we currently preload the entire world.
        Region previousRegion = RegionManager.getRegion(currentRegionId);
        if (previousRegion != null) {
            previousRegion.getPlayers().remove(index);
        }
        currentRegionId = position.getRegionId();
        Region currentRegion = RegionManager.getRegion(currentRegionId);
        if (currentRegion != null) {
            currentRegion.getPlayers().add(index);
        }
        // Calculate the current chunk x and y positions.
        int baseChunkX = position.getRealChunkX();
        int baseChunkY = position.getRealChunkY();
        // Clear the player region(id)s and visbility in other region(s).
        regionIds
                .stream()
                .map(RegionManager::getRegion)
                .filter(Objects::nonNull)
                .forEach(region -> region.getVisiblePlayers().remove(index));
        regionIds.clear();
        boolean dynamicRegion = false;
        // Rebuild the region id(s) that are visible to the player.
        for (int chunkX = baseChunkX - 6; chunkX <= baseChunkX + 6; chunkX++) {
            for (int chunkY = baseChunkY - 6; chunkY <= baseChunkY + 6; chunkY++) {
                int regionId = ((chunkX >> 3) << 8) | (chunkY >> 3);
                if (regionIds.contains(regionId)) {
                    continue;
                }
                regionIds.add(regionId);
                Region region = RegionManager.getRegion(regionId);
                if (region != null) {
                    region.getVisiblePlayers().add(index);
                }
                if (region instanceof DynamicRegion) {
                    dynamicRegion = true;
                }
            }
        }
        // ...
        setRegionChange(true);
        setAllowRegionChangePacket(true);
        setCurrentMapCenter(position.copy());

        // Send the map to the the client.
        if (dynamicRegion) {
            packetSender.sendDynamicMapRegion();
        } else {
            packetSender.sendMapRegion();
        }
        // Refresh all of the objects for the player.
        regionIds.stream().map(RegionManager::getRegion).filter(Objects::nonNull).forEach(region -> region.refreshAll(this));
        // Refresh all of the private objects.
        for (GameObject object : privateObjects.values()) {
            if (!object.getPosition().withinScene(getCurrentMapCenter())) {
                continue;
            }
            packetSender.sendObject(object);
        }
        // Refresh the farming objects for the player.
        /*if (!login) {
            farming.updateAll();
        }*/
    }

    @Override
    public void removeFromMap() {
        Region currentRegion = RegionManager.getRegion(currentRegionId);
        if (currentRegion != null) {
            currentRegion.getPlayers().remove(index);
        }
        regionIds.stream().map(RegionManager::getRegion).filter(Objects::nonNull).forEach(region -> region.getVisiblePlayers().remove(index));
    }
    @Getter
    private final Map<Long, GameObject> privateObjects = new HashMap<>();

    public void addObject(GameObject object) {
        privateObjects.put(object.getUid(), object);
        packetSender.sendObject(object);
    }

    public void removeObject(GameObject object) {
        if (privateObjects.remove(object.getUid()) == null) {
            return;
        }
        GameObject spawned = RegionManager.getObjectWithType(object.getPosition(), object.getType());
        if (spawned == null) {
            packetSender.sendObjectRemoval(object);
        } else {
            packetSender.sendObject(spawned);
        }
    }

    public void msgGreen(String string) {
        packetSender.sendMessage("@gre@<shad=0>" + string);
    }
    public void msgRed(String string) {
        packetSender.sendMessage("@red@<shad=0>" + string);
    }
    public void msgPurp(String string) {
        packetSender.sendMessage("<col=AF70C3><shad=0>" + string);
    }
    public void msgFancyPurp(String string) {packetSender.sendMessage("<shad=AF70C3>" + string);}
    public void msgGold(String string) {
        packetSender.sendMessage("<col=F5AD05><shad=0>" + string);
    }
    public void message(String string) {
        packetSender.sendMessage(string);
    }
    public void sendMessage(String string) {
        packetSender.sendMessage(string);
    }

    public int getBossPoints() {
        return 0;
    }

    public boolean isActive() {
        return true;
    }

    public void setVoteCount(int voteCount) {
        //doMotivote.setVoteCount(voteCount);
    }




    public void depositItemBank(Item item) {
        if (ItemDefinition.forId(item.getId()).isNoted()) {
            item.setId(Item.getUnNoted(item.getId()));
        }
        getBank(Bank.getTabForItem(this, item.getId())).add(item);
    }

    public void depositItemBank(Item item, boolean refresh) {
        if (ItemDefinition.forId(item.getId()).isNoted()) {
            item.setId(Item.getUnNoted(item.getId()));
        }
        getBank(Bank.getTabForItem(this, item.getId())).add(item, refresh);
    }
    public void depositItemBank(int tab, Item item, boolean refresh) {
        if (ItemDefinition.forId(item.getId()).isNoted()) {
            item.setId(Item.getUnNoted(item.getId()));
        }
        getBank(tab).add(item, refresh);
    }

    @Getter @Setter
    public boolean madeDRpot;

    @Getter @Setter
    public boolean receivedExtras;

    @Getter @Setter
    public boolean receivedEaster;


    @Getter @Setter
    public boolean madeCandyBoost;

    @Getter @Setter
    public boolean madeCritPot;

    @Getter @Setter
    public boolean madeDmgPot;


    @Getter @Setter
    public boolean inTutBossRoom;

    @Getter @Setter
    public int tutBossStage;

    @Getter@Setter
    private Transmog transmog = new Transmog(this);
    @Getter @Setter
    public boolean cosmeticHeadOn;

    @Getter @Setter
    public boolean cosmeticBodyOn;

    @Getter @Setter
    public boolean cosmeticLegsOn;

    @Getter @Setter
    public boolean cosmeticGlovesOn;

    @Getter @Setter
    public boolean cosmeticBootsOn;

    @Getter @Setter
    public boolean cosmeticCapeOn;

    @Getter @Setter
    public boolean cosmeticWeaponOn;

    @Getter @Setter
    public boolean cosmeticShieldOn;

    @Getter @Setter
    public boolean cosmeticAmuletOn;


    @Getter @Setter
    public boolean startedTutorial;

    @Getter @Setter
    public boolean unlockedIntermediateZones;

    @Getter @Setter
    public boolean unlockedEliteZones;

    @Getter @Setter
    public boolean unlockedMasterZones;

    @Getter @Setter
    public boolean unlockedSpectralZones;



    @Getter @Setter
    public boolean completedBeginner;

    @Getter @Setter
    public boolean claimedGear;


    @Getter @Setter
    public boolean claimedPermDamage;


    @Getter @Setter
    public boolean claimedPermDroprate;

    @Getter @Setter
    public boolean receivedprayerunlock;

    @Getter @Setter
    public int vgTutStage;

    @Getter @Setter
    public boolean receivedarmor;

    @Getter @Setter
    public boolean tutTask1Started;
    @Getter @Setter
    public boolean tutTask1Ready;
    @Getter @Setter
    public boolean tutTask1Complete;

    @Getter @Setter
    public boolean tutTask2Started;
    @Getter @Setter
    public boolean tutTask2Ready;
    @Getter @Setter
    public boolean tutTask2Complete;

    @Getter @Setter
    public boolean tutTask3Started;
    @Getter @Setter
    public boolean tutTask3Ready;
    @Getter @Setter
    public boolean tutTask3Complete;


    @Getter @Setter
    public boolean madeCritPotion;

    @Getter @Setter
    public boolean madeDropratePotion;
    @Getter @Setter
    public boolean madeDamagePotion;
    @Getter @Setter
    public boolean madeDivinePotion;

    @Getter @Setter
    public boolean tutTask4Started;
    @Getter @Setter
    public boolean tutTask4Ready;
    @Getter @Setter
    public boolean tutTask4Complete;

    @Getter @Setter
    public boolean tutTask5Started;
    @Getter @Setter
    public boolean tutTask5Ready;
    @Getter @Setter
    public boolean tutTask5Complete;

    @Getter @Setter
    public boolean tutTask6Started;
    @Getter @Setter
    public boolean tutTask6Ready;
    @Getter @Setter
    public boolean tutTask6Complete;

    @Getter @Setter
    public boolean defeatedbeast;
    @Getter @Setter
    public boolean completedtutorial;

    @Getter @Setter
    public boolean skippedTutorial;

    @Getter @Setter
    public boolean claimedSkipPrayer;

    @Getter @Setter
    public boolean claimedSkipArmor;


    @Getter @Setter
    public boolean hasManualPrayer;

    @Getter @Setter
    public boolean hasManualArmor;


    @Getter @Setter
    public boolean unlockedWolfCamp;

    @Getter @Setter
    public boolean unlockedDemonCamp;

    @Getter @Setter
    public boolean unlockedTitanCamp;

    @Getter @Setter
    public int dailyForestTaskAmount;

    @Getter @Setter
    public LocalDateTime forestResetTime;

    public void setLastForestTaskCompletionTime(LocalDateTime lastCompletionTime) {
        this.forestResetTime = lastCompletionTime;
    }

    @Getter @Setter
    public int tutDrSaltMined;

    @Getter @Setter
    public int tutDmgSaltMined;

    @Getter @Setter
    public int tutCritSaltMined;

    @Getter @Setter
    public int timesDuginMinigame;

    @Getter @Setter
    public boolean pouchMessagesEnabled = true;

    @Getter @Setter
    public boolean globalDropMessagesEnabled = true;

    @Getter @Setter
    public boolean critMessagesEnabled = true;

    @Getter @Setter
    public boolean halloweenQ;

    @Getter @Setter
    public boolean halloweenQ2;

    @Getter @Setter
    public boolean halloweenQ3;

    @Getter @Setter
    public boolean claimedskillingtools;

    @Getter @Setter
    public boolean hweenquestdone;

    @Getter @Setter
    public boolean didconfirmchallengetask;

    @Getter @Setter
    public boolean openedStarterBox;


    @Getter @Setter
    public boolean brimnstoneattackrunnning;

    @Getter @Setter
    public boolean everthornattackrunning;

    @Getter @Setter
    public boolean gfxattackrunning;

    @Getter @Setter
    public boolean basiliskattackrunning;


    @Getter @Setter
    public boolean movementTaskRunning;
    @Getter @Setter
    public boolean didconfirmcanceltask;

    @Getter @Setter
    public boolean isInInfernalCavern = false;

    @Getter @Setter
    public boolean isInFrozenCavern = false;

    @Getter @Setter
    public boolean isInOvergrownGardens = false;;

    @Getter @Setter
    public boolean eventstoggle;

    @Getter @Setter
    public boolean chair;

    @Getter @Setter
    public boolean table;

    @Getter @Setter
    public boolean candle;

    @Getter @Setter
    public boolean fire;

    @Getter @Setter
    public boolean eEarth;
    @Getter @Setter
    public boolean eFire;
    @Getter @Setter
    public boolean eWater;

    @Getter @Setter
    public boolean unlockedSkeletalSpells;
    @Getter @Setter
    public boolean unlockedDemonicSpells;
    @Getter @Setter
    public boolean unlockedOgreSpells;
    @Getter @Setter
    public boolean unlockedSpectralSpells;
    @Getter @Setter
    public boolean unlockedMasterSpells;

    @Getter @Setter
    public boolean unlockedGaiablessingPrayer;
    @Getter @Setter
    public boolean unlockedSerenityPrayer;
    @Getter @Setter
    public boolean unlockedRockheartPrayer;
    @Getter @Setter
    public boolean unlockedGeovigorPrayer;
    @Getter @Setter
    public boolean unlockedStonehavenPrayer;

    @Getter @Setter
    public boolean unlockedEbbflowPrayer;
    @Getter @Setter
    public boolean unlockedAquastrikePrayer;
    @Getter @Setter
    public boolean unlockedRiptidePrayer;
    @Getter @Setter
    public boolean unlockedSeaslicerPrayer;
    @Getter @Setter
    public boolean unlockedSwifttidePrayer;

    @Getter @Setter
    public boolean unlockedCindersTouch;
    @Getter @Setter
    public boolean unlockedEmberblastPrayer;
    @Getter @Setter
    public boolean unlockedInfernifyPrayer;
    @Getter @Setter
    public boolean unlockedBlazeupPrayer;
    @Getter @Setter
    public boolean unlockedInfernoPrayer;

    @Getter @Setter
    public boolean unlockedMalevolencePrayer;

    @Getter @Setter
    public boolean unlockedDesolationPrayer;



    //EASTER QUEST STAGES
    //1 - Started
    //2 - Log/ore Task
    //3 - Killing Task
    //4 - Egg Hunt 1
    //5 - Egg Hunt 2
    //6 - Completion

    @Getter @Setter
    public int easterQuestStage = 0;

    @Getter @Setter
    public boolean searchedKingsWardrobe;

    @Getter @Setter
    public int rabidBunniesKilled;

    @Getter @Setter
    public boolean foundFinalEgg;

    @Getter @Setter
    public boolean acceptedEasterReward;





    @Getter @Setter
    public boolean questquestdone;
    @Getter @Setter
    public boolean easterstage1;
    @Getter @Setter
    public boolean spade;

    @Getter @Setter
    public boolean dig1;
    @Getter @Setter
    public boolean dig2;
    @Getter @Setter
    public boolean dig3;
    @Getter @Setter
    public boolean digdone;


    @Getter @Setter
    public boolean plant1;
    @Getter @Setter
    public boolean plant2;
    @Getter @Setter
    public boolean plant3;
    @Getter @Setter
    public boolean plant4;
    @Getter @Setter
    public boolean plantdone;

    @Getter @Setter
    public boolean bunnieskilled;


    @Getter @Setter
    public boolean foundemberegg;

    @Getter @Setter
    public boolean foundmiraculousegg;

    @Getter @Setter
    public boolean QuestStart;

    @Getter @Setter
    public boolean QuestOne;

    @Getter @Setter
    public boolean QuestS2;


    @Getter @Setter
    public boolean powerUpSpawned;


    @Getter @Setter
    public boolean corruptRaidEasy;

    @Getter
    @Setter
    public boolean fixedFerry = false;

    @Getter
    @Setter
    public boolean posAbility = false;

    @Getter
    @Setter
    public boolean claimedLantern = false;

    @Getter @Setter
    public boolean corruptRaidMedium;

    @Getter @Setter
    public boolean corruptRaidHard;

    @Getter @Setter
    public boolean isdespawningpet;

    @Getter @Setter
    public boolean superiorisspawned;

    @Getter @Setter
    public boolean ascensionsuperiorspawned;


    @Getter
    @Setter
    public int SplashTasksDone;

    @Getter
    @Setter
    public int SplashdownStreak;

    @Getter @Setter
    public boolean bpassopen;



    //LEADERBOARD VARS

    @Getter
    @Setter
    public int easyTasksDone;



    @Getter
    @Setter
    public int mediumTasksDone;

    @Getter
    @Setter
    public int eliteTasksDone;

    @Getter
    @Setter
    public int beastTasksDone;

    @Getter
    @Setter
    public int totalKeysOpened;

    @Getter
    @Setter
    public int totalBoxesOpened;

    @Getter
    @Setter
    public int totalSoulbaneRuns;

    @Getter
    @Setter
    public int enchantedTasksDone;


    @Getter
    @Setter
    public int mysticMushTaskAmount;

    @Getter
    @Setter
    public int corruptMushTaskAmount;




    @Getter @Setter
    public boolean prayerMinigameBossInstanceActive;

    @Getter @Setter
    public boolean aresTrialsInstanceActive;
    @Getter @Setter
    public boolean trials2Unlocked;
    @Getter @Setter
    public boolean trials3Unlocked;
    @Getter @Setter
    public boolean trials4Unlocked;
    @Getter @Setter
    public boolean trials5Unlocked;

    @Getter @Setter
    public int prayerMinigameMinionKills = 0;

    @Getter @Setter
    public int prayerMinigameBossKillsLeft = 0;


    @Getter @Setter
    public int soulbaneStreak = 0;


    @Getter @Setter
    public int rubAllLampId = 0;

    @Getter @Setter
    public boolean spassopen;

    @Getter @Setter
    public boolean hasforgedsummercrystal;

    @Getter @Setter
    public boolean StartedSandQuest;

    @Getter @Setter
    public boolean SandStage2;

    @Getter @Setter
    public boolean talkedtoghost;
    @Getter @Setter
    public boolean hasenchantedtablet;
    @Getter @Setter
    public boolean hasfoundtablet;
    @Getter @Setter
    public boolean talkedtoKaden2ndtime;

    @Getter @Setter
    public boolean StartedOraclePart;

    @Getter @Setter
    public boolean rockarefalling;
    @Getter @Setter
    public boolean disturbedinfernus;

    @Getter @Setter
    public boolean passedinfernus;

    @Getter @Setter
    public boolean gotkeyfrompillar;

    @Getter @Setter
    public boolean openedmagicgate;

    @Getter @Setter
    public boolean movedaftermagic;

    @Getter @Setter
    public boolean talkedtosuleimghost;

    @Getter @Setter
    public boolean openedhorrorchest;

    @Getter @Setter
    public boolean searchedpillarafterhorror;

    @Getter @Setter
    public boolean openedgatebeforekharalos;

    @Getter @Setter
    public boolean madeittokharalos;

    @Getter @Setter
    public boolean killedkharalos;

    @Getter @Setter
    public boolean completedwhisperquest;



    @Getter @Setter
    public boolean inActiveInstance;

    @Getter @Setter
    public boolean isShockwaveactive;
    @Getter
    @Setter
    public int supermanstage;



    @Getter @Setter
    public boolean Islereq1;
    @Getter @Setter
    public boolean Islereq2;
    @Getter @Setter
    public boolean Islereq3;
    @Getter @Setter
    public boolean Islereq4;
    @Getter @Setter
    public boolean Islereq5;
    @Getter @Setter
    public boolean Islereq6;

    @Getter @Setter
    public boolean IsleStage1;

    @Getter @Setter
    public boolean IsleStage2;

    @Getter @Setter
    public boolean IsleStage3;

    @Getter @Setter
    public boolean IsleStage4;

    @Getter @Setter
    public boolean IsleStage5;

    @Getter @Setter
    public boolean IsleStage6;

    @Getter @Setter
    public boolean IsleStage7;







    @Getter @Setter
    public int bondClicked;

    @Getter @Setter
    public boolean bondClickedClaimAll;

    public void setCurrencyPouch(CurrencyPouch pouch) {
        this.currencyPouch = pouch;
    }

    @Getter @Setter
    public boolean unlockedLucifers;

    @Getter @Setter
    public boolean openedCoffin;

    @Getter
    @Setter
    public boolean unlockedDarkSupreme;

    public int distanceToPoint(int pointX, int pointY) {
        return (int) Math.sqrt(Math.pow(getPosition().getX() - pointX, 2) + Math.pow(getPosition().getY() - pointY, 2));
    }

    public int afkTicks;
    public boolean afk;

    public boolean register() {
        return World.getPlayers().add(this);
    }


    public int lastInstanceNpc = -1;


    @Getter
    @Setter
    private int killmessageamount;

    @Getter
    @Setter
    private int attemptDissolve;

    @Getter
    @Setter
    private boolean visible = true;

    @Getter
    @Setter
    private boolean canChat = true;

    public boolean isInMinigame() {
        boolean inMinigameLoc = getLocation() == Locations.Location.NECROMANCY_GAME_AREA || getLocation() == Locations.Location.TREASURE_HUNTER;
        boolean inMinigameInstance = getMapInstance() instanceof DungeoneeringInstance;
        return inMinigameLoc || inMinigameInstance;
    }

    private final BattlePass battlePass = new BattlePass(this);

    public BattlePass getBattlePass() {
        return battlePass;
    }

    public void setTier1ValpassClaimed(String ValPassClaimed) {
        this.BattlePassClaimed = ValPassClaimed;
    }
    public void setTier1ValPassExpires(String ValPassExpires) {
        this.BattlePassExpires = ValPassExpires;
    }
    public void setTier2ValPassClaimed(String ValPass2Claimed) {
        this.BattlePassTier2Claimed = ValPass2Claimed;
    }
    public void setTier2ValPassExpires(String ValPass2Expires) {
        this.BattlePassTier2Expires = ValPass2Expires;
    }
    public String BattlePassClaimed;
    public String BattlePassExpires;
    public String BattlePassTier2Claimed;
    public String BattlePassTier2Expires;

    private boolean[] unlockedHolyPrayers = new boolean[6];

    public boolean[] getUnlockedHolyPrayers() {
        return unlockedHolyPrayers;
    }
    public void setUnlockedHolyPrayer(int idx, boolean val) {
        if(idx >= 0 && idx < unlockedHolyPrayers.length) {
            unlockedHolyPrayers[idx] = val;
        }
    }
    public void setUnlockedHolyPrayers(boolean[] val) {
        unlockedHolyPrayers = val;
    }
    public boolean isHolyPrayerUnlocked(int idx) {
        return idx >= 0 && idx < unlockedHolyPrayers.length && unlockedHolyPrayers[idx];
    }


    private PlayerShops playerShops = new PlayerShops(this);
    private final Queue<Consumer<Player>> processConsumer = new ConcurrentLinkedQueue<>();
    public void addProcessCallable(Consumer<Player> callable) {
        processConsumer.add(callable);
    }

    void executeProcessCallables() {
        Consumer<Player> callable;
        while ((callable = processConsumer.poll()) != null) {
            callable.accept(this);
        }
    }
    private int upgradeConfirmationCounter = 0;

    public int getUpgradeConfirmationCounter() {
        return upgradeConfirmationCounter;
    }

    public void setUpgradeConfirmationCounter(int upgradeConfirmationCounter) {
        this.upgradeConfirmationCounter = upgradeConfirmationCounter;
    }

    @Getter
    private Companion companion = new Companion(this);

    /**
     * Start of Timers
     */
    @Getter
    private ShockWaveTimer shockwave = new ShockWaveTimer(this);
    @Getter
    private Luck_Timer luck = new Luck_Timer(this);
    @Getter
    private BlitzTimer blitz = new BlitzTimer(this);
    @Getter
    private HolyGraceTimer grace = new HolyGraceTimer(this);
    @Getter
    private DroprateTimer droprateTimer = new DroprateTimer(this);
    @Getter
    private DamageTimer damageTimer = new DamageTimer(this);
    @Getter
    private FuryTimer fury = new FuryTimer(this);
    @Getter
    private RageTimer rage = new RageTimer(this);

    @Getter
    private VoteBoostTimer voteBoost = new VoteBoostTimer(this);
    @Getter
    private CritTimer critTimer = new CritTimer(this);

    @Getter
    private IceBornTimer iceBornTimer = new IceBornTimer(this);

    @Getter
    private EasterTimer easterTimer = new EasterTimer(this);


    @Getter
    private LoveTimer loveTImer = new LoveTimer(this);


    private DivineTimer divine = new DivineTimer(this);
    public DivineTimer getDivine() {
        return divine;
    }
    private int divineTimeLeft;

    public int getDivineTimeLeft() {
        return divineTimeLeft;
    }

    public void setDivineTimeLeft(int divinetime) {
        this.divineTimeLeft = divinetime;
    }




    private NecromancyTimer necro = new NecromancyTimer(this);
    public NecromancyTimer getNecro() {
        return necro;
    }
    private int necrotimeleft;

    public int getNecrotimeleft() {
        return necrotimeleft;
    }

    public void setNecrotimeleft(int necrotime) {
        this.necrotimeleft = necrotime;
    }



    //
    private RaidBoostTimer raidboost = new RaidBoostTimer(this);
    public RaidBoostTimer getRaidBoost() {
        return raidboost;
    }
    private int raidboostTimeLeft;

    public int getRaidBoostTimeLeft() {
        return raidboostTimeLeft;
    }

    public void setRaidBoostTimeLeft(int raidTime) {
        this.raidboostTimeLeft = raidTime;
    }



    private NecroBoostTimer necroBoost = new NecroBoostTimer(this);
    public NecroBoostTimer getNecroBoost() {
        return necroBoost;
    }
    private int necroBoostTimeLeft;

    public int getNecroBoostTimeLeft() {
        return necroBoostTimeLeft;
    }

    public void setNecroBoostTimeLeft(int necroBoostTime) {
        this.necroBoostTimeLeft = necroBoostTime;
    }
    //

    private SlayerBoostTimer slayerBoost = new SlayerBoostTimer(this);
    public SlayerBoostTimer getSlayerBoost() {
        return slayerBoost;
    }
    private int slayerBoostTimeLeft;

    public int getSlayerBoostTimeLeft() {
        return slayerBoostTimeLeft;
    }

    public void setSlayerBoostTimeLeft(int slayerboostTime) {
        this.slayerBoostTimeLeft = slayerboostTime;
    }
    //

    private KillBoostTimer killBoost = new KillBoostTimer(this);
    public KillBoostTimer getKillBoost() {
        return killBoost;
    }
    private int killboostTimeLeft;

    public int getKillBoostTimeLeft() {
        return killboostTimeLeft;
    }

    public void setKillBoostTimeLeft(int killboosttime) {
        this.killboostTimeLeft = killboosttime;
    }
    //





    private NoobTimer noob = new NoobTimer(this);
    public NoobTimer getNoob() {
        return noob;
    }
    private int noobTimeLeft;

    public int getNoobTimeLeft() {
        return noobTimeLeft;
    }

    public void setNoobTimeLeft(int noobtime) {
        this.noobTimeLeft = noobtime;
    }


    public FuryTimer getFury() {
        return fury;
    }
    private int furytimeleft;
    public int getFuryTimeLeft() {
        return furytimeleft;
    }
    public void SetFuryTimeLeft(int furytime) {
        this.furytimeleft = furytime;
    }


    public IceBornTimer getIceBorn() {
        return iceBornTimer;
    }
    private int iceborntimeleft;
    public int getIceBornTimeLeft() {
        return iceborntimeleft;
    }
    public void setIceBornTimeLeft(int icetime) {
        this.iceborntimeleft = icetime;
    }




    public EasterTimer getEaster() {
        return easterTimer;
    }
    private int easterTimeLeft;
    public int getEasterTimeLeft() {
        return easterTimeLeft;
    }
    public void setEasterTimeLeft(int eastertime) {
        this.easterTimeLeft = eastertime;
    }



    public LoveTimer getLove() {
        return loveTImer;
    }
    private int loveTimeLeft;
    public int getLoveTimeLeft() {
        return loveTimeLeft;
    }
    public void setLoveTimeLeft(int lovetime) {
        this.loveTimeLeft = lovetime;
    }

    public RageTimer getRage() {
        return rage;
    }
    private int ragetimeleft;
    public int getRageTimeLeft() {
        return ragetimeleft;
    }
    public void SetRageTimeLeft(int ragetime) {
        this.ragetimeleft = ragetime;
    }



    public VoteBoostTimer getVoteBoost() {
        return voteBoost;
    }
    private int voteBoostTimeLeft;
    public int getVoteBoostTimeLeft() {
        return voteBoostTimeLeft;
    }
    public void setVoteBoostTimeLeft(int votetime) {
        this.voteBoostTimeLeft = votetime;
    }



    private MagmaTimer magma = new MagmaTimer(this);
    public MagmaTimer getMagma() {
        return magma;
    }
    private int magmatimeleft;
    public int getMagmaTimeLeft() {
        return magmatimeleft;
    }
    public void setMagmaTimeLeft(int magmatime) {
        this.magmatimeleft = magmatime;
    }

    private NauticTimer nautic = new NauticTimer(this);
    public NauticTimer getNautic() {
        return nautic;
    }
    private int nauticTimeLeft;
    public int getNauticTimeLeft() {
        return nauticTimeLeft;
    }
    public void setNauticTimeLeft(int nauticTime) {
        this.nauticTimeLeft = nauticTime;
    }

    private OvergrownTimer overgrown = new OvergrownTimer(this);
    public OvergrownTimer getOvergrown() {
        return overgrown;
    }
    private int overgrownTimeLeft;
    public int getOverGrownTimeLeft() {
        return overgrownTimeLeft;
    }
    public void setOverGrownTimeLeft(int overgrownTime) {
        this.overgrownTimeLeft = overgrownTime;
    }



    public void incrementUpgradeConfirmationCounter() {
        this.upgradeConfirmationCounter++;
    }

    public void decrementUpgradeConfirmationCounter() {
        this.upgradeConfirmationCounter--;
    }
    public PlayerShops getPlayerShops() {
        return playerShops;
    }

    public void incrementDoubleDamageTimer(int timer) {
        this.doubleDMGTimer += timer;
    }

    @Setter
    public boolean[] claimed = new boolean[0];

    @Getter
    @Setter
    public int donatedThroughMonth = 0;

    @Getter
    @Setter
    public LocalDate lastCampaignResetDate;

    public void setClaimed(int index, boolean state) {
        if (this.claimed == null) {
            this.claimed = new boolean[0];
        }
        if (index >= 0 && index < this.claimed.length) {
            this.claimed[index] = state;
        }
    }

    public void setClaimed(boolean[] claimed) {
        this.claimed = (claimed != null) ? claimed : new boolean[0];
    }

    // Manual getter with null safety
    public boolean[] getClaimed() {
        if (this.claimed == null) {
            this.claimed = new boolean[0];
        }
        return claimed;
    }    

    public Membership.Tiers membershipTier = Membership.Tiers.NONE;

    public Membership.Tiers getMembershipTier() {
        return membershipTier;
    }

    public Player setMemberShipTier(Membership.Tiers tier) {
        this.membershipTier = tier;
        return this;
    }

    private Membership membership = new Membership();

    public Membership getMembership() {
        return membership;
    }
    @Getter@Setter
    private int[] lifetimeStreakVal = new int[3];

    public void decrementLifeTimeStreakVal(int index, int val) {
        lifetimeStreakVal[index] -= val;
    }

    public void incrementLifeTimeStreakVal(int index, int val) {
        lifetimeStreakVal[index] += val;
    }

    @Getter@Setter
    public int dailyDealsClaimed;
    @Getter@Setter
    public int totalDonatedThroughStore;
    @Getter@Setter
    public int lifeTimeClaimedTicket;
    @Getter@Setter
    public int lifeTimeClaimedSomething;

    @Getter
    public CollectionLogManager collectionLogManager = new CollectionLogManager(this);

    private CreationMenu creationMenu;

    public CreationMenu getCreationMenu() {
        return creationMenu;
    }

    public void setCreationMenu(CreationMenu creationMenu) {
        this.creationMenu = creationMenu;
    }

    private Optional<Skillable> skill = Optional.empty();

    public Optional<Skillable> getSkill() {
        return skill;
    }

    public void setSkill(Optional<Skillable> skill) {
        this.skill = skill;
    }

    @Getter@Setter
    public int christmasQuest2023Stage;
    @Getter@Setter
    public boolean completedChristmas;

    @Getter@Setter
    public int jackFrostStage;

    @Getter@Setter
    public int combatLevel = 1;

    @Getter@Setter
    public int rockCakeCharges;


}

