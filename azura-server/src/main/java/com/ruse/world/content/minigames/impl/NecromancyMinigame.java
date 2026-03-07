package com.ruse.world.content.minigames.impl;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.container.impl.Equipment;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.SkillingPetBonuses;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.combat.prayer.PrayerHandler;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class NecromancyMinigame {
    private static boolean soulbanealive;
    private static int tick;
    public static int BOSSES_KILLED = 0;
    public static final Position BANKING_AREA = new Position(3034, 3861,0);
    public static final Position LOBBY_AREA = new Position(3031, 3861,0);
    public static final Position GAME_AREA = new Position(2783, 4042,0);
    private static final int damageNeeded = 50;
    public static final int NECROMANCY_TICKET = 621;
    public static final int TIER_1_TOTEM = 20080;
    public static final int TIER_2_TOTEM = 20081;
    public static final int TIER_3_TOTEM = 20082;
    public static int ROUND_TIMER = 1200;
    public static final int WAIT_TIMER = 120;
    public static int waitTimer = WAIT_TIMER;
    public static int TOTAL_PLAYERS = 0;
    private static int PLAYERS_WAITING = 0;
    public static final int MIN_PLAYERS = 1;
    public static final String PLAYING = "PLAYING";
    public static final String WAITING = "WAITING";
    private static boolean gameRunning;
    private static Map<Player, String> playerMap = new HashMap<>();
    private static CopyOnWriteArrayList<NPC> npcList = new CopyOnWriteArrayList<>();
    private static NPC[] BOSSES = new NPC[5];
    public static String getState(Player player) {
        return playerMap.get(player);
    }

    public static void insertWaiting(Player p) {
        if (getState(p) == null) {
            playerMap.put(p, WAITING);
            TOTAL_PLAYERS++;
            PLAYERS_WAITING++;
        }
        p.getSession().clearMessages();
        p.moveTo(LOBBY_AREA);
        clearGameItems(p);
        p.getMovementQueue().setLockMovement(false).reset();
        p.getPacketSender().sendWalkableInterface(21005, true);
    }

    public static void removeWaiting(Player p, boolean fromList) {
        String state = getState(p);
        if (state != null) {
            if (fromList) {
                playerMap.remove(p);
            }
            TOTAL_PLAYERS--;
            if (state == WAITING) {
                PLAYERS_WAITING--;
            }
        }
        p.getPacketSender().sendInterfaceRemoval();
        p.getPacketSender().sendWalkableInterface(21005, false);
        p.getSession().clearMessages();
        p.moveTo(BANKING_AREA);
        p.getMovementQueue().setLockMovement(false).reset();
    }

    public static void sequence() {
        if (TOTAL_PLAYERS == 0 && !gameRunning)
            return;
        tick++;
        if (tick == 2){
            tick = 0;
            updateWaitingInterface();
        }
        if (waitTimer > 0)
            waitTimer--;
        if (waitTimer <= 0) {
            if (!gameRunning)
                startGame();
            else {
                for (Player p : playerMap.keySet()) {
                    if (p == null)
                        continue;
                    String state = getState(p);
                    if (state != null && state.equals(WAITING)) {
                        p.getPacketSender().sendMessage("@gre@<shad=0>The Soulbane Event will start once the current game has ended.");
                    }
                }
            }
            waitTimer = WAIT_TIMER;
        } else if (waitTimer > 0 && (waitTimer % 50) == 0) {
            for (Player p : playerMap.keySet()) {
                if (p == null)
                    continue;
                String state = getState(p);
                if (state != null && state.equals(WAITING) && !gameRunning) {
                    p.getPacketSender().sendMessage(
                            "@red@The Soulbane Event will begin soon!");
                }
            }
        }
        if (gameRunning) {
            updateIngameInterface();
            processNPCs();
            updateRoundState();
            if (allBossesDead() || allPlayersDead()) {
                endGame(true);
                waitTimer = WAIT_TIMER;
            }
        }
    }

    private static void updateRoundState() {

        if (ROUND_TIMER > 0)
            ROUND_TIMER = ROUND_TIMER - 1;

        if (ROUND_TIMER <= 0) {
            if (BOSSES_KILLED < 4) {
                for (Player p : playerMap.keySet()) {
                    if (p == null)
                        continue;
                    String state = getState(p);
                    if (state != null && state.equals(PLAYING)) {
                        p.msgRed("Your Team Failed to defeat 4 bosses in time.");
                    }
                }
                endGame(false);
                waitTimer = WAIT_TIMER;
            }
        }
    }



        private static void updateWaitingInterface() {
        for (Player p : playerMap.keySet()) {
            if (p == null)
                continue;
            String state = getState(p);
            if (state != null && state.equals(WAITING)) {
                p.getPacketSender().sendString(21006, "Next Game: " + waitTimer / 2 + "");
                p.getPacketSender().sendString(21007, "Players Ready: " + PLAYERS_WAITING + "");
                p.getPacketSender().sendString(21008, "(Need at least " + MIN_PLAYERS + " players)");
                p.getPacketSender().sendString(21009, "");
            }
        }
    }
    private static boolean allBossesDead() {
        int count = 0;
        for (NPC boss : BOSSES) {
            if (boss != null) {
                if (boss.getConstitution() <= 0 || boss.isDying()) {
                    count++;
                }
            }
        }
        BOSSES_KILLED = count;
        if (BOSSES_KILLED == 4 && !soulbanealive) {
            for (Player p : new HashSet<>(playerMap.keySet())) {
                if (p == null) {
                    continue;
                }
                soulbanealive = true;
                if (!p.forgedGauntletWeapon) {
                    p.msgRed("You failed to Forge a Soulstone Blade in time, only the strongest prevail...");
                    leave(p, true);  // Safe to remove from playerMap now
                } else {
                    p.sendMessage("Your team has summoned the Soulbane!");
                    p.getPacketSender().sendCameraShake(3, 2, 3, 2);
                    p.getPacketSender().sendFadeTransition(50, 50, 50);
                    p.moveTo(2862, 3982, 0);

                    TaskManager.submit(new Task(3, p, false) {
                        @Override
                        public void execute() {
                            p.getPacketSender().sendCameraNeutrality();
                            BOSSES[0].performGraphic(new Graphic(2));
                            BOSSES[0].performAnimation(new Animation(2));
                            this.stop();
                        }
                    });
                }
            }
        }
        return count >= 5;
    }

    private static boolean allPlayersDead() {
        for (Player p : playerMap.keySet()) {
            if (p == null)
                continue;
            String state = getState(p);
            if (state != null && state.equals(PLAYING)) {
                return false;
            }
        }
        return true;
    }

    public static void sendGameMessage(String message) {
        for (Player p : playerMap.keySet()) {
            if (p == null)
                continue;
            p.msgFancyPurp(message);
        }
    }


        private static void processNPCs() {
        for (NPC npc : npcList) {
            if (npc == null)
                continue;
        }
    }
    private static void updateIngameInterface() {
        int totalSeconds = ROUND_TIMER / 2;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        String timeRemaining = String.format("Time Remaining %d:%02d", minutes, seconds);

        for (Player p : playerMap.keySet()) {
            if (p == null)
                continue;
            String state = getState(p);
            if (state != null && state.equals(PLAYING)) {
                String prefix = p.getMinigameAttributes().getPestControlAttributes().getDamageDealt() == 0 ? "@red@"
                        : p.getMinigameAttributes().getPestControlAttributes().getDamageDealt() < damageNeeded ? "@yel@"
                        : "@gre@";
                p.getPacketSender().sendString(21006,"The Soul Bane");
                if (!soulbanealive) {
                    p.getPacketSender().sendString(21007, timeRemaining);
                }
                if (soulbanealive) {
                    p.getPacketSender().sendString(21007, "Defeat Soulbane");
                }
                p.getPacketSender().sendString(21008,"Bosses killed " +  BOSSES_KILLED + "/5");
                p.getPacketSender().sendString(21009,"Your Damage: " + p.getMinigameAttributes().getPestControlAttributes().getDamageDealt());
            }
        }
    }

    private static void startGame() {
        boolean startGame = !gameRunning && PLAYERS_WAITING >= MIN_PLAYERS;
        if (startGame) {
            gameRunning = true;
            soulbanealive = false;
            ROUND_TIMER = 1800;
            spawnMainNPCs();
        }
        for (Player player : playerMap.keySet()) {
            if (player != null) {
                String state = getState(player);
                if (state != null && state.equals(WAITING)) {
                    if (startGame) {
                        movePlayerToIsland(player);

                        playerMap.put(player, PLAYING);
                    } else
                        player.getPacketSender().sendMessage("3 Players Needed to Start!");
                }
            }
        }
    }

    private static void movePlayerToIsland(Player p) {
        p.getPacketSender().sendInterfaceRemoval();
        p.getSession().clearMessages();
        p.moveTo(GAME_AREA);
        p.setTimesDuginMinigame(0);
        p.getInventory().add(8044, 1);
        p.getInventory().add(8046, 1);
        p.getInventory().add(311, 1);
        PrayerHandler.deactivateAll(p);
        CurseHandler.deactivateAll(p);

        p.setCrystalRunning(false);
        p.setForgedGauntletWeapon(false);

        p.setTier2GauntletWeapon(false);
        p.setTier3GauntletWeapon(false);

        p.getMovementQueue().setLockMovement(false).reset();

        PLAYERS_WAITING--;
    }

    public static void clearGameItems(Player p) {
        //p.msgRed("Attempting to clear Soulbane Game Items from inventory - Bank");

        if (p.wearingNecroGameItems()){
            p.getEquipment().setItem(Equipment.WEAPON_SLOT, new Item(-1));
            p.getUpdateFlag().flag(Flag.APPEARANCE);
            p.getEquipment().refreshItems();

        }
        if (p.wearingNecroGameItemsonCosmetic()){
            p.getCosmetics().setItem(Equipment.WEAPON_SLOT, new Item(-1));
            p.getUpdateFlag().flag(Flag.APPEARANCE);
            p.getEquipment().refreshItems();


        }

        for (Bank bank : p.getBanks()) {
            if (bank != null && bank.contains(311)) {
                bank.delete(311, bank.getAmount(311));
            }
            if (bank != null && bank.contains(8044)) {
                bank.delete(8044, bank.getAmount(8044));
            }
            if (bank != null && bank.contains(8046)) {
                bank.delete(8046, bank.getAmount(8046));
            }
            if (bank != null && bank.contains(7766)) {
                bank.delete(7766, bank.getAmount(7766));
            }
            if (bank != null && bank.contains(7788)) {
                bank.delete(7788, bank.getAmount(7788));
            }
            if (bank != null && bank.contains(7747)) {
                bank.delete(7747, bank.getAmount(7747));
            }
            if (bank != null && bank.contains(373)) {
                bank.delete(373, bank.getAmount(373));
            }
            if (bank != null && bank.contains(375)) {
                bank.delete(375, bank.getAmount(375));
            }
            if (bank != null && bank.contains(7780)) {
                bank.delete(7780, bank.getAmount(7780));
            }
            if (bank != null && bank.contains(7782)) {
                bank.delete(7782, bank.getAmount(7782));
            }
            if (bank != null && bank.contains(7783)) {
                bank.delete(7783, bank.getAmount(7783));
            }

            //wep
            if (bank != null && bank.contains(7784)) {
                bank.delete(7784, bank.getAmount(7784));
            }
            if (bank != null && bank.contains(7785)) {
                bank.delete(7785, bank.getAmount(7785));
            }
            if (bank != null && bank.contains(7786)) {
                bank.delete(7786, bank.getAmount(7786));
            }



        }


        if (p.getInventory().contains(375)){
            p.getInventory().delete(375, 9999999);
        }
        if (p.getInventory().contains(311)){
            p.getInventory().delete(311, 9999999);
        }

        if (p.getInventory().contains(373)){
            p.getInventory().delete(373, 9999999);
        }

        if (p.getInventory().contains(7780)){
            p.getInventory().delete(7780, 9999999);
        }
        if (p.getInventory().contains(7782)){
            p.getInventory().delete(7782, 9999999);
        }
        if (p.getInventory().contains(7783)){
            p.getInventory().delete(7783, 9999999);
        }

        if (p.getInventory().contains(7784)){
            p.getInventory().delete(7784, 9999999);
        }
        if (p.getInventory().contains(7785)){
            p.getInventory().delete(7785, 9999999);
        }
        if (p.getInventory().contains(7786)){
            p.getInventory().delete(7786, 9999999);
        }



            if (p.getInventory().contains(8044)){
                p.getInventory().delete(8044, 9999999);
            }
            if (p.getInventory().contains(8046)){
                p.getInventory().delete(8046, 9999999);
            }
            if (p.getInventory().contains(7766)){
                p.getInventory().delete(7766, 9999999);
            }
            if (p.getInventory().contains(7788)){
                p.getInventory().delete(7788, 9999999);
            }
            if (p.getInventory().contains(7747)){
                p.getInventory().delete(7747, 9999999);
            }
       // p.msgRed("Cleared");

    }


        private static void rollRareTotems(Player p) {
        int rarechance = Misc.random(0,200);
        int tier2Amount = Misc.random(2,4);
        int tier3Amount = Misc.random(3,5);
        int baseAmount = Misc.random(1,3);


        //RARE
         if (rarechance >= 195) {
            if (p.tier2GauntletWeapon){
                p.getInventory().add(TIER_3_TOTEM, tier2Amount);
                p.msgFancyPurp("You received " + tier2Amount + " extra Totems for completing a tier 2 run!");
            }
            if (p.tier3GauntletWeapon){
                p.getInventory().add(TIER_3_TOTEM, tier3Amount);
                p.msgFancyPurp("You received " + tier3Amount + " extra Totems for completing a tier 3 run!");

            }
            p.getInventory().add(TIER_3_TOTEM, baseAmount);
             p.sendMessage("You received " + baseAmount + " tier 3 totems for your efforts!");
        }

        //UNCOMMON
        if (rarechance >= 150 && rarechance < 195) {
            if (p.tier2GauntletWeapon){
                p.getInventory().add(TIER_2_TOTEM, tier2Amount);
                p.msgFancyPurp("You received " + tier2Amount + " extra Totems for completing a tier 2 run!");

            }
            if (p.tier3GauntletWeapon){
                p.getInventory().add(TIER_2_TOTEM, tier3Amount);
                p.msgFancyPurp("You received " + tier3Amount + " extra Totems for completing a tier 3 run!");

            }
            p.getInventory().add(TIER_2_TOTEM, baseAmount);
            p.sendMessage("You received " + baseAmount + " tier 2 totems for your efforts!");
        }

        //COMMON
        if (rarechance >= 0) {
            if (p.tier2GauntletWeapon){
                p.getInventory().add(TIER_1_TOTEM, tier2Amount);
                p.msgFancyPurp("You received " + tier2Amount + " extra Totems for completing a tier 2 run!");

            }
            if (p.tier3GauntletWeapon){
                p.getInventory().add(TIER_1_TOTEM, tier3Amount);
                p.msgFancyPurp("You received " + tier3Amount + " extra Totems for completing a tier 3 run!");

            }
            p.getInventory().add(TIER_1_TOTEM, baseAmount);
            p.sendMessage("You received " + baseAmount + " tier 1 totems for your efforts!");
        }
    }

    private static void endGame(boolean won) {
        for (Player p : playerMap.keySet()) {
            clearGameItems(p);

            if (p == null)
                continue;
            String state = getState(p);
            Item xTotem = new Item(3781, 1);
            if (state != null && state.equals(PLAYING)) {
                leave(p, false);

                if (won && p.getMinigameAttributes().getPestControlAttributes().getDamageDealt() >= damageNeeded) {
                    p.getPacketSender().sendMessage("Your team has successfully completed the Soul Bane.");
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_2_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_10_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_25_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_50_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_100_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_150_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_250_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_350_SOULBANE, 1);
                    Achievements.doProgress(p, Achievements.Achievement.COMPLETE_500_SOULBANE, 1);
                    StarterTasks.doProgress(p, StarterTasks.StarterTask.COMPLETE_5_SOULBANE, 1);
                    p.setTotalSoulbaneRuns(p.getTotalSoulbaneRuns() + 1);
                    p.setSoulbaneStreak(p.getSoulbaneStreak() + 1);

                    if (p.getSoulbaneStreak() >= 10){
                        p.setSoulbaneStreak(0);
                        p.msgFancyPurp("You received an Enhanced Totem for completing 10 runs in a row!");
                        if (!p.getInventory().canHold(xTotem)) {
                            p.getBank(0).add(xTotem);
                            p.msgFancyPurp("Enhanced Totem was added to your bank!");
                        } else {
                            p.getInventory().add(xTotem);
                            p.msgFancyPurp("Enhanced Totem was added to your Inventory!");
                        }
                    }

                    boolean undead_energy = p.getEquipment().contains(17022) || p.getEquipment().contains(2614) ;

                    if (undead_energy){
                        if (Misc.random(0, 5) == 0){
                            rollRareTotems(p);
                        }
                    }
                    if (p != null) {
                        int valExp = Misc.random(25000, 50000);
                        if (p.getBattlePass().getType() == BattlePassType.TIER2 || p.getBattlePass().getType() == BattlePassType.TIER1) {
                            p.getBattlePass().addExperience(Misc.random(valExp));
                            p.msgFancyPurp("You received " + valExp + " Battle Pass Experience for completing Soulbane!");
                        }
                    }

                    PlayerPanel.refreshPanel(p);
                    int baseVal = 2000;
                    int bonus = p.getRights().getNecromancyTicketBonus(p);
                    int tickets = Misc.random(0, 1000);
                    int total_extras = bonus + tickets;
                    //NOCTURNE AMULET
                    boolean necro_necklace = p.getEquipment().contains(20065) || p.getCosmetics().contains(20065);



                    if (PerkManager.currentPerk != null) {
                        if (PerkManager.currentPerk.getName().equalsIgnoreCase("Necro")) {
                            baseVal *= 2;
                            total_extras *= 2;
                        }
                    }

                    if (necro_necklace){
                        int necro_boost_amount = total_extras *= 1.15;
                        total_extras *= 1.15;
                        p.getSkillManager().addExperience(Skill.NECROMANCY, 5000);
                        if (p.getNecroBoost().isActive()){
                            p.getSkillManager().addExperience(Skill.NECROMANCY, 5000);
                        }

                    }
                    if (p.getMembershipTier().isMember())
                        total_extras *= p.getMembershipTier().getExtra_points_tickets();

                    if (GameSettings.DOUBLE_NECRO_POINTS) {
                        if (necro_necklace){
                            int necro_boost_amount = total_extras *= 1.15;
                            total_extras *= 1.15;
                            p.getSkillManager().addExperience(Skill.NECROMANCY, 5000);
                            if (p.getNecroBoost().isActive()){
                                p.getSkillManager().addExperience(Skill.NECROMANCY, 5000);
                            }
                        }
                        p.getInventory().add(NECROMANCY_TICKET, (baseVal * 2) + (total_extras * 2));
                        p.getPacketSender().sendMessage("You have received " + ((baseVal * 2) + (total_extras * 2)) + " Necro Points");
                        p.getSkillManager().addExperience(Skill.NECROMANCY, 10000);
                        if (p.getNecroBoost().isActive()){
                            p.getSkillManager().addExperience(Skill.NECROMANCY, 10000);
                        }
                        rollRareTotems(p);

                    } else {
                        p.getInventory().add(NECROMANCY_TICKET, baseVal + total_extras);
                        p.getPacketSender().sendMessage("You have received " + (500 + total_extras) + " Necro Points");
                        p.getSkillManager().addExperience(Skill.NECROMANCY, 5000);
                        if (p.getNecroBoost().isActive()){
                            p.getSkillManager().addExperience(Skill.NECROMANCY, 5000);
                        }
                        rollRareTotems(p);
                    }
                     p.restart();
                } else if (won) {
                    p.getPacketSender().sendMessage("You didn't participate enough to receive a reward.");
                } else {
                    p.getPacketSender().sendMessage("You failed to complete the Soul Bane Minigame");
                }
                p.getMinigameAttributes().getPestControlAttributes().setDamageDealt(0);
            }
        }
        playerMap.entrySet().removeIf(entry -> (PLAYING.equalsIgnoreCase(entry.getValue())));

        for (NPC n : npcList) {
            if (n == null || !n.isRegistered())
                continue;
            if (n.getLocation() == Locations.Location.NECROMANCY_GAME_AREA) {
                World.deregister(n);
                n = null;
            }
        }
        npcList.clear();
        Arrays.fill(BOSSES, null);
        gameRunning = false;
    }


    private static void spawnMainNPCs() {
        // MAIN BOSS STARTS OFF MAP
        BOSSES[0] = spawnPCNPC(316, new Position(2859, 3986, 0));
        //
        BOSSES[1] = spawnPCNPC(309, new Position(2761, 4086, 0));
        BOSSES[2] = spawnPCNPC(310, new Position(2805, 4086, 0));
        BOSSES[3] = spawnPCNPC(314, new Position(2804, 4040, 0));
        BOSSES[4] = spawnPCNPC(315, new Position(2761, 4041, 0));



        for (NPC n : BOSSES) {
            double rate = playerMap.size() / 1.0;
            if (rate >= 10.0) {
                double multiplier = 3;
                int newHP = (int) (n.getDefinition().getHitpoints() * multiplier);
                n.setConstitution(newHP);
            } else if (rate >= 5.0) {
                double multiplier = 2;
                int newHP = (int) (n.getDefinition().getHitpoints() * multiplier);
                n.setConstitution(newHP);
            } else {
                int newHP = (int) (n.getDefinition().getHitpoints());
                n.setConstitution(newHP);
            }
            npcList.add(n);
        }
    }

    public static NPC spawnPCNPC(int id, Position pos) {
        NPC np = new NPC(id, pos);
        World.register(np);
        np.getMovementQueue().setLockMovement(true).reset();
        np.setPositionToFace(new Position(2920, 4768));
        return np;
    }

    public static void leave(Player p, boolean fromList) {
        String state = getState(p);
        if (state != null) {
            if (fromList) {
                playerMap.remove(p);
            }
            TOTAL_PLAYERS--;
            if (state == WAITING) {
                PLAYERS_WAITING--;
            }
            clearGameItems(p);
            p.getPacketSender().sendInterfaceRemoval();
            p.getPacketSender().sendWalkableInterface(21005, false);
            p.getSession().clearMessages();
            p.moveTo(BANKING_AREA);
            p.getMovementQueue().setLockMovement(false).reset();
            p.setCrystalRunning(false);
            p.setForgedGauntletWeapon(false);

            p.setTier2GauntletWeapon(false);
            p.setTier3GauntletWeapon(false);
        }
    }
}
