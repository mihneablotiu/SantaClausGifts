package gifts;

import children.Child;
import enums.Category;
import enums.Cities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import readersandwriters.AnualChangesData;
import readersandwriters.ChildData;
import readersandwriters.ChildrenUpdatesData;
import readersandwriters.GiftData;
import readersandwriters.InitialData;
import santa.Santa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

public final class GiftsDatabase {
    private final ArrayList<Child> childrenList;
    private final Santa santa;
    private final InitialData initialData;
    private final ArrayList<AnualChangesData> annualChangesDataList;
    private final JSONArray jsonArray;
    private String strategy;

    public GiftsDatabase(final ArrayList<Child> childrenList, final Santa santa,
                         final InitialData initialData,
                         final ArrayList<AnualChangesData> annualChangesDataList,
                         final JSONArray jsonArray, final String strategy) {

        this.childrenList = childrenList;
        this.santa = santa;
        this.initialData = initialData;
        this.annualChangesDataList = annualChangesDataList;
        this.jsonArray = jsonArray;
        this.strategy = strategy;
    }


    /**
     * The function that iterates through the years and updates
     * the list of children and the list of gifts and calls
     * the giftDistribution method in order to give each
     * child his gift
     */
    @SuppressWarnings("unchecked")
    public void giftsDistribution() {
        double numberOfRounds = santa.getNumberOfYears() + 1;
        int annualChangesPosition = 0;

        while (numberOfRounds != 0) {
            JSONArray currentArray = new JSONArray();
            JSONObject currentObject = new JSONObject();

            this.childrenList.removeIf(child -> child.getAgeCategory().equals("Young Adult"));
            switch (this.strategy) {
                case "id" -> {
                    for (Child child : this.childrenList) {
                        child.getReceivedGifts().clear();
                        childDistribution(child);
                    }
                    this.childrenList.forEach(this::yellowElf);
                    for (Child child : this.childrenList) {
                        jsonWriter(child, currentArray);
                    }
                }
                case "niceScore" -> {
                    this.childrenList.sort((o1, o2) -> {
                        if (o1.getAverageScore().compareTo(o2.getAverageScore()) != 0) {
                            return o2.getAverageScore().compareTo(o1.getAverageScore());
                        } else {
                            return o1.getId().compareTo(o2.getId());
                        }
                    });
                    for (Child child : this.childrenList) {
                        child.getReceivedGifts().clear();
                        childDistribution(child);
                    }
                    this.childrenList.forEach(this::yellowElf);
                    this.childrenList.sort(Comparator.comparing(Child::getId));
                    for (Child child : this.childrenList) {
                        jsonWriter(child, currentArray);
                    }
                }
                case "niceScoreCity" -> {
                    this.childrenList.sort(Comparator.comparing(Child::getId));
                    santa.getNiceScoreCityList().clear();
                    santa.getNiceScoreAverage().clear();
                    santa.getNiceScoreCity(this.childrenList);
                    for (Child child : this.childrenList) {
                        child.setCityAverage(santa.getNiceScoreAverage().get(child.getCity()));
                    }
                    this.childrenList.sort((o1, o2) -> {
                        if (o1.getCityAverage().compareTo(o2.getCityAverage()) != 0) {
                            return o2.getCityAverage().compareTo(o1.getCityAverage());
                        } else if (cityToString(o1.getCity())
                                .compareTo(cityToString(o2.getCity())) != 0) {
                            return cityToString(o1.getCity())
                                    .compareTo(cityToString(o2.getCity()));
                        } else {
                            return o1.getId().compareTo(o2.getId());
                        }
                    });

                    this.childrenList.forEach(child -> {
                        child.getReceivedGifts().clear();
                        childDistribution(child);
                    });
                    this.childrenList.forEach(this::yellowElf);
                    this.childrenList.sort(Comparator.comparing(Child::getId));
                    for (Child child : this.childrenList) {
                        jsonWriter(child, currentArray);
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: "
                        + this.strategy);
            }

            currentObject.put("children", currentArray);
            this.jsonArray.add(currentObject);

            if (annualChangesPosition >= santa.getNumberOfYears()) {
                break;
            }

            for (Child child : this.childrenList) {
                child.setAge(child.getAge() + 1);
            }

            AnualChangesData currentPosition = annualChangesDataList.get(annualChangesPosition);
            this.santa.setSantaBudget(currentPosition.getNewSantaBudget());

            for (ChildData currentChild : currentPosition.getNewChildren()) {
                Child current = new Child(
                        currentChild.getId(), currentChild.getLastName(),
                        currentChild.getFirstName(), currentChild.getAge(),
                        currentChild.getCity(), currentChild.getNiceScore(),
                        currentChild.getGiftsPreferences(), currentChild.getElf(),
                        currentChild.getNiceScoreBonus()
                );

                current.updateNiceScoreList();
                childrenList.add(current);
            }

            for (GiftData currentGift : currentPosition.getNewGifts()) {
                initialData.getGiftData().add(currentGift);
            }

            for (ChildrenUpdatesData childrenUpdatesData : currentPosition.getChildrenUpdates()) {
                for (Child child : childrenList) {
                    if (child.getId().equals(childrenUpdatesData.getId())) {
                        if (childrenUpdatesData.getNiceScore() != Double.MAX_VALUE) {
                            child.setNiceScore(childrenUpdatesData.getNiceScore());
                            child.updateNiceScoreList();
                        }

                        ArrayList<Category> newGiftsPreferences
                                = childrenUpdatesData.getGiftsPreferences();

                        Set<Category> uniqueSet = new LinkedHashSet<>(newGiftsPreferences);
                        newGiftsPreferences.clear();
                        newGiftsPreferences.addAll(uniqueSet);

                        child.getGiftsPreferences().removeIf(newGiftsPreferences::contains);
                        Collections.reverse(newGiftsPreferences);

                        for (Category category : newGiftsPreferences) {
                            child.getGiftsPreferences().add(0, category);
                        }

                        child.setElf(childrenUpdatesData.getElf());
                    }
                }
            }

            this.strategy = currentPosition.getNewStrategy();

            for (Child child : childrenList) {
                child.determineAgeCategory();
                child.determineAverageScore();
            }

            santa.getSumAverage(childrenList);
            santa.setBudgetUnit();

            for (Child child : childrenList) {
                child.getBudget(santa.getBudgetUnit());
                child.blackPinkElf();
            }

            numberOfRounds--;
            annualChangesPosition++;
        }
    }

    /**
     * Function used to distribute the gifts for each child depending
     * on their preferences, on the prices and the budget. Also used
     * to write in the output file the answer
     * @param currentChild the current child we are distributing the
     *                     gifts for
     */
    public void childDistribution(final Child currentChild) {
        Double currentBudget = currentChild.getBudget();

        for (int i = 0; i < currentChild.getGiftsPreferences().size(); i++) {
            Double minimumPrice = Double.MAX_VALUE;
            GiftData currentGift = null;

            for (int j = 0; j < this.initialData.getGiftData().size(); j++) {
                if (this.initialData.getGiftData().get(j)
                        .getCategory().equals(currentChild.getGiftsPreferences().get(i))) {
                    if (this.initialData.getGiftData()
                            .get(j).getPrice() < minimumPrice && this.initialData.getGiftData()
                            .get(j).getPrice() < currentBudget
                            && this.initialData.getGiftData().get(j).getQuantity() > 0) {
                        minimumPrice = this.initialData.getGiftData().get(j).getPrice();
                        currentGift = this.initialData.getGiftData().get(j);
                    }
                }
            }

            if (minimumPrice != Double.MAX_VALUE) {
                currentBudget -= minimumPrice;
                currentChild.getReceivedGifts().add(currentGift);

                for (int j = 0; j < this.initialData.getGiftData().size(); j++) {
                    if (this.initialData.getGiftData().get(j).equals(currentGift)) {
                        this.initialData.getGiftData().get(j).setQuantity(this.initialData
                                .getGiftData().get(j).getQuantity() - 1);
                    }
                }
            }
        }
    }

    /**
     * Function that writes a child in the JSONArray
     * @param currentChild the child that is going to be
     *                     written
     * @param currentArray the array in which the child is
     *                     going to be written
     */
    @SuppressWarnings("unchecked")
    public void jsonWriter(final Child currentChild, final JSONArray currentArray) {
        JSONObject childrenInfo = new JSONObject();
        childrenInfo.put("id", currentChild.getId());
        childrenInfo.put("lastName", currentChild.getLastName());
        childrenInfo.put("firstName", currentChild.getFirstName());
        childrenInfo.put("city", cityToString(currentChild.getCity()));
        childrenInfo.put("age", currentChild.getAge());

        JSONArray giftsArray = new JSONArray();
        for (Category category : currentChild.getGiftsPreferences()) {
            giftsArray.add(categoryToString(category));
        }

        childrenInfo.put("giftsPreferences", giftsArray);
        childrenInfo.put("averageScore", currentChild.getAverageScore());

        JSONArray niceScoreArray = new JSONArray();
        niceScoreArray.addAll(currentChild.getNiceScoreList());

        childrenInfo.put("niceScoreHistory", niceScoreArray);
        childrenInfo.put("assignedBudget", currentChild.getBudget());

        JSONArray receivedGiftsArray = new JSONArray();
        for (GiftData gift : currentChild.getReceivedGifts()) {
            JSONObject giftObject = new JSONObject();
            giftObject.put("productName", gift.getProductName());
            giftObject.put("price", gift.getPrice());
            giftObject.put("category", categoryToString(gift.getCategory()));
            receivedGiftsArray.add(giftObject);
        }

        childrenInfo.put("receivedGifts", receivedGiftsArray);
        currentArray.add(childrenInfo);
    }

    /**
     * The function that applies the effect of the
     * yellow Elf at the end of the gift distribution
     * @param child the child on with the efect is
     *              applied
     */
    public void yellowElf(final Child child) {
        Double minimumPrice = Double.MAX_VALUE;
        GiftData currentGift = null;

        if (child.getElf().equals("yellow") && child.getReceivedGifts().isEmpty()) {
            if (!child.getGiftsPreferences().isEmpty()) {
                for (int i = 0; i < initialData.getGiftData().size(); i++) {
                    if (initialData.getGiftData().get(i)
                            .getCategory().equals(child.getGiftsPreferences().get(0))) {
                        if (initialData.getGiftData().get(i).getPrice() < minimumPrice) {
                            minimumPrice = initialData.getGiftData().get(i).getPrice();
                            currentGift = initialData.getGiftData().get(i);
                        }
                    }
                }

                if (minimumPrice != Double.MAX_VALUE  && currentGift.getQuantity() > 0) {
                    child.getReceivedGifts().add(currentGift);

                    for (int j = 0; j < this.initialData.getGiftData().size(); j++) {
                        if (this.initialData.getGiftData().get(j).equals(currentGift)) {
                            this.initialData.getGiftData().get(j).setQuantity(this.initialData
                                    .getGiftData().get(j).getQuantity() - 1);
                        }
                    }
                }

            }
        }
    }

    /**
     * Function used to transform from a City enum type to a string
     * in order to be able to use the strings to write in JSON
     * @param city the city to be transformed
     * @return the string with the name of the city
     */
    public static String cityToString(final Cities city) {
        return switch (city) {
            case BUCURESTI -> "Bucuresti";
            case CONSTANTA -> "Constanta";
            case BUZAU -> "Buzau";
            case TIMISOARA -> "Timisoara";
            case CLUJ -> "Cluj-Napoca";
            case IASI -> "Iasi";
            case CRAIOVA -> "Craiova";
            case BRASOV -> "Brasov";
            case BRAILA -> "Braila";
            case ORADEA -> "Oradea";
        };
    }

    /**
     * Function used to transform from a Category enum type to a string
     * in order to be able to use the strings to write in JSON
     * @param category the category to be transformed
     * @return the string with the name of the category
     */
    public static String categoryToString(final Category category) {
        return switch (category) {
            case BOARD_GAMES -> "Board Games";
            case BOOKS -> "Books";
            case CLOTHES -> "Clothes";
            case SWEETS -> "Sweets";
            case TECHNOLOGY -> "Technology";
            case TOYS -> "Toys";
        };
    }
    public InitialData getInitialData() {
        return initialData;
    }
}
