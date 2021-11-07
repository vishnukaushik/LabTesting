package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class FeedbackScene {
    @FXML
    private javafx.scene.control.Button btn_cancel;

    public void cancel(ActionEvent actionEvent){
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    public void submit(ActionEvent actionEvent)
    {
//        TODO: write a query to database
    }
}
