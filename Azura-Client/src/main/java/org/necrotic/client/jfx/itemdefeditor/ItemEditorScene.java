
        package org.necrotic.client.jfx.itemdefeditor;

        import javafx.application.Application;
        import javafx.application.Platform;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.stage.Stage;
        import javafx.stage.StageStyle;

        import java.io.IOException;
        import java.net.URL;

public class ItemEditorScene extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        String resourcePath = "/ItemDefEditor.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(location);
        try {
            stage.setTitle("Itemdef Editor");
            Scene scene = new Scene(loader.load());
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
            scene.getStylesheets().add("modena_dark.css");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }

    }

}
