package com.ruse.engine.task.impl;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ruse.engine.task.Task;
import com.ruse.model.Animation;
import com.ruse.model.DamageDealer;
import com.ruse.model.Flag;
import com.ruse.model.GameMode;
import com.ruse.model.GroundItem;
import com.ruse.model.Item;
import com.ruse.model.Locations.Location;
import com.ruse.model.PlayerRights;
import com.ruse.model.Position;
import com.ruse.model.Skill;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.util.RandomUtility;
import com.ruse.world.content.ItemsKeptOnDeath;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.content.minigames.impl.NecromancyMinigame;
import com.ruse.world.content.skill.impl.old_dungeoneering.Dungeoneering;
import com.ruse.world.content.skill.impl.summoning.SummoningTab;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.entity.impl.GroundItemManager;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

/**
 * Represents a player's death task, through which the process of dying is
 * handled, the animation, dropping items, etc.
 *
 * @author relex lawl, redone by Gabbe.
 */

public class PlayerDeathTask extends Task {

    /**
     * The PlayerDeathTask constructor.
     *
     * @param player The player setting off the task.
     */
    public PlayerDeathTask(Player player) {
        super(1, player, false);
        this.player = player;
    }

    private Player player;
    private int ticks = 5;
    private boolean dropItems = false;
    private boolean spawnItems = false;
    Position oldPosition;
    Location loc;
    ArrayList<Item> itemsToKeep = null;
    NPC death;

