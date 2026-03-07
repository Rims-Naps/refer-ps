package org.necrotic.client.cache.media.rsinterface;

import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.Signlink;
import org.necrotic.client.cache.definition.ItemDef;
import org.necrotic.client.cache.media.TextDrawingArea;

public class InterfaceTradingPost extends Interfaces {


    private static int[] itemSellStatusBars = new int[10];
    private static int displayCurrentSellingItemsContainer = 0;
    private static int selectItemToSellContainer = 0;
    private static int configureItemToSellContainer = 0;

    private static int[] items = new int[25];
    private static int itemSearchScrollable;
    private static int itemSearchInventory;
    private static int itemSearchNameStart;

    public enum InterfaceState {
        DEFAULT, SELECTING_SELL_ITEM, CONFIGURING_SELL_ITEM
    }

    @Override
    public void load(TextDrawingArea[] fonts) {
        itemSellStatusBars = new int[10];
        loadSearchMenu(fonts);
        loadMainExchangeMenu(fonts);
        loadSearchResults(fonts);
        enterState(InterfaceState.DEFAULT);
    }

    @Override
    public boolean button(int button) {
        if (button >= 53809 && button <= 53832) {
            int index = button - 53809;
            int item = items[index];
            if (item != -1) {
                Client.instance.getInputBuffer().putOpcode(220);
                Client.instance.getInputBuffer().putInt(item);
            }
        }
        return false;
    }

    @Override
    public boolean configPacketReceived(int config, int value) {
        if (config >= 3103 && config < 3103 + 10) {
            int idx = config - 3103;
            int start = itemSellStatusBars[idx];
            int width = (int) Math.ceil((149d * (((double) value) / 100d)));
            RSInterface.get(start).width = width;
            RSInterface.get(start).disabledColor = value == 100 ? 0x228B22 : 0xff9933;
            return true;
        } else if (config == 3102) {
            if (value < 0 || value >= InterfaceState.values().length) {
                Signlink.reportError("Invalid rightHandItem shop state");
                return true;
            }
            enterState(InterfaceState.values()[value]);
        }
        return false;
    }

