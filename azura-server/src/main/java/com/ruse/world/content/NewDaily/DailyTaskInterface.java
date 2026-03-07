package com.ruse.world.content.NewDaily;

import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author John (Concerned)
 */
@RequiredArgsConstructor
public class DailyTaskInterface {
    final Player p;
    DailyTask current = DailyTask.task1;
    Category category;
    @Getter
    private static List<DailyTask> VALUES = Collections.unmodifiableList(Arrays.asList(DailyTask.values()));

    public void randomizeTask(int id) {
        List<DailyTask> temp = new ArrayList<>();
        VALUES.forEach(dailyTask -> {
            if (dailyTask.id == id) {
                temp.add(dailyTask);
            }
        });
        DailyTask randomEntry = Misc.random(temp);

        new DailyEntry(randomEntry.id, 0, randomEntry.taskName, false, false, randomEntry.npcId, randomEntry.amountNeeded, randomEntry.catagory, false).submit(p);

    }

    public void display() {
        category = Category.EASY;
        p.getPacketSender().sendConfig(1098, 0);
        p.getPacketSender().sendSpriteChange(4579, 1233);
        p.getPacketSender().sendInterfaceDisplayState(4580, true);
        updateInterface();
        p.getPacketSender().sendInterface(4509);
    }

    public void changeCatagory(int btn) {
        if (btn == 4515) {
            category = Category.EASY;
        } else if (btn == 4516) {
            category = Category.MEDIUM;
        } else if (btn == 4517) {
            category = Category.ELITE;
        } else if (btn == 4518) {
            category = Category.MASTER;
        }
        updateInterface();
    }

    public void updateInterface() {
        VALUES.forEach(dailyTask -> {

            p.getPacketSender().sendProgressBar1(0, dailyTask.progressBarId);
            if (dailyTask.catagory == category) {
                if (p.getDailyEntries().stream().anyMatch(data -> data.getNpcId() == dailyTask.npcId)) {
                    p.getPacketSender().sendString(dailyTask.stringId, dailyTask.taskDescription);
                } else if (dailyTask.npcId == -1) {
                    p.getPacketSender().sendString(dailyTask.stringId, dailyTask.taskDescription);
                }
            }
        });
        VALUES.forEach(dailyTask -> p.getDailyEntries().forEach(dailyEntry -> {
            if (dailyEntry.getTaskId() == dailyTask.id && dailyEntry.getCatagory() == category) {
                double percent = (((double)dailyEntry.getProgress()/(double)dailyEntry.getAmountNeeded())*100D);
                if (dailyEntry.getTaskId() == 19)
                    System.out.println("Percent: "+percent);
                // System.out.println("Sent progress: "+dailyEntry.getProgress());
                p.getPacketSender().sendProgressBar1((int)percent, dailyTask.progressBarId);
            }
        }));
    }

    public void viewReward(int btnId) {
        if (current != null) {
            CopyOnWriteArrayList<Item> rewardItems = new CopyOnWriteArrayList<>();
            VALUES.forEach(dailyTask -> {
                if (dailyTask.rewardButtonId == btnId && category == dailyTask.catagory) {
                    for (Item reward : dailyTask.reward) {
                        if (dailyTask.npcId == -1) {
                            rewardItems.add(reward);
                        } else {
                            if (p.getDailyEntries().stream().anyMatch(data -> data.getNpcId() == dailyTask.npcId)) {
                                rewardItems.add(reward);
                            }
                        }
                    }
                }
            });
            p.getPacketSender().sendInterfaceItems(4586, rewardItems);
            p.getPacketSender().sendSpriteChange(4579, 1528);
            p.getPacketSender().sendInterfaceDisplayState(4580, false);
            rewardItems.clear();
        }
    }

