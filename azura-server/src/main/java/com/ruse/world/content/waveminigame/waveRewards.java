package com.ruse.world.content.waveminigame;

import com.ruse.model.Item;
import lombok.Getter;

public class waveRewards {

    public enum waveLoot {

        WAVE_1(1,  new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_2(2 , new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_3(3,  new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_4(4, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_5(5, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_6(6, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_7(7, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_8(8, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_9(9, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_10(10, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_11(11, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_12(12, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_13(13, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_14(14, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_15(15, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_16(16, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_17(17, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_18(18, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_19(19, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_20(20, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_21(21, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_22(22, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_23(23, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_24(24, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_25(25, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_26(26, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_27(27, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_28(28, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_29(29, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_30(30, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_31(31, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_32(32, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_33(33, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_34(34, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_35(35, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_36(36, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_37(37, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_38(38, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_39(39, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_40(40, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_41(41, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_42(42, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_43(43, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_44(44, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_45(45, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_46(46, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_47(47, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_48(48, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_49(49, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)}),
        WAVE_50(50, new Item[]{new Item(995, 1), new Item(995, 1), new Item(995, 1)})
        ;


        @Getter
        private int waveNumber;

        @Getter
        private Item[] rewards;

        waveLoot(int waveNumber, Item[] rewards) {
            this.waveNumber = waveNumber;
            this.rewards = rewards;
        }

        public static waveLoot forID(int waveNumber) {
            for (waveLoot loot : waveLoot.values()) {
                if (loot.getWaveNumber() == waveNumber) {
                    return loot;
                }
            }
            return null;
        }

    }
}
