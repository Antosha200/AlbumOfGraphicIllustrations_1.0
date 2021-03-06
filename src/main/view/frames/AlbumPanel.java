package view.frames;

import view.frames.mainFrame.MainFrame;
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
import java.util.ArrayList;

/**
 * Class contains methods for working with panels which contains
 * displaying photo and buttons
 *
 * @author Naumov A.M
 * @version 1.0
 */
public class AlbumPanel extends JPanel {
    private Album album = new Album();
    private Album albumJson = new Album();
    private static final String JSON_STORE = "./photos/workroom.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private PhotoPanel photoPanel = new PhotoPanel();
    private PhotoFileChooser photoFileChooser = new PhotoFileChooser();
    private MainFrame frame;

    /**
     * Create and display the main window.
     */
    public AlbumPanel(MainFrame frame) {

        this.frame = frame;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        frame.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(
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
     * Display a confirmation box for loading album upon starting app.
     */
    private void windowOpenMethod(JFrame frame) {
        int result = JOptionPane.showConfirmDialog(frame, "Do you want to load a previous album?");
        if (result == JOptionPane.OK_OPTION) {
            photoPanel.loadMethod();
        }
    }

    /**
     * Display a confirmation box for saving album upon closing app.
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
     * Display an error message box for adding a photo.
     */
    private void errorPopup(String message) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display a confirmation box for removing a photo.
     */
    private boolean confirmPopup(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Confirm action",
                JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION;
    }

    /**
     * Cleanly removes a photo from the album.
     */
    public void removePhoto(Photo photo) {
        try {
            album.removePhoto(photo);
        } catch (Exception e) {
            //
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

        /**
         * Construct PhotoPanel.
         * Adds GUI elements on panel.
         */
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
            JButton btnDesc = getBtnDescription();
            JButton btnDeleteDesc = getBtnDeleteDesc();
            JButton btnShow = getBtnShowAllPhoto();
            infoPanel.add(infoLabel);
            addComponents(btnRemove, btnAdd, btnNext, btnPrev, btnSize, btnSave, btnLoad, btnDesc, btnDeleteDesc, btnShow);

            for (Component c : infoPanel.getComponents()) {
                ((JComponent) c).setAlignmentX(Component.CENTER_ALIGNMENT);
                ((JComponent) c).setAlignmentY(Component.CENTER_ALIGNMENT);
            }
            infoPanel.setPreferredSize(new Dimension(150, 200));
            infoPanel.setBackground(Color.white);
            add(infoPanel, BorderLayout.WEST);
        }

        /**
         * Adds all the buttons components.
         */
        private void addComponents(JButton btnRemove, JButton btnAdd,
                                   JButton btnNext, JButton btnPrev,
                                   JButton btnSize, JButton btnSave,
                                   JButton btnLoad, JButton btnDesc, JButton btnDeleteDesc, JButton btnShow) {

            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.add(btnNext);
            infoPanel.add(btnPrev);
            infoPanel.add(btnAdd);
            infoPanel.add(btnRemove);
            infoPanel.add(btnSize);
            infoPanel.add(btnSave);
            infoPanel.add(btnLoad);
            infoPanel.add(btnDesc);
            infoPanel.add(btnDeleteDesc);
            infoPanel.add(btnShow);
        }

        /**
         * Displaying photo gallery
         */
        private JButton getBtnShowAllPhoto() {
            JButton btnShowAllPhoto = new JButton("Show full album");
            btnShowAllPhoto.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Photo> photos = album.getPhotos();
                    JFrame frame = new JFrame("Album");
                    JPanel panel = new JPanel();
                    frame.setVisible(true);
                    frame.setSize(1200, 300);
                    for (int i = 0; i < photos.size(); i++) {
                        String line = "";
                        if (photos.get(i) != null) {
                            Photo photo = photos.get(i);
                            String fileName = ("photos/descriptions/" + photo.getName() + ".txt");
                            try {
                                BufferedReader reader;
                                reader = new BufferedReader(new FileReader(fileName));
                                line = reader.readLine();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            JLabel label = new JLabel(line);
                            panel.add((new JLabel(new ImageIcon(photo.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)))));
                            panel.add(label);
                        }
                    }
                    frame.add(panel);
                    JScrollPane scrollPane = new JScrollPane(panel);
                    frame.add(scrollPane);
                }
            });
            return btnShowAllPhoto;
        }

