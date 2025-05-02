package app.youtubedownloaderex.core.data;

public enum FileFormat {
    MP4("mp4", "動画 (mp4)", FileType.VIDEO),
    WAVE("wav", "音声 (wav)", FileType.AUDIO),
    M4A("m4a", "音声 (m4a)", FileType.AUDIO),
    MP3("mp3", "音声 (mp3)", FileType.AUDIO),

    ;

    public final String ext;
    public final String description;
    public final FileType fileType;

    FileFormat(String ext, String description, FileType fileType) {
        this.ext = ext;
        this.description = description;
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
