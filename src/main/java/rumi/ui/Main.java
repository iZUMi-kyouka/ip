package rumi.ui;

import java.io.IOException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import rumi.Rumi;

/**
 * A GUI for Rumi using FXML
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            loadFonts();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Set window metadata
            stage.setTitle(Rumi.CHATBOT_NAME);
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));

            // Set default window size and minimum size
            stage.setResizable(true);
            stage.setHeight(800);
            stage.setWidth(800);
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.printf("[ERROR] Failed to initialise GUI: %s\n", e.getMessage());
        }
    }

    private void loadFonts() {
        // Load required fonts
        InputStream robotoStream = Main.class.getResourceAsStream("/fonts/Roboto-Regular.ttf");
        InputStream iosevkaStream = Main.class.getResourceAsStream("/fonts/Iosevka-Medium.ttf");

        if (robotoStream == null) {
            System.err.println("[ERROR] Font 'Roboto' not found.");
        }
        if (iosevkaStream == null) {
            System.err.println("[ERROR] Font 'Iosevka Medium' not found.");
        }

        Font fontIosevka = Font.loadFont(iosevkaStream, 16);
        Font fontRoboto = Font.loadFont(robotoStream, 16);
        if (fontIosevka == null) {
            System.err.println("[ERROR] Font 'Iosevka Medium' is corrupted.");
        } else {
            System.out.printf("[INFO] Using font 'Iosevka Medium' with family '%s'\n",
                    fontIosevka.getFamily());
        }
        if (fontRoboto == null) {
            System.err.println("[ERROR] Font 'Roboto' is corrupted.");
        } else {
            System.out.printf("[INFO] Using font 'Roboto' with family '%s'\n", fontRoboto.getFamily());
        }
    }
}
