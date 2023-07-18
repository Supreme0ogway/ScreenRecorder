package RunningPackage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.awt.GraphicsEnvironment;

public class Home {

    private Stage stage;

    public Home(Stage stage) {
        this.stage = stage;
    }

    // Method to get the root node of the UI elements to be displayed
    public StackPane getRootNode() {
        // Create a button labeled "Screen capture"
        Button recordButton = new Button("Screen capture");

        // Create a ChoiceBox to display the names of active screens
        ChoiceBox<String> screenChoiceBox = new ChoiceBox<>();
        // Get the names of active screens
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.GraphicsDevice[] screens = ge.getScreenDevices();
        for (int i = 0; i < screens.length; i++) {
            screenChoiceBox.getItems().add("Screen " + (i + 1));
        }
        screenChoiceBox.setValue(screenChoiceBox.getItems().get(0)); // Set the first screen as default

        // Handle the button click event
        recordButton.setOnAction(e -> {
            // You can perform any action here when the button is clicked.
            // For example, you could get the selected screen name and do something with it.
            String selectedScreen = screenChoiceBox.getValue();
            System.out.println("capture button clicked for " + selectedScreen);

            // Temporarily hide the primary stage
            stage.toBack();

            // Get the corresponding Screen object based on the selected screen index
            int selectedIndex = screenChoiceBox.getItems().indexOf(selectedScreen);
            if (selectedIndex >= 0 && selectedIndex < screens.length) {
                Screen screen = Screen.getScreens().get(selectedIndex);
                // Call the Capture class to capture and display the screen image
                Capture.captureAndDisplayScreen(screen, stage);
            }
        });

        // Create a VBox to stack elements vertically
        VBox layout = new VBox(10); // 10 is the spacing between elements
        layout.getChildren().addAll(recordButton, screenChoiceBox);
        layout.setAlignment(Pos.CENTER); // Center the elements within the VBox

        // Create a new StackPane and use it as the root node for the scene
        StackPane root = new StackPane(layout);

        return root;
    }
}