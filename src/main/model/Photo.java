package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Class contains methods for working with the photo entity.
 * Getting a name, uploading a photo, converting to JSON format, as well as redefining equals() and hashCode() methods.
 *
 * @author Naumov A.M
 * @version 1.0
 */
public class Photo implements Writable {
    private final String name;
    // Information about where we keep the photos
    private static final String PICTURES_DIRECTORY = "photos";
    private static final String PHOTO_FILE_TYPE = ".jpg";
    private static final String PROJECT_DIRECTORY_PATH = System.getProperty("user.dir");
    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private Album album;
    private BufferedImage image;

    /**
     * Initializes each newly created Photo.
     */
    public Photo(String photoName) {
        this.name = photoName;
    }

    /**
     * Get Photo name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get Album Instance
     *
     * @return album
     */
    public Album getAlbum() {
        return album;
    }

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
     *
     * @return image
     */
    public Image getImage() {
        return image;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        return json;
    }
}
