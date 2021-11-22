package sample;
//192.168.0.139
import FeedbackScene.FeedbackScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TempMain extends Application {
//    public static void StartApplication(Stage primaryStage) throws IOException {
//        Parent root = FXMLLoader.load(TimerScene.class.getResource("timerScene.fxml"));
//        primaryStage.setTitle("Timer Page");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//    }


    public static void StartApplication(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(FeedbackScene.class.getResource("../FeedbackScene/feedbackScene.fxml"));
        primaryStage.setTitle("Feedback Page");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        StartApplication(primaryStage);
    }
    public static void main(String[] args) {
        Application.launch();
    }
}
