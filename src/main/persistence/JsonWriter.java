package persistence;

import model.Album;
import org.json.JSONObject;

import java.io.*;

/**
 * Class contains methods for working with the JSON files.
 *
 * @author Naumov A.M
 * @version 1.0
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    /**
     * Constructs writer to write to destination file
     */
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    /**
     * Opens writer. throws FileNotFoundException if destination file cannot
     * be opened for writing
     */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    /**
     * Writes JSON representation of album to file
     */
    public void write(Album alb) {
        JSONObject json = alb.toJson();
        saveToFile(json.toString(TAB));
    }

    /**
     * Closes writer
     */
    public void close() {
        writer.close();
    }

    /**
     * Writes string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
