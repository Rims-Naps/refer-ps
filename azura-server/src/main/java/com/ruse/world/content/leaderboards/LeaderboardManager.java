package com.ruse.world.content.leaderboards;


import com.ruse.model.PlayerRights;
import com.ruse.util.Misc;
import com.ruse.world.content.KillsTracker;
import com.ruse.world.entity.impl.player.Player;

import java.util.*;

public class LeaderboardManager {

    private final Player player;

    private LeaderboardData.LeaderboardType leaderboardType;
    private LeaderboardData leaderboardViewing;

    public LeaderboardManager(Player player) {
        this.player = player;
    }


    /**
     * Handles button functions
     *
     * @param buttonId
     * @return
     */
    public boolean handleButton(final int buttonId) {
        if (buttonId >= 120005 && buttonId <= 120007) {
            int index = (buttonId - 120005) / 2;
            if (index < LeaderboardData.LeaderboardType.values().length) {
                leaderboardType = LeaderboardData.LeaderboardType.values()[index];
                leaderboardViewing = LeaderboardData.getItems(leaderboardType).get(0);
                openInterface();
            }
            return true;
        } else if (buttonId >= 120151 && buttonId <= 120190) {
            int index = buttonId - 120151;
            if (index < viewingBoards.size()) {
                leaderboardViewing = viewingBoards.get(index);
                setupList();
                loadInfo();
            }
            return true;
        }
        return false;
    }

    /**
     * Initializes the interface
     */
    public void openInterface() {
        setupList();
        loadInfo();
        player.getPacketSender().sendInterface(120000);
    }


    /**
     * Sets up the list of titles on the interface
     */
    private ArrayList<LeaderboardData> viewingBoards;

    private void setupList() {
        if (leaderboardViewing == null)
            leaderboardViewing = LeaderboardData.TOTAL_NPC_KILLS;
        if (leaderboardType == null)
            leaderboardType = LeaderboardData.LeaderboardType.MAIN;

        viewingBoards = LeaderboardData.getItems(leaderboardType);

        int length = viewingBoards.size();
        int id = 120151;

        int scroll = (length * 18);
        if (scroll <= 195)
            scroll = 195;

        for (int i = 0; i < (Math.max(length, 11)); i++) {
            if (viewingBoards.size() > i) {
                player.getPacketSender().sendString(id++, (leaderboardViewing == viewingBoards.get(i) ? "@whi@" : "")
                        + viewingBoards.get(i).getName().replace("##", ""));
            } else {
                player.getPacketSender().sendString(id++, "");
            }
        }

        player.getPacketSender().sendConfig(4511, leaderboardType.ordinal());
        player.getPacketSender().setScrollBar(120100, scroll);
    }


