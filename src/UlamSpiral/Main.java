package UlamSpiral;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ulamSpiral.fxml"));
        loader.setResources(ResourceBundle.getBundle("ulamSpiral"));
        primaryStage.setTitle("Ulam's Spiral");
        primaryStage.setScene(new Scene(loader.load(), 800, 640));
        UlamSpiralController controller = loader.getController();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
