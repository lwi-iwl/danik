package client;

import server.Capture;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientManagement {
    private float x = 1;
    private float y = 1;
    private boolean isEnter = false;
    private Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
    public void startClientManagement(Board board) throws IOException {
        Socket socket = new Socket("192.168.1.9", 3346);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                    isEnter = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                    isEnter = false;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    dataOutputStream.writeUTF("CLICK");
                    dataOutputStream.writeInt(Math.round(e.getX()*x));
                    dataOutputStream.writeInt(Math.round(e.getY()*y));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        board.addMouseMotionListener(new MouseAdapter(){
            @Override
            public void mouseMoved(MouseEvent e) {
                try {
                    if (isEnter){
                        dataOutputStream.writeUTF("DRAG");
                        dataOutputStream.writeInt(Math.round(e.getX()*x));
                        dataOutputStream.writeInt(Math.round(e.getY()*y));
                    }
                    else {
                        dataOutputStream.writeUTF("MOVE");
                        dataOutputStream.writeInt(Math.round(e.getX() * x));
                        dataOutputStream.writeInt(Math.round(e.getY() * y));
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
    }

    public void setMultiplier(BufferedImage image){
        x = (float)image.getWidth()/sSize.width;
        y = (float)image.getHeight()/sSize.height;
        System.out.println(x);
    }
}