    @Override
    public void execute() {
        if (player == null) {
            stop();
            return;
        }
        try {
            switch (ticks) {
                case 5:
                    player.getPacketSender().sendInterfaceRemoval();
                    player.getMovementQueue().setLockMovement(true).reset();
                    break;
                case 3:

                    if (player.currentInstanceAmount < 1) {
                        player.performAnimation(new Animation(0x900));
                        player.getPacketSender().sendMessage("Oh dear, you are dead!");
                    }
                    if (player.currentInstanceAmount >= 1) {
                        player.msgRed("You died in your instance");
                        player.getRegionInstance().destruct();
                        //player.getInstanceManager().selectedInstance = (null);
                        player.setCurrentInstanceAmount(-1);
                        player.setCurrentInstanceNpcId(-1);
                        player.setCurrentInstanceNpcName("");

                        player.performAnimation(new Animation(0x900));
                        Position[] locations = new Position[]{new Position(2656, 4016, 0), new Position(2656, 4016, 0)};
                        Position teleportLocation = locations[RandomUtility.exclusiveRandom(0, locations.length)];
                        TeleportHandler.teleportPlayer(player, teleportLocation, player.getSpellbook().getTeleportType());
                    }
                    if (player.getBossFight() != null) {
                        player.getBossFight().onPlayerDeath();
                    }
                    break;
                case 1:
                    this.oldPosition = player.getPosition().copy();
                    this.loc = player.getLocation();
                    if (loc != Location.DUNGEONEERING && loc != Location.DUEL_ARENA) {

                        DamageDealer damageDealer = player.getCombatBuilder().getTopDamageDealer(false, null);
                        Player killer = damageDealer == null ? null : damageDealer.getPlayer();




                        if (player.getSummoning().getFamiliar() != null) {
                            player.getSummoning().unsummon(true, true);
                            player.msgRed("Your follower Crumbles to Dust...");
                            SummoningTab.handleDismiss(player, true);

                        }
                        player.setNecrotimeleft(0);

                        player.getCompanion().reFollow();


                        if (player.getRights() == PlayerRights.MODERATOR || player.getRights() == PlayerRights.ADMINISTRATOR
                                || player.getRights() == PlayerRights.OWNER
                                || player.getRights() == PlayerRights.MANAGER_2
                                || player.getRights() == PlayerRights.CO_OWNER
                                || player.getRights() == PlayerRights.MANAGER
                                || player.getRights() == PlayerRights.DEVELOPER)
                            dropItems = false;
                        if (loc == Location.WILDERNESS) {
                            if (killer != null && (killer.getRights() == PlayerRights.ADMINISTRATOR
                                    || killer.getRights() == PlayerRights.DEVELOPER)) // ||
                                // killer.getGameMode().equals(GameMode.LONE_WOLF)
                                // ||
                                // killer.getGameMode().equals(GameMode.ULTIMATE_IRONMAN)))
                                dropItems = false;
                        }
                        if (killer != null) {
                            if (killer.getRights() == PlayerRights.ADMINISTRATOR
                                    || killer.getRights() == PlayerRights.DEVELOPER) {
                                dropItems = false;
                            }
                        }
                        if (loc == Location.THE_SIX) {
                            spawnItems = false;// && loc != Location.NOMAD && (loc != Location.WILDERNESS &&
                            // killer.getGameMode() != GameMode.NORMAL); //&& !(loc ==
                            // Location.GODWARS_DUNGEON &&
                            // player.getMinigameAttributes().getGodwarsDungeonAttributes().hasEnteredRoom());
                        } else if (loc == Location.WILDERNESS && killer != null && killer.isPlayer()
                                && killer.getGameMode() != GameMode.NORMAL) {
                            spawnItems = false;
                        } else {
                            spawnItems = true;
                        }
                        // // System.out.println("Location: "+loc+" | spawnItems: "+spawnItems);
                        // // System.out.println("Killer: "+killer.getUsername()+" |
                        // "+killer.getGameMode());
                        // // System.out.println("Victim: "+player.getUsername()+" |
                        // "+player.getGameMode());
                        if (dropItems) { // check for item dropping
                            if (spawnItems == false) {
                                if (loc == Location.WILDERNESS && killer != null && killer.isPlayer()
                                        && killer.getGameMode() != GameMode.NORMAL) {
                                    killer.getPacketSender()
                                            .sendMessage("As an Iron/UIM player, you cannot loot " + player.getUsername()
                                                    + "...")
                                            .sendMessage(
                                                    "To stop them from freely attacking Iron folk, their dropped items have been removed.");
                                    player.getPacketSender().sendMessage(killer.getUsername() + " was an Iron Man or UIM.")
                                            .sendMessage(
                                                    "Because they cannot loot, all of your dropped items have been removed.");
                                    CopyOnWriteArrayList<Item> goneItems = new CopyOnWriteArrayList<>();
                                    goneItems.addAll(player.getInventory().getValidItems());
                                    goneItems.addAll(player.getEquipment().getValidItems());
                                    for (Item item : goneItems) {
                                        if (item != null && item.getAmount() > 0 && item.getId() > 0) {
                                            PlayerLogs.log(player.getUsername(), "Died to IRON: " + killer.getUsername()
                                                    + ", losing: " + item.getId() + " x " + item.getAmount());
                                        }
                                    }
                                }
                            }
                            itemsToKeep = ItemsKeptOnDeath.getItemsToKeep(player);
                            ArrayList<Item> playerItems = new ArrayList<>();
                            playerItems.addAll(player.getInventory().getValidItems());
                            playerItems.addAll(player.getEquipment().getValidItems());
                            /*
                             * final CopyOnWriteArrayList<Item> playerItems = new
                             * CopyOnWriteArrayList<Item>();
                             * playerItems.addAll(player.getInventory().getValidItems());
                             * playerItems.addAll(player.getEquipment().getValidItems());
                             */
                            Position position = player.getPosition();
                            /*
                             * Collections.sort(playerItems, new Comparator<Item>() { // Despite this
                             * actually sorting properly, it does not affect how the client displays
                             * grounditems.
                             *
                             * @Override public int compare(Item i1, Item i2) { if (((long)
                             * i1.getAmount()*i1.getDefinition().getValue()) > ((long)
                             * i2.getAmount()*i2.getDefinition().getValue())) {
                             * // System.out.println("r1 "+((long)
                             * i1.getAmount()*i1.getDefinition().getValue()));
                             * // System.out.println("r1 "+((long)
                             * i2.getAmount()*i2.getDefinition().getValue())); return 1; } if (((long)
                             * i1.getAmount()*i1.getDefinition().getValue()) < ((long)
                             * i2.getAmount()*i2.getDefinition().getValue())) {
                             * // System.out.println("r-1 "+((long)
                             * i1.getAmount()*i1.getDefinition().getValue()));
                             * // System.out.println("r-1 "+((long)
                             * i2.getAmount()*i2.getDefinition().getValue())); return -1; } return 0; } });
                             */
                            for (Item item : playerItems) {
                                // System.out.println(item.getDefinition().getName());
                                // World.sendMessage("Before setting: "+item.getDefinition().getName() + " "+
                                // item.getAmount());
                                // item.setAmount(item.getAmount());
                                // World.sendMessage("After setting: "+item.getDefinition().getName() + " "+
                                // item.getAmount());
                                if (!item.tradeable() || itemsToKeep.contains(item)) {
                                    if (!itemsToKeep.contains(item)) {
                                        itemsToKeep.add(item);
                                    }
                                    continue;
                                }
                                // World.sendMessage("spawnItems = "+spawnItems);
                                if (spawnItems) {
                                    // if(killer.getGameMode() != GameMode.NORMAL) {
                                    if (item != null && item.getId() > 0 && item.getAmount() > 0) {
                                        PlayerLogs.log(player.getUsername(),
                                                "Died and dropped: " + (ItemDefinition.forId(item.getId()) != null
                                                        && ItemDefinition.forId(item.getId()).getName() != null
                                                        ? ItemDefinition.forId(item.getId()).getName()
                                                        : item.getId())
                                                        + ", amount: " + item.getAmount());
                                      //  GroundItemManager.spawnGroundItem((killer != null && killer.getGameMode() == GameMode.NORMAL ? killer : player),
                                              //  new GroundItem(item, position, killer != null ? killer.getUsername() : player.getUsername(), player.getHostAddress(), false, 150, true, 150));
                                    }
                                }
                            }
                            if (killer != null && player.getLocation() != Location.DUEL_ARENA) {
                                killer.getPlayerKillingAttributes().add(player);
                                player.getPlayerKillingAttributes()
                                        .setPlayerDeaths(player.getPlayerKillingAttributes().getPlayerDeaths() + 1);
                                player.getPlayerKillingAttributes().setPlayerKillStreak(0);
                                PlayerPanel.refreshPanel(player);
                                PlayerLogs.logKills(killer.getUsername(), "Killed player: " + player.getUsername());
                                PlayerLogs.logKills(player.getUsername(), "Died to player: " + killer.getUsername());
                            } else {
                                PlayerLogs.logKills(player.getUsername(), "Died to npc or unknown");
                            }
                            player.getInventory().resetItems().refreshItems();
                            player.getEquipment().resetItems().refreshItems();
                        }
                    } else
                        dropItems = false;
                    player.getPacketSender().sendInterfaceRemoval();
                    player.setEntityInteraction(null);
                    player.getMovementQueue().setFollowCharacter(null);
                    player.getCombatBuilder().cooldown(false);
                    player.setTeleporting(false);
                    player.setWalkToTask(null);
                    player.getSkillManager().stopSkilling();
                    break;
                case 0:
                    if (dropItems) {
                        if (player.getGameMode() == GameMode.IRONMAN) {
                            GameMode.set(player, player.getGameMode(), true);
                        } else {
                            if (itemsToKeep != null) {
                                for (Item it : itemsToKeep) {
                                    PlayerLogs.log(player.getUsername(),
                                            "Died, but KEPT: " + (ItemDefinition.forId(it.getId()) != null
                                                    && ItemDefinition.forId(it.getId()).getName() != null
                                                    ? ItemDefinition.forId(it.getId()).getName()
                                                    : it.getId())
                                                    + ", amount: " + it.getAmount());
                                    player.getInventory().add(it.getId(), it.getAmount());

                                }
                                itemsToKeep.clear();
                            }
                        }
                    }

                    player.restart();
                    player.getUpdateFlag().flag(Flag.APPEARANCE);
                    loc.onDeath(player);
                    if (loc != Location.DUNGEONEERING) {
                        if (player.getPosition().equals(oldPosition)) {
                            if (loc == Location.TOWER_GAME) {
                                player.getTowerParty().getOwner().getTowerRaid().proceed();
                            } else if (player.getForgottenRaidParty() != null && player.getForgottenRaidParty().isInRaid()) {
                                player.getForgottenRaidParty().getRaid().getCurrentBoss().submitDeath(player.getUsername());
                                player.moveTo(player.getForgottenRaidParty().getRaid().getCurrentBoss().afterDeathPosition());
                            } else {
                                if (player.isCompletedtutorial()) {
                                    player.moveTo(3167 + -Misc.random(0, 3), 3544 + -Misc.random(0, 3), 0);
                                }
                                if (!player.isCompletedtutorial()) {
                                    player.moveTo(3167 + -Misc.random(0, 3), 3544 + -Misc.random(0, 3), 0);
                                }
                            }
                        }
                        Dungeoneering.raidCount = 0;
                    }
                    player = null;
                    oldPosition = null;
                    stop();
                    break;
            }
            ticks--;
        } catch (Exception e) {
            setEventRunning(false);
            e.printStackTrace();
            if (player != null) {
                if (player.getForgottenRaidParty() != null && player.getForgottenRaidParty().isInRaid()) {
                    player.moveTo(player.getForgottenRaidParty().getRaid().getCurrentBoss().afterDeathPosition());
                } else {
                    if (player.isCompletedtutorial()) {
                        player.moveTo(3167 + -Misc.random(0, 3), 3544 + -Misc.random(0, 3), 0);
                    }
                    if (!player.isCompletedtutorial()) {
                        player.moveTo(3167 + -Misc.random(0, 3), 3544 + -Misc.random(0, 3), 0);
                    }
                }

                if (player.isGodMode()) {
                    return;
                }
                player.setConstitution(player.getSkillManager().getMaxLevel(Skill.HITPOINTS));
            }
        }
    }
}
