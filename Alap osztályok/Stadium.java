package bigcity;

import java.util.ArrayList;

/**
 *
 * @author Sütő Attila
 */
public class Stadium extends PublicZone {
    
    protected ArrayList<Field> nearbyFields;
            
    @Override
    public int upgrade(){
        return 1;
    }

    public Stadium() {
    }
}
