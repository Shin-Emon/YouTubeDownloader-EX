package app.youtubedownloaderex.ui.preview;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.core.data.AreaSettings;
import app.youtubedownloaderex.core.data.TempAreaStatus;
import app.youtubedownloaderex.core.preview.PlayTimeFormatter;
import app.youtubedownloaderex.core.preview.PreviewManager;
import app.youtubedownloaderex.lang.LangAssets;
import app.youtubedownloaderex.ui.common.Assets;
import app.youtubedownloaderex.ui.common.ColorAssets;
import app.youtubedownloaderex.ui.common.MessageDialog;
import uk.co.caprica.vlcj.media.Meta;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;

public class PreviewPanel extends JPanel implements ChangeListener {

    private JSlider slider = new JSlider();
    private JLabel timeLabel = new JLabel();
    private JPanel sliderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

    private JPanel posPanel;

    private final int BTN_SIZE = 30;

    private JButton setStart = new JButton("始");
    private JButton back = new JButton(Assets.icoPlayBack(BTN_SIZE));
    private JButton playPause = new JButton(Assets.icoPause(BTN_SIZE));
    private JButton forward = new JButton(Assets.icoForward(BTN_SIZE));
    private JButton setEnd = new JButton("終");

    private final EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

    private final JFrame parent;

    public PreviewPanel(JFrame parent) {
        this.parent = parent;
        // プレビューのダウンロードはすでに終わってる前提
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        posPanel = new JPanel();

        mediaPlayerComponent.setPreferredSize(new Dimension(540, 360));

        slider.setValue(0);
        slider.setMaximum(1000);
        slider.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                stateChanged(null);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                stateChanged(null);
                slider.addChangeListener(PreviewPanel.this);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                stateChanged(null);
                slider.removeChangeListener(PreviewPanel.this);
            }
        });

        setStart.addActionListener(e -> {
            TempAreaStatus.start = slider.getValue();
            AreaSettings.start = (PreviewManager.getLength() / slider.getMaximum()) * TempAreaStatus.start;
        });

        setEnd.addActionListener(e -> {
            TempAreaStatus.end = slider.getValue();
            AreaSettings.end = (PreviewManager.getLength() / slider.getMaximum()) * TempAreaStatus.end;
        });

        playPause.addActionListener(e -> {
            PreviewManager.pauseResume();
            if (PreviewManager.isPlaying()) {
                playPause.setIcon(Assets.icoPause(BTN_SIZE));
            } else {
                playPause.setIcon(Assets.icoPlay(BTN_SIZE));
            }
        });

        controlPanel.add(setStart);
        controlPanel.add(back);
        controlPanel.add(playPause);
        controlPanel.add(forward);
        controlPanel.add(setEnd);

        add(mediaPlayerComponent);
        add(posPanel);
        add(getSliderPanel());
        add(controlPanel);

    }

    private JPanel getSliderPanel() {
        var panel = new JPanel(new FlowLayout(FlowLayout.LEFT)) {

            // 始点・終点フラッグ描画

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                var g2 = (Graphics2D) g;

                g2.setColor(ColorAssets.FLAG_START);

                int sliderWidth = slider.getWidth();

                TempAreaStatus.end = slider.getMaximum();

                int sliderX = 15;

                // START
                {
                    int startPosAX = sliderX + ( sliderWidth * TempAreaStatus.start) / slider.getMaximum();
                    int baseX1 = startPosAX - 6;
                    int baseX2 = startPosAX + 6;

                    int topY = 10;
                    int baseY = 0;
                    g2.setColor(ColorAssets.FLAG_START);
                    g2.fillPolygon(new int[] {startPosAX, baseX1, baseX2}, new int[] {topY, baseY, baseY}, 3);
                }

                // END
                {
                    int endPosAX = -5 + ( sliderWidth * TempAreaStatus.end ) / slider.getMaximum();
                    int baseX1 = endPosAX - 6;
                    int baseX2 = endPosAX + 6;

                    int topY = 10;
                    int baseY = 0;
                    g2.setColor(ColorAssets.FLAG_END);
                    g2.fillPolygon(new int[] {endPosAX, baseX1, baseX2}, new int[] {topY, baseY, baseY}, 3);
                }
            }
        };
        slider.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));

        panel.add(slider);


        return panel;
    }

    public void load() {
        PreviewManager.startPlaying(mediaPlayerComponent.mediaPlayer(), Paths.get(AppConstraints.PREV_DEST_DIR).resolve("temp.mp4"), this::listenAndSyncSlider);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        long len = PreviewManager.getLength();
        System.out.println(len / slider.getMaximum());
        PreviewManager.setTime(len / slider.getMaximum() * slider.getValue());
    }

    private void listenAndSyncSlider(long currentTime) {
//        System.out.println(currentTime);
        // 500ms に一回呼び出される
        try {
            long length = PreviewManager.getLength();
            int sMax = slider.getMaximum();
            if (length != 0) {
//                    System.out.println((int) ((currentTime * sMax) / length));
                slider.setValue((int) ((currentTime * sMax) / length));
                System.out.println("=============\n" + slider.getValue() + "\n=============\n");
                System.out.println((int) ((currentTime * sMax) / length));
                timeLabel.setText(PlayTimeFormatter.msecToMinSec(currentTime) + "/" + PlayTimeFormatter.msecToMinSec(length));
//                sliderX = slider.getX() + 10;
                posPanel.repaint();
            }

        } catch (Exception e) {
            MessageDialog.throwErrorMessage(LangAssets.get("dialog.error.common"), e);
        }
    }
}
