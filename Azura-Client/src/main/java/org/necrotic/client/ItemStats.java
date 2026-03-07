package org.necrotic.client;

import org.necrotic.client.cache.definition.ItemDef;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ItemStats {

	public static final int STAB = 0;
	public static final int SLASH = 1;
	public static final int CRUSH = 2;
	public static final int MAGIC = 3;
	public static final int RANGED = 4;

	public static ItemStats[] itemstats = new ItemStats[ItemDef.totalItems > 0 ? ItemDef.totalItems : 25000];

	public int itemId;
	public int[] attackBonus;
	public int[] defenceBonus;
	public int prayerBonus;
	public int rangeBonus;
	public int strengthBonus;
	public int magicBonus;
    public int speed;
	public int healAmount;
	public int type;
	public int[][] rewards;
	public String information;

	public ItemStats(int id, int typeOfStat) {
		this.itemId = id;
		this.attackBonus = new int[]{0, 0, 0, 0, 0};
		this.defenceBonus = new int[]{0, 0, 0, 0, 0};
		this.prayerBonus = 0;
		this.strengthBonus = 0;
		this.rangeBonus = 0;
		this.magicBonus = 0;
		this.healAmount = 0;
        this.speed = getSpeedBonus(id);
		this.type = typeOfStat;
	}

	private static int readType = 0;

	public static void readDefinitions() {
		try {
			File file = new File(Signlink.getCacheDirectory() + "itemstats.dat");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.equals("[STATS]")) {
					readType = 1;
					continue;
				}
				if (readType == 1) {
					String[] data = line.split(" ");
					int slot = 0;
					int id = Integer.parseInt(data[slot++]);
					itemstats[id] = new ItemStats(id, readType);
					for (int i = 0; i < 5; ++i) {
						itemstats[id].attackBonus[i] = Integer.parseInt(data[slot++]);
					}
					for (int i = 0; i < 5; ++i) {
						itemstats[id].defenceBonus[i] = Integer.parseInt(data[slot++]);//the problem is that it doesnt print out 12 things
						//like 0 0 0 0 0 0 0 0 0 it has do to that for every single item
					}
					itemstats[id].strengthBonus = Integer.parseInt(data[slot++]);
                    itemstats[id].magicBonus = Integer.parseInt(data[slot++]);
					itemstats[id].prayerBonus = Integer.parseInt(data[slot++]);
                    itemstats[id].rangeBonus = Integer.parseInt(data[slot++]);
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public static int getSpeedBonus(int itemId) {
        switch (itemId)  {
            case 2651:
            case 2653:
            case 2655:
                return 2;
            //CINDER
            case 16024:
            case 13042:
            case 14915:
                //STONEWARD
            case 12930:
            case 16879:
            case 23144:
                //TIDEWAVE
            case 23145:
            case 23146:
            case 23033:
                //VERDANT
            case 18332:
            case 3745:
            case 3743:
                return 4;
            //FLAMESTRIKE
            case 23039:
            case 23064:
            case 23065:
                //WAVEBREAKER
            case 23066:
            case 23067:
            case 14065:
                //MAGMITE
            case 14004:
            case 18799:
            case 5010:
                //MOSSHEART
            case 23055:
            case 23056:
            case 13943:
                //SURGE
            case 13049:
            case 13056:
            case 13063:
                //MOSSBORN
            case 13015:
            case 13022:
            case 13029:
                return 3;
            //BLAZEN
            case 18974:
            case 18593:
            case 18972:
                return 2;
            //PULSAR
            case 13035:
            case 13964:
            case 21070:
                //MOLTAROK
            case 18629:
            case 17714:
            case 15485:
                //CLIFFBREAKER
            case 16867:
            case 16337:
            case 17620:
                //TIDAL
            case 20171:
            case 21023:
            case 16875:
                //MERCURIAL
            case 14005:
            case 18749:
            case 18748:
                //VIRE
            case 18638:
            case 12994:
            case 18009:
                //SEAFIRE
            case 8087:
            case 16871:
            case 14006:
                //TORRID
            case 13328:
            case 13329:
            case 13330:

                //WARDEN
            case 16137:
            case 16873:
            case 12902:
                return 2;

            //VOID
            case 20000:
            case 20001:
            case 20002:
                return 2;

            //ENCHANTED
            case 16415:
            case 16416:
                return 3;

            case 16417:
            case 16418:
            case 16419:
            case 16420:
            case 16421:
            case 17101:
            case 17102:
            case 17103:
            case 17104:
            case 17105:
            case 17106:
            case 17107:
            case 17108:
            case 17109:
            case 17110:
            case 17111:
            case 17112:
            case 17113:
            case 17114:

            case 1475:
            case 1481:
            case 1487:
            case 1493:
            case 1499:
            case 1505:
            case 1511:
            case 1517:
            case 1524:
                //XMAS
            case 1461:
            case 1462:
            case 1463:

            case 1560:
            case 1561:
            case 1562:
            case 1563:
            case 1564:
            case 1565:
            case 1566:
            case 1567:
            case 1568:
            case 1570:
            case 1571:
            case 1572:
            case 1573:
            case 1574:
            case 1575:
            case 1576:
            case 1577:
            case 1578:
            case 1580:
            case 1581:
            case 1582:
            case 1583:
            case 1584:
            case 1585:
            case 1586:
            case 1587:
            case 1588:
                return 2;
            case 711:
            case 712:
            case 713:
            case 714:
                return 3;
            case 2086:
            case 2087:
            case 2088:
            case 2079:
            case 2080:
            case 2081:
                return 2;
            default:
                return 6;
        }
    }
}
