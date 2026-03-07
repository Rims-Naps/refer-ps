package com.ruse.world.content.combat;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.engine.task.impl.CombatSkullEffect;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.movement.MovementQueue;
import com.ruse.model.movement.PathFinder;
import com.ruse.util.Misc;
import com.ruse.world.clip.region.RegionClipping;
import com.ruse.world.content.BonusManager;
import com.ruse.world.content.ItemDegrading;
import com.ruse.world.content.ItemDegrading.DegradingItem;
import com.ruse.world.content.combat.effect.BurnEffect;
import com.ruse.world.content.combat.effect.BurnEffect.BurnType;
import com.ruse.world.content.combat.magic.CombatAncientSpell;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.combat.range.CombatRangedAmmo.RangedWeaponData;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.combat.weapon.FightStyle;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.npc.NPCMovementCoordinator.CoordinateState;
import com.ruse.world.entity.impl.player.Player;

import java.util.Optional;

import static com.ruse.world.content.combat.CombatType.RANGED;

/**
 * A static factory class containing all miscellaneous methods related to, and
 * used for combat.
 *
 * @author lare96
 * @author Scu11
 * @author Graham
 */
public final class CombatFactory {

    /**
     * The amount of time it takes for cached damage to timeout.
     */
    // Damage cached for currently 5 seconds will not be accounted for.
    public static final long DAMAGE_CACHE_TIMEOUT = 20000;

    /**
     * The amount of damage that will be drained by combat protection prayer.
     */
    // Currently at .20 meaning 20% of damage drained when using the right
    // protection prayer.
    public static final double PRAYER_DAMAGE_REDUCTION = .20;

    /**
     * The rate at which accuracy will be reduced by combat protection prayer.
     */
    // Currently at .255 meaning 25.5% percent chance of canceling damage when
    // using the right protection prayer.
    public static final double PRAYER_ACCURACY_REDUCTION = .90;

    /**
     * The amount of hitpoints the redemption prayer will heal.
     */
    // Currently at .25 meaning hitpoints will be healed by 25% of the remaining
    // prayer points when using redemption.
    public static final double REDEMPTION_PRAYER_HEAL = .25;

    /**
     * The maximum amount of damage inflicted by retribution.
     */
    // Damage between currently 0-15 will be inflicted if in the specified
    // radius when the retribution prayer effect is activated.
    public static final int MAXIMUM_RETRIBUTION_DAMAGE = 150;

    /**
     * The radius that retribution will hit players in.
     */
    // All players within currently 5 squares will get hit by the retribution
    // effect.
    public static final int RETRIBUTION_RADIUS = 5;

    /**
     * The default constructor, will throw an {@link UnsupportedOperationException}
     * if instantiated.
     */
    private CombatFactory() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * Determines if the entity is wearing full veracs.
     *
     * @param entity the entity to determine this for.
     * @return true if the player is wearing full veracs.
     */
    public static boolean fullVeracs(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals("Verac the Defiled")
                : ((Player) entity).getEquipment().containsAll(4753, 4757, 4759, 4755);
    }

    /**
     * Determines if the entity is wearing full dharoks.
     *
     * @param entity the entity to determine this for.
     * @return true if the player is wearing full dharoks.
     */
    public static boolean fullDharoks(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals("Dharok the Wretched")
                : ((Player) entity).getEquipment().containsAll(4716, 4720, 4722, 4718);
    }

    /**
     * Determines if the entity is wearing full karils.
     *
     * @param entity the entity to determine this for.
     * @return true if the player is wearing full karils.
     */
    public static boolean fullKarils(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals("Karil the Tainted")
                : ((Player) entity).getEquipment().containsAll(4732, 4736, 4738, 4734);
    }

    /**
     * Determines if the entity is wearing full ahrims.
     *
     * @param entity the entity to determine this for.
     * @return true if the player is wearing full ahrims.
     */
    public static boolean fullAhrims(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals("Ahrim the Blighted")
                : ((Player) entity).getEquipment().containsAll(4708, 4712, 4714, 4710);
    }

    /**
     * Determines if the entity is wearing full torags.
     *
     * @param entity the entity to determine this for.
     * @return true if the player is wearing full torags.
     */
    public static boolean fullTorags(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals("Torag the Corrupted")
                : ((Player) entity).getEquipment().containsAll(4745, 4749, 4751, 4747);
    }

    /**
     * Determines if the entity is wearing full guthans.
     *
     * @param entity the entity to determine this for.
     * @return true if the player is wearing full guthans.
     */
    public static boolean fullGuthans(Character entity) {
        return entity.isNpc() ? ((NPC) entity).getDefinition().getName().equals("Guthan the Infested")
                : ((Player) entity).getEquipment().containsAll(4724, 4728, 4730, 4726);
    }

