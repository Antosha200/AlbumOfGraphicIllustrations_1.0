package ui;

import java.io.FileNotFoundException;

public class UIMain {
    public static void main(String[] args) {
        try {
            new UIApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
