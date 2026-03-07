package com.ruse.ServerSaves;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VoteAmountUpdater {
    private static final String FILE_PATH = "data/serversaves/votecount.txt";

    public static void updateVoteCount(double newValue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(Double.toString(newValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}