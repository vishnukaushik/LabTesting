package FeedbackScene;

import SecondScene.SecondScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.sql.SQLException;

public class FeedbackScene {
    @FXML
    private javafx.scene.control.Button btn_cancel;
    @FXML
    private TextArea textArea;


    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    public void submit(ActionEvent actionEvent) throws SQLException {
        String feedback = textArea.getText();
        System.out.println(feedback);
        SecondScene.submitFeedback(feedback);
        cancel(actionEvent);
    }
}
