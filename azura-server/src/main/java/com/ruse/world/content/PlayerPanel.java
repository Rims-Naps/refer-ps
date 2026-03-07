package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.donation.ServerCampaignHandler;
import com.ruse.model.Locations;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.net.packet.impl.CommandPacketListener;
import com.ruse.util.Misc;
import com.ruse.world.World;
//import com.ruse.world.content.christmas.HolidayManager;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.entity.impl.player.Player;

import java.time.Duration;
import java.time.LocalDateTime;

public class PlayerPanel {

    public static void refreshCurrentTab(Player player) {
        refreshPanel(player);
    }

    public static void refreshPanel(Player player) {
        String usernameDisplay = "";
        String updatePerks = "";

   /*     LocalDateTime now = LocalDateTime.now();
        Duration durationUntilReset = Duration.between(now.minusDays(1), player.getForestResetTime());
        long hoursUntilReset = durationUntilReset.toHours();*/

        if (player.isEEarth()) {
            usernameDisplay = "@gre@(E)";
        } else if (player.isEFire()) {
            usernameDisplay = "@red@(F)";
        } else if (player.isEWater()) {
            usernameDisplay = "@cya@(W)";
        }

        if (PerkManager.currentPerk != null) {
            updatePerks = PerkManager.currentPerk.getName();
        }

        int interfaceID = 111201;
        int players = (int) (World.getPlayers().size() * 1.4);
        String[] Messages = new String[]{

                "Athens",
                "Players: @whi@" + ((Math.round(players))),
                "Active Perk@whi@: " + (updatePerks.isEmpty() ? "Vote for Perk" : updatePerks),
                "Votes:  @whi@" + CommandPacketListener.voteCount + "@whi@/20",
                "Dono Boss: @whi@" + ServerCampaignHandler.getCurrentDonoBossVal() + "@whi@/500",
                "Slayer: @whi@" + GameSettings.globalslayertasks + "@gre@/100",
                "Rift: @whi@ " + GameSettings.DISSOLVE_AMOUNT + "@whi@/2k",
                "Demon: @whi@" + GameSettings.KINGS_BONES + "@whi@/5",
                "Champion: @whi@" + GameSettings.EMERALD_CHAMP_AMOUNT + "@whi@/5000",
                "Text Here@whi@",
                "Text Here@whi@",
                "Text Here@whi@",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "Useful Links",
                "                      @whi@Wiki",
                "                      @whi@Vote",
                "                     @whi@Store",
                "                   @whi@Discord",
                "",
                "",
                "",

        };

        for (int i = 0; i < Messages.length; i++) {
            player.getPacketSender().sendString(interfaceID++, Messages[i]);
        }

        String forestTimer = "";
        try{
            LocalDateTime resetTime = player.getForestResetTime();
            if(resetTime == null){
                forestTimer = "0 hour(s)";
            } else {
                Duration duration = Duration.between(LocalDateTime.now(), resetTime);
                forestTimer = duration.toHours() + " hour(s)";
            }
        } catch (Exception e){
            e.printStackTrace();
        }


        interfaceID = 111401;
        Messages = new String[]{"My Account",
                "Playtime: @whi@" + Misc.getHoursPlayed((player.getTotalPlayTime() + player.getRecordedLogin().elapsed())),
                "Username: @whi@" + player.getUsername() + " " + usernameDisplay,
                "Membership: @whi@" + Misc.capitalizeString(player.getMembershipTier().getName()),
                "Rank: @whi@" + Misc.formatText(player.getRights().toString().toLowerCase()),
                "Game Mode: @whi@" + Misc.capitalizeString(player.getGameMode().toString().toLowerCase().replace("_", " ")),
                "Difficulty: @whi@" + Misc.capitalizeString(player.getXpmode().toString().toLowerCase().replace("_", " ")),
                "Path: @whi@" + Misc.capitalizeString(player.getBoostMode().toString().toLowerCase().replace("_", " ")),
                "Droprate : @whi@" + CustomDropUtils.drBonus(player, player.getSlayer().getSlayerTask().getNpcId()) + "%",
                "Critical : @whi@" + CritUtils.criticalbonus(player) + "%",
                "Stats",
                "Total Kills: @whi@" + player.getPointsHandler().getNPCKILLCount(),
                "Vote Points: @whi@" + player.getPointsHandler().getVotingPoints(),
                "Dono Points: @whi@" + player.getPointsHandler().getDonatorPoints(),
                "Bonds Value: @whi@$" + player.getAmountDonated(),
                "Store Donated: @whi@$" + player.getTotalDonatedThroughStore(),
                "Forest Timer: @yel@" + forestTimer,
                "Slayer Info",
                "Master: @whi@" + Misc.formatText(player.getSlayer().getSlayerMaster().toString().toLowerCase().replaceAll("_", " ")),
                (player.getSlayer().getSlayerTask() == SlayerTasks.NO_TASK ? "Current Task: @yel@" + player.getSlayer().getSlayerTask().getName() : "Current Task: @yel@" + player.getSlayer().getSlayerTask().getName() + " "),
                "Task Amount: @whi@" + player.getSlayer().getAmountToSlay(),
                "Easy Streak: @whi@" + player.getSlayer().getTaskStreak(),
                "Med Streak: @whi@" + player.getSlayer().getTaskStreakBeastI(),
                "Elite Streak: @whi@" + player.getSlayer().getTaskStreakBeastX(),
                "Corrupt Streak: @whi@" + player.getSlayer().getDragonstreak(),

                "Beast Streak: @whi@" + player.getSlayer().getGodstreak(),
                (player.getSlayer().getDuoPartner() != null ? "Duo Partner: @whi@" + player.getSlayer().getDuoPartner() : "Duo Partner: @whi@N/A"),

        };

        for (int i = 0; i < Messages.length; i++) {
            player.getPacketSender().sendString(interfaceID++, Messages[i]);
        }
        interfaceID = 111601;

    }

}
