package client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

public class Client {
    Timer timer = new Timer();
    public Client() throws AWTException {

    }
    public final static int SERVICE_PORT = 50001;
    public void startClient(Board board) throws IOException {

        DatagramSocket clientSocket = new DatagramSocket();

        InetAddress IPAddress = InetAddress.getByName("localhost");

        byte[] sendingDataBuffer = new byte[65024];
        byte[] receivingDataBuffer = new byte[65024];

        String sentence = "Hello from UDP client";
        sendingDataBuffer = sentence.getBytes();

        DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, SERVICE_PORT);

        clientSocket.send(sendingPacket);

        final DatagramPacket[] receivingPacket = new DatagramPacket[1];
        String receivedData;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                receivingPacket[0] = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                try {
                    clientSocket.receive(receivingPacket[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //receivedData = new String(receivingPacket.getData(), receivingPacket.getOffset(), receivingPacket.getLength());
                InputStream is = new ByteArrayInputStream(receivingPacket[0].getData(), receivingPacket[0].getOffset(), receivingPacket[0].getLength());
                BufferedImage newBi = null;
                try {
                    newBi = ImageIO.read(is);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                board.setCapture(newBi);
                board.repaint();
            }
        }, 10, 10);

    }

}
