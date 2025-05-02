package app.youtubedownloaderex.ui.main;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.main.Main;

import javax.swing.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("YouTube Downloader EX v" + AppConstraints.VERSION);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addWindowListener(new MainWindowListener());

        var menubar = new JMenuBar();

        var file = new JMenu("ファイル");
        var settings = new JMenuItem("設定");
        file.add(settings);

        var exit = new JMenuItem("終了");
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
