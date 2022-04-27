package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for the Photo class
 */
public class PhotoTest {
    Photo p1;
    Photo p2;
    Photo p3;

    // Information about where we keep the photos
    private static final String PICTURES_DIRECTORY = "photos";
    private static final String PHOTO_FILE_TYPE = ".jpg";
    private static final String PROJECT_DIRECTORY_PATH = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    @BeforeEach
    void runBefore() {
        p1 = new Photo("Apples");
        p2 = new Photo("Bananas");
        p3 = new Photo("Cherries");
    }

    @Test
    void testConstructor() {
        assertEquals("Apples", p1.getName());
    }

    @Test
    void testGetName() {
        assertEquals(p1.getName(), "Apples");
        assertEquals(p2.getName(), "Bananas");
        assertEquals(p3.getName(), "Cherries");
    }

    @Test
    void testEquals() {
        Photo p = new Photo("Apples");
        assertTrue(p1.equals(p));
        assertFalse(p2.equals(p));
    }

    @Test
    void testHashCode() {
        Photo p9;
        p9 = new Photo("Apples");
        assertEquals(p9.hashCode(), p1.hashCode());

        assertEquals(1967772824, p1.hashCode());
        assertEquals(1327314349, p2.hashCode());
        assertEquals(1615603766, p3.hashCode());
        assertEquals(1967772824, p9.hashCode());
    }

    @Test
    void testLoadIOExceptionNotExpected() {
        //not expecting an IOException
        Photo apple = new Photo("apple");

        assertNotNull(apple);
        try {
            apple.loadPhoto();
        } catch (IOException e) {
            fail("Exception not expected");
        }
        assertEquals(apple.getImage(), apple.getImage());
    }

    @Test
    void testLoadIOExceptionExpected() {
        //expecting an IOException

        assertNull(p1.getImage());
        try {
            p1.loadPhoto();
            fail("This should not have occurred at all");
        } catch (IOException e) {
            //Exception expected
        }
    }

    @Test
    void testToString() {
        assertEquals("Photo(" + p1.getName() + ")", p1.toString());

    }

    //    @Test
//    void testToJson() {
//        JSONObject json = new JSONObject();
//        String name = new String();
//
//        json.put("Apples", name);
//        assertEquals(json, p1.toJson());
//    }
}



