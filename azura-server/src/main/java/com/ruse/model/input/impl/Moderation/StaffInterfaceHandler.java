package com.ruse.model.input.impl.Moderation;

import com.ruse.model.Position;
import com.ruse.world.content.transportation.TeleportHandler;
import com.ruse.world.content.transportation.TeleportType;
import com.ruse.world.entity.impl.player.Player;

public class StaffInterfaceHandler {

    public static boolean handleButtonClick(Player player, int buttonID) {
        if (!player.getRights().isStaff()) {
            return false;
        }

        ButtonAction action = ButtonAction.fromButtonID(buttonID);
        if (action != null) {
            return action.execute(player);
        }

        return false;
    }

    private enum ButtonAction {
        CLOSE(-18734, player -> {
            player.getPacketSender().sendInterfaceRemoval();
            return true;
        }),

        MUTE(-18731, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new MutePacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Mute.");
                return true;
            }
        })),

        UNMUTE(-18730, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new UnmutePacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Un-Mute.");
                return true;
            }
        })),

        KICK(-18729, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new KickPlayerPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Kick.");
                return true;
            }
        })),

        JAIL(-18728, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new JailPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Jail.");
                return true;
            }
        })),

        UNJAIL(-18727, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new UnJailPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Un-Jail.");
                return true;
            }
        })),

        MOVEHOME(-18723, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new MoveHomePacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Move Home.");
                return true;
            }
        })),

        TELE_TO_PLAYER(-18720, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new TeleToPlayerPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Teleport to.");
                return true;
            }
        })),

        TELE_PLAYER_TO_ME(-18719, player -> checkSupportPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new TelePlayerToMePacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Teleport to you.");
                return true;
            }
        })),

        STAFF_ZONE_TELEPORT(-18724, player -> {
            checkSupportPerms(player, new Action() {
                @Override
                public boolean perform(Player player) {
                    TeleportHandler.teleportPlayer(player, new Position(3167, 3545, 4), TeleportType.NORMAL);
                    return true;
                }
            });
            return true;
        }),

        BAN(-18733, player -> checkModeratorPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new BanPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to ban.");
                return true;
            }
        })),

        UNBAN(-18732, player -> checkModeratorPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new UnbanPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Un-ban.");
                return true;
            }
        })),

        ALT_CHECK(-18725, player -> checkModeratorPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new AltCheckPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Check alts for.");
                return true;
            }
        })),

        KILL(-18722, player -> checkAdminPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new KillPlayerPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the name of the player you wish to Kill.");
                return true;
            }
        })),

        INVISIBLE(-18721, player -> checkAdminPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setVisible(!player.isVisible());
                player.sendMessage("You are now " + (player.isVisible() ? "visible" : "invisible"));
                return true;
            }
        })),


        ALERT(-18726, player -> checkManagerPerms(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setInputHandling(new AlertPacketListener());
                player.getPacketSender().sendEnterInputPrompt("Enter the Broadcast Message you would like to send.");
                return true;
            }
        }));

        private final int buttonID;
        private final Action action;

        ButtonAction(int buttonID, Action action) {
            this.buttonID = buttonID;
            this.action = action;
        }

        public static ButtonAction fromButtonID(int buttonID) {
            for (ButtonAction action : values()) {
                if (action.buttonID == buttonID) {
                    return action;
                }
            }
            return null;
        }

        public boolean execute(Player player) {
            return action.perform(player);
        }

        private interface Action {
            boolean perform(Player player);
        }
    }

    private static boolean checkSupportPerms(Player player, ButtonAction.Action action) {
        if (!player.getRights().isSupport()) {
            player.sendMessage("You don't have permission to use this command.");
            return false;
        }
        return action.perform(player);
    }

    private static boolean checkModeratorPerms(Player player, ButtonAction.Action action) {
        if (!player.getRights().isModerator()) {
            player.sendMessage("You don't have permission to use this command.");
            return false;
        }
        return action.perform(player);
    }

    private static boolean checkAdminPerms(Player player, ButtonAction.Action action) {
        if (!player.getRights().isAdmin()) {
            player.sendMessage("You don't have permission to use this command.");
            return false;
        }
        return action.perform(player);
    }

    private static boolean checkManagerPerms(Player player, ButtonAction.Action action) {
        if (!player.getRights().isManagement()) {
            player.sendMessage("You don't have permission to use this command.");
            return false;
        }
        return action.perform(player);
    }
}
