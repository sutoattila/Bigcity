package bigcity;

public class Engine {
    protected int money;
    protected double combinedHappiness;
    protected String date;
    protected int timeSpeed;
    protected int taxPercentage;
    protected String name;

    public int getMoney() {
        return money;
    }

    public double getCombinedHappiness() {
        return combinedHappiness;
    }

    public String getDate() {
        return date;
    }

    public int getTimeSpeed() {
        return timeSpeed;
    }

    public int getTaxPercentage() {
        return taxPercentage;
    }

    public String getName() {
        return name;
    }
    
    public int addMoney(int value){
        money+=value;
        return money;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void aDayPassed(){
        //TODO
    }
    public void aMonthPassed(){
        //TODO
    }
    public void aYearPassed(){
        //TODO
    }

    public void setTaxPercentage(int taxPercentage) {
        this.taxPercentage = taxPercentage;
    }
    
    public int calculateHappieness(){
        //TODO
        return 0;
    }
    
    public void collectTax(){
        //TODO
    }

    public Engine(String name) {
        this.name = name;
    }

    public Engine(int money, String date, int taxPercentage, String name) {
        this.money = money;
        this.date = date;
        this.taxPercentage = taxPercentage;
        this.name = name;
    }
    
    public void refactor(){
    
    }
}
