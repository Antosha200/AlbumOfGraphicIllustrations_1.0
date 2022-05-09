package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;

public class Album implements Writable, Iterable<Photo> {
    private final ArrayList<Photo> photos;
    private String name;

    // EFFECTS: initializes each newly created Album as an empty album
    public Album() {
        photos = new ArrayList<>();
    }

    // EFFECTS: returns a list of all the photos in album.
    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    // EFFECTS: add a photo to album
    public void addPhoto(Photo photo) {
        this.photos.add(photo);
    }

    // EFFECTS: remove a photo from album
    public Boolean removePhoto(Photo photo) {
        if (photos.contains(photo)) {
            this.photos.remove(photo);
            return true;
        }
        return false;
    }

    // EFFECTS: remove all photos from album
    public Boolean removeAll() {
        if (!photos.isEmpty()) {
            photos.clear();
            return true;
        }
        return false;
    }

    // EFFECTS: return photo index
    public Integer getIndex(Photo photo) {
        return photos.indexOf(photo);
    }

    // EFFECTS: return next photo index
    public Integer nextIndex(Photo photo) {
        if (photos.indexOf(photo) < getPhotos().size() - 1) {
            return photos.indexOf(photo) + 1;
        } else {
            return photos.indexOf(photo);
        }
    }

    // REQUIRES: index cannot be negative
    // EFFECTS: return previous photo index
    public Integer prevIndex(Photo photo) {
        if (photos.indexOf(photo) >= 1) {
            return photos.indexOf(photo) - 1;
        } else {
            return 0;
            //return album.indexOf(photo);
        }
    }

    // EFFECTS: show next photo
    public Photo nextPhoto(Photo photo) {
        return photos.get(nextIndex(photo));
    }

    // EFFECTS: show previous photo
    public Photo prevPhoto(Photo photo) {
        return photos.get(prevIndex(photo));
    }

    // EFFECTS: return number of photos in album
    public Integer sizeAlbum() {
        return photos.size();
    }

    // REQUIRES: a photo with same name must be in album
    // EFFECTS: return a photo by name
    public Photo getPhotoByName(String name) {
        Photo p = new Photo(name);
        if (photos.contains(p)) {
            return photos.get(photos.indexOf(p));
        } else {
            return null;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("photos", photosToJson());
        return json;
    }

    // EFFECTS: returns photos in this album as a JSON array
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
