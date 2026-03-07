package com.ruse.world.content.skill;

import com.ruse.GameSettings;
import com.ruse.donation.DonatorRanks;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.WeaponAnimations;
import com.ruse.model.definitions.WeaponInterfaces;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BonusManager;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.skill.skillable.Skillable;
import com.ruse.world.content.skill.skillable.impl.Mining;
import com.ruse.world.content.skill.skillable.impl.Woodcutting;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.entity.impl.player.Player;

import java.util.List;
import java.util.Optional;

/**
 * Represents a player's skills in the game, also manages calculations such as
 * combat level and total level.
 *
 * @author relex lawl
 * @editor Gabbe
 */

public class SkillManager {

    /**
     * The maximum amount of skills in the game.
     */
    public static final int MAX_SKILLS = 26;
    /**
     * The maximum amount of experience you can achieve in a skill.
     */
    private static final int MAX_EXPERIENCE = 2000000000;
    private static final int EXPERIENCE_FOR_99 = 13034431;
    private static final int EXPERIENCE_FOR_120 = 104273167;
    private static final int[] EXP_ARRAY = {0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107,
            2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833,
            16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983,
            75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742,
            302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895,
            1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
            3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629,
            11805606, 13034431, 14391160, 15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069,
            31777943, 35085654, 38737661, 42769801, 47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
            85539082, 94442737, 104273167};
    /**
     * The player associated with this Skills instance.
     */
    private Player player;
    private Skills skills;
    private long totalGainedExp;

    /**
     * Checks if a skill should be started based on the {@link GameObject} that was
     * given.
     * @param object
     * @return
     */
    public boolean startSkillable(GameObject object) {
        // Check woodcutting..
        Optional<Woodcutting.Tree> tree = Woodcutting.Tree.forObjectId(object.getId());
        if (tree.isPresent()) {
            startSkillable(new Woodcutting(object, tree.get()));
            return true;
        }

        // Check mining..
        Optional<Mining.Rock> rock = Mining.Rock.forObjectId(object.getId());
        if (rock.isPresent()) {
            startSkillable(new Mining(object, rock.get()));
            return true;
        }

        return false;
    }

    /**
     * Starts the {@link Skillable} skill.
     * @param skill
     */
    public void startSkillable(Skillable skill) {
        // Stop previous skills..
        stopSkillable();

        // Close interfaces..
        player.getPacketSender().sendInterfaceRemoval();

        // Check if we have the requirements to start this skill..
        if (!skill.hasRequirements(player)) {
            return;
        }

        // Start the skill..
        player.setSkill(Optional.of(skill));
        skill.start(player);
    }

    /**
     * Stops the player's current skill, if they have one active.
     */
    public void stopSkillable() {
        player.getSkill().ifPresent(e -> e.cancel(player));
        player.setSkill(Optional.empty());
        player.setCreationMenu(null);
    }


    /**
     * The skillmanager's constructor
     *
     * @param player The player's who skill set is being represented.
     */
    public SkillManager(Player player) {
        this.player = player;
        newSkillManager();
    }

    public void newSkillManager() {
        this.skills = new Skills();
        for (int i = 0; i < MAX_SKILLS; i++) {
            skills.level[i] = skills.maxLevel[i] = 1;
            skills.experience[i] = 0;
        }
        skills.level[Skill.HITPOINTS.ordinal()] = skills.maxLevel[Skill.HITPOINTS.ordinal()] = 10;
        skills.experience[Skill.HITPOINTS.ordinal()] = 1184;
    }

    public static boolean realMaxed(Player p) {
        for (int i = 0; i < Skill.values().length; i++) {
            if (i == 21 || i == 24)
                continue;
            if (p.getSkillManager().getMaxLevel(i) < (120)) {
                return false;
            }
        }
        return true;
    }

