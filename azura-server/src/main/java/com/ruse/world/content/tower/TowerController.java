package com.ruse.world.content.tower;

import com.ruse.GameSettings;
import com.ruse.model.Animation;
import com.ruse.model.Graphic;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.collectionlogs.CollectionLogs;
import com.ruse.world.content.combat.Maxhits;
import com.ruse.world.content.raids.Raid;
import com.ruse.world.content.raids.RaidType;
import com.ruse.world.content.raids.Rarity;
import com.ruse.world.content.raids.Reward;
import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TowerController extends Raid {

    public int tower_x = 3488;
    public int tower_y = 3424;

    public int tower_death_x = 3475;
    public int tower_death_y = 3421;
    public int loot_room_x = 2398;
    public int loot_room_y = 9223;
    public int tower_z = 1;
    public int baseSpawns = 1;
    private int current_wave = 0;
    private double modifier_waves = 1.0;
    private double spawn_modifier = 1.0;
    private int killCounter = 0;
    public int spawns_for_this_round = 0;
    private int baseHits = 7;
    private boolean isElementalRound = false;
    private final int COMMON_CHEST = 1853;
    private final int UNCOMMON_CHEST = 1854;
    private final int RARE_CHEST = 1855;
    // Constants for loot thresholds
    private static final int UNCOMMON_THRESHOLD = 25;
    private static final int RARE_THRESHOLD = 100;

    // Odds for hitting Uncommon and Rare Loot
    private static int UNCOMMON_ODDS = 100;
    private static int RARE_ODDS = 500;

    // Random number generator
    private static final Random random = new Random();

    private ArrayList<NPC> spawnedNpcs = new ArrayList<>();

    List<Player> spawnedChest = new ArrayList<>();

    private int average_damage = 0;

    private int lastRoundsMaxhit = 0;

    private final Position commonChestSpots[] = new Position[]{
        new Position(2396, 9249),
        new Position(2396, 9247),
        new Position(2401, 9249),
        new Position(2401, 9247),
        new Position(2399, 9251)
    };


    public int[] spwanable_golem = {
        2023, 2024, 2025
    };

    public int[] spawnable_elementals = {
        2019, 2022, 2021
    };

    public TowerController() {
        super(RaidType.TOWER_OF_ASCENSION);
    }

    public void teleportParty(Player plr, Position position) {
        TeleportHandler.teleportPlayer(plr, position, plr.getSpellbook().getTeleportType());
    }

    public void moveParty(Player plr, Position position) {
        plr.performAnimation(new Animation(9606));
        plr.performGraphic(new Graphic(1685));
        plr.getPacketSender().sendFadeTransition(250, 100, 150);
        plr.moveTo(position);
    }

    public String getActiveInvocations(Player player) {
        StringBuilder builder = new StringBuilder();
        for (Invocations.TowerInvocations invo : Invocations.TowerInvocations.values()) {
            if (player.getInvocations().is_invocation_in_use(invo)) {
                builder.append(invo.getName() + " ");
            }
        }
        return builder.toString();
    }

    @Override
    public void start(Player player) {
        timer.reset();
        tower_z = 1 + (GameSettings.instance_counter * 4);
        GameSettings.instance_counter++;
        isElementalRound = false;
        if (party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.ELEMENTAL_OVERDRIVE)) {
            isElementalRound = true;
            spawns_for_this_round = party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.DOUBLE_TROUBLE) ? 2 : 1;
        } else {
            spawns_for_this_round = party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.DOUBLE_DOUBLE) ? 2 : 1;
        }
        current_wave = 1;
        spawnNPCs();
        String activeInvocations = getActiveInvocations(player);
        for (Player partyPlayer : party.getPlayers()) {
            partyPlayer.getTowerRaid().setParty(partyPlayer.getTowerParty());
            moveParty(partyPlayer, new Position(tower_x, tower_y, tower_z));
            partyPlayer.msgRed("You have started your ascent into the tower!");
            partyPlayer.setRaidPoints(0);//Reset Participation Points incase this doesn't get reset at some point after ending
            if (player.getInvocations().invocations_bound() > 0) {
                partyPlayer.msgRed("Current Invocations: " + activeInvocations);
            }
        }
    }

    @Override
    public void proceed() {
        killCounter = 0;
        for (Player player : party.getPlayers()) {
            killCounter += player.getRaidKills();
        }
        if (killCounter >= spawns_for_this_round) {
            for (Player player : party.getPlayers()) {
                player.setRaidKills(0);
                player.msgRed("You have completed the wave in " + formatMilliseconds(wave_time.elapsed()));
            }
            handleNextWave();
            spawnNPCs();
        }
    }

    @Override
    public void handleNextWave() {
        killCounter = 0;
        isElementalRound = false;
        current_wave++;
        boolean increasePower = false;
        if (current_wave % 4 == 0) {
            isElementalRound = true;
            baseSpawns = 0;
        }
        if (current_wave >= 30)
            isElementalRound = true;

        if (party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.ELEMENTAL_OVERDRIVE))
            isElementalRound = true;

        if (current_wave % 9 == 0) {
            modifier_waves += 0.5;
            spawn_modifier += 0.5;
            increasePower = true;
        }
        if (!isElementalRound)
            baseSpawns++;
        if (isElementalRound)
            spawns_for_this_round = party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.DOUBLE_TROUBLE) ? 2 : 1;
        else
            spawns_for_this_round = party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.DOUBLE_DOUBLE) ? baseSpawns * 2 : baseSpawns;

        for (Player partyPlayer : party.getPlayers()) {
            if (partyPlayer.is_dead_in_tower()) {
                moveParty(partyPlayer, new Position(tower_x, tower_y, tower_z));
            }
            partyPlayer.set_dead_in_tower(false);
            if (increasePower) {
                partyPlayer.getInventory().add(995, 500);
                partyPlayer.msgFancyPurp("You received 500 Bonus Coins!");
            }

            if (current_wave % 5 == 0) {
                rollLiveLoot();
            }

            partyPlayer.msgFancyPurp("You ascend to floor: " + (current_wave));
            int points = partyPlayer.getRaidPoints();
            double modifier = party.getOwner().getInvocations().grabTotalModifier();
            points = (int) (points * modifier);
            int multiplierEffectiveNumber = points - partyPlayer.getRaidPoints();
            partyPlayer.msgFancyPurp("You are currently at " + partyPlayer.getRaidPoints() + " (+" + multiplierEffectiveNumber + ") participation points!");
        }
        increasePower = false;
    }

    @Override
    public void endRaid() {
        for (Player partyPlayer : party.getPlayers()) {
            if (current_wave <= 2) {
                moveParty(partyPlayer, new Position(3486, 9246, 0));
                partyPlayer.msgRed("There is only failure amongst your party");
            } else {
                handleReward();
                moveParty(partyPlayer, new Position(3486, 9246, 0));
                partyPlayer.msgFancyPurp("Where you fail, you prosper!");
            }
            if (current_wave > partyPlayer.getBest_tower_wave()) {
                partyPlayer.setBest_tower_wave(current_wave);
            }
            partyPlayer.setTotal_tower_completions(partyPlayer.getTotal_tower_completions() + 1);
            if (partyPlayer.getTotal_tower_completions() == 500) {
                partyPlayer.msgFancyPurp("You have just unlocked the ability to use 2 Invocations");
            }
            if (partyPlayer.getTotal_tower_completions() == 1000) {
                partyPlayer.msgFancyPurp("You have just unlocked the ability to use 3 Invocations");
            }
            partyPlayer.msgFancyPurp("You survived for: " + formatMilliseconds(timer.elapsed()));
        }
        for (NPC npc : spawnedNpcs) {
            World.deregister(npc);
        }
        spawnedNpcs.clear();
    }

    private void rollLiveLoot() {
        Item item = liveDropTable[Misc.inclusiveRandom(liveDropTable.length - 1)];
        for (Player partyPlayer : party.getPlayers()) {
            partyPlayer.getInventory().add(item);
            partyPlayer.msgRed("You receive a " + item.getDefinition().getName() + "  for reaching another 5 waves!");
        }
    }

    private void handleReward() {

        for (Player partyPlayer : party.getPlayers()) {
            int points = partyPlayer.getRaidPoints();

            if (current_wave >= 10 && current_wave <= 24){
                Item item = common_reward[Misc.inclusiveRandom(common_reward.length - 1)];
                Item item2 = common_reward[Misc.inclusiveRandom(common_reward.length - 1)];

                partyPlayer.getInventory().add(item);
                partyPlayer.msgRed(" You received a " + item.getDefinition().getName() + "from TOA.");
                partyPlayer.getCollectionLogManager().addItem(CollectionLogs.TOWER, item);
                partyPlayer.setRaidPoints(0);
            }

            if (current_wave >= 25 && current_wave <= 49){
                Item item = uncommon_reward[Misc.inclusiveRandom(uncommon_reward.length - 1)];
                Item item2 = common_reward[Misc.inclusiveRandom(common_reward.length - 1)];

                partyPlayer.getCollectionLogManager().addItem(CollectionLogs.TOWER, item);
                partyPlayer.setRaidPoints(0);

                if (points >= 1750){
                    partyPlayer.msgRed("You received an extra reward for reaching 1750 Points.");
                    partyPlayer.getInventory().add(item2);
                    partyPlayer.msgRed(" You received a " + item2.getDefinition().getName() + "from TOA.");
                    partyPlayer.getCollectionLogManager().addItem(CollectionLogs.TOWER, item2);
                }
            }

            if (current_wave >= 50){
                Item item = rare_reward[Misc.inclusiveRandom(rare_reward.length - 1)];
                Item item2 = uncommon_reward[Misc.inclusiveRandom(uncommon_reward.length - 1)];
                partyPlayer.getCollectionLogManager().addItem(CollectionLogs.TOWER, item);
                partyPlayer.setRaidPoints(0);

                if (points >= 3000){
                    partyPlayer.msgRed("You received an extra reward for reaching 3k Points.");
                    partyPlayer.getInventory().add(item2);
                    partyPlayer.msgRed(" You received a " + item2.getDefinition().getName() + "from TOA.");
                    World.sendMessage(partyPlayer.getUsername() + " received a " + item2.getDefinition().getName() + " from TOA.");
                    partyPlayer.getCollectionLogManager().addItem(CollectionLogs.TOWER, item2);
                }
            }
        }
    }

 /*   private void handleReward() {
        int counter = 0;
        for (Player partyPlayer : party.getPlayers()) {
            if (spawnedChest.contains(partyPlayer))
                continue;
            Rarity rarity = generateLoot(partyPlayer);
            Position position = new Position(commonChestSpots[counter].getX(), commonChestSpots[counter].getY(), tower_z);
            NPC npcToSpawn = new NPC(rarity == Rarity.COMMON ? COMMON_CHEST : rarity == Rarity.UNCOMMON ? UNCOMMON_CHEST : RARE_CHEST, position);
            partyPlayer.setReward(new Reward(position, rarity));
            int health = (int) ((getPartiesAverageDamage() * getHitpointMulti()) * modifier_waves);
            if (health < 10000) {
                health = 10000;
            }
            npcToSpawn.setDefaultConstitution(health);
            npcToSpawn.setConstitution(health);
            npcToSpawn.respawn = false;
            npcToSpawn.getMovementQueue().setLockMovement(true).reset();
            npcToSpawn.setPositionToFace(new Position(position.getX(), position.getY() - 1, tower_z));
            World.register(npcToSpawn);
            partyPlayer.getPacketSender().sendPositionalHint(position, 3);
            spawnedChest.add(partyPlayer);
            counter++;
        }
    }*/

  /*  public Item getRewardItem(Rarity rarity) {
        Random generator = new Random();
        int randomIndex = 0;
        switch (rarity) {
            case COMMON:
                generator = new Random();
                randomIndex = generator.nextInt(common_reward.length);
                return common_reward[randomIndex];
            case UNCOMMON:
                generator = new Random();
                randomIndex = generator.nextInt(uncommon_reward.length);
                return uncommon_reward[randomIndex];
            case RARE:
                generator = new Random();
                randomIndex = generator.nextInt(rare_reward.length);
                return rare_reward[randomIndex];
            default:
                return new Item(1);
        }
    }*/

    // Method to generate loot based on points
