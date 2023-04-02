package bigcity;

public abstract class PrivateZone extends Zone {
    public int upgrade() {
        if(level < 3)
            level++;
        
        //TODO
        
        return level;
    }
    
    public int collectTax() {
        //TODO
        return 0;
    }
}
