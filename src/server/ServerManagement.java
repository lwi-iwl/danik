package server;


import java.awt.*;
import java.awt.event.InputEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerManagement {

    private Boolean isTrue = true;
    private DataInputStream dataInputStream;
    private Socket client;
    ServerSocket server;
    public void startServerManagement() throws IOException {
        new Thread(() -> {
            try {
                String action = "";
                server = new ServerSocket(3346);
                client = server.accept();
                dataInputStream = new DataInputStream(client.getInputStream());
                Robot bot = new Robot();
                while (isTrue) {
                    try {
                        action = dataInputStream.readUTF();
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
                    catch (SocketException e){
                        e.getMessage();
                    }
                }
            }
            catch (IOException | IllegalArgumentException | AWTException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setClose(Boolean isTrue) throws IOException {
        this.isTrue = isTrue;
        dataInputStream.close();
        client.close();
        server.close();
        System.out.println("close");
    }
}