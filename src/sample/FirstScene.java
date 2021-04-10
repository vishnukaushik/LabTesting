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
import java.sql.*;

import static java.lang.Class.forName;

public class FirstScene {
    @FXML TextField username;
    @FXML TextField password;
    @FXML Label message = new Label();
    @FXML public void getUsername(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        forName("com.mysql.cj.jdbc.Driver");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root","root");
        Statement stmt = con.createStatement();
        ResultSet rs=stmt.executeQuery("select * from lab.auth");
        String realUsername = "admin";
        String realPassword = "admin";

        if (rs.next())
        {
            realUsername = rs.getString(1);
            realPassword = rs.getString(2);
        }

        if((realUsername.equals(username.getText())) && (realPassword.equals(password.getText())))
        {
            message.setText("Login Successful");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("secondScene.fxml"));
            Parent root = loader.load();
            SecondScene controller = loader.getController();
//            TODO add name and roll no. from server to below Student object
            controller.setStudent(new Student("Admin","108"));
            //insert into logs -
            //change status as well
//            int rs1=stmt.executeUpdate("UPDATE clients SET  status=0 WHERE IP='192.168.0.20' ");//and check_out=NULL
//            int rs2=stmt.executeUpdate("INSERT INTO `logs`(roll_no,name,sys_allocated) VALUES ('"+roll+"','"+name+"','"+ids.get(0)+"')");

            controller.setTime();
            Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            client.Platform_store=primaryStage;
            Kiosk.unblockKey();
            primaryStage.setScene(new Scene(root,600,500));
            primaryStage.show();
        }
        else
        {
            message.setText("Login Failed!!! Retry");
        }
    }
}
