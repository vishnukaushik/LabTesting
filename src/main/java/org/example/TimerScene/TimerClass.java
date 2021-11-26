package org.example.TimerScene;


import javafx.application.Platform;
import org.example.SecondScene.SecondScene;
import org.example.sample.MainClass;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

public class TimerClass implements Runnable {
    public static LocalTime sessionEndTime, triggerTime;
    public static boolean extend = false, canPopUp = true;

    public TimerClass(LocalTime loggedInTime) {
        sessionEndTime = loggedInTime.plusSeconds(30);
        triggerTime = sessionEndTime.minusSeconds(10);
//        TODO uncomment below line
//        this.triggerTime = sessionEndTime.minusMinutes(10);
//        this.sessionEndTime = loggedInTime.plusHours(1);
    }

    public static void updateTime() {
        sessionEndTime = sessionEndTime.plusSeconds(30);
        triggerTime = sessionEndTime.minusSeconds(10);
        System.out.println("Time Updated");
        System.out.println("\t\t" + sessionEndTime);
        System.out.println("\t\t" + triggerTime);
        canPopUp = true;
//        TODO uncomment below 2 lines
//        sessionEndTime = sessionEndTime.plusHours(1);
//        triggerTime = sessionEndTime.minusMinutes(10);
    }

    public void run() {

        while (sessionEndTime.compareTo(LocalTime.now()) > 0 && !SecondScene.exit_status) {
            if (triggerTime.compareTo(LocalTime.now()) <= 0 && canPopUp) {
                canPopUp = false;
                Platform.runLater(new LoadTimerScene());
            }
        }
        SecondScene.exit_status = true;
        try {
            MainClass.logout();
        } catch (SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Session Ended");
    }
}