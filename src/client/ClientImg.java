package client;


import panel.NewDialog;
import panel.StartManage;
import server.ServerImg;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;

public class ClientImg {


    public ClientImg() {

    }
    private boolean isSetSize = false;
    private String clientCommand = "REQUEST";
    public void startClient(Board board, ServerImg server, StartManage startManage, NewDialog dialog, String host, JFrame jf) throws IOException {
        isSetSize = false;
        ClientManagement clientManagement = new ClientManagement();
        new Thread(() -> {
        try{
            clientCommand = "REQUEST";
            Socket socket = new Socket();
            socket.setSoTimeout(20000);
            socket.connect(new InetSocketAddress(host, 3345), 3000);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            dataOutputStream.writeUTF(clientCommand);
            dataOutputStream.flush();
            System.out.println("bufferedinputstream");
            String serverCommand = dataInputStream.readUTF();
            System.out.println("bufferedREAD");
            if (!serverCommand.equals("STOP")) {
                    byte[] buffer = new byte[280000];
                    BufferedImage newBi;
                    InputStream is = new ByteArrayInputStream(buffer);
                    try {
                        int quan;
                        while (true) {
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
                                    server.getSocket().close();
                                    startManage.start();
                                    jf.setVisible(false);
                                    isSetSize = true;
                                    clientManagement.setMultiplier(newBi);
                                    board.setMultiplier(newBi);
                                    clientManagement.startClientManagement(board, host);
                                }
                                board.setCapture(newBi);
                                board.repaint();
                            } else
                                System.out.println(0);
                        }

                    } catch (IOException | AWTException e) {
                        System.out.println(e.getMessage());
                        startManage.startManageExit();
                        jf.setVisible(true);
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
                            server.startServer(dialog);
                        } catch (Exception ioException) {
                            ioException.printStackTrace();
                        }
                    }
            }
            else
            {
                socket.close();
                dataInputStream.close();
                dataOutputStream.close();
                bufferedInputStream.close();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        }).start();
    }


    public void setClientCommand(String clientCommand){
        System.out.println("EXIT");
        this.clientCommand = clientCommand;
    }
}