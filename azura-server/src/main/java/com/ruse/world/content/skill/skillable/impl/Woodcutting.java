package com.ruse.world.content.skill.skillable.impl;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.CustomObjects;
import com.ruse.world.content.SkillingBossSpawner;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.skill.skillable.DefaultSkillable;
import com.ruse.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Woodcutting extends DefaultSkillable {

    /**
     * The {@link GameObject} to cut down.
     */
    private final GameObject treeObject;
    /**
     * The {@code treeObject} as an enumerated type
     * which contains information about it, such as
     * required level.
     */
    private final Tree tree;
    /**
     * The axe we're using to cut down the tree.
     */
    private Optional<Axe> axe = Optional.empty();

    /**
     * Constructs a new {@link Woodcutting}.
     *
     * @param treeObject The tree to cut down.
     * @param tree       The tree's data
     */
    public Woodcutting(GameObject treeObject, Tree tree) {
        this.treeObject = treeObject;
        this.tree = tree;
    }

    @Override
    public void start(Player player) {
        player.getPacketSender().sendMessage("You swing your axe at the tree..");
        super.start(player);
    }

    @Override
    public void startAnimationLoop(Player player) {
        Task animLoop = new Task(4, player, true) {
            @Override
            protected void execute() {
                player.performAnimation(axe.get().getAnimation());
            }
        };
        TaskManager.submit(animLoop);
        getTasks().add(animLoop);
    }

    @Override
    public void onCycle(Player player) {
        player.performAnimation(axe.get().getAnimation());
    }

    @Override
    public void finishedCycle(Player player) {

        GameSettings.EMERALD_CHAMP_AMOUNT++;
        int emeraldChampAmount = GameSettings.EMERALD_CHAMP_AMOUNT;

        switch (emeraldChampAmount) {
            case 500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] 4500 resources remaining to summon the Emerald Champion!");
                break;
            case 1500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] 3500 resources remaining to summon the Emerald Champion!");
                break;
            case 2500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] Only 2500 resources remaining to summon the Emerald Champion!");
                break;
            case 3500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] Only 1500 resources remaining to summon the Emerald Champion!");
                DiscordManager.sendMessage("[SKILLING] Only 1500 resources remaining to summon the Emerald Champion! <@&1475675670196654080>" , Channels.ACTIVE_EVENTS);
                break;
            case 4500:
                World.sendMessage("<shad=0><col=AF70C3>[SKILLING] Only 500 resources remaining to summon the Emerald Champion!");
                break;
            default:
                if (emeraldChampAmount >= 5000) {
                    SkillingBossSpawner.startSkillingBossEvent(player);
                    GameSettings.EMERALD_CHAMP_AMOUNT = 0;
                }
        }
        int modifier = 0;
        double expMod = 1.0;
        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SKELETAL_SERVANT.npcId) {
            modifier += Misc.random(1,3);
            expMod += 0.04;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.DEMONIC_SERVANT.npcId) {
            modifier += Misc.random(2,4);
            expMod += 0.08;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.OGRE_SERVANT.npcId) {
            modifier += Misc.random(3,5);
            expMod += 0.12;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.SPECTRAL_SERVANT.npcId) {
            modifier += Misc.random(4,7);
            expMod += 0.16;
        }

        if (player.getSummoning() != null && player.getSummoning().getFamiliar() != null
                && player.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.MASTER_SERVANT.npcId) {
            modifier += Misc.random(5,8);
            expMod += 0.20;
        }

        //Add logs..
        Achievements.doProgress(player, Achievements.Achievement.CUT_500_LOGS, 10 + modifier);
        Achievements.doProgress(player, Achievements.Achievement.CUT_1k_LOGS, 10 + modifier);
        Achievements.doProgress(player, Achievements.Achievement.CUT_5k_LOGS, 10 + modifier);
        Achievements.doProgress(player, Achievements.Achievement.CUT_10k_LOGS, 10 + modifier);
        Achievements.doProgress(player, Achievements.Achievement.CUT_50k_LOGS, 10 + modifier);
        Achievements.doProgress(player, Achievements.Achievement.CUT_100k_LOGS, 10 + modifier);
            player.getInventory().add(tree.getLogId(), 10 + modifier);
            player.getPacketSender().sendMessage("You get some logs.");
        //FEATHER HANDLE - TYLER
        int chanceForRandomItem = Misc.random(0,500);
        int feather = 2950;

        if (player.getEquipment().contains(feather)){
            if (chanceForRandomItem == 0){
                Journeymen.rollSkillingLoot(player);
            }
        }




        if (Misc.inclusiveRandom(1, (125 - player.getSkillManager().getMaxLevel(Skill.WOODCUTTING))) == 1)
            player.getInventory().add(new Item(6693, 1 + modifier));

        //Add exp..
        player.getSkillManager().addExperience(Skill.WOODCUTTING, tree.getXpReward() * expMod);

        if (Misc.getRandom(tree.getCycles()) == 1) {
            //Stop skilling..
            cancel(player);

            //Despawn object and respawn it after a short period of time..
            treeRespawn(player, treeObject, tree);
        }
    }

    @Override
    public int cyclesRequired(Player player) {
        int cycles = tree.getCycles() + Misc.getRandom(4);
        cycles -= (int) player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) * 0.1;
        cycles -= cycles * axe.get().getSpeed();
        if (cycles < 3) {
            cycles = 3;
        }
        return cycles;
    }

    @Override
    public boolean hasRequirements(Player player) {
        //Attempt to find an axe..
        axe = Optional.empty();
        for (Axe a : Axe.values()) {
            if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == a.getId()
                || player.getInventory().contains(a.getId())) {

                //If we have already found an axe,
                //don't select others that are worse or can't be used
                if (axe.isPresent()) {
                    if (player.getSkillManager().getMaxLevel(Skill.WOODCUTTING) < a.getRequiredLevel()) {
                        continue;
                    }
                    if (a.getRequiredLevel() < axe.get().getRequiredLevel()) {
                        continue;
                    }
                }

                axe = Optional.of(a);
            }
        }

        //Check if we found one..
        if (!axe.isPresent()) {
            player.getPacketSender().sendMessage("You don't have an axe which you can use.");
            return false;
        }

        //Check if we have the required level to cut down this {@code tree} using the {@link Axe} we found..
        if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < axe.get().getRequiredLevel()) {
            player.getPacketSender().sendMessage("You don't have an axe which you have the required Woodcutting level to use.");
            return false;
        }

        //Check if we have the required level to cut down this {@code tree}..
        if (player.getSkillManager().getCurrentLevel(Skill.WOODCUTTING) < tree.getRequiredLevel()) {
            player.getPacketSender().sendMessage("You need a Woodcutting level of at least " + tree.getRequiredLevel() + " to cut this tree.");
            return false;
        }

        return super.hasRequirements(player);
    }

    @Override
    public boolean loopRequirements() {
        return true;
    }

    @Override
    public boolean allowFullInventory() {
        return false;
    }

    public GameObject getTreeObject() {
        return treeObject;
    }

    /**
     * Holds data related to the axes
     * that can be used for this skill.
     */
    public static enum Axe {
        BEGINNER(1432, 1, 0.03, new Animation(29008)),
        VORPAL(1420, 1, 0.05, new Animation(29000)),
        BLOODSTAINED(1421, 25, 0.09, new Animation(29001)),
        SYMBIOTIC(1422, 50, 0.11, new Animation(29002)),
        NETHER(1423, 75, 0.13, new Animation(29003)),
        SOULSTONE(8046, 1, 0.13, new Animation(29003));
        private final int id;
        private final int requiredLevel;
        private final double speed;
        private final Animation animation;

        private Axe(int id, int level, double speed, Animation animation) {
            this.id = id;
            this.requiredLevel = level;
            this.speed = speed;
            this.animation = animation;
        }

        public int getId() {
            return id;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }

        public double getSpeed() {
            return speed;
        }

        public Animation getAnimation() {
            return animation;
        }
    }

    /**
     * Holds data related to the trees
     * which can be used to train this skill.
     */
    public static enum Tree {
        VORPAL(1, 50, 1400, new int[]{711}, 15, 8, false),
        BLOODSTAND(35, 125, 1401, new int[]{712}, 17, 9, false),
        SYMBIOTIC(75, 250, 1402, new int[]{713}, 20, 11, true),
        NETHER(95, 500, 1403, new int[]{694}, 23, 14, true),
        UNDEAD(1, 100,7747, new int[]{1345}, 15, 2, true );
        private static final Map<Integer, Tree> trees = new HashMap<Integer, Tree>();

        static {
            for (Tree t : Tree.values()) {
                for (int obj : t.objects) {
                    trees.put(obj, t);
                }
                trees.put(t.getLogId(), t);
            }
        }

        private final int[] objects;
        private final int requiredLevel;
        private final int xpReward;
        private final int logId;
        private final int cycles;
        private final int respawnTimer;
        private final boolean multi;

        Tree(int req, int xp, int log, int[] obj, int cycles, int respawnTimer, boolean multi) {
            this.requiredLevel = req;
            this.xpReward = xp;
            this.logId = log;
            this.objects = obj;
            this.cycles = cycles;
            this.respawnTimer = respawnTimer;
            this.multi = multi;
        }




        public static Optional<Tree> forObjectId(int objectId) {
            return Optional.ofNullable(trees.get(objectId));
        }

        public boolean isMulti() {
            return multi;
        }

        public int getCycles() {
            return cycles;
        }

        public int getRespawnTimer() {
            return respawnTimer;
        }

        public int getLogId() {
            return logId;
        }

        public int getXpReward() {
            return xpReward;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }
    }

    public static void treeRespawn(Player player, GameObject oldTree, Tree o) {
        if (oldTree == null || oldTree.getPickAmount() >= o.getRespawnTimer()) // Assuming 'respawn' is max pick amount
            return;
        oldTree.setPickAmount(o.getRespawnTimer());
        for (Player players : player.getLocalPlayers()) {
            if (players == null)
                continue;
            if (players.getInteractingObject() != null && players.getInteractingObject().getPosition()
                .equals(player.getInteractingObject().getPosition().copy())) {
                players.getSkillManager().stopSkilling();
                players.getPacketSender().sendClientRightClickRemoval();
            }
        }
        player.getPacketSender().sendClientRightClickRemoval();
        player.getSkillManager().stopSkilling();
        CustomObjects.globalObjectRespawnTask(new GameObject(709, oldTree.getPosition().copy(), 10, 0), oldTree,
            o.getRespawnTimer()); // Assuming 'respawn' is in ticks
    }
}
