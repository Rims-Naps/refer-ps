package com.ruse.world.content.BattlePass;

import com.ruse.model.container.impl.Bank;
import com.ruse.world.entity.impl.player.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.Date;

public class BattlePass {
    protected Player player;

    public BattlePass(Player player) {
        this.player = (player);
    }

    @Getter
    @Setter
    private boolean ResetSeasonOne = false;
    @Getter
    @Setter
    private int BattlePass_Tier1_XP = 0;
    @Getter
    @Setter
    private int BattlePass_Tier2_XP = 0;
    @Getter
    @Setter
    private int BattlePass_Tier1_Level = 0;
    @Getter
    @Setter
    private int BattlePass_Tier2_Level = 0;
    @Getter
    @Setter
    private BattlePassType type = BattlePassType.NONE;
    @Getter
    @Setter
    private BattlePassPages currentPage = BattlePassPages.PAGE_ONE;

    public void setClaimDate() {

    }

    public void displayPage() {
        int count1 = 0;
        int count2 = 0;
        player.getPA().resetItemsOnInterface(13798, 8).resetItemsOnInterface(13799, 8);
        for (BattlePassData data : BattlePassData.values()) {
            if (currentPage.equals(data.getPage())) {
                player.getPacketSender().sendItemOnInterface(13798, data.getTier1reward().getId(), count1++, data.getTier1reward().getAmount());
                player.getPacketSender().sendItemOnInterface(13799, data.getTier2reward().getId(), count2++, data.getTier2reward().getAmount());

            }
        }
        player.getPA().sendString(13784, "Battle Pass");

        player.getPA().sendString(13789, "Current Level: " + "" + (player.getBattlePass().getType() == BattlePassType.TIER2 ? BattlePass_Tier2_Level : BattlePass_Tier1_Level));

        if (player.getBattlePass().getType() == BattlePassType.TIER2){
            player.getPA().sendString(13789, "          " + "" + this.BattlePass_Tier2_Level);
        } else  if (player.getBattlePass().getType() == BattlePassType.TIER1){
            player.getPA().sendString(13789, "          " + "" + this.BattlePass_Tier1_Level);
        }

        if (player.getBattlePass().getType() == BattlePassType.TIER2){
            player.getPA().sendString(13790, "       " + "" + this.BattlePass_Tier2_XP);
        } else  if (player.getBattlePass().getType() == BattlePassType.TIER1){
            player.getPA().sendString(13790, "       " + "" + this.BattlePass_Tier1_XP);
        }

        player.getPA().sendString(13791, "         " + "" + this.type.toString());

        player.getPA().sendString(13784, "");
        player.getPA().sendString(13792, "Tier 1");
        player.getPA().sendString(13793, "Tier 2");

        checklevelUp();
        updateLevels(player);
        player.getPA().sendInterface(13782);

        player.getPacketSender().sendSpriteChange(13783, 2014);

        player.getPA().sendString(13788, "");
        player.getPA().sendString(13800, "");
    }

    public void unlockTier1() {
        if (player.getBattlePass().getType() == BattlePassType.TIER1){
            player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Tier(1) Pass.");
            return;
        }
        if (player.getBattlePass().getType() == BattlePassType.TIER2) {
            player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Tier(2) Pass.");
            return;
        }
        player.getBattlePass().setType(BattlePassType.TIER1);

        Calendar c = Calendar.getInstance();

        c.setTime(new Date()); // Now use today date.
        player.BattlePassClaimed = c.getTime().toString();
        Calendar c1 = Calendar.getInstance();

        c1.setTime(new Date()); // Now use today date.

        c1.add(Calendar.DATE, 30);
        player.BattlePassExpires = c1.getTime().toString();
        player.getPA().sendMessage("@blu@<shad=0>You have claimed the Tier(1) Battle pass!");
        player.getInventory().delete(463,1);
    }

