package com.ruse.world.content.BattlePass;

import com.ruse.model.Item;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum BattlePassData
{
    Level1(1, new Item(5585, 2), new Item(5585, 4), BattlePassPages.PAGE_ONE), // FREE TO PLAY - P2W
    Level2(2, new Item(15669, 1), new Item(15669, 2), BattlePassPages.PAGE_ONE),
    Level3(3, new Item(23173, 1), new Item(23173, 2), BattlePassPages.PAGE_ONE),
    Level4(4, new Item(17129, 1), new Item(17129, 2), BattlePassPages.PAGE_ONE),
    Level5(5, new Item(10946, 1), new Item(10946, 2), BattlePassPages.PAGE_ONE),
    Level6(6, new Item(5585, 5), new Item(5585, 10), BattlePassPages.PAGE_ONE),
    Level7(7, new Item(1447, 3), new Item(1447, 6), BattlePassPages.PAGE_ONE),
    Level8(8, new Item(15668, 2), new Item(15668, 4), BattlePassPages.PAGE_ONE),
    Level9(9, new Item(23173, 2), new Item(23173, 4), BattlePassPages.PAGE_TWO),
    Level10(10, new Item(23057, 1), new Item(23057, 2), BattlePassPages.PAGE_TWO),
    Level11(11, new Item(5585, 7), new Item(5585, 15), BattlePassPages.PAGE_TWO),
    Level12(12, new Item(1448, 5), new Item(1448, 10), BattlePassPages.PAGE_TWO),
    Level13(13, new Item(17129, 1), new Item(17129, 2), BattlePassPages.PAGE_TWO),
    Level14(14, new Item(17130, 1), new Item(17130, 2), BattlePassPages.PAGE_TWO),
    Level15(15, new Item(1540, 5), new Item(1540, 10), BattlePassPages.PAGE_TWO),
    Level16(16, new Item(15668, 2), new Item(15668, 4), BattlePassPages.PAGE_TWO),
    Level17(17, new Item(23057, 1), new Item(23057, 2), BattlePassPages.PAGE_THREE),
    Level18(18, new Item(5585, 10), new Item(5585, 20), BattlePassPages.PAGE_THREE),
    Level19(19, new Item(17129, 1), new Item(17129, 2), BattlePassPages.PAGE_THREE),
    Level20(20, new Item(460, 1), new Item(461, 1), BattlePassPages.PAGE_THREE),
    Level21(21, new Item(5585, 5), new Item(5585, 10), BattlePassPages.PAGE_THREE),
    Level22(22, new Item(15669, 3), new Item(15669, 6), BattlePassPages.PAGE_THREE),
    Level23(23, new Item(23173, 1), new Item(23173, 2), BattlePassPages.PAGE_THREE),
    Level24(24, new Item(17129, 1), new Item(17129, 2), BattlePassPages.PAGE_THREE),
    Level25(25, new Item(10946, 1), new Item(10946, 2), BattlePassPages.PAGE_FOUR),
    Level26(26, new Item(5585, 5), new Item(5585, 10), BattlePassPages.PAGE_FOUR),
    Level27(27, new Item(1447, 3), new Item(1447, 6), BattlePassPages.PAGE_FOUR),
    Level28(28, new Item(15668, 2), new Item(15668, 4), BattlePassPages.PAGE_FOUR),
    Level29(29, new Item(23173, 2), new Item(23173, 4), BattlePassPages.PAGE_FOUR),
    Level30(30, new Item(23057, 1), new Item(23057, 2), BattlePassPages.PAGE_FOUR),
    Level31(31, new Item(5585, 7), new Item(5585, 15), BattlePassPages.PAGE_FOUR),
    Level32(32, new Item(17129, 3), new Item(17129, 6), BattlePassPages.PAGE_FOUR),
    Level33(33, new Item(23172, 1), new Item(23172, 2), BattlePassPages.PAGE_FIVE),
    Level34(34, new Item(23057, 1), new Item(23057, 2), BattlePassPages.PAGE_FIVE),
    Level35(35, new Item(17130, 3), new Item(17130, 6), BattlePassPages.PAGE_FIVE),
    Level36(36, new Item(15668, 2), new Item(15668, 4), BattlePassPages.PAGE_FIVE),
    Level37(37, new Item(19118, 1), new Item(19118, 2), BattlePassPages.PAGE_FIVE),
    Level38(38, new Item(3578, 1), new Item(3578, 2), BattlePassPages.PAGE_FIVE),
    Level39(39, new Item(4022, 1), new Item(4022, 2), BattlePassPages.PAGE_FIVE),
    Level40(40, new Item(461, 1), new Item(462, 1), BattlePassPages.PAGE_FIVE),
    ;
    @Getter
    private int level;
    @Getter
    private Item tier1reward, tier2reward;
    @Getter
    private BattlePassPages page;

    BattlePassData(int level, Item bronzeReward, Item goldReward, BattlePassPages page)
    {
        this.level = (level);
        this.tier1reward = (bronzeReward);
        this.tier2reward = (goldReward);
        this.page = (page);
    }

    static final Map<Integer, BattlePassData> byId = new HashMap<Integer, BattlePassData>();

    static
    {
        for (BattlePassData e : BattlePassData.values())//onesec
        {
            if (byId.put(e.getLevel(), e) != null) {
                throw new IllegalArgumentException("duplicate id: " + e.getLevel());
            }
        }
    }

    public static BattlePassData getByLevel(int id)
    {
        if(byId.get(id) == null)
        {
            return byId.get(0);
        }
        return byId.get(id);
    }
}