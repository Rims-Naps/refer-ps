package org.necrotic.client.cache.definition.items;

import org.necrotic.client.cache.definition.ItemDef;

public class CustomItems {

    public static void addCustomItems(ItemDef itemDef, int customId) {
        switch (customId) {
            case 6729:
                itemDef.name = "Corrupt Bones";
                break;
            case 530:
                itemDef.name = "Corrupt Bones(T2)";
                break;
            case 534:
                itemDef.name = "@bla@Ancient Bones";
                itemDef.actions = new String[]{null, null, null, "Crush", "Drop"};
                break;
            case 20431:
                itemDef.name = "Starter Tasks";
                itemDef.modelID = 101128;
                itemDef.modelZoom = 1036;
                itemDef.rotation_x = 145;
                itemDef.rotation_y = 345;
                itemDef.rotation_z = 136;
                itemDef.translate_x = 8;
                itemDef.translate_yz = 22;
                itemDef.actions = new String[]{"Read", null, null, null, "Drop"};
                break;
            case 6833:
                itemDef.name = "Box Of Boxes";
                itemDef.actions = new String[]{"Open", null, null, null, "Drop"};
                itemDef.modelID = 101021;
                itemDef.modelZoom = 375;
                itemDef.rotation_x = 43;
                itemDef.rotation_y = 26;
                break;







            case 16878:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Bovistrangler shortbow";
                itemDef.description = "Its a Bovistrangler shortbow.".getBytes();
                break;



            case 16880:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Thigat shortbow";
                itemDef.description = "Its a Thigat shortbow.".getBytes();
                break;


            case 16882:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Corpsethorn shortbow";
                itemDef.description = "Its a Corpsethorn shortbow.".getBytes();
                break;



            case 16884:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Entgallow shortbow";
                itemDef.description = "Its a Entgallow shortbow.".getBytes();
                break;



            case 16886:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Grave creeper shortbow";
                itemDef.description = "Its a Grave creeper shortbow.".getBytes();
                break;

            case 16887:
                itemDef.name = "Exp Bow";
                itemDef.modelID = 100715;
                itemDef.maleEquip1 = 100715;
                itemDef.femaleEquip1 = 100715;
                itemDef.modelZoom = 1661;
                itemDef.rotation_x = 791;
                itemDef.rotation_y = 235;
                itemDef.translate_x = -26;
                itemDef.translate_yz = 339;
                itemDef.actions = new String[]{null, "Equip", null, null, "Drop"};
                break;




            case 16889:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 56131;
                itemDef.modelID = 54412;
                itemDef.maleEquip1 = 56131;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1616;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Novite 2h sword";
                itemDef.description = "Its a Novite 2h sword.".getBytes();
                break;

            case 16890:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Novite 2h sword";
                itemDef.description = "Its a Novite 2h sword.".getBytes();
                break;

            case 16891:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 56297;
                itemDef.modelID = 54404;
                itemDef.maleEquip1 = 56297;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1616;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Bathus 2h sword";
                itemDef.description = "Its a Bathus 2h sword.".getBytes();
                break;

            case 16892:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Bathus 2h sword";
                itemDef.description = "Its a Bathus 2h sword.".getBytes();
                break;

            case 16893:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 55927;
                itemDef.modelID = 54322;
                itemDef.maleEquip1 = 55927;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1616;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Marmaros 2h sword";
                itemDef.description = "Its a Marmaros 2h sword.".getBytes();
                break;

            case 16894:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Marmaros 2h sword";
                itemDef.description = "Its a Marmaros 2h sword.".getBytes();
                break;

            case 16895:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 56306;
                itemDef.modelID = 54464;
                itemDef.maleEquip1 = 56306;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1616;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Kratonite 2h sword";
                itemDef.description = "Its a Kratonite 2h sword.".getBytes();
                break;

            case 16896:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Kratonite 2h sword";
                itemDef.description = "Its a Kratonite 2h sword.".getBytes();
                break;

            case 16897:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 56146;
                itemDef.modelID = 54235;
                itemDef.maleEquip1 = 56146;
                itemDef.translate_x = -1;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1603;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Fractite 2h sword";
                itemDef.description = "Its a Fractite 2h sword.".getBytes();
                break;

            case 16898:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Fractite 2h sword";
                itemDef.description = "Its a Fractite 2h sword.".getBytes();
                break;

            case 16899:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 55906;
                itemDef.modelID = 54401;
                itemDef.maleEquip1 = 55906;
                itemDef.translate_x = -1;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1603;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Zephyrium 2h sword";
                itemDef.description = "Its a Zephyrium 2h sword.".getBytes();
                break;

            case 16900:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Zephyrium 2h sword";
                itemDef.description = "Its a Zephyrium 2h sword.".getBytes();
                break;

            case 16901:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 56164;
                itemDef.modelID = 54199;
                itemDef.maleEquip1 = 56164;
                itemDef.translate_x = -1;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1603;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Argonite 2h sword";
                itemDef.description = "Its a Argonite 2h sword.".getBytes();
                break;


            case 16907:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 56142;
                itemDef.modelID = 54230;
                itemDef.maleEquip1 = 56142;
                itemDef.translate_x = -1;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -5;
                itemDef.rotation_y = 1603;
                itemDef.rotation_x = 1589;
                itemDef.modelZoom = 1645;
                itemDef.name = "Promethium 2h sword";
                itemDef.description = "Its a Promethium 2h sword.".getBytes();
                break;

            case 16908:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Promethium 2h sword";
                itemDef.description = "Its a Promethium 2h sword.".getBytes();
                break;

            case 16909:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[1] = "Wield";
                itemDef.actions[2] = "Bind";
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = 56046;
                itemDef.modelID = 54373;
                itemDef.maleEquip1 = 56046;
                itemDef.translate_x = -1;
                itemDef.rotation_z = 956;
                itemDef.translate_yz = -3;
                itemDef.rotation_y = 1589;
                itemDef.rotation_x = 1576;
                itemDef.modelZoom = 1776;
                itemDef.name = "Primal 2h sword";
                itemDef.description = "Its a Primal 2h sword.".getBytes();
                break;

            case 16910:
                itemDef.groundActions = new String[5];
                itemDef.groundActions[2] = "Take";
                itemDef.actions = new String[5];
                itemDef.actions[4] = "Drop";
                itemDef.femaleEquip1 = -1;
                itemDef.modelID = 2429;
                itemDef.maleEquip1 = -1;
                itemDef.translate_x = 0;
                itemDef.rotation_z = 0;
                itemDef.translate_yz = 2;
                itemDef.rotation_y = 552;
                itemDef.rotation_x = 28;
                itemDef.modelZoom = 760;
                itemDef.name = "Primal 2h sword";
                itemDef.description = "Its a Primal 2h sword.".getBytes();
                break;
        }
    }
}
