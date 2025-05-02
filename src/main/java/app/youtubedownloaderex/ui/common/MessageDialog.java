package app.youtubedownloaderex.ui.common;

import app.youtubedownloaderex.AppConstraints;

import javax.swing.*;

public class MessageDialog {

    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "YouTube Downloader EX - error", JOptionPane.ERROR_MESSAGE);
    }

    public static void throwErrorMessage(String message, Throwable throwable) {

        if (AppConstraints.DEBUG_MODE) {
            throwable.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, String.format("%s\n詳細:\n%s", message, throwable.getMessage()), "YouTube Downloader EX - error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "YouTube Downloader EX", JOptionPane.INFORMATION_MESSAGE);
    }
}
