package SecondScene;

import Credentials.DbCredentials;
import TimerScene.TimerClass;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import sample.Main;
import sample.Student;
import sample.Client;

import java.sql.*;

import static java.lang.Class.forName;


import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

public class SecondScene implements DbCredentials{

    public static boolean exit_status= false;
    @FXML Label StudentName = new Label();
    @FXML Label StudentRoll = new Label();
    @FXML Label endTime = new Label();
    public static LocalTime loggedInTime;
    public void setStudent(Student student)
    {
        StudentName.setText("Name: "+student.getName());
        StudentRoll.setText("Roll Number: "+student.getRoll());
    }
    public void setTime() throws SQLException {
        loggedInTime = LocalTime.now();
        String initial = "The session ends at " + loggedInTime.plusHours(25).format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
//        TODO uncomment below 3 lines.
//        Connection con= DriverManager.getConnection(DbCredentials.url,DbCredentials.user,DbCredentials.password);
//        Statement stmt = con.createStatement();
//        int rs1=stmt.executeUpdate("UPDATE clients SET  status=0 WHERE IP="+"'"+Main.IP+"' ");//and check_out=NULL

        endTime.setText(initial);
    }

    public static void openThread(){
        TimerClass task = new TimerClass(loggedInTime);
        Platform.runLater(task);
    }
    public static void openThread(LocalTime loggedTime){
        TimerClass task = new TimerClass(loggedTime);
        Platform.runLater(task);
    }
    public void logoutScene(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException {
        TimerClass.extend = false;
        Main.logout();
    }

}


