package com.ruse.world.content;

import com.ruse.model.Flag;
import com.ruse.model.Item;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.input.impl.RenamePreset;
import com.ruse.net.packet.impl.EquipPacketListener;
import com.ruse.world.content.combat.Maxhits;
import com.ruse.world.entity.impl.player.Player;

public class PresetManager {

    private Player player;
    private Item[][] presets = new Item[4][14];
    private int wearingPreset = 0;
    private int presetsOwned = 2;
    private String[] presetNames = { "Preset 1", "Preset 2", "Preset 3", "Preset 4" };

    public PresetManager(Player player) {
        this.player = player;
    }

    public String[] getPresetNames() {
        return presetNames;
    }

    public void setPresetNames(String[] presetNames) {
        this.presetNames = presetNames;
    }

    public void renamePreset(String name) {
        presetNames[wearingPreset] = name;
        update();
    }

    public boolean handleButton(int button) {
        switch (button) {
            case -26501:
                if (!player.getClickDelay().elapsed(600)) {
                    player.sendMessage("Please wait a few seconds before switching presets");
                } else {
                    player.getClickDelay().reset();
                    swapPreset(false);
                }
                return true;
            case -26498:
                if (!player.getClickDelay().elapsed(600)) {
                    player.sendMessage("Please wait a few seconds before switching presets");
                } else {
                    player.getClickDelay().reset();
                    swapPreset(true);
                }
                return true;
            case -26513:
                player.setInputHandling(new RenamePreset());
                player.getPA().sendEnterInputPrompt("Enter a name for the preset:");
                return true;
            case -26509:
                //clearCurrentPreset();
                return true;
            case -26505:
                //clearAllPresets();
                return true;
        }
        return false;
    }

    public void clearCurrentPreset() {
        Bank.depositItems(player, player.getEquipment(), true);
        for (int i = 0; i < presets[wearingPreset].length; i++) {
            presets[wearingPreset][i] = new Item(-1);
        }
        player.getPA().sendMessage(
                "You have cleared " + presetNames[wearingPreset] + ". All items have been sent to your bank.");
        presetNames[wearingPreset] = "Preset " + (wearingPreset + 1);
        player.getEquipment().setItems(presets[wearingPreset]);
        player.getPacketSender().sendString(15116, presetNames[wearingPreset]);
        player.setCastSpell(null);
        BonusManager.update(player);
        player.getEquipment().refreshItems();
        player.getInventory().refreshItems();
        player.getUpdateFlag().flag(Flag.APPEARANCE);
        Sounds.sendSound(player, Sounds.Sound.EQUIP_ITEM);
        EquipPacketListener.resetWeapon(player);
    }

    public void clearAllPresets() {
        for (int i = 0; i < 4; i++) {
            player.getEquipment().setItems(presets[i]);
            Bank.depositItems(player, player.getEquipment(), true);
            presetNames[i] = "Preset " + (i + 1);
            for (int j = 0; j < 14; j++) {
                presets[i][j] = new Item(-1);
            }
        }
        player.getPA().sendMessage("You have cleared all your presets. All items have been sent to your bank.");
        player.getEquipment().setItems(presets[wearingPreset]);
        player.getPacketSender().sendString(15116, presetNames[wearingPreset]);
        player.setCastSpell(null);
        BonusManager.update(player);
        player.getEquipment().refreshItems();
        player.getInventory().refreshItems();
        player.getUpdateFlag().flag(Flag.APPEARANCE);
        Sounds.sendSound(player, Sounds.Sound.EQUIP_ITEM);
        EquipPacketListener.resetWeapon(player);
    }

    public void swapPreset(boolean up) {
        if (up) {
            if (wearingPreset + 1 == presetsOwned) {
                equipPreset(0);
            } else {
                equipPreset(wearingPreset + 1);
            }
        } else {
            equipPreset(wearingPreset == 0 ? (presetsOwned - 1) : wearingPreset - 1);
        }
    }

    public Item[][] getPresets() {
        return presets;
    }

    public void setPresets(Item[][] presets) {
        this.presets = presets;
    }

    public int getWearingPreset() {
        return wearingPreset;
    }

    public Item[] getCurrentPreset() {
        return presets[wearingPreset];
    }

    public void setCurrentPreset(Item[] preset) {
        presets[wearingPreset] = preset;
    }



    public void update() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 14; j++) {
                if (presets[i][j] == null) {
                    presets[i][j] = new Item(-1);
                }
            }
        }
        player.getPacketSender().sendString(15116, presetNames[wearingPreset]);
        player.getPacketSender().sendString(15118, "Presets Owned: " + presetsOwned);


    }

    public void setWearingPreset(int wearingPreset) {
        this.wearingPreset = wearingPreset;
    }

    public void setPreset(int i, Item[] preset) {
        presets[i] = preset;
    }

    public void nullCheck() {
        for (int i = 0; i < presets.length; i++) {
            for (int j = 0; j < presets[i].length; j++) {
                if (presets[i][j] == null) {
                    presets[i][j] = new Item(-1, 0);
                }
            }
        }
    }

    public void equipPreset(int preset) {
        saveCurrentPreset();
        player.getEquipment().setItems(presets[preset]);
        player.getPacketSender().sendString(15116, presetNames[preset]);
        player.setCastSpell(null);
        BonusManager.update(player);
        player.getEquipment().refreshItems();
        player.getInventory().refreshItems();
        player.getUpdateFlag().flag(Flag.APPEARANCE);
        Sounds.sendSound(player, Sounds.Sound.EQUIP_ITEM);
        EquipPacketListener.resetWeapon(player);
        wearingPreset = preset;
    }

    public void onLogin() {
        nullCheck();
        player.getPacketSender().sendString(15116, presetNames[wearingPreset]);
        player.setCastSpell(null);
        player.getUpdateFlag().flag(Flag.APPEARANCE);
        Sounds.sendSound(player, Sounds.Sound.EQUIP_ITEM);
        BonusManager.update(player);
        player.getEquipment().refreshItems();
        player.getInventory().refreshItems();
        EquipPacketListener.resetWeapon(player);
        update();
    }

    public void saveCurrentPreset() {
        presets[wearingPreset] = player.getEquipment().getCopiedItems();
    }

    public void changePresetItem(Item item) {
        int slot = item.getDefinition().getEquipmentSlot();
        presets[wearingPreset][slot] = item;
    }

    public int getPresetsOwned() {
        return presetsOwned;
    }

    public void setPresetsOwned(int presetsOwned) {
        this.presetsOwned = presetsOwned;
    }

    public void increasePresetsOwned() {
        this.presetsOwned += 1;
    }

}
