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
        Input input = new Input();
        oos.writeUTF(clientCommand);
        oos.flush();
        new Thread(() -> {

            try {
                input.setInputStream(is);
                while (true) {
                    is.reset();
                    int bytesRead = ois.read(buffer);
                    input.setInputStream(is);
                    oos.writeUTF(clientCommand);
                    oos.flush();
                    newBi = ImageIO.read(is);
                    board.setCapture(newBi);
                    if (newBi!=null)
                        board.repaint();
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();

        //input.startPaint(board);

    }

}
