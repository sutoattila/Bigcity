package bigcity;

import res.Assets;

public class Police extends PublicZone {
    public Police(int topLeftX, int topLeftY, int maintenanceCost) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        level = 1;
        img = Assets.police;
        this.maintenanceCost = maintenanceCost;
    }
}
