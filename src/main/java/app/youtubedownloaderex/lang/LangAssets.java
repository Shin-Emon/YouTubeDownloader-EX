package app.youtubedownloaderex.lang;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.main.Main;
import app.youtubedownloaderex.ui.common.MessageDialog;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

public class LangAssets {

    private static LangProperty en_us;
    private static LangProperty ja_jp;

    static {
        try {
//            Main.class.getClassLoader().getResourceAsStream("font/en_us").readAllBytes();
            var en_us_file = Paths.get(Main.class.getClassLoader().getResource("lang/en_us.lang").toURI());
            var ja_jp_file = Paths.get(Main.class.getClassLoader().getResource("lang/ja_jp.lang").toURI());

            en_us = new LangProperty(en_us_file);
            ja_jp = new LangProperty(ja_jp_file);
        } catch (URISyntaxException | IOException e) {
            MessageDialog.throwErrorMessage("An error has occurred initializing locale settings.", e);
            Main.exit(-1);
        }
    }

    private static LangProperty localeToProperty(Locale locale) {
        System.out.println(locale.toString());
        return switch (locale.toString()) {
            case "en_US" -> en_us;
            case "ja_JP" -> ja_jp;
            default -> null;
        };
    }

    public static String get(String key) {
        LangProperty prop = localeToProperty(AppConstraints.LOCALE);

        return prop.get(key);
    }

    public static String getAs(String key, Locale locale) {
        LangProperty prop = localeToProperty(locale);

        return prop.get(key);
    }
}
