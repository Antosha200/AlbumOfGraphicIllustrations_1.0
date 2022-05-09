package view.frames.aboutAuthorDialog;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * {@link AboutAuthorDialog} is an extension of {@link JDialog}
 * that contains information about author of the project.
 *
 * @author Naumov A.M.
 * @version 1.0
 */
public class AboutAuthorDialog extends JDialog {
    /**
     * Creates dialog with information about the author.
     *
     * @param parent parent {@link JFrame} of the dialog. Used for centering.
     */
    public AboutAuthorDialog(JFrame parent) {
        super(parent);
        setTitle("About author");
        JLabel imageLabel = createImageLabel();
        JLabel[] textLabels = new JLabel[]{
                imageLabel,
                new JLabel("Author"),
                new JLabel("student of group 10702319"),
                new JLabel("Naumov A.M."),
                new JLabel("anton2-2000@mail.ru")
        };
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.WHITE);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        setLayout(new BorderLayout());
        Font font = new Font(Font.DIALOG, Font.BOLD, 16);
        for (JLabel label : textLabels) {
            label.setFont(font);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            textPanel.add(label);
        }
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> dispose());
        JPanel backButtonPanel = new JPanel();
        backButtonPanel.setBackground(Color.WHITE);
        backButtonPanel.add(backButton);
        add(textPanel, BorderLayout.CENTER);
        add(backButtonPanel, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
        setModal(true);
        setVisible(true);
    }

    private JLabel createImageLabel() {
        ClassLoader loader = getClass().getClassLoader();
        URL url = loader.getResource("author.jpg");
        if (url == null) {
            return new JLabel("Image not found");
        }
        Image image = new ImageIcon(url).getImage();
        Image scaledImage = image.getScaledInstance(getToolkit().getScreenSize().width / 5,
                getToolkit().getScreenSize().width / 5, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(scaledImage));
        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return label;
    }
}
