import com.sun.org.apache.regexp.internal.RE;

/**
 * Keeps track of the location of the
 * data and fcb blocks
 */

public class FsManager {

    /**
     * Add some constants in the FsManager
     */
    private static final int PFS_BLOCK_SIZE = 256;
    private static final int PFS_FILE_SIZE = 10000;
    private static final int RESERVED_BLOCKS = 3;
    private static final int EMPTY_BLOCK = -1;

    /**
     * The default constructor
     * -sets all block id's to -1 to indicate they are not yet allocated
     * -the available block count should be 36
     * (since the file manager block, fcb one block and fcb two blocks
     *  are reserved)
     */
    public FsManager() {
        this.availableBlockCount = (PFS_FILE_SIZE / PFS_BLOCK_SIZE) - RESERVED_BLOCKS;
        this.fcbOneBlockId = EMPTY_BLOCK;
        this.dataOneStartBlockId = EMPTY_BLOCK;
        this.dataOneEndBlockId = EMPTY_BLOCK;
        this.fcbTwoBlockId = EMPTY_BLOCK;
        this.dataTwoStartBlockId = EMPTY_BLOCK;
        this.dataTwoEndBlockId = EMPTY_BLOCK;
    }

    /**
     * returns the details of the file manager
     * @return
     */
    @Override
    public String toString() {
        return "FsManager{" +
                "availableBlockCount=" + availableBlockCount +
                ", fcbOneBlockId=" + fcbOneBlockId +
                ", dataOneStartBlockId=" + dataOneStartBlockId +
                ", dataOneEndBlockId=" + dataOneEndBlockId +
                ", fcbTwoBlockId=" + fcbTwoBlockId +
                ", dataTwoStartBlockId=" + dataTwoStartBlockId +
                ", dataTwoEndBlockId=" + dataTwoEndBlockId +
                '}';
    }

    /**
     * checks if the pfs is full
     * returns true only if fcbone and fcbtwo are unused i.e < 1
     * @return
     */
    public boolean isEmpty() {
        boolean _isFcboneEmpty = this.getFcbOneBlockId() < 0;
        boolean _isFcbTwoEmpty = this.getFcbOneBlockId() < 0;
        return (_isFcboneEmpty && _isFcbTwoEmpty);
    }

    /**
     * returns the details of the file manager as comma seperated values
     * @return
     */
    public String toStringData() {
        return  getAvailableBlockCount() + "," +
                getFcbOneBlockId()+ "," +
                getDataOneStartBlockId() + "," +
                getDataOneEndBlockId() + "," +
                getFcbTwoBlockId() + "," +
                getDataTwoStartBlockId() + "," +
                getDataTwoEndBlockId();
    }

    /**
     * returns the details of the file manager in byte format
     * that would be written directly to the pfs
     * @return
     */
    public byte[] toByteData() {
        byte[] b = toStringData().getBytes();
        return b;
    }

    public int getAvailableBlockCount() {
        return availableBlockCount;
    }

    public void setAvailableBlockCount(int availableBlockCount) {
        this.availableBlockCount = availableBlockCount;
    }

    public int getFcbOneBlockId() {
        return fcbOneBlockId;
    }

    public void setFcbOneBlockId(int fcbOneBlockId) {
        this.fcbOneBlockId = fcbOneBlockId;
    }

    public int getDataOneStartBlockId() {
        return dataOneStartBlockId;
    }

    public void setDataOneStartBlockId(int dataOneStartBlockId) {
        this.dataOneStartBlockId = dataOneStartBlockId;
    }

    public int getDataOneEndBlockId() {
        return dataOneEndBlockId;
    }

    public void setDataOneEndBlockId(int dataOneEndBlockId) {
        this.dataOneEndBlockId = dataOneEndBlockId;
    }

    public int getFcbTwoBlockId() {
        return fcbTwoBlockId;
    }

    public void setFcbTwoBlockId(int fcbTwoBlockId) {
        this.fcbTwoBlockId = fcbTwoBlockId;
    }

    public int getDataTwoStartBlockId() {
        return dataTwoStartBlockId;
    }

    public void setDataTwoStartBlockId(int dataTwoStartBlockId) {
        this.dataTwoStartBlockId = dataTwoStartBlockId;
    }

    public int getDataTwoEndBlockId() {
        return dataTwoEndBlockId;
    }

    public void setDataTwoEndBlockId(int dataTwoEndBlockId) {
        this.dataTwoEndBlockId = dataTwoEndBlockId;
    }

    /**
     * member variables
     */
    int availableBlockCount;
    int fcbOneBlockId;
    int dataOneStartBlockId;
    int dataOneEndBlockId;
    int fcbTwoBlockId;
    int dataTwoStartBlockId;
    int dataTwoEndBlockId;




}
