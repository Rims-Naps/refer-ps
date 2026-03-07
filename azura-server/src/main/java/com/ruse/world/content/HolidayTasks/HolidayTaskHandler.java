package com.ruse.world.content.HolidayTasks;
import com.ruse.GameSettings;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.entity.impl.player.Player;
import lombok.Data;

@Data
public class HolidayTaskHandler {
    final Player p;

    private int XMAS_FRAGMENTS = 1450;
    private int taskNpc;
    private int taskAmt;
    private int taskRewardPt;


    private boolean hasTask;

    public boolean handleNpcDeath(int id) {
        if(!hasTask) return false;
       // if(taskAmt == 0) return false;

        if(id == taskNpc) {
            taskAmt -= 1;
            if(taskAmt <= 0) {
                int totalpoints = 0;

                if (p.getCosmetics().contains(1455)|| p.getEquipment().contains(1455)){ // HAT
                    totalpoints += 1;
                }
                if (p.getCosmetics().contains(1456)|| p.getEquipment().contains(1456)){  // BODY
                    totalpoints += 1;
                }
                if (p.getCosmetics().contains(1457)|| p.getEquipment().contains(1457)){ // LEGS
                    totalpoints += 1;
                }
                if (p.getCosmetics().contains(1458)|| p.getEquipment().contains(1458)){ // GLOVES
                    totalpoints += 1;
                }
                if (p.getCosmetics().contains(1459)|| p.getEquipment().contains(1459)){ // BOOTS
                    totalpoints += 1;
                }
                if (p.getCosmetics().contains(1460)|| p.getEquipment().contains(1460)){ // CAPE
                    totalpoints += 1;
                }
                if (p.getCosmetics().contains(1461) || p.getEquipment().contains(1461)){ // SCYTHE
                    totalpoints += 1;
                }
                if (p.getEquipment().contains(1462)|| p.getEquipment().contains(1462)){// SNOWBALL
                    totalpoints += 1;
                }
                if (p.getEquipment().contains(1463)|| p.getEquipment().contains(1463)){ // WAND
                    totalpoints += 1;
                }
                if (p.getEquipment().contains(1466)|| p.getEquipment().contains(1466)){// BOOK
                    totalpoints += 1;
                }

                String message = "<col=AF70C3><shad=0>You have completed your Frost task and earned @red@<shad=0>" + (taskRewardPt + totalpoints) + " <col=AF70C3><shad=0>Frost Fragments.";
                p.getInventory().add(XMAS_FRAGMENTS,taskRewardPt + totalpoints);
                p.setEasyXmasTasks(p.getEasyXmasTasks() + 1);

                switch (taskRewardPt) {
                    case 25:
                        p.msgFancyPurp("Frost Tasks: " + p.getEasyXmasTasks());
                        break;
                    case 50:
                        p.msgFancyPurp("Frost Tasks: " + p.getEasyXmasTasks());
                        break;
                    case 100:
                        p.msgFancyPurp("Frost Tasks: " + p.getEasyXmasTasks());
                        break;
                    }

                    p.sendMessage(message);
                    taskNpc = -1;
                    hasTask = false;
                }
                return true;
            }
            return false;
        }
    }