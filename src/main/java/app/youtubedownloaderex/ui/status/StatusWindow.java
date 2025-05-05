package app.youtubedownloaderex.ui.status;

import app.youtubedownloaderex.lang.LangAssets;

import javax.swing.*;
import java.awt.*;

public class StatusWindow extends JFrame {

    public final StatusPanel statusPanel;

    public StatusWindow(JPanel parent) {
        setTitle(LangAssets.get("window.title.downloading"));

        statusPanel = new StatusPanel(this);

        setResizable(false);
        add(statusPanel);
        pack();
        setLocationRelativeTo(parent);
    }

}
