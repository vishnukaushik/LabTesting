package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Kiosk.blockKeys();
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root));
        Kiosk.kiosk(primaryStage);
        primaryStage.show();
    }




    public static void main(String[] args) {
//        TODO connect to database here
        launch(args);
    }
}
