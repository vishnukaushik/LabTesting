package TimerScene;


import SecondScene.SecondScene;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

public class TimerClass implements Runnable {
    public static LocalTime sessionEndTime, triggerTime;
    public static boolean extend = false, canPopUp = true;
    public TimerClass(LocalTime loggedInTime){
        sessionEndTime = loggedInTime.plusSeconds(30);
        triggerTime = sessionEndTime.minusSeconds(10);
//        TODO uncomment below line
//        this.triggerTime = sessionEndTime.minusMinutes(10);
//        this.sessionEndTime = loggedInTime.plusHours(1);
    }

    public static void updateTime(){
        sessionEndTime = sessionEndTime.plusSeconds(30);
        triggerTime = sessionEndTime.minusSeconds(10);
        System.out.println("Time Updated");
        System.out.println("\t\t"+sessionEndTime);
        System.out.println("\t\t"+triggerTime);
        canPopUp = true;
//        TODO uncomment below 2 lines
//        sessionEndTime = sessionEndTime.plusHours(1);
//        triggerTime = sessionEndTime.minusMinutes(10);
    }

    public void run() {
        Parent root = null;
        try {
            root = FXMLLoader.load(TimerClass.class.getResource("timerScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        assert root != null;
        Platform.runLater(new LoadTimerScene(secondaryStage, root));
        while(sessionEndTime.compareTo(LocalTime.now())>0 && !SecondScene.exit_status){
            System.out.println(LocalTime.now());
            if(triggerTime.compareTo(LocalTime.now())<=0 && canPopUp)
            {
                canPopUp = false;
            }
        }
        SecondScene.exit_status = true;
        try {
            Main.logout();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Session Ended");
    }
}
