package averagedetermination;

import common.Constants;

import java.util.ArrayList;

public final class BabyAverageStrategy implements AverageStrategy {
    @Override
    public Double getAverageScore(final ArrayList<Double> niceScoreList) {
        return Constants.MAXIMUM_GRADE;
    }

    @Override
    public String getAverageScoreDescription() {
        return "Aceasta strategie ofera nota 10 pentru toti copii cu varsta mai mica de 5 ani";
    }
}
