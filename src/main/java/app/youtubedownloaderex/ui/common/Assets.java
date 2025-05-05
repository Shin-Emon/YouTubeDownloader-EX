package app.youtubedownloaderex.ui.common;

import app.youtubedownloaderex.lang.LangAssets;
import app.youtubedownloaderex.main.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Assets {
    private static Font NOTOSANS;

    private static Image PLAY;
    private static Image PLAY_FORWARD;
    private static Image PLAY_BACK;
    private static Image PAUSE;

    static {
        try {
            NOTOSANS = Font.createFont(Font.TRUETYPE_FONT, Main.class.getClassLoader().getResourceAsStream("font/NotoSansJP.ttf")).deriveFont(Font.BOLD);

            PLAY = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icon/play.png")));
            PLAY_FORWARD = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icon/play-forward.png")));
            PLAY_BACK = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icon/play-back.png")));
            PAUSE = ImageIO.read(Objects.requireNonNull(Main.class.getClassLoader().getResourceAsStream("icon/pause.png")));
        } catch (IOException | FontFormatException e) {
            MessageDialog.throwErrorMessage(LangAssets.get("dialog.error.font"), e);
        }
    }

    public static Font notoSans(int pt) {
        return NOTOSANS.deriveFont(pt * 1.0f);
    }

    public static ImageIcon icoPlay(int size) {
        return new ImageIcon(PLAY.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    public static ImageIcon icoForward(int size) {
        return new ImageIcon(PLAY_FORWARD.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    public static ImageIcon icoPlayBack(int size) {
        return new ImageIcon(PLAY_BACK.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    public static ImageIcon icoPause(int size) {
        return new ImageIcon(PAUSE.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }
}
