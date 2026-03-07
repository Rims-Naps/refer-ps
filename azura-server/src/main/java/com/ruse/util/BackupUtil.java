package com.ruse.util;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BackupUtil {

    private static final String BACKUP_DIR = "./backups/";
    private static final String CHARS_DIR = "./data/characters/";

    /**
     * Performs a backup of player character files to a timestamped directory.
     * 
     * @return true if the backup completed successfully, false otherwise
     */
    public static boolean backup() {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            Path backupPath = Paths.get(BACKUP_DIR + "backup_" + timestamp);
            Files.createDirectories(backupPath);

            Path source = Paths.get(CHARS_DIR);
            if (!Files.exists(source)) {
                System.out.println("[BackupUtil] Source directory not found: " + CHARS_DIR);
                return false;
            }

            Files.walk(source).forEach(src -> {
                try {
                    Path dest = backupPath.resolve(source.relativize(src));
                    if (Files.isDirectory(src)) {
                        Files.createDirectories(dest);
                    } else {
                        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    System.out.println("[BackupUtil] Error copying file: " + e.getMessage());
                }
            });

            System.out.println("[BackupUtil] Backup completed: " + backupPath);
            return true;
        } catch (Exception e) {
            System.out.println("[BackupUtil] Backup failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
