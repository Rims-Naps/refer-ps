package com.ruse.world.content;

import com.ruse.model.container.impl.Equipment;
import com.ruse.world.content.combat.magic.CombatAncientSpell;
import com.ruse.world.content.combat.magic.CombatSpells;
import com.ruse.world.entity.impl.player.Player;

public enum AutoCastSpell {
    EARTH_APPRENTICE(5002, CombatSpells.EARTH_1),
    WATER_APPRENTICE(5005, CombatSpells.WATER_1),
    FIRE_APPRENTICE(5008, CombatSpells.FIRE_1),
    CINDERFALL(14915, CombatSpells.FIRE_1),
    STONEWARD(23144, CombatSpells.EARTH_1),
    TIDEWAVE(23033, CombatSpells.WATER_1),
    VERDANT(3743, CombatSpells.EARTH_1),
    FLAMESTRIKE(23065, CombatSpells.FIRE_1),
    WAVEBREAKER(14065, CombatSpells.WATER_1),
    MAGMITE(5010, CombatSpells.FIRE_1),
    MOSSHEART(13943, CombatSpells.EARTH_1),
    SURGE(13063, CombatSpells.WATER_1),
    BLAZEN(18972, CombatSpells.FIRE_1),
    MOSSBORN(13029, CombatSpells.EARTH_1),
    PULSAR(21070, CombatSpells.WATER_1),
    MOLTAROK(15485, CombatSpells.FIRE_1),
    CLIFFBREAKER(17620, CombatSpells.EARTH_1),
    TIDAL(16875, CombatSpells.WATER_1),
    MERCURIAL(18748, CombatSpells.FIRE_1),
    VIRE(18009, CombatSpells.EARTH_1),
    SEAFIRE(14006, CombatSpells.WATER_1),
    TORRID(13330, CombatSpells.FIRE_1),
    WARDEN(12902, CombatSpells.EARTH_1),
    VOID1(20002, CombatSpells.VOID_1),
    LAVA_STAFF(16417, CombatSpells.LAVA_SPELL_1),
    AQUA_STAFF(16420, CombatSpells.AQUA_SPELL_1),
    ELITE_3(17103, CombatSpells.AQUA_SPELL_1),
    CASCADE(17111, CombatSpells.AQUA_SPELL_1),
    BEDROCK(17110, CombatSpells.EARTH_1),
    ELITE_1(17103, CombatSpells.LAVA_SPELL_1),
    ELITE_2(17106, CombatSpells.LAVA_SPELL_1),

    TERRASTEEL(1475, CombatSpells.EARTH_1),
    FIRELASH(1499, CombatSpells.FIRE_1),
    INCENDRA(1517, CombatSpells.FIRE_1),
    AZURE(1524, CombatSpells.WATER_1),

    MAGMA_STAFF1(1580, CombatSpells.FIRE_1),
    MAGMA_STAFF2(1581, CombatSpells.FIRE_1),
    MAGMA_STAFF3(1582, CombatSpells.FIRE_1),

    OVERGROWN_STAFF(1586, CombatSpells.EARTH_1),
    OVERGROWN_STAFF1(1587, CombatSpells.EARTH_1),
    OVERGROWN_STAFF2(1588, CombatSpells.EARTH_1),

    AQUATIC_STAFF(1583, CombatSpells.WATER_1),
    AQUATIC_STAFF1(1584, CombatSpells.WATER_1),
    AQUATIC_STAFF2(1585, CombatSpells.WATER_1),

    OWNER_STAFF(752, CombatSpells.WATER_1),


    CANDY_CANE(1463, CombatSpells.CANDY_CANEE),
    CORRUPT_STAFF(2655, CombatSpells.CORRUPT_STAFF),

    SPECTRAL_1(2087, CombatSpells.VOID_1),



    ;

    private int itemId;
    private CombatSpells spell;

    AutoCastSpell(int itemId, CombatSpells spell) {
        this.itemId = itemId;
        this.spell = spell;
    }

    public static CombatSpells getAutoCastSpell(Player player) {
        for (AutoCastSpell d : AutoCastSpell.values()) {
            if (player.getEquipment().get(Equipment.WEAPON_SLOT).getId() == d.getItemId()) {
                return d.getSpell();
            }
        }
        return null;
    }


    public static AutoCastSpell getAutoCast(Player player) {
        for (AutoCastSpell d : AutoCastSpell.values()) {
            if (player.getEquipment().get(Equipment.WEAPON_SLOT).getId() == d.getItemId()) {
                return d;
            }
        }
        return null;
    }

    public int getItemId() {
        return itemId;
    }

    public CombatSpells getSpell() {
        return spell;
    }

}
