package app.youtubedownloaderex.core.data;

import app.youtubedownloaderex.lang.LangAssets;

public enum AudioQuality implements Quality {

    AUDIO_256K(256, "256Kbps (" + LangAssets.get("text.quality.256k") + ")"),
    AUDIO_160K(160, "160Kbps (" + LangAssets.get("text.quality.160k") + ")"),
    AUDIO_128K(128, "128Kbps (" + LangAssets.get("text.quality.128k") + ")"),
    AUDIO_96K(96, "96Kbps (" + LangAssets.get("text.quality.96k") + ")"),

    ;

    public final int rate;
    public final String description;

    AudioQuality(int rate, String description) {
        this.rate = rate;
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }

}
