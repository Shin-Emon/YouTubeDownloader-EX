package app.youtubedownloaderex.main;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.core.data.FileFormat;
import app.youtubedownloaderex.core.data.FormData;
import app.youtubedownloaderex.core.data.VideoQuality;
import app.youtubedownloaderex.core.io.IO;
import app.youtubedownloaderex.core.preview.PreviewManager;
import app.youtubedownloaderex.lang.LangAssets;
import app.youtubedownloaderex.ui.common.MessageDialog;
import app.youtubedownloaderex.ui.main.MainWindow;
import app.youtubedownloaderex.ui.splash.SplashWindow;
import com.formdev.flatlaf.FlatLightLaf;
import com.sun.jna.NativeLibrary;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Locale;

public class Main {



    public static void main(String[] args) throws IOException {

//        new StatusReceiver().start();
        var splash = new SplashWindow(LangAssets.get("dialog.splash.loading"));


        try {
            // check vlc
            NativeLibrary.addSearchPath("libvlc", "C:\\Program Files\\VideoLAN\\VLC");

            UIManager.setLookAndFeel(new FlatLightLaf());
            IO.initialize();
            IO.copyAssets();
        } catch (Exception e) {
            splash.setVisible(false);
            MessageDialog.throwErrorMessage(LangAssets.get("dialog.error.common"), e);
        }


        if (AppConstraints.DEBUG_MODE) {
            MessageDialog.showInfoMessage("デバッグモードで起動します。");
        }

//        PreviewManager.startPreviewing(new FormData(
//                "https://www.youtube.com/watch?v=SY18qVIRhlA",
//                VideoQuality.PREVIEW,
//                FileFormat.MP4,
//                Paths.get(AppConstraints.PREV_DEST_DIR),
//                true
//        ));

        splash.setVisible(false);

        AppConstraints.window = new MainWindow();
        AppConstraints.window.setVisible(true);

        System.out.println("YouTube Downloader EX");
    }

    public static void exit(int exitCode) {
        try {
            IO.removePreviewData();
        } catch (IOException e) {
            MessageDialog.throwErrorMessage(LangAssets.get("dialog.error.common"), e);
        }

        System.exit(exitCode);
    }
}
