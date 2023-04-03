package bigcity;

public abstract class PrivateZone extends Zone {
    protected int size;
    protected int capacity;
    
    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public abstract int upgrade();
    
    public int collectTax() {
        //TODO
        return 0;
    }
}
