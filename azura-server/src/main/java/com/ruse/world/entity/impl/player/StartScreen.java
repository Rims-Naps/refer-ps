package com.ruse.world.entity.impl.player;

import com.ruse.GameSettings;
import com.ruse.model.GameMode;
import com.ruse.model.Item;
import com.ruse.model.Position;
import com.ruse.world.World;
import com.ruse.world.content.PlayerPanel;
import com.ruse.world.content.clan.ClanChatManager;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.impl.Tutorial;

/**
 * Start screen functionality.
 *
 * @author Joey wijers
 */
public class StartScreen {
    public static void open(Player player) {
        sendNames(player);
        player.getPacketSender().sendInterface(116000);
        player.getPacketSender().sendConfig(5331, 0);
        player.selectedGameMode = GameModes.NORMAL;
        check(player);
        sendStartPackItems(player, GameModes.NORMAL);
        sendDescription(player, GameModes.NORMAL);
    }

    public static void sendDescription(Player player, GameModes mode) {
        player.getPacketSender().sendString(116101,  mode.line1 + "\\n" + mode.line2 + "\\n" + mode.line3 + "\\n" + mode.line4 + "\\n" + mode.line5 + "\\n" + mode.line6 );
    }

    public static void sendStartPackItems(Player player, GameModes mode) {
        final int START_ITEM_INTERFACE = 116201;
        for (int i = 0; i < 28; i++) {
            int id = -1;
            int amount = 0;
            try {
                id = mode.starterPackItems[i].getId();
                amount = mode.starterPackItems[i].getAmount();
            } catch (Exception e) {

            }
            player.getPacketSender().sendItemOnInterface(START_ITEM_INTERFACE + i, id, amount);
        }
    }

    public static boolean handleButton(Player player, int buttonId) {
        final int CONFIRM = 116010;
        if (buttonId == CONFIRM) {
            if (player.didReceiveStarter()) {
                return true;
            }
            player.getPacketSender().sendInterfaceRemoval();
            player.setReceivedStarter(true);
            handleConfirm(player);
            addStarterToInv(player);
            ClanChatManager.join(player, "v");
            player.setPlayerLocked(false);
            player.getAppearance().setCanChangeAppearance(true);
            player.setNewPlayer(false);
            World.sendMessage("<img=4><shad=1><col=0><shad=6C1894>[Welcome] " + player.getUsername() + " logged into @red@Athens<col=500D17> for the first time!");
            World.sendMessage("<img=4><col=0><shad=6C1894>Everyone Welcome " + player.getUsername() + " to Athens!");
            player.getPacketSender().sendRights();

            if (player.getGameMode() == GameMode.GROUP_IRON) {
                player.moveTo(new Position(3106, 3475, 0));
                player.setGroupIronmanLocked(true);
                player.sendMessage("<col=AF70C3>Speak to the Wolf Den Leader to create a group or get invited to a group.");
            } else {
                player.moveTo(GameSettings.STARTER);
            }
            DialogueManager.start(player, Tutorial.get(player, 0));
            return true;
        }
        for (GameModes mode : GameModes.values()) {
            if (mode.checkClick == buttonId || mode.textClick == buttonId) {
                selectMode(player, mode);
                return true;
            }
        }
        return false;

    }

    public static void handleConfirm(Player player) {

        // System.out.println("Game mode: " + player.selectedGameMode);

        if (player.selectedGameMode == GameModes.NORMAL) {
        } else {
            GameMode.set(player, GameMode.NORMAL, false);
        }
        PlayerPanel.refreshPanel(player);

    }

    public static void addStarterToInv(Player player) {
        for (Item item : player.selectedGameMode.starterPackItems) {
            player.getInventory().add(item);
        }
    }

    public static void selectMode(Player player, GameModes mode) {
        player.selectedGameMode = mode;
        check(player);
        sendStartPackItems(player, mode);
        sendDescription(player, mode);
    }

    public static void check(Player player) {
        for (GameModes gameMode : GameModes.values()) {
            if (player.selectedGameMode == gameMode) {
                player.getPacketSender().sendConfig(gameMode.configId, 1);
                continue;
            }
            player.getPacketSender().sendConfig(gameMode.configId, 0);
        }
    }

