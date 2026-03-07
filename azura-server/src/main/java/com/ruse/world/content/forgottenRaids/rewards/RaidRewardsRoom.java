package com.ruse.world.content.forgottenRaids.rewards;

import com.ruse.model.*;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.combat.strategy.CombatStrategy;
import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.boss.ForgottenRaidBoss;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ruse.model.container.impl.Equipment.AMMUNITION_SLOT;

public class RaidRewardsRoom extends ForgottenRaidBoss {

    private final Position[] CHEST_POSITIONS = new Position[] {
            new Position(3290, 3347).setZ(getConnectedRaid().getMyHeight()),
            new Position(3292, 3347).setZ(getConnectedRaid().getMyHeight()),
            new Position(3298, 3347).setZ(getConnectedRaid().getMyHeight()),
            new Position(3300, 3347).setZ(getConnectedRaid().getMyHeight()),
            new Position(3301, 3351).setZ(getConnectedRaid().getMyHeight())};

    public RaidRewardsRoom(ForgottenRaid connectedRaid) {
        super(connectedRaid);
    }

    ArrayList<RaidReward> raidReward = new ArrayList<>();


    @Override
    public void spawn() {
        AtomicInteger index = new AtomicInteger();
        getConnectedRaid().getParty().getPlayers().forEach(playerName -> {
            Player player = World.getPlayerByName(playerName);
            if (player != null) {
                player.moveTo(position().copy());
                assignChest(player, index.getAndIncrement());
                displayChests(player);
            }
        });

    }

    private void assignChest(Player player, int index) {
        boolean nameListedAlready = false;
        for (RaidReward reward : raidReward) {
            if (reward.getPlayerName().equals(player.getUsername())) {
                nameListedAlready = true;
                break;
            }
        }
        if (!nameListedAlready) {
            RaidRewardData data = RaidRewardData.getRandomReward();
            raidReward.add(new RaidReward(player.getUsername(), data.getRarity(), data, new Position(CHEST_POSITIONS[index])));
        }
    }

    private void displayChests(Player player) {
        if (player != null) {
            for (RaidReward reward : raidReward) {
                if (reward.getPlayerName().equals(player.getUsername())) {
                    NPC npc = new NPC(getChestForRarity(reward.getRarity()), reward.getPosition());
                    npc.setDirection(Direction.SOUTH);
                    World.register(npc);
                    player.getPacketSender().sendPositionalHint(reward.getPosition(), 3);
                }
            }
        }
    }
    public void openChest(Player player, Position loc, NPC npc) {

        for (int i = 0; i < raidReward.size(); i++) {
            if (raidReward.get(i).getPlayerName().equals(player.getUsername())) {
                if (raidReward.get(i).getPosition().equals(loc)) {
                    player.performAnimation(new Animation(827));
                    player.performGraphic(new Graphic(1322));
                    if (raidReward.get(i).getRarity().equals(RaidRewardRarity.EXOTIC)) {
                        World.sendMessage( "@bla@<shad=0><img=3322>[NARNIA]<img=3322> " + player.getUsername() + " @bla@<shad=0>has just received @red@<shad=0> " + ItemDefinition.forId(raidReward.get(i).getItemData().getReward().getId()) + " @bla@<shad=0> From Narnia Raids");
                    }

                    if (player.getEquipment().get(AMMUNITION_SLOT).getId() == 2728) {
                        player.getInventory().add(new Item(raidReward.get(i).getItemData().getReward().getId(), raidReward.get(i).getItemData().getReward().getAmount()));
                        player.sendMessage("@red@Your Raids Soul Granted you extra rewards!");
                    }

                    if (player.getEquipment().get(AMMUNITION_SLOT).getId() == 2730) {
                        player.getInventory().add(new Item(raidReward.get(i).getItemData().getReward().getId(), raidReward.get(i).getItemData().getReward().getAmount()));
                        player.getInventory().add(new Item(raidReward.get(i).getItemData().getReward().getId(), raidReward.get(i).getItemData().getReward().getAmount()));
                        player.sendMessage("@red@Your Raids Soul(2) Granted you 2 extra rewards!");
                    }

                        player.getInventory().add(new Item(raidReward.get(i).getItemData().getReward().getId(), raidReward.get(i).getItemData().getReward().getAmount()));
                    player.increaseNarniaRaids(1);

                    player.getPacketSender().sendPositionalHint(raidReward.get(i).getPosition(), -1);
                    raidReward.remove(i);
                    World.deregister(npc);
                    death(69420);
                    break;
                }
            }
        }
    }



    private int getChestForRarity(RaidRewardRarity rarity) {
        switch (rarity) {
            case COMMON:
                return 1853;
            case RARE:
                return 1854;
            case EXOTIC:
                return 1855;
        }
        return -1;
    }

    @Override
    public int npcId() {
        return -1;
    }

    @Override
    public int health() {
        return -1;
    }

    @Override
    public Position position() {
        return new Position(3296, 3365).setZ(getConnectedRaid().getMyHeight());
    }

    @Override
    public Position afterDeathPosition() {
        return null;
    }

    @Override
    public String bossName() {
        return "raid-reward-room";
    }

    @Override
    public CombatStrategy combatStrategy() {
        return null;
    }

    public void death(int npcId) {
        if (raidReward.size() == 0) {
            getConnectedRaid().getParty().getPlayers().forEach(playerName -> {
                Player player = World.getPlayerByName(playerName);
                if (player != null) {
                    player.moveTo(3244, 3363,0);
                    player.getForgottenRaidParty().setInRaid(false);
                    player.getPacketSender().sendMessage("You have completed the raid");
                }
            });
        }
    }
}
