package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        String username = "root",password="root", url = "jdbc:mysql://localhost:3306/Lab";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful");
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed!!!");

        }
        launch(args);
    }
}
