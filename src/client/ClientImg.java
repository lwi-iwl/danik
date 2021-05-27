package client;


import panel.StartManage;
import server.ServerImg;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

public class ClientImg {


    public ClientImg() throws AWTException {

    }
    private boolean isSetSize = false;
    private BufferedImage newBi;
    private byte[] buffer = new byte[280000];
    private String clientCommand = "REQUEST";
    private InputStream is = new ByteArrayInputStream(buffer);
    public void startClient(Board board, ServerImg server, StartManage startManage) throws IOException {
        isSetSize = false;
        ClientManagement clientManagement = new ClientManagement();
        try{
            clientCommand = "REQUEST";
            Socket socket = new Socket("192.168.1.9", 3345);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(clientCommand);
            dataOutputStream.flush();
            String serverCommand = dataInputStream.readUTF();
            if (!serverCommand.equals("STOP")) {
                new Thread(() -> {
                    try {
                        int quan = 0;
                        int cursor;
                        while (quan!=1) {
                            is.reset();
                            quan = dataInputStream.readInt();
                            System.out.println("quan" + quan);
                            int offset = 0;
                            int bytesRead = 0;
                            while (offset < quan) {
                                bytesRead = bufferedInputStream.read(buffer, offset, quan - offset);
                                offset += bytesRead;
                            }
                            System.out.println("Client" + offset);
                            dataOutputStream.writeUTF(clientCommand);
                            dataOutputStream.flush();
                            newBi = ImageIO.read(is);
                            if (newBi != null) {
                                if (!isSetSize) {
                                    server.infStopServ();
                                    startManage.start();
                                    isSetSize = true;
                                    clientManagement.setMultiplier(newBi);
                                    board.setMultiplier(newBi);
                                    clientManagement.startClientManagement(board);
                                }
                                board.setCapture(newBi);
                                board.repaint();
                            } else
                                System.out.println(0);
                        }

                    } catch (IOException | AWTException e) {
                        System.out.println(e.getMessage());
                        startManage.startManageExit();
                        try {
                            MouseListener[] mouseListeners = board.getMouseListeners();
                            for (MouseListener mouseListener : mouseListeners) {
                                board.removeMouseListener(mouseListener);
                            }
                            MouseMotionListener[] mouseMotionListeners = board.getMouseMotionListeners();
                            for (MouseMotionListener mouseMotionListener : mouseMotionListeners) {
                                board.removeMouseMotionListener(mouseMotionListener);
                            }
                            MouseWheelListener[] mouseWheelListeners = board.getMouseWheelListeners();
                            for (MouseWheelListener mouseWheelListener : mouseWheelListeners) {
                                board.removeMouseWheelListener(mouseWheelListener);
                            }
                            dataInputStream.close();
                            dataOutputStream.close();
                            bufferedInputStream.close();
                            clientManagement.stopClientManagement();
                            System.out.println("EXITT");
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                }).start();
            }
            else
            {
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
                bufferedInputStream.close();
            }
        }
        catch (ConnectException e){
            System.out.println(e.getMessage());
        }
    }





    public void startUDPClient(Board board) throws IOException {
        new Thread(() -> {
            try {
                DatagramSocket clientSocket = new DatagramSocket();

                InetAddress IPAddress = null;
                IPAddress = InetAddress.getByName("192.168.1.9");

                byte[] sendingDataBuffer = new byte[65024];
                byte[] receivingDataBuffer = new byte[65024];

                String sentence = "Hello from UDP client";
                sendingDataBuffer = sentence.getBytes();

                DatagramPacket sendingPacket = new DatagramPacket(sendingDataBuffer, sendingDataBuffer.length, IPAddress, 50001);
                clientSocket.send(sendingPacket);

                while (true) {
                    DatagramPacket receivingPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
                    clientSocket.receive(receivingPacket);
                    clientSocket.send(sendingPacket);

                    InputStream is = new ByteArrayInputStream(receivingPacket.getData(), receivingPacket.getOffset(), receivingPacket.getLength());
                    BufferedImage newBi = null;
                    newBi = ImageIO.read(is);
                    board.setCapture(newBi);
                    board.repaint();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setClientCommand(String clientCommand){
        System.out.println("EXIT");
        this.clientCommand = clientCommand;
    }
}