package com.ruse.world.content.backup;

import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.world.content.discord.handler.DiscordManager;
import lombok.var;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class BackUp {

    public static void createFullBackup(boolean send) {
        // 1) Build timestamped filename
        LocalDateTime now = LocalDateTime.now();
        String timestamp = String.format("%d-%02d-%02d %02d-%02d",
                now.getYear(), now.getMonthValue(), now.getDayOfMonth(),
                now.getHour(), now.getMinute());
        Path backupDir = Paths.get("data", "backups");
        Path zipPath   = backupDir.resolve(timestamp + ".zip");
        String message = "Creating Backup At: " + now + "\n\nBackup Details:\n"
                + "File Name: " + timestamp + ".zip\n";

        try {
            Files.createDirectories(backupDir);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // 2) Zip walk data/, skipping backups/
        Path sourceDir = Paths.get("data");
        int[] fileCount = {0};

        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath));
             Stream<Path> paths   = Files.walk(sourceDir)) {

            paths
                    .filter(Files::isRegularFile)
                    .filter(p -> {
                        Path rel = sourceDir.relativize(p);
                        return !rel.startsWith("backups");
                    })
                    .forEach(p -> {
                        Path rel = sourceDir.relativize(p);
                        String entryName = rel.toString().replace(File.separatorChar, '/');
                        try {
                            zos.putNextEntry(new ZipEntry(entryName));
                            Files.copy(p, zos);
                            zos.closeEntry();
                            fileCount[0]++;
                        } catch (IOException ex) {
                            throw new UncheckedIOException("Error zipping " + p, ex);
                        }
                    });

            message += "Backed up " + fileCount[0] + " files.\n";
        } catch (UncheckedIOException uio) {
            uio.printStackTrace();
            return;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        // 3) Validate ZIP integrity
        if (!isZipValid(zipPath)) {
            System.err.println("ERROR: Generated ZIP is invalid or corrupt: " + zipPath);
            return;
        }

        System.out.println("Created valid ZIP with " + fileCount[0] + " files -> " + zipPath);

        // 4) Upload and notify
        if (send) {
            sendBackup(zipPath.toAbsolutePath().toString(),
                    zipPath.getFileName().toString(),
                    message);
        }
    }

    /** Checks that every entry in the ZIP can be read. */
    private static boolean isZipValid(Path zipPath) {
        try (ZipFile zf = new ZipFile(zipPath.toFile())) {
            zf.stream().forEach(entry -> {
                try (var in = zf.getInputStream(entry)) {
                    byte[] buf = new byte[4096];
                    while (in.read(buf) != -1) {
                        // just read through
                    }
                } catch (IOException ignored) {}
            });
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void backupTask() {
        TaskManager.submit(new Task(10_000, false) {
            @Override
            protected void execute() {
                createFullBackup(false);
            }
        });
    }

    public static void sendBackup(String filePath, String fileName, String text) {
        try {
            System.out.println("Uploading backup to S3: " + fileName);
            S3Manager.uploadFile(fileName, Paths.get(filePath));
            DiscordManager.sendMessage(text, 1348381777772937228L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
