package com.ruse.world.content;

import java.util.HashMap;
import java.util.Map;

public class ItemNamestoID {


    public static int getIdByName(String itemName) {
        // Implement logic to map item names to IDs
        // For example, you could use a Map<String, Integer> or a database lookup
        // Return -1 if the name is not found
        // This is just a placeholder; you should replace it with your actual logic
        if (itemName.equalsIgnoreCase("water crate")) {
            return 10060;
        } else if (itemName.equalsIgnoreCase("item2")) {
            return 2;
        }
        return -1;
    }
}
