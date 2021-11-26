package org.example.FeedbackScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.SecondScene.SecondScene;
import org.example.sample.MainClass;

import java.io.IOException;
import java.sql.SQLException;

public class FeedbackScene {
    @FXML
    private javafx.scene.control.Button btn_cancel;
    @FXML
    private TextArea textArea;


    public void cancel(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
        MainClass.restartProcess();
    }

    public void submit(ActionEvent actionEvent) throws SQLException, IOException, InterruptedException {
        String feedback = textArea.getText();
        System.out.println(feedback);
        SecondScene.submitFeedback(feedback);
        cancel(actionEvent);
    }
}
