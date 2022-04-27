package persistence;

import model.Album;
import model.Photo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Album read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAlbum(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses album from JSON object and returns it
    private Album parseAlbum(JSONObject jsonObject) {
//        String name = jsonObject.getString("name");
        Album alb = new Album();
        addPhotos(alb, jsonObject);
        return alb;
    }

    // MODIFIES: alb
    // EFFECTS: parses photos from JSON object and adds them to album
    private void addPhotos(Album alb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("photos");
        for (Object json : jsonArray) {
            JSONObject nextPhoto = (JSONObject) json;
            addPhoto(alb, nextPhoto);
        }
    }

    // MODIFIES: alb
    // EFFECTS: parses photo from JSON object and adds it to album
    private void addPhoto(Album alb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Photo photo = new Photo(name);
        alb.addPhoto(photo);
    }
}
