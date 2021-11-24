
package shutdown;
import java.io.*;

public class Shutdown {
    public static void main(String[] args)
    {

    }

    public static void main() {
        Runtime runtime = Runtime.getRuntime();
        try
        {
            System.out.println("Shutting down the PC after 5 seconds.");
            runtime.exec("shutdown -s -t 75");
        }
        catch(IOException e)
        {
            System.out.println("Exception: " +e);
        }
    }
}
