package readersandwriters;

import java.util.ArrayList;

public final class InitialData {
    private ArrayList<ChildData> childData = new ArrayList<>();
    private ArrayList<GiftData> giftData = new ArrayList<>();

    public InitialData() {

    }

    public InitialData(final ArrayList<ChildData> childData,
                       final ArrayList<GiftData> giftData) {
        this.childData = childData;
        this.giftData = giftData;
    }

    public ArrayList<ChildData> getChildData() {
        return childData;
    }

    public ArrayList<GiftData> getGiftData() {
        return giftData;
    }
}
