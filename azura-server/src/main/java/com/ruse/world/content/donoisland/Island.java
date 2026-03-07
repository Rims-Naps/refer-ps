package com.ruse.world.content.donoisland;

import com.ruse.GameSettings;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.raids.invocation.Invocations;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;

import java.util.*;

public class Island {

    @Getter
    List<IslandPortal> portalList;
    @Getter
    PickTier tierOfPick;
    Player player;
    Position position_one = new Position(2127, 5152, 1);
    int face_one = 1;
    Position position_two = new Position(2142, 5167, 1);
    int face_two = 1;
    Position position_three = new Position(2157, 5152, 1);
    int face_three = 1;

    Position starting_point = new Position(2142, 5152, 1);

    Position chest_map = new Position(2142, 5534, 1);
    int currentZ = 0;

    /**
     * Player constructor for accessing view player calls.
     * @param player
     */
    public Island(Player player) {
        this.player = player;
    }

    /**
     * Player constructor for accessing view player calls.
     * @param player
     */
    public Island(Player player, PickTier tierOfPick, List<IslandPortal> portalList) {
        this.player = player;
        this.tierOfPick = tierOfPick;
        this.portalList = portalList;
    }

    public void constructIsland(int amountDonated) {
        PickTier tier = PickTier.LOW_TIER;
        if (amountDonated < 25) {
            return;
        }
        if (amountDonated >= 50) {
            tier = PickTier.MED_TIER;
        }
        if (amountDonated >= 100) {
            tier = PickTier.HIGH_TIER;
        }
        int amountOfTries = 0;
        if (amountDonated >= 100) {
            amountOfTries = amountDonated / 100;
        }
        player.setIslandPicks(amountOfTries);
        startIsland(tier);
    }

    public void startIsland(PickTier tier) {
        List<IslandPortal> portals = new ArrayList<>();
        int random = Misc.random(1, 3);
        if (player.getIslandPicks() > 1) {
            player.msgFancyPurp("You now have " + player.getIslandPicks() + " tries remaining.");
        }
        if (random == 1) {
            portals.add(new IslandPortal(13628, position_one, face_one, true));
            portals.add(new IslandPortal(13629, position_two, face_two, false));
            portals.add(new IslandPortal(13630, position_three, face_three, false));
            System.out.println(1);
        } else if (random == 2) {
            portals.add(new IslandPortal(13628, position_one, face_one, false));
            portals.add(new IslandPortal(13629, position_two, face_two, true));
            portals.add(new IslandPortal(13630, position_three, face_three, false));
            System.out.println(2);
        } else if (random == 3) {
            portals.add(new IslandPortal(13628, position_one, face_one, false));
            portals.add(new IslandPortal(13629, position_two, face_two, false));
            portals.add(new IslandPortal(13630, position_three, face_three, true));
            System.out.println(3);
        }
        player.setIsland(new Island(player, tier, portals));
        currentZ = 0 + (GameSettings.instance_counter * 4);
        GameSettings.instance_counter++;
        for (int i = 0; i < portals.size(); i++) {
            NPC npcToSpawn = new NPC(portals.get(i).getNpcId(), new Position(portals.get(i).getSpawnLocation().getX(), portals.get(i).getSpawnLocation().getY(), portals.get(i).getSpawnLocation().getZ() + currentZ));
            npcToSpawn.setDefaultConstitution(100);
            npcToSpawn.setConstitution(100);
            npcToSpawn.respawn = false;
            npcToSpawn.getMovementQueue().setLockMovement(true).reset();
            World.register(npcToSpawn);
        }
        TeleportHandler.teleportPlayer(player, new Position(starting_point.getX(), starting_point.getY(), starting_point.getZ() + currentZ), TeleportType.ANCIENT);
        player.msgFancyPurp("May luck be in your favor!");
    }