    public static void sendNames(Player player) {
        for (GameModes mode : GameModes.values()) {
            player.getPacketSender().sendString(mode.stringId, mode.name);
        }
    }

    public enum GameModes {
        NORMAL("Forgotten", 52761, 116005, 1, 0,
                new Item[]{new Item(8326, 1), new Item(8327, 1), new Item(8328, 1),
                        new Item(19939, 1), new Item(19938, 1), new Item(19914, 1),
                        new Item(15432, 1), new Item(23089, 1), new Item(23091, 1),
                        new Item(4178, 1), new Item(23118, 1), new Item(23121, 1), new Item(23110, 1), new Item(995, 20)
                },
                "@yel@BETA TESTER",
                "",
                "@red@NORMAL MODE AVAILABLE DURING BETA", "", "@yel@NEW GAME MODES+INTERFACE SOON", "", ""),
/*

        LONE_WOLF("Lone Wolf", 52762, 116006, 1, 1,
                new Item[]{new Item(8326, 1), new Item(8327, 1), new Item(8328, 1),
                        new Item(19939, 1), new Item(19938, 1), new Item(19914, 1),
                        new Item(15432, 1), new Item(23089, 1), new Item(23091, 1),
                        new Item(4178, 1), new Item(23118, 1), new Item(23081, 1), new Item(23121, 1), new Item(995, 20)
                },
                "Lone Wolf Mode can be very difficult",
                "You cannot Trade, use player owned shops.",
                "Drops only appear if you defeat a monster yourself",
                "Choose Lone Wolf for a Challenge!",
                "@whi@6.0% Drop rate bonus", "", ""),


        GROUP_LONEWOLF("Group Lone Wolf", 52778, 116008, 1, 4,
                new Item[]{new Item(8326, 1), new Item(8327, 1), new Item(8328, 1),
                        new Item(19939, 1), new Item(19938, 1), new Item(19914, 1),
                        new Item(15432, 1), new Item(23089, 1), new Item(23091, 1),
                        new Item(4178, 1), new Item(23118, 1), new Item(23081, 1), new Item(23121, 1), new Item(995, 20)
                },
                "Team Lone Wolf is a very fun game mode",
                "Same rules as Lone wolf Mode",
                "You can have a group with up to five players",
                "You can trade other players in your group",
                "You have a shared bank with your group",
                "@whi@10.0% Drop rate bonus", ""),


        EXILED("Exiled", 52774, 116009, 1, 3,
                new Item[]{new Item(8326, 1), new Item(8327, 1), new Item(8328, 1),
                        new Item(19939, 1), new Item(19938, 1), new Item(19914, 1),
                        new Item(15432, 1), new Item(23089, 1), new Item(23091, 1),
                        new Item(4178, 1), new Item(23118, 1), new Item(23081, 1), new Item(23121, 1), new Item(995, 20)
                },
                "This Game Mode is EXTREMELY difficult",
                "@red@Lowered XP Rates",
                "Exiled mode offers Bonus Damage and Drop Rate stats.",
                "@whi@15.0% Drop rate bonus", "@whi@10% Damage Bonus", "", ""),
*/


        ;
        private String name;
        private int stringId;
        private int checkClick;
        private int textClick;
        private int configId;
        private Item[] starterPackItems;
        private String line1;
        private String line2;
        private String line3;
        private String line4;
        private String line5;
        private String line6;
        private String line7;

        GameModes(String name, int stringId, int checkClick, int textClick, int configId, Item[] starterPackItems, String line1, String line2, String line3, String line4, String line5, String line6, String line7) {
            this.name = name;
            this.stringId = stringId;
            this.checkClick = checkClick;
            this.textClick = textClick;
            this.configId = configId;
            this.starterPackItems = starterPackItems;
            this.line1 = line1;
            this.line2 = line2;
            this.line3 = line3;
            this.line4 = line4;
            this.line5 = line5;
            this.line6 = line6;
            this.line7 = line7;
        }
    }
}
