package bigcity;

/**
 *
 * @author Sütő Attila
 */
public class Field {
    protected int rowIndex;
    protected int columnIndex;
    protected Zone zone;

    public Field(int rowIndex, int columnIndex, Zone zone) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.zone = zone;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }
    
    public void freeField(){
    
    }
}
