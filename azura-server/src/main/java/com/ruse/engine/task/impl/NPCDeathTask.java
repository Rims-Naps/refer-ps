package com.ruse.engine.task.impl;

import com.ruse.GameSettings;
import com.ruse.donation.ServerCampaignHandler;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.DamageDealer;
import com.ruse.model.Item;
import com.ruse.model.Locations.Location;
import com.ruse.model.Position;
import com.ruse.model.Skill;
import com.ruse.model.container.impl.Equipment;
import com.ruse.model.definitions.NPCDrops;
import com.ruse.model.definitions.NewDropSystem;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.*;
import com.ruse.world.content.GlobalBosses.ArchonBossEvent;
import com.ruse.world.content.GlobalBosses.AscendentBossEvent;
import com.ruse.world.content.GlobalBosses.CelestialBossEvent;
import com.ruse.world.content.GlobalBosses.GladiatorBossEvent;
import com.ruse.world.content.Necromancy.RuneDropTables;
import com.ruse.world.content.PrayerMinigame.PrayerMinigame;
import com.ruse.world.content.ProgressTaskManager.EliteTasks;
import com.ruse.world.content.ProgressTaskManager.MediumTasks;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.ZoneProgression.NpcRequirements;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.combat.strategy.impl.FallenKnightScript;
import com.ruse.world.content.holidays.HolidayManager;
import com.ruse.world.content.combat.CombatBuilder;
import com.ruse.world.content.combat.CombatFactory;
import com.ruse.world.content.combat.prayer.CurseHandler;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.new_raids_system.raids_system.RaidsConstants;
import com.ruse.world.content.parties.PartyService;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.content.serverperks.ServerPerks;
import com.ruse.world.content.skill.impl.slayer.SlayerMaster;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.content.slayercontent.SlayerHelmets;
import com.ruse.world.content.startertasks.StarterTasks;
import com.ruse.world.content.tree.Node;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

import java.time.LocalDateTime;
import java.util.*;


public class NPCDeathTask extends Task {
    private final NPC npc;
    private int ticks = 2;
    private Player killer = null;

    private int NYTHOR = 13747;
    private int INFERNUS = 12228;

    private int PRAYER_MINION = 3168;
    private int PRAYER_EARTH_BOSS = 3169;
    private int PRAYER_WATER_BOSS = 3170;
    private int PRAYER_FIRE_BOSS = 3171;
    private int BRIMSTONE = 2017;
    private int TUTORIAL_BOSS = 655;
    private int EVERTHORN = 6323;
    private int BASILISK = 2018;
    private int LAVA_SUPERIOR_GLOBAL = 8002;
    private int AQUA_SUPERIOR_GLOBAL = 8004;
    private int GAIA_SUPERIOR_GLOBAL = 8000;

    private int FRENZY_BOSS = 1023;

    private int STREAM_BOSS = 1022;

    private int CUPIDBOSS = 3000;

    private int DONOBOSS = 2341;
    private int VOTEBOSS = 2342;

    private int ARCHON_BOSS = 555;
    private int CELESTIAL_BOSS = 556;
    private int ASCENDANT_BOSS = 557;
    private int GLADIATOR_BOSS = 559;

    private int SANTABOSS = 1061;

    private int SKELE_DEMON = 3307;
    private int EMERALD_CHAMP = 3308;


    private int DAILY_EARTH_BOSS = 3172;
    private int DAILY_WATER_BOSS = 3173;
    private int DAILY_FIRE_BOSS = 3174;


    private int ZONE_MULTIBOSS_1 = 6330;
    private int ZONE_MULTIBOSS_2 = 9800;
    private int ZONE_MULTIBOSS_3 = 3117;

    private int EARTH_ELEMENTAL = 2019;
    private int WATER_ELEMENTAL = 2022;
    private int FIRE_ELEMENTAL = 2021;
    private int EARTH_GOLEM = 2023;
    private int WATER_GOLEM = 2024;
    private int FIRE_GOLEM = 2025;
    private int TOWER_SUPERIOR = 2034;


    private int INSTANCE_BUNNY = 1194;
    private int RABID_BUNNY = 409;
    private int BUNNY_GLOBAL = 1789;

    private int AFK_BOSS = 576;


    private int WATER_BARROWS = 9814;
    private int FIRE_BARROWS = 9813;
    private int EARTH_BARROWS = 12239;

