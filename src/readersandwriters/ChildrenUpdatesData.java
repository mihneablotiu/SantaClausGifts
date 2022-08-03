package readersandwriters;

import enums.Category;

import java.util.ArrayList;

public final class ChildrenUpdatesData {
    private final Integer id;
    private final Double niceScore;
    private final ArrayList<Category> giftsPreferences;
    private final String elf;

    public ChildrenUpdatesData(final Integer id, final Double niceScore,
                               final ArrayList<Category> giftsPreferences, final String elf) {
        this.id = id;
        this.niceScore = niceScore;
        this.giftsPreferences = giftsPreferences;
        this.elf = elf;
    }

    public Integer getId() {
        return id;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public ArrayList<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public String getElf() {
        return elf;
    }
}
