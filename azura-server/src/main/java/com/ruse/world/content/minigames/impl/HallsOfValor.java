package com.ruse.world.content.minigames.impl;

import com.ruse.model.Item;
import com.ruse.model.Locations;
import com.ruse.model.Position;
import com.ruse.model.RegionInstance;
import com.ruse.world.World;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.content.casketopening.Box;
import com.ruse.world.content.casketopening.BoxLoot;
import com.ruse.world.content.dailytasks_new.DailyTask;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.npc.impl.HoVMobs.*;
import com.ruse.world.entity.impl.player.Player;

public class HallsOfValor {

   /* public static final Position[] NPC_SPAWNS = {
            new Position(2217, 5032, 0),
            new Position(2197, 5016, 0),
            new Position(2784, 3352, 0),
            new Position(2185, 5047, 0),
            new Position(2229, 5001, 0),
    };
    /*public static final NPC[] HoVNPC = {
            new Skreeg(9025, NPC_SPAWNS[0]),
            new Orix(9026, NPC_SPAWNS[1]),
            new CrystalOrc(9027, NPC_SPAWNS[2]),
            new Kiljaeden(9024, NPC_SPAWNS[3]),
            new Heimdall(9814, NPC_SPAWNS[4])};*/



        /*player.setRegionInstance(new RegionInstance(player, RegionInstance.RegionInstanceType.HALLS_OF_VALOR));
        for (NPC newNpc : HoVNPC) {
            NPC npc = new NPC(newNpc.getId(), newNpc.getPosition().copy());
            if (newNpc.getId() == 9025){
                npc = new Skreeg(newNpc.getId(), newNpc.getPosition().copy());
            }else if (newNpc.getId() == 9026){
                npc = new Orix(newNpc.getId(), newNpc.getPosition().copy());
            }else if (newNpc.getId() == 9027){
                npc = new CrystalOrc(newNpc.getId(), newNpc.getPosition().copy());
            }else if (newNpc.getId() == 9024){
                npc = new Kiljaeden(newNpc.getId(), newNpc.getPosition().copy());
            }else if (newNpc.getId() == 9814){
                npc = new Heimdall(newNpc.getId(), newNpc.getPosition().copy());
            }
            npc.setForceAggressive(true);
            npc.setAggressive(true);
            npc.getPosition().setZ(player.getPosition().getZ());
            npc.getCombatBuilder().setAttackTimer(3);
            npc.setSpawnedFor(player);
            npc.getDefinition().setRespawnTime(-1);
            World.register(player, npc);
           // player.getRegionInstance().getNpcsList().add(newNpc);
        }*/



        /*if (killed) {
            player.getMinigameAttributes().getHallsOfValorAttributes().setKillcount(player.getMinigameAttributes().getHallsOfValorAttributes().getKillcount() + 1);
            if (player.getRegionInstance() != null) {
                player.getRegionInstance().getNpcsList().remove(n);
            }
            player.setHovKilled(player.getHovKilled() + 1);
            player.sendMessage("current KC " + player.getMinigameAttributes().getHallsOfValorAttributes().getKillcount());
            if (player.getMinigameAttributes().getHallsOfValorAttributes().getKillcount() > 4) {
                resetBarrows(player);
                player.getInventory().add(KEY_REWARD);
                if (player.getRegionInstance() != null) {
                    player.getRegionInstance().getNpcsList().remove(player);
                }
                player.moveTo(HallsOfValor.TELEPORT_OUT);
            }
        }*/

}
