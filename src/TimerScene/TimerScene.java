package TimerScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;


public class TimerScene{
    @FXML private javafx.scene.control.Button btn_cancel;

    public void cancel(ActionEvent actionEvent){
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
    public void extendSession(ActionEvent actionEvent){
//        TODO(manage the time)
        System.out.println("session extended");
    }
}