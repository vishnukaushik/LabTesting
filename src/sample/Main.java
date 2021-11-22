package sample;

import Credentials.DbCredentials;
import FirstScene.FirstScene;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;
import java.sql.*;
import java.util.*;

import static java.lang.Class.forName;


public class Main extends Application implements DbCredentials {

    public static Stage storage = null;
    public static String[] arguments;
    public static Thread client_Thread;
    static volatile boolean exit = false;
    public static boolean restart = false;
    public static int port_server;
    public static int counter = 0;
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(FirstScene.class.getResource("../FirstScene/firstScene.fxml")));
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
                "cmd.exe", "/c", "cd \"C:\\LabAuthentication\" && Script.cmd");
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

    public static void main(String[] args) throws SQLException, UnknownHostException {
        //make status=1 here as well
        InetAddress localhost = InetAddress.getLocalHost();
        IP = (localhost.getHostAddress()).trim();
        System.out.println(IP);
//       TODO uncomment the below line  ...  int port_no = getRandomNumberUsingNextInt(5000, 5100);
        int port_no = 5019;
        Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
        Statement stmt = con.createStatement();
        int rs1 = stmt.executeUpdate("UPDATE clients SET  port= " + port_no + " WHERE id=" + "'" + IP + "'");
        int rs2 = stmt.executeUpdate("UPDATE clients SET  status=1 WHERE id=" + "'" + IP + "'");

//UPDATE `clients` SET `port`=5001 WHERE id='1'
        // int rs2=stmt.executeUpdate("INSERT INTO `logs`(roll_no,name,sys_allocated) VALUES ('"+roll+"','"+name+"','"+ids.get(0)+"')");
        System.out.println("Successfully updated port and status to database....");

        port_server = port_no;
        counter++;
        System.out.println(counter);
        StartClient(port_no);
        launch(args);

    }
}