    public static int getPrestigePoints(Player player, Skill skill) {
        float MAX_EXP = (float) MAX_EXPERIENCE;
        float experience = player.getSkillManager().getExperience(skill);
        int basePoints = skill.getPrestigePoints();
        double bonusPointsModifier = player.getGameMode() == GameMode.GROUP_IRON ? 1.3
                : player.getGameMode() == GameMode.IRONMAN ? 1.3
                : player.getGameMode() == GameMode.EZMODE ? 2.0
                : player.getGameMode() == GameMode.GROUP_IRON ? 1.2 : 1;

        bonusPointsModifier += (experience / MAX_EXP) * 5;
        int totalPoints = (int) (basePoints * bonusPointsModifier);
        return totalPoints;
    }

    /**
     * Gets the minimum experience in said level.
     *
     * @param level The level to get minimum experience for.
     * @return The least amount of experience needed to achieve said level.
     */
    public static int getExperienceForLevel(int level) {
        if (level <= 120) {
            return EXP_ARRAY[--level > 119 ? 119 : level];
        } else {
            int points = 0;
            int output = 0;
            for (int lvl = 1; lvl <= level; lvl++) {
                points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
                if (lvl >= level) {
                    return output;
                }
                output = (int) Math.floor(points / 4);
            }
        }
        return 0;
    }

    /**
     * Gets the level from said experience.
     *
     * @param experience The experience to get level for.
     * @return The level you obtain when you have specified experience.
     */
    public static int getLevelForExperience(int experience) {
        if (experience <= EXPERIENCE_FOR_120) {
            for (int j = 119; j >= 0; j--) {
                if (EXP_ARRAY[j] <= experience) {
                    return j + 1;
                }
            }
        } else {
            int points = 0, output = 0;
            for (int lvl = 1; lvl <= 120; lvl++) {
                points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
                output = (int) Math.floor(points / 4);
                if (output >= experience) {
                    return lvl;
                }
            }
        }
        return 120;
    }

    /**
     * Checks if the skill is a x10 skill.
     *
     * @param skill The skill to check.
     * @return The skill is a x10 skill.
     */


    /**
     * Gets the max level for <code>skill</code>
     *
     * @param skill The skill to get max level for.
     * @return The max level that can be achieved in said skill.
     */
    public static int getMaxAchievingLevel(Skill skill) {
        int level = 120;
        return level;
    }

    /**
     * Creates a new skillmanager for the player Sets current and max appropriate
     * levels.
     */


