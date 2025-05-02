package app.youtubedownloaderex.core.preview;

public class PlayTimeFormatter {

    public static String msecToMinSec(long msec) {
        long secRaw = msec / 1000;

        long sec = secRaw % 60;
        long min = (secRaw - sec) / 60;

        return String.format("%02d:%02d", min, sec);
    }

    public static String msecToHourMinSec(long msec) {
        long secRaw = msec / 1000;

        long sec = secRaw % 60;
        long min = ((secRaw - sec) / 60);
        long hour = (min - min % 60) / 60;
        if (min < 60) {
            hour = 0;
        }

        if (min > 60) {
            min = min % 60;
        }

        return String.format("%d:%d:%d", hour, min, sec);
    }
}
