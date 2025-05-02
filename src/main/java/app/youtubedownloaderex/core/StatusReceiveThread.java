package app.youtubedownloaderex.core;

import app.youtubedownloaderex.AppConstraints;
import app.youtubedownloaderex.core.function.StatusListener;
import app.youtubedownloaderex.ui.common.MessageDialog;

import java.io.*;
import java.net.ServerSocket;

public class StatusReceiveThread extends Thread {

    private final StatusListener receiver;

    public StatusReceiveThread(StatusListener receiver) {
        this.receiver = receiver;
    }

    @Override
    public void run() {

        try (var server = new ServerSocket(AppConstraints.receivePort)) {

            var sock = server.accept();

            var writer = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()), true);
            writer.println("ready");

            var br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
//            System.out.println(br.readLine());

            String line = "";
            while (line != null) {
                line = br.readLine();
                receiver.receive(line);
            }
            br.close();
            writer.close();
            sock.close();

        } catch (Exception e) {
            MessageDialog.throwErrorMessage("むりやった。", e);
        }

        receiver.receive("closed");

    }
}
