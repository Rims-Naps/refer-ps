package org.necrotic.client;

import org.necrotic.client.cache.media.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BossOverlay {

    public static final List<BossOverlay> BOSS_OVERLAYS = new ArrayList<>();
    private final int spriteId;
    private int xPos;
    private int yPos;

    public BossOverlay(int spriteId) {
        this.spriteId = spriteId;
    }

    public static void position(BossOverlay overlay) {
        System.out.println(BOSS_OVERLAYS.size());
        if (BOSS_OVERLAYS.size() > 1) {
            BossOverlay previous = BOSS_OVERLAYS.get(BOSS_OVERLAYS.size() - 2);
            int previousHeight = Client.spritesMap.get(previous.spriteId).myHeight;
            int previousYPos = previous.yPos;
            overlay.yPos = previousHeight + previousYPos + 10;
        } else {
            overlay.yPos = 100;  // adjust this initial Y position if needed
        }
        overlay.xPos = 5;
    }


    public boolean isMouseOver(int mouseX, int mouseY) {
        int width = Client.spritesMap.get(this.spriteId).myWidth;
        int height = Client.spritesMap.get(this.spriteId).myHeight;
        return (mouseX >= this.xPos && mouseX <= this.xPos + width) && mouseY >= this.yPos && mouseY <= this.yPos + height;
    }

    public static void remove(BossOverlay overlay) {
        BOSS_OVERLAYS.remove(overlay);
    }

    public static void displayOverlays(int mouseX, int mouseY) {
        if(BOSS_OVERLAYS.isEmpty()) return;
        if(Client.openInterfaceID != -1) return;
        for(BossOverlay overlay : BOSS_OVERLAYS) {
            Sprite sprite;
            if((sprite = Client.spritesMap.get(overlay.spriteId)) != null) {
                sprite.drawAdvancedSprite(overlay.xPos, overlay.yPos);
            }
        }
    }

    public static boolean clickOverlay(int mouseX, int mouseY) {
        if(BOSS_OVERLAYS.isEmpty()) return false;
        if(Client.openInterfaceID != -1) return false;
        for(BossOverlay overlay : BOSS_OVERLAYS) {
            int width = Client.spritesMap.get(overlay.spriteId).myWidth;
            int height = Client.spritesMap.get(overlay.spriteId).myHeight;
            if((mouseX >= overlay.xPos && mouseX <= overlay.xPos + width) && mouseY >= overlay.yPos && mouseY <= overlay.yPos + height) {
                Client.getOut().putOpcode(20);
                Client.getOut().putShort(overlay.spriteId);
                return true;
            }
        }
        return false;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getSpriteId() {
        return spriteId;
    }
}
