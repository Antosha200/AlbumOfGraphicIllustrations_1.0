package view.frames.mainFrame;

import view.frames.aboutAuthorDialog.AboutAuthorDialog;
import view.frames.aboutProgramDialog.AboutProgramDialog;
import view.frames.inputFileHelpFrame.HelpFrame;

import javax.swing.*;

class MainFrameMenuBar extends JMenuBar {

    private final MainFrame frame;

    protected MainFrameMenuBar(MainFrame frame) {
        this.frame = frame;
        add(createHelpMenu());
        add(createAboutMenu());
    }

    private JMenu createHelpMenu() {
        JMenuItem inputFileHelpMenuItem = new JMenuItem("Contact us");
        inputFileHelpMenuItem.addActionListener(e -> new Thread(HelpFrame::new).start());
        JMenu menu = new JMenu("Help");
        menu.add(inputFileHelpMenuItem);
        return menu;
    }

    private JMenu createAboutMenu() {
        JMenuItem aboutProgramMenuItem = new JMenuItem("About program");
        aboutProgramMenuItem.addActionListener(e -> new Thread(() -> new AboutProgramDialog(frame)).start());
        JMenuItem aboutAuthorMenuItem = new JMenuItem("About author");
        aboutAuthorMenuItem.addActionListener(e -> new Thread(() -> new AboutAuthorDialog(frame)).start());
        JMenu menu = new JMenu("About");
        menu.add(aboutProgramMenuItem);
        menu.add(aboutAuthorMenuItem);
        return menu;
    }
}
