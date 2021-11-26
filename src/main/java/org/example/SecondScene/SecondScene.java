package org.example.SecondScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.Credentials.DbCredentials;
import org.example.TimerScene.TimerClass;
import org.example.sample.MainClass;
import org.example.sample.Student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.example.sample.MainClass.IP;

public class SecondScene implements DbCredentials {

    public static boolean exit_status = false;
    @FXML
    Label StudentName = new Label();
    @FXML
    Label StudentRoll = new Label();
    @FXML
    Label endTime = new Label();
    public static LocalTime loggedInTime;
    public static Student student;

    public void setStudent(Student student) {
        SecondScene.student = student;
        StudentName.setText("Name: " + student.getName());
        StudentRoll.setText("Roll Number: " + student.getRoll());
    }

    public void setTime() throws SQLException {
        loggedInTime = LocalTime.now();
        String initial = "The session ends at " + loggedInTime.plusHours(25).format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
        Statement stmt = con.createStatement();
        int rs1 = stmt.executeUpdate("UPDATE clients SET  status=0 WHERE IP=" + "'" + IP + "' ");
        endTime.setText(initial);
    }

    public void openThread() {
        TimerClass task = new TimerClass(loggedInTime);
        Thread t = new Thread(task);
        t.start();
    }

    public void updateTime(LocalTime updatedTime) {
        String initial = "The session ends at " + updatedTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        System.out.println("Time Updated to:");
        System.out.println(initial);
        endTime.setText(initial);
    }

    public void logoutScene(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException {
        TimerClass.extend = false;
        if(TimerClass.canPopUp)
        {
            System.out.println("Logout called from logout btn");
            MainClass.logout();
        }
    }

    public static void submitFeedback(String feedback) throws SQLException {
        Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
        Statement stmt = con.createStatement();
        String statement = "INSERT INTO feedback(IP, roll_no, description) VALUES(" + '"' + IP + '"' + ',' + '"' + student.getRoll() + '"' + ',' + '"' + feedback + '"' + ")";
        int rs1 = stmt.executeUpdate(statement);
    }

}


