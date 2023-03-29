package bigcity;

import java.util.ArrayList;

public abstract class Zone {
    protected int width;
    protected int height;
    protected int price;
    protected int size;
    protected int capacity;
    protected int level;

    protected ArrayList<Person> people;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }
        
    public int getPrice() {
        return price;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getLevel() {
        return level;
    }
    
    public boolean isConnectedToRoad(){
        //TODO
        return true;
    }
    public void destroy(){
        //TODO
    }
    public abstract int upgrade();
}
