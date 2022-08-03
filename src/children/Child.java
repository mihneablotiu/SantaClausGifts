package children;

import averagedetermination.AverageStrategy;
import averagedetermination.AverageStrategyFactory;
import builder.NiceScoreBuilder;
import common.Constants;
import enums.Category;
import enums.Cities;
import readersandwriters.GiftData;

import java.util.ArrayList;

public final class Child {
    private final Integer id;
    private final String lastName;
    private final String firstName;
    private Integer age;
    private final Cities city;
    private Double niceScore;
    private final ArrayList<Category> giftsPreferences;
    private String ageCategory;
    private final ArrayList<Double> niceScoreList = new ArrayList<>();
    private Double averageScore;
    private Double budget;
    private final ArrayList<GiftData> receivedGifts = new ArrayList<>();
    private String elf;
    private final Double niceScoreBonus;
    private Double cityAverage;

    public Child(final Integer id, final String lastName,
                 final String firstName, final Integer age,
                 final Cities city, final Double niceScore,
                 final ArrayList<Category> giftsPreferences,
                 final String elf, final Double niceScoreBonus) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.city = city;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreferences;
        this.elf = elf;
        this.niceScoreBonus = niceScoreBonus;
    }

    /**
     * Function to set the age category in which
     * each child should be
     */
    public void determineAgeCategory() {
        if (this.age.compareTo(Constants.BABY_AGE) < 0) {
            this.ageCategory = "Baby";
        } else if (this.age.compareTo(Constants.BABY_AGE) >= 0
                && this.age.compareTo(Constants.KID_AGE) < 0) {
            this.ageCategory = "Kid";
        } else if (this.age.compareTo(Constants.KID_AGE) >= 0
                && this.age.compareTo(Constants.YOUNG_ADULT_AGE) <= 0) {
            this.ageCategory = "Teen";
        } else {
            this.ageCategory = "Young Adult";
        }
    }

    /**
     * Function to add a score in the nice score
     * list when the child gets a new one
     */
    public void updateNiceScoreList() {
        this.niceScoreList.add(this.niceScore);
    }

    /**
     * Function used to determine the average score of each child
     * depending on the strategy we are using
     */
    public void determineAverageScore() {
        AverageStrategyFactory averageStrategyFactory = AverageStrategyFactory.getInstance();
        AverageStrategy averageStrategy = averageStrategyFactory.createStrategy(this.ageCategory);
        this.averageScore = averageStrategy.getAverageScore(this.niceScoreList);

        if (!this.getNiceScoreBonus().equals(0d) && this.averageScore != null) {
            NiceScoreBuilder niceScoreBuilder = new NiceScoreBuilder.Builder()
                    .niceScoreBonus(this.getNiceScoreBonus())
                    .build();

            this.averageScore += this.averageScore * niceScoreBuilder.getNiceScoreBonus()
                    / Constants.PERCENTAGE;

            if (this.averageScore.compareTo(Constants.MAXIMUM_GRADE) > 0) {
                this.averageScore = Constants.MAXIMUM_GRADE;
            }
        }

    }

    /**
     * Function used to determine the budget of each child
     * in a specific year
     * @param budgetUnit the budgetUnit determined from
     *                   the Santa's budget and the
     *                   sum of the average scores of each
     *                   child
     */
    public void getBudget(final Double budgetUnit) {
        if (this.averageScore != null) {
            this.budget = this.averageScore * budgetUnit;
        } else {
            this.budget = null;
        }
    }

    /**
     * The function that modifies the budget of a child
     * depending on the type of elf he/she has
     */
    public void blackPinkElf() {
        if (this.budget != null) {
            if (this.getElf().equals("black")) {
                this.budget -= this.budget * Constants.ELVES_PERCENTAGE
                        / Constants.PERCENTAGE;
            } else if (this.getElf().equals("pink")) {
                this.budget += this.budget * Constants.ELVES_PERCENTAGE
                        / Constants.PERCENTAGE;
            }
        }
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public void setNiceScore(final Double niceScore) {
        this.niceScore = niceScore;
    }

    public ArrayList<GiftData> getReceivedGifts() {
        return receivedGifts;
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

    public String getAgeCategory() {
        return ageCategory;
    }

    public ArrayList<Double> getNiceScoreList() {
        return niceScoreList;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public Double getBudget() {
        return budget;
    }

    public String getElf() {
        return elf;
    }

    public Double getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public Double getCityAverage() {
        return cityAverage;
    }

    public void setCityAverage(final Double cityAverage) {
        this.cityAverage = cityAverage;
    }

    public void setElf(final String elf) {
        this.elf = elf;
    }
}
