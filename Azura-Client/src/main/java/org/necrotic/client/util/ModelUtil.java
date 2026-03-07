
package org.necrotic.client.util;
/*import javafx.application.Application;*/
import javafx.application.Application;
import org.necrotic.client.Client;
import org.necrotic.client.cache.definition.ItemDef;
import org.necrotic.client.cache.definition.AnimatedGraphic;
/*import org.necrotic.client.jfx.gfxtool.GFXToolApplication;
import org.necrotic.client.jfx.itemdefeditor.ItemEditorScene;
import org.necrotic.client.jfx.npcspawneditor.NpcSpawnEditor;*/
import org.necrotic.client.jfx.itemdefeditor.ItemEditorScene;
import org.necrotic.client.media.renderable.Model;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ModelUtil {

    private static Path DUMP_PATH = Paths.get("dumps");
    public static int lastGFX = 0;
    public static AnimatedGraphic spotAnim;
    public static ItemDef itemDef;
    public static boolean keepPlaying = false;

    public static void setGFX(int id) {
        lastGFX = id;
        spotAnim = AnimatedGraphic.cache[id];
    }

    public static boolean parseClientCommand(String command) {
        String[] args = command.split(" ");

        if (command.contains("gfx")) {
            System.out.println(Arrays.toString(args));
            try {

                int gfx = args.length < 2 ? lastGFX : Integer.parseInt(args[1]);
                spotAnim = AnimatedGraphic.cache[gfx];
                lastGFX = gfx;

                switch (args[0]) {
                    case "togglegfx":
                        keepPlaying = !keepPlaying;
                        break;
                  /*  case "gfxtool":
                        System.out.println("Executed gfxtool");
                        new Thread(() -> {
                            Application.launch(GFXToolApplication.class);
                        }).start();

                        break;*/
                    case "selectgfx":
                        break;
                    case "changegfxanim":
                        int anim = Integer.parseInt(args[2]);
                        spotAnim.setAnimation(anim);
                        System.out.println("Set the animation of " + gfx + " to " + anim);
                        break;
                    case "changegfxmodel":
                        int model = Integer.parseInt(args[2]);
                        spotAnim.setModelId(model);
                        AnimatedGraphic.modelCache.clear();
                        System.out.println("Set the model of " + gfx + " to " + model);
                        break;
                    case "changegfxsize":
                        int width = Integer.parseInt(args[2]);
                        int height = Integer.parseInt(args[2]);
                        spotAnim.setResizeX(width);
                        spotAnim.setResizeY(height);
                        System.out.println("Set the width and height of " + gfx + " to (" + width + ", " + height + ")");
                        break;

                    /*
                     * case "getxysize": System.out.println(spotAnim.getResizeX()); break;
                     */

                    case "rotategfx":
                        int angleAmount = Integer.parseInt(args[2]);
                        spotAnim.rotate(angleAmount);
                        break;
                    case "setgfxrotation":
                        int rotation = Integer.parseInt(args[2]);
                        spotAnim.rotation = rotation;
                        break;
                    case "modgfxlight":
                        double brightnessMultiplier = Double.parseDouble(args[2]);
                        spotAnim.brighten(brightnessMultiplier);
                        break;
                    case "setgfxlight":
                        int lightness = Integer.parseInt(args[2]);
                        spotAnim.contrast = lightness;
                        break;
                    case "modgfxshadow":
                        double shadowMultiplier = Double.parseDouble(args[2]);
                        spotAnim.darken(shadowMultiplier);
                        break;
                    case "setgfxshadow":
                        int shadowness = Integer.parseInt(args[2]);
                        spotAnim.ambient = shadowness;
                        break;
                    case "recolorgfx":
                        int targetColor = Integer.parseInt(args[2]);
                        int newColor = Integer.parseInt(args[3]);
                        spotAnim.recolor(targetColor, newColor);
                        System.out.println("Recolored " + gfx + ": " + targetColor + " -> " + newColor + "");
                        break;
                    case "removegfxrecolors":
                        System.out.println("Removing recolors of " + gfx);
                        spotAnim.removeRecolors();
                        AnimatedGraphic.modelCache.clear();
                        break;
                    case "printgfx":
                        System.out.println("Printing colors of " + gfx);
                        System.out.println(spotAnim.toString());
                        break;
                    case "printgfxcolors":
                        getModelColors(spotAnim.getModel()).forEach(entry -> {
                            System.out.println(
                                    " color(" + entry.getKey() + ") occured " + entry.getValue().get() + " times.");
                        });
                        break;
                    case "cleargfxmodelcache":
                        AnimatedGraphic.modelCache.clear();
                        break;
                    case "addgfx":
                        addGfx(args);
                        break;
                    case "savegfxs":
                        AnimatedGraphic.saveCustomGFXes();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

   /*     if (command.startsWith("npctool")) {
            new Thread(() -> Application.launch(NpcSpawnEditor.class)).start();
        }

        if (command.startsWith("ol")) {
            if(Client.myPlayer.playerRights > 10 || Client.myPlayer.playerRights == 4) {
                Client.itemEditing = true;
                new Thread(() -> {
                    Application.launch(ItemEditorScene.class);
                }).start();
            }
        }*/
    /*    if (command.startsWith("ol")) {
            if(Client.myPlayer.playerRights > 10 || Client.myPlayer.playerRights == 4) {
                Client.itemEditing = true;
                new Thread(() -> {
                    Application.launch(ItemEditorScene.class);
                }).start();
            }
        }
*/
        return false;
    }

    public static void addGfx(String[] args) {
        final boolean copyExisting = args.length > 1;
        final AnimatedGraphic newGFX = copyExisting ? AnimatedGraphic.createNewGFXFrom(Integer.parseInt(args[1]))
                : AnimatedGraphic.createNewGFX();
        System.out.println("Created new gfx with id " + (newGFX.getId()));
        newGFX.cache();
        lastGFX = newGFX.getId();
    }

    public static void addGfx(int copyId) {
        final boolean copyExisting = copyId != -1;
        final AnimatedGraphic newGFX = copyExisting ? AnimatedGraphic.createNewGFXFrom(copyId)
                : AnimatedGraphic.createNewGFX();
        System.out.println("Created new gfx with id " + (newGFX.getId()));
        newGFX.cache();
        lastGFX = newGFX.getId();
    }

    public static Set<Map.Entry<Integer, AtomicInteger>> getModelColors(Model model) {
        final TreeMap<Integer, AtomicInteger> weightedColors = new TreeMap<>();

        for (int color : model.colors) {
            weightedColors.putIfAbsent(color, new AtomicInteger(0));
            weightedColors.get(color).incrementAndGet();
        }
        return weightedColors.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getValue().get()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static List<Map.Entry<Integer, Integer>> getColors(Model model) {
        Map<Integer, Integer> weightedColors = new HashMap<>();
        for(int color : model.colors) {
            weightedColors.merge(color, 1, Integer::sum);
        }
        return weightedColors.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());
    }



}
