package pfsFileSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * The main program
 */

public class PfsFileSystem {
    public static void main(String args[]) {

    //  make terminal logic
        while (true) {

        }
    }

    /**
     * returns the user input as a string
     * @return
     */
    private static String getUserInput() {

        String inputLine = null;

        try{
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
            if(inputLine.length() == 0) {
                return null;
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return inputLine;
    }

    
    /**
     * displays the terminal prompt
     */
    private static void displayTerminalPrompt() {
        System.out.print("pfs> ");
    }

}
