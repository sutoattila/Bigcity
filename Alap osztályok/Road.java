package bigcity;

import java.util.ArrayList;

public class Road extends PublicZone {
    
    protected ArrayList<Field> connectedRoads;
    
    @Override
    public int upgrade(){
        return 1;
    }

    public Road() {
    }
    
}
