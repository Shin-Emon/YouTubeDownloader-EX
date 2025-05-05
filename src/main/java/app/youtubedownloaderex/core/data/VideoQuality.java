package app.youtubedownloaderex.core.data;

import app.youtubedownloaderex.lang.LangAssets;

public enum VideoQuality implements Quality {

    UHD(2160, LangAssets.get("text.quality.4k")),
    WQHD(1440, LangAssets.get("text.quality.wqhd")),
    FHD(1920, LangAssets.get("text.quality.fhd")),
    HD(1280, LangAssets.get("text.quality.hd")),
    SD(480, LangAssets.get("text.quality.sd")),
    PREVIEW(360, LangAssets.get("text.quality.preview"))

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
