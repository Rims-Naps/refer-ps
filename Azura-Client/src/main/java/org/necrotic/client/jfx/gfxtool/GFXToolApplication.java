/*

package org.necrotic.client.jfx.gfxtool;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GFXToolApplication extends Application {

    @Override
    public void start(Stage arg0) throws Exception {

        String resourcePath = "/gfxtoolui.fxml";
        URL location = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(location);
        try {
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(this.getClass().getResource("/modena_dark.css").toExternalForm());
            arg0.setScene(scene);
            arg0.show();
        } catch (IOException e) {
            e.printStackTrace();
            Platform.exit();
        }

    }


}

*/
