package com.ruse.world.content.forgottenRaids.data;

import com.ruse.world.content.forgottenRaids.ForgottenRaid;
import com.ruse.world.content.forgottenRaids.ForgottenRaidDifficulty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RaidData {
    private ForgottenRaidDifficulty difficulty;
    private final List<String> players = new ArrayList<>();
    private int raidHeight;
    private String partyOwner;

    public RaidData(ForgottenRaid raid) {
        this.difficulty = raid.getDifficulty();
        this.players.addAll(raid.getParty().getPlayers());
        this.raidHeight = raid.getMyHeight();
        partyOwner = raid.getParty().getOwner();
    }

    public RaidData() {

    }

    private long startTime;
    private long endTime;
    private long totalTime;
    private final Map<String, Integer> deaths = new HashMap<>();
    private final Map<String, Long> damageDealers = new HashMap<>();
    private final List<RaidFightData> raidFightData = new ArrayList<>();

    public void submitFightData(RaidFightData data) {
        for (Map.Entry<String, Integer> deathEntry : data.getDeaths().entrySet()) {
            String playerName = deathEntry.getKey();
            int deathValue = deathEntry.getValue();

            if (deaths.containsKey(playerName)) deaths.put(playerName, deaths.get(playerName) + deathValue);
            else deaths.put(playerName, deathValue);
        }

        for (Map.Entry<String, Long> damageDealerEntry : data.getDamageDealers().entrySet()) {
            String playerName = damageDealerEntry.getKey();
            long damageValue = damageDealerEntry.getValue();

            if (damageDealers.containsKey(playerName))
                damageDealers.put(playerName, damageDealers.get(playerName) + damageValue);
            else damageDealers.put(playerName, damageValue);
        }
        raidFightData.add(data);
    }

    public void startRaid() {
        startTime = System.currentTimeMillis();
    }

    public void endRaid() {
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        RaidDataManager.addData(copy());
    }

    public RaidData copy() {
        RaidData copy = new RaidData();
        copy.raidHeight = this.raidHeight;
        copy.players.addAll(this.players);
        copy.partyOwner = this.partyOwner;
        copy.difficulty = this.difficulty;
        copy.startTime = this.startTime;
        copy.endTime = this.endTime;
        copy.totalTime = this.totalTime;
        copy.raidFightData.addAll(this.raidFightData);
        copy.deaths.putAll(this.deaths);
        copy.damageDealers.putAll(this.damageDealers);
        return copy;
    }
}
