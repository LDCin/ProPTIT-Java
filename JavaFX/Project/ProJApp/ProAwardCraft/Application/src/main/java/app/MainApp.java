package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main/MainView.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("ProAwardCraft");
        primaryStage.setScene(new Scene(root, 1500, 1000));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}