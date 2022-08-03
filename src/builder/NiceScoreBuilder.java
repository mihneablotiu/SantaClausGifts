package builder;

public final class NiceScoreBuilder {
    private final Double niceScoreBonus;

    public static final class Builder {
        private Double niceScoreBonus = 0d;

        /**
         * Function that builds an object with an optional
         * niceScoreBonus that is different from 0
         * @param newNiceScoreBonus the new nice score bonus
         *                          different from 0
         * @return the built object
         */
        public Builder niceScoreBonus(final Double newNiceScoreBonus) {
            this.niceScoreBonus = newNiceScoreBonus;
            return this;
        }

        /**
         * Function that builds the niceScore final object taking
         * into consideration if the new nice score bonus is different
         * from 0
         * @return the NiceScoreBuilder object
         */
        public NiceScoreBuilder build() {
            return new NiceScoreBuilder(this);
        }
    }

    public NiceScoreBuilder(final Builder builder) {
        this.niceScoreBonus = builder.niceScoreBonus;
    }

    public Double getNiceScoreBonus() {
        return niceScoreBonus;
    }
}
