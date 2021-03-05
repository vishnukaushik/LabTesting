package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstScene {
    @FXML TextField username;
    @FXML TextField password;
    @FXML Label message = new Label();
    @FXML public void getUsername(ActionEvent actionEvent) throws IOException {
        String realUsername="admin";
        String realPassword="admin";
        if((realUsername.equals(username.getText())) && (realPassword.equals(password.getText())))
        {
            message.setText("Login Successful");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondScene.fxml"));
            Parent root = loader.load();
            SecondScene controller = loader.getController();
//            TODO add name and roll no. from server to below Student object
            //controller.setStudent(new Student("Vishnu","059"));
            controller.setTime();
            Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            primaryStage.setScene(new Scene(root,600,500));

        }
        else
        {
            message.setText("Login Failed!!! Retry");
        }
    }
}
