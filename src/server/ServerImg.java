package server;


import panel.NewDialog;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.*;


public class ServerImg {

    public ServerImg() {

    }
    private String entry = "STOP";
    ServerSocket server;
    public void startServer(NewDialog dialog){
        new Thread(() -> {
            entry = "STOP";
            ServerManagement serverManagement = new ServerManagement();
            try {
                server = new ServerSocket(3345);
                server.setSoTimeout(1000);
                DataInputStream dataInputStream = null;
                Capture capture = new Capture();
                BufferedOutputStream bufferedOutputStream = null;
                DataOutputStream dataOutputStream = null;
                Socket client = null;
                while (!entry.equals("START") && !entry.equals("INFSTOP")) {
                    while (entry.equals("STOP")) {
                        try {
                            System.out.println("TRY");
                            client = server.accept();
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
                    if(entry.equals("STOP"))
                        dataOutputStream.writeUTF("STOP");
                }
                if (!entry.equals("INFSTOP")) {
                    dataOutputStream.writeUTF("START");
                    System.out.println("START");

                    serverManagement.startServerManagement();
                    capture.newCapture();

                    byte[] bytes;
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Robot robot = new Robot();
                    ImageIO.write(robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())), "jpeg", baos);
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

                    capture.setClose(false);
                    if (bufferedOutputStream!=null){
                        bufferedOutputStream.close();
                        dataInputStream.close();
                        dataOutputStream.close();
                        client.close();
                    }
                    try {
                        server.close();
                        serverManagement.getServerManagementSocket().close();
                        startServer(dialog);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("INFSTOP");
                    dataOutputStream.writeUTF("STOP");
                }
            } catch (IOException | NullPointerException | InterruptedException | AWTException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("EXIT");
        }).start();
    }



    public void stopServ(){
        entry = "STOP";
    }

    public void infStopServ() {
        entry = "INFSTOP";
    }


    public void startServ() {
        entry = "START";
    }

    public ServerSocket getSocket(){
        return server;
    }

}