package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    static boolean bypass = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Kiosk.blockKeys();
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root));
        Kiosk.kiosk(primaryStage);
        primaryStage.show();
        Platform.runLater(()->{
            if(bypass)
                primaryStage.close();
        });
    }




    public static void main(String[] args) {
//        TODO connect to database here
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Application.launch(Main.class, args);
            }
        });
        t1.start();

        Platform.runLater(()->{
            String name = "vishnus";
            if(name.equals("vishnu"))
            {
                Stage primaryStage = new Stage();
                bypass = true;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(SecondScene.class.getResource("secondScene.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SecondScene controller = loader.getController();
//            TODO add name and roll no. from server to below Student object
                controller.setStudent(new Student("Vishnu","059"));
                controller.setTime();
                primaryStage.setScene(new Scene(root,600,500));
                primaryStage.show();
            }
        });
    }
}
