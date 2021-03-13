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
    public void setTime()
    {
        LocalTime present = LocalTime.now();
        String initial = "The session ends at " + present.plusHours(25).format(DateTimeFormatter.ofPattern("hh:mm:ss a"));
        endtime.setText(initial);
    }

    public void logout(ActionEvent actionEvent) throws IOException, InterruptedException {

        System.out.println("logout invoked");
        client.Platform_store.close();

        Main.cleanup();
        Main.restartProcess();

//        TODO update database when user logs out
    }
}
