package com.ruse.world.content;
import com.ruse.GameSettings;
import com.ruse.model.Skill;
import com.ruse.model.definitions.NPCDrops;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.combat.CombatBuilder;
import com.ruse.world.content.combat.CombatFactory;
import com.ruse.world.content.slayercontent.SlayerHelmets;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.*;

public class DropForMultiplePlayers {


    public static NPC currentBoss = null;

    public static void handleDrop(NPC npc, Map<String, CombatBuilder.CombatDamageCache> damageCache) {
        currentBoss = null;
        if (damageCache.isEmpty()) {
            return;
        }
        Map<Player, Integer> killers = new HashMap<>();
        int maxDmg = 0;
        int topDmg = 100;
        for (Map.Entry<String, CombatBuilder.CombatDamageCache> entry : damageCache.entrySet()) {
            if (entry == null) {
                continue;
            }
            long timeout = entry.getValue().getStopwatch().elapsed();
            if (timeout > CombatFactory.DAMAGE_CACHE_TIMEOUT) {
                continue;
            }
            String username = entry.getKey();
            Player player = World.getPlayerByName(username);
            if (player == null || player.getConstitution() <= 0 || !player.isRegistered()) {
                continue;
            }
            if (entry.getValue().getDamage() > maxDmg) {
                maxDmg = entry.getValue().getDamage();
                topDmg = player.getIndex();
            }
            killers.put(player, entry.getValue().getDamage());
        }

        List<Map.Entry<Player, Integer>> result = sortEntries(killers);
        for (Iterator<Map.Entry<Player, Integer>> iterator = result.iterator(); iterator.hasNext(); ) {
            Map.Entry<Player, Integer> entry = iterator.next();
            Player killer = entry.getKey();

            if (killer.getIndex() != topDmg) {
                if (npc.getId() == 6326 || npc.getId() == 2745 || npc.getId() == 8009 || npc.getId() == 9006 || npc.getId() == 1807 || npc.getId() == 1084 || npc.getId() == 601 || npc.getId() == 4444) {
                    StarterTasks.doProgress(killer, StarterTasks.StarterTask.KILL_4_RIFT_BOSSES, 1);
                }

                if (npc.getId() == 2341){
                    if (killer.getMarinaTaskID() == 8){
                        MarinasTasks.handleTasks(killer);
                    }
                }

                if (npc.getId() == 1084){
                    if (killer.getMarinaTaskID() == 10){
                        MarinasTasks.handleTasks(killer);
                    }
                }

                if (npc.getId() == 9800 ) {
                    if (killer.getMediumTasks().hasCompletedAll() && !killer.getEliteTasks().hasCompletedAll()) {
                        EliteTasks.doProgress(killer, EliteTasks.EliteTaskData.KILL_750_AQUA_GUARDIAN, 1);
                    }
                }

                if (npc.getId() == 2465 || npc.getId() == 2466 || npc.getId() == 2467) {
                    SlayerHelmets.process(killer,1305);
                }

                if (npc.getId() == 3307) {
                    killer.getSkillManager().addExperience(Skill.PRAYER, 7500);
                }
                if (npc.getId() == 3308) {
                    killer.getSkillManager().addExperience(Skill.JOURNEYMAN, 7500);
                }
                if (npc.getId() == 8004 || npc.getId() == 8002 || npc.getId() == 8000) {
                    killer.getSkillManager().addExperience(Skill.SLAYER, 7500);
                }
                if (npc.getId() == 2111 || npc.getId() == 2112 || npc.getId() == 2113) {
                    int valExp = Misc.random(5000, 10000);
                    if (killer.getBattlePass().getType() == BattlePassType.TIER2 || killer.getBattlePass().getType() == BattlePassType.TIER1) {
                        killer.getBattlePass().addExperience(Misc.random(valExp));
                        killer.msgFancyPurp("You received " + valExp + " Battle Pass Experience for Killing a Multi Boss!");
                    }
                }


                if (npc.getId() != 2111 &&npc.getId() != 2112 &&npc.getId() != 2113 &&npc.getId() != 6330 && npc.getId() != 9800 && npc.getId() != 3117 && npc.getId() != 1081 && npc.getId() != 316
                        && npc.getId() != 2465 && npc.getId() != 2466 && npc.getId() != 2467
                        && npc.getId() != 2019 && npc.getId() != 2022 && npc.getId() != 2021 && npc.getId() != 2034 && npc.getId() != 643 && npc.getId() != 640) {
                    int valExp = Misc.random(15000, 40000);
                    if (killer.getBattlePass().getType() == BattlePassType.TIER2 || killer.getBattlePass().getType() == BattlePassType.TIER1) {
                        killer.getBattlePass().addExperience(Misc.random(valExp));
                        killer.msgFancyPurp("You received " + valExp + " Battle Pass Experience for Killing a Global Boss!");
                    }
                }


                NPCDrops.handleDrops(killer, npc);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_10_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_50_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_250_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_500_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_1000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_2500_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_5000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_7500_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_10000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_15000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_30000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_50000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_75000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_100000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_150000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_200000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_250000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_300000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_400000_NPCS, 1);
                Achievements.doProgress(killer, Achievements.Achievement.KILL_500000_NPCS, 1);
                StarterTasks.doProgress(killer, StarterTasks.StarterTask.KILL_1000_MONSTERS, 1);
                killer.getPointsHandler().incrementNPCKILLCount(1);
                killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
            }
            iterator.remove();
        }
    }

    static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortEntries(Map<K, V> map) {

        List<Map.Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());

        Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        return sortedEntries;

    }
}
