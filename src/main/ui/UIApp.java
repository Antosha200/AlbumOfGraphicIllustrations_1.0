package ui;

import model.Album;
import model.Photo;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UIApp {
    private static final String JSON_STORE = "./data/workroom.json";
    private Scanner input;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Album album;
    private Photo displayedPhoto;

    // EFFECTS: runs the photoAlbum application
    public UIApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        album = new Album();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPhotoAlbum();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runPhotoAlbum() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();

        System.out.println("\nWelcome to your photo album experience. Have a wonderful stay!\n");
        reminderToLoad();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                reminderToSave();
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nThank you. See you next time!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("n")) {
            doNext();
        } else if (command.equals("p")) {
            doPrevious();
        } else if (command.equals("s")) {
            doSize();
        } else if (command.equals("a")) {
            doAdd();
        } else if (command.equals("r")) {
            doRemove();
        } else if (command.equals("k")) {
            saveAlbum();
        } else if (command.equals("l")) {
            loadAlbum();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: initializes photos
    private void init() {
        Photo photo1 = new Photo("Apples");
        Photo photo2 = new Photo("Bananas");
        Photo photo3 = new Photo("Cherries");
        album = new Album();
        album.addPhoto(photo1);
        album.addPhoto(photo2);
        album.addPhoto(photo3);
        displayedPhoto = photo1;
        input = new Scanner(System.in);
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select from the following options:");
        System.out.println("\tn -> next");
        System.out.println("\tp -> previous");
        System.out.println("\ts -> size");
        System.out.println("\ta -> add");
        System.out.println("\tr -> remove");
        System.out.println("\tk -> save album to file");
        System.out.println("\tl -> load album from file");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: conduct a displays of next photo
    private void doNext() {
        if (album.sizeAlbum() >= 1) {
            System.out.print("Displaying next photo");

            displayedPhoto = album.nextPhoto(displayedPhoto);
            System.out.println("\n" + displayedPhoto.getName());

        } else {
            System.out.print("There are no photos. Please add a photo first.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a display of previous photo
    private void doPrevious() {
        if (album.sizeAlbum() >= 1) {
            System.out.print("Displaying previous photo");

            displayedPhoto = album.prevPhoto(displayedPhoto);
            System.out.println("\n" + displayedPhoto.getName());

        } else {
            System.out.print("There are no photos. Please add a photo first.");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a return of number of photos in album
    private void doSize() {
        System.out.print("There are " + album.sizeAlbum() + " photos in the album");
    }

    // MODIFIES: this
    // EFFECTS: conduct addition of a photo to album
    private void doAdd() {
        System.out.print("Adding a new photo. Enter new photo name: ");
        String newName = input.next();

        if (newName.length() >= 1) {
            Photo photo3 = new Photo(newName);
            album.addPhoto(photo3);
        }
    }

    // MODIFIES: this
    // EFFECTS: conduct removal of a photo from album
    private void doRemove() {
        System.out.print("Removing an old photo. Enter old photo name: ");
        String oldName = input.next();

        if (album.removePhoto(album.getPhotoByName(oldName))) {
            System.out.print("Successfully removed photo from album.");
        } else {
            System.out.print("Photo does not exist in album.");
        }
    }


    // EFFECTS: saves the album to file
    private void saveAlbum() {
        try {
            jsonWriter.open();
            jsonWriter.write(album);
            jsonWriter.close();
            System.out.println("Saved album to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads album from file
    private void loadAlbum() {
        try {
            album = jsonReader.read();
            System.out.println("Loaded from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void reminderToSave() {
        System.out.println("Would you like to save your album? Enter y/n");
        String answer = input.next();

        if (answer.equals("y")) {
            System.out.println("Preparing to save...");
            saveAlbum();
        } else if (answer.equals("n")) {
            System.out.println("Continuing without saving...");
        } else {
            System.out.println("Incorrect input.");
            reminderToSave();
        }
    }

    private void reminderToLoad() {
        System.out.println("Would you like to load a saved album? Enter y/n");
        String answer = input.next();

        if (answer.equals("y")) {
            System.out.println("Preparing to load...");
            loadAlbum();
        } else if (answer.equals("n")) {
            System.out.println("Starting a preset album...");
        } else {
            System.out.println("Incorrect input.");
            reminderToLoad();
        }
    }
}
