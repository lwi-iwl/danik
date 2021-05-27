package server;


import panel.NewDialog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;


public class ServerImg {

    private final Capture capture = new Capture();
    public ServerImg() throws AWTException {

    }
    String entry = "STOP";
    Thread threadServer;

    public void startServer(NewDialog dialog) throws Exception{
        threadServer = new Thread(() -> {
            entry = "STOP";
            try {
                ServerSocket server= new ServerSocket(3345);
                server.setSoTimeout(1000);
                BufferedOutputStream bufferedOutputStream = null;
                DataInputStream dataInputStream = null;
                DataOutputStream dataOutputStream = null;
                while (!entry.equals("START") && !entry.equals("INFSTOP")) {
                    while (entry.equals("STOP")) {
                        try {
                            System.out.println("TRY");
                            Socket client = server.accept();
                            System.out.print("Connection accepted.");
                            bufferedOutputStream = new BufferedOutputStream(client.getOutputStream());
                            System.out.println("DataOutputStream  created");
                            dataInputStream = new DataInputStream(client.getInputStream());
                            System.out.println("DataInputStream created");
                            dataOutputStream = new DataOutputStream(client.getOutputStream());
                            System.out.println("Server reading from channel");
                            entry = dataInputStream.readUTF();
                        } catch (InterruptedIOException e) {
                            e.getMessage();
                        }
                    }
                    if (entry.equals("REQUEST")) {
                        dialog.setVisible();
                    }
                    while ((!entry.equals("START")) && (!entry.equals("STOP"))) {
                        Thread.sleep(100);
                    }
                }
                if (!entry.equals("INFSTOP")) {
                    System.out.println("START");
                    ServerManagement serverManagement = new ServerManagement();
                    serverManagement.startServerManagement();
                    capture.newCapture();

                    byte[] bytes;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(capture.getCapture(), "jpeg", baos);
                    bytes = baos.toByteArray();
                    while (!entry.equals("INFSTOP")) {
                        dataOutputStream.writeInt(bytes.length);
                        bufferedOutputStream.write(bytes);
                        System.out.println("Server" + bytes.length);
                        bufferedOutputStream.flush();
                        if (capture.getBaos() != null)
                            bytes = capture.getBaos();
                        entry = dataInputStream.readUTF();
                    }
                }
                else{
                    System.out.println("INFSTOP");
                }
            } catch (IOException | NullPointerException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
        threadServer.start();
    }


    public void stopServ(){
        entry = "STOP";
    }

    public void infStopServ(){
        entry = "INFSTOP";
    }

    public void startServ(){
        entry = "START";
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
                capture.newCapture();
                byte[] bytes;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(capture.getCapture(), "jpeg", baos);
                bytes = baos.toByteArray();
                while(true) {
                    byte[] bytes1 = Arrays.copyOfRange(bytes, 0, 65000);
                    System.out.println(bytes.length);
                    DatagramPacket outputPacket = new DatagramPacket(
                            bytes1, bytes1.length,
                            senderAddress,senderPort
                    );
                    serverSocket.send(outputPacket);
                    if (capture.getBaos()!=null)
                        bytes = capture.getBaos();
                    serverSocket.receive(inputPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}