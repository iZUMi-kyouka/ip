import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import rumi.Rumi;

/** Main JavaFX entry point */
public class Main extends Application {


    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;
    private Image userProfilePicture =
            new Image(this.getClass().getResourceAsStream("./images/user.jpeg"));
    private Image rumiProfilePicture =
            new Image(this.getClass().getResourceAsStream("./images/rumi.png"));
    private BlockingQueue<String> commandQueue;
    private BlockingQueue<String> responseQueue;
    private Rumi rumi;

    @Override
    public void start(Stage stage) {
        commandQueue = new LinkedBlockingQueue<String>();
        responseQueue = new LinkedBlockingQueue<String>();
        rumi = new Rumi(commandQueue, responseQueue);

        // Set up the required components
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);

        // Styling
        stage.setTitle("Rumi（ルミ）");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        // Handling user input
        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        // Run rumi
        CompletableFuture.runAsync(() -> rumi.run());

        // Handle Rumi's response
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

        // Scroll down to the end every time dialogContainer's height changes.
        dialogContainer.heightProperty().addListener((observable) -> scrollPane.setVvalue(1.0));

        stage.setScene(scene);
        stage.show();

        // More code to be added here later
    }


    /**
     * Creates a dialog box containing user input, and appends it to the dialog container. Clears
     * the user input after processing.
     */
    private void handleUserInput() {
        String input = userInput.getText();
        dialogContainer.getChildren().addAll(new DialogBox(input, userProfilePicture));
        userInput.clear();
        commandQueue.offer(input);
    }
}
