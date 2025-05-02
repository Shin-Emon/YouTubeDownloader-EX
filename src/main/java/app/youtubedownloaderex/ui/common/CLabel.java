package app.youtubedownloaderex.ui.common;

import javax.swing.*;
import java.awt.*;

public class CLabel extends JLabel {

    public CLabel(String text) {
        super(text);

        init();
    }

    public CLabel() {
        super();

        init();
    }

    public void italic() {
        setFont(getFont().deriveFont(Font.ITALIC));
    }

    public void changeSize(int pt) {
        setFont(getFont().deriveFont(pt * 1.0f));
    }

    private void init() {
        setFont(Assets.notoSans(15));
    }
}
