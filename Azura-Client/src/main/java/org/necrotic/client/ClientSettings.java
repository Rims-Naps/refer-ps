package org.necrotic.client;

public class ClientSettings {

    public static int newFunctionKeys = 0;
    public static int newHealthBars = 1;
    public static int newHitmarks = 1;
    public static int newCursors = 1;
    public static int usernamesAboveHead = 1;
    public static int toggleParticles = 1;
    public static int groundItemNames = 1;
    public static int usernameHighlighting = 1;
    public static int levelUpMessages = 1;
    public static int placeholder = 1;

    public static int viewDistance = 25;
    public static int textureAnimationSpeed = 1;
    public static int fogStartValue = 2500;
    public static int fogColor = 0;

    public static void clamp() {
        if(newFunctionKeys != 0 && newFunctionKeys != 1) {
            newFunctionKeys = 0;
        }

        if(newHealthBars != 0 && newHealthBars != 1) {
            newHealthBars = 0;
        }

        if(newHitmarks != 0 && newHitmarks != 1) {
            newHitmarks = 1;
        }

        if(newCursors != 0 && newCursors != 1) {
            newCursors = 1;
        }

        if(usernamesAboveHead != 0 && usernamesAboveHead != 1) {
            usernamesAboveHead = 1;
        }

        if(toggleParticles != 0 && toggleParticles != 1) {
            toggleParticles = 1;
        }

        if(groundItemNames != 0 && groundItemNames != 1) {
            groundItemNames = 1;
        }

        if(usernameHighlighting != 0 && usernameHighlighting != 1) {
            usernameHighlighting = 1;
        }

        if(levelUpMessages != 0 && levelUpMessages != 1) {
            levelUpMessages = 1;
        }

        if( placeholder != 0 && placeholder != 1) {
            placeholder = 1;
        }

        if (ClientSettings.viewDistance < 25) {
            ClientSettings.viewDistance = 25;
        }
        if (ClientSettings.viewDistance > 50) {
            ClientSettings.viewDistance = 50;
        }

        if(textureAnimationSpeed < 0 || textureAnimationSpeed > 7) {
            textureAnimationSpeed = 1;
        }

        if(fogStartValue < 1000 || fogStartValue > 4000) {
            fogStartValue = 2500;
        }

        Client.instance.updateGameArea();
    }
}
