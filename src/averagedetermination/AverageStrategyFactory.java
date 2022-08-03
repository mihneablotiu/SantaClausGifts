package averagedetermination;

public final class AverageStrategyFactory {
    private static AverageStrategyFactory instance = null;

    private AverageStrategyFactory() {
    }

    /**
     * Singleton design pattern for the factory
     * of the strategies
     *
     * @return the factory of the strategies
     */
    public static AverageStrategyFactory getInstance() {
        if (instance == null) {
            instance = new AverageStrategyFactory();
        }

        return instance;
    }

    /**
     * Function to crate different strategies depending
     * on the age category
     *
     * @param ageCategory the category in which each child
     *                    is included used to determine what
     *                    strategy is going to be applied
     * @return the applied strategy
     */
    public AverageStrategy createStrategy(final String ageCategory) {
        return switch (ageCategory) {
            case "Baby" -> new BabyAverageStrategy();
            case "Kid" -> new KidAverageStrategy();
            case "Teen" -> new TeenAverageStrategy();
            default -> new YoungAdultStrategy();
        };
    }
}
