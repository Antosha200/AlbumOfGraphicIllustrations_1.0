package persistence;

import model.Album;
import model.Photo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/**
 * Class contains methods for working with the JSON files.
 *
 * @author Naumov A.M
 * @version 1.0
 */
public class JsonReader {
    private String source;

    /**
     * Constructs reader to read from source file
     */
    public JsonReader(String source) {
        this.source = source;
    }

    /**
     * Reads workroom from file and returns it
     * throws IOException if an error occurs reading data from file
     */
    public Album read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAlbum(jsonObject);
    }

    /**
     * Reads source file as string and returns it
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    /**
     * Parses album from JSON object and returns it
     */
    private Album parseAlbum(JSONObject jsonObject) {
        ;
        Album alb = new Album();
        addPhotos(alb, jsonObject);
        return alb;
    }

    /**
     * Parses photos from JSON object and adds them to album
     */
    private void addPhotos(Album alb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("photos");
        for (Object json : jsonArray) {
            JSONObject nextPhoto = (JSONObject) json;
            addPhoto(alb, nextPhoto);
        }
    }

    /**
     * Parses photo from JSON object and adds it to album
     */
    private void addPhoto(Album alb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Photo photo = new Photo(name);
        alb.addPhoto(photo);
    }
}