/*    public Rarity generateLoot(Player player) {
        int points = player.getRaidPoints();
        double modifier = player.getInvocations().grabTotalModifier();
        points = (int) (points * modifier);


       *//* // Check for Rare Loot
        if (points >= RARE_THRESHOLD && rollDice(RARE_ODDS)) {
            return Rarity.RARE;
        }

        // Check for Uncommon Loot
        if (points >= UNCOMMON_THRESHOLD && rollDice(UNCOMMON_ODDS)) {
            return Rarity.UNCOMMON;
        }*//*

        // Default to Common Loot
        return Rarity.COMMON;
    }*/

    // Method to simulate rolling a dice with given odds
   /* private boolean rollDice(int odds) {
        int roll = random.nextInt(odds) + 1;
        return roll == 1;
    }*/

    public void openChest(Player player, Position position, NPC npc) {
       /* if (player.getReward().getRarity() == null) {
            return;
        }
        if (player.getReward().getLocation() == position) {
            Item itemCollected = getRewardItem(player.getReward().getRarity());
            player.getCollectionLogManager().addItem(CollectionLogs.TOWER, itemCollected);
            player.msgFancyPurp("You received " + itemCollected.getAmount() + "X " + ItemDefinition.forId(itemCollected.getId()).getName() + " for your efforts.");
            if (player.getInventory().getFreeSlots() == 0) {
                int tab = Bank.getTabForItem(player, itemCollected);
                player.getBank(tab).add(itemCollected);
            } else {
                player.getInventory().add(itemCollected);
            }
            int points = player.getRaidPoints();
            double modifier = party.getOwner().getInvocations().grabTotalModifier();
            points = (int) (points * modifier);
            int pointsToGive = points / 10;
            player.getInventory().add(1601, pointsToGive);
            World.deregister(npc);
            player.setReward(new Reward());
            moveParty(player, new Position(3486, 9246, 0));
            player.setRaidPoints(0);
        }*/
    }

    @Override
    public void spawnNPCs() {
        spawnedNpcs.clear();
        for (int i = 0; i < spawns_for_this_round; i++) {
            if (isElementalRound) {
                Random generator = new Random();
                int randomIndex = generator.nextInt(spawnable_elementals.length);
                int npcId = spawnable_elementals[randomIndex];
                if (party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.WARLOCKS_PACT)) {
                    if (party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.ELEMENTAL_OVERDRIVE)) {
                        if (Misc.random(1,16) == 1) {
                            npcId = 2034;
                        }
                    } else {
                        if (Misc.random(1,8) == 1) {
                            npcId = 2034;
                        }
                    }
                }
                int random_x = Misc.random(0, 4);
                int random_y = Misc.random(0, 4);
                boolean negative_x = Misc.random(0, 1) == 1;
                boolean negative_y = Misc.random(0, 1) == 1;
                NPC npcToSpawn = new NPC(npcId, new Position(negative_x ? tower_x - random_x : tower_x + random_x, negative_y ? tower_y - random_y : tower_y + random_y, tower_z));
                int health = (int) ((getPartiesAverageDamage() * baseHits) * modifier_waves);
                if (health < 10000) {
                    health = 10000;
                }
                npcToSpawn.setDefaultConstitution(health);
                npcToSpawn.setConstitution(health);
                npcToSpawn.respawn = false;
                if (party.getOwner().getInvocations().is_invocation_in_use(Invocations.TowerInvocations.DEMONIC_AGGRESSION)) {
                    npcToSpawn.setForceAggressive(true);
                }
                World.register(npcToSpawn);
                spawnedNpcs.add(npcToSpawn);
            } else {
                Random generator = new Random();
                int randomIndex = generator.nextInt(spwanable_golem.length);
                int random_x = Misc.random(0, 4);
                int random_y = Misc.random(0, 4);
                boolean negative_x = Misc.random(0, 1) == 1;
                boolean negative_y = Misc.random(0, 1) == 1;
                NPC npcToSpawn = new NPC(spwanable_golem[randomIndex], new Position(negative_x ? tower_x - random_x : tower_x + random_x, negative_y ? tower_y - random_y : tower_y + random_y, tower_z));
                int health = (int) ((getPartiesAverageDamage() * baseHits) * modifier_waves);
                if (health < 10000) {
                    health = 10000;
                }
                npcToSpawn.setDefaultConstitution(health);
                npcToSpawn.setConstitution(health);
                npcToSpawn.respawn = false;
                World.register(npcToSpawn);
                spawnedNpcs.add(npcToSpawn);
            }
        }
        wave_time.reset();
    }

    @Override
    public double getHitpointMulti() {
        double partyModifier = 1.0;
        if (party.getPlayers().size() > 1) {
            partyModifier += ((party.getPlayers().size() - 1) * 0.2);
        }
        return baseHits * partyModifier;
    }

    @Override
    public int getPartiesAverageDamage() {
        int damage_counter = 0;
        for (Player player : party.getPlayers()) {
            damage_counter += Maxhits.ranged(player, player);
            damage_counter += Maxhits.magic(player, player);
            damage_counter += Maxhits.melee(player, player);
        }
        if (damage_counter / 3 > average_damage) {
            average_damage = damage_counter / 3;
        }
        return average_damage;
    }

    @Override
    public void handleDeath(Player player) {
        moveParty(player, new Position(tower_death_x, tower_death_y, player.getTowerParty().getOwner().getPosition().getZ()));
        player.set_dead_in_tower(true);
        int deadCount = 0;
        if (player.getRaidPoints() - 20 < 0)
            player.setRaidPoints(0);
        else
            player.setRaidPoints(player.getRaidPoints() - 20);
        for (Player partyPlayer : party.getPlayers()) {
            if (partyPlayer.getPosition().equals(new Position(tower_death_x, tower_death_y, player.getTowerParty().getOwner().getPosition().getZ()))) {
                deadCount++;
            }
        }
        if (deadCount == party.getPlayers().size()) {
            endRaid();
        }
    }


    private Item[] common_reward = new Item[] {
        new Item(15667, 3),
        new Item(17490, 3),
        new Item(1400, 50),
        new Item(1408, 50),
        new Item(1401, 50),
        new Item(1409, 50),
        new Item(1402, 50),
        new Item(1410, 50),
        new Item(1403, 50),
        new Item(1411, 50),
        new Item(1448, 1),
        new Item(3580, 1),
        new Item(19062, 15),
        new Item(2706, 1),
    };

    private Item[] uncommon_reward = new Item[] {
        new Item(15670, 1),
        new Item(1446, 1),
        new Item(15666, 1),
        new Item(23171, 1),
        new Item(23172, 1),
        new Item(10256, 1),
        new Item(10260, 1),
        new Item(10262, 1),
        new Item(2706, 2),
        new Item(1448, 3),
        new Item(17490, 5),
        new Item(3580, 3),
        new Item(19062, 25),
        new Item(12423, 1),
        new Item(12424, 1),
        new Item(12427, 1),
    };

    private Item[] rare_reward = new Item[] {
        new Item(17130, 1),
        new Item(17130, 1),
        new Item(17130, 1),
        new Item(17130, 1),
        new Item(17130, 1),
        new Item(17130, 1),
        new Item(17129, 1),
        new Item(17129, 1),
        new Item(17129, 1),
        new Item(2688, 1),//DROPRATE
        new Item(2689, 1),//MAXHIT
        new Item(2706, 2),
        new Item(2706, 2),
        new Item(2706, 2),
        new Item(2706, 2),
        new Item(2706, 2),
        new Item(2706, 2),
        new Item(1448, 3),
        new Item(17490, 5),
        new Item(3580, 3),
        new Item(3580, 3),
        new Item(3580, 3),
        new Item(19062, 50),
        new Item(20080, 1),
        new Item(20081, 1),
        new Item(20082, 1),
    };


    public static final Item[] liveDropTable = { // - TYLER
            new Item(10945, 1),
            new Item(10946, 1),
            new Item(1446, 2),
            new Item(1448, 2),
            new Item(15668, 1),
            new Item(3576, 150),
            new Item(995, 2500),
            new Item(15667, 5),
            new Item(17129, 1),
            new Item(15669, 2),
            new Item(23171, 1),
            new Item(10262, 1),
            new Item(10260, 1),
            new Item(10256, 1),
            new Item(3580, 3),
            new Item(3582, 1),
            new Item(5585, 5),
            new Item(15670, 1),
            new Item(5585, 5),
    };

    public static int[] rares = {17130, 17129, 2706, 1448, 17490, 3580, 19062, 20080, 20081, 20082};

    public static String formatMilliseconds(long milliseconds) {
        // Convert milliseconds to seconds
        long seconds = milliseconds / 1000;

        long minutes = (seconds % 3600) / 60;

        long hours = seconds / 3600;
        seconds = seconds % 60;

        String hour = hours + "";
        if (hours < 10) {
            hour = "0" + hours;
        }

        String minute = minutes + "";
        if (minutes < 10) {
            minute = "0" + minutes;
        }
        String second = seconds + "";
        if (seconds < 10) {
            second = "0" + seconds;
        }
        if (hours > 0)
            return hour + ":" + minute + ":" + second;
        else
            return minute + ":" + second;
    }


}
