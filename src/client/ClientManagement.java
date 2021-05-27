package client;

import server.Capture;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientManagement {
    private float x = 1;
    private float y = 1;
    private boolean isEnter = false;
    private Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    private DataOutputStream dataOutputStream;
    private Socket socket;
    public void startClientManagement(Board board) throws IOException {
        socket = new Socket("192.168.1.9", 3346);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
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
                    isEnter = true;
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
                    isEnter = false;
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
                    dataOutputStream.writeInt(e.getKeyCode());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    dataOutputStream.writeUTF("KEYRELEASE");
                    dataOutputStream.writeInt(e.getKeyCode());
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

    public void stopClientManagement() throws IOException {
        socket.close();
        dataOutputStream.close();
    }
}
