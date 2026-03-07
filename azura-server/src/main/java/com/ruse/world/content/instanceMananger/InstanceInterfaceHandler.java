package com.ruse.world.content.instanceMananger;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.content.ZoneProgression.NpcRequirements;
import com.ruse.world.entity.impl.player.Player;

public class InstanceInterfaceHandler {

    private Player player;

    private static final InstanceData data[] = InstanceData.values();

    public InstanceInterfaceHandler(Player player) {
        this.player = player;
    }

    public void open() {
        if (player.getInstanceManager().selectedInstance == null)
            player.getInstanceManager().selectedInstance = InstanceData.values()[0];
        sendBossNames();
        player.getPA().sendInterface(35000);
    }

    public void sendBossNames() {
        int startID = 35071;
        for (InstanceData data : data) {
            String color = getColorForBoss(data.getNpcId());
            player.getPA().sendString(startID++, color + data.getName());
        }
        player.getPacketSender().setScrollBar(35070, data.length * 18);
        player.getPacketSender().sendNpcOnInterface(35017, player.getInstanceManager().selectedInstance.getNpcId(), player.getInstanceManager().selectedInstance.getZoom());
        sendInfo();
    }
    public String getColorForBoss(int npcId) {
        int totalKills = KillsTracker.getTotalKillsForNpc(npcId, player);
        NpcRequirements[] requirements = NpcRequirements.values();

        for (int i = 0; i < requirements.length - 1; i++) {
            if (npcId == requirements[i].getNpcId()) {
                NpcRequirements nextRequirement = requirements[i + 1];
                int requiredKills = nextRequirement.getAmountRequired();

                if (totalKills >= requiredKills) {
                    return "@gre@";
                } else if (totalKills > 0) {
                    return "@yel@";
                } else {
                    return "@red@";
                }
            }
        }

        return "@red@";
    }

    public void sendInfo() {
        NpcRequirements[] requirements = NpcRequirements.values();
        for (int i = 0; i < requirements.length - 1; i++) {
            if (player.getInstanceManager().selectedInstance.getNpcId() == requirements[i].getNpcId()) {
                NpcRequirements nextRequirement = requirements[i + 1];
                int totalKills = KillsTracker.getTotalKillsForNpc(player.getInstanceManager().selectedInstance.getNpcId(), player);
                String killsString = totalKills >= 1000 ? (totalKills / 1000) + "K" : String.valueOf(totalKills);
                String requiredString = nextRequirement.getAmountRequired() >= 1000 ? (nextRequirement.getAmountRequired() / 1000) + "K" : String.valueOf(nextRequirement.getAmountRequired());
                player.getPA().sendString(35007, "@yel@ " + player.getInstanceManager().selectedInstance.getName() + " " + killsString + "/" + requiredString);
            }
        }
       // player.getPacketSender().sendString(35007, "@yel@ " + player.getInstanceManager().selectedInstance.getName());
        player.getPacketSender().sendItemOnInterface(35008, player.getInstanceManager().ticketID, 1);
        player.getPacketSender().sendString(35009, "@yel@" + ItemDefinition.forId(player.getInstanceManager().ticketID).getName());
        if (player.getInstanceManager().ticketID == 2706) {
            player.getPacketSender().sendString(35010, "Spawns: @whi@" + (player.getInstanceManager().grid == 4 ? "150-300" : "300-600"));
        }
        if (player.getInstanceManager().ticketID == 2708) {
            player.getPacketSender().sendString(35010, "Spawns: @whi@" + (player.getInstanceManager().grid == 4 ? "275-375" : "275-375"));
        }
        player.getPacketSender().sendString(35011, "Price: @whi@" + Misc.sendCashToString(player.getInstanceManager().getCost()) + " Tickets");

        if (player.getInstanceManager().grid == 4) {
            player.getPacketSender().sendConfig(1355, 0);
        }else {
            player.getPacketSender().sendConfig(1355, 1);
        }
    }



    public void handleButtons(int id) {
        if (id == -30524) {
            player.getInstanceManager().grid = 4; // Set grid size to 4x4
            sendInfo();
        }
        if (id == -30523) {
            player.getInstanceManager().grid = 6; // Set grid size to 6
            sendInfo();
        }

        for (InstanceData data : data) {
            if (id == -30465 + data.ordinal()) {
                player.getInstanceManager().selectedInstance = (data);
                sendBossNames();
            }
        }
    }

}
