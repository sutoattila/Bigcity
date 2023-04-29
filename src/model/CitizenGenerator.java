package model;

import bigcity.EducationLevel;
import bigcity.Person;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class CitizenGenerator {

    private List<String> maleNames;
    private List<String> femaleNames;

    public CitizenGenerator() {
        try {
            Path path = Path.of("./src/res/citizenNames/maleNames.txt");
            this.maleNames = Files.lines(path).toList();
            path = Path.of("./src/res/citizenNames/femaleNames.txt");
            this.femaleNames = Files.lines(path).toList();
        } catch (IOException ex) {
            System.err.println("Couldn't open the file.");
        }
    }

    public Person createCitizen() {

        Random random = new Random();

        //0->female, 1->male
        boolean male = random.nextInt(2) == 0 ? false : true;

        int minAge = 18;
        int maxAge = 60;
        int age = random.nextInt(maxAge - minAge + 1) + minAge;

        String name;
        if (male) {
            name = maleNames.get(random.nextInt(maleNames.size()));
        } else {
            name = femaleNames.get(random.nextInt(femaleNames.size()));
        }

        return new Person(
                name,
                age,
                100,
                male,
                EducationLevel.PRIMARY_SCHOOL,
                null,
                null
        );
    }
}
