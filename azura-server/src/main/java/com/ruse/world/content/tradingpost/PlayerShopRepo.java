package com.ruse.world.content.tradingpost;

import com.google.common.base.Predicate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.ruse.model.Item;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.util.StringUtils;
import com.ruse.world.entity.impl.player.Player;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Contains all player shops.
 */
public class PlayerShopRepo {

    public static final String SAVE_DIRECTORY = "./data/saves/trading_post/";
    private static final ExecutorService searchExecutor = Executors.newSingleThreadExecutor();
    private static final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final ConcurrentMap<Long, PlayerShop> shops = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Integer, ListingMetaData> listingMetaData = new ConcurrentHashMap<>();
    private static final Logger logger = Logger.getLogger(PlayerShopRepo.class.getName());

    public static class ListingMetaData {
        private final int itemId;
        private final ArrayList<Integer> prices = new ArrayList<>();
        private final ArrayList<Integer> counts = new ArrayList<>();

        public ListingMetaData(int itemId) {
            this.itemId = itemId;
        }
    }

    /**
     * Load on-disc shops and start the 15-minute metadata sampler.
     */
    public static void init() {
        deserialize();
        scheduler.scheduleAtFixedRate(() -> {
            listingMetaData.clear();
            for (ExchangeSearchResult result : search(null, Integer.MAX_VALUE, null, null)) {
                listingMetaData
                        .computeIfAbsent(result.getId(), ListingMetaData::new)
                        .prices.add(result.getPrice());
                listingMetaData
                        .get(result.getId())
                        .counts.add(result.getAmount());
            }
        }, 0, 15, TimeUnit.MINUTES);
    }

    /** Read every `*.json` in SAVE_DIRECTORY into memory */
    public static void deserialize() {
        File dir = new File(SAVE_DIRECTORY);
        if (!dir.exists()) return;
        for (File f : Objects.requireNonNull(dir.listFiles())) {
            if (!f.getName().endsWith(".json")) continue;
            try (JsonReader reader = new JsonReader(new FileReader(f))) {
                PlayerShopSave save = new Gson().fromJson(reader, PlayerShopSave.class);
                shops.put(save.getOwnerLong(), new PlayerShop(save));
            } catch (Exception e) {
                logger.warning("Failed to read shop file " + f.getName() + ": " + e.getMessage());
            }
        }
    }

    /** Write one shop back out to disk */
    public static boolean serialize(PlayerShop shop) {
        Optional<PlayerShopSave> save = shop.createSave();
        if (!save.isPresent()) return false;
        new File(SAVE_DIRECTORY).mkdirs();
        try (Writer w = new FileWriter(SAVE_DIRECTORY + shop.getOwner() + ".json")) {
            w.write(new GsonBuilder().serializeNulls().setPrettyPrinting().create()
                    .toJson(save.get()));
            return true;
        } catch (IOException e) {
            logger.severe("Failed to serialize shop for " + shop.getOwner() + ": " + e.getMessage());
            return false;
        }
    }

    /** Serialize all shops currently in memory */
    public static void serializeAll() {
        int count = 0;
        for (PlayerShop shop : shops.values()) {
            if (serialize(shop)) count++;
        }
        logger.info("Serialized " + count + " trading post inventories.");
    }

    public static int getListings(int itemId) {
        return listingMetaData.containsKey(itemId)
                ? listingMetaData.get(itemId).counts.size()
                : 0;
    }

    public static long getAmount(int itemId) {
        if (!listingMetaData.containsKey(itemId)) return 0;
        long sum = 0;
        for (int c : listingMetaData.get(itemId).counts) sum += c;
        return sum;
    }

    public static int getMedianPrice(int itemId) {
        if (!listingMetaData.containsKey(itemId)) return 0;
        ListingMetaData d = listingMetaData.get(itemId);
        d.prices.sort(Integer::compareTo);
        int mid = d.prices.size()/2;
        return (d.prices.size() % 2 == 1)
                ? d.prices.get(mid)
                : (d.prices.get(mid-1) + d.prices.get(mid)) / 2;
    }

    public static long getAveragePrice(int itemId) {
        if (!listingMetaData.containsKey(itemId)) return 0;
        long sum = 0;
        for (int p : listingMetaData.get(itemId).prices) sum += p;
        return sum / listingMetaData.get(itemId).prices.size();
    }

