package com.ruse.world.content;

import com.ruse.model.Item;
import com.ruse.world.entity.impl.npc.NPC;
import com.ruse.world.entity.impl.player.Player;

public class BingoHandler {

    private final Player player;
    private int[][] bingoItems;
    private boolean[][] bingoMarked;
    private static final int RARE_DROP_THRESHOLD = 50;

    public BingoHandler(Player player) {
        this.player = player;
        // Initialize arrays if they're null
        if (this.bingoItems == null) {
            this.bingoItems = new int[5][5];
        }
        if (this.bingoMarked == null) {
            this.bingoMarked = new boolean[5][5];
        }
        // Sync with player's saved data if available
        if (player.getSavedBingoItems() != null) {
            this.bingoItems = player.getSavedBingoItems();
        }
        if (player.getSavedBingoMarked() != null) {
            this.bingoMarked = player.getSavedBingoMarked();
        }
    }

    public void openBingoInterface() {
        initializeBingoBoard();
        player.getPacketSender().sendInterface(45000);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int componentId = 45008 + (row * 5 + col);
                int itemId = bingoItems[row][col];
                boolean marked = bingoMarked[row][col];
                player.getPacketSender().sendMessage("Sending item " + itemId + " to component " + componentId);
                player.getPacketSender().sendItemOnInterface(componentId, itemId, 1);
                int spriteId = marked ? 5748 : 5749;
            //    player.getPacketSender().sendSpriteChange(componentId - 1, spriteId);
            }
        }
    }

    public void handleBingoRareDrop(Item drop, int dropChance, NPC npc) {
        if (dropChance < RARE_DROP_THRESHOLD) {
            return;
        }

        int droppedItemId = drop.getId();
        boolean foundMatch = false;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (bingoItems[row][col] == droppedItemId && !bingoMarked[row][col]) {
                    bingoMarked[row][col] = true;
                    foundMatch = true;
                    player.getPacketSender().sendMessage("Bingo! You received a rare drop (" + drop.getDefinition().getName() + ") that matches your bingo board!");
                    player.getPacketSender().sendMessage("Cell [" + (row + 1) + "," + (col + 1) + "] has been marked.");
                    // Sync with player's saved data
                    player.setSavedBingoMarked(bingoMarked);
                    checkBingoCompletion();
                    break;
                }
            }
            if (foundMatch) {
                break;
            }
        }
    }

    private void checkBingoCompletion() {
        boolean hasBingo = false;
        for (int row = 0; row < 5; row++) {
            boolean rowComplete = true;
            for (int col = 0; col < 5; col++) {
                if (!bingoMarked[row][col]) {
                    rowComplete = false;
                    break;
                }
            }
            if (rowComplete) {
                hasBingo = true;
                player.getPacketSender().sendMessage("Bingo! You've completed a row!");
                break;
            }
        }

        if (!hasBingo) {
            for (int col = 0; col < 5; col++) {
                boolean colComplete = true;
                for (int row = 0; row < 5; row++) {
                    if (!bingoMarked[row][col]) {
                        colComplete = false;
                        break;
                    }
                }
                if (colComplete) {
                    hasBingo = true;
                    player.getPacketSender().sendMessage("Bingo! You've completed a column!");
                    break;
                }
            }
        }

        if (!hasBingo) {
            boolean diagonal1Complete = true;
            boolean diagonal2Complete = true;
            for (int i = 0; i < 5; i++) {
                if (!bingoMarked[i][i]) {
                    diagonal1Complete = false;
                }
                if (!bingoMarked[i][4 - i]) {
                    diagonal2Complete = false;
                }
            }
            if (diagonal1Complete) {
                hasBingo = true;
                player.getPacketSender().sendMessage("Bingo! You've completed a diagonal (top-left to bottom-right)!");
            } else if (diagonal2Complete) {
                hasBingo = true;
                player.getPacketSender().sendMessage("Bingo! You've completed a diagonal (top-right to bottom-left)!");
            }
        }

        if (hasBingo) {
            rewardPlayer();
            resetBingoBoard();
        }
    }

    private void rewardPlayer() {
        player.getInventory().add(new Item(995, 1000000));
        player.getPacketSender().sendMessage("Congratulations! You've completed a bingo and received a reward!");
    }

    private void resetBingoBoard() {
        bingoMarked = new boolean[5][5];
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                bingoItems[row][col] = getRandomBingoItem();
            }
        }
        // Sync with player's saved data
        player.setSavedBingoItems(bingoItems);
        player.setSavedBingoMarked(bingoMarked);
        player.getPacketSender().sendMessage("Your bingo board has been reset with new items!");
    }

    private int getRandomBingoItem() {
        int[] possibleItems = {4151, 6585, 11235, 11732, 13239};
        return possibleItems[new java.util.Random().nextInt(possibleItems.length)];
    }

    private void initializeBingoBoard() {
        if (bingoItems == null) {
            bingoItems = new int[5][5];
        }
        if (bingoMarked == null) {
            bingoMarked = new boolean[5][5];
        }

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (bingoItems[row][col] <= 0) {
                    bingoItems[row][col] = getRandomBingoItem();
                }
            }
        }

        player.setSavedBingoItems(bingoItems);
    }

    public int[][] getBingoItems() {
        return bingoItems;
    }

    public void setBingoItems(int[][] bingoItems) {
        this.bingoItems = bingoItems;

        player.setSavedBingoItems(bingoItems);
    }

    public boolean[][] getBingoMarked() {
        return bingoMarked;
    }

    public void setBingoMarked(boolean[][] bingoMarked) {
        this.bingoMarked = bingoMarked;
        // Sync with player's saved data
        player.setSavedBingoMarked(bingoMarked);
    }
}