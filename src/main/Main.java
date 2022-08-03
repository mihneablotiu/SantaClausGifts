package main;
import checker.Checker;
import children.Child;
import common.Constants;
import gifts.GiftsDatabase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import readersandwriters.ChildData;
import readersandwriters.Input;
import readersandwriters.InputLoader;
import readersandwriters.Writer;
import santa.Santa;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }

    /**
     * This method is used to call the checker which calculates the score
     * and also to create the output directory and files in which we write
     * the answer
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);

        Path path = Paths.get("output");
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File("output");
        deleteFile(outputDirectory.listFiles());

        int fileNumber = 1;

        File[] array = Objects.requireNonNull(directory.listFiles());

        Arrays.sort(array, (o1, o2) -> {
            String file1 = o1.getName();
            String file2 = o2.getName();

            if (file1.contains("test") && file2.contains("test")) {
                file1 = file1.split(".json")[0];
                file2 = file2.split(".json")[0];
                file1 = file1.replaceFirst("test", "");
                file2 = file2.replaceFirst("test", "");
                return Integer.compare(Integer.parseInt(file1), Integer.parseInt(file2));
            }

            return 0;
        });

        for (File file : array) {
            if (file.getName().contains("test")) {
                String filepath = Constants.OUTPUT_PATH + fileNumber + Constants.FILE_EXTENSION;
                fileNumber++;
                File out = new File(filepath);
                boolean isCreated = out.createNewFile();

                if (isCreated) {
                    action(file.getAbsolutePath(), filepath);
                }
            }
        }

        Checker.calculateScore();
    }

    /**
     * Function used to delete all the files from a
     * directory
     * @param directory the directory from which
     *                  we want to delete all the
     *                  files
     */
    public static void deleteFile(final File[] directory) {
        if (directory != null) {
            for (File file : directory) {
                if (!file.delete()) {
                    System.out.println("nu s-a șters fișierul");
                }
            }
        }
    }

    /**
     * The function that effectively does the action for each
     * input test. Reads the input, calls the gifts distribution
     * and writes the output
     * @param filepath1 the file from which we are reading the
     *                  input
     * @param filepath2 the file in which we are writing the
     *                  output
     * @throws IOException in case of exceptions to reading / writing
     */
    @SuppressWarnings("unchecked")
    public static void action(final String filepath1, final String filepath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filepath1);
        Input input = inputLoader.readData();

        ArrayList<ChildData> newChildDatalist
                = new ArrayList<>(input.getInitialData().getChildData());
        ArrayList<Child> newChildList = new ArrayList<>();

        for (ChildData child : newChildDatalist) {
            Child newChild = new Child(child.getId(), child.getLastName(),
                    child.getFirstName(), child.getAge(), child.getCity(),
                    child.getNiceScore(), child.getGiftsPreferences(),
                    child.getElf(), child.getNiceScoreBonus());
            newChildList.add(newChild);
            newChild.determineAgeCategory();
            newChild.updateNiceScoreList();
            newChild.determineAverageScore();
        }

        Santa santa = new Santa(input.getSantaGeneralData().getNumberOfYears(),
                input.getSantaGeneralData().getSantaBudget());
        santa.getSumAverage(newChildList);
        santa.setBudgetUnit();

        for (Child child : newChildList) {
            child.getBudget(santa.getBudgetUnit());
            child.blackPinkElf();
        }

        Writer fileWriter = new Writer(filepath2);
        JSONArray arrayResult = new JSONArray();

        GiftsDatabase giftsDatabase = new GiftsDatabase(newChildList,
                santa, input.getInitialData(), input.getAnualChangesData(), arrayResult, "id");
        giftsDatabase.giftsDistribution();

        JSONObject finalObject = new JSONObject();
        finalObject.put("annualChildren", arrayResult);

        fileWriter.closeJSON(finalObject);
    }
}
