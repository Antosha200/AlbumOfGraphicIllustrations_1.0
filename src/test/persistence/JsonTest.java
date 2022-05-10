package persistence;

import model.Photo;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPhoto(String name, Photo photo) {
        assertEquals(name, photo.getName());
    }
}
