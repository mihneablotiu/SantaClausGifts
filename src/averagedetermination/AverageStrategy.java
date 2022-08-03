package averagedetermination;

import java.util.ArrayList;

public interface AverageStrategy {
    /**
     *
     * @param niceScoreList the list of the nice scores
     *                      for each child
     * @return the average score returned by one of the
     * strategies
     */
    Double getAverageScore(ArrayList<Double> niceScoreList);

    /**
     *
     * @return the description of each strategy
     */
    String getAverageScoreDescription();
}
