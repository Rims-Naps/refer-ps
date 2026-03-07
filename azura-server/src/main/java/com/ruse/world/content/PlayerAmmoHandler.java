package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.container.impl.Equipment;
import com.ruse.util.Misc;
import com.ruse.world.entity.impl.player.Player;

public class PlayerAmmoHandler {

    private Player player;

    public PlayerAmmoHandler(Player player) {
        this.player = player;
    }

    public void handleAmmo() {
        boolean ammoEquipped = isAmmoEquipped();
        int invAmmo = getInventoryAmmoAmount();
        boolean quiverEquipped = isQuiverEquipped();
        int chanceToSaveArrow = Misc.inclusiveRandom(20);

        if (quiverEquipped) {
            if (Misc.inclusiveRandom(20) == 1) {
                player.msgRed("Your Quiver managed to save some of your ammo.");
            } else {
                handleQuiverAmmo();
            }
        }

        if (ammoEquipped) {
            handleEquippedAmmo(invAmmo);
        }
    }

    private boolean isAmmoEquipped() {
        int ammoId = player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId();
        return ammoId == 1428 || ammoId == 1430 || ammoId == 1429 || ammoId == 1431;
    }

    private int getInventoryAmmoAmount() {
        return player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getAmount();
    }

    private boolean isQuiverEquipped() {
        return player.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 2056;
    }

    private void handleQuiverAmmo() {
        switch (player.getQuiverMode()) {
            case 1:
                decrementAmmo(player.getVorpalAmmo(), "Vorpal");
                break;
            case 2:
                decrementAmmo(player.getBloodstainedAmmo(), "Bloodstained");
                break;
            case 3:
                decrementAmmo(player.getSymbioteAmmo(), "Symbiote");
                break;
            case 4:
                decrementAmmo(player.getNetherAmmo(), "Nether");
                break;
        }
    }

    private void decrementAmmo(int ammo, String ammoType) {
        if (ammo >= 1) {
            setAmmoByType(ammoType, ammo - 1);
        }
        if (ammo == 0) {
            player.msgRed("Your Quiver ran out of " + ammoType + " Ammo!");
        }
    }

    private void setAmmoByType(String ammoType, int amount) {
        switch (ammoType) {
            case "Vorpal":
                player.setVorpalAmmo(amount);
                break;
            case "Bloodstained":
                player.setBloodstainedAmmo(amount);
                break;
            case "Symbiote":
                player.setSymbioteAmmo(amount);
                break;
            case "Nether":
                player.setNetherAmmo(amount);
                break;
        }
    }

    private void handleEquippedAmmo(int invAmmo) {
        player.getEquipment().get(Equipment.AMMUNITION_SLOT).decrementAmount();
        player.getEquipment().refreshItems();
        if (invAmmo == 1) {
            player.msgRed("You have run out of ammunition!");
            player.getEquipment().set(Equipment.AMMUNITION_SLOT, new Item(-1));
            player.getEquipment().refreshItems();
        }
    }
}