    public void getTask(int btn) {
        VALUES.forEach(dailyTask -> {
            if (dailyTask.acceptTaskButtonId == btn && category == dailyTask.catagory) {

                if (p.getDailyEntries().stream().anyMatch(data -> data.getTaskId() == dailyTask.id && !data.isHasStarted() && data.getNpcId() == dailyTask.npcId)) {
                    /**
                     * Accept the randomized kill task
                     * if isHasStarted is false then it was randomly assigned
                     */

                    p.getDailyEntries().stream().filter(data -> data.getTaskId() == dailyTask.id && !data.isHasStarted() && data.getNpcId() == dailyTask.npcId).findFirst().get().setHasStarted(true);
                    p.sendMessage("You have accepted your task");

                    return;

                } else if (p.getDailyEntries().stream().anyMatch(data -> data.getTaskId() == dailyTask.id && data.isHasStarted() && data.getNpcId() == dailyTask.npcId)) {
                    p.sendMessage("You have already accepted this task.");

                    return;


                } else if (dailyTask.npcId == -1) {
                    p.sendMessage("You have accepted a daily task.");
                    new DailyEntry(dailyTask.id, 0, dailyTask.taskName, true, false, dailyTask.npcId, dailyTask.amountNeeded, dailyTask.catagory, false).submit(p);
                }
            }
        });
    }

    public void acceptAll() {
        VALUES.forEach(dailyTask -> {
            if (p.getDailyEntries().stream().anyMatch(data -> data.getTaskId() == dailyTask.id && data.isHasStarted())) {
                return;
            }
            new DailyEntry(dailyTask.id, 0, dailyTask.taskName, true, false, dailyTask.npcId, dailyTask.amountNeeded, dailyTask.catagory, false).submit(p);
        });
        p.sendMessage("@red@You have accepted all of the daily tasks.");
    }

    public boolean handleBtnClick(int id) {
        if (id == 4583) {
            p.getPacketSender().sendSpriteChange(4579, 1233);
            p.getPacketSender().sendInterfaceDisplayState(4580, true);
        } else if (id == 4511) {
            p.getPacketSender().sendInterfaceRemoval();
        }

        switch (id) {
            case 4530:
            case 4536:
            case 4542:
            case 4548:
            case 4554:
                getTask(id);
                return true;
            //case 31616:
            // acceptAll();
            // break;
            case 4533:
            case 4539:
            case 4545:
            case 4551:
            case 4557:
                viewReward(id);
                return true;
            case 4515:
            case 4516:
            case 4517:
            case 4518:
                changeCatagory(id);
                return true;
        }

        return false;
    }

    public enum Category {
        EASY, MEDIUM, ELITE, MASTER
    }

    public enum DailyTask {

        task1("easy_Task_1", "Open 5 Exotic Keys", new Item[]{new Item(6833, 1)}, 4533, 4530,
                4572, 0, Category.EASY, 4567, -1, 5),
        task2("easy_Task_2", "Kill 100 Xerath", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 9463, 100),
        task2v1("easy_Task_2", "Kill 100 Gorgon", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 13458, 100),
        task2v2("easy_Task_2", "Kill 100 Asmodai", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 438, 100),
        task2v3("easy_Task_2", "Kill 100 Mantus", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 12228, 100),
        task2v4("easy_Task_2", "Kill 100 Loki", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 9026, 100),
        task2v5("easy_Task_2", "Kill 100 Chenoo", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 13747, 100),
        task2v6("easy_Task_2", "Kill 100 Chimaera", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 1741, 100),
        task2v8("easy_Task_2", "Kill 100 Gorgon", new Item[]{new Item(19025, 2)}, 4539, 4536,
                4573, 1, Category.EASY, 4568, 13458, 100),
        task3("easy_Task_3", "Claim 5 Vote Scrolls", new Item[]{new Item(8018, 1)}, 4545, 4542,
                4574, 2, Category.EASY, 4569, -1, 5),
        task4("easy_Task_4", "Forge a Luck Amulet", new Item[]{new Item(6833, 1)}, 4551, 4548,
                4575, 3, Category.EASY, 4570, -1, 1),
        task5("easy_Task_5", "Finish 10 Beast Tasks", new Item[]{new Item(19025, 2)}, 4557, 4554,
                4576, 4, Category.EASY, 4571, -1, 10),

