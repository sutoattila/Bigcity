package bigcity;

import java.util.ArrayList;
import res.Assets;

public class Residence extends PrivateZone {

    @Override
    public int upgrade() {
        //TODO
        return 1;
    }

    public Residence(int topLeftX, int topLeftY, int capacity) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        size = 0;
        this.capacity = capacity;
        level = 1;
        img = Assets.copperR;
        people = new ArrayList<>();
    }

}
