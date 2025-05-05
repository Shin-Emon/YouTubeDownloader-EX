package app.youtubedownloaderex.ui.main;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.lang.LangAssets;
import app.youtubedownloaderex.main.Main;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle(AppConstraints.TITLE + " v" + AppConstraints.VERSION);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new MainWindowListener());

        var menubar = new JMenuBar();

        var file = new JMenu(LangAssets.get("window.menubar.file"));
        var settings = new JMenuItem(LangAssets.get("window.menubar.file.settings"));
        file.add(settings);

        var exit = new JMenuItem(LangAssets.get("window.menubar.file.exit"));
        exit.addActionListener(e -> Main.exit(0));
        file.add(exit);

        menubar.add(file);

        add(new MainPanel());
        pack();
        setResizable(false);
        setJMenuBar(menubar);
        setLocationRelativeTo(null);
    }
}
