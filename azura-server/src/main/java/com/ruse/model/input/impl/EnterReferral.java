package com.ruse.model.input.impl;

import com.ruse.model.definitions.ItemDefinition;
import com.ruse.model.input.Input;
import com.ruse.world.entity.impl.player.Player;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.ruse.world.content.PlayerLogs.getTime;

public class EnterReferral extends Input {



    public static final String[] referral = {
            "jipy","yoda","noobsown","maxi","wr3ckedyou","runelocus","rspslist","vibranium", "perk", "hades"
    };

    public static final String[] walkchaos = {
            "erererwsffsdfsd",
    };

    private static List<String> addValue(List<String> lines, String username) {
        List<String> newLines = new ArrayList<>();
        for (String line : lines) {
            if (line.contains(username)) {
                String[] vals = line.split(" : ");
                newLines.add(vals[0] + " : " + (Integer.parseInt(vals[1]) + 1));
            } else {
                newLines.add(line);
            }

        }
        return newLines;
    }

    public static boolean referralResponse(Player player, String username) {

        if (Arrays.stream(referral).anyMatch(username::equalsIgnoreCase)){
            player.getInventory().add(23058, 1);
            player.getInventory().add(3580, 1);
            player.getInventory().add(1448, 1);
            player.getInventory().add(2706, 5);
            player.sendMessage("<col=3E0957><shad=6C1894>Thank you for joining, here is your special reward!");
            return true;

        }

        return false;
    }

    @SneakyThrows
    public static boolean checkIps(String ip) {
        File file = new File("data/refer/referral_data.txt");
        if (!file.exists())
            file.createNewFile();
        Scanner scanner = new Scanner(file);
        String content;
        int count = 0;
        while (scanner.hasNext()) {
            content = scanner.nextLine();
            String[] split = content.split(" : ");
            if (ip.equalsIgnoreCase(split[0].replaceAll(" ", ""))) {
                count++;
            }
            if (count >= 1) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public static boolean checkMac(String mac) {
        File file = new File("data/refer/referral_data.txt");
        if (!file.exists())
            file.createNewFile();

        Scanner scanner = new Scanner(file);
        String content;
        int count = 0;
        while (scanner.hasNext()) {
            content = scanner.nextLine();
            String[] split = content.split(" : ");
            if (mac.equalsIgnoreCase(split[1])) {
                count++;
            }
            if (count >= 1) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleSyntax(Player player, String syntax) {
        if (checkIps(player.getHostAddress())) {
            player.getPacketSender().sendMessage("You have already received a referral reward!");
            return;
        }
        if (player.getMac() != null && checkMac(player.getMac())) {
            player.getPacketSender().sendMessage("You have already received a referral reward!");
            return;
        }

        if (syntax.startsWith(":")) {
            player.getPacketSender().sendMessage("Please only enter the referral code without any additional command.");
            return;
        }

        if (referralResponse(player, syntax)) {
            try {
                BufferedWriter w = new BufferedWriter(new FileWriter("data/refer/referral_data.txt", true));
                w.write(player.getHostAddress() + " : " + player.getMac() + " : " + player.getUsername() + " : " + getTime() + ": " + syntax);
                w.newLine();
                w.flush();
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                List<String> lines;
                boolean contains = false;
                File f = new File("./data/refer/total.txt");
                if (!f.exists())
                    f.createNewFile();


                lines = Files.readAllLines(f.toPath(), Charset.defaultCharset());

                for (String line : lines) {
                    if (line.split(" : ")[0].equalsIgnoreCase(syntax)) {
                        contains = true;
                    }
                }

                if (contains) {
                    addValue(lines, syntax);
                    Files.write(f.toPath(), addValue(lines, syntax), Charset.defaultCharset());
                } else {
                    FileWriter total = new FileWriter("./data/refer/total.txt", true);
                    if (total != null) {
                        total.write(syntax + " : 1");
                        total.write(System.lineSeparator());
                        total.close();
                    }
                }
            } catch (Exception e) {
                // log.error("Error while writing promo code data", e);
            }

        }
    }

    public static List<String> getReferrals() {
        try {
            List<String> lines;
            File f = new File("./data/refer/total.txt");
            lines = Files.readAllLines(f.toPath(), Charset.defaultCharset());
            return lines;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }
}