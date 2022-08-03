package readersandwriters;

public final class SantaGeneralData {
    private Double numberOfYears;
    private Double santaBudget;

    public SantaGeneralData() {

    }

    public SantaGeneralData(final Double numberOfYears, final Double santaBudget) {
        this.numberOfYears = numberOfYears;
        this.santaBudget = santaBudget;
    }

    public Double getNumberOfYears() {
        return numberOfYears;
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public void setNumberOfYears(final Double numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }
}
