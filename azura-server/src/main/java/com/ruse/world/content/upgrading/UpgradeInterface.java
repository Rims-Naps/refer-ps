package com.ruse.world.content.upgrading;

import com.ruse.donation.DonatorRanks;
import com.ruse.model.Item;
import com.ruse.model.PlayerRights;
import com.ruse.model.Skill;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.PlayerLogs;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class UpgradeInterface {

    private Player player;
    private Upgradeables selectedUpgrade;
    private ArrayList<Upgradeables> upgradeablesArrayList;

    public UpgradeInterface(Player player) {
        this.player = player;
    }

    public boolean handleButton(int buttonId) {

        switch (buttonId) {
            case 121020:
                handleUpgrade(true);
                return true;
            case 121021:
                handleUpgrade(false);
                return true;
            case 121005:
                openInterface(Upgradeables.UpgradeType.WEAPON);
                return true;
            case 121006:
                openInterface(Upgradeables.UpgradeType.ARMOUR);
                return true;
            case 121007:
                openInterface(Upgradeables.UpgradeType.ACCESSORY);
                return true;
            case 121008:
                openInterface(Upgradeables.UpgradeType.MISC);
                return true;
        }

        return false;
    }


    public void handleUpgrade(boolean all) {

        if (!player.getClickDelay().elapsed(1200)) {
            player.getPacketSender().sendMessage("<shad=1>@red@Please wait a few seconds before trying to Empower again.");
            return;
        }
        player.getClickDelay().reset();

        if (selectedUpgrade == null) {
            player.sendMessage("Choose an item to Empower.");
            return;
        }
        Arrays.stream(Upgradeables.values()).forEach(val -> {
            if (val.getRequired().getId() == selectedUpgrade.getRequired().getId()) {
                if (getRestrictions(val, all)) {
                    ItemDefinition definition = ItemDefinition.forId(val.getRequired().getId() + 1);
                    boolean noted = false;
                    if (all) {
                        if (definition.isNoted() && definition.isStackable()) {
                            String name = definition.getName();
                            definition = ItemDefinition.forId(val.getRequired().getId());
                            String originalName = definition.getName();
                            noted = name.equals(originalName);
                        }
                        int amount = player.getInventory().getAmount(selectedUpgrade.getRequired().getId()) +
                                (noted ? player.getInventory().getAmount(selectedUpgrade.getRequired().getAmount() + 1) : 0);
                        int failed = 0, success = 0;
                        for (int i = 0; i < amount; i++) {
                            if (player.getInventory().contains(ItemDefinition.COIN_ID,
                                    val.getCost())) {
                                if (noted) {
                                    if (player.getInventory().contains(val.getRequired().getId())) {
                                        player.getInventory().delete(val.getRequired());
                                    } else if (player.getInventory().contains(val.getRequired().getId() + 1)) {
                                        player.getInventory().delete(new Item(val.getRequired().getId() + 1,
                                                val.getRequired().getAmount()));
                                    }
                                } else {
                                    player.getInventory().delete(val.getRequired());
                                }
                                player.getInventory().delete(ItemDefinition.COIN_ID,
                                        val.getCost());

                              
                                boolean random =  Misc.getRandomDouble(99) < getBoost(val.getSuccessRate());
                                if (random) {
                                    player.getSkillManager().addExperience(Skill.ALCHEMY, 1000);
                                    success++;
                                    if (noted) {
                                        player.getBank(Bank.getTabForItem(player, val.getReward().getId())).add(val.getReward(), false);
                                        player.sendMessage("Your items have been banked.");
                                    } else {
                                        player.getInventory().add(val.getReward());
                                    }
                                    if (val.isRare()) {
                                        String msg = "<col=AF70C3><img=5>[Empowerment]<img=5>@red@ " + player.getUsername()
                                                + " Has successfully Empowered their "
                                                + val.getRequired().getDefinition().getName() + " to "
                                                + val.getReward().getDefinition().getName();
                                        World.sendMessage(msg);


                                    }
                                    PlayerLogs.logUpgrades(player.getUsername(), "Empowered : " + val.getRequired().getDefinition().getName()
                                            + ", id: " + val.getRequired().getId() + ", amount: " + val.getRequired().getAmount() + " --into-- " +
                                            val.getReward().getDefinition().getName() + ", id: " + val.getReward().getId() + ", amount: " + val.getReward().getAmount());

                                } else {
                                    failed++;
                                }
                            } else {
                                break;
                            }
                        }
                        player.getPacketSender().sendMessage("You successfully Empowered " + success
                                + " items and failed to Empower " + failed + " items.");
                    } else {
                        if (definition.isNoted() && definition.isStackable()) {
                            String name = definition.getName();
                            definition = ItemDefinition.forId(val.getRequired().getId());
                            String originalName = definition.getName();
                            noted = name.equals(originalName);
                        }
                        if (noted) {
                            if (player.getInventory().contains(val.getRequired().getId())) {
                                player.getInventory().delete(val.getRequired());
                            } else if (player.getInventory().contains(val.getRequired().getId() + 1)) {
                                player.getInventory().delete(new Item(val.getRequired().getId() + 1,
                                        val.getRequired().getAmount()));
                            }
                        } else {
                            player.getInventory().delete(val.getRequired());
                        }
                        player.getInventory().delete(ItemDefinition.COIN_ID, val.getCost());

                        TimerTask task = new TimerTask() {
                            int tick = 0;

                            @Override
                            public void run() {
                                if (tick == 0) {
                                    player.getPacketSender().sendMessage("You attempt to Empower your item....");
                                } else if (tick == 2) {
                                    boolean success =  Misc.getRandomDouble(99) < getBoost(val.getSuccessRate());
                                    if (success) {
                                        player.getSkillManager().addExperience(Skill.ALCHEMY, 1000);
                                        player.getPacketSender().sendMessage("You successfully Empowered your item!");
                                        player.getInventory().add(val.getReward());
                                        String msg = "@red@<shad=0><img=5>Empower:<img=5><shad=0>@red@ " + player.getUsername()
                                                + " Empowered his "
                                                + val.getRequired().getDefinition().getName() + " to "
                                                + val.getReward().getDefinition().getName();
                                            World.sendMessage(msg);
                                        if (val.getReward().getId() == 23112) {
                                        }



                                    } else {
                                        player.getPacketSender().sendMessage("You failed to Empower!");
                                        World.sendMessage("@blu@<shad=0><img=5>Empower:<img=5><shad=0>@red@ " + player.getUsername() + " @bla@<shad=0>Failed To Upgrade@red@<shad=0> "  + val.getRequired().getDefinition().getName() + "@bla@<shad=0> To@red@<shad=0> " + val.getReward().getDefinition().getName());
                                    }
                                    cancel();
                                }
                                tick++;
                            }

                        };

                        Timer timer = new Timer();
                        timer.schedule(task, 500, 500);
                    }
                }
            }
        });
    }
    
    public double getBoost(double chance){
        double percentBoost = 0D;
        if(player.getRights() == PlayerRights.YOUTUBER) {
            percentBoost += 15;
        } else if(player.getAmountDonated() >= DonatorRanks.CORRUPT_AMOUNT) {
            percentBoost += 15;
        } else if(player.getAmountDonated() >= DonatorRanks.GUARDIAN_AMOUNT) {
            percentBoost += 14;
        } else if(player.getAmountDonated() >= DonatorRanks.COSMIC_AMOUNT) {
            percentBoost += 13;
        } else if(player.getAmountDonated() >= DonatorRanks.GLADIATOR_AMOUNT) {
            percentBoost += 12;
        } else if(player.getAmountDonated() >= DonatorRanks.ASCENDANT_AMOUNT) {
            percentBoost += 11;
        } else if(player.getAmountDonated() >= DonatorRanks.CELESTIAL_AMOUNT) {
            percentBoost += 10;
        } else if(player.getAmountDonated() >= DonatorRanks.ARCHON_AMOUNT) {
            percentBoost += 7;
        } else if(player.getAmountDonated() >= DonatorRanks.MYTHIC_AMOUNT) {
            percentBoost += 5;
        }


        if (player.getUpgBoost() > 0) {
            percentBoost += player.getUpgBoost();
        }



        double multiplier = 1 + (percentBoost / 100D);

        chance *= multiplier;

        return chance;
    }

    private boolean getRestrictions(Upgradeables data, boolean all) {
        ItemDefinition definition = ItemDefinition.forId(data.getRequired().getId() + 1);
        boolean noted = false;
        if (definition.isNoted() && definition.isStackable()) {
            String name = definition.getName();
            definition = ItemDefinition.forId(data.getRequired().getId());
            String originalName = definition.getName();
            noted = name.equals(originalName);
        }
        if (noted && player.getInventory().contains(data.getRequired().getId() + 1)) {
            int amount = all ? player.getInventory().getAmount(data.getRequired().getId() + 1) : 1;
            if (!player.getInventory().contains(data.getRequired().getId() + 1, amount) ||
                    !player.getInventory().contains(ItemDefinition.COIN_ID, amount *
                            data.getCost())) {
                player.getPacketSender().sendMessage("You do not have the required materials!");
                return false;
            }
        } else {
            if (!player.getInventory().contains(data.getRequired().getId()) ||
                    !player.getInventory().contains(ItemDefinition.COIN_ID,  data.getCost())) {
                player.getPacketSender().sendMessage("You do not have the required materials!");
                return false;
            }
        }
        return true;
    }

    public void handleItemAction(int slot) {
        if (upgradeablesArrayList != null && upgradeablesArrayList.get(slot) != null) {
            showUpgrade(upgradeablesArrayList.get(slot));
        }
    }


    public void showUpgrade(Upgradeables upgrade) {

        selectedUpgrade = upgrade;

        player.getPacketSender().sendItemOnInterface(121017, upgrade.getReward());
        player.getPacketSender().sendString(121018, "Coins Needed: @red@" + Misc.formatNumber(upgrade.getCost()));
        player.getPacketSender().sendString(121019, "Chance: @red@" + upgrade.getSuccessRate() + "%");

    }

    public void openInterface(Upgradeables.UpgradeType type) {
        player.getPacketSender().sendConfig(5334, type.ordinal());

        selectedUpgrade = null;

        player.getPacketSender().sendItemOnInterface(121017, -1, 1);
        player.getPacketSender().sendString(121018, "Coins req: @whi@---");
        player.getPacketSender().sendString(121019, "Success rate: @whi@---");

        upgradeablesArrayList = Upgradeables.getForType(type);
        for (int i = 0; i < 50; i++) {
            if (upgradeablesArrayList.size() > i) {
                player.getPacketSender().sendItemOnInterface(62209,
                        upgradeablesArrayList.get(i).getRequired().getId(), i, upgradeablesArrayList.get(i).getRequired().getAmount());
            } else {
                player.getPacketSender().sendItemOnInterface(62209, -1, i, 1);
            }
        }

        player.getPacketSender().sendInterface(121000);

    }

}
