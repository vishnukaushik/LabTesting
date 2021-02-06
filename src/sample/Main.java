package sample;
//tempgit status
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

                System.out.println("Closing connection");
                Stage primaryStage= new Stage();

                BorderPane bp = new BorderPane();
                bp.setPadding(new Insets(100,100,100,100));

                SecondScene test = new SecondScene("Vishnu", "1602-18-735-059");
                Scene scene = new Scene(bp);
                primaryStage.setScene(scene);
                primaryStage.show();
                // close connection
                // socket.close();
                // in.close();
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
//
//class Simple2 extends Thread{
//    public void run(){
//        System.out.println("task two");
//    }
//}


public class Main extends Application {

    String username = "admin";
    String password = "admin";
    String checkUser, checkPw;

    public static void main(String[] args) {

        //t2.start();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        client t1=new client(0);
        //Simple2 t2=new Simple2();

        t1.start();
        primaryStage.setTitle("Login");


        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(100,100,100,100));


        HBox headingHB = new HBox();
        headingHB.setAlignment(Pos.BASELINE_CENTER);
        headingHB.setPadding(new Insets(50,50,10,50));

        Text text = new Text("LAB AUTHENTICATION LOGIN");
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 36));

        headingHB.getChildren().add(text);



        Label lblUserName = new Label("Username: ");
        lblUserName.setFont(new Font("Arial", 20));
        final TextField txtUserName = new TextField();
        txtUserName.setFont(new Font("Arial",20));
        Label lblPassword = new Label("Password: ");
        lblPassword.setFont(new Font("Arial", 20));
        final PasswordField pf = new PasswordField();
        pf.setFont(new Font("Arial",20));
        Button btnLogin = new Button("Login");
        btnLogin.setFont(new Font("Arial",14));
        final Label lblMessage = new Label();
        lblMessage.setFont(new Font("Arial",14));

        //Adding GridPane
        GridPane topGridPane = new GridPane();
        topGridPane.setAlignment(Pos.BASELINE_CENTER);
        topGridPane.setPadding(new Insets(50,50,20,50));
        topGridPane.setHgap(10);
        topGridPane.setVgap(30);
        topGridPane.add(lblUserName, 0, 0);
        topGridPane.add(txtUserName, 1, 0);
        topGridPane.add(lblPassword, 0, 1);
        topGridPane.add(pf, 1, 1);

        HBox bottomHB1 = new HBox();
        bottomHB1.setAlignment(Pos.BASELINE_CENTER);
        bottomHB1.setPadding(new Insets(20,50,10,50));
        bottomHB1.getChildren().add(btnLogin);

        HBox bottomHB2 = new HBox();
        bottomHB2.setAlignment(Pos.BASELINE_CENTER);
        bottomHB2.setPadding(new Insets(10,50,50,50));
        bottomHB2.getChildren().add(lblMessage);

        GridPane bottomGridPane = new GridPane();
        bottomGridPane.setAlignment(Pos.BASELINE_CENTER);
        bottomGridPane.setPadding(new Insets(10,50,50,50));
        bottomGridPane.setHgap(20);
        bottomGridPane.setVgap(5);
        bottomGridPane.add(bottomHB1, 1, 0);
        bottomGridPane.add(bottomHB2, 1, 1);


        bp.setTop(headingHB);
        bp.setCenter(topGridPane);
        bp.setBottom(bottomGridPane);
        BorderPane.setMargin(topGridPane,new Insets(20));


        bp.setId("bp");
        topGridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");


        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                checkUser = txtUserName.getText().toString();
                checkPw = pf.getText().toString();
                if(checkUser.equals(username) && checkPw.equals(password)){
                    lblMessage.setText("      Login Successful!!!");
                    lblMessage.setTextFill(Color.GREEN);

                    SecondScene test = new SecondScene("Vishnu", "1602-18-735-059");
                    primaryStage.setTitle("Session Info");
                    primaryStage.setScene(new Scene(test.bp));
//                    primaryStage.close();
                }
                else{
                    lblMessage.setText("Incorrect Username or Password.");
                    lblMessage.setTextFill(Color.RED);
                }
                txtUserName.setText("");
                pf.setText("");
            }
        });


        Scene scene = new Scene(bp);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}