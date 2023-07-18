package RunningPackage;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.Taskbar;
import java.awt.Taskbar.Feature;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class MainClass extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create an instance of the Home class and pass the Stage
        Home home = new Home(primaryStage);

        // Create the JavaFX scene using the root node from the Home class
        Scene scene = new Scene(home.getRootNode(), 200, 100);

        // Set the scene to the primary stage and display it
        primaryStage.setScene(scene);
        primaryStage.setTitle("Screen Snap");

        // Set the icon for the application's window
        javafx.scene.image.Image windowIcon = new javafx.scene.image.Image(getClass().getResourceAsStream("icon.png"));
        primaryStage.getIcons().add(windowIcon);

        // Set the taskbar icon if supported
        if (Taskbar.isTaskbarSupported() && Taskbar.getTaskbar().isSupported(Feature.ICON_IMAGE)) {
            try {
                // Load the icon image
                BufferedImage awtIcon = ImageIO.read(getClass().getResourceAsStream("icon.png"));

                // Set the taskbar icon image
                Taskbar.getTaskbar().setIconImage(awtIcon);
            } catch (IOException | UnsupportedOperationException e) {
                // Handle exceptions if the feature is not supported or if there is an I/O error
                e.printStackTrace();
            }
        }

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class MainClass extends Application {
//
//    @Override
//    public void start(Stage primaryStage) {
//        // Create an instance of the Home class and pass the Stage
//        Home home = new Home(primaryStage);
//
//        // Create the JavaFX scene using the root node from the Home class
//        Scene scene = new Scene(home.getRootNode(), 200, 100);
//
//        // Set the scene to the primary stage and display it
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("Screen Snap");
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}