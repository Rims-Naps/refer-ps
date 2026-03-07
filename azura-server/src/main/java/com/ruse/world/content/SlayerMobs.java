package com.ruse.world.content;

public enum SlayerMobs {
    S1(450),
    s2(4001),
    s3(4000),
    s4(188),
    s5(606),
    s6(3004),
    s7(3688),
    s8(9838),
    s9(6306),
    s10(9846),
    s11(1738),
    s12(1737),
    s13(203),
    s14(603),
    s15(185),
    s16(5002),
    s17(202),
    s18(3005),
    s19(8010),
    s20(928),
    s21(352),
    s22(1739),
    s23(201),
    s24(452),
    s25(5080),
    s26(201),
    s27(452),

    ;

    private int npcId;


    SlayerMobs(int npcId, int requireNpcId, int amountRequired) {
        this.npcId = npcId;
    }

    SlayerMobs(int npcId) {
        this.npcId = npcId;

    }

    public int getNpcId() {
        return npcId;
    }

}
