package bigcity;

public class Person {

    protected String name;
    protected int age;
    protected int hapiness;
    protected EducationLevel educationLevel;
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

    public EducationLevel getEducationLevel() {
        return educationLevel;
    }

    public int changeHappinessBy(int value) {
        hapiness += value;
        return hapiness;
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

    public Person(String name, int age, int hapiness,
            EducationLevel educationLevel, Zone home, Zone job) {
        this.name = name;
        //Min 18.
        this.age = age;
        this.hapiness = hapiness;
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
}
