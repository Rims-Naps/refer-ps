package com.ruse.world.content.dialogue;

import com.ruse.world.entity.impl.player.Player;

import java.util.function.Consumer;

public class SelectionDialogue {

    private final Player player;
    private final Selection[] selections;
    private int page = -1;
    private final String title;

    public SelectionDialogue(Player player, String title, Selection...selections) {
        this.player = player;
        this.title = title;
        this.selections = selections;
    }

    public SelectionDialogue(Player player, Selection...selections) {
        this(player, "Select an Option", selections);
    }

    public void nextPage() {
        openToPage(page + 1);
    }

    public void start() {
        openToPage(0);
    }

    public void openToPage(int page) {
        this.page = page;
        final int offset = page * 4;
        final int length = selections.length;

        Dialogue d = new Dialogue() {

            @Override
            public DialogueType type() {
                return DialogueType.OPTION;
            }

            @Override
            public DialogueExpression animation() {
                return DialogueExpression.NORMAL;
            }

            @Override
            public String[] dialogue() {
                if (length - offset == 1)
                    return new String[] {selections[offset].option};
                if (length - offset == 2)
                    return new String[] {selections[offset].option, selections[1 + offset].option};
                if (length - offset == 3)
                    return new String[] {selections[offset].option, selections[1 + offset].option, selections[2 + offset].option};
                if (length - offset == 4)
                    return new String[] {selections[offset].option, selections[1 + offset].option, selections[2 + offset].option, selections[3 + offset].option};
                if (length - offset == 5)
                    return new String[] {selections[offset].option, selections[1 + offset].option, selections[2 + offset].option, selections[3 + offset].option, selections[4 + offset].option};
                if (length - offset > 5)
                    return new String[] {selections[offset].option, selections[1 + offset].option, selections[2 + offset].option, selections[3 + offset].option, "More options.."};

                return null;
            }

            @Override
            public void option(Player player, int option) {
                // Reset the dialogue instance to avoid double calling the select option BEFORE
                // So in case the option redirects to another dialogue
                player.setDialogue(null);

                // Select the option
                selectedOption(player, option);
            }
        };

        d.setClosesMainScreen(false);
        DialogueManager.start(player, d);
    }

    private void selectedOption(Player player, int option) {
        int offset = (page * 4);
        if (option == 5 && selections.length - offset > 5) {
            nextPage();
        } else {
            int index = offset - 1 + option;
            if (selections[index].consumer != null) {
                selections[index].consumer.accept(player);
            }
            selections[index].clicked(page);
        }
    }



    public static class Selection {
        private final String option;
        private final int index;
        private final Consumer<Player> consumer;

        public void clicked(int currentPage) {

        }

        public Selection(String option, int index, Consumer<Player> consumer) {
            this.option = option;
            this.index = index;
            this.consumer = consumer;
        }

        public Selection(String option, int index) {
            this(option, index, null);
        }

        public String getOption() {
            return option;
        }

        public int getIndex() {
            return index;
        }
    }

}