package pfsFileSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Arrays;

/**
 * The main program
 */

public class PfsFileSystem {

    private static long pfsFilePointerOffset = 0;        //used to indicate the position of the current block in pfs
    private static RandomAccessFile pfsFile = null;
    private static final int PFS_BLOCK_SIZE = 256;
    private static final int PFS_FILE_SIZE = 10000;
    private static final int RESERVED_BLOCKS = 3;
    private static final int EMPTY_BLOCK = -1;
    private static FsManager fsManager = null;


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
                //  open and create pfs file, if the user enters "open pfsfile",
                //  note, user must enter "open pfsfile"
                case "open":
                    try {
                        //  creates the pfs file of 10kb for the file system use
                        pfsFile = new RandomAccessFile("PFS", "rw");
                        //  create and store file manager info
                        fsManager = new FsManager();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    openPfsFile(fsManager, pfsFile);
                    break;

                //  end the program if the user enters exit
                case "put":

                    break;

                //  get the data from pfs and display in console
                case "get":

                    break;

                //  display all the files in pfs
                case "dir":

                    break;

                //  remove the file from pfs
                case "rm":

                    break;

                //  put the remark in the fcb
                case "putr":

                    break;

                //  end the program if the user enters exit
                case "exit":
                    displayExitMessage();
                    return;

                //  end the program if the user enters quit
                case "quit":
                    displayExitMessage();
                    return;

                //  notify the user of invalid inputs
                default:
                    System.out.println("Invalid Input. Please enter a valid option");
                    break;
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

    /*
       returns the pfs file pointer to zero
    */
    private static void resetFilePointer() {
        pfsFilePointerOffset = 0;
    }

    /**
     * Creates a pfs file of 10kb and writes the details of the
     * pfsManger to the file
     * @param pfs
     */
    private static void openPfsFile(FsManager fsManager, RandomAccessFile _pfs) {
        createPfsFile(_pfs);
        writeFsManagerInBytesToPfs(fsManager,_pfs);
    }

    /**
     * creates a random access pfs file of 10kb
     * @param pfsFile
     */
    private static void createPfsFile(RandomAccessFile pfsFile) {
        try {
            //  creates the pfs file of 10kb for the file system use
            pfsFile.setLength(PFS_FILE_SIZE);
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

    /**
     * writes the details of file manager to the pfs
     * @param fsManager
     * @param pfsFile
     */
    private static void writeFsManagerInBytesToPfs(FsManager fsManager, RandomAccessFile pfsFile) {
        // save the details of the manager in bytes
        byte[] fsManangerDataInBytes = fsManager.toByteData();

        /*//  test the location of the file pointer
        System.out.println("the byte info is @: " +  pfsFilePointerOffset + Arrays.toString(fsManangerDataInBytes));*/
        try {
            //  write the byte to the file and see
            resetFilePointer();
            pfsFile.write(fsManangerDataInBytes, 0, fsManangerDataInBytes.length);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
