package bigcity;

import java.util.ArrayList;

/**
 *
 * @author Sütő Attila
 */
public class Police extends PublicZone {
    
    protected ArrayList<Field> securedFields;
    
    @Override
    public int upgrade(){
        //TODO
        return 1;
    }

    public Police() {
    }
    
}
