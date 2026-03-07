package com.ruse.world.content.quests.impl;

import com.ruse.model.Item;
import com.ruse.world.content.bossfights.impl.EverthornFight;
import com.ruse.world.content.dialogue.Dialogue;
import com.ruse.world.content.dialogue.DialogueExpression;
import com.ruse.world.content.dialogue.DialogueManager;
import com.ruse.world.content.dialogue.DialogueType;
import com.ruse.world.content.skill.impl.slayer.SlayerTasks;
import com.ruse.world.entity.impl.player.Player;

public class Christmas2023 {

    public static void handleSanta(Player plr) {
        switch (plr.getChristmasQuest2023Stage()) {
            case 6:
                DialogueManager.start(plr, santa_response_15(plr));
            break;
            case 5:
                if (plr.getInventory().contains(1468, 1))
                    DialogueManager.start(plr, santa_response_14(plr));
            break;
            case 4:
                DialogueManager.start(plr, player_response_2(plr));
            break;
            case 3:
                DialogueManager.start(plr, santa_response_13(plr));
            break;
            case 2:
                if (plr.getInventory().contains(1400, 25))
                    DialogueManager.start(plr, santa_response_12(plr));
                else
                    DialogueManager.start(plr, santa_response_11(plr));
            break;
            case 1:
                if (plr.getInventory().contains(1467, 10))
                    DialogueManager.start(plr, santa_response_9(plr));
                else
                    DialogueManager.start(plr, santa_response_8(plr));
            break;
            default:
                plr.setDialogueActionId(5555);
                DialogueManager.start(plr, startTalking(plr));
        }
    }
    public static Dialogue startTalking(Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.UNSURE;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"You're not one of my typical Elves..."};

            }

            public Dialogue nextDialogue() {
                return player_response(player);
            }
        };
    }

    public static Dialogue player_response(Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.PLAYER_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"That I am not"};

            }

            public Dialogue nextDialogue() {
                return santa_response_1(player);
            }
        };
    }

    public static Dialogue santa_response_1(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.CROOKED_HEAD;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Well at least you're honest. While you're here I",
                    "could use your help with something"};

            }
            public Dialogue nextDialogue() {
                return player_response_1(player);
            }
        };
    }

    public static Dialogue player_response_1(Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.PLAYER_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"How can I be of your assistance."};

            }

            public Dialogue nextDialogue() {
                return santa_response_2(player);
            }
        };
    }

    public static Dialogue santa_response_2(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.JUST_LISTEN;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Long ago a massive battle ensued, between myself,"," and Jack Frost.",
                    "Jack frost threatened to take over the world,"," and freeze it over for himself"};
            }
            public Dialogue nextDialogue() {
                return santa_response_3(player);
            }
        };
    }

    public static Dialogue santa_response_3(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.MIDLY_ANGRY;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "In a turn of events, my army of Elves,"," and through the christmas spirit",
                    "We managed to overcome his powers, and", " quell his thirst for world domination"};
            }
            public Dialogue nextDialogue() {
                return santa_response_4(player);
            }
        };
    }

    public static Dialogue santa_response_4(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.ANGRY;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "However, it seems the seal that held his powers has been",
                    "broken. Now Jack Frost is back, trying to take over",
                    "the world again. This time, his army is much bigger",
                    "backed by an unknown force, someone on the naughty list."};
            }
            public Dialogue nextDialogue() {
                return santa_response_5(player);
            }
        };
    }

    public static Dialogue santa_response_5(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.CALM;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "I need your help to get rid of Jack Frost forever",
                    "Are you willing to help?"
                };
            }
            public Dialogue nextDialogue() {
                return player_options(player);
            }
        };
    }

    public static Dialogue player_options(Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.OPTION;
            }

            @Override
            public String[] dialogue() {
                return new String[] { "Help Santa, and Save the day",
                    "Ignore Santa's plea's for help",
                };
            }
        };
    }

    public static Dialogue santa_response_6(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.CALM;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Perfect, To Start, Fight some of Jack Frosts army,",
                    " at ::christmas and bring me back 10x Snowflakes"
                };
            }
        };
    }

    public static Dialogue santa_response_7(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.ANGRY;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Pfff Typical Human!"
                };
            }
        };
    }

    public static Dialogue santa_response_8(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.MIDLY_ANGRY;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Please hurry up with the snow flakes,",
                    "The fate of the world depends on you"
                };
            }
        };
    }

    public static Dialogue santa_response_9(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.THINKING_STILL;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Ahhh you've brought back the Snowflakes,", "let me examine them really quick"
                };
            }

            public Dialogue nextDialogue() {
                return santa_response_10(player);
            }

            @Override
            public void specialAction() {
                player.getInventory().delete(new Item(1467, 10000));
                player.setChristmasQuest2023Stage(2);
            }
        };
    }

    public static Dialogue santa_response_10(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.THINKING_STILL;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "It seems his army is stronger than last time.",
                    "I have another task for you bring me 25x Vorpal Logs",
                    "to help light the brazier. This is the only way",
                    "to rid the world of Jack Frost once and for-all"
                };
            }
        };
    }

    public static Dialogue santa_response_11(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.MIDLY_ANGRY;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Please hurry up with the 25 Vorpal Logs,",
                    "The fate of the world depends on you"
                };
            }
        };
    }

    public static Dialogue santa_response_12(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.MANIC_FACE;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "YES! This will do it. I need to light the brazier",
                };
            }
            public Dialogue nextDialogue() {
                return santa_response_13(player);
            }

            @Override
            public void specialAction() {
                player.getInventory().delete(new Item(1400, 25));
                player.setChristmasQuest2023Stage(3);
            }
        };
    }

    public static Dialogue santa_response_13(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.MANIC_FACE;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "The final thing I need his is icy heart.",
                    "Find Jack Frost, and Kill him. Rip out his icy heart",
                    "And we can finally quell the world of this icy monster"
                };
            }
            public Dialogue nextDialogue() {
                return player_response_2(player);
            }
        };
    }

    public static Dialogue player_response_2(Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.PLAYER_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.THINKING_STILL;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"I wonder how I can find and kill Jack Frost"};

            }

            public Dialogue nextDialogue() {
                return jack_frost_1(player);
            }

            @Override
            public void specialAction() {
                player.setChristmasQuest2023Stage(4);
            }
        };
    }

    public static Dialogue jack_frost_1(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 8517;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.ANGRY;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "You seem to be really invested in finding me.",
                    "You think you have what it takes to defeat me?",
                };
            }
            public Dialogue nextDialogue() {
                return player_response_3(player);
            }
        };
    }

    public static Dialogue player_response_3(Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.PLAYER_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.MANIC_FACE;
            }

            @Override
            public String[] dialogue() {
                return new String[]{"Of course I think I can defeat you!"};

            }

            public Dialogue nextDialogue() {
                return jack_frost_2(player);
            }
        };
    }

    public static Dialogue jack_frost_2(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 8517;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.ANGRY;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Its cute that a meer mortal like you thinks",
                    "they have what it takes to defeat me....",
                    "You're going to be destroyed!"
                };
            }
            public Dialogue nextDialogue() {
                return teleport_response(player);
            }
        };
    }

    public static Dialogue teleport_response(Player player) {
        return new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Its cute that a meer mortal like you thinks",
                    "they have what it takes to defeat me....",
                    "You're going to be destroyed!"
                };
            }
            @Override
            public void specialAction() {
                if (player.getBossFight() != null) {
                    player.getBossFight().endFight();
                }

                player.setBossFight(new JackFrostFight(player));
                player.getBossFight().begin();
                player.getPacketSender().closeAllWindows();
            }
        };
    }

    public static Dialogue santa_response_14(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.WIDEN_EYES;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "You did it!",
                    "I can't believe it, With this we can finally get rid",
                    "Of Jack Frost once and for-all"
                };
            }
            public Dialogue nextDialogue() {
                return endQuest(player);
            }

            @Override
            public void specialAction() {
                player.getInventory().delete(new Item(1468, 99));
            }
        };
    }

    public static Dialogue endQuest(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.WIDEN_EYES;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "You did it!"
                };
            }

            @Override
            public void specialAction() {
                player.setCompletedChristmas(true);
                player.setChristmasQuest2023Stage(6);
                player.getBank(0).add(1469, 1);
                player.getBank(0).add(1453, 2);
                player.getPacketSender().closeAllWindows();
                player.getPacketSender().sendInterface(4909);
                player.getPacketSender().sendString(4913, "You completed the Christmas Quest!");
                player.getPacketSender().sendString(4911, "           You are awarded:");
                player.getPacketSender().sendString(4914, "       Access to Christmas Slayer");
                player.getPacketSender().sendString(4915, "     Access to Christmas Slayer Shop");
                player.getPacketSender().sendString(4918, "    2x Frost Crates & 1x Snow Globe");
                player.getPacketSender().sendString(4916, "     Have a Merry Christmas!");
            }
        };
    }

    public static Dialogue santa_response_15(Player player) {
        return new Dialogue() {

            @Override
            public int npcId() {
                return 1060;
            }

            @Override
            public DialogueType type() {
                return DialogueType.NPC_STATEMENT;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.WIDEN_EYES;
            }

            @Override
            public String[] dialogue() {
                return new String[]{
                    "Thank you again for you service!"
                };
            }
        };
    }

}
