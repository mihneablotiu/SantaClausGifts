package averagedetermination;

import java.util.ArrayList;

public final class YoungAdultStrategy implements AverageStrategy {
    @Override
    public Double getAverageScore(final ArrayList<Double> niceScoreList) {
        return null;
    }

    @Override
    public String getAverageScoreDescription() {
        return "Această strategie returnează null pentru adulții cu vârsta mai mare de 18 ani";
    }
}
