package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.sql.*;
import java.util.*;
import static java.lang.Class.forName;


public class Main extends Application {

    public static Stage storage = null;
    public static String[] arguments;
    public static Thread client_Thread;
    static volatile boolean exit=false;
    public static boolean restart=false;
    public static int port_server;
    public static int counter=0;

    public static void StartClient(int port_no){
        client_Thread= new Thread(new Runnable() {
            @Override
            public void run() {
                new client(port_no);
                System.out.println(port_no);
            }
        });
        client_Thread.start();
    }

    public static void StartApplication(Stage primaryStage) throws IOException {
        Kiosk.blockKeys();
        Parent root = FXMLLoader.load(FirstScene.class.getResource("firstScene.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root));
        //Kiosk.kiosk(primaryStage);
        primaryStage.show();
        storage= primaryStage;
    }

    public static void cleanup(){

        client_Thread.interrupt();
        Platform.exit();
    }
    public static int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public static void restartProcess() throws IOException {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", "cd \"C:\\Users\\P.VISHNU VARDHAN\\IdeaProjects\\LabTesting-1\" && Script.cmd");
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
            if (line == null) { break; }
            System.out.println(line);
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
       StartApplication(primaryStage);
    }

    public static void main(String[] args) throws SQLException {
//        TODO connect to database here

            int port_no= getRandomNumberUsingNextInt(5000,5100);
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root","root");
            Statement stmt = con.createStatement();
            int rs1=stmt.executeUpdate("UPDATE clients SET  port= "+port_no+" WHERE id='1'");
//UPDATE `clients` SET `port`=5001 WHERE id='1'
       // int rs1=stmt.executeUpdate("UPDATE clients  INTO `logs`(roll_no,name,sys_allocated) VALUES ('"+roll+"','"+name+"','"+ids.get(0)+"')");
            System.out.println("Successfully updated port number to database....");

        port_server=port_no;
            counter++;
            System.out.println(counter);
            StartClient(port_no);
            launch(args);

    }
}
class client extends Thread{
    //initialize socket and input stream
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out	 = null;
    private ServerSocket server = null;
    private DataInputStream in	 = null;
    public static Stage Platform_store;
    // constructor with port

    public client(int port)
    {
        if (port!=0)
        {

            // starts server and waits for a connection
            try
            {
                server = new ServerSocket(port);
                System.out.println("main.java.Client started");
                System.out.println(Main.port_server);
                System.out.println("Waiting for server to respond ...");


                socket = server.accept();
                System.out.println("Server accepted");

                // takes input from the client socket
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                input = new DataInputStream(System.in);

                // sends output to the socket
                out = new DataOutputStream(socket.getOutputStream());

                String line = "";

                // reads message from client until "Over" is sent
                while (!line.equals("ok"))
                {
                    try
                    {

                        line = in.readUTF();
                        System.out.println("Received : "+line);
                        if(line.equals("login")){
                            Platform.runLater(()->{
                                    Stage primaryStage = new Stage();
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(SecondScene.class.getResource("secondScene.fxml"));
                                    Parent root = null;
                                    try {
                                        root = loader.load();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    SecondScene controller = loader.getController();

                                    controller.setStudent(new Student("Vishnu","059"));
                                    controller.setTime();
                                    primaryStage.setScene(new Scene(root,600,500));
                                    primaryStage.show();
                                    System.out.println("i am in a fx thread");
                                    Platform_store=primaryStage;
                                    Main.storage.close();
                                    Kiosk.unblockKey();

                            });

                        }
                        line="ok";
                        out.writeUTF(line);

                    }
                    catch(IOException i)
                    {
                        System.out.println(i);
                    }
                }

            }
            catch(IOException i)
            {
                System.out.println(i);
            }

            try
            {
                //input.close();
                out.close();
                in.close();
                socket.close();
            }
            catch(IOException i)
            {
                System.out.println(i);
            }
        }
    }

    public void run(){
        System.out.println("Waiting for the server in separate thread.......");
        client obj = new client(5000);
    }
}
