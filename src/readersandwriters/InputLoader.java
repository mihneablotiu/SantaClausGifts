package readersandwriters;
import enums.Category;
import enums.Cities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class InputLoader {
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * The method reads the input file
     * @return an Input object with all the information
     * needed to solve the problem
     */
    public Input readData() {
        JSONParser jsonParser = new JSONParser();

        SantaGeneralData santaGeneralData = new SantaGeneralData();
        InitialData initialData = new InitialData();
        ArrayList<AnualChangesData> anualChangesData = new ArrayList<>();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(inputPath));
            Long numberOfYears = (Long) jsonObject.get("numberOfYears");
            Long santaBudget = (Long) jsonObject.get("santaBudget");

            if (numberOfYears != null && santaBudget != null) {
                santaGeneralData.setNumberOfYears(numberOfYears.doubleValue());
                santaGeneralData.setSantaBudget(santaBudget.doubleValue());
            } else {
                System.out.println("Nu exista date generale");
            }


            JSONObject initialDataObject = (JSONObject) jsonObject.get("initialData");

            if (initialDataObject != null) {
                JSONArray children = (JSONArray) initialDataObject.get("children");
                for (Object jsonChildren : children) {
                    initialData.getChildData().add(new ChildData(
                            ((Long) ((JSONObject) jsonChildren).get("id")).intValue(),
                            (String) ((JSONObject) jsonChildren).get("lastName"),
                            (String) ((JSONObject) jsonChildren).get("firstName"),
                            ((Long) ((JSONObject) jsonChildren).get("age")).intValue(),
                            stringToCity((String) ((JSONObject) jsonChildren).get("city")),
                            ((Long) ((JSONObject) jsonChildren).get("niceScore")).doubleValue(),
                            convertJSONArray((JSONArray) ((JSONObject) jsonChildren)
                                    .get("giftsPreferences")),
                            ((Long) ((JSONObject) jsonChildren)
                                    .get("niceScoreBonus")).doubleValue(),
                            (String) ((JSONObject) jsonChildren).get("elf")
                    ));
                }

                JSONArray santaGiftsList = (JSONArray) initialDataObject.get("santaGiftsList");
                for (Object jsonGift : santaGiftsList) {
                    initialData.getGiftData().add(new GiftData(
                            ((String) ((JSONObject) jsonGift).get("productName")),
                            ((Long) ((JSONObject) jsonGift).get("price")).doubleValue(),
                            stringToCategory((String) ((JSONObject) jsonGift).get("category")),
                            ((Long) ((JSONObject) jsonGift).get("quantity")).doubleValue()
                    ));
                }
            } else {
                System.out.println("Nu exista date initiale");
            }

            JSONArray annualChanges = (JSONArray) jsonObject.get("annualChanges");

            if (annualChanges != null) {
                for (Object jsonIterator : annualChanges) {
                    String newStrategy = ((String) ((JSONObject) jsonIterator).get("strategy"));
                    Double newSantaBudged = ((Long) ((JSONObject) jsonIterator)
                            .get("newSantaBudget")).doubleValue();

                    JSONArray newGifts = (JSONArray) ((JSONObject) jsonIterator).get("newGifts");
                    ArrayList<GiftData> newGiftsList = new ArrayList<>();

                    for (Object newGiftsIterator : newGifts) {
                        newGiftsList.add(new GiftData(
                                ((String) ((JSONObject) newGiftsIterator).get("productName")),
                                ((Long) ((JSONObject) newGiftsIterator)
                                        .get("price")).doubleValue(),
                                stringToCategory((String) ((JSONObject) newGiftsIterator)
                                        .get("category")),
                                ((Long) ((JSONObject) newGiftsIterator)
                                        .get("quantity")).doubleValue()
                        ));
                    }

                    JSONArray newChildren = (JSONArray) ((JSONObject) jsonIterator)
                            .get("newChildren");
                    ArrayList<ChildData> newChildrenList = new ArrayList<>();

                    for (Object newChildrenIterator : newChildren) {
                        newChildrenList.add(new ChildData(
                                ((Long) ((JSONObject) newChildrenIterator).get("id")).intValue(),
                                (String) ((JSONObject) newChildrenIterator).get("lastName"),
                                (String) ((JSONObject) newChildrenIterator)
                                        .get("firstName"),
                                ((Long) ((JSONObject) newChildrenIterator)
                                        .get("age")).intValue(),
                                stringToCity((String) ((JSONObject) newChildrenIterator)
                                        .get("city")),
                                ((Long) ((JSONObject) newChildrenIterator)
                                        .get("niceScore")).doubleValue(),
                                convertJSONArray((JSONArray) ((JSONObject) newChildrenIterator)
                                        .get("giftsPreferences")),
                                ((Long) ((JSONObject) newChildrenIterator)
                                        .get("niceScoreBonus")).doubleValue(),
                                (String) ((JSONObject) newChildrenIterator).get("elf")
                        ));
                    }

                    JSONArray childrenUpdates = (JSONArray) ((JSONObject) jsonIterator)
                            .get("childrenUpdates");
                    ArrayList<ChildrenUpdatesData> childrenUpdatesDataArrayList = new ArrayList<>();

                    for (Object updatesIterator : childrenUpdates) {
                        if ((((JSONObject) updatesIterator).get("niceScore")) != null) {
                            childrenUpdatesDataArrayList.add(new ChildrenUpdatesData(
                                    ((Long) ((JSONObject) updatesIterator)
                                            .get("id")).intValue(),
                                    ((Long) ((JSONObject) updatesIterator)
                                            .get("niceScore")).doubleValue(),
                                    convertJSONArray((JSONArray) ((JSONObject) updatesIterator)
                                            .get("giftsPreferences")),
                                    (String) ((JSONObject) updatesIterator).get("elf")
                            ));
                        } else {
                            childrenUpdatesDataArrayList.add(new ChildrenUpdatesData(
                                    ((Long) ((JSONObject) updatesIterator).get("id")).intValue(),
                                    Double.MAX_VALUE,
                                    convertJSONArray((JSONArray) ((JSONObject) updatesIterator)
                                            .get("giftsPreferences")),
                                    (String) ((JSONObject) updatesIterator).get("elf")
                            ));
                        }
                    }

                    AnualChangesData newList = new AnualChangesData(newSantaBudged, newGiftsList,
                            newChildrenList, childrenUpdatesDataArrayList, newStrategy);
                    anualChangesData.add(newList);

                }
            } else {
                System.out.println("Nu exista schimbari anuale");
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Input(santaGeneralData, initialData, anualChangesData);
    }

    /**
     * Transforms an array of JSON's into an array of categories
     * @param array of JSONs
     * @return a list of categories
     */
    public static ArrayList<Category> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<Category> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add(stringToCategory((String) object));
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Method used to transform a string into a city
     * from the city enum
     * @param city the string to be transformed
     * @return the corespondent from the Cities enum
     */
    public static Cities stringToCity(final String city) {
        return switch (city.toLowerCase()) {
            case "bucuresti" -> Cities.BUCURESTI;
            case "constanta" -> Cities.CONSTANTA;
            case "buzau" -> Cities.BUZAU;
            case "timisoara" -> Cities.TIMISOARA;
            case "cluj-napoca" -> Cities.CLUJ;
            case "iasi" -> Cities.IASI;
            case "craiova" -> Cities.CRAIOVA;
            case "brasov" -> Cities.BRASOV;
            case "braila" -> Cities.BRAILA;
            case "oradea" -> Cities.ORADEA;
            default -> null;
        };
    }

    /**
     * Method used to transform a string into a category
     * from the category enum
     * @param category the string to be transformed
     * @return the category corespondent the given string
     */
    public static Category stringToCategory(final String category) {
        return switch (category.toLowerCase()) {
            case "board games" -> Category.BOARD_GAMES;
            case "books" -> Category.BOOKS;
            case "clothes" -> Category.CLOTHES;
            case "sweets" -> Category.SWEETS;
            case "technology" -> Category.TECHNOLOGY;
            case "toys" -> Category.TOYS;
            default -> null;
        };
    }
}
