package server;


import client.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

public class Server {

    private final Capture capture = new Capture();
    public Server() throws AWTException {

    }
    String entry;

    public void startServer() throws IOException{
        new Thread(() -> {
            try {
                ServerSocket server= new ServerSocket(3345);
                Socket client = server.accept();
                System.out.print("Connection accepted.");
                BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
                System.out.println("DataOutputStream  created");
                DataInputStream in = new DataInputStream(client.getInputStream());
                System.out.println("DataInputStream created");
                System.out.println("Server reading from channel");
                entry = in.readUTF();
                capture.newCapture();

                byte[] bytes;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(capture.getCapture(), "jpeg", baos);
                bytes = baos.toByteArray();
                while (true) {
                    out.write(bytes);
                    System.out.println("Server"+bytes.length);
                    out.flush();
                    if (capture.getBaos()!=null)
                        bytes = capture.getBaos();
                    entry = in.readUTF();
                }
            } catch (IOException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }).start();


    }
}
