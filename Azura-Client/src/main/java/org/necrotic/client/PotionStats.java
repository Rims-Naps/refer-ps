package org.necrotic.client;

public class PotionStats {

    public static int[] getDataForId(int id) {
/*      KEYS
        int droprate = data[0];
        int crit = data[1];
        int damage = data[2];
        int plus_stats = data[3];*/
        switch (id) {
            case 1321:
                return new int[]{0, 0, 0, 12};
            case 1323:
                return new int[]{0, 0, 0, 18};
            case 17582:
            case 23121:
                return new int[]{0, 0, 7, 0};
            case 17584:
            case 23122:
                return new int[]{0, 2, 0, 0};
            case 23119:
            case 17586:
                return new int[]{3, 0, 0, 0};
            case 358:
                return new int[]{3, 2, 7, 0};
            case 357:
                return new int[]{3, 2, 7, 0};

            case 1465:
                return new int[]{2, 3, 7, 0};

            case 12839:
                return new int[]{1, 0, 0, 0};
            case 17003:
                return new int[]{2, 0, 0, 0};
            case 17004:
                return new int[]{3, 0, 0, 0};
            case 12841:
                return new int[]{0, 1, 0, 0};
            case 17007:
                return new int[]{0, 2, 0, 0};
            case 17008:
                return new int[]{0, 3, 0, 0};
            case 12840:
                return new int[]{0, 0, 2, 0};
            case 17005:
                return new int[]{0, 0, 3, 0};
            case 17006:
                return new int[]{0, 0, 4, 0};

            case 12842:
                return new int[]{3, 3, 4, 0};
            case 17009:
                return new int[]{4, 4, 5, 0};
            case 17010:
                return new int[]{5, 5, 6, 0};

            case 763:
                return new int[]{4, 4, 7, 0};
            case 764:
                return new int[]{5, 5, 8, 0};
            case 765:
                return new int[]{6, 6, 9, 0};
            default:
                return new int[5];
        }
    }

}