    public static void put(PlayerShop shop) {
        if (shops.putIfAbsent(shop.getOwnerLong(), shop) != null) {
            throw new IllegalStateException("Shop already exists: " + shop.getOwner());
        }
    }

    public static PlayerShop get(long ownerLong) {
        return shops.get(ownerLong);
    }

    public static PlayerShop getOrRegister(String name) {
        long key = StringUtils.stringToLong(name);
        return shops.computeIfAbsent(key, __ -> new PlayerShop(name));
    }

    /**
     * Core search loop.
     * @param searching if non-null, we skip that player’s own shop
     */
    private static List<ExchangeSearchResult> search(
            Player searching,
            int clampSize,
            Predicate<ExchangeSearchResult> filter,
            Comparator<ExchangeSearchResult> sorter
    ) {
        List<ExchangeSearchResult> results = new ArrayList<>();
        for (PlayerShop shop : shops.values()) {
            if (searching != null && shop.getOwnerLong() == searching.getLongUsername()) continue;
            Item[] items = shop.getContainer().array();
            int[] prices = shop.getPrices();
            long[] times = shop.getListingTime();
            TradingPostCurrency[] curr = shop.getCurrencies();
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null || items[i].getAmount() <= 0) continue;
                ExchangeSearchResult r = new ExchangeSearchResult(
                        items[i].getId(), i, items[i].getAmount(),
                        prices[i], times[i], shop.getOwner(), curr[i]
                );
                if (filter == null || filter.apply(r)) results.add(r);
            }
        }
        if (sorter != null) results.sort(sorter);
        return (results.size() <= clampSize) ? results : results.subList(0, clampSize);
    }

    /** “Most recent” must pass null so we include *all* shops */
    public static void searchForRecent(final Player player) {
        if (player.getPlayerShops().isSearchDelayed()) return;
        searchExecutor.submit(() -> {
            List<ExchangeSearchResult> res = search(
                    /*searching=*/ null,
                    /*clamp=*/100,
                    /*filter=*/ null,
                    /*sort=*/ (a,b) -> Long.compare(b.getTime(), a.getTime())
            );
            openResults(player, res, "Most recent");
        });
    }

    public static void searchForPlayerName(final Player player, final String name) {
        if (player.getPlayerShops().isSearchDelayed()) return;
        final String lower = name.toLowerCase();
        searchExecutor.submit(() -> {
            List<ExchangeSearchResult> res = search(
                    /*searching=*/ player,
                    100,
                    r -> r.getSellerName().toLowerCase().contains(lower),
                    (r1,r2) -> {
                        int c1 = r1.getSellerName().toLowerCase().compareTo(lower);
                        int c2 = r2.getSellerName().toLowerCase().compareTo(lower);
                        return Integer.compare(c1, c2);
                    }
            );
            openResults(player, res, name);
        });
    }

    public static void searchForItemName(final Player player, final String itemName) {
        if (player.getPlayerShops().isSearchDelayed()) return;
        final String lower = itemName.toLowerCase();
        searchExecutor.submit(() -> {
            List<ExchangeSearchResult> res = search(
                    /*searching=*/ player,
                    100,
                    r -> ItemDefinition.forId(r.getId()).getName().toLowerCase().contains(lower),
                    (r1,r2) -> ItemDefinition.forId(r1.getId())
                            .getName().compareToIgnoreCase(
                                    ItemDefinition.forId(r2.getId()).getName())
            );
            openResults(player, res, itemName);
        });
    }

    public static void searchForItemId(final Player player, final int itemId) {
        if (player.getPlayerShops().isSearchDelayed()) return;
        searchExecutor.submit(() -> {
            List<ExchangeSearchResult> res = search(
                    /*searching=*/ player,
                    100,
                    r -> r.getId() == itemId,
                    null
            );
            openResults(player, res, ItemDefinition.forId(itemId).getName());
        });
    }

    /** Only populate UI if they’re in the trading-post interface */
    private static void openResults(
            final Player player,
            final List<ExchangeSearchResult> results,
            String header
    ) {
        player.addProcessCallable(p -> {
            int iid = player.getInterfaceId();
            if (iid != PlayerShops.MAIN_INTERFACE_ID &&
                    iid != PlayerShops.SEARCH_RESULTS_INTERFACE_ID) {
                return;
            }
            player.getPlayerShops().openSearchResult(header, results);
        });
    }
}
