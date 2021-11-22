package sample;// A Java program for a Client

//package main.java;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import static java.lang.Class.forName;

public class server {
    //public static final Scanner sc = new Scanner(System.in);

    // initialize socket and input output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    private ServerSocket server = null;
    private DataInputStream in = null;

    // constructor to put ip address and port
    public server(String address, String port, String roll, String name) throws IOException {
        String[] str = new String[]{roll, name, "login", "login"};
        int j = 0;
        // establish a connection
        try {
            socket = new Socket(address, Integer.parseInt(port));
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
        String line = "";

        // keep reading until "Over" is input

        while (!line.equals("ok")) {
            try {
                line = str[j];
                j++;
                //line = input.readLine();
                System.out.println("Sent : " + line);
                out.writeUTF(line);
                line = in.readUTF();
                System.out.println("Received : " + line);

            } catch (IOException i) {
                System.out.println(i);
            }
        }
        //out.writeUTF(line);
        // close the connection
        try {
//            input.close();
            out.close();
            in.close();
            socket.close();
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    public static void main(String args[]) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);

            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

//            System.out.print("\033[H\033[2J");
//            System.out.flush();
            System.out.println("Server Initialized.............");
            // Scanner sc= new Scanner(System.in);

            try {
                //System.in is a standard input stream
                System.out.print("Enter Your Roll Number : ");
                //long then = System.currentTimeMillis();
                String roll = sc.nextLine();
                //  long now = System.currentTimeMillis();//reads string
                System.out.print("Enter Your Name : ");
                String name = sc.nextLine();              //reads string
                //sc.close();
                //System.out.print("You have entered: "+roll);
                forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", "root", "Lab@Authentication123");
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("select * from lab.clients where status=1");
                Vector<String> vec = new Vector<String>();
                Vector<String> ids = new Vector<String>();
                Vector<String> ports = new Vector<String>();

                if (rs.next()) {

                    do {
                        vec.add(rs.getString(2));
                        ids.add(rs.getString(1));
                        ports.add(rs.getString(3));
                        // System.out.println(rs.getInt(1)+"  "+rs.getString(2));
                    } while (rs.next());
                    String tempIP = "172.16.6.202"; // TODO delete tempIP;
                    System.out.println("Available Systems : " + vec);
                    System.out.println("Allocating System : " + tempIP); // TODO change tempIP to vec.get(0);
                    server client = new server(tempIP, "5019", roll, name); // TODO change tempIP to vec.get(0), ports.get(0) instead of 5000;
                    // TODO update below statement to ids.get(0) instead of 22
                    int rs1=stmt.executeUpdate("INSERT INTO `logs`(roll_no,name,sys_allocated) VALUES ("+"'" + roll+"'"+","+ "'" + name+"'" +"," + "'" + tempIP+"'"+")");
                    int rs2=stmt.executeUpdate("UPDATE clients SET  status=0 WHERE IP="+"'"+ tempIP+"'");
                    System.out.println("Successfully allocated System " + ids.get(0) + " to user " + roll);
                    System.out.println("Client ip address: "+tempIP);
                    System.out.println("INSERT INTO `logs`(roll_no,name,sys_allocated) VALUES ("+("'" + roll+"'")+","+ ("'" + name+"'") +"," + ("'" + tempIP+"'")+")");
                } else {
                    System.out.println("Systems Not available....Please come after sometime....");
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

    }
}
