package com.ruse.world.content.discord.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized channel mappings for Discord integration.
 * This class contains mappings between RSPS channel IDs and Discord channel names/IDs.
 */
public class ChannelMappings {
    
    // Maps RSPS channel IDs to Discord channel names
    private static final Map<Long, String> channelNameMapping = new HashMap<>();
    
    // Maps Discord channel names to Discord channel IDs
    private static final Map<String, String> channelIdMapping = new HashMap<>();
    
    static {
        // Initialize RSPS channel ID to Discord channel name mapping
        channelNameMapping.put(Channels.COMMANDS_TO_SERVER, "commands_to_server");
        channelNameMapping.put(Channels.CONNECT_DISCORD, "connect_discord");
        channelNameMapping.put(Channels.GLOBAL_CHAT, "global_chat");
        channelNameMapping.put(Channels.TESTING_CHANNEL, "testing_channel");
        channelNameMapping.put(Channels.LOGINS, "logins");
        channelNameMapping.put(Channels.DROPS, "drops");
        channelNameMapping.put(Channels.TRADINGPOST, "tradingpost");
        channelNameMapping.put(Channels.CLANCHAT, "clanchat");
        channelNameMapping.put(Channels.afkLog, "afk_log");
        channelNameMapping.put(Channels.COMMANDS, "commands");
        channelNameMapping.put(Channels.TRADING, "trading");
        channelNameMapping.put(Channels.PUNISHMENTS, "punishments");
        channelNameMapping.put(Channels.STORE, "store");
        channelNameMapping.put(Channels.STAFF_COMMANDS, "staff_commands");
        channelNameMapping.put(Channels.ADMIN_COMMANDS, "admin_commands");
        channelNameMapping.put(Channels.WATCHLIST_LOGINS, "watchlist_logins");
        channelNameMapping.put(Channels.WATCHLIST_COMMANDS, "watchlist_commands");
        channelNameMapping.put(Channels.WATCHLIST_CHAT, "watchlist_chat");
        channelNameMapping.put(Channels.WATCH_LIST_PMS, "watchlist_pms");
        channelNameMapping.put(Channels.WATCHLIST_TRADES, "watchlist_trades");
        channelNameMapping.put(Channels.DAILY_DEALS, "daily_deals");
        channelNameMapping.put(Channels.ACTIVE_EVENTS, "active_events");
        
    //     // Initialize Discord channel name to Discord channel ID mapping
    //     channelIdMapping.put("commands_to_server", "1348381777772937228");
    //     channelIdMapping.put("connect_discord", "1348381777772937228");
    //     channelIdMapping.put("global_chat", "1348381843052957767");
    //     channelIdMapping.put("testing_channel", "1348381843052957767");
    //     channelIdMapping.put("logins", "1363956830614585364");
    //     channelIdMapping.put("drops", "1341906826937241712");
    //     channelIdMapping.put("tradingpost", "1341906873552605224");
    //     channelIdMapping.put("clanchat", "1351822352769486939");
    //     channelIdMapping.put("afk_log", "1356856158857592902");
    //     channelIdMapping.put("commands", "1348381777772937228");
    //     channelIdMapping.put("trading", "1351822625185206313");
    //     channelIdMapping.put("punishments", "1363957147448250428");
    //     channelIdMapping.put("store", "1351822735910764577");
    //     channelIdMapping.put("staff_commands", "1363957366646636594");
    //     channelIdMapping.put("admin_commands", "1363957366646636594");
    //     channelIdMapping.put("watchlist_logins", "1363957435785674902");
    //     channelIdMapping.put("watchlist_commands", "1363957435785674902");
    //     channelIdMapping.put("watchlist_chat", "1363957435785674902");
    //     channelIdMapping.put("watchlist_pms", "1363957435785674902");
    //     channelIdMapping.put("watchlist_trades", "1363957435785674902");
    //     channelIdMapping.put("daily_deals", "1348382365512110262");
    //     channelIdMapping.put("active_events", "1360736183319269406");
    // }
    

    // Initialize Discord channel name to Discord channel ID mapping
    channelIdMapping.put("commands_to_server", "1475675756473483395");
    channelIdMapping.put("connect_discord", "1475675756473483395");
    channelIdMapping.put("global_chat", "1475675730120544368");
    channelIdMapping.put("testing_channel", "1348381843052957767");
    channelIdMapping.put("logins", "1475675685686218894");
    channelIdMapping.put("drops", "1475675696268578990");
    channelIdMapping.put("tradingpost", "1475675717751668827");
    channelIdMapping.put("clanchat", "1475675727910273074");
    channelIdMapping.put("afk_log", "1475675708071215117");
    channelIdMapping.put("commands", "1475675688672690349");
    channelIdMapping.put("trading", "1475675714597421228");
    channelIdMapping.put("punishments", "1475675702392000522");
    channelIdMapping.put("store", "1475675721841119417");
    channelIdMapping.put("staff_commands", "1475675691465834558");
    channelIdMapping.put("admin_commands", "1475675704665440369");
    // channelIdMapping.put("char_files", "1348382647822450710");
    channelIdMapping.put("watchlist_logins", "1475675741889757225");
    channelIdMapping.put("watchlist_commands", "1475675739683557506");
    channelIdMapping.put("watchlist_chat", "1475675737498583221");
    channelIdMapping.put("watchlist_pms", "1475675735229202603");
    channelIdMapping.put("watchlist_trades", "1475675744079184003");
    channelIdMapping.put("daily_deals", "1348382365512110262");
    channelIdMapping.put("active_events", "1475675693928153189");
}

    /**
     * Get the Discord channel name for a given RSPS channel ID
     * @param rspsChannelId The RSPS channel ID
     * @return The Discord channel name, or null if not found
     */
    public static String getChannelName(Long rspsChannelId) {
        return channelNameMapping.get(rspsChannelId);
    }
    
    /**
     * Get the Discord channel ID for a given Discord channel name
     * @param channelName The Discord channel name
     * @return The Discord channel ID, or null if not found
     */
    public static String getChannelId(String channelName) {
        return channelIdMapping.get(channelName);
    }
    
    /**
     * Add a new channel mapping
     * @param rspsChannelId The RSPS channel ID
     * @param channelName The Discord channel name
     * @param discordChannelId The Discord channel ID
     */
    public static void addMapping(Long rspsChannelId, String channelName, String discordChannelId) {
        channelNameMapping.put(rspsChannelId, channelName);
        channelIdMapping.put(channelName, discordChannelId);
    }
} 