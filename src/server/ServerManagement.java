package server;


import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ServerManagement {

    private Boolean isTrue = true;
    ServerSocket server;
    public void startServerManagement() throws IOException {
        new Thread(() -> {
            try {
                isTrue = true;
                String action = "";
                server = new ServerSocket(3346);
                Socket client = server.accept();
                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                Robot bot = new Robot();
                int keyInt;
                while (isTrue) {
                    try {
                        action = dataInputStream.readUTF();
                        System.out.println(action);
                        if (action.equals("DRAG")) {
                            bot.mouseMove((int) Math.round(dataInputStream.readInt()  ), (int) Math.round(dataInputStream.readInt()  ));
                            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        }

                        else if (action.equals("PRESSL")){
                            bot.mouseMove((int) Math.round(dataInputStream.readInt()  ), (int) Math.round(dataInputStream.readInt()  ));
                            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                        }
                        else if (action.equals("PRESSR")){
                            bot.mouseMove((int) Math.round(dataInputStream.readInt()  ), (int) Math.round(dataInputStream.readInt()  ));
                            bot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                        }
                        else if (action.equals("RELEASEL")){
                            bot.mouseMove((int) Math.round(dataInputStream.readInt()  ), (int) Math.round(dataInputStream.readInt()  ));
                            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                        }
                        else if (action.equals("RELEASER")){
                            bot.mouseMove((int) Math.round(dataInputStream.readInt()  ), (int) Math.round(dataInputStream.readInt()  ));
                            bot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
                        }
                        else if (action.equals("MOVE")){
                            bot.mouseMove((int) Math.round(dataInputStream.readInt()  ), (int) Math.round(dataInputStream.readInt()  ));
                        }
                        else if (action.equals("WHEEL")){
                            bot.mouseWheel(dataInputStream.readInt());
                        }
                        else if (action.equals("KEYPRESS")){
                                keyInt = dataInputStream.readInt();
                                System.out.println(keyInt);
                            try {
                                bot.keyPress(keyInt);
                            }
                            catch (IllegalArgumentException e){
                                char c = '0';
                                switch (keyInt) {
                                    case KeyEvent.VK_COMMA -> c = 'б';
                                    case KeyEvent.VK_LESS -> c = 'Б';
                                    case KeyEvent.VK_PERIOD -> c = 'ю';
                                    case KeyEvent.VK_GREATER -> c = 'Ю';
                                    //case KeyEvent.VK_SLASH -> c = '.';
                                    //case KeyEvent.VK_Q -> c = 'Б';
                                    case KeyEvent.VK_SEMICOLON -> c = 'ж';
                                    case KeyEvent.VK_COLON -> c = 'Ж';
                                    case KeyEvent.VK_QUOTE -> c = 'э';
                                    case KeyEvent.VK_QUOTEDBL -> c = 'Э';
                                    case KeyEvent.VK_OPEN_BRACKET -> c = 'х';
                                    case KeyEvent.VK_BRACELEFT -> c = 'Х';
                                    case KeyEvent.VK_CLOSE_BRACKET -> c = 'ъ';
                                    case KeyEvent.VK_BRACERIGHT -> c = 'Ъ';
                                }
                                pressUnicode(c, bot);
                            }
                        }
                        else if (action.equals("KEYRELEASE")){
                            keyInt = dataInputStream.readInt();
                            bot.keyRelease(keyInt);
                        }
                    }
                    catch (SocketException e){
                        e.getMessage();
                        server.close();
                    }
                }
                server.close();
                System.out.println("managementClose");
            }
            catch (IOException | IllegalArgumentException | AWTException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public ServerSocket getServerManagementSocket(){
        return server;
    }

    private void pressUnicode(char c, Robot robot) {
        String cantRecognize = ""+c;
        StringSelection selection = new StringSelection(cantRecognize);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
    }
}