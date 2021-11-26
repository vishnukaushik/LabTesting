package org.example.FirstScene;

import org.example.Credentials.DbCredentials;
import org.example.SecondScene.SecondScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.sample.Client;
import org.example.sample.MainClass;
import org.example.sample.Student;

import java.io.IOException;
import java.sql.*;

import static java.lang.Class.forName;

public class FirstScene implements DbCredentials {
    @FXML
    TextField username;
    @FXML
    TextField password;
    @FXML
    Label message = new Label();

    @FXML
    public void getUsername(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from lab.auth");
        String realUsername = "admin";
        String realPassword = "admin";

        if (rs.next()) {
            realUsername = rs.getString(1);
            realPassword = rs.getString(2);
        }

        if ((realUsername.equals(username.getText())) && (realPassword.equals(password.getText()))) {
            message.setText("Login Successful");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("..\\org.example.SecondScene\\secondScene.fxml"));
            Parent root = loader.load();
            SecondScene controller = loader.getController();
            controller.setStudent(new Student("Admin", "108"));

            //insert into logs -
            //change status as well
            int rs1 = stmt.executeUpdate("UPDATE clients SET  status=0 WHERE IP=" + "'" + MainClass.IP + "'");//and check_out=NULL
            int rs2 = stmt.executeUpdate("INSERT INTO `logs`(roll_no,name,sys_allocated) VALUES ('108','Admin'," + "'" + MainClass.IP + "'" + ")");

            controller.setTime();
            controller.openThread();
            Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Client.Platform_store = primaryStage;
//          Kiosk.unblockKey();

            Scene scene = new Scene(root, 600, 500);
            scene.setUserData(loader);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.show();
        } else {
            message.setText("Login Failed!!! Retry");
        }

    }
}
