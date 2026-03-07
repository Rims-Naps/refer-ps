package com.ruse.net.packet.impl;

import com.ruse.model.Flag;
import com.ruse.model.GameMode;
import com.ruse.model.Item;
import com.ruse.model.Locations.Location;
import com.ruse.model.Skill;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.container.impl.Inventory;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.definitions.WeaponAnimations;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.net.packet.Packet;
import com.ruse.net.packet.PacketListener;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.AutoCastSpell;
import com.ruse.world.content.BonusManager;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.Sounds;
import com.ruse.world.content.Sounds.Sound;
import com.ruse.world.content.ascension.TierUpgradeType;
import com.ruse.world.content.combat.magic.Autocasting;
import com.ruse.world.content.combat.weapon.CombatSpecial;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.minigames.impl.Dueling.DuelRule;
import com.ruse.world.content.skill.impl.old_dungeoneering.Dungeoneering;
import com.ruse.world.entity.impl.player.Player;

/**
 * This packet listener manages the equip action a player executes when wielding
 * or equipping an item.
 *
 * @author relex lawl
 */

public class EquipPacketListener implements PacketListener {


    public static final int OPCODE = 41;

    public static boolean equipCosmetic(Player player, int id, int slot, int interfaceId) {
        if (interfaceId == Inventory.INTERFACE_ID) {
            /*
         * Making sure slot is valid.
         */
            if (slot >= 0 && slot <= 28) {
                Item item = player.getInventory().getItems()[slot].copy();
                if (!player.getInventory().contains(item.getId()))
                    return false;
                /*
                 * Making sure item exists and that id is consistent.
                 */
                if (item != null && id == item.getId()) {

                    int equipmentSlot = item.getDefinition().getEquipmentSlot();
                    Item equipItem = player.getCosmetics().forSlot(equipmentSlot).copy();

                    if (equipItem.getDefinition().isStackable() && equipItem.getId() == item.getId()) {
                        int amount = equipItem.getAmount() + item.getAmount() <= Integer.MAX_VALUE
                                ? equipItem.getAmount() + item.getAmount()
                                : Integer.MAX_VALUE;
                        player.getInventory().delete(item);
                        player.getCosmetics().getItems()[equipmentSlot].setAmount(amount);
                        equipItem.setAmount(amount);
                        player.getCosmetics().refreshItems();
                    } else {
                        if (item.getDefinition().isTwoHanded()
                                && item.getDefinition().getEquipmentSlot() == Equipment.WEAPON_SLOT) {
                            int slotsRequired = player.getCosmetics().isSlotOccupied(Equipment.SHIELD_SLOT)
                                    && player.getCosmetics().isSlotOccupied(Equipment.WEAPON_SLOT) ? 1 : 0;
                            if (player.getInventory().getFreeSlots() < slotsRequired) {
                                player.getInventory().full();
                                return false;
                            }

                            Item shield = player.getCosmetics().getItems()[Equipment.SHIELD_SLOT];
                            Item weapon = player.getCosmetics().getItems()[Equipment.WEAPON_SLOT];

                            player.getCosmetics().set(Equipment.SHIELD_SLOT, new Item(-1, 0));
                            player.getInventory().delete(item);
                            player.getCosmetics().set(equipmentSlot, item);

                            if (shield.getId() != -1) {
                                player.getInventory().add(shield);
                            }

                            if (weapon.getId() != -1) {
                                player.getInventory().add(weapon);
                            }
                        } else if (equipmentSlot == Equipment.SHIELD_SLOT
                                && player.getCosmetics().getItems()[Equipment.WEAPON_SLOT].getDefinition()
                                .isTwoHanded()) { //
                            player.getInventory().setItem(slot,
                                    player.getCosmetics().getItems()[Equipment.WEAPON_SLOT]);
                            player.getCosmetics().setItem(Equipment.WEAPON_SLOT, new Item(-1));
                            player.getCosmetics().setItem(Equipment.SHIELD_SLOT, item);
                            resetWeapon(player);
                        } else {
                            if (item.getDefinition().getEquipmentSlot() == equipItem.getDefinition().getEquipmentSlot()
                                    && equipItem.getId() != -1) {
                                if (player.getInventory().contains(equipItem.getId())) {
                                    player.getInventory().delete(item);
                                    player.getInventory().add(equipItem);
                                } else
                                    player.getInventory().setItem(slot, equipItem);
                                player.getCosmetics().setItem(equipmentSlot, item);
                            } else {
                                player.getInventory().setItem(slot, new Item(-1, 0));
                                player.getCosmetics().setItem(item.getDefinition().getEquipmentSlot(), item);
                            }
                        }
                    }

                    player.getCosmetics().refreshItems();
                    player.getInventory().refreshItems();
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    Sounds.sendSound(player, Sound.EQUIP_ITEM);
                }
            }
        }
        return true;
    }

