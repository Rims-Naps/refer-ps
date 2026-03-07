package com.ruse.ServerSaves;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DailyBossReader {
    private static final String FILE_PATH = "data/serversaves/dailybosses.txt";

    public static double getDailyBosses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String value = reader.readLine();
            if (value != null && !value.isEmpty()) {
                return Double.parseDouble(value);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 0; // Default value if the file cannot be read or contains invalid data
    }
}
