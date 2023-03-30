package model;

public enum CursorSignal {
    //Private zones.----------------
    POLICE(1, 1),
    STADIUM(2, 2),
    HIGH_SCHOOL(2, 1),
    UNIVERSITY(2, 2),
    ROAD(1, 1),
    //Public zones.-----------------
    RESIDENCE(1, 1),
    INDUSTRY(1, 1),
    SERVICE(1, 1),
    //------------------------------
    //On hover we must change the paintComponet's code!
    DESTROY(1, 1),
    //It's for selecting a zone to see its JPanel.
    SELECT(1, 1);

    private int width;
    private int height;

    private CursorSignal() {
    }

    private CursorSignal(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
