package gui.view.frames.aboutProgramDialog;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * {@link AboutProgramDialog} is an extension of {@link JDialog} which contains
 * information about the program including a picture that partly explains its functioning
 * and list of operations that program provides with.
 *
 * @author Naumov A.M.
 * @version 1.0
 */
public class AboutProgramDialog extends JDialog {
    private final GridBagConstraints constraints;

    /**
     * Creates a dialog with added information and picture on it.
     *
     * @param parent parent {@link JFrame} of dialog. Used for centering.
     */
    public AboutProgramDialog(JFrame parent) {
        super(parent, "About program");
        constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        addProgramNameLabel();
        //addPictureLabel();
        //addInfoLabel();
        //addVersionLabel();
        addBackButton();

        getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        getContentPane().setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        pack();
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addProgramNameLabel() {
        JLabel nameLabel = new JLabel("Album of graphic illustrations");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(nameLabel, constraints);
    }

    private void addPictureLabel() {
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource("program.jpg");
        if (url == null) {
            return;
        }
        Image image = new ImageIcon(url).getImage();
        Image scaledImage = image.getScaledInstance(300, 127, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(imageLabel, constraints);
    }

    private void addInfoLabel() {
        JLabel infoLabel = new JLabel("<html>Program allows:<br>");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        constraints.gridx = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(infoLabel, constraints);
    }

    private void addVersionLabel() {
        JLabel versionLabel = new JLabel("Version 1.0.0.2021");
        versionLabel.setFont(new Font("Arial", Font.BOLD, 15));
        versionLabel.setHorizontalAlignment(JLabel.CENTER);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(versionLabel, constraints);
    }

    private void addBackButton() {
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.add(backButton);
        add(panel, constraints);
    }
}
