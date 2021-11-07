package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FeedbackScene {
    @FXML
    private javafx.scene.control.Button btn_cancel;
    @FXML
    private TextArea textArea;


    public void cancel(ActionEvent actionEvent){
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    public void submit(ActionEvent actionEvent)
    {
        String feedback = textArea.getText();
        System.out.println(feedback);
//        TODO: write a query to database

        cancel(actionEvent);
    }
}
