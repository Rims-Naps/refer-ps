package com.ruse.model.definitions;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ruse.model.*;
import com.ruse.model.Locations.Location;
import com.ruse.model.container.impl.Bank;
import com.ruse.util.JsonLoader;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.DropLog.DropLogEntry;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.combat.CombatBuilder.CombatDamageCache;
import com.ruse.world.content.combat.CombatFactory;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.content.minigames.impl.TreasureHunter;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.impl.prayer.BonesData;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.ruse.model.container.impl.Equipment.*;


/**
 * Controls the npc drops
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>, Gabbe &
 * Samy
 */
public class NPCDrops {

    public static final HashSet<Integer> multiKillNpcs = new HashSet<>(Arrays.asList(
            TreasureHunter.NPC_1, TreasureHunter.NPC_2,
            TreasureHunter.NPC_3, TreasureHunter.NPC_4));
    private static final List<NpcDropItem> constantDrops = new ArrayList<>();
    private static final List<NpcDropItem> potentialDrops = new ArrayList<>();
    private static final List<NpcDropItem> finalDropList = new ArrayList<>();

    private static Map<Integer, NPCDrops> dropControllers = new HashMap<>();

    private int[] npcIds;

    private NpcDropItem[] drops;

    public static JsonLoader parseDrops() {

        return new JsonLoader("./data/def/json/drops.json") {

            @Override
            public void load(JsonObject reader, Gson builder) {
                int[] npcIds = builder.fromJson(reader.get("npcIds"), int[].class);
                NpcDropItem[] drops = builder.fromJson(reader.get("drops"), NpcDropItem[].class);

                NPCDrops d = new NPCDrops();
                d.npcIds = npcIds;
                d.drops = drops;
                for (int id : npcIds) {
                    dropControllers.put(id, d);
                }
            }
        };
    }

    public static NPCDrops forId(int id) {
        return dropControllers.get(id);
    }

    public static Map<Integer, NPCDrops> getDrops() {
        return dropControllers;
    }

    public static void getDropTable(Player p, int npcId) {
        NPCDrops drops = NPCDrops.forId(npcId);
        if (drops == null) {
            p.getPacketSender().sendMessage("No drops were found. [Error 194510]");
            return;
        }
        for (int i = 0; i < drops.getDropList().length; i++) {
            if (drops.getDropList()[i].getItem().getId() <= 0
                    || drops.getDropList()[i].getItem().getId() > ItemDefinition.getMaxAmountOfItems()
                    || drops.getDropList()[i].getItem().getAmount() <= 0) {
                continue;
            }

            final int dropChance = drops.getDropList()[i].getChance();
            p.getPacketSender().sendMessage(drops.getDropList()[i].getItem().getAmount() + "x "
                    + ItemDefinition.forId(drops.getDropList()[i].getItem().getId()).getName() + " at a chance of 1/"
                    + dropChance + ".");
        }
    }

    public static void dropItemsMultiKill(NPC npc, Map<String, CombatDamageCache> damageMap) {
        //System.out.println("Multi dropping for " + npc.getId() + " " + npc.getDefinition().getName());
        if (damageMap.isEmpty()) {
            //System.out.println("Damage map size: "+damageMap.size());
            StackTraceElement[] st = Thread.currentThread().getStackTrace();
            if (st.length != 0) {
                for (StackTraceElement el : st) {
                   // System.out.println(el);
                }
            }
            return;
        }

        Map<Player, Integer> killers = new HashMap<>();

        //System.out.println("Combat damage cache size: "+damageMap.size());
        for (Entry<String, CombatDamageCache> entry : damageMap.entrySet()) {
            if (entry == null) {
                continue;
            }

            long timeout = entry.getValue().getStopwatch().elapsed();
            if (timeout > CombatFactory.DAMAGE_CACHE_TIMEOUT) {
                continue;
            }

            String username = entry.getKey();
            //System.out.println("Player username: "+username);

            Player player = World.getPlayerByName(username);
            if (player == null || player.getConstitution() <= 0 || !player.isRegistered()) {
               // System.out.println("Player not valid: "+username);
               // System.out.println("Player null: "+(player== null));
                //System.out.println("Player constitution: "+player.getConstitution());
                System.out.println("Player registered: "+player.isRegistered());
                continue;
            }
            System.out.println("Player valid");
            //System.out.println();


            killers.put(player, entry.getValue().getDamage());

        }


        List<Entry<Player, Integer>> result = sortEntries(killers);
        int count = 0;

        for (Entry<Player, Integer> entry : result) {

            Player killer = entry.getKey();
            int damage = entry.getValue();


            //    System.out.println("Multi dropping for " + killer.getUsername() + " dealt " + damage + " damage");
            handleDrops(killer, npc);

                   /* if (npc.getId() >= TreasureHunter.NPC_1 && npc.getId() <= TreasureHunter.NPC_4 && npc.getPosition().getRegionId() == 8014) {
                        TreasureHunter.addKey(killer, npc);
                    }*/


            //if (++count >= 10) {
            //    break;
            // }

        }
    }

