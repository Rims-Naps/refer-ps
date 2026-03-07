package com.ruse.world.content.PrayerMinigame;

import com.ruse.model.Locations;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.entity.impl.player.Player;

public class PrayerMinigame {
    public static int RESET_MINION_KILLS = 0;
    public static int DEFAULT_BOSS_KILLS = 10;

    public static void updateMinionAreaInterface(Player p) {
        if (p.getLocation() != Locations.Location.PRAYER_MINIGAME_MINION_1 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_1 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_2 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_3){
            resetFullInterface(p);
            return;
        }

        int PLAYER_MINIONKILLS = p.getPrayerMinigameMinionKills();
        if (p.getLocation() == Locations.Location.PRAYER_MINIGAME_MINION_1) {
            if (p.prayerMinigameBossInstanceActive) {
                updateBossAreaInterface(p);
                return;
            }
            p.getPacketSender().sendWalkableInterface(4958, true);
            p.getPacketSender().sendString(4960, "Minion");
            p.getPacketSender().sendString(4961, "Kills");
            p.getPacketSender().sendString(4962, PLAYER_MINIONKILLS);
            return;
        }
        if (p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_1 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_2 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_3){
            resetFullInterface(p);
            return;
        }
    }

    public static void resetFullInterface(Player p) {
        p.getPacketSender().sendWalkableInterface(4958, false);
    }

    public static void updateBossAreaInterface(Player p) {
        if (p.getLocation() != Locations.Location.PRAYER_MINIGAME_MINION_1 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_1 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_2 && p.getLocation() != Locations.Location.PRAYER_MINIGAME_BOSS_3){
            resetFullInterface(p);
            return;
        }
        int PLAYER_BOSS_KILLS_LEFT = p.getPrayerMinigameBossKillsLeft();
        if (p.getLocation() == Locations.Location.PRAYER_MINIGAME_BOSS_1 || p.getLocation() == Locations.Location.PRAYER_MINIGAME_BOSS_2 || p.getLocation() == Locations.Location.PRAYER_MINIGAME_BOSS_3){
            p.getPacketSender().sendWalkableInterface(4958, true);
            p.getPacketSender().sendString(4960, "Boss");
            p.getPacketSender().sendString(4961, "Kills left");
            p.getPacketSender().sendString(4962, PLAYER_BOSS_KILLS_LEFT);
            return;
        }
        if (p.getLocation() != Locations.Location.PRAYER_MINIGAME_MINION_1){
            resetFullInterface(p);
            return;
        }
    }

    public static void handleMinionKill(Player p) {
        increaseRequiredMinionsKills(p);
        updateMinionAreaInterface(p);
  /*      Achievements.doProgress(p, Achievements.Achievement.KILL_100_CRYPT_MINIONS, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_1000_CRYPT_MINIONS, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_5000_CRYPT_MINIONS, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_10000_CRYPT_MINIONS, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_25000_CRYPT_MINIONS, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_50000_CRYPT_MINIONS, 1);*/
    }

    public static void handleBossKill(Player p) {
        updateBossAreaInterface(p);
/*        Achievements.doProgress(p, Achievements.Achievement.KILL_5_CRYPT_BOSSES, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_25_CRYPT_BOSSES, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_50_CRYPT_BOSSES, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_100_CRYPT_BOSSES, 1);
        Achievements.doProgress(p, Achievements.Achievement.KILL_250_CRYPT_BOSSES, 1);*/
    }

    public static void increaseRequiredMinionsKills(Player p) {
        int minion_kills_total = p.getPrayerMinigameMinionKills();
        if (minion_kills_total >= 1000){
            p.sendMessage("You reached the maximum amount of minion kills, time to fight the bosses!");
            return;
        }
        p.setPrayerMinigameMinionKills(minion_kills_total + 1);
    }

    public static void resetRequiredMinionKills(Player p) {
        p.setPrayerMinigameMinionKills(RESET_MINION_KILLS);
    }

    public static void setInstanceBossKillsAmount(Player p) {
        int PLAYER_MINIONKILLS = p.getPrayerMinigameMinionKills();
        if (p.getPrayerMinigameMinionKills() <= 99){
            p.sendMessage("Need atleast 100 kills to start boss instances!");
            return;
        }

        if (p.isPrayerMinigameBossInstanceActive()){
            p.sendMessage("<col=AF70C3><shad=0>You have @red@<shad=0>" + p.getPrayerMinigameBossKillsLeft() + " <col=AF70C3><shad=0>boss spawns left!");
            p.setPrayerMinigameBossKillsLeft(p.getPrayerMinigameBossKillsLeft());
            return;
        }

        int[] minion_kills_amount = {1000, 900, 800, 700, 600, 500, 400, 300, 200, 100};
        int[] bossKills = {50, 45, 40, 35, 30, 25, 20, 15, 10, 5};

        for (int i = 0; i < minion_kills_amount.length; i++) {
            if (Math.ceil(PLAYER_MINIONKILLS / minion_kills_amount[i]) == 1) {
                p.setPrayerMinigameBossKillsLeft(bossKills[i]);
                return;
            }
        }
    }
}
