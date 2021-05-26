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

                    else if (action.equals("PRESSL")){
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    }
                    else if (action.equals("PRESSR")){
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                        bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    }
                    else if (action.equals("RELEASEL")){
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    }
                    else if (action.equals("RELEASER")){
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                        bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                    }
                    else if (action.equals("MOVE")){
                        bot.mouseMove((int) Math.round(dataInputStream.readInt() * 1.25), (int) Math.round(dataInputStream.readInt() * 1.25));
                    }
                    else if (action.equals("WHEEL")){
                        bot.mouseWheel(dataInputStream.readInt());
                    }
                    else if (action.equals("KEYPRESS")){
                        bot.keyPress(dataInputStream.readInt());
                    }
                    else if (action.equals("KEYRELEASE")){
                        bot.keyRelease(dataInputStream.readInt());
                    }
                }
            }
            catch (IOException | AWTException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }).start();
    }
}