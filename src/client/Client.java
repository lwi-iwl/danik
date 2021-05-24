package client;


import java.awt.*;
import java.io.*;
import java.net.Socket;

public class Client {


    public Client() throws AWTException {

    }

    public void startClient(Board board) throws IOException {
        Socket socket = new Socket("192.168.1.9", 3345);
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
        BufferedInputStream ois = new BufferedInputStream(socket.getInputStream());
        String clientCommand = "sd";
        Input input = new Input();
        oos.writeUTF(clientCommand);
        oos.flush();
        new Thread(() -> {
            byte[] buffer = new byte[180000];
            try (InputStream is = new ByteArrayInputStream(buffer)){
                input.setInputStream(is);
                input.startPaint(board);
            while (true) {
                    is.reset();
                    int bytesRead = ois.read(buffer);
                    input.setInputStream(is);
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();




    }

}
