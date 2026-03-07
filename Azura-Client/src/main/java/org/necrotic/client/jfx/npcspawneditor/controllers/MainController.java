/*

package org.necrotic.client.jfx.npcspawneditor.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.necrotic.client.Client;
import org.necrotic.client.jfx.npcspawneditor.model.Direction;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


public class MainController implements Initializable {


    @FXML
    private TextField idField, radiusField, formatField, positionField;

    @FXML
    private ChoiceBox<Direction> directionChoiceBox;

    @FXML
    private Button saveButton;

    @FXML
    private CheckBox coordinateCheckBox, editingCheckBox;

    private final Client client = Client.instance;

    private int x, y, z;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            Arrays.stream(Direction.values()).forEach(directionChoiceBox.getItems()::add);
            radiusField.managedProperty().bind(coordinateCheckBox.selectedProperty());
            Client.instance.npcSpawnEditing.bind(editingCheckBox.selectedProperty());
        });
        saveButton.setOnAction(x -> {
            Platform.runLater(() -> {
                int id = Integer.parseInt(idField.getText());
                int direction = directionChoiceBox.getSelectionModel().getSelectedItem().toInteger();
                int radius = coordinateCheckBox.isSelected() ? Integer.parseInt(radiusField.getText()) : 0;
                writeData(id, direction, radius);
            });
        });

        client.getPos().addListener((observable, oldPos, pos) -> {
            Platform.runLater(() -> {
                x = pos.x;
                y = pos.y;
                z = pos.z;
                positionField.setText(x + ", " + y + ", " + z);
            });
        });
    }

    private void writeData(int id, int direction, int radius) {
        Client.getOut().putOpcode(154);
        Client.getOut().putByte(formatField.getText().length() + 11);
        Client.getOut().putShort(id);
        Client.getOut().putByte(direction);
        Client.getOut().putByte(radius);
        Client.getOut().putShort(x);
        Client.getOut().putShort(y);
        Client.getOut().putShort(z);
        Client.getOut().putString(formatField.getText());
        System.out.println(Arrays.toString(formatField.getText().toCharArray()) + " | " + formatField.getText().toCharArray().length);
    }


}


*/
