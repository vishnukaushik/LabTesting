
package org.example.shutdown;

import java.io.*;

public class Shutdown {


    public static void main() {
        Runtime runtime = Runtime.getRuntime();
        try {
            System.out.println("Shutting down the PC after 5 seconds.");
            runtime.exec("org.example.shutdown -s -t 5");
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }
}
