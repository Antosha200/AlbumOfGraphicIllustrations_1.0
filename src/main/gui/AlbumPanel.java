package gui;

import View.frames.mainFrame.MainFrame;
import model.Album;
import model.Photo;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class AlbumPanel extends JPanel {

    // Main album
    private Album album = new Album();
    private Album albumJson = new Album();

    // Json parts and storing
    private static final String JSON_STORE = "./photos/workroom.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // UI components
    private PhotoPanel photoPanel = new PhotoPanel();

    //private BrowsePanel browsePanel = new BrowsePanel();
    private PhotoFileChooser photoFileChooser = new PhotoFileChooser();

    private MainFrame frame;

    /**
     * MODIFIES: this
     * EFFECTS: Create and display the main window.
     */
    public AlbumPanel(MainFrame frame) {

        this.frame = frame;
        // Initialize Json parts
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(
                                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), photoPanel),
                        BorderLayout.CENTER);

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                windowCloseMethod(frame);
            }

            @Override
            public void windowOpened(WindowEvent e) {
                windowOpenMethod(frame);
            }
        });
    }

    /**
     * EFFECTS: Display a confirmation box for loading album upon starting app.
     */
    private void windowOpenMethod(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to load a previous album?");
        if (result == JOptionPane.OK_OPTION) {
            photoPanel.loadMethod();
        }
    }

    /**
     * EFFECTS: Display a confirmation box for saving album upon closing app.
     */
    private void windowCloseMethod(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to save?");
        if (result == JOptionPane.OK_OPTION) {
            photoPanel.saveMethod();
            setVisible(false);
            frame.dispose();
        } else if (result == JOptionPane.NO_OPTION) {
            setVisible(false);
            frame.dispose();
        }
    }

    /**
     * MODIFIES: this
     * EFFECTS: Add some test data (default photos) to the album.
     */
    private void populateAlbum() {

        try {
            Photo apple = new Photo("apple");
            Photo banana = new Photo("banana2553");
            Photo cherry = new Photo("cherry365");
            apple.loadPhoto();
            banana.loadPhoto();
            cherry.loadPhoto();
            album.addPhoto(apple);
            album.addPhoto(banana);
            album.addPhoto(cherry);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * EFFECTS: Display an error message box for adding a photo.
     */
    private void errorPopup(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * EFFECTS: Display a confirmation box for removing a photo.
     */
    private boolean confirmPopup(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm action",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }

    /**
     * REQUIRES: Photo can be removed.
     * MODIFIES: this
     * EFFECTS: Cleanly removes a photo from the album.
     */
    public void removePhoto(Photo photo) {
        try {
            album.removePhoto(photo);
        } catch (Exception e) {
            System.out.println("Photo cannot be removed");
        }
    }

    /**
     * The panel for displaying a photo and buttons
     */
    public class PhotoPanel extends JPanel {

        private Photo selectedPhoto;
        private Photo displayedPhoto;

        private JPanel imagePanel = new JPanel();
        private JPanel infoPanel = new JPanel();

        private JLabel infoLabel = new JLabel();

        public PhotoPanel() {
            super(new BorderLayout());

            JScrollPane scrollPane = new JScrollPane(imagePanel);
            scrollPane.setBackground(Color.WHITE);
            add(scrollPane, BorderLayout.CENTER);

            JButton btnRemove = getBtnRemove();
            JButton btnAdd = getBtnAdd();
            JButton btnNext = getBtnNext(album);
            JButton btnPrev = getBtnPrev(album);
            JButton btnSize = getBtnSize(album);
            JButton btnSave = getBtnSave();
            JButton btnLoad = getBtnLoad();

            //Add the infoPanel label
            infoPanel.add(infoLabel);

            //Add the components to the panel
            addComponents(btnRemove, btnAdd, btnNext, btnPrev, btnSize, btnSave, btnLoad);


            // Center everything in the infoPanel
            for (Component c : infoPanel.getComponents()) {
                ((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT);
                ((JComponent) c).setAlignmentY(Component.CENTER_ALIGNMENT);
            }
            infoPanel.setPreferredSize(new Dimension(150, 200));
            infoPanel.setBackground(Color.white);
            add(infoPanel, BorderLayout.WEST);
        }

        /**
         * MODIFIES: this
         * EFFECTS: Add all of the button components.
         */
        private void addComponents(JButton btnRemove, JButton btnAdd,
                                   JButton btnNext, JButton btnPrev,
                                   JButton btnSize, JButton btnSave,
                                   JButton btnLoad) {

            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            // Add all the buttons to the side
            infoPanel.add(btnNext);
            infoPanel.add(btnPrev);
            infoPanel.add(btnAdd);
            infoPanel.add(btnRemove);
            infoPanel.add(btnSize);
            infoPanel.add(btnSave);
            infoPanel.add(btnLoad);
        }

        /**
         * EFFECTS: Create a new button that loads a saved album.
         */
        private JButton getBtnLoad() {
            JButton btnLoad = new JButton("Load album");
            btnLoad.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadMethod();
                }
            });
            return btnLoad;
        }

        /**
         * MODIFIES: this
         * EFFECTS: Loads a saved album.
         */
        private void loadMethod() {
            try {
                album.removeAll();
                albumJson = jsonReader.read();
                for (Photo photo : albumJson) {
                    photo.loadPhoto();
                    System.out.print("There are " + albumJson.sizeAlbum() + " photos in JSON\n");
                    album.addPhoto(photo);
                    System.out.print("There are " + album.sizeAlbum() + " photos\n");
                }
                System.out.println("Loaded from" + JSON_STORE);
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }

        /**
         * EFFECTS: Create a new button that saves the current album.
         */
        private JButton getBtnSave() {
            JButton btnSave = new JButton("Save album");
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    saveMethod();
                }
            });
            return btnSave;
        }

        /**
         * EFFECTS: Saves the current album.
         */
        private void saveMethod() {
            try {
                jsonWriter.open();
                jsonWriter.write(album);
                jsonWriter.close();
                System.out.println("Saved album to" + JSON_STORE);
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }

        /**
         * EFFECTS: Create a new button that returns the album size.
         */
        private JButton getBtnSize(Album album) {
            JButton btnSize = new JButton("Album size");
            btnSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateSize(album);
                    System.out.print("There are " + album.sizeAlbum());
                }
            });
            return btnSize;
        }

        /**
         * EFFECTS: Create a new button that displays the previous photo.
         */
        private JButton getBtnPrev(Album album) {
            JButton btnPrev = new JButton("Previous photo");
            btnPrev.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayedPhoto = album.prevPhoto(displayedPhoto);
                    selectPhoto(displayedPhoto);
                }
            });
            return btnPrev;
        }

        /**
         * EFFECTS: Create a new button that displays the next photo.
         */
        private JButton getBtnNext(Album album) {
            JButton btnNext = new JButton("Next photo");
            btnNext.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayedPhoto = album.nextPhoto(displayedPhoto);
                    selectPhoto(displayedPhoto);
                }
            });
            return btnNext;
        }

        /**
         * REQUIRES: Photo is valid
         * EFFECTS: Create a new button that adds a new photo.
         */
        private JButton getBtnAdd() {
            JButton btnAdd = new JButton("Add photo");
            btnAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    photoFileChooser.showAddPhotoDialog();
                }
            });
            return btnAdd;
        }

        /**
         * EFFECTS: Create a new button that removes a photo.
         */
        private JButton getBtnRemove() {
            JButton btnRemove = new JButton("Delete photo");
            btnRemove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!(selectedPhoto == null) && album.sizeAlbum() > 1) {
                        if (confirmPopup("Remove photo " + selectedPhoto.getName() + "?")) {
                            removePhoto(selectedPhoto);
                        }
                    }
                }
            });
            return btnRemove;
        }

        /**
         * REQUIRES: Album has photos
         * EFFECTS: Selects the current photo to be displayed
         */
        private void selectPhoto(Photo photo) {
            selectedPhoto = photo;

            // Un-focus the description text area
            imagePanel.requestFocusInWindow();

            // Add the image
            imagePanel.removeAll();
            if (photo != null) {
                imagePanel.add(new JLabel(new ImageIcon(photo.getImage())));
            } else {
                imagePanel.add(new JLabel("No photo selected."));
            }

            repaint();
            revalidate();
        }

        /**
         * EFFECTS: Display the updated size of album on top of the buttons.
         */
        private void updateSize(Album album) {
            infoLabel.setText("There are " + album.sizeAlbum() + " photos");
        }
    }

    /**
     * The JFileChooser for selecting photo files
     */
    private class PhotoFileChooser extends JFileChooser {

        public PhotoFileChooser() {
            setMultiSelectionEnabled(true);
            setAcceptAllFileFilterUsed(false);
            setApproveButtonText("Add Photos");

            setFileFilter(new FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg");
                }

                @Override
                public String getDescription() {
                    return "JPEG images (*.jpg, *.jpeg)";
                }
            });
        }

        /**
         * EFFECTS: Show the dialog to add a photo to the library
         */
        public void showAddPhotoDialog() {

            if (photoFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                for (File file : getSelectedFiles()) {
                    try {
                        Photo photo = new Photo(importPhotoFile(file));
                        photo.loadPhoto();

                        album.addPhoto(photo);
                    } catch (IOException e) {
                        errorPopup("Photo " + file.getPath() + " not found.");
                    }
                }
            }
        }

        /**
         * EFFECTS: Given any JPEG image file, returns a name suitable for passing to the Photo constructor.
         */
        private String importPhotoFile(File file) throws IOException {
            String name = file.getName().substring(0,
                    file.getName().lastIndexOf('.'));

            // If the file isn't in the photos folder with the expected
            // filename, copy it there
            if (!file.getCanonicalPath().equals(
                    new File("photos" + System.getProperty("file.separator")
                            + name + ".jpg").getCanonicalPath())) {

                // append a number that depends on the whole path, to reduce
                // collisions
                name += file.getCanonicalPath().hashCode() % 5000 + 5000;
                File dest = new File("photos"
                        + System.getProperty("file.separator") + name + ".jpg");

                if (!dest.exists()) {
                    InputStream in = new FileInputStream(file);
                    OutputStream out = new FileOutputStream(dest);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }
            return name;
        }
    }
}
