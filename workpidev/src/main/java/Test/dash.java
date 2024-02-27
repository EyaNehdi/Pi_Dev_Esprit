package Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class dash extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dashbord.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(new Scene(root,1315,890));
            primaryStage.setTitle("Gestion des certificats");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
