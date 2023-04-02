package bigcity;

import java.util.ArrayList;
import res.Assets;

public class Industry extends Workplace {

    public Industry(int topLeftX, int topLeftY, int capacity) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        size = 0;
        this.capacity = capacity;
        level = 1;
        img = Assets.copperI;
        workers = new ArrayList<>();
    }

}
