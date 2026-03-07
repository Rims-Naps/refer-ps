package com.ruse.world.content.HolidayTasks;

import com.ruse.util.Misc;

import java.util.Random;

public final class HolidayTaskData {
    public enum EasyTasks {
        FROST_TITAN(1051, Misc.random(15)+15, 25,"Frost Titan"),
        FROST_LORD(1052, Misc.random(15)+15, 25,"Frost Lord"),
        FROST_DEMON(1053, Misc.random(15)+15,25, "Frost Demon");
        EasyTasks(int npcId, int amount, int rewardPt, String npcName) {
            this.npcId = npcId;
            this.amount = amount;
            this.rewardPt = rewardPt;
            this.npcName = npcName;
        }

        public int npcId;
        public int amount;
        public int rewardPt;
        public String npcName;
        public Random rand = new Random();

    }

    public EasyTasks randomizeEasyTasks() {
        Random rand = new Random();
        int randomTask = rand.nextInt(EasyTasks.values().length);
        return EasyTasks.values()[randomTask];
    }

    public enum MediumTasks {
        FROST_FIEND(1054, Misc.random(15)+15, 50,"Frost Fiend"),
        FROST_GUARDIAN(1055, Misc.random(15)+15, 50,"Frost Guardian"),
        FROST_ELEMENTAL(1056, Misc.random(15)+15,50, "Frost Elemental");

        MediumTasks(int npcId, int amount, int rewardPt, String npcName) {
            this.npcId = npcId;
            this.amount = amount;
            this.rewardPt = rewardPt;
            this.npcName = npcName;
        }

        public int npcId;
        public int amount;
        public int rewardPt;
        public String npcName;
    }

    public MediumTasks randomizeMediumTasks() {
        Random rand = new Random();
        int randomTask = rand.nextInt(MediumTasks.values().length);
        return MediumTasks.values()[randomTask];
    }

    public enum EliteTasks {
        FROST_GIANT(1057, Misc.random(15)+15, 100,"Frost Giant"),
        FROST_DRAGON(1058, Misc.random(15)+15, 100,"Frost Dragon"),
        FROST_IMP(1059, Misc.random(15)+15,100, "Frost Imp");
        EliteTasks(int npcId, int amount, int rewardPt, String npcName) {
            this.npcId = npcId;
            this.amount = amount;
            this.rewardPt = rewardPt;
            this.npcName = npcName;
        }

        public int npcId;
        public int amount;
        public int rewardPt;
        public String npcName;

    }

    public EliteTasks randomizeEliteTasks() {
        Random rand = new Random();
        int randomTask = rand.nextInt(EliteTasks.values().length);
        return EliteTasks.values()[randomTask];
    }
}