        /**
         * Deletes caption
         */
        private JButton getBtnDeleteDesc() {
            JButton deleteButton = new JButton("Delete description");
            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame frame = new JFrame("Confirmation");
                    frame.setVisible(true);
                    JButton yes = new JButton("Yes");
                    JButton no = new JButton("No");
                    JPanel panel = new JPanel();
                    JLabel text = new JLabel("Are you sure?");
                    panel.add(text);
                    panel.add(yes);
                    panel.add(no);
                    frame.add(panel);
                    frame.setSize(300, 150);
                    frame.setLocationRelativeTo(null);

                    yes.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            File file = new File("photos/descriptions/" + displayedPhoto.getName() + ".txt");
                            file.delete();
                            frame.setVisible(false);
                        }
                    });

                    no.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            frame.setVisible(false);
                        }
                    });

                }
            });
            return deleteButton;
        }

        /**
         * Creates a new button that loads a saved album.
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
         * Loads a saved album.
         */
        private void loadMethod() {
            try {
                album.removeAll();
                albumJson = jsonReader.read();
                for (Photo photo : albumJson) {
                    photo.loadPhoto();
                    album.addPhoto(photo);
                }
            } catch (IOException exception) {
                //
            }
        }

        /**
         * Adds caption to displayed photo
         */
        private JButton getBtnDescription() {
            JButton addDescription = new JButton("Add Description");
            JFrame frame = new JFrame("Photo Description");
            JPanel panel = new JPanel();
            JButton add = new JButton("Add");
            JButton cancel = new JButton("Cancel");
            JTextField textField = new JTextField(30);

            addDescription.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textField.setText("");
                    frame.setVisible(true);
                    panel.add(textField);
                    panel.add(add);
                    panel.add(cancel);
                    frame.add(panel);
                    frame.setLocation(100, 250);
                    frame.pack();
                }
            });
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                }
            });

            add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textField.getText() == null) {

                    } else {
                        String description = textField.getText();
                        File file = new File("photos/descriptions/" + displayedPhoto.getName() + ".txt");
                        if (!file.exists()) {
                            try {
                                file.createNewFile();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        try {
                            PrintWriter printWriter = new PrintWriter(file);
                            printWriter.println(description);
                            printWriter.close();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        frame.setVisible(false);
                    }
                }
            });
            return addDescription;
        }

        /**
         * Creates a new button that saves the current album.
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
         * Saves the current album.
         */
        private void saveMethod() {
            try {
                jsonWriter.open();
                jsonWriter.write(album);
                jsonWriter.close();
            } catch (FileNotFoundException exception) {
                //
            }
        }

        /**
         * Creates a new button that returns the album size.
         */
        private JButton getBtnSize(Album album) {
            JButton btnSize = new JButton("Album size");
            btnSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateSize(album);
                }
            });
            return btnSize;
        }

        /**
         * Creates a new button that displays the previous photo.
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
         * Creates a new button that displays the next photo.
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
         * Creates a new button that adds a new photo.
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
         * Creates a new button that removes a photo.
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
         * Selects the current photo to be displayed
         */
        private void selectPhoto(Photo photo) {
            selectedPhoto = photo;
            imagePanel.requestFocusInWindow();
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
         * Displays the updated size of album on top of the buttons.
         */
        private void updateSize(Album album) {
            infoLabel.setText("There are " + album.sizeAlbum() + " photos");
        }
    }

    /**
     * The JFileChooser for selecting photo files
     */
    private class PhotoFileChooser extends JFileChooser {

        /**
         * Construct PhotoFileChooser
         */
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
         * Shows the dialog to add a photo to the library
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
         * Gives any JPEG image file, returns a name suitable for passing to the Photo constructor.
         */
        private String importPhotoFile(File file) throws IOException {
            String name = file.getName().substring(0,
                    file.getName().lastIndexOf('.'));

            if (!file.getCanonicalPath().equals(
                    new File("photos" + System.getProperty("file.separator")
                            + name + ".jpg").getCanonicalPath())) {
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
