package readersandwriters;

import enums.Category;
import enums.Cities;

import java.util.ArrayList;

public final class ChildData {
    private final Integer id;
    private final String lastName;
    private final String firstName;
    private final Integer age;
    private final Cities city;
    private final Double niceScore;
    private final ArrayList<Category> giftsPreferences;
    private final Double niceScoreBonus;
    private final String elf;

    public ChildData(final Integer id, final String lastName,
                     final String firstName, final Integer age,
                     final Cities city, final Double niceScore,
                     final ArrayList<Category> giftsPreferences,
                     final Double niceScoreBonus, final String elf) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreferences;
        this.niceScoreBonus = niceScoreBonus;
        this.elf = elf;
    }

    public Integer getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Integer getAge() {
        return age;
    }

    public Cities getCity() {
        return city;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public ArrayList<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public Double getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public String getElf() {
        return elf;
    }
}