    @Override
    public boolean onInputFieldEdited(int child) {
        if (child == 53802) {
            try {
                String input = RSInterface.get(child).inputFieldString.toLowerCase();
                for (int i = 0; i < items.length; i++) {
                    items[i] = -1;
                    RSInterface.get(itemSearchNameStart + i).message = "";
                    RSInterface.get(itemSearchInventory).inv[i] = -1;
                }

                if (input.length() == 0)
                    return true;

                RSInterface.get(itemSearchScrollable).scrollPosition = 0;

                int index = 0;
                main: for (int itemId = 0; itemId < 50_000; itemId++) {
                    if (itemId < 1) {
                        continue;
                    }

                    ItemDef def = ItemDef.get(itemId);
                    if (def != null && def.name != null) {
                        //if (InterfaceItemSearch.skip(def)) {
                        //    continue main;
                        //}

                        if (def.certID != -1)
                            continue main;

                        if (def.name.toLowerCase().contains(input)) {
                            items[index] = itemId;
                            String name = def.name;
                            if (name.length() > 20)
                                name = name.substring(0, 20) + "..";
                            RSInterface.get(itemSearchInventory).inv[index] = itemId + 1;
                            RSInterface.get(itemSearchNameStart + index).message = name;
                            index++;
                            if (index == 24) {
                                break;
                            }
                        }
                    }
                }

                for (int i = 0; i < items.length; i++) {
                    if (items[i] > -1) {

                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                Signlink.reportError("Error fetching input");
            }
        }
        return false;
    }

    @Override
    public boolean configButtonClicked(int config, int value) {
        return false;
    }

    private static void enterState(InterfaceState state) {
        RSInterface.get(displayCurrentSellingItemsContainer).active = state == InterfaceState.DEFAULT;
        RSInterface.get(selectItemToSellContainer).active = state == InterfaceState.SELECTING_SELL_ITEM;
        RSInterface.get(configureItemToSellContainer).active = state == InterfaceState.CONFIGURING_SELL_ITEM;
    }

    private static void loadMainExchangeMenu(TextDrawingArea[] fonts) {
        int child = 109_000;
        RSInterface inter = RSInterface.addInterface(child++);

        addSprite(child++, 5562);
        RSInterface.addCloseButton(child++, child++, child++);

        newHoverButton(child++, "Claim", 5563, 5564);
        addText(child++, "0 GP", 0xAF70C3, true, fonts, 0);
        newHoverButton(child++, "Search by item name", 5565, 5566);
        addText(child++, "Item", 0xAF70C3, true, fonts, 2);
        newHoverButton(child++, "Search by player name", 5565, 5566);
        addText(child++, "Player", 0xAF70C3, true, fonts, 2);
        newHoverButton(child++, "View recent", 5565, 5566);
        addText(child++, "Recent", 0xAF70C3, true, fonts, 2);
        newHoverButton(child++, "Sell item", 5567, 5568); // MRUNodes item for sale
        addText(child++, "List item for sale", 0xAF70C3, true, fonts, 2);
        addText(child++, "(0/10)", 0xAF70C3, true, fonts, 2);

        int child2 = 109_001;
        int childIndex = 0;
        inter.setChildren(child - child2 - 1 + 5);

        inter.child(childIndex++, child2++, 12, 16); // base
        inter.child(childIndex++, displayCurrentSellingItemsContainer = loadCurrentSellingItemsContainer(fonts), 268, 84);
        inter.child(childIndex++, selectItemToSellContainer = loadSelectItemToSellContainer(fonts), 262, 78);
        inter.child(childIndex++, configureItemToSellContainer = loadConfigureItemToSellContainer(fonts), 262, 78);
        inter.child(childIndex++, loadSellHistoryContainer(fonts), 31, 142);

        inter.child(childIndex++, child2++, 475, 24);
        inter.child(childIndex++, child2++, 475, 24);
        child2++;

        inter.child(childIndex++, child2++, 25, 77); // claim button
        inter.child(childIndex++, child2++, 118, 89); // claim button text

        inter.child(childIndex++, child2++, 25, 274); // search by item button
        inter.child(childIndex++, child2++, 28 + 36, 282); // search by item text

        inter.child(childIndex++, child2++, 102, 274); // search by rightHandItem button
        inter.child(childIndex++, child2++, 104 + 36, 282); // search by rightHandItem text

        inter.child(childIndex++, child2++, 179, 274); // view recent button
        inter.child(childIndex++, child2++, 178 + 36, 282); // view recent text

        inter.child(childIndex++, child2++, 301, 272); // list item for sale button
        inter.child(childIndex++, child2++, 299 + 76, 272 + 9); // list item for sale text

        inter.child(childIndex++, child2++, 458, 58); // current offer count string

        addButton(child2, 72, 32, "Search by item");
        inter.child(childIndex++, child2++, 25, 274); // search by item button

    }

    private static int loadSellHistoryContainer(TextDrawingArea[] fonts) {
        final int start = 109_400;
        int child = start;
        int childIndex = 0;
        RSInterface container = RSInterface.addInterface(child++);
        container.scrollMax = 300;
        container.width = 199;
        container.height = 101;

        container.setChildren(5 + 10);

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                container.child(childIndex++, child, 0, i * 30);
                addTransparentSprite(child++, 5569, 255);
            }
        }

        for (int i = 0; i < 10; i++) {
            container.child(childIndex++, child, 100, 5 + (i * 30));
            addText(child++, "Items Go Here !!!\\nItems Go Here !!!" + i, 0xFFEE00, true, fonts, 0);
        }


        return start;
    }