    static <K, V extends Comparable<? super V>> List<Entry<K, V>> sortEntries(Map<K, V> map) {

        List<Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());

        Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;

    }



    public static void handleDrops(Player player, NPC npc) {
        handleDrops(player, npc, 1);
    }

    public static void handleDrops(Player player, NPC npc, double divider) {
        int npcId = npc.getId();

        NPCDrops dropt = getDrops().get(npcId);
        if (dropt == null) {
            return;
        }
        NpcDropItem[] drops = dropt.getDropList();

        if (drops == null)
            return;

        final Position npcPos = npc.getPosition().copy();

        HashMap<Double, ArrayList<NpcDropItem>> dropRates = new HashMap<>();

        for (NpcDropItem drop : drops) {
            if (drop == null)
                continue;
            double divisor = drop.getChance();
            if (drop.getChance() == 1 && drop.getCount()[0] != -1) {
                constantDrops.add(drop);
            } else {
                    if (!dropRates.containsKey(divisor)) {
                        ArrayList<NpcDropItem> items = new ArrayList<>();
                        items.add(drop);
                        dropRates.put(divisor, items);
                    } else {
                        dropRates.get(divisor).add(drop);
                    }
                }
            }

        for (double dropRate : dropRates.keySet()) {
            double roll_chance = new SecureRandom().nextDouble() / divider;

            double divide = (CustomDropUtils.drBonus(player, npc.getId()) > 0 ? (CustomDropUtils.drBonus(player, npc.getId()) / 100.0) + 1 : 1);

            double required = 1 / dropRate;
            if (roll_chance / divide <= required) {
                potentialDrops.add(dropRates.get(dropRate).get(Misc.getRandom(dropRates.get(dropRate).size() - 1)));
            }
        }
        if (!constantDrops.isEmpty()) {
            finalDropList.addAll(constantDrops);
        }
        if (!potentialDrops.isEmpty()) {
            finalDropList.add(potentialDrops.get(Misc.randomMinusOne(potentialDrops.size())));
        }
        if (!finalDropList.isEmpty()) {
            sendDrop(player, npc);
        }
    }

    public static void sendDrop(Player player, NPC npc) {

        List<NpcDropItem> list = finalDropList;
        if (list == null) {
            System.err.println("List of UniversalDrops is null for " + npc);
            return;
        }
        if (player == null){
            return;
        }
        Position pos = npc.getPosition().copy();

        final boolean goGlobal = player.getPosition().getZ() >= 0 && player.getPosition().getZ() < 4;

        for (NpcDropItem drop : list) {
            int[] count = drop.getCount();
            int amount = count[0];

            if (count.length > 1) {
                if (count[0] == count[1])
                    amount = 1;
                else if (count[1] - count[0] > 0)
                    amount = Misc.getRandom(count[1] - count[0]) + count[0];
            }

            List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();
            if (activePerks.contains(ServerPerks.Perk.DBL_DROPS)) {
                amount *= 2;
            }

            Item item = new Item(drop.getId(), amount);
            int itemId = item.getId();



            boolean ccAnnounce = false;
            if (Location.inMulti(player)) {
                if (player.getCurrentClanChat() != null && player.getCurrentClanChat().getLootShare()) {
                    CopyOnWriteArrayList<Player> playerList = new CopyOnWriteArrayList<>();
                    for (Player member : player.getCurrentClanChat().getMembers()) {
                        if (member != null) {
                            if (member.getPosition().isWithinDistance(player.getPosition())) {
                                playerList.add(member);
                            }
                        }
                    }
                    if (!playerList.isEmpty()) {
                        player = playerList.get(Misc.getRandom(playerList.size() - 1));
                        if (player == null || player.getCurrentClanChat() == null
                                || player.getCurrentClanChat() != player.getCurrentClanChat()) {
                            player = player;
                        }
                        ccAnnounce = true;
                    }
                }
            }

            int spectralFix = player.getLocation() == Location.SPECTRAL_DUNGEON ? 500 : 50;

            if (drop.isAnnounce() || drop.getChance() >= spectralFix) {
                String itemName = item.getDefinition().getName();
                String itemMessage = "x" + amount + " " + itemName;
                String npcName = Misc.formatText(npc.getDefinition().getName());
                String kills = Misc.insertCommasToNumber(String.valueOf(KillsTracker.getTotalKillsForNpc(npc.getId(), player)));
                String message = "<col=AF70C3><shad=0>[DROP]  @red@" + player.getUsername() + "<col=AF70C3> received @red@" + itemMessage + "<col=AF70C3> from @red@" + npcName + " <col=AF70C3>(" + kills + " <col=AF70C3>Kills)";

                if (!itemName.contains("Elemental Key") && (!itemName.contains("essence") && (!itemName.contains("Hunter's Purse") && (!itemName.contains("Easter Fragments") && (!itemName.contains("Favor Casket") && (!itemName.contains("Christmas Key(1)") && (!itemName.contains("Task Skip") && (!itemName.contains("Christmas Key(2)") && (!itemName.contains("Coin Casket") && (!itemName.contains("Slayer Essence")  && (!itemName.contains("coins") && (!itemName.contains("Cinderfall"))))))))))))) {
                   // DiscordManager.sendMessage("[DROP] " + player.getUsername() + " received " + itemMessage + " from " + npcName + " (" + kills + " Kills)", Channels.DROPS);
                    World.sendDropMessage(message);

                    if (ccAnnounce) {
                        ClanChatManager.sendMessage(player.getCurrentClanChat(), "<col=16777215>[<col=255>Lootshare<col=16777215>]<col=3300CC> " + player.getUsername() + " received " + itemMessage + " from " + npcName + "!");
                    }

                    PlayerLogs.log(player.getUsername(), "" + player.getUsername() + " received " + itemMessage + " from " + npcName + "!");
                    PlayerLogs.logNpcDrops(player.getUsername(), "Player received drop: " + itemMessage + ", id: " + itemId + ", amount: " + amount + ", from: " + npcName);

                }
            }


            if (player == null){
                return;
            }
            if (item.getId() != 19062 && item.getId() != 11054 && item.getId() != 11056 && item.getId() != 11052 && item.getId() != 3576 ) {
                if (player.getMembershipTier().isHighTierMember()) {
                    if (player.getDissolving().isDissolvable(item.getId()) && player.getMembershipTier().isAuto_loot_salvaging() && player.getAutoDissolve()) {
                        player.getDissolving().handle(item.getId(), item.getAmount(), false);
                    } else {
                        if (player.getAutoBank() && item.getId() != 6754) {
                            int tab = Bank.getTabForItem(player, item);
                            player.getBank(tab).add(item);
                        } else {
                            player.getInventory().add(item);
                        }
                    }
                } else if (player.getInventory().canHold(item)) {
                    player.getInventory().add(item);
                } else {
                    player.getBank(player.getCurrentBankTab()).add(item);
                    player.sendMessage("Your inventory is full, so your drop went to your bank.");
                }
               //
                  double dropChance = 0.0025;
                  //double dropChance = 0.75;
                String itemName = item.getDefinition().getName();
                String itemMessage = "x" + amount + " " + itemName;
                String npcName = Misc.formatText(npc.getDefinition().getName());
                for (Player players : World.getPlayers()) {
                    if (players != null && players.getEquipment().contains(23169) && (Math.random() < dropChance) && drop.getChance() >= spectralFix) {
                        players.getBank(player.getCurrentBankTab()).add(item);
                        players.sendMessage( "Your pot of greed activated and duplicated a " + itemMessage + " from " + npcName + "!");
                    }
                }

                if (drop.getChance() >= 800){
                    player.getBank(player.getCurrentBankTab()).add(2617, 1);
                    player.msgFancyPurp("You have received a Skill point while on your Journey.");
                }

                player.getCollectionLogManager().addDrop(npc, item);
             //   player.getBingoHandler().handleBingoRareDrop(drop.getItem(), drop.getChance(), npc);
                DropLog.submit(player, new DropLogEntry(itemId, item.getAmount(), drop.isAnnounce()));
            }

            //GOLD CLUSTER HANDLE - TYLER
            if (player.getEquipment().get(WINGS).getId() == 433) {
                if (Misc.getRandom(125) == 1) {
                    if (player.getInventory().canHold(item)) {
                        player.sendMessage("<shad=0>@bla@You feel riches rush through your veins!");
                        player.performGraphic(new Graphic(524));
                        player.getInventory().add(item);
                    } else {
                        player.depositItemBank(item);
                        player.sendMessage("<shad=0>@bla@You feel riches rush through your veins!");
                        player.performGraphic(new Graphic(524));
                    }
                }
            }

            if (itemId == 19062) { // Monster Essence
                DropLog.submit(player, new DropLogEntry(itemId, item.getAmount(), drop.isAnnounce()));
                if (player.getInventory().contains(18015, 1)) {
                    player.setMonsteressence(player.getMonsteressence() + item.getAmount());
                    if (player.pouchMessagesEnabled) {
                        player.sendMessage(item.getAmount() + "X Monster Essence has been added to your pouch!");
                    }
                } else {
                    if (player.getInventory().canHold(item)) {
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage(item.getAmount() + "X Monster Essence has been added to your Inventory!");
                        }
                        player.getInventory().add(item);
                    } else {
                        player.depositItemBank(item);
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage("Your inventory is full, so your Monster Essence went to your bank.");
                        }
                    }
                }
            } else if (itemId == 11054) { // Fire Essence
                DropLog.submit(player, new DropLogEntry(itemId, item.getAmount(), drop.isAnnounce()));
                if (player.getInventory().contains(18015, 1)) {
                    player.setFireessence(player.getFireessence() + item.getAmount());
                    if (player.pouchMessagesEnabled) {
                        player.sendMessage(item.getAmount() + "X Fire Essence has been added to your pouch!");
                    }
                } else {
                    if (player.getInventory().canHold(item)) {
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage(item.getAmount() + "X Fire Essence has been added to your Inventory!");
                        }
                        player.getInventory().add(item);
                    } else {
                        player.depositItemBank(item);
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage("Your inventory is full, so your Fire Essence went to your bank.");
                        }
                    }
                }
            } else if (itemId == 11056) { // Water Essence
                DropLog.submit(player, new DropLogEntry(itemId, item.getAmount(), drop.isAnnounce()));
                if (player.getInventory().contains(18015, 1)) {
                    player.setWateressence(player.getWateressence() + item.getAmount());
                    if (player.pouchMessagesEnabled) {
                        player.sendMessage(item.getAmount() + "X Water Essence has been added to your pouch!");
                    }
                } else {
                    if (player.getInventory().canHold(item)) {
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage(item.getAmount() + "X Water Essence has been added to your Inventory!");
                        }
                        player.getInventory().add(item);
                    } else {
                        player.depositItemBank(item);
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage("Your inventory is full, so your Water Essence went to your bank.");
                        }
                    }
                }
            } else if (itemId == 11052) { // Earth Essence
                DropLog.submit(player, new DropLogEntry(itemId, item.getAmount(), drop.isAnnounce()));
                if (player.getInventory().contains(18015, 1)) {
                    player.setEarthessence(player.getEarthessence() + item.getAmount());
                    if (player.pouchMessagesEnabled) {
                        player.sendMessage(item.getAmount() + "X Earth Essence has been added to your pouch!");
                    }
                } else {
                    if (player.getInventory().canHold(item)) {
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage(item.getAmount() + "X Earth Essence has been added to your Inventory!");
                        }
                        player.getInventory().add(item);
                    } else {
                        player.depositItemBank(item);
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage("Your inventory is full, so your Earth Essence went to your bank.");
                        }
                    }
                }
            } else if (itemId == 3576) { // Slayer Essence
                DropLog.submit(player, new DropLogEntry(itemId, item.getAmount(), drop.isAnnounce()));
                if (player.getInventory().contains(18015, 1)) {
                    player.setSlayeressence(player.getSlayeressence() + item.getAmount());
                    if (player.pouchMessagesEnabled) {
                        player.sendMessage(item.getAmount() + "X Slayer Essence has been added to your pouch!");
                    }
                } else {
                    if (player.getInventory().canHold(item)) {
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage(item.getAmount() + "X Slayer Essence has been added to your Inventory!");
                        }
                        player.getInventory().add(item);
                    } else {
                        player.depositItemBank(item);
                        if (player.pouchMessagesEnabled) {
                            player.sendMessage("Your inventory is full, so your Slayer Essence went to your bank.");
                        }
                    }
                }
            }

        }

        finalDropList.clear();
        constantDrops.clear();
        potentialDrops.clear();
    }

    public NpcDropItem[] getDropList() {
        return drops;
    }

    public int[] getNpcIds() {
        return npcIds;
    }

    public static class NpcDropItem {

        @Getter
        private final int id;

        @Getter
        private final int[] count;

        @Getter
        @Setter
        private int chance;

        @Getter
        @Setter
        private boolean announce;

        public NpcDropItem(int id, int[] count, int chance) {
            this.id = id;
            this.count = count;
            this.chance = chance;
            this.announce = false;
        }

        public NpcDropItem(int id, int[] count, int chance, boolean announce) {
            this.id = id;
            this.count = count;
            this.chance = chance;
            this.announce = announce;
        }

        public Item getItem() {
            int amount = 0;
            for (int i = 0; i < count.length; i++)
                amount += count[i];
            if (amount > count[0])
                amount = count[0] + Misc.getRandom(count[1]);
            return new Item(id, amount);
        }

        public Item getMaxAmount() {
            int amount = 0;
            for (int i = 0; i < count.length; i++)
                amount += count[i];
            if (amount > count[0])
                amount = count[0] + Misc.getRandom(count[1]);
            return new Item(id, amount);
        }
    }
}
