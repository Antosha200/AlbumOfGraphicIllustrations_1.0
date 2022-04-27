package model;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Array;
import java.util.ArrayList;

/**
 * Tests for the Album class
 */
class AlbumTest {
    //private ArrayList<Photo> photos;

    Album albumGetIndex;
    Album albumAddPhoto;
    Album albumRemovePhoto;
    Album albumNextPhoto;
    Album albumPrevPhoto;
    Album albumSizeAlbum;
    Album albumGetPhotoByName;
    Album albumToJson;
    Album albumPhotosToJson;

    Photo p1;
    Photo p2;
    Photo p3;
    Photo p4;

    @BeforeEach
    void runBefore() {
        albumGetIndex = new Album();
        albumAddPhoto = new Album();
        albumRemovePhoto = new Album();
        albumNextPhoto = new Album();
        albumPrevPhoto = new Album();
        albumSizeAlbum = new Album();
        albumGetPhotoByName = new Album();
        albumToJson = new Album();
        albumPhotosToJson = new Album();


        p1 = new Photo("Apples");
        p2 = new Photo("Bananas");
        p3 = new Photo("Cherries");
    }

    @Test
    void testGetIndex() {
        albumGetIndex.addPhoto(p1);
        albumGetIndex.addPhoto(p2);
        albumGetIndex.addPhoto(p3);
        assertEquals(0, albumGetIndex.getIndex(p1));
        assertEquals(1, albumGetIndex.getIndex(p2));
        assertEquals(2, albumGetIndex.getIndex(p3));
    }

    @Test
    void testAddPhoto() {
        assertEquals(0, albumAddPhoto.sizeAlbum());
        albumAddPhoto.addPhoto(p1);
        assertEquals(1, albumAddPhoto.sizeAlbum());
    }

    @Test
    void testRemovePhoto() {
        assertFalse(albumRemovePhoto.removePhoto(p1));
        albumRemovePhoto.addPhoto(p1);
        albumRemovePhoto.addPhoto(p2);
        albumRemovePhoto.removePhoto(p1);
        assertEquals(1, albumRemovePhoto.sizeAlbum());
        assertFalse(albumRemovePhoto.removePhoto(p4));
    }

    @Test
    void testRemoveAllPhotos() {
        assertFalse(albumRemovePhoto.removeAll());
        albumRemovePhoto.addPhoto(p1);
        albumRemovePhoto.addPhoto(p2);
        assertTrue(albumRemovePhoto.removeAll());
        assertEquals(0, albumRemovePhoto.sizeAlbum());
    }

    @Test
    void testNextPhoto() {
        albumNextPhoto.addPhoto(p1);
        albumNextPhoto.addPhoto(p2);
        albumNextPhoto.addPhoto(p3);
        assertEquals(p2, albumNextPhoto.nextPhoto(p1));
        assertEquals(p3, albumNextPhoto.nextPhoto(p2));
        assertEquals(p3, albumNextPhoto.nextPhoto(p3));
    }

    @Test
    void testPrevPhoto() {
        albumPrevPhoto.addPhoto(p1);
        albumPrevPhoto.addPhoto(p2);
        albumPrevPhoto.addPhoto(p3);
        assertEquals(p1, albumPrevPhoto.prevPhoto(p1));
        assertEquals(p1, albumPrevPhoto.prevPhoto(p2));
        assertEquals(p2, albumPrevPhoto.prevPhoto(p3));
    }

    @Test
    void testSizeAlbum() {
        assertEquals(0, albumSizeAlbum.sizeAlbum());
        albumSizeAlbum.addPhoto(p1);
        assertEquals(1, albumSizeAlbum.sizeAlbum());
    }

    @Test
    void testGetPhotoByName() {
        assertEquals(null, albumGetPhotoByName.getPhotoByName("Cake"));
        albumGetPhotoByName.addPhoto(p1);
        assertEquals(p1, albumGetPhotoByName.getPhotoByName("Apples"));
    }

    @Test
    void testPhotosToJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(p2.toJson());
        albumPhotosToJson.addPhoto(p1);
        assertNotEquals(jsonArray, albumPhotosToJson.photosToJson());
    }

    @Test
    void testToJson() {
        albumToJson.addPhoto(p1);
        JSONObject json = new JSONObject();
        json.put("photos", albumToJson.photosToJson());
        assertNotEquals(json, albumToJson.toJson());
    }

    @Test
    void testIterator() {
        albumAddPhoto.iterator();
        for (Photo photo : albumAddPhoto) {
            try {
                photo.loadPhoto();
            } catch (Exception e) {
                fail("Exception not expected");
            }
        }
    }
}