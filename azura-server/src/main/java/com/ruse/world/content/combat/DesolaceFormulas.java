package com.ruse.world.content.combat;

import com.ruse.model.Skill;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.content.combat.magic.CombatSpell;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.ruse.world.content.combat.weapon.FightType;
import com.ruse.world.content.skill.impl.summoning.Familiar;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class DesolaceFormulas {

    /*
     * =============================================================================
     * =
     */
    /*
     * ===================================MELEE=====================================
     */

    public static int calculateMaxMeleeHit(Character entity, Character victim) {
        double maxHit = 0;
        if (entity.isNpc()) {
            NPC npc = (NPC) entity;
            maxHit = npc.getDefinition().getMaxHit();
            if (npc.getStrengthWeakened()[0]) {
                maxHit -= (int) ((0.10) * (maxHit));
            } else if (npc.getStrengthWeakened()[1]) {
                maxHit -= (int) ((0.20) * (maxHit));
            } else if (npc.getStrengthWeakened()[2]) {
                maxHit -= (int) ((0.30) * (maxHit));
            }
        } else {
            Player plr = (Player) entity;

            double base = 0;
            double effective = getEffectiveStr(plr);
            double specialBonus = 1;

            double strengthBonus = plr.getBonusManager().getOtherBonus()[0];
            base = (13 + effective + (strengthBonus / 8) + ((effective * strengthBonus) / 65)) / 11;
            if (plr.getEquipment().getItems()[3].getId() == 4718 && plr.getEquipment().getItems()[0].getId() == 4716 && plr.getEquipment().getItems()[4].getId() == 4720 && plr.getEquipment().getItems()[7].getId() == 4722)
                base += ((plr.getSkillManager().getMaxLevel(Skill.HITPOINTS) - plr.getConstitution()) * .045) + 1;
            if (specialBonus > 1)
                base = (base * specialBonus);
            if (victim.isNpc()) {
                NPC npc = (NPC) victim;
                if (npc.getDefenceWeakened()[0]) {
                    base += (int) ((0.10) * (base));
                } else if (npc.getDefenceWeakened()[1]) {
                    base += (int) ((0.20) * (base));
                } else if (npc.getDefenceWeakened()[2]) {
                    base += (int) ((0.30) * (base));
                }
                Familiar playerFamiliar = plr.getSummoning().getFamiliar();
                }

            }
        if (victim != null && victim.isNpc()) {
            maxHit = NpcMaxHitLimit.limit((NPC) victim, maxHit, CombatType.MELEE);
        }

        return (int) Math.floor(maxHit);
    }

    /**
     * Calculates a player's Melee attack level (how likely that they're going to
     * hit through defence)
     *
     * @param plr The player's Meelee attack level
     * @return The player's Melee attack level
     */
    @SuppressWarnings("incomplete-switch")
    public static int getMeleeAttack(Player plr) {
        int attackLevel = plr.getSkillManager().getCurrentLevel(Skill.ATTACK);
        switch (plr.getFightType().getStyle()) {
            case AGGRESSIVE:
                attackLevel += 3;
                break;
            case CONTROLLED:
                attackLevel += 1;
                break;
        }
        // boolean hasVoid = EquipmentBonus.wearingVoid(plr);//, CombatType.MELEE);

        if (PrayerHandler.isActivated(plr, PrayerHandler.CLARITY_OF_THOUGHT)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.05;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.IMPROVED_REFLEXES)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.1;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.INCREDIBLE_REFLEXES)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.CHIVALRY)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.15;
        } else if (PrayerHandler.isActivated(plr, PrayerHandler.PIETY)) {
            attackLevel += plr.getSkillManager().getMaxLevel(Skill.ATTACK) * 0.2;
        }

        attackLevel *= 1;
        int i = (int) plr.getBonusManager().getAttackBonus()[bestMeleeAtk(plr)];
        return (int) (attackLevel + (attackLevel * 0.15) + (i + i * 0.04));
    }

    public static int getMeleeDefence(Player plr) {
        int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE);
        int i = (int) plr.getBonusManager().getDefenceBonus()[bestMeleeDef(plr)];

        if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if(plr.getPrayerActive()[PrayerHandler.HUNTERS_EYE]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.10;
        } else if(plr.getPrayerActive()[PrayerHandler.FORTITUDE]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.50;
        }
            return (int) (defenceLevel + (defenceLevel * 0.15) + (i + i * 0.05));
    }

    public static int bestMeleeDef(Player p) {
        if (p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[1]
                && p.getBonusManager().getDefenceBonus()[0] > p.getBonusManager().getDefenceBonus()[2]) {
            return 0;
        }
        if (p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[0]
                && p.getBonusManager().getDefenceBonus()[1] > p.getBonusManager().getDefenceBonus()[2]) {
            return 1;
        }
        return p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[0]
                || p.getBonusManager().getDefenceBonus()[2] <= p.getBonusManager().getDefenceBonus()[1] ? 0 : 2;
    }

    public static int bestMeleeAtk(Player p) {
        if (p.getBonusManager().getAttackBonus()[0] > p.getBonusManager().getAttackBonus()[1]
                && p.getBonusManager().getAttackBonus()[0] > p.getBonusManager().getAttackBonus()[2]) {
            return 0;
        }
        if (p.getBonusManager().getAttackBonus()[1] > p.getBonusManager().getAttackBonus()[0]
                && p.getBonusManager().getAttackBonus()[1] > p.getBonusManager().getAttackBonus()[2]) {
            return 1;
        }
        return p.getBonusManager().getAttackBonus()[2] <= p.getBonusManager().getAttackBonus()[1]
                || p.getBonusManager().getAttackBonus()[2] <= p.getBonusManager().getAttackBonus()[0] ? 0 : 2;
    }

    /**
     * Obsidian items
     */

    @SuppressWarnings("incomplete-switch")
    public static int getStyleBonus(Player plr) {
        switch (plr.getFightType().getStyle()) {
            case AGGRESSIVE:
            case ACCURATE:
                return 3;
            case CONTROLLED:
                return 1;
        }
        return 0;
    }

    public static double getEffectiveStr(Player plr) {
        return ((plr.getSkillManager().getCurrentLevel(Skill.STRENGTH)) * getPrayerStr(plr)) + getStyleBonus(plr);
    }

    public static double getPrayerStr(Player plr) {
        if (plr.getPrayerActive()[1])
            return 1.05;
        else if (plr.getPrayerActive()[6])
            return 1.1;
        else if (plr.getPrayerActive()[14])
            return 1.15;
        else if (plr.getPrayerActive()[24])
            return 1.18;
        else if (plr.getPrayerActive()[25])
            return 1.23;
        return 1;
    }

    /**
     * Calculates a player's Ranged attack (level). Credits: Dexter Morgan
     *
     * @param plr The player to calculate Ranged attack level for
     * @return The player's Ranged attack level
     */
    public static int getRangedAttack(Player plr) {
        int rangeLevel = plr.getSkillManager().getCurrentLevel(Skill.RANGED);
        // boolean hasVoid = EquipmentBonus.wearingVoid(plr);//, CombatType.RANGED);
        double accuracy = 1;
        rangeLevel *= accuracy;
        if (plr.getCurseActive()[PrayerHandler.SHARP_EYE]) {
            rangeLevel *= 1.05;
        } else if (plr.getPrayerActive()[PrayerHandler.HAWK_EYE]) {
            rangeLevel *= 1.10;
        } else if (plr.getPrayerActive()[PrayerHandler.EAGLE_EYE]) {
            rangeLevel *= 1.15;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            rangeLevel *= 1.22;
        }  else if(plr.getPrayerActive()[PrayerHandler.HUNTERS_EYE]) {
            rangeLevel *= 1.25;
        } else if (plr.getPrayerActive()[33]) {
            rangeLevel *= 1.50;

        }

        return (int) (rangeLevel + (plr.getBonusManager().getAttackBonus()[4] * 2));
    }

    /**
     * Calculates a player's Ranged defence level.
     *
     * @param plr The player to calculate the Ranged defence level for
     * @return The player's Ranged defence level
     */
    public static int getRangedDefence(Player plr) {
        int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE);
        if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if(plr.getPrayerActive()[PrayerHandler.FORTITUDE]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.50;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        }

        return (int) (defenceLevel + plr.getBonusManager().getDefenceBonus()[4]
                + (plr.getBonusManager().getDefenceBonus()[4] / 2));
    }

    public static int getMagicAttack(Player plr) {
        // boolean voidEquipment = EquipmentBonus.wearingVoid(plr);//,
        // CombatType.MAGIC);
        int magicLevel = plr.getSkillManager().getCurrentLevel(Skill.MAGIC);
        magicLevel *= 1;
        if (plr.getPrayerActive()[PrayerHandler.MYSTIC_WILL]) {
            magicLevel *= 1.05;
        } else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_LORE]) {
            magicLevel *= 1.10;
        } else if (plr.getPrayerActive()[PrayerHandler.MYSTIC_MIGHT]) {
            magicLevel *= 1.15;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            magicLevel *= 1.22;
        }  else if (plr.getPrayerActive()[PrayerHandler.DESTRUCTION]) {
            magicLevel *= 1.25;
        }

        return (int) (magicLevel + (plr.getBonusManager().getAttackBonus()[3] * 2));
    }

    /**
     * Calculates a player's magic defence level
     *
     * @param plr The player to calculate magic defence level for
     * @return The player's magic defence level
     */
    public static int getMagicDefence(Player plr) {

        int defenceLevel = plr.getSkillManager().getCurrentLevel(Skill.DEFENCE) / 2
                + plr.getSkillManager().getCurrentLevel(Skill.MAGIC) / 2;

        if (plr.getPrayerActive()[PrayerHandler.THICK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.05;
        } else if (plr.getPrayerActive()[PrayerHandler.ROCK_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.1;
        } else if (plr.getPrayerActive()[PrayerHandler.STEEL_SKIN]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.15;
        } else if(plr.getPrayerActive()[PrayerHandler.FORTITUDE]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.50;
        } else if (plr.getPrayerActive()[PrayerHandler.CHIVALRY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.2;
        } else if (plr.getPrayerActive()[PrayerHandler.PIETY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.RIGOUR]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        } else if (plr.getPrayerActive()[PrayerHandler.AUGURY]) {
            defenceLevel += plr.getSkillManager().getMaxLevel(Skill.DEFENCE) * 0.25;
        }

        return (int) (defenceLevel + plr.getBonusManager().getDefenceBonus()[3]
                + (plr.getBonusManager().getDefenceBonus()[3] / 3));
    }

    public static int getMagicMaxhit(Character attacker, Character victim) {
        int damage = 0;
        CombatSpell spell = attacker.getCurrentlyCasting();
        if (spell != null) {
            if (spell.maximumHit() > 0)
                damage += spell.maximumHit();
            else {
                if (attacker.isNpc()) {
                    damage = ((NPC) attacker).getDefinition().getMaxHit();
                } else {
                    damage = 1;
                }
            }
        }

        if (attacker.isNpc()) {
            if (spell == null) {
                damage = Misc.getRandom(((NPC) attacker).getDefinition().getMaxHit());
            }
            return damage;
        }

        Player p = (Player) attacker;
        // NPC n = (NPC)c;
        double damageMultiplier = 1;

        int maxHit = -1;

        damage *= damageMultiplier;

        if (maxHit > 0) {
            if (damage > maxHit) {
                damage = maxHit;
            }
        }

        if (p.isOpMode() || p.isGodMode()) {
            damage *= 6;
        }

        if (victim.isNpc()) {
            damage = (int) NpcMaxHitLimit.limit((NPC) victim, damage, CombatType.MAGIC);
        }
        return (int) damage;
    }

    public static int getAttackDelay(Player plr) {
        int id = plr.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId();
        String s = ItemDefinition.forId(id).getName().toLowerCase();
        if (id == -1)
            return 4;// unarmed
        RangedWeaponData rangedData = plr.getRangedWeaponData();
        if (rangedData != null) {
            int speed = rangedData.getType().getAttackDelay();
            if (plr.getFightType() == FightType.SHORTBOW_RAPID || plr.getFightType() == FightType.DART_RAPID || plr.getFightType() == FightType.KNIFE_RAPID || plr.getFightType() == FightType.THROWNAXE_RAPID || plr.getFightType() == FightType.JAVELIN_RAPID || plr.getFightType() == FightType.BLOWPIPE_RAPID) {
                speed--;
            }
            return speed;
        }
        switch (id) {
            case 18357:
                return 4;
        }
        return 5;
    }
}
