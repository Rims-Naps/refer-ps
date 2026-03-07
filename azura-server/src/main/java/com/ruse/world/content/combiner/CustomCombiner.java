package com.ruse.world.content.combiner;

import com.ruse.model.Item;
import com.ruse.world.World;
import com.ruse.world.content.achievement.Achievements;
import com.ruse.world.entity.impl.player.Player;

public class CustomCombiner {

    private final Player player;

    public CustomCombiner(Player player) {
        this.player = player;
    }

    enum CustomCombinerData {
        LUCK_AMULET(new Item(9687), new Item(23087, 1), new Item(9688, 1), new Item(9689, 1)),
        GODLYAURA(new Item(11052), new Item(12630, 1), new Item(11054, 1), new Item(11056, 1)),
        ELITE_PROTECTOR(new Item(9724), new Item(15815, 1), new Item(9725, 1), new Item(9726, 1)),
        POWERSHARDS(new Item(780), new Item(6644, 1), new Item(780, 4)),
        MYSTICSHARDS(new Item(779), new Item(6642, 1), new Item(779, 4)),
        VISIONSHARDS(new Item(781), new Item(6643, 1), new Item(781, 4)),
        TRIBRIDCRYSTAL(new Item(6644), new Item(6645, 1), new Item(6643, 1), new Item(6642, 1)),
        FORGOTTEN_PHAT(new Item(1048), new Item(18417, 1), new Item(1038, 1), new Item(1040, 1), new Item(1042, 1), new Item(1044, 1), new Item(1046, 1), new Item(18415, 1), new Item(18416, 1), new Item(18418, 1), new Item(18419, 1), new Item(19133, 1)),
        DEMONIC_RING(new Item(23092), new Item(8335, 1), new Item(995, 150000), new Item(11048, 1)),
        DEMONIC_AMULET(new Item(21068), new Item(16140, 1), new Item(995, 150000), new Item(6466, 1)),
        MINI_ME(new Item(21816), new Item(6798, 1), new Item(23161, 1), new Item(23111, 1), new Item(21815, 1), new Item(23082, 1), new Item(23117, 1)),
        KIL_PET(new Item(23041), new Item(23114, 1), new Item(23043, 1), new Item(23042, 1)),
        VanguardCAPE(new Item(22109), new Item(21613, 1), new Item(11304, 1), new Item(17604, 1)),
        MARKSMAN_WINGS(new Item(23149), new Item(4413, 1), new Item(21613, 1)),
        WIZARD_WINGS(new Item(23148), new Item(17716, 1), new Item(21613, 1)),
        BERZERKER_WINGS(new Item(23147), new Item(21603, 1), new Item(21613, 1)),
        VanguardWINGS(new Item(21603), new Item(22111, 1), new Item(17716, 1), new Item(4413, 1)),
        MASTERSOUL(new Item(18653), new Item(6651, 1), new Item(5020, 5000000), new Item(18656, 1), new Item(18654, 1)),
        NEW1(new Item(15924), new Item(19090, 1), new Item(18749, 1), new Item(13328, 1), new Item(5023, 10000), new Item(18660, 15000), new Item(19080, 75000)),
        NEW2(new Item(16023), new Item(19091, 1), new Item(18748, 1), new Item(13329, 1), new Item(5023, 10000), new Item(18660, 15000), new Item(19080, 75000)),
        NEW3(new Item(15935), new Item(19092, 1), new Item(18638, 1), new Item(13330, 1), new Item(5023, 10000), new Item(18660, 15000), new Item(19080, 75000)),
        NEW4(new Item(18885), new Item(19093, 1), new Item(12994, 1), new Item(11766, 1), new Item(5023, 10000), new Item(18660, 15000), new Item(19080, 75000)),
        NEW5(new Item(16272), new Item(19094, 1), new Item(18009, 1), new Item(13332, 1), new Item(5023, 10000), new Item(18660, 15000), new Item(19080, 75000)),
        NEW6(new Item(21023), new Item(19095, 1), new Item(8087, 1), new Item(16137, 1), new Item(5023, 10000), new Item(18660, 20000), new Item(19080, 75000)),
        NEW7(new Item(16875), new Item(19096, 1), new Item(16871, 1), new Item(16873, 1), new Item(5023, 10000), new Item(18660, 20000), new Item(19080, 75000)),
        NEW8(new Item(14005), new Item(19097, 1), new Item(14006, 1), new Item(12902, 1), new Item(5023, 10000), new Item(18660, 20000), new Item(19080, 75000)),
        NEW9(new Item(457), new Item(23156, 1), new Item(458, 1), new Item(459, 1), new Item(5023, 10000), new Item(18660, 10000), new Item(19080, 75000)),
        NEW10(new Item(23156), new Item(23164, 1), new Item(23156, 1), new Item(8266, 1), new Item(8265, 1), new Item(20177, 1), new Item(19080, 150000)),
        ETHPET(new Item(23164), new Item(6200, 1), new Item(10180, 1), new Item(10182, 1), new Item(10184, 1), new Item(10224, 50000), new Item(995, 5000000)),
        DEFENDER(new Item(15815), new Item(19113, 1),new Item(19104, 1), new Item(19105, 1), new Item(19106, 1), new Item(19107, 1), new Item(19108, 1), new Item(19109, 1), new Item(19110, 1), new Item(19111, 1), new Item(19112, 1)),
        DMGSCROLL(new Item(19025), new Item(15357, 1), new Item(19025, 9)),
        GODLYBOW(new Item(20171), new Item(16881, 1), new Item(16869, 1), new Item(16879, 1), new Item(11710, 5), new Item(11712, 5), new Item(995, 500000)),
        GODLYSTAFF(new Item(17670), new Item(22114, 1), new Item(14007, 1), new Item(6818, 1), new Item(11710, 5), new Item(11712, 5), new Item(995, 500000)),
        GODLYBLADE(new Item(16024), new Item(11308, 1), new Item(17694, 1), new Item(17722, 1), new Item(11710, 5), new Item(11712, 5), new Item(995, 500000)),
        LIGHTPATH(new Item(19033), new Item(19049, 1), new Item(19034, 1), new Item(19035, 1), new Item(19036, 1), new Item(19037, 1), new Item(19048, 100), new Item(995, 10000000)),
        DARKPATH(new Item(19038), new Item(19050, 1), new Item(19039, 1), new Item(19041, 1), new Item(19042, 1), new Item(19043, 1), new Item(19048, 100), new Item(995, 10000000)),
        GALACTICCRYSTAL(new Item(19061), new Item(19063, 1), new Item(19057, 1), new Item(19062, 50000)),
        GOLDENFCAPE(new Item(21613), new Item(4357, 1), new Item(995, 2000000), new Item(19048, 100)),
        GALACAPE(new Item(4357), new Item(2783, 1), new Item(995, 2000000), new Item(19048, 200)),
        UNICAPE(new Item(2783), new Item(2785, 1), new Item(995, 2000000), new Item(19048, 300)),
        GOLDENEXGODLYAURA(new Item(12610), new Item(4387, 1), new Item(995, 500000), new Item(19048, 75)),
        GALAAURA(new Item(4387), new Item(2780, 1), new Item(995, 2000000), new Item(19048, 200)),
        UNIAURA(new Item(2780), new Item(2782, 1), new Item(995, 2000000), new Item(19048, 300)),
        GODLENFWINGS(new Item(22111), new Item(8830, 1), new Item(995, 5000000), new Item(19048, 200)),
        GALACTICWINGS(new Item(19053), new Item(7262, 1), new Item(13080, 1), new Item(2847, 1), new Item(2848, 1), new Item(2849, 1), new Item(995, 45000000)),
        FLAREON(new Item(7296), new Item(7286, 1), new Item(995, 2500000), new Item(7284, 1)),
        JOLTEON(new Item(7300), new Item(7288, 1), new Item(995, 2500000), new Item(7284, 1)),
        VAPOREON(new Item(7301), new Item(7290, 1), new Item(995, 2500000), new Item(7284, 1)),
        SYLVEON(new Item(7298), new Item(7292, 1), new Item(995, 2500000), new Item(7284, 1)),
        LEAFEON(new Item(7303), new Item(7294, 1), new Item(995, 2500000), new Item(7284, 1)),
        CHARIZARD(new Item(7286), new Item(3530, 1),new Item(7288, 1),new Item(7290, 1),new Item(7292, 1),new Item(7294, 1), new Item(995, 5000000)),
        MARSKMANNECK(new Item(7305), new Item(7236, 1), new Item(16140, 1), new Item(19887, 1), new Item(995, 5000000), new Item(19048, 25)),
        BERZERKERNECK(new Item(7306), new Item(7238, 1), new Item(16140, 1), new Item(19887, 1), new Item(995, 5000000), new Item(19048, 25)),
        WIZARDNECK(new Item(7307), new Item(7241, 1), new Item(16140, 1), new Item(19887, 1), new Item(995, 5000000), new Item(19048, 25)),
        MARKSMANRING(new Item(7308), new Item(7245, 1), new Item(8335, 1), new Item(20092, 1), new Item(995, 5000000), new Item(19048, 25)),
        BERZERKERRING(new Item(7309), new Item(7247, 1), new Item(8335, 1), new Item(20092, 1), new Item(995, 5000000), new Item(19048, 25)),
        WIZARDRING(new Item(7311), new Item(7249, 1), new Item(8335, 1), new Item(20092, 1), new Item(995, 5000000), new Item(19048, 25)),
        VanguardNECK(new Item(7236), new Item(7243, 1), new Item(7238, 1), new Item(7241, 1), new Item(995, 10000000), new Item(19048, 50)),
        VanguardRNG(new Item(7245), new Item(7250, 1), new Item(7247, 1), new Item(7249, 1), new Item(995, 10000000), new Item(19048, 50)),