        task6("medium_Task_1", "Open 10 Mystery Boxes", new Item[]{new Item(6833)}, 4533, 4530,
                4572, 5, Category.MEDIUM, 4567, -1, 10),
        task7("medium_Task_2", "Finish 100 Beast Tasks", new Item[]{new Item(19025, 4)}, 4539, 4536,
                4573, 6, Category.MEDIUM, 4568, -1, 100),
        task8("medium_Task_3", "Open 10 Beast Chests", new Item[]{new Item(10946, 1)}, 4545, 4542,
                4574, 7, Category.MEDIUM, 4569, -1, 10),
        task9("medium_Task_4", "Answer a trivia question", new Item[]{new Item(19025, 2)}, 4551, 4548,
                4575, 8, Category.MEDIUM, 4570, -1, 1),
        task10("medium_Task_5", "Kill 500 Drakk", new Item[]{new Item(19025, 3)}, 4557, 4554,
                4576, 9, Category.MEDIUM, 4571, 4412, 500),
        task10v1("medium_Task_5", "Kill 500 Eblis", new Item[]{new Item(19025, 3)}, 4557, 4554,
                4576, 9, Category.MEDIUM, 4571, 9802, 500),
        task10v2("medium_Task_5", "Kill 500 Kazan", new Item[]{new Item(19025, 3)}, 4557, 4554,
                4576, 9, Category.MEDIUM, 4571, 9811, 500),
        task10v3("medium_Task_5", "Kill 500 Treron", new Item[]{new Item(19025, 3)}, 4557, 4554,
                4576, 9, Category.MEDIUM, 4571, 8549, 500),
        task10v4("medium_Task_5", "Kill 500 Hecate", new Item[]{new Item(19025, 3)}, 4557, 4554,
                4576, 9, Category.MEDIUM, 4571, 8006, 500),
        task10v5("medium_Task_5", "Kill 500 Ladon", new Item[]{new Item(19025, 3)}, 4557, 4554,
                4576, 9, Category.MEDIUM, 4571, 9806, 500),
        task10v6("medium_Task_5", "Kill 500 Tokage", new Item[]{new Item(19025, 3)}, 4557, 4554,
                4576, 9, Category.MEDIUM, 4571, 9807, 500),


        task11v1("hard_Task_1", "Kill 1000 Xilon", new Item[]{new Item(19025, 10)}, 4533, 4530,
                4572, 10, Category.ELITE, 4567, 1700, 1000),
        task11v2("hard_Task_1", "Kill 1000 Charon", new Item[]{new Item(19025, 10)}, 4533, 4530,
                4572, 10, Category.ELITE, 4567, 1703, 1000),
        task11v3("hard_Task_1", "Kill 1000 Gandaka", new Item[]{new Item(19025, 10)}, 4533, 4530,
                4572, 10, Category.ELITE, 4567, 8018, 1000),
        task11v4("hard_Task_1", "Kill 1000 Harpy", new Item[]{new Item(19025, 10)}, 4533, 4530,
                4572, 10, Category.ELITE, 4567, 1701, 1000),
        task11v7("hard_Task_1", "Kill 1000 Mystique", new Item[]{new Item(19025, 10)}, 4533, 4530,
                4572, 10, Category.ELITE, 4567, 1702, 1000),
        task11v9("hard_Task_1", "Kill 1000 Gularg", new Item[]{new Item(19025, 10)}, 4533, 4530,
                4572, 10, Category.ELITE, 4567, 6363, 1000),
        task12("hard_Task_2", "Attend 20 Global Bosses", new Item[]{new Item(19025, 5)}, 4539, 4536,
                4573, 11, Category.ELITE, 4568, -1, 20),
        task13("hard_Task_3", "Pull Rare from Exotic Chest", new Item[]{new Item(19025, 5)}, 4545, 4542,
                4574, 12, Category.ELITE, 4569, -1, 1),
        task14("hard_Task_4", "Finish 200 Beast Tasks", new Item[]{new Item(995)}, 4551, 4548,
                4575, 13, Category.ELITE, 4570, -1, 200),
        task15("hard_Task_5", "Open 20 Darkness Chests", new Item[]{new Item(8018, 1)}, 4557, 4554,
                4576, 14, Category.ELITE, 4571, -1, 20),
        task16("master_Task_1", "Kill 500 Master Bosses", new Item[]{new Item(8018), new Item(19025, 10)}, 4533, 4530,
                4572, 15, Category.MASTER, 4567, -1, 500),
        task17("master_Task_2", "Complete 10 Trials", new Item[]{new Item(19025, 3)}, 4539, 4536,
                4573, 16, Category.MASTER, 4568, -1, 10),
        task18("master_Task_3", "Kill Galaxy 5 Times", new Item[]{new Item(6833)}, 4545, 4542,
                4574, 17, Category.MASTER, 4569, -1, 5),
        task19("master_Task_4", "Crush 2 Offerings", new Item[]{new Item(6833)}, 4551, 4548,
                4575, 18, Category.MASTER, 4570, -1, 2),
        task20("master_Task_5", "Kill 10,000 Npcs", new Item[]{new Item(19025, 5)}, 4557, 4554,
                4576, 19, Category.MASTER, 4571, -1, 10000);

