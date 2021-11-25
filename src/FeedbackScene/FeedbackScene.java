package FeedbackScene;

import Credentials.DbCredentials;
import SecondScene.SecondScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;

import static sample.Main.IP;

public class FeedbackScene {
    @FXML
    private javafx.scene.control.Button btn_cancel;
    @FXML
    private TextArea textArea;


    public void cancel(ActionEvent actionEvent){
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
