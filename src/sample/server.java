// A Java program for a Client
package sample;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class server
{

    // initialize socket and input output streams
    private Socket socket		 = null;
    private DataInputStream input = null;
    private DataOutputStream out	 = null;

    private ServerSocket server = null;
    private DataInputStream in	 = null;

    // constructor to put ip address and port
    public server(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input = new DataInputStream(System.in);
//
//            // sends output to the socket
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
        }
        catch(UnknownHostException u)
        {
            u.getStackTrace();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "login";

        // keep reading until "Over" is input

        while (!line.equals("ok"))
        {
            try
            {
                //line = input.readLine();
                System.out.println("Sent : "+line);
                out.writeUTF(line);
                line = in.readUTF();
                System.out.println("Received : "+line);
            }
            catch(IOException i)            {
                System.out.println(i);
            }
        }

        // close the connection
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

    /*public static void main(String args[])
    {

        try{
            Scanner sc= new Scanner(System.in); //System.in is a standard input stream
            System.out.print("Enter Your Roll Number : ");
            String roll= sc.nextLine();              //reads string
            System.out.print("Enter Your Name : ");
            String name= sc.nextLine();              //reads string

            //System.out.print("You have entered: "+roll);
            forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/lab","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from lab.clients where status=1");
            Vector<String> vec = new Vector<String>();
            Vector<String> ids = new Vector<String>();
            if (rs.next())
            {

                do {
                    vec.add(rs.getString(2));
                    ids.add(rs.getString(1));
                    // System.out.println(rs.getInt(1)+"  "+rs.getString(2));
                }while(rs.next());
                System.out.println("Available Systems : "+vec);
                System.out.println("Allocating System : "+vec.get(0));
                server client = new server(vec.get(0), 5000);

                int rs1=stmt.executeUpdate("INSERT INTO `logs`(roll_no,name,sys_allocated) VALUES ('"+roll+"','"+name+"','"+ids.get(0)+"')");
                System.out.println("Successfully allocated System "+ids.get(0)+" to user "+roll);

            }
            else{
                System.out.println("Systems Not available....Please come after sometime....");
            }
            con.close();
        }catch(Exception e){ System.out.println(e);}



    }*/

    public static void main(String[] args) {
        server client = new server("192.168.43.78", 5000);
    }
}