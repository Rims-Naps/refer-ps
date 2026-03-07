package com.ruse.world.content;

import com.ruse.world.entity.impl.Character;
import com.ruse.world.entity.impl.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AttributeMap {

    /**
     * The {@link Character} this attribute map belongs to
     */
    private final Character entity;

    /**
     * The internal map of keys to values
     */
    private final Map<AttributeKey<?>, Object> attributes = new HashMap<>();

    /**
     * A map of persistence keys to configuration values, for attributes that are
     * loaded by the player serializer
     */
    private static final Map<String, Integer> configValues = new HashMap<>();

    /**
     * Creates the {@code AttributeMap} instance
     * @param entity    The entity this belongs to
     */
    public AttributeMap(Character entity) {
        this.entity = entity;
    }

    /**
     * Registers a key to the map
     * @param key   The {@code AttributeKey} instance
     * @param <T>   The attribute type
     */
    static <T> void register(AttributeKey<T> key) {
        if (key.getPersistenceKey() != null && key.getConfig() != -1 && !configValues.containsKey(key.getPersistenceKey()))
            configValues.put(key.getPersistenceKey(), key.getConfig());
    }

    /**
     * Gets the value for a given attribute key
     * @param key   The key
     * @return      The value
     */
    public <T> T get(AttributeKey<T> key) {
        return (T) attributes.get(key);
    }

    /**
     * Gets the value for a given attribute key. If the key is not found, the
     * default value will be used.
     * @param key       The key
     * @param value     The default value
     * @return          The value
     */
    public <T> T getOrDefault(AttributeKey<T> key, T value) {
        if (!attributes.containsKey(key)) {
            return value;
        }

        T entry = (T) attributes.get(key);
        if (entry == null) {
            return value;
        }

        return entry;
    }

    /**
     * Sets the value of an {@link AttributeKey}
     * @param key           The attribute key instance
     * @param value         The value to use
     */
    public <T> void set(AttributeKey<T> key, T value) {
        set(key, value, true);
    }

    /**
     * Sets the value of an {@link AttributeKey}
     * @param key           The attribute key instance
     * @param value         The value to use
     * @param syncConfig    If true and the attribute map belongs to a player, it will send the associated config value
     */
    public <T> void set(AttributeKey<T> key, T value, boolean syncConfig) {
        attributes.put(key, value);
        if (syncConfig && key.getPersistenceKey() != null && key.getConfig() != -1 && (entity instanceof Player)) {
            Player player = (Player) entity;
            int config = configValues.getOrDefault(key.getPersistenceKey(), key.getConfig());
            player.getPacketSender().sendConfig(config, (Integer) value);
        }
    }

    /**
     * Synchronises the config values of the player's attributes
     */
    public void sync() {
        if (!(entity instanceof Player)) return;
        Player player = (Player) entity;
        for (Map.Entry<AttributeKey<?>, Object> entry : attributes.entrySet()) {
            AttributeKey<?> key = entry.getKey();
            if (key.getConfig() != -1 || configValues.containsKey(key.getPersistenceKey())) {
                int configId = configValues.getOrDefault(key.getPersistenceKey(), key.getConfig());
                player.getPacketSender().sendConfig(configId, (Integer) entry.getValue());
            }
        }
    }

    /**
     * If the {@code AttributeMap} contains a specified key
     * @param key   The attribute key
     * @return      If the key exists
     */
    public <T> boolean has(AttributeKey<T> key) {
        return attributes.containsKey(key);
    }

    /**
     * Removes an {@link AttributeKey} from this {@code AttributeMap}
     * @param key   The key to remove
     */
    public <T> void remove(AttributeKey<T> key) {
        attributes.remove(key);
        if (entity instanceof Player && key.getConfig() != -1) {
            Player player = (Player) entity;
            player.getPacketSender().sendConfig(key.getConfig(), 0);
        }
    }

    /**
     * Adds an amount to an {@link AttributeKey} whose type is an {@link Integer}
     * @param key       The {@code AttributeKey}
     * @param amount    The amount to add to the value
     */
    public void plus(AttributeKey<Integer> key, int amount) {
        Integer value = (Integer) attributes.getOrDefault(key, 0);
        attributes.put(key, value + amount);
    }

    /**
     * Adds 1 to an {@link AttributeKey} whose type is an {@link Integer}
     * @param key       The {@code AttributeKey}
     */
    public void increment(AttributeKey<Integer> key) {
        plus(key, 1);
    }

    /**
     * Subtracts an amount from an {@link AttributeKey} whose type is an {@link Integer}
     * @param key       The {@code AttributeKey}
     * @param amount    The amount to subtract from the value
     */
    public void minus(AttributeKey<Integer> key, int amount) {
        Integer value = (Integer) attributes.getOrDefault(key, 0);
        attributes.put(key, value - amount);
    }

    /**
     * Subtracts 1 from an {@link AttributeKey} whose type is an {@link Integer}
     * @param key       The {@code AttributeKey}
     */
    public void decrement(AttributeKey<Integer> key) {
        minus(key, 1);
    }

    /**
     * A map of persistence keys to their values
     * @return  The map
     */
    public Map<String, Object> toPersistenceMap() {
        return attributes.entrySet().stream()
                .filter(entry -> entry.getKey().getPersistenceKey() != null)
                .collect(Collectors.toMap(entry -> entry.getKey().getPersistenceKey(), Map.Entry::getValue));
    }
}
