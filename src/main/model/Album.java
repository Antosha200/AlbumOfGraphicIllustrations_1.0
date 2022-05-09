package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class contains methods for working with the album entity.
 * Deleting, receiving and formatting in JSON format.
 *
 * @author Naumov A.M
 * @version 1.0
 */
public class Album implements Writable, Iterable<Photo> {
    private final ArrayList<Photo> photos;
    private String name;

    /**
     * Initializes each newly created Album.
     */
    public Album() {
        photos = new ArrayList<>();
    }

    /**
     * Returns a list of all the photos in album.
     */
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    /**
     * Adds a photo to album
     */
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    /**
     * Removes the photo from album
     */
    public Boolean removePhoto(Photo photo) {
        if (photos.contains(photo)) {
            this.photos.remove(photo);
            return true;
        }
        return false;
    }

    /**
     * Removes all photos from album
     */
    public Boolean removeAll() {
        if (!photos.isEmpty()) {
            photos.clear();
            return true;
        }
        return false;
    }

    /**
     * Returns photo index
     */
    public Integer getIndex(Photo photo) {
        return photos.indexOf(photo);
    }

    /**
     * Returns next photo index
     */
    public Integer nextIndex(Photo photo) {
        if (photos.indexOf(photo) < getPhotos().size() - 1) {
            return photos.indexOf(photo) + 1;
        } else {
            return photos.indexOf(photo);
        }
    }

    /**
     * Return previous photo index
     */
    public Integer prevIndex(Photo photo) {
        if (photos.indexOf(photo) >= 1) {
            return photos.indexOf(photo) - 1;
        } else {
            return 0;
            //return album.indexOf(photo);
        }
    }

    /**
     * Shows next photo
     */
    public Photo nextPhoto(Photo photo) {
        return photos.get(nextIndex(photo));
    }

    /**
     * Shows previous photo
     */
    public Photo prevPhoto(Photo photo) {
        return photos.get(prevIndex(photo));
    }

    /**
     * Returns number of photos in album
     */
    public Integer sizeAlbum() {
        return photos.size();
    }

    /**
     * Returns a photo by name
     */
    public Photo getPhotoByName(String name) {
        Photo p = new Photo(name);
        if (photos.contains(p)) {
            return photos.get(photos.indexOf(p));
        } else {
            return null;
        }
    }

    /**
     * Returns object in JSON format
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("photos", photosToJson());
        return json;
    }

    /**
     * Returns photos in album as a JSON array
     */
    public JSONArray photosToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Photo p : photos) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Photo> iterator() {
        return photos.iterator();
    }
}
