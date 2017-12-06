package pfsFileSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
    private static final int BLOCK_ONE = 1;
    private static final int BLOCK_TWO = 2;
    private static Fcb[] fcb;
    private static int _currentFcb = 0;
    private static String _dataOneFilePathString = null;
    private static String _dataTwoFilePathString = null;


    public static void main(String args[]) {
        String rawUserInput;       //the input entered by the user
        String userInput[];        //holds the result of splitting the user input
        String command;            //indicates what command the user intends to execute
        String fileName;           //indicates the file which the user intends to manipulate

        //  position trackers for block1
        int _startBlockId_1;
        int _endBlockId_1;

        //  position trackers for block2
        int _startBlockId_2 = -1;
        int _endBlockId_2;

        //  test byte
        byte[] _testDataByteFromPfs = null;



        //  the file control blocks
        fcb = new Fcb[2];

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

                    //  get the file name from the user input
                    fileName = userInput[1];

                    //  get the path to the file
                    String filePathString = "/home/neo/IdeaProjects/PfsFileSystem/" + fileName;
                    _dataOneFilePathString = filePathString;
                    File writeFileToPfs = new File(filePathString);

                    //  check file existence and file fit
                    if (isFileExist(writeFileToPfs)
                            && isSpaceAvailable(fsManager, writeFileToPfs)) {

                        //  pick a particular fcb
                        if (fcb[0] == null) {

                            //  block tracker
                            _currentFcb = BLOCK_ONE;

                            //  number of blocks in file
                            int _numBlocksInFile = getNumBlocks((int) writeFileToPfs.length());

                            //create fcb[0]
                            String _fileName = writeFileToPfs.getName();
                            int _fileSize = (int) writeFileToPfs.length();
                            _startBlockId_1 = allocateStartBlockId(_currentFcb, _numBlocksInFile);
                            _endBlockId_1 = allocateEndBlockId(_currentFcb, _numBlocksInFile, _startBlockId_2);
                            String _createTime = getTime();
                            String _createDate = getDate();
                            String _remarks = "";

                            //  create fcb
                            fcb[0] = new Fcb(_fileName,
                                    _fileSize,
                                    _startBlockId_1,
                                    _endBlockId_1,
                                    _numBlocksInFile,
                                    _createTime,
                                    _createDate,
                                    _remarks);

                            //  dislay fcb
                            System.out.println(fcb[0].toString());

                            //  decrement the available space in the fsmanager
                            int _newAvailableBlockCount = (fsManager.getAvailableBlockCount() - fcb[0].getNumBlocks());
                            fsManager.setAvailableBlockCount(_newAvailableBlockCount);

                            //  write new info to fsManger
                            writeFsManagerInBytesToPfs(fsManager,pfsFile);

                            //  write fcb to pfs
                            writeFcbToPFs(_currentFcb, pfsFile, fcb[0]);

                            //  ------------------ write data to pfs -----------------
                            //  convert file to byte[]
                            byte[] _dataOneInByte = getDataInBytes(filePathString);

                            //  test byte form pfs
                            _testDataByteFromPfs = _dataOneInByte;

                            //  test the output of the bytes
                            System.out.println("the data is: " + Arrays.toString(_dataOneInByte));

                            //  write file to pfs
                            writeDataInBytesToPfs(pfsFile, fcb[0], _dataOneInByte);

                            //  ------------------ write data to fcb -----------------
                        } else if (fcb[1] == null) {

                            //select fcb 2
                            _currentFcb = BLOCK_TWO;

                            //  number of blocks in file
                            int _numBlocksInFile = getNumBlocks((int) writeFileToPfs.length());

                            //create fcb[0]
                            String _fileName = writeFileToPfs.getName();
                            int _fileSize = (int) writeFileToPfs.length();
                            _startBlockId_2 = allocateStartBlockId(_currentFcb, _numBlocksInFile);
                            _endBlockId_2 = allocateEndBlockId(_currentFcb, _numBlocksInFile, _startBlockId_2);
                            String _createTime = getTime();
                            String _createDate = getDate();
                            String _remarks = "";

                            //  create fcb
                            fcb[1] = new Fcb(_fileName,
                                    _fileSize,
                                    _startBlockId_2,
                                    _endBlockId_2,
                                    _numBlocksInFile,
                                    _createTime,
                                    _createDate,
                                    _remarks);

                            //  dislay fcb
                            System.out.println(fcb[1].toString());

                            //  decrement the available space in the fsmanager
                            int _newAvailableBlockCount = (fsManager.getAvailableBlockCount() - fcb[0].getNumBlocks());
                            fsManager.setAvailableBlockCount(_newAvailableBlockCount);

                            //  write new info to fsManger
                            writeFsManagerInBytesToPfs(fsManager,pfsFile);

                            //  write fcb to pfs
                            writeFcbToPFs(_currentFcb, pfsFile, fcb[1]);

                            //  ------------------ write data to pfs -----------------
                            //  convert file to byte[]
                            byte[] _dataTwoInByte = getDataInBytes(filePathString);

                            //  test the output of the bytes
                            System.out.println("the data is: " + Arrays.toString(_dataTwoInByte));

                            //  write file to pfs
                            writeDataInBytesToPfs(pfsFile, fcb[1], _dataTwoInByte);

                            //create fcb[1
                        } else {

                            System.out.println("Pfs is full");
                            break;
                        }

                        //  test
                        System.out.println("can write file to pfs");
                        //create pfs and write to file
                    }

                    break;

                //  get the data from pfs and display in console
                case "get":

                    //  get the file name from the user input
                    fileName = userInput[1];

                    //  get the path to the file
                    filePathString = "/home/neo/IdeaProjects/PfsFileSystem/" + fileName;
                    writeFileToPfs = new File(filePathString);

                    //  check if the file exist
                    if (isFileExist(writeFileToPfs)) {

                        //  check if the file is in fcb[0]
                        if (writeFileToPfs.getName() == fcb[0].getFileName()) {

                            for (int i = 0; i < _testDataByteFromPfs.length; i++) {


                            }

                        } else {

                        }
                    } else {
                        System.out.println("File doesn't Exist");
                    }


                    break;

                //  display all the files in pfs
                case "dir":
                    displayDirectory();
                    break;

                //  remove the file from pfs
                case "rm":

                    break;

                //  put the remark in the fcb
                case "putr":

                    break;

                    // delete the pfs file
                case "kill":
                    killPfsFileSystem();
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

    private static void writeDataInBytesToPfs(RandomAccessFile pfsFile,
                                              Fcb fcb,
                                              byte[] dataOneInByte) {
        resetFilePointer();

        //movePointerTo block
        try {
            pfsFile.seek((long) fcb.getStartBlockId() * PFS_BLOCK_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  write data to pfs
        try {
            pfsFile.write(dataOneInByte);
        } catch (IOException e) {
            e.printStackTrace();
        }

        resetFilePointer();
    }

    /**
     * converts a file to byte[]
     * @param writeFileToPfs
     * @return
     */
    private static byte[] getDataInBytes(String filePath) {
        /*
        byte[] bFile = Files.readAllBytes(new File(filePath).toPath());
//or this
        byte[] bFile = Files.readAllBytes(Paths.get(filePath));
        */

        Path path = Paths.get(filePath);
        byte[] data = null;
        try {
            data = Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    /*
        writes the value of the fcb to the pfs file
     */
    private static void writeFcbToPFs(int _currentFcb, RandomAccessFile pfsFile, Fcb fcb) {
        resetFilePointer();
        //  insert an if statement here for block one or two
        int _blockNum = 0;
        if (_currentFcb == BLOCK_ONE) {
             _blockNum = BLOCK_ONE;
        } else {
            _blockNum = BLOCK_TWO;
        }
        try {
            pfsFile.seek((long) _blockNum * PFS_BLOCK_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeDataToPfsBlock(fcb);
        resetFilePointer();
    }

    /**
     * writes the data to the pfs file starting from the pointer
     * specified by the user
     * @param fcb
     */
    private static void writeDataToPfsBlock(Fcb fcb) {
        try {
            // 3 write fcb to file
            pfsFile.write(fcb.toByteData());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * moves the pointer to the fcb position
     * @param pfsFile
     */
    private static void movePointerToFcbBlock(RandomAccessFile pfsFile) {
        try {
            //  set the pointer to the fcb block
            pfsFile.seek((long) fcb[0].getStartBlockId() * PFS_BLOCK_SIZE);

        } catch(IOException ex) {
            ex.printStackTrace();
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
        //  reset the file pointer
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

    /**
     * checks it the file exist or not
     * @param File object
     * @return
     */
    private static boolean isFileExist(File writeFileToPfs) {
        if (writeFileToPfs.exists()) {
            //  test code
            System.out.println("File exist");
            return true;
        } else {
            System.out.println("File dose not exist");
            return false;
        }
    }

    /**
     * checks if the file is larger than pfs or not
     * @param fsManager
     * @param writeFileToPfs
     * @return
     */
    private static boolean isSpaceAvailable(FsManager fsManager, File writeFileToPfs) {
        return  writeFileToPfs.length() < (fsManager.availableBlockCount * PFS_BLOCK_SIZE);
    }


    /**
     * allocates a start block id to the caller
     * @param _currentBlock
     * @param _numBlocks
     * @return
     */
    private static int allocateStartBlockId(int _currentBlock, int _numBlocks) {
        if (_currentBlock == BLOCK_ONE) {
            return 3;
        } else if (_currentBlock == BLOCK_TWO) {
            return (_numBlocks) + 1;
        } else {
            return -1;
        }
    }

    /**
     * allocates a start block id to the caller
     * @param _currentBlock
     * @param _numBlocks
     * @return
     */
    private static int allocateEndBlockId(int _currentBlock,
                                          int _numBlocks,
                                          int _startBlockid_2) {
        if (_currentBlock == BLOCK_ONE) {
            return 3 + _numBlocks;
        } else if (_currentBlock == BLOCK_TWO) {
                return _startBlockid_2 + _numBlocks;
        } else {
            return -1;
        }
    }

    /**
     * returns the number of blocks required to save a specific file
     * the value is == (fileSize // PFS_BLoCK_SIZE
     * @param fileSize
     * @return
     */
    private static int getNumBlocks(int fileSize) {
        int _blockSize = 0;
        if (fileSize < 1) {
            return 1;
        } else {
            _blockSize = (fileSize) / PFS_BLOCK_SIZE;
            return ++_blockSize;
        }
    }

    /**
     * returns the current time
     * @return
     */
    private static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("h:MMaa");
        String currentTime = sdf.format(cal.getTime());
        return currentTime;
    }

    /**
     * returns the current date
     * @return
     */
    private static String getDate() {
        Format formatter = new SimpleDateFormat("EEE dd MMMM YYYY");
        String s = formatter.format(new Date());
        return s;
    }

    /**
     * deletes the entire pfs file system
     */
    private static void killPfsFileSystem() {
        try{
            File file = new File("PFS");
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation failed - Pfs file does not exist.");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void displayDirectory() {
        //  if fcb one and two are null display empty fcb
        //  check if fcb one is not null and call display file
        //  check if fcb two is not null and display file

        if (fcb[0] == null && fcb[1] == null) {
            System.out.println("--------- ------ ------ ------");
        }

        if(fcb[0] != null) {
            //  display file's info on fcb
            System.out.println(fcb[0].toDir());
        }

        if (fcb[1] != null) {
            //  display file's info on fcb
            System.out.println(fcb[1].toDir());
        }

    }

}
