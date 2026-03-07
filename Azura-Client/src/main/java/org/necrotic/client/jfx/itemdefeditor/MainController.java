/*

        package org.necrotic.client.jfx.itemdefeditor;

        import javafx.beans.property.ObjectProperty;
        import javafx.beans.property.SimpleObjectProperty;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.*;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.image.ImageView;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import org.necrotic.client.JFXColorUtils;
        import org.necrotic.client.util.ModelUtil;
        import org.necrotic.client.cache.media.RSInterface;
        import org.necrotic.client.cache.definition.ItemDef;
        import org.necrotic.client.media.renderable.actor.Player;
        import org.necrotic.client.media.renderable.Model;
        import org.necrotic.client.media.Rasterizer3D;

        import java.awt.*;
        import java.awt.datatransfer.Clipboard;
        import java.awt.datatransfer.StringSelection;
        import java.awt.datatransfer.Transferable;
        import java.net.URL;
        import java.util.*;
        import java.util.List;
        import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    private Button resetButton, generateJsonButton, saveButton;

    @FXML
    private ListView<ColorWrapper> colorListView;

    @FXML
    private TextField idTextField, textureField1, textureField2, textureField3, newIdField;

    @FXML
    private Label zoomLabel, xRotationLabel, yRotationLabel, xOffsetLabel, yOffsetLabel, zOffsetLabel, xTranslationLabel, yTranslationLabel, zTranslationLabel;

    @FXML
    private ImageView itemImage;
    @FXML
    private Slider zoomSlider, xRotationSlider, yRotationSlider, xOffsetSlider, yOffsetSlider, zOffsetSlider, xTranslationSlider, yTranslationSlider, zTranslationSlider;

    @FXML
    private VBox slidersVBox, texturesVBox;

    @FXML
    private TabPane parent;

    @FXML
    private CheckBox useNewIdCheckBox;

    private List<Integer> colorList = new ArrayList<>();

    private int editedID = 1050; // just a default id

    private int originalZoom = 0;
    private int originalXRotation = 0;
    private int originalYRotation = 0;
    private int originalXOffset = 0;
    private int originalYOffset = 0;
    private int originalZOffset = 0;
    private int[] originalModelColors;
    private int originalXTranslation;
    private int originalYTranslation;
    private int originalZTranslation;

    private static ItemDef def;

    private final Deque<SavedItemDefinition> definitionEdits = new ArrayDeque<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

    private void init() {
        setFactories();
        handleButtons();
        handleSliders();
        parent.setOnKeyPressed(keyboard -> {
            if (keyboard.isControlDown()) {
                switch (keyboard.getCode()) {
                    case Z:
                        undo();
                        break;

                    case S:
                        appendClientCodeChanges();
                        appendServerCodeChanges();
                        System.out.println("Appended changes");
                        break;

                    case R:
                        clientCodeBuilder.setLength(0);
                        serverCodeBuilder.setLength(0);
                        System.out.println("Reset builders");
                        break;

                    case N:
                        def = null;
                        System.out.println("DEF = NULL");
                        break;
                }
            }
        });
    }

    private void appendClientCodeChanges() {
        if (useNewIdCheckBox.isSelected()) {
            int newId = Integer.parseInt(newIdField.getText());
            clientCodeBuilder.append("case ")
                    .append(newId)
                    .append(":")
                    .append("\n")
                    .append("ItemDef.copyModel(itemDef, ")
                    .append(def.id)
                    .append(");")
                    .append("\n")
                    .append("itemDef.originalModelColors = new int[] ")
                    .append(Arrays.toString(def.originalModelColors)
                            .replace("[", "{").replace("]", "}"))
                    .append(";")
                    .append("\n")
                    .append("itemDef.newModelColors = new int[] ")
                    .append(Arrays.toString(def.newModelColors)
                            .replace("[", "{").replace("]", "}"))
                    .append(";")
                    .append("\n")
                    .append("itemDef.xTranslation = ")
                    .append(def.xTranslation)
                    .append(";")
                    .append("\n")
                    .append("itemDef.yTranslation = ")
                    .append(def.yTranslation)
                    .append(";")
                    .append("\n")
                    .append("itemDef.zTranslation = ")
                    .append(def.zTranslation)
                    .append(";")
                    .append("\n")
                    .append("break;").append("\n");
        } else {
            clientCodeBuilder.append("case ")
                    .append(editedID)
                    .append(":")
                    .append("\n")
                    .append("itemDef.modelZoom = ")
                    .append(def.modelZoom)
                    .append(";")
                    .append("\n")
                    .append("itemDef.rotation_x = ")
                    .append(def.rotation_x)
                    .append(";")
                    .append("\n")
                    .append("itemDef.rotation_y = ")
                    .append(def.rotation_y)
                    .append(";")
                    .append("\n")
                    .append("itemDef.rotation_z = ")
                    .append(def.rotation_z)
                    .append(";")
                    .append("\n")
                    .append("itemDef.translate_yz = ")
                    .append(def.translate_yz)
                    .append(";")
                    .append("\n")
                    .append("itemDef.translate_x = ")
                    .append(def.translate_x)
                    .append(";")
                    .append("\n")
                    .append("itemDef.originalModelColors = new int[] ")
                    .append(Arrays.toString(def.originalModelColors)
                            .replace("[", "{").replace("]", "}"))
                    .append(";")
                    .append("\n")
                    .append("itemDef.newModelColors = new int[] ")
                    .append(Arrays.toString(def.newModelColors)
                            .replace("[", "{").replace("]", "}"))
                    .append(";").append("\n")
                    .append("itemDef.xTranslation = ")
                    .append(def.xTranslation)
                    .append(";")
                    .append("\n")
                    .append("itemDef.yTranslation = ")
                    .append(def.yTranslation)
                    .append(";")
                    .append("\n")
                    .append("itemDef.zTranslation = ")
                    .append(def.zTranslation)
                    .append(";")
                    .append("\n")
                    .append("break;").append("\n");
        }
    }

    private void appendServerCodeChanges() {
 int originalId = def.id;
        int newId = originalId;
        if(!newIdField.getText().isEmpty()) {
            newId = Integer.parseInt(newIdField.getText());
        }
       // String json = Loader.generateJson(ItemDefinitionLoader.getItemFromId(originalId), newId);
       // serverCodeBuilder.append(json).append("\n");

    }

    private final StringBuilder clientCodeBuilder = new StringBuilder();
    private final StringBuilder serverCodeBuilder = new StringBuilder();

    private void updateControls() {
        zoomLabel.setText("Zoom: " + def.modelZoom);
        xRotationLabel.setText("X Rotation: " + def.rotation_x);
        yRotationLabel.setText("Y Rotation: " + def.rotation_y);
        xOffsetLabel.setText("X offset: " + def.rotation_z);
        yOffsetLabel.setText("Y offset: " + def.translate_yz);
        zOffsetLabel.setText("Z offset: " + def.translate_x);
        xOffsetLabel.setText("X Translation: " + def.xTranslation);
        yOffsetLabel.setText("Y Translation: " + def.yTranslation);
        zOffsetLabel.setText("Z Translation: " + def.zTranslation);

        zoomSlider.setValue(def.modelZoom);
        xRotationSlider.setValue(def.rotation_x);
        yRotationSlider.setValue(def.rotation_y);
        xOffsetSlider.setValue(def.rotation_z);
        yOffsetSlider.setValue(def.translate_yz);
        zOffsetSlider.setValue(def.translate_x);
        xTranslationSlider.setValue(def.xTranslation);
        yTranslationSlider.setValue(def.yTranslation);
        zTranslationSlider.setValue(def.zTranslation);
    }

    private void saveEdit() {
        System.out.println("adding " + Arrays.toString(def.originalModelColors) + " - " + Arrays.toString(def.newModelColors));
        System.out.println("Adding zoom " + def.modelZoom);
        definitionEdits.add(new SavedItemDefinition(def.modelZoom, def.rotation_x, def.rotation_y, def.rotation_z, def.translate_yz, def.translate_x, def.originalModelColors, def.newModelColors, def.xTranslation, def.yTranslation, def.zTranslation));
    }

    private void undo() {
        if (definitionEdits.isEmpty()) {
            System.out.println("Empty lol");
            return;
        }
        definitionEdits.pollLast().reset(def);
        updateControls();
        clearCaches();
    }

    private void setFactories() {
        colorListView.setCellFactory(param -> new ListCell<ColorWrapper>() {
            @Override
            protected void updateItem(ColorWrapper item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                ColorPicker picker = new ColorPicker(); // ensures that the picker is not bound to things it shouldn't
                // be bound to
                setGraphic(picker);
                setText("occurrences: " + item.occurrences);
                picker.valueProperty().bindBidirectional(item.wrappedColor);
                picker.setOnMousePressed(x -> {
                    saveEdit();
                    System.out.println("Chose color");
                });
            }
        });

    }

    private void handleSliders() {
        zoomSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.modelZoom = var3.intValue();
            zoomLabel.setText("Zoom: " + def.modelZoom);
            System.out.println("Edited id = " + def.id + " | " + editedID);
        });

        xRotationSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.rotation_x = var3.intValue();
            xRotationLabel.setText("X Rotation: " + def.rotation_x);
        });

        yRotationSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.rotation_y = var3.intValue();
            yRotationLabel.setText("Y Rotation: " + def.rotation_y);
        });

        xOffsetSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.rotation_z = var3.intValue();
            xOffsetLabel.setText("X Offset: " + def.rotation_z);
        });

        yOffsetSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.translate_yz = var3.intValue();
            yOffsetLabel.setText("Y Offset: " + def.translate_yz);
        });

        zOffsetSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.translate_x = var3.intValue();
            zOffsetLabel.setText("Z Offset: " + def.translate_x);
        });

        xTranslationSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.xTranslation = var3.intValue();
            xTranslationLabel.setText("X Translation: " + def.xTranslation);
            Player.mruNodes.clear();
        });

        yTranslationSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.yTranslation = var3.intValue();
            yTranslationLabel.setText("Y Translation: " + def.yTranslation);
            Player.mruNodes.clear();
        });

        zTranslationSlider.valueProperty().addListener((var1, var2, var3) -> {
            def.zTranslation = var3.intValue();
            zTranslationLabel.setText("Z Translation: " + def.zTranslation);
            Player.mruNodes.clear();
        });

        slidersVBox.getChildren().forEach(node -> {
            node.setOnMousePressed(x -> {
                System.out.println("Detected click on slider " + node);
                saveEdit();
            });
        });

    }

    public void copy(String str) {
        try {
            if (str.length() < 1) {
                System.err.println("Copy input was < 1");
                return;
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(str);
            Transferable t = clipboard.getContents(this);
            clipboard.setContents(selection, selection);
        } catch (Exception e) {
            System.err.println("Copy threw an exception");
            e.printStackTrace();
        }
    }

    @FXML
    private void generateCode() {
        copy(clientCodeBuilder.toString());
    }

    private List<Integer> foundTextures;

    private void loadTextures() {
        texturesVBox.getChildren().forEach(node -> node.setVisible(false));
        if (def.originalModelColors == null) {
            return;
        }
        foundTextures = Arrays.stream(def.originalModelColors)
                .filter(id -> (id >= 50 || id == 40) && id <= Rasterizer3D.TEXTURE_AMOUNT).boxed().collect(Collectors.toList());
        if (foundTextures.size() > 0) {
            addTextureFieldListeners();
        }
    }

    private void addTextureFieldListeners() {
        for (int i = 0; i < Math.min(foundTextures.size(), 3); i++) {
            TextField textureField = (TextField) texturesVBox.getChildren().get(i);
            textureField.setVisible(true);
            final int index = i;
            textureField.setOnAction(x -> {
                int id = Integer.parseInt(textureField.getText());
                System.out.println("ID = " + id);
                modifyTexture(foundTextures.get(index), id);
            });
        }
    }

    private void modifyTexture(int previousId, int newId) {
        def.updateColor(previousId, newId);
        clearCaches();
    }

    private void handleButtons() {
        resetButton.setOnMouseClicked(x -> {
            reset();
        });

        idTextField.setOnAction(x -> {
            //reset();
            clearCaches();
            setEditedItem();
            readModelColors();
            loadTextures();
            update();
            definitionEdits.clear();
        });

        generateJsonButton.setOnMouseClicked(x -> {
            copy(serverCodeBuilder.toString());
        });

        saveButton.setOnMouseClicked(x -> {
 notifyServer();
            def.originalModelColors = null;
            def.newModelColors = null;
            //Player.modelCache.clear();

        });

    }

    private void notifyServer() {
        // Client.instance.sendColors(def.id,def.originalModelColors,def.newModelColors);
    }

    private void readModelColors() {
        System.out.println("previous colors " + Arrays.toString(def.originalModelColors));
        System.out.println("previous colors " + Arrays.toString(def.newModelColors));
        def.originalModelColors = null;
        def.newModelColors = null;
        boolean fetchEquip = def.maleEquip1 > 0;
        Model model = def.getEquipModel(0);
        long start = System.currentTimeMillis();
        // clearCaches();
        while (model == null && (start + 3000) > System.currentTimeMillis()) {
            model = fetchEquip ? def.getEquipModel(0) : def.method202(0);
        }
        if (model == null) {
            System.out.println("Model was still null rip");
            return;
        }
        colorListView.getItems().clear();
        List<Map.Entry<Integer, Integer>> uniqueColors = ModelUtil.getColors(model);
        int[] colors = new int[uniqueColors.size()];
        for (int i = 0; i < uniqueColors.size(); i++) {
            colors[i] = uniqueColors.get(i).getKey();
        }

        def.originalModelColors = colors.clone();
        def.newModelColors = colors.clone();

        System.out.println("Set colors to " + Arrays.toString(def.originalModelColors));
        System.out.println("Set colors to " + Arrays.toString(def.newModelColors));

        uniqueColors.forEach(entry -> {
            int id = entry.getKey();
            int occurences = entry.getValue();
            if ((id < 50 && id != 40) || id > Rasterizer3D.TEXTURE_AMOUNT) {
                colorListView.getItems().add(new ColorWrapper(id, occurences));
            }
        });

    }

    private void setEditedItem() {
        editedID = Integer.parseInt(idTextField.getText());
        RSInterface rsi = RSInterface.interfaceCache[3214];
        for (int slot = 0; slot < 28; slot++) {
            rsi.inv[slot] = editedID + 1;
            rsi.invStackSizes[slot] = 1;
        }

        int currentNewId = def != null && !newIdField.getText()
                .isEmpty() ? Integer.parseInt(newIdField.getText()) : -1;
        if (currentNewId != -1) {
            appendClientCodeChanges();
            appendServerCodeChanges();
            System.out.println("appended changes");
            newIdField.setText(String.valueOf(currentNewId + 1));
        }
        def = ItemDef.get(editedID);
        System.out.println("Set def to " + def.id + " | " + editedID);
    }

    private void reset() {
        def.modelZoom = originalZoom;
        def.rotation_x = originalXRotation;
        def.rotation_y = originalYRotation;
        def.rotation_z = originalXOffset;
        def.translate_yz = originalYOffset;
        def.translate_x = originalZOffset;
        def.originalModelColors = new int[originalModelColors.length];
        for (int i = 0; i < originalModelColors.length; i++) {
            def.originalModelColors[i] = originalModelColors[i];
            def.newModelColors[i] = originalModelColors[i];
        }
        def.xTranslation = originalXTranslation;
        def.yTranslation = originalYTranslation;
        def.zTranslation = originalZTranslation;
        clearCaches();
        updateControls();
    }

    private void update() {
        originalZoom = def.modelZoom;
        originalXRotation = def.rotation_x;
        originalYRotation = def.rotation_y;
        originalXOffset = def.rotation_z;
        originalYOffset = def.translate_yz;
        originalZOffset = def.translate_x;
        originalModelColors = new int[def.originalModelColors.length];
        for (int i = 0; i < def.originalModelColors.length; i++) {
            originalModelColors[i] = def.originalModelColors[i];
        }
        originalXTranslation = def.xTranslation;
        originalYTranslation = def.yTranslation;
        originalZTranslation = def.zTranslation;
        updateControls();
    }

    private void clearCaches() {
        ItemDef.mruNodes1.clear();
        ItemDef.mruNodes2.clear();
        Player.mruNodes.clear();
    }

    private class ColorWrapper {
        private final ObjectProperty<Color> wrappedColor;
        private final int color;
        private final int occurrences;

        public ColorWrapper(int color, int occurrences) {
            this.color = color;
            this.wrappedColor = new SimpleObjectProperty<>(JFXColorUtils.convert(color));
            this.wrappedColor.addListener((observable, oldValue, newValue) -> {
                int convertedNewValue = JFXColorUtils.convert(newValue);
                updateColor(convertedNewValue);
            });
            this.occurrences = occurrences;
        }

        private void updateColor(int newColor) {
            def.updateColor(color, newColor);
            clearCaches();
        }
    }

}
*/
