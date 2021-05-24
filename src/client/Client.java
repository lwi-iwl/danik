package client;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client {


    public Client() throws AWTException {

    }
    BufferedImage newBi;
    byte[] buffer = new byte[180000];
    InputStream is = new ByteArrayInputStream(buffer);
    public void startClient(Board board) throws IOException {
        Socket socket = new Socket("localhost", 3345);
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
        BufferedInputStream ois = new BufferedInputStream(socket.getInputStream());
        String clientCommand = "sd";
        oos.writeUTF(clientCommand);
        oos.flush();
        new Thread(() -> {
            try {
                while (true) {
                    is.reset();
                    int bytesRead = ois.read(buffer);
                    System.out.println(bytesRead);
                    oos.writeUTF(clientCommand);
                    oos.flush();
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
