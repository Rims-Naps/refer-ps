/*
package com.ruse.world.content.titles;

import com.ruse.model.Item;
import com.ruse.model.definitions.NpcDefinition;
import com.ruse.util.Misc;

import lombok.Getter;

import java.util.ArrayList;

import static com.ruse.world.content.titles.Titles.TitleType.*;

@Getter
public enum Titles {

    NONE(RESET, "",  new String[]{"", ""}),

    //LOYALTY
    GOOD(LOYALTY, "Good", 5000),
    EVIL(LOYALTY, "Evil", 5000),
    BARONESS(LOYALTY, "Baroness", 10000),
    BARON(LOYALTY, "Baron", 10000),
    DUCHESS(LOYALTY, "Duchess", 50000),
    DUKE(LOYALTY, "Duke", 50000),
    LORD(LOYALTY, "Lord", 100000),
    LADY(LOYALTY, "Lady", 100000),
    QUEEN(LOYALTY, "Queen", 250000),
    KING(LOYALTY, "King", 250000),
    LOYAL(LOYALTY, "the Loyal##", 1000000),

    // BOSSES
    SCARLET(BOSS, "the Falcon", 2949, 15000),
    HERBAL(BOSS, "Rogue", 2342,15000),
    AZURE(BOSS, "Azure", 3831,15000),
    JOKER(BOSS, "the Joker", 185, 25000),
    CRYSTAL(BOSS, "Crystal", 6430, 25000),
    SUPREME(BOSS, "Supreme", 440, 30000),
    VASA(BOSS, "the Ancient##", 1120, 50000),
    ELITE(BOSS, "the Elite##", 8015, 50000),
    MEGA(BOSS, "the Cursed", 4540, 50000),
    INFERNAL(BOSS, "the Infernal", 12810, 25000),
    LUCIFER(BOSS, "the Satanic", 9012, 50000),
    DARK(BOSS, "Dark", 438, 50000),
    ODIN(BOSS, "the All-Father", 9813, 100000),
    SARA(BOSS, "of Saradomin", 10000, 5000),
    ZAMMY(BOSS, "of Zamorak", 10001, 5000),

    //MISC
    SELFLESS(MISC, "Selfless", new String[]{"- Donate 250m to Server perks", ""}),
    BILLIONAIRE(MISC, "the Billionaire##", new String[]{"- Buy for 1B Upgrade tokens", ""}),
    MAXED(MISC, "Maxed", new String[]{"- Max in all skills",""}),
    DEVOURER(MISC, "the Devourer##", new String[]{"- 2.5m Npc Killcount",""}),
    GRINDER(MISC, "the Grinder##", new String[]{"- 2,000 Hours Playtime",""}),
    NOLIFE(MISC, "No-Life", new String[]{"- 5,000 Hours Playtime",""}),
    ASSASSIN(MISC, "Assassin", new String[]{"- Become an Assassin Master",""}),
    CELESTIAL(MISC, "Celestial", new String[]{"- Unlock the Celestial Zone",""}),

    ;

    private final String name;
    private final TitleType type;
    private final String[] requirements;
    public int cost;
    public int section;
    public int npcId;
    public int amount;
    Titles(TitleType type, String name, int cost) {
        this.type = type;
        this.name = name;
        this.cost = cost;
        this.requirements = new String[]{"- " + Misc.insertCommasToNumber( cost) + " Loyalty Points", ""};
    }
    Titles(TitleType type, String name, int npcId, int amount) {
        this.type = type;
        this.name = name;
        this.npcId = npcId;
        this.amount = amount;
        this.requirements = 
                      "- "+  Misc.insertCommasToNumber(amount) + " " + NpcDefinition.forId(npcId).getName() + " KC"};
    }

    Titles(TitleType type, String name, String[] requirements) {
        this.type = type;
        this.name = name;
        this.requirements = requirements;
    }

    public static ArrayList<Titles> getItems(TitleType type) {
        ArrayList<Titles> items = new ArrayList<>();
        for (Titles s : Titles.values()) {
            if (s.type == type) {
                items.add(s);
            }
        }
        return items;
    }

    public String[] getRequirements() {
        return requirements;
    }

    public String getName() {
        return name;
    }

    public enum TitleType {

        LOYALTY, BOSS,  MISC, RESET
        ;

        TitleType() {
        }
    }
}*/
