package bigcity;

import res.Assets;

public class Road extends PublicZone {

    @Override
    public int upgrade() {
        return 1;
    }

    public Road(int topLeftX, int topLeftY,
            int maintenanceCost) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        level = 1;
        img = Assets.roadNS;
        this.maintenanceCost = maintenanceCost;
    }

}