    private static int loadConfigureItemToSellContainer(TextDrawingArea[] fonts) {
        final int start = 109_700;
        int child = start;

        RSInterface container = RSInterface.addInterface(child++);
        container.width = 227;
        container.height = 188;

        int childIndex = 0;
        container.setChildren(19);

        container.child(childIndex++, child, 0, 0);
        addSprite(child++, 5570); // background

        container.child(childIndex++, child, 96, 18);
        addItemContainer(child, 1, 1, new String[5]);
        RSInterface.get(child).inv[0] = 4152;
        RSInterface.get(child).invStackSizes[0] = 1;
        child++;

        container.child(childIndex++, child, 112, 58);
        addText(child++, "Abyssal whip", 0xAF70C3, true, fonts, 2);

        container.child(childIndex++, child, 112, 75);
        addText(child++, "Buyer pays: @yel@1 GP",0xAF70C3, true, fonts, 1);

        container.child(childIndex++, child, 112, 90);
        addText(child++, "Quantity: @yel@1", 0xAF70C3, true, fonts, 1);

        container.child(childIndex++, child, 48, 12);
        addText(child++, "Your price:\\n@yel@1", 0xAF70C3, true, fonts, 0);

        container.child(childIndex++, child, 176, 12);
        addText(child++, "Listings: @yel@1",0xAF70C3, true, fonts, 0);

        container.child(childIndex++, child, 176, 12 + 14);
        addText(child++, "", RSInterface.DEFAULT_COLOR, true, fonts, 0);

        container.child(childIndex++, child, 38, 107);
        addButton(child++, 72, 32, "Guide price");
        container.child(childIndex++, child, 38, 107);
        newHoverButton(child++, "Set price", 5565, 5566);
        container.child(childIndex++, child, 38 + 35, 107 + 3);
        addText(child++, "Set\\nPrice", 0xAF70C3, true, fonts, 1);

        container.child(childIndex++, child, 118, 107);
        addButton(child++, 72, 32, "Add-all");
        container.child(childIndex++, child, 118, 107);
        addButton(child++, 72, 32, "Add-1000");
        container.child(childIndex++, child, 118, 107);
        addButton(child++, 72, 32, "Add-100");
        container.child(childIndex++, child, 118, 107);
        addButton(child++, 72, 32, "Add-10");
        container.child(childIndex++, child, 118, 107);
        newHoverButton(child++, "Set quantity", 5565, 5566);

        container.child(childIndex++, child, 118 + 35, 107 + 3);
        addText(child++, "Set\\nQuantity", 0xAF70C3, true, fonts, 1);

        container.child(childIndex++, child, 76, 143);
        newHoverButton(child++, "Confirm", 5565, 5566);
        container.child(childIndex++, child, 76 + 35, 146 + 5);
        addText(child++, "Confirm", 0x3F821E, true, fonts, 2);

        return start;
    }

    private static int loadSelectItemToSellContainer(TextDrawingArea[] fonts) {
        final int start = 109_600;
        int child = start;

        RSInterface container = RSInterface.addInterface(child++);
        container.width = 227;
        container.height = 188;

        int childIndex = 0;
        container.setChildren(2);

        container.child(childIndex++, child, 0, 0);
        addSprite(child++, 5571);

        container.child(childIndex++, child, 112, 78);
        addText(child++, "Select an item\\nfrom your\\ninventory.", RSInterface.DEFAULT_COLOR, true, fonts, 2);

        // Inventory container
        final int start2 = 109_690;
        int child2 = start2;
        RSInterface itemContainer = RSInterface.addInterface(child2++);
        itemContainer.setChildren(1);
        itemContainer.child(0, child2, 16, 8);
        addItemContainer(child2, 4, 7, new String[] {"Offer 1", "Offer 5", "Offer 10", "Offer X", null},
                10, 4);
        for (int i = 0; i < 28; i++) {
            RSInterface.get(child2).inv[i] = 1216;
            RSInterface.get(child2).invStackSizes[i] = i + 1;
        }
        child2++;

        return start;
    }

