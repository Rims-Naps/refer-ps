package com.ruse.world.content.tree;

import com.ruse.model.Item;
import com.ruse.world.content.dialogue.SelectionDialogue;
import com.ruse.world.entity.impl.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeController {

    Player player;

    public TreeController(Player player) {
        this.player = player;
    }

    public void unlockNode(int button) {
        if (!player.isTreeUnlocked()) {
            player.msgRed("You need to unlock the first node to do this");
            return;
        }

        Node nodes[] = Node.values();
        int counter = 0;
        for (Node node : Node.values()) {
            if (player.getCurrentPath() != Path.NONE) {
                if (node.getComponentId() == button) {
                    if (isNodeUnlocked(node)) {
                        player.msgRed("You already have this node unlocked!");
                        return;
                    }
                    if (player.getCurrentPath() != node.getPath()) {
                        player.msgRed("You must be on this path to unlock this node");
                        return;
                    }
                    if (node != Node.FERAL_FURY && node != Node.PERFECTIONIST && node != Node.VERDANT_VITALITY) {
                        if (player.getLastPath() != Path.NONE) {
                            boolean needsReturn = true;
                            switch (node) {
                                case AQUATIC_SURGE:
                                case CALL_TO_GAIA:
                                case INFERNAL_BLAZE:
                                    if (isNodeUnlocked(Node.ALCHEMICAL_MIND) || isNodeUnlocked(Node.ESSENCE_REAVER) || isNodeUnlocked(Node.HERBALIST)) {
                                        needsReturn = false;
                                    }
                                    if (isNodeUnlocked(Node.AQUATIC_SURGE) || isNodeUnlocked(Node.CALL_TO_GAIA) || isNodeUnlocked(Node.INFERNAL_BLAZE)) {
                                        needsReturn = true;
                                    }
                                    if (needsReturn)
                                        return;
                                break;
                                case SORCERERS_VEIL:
                                case HAWKEYE:
                                case BERSERKER:
                                    if (isNodeUnlocked(Node.MAXIMUM_OVERDRIVE) || isNodeUnlocked(Node.FERAL_MASTERY) || isNodeUnlocked(Node.SPIRITUAL_SIPHON)) {
                                        needsReturn = false;
                                    }
                                    if (isNodeUnlocked(Node.SORCERERS_VEIL) || isNodeUnlocked(Node.HAWKEYE) || isNodeUnlocked(Node.BERSERKER)) {
                                        needsReturn = true;
                                    }
                                    if (needsReturn)
                                        return;
                                break;
                                default:
                                    if (!isNodeUnlocked(nodes[counter - 1])) {
                                        player.msgRed("You must have the previous node to unlock this node.");
                                        return;
                                    }
                            }
                        } else {
                            if (!isNodeUnlocked(nodes[counter - 1])) {
                                player.msgRed("You must have the previous node to unlock this node.");
                                return;
                            }
                        }
                    } else
                        return;
                    if (player.getInventory().contains(node.getRequiredItemToUnlock())) {
                        new SelectionDialogue(player,"Unlock Node?",
                            new SelectionDialogue.Selection("Yes", 0, p -> {
                                addNode(node);
                                player.getInventory().delete(node.getRequiredItemToUnlock());
                                player.setCurrentSection(node.getSection());
                                player.msgFancyPurp("You have unlocked " + node.name().replaceAll("_", " ").toLowerCase());
                                p.getPacketSender().sendChatboxInterfaceRemoval();
                                displayInterface();
                            }),
                            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    } else {
                        player.msgRed("You need at least " + node.getRequiredItemToUnlock().getAmount() + " Skill Points to unlock this Node!");
                    }
                    return;//Stop the loop from running as we've reached the correct node
                }
            } else {//assume the player hasn't unlocked a node yet
                if (node.getComponentId() == button) {
                    if (node != Node.FERAL_FURY && node != Node.PERFECTIONIST && node != Node.VERDANT_VITALITY) {
                        player.msgRed("You must unlock the first node in the path to gain access to this node.");
                        return;
                    }
                    if (player.getInventory().contains(node.getRequiredItemToUnlock())) {
                        new SelectionDialogue(player,"Unlock Node?",
                            new SelectionDialogue.Selection("Yes", 0, p -> {
                                addNode(node);
                                player.getInventory().delete(node.getRequiredItemToUnlock());
                                player.setCurrentPath(node.getPath());
                                player.setCurrentSection(node.getSection());
                                player.msgFancyPurp("You start your way down the path of " + node.getPath().name().replaceAll("_", " ").toLowerCase());
                                player.msgFancyPurp("You have unlocked " + node.name().replaceAll("_", " ").toLowerCase());
                                p.getPacketSender().sendChatboxInterfaceRemoval();
                                displayInterface();
                            }),
                            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
                            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
                        ).start();
                    } else {
                        player.msgRed("You need at least " + node.getRequiredItemToUnlock().getAmount() + " Skill Points to unlock this Node!");
                    }
                    return;//Stop the loop from running as we've reached the correct node
                }
            }
            counter++;
        }
    }

    public void addNode(Node node) {
        List<Node> nodeList = new ArrayList<>();
        if (player.getNodesUnlocked() != null) {
            for (int i = 0; i < player.getNodesUnlocked().length; i++) {
                nodeList.add(player.getNodesUnlocked()[i]);
            }
        }
        nodeList.add(node);
        Node[] nodeArray = new Node[nodeList.size()];
        for (int i = 0; i < nodeList.size(); i++) {
            nodeArray[i] = nodeList.get(i);
        }
        player.setNodesUnlocked(nodeArray);
    }

    public void addBridge(Bridge node) {
        List<Bridge> nodeList = new ArrayList<>();
        if (player.getBridgesUnlocked() != null) {
            for (int i = 0; i < player.getBridgesUnlocked().length; i++) {
                nodeList.add(player.getBridgesUnlocked()[i]);
            }
        }
        nodeList.add(node);
        Bridge[] nodeArray = new Bridge[nodeList.size()];
        for (int i = 0; i < nodeList.size(); i++) {
            nodeArray[i] = nodeList.get(i);
        }
        player.setBridgesUnlocked(nodeArray);
    }

    public void bridge(Path path, int buttonId) {
        player.setLastPath(player.getCurrentPath());
        player.setCurrentPath(path);
        for (Bridge bridge : Bridge.values()) {
            if (bridge.getComponentId() == buttonId) {
                addBridge(bridge);
            }
        }
        player.getInventory().delete(2619, 1);
        player.msgFancyPurp("You have bridged to: " + path.name().replaceAll("_", " ").toLowerCase());
        displayInterface();
    }

    public boolean has_requirements_for_first_bridge(int buttonId) {
        int count = 0;
        for (Node node : Node.values()) {
            if (player.getCurrentSection().equals(node.getSection())) {
                if (player.getCurrentPath().equals(node.getPath())) {
                    if (isNodeUnlocked(node)) {
                        count++;
                    }
                }
            }
        }

        int requiredCount = 4;
        if (player.getCurrentSection().equals(Section.SECTION_1))
            requiredCount = 3;
        boolean hasReq = false;
        if (count == requiredCount)
            hasReq = true;

        for (Bridge bridge : Bridge.values()) {
            if (bridge.getComponentId() == buttonId) {
                if (player.getBridgesUnlocked() != null) {
                    if (bridge.equals(Bridge.BRIDGE_TWO)) {
                        if (isBridgeUnlocked(Bridge.BRIDGE_ONE) && !hasTier4Node() && !hasTier8Node())
                            hasReq = true;
                    }
                    if (bridge.equals(Bridge.BRIDGE_ONE)) {
                        if (isBridgeUnlocked(Bridge.BRIDGE_ONE) && !hasTier4Node() && !hasTier8Node())
                            hasReq = true;
                    }
                    if (bridge.equals(Bridge.BRIDGE_THREE)) {
                        if (isBridgeUnlocked(Bridge.BRIDGE_FOUR) && !hasTier8Node() && hasTier4Node())
                            hasReq = true;
                    }
                    if (bridge.equals(Bridge.BRIDGE_FOUR)) {
                        if (isBridgeUnlocked(Bridge.BRIDGE_THREE) && !hasTier8Node() && hasTier4Node())
                            hasReq = true;
                    }
                }
            }
        }
        return hasReq;
    }

    public boolean hasTier4Node() {
        return isNodeUnlocked(Node.AQUATIC_SURGE) || isNodeUnlocked(Node.INFERNAL_BLAZE) || isNodeUnlocked(Node.CALL_TO_GAIA);
    }
    public boolean hasTier8Node() {
        return isNodeUnlocked(Node.SORCERERS_VEIL) || isNodeUnlocked(Node.BERSERKER) || isNodeUnlocked(Node.HAWKEYE);
    }

    public boolean isButton(int buttonId) {
        for (Node node : Node.values()) {
            if (node.getComponentId() == buttonId) {
                return true;
            }
        }
        return false;
    }

    public boolean isNodeUnlocked(Node node) {
        return Arrays.asList(player.getNodesUnlocked()).contains(node);
    }

    public boolean isBridgeUnlocked(Bridge bridge) {
        return Arrays.asList(player.getBridgesUnlocked()).contains(bridge);
    }

    public void displayInterface() {
        for (Node node : Node.values()) {
            player.getPacketSender().sendCustomHoverText(node.getComponentId() + 1, node.name().replaceAll("_", " ") + ": " + node.getDescription());
            player.getPacketSender().changeClientTooltip(node.getComponentId(), node.name().replaceAll("_", " "));
            if (player.getNodesUnlocked() != null) {
                if (isNodeUnlocked(node)) {
                    player.getPacketSender().sendButtonChange(node.getComponentId(), node.getButtonOnIdUnlocked(), node.getButtonOffIdUnlocked());
                }
            } else {
                player.getPacketSender().sendButtonChange(node.getComponentId(), node.getButtonOnIdLocked(), node.getButtonOffIdLocked());
            }
        }
        for (Bridge bridge : Bridge.values()) {
            player.getPacketSender().sendCustomHoverText(bridge.getComponentId() + 1, bridge.getDescription());
            if (player.getBridgesUnlocked() != null) {
                if (isBridgeUnlocked(bridge)) {
                    player.getPacketSender().sendButtonChange(bridge.getComponentId(), bridge.getButtonOnIdUnlocked(), bridge.getButtonOffIdUnlocked());
                    player.getPacketSender().sendInterfaceItems(bridge.getItemSlotId(), new Item[]{new Item(2619)});
                } else {
                    player.getPacketSender().sendInterfaceItems(bridge.getItemSlotId(), new Item[]{new Item(-1)});
                }
            } else {
                player.getPacketSender().sendButtonChange(bridge.getComponentId(), bridge.getButtonOnIdLocked(), bridge.getButtonOffIdLocked());
                player.getPacketSender().sendInterfaceItems(bridge.getItemSlotId(), new Item[]{new Item(-1)});
            }
        }
        if (player.isTreeUnlocked()) {
            player.getPacketSender().sendButtonChange(90060, 1659, 1660);
        } else {
            player.getPacketSender().sendButtonChange(90060, 1657, 1658);
        }
        player.getPacketSender().sendInterface(90000);
    }

    public void resetScroll() {
        if (!player.getInventory().contains(2620)) {
            return;
        }
        if (player.getNodesUnlocked() == null) {
            player.sendMessage("I'd suggest unlocking more of the tree before doing this.");
            return;
        }
        new SelectionDialogue(player,"Reset Skill Tree?",
            new SelectionDialogue.Selection("Yes", 0, p -> {
                for (Node nodes : Node.values()) {
                    if (isNodeUnlocked(nodes)) {
                        player.getInventory().add(nodes.getRequiredItemToUnlock());
                    }
                }
                nullify();
                player.sendMessage("You reset your skill tree.");
                displayInterface();
                player.getInventory().delete(2620, 1);
            }),
            //new SelectionDialogue.Selection("Search for an item", 1, p -> openSelectSearchItemId()),
            new SelectionDialogue.Selection("No", 1, p -> p.getPacketSender().sendChatboxInterfaceRemoval())
        ).start();
    }

    private void nullify() {
        player.setBridgesUnlocked(null);
        player.setCurrentPath(Path.NONE);
        player.setNodesUnlocked(null);
        player.setCurrentSection(Section.NONE);
        player.setLastPath(Path.NONE);
    }


}
