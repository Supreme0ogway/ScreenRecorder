package RunningPackage;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Capture {

    public static void captureAndDisplayScreen(Screen screen, Stage primaryStage) {
        try {
            Robot robot = new Robot();
            Rectangle bounds = new Rectangle((int) screen.getBounds().getMinX(), (int) screen.getBounds().getMinY(),
                    (int) screen.getBounds().getWidth(), (int) screen.getBounds().getHeight());
            BufferedImage screenCapture = robot.createScreenCapture(bounds);

            // Convert BufferedImage to JavaFX Image
            Image image = SwingFXUtils.toFXImage(screenCapture, null);

            // Create a new stage to display the captured image
            Stage stage = new Stage();
            stage.setTitle("Captured Screen");

            // Create a ChoiceBox to select the save location
            ChoiceBox<String> saveLocationChoiceBox = new ChoiceBox<>();
            saveLocationChoiceBox.getItems().addAll("Desktop", "Documents", "Pictures", "Custom");
            saveLocationChoiceBox.setValue(saveLocationChoiceBox.getItems().get(0)); // Set the first option as default

            // Create a text field to enter the file name
            TextField fileNameTextField = new TextField("screenshot.png");

            // Create a button to save the image
            Button saveButton = new Button("Save");
            saveButton.setOnAction(e -> {
                String fileName = fileNameTextField.getText();
                if (!fileName.toLowerCase().endsWith(".png")) {
                    fileName += ".png";
                }

                File outputFile;
                if (saveLocationChoiceBox.getValue().equals("Custom")) {
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle("Choose Directory to Save");
                    File selectedDirectory = directoryChooser.showDialog(stage);
                    if (selectedDirectory == null) {
                        return;
                    }
                    outputFile = new File(selectedDirectory.getAbsolutePath() + File.separator + fileName);
                } else {
                    String homeDir = System.getProperty("user.home");
                    String selectedDir = saveLocationChoiceBox.getValue().toLowerCase();
                    outputFile = new File(homeDir + File.separator + selectedDir + File.separator + fileName);
                }

                try {
                    // Create directories if they don't exist
                    Path outputPath = Paths.get(outputFile.getParent());
                    Files.createDirectories(outputPath);

                    ImageIO.write(screenCapture, "png", outputFile);
                    System.out.println("Image saved successfully!");

                    // Close the stage after saving the image
                    stage.close();
                    // Show the primary stage again after the capture is done
                    primaryStage.toFront();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(800); // Set the preferred width for the displayed image
            imageView.setFitHeight(600); // Set the preferred height for the displayed image

            VBox layout = new VBox(10);
            layout.getChildren().addAll(imageView, saveLocationChoiceBox, fileNameTextField, saveButton);

            Scene scene = new Scene(layout);
            stage.setScene(scene);
            stage.show();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}