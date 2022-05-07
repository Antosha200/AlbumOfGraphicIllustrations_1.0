package View.frames.mainFrame;

import View.frames.aboutAuthorDialog.AboutAuthorDialog;
import View.frames.aboutProgramDialog.AboutProgramDialog;
import View.frames.inputFileHelpFrame.HelpFrame;

import javax.swing.*;

class MainFrameMenuBar extends JMenuBar {

    private final MainFrame frame;

    protected MainFrameMenuBar(MainFrame frame) {
        this.frame = frame;
        //add(createFileMenu());
        add(createHelpMenu());
        add(createAboutMenu());
    }

    private JMenu createFileMenu() {

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> System.exit(0));
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(exitMenuItem);
        return fileMenu;
    }


    private JMenu createHelpMenu() {
        JMenuItem inputFileHelpMenuItem = new JMenuItem("Some Help");
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
