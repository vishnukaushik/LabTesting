package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
    public void logout(){
        System.exit(1);
    }
}
