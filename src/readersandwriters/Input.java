package readersandwriters;

import java.util.ArrayList;

public final class Input {
    private final SantaGeneralData santaGeneralData;
    private final InitialData initialData;
    private final ArrayList<AnualChangesData> anualChangesData;

    public Input(final SantaGeneralData santaGeneralData,
                 final InitialData initialData,
                 final ArrayList<AnualChangesData> anualChangesData) {
        this.santaGeneralData = santaGeneralData;
        this.initialData = initialData;
        this.anualChangesData = anualChangesData;
    }

    public SantaGeneralData getSantaGeneralData() {
        return santaGeneralData;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public ArrayList<AnualChangesData> getAnualChangesData() {
        return anualChangesData;
    }
}
