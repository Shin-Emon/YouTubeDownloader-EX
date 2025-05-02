package app.youtubedownloaderex.core;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.core.data.*;
import app.youtubedownloaderex.core.function.FetchFunc;
import app.youtubedownloaderex.core.function.LogListener;
import app.youtubedownloaderex.core.function.StatusListener;
import app.youtubedownloaderex.ui.common.MessageDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {

    public static void download(FormData formData, StatusListener statusListener, LogListener logListener, String... customFileName) throws IOException {

        var destination = formData.destDirectory();
        var fileFormat = formData.fileFormat();

        var downloadOption = fileFormat.fileType == FileType.VIDEO ? "video" : "audioonly";

        List<String> command = new ArrayList<>(List.of(
                AppConstraints.APP_HOME + "\\script.exe",
                "dl",
                formData.url(),
                destination.toString(),
                customFileName.length > 0 ? customFileName[0] : ":false",
                downloadOption,
                fileFormat.ext
        ));

        if (fileFormat.fileType == FileType.VIDEO) {
            // VIDEO
            command.add(String.valueOf(((VideoQuality) formData.quality()).height)); // 縦の幅

        } else {
            // AUDIO
            command.add(String.valueOf(((AudioQuality) formData.quality()).rate)); // ビットレート

        }

        command.add(formData.saveMetadata() ? "true" : "false");
        command.add(String.valueOf(AppConstraints.receivePort));

        executeCommand(statusListener, logListener, command);
    }

    private static void executeCommand(final StatusListener statusListener, final LogListener logListener, List<String> command) throws IOException {
        // 受付サーバー起動
        var server = new StatusReceiveThread(statusListener);
        server.start();

        if (AppConstraints.DEBUG_MODE) {
            command.forEach(System.out::println);
        }

        var pb = new ProcessBuilder(command);
        var process = pb.start();

        var input = new BufferedReader(new InputStreamReader(process.getInputStream()));

        var th = new Thread(() -> {
            String line;
            try {
                while ((line = input.readLine()) != null) {
                    logListener.receiveLog(line);
                }
            } catch (IOException e) {
                MessageDialog.throwErrorMessage("むりやった。", e);
            }
        });
        th.setName("log_receive_thread_X");
        th.start();

        AppConstraints.receivePort++;
    }

    public static void fetchMetadata(FormData formData, FetchFunc func) throws IOException {

        List<String> command = new ArrayList<>(List.of(
                AppConstraints.APP_HOME + "\\script.exe",
                "info",
                formData.url(),
                String.valueOf(AppConstraints.receivePort)
        ));

        String[] title = new String[] {""};
        int[] duration = new int[] {-10};

        // DO NOTHING
        executeCommand(
                status -> {
                    if (status != null && !status.equals("closed") && !status.isBlank()) {
                        String[] keyVal = status.split("=");
                        if (keyVal[0].equals("title")) {
                            title[0] = keyVal[1];
                        } else if (keyVal[0].equals("duration")) {
                            duration[0] = (int) Double.parseDouble(keyVal[1]);
                        }

//                        if (!title[0].isBlank() && !(duration[0] < 0)) {
//                                if (formData.fileFormat().fileType == FileType.VIDEO) {
//                                    func.done(new Video(formData.url(), (VideoQuality) formData.quality(), formData.fileFormat(), title[0], duration[0]));
//                                } else {
//                                    func.done(new Audio(formData.url(), (AudioQuality) formData.quality(), formData.fileFormat(), title[0], duration[0]));
//                                }
//                        }
                    } else if (status != null && status.equals("closed")) {
                        if (formData.fileFormat().fileType == FileType.VIDEO) {
                            func.done(new Video(formData.url(), (VideoQuality) formData.quality(), formData.fileFormat(), title[0], duration[0]));
                        } else {
                            func.done(new Audio(formData.url(), (AudioQuality) formData.quality(), formData.fileFormat(), title[0], duration[0]));
                        }
                    }
                },
                System.out::println,
                command
        );
    }
}