    private int VOID1 = 640;
    private int VOID2 = 643;



    private Map<String, CombatBuilder.CombatDamageCache> cachedDamageMap = new HashMap<>();

    public NPCDeathTask(NPC npc) {
        super(2);
        this.npc = npc;
        this.ticks = 2;
        cachedDamageMap.putAll(npc.getCombatBuilder().getDamageMap());
    }


    @SuppressWarnings("incomplete-switch")
    @Override
    public void execute() {
        try {
            npc.setEntityInteraction(null);

            switch (ticks) {
                case 2:
                        List<ServerPerks.Perk> activePerks = ServerPerks.getInstance().getActivePerks();
                        npc.getMovementQueue().setLockMovement(true).reset();
                        DamageDealer damageDealer = npc.getCombatBuilder().getTopDamageDealer(true, null);
                        npc.clearDamageMapOnDeath();
                        killer = damageDealer == null ? null : damageDealer.getPlayer();

                        if (killer != null) {
                            killer.getControllerManager().processNPCDeath(npc);
                        }

                    if (killer == null){
                        stop();
                        return;
                    }

                    NpcRequirements[] requirements = NpcRequirements.values();
                    String npcName = Misc.formatText(npc.getDefinition().getName());
                    String kills = Misc.insertCommasToNumber(String.valueOf(KillsTracker.getTotalKillsForNpc(npc.getId(), killer)));
                    String message = "<col=0><shad=6C1894>" + "You have @red@" + kills + "<col=0><shad=6C1894> kills " + "Of @red@" + npcName;
                    int totalKills = KillsTracker.getTotalKillsForNpc(npc.getId(), killer);

                    if (killer.getKillmessageamount() > 0) {
                        if (totalKills % killer.getKillmessageamount() == 0 && totalKills > 0) {
                            killer.sendMessage(message);
                        }
                    }


                        for (int i = 0; i < requirements.length - 1; i++) {
                            if (npc.getId() == requirements[i].getNpcId()) {
                                NpcRequirements nextRequirement = requirements[i + 1];
                                if (totalKills == nextRequirement.getAmountRequired()) {
                                    killer.sendMessage("<shad=0><col=AF70C3>You have completed the required kills for @red@<shad=0>" + npc.getDefinition().getName());
                                }
                                break; // Exit the loop after finding the match for the current NPC
                            }
                        }


                        //MAIN KILLS METHOD
                        if (killer != null) {
                            int toAdd = 1;
                            if (PerkManager.currentPerk != null) {
                                if (PerkManager.currentPerk.getName().equalsIgnoreCase("Frenzy")) {
                                    if (Misc.random(24) == 1)
                                        toAdd = 2;
                                }
                            }

                            killer.getPointsHandler().incrementNPCKILLCount(toAdd);

                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), toAdd, false));
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_10_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_50_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_250_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_500_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_1000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_2500_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_5000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_7500_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_10000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_15000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_30000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_50000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_75000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_100000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_150000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_200000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_250000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_300000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_400000_NPCS, toAdd);
                            Achievements.doProgress(killer, Achievements.Achievement.KILL_500000_NPCS, toAdd);
                            StarterTasks.doProgress(killer, StarterTasks.StarterTask.KILL_1000_MONSTERS, toAdd);

                        }
                    if (killer.getInventory().contains(7053)) {
                        killer.setSouls(killer.getSouls() + 1);
                        killer.getSkillManager().addExperience(Skill.SOULRENDING, 1000);
                    }
                    //OWNER CAPE HANDLE
                    int chance_2x = Misc.random(0,50);
                    if (killer.getCosmetics().contains(19944)) {
                        if (chance_2x == 0){
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
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
                            killer.msgRed("Your Owner Cape Doubled your Kill!");
                        }
                    }

                    if (killer.getCosmetics().contains(3520)) {
                        if (chance_2x == 0){
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
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
                            killer.msgRed("Your Owner Cape Doubled your Kill!");
                        }
                    }

                    if (killer.getCosmetics().contains(3521)) {
                        if (chance_2x == 0){
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
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
                            killer.msgRed("Your Owner Cape Doubled your Kill!");
                        }
                    }

                    if (killer.getCosmetics().contains(3522)) {
                        if (chance_2x == 0){
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
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
                            killer.msgRed("Your Owner Cape Doubled your Kill!");
                        }
                    }

                    //SANTAS BAG HANDLE
                    int chance_for_2x = Misc.random(0,75);
                    if (killer.getCosmetics().contains(1460) || killer.getEquipment().contains(1460)) {
                        if (chance_2x == 0){
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
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
                            killer.msgRed("Your Santa Bag Doubled your Kill!");
                        }
                    }



                    //KILL BOOST
                    int chance_2xx = Misc.random(0,25);
                    if (killer.getKillBoost().isActive()){
                        if (chance_2xx == 0){
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
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
                            killer.msgRed("Your Kill boost Doubled your kill!");
                        }
                    }



              /*      int chance_for_hearts = Misc.random(0,1500);
                    Item heart = new Item(Misc.random(744,745), 1);
                    if (chance_for_hearts == 0){
                        if (killer.getInventory().canHold(heart)) {
                            killer.getInventory().add(heart);
                            killer.msgFancyPurp("You received a Candy Heart!");
                        } else {
                            killer.getBank(killer.getCurrentBankTab()).add(heart);
                            killer.msgFancyPurp("You received a Candy Heart!");
                            killer.msgPurp("Your inventory is full, so your drop went to your bank.");
                        }
                    }*/



                    if (npc.getLocation().handleKilledNPC(killer, npc)) {
                        stop();
                        return;
                    }

                    HolidayManager.increaseMeter(killer, .002);


                    //FALLEN EVENT HANDLE
                    if (npc.getId() == 2097){
                        GameSettings.fallenMinionActive = false;
                        GameSettings.fallenKilledCount = 0;
                        GameSettings.fallenKnightStage = 0;
                        GameSettings.fallentEventActive = false;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[FALLEN] The Fallen Knight has been slain...");
                    }
                    if (npc.getId() == 2200 || npc.getId() == 2201){
                        GameSettings.fallenKilledCount++;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        if (GameSettings.fallenKilledCount >= 2){
                            GameSettings.fallenMinionActive = false;
                            GameSettings.fallenKilledCount = 0;
                        }
                    }


                    if (npc.getId() == 1051 || npc.getId() == 1052 || npc.getId() == 1053){
                        killer.getSkillManager().addExperience(Skill.SLAYER, 200);
                    }
                    if (npc.getId() == 1054 || npc.getId() == 1055 || npc.getId() == 1056){
                        killer.getSkillManager().addExperience(Skill.SLAYER, 300);
                    }
                    if (npc.getId() == 1057 || npc.getId() == 1058 || npc.getId() == 1059){
                        killer.getSkillManager().addExperience(Skill.SLAYER, 500);
                    }

                    // HOLIDAY SLAYER HANDLE
                    if (npc.getId() == 1051 || npc.getId() == 1052 || npc.getId() == 1053
                            || npc.getId() == 1054 || npc.getId() == 1055 || npc.getId() == 1056
                            || npc.getId() == 1057 || npc.getId() == 1058 || npc.getId() == 1059) {
                        if (killer.getHolidayTaskHandler().handleNpcDeath(npc.getId())) {
                            //HolidayManager.increaseMeter(killer, .01);
                            if (Misc.random(750) == 1) {
                                killer.getInventory().add(1454, 1);
                                killer.msgFancyPurp("You received a Frozen Casket while on task!");
                            }
                            if (Misc.random(5000) == 1) {
                                killer.getInventory().add(1453, 1);
                                killer.msgFancyPurp("You received a Frost Crate while on task!");
                            }
                        }
                    }



                    //NETHER AMMO HANDLE
                    int chance_2x_kc = Misc.random(0,100);
                    if (killer.getEquipment().getItems()[Equipment.AMMUNITION_SLOT].getId() == 1431) {
                        if (chance_2x_kc == 0){
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
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
                            killer.msgRed("Nether Energy doubled the value of your kill");
                        }
                    }



                        if (killer != null && activePerks.contains(ServerPerks.Perk.DBL_KC)) {
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
                        }



                      /*  if (killer != null && killer.getSummoning() != null && killer.getSummoning().getFamiliar() != null && killer.getSummoning().getFamiliar().getSummonNpc().getId() == BossPets.BossPet.CEATUS_PET.npcId) {
                            killer.getPointsHandler().incrementNPCKILLCount(1);
                            killer.getKillsTracker().add(new KillsTracker.KillsEntry(npc.getId(), 1, false));
                        }*/


                    //BATTLE PASS INSTANCE HANDLE
                    if (killer != null && killer.getPosition().getRegionId() == 9019) {
                        if (killer.getBattlePass().getType() == BattlePassType.TIER2 ) {
                            killer.getBattlePass().addExperience(Misc.inclusiveRandom(25, 50));
                        }
                        if (killer.getBattlePass().getType() == BattlePassType.TIER1) {
                            killer.getBattlePass().addExperience(Misc.inclusiveRandom(25, 50));
                        }
                    }

                    if (killer.getNodesUnlocked() != null) {
                        if (killer.getSkillTree().isNodeUnlocked(Node.GOBLINS_GREED)) {
                            if (Misc.random(1, 25) == 1) {
                                if (killer.getPosition().getRegionId() == 9019) {
                                    if (Misc.random(1,5) == 1) {
                                        killer.getInventory().add(995, 1);
                                    }
                                } else {
                                    killer.getInventory().add(995, 1);
                                }

                            }
                        }
                    }

                    RuneDropTables.handleNpcKill(killer, npc.getId());
                    NewDropSystem.handleNpcKill(killer,npc.getId());


                        if (killer != null && killer.getCompanion().getSummoned() != null) {
                            if (npc.getId() == 143 || npc.getId() == 1472 ||  npc.getId() == 7329){
                                killer.getCompanion().addExp(Misc.random(100, 1000));
                                killer.getCompanion().findTreasure();
                            }
                        }

                    if (npc.getId() == 1063 && killer.getChristmasQuest2023Stage() == 1) {
                        killer.getInventory().add(1467, 1);
                    }

                    if (npc.getId() == 8517 && killer.getChristmasQuest2023Stage() == 4 || npc.getId() == 8518 && killer.getChristmasQuest2023Stage() == 4) {
                        killer.getBossFight().onDeath(npc);
                        stop();
                        return;
                    }

                    if (killer.getStarterTasks().hasCompletedAll() && !killer.getMediumTasks().hasCompletedAll()) {
                        if (npc.getId() == INFERNUS) {
                            MediumTasks.doProgress(killer, MediumTasks.MediumTaskData.KILL_3K_INFERNUS, 1);
                        }
                    }


                    if (npc.getId() == NYTHOR) {
                        StarterTasks.doProgress(killer, StarterTasks.StarterTask.KILL_100_NYTHOR, 1);
                    }
                       if (npc.getId() == PRAYER_MINION && !killer.prayerMinigameBossInstanceActive) {
                           PrayerMinigame.handleMinionKill(killer);
                       }
                       if (npc.getId() == PRAYER_MINION && killer.prayerMinigameBossInstanceActive) {
                           killer.sendMessage("<col=AF70C3><shad=0>You still have @red@<shad=0>" + killer.getPrayerMinigameBossKillsLeft() + " <col=AF70C3><shad=0>boss spawns left!");
                       }


                       if (npc.getId() == PRAYER_EARTH_BOSS || npc.getId() == PRAYER_WATER_BOSS|| npc.getId() == PRAYER_FIRE_BOSS) {
                           killer.getBossFight().onDeath(npc);
                           NPCDrops.handleDrops(killer, npc, 1);
                           stop();
                           return;
                       }

                    if (npc.getId() == RABID_BUNNY){
                        if (killer.getEasterQuestStage() == 3){
                            if (killer.getRabidBunniesKilled() < 25){
                                killer.setRabidBunniesKilled(killer.getRabidBunniesKilled() + 1);
                                killer.msgRed("You've killed " + killer.getRabidBunniesKilled() + " out of 25 required Bunnies.");
                            }
                        }

                    }

                        if (npc.getId() == ZONE_MULTIBOSS_3){
                           if (killer.getMediumTasks().hasCompletedAll() && !killer.getEliteTasks().hasCompletedAll()) {
                               EliteTasks.doProgress(killer, EliteTasks.EliteTaskData.KILL_750_AQUA_GUARDIAN, 1);
                           }
                       }

                    if (npc.getId() == 9800 ) {
                        if (killer.getMediumTasks().hasCompletedAll() && !killer.getEliteTasks().hasCompletedAll()) {
                            EliteTasks.doProgress(killer, EliteTasks.EliteTaskData.KILL_750_AQUA_GUARDIAN, 1);
                        }
                    }

                    if (npc.getId() == 1081){
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);

                    }




                    if (npc.getId() == ZONE_MULTIBOSS_1 || npc.getId() == ZONE_MULTIBOSS_2  || npc.getId() == ZONE_MULTIBOSS_3
                        || npc.getId() == 457 || npc.getId() == 458) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }

                    if (npc.getId() == BUNNY_GLOBAL){
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[EASTER] The Bunny Boss has been slain...");
                    }

                    if (npc.getId() == VOID1 || npc.getId() == VOID2) {//VOID CRYSTAL MULTIS
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }
                    if (npc.getId() == 8520) {  //seasonal multi
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }

                    if (npc.getId() == 644) {
                        World.sendMessage("<col=AF70C3><shad=0>[SPRING] The Spring Boss has been slain...");

                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }

                        if (npc.getId() == AFK_BOSS) {// AFK BOSS
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        GameSettings.aggrostage = false;
                    }

                    if (npc.getId() == AFK_BOSS) {// AFK BOSS
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        GameSettings.aggrostage = false;
                    }

                    //SOULBANE MULTI DROPS HANDLE
                    if (npc.getId() == 309 ||npc.getId() == 310 ||npc.getId() == 314 ||npc.getId() == 315 ||npc.getId() == 316) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }




                    if (killer != null && killer.getPosition().getRegionId() != 9019) {
                        if (killer.getBattlePass().getType() == BattlePassType.TIER2) {
                            killer.getBattlePass().addExperience(Misc.inclusiveRandom(100, 250));
                        }
                        if ( killer.getBattlePass().getType() == BattlePassType.TIER1) {
                            killer.getBattlePass().addExperience(Misc.inclusiveRandom(100, 250));
                        }
                    }

                    if (npc.getId() == 2034) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }

                    if (npc.getId() == DAILY_EARTH_BOSS || npc.getId() == DAILY_WATER_BOSS  || npc.getId() == DAILY_FIRE_BOSS  ) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }

                    if (npc.getId() == STREAM_BOSS) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[EVENT] The Stream boss has been slain...");

                    }
                    if (npc.getId() == FRENZY_BOSS) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[FRENZY] The Frenzy boss has been slain...");

                    }


                    if (npc.getId() == EARTH_BARROWS || npc.getId() == FIRE_BARROWS || npc.getId() == WATER_BARROWS) {
                        killer.vod.killBarrowsNpc(killer, npc, true);
                        stop();
                        return;
                    }

                   

                        //SLAYER SUPERIOR BOSSES
                    if (npc.getId() == EARTH_ELEMENTAL || npc.getId() == WATER_ELEMENTAL || npc.getId() == FIRE_ELEMENTAL || npc.getId() == EARTH_GOLEM || npc.getId() == FIRE_GOLEM || npc.getId() == WATER_GOLEM || npc.getId() == TOWER_SUPERIOR) {
                        if (PartyService.playerIsInParty(killer)) {
                            killer.setRaidKills(killer.getRaidKills() + 1);
                            killer.getTowerParty().getOwner().getTowerRaid().proceed();
                        }
                    }
                    if (npc.getId() == TUTORIAL_BOSS) {
                            killer.getBossFight().onDeath(npc);
                            stop();
                            return;
                        }

                        if (npc.getId() == BRIMSTONE || npc.getId() == EVERTHORN || npc.getId() == BASILISK) {
                            if (killer.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK && npc.getId() == killer.getSlayer().getSlayerTask().getNpcId()) {
                                killer.getSlayer().handleSlayerTaskDeath(false);
                                killer.getBossFight().onDeath(npc);
                                killer.getSkillManager().addExperience(Skill.BEAST_HUNTER, 5000);
                                NPCDrops.handleDrops(killer, npc, 1);
                                SlayerHelmets.process(killer,1305);
                                stop();
                                if (npc.getId() == BRIMSTONE){
                                    killer.setBrimstone_kills(killer.getBrimstone_kills() + 1);
                                }
                                if (npc.getId() == EVERTHORN){
                                    killer.setEverthorn_kills(killer.getEverthorn_kills() + 1);
                                }
                                if (npc.getId() == BASILISK){
                                    killer.setBassilisk_kills(killer.getBassilisk_kills() + 1);
                                }
                                return;
                            }
                        }

                        // ALL MULTIBOSSES
                     if (npc.getId() == 9020) {
                         DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                     }

                    if (npc.getId() == ARCHON_BOSS) {
                        ArchonBossEvent.isAlive = false;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("@red@<shad=0>[ARCHON] Archonic C'thulu has been slain...");
                    }
                    if (npc.getId() == CELESTIAL_BOSS) {
                        CelestialBossEvent.isAlive = false;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("@red@<shad=0>[CELESTIAL] Celestial Orc has been slain...");
                    }
                    if (npc.getId() == ASCENDANT_BOSS) {
                        AscendentBossEvent.isAlive = false;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("@red@<shad=0>[ASCENDANT] Ascendant Berserker has been slain...");
                    }
                    if (npc.getId() == GLADIATOR_BOSS) {
                        GladiatorBossEvent.isAlive = false;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("@red@<shad=0>[GLADIATOR] Gladiator has been slain...");
                    }

                        if (npc.getId() == DONOBOSS) {
                       GameSettings.donobossStage = 0;
                       DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                       ServerCampaignHandler.setDonoBossSpawning(false);
                       ServerCampaignHandler.setLastDeath(LocalDateTime.now());
                       World.sendMessage("<col=AF70C3><shad=0>[DONOBOSS] The Donation boss has been slain...");

                    }

                    if (npc.getId() == CUPIDBOSS) {
                        GameSettings.cupidBossStage = 0;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[VALENTINES] Cosmic Cupid has been slain...");
                        HolidayManager.reset();
                    }

                    if (npc.getId() == VOTEBOSS) {
                        GameSettings.votebossStage = 0;
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[VOTEBOSS] The Vote boss has been slain...");
                    }


                    if (npc.getId() == SKELE_DEMON) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[SKELETAL] The Skeletal Demon has been slain...");
                    }
                    if (npc.getId() == EMERALD_CHAMP) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[SKILLING] The Emerald Champion has been slain...");
                    }

                        //RIFT EVENT HANDLE
                        if (npc.getId() == 6326 || npc.getId() == 2745 || npc.getId() == 8009 || npc.getId() == 9006 ||
                                npc.getId() == 1807 || npc.getId() == 1084 || npc.getId() == 601 || npc.getId() == 4444) {
                            DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                            RiftEvent.isAlive = false;
                            TaskManager.submit(new Task(2, false) {
                                @Override
                                public void execute() {
                                    RiftEvent.spawnNextBoss();
                                    this.stop();
                                }
                            });
                        }


                    //CORRUPT EVENT HANDLE
                    if (npc.getId() == 4412 || npc.getId() == 4413 || npc.getId() == 4414 || npc.getId() == 4415 ||
                            npc.getId() == 4416 || npc.getId() == 4417 || npc.getId() == 4418 || npc.getId() == 4419) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        CorruptRiftEvent.isAlive = false;
                        TaskManager.submit(new Task(2, false) {
                            @Override
                            public void execute() {
                                CorruptRiftEvent.spawnNextBoss();
                                this.stop();
                            }
                        });
                    }

                    RaidsParty party = killer.getMinigameAttributes().getRaidsAttributes().getParty();

                    if (npc.getId() == RaidsConstants.CORRUPT_RAID_MINION) {
                        // Check if the killer is a member of the party
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                                //System.out.println("Kill count for " + killer.getName() + ": " + killer.getMinigameAttributes().getRaidsAttributes().getKillcount());
                            }
                        }
                    }
                    if (npc.getId() == RaidsConstants.CORRUPT_RAID_BOSS_1) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }

                    if (npc.getId() == RaidsConstants.CORRUPT_RAID_BOSS_2) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }

                    if (npc.getId() == RaidsConstants.CORRUPT_RAID_BOSS_3) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }

                    if (npc.getId() == RaidsConstants.CORRUPT_RAID_BOSS_4) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }



                    if (npc.getId() == RaidsConstants.VOID_RAID_MINION) {
                        // Check if the killer is a member of the party
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                                //System.out.println("Kill count for " + killer.getName() + ": " + killer.getMinigameAttributes().getRaidsAttributes().getKillcount());
                            }
                        }
                    }
                    if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_1) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }

                    if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_2) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }

                    if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_3) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }

                    if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_4) {
                        if (party != null) {
                            if (party.getPlayers().contains(killer)) {
                                killer.getMinigameAttributes().getRaidsAttributes().incrementKillcount();
                            }
                        }
                    }

                    int icebornSpecial = Misc.random(1, 22);
                    if (icebornSpecial == 1) {
                        if (killer.getIceBornTimer().isActive()) {
                            killer.setIceBornSpecialRunning(true);
                        }
                    }

                        //FIRE 2 PRAYER
                    int emberspecial = Misc.random(1, 15);
                        if (emberspecial == 1) {
                            if (killer.getCurseActive()[CurseHandler.EMBERBLAST] || killer.getCurseActive()[CurseHandler.INFERNO]) {
                                killer.setEmberspecialrunning(true);
                            }
                        }

                        //EARTH 2 + 5 PRAYER
                    int healingspecial = Misc.random(1, 75);
                        if (healingspecial == 1) {
                            if (killer.getCurseActive()[CurseHandler.SERENITY] || killer.getCurseActive()[CurseHandler.STONEHAVEN]) {
                                killer.setHealingspecialrunning(true);
                        }
                    }

                    int chance1 = Misc.random(1, 15000);
                    if (chance1 == 1) {
                        killer.getInventory().add(10945, 1);
                        killer.msgFancyPurp("You received a $1 Bond");
                    }

                    int chance2 = Misc.random(1, 2500);
                    if (chance2 == 1) {
                        killer.getInventory().add(18979, 1);
                        killer.msgFancyPurp("You received a protection Gem!");
                    }


                        //NORMAL SLAYER SUPERIORS

                    if ( killer != null) {
                        if (killer.getSlayer().getSlayerTask() != SlayerTasks.NO_TASK && npc.getId() == killer.getSlayer().getSlayerTask().getNpcId()) {
                            int random = 100;
                            if (killer.getNodesUnlocked() != null) {
                                if (killer.getSkillTree().isNodeUnlocked(Node.BLOOD_OATH)) {
                                    random = 90;
                                }
                            }
                            if (Misc.random(1, random) == 1 && !killer.superiorisspawned) {
                                killer.setSuperiorisspawned(true);
                                if (killer.getSlayer().getSlayerMaster().equals(SlayerMaster.BEGINNER_SLAYER)) {
                                    NPC n = new NPC(6830, new Position(killer.getPosition().getX(), killer.getPosition().getY(), killer.getPosition().getZ())).setSpawnedFor(killer);
                                    World.register(n);
                                    killer.sendMessage("@bla@<shad=0>A Beginner Beast has appeared!");
                                    killer.getSkillManager().addExperience(Skill.SLAYER, 10000);
                                }
                                if (killer.getSlayer().getSlayerMaster().equals(SlayerMaster.MEDIUM_SLAYER)) {
                                    NPC n = new NPC(6841, new Position(killer.getPosition().getX(), killer.getPosition().getY(), killer.getPosition().getZ())).setSpawnedFor(killer);
                                    World.register(n);
                                    killer.sendMessage("@bla@<shad=0>A Medium Beast has appeared!");
                                    killer.getSkillManager().addExperience(Skill.SLAYER, 25000);

                                }
                                if (killer.getSlayer().getSlayerMaster().equals(SlayerMaster.ELITE_SLAYER)) {
                                    NPC n = new NPC(6831, new Position(killer.getPosition().getX(), killer.getPosition().getY(), killer.getPosition().getZ())).setSpawnedFor(killer);
                                    World.register(n);
                                    killer.sendMessage("@bla@<shad=0>An Elite Beast has appeared!");
                                    killer.getSkillManager().addExperience(Skill.SLAYER, 50000);
                                }
                                if (killer.getSlayer().getSlayerMaster().equals(SlayerMaster.CORRUPT_SLAYER)) {
                                    NPC n = new NPC(4410, new Position(killer.getPosition().getX(), killer.getPosition().getY(), killer.getPosition().getZ())).setSpawnedFor(killer);
                                    World.register(n);
                                    killer.sendMessage("@bla@<shad=0>An Corrupt Superior has appeared!");
                                    killer.getSkillManager().addExperience(Skill.SLAYER, 150000);
                                }

                                if (killer.getSlayer().getSlayerMaster().equals(SlayerMaster.SPECTRAL_SLAYER)) {
                                    NPC n = new NPC(4411, new Position(killer.getPosition().getX(), killer.getPosition().getY(), killer.getPosition().getZ())).setSpawnedFor(killer);
                                    World.register(n);
                                    killer.sendMessage("@bla@<shad=0>An Spectral Superior has appeared!");
                                    killer.getSkillManager().addExperience(Skill.SLAYER, 175000);
                                }
                            }
                        }
                    }

                    if (npc.getId() == 8004 || npc.getId() == 8002 || npc.getId() == 8000) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                        World.sendMessage("<col=AF70C3><shad=0>[SLAYER] The Slayer Boss has been slain...");
                        GameSettings.azgothstage = 0;
                    }

                    //NEW GROUP BEAST HANDLE
                    if (npc.getId() == 2465 || npc.getId() == 2466 || npc.getId() == 2467) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }
                    //SPECTRAL MULTI BOSSES
                    if (npc.getId() == 2111 || npc.getId() == 2112 || npc.getId() == 2113) {
                        DropForMultiplePlayers.handleDrop(npc, cachedDamageMap);
                    }


                        if (npc.getId() == 6830 || npc.getId() == 6841  || npc.getId() == 6831 || npc.getId() == 4410 ) {
                            killer.setSuperiorisspawned(false);
                        }

                        if (killer.getForgottenRaidParty() != null) {
                            if (killer.getForgottenRaidParty().isInRaid()) {
                                killer.getForgottenRaidParty().getRaid().getCurrentBoss().getFightData().submitDamageMap(cachedDamageMap);
                                killer.getForgottenRaidParty().getRaid().handleDeath(killer, npc.getId());
                            }
                        }

                        if (npc.getPosition().getRegionId() == 7758) {
                            killer.vod.killBarrowsNpc(killer, npc, true);
                            stop();
                            return;
                        }

                        if (!npc.isEventBoss()) {
                            NPCDrops.handleDrops(killer, npc, 1);
                            int playerDamageMapIndex = 0;
                            Map<Player, Integer> killers = new HashMap<>();
                            for (Map.Entry<String, CombatBuilder.CombatDamageCache> entry : cachedDamageMap.entrySet()) {
                                if (entry == null) {
                                    //System.out.println("Entry == null");
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
                                killers.put(player, entry.getValue().getDamage());
                            }

                            List<Map.Entry<Player, Integer>> result = sortEntries(killers);
                            List<String> foundDuoPartners = new LinkedList<>();
                            for (Map.Entry<Player, Integer> damageEntry : result) {
                                Player toDrop = damageEntry.getKey();
                                if (foundDuoPartners.contains(toDrop.getUsername())) continue;
                                toDrop.getSlayer().killedNpc(npc);
                                if (toDrop.getSlayer().getDuoPartner() != null && World.getPlayerByName(toDrop.getSlayer().getDuoPartner()) != null) {
                                    Player duoPlayer = World.getPlayerByName(toDrop.getSlayer().getDuoPartner());
                                    foundDuoPartners.add(toDrop.getUsername());
                                    foundDuoPartners.add(duoPlayer.getUsername());
                                    if (result.stream().anyMatch(mp -> mp.getKey().getUsername().equalsIgnoreCase(duoPlayer.getUsername()))) {
                                        duoPlayer.getSlayer().killedNpc(npc);
                                    }
                                }

                                if (++playerDamageMapIndex > 500) {
                                    break;
                                }
                            }




                            killer.getInstanceManager().death(killer, npc, npc.getDefinition().getName());
                            npc.getCombatBuilder().getDamageMap().clear();
                        }

                    stop();
                    break;
            }
            ticks--;
        } catch (Exception e) {
            e.printStackTrace();
            stop();
        }
    }

    @Override
    public void stop() {
        setEventRunning(false);
        npc.setDying(false);
        if (killer != null) {
            PlayerPanel.refreshPanel(killer);
        }

        // respawn
        if (npc.getDefinition().getRespawnTime() > 0 && npc.getLocation() != Location.NECROMANCY_GAME_AREA && npc.getLocation() != Location.DUNGEONEERING && !npc.isEventBoss()) {
            boolean instanceNPC = npc.getLocation() == Location.INSTANCE1 || npc.getLocation() == Location.INSTANCE2;

            TaskManager.submit(new NPCRespawnTask(npc, instanceNPC ? 4 : npc.getDefinition().getRespawnTime(), killer, instanceNPC));

        }


        World.deregister(npc);
    }
    public static <K, V extends Comparable<? super V>> List<Map.Entry<K, V>> sortEntries(Map<K, V> map) {
        List<Map.Entry<K, V>> sortedEntries = new ArrayList<>(map.entrySet());
        Collections.sort(sortedEntries, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return sortedEntries;
    }
}
