package com.ruse.world.content.new_raids_system.instances;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.Position;
import com.ruse.model.RegionInstance;
import com.ruse.model.RegionInstance.RegionInstanceType;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.BattlePass.BattlePassType;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.new_raids_system.raids_party.RaidsParty;
import com.ruse.world.content.new_raids_system.raids_system.RaidsConstants;
import com.ruse.world.content.serverperks.PerkManager;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class VoidRaid
{
    public static boolean isPhase2 = false;
    public static boolean isPhase3 = false;

    private static NPC spawnNPCWithMode(int npcId, Position position, Player player) {
        NPC npc = new NPC(npcId, position);

        switch (player.getVoidRaidDifficulty()){
            case 1:
                npc.getDefinition().setDefenceMelee(0);
                npc.getDefinition().setDefenceMage(0);
                npc.getDefinition().setDefenceRange(0);
                npc.setConstitution(npc.getDefaultConstitution());
                break;
            case 2:
                npc.getDefinition().setDefenceMelee(0);
                npc.getDefinition().setDefenceMage(0);
                npc.getDefinition().setDefenceRange(0);
                npc.setConstitution(75_000);
                break;
            case 3:
                if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_1){//ATTACK WITH RANGE
                    npc.getDefinition().setDefenceMelee(99999);
                    npc.getDefinition().setDefenceMage(99999);
                }
                if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_2){//ATTACK WITH MELEE
                    npc.getDefinition().setDefenceRange(99999);
                    npc.getDefinition().setDefenceMage(99999);
                }
                if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_3){//ATTACK WITH MAGIC
                    npc.getDefinition().setDefenceMelee(99999);
                    npc.getDefinition().setDefenceRange(99999);
                }
                if (npc.getId() == RaidsConstants.VOID_RAID_BOSS_4){//ATTACK WITH RANGE
                    npc.getDefinition().setDefenceMelee(99999);
                    npc.getDefinition().setDefenceMage(99999);
                }
                npc.setConstitution(200_000);
                break;
        }
        World.register(npc);
        return npc;
    }

    public static void startRaid(final Player p) {
        final int height = p.getIndex() * 4;
        final RaidsParty party = p.getMinigameAttributes().getRaidsAttributes().getParty();

        if (p.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
            DialogueManager.sendStatement(p, "You must start a Raid party first.");
            return;
        }

        if (p != null && p.getCompanion().getSummoned() != null) {
            p.getCompanion().remove();
        }

        p.getPacketSender().sendInterfaceRemoval();
        p.sendMessage("@yel@<shad=0>Raid has started.");

        party.setHeight(height);
        party.setInstanceLevel(height);
        p.setRegionInstance(new RegionInstance(p, RegionInstanceType.VOID_RAID_1));
        //boss
        p.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_BOSS_1, new Position(2461, 4642, height), p));
        //minions
        p.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2457, 4642,	height), p));
        p.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2457, 4636,	height), p));
        p.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2464, 4641,	height), p));
        p.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2466, 4636,	height), p));


        if (p.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
            DialogueManager.sendStatement(p, "@red@<shad=0>You must start a Raid party first.");
            return;
        }

        if (party.hasEnteredRaids()) {
            p.getPacketSender().sendMessage("@red@<shad=0>Your party is currently in a Raid.");
            return;
        }

        if (party.getOwner() != p) {
            p.getPacketSender().sendMessage("@red@<shad=0>Only the party leader can start the Raid.");
            return;
        }

        party.enteredDungeon(true);

        p.getPacketSender().sendInterfaceRemoval();
        p.setRegionInstance(null);
        p.getMovementQueue().reset();
        p.getClickDelay().reset();
        p.moveTo(new Position(2461, 4637 + Misc.getRandom(3), height));
        p.getPacketSender().sendInteractionOption("null", 2, true);
        p.setInsideRaids(true);
        p.getSkillManager().stopSkilling();
        p.getPacketSender().sendClientRightClickRemoval();

        for (Player player : party.getPlayers()) {
            player.getRaidsSaving().initPhase2 = false;
            player.getRaidsSaving().initPhase3 = false;
            player.setRaidsParty(party);
            player.setInsideRaids(true);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            party.getPlayersInRaids().add(player);
            player.getMinigameAttributes().getRaidsAttributes().setKillcount(0);
        }

        TaskManager.submit(new Task(1) {
            int tick = 0;

            @Override
            public void execute()
            {
                if (tick == 10) {
                    startTask(party, height);
                    for (Player member : party.getPlayers()) {
                        member.getPacketSender().sendCameraNeutrality();
                    }
                }
                if(tick == 7 ) {
                    for (NPC npc : World.getNpcs()) {
                        if (npc != null && npc.getPosition().getZ() == party.getHeight()) {
                            for (Player member : party.getPlayers()) {
                                npc.getCombatBuilder().attack(member);
                            }
                            if (npc.getId() == 2115 || npc.getId() == 2116 || npc.getId() == 2117 || npc.getId() == 2118 || npc.getId() == 2119) {
                                npc.forceChat("You won't last long here!!");
                            }
                        }
                    }
                }
                tick++;
            }
        });
    }


    public static void sendPhaseTwo(RaidsParty party){

        Player owner = party.getOwner();
        final int height = owner.getIndex() * 4;
        isPhase2 = true;

        owner.getPacketSender().sendInterfaceRemoval();
        owner.sendMessage("@red@<shad=0>Wave 2.");
        owner.getRaidsSaving().initPhase2 = true;
        owner.setRegionInstance(new RegionInstance(owner, RegionInstanceType.VOID_RAID_2));
        owner.moveTo(new Position(2588, 4637 + Misc.getRandom(3), height));

        TaskManager.submit(new Task(2, owner, false) {

            @Override
            public void execute() {
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_BOSS_2, new Position(2585, 4636, height), owner));
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2584, 4642, height), owner));
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2592, 4640,	height), owner));
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2589, 4643,	height), owner));
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2592, 4635,	height), owner));
                stop();
            }
        });

        for (NPC npc : World.getNpcs()) {
            if (npc != null && npc.getPosition().getZ() == party.getHeight()) {
                for (Player member : party.getPlayers()) {
                    npc.getCombatBuilder().attack(member);
                }
                if (npc.getId() == 2115 || npc.getId() == 2116 || npc.getId() == 2117 || npc.getId() == 2118 || npc.getId() == 2119) {
                    npc.forceChat("Give up yet??");
                }
            }
        }
    }

    public static void sendPhaseThree(RaidsParty party) {
        Player owner = party.getOwner();
        final int height = owner.getIndex() * 4;
        isPhase3 = true;

        owner.getPacketSender().sendInterfaceRemoval();
        owner.sendMessage("@red@<shad=0>Final Wave.");
        owner.getRaidsSaving().initPhase3 = true;
        owner.setRegionInstance(new RegionInstance(owner, RegionInstanceType.VOID_RAID_3));
        owner.moveTo(new Position(2717, 4637 + Misc.getRandom(3), height));

        TaskManager.submit(new Task(2, owner, false) {

            @Override
            public void execute() {
                //bosses
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_BOSS_3, new Position(2714, 4643, height), owner));
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_BOSS_4, new Position(2719, 4636, height), owner));
                //minions
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2717, 4644, height), owner));
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2722, 4641,	height), owner));
                owner.getRegionInstance().spawnNPC(spawnNPCWithMode(RaidsConstants.VOID_RAID_MINION, new Position(2711, 4639,	height), owner));
                stop();
            }
        });

        for (NPC npc : World.getNpcs()) {
            if (npc != null && npc.getPosition().getZ() == party.getHeight()) {
                for (Player member : party.getPlayers()) {
                    npc.getCombatBuilder().attack(member);
                }
                if (npc.getId() == 2115 || npc.getId() == 2116 || npc.getId() == 2117 || npc.getId() == 2118 || npc.getId() == 2119) {
                    npc.forceChat("Hold your ground!!!!!");
                }
            }
        }


    }

    public static void startTask(RaidsParty party, int height) {
        TaskManager.submit(new Task(1, party, false) {
            private int tick = 0;
            @Override
            public void execute() {
                if (party.getPlayersInRaidsLocation(party) == 0)
                {
                    party.enteredDungeon(false);
                    for (NPC npc : World.getNpcs()) {
                        if (npc != null && npc.getPosition().getZ() == party.getHeight()) {
                            if (npc.getId() == 2115 || npc.getId() == 2116 || npc.getId() == 2117 || npc.getId() == 2118 || npc.getId() == 2119) {
                                World.deregister(npc);
                            }
                        }
                    }

                    stop();
                }
                tick++;
            }
        });
    }

    public static void exitRaidsOne(Player p) {
        final RaidsParty party = p.getMinigameAttributes().getRaidsAttributes().getParty();

        p.getPacketSender().sendInterfaceRemoval();

        if (p.getMinigameAttributes().getRaidsAttributes().getParty() == null) {
            DialogueManager.sendStatement(p, "@red@<shad=0>Join a party first.");
            return;
        }

        if (party.getOwner() != p) {
            return;
        }

        party.enteredDungeon(false);
        for(Player partyMember : party.getPlayers()) {
            partyMember.getRaidsSaving().initPhase2 = false;
            partyMember.getRaidsSaving().initPhase3 = false;
            partyMember.setRegionInstance(null);
            partyMember.moveTo(new Position(2521, 4833, 0));
            partyMember.getPacketSender().sendInterfaceRemoval();
            partyMember.getMovementQueue().setLockMovement(false);
            partyMember.getPacketSender().sendTabInterface(GameSettings.ACHIEVEMENT_TAB, 27601);
            partyMember.getPacketSender().sendDungeoneeringTabIcon(false);
            partyMember.getPacketSender().sendTab(GameSettings.ACHIEVEMENT_TAB);
            partyMember.getEquipment().refreshItems();

            if (partyMember.getMinigameAttributes().getRaidsAttributes().getKillcount() >= 2) {
                switch (p.getVoidRaidDifficulty()) {
                    case 1:
                        if (GameSettings.DOUBLE_RAIDS = true){
                            partyMember.getInventory().add(2053, 1);
                            partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due to the raid event!");
                        }
                        if (p.getRaidBoost().isActive()){
                            partyMember.getInventory().add(2053, 1);
                            partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due your active Boost!");
                        }
                        if (PerkManager.currentPerk != null) {
                            if (PerkManager.currentPerk.getName().equalsIgnoreCase("Raids")) {
                                partyMember.getInventory().add(2053, 1);
                                partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due to the active Perk!");
                            }
                        }


                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_1_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_5_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_25_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_100_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_250_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_500_SPECTRAL_RAIDS, 1);
                        partyMember.getInventory().add(2053, 1);
                        partyMember.getMinigameAttributes().getRaidsAttributes().incrementCompleted();
                        partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You managed to complete a round of Raids.. Here's your reward!");
                        partyMember.incrementVoidCompletions(1);
                        int valExp = Misc.random(6000, 8000);
                        if (p.getBattlePass().getType() == BattlePassType.TIER2 || p.getBattlePass().getType() == BattlePassType.TIER1) {
                            p.getBattlePass().addExperience(Misc.random(valExp));
                            p.msgFancyPurp("You received " + valExp + " Battle Pass Experience for an Easy Raid!");
                        }
                        break;
                    case 2:
                        if (p.getRaidBoost().isActive()){
                            partyMember.getInventory().add(2054, 1);
                            partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due your active Boost!");
                        }
                        if (GameSettings.DOUBLE_RAIDS = true){
                            partyMember.getInventory().add(2054, 1);
                            partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due to the raid event!");
                        }
                        if (PerkManager.currentPerk != null) {
                            if (PerkManager.currentPerk.getName().equalsIgnoreCase("Raids")) {
                                partyMember.getInventory().add(2054, 1);
                                partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due to the active Perk!");
                            }
                        }

                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_1_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_5_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_25_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_100_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_250_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_500_SPECTRAL_RAIDS, 1);
                        partyMember.getInventory().add(2054, 1);
                        partyMember.getMinigameAttributes().getRaidsAttributes().incrementCompleted();
                        partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You managed to complete a round of Medium Raids.. Here's your reward!");
                        partyMember.incrementMediumVoidCompletions(1);
                        int valExp2 = Misc.random(9000, 15000);
                        if (p.getBattlePass().getType() == BattlePassType.TIER2 || p.getBattlePass().getType() == BattlePassType.TIER1) {
                            p.getBattlePass().addExperience(Misc.random(valExp2));
                            p.msgFancyPurp("You received " + valExp2 + " Battle Pass Experience for a Medium Raid!");
                        }
                        break;
                    case 3:
                        if (p.getRaidBoost().isActive()){
                            partyMember.getInventory().add(2055, 1);
                            partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due your active Boost!");
                        }
                        if (GameSettings.DOUBLE_RAIDS = true){
                            partyMember.getInventory().add(2055, 1);
                            partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due to the raid event!");
                        }
                        if (PerkManager.currentPerk != null) {
                            if (PerkManager.currentPerk.getName().equalsIgnoreCase("Raids")) {
                                partyMember.getInventory().add(2055, 1);
                                partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You receive an extra key due to the active Perk!");
                            }
                        }

                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_1_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_5_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_25_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_100_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_250_SPECTRAL_RAIDS, 1);
                        Achievements.doProgress(partyMember, Achievements.Achievement.COMPLETE_500_SPECTRAL_RAIDS, 1);
                        partyMember.getInventory().add(2055, 1);
                        partyMember.getMinigameAttributes().getRaidsAttributes().incrementCompleted();
                        partyMember.getPacketSender().sendMessage("<col=AF70C3><shad=0>You managed to complete a round of Hard Raids.. Here's your reward!");
                        partyMember.incrementHardVoidCompletions(1);
                        int valExp3 = Misc.random(15000, 23000);
                        if (p.getBattlePass().getType() == BattlePassType.TIER2 || p.getBattlePass().getType() == BattlePassType.TIER1) {
                            p.getBattlePass().addExperience(Misc.random(valExp3));
                            p.msgFancyPurp("You received " + valExp3 + " Battle Pass Experience for a Hard Raid!");
                        }
                        break;
                }

            } else {
                partyMember.getPacketSender().sendMessage("@red@<shad=0>You didn't reach the required killcount to receive a Raids Key.");
            }

            partyMember.setInsideRaids(false);
            partyMember.getRaidsOne().getRaidsConnector().leaveWithMoveVoidRaid();
            partyMember.getRaidsOne().getRaidsConnector().leaveVoid(true);
            partyMember.getRaidsSaving().completedRaids1 = true;
        }
    }
}
