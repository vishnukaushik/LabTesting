package TimerScene;
import SecondScene.SecondScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import sample.Client;


public class TimerScene{
    @FXML private javafx.scene.control.Button btn_cancel;

    public void cancel(ActionEvent actionEvent){
        TimerClass.canPopUp = false;
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
    public void extendSession(ActionEvent actionEvent){
        TimerClass.extend = true;
        TimerClass.updateTime();
        Stage secondaryStage = Client.Platform_store;
        SecondScene controller = ((FXMLLoader) secondaryStage.getScene().getUserData()).getController();
        controller.updateTime(TimerClass.sessionEndTime);
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
        System.out.println("session extended");
    }
}