package com.ruse.world.content.HolidayTasks;

import com.ruse.model.container.impl.Shop;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HolidayTaskInterface {
    final Player p;
    final int INTERFACE_ID = 63344;

    @Getter
    private HolidayTaskData.EasyTasks easyChallenges;
    @Getter
    private HolidayTaskData.MediumTasks mediumChallenges;
    @Getter
    private HolidayTaskData.EliteTasks eliteChallenges;

    public void displayInterface() {
        p.getPacketSender().sendString(63380, "");
        p.getPacketSender().sendString(63381, "");
        p.getPacketSender().sendString(63382, "");

        if (p.getHolidayTaskHandler().isHasTask()) {
            if (p.getHolidayTaskHandler().getTaskRewardPt() == 25) {
                p.getPacketSender().sendString(63380, "   \nYou're assigned an @gre@Easy task!");
            } else if (p.getHolidayTaskHandler().getTaskRewardPt() == 50) {
                p.getPacketSender().sendString(63380, "   You're assigned an @gre@Medium task!");
            } else if (p.getHolidayTaskHandler().getTaskRewardPt() == 100) {
                p.getPacketSender().sendString(63380, "   You're assigned an @gre@Hard task!");
            }

            p.getPacketSender().sendString(63381, "Kill: @yel@" + p.getHolidayTaskHandler().getTaskAmt());

            if (NpcDefinition.forId(p.getHolidayTaskHandler().getTaskNpc()) != null) {
                p.getPacketSender().sendString(63382, "@red@" + NpcDefinition.forId(p.getHolidayTaskHandler().getTaskNpc()).getName());
            } else {
                p.getPacketSender().sendString(63382, "");
            }
        }
        p.getPacketSender().sendInterface(INTERFACE_ID);
        randomizeAll();
    }

    public HolidayTaskData data = new HolidayTaskData();

    public void randomizeAll() {
        HolidayTaskData data = new HolidayTaskData();
        easyChallenges = data.randomizeEasyTasks();
        mediumChallenges = data.randomizeMediumTasks();
        eliteChallenges = data.randomizeEliteTasks();
    }

    public boolean handleBtn(int btn) {

        if (btn == -2186 || btn == -2183 || btn == -2180) {
            if (p.getHolidayTaskHandler().isHasTask()) {
                DialogueManager.sendStatement(p, "You already have a Frost Task.");
                p.setDidconfirmcanceltask(false);
                return true;
            }
        }

        if (btn == -2171) {
            if (!p.getHolidayTaskHandler().isHasTask()) {
                p.msgRed("You do not have a task!");
                p.setDidconfirmcanceltask(false);
                return true;

            }

            if ((p.getInventory().getAmount(995) >= 1000) && p.getHolidayTaskHandler().isHasTask()) {if (!p.didconfirmcanceltask) {
                p.setDidconfirmcanceltask(true);
                p.msgRed("Click Skip again to skip your task!");
                return true;
            }
                p.getInventory().delete(995, 1000);
                p.getHolidayTaskHandler().setHasTask(false);
                p.getHolidayTaskHandler().setTaskNpc(-1);
                p.msgRed("Your task has been cancelled.");
                p.getPacketSender().sendString(63380, "");
                p.getPacketSender().sendString(63381, "");
                p.getPacketSender().sendString(63382, "");
                p.setDidconfirmchallengetask(false);
                p.setDidconfirmcanceltask(false);
                randomizeAll();
            } else {
                p.msgRed("You need 1k coins to skip your task.");
                p.setDidconfirmchallengetask(false);
                p.setDidconfirmcanceltask(false);
            }
        }

        switch (btn) {
            case -2189:
                p.getPacketSender().sendInterfaceRemoval();
                p.setDidconfirmchallengetask(false);
                p.setDidconfirmcanceltask(false);
                return true;
            case -2186:
                if (p.didconfirmchallengetask) {
                    p.getHolidayTaskHandler().setTaskAmt(easyChallenges.amount);
                    p.getHolidayTaskHandler().setTaskNpc(easyChallenges.npcId);
                    p.getHolidayTaskHandler().setTaskRewardPt(easyChallenges.rewardPt);
                    p.getHolidayTaskHandler().setHasTask(true);
                    p.getPacketSender().sendString(63380, "   You've chosen an Easy task!");
                    p.getPacketSender().sendString(63381, " Defeat: @yel@" + easyChallenges.amount);
                    p.setDidconfirmchallengetask(false);
                    p.setDidconfirmcanceltask(false);

                    if (NpcDefinition.forId(easyChallenges.npcId) != null) {
                        p.getPacketSender().sendString(63382, "@red@" + NpcDefinition.forId(easyChallenges.npcId).getName());
                    }
                }

                p.setDidconfirmchallengetask(true);
                p.setDidconfirmcanceltask(false);
                p.msgRed("@red@<shad=0>Please Confirm your task selection once more!");
                return true;
            case -2183:
                if (p.getEasyXmasTasks() < 100){
                    p.msgRed("@red@<shad=0>You must complete at least 100 Frost tasks first!");
                    p.msgFancyPurp("Frost Tasks: " + p.getEasyXmasTasks());
                    return false;
                }
                if (p.didconfirmchallengetask) {
                    p.getHolidayTaskHandler().setTaskAmt(mediumChallenges.amount);
                    p.getHolidayTaskHandler().setTaskNpc(mediumChallenges.npcId);
                    p.getHolidayTaskHandler().setTaskRewardPt(mediumChallenges.rewardPt);
                    p.getHolidayTaskHandler().setHasTask(true);
                    p.getPacketSender().sendString(63380, "   You've chosen a Medium Task!");
                    p.getPacketSender().sendString(63381, " Defeat: @yel@" + mediumChallenges.amount);

                    if (NpcDefinition.forId(mediumChallenges.npcId) != null) {
                        p.getPacketSender().sendString(63382, "@red@" + NpcDefinition.forId(mediumChallenges.npcId).getName());
                    }
                }
                    p.setDidconfirmchallengetask(true);
                    p.setDidconfirmcanceltask(false);
                    p.msgRed("@red@<shad=0>Please Confirm your task selection once more!");
                return true;
            case -2180:
                if (p.getEasyXmasTasks() < 200){
                    p.msgRed("@red@<shad=0>You must complete at least 200 Frost tasks first!");
                    p.msgFancyPurp("Frost Tasks: " + p.getEasyXmasTasks());
                    return false;
                }
                if (p.didconfirmchallengetask) {
                    p.getHolidayTaskHandler().setTaskAmt(eliteChallenges.amount);
                    p.getHolidayTaskHandler().setTaskNpc(eliteChallenges.npcId);
                    p.getHolidayTaskHandler().setTaskRewardPt(eliteChallenges.rewardPt);
                    p.getHolidayTaskHandler().setHasTask(true);
                    p.getPacketSender().sendString(63380, "   You've chosen an Elite task!");
                    p.getPacketSender().sendString(63381, " Defeat: @yel@" + eliteChallenges.amount);

                    if (NpcDefinition.forId(eliteChallenges.npcId) != null) {
                        p.getPacketSender().sendString(63382, "@red@" + NpcDefinition.forId(eliteChallenges.npcId).getName());
                    }
                }
                    p.setDidconfirmchallengetask(true);
                    p.setDidconfirmcanceltask(false);
                    p.msgRed("@red@<shad=0>Please Confirm your task selection once more!");
                    return true;
            case -2174:
                Shop.ShopManager.getShops().get(Shop.CHRISTMAS_SHOP).open(p);
                p.setDidconfirmchallengetask(false);
                p.setDidconfirmcanceltask(false);
                return true;
        }
        return false;
    }
}
