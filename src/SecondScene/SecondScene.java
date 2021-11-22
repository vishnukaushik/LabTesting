package SecondScene;

import Credentials.DbCredentials;
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

public class SecondScene implements DbCredentials{

    public static boolean exit_status= false;
    @FXML Label StudentName = new Label();
    @FXML Label StudentRoll = new Label();
    @FXML Label endTime = new Label();
    public void setStudent(Student student)
    {
        StudentName.setText("Name: "+student.getName());
        StudentRoll.setText("Roll Number: "+student.getRoll());
    }
    public void setTime() throws SQLException {
        LocalTime present = LocalTime.now();
        String initial = "The session ends at " + present.plusHours(25).format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        Connection con= DriverManager.getConnection(DbCredentials.url,DbCredentials.user,DbCredentials.password);
        Statement stmt = con.createStatement();
        int rs1=stmt.executeUpdate("UPDATE clients SET  status=0 WHERE IP="+"'"+Main.IP+"' ");//and check_out=NULL

        endTime.setText(initial);
    }

    public void logout(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException {

        System.out.println("logout invoked");
        Connection con= DriverManager.getConnection(DbCredentials.url,DbCredentials.user,DbCredentials.password);
        Statement stmt = con.createStatement();
        //also set status = 1
        int rs1=stmt.executeUpdate("UPDATE logs SET  check_out=NOW() WHERE check_out is NULL && sys_allocated="+"'"+Main.IP+"'");//and check_out=NULL
        int rs2=stmt.executeUpdate("UPDATE clients SET  status=1 WHERE IP="+"'"+Main.IP+"'");
        System.out.println("Check_out and status updated");
       // int rs2=stmt.executeUpdate("UPDATE cl SET  check_out=NOW() WHERE check_out=NULL && sys_allocated=1456 ");//and check_out=NULL
//
//        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root","root");
//        Statement stmt = con.createStatement();
//        int rs1=stmt.executeUpdate("UPDATE clients SET  status=1 WHERE IP='192.168.0.20' ");//and check_out=NULL
////instead of making status 1 here why cant make it at begin.......

        Client.Platform_store.close();

        Main.cleanup();
        Main.restartProcess();

//        TODO update database when user logs out
    }
}


