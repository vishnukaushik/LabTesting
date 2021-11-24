package TimerScene;


import SecondScene.SecondScene;
import com.mysql.cj.jdbc.MysqlSQLXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Client;
import sample.Main;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

public class TimerClass implements Runnable {
    LocalTime sessionEndTime, triggerTime;
    public static boolean extend = false;
    public TimerClass(LocalTime loggedInTime){
        this.sessionEndTime = loggedInTime.plusSeconds(10);
        this.triggerTime = sessionEndTime.minusSeconds(3);
//        TODO uncomment below line
//        this.triggerTime = sessionEndTime.minusMinutes(10);
//        this.sessionEndTime = loggedInTime.plusHours(1);
    }

    public void run() {;
        while(sessionEndTime.compareTo(LocalTime.now())>0 && !SecondScene.exit_status){
            if(triggerTime.compareTo(LocalTime.now())>0)
            {
//              TODO get the timer screen working
                Stage secondaryStage = new Stage();
                secondaryStage.setScene();
                secondaryStage.show();
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
