package averagedetermination;

import java.util.ArrayList;

public final class KidAverageStrategy implements AverageStrategy {
    @Override
    public Double getAverageScore(final ArrayList<Double> niceScoreList) {
        Double sum = 0d;
        for (Double current : niceScoreList) {
            sum += current;
        }

        return sum / niceScoreList.size();
    }

    @Override
    public String getAverageScoreDescription() {
        return "Această strategie calculează media aritmetică a scorurilor din lista de scoruri";
    }
}
