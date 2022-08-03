package readersandwriters;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public final class Writer {
    private final FileWriter file;

    public Writer(final String path) throws IOException {
        this.file = new FileWriter(path);
    }

    /**
     * writes to the file and close it
     *
     * @param array of JSON
     */
    public void closeJSON(final JSONObject array) {
        try {
            file.write(array.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
