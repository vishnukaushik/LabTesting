package org.example.TimerScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadTimerScene implements Runnable {
    public static Stage secondaryStage;

    @Override
    public void run() {
        Parent root = null;
        try {
            root = FXMLLoader.load(TimerClass.class.getResource("/timerScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        secondaryStage = new Stage();
//        secondaryStage.setAlwaysOnTop(true);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        assert root != null;
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }
}