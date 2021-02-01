package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

public class SampleController {
    @FXML TextField username;
    @FXML TextField password;
    @FXML Label message = new Label();
    @FXML public void getUsername(ActionEvent actionEvent) {
//        TODO connect to the DataBase
        String realUsername="admin";
        String realPassword="1234";
        if((realUsername.equals(username.getText())) && (realPassword.equals(password.getText())))
        {
            message.setText("Login Successful");

        }
        else
        {
            message.setText("Login Failed!!! Retry");
        }
    }
}
