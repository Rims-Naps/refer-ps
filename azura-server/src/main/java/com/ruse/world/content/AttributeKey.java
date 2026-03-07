package com.ruse.world.content;

public class AttributeKey<T> {

    /**
     * The persistence key for this attribute key. If an {@code AttributeKey} has a persistenceKey,
     * the attribute will be saved and loaded with the player, rather than being a temporary attribute.
     */
    private final String persistenceKey;

    /**
     * The config id for this {@code AttributeKey}.
     */
    private final int config;

    /**
     * Create an {@code AttributeKey} representing a temporary attribute.
     */
    public AttributeKey() {
        this(null);
    }

    /**
     * Create an {@code AttributeKey} with a specified persistence key
     * @param persistenceKey    The persistence key
     */
    public AttributeKey(String persistenceKey) {
        this(persistenceKey, -1);
    }

    /**
     * Creates an {@code AttributeKey} with a specified persistence key and config
     * @param persistenceKey    The persistence key
     * @param config            The config id
     */
    public AttributeKey(String persistenceKey, int config) {
        this.persistenceKey = persistenceKey;
        this.config = config;
        AttributeMap.register(this);
    }

    /**
     * Compare attributes by their contents, or persistence keys.
     * @param obj   The object to compare
     * @return      If the attribute is the same
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AttributeKey) {
            AttributeKey<?> other = (AttributeKey) obj;
            if (persistenceKey != null && other.persistenceKey != null) {
                return other.persistenceKey.equals(persistenceKey);
            }
            return hashCode() == other.hashCode();
        }
        return false;
    }

    /**
     * Gets the hash code of the {@code AttributeKey}
     * @return  The hash code
     */
    @Override
    public int hashCode() {
        return persistenceKey != null ? persistenceKey.hashCode() : super.hashCode();
    }

    /**
     * Gets the persistence key for this {@code AttributeKey}
     * @return  The persistence key
     */
    String getPersistenceKey() {
        return persistenceKey;
    }

    /**
     * Gets the config id for this {@code AttributeKey}
     * @return  The config id
     */
    public int getConfig() {
        return config;
    }
}
