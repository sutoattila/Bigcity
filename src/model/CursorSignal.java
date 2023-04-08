package model;

public enum CursorSignal {
    //Private zones.----------------
    POLICE(1, 1, 300, 200, 300),
    STADIUM(2, 2, 300, 200, 300),
    HIGH_SCHOOL(2, 1, 300, 200, 300),
    UNIVERSITY(2, 2, 500, 200, 300),
    ROAD(1, 1, 50, 200, 300),
    //Public zones.-----------------
    RESIDENCE(1, 1, 50, 200, 300),
    INDUSTRY(1, 1, 50, 200, 300),
    SERVICE(1, 1, 50, 200, 300),
    //------------------------------
    DESTROY(1, 1),
    //It's for selecting a zone.
    SELECT(1, 1);

    private int width;
    private int height;

    private int priceL1;
    private int priceL2;
    private int priceL3;

    private CursorSignal(int width, int height) {
        this.width = width;
        this.height = height;
    }

    private CursorSignal(int width, int height, int priceL1, int priceL2,
            int priceL3) {
        this.width = width;
        this.height = height;
        this.priceL1 = priceL1;
        this.priceL2 = priceL2;
        this.priceL3 = priceL3;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    int getPriceL1() {
        return priceL1;
    }

    public int getPriceL2() {
        return priceL2;
    }

    public int getPriceL3() {
        return priceL3;
    }

}
