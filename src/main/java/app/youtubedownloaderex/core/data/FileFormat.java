package app.youtubedownloaderex.core.data;

import app.youtubedownloaderex.lang.LangAssets;

public enum FileFormat {
    MP4("mp4", LangAssets.get("text.format.video") + " (mp4)", FileType.VIDEO),
    WAVE("wav", LangAssets.get("text.format.audio") + " (wav)", FileType.AUDIO),
    M4A("m4a", LangAssets.get("text.format.audio") + " (m4a)", FileType.AUDIO),
    MP3("mp3", LangAssets.get("text.format.audio") + " (mp3)", FileType.AUDIO),

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
