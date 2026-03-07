/*
package org.necrotic.client.jfx.itemdefeditor;

import org.necrotic.client.cache.definition.ItemDef;

public class SavedItemDefinition {
    private final int zoom, xRot, yRot, xOff, yOff, zOff, xTranslate, yTranslate, zTranslate;
    private int[] originalColors, targetColors;

    public SavedItemDefinition(int zoom, int xRot, int yRot, int xOff, int yOff, int zOff, int[] originalColors, int[] targetColors, int xTranslate, int yTranslate, int zTranslate) {
        this.zoom = zoom;
        this.xRot = xRot;
        this.yRot = yRot;
        this.xOff = xOff;
        this.yOff = yOff;
        this.zOff = zOff;
        if(originalColors != null) {
            this.originalColors = originalColors.clone();
            this.targetColors = targetColors.clone();
        }
        this.xTranslate = xTranslate;
        this.yTranslate = yTranslate;
        this.zTranslate = zTranslate;
    }

    public void reset(ItemDef def) {
       // System.out.println("clearing " + Arrays.toString(def.newModelColors) + " - " + Arrays.toString(def.newModelColors));
       // System.out.println("clearing " + Arrays.toString(originalColors) + " - " + Arrays.toString(targetColors));
       // System.out.println("Clearing zoom " + zoom + " | " + def.modelZoom);
        def.modelZoom = zoom;
        def.rotation_x = xRot;
        def.rotation_y = yRot;
        def.rotation_z = xOff;
        def.translate_yz = yOff;
        def.translate_x = zOff;
        def.originalModelColors = originalColors.clone();
        def.newModelColors = targetColors.clone();
        def.xTranslation = xTranslate;
        def.yTranslation = yTranslate;
        def.zTranslation = zTranslate;
    }
}
*/
