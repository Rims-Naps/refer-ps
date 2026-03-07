package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OfflineStorageSystem {

    private static final String STORAGE_PATH = "data/offline_storage/";

    // Initialize the storage directory
    public static void init() {
        File dir = new File(STORAGE_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    // Send an item to a player's offline storage, creating the file if it doesn't exist
    public static boolean sendItemToStorage(String targetName, int itemId, int amount) {
        if (amount <= 0 || amount > Integer.MAX_VALUE) {
            return false; // Invalid amount
        }
        if (!isValidItem(itemId)) {
            return false; // Invalid item
        }

        List<Item> storage = loadStorage(targetName); // Load existing items (returns empty list if file doesn't exist)
        storage.add(new Item(itemId, amount));
        return saveStorage(targetName, storage); // Save items, creating file if needed
    }

    // Load a player’s offline storage from their storage file
    public static List<Item> loadStorage(String playerName) {
        List<Item> items = new ArrayList<>();
        File file = new File(STORAGE_PATH + playerName.toLowerCase() + ".txt");
        if (!file.exists()) {
            return items; // Return empty list if file doesn’t exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("item=")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        String[] itemData = parts[1].split(",");
                        if (itemData.length == 2) {
                            try {
                                int itemId = Integer.parseInt(itemData[0]);
                                int amount = Integer.parseInt(itemData[1]);
                                items.add(new Item(itemId, amount));
                            } catch (NumberFormatException e) {
                                System.err.println("Error parsing item data: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading storage for " + playerName + ": " + e.getMessage());
        }
        return items;
    }

    // Save a player’s offline storage to their storage file
    private static boolean saveStorage(String playerName, List<Item> items) {
        File file = new File(STORAGE_PATH + playerName.toLowerCase() + ".txt");
        File tempFile = new File(STORAGE_PATH + playerName.toLowerCase() + "_temp.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            for (Item item : items) {
                writer.write("item=" + item.getId() + "," + item.getAmount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving storage for " + playerName + ": " + e.getMessage());
            return false;
        }

        // Replace original file with updated file
        if (file.exists() && !file.delete()) {
            System.err.println("Error deleting old storage file for " + playerName);
            return false;
        }
        if (!tempFile.renameTo(file)) {
            System.err.println("Error renaming temp file for " + playerName);
            return false;
        }
        return true;
    }

    // Claim items from offline storage
    public static boolean claimStorage(Player player) {
        List<Item> items = loadStorage(player.getUsername());
        if (items.isEmpty()) {
            player.msgFancyPurp("Your offline storage is empty.");
         //   player.getPackets().sendGameMessage("Your offline storage is empty.");
            return false;
        }

        // Check if player has enough inventory space
        int freeSlots = player.getInventory().getFreeSlots();
        if (freeSlots < items.size()) {
            player.msgFancyPurp("Not enough space in your inventory to claim all items.");
           // player.getPackets().sendGameMessage("Not enough space in your inventory to claim all items.");
            return false;
        }

        // Add items to inventory and clear storage
        for (Item item : items) {
            player.depositItemBank(new Item(item.getId(),item.getAmount()), false);
           // player.getInventory().addItem(item.getId(), item.getAmount());
        }
        clearStorage(player.getUsername());
        player.msgFancyPurp("You have claimed all items from your offline storage.");
      //  player.getPackets().sendGameMessage("You have claimed all items from your offline storage.");
        return true;
    }

    // Clear a player's offline storage by emptying the file
    private static void clearStorage(String playerName) {
        // Save an empty list to the file instead of deleting it
        saveStorage(playerName, new ArrayList<>());
    }

    // Check if an item is valid (basic check, expand as needed)
    private static boolean isValidItem(int itemId) {
        return itemId >= 0 && itemId < 40000; // Adjust based on your item definitions
    }

    // Notify player on login if they have items in storage
    public static void checkStorageOnLogin(Player player) {
        List<Item> items = loadStorage(player.getUsername());
        if (!items.isEmpty()) {
            player.msgFancyPurp("You have " + items.size() + " item(s) in your offline storage. Use ::claimstorage to claim them.");
            //player.getPackets().sendGameMessage("You have " + items.size() + " item(s) in your offline storage. Use ::claimstorage to claim them.");
        }
    }
}