package app.youtubedownloaderex.ui.main;

import app.youtubedownloaderex.core.io.IO;
import app.youtubedownloaderex.main.Main;
import app.youtubedownloaderex.ui.common.MessageDialog;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class MainWindowListener implements WindowListener {
    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        Main.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
