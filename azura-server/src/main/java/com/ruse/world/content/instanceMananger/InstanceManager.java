package com.ruse.world.content.instanceMananger;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Locations;
import com.ruse.model.PlayerRights;
import com.ruse.model.Position;
import com.ruse.model.RegionInstance;
import com.ruse.model.RegionInstance.RegionInstanceType;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.content.ZoneProgression.NpcRequirements;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;

public class InstanceManager {

    private final Player player;

    public InstanceManager(Player player) {
        this.player = player;
    }

    public int pos = 4;
    public int ticketID;
    public int grid = 4;
    public InstanceData selectedInstance;

    public static int[] POWER_UP_IDS_1 = {
            1717 , 1718 , 1719 ,1721
    };
    private static List<Position> POWER_UP_SPOTS_1 = new ArrayList<>();

    private static final InstanceData[] values = InstanceData.values();

    public int getCost() {
        if (selectedInstance != null) {
            if (ticketID == 2706){
                switch (grid){
                    case 4:
                        return 1;
                    case 6:
                        return 2;
                }
            }
            if (ticketID == 2708){
                switch (grid){
                    case 4:
                        return 1;
                    case 6:
                        return 2;
                }
            }
        }

        return -1;
    }

    public void createInstance(int npcId, RegionInstanceType type) {


        if (player.getRights() != PlayerRights.OWNER && player.getRights() != PlayerRights.CO_OWNER && player.getRights() != PlayerRights.YOUTUBER && player.getRights() != PlayerRights.MANAGER ) {
            for (NpcRequirements req : NpcRequirements.values()) {
                if (npcId == req.getNpcId()) {
                    if (req.getKillCount() > 0) {
                        if (player.getPointsHandler().getNPCKILLCount() < req.getKillCount()) {
                            player.sendMessage("@red@<shad=1>You need atleast " + req.getKillCount() + "@red@<shad=1>NPC kills to start a dream for this monster.. (@red@<shad=1>" + player.getPointsHandler().getNPCKILLCount() + "/"
                                    + req.getKillCount() + ")");
                            return;
                        }
                    } else {
                        int npc = req.getRequireNpcId();
                        int total = KillsTracker.getTotalKillsForNpc(npc, player);
                        if (total < req.getAmountRequired()) {
                            player.sendMessage("@red@<shad=1>You need atleast " + req.getAmountRequired() + " "
                                    + NpcDefinition.forId(npc).getName() + "@red@<shad=1> kills to start a dream for this monster. (" + total + "@red@<shad=1>/"
                                    + req.getAmountRequired() + "@red@<shad=1>)");
                            return;
                        }
                    }
                    break;
                }
            }
        }

        if (selectedInstance != null) {
            if (selectedInstance == InstanceData.Kezel ||
                selectedInstance == InstanceData.Hydrora ||
                selectedInstance == InstanceData.Infernus ||
                selectedInstance == InstanceData.Tellurion ||
                selectedInstance == InstanceData.Marinus ||
                selectedInstance == InstanceData.Pyrox ||
                selectedInstance == InstanceData.Astaran ||
                selectedInstance == InstanceData.Nereus ||
                selectedInstance == InstanceData.Volcar) {
                if (!player.unlockedIntermediateZones){
                    player.msgRed("You do not have Intermediate Zones Unlocked");
                    return;
                }
            }

            if (selectedInstance == InstanceData.Lagoon ||
                selectedInstance == InstanceData.Incendia ||
                selectedInstance == InstanceData.Terra ||
                selectedInstance == InstanceData.Abyss ||
                selectedInstance == InstanceData.Pyra ||
                selectedInstance == InstanceData.Geode ||
                selectedInstance == InstanceData.Cerulean ||
                selectedInstance == InstanceData.Scorch ||
                selectedInstance == InstanceData.Geowind) {
                if (!player.unlockedEliteZones){
                    player.msgRed("You do not have Elite Zones Unlocked");
                    return;
                }
            }

            if (selectedInstance == InstanceData.Goliath ||
                    selectedInstance == InstanceData.Volcanus ||
                    selectedInstance == InstanceData.Nautilus ||
                    selectedInstance == InstanceData.Quake ||
                    selectedInstance == InstanceData.Scaldor ||
                    selectedInstance == InstanceData.Seabane ||
                    selectedInstance == InstanceData.Rumble ||
                    selectedInstance == InstanceData.Moltron ||
                    selectedInstance == InstanceData.Hydrox) {
                if (!player.unlockedMasterZones){
                    player.msgRed("You do not have Master Zones Unlocked");
                    return;
                }
            }
        }

        boolean instanceTrinket = player.getInventory().contains(1542);

        if ((grid == 4 || grid == 6) && (ticketID == 2706 || ticketID == 2708)) {
            int requiredCost = 0;


            if (ticketID == 2706 && grid == 4){
                requiredCost = 1;
            }
            if (ticketID == 2706 && grid == 6){
                requiredCost = 2;
            }

            if (ticketID == 2708 && grid == 4){
                requiredCost = 1;
            }
            if (ticketID == 2708 && grid == 6){
                requiredCost = 2;
            }


            if (instanceTrinket) {
                player.getInventory().delete(1542, 1);
                player.msgFancyPurp("You used an Instance Trinket to start the instance.");
                if (selectedInstance != null) {
                    if (selectedInstance.getNpcId() == 1194) {
                        player.getInventory().delete(715, 50);
                    }
                }
            } else if (player.getInventory().contains(ticketID, requiredCost)) {
                player.getInventory().delete(ticketID, requiredCost);
                if (selectedInstance != null) {
                    if (selectedInstance.getNpcId() == 1194) {
                        player.getInventory().delete(715, 50);
                    }
                }
            } else {
                player.getPA().sendMessage("You need " + Misc.insertCommasToNumber(requiredCost) + " Tickets" + (ticketID == 2708 ? "(u)" : ""));
                return;
            }

        } else {
            player.getPA().sendMessage("Invalid ticket or grid size.");
            return;
        }

        int spawnCount = (grid == 4) ? 16 : 64; // Set spawn count based on grid size

        World.getNpcs().forEach(n -> n.removeInstancedNpcs(Locations.Location.INSTANCE1, player.getIndex() * pos));
        World.getNpcs().forEach(n -> n.removeInstancedNpcs(Locations.Location.INSTANCE2, player.getIndex() * pos));

        if (player.getRegionInstance() != null) {
            for (NPC n : player.getRegionInstance().getNpcsList()) {
                if (n != null) {
                    World.deregister(n);
                }
            }
            player.getRegionInstance().getNpcsList().clear();
        } else {
            for (NPC n : World.getNpcs()) {
                if (n != null) {
                    if (n.getPosition().getRegionId() == 9019 && n.getPosition().getZ() == (player.getIndex() * pos)) {
                        World.deregister(n);
                    }
                }
            }
        }

        player.setInstanceID(player.getInstanceID() + 1);
        player.setRegionInstance(new RegionInstance(player, type));
        player.lastInstanceNpc = npcId;
        GridSpawns selectedGrid = (grid == 6) ? GridSpawns.SIX_BY_SIX : GridSpawns.FOUR_BY_FOUR;
        player.moveTo(selectedGrid.getPlayerPosition());
        player.moveTo(new Position(selectedGrid.getPlayerPosition().getX(), selectedGrid.getPlayerPosition().getY(), selectedGrid.getStart().getZ() + (player.getIndex() * 4)));
        player.getPA().sendEntityHintRemoval(true);

        int offset = selectedGrid.getOffset();
        int startZ = selectedGrid.getStart().getZ() + (player.getIndex() * 4);

        for (int r = 0; r < selectedGrid.getGridSize(); r++) {
            for (int c = 0; c < selectedGrid.getGridSize(); c++) {
                if (r * selectedGrid.getGridSize() + c < spawnCount) {
                    //System.out.println("NPC X CORDS: " + selectedGrid.getStart().getX());
                    //System.out.println("NPC Y CORDS: " +selectedGrid.getStart().getY());
                    //System.out.println("NPC HEIGHT: " + startZ);
                    NPC npc_ = new NPC(npcId , new Position(selectedGrid.getStart().getX() + (offset * r), selectedGrid.getStart().getY() + (offset * c), startZ));
                    npc_.setInstanceID(player.getInstanceID());
                    npc_.setSpawnedFor(player);
                    player.getRegionInstance().getNpcsList().add(npc_);
                    World.register(npc_);
                }
            }
        }

        for (InstanceData data : values) {
            if (npcId == data.getNpcId() || NpcDefinition.forId(npcId).getName() == data.getName()) {
                if (grid == 4 && ticketID == 2706){
                    player.setCurrentInstanceAmount(Misc.random(150,300));
                    player.setCurrentInstanceNpcId(data.getNpcId());
                    player.setCurrentInstanceNpcName(data.getName());
                }
                if (grid == 6 && ticketID == 2706){
                    player.setCurrentInstanceAmount(Misc.random(300,600));
                    player.setCurrentInstanceNpcId(data.getNpcId());
                    player.setCurrentInstanceNpcName(data.getName());
                }

                if (grid == 4 && ticketID == 2708){
                    player.setCurrentInstanceAmount(Misc.random(250,375));
                    player.setCurrentInstanceNpcId(data.getNpcId());
                    player.setCurrentInstanceNpcName(data.getName());
                }
                if (grid == 6 && ticketID == 2708){
                    player.setCurrentInstanceAmount(Misc.random(250,375));
                    player.setCurrentInstanceNpcId(data.getNpcId());
                    player.setCurrentInstanceNpcName(data.getName());
                }
            }
        }


        if (player.getRights() == PlayerRights.OWNER   || player.getRights() == PlayerRights.CO_OWNER || player.getRights() == PlayerRights.MANAGER || player.getRights() == PlayerRights.YOUTUBER ) {
            player.sendMessage("<col=AF70C3><shad=0>You have bypassed requirements with rights: @red@<shad=0>" + player.getRights());
        }
        player.setInActiveInstance(true);
        player.getPA().sendMessage("You started a dream of " + player.getCurrentInstanceAmount() + " " + player.getCurrentInstanceNpcName());
        player.getPA().sendInterfaceRemoval();
    }

