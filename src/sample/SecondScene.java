package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class SecondScene {
    BorderPane bp = new BorderPane();
    Label name = new Label();
    Label roll = new Label();
    public SecondScene(String name, String roll) {

        this.name.setFont(new Font("Arial",20));
        this.roll.setFont(new Font("Arial",20));

        bp.setPadding(new Insets(100,100,100,100));

        HBox heading_box = new HBox();
        heading_box.setAlignment(Pos.BASELINE_CENTER);
        Text heading = new Text("Authentication Successful");
        heading.setFont(new Font("Arial",20));
        heading_box.getChildren().add(heading);

        this.name.setText(name);
        this.roll.setText(roll);


        LocalTime present_time = LocalTime.now().plusHours(25);
        DateTimeFormatter Format = DateTimeFormatter.ofPattern("hh:mm a");
        String initial_text = "The session ends at: ";
        Label time = new Label(initial_text+present_time.format(Format));
        time.setFont(new Font("Arial",20));

        GridPane main_gp = new GridPane();
        main_gp.setPadding(new Insets(100));
        main_gp.setAlignment(Pos.BASELINE_CENTER);
        main_gp.add(this.name,0,0);
        main_gp.add(this.roll,0,1);

        bp.setTop(heading_box);
        bp.setCenter(main_gp);
        bp.setBottom(time);
        BorderPane.setAlignment(time,Pos.BASELINE_CENTER);
    }
}
