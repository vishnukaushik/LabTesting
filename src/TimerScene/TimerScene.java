package TimerScene;
import SecondScene.SecondScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class TimerScene{
    @FXML private javafx.scene.control.Button btn_cancel;

    public void cancel(ActionEvent actionEvent){
        TimerClass.canPopUp = false;
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
    public void extendSession(ActionEvent actionEvent){
//      TODO(manage the time)
        TimerClass.extend = true;
        TimerClass.updateTime();
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
        SecondScene.openThread(TimerClass.sessionEndTime);
        System.out.println("session extended");
    }
}