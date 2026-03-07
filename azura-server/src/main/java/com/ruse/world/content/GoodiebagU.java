package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.model.container.impl.Bank;
import com.ruse.model.definitions.ItemDefinition;
import com.ruse.world.World;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.Random;

public class GoodiebagU {
    private Player player;
    public GoodiebagU(Player player) {
        this.player = player;
    }
    private boolean claimed = false;
    public int boxId = -1;

    public static final int[] RARES = {17124, 10724 , 19944, 15792, 15793, 15794, 15795, 15796};

    private int AMULET = 17124;
    private int RING = 10724;
    private int CAPE = 19944;
    private int HAT = 15792;
    private int BODY = 15793;
    private int LEGS = 15794;
    private int GLOVES = 15795;
    private int BOOTS = 15796;
    private int VOID_BLADE = 20000;
    private int VOID_STAFF = 20002;
    private int VOID_BOW = 20001;





    public int[] rewards = {995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995, 995};
    public void setRewards(int[] rewards) {
        this.rewards = rewards;
    }

    public void open() {
        player.getPacketSender().sendInterface(49200);
        player.getPacketSender().resetItemsOnInterface(49270, 20);
        shuffle(rewards);
        claimed = false;
        player.selectedGoodieBagU = -1;

        for (int i = 1; i <= 20; i++) {
            player.getPacketSender().sendString(49232 + i, String.valueOf(i));
        }

    }

    private void shuffle(int[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    private void showRewards() {

        for (int i = 1; i <= 20; i++) {
            player.getPacketSender().sendString(49232 + i, "");
        }

        for (int i = 0; i < rewards.length; i++) {
            player.getPacketSender().sendItemOnInterface(49270, rewards[i], i, 1);
        }
    }

    public boolean handleClick(int buttonId) {
        if (!(buttonId >= -16325 && buttonId <= -16306)) {
            return false;
        }

        if(claimed) {
            return false;
        }

        int index = -1;

        if (buttonId >= -16325) {
            index = 16325 + buttonId;
        }
        player.getPacketSender().sendString(49232 + player.selectedGoodieBagU + 1,
                String.valueOf(player.selectedGoodieBagU + 1));
        player.selectedGoodieBagU = index;
        player.getPacketSender().sendString(49232 + index + 1, "Pick");

        return true;
    }

    public void claim() {
        if (!player.getInventory().contains(4022)){
            return;
        }
        if (player.selectedGoodieBagU == -1) {
            player.sendMessage("@red@You haven't picked a number yet");
            return;
        }

        if (boxId == -1) {
            player.sendMessage("You already opened this box");
            return;
        }
        if (!claimed) {
            if (player.getInventory().contains(boxId)) {
                int reward = ItemDefinition.forId(rewards[player.selectedGoodieBagU]).getId();
                showRewards();
                player.getInventory().delete(boxId, 1);
                Achievements.doProgress(player, Achievements.Achievement.OPEN_10_BOXES, 1);
                Achievements.doProgress(player, Achievements.Achievement.OPEN_50_BOXES, 1);
                Achievements.doProgress(player, Achievements.Achievement.OPEN_100_BOXES, 1);
                Achievements.doProgress(player, Achievements.Achievement.OPEN_250_BOXES, 1);
                Achievements.doProgress(player, Achievements.Achievement.OPEN_500_BOXES, 1);
                Item itemCollected = new Item(rewards[player.selectedGoodieBagU], 1);
                if (player.getInventory().getFreeSlots() == 0) {
                    int tab = Bank.getTabForItem(player, itemCollected);
                    player.getBank(tab).add(itemCollected);
                } else {
                    player.getInventory().add(itemCollected);
                }

                player.sendMessage("<col=AF70C3><shad=0>[Hyperion] You received a @red@<shad=0>" + ItemDefinition.forId(rewards[player.selectedGoodieBagU]).getName() + " <col=AF70C3><shad=0>from the Goodiebag");

                if (reward == VOID_BLADE ||reward == VOID_BOW ||reward == VOID_STAFF ||reward == AMULET || reward == RING || reward == CAPE || reward == HAT || reward == BODY || reward == LEGS || reward == GLOVES || reward == BOOTS){
                    World.sendMessage("<col=AF70C3><shad=0>[OWNER(u)] @red@<shad=0>" + player.getUsername() + "<col=AF70C3><shad=0> pulled @red@<shad=0>" + ItemDefinition.forId(rewards[player.selectedGoodieBagU]).getName() + " <col=AF70C3><shad=0>from an Owner Goodiebag");
                }
                claimed = true;
                boxId = -1;
            } else {
                player.msgRed("You need a goodiebag box to claim the reward");
            }
        } else {
            player.msgRed("You have already claimed the reward for this box");
        }
    }
}
