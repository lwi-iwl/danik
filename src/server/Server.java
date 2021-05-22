package server;


import client.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Server<inputPacket, receivedData, sendingDataBuffer, outputPacket> {
    Timer timer = new Timer();
    private final Capture capture = new Capture();
    public Server() throws AWTException {

    }
    public void doServer(Board board) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                board.repaint();
            }
        }, 10, 10);}



    private final static int SERVICE_PORT=50001;

    public void startServer() throws IOException{
        ServerSocket server= new ServerSocket(3345);
        Socket client = server.accept();

        System.out.print("Connection accepted.");

        BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
        System.out.println("DataOutputStream  created");

        DataInputStream in = new DataInputStream(client.getInputStream());
        System.out.println("DataInputStream created");

        System.out.println("Server reading from channel");
        String entry = in.readUTF();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try {
                    ImageIO.write(capture.getCapture(), "jpg", baos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byte[] bytes = baos.toByteArray();
                try {
                    out.write(bytes, 0, bytes.length);
                    System.out.println("Server Wrote message to client.");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 10, 10);


        //serverSocket.close();
    }
}