        GALACTICTOTEM(new Item(23123), new Item(17489, 1), new Item(23010, 1), new Item(23012, 1), new Item(19019, 1), new Item(6645, 1), new Item(995, 2000000)),

        FIRESOUL(new Item(2692), new Item(2722, 1), new Item(2693, 1), new Item(2694, 1), new Item(2695, 1), new Item(2776, 1), new Item(2720, 1), new Item(995, 2000000)),
        WATERSOUL(new Item(2696), new Item(2724, 1), new Item(2697, 1), new Item(2698, 1), new Item(2699, 1), new Item(2778, 1), new Item(2720, 1), new Item(995, 2000000)),
        EARTHSOUL(new Item(2700), new Item(2726, 1), new Item(2747, 1), new Item(2773, 1), new Item(2774, 1), new Item(13070, 1), new Item(2720, 1), new Item(995, 2000000)),

        AFKFRAG(new Item(7253), new Item(2736, 1), new Item(5020, 10000000), new Item(2720, 1), new Item(995, 2000000)),

        RAIDSOUL(new Item(7251), new Item(2728, 1), new Item(7252, 1), new Item(2720, 1), new Item(995, 1000000)),

       SUMMERBOSS2x(new Item(23155), new Item(23163, 1), new Item(6545, 1) , new Item(995, 2500000)),
        COSMICBLADE(new Item(11308), new Item(3564, 1),new Item(13042, 1), new Item(13022, 1), new Item(18972, 1), new Item(13035, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),
        COSMICBOW(new Item(16881), new Item(3566, 1),new Item(13063, 1), new Item(18974, 1), new Item(13056, 1), new Item(13029, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),
        COSMICSTAFF(new Item(22114), new Item(3568, 1), new Item(13015, 1),new Item(18593, 1), new Item(18985, 1), new Item(13049, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),

        QUANTUMWEAPON(new Item(3564), new Item(3570, 1), new Item(3566, 1), new Item(3568, 1),new Item(19062, 35000),new Item(3576, 25000), new Item(995, 15000000)),

        MANIAHELM1(new Item(8330), new Item(12443, 1),new Item(13010, 1), new Item(18588, 1), new Item(13044, 1), new Item(13030, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),
        MANIABODY1(new Item(8331), new Item(12444, 1),new Item(13011, 1), new Item(18589, 1), new Item(13045, 1), new Item(13031, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),
        MANIALEGS1(new Item(8332), new Item(12445, 1),new Item(13012, 1), new Item(18590, 1), new Item(13046, 1), new Item(13032, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),
        MANIAGLOVES1(new Item(12864), new Item(12446, 1),new Item(13013, 1), new Item(18591, 1), new Item(13047, 1), new Item(13033, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),
        MANIABOOTS1(new Item(10865), new Item(12447, 1),new Item(13014, 1), new Item(18592, 1), new Item(13048, 1), new Item(13034, 1),new Item(19062, 25000),new Item(3576, 10000), new Item(995, 5000000)),


        BRUTALTOME(new Item(12431), new Item(7272, 1), new Item(12431, 11), new Item(15815, 1), new Item(19924, 1), new Item(995, 10000000)),
        JADEDTOME(new Item(12432), new Item(7270, 1), new Item(12432, 11), new Item(15815, 1), new Item(11303, 1), new Item(995, 10000000)),
        MYSTICTOME(new Item(12433), new Item(7266, 1), new Item(12433, 11), new Item(15815, 1), new Item(19987, 1), new Item(995, 10000000)),

        SPECTRALTOME(new Item(7272), new Item(12442, 1), new Item(12431, 5), new Item(12432, 5), new Item(12433, 5) , new Item(7270, 1) , new Item(7266, 1), new Item(19113, 1), new Item(995, 15000000)),



        //  EASTERSPECIAL(new Item(15328), new Item(2831, 1), new Item(995, 1000000), new Item(11211, 1)),
        // BUNNY2XPET(new Item(23018), new Item(10216, 1), new Item(6545, 1) , new Item(11211, 1) , new Item(13558, 50), new Item(995, 1500000)),



        CHRISMASKEY2(new Item(-1), new Item(-1, -1), new Item(-1, -1));





       // SANTAHAT(new Item(18411), new Item(18410, 1), new Item(19134, 1), new Item(18412, 1), new Item(18414, 1), new Item(18413, 1));

        //HAUNTEDBOX(new Item(19028), new Item(19026, 1), new Item(19028, 500)),
        //TERRORBOX(new Item(19026), new Item(19046, 1), new Item(19026, 5), new Item(19047, 400)),
        // TERRORPET(new Item(19031), new Item(15279, 1), new Item(19029, 1), new Item(19030, 1), new Item(19047, 5000)),
        // TERRORPETX2(new Item(15279), new Item(15278, 1), new Item(6545, 1) , new Item(19031, 1) , new Item(19029, 1) , new Item(19030, 1), new Item(19047, 5000), new Item(995, 2500000)),

        CustomCombinerData(Item upgradedItem, Item reward, Item... requirements) { // Upgrade item, chance, requirements
            this.upgradedItem = upgradedItem;
            this.reward = reward;
            this.requirements = requirements;
        }

        private final Item upgradedItem, reward;
        private final Item[] requirements;

    }

    private Item selectedItem = null;

    private final CustomCombinerData[] VALUES = CustomCombinerData.values();

    public void open() {
        player.getPacketSender().sendInterface(30330);
        updateInterface();
    }

    private void updateInterface() {
        int index = 0;
        for (CustomCombinerData data : VALUES) {
            player.getPacketSender().sendItemOnInterface(30351, data.upgradedItem.getId(), index, data.upgradedItem.getAmount());
            index++;
        }
    }

    public void handleSelection(Item item) {
        selectedItem = item;

        for (CustomCombinerData data : VALUES) {
            if (data.upgradedItem.getId() == selectedItem.getId()) {
                player.getPacketSender().resetItemsOnInterface(30340, 12);
                player.getPacketSender().sendItemArrayOnInterface(30340, data.requirements);
                player.getPacketSender().sendItemOnInterface(30336, data.reward.getId(), 0, data.reward.getAmount());
                break;
            }
        }

    }

    public void combine() {

        if (selectedItem == null) {
            player.sendMessage("@red@You haven't selected an item yet.");
            return;
        }

        for (CustomCombinerData data : VALUES) {

            if (data.upgradedItem.getId() == selectedItem.getId()) {
                if (player.getInventory().contains(data.upgradedItem.getId()) && player.getInventory().containsAll(data.requirements)) {
                    player.getInventory().delete(data.upgradedItem);
                    player.getInventory().deleteItemSet(data.requirements);
                    player.getInventory().add(data.reward);
                    World.sendMessage("<img=4><col=0><shad=6C1894>@blu@FORGING<img=4>@red@ " + player
                            .getUsername() + "@blu@ combined " + selectedItem.getDefinition()
                            .getName() + " to " + data.reward.getDefinition().getName());
                } else {
                        player.sendMessage("@red@You don't have the required items for this combination");
                    }
                    break;
                }
            }

        }


    }

