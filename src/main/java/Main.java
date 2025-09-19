import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import rumi.Rumi;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            stage.setTitle(Rumi.CHATBOT_NAME);
            stage.setScene(scene);

            stage.setResizable(true);
            stage.setHeight(800);
            stage.setWidth(600);
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
