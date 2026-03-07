package com.ruse.ServerSaves;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SlayerGlobalUpdater {
    private static final String FILE_PATH = "data/serversaves/azgothamount.txt";

    public static void updateAzgothAmount(double newValue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(Double.toString(newValue));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}