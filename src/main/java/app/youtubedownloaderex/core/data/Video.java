package app.youtubedownloaderex.core.data;

public class Video implements Content {

    private String url;
    private final String title;
    private final int duration;
    private final VideoQuality videoQuality;
    private final FileFormat fileFormat;

    public Video(String url, VideoQuality videoQuality, FileFormat fileFormat, String title, int duration) {
        this.url = url;
        this.videoQuality = videoQuality;
        this.fileFormat = fileFormat;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public void setURL(String url) {
        if (url != null && !url.isBlank()) {
            this.url = url;
        }
    }

    @Override
    public String toString() {
        return videoQuality.description + " FROM " + url;
    }

    @Override
    public String getURL() {
        return this.url;
    }

    @Override
    public FileFormat getFileFormat() {
        return this.fileFormat;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getDurationSec() {
        return this.duration;
    }

    public VideoQuality getQuality() {
        return this.videoQuality;
    }
}
