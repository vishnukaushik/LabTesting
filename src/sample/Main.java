package sample;

import javafx.application.Application;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        String username = "root",password="root", url = "jdbc:mysql://localhost:3306/Lab";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful");
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed!!!");

        }
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
                        // line = input.readLine();
                        //  out.writeUTF(line);

                        line = in.readUTF();
                        System.out.println("Received : "+line);
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
                input.close();
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
