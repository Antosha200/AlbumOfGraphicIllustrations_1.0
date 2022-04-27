package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Photo implements Writable {
    private final String name;

    // Information about where we keep the photos
    private static final String PICTURES_DIRECTORY = "photos";
    private static final String PHOTO_FILE_TYPE = ".jpg";
    private static final String PROJECT_DIRECTORY_PATH = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private Album album;

    private BufferedImage image;

    // REQUIRES: photoName has a non-zero length
    // EFFECTS: name is set to photoName
    public Photo(String photoName) {
        this.name = photoName;
    }

    public String getName() {
        return name;
    }

//    public Album getAlbum() {
//        return album;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        Photo photo = (Photo) o;
        return Objects.equals(name, photo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Read the photo in based on its name.
     */
    public void loadPhoto() throws IOException {
        image = ImageIO.read(new File(PROJECT_DIRECTORY_PATH
                    + FILE_SEPARATOR + PICTURES_DIRECTORY
                    + FILE_SEPARATOR + name + PHOTO_FILE_TYPE));
        if (image == null) {
            throw new IOException();
        }
    }

    @Override
    public String toString() {
        return "Photo(" + name + ")";
    }

    /**
     * Provide the photo image
     */
    public Image getImage()  {
        return image;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }
}
