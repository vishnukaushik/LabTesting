package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.awt.*;

public class SecondScene {
    BorderPane bp = new BorderPane();
    public SecondScene() {

        bp.setPadding(new Insets(100,100,100,100));

        HBox time_box = new HBox();
        time_box.setAlignment(Pos.BASELINE_CENTER);
        time_box.setPadding(new Insets(50));
//        TODO add time
        Text time = new Text();
        time.setText("Get the time and print it here!!!");
        time_box.getChildren().add(time);

        bp.setCenter(time_box);
    }
}
