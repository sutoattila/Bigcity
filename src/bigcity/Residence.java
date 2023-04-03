package bigcity;

import java.util.ArrayList;
import java.util.List;
import res.Assets;

public class Residence extends PrivateZone {
    protected List<Person> resinedts;
    
    public Residence(int topLeftX, int topLeftY, int price) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.size = 0;
        this.capacity = 8;
        this.level = 1;
        this.img = Assets.copperR;
        this.resinedts = new ArrayList<>();
        this.price = price;
    }

    public List<Person> getResidents(){
        return resinedts;
    }
    
    @Override
    public int upgrade(){
        if(getLevel() < 3) {
            capacity *= 2;
            level++;
        }
        return level;
    }
}
