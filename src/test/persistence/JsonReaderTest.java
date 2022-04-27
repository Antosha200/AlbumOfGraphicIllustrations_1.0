package persistence;

import model.Album;
import model.Photo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * JsonReaderTest
 * Modelled based on TellerApp https://github.students.cs.ubc.ca/CPSC210/TellerApp
 */

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Album alb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyAlbum() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyAlbum.json");
        try {
            Album alb = reader.read();
            assertEquals(0, alb.sizeAlbum());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralAlbum() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralAlbum.json");
        try {
            Album alb = reader.read();
            ArrayList<Photo> album = alb.getPhotos();
            assertEquals(2, album.size());
            checkPhoto("Apples", album.get(0));
            checkPhoto("Bananas",album.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