    public void unlockTier2() {
        if (player.getBattlePass().getType() == BattlePassType.TIER2) {
            player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Tier(2) Pass.");
            return;
        }
        if (player.getBattlePass().getType() == BattlePassType.TIER1) {
            player.getPacketSender().sendMessage("@blu@<shad=0>You already have the @yel@Tier(1) Pass.");
            return;
        }
        player.getBattlePass().setType(BattlePassType.TIER2);
        for (BattlePassData data : BattlePassData.values()) {
            int level = player.getBattlePass().getType() == BattlePassType.TIER2 ? player.getBattlePass().getBattlePass_Tier2_Level() : player.getBattlePass().getBattlePass_Tier1_Level();
            if (data.getLevel() <= level) {
                player.getBank(Bank.getTabForItem(player, data.getTier1reward().getId())).add(data.getTier1reward());
                player.getBank(Bank.getTabForItem(player, data.getTier2reward().getId())).add(data.getTier2reward());
            }
        }
        Calendar c = Calendar.getInstance();

        c.setTime(new Date()); // Now use today date.
        player.BattlePassTier2Claimed = c.getTime().toString();
        Calendar c1 = Calendar.getInstance();

        c1.setTime(new Date()); // Now use today date.

        c1.add(Calendar.DATE, 30);
        player.BattlePassTier2Expires = c1.getTime().toString();
        player.getPA().sendMessage("@blu@<shad=0>You have claimed the Tier(2) Battle pass!");
        player.getInventory().delete(464,1);

    }

