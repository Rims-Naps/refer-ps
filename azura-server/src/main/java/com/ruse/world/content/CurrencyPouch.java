package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.player.Player;

public class CurrencyPouch {
   public long monsteressence;
   public long firessence;
   public long wateressence;
   public long earthessence;
   public long slayeressence;


    public static void checkBalance(Player player) {
        long amount;
        String currency;

        amount = player.getCurrencyPouch().monsteressence;
        currency = "Monster Essence";
        player.sendMessage("Your pouch contains @red@" + Misc.formatNumber(amount) + " @bla@" + currency);

        amount = player.getCurrencyPouch().firessence;
        currency = "Fire Essence";
        player.sendMessage("Your pouch contains @red@" + Misc.formatNumber(amount) + " @bla@" + currency);

        amount = player.getCurrencyPouch().wateressence;
        currency = "Water Essence";
        player.sendMessage("Your pouch contains @red@" + Misc.formatNumber(amount) + " @bla@" + currency);

        amount = player.getCurrencyPouch().earthessence;
        currency = "Earth Essence";
        player.sendMessage("Your pouch contains @red@" + Misc.formatNumber(amount) + " @bla@" + currency);

        amount = player.getCurrencyPouch().slayeressence;
        currency = "Slayer Essence";
        player.sendMessage("Your pouch contains @red@" + Misc.formatNumber(amount) + " @bla@" + currency);
    }

    public static void withdrawCurrency(Player player, int type, int amount) {
        if (amount <= 0) {
            DialogueManager.sendStatement(player, "You can not withdraw an invalid amount!");
            return;
        }

        boolean insufficientFundsError = false;
        boolean emptyFundsError = false;
        int currency;
        int inventoryAmount;

        switch (type) {
            case 0: //MONSTER ESSENCE
                player.getPacketSender().sendInterfaceRemoval();

                currency = 19062;
                inventoryAmount = player.getInventory().getAmount(currency);

                if (player.getCurrencyPouch().monsteressence < amount) {
                    insufficientFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().monsteressence <= 0) {
                    emptyFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().monsteressence >= amount) {
                    long longAmount = ((long)inventoryAmount + (long)amount);
                    if (longAmount > Integer.MAX_VALUE) {
                        long amountToSubtract = Integer.MAX_VALUE - inventoryAmount;
                        amount = (int) amountToSubtract;
                    }
                    if (amount <= 0) {
                        break;
                    }
                    player.getCurrencyPouch().monsteressence -= amount;
                    player.setMonsteressence(player.getMonsteressence() - amount);
                    player.getInventory().add(new Item(currency, amount));
                }
                break;
            case 1: //FIRE ESSENCE
                currency = 11054;
                inventoryAmount = player.getInventory().getAmount(currency);

                if (player.getCurrencyPouch().firessence < amount) {
                    insufficientFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().firessence <= 0) {
                    emptyFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().firessence >= amount) {
                    long longAmount = ((long)inventoryAmount + (long)amount);
                    if (longAmount > Integer.MAX_VALUE) {
                        long amountToSubtract = Integer.MAX_VALUE - inventoryAmount;
                        amount = (int) amountToSubtract;
                    }
                    if (amount <= 0) {
                        break;
                    }
                    player.getCurrencyPouch().firessence -= amount;
                    player.setFireessence(player.getFireessence() - amount);
                    player.getInventory().add(new Item(currency, amount));
                }
                break;
            case 2: //WATER ESSENCE
                currency = 11056;
                inventoryAmount = player.getInventory().getAmount(currency);

                if (player.getCurrencyPouch().wateressence < amount) {
                    insufficientFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().wateressence <= 0) {
                    emptyFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().wateressence >= amount) {
                    long longAmount = ((long)inventoryAmount + (long)amount);
                    if (longAmount > Integer.MAX_VALUE) {
                        long amountToSubtract = Integer.MAX_VALUE - inventoryAmount;
                        amount = (int) amountToSubtract;
                    }
                    if (amount <= 0) {
                        break;
                    }
                    player.getCurrencyPouch().wateressence -= amount;
                    player.setWateressence(player.getWateressence() - amount);
                    player.getInventory().add(new Item(currency, amount));
                }
                break;
            case 3: //EARTH ESSENCE
                currency = 11052;
                inventoryAmount = player.getInventory().getAmount(currency);

                if (player.getCurrencyPouch().earthessence < amount) {
                    insufficientFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().earthessence <= 0) {
                    emptyFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().earthessence >= amount) {
                    long longAmount = ((long)inventoryAmount + (long)amount);
                    if (longAmount > Integer.MAX_VALUE) {
                        long amountToSubtract = Integer.MAX_VALUE - inventoryAmount;
                        amount = (int) amountToSubtract;
                    }
                    if (amount <= 0) {
                        break;
                    }
                    player.getCurrencyPouch().earthessence -= amount;
                    player.setEarthessence(player.getEarthessence() - amount);
                    player.getInventory().add(new Item(currency, amount));
                }
                break;
            case 4: //SLAYER ESSENCE
                currency = 3576;
                inventoryAmount = player.getInventory().getAmount(currency);

                if (player.getCurrencyPouch().slayeressence < amount) {
                    insufficientFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().slayeressence <= 0) {
                    emptyFundsError = true;
                    break;
                }

                if (player.getCurrencyPouch().slayeressence >= amount) {
                    long longAmount = ((long)inventoryAmount + (long)amount);
                    if (longAmount > Integer.MAX_VALUE) {
                        long amountToSubtract = Integer.MAX_VALUE - inventoryAmount;
                        amount = (int) amountToSubtract;
                    }
                    if (amount <= 0) {
                        break;
                    }
                    player.getCurrencyPouch().slayeressence -= amount;
                    player.setSlayeressence(player.getSlayeressence() - amount);
                    player.getInventory().add(new Item(currency, amount));
                }
                break;
        }
        if (insufficientFundsError) {
            DialogueManager.sendStatement(player, "You do not Enough Essence/Shards to withdraw.");
            return;
        }
        if (emptyFundsError) {
            DialogueManager.sendStatement(player, "You do not have any of this Essence/Shards!");
        }
    }

