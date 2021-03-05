package sample;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main extends Application {

    static boolean bypass = false;

    @Override
    public void start(Stage primaryStage)
            throws Exception{
        Kiosk.blockKeys();
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root));
        //Kiosk.kiosk(primaryStage);
        primaryStage.show();
        Platform.runLater(()->{
            if(bypass) {
                primaryStage.close();
                try {
                    Parent root1 = FXMLLoader.load(getClass().getResource("secondScene.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setTitle("Session Info");
                primaryStage.setScene(new Scene(root));
            }
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

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                new Client(5000);
            }
        });
        t2.start();
        System.out.println("After t2.start");

        /*
        Platform.runLater(()->{
            String name = "vishnu";
            if(name.equals("vishnu"))
            {
                Stage primaryStage = new Stage();
//                bypass = true;
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
        });*/
    }
}

//Client class

class Client extends Thread{
    //initialize socket and input stream
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out	 = null;
    private ServerSocket server = null;
    private DataInputStream in	 = null;

    public  static String line="";
    // constructor with port
    public Client(int port)
    {
        if (port!=0)
        {

            // starts server and waits for a connection
            try
            {
                server = new ServerSocket(port);
                System.out.println("main.java.Client started");

                System.out.println("Waiting for server to respond ...");

                socket = server.accept();
                System.out.println("Server accepted");

                // takes input from the client socket
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

                input = new DataInputStream(System.in);

                // sends output to the socket
                out = new DataOutputStream(socket.getOutputStream());

//                String line = "";

                // reads message from client until "Over" is sent
                while (!line.equals("ok"))
                {
                    try
                    {
                        // line = input.readLine();
                        //  out.writeUTF(line);

                        line = in.readUTF();
                        System.out.println("Received : "+line);
                        Main.bypass=true;
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
                System.out.println("Inside try closing");
                input.close();
                out.close();
                in.close();
                socket.close();
            }
            catch(IOException i)
            {
                System.out.println("Inside catch");
                System.out.println(i);
            }
        }
    }

}
