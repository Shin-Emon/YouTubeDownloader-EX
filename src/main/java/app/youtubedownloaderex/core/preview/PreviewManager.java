package app.youtubedownloaderex.core.preview;


import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.core.DataLoader;
import app.youtubedownloaderex.core.data.Content;
import app.youtubedownloaderex.core.data.FormData;
import app.youtubedownloaderex.lang.LangAssets;
import app.youtubedownloaderex.ui.common.MessageDialog;
import app.youtubedownloaderex.ui.preview.PreviewWindow;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.io.IOException;
import java.nio.file.Path;

public class PreviewManager {

    private static boolean isPlaying;
    private static boolean isLoaded;

    private static long currentTime;

    private static MediaPlayer mediaPlayer;

    private static final long SLEEP_DURATION = 250;

    private static PreviewPlayListener listener;
    private static Content content;

    public static boolean isPlaying() {
        return isPlaying;
    }


    public static void startPreviewing(FormData previewData) throws IOException {

        // 動画のメタデータを取得。
        DataLoader.fetchMetadata(previewData, content -> {
            PreviewManager.content = content;

            try {
                DataLoader.download(previewData, PreviewManager::listenStatus, PreviewManager::listenLog, "temp");
            } catch (IOException e) {
                MessageDialog.throwErrorMessage(LangAssets.get("dialog.error.common"), e);
            }
        });

        // プレビューファイルをダウンロード。ダウンロードが終わり次第、再生開始。

    }

    public static Content getContent() {
        return content;
    }

    public static void stopPlaying() {
        isPlaying = false;
        mediaPlayer.controls().stop();
    }

    public static void setTime(long t) {
        mediaPlayer.controls().setTime(t);
        if (t <= getLength()) {
            currentTime = t;
        }
    }

    // 再生状態になったかどうかをboolで返す
    public static boolean pauseResume() {
        mediaPlayer.controls().pause();
        isPlaying = !isPlaying;
        return isPlaying;
    }

    public static void startPlaying(EmbeddedMediaPlayer _mediaPlayer, Path filePath, PreviewPlayListener _listener) {
        isPlaying = true;
        isLoaded = true;
        listener = _listener;
        _mediaPlayer.media().play(filePath.toString());
        _mediaPlayer.controls().setRepeat(true);
        mediaPlayer = _mediaPlayer;


        new Thread(() -> {
            try {
                while (isPlaying) {
                    currentTime = _mediaPlayer.status().time();
                    _listener.listen(currentTime);

                    Thread.sleep(SLEEP_DURATION);
                }
            } catch (Exception e) {
                MessageDialog.throwErrorMessage(LangAssets.get("dialog.error.common"), e);
            }
        }).start();
    }

    public static long getLength() {
        if (isLoaded) {
            return mediaPlayer.status().length();
        }

        return -1;
    }


    private static void listenStatus(String statusStr) {

//        System.out.println(statusStr);

        if (statusStr != null && statusStr.equals("closed")) {

            // ダウンロードが終わり次第、プレビューを表示
            var window = new PreviewWindow(AppConstraints.window);
            window.panel.load();


        }

    }

    private static void listenLog(String d) {
        // DO NOTHING

        if (AppConstraints.DEBUG_MODE) {
            System.out.println(d);
        }
    }


}
