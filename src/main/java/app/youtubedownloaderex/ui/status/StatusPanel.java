package app.youtubedownloaderex.ui.status;

import app.youtubedownloaderex.core.function.LogListener;
import app.youtubedownloaderex.core.function.StatusListener;
import app.youtubedownloaderex.ui.common.MessageDialog;

import javax.swing.*;
import java.awt.*;

public class StatusPanel extends JPanel implements StatusListener, LogListener {

    private final JProgressBar progressBar = new JProgressBar();
    private final JTextArea logArea = new JTextArea();
    private final JFrame parentWindow;

    private double max = 0.0;

    public StatusPanel(JFrame parentWindow) {
        this.parentWindow = parentWindow;

        setPreferredSize(new Dimension(650, 450));
        setLayout(new BorderLayout());

        logArea.setEditable(false);

        progressBar.setPreferredSize(new Dimension(650, 15));
        progressBar.setMaximum(100);

        add(logArea, BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

    }

    @Override
    public void receive(String data) {

        System.out.println(data);

        if (data == null) {
            return;
        }

        String d2 = data.replaceAll(" ", "").strip().stripIndent();

        if (d2.equals("finished")) {
            max = 0.0;
        } else if (d2.equals("closed")) {
            progressBar.setValue(progressBar.getMaximum());
            MessageDialog.showInfoMessage("ダウンロード完了！");
            parentWindow.setVisible(false);
        } else {
            max = Math.max(max, Double.parseDouble(d2));
            progressBar.setValue((int) max);
        }

    }

    @Override
    public void receiveLog(String log) {

        logArea.setText(logArea.getText() + "\r\n" + log);

    }
}
