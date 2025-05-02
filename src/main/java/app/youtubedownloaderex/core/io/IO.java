package app.youtubedownloaderex.core.io;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.main.Main;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class IO {

    public static void initialize() throws IOException {
        var homeDir = Paths.get(AppConstraints.APP_HOME);

        if (Files.notExists(homeDir)) {
            Files.createDirectory(homeDir);
        }

    }

    public static void copyAssets() throws IOException, URISyntaxException {
        // ffmpeg
        copyIfNotExists(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("bin/ffmpeg.exe")), Paths.get(AppConstraints.APP_HOME + "\\ffmpeg.exe"));
//        copyIfNotExists(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("bin/ffplay.exe")), Paths.get(AppConstraints.APP_HOME + "\\ffplay.exe"));

        // ffprobe
        copyIfNotExists(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("bin/ffprobe.exe")), Paths.get(AppConstraints.APP_HOME + "\\ffprobe.exe"));

        // yt-dlp
//        copyIfNotExists(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("bin/yt-dlp.exe")), Paths.get(AppConstraints.APP_HOME + "\\yt-dlp.exe"));

        // python script
        copyIfNotExists(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("script/dist/script.exe")), Paths.get(AppConstraints.APP_HOME + "\\script.exe"));
    }

    public static void removePreviewData() throws IOException {

        Files.deleteIfExists(Paths.get(AppConstraints.PREV_DEST_DIR).resolve("temp.mp4"));

    }

    private static void copyIfNotExists(InputStream in, Path dest) throws URISyntaxException, IOException {
        if (AppConstraints.DEBUG_MODE) {
            Files.deleteIfExists(dest);
        }

        if (AppConstraints.DEBUG_MODE || Files.notExists(dest)) {
            System.out.println("COPY");
            Files.copy(in, dest);
        }
        System.out.println("END");
    }

}