    /**
     * Determines if the player is wielding a crystal bow.
     *
     * @param player the player to determine for.
     * @return true if the player is wielding a crystal bow.
     */
    public static boolean crystalBow(Player player) {
        Item item = player.getEquipment().get(Equipment.WEAPON_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().contains("crystal bow");
    }

    public static boolean toxicblowpipe(Player p) {
        Item item = p.getEquipment().get(Equipment.WEAPON_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().equalsIgnoreCase("toxic blowpipe");
    }

    public static boolean zarytebow(Player p) {
        Item item = p.getEquipment().get(Equipment.WEAPON_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().equalsIgnoreCase("zaryte bow");
    }
    public static boolean darkBow(Player player) {
        Item item = player.getEquipment().get(Equipment.WEAPON_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().contains("dark bow");
    }
    /**
     * Determines if the player is wielding a dark bow.
     *
     * @param player the player to determine for.
     * @return true if the player is wielding a dark bow.
     */
    public static boolean firesoul(Player player) {
        Item item = player.getEquipment().get(Equipment.AMMUNITION_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().contains("fire soul");
    }

    public static boolean watersoul(Player player) {
        Item item = player.getEquipment().get(Equipment.AMMUNITION_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().contains("water soul");
    }

    public static boolean earthsoul(Player player) {
        Item item = player.getEquipment().get(Equipment.AMMUNITION_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().contains("earth soul");
    }

    public static boolean vsoul(Player player) {
        Item item = player.getEquipment().get(Equipment.AMMUNITION_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().contains("Forgotten soul");
    }
    public static boolean vsoul2(Player player) {
        Item item = player.getEquipment().get(Equipment.AMMUNITION_SLOT);
        return item != null && item.getDefinition().getName().toLowerCase().endsWith("ris soul(2)");
    }


    /**
     * Determines if the player has arrows equipped.
     *
     * @param player the player to determine for.
     * @return true if the player has arrows equipped.
     */
    public static boolean arrowsEquipped(Player player) {
        Item item;
        return (item = player.getEquipment().get(Equipment.AMMUNITION_SLOT)) != null && !(!item.getDefinition().getName().endsWith("arrow") && !item.getDefinition().getName().endsWith("arrowp")
                && !item.getDefinition().getName().endsWith("arrow(p+)")
                && !item.getDefinition().getName().endsWith("arrow(p++)"));

    }

    /**
     * Determines if the player has bolts equipped.
     *
     * @param player the player to determine for.
     * @return true if the player has bolts equipped.
     */
    public static boolean boltsEquipped(Player player) {
        Item item;
        return (item = player.getEquipment().get(Equipment.AMMUNITION_SLOT)) != null && item.getDefinition().getName().toLowerCase().contains("bolts");
    }

    /**
     * Attempts to poison the argued {@link Character} with the argued
     * {@link BurnType}. This method will have no effect if the entity is already
     * poisoned.
     *
     * @param entity     the entity that will be poisoned, if not already.
     * @param burnType the poison type that this entity is being inflicted with.
     */
    public static void burnentity(Character entity, Optional<BurnType> burnType) {

        // We are already poisoned or the poison type is invalid, do nothing.
        if (entity.isOnFire() || !burnType.isPresent()) {
            return;
        }

        // If the entity is a player, we check for poison immunity. If they have
        // no immunity then we send them a message telling them that they are
        // poisoned.
        if (entity.isPlayer()) {
            Player player = (Player) entity;
            if (player.getPoisonImmunity() > 0)
                return;
        }

        entity.setBurnDamage(burnType.get().getDamage());
        TaskManager.submit(new BurnEffect(entity));
    }

    /**
     * Attempts to poison the argued {@link Character} with the argued
     * {@link BurnType}. This method will have no effect if the entity is already
     * poisoned.
     *
     * @param entity     the entity that will be poisoned, if not already.
     * @param burnType the poison type that this entity is being inflicted with.
     */
    public static void burnentity(Character entity, BurnType burnType) {
        burnentity(entity, Optional.ofNullable(burnType));
    }

    /**
     * Attempts to put the skull icon on the argued player, including the effect
     * where the player loses all item upon death. This method will have no effect
     * if the argued player is already skulled.
     *
     * @param player the player to attempt to skull to.
     */
    public static void skullPlayer(Player player) {

        // We are already skulled, return.
        if (player.getSkullTimer() > 0) {
            return;
        }

        // Otherwise skull the player as normal.
        player.setSkullTimer(1200);
        player.setSkullIcon(1);
        player.getPacketSender().sendMessage("@red@You have been skulled!");
        TaskManager.submit(new CombatSkullEffect(player));
        player.getUpdateFlag().flag(Flag.APPEARANCE);
    }

    /**
     * Calculates the combat level difference for wilderness player vs. player
     * combat.
     *
     * @param combatLevel      the combat level of the first person.
     * @param otherCombatLevel the combat level of the other person.
     * @return the combat level difference.
     */
    public static int combatLevelDifference(int combatLevel, int otherCombatLevel) {
        if (combatLevel > otherCombatLevel) {
            return (combatLevel - otherCombatLevel);
        } else if (otherCombatLevel > combatLevel) {
            return (otherCombatLevel - combatLevel);
        } else {
            return 0;
        }
    }

    public static int getLevelDifference(Player player, boolean up) {
        int max = player.getLocation() == Location.WILDERNESS ? 126 : 138;
        int wildLevel = player.getWildernessLevel() + 5; // + 5 to make wild more active
        int combatLevel = player.getSkillManager().getCombatLevel();
        int difference = up ? combatLevel + wildLevel : combatLevel - wildLevel;
        return difference < 3 ? 3 : difference > max && up ? max : difference;
    }

    /**
     * Generates a random {@link Hit} based on the argued entity's stats.
     *
     * @param entity the entity to generate the random hit for.
     * @param victim the victim being attacked.
     * @param type   the combat type being used.
     * @return the melee hit.
     */
    public static Hit getHit(Character entity, Character victim, CombatType type) {
        Hitmask hit = Hitmask.NEUTRAL;
        int maxhit = 0;
        switch (type) {
            case MELEE:
                maxhit = Maxhits.melee(entity, victim) / 10;
                int damage = Misc.inclusiveRandom(0, maxhit) * 10;
                if (entity.isPlayer() && victim.isNpc()) {
                    Player player = (Player) entity;
                    hit = ElementalUtils.getTypingForHit(player);
                }
                return new Hit(damage, hit, CombatIcon.MELEE);
            case RANGED:
                maxhit = Maxhits.ranged(entity, victim) / 10;
                int calc = Misc.inclusiveRandom(0, maxhit) * 10;
                if (entity.isPlayer() && victim.isNpc()) {
                    Player player = (Player) entity;
                    hit = ElementalUtils.getTypingForHit(player);
                    if (player.getEquipment().contains(22006) && player.getLastCombatType() == RANGED) {
                        NPC npc = (NPC) victim;
                        if (!npcsDeathDartDontWork(npc)) {
                            calc = (int) victim.getConstitution();
                        } else {
                            player.sendMessage("The Death-touch dart didn't work on this.");
                        }
                    }
                }
                return new Hit(calc, hit, CombatIcon.RANGED);
            case MAGIC:
                maxhit = Maxhits.magic(entity, victim) / 10;
                if (entity.isPlayer() && victim.isNpc()) {
                    Player player = (Player) entity;
                    hit = ElementalUtils.getTypingForHit(player);
                }
                return new Hit(Misc.inclusiveRandom(0, maxhit) * 10, hit, CombatIcon.MAGIC);
            case DRAGON_FIRE:
                return new Hit(Misc.inclusiveRandom(0, CombatFactory.calculateMaxDragonFireHit(entity, victim)),
                        Hitmask.NEUTRAL, CombatIcon.MAGIC);
            default:
                throw new IllegalArgumentException("Invalid combat type: " + type);
        }
    }

    public static boolean npcsDeathDartDontWork(NPC npc) {
        int id = npc.getId();
        return id == 8013;

    }

    /**
     * A flag that determines if the entity's attack will be successful based on the
     * argued attacker's and victim's stats.
     *
     * @param attacker the attacker who's hit is being calculated for accuracy.
     * @param victim   the victim who's awaiting to either be hit or dealt no
     *                 damage.
     * @param type     the type of combat being used to deal the hit.
     * @return true if the hit was successful, or in other words accurate.
     */
    @SuppressWarnings("incomplete-switch")
    public static boolean rollAccuracy(Character attacker, Character victim, CombatType type) {

        if (attacker.isPlayer() && victim.isPlayer()) {
            Player p1 = (Player) attacker;
            Player p2 = (Player) victim;
            switch (type) {
                case MAGIC:
                    int mageAttk = DesolaceFormulas.getMagicAttack(p1);
                    return Misc.getRandom(DesolaceFormulas.getMagicDefence(p2)) < Misc.getRandom((mageAttk / 2))
                            + Misc.getRandom((int) (mageAttk / 2.1));
                case MELEE:
                    int def = 1 + DesolaceFormulas.getMeleeDefence(p2);
                    return Misc.getRandom(def) < Misc.getRandom(1 + DesolaceFormulas.getMeleeAttack(p1)) + (def / 4.5);
                case RANGED:
                    return Misc.getRandom(10 + DesolaceFormulas.getRangedDefence(p2)) < Misc
                            .getRandom(15 + DesolaceFormulas.getRangedAttack(p1));
            }
        } else if (attacker.isPlayer() && victim.isNpc() && type != CombatType.MAGIC) {
            Player p1 = (Player) attacker;
            NPC n = (NPC) victim;
            int percentBoost = 0;
            switch (type) {
                case MELEE:
                    int def = 1 + n.getDefinition().getDefenceMelee();
                    return Misc.getRandom(def) < Misc.getRandom(5 + DesolaceFormulas.getMeleeAttack(p1) + percentBoost) + (def / 4);
                case RANGED:
                    return Misc.getRandom(5 + n.getDefinition().getDefenceRange()) < Misc
                            .getRandom(5 + DesolaceFormulas.getRangedAttack(p1) + percentBoost);
            }
        }

        boolean veracEffect = false;

        if (type == CombatType.MELEE) {
            if (CombatFactory.fullVeracs(attacker)) {
                if (Misc.RANDOM.nextInt(8) == 3) {
                    veracEffect = true;
                }
            }
        }

        if (type == CombatType.DRAGON_FIRE)
            type = CombatType.MAGIC;
        double random =  Math.random() * 10;
        double prayerMod = 1;
        double equipmentBonus = 1;
        double specialBonus = 1;
        int styleBonus = 0;
        int bonusType = -1;
        if (attacker.isPlayer()) {
            Player player = (Player) attacker;

            equipmentBonus = type == CombatType.MAGIC
                    ? player.getBonusManager().getAttackBonus()[BonusManager.ATTACK_MAGIC]
                    : player.getBonusManager().getAttackBonus()[player.getFightType().getBonusType()];
            bonusType = player.getFightType().getCorrespondingBonus();

            if (type == CombatType.MELEE) {
                if (PrayerHandler.isActivated(player, PrayerHandler.CLARITY_OF_THOUGHT)) {
                    prayerMod = 1.05;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.IMPROVED_REFLEXES)) {
                    prayerMod = 1.10;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.INCREDIBLE_REFLEXES)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.CHIVALRY)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.PIETY)) {
                    prayerMod = 1.20;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                    prayerMod = 1.20;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                    prayerMod = 1.20;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.HUNTERS_EYE)) {
                    prayerMod = 1.20;
                }

            } else if (type == CombatType.RANGED) {
                if (PrayerHandler.isActivated(player, PrayerHandler.SHARP_EYE)) {
                    prayerMod = 1.05;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.HAWK_EYE)) {
                    prayerMod = 1.10;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.EAGLE_EYE)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                    prayerMod = 1.22;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.HUNTERS_EYE)) {
                    prayerMod = 1.22;
                }
            } else if (type == CombatType.MAGIC) {
                if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_WILL)) {
                    prayerMod = 1.05;
                } if (PrayerHandler.isActivated(player, PrayerHandler.DESTRUCTION)) {
                    prayerMod = 1.05;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_LORE)) {
                    prayerMod = 1.10;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.MYSTIC_MIGHT)) {
                    prayerMod = 1.15;
                } else if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                    prayerMod = 1.22;
                }
            }

            if (player.getFightType().getStyle() == FightStyle.ACCURATE) {
                styleBonus = 3;
            } else if (player.getFightType().getStyle() == FightStyle.CONTROLLED) {
                styleBonus = 1;
            }


        }

        double attackCalc = Math.floor(equipmentBonus + attacker.getBaseAttack(type)) + 8;

        attackCalc *= prayerMod;
        attackCalc += styleBonus;

        if (equipmentBonus < -67) {
            attackCalc = Misc.exclusiveRandom(8) == 0 ? attackCalc : 0;
        }
        attackCalc *= specialBonus;

        equipmentBonus = 1;
        prayerMod = 1;
        styleBonus = 0;
        if (victim.isPlayer()) {
            Player player = (Player) victim;

            if (bonusType == -1) {
                equipmentBonus = type == CombatType.MAGIC
                        ? player.getBonusManager().getDefenceBonus()[BonusManager.DEFENCE_MAGIC]
                        : player.getSkillManager().getCurrentLevel(Skill.DEFENCE);
            } else {
                equipmentBonus = type == CombatType.MAGIC
                        ? player.getBonusManager().getDefenceBonus()[BonusManager.DEFENCE_MAGIC]
                        : player.getBonusManager().getDefenceBonus()[bonusType];
            }

            if (PrayerHandler.isActivated(player, PrayerHandler.THICK_SKIN)) {
                prayerMod = 1.05;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.ROCK_SKIN)) {
                prayerMod = 1.10;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.STEEL_SKIN)) {
                prayerMod = 1.15;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.CHIVALRY)) {
                prayerMod = 1.20;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.PIETY)) {
                prayerMod = 1.25;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.RIGOUR)) {
                prayerMod = 1.25;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.AUGURY)) {
                prayerMod = 1.25;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.DESTRUCTION)) {
                prayerMod = 1.25;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.HUNTERS_EYE)) {
                prayerMod = 1.25;
            } else if (PrayerHandler.isActivated(player, PrayerHandler.FORTITUDE)) {
                prayerMod = 8.0;
            }

            if (player.getFightType().getStyle() == FightStyle.DEFENSIVE) {
                styleBonus = 3;
            } else if (player.getFightType().getStyle() == FightStyle.CONTROLLED) {
                styleBonus = 1;
            }
        }

        double defenceCalc = Math.floor(equipmentBonus + victim.getBaseDefence(type)) + 8;
        defenceCalc *= prayerMod;
        defenceCalc += styleBonus;

        if (equipmentBonus < -67) {
            defenceCalc = Misc.exclusiveRandom(8) == 0 ? defenceCalc : 0;
        }
        if (veracEffect) {
            defenceCalc = 0;
        }
        double A = Math.floor(attackCalc);
        double D = Math.floor(defenceCalc);
        double hitSucceed = A < D ? (A - 1.0) / (2.0 * D) : 1.0 - (D + 1.0) / (2.0 * A);
        hitSucceed = hitSucceed >= 1.0 ? 0.99 : hitSucceed <= 0.0 ? 0.01 : hitSucceed;
        return hitSucceed >= Misc.RANDOM.nextDouble();
    }

    /**
     * Calculates the maximum melee hit for the argued {@link Character} without
     * taking the victim into consideration.
     *
     * @param entity the entity to calculate the maximum hit for.
     * @param victim the victim being attacked.
     * @return the maximum melee hit that this entity can deal.
     */
    @SuppressWarnings("incomplete-switch")
    public static int calculateMaxMeleeHit(Character entity, Character victim) {
        int maxHit = 0;

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
            return maxHit;
        }

        Player player = (Player) entity;
        double specialMultiplier = 1;
        double prayerMultiplier = 1;
        // amulet(e) = 1.2
        double otherBonusMultiplier = 1;
        double voidDmgBonus = 1;
        int strengthLevel = player.getSkillManager().getCurrentLevel(Skill.STRENGTH);
        int attackLevel = player.getSkillManager().getCurrentLevel(Skill.ATTACK);
        int combatStyleBonus = 0;

        switch (player.getFightType().getStyle()) {
            case AGGRESSIVE:
                combatStyleBonus = 3;
                break;
            case CONTROLLED:
                combatStyleBonus = 1;
                break;
        }

        if (strengthLevel <= 10 || attackLevel <= 10) {
            otherBonusMultiplier = 1.8;
        }

        int effectiveStrengthDamage = (int) ((strengthLevel * prayerMultiplier * otherBonusMultiplier)
                + combatStyleBonus);
        double baseDamage = 1.3 + (effectiveStrengthDamage / 10)
                + (player.getBonusManager().getOtherBonus()[BonusManager.BONUS_STRENGTH] / 80)
                + ((effectiveStrengthDamage * player.getBonusManager().getOtherBonus()[BonusManager.BONUS_STRENGTH])
                / 640);



        //SOUL ENERGY AURA
        // boolean soulEnergyEquiped = player.getEquipment().contains(17021) || player.getEquipment().contains(2613) ;
      /*  if (player.getEquipment().contains(2613) || player.getEquipment().contains(17021)) {
            maxHit += (player.getSkillManager().getMaxLevel(Skill.HITPOINTS) - player.getSkillManager().getCurrentLevel(Skill.HITPOINTS)) * 0.25;
        }*/

        maxHit = (int) (baseDamage * specialMultiplier * voidDmgBonus);
        maxHit *= 10;
        if (victim.isNpc()) {
            NPC npc = (NPC) victim;
            if (npc.getDefenceWeakened()[0]) {
                maxHit += (int) ((0.010) * (maxHit));
            } else if (npc.getDefenceWeakened()[1]) {
                maxHit += (int) ((0.020) * (maxHit));
            } else if (npc.getDefenceWeakened()[2]) {
                maxHit += (int) ((0.030) * (maxHit));
            }

        }

        return maxHit;

    }

    /**
     * Calculates the maximum ranged hit for the argued {@link Character} without
     * taking the victim into consideration.
     *
     * @param entity the entity to calculate the maximum hit for.
     * @param victim the victim being attacked.
     * @return the maximum ranged hit that this entity can deal.
     */
    @SuppressWarnings("incomplete-switch")
    public static int calculateMaxRangedHit(Character entity, Character victim) {
        int maxHit = 0;
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
            return maxHit;
        }

        Player player = (Player) entity;

        double specialMultiplier = 1;
        double prayerMultiplier = 1;
        double otherBonusMultiplier = 1;
        double voidDmgBonus = 1;

        int rangedStrength = ((int) player.getBonusManager().getAttackBonus()[4] / 10);
        if (player.getRangedWeaponData() != null)
            rangedStrength += (RangedWeaponData.getAmmunitionData(player).getStrength());
        int rangeLevel = player.getSkillManager().getCurrentLevel(Skill.RANGED);
        int combatStyleBonus = 0;

        switch (player.getFightType().getStyle()) {
            case ACCURATE:
                combatStyleBonus = 3;
                break;
        }

        int effectiveRangeDamage = (int) ((rangeLevel * prayerMultiplier * otherBonusMultiplier) + combatStyleBonus);
        double baseDamage = 1.3 + (effectiveRangeDamage / 10) + (rangedStrength / 80)
                + ((effectiveRangeDamage * rangedStrength) / 640);

        maxHit = (int) (baseDamage * specialMultiplier * voidDmgBonus);

        if (victim != null && victim.isNpc()) {
            NPC npc = (NPC) victim;
            if (npc.getDefenceWeakened()[0]) {
                maxHit += (int) ((0.10) * (maxHit));
            } else if (npc.getDefenceWeakened()[1]) {
                maxHit += (int) ((0.20) * (maxHit));
            } else if (npc.getDefenceWeakened()[2]) {
                maxHit += (int) ((0.30) * (maxHit));
            }
        }

        maxHit *= 10;
        if (victim != null && victim.isNpc()) {
            maxHit = (int) NpcMaxHitLimit.limit((NPC) victim, maxHit, CombatType.RANGED);
        }
        return maxHit;
    }

    public static int calculateMaxDragonFireHit(Character e, Character v) {
        int baseMax = 90;
        return baseMax;
    }



    /**
     * A series of checks performed before the entity attacks the victim.
     * <p>
     * <p>
     * the builder to perform the checks with.
     *
     * @return true if the entity passed the checks, false if they did not.
     */
    public static boolean checkHook(Character entity, Character victim) {

        // Check if we need to reset the combat session.
        if (!victim.isRegistered() || !entity.isRegistered() || entity.getConstitution() <= 0
                || victim.getConstitution() <= 0) {
            entity.getCombatBuilder().reset(true);
            return false;
        }

        // Here we check if the victim has teleported away.
        if (victim.isPlayer()) {
            if (((Player) victim).isTeleporting()
                    || !Location.ignoreFollowDistance(entity)
                    && !Locations.goodDistance(victim.getPosition(), entity.getPosition(), 40)
                    || (((Player) victim).isPlayerLocked() || ((Player) victim).isGroupIronmanLocked())) {
                entity.getCombatBuilder().cooldown = 10;
                entity.getMovementQueue().setFollowCharacter(null);
                return false;
            }
        }

        if (victim.isPlayer() && entity.isPlayer() && CombatFactory.zarytebow((Player) entity) && victim != null
                && entity != null) {
            return false;
        }

        if (victim.isNpc() && entity.isPlayer()) {
            NPC npc = (NPC) victim;
            if (npc.getSpawnedFor() != null && npc.getSpawnedFor().getIndex() != ((Player) entity).getIndex()) {
                ((Player) entity).getPacketSender().sendMessage("That's not your enemy to fight.");
                entity.getCombatBuilder().reset(true);
                return false;
            }

            if (npc.isSummoningNpc()) {
                Player player = ((Player) entity);
                if (player.getLocation() != Location.WILDERNESS) {
                    player.getPacketSender().sendMessage("You can only attack familiars in the wilderness.");
                    player.getCombatBuilder().reset(true);
                    return false;
                } else if (npc.getLocation() != Location.WILDERNESS) {
                    player.getPacketSender().sendMessage("That familiar is not in the wilderness.");
                    player.getCombatBuilder().reset(true);
                    return false;
                }
                /** DEALING DMG TO THEIR OWN FAMILIAR **/
                if (player.getSummoning().getFamiliar() != null
                        && player.getSummoning().getFamiliar().getSummonNpc() != null
                        && player.getSummoning().getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
                    return false;
                }
            }
        }

        // Here we check if we are already in combat with another entity.
       /* if (entity.getCombatBuilder().getLastAttacker() != null && !Location.inMulti(entity)
                && entity.getCombatBuilder().isBeingAttacked()
                && !victim.equals(entity.getCombatBuilder().getLastAttacker())) {
            if (entity.isPlayer())
                ((Player) entity).getPacketSender().sendMessage("You are already under attack!");
            entity.getCombatBuilder().reset(true);
            return false;
        }*/

        // Here we check if the entity we are attacking is already in
        // combat.
        if (!(entity.isNpc() && ((NPC) entity).isSummoningNpc())) {
            boolean allowAttack = false;
            boolean isMultiNPC = victim.isNpc() && (((NPC) victim).getDefinition().isMulti());
            if (victim.getCombatBuilder().getLastAttacker() != null && !Location.inMulti(entity)
                    && victim.getCombatBuilder().isBeingAttacked()
                    && !victim.getCombatBuilder().getLastAttacker().equals(entity)) {

                if (victim.getCombatBuilder().getLastAttacker().isNpc()) {
                    NPC npc = (NPC) victim.getCombatBuilder().getLastAttacker();
                    if (npc.isSummoningNpc()) {
                        if (entity.isPlayer()) {
                            Player player = (Player) entity;
                            if (player.getSummoning().getFamiliar() != null
                                    && player.getSummoning().getFamiliar().getSummonNpc() != null && player
                                    .getSummoning().getFamiliar().getSummonNpc().getIndex() == npc.getIndex()) {
                                player.getPacketSender().sendMessage("Summoning only works in multi for now...");
                                allowAttack = false;
                                // getting source tree to detect this zzz.
                            }
                        }
                    }
                }

                if (!allowAttack && !isMultiNPC) {
                    if (entity.isPlayer())
                        ((Player) entity).getPacketSender().sendMessage("They are already under attack!");
                    entity.getCombatBuilder().reset(true);
                    return false;
                }
            }
        }

        // Check if the victim is still in the wilderness, and check if the
        if (entity.isPlayer()) {
            if (victim.isPlayer()) {
                if (!properLocation((Player) entity, (Player) victim)) {
                    entity.getCombatBuilder().reset(true);
                    entity.setPositionToFace(victim.getPosition());
                    return false;
                }
            }
            if (((Player) entity).isCrossingObstacle()) {
                entity.getCombatBuilder().reset(true);
                return false;
            }
        }

        // Check if the npc needs to retreat.
        if (entity.isNpc()) {
            NPC n = (NPC) entity;
            if (!Location.ignoreFollowDistance(n)  && !n.isSummoningNpc()) { // Stops combat
                // for npcs if
                // too far away
                if (n.getPosition().isWithinDistance(victim.getPosition(), 1)) {
                    return true;
                }
                if (!n.getPosition().isWithinDistance(n.getDefaultPosition(),
                        10 + n.getMovementCoordinator().getCoordinator().getRadius())) {
                    n.getMovementQueue().reset();
                    n.getMovementCoordinator().setCoordinateState(CoordinateState.AWAY);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks if the entity is close enough to attack.
     *
     * @param builder the builder used to perform the check.
     * @return true if the entity is close enough to attack, false otherwise.
     */
    public static boolean checkAttackDistance(CombatBuilder builder) {
        return checkAttackDistance(builder.getCharacter(), builder.getVictim());
    }

    public static boolean checkAttackDistance(Character a, Character b) {

        Position attacker = a.getPosition();
        Position victim = b.getPosition();

        if (a.isNpc() && ((NPC) a).isSummoningNpc()) {
            return Locations.goodDistance(attacker, victim, a.getSize());
        }


        if (a.getCombatBuilder().getStrategy() == null)
            a.getCombatBuilder().determineStrategy();
        CombatStrategy strategy = a.getCombatBuilder().getStrategy();
        int distance = strategy.attackDistance(a);

        if (a.isPlayer() && strategy.getCombatType() != CombatType.MELEE) {
            if (b.getSize() >= 2)
                distance += b.getSize() - 1;
        }

        MovementQueue movement = a.getMovementQueue();
        MovementQueue otherMovement = b.getMovementQueue();

        // We're moving so increase the distance.
        if (!movement.isMovementDone() && !otherMovement.isMovementDone() && !movement.isLockMovement()
                && !a.isFrozen()) {
            distance += 1;

            // We're running so increase the distance even more.
            // XXX: Might have to change this back to 1 or even remove it, not
            // sure what it's like on actual runescape. Are you allowed to
            // attack when the entity is trying to run away from you?
            if (movement.isRunToggled()) {
                distance += 2;
            }
        }

        /*
         * Clipping checks and diagonal blocking by gabbe
         */

        boolean sameSpot = attacker.equals(victim) && !a.getMovementQueue().isMoving()
                && !b.getMovementQueue().isMoving();
        boolean goodDistance = !sameSpot
                && Locations.goodDistance(attacker.getX(), attacker.getY(), victim.getX(), victim.getY(), distance);
        boolean projectilePathBlocked = false;
        if (a.isPlayer()
                && (strategy.getCombatType() == CombatType.RANGED
                || strategy.getCombatType() == CombatType.MAGIC && ((Player) a).getCastSpell() != null
                && !(((Player) a).getCastSpell() instanceof CombatAncientSpell))
                || a.isNpc() && strategy.getCombatType() == CombatType.MELEE) {
            if (!RegionClipping.canProjectileAttack(b, a))
                projectilePathBlocked = true;
        }
        if (!projectilePathBlocked && goodDistance) {
            if (strategy.getCombatType() == CombatType.MELEE && RegionClipping.isInDiagonalBlock(b, a)) {
                PathFinder.findPath(a, victim.getX(), victim.getY() + 1, true, 1, 1);
                return false;
            } else
                a.getMovementQueue().reset();
            return true;
        } else if (projectilePathBlocked || !goodDistance) {
            a.getMovementQueue().setFollowCharacter(b);
            return false;
        }
        // Check if we're within the required distance.
        return attacker.isWithinDistance(victim, distance);
    }

    /**
     * Applies combat prayer effects to the calculated hits.
     *
     * @param container the combat container that holds the hits.
     * @param builder   the builder to apply prayer effects to.
     */
    protected static void applyPrayerProtection(CombatContainer container, CombatBuilder builder) {

        // If we aren't checking the accuracy, then don't bother doing any of
        // this.
        if (!container.isCheckAccuracy() || builder.getVictim() == null) {
            return;
        }

        // The attacker is an npc, and the victim is a player so we completely
        // cancel the hits if the right prayer is active.

        if (builder.getVictim().isPlayer()) {
            Player victim = (Player) builder.getVictim();
            if (victim.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 13740
                    || victim.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 13742) {

                if (PrayerHandler.isActivated(victim, PrayerHandler.getProtectingPrayer(container.getCombatType()))
                        || CurseHandler.isActivated(victim,
                        CurseHandler.getProtectingPrayer(container.getCombatType()))) {
                    container.allHits(context -> {
                        int hit = (int) context.getHit().getDamage();
                        context.setAccurate(false);
                        context.getHit().incrementAbsorbedDamage(hit);
                    });
                } else {
                    if (Misc.getRandom(10) <= 7) {
                        container.allHits(context -> {
                            if (PrayerHandler.isActivated(victim,
                                    PrayerHandler.getProtectingPrayer(container.getCombatType()))
                                    || CurseHandler.isActivated(victim,
                                    CurseHandler.getProtectingPrayer(container.getCombatType()))) {
                                return; // we don't want to do the calculation now if they are praying against the right
                                // style.
                            }
                            if (context.getHit().getDamage() > 10) {
                                if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) > 0) {
                                    int prayerLost = (int) (context.getHit().getDamage() * 0.1);
                                    if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) >= prayerLost) {
                                        context.getHit().incrementAbsorbedDamage((int) (context.getHit().getDamage()
                                                - (context.getHit().getDamage() * 0.75)));
                                        if (victim.getEquipment().getItems()[Equipment.WINGS].getId() != 1486) {
                                            victim.getSkillManager().setCurrentLevel(Skill.PRAYER,
                                                    victim.getSkillManager().getCurrentLevel(Skill.PRAYER) - prayerLost);
                                        }
                                        if (victim.isSpiritDebug()) {
                                            victim.getPacketSender()
                                                    .sendMessage(
                                                            "Your spirit shield has drained " + prayerLost
                                                                    + " prayer points to absorb "
                                                                    + (int) (context.getHit().getDamage()
                                                                    - (context.getHit().getDamage() * 0.75))
                                                                    + " damage.");
                                        }
                                    }
                                }
                            } else {
                                if (victim.isSpiritDebug()) {
                                    victim.getPacketSender()
                                            .sendMessage("Spirit Shield did not activate as damage was under 10.");
                                }
                            }
                        });
                    } else {
                        if (victim.isSpiritDebug()) {
                            victim.getPacketSender()
                                    .sendMessage("Your shield was not in the 70% RNG required to activate it.");
                        }
                    }
                }
            }
            if (builder.getCharacter().isNpc()) {
                NPC attacker = (NPC) builder.getCharacter();
                // Except for verac of course :)
                if (attacker.getId() == 2030) {
                    return;
                }
                // It's not verac so we cancel all of the hits.
                if (PrayerHandler.isActivated(victim, PrayerHandler.getProtectingPrayer(container.getCombatType()))
                        || CurseHandler.isActivated(victim,
                        CurseHandler.getProtectingPrayer(container.getCombatType()))) {
                    container.allHits(context -> {
                        int hit = (int) context.getHit().getDamage();
                        if (attacker.getId() == 2745) { // Jad
                            context.setAccurate(false);
                            context.getHit().incrementAbsorbedDamage(hit);
                        } else {
                            // now that we know they're praying, check if they also have the spirit shield.
                            if (victim.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 13740
                                    || victim.getEquipment().getItems()[Equipment.SHIELD_SLOT].getId() == 13742) {
                                if (victim.isSpiritDebug()) {
                                    victim.getPacketSender()
                                            .sendMessage("Original DMG: " + context.getHit().getDamage());
                                }
                                double reduceRatio = attacker.getId() == 1158 || attacker.getId() == 1160 ? 0.4 : 0.8;
                                double mod = Math.abs(1 - reduceRatio);
                                context.getHit().incrementAbsorbedDamage((int) (hit - (hit * mod)));
                                mod = Math.round(Misc.RANDOM.nextDouble() * 100.0) / 100.0;
                                if (mod <= CombatFactory.PRAYER_ACCURACY_REDUCTION) {
                                    context.setAccurate(false);
                                }
                                if (victim.isSpiritDebug()) {
                                    victim.getPacketSender().sendMessage(
                                            "Prayer method finished. New DMG: " + context.getHit().getDamage()
                                                    + " | total absorbed: " + context.getHit().getAbsorb());
                                }
                                if (Misc.getRandom(10) <= 7) {
                                    if (context.getHit().getDamage() > 10) {
                                        if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) > 0) {
                                            int prayerLost = (int) (context.getHit().getDamage() * 0.1);
                                            if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) >= prayerLost) {
                                                context.getHit()
                                                        .incrementAbsorbedDamage((int) (context.getHit().getDamage()
                                                                - (context.getHit().getDamage() * 0.75)));
                                                if (victim.getEquipment().getItems()[Equipment.WINGS].getId() != 1486) {
                                                    victim.getSkillManager().setCurrentLevel(Skill.PRAYER, victim.getSkillManager()
                                                            .getCurrentLevel(Skill.PRAYER) - prayerLost);
                                                }
                                                if (victim.isSpiritDebug()) {
                                                    victim.getPacketSender()
                                                            .sendMessage("Your spirit shield has drained " + prayerLost
                                                                    + " prayer points to absorb "
                                                                    + (int) (context.getHit().getDamage()
                                                                    - (context.getHit().getDamage() * 0.75))
                                                                    + " damage.");
                                                }
                                            }
                                        }
                                    } else {
                                        if (victim.isSpiritDebug()) {
                                            victim.getPacketSender().sendMessage(
                                                    "Spirit Shield did not activate as damage was under 10.");
                                        }
                                    }
                                } else {
                                    if (victim.isSpiritDebug()) {
                                        victim.getPacketSender().sendMessage(
                                                "Your shield was not in the 70% RNG required to activate it.");
                                    }
                                }
                            } else {
                                double reduceRatio = attacker.getId() == 1158 || attacker.getId() == 1160 ? 0.4 : 0.8;
                                double mod = Math.abs(1 - reduceRatio);
                                context.getHit().incrementAbsorbedDamage((int) (hit - (hit * mod)));
                                mod = Math.round(Misc.RANDOM.nextDouble() * 100.0) / 100.0;
                                if (mod <= CombatFactory.PRAYER_ACCURACY_REDUCTION) {
                                    context.setAccurate(false);
                                }
                            }
                        }
                    });
                }
            } else if (builder.getCharacter().isPlayer()) {
                Player attacker = (Player) builder.getCharacter();
                // If wearing veracs, the attacker will hit through prayer
                // protection.
                if (CombatFactory.fullVeracs(attacker)) {
                    return;
                }

                // They aren't wearing veracs so lets reduce the accuracy and hits.
                if (PrayerHandler.isActivated(victim, PrayerHandler.getProtectingPrayer(container.getCombatType()))
                        || CurseHandler.isActivated(victim,
                        CurseHandler.getProtectingPrayer(container.getCombatType()))) {
                    // PLAYER TO PLAYER EVENTS
                    container.allHits(context -> {
                        // First reduce the damage.
                        int hit = (int) context.getHit().getDamage();
                        double mod = Math.abs(1 - 0.5);
                        context.getHit().incrementAbsorbedDamage((int) (hit - (hit * mod)));
                        // Then reduce the accuracy.
                        mod = Math.round(Misc.RANDOM.nextDouble() * 100.0) / 100.0;
                        if (mod <= CombatFactory.PRAYER_ACCURACY_REDUCTION) {
                            context.setAccurate(false);
                        }
                    });
                }
            }
        }
    }

    /**
     * Gives experience for the total amount of damage dealt in a combat hit.
     *
     * @param builder   the attacker's combat builder.
     * @param container the attacker's combat container.
     * @param damage    the total amount of damage dealt.
     */
    protected static void giveExperience(CombatBuilder builder, CombatContainer container, int damage) {

        // This attack does not give any experience.
        if (container.getExperience().length == 0 && container.getCombatType() != CombatType.MAGIC) {
            return;
        }

        // Otherwise we give experience as normal.
        if (builder.getCharacter().isPlayer()) {
            Player player = (Player) builder.getCharacter();

            if (container.getCombatType() == CombatType.MAGIC) {
                if (player.getCurrentlyCasting() != null)
                    player.getSkillManager().addExperience(Skill.MAGIC,
                            (int) (((damage * .90)) / container.getExperience().length)
                                    + builder.getCharacter().getCurrentlyCasting().baseExperience());
            } else {
                for (int i : container.getExperience()) {
                    Skill skill = Skill.forId(i);
                    player.getSkillManager().addExperience(skill,
                            (int) (((damage * .90)) / container.getExperience().length));
                }
            }

            player.getSkillManager().addExperience(Skill.HITPOINTS, (int) ((damage * 0.7)));
        }
    }

    protected static void handleDegradingWeapons(Player attacker) {
        if (attacker == null)
            return;

        if (attacker.getLocation() == Location.DUEL_ARENA) {
            return;
        }

        for (DegradingItem DI : DegradingItem.getWeapons()) {
            if (!DI.degradeWhenHit()) {
                continue;
            }
            if (attacker.checkItem(DI.getSlot(), DI.getDeg()) || attacker.checkItem(DI.getSlot(), DI.getNonDeg())) {
                ItemDegrading.handleItemDegrading(attacker, DI);
            }
        }

    }

    protected static void handleDegradingArmor(Player victim) {
        if (victim == null)
            return;

        if ( victim.getLocation() == Location.DUEL_ARENA) {
            return;
        }

        for (DegradingItem DI : DegradingItem.getNonWeapons()) {
            if (!DI.degradeWhenHit()) {
                continue;
            }
            if (victim.checkItem(DI.getSlot(), DI.getDeg()) || victim.checkItem(DI.getSlot(), DI.getNonDeg())) {
                ItemDegrading.handleItemDegrading(victim, DI);
            }
        }
    }


    protected static void handleArmorEffects(Character attacker, Character target, int damage, CombatType combatType) {
        if (attacker.getConstitution() > 0 && damage > 0) {
            if (target != null && target.isPlayer()) {
                Player t2 = (Player) target;
                /** RECOIL **/
                if (t2.getEquipment().getItems()[Equipment.RING_SLOT].getId() == 2550) {
                    int recDamage = Math.round((float) (damage * 0.10));
                    if (recDamage < 1) {
                        recDamage = 1;
                    }
                    if (recDamage > t2.getConstitution())
                        recDamage = (int) t2.getConstitution();
                    attacker.dealDamage(new Hit(recDamage, Hitmask.NEUTRAL, CombatIcon.DEFLECT));
                    ItemDegrading.handleItemDegrading(t2, DegradingItem.RING_OF_RECOIL);
                }
            }
        }
    }


    protected static void handlePrayerEffects(Character attacker, Character target, int damage, CombatType combatType) {
        if (attacker == null || target == null)
            return;
        // Prayer effects can only be done with victims that are players.
        if (target.isPlayer() && damage > 0) {
            Player victim = (Player) target;

            // The redemption prayer effect.
            if (PrayerHandler.isActivated(victim, PrayerHandler.REDEMPTION)
                    && victim.getConstitution() <= (victim.getSkillManager().getMaxLevel(Skill.HITPOINTS) / 10)) {
                int amountToHeal = (int) (victim.getSkillManager().getMaxLevel(Skill.PRAYER) * .25);
                victim.performGraphic(new Graphic(436));
                victim.getSkillManager().setCurrentLevel(Skill.PRAYER, 0);
                victim.getSkillManager().updateSkill(Skill.PRAYER);
                victim.getSkillManager().setCurrentLevel(Skill.HITPOINTS, (int) (victim.getConstitution() + amountToHeal));
                victim.getSkillManager().updateSkill(Skill.HITPOINTS);
                victim.getPacketSender().sendMessage("You have run out of prayer points!");
                PrayerHandler.deactivateAll(victim);
                return;
            }

            // These last prayers can only be done with player attackers.
            if (attacker.isPlayer()) {

                Player p = (Player) attacker;

                if (PrayerHandler.isActivated((Player) attacker, PrayerHandler.SMITE)) {
                    victim.getSkillManager().setCurrentLevel(Skill.PRAYER,
                            victim.getSkillManager().getCurrentLevel(Skill.PRAYER) - damage / 4);
                    if (victim.getSkillManager().getCurrentLevel(Skill.PRAYER) < 0)
                        victim.getSkillManager().setCurrentLevel(Skill.PRAYER, 0);
                    victim.getSkillManager().updateSkill(Skill.PRAYER);
                }
            }
        }

        if (attacker.isPlayer()) {
            Player p = (Player) attacker;
            NPC npc = (NPC) target;
            Player k = (Player) attacker;
            double meleeHit = Maxhits.melee(k, k);
            double rangedHit = Maxhits.ranged(k, k);
            double magicHit = Maxhits.magic(k, k);
            double averageHit = (meleeHit + rangedHit + magicHit) / 3;
            CombatStrategy strategy = k.getCombatBuilder().getStrategy();


            if (CurseHandler.isActivated(p, CurseHandler.DESOLATION)) {
                if (damage > 0 && npc.getConstitution() != 0) {
                    if (p.getPosition().getRegionId() != 14676 && p.getPosition().getRegionId() != 14164 && p.getPosition().getRegionId() != 13910 && p.getLocation() != Location.CORRUPT_DUNGEON) {
                        p.heal(damage / 40);
                    } else {
                        p.heal(damage / 625);
                    }
                }
            }

            //EARTH 1
            if (CurseHandler.isActivated(p, CurseHandler.GAIABLESSING)) {
                if (damage > 0 && npc.getConstitution() != 0) {
                    if (!CurseHandler.isActivated(p, CurseHandler.ROCKHEART)) {//EARTH 3
                        if (p.getPosition().getRegionId() != 14676 && p.getPosition().getRegionId() != 14164 && p.getPosition().getRegionId() != 13910 && p.getLocation() != Location.CORRUPT_DUNGEON) {
                            p.heal(damage / 45);
                        } else {
                            p.heal(damage / 500);
                        }
                    }
                    if (CurseHandler.isActivated(p, CurseHandler.ROCKHEART)) {//EARTH 3
                        if (p.getPosition().getRegionId() != 14676 && p.getPosition().getRegionId() != 14164 && p.getPosition().getRegionId() != 13910 && p.getLocation() != Location.CORRUPT_DUNGEON) {
                            p.heal(damage / 35);
                        } else {
                            p.heal(damage / 450);
                        }
                    }
                }
            }
            if (p.getNodesUnlocked() != null) {
                if (p.getSkillTree().isNodeUnlocked(Node.SPIRITUAL_SIPHON)) {
                    if (damage > 0 && npc.getConstitution() != 0) {
                        p.heal(damage / 100);
                    }
                }
            }

            // EARTH 2
            if (p.healingspecialrunning && CurseHandler.isActivated(p, CurseHandler.SERENITY)) {
                p.setHealingspecialrunning(false);
                p.performGraphic(new Graphic(184));
                p.setConstitution((p.getSkillManager().getMaxLevel(Skill.HITPOINTS)));
                p.sendMessage("Full Healed");
            }

            //EARTH 5
            if (CurseHandler.isActivated(p, CurseHandler.STONEHAVEN)) {
                if (damage > 0 && npc.getConstitution() != 0) {
                    if (p.getPosition().getRegionId() != 14676 && p.getPosition().getRegionId() != 14164 && p.getPosition().getRegionId() != 13910 && p.getLocation() != Location.CORRUPT_DUNGEON) {
                        p.heal(damage / 35);
                    } else {
                        p.heal(damage / 500);
                    }
                }
            }
            if (p.healingspecialrunning && CurseHandler.isActivated(p, CurseHandler.STONEHAVEN)) {
                p.setHealingspecialrunning(false);
                p.performGraphic(new Graphic(184));
                p.setConstitution((p.getSkillManager().getMaxLevel(Skill.HITPOINTS)));
                p.sendMessage("Full Healed");
            }


            //FIRE 1
            if (p.getLocation() != Location.CORRUPT_RAID_ROOM_1 && p.getLocation() != Location.CORRUPT_RAID_ROOM_2 && p.getLocation() != Location.CORRUPT_RAID_ROOM_3 && p.getLocation() != Location.VOID_RAID_ROOM_1 && p.getLocation() != Location.VOID_RAID_ROOM_2 && p.getLocation() != Location.VOID_RAID_ROOM_3) {
                if (CurseHandler.isActivated(p, CurseHandler.CINDERSTOUCH)) {
                    if (!CurseHandler.isActivated(p, CurseHandler.INFERNIFY)) {
                        if (npc.isOnFire()) {
                            return;
                        }
                        npc.setBurnDamage(BurnEffect.BurnType.BASE_PRAYER.getDamage());
                        TaskManager.submit(new BurnEffect(npc));
                        npc.performGraphic(new Graphic(453));
                    }
                    if (CurseHandler.isActivated(p, CurseHandler.INFERNIFY)) {
                        if (npc.isOnFire()) {
                            return;
                        }
                        npc.setBurnDamage(BurnType.BOOSTED_BURN.getDamage());
                        TaskManager.submit(new BurnEffect(npc));
                    }
                }
            }

            if (p.getLocation() != Location.CORRUPT_RAID_ROOM_1 && p.getLocation() != Location.CORRUPT_RAID_ROOM_2 && p.getLocation() != Location.CORRUPT_RAID_ROOM_3&& p.getLocation() != Location.VOID_RAID_ROOM_1 && p.getLocation() != Location.VOID_RAID_ROOM_2 && p.getLocation() != Location.VOID_RAID_ROOM_3) {
                if (CurseHandler.isActivated(p, CurseHandler.INFERNO)) {
                    if (npc.isOnFire()) {
                        return;
                    }
                    npc.setBurnDamage(BurnType.INFERNO.getDamage());
                    TaskManager.submit(new BurnEffect(npc));
                }
            }

            //WATER 1
            if (CurseHandler.isActivated(p, CurseHandler.EBBFLOW)) {
                int chance = Misc.random(1, 2);
                int recDamage = Math.round((float) (npc.getDefinition().getMaxHit()));
                if (chance == 1) {
                    if (recDamage < 1) {
                        recDamage = 1;
                    }
                    if (recDamage > attacker.getConstitution())
                        recDamage = (int) attacker.getConstitution();
                    target.dealDamage(new Hit(Misc.random(recDamage, recDamage * 5), Hitmask.CRITICAL, CombatIcon.DEFLECT));
                }
            }
        }


        if (attacker.isPlayer()) {
            int chance = Misc.random(0, 3);
            Player p = (Player) attacker;
            if (p.getEquipment().contains(750)) {
                if (target != null) {
                    if (chance == 0) {
                        attacker.performGraphic(new Graphic(1307, GraphicHeight.LOW));

                    }
                }
            }
        }
        if (attacker.isPlayer()) {
            int chance = Misc.random(0, 3);
            Player p = (Player) attacker;
            if (p.getEquipment().contains(751)) {
                if (target != null) {
                    if (chance == 0) {
                        attacker.performGraphic(new Graphic(1308, GraphicHeight.LOW));
                    }
                }
            }
        }
        if (attacker.isPlayer()) {
            int chance = Misc.random(0, 3);
            Player p = (Player) attacker;
            if (p.getEquipment().contains(752)) {
                if (target != null) {
                    if (chance == 0) {
                        attacker.performGraphic(new Graphic(1296, GraphicHeight.LOW));
                    }
                }
            }
        }
        if (target.isPlayer()) {
            Player victim = (Player) target;
            if (damage > 0 && Misc.getRandom(10) <= 4) {
                int deflectDamage = -1;
                if (deflectDamage > 0) {
                    if (deflectDamage > attacker.getConstitution())
                        deflectDamage = (int) attacker.getConstitution();
                    int toDeflect = deflectDamage;
                    TaskManager.submit(new Task(1, victim, false) {
                        @Override
                        public void execute() {
                            if (attacker == null || attacker.getConstitution() <= 0) {
                                stop();
                            } else
                                attacker.dealDamage(new Hit(toDeflect, Hitmask.NEUTRAL, CombatIcon.DEFLECT));
                            stop();
                        }
                    });
                }
            }
        }

    }

    protected static void handlePoseidonAbility(Character attacker, Character target, int damage, CombatType combatType) {
        int chance = Misc.random(1, 10);
        if (target == null) {
            return;
        }
        if (attacker == null) {
            return;
        }
        if (target.isNpc() && attacker.isPlayer()) {
            Player player = (Player) attacker;
            NPC npc = (NPC) target;
            if (player.isPosAbility() && chance > 9) {
                player.sendMessage("@cya@You summon the wrath of poseidon to aide you in combat!");
                attacker.performGraphic(new Graphic(1296, GraphicHeight.LOW));
                target.performGraphic(new Graphic(750));
                target.dealDamage(new Hit(Misc.random(500, 2000), Hitmask.WATER, CombatIcon.MAGIC));
            }
        }
    }

    protected static void handleSpellEffects(Character attacker, Character target, int damage, CombatType combatType) {
        if (damage <= 0)
            return;
        if (target.isPlayer()) {
            Player t = (Player) target;
            if (t.hasVengeance()) {
                t.setHasVengeance(false);
                // t.forceChat("Taste CorruptRaid!");
                int returnDamage = (int) (damage * 0.75);
                if (attacker.getConstitution() < returnDamage)
                    returnDamage = (int) attacker.getConstitution();
                attacker.dealDamage(new Hit(returnDamage, Hitmask.NEUTRAL, CombatIcon.MAGIC));
            }
        }
        if (target.isNpc() && attacker.isPlayer()) {
            Player player = (Player) attacker;
            NPC npc = (NPC) target;
            if (npc.getId() == 2043) { // zulrah red form
                player.getMinigameAttributes().getZulrahAttributes().setRedFormDamage(damage, true);
            }
        }
    }

    public static boolean properLocation(Player player, Player player2) {
        return player.getLocation().canAttack(player, player2);
    }
}