        DailyTask(String taskName, String taskDescription, Item[] reward, int rewardButtonId, int acceptTaskButtonId, int stringId, int id, Category catagory, int progressBarId, int npcId, int amountNeeded) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.reward = reward;
            this.rewardButtonId = rewardButtonId;
            this.acceptTaskButtonId = acceptTaskButtonId;
            this.stringId = stringId;
            this.id = id;
            this.catagory = catagory;
            this.progressBarId = progressBarId;
            this.npcId = npcId;
            this.amountNeeded = amountNeeded;
        }

        private String taskName;
        private String taskDescription;
        @Getter
        private Item[] reward;
        private int rewardButtonId;
        private int acceptTaskButtonId;
        private int stringId;
        @Getter
        private int id;
        private Category catagory;
        private int progressBarId;
        private int npcId;
        private int amountNeeded;
    }

    public void NpcKillsCompleted(int npcId, int id, int progress) {
        p.getDailyEntries().forEach(dailyEntry -> {
            if (dailyEntry.getTaskId() == id && dailyEntry.getNpcId() == npcId && dailyEntry.isHasStarted()) {
                if (dailyEntry.isCompleted())
                    return;

                // p.sendMessage("You have worked towards your daily task");
                dailyEntry.setProgress(dailyEntry.getProgress() + progress);
                if (dailyEntry.getProgress() >= dailyEntry.getAmountNeeded()) {
                    dailyEntry.setProgress(dailyEntry.getAmountNeeded());
                }
                if (dailyEntry.getProgress() == dailyEntry.getAmountNeeded()) {
                    DailyTaskInterface.getVALUES().forEach(dailyTask -> {
                        if (dailyTask.getId() == dailyEntry.getTaskId() && dailyEntry.getNpcId() == dailyTask.npcId && dailyEntry.getCatagory() == dailyTask.catagory) {
                            for (Item reward : dailyTask.getReward()) {
                                p.giveItem(reward.getId(), reward.getAmount());
                            }
                            dailyEntry.setHasReward(true);
                            p.sendMessage("@red@You've been given your daily task reward.");
                        }
                    });
                    dailyEntry.setCompleted(true);
                }
            }
        });
    }

    public void MiscTasksCompleted(int id, int progress) {
        p.getDailyEntries().forEach(dailyEntry -> {
            if (dailyEntry.getTaskId() == id && dailyEntry.isHasStarted()) {
                if (dailyEntry.isCompleted())
                    return;

                //  p.sendMessage("You have worked towards your daily task");
                dailyEntry.setProgress(dailyEntry.getProgress() + progress);
                if (dailyEntry.getProgress() > dailyEntry.getAmountNeeded()) {
                    dailyEntry.setProgress(dailyEntry.getAmountNeeded());
                }
                if (dailyEntry.getProgress() == dailyEntry.getAmountNeeded()) {
                    DailyTaskInterface.getVALUES().forEach(dailyTask -> {
                        if (dailyTask.getId() == dailyEntry.getTaskId() && dailyTask.npcId == dailyEntry.getNpcId() && dailyEntry.getCatagory() == dailyTask.catagory) {
                            for (Item reward : dailyTask.getReward()) {
                                p.giveItem(reward.getId(), reward.getAmount());
                            }
                            dailyEntry.setHasReward(true);
                            p.sendMessage("@red@You've been given your daily task reward.");
                        }
                    });
                    dailyEntry.setCompleted(true);
                }

            }
        });
    }
}
