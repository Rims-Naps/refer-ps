package com.ruse.world.content.doorpick;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class DoorInterface {

    private final Player p;

    @Getter
    @Setter
    private boolean isViewing;

    private String editing = "Common";

    public void open() {
        isViewing = true;
        if (!(p.getUsername().equalsIgnoreCase("Hades") || !p.getUsername().equalsIgnoreCase("Iowna") || !p.getUsername().equalsIgnoreCase("Dark Phoenix"))) {
            p.sendMessage("You do not have access to this interface!");
            return;
        }
        p.getPA().sendInterface(42200);
        updateInterface();
    }

    public void updateInterface() {

        p.getPA().sendString(42218, "Door Picks Enabled: "+(DoorPick.isPicksOpen() ? "@gre@True" : "@red@False"));
        p.getPA().sendString(42217, "Editing: "+(editing.equalsIgnoreCase("Common") ? "@gre@Common" : "@cya@Rare"));
        p.getPA().sendInterfaceItems(42206, DoorPick.commonLoot);
        p.getPA().sendInterfaceItems(42208, DoorPick.rareLoot);
        p.getPacketSender().sendItemContainer(p.getInventory(), 3322);
    }

    public void addItem(Item item) {
        if (editing.equalsIgnoreCase("Common")) {
            DoorPick.commonLoot.add(item);
            p.getInventory().delete(item);
        } else {
            DoorPick.rareLoot.add(item);
            p.getInventory().delete(item);
        }
        updateInterface();
    }

    public boolean handleButton(int id) {
        switch (id) {
            case -23319:
                editing = (editing.equalsIgnoreCase("Common") ? "Rare" : "Common");
                updateInterface();
                return true;
            case -23318:
                DoorPick.setPicksOpen(!(DoorPick.isPicksOpen()));
                updateInterface();
                return true;

            case -23327:
                DoorPick.commonLoot.clear();
                updateInterface();
                return true;

            case -23324:
                DoorPick.rareLoot.clear();
                updateInterface();
                return true;
        }
        return false;
    }

}
