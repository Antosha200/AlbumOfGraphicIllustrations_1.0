package persistence;

import model.Photo;
import model.Album;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JsonWriterTest
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Album alb = new Album();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyAlbum() {
        try {
            Album alb = new Album();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyAlbum.json");
            writer.open();
            writer.write(alb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyAlbum.json");
            alb = reader.read();
            assertEquals(0, alb.sizeAlbum());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralAlbum() {
        try {
            Album alb = new Album();
            alb.addPhoto(new Photo("Bananas"));
            alb.addPhoto(new Photo("Cherries"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralAlbum.json");
            writer.open();
            writer.write(alb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralAlbum.json");
            alb = reader.read();
            ArrayList<Photo> album = alb.getPhotos();
            assertEquals(2, album.size());
            checkPhoto("Bananas", album.get(0));
            checkPhoto("Cherries", album.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}