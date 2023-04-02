package bigcity;

import res.Assets;

public class Stadium extends PublicZone {
    public Stadium(int topLeftX, int topLeftY, int maintenanceCost) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        level = 1;
        img = Assets.stadium;
        this.maintenanceCost = maintenanceCost;
    }
}
