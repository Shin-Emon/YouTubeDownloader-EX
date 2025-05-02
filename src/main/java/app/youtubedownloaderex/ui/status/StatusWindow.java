package app.youtubedownloaderex.ui.status;

import javax.swing.*;
import java.awt.*;

public class StatusWindow extends JFrame {

    public final StatusPanel statusPanel;

    public StatusWindow(JPanel parent) {
        setTitle("ダウンロード中...");

        statusPanel = new StatusPanel(this);

        setResizable(false);
        add(statusPanel);
        pack();
        setLocationRelativeTo(parent);
    }

}
