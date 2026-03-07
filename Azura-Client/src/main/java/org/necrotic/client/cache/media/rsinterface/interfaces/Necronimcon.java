package org.necrotic.client.cache.media.rsinterface.interfaces;

import org.necrotic.ColorConstants;
import org.necrotic.client.cache.media.RSInterface;
import org.necrotic.client.cache.media.TextDrawingArea;

public class Necronimcon extends RSInterface {

    public static int STARTING_POINT = 27585;

    private static final int WIDTH = 183;
    private static final int HEIGHT = 230;

    public static void unpack(TextDrawingArea[] tda) {
        setRunes();
        necronomicon(tda);
    }

    public static void necronomicon(TextDrawingArea[] tda) {
        int localIdsForHover = STARTING_POINT + 300;
        int localIds = STARTING_POINT;
        int child = 0;
        int childX = 19;
        int childY = 45;
        int row = 0;
        int column = 0;
        int childXModified = childX;
        int index = 0;

        /**
         Rune ItemId = Sprite
         (Curse)20010 = 208 - 27985
         (Wraith)20011 = 209 - 27986
         (Shadow)20012 = 210 - 27987
         (Void)20013 = 211 - 27988
         (Soul)20014 = 212 - 27989
         (Crypt)20015 = 213 - 27990 **/
        Object[][] necroData = {{5679, "@mag@Deathwalker", "Summons a Skeletal Follower", 1, 2,
                20010, 2, 27985, 20015, 3, 27990},
            {5680, "@mag@Arrowshade ", "Summons a Skeletal Archer Follower", 15, 2,
                    20010, 3, 27985, 20015, 4, 27990},
            {5681, "@mag@Bonemancer", "Summons a Skeletal Mage Follower", 20, 2,
                    20010, 4, 27985, 20015, 5, 27990},
            {5678, "@mag@Skeletal Servant", "Increased Skilling Resources/Exp", 25, 2,
                    20010, 5, 27985, 20015, 5, 27990},

            {5683, "@gre@Shadowfiend", "Summons a baby Imp Follower", 35, 2,
                    20010, 3, 27985, 20014, 4, 27989},
            {5684, "@gre@Devilspawn", "Summons a Demonic Follower", 40, 2,
                    20010, 4, 27985, 20014, 5, 27989},
            {5685, "@gre@Abyssal Tormentor", "Summons a King Demon Follower", 40, 2,
                    20010, 5, 27985, 20014, 6, 27989},
            {5682, "@gre@Demonic Servant", "Increased Skilling Resources/Exp", 45, 2,
                    20010, 5, 27985, 20014, 5, 27989},

            {5687, "@yel@Grunt Mauler", "Summons an Ogre Follower", 55, 3,
                    20010, 5, 27985, 20015, 5, 27990, 20012, 2, 27987},
            {5688, "@yel@Brute Crusher", "Summons an Ogre Warrior Follower", 60, 3,
                    20010, 6, 27985, 20015, 6, 27990, 20012, 3, 27987},
            {5689, "@yel@Vinesplitter", "Summons an Ogre Queen Follower", 65, 3,
                    20010, 7, 27985, 20015, 8, 27990, 20012, 4, 27987},
            {5686, "@yel@Ogre Servant", "Increased Skilling Resources/Exp", 70, 3,
                    20010, 7, 27985, 20015, 7, 27990, 20012, 3, 27987},

            {5691, "@cya@Phantom Drifter", "Summons a Ghostly Follower", 75, 3,
                20010, 6, 27985, 20014, 5, 27989, 20011, 2, 27986},
            {5692, "@cya@Whispering Wraith", "Summons a Spectral Follower", 80, 2,
                    20010, 7, 27985, 20014, 6, 27989, 20011, 3, 27986},
            {5693, "@cya@Banshee King", "Summons a Banshee King Follower", 85, 3,
                    20010, 8, 27985, 20014, 7, 27989, 20011, 4, 27986},
            {5690, "@cya@Spectral Servant", "Increased Skilling Resources/Exp", 90, 3,
                20010, 8, 27985, 20014, 8, 27989, 20011, 3, 27986},

            {5695, "@red@Eye of the Beyond", "Summons a Nightmare Follower", 95, 3,
                    20012, 10, 27987, 20011, 10, 27986, 20013, 3, 27988},
            {5696, "@red@Talonwing", "Summons a Phoenix Follower", 100, 3,
                    20012, 15, 27987, 20011, 15, 27986, 20013, 4, 27988},
            {5697, "@red@Umbral Beast", "Summons an Undead Beast Follower", 105, 3,
                    20012, 25, 27987, 20011, 20, 27986, 20013, 5, 27988},
            {5694, "@red@Master Servant", "Increased Skilling Resources/Exp", 112, 3,
                    20012, 25, 27987, 20011, 25, 27986, 20012, 5, 27988}};
        int indexRune = necroData.length;
        RSInterface necronomiconMain = addTabInterface(localIds++);
        necronomiconMain.totalChildren(5);
        addHoverButtonWSpriteLoader(localIdsForHover, 1367, 77, 23, "Teleport Home", -1, localIdsForHover + 1, 1);
        necronomiconMain.child(child++, localIdsForHover++, 57, 3);
        addHoveredImageWSpriteLoader(localIdsForHover, 1368, 77, 23, localIdsForHover + 1);
        necronomiconMain.child(child++, localIdsForHover++, 57, 3);
        addText(++localIdsForHover, "Home", 0xAF70C3, true, true, 52, 2);
        necronomiconMain.child(child++, localIdsForHover++, 95, 7);
        addSpriteLoader(localIdsForHover, 5698);
        necronomiconMain.child(child++, localIdsForHover, 6, 28);
        RSInterface necronomiconSpell = addTabInterface(localIds++);
        setChildren(necroData.length * 2, necronomiconSpell);
        for (int i = 0; i < necroData.length; i++) {
            if ((Integer) necroData[i][4] == 3)
                addNecro3Rune(localIds, (Integer) necroData[i][5], (Integer) necroData[i][8], (Integer) necroData[i][11], (Integer) necroData[i][6], (Integer) necroData[i][9], (Integer) necroData[i][12], (Integer) necroData[i][7], (Integer) necroData[i][10], (Integer) necroData[i][13], (Integer) necroData[i][3], (String) necroData[i][1], (String) necroData[i][2], tda, (Integer) necroData[i][0], 16, 5);
            else if ((Integer) necroData[i][4] == 2)
                addNecro2Rune(localIds, (Integer) necroData[i][5], (Integer) necroData[i][8], (Integer) necroData[i][6], (Integer) necroData[i][9],  (Integer) necroData[i][7], (Integer) necroData[i][10],  (Integer) necroData[i][3], (String) necroData[i][1], (String) necroData[i][2], tda, (Integer) necroData[i][0], 16, 5);
            else
                addNecroRune(localIds, (Integer) necroData[i][5], (Integer) necroData[i][6], (Integer) necroData[i][7], (Integer) necroData[i][3], (String) necroData[i][1], (String) necroData[i][2], tda, (Integer) necroData[i][0], 16, 5);
            setBounds(localIds++, childXModified, childY, index++, necronomiconSpell);
            if (column >= 3)
                setBounds(localIds, 5, 20, indexRune++, necronomiconSpell);
            else
                setBounds(localIds, 5, 180, indexRune++, necronomiconSpell);
            localIds += 7;
            childXModified+= 40;
            if (row < 4)
                row++;
            if (row == 4) {
                row = 0;
                column++;
                childXModified = childX;
                childY += 40;
            }
        }
        necronomiconMain.child(child, necronomiconSpell.id, 0, 0);
    }

    /**
     Rune ItemId = Sprite
     (Curse)20010 = 208
     (Wraith)20011 = 209
     (Shadow)20012 = 210
     (Void)20013 = 211
     (Soul)20014 = 212
     (Crypt)20015 = 213 **/
    public static void setRunes() {
        drawRune(27985, 208);
        drawRune(27986, 209);
        drawRune(27987, 210);
        drawRune(27988, 211);
        drawRune(27989, 212);
        drawRune(27990, 213);
    }
}
