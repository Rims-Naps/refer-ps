package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.util.TimeUtils;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

public class Streaking {
    @Getter@Setter
    LocalDateTime lastVote;
    @Getter@Setter
    LocalDateTime nextCountableVote;

    @Getter@Setter
    public int currentStreak;

    public static Item[] rewards = new Item[30];

    private Player player;

    public static final String REWARDS_FILE_PATH = "./data/saves/dailyrewards.txt";

    @Getter
    @Setter
    private boolean[] daysVoted = new boolean[30];

    public Streaking(Player player) {
        this.player = player;
        lastVote = LocalDateTime.now();
        nextCountableVote = LocalDateTime.now();
    }

    public void vote() {
        checkReset();
        lastVote = LocalDateTime.now();
        if (LocalDateTime.now().isAfter(nextCountableVote)) {
            nextCountableVote = LocalDateTime.now().plusDays(1);
            handleReward();
            openInterface();
        }

    }

    public void checkReset() {
        if (LocalDateTime.now().isAfter(lastVote.plusDays(2))) {
            reset();
        }
        int counter = 0;
        for (int i = 0; i < daysVoted.length; i++) {
            if (daysVoted[i])
                counter++;
        }
        if (counter == 30) {
            reset();
            player.sendMessage("Your voting streak has reset due to reaching the maximum days.");
        }
    }

    public void handleReward() {
        Item reward = rewards[currentStreak];
        daysVoted[currentStreak] = true;
        player.depositItemBank(reward);
        player.sendMessage("Your Voting Streak rewards have been added to your bank.");
        currentStreak++;
    }

    public void reset() {
        daysVoted = new boolean[30];
        currentStreak = 0;
        lastVote = LocalDateTime.now();
        nextCountableVote = LocalDateTime.now();
    }

    public static void loadRewards() {
        rewards = new Item[30];
        try {
            BufferedReader r = new BufferedReader(new FileReader(REWARDS_FILE_PATH));
            int index = 0;
            while (true) {
                String line = r.readLine();
                if (line == null) {
                    break;
                } else {
                    line = line.trim();
                }
                String[] code = line.split(" : ");

                rewards[index] = new Item(Integer.parseInt(code[0]), Integer.parseInt(code[1]));
                index++;
            }
            r.close();
        } catch (IOException e) {
            System.err.println("Did not load '" + REWARDS_FILE_PATH + "'");
        }
    }

    public void openInterface() {
        checkReset();
        if (LocalDateTime.now().isAfter(nextCountableVote))
            player.getPacketSender().sendString(149006, "@gre@You can vote now for a reward ::vote");
        else
            player.getPacketSender().sendString(149006, "@gre@" + TimeUtils.getTimeRemainingDHM(LocalDateTime.now(), nextCountableVote));

        int inter = 149008;

        for (int i = 0; i < 30; i++) {
            player.getPacketSender().sendSpriteChange(inter++, daysVoted[i] ? 3362 : currentStreak == i ? 3361 : 3541);
            inter++;
            player.getPacketSender().sendItemOnInterface(inter++, rewards[i].getId(), rewards[i].getAmount());
        }

        player.getPacketSender().sendInterface(149000);

        player.sendMessage("<col=AF70C3>You have currently voted a total of: " + currentStreak + " days concurrently!");
    }
}