    public void clickPortal(int npcId) {
        if (tierOfPick != null) {
            for (int i = 0; i < portalList.size(); i++) {
                if (portalList.get(i).getNpcId() == npcId) {
                    if (portalList.get(i).isWinningPort()) {
                        NPC npcToSpawn = new NPC(13631, new Position(2142, 5538, 1 + currentZ));
                        npcToSpawn.setDefaultConstitution(100);
                        npcToSpawn.setConstitution(100);
                        npcToSpawn.respawn = false;
                        npcToSpawn.setPositionToFace(new Position(2142, 5538 - 1, 1 + currentZ));
                        npcToSpawn.getMovementQueue().setLockMovement(true).reset();
                        World.register(npcToSpawn);
                        player.moveTo(new Position(chest_map.getX(), chest_map.getY(), chest_map.getZ() + currentZ));
                        player.msgFancyPurp("You have selected the correct portal!");
                        return;
                    }
                }
            }
            switch (tierOfPick) {
                case LOW_TIER:
                    int random = Misc.random(1, 4);
                    if (random == 1) {
                        player.getInventory().add(10946, 1);//$5 Bond
                    } else if (random == 2) {
                        player.getInventory().add(1446, 3);//Resource Pack(T1)
                    } else if (random == 3) {
                        player.getInventory().add(23173, 1);//Necromancy Crate
                    } else if (random == 4) {
                        player.getInventory().add(5585, 5);//Rift Key
                    }
                break;
                case MED_TIER:
                    int random1 = Misc.random(1, 4);
                    if (random1 == 1) {
                        player.getInventory().add(23057, 1);//$10 Bond
                    } else if (random1 == 2) {
                        player.getInventory().add(10256, 1);//Earth Crate
                    } else if (random1 == 3) {
                        player.getInventory().add(10260, 1);//Water Crate
                    } else if (random1 == 4) {
                        player.getInventory().add(10262, 1);//Fire Crate
                    }
                break;
                case HIGH_TIER:
                    int random2 = Misc.random(1, 4);
                    if (random2 == 1) {
                        player.getInventory().add(23058, 1);//$25 Bond
                    } else if (random2 == 2) {
                        player.getInventory().add(17129, 1);//Elemental cache
                    } else if (random2 == 3) {
                        player.getInventory().add(5585, 25);//Rift Key
                    } else if (random2 == 4) {
                        player.getInventory().add(23058, 1);//Lover's Crate
                    }
                break;
            }
            if (player.getIslandPicks() > 1) {
                player.setIslandPicks(player.getIslandPicks() - 1);
                startIsland(tierOfPick);
            } else {
                player.moveTo(GameSettings.DEFAULT_POSITION);
                player.setIsland(new Island(player));
            }
            player.msgRed("You have selected the wrong portal!");
        }
    }

    public void clickChest(NPC npc) {
        if (tierOfPick != null) {
            if (npc.getId() == 13631) {
                Item itemToGive = null;
                Random rand = new Random();
                if (tierOfPick.equals(PickTier.LOW_TIER)) {
                    if (IslandData.tier_one.size() > 0) {
                        int randomIndex = rand.nextInt(IslandData.tier_one.size());
                        itemToGive = IslandData.tier_one.get(randomIndex);
                        player.getInventory().add(itemToGive);
                        player.setIslandPicks(player.getIslandPicks() - 1);
                        if (player.getIslandPicks() > 1) {
                            startIsland(tierOfPick);
                        } else {
                            player.moveTo(GameSettings.DEFAULT_POSITION);
                            player.setIsland(new Island(player));
                        }
                        World.sendMessage("<col=AF70C3><shad=0>[DONO]@red@ " + player.getUsername() + " <col=AF70C3><shad=0>has just received a(n) " + itemToGive.getDefinition().getName() + " from Donation Island.");
                        World.deregister(npc);
                    } else {
                        player.msgRed("Please ask a manager+ to fill this.");
                    }
                }
                if (tierOfPick.equals(PickTier.MED_TIER)) {
                    if (IslandData.tier_two.size() > 0) {
                        int randomIndex = rand.nextInt(IslandData.tier_two.size());
                        itemToGive = IslandData.tier_two.get(randomIndex);
                        player.getInventory().add(itemToGive);
                        player.setIslandPicks(player.getIslandPicks() - 1);
                        if (player.getIslandPicks() > 1) {
                            startIsland(tierOfPick);
                        } else {
                            player.moveTo(GameSettings.DEFAULT_POSITION);
                            player.setIsland(new Island(player));
                        }
                        World.sendMessage("<col=AF70C3><shad=0>[DONO]@red@ " + player.getUsername() + " <col=AF70C3><shad=0>has just received a(n) " + itemToGive.getDefinition().getName() + " from Donation Island.");
                        World.deregister(npc);
                    } else {
                        player.msgRed("Please ask a Manager+ to fill this.");
                    }
                }
                if (tierOfPick.equals(PickTier.HIGH_TIER)) {
                    if (IslandData.tier_three.size() > 0) {
                        int randomIndex = rand.nextInt(IslandData.tier_three.size());
                        itemToGive = IslandData.tier_three.get(randomIndex);
                        player.getInventory().add(itemToGive);
                        player.setIslandPicks(player.getIslandPicks() - 1);
                        if (player.getIslandPicks() > 1) {
                            startIsland(tierOfPick);
                        } else {
                            player.moveTo(GameSettings.DEFAULT_POSITION);
                            player.setIsland(new Island(player));
                        }
                        World.sendMessage("<col=AF70C3><shad=0>[DONO]@red@ " +player.getUsername() + " <col=AF70C3><shad=0>has just received a(n) " + itemToGive.getDefinition().getName() + " from Donation Island.");
                        World.deregister(npc);
                    } else {
                        player.msgRed("Please ask a Manager+ to fill this.");
                    }
                }
            }
        }
    }

}
