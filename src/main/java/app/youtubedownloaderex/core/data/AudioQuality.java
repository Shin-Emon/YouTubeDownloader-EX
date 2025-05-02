package app.youtubedownloaderex.core.data;

public enum AudioQuality implements Quality {

    AUDIO_256K(256, "最強 256Kbps"),
    AUDIO_160K(160, "いいやつ 160Kbps"),
    AUDIO_128K(128, "おすすめ 128Kbps"),
    AUDIO_96K(96, "やめとけ 96Kbps"),

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