    /**
     * Adds experience to {@code skill} by the {@code experience} amount.
     *
     * @param skill      The skill to add experience to.
     * @param experience The amount of experience to add to the skill.
     * @return The Skills instance.
     */
    public SkillManager addExperience(Skill skill, double experience) {
        if (player.experienceLocked())
            return this;
        /*
         * If the experience in the skill is already greater or equal to {@code
         * MAX_EXPERIENCE} then stop.
         */


        if (this.skills.experience[skill.ordinal()] >= MAX_EXPERIENCE)
            return this;


        //COMBAT EXPERIENCE RATES
        if (player.getXpmode() == XpMode.EASY){
            if (skill.isCombat() && skill != Skill.PRAYER) {
                experience *= 75;
            }
        }
        if (player.getXpmode() == XpMode.MEDIUM){
            if (skill.isCombat() && skill != Skill.PRAYER) {
                experience *= 37;
            }
        }
        if (player.getXpmode() == XpMode.ELITE){
            if (skill.isCombat() && skill != Skill.PRAYER) {
                experience *= 18;
            }
        }
        if (player.getXpmode() == XpMode.MASTER){
            if (skill.isCombat() && skill != Skill.PRAYER) {
                experience *= 4;
            }
        }
        if (skill == Skill.JOURNEYMAN || skill == Skill.HERBLORE) {
            double expMod = 1.0;
            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SKELETAL_SERVANT.npcId) {
                expMod += 0.04;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEMONIC_SERVANT.npcId) {
                expMod += 0.08;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.OGRE_SERVANT.npcId) {
                expMod += 0.12;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SPECTRAL_SERVANT.npcId) {
                expMod += 0.16;
            }

            if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.MASTER_SERVANT.npcId) {
                expMod += 0.20;
            }
            experience *= expMod;
        }


        //PRAYER EXPERIENCE RATES
        if (player.getXpmode() == XpMode.EASY){
            if (skill == Skill.PRAYER) {
                experience *= 4;
            }
        }
        if (player.getXpmode() == XpMode.MEDIUM){
            if (skill == Skill.PRAYER) {
                experience *= 3;
            }
        }
        if (player.getXpmode() == XpMode.ELITE){
            if (skill == Skill.PRAYER) {
                experience *= 2;
            }
        }
        if (player.getXpmode() == XpMode.MASTER){
            if (skill == Skill.PRAYER) {
                experience *= 1;
            }
        }


        //SLAYER EXPERIENCE RATES
        if (player.getXpmode() == XpMode.EASY){
            if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                experience *= 5;
            }
        }
        if (player.getXpmode() == XpMode.MEDIUM){
            if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                experience *= 4;
            }
        }
        if (player.getXpmode() == XpMode.ELITE){
            if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                experience *= 3;
            }
        }
        if (player.getXpmode() == XpMode.MASTER){
            if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                experience *= 2;
            }
        }


        //SKILLING EXPERIENCE RATES
        if (player.getXpmode() == XpMode.EASY){
            if (skill != Skill.SLAYER && skill != Skill.BEAST_HUNTER && skill != Skill.PRAYER && !skill.isCombat()) {
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING)
                    experience *= 2;
                experience *= 5;
            }
        }

        if (player.getXpmode() == XpMode.MEDIUM){
            if (skill != Skill.SLAYER && skill != Skill.BEAST_HUNTER && skill != Skill.PRAYER && !skill.isCombat()) {
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING)
                    experience *= 2;
                experience *= 4;
            }
        }
        if (player.getXpmode() == XpMode.ELITE){
            if (skill != Skill.SLAYER && skill != Skill.BEAST_HUNTER && skill != Skill.PRAYER && !skill.isCombat()) {
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING)
                    experience *= 2;
                experience *= 3;
            }
        }

        if (player.getXpmode() == XpMode.MASTER){
            if (skill != Skill.SLAYER && skill != Skill.BEAST_HUNTER && skill != Skill.PRAYER && !skill.isCombat()) {
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING)
                    experience *= 2;
                experience *= 2;
            }
        }



        if (GameSettings.DOUBLE_SKILL_EXP) {
            experience *= 2;
        }

        if (PerkManager.currentPerk != null) {
            if (PerkManager.currentPerk.getName().equalsIgnoreCase("Experience")) {
                experience *= 1.50;
            }
        }

        List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();
        if (activePerks.contains(ServerPerks.Perk.COMBO)) {
            experience *= 2.0;
        }
        /*
         * Donator Rank Bonusses
        */

        //DONATOR ZONE XP BOOSTS
        if (player.getPosition().getRegionId() == 9527) {
            experience *= 1.10;
        }
        if (player.getPosition().getRegionId() == 10039) {
            experience *= 1.15;
        }
        if (player.getPosition().getRegionId() == 10551) {
            experience *= 1.17;
        }
        if (player.getPosition().getRegionId() == 11063) {
            experience *= 1.20;
        }
            if(player.getAmountDonated() >= DonatorRanks.CELESTIAL_AMOUNT) {
            experience *= 1.10;
        } else if(player.getAmountDonated() >= DonatorRanks.ASCENDANT_AMOUNT) {
            experience *= 1.12;
        } else if(player.getAmountDonated() >= DonatorRanks.GLADIATOR_AMOUNT) {
            experience *= 1.14;
        } else if(player.getAmountDonated() >= DonatorRanks.COSMIC_AMOUNT) {
            experience *= 1.16;
        } else if(player.getAmountDonated() >= DonatorRanks.GUARDIAN_AMOUNT) {
            experience *= 1.18;
        } else if(player.getAmountDonated() >= DonatorRanks.CORRUPT_AMOUNT) {
            experience *= 1.20;
        } else if(player.getAmountDonated() >= DonatorRanks.ARCHON_AMOUNT) {
            experience *= 1.08;
        } else if(player.getAmountDonated() >= DonatorRanks.MYTHIC_AMOUNT) {
            experience *= 1.06;
        } else if(player.getAmountDonated() >= DonatorRanks.ETHEREAL_AMOUNT) {
            experience *= 1.04;
        } else if(player.getAmountDonated() >= DonatorRanks.ADEPT_AMOUNT) {
            experience *= 1.02;
        }

        if (player.getMembershipTier().isMember())
            experience *= player.getMembershipTier().getExperience_increase();

        /*
         * The skill's level before adding experience.
         */
        int startingLevel = skills.maxLevel[skill.ordinal()];
        /*
         * Adds the experience to the skill's experience.
         */
        this.skills.experience[skill.ordinal()] = (int) Math.min(this.skills.experience[skill.ordinal()] + experience, MAX_EXPERIENCE);

        /*
         * The skill's level after adding the experience.
         */
        int newLevel = getLevelForExperience(this.skills.experience[skill.ordinal()]);
        /*
         * If the starting level less than the new level, level up.
         */
        if (newLevel > startingLevel) {
            int level = newLevel - startingLevel;
            String skillName = Misc.formatText(skill.toString().toLowerCase());
            skills.maxLevel[skill.ordinal()] += level;

            /*
             * If the skill is not constitution, prayer or summoning, then set the current
             * level to the max level.
             */

                if (getCurrentLevel(skill) < newLevel)
                    setCurrentLevel(skill, skills.maxLevel[skill.ordinal()]);


            player.setDialogue(null);


            player.performGraphic(new Graphic(312));
            if (skill.isCombat() && newLevel == 120){
                StarterTasks.doProgress(player, StarterTasks.StarterTask.REACH_120_COMBAT, 1);
            }
            player.sendMessage("<shad=0><col=AF70C3>You've just advanced a @red@<shad=0>" + skillName + " <shad=0><col=AF70C3>level! You are now level @red@<shad=0>" + newLevel + ".");


            if (skills.maxLevel[skill.ordinal()] == getMaxAchievingLevel(skill)) {
                player.sendMessage("<shad=0><col=AF70C3>Well done! You've achieved the highest possible level in this skill!");
                World.sendMessage("@red@<shad=0> " + player.getUsername() + " <shad=0><col=AF70C3>has just reached max level in " + skillName + "<shad=0><col=AF70C3>, on XP Mode @red@<shad=0>" + player.getXpmode());
                if (maxed()) {
                    World.sendMessage("@red@<shad=0> " + player.getUsername() + " <shad=0><col=AF70C3>has just achieved max level in all skills!");
                }

                TaskManager.submit(new Task(2, player, true) {
                    int localGFX = 1634;

                    @Override
                    public void execute() {
                        player.performGraphic(new Graphic(localGFX));
                        if (localGFX == 1637) {
                            stop();
                            return;
                        }
                        localGFX++;
                        player.performGraphic(new Graphic(localGFX));
                    }
                });
            } else {
                TaskManager.submit(new Task(2, player, false) {
                    @Override
                    public void execute() {
                        player.performGraphic(new Graphic(199));
                        stop();
                    }
                });
            }
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }
        updateSkill(skill);
        this.totalGainedExp += experience;
        return this;
    }

    public boolean skillCape(Skill skill) {
        int c = skill.getSkillCapeId();
        int ct = skill.getSkillCapeTrimmedId();
        if (player.checkItem(Equipment.CAPE_SLOT, c) || player.checkItem(Equipment.CAPE_SLOT, ct)
                || player.checkItem(Equipment.CAPE_SLOT, 14019) || player.checkItem(Equipment.CAPE_SLOT, 14022)
                || player.checkItem(Equipment.CAPE_SLOT, 22052) || player.checkItem(Equipment.CAPE_SLOT, 20081)
                || (player.checkItem(Equipment.CAPE_SLOT, 22052) && player.getSkillManager().getMaxLevel(skill) >= 99)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean maxed() {
        for (int i = 0; i < Skill.values().length; i++) {
            if(i == 23)//skip summoning
                continue;
            if (player.getSkillManager().getMaxLevel(i) < (120)) {
                return false;
            }
        }
        return true;
    }

    public SkillManager stopSkilling() {
        if (player.getCurrentTask() != null) {
            player.getCurrentTask().stop();
            player.setCurrentTask(null);
        }
        player.setResetPosition(null);
        player.setInputHandling(null);
        return this;
    }

    /**
     * Updates the skill strings, for skill tab and orb updating.
     *
     * @param skill The skill who's strings to update.
     * @return The Skills instance.
     */
    public SkillManager updateSkill(Skill skill) {
        int maxLevel = getMaxLevel(skill), currentLevel = getCurrentLevel(skill);
        // if (skill == Skill.PRAYER)
        // player.getPacketSender().sendString(687, currentLevel + "/" + maxLevel);

        player.getPacketSender().sendString(86004, "Total level: " + getTotalLevel());
        player.getPacketSender().sendString(19000, "Combat level: " + getCombatLevel());
        player.getPacketSender().sendSkill(skill);
        return this;
    }

    public SkillManager resetSkill(Skill skill, boolean prestige) {
        if (player.getEquipment().getFreeSlots() != player.getEquipment().capacity()) {
            player.getPacketSender().sendMessage("Please unequip all your items first.");
            return this;
        }
        if (player.getLocation() == Location.WILDERNESS || player.getCombatBuilder().isBeingAttacked()) {
            player.getPacketSender().sendMessage("You cannot do this at the moment");
            return this;
        }
        if (prestige && player.getSkillManager().getMaxLevel(skill) < getMaxAchievingLevel(skill)) {
            player.getPacketSender().sendMessage(
                    "You must have reached the maximum level in a skill to prestige in it. @blu@(Level 120)");
            return this;
        }
        if (prestige) {
            int pts = getPrestigePoints(player, skill);
            player.getPointsHandler().setPrestigePoints(pts, true);
            player.getPacketSender().sendMessage("You've received " + pts + " Prestige points!");


            player.getPointsHandler().incrementTotalPrestiges(1);
            PlayerPanel.refreshPanel(player);
        } else {
            player.getInventory().delete(13663, 1);
        }
        setCurrentLevel(skill, skill == Skill.PRAYER ? 10 : skill == Skill.HITPOINTS ? 100 : 1)
                .setMaxLevel(skill, skill == Skill.PRAYER ? 10 : skill == Skill.HITPOINTS ? 100 : 1)
                .setExperience(skill, SkillManager.getExperienceForLevel(skill == Skill.HITPOINTS ? 10 : 1));
        PrayerHandler.deactivateAll(player);
        CurseHandler.deactivateAll(player);
        BonusManager.update(player);
        WeaponInterfaces.assign(player, player.getEquipment().get(Equipment.WEAPON_SLOT));
        WeaponAnimations.update(player);
        player.getPacketSender().sendMessage("You have reset your " + skill.getFormatName() + " level.");
        return this;
    }

    public int getTotalKills() {
        return KillsTracker.getTotalKills(player);
    }

    public int getCombatLevel(int kills) {
        int points = 0, output = 0;
        for (int lvl = 1; lvl <= 500; lvl++) {
            points += Math.floor(1000 * (1.5 * lvl));
            output = (int) Math.floor(points);
            if (output >= kills) {
                return lvl;
            }
        }
        return 1;
    }

    /**
     * Calculates the player's combat level.
     *
     * @return The average of the player's combat skills.
     */
    public int getCombatLevel() {
        int combatLevel = getCombatLevel(getTotalKills());
        return combatLevel;
    }

    /**
     * Gets the player's total level.
     *
     * @return The value of every skill summed up.
     */
    public int getTotalLevel() {
        int total = 0;
        for (Skill skill : Skill.values()) {
                total += skills.maxLevel[skill.ordinal()];
            }
        return total;
    }

    /**
     * Gets the player's total experience.
     *
     * @return The experience value from the player's every skill summed up.
     */
    public long getTotalExp() {
        long xp = 0;
        for (Skill skill : Skill.values())
            xp += player.getSkillManager().getExperience(skill);
        return xp;
    }

    /**
     * Gets the current level for said skill.
     *
     * @param skill The skill to get current/temporary level for.
     * @return The skill's level.
     */
    public int getCurrentLevel(Skill skill) {
        return skills.level[skill.ordinal()];
    }

    /**
     * Gets the max level for said skill.
     getMaxLevel
     * @param skill The skill to get max level for.
     * @return The skill's maximum level.
     */
    public int getMaxLevel(Skill skill) {
        return skills.maxLevel[skill.ordinal()];
    }

    /**
     * Gets the max level for said skill.
     *
     * @param skill The skill to get max level for.
     * @return The skill's maximum level.
     */
    public int getMaxLevel(int skill) {
        return skills.maxLevel[skill];
    }

    /**
     * Gets the experience for said skill.
     *
     * @param skill The skill to get experience for.
     * @return The experience in said skill.
     */
    public int getExperience(Skill skill) {
        return skills.experience[skill.ordinal()];
    }

    /**
     * Sets the current level of said skill.
     *
     * @param skill   The skill to set current/temporary level for.
     * @param level   The level to set the skill to.
     * @param refresh If <code>true</code>, the skill's strings will be updated.
     * @return The Skills instance.
     */
    public SkillManager setCurrentLevel(Skill skill, int level, boolean refresh) {
        this.skills.level[skill.ordinal()] = level < 0 ? 0 : level;
        if (refresh)
            updateSkill(skill);
        return this;
    }

    /**
     * Sets the maximum level of said skill.
     *
     * @param skill   The skill to set maximum level for.
     * @param level   The level to set skill to.
     * @param refresh If <code>true</code>, the skill's strings will be updated.
     * @return The Skills instance.
     */
    public SkillManager setMaxLevel(Skill skill, int level, boolean refresh) {
        skills.maxLevel[skill.ordinal()] = level;
        if (refresh)
            updateSkill(skill);
        return this;
    }

    /**
     * Sets the experience of said skill.
     *
     * @param skill      The skill to set experience for.
     * @param experience The amount of experience to set said skill to.
     * @param refresh    If <code>true</code>, the skill's strings will be updated.
     * @return The Skills instance.
     */
    public SkillManager setExperience(Skill skill, int experience, boolean refresh) {
        this.skills.experience[skill.ordinal()] = experience < 0 ? 0 : experience;
        if (refresh)
            updateSkill(skill);
        return this;
    }

    /**
     * Sets the current level of said skill.
     *
     * @param skill The skill to set current/temporary level for.
     * @param level The level to set the skill to.
     * @return The Skills instance.
     */
    public SkillManager setCurrentLevel(Skill skill, int level) {
        setCurrentLevel(skill, level, true);
        return this;
    }

    /**
     * Sets the maximum level of said skill.
     *
     * @param skill The skill to set maximum level for.
     * @param level The level to set skill to.
     * @return The Skills instance.
     */
    public SkillManager setMaxLevel(Skill skill, int level) {
        setMaxLevel(skill, level, true);
        return this;
    }

    /**
     * Sets the experience of said skill.
     *
     * @param skill      The skill to set experience for.
     * @param experience The amount of experience to set said skill to.
     * @return The Skills instance.
     */
    public SkillManager setExperience(Skill skill, int experience) {
        setExperience(skill, experience, true);
        return this;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public long getTotalGainedExp() {
        return totalGainedExp;
    }

    public void setTotalGainedExp(long totalGainedExp) {
        this.totalGainedExp = totalGainedExp;
    }

    public class Skills {


        public Skills() {
            level = new int[MAX_SKILLS];
            maxLevel = new int[MAX_SKILLS];
            experience = new int[MAX_SKILLS];
        }

        public int[] level;
        public int[] maxLevel;
        public int[] experience;

    }

}
