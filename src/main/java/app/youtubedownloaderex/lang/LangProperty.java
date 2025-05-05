package app.youtubedownloaderex.lang;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class LangProperty {

    private final Properties properties;

    public LangProperty(Path path) throws IOException {
        this.properties = new Properties();
        properties.load(Files.newBufferedReader(path, StandardCharsets.UTF_8));
    }

    public String get(String key) {
        return properties.getProperty(key);
    }
}
