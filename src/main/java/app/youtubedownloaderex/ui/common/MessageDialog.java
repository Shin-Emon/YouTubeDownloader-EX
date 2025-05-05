package app.youtubedownloaderex.ui.common;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.lang.LangAssets;

import javax.swing.*;

public class MessageDialog {

    public static void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(null, message, AppConstraints.TITLE + " - error", JOptionPane.ERROR_MESSAGE);
    }

    public static void throwErrorMessage(String message, Throwable throwable) {

        if (AppConstraints.DEBUG_MODE) {
            throwable.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, String.format("%s\n%s:\n%s", message, LangAssets.get("dialog.info.detail_info"), throwable.getMessage()), "YouTube Downloader EX - error", JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(null, message, AppConstraints.TITLE, JOptionPane.INFORMATION_MESSAGE);
    }
}
