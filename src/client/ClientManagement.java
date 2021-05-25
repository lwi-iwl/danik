package client;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientManagement {

    boolean isEnter = false;
    public void startClientManagement(Board board) throws IOException {
        Socket socket = new Socket("localhost", 3346);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                try {
                    dataOutputStream.writeUTF("ENTER");
                    dataOutputStream.writeInt(e.getX());
                    dataOutputStream.writeInt(e.getY());
                    isEnter = true;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    dataOutputStream.writeUTF("RELEASE");
                    dataOutputStream.writeInt(e.getX());
                    dataOutputStream.writeInt(e.getY());
                    isEnter = false;
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (isEnter) {
                    try {
                        dataOutputStream.writeUTF("MOVE");
                        dataOutputStream.writeInt(e.getX());
                        dataOutputStream.writeInt(e.getY());
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    dataOutputStream.writeUTF("CLICK");
                    dataOutputStream.writeInt(e.getX());
                    dataOutputStream.writeInt(e.getY());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }
}
