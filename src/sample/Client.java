package sample;

import Credentials.DbCredentials;
import SecondScene.SecondScene;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Client extends Thread implements DbCredentials {
    //initialize socket and input stream
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    public static Stage Platform_store;
    // constructor with port

    public Client(int port) {
        if (port != 0) {

            // starts server and waits for a connection
            try {
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
                Vector<String> data = new Vector<String>();
                // reads message from client until "Over" is sent
                while (!line.equals("ok")) {
                    try {

                        line = in.readUTF();
                        data.add(line);
                        System.out.println("Received : " + line);
                        //line="next";
                        if (line.equals("login")) {
                            line = "ok";
                            out.writeUTF(line);
                            //line = in.readUTF();
                            Platform.runLater(() -> {

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

                                controller.setStudent(new Student(data.get(1), data.get(0)));
                                try {
                                    controller.setTime();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                controller.openThread();
                                primaryStage.setScene(new Scene(root, 600, 500));
                                primaryStage.show();
                                System.out.println("i am in a fx thread");
                                Platform_store = primaryStage;
                                Main.storage.close();
                                Kiosk.unblockKey();

                            });
                            Connection con = DriverManager.getConnection(DbCredentials.url, DbCredentials.user, DbCredentials.password);
                            Statement stmt = con.createStatement();
                            int rs1 = stmt.executeUpdate("UPDATE clients SET  status=0 WHERE id=" + "'"+Main.IP+"'");//and check_out=NULL
                            System.out.println("Status set to 0");
                        } else {
                            line = "next";
                            out.writeUTF(line);
                        }


                    } catch (IOException i) {
                        System.out.println(i);
                    } catch (SQLException t) {
                        t.printStackTrace();
                    }
                }

            } catch (IOException i) {
                System.out.println(i);
            }

            try {
                //input.close();
                out.close();
                in.close();
                socket.close();
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    public void run() {
        System.out.println("Waiting for the server in separate thread.......");
        Client obj = new Client(3160);
    }
}
