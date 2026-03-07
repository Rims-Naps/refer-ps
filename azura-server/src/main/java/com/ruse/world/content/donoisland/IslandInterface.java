package com.ruse.world.content.donoisland;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class IslandInterface {
    private final Player p;

    @Getter
    @Setter
    private boolean isViewing;

    private String editing = "Tier 1";

    public void open() {
        isViewing = true;
        if (!p.getRights().isManagement()) {
            p.sendMessage("You do not have access to this interface!");
            return;
        }
        p.getPA().sendInterfaceItems(42208, IslandData.tier_one);
        p.getPA().sendInterfaceItems(42210, IslandData.tier_two);
        p.getPA().sendInterfaceItems(42212, IslandData.tier_three);
        p.getPA().sendInterface(42200);
        updateInterface();
    }

    public void updateInterface() {
        p.getPacketSender().changeClientTooltip(42216, "Editing: "+(editing.equalsIgnoreCase("Tier 1") ? "@gre@Tier 1" : (editing.equalsIgnoreCase("Tier 3") ? "@gre@Tier 3" : "@gre@Tier 2")));
        p.getPacketSender().sendCustomHoverText(42216, "Editing: "+(editing.equalsIgnoreCase("Tier 1") ? "@gre@Tier 1" : (editing.equalsIgnoreCase("Tier 3") ? "@gre@Tier 3" : "@gre@Tier 2")));
        p.getPA().sendInterfaceItems(42208, IslandData.tier_one);
        p.getPA().sendInterfaceItems(42210, IslandData.tier_two);
        p.getPA().sendInterfaceItems(42212, IslandData.tier_three);
        p.getPacketSender().sendItemContainer(p.getInventory(), 3322);
    }

    public void addItem(Item item) {
        if (editing.equalsIgnoreCase("Tier 1")) {
            IslandData.tier_one.add(item);
            p.getInventory().delete(item);
        } else if (editing.equalsIgnoreCase("Tier 2")) {
            IslandData.tier_two.add(item);
           p.getInventory().delete(item);
        } else {
            IslandData.tier_three.add(item);
            p.getInventory().delete(item);
        }
        updateInterface();
    }

    public boolean handleButton(int id) {
        switch (id) {
            case -23323:
                IslandData.tier_one.clear();
                updateInterface();
                return true;
            case -23322:
                IslandData.tier_two.clear();
                updateInterface();
                return true;
            case -23321:
                IslandData.tier_three.clear();
                updateInterface();
                return true;
            case -23320:
                editing = (editing.equalsIgnoreCase("Tier 1") ? "Tier 2" : editing.equalsIgnoreCase("Tier 2") ? "Tier 3" : "Tier 1");
                updateInterface();
                return true;
        }
        return false;
    }
}
