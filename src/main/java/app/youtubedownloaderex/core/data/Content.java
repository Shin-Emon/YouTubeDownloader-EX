package app.youtubedownloaderex.core.data;

public interface Content {

    void setURL(String url);
    String getURL();
    FileFormat getFileFormat();
    String getTitle();
    int getDurationSec();
}
