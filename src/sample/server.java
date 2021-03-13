// A Java program for a Client
package sample;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Class.forName;

public class server {

    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor to put ip address and port
    public server(String address, int port) {
        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);
//
//            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }

        // string to read message from input
        String line = "login";

        // keep reading until "Over" is input

        while (!line.equals("ok")) {
            try {
                //line = input.readLine();
                System.out.println("Sent : " + line);
                out.writeUTF(line);
                line = in.readUTF();
                System.out.println("Received : " + line);
            } catch (IOException i) {
                System.out.println(i);
            }
        }

        // close the connection
        try {
            //input.close();
            out.close();
            in.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) throws IOException {

        /*try{
            forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from lab.clients where status=1");
            Vector<String> vec = new Vector<String>();
            if (rs.next())
            {

                do {
                    vec.add(rs.getString(2));
                    // System.out.println(rs.getInt(1)+"  "+rs.getString(2));
                }while(rs.next());
                System.out.println("Available Systems : "+vec);
                System.out.println("Allocating System : "+vec.get(0));
                server client = new server(vec.get(0), 5000);
            }
            else{
                System.out.println("Systems Not available....Please come after sometime....");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}*/


        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        do{

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            input.readLine();
            try {

                server client = new server("192.168.1.3", 5056);
                System.out.println("connection established");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }while(true);


    }
}

