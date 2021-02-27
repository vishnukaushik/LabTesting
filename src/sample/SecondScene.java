package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SecondScene {


    @FXML Label StudentName = new Label();
    @FXML Label StudentRoll = new Label();
    @FXML Label endtime = new Label();
    public void setStudent(Student student)
    {
        System.out.println("Hi");
        StudentName.setText("Name: "+student.getName());
        StudentRoll.setText("Roll Number: "+student.getRoll());
    }
    public void setTime()
    {
        LocalTime present = LocalTime.now();
        String initial = "The session ends at " + present.plusHours(25).format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        endtime.setText(initial);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("firstScene.fxml"));
        Parent root = loader.load();
        FirstScene controller = loader.getController();
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(new Scene(root,600,500));
//        TODO update database when user logs out
    }

}
