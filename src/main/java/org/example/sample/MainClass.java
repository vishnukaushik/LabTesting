package org.example.sample;

import org.example.Credentials.DbCredentials;
import org.example.FeedbackScene.FeedbackScene;
import org.example.SecondScene.SecondScene;
import org.example.TimerScene.LoadTimerScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Random;


public class MainClass extends Application implements DbCredentials {

    public static Stage storage = null;
    public static String[] arguments;
    public static Thread client_Thread;
    static volatile boolean exit = false;
    public static boolean restart = false;
    public static int port_server;
    public static String IP;

    public static void StartClient(int port_no) {
        client_Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                new Client(port_no);
                System.out.println(port_no);
            }
        });
        client_Thread.start();
    }

    public static void StartApplication(Stage primaryStage) throws IOException {
//        Kiosk.blockKeys();
        Parent root = FXMLLoader.load(MainClass.class.getClassLoader().getResource("firstScene.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root));
        //Kiosk.kiosk(primaryStage);
        primaryStage.show();
        storage = primaryStage;
    }

    public static void cleanup() {

        client_Thread.interrupt();
        Platform.exit();
    }

    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static void restartProcess() throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
//                C:\\Users\\admin\\IdeaProjects\\LabAuthentication\\LabTesting\
                "cmd.exe", "/c", "cd \"C:\\Users\\admin\\IdeaProjects\\LabAuthentication\\LabTesting\\ && Script.cmd");
        //builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while (true) {
            try {
                line = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null) {
                break;
            }
            System.out.println(line);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        StartApplication(primaryStage);
    }

    public static void main(String[] args) throws SQLException, UnknownHostException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //make status=1 here as well
        InetAddress localhost = InetAddress.getLocalHost();
        IP = (localhost.getHostAddress()).trim();
        System.out.println(IP);
//       TODO uncomment the below line  ...  int port_no = getRandomNumberUsingNextInt(5000, 5100);
        int port_no = 5019;
//        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
        Statement stmt = con.createStatement();
        int rs1 = stmt.executeUpdate("UPDATE clients SET  port= " + port_no + " WHERE IP=" + "'" + IP + "'");
        int rs2 = stmt.executeUpdate("UPDATE clients SET  status=1 WHERE IP=" + "'" + IP + "'");
        System.out.println("Successfully updated port and status to database....");

        port_server = port_no;
        StartClient(port_no);
        launch(args);

    }

    public static void logout() throws SQLException, IOException, InterruptedException {
        SecondScene.exit_status = true;
        if (LoadTimerScene.secondaryStage != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    LoadTimerScene.secondaryStage.close();
                }
            });
        }
        System.out.println("logout invoked");
        Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
        Statement stmt = con.createStatement();
        int rs1 = stmt.executeUpdate("UPDATE logs SET  check_out=NOW() WHERE check_out is NULL && sys_allocated=" + "'" + MainClass.IP + "'");//and check_out=NULL
        int rs2 = stmt.executeUpdate("UPDATE clients SET  status=1 WHERE IP=" + "'" + MainClass.IP + "'");
        System.out.println("Check_out and status updated");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                Client.Platform_store.close();
                Parent root = null;
                try {
                    root = FXMLLoader.load(Objects.requireNonNull(FeedbackScene.class.getResource("/feedbackScene.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage feedbackStage = Client.Platform_store;
                assert root != null;
                feedbackStage.setScene(new Scene(root));
                System.out.println("Feedback opened");
                feedbackStage.show();
            }
        });

//        Main.cleanup();
        MainClass.restartProcess();
    }

}
