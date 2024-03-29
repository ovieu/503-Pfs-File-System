package pfsFileSystem;

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
               String createTime,
               String createDate,
               String remarks) {

        this.fileName = fileName;
        this.fileSize = fileSize;
        this.startBlockId = startBlockId;
        this.endBlockId = endBlockId;
        this.numBlocks = numBlocks;
        this.createTime = createTime;
        this.createDate = createDate;
        this.remarks = remarks;
    }

    /**
     * dislpays the details of the fcb
     * @return
     */
    @Override
    public String toString() {
        return "pfsFileSystem.Fcb{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", startBlockId=" + startBlockId +
                ", endBlockId=" + endBlockId +
                ", numBlocks=" + numBlocks +
                ", createTime=" + createTime +
                ", createDate=" + createDate +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    /**
     * dislpays the details of the fcb as comma seperated
     * values. These values are then written to the pfs
     * @return
     */
    public String toStringData() {
        return getFileName() + "," +
                getFileSize() + "," +
                getStartBlockId() + "," +
                getEndBlockId() + "," +
                getNumBlocks() + "," +
                getCreateTime() + "," +
                getCreateDate() + "," +
                getRemarks() + ",";
    }


    /**
     * dislpays the details of the fcb as comma seperated
     * values. These values are then written to the pfs
     * @return
     */
    public String toDir() {
        return getFileName() + "\t" +
                getFileSize() + "\t" +
                getCreateTime() + "\t" +
                getCreateDate()+ "\t" +
                getRemarks();
    }
    /**
     * returns the details of the fcb in byte format
     * that would be written directly to the pfs
     * @return
     */
    public byte[] toByteData() {
        byte[] b = toStringData().getBytes();
        return b;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * member varibales
     */
    private String fileName;
    private int fileSize;
    private int startBlockId;
    private int endBlockId;
    private int numBlocks;
    private String remarks;
    private String createTime;
    private String createDate;
}
