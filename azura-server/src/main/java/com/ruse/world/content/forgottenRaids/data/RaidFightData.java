package com.ruse.world.content.forgottenRaids.data;

import com.ruse.world.content.combat.CombatBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RaidFightData {

    public RaidFightData(String bossName) {
        this.bossName = bossName;
    }

    @Setter
    private long startTime;
    private long endTime;
    private long totalTime;
    private final String bossName;

    public void setEndTime(long time) {
        this.endTime = time;
        totalTime = endTime - startTime;
    }

    private final Map<String, Integer> deaths = new HashMap<>();
    private final Map<String, Long> damageDealers = new HashMap<>();

    public void submitDamageMap(Map<String, CombatBuilder.CombatDamageCache> map) {
        for (Map.Entry<String, CombatBuilder.CombatDamageCache> entry : map.entrySet()) {
            String dealer = entry.getKey();
            long damage = entry.getValue().getDamage();
            damageDealers.put(dealer, damage);
        }
    }

}