    public void death(Player player, NPC npc, String NpcName) {

        if (npc.getId() != player.getCurrentInstanceNpcId()) {
            return;
        }

        if (player.currentInstanceNpcId == -1 || player.currentInstanceNpcName == "") {
            return;
        }

        if (player.currentInstanceNpcId == player.getSlayer().getSlayerTask().getNpcId())
            player.getSlayer().setAmountToSlay(player.getSlayer().getAmountToSlay() -1);

        player.setCurrentInstanceAmount(player.getCurrentInstanceAmount() - 1);
        if (player.getCurrentInstanceAmount() % 25 == 0) {
            player.getPA().sendMessage("@bla@<shad=0>You have " + player.getCurrentInstanceAmount() + " spawns remaining ");
        }

        int pchance = Misc.random(0,100);
        if (pchance == 0){
            spawnPowerup();
        }

        if (player.getCurrentInstanceAmount() <= 0) {
            cleanupPowerups();
            finish();
            return;
        }
    }

    public void spawnPowerup() {
        if (!player.powerUpSpawned) {
            player.setPowerUpSpawned(true);
            int spot = Misc.random(1, 4);
            TaskManager.submit(new Task(5, false) {
                @Override
                public void execute() {
                    int powerup = Misc.random(1716, 1719);

                    Position spawnPosition;
                    switch (spot) {
                        case 1:
                            spawnPosition = new Position(2253, 3804, player.getPosition().getZ());
                            break;
                        case 2:
                            spawnPosition = new Position(2265, 3809, player.getPosition().getZ());
                            break;
                        case 3:
                            spawnPosition = new Position(2259, 3815, player.getPosition().getZ());
                            break;
                        case 4:
                            spawnPosition = new Position(2259, 3803, player.getPosition().getZ());
                            break;
                        default:
                            spawnPosition = new Position(0, 0, 0);
                            break;
                    }

                    NPC spawn = new NPC(powerup, spawnPosition).setSpawnedFor(player);
                    player.getPacketSender().sendPositionalHint(spawnPosition, 3);
                    player.sendMessage("@red@<shad=0>A @red@<shad=0>" + spawn.getDefinition().getName() + " @red@<shad=0>has appeared, activate it quickly!");
                    World.register(spawn);
                    this.stop();
                }
            });
        }
    }


    public void cleanupPowerups() {
        player.getShockwave().setTimeForTask(0);
        player.getBlitz().setTimeForTask(0);
        player.getLuck().setTimeForTask(0);
        player.getGrace().setTimeForTask(0);
    }

    public void finish() {
        player.setInActiveInstance(false);
        player.getPA().sendMessage("@bla@<shad=0>Your dream has ended!");
        player.getLastRunRecovery().reset();
        cleanupPowerups();
        if (player != null) {
            onLogout();
        }
    }

    public void onLogout() {
        if (player.getRegionInstance() != null) {
            player.getRegionInstance().destruct();
        }
        player.setInActiveInstance(false);
        player.getInstanceManager().selectedInstance = (null);
        player.setCurrentInstanceAmount(-1);
        player.setCurrentInstanceNpcId(-1);
        player.setCurrentInstanceNpcName("");
        cleanupPowerups();
    }
}
