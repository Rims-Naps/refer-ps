/*

package org.necrotic.client.jfx.gfxtool;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.necrotic.client.Client;
import org.necrotic.client.JagexColor;
import org.necrotic.client.cache.definition.AnimatedGraphic;
import org.necrotic.client.util.ModelUtil;
import org.necrotic.client.media.AnimationSkeleton;
import org.necrotic.client.media.renderable.Model;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;


public class GFXEditorController implements Initializable {

    public ListView<ColorWrapper> colors;


    private ObservableList<ColorWrapper> modelColors = FXCollections.observableArrayList();

    public Button loadModelColors;

    public Button save;
    public Button reset;
    public Button toggle;

    public Slider widthSlider;
    public Slider heightSlider;
    public Slider rotationSlider;

    public TextField gfxIdBox;
    @FXML
    private TextField modelField;
    @FXML
    private TextField animationField;
    @FXML
    private Button addButton;

    @FXML
    private TextField gfxField;

    @FXML
    private Button existingButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SwingUtilities.invokeLater(this::reset);

        loadModelColors.setOnAction(event -> readColors(modelColors));
        reset.setOnAction(event -> SwingUtilities.invokeLater(this::reset));
        save.setOnAction(event -> SwingUtilities.invokeLater(this::save));
        toggle.setOnAction(event -> {
            SwingUtilities.invokeLater(() -> {

                ModelUtil.keepPlaying = !ModelUtil.keepPlaying;
                if (ModelUtil.keepPlaying) {

                    Client.myPlayer.gfxId = ModelUtil.spotAnim.getId();
                }

            });
        });
        rotationSlider.valueProperty()
                .addListener((var1, var2, var3) -> SwingUtilities.invokeLater(() -> {
                    ModelUtil.spotAnim.rotation = var3.intValue();
                    AnimatedGraphic.modelCache.clear();
                }));
        widthSlider.valueProperty()
                .addListener((var1, var2, var3) -> SwingUtilities.invokeLater(() -> {
                    ModelUtil.spotAnim.setMultiplierWidth(var3.doubleValue());

                }));
        heightSlider.valueProperty()
                .addListener((var1, var2, var3) -> SwingUtilities.invokeLater(() -> {
                    ModelUtil.spotAnim.setMultiplierHeight(var3.doubleValue());
                }));
        gfxIdBox.setOnKeyPressed(var1 -> {
            if (var1.getCode().equals(KeyCode.ENTER)) {
                try {
                    int id = Integer.parseInt(gfxIdBox.getText().trim());
                    SwingUtilities.invokeLater(() -> {
                        ModelUtil.setGFX(id);
                        Client.myPlayer.gfxId = id;
                        Client.myPlayer.currentAnim = 0;
                        modelField.setText(String.valueOf(ModelUtil.spotAnim.getModelId()));
                        reset();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        addButton.setOnMouseClicked(x -> {
            int modelId = Integer.parseInt(modelField.getText());
            int animationId = Integer.parseInt(animationField.getText());
            ModelUtil.addGfx(-1);
            ModelUtil.setGFX(ModelUtil.lastGFX);
            ModelUtil.spotAnim.setAnimation(animationId);
            ModelUtil.spotAnim.setModelId(modelId);
            AnimatedGraphic.modelCache.clear();

            Client.myPlayer.gfxId = ModelUtil.lastGFX;
            Client.myPlayer.currentAnim = 0;

            reset();

        });

        existingButton.setOnMouseClicked(x -> {
            ModelUtil.addGfx(Integer.parseInt(gfxField.getText()));


            ModelUtil.setGFX(ModelUtil.lastGFX);
            Client.myPlayer.gfxId = ModelUtil.lastGFX;
            Client.myPlayer.currentAnim = 0;

            reset();
        });
    }


    private void readColors(ObservableList<ColorWrapper> source) {
        colors.getItems().clear();
        colors.getItems().addAll(source);
    }

    private void readColorsFromModel() {
        modelColors.clear();

        Model model = ModelUtil.spotAnim.getModel();
        assert model != null;
        if (model.colors != null) {
            for (Map.Entry<Integer, AtomicInteger> entry : ModelUtil.getModelColors(model)) {
                int color = entry.getKey();
                int[] dest = ModelUtil.spotAnim.destColours;
                if (dest != null) {
                    for (int i = 0; i < dest.length; i++) {
                        int dColor = dest[i];
                        if (dColor == color) {
                            color = ModelUtil.spotAnim.originalColours[i];
                            System.out.println("Found a color");
                        }
                    }
                }

                modelColors.add(new ColorWrapper(ModelUtil.spotAnim, entry.getValue()
                        .get(), color, entry.getKey()));
            }
            System.out.println("Added " + modelColors.size() + " colors");
        } else
            System.out.println("Model colors: " + Arrays.toString(model.colors));

    }

    private void save() {
        try {
            AnimatedGraphic.saveCustomGFXes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reset() {
        colors.getItems().clear();
        if (ModelUtil.spotAnim != null) {

            if (ModelUtil.spotAnim.getModel() == null) {
                Client.myPlayer.gfxId = ModelUtil.spotAnim.getId(); // gfx id
                Client.myPlayer.currentAnim = 0; // current animation
                Client.myPlayer.animCycle = 0; // animation cycle
                int id = AnimatedGraphic.cache[ModelUtil.spotAnim.getId()].animation.frameIDs[0];
                AnimationSkeleton.forId(id);
                while (ModelUtil.spotAnim.getModel() == null) {
                    System.out.println("Waiting on model to load!");
                }

            }
            readColorsFromModel();
            widthSlider.setValue(ModelUtil.spotAnim.multiplierWidth);
            heightSlider.setValue(ModelUtil.spotAnim.multiplierHeight);
            rotationSlider.setValue(ModelUtil.spotAnim.rotation);
        } else
            System.out.println("Please first select a gfx, ::selectgfx <id>");

    }

    private static Color convert(int hslColor) {
        return JagexColor.RS2HSB_to_RGB(hslColor);
    }

    private static int convert(Color color) {

        return JagexColor.RGB_to_RS2HSB((int) (color.getRed() * 255D), (int) (color.getGreen() * 255D),
                (int) (color.getBlue() * 255D));
    }

    private static final class ColorWrapper extends HBox {

        final ColorPicker picker = new ColorPicker();
        final Button reset = new Button("reset");
        final AnimatedGraphic definition;
        int initialColor;
        int targetColor;

        ColorWrapper(AnimatedGraphic definition, int occurences, int initialColor, int targetColor) {
            this.initialColor = initialColor;
            this.targetColor = targetColor;
            this.definition = definition;
            getChildren().addAll(picker, reset, new Label("Occurences: " + occurences));
            picker.setValue(convert(targetColor));
            picker.setOnAction(event -> changeColor());
            reset.setOnAction(event -> resetValueToInitialColor());
        }

        void changeColor() {
            targetColor = convert(picker.getValue());
            System.out.println("Set color " + initialColor + " to " + convert(targetColor));
            SwingUtilities.invokeLater(() -> {
                definition.recolor(initialColor, targetColor);
                AnimatedGraphic.modelCache.clear();
               // Player.mruNodes.clear();
            });

        }

        void resetValueToInitialColor() {
            picker.setValue(convert(initialColor));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            ColorWrapper wrapper = (ColorWrapper) o;

            if (initialColor != wrapper.initialColor)
                return false;
            return targetColor == wrapper.targetColor;
        }

        @Override
        public int hashCode() {
            int result = initialColor;
            result = 31 * result + targetColor;
            return result;
        }
    }


}

*/
