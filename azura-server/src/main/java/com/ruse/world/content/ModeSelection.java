package com.ruse.world.content;

import com.ruse.GameSettings;
import com.ruse.engine.task.Task;
import com.ruse.engine.task.TaskManager;
import com.ruse.model.*;
import com.ruse.world.World;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.entity.impl.player.Player;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class ModeSelection {

    private final Player p;
    private GameMode selectedMode;
    private XpMode chosenxp;
    private int selectedModeIndex = 0;
    private int selectedXpIndex = 0;
    private GameMode[] availableModes = {GameMode.NORMAL, GameMode.IRONMAN, GameMode.GROUP_IRON};
    private XpMode[] availableXpModes = {XpMode.EASY, XpMode.MEDIUM, XpMode.ELITE, XpMode.MASTER};
    private BoostMode[] availBoostModes = {BoostMode.SINISTER, BoostMode.DIVINE};
    private int selectedBoostIndex = 0;
    private BoostMode chosenboost;

    private void selectMode(GameMode mode) {
        if (mode == selectedMode)
            return;
        selectedMode = mode;
    }
    private void updateInterface() {
        if (!p.newPlayer()){
            return;
        }

            if (selectedModeIndex == 0){
                p.getPA().sendString(48415, "Normal");
                p.getPA().sendString(48421, "@whi@Trade with Players and Use POS");
                selectedMode = GameMode.NORMAL;
            }

            if (selectedModeIndex == 1){
                p.getPA().sendString(48415, "Ironman");
                p.getPA().sendString(48421, "@red@No Trading and No use of POS");
                selectedMode = GameMode.IRONMAN;
            }

            if (selectedModeIndex == 2){
                p.getPA().sendString(48415, "Group");
                p.getPA().sendString(48421, "@whi@Group Ironman with 5 Players");
                selectedMode = GameMode.GROUP_IRON;
            }

            if (selectedXpIndex == 0){
                p.getPA().sendString(48416, "@gre@Easy");
                p.getPA().sendString(48422, "@gre@100x XP Rates / 1% Crit");
                chosenxp = XpMode.EASY;
            }

            if (selectedXpIndex == 1){
                p.getPA().sendString(48416, "@or2@Medium");
                p.getPA().sendString(48422, "@or2@50x XP Rates / 10% Damage / 3% Crit");
                chosenxp = XpMode.MEDIUM;
            }

            if (selectedXpIndex == 2){
                p.getPA().sendString(48416, "@yel@Elite");
                p.getPA().sendString(48422, "@yel@25x XP Rates / 15% Damage / 5% Crit");
                chosenxp = XpMode.ELITE;
            }

            if (selectedXpIndex == 3){
                p.getPA().sendString(48416, "@red@Master");
                p.getPA().sendString(48422, "@red@5x XP Rates / 20% Damage / 8% Crit");
                chosenxp = XpMode.MASTER;
            }

        if (selectedBoostIndex == 0){
            p.getPA().sendString(48417, "@red@Sinister");
            p.getPA().sendString(48423, "@red@2% Additional Crit" );
            chosenboost = BoostMode.SINISTER;

        }

        if (selectedBoostIndex == 1){
            p.getPA().sendString(48417, "@gre@Divine");
            p.getPA().sendString(48423, "@gre@4% Base Droprate" );
            chosenboost = BoostMode.DIVINE;
        }
    }

    private void cycleMode() {
        selectedModeIndex = (selectedModeIndex + 1) % availableModes.length;
    }
    private void cycleXp() {
        selectedXpIndex = (selectedXpIndex + 1) % availableXpModes.length;
    }

    private void cycleBoost() {
        selectedBoostIndex = (selectedBoostIndex + 1) % availBoostModes.length;
    }
    public boolean isButton(int id) {
        if (id == -17134) {
            cycleMode();
            updateInterface();
            System.out.println("" + selectedMode);
            return true;
        }
        if (id == -17131) {
            cycleXp();
            updateInterface();
            System.out.println("" + selectedXpIndex);
            return true;
        }
        if (id == -17128) {
            cycleBoost();
            updateInterface();
            System.out.println("" + selectedBoostIndex);
            return true;
        }
        if (id == -17111) {
            confirm();
            return true;
        }
        return false;
    }
    public void open() {
        selectMode(GameMode.NORMAL);
        p.getPA().sendInterface(48400);
    }
    private boolean confirmedOnce = false;
    private boolean confirmedtwice = false;

    private void confirm() {
        if (!p.newPlayer()) {
            return;
        }
        Position tutorialstart = new Position(2013, 3388, 0);
        int totalcrit = 0;
        int totaldr = 0;
        if (selectedMode == null)
            return;
        if (chosenxp == null)
            return;
        if (chosenboost == null)
            return;
        if (p.didReceiveStarter()) {
            return;
        }

        if (!confirmedOnce) {
            confirmedOnce = true;
            p.getPA().sendMessage("<shad=0>@red@Please double check your selection and hit confirm again!");
            return;
        }
        if (!confirmedtwice) {
            confirmedtwice = true;
            p.getPA().sendMessage("<shad=0>@red@Check your selected settings one Last Time!!");
            return;
        }

        p.setGameMode(selectedMode);
        if (selectedMode == GameMode.IRONMAN) {
            totaldr += 6;
        }
        p.setXpMode(chosenxp);
        if (chosenxp == XpMode.EASY) {
            totalcrit += 1;
        }
        if (chosenxp == XpMode.MEDIUM) {
            totalcrit += 3;
        }
        if (chosenxp == XpMode.ELITE) {
            totalcrit += 5;
        }
        if (chosenxp == XpMode.MASTER) {
            totalcrit += 8;
        }

        p.setBoostMode(chosenboost);
        if (chosenboost == BoostMode.DIVINE) {
            totaldr += 10;
        }
        if (chosenboost == BoostMode.SINISTER) {
            totalcrit += 2;
        }

        p.setCritchance(totalcrit);
        p.setDrBoost(totaldr);
        p.getEquipment().clear();
        p.getEquipment().refreshItems();
        p.getUpdateFlag().flag(Flag.APPEARANCE);
        p.getInventory().add(995, 250);
        p.getPA().sendMessage("You have selected your game mode!");
        p.getPacketSender().sendInterfaceRemoval();
        p.setReceivedStarter(true);
        ClanChatManager.join(p, "v");
        p.setPlayerLocked(false);
        p.getAppearance().setCanChangeAppearance(true);
        p.setNewPlayer(false);
        World.sendMessage("@bla@<shad=9B0CFF>[Welcome]@bla@<shad=9B0CFF> " + p.getUsername() + "@bla@<shad=9B0CFF> logged into Athens for the first time!");
        p.getPacketSender().sendRights();
        p.setPrayerbook(Prayerbook.CURSES);
        p.getPacketSender().sendTabInterface(GameSettings.PRAYER_TAB, p.getPrayerbook().CURSES.getInterfaceId());
        p.setReceivedprayerunlock(true);
        p.setReceivedarmor(true);
        p.setTutTask1Started(true);
        p.setTutTask1Ready(true);
        p.setTutTask1Complete(true);
        p.setTutTask2Started(true);
        p.setTutTask2Ready(true);
        p.setTutTask2Complete(true);
        p.setTutTask3Started(true);
        p.setTutTask3Ready(true);
        p.setTutTask3Complete(true);
        p.setTutTask4Started(true);
        p.setTutTask4Ready(true);
        p.setTutTask4Complete(true);
        p.setMadeCritPotion(true);
        p.setMadeDropratePotion(true);
        p.setMadeDamagePotion(true);
        p.setTutTask5Started(true);
        p.setTutTask5Ready(true);
        p.setTutTask5Complete(true);
        p.setTutTask6Started(true);
        p.setTutTask6Ready(true);
        p.setTutTask6Complete(true);
        p.setDefeatedbeast(true);
        p.setCompletedtutorial(true);
        p.setVgTutStage(10);

        p.getNoob().append();

    }
}