    public static boolean equipItem(Player player, int id, int slot, int interfaceId) {
        if (!player.getControllerManager().canEquip(slot, id)) {
            return false;
        }

        ItemDefinition def = ItemDefinition.forId(id);
        if(def != null) {
            if(def.isNoted()) {
                String offence = "Attempted to equip notable item!";
                PlayerLogs.log(player.getUsername(), offence);
                player.getOffences().add(offence);
                return false;
            }
        }



        switch (id) {
            //CASE
        }
        if (interfaceId == Inventory.INTERFACE_ID) {/*
         * Making sure slot is valid.
         */
            if (slot >= 0 && slot <= 28) {
                Item item = player.getInventory().getItems()[slot].copy();
                if (!player.getInventory().contains(item.getId()))
                    return false;
                /*
                 * Making sure item exists and that id is consistent.
                 */
                if (item != null && id == item.getId()) {
                    for (Skill skill : Skill.values()) {
                        if (item.getDefinition().getRequirement()[skill.ordinal()] > player.getSkillManager()
                                .getMaxLevel(skill)) {
                            StringBuilder vowel = new StringBuilder();
                            if (skill.getName().startsWith("a") || skill.getName().startsWith("e")
                                    || skill.getName().startsWith("i") || skill.getName().startsWith("o")
                                    || skill.getName().startsWith("u")) {
                                vowel.append("an ");
                            } else {
                                vowel.append("a ");
                            }
                            player.getPacketSender().sendMessage("You need " + vowel
                                    + Misc.formatText(skill.getName()) + " level of at least "
                                    + item.getDefinition().getRequirement()[skill.ordinal()] + " to wear this.");
                            return false;
                        }
                    }
                    int equipmentSlot = item.getDefinition().getEquipmentSlot();
                    Item equipItem = player.getEquipment().forSlot(equipmentSlot).copy();
                    if (player.getLocation() == Location.DUEL_ARENA) {
                        for (int i = 10; i < player.getDueling().selectedDuelRules.length; i++) {
                            if (player.getDueling().selectedDuelRules[i]) {
                                DuelRule duelRule = DuelRule.forId(i);
                                if (equipmentSlot == duelRule.getEquipmentSlot()
                                        || duelRule == DuelRule.NO_SHIELD
                                        && item.getDefinition().isTwoHanded()) {
                                    player.getPacketSender().sendMessage(
                                            "The rules that were set do not allow this item to be equipped.");
                                    return false;
                                }
                            }
                        }
                        if (player.getDueling().selectedDuelRules[DuelRule.LOCK_WEAPON.ordinal()]) {
                            if (equipmentSlot == Equipment.WEAPON_SLOT || item.getDefinition().isTwoHanded()) {
                                player.getPacketSender().sendMessage("Weapons have been locked during this duel!");
                                return false;
                            }
                        }
                    }
                  /*  if (ItemDefinition.forId(item.getId()).isEarth() && !player.eEarth){
                        player.sendMessage("Only those molded by the Earth can equip this gear");
                        return false;
                    }

                    if (ItemDefinition.forId(item.getId()).isWater() && !player.eWater){
                        player.sendMessage("Only those adept to Water can equip this gear");
                        return false;
                    }

                    if (ItemDefinition.forId(item.getId()).isFire() && !player.eFire){
                        player.sendMessage("Only those Forged by Fire can equip this gear");
                        return false;
                    }*/

                    if (player.hasStaffOfLightEffect()

                            && equipItem.getDefinition().getName().toLowerCase().contains("staff of light")) {
                        player.setStaffOfLightEffect(-1);
                        player.getPacketSender()
                                .sendMessage("You feel the spirit of the Staff of Light begin to fade away...");

                    }
                    if (equipItem.getDefinition().isStackable() && equipItem.getId() == item.getId()) {
                        int amount = equipItem.getAmount() + item.getAmount() <= Integer.MAX_VALUE
                                ? equipItem.getAmount() + item.getAmount()
                                : Integer.MAX_VALUE;
                        player.getInventory().delete(item);
                        player.getEquipment().getItems()[equipmentSlot].setAmount(amount);
                        equipItem.setAmount(amount);
                        player.getEquipment().refreshItems();
                    } else {
                        if (item.getDefinition().isTwoHanded()
                                && item.getDefinition().getEquipmentSlot() == Equipment.WEAPON_SLOT) {
                            int slotsRequired = player.getEquipment().isSlotOccupied(Equipment.SHIELD_SLOT)
                                    && player.getEquipment().isSlotOccupied(Equipment.WEAPON_SLOT) ? 1 : 0;
                            if (player.getInventory().getFreeSlots() < slotsRequired) {
                                player.getInventory().full();
                                return false;
                            }

                            Item shield = player.getEquipment().getItems()[Equipment.SHIELD_SLOT];
                            Item weapon = player.getEquipment().getItems()[Equipment.WEAPON_SLOT];

                            player.getEquipment().set(Equipment.SHIELD_SLOT, new Item(-1, 0));
                            player.getInventory().delete(item);
                            player.getEquipment().set(equipmentSlot, item);

                            if (shield.getId() != -1) {
                                player.getInventory().add(shield);
                            }

                            if (weapon.getId() != -1) {
                                player.getInventory().add(weapon);
                            }
                        } else if (equipmentSlot == Equipment.SHIELD_SLOT
                                && player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getDefinition()
                                .isTwoHanded()) { //
                            player.getInventory().setItem(slot,
                                    player.getEquipment().getItems()[Equipment.WEAPON_SLOT]);
                            player.getEquipment().setItem(Equipment.WEAPON_SLOT, new Item(-1));
                            player.getEquipment().setItem(Equipment.SHIELD_SLOT, item);
                            resetWeapon(player);
                        } else {
                            if (item.getDefinition().getEquipmentSlot() == equipItem.getDefinition().getEquipmentSlot()
                                    && equipItem.getId() != -1) {
                                if (player.getInventory().contains(equipItem.getId())) {
                                    player.getInventory().delete(item);
                                    player.getInventory().add(equipItem);
                                } else
                                    player.getInventory().setItem(slot, equipItem);
                                player.getEquipment().setItem(equipmentSlot, item);
                            } else {
                                player.getInventory().setItem(slot, new Item(-1, 0));
                                player.getEquipment().setItem(item.getDefinition().getEquipmentSlot(), item);
                            }
                        }
                    }
                    if (equipmentSlot == Equipment.WEAPON_SLOT) {
                        resetWeapon(player);
                    }

                    if (player.getEquipment().get(Equipment.WEAPON_SLOT).getId() != 4153) {
                        player.getCombatBuilder().cooldown(false);
                    }

                    player.setCastSpell(null);
                    BonusManager.update(player);
                    player.getEquipment().refreshItems();
                    player.getInventory().refreshItems();
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    Sounds.sendSound(player, Sound.EQUIP_ITEM);
                }
            }
        }
        return true;
    }

    public static void resetWeapon(Player player) {
        Item weapon = player.getEquipment().get(Equipment.WEAPON_SLOT);

        WeaponInterfaces.assign(player, weapon);
        WeaponAnimations.update(player);
        player.getEquipment().refreshItems();

        if (AutoCastSpell.getAutoCastSpell(player) != null) {
            player.setAutocastSpell(AutoCastSpell.getAutoCastSpell(player).getSpell());
        } else {
            if (player.getAutocastSpell() != null || player.isAutocast()) {
                Autocasting.resetAutocast(player, true);
            }
        }


        player.setSpecialActivated(false);
        CombatSpecial.updateBar(player);
    }

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (player.getConstitution() <= 0)
            return;
        int id = packet.readShort();
        int slot = packet.readShortA();
        int interfaceId = packet.readShortA();
        if (player.getInterfaceId() > 0 && player.getInterfaceId() != 21172 && player.getInterfaceId() != 42600 /* EQUIP SCREEN */) {
            player.getPacketSender().sendInterfaceRemoval();
            // return;
        }


        if (player.viewingCosmeticTab)
            equipCosmetic(player, id, slot, interfaceId);
        else
            equipItem(player, id, slot, interfaceId);

    }
}