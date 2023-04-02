package bigcity;

import java.util.ArrayList;
import java.util.List;
import res.Assets;

public class Residence extends PrivateZone {
    protected List<Person> resinedts;
    
    public Residence(int topLeftX, int topLeftY, int capacity) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        size = 0;
        this.capacity = capacity;
        level = 1;
        img = Assets.copperR;
        resinedts = new ArrayList<>();
    }

    public List<Person> getResidents(){
        return resinedts;
    }
}