    public void updateLevels(Player player) {
        if (player.getBattlePass().getCurrentPage() == BattlePassPages.PAGE_ONE) {
            /** BRONZE **/
            player.getPA().sendString(13802, "lvl 1");
            player.getPA().sendString(13803, "lvl 2");
            player.getPA().sendString(13804, "lvl 3");
            player.getPA().sendString(13805, "lvl 4");
            player.getPA().sendString(13806, "lvl 5");
            player.getPA().sendString(13807, "lvl 6");
            player.getPA().sendString(13808, "lvl 7");
            player.getPA().sendString(13809, "lvl 8");
            /** GOLD **/
            player.getPA().sendString(13810, "lvl 1");
            player.getPA().sendString(13811, "lvl 2");
            player.getPA().sendString(13812, "lvl 3");
            player.getPA().sendString(13813, "lvl 4");
            player.getPA().sendString(13814, "lvl 5");
            player.getPA().sendString(13815, "lvl 6");
            player.getPA().sendString(13816, "lvl 7");
            player.getPA().sendString(13817, "lvl 8");
        } else if (player.getBattlePass().getCurrentPage() == BattlePassPages.PAGE_TWO) {
            /** BRONZE **/
            player.getPA().sendString(13802, "lvl 9");
            player.getPA().sendString(13803, "lvl 10");
            player.getPA().sendString(13804, "lvl 11");
            player.getPA().sendString(13805, "lvl 12");
            player.getPA().sendString(13806, "lvl 13");
            player.getPA().sendString(13807, "lvl 14");
            player.getPA().sendString(13808, "lvl 15");
            player.getPA().sendString(13809, "lvl 16");
            /** GOLD **/
            player.getPA().sendString(13810, "lvl 9");
            player.getPA().sendString(13811, "lvl 10");
            player.getPA().sendString(13812, "lvl 11");
            player.getPA().sendString(13813, "lvl 12");
            player.getPA().sendString(13814, "lvl 13");
            player.getPA().sendString(13815, "lvl 14");
            player.getPA().sendString(13816, "lvl 15");
            player.getPA().sendString(13817, "lvl 16");
        } else if (player.getBattlePass().getCurrentPage() == BattlePassPages.PAGE_THREE) {
            /** BRONZE **/
            player.getPA().sendString(13802, "lvl 17");
            player.getPA().sendString(13803, "lvl 18");
            player.getPA().sendString(13804, "lvl 19");
            player.getPA().sendString(13805, "lvl 20");
            player.getPA().sendString(13806, "lvl 21");
            player.getPA().sendString(13807, "lvl 22");
            player.getPA().sendString(13808, "lvl 23");
            player.getPA().sendString(13809, "lvl 24");
            /** GOLD **/
            player.getPA().sendString(13810, "lvl 17");
            player.getPA().sendString(13811, "lvl 18");
            player.getPA().sendString(13812, "lvl 19");
            player.getPA().sendString(13813, "lvl 20");
            player.getPA().sendString(13814, "lvl 21");
            player.getPA().sendString(13815, "lvl 22");
            player.getPA().sendString(13816, "lvl 23");
            player.getPA().sendString(13817, "lvl 24");
        } else if (player.getBattlePass().getCurrentPage() == BattlePassPages.PAGE_FOUR) {
            /** BRONZE **/
            player.getPA().sendString(13802, "lvl 25");
            player.getPA().sendString(13803, "lvl 26");
            player.getPA().sendString(13804, "lvl 27");
            player.getPA().sendString(13805, "lvl 28");
            player.getPA().sendString(13806, "lvl 29");
            player.getPA().sendString(13807, "lvl 30");
            player.getPA().sendString(13808, "lvl 31");
            player.getPA().sendString(13809, "lvl 32");
            /** GOLD **/
            player.getPA().sendString(13810, "lvl 25");
            player.getPA().sendString(13811, "lvl 26");
            player.getPA().sendString(13812, "lvl 27");
            player.getPA().sendString(13813, "lvl 28");
            player.getPA().sendString(13814, "lvl 29");
            player.getPA().sendString(13815, "lvl 30");
            player.getPA().sendString(13816, "lvl 31");
            player.getPA().sendString(13817, "lvl 32");
        } else if (player.getBattlePass().getCurrentPage() == BattlePassPages.PAGE_FIVE) {
            /** BRONZE **/
            player.getPA().sendString(13802, "lvl 33");
            player.getPA().sendString(13803, "lvl 34");
            player.getPA().sendString(13804, "lvl 35");
            player.getPA().sendString(13805, "lvl 36");
            player.getPA().sendString(13806, "lvl 37");
            player.getPA().sendString(13807, "lvl 38");
            player.getPA().sendString(13808, "lvl 39");
            player.getPA().sendString(13809, "lvl 40");
            /** GOLD **/
            player.getPA().sendString(13810, "lvl 33");
            player.getPA().sendString(13811, "lvl 34");
            player.getPA().sendString(13812, "lvl 35");
            player.getPA().sendString(13813, "lvl 36");
            player.getPA().sendString(13814, "lvl 37");
            player.getPA().sendString(13815, "lvl 38");
            player.getPA().sendString(13816, "lvl 39");
            player.getPA().sendString(13817, "lvl 40");
        }
    }

    public void checklevelUp() {//thats the max level so it caps it i c
        boolean isT2 = this.getType() == BattlePassType.TIER2;
        if ((isT2 && BattlePass_Tier2_Level == 40) || (!isT2 && BattlePass_Tier1_Level == 40)) {
            //  System.out.println("->>");
            return;
        }
        // System.out.println("Current xp: " + goldXp + " and " + bronzeXp + " for type " + this.getType());
        if (isT2 && BattlePass_Tier2_XP >= 250000) {
            BattlePass_Tier2_XP -= 250000;
            BattlePass_Tier2_Level++;
            player.getPA().sendMessage("@blu@<shad=0>Congratulations! Your Battle Pass leveled up! Your level now is @red@" + this.BattlePass_Tier2_Level);
            giveReward(BattlePass_Tier2_Level, type);
        } else if (!isT2 && BattlePass_Tier1_XP >= 250000) {
            BattlePass_Tier1_XP -= 250000;
            BattlePass_Tier1_Level++;
            player.getPA().sendMessage("@blu@<shad=0>Congratulations! Your Battle Pass leveled up! Your level now is @red@" + this.BattlePass_Tier1_Level);
            giveReward(BattlePass_Tier1_Level, type);
        }
    }

