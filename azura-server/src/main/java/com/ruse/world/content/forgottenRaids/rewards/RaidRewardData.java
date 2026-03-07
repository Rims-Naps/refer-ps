package com.ruse.world.content.forgottenRaids.rewards;

import com.ruse.model.Item;
import com.ruse.util.Misc;
import com.ruse.world.World;
import com.ruse.world.entity.impl.player.Player;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum RaidRewardData {

    COMMON_REWARD_1(new Item(995, 7241), RaidRewardRarity.COMMON),
    COMMON_REWARD_2(new Item(995, 7441), RaidRewardRarity.COMMON),
    COMMON_REWARD_3(new Item(995, 9241), RaidRewardRarity.COMMON),
    COMMON_REWARD_4(new Item(995, 14240), RaidRewardRarity.COMMON),
    COMMON_REWARD_5(new Item(995, 8943), RaidRewardRarity.COMMON),
    COMMON_REWARD_6(new Item(995, 6993), RaidRewardRarity.COMMON),
    COMMON_REWARD_7(new Item(995, 12567), RaidRewardRarity.COMMON),

    RARE1(new Item(10834, 150), RaidRewardRarity.RARE),
    RARE2(new Item(10246, 1), RaidRewardRarity.RARE),
    RARECOINS(new Item(995, 25000), RaidRewardRarity.RARE),
    RARE3(new Item(6183, 1), RaidRewardRarity.RARE),
    BOXES1(new Item(6833, 2), RaidRewardRarity.RARE),
    RARE4(new Item(15358, 1), RaidRewardRarity.RARE),
    RARE5(new Item(19051, 1), RaidRewardRarity.RARE),
    RARECOINS2(new Item(995, 25000), RaidRewardRarity.RARE),
    BOXES2(new Item(6833, 1), RaidRewardRarity.RARE),
    RARE8(new Item(15357, 2), RaidRewardRarity.RARE),

    RAIDFRAG2(new Item(7252, 1), RaidRewardRarity.EXOTIC),


    ACCURSED1(new Item(3500, 1), RaidRewardRarity.EXOTIC),
    ACCURSED2(new Item(3501, 1), RaidRewardRarity.EXOTIC),
    EXOTICCOINS1(new Item(995, 250000), RaidRewardRarity.EXOTIC),
    ACCURSED3(new Item(3502, 1), RaidRewardRarity.EXOTIC),
    ACCURSED4(new Item(3503, 1), RaidRewardRarity.EXOTIC),
    EXOTICCOINS4(new Item(995, 250000), RaidRewardRarity.EXOTIC),
    ACCURSED5(new Item(3504, 1), RaidRewardRarity.EXOTIC),
    ACCURSEDBOW(new Item(10230, 1), RaidRewardRarity.EXOTIC),
    ICESLED(new Item(18437, 1), RaidRewardRarity.EXOTIC),
    LEGION1(new Item(3490, 1), RaidRewardRarity.EXOTIC),
    LEGION2(new Item(3491, 1), RaidRewardRarity.EXOTIC),
    LEGION3(new Item(3492, 1), RaidRewardRarity.EXOTIC),
    EXOTICCOINS2(new Item(995, 250000), RaidRewardRarity.EXOTIC),
    LEGION4(new Item(3493, 1), RaidRewardRarity.EXOTIC),
    LEGION5(new Item(3494, 1), RaidRewardRarity.EXOTIC),
    LEGIONSCYTHE(new Item(22091, 1), RaidRewardRarity.EXOTIC),
    GALAXYPOT(new Item(15328, 1), RaidRewardRarity.EXOTIC),
    ASTRADOX1(new Item(3495, 1), RaidRewardRarity.EXOTIC),
    ASTRADOX2(new Item(3496, 1), RaidRewardRarity.EXOTIC),
    EXOTICCOINS3(new Item(995, 300000), RaidRewardRarity.EXOTIC),
    ASTRADOX3(new Item(3497, 1), RaidRewardRarity.EXOTIC),
    ASTRADOX4(new Item(3498, 1), RaidRewardRarity.EXOTIC),
    UNIVERSALCASKET(new Item(10250, 1), RaidRewardRarity.EXOTIC),
    ASTRADOX5(new Item(3499, 1), RaidRewardRarity.EXOTIC),
    ASTRADOXWAND(new Item(10228, 1), RaidRewardRarity.EXOTIC),

    ;

    private final Item reward;
    private final RaidRewardRarity rarity;

    public static List<RaidRewardData> getRewardsForRarity(RaidRewardRarity rarity) {
        List<RaidRewardData> toReturn = new ArrayList<>();
        for (RaidRewardData data : values())
            if (data.rarity == rarity)
                toReturn.add(data);
        return toReturn;

    }

    public RaidRewardRarity getRarity() {return rarity;}

    public Item getReward() {
        return reward;
    }

    public static RaidRewardData getRandomReward() {
        return Misc.random(getRewardsForRarity(getRandomRarity()));
    }

    public static RaidRewardRarity getRandomRarity() {
        int chance = Misc.inclusiveRandom(1,400);
        if (chance == 1)
            return RaidRewardRarity.EXOTIC;
        else if (chance > 1 && chance < 60)
            return RaidRewardRarity.RARE;
        else return RaidRewardRarity.COMMON;
    }
}
