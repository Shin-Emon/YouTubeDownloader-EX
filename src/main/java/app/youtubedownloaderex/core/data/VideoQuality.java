package app.youtubedownloaderex.core.data;

public enum VideoQuality implements Quality {

    UHD(2160, "4K"),
    WQHD(1440, "WQHD"),
    FHD(1920, "フルHD"),
    HD(1280, "HD"),
    SD(480, "SD"),
    PREVIEW(360, "preview")

    ;

    public final int height;
    public final String description;

    VideoQuality(int height, String description) {
        this.height = height;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
