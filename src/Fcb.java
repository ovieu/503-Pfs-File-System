/**
 * The file control block represents data
 * stored in the file system
 */

public class Fcb {
    /**
     * Constructor
     * @param fileName
     * @param fileSize
     * @param startBlockId
     * @param endBlockId
     * @param numBlocks
     * @param remarks
     */
    public Fcb(String fileName,
               int fileSize,
               int startBlockId,
               int endBlockId,
               int numBlocks,
               String remarks) {

        this.fileName = fileName;
        this.fileSize = fileSize;
        this.startBlockId = startBlockId;
        this.endBlockId = endBlockId;
        this.numBlocks = numBlocks;
        this.remarks = remarks;
    }

    /**
     * public getter and setter methods
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getStartBlockId() {
        return startBlockId;
    }

    public void setStartBlockId(int startBlockId) {
        this.startBlockId = startBlockId;
    }

    public int getEndBlockId() {
        return endBlockId;
    }

    public void setEndBlockId(int endBlockId) {
        this.endBlockId = endBlockId;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    public void setNumBlocks(int numBlocks) {
        this.numBlocks = numBlocks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * member varibales
     *
     */
    private String fileName;
    private int fileSize;
    private int startBlockId;
    private int endBlockId;
    private int numBlocks;
    private String remarks;
}