    public void giveReward(int level, BattlePassType type) {
        BattlePassData e = BattlePassData.getByLevel(level);
        String bronzeReward = e.getTier1reward().getDefinition().getName();
        String goldReward = e.getTier2reward().getDefinition().getName();

        if (type == BattlePassType.TIER1) {
            if (e.getTier1reward().getId() != -1) {
                player.getPA().sendMessage("@blu@<shad=0>[BattlePass] " + bronzeReward + ", has been sent to your bank." );
                player.getBank(Bank.getTabForItem(player, e.getTier1reward().getId())).add(e.getTier1reward());

            }
        } else if (type == BattlePassType.TIER2) {
            if (e.getTier2reward().getId() != -1) {
                player.getPA().sendMessage("@blu@<shad=0>[BattlePass] " + goldReward + ", has been sent to your bank." );
                player.getBank(Bank.getTabForItem(player, e.getTier2reward().getId())).add(e.getTier2reward());

            }
        }
    }

    public void nextPage() {
        switch (getCurrentPage()) {
            case PAGE_ONE:
                setCurrentPage(BattlePassPages.PAGE_TWO);
                updateLevels(player);
                break;
            case PAGE_TWO:
                setCurrentPage(BattlePassPages.PAGE_THREE);
                updateLevels(player);
                break;
            case PAGE_THREE:
                setCurrentPage(BattlePassPages.PAGE_FOUR);
                updateLevels(player);
                break;
            case PAGE_FOUR:
                setCurrentPage(BattlePassPages.PAGE_FIVE);
                updateLevels(player);
                break;
            case PAGE_FIVE:
                updateLevels(player);
                player.getPA().sendMessage("@blu@<shad=0>Last Page...");
                break;
            default:
                break;
        }
    }

    public void prevPage() {
        switch (getCurrentPage()) {
            case PAGE_ONE:
                player.getPA().sendMessage("@blu@<shad=0>First Page...");
                break;
            case PAGE_TWO:
                setCurrentPage(BattlePassPages.PAGE_ONE);
                updateLevels(player);
                break;
            case PAGE_THREE:
                setCurrentPage(BattlePassPages.PAGE_TWO);
                updateLevels(player);
                break;
            case PAGE_FOUR:
                setCurrentPage(BattlePassPages.PAGE_THREE);
                updateLevels(player);
                break;
            case PAGE_FIVE:
                setCurrentPage(BattlePassPages.PAGE_FOUR);
                updateLevels(player);
                break;
            default:
                break;
        }
    }

    public boolean handleClick(int Id) {
        if (Id == 13795) {
            prevPage();
            displayPage();
        } else if (Id == 13794) {
            nextPage();
            displayPage();
        } else if (Id == 22168) {
            player.getPA().closeAllWindows();
        } else if (Id == 13797) {
            player.getPA().sendString(1, "https://refer-ps.teamgames.io/services/store"); // GOLD PASS LINK
        } else if (Id == 13796) {
            player.getPA().sendString(1, "https://refer-ps.teamgames.io/services/store"); // BRONZE PASS LINK
        }
        return false;
    }

    public void addExperience(int exp) {
        boolean isT2 = this.getType() == BattlePassType.TIER2;
        if ((isT2 && BattlePass_Tier2_Level == 40) || (!isT2 && BattlePass_Tier1_Level == 40)) {
            //System.out.println("->");
            return;
        }

        if (isT2) {
            this.BattlePass_Tier2_XP += exp;
        } else {
            BattlePass_Tier1_XP += exp;
        }
        // System.out.println("Added " + exp + " to " + (isT2 ? "gold" : "bronze"));
        checklevelUp();
    }


}
