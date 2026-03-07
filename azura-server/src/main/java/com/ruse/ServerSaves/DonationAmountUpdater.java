package com.ruse.ServerSaves;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class DonationAmountUpdater {
    private static final String FILE_PATH = "data/serversaves/donobossamount.txt";

    public static void updateDonoBossAmount(double newValue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(Double.toString(newValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}