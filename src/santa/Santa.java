package santa;

import children.Child;
import enums.Cities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Santa {
    private final Double numberOfYears;
    private Double santaBudget;
    private Double sumAverage;
    private Double budgetUnit;
    private final HashMap<Cities, ArrayList<Double>> niceScoreCityList = new HashMap<>();
    private final HashMap<Cities, Double> niceScoreAverage = new HashMap<>();

    public Santa(final Double numberOfYears, final Double santaBudget) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
    }

    /**
     * The function that calculates the average score of every
     * city depending on the average score of each child that is
     * from the corespondent city
     * @param arrayList the arraylist of the children that is going
     *                  to be taken into consideration
     */
    public void getNiceScoreCity(final ArrayList<Child> arrayList) {
        for (Child child : arrayList) {
            if (!this.niceScoreCityList.containsKey(child.getCity())) {
                ArrayList<Double> newList = new ArrayList<>();
                newList.add(child.getAverageScore());
                this.niceScoreCityList.put(child.getCity(), newList);
            } else {
                this.niceScoreCityList.get(child.getCity()).add(child.getAverageScore());
            }
        }

        for (Map.Entry<Cities, ArrayList<Double>> entry : this.niceScoreCityList.entrySet()) {
            ArrayList<Double> currentList = entry.getValue();
            Double average = 0d;
            for (Double value : currentList) {
                average += value;
            }

            average = average / currentList.size();

            this.niceScoreAverage.put(entry.getKey(), average);
        }
    }

    /**
     * Method used to get the sum of the average grades of
     * all the children
     * @param arrayList the list with the children
     */
    public void getSumAverage(final ArrayList<Child> arrayList) {
        double sum = 0d;
        for (Child child : arrayList) {
            if (child.getAverageScore() != null) {
                sum += child.getAverageScore();
            }
        }

        this.sumAverage = sum;
    }

    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }

    /**
     * Function used to set the budgetUnit by the given formula
     */
    public void setBudgetUnit() {
        this.budgetUnit = this.santaBudget / this.sumAverage;
    }

    public Double getNumberOfYears() {
        return numberOfYears;
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public Double getBudgetUnit() {
        return budgetUnit;
    }

    public HashMap<Cities, Double> getNiceScoreAverage() {
        return niceScoreAverage;
    }

    public HashMap<Cities, ArrayList<Double>> getNiceScoreCityList() {
        return niceScoreCityList;
    }
}
