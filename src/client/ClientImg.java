package client;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class ClientImg {


    public ClientImg() throws AWTException {

    }
    BufferedImage newBi;
    byte[] buffer = new byte[280000];
    InputStream is = new ByteArrayInputStream(buffer);
    public void startClient(Board board) throws IOException {
        Socket socket = new Socket("localhost", 3345);
        DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        String clientCommand = "sd";
        dataOutputStream.writeUTF(clientCommand);
        dataOutputStream.flush();
        new Thread(() -> {
            try {
                int quan;
                while (true) {
                    is.reset();
                    quan = dataInputStream.readInt();
                    System.out.println(quan);
                    int offset = 0;
                    int bytesRead = 0;
                    while (offset < quan) {
                        bytesRead = bufferedInputStream.read(buffer, offset, quan-offset);
                        offset += bytesRead;
                    }
                    System.out.println("Client"+offset);
                    dataOutputStream.writeUTF(clientCommand);
                    dataOutputStream.flush();
                    newBi = ImageIO.read(is);
                    if (newBi!=null) {
                        board.setCapture(newBi);
                        board.repaint();
                    }
                    else
                        System.out.println(0);
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();

    }

}