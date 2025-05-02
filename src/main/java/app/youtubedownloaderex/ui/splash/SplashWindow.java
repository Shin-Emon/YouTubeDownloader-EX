package app.youtubedownloaderex.ui.splash;

import app.youtubedownloaderex.ui.common.CLabel;

import javax.swing.*;

public class SplashWindow extends JFrame {


    public SplashWindow(String message) {

        setUndecorated(true);

        var root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        var appNameLabel = new CLabel(message);
        appNameLabel.changeSize(18);
        appNameLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        appNameLabel.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        root.add(appNameLabel);
        add(root);

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }

}
