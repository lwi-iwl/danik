package server;


import client.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ServerImg {

    private final Capture capture = new Capture();
    public ServerImg() throws AWTException {

    }
    String entry;

    public void startServer() throws IOException{
        new Thread(() -> {
            try {
                ServerSocket server= new ServerSocket(3345);
                Socket client = server.accept();
                System.out.print("Connection accepted.");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(client.getOutputStream());
                System.out.println("DataOutputStream  created");
                DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
                System.out.println("DataInputStream created");
                DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
                System.out.println("Server reading from channel");
                entry = dataInputStream.readUTF();
                capture.newCapture();

                byte[] bytes;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(capture.getCapture(), "jpeg", baos);
                bytes = baos.toByteArray();
                while (true) {
                    dataOutputStream.writeInt(bytes.length);
                    bufferedOutputStream.write(bytes);
                    System.out.println("Server"+bytes.length);
                    bufferedOutputStream.flush();
                    if (capture.getBaos()!=null)
                        bytes = capture.getBaos();
                    entry = dataInputStream.readUTF();
                }
            } catch (IOException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }).start();


    }



    public void startUDPServer() throws IOException {
        new Thread(() -> {
            try {
                DatagramSocket serverSocket = new DatagramSocket(50001);

                byte[] receivingDataBuffer = new byte[65024];
                byte[] sendingDataBuffer = new byte[65024];

                DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                System.out.println("Waiting for a client to connect...");
                serverSocket.receive(inputPacket);
                String receivedData = new String(inputPacket.getData());
                System.out.println("Sent from the client: "+receivedData);


                InetAddress senderAddress = inputPacket.getAddress();
                int senderPort = inputPacket.getPort();



                while(true) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(capture.getCapture(), "jpg", baos);
                    byte[] bytes = baos.toByteArray();
                    byte[] bytes1 = Arrays.copyOfRange(bytes, 0, 65000);
                    System.out.println(bytes.length);
                    DatagramPacket outputPacket = new DatagramPacket(
                            bytes1, bytes1.length,
                            senderAddress,senderPort
                    );
                    serverSocket.send(outputPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

