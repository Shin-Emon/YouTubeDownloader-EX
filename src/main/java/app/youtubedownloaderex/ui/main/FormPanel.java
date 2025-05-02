package app.youtubedownloaderex.ui.main;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.core.DataLoader;
import app.youtubedownloaderex.core.data.*;
import app.youtubedownloaderex.core.preview.PreviewManager;
import app.youtubedownloaderex.ui.common.CLabel;
import app.youtubedownloaderex.ui.common.ColorAssets;
import app.youtubedownloaderex.ui.common.MessageDialog;
import app.youtubedownloaderex.util.Validator;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FormPanel extends JPanel {

    private final GroupLayout layout;

    // メイン設定パネル
    private JPanel mainSettingPanel = new JPanel();

    // フォームの一時データ
    private TempFormStatus status = new TempFormStatus();

    // URL
    private final JTextField urlInput = new JTextField(25);

    // タイトル
    private final JLabel title = new JLabel();

    // フォーマット
    private final JComboBox<FileFormat> fileFormatSelector = new JComboBox<>();

    // 品質
    private final JComboBox<AudioQuality> audioQualitySelector = new JComboBox<>();
    private final JComboBox<VideoQuality> videoQualitySelector = new JComboBox<>();

    // 出力先
    private final JTextField destInput = new JTextField(25);

    /* ******** 詳細設定 ********* */

    // メタデータ保存
    private final JCheckBox saveMetadata = new JCheckBox("メタデータを埋め込む");

    // 出力範囲指定する？
    private final JCheckBox isRangeSet = new JCheckBox("出力範囲を指定する");

    private final JTextField startMin = new JTextField(4);
    private final JTextField startSec = new JTextField(4);

    private final JTextField endMin = new JTextField(4);
    private final JTextField endSec = new JTextField(4);

    private JComponent[][] compos;

    public FormPanel(JPanel parent) {

        setPreferredSize(new Dimension((int) (parent.getPreferredSize().getWidth() * 0.6), (int) parent.getPreferredSize().getHeight() / 2));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        layout = new GroupLayout(mainSettingPanel);
        mainSettingPanel.setLayout(layout);
        mainSettingPanel.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        mainSettingPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(215, 215, 215)));

        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        CLabel urlLbl = new CLabel("URL");

        JCheckBox playlistCheck = new JCheckBox("プレイリスト");
        JButton previewButton = new JButton("プレビュー");
        previewButton.addActionListener(e -> {
            try {
                String url = urlInput.getText();
                if (url == null || url.isBlank()) {
                    urlInput.setText(url = AppConstraints.DOWNLOADS);
                }

                var formData = new FormData(
                        url,
                        VideoQuality.PREVIEW,
                        FileFormat.MP4,
                        Paths.get(AppConstraints.PREV_DEST_DIR),
                        true
                );
                PreviewManager.startPreviewing(formData);
            } catch (IOException ex) {
                MessageDialog.throwErrorMessage("フォームデータをロードできませんでした。", ex);
            }
        });

        urlInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {

                    if (Validator.isValidURL(urlInput.getText())) {
                        urlInput.setForeground(Color.BLACK);
                        title.setText("タイトル取得中...");
                        DataLoader.fetchMetadata(getFormData(), content -> {
                            title.setText(content.getTitle());
                        });
                    } else {
                        urlInput.setForeground(ColorAssets.WINE_RED);
                    }
                } catch (IOException ex) {
                    MessageDialog.throwErrorMessage("エラーで草w", ex);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!Validator.isValidURL(urlInput.getText())) {
                    urlInput.setForeground(ColorAssets.WINE_RED);
                }

                if (urlInput.getText().isBlank()) {
                    title.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

        CLabel formatLbl = new CLabel("形式");
        fileFormatSelector.addItem(FileFormat.MP4);
        fileFormatSelector.addItem(FileFormat.WAVE);
        fileFormatSelector.addItem(FileFormat.M4A);
        fileFormatSelector.addItem(FileFormat.MP3);


        CLabel qualityLbl = new CLabel("品質");
        audioQualitySelector.addItem(AudioQuality.AUDIO_256K);
        audioQualitySelector.addItem(AudioQuality.AUDIO_160K);
        audioQualitySelector.addItem(AudioQuality.AUDIO_128K);
        audioQualitySelector.addItem(AudioQuality.AUDIO_96K);
        audioQualitySelector.setSelectedItem(AudioQuality.AUDIO_128K);

        videoQualitySelector.addItem(VideoQuality.UHD);
        videoQualitySelector.addItem(VideoQuality.WQHD);
        videoQualitySelector.addItem(VideoQuality.FHD);
        videoQualitySelector.addItem(VideoQuality.HD);
        videoQualitySelector.addItem(VideoQuality.SD);
        videoQualitySelector.setSelectedItem(VideoQuality.FHD);

        // 形式の選択が来たとき、それに応じて品質のリストを変更...
        fileFormatSelector.addActionListener(ev -> {
            formatChanged((FileFormat) fileFormatSelector.getSelectedItem());
        });

        CLabel destLbl = new CLabel("出力先");
        destInput.setText(AppConstraints.DOWNLOADS);

        JButton destSelectButton = getDestSelectButton();

        compos = new JComponent[][] {
                {urlLbl, urlInput, previewButton, playlistCheck},
                {null, title, null, null},
                {formatLbl, fileFormatSelector, null, null},
                {qualityLbl, videoQualitySelector, null, null},
                {destLbl, destInput, destSelectButton, null},
        };

//        compos = new JComponent[][] {
//                {urlLbl, urlInput, playlistCheck, null},
//                {formatLbl, fileFormatSelector, null, null},
//                {qualityLbl, videoQualitySelector, null, null},
//                {destLbl, destInput, destSelectButton, null},
//        };

        initLayout(layout, compos);

        add(mainSettingPanel);
        add(detailSettingPanel());
    }

    private JButton getDestSelectButton() {
        JButton destSelectButton = new JButton("選択...");
        destSelectButton.addActionListener(ev -> {
            if (destInput.getText().isBlank()) {
                destInput.setText(AppConstraints.DOWNLOADS);
            }
            var selector = new JFileChooser(destInput.getText());
            selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            selector.setDialogTitle("出力先フォルダを選択...");

            int s = selector.showSaveDialog(this);
            if (s == JFileChooser.APPROVE_OPTION) {
                var file = selector.getSelectedFile();
                destInput.setText(file.getAbsolutePath());
            } else if (s == JFileChooser.ERROR_OPTION) {
                MessageDialog.throwErrorMessage("むりやった。", null);
            }
        });
        return destSelectButton;
    }

    private JPanel detailSettingPanel() {
        var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        var title = new CLabel("詳細設定");
//        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        var rangeSelectButton = new JButton("出力範囲を選択");
        rangeSelectButton.setVisible(isRangeSet.isSelected());

        panel.add(title);
        panel.add(saveMetadata);
        panel.add(isRangeSet);

        isRangeSet.addActionListener(e -> {
            rangeSelectButton.setVisible(isRangeSet.isSelected());
        });
        panel.add(rangeSelectButton);

        return panel;
    }

    public FormData getFormData() {
        if ( urlInput.getText() == null || destInput.getText() == null || Files.notExists(Paths.get(destInput.getText())) ) {
            return null;
        }

        if (status.isVideo()) {
            return new FormData(
                    urlInput.getText(),
                    (VideoQuality) videoQualitySelector.getSelectedItem(),
                    (FileFormat) fileFormatSelector.getSelectedItem(),
                    Paths.get(destInput.getText()),
                    saveMetadata.isSelected()
            );
        } else {
            return new FormData(
                    urlInput.getText(),
                    (AudioQuality) audioQualitySelector.getSelectedItem(),
                    (FileFormat) fileFormatSelector.getSelectedItem(),
                    Paths.get(destInput.getText()),
                    saveMetadata.isSelected()
            );
        }
    }

    private void formatChanged(FileFormat format) {
        if (format == FileFormat.MP4) {
            status.video();
            refresh(new int[] {3, 1}, videoQualitySelector);
        } else {
            status.audio();
            refresh(new int[] {3, 1}, audioQualitySelector);
        }
    }

    private void refresh(int[] pos, JComponent component) {
        mainSettingPanel.removeAll();

        compos[pos[0]][pos[1]] = component;

        initLayout(layout, compos);
    }

    private void initLayout(GroupLayout layout, JComponent[][] compos) {
        int nx = compos[0].length;
        int ny = compos.length;

        // 水平方向
        var hg = layout.createSequentialGroup();
        for (int x = 0; x < nx; x++) {
            var pg = layout.createParallelGroup();
            for (int y = 0; y < ny; y++) {
                var c = compos[y][x];
                if (c != null) {
                    pg.addComponent(c);
                }
            }
            hg.addGroup(pg);
        }
        layout.setHorizontalGroup(hg);

        var vg = layout.createSequentialGroup();
        for (int y = 0; y < ny; y++) {
            var pg = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
            for (int x = 0; x < nx; x++) {
                var c = compos[y][x];
                if (c != null) {
                    pg.addComponent(c);
                }
            }
            vg.addGroup(pg);
        }
        layout.setVerticalGroup(vg);
    }
}
