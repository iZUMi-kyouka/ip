import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import rumi.Rumi;

/** Main JavaFX entry point */
public class MainWindow extends AnchorPane {

    private Scene scene;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Image userProfilePicture =
            new Image(this.getClass().getResourceAsStream("./images/user.jpeg"));
    private Image rumiProfilePicture =
            new Image(this.getClass().getResourceAsStream("./images/rumi.png"));

    private BlockingQueue<String> commandQueue;
    private BlockingQueue<String> responseQueue;
    private Rumi rumi;

    /** Initialises the main window with the Rumi instance, command and response queue */
    @FXML
    public void initialize() {
        commandQueue = new LinkedBlockingQueue<String>();
        responseQueue = new LinkedBlockingQueue<String>();
        rumi = new Rumi(commandQueue, responseQueue);

        // Run rumi asynchronously
        CompletableFuture.runAsync(() -> rumi.run());

        // Handle Rumi's response asynchronously
        CompletableFuture.runAsync(() -> {
            while (true) {
                String rumiResponse = "An error has occured :( Please restart Rumi.";
                try {
                    rumiResponse = responseQueue.take();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                final String response = rumiResponse;
                javafx.application.Platform.runLater(() -> {
                    dialogContainer.getChildren()
                            .addAll(DialogBox.getRumiDialog(response, rumiProfilePicture));
                });
            }
        });

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Creates a dialog box containing user input, and appends it to the dialog container. Clears
     * the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userProfilePicture));
        userInput.clear();
        commandQueue.offer(input);
    }
}
