package averagedetermination;

import java.util.ArrayList;

public final class TeenAverageStrategy implements AverageStrategy {
    @Override
    public Double getAverageScore(final ArrayList<Double> niceScoreList) {
        double sum = 0d;
        int numberOfNumbers = 0;
        for (int i = 0; i < niceScoreList.size(); i++) {
            sum += (i + 1) * niceScoreList.get(i);
            numberOfNumbers += i + 1;
        }

        return sum / numberOfNumbers;
    }

    @Override
    public String getAverageScoreDescription() {
        return "Această strategie calculează media"
                + " aritmetică ponderată a scorurilor din lista de scoruri";
    }
}