    public static void depositCurrency(Player player, int type, int amount) {
        if (amount <= 0) {
            DialogueManager.sendStatement(player, "You can not deposit an invalid amount!");
            return;
        }

        boolean insufficientFundsError = false;
        int currency;
        switch (type) {
            case 0: //MONSTER ESSENCE
                currency = 19062;
                break;
            case 1: //FIRE ESSENCE
                currency = 11054;
                break;
            case 2: //WATER ESSENCE
                currency = 11056;
                break;
            case 3: //EARTH ESSENCE
                currency = 11052;
                break;
            case 4: //SLAYER ESSENCE
                currency = 3576;
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        if (attemptDeposit(player, amount, insufficientFundsError, currency)) {
            DialogueManager.sendStatement(player, "You do not have this much to withdraw!");
        }

    }

    private static boolean attemptDeposit(Player player, int amount, boolean insufficientFundsError, int currency) {
        int inventoryAmount = player.getInventory().getAmount(currency);

        if (inventoryAmount < amount) {
            return true;
        }

        if (currency == 19062) {
            player.getCurrencyPouch().monsteressence += amount;
            player.setMonsteressence(player.getMonsteressence() + amount);
        } else if (currency == 11054) {
            player.getCurrencyPouch().firessence += amount;
            player.setFireessence(player.getFireessence() + amount);
        } else if (currency == 11056) {
            player.getCurrencyPouch().wateressence += amount;
            player.setWateressence(player.getWateressence() + amount);
        } else if (currency == 11052) {
            player.getCurrencyPouch().earthessence += amount;
            player.setEarthessence(player.getEarthessence() + amount);
        } else if (currency == 3576) {
            player.getCurrencyPouch().slayeressence += amount;
            player.setSlayeressence(player.getSlayeressence() + amount);
        }
        player.getInventory().delete(new Item(currency, amount));
        return insufficientFundsError;
    }

    public static void handle(Player player, Item used) {
        if (used.getId() == 19062) {
            depositCurrency(player, 0, used.getAmount());
        } else if (used.getId() == 11054) {
            depositCurrency(player, 1, used.getAmount());
        } else if (used.getId() == 11056) {
            depositCurrency(player, 2, used.getAmount());
        } else if (used.getId() == 11052) {
            depositCurrency(player, 3, used.getAmount());
        } else if (used.getId() == 3576) {
            depositCurrency(player, 4, used.getAmount());
        }  else {
            DialogueManager.sendStatement(player, "You can not use this currency on the pouch!");
        }
    }
}
