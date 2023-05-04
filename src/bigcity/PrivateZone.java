package bigcity;

public abstract class PrivateZone extends Zone {
    protected int capacity;

    public int getCapacity() {
        return capacity;
    }
    
    public abstract int getSize();
    
    public abstract int upgrade();
    public abstract void addPerson(Person p);
    
}
