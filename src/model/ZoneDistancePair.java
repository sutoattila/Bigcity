package model;

import bigcity.Zone;

public class ZoneDistancePair {

    private Zone zone;
    private int distance;

    public ZoneDistancePair(Zone zone, int distance) {
        this.zone = zone;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public Zone getZone() {
        return zone;
    }

}
