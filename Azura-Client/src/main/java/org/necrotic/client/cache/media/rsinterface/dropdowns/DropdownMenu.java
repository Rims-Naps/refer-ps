package org.necrotic.client.cache.media.rsinterface.dropdowns;


import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;

public class DropdownMenu {

    private final int height;
    private final int width;
    private final String[] options;
    private final Dropdown dropdown;
    private final boolean split;
    private boolean open;
    private String optionSelected;
    private int scroll;

    public DropdownMenu(int width, boolean split, int defaultOption, String[] options, Dropdown d) {
        this.width = width;
        this.height = split ? ((14 * options.length) / 2) + 3 : (14 * options.length) + 3;
        this.options = options;
        this.optionSelected = defaultOption == -1 ? "Select an option" : options[defaultOption];
        this.open = false;
        this.dropdown = d;
        this.split = split;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public String[] getOptions() {
        return this.options;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean b) {
        this.open = b;
    }

    public String getSelected() {
        return this.optionSelected;
    }

    public void setSelected(String s) {
        this.optionSelected = s;
    }

    public Dropdown getDrop() {
        return this.dropdown;
    }

    public boolean doesSplit() {
        return this.split;
    }

    public void changeScroll(int scrollPosition) {
        scroll += scrollPosition;
        if (scroll >= options.length - 5)
            scroll = options.length - 5;
        if (scroll < 0)
            scroll = 0;
    }

    public void hover(RSInterface parent, RSInterface child, int hoverX, int hoverY, int xBounds, int yBounds) {
        if (hoverX < xBounds || hoverY < yBounds + 20) {
            child.dropdownHover = -1;
            return;
        }

        int height = 13;
        height *= Math.min(options.length, 5);

        if (hoverX > xBounds + width || hoverY >= yBounds + 21 + height) {
            child.dropdownHover = -1;
            return;
        }

        int yy = (yBounds + 21 + height + 13) - hoverY;

        int len = options.length;
        if (len > 5)
            len = 5;

        int shit = yy / 13;
        if (shit > len)
            shit = len;

        child.dropdownHover = (len - shit) + scroll;

        Client game = Client.instance;
        game.menuActionName[game.menuActionRow] = "Select " + options[child.dropdownHover];
        game.menuActionID[game.menuActionRow] = 770;
        game.menuActionCmd3[game.menuActionRow] = child.id;
        game.menuActionCmd2[game.menuActionRow] = child.dropdownHover;
        game.menuActionCmd1[game.menuActionRow] = parent.id;
        game.menuActionRow++;
    }
}
