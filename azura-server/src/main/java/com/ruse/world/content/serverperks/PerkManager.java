package com.ruse.world.content.serverperks;

import com.ruse.GameSettings;
import com.ruse.util.TimeUtils;
import com.ruse.world.World;
import com.ruse.world.content.holidays.HolidayManager;
import com.ruse.world.content.discord.handler.DiscordManager;
import com.ruse.world.content.discord.constants.Channels;
import com.ruse.world.entity.impl.player.Player;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerkManager {
    private static List<Perk> perks;
    private static List<Perk> currentPerks;
    private static List<String> ipsVoted;

    private static LocalDateTime startTime = LocalDateTime.now();
    private static LocalDateTime voteTime = LocalDateTime.now();

    private static boolean perkIsActive;

    public static Perk currentPerk = null;

    public PerkManager() {
        this.perks = new ArrayList<>();
        this.currentPerks = new ArrayList<>();
        this.ipsVoted = new ArrayList<>();
        addPerks();
    }

    public static void init() {
        perks = new ArrayList<>();
        currentPerks = new ArrayList<>();
        ipsVoted = new ArrayList<>();
        addPerks();
        chooseRandomPerks();
        perkIsActive = false;
    }

    public static void addPerk(Perk perk) {
        perks.add(perk);
    }

    public static void addPerks() {
        Object[][] information = {
            {"Critical", "Increase critical\\nhit chance by 10%", 5731},
            {"Necro", "Earn double\\nnecromancy points", 5732},
            {"Droprate", "Gain a 5% bonus to\\ndrop rates", 5733},
            {"Experience", "Earn 25% more experience", 5734},
            {"Slayer", "Earn 20% more\\nslayer essence", 5735},
            {"Damage", "Deal 20% more damage", 5736},
            {"Skilling", "Gain a 50% bonus\\nto skilling", 5737},
            {"Vitality", "2x Vote rewards\\nBonds for donating", 5738},
            {"Frenzy", "1 in 25 chance to\\ndouble kills", 5739},
            {"Raids", "2x Raids keys", 5740}
        };

        for (Object[] info : information) {
            String name = (String) info[0];
            String description = (String) info[1];
            int spriteId = (int) info[2];

            Perk perk = new Perk(name, description, spriteId);
            addPerk(perk);
        }
    }

    static int tick = 0;

    public static void process_all() {
        tick++;
        if (tick != 10)
            return;
        tick = 0;
        process();
        process_voting();

        if (!perkIsActive) {
            displayPerksToPlayers();
            displayVotesToPlayers();
        }

        if (currentPerk != null && perkIsActive) {
            LocalDateTime now = LocalDateTime.now();
            long remainingMillis = Duration.between(now, startTime.plusHours(1)).toMillis();

            String timeString;
            if (remainingMillis <= 0) {
                timeString = "1 Votes Minimum"; // Replace with the desired message for elapsed time
            } else {
                timeString = TimeUtils.getTimeRemainingDHM(now, startTime.plusHours(1));
            }

            World.getPlayers().forEach(p ->
                    p.getPacketSender().sendString(19903, currentPerk.getName())
                            .sendString(19904, currentPerk.getDescription())
                            .sendSpriteChange(19905, currentPerk.getSpriteId())
                            .sendString(19907, timeString)
                            .sendTabInterface(GameSettings.SERVER_TAB, 19900)
            );
        }
    }


    public static void process_meter() {
        World.getPlayers().forEach(p ->
            p.getPacketSender().sendCustomProgressBar(20113, (int) HolidayManager.getMeter(), 100).sendCustomProgressBar(19909, (int) HolidayManager.getMeter(), 100)
        );
    }

    public static void process() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (startTime.plusHours(1).isBefore(currentTime) || startTime.plusHours(1).isEqual(currentTime) || startTime == null) {
            if (!perkIsActive)
                return;
            init();
            voteTime = currentTime;
            World.sendMessage("@red@<shad=0>[PERKS]Voting has started for the next server perk!");

            String message = "[PERKS]Voting has started for the next server perk!";

            for (Player player : World.getPlayers()) {
                if (player == null) {
                    continue;
                }
                player.getPacketSender().sendBroadCastMessage(message, 50);
            }

        }
    }
    public static void process_voting() {
        LocalDateTime currentTime = LocalDateTime.now();
        if (ipsVoted.isEmpty()){
            return;
        }
        if (voteTime.plusMinutes(3).isBefore(currentTime) || voteTime.plusMinutes(3).isEqual(currentTime)) {
            pickWinningPerk();
        }
    }

    public static void chooseRandomPerks() {
        Random random = new Random();
        int numberOfPerksToShow = 3;

        while (currentPerks.size() < numberOfPerksToShow) {
            int randomIndex = random.nextInt(perks.size());
            Perk selectedPerk = perks.get(randomIndex);

            if (!currentPerks.contains(selectedPerk)) {
                currentPerks.add(selectedPerk);
            }
        }
        voteTime = LocalDateTime.now();
    }

    public static void displayPerksToPlayers() {
        LocalDateTime currentTime = LocalDateTime.now();
        long remainingMillis = Duration.between(currentTime, voteTime.plusMinutes(3)).toMillis();

        String timeString;
        if (remainingMillis <= 0) {
            timeString =  "1 Votes Minimum"; // Replace with the desired message for elapsed time
        } else {
            timeString = TimeUtils.getTimeRemainingDHM(currentTime, voteTime.plusMinutes(3));
        }

        if (currentPerks != null) {
            World.getPlayers().forEach(p ->
                    p.getPacketSender().sendString(20103, "Voting for Perk")
                            .sendSpriteChange(20104, currentPerks.get(0).getSpriteId())
                            .sendSpriteChange(20105, currentPerks.get(1).getSpriteId())
                            .sendSpriteChange(20106, currentPerks.get(2).getSpriteId())
                            .sendString(20111, timeString)
                            .sendTabInterface(GameSettings.SERVER_TAB, 20100)
            );
        }
    }

    public static void displayVotesToPlayers() {
        if (currentPerks != null) {
            World.getPlayers().forEach(p ->
                p.getPacketSender().sendString(20107, currentPerks.get(0).getVotes())
                    .sendString(20108, currentPerks.get(1).getVotes())
                    .sendString(20109, currentPerks.get(2).getVotes())
            );
        }
    }

    public static void voteForPerk(String ip, int perkIndex) {
        if (ipsVoted.contains(ip))
            return;
        ipsVoted.add(ip);
        if (perkIndex >= 0 && perkIndex < currentPerks.size()) {
            Perk selectedPerk = currentPerks.get(perkIndex);
            selectedPerk.incrementVotes();
        }
    }

    public static void pickWinningPerk() {
        List<Perk> topPerks = new ArrayList<>();
        int maxVotes = 0;

        for (Perk perk : currentPerks) {
            if (perk.getVotes() > maxVotes) {
                maxVotes = perk.getVotes();
                topPerks.clear();
                topPerks.add(perk);
            } else if (perk.getVotes() == maxVotes) {
                topPerks.add(perk);
            }
        }

        if (!topPerks.isEmpty()) {
            Perk winningPerk = topPerks.get(new Random().nextInt(topPerks.size()));
            activateWinningPerk(winningPerk);
        }
    }

    public static void activateWinningPerk(Perk winningPerk) {
        if (perkIsActive)
            return;

        String message = "[PERKS] " + winningPerk.getName() + " has been selected as the server perk for the next hour.";

        for (Player player : World.getPlayers()) {
            if (player == null) {
                continue;
            }
            player.getPacketSender().sendBroadCastMessage(message, 50);
        }

        World.sendMessage("@red@<shad=0>@red@<shad=0>[WORLD] " + winningPerk.getName() + " has been selected as the server perk for the next hour.");
        DiscordManager.sendMessage("[PERKS] " + winningPerk.getName() + " has been selected as the server perk for the next hour. <@&1475675670196654080>", Channels.ACTIVE_EVENTS);

        currentPerk = winningPerk;
        startTime = LocalDateTime.now();
        perkIsActive = true;
    }
}
