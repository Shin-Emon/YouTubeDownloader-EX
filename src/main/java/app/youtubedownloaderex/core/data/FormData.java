package app.youtubedownloaderex.core.data;

import java.nio.file.Path;

public record FormData(
        String url,
        Quality quality,
        FileFormat fileFormat,
        Path destDirectory,
        boolean saveMetadata
) {
}
