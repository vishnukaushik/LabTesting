package FeedbackScene;

import Credentials.DbCredentials;
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
//      TODO: write a query to database
        Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
        Statement stmt = con.createStatement();
        int rs1 = stmt.executeUpdate("UPDATE clients SET  status=0 WHERE id=" + "'"+ Main.IP+"'");
        cancel(actionEvent);
    }
}
