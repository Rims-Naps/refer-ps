package com.ruse.donation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruse.model.Item;
import com.ruse.util.JsonLoader;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joshua Ransom on 9/1/2020.
 */
public class StoreItems {
    @Getter
    private static final Map<Integer, StoreItems> storeItems = new HashMap<>();
    @Getter
    public final int productId;
    @Getter
    private final String name;
    @Getter
    private final Item[] items;

    StoreItems(int productId, String name, Item... items) {
        this.productId = productId;
        this.name = name;
        this.items = items;
    }

    public static JsonLoader parseStore() {
        return new JsonLoader("./data/def/json/store.json") {
            @Override
            public void load(JsonObject reader, Gson builder) {
                int productId = reader.get("productId").getAsInt();
                String name = reader.get("name").getAsString();
                Item[] items = builder.fromJson(reader.get("items").getAsJsonArray(), Item[].class);
                storeItems.put(productId, new StoreItems(productId, name, items));
            }
        };
    }
}

class StoreItem {
    public final int productId;
    public final int quantity;

    public StoreItem(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}