    private static int loadCurrentSellingItemsContainer(TextDrawingArea[] fonts) {
        final int start = 109_200;
        int child = start;
        int childIndex = 0;
        RSInterface container = RSInterface.addInterface(child++);
        container.scrollMax = 500;
        container.width = 197;
        container.height = 174;

        addItemContainer(child, 1, 10, new String[] {null, null, null, null, null}, 0, 18);
        for (int i = 0; i < 10; i++) {
            RSInterface.get(child).inv[i] = 1216;
            RSInterface.get(child).invStackSizes[i] = i + 1;
        }
        int itemsId = child;
        child++;

        container.setChildren(6 + 70);

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 1) {
                container.child(childIndex++, child, 0, i * 50);
                addTransparentSprite(child++, 5572, 255);
            }
        }

        for (int i = 0; i < 10; i++) {
            container.child(childIndex++, child, 38, 6 + (i * 50));
            addButton(child++, 153, 22, "Cancel-offer");
            container.child(childIndex++, child, 38, 6 + (i * 50));
            addClickableText(child++, "Dragon dagger", "View-details", fonts, 1, 0xAF70C3, false, true, 153, 22);
            container.child(childIndex++, child, 18, 37 + (i * 50));
            addText(child++, "50k", 0xFFEE00, true, fonts, 0);

            // progress bar
            container.child(childIndex++, child, 36, 26 + (i * 50));
            addSprite(child++, 5573);
            container.child(childIndex++, child, 38, 28 + (i * 50));
            itemSellStatusBars[i] = child;
            addPixels(child++, 0x228B22, 110, 13, 0, true);
            container.child(childIndex++, child, 38, 28 + (i * 50));
            addTransparentSprite(child++, 5574, 125);

            container.child(childIndex++, child, 113, 29 + (i * 50));
            addText(child++, "70 / 100", 0xFFEE00, true, fonts, 0);
        }

        container.child(childIndex++, itemsId, 2, 3);

        return start;
    }

    private static void loadSearchMenu(TextDrawingArea[] fonts) {
        RSInterface inter = RSInterface.addInterface(53_800);
        int child1 = 53_801;

        addText(child1++, "Item Search", 0xFE972F, true, true, 0, fonts, 2, 0, 1, 1);

        addSprite(child1, 546);
        RSInterface search = RSInterface.get(child1);
        search.inputField = true;
        search.inputFieldString = "";
        search.inputFieldStringX = search.enabledSprite.myWidth / 2;
        search.inputFieldStringY = (search.enabledSprite.myHeight / 2) + 4;
        child1++;

        RSInterface container = RSInterface.addInterface(child1++);
        container.width = 154;
        container.height = 220;
        container.scrollMax = 221;
        itemSearchScrollable = child1 - 1;

        RSInterface.newHoverButton(child1++, "Close", 737, 738, 1);
        child1++;
        child1++;
        child1++;

        container.setChildren(26);
        int child4 = 0;

        addItemContainer(child1, 1, 25, new String[5], 0, 0);
        RSInterface items = RSInterface.get(child1);
        items.parentID = container.id;
        items.invDynamicScrollbar = true;
        items.invDefaultScrollMax = 221;
        items.hideInvStackSizes = true;
       // items.invScrollItemsBeforeAdditionalScroll = 7;
        items.itemSearch = false;
        itemSearchInventory = child1;

        container.child(child4++, child1, 0, 0);
        child1++;

        itemSearchNameStart = child1;
        for (int i = 0; i < 25; i++) {
            addClickableText(child1, "", "Select item to search", fonts, 0, 0xFE972F, false, true, 170, 10);
            container.child(child4++, child1, 40, 6 + (i * 32));
            child1++;
        }

        int child2 = 53_801;
        int child3 = 0;
        inter.setChildren(4);

        inter.child(child3++, child2++, 93, 8);
        inter.child(child3++, child2++, 31, 26);
        inter.child(child3++, child2++, 12, 42);

        inter.child(child3++, child2++, 166, 8);
    }

    private static void loadSearchResults(TextDrawingArea[] tda) {
        RSInterface widget = RSInterface.addInterface(110_400);
        addSprite(110_401, 5575);

        newHoverButton(110_402, "Go back", 5576, 5577);

        newHoverButton(110_403, "Modify", 5576, 5577);

        newHoverButton(110_404, "Refresh", 5576, 5577);

        RSInterface.addText(110_405, "Go back", 0xAF70C3, true, true, -1, tda, 2);
        RSInterface.addText(110_406, "Modify", 0xAF70C3, true, true, -1, tda, 1);
        RSInterface.addText(110_407, "@gre@Refresh", 0xff9933, true, true, -1, tda, 1);

        RSInterface.addText(110_408, "Quantity", 0xAF70C3, false, true, -1, tda, 0);
        RSInterface.addText(110_409, "Name", 0xAF70C3, false, true, -1, tda, 0);
        addConfigButton(110_416, widget.id, 5578, 5579, "sprites3", 7, 5, "Sort", 0, 4, 3100);
        RSInterface.get(110_416).clickingChangesConfig = false;

        RSInterface.addText(110_410, "Price (ea)", 0xAF70C3, false, true, -1, tda, 0);
        addConfigButton(110_417, widget.id, 5578, 5579, "sprites3", 7, 5, "Sort", 0, 4, 3101);
        RSInterface.get(110_417).clickingChangesConfig = false;
        RSInterface.addText(110_411, "Seller", 0xAF70C3, false, true, -1, tda, 0);
        RSInterface.addText(110_412, "Purchase", 0xAF70C3, false, true, -1, tda, 0);

        RSInterface.addCloseButton(110_413, 110_414, 110_415);

        RSInterface.addText(110_418, "Search Results:", 0xAF70C3, true, true, -1, tda, 2);

        RSInterface listings = RSInterface.addTabInterface(110_419);
        listings.width = 452;
        listings.height = 200;
        listings.scrollMax = 604;


        final int start = listings.id + 1;
        final int listingElements = 7;
        final int listingCount = 20;
        int childIndex = 0;
        int child = start;

        listings.setChildren(1 + (listingCount * listingElements));

        for (int i = 0; i < listingCount; i++) {
            final int y = i * 30;
            listings.child(childIndex++, child, 0, y);
            addSprite(child++, i % 2 == 0 ? 5581 : 5580); // background

            listings.child(childIndex++, child, 118, 8 + y);
            RSInterface.addText(child++, "Abyssal whip", 0xAF70C3, true, true, -1, tda, 1);

            listings.child(childIndex++, child, 233, 8 + y);
            RSInterface.addText(child++, "1,111,152,206", 0x54EB46, true, true, -1, tda, 1);

            listings.child(childIndex++, child, 331, 8 + y);
            RSInterface.addText(child++, "Mobjunk", 0x808080, true, true, -1, tda, 0);

            listings.child(childIndex++, child, 403, 8 + y);
            addButton(child++, 50, 16, "Buy-All");

            listings.child(childIndex++, child, 403, 8 + y);
            addButton(child++, 50, 16, "Buy-X");

            listings.child(childIndex++, child, 403, 8 + y);
            RSInterface.addClickableText(child++, "Buy", "Buy-1", tda, 1, 0xffff00, 150, 16);
        }

        listings.child(childIndex++, child, 6, 0);
        addItemContainer(child, 1, 20, new String[5], 1, -2);
        for (int i = 0; i < listingCount; i++) {
            RSInterface.get(child).inv[i] = 4152;
            RSInterface.get(child).invStackSizes[i] = i;
        }
        child++;

        RSInterface.setChildren(18, widget);
        RSInterface.setBounds(110_401, 16, 16, 0, widget);
        //RSInterface.setBounds(110_415, 260, 26, 1, widget); //Title
        RSInterface.setBounds(110_412, 418, 59, 1, widget); //Close
        RSInterface.setBounds(110_402, 25, 277, 2, widget); //Go back
        RSInterface.setBounds(110_403, 309, 277, 3, widget); //Prev page
        RSInterface.setBounds(110_404, 407, 277, 4, widget); //Next page
        RSInterface.setBounds(110_405, 66, 286, 5, widget); //Go back
        RSInterface.setBounds(110_406, 352, 286, 6, widget); //modify
        RSInterface.setBounds(110_407, 451, 286, 7, widget); //refresh
        RSInterface.setBounds(110_408, 30, 59, 8, widget); //Quantity
        RSInterface.setBounds(110_409, 131, 59, 9, widget); //Name
        RSInterface.setBounds(110_416, 160, 62, 16, widget); //name sort button
        RSInterface.setBounds(110_410, 231, 59, 10, widget); //Price (each)
        RSInterface.setBounds(110_417, 280, 62, 17, widget); //price sort button
        RSInterface.setBounds(110_411, 341, 59, 11, widget); //Seller
        //RSInterface.setBounds(110_412, 418, 59, 13, widget); //Purchase
        RSInterface.setBounds(110_419, 26, 73, 12, widget); // Container
        RSInterface.setBounds(110_418, 260, 25, 13, widget); // header title
        RSInterface.setBounds(110_413, 480, 23, 14, widget); // close
        RSInterface.setBounds(110_414, 480, 23, 15, widget); // close
    }
}
