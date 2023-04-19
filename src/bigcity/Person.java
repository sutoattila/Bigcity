package bigcity;

public class Person {

    protected String name;
    protected int age;
    protected int happiness;
    protected EducationLevel educationLevel;
    protected Zone home;
    protected Zone job;
    protected boolean male;

    //-1 means that this person doesn't have a job.
    protected int homeJobDistance = -1;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getHappiness() {
        return happiness;
    }

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public boolean isMale() {
        return male;
    }

    public int changeHappinessBy(int value) {
        happiness += value;
        return happiness;
    }

    public void educate() {
        if (EducationLevel.UNIVERSITY == educationLevel) {
            return;
        }
        if (EducationLevel.HIGH_SCHOOL == educationLevel) {
            educationLevel = EducationLevel.UNIVERSITY;
            return;
        }
        if (EducationLevel.PRIMARY_SCHOOL == educationLevel) {
            educationLevel = EducationLevel.HIGH_SCHOOL;
            return;
        }
    }

    public int growOlder() {
        age += 1;
        return age;
    }

    public Person(String name, int age, int happiness, boolean male,
            EducationLevel educationLevel, Zone home, Zone job) {
        this.name = name;
        //Min 18.
        this.age = age;
        this.happiness = happiness;
        this.male = male;
        this.educationLevel = educationLevel;
        this.home = home;
        this.job = job;
    }

    public void findJob() {
        //TODO
    }

    public void findHome() {
        //TODO
    }

    public void die() {
        age = 18;
        educationLevel = EducationLevel.PRIMARY_SCHOOL;
    }

    public void moveFromTown() {
        //TODO
    }

    public Zone getHome() {
        return home;
    }

    public Zone getJob() {
        return job;
    }

    public void setHome(Zone home) {
        this.home = home;
    }

    public void setJob(Zone job) {
        this.job = job;
    }

    public void setHomeJobDistance(int homeJobDistance) {
        this.homeJobDistance = homeJobDistance;
    }

    public int getHomeJobDistance() {
        return homeJobDistance;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
    
    

}
