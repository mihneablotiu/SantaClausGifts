package readersandwriters;

import java.util.ArrayList;

public final class AnualChangesData {
    private Double newSantaBudget;
    private ArrayList<GiftData> newGifts;
    private ArrayList<ChildData> newChildren;
    private ArrayList<ChildrenUpdatesData> childrenUpdates;
    private final String newStrategy;

    public AnualChangesData(final Double newSantaBudget, final ArrayList<GiftData> newGifts,
                            final ArrayList<ChildData> newChildren,
                            final ArrayList<ChildrenUpdatesData> childrenUpdates,
                            final String newStrategy) {
        this.newSantaBudget = newSantaBudget;
        this.newGifts = newGifts;
        this.newChildren = newChildren;
        this.childrenUpdates = childrenUpdates;
        this.newStrategy = newStrategy;
    }

    public Double getNewSantaBudget() {
        return newSantaBudget;
    }

    public void setNewSantaBudget(final Double newSantaBudget) {
        this.newSantaBudget = newSantaBudget;
    }

    public ArrayList<GiftData> getNewGifts() {
        return newGifts;
    }

    public void setNewGifts(final ArrayList<GiftData> newGifts) {
        this.newGifts = newGifts;
    }

    public ArrayList<ChildData> getNewChildren() {
        return newChildren;
    }

    public void setNewChildren(final ArrayList<ChildData> newChildren) {
        this.newChildren = newChildren;
    }

    public ArrayList<ChildrenUpdatesData> getChildrenUpdates() {
        return childrenUpdates;
    }

    public void setChildrenUpdates(final ArrayList<ChildrenUpdatesData> childrenUpdates) {
        this.childrenUpdates = childrenUpdates;
    }

    public String getNewStrategy() {
        return newStrategy;
    }

    @Override
    public String toString() {
        return "AnualChangesData{"
                + "newSantaBudget=" + newSantaBudget
                + ", newGifts=" + newGifts
                + ", newChildren=" + newChildren
                + ", childrenUpdates=" + childrenUpdates
                + '}' + "\n";
    }
}
