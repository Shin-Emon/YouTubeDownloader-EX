package app.youtubedownloaderex.core.data;

public class TempFormStatus {
    private boolean isVideo = true;

    public void audio() { this.isVideo = false; }
    public void video() { this.isVideo = true; }
    public boolean isVideo() { return this.isVideo; }
}
