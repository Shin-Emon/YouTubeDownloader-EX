package app.youtubedownloaderex.ui.main;

import app.youtubedownloaderex.core.DataLoader;
import app.youtubedownloaderex.ui.common.Assets;
import app.youtubedownloaderex.ui.common.CLabel;
import app.youtubedownloaderex.ui.common.MessageDialog;
import app.youtubedownloaderex.ui.status.StatusWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainPanel extends JPanel {

    private final JPanel rootPanel = new JPanel();
    private final FormPanel formPanel;
    private final JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    public MainPanel() {
        setPreferredSize(new Dimension(800, 600));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        CLabel title = new CLabel("YouTube Downloader EX");
        title.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
        title.setFont(Assets.notoSans(20));
        title.italic();


        formPanel = new FormPanel(this);
        setLayout(new BorderLayout());


        JButton downloadButton = getDownloadButton();

        bottomPanel.add(downloadButton);


        add(title, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


    }

    private JButton getDownloadButton() {
        JButton downloadButton = new JButton("ダウンロード");
        downloadButton.setFont(downloadButton.getFont().deriveFont(18f));

        downloadButton.addActionListener(ev -> {
            var formData = formPanel.getFormData();
            var statusWindow = new StatusWindow(this);
            statusWindow.setVisible(true);
            try {
                DataLoader.download(formData, statusWindow.statusPanel, statusWindow.statusPanel);
            } catch (IOException e) {
                MessageDialog.throwErrorMessage("エラーで草www 大草原不可避www", e);
            }
        });

        return downloadButton;
    }
}
