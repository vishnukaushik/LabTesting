package sample;

import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.sql.*;
import java.util.*;
import static java.lang.Class.forName;
import java.net.InetAddress;
import java.net.UnknownHostException;


import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SecondScene {

    public static boolean exit_status= false;
    @FXML Label StudentName = new Label();
    @FXML Label StudentRoll = new Label();
    @FXML Label endtime = new Label();
    public void setStudent(Student student)
    {
        StudentName.setText("Name: "+student.getName());
        StudentRoll.setText("Roll Number: "+student.getRoll());
    }
    public void setTime() throws SQLException {
        LocalTime present = LocalTime.now();
        String initial = "The session ends at " + present.plusHours(25).format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root","root");
        Statement stmt = con.createStatement();
        int rs1=stmt.executeUpdate("UPDATE clients SET  status=0 WHERE IP='192.168.0.20' ");//and check_out=NULL

        endtime.setText(initial);
    }

    public void logout(ActionEvent actionEvent) throws IOException, InterruptedException, SQLException {

        System.out.println("logout invoked");
        InetAddress localhost = InetAddress.getLocalHost();
        String IP = (localhost.getHostAddress()).trim();
        System.out.println(IP);
//        Connection con=DriverManager.getConnection("jdbc:mysql://172.16.6.17:3306/lab","root","Lab@Authentication123");
//        Statement stmt = con.createStatement();
        //also set status = 1
//        int rs1=stmt.executeUpdate("UPDATE logs SET  check_out=NOW() WHERE check_out is NULL && sys_allocated='1455' ");//and check_out=NULL
//        int rs2=stmt.executeUpdate("UPDATE clients SET  status=1 WHERE id='1455'");
        System.out.println("Check_out and status updated");
       // int rs2=stmt.executeUpdate("UPDATE cl SET  check_out=NOW() WHERE check_out=NULL && sys_allocated=1456 ");//and check_out=NULL

        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root","root");
        Statement stmt = con.createStatement();
        int rs1=stmt.executeUpdate("UPDATE clients SET  status=1 WHERE IP='192.168.0.20' ");//and check_out=NULL


        client.Platform_store.close();

        Main.cleanup();
        Main.restartProcess();

//        TODO update database when user logs out
    }
}
