package server;

import client.Board;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerManagement {

    public void startServerManagement() throws IOException {
        new Thread(() -> {
            try {
                ServerSocket server= new ServerSocket(3346);
                Socket client = server.accept();
                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                while (true) {
                    Robot bot = new Robot();
                    String action = dataInputStream.readUTF();
                    System.out.println(action);
                    if (action.equals("DRAG")) {
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }

                    if (action.equals("CLICK")){
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                    if (action.equals("MOVE")){
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                    }
                }
            }
            catch (IOException | AWTException e) {
                e.printStackTrace();
            }
        }).start();
    }
}