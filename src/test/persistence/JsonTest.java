package persistence;

import model.Photo;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JsonTest
 * Modelled based on JsonSerializationDemo https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class JsonTest {
    protected void checkPhoto(String name, Photo photo) {
        assertEquals(name, photo.getName());
    }
}
