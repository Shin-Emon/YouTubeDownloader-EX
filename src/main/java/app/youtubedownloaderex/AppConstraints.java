package app.youtubedownloaderex;

import app.youtubedownloaderex.lang.LangAssets;

import javax.swing.*;
import java.util.Locale;

public class AppConstraints {
    public static final Locale LOCALE = Locale.JAPAN;

    public static final String TITLE = LangAssets.get("common.app.title");

    public static final int MAJOR = 1;
    public static final int MINOR = 0;
    public static final int SUB_MIN = 0;
    public static final String SUFFIX = "rc 3";
    public static final String VERSION = MAJOR + "." + MINOR + "." + SUB_MIN + " " + SUFFIX;

    public static final String USER_DIR = System.getProperty("user.home");
    public static final String APP_HOME = USER_DIR + "\\YouTubeDownloaderEX";
    public static final String DOWNLOADS = USER_DIR + "\\Downloads";
    public static final String DESKTOP = USER_DIR + "\\Desktop";

    public static final String PREV_DEST_DIR = APP_HOME;

    public static final int DEFAULT_PORT = 11225;
    public static int receivePort = DEFAULT_PORT;

    public static final boolean DEBUG_MODE = true;

    public static JFrame window;
}
