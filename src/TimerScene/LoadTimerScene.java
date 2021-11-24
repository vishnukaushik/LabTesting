package TimerScene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadTimerScene implements Runnable{
    Stage secondaryStage;
    Parent root;
    LoadTimerScene(Stage secondaryStage, Parent root){
        this.secondaryStage = secondaryStage;
        this.root = root;
    }
    @Override
    public void run() {
        secondaryStage.setScene(new Scene(root));
        secondaryStage.show();
    }
}
