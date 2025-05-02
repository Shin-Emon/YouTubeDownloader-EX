package app.youtubedownloaderex.util;

import java.net.MalformedURLException;
import java.net.URI;

public class Validator {

    public static boolean isValidURL(String url) {

        try {
            URI.create(url).toURL();
        } catch (MalformedURLException | IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
