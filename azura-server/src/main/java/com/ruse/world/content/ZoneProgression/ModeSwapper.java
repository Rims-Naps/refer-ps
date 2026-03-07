package com.ruse.world.content.ZoneProgression;

import com.ruse.model.Flag;
import com.ruse.model.GameMode;
import com.ruse.model.Skill;
import com.ruse.model.XpMode;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.content.skill.SkillManager;
import com.ruse.world.entity.impl.player.Player;

public class ModeSwapper {

    public static void optionSelection(Player player) {
        new SelectionDialogue(player, "Selection",
                new SelectionDialogue.Selection("De-Iron", 0, p -> {
                    if (p.getGameMode() != GameMode.IRONMAN) {
                        p.msgRed("You're not an Ironman");
                    } else {
                        deIronOption(p);
                    }
                    p.getPacketSender().sendChatboxInterfaceRemoval();
                }),
                new SelectionDialogue.Selection("Change Difficulty - Your Exp will be scaled accordingly.", 1, p -> {
                    if (p.isHasChangedGameMode()) {
                        p.msgRed("You have already changed your game mode once.");
                        p.getPacketSender().sendChatboxInterfaceRemoval();
                    } else {
                        difficultySelection(p);
                    }
                }),
                new SelectionDialogue.Selection("Nevermind...", 2, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
        ).start();
    }

    public static void deIronOption(Player player) {
        new SelectionDialogue(player, "Are you sure?",
                new SelectionDialogue.Selection("Yes, remove Ironman status", 0, p -> {
                    p.msgFancyPurp("You have successfully removed your Ironman status. You are now free to trade.");
                    p.setGameMode(GameMode.NORMAL);
                    p.getPacketSender().sendChatboxInterfaceRemoval();
                }),
                new SelectionDialogue.Selection("Nevermind...", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
        ).start();
    }

    public static void difficultySelection(Player player) {
        new SelectionDialogue(player, "Pick your new XP-mode",
                new SelectionDialogue.Selection("Easy",   0, p -> tryChangeXpMode(p, XpMode.EASY)),
                new SelectionDialogue.Selection("Medium", 1, p -> tryChangeXpMode(p, XpMode.MEDIUM)),
                new SelectionDialogue.Selection("Elite",  2, p -> tryChangeXpMode(p, XpMode.ELITE)),
                new SelectionDialogue.Selection("Master", 3, p -> tryChangeXpMode(p, XpMode.MASTER)),
                new SelectionDialogue.Selection("Nevermind...", 4, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
        ).start();
    }

    private static void tryChangeXpMode(Player player, XpMode newMode) {
        XpMode oldMode = player.getXpmode();

        // 1) can't downgrade or pick same
        if (newMode.ordinal() <= oldMode.ordinal()) {
            player.msgRed("You can only change your experience mode to a harder mode.");
            player.getPacketSender().sendChatboxInterfaceRemoval();
            return;
        }

        // 2) only allowed once
        if (player.isHasChangedGameMode()) {
            player.msgRed("You have already changed your game mode once.");
            player.getPacketSender().sendChatboxInterfaceRemoval();
            return;
        }

        // passed all checks → perform swap
        changeXpMode(player, newMode);
    }

    public static void changeXpMode(Player player, XpMode newMode) {
        XpMode oldMode = player.getXpmode();

        // adjust all skills proportionally
        for (Skill skill : Skill.values()) {
            double oldMul = getXpModifier(oldMode, skill);
            double newMul = getXpModifier(newMode, skill);
            int   curXp  = player.getSkillManager().getExperience(skill);
            int   adjXp  = (int)((curXp / oldMul) * newMul);

            player.getSkillManager().setExperience(skill, adjXp);
            int newLevel = SkillManager.getLevelForExperience(adjXp);
            player.getSkillManager().setMaxLevel(skill, newLevel);
            player.getSkillManager().setCurrentLevel(skill, newLevel);
            player.getUpdateFlag().flag(Flag.APPEARANCE);
        }

        player.setXpMode(newMode);
        player.setHasChangedGameMode(true);
        player.msgFancyPurp("Your experience mode has been changed to " + newMode + ".");
        player.getPacketSender().sendChatboxInterfaceRemoval();
    }


    public static double getXpModifier(XpMode mode, Skill skill) {
        double multiplier = 1.0;

        if (mode == XpMode.EASY) {
            if (skill.isCombat() && skill != Skill.PRAYER) {
                multiplier = 75;
            } else if (skill == Skill.PRAYER) {
                multiplier = 4;
            } else if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                multiplier = 5;
            } else if (!skill.isCombat()) {
                multiplier = 5;
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING) {
                    multiplier *= 2;
                }
            }
        } else if (mode == XpMode.MEDIUM) {
            if (skill.isCombat() && skill != Skill.PRAYER) {
                multiplier = 37;
            } else if (skill == Skill.PRAYER) {
                multiplier = 3;
            } else if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                multiplier = 4;
            } else if (!skill.isCombat()) {
                multiplier = 4;
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING) {
                    multiplier *= 2;
                }
            }
        } else if (mode == XpMode.ELITE) {
            if (skill.isCombat() && skill != Skill.PRAYER) {
                multiplier = 18;
            } else if (skill == Skill.PRAYER) {
                multiplier = 2;
            } else if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                multiplier = 3;
            } else if (!skill.isCombat()) {
                multiplier = 3;
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING) {
                    multiplier *= 2;
                }
            }
        } else if (mode == XpMode.MASTER) {
            if (skill.isCombat() && skill != Skill.PRAYER) {
                multiplier = 4;
            } else if (skill == Skill.PRAYER) {
                multiplier = 1;
            } else if (skill == Skill.SLAYER || skill == Skill.BEAST_HUNTER) {
                multiplier = 2;
            } else if (!skill.isCombat()) {
                multiplier = 2;
                if (skill == Skill.JOURNEYMAN || skill == Skill.MINING || skill == Skill.WOODCUTTING) {
                    multiplier *= 2;
                }
            }
        }

        return multiplier;
    }
}


