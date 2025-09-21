package rumi.ui;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import rumi.Rumi;
import rumi.ui.components.DialogBox;

/**
 * Main JavaFX entry point
 */
public class MainWindow extends AnchorPane {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private final Image userProfilePicture =
            new Image(this.getClass().getResourceAsStream("/images/user.jpeg"));
    private final Image rumiProfilePicture =
            new Image(this.getClass().getResourceAsStream("/images/rumi.png"));

    private final BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> responseQueue = new LinkedBlockingQueue<>();
    private final Rumi rumi = new Rumi(commandQueue, responseQueue);

    /**
     * Initialise the main application by running Rumi and handler for Rumi's response in two
     * different threads.
     */
    @FXML
    public void initialize() {

        // Run rumi asynchronously
        CompletableFuture.runAsync(() -> rumi.run());

        // Handle Rumi's response asynchronously
        CompletableFuture.runAsync(() -> {
            String rumiResponse;
            while (true) {
                try {
                    rumiResponse = responseQueue.take();
                } catch (InterruptedException e) {
                    rumiResponse =
                            "Rumi failed to receive your message for an unknown reason :(\nPlease resend your message!";
                }

                final String response = rumiResponse;
                if (rumiResponse.equals(Rumi.RESPONSE_GOODBYE)) {
                    terminateGui();
                }

                replyUser(response);
            }
        });

        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Creates and add a new DialogBox containing the given response.
     */
    private void replyUser(String response) {
        Platform.runLater(() -> {
            dialogContainer.getChildren()
                    .addAll(DialogBox.makeRumiDialog(response, rumiProfilePicture));
        });
    }

    private void terminateGui() {
        CompletableFuture.runAsync(() -> {
            try {
                // Give time for user to read Rumi's goodbye message before terminating the GUI
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Platform.runLater(() -> {
                Platform.exit();
                System.exit(0);
            });
        });
    }

    /**
     * Creates a dialog box containing user input, and appends it to the dialog container. Clears
     * the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().addAll(DialogBox.makeUserDialog(input, userProfilePicture));
        userInput.clear();
        commandQueue.offer(input);
    }
}
