package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.Client;
import org.necrotic.client.cache.media.RSInterface;

public class SkillTab extends RSInterface {

    private static final int STARTING_POINT = 86000;
    private static final int WIDTH = 183;
    private static final int HEIGHT = 235;

    public static void unpack() {
        navigation();
        combat();
        skill();
    }

    public static void navigation() {
        RSInterface main = addInterface(STARTING_POINT);
        addSprite(STARTING_POINT + 1, 623);
        add_button(STARTING_POINT + 2, 528, "Switch to Combat");
        add_button(STARTING_POINT + 3, 529, "Switch to Skills");
        addText(STARTING_POINT + 4, "Total Level: 9999", ColorConstants.YELLOW, false, true, 52, 0);

        main.totalChildren(4);
        main.child(0, STARTING_POINT + 1, 4, 4);
        main.child(1, STARTING_POINT + 2, 130, 2);
        main.child(2, STARTING_POINT + 3, 160, 2);
        main.child(3, STARTING_POINT + 4, 10, 7);
    }

    public static void combat() {
        int localIds = STARTING_POINT + 100;
        RSInterface main = addTab(localIds++, 1);
        int childId = 1;
        int childId2 = 0;
        RSInterface combatComp = addInterface(localIds++);
        String[] skills = {"Attack", "Strength", "Defence", "Magic", "Ranged", "Prayer", "Necromancy", "Slayer", "Beast Hunter", "Hitpoints"};
        int[] spriteIds = { 625, 645, 629, 638, 641, 640, 646, 643, 637, 636};
        int[][] iconCoords = {
            { 6, 9 },
            { 68, 9 },
            { 131, 11 },
            { 7, 37 },
            { 68, 38 },
            { 129, 37 },
            { 6, 66 },
            { 67, 64 },
            { 130, 64 },
            { 7, 96 }};
        combatComp.totalChildren(skills.length * 4);
        int row = 0;
        int xButton = 3;
        int yButton = 25;
        int xText = 60;
        int yText = 33;
        for (int i = 0; i < skills.length; i++) {
            addSkillButton(localIds, skills[i]);
            combatComp.child(childId2++, localIds++, xButton, yButton);
            addSpriteLoader(localIds, spriteIds[i]);
            combatComp.child(childId2++, localIds++, iconCoords[i][0], iconCoords[i][1] + 18);
            addSkillText(localIds, false, i);
            combatComp.child(childId2++, localIds++, xText, yText);
            if (row == 2) {
                row = 0;
                xButton = 3;
                xText = 60;
                yButton += 28;
                yText += 28;
                continue;
            }
            xText += 62;
            xButton += 62;
            row++;
        }
        xButton = 3;
        yButton = 25;
        row = 0;
        for (int i = 0; i < skills.length; i++) {
            createSkillHover(localIds, 205 + i);
            combatComp.child(childId2++, localIds++, xButton, yButton);
            if (row == 2) {
                row = 0;
                xButton = 3;
                xText = 29;
                yButton += 28;
                yText += 28;
                continue;
            }
            xText += 62;
            xButton += 62;
            row++;
        }
        main.child(childId, combatComp.id, 0, 0);
    }

    public static void skill() {
        int localIds = STARTING_POINT + 300;
        RSInterface main = addTab(localIds++, 1);
        int childId = 1;
        int childId2 = 0;
        RSInterface combatComp = addInterface(localIds++);
        String[] skills = {"Mining", "Woodcutting", "Journeyman", "Herblore", "Alchemy", "Soulrending", "Arcana"};
        int[] spriteIds = { 639, 648, 511, 635, 519 , 1992, 709};
        int[][] iconCoords = {
            { 6, 9 },
            { 68, 9 },
            { 131, 11 },
            { 7, 37 },
            { 68, 37},
            { 128, 38 },
            { 6, 65 },
                {68, 65}
        };
        combatComp.totalChildren(skills.length * 4);
        int row = 0;
        int xButton = 3;
        int yButton = 25;
        int xText = 60;
        int yText = 33;
        for (int i = 0; i < skills.length; i++) {
            addSkillButton(localIds, skills[i]);
            combatComp.child(childId2++, localIds++, xButton, yButton);
            addSpriteLoader(localIds, spriteIds[i]);
            combatComp.child(childId2++, localIds++, iconCoords[i][0], iconCoords[i][1] + 18);
            addSkillText(localIds, false, i + 10);
            combatComp.child(childId2++, localIds++, xText, yText);
            if (row == 2) {
                row = 0;
                xButton = 3;
                xText = 60;
                yButton += 28;
                yText += 28;
                continue;
            }
            xText += 62;
            xButton += 62;
            row++;
        }
        xButton = 3;
        yButton = 25;
        row = 0;
        for (int i = 0; i < skills.length; i++) {
            createSkillHover(localIds, 215 + i);
            combatComp.child(childId2++, localIds++, xButton, yButton);
            if (row == 2) {
                row = 0;
                xButton = 3;
                xText = 29;
                yButton += 28;
                yText += 28;
                continue;
            }
            xText += 62;
            xButton += 62;
            row++;
        }
        main.child(childId, combatComp.id, 0, 0);
    }

    private static RSInterface addTab(int id, int children) {
        RSInterface tab = addTabInterface(id);
        tab.totalChildren(1 + children);
        tab.child(0, STARTING_POINT, 0, 0);
        return tab;
    }
}
