package org.necrotic.client.notification;


import org.necrotic.client.Client;
import org.necrotic.client.media.Raster;

public class AlertHandler {

    public boolean hovered = false;
    public boolean close = false;
    public boolean show = false;
    public boolean remove = false;
    public Alert alert = null;
    public Client c;
    int hexColor = 0x3838d1;

    public AlertHandler(Client c) {
        this.c = c;
    }

    public void close() {
        close = true;
    }

    public void processAlerts() {

        if (alert == null) {
            return;
        }
        if (alert.active()) {
            if (close) {
                alert.close();
                close = false;
            }
            if (!alert.isClosed() && alert.getOpacity() < 90 && alert.extraY > 0) {
                alert.opacity += 5;
                if (alert.extraY > 0)
                    alert.extraY -= 5;
                if (alert.extraY < 0)
                    alert.extraY = 0;

                Raster.drawTransparentBox(alert.getX(), alert.getY() + alert.extraY, 484, 65, 0x533968, alert.getOpacity());
                //Client.cacheSprite[1041].drawAdvancedSprite(alert.getX(), alert.getY() +alert.extraY,alert.getOpacity());
                //Client.cacheSprite[1041].drawAdvancedSprite(alert.getX() + 365, alert.getY() + alert.extraY,alert.getOpacity());
            } else if (alert.isClosed()) {
                alert.extraY += 5;
                if (alert.getOpacity() > 0)
                    alert.opacity -= 5;
                else {
                    remove = true;
                    show = false;
                    alert.active = false;
                }
                Raster.drawTransparentBox(alert.getX(), alert.getY() + alert.extraY, 484, 65, 0x533968, alert.getOpacity());
                //Client.cacheSprite[1041].drawAdvancedSprite(alert.getX(), alert.getY() +alert.extraY,alert.getOpacity());
                //Client.cacheSprite[1041].drawAdvancedSprite(alert.getX() + 365, alert.getY() + alert.extraY,alert.getOpacity());
            } else {
                if (alert.getOpacity() < 90)
                    alert.opacity = 90;
                if (show) {
                    //System.out.println("Drawing at " + alert.getX() + " and " + alert.getY() + " for " + alert.getOpacity());
                    Raster.drawTransparentBox(alert.getX(), alert.getY(), 484, 65, 0x533968, hovered ? alert.getOpacity() + 25 : alert.getOpacity());
                    //	Client.cacheSprite[1041].drawAdvancedSprite(alert.getX(), alert.getY());
                    //	Client.cacheSprite[1041].drawAdvancedSprite(alert.getX() + 365, alert.getY());
                }

                c.smallText.drawText(0, Client.capitalizeFirstChar(alert.getTitle())
                        .replace("_", " "), alert.getY() + 16, alert.getX() + 243);
                c.smallText.drawText(alert.getTitleColor(), Client.capitalizeFirstChar(alert.getTitle())
                        .replace("_", " "), alert.getY() + 15, alert.getX() + 242);
                c.smallText.drawText(0, Client.capitalizeFirstChar(alert.getLine(1))
                        .replace("_", " "), alert.getY() + 36, alert.getX() + 243);
                c.smallText.drawText(alert.getColor(2), Client.capitalizeFirstChar(alert.getLine(1))
                        .replace("_", " "), alert.getY() + 35, alert.getX() + 242);
                c.smallText.drawText(0, Client.capitalizeEachFirstLetter(alert.getLine(2))
                        .replace("_", " "), alert.getY() + 56, alert.getX() + 243);
                c.smallText.drawText(alert.getColor(2), Client.capitalizeEachFirstLetter(alert.getLine(2))
                        .replace("_", " "), alert.getY() + 55, alert.getX() + 242);
                show = true;
            }
        }
        if (remove) {
            alert = null;
            remove = false;
        }
    }

    public void processMouse(int x, int y) {
        if (alert == null)
            return;
        if (alert.active()) {
            hovered = (x >= alert.getX() && x <= alert.getX() + 484 && y >= alert.getY() && y <= alert.getY() + 69);
            if (hovered) {
                c.menuActionName[1] = "Close";
                c.menuActionID[1] = 476;
                c.menuActionRow = 2;
            }
        }
    }

}
