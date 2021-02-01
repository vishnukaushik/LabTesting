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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //Parent second = FXMLLoader.load(getClass().getResource("Secondary.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
        /*Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Second page");
        secondaryStage.setScene(new Scene(second,350,500));
        secondaryStage.show();*/
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