    private void loadInfo() {
        int index = 0;

        if (Leaderboard.DATA.containsKey(leaderboardViewing)) {

            if (Leaderboard.DATA.get(leaderboardViewing).containsKey(player.getUsername())) {
                long entry = Leaderboard.DATA.get(leaderboardViewing).get(player.getUsername());
                if (leaderboardViewing == LeaderboardData.AMOUNT_DONATED_CLAIMED)
                    player.getPacketSender().sendString(120014, "@gre@x: $" + Misc.insertCommasToNumber(entry));
                else if (leaderboardViewing == LeaderboardData.PLAYTIME)
                    player.getPacketSender().sendString(120014, "@gre@x: " + Misc.getHoursPlayed(entry));
                else
                    player.getPacketSender().sendString(120014, "@gre@x: " + Misc.insertCommasToNumber(entry));
            }else{
                player.getPacketSender().sendString(120014, "@gre@x: " + 0);
            }


            List<Map.Entry<String, Long>> result = sortEntries(Leaderboard.DATA.get(leaderboardViewing));
            for (Iterator<Map.Entry<String, Long>> iterator = result.iterator(); iterator.hasNext(); ) {
                Map.Entry<String, Long> entry = iterator.next();
                String user = entry.getKey();
                Long entryValue = entry.getValue();
                if (index >= 50) {
                    break;
                }
                player.getPacketSender().sendString(120551 + (index * 3), "#" + (index + 1));
                if (leaderboardViewing == LeaderboardData.AMOUNT_DONATED_CLAIMED)
                    player.getPacketSender().sendString(120552 + (index * 3), "$" + Misc.insertCommasToNumber(entryValue));
                else if (leaderboardViewing == LeaderboardData.PLAYTIME)
                    player.getPacketSender().sendString(120552 + (index * 3), "" + Misc.getHoursPlayed(entryValue));
                else
                    player.getPacketSender().sendString(120552 + (index * 3), "" + Misc.insertCommasToNumber(entryValue));
                player.getPacketSender().sendString(120553 + (index * 3), user +  "");
                index++;

                iterator.remove();
            }
        }
        if (index < 50) {
            int remaining = 50 - index;
            for (int i = 0; i < remaining; i++) {
                player.getPacketSender().sendString(120551 + (index * 3), "");
                player.getPacketSender().sendString(120552 + (index * 3), "");
                player.getPacketSender().sendString(120553 + (index * 3), "");
                index++;
            }
        }
    }


public void updateData() {
    // Disabled for development
    return;
}    


//     public void updateData() {
//         if (player.getRights().isManagement() || player.getRights() == PlayerRights.YOUTUBER)
//             return;



//         for (LeaderboardData data : LeaderboardData.values()) {
//             if (data != null && data.getNpcId() > 0) {
//                 if (KillsTracker.getTotalKillsForNpc(data.getNpcId(), player) > 0) {
//                     Leaderboard.DATA.get(LeaderboardData.forNpcId(data.getNpcId())).put(player.getUsername(), (long) KillsTracker.getTotalKillsForNpc(data.getNpcId(), player));
//                 }
//             }
//         }

//         long total = player.getTotalPlayTime();
//         if (total >= 1_000_000_000_000L)
//             total = 9_000_000_000L;

//         Leaderboard.DATA.get(LeaderboardData.TOTAL_NPC_KILLS).put(player.getUsername(), (long) KillsTracker.getTotalKills(player));
//         Leaderboard.DATA.get(LeaderboardData.EASY_SLAYER_TASKS).put(player.getUsername(), (long) player.getEasyTasksDone());
//         Leaderboard.DATA.get(LeaderboardData.MED_SLAYER_TASKS).put(player.getUsername(), (long) player.getMediumTasksDone());
//         Leaderboard.DATA.get(LeaderboardData.HARD_SLAYER_TASKS).put(player.getUsername(), (long) player.getEliteTasksDone());
//         Leaderboard.DATA.get(LeaderboardData.BEAST_HUNTER_TASKS).put(player.getUsername(), (long) player.getBeastTasksDone());
//         Leaderboard.DATA.get(LeaderboardData.PLAYTIME).put(player.getUsername(), total);
//         Leaderboard.DATA.get(LeaderboardData.AMOUNT_DONATED_CLAIMED).put(player.getUsername(), (long) player.getAmountDonated());
//         Leaderboard.DATA.get(LeaderboardData.COLLECTION_LOGS_COMPLETED).put(player.getUsername(), (long) player.getCollectionLogManager().totalCompleted());
//         Leaderboard.DATA.get(LeaderboardData.KEYS_OPENED).put(player.getUsername(), (long) player.getTotalKeysOpened());
//         Leaderboard.DATA.get(LeaderboardData.BOXES_OPENED).put(player.getUsername(), (long) player.getTotalBoxesOpened());
//         Leaderboard.DATA.get(LeaderboardData.SOULBANE).put(player.getUsername(), (long) player.getTotalSoulbaneRuns());
//         Leaderboard.DATA.get(LeaderboardData.TOWER).put(player.getUsername(), (long) player.getTotal_tower_completions());
//         Leaderboard.DATA.get(LeaderboardData.CORRUPT_RAIDS).put(player.getUsername(), (long) player.getCorruptCompletions());
//         Leaderboard.DATA.get(LeaderboardData.CORRUPT_RAIDS_MED).put(player.getUsername(), (long) player.getCorruptMediumCompletions());
//         Leaderboard.DATA.get(LeaderboardData.CORRUPT_RAIDS_HARD).put(player.getUsername(), (long) player.getCorruptHardCompletions());
//         Leaderboard.DATA.get(LeaderboardData.SPECTRAL_RAIDS).put(player.getUsername(), (long) player.getVoidCompletions());
//         Leaderboard.DATA.get(LeaderboardData.SPECTRAL_RAIDS_MED).put(player.getUsername(), (long) player.getVoidMediumCompletions());
//         Leaderboard.DATA.get(LeaderboardData.SPECTRAL_RAIDS_HARD).put(player.getUsername(), (long) player.getVoidHardCompletions());
//         Leaderboard.DATA.get(LeaderboardData.CIRCLE_OF_ELEMENTS).put(player.getUsername(), (long) player.getCOECompletions());
//     }

    static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortEntries(Map<K, V> map) {

        List<Map.Entry<K, V>> sortedEntries = new ArrayList<Map.Entry<K, V>>(map.entrySet());

        Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;

    }

}
