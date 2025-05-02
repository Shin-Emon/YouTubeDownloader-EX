package app.youtubedownloaderex.ui.preview;

import app.youtubedownloaderex.core.preview.PreviewManager;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PreviewWindow extends JFrame {

    public final PreviewPanel panel;

    public PreviewWindow(JFrame parent) {

        setTitle("[プレビュー] " + PreviewManager.getContent().getTitle());
        setResizable(false);

        panel = new PreviewPanel(this);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(parent);

        addWindowListener(new PreviewWindowListener());

        setVisible(true);
    }

}
