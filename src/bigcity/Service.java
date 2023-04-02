package bigcity;

import java.util.ArrayList;
import res.Assets;

public class Service extends Workplace {
    public Service(int topLeftX, int topLeftY, int capacity) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        size = 0;
        this.capacity = capacity;
        level = 1;
        img = Assets.copperS;
        workers = new ArrayList<>();
    }
}
