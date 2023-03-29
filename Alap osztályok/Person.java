package bigcity;

/**
 *
 * @author Sütő Attila
 */
public class Person {
    protected String name;
    protected  int age;
    protected int hapiness;
    protected int educationLevel;
    protected Zone home;
    protected Zone job;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHapiness() {
        return hapiness;
    }

    public int getEducationLevel() {
        return educationLevel;
    }
    
    public int changeHappinessBy(int value){
        hapiness+=value;
        return hapiness;
    }
    
    public int educate(){
        return educationLevel*2;
    } 
    
    public int growOlder(){
        age+=1;
        return age;
    }

    public Person(String name) {
        //TODO
        this.name = name;
        this.educationLevel = 1;
    }

    public Person(String name, int age, int hapiness, int educationLevel, Zone home, Zone job) {
        this.name = name;
        this.age = age;
        this.hapiness = hapiness;
        this.educationLevel = educationLevel;
        this.home = home;
        this.job = job;
    }
    
    public Zone findJob(){
        //TODO
        return new Industry();
    }
    
    public Zone findHome(){
        //TODO
        return new Residence();
    }
    
    public void die(){
        age=18;
        educationLevel=1;
    }
    
    public void moveFromTown(){
        //TODO
    }
}
