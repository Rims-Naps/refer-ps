package com.ruse.world.content.skill.skillable.impl;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.model.container.impl.Equipment;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BoneBossSpawner;
import com.ruse.world.content.CustomObjects;
import com.ruse.world.content.SkillingBossSpawner;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.skill.impl.summoning.BossPets;
import com.ruse.world.content.skill.skillable.DefaultSkillable;
import com.ruse.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Mining extends DefaultSkillable {

    /**
     * The {@link GameObject} to mine.
     */
    private final GameObject rockObject;
    /**
     * The {@code rock} as an enumerated type
     * which contains information about it, such as
     * required level.
     */
    private final Rock rock;
    /**
     * The pickaxe we're using to mine.
     */
    private Optional<Pickaxe> pickaxe = Optional.empty();

    /**
     * Constructs a new {@link Mining}.
     *
     * @param rockObject The rock to mine.
     * @param rock       The rock's data
     */
    public Mining(GameObject rockObject, Rock rock) {
        this.rockObject = rockObject;
        this.rock = rock;
    }

    @Override
    public void start(Player player) {
        player.getPacketSender().sendMessage("You swing your pickaxe at the rock..");
        super.start(player);
    }

    @Override
    public void startAnimationLoop(Player player) {
        Task animLoop = new Task(1, player, true) {
            @Override
            protected void execute() {
                player.performAnimation(pickaxe.get().getAnimation());
            }
        };
        TaskManager.submit(animLoop);
        getTasks().add(animLoop);
    }

    @Override
    public void onCycle(Player player) {

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

        // Add ores..
        player.getInventory().add(rock.getOreId(), 10 + modifier);
        player.getPacketSender().sendMessage("You get some ores.");

        //FEATHER HANDLE - TYLER
        int chanceForRandomItem = Misc.random(0,500);
        int feather = 2950;

        if (player.getEquipment().contains(feather)){
            if (chanceForRandomItem == 0){
                Journeymen.rollSkillingLoot(player);
            }
        }

        if (Misc.inclusiveRandom(1, (125 - player.getSkillManager().getMaxLevel(Skill.MINING))) == 1)
            player.getInventory().add(new Item(1633, 1  + modifier));

        // Add exp..
        player.getSkillManager().addExperience(Skill.MINING, rock.getXpReward() * expMod);

        // Stop skilling..
        if (Misc.random(rock.getCycles()) == 1) {
            cancel(player);
            oreRespawn(player, rockObject, rock);
        }
    }

    @Override
    public int cyclesRequired(Player player) {
        int cycles = rock.getCycles() + Misc.getRandom(4);
        cycles -= (int) player.getSkillManager().getMaxLevel(Skill.MINING) * 0.1;
        cycles -= cycles * pickaxe.get().getSpeed();
        if (cycles < 3) {
            cycles = 3;
        }
        return cycles;
    }

    @Override
    public boolean hasRequirements(Player player) {
        //Attempt to find a pickaxe..
        pickaxe = Optional.empty();
        for (Pickaxe a : Pickaxe.values()) {
            if (player.getEquipment().getItems()[Equipment.WEAPON_SLOT].getId() == a.getId()
                || player.getInventory().contains(a.getId())) {

                //If we have already found a pickaxe,
                //don't select others that are worse or can't be used
                if (pickaxe.isPresent()) {
                    if (player.getSkillManager().getMaxLevel(Skill.MINING) < a.getRequiredLevel()) {
                        continue;
                    }
                    if (a.getRequiredLevel() < pickaxe.get().getRequiredLevel()) {
                        continue;
                    }
                }

                pickaxe = Optional.of(a);
            }
        }

        //Check if we found one..
        if (!pickaxe.isPresent()) {
            player.getPacketSender().sendMessage("You don't have a pickaxe which you can use.");
            return false;
        }

        //Check if we have the required level to mine this {@code rock} using the {@link Pickaxe} we found..
        if (player.getSkillManager().getCurrentLevel(Skill.MINING) < pickaxe.get().getRequiredLevel()) {
            player.getPacketSender().sendMessage("You don't have a pickaxe which you have the required Mining level to use.");
            return false;
        }

        //Check if we have the required level to mine this {@code rock}..
        if (player.getSkillManager().getCurrentLevel(Skill.MINING) < rock.getRequiredLevel()) {
            player.getPacketSender().sendMessage("You need a Mining level of at least " + rock.getRequiredLevel() + " to mine this rock.");
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
        return rockObject;
    }

    /**
     * Holds data related to the pickaxes
     * that can be used for this skill.
     */
    public static enum Pickaxe {
        BEGINNER(1433, 1, new Animation(29009), 0.03),
        VORPAL(1424, 1, new Animation(29004), 0.05),
        BLOOD_STAINED(1425, 35, new Animation(29005), 0.09),
        SYMBIOTIC(1426, 75, new Animation(29006), 0.13),
        NETHER(1427, 95, new Animation(29007), 0.16);

        private final int id, requiredLevel;
        private final Animation animation;
        private final double speed;

        private Pickaxe(int id, int req, Animation animaion, double speed) {
            this.id = id;
            this.requiredLevel = req;
            this.animation = animaion;
            this.speed = speed;
        }

        public int getId() {
            return id;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }

        public Animation getAnimation() {
            return animation;
        }

        public double getSpeed() {
            return this.speed;
        }
    }

    /**
     * Holds data related to the rocks
     * which can be used to train this skill.
     */
    public static enum Rock {
        VORPAL(new int[]{3042}, 1, 50, 1408, 15, 2),
        BLOODSTAINED(new int[]{2102}, 35, 125, 1409, 17, 4),
        SYMBIOTIC(new int[]{2100}, 75, 250, 1410, 20, 4),
        NETHER(new int[]{2104}, 95, 500, 1411, 23, 5);

        private static final Map<Integer, Rock> rocks = new HashMap<Integer, Rock>();

        static {
            for (Rock t : Rock.values()) {
                for (int obj : t.objects) {
                    rocks.put(obj, t);
                }
                rocks.put(t.getOreId(), t);
            }
        }

        private int objects[];
        private int oreId, requiredLevel, xpReward, cycles, respawnTimer;

        private Rock(int[] objects, int requiredLevel, int xpReward, int oreId, int cycles, int respawnTimer) {
            this.objects = objects;
            this.requiredLevel = requiredLevel;
            this.xpReward = xpReward;
            this.oreId = oreId;
            this.cycles = cycles;
            this.respawnTimer = respawnTimer;
        }

        public static Optional<Rock> forObjectId(int objectId) {
            return Optional.ofNullable(rocks.get(objectId));
        }

        public int getRespawnTimer() {
            return respawnTimer;
        }

        public int getRequiredLevel() {
            return requiredLevel;
        }

        public int getXpReward() {
            return xpReward;
        }

        public int getOreId() {
            return oreId;
        }

        public int getCycles() {
            return cycles;
        }
    }

    public static void oreRespawn(Player player, GameObject oldOre, Rock o) {
        if (oldOre == null || oldOre.getPickAmount() >= o.getRespawnTimer()) // Assuming 'respawn' is max pick amount
            return;
        oldOre.setPickAmount(o.getRespawnTimer());
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
        CustomObjects.globalObjectRespawnTask(new GameObject(7644, oldOre.getPosition().copy(), 10, 0), oldOre,
            o.getRespawnTimer()); // Assuming 'respawn' is in ticks
    }
}
