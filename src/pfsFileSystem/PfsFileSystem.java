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
        String rawUserInput;           //the input entered by the user
        String userInput[];        //holds the result of splitting the user input
        String command;             //indicates what command the user intends to execute

        //  make terminal logic
        while (true) {

            displayTerminalPrompt();
            rawUserInput = getUserInput();

            //  split the user input by whitespace
            userInput = rawUserInput.split(" ");
            command = userInput[0];

            //  perform pfs operations based on user entry
            switch (command) {

                //  end the program if the user enters exit
                case "exit":
                    displayExitMessage();
                    return;

                //  end the program if the user enters quit
                case "quit":
                    displayExitMessage();
                    return;
            }

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

    /**
     * display message
     */
    private static void displayExitMessage() {
        System.out.println();
        System.out.println("Exiting PFS....");
    }

}
