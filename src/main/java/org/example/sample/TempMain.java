package org.example.sample;
//192.168.0.139

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.SecondScene.SecondScene;

import java.io.IOException;
import java.sql.SQLException;

public class TempMain extends Application {
//    public static void StartApplication(Stage primaryStage) throws IOException {
//        Parent root = FXMLLoader.load(org.example.TimerScene.class.getResource("timerScene.fxml"));
//        primaryStage.setTitle("Timer Page");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();
//    }


    public static void StartApplication(Stage primaryStage) throws IOException, SQLException {
//        Parent root = FXMLLoader.load(org.example.FeedbackScene.class.getResource("../org.example.FeedbackScene/feedbackScene.fxml"));
//        primaryStage.setTitle("Feedback Page");
//        primaryStage.setScene(new Scene(root));
//        primaryStage.show();

// TODO uncomment in setTime() function in secondScene Controller
//        TODO uncomment in logout() function in Main class aswell.
        FXMLLoader loader = new FXMLLoader(TempMain.class.getResource("..\\org.example.SecondScene\\secondScene.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

        SecondScene controller = loader.getController();
        controller.setStudent(new Student("Admin", "108"));
        controller.setTime();
        primaryStage.show();
        controller.openThread();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StartApplication(primaryStage);
    }
//    public static void main(String[] args) {
//        Application.launch();
//    }
}
