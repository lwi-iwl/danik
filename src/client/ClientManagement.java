package client;

import java.awt.*;
import java.awt.event.*;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Locale;

public class ClientManagement {
    private float x = 1;
    private float y = 1;
    private Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    public void startClientManagement(Board board, String host) throws IOException {
        Socket socket = new Socket(host, 3346);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        board.setFocusable(true);
        board.requestFocusInWindow();
        board.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e){
                try {

                    int notches = e.getWheelRotation();
                    dataOutputStream.writeUTF("WHEEL");
                    dataOutputStream.writeInt(notches);
                }
                catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        board.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                        dataOutputStream.writeUTF("PRESSR");
                        dataOutputStream.writeInt(Math.round(e.getX() * x));
                        dataOutputStream.writeInt(Math.round(e.getY() * y));
                    }
                    else
                    {
                        dataOutputStream.writeUTF("PRESSL");
                        dataOutputStream.writeInt(Math.round(e.getX() * x));
                        dataOutputStream.writeInt(Math.round(e.getY() * y));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
                        dataOutputStream.writeUTF("RELEASER");
                        dataOutputStream.writeInt(Math.round(e.getX() * x));
                        dataOutputStream.writeInt(Math.round(e.getY() * y));
                    }
                    else {
                        dataOutputStream.writeUTF("RELEASEL");
                        dataOutputStream.writeInt(Math.round(e.getX() * x));
                        dataOutputStream.writeInt(Math.round(e.getY() * y));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        });

        board.addMouseMotionListener(new MouseAdapter(){

            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    dataOutputStream.writeUTF("MOVE");
                    dataOutputStream.writeInt(Math.round(e.getX() * x));
                    dataOutputStream.writeInt(Math.round(e.getY() * y));

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                try {
                    System.out.println("MOVE");
                    dataOutputStream.writeUTF("MOVE");
                    dataOutputStream.writeInt(Math.round(e.getX()*x));
                    dataOutputStream.writeInt(Math.round(e.getY()*y));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        board.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                try {
                    dataOutputStream.writeUTF("KEYPRESS");
                    if (e.getKeyCode() != 0) {
                        InputContext context2 = InputContext.getInstance();
                        String Language=""+context2.getLocale().getISO3Language();
                        System.out.println(Language);
                        if ((Language.equals("rus"))&&(e.getKeyCode() == 46))
                            dataOutputStream.writeInt(KeyEvent.VK_SLASH);
                        else
                            dataOutputStream.writeInt(e.getKeyCode());
                    }
                    else {
                        switch (e.getKeyChar()) {
                            case ('б'), ('Б') -> dataOutputStream.writeInt(KeyEvent.VK_COMMA);
                            case ('ю'), ('Ю') -> dataOutputStream.writeInt(KeyEvent.VK_PERIOD);
                            case ('ж'), ('Ж') -> dataOutputStream.writeInt(KeyEvent.VK_SEMICOLON);
                            case ('.'), (',') -> dataOutputStream.writeInt(KeyEvent.VK_SLASH);
                            case ('э'), ('Э') -> dataOutputStream.writeInt(KeyEvent.VK_QUOTE);
                            case ('х'), ('Х') -> dataOutputStream.writeInt(KeyEvent.VK_OPEN_BRACKET);
                            case ('ъ'), ('Ъ') -> dataOutputStream.writeInt(KeyEvent.VK_CLOSE_BRACKET);
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    dataOutputStream.writeUTF("KEYRELEASE");
                    if (e.getKeyCode() != 0) {
                        dataOutputStream.writeInt(e.getKeyCode());
                    }
                    else {
                        switch (e.getKeyChar()) {
                            case ('б'), ('Б') -> dataOutputStream.writeInt(KeyEvent.VK_COMMA);
                            case ('ю'), ('Ю') -> dataOutputStream.writeInt(KeyEvent.VK_PERIOD);
                            case ('ж'), ('Ж') -> dataOutputStream.writeInt(KeyEvent.VK_SEMICOLON);
                            case ('/'), ('?') -> dataOutputStream.writeInt(KeyEvent.VK_SLASH);
                            case ('э'), ('Э') -> dataOutputStream.writeInt(KeyEvent.VK_QUOTE);
                            case ('х'), ('Х') -> dataOutputStream.writeInt(KeyEvent.VK_OPEN_BRACKET);
                            case ('ъ'), ('Ъ') -> dataOutputStream.writeInt(KeyEvent.VK_CLOSE_BRACKET);
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public void setMultiplier(BufferedImage image){
        x = (float)(image.getWidth()/sSize.width/0.9);
        y = (float)(image.getHeight()/sSize.height/0.9);
        System.out.println(x);
    }

}