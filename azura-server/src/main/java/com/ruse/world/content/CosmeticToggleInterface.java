package com.ruse.world.content;

import com.ruse.model.Flag;
import com.ruse.world.entity.impl.player.Player;

public class CosmeticToggleInterface {

    public static boolean handleButtonClick(Player player, int buttonID) {


        ButtonAction action = ButtonAction.fromButtonID(buttonID);
        if (action != null) {
            return action.execute(player);
        }

        return false;
    }

    private enum ButtonAction {
        CLOSE(-22934, player -> {
            player.getPacketSender().sendInterfaceRemoval();
            return true;
        }),

        HELMET(-22933, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticHeadOn(!player.cosmeticHeadOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);

                if (player.isCosmeticHeadOn()) {
                    player.getPacketSender().sendString(42603,"@gre@Helmet");
                    player.msgGreen("Cosmetic Override for Helmet toggled on.");
                } else {
                    player.getPacketSender().sendString(42603,"@red@Helmet");
                    player.msgRed("Cosmetic Override for Helmet toggled off.");
                }
                return true;
            }
        })),

        BODY(-22932, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticBodyOn(!player.cosmeticBodyOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                if (player.isCosmeticBodyOn()) {
                    player.getPacketSender().sendString(42604,"@gre@Body");
                    player.msgGreen("Cosmetic Override for Body toggled on.");
                } else {
                    player.getPacketSender().sendString(42604,"@red@Body");
                    player.msgRed("Cosmetic Override for Body toggled off.");
                }
                return true;
            }
        })),
        LEGS(-22931, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticLegsOn(!player.cosmeticLegsOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                if (player.isCosmeticLegsOn()) {
                    player.getPacketSender().sendString(42605,"@gre@Legs");
                    player.msgGreen("Cosmetic Override for Legs toggled on.");
                } else {
                    player.getPacketSender().sendString(42605,"@red@Legs");
                    player.msgRed("Cosmetic Override for Legs toggled off.");
                }
                return true;
            }
        })),
        GLOVES(-22930, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticGlovesOn(!player.cosmeticGlovesOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                if (player.isCosmeticGlovesOn()) {
                    player.getPacketSender().sendString(42606,"@gre@Gloves");
                    player.msgGreen("Cosmetic Override for Gloves toggled on.");
                } else {
                    player.getPacketSender().sendString(42606,"@red@Gloves");
                    player.msgRed("Cosmetic Override for Gloves toggled off.");
                }
                return true;
            }
        })),
        BOOT(-22929, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticBootsOn(!player.cosmeticBootsOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);

                if (player.isCosmeticBootsOn()) {
                    player.getPacketSender().sendString(42607,"@gre@Boots");
                    player.msgGreen("Cosmetic Override for Boots toggled on.");
                } else {
                    player.getPacketSender().sendString(42607,"@red@Boots");
                    player.msgRed("Cosmetic Override for Boots toggled off.");
                }
                return true;
            }
        })),
        SHIELD(-22928, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticShieldOn(!player.cosmeticShieldOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                if (player.isCosmeticShieldOn()) {
                    player.getPacketSender().sendString(42608,"@gre@Shield");
                    player.msgGreen("Cosmetic Override for Shield toggled on.");
                } else {
                    player.getPacketSender().sendString(42608,"@red@Shield");
                    player.msgRed("Cosmetic Override for Shield toggled off.");
                }
                return true;
            }
        })),
        WEAPON(-22927, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticWeaponOn(!player.cosmeticWeaponOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                if (player.isCosmeticWeaponOn()) {
                    player.getPacketSender().sendString(42609,"@gre@Weapon");
                    player.msgGreen("Cosmetic Override for Weapon toggled on.");
                } else {
                    player.getPacketSender().sendString(42609,"@red@Weapon");
                    player.msgRed("Cosmetic Override for Weapon toggled off.");
                }

                return true;
            }
        })),
        CAPE(-22926, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticCapeOn(!player.cosmeticCapeOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                if (player.isCosmeticCapeOn()) {
                    player.getPacketSender().sendString(42610,"@gre@Cape");
                    player.msgGreen("Cosmetic Override for Cape toggled on.");
                } else {
                    player.getPacketSender().sendString(42610,"@red@Cape");
                    player.msgRed("Cosmetic Override for Cape toggled off.");
                }
                return true;
            }
        })),
        AMULET(-22925, player -> submitAction(player, new Action() {
            @Override
            public boolean perform(Player player) {
                player.setCosmeticAmuletOn(!player.cosmeticAmuletOn);
                player.getUpdateFlag().flag(Flag.APPEARANCE);
                if (player.isCosmeticAmuletOn()) {
                    player.getPacketSender().sendString(42611,"@gre@Amulet");
                    player.msgGreen("Cosmetic Override for Amulet toggled on.");
                } else {
                    player.getPacketSender().sendString(42611,"@red@Amulet");
                    player.msgRed("Cosmetic Override for Amulet toggled off.");
                }
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

    private static boolean submitAction(Player player, ButtonAction.Action action) {
        return action.perform(player);
    }

    public static void openInterface(Player player) {
        player.getPacketSender().sendInterface(42600);
        if (player.isCosmeticHeadOn()) {
            player.getPacketSender().sendString(42603,"@gre@Helmet");
        } else {
            player.getPacketSender().sendString(42603,"@red@Helmet");
        }

        if (player.isCosmeticBodyOn()) {
            player.getPacketSender().sendString(42604,"@gre@Body");
        } else {
            player.getPacketSender().sendString(42604,"@red@Body");
        }

        if (player.isCosmeticLegsOn()) {
            player.getPacketSender().sendString(42605,"@gre@Legs");
        } else {
            player.getPacketSender().sendString(42605,"@red@Legs");
        }

        if (player.isCosmeticGlovesOn()) {
            player.getPacketSender().sendString(42606,"@gre@Gloves");
        } else {
            player.getPacketSender().sendString(42606,"@red@Gloves");
        }

        if (player.isCosmeticBootsOn()) {
            player.getPacketSender().sendString(42607,"@gre@Boots");
        } else {
            player.getPacketSender().sendString(42607,"@red@Boots");
        }

        if (player.isCosmeticShieldOn()) {
            player.getPacketSender().sendString(42608,"@gre@Shield");
        } else {
            player.getPacketSender().sendString(42608,"@red@Shield");
        }

        if (player.isCosmeticWeaponOn()) {
            player.getPacketSender().sendString(42609,"@gre@Weapon");
        } else {
            player.getPacketSender().sendString(42609,"@red@Weapon");
        }

        if (player.isCosmeticCapeOn()) {
            player.getPacketSender().sendString(42610,"@gre@Cape");
        } else {
            player.getPacketSender().sendString(42610,"@red@Cape");
        }

        if (player.isCosmeticAmuletOn()) {
            player.getPacketSender().sendString(42611,"@gre@Amulet");
        } else {
            player.getPacketSender().sendString(42611,"@red@Amulet");

        }
    }

}
