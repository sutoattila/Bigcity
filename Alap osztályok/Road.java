package bigcity;

import java.util.ArrayList;

/**
 *
 * @author Sütő Attila
 */
public class Road extends PublicZone {
    
    protected ArrayList<Field> connectedRoads;
    
    @Override
    public int upgrade(){
        return 1;
    }

    public Road() {
    }
    
